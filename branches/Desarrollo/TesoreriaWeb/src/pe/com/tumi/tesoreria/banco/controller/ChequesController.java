package pe.com.tumi.tesoreria.banco.controller;

import java.math.BigDecimal;
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

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.ConvertirLetras;
import pe.com.tumi.common.util.DocumentoGeneral;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.legalizacion.domain.PagoLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.facade.LegalizacionFacadeRemote;
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
//import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp2;
import pe.com.tumi.servicio.prevision.facade.LiquidacionFacadeRemote;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
//import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp2;
import pe.com.tumi.servicio.solicitudPrestamo.facade.PrestamoFacadeRemote;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;

public class ChequesController {

	protected static Logger log = Logger.getLogger(ChequesController.class);
	
	private EmpresaFacadeRemote 		empresaFacade;
	private PersonaFacadeRemote 		personaFacade;
	private TablaFacadeRemote 			tablaFacade;
	private BancoFacadeLocal 			bancoFacade;
	private EgresoFacadeLocal 			egresoFacade;
	private LegalizacionFacadeRemote 	legalizacionFacade;
	private PrestamoFacadeRemote 		prestamoFacade;
	private PrevisionFacadeRemote		previsionFacade;
	private LiquidacionFacadeRemote		liquidacionFacade;
	private LibroDiarioFacadeRemote 	libroDiarioFacade;
	
	private	List<Egreso>	listaEgreso;
	private	List<Tabla>		listaTipoDocumento;	
	private	List<Sucursal>		listaSucursal;
	private	List<Subsucursal>		listaSubsucursal;
	private	List<Bancocuenta>	listaBancoCuenta;
	private	List<Tabla>	listaMoneda;
	private	List<Tabla>	listaTablaDocumentoGeneral;
	/** evisar donde se usa **/
//	private	List<Tabla>	listaTablaTipoCredito;
//	private	List<Tabla>		listaSubTipoOperacion;
	private List<Bancofondo>	listaBancoFondo;
	private List<EgresoDetalleInterfaz>	listaEgresoDetalleInterfazAgregado;
	private List<BeneficiarioPrevision> listaBeneficiarioPrevision;
	private List<Persona>	listaBeneficiarioPersona;
	private List<Tabla>		listaTipoVinculo;
	private List<Persona>	listaPersonaFiltro;
	
	private	List<Persona>	listaPersona;	
	private List<Tabla>		listaPersonaRolUsar;
	private List<DocumentoGeneral>	listaDocumentoAgregados;
	private List<DocumentoGeneral>	listaDocumentoPorAgregar;
	private List<Tabla>		listaTipoDocumentoTerceros;
	
	private Archivo		archivoCartaPoder;
	private Persona		personaApoderado;
	private Egreso		egresoFiltro;
	private Sucursal	sucursalUsuario;
	private	Subsucursal	subsucursalUsuario;
	private	Persona 	personaSeleccionada;
	private LibroDiario	libroDiario;	
	
	private Sucursal	sucursalSedeCentral;
	private	Subsucursal	subsucursalSedeCentral;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private String 		mensajePopUp;
	private Integer		intTipoDocumentoValidar;
	private	Integer		intBancoSeleccionado;
	private Integer		intBancoCuentaSeleccionado;
	private	Integer		intNumeroTransferencia;
	private	BigDecimal	bdMontoGirar;
	private	Integer		intTipoSubOperacion;
	private	String		strObservacion;
	private	Date		dtDesdeFiltro;
	private	Date		dtHastaFiltro;
	private	String		strMontoGirar;
	private	String		strMontoGirarDescripcion;
	private Integer 	intBeneficiarioSeleccionar;
	/***/
	private	Integer		intTipoPersona;
	private	String		strFiltroTextoPersona;
	private	Integer		intTipoDocumentoAgregar;
	/***/
	private	Integer		intTipoPersonaFiltro;
	private	Integer		intTipoBusquedaPersona;
	private String		strTextoPersonaFiltro;
	
	private	Integer		intTipoBuscarPersona;
	private	Integer		intTipoRol;
	private	DocumentoGeneral	documentoGeneralSeleccionado;
	private	NumberFormat 		formato;
	private Date		dtFechaActual;
	private Integer		intNumeroCheque;
	
	private	Integer		EMPRESA_USUARIO;
	//revisar donde se usa
//	private	Integer		PERSONA_USUARIO;
	private Integer		ID_SUCURSAL_SEDECENTRAL = 59;
	private	Integer		ID_SUBSUCURSAL_SEDECENTRAL = 68;
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
	
	//
	private List<RequisitoCreditoComp> lstRequisitoCreditoComp;
	private List<RequisitoCredito> lstRequisitoCredito;
//	private List<RequisitoPrevision> lstRequisitoPrevision;
//	private List<RequisitoPrevisionComp2> lstRequisitoPrevisionComp;
//	private List<RequisitoLiquidacion> lstRequisitoLiquidacion;
//	private List<RequisitoLiquidacionComp2> lstRequisitoLiquidacionComp;
	private Archivo archivoAdjuntoGiro;
	private List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacion;
	
	//jchavez 30.04.2014
	private Boolean blnNoExisteRequisito;
	private List<RequisitoCreditoComp> lstRequisitoCreditoCmp;
	private String strMensajeError;
	private Boolean blnVerBotonApoderado;
	
	//jchavez 12.05.2014
	private List<RequisitoPrevision> lstRequisitoPrevision;
	private List<RequisitoPrevisionComp> lstRequisitoPrevisionComp;
	private BeneficiarioPrevision beneficiarioPrevisionSeleccionado;
	private String strMensajeErrorPorBeneficiario;
	private List<RequisitoLiquidacion> lstRequisitoLiquidacion;
	private List<RequisitoLiquidacionComp> lstRequisitoLiquidacionComp;
	private BeneficiarioLiquidacion beneficiarioLiquidacionSeleccionado;
	
	private Boolean blnBeneficiarioSinAutorizacion;
	private Boolean blnHabilitarObservacion;
	private ConceptoFacadeRemote conceptoFacade;
	
	private Integer intCuenta;
	
	public ChequesController(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.TRANSACCION_BANCOS_CHEQUES);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
	}

	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");		
//		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();	
	}
	
	private void cargarValoresIniciales(){
		try{
			listaEgreso = new ArrayList<Egreso>();
			
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			prestamoFacade = (PrestamoFacadeRemote) EJBFactory.getRemote(PrestamoFacadeRemote.class);
			previsionFacade = (PrevisionFacadeRemote) EJBFactory.getRemote(PrevisionFacadeRemote.class);
			liquidacionFacade = (LiquidacionFacadeRemote) EJBFactory.getRemote(LiquidacionFacadeRemote.class);
			egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			legalizacionFacade = (LegalizacionFacadeRemote) EJBFactory.getRemote(LegalizacionFacadeRemote.class);
			libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			listaTipoVinculo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOVINCULO));			
			listaTipoDocumento = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "C");
			listaMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
//			listaSubTipoOperacion = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPODESUBOPERACION), "A");
			listaTablaDocumentoGeneral = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL)); 
//			listaTablaTipoCredito = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPO_CREDITO));
			cargarListaSucursal();
			sucursalUsuario = usuario.getSucursal();
			subsucursalUsuario = usuario.getSubSucursal();
			egresoFiltro = new Egreso();
			intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			cargarSedeCentral();
			//Dar formato de comas y punto a BigDecimal
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(','); 
			formato = new DecimalFormat("#,###.00",otherSymbols);
			//
//			lstRequisitoCredito = new ArrayList<RequisitoCredito>();
//			lstRequisitoPrevision = new ArrayList<RequisitoPrevision>();
//			lstRequisitoLiquidacion = new ArrayList<RequisitoLiquidacion>();
			documentoGeneralSeleccionado = new DocumentoGeneral();
			blnVerBotonApoderado = true;
			
			//
			lstRequisitoCredito = new ArrayList<RequisitoCredito>();
			lstRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
			lstRequisitoPrevision = new ArrayList<RequisitoPrevision>();
			lstRequisitoPrevisionComp = new ArrayList<RequisitoPrevisionComp>();
			lstRequisitoLiquidacion = new ArrayList<RequisitoLiquidacion>();
			lstRequisitoLiquidacionComp = new ArrayList<RequisitoLiquidacionComp>();
			listaEgresoDetalleInterfazAgregado = new ArrayList<EgresoDetalleInterfaz>();
			blnBeneficiarioSinAutorizacion = false;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscar(){
		log.info("--buscar");
		try{
			strMensajeError = "";
			strMensajeErrorPorBeneficiario = "";
			
			listaPersonaFiltro = new ArrayList<Persona>();
			if(strTextoPersonaFiltro!=null && !strTextoPersonaFiltro.isEmpty()){
				buscarPersonaFiltro();
			}
			
			egresoFiltro.getId().setIntPersEmpresaEgreso(EMPRESA_USUARIO);
			listaEgreso = egresoFacade.buscarEgresoParaCaja(listaPersonaFiltro, egresoFiltro, dtDesdeFiltro, dtHastaFiltro);
			
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
	
	public void grabar(){
		log.info("--grabar");
		log.info("observacion"+strObservacion);
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			//validaciones
			if(intNumeroCheque==null){
				mensaje = "Debe de ingresar un número de cheque.";
				return;
			}
			if(intTipoDocumentoAgregar==null || intTipoDocumentoAgregar.equals(new Integer(0))){
				mensaje = "Debe seleccionar un tipo de documento a agregar.";
				return;
			}
			if(listaDocumentoAgregados==null || listaDocumentoAgregados.isEmpty()){
				mensaje = "Debe agregar al menos un documento.";
				return;
			}
			if(listaEgresoDetalleInterfazAgregado==null || listaEgresoDetalleInterfazAgregado.isEmpty()){
				mensaje = "Debe agregar al menos un documento.";
				return;
			}
			if(strObservacion==null || strObservacion.isEmpty()){
				mensaje = "Debe ingresar una observación.";
				return;
			}
			if(personaApoderado != null && archivoCartaPoder==null){
				mensaje = "Debe asignar una Carta de Poder al apoderado.";
				return;
			}
			//fin validaciones
			
			agregarStrGlosa(listaDocumentoAgregados);
			
			exito = grabarGiro(listaDocumentoAgregados, usuario);
			if (exito) {
				mensaje = "Se registró correctamente el giro mediante cheques.";
				habilitarGrabar = Boolean.FALSE;
				deshabilitarNuevo = Boolean.TRUE;
				buscar();
			}else {
				mensaje = "Ocurrio un error durante el giro mediante cheques.";
			}
			blnHabilitarObservacion = true;
//			exito = Boolean.TRUE;
//			habilitarGrabar = Boolean.FALSE;
//			deshabilitarNuevo = Boolean.TRUE;
//			buscar();
		} catch (Exception e) {
			mensaje = "Ocurrio un error durante el giro mediante cheques.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	public Boolean grabarGiro(List<DocumentoGeneral> listaDocumentoGeneral, Usuario usuario) throws Exception {
		Bancocuenta bancoCuentaSeleccionado = null;
		Boolean exito = false;
		Calendar calen = Calendar.getInstance();
		Integer periodoCta = calen.get(Calendar.YEAR);
		try {
			for(Bancocuenta bancoCuenta : listaBancoCuenta){
				if(bancoCuenta.getId().getIntItembancocuenta().equals(intBancoCuentaSeleccionado)){
					if (bancoCuenta.getIntPeriodocuenta().equals(periodoCta)) {
						bancoCuentaSeleccionado = bancoCuenta;
						break;
					}
				}
			}
			
			Integer intTipoDocumentoGeneral = listaDocumentoGeneral.get(0).getIntTipoDocumento();
			//Solo pata el caso de documentos de movilidad, todos los documentoGeneral se giraran en un solo egreso
			if(intTipoDocumentoGeneral.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				List<Movilidad> listaMovilidad = new ArrayList<Movilidad>();
				for(DocumentoGeneral documentoGeneral: listaDocumentoAgregados){
					listaMovilidad.add(documentoGeneral.getMovilidad());
				}
				Egreso egreso = egresoFacade.generarEgresoMovilidadCheque(listaMovilidad, bancoCuentaSeleccionado, usuario, intNumeroCheque, 
						intTipoDocumentoValidar);
				egresoFacade.grabarGiroMovilidad(egreso, listaMovilidad);
			}		

			
			for(DocumentoGeneral documentoGeneral : listaDocumentoGeneral){
				if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){				
					
				}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
					ExpedienteCredito expedienteCredito = documentoGeneral.getExpedienteCredito();
					Egreso egreso = prestamoFacade.generarEgresoPrestamoCheque(expedienteCredito, bancoCuentaSeleccionado, usuario, intNumeroCheque, 
							intTipoDocumentoValidar);
					//jchavez 24.06.2014
					expedienteCredito = egreso.getExpedienteCredito();
					expedienteCredito.setEgreso(egreso);
					prestamoFacade.grabarGiroPrestamoPorTesoreria(expedienteCredito);
					log.info("Se realizó la grabación de forma satisfactoria");
					
				}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
					|| documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
					|| documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
					ExpedientePrevision expedientePrevision = documentoGeneral.getExpedientePrevision();
					Egreso egreso = previsionFacade.generarEgresoPrevisionCheque(expedientePrevision, bancoCuentaSeleccionado, usuario, intNumeroCheque, 
							intTipoDocumentoValidar);
					expedientePrevision.setEgreso(egreso);
					previsionFacade.grabarGiroPrevisionPorTesoreria(expedientePrevision);
					
				}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
					ExpedienteLiquidacion expedienteLiquidacion = documentoGeneral.getExpedienteLiquidacion();
					expedienteLiquidacion.setBeneficiarioLiquidacionGirar(new BeneficiarioLiquidacion());
					expedienteLiquidacion.getBeneficiarioLiquidacionGirar().setPersonaApoderado(personaApoderado);
					expedienteLiquidacion.getBeneficiarioLiquidacionGirar().setArchivoCartaPoder(archivoCartaPoder);
					Egreso egreso = liquidacionFacade.generarEgresoLiquidacionCheque(expedienteLiquidacion, bancoCuentaSeleccionado, usuario, intNumeroCheque, 
							intTipoDocumentoValidar);
					expedienteLiquidacion.setEgreso(egreso);
					liquidacionFacade.grabarGiroLiquidacionPorTesoreria(expedienteLiquidacion);
					log.info("La grabacion fue exitosa");
				}
			}
			exito = true;
		} catch (Exception e) {
			log.error("Error en grabarGiro ---> "+e.getMessage());
		}
		return exito;
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
			}
		}
		return listaDocumentoGeneral;
	}
	
	private LibroDiario obtenerLibroDiario(Egreso egreso) throws Exception{
		LibroDiarioId libroDiarioId = new LibroDiarioId();
		libroDiarioId.setIntPersEmpresaLibro(egreso.getIntPersEmpresaLibro());
		libroDiarioId.setIntContPeriodoLibro(egreso.getIntContPeriodoLibro());
		libroDiarioId.setIntContCodigoLibro(egreso.getIntContCodigoLibro());
		return libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);		
	}
	
	public void verRegistro(ActionEvent event){
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try{
			limpiarFormulario();
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			datosValidados = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			cargarListaBanco();
			
			Egreso egreso = (Egreso)event.getComponent().getAttributes().get("item");
			EgresoDetalle egresoDetalle = egreso.getListaEgresoDetalle().get(0);
			libroDiario = obtenerLibroDiario(egreso);
			
			log.info(egreso);
			dtFechaActual = egreso.getDtFechaEgreso();
			/*bdMontoGirar = egreso.getBdMontoTotal();
			strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, egresoDetalle.getIntParaTipoMoneda());
			*/strObservacion = egreso.getStrObservacion();
			personaSeleccionada = egreso.getPersonaApoderado();
			intTipoDocumentoValidar = egreso.getIntParaDocumentoGeneral();
			intNumeroCheque = egreso.getIntNumeroCheque();
			intTipoDocumentoAgregar = egresoDetalle.getIntParaDocumentoGeneral();
			intBancoSeleccionado = egreso.getBancoCuenta().getBancofondo().getIntBancoCod();
			seleccionarBanco();
			intBancoCuentaSeleccionado = egreso.getBancoCuenta().getId().getIntItembancocuenta();
			
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
				validarNoExisteRequisitoCredito();
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
				validarNoExisteRequisitoAdjuntoGiroPrevision();
				agregarDocumento();
				
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				List<BeneficiarioLiquidacion> listaBeneficiarioliquidacion = liquidacionFacade.getListaBeneficiarioLiquidacionPorEgreso(egreso);
				BeneficiarioLiquidacion beneficiarioLiquidacion = listaBeneficiarioliquidacion.get(0);
				ExpedienteLiquidacion expedienteLiquidacion = liquidacionFacade.getExpedienteLiquidacionPorBeneficiario(beneficiarioLiquidacion);
				expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(liquidacionFacade.getListaExpedienteLiquidacionDetallePorExpediente(expedienteLiquidacion));
				documentoGeneralSeleccionado.setExpedienteLiquidacion(expedienteLiquidacion);
				intBeneficiarioSeleccionar = beneficiarioLiquidacion.getIntPersPersonaBeneficiario();
				cargarListaBeneficiarioLiquidacion();
				if(egreso.getIntPersPersonaApoderado()!=null){
					personaApoderado = devolverPersonaCargada(egreso.getIntPersPersonaApoderado());
					archivoCartaPoder = egresoFacade.getArchivoPorEgreso(egreso);
				}
				validarNoExisteRequisitoAdjuntoGiroLiquidacion();
				agregarDocumento();
			}
			
			bdMontoGirar = egreso.getBdMontoTotal();
			strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, egresoDetalle.getIntParaTipoMoneda());
			exito = Boolean.TRUE;
			
			documentoGeneralSeleccionado = new DocumentoGeneral();
			documentoGeneralSeleccionado.setIntTipoDocumento(intTipoDocumentoAgregar);
			
		}catch (Exception e) {
			mensaje = "Ocurrio un error en la selección de egreso.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
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
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}
	/**
	 * Comentado hasta ver en que se usa JCHAVEZ 10.02.2014
	 * @return
	 */
	/*
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}*/
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			datosValidados = Boolean.FALSE;
			cargarListaBanco();
			intBancoCuentaSeleccionado = null;
			intTipoDocumentoValidar = null;
			intBancoSeleccionado = null;
			
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	private void cargarListaSucursal() throws Exception{
		listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
		List<Sucursal> listaSucursalTemp = listaSucursal;
		//Ordenamos por nombre
		Collections.sort(listaSucursalTemp, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});		
	}
	
	private void cargarSedeCentral() throws Exception{
		sucursalSedeCentral = obtenerSucursal(ID_SUCURSAL_SEDECENTRAL);
		
		//log.info("ID_SUCURSAL_SEDECENTRAL:"+ID_SUCURSAL_SEDECENTRAL);
		listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(ID_SUCURSAL_SEDECENTRAL);
		/*for(Object o : listaSubsucursal){
			Subsucursal s = (Subsucursal)o;
			log.info(s.getStrDescripcion());
		}*/
		subsucursalSedeCentral = obtenerSubsucursal(ID_SUBSUCURSAL_SEDECENTRAL);		
	}
	
	private Sucursal obtenerSucursal(Integer intIdSucursal) throws Exception{
		for(Object o : listaSucursal){
			Sucursal sucursal = (Sucursal)o;
			if(sucursal.getId().getIntIdSucursal().equals(intIdSucursal)){
				return sucursal;
			}
		}
		return null;
	}
	
	private Subsucursal obtenerSubsucursal(Integer intIdSubsucursal) throws Exception{
		for(Object o : listaSubsucursal){
			Subsucursal subsucursal = (Subsucursal)o;
			if(subsucursal.getId().getIntIdSubSucursal().equals(intIdSubsucursal)){
				return subsucursal;
			}
		}
		return null;
	}

	private void cargarListaBanco()throws Exception{
		Bancofondo bancoFondoFiltro = new Bancofondo();
		bancoFondoFiltro.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_BANCO);
		bancoFondoFiltro.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		listaBancoFondo = bancoFacade.buscarBancoFondo(bancoFondoFiltro);
	}

	public void seleccionarBanco() throws Exception{
		try{
			listaBancoCuenta = new ArrayList<Bancocuenta>();
			if(intBancoSeleccionado.equals(new Integer(0))){
				return;
			}
			String strEtiqueta = "";
			for(Bancofondo bancoFondo : listaBancoFondo){
				if(bancoFondo.getIntBancoCod().equals(intBancoSeleccionado)){
					for(Bancocuenta bancoCuenta : bancoFondo.getListaBancocuenta()){
						strEtiqueta = bancoCuenta.getStrNombrecuenta()+" - "
										+bancoCuenta.getCuentaBancaria().getStrNroCuentaBancaria()+" - "
										+obtenerEtiquetaTipoMoneda(bancoCuenta.getCuentaBancaria().getIntMonedaCod());
						bancoCuenta.setStrEtiqueta(strEtiqueta);
						listaBancoCuenta.add(bancoCuenta);
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private String obtenerEtiquetaTipoMoneda(Integer intTipoMoneda){
		for(Tabla tabla : listaMoneda){
			if(tabla.getIntIdDetalle().equals(intTipoMoneda)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	/**
	 * Agregado 12.05.2014 jchavez
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
	}
	
	public void validarDatos(){
		strMensajeErrorPorBeneficiario = "";
		blnBeneficiarioSinAutorizacion = false;
		try{
			log.info("intTipoDocumentoValidar:"+intTipoDocumentoValidar);
			datosValidados = Boolean.FALSE;
			habilitarGrabar = Boolean.FALSE;
			intNumeroCheque = null;
			limpiarFormulario();
			
			if(intBancoCuentaSeleccionado.equals(new Integer(0))){
				mostrarMensaje(Boolean.FALSE, "Debe de seleccionar una cuenta bancaria.");
				return;
			}
			
			if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA)){
				Bancocuenta bancoCuentaSeleccionado = null;
				for(Bancocuenta bancoCuenta : listaBancoCuenta){
					if(bancoCuenta.getId().getIntItembancocuenta().equals(intBancoCuentaSeleccionado)){
						
						bancoCuentaSeleccionado = bancoCuenta;
						break;
					}
				}			
				Bancocuentacheque bancoCuentaCheque = bancoFacade.getUltimoBancoCuentaCheque(bancoCuentaSeleccionado);
				if(bancoCuentaCheque==null){
					mostrarMensaje(Boolean.FALSE, "No existe un registro desponible de cheque para la cuenta bancaria seleccionada.");
					return;
				}
				intNumeroCheque = Integer.parseInt(bancoCuentaCheque.getStrControl()) + 1;				
				if(intNumeroCheque.compareTo(bancoCuentaCheque.getIntNumerofin())>1){
					mostrarMensaje(Boolean.FALSE, "El número de control de cheque excedió el numero máximo configurado.");
					return;
				}
			}
			
			dtFechaActual = new Date();			
			ocultarMensaje();
			datosValidados = Boolean.TRUE;
			habilitarGrabar = Boolean.TRUE;
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Ocurrio un error durante la validación");
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarPersona(ActionEvent event){
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
			
		}catch(Exception e){
			log.error(e.getMessage(), e);
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
	/**
	 * Procedimiento en el que se realiza la busqueda del documento a girar, dependiendo del Tipo Documento General:
	 * Préstamo (100): Mostrar prestamos con ultimo estado 4, monto solicitado mayor al maximo y con adjunto giro
	 * AES (101): Mostrar prevision tipo AES con ultimo estado 4, monto mayor al maximo y con adjunto giro
	 * Fdo.Sepelio (102): Mostrar prevision tipo Fdo.Sepelio con ultimo estado 4, monto mayor al maximo y con adjunto giro
	 * Fdo.Retiro (103): Mostrar prevision tipo Fdo.Retiro con ultimo estado 4, monto mayor al maximo y con adjunto giro
	 * Liquidación de Cuenta (104): Mostrar Liquidacion de Cuenta con ultimo estado 4, monto mayor al maximo y con adjunto giro
	 * Planilla de Movilidad (305): Misterio Sin Resolver
	 */
	public void abrirPopUpBuscarDocumento(){
		strMensajeError = "";
		try{
			listaDocumentoPorAgregar = new ArrayList<DocumentoGeneral>();
			Integer intIdPersona = personaSeleccionada.getIntIdPersona();
			
			log.info("Documento General a agregar en la Lista: "+intTipoDocumentoAgregar);
			//GIRO PLANILLA MOVILIDAD
			if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				List<Movilidad> listaMovilidad = egresoFacade.buscarMovilidadPorIdPersona(intIdPersona, EMPRESA_USUARIO);
				for(Movilidad movilidad : listaMovilidad){
					DocumentoGeneral documentoGeneral = new DocumentoGeneral();
					documentoGeneral.setIntTipoDocumento(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD);
					documentoGeneral.setStrNroDocumento(""+movilidad.getId().getIntItemMovilidad());
					documentoGeneral.setSucursal(movilidad.getSucursal());
					documentoGeneral.setSubsucursal(movilidad.getSubsucursal());
					documentoGeneral.setBdMonto(movilidad.getBdMontoAcumulado());
					
					documentoGeneral.setMovilidad(movilidad);
					listaDocumentoPorAgregar.add(documentoGeneral);
				}
			//GIRO PRÉSTAMO
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				List<ExpedienteCredito> listaExpedienteCredito = prestamoFacade.obtenerExpedientePorIdPersonayIdEmpresa(intIdPersona, EMPRESA_USUARIO);
				for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
//					if (validarExisteRequisitoCredito(expedienteCredito)) {
						EstadoCredito estadoCredito = expedienteCredito.getListaEstadoCredito().get(0);
						DocumentoGeneral documentoGeneral = new DocumentoGeneral();
						documentoGeneral.setIntTipoDocumento(intTipoDocumentoAgregar);
						String strNro = expedienteCredito.getId().getIntItemExpediente()
							+ "-" + expedienteCredito.getId().getIntItemDetExpediente();
						documentoGeneral.setStrNroDocumento(strNro);
						documentoGeneral.setSucursal(estadoCredito.getSucursal());
						documentoGeneral.setSubsucursal(estadoCredito.getSubsucursal());
						documentoGeneral.setBdMonto(expedienteCredito.getBdMontoTotal());
//						expedienteCredito.setArchivoGiro(prestamoFacade.getArchivoPorRequisitoCredito(lstRequisitoCredito.get(0)));
						documentoGeneral.setExpedienteCredito(expedienteCredito);
						listaDocumentoPorAgregar.add(documentoGeneral);
//					}
				}
			//MISTERIO SIN RESOLVER CONTINUA...
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PAGOLEGALIZACIONLIBROS)){
				List<PagoLegalizacion> listaPagoLegalizacion = legalizacionFacade.obtenerPagoLegalizacionPorPersona(intIdPersona, EMPRESA_USUARIO);
				for(PagoLegalizacion pagoLegalizacion : listaPagoLegalizacion){
					DocumentoGeneral documentoGeneral = new DocumentoGeneral();
					documentoGeneral.setIntTipoDocumento(Constante.PARAM_T_DOCUMENTOGENERAL_PAGOLEGALIZACIONLIBROS);
					documentoGeneral.setStrNroDocumento(""+pagoLegalizacion.getId().getIntItemPagoLegalizacion());
					documentoGeneral.setSucursal(sucursalSedeCentral);
					documentoGeneral.setSubsucursal(subsucursalSedeCentral);
					documentoGeneral.setBdMonto(pagoLegalizacion.getBdMonto());
					documentoGeneral.setStrMonto(formato.format(documentoGeneral.getBdMonto()));					
					
					documentoGeneral.setPagoLegalizacion(pagoLegalizacion);
					listaDocumentoPorAgregar.add(documentoGeneral);
				}
			//GIRO PREVISIÓN
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
//							expedientePrevision.setArchivoGrio(previsionFacade.getArchivoPorRequisitoPrevision(lstRequisitoPrevision.get(0)));
							documentoGeneral.setExpedientePrevision(expedientePrevision);
							listaDocumentoPorAgregar.add(documentoGeneral);
							}							
					}
			//GIRO LIQUIDACIÓN DE CUENTA
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				List<ExpedienteLiquidacion> listaExpedienteLiquidacion = liquidacionFacade.buscarExpedienteParaGiroDesdeFondo(intIdPersona, 
						EMPRESA_USUARIO);
				for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
//					if (validarExisteRequisitoLiquidacion(expedienteLiquidacion)) {
						EstadoLiquidacion estadoLiquidacion = expedienteLiquidacion.getEstadoLiquidacionUltimo();
						if (estadoLiquidacion.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICITUD_APROBADO)) {
							DocumentoGeneral documentoGeneral = new DocumentoGeneral();
							documentoGeneral.setIntTipoDocumento(expedienteLiquidacion.getIntParaDocumentoGeneral());
							String strNro = ""+expedienteLiquidacion.getId().getIntItemExpediente();
							documentoGeneral.setStrNroDocumento(strNro);
							documentoGeneral.setSucursal(estadoLiquidacion.getSucursal());
							documentoGeneral.setSubsucursal(estadoLiquidacion.getSubsucursal());
							documentoGeneral.setBdMonto(expedienteLiquidacion.getBdMontoBrutoLiquidacion());
//							expedienteLiquidacion.setArchivoGrio(liquidacionFacade.getArchivoPorRequisitoLiquidacion(lstRequisitoLiquidacion.get(0)));
							documentoGeneral.setExpedienteLiquidacion(expedienteLiquidacion);
							listaDocumentoPorAgregar.add(documentoGeneral);
						}
//					}
				}
			}
			
			listaDocumentoPorAgregar = egresoFacade.filtrarDuplicidadDocumentoGeneralParaEgreso(listaDocumentoPorAgregar, 
					intTipoDocumentoAgregar, listaDocumentoAgregados);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
//	public boolean validarExisteRequisitoCredito(ExpedienteCredito expedienteCredito){
//		log.info("--validarExisteRequisitoCredito()");
//		lstRequisitoCredito.clear();
//		Boolean exito = Boolean.TRUE;
//		try {
//			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
//			lstRequisitoCreditoComp = prestamoFacade.getRequisitoGiroPrestamoBanco(expedienteCredito);
//			//2. validamos si existe registro en requisito credito
//			if (lstRequisitoCreditoComp!=null && !lstRequisitoCreditoComp.isEmpty()) {
//				for (RequisitoCreditoComp2 o : lstRequisitoCreditoComp) {
//					ConfServDetalleId reqAutDetalleId = new ConfServDetalleId();
//					reqAutDetalleId.setIntPersEmpresaPk(o.getIntEmpresa());
//					reqAutDetalleId.setIntItemSolicitud(o.getIntItemRequisito());
//					reqAutDetalleId.setIntItemDetalle(o.getIntItemRequisitoDetalle());
//					lstRequisitoCredito.addAll(prestamoFacade.getListaPorPkExpedienteCreditoYRequisitoDetalle(expedienteCredito.getId(), reqAutDetalleId));
//				}
//				if (lstRequisitoCredito==null || lstRequisitoCredito.isEmpty()) {
//					exito = Boolean.FALSE;	
//				}
//			}
//		}catch (Exception e){
//			log.error(e.getMessage(),e);
//		}
//		return exito;
//	}
//	
//	public boolean validarExisteRequisitoPrevision(ExpedientePrevision expedientePrevision){
//		log.info("--validarExisteRequisitoPrevision()");
//		lstRequisitoPrevision.clear();
//		Boolean exito = Boolean.TRUE;
//		Integer intIdMaestro = null;
//		Integer intParaTipoDescripcion = null;
//		try {
//			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
//			if (expedientePrevision.getIntParaTipoCaptacion().equals(Constante.CAPTACION_FDO_SEPELIO)) {
//				expedientePrevision.setIntParaTipoOperacion(Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO);
//				intIdMaestro = Constante.PARAM_T_ADJUNTOGIROPREVISION_SEPELIO;
//				intParaTipoDescripcion = Constante.PARAM_T_ADJUNTOGIROPREVISION_SEPELIO_DETALLE;
//			}
//			if (expedientePrevision.getIntParaTipoCaptacion().equals(Constante.CAPTACION_FDO_RETIRO)) {
//				expedientePrevision.setIntParaTipoOperacion(Constante.PARAM_T_TIPOOPERACION_FONDORETIRO);
//				intIdMaestro = Constante.PARAM_T_ADJUNTOGIROPREVISION_RETIRO;
//				intParaTipoDescripcion = Constante.PARAM_T_ADJUNTOGIROPREVISION_RETIRO_DETALLE;
//			}
//			if (expedientePrevision.getIntParaTipoCaptacion().equals(Constante.CAPTACION_AES)) {
//				expedientePrevision.setIntParaTipoOperacion(Constante.PARAM_T_TIPOOPERACION_AES);
//				intIdMaestro = Constante.PARAM_T_ADJUNTOGIROPREVISION_AES;
//				intParaTipoDescripcion = Constante.PARAM_T_ADJUNTOGIROPREVISION_AES_DETALLE;
//			}
//			lstRequisitoPrevisionComp = previsionFacade.getRequisitoGiroPrevisionBanco(expedientePrevision, intIdMaestro, intParaTipoDescripcion);
//			//2. validamos si existe registro en requisito credito
//			if (lstRequisitoPrevisionComp!=null && !lstRequisitoPrevisionComp.isEmpty()) {
//				for (RequisitoPrevisionComp2 o : lstRequisitoPrevisionComp) {
//					ConfServDetalleId reqAutDetalleId = new ConfServDetalleId();
//					reqAutDetalleId.setIntPersEmpresaPk(o.getIntEmpresa());
//					reqAutDetalleId.setIntItemSolicitud(o.getIntItemRequisito());
//					reqAutDetalleId.setIntItemDetalle(o.getIntItemRequisitoDetalle());
//					//Se agrega campo del codigo de persona beneficiario. jchavez 09.05.2014
//					lstRequisitoPrevision.addAll(previsionFacade.getListaPorPkExpedientePrevisionYRequisitoDetalle(expedientePrevision.getId(), reqAutDetalleId, null));
//				}
//				if (lstRequisitoPrevision==null || lstRequisitoPrevision.isEmpty()) {
//					exito = Boolean.FALSE;	
//				}
//			}
//		}catch (Exception e){
//			log.error(e.getMessage(),e);
//		}
//		return exito;
//	}
//
//	public boolean validarExisteRequisitoLiquidacion(ExpedienteLiquidacion expedienteLiquidacion){
//		log.info("--validarExisteRequisitoLiquidacion()");
//		lstRequisitoLiquidacion.clear();
//		Boolean exito = Boolean.TRUE;
//		try {
//			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
//			lstRequisitoLiquidacionComp = liquidacionFacade.getRequisitoGiroLiquidacionBanco(expedienteLiquidacion);
//			//2. validamos si existe registro en requisito credito
//			if (lstRequisitoLiquidacionComp!=null && !lstRequisitoLiquidacionComp.isEmpty()) {
//				for (RequisitoLiquidacionComp2 o : lstRequisitoLiquidacionComp) {
//					ConfServDetalleId reqAutDetalleId = new ConfServDetalleId();
//					reqAutDetalleId.setIntPersEmpresaPk(o.getIntEmpresa());
//					reqAutDetalleId.setIntItemSolicitud(o.getIntItemRequisito());
//					reqAutDetalleId.setIntItemDetalle(o.getIntItemRequisitoDetalle());
//					//Se agrega campo del codigo de persona beneficiario. jchavez 09.05.2014
//					lstRequisitoLiquidacion.addAll(liquidacionFacade.getListaPorPkExpedienteLiquidacionYRequisitoDetalle(expedienteLiquidacion.getId(), reqAutDetalleId, null));
//				}
//				if (lstRequisitoLiquidacion==null || lstRequisitoLiquidacion.isEmpty()) {
//					exito = Boolean.FALSE;	
//				}
//			}
//		}catch (Exception e){
//			log.error(e.getMessage(),e);
//		}
//		return exito;
//	}	
	
	/**
	 * Agregado 10.05.2014 jchavez 
	 * validacion de existencia de adjunto de giro prevision 
	 */
	public boolean validarNoExisteRequisitoCredito(){
		log.info("--validarExisteRequisito()");
		blnNoExisteRequisito = true;
		deshabilitarNuevo = false;
		ExpedienteCredito expedienteCredito = null;
		lstRequisitoCredito.clear();
		lstRequisitoCreditoCmp = new ArrayList<RequisitoCreditoComp>();
		
		try {
//			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			//0. Recuperamos el expediente credito seleccionado...
			expedienteCredito = documentoGeneralSeleccionado.getExpedienteCredito();
			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
			lstRequisitoCreditoCmp = prestamoFacade.getRequisitoGiroPrestamo(expedienteCredito);
			//2. validamos si existe registro en requisito credito
			if (lstRequisitoCreditoCmp!=null && !lstRequisitoCreditoCmp.isEmpty()) {
				Integer requisitoSize = lstRequisitoCreditoCmp.size();

				for (RequisitoCreditoComp o : lstRequisitoCreditoCmp) {
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
				//obtener el mov cta por pagar de prevision. jchavez 20.05.2014
				buscarMovCtaCtePorPagarLiquidacion(documentoGeneralSeleccionado.getExpedienteLiquidacion());
//				archivoAdjuntoGiro = documentoGeneralSeleccionado.getExpedienteLiquidacion().getArchivoGrio();
			}
			
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				if(validarNoExisteRequisitoCredito()){
					documentoGeneralSeleccionado = null;
					strMensajeError = "Préstamo no cuenta con documento de autorizacion de giro";
				}else{
					strMensajeError = "";
					deshabilitarNuevo = false;
					blnVerBotonApoderado = true;
				}
			}
			log.info("mensajito: "+strMensajeError);
			log.info("No Existe Requisito: "+blnNoExisteRequisito);
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
	
	private void cargarListaBeneficiarioPrevision()throws Exception{
		listaBeneficiarioPrevision = new ArrayList<BeneficiarioPrevision>();
		List<BeneficiarioPrevision> listaBeneficiarioPrevisionBD = previsionFacade.getListaBeneficiarioPrevision(
				documentoGeneralSeleccionado.getExpedientePrevision());
		
		for(BeneficiarioPrevision beneficiarioPrevision : listaBeneficiarioPrevisionBD){
			if(beneficiarioPrevision.getIntItemEgresoGeneral()!=null && deshabilitarNuevo==Boolean.FALSE){
				continue;
			}
			
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
					break;
				}
			}
			String strEtiqueta = "";
			Persona persona = devolverPersonaCargada(intIdPersona);
			strEtiqueta = (intItemVinculo!=null?intItemVinculo+ " - ":"") + persona.getNatural().getStrNombreCompleto();
			strEtiqueta = strEtiqueta + " - DNI : " + persona.getDocumento().getStrNumeroIdentidad();
			strEtiqueta = strEtiqueta + " - VINCULO : "+(intItemVinculo!=null?obtenerEtiquetaVinculo(intItemVinculo):"TITULAR");
			persona.setStrEtiqueta(strEtiqueta);
			
			listaPersona.add(persona);
		}
		expedienteLiquidacion.setListaPersona(listaPersona);
		listaBeneficiarioPersona.addAll(listaPersona);
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
					eDI.setArchivoAdjuntoGiro(archivoAdjuntoGiro);
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
					eDI.setStrDescripcion(eDI.getExpedienteLiquidacionDetalle().getCuentaConcepto().getDetalle().getCaptacion().getStrDescripcion());
					eDI.setBdMonto(eDI.getBdSubTotal());
					eDI.setLibroDiario(libroDiario);
					eDI.setArchivoAdjuntoGiro(archivoAdjuntoGiro);
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
			}			

			for(Object o : listaEgresoDetalleInterfazAgregado){
				EgresoDetalleInterfaz egresoDetalleInterfaz = (EgresoDetalleInterfaz)o;
				if(egresoDetalleInterfaz.getIntOrden()==null){
					egresoDetalleInterfaz.setIntOrden(intOrden);
					bdMontoGirar = bdMontoGirar.add(egresoDetalleInterfaz.getBdSubTotal());
					intOrden++;
				}
			}
			strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, Constante.PARAM_T_TIPOMONEDA_SOLES);
			
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

	private void manejarAgregarExpedientePrevision() throws Exception{
		ExpedientePrevision expedientePrevision = documentoGeneralSeleccionado.getExpedientePrevision();
		BeneficiarioPrevision benefPrevSelected = null;
		for(Object o : listaBeneficiarioPrevision){
			BeneficiarioPrevision beneficiarioPrevision = (BeneficiarioPrevision)o;
			if(listaBeneficiarioPrevision.size()==1){
				beneficiarioPrevision.setEsUltimoBeneficiarioAGirar(Boolean.TRUE);
			}else{
				beneficiarioPrevision.setEsUltimoBeneficiarioAGirar(Boolean.FALSE);
			}
			if(beneficiarioPrevision.getId().getIntItemBeneficiario().equals(intBeneficiarioSeleccionar)){
				benefPrevSelected = beneficiarioPrevision;
				break;
			}
		}

		benefPrevSelected.setPersonaApoderado(personaApoderado);
		benefPrevSelected.setArchivoCartaPoder(archivoCartaPoder);
		expedientePrevision.setBeneficiarioPrevisionGirar(benefPrevSelected);

		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = previsionFacade.
										cargarListaEgresoDetalleInterfaz(expedientePrevision, benefPrevSelected);
		expedientePrevision.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
	}
	
	private void manejarAgregarExpedienteLiquidacion() throws Exception{
		ExpedienteLiquidacion expedienteLiquidacion = documentoGeneralSeleccionado.getExpedienteLiquidacion();
		if(listaBeneficiarioPersona.size()==1){
			expedienteLiquidacion.setEsUltimoBeneficiarioAGirar(Boolean.TRUE);
		}else{
			expedienteLiquidacion.setEsUltimoBeneficiarioAGirar(Boolean.FALSE);
		}
		expedienteLiquidacion.setIntIdPersonaBeneficiarioGirar(intBeneficiarioSeleccionar);
		
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = liquidacionFacade.
					cargarListaEgresoDetalleInterfaz(expedienteLiquidacion.getListaExpedienteLiquidacionDetalle(), intBeneficiarioSeleccionar);
		expedienteLiquidacion.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
	}
	
	public void seleccionarRol(){
		try{
			if(intTipoRol.equals(new Integer(0))){
				listaTipoDocumentoTerceros = new ArrayList<Tabla>();
			
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_PERSONAL)){
				listaTipoDocumentoTerceros  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "P");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_SOCIO)){
				listaTipoDocumentoTerceros  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "S");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_USUARIO)){
				listaTipoDocumentoTerceros  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "U");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_ENTIDADREGULADORA)){
				listaTipoDocumentoTerceros = new ArrayList<Tabla>();
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_ENTIDADDESCUENTOS)){
				listaTipoDocumentoTerceros = new ArrayList<Tabla>();
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_UNIDADEJECUTORA)){
				listaTipoDocumentoTerceros  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "E");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_PROVEEDOR)){
				listaTipoDocumentoTerceros  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "V");
				
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * Comentado hasta ver en que se usa JCHAVEZ 10.02.2014
	 * @param intTipoCredito
	 * @return
	 */
	/*
	private String obtenerEtiquetaTipoCredito(Integer intTipoCredito){
		for(Tabla tabla : listaTablaTipoCredito){
			if(tabla.getIntIdDetalle().equals(intTipoCredito)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}*/
	
	private String obtenerEtiquetaTipoDocumentoGeneral(Integer intTipoDocumento){
		for(Tabla tabla : listaTablaDocumentoGeneral){
			if(tabla.getIntIdDetalle().equals(intTipoDocumento)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	public void abrirPopUpBuscarPersona(){
		try{
			intTipoBuscarPersona = BUSCAR_PERSONA;
			listaPersona = new ArrayList<Persona>();
			strFiltroTextoPersona = "";
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
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

	public String getInicioPage() {
		cargarUsuario();
		if(usuario!=null){
			limpiarFormulario();
			listaEgreso.clear();
			deshabilitarPanelInferior(null);
		}else log.error("--Usuario obtenido es NULL.");
		return "";
	}
	
	private void limpiarFormulario(){
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
		
		intTipoRol = 0;
		intTipoDocumentoAgregar = 0;
		listaDocumentoPorAgregar = new ArrayList<DocumentoGeneral>();
	}
	
	public void quitarPersonaSeleccionada(){
		try{
			limpiarFormulario();
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
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
			
			listaPersona.add(persona);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void deseleccionarPersonaApoderado(){
		try{
			personaApoderado = null;
			quitarCartaPoder();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
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
	
	/*
	 * 								<a4j:support event="onchange"
									action="#{chequesController.quitarPersonaApoderado}" 
									reRender="panelApoderadoC"/>S
	 */
	
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
	public Integer getIntTipoRol() {
		return intTipoRol;
	}
	public void setIntTipoRol(Integer intTipoRol) {
		this.intTipoRol = intTipoRol;
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
	public List<Persona> getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
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
	public List<BeneficiarioPrevision> getListaBeneficiarioPrevision() {
		return listaBeneficiarioPrevision;
	}
	public void setListaBeneficiarioPrevision(List<BeneficiarioPrevision> listaBeneficiarioPrevision) {
		this.listaBeneficiarioPrevision = listaBeneficiarioPrevision;
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
	public Integer getIntBeneficiarioSeleccionar() {
		return intBeneficiarioSeleccionar;
	}
	public void setIntBeneficiarioSeleccionar(Integer intBeneficiarioSeleccionar) {
		this.intBeneficiarioSeleccionar = intBeneficiarioSeleccionar;
	}
	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}
	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
	}
	public Integer getIntTipoBuscarPersona() {
		return intTipoBuscarPersona;
	}
	public void setIntTipoBuscarPersona(Integer intTipoBuscarPersona) {
		this.intTipoBuscarPersona = intTipoBuscarPersona;
	}
	public List<EgresoDetalleInterfaz> getListaEgresoDetalleInterfazAgregado() {
		return listaEgresoDetalleInterfazAgregado;
	}
	public void setListaEgresoDetalleInterfazAgregado(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfazAgregado) {
		this.listaEgresoDetalleInterfazAgregado = listaEgresoDetalleInterfazAgregado;
	}
	public Persona getPersonaApoderado() {
		return personaApoderado;
	}
	public void setPersonaApoderado(Persona personaApoderado) {
		this.personaApoderado = personaApoderado;
	}
	public Date getDtFechaActual() {
		return dtFechaActual;
	}
	public void setDtFechaActual(Date dtFechaActual) {
		this.dtFechaActual = dtFechaActual;
	}
	public Integer getIntNumeroCheque() {
		return intNumeroCheque;
	}
	public void setIntNumeroCheque(Integer intNumeroCheque) {
		this.intNumeroCheque = intNumeroCheque;
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
	public String getStrTextoPersonaFiltro() {
		return strTextoPersonaFiltro;
	}
	public void setStrTextoPersonaFiltro(String strTextoPersonaFiltro) {
		this.strTextoPersonaFiltro = strTextoPersonaFiltro;
	}
	public List<Egreso> getListaEgreso() {
		return listaEgreso;
	}
	public void setListaEgreso(List<Egreso> listaEgreso) {
		this.listaEgreso = listaEgreso;
	}
	//
	public List<Tabla> getListaTipoDocumento() {
		return listaTipoDocumento;
	}
	public void setListaTipoDocumento(List<Tabla> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}
	public List<Tabla> getListaPersonaRolUsar() {
		return listaPersonaRolUsar;
	}
	public void setListaPersonaRolUsar(List<Tabla> listaPersonaRolUsar) {
		this.listaPersonaRolUsar = listaPersonaRolUsar;
	}
	public List<Tabla> getListaTipoDocumentoTerceros() {
		return listaTipoDocumentoTerceros;
	}
	public void setListaTipoDocumentoTerceros(List<Tabla> listaTipoDocumentoTerceros) {
		this.listaTipoDocumentoTerceros = listaTipoDocumentoTerceros;
	}
//	public List<Tabla> getListaSubTipoOperacion() {
//		return listaSubTipoOperacion;
//	}
//	public void setListaSubTipoOperacion(List<Tabla> listaSubTipoOperacion) {
//		this.listaSubTipoOperacion = listaSubTipoOperacion;
//	}
	public Archivo getArchivoAdjuntoGiro() {
		return archivoAdjuntoGiro;
	}
	public void setArchivoAdjuntoGiro(Archivo archivoAdjuntoGiro) {
		this.archivoAdjuntoGiro = archivoAdjuntoGiro;
	}
	public List<RequisitoCreditoComp> getLstRequisitoCreditoComp() {
		return lstRequisitoCreditoComp;
	}
	public void setLstRequisitoCreditoComp(
			List<RequisitoCreditoComp> lstRequisitoCreditoComp) {
		this.lstRequisitoCreditoComp = lstRequisitoCreditoComp;
	}
	public List<RequisitoCredito> getLstRequisitoCredito() {
		return lstRequisitoCredito;
	}
	public void setLstRequisitoCredito(List<RequisitoCredito> lstRequisitoCredito) {
		this.lstRequisitoCredito = lstRequisitoCredito;
	}
	public List<RequisitoPrevision> getLstRequisitoPrevision() {
		return lstRequisitoPrevision;
	}
	public void setLstRequisitoPrevision(
			List<RequisitoPrevision> lstRequisitoPrevision) {
		this.lstRequisitoPrevision = lstRequisitoPrevision;
	}
	public List<RequisitoPrevisionComp> getLstRequisitoPrevisionComp() {
		return lstRequisitoPrevisionComp;
	}
	public void setLstRequisitoPrevisionComp(
			List<RequisitoPrevisionComp> lstRequisitoPrevisionComp) {
		this.lstRequisitoPrevisionComp = lstRequisitoPrevisionComp;
	}
	public List<RequisitoLiquidacion> getLstRequisitoLiquidacion() {
		return lstRequisitoLiquidacion;
	}
	public void setLstRequisitoLiquidacion(
			List<RequisitoLiquidacion> lstRequisitoLiquidacion) {
		this.lstRequisitoLiquidacion = lstRequisitoLiquidacion;
	}
	public List<RequisitoLiquidacionComp> getLstRequisitoLiquidacionComp() {
		return lstRequisitoLiquidacionComp;
	}
	public void setLstRequisitoLiquidacionComp(
			List<RequisitoLiquidacionComp> lstRequisitoLiquidacionComp) {
		this.lstRequisitoLiquidacionComp = lstRequisitoLiquidacionComp;
	}
	public List<BeneficiarioLiquidacion> getListaBeneficiarioLiquidacion() {
		return listaBeneficiarioLiquidacion;
	}
	public void setListaBeneficiarioLiquidacion(
			List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacion) {
		this.listaBeneficiarioLiquidacion = listaBeneficiarioLiquidacion;
	}
	public Boolean getBlnNoExisteRequisito() {
		return blnNoExisteRequisito;
	}
	public void setBlnNoExisteRequisito(Boolean blnNoExisteRequisito) {
		this.blnNoExisteRequisito = blnNoExisteRequisito;
	}
	public List<RequisitoCreditoComp> getLstRequisitoCreditoCmp() {
		return lstRequisitoCreditoCmp;
	}
	public void setLstRequisitoCreditoCmp(
			List<RequisitoCreditoComp> lstRequisitoCreditoCmp) {
		this.lstRequisitoCreditoCmp = lstRequisitoCreditoCmp;
	}
	public String getStrMensajeError() {
		return strMensajeError;
	}
	public void setStrMensajeError(String strMensajeError) {
		this.strMensajeError = strMensajeError;
	}
	public Boolean getBlnVerBotonApoderado() {
		return blnVerBotonApoderado;
	}
	public void setBlnVerBotonApoderado(Boolean blnVerBotonApoderado) {
		this.blnVerBotonApoderado = blnVerBotonApoderado;
	}

	public BeneficiarioPrevision getBeneficiarioPrevisionSeleccionado() {
		return beneficiarioPrevisionSeleccionado;
	}

	public void setBeneficiarioPrevisionSeleccionado(
			BeneficiarioPrevision beneficiarioPrevisionSeleccionado) {
		this.beneficiarioPrevisionSeleccionado = beneficiarioPrevisionSeleccionado;
	}

	public String getStrMensajeErrorPorBeneficiario() {
		return strMensajeErrorPorBeneficiario;
	}

	public void setStrMensajeErrorPorBeneficiario(
			String strMensajeErrorPorBeneficiario) {
		this.strMensajeErrorPorBeneficiario = strMensajeErrorPorBeneficiario;
	}

	public BeneficiarioLiquidacion getBeneficiarioLiquidacionSeleccionado() {
		return beneficiarioLiquidacionSeleccionado;
	}

	public void setBeneficiarioLiquidacionSeleccionado(
			BeneficiarioLiquidacion beneficiarioLiquidacionSeleccionado) {
		this.beneficiarioLiquidacionSeleccionado = beneficiarioLiquidacionSeleccionado;
	}

	public Boolean getBlnBeneficiarioSinAutorizacion() {
		return blnBeneficiarioSinAutorizacion;
	}

	public void setBlnBeneficiarioSinAutorizacion(
			Boolean blnBeneficiarioSinAutorizacion) {
		this.blnBeneficiarioSinAutorizacion = blnBeneficiarioSinAutorizacion;
	}

	public Boolean getBlnHabilitarObservacion() {
		return blnHabilitarObservacion;
	}

	public void setBlnHabilitarObservacion(Boolean blnHabilitarObservacion) {
		this.blnHabilitarObservacion = blnHabilitarObservacion;
	}
	
}
/*
 * COD01
 * 				
					rendered="#{(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)
					||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_AES)
					||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
					||	(chequesController.documentoGeneralSeleccionado.intTipoDocumento==applicationScope.Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)}"		
 *
 *COD02
 *rendered="#{(not empty giroPrevisionController.archivoAdjuntoGiro)&&(giroPrevisionController.deshabilitarNuevoBeneficiario)}"> 
 */
