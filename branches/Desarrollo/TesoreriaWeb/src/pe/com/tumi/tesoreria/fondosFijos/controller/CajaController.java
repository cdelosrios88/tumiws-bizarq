package pe.com.tumi.tesoreria.fondosFijos.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.cobranza.gestion.facade.GestionCobranzaFacadeRemote;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.common.util.AjusteMonto;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.ConvertirLetras;
import pe.com.tumi.common.util.DocumentoGeneral;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeRemote;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;
import pe.com.tumi.tesoreria.ingreso.domain.DepositoIngreso;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalleInterfaz;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;
import pe.com.tumi.tesoreria.ingreso.facade.IngresoFacadeLocal;

public class CajaController {

	protected static Logger log = Logger.getLogger(CajaController.class);
	
	private	PermisoFacadeRemote 		permisoFacade;
	private	PersonaFacadeRemote 		personaFacade;
	private	TablaFacadeRemote 			tablaFacade;
	private	GestionCobranzaFacadeRemote gestionCobranzaFacade;
	private	IngresoFacadeLocal 			ingresoFacade;
	private PlanillaFacadeRemote		planillaFacadeRemote;
	private BancoFacadeLocal			bancoFacade;
	private PlanCuentaFacadeRemote		planCuentaFacade;
	private EmpresaFacadeRemote			empresaFacade;
	private CierreDiarioArqueoFacadeRemote	cierreDiarioArqueoFacade;
	private EstructuraFacadeRemote		estructuraFacade;
	
	private	List<Ingreso>				listaIngreso;
	private List<Persona>				listaPersona;
	private List<DocumentoGeneral>		listaDocumentoPorAgregar;
	private List<DocumentoGeneral>		listaDocumentoAgregados;
	private List<IngresoDetalleInterfaz>listaIngresoDetalleInterfaz;
	private List<Tabla>					listaTablaDocumentoGeneral;
	private List<Persona>	 			listaPersonaFiltro;
	private List<Bancocuenta>			listaBancoCuenta;
	private	List<Ingreso>				listaIngresoDepositar;
	private List<Tabla>					listaTablaSucursal;
	private List<Bancofondo> 			listaBancoFondo;
	private List<Subsucursal>			listaSubsucursal;
	private List<Tabla>					lstPersonaRol;
	
	private Usuario			usuarioSesion;
	private Sucursal		sucursalUsuario;
	private Subsucursal		subsucursalUsuario;
	private NumberFormat 	formato;
	private Persona			personaSeleccionada;
	private	DocumentoGeneral	documentoGeneralSeleccionado;
	private	GestorCobranza	gestorCobranzaSeleccionado;
	private	GestorCobranza	gestorCobranzaTemp;
	private	ReciboManual	reciboManual;
	private Bancofondo		bancoFondoIngresar;
	private Ingreso			ingresoFiltro;
	private LibroDiario		libroDiario;
	private Modelo			modeloPlanillaEfectuada;
	private Archivo			archivoVoucher;
	private Sucursal		sucursalIngreso;
	private Subsucursal		subsucursalIngreso;
	
	private Integer		intTipoDocumentoValidar;
	private Integer		intFormaPagoValidar;
	private Integer		intMonedaValidar;
	private	BigDecimal	bdMontoIngresar;
	private	BigDecimal	bdMontoIngresarTotal;
	private	String		strMontoIngresarTotalDescripcion;
	private	String		strObservacion;
	private Integer 	intTipoPersona;
	private Integer 	intTipoBuscarPersona;
	private	String		strFiltroTextoPersona;
	private	Date		dtFechaActual;
	private	String		strListaPersonaRolUsar;
	private Integer		intTipoDocumentoAgregar;
	private	String		strNumeroCheque;
	private Integer		intTipoPersonaFiltro;
	private Integer		intTipoBusquedaPersonaFiltro;
	private String		strTextoPersonaFiltro;
	private Integer		intBancoSeleccionado;
	private Integer		intBancoCuentaSeleccionado;
	private BigDecimal	bdMontoDepositarTotal;
	private String		strMontoDepositarTotalDescripcion;
	private BigDecimal	bdMontoAjuste;
	private String		strMontoAjusteDescripcion;
	private BigDecimal	bdOtrosIngresos;
	private	String		strNumeroOperacion;
	
	private String		mensajeOperacion;
	private	Integer		SESION_IDEMPRESA;
//	private	Integer		SESION_IDUSUARIO;
	private	Integer		SESION_IDSUCURSAL;
	private	Integer		SESION_IDSUBSUCURSAL;
	private final Integer	BUSCAR_PERSONA = 1;
	private final Integer 	BUSCAR_GESTOR = 2;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean datosValidados;
	private boolean permiso;
	
	//jchavez 13.06.2014
	private String strMontoSaldoCaja;
	private Integer intTipoPersonaC;
	private Integer intPersonaRolC;
	private Boolean blnEsNaturalSocio;
	private Boolean deshabilitarTipoDocumento;
	private List<Tabla>	listaTablaModalidad;
	private List<Tabla>	listaTablaTipoSocio;
	private List<EstructuraDetalle>	listaEstructuraDetalle;
	private EstructuraComp entidadSeleccionada;
	private Boolean isDisabledRegistroDocumento;
	private Integer planillaEfectSelec;
	private List<Persona> listaGestorIngreso;
	
