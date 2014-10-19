package pe.com.tumi.tesoreria.banco.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.ConvertirLetras;
import pe.com.tumi.common.util.DocumentoGeneral;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
//import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;
//import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp2;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp;
import pe.com.tumi.servicio.prevision.facade.LiquidacionFacadeRemote;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.PrestamoFacadeRemote;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.BancofondoId;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalleId;
import pe.com.tumi.tesoreria.egreso.domain.EgresoId;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;

public class TransferenciaController {

	protected static Logger log = Logger.getLogger(TransferenciaController.class);
	
	private EmpresaFacadeRemote 		empresaFacade;
	private PersonaFacadeRemote 		personaFacade;
	private TablaFacadeRemote 			tablaFacade;
	private PlanCuentaFacadeRemote 		planCuentaFacade;
	private BancoFacadeLocal 			bancoFacade;
	private EgresoFacadeLocal 			egresoFacade;
	private PrestamoFacadeRemote 		prestamoFacade;
	private PrevisionFacadeRemote		previsionFacade;
	private LiquidacionFacadeRemote		liquidacionFacade;
	private LibroDiarioFacadeRemote		libroDiarioFacade;
	private CierreDiarioArqueoFacadeLocal 	cierreDiarioArqueoFacade;
	
	private	List<Egreso>		listaEgreso;
	private	List<Tabla>			listaTipoDocumento;	
	private	List<Sucursal>		listaSucursal;
	//Autor jchavez / Tarea: Comentado / Fecha:11.09.2014
//	private	List<Subsucursal>	listaSubsucursal;
	private	List<Bancocuenta>	listaBancoCuentaOrigen;
	private	List<Bancocuenta>	listaBancoCuentaDestino;
	private	List<Tabla>	 		listaMoneda;
	private	List<Tabla>	 		listaTablaDocumentoGeneral;
	private	List<Tabla>	 		listaTablaTipoCredito;
	private	List<Tabla>	 		listaSubTipoOperacion;
	private List<Egreso> 		listaEgresoPorAgregar;
	private List<Egreso> 		listaEgresoAgregado;
	private List<Tabla>	 		listaTipoVinculo;
	private List<Persona>		listaPersonaFiltro;
	
	private	List<Persona>		listaPersona;
	private List<DocumentoGeneral>		listaDocumentoAgregados;
	private List<DocumentoGeneral>		listaDocumentoPorAgregar;
	private List<EgresoDetalleInterfaz> listaEgresoDetalleInterfazAgregado;
	private List<BeneficiarioPrevision> listaBeneficiarioPrevision;
	private List<Persona>	listaBeneficiarioPersona;
	
	private Egreso		egresoNuevo;
	private Egreso		egresoFiltro;
	private Sucursal	sucursalUsuario;
	private	Subsucursal	subsucursalUsuario;
	private	Bancocuenta bancoCuentaOrigen;
	private	Bancocuenta bancoCuentaDestino;
	private	Persona 	personaSeleccionada;
	private	CuentaBancaria	cuentaBancariaSeleccionada;
	private Egreso		egresoTelecreditoFiltro;
	private Bancofondo	fondoTelecredito;
	private Archivo		archivoCartaPoder;
	private Persona 	personaApoderado;
	private LibroDiario	libroDiario;
	private	DocumentoGeneral	documentoGeneralSeleccionado;
	//Autor jchavez / Tarea: Comentado / Fecha:11.09.2014
//	private Sucursal	sucursalSedeCentral;
//	private	Subsucursal	subsucursalSedeCentral;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private String 		mensajePopUp;
	private Integer		intTipoDocumentoValidar;
	private	Integer		intBancoSeleccionadoOrigen;
	private Integer		intBancoCuentaSeleccionadoOrigen;
	private	Integer		intBancoSeleccionadoDestino;
	private Integer		intBancoCuentaSeleccionadoDestino;
	private	Integer		intNumeroTransferencia;
	private	BigDecimal	bdMontoGirar;
	private	Integer		intTipoSubOperacion;
	private	String		strObservacion;
	private	Date		dtDesdeFiltro;
	private	Date		dtHastaFiltro;
	private	String		strMontoGirar;
	private	String		strMontoGirarDescripcion;
	private boolean		seleccionaTelecredito;
	private Integer		intBeneficiarioSeleccionar;
	private Integer		intTipoBuscarPersona;
	private String		strTextoPersonaFiltro;
	private Integer		intTipoPersonaFiltro;
	private Integer 	intTipoBusquedaPersona;
	
	private	Integer		intTipoPersona;
	private	Integer		intTipoRol;
	private	String		strFiltroTextoPersona;
	private	Integer		intTipoDocumentoAgregar;
	private	NumberFormat 		formato;
	
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	//Autor jchavez / Tarea: Comentado / Fecha:11.09.2014
//	private Integer		ID_SUCURSAL_SEDECENTRAL = 59;
//	private	Integer		ID_SUBSUCURSAL_SEDECENTRAL = 68;
	private	Integer		BUSCAR_PERSONA = 1;
	private	Integer		BUSCAR_APODERADO = 2;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean datosValidados;
	private boolean poseePermiso;
	
	//jchavez 29.04.2014
	private List<RequisitoCredito> lstRequisitoCredito;
	private List<RequisitoCreditoComp> lstRequisitoCreditoComp;
	private String strMensajeError;
	private Boolean blnNoExisteRequisito;
	private Boolean blnVerBotonApoderado;
	
	//jchavez 10.05.2014
	private List<RequisitoPrevision> lstRequisitoPrevision;
	private List<RequisitoPrevisionComp> lstRequisitoPrevisionComp;
	private BeneficiarioPrevision beneficiarioPrevisionSeleccionado;
	private String strMensajeErrorPorBeneficiario;
	private List<RequisitoLiquidacion> lstRequisitoLiquidacion;
	private List<RequisitoLiquidacionComp> lstRequisitoLiquidacionComp;
	private BeneficiarioLiquidacion beneficiarioLiquidacionSeleccionado;
	
	private Boolean blnBeneficiarioSinAutorizacion;
	//jchavez 12.05.2014
	private Archivo archivoAdjuntoGiro;
	private List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacion;
	
	private Boolean blnFiltrosGrabOk;
	private Boolean blnHabilitarObservacion;
	private ConceptoFacadeRemote conceptoFacade;
	
	private Boolean blnVerPanelCuentaBancariaDestino;
	private Boolean blnVerPanelCuentaBancariaBeneficiario;
	private	CuentaBancaria	cuentaBancariaBeneficiarioSeleccionada;
	private Persona beneficiarioSeleccionado;
	
	private Integer intCuenta;
	
	//Autor: jchavez / Tarea: Creacion / Fecha: 11.09.2014
	private List<Tabla> listaTablaTipoMoneda;
	private List<Tabla> listaTablaTipoCuentaBancaria;
	private BigDecimal bdDiferencialGirar;
	private String strDiferencialGirarDescripcion;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 11.10.2014
	private LogisticaFacadeLocal		logisticaFacade;
	
	public TransferenciaController(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.TRANSACCION_BANCOS_TRANSFERENCIA);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	public String getInicioPage() {
		cargarUsuario();
		if(usuario!=null){
			limpiarFormulario();
			listaEgreso.clear();
			deshabilitarPanelInferior(null);
		}else log.error("--Usuario obtenido es NULL.");
		return "";
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");		
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();	
	}
	
	private void cargarValoresIniciales(){
		try{
			cierreDiarioArqueoFacade = (CierreDiarioArqueoFacadeLocal) EJBFactory.getLocal(CierreDiarioArqueoFacadeLocal.class);
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			prestamoFacade = (PrestamoFacadeRemote) EJBFactory.getRemote(PrestamoFacadeRemote.class);
			previsionFacade = (PrevisionFacadeRemote) EJBFactory.getRemote(PrevisionFacadeRemote.class);
			liquidacionFacade = (LiquidacionFacadeRemote) EJBFactory.getRemote(LiquidacionFacadeRemote.class);
			libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			logisticaFacade = (LogisticaFacadeLocal) EJBFactory.getLocal(LogisticaFacadeLocal.class);
			
			listaTipoDocumento = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "D");
			listaMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
			listaSubTipoOperacion = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPODESUBOPERACION), "A");
			listaTablaDocumentoGeneral = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL)); 
			listaTablaTipoCredito = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPO_CREDITO));
			listaTipoVinculo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOVINCULO));			
			
			intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			cargarListaSucursal();
			sucursalUsuario = usuario.getSucursal();
			subsucursalUsuario = usuario.getSubSucursal();
			egresoFiltro = new Egreso();
			cargarSedeCentral();
			log.info("subsucursalUsuario:"+subsucursalUsuario.getStrDescripcion());
			blnVerBotonApoderado = true;
			/**FORMATO**/
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(','); 
			formato = new DecimalFormat("#.00",otherSymbols);
			listaEgreso = new ArrayList<Egreso>();
			//
			lstRequisitoCredito = new ArrayList<RequisitoCredito>();
			lstRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
			lstRequisitoPrevision = new ArrayList<RequisitoPrevision>();
			lstRequisitoPrevisionComp = new ArrayList<RequisitoPrevisionComp>();
			lstRequisitoLiquidacion = new ArrayList<RequisitoLiquidacion>();
			lstRequisitoLiquidacionComp = new ArrayList<RequisitoLiquidacionComp>();
			
			blnBeneficiarioSinAutorizacion = false;
			listaTablaTipoCuentaBancaria = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCUENTABANCARIA));
			listaTablaTipoMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarSedeCentral() throws Exception{
		//Autor jchavez / Tarea: Comentado / Fecha:11.09.2014
//		sucursalSedeCentral = obtenerSucursal(ID_SUCURSAL_SEDECENTRAL);		
//		listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(ID_SUCURSAL_SEDECENTRAL);
//		subsucursalSedeCentral = obtenerSubsucursal(ID_SUBSUCURSAL_SEDECENTRAL);		
	}
	
	private void cargarListaSucursal() throws Exception{
		listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
		//Ordenamos por nombre
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});		
	}
	//Autor jchavez / Tarea: Comentado / Fecha:11.09.2014
//	private Sucursal obtenerSucursal(Integer intIdSucursal) throws Exception{
//		for(Sucursal sucursal : listaSucursal){
//			if(sucursal.getId().getIntIdSucursal().equals(intIdSucursal)){
//				return sucursal;
//			}
//		}
//		return null;
//	}
	
//	private Subsucursal obtenerSubsucursal(Integer intIdSubsucursal) throws Exception{
//		for(Subsucursal subsucursal : listaSubsucursal){
//			if(subsucursal.getId().getIntIdSubSucursal().equals(intIdSubsucursal)){
//				return subsucursal;
//			}
//		}
//		return null;
//	}
	//Fin jchavez - 11.09.2014
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			limpiarFormulario();
			datosValidados = Boolean.FALSE;
			seleccionaTelecredito = Boolean.FALSE;
			intTipoDocumentoValidar = Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS;
			intBancoCuentaSeleccionadoOrigen = null;
			intBancoSeleccionadoOrigen = null;
			intBancoCuentaSeleccionadoDestino = null;
			intBancoSeleccionadoDestino = null;
			
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = false;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void limpiarFormulario(){
		cuentaBancariaBeneficiarioSeleccionada = null;
		cuentaBancariaSeleccionada = null;
		personaSeleccionada = null;
		documentoGeneralSeleccionado = null;
		listaDocumentoAgregados = new ArrayList<DocumentoGeneral>();
		listaEgresoDetalleInterfazAgregado = new ArrayList<EgresoDetalleInterfaz>();
		listaBeneficiarioPrevision = null;
		personaApoderado = null;
		archivoCartaPoder = null;
		strObservacion = "";
		intBeneficiarioSeleccionar = null;
		bdMontoGirar = null;
		strMontoGirarDescripcion = "";
		libroDiario = null;
		strMontoGirar = "";
		intTipoPersona = 0;
		intTipoRol = 0;
		egresoNuevo = new Egreso();
		intNumeroTransferencia = null;
		intTipoDocumentoAgregar = 0;
		listaDocumentoPorAgregar = new ArrayList<DocumentoGeneral>();
		bdDiferencialGirar = null;
		strDiferencialGirarDescripcion = "";
	}
	
	public void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
	}
	
	public void mostrarMensaje(boolean exito, String mensaje){
		if(exito){
			mostrarMensajeExito = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mensajeOperacion = mensaje;
		}else{
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.TRUE;
			mensajeOperacion = mensaje;
		}
	}
	
	public void grabar(){
		log.info("--grabar");
		log.info("observacion"+strObservacion);
		Boolean exito = Boolean.FALSE;
//		deshabilitarNuevo = false;
		blnFiltrosGrabOk = true;
		String mensaje = null;
		ControlFondosFijos controlFondosFijos = new ControlFondosFijos();
		controlFondosFijos.setId(new ControlFondosFijosId());
		Egreso egreso = new Egreso();
		try {
			
			if(intBancoSeleccionadoOrigen.equals(new Integer(0))){
				mensaje = "Debe seleccionar un banco de origen.";
				blnFiltrosGrabOk = false;
				return;
			}
			if(intBancoCuentaSeleccionadoOrigen.equals(new Integer(0))){
				mensaje = "Debe seleccionar una cuenta bancaria de origen.";blnFiltrosGrabOk = false;return;
			}
			if(bdMontoGirar == null || bdMontoGirar.equals(new BigDecimal(0))){
				mensaje = "Debe ingresar un monto a girar.";blnFiltrosGrabOk = false;return;
			}
			if(strObservacion == null || strObservacion.isEmpty()){
				mensaje = "Debe ingresar una observación.";blnFiltrosGrabOk = false;return;
			}
			
			//validaciones
			if(cierreDiarioArqueoFacade.existeCierreDiarioArqueo(EMPRESA_USUARIO, sucursalUsuario.getId().getIntIdSucursal(), null)){
				//valida si la caja asociada a la sucursal se encuentra cerrada
				controlFondosFijos.getId().setIntPersEmpresa(EMPRESA_USUARIO);
				controlFondosFijos.getId().setIntSucuIdSucursal(sucursalUsuario.getId().getIntIdSucursal());
				controlFondosFijos.setIntSudeIdSubsucursal(subsucursalUsuario.getId().getIntIdSubSucursal());
				if(!cierreDiarioArqueoFacade.existeCierreDiaAnterior(controlFondosFijos)){
					mensaje = "La caja de la sucursal no se ha cerrado el dia anterior.";
					return;
				}
				if(cierreDiarioArqueoFacade.existeCierreDiaActual(controlFondosFijos)){
					mensaje = "La caja de la sucursal ya se ha cerrado para el dia de hoy.";
					return;
				}
			}	
			
			if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS)){				
				if(intBancoSeleccionadoDestino.equals(new Integer(0))){
					mensaje = "Debe seleccionar un banco de origen.";return;
				}
				if(intBancoCuentaSeleccionadoDestino.equals(new Integer(0))){
					mensaje = "Debe seleccionar una cuenta bancaria de origen.";return;
				}
				if(intNumeroTransferencia == null){
					mensaje = "Debe ingresar un número de transferencia.";return;
				}
				//Autor: jchavez / Tarea: Creación / Fecha: 18.08.2014 / Se agrega validacion de bancos
				if (intBancoSeleccionadoOrigen.equals(intBancoSeleccionadoDestino)) {
					if (intBancoCuentaSeleccionadoOrigen.equals(intBancoCuentaSeleccionadoDestino)) {
						mensaje = "El banco de origen y de destino no pueden ser iguales. Verifique.";return;
					}					
				}
			}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
				if(seleccionaTelecredito){
					if(intNumeroTransferencia == null){
						mensaje = "Debe ingresar un número de teleoperacion.";return;
					}
				}else{
					if(intNumeroTransferencia == null){
						mensaje = "Debe ingresar un número de transferencia.";return;
					}
					if(cuentaBancariaSeleccionada==null && !blnVerPanelCuentaBancariaBeneficiario){
						mensaje = "Debe seleccionar una cuenta bancaria de destino";return;
					}
					if (blnVerPanelCuentaBancariaBeneficiario && cuentaBancariaBeneficiarioSeleccionada==null) {
						mensaje = "Debe seleccionar una cuenta bancaria del beneficiario de destino";return;
					}
					//Autor: jchavez / Tarea: Creación / Fecha: 18.08.2014 / Se agrega validacion de bancos
					if (intBancoSeleccionadoOrigen.equals(intBancoSeleccionadoDestino)) {
						if (intBancoCuentaSeleccionadoOrigen.equals(intBancoCuentaSeleccionadoDestino)) {
							mensaje = "El banco de origen y de destino no pueden ser iguales. Verifique.";return;
						}					
					}
				}
			}else{
				return;
			}
			//fin validaciones
			
			agregarStrGlosa(listaDocumentoAgregados);
			
			if(registrarNuevo){
				log.info("--registrar");
				if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS)){
					procesarTransferencia();
					egreso = egresoFacade.grabarTransferenciaEntreCuentas(egresoNuevo);
					libroDiario = obtenerLibroDiario(egreso);
					strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, egresoNuevo.getListaEgresoDetalle().get(0).getIntParaTipoMoneda()); 
					
				}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS) && seleccionaTelecredito){
					procesarTransferencia();
					egreso = egresoFacade.grabarTransferenciaTelecredito(egresoNuevo, listaEgresoAgregado);
					libroDiario = obtenerLibroDiario(egreso);
					strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, egresoNuevo.getListaEgresoDetalle().get(0).getIntParaTipoMoneda()); 
				
				}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS) && !seleccionaTelecredito){
					grabarGiro(listaDocumentoAgregados , usuario);
					strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, Constante.PARAM_T_TIPOMONEDA_SOLES);
				}
				
				mensaje = "Se registró correctamente la transferencia.";
				exito = Boolean.TRUE;
				habilitarGrabar = Boolean.FALSE;
				deshabilitarNuevo = Boolean.TRUE;
				buscar();
				blnFiltrosGrabOk = true;
			}
			blnHabilitarObservacion = true;
		} catch (Exception e) {
			mensaje = "Ocurrio un error durante el proceso de transferencia.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	public void grabarGiro(List<DocumentoGeneral> listaDocumentoGeneral, Usuario usuario) throws Exception {
		bancoCuentaOrigen = obtenerBancoCuentaSeleccionadoOrigen();;
		
		Integer intTipoDocumentoGeneral = listaDocumentoGeneral.get(0).getIntTipoDocumento();
		//Solo pata el caso de documentos de movilidad, todos los documentoGeneral se giraran en un solo egreso
		if(intTipoDocumentoGeneral.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
			List<Movilidad> listaMovilidad = new ArrayList<Movilidad>();
			for(DocumentoGeneral documentoGeneral: listaDocumentoAgregados){
				listaMovilidad.add(documentoGeneral.getMovilidad());
			}
			Egreso egreso = egresoFacade.generarEgresoMovilidadTransferencia(listaMovilidad, bancoCuentaOrigen, usuario, intNumeroTransferencia, 
					intTipoDocumentoValidar, cuentaBancariaSeleccionada);
			egreso = egresoFacade.grabarGiroMovilidad(egreso, listaMovilidad);
			egresoNuevo = egreso;
			procesarItems(egresoNuevo);
			libroDiario = obtenerLibroDiario(egreso);
		//Autor: jchavez / Tarea: Creacion / Fecha: 11.10.2014
		}else if (listaDocumentoGeneral.get(0).getOrdenCompra()!=null && 
				(listaDocumentoGeneral.get(0).getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)
						|| listaDocumentoGeneral.get(0).getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA))) {
			List<OrdenCompra> listaOrdenCompra = new ArrayList<OrdenCompra>();
			for(DocumentoGeneral documentoGeneral: listaDocumentoAgregados){
				listaOrdenCompra.add(documentoGeneral.getOrdenCompra());
			}
			Egreso egreso = egresoFacade.grabarGiroOrdenCompraDocumentoPorTesoreria(listaEgresoDetalleInterfazAgregado, bancoCuentaOrigen, usuario, intTipoDocumentoAgregar, intTipoDocumentoValidar);
			egresoNuevo = egreso;
			procesarItems(egresoNuevo);
			libroDiario = obtenerLibroDiario(egreso);
		}//Fin jchavez - 11.10.2014		
		
		for(DocumentoGeneral documentoGeneral : listaDocumentoGeneral){
			ExpedienteCredito expCred = null;
			ExpedientePrevision expPrev = null;
			ExpedienteLiquidacion expLiq = null;
			if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){				
				
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				ExpedienteCredito expedienteCredito = documentoGeneral.getExpedienteCredito();
				Egreso egreso = prestamoFacade.generarEgresoPrestamoTransferencia(expedienteCredito, bancoCuentaOrigen, usuario, intNumeroTransferencia, 
						intTipoDocumentoValidar, cuentaBancariaSeleccionada);
				//jchavez 24.06.2014
				expedienteCredito = egreso.getExpedienteCredito();
				expedienteCredito.setEgreso(egreso);
				expCred = prestamoFacade.grabarGiroPrestamoPorTesoreria(expedienteCredito);
				egresoNuevo = expCred.getEgreso();
				procesarItems(egresoNuevo);
				libroDiario = obtenerLibroDiario(expCred.getEgreso());
					
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
				|| documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
				|| documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				ExpedientePrevision expedientePrevision = documentoGeneral.getExpedientePrevision();
				Egreso egreso = previsionFacade.generarEgresoPrevisionTransferencia(expedientePrevision, bancoCuentaOrigen, usuario, intNumeroTransferencia, 
						intTipoDocumentoValidar, cuentaBancariaSeleccionada!=null?cuentaBancariaSeleccionada:cuentaBancariaBeneficiarioSeleccionada);
				expedientePrevision.setEgreso(egreso);
				expPrev = previsionFacade.grabarGiroPrevisionPorTesoreria(expedientePrevision);
				egresoNuevo = expPrev.getEgreso();
				procesarItems(egresoNuevo);
				libroDiario = obtenerLibroDiario(expPrev.getEgreso());
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				ExpedienteLiquidacion expedienteLiquidacion = documentoGeneral.getExpedienteLiquidacion();
				expedienteLiquidacion.setBeneficiarioLiquidacionGirar(new BeneficiarioLiquidacion());
				for (BeneficiarioLiquidacion x : listaBeneficiarioLiquidacion) {
					if (x.getIntPersPersonaBeneficiario().equals(intBeneficiarioSeleccionar)) {
						expedienteLiquidacion.setBeneficiarioLiquidacionGirar(x);		
						break;
					}
				}
				expedienteLiquidacion.getBeneficiarioLiquidacionGirar().setPersonaApoderado(personaApoderado);
				expedienteLiquidacion.getBeneficiarioLiquidacionGirar().setArchivoCartaPoder(archivoCartaPoder);
				Egreso egreso = liquidacionFacade.generarEgresoLiquidacionTransferencia(expedienteLiquidacion, bancoCuentaOrigen, usuario, intNumeroTransferencia,intTipoDocumentoValidar, cuentaBancariaSeleccionada);
				expedienteLiquidacion.setEgreso(egreso);
				expLiq = liquidacionFacade.grabarGiroLiquidacionPorTesoreria(expedienteLiquidacion);
				egresoNuevo = expLiq.getEgreso();
				procesarItems(egresoNuevo);
				libroDiario = obtenerLibroDiario(expLiq.getEgreso());
			}
		}
	}
	
	public void procesarItems(Egreso egreso){ 
		egreso.setStrNumeroEgreso(
				obtenerPeriodoItem(	egreso.getIntItemPeriodoEgreso(), 
									egreso.getIntItemEgreso(), 
									"000000"));
			
		if(egreso.getIntContPeriodoLibro()!=null){
			egreso.setStrNumeroLibro(
					obtenerPeriodoItem(	egreso.getIntContPeriodoLibro(),
										egreso.getIntContCodigoLibro(), 
										"000000"));
		}		
	}
	
	private String obtenerPeriodoItem(Integer intPeriodo, Integer item, String patron){
		try{
			DecimalFormat formato = new DecimalFormat(patron);
			return intPeriodo+"-"+formato.format(Double.parseDouble(""+item));
		}catch (Exception e) {
			log.error(intPeriodo+" "+item+" "+patron+e.getMessage());
			return intPeriodo+"-"+item;
		}
	}
		
	public void buscar(){
		log.info("--buscar");
		listaEgreso.clear();
		try{
			strMensajeError = "";
			strMensajeErrorPorBeneficiario = "";
			if(dtDesdeFiltro!=null){
				dtDesdeFiltro = new Timestamp(dtDesdeFiltro.getTime());
			}
			
			if(dtHastaFiltro!=null){
				//Se añade un dia extra en fecha fin porque orginalmente el calendar da la fecha con 00h 00m 00s y ocasiona
				//seleccion de rangos erroneos con fechas registradas en la base de datos que si poseen horas.
				dtHastaFiltro = new Timestamp(dtHastaFiltro.getTime()+1 * 24 * 60 * 60 * 1000);
			}
			
			listaPersonaFiltro = new ArrayList<Persona>();
			if(strTextoPersonaFiltro!=null && !strTextoPersonaFiltro.isEmpty()){
				buscarPersonaFiltro();
			}
			
			egresoFiltro.getId().setIntPersEmpresaEgreso(EMPRESA_USUARIO);			
			log.info(egresoFiltro);
			listaEgreso = egresoFacade.buscarTransferencia(egresoFiltro, dtDesdeFiltro, dtHastaFiltro, listaPersonaFiltro);
			
			for(Egreso egreso : listaEgreso){
				egreso.setPersonaApoderado(devolverPersonaCargada(egreso.getIntPersPersonaGirado()));
			}
			
			//ordenamos
			Collections.sort(listaEgreso, new Comparator<Egreso>(){
				public int compare(Egreso uno, Egreso otro) {
					return otro.getStrNumeroEgreso().compareTo(uno.getStrNumeroEgreso());
				}
			});
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void buscarPersonaFiltro(){
		try{
			listaPersonaFiltro = new ArrayList<Persona>();
			Persona persona = null;
			
			if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				if(intTipoBusquedaPersona.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
					Natural natural = new Natural();
					natural.setStrNombres(strTextoPersonaFiltro);
					List<Natural> listaNatural = personaFacade.getListaNaturalPorBusqueda(natural);
					for(Natural natu : listaNatural){
						listaPersonaFiltro.add(personaFacade.getPersonaPorPK(natu.getIntIdPersona()));
					}
				}else if(intTipoBusquedaPersona.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
					persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
							Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
							strTextoPersonaFiltro, 
							EMPRESA_USUARIO);
					if(persona!=null)listaPersonaFiltro.add(persona);
				}
				
			}else if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				if(intTipoBusquedaPersona.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
					List<Juridica>listaJuridica = personaFacade.getListaJuridicaPorNombreComercial(strTextoPersonaFiltro);
					for(Juridica juridica : listaJuridica){
						listaPersonaFiltro.add(personaFacade.getPersonaPorPK(juridica.getIntIdPersona()));
					}
					
				}else if(intTipoBusquedaPersona.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)){
					persona = personaFacade.getPersonaPorRuc(strTextoPersonaFiltro);
					if(persona!=null)listaPersonaFiltro.add(persona);
				}
			}
			
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void abrirPopUpBuscarApoderado(){
		try{
			intTipoBuscarPersona = BUSCAR_APODERADO;
			listaPersona = new ArrayList<Persona>();
			strFiltroTextoPersona = "";
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
		seleccionaTelecredito = Boolean.FALSE;
	}
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	private LibroDiario obtenerLibroDiario(Egreso egreso) throws Exception{
		LibroDiarioId libroDiarioId = new LibroDiarioId();
		libroDiarioId.setIntPersEmpresaLibro(egreso.getIntPersEmpresaLibro());
		libroDiarioId.setIntContPeriodoLibro(egreso.getIntContPeriodoLibro());
		libroDiarioId.setIntContCodigoLibro(egreso.getIntContCodigoLibro());
		LibroDiario lDiario = libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);
		lDiario.setListaLibroDiarioDetalle(libroDiarioFacade.getListaLibroDiarioDetallePorLibroDiario(lDiario));
		return lDiario;
	}
	
	private Bancofondo obtenerBanco(Integer intItembancofondo)throws Exception{
		BancofondoId bancoFondoId = new BancofondoId();
		bancoFondoId.setIntEmpresaPk(EMPRESA_USUARIO);
		bancoFondoId.setIntItembancofondo(intItembancofondo);
		return bancoFacade.getBancoFondoPorId(bancoFondoId);
	}
	
	/**
	 * revisar error , no trae la cuenta bancaria
	 * @param egreso
	 * @return
	 * @throws Exception
	 */
	private CuentaBancaria obtenerCuentaBancariaOrigen(Egreso egreso)throws Exception{
//		List<CuentaBancaria> lstCuentaBancaria = null;
		CuentaBancaria ctaBank = new CuentaBancaria();
		//Autor jchavez / Tarea: Se regresa al tipo de dato integer y se graba la llave de la cuenta / Fecha: 19.09.2014
//		lstCuentaBancaria = personaFacade.getListaCuentaBancariaPorStrNumero(egreso.getStrPersCuentaBancariaGirado());
//		if (lstCuentaBancaria!=null && !lstCuentaBancaria.isEmpty()) {
//			for (CuentaBancaria cuentaBancaria : lstCuentaBancaria) {
//				if (egreso.getIntPersPersonaGirado().equals(cuentaBancaria.getId().getIntIdPersona())) {
//					ctaBank = cuentaBancaria;
//					break;
//				}
//			}			
//		}
		ctaBank.setId(new CuentaBancariaPK());
		ctaBank.getId().setIntIdPersona(egreso.getIntPersPersonaGirado());
		ctaBank.getId().setIntIdCuentaBancaria(egreso.getIntPersCuentaBancariaGirado());
		ctaBank = personaFacade.getCuentaBancariaPorPK(ctaBank.getId());
		if (ctaBank!=null) {
			blnVerPanelCuentaBancariaDestino=true;
		}
		return ctaBank;
		//Fin jchavez - 19.09-2014
		
//		CuentaBancariaPK cuentaBancariaId = new CuentaBancariaPK();
//		cuentaBancariaId.setIntIdPersona(egreso.getIntPersPersonaGirado());
//		cuentaBancariaId.setIntIdCuentaBancaria(egreso.getStrPersCuentaBancariaGirado());
//		cuentaBancariaId.setIntIdCuentaBancaria(59);
		
//		return personaFacade.getCuentaBancariaPorPK(cuentaBancariaId);
		
	}
	
	public void verRegistro(ActionEvent event){
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try{
			limpiarFormulario();
			
			Egreso egreso = (Egreso)event.getComponent().getAttributes().get("item");
			
			libroDiario = obtenerLibroDiario(egreso);
			intTipoDocumentoValidar = egreso.getIntParaDocumentoGeneral();
			seleccionaTelecredito = Boolean.FALSE;
			
			if(egreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
				//se trata de transferencia a terceros
				if(egreso.getIntNumeroTransferencia()!=null){
					verRegistroTransferenciaTerceros(egreso);
					
				//se trata de planilla de telecredito
				}else if(egreso.getIntNumeroPlanilla()!=null){
					seleccionaTelecredito = Boolean.TRUE;
					verRegistroTransferenciaTelecredito(egreso);
				}
			}else if(egreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS)){
				verRegistroTransferenciaEntreCuentas(egreso);
			}
			

			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			datosValidados = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
		}catch (Exception e) {
			mensaje = "Ocurrio un error en la selección de egreso.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	private void verRegistroTransferenciaTelecredito(Egreso egreso)throws Exception{
		egresoNuevo = egreso;
		LibroDiarioDetalle libroDiarioDetalleHaber = null;
		LibroDiarioDetalle libroDiarioDetalleDebe = null;
		for(LibroDiarioDetalle libroDiarioDetalle : libroDiario.getListaLibroDiarioDetalle()){
			log.info(libroDiarioDetalle);
			if(libroDiarioDetalle.getBdHaberSoles()!=null){
				libroDiarioDetalleHaber = libroDiarioDetalle;
			}else{
				libroDiarioDetalleHaber = libroDiarioDetalle;
			}
		}
		
		obtenerFondoTelecredito();
		BancofondoId bancoFondoDestinoId = new BancofondoId();
		bancoFondoDestinoId.setIntEmpresaPk(egreso.getId().getIntPersEmpresaEgreso());
		bancoFondoDestinoId.setIntItembancofondo(egreso.getIntPersPersonaGirado());
		Bancofondo bancoFondo = bancoFacade.getBancoFondoPorEmpresayPersonaBanco(
																egreso.getIntPersEmpresaGirado(), egreso.getIntPersPersonaGirado()).get(0);
		intBancoSeleccionadoOrigen = bancoFondo.getIntBancoCod();
		log.info(egreso.getListaEgresoDetalle().get(0)); 
		log.info(libroDiarioDetalleHaber);
		log.info(libroDiarioDetalleDebe);
		
		seleccionarBancoOrigen();
		for(Bancocuenta bancoCuenta : listaBancoCuentaOrigen){
			log.info(bancoCuenta);
			if(bancoCuenta.getIntEmpresacuentaPk().equals(libroDiarioDetalleHaber.getIntPersEmpresaCuenta())
			&& bancoCuenta.getIntPeriodocuenta().equals(libroDiarioDetalleHaber.getIntContPeriodo())
			&& bancoCuenta.getStrNumerocuenta().equals(libroDiarioDetalleHaber.getStrContNumeroCuenta())){
				intBancoCuentaSeleccionadoOrigen = bancoCuenta.getId().getIntItembancocuenta();
			}
		}
		
		intNumeroTransferencia = egreso.getIntNumeroPlanilla();
		bdMontoGirar = egreso.getBdMontoTotal();
		strObservacion = egreso.getStrObservacion();
		
		EgresoDetalle egresoDetalle = egreso.getListaEgresoDetalle().get(0);		
		log.info(egresoDetalle);
		ControlFondosFijos controlFondosFijos = egresoFacade.obtenerControlFondosFijosPorEgresoDetalle(egresoDetalle);
		log.info(controlFondosFijos);
		listaEgresoAgregado = egresoFacade.obtenerListaEgresoPorControlFondosFijos(controlFondosFijos);
		
		bdMontoGirar = new BigDecimal(0);
		for(Egreso egresoAgregado : listaEgresoAgregado){
			egresoAgregado.setPersonaApoderado(personaFacade.devolverPersonaCargada(egresoAgregado.getIntPersPersonaGirado()));
			egresoAgregado = egresoFacade.procesarItems(egresoAgregado);
			bdMontoGirar = bdMontoGirar.add(egresoAgregado.getBdMontoTotal());
		}
		strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, Constante.PARAM_T_TIPOMONEDA_SOLES);
	}
	
	private void verRegistroTransferenciaEntreCuentas(Egreso egreso)throws Exception{
		egresoNuevo = egreso;
		EgresoDetalle egresoDetalle = egreso.getListaEgresoDetalle().get(0);
		
		LibroDiarioDetalle libroDiarioDetalleDebe = null;
		LibroDiarioDetalle libroDiarioDetalleHaber = null;
		for(LibroDiarioDetalle libroDiarioDetalle : libroDiario.getListaLibroDiarioDetalle()){
			log.info(libroDiarioDetalle);
			if(libroDiarioDetalle.getBdDebeSoles()!=null){
				libroDiarioDetalleDebe = libroDiarioDetalle;
			}
			if(libroDiarioDetalle.getBdHaberSoles()!=null){
				libroDiarioDetalleHaber = libroDiarioDetalle;
			}
		}

		//Autor: jchavez / Tarea: Modificación / Fecha: 19.08.2014 /
		Bancofondo bancoOrigen = bancoFacade.obtenerBancoPorPlanCuenta(libroDiarioDetalleHaber);
		Bancofondo bancoDestino = bancoFacade.obtenerBancoPorPlanCuenta(libroDiarioDetalleDebe);
		log.info("libroDiarioDetalleDebe: "+libroDiarioDetalleDebe);
		log.info("libroDiarioDetalleHaber:"+libroDiarioDetalleHaber);
		
		log.info("bancoOrigen: "+bancoOrigen);
		log.info("bancoDestino:"+bancoDestino);
		
		log.info("1");intBancoSeleccionadoOrigen = bancoOrigen.getIntBancoCod();
		log.info("2");seleccionarBancoOrigen();
		log.info("3");intBancoCuentaSeleccionadoOrigen = bancoOrigen.getBancoCuentaSeleccionada().getId().getIntItembancocuenta();
		log.info("4");seleccionarBancoCuentaOrigen();
		log.info("bancoCuentaOrigen:"+bancoCuentaOrigen);
		log.info("5");intBancoSeleccionadoDestino = bancoDestino.getIntBancoCod();
		log.info("6");seleccionarBancoDestino();
		log.info("7");intBancoCuentaSeleccionadoDestino = bancoDestino.getBancoCuentaSeleccionada().getId().getIntItembancocuenta();
		
		
		log.info("intBancoCuentaSeleccionadoOrigen: "+intBancoCuentaSeleccionadoOrigen);
		log.info("intBancoCuentaSeleccionadoDestino:"+intBancoCuentaSeleccionadoDestino);
		intNumeroTransferencia = egreso.getIntNumeroTransferencia();
		bdMontoGirar = egreso.getBdMontoTotal();
		strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, egresoDetalle.getIntParaTipoMoneda());
		strObservacion = egreso.getStrObservacion();
	}
		
	private void verRegistroTransferenciaTerceros(Egreso egreso)throws Exception{
		String strTipoCuenta = "";
		String strTipoMoneda = "";
		try{			
			EgresoDetalle egresoDetalle = egreso.getListaEgresoDetalle().get(0);
			
			log.info(egreso);
			egresoNuevo = egreso;
			/*bdMontoGirar = egreso.getBdMontoTotal();
			strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, egresoDetalle.getIntParaTipoMoneda());
			*/strObservacion = egreso.getStrObservacion();
			personaSeleccionada = egreso.getPersonaApoderado();
			intTipoDocumentoValidar = egreso.getIntParaDocumentoGeneral();
			intNumeroTransferencia = egreso.getIntNumeroTransferencia();
			intTipoDocumentoAgregar = egresoDetalle.getIntParaDocumentoGeneral();
			Bancofondo bancoOrigen = obtenerBanco(egreso.getIntItemBancoFondo());
			intBancoSeleccionadoOrigen = bancoOrigen.getIntBancoCod();
			seleccionarBancoOrigen();
			intBancoCuentaSeleccionadoOrigen = egreso.getIntItemBancoCuenta();
			cuentaBancariaSeleccionada = obtenerCuentaBancariaOrigen(egreso);
			
			//Autor: jchavez / Tarea: Creacion / Fecha: 11.09.2014
			if (cuentaBancariaSeleccionada!=null && cuentaBancariaSeleccionada.getId().getIntIdCuentaBancaria()!=null && cuentaBancariaSeleccionada.getIntBancoCod().equals(Constante.PARAM_T_BANCO_BANCODECREDITO)) {
				for (Tabla ctaBca : listaTablaTipoCuentaBancaria) {
					if (ctaBca.getIntIdDetalle().equals(cuentaBancariaSeleccionada.getIntTipoCuentaCod())) {
						strTipoCuenta = "Tipo Cuenta: "+ctaBca.getStrDescripcion();
						break;
					}
				}
				for (Tabla tipMda : listaTablaTipoMoneda) {
					if (tipMda.getIntIdDetalle().equals(cuentaBancariaSeleccionada.getIntMonedaCod())) {
						strTipoMoneda = " - Moneda: "+tipMda.getStrDescripcion();
						break;
					}
				}
				cuentaBancariaSeleccionada.setStrEtiqueta(strTipoCuenta+strTipoMoneda+" - Nro. Cuenta: "+cuentaBancariaSeleccionada.getStrNroCuentaBancaria());
			}
			//Fin jchavez - 20.09.2014
			
			/**Para que se muestre correctamente el panel de la persona seleccionada**/
			intTipoPersona = personaSeleccionada.getIntTipoPersonaCod();
			strFiltroTextoPersona = personaSeleccionada.getDocumento().getStrNumeroIdentidad();
			listaPersona = new ArrayList<Persona>();
			buscarPersona();
			personaSeleccionada = (Persona)listaPersona.get(0);
			if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				agregarNombreCompleto(personaSeleccionada);
				agregarDocumentoDNI(personaSeleccionada);
			}			
			//filtrarListaTablaDocumentoGeneral();
			/****/			
			
			documentoGeneralSeleccionado = new DocumentoGeneral();
			documentoGeneralSeleccionado.setIntTipoDocumento(intTipoDocumentoAgregar);
			documentoGeneralSeleccionado.setBdMonto(new BigDecimal(0));
			if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				List<Movilidad> listaMovilidad = egresoFacade.obtenerListaMovilidadPorEgreso(egreso);
				for(Movilidad movilidad : listaMovilidad){
					documentoGeneralSeleccionado = new DocumentoGeneral();
					documentoGeneralSeleccionado.setIntTipoDocumento(intTipoDocumentoAgregar);
					documentoGeneralSeleccionado.setMovilidad(movilidad);
					documentoGeneralSeleccionado.setBdMonto(new BigDecimal(0));
					agregarDocumento();
				}
				
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				ExpedienteCredito expedienteCredito = prestamoFacade.obtenerExpedientePorEgreso(egreso);
				documentoGeneralSeleccionado.setExpedienteCredito(expedienteCredito);
				if(egreso.getIntPersPersonaApoderado()!=null){
					personaApoderado = devolverPersonaCargada(egreso.getIntPersPersonaApoderado());
					archivoCartaPoder = egresoFacade.getArchivoPorEgreso(egreso);
				}				
				agregarDocumento();
			
				
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				
				BeneficiarioPrevision beneficiarioPrevision = previsionFacade.getBeneficiarioPrevisionPorEgreso(egreso);
				ExpedientePrevision expedientePrevision = previsionFacade.getExpedientePrevisionPorBeneficiarioPrevision(beneficiarioPrevision);
				documentoGeneralSeleccionado.setExpedientePrevision(expedientePrevision);
				intBeneficiarioSeleccionar = beneficiarioPrevision.getId().getIntItemBeneficiario();
				cargarListaBeneficiarioPrevision();
				if(egreso.getIntPersPersonaApoderado()!=null){
					personaApoderado = devolverPersonaCargada(egreso.getIntPersPersonaApoderado());
					archivoCartaPoder = egresoFacade.getArchivoPorEgreso(egreso);
				}
				agregarDocumento();
				
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				List<BeneficiarioLiquidacion> listaBeneficiarioliquidacion = liquidacionFacade.getListaBeneficiarioLiquidacionPorEgreso(egreso);
				BeneficiarioLiquidacion beneficiarioLiquidacion = listaBeneficiarioliquidacion.get(0);
				ExpedienteLiquidacion expedienteLiquidacion = liquidacionFacade.getExpedienteLiquidacionPorBeneficiario(beneficiarioLiquidacion);
				expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(liquidacionFacade.getListaExpedienteLiquidacionDetallePorExpediente(expedienteLiquidacion));
				documentoGeneralSeleccionado.setExpedienteLiquidacion(expedienteLiquidacion);
				intBeneficiarioSeleccionar = beneficiarioLiquidacion.getIntPersPersonaBeneficiario();
				cargarListaBeneficiarioLiquidacion();
				
				agregarDocumento();
			}
			
			bdMontoGirar = egreso.getBdMontoTotal();
			strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, egresoDetalle.getIntParaTipoMoneda());
			
			documentoGeneralSeleccionado = new DocumentoGeneral();
			documentoGeneralSeleccionado.setIntTipoDocumento(intTipoDocumentoAgregar);			
		}catch (Exception e) {
			throw e;
		}
	}
	
	public void validarDatos(){
		String mensaje = "";
		strMensajeErrorPorBeneficiario = "";
		blnBeneficiarioSinAutorizacion = false;
		blnVerPanelCuentaBancariaDestino = true;
		blnVerPanelCuentaBancariaBeneficiario  = false;
		try{
			log.info("intTipoDocumentoValidar:"+intTipoDocumentoValidar);
			datosValidados = Boolean.FALSE;
			habilitarGrabar = Boolean.FALSE;
			fondoTelecredito = null;
			
			if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){				
				if(seleccionaTelecredito){
					fondoTelecredito = obtenerFondoTelecredito();
					if(fondoTelecredito==null){
						mensaje = "Ocurre un problema con el fondo de telecredito. Puede que no esteconfigurado correctamente";
						return;
					}
//					log.info(fondoTelecredito);
//					log.info(fondoTelecredito.getFondoDetalleUsar());
//					log.info(fondoTelecredito.getFondoDetalleUsar().getPlanCuenta());
				}
			}
			
			egresoNuevo = new Egreso();
			egresoNuevo.setDtFechaEgreso(obtenerFechaActual());
			
			listaEgresoAgregado = new ArrayList<Egreso>();
			datosValidados = Boolean.TRUE;
			habilitarGrabar = Boolean.TRUE;
			
			//limpieza de combos
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(datosValidados){
				ocultarMensaje();
			}else{
				mostrarMensaje(datosValidados, mensaje);
			}
		}
	}
	
	public void seleccionarBancoOrigen(){
		try{
			log.info("--seleccionarBancoOrigen");
			listaBancoCuentaOrigen = seleccionarBanco(intBancoSeleccionadoOrigen);
			for(Bancocuenta bancocuenta : listaBancoCuentaOrigen){
				log.info(bancocuenta);
			}
			//listaBancoCuentaDestino solo puede tener BancoCuenta's con moneda del bancocuenta seleccionado
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarBancoCuentaOrigen(){
		try{
			log.info("--seleccionarBancoCuentaOrigen");
			bancoCuentaOrigen = obtenerBancoCuentaSeleccionadoOrigen();
			log.info("bancoCuentaOrigen:"+bancoCuentaOrigen);
			
			if(egresoNuevo.getId().getIntItemEgresoGeneral()==null){
				//listaBancoCuentaDestino solo puede mostrar BC's con el mismto tipo del BCOrigen seleccionado
				seleccionarBancoDestino();
				if(listaBancoCuentaDestino!=null && !listaBancoCuentaDestino.isEmpty()){
					List<Bancocuenta> listaTemp = new ArrayList<Bancocuenta>();
					for(Object o : listaBancoCuentaDestino){
						Bancocuenta bancoCuentaDestino = (Bancocuenta)o;
						if(bancoCuentaDestino.getCuentaBancaria().getIntMonedaCod().equals(bancoCuentaOrigen.getCuentaBancaria().getIntMonedaCod())){
							listaTemp.add(bancoCuentaDestino);
						}
					}
					listaBancoCuentaDestino = listaTemp;
				}
			}			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarBancoDestino(){
		try{
			log.info("--seleccionarBancoDestino");
			listaBancoCuentaDestino = seleccionarBanco(intBancoSeleccionadoDestino);
			
			if(egresoNuevo.getId().getIntItemEgresoGeneral()==null){
				//listaBancoCuentaDestino solo puede mostrar BC's con el mismto tipo del BCOrigen seleccionado
				if(!intBancoSeleccionadoOrigen.equals(new Integer(0)) && !intBancoCuentaSeleccionadoOrigen.equals(new Integer(0))){
					if(listaBancoCuentaDestino!=null && !listaBancoCuentaDestino.isEmpty()){
						List<Bancocuenta> listaTemp = new ArrayList<Bancocuenta>();
						for(Object o : listaBancoCuentaDestino){
							Bancocuenta bancoCuentaDestino = (Bancocuenta)o;
							if(bancoCuentaDestino.getCuentaBancaria().getIntMonedaCod().equals(bancoCuentaOrigen.getCuentaBancaria().getIntMonedaCod())){
								listaTemp.add(bancoCuentaDestino);
							}
						}
						listaBancoCuentaDestino = listaTemp;
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public List<Bancocuenta> seleccionarBanco(Integer intBancoSeleccionado) throws Exception{
			//log.info("--seleccionarBanco");
			if (intBancoSeleccionado==null) intBancoSeleccionado=0;
				
			List<Bancocuenta> listaBancoCuenta = new ArrayList<Bancocuenta>();
			if(intBancoSeleccionado.equals(new Integer(0))){
				return listaBancoCuenta;
			}
			Bancofondo bancoFondoTemp = new Bancofondo();
			bancoFondoTemp.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_BANCO);
			bancoFondoTemp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			List<Bancofondo> listaBancoFondoTemp = bancoFacade.buscarBancoFondo(bancoFondoTemp);
			String strEtiqueta = "";
			//log.info("--intBancoSeleccionado:"+intBancoSeleccionado);
			for(Bancofondo bancoFondo : listaBancoFondoTemp){
				//log.info(bancoFondo);
				if(bancoFondo.getIntBancoCod().equals(intBancoSeleccionado)){
					for(Bancocuenta bancoCuenta : bancoFondo.getListaBancocuenta()){
						//log.info(bancoCuenta);
						strEtiqueta = bancoCuenta.getStrNombrecuenta()+" - "
										+bancoCuenta.getCuentaBancaria().getStrNroCuentaBancaria()+" - "
										+obtenerEtiquetaTipoMoneda(bancoCuenta.getCuentaBancaria().getIntMonedaCod());
						bancoCuenta.setStrEtiqueta(strEtiqueta);
						listaBancoCuenta.add(bancoCuenta);
					}
				}
			}
			return listaBancoCuenta;
	}
	
	private String obtenerEtiquetaTipoMoneda(Integer intTipoMoneda){
		for(Tabla tabla : listaMoneda){
			if(tabla.getIntIdDetalle().equals(intTipoMoneda)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	private String obtenerEtiquetaTipoDocumentoGeneral(Integer intTipoDocumento){
		for(Tabla tabla : listaTablaDocumentoGeneral){
			if(tabla.getIntIdDetalle().equals(intTipoDocumento)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	private String obtenerEtiquetaTipoCredito(Integer intTipoCredito){
		for(Tabla tabla : listaTablaTipoCredito){
			if(tabla.getIntIdDetalle().equals(intTipoCredito)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	private Integer	obtenerPeriodoActual(){
		String strPeriodo = "";
		Calendar cal=Calendar.getInstance();
		int año = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH);
		mes = mes + 1; 
		if(mes<10){
			strPeriodo = año + "0" + mes;
		}else{
			strPeriodo  = año + "" + mes;
		}		
		return Integer.parseInt(strPeriodo);		
	}
	
	private Bancocuenta obtenerBancoCuentaSeleccionadoOrigen(){
		if(seleccionaTelecredito){
			
		}else{
			log.info("intBancoCuentaSeleccionadoOrigen:"+intBancoCuentaSeleccionadoOrigen);
			for(Bancocuenta bancoCuenta : listaBancoCuentaOrigen){
				log.info(bancoCuenta);
				if(bancoCuenta.getId().getIntItembancocuenta().equals(intBancoCuentaSeleccionadoOrigen)){
					log.info("match!");
					return bancoCuenta;
				}
			}
		}		
		return null;
	}
	
	private Bancocuenta obtenerBancoCuentaSeleccionadoDestino(){
		if(seleccionaTelecredito){
			for(Bancocuenta bancoCuenta : listaBancoCuentaOrigen){
				if(bancoCuenta.getId().getIntItembancocuenta().equals(intBancoCuentaSeleccionadoOrigen)){
					return bancoCuenta;
				}
			}
		}else{
			for(Bancocuenta bancoCuenta : listaBancoCuentaDestino){
				if(bancoCuenta.getId().getIntItembancocuenta().equals(intBancoCuentaSeleccionadoDestino)){
					return bancoCuenta;
				}
			}
		}		
		return null;
	}
	
	private PlanCuenta obtenerPlanCuenta(Bancocuenta bancoCuenta) throws Exception{
		PlanCuenta planCuenta = new PlanCuenta();
		planCuenta.setId(new PlanCuentaId());
		planCuenta.getId().setIntEmpresaCuentaPk(bancoCuenta.getIntEmpresacuentaPk());
		planCuenta.getId().setIntPeriodoCuenta(bancoCuenta.getIntPeriodocuenta());
		planCuenta.getId().setStrNumeroCuenta(bancoCuenta.getStrNumerocuenta());
		
		planCuenta = planCuentaFacade.getPlanCuentaPorPk(planCuenta.getId());
		return planCuenta;		
	}
	
	private Bancofondo obtenerFondoTelecredito(){
		try{
			fondoTelecredito = new Bancofondo();
			fondoTelecredito.getId().setIntEmpresaPk(EMPRESA_USUARIO);
			fondoTelecredito.setIntTipoFondoFijo(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO);
			fondoTelecredito.setIntMonedaCod(Constante.PARAM_T_TIPOMONEDA_SOLES);
			fondoTelecredito = bancoFacade.getBancoFondoPorTipoFondoFijoYMoneda(fondoTelecredito);
			fondoTelecredito.setListaFondodetalle(bancoFacade.getListaFondoDetallePorBancoFondo(fondoTelecredito));
			fondoTelecredito.setFondoDetalleUsar(bancoFacade.obtenerFondoDetalleContable(fondoTelecredito, 
												usuario.getSucursal(), usuario.getSubSucursal()));
			
			
			return fondoTelecredito;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}
	
	private Bancofondo obtenerBancoFondoOrigen(Bancocuenta bancoCuenta)throws Exception{
		if(seleccionaTelecredito){
			return fondoTelecredito;
		}else{
			return bancoFacade.getBancoFondoPorBancoCuenta(bancoCuentaOrigen);
		}
	}
	
	private CuentaBancaria obtenerCuentaBancariaOrigen(Bancocuenta bancoCuenta)throws Exception{
		if(seleccionaTelecredito){
			return null;
		}else{
			return bancoCuenta.getCuentaBancaria();
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void procesarTransferencia() throws Exception{
		try {
			if(!seleccionaTelecredito){
				bancoCuentaOrigen = obtenerBancoCuentaSeleccionadoOrigen();
			}		
			bancoCuentaDestino = obtenerBancoCuentaSeleccionadoDestino();
			Bancofondo bancoFondoOrigen = obtenerBancoFondoOrigen(bancoCuentaOrigen);
			Bancofondo bancoFondoDestino = bancoFacade.getBancoFondoPorBancoCuenta(bancoCuentaDestino);
			CuentaBancaria cuentaBancariaOrigen = obtenerCuentaBancariaOrigen(bancoCuentaOrigen);
			CuentaBancaria cuentaBancariaDestino = bancoCuentaDestino.getCuentaBancaria();
			PlanCuenta planCuentaOrigen = null;
			PlanCuenta planCuentaDestino = null;		
			if(seleccionaTelecredito){
				planCuentaOrigen = fondoTelecredito.getFondoDetalleUsar().getPlanCuenta();
				planCuentaDestino = obtenerPlanCuenta(bancoCuentaDestino);
			}else{
				planCuentaOrigen = obtenerPlanCuenta(bancoCuentaOrigen);
				planCuentaDestino = obtenerPlanCuenta(bancoCuentaDestino);
			}
						
			intTipoSubOperacion = intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)?Constante.PARAM_T_TIPODESUBOPERACION_TRANSFATERCER:Constante.PARAM_T_TIPODESUBOPERACION_TRANSFENRTCTA;
			log.info("Subtipo Operacion: "+intTipoSubOperacion);
			log.info(bancoCuentaOrigen);
			log.info(bancoCuentaDestino);
			
			log.info(bancoFondoOrigen);
			log.info(bancoFondoDestino);
			
			log.info(cuentaBancariaOrigen);
			log.info(cuentaBancariaDestino);
			
			log.info(planCuentaOrigen);
			log.info(planCuentaDestino);
			
			egresoNuevo = new Egreso();
			// CGD-12.10.2013
			egresoNuevo.setId(new EgresoId());
			egresoNuevo.setListaEgresoDetalle(new ArrayList<EgresoDetalle>());
			
			egresoNuevo.getId().setIntPersEmpresaEgreso(EMPRESA_USUARIO);
			//JCHAVEZ 21.12.2013
			egresoNuevo.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_INGRESOEGRESO);
//			egresoNuevo.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egresoNuevo.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_TRANSFERENCIA);
			egresoNuevo.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			egresoNuevo.setIntItemPeriodoEgreso(obtenerPeriodoActual());
			egresoNuevo.setIntItemEgreso(null);
			egresoNuevo.setIntSucuIdSucursal(sucursalUsuario.getId().getIntIdSucursal());
			egresoNuevo.setIntSudeIdSubsucursal(subsucursalUsuario.getId().getIntIdSubSucursal());
			//JCHAVEZ 21.12.2013
			log.info(Constante.PARAM_T_TIPODESUBOPERACION_TRANSFATERCER);
//			egresoNuevo.setIntParaSubTipoOperacion();
			egresoNuevo.setIntParaSubTipoOperacion(intTipoSubOperacion);
			egresoNuevo.setTsFechaProceso(obtenerFechaActual());
			egresoNuevo.setDtFechaEgreso(obtenerFechaActual());
			egresoNuevo.setIntParaTipoFondoFijo(null);
			egresoNuevo.setIntItemPeriodoFondo(null);
			egresoNuevo.setIntItemFondoFijo(null);
			egresoNuevo.setIntItemBancoFondo(bancoFondoOrigen.getId().getIntItembancofondo());
			//Autor: jchavez / Tarea: se agrega validacion cuando es terceros telecrédito / Fecha: 26.09.2014
			if(!seleccionaTelecredito){
				egresoNuevo.setIntItemBancoCuenta(bancoCuentaOrigen.getId().getIntItembancocuenta());
			}else {
				egresoNuevo.setIntItemBancoCuenta(intBancoCuentaSeleccionadoOrigen);
			}
			egresoNuevo.setIntItemBancoCuentaCheque(null);		
			if(seleccionaTelecredito) {
				egresoNuevo.setIntNumeroPlanilla(intNumeroTransferencia);
			} else{
				egresoNuevo.setIntNumeroTransferencia(intNumeroTransferencia);
			}				
			
			egresoNuevo.setIntNumeroCheque(null);
			egresoNuevo.setTsFechaPagoDiferido(null);
			egresoNuevo.setIntPersEmpresaGirado(bancoFondoDestino.getId().getIntEmpresaPk());
			egresoNuevo.setIntPersPersonaGirado(bancoFondoDestino.getIntPersonabancoPk());
			egresoNuevo.setIntCuentaGirado(null);
			//jchavez 20.05.2014 cambio de tipo de dato
			//Autor jchavez / Tarea: Se regresa al tipo de dato integer y se graba la llave de la cuenta / Fecha: 19.09.2014
//			egresoNuevo.setStrPersCuentaBancariaGirado(null);
			egresoNuevo.setIntPersCuentaBancariaGirado(null);
			//Fin jchavez - 19.09.2014
			egresoNuevo.setIntPersEmpresaBeneficiario(null);
			egresoNuevo.setIntPersPersonaBeneficiario(null);
			egresoNuevo.setIntPersCuentaBancariaBeneficiario(null);
			egresoNuevo.setIntPersPersonaApoderado(null);
			egresoNuevo.setIntPersEmpresaApoderado(null);		
			egresoNuevo.setBdMontoTotal(bdMontoGirar);
			egresoNuevo.setStrObservacion(strObservacion);
			egresoNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egresoNuevo.setTsFechaRegistro(obtenerFechaActual());
			egresoNuevo.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			egresoNuevo.setIntPersPersonaUsuario(PERSONA_USUARIO);
			
			EgresoDetalle egresoDetalle = new EgresoDetalle();
			// CGD-12.10.2013
			egresoDetalle.setId(new EgresoDetalleId());
			egresoDetalle.getId().setIntPersEmpresaEgreso(EMPRESA_USUARIO);
			//JCHAVEZ 21.12.2013
			//Transferencia a terceros
			if (intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)){
				if (seleccionaTelecredito) 
					egresoDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLATELECREDITO);
				else
					egresoDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS);
				
				egresoDetalle.setStrNumeroDocumento(planCuentaOrigen.getId().getStrNumeroCuenta());
				egresoDetalle.setStrDescripcionEgreso(planCuentaOrigen.getStrDescripcion());
				egresoDetalle.setIntPersEmpresaCuenta(planCuentaOrigen.getId().getIntEmpresaCuentaPk());
				egresoDetalle.setIntContPeriodoCuenta(planCuentaOrigen.getId().getIntPeriodoCuenta());
				egresoDetalle.setStrContNumeroCuenta(planCuentaOrigen.getId().getStrNumeroCuenta());
			}
			//Transferencia entre cuentas 
			if (intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS)){
				egresoDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS);
				egresoDetalle.setStrNumeroDocumento(planCuentaDestino.getId().getStrNumeroCuenta());
				egresoDetalle.setStrDescripcionEgreso(planCuentaDestino.getStrDescripcion());
				egresoDetalle.setIntPersEmpresaCuenta(planCuentaDestino.getId().getIntEmpresaCuentaPk());
				egresoDetalle.setIntContPeriodoCuenta(planCuentaDestino.getId().getIntPeriodoCuenta());
				egresoDetalle.setStrContNumeroCuenta(planCuentaDestino.getId().getStrNumeroCuenta());			
			}
//			egresoDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLATELECREDITO); 
			egresoDetalle.setIntParaTipoComprobante(null);
			egresoDetalle.setStrSerieDocumento(null);


			egresoDetalle.setIntPersEmpresaGirado(bancoFondoOrigen.getId().getIntEmpresaPk());
			//Autor: jchavez / Tarea: Modificacion / Fecha:30.09.2014
			if(seleccionaTelecredito)
				egresoDetalle.setIntPersonaGirado(Constante.PERSONA_SEDECENTRAL);
			else
				egresoDetalle.setIntPersonaGirado(bancoFondoDestino.getIntPersonabancoPk());
//				egresoDetalle.setIntPersonaGirado(bancoFondoOrigen.getIntPersonabancoPk());
			//Fin jchavez - 30.09.2014
			egresoDetalle.setIntCuentaGirado(null);
			egresoDetalle.setIntSucuIdSucursalEgreso(sucursalUsuario.getId().getIntIdSucursal());
			egresoDetalle.setIntSudeIdSubsucursalEgreso(subsucursalUsuario.getId().getIntIdSubSucursal());
			egresoDetalle.setIntParaTipoMoneda(cuentaBancariaDestino.getIntMonedaCod());
			egresoDetalle.setBdMontoDiferencial(null);
			egresoDetalle.setBdMontoCargo(bdMontoGirar);
			egresoDetalle.setBdMontoAbono(null);
			egresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egresoDetalle.setTsFechaRegistro(obtenerFechaActual());
			egresoDetalle.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			egresoDetalle.setIntPersPersonaUsuario(PERSONA_USUARIO);
			egresoDetalle.setIntPersEmpresaLibroDestino(null);
			egresoDetalle.setIntContPeriodoLibroDestino(null);
			egresoDetalle.setIntContCodigoLibroDestino(null);

			egresoDetalle.setIntParaTipoFondoFijo(null);
			egresoDetalle.setIntItemPeriodoFondo(null);
			egresoDetalle.setIntSucuIdSucursal(null);
			egresoDetalle.setIntItemFondoFijo(null);	
//			egresoDetalle.setId(new EgresoDetalleId());
//			egresoDetalle.getId().setIntPersEmpresaEgreso(EMPRESA_USUARIO);
//			//JCHAVEZ 21.12.2013
//			egresoDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLATELECREDITO); 
////			egresoDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS);
//			egresoDetalle.setIntParaTipoComprobante(null);
//			egresoDetalle.setStrSerieDocumento(null);
//			egresoDetalle.setStrNumeroDocumento(cuentaBancariaDestino.getStrNroCuentaBancaria());
//			egresoDetalle.setStrDescripcionEgreso(planCuentaDestino.getStrDescripcion());
//			egresoDetalle.setIntPersEmpresaGirado(bancoFondoDestino.getId().getIntEmpresaPk());
//			egresoDetalle.setIntPersonaGirado(bancoFondoDestino.getIntPersonabancoPk());
//			egresoDetalle.setIntCuentaGirado(null);
//			egresoDetalle.setIntSucuIdSucursalEgreso(sucursalUsuario.getId().getIntIdSucursal());
//			egresoDetalle.setIntSudeIdSubsucursalEgreso(subsucursalUsuario.getId().getIntIdSubSucursal());
//			egresoDetalle.setIntParaTipoMoneda(cuentaBancariaDestino.getIntMonedaCod());
//			egresoDetalle.setBdMontoDiferencial(null);
//			egresoDetalle.setBdMontoCargo(null);
//			egresoDetalle.setBdMontoAbono(bdMontoGirar);
//			egresoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//			egresoDetalle.setTsFechaRegistro(obtenerFechaActual());
//			egresoDetalle.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
//			egresoDetalle.setIntPersPersonaUsuario(PERSONA_USUARIO);
//			egresoDetalle.setIntPersEmpresaLibroDestino(null);
//			egresoDetalle.setIntContPeriodoLibroDestino(null);
//			egresoDetalle.setIntContCodigoLibroDestino(null);
//			egresoDetalle.setIntPersEmpresaCuenta(planCuentaDestino.getId().getIntEmpresaCuentaPk());
//			egresoDetalle.setIntContPeriodoCuenta(planCuentaDestino.getId().getIntPeriodoCuenta());
//			egresoDetalle.setStrContNumeroCuenta(planCuentaDestino.getId().getStrNumeroCuenta());
//			egresoDetalle.setIntParaTipoFondoFijo(null);
//			egresoDetalle.setIntItemPeriodoFondo(null);
//			egresoDetalle.setIntSucuIdSucursal(null);
//			egresoDetalle.setIntItemFondoFijo(null);		
			
			
			LibroDiario libroDiario = new LibroDiario();	
			// CGD-12.10.2013
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(EMPRESA_USUARIO);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			//JCHAVEZ 23.12.2013
			libroDiario.setIntParaTipoDocumentoGeneral(intTipoDocumentoValidar);
//			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS);
			libroDiario.setStrGlosa(strObservacion);
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			libroDiario.setIntPersPersonaUsuario(PERSONA_USUARIO);
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);		
			
			
			LibroDiarioDetalle libroDiarioDetalleEgreso = new LibroDiarioDetalle();
			// CGD-12.10.2013
			libroDiarioDetalleEgreso.setId(new LibroDiarioDetalleId());
			libroDiarioDetalleEgreso.getId().setIntPersEmpresaLibro(EMPRESA_USUARIO);
			libroDiarioDetalleEgreso.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioDetalleEgreso.setIntPersEmpresaCuenta(planCuentaOrigen.getId().getIntEmpresaCuentaPk());
			libroDiarioDetalleEgreso.setIntContPeriodo(planCuentaOrigen.getId().getIntPeriodoCuenta());
			libroDiarioDetalleEgreso.setStrContNumeroCuenta(planCuentaOrigen.getId().getStrNumeroCuenta());
			if(seleccionaTelecredito)
				libroDiarioDetalleEgreso.setIntPersPersona(Constante.PERSONA_SEDECENTRAL);
			else
				libroDiarioDetalleEgreso.setIntPersPersona(bancoFondoOrigen.getIntPersonabancoPk());
			
			//JCHAVEZ 23.12.2013
			log.info("cadena real: "+planCuentaOrigen.getStrDescripcion());
			log.info("cadena partida: "+(planCuentaOrigen.getStrDescripcion().length()<=20?planCuentaOrigen.getStrDescripcion():planCuentaOrigen.getStrDescripcion().substring(0, 19)));
			libroDiarioDetalleEgreso.setStrComentario(planCuentaOrigen.getStrDescripcion().length()<=20?planCuentaOrigen.getStrDescripcion():planCuentaOrigen.getStrDescripcion().substring(0, 19));
			//JCHAVEZ 26.12.2013
			if(seleccionaTelecredito)
				libroDiarioDetalleEgreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLATELECREDITO);
			else
				libroDiarioDetalleEgreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			
//			libroDiarioDetalleEgreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
			libroDiarioDetalleEgreso.setStrSerieDocumento(null);
			libroDiarioDetalleEgreso.setStrNumeroDocumento(null);
			libroDiarioDetalleEgreso.setIntPersEmpresaSucursal(sucursalUsuario.getId().getIntPersEmpresaPk());
			libroDiarioDetalleEgreso.setIntSucuIdSucursal(sucursalUsuario.getId().getIntIdSucursal());
			libroDiarioDetalleEgreso.setIntSudeIdSubSucursal(subsucursalUsuario.getId().getIntIdSubSucursal());
			libroDiarioDetalleEgreso.setIntParaMonedaDocumento(cuentaBancariaDestino.getIntMonedaCod());
			if (intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)) {
				if(cuentaBancariaDestino.getIntMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
					libroDiarioDetalleEgreso.setBdHaberSoles(null);
					libroDiarioDetalleEgreso.setBdDebeSoles(bdMontoGirar);			
				}else{
					libroDiarioDetalleEgreso.setBdHaberExtranjero(null);
					libroDiarioDetalleEgreso.setBdDebeExtranjero(bdMontoGirar);			
				}
			}
			if (intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS)) {
				if(cuentaBancariaDestino.getIntMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
					libroDiarioDetalleEgreso.setBdHaberSoles(bdMontoGirar);
					libroDiarioDetalleEgreso.setBdDebeSoles(null);			
				}else{
					libroDiarioDetalleEgreso.setBdHaberExtranjero(bdMontoGirar);
					libroDiarioDetalleEgreso.setBdDebeExtranjero(null);			
				}
			}
			
			
			LibroDiarioDetalle libroDiarioDetalleIngreso = new LibroDiarioDetalle();
			// CGD-12.10.2013
			libroDiarioDetalleIngreso.setId(new LibroDiarioDetalleId());
			libroDiarioDetalleIngreso.getId().setIntPersEmpresaLibro(EMPRESA_USUARIO);
			libroDiarioDetalleIngreso.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioDetalleIngreso.setIntPersEmpresaCuenta(planCuentaDestino.getId().getIntEmpresaCuentaPk());
			libroDiarioDetalleIngreso.setIntContPeriodo(planCuentaDestino.getId().getIntPeriodoCuenta());
			libroDiarioDetalleIngreso.setStrContNumeroCuenta(planCuentaDestino.getId().getStrNumeroCuenta());
			libroDiarioDetalleIngreso.setIntPersPersona(bancoFondoDestino.getIntPersonabancoPk());
			//JCHAVEZ 26.12.2013
			libroDiarioDetalleIngreso.setIntParaDocumentoGeneral(intTipoDocumentoValidar);
//			libroDiarioDetalleIngreso.setIntParaDocumentoGeneral((intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS) && seleccionaTelecredito)?Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLATELECREDITO:Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS);
			libroDiarioDetalleIngreso.setStrSerieDocumento(null);
			libroDiarioDetalleIngreso.setStrNumeroDocumento(null);
			libroDiarioDetalleIngreso.setIntPersEmpresaSucursal(sucursalUsuario.getId().getIntPersEmpresaPk());
			libroDiarioDetalleIngreso.setIntSucuIdSucursal(sucursalUsuario.getId().getIntIdSucursal());
			libroDiarioDetalleIngreso.setIntSudeIdSubSucursal(subsucursalUsuario.getId().getIntIdSubSucursal());
			libroDiarioDetalleIngreso.setIntParaMonedaDocumento(cuentaBancariaDestino.getIntMonedaCod());
			if (intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIATERCEROS)) {
				if(cuentaBancariaDestino.getIntMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
					libroDiarioDetalleIngreso.setBdHaberSoles(bdMontoGirar);
					libroDiarioDetalleIngreso.setBdDebeSoles(null);			
				}else{
					libroDiarioDetalleIngreso.setBdHaberExtranjero(bdMontoGirar);
					libroDiarioDetalleIngreso.setBdDebeExtranjero(null);
				}
			}
			
			if (intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_TRANSFERENCIAENTRECUENTAS)) {
				if(cuentaBancariaDestino.getIntMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
					libroDiarioDetalleIngreso.setBdHaberSoles(null);
					libroDiarioDetalleIngreso.setBdDebeSoles(bdMontoGirar);			
				}else{
					libroDiarioDetalleIngreso.setBdHaberExtranjero(null);
					libroDiarioDetalleIngreso.setBdDebeExtranjero(bdMontoGirar);
				}
			}

			//JCHAVEZ 23.12.2013
			log.info("cadena real: "+planCuentaDestino.getStrDescripcion());
			log.info("cadena partida: "+(planCuentaDestino.getStrDescripcion().length()<=20?planCuentaDestino.getStrDescripcion():planCuentaDestino.getStrDescripcion().substring(0, 19)));
			libroDiarioDetalleIngreso.setStrComentario(planCuentaDestino.getStrDescripcion().length()<=20?planCuentaDestino.getStrDescripcion():planCuentaDestino.getStrDescripcion().substring(0, 19));
			// CGD-12.10.2013
			libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleEgreso);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleIngreso);		
			
			egresoNuevo.setLibroDiario(libroDiario);
			egresoNuevo.getListaEgresoDetalle().add(egresoDetalle);
			
			if(seleccionaTelecredito){
				ControlFondosFijos controlFondosFijos = crearControlFondosFijos(); 
				egresoDetalle.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
				egresoDetalle.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
				egresoDetalle.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
				egresoDetalle.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
				
				egresoNuevo.setControlFondosFijos(controlFondosFijos);
			}		
		} catch (Exception e) {
			log.error("Error en procesarTransferencia ---> "+e);
		}
			
		
	}
	
	private ControlFondosFijos crearControlFondosFijos() throws Exception{
		ControlFondosFijos controlFondosFijosNuevo = new ControlFondosFijos();		
		controlFondosFijosNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
		controlFondosFijosNuevo.getId().setIntParaTipoFondoFijo(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO);
		controlFondosFijosNuevo.getId().setIntItemPeriodoFondo(obtenerPeriodoActual());
		controlFondosFijosNuevo.getId().setIntSucuIdSucursal(Constante.SUCURSAL_SEDECENTRAL);				
		controlFondosFijosNuevo.setIntSudeIdSubsucursal(Constante.SUBSUCURSAL_SEDE1);
		controlFondosFijosNuevo.setIntPersPersonaResponsable(Constante.PERSONA_SEDECENTRAL);
		controlFondosFijosNuevo.setIntEstadoFondo(Constante.PARAM_T_ESTADOFONDO_ABIERTO);
		controlFondosFijosNuevo.setTsFechaRegistro(obtenerFechaActual());
		controlFondosFijosNuevo.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
		controlFondosFijosNuevo.setIntPersPersonaUsuario(PERSONA_USUARIO);
		
		controlFondosFijosNuevo.setBdMontoGirado(bdMontoGirar);
		controlFondosFijosNuevo.setBdMontoApertura(bdMontoGirar);
		//JCHAVEZ 21.12.2013
		controlFondosFijosNuevo.setBdMontoUtilizado(bdMontoGirar);
		controlFondosFijosNuevo.setBdMontoSaldo(new BigDecimal(0));
		controlFondosFijosNuevo.setIntEstadoFondo(Constante.PARAM_T_ESTADOFONDO_CERRADO);
		controlFondosFijosNuevo.setTsFechaCierre(obtenerFechaActual());
		controlFondosFijosNuevo.setIntPersEmpresaCierre(EMPRESA_USUARIO);
		controlFondosFijosNuevo.setIntPersPersonaCierre(PERSONA_USUARIO);
//		controlFondosFijosNuevo.setBdMontoUtilizado(new BigDecimal(0));
//		controlFondosFijosNuevo.setBdMontoSaldo(bdMontoGirar);
		 
		return controlFondosFijosNuevo;
	}
	
	
	/**Transferencia a Terceros**/
	
	public void abrirPopUpBuscarPersona(){
		intTipoBuscarPersona = BUSCAR_PERSONA;
		listaPersona = new ArrayList<Persona>();
		strFiltroTextoPersona = "";
		intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
	}
	
	public void abrirPopUpBuscarEgreso(){
		try{
			egresoTelecreditoFiltro = new Egreso();
			egresoTelecreditoFiltro.getId().setIntPersEmpresaEgreso(EMPRESA_USUARIO);			
			listaEgresoPorAgregar = egresoFacade.buscarEgresoParaTelecredito(egresoTelecreditoFiltro);	
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarEgreso(){
		try{
			listaEgresoPorAgregar = egresoFacade.buscarEgresoParaTelecredito(egresoTelecreditoFiltro);	
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarSeleccionEgreso(){
		try{
			for(Egreso egreso : listaEgresoPorAgregar){
				if(egreso.getChecked()!=null && egreso.getChecked() && !estaEnListaEgresoAgregado(egreso)){
					listaEgresoAgregado.add(egreso);
				}
			}
			
			bdMontoGirar = new BigDecimal(0);
			for(Egreso egreso : listaEgresoAgregado){
				bdMontoGirar = bdMontoGirar.add(egreso.getBdMontoTotal());
			}
			strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, Constante.PARAM_T_TIPOMONEDA_SOLES);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private boolean estaEnListaEgresoAgregado(Egreso egreso){
		boolean estaEnLista = Boolean.FALSE;
		if(listaEgresoAgregado==null)
			listaEgresoAgregado = new ArrayList<Egreso>();
		
		for(Egreso egresoAgregado : listaEgresoAgregado){
			if(egresoAgregado.getId().getIntItemEgresoGeneral().equals(egreso.getId().getIntItemEgresoGeneral())){
				estaEnLista = Boolean.TRUE;
			}
		}
		return estaEnLista;
	}
	
	public void buscarPersona(){
		try{
			Persona persona = null;
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				log.info("strFiltroTextoPersona:"+strFiltroTextoPersona);
				persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
						Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
						strFiltroTextoPersona, 
						EMPRESA_USUARIO);
			
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				persona = personaFacade.getPersonaJuridicaYListaPersonaPorRucYIdEmpresa2(strFiltroTextoPersona,EMPRESA_USUARIO);
			}
			log.info(persona);
			
			/*if(persona!=null && persona.getIntIdPersona()!=null){
				boolean poseeRol = Boolean.FALSE;
				for(PersonaRol personaRol : persona.getPersonaEmpresa().getListaPersonaRol()){
					if(personaRol.getId().getIntParaRolPk().equals(intTipoRol)){
						poseeRol = Boolean.TRUE;
						break;
					}
				}
				if(poseeRol){
					listaPersona.add(persona);
				}
			}*/
			listaPersona.add(persona);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void seleccionarPersona(ActionEvent event){
		String strTipoCuenta = "";
		String strTipoMoneda = "";
		try{
			log.info("intTipoBuscarPersona:"+intTipoBuscarPersona);
			if(intTipoBuscarPersona.equals(BUSCAR_PERSONA)){
				personaSeleccionada = (Persona)event.getComponent().getAttributes().get("item");
				if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
					agregarNombreCompleto(personaSeleccionada);
					agregarDocumentoDNI(personaSeleccionada);
				}
				
				//filtrarListaTablaDocumentoGeneral();
			
			}else if(intTipoBuscarPersona.equals(BUSCAR_APODERADO)){
				personaApoderado = (Persona)event.getComponent().getAttributes().get("item");
				agregarNombreCompleto(personaApoderado);
				agregarDocumentoDNI(personaApoderado);
			}
			//Autor: jchavez / Tarea: Creacion / Fecha: 11.09.2014
			if (personaSeleccionada.getListaCuentaBancaria()!=null && !personaSeleccionada.getListaCuentaBancaria().isEmpty()) {
				List<CuentaBancaria> lstCtaBancaria = new ArrayList<CuentaBancaria>();
				for (CuentaBancaria x : personaSeleccionada.getListaCuentaBancaria()) {
					if (x.getIntBancoCod().equals(Constante.PARAM_T_BANCO_BANCODECREDITO)) {
						lstCtaBancaria.add(x);
						for (Tabla ctaBca : listaTablaTipoCuentaBancaria) {
							if (ctaBca.getIntIdDetalle().equals(x.getIntTipoCuentaCod())) {
								strTipoCuenta = "Tipo Cuenta: "+ctaBca.getStrDescripcion();
								break;
							}
						}
						for (Tabla tipMda : listaTablaTipoMoneda) {
							if (tipMda.getIntIdDetalle().equals(x.getIntMonedaCod())) {
								strTipoMoneda = " - Moneda: "+tipMda.getStrDescripcion();
								break;
							}
						}
						x.setStrEtiqueta(strTipoCuenta+strTipoMoneda+" - Nro. Cuenta: "+x.getStrNroCuentaBancaria());
					}
				}
				personaSeleccionada.getListaCuentaBancaria().clear();
				if (!lstCtaBancaria.isEmpty()) {
					personaSeleccionada.getListaCuentaBancaria().addAll(lstCtaBancaria);
				}
			}
			
			blnVerPanelCuentaBancariaDestino = true;
			blnVerPanelCuentaBancariaBeneficiario  = false;
			validarPersonaTitularFallecida();
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void cargarListaCuentaBancariaBeneficiario(){
		try {
			if (listaBeneficiarioPrevision!=null && !listaBeneficiarioPrevision.isEmpty()) {
				for (BeneficiarioPrevision benefPrev : listaBeneficiarioPrevision) {
					if (benefPrev.getId().getIntItemBeneficiario().equals(intBeneficiarioSeleccionar)) {
						beneficiarioSeleccionado = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
								Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
								benefPrev.getPersona().getDocumento().getStrNumeroIdentidad(), 
								EMPRESA_USUARIO);
					}
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	public void cargarListaCuentaBancariaBeneficiarioLiquidacion(){
		try {
			if (listaBeneficiarioPersona!=null && !listaBeneficiarioPersona.isEmpty()) {
				for (Persona benefLiq : listaBeneficiarioPersona) {
					if (benefLiq.getIntIdPersona().equals(intBeneficiarioSeleccionar)) {
						beneficiarioSeleccionado = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
								Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
								benefLiq.getDocumento().getStrNumeroIdentidad(), 
								EMPRESA_USUARIO);
					}
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void validarPersonaTitularFallecida(){
		//Estado 2, persona fallecida...
		if (personaSeleccionada.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO)) {
			blnVerPanelCuentaBancariaDestino = false;
			blnVerPanelCuentaBancariaBeneficiario  = true; 
		}
	}
	public void aceptarAdjuntarCartaPoder(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			archivoCartaPoder = fileUploadController.getArchivoCartaPoder(); 
		}catch(Exception e){
			log.error(e.getMessage(),e); 
		}
	}
	
	private void agregarNombreCompleto(Persona persona){
		persona.getNatural().setStrNombreCompleto(
				persona.getNatural().getStrNombres()+" "+
				persona.getNatural().getStrApellidoPaterno()+" "+
				persona.getNatural().getStrApellidoMaterno());
	}
	
	private void agregarDocumentoDNI(Persona persona){
		for(Documento documento : persona.getListaDocumento()){
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				persona.setDocumento(documento);
			}
		}
	}
	
	public void seleccionarCuentaBancaria(ActionEvent event){
		try{
			cuentaBancariaSeleccionada = (CuentaBancaria)event.getComponent().getAttributes().get("item");
			log.info(personaSeleccionada);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void seleccionarCuentaBancariaBeneficiario(ActionEvent event){
		try{
			cuentaBancariaBeneficiarioSeleccionada = (CuentaBancaria)event.getComponent().getAttributes().get("item");
			log.info(personaSeleccionada);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}

	
	public void abrirPopUpBuscarDocumento(){
		strMensajeError = "";
		try{
			listaDocumentoPorAgregar = new ArrayList<DocumentoGeneral>();
			Integer intIdPersona = personaSeleccionada.getIntIdPersona();
			
			log.info("intTipoDocumentoAgregar:"+intTipoDocumentoAgregar);
			
			if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				List<Movilidad> listaMovilidad = egresoFacade.buscarMovilidadPorIdPersona(intIdPersona, EMPRESA_USUARIO);
				for(Movilidad movilidad : listaMovilidad){
					//Autor: jchavez / Tarea: Se agrega validación solo listar pendientes de pago / Fecha: 26.09.2014
					if (movilidad.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_PENDIENTE)) {
						DocumentoGeneral documentoGeneral = new DocumentoGeneral();
						documentoGeneral.setIntTipoDocumento(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD);
						documentoGeneral.setStrNroDocumento(""+movilidad.getId().getIntItemMovilidad());
						documentoGeneral.setSucursal(movilidad.getSucursal());
						documentoGeneral.setSubsucursal(movilidad.getSubsucursal());
						documentoGeneral.setBdMonto(movilidad.getBdMontoAcumulado());
						
						documentoGeneral.setMovilidad(movilidad);
						listaDocumentoPorAgregar.add(documentoGeneral);
					}					
				}
			
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				List<ExpedienteCredito> listaExpedienteCredito = prestamoFacade.obtenerExpedientePorIdPersonayIdEmpresa(intIdPersona, EMPRESA_USUARIO);
				for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
					log.info(expedienteCredito);
					EstadoCredito estadoCredito = expedienteCredito.getListaEstadoCredito().get(0);
					DocumentoGeneral documentoGeneral = new DocumentoGeneral();
					documentoGeneral.setIntTipoDocumento(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
					String strNro = expedienteCredito.getId().getIntItemDetExpediente()
						+ "-" + estadoCredito.getId().getIntItemEstado()
						+ "-" + obtenerEtiquetaTipoCredito(expedienteCredito.getIntParaTipoCreditoCod());
					documentoGeneral.setStrNroDocumento(strNro);
					documentoGeneral.setSucursal(estadoCredito.getSucursal());
					documentoGeneral.setSubsucursal(estadoCredito.getSubsucursal());
					documentoGeneral.setBdMonto(expedienteCredito.getBdMontoTotal());
					
					documentoGeneral.setExpedienteCredito(expedienteCredito);
					listaDocumentoPorAgregar.add(documentoGeneral);
				}
				
//			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PAGOLEGALIZACIONLIBROS)){
//				List<PagoLegalizacion> listaPagoLegalizacion = legalizacionFacade.obtenerPagoLegalizacionPorPersona(intIdPersona, EMPRESA_USUARIO);
//				for(PagoLegalizacion pagoLegalizacion : listaPagoLegalizacion){
//					DocumentoGeneral documentoGeneral = new DocumentoGeneral();
//					documentoGeneral.setIntTipoDocumento(Constante.PARAM_T_DOCUMENTOGENERAL_PAGOLEGALIZACIONLIBROS);
//					documentoGeneral.setStrNroDocumento(""+pagoLegalizacion.getId().getIntItemPagoLegalizacion());
//					documentoGeneral.setSucursal(sucursalSedeCentral);
//					documentoGeneral.setSubsucursal(subsucursalSedeCentral);
//					documentoGeneral.setBdMonto(pagoLegalizacion.getBdMonto());					
//					
//					documentoGeneral.setPagoLegalizacion(pagoLegalizacion);
//					listaDocumentoPorAgregar.add(documentoGeneral);
//				}
			
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
					|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
					|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
					List<ExpedientePrevision> listaExpedientePrevision = previsionFacade.buscarExpedienteParaGiroDesdeFondo(intIdPersona, 
							EMPRESA_USUARIO, intTipoDocumentoAgregar);
					for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
						EstadoPrevision estadoPrevision = expedientePrevision.getEstadoPrevisionUltimo();
						if (estadoPrevision.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICITUD_APROBADO)) {
							DocumentoGeneral documentoGeneral = new DocumentoGeneral();
							documentoGeneral.setIntTipoDocumento(expedientePrevision.getIntParaDocumentoGeneral());
							String strNro = ""+expedientePrevision.getId().getIntItemExpediente();
							documentoGeneral.setStrNroDocumento(strNro);
							documentoGeneral.setSucursal(estadoPrevision.getSucursal());
							documentoGeneral.setSubsucursal(estadoPrevision.getSubsucursal());
							documentoGeneral.setBdMonto(expedientePrevision.getBdMontoBrutoBeneficio());
							
							documentoGeneral.setExpedientePrevision(expedientePrevision);
							listaDocumentoPorAgregar.add(documentoGeneral);
						}						
					}
					
				}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
					List<ExpedienteLiquidacion> listaExpedienteLiquidacion = liquidacionFacade.buscarExpedienteParaGiroDesdeFondo(intIdPersona, 
							EMPRESA_USUARIO);
					for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
						EstadoLiquidacion estadoLiquidacion = expedienteLiquidacion.getEstadoLiquidacionUltimo();
						if (estadoLiquidacion.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICITUD_APROBADO)) {
							DocumentoGeneral documentoGeneral = new DocumentoGeneral();
							documentoGeneral.setIntTipoDocumento(expedienteLiquidacion.getIntParaDocumentoGeneral());
							String strNro = ""+expedienteLiquidacion.getId().getIntItemExpediente();
							documentoGeneral.setStrNroDocumento(strNro);
							documentoGeneral.setSucursal(estadoLiquidacion.getSucursal());
							documentoGeneral.setSubsucursal(estadoLiquidacion.getSubsucursal());
							documentoGeneral.setBdMonto(expedienteLiquidacion.getBdMontoBrutoLiquidacion());
							
							documentoGeneral.setExpedienteLiquidacion(expedienteLiquidacion);
							listaDocumentoPorAgregar.add(documentoGeneral);
						}						
					}
				}//Autor: jchavez / Tarea: Creación / Fecha: 11.10.2014
				else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)){
					List<OrdenCompra> listaOrdenCompra = logisticaFacade.buscarDocumentoAdelantoGarantiaParaGiroPorTesoreria(intIdPersona, EMPRESA_USUARIO, Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO);
					for (OrdenCompra ordenCompra : listaOrdenCompra) {
						log.info(ordenCompra);
						DocumentoGeneral documentoGeneral = new DocumentoGeneral();
						documentoGeneral.setIntTipoDocumento(ordenCompra.getIntParaTipoDocumentoGeneral());
						documentoGeneral.setStrNroDocumento(""+ordenCompra.getId().getIntItemOrdenCompra());
						BigDecimal bdMonto = BigDecimal.ZERO;
						for (OrdenCompraDetalle ordCmpDet : ordenCompra.getListaOrdenCompraDetalle()) {
							bdMonto = bdMonto.add(ordCmpDet.getBdPrecioTotal());
						}
						documentoGeneral.setBdMonto(bdMonto);
						documentoGeneral.setOrdenCompra(ordenCompra);
						listaDocumentoPorAgregar.add(documentoGeneral);
					}
				}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA)){
					List<OrdenCompra> listaOrdenCompra = logisticaFacade.buscarDocumentoAdelantoGarantiaParaGiroPorTesoreria(intIdPersona, EMPRESA_USUARIO, Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA);
					for (OrdenCompra ordenCompra : listaOrdenCompra) {
						log.info(ordenCompra);
						DocumentoGeneral documentoGeneral = new DocumentoGeneral();
						documentoGeneral.setIntTipoDocumento(ordenCompra.getIntParaTipoDocumentoGeneral());
						documentoGeneral.setStrNroDocumento(""+ordenCompra.getId().getIntItemOrdenCompra());
						BigDecimal bdMonto = BigDecimal.ZERO;
						for (OrdenCompraDetalle ordCmpDet : ordenCompra.getListaOrdenCompraDetalle()) {
							bdMonto = bdMonto.add(ordCmpDet.getBdPrecioTotal());
						}
						documentoGeneral.setBdMonto(bdMonto);
						documentoGeneral.setOrdenCompra(ordenCompra);
						listaDocumentoPorAgregar.add(documentoGeneral);
					}
				}
				//Fin jchavez - 11.10.2014
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	private void cargarListaBeneficiarioPrevision()throws Exception{
		listaBeneficiarioPrevision = new ArrayList<BeneficiarioPrevision>();
		List<BeneficiarioPrevision> listaBeneficiarioPrevisionBD = previsionFacade.getListaBeneficiarioPrevision(
				documentoGeneralSeleccionado.getExpedientePrevision());
		
		for(BeneficiarioPrevision beneficiarioPrevision : listaBeneficiarioPrevisionBD){
			if(beneficiarioPrevision.getIntItemEgresoGeneral()!=null && deshabilitarNuevo==Boolean.FALSE){
				continue;
			}
			beneficiarioPrevisionSeleccionado = beneficiarioPrevision;
			BigDecimal bdPorcentaje = beneficiarioPrevision.getBdPorcentajeBeneficio().divide(new BigDecimal(100));			
			BigDecimal bdCapital = documentoGeneralSeleccionado.getExpedientePrevision().getBdMontoBrutoBeneficio().multiply(bdPorcentaje);			
			
			Persona persona = devolverPersonaCargada(beneficiarioPrevision.getIntPersPersonaBeneficiario());
			String strEtiqueta = ""+beneficiarioPrevision.getId().getIntItemBeneficiario();
			strEtiqueta = strEtiqueta + " - " + persona.getNatural().getStrNombreCompleto();
			strEtiqueta = strEtiqueta + " - DNI:" + persona.getDocumento().getStrNumeroIdentidad();
			if(beneficiarioPrevision.getIntItemViculo()!=null){
				strEtiqueta = strEtiqueta + " - VINCULO:"+obtenerEtiquetaVinculo(beneficiarioPrevision.getIntItemViculo());
			}
			strEtiqueta = strEtiqueta + " - " + beneficiarioPrevision.getBdPorcentajeBeneficio()+"%  " + formato.format(bdCapital);			
			
			beneficiarioPrevision.setStrEtiqueta(strEtiqueta);
			beneficiarioPrevision.setPersona(persona);
			
			listaBeneficiarioPrevision.add(beneficiarioPrevision);
		}
		documentoGeneralSeleccionado.getExpedientePrevision().setListaBeneficiarioPrevision(listaBeneficiarioPrevision);
	}
	
	private void cargarListaBeneficiarioLiquidacion()throws Exception{
		ExpedienteLiquidacion expedienteLiquidacion = documentoGeneralSeleccionado.getExpedienteLiquidacion();
		listaBeneficiarioPersona = new ArrayList<Persona>();
		listaBeneficiarioLiquidacion = new ArrayList<BeneficiarioLiquidacion>();
		
		List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacionTemp = new ArrayList<BeneficiarioLiquidacion>();
		for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
			log.info(expedienteLiquidacionDetalle);
			List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacionTemp2 = liquidacionFacade.getListaBeneficiarioLiquidacionPorExpedienteLiquidacionDetalle(expedienteLiquidacionDetalle); 
			expedienteLiquidacionDetalle.setListaBeneficiarioLiquidacion(listaBeneficiarioLiquidacionTemp2);
			listaBeneficiarioLiquidacionTemp.addAll(listaBeneficiarioLiquidacionTemp2);
			intCuenta = expedienteLiquidacionDetalle.getId().getIntCuenta();
		}
		//Cargamos la lista de los beneficiarios
		listaBeneficiarioLiquidacion.addAll(listaBeneficiarioLiquidacionTemp);
		
		HashSet<Integer> hashIntIdPersona = new HashSet<Integer>();
		for(BeneficiarioLiquidacion beneficiarioLiquidacion : listaBeneficiarioLiquidacionTemp){
			log.info(beneficiarioLiquidacion);
			//Solo listaremos los beneficiarios que aun no hayan girado
			if(beneficiarioLiquidacion.getIntItemEgresoGeneral()==null || deshabilitarNuevo==Boolean.TRUE){
				hashIntIdPersona.add(beneficiarioLiquidacion.getIntPersPersonaBeneficiario());
			}
		}
		
		List<Persona> listaPersona = new ArrayList<Persona>();
		for(Integer intIdPersona : hashIntIdPersona){
			log.info(intIdPersona);
			Integer intItemVinculo = null;
			for(BeneficiarioLiquidacion beneficiarioLiquidacion : listaBeneficiarioLiquidacionTemp){
				log.info(beneficiarioLiquidacion);
				if(beneficiarioLiquidacion.getIntPersPersonaBeneficiario().equals(intIdPersona)){
					intItemVinculo = beneficiarioLiquidacion.getIntItemViculo();
					//en teoria aca tomo el valor del beneficiario seleccionado.
					beneficiarioLiquidacionSeleccionado= beneficiarioLiquidacion;
					break;
				}
			}
			String strEtiqueta = "";
			Persona persona = devolverPersonaCargada(intIdPersona);
			strEtiqueta = intItemVinculo + " - " + persona.getNatural().getStrNombreCompleto();
			strEtiqueta = strEtiqueta + " - DNI : " + persona.getDocumento().getStrNumeroIdentidad();
			strEtiqueta = strEtiqueta + " - VINCULO : "+obtenerEtiquetaVinculo(intItemVinculo);
			persona.setStrEtiqueta(strEtiqueta);
			
			listaPersona.add(persona);
		}
		expedienteLiquidacion.setListaPersona(listaPersona);
		listaBeneficiarioPersona.addAll(listaPersona);
	}
	
	private Persona devolverPersonaCargada(Integer intIdPersona) throws Exception{
		//log.info(intIdPersona);
		Persona persona = personaFacade.getPersonaPorPK(intIdPersona);
		if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
			persona = personaFacade.getPersonaNaturalPorIdPersona(persona.getIntIdPersona());			
			agregarDocumentoDNI(persona);
			agregarNombreCompleto(persona);			
		
		}else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
			persona.setJuridica(personaFacade.getJuridicaPorPK(persona.getIntIdPersona()));			
		}
		
		return persona;
	}
	
	private String obtenerEtiquetaVinculo(Integer intItemVinculo) throws Exception{
		Vinculo vinculo = personaFacade.getVinculoPorId(intItemVinculo);
		for(Tabla tabla : listaTipoVinculo){
			if(tabla.getIntIdDetalle().equals(vinculo.getIntTipoVinculoCod())){
				return tabla.getStrDescripcion();
			}
		}
		return null;
	}
	
	public void seleccionarDocumento(ActionEvent event){
		blnBeneficiarioSinAutorizacion = false;
		documentoGeneralSeleccionado = null;
		blnNoExisteRequisito = false;
		try{
			documentoGeneralSeleccionado = (DocumentoGeneral)event.getComponent().getAttributes().get("item");
			
			String strEtiqueta = obtenerEtiquetaTipoDocumentoGeneral(documentoGeneralSeleccionado.getIntTipoDocumento()) 
				+ " - "+ documentoGeneralSeleccionado.getStrNroDocumento() + " - ";
			
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				strEtiqueta = strEtiqueta + obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES)+" ";
			}
			//Autor: jchavez / Tarea: Creacion / Fecha: 11.10.2014
			if(documentoGeneralSeleccionado.getOrdenCompra()!=null && 
					(documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)
							|| documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA))){
				strEtiqueta = strEtiqueta + obtenerEtiquetaTipoMoneda(documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaTipoMoneda())+" ";
			}//Fin jchavez - 11.10.2014
			strEtiqueta = strEtiqueta + formato.format(documentoGeneralSeleccionado.getBdMonto());
			
			documentoGeneralSeleccionado.setStrEtiqueta(strEtiqueta);
			if (documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)) {
				archivoAdjuntoGiro = documentoGeneralSeleccionado.getExpedienteCredito().getArchivoGiro();
			}
			
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
				|| documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
				|| documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				cargarListaBeneficiarioPrevision();				
				//obtener el mov cta por pagar de prevision. jchavez 20.05.2014
				buscarMovCtaCtePorPagarPrevision(documentoGeneralSeleccionado.getExpedientePrevision());
				blnBeneficiarioSinAutorizacion = true;
//				archivoAdjuntoGiro = documentoGeneralSeleccionado.getExpedientePrevision().getArchivoGrio();
			
			}else if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				cargarListaBeneficiarioLiquidacion();
				buscarMovCtaCtePorPagarLiquidacion(documentoGeneralSeleccionado.getExpedienteLiquidacion());
//				archivoAdjuntoGiro = documentoGeneralSeleccionado.getExpedienteLiquidacion().getArchivoGrio();
			}
			
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				if(validarNoExisteRequisitoAdjuntoGiroPrestamo()){
					documentoGeneralSeleccionado = null;
					strMensajeError = "Préstamo no cuenta con documento de autorizacion de giro";
				}else{
					strMensajeError = "";
					deshabilitarNuevo = false;
					blnVerBotonApoderado = true;
				}
			}
			log.info("mensajito: "+strMensajeError);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Metodo que recupera el movimiento generado en cuenta por pagar
	 */
	public void buscarMovCtaCtePorPagarPrevision(ExpedientePrevision expPrev) throws Exception {
		List<Movimiento> lstMov = null;
		Integer intParaTipoConcepto = null;

		try {
			switch (expPrev.getIntParaDocumentoGeneral()) {
			case (102): //Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO
				intParaTipoConcepto = Constante.PARAM_T_CUENTACONCEPTO_SEPELIO;
				break;
			case (103): //Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO
				intParaTipoConcepto = Constante.PARAM_T_CUENTACONCEPTO_RETIRO;
				break;
//			case Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO:
//				intParaTipoConcepto = Constante.PARAM_T_CUENTACONCEPTO_SEPELIO;
//				break;				
			default:
				break;
			}
			lstMov = conceptoFacade.getListaMovVtaCtePorPagar(expPrev.getId().getIntPersEmpresaPk(), expPrev.getId().getIntCuentaPk(), expPrev.getId().getIntItemExpediente(), intParaTipoConcepto,expPrev.getIntParaDocumentoGeneral());
			if (lstMov!=null && !lstMov.isEmpty()) {
				for (Movimiento movimiento : lstMov) {
					documentoGeneralSeleccionado.getExpedientePrevision().setMovimiento(movimiento);
					break;
				}
			}else documentoGeneralSeleccionado.getExpedientePrevision().setMovimiento(null);
		} catch (Exception e) {
			log.error("Error al buscarMovCtaCtePorPagar: "+e.getMessage(),e);
		}
		
	}
	
	public void buscarMovCtaCtePorPagarLiquidacion(ExpedienteLiquidacion expLiq) throws Exception {
		List<Movimiento> lstMov = null;
		try {
			lstMov = conceptoFacade.getListaMovCtaCtePorPagarLiq(expLiq.getId().getIntPersEmpresaPk(), intCuenta, expLiq.getId().getIntItemExpediente(), expLiq.getIntParaDocumentoGeneral());
			if (lstMov!=null && !lstMov.isEmpty()) {
				for (Movimiento movimiento : lstMov) {
					if (movimiento.getIntItemMovimientoRel()==null) {
						documentoGeneralSeleccionado.getExpedienteLiquidacion().setMovimiento(movimiento);
						break;
					}					
				}
			}else documentoGeneralSeleccionado.getExpedienteLiquidacion().setMovimiento(null);
		} catch (Exception e) {
			log.error("Error al buscarMovCtaCtePorPagar: "+e.getMessage(),e);
		}
		
	}
	/**
	 * Agregado 10.05.2014 jchavez
	 * evento al seleccionar un beneficiario.
	 */
	public void seleccionarBeneficiario(){
		strMensajeErrorPorBeneficiario = "";
		blnBeneficiarioSinAutorizacion = false;
		if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
			if (intBeneficiarioSeleccionar!=0) {
				if(validarNoExisteRequisitoAdjuntoGiroPrevision()){
					strMensajeErrorPorBeneficiario = "Este beneficiario no tiene adjunto de autorización de giro.";
					blnBeneficiarioSinAutorizacion = true;
				}
			}else {
				strMensajeErrorPorBeneficiario = "Debe de seleccionar un beneficiario.";
				blnBeneficiarioSinAutorizacion = true;
			}
		}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
			if (intBeneficiarioSeleccionar!=0) {
				if(validarNoExisteRequisitoAdjuntoGiroLiquidacion()){
					strMensajeErrorPorBeneficiario = "Este beneficiario no tiene adjunto de autorización de giro.";
					blnBeneficiarioSinAutorizacion = true;
				}
			}else {
				strMensajeErrorPorBeneficiario = "Debe de seleccionar un beneficiario.";
				blnBeneficiarioSinAutorizacion = true;
			}
		}
		log.info("documentoGeneralSeleccionado:"+documentoGeneralSeleccionado);
		log.info("deshabilitarNuevo:"+deshabilitarNuevo);
		log.info("blnBeneficiarioSinAutorizacion:"+blnBeneficiarioSinAutorizacion);
		cargarListaCuentaBancariaBeneficiario();
		cargarListaCuentaBancariaBeneficiarioLiquidacion();
	}
	
	/**
	 * 30.04.2014 jchavez
	 * Metodo que se encarga de la validacion de existencia de requisito para que se realice el giro por 
	 * medio de la sede.
	 * @return
	 */
	public boolean validarNoExisteRequisitoAdjuntoGiroPrestamo(){
		log.info("----- validarNoExisteRequisitoAdjuntoGiroPrestamo()");
		blnNoExisteRequisito = true;
		deshabilitarNuevo = false;
		ExpedienteCredito expedienteCredito = null;
		lstRequisitoCredito.clear();
		lstRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		
		try {
			//0. Recuperamos el expediente credito seleccionado...
			expedienteCredito = documentoGeneralSeleccionado.getExpedienteCredito();
			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
			lstRequisitoCreditoComp = prestamoFacade.getRequisitoGiroPrestamo(expedienteCredito);
			//2. validamos si existe registro en requisito credito
			if (lstRequisitoCreditoComp!=null && !lstRequisitoCreditoComp.isEmpty()) {
				Integer requisitoSize = lstRequisitoCreditoComp.size();

				for (RequisitoCreditoComp o : lstRequisitoCreditoComp) {
					ConfServDetalleId reqAutDetalleId = new ConfServDetalleId();
					reqAutDetalleId.setIntPersEmpresaPk(o.getIntEmpresa());
					reqAutDetalleId.setIntItemSolicitud(o.getIntItemRequisito());
					reqAutDetalleId.setIntItemDetalle(o.getIntItemRequisitoDetalle());
					lstRequisitoCredito.addAll(prestamoFacade.getListaPorPkExpedienteCreditoYRequisitoDetalle(expedienteCredito.getId(), reqAutDetalleId));
				}
				if (lstRequisitoCredito!=null && !lstRequisitoCredito.isEmpty()) {
					if (requisitoSize.equals(lstRequisitoCredito.size())) {
						archivoAdjuntoGiro = prestamoFacade.getArchivoPorRequisitoCredito(lstRequisitoCredito.get(0));
						blnNoExisteRequisito = false;	
						deshabilitarNuevo = true;
					}
				}
			}
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return blnNoExisteRequisito;
	}
	/**
	 * Agregado 10.05.2014 jchavez 
	 * validacion de existencia de adjunto de giro prevision 
	 * @return
	 */
	public boolean validarNoExisteRequisitoAdjuntoGiroPrevision(){
		log.info("----- validarNoExisteRequisitoAdjuntoGiroPrevision()");
		blnNoExisteRequisito = true;
		deshabilitarNuevo = false;
		ExpedientePrevision expedientePrevision = null;
		lstRequisitoPrevision.clear();
		lstRequisitoPrevisionComp.clear();
		Integer intIdMaestro = null;
		Integer intParaTipoDescripcion = null;
		listaEgresoDetalleInterfazAgregado.clear();
		try {
			//0. Recuperamos el expediente prevision seleccionado...
			expedientePrevision = documentoGeneralSeleccionado.getExpedientePrevision();
			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
			if (expedientePrevision.getIntParaTipoCaptacion().equals(Constante.CAPTACION_FDO_SEPELIO)) {
				expedientePrevision.setIntParaTipoOperacion(Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO);
				intIdMaestro = Constante.PARAM_T_ADJUNTOGIROPREVISION_SEPELIO;
				intParaTipoDescripcion = Constante.PARAM_T_ADJUNTOGIROPREVISION_SEPELIO_DETALLE;
			}
			if (expedientePrevision.getIntParaTipoCaptacion().equals(Constante.CAPTACION_FDO_RETIRO)) {
				expedientePrevision.setIntParaTipoOperacion(Constante.PARAM_T_TIPOOPERACION_FONDORETIRO);
				intIdMaestro = Constante.PARAM_T_ADJUNTOGIROPREVISION_RETIRO;
				intParaTipoDescripcion = Constante.PARAM_T_ADJUNTOGIROPREVISION_RETIRO_DETALLE;
			}
			if (expedientePrevision.getIntParaTipoCaptacion().equals(Constante.CAPTACION_AES)) {
				expedientePrevision.setIntParaTipoOperacion(Constante.PARAM_T_TIPOOPERACION_AES);
				intIdMaestro = Constante.PARAM_T_ADJUNTOGIROPREVISION_AES;
				intParaTipoDescripcion = Constante.PARAM_T_ADJUNTOGIROPREVISION_AES_DETALLE;
			}
			lstRequisitoPrevisionComp = previsionFacade.getRequisitoGiroPrevision(expedientePrevision, intIdMaestro, intParaTipoDescripcion);
			//
			for(BeneficiarioPrevision beneficiarioPrevision : listaBeneficiarioPrevision){
				if(beneficiarioPrevision.getId().getIntItemBeneficiario().equals(intBeneficiarioSeleccionar)){
					beneficiarioPrevisionSeleccionado = beneficiarioPrevision;
//					listaEgresoDetalleInterfaz = previsionFacade.cargarListaEgresoDetalleInterfaz(expedientePrevisionGirar, beneficiarioSeleccionado);					
//					bdTotalEgresoDetalleInterfaz = ((EgresoDetalleInterfaz)(listaEgresoDetalleInterfaz.get(0))).getBdSubTotal();;
					break;
				}
			}
			//2. validamos si existe registro en requisito prevision
			if (lstRequisitoPrevisionComp!=null && !lstRequisitoPrevisionComp.isEmpty()) {
				Integer requisitoSize = lstRequisitoPrevisionComp.size();

				for (RequisitoPrevisionComp o : lstRequisitoPrevisionComp) {
					ConfServDetalleId reqAutDetalleId = new ConfServDetalleId();
					reqAutDetalleId.setIntPersEmpresaPk(o.getIntEmpresa());
					reqAutDetalleId.setIntItemSolicitud(o.getIntItemRequisito());
					reqAutDetalleId.setIntItemDetalle(o.getIntItemRequisitoDetalle());
					lstRequisitoPrevision.addAll(previsionFacade.getListaPorPkExpedientePrevisionYRequisitoDetalle(expedientePrevision.getId(), reqAutDetalleId, beneficiarioPrevisionSeleccionado.getIntPersPersonaBeneficiario()));
				}
				if (lstRequisitoPrevision!=null && !lstRequisitoPrevision.isEmpty()) {
					if (requisitoSize.equals(lstRequisitoPrevision.size())) {
						archivoAdjuntoGiro = previsionFacade.getArchivoPorRequisitoPrevision(lstRequisitoPrevision.get(0));
						blnNoExisteRequisito = false;	
//						deshabilitarNuevo = true;
					}
				}
			}
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return blnNoExisteRequisito;
	}
	/**
	 * Agregado 12.05.2014 jchavez
	 * valida la existencia de requisito liquidacion en giro
	 * @return
	 */
	public boolean validarNoExisteRequisitoAdjuntoGiroLiquidacion(){
		log.info("----- validarNoExisteRequisitoAdjuntoGiroLiquidacion()");
		blnNoExisteRequisito = true;
		deshabilitarNuevo = false;
		ExpedienteLiquidacion expedienteLiquidacion = null;
		lstRequisitoLiquidacion.clear();
		lstRequisitoLiquidacionComp.clear();
		listaEgresoDetalleInterfazAgregado.clear();
		try {
			//0. Recuperamos el expediente prevision seleccionado...
			expedienteLiquidacion = documentoGeneralSeleccionado.getExpedienteLiquidacion();
			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
			lstRequisitoLiquidacionComp = liquidacionFacade.getRequisitoGiroLiquidacionBanco(expedienteLiquidacion);
			//
			for(BeneficiarioLiquidacion beneficiarioLiquidacion : listaBeneficiarioLiquidacion){
				if(beneficiarioLiquidacion.getIntPersPersonaBeneficiario().equals(intBeneficiarioSeleccionar)){
					beneficiarioLiquidacionSeleccionado = beneficiarioLiquidacion;
					break;
				}
			}		
			//2. validamos si existe registro en requisito credito
			if (lstRequisitoLiquidacionComp!=null && !lstRequisitoLiquidacionComp.isEmpty()) {
				Integer requisitoSize = lstRequisitoLiquidacionComp.size();

				for (RequisitoLiquidacionComp o : lstRequisitoLiquidacionComp) {
					ConfServDetalleId reqAutDetalleId = new ConfServDetalleId();
					reqAutDetalleId.setIntPersEmpresaPk(o.getIntEmpresa());
					reqAutDetalleId.setIntItemSolicitud(o.getIntItemRequisito());
					reqAutDetalleId.setIntItemDetalle(o.getIntItemRequisitoDetalle());
					//Se agrega campo del codigo de persona beneficiario. jchavez 09.05.2014
					lstRequisitoLiquidacion.addAll(liquidacionFacade.getListaPorPkExpedienteLiquidacionYRequisitoDetalle(expedienteLiquidacion.getId(), reqAutDetalleId, beneficiarioLiquidacionSeleccionado.getIntPersPersonaBeneficiario()));
				}
				if (lstRequisitoLiquidacion!=null && !lstRequisitoLiquidacion.isEmpty()) {
					if (requisitoSize.equals(lstRequisitoLiquidacion.size())) {
						archivoAdjuntoGiro = liquidacionFacade.getArchivoPorRequisitoLiquidacion(lstRequisitoLiquidacion.get(0));
						blnNoExisteRequisito = false;	
//						deshabilitarNuevo = true;
					}
				}
			}
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return blnNoExisteRequisito;
	}	
	
	
	private String obtenerEtiquetaTipoMovilidad(Integer intTipoMovilidad)throws Exception{
		List<Tabla> listaTablaTipoMovilidad = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_PLANILLAMOVILIDAD));
		for(Tabla tabla : listaTablaTipoMovilidad){
			if(tabla.getIntIdDetalle().equals(intTipoMovilidad)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	private SocioEstructura obtenerSocioEstructura(Persona persona, Integer intIdEmpresa) throws Exception{
		SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
		List<SocioEstructura> lista = socioFacade.getListaSocioEstrucuraPorIdPersona(persona.getIntIdPersona(), intIdEmpresa);		
		SocioEstructura socioEstructura = lista.get(0);
		
		Sucursal sucursal = new Sucursal();
		sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
		sucursal.getId().setIntIdSucursal(socioEstructura.getIntIdSucursalAdministra());
		sucursal = empresaFacade.getSucursalPorPK(sucursal);
		
		Subsucursal subsucursal = empresaFacade.getSubSucursalPorIdSubSucursal(socioEstructura.getIntIdSubsucurAdministra());
		
		socioEstructura.setSucursal(sucursal);
		socioEstructura.setSubsucursal(subsucursal);
		
		return socioEstructura;
	}
	
	private boolean validarMontosExpedienteCredito(ExpedienteCredito expedienteCredito) throws Exception{
		boolean exito = Boolean.FALSE;
		BigDecimal bdMontoCanceladoAcumulado = new BigDecimal(0);
		for(CancelacionCredito cancelacionCredito : expedienteCredito.getListaCancelacionCredito()){
			bdMontoCanceladoAcumulado = bdMontoCanceladoAcumulado.add(cancelacionCredito.getBdMontoCancelado());
		}
		
		if(expedienteCredito.getBdMontoTotal()==null){
			return exito;//FALSE
		}
		
		BigDecimal bdMontoValidar = new BigDecimal(0);
		bdMontoValidar = bdMontoValidar.add(bdMontoCanceladoAcumulado);
		if(expedienteCredito.getBdMontoGravamen()!=null){
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoGravamen());
		}
		if(expedienteCredito.getBdMontoAporte()!=null){
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoAporte());
		}
		if(expedienteCredito.getBdMontoInteresAtrasado()!=null){
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoInteresAtrasado());
		}
		if(expedienteCredito.getBdMontoSolicitado()!=null){
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoSolicitado());
		}else{
			return exito;//FALSE
		}
		log.info("total : "+expedienteCredito.getBdMontoTotal());
		log.info("valid : "+bdMontoValidar);
		if(!expedienteCredito.getBdMontoTotal().equals(bdMontoValidar)){
			return exito;//FALSE
		}
		exito = Boolean.TRUE;
		return exito;
	}
	
	private void manejarAgregarExpedientePrevision() throws Exception{
		ExpedientePrevision expedientePrevision = documentoGeneralSeleccionado.getExpedientePrevision();
		BeneficiarioPrevision beneficiarioPrevisionSeleccionado = null;
		for(Object o : listaBeneficiarioPrevision){
			BeneficiarioPrevision beneficiarioPrevision = (BeneficiarioPrevision)o;
			if(listaBeneficiarioPrevision.size()==1){
				beneficiarioPrevision.setEsUltimoBeneficiarioAGirar(Boolean.TRUE);
			}else{
				beneficiarioPrevision.setEsUltimoBeneficiarioAGirar(Boolean.FALSE);
			}
			if(beneficiarioPrevision.getId().getIntItemBeneficiario().equals(intBeneficiarioSeleccionar)){
				beneficiarioPrevisionSeleccionado = beneficiarioPrevision;
				break;
			}
		}

		beneficiarioPrevisionSeleccionado.setPersonaApoderado(personaApoderado);
		beneficiarioPrevisionSeleccionado.setArchivoCartaPoder(archivoCartaPoder);
		expedientePrevision.setBeneficiarioPrevisionGirar(beneficiarioPrevisionSeleccionado);		
		
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = previsionFacade.
										cargarListaEgresoDetalleInterfaz(expedientePrevision, beneficiarioPrevisionSeleccionado);
		expedientePrevision.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
	}
	
	private void manejarAgregarExpedienteLiquidacion() throws Exception{
		ExpedienteLiquidacion expedienteLiquidacion = documentoGeneralSeleccionado.getExpedienteLiquidacion();
		
		if(listaBeneficiarioPersona.size()==1){
			expedienteLiquidacion.setEsUltimoBeneficiarioAGirar(Boolean.TRUE);
		}else{
			expedienteLiquidacion.setEsUltimoBeneficiarioAGirar(Boolean.FALSE);
		}
		
//		for (BeneficiarioLiquidacion x : listaBeneficiarioLiquidacion) {
//			if (x.getIntPersPersonaBeneficiario().equals(intBeneficiarioSeleccionar)) {
//				expedienteLiquidacion.setBeneficiarioLiquidacionGirar(x);		
//				break;
//			}
//		}
		
		expedienteLiquidacion.setIntIdPersonaBeneficiarioGirar(intBeneficiarioSeleccionar);
		
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = liquidacionFacade.
					cargarListaEgresoDetalleInterfaz(expedienteLiquidacion.getListaExpedienteLiquidacionDetalle(), intBeneficiarioSeleccionar);
		expedienteLiquidacion.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
	}
	
	public void agregarDocumento(){
		try{
			if(documentoGeneralSeleccionado.getBdMonto()==null){
				return;
			}
			
			int intOrden = 1;			
			if(bdMontoGirar!=null && bdMontoGirar.signum()>0){
				EgresoDetalleInterfaz ultimoEDI = (EgresoDetalleInterfaz)
					(listaEgresoDetalleInterfazAgregado.get(listaEgresoDetalleInterfazAgregado.size()-1));
				intOrden = ultimoEDI.getIntOrden() + 1;
			}else{
				bdMontoGirar = new BigDecimal(0);
				bdDiferencialGirar = new BigDecimal(0);
			}			
			
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				Movilidad movilidad = documentoGeneralSeleccionado.getMovilidad();
				List<EgresoDetalleInterfaz> listaEDI = egresoFacade.cargarListaEgresoDetalleInterfaz(movilidad);
				movilidad.setListaEgresoDetalleInterfaz(listaEDI);
				movilidad.setPersona(personaSeleccionada);
				for(EgresoDetalleInterfaz eDI : listaEDI){
					eDI.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD);
					eDI.setStrNroDocumento(""+movilidad.getId().getIntItemMovilidad());
					eDI.setPersona(personaSeleccionada);
					eDI.setSucursal(movilidad.getSucursal());
					eDI.setSubsucursal(movilidad.getSubsucursal());
					eDI.setStrDescripcion(obtenerEtiquetaTipoMovilidad(eDI.getIntParaConcepto()));
					eDI.setBdSubTotal(eDI.getBdMonto());
					eDI.setLibroDiario(libroDiario);
					
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}				
				
			}else if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				if(personaApoderado!=null && archivoCartaPoder==null){
					mostrarMensaje(Boolean.FALSE, "Debe adjuntar una carta de poder al apoderado.");
					return;
				}
				ExpedienteCredito expedienteCredito = documentoGeneralSeleccionado.getExpedienteCredito();
				log.info(personaSeleccionada);
				log.info(personaApoderado);
				log.info(archivoCartaPoder);
				expedienteCredito.setPersonaGirar(personaSeleccionada);
				expedienteCredito.setPersonaApoderado(personaApoderado);
				expedienteCredito.setArchivoCartaPoder(archivoCartaPoder);
				SocioEstructura socioEstructura = obtenerSocioEstructura(personaSeleccionada, EMPRESA_USUARIO);
				
				expedienteCredito.setListaCancelacionCredito(prestamoFacade.getListaCancelacionCreditoPorExpedienteCredito(expedienteCredito));
				List<EgresoDetalleInterfaz> listaEDI = prestamoFacade.cargarListaEgresoDetalleInterfaz(expedienteCredito);
				expedienteCredito.setListaCancelacionCredito(prestamoFacade.getListaCancelacionCreditoPorExpedienteCredito(expedienteCredito));
				if(!validarMontosExpedienteCredito(documentoGeneralSeleccionado.getExpedienteCredito())){
					mostrarMensaje(Boolean.FALSE, "Hay un error con los montos del expediente de crédito.");
					return;
				}
				for(EgresoDetalleInterfaz eDI : listaEDI){
					eDI.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
					eDI.setStrNroDocumento(""+expedienteCredito.getId().getIntItemExpediente());
					eDI.setPersona(personaSeleccionada);
					eDI.setSucursal(socioEstructura.getSucursal());
					eDI.setSubsucursal(socioEstructura.getSubsucursal());
					eDI.setStrDescripcion(obtenerEtiquetaTipoDocumentoGeneral(eDI.getIntParaDocumentoGeneral()));
					eDI.setBdMonto(eDI.getBdSubTotal());
					eDI.setLibroDiario(libroDiario);
					
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
			}else if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
					|| documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
					|| documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				if(personaApoderado!=null && archivoCartaPoder==null){
					mostrarMensaje(Boolean.FALSE, "Debe adjuntar una carta de poder al apoderado.");
					return;
				}
				manejarAgregarExpedientePrevision();
				ExpedientePrevision expedientePrevision = documentoGeneralSeleccionado.getExpedientePrevision();
				SocioEstructura socioEstructura = obtenerSocioEstructura(personaSeleccionada, EMPRESA_USUARIO);				
				List<EgresoDetalleInterfaz> listaEDI = expedientePrevision.getListaEgresoDetalleInterfaz();
				expedientePrevision.setPersonaGirar(personaSeleccionada);
				expedientePrevision.setListaEgresoDetalleInterfaz(listaEDI);
				 
				for(EgresoDetalleInterfaz eDI : listaEDI){
					eDI.setIntParaDocumentoGeneral(expedientePrevision.getIntParaDocumentoGeneral());
					eDI.setStrNroDocumento(""+expedientePrevision.getId().getIntItemExpediente());
					eDI.setPersona(personaSeleccionada);
					eDI.setSucursal(socioEstructura.getSucursal());
					eDI.setSubsucursal(socioEstructura.getSubsucursal());
					eDI.setStrDescripcion(obtenerEtiquetaTipoDocumentoGeneral(eDI.getIntParaDocumentoGeneral()));
					eDI.setBdMonto(eDI.getBdSubTotal());
					eDI.setLibroDiario(libroDiario);
					eDI.setArchivoAdjuntoGiro(archivoAdjuntoGiro);
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
				
			}else if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				manejarAgregarExpedienteLiquidacion();
				ExpedienteLiquidacion expedienteLiquidacion = documentoGeneralSeleccionado.getExpedienteLiquidacion();
				List<EgresoDetalleInterfaz> listaEDI = expedienteLiquidacion.getListaEgresoDetalleInterfaz(); 
				expedienteLiquidacion.setPersonaGirar(personaSeleccionada);
				expedienteLiquidacion.setListaEgresoDetalleInterfaz(listaEDI);
				
				SocioEstructura socioEstructura = obtenerSocioEstructura(personaSeleccionada, EMPRESA_USUARIO);
				
				for(EgresoDetalleInterfaz eDI : listaEDI){
					eDI.setIntParaDocumentoGeneral(expedienteLiquidacion.getIntParaDocumentoGeneral());
					eDI.setStrNroDocumento(""+expedienteLiquidacion.getId().getIntItemExpediente());
					eDI.setPersona(personaSeleccionada);
					eDI.setSucursal(socioEstructura.getSucursal());
					eDI.setSubsucursal(socioEstructura.getSubsucursal());
					//Autor: jchavez / Tarea: Modificación / Fecha: 18.08.2014 /
					eDI.setStrDescripcion(obtenerEtiquetaTipoDocumentoGeneral(eDI.getIntParaDocumentoGeneral()));//eDI.getExpedienteLiquidacionDetalle().getCuentaConcepto().getDetalle().getCaptacion().getStrDescripcion()
					eDI.setBdMonto(eDI.getBdSubTotal());
					eDI.setLibroDiario(libroDiario);
					eDI.setArchivoAdjuntoGiro(archivoAdjuntoGiro);
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
			}			
			//Autor: jchavez / Tarea: Creacion / Fecha: 11.10.2014
			else if(documentoGeneralSeleccionado.getOrdenCompra()!=null && documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)){
				OrdenCompra ordenCompra = documentoGeneralSeleccionado.getOrdenCompra();
				List<EgresoDetalleInterfaz> listaEDI = egresoFacade.cargarListaEgresoDetalleInterfazOrdenCompra(ordenCompra, intTipoDocumentoAgregar);
				for(EgresoDetalleInterfaz eDI : listaEDI){
					log.info(eDI.getIntParaDocumentoGeneral());
					eDI.setStrDescripcion(MyUtil.obtenerEtiquetaTabla(eDI.getIntParaDocumentoGeneral(), listaTablaDocumentoGeneral));
					eDI.setPersona(personaSeleccionada);
					eDI.setLibroDiario(libroDiario);
					eDI.setOrdenCompra(ordenCompra);
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
			}else if(documentoGeneralSeleccionado.getOrdenCompra()!=null && documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA)){
				OrdenCompra ordenCompra = documentoGeneralSeleccionado.getOrdenCompra();
				List<EgresoDetalleInterfaz> listaEDI = egresoFacade.cargarListaEgresoDetalleInterfazOrdenCompra(ordenCompra, intTipoDocumentoAgregar);
				for(EgresoDetalleInterfaz eDI : listaEDI){
					log.info(eDI.getIntParaDocumentoGeneral());
					eDI.setStrDescripcion(MyUtil.obtenerEtiquetaTabla(eDI.getIntParaDocumentoGeneral(), listaTablaDocumentoGeneral));
					eDI.setPersona(personaSeleccionada);
					eDI.setLibroDiario(libroDiario);
					eDI.setOrdenCompra(ordenCompra);
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
			}//Fin jchavez - 11.10.2014
			
			for(Object o : listaEgresoDetalleInterfazAgregado){
				EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
				if(egresoDetalleInterfaz.getIntOrden()==null){
					egresoDetalleInterfaz.setIntOrden(intOrden);
					if (egresoDetalleInterfaz.isEsDiferencial()==Boolean.FALSE) {
						bdMontoGirar = bdMontoGirar.add(egresoDetalleInterfaz.getBdSubTotal());
					}else {
						bdDiferencialGirar = bdDiferencialGirar.add(egresoDetalleInterfaz.getBdSubTotal());
					}
					
					intOrden++;
				}
			}
			
			strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, Constante.PARAM_T_TIPOMONEDA_SOLES);
			strDiferencialGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdDiferencialGirar, Constante.PARAM_T_TIPOMONEDA_SOLES);
			
			listaDocumentoAgregados.add(documentoGeneralSeleccionado);
			
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfazTemp = listaEgresoDetalleInterfazAgregado;
			//Ordenamos por intOrden
			Collections.sort(listaEgresoDetalleInterfazTemp, new Comparator<EgresoDetalleInterfaz>(){
				public int compare(EgresoDetalleInterfaz uno, EgresoDetalleInterfaz otro) {
					return uno.getIntOrden().compareTo(otro.getIntOrden());
				}
			});
			//CUSI CUSA PARA VER DOCUMENTO DE GIRO
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			Archivo archivoReqGiro = new Archivo();
			archivoReqGiro.setId(new ArchivoId());
			if (lstRequisitoCredito!=null && !lstRequisitoCredito.isEmpty()) {
				for (RequisitoCredito x : lstRequisitoCredito) {
					archivoReqGiro.getId().setIntItemArchivo(x.getIntParaItemArchivo());
					archivoReqGiro.getId().setIntItemHistorico(x.getIntParaItemHistorico());
					archivoReqGiro.getId().setIntParaTipoCod(x.getIntParaTipoArchivoCod());
					archivoReqGiro = generalFacade.getArchivoPorPK(archivoReqGiro.getId());
					listaEgresoDetalleInterfazAgregado.get(0).setArchivoAdjuntoGiro(archivoReqGiro);
				}
			}
			
			blnVerBotonApoderado = false;
			deshabilitarNuevo = true;
			blnHabilitarObservacion = false;
//			documentoGeneralSeleccionado = null;
			ocultarMensaje();
		}catch(Exception e){
			mostrarMensaje(Boolean.FALSE,"Ocurrió un error al agregar el documento.");
			log.error(e.getMessage(), e);
		}
	}
	
	public void quitarPersonaSeleccionada(){
		try{
			limpiarFormulario();
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void quitarCartaPoder(){
		try{
			archivoCartaPoder = null;
			((FileUploadController)getSessionBean("fileUploadController")).setArchivoCartaPoder(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private List<DocumentoGeneral> agregarStrGlosa(List<DocumentoGeneral> listaDocumentoGeneral) throws Exception{
		for(DocumentoGeneral documentoGeneral : listaDocumentoGeneral){
			if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				Movilidad movilidad = documentoGeneral.getMovilidad();
				movilidad.setStrGlosaEgreso(strObservacion);
				
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				ExpedienteCredito expedienteCredito = documentoGeneral.getExpedienteCredito();
				expedienteCredito.setStrGlosaEgreso(strObservacion);
				
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
					|| documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
					|| documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				ExpedientePrevision expedientePrevision = documentoGeneral.getExpedientePrevision();
				expedientePrevision.setStrGlosaEgreso(strObservacion);
				
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				ExpedienteLiquidacion expedienteLiquidacion = documentoGeneral.getExpedienteLiquidacion();
				expedienteLiquidacion.setStrGlosaEgreso(strObservacion);
			//Autor: jchavez / Tarea: Creación / Fecha: 11.10.2014
			}else if(documentoGeneralSeleccionado.getOrdenCompra()!=null && 
					(documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)
							|| documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA))){
				OrdenCompra ordCmp = documentoGeneral.getOrdenCompra();
				ordCmp.setStrGlosaEgreso(strObservacion);
			}//fin jchavez - 11.10.2014
		}
		return listaDocumentoGeneral;
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		return sesion.getAttribute(beanName);
	}
	
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public String getMensajePopUp() {
		return mensajePopUp;
	}
	public void setMensajePopUp(String mensajePopUp) {
		this.mensajePopUp = mensajePopUp;
	}
	public boolean isMostrarBtnEliminar() {
		return mostrarBtnEliminar;
	}
	public void setMostrarBtnEliminar(boolean mostrarBtnEliminar) {
		this.mostrarBtnEliminar = mostrarBtnEliminar;
	}
	public boolean isMostrarMensajeExito() {
		return mostrarMensajeExito;
	}
	public void setMostrarMensajeExito(boolean mostrarMensajeExito) {
		this.mostrarMensajeExito = mostrarMensajeExito;
	}
	public boolean isMostrarMensajeError() {
		return mostrarMensajeError;
	}
	public void setMostrarMensajeError(boolean mostrarMensajeError) {
		this.mostrarMensajeError = mostrarMensajeError;
	}
	public boolean isDeshabilitarNuevo() {
		return deshabilitarNuevo;
	}
	public void setDeshabilitarNuevo(boolean deshabilitarNuevo) {
		this.deshabilitarNuevo = deshabilitarNuevo;
	}
	public boolean isMostrarPanelInferior() {
		return mostrarPanelInferior;
	}
	public void setMostrarPanelInferior(boolean mostrarPanelInferior) {
		this.mostrarPanelInferior = mostrarPanelInferior;
	}
	public boolean isRegistrarNuevo() {
		return registrarNuevo;
	}
	public void setRegistrarNuevo(boolean registrarNuevo) {
		this.registrarNuevo = registrarNuevo;
	}
	public boolean isHabilitarGrabar() {
		return habilitarGrabar;
	}
	public void setHabilitarGrabar(boolean habilitarGrabar) {
		this.habilitarGrabar = habilitarGrabar;
	}
	public boolean isDatosValidados() {
		return datosValidados;
	}
	public void setDatosValidados(boolean datosValidados) {
		this.datosValidados = datosValidados;
	}
	public List<Egreso> getListaEgreso() {
		return listaEgreso;
	}
	public void setListaEgreso(List<Egreso> listaEgreso) {
		this.listaEgreso = listaEgreso;
	}
//	public List getListaTipoDocumento() {
//		return listaTipoDocumento;
//	}
//	public void setListaTipoDocumento(List listaTipoDocumento) {
//		this.listaTipoDocumento = listaTipoDocumento;
//	}
	public Integer getIntTipoDocumentoValidar() {
		return intTipoDocumentoValidar;
	}
	public void setIntTipoDocumentoValidar(Integer intTipoDocumentoValidar) {
		this.intTipoDocumentoValidar = intTipoDocumentoValidar;
	}
	public Sucursal getSucursalUsuario() {
		return sucursalUsuario;
	}
	public void setSucursalUsuario(Sucursal sucursalUsuario) {
		this.sucursalUsuario = sucursalUsuario;
	}
	public Subsucursal getSubsucursalUsuario() {
		return subsucursalUsuario;
	}
	public void setSubsucursalUsuario(Subsucursal subsucursalUsuario) {
		this.subsucursalUsuario = subsucursalUsuario;
	}
	public Egreso getEgresoNuevo() {
		return egresoNuevo;
	}
	public void setEgresoNuevo(Egreso egresoNuevo) {
		this.egresoNuevo = egresoNuevo;
	}
	public List<Bancocuenta> getListaBancoCuentaOrigen() {
		return listaBancoCuentaOrigen;
	}
	public void setListaBancoCuentaOrigen(List<Bancocuenta> listaBancoCuentaOrigen) {
		this.listaBancoCuentaOrigen = listaBancoCuentaOrigen;
	}
	public List<Bancocuenta> getListaBancoCuentaDestino() {
		return listaBancoCuentaDestino;
	}
	public void setListaBancoCuentaDestino(List<Bancocuenta> listaBancoCuentaDestino) {
		this.listaBancoCuentaDestino = listaBancoCuentaDestino;
	}
	public Integer getIntBancoSeleccionadoOrigen() {
		return intBancoSeleccionadoOrigen;
	}
	public void setIntBancoSeleccionadoOrigen(Integer intBancoSeleccionadoOrigen) {
		this.intBancoSeleccionadoOrigen = intBancoSeleccionadoOrigen;
	}
	public Integer getIntBancoCuentaSeleccionadoOrigen() {
		return intBancoCuentaSeleccionadoOrigen;
	}
	public void setIntBancoCuentaSeleccionadoOrigen(Integer intBancoCuentaSeleccionadoOrigen) {
		this.intBancoCuentaSeleccionadoOrigen = intBancoCuentaSeleccionadoOrigen;
	}
	public Integer getIntBancoSeleccionadoDestino() {
		return intBancoSeleccionadoDestino;
	}
	public void setIntBancoSeleccionadoDestino(Integer intBancoSeleccionadoDestino) {
		this.intBancoSeleccionadoDestino = intBancoSeleccionadoDestino;
	}
	public Integer getIntBancoCuentaSeleccionadoDestino() {
		return intBancoCuentaSeleccionadoDestino;
	}
	public void setIntBancoCuentaSeleccionadoDestino(Integer intBancoCuentaSeleccionadoDestino) {
		this.intBancoCuentaSeleccionadoDestino = intBancoCuentaSeleccionadoDestino;
	}
//	public List getListaSubTipoOperacion() {
//		return listaSubTipoOperacion;
//	}
//	public void setListaSubTipoOperacion(List listaSubTipoOperacion) {
//		this.listaSubTipoOperacion = listaSubTipoOperacion;
//	}
	public Integer getIntNumeroTransferencia() {
		return intNumeroTransferencia;
	}
	public void setIntNumeroTransferencia(Integer intNumeroTransferencia) {
		this.intNumeroTransferencia = intNumeroTransferencia;
	}
	public BigDecimal getBdMontoGirar() {
		return bdMontoGirar;
	}
	public void setBdMontoGirar(BigDecimal bdMontoGirar) {
		this.bdMontoGirar = bdMontoGirar;
	}
	public Integer getIntTipoSubOperacion() {
		return intTipoSubOperacion;
	}
	public void setIntTipoSubOperacion(Integer intTipoSubOperacion) {
		this.intTipoSubOperacion = intTipoSubOperacion;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Date getDtDesdeFiltro() {
		return dtDesdeFiltro;
	}
	public void setDtDesdeFiltro(Date dtDesdeFiltro) {
		this.dtDesdeFiltro = dtDesdeFiltro;
	}
	public Date getDtHastaFiltro() {
		return dtHastaFiltro;
	}
	public void setDtHastaFiltro(Date dtHastaFiltro) {
		this.dtHastaFiltro = dtHastaFiltro;
	}
	public Egreso getEgresoFiltro() {
		return egresoFiltro;
	}
	public void setEgresoFiltro(Egreso egresoFiltro) {
		this.egresoFiltro = egresoFiltro;
	}
	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}
	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
	}
	public Integer getIntTipoRol() {
		return intTipoRol;
	}
	public void setIntTipoRol(Integer intTipoRol) {
		this.intTipoRol = intTipoRol;
	}
	public List<Persona> getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}
	public String getStrFiltroTextoPersona() {
		return strFiltroTextoPersona;
	}
	public void setStrFiltroTextoPersona(String strFiltroTextoPersona) {
		this.strFiltroTextoPersona = strFiltroTextoPersona;
	}
	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}
	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}
	public CuentaBancaria getCuentaBancariaSeleccionada() {
		return cuentaBancariaSeleccionada;
	}
	public void setCuentaBancariaSeleccionada(CuentaBancaria cuentaBancariaSeleccionada) {
		this.cuentaBancariaSeleccionada = cuentaBancariaSeleccionada;
	}
	public Integer getIntTipoDocumentoAgregar() {
		return intTipoDocumentoAgregar;
	}
	public void setIntTipoDocumentoAgregar(Integer intTipoDocumentoAgregar) {
		this.intTipoDocumentoAgregar = intTipoDocumentoAgregar;
	}
	public DocumentoGeneral getDocumentoGeneralSeleccionado() {
		return documentoGeneralSeleccionado;
	}
	public void setDocumentoGeneralSeleccionado(DocumentoGeneral documentoGeneralSeleccionado) {
		this.documentoGeneralSeleccionado = documentoGeneralSeleccionado;
	}
	public List<DocumentoGeneral> getListaDocumentoAgregados() {
		return listaDocumentoAgregados;
	}
	public void setListaDocumentoAgregados(List<DocumentoGeneral> listaDocumentoAgregados) {
		this.listaDocumentoAgregados = listaDocumentoAgregados;
	}
	public List<DocumentoGeneral> getListaDocumentoPorAgregar() {
		return listaDocumentoPorAgregar;
	}
	public void setListaDocumentoPorAgregar(List<DocumentoGeneral> listaDocumentoPorAgregar) {
		this.listaDocumentoPorAgregar = listaDocumentoPorAgregar;
	}
	public String getStrMontoGirarDescripcion() {
		return strMontoGirarDescripcion;
	}
	public void setStrMontoGirarDescripcion(String strMontoGirarDescripcion) {
		this.strMontoGirarDescripcion = strMontoGirarDescripcion;
	}
	public String getStrMontoGirar() {
		return strMontoGirar;
	}
	public void setStrMontoGirar(String strMontoGirar) {
		this.strMontoGirar = strMontoGirar;
	}
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	public boolean isSeleccionaTelecredito() {
		return seleccionaTelecredito;
	}
	public void setSeleccionaTelecredito(boolean seleccionaTelecredito) {
		this.seleccionaTelecredito = seleccionaTelecredito;
	}
	public List<Egreso> getListaEgresoPorAgregar() {
		return listaEgresoPorAgregar;
	}
	public void setListaEgresoPorAgregar(List<Egreso> listaEgresoPorAgregar) {
		this.listaEgresoPorAgregar = listaEgresoPorAgregar;
	}
	public List<Egreso> getListaEgresoAgregado() {
		return listaEgresoAgregado;
	}
	public void setListaEgresoAgregado(List<Egreso> listaEgresoAgregado) {
		this.listaEgresoAgregado = listaEgresoAgregado;
	}
	public Egreso getEgresoTelecreditoFiltro() {
		return egresoTelecreditoFiltro;
	}
	public void setEgresoTelecreditoFiltro(Egreso egresoTelecreditoFiltro) {
		this.egresoTelecreditoFiltro = egresoTelecreditoFiltro;
	}
	public List<EgresoDetalleInterfaz> getListaEgresoDetalleInterfazAgregado() {
		return listaEgresoDetalleInterfazAgregado;
	}
	public void setListaEgresoDetalleInterfazAgregado(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfazAgregado) {
		this.listaEgresoDetalleInterfazAgregado = listaEgresoDetalleInterfazAgregado;
	}
	public List<BeneficiarioPrevision> getListaBeneficiarioPrevision() {
		return listaBeneficiarioPrevision;
	}
	public void setListaBeneficiarioPrevision(List<BeneficiarioPrevision> listaBeneficiarioPrevision) {
		this.listaBeneficiarioPrevision = listaBeneficiarioPrevision;
	}
	public Persona getPersonaApoderado() {
		return personaApoderado;
	}
	public void setPersonaApoderado(Persona personaApoderado) {
		this.personaApoderado = personaApoderado;
	}
	public LibroDiario getLibroDiario() {
		return libroDiario;
	}
	public void setLibroDiario(LibroDiario libroDiario) {
		this.libroDiario = libroDiario;
	}
	public Integer getIntTipoBuscarPersona() {
		return intTipoBuscarPersona;
	}
	public void setIntTipoBuscarPersona(Integer intTipoBuscarPersona) {
		this.intTipoBuscarPersona = intTipoBuscarPersona;
	}
	public List<Persona> getListaBeneficiarioPersona() {
		return listaBeneficiarioPersona;
	}
	public void setListaBeneficiarioPersona(List<Persona> listaBeneficiarioPersona) {
		this.listaBeneficiarioPersona = listaBeneficiarioPersona;
	}
	public Archivo getArchivoCartaPoder() {
		return archivoCartaPoder;
	}
	public void setArchivoCartaPoder(Archivo archivoCartaPoder) {
		this.archivoCartaPoder = archivoCartaPoder;
	}
	public String getStrTextoPersonaFiltro() {
		return strTextoPersonaFiltro;
	}
	public void setStrTextoPersonaFiltro(String strTextoPersonaFiltro) {
		this.strTextoPersonaFiltro = strTextoPersonaFiltro;
	}
	public Integer getIntTipoPersonaFiltro() {
		return intTipoPersonaFiltro;
	}
	public void setIntTipoPersonaFiltro(Integer intTipoPersonaFiltro) {
		this.intTipoPersonaFiltro = intTipoPersonaFiltro;
	}
	public Integer getIntTipoBusquedaPersona() {
		return intTipoBusquedaPersona;
	}
	public void setIntTipoBusquedaPersona(Integer intTipoBusquedaPersona) {
		this.intTipoBusquedaPersona = intTipoBusquedaPersona;
	}
	//Agregado 20.12.2013 JCHAVEZ
	public List<Tabla> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	public void setListaTipoDocumento(List<Tabla> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	public List<Tabla> getListaSubTipoOperacion() {
		return listaSubTipoOperacion;
	}

	public void setListaSubTipoOperacion(List<Tabla> listaSubTipoOperacion) {
		this.listaSubTipoOperacion = listaSubTipoOperacion;
	}
	public String getStrMensajeError() {
		return strMensajeError;
	}
	public void setStrMensajeError(String strMensajeError) {
		this.strMensajeError = strMensajeError;
	}
	public Boolean getBlnNoExisteRequisito() {
		return blnNoExisteRequisito;
	}
	public void setBlnNoExisteRequisito(Boolean blnNoExisteRequisito) {
		this.blnNoExisteRequisito = blnNoExisteRequisito;
	}
	public Boolean getBlnVerBotonApoderado() {
		return blnVerBotonApoderado;
	}
	public void setBlnVerBotonApoderado(Boolean blnVerBotonApoderado) {
		this.blnVerBotonApoderado = blnVerBotonApoderado;
	}
	public Integer getIntBeneficiarioSeleccionar() {
		return intBeneficiarioSeleccionar;
	}
	public void setIntBeneficiarioSeleccionar(Integer intBeneficiarioSeleccionar) {
		this.intBeneficiarioSeleccionar = intBeneficiarioSeleccionar;
	}
	public BeneficiarioPrevision getBeneficiarioPrevisionSeleccionado() {
		return beneficiarioPrevisionSeleccionado;
	}
	public void setBeneficiarioPrevisionSeleccionado(
			BeneficiarioPrevision beneficiarioPrevisionSeleccionado) {
		this.beneficiarioPrevisionSeleccionado = beneficiarioPrevisionSeleccionado;
	}
	public BeneficiarioLiquidacion getBeneficiarioLiquidacionSeleccionado() {
		return beneficiarioLiquidacionSeleccionado;
	}
	public void setBeneficiarioLiquidacionSeleccionado(
			BeneficiarioLiquidacion beneficiarioLiquidacionSeleccionado) {
		this.beneficiarioLiquidacionSeleccionado = beneficiarioLiquidacionSeleccionado;
	}
	public String getStrMensajeErrorPorBeneficiario() {
		return strMensajeErrorPorBeneficiario;
	}
	public void setStrMensajeErrorPorBeneficiario(
			String strMensajeErrorPorBeneficiario) {
		this.strMensajeErrorPorBeneficiario = strMensajeErrorPorBeneficiario;
	}
	public Boolean getBlnBeneficiarioSinAutorizacion() {
		return blnBeneficiarioSinAutorizacion;
	}
	public void setBlnBeneficiarioSinAutorizacion(
			Boolean blnBeneficiarioSinAutorizacion) {
		this.blnBeneficiarioSinAutorizacion = blnBeneficiarioSinAutorizacion;
	}

	public List<BeneficiarioLiquidacion> getListaBeneficiarioLiquidacion() {
		return listaBeneficiarioLiquidacion;
	}

	public void setListaBeneficiarioLiquidacion(
			List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacion) {
		this.listaBeneficiarioLiquidacion = listaBeneficiarioLiquidacion;
	}

	public Boolean getBlnFiltrosGrabOk() {
		return blnFiltrosGrabOk;
	}

	public void setBlnFiltrosGrabOk(Boolean blnFiltrosGrabOk) {
		this.blnFiltrosGrabOk = blnFiltrosGrabOk;
	}

	public Boolean getBlnHabilitarObservacion() {
		return blnHabilitarObservacion;
	}

	public void setBlnHabilitarObservacion(Boolean blnHabilitarObservacion) {
		this.blnHabilitarObservacion = blnHabilitarObservacion;
	}

	public Boolean getBlnVerPanelCuentaBancariaDestino() {
		return blnVerPanelCuentaBancariaDestino;
	}

	public void setBlnVerPanelCuentaBancariaDestino(
			Boolean blnVerPanelCuentaBancariaDestino) {
		this.blnVerPanelCuentaBancariaDestino = blnVerPanelCuentaBancariaDestino;
	}

	public Boolean getBlnVerPanelCuentaBancariaBeneficiario() {
		return blnVerPanelCuentaBancariaBeneficiario;
	}

	public void setBlnVerPanelCuentaBancariaBeneficiario(
			Boolean blnVerPanelCuentaBancariaBeneficiario) {
		this.blnVerPanelCuentaBancariaBeneficiario = blnVerPanelCuentaBancariaBeneficiario;
	}

	public CuentaBancaria getCuentaBancariaBeneficiarioSeleccionada() {
		return cuentaBancariaBeneficiarioSeleccionada;
	}

	public void setCuentaBancariaBeneficiarioSeleccionada(
			CuentaBancaria cuentaBancariaBeneficiarioSeleccionada) {
		this.cuentaBancariaBeneficiarioSeleccionada = cuentaBancariaBeneficiarioSeleccionada;
	}

	public Persona getBeneficiarioSeleccionado() {
		return beneficiarioSeleccionado;
	}

	public void setBeneficiarioSeleccionado(Persona beneficiarioSeleccionado) {
		this.beneficiarioSeleccionado = beneficiarioSeleccionado;
	}

	public BigDecimal getBdDiferencialGirar() {
		return bdDiferencialGirar;
	}

	public void setBdDiferencialGirar(BigDecimal bdDiferencialGirar) {
		this.bdDiferencialGirar = bdDiferencialGirar;
	}

	public String getStrDiferencialGirarDescripcion() {
		return strDiferencialGirarDescripcion;
	}

	public void setStrDiferencialGirarDescripcion(
			String strDiferencialGirarDescripcion) {
		this.strDiferencialGirarDescripcion = strDiferencialGirarDescripcion;
	}	
}

/*
 *                       		value="#{item.personaApoderado.juridica.strRazonSocial}" LINE 168
 */