	public CajaController(){
		cargarUsuario();
		if(usuarioSesion!=null && poseePermiso()){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	public String getInicioPage() {
		cargarUsuario();		
		if(usuarioSesion!=null){
			limpiarFormulario();
			deshabilitarPanelInferior();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}		
		return "";
	}
	
	
	private void cargarUsuario(){
		usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		
//		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getPerfil().getId().getIntPersEmpresaPk();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		
		sucursalUsuario = usuarioSesion.getSucursal();
		subsucursalUsuario = usuarioSesion.getSubSucursal();	
	}
	
	private boolean poseePermiso(){
		permiso = Boolean.FALSE;
		try{			
			PermisoPerfilId permisoPerfilid = null;
			permisoPerfilid = new PermisoPerfilId();
			permisoPerfilid.setIntPersEmpresaPk(SESION_IDEMPRESA);
			permisoPerfilid.setIntIdTransaccion(Constante.TRANSACCION_FONDOSFIJOS_CAJA);
			permisoPerfilid.setIntIdPerfil(usuarioSesion.getPerfil().getId().getIntIdPerfil());
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			
			PermisoPerfil permisoPerfil = permisoFacade.getPermisoPerfilPorPk(permisoPerfilid);
			
			if(permisoPerfil != null && permisoPerfil.getId()!=null){
				permiso= Boolean.TRUE;
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}		
		return permiso;
	}
	
	private void cargarValoresIniciales(){
		try{
			//FACADE
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			gestionCobranzaFacade = (GestionCobranzaFacadeRemote) EJBFactory.getRemote(GestionCobranzaFacadeRemote.class);
			ingresoFacade = (IngresoFacadeLocal) EJBFactory.getLocal(IngresoFacadeLocal.class);
			planillaFacadeRemote = (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			cierreDiarioArqueoFacade = (CierreDiarioArqueoFacadeRemote) EJBFactory.getRemote(CierreDiarioArqueoFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			
			
			listaIngreso = new ArrayList<Ingreso>();
			ingresoFiltro = new Ingreso();
			intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
			blnEsNaturalSocio = true;
			deshabilitarTipoDocumento = false;
//			listaTablaDocumentoGeneral = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL));
			listaTablaModalidad = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
			listaTablaTipoSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			listaEstructuraDetalle = new ArrayList<EstructuraDetalle>();
			cargarUsuario();
			cargarListaTablaSucursal();
			//Formatear montos
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(','); 
			formato = new DecimalFormat("#,###.00",otherSymbols);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	
	public void habilitarPanelInferior(){
		try{
			datosValidados = Boolean.FALSE;
			intTipoDocumentoValidar = null;
			intFormaPagoValidar = null;
			cargarUsuario();
			
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
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
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {			
			
			//validaciones
			//valida si la caja asociada a la sucursal se encuentra cerrada
			log.info(sucursalIngreso);
			log.info(subsucursalIngreso);
			// 1. Valida Cierre Diario Arqueo, si existe un cierre diario para la sucursal que hace el giro, se procede a la siguiente validación
			if(cierreDiarioArqueoFacade.existeCierreDiarioArqueo(SESION_IDEMPRESA, SESION_IDSUCURSAL, SESION_IDSUBSUCURSAL)){
				// 1.1. Valida si la caja asociada a la sucursal se encuentra cerrada el dia anterior
				if(!cierreDiarioArqueoFacade.existeCierreDiaAnterior(SESION_IDEMPRESA, sucursalIngreso.getId().getIntIdSucursal(), subsucursalIngreso.getId().getIntIdSubSucursal())){
					mensaje = "La caja de la sucursal no se ha cerrado el dia anterior.";
					return;
				}
				// 1.2. Valida si la caja asociada a la sucursal se encuentra cerrada a la fecha actual
				if(cierreDiarioArqueoFacade.existeCierreDiaActual(SESION_IDEMPRESA, sucursalIngreso.getId().getIntIdSucursal(), subsucursalIngreso.getId().getIntIdSubSucursal())){
					mensaje = "La caja de la sucursal ya se ha cerrado para el dia de hoy.";
					return;
				}
			}
			
			if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA)){
				if(strObservacion==null || strObservacion.isEmpty()){
					mensaje = "Debe ingresar una observación.";
					return;
				}
				if(intFormaPagoValidar.equals(Constante.PARAM_T_PAGOINGRESO_CHEQUE) && (strNumeroCheque==null || strNumeroCheque.isEmpty())){
					mensaje = "Debe ingresar un número de cheque.";
					return;
				}
				if(listaDocumentoAgregados==null || listaDocumentoAgregados.isEmpty()){
					mensaje = "Debe agregar al menos un documento para el ingreso a caja.";
					return;
				}
				if(bdMontoIngresarTotal==null || bdMontoIngresarTotal.signum()<=0){
					mensaje = "El monto total a ingresar posee problemas, debe ser mayor a 0.";
					return;
				}
				//fin validaciones
				listaDocumentoAgregados = agregarDatosComplementarios(listaDocumentoAgregados);
				
				grabarGiro(listaDocumentoAgregados, bancoFondoIngresar, usuarioSesion);				
				
				mensaje = "Se registró correctamente el ingreso de caja.";
			
			}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO)){
				
				if(archivoVoucher==null){
					mensaje = "Debe de adjuntar un voucher.";
					return;
				}
				if(listaIngresoDepositar==null || listaIngresoDepositar.isEmpty()){
					mensaje = "Debe de agregar al menos un ingreso para depositar.";
					return;
				}
				if(strObservacion==null || strObservacion.isEmpty()){
					mensaje = "Debe ingresar una observación.";
					return;
				}
				if(intBancoSeleccionado.equals(new Integer(0))){
					mensaje = "Debe seleccionar un banco valido.";
					return;
				}
				if(intBancoCuentaSeleccionado.equals(new Integer(0))){
					mensaje = "Debe seleccionar una cuenta bancaria.";
					return;
				}
				if(strNumeroOperacion==null || strNumeroOperacion.isEmpty()){
					mensaje = "Debe ingresar un numero de operacion.";
					return;
				}
				
				for(Ingreso ingresoDepositar : listaIngresoDepositar){
					if(ingresoDepositar.getBdMontoDepositar()==null){
						mensaje = "Debe ingresar el monto a depositar del ingreso "+ingresoDepositar.getStrNumeroIngreso();return;
					}else if(ingresoDepositar.getBdMontoDepositar().compareTo(ingresoDepositar.getBdMontoDepositable())>0){
						mensaje = "El monto de deposito es mayor que el deposito de ingreso para "+ingresoDepositar.getStrNumeroIngreso();return;
					}else if (ingresoDepositar.getBdMontoDepositar().compareTo(BigDecimal.ZERO)<0) {
						mensaje = "El monto de deposito no puede ser negativo para "+ingresoDepositar.getStrNumeroIngreso();return;
					}
				}
				
				Bancofondo bancoFondoDepositar = obtenerBancoFondoSeleccionado();
				
				ingresoFacade.grabarDeposito(listaIngresoDepositar, usuarioSesion, bancoFondoDepositar, strObservacion, 
						archivoVoucher, bdOtrosIngresos, strNumeroOperacion, bancoFondoIngresar);
				
				calcularTotalDepositar();
				
				mensaje = "Se registró correctamente el depósito en caja.";
			}
			
			
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			buscar();
		} catch (Exception e) {
			mensaje = "Ocurrio un error durante el registro.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	private List<DocumentoGeneral> agregarDatosComplementarios(List<DocumentoGeneral> listaDocumentoGeneral) throws Exception{
		for(DocumentoGeneral documentoGeneral : listaDocumentoGeneral){
			if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA)){
				EfectuadoResumen efectuadoResumen = documentoGeneral.getEfectuadoResumen();
				efectuadoResumen.setStrObservacionIngreso(strObservacion);
				efectuadoResumen.setStrNumeroCheque(strNumeroCheque);
				efectuadoResumen.setGestorCobranza(gestorCobranzaSeleccionado);
				efectuadoResumen.setPersonaIngreso(personaSeleccionada);
				efectuadoResumen.setIntParaFormaPago(intFormaPagoValidar);
			}
		}
		return listaDocumentoGeneral;
	}
	
	public void grabarGiro(List<DocumentoGeneral> listaDocumentoGeneral, Bancofondo bancoFondo, Usuario usuario) throws Exception {
		
		Integer intTipoDocumentoGeneral = listaDocumentoGeneral.get(0).getIntTipoDocumento();
		//Solo pata el caso de documentos de planilla efectuada, todos los documentoGeneral se giraran en un solo egreso
		if(intTipoDocumentoGeneral.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA)){
			List<EfectuadoResumen> listaEfectuadoResumen = new ArrayList<EfectuadoResumen>();
			for(DocumentoGeneral documentoGeneral: listaDocumentoAgregados){
				listaEfectuadoResumen.add(documentoGeneral.getEfectuadoResumen());
			}
			Ingreso ingreso = ingresoFacade.generarIngresoEfectuadoResumen(listaEfectuadoResumen, bancoFondo, usuario);
			if(gestorCobranzaSeleccionado!=null){
				reciboManual.setGestorCobranza(gestorCobranzaSeleccionado);
				ingreso.setReciboManual(reciboManual);
			}
			ingreso = ingresoFacade.grabarIngresoEfectuadoResumen(ingreso, listaEfectuadoResumen);
		}
	}

	private void buscarPersonaFiltro(){
		try{
			listaPersonaFiltro = new ArrayList<Persona>();
			Persona persona = null;
			strTextoPersonaFiltro = strTextoPersonaFiltro.trim();
			
			if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
					Natural natural = new Natural();
					natural.setStrNombres(strTextoPersonaFiltro);
					List<Natural> listaNatural = personaFacade.getListaNaturalPorBusqueda(natural);
					for(Natural natu : listaNatural){
						listaPersonaFiltro.add(personaFacade.getPersonaPorPK(natu.getIntIdPersona()));
					}
				}else if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
					persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
							Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
							strTextoPersonaFiltro, 
							SESION_IDEMPRESA);
					if(persona!=null)listaPersonaFiltro.add(persona);
				}
				
			}else if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
					List<Juridica>listaJuridica = personaFacade.getListaJuridicaPorNombreComercial(strTextoPersonaFiltro);
					for(Juridica juridica : listaJuridica){
						listaPersonaFiltro.add(personaFacade.getPersonaPorPK(juridica.getIntIdPersona()));
					}
					
				}else if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)){
					persona = personaFacade.getPersonaPorRuc(strTextoPersonaFiltro);
					if(persona!=null)listaPersonaFiltro.add(persona);
				}
			}
			
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void buscar(){
		log.info("--buscar");
		try{
			ingresoFiltro.getId().setIntIdEmpresa(SESION_IDEMPRESA);
			
			listaPersonaFiltro = new ArrayList<Persona>();
			if(strTextoPersonaFiltro!=null && !strTextoPersonaFiltro.isEmpty()){
				buscarPersonaFiltro();
			}
			if(ingresoFiltro.getIntSucuIdSucursal().equals(new Integer(0))){
				ingresoFiltro.setIntSucuIdSucursal(null);
			}
			if(ingresoFiltro.getIntSudeIdSubsucursal().equals(new Integer(0))){
				ingresoFiltro.setIntSudeIdSubsucursal(null);
			}
			if(ingresoFiltro.getStrNumeroOperacion()!= null && ingresoFiltro.getStrNumeroOperacion().isEmpty()){
				ingresoFiltro.setStrNumeroOperacion(null);
			}
			log.info("nro op:"+ingresoFiltro.getStrNumeroOperacion());
			listaIngreso = ingresoFacade.buscarIngresoParaCaja(ingresoFiltro, listaPersonaFiltro);
			
			for(Ingreso ingreso : listaIngreso){
				if(ingreso.getIntPersPersonaGirado()!=null)
					ingreso.setPersona(devolverPersonaCargada(ingreso.getIntPersPersonaGirado()));
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verRegistro(ActionEvent event){
		try{
			limpiarFormulario();
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			datosValidados = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			
			Ingreso ingreso = (Ingreso)event.getComponent().getAttributes().get("item");
			intTipoDocumentoValidar = ingreso.getIntParaDocumentoGeneral();
			intMonedaValidar = ingreso.getIntParaTipoMoneda();
			dtFechaActual = ingreso.getDtFechaIngreso();
			
			
			if(ingreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA)){
				cargarIngreso(ingreso);			
			}else if(ingreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO)){
				cargarDeposito(ingreso);
			}
			
			Sucursal sucursal = new Sucursal();
			sucursal.getId().setIntPersEmpresaPk(ingreso.getId().getIntIdEmpresa());
			sucursal.getId().setIntIdSucursal(ingreso.getIntSucuIdSucursal());
			sucursalIngreso = empresaFacade.getSucursalPorPK(sucursal);
			
			subsucursalIngreso = empresaFacade.getSubSucursalPorIdSubSucursal(ingreso.getIntSudeIdSubsucursal());
			ocultarMensaje();
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Ocurrio un error en la selección del ingreso.");
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaTablaSucursal() throws Exception{
		List<Sucursal>listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
		//Ordena la sucursal alafabeticamente
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});
		
		listaTablaSucursal = new ArrayList<Tabla>();
		listaTablaSucursal = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
		Sucursal sucursal;
		Tabla tabla;
		for(Object o : listaSucursal){
			 sucursal = (Sucursal)o;
			 tabla = new Tabla();
			 tabla.setIntIdDetalle(sucursal.getId().getIntIdSucursal());
			 tabla.setStrDescripcion(sucursal.getJuridica().getStrSiglas());
			 listaTablaSucursal.add(tabla);
		}
	}
	
	public void seleccionarSucursal(){
		try{
			if(ingresoFiltro.getIntSucuIdSucursal().intValue()>0){
				listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(ingresoFiltro.getIntSucuIdSucursal());
			}else{
				listaSubsucursal = new ArrayList<Subsucursal>();
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarDeposito(Ingreso ingreso)throws Exception{
		listaIngresoDepositar = new ArrayList<Ingreso>();
		for(DepositoIngreso depositoIngreso : ingreso.getListaDepositoIngreso()){
			listaIngresoDepositar.add(depositoIngreso.getIngreso());
		}
		strObservacion = ingreso.getStrObservacion();
		archivoVoucher = ingreso.getArchivoVoucher();
		strNumeroOperacion = ingreso.getStrNumeroOperacion();
		calcularOtrosIngresos(ingreso);
		cargarNuevoDeposito(listaIngresoDepositar);
		intBancoSeleccionado = ingreso.getIntItemBancoFondo();
		seleccionarBanco();
		intBancoCuentaSeleccionado = ingreso.getIntItemBancoCuenta(); //jchavez - no pinta xq los registros no tienen la confiuracion de banco actual
//		seleccionarBanco();
		calcularTotalDepositar();
	
	}
	
	private void calcularOtrosIngresos(Ingreso ingreso)throws Exception{
		//Segun DepositoService.generarIngresoDetalle el ingresoDetalle de bdOtrosIngresos posee esa condicion
		for(IngresoDetalle ingresoDetalle : ingreso.getListaIngresoDetalle()){
			if(ingresoDetalle.getIntPersPersonaGirado().equals(ingreso.getBancoFondo().getIntPersonabancoPk())
			&& ingresoDetalle.getBdAjusteDeposito()==null){
				bdOtrosIngresos = ingresoDetalle.getBdMontoAbono();
			}
		}
	}
	
	private void cargarIngreso(Ingreso ingreso)throws Exception{
		IngresoDetalle ingresoDetalle = ingreso.getListaIngresoDetalle().get(0);
		
		libroDiario = ingresoFacade.obtenerLibroDiarioPorIngreso(ingreso);
		
		PersonaEmpresa personaEmpresa = new PersonaEmpresa();
		personaEmpresa.setId(new PersonaEmpresaPK());
		personaEmpresa.getId().setIntIdEmpresa(ingreso.getIntPersEmpresaGirado());
		personaEmpresa.getId().setIntIdPersona(ingreso.getIntPersPersonaGirado());
		personaEmpresa.setListaPersonaRol(personaFacade.getListaPersonaRolPorPKPersonaEmpresa(personaEmpresa.getId()));
		ingreso.getPersona().setPersonaEmpresa(personaEmpresa);
		personaSeleccionada = ingreso.getPersona();
		filtrarListaTablaDocumentoGeneral();
		
		
		if(ingreso.getStrNumeroCheque()==null  || ingreso.getStrNumeroCheque().isEmpty())
			intFormaPagoValidar = Constante.PARAM_T_PAGOINGRESO_EFECTIVO;
		else
			intFormaPagoValidar = Constante.PARAM_T_PAGOINGRESO_CHEQUE;
		
		intTipoDocumentoAgregar = ingresoDetalle.getIntParaDocumentoGeneral();
		strObservacion = ingreso.getStrObservacion();
		bancoFondoIngresar = bancoFacade.obtenerBancoFondoParaIngreso(usuarioSesion, intMonedaValidar);
		
		reciboManual = ingresoFacade.obtenerReciboManualPorIngreso(ingreso);
		if(reciboManual!=null){
			ReciboManualDetalle reciboManualDetalle = reciboManual.getListaReciboManualDetalle().get(0);
			gestorCobranzaSeleccionado = new GestorCobranza();
			gestorCobranzaSeleccionado.setPersona(devolverPersonaCargada(reciboManualDetalle.getIntPersPersonaGestor()));
		}
		
		
		
		if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA)){
			modeloPlanillaEfectuada = obtenerModeloPlanillaEfectuada();
			List<EfectuadoResumen> listaEfectuadoResumen = ingresoFacade.obtenerListaEfectuadoResumenPorIngreso(ingreso);
			for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
				log.info(efectuadoResumen);
				documentoGeneralSeleccionado = new DocumentoGeneral();
				documentoGeneralSeleccionado.setIntTipoDocumento(intTipoDocumentoAgregar);
				documentoGeneralSeleccionado.setStrNroDocumento(""+efectuadoResumen.getId().getIntItemEfectuadoResumen());
				documentoGeneralSeleccionado.setEfectuadoResumen(efectuadoResumen);
				documentoGeneralSeleccionado.setBdMonto(efectuadoResumen.getBdMontIngresar());
				documentoGeneralSeleccionado.setBdSubTotal(documentoGeneralSeleccionado.getBdMonto());
				bdMontoIngresar = efectuadoResumen.getBdMontIngresar();
				agregarDocumento();
			}			
		//soporte temporal para cierre de fondos
		}else{
			bdMontoIngresarTotal = ingreso.getBdMontoTotal();
			strMontoIngresarTotalDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoIngresarTotal, bancoFondoIngresar.getIntMonedaCod());
			
			Sucursal sucursal = new Sucursal();
			sucursal.getId().setIntPersEmpresaPk(ingreso.getId().getIntIdEmpresa());
			sucursal.getId().setIntIdSucursal(ingreso.getIntSucuIdSucursal());
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			sucursal = empresaFacade.getSucursalPorPK(sucursal);
			Subsucursal subsucursal = empresaFacade.getSubSucursalPorIdSubSucursal(ingreso.getIntSudeIdSubsucursal());
			
			IngresoDetalleInterfaz ingresoDetalleInterfaz = new IngresoDetalleInterfaz();
			ingresoDetalleInterfaz.setIntDocumentoGeneral(intTipoDocumentoAgregar);
			ingresoDetalleInterfaz.setStrNroDocumento(ingresoDetalle.getStrNumeroDocumento());
			ingresoDetalleInterfaz.setPersona(ingreso.getPersona());
			ingresoDetalleInterfaz.setSucursal(sucursal);
			ingresoDetalleInterfaz.setSubsucursal(subsucursal);
			ingresoDetalleInterfaz.setStrDescripcion(null);
			ingresoDetalleInterfaz.setBdSubtotal(bdMontoIngresarTotal);
			ingresoDetalleInterfaz.setBdMonto(bdMontoIngresarTotal);
			ingresoDetalleInterfaz.setLibroDiario(libroDiario);
			
			listaIngresoDetalleInterfaz = new ArrayList<IngresoDetalleInterfaz>();
			listaIngresoDetalleInterfaz.add(ingresoDetalleInterfaz);
		}
	}
	
	//modificado 18.06.2014 jchavez
	private Modelo obtenerModeloPlanillaEfectuada()throws Exception{
		List<Modelo> listaModelo = null;
		Modelo rtnModelo = null;
		try {
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_CANCELACIONPLANILLAEFECTUADA, 
					SESION_IDEMPRESA);
			if(listaModelo!=null && !listaModelo.isEmpty()){
				rtnModelo = listaModelo.get(0);
			}else{
				throw new Exception("No existe modelo contable para el periodo actual.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		return rtnModelo;
	}
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}

	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	private void limpiarFormulario(){
		strObservacion = "";
		bdMontoIngresar = null;
		bdMontoIngresarTotal = null;
		strMontoIngresarTotalDescripcion = "";
		gestorCobranzaSeleccionado = null;
		personaSeleccionada = null;
		strListaPersonaRolUsar = "";
		listaIngresoDetalleInterfaz = new ArrayList<IngresoDetalleInterfaz>();
		listaDocumentoAgregados = new ArrayList<DocumentoGeneral>();
		strNumeroCheque = "";
		libroDiario = null;
		archivoVoucher = null;
		listaIngresoDepositar = new ArrayList<Ingreso>();
		listaBancoFondo = new ArrayList<Bancofondo>();
		bdMontoDepositarTotal = null;
		strMontoDepositarTotalDescripcion = "";
		bdMontoAjuste = null;
		strMontoAjusteDescripcion = "";
		bdOtrosIngresos = null;
		strNumeroOperacion = "";
		listaBancoFondo = new ArrayList<Bancofondo>();
		listaBancoCuenta = new ArrayList<Bancocuenta>();
		intBancoSeleccionado = 0;
		intBancoCuentaSeleccionado = 0;
		entidadSeleccionada = null;
		listaEstructuraDetalle.clear();
		intTipoPersonaC = 0;
	}
	
	private void calcularTotalDepositar()throws Exception{
		bdMontoDepositarTotal = new BigDecimal(0);
		if(listaIngresoDepositar==null)
			return;
		
		for(Ingreso ingreso : listaIngresoDepositar){
			bdMontoDepositarTotal = bdMontoDepositarTotal.add(ingreso.getBdMontoDepositar());
		}
		
		if(bdOtrosIngresos!=null){
			bdMontoDepositarTotal = bdMontoDepositarTotal.add(bdOtrosIngresos);
		}

		BigDecimal bdMontoDepositarTotalAjustado = AjusteMonto.obtenerMontoAjustado(bdMontoDepositarTotal);
		bdMontoAjuste = bdMontoDepositarTotalAjustado.subtract(bdMontoDepositarTotal);
		
		bdMontoDepositarTotal = bdMontoDepositarTotalAjustado;
		strMontoDepositarTotalDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoDepositarTotal, intMonedaValidar);
		strMontoAjusteDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoAjuste.abs(), intMonedaValidar);
	}	
	
	public void validarDatos(){
		try{
			datosValidados = Boolean.FALSE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			limpiarFormulario();
			
			if(intMonedaValidar.equals(Constante.PARAM_T_TIPOMONEDA_DOLARES)){
				mostrarMensaje(Boolean.FALSE, "Actualmente no hay soporte para fondos de caja en dolares.");
				return;
			}
			
			bancoFondoIngresar = bancoFacade.obtenerBancoFondoParaIngreso(usuarioSesion, intMonedaValidar);
			if(bancoFondoIngresar==null || bancoFondoIngresar.getFondoDetalleUsar()==null){
				mostrarMensaje(Boolean.FALSE, "No existe un fondo de caja creado para "+
						usuarioSesion.getSucursal().getJuridica().getStrRazonSocial()+"-"+usuarioSesion.getSubSucursal().getStrDescripcion());
				return;
			}
			
			if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA)){				
				reciboManual = ingresoFacade.getReciboManualPorSubsucursal(usuarioSesion.getSubSucursal());			
				modeloPlanillaEfectuada = obtenerModeloPlanillaEfectuada();
				
			}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO)){				
				listaIngresoDepositar = ingresoFacade.obtenerListaIngresoParaDepositar(SESION_IDEMPRESA, intMonedaValidar, intFormaPagoValidar, usuarioSesion);				
				cargarNuevoDeposito(listaIngresoDepositar);				
			}			
			
			sucursalIngreso = usuarioSesion.getSucursal();
			subsucursalIngreso = usuarioSesion.getSubSucursal();
			
			dtFechaActual = obtenerFechaActual();
			datosValidados = Boolean.TRUE;
			habilitarGrabar = Boolean.TRUE;
			
			ocultarMensaje();
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la validación.");
			log.error(e.getMessage(),e);
		}
	}


	private void cargarNuevoDeposito(List<Ingreso> listaIngresoDepositar) throws Exception{
		
		if(listaIngresoDepositar != null && !listaIngresoDepositar.isEmpty()){
			for(Ingreso ingreso : listaIngresoDepositar){
				ingreso.setPersona(devolverPersonaCargada(ingreso.getIntPersPersonaGirado()));
			}
			
			//Ordenamos por IntItemIngreso
			Collections.sort(listaIngresoDepositar, new Comparator<Ingreso>(){
				public int compare(Ingreso uno, Ingreso otro) {
					return uno.getIntItemIngreso().compareTo(otro.getIntItemIngreso());
				}
			});
			

			Bancofondo bancoFondoTemp = new Bancofondo();
			bancoFondoTemp.getId().setIntEmpresaPk(SESION_IDEMPRESA);
			bancoFondoTemp.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_BANCO);
			bancoFondoTemp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			bancoFondoTemp.setIntMonedaCod(intMonedaValidar);					
			listaBancoFondo = bancoFacade.buscarBancoFondoParaDeposito(bancoFondoTemp, usuarioSesion);
		}
	}
	
	/**Ingresos**/
	
	public void abrirPopUpBuscarPersona(){
		try{
			listaPersona = new ArrayList<Persona>();
			strFiltroTextoPersona = "";
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			intTipoBuscarPersona = BUSCAR_PERSONA;
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void abrirPopUpBuscarEntidad(){
		try{
			listaEstructuraDetalle.clear();
			strFiltroTextoPersona = "";
//			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
//			intTipoBuscarPersona = BUSCAR_PERSONA;
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void abrirPopUpBuscarGestor(){
		try{
			listaPersona = new ArrayList<Persona>();
			strFiltroTextoPersona = "";
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			intTipoBuscarPersona = BUSCAR_GESTOR;
			gestorCobranzaTemp = null;
		}catch(Exception e){
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
	
	public void quitarGestorSeleccionado(){
		try{
			gestorCobranzaSeleccionado = null;
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	//JCHAVEZ 18.06.2014 - CASO JURIDICA / ENTIDAD
	public void buscarEntidad(){
		listaEstructuraDetalle.clear();
		Persona persona = null;
		List<EstructuraDetalle> lstEstructuraDetalle = null;
		try {
			strFiltroTextoPersona = strFiltroTextoPersona.trim();
			if(intTipoPersonaC.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				if (intPersonaRolC.equals(Constante.PARAM_T_TIPOROL_ENTIDAD)) {
					lstEstructuraDetalle = estructuraFacade.getListaEstructuraDetalleIngresos(SESION_IDSUCURSAL,SESION_IDSUBSUCURSAL);
					if (lstEstructuraDetalle!=null && !lstEstructuraDetalle.isEmpty()) {
						for (EstructuraDetalle estructuraDetalle : lstEstructuraDetalle) {
							persona = personaFacade.getPersonaPorPK(estructuraDetalle.getEstructura().getJuridica().getIntIdPersona());
							if (persona.getStrRuc().trim().equals("strFiltroTextoPersona")) {
								estructuraDetalle.getEstructura().getJuridica().setPersona(persona);
								listaEstructuraDetalle.add(estructuraDetalle);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	//JCHAVEZ 25.06.2014 - BUSCAR GESTOR
	public void buscarGestor(){
		
	}
	public void buscarPersona(){
		try{
			listaPersona = new ArrayList<Persona>();
			strFiltroTextoPersona = strFiltroTextoPersona.trim();
			Persona persona = null;
			if(intTipoPersonaC.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
						Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
						strFiltroTextoPersona, 
						SESION_IDEMPRESA);
			
			}else if(intTipoPersonaC.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				persona = personaFacade.getPersonaJuridicaYListaPersonaPorRucYIdEmpresa2(strFiltroTextoPersona,SESION_IDEMPRESA);
			}				
			
			if(intTipoBuscarPersona.equals(BUSCAR_PERSONA)){
				if(persona!=null){
					listaPersona.add(persona);
				}
				
			}else if(intTipoBuscarPersona.equals(BUSCAR_GESTOR)){
				if(persona!=null) gestorCobranzaTemp = gestionCobranzaFacade.getGestorCobranzaPorPersona(persona, SESION_IDEMPRESA);
				if(gestorCobranzaTemp!=null) listaPersona.add(gestorCobranzaTemp.getPersona());
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
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
	
	public void cargarListaPersonaRol(){
		log.info("-------------------------------------Debugging CajaController.getListaPersonaRol-------------------------------------");
		Integer intTipoPersona = null;
		List<Tabla> listPersonaRol = null;
		blnEsNaturalSocio = true;
		
		try {
			intTipoPersona = Integer.valueOf(getRequestParameter("pCboTipoPersonaC"));
			
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				listPersonaRol = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "H");
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				listPersonaRol = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "I");
				blnEsNaturalSocio = false;
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setLstPersonaRol(listPersonaRol);
	}
	
	public void cargarListaTipoDocumento(){
		log.info("-------------------------------------Debugging CajaController.getListaPersonaRol-------------------------------------");

		Integer intPersonaRol = null;
		List<Tabla> listTipoDocumentoTemp = null;
		List<Tabla> listTipoDocumento = new ArrayList<Tabla>();
		blnEsNaturalSocio = false;
		deshabilitarTipoDocumento = false;
		try {
			intPersonaRol = Integer.valueOf(getRequestParameter("pCboPersonaRolC"));
			
			if(intPersonaRol.equals(Constante.PARAM_T_TIPOROL_ENTIDAD)){
				listTipoDocumentoTemp = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTOINGRESO));
				if (listTipoDocumentoTemp!=null && !listTipoDocumentoTemp.isEmpty()) {
					for (Tabla tipoDoc : listTipoDocumentoTemp) {
						if (tipoDoc.getIntIdDetalle().equals(Constante.PARAM_T_TIPODOCUMENTOINGRESO_PLANILLAEFECTUADA)) {
							listTipoDocumento.add(tipoDoc);
							intTipoDocumentoAgregar = tipoDoc.getIntIdDetalle();
							deshabilitarTipoDocumento = true;
							break;
						}
					}
				}
			}else if (intPersonaRol.equals(Constante.PARAM_T_TIPOROL_SOCIO)) {
				blnEsNaturalSocio = true;
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListaTablaDocumentoGeneral(listTipoDocumentoTemp);
	}
	
	public void seleccionarEntidad(ActionEvent event){
		
		try {
			entidadSeleccionada = new EstructuraComp();
			entidadSeleccionada.setEstructuraDetalle((EstructuraDetalle)event.getComponent().getAttributes().get("item"));
			//Descripcion tipo socio
			for (Tabla tipoSocio : listaTablaTipoSocio) {
				if (entidadSeleccionada.getEstructuraDetalle().getIntParaTipoSocioCod().equals(tipoSocio.getIntIdDetalle())) {
					entidadSeleccionada.setStrTipoSocioConcatenado(tipoSocio.getStrDescripcion());
					break;
				}
			}
			//Descripcion modalidad
			for (Tabla modalidad : listaTablaModalidad) {
				if (entidadSeleccionada.getEstructuraDetalle().getIntParaModalidadCod().equals(modalidad.getIntIdDetalle())) {
					entidadSeleccionada.setStrModalidadConcatenado(modalidad.getStrDescripcion());
					break;
				}
			}
			//Razon social de la entidad
			entidadSeleccionada.setStrSucursalConcatenado(entidadSeleccionada.getEstructuraDetalle().getEstructura().getJuridica().getPersona().getStrRuc()+" - "+entidadSeleccionada.getEstructuraDetalle().getEstructura().getJuridica().getStrRazonSocial());
			log.info("entidad seleccionada:"+entidadSeleccionada.getStrSucursalConcatenado()+","+entidadSeleccionada.getStrTipoSocioConcatenado()+","+entidadSeleccionada.getStrModalidadConcatenado());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void seleccionarPersona(ActionEvent event){
		try{
			if(intTipoBuscarPersona.equals(BUSCAR_PERSONA)){
				personaSeleccionada = (Persona)event.getComponent().getAttributes().get("item");
				if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
					agregarNombreCompleto(personaSeleccionada);
					agregarDocumentoDNI(personaSeleccionada);
				}
				filtrarListaTablaDocumentoGeneral();
			
			}else if(intTipoBuscarPersona.equals(BUSCAR_GESTOR)){
				if(reciboManual==null){
					mostrarMensaje(Boolean.FALSE, "No existe un recibo manual configurado para la sucursal.");
					habilitarGrabar = Boolean.FALSE;
					return;
				}
				gestorCobranzaSeleccionado = gestorCobranzaTemp;
				agregarNombreCompleto(gestorCobranzaSeleccionado.getPersona());
				agregarDocumentoDNI(gestorCobranzaSeleccionado.getPersona());
				if(reciboManual.getIntNumeroActual().compareTo(reciboManual.getIntNumeroFinal())>0){
					mostrarMensaje(Boolean.FALSE, "La cantidad de numeros de recibos configurados pasó el límite.");
					habilitarGrabar = Boolean.FALSE;
					return;
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void seleccionarGestorIngreso(ActionEvent event){
		try {
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}	
	
	private void filtrarListaTablaDocumentoGeneral() throws Exception{
		listaTablaDocumentoGeneral = new ArrayList<Tabla>();
		List<PersonaRol> listaPersonaRolUsar = new ArrayList<PersonaRol>();
		strListaPersonaRolUsar = "";
		List<Tabla> listaTablaRolPermitidos = new ArrayList<Tabla>();
		
		if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
			listaTablaRolPermitidos = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "F");
			
		}else if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
			listaTablaRolPermitidos = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "G");
		}
		//listaTablaRolPermitidos = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOROL));		
		for(PersonaRol personaRol : personaSeleccionada.getPersonaEmpresa().getListaPersonaRol()){
			for(Tabla tablaRolPermitido : listaTablaRolPermitidos){
				if(personaRol.getId().getIntParaRolPk().equals(tablaRolPermitido.getIntIdDetalle())){
					listaPersonaRolUsar.add(personaRol);
					strListaPersonaRolUsar = strListaPersonaRolUsar + "/" + tablaRolPermitido.getStrDescripcion();
					break;
				}
			}
		}
		
		//quitamos el 1er caracter de la cadena "/"
		if(!strListaPersonaRolUsar.isEmpty()) strListaPersonaRolUsar = strListaPersonaRolUsar.substring(1);		
		
		for(PersonaRol personaRolUsar : listaPersonaRolUsar){
			listaTablaDocumentoGeneral.addAll(obtenerListaTipoDocumentoGeneral(personaRolUsar.getId().getIntParaRolPk()));
		}
	}
	
	private List<Tabla> obtenerListaTipoDocumentoGeneral(Integer intTipoRol){
		List<Tabla> listaTablaTipoDocumento = new ArrayList<Tabla>();
		try{			
			if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_PERSONAL)){
				listaTablaTipoDocumento  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "P");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_SOCIO)){
				listaTablaTipoDocumento  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "S");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_USUARIO)){
				listaTablaTipoDocumento  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "U");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_ENTIDADREGULADORA)){
				listaTablaTipoDocumento = new ArrayList<Tabla>();
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_ENTIDADDESCUENTOS)){
				listaTablaTipoDocumento = new ArrayList<Tabla>();
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_UNIDADEJECUTORA)){
				listaTablaTipoDocumento  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "E");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_PROVEEDOR)){
				listaTablaTipoDocumento  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "V");
				
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return listaTablaTipoDocumento;
	}
	
	public void abrirPopUpBuscarDocumento(){
		try{
			listaDocumentoPorAgregar = new ArrayList<DocumentoGeneral>();
//			Integer intIdPersona = personaSeleccionada.getIntIdPersona();
			
			log.info("intTipoDocumentoAgregar:"+intTipoDocumentoAgregar);
			
			if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_TIPODOCUMENTOINGRESO_PLANILLAEFECTUADA)){
//				List<EfectuadoResumen> listaEfectuadoResumen = planillaFacadeRemote.getListaEfectuadoResumenParaIngreso(personaSeleccionada, usuarioSesion);
				List<EfectuadoResumen> listaEfectuadoResumen = planillaFacadeRemote.getListaEfectuadoResumenParaIngreso2(entidadSeleccionada, usuarioSesion);
				int i = 0;
				for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
					DocumentoGeneral documentoGeneral = new DocumentoGeneral();
					documentoGeneral.setStrDescripcion(entidadSeleccionada.getEstructuraDetalle().getEstructura().getJuridica().getStrRazonSocial());
					documentoGeneral.setIntTipoDocumento(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
					documentoGeneral.setStrNroDocumento(""+efectuadoResumen.getId().getIntItemEfectuadoResumen());
					documentoGeneral.setBdMonto(efectuadoResumen.getBdMontoTotal());
					documentoGeneral.setBdMontoPagado(efectuadoResumen.getBdMontIngresar());
					documentoGeneral.setIntPeriodoPlanilla(efectuadoResumen.getIntPeriodoPlanilla());
					documentoGeneral.setIntPlanillaEfectSelec(i++);
					//Se asumen que todos los efectuadoResumen estan en soles
					documentoGeneral.setIntMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
					documentoGeneral.setEfectuadoResumen(efectuadoResumen);
					log.info(efectuadoResumen);
					listaDocumentoPorAgregar.add(documentoGeneral);
				}
			}
			
			listaDocumentoPorAgregar = ingresoFacade.filtrarDuplicidadDocumentoGeneralParaIngreso(listaDocumentoPorAgregar, 
					intTipoDocumentoAgregar, listaDocumentoAgregados);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	
	private String obtenerEtiquetaTipoDocumentoGeneral(Integer intTipoDocumento){
		for(Tabla tabla : listaTablaDocumentoGeneral){
			if(tabla.getIntIdDetalle().equals(intTipoDocumento)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	public void disableRegistroDocumento(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.disableTipoRegistro-------------------------------------");
		log.info("pRdoRegistroDocumento: "+getRequestParameter("pRdoRegistroDocumento"));
		String pRegistroDocumento = getRequestParameter("pRdoRegistroDocumento");
		int i = 0;
		int pos = 0;
		for (DocumentoGeneral docAgregado : listaDocumentoPorAgregar) {
			if (docAgregado.getIntPlanillaEfectSelec().equals(new Integer(pRegistroDocumento))) {
				docAgregado.setRbPlanillaEfectSelec(i);				
				docAgregado.setIsDisabledDocPlanillaEfect(false);
				pos = i;
				break;
			}
//			else {
//				docAgregado.setRbPlanillaEfectSelec(pos);
////				planillaEfectSelec = 0;
//				docAgregado.setIsDisabledDocPlanillaEfect(true);
//			}
			i++;
		}
		//deseleccionar otros registros
		for (DocumentoGeneral docAgregado : listaDocumentoPorAgregar) {
			if (!docAgregado.getIntPlanillaEfectSelec().equals(new Integer(pRegistroDocumento))) {
				docAgregado.setRbPlanillaEfectSelec(pos);
				docAgregado.setIsDisabledDocPlanillaEfect(true);
			}
		}

//		if(Integer.valueOf(pRegistroDocumento).equals(Constante.PARAM_T_TIPOREGISTROMODELO_VALORFIJO)){
//			setIsDisabledRegistroDocumento(true);
//		}else if(Integer.valueOf(pRegistroDocumento).equals(Constante.PARAM_T_TIPOREGISTROMODELO_TABLA)){
//			setIsDisabledRegistroDocumento(false);
//		}
	}
	public void seleccionarPlanillaEfectuada(){
		
	}
	
	private String obtenerEtiquetaTipoMoneda(Integer intTipoMoneda)throws Exception{
		Tabla tablaMoneda = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA), intTipoMoneda);
		if(tablaMoneda==null) return "";
		return tablaMoneda.getStrDescripcion();
	}
	
	public void seleccionarDocumento(ActionEvent event){
		try{
			documentoGeneralSeleccionado = (DocumentoGeneral)event.getComponent().getAttributes().get("item");			
			String strEtiqueta = "";
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA)){
				strEtiqueta = obtenerEtiquetaTipoDocumentoGeneral(documentoGeneralSeleccionado.getIntTipoDocumento()) 
					+ " - "+ documentoGeneralSeleccionado.getStrNroDocumento() + " - ";			
				strEtiqueta = strEtiqueta + " disponible : "
					+ formato.format(documentoGeneralSeleccionado.getEfectuadoResumen().getBdMontoDisponibelIngresar())
					+ " "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
			}else{
				strEtiqueta = obtenerEtiquetaTipoDocumentoGeneral(documentoGeneralSeleccionado.getIntTipoDocumento()) 
					+ " - "+ documentoGeneralSeleccionado.getStrNroDocumento() + " - ";			
				strEtiqueta = strEtiqueta + formato.format(documentoGeneralSeleccionado.getBdMonto())
					+ " "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
			}
						
			documentoGeneralSeleccionado.setStrEtiqueta(strEtiqueta);
			
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
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


	public void agregarDocumento(){
		try{
			if(documentoGeneralSeleccionado.getBdMonto()==null){
				mostrarMensaje(Boolean.FALSE,"El documento general seleccionado no posee un monto.");
				return;
			}
			if(bdMontoIngresar==null || bdMontoIngresar.compareTo(new BigDecimal(0))<=0){
				mostrarMensaje(Boolean.FALSE,"Debe de ingresar un monto adecuado.");
				return;
			}
			
			int intOrden = 1;
			if(bdMontoIngresarTotal!=null && bdMontoIngresarTotal.signum()>0){
				IngresoDetalleInterfaz ultimoIDI = (IngresoDetalleInterfaz)
					(listaIngresoDetalleInterfaz.get(listaIngresoDetalleInterfaz.size()-1));
				intOrden = ultimoIDI.getIntOrden() + 1;
			}else{
				bdMontoIngresarTotal = new BigDecimal(0);
			}
			
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA)){
				EfectuadoResumen efectuadoResumen = documentoGeneralSeleccionado.getEfectuadoResumen();
				//la validacion no se realiza cuando el metodo se usa para ver un ingreso ya registrado
				if(!deshabilitarNuevo &&(bdMontoIngresar.compareTo(efectuadoResumen.getBdMontoDisponibelIngresar())>0)){
					mostrarMensaje(Boolean.FALSE,"El monto ingresado es mayor al monto que esta disponible a ingresar.");
					return;
				}
				IngresoDetalleInterfaz ingresoDetalleInterfaz = new IngresoDetalleInterfaz();
				ingresoDetalleInterfaz.setIntDocumentoGeneral(documentoGeneralSeleccionado.getIntTipoDocumento());
				ingresoDetalleInterfaz.setStrNroDocumento(documentoGeneralSeleccionado.getStrNroDocumento());
				ingresoDetalleInterfaz.setPersona(personaSeleccionada);
				ingresoDetalleInterfaz.setSucursal(usuarioSesion.getSucursal());
				ingresoDetalleInterfaz.setSubsucursal(usuarioSesion.getSubSucursal());
				ingresoDetalleInterfaz.setStrDescripcion(modeloPlanillaEfectuada.getListModeloDetalle().get(0).getPlanCuenta().getStrDescripcion());
				ingresoDetalleInterfaz.setBdSubtotal(bdMontoIngresar);
				ingresoDetalleInterfaz.setBdMonto(bdMontoIngresar);
				ingresoDetalleInterfaz.setLibroDiario(libroDiario);
				efectuadoResumen.setBdMontIngresar(bdMontoIngresar);
				
				listaIngresoDetalleInterfaz.add(ingresoDetalleInterfaz);
			}			
			
			
			for(Object o : listaIngresoDetalleInterfaz){
				IngresoDetalleInterfaz ingresoDetalleInterfaz = (IngresoDetalleInterfaz)o;
				if(ingresoDetalleInterfaz.getIntOrden()==null){
					ingresoDetalleInterfaz.setIntOrden(intOrden);
					bdMontoIngresarTotal = bdMontoIngresarTotal.add(ingresoDetalleInterfaz.getBdSubtotal());
					intOrden++;
				}
			}
			strMontoIngresarTotalDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoIngresarTotal, bancoFondoIngresar.getIntMonedaCod());
			
			listaDocumentoAgregados.add(documentoGeneralSeleccionado);
			bdMontoIngresar = null;
			//Ordenamos por intOrden
			Collections.sort(listaIngresoDetalleInterfaz, new Comparator<IngresoDetalleInterfaz>(){
				public int compare(IngresoDetalleInterfaz uno, IngresoDetalleInterfaz otro) {
					return uno.getIntOrden().compareTo(otro.getIntOrden());
				}
			});
			documentoGeneralSeleccionado = null;
			ocultarMensaje();
		}catch(Exception e){
			mostrarMensaje(Boolean.FALSE,"Ocurrió un error al agregar el documento.");
			log.error(e.getMessage(), e);
		}
	}	

	public void seleccionarBanco() throws Exception{
		try{
			listaBancoCuenta = new ArrayList<Bancocuenta>();
			if(intBancoSeleccionado.equals(new Integer(0))){
				return;
			}

			String strEtiqueta = "";
			for(Bancofondo bancoFondo : listaBancoFondo){
				if(bancoFondo.getId().getIntItembancofondo().equals(intBancoSeleccionado)){
					for(Bancocuenta bancoCuenta : bancoFondo.getListaBancocuenta()){
						if(intMonedaValidar.equals(bancoCuenta.getCuentaBancaria().getIntMonedaCod())){
							strEtiqueta = bancoCuenta.getStrNombrecuenta()+" - "
											+bancoCuenta.getCuentaBancaria().getStrNroCuentaBancaria()+" - "
											+obtenerEtiquetaTipoMoneda(bancoCuenta.getCuentaBancaria().getIntMonedaCod());
							bancoCuenta.setStrEtiqueta(strEtiqueta);
							listaBancoCuenta.add(bancoCuenta);
						}
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public Bancofondo obtenerBancoFondoSeleccionado() throws Exception{
		Bancofondo bancoFondoSeleccionado = null;
		log.info("intBancoSeleccionado:"+intBancoSeleccionado);
		log.info("intBancoCuentaSeleccionado:"+intBancoCuentaSeleccionado);
		for(Bancofondo bancoFondo : listaBancoFondo){
			if(intBancoSeleccionado.equals(bancoFondo.getId().getIntItembancofondo())){
				bancoFondoSeleccionado = bancoFondo;
				for(Bancocuenta bancoCuenta : bancoFondo.getListaBancocuenta()){
					if(intBancoCuentaSeleccionado.equals((bancoCuenta.getId().getIntItembancocuenta()))){
						bancoFondoSeleccionado.setBancoCuentaSeleccionada(bancoCuenta);
						break;
					}
				}
				break;
			}
		}
		
		PlanCuentaId planCuentaId = new PlanCuentaId();
		planCuentaId.setIntEmpresaCuentaPk(bancoFondoSeleccionado.getBancoCuentaSeleccionada().getIntEmpresacuentaPk());
		planCuentaId.setIntPeriodoCuenta(bancoFondoSeleccionado.getBancoCuentaSeleccionada().getIntPeriodocuenta());
		planCuentaId.setStrNumeroCuenta(bancoFondoSeleccionado.getBancoCuentaSeleccionada().getStrNumerocuenta());
		
		bancoFondoSeleccionado.getBancoCuentaSeleccionada().setPlanCuenta(planCuentaFacade.getPlanCuentaPorPk(planCuentaId));
		return bancoFondoSeleccionado;
	}
	
	public void quitarIngresoDeposito(ActionEvent event){
		try{
			Ingreso ingreso = (Ingreso)event.getComponent().getAttributes().get("item");			
			listaIngresoDepositar.remove(ingreso);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void aceptarAdjuntarVoucher(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			archivoVoucher = fileUploadController.getArchivoVoucher();
			fileUploadController.setArchivoVoucher(null);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void quitarCartaPoder(){
		try{
			archivoVoucher = null;
			((FileUploadController)getSessionBean("fileUploadController")).setArchivoVoucher(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		return sesion.getAttribute(beanName);
	}
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
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
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}
	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
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
	public String getStrMontoIngresarTotalDescripcion() {
		return strMontoIngresarTotalDescripcion;
	}
	public void setStrMontoIngresarTotalDescripcion(String strMontoIngresarTotalDescripcion) {
		this.strMontoIngresarTotalDescripcion = strMontoIngresarTotalDescripcion;
	}
	public boolean isPermiso() {
		return permiso;
	}
	public void setPermiso(boolean permiso) {
		this.permiso = permiso;
	}
	public Integer getIntTipoBuscarPersona() {
		return intTipoBuscarPersona;
	}
	public void setIntTipoBuscarPersona(Integer intTipoBuscarPersona) {
		this.intTipoBuscarPersona = intTipoBuscarPersona;
	}
	public Integer getIntTipoDocumentoValidar() {
		return intTipoDocumentoValidar;
	}
	public void setIntTipoDocumentoValidar(Integer intTipoDocumentoValidar) {
		this.intTipoDocumentoValidar = intTipoDocumentoValidar;
	}
	public Integer getIntFormaPagoValidar() {
		return intFormaPagoValidar;
	}
	public void setIntFormaPagoValidar(Integer intFormaPagoValidar) {
		this.intFormaPagoValidar = intFormaPagoValidar;
	}
	public List<Ingreso> getListaIngreso() {
		return listaIngreso;
	}
	public void setListaIngreso(List<Ingreso> listaIngreso) {
		this.listaIngreso = listaIngreso;
	}
	public Date getDtFechaActual() {
		return dtFechaActual;
	}
	public void setDtFechaActual(Date dtFechaActual) {
		this.dtFechaActual = dtFechaActual;
	}
	public String getStrListaPersonaRolUsar() {
		return strListaPersonaRolUsar;
	}
	public void setStrListaPersonaRolUsar(String strListaPersonaRolUsar) {
		this.strListaPersonaRolUsar = strListaPersonaRolUsar;
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
	public GestorCobranza getGestorCobranzaSeleccionado() {
		return gestorCobranzaSeleccionado;
	}
	public void setGestorCobranzaSeleccionado(GestorCobranza gestorCobranzaSeleccionado) {
		this.gestorCobranzaSeleccionado = gestorCobranzaSeleccionado;
	}
	public BigDecimal getBdMontoIngresar() {
		return bdMontoIngresar;
	}
	public void setBdMontoIngresar(BigDecimal bdMontoIngresar) {
		this.bdMontoIngresar = bdMontoIngresar;
	}
	public BigDecimal getBdMontoIngresarTotal() {
		return bdMontoIngresarTotal;
	}
	public void setBdMontoIngresarTotal(BigDecimal bdMontoIngresarTotal) {
		this.bdMontoIngresarTotal = bdMontoIngresarTotal;
	}
	public ReciboManual getReciboManual() {
		return reciboManual;
	}
	public void setReciboManual(ReciboManual reciboManual) {
		this.reciboManual = reciboManual;
	}
	public List<Tabla> getListaTablaDocumentoGeneral() {
		return listaTablaDocumentoGeneral;
	}
	public void setListaTablaDocumentoGeneral(List<Tabla> listaTablaDocumentoGeneral) {
		this.listaTablaDocumentoGeneral = listaTablaDocumentoGeneral;
	}
	public List<IngresoDetalleInterfaz> getListaIngresoDetalleInterfaz() {
		return listaIngresoDetalleInterfaz;
	}
	public void setListaIngresoDetalleInterfaz(List<IngresoDetalleInterfaz> listaIngresoDetalleInterfaz) {
		this.listaIngresoDetalleInterfaz = listaIngresoDetalleInterfaz;
	}
	public List<DocumentoGeneral> getListaDocumentoPorAgregar() {
		return listaDocumentoPorAgregar;
	}
	public void setListaDocumentoPorAgregar(List<DocumentoGeneral> listaDocumentoPorAgregar) {
		this.listaDocumentoPorAgregar = listaDocumentoPorAgregar;
	}
	public Integer getIntMonedaValidar() {
		return intMonedaValidar;
	}
	public void setIntMonedaValidar(Integer intMonedaValidar) {
		this.intMonedaValidar = intMonedaValidar;
	}
	public Bancofondo getBancoFondoIngresar() {
		return bancoFondoIngresar;
	}
	public void setBancoFondoIngresar(Bancofondo bancoFondoIngresar) {
		this.bancoFondoIngresar = bancoFondoIngresar;
	}
	public String getStrNumeroCheque() {
		return strNumeroCheque;
	}
	public void setStrNumeroCheque(String strNumeroCheque) {
		this.strNumeroCheque = strNumeroCheque;
	}
	public Integer getIntTipoPersonaFiltro() {
		return intTipoPersonaFiltro;
	}
	public void setIntTipoPersonaFiltro(Integer intTipoPersonaFiltro) {
		this.intTipoPersonaFiltro = intTipoPersonaFiltro;
	}
	public Integer getIntTipoBusquedaPersonaFiltro() {
		return intTipoBusquedaPersonaFiltro;
	}
	public void setIntTipoBusquedaPersonaFiltro(Integer intTipoBusquedaPersonaFiltro) {
		this.intTipoBusquedaPersonaFiltro = intTipoBusquedaPersonaFiltro;
	}
	public String getStrTextoPersonaFiltro() {
		return strTextoPersonaFiltro;
	}
	public void setStrTextoPersonaFiltro(String strTextoPersonaFiltro) {
		this.strTextoPersonaFiltro = strTextoPersonaFiltro;
	}
	public Ingreso getIngresoFiltro() {
		return ingresoFiltro;
	}
	public void setIngresoFiltro(Ingreso ingresoFiltro) {
		this.ingresoFiltro = ingresoFiltro;
	}
	public LibroDiario getLibroDiario() {
		return libroDiario;
	}
	public void setLibroDiario(LibroDiario libroDiario) {
		this.libroDiario = libroDiario;
	}
	public Archivo getArchivoVoucher() {
		return archivoVoucher;
	}
	public void setArchivoVoucher(Archivo archivoVoucher) {
		this.archivoVoucher = archivoVoucher;
	}
	public Integer getIntBancoSeleccionado() {
		return intBancoSeleccionado;
	}
	public void setIntBancoSeleccionado(Integer intBancoSeleccionado) {
		this.intBancoSeleccionado = intBancoSeleccionado;
	}
	public List<Bancocuenta> getListaBancoCuenta() {
		return listaBancoCuenta;
	}
	public void setListaBancoCuenta(List<Bancocuenta> listaBancoCuenta) {
		this.listaBancoCuenta = listaBancoCuenta;
	}
	public Integer getIntBancoCuentaSeleccionado() {
		return intBancoCuentaSeleccionado;
	}
	public void setIntBancoCuentaSeleccionado(Integer intBancoCuentaSeleccionado) {
		this.intBancoCuentaSeleccionado = intBancoCuentaSeleccionado;
	}
	public List<Ingreso> getListaIngresoDepositar() {
		return listaIngresoDepositar;
	}
	public void setListaIngresoDepositar(List<Ingreso> listaIngresoDepositar) {
		this.listaIngresoDepositar = listaIngresoDepositar;
	}
	public BigDecimal getBdMontoDepositarTotal() {
		return bdMontoDepositarTotal;
	}
	public void setBdMontoDepositarTotal(BigDecimal bdMontoDepositarTotal) {
		this.bdMontoDepositarTotal = bdMontoDepositarTotal;
	}
	public String getStrMontoDepositarTotalDescripcion() {
		return strMontoDepositarTotalDescripcion;
	}
	public void setStrMontoDepositarTotalDescripcion(String strMontoDepositarTotalDescripcion) {
		this.strMontoDepositarTotalDescripcion = strMontoDepositarTotalDescripcion;
	}
	public BigDecimal getBdMontoAjuste() {
		return bdMontoAjuste;
	}
	public void setBdMontoAjuste(BigDecimal bdMontoAjuste) {
		this.bdMontoAjuste = bdMontoAjuste;
	}
	public String getStrMontoAjusteDescripcion() {
		return strMontoAjusteDescripcion;
	}
	public void setStrMontoAjusteDescripcion(String strMontoAjusteDescripcion) {
		this.strMontoAjusteDescripcion = strMontoAjusteDescripcion;
	}
	public BigDecimal getBdOtrosIngresos() {
		return bdOtrosIngresos;
	}
	public void setBdOtrosIngresos(BigDecimal bdOtrosIngresos) {
		this.bdOtrosIngresos = bdOtrosIngresos;
	}
	public String getStrNumeroOperacion() {
		return strNumeroOperacion;
	}
	public void setStrNumeroOperacion(String strNumeroOperacion) {
		this.strNumeroOperacion = strNumeroOperacion;
	}	
	public Sucursal getSucursalIngreso() {
		return sucursalIngreso;
	}
	public void setSucursalIngreso(Sucursal sucursalIngreso) {
		this.sucursalIngreso = sucursalIngreso;
	}
	public Subsucursal getSubsucursalIngreso() {
		return subsucursalIngreso;
	}
	public void setSubsucursalIngreso(Subsucursal subsucursalIngreso) {
		this.subsucursalIngreso = subsucursalIngreso;
	}
	public List<Tabla> getListaTablaSucursal() {
		return listaTablaSucursal;
	}
	public void setListaTablaSucursal(List<Tabla> listaTablaSucursal) {
		this.listaTablaSucursal = listaTablaSucursal;
	}
	public List<Subsucursal> getListaSubsucursal() {
		return listaSubsucursal;
	}
	public void setListaSubsucursal(List<Subsucursal> listaSubsucursal) {
		this.listaSubsucursal = listaSubsucursal;
	}
	public List<Bancofondo> getListaBancoFondo() {
		return listaBancoFondo;
	}
	public void setListaBancoFondo(List<Bancofondo> listaBancoFondo) {
		this.listaBancoFondo = listaBancoFondo;
	}
	public String getStrMontoSaldoCaja() {
		return strMontoSaldoCaja;
	}
	public void setStrMontoSaldoCaja(String strMontoSaldoCaja) {
		this.strMontoSaldoCaja = strMontoSaldoCaja;
	}
	public List<Tabla> getLstPersonaRol() {
		return lstPersonaRol;
	}
	public void setLstPersonaRol(List<Tabla> lstPersonaRol) {
		this.lstPersonaRol = lstPersonaRol;
	}
	public Integer getIntTipoPersonaC() {
		return intTipoPersonaC;
	}
	public void setIntTipoPersonaC(Integer intTipoPersonaC) {
		this.intTipoPersonaC = intTipoPersonaC;
	}
	public Integer getIntPersonaRolC() {
		return intPersonaRolC;
	}
	public void setIntPersonaRolC(Integer intPersonaRolC) {
		this.intPersonaRolC = intPersonaRolC;
	}
	public Boolean getBlnEsNaturalSocio() {
		return blnEsNaturalSocio;
	}
	public void setBlnEsNaturalSocio(Boolean blnEsNaturalSocio) {
		this.blnEsNaturalSocio = blnEsNaturalSocio;
	}
	public Boolean getDeshabilitarTipoDocumento() {
		return deshabilitarTipoDocumento;
	}
	public void setDeshabilitarTipoDocumento(Boolean deshabilitarTipoDocumento) {
		this.deshabilitarTipoDocumento = deshabilitarTipoDocumento;
	}
	public List<Tabla> getListaTablaModalidad() {
		return listaTablaModalidad;
	}
	public void setListaTablaModalidad(List<Tabla> listaTablaModalidad) {
		this.listaTablaModalidad = listaTablaModalidad;
	}
	public List<Tabla> getListaTablaTipoSocio() {
		return listaTablaTipoSocio;
	}
	public void setListaTablaTipoSocio(List<Tabla> listaTablaTipoSocio) {
		this.listaTablaTipoSocio = listaTablaTipoSocio;
	}
	public List<EstructuraDetalle> getListaEstructuraDetalle() {
		return listaEstructuraDetalle;
	}
	public void setListaEstructuraDetalle(
			List<EstructuraDetalle> listaEstructuraDetalle) {
		this.listaEstructuraDetalle = listaEstructuraDetalle;
	}
	public EstructuraComp getEntidadSeleccionada() {
		return entidadSeleccionada;
	}
	public void setEntidadSeleccionada(EstructuraComp entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}
	public Boolean getIsDisabledRegistroDocumento() {
		return isDisabledRegistroDocumento;
	}
	public void setIsDisabledRegistroDocumento(Boolean isDisabledRegistroDocumento) {
		this.isDisabledRegistroDocumento = isDisabledRegistroDocumento;
	}
	public Integer getPlanillaEfectSelec() {
		return planillaEfectSelec;
	}
	public void setPlanillaEfectSelec(Integer planillaEfectSelec) {
		this.planillaEfectSelec = planillaEfectSelec;
	}
	public List<Persona> getListaGestorIngreso() {
		return listaGestorIngreso;
	}
	public void setListaGestorIngreso(List<Persona> listaGestorIngreso) {
		this.listaGestorIngreso = listaGestorIngreso;
	}
}