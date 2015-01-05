package pe.com.tumi.servicio.prevision.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadControllerServicio;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeLocal;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
//import pe.com.tumi.servicio.prevision.bo.RequisitoPrevisionBO;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevisionId;
//import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
//import pe.com.tumi.servicio.prevision.domain.FallecidoPrevision;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevisionId;
import pe.com.tumi.servicio.prevision.domain.composite.AutorizaPrevisionComp;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp;
import pe.com.tumi.servicio.prevision.facade.AutorizacionPrevisionFacadeRemote;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeRemote;
import pe.com.tumi.servicio.prevision.service.solicitudPrevisionService;

public class AutorizacionPrevisionController {
	protected static Logger log = Logger.getLogger(AutorizacionPrevisionController.class);
	private Usuario usuario;
	private AutorizaVerificaPrevision beanAutorizaVerificacion;
	private List<Tabla> listaSubTipoSolicitudBusqueda;
	private ExpedientePrevisionComp registroSeleccionadoBusqueda;
	private Persona personaBusqueda;
	private Integer intTipoPersona;
	private Integer intIdTipoPersona;
	private String strNroSolicitud;
	private Integer intTipoSucursalBusq;
	private Integer intIdEstadoSolicitud;
	private Date dtFecInicio;
	private Date dtFecFin;
	private List<Estructura> listEstructura;
	private List<Subsucursal>listSubSucursal;
	private List<Archivo> listaArchivo;
	private Tabla tablaEstado;
	private String strTxtMsgPerfil;
	private String strTxtMsgUsuario;
	private String strTxtMsgValidacion;
	private String strTxtMsgError;
	private Boolean formAutorizacionPrevisionRendered;
	private List<AutorizaPrevisionComp> listaAutorizaPrevisionComp;
	private AutorizaPrevision beanAutorizaPrevision;
	
	private String strTxtMsgConfiguracion;

	// Campos para la busqueda
	private Integer				intTipoCreditoFiltro;
	private Integer				intSubTipoCreditoFiltro;
	private Integer				intTipoPersonaFiltro;
	private Integer				intTipoBusquedaPersonaFiltro;
	private String				strTextoPersonaFiltro;
	private Integer				intItemExpedienteFiltro;
	private EstadoPrevision		estadoPrevisionFiltro;
	private List<Tabla>			listaTablaEstadoPago;
	private List<Tabla>			listaTablaTipoDocumento;
	private Integer				intTipoBusquedaFechaFiltro;
	private Integer				intTipoBusquedaSucursal;
	private List<Tabla>			listaTipoBusquedaSucursal;
	private Integer				intIdSubsucursalFiltro;
	private Integer				intIdSucursalFiltro;
//	private List				listaExpedientePrevisionX;
	private List<ExpedientePrevision> listaExpedientePrevision;
	private List<Tabla> 		listaTipoVinculo;
	private List<ExpedientePrevision> listaExpedientePrevisionSocio;
	private List<Subsucursal> 	listaSubsucursal;
	private List<Tabla>			listaTablaSucursal;
	private List<RequisitoPrevisionComp> listaRequisitoPrevisionComp;
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoOperacionPersona;
	private Archivo archivoDeJu;
	private Archivo archivoReniecAut;
	private List<AutorizaVerificaPrevision> listaAutorizaVerificacionAdjuntos;
	private List<ConfServSolicitud> listaAutorizacionConfigurada = null;
	private List<Tabla> listaTipoOperacion;
	private List<Sucursal> listSucursal;
	private boolean blnIsRetiro;
	private ConfSolicitudFacadeLocal solicitudFacade = null;
	private GeneralFacadeRemote generalFacade = null;	
	private SocioFacadeRemote socioFacade = null;
	private TablaFacadeRemote tablaFacade = null;
	private PrevisionFacadeRemote previsionFacade = null;
	private PersonaFacadeRemote personaFacade = null;
	private PermisoFacadeRemote permisoFacade = null;
	private EmpresaFacadeRemote empresaFacade= null;
	private AutorizacionPrevisionFacadeRemote autorizacionFacade = null;
	private solicitudPrevisionService solicitudPresvisionService = null;
	private CuentaFacadeRemote cuentaFacade = null;
	private ConceptoFacadeRemote conceptoFacade = null;
	private LibroDiarioFacadeRemote libroDiarioFacade = null;
	private Boolean blnMostrarAutorizacion;
	private SocioComp beanSocioComp;
	private String strConfiguracionesEvaluadas;
	private ConfServSolicitud autorizacionConfiguradaFinal = null;
	
	private Boolean blnBloquearXCuenta;
	private String strMensajeValidacionCuenta;
	
	// parametros de busqueda - cgd 01.10.2013
	private Integer intBusqTipo; 	
	private String strBusqCadena;		    
	private String strBusqNroSol;		   
	private Integer intBusqSucursal;
	private Integer intBusqEstado;	
	private Integer intBusqTipoPrevision;
	private Integer intBusqSubTipoPrevision;
	private Date dtBusqFechaEstadoDesde;  
	private Date dtBusqFechaEstadoHasta;
	private	List<Sucursal> listaSucursalBusqueda;
	private List<Subsucursal> listaSubSucursaBusqueda;
	
	private List<ExpedientePrevisionComp> listaExpedientePrevisionComp;
	
	//Autor: jchavez / Tarea: Modificación / Fecha: 01.09.2014 / 
	private List<Tabla> listaEstadoSolicitud;
	

	public AutorizacionPrevisionController() {
		beanAutorizaPrevision = new AutorizaPrevision();
		beanAutorizaVerificacion = new AutorizaVerificaPrevision();
		formAutorizacionPrevisionRendered = false;
		listaRequisitoPrevisionComp = new ArrayList<RequisitoPrevisionComp>();
		personaBusqueda = new Persona();
		personaBusqueda.setDocumento(new Documento());
		listaArchivo = new ArrayList<Archivo>();
		beanSocioComp = new SocioComp();
		beanSocioComp.setPersona(new Persona());
		beanSocioComp.getPersona().setNatural(new Natural());
		beanSocioComp.getPersona().getNatural().setPerLaboral(new PerLaboral());
		beanSocioComp.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
		beanSocioComp.getPersona().setDocumento(new Documento());
		beanSocioComp.getPersona().setPersonaEmpresa(new PersonaEmpresa());
		beanSocioComp.getPersona().getPersonaEmpresa().setVinculo(new Vinculo());
		beanSocioComp.setPersonaRol(new PersonaRol());
		beanSocioComp.getPersonaRol().setId(new PersonaRolPK());
		beanSocioComp.setSocio(new Socio());
		beanSocioComp.getSocio().setSocioEstructura(new SocioEstructura());
		beanSocioComp.setCuenta(new Cuenta());
		beanSocioComp.setCuentaComp(new CuentaComp());

		try {
			solicitudFacade = (ConfSolicitudFacadeLocal) EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			previsionFacade = (PrevisionFacadeRemote) EJBFactory.getRemote(PrevisionFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			autorizacionFacade = (AutorizacionPrevisionFacadeRemote)EJBFactory.getRemote(AutorizacionPrevisionFacadeRemote.class);
			solicitudPresvisionService = (solicitudPrevisionService)TumiFactory.get(solicitudPrevisionService.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			
			intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			estadoPrevisionFiltro = new EstadoPrevision();
			estadoPrevisionFiltro.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);			
			
			listaExpedientePrevision = new ArrayList<ExpedientePrevision>();
			listaTablaTipoDocumento = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "F");
			listaTablaEstadoPago = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSOLICPRESTAMO), "A");
			listaTipoBusquedaSucursal = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA), "A");
			listaTablaTipoDocumento = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "F");
			listaTipoVinculo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOVINCULO));
			listaTipoOperacion = tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RETIRO);
			
			blnIsRetiro = false;

			//Autor: jchavez / Tarea: Modificación / Fecha: 01.09.2014 / 
			listaEstadoSolicitud = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSOLICPRESTAMO),"D");
			} catch (NumberFormatException e) {
				log.error("Error NumberFormatException en AutorizacionPrevisionController ---> "+e);
			} catch (BusinessException e) {
				log.error("Error BusinessException en AutorizacionPrevisionController ---> "+e);
			} catch (EJBFactoryException e) {
				log.error("Error EJBFactoryException en AutorizacionPrevisionController ---> "+e);
		} finally {
			inicio();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getLimpiarAutorizacion(){
		inicio();
		listaExpedientePrevisionComp = new ArrayList<ExpedientePrevisionComp>();
		return "";
	}
	
	
	/**
	 * 
	 */
	public void inicio() {
		usuario = new Usuario();
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
		limpiarFormAutorizacionPrevision();
		formAutorizacionPrevisionRendered = false;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public List<Sucursal> getListSucursal() {
		try {
			if (listSucursal == null) {
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote) EJBFactory
						.getRemote(EmpresaFacadeRemote.class);
				this.listSucursal = facade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
				//Ordenamos por nombre
				Collections.sort(listSucursal, new Comparator<Sucursal>(){
					public int compare(Sucursal sucUno, Sucursal sucDos) {
						return sucUno.getJuridica().getStrSiglas().compareTo(sucDos.getJuridica().getStrSiglas());
					}
				});
			}
		} catch (EJBFactoryException e) {
			log.error("Error EJBFactoryException getListSucursal ---> "+e);
		} catch (BusinessException e) {
			log.error("Error BusinessException getListSucursal ---> "+e);
		}
		return listSucursal;
	}
	
	
	/**
	 * 
	 * @param beanName
	 * @return
	 */
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}

	
	/**
	 * 
	 * @param name
	 * @return
	 */
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	
	/**
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	/**
	 *  
	 */
	public void limpiarFormAutorizacionPrevision() {
		beanAutorizaPrevision = new AutorizaPrevision();
		beanAutorizaVerificacion = new AutorizaVerificaPrevision();
		listaRequisitoPrevisionComp = new ArrayList<RequisitoPrevisionComp>();
		blnIsRetiro = false;
		archivoDeJu = null;
		archivoReniecAut = null;
		blnMostrarAutorizacion = Boolean.TRUE;
		limpiarMensajes();
	}

	
	/**
	 * 
	 * @param event
	 */
	public void buscarSolicitudPrevision(ActionEvent event){
		ExpedientePrevisionComp expedienteCompBusq = null;
		try{
						
			expedienteCompBusq = new ExpedientePrevisionComp();
			expedienteCompBusq.setIntBusquedaTipo(intBusqTipo);
			expedienteCompBusq.setStrBusqCadena(strBusqCadena.trim());
			expedienteCompBusq.setStrBusqNroSol("");
			expedienteCompBusq.setIntBusqSucursal(intBusqSucursal);
			expedienteCompBusq.setIntBusqEstado(intBusqEstado);
			expedienteCompBusq.setDtBusqFechaEstadoDesde(dtBusqFechaEstadoDesde);
			expedienteCompBusq.setDtBusqFechaEstadoHasta(dtBusqFechaEstadoHasta);
			expedienteCompBusq.setIntBusqTipoPrevision(intBusqTipoPrevision);
			expedienteCompBusq.setIntBusqSubTipoPrevision(intBusqSubTipoPrevision);
			
			listaExpedientePrevisionComp = previsionFacade.getListaBusqAutExpPrevFiltros(expedienteCompBusq);

			/*if(listaExpedientePrevision != null && !listaExpedientePrevision.isEmpty()){
				listaExpedientePrevisionComp = new ArrayList<ExpedientePrevisionComp>();
				
				for (ExpedientePrevision expedienteprevision: listaExpedientePrevision) {
					ExpedientePrevisionComp expComp = new ExpedientePrevisionComp();
					expComp.setExpedientePrevision(expedienteprevision);
					listaExpedientePrevisionComp.add(expComp);
				}
				
			}*/

		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
//	private List<Persona> buscarPersona() throws Exception{
//		List<Persona> listaPersona = new ArrayList<Persona>();
//		
//		log.info("intTipoBusquedaPersonaFiltro:"+intTipoBusquedaPersonaFiltro);
//		
//		if(strTextoPersonaFiltro==null || strTextoPersonaFiltro.isEmpty()){
//			//Si strTextoPersonaFiltro se deja vacio, entonces buscaremos todos los registros
//			return null;
//		}
//		
//		if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_BUSQSOLICPTMO_CODIGOPERSONA)){	
//			Persona persona = personaFacade.getPersonaPorPK(Integer.parseInt(strTextoPersonaFiltro));
//			if(persona!=null)listaPersona.add(persona);
//		
//		}else if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_BUSQSOLICPTMO_APELLIDOSNOMBRES)){
//			Natural natural = new Natural();
//			natural.setStrNombres(strTextoPersonaFiltro);
//			List<Natural> listaNatural = personaFacade.getListaNaturalPorBusqueda(natural);
//			if(listaNatural!=null && !listaNatural.isEmpty()){
//				for(Natural naturalTemp : listaNatural){
//					Persona persona = new Persona();
//					persona.setIntIdPersona(naturalTemp.getIntIdPersona());
//					listaPersona.add(persona);
//				}
//			}
//			
//		}else if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_BUSQSOLICPTMO_DOCUMENTO)){
//			if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
//				Persona persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
//						Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
//						strTextoPersonaFiltro,
//						Constante.PARAM_EMPRESASESION);
//				if(persona!=null) listaPersona.add(persona);
//			}else if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
//				Persona persona = personaFacade.getPersonaPorRuc(strTextoPersonaFiltro);
//				if(persona!=null) listaPersona.add(persona);
//			}			
//		}
//		
//		return listaPersona;
//	}
	
	
//	private Persona devolverPersonaCargada(Integer intIdPersona) throws Exception{
//		
//		Persona persona = null;
//		try {
//			log.info(intIdPersona);
//			persona = personaFacade.getPersonaPorPK(intIdPersona);
//			if(persona != null){
//				if(persona.getIntTipoPersonaCod() != null){
//					if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
//						persona = personaFacade.getPersonaNaturalPorIdPersona(persona.getIntIdPersona());			
//						agregarDocumentoDNI(persona);
//						agregarNombreCompleto(persona);			
//					
//					}else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
//						persona.setJuridica(personaFacade.getJuridicaPorPK(persona.getIntIdPersona()));			
//					}
//				}
//				
//			}
//			
//		} catch (Exception e) {
//			log.error("Error en devolverPersonaCargada = intIdPersona "+intIdPersona +" --> "+e);
//		}
//		
//		return persona;
//	}
//	
//	private void agregarDocumentoDNI(Persona persona){
//		for(Documento documento : persona.getListaDocumento()){
//			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
//				persona.setDocumento(documento);
//			}
//		}
//	}
//	
//	private void agregarNombreCompleto(Persona persona){
//		persona.getNatural().setStrNombreCompleto(
//				persona.getNatural().getStrNombres()+" "+
//				persona.getNatural().getStrApellidoPaterno()+" "+
//				persona.getNatural().getStrApellidoMaterno());
//	}
//	
//
//	
//	
	private void cargarListaTablaSucursal() throws Exception{
		List<Sucursal>listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
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
	
	
	public void seleccionarRegistro(ActionEvent event){
		ExpedientePrevisionId expedienteCreditoId = null;
		try{
			strConfiguracionesEvaluadas = "";
			registroSeleccionadoBusqueda = (ExpedientePrevisionComp)event.getComponent().getAttributes().get("itemExpedientes");
			
			if(registroSeleccionadoBusqueda != null){
				log.info("reg selec:"+registroSeleccionadoBusqueda);	
				Integer intEstado = registroSeleccionadoBusqueda.getExpedientePrevision().getIntEstadoCreditoUltimo();
				
				//
				// REVISAR ---> 28.08.2013  WTF .................................................................. :D
				//
				Integer intTipoCaptacion = registroSeleccionadoBusqueda.getExpedientePrevision().getIntParaTipoCaptacion();

					if(intTipoCaptacion.compareTo(Constante.CAPTACION_FDO_RETIRO)==0){
						blnIsRetiro = true;
					} else {
							blnIsRetiro = false;
					}	
					
				recuperarAdjuntosAutorizacion(registroSeleccionadoBusqueda.getExpedientePrevision());
				/*public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO = 1;
				public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD = 2;
				public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO = 3;
				public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_APROBADO = 4;
				public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO = 5;
				public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_GIRADO = 6;
				public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_ANULADO = 7;*/
				if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
					blnMostrarAutorizacion = Boolean.FALSE;
				}
				if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0){
					blnMostrarAutorizacion = Boolean.TRUE;
				}
				if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)==0){
					blnMostrarAutorizacion = Boolean.FALSE;

				}
				

					expedienteCreditoId = new ExpedientePrevisionId();
					expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedientePrevision().getId().getIntCuentaPk());
					expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedientePrevision().getId().getIntPersEmpresaPk());
					validarEstadoCuenta(expedienteCreditoId);


			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * Valida la Situacion de la cuenta del expediente seleccionado, a find e permitir solo los de Estado ACTIVO
	 * @return
	 */
	public Boolean validarEstadoCuenta(ExpedientePrevisionId expId){
		Boolean blnValido= null;
		CuentaFacadeRemote cuentaFacade = null;
		CuentaId ctaIdExp = null;
		Cuenta ctaExpediente = null;
		strMensajeValidacionCuenta = "";
		try {
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			if(expId != null){
				ctaIdExp = new CuentaId();
				ctaIdExp.setIntCuenta(expId.getIntCuentaPk());
				ctaIdExp.setIntPersEmpresaPk(expId.getIntPersEmpresaPk());
				
				ctaExpediente = cuentaFacade.getListaCuentaPorPkTodoEstado(ctaIdExp);
				if(ctaExpediente != null){
					if(ctaExpediente.getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						blnValido = Boolean.FALSE;
						blnBloquearXCuenta =  Boolean.FALSE;
						strMensajeValidacionCuenta = "";
					}else{
						blnBloquearXCuenta =  Boolean.TRUE;
						blnValido = Boolean.TRUE;
						strMensajeValidacionCuenta = "No se puede Autorizar. Solicitud de Previsión pertenece a una Cuenta No Vigente.";
					}
				}

			}
			
			
		} catch (Exception e) {
			log.error("Error en validarEstadoCuenta ----> "+e);
		}
		return blnValido;
	}
	
	public void limpiarMensajes(){
		
		try {
			strTxtMsgPerfil = "";
			strTxtMsgUsuario = "";
			strTxtMsgValidacion = "";
			strTxtMsgError = "";
			strTxtMsgConfiguracion = "";
			strConfiguracionesEvaluadas = "";
			
		} catch (Exception e) {
			log.error("Error en limpiarMensajes --> "+e);
		}
		
	}
	
	/**
	 * 
	 * @param event
	 */
	public void limpiarCampos(ActionEvent event){
		intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
		estadoPrevisionFiltro = new EstadoPrevision();
		estadoPrevisionFiltro.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
		strTextoPersonaFiltro = null;
		intItemExpedienteFiltro = null;
		intTipoCreditoFiltro = 0; 
		intSubTipoCreditoFiltro=0;
		intTipoBusquedaSucursal =0;
		intIdSucursalFiltro = 0;
		intIdSubsucursalFiltro = 0;
		listaExpedientePrevision = new ArrayList<ExpedientePrevision>();

	}
	
	
		public void seleccionarSucursalBusqueda(){
		try{
			if(intIdSucursalFiltro.intValue()>0){
				listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(intIdSucursalFiltro);
			}else{
				listaSubsucursal = new ArrayList<Subsucursal>();
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	

		/**
		 * 
		 */
public void seleccionarTipoBusquedaSucursal(){
		try{
			if(intTipoBusquedaSucursal.intValue()>0){
				cargarListaTablaSucursal();
				listaSubsucursal = new ArrayList<Subsucursal>();
			}else{
				listaTablaSucursal  = new ArrayList<Tabla>();
				listaSubsucursal = new ArrayList<Subsucursal>();
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
/**
 * 
 * @param event
 */
public void loadSubTipoSolicitudBusqueda(ActionEvent event) {
		log.info("-------------------------------------Debugging solicitudPrevisionController.loadListDocumento-------------------------------------");
		String strIdTipoSolicitudBusq = null;
		Integer intIdTipoSolicitudBusq = null;
		
		try {
			strIdTipoSolicitudBusq = getRequestParameter("pIntTipoSolicitudBusquedaAut");
			intIdTipoSolicitudBusq = new Integer(strIdTipoSolicitudBusq);
			System.out.println("TIPO DE SOLICITUD busqueda ---> "+strIdTipoSolicitudBusq);

			if(!intIdTipoSolicitudBusq.equals("0")){

				if(intIdTipoSolicitudBusq.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)){
						listaSubTipoSolicitudBusqueda = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO));

				} else if(intIdTipoSolicitudBusq.equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)){
					listaSubTipoSolicitudBusqueda = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));  
							
				} else  if(intIdTipoSolicitudBusq.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
					listaSubTipoSolicitudBusqueda = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_RETIRO));  			
				} 
				
				//Ordenamos los subtipos por int
				Collections.sort(listaSubTipoSolicitudBusqueda, new Comparator<Tabla>(){
					public int compare(Tabla uno, Tabla otro) {
						return uno.getIntOrden().compareTo(otro.getIntOrden());
					}
				});
			}
			
		} catch (NumberFormatException e1) {
			log.error("Error loadSubTipoSolicitudBusqueda 1-->"+e1);
		} catch (BusinessException e2) {
			log.error("Error loadSubTipoSolicitudBusqueda 2-->"+e2);} 


	} 
	

/**
 * 
 */
public void showAutorizacionPrevision(ActionEvent event) {
	log.info("-----------------------Debugging CreditoController.showAutorizacionPrestamo-----------------------------");

	List<ConfServSolicitud> listaSolicitudAutorizada = null;
	ConfServSolicitud confServSolicitud = null;
	listaAutorizacionConfigurada = new ArrayList<ConfServSolicitud>();
	autorizacionConfiguradaFinal = new ConfServSolicitud();
	usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
//	Calendar clToday = Calendar.getInstance();
	String strConfiguracionesEvaluadas = "";
	Boolean blnContinuaValidacion = Boolean.TRUE;
	try {
		confServSolicitud = new ConfServSolicitud();
		Integer intTipoCaptacion = registroSeleccionadoBusqueda.getExpedientePrevision().getIntParaTipoCaptacion();
		setStrTxtMsgConfiguracion("");
		
			if(intTipoCaptacion.compareTo(Constante.CAPTACION_AES)==0){
				confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_AES);

			} else if(intTipoCaptacion.compareTo(Constante.CAPTACION_FDO_SEPELIO)==0){
						confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO);
						
			} else if(intTipoCaptacion.compareTo(Constante.CAPTACION_FDO_RETIRO)==0){
						confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_FONDORETIRO);
			}
			confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION);

		listaSolicitudAutorizada = solicitudFacade.buscarConfSolicitudAutorizacion(confServSolicitud, null,	null, null);

		if (listaSolicitudAutorizada != null && listaSolicitudAutorizada.size() > 0) {
			for (ConfServSolicitud solicitud : listaSolicitudAutorizada) {
				if(solicitud.getIntParaTipoRequertoAutorizaCod().compareTo(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION)==0){
					strConfiguracionesEvaluadas = solicitud.getId().getIntItemSolicitud() + ". " +strConfiguracionesEvaluadas;
					solicitud = validarConfiguracion(solicitud);
					if (solicitud != null) {
						listaAutorizacionConfigurada.add(solicitud);
					}
				}
			}
		}

		if (listaAutorizacionConfigurada != null && listaAutorizacionConfigurada.size() > 0) {
			for (ConfServSolicitud solicitud : listaAutorizacionConfigurada) {
				
				//strConfiguracionesEvaluadas = solicitud.getId().getIntItemSolicitud() + ". " +strConfiguracionesEvaluadas;
				// 1. validando lista de perfiles
				if (solicitud.getListaPerfil() != null
						&& solicitud.getListaPerfil().size() > 0) {
					for (ConfServPerfil perfil : solicitud.getListaPerfil()) {
						if(blnContinuaValidacion){
						log.info("perfil.getIntIdPerfilPk(): "+ perfil.getIntIdPerfilPk());
						if (usuario.getPerfil().getId().getIntIdPerfil().equals(perfil.getIntIdPerfilPk())) {
							System.out.println("PERFIL APROBADO --> "+ perfil.getIntIdPerfilPk());
							blnContinuaValidacion = Boolean.FALSE;
							autorizacionConfiguradaFinal = solicitud;
							formAutorizacionPrevisionRendered = true;
							setStrTxtMsgPerfil("");
							listarEncargadosAutorizar();
							break;
						} else {
							formAutorizacionPrevisionRendered = false;
							setStrTxtMsgPerfil("El Perfil no concuerda con el que se ha configurado en la Autorización de la Solicitud.");
						}
					}
					}
				}
				// 2. validando lista de usuarios
				if (solicitud.getListaUsuario() != null	&& solicitud.getListaUsuario().size() > 0) {
					for (ConfServUsuario $usuario : solicitud.getListaUsuario()) {
						if(blnContinuaValidacion){
						log.info("$usuario.getIntPersUsuarioPk(): "+ $usuario.getIntPersUsuarioPk());
						if (usuario.getIntPersPersonaPk().equals($usuario.getIntPersUsuarioPk())) {
							System.out.println("USUARIO APROBADO --> "	+ usuario.getStrUsuario());
							autorizacionConfiguradaFinal = solicitud;
							blnContinuaValidacion = Boolean.FALSE;
							formAutorizacionPrevisionRendered = true;
							setStrTxtMsgUsuario("");
							if (listarEncargadosAutorizar() == true) {

							}
							break;
						} else {
							formAutorizacionPrevisionRendered = false;
							setStrTxtMsgUsuario("El Usuario no concuerda con el que se ha configurado en la Autorización de la Solicitud.");
						}
					}
				}
				}
			}
		}else{
			setStrTxtMsgConfiguracion("No existen Configuraciones para la Autorización o no cumplieron con las condiciones establecidas. ("+strConfiguracionesEvaluadas+")");
			formAutorizacionPrevisionRendered = Boolean.FALSE;
		}
		// para pruebas
		//formAutorizacionPrevisionRendered = true;
	} catch (BusinessException e) {
		System.out.println("BusinessException --> "+e);
		e.printStackTrace();
	}
}
	
public void refrescarEncargadosAutorizar() {
	List<AutorizaPrevision> listaAutorizaPrevision = new ArrayList<AutorizaPrevision>();
	listaAutorizaPrevisionComp = new ArrayList<AutorizaPrevisionComp>();
	AutorizaPrevisionComp autorizaPrevisionComp = null;
	Persona persona = null;
	try {
		listaAutorizaPrevision = solicitudPresvisionService.getListaAutorizaPrevisionPorPkExpediente(registroSeleccionadoBusqueda.getExpedientePrevision().getId());
		
		if (listaAutorizaPrevision != null && listaAutorizaPrevision.size() > 0) {
			for (AutorizaPrevision autorizaPrevision : listaAutorizaPrevision) {
				if(autorizaPrevision.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
					autorizaPrevisionComp = new AutorizaPrevisionComp();
					autorizaPrevisionComp.setAutorizaPrevision(autorizaPrevision);
					persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaPrevision.getIntPersUsuarioAutoriza());
					for (int k = 0; k < persona.getListaDocumento().size(); k++) {
						if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(
										new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
							persona.setDocumento(persona.getListaDocumento().get(k));
							break;
						}
					}
					autorizaPrevisionComp.setPersona(persona);
					listaAutorizaPrevisionComp.add(autorizaPrevisionComp);
				}
			}
		}
	} catch (BusinessException e) {
		System.out.println("BusinessException --> "+e);
		e.printStackTrace();
	}

}


private Boolean isValidoAutorizacion(AutorizaPrevision autorizaPrevision) {
	Boolean validAutorizaPrevision = true;
	strTxtMsgValidacion = "";
	if (autorizaPrevision.getIntParaEstadoAutorizar() == 0) {
		strTxtMsgValidacion = strTxtMsgValidacion
				+ "* Seleccionar Operación. ";
		validAutorizaPrevision = false;
	}
	
	Integer intTipoCaptacion = registroSeleccionadoBusqueda.getExpedientePrevision().getIntParaTipoCaptacion();
	
	if(intTipoCaptacion.compareTo(Constante.CAPTACION_FDO_RETIRO)==0){
		if (autorizaPrevision.getIntParaTipoAureobCod() == 0) {
			strTxtMsgValidacion = strTxtMsgValidacion
					+ "* Seleccionar Tipo de Operación. ";
			validAutorizaPrevision = false;
		}
	}
	
	// -------< validacion agregada 08112012 ----->
	/*
	 * if (listaGarantiaCreditoComp == null) {
	 * setMsgTxtListaCapacidadPago(msgTxtListaCapacidadPago +
	 * "* Deben Garantes Solidarios."); validExpedienteCredito = false; }
	 * else { setMsgTxtListaCapacidadPago(msgTxtListaCapacidadPago); }
	 */

	return validAutorizaPrevision;
}



/**
 * Indica si el usuario ya realizo una autorizacion.
 * 
 * @return boolean blnNoExiste
 */
public boolean existeRegistro(Integer intTipoValidacion) {
	boolean blnNoExiste = true;

	if (listaAutorizaPrevisionComp.size() > 0
			&& listaAutorizaPrevisionComp != null) {
		for (int k = 0; k < listaAutorizaPrevisionComp.size(); k++) {
			AutorizaPrevision autorizaPrevision = new AutorizaPrevision();
			autorizaPrevision = listaAutorizaPrevisionComp.get(k).getAutorizaPrevision();

			if (intTipoValidacion.intValue() == 0) {// usuario
				if (autorizaPrevision.getIntPersUsuarioAutoriza().intValue() == (usuario.getPerfil().getId().getIntIdPerfil().intValue())
					&& autorizaPrevision.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0) {
					blnNoExiste = false;
					break;
				}

			} else if (intTipoValidacion.intValue() == 1) { // perfil
				if (autorizaPrevision.getIntIdPerfilAutoriza().intValue() == (usuario.getPerfil().getId().getIntIdPerfil().intValue())
					&& autorizaPrevision.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0) {
					blnNoExiste = false;
					break;
				}

			}
		}
	}

	return blnNoExiste;
}



public void grabarAutorizacionPrevision(ActionEvent event) throws Exception {
	log.info("-----------------------Debugging CreditoController.grabarAutorizacionPrestamo ----------------------------- 1");
	ExpedientePrevision expedientePrevision = null;
	List<AutorizaPrevision> listaAutorizaPrevision = new ArrayList<AutorizaPrevision>();
	List<AutorizaVerificaPrevision> listaAutorizaVerificacion = new ArrayList<AutorizaVerificaPrevision>();
	Integer intNroPerfiles = null;
	Integer intNroUsuarios = null;
	boolean blnValidarUsuario = false;
	boolean blnValidarPerfil = false;
	Integer intTipoValidacion = new Integer(0); // o-usuario 1-perfil
	boolean blnNoExiste = true;
//	boolean blnSeRegistaronTodos = false;

	usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
	boolean blnEsElUltimo = false;
//	RequisitoPrevisionBO boRequisitoPrevision = null;

	try {
		
//		boRequisitoPrevision = (RequisitoPrevisionBO)TumiFactory.get(RequisitoPrevisionBO.class);
		refrescarEncargadosAutorizar();
		recuperarCuentaCompSocio();
		
		if (isValidoAutorizacion(beanAutorizaPrevision) == false) {
			strTxtMsgValidacion = strTxtMsgValidacion
					+ "No se puede continuar con el proceso de grabación. ";
			return;
		}
		intNroUsuarios = listaAutorizacionConfigurada.get(0).getListaUsuario().size(); // numero de usuarios q deben autorizar
		intNroPerfiles = listaAutorizacionConfigurada.get(0).getListaPerfil().size(); // numero de perfiless que deben  autorizar

		if (intNroUsuarios.intValue() > 0) {
			blnValidarUsuario = true;
		}
		if (intNroPerfiles.intValue() > 0) {
			blnValidarPerfil = true;
			intTipoValidacion = new Integer(1);
		}
		
		beanAutorizaPrevision.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedientePrevision().getId().getIntPersEmpresaPk());
		beanAutorizaPrevision.setIntPersEmpresaAutoriza(usuario.getEmpresa().getIntIdEmpresa());
		beanAutorizaPrevision.setIntPersUsuarioAutoriza(usuario.getIntPersPersonaPk());
		beanAutorizaPrevision.setIntIdPerfilAutoriza(usuario.getPerfil().getId().getIntIdPerfil());
		beanAutorizaPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		beanAutorizaVerificacion.setId(null);
		beanAutorizaVerificacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

		if (archivoDeJu != null) {
			beanAutorizaVerificacion.setIntItemArchivoDeJu( archivoDeJu.getId().getIntItemArchivo());
			beanAutorizaVerificacion.setIntItemHistoricoDeJu(archivoDeJu.getId().getIntItemHistorico());
			beanAutorizaVerificacion.setIntParaTipoArchivoDeJu(archivoDeJu.getId().getIntParaTipoCod());

		}
		if (archivoReniecAut != null) {
			beanAutorizaVerificacion.setIntItemArchivoRen(archivoReniecAut.getId().getIntItemArchivo());
			beanAutorizaVerificacion.setIntItemHistoricoRen(archivoReniecAut.getId().getIntItemHistorico());
			beanAutorizaVerificacion.setIntParaTipoArchivoRen(archivoReniecAut.getId().getIntParaTipoCod());
		}

		expedientePrevision = registroSeleccionadoBusqueda.getExpedientePrevision();

		listaAutorizaPrevision.add(beanAutorizaPrevision);
		listaAutorizaVerificacion.add(beanAutorizaVerificacion);
		expedientePrevision.setListaAutorizaPrevision(listaAutorizaPrevision);
		expedientePrevision.setListaAutorizaVerificaPrevision(listaAutorizaVerificacion);

		listarEncargadosAutorizar();
		
		// Validamos la Operacion - AUTORIZACION
		// ==================================================================================================================================
		if (beanAutorizaPrevision.getIntParaEstadoAutorizar().intValue() == Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO) {

			if (registroSeleccionadoBusqueda.getExpedientePrevision().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0) {
				// Validamos Usuario
				if (blnValidarUsuario) {
					if (blnNoExiste) {
						blnEsElUltimo = faltaSoloUno(intTipoValidacion);
						solicitudPresvisionService.grabarAutorizacionPrevision(expedientePrevision);

					} else {
						strTxtMsgPerfil = strTxtMsgUsuario + "Ya existe una Autorización registrada con el usuario: "
								+ usuario.getStrUsuario()+ ". No se puede continuar con el grabado.";
					}

				}
				
				// Validamos Perfil
				else if (blnValidarPerfil) {
					blnNoExiste = existeRegistro(intTipoValidacion);
					if (blnNoExiste) {
						
						blnEsElUltimo = faltaSoloUno(intTipoValidacion);
						
						if(blnEsElUltimo){
							LibroDiario libroDiario = null;
								RequisitoPrevision requisitoPrev = new RequisitoPrevision();
								requisitoPrev.setId(new RequisitoPrevisionId());
								RequisitoPrevisionId requisitoPrevId = new RequisitoPrevisionId();
								requisitoPrevId.setIntCuentaPk(0);
								requisitoPrevId.setIntItemExpediente(0);
								requisitoPrevId.setIntItemRequisito(0);
								requisitoPrevId.setIntPersEmpresaPrevision(0);
								requisitoPrev.setId(requisitoPrevId);
								requisitoPrev.setIntParaTipoArchivo(0);
								requisitoPrev.setIntParaItemArchivo(0);
								requisitoPrev.setIntParaItemHistorico(0);
								requisitoPrev.setIntItemReqAut(0);
								requisitoPrev.setIntItemReqAutEstDetalle(0);
								requisitoPrev.setIntParaEstado(0);
								requisitoPrev.setIntPersEmpresaPk(0);
								requisitoPrev.setTsFechaRequisito(null);
								
								libroDiario  = autorizacionFacade.aprobarPrevision(beanSocioComp, obtenerPeriodoActual(), requisitoPrev, 
																							registroSeleccionadoBusqueda.getExpedientePrevision(),usuario,
																							expedientePrevision,Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
								log.info("libro diario: "+libroDiario);
						}else{
							solicitudPresvisionService.grabarAutorizacionPrevision(expedientePrevision);
						}

					} else {
						strTxtMsgPerfil = strTxtMsgPerfil
								+ "Ya existe una Autorización con el Perfil "
								+ usuario.getPerfil().getStrDescripcion()
								+ " id: "
								+ usuario.getPerfil().getId()
										.getIntIdPerfil()
								+ ". No se puede continuar con el grabado.";
					}

					/*if (blnSeRegistaronTodos) {
						cambioEstadoPrevision(expedientePrevision,Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
						List<FallecidoPrevision> listFallecidos = null;
						listFallecidos = previsionFacade.getListaFallecidosPrevisionPorExpediente(registroSeleccionadoBusqueda);
						if(listFallecidos != null){
							cambioEstadoPersonaFallecida(listFallecidos);	
						}
					}*/

				}

			} else {
				strTxtMsgError = "La Solicitud solo puede Autorizarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
				return;
			}

			// Validamos la Operacion - OBSERVAR
			// ==================================================================================================================================
		} else if (beanAutorizaPrevision.getIntParaEstadoAutorizar().intValue() == Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_OBSERVAR_PRESTAMO) {

			if (registroSeleccionadoBusqueda.getExpedientePrevision().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0) {

				// Validamos Usuario
				if (blnValidarUsuario) {
					blnNoExiste = existeRegistro(intTipoValidacion);
					if (blnNoExiste) {
						solicitudPresvisionService.grabarAutorizacionPrevision(expedientePrevision);
						previsionFacade.eliminarVerificaAutorizacionAdjuntosPorObservacion(expedientePrevision);

						cambioEstadoPrevision(expedientePrevision,Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO);

					} else {
						strTxtMsgPerfil = strTxtMsgPerfil
								+ "Ya existe una Autorización registrada con el usuario: "
								+ usuario.getStrUsuario()
								+ ". No se puede continuar con el grabado.";
					}

				}
				// Validamos Perfil
				else if (blnValidarPerfil) {
					blnNoExiste = existeRegistro(intTipoValidacion);
					if (blnNoExiste) {
						solicitudPresvisionService.grabarAutorizacionPrevision(expedientePrevision);
						previsionFacade.eliminarVerificaAutorizacionAdjuntosPorObservacion(expedientePrevision);

						cambioEstadoPrevision(expedientePrevision, Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO);

					} else {
						strTxtMsgPerfil = strTxtMsgPerfil
								+ "Ya existe una Autorización con el Perfil "
								+ usuario.getPerfil().getStrDescripcion()
								+ " id: "
								+ usuario.getPerfil().getId()
										.getIntIdPerfil()
								+ ". No se puede continuar con el grabado.";
					}

				}

			} else {
				strTxtMsgError = "La Solicitud solo puede Observarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
				return;
			}

			// Validamos la Operacion - RECHAZAR
			// ==================================================================================================================================
		} else if (beanAutorizaPrevision.getIntParaEstadoAutorizar().intValue() == Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_PRESTAMO) {
			if (registroSeleccionadoBusqueda.getExpedientePrevision().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0) {
				// Validamos Usuario
				if (blnValidarUsuario) {
					blnNoExiste = existeRegistro(intTipoValidacion);
					if (blnNoExiste) {
						solicitudPresvisionService.grabarAutorizacionPrevision(expedientePrevision);
						cambioEstadoPrevision(expedientePrevision, Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO);

					} else {
						strTxtMsgPerfil = strTxtMsgPerfil
								+ "Ya existe una Autorización registarda con el usuario: "
								+ usuario.getStrUsuario()
								+ ". No se puede continuar con el grabado.";
					}
					// COPIAR LO DE PERFIL

				}
				// Validamos Perfil
				else if (blnValidarPerfil) {
					blnNoExiste = existeRegistro(intTipoValidacion);
					if (blnNoExiste) {
						solicitudPresvisionService.grabarAutorizacionPrevision(expedientePrevision);
						cambioEstadoPrevision(expedientePrevision,Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO);

					} else {
						strTxtMsgPerfil = strTxtMsgPerfil
								+ "Ya existe una una Autorización con el Perfil "
								+ usuario.getPerfil().getStrDescripcion()
								+ " id: "
								+ usuario.getPerfil().getId()
										.getIntIdPerfil()
								+ ". No se puede continuar con el grabado.";
					}

				}

			} else {
				strTxtMsgError = "La Solicitud solo puede Rechazarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
			}

		}
		limpiarFormAutorizacionPrevision();
		cancelarGrabarAutorizacionPrevision(event);


	} catch (BusinessException e) {
		log.info("Error al grabarAutorizacionPrestamo --> " + e);
	}
}


/**
 * Cambia de estado al expediente, agregando un registro de Estado.
 * @param expedienteCredito
 * @throws Exception
 */
private void cambioEstadoPrevision(ExpedientePrevision expedientePrevision, Integer intParaEstadoPrevisionCod) throws Exception {
	EstadoPrevision estadoPrevision = null;
	EstadoPrevisionId estadoPrevisionId = null;
	try {
		estadoPrevision = new EstadoPrevision();
		
		estadoPrevision.setId(estadoPrevisionId);
		estadoPrevision.setTsFechaEstado(new Timestamp(new Date().getTime()));
		estadoPrevision.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
		estadoPrevision.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
		estadoPrevision.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
		estadoPrevision.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
		estadoPrevision.setIntParaEstado(intParaEstadoPrevisionCod);
		//expedientePrevision.getListaEstadoPrevision().add(estadoPrevision);
		
		solicitudPresvisionService.grabarEstadoPrevision(estadoPrevision,registroSeleccionadoBusqueda.getExpedientePrevision().getId());

	} catch (Exception e) {
		log.error("Error en cambioEstadoPrevision ---> "+e);
	}
	
}

/**
 * 
 * @param confServSolicitud
 * @return
 */
public ConfServSolicitud validarConfiguracion(ConfServSolicitud confServSolicitud) {
	Boolean hasMontDesde = false;
	Boolean hasMontHasta = false;
	Boolean hasCuotaDesde = false;
	Boolean hasCuotaHasta = false;
	Integer nroValidaciones = null;
	Integer contAprob = new Integer(0);
	Boolean boAprueba = new Boolean(false);
	ConfServSolicitud solicitud = null;
	Calendar clToday = Calendar.getInstance();
	String strResumen = "";
	solicitud = confServSolicitud;
	strResumen = solicitud.getId().getIntItemSolicitud()+"- No pasa las validaciones de: ";
	nroValidaciones = new Integer(3); // Se inicializa en 3 xq se toma en cuenta (1)Vigencia, (2) Estado, (3)Tipo y subtipo.

	if (solicitud.getBdMontoDesde() != null) {
		hasMontDesde = true;
		nroValidaciones++;
	}
	if (solicitud.getBdMontoHasta() != null) {
		hasMontHasta = true;
		nroValidaciones++;
	}
	if (solicitud.getBdCuotaDesde() != null) {
		hasCuotaDesde = true;
		nroValidaciones++;
	}
	if (solicitud.getBdCuotaHasta() != null) {
		hasCuotaHasta = true;
		nroValidaciones++;
	}

	// 1. Validando la Vigencia
	if ((clToday.getTime().compareTo(solicitud.getDtDesde()) > 0 && solicitud.getDtHasta() == null)
			|| (clToday.getTime().compareTo(solicitud.getDtDesde()) > 0 && clToday.getTime().compareTo(solicitud.getDtHasta()) < 0)) {
		contAprob++;
	}else{
		strResumen = strResumen + " 1. Validando la Vigencia. ";
	}
	// 2. Validando el estado
	if (solicitud.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) == 0) {
		contAprob++;
	}else{
		strResumen = strResumen + " 2. Validando el estado. ";
	}
	
	
	Integer intTipoCaptacion = registroSeleccionadoBusqueda.getExpedientePrevision().getIntParaTipoCaptacion();
	Integer intTipoOperacion = 0 ;
		if(intTipoCaptacion.compareTo(Constante.CAPTACION_AES)==0){
			intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_AES;

		} else if(intTipoCaptacion.compareTo(Constante.CAPTACION_FDO_SEPELIO)==0){
			intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO;
					
		} else if(intTipoCaptacion.compareTo(Constante.CAPTACION_FDO_RETIRO)==0){
			intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_FONDORETIRO;
		}

	
	// 3. Validando el tipo y subtipo
	if (registroSeleccionadoBusqueda.getExpedientePrevision().getIntParaSubTipoOperacion().compareTo(solicitud.getIntParaSubtipoOperacionCod())==0
		&& (solicitud.getIntParaTipoOperacionCod().compareTo(intTipoOperacion))==0) {
		contAprob++;
		/*public static final Integer PARAM_T_TIPOOPERACION_LIQUIDACIONDECUENTA = 1;
		public static final Integer PARAM_T_TIPOOPERACION_PRESTAMO = 2;
		public static final Integer PARAM_T_TIPOOPERACION_ORDENCREDITO = 3;
		public static final Integer PARAM_T_TIPOOPERACION_ACTIVIDAD = 4;
		public static final Integer PARAM_T_TIPOOPERACION_FONDOSEPELIO = 5;
		public static final Integer PARAM_T_TIPOOPERACION_FONDORETIRO = 6;
		public static final Integer PARAM_T_TIPOOPERACION_AES = 7;*/
		
	}else{
		strResumen = strResumen + " 3. Validando el tipo y subtipo. ";
	}
	// 4. Validando Monto Desde
	if (hasMontDesde){
			if (registroSeleccionadoBusqueda.getExpedientePrevision().getBdMontoNetoBeneficio().compareTo(solicitud.getBdMontoDesde()) >= 0){
				contAprob++;
			}else{
				strResumen = strResumen + " 4. Validando Monto Desde. ";
			}
	}
	// 5. Validando Monto Hasta
	if (hasMontHasta){
		if (registroSeleccionadoBusqueda.getExpedientePrevision().getBdMontoNetoBeneficio().compareTo(solicitud.getBdMontoHasta()) <= 0){
			contAprob++;
		}else{
			strResumen = strResumen + " 5. Validando Monto Hasta. ";
		}
	}
	// 6. Validando Nro Cuotas Desde
	if (hasCuotaDesde){
		if(registroSeleccionadoBusqueda.getExpedientePrevision().getIntNumeroCuotaFondo().compareTo(solicitud.getBdCuotaDesde().intValue()) >= 0){
			contAprob++;
		}else{
			strResumen = strResumen + " 6. Validando Nro Cuotas Desde. ";
		}	
	}
			
	// 7. Validando Nro Cuotas Hasta
	if (hasCuotaHasta){
		if(registroSeleccionadoBusqueda.getExpedientePrevision().getIntNumeroCuotaFondo().compareTo(solicitud.getBdCuotaHasta().intValue()) <= 0){
			contAprob++;
		}else{
			strResumen = strResumen + " 7. Validando Nro Cuotas Hasta. ";
		}
	}
			
	// 8. Validando que exista al menos uno
	/*if ((solicitud.getBdMontoDesde() != null)
			|| (solicitud.getBdMontoHasta() != null)
			|| (solicitud.getBdCuotaDesde() != null)
			|| (solicitud.getBdCuotaHasta() != null)) {
		contAprob++;
	}*/
	// ------------------
	// }
	System.out.println("===========================================================");
	System.out.println(" NRO DE REQAUT " + solicitud.getId().getIntItemSolicitud());
	System.out.println(" NRO DE VALIDACIONES EXISTENTES " + nroValidaciones);
	System.out.println(" NRO DE VALIDACIONES APROBADAS " + contAprob);
	System.out.println(" hasCuotaDesde --->  " + hasCuotaDesde);
	System.out.println(" hasCuotaHasta --->  " + hasCuotaHasta);
	
	System.out.println(" OBSERVACION " + strResumen);
	System.out.println("===========================================================");

	if (nroValidaciones == contAprob)
		boAprueba = true;
	if (boAprueba) {
		return solicitud;
	} else {
		return null;
	}
}


/**
 * Muestran a los encargados que YA HAN realizado alguna accion: autorizar, rechazar u observar.
 * 
 * @return
 */
public boolean listarEncargadosAutorizar() {
	boolean isValidEncaragadoAutorizar = true;
	List<AutorizaPrevision> listaAutorizaPrevision = new ArrayList<AutorizaPrevision>();
	listaAutorizaPrevisionComp = new ArrayList<AutorizaPrevisionComp>();
	AutorizaPrevisionComp autorizaPrevisionComp = null;
	Persona persona = null;
	try {
		listaAutorizaPrevision =  solicitudPresvisionService.getListaAutorizaPrevisionPorPkExpediente(registroSeleccionadoBusqueda.getExpedientePrevision().getId());
		if (listaAutorizaPrevision != null && listaAutorizaPrevision.size() > 0) {
			for (AutorizaPrevision autorizaPrevision : listaAutorizaPrevision) {
				autorizaPrevisionComp = new AutorizaPrevisionComp();
				autorizaPrevisionComp.setAutorizaPrevision(autorizaPrevision);
				persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaPrevision.getIntPersUsuarioAutoriza());
				for (int k = 0; k < persona.getListaDocumento().size(); k++) {
					if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
						persona.setDocumento(persona.getListaDocumento().get(k));
						break;
					}
				}
				// recuperando el perfil del usuario
				Perfil perfil = null;
				PerfilId perfilId = null;
				autorizaPrevisionComp.setPersona(persona);
				
				perfilId = new PerfilId();
				perfilId.setIntPersEmpresaPk(autorizaPrevisionComp.getAutorizaPrevision().getIntPersEmpresaAutoriza());
				perfilId.setIntIdPerfil(autorizaPrevisionComp.getAutorizaPrevision().getIntIdPerfilAutoriza());
				// recuperando el perfil del usuario
				perfil = permisoFacade.getPerfilYListaPermisoPerfilPorPkPerfil(perfilId);
				if(perfil != null){
					autorizaPrevisionComp.setStrPerfil(perfil.getStrDescripcion());
				}
				listaAutorizaPrevisionComp.add(autorizaPrevisionComp);
			}
		}

	} catch (BusinessException e) {
		log.error("Error en listarEncargadosAutorizar ---> "+e);
		e.printStackTrace();
	}

	return isValidEncaragadoAutorizar;
}
	

/**
 * Recupera y carga la lista de los docuemntos adjuntos de la solicitud de prevision.
 */
public void mostrarArchivosAdjuntosNousado() {
	log.info("-----------------------Debugging SolicitudPrestamoController.mostrarArchivosAdjuntos-----------------------------");
	ConfSolicitudFacadeRemote facade = null;
	TablaFacadeRemote tablaFacade = null;
	EstructuraFacadeRemote estructuraFacade = null;
	ConfServSolicitud confServSolicitud = null;
	String strToday = Constante.sdf.format(new Date());
	Date dtToday = null;
	List<ConfServSolicitud> listaDocAdjuntos = new ArrayList<ConfServSolicitud>();
	EstructuraDetalle estructuraDet = null;
	List<EstructuraDetalle> listaEstructuraDet = new ArrayList<EstructuraDetalle>();
	listaRequisitoPrevisionComp = new ArrayList<RequisitoPrevisionComp>();
	RequisitoPrevisionComp requisitoPrevisionComp;
	try {
		dtToday = Constante.sdf.parse(strToday);
		SocioComp beanSocioComp = new SocioComp();

		Integer intTipoCaptacion = registroSeleccionadoBusqueda.getExpedientePrevision().getIntParaTipoCaptacion();
		Integer intTipoOperacion = 0;
		Integer intReqDesc = new Integer(0);
			if(intTipoCaptacion.compareTo(Constante.CAPTACION_AES)==0){
				intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_AES;
				intReqDesc = new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_AES);
			} else if(intTipoCaptacion.compareTo(Constante.CAPTACION_FDO_SEPELIO)==0){
				intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO;
				intReqDesc = new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDOSEPELIO);						
			} else if(intTipoCaptacion.compareTo(Constante.CAPTACION_FDO_RETIRO)==0){
				intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_FONDORETIRO;
				intReqDesc = new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDORETIRO);
			}

		facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
		estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
		tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
		confServSolicitud = new ConfServSolicitud();
		listaDocAdjuntos = facade.buscarConfSolicitudRequisito(	confServSolicitud, null, dtToday, 1);
		
		for(int i=0; i<listaDocAdjuntos.size();i++){
			System.out.println(""+listaDocAdjuntos.get(i).getIntParaTipoOperacionCod());
			System.out.println(""+listaDocAdjuntos.get(i).getIntParaSubtipoOperacionCod());
			System.out.println(""+listaDocAdjuntos.get(i).getId().getIntItemSolicitud());
			System.out.println(""+listaDocAdjuntos.get(i).getId().getIntPersEmpresaPk());
			System.out.println("-------------------------------------------------------");
		}
		
		if (listaDocAdjuntos != null && listaDocAdjuntos.size() > 0) {
			forSolicitud: for (ConfServSolicitud solicitud : listaDocAdjuntos) {
				if (solicitud.getIntParaTipoOperacionCod().compareTo(intTipoOperacion)==0) {
					if (solicitud.getIntParaSubtipoOperacionCod().compareTo(registroSeleccionadoBusqueda.getExpedientePrevision().getIntParaSubTipoOperacion())==0) {
						if (solicitud.getListaEstructuraDetalle() != null) {
							for (ConfServEstructuraDetalle estructuraDetalle : solicitud.getListaEstructuraDetalle()) {
								estructuraDet = new EstructuraDetalle();
								estructuraDet.setId(new EstructuraDetalleId());
								// getSocioEstructuraDeOrigenPorPkSocio
								// **********************************************************************
								SocioEstructura socioEsctructura = new SocioEstructura();
								socioEsctructura = socioFacade.getSocioEstructuraDeOrigenPorPkSocio(beanSocioComp.getSocio().getId());

							
								estructuraDet.getId().setIntNivel(socioEsctructura.getIntNivel());
								estructuraDet.getId().setIntCodigo(	socioEsctructura.getIntCodigo());
								listaEstructuraDet = estructuraFacade.getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(
												estructuraDet.getId(),socioEsctructura.getIntTipoSocio(),
												socioEsctructura.getIntModalidad());

								if (listaEstructuraDet != null
										&& listaEstructuraDet.size() > 0) {
									for (EstructuraDetalle estructDetalle : listaEstructuraDet) {
										// if
										// (estructuraDetalle.getIntCodigoPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo())
										// &&
										// estructuraDetalle.getIntNivelPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntNivel())
										if (estructuraDetalle.getIntCodigoPk().compareTo(socioEsctructura.getIntCodigo())==0
												&& estructuraDetalle.getIntNivelPk().compareTo(socioEsctructura.getIntNivel())==0
												&& estructuraDetalle.getIntCaso().compareTo(estructDetalle.getId().getIntCaso())==0
												&& estructuraDetalle.getIntItemCaso().compareTo(estructDetalle.getId().getIntItemCaso())==0) {
											if (solicitud.getListaDetalle() != null	&& solicitud.getListaDetalle().size() > 0) {

												List<RequisitoPrevisionComp> listaRequisitoPrevisionCompTemp = new ArrayList<RequisitoPrevisionComp>();
												for (ConfServDetalle detalle : solicitud.getListaDetalle()) {

													if (detalle.getId().getIntPersEmpresaPk().compareTo(estructuraDetalle.getId().getIntPersEmpresaPk())==0
															&& detalle.getId().getIntItemSolicitud().compareTo(estructuraDetalle.getId().getIntItemSolicitud())==0) {

														requisitoPrevisionComp = new RequisitoPrevisionComp();
														requisitoPrevisionComp.setDetalle(detalle);
														// listaRequisitoCreditoComp.add(requisitoCreditoComp);
														listaRequisitoPrevisionCompTemp.add(requisitoPrevisionComp);
													}
												}

												List<Tabla> listaTablaRequisitos = new ArrayList<Tabla>();
												// validamos que solo se muestre las de agrupamioento A = b
												//listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO),"B");

												listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(intReqDesc,"B");
												for (int i = 0; i < listaTablaRequisitos.size(); i++) {
													for (int j = 0; j < listaRequisitoPrevisionCompTemp.size(); j++) {
														if ((listaRequisitoPrevisionCompTemp.get(j).getDetalle().getIntParaTipoDescripcion().intValue()) == 
															(listaTablaRequisitos.get(i).getIntIdDetalle().intValue())) {
															listaRequisitoPrevisionComp.add(listaRequisitoPrevisionCompTemp.get(j));
														}
													}
												}
												break forSolicitud;
											}
										}
									}
								}
							}
						}
					}
				}
			}

		}

	} catch (BusinessException e) {
		log.error("Error BusinessException  mostrarArchivosAdjuntos ---> "+e);
		e.printStackTrace();
	} catch (EJBFactoryException e) {
		log.error("Error EJBFactoryException  mostrarArchivosAdjuntos ---> "+e);
		e.printStackTrace();
	} catch (ParseException e) {
		log.error("Error ParseException  mostrarArchivosAdjuntos ---> "+e);
		e.printStackTrace();
	}
}

/**
 * 
 * @param event
 */
public void irModificarSolicitudPrevisionAutoriza(ActionEvent event){
	System.out.println("eventeventevent --> "+event);
	System.out.println("registroSeleccionadoregistroSeleccionadoregistroSeleccionado --> "+registroSeleccionadoBusqueda);
	System.out.println();
	SolicitudPrevisionController solicitudPrevisionController = (SolicitudPrevisionController) getSessionBean("solicitudPrevisionController");
	solicitudPrevisionController.irModificarSolicitudPrevisionAutoriza2(event, registroSeleccionadoBusqueda.getExpedientePrevision());	
//	solicitudPrevisionController.irVerSolicitudPrevisionAutoriza2(event, registroSeleccionadoBusqueda.getExpedientePrevision());
}	


/**
 * 
 * @param event
 */
public void cancelarGrabarAutorizacionPrevision(ActionEvent event) {
	limpiarFormAutorizacionPrevision();
	formAutorizacionPrevisionRendered = false;
}


/**
 * 
 */
public void aceptarAdjuntarDeJu() {
	FileUploadControllerServicio fileUploadController = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");

	try {
		archivoDeJu = fileUploadController.getArchivoDeJu();
		fileUploadController = new FileUploadControllerServicio();
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
}


/**
 * 
 */
public void aceptarAdjuntarReniecAut() {
	FileUploadControllerServicio fileUploadController = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
	try {
		archivoReniecAut = fileUploadController.getArchivoReniecAut();
		fileUploadController = new FileUploadControllerServicio();
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
}


/**
 * 
 */
public void quitarDeJu() {
	try {
		archivoDeJu = null;
		((FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio"))
				.setArchivoInfoCorp(null);
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
}


/**
 * 
 */
public void quitarReniecAut() {
	try {
		archivoReniecAut = null;
		((FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio"))
				.setArchivoReniec(null);
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
}


/**
 * 
 */
private void recuperarAdjuntosAutorizacion(	ExpedientePrevision registroSeleccionadoBusqueda)
		throws BusinessException {
	List<AutorizaVerificaPrevision> listaVerificacionBD = null;
	try {

		listaVerificacionBD =  solicitudPresvisionService.getListaVerificaPrevisionPorPkExpediente(registroSeleccionadoBusqueda.getId());
		if (listaVerificacionBD.size() > 0) {
			beanAutorizaVerificacion = listaVerificacionBD.get(0);
			if (listaVerificacionBD.size() > 0) {
				AutorizaVerificaPrevision verificacion = new AutorizaVerificaPrevision();
				verificacion = listaVerificacionBD.get(0);

				if (verificacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
					&& verificacion.getIntItemArchivoDeJu() != null
					&& verificacion.getIntItemHistoricoDeJu()!= null
					&& verificacion.getIntParaTipoArchivoDeJu() != null) {

						Archivo archivoDJ = new Archivo();
						ArchivoId archivoIdDJ = new ArchivoId();

						archivoIdDJ.setIntItemArchivo(verificacion.getIntItemArchivoDeJu());
						archivoIdDJ.setIntItemHistorico(verificacion.getIntItemHistoricoDeJu());
						archivoIdDJ.setIntParaTipoCod(verificacion.getIntParaTipoArchivoDeJu());

						archivoDJ = generalFacade.getArchivoPorPK(archivoIdDJ);
						if (archivoDJ.getId().getIntParaTipoCod() != null
							&& archivoDJ.getId().getIntItemArchivo() != null
							&& archivoDJ.getId().getIntItemHistorico() != null) {
								archivoDJ.setRutaActual(archivoDJ.getTipoarchivo().getStrRuta());
								archivoDJ.setStrNombrearchivo(archivoDJ.getStrNombrearchivo());
								archivoDeJu = archivoDJ;
						}

				}  else {archivoDeJu = null;}

				if (verificacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
					&& verificacion.getIntItemArchivoRen() != null
					&& verificacion.getIntItemHistoricoRen() != null
					&& verificacion.getIntParaTipoArchivoRen() != null) {

					Archivo archivoR = new Archivo();
					ArchivoId archivoIdR = new ArchivoId();

					archivoIdR.setIntItemArchivo(verificacion.getIntItemArchivoRen());
					archivoIdR.setIntItemHistorico(verificacion.getIntItemHistoricoRen());
					archivoIdR.setIntParaTipoCod(verificacion.getIntParaTipoArchivoRen());

					archivoR = generalFacade.getArchivoPorPK(archivoIdR);
					if (archivoR.getId().getIntParaTipoCod() != null
						&& archivoR.getId().getIntItemArchivo() != null
						&& archivoR.getId().getIntItemHistorico() != null) {
						archivoR.setRutaActual(archivoR.getTipoarchivo().getStrRuta());
						archivoR.setStrNombrearchivo(archivoR.getStrNombrearchivo());
						archivoReniecAut = archivoR;
					}

				} else {archivoReniecAut = null;}
			}
		} else {
			beanAutorizaVerificacion = new AutorizaVerificaPrevision();
			archivoDeJu = null;
			archivoReniecAut = null;

		}

	} catch (BusinessException e) {
		log.info("Error durante solicitudPrestamoFacade.getListaVerifificacionesCreditoPorPkExpediente --> "
				+ e);
	}
}


/**
 * 
 * @param expedientePrevision
 * @return
 */
public List<AutorizaPrevisionComp> recuperarEncargadosAutorizar(ExpedientePrevision expedientePrevision) {
	List<AutorizaPrevision> listaAutorizaPrevision = new ArrayList<AutorizaPrevision>();
	List<AutorizaPrevisionComp> listaAutorizaPrevisionComp = null;
	AutorizaPrevisionComp autorizaPrevisionComp = null;
	Persona persona = null;
	
	try {
		listaAutorizaPrevision = solicitudPresvisionService.getListaAutorizaPrevisionPorPkExpediente(registroSeleccionadoBusqueda.getExpedientePrevision().getId());
		if (listaAutorizaPrevision != null && listaAutorizaPrevision.size() > 0) {
			listaAutorizaPrevisionComp = new ArrayList<AutorizaPrevisionComp>();
			for (AutorizaPrevision autorizaPrevision : listaAutorizaPrevision) {
				autorizaPrevisionComp = new AutorizaPrevisionComp();
				autorizaPrevisionComp.setAutorizaPrevision(autorizaPrevision);
				
				persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaPrevision.getIntPersUsuarioAutoriza());
				for (int k = 0; k < persona.getListaDocumento().size(); k++) {
					if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
						persona.setDocumento(persona.getListaDocumento().get(k));
						break;
					}
				}
				autorizaPrevisionComp.setPersona(persona);
				listaAutorizaPrevisionComp.add(autorizaPrevisionComp);
			}
		}
	} catch (BusinessException e) {
		e.printStackTrace();
	}
	return listaAutorizaPrevisionComp;
}


/**
 * 
 * @param lstFallecido
 */
/*
public void cambioEstadoPersonaFallecida(List<FallecidoPrevision> lstFallecido){
	Persona persona = null;
	try {
		if(lstFallecido != null && !lstFallecido.isEmpty()){
			for(int k=0;k<lstFallecido.size();k++){
				persona = new Persona();
				persona = lstFallecido.get(k).getPersona();	
			}
			persona.setIntEstadoCod(Constante.PARAM_PERSONA_ESTADO_FALLECIDO);
			personaFacade.modificarPersona(persona);
		}
		
	} catch (Exception e) {
		System.out.println("Error en cambioEstadoPersonaFallecida --> "+e);
	}

}*/



/**
 * Valida que si con el registro de la presente autorizacion se inica los registros de prevision y cambio de estado.
 * @param intTipoValidacion
 * @param listaAutorizacionConfigurada
 * @return
 */
//private boolean faltaSoloUno(Integer intTipoValidacion, List<ConfServSolicitud> listaAutorizacionConfigurada) {
private boolean faltaSoloUno(Integer intTipoValidacion) {
//	Integer intNroUsuariosConf = null;
//	Integer intNroPerfilesConf = null;
	BigDecimal bdNroUsuariosConf = null;
	BigDecimal bdNroPerfilesConf = null;
	
	boolean blnEsElUltimo = false;
	Integer intRecuperados = 0;

	if (autorizacionConfiguradaFinal != null) {

		bdNroUsuariosConf = BigDecimal.ZERO;
		bdNroPerfilesConf = BigDecimal.ZERO;
		
			if(autorizacionConfiguradaFinal.getListaPerfil() != null && !autorizacionConfiguradaFinal.getListaPerfil().isEmpty()){
				bdNroPerfilesConf = bdNroPerfilesConf.add(new BigDecimal(autorizacionConfiguradaFinal.getListaPerfil().size()));	
			}
			
			if(autorizacionConfiguradaFinal.getListaUsuario() != null && !autorizacionConfiguradaFinal.getListaUsuario().isEmpty()){
				bdNroUsuariosConf = bdNroUsuariosConf.add(new BigDecimal(autorizacionConfiguradaFinal.getListaUsuario().size()));	
			}	

		refrescarEncargadosAutorizar();
		intRecuperados = listaAutorizaPrevisionComp.size();

		if (intTipoValidacion.compareTo(1)==0) { // perfil
			BigDecimal bdDif = BigDecimal.ZERO;
			bdDif = bdNroPerfilesConf.subtract(new BigDecimal(intRecuperados));
			
			if (bdDif.compareTo(BigDecimal.ONE) == 0) {
				blnEsElUltimo = true;
			}

		} else if (intTipoValidacion.compareTo(0)== 0) { // usuario
			BigDecimal bdDif = BigDecimal.ZERO;
			bdDif = bdNroPerfilesConf.subtract(new BigDecimal(intRecuperados));
			
			if (bdDif.compareTo(BigDecimal.ONE) == 0) {
				blnEsElUltimo = true;
			}
		}

	}

	return blnEsElUltimo;
}




/**
 * Recupera el beanSocioComp en base al expediente de prevision - cuentaiD
 */
private void recuperarCuentaCompSocio() {
	CuentaId cuentaId = null;
	Cuenta cuentaSocio = null;
	Integer intIdPersona = null;
	Persona persona = null;
	SocioComp socioComp = null;
//	List<ExpedienteLiquidacionDetalle> listaDetalles = null;
	List<CuentaIntegrante> listaCuentaIntegranteSocio = null;	
	
	try {
		if(	registroSeleccionadoBusqueda != null){

			
			if(registroSeleccionadoBusqueda.getExpedientePrevision().getId().getIntCuentaPk() != null ){
				cuentaId =  new CuentaId();
				cuentaId.setIntCuenta(registroSeleccionadoBusqueda.getExpedientePrevision().getId().getIntCuentaPk());
				cuentaId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedientePrevision().getId().getIntPersEmpresaPk() );

				
				cuentaSocio = cuentaFacade.getCuentaPorId(cuentaId);
		
				listaCuentaIntegranteSocio = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaSocio.getId());
		
					if(listaCuentaIntegranteSocio != null){
						intIdPersona = listaCuentaIntegranteSocio.get(0).getId().getIntPersonaIntegrante();
						persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
						if (persona != null) {
							if (persona.getListaDocumento() != null
									&& persona.getListaDocumento().size() > 0) {
								for (Documento documento : persona.getListaDocumento()) {
									if (documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))) {
										persona.setDocumento(documento);
										break;
									}
								}
							}							
							
							socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
																						persona.getDocumento().getStrNumeroIdentidad(),
																						Constante.PARAM_EMPRESASESION);
		
							for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
								if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
									socioComp.getSocio().setSocioEstructura(socioEstructura);
								}
							}
							beanSocioComp = socioComp;
						}
					}	
			}
			
		}
	} catch (NumberFormatException e) {
		System.out.println("NumberFormatExceptionNumberFormatException---> "+e);
		e.printStackTrace();
	} catch (BusinessException e) {
		System.out.println("BusinessExceptionBusinessException--> "+e);
		e.printStackTrace();
	}
	
}



/**
 * Obtiene el periodo actual en el formato YYYYMM
 * @return
 * @throws Exception
 */
public Integer obtenerPeriodoActual() throws Exception {
	String strPeriodo = "";
	Calendar cal = Calendar.getInstance();
	int año = cal.get(Calendar.YEAR);
	int mes = cal.get(Calendar.MONTH);
	mes = mes + 1;
	if (mes < 10) {
		strPeriodo = año + "0" + mes;
	} else {
		strPeriodo = año + "" + mes;
	}
	return Integer.parseInt(strPeriodo);
}

//---------------------------------------------------------------------------------------------------------->
	



	public static Logger getLog() {
		return log;
	}


	public Archivo getArchivoReniecAut() {
		return archivoReniecAut;
	}


	public void setArchivoReniecAut(Archivo archivoReniecAut) {
		this.archivoReniecAut = archivoReniecAut;
	}


	public static void setLog(Logger log) {
		AutorizacionPrevisionController.log = log;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public AutorizaPrevision getBeanAutorizaPrevision() {
		return beanAutorizaPrevision;
	}


	public void setBeanAutorizaPrevision(AutorizaPrevision beanAutorizaPrevision) {
		this.beanAutorizaPrevision = beanAutorizaPrevision;
	}


	public Integer getIntParaTipoDescripcion() {
		return intParaTipoDescripcion;
	}


	public void setIntParaTipoDescripcion(Integer intParaTipoDescripcion) {
		this.intParaTipoDescripcion = intParaTipoDescripcion;
	}


	public Integer getIntParaTipoOperacionPersona() {
		return intParaTipoOperacionPersona;
	}


	public void setIntParaTipoOperacionPersona(Integer intParaTipoOperacionPersona) {
		this.intParaTipoOperacionPersona = intParaTipoOperacionPersona;
	}

	public List<AutorizaPrevisionComp> getListaAutorizaPrevisionComp() {
		return listaAutorizaPrevisionComp;
	}


	public void setListaAutorizaPrevisionComp(
			List<AutorizaPrevisionComp> listaAutorizaPrevisionComp) {
		this.listaAutorizaPrevisionComp = listaAutorizaPrevisionComp;
	}


	public List<AutorizaVerificaPrevision> getListaAutorizaVerificacionAdjuntos() {
		return listaAutorizaVerificacionAdjuntos;
	}


	public void setListaAutorizaVerificacionAdjuntos(
			List<AutorizaVerificaPrevision> listaAutorizaVerificacionAdjuntos) {
		this.listaAutorizaVerificacionAdjuntos = listaAutorizaVerificacionAdjuntos;
	}


	public List<ConfServSolicitud> getListaAutorizacionConfigurada() {
		return listaAutorizacionConfigurada;
	}


	public void setListaAutorizacionConfigurada(
			List<ConfServSolicitud> listaAutorizacionConfigurada) {
		this.listaAutorizacionConfigurada = listaAutorizacionConfigurada;
	}


	public List<Tabla> getListaTipoOperacion() {
		return listaTipoOperacion;
	}


	public void setListaTipoOperacion(List<Tabla> listaTipoOperacion) {
		this.listaTipoOperacion = listaTipoOperacion;
	}


	public ConfSolicitudFacadeLocal getSolicitudFacade() {
		return solicitudFacade;
	}


	public void setSolicitudFacade(ConfSolicitudFacadeLocal solicitudFacade) {
		this.solicitudFacade = solicitudFacade;
	}


	public GeneralFacadeRemote getGeneralFacade() {
		return generalFacade;
	}


	public void setGeneralFacade(GeneralFacadeRemote generalFacade) {
		this.generalFacade = generalFacade;
	}


	public SocioFacadeRemote getSocioFacade() {
		return socioFacade;
	}


	public void setSocioFacade(SocioFacadeRemote socioFacade) {
		this.socioFacade = socioFacade;
	}


	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}


	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}





	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}


	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}


	public PermisoFacadeRemote getPermisoFacade() {
		return permisoFacade;
	}


	public void setPermisoFacade(PermisoFacadeRemote permisoFacade) {
		this.permisoFacade = permisoFacade;
	}


	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}


	public List<RequisitoPrevisionComp> getListaRequisitoPrevisionComp() {
		return listaRequisitoPrevisionComp;
	}


	public void setListaRequisitoPrevisionComp(
			List<RequisitoPrevisionComp> listaRequisitoPrevisionComp) {
		this.listaRequisitoPrevisionComp = listaRequisitoPrevisionComp;
	}


	public Archivo getArchivoDeJu() {
		return archivoDeJu;
	}


	public void setArchivoDeJu(Archivo archivoDeJu) {
		this.archivoDeJu = archivoDeJu;
	}


	public void setBeanAutorizaVerificacion(
			AutorizaVerificaPrevision beanAutorizaVerificacion) {
		this.beanAutorizaVerificacion = beanAutorizaVerificacion;
	}


	public Integer getIntTipoCreditoFiltro() {
		return intTipoCreditoFiltro;
	}


	public void setIntTipoCreditoFiltro(Integer intTipoCreditoFiltro) {
		this.intTipoCreditoFiltro = intTipoCreditoFiltro;
	}


	public Integer getIntSubTipoCreditoFiltro() {
		return intSubTipoCreditoFiltro;
	}


	public void setIntSubTipoCreditoFiltro(Integer intSubTipoCreditoFiltro) {
		this.intSubTipoCreditoFiltro = intSubTipoCreditoFiltro;
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


	public Integer getIntItemExpedienteFiltro() {
		return intItemExpedienteFiltro;
	}


	public void setIntItemExpedienteFiltro(Integer intItemExpedienteFiltro) {
		this.intItemExpedienteFiltro = intItemExpedienteFiltro;
	}


	public EstadoPrevision getEstadoPrevisionFiltro() {
		return estadoPrevisionFiltro;
	}


	public void setEstadoPrevisionFiltro(EstadoPrevision estadoPrevisionFiltro) {
		this.estadoPrevisionFiltro = estadoPrevisionFiltro;
	}


	public List<Tabla> getListaTablaEstadoPago() {
		return listaTablaEstadoPago;
	}


	public void setListaTablaEstadoPago(List<Tabla> listaTablaEstadoPago) {
		this.listaTablaEstadoPago = listaTablaEstadoPago;
	}


	public List<Tabla> getListaTablaTipoDocumento() {
		return listaTablaTipoDocumento;
	}


	public void setListaTablaTipoDocumento(List<Tabla> listaTablaTipoDocumento) {
		this.listaTablaTipoDocumento = listaTablaTipoDocumento;
	}


	public Integer getIntTipoBusquedaFechaFiltro() {
		return intTipoBusquedaFechaFiltro;
	}


	public void setIntTipoBusquedaFechaFiltro(Integer intTipoBusquedaFechaFiltro) {
		this.intTipoBusquedaFechaFiltro = intTipoBusquedaFechaFiltro;
	}


	public Integer getIntTipoBusquedaSucursal() {
		return intTipoBusquedaSucursal;
	}


	public void setIntTipoBusquedaSucursal(Integer intTipoBusquedaSucursal) {
		this.intTipoBusquedaSucursal = intTipoBusquedaSucursal;
	}


	public List<Tabla> getListaTipoBusquedaSucursal() {
		return listaTipoBusquedaSucursal;
	}


	public void setListaTipoBusquedaSucursal(List<Tabla> listaTipoBusquedaSucursal) {
		this.listaTipoBusquedaSucursal = listaTipoBusquedaSucursal;
	}


	public Integer getIntIdSubsucursalFiltro() {
		return intIdSubsucursalFiltro;
	}


	public void setIntIdSubsucursalFiltro(Integer intIdSubsucursalFiltro) {
		this.intIdSubsucursalFiltro = intIdSubsucursalFiltro;
	}


	public Integer getIntIdSucursalFiltro() {
		return intIdSucursalFiltro;
	}


	public void setIntIdSucursalFiltro(Integer intIdSucursalFiltro) {
		this.intIdSucursalFiltro = intIdSucursalFiltro;
	}

//
//	public List getListaExpedientePrevisionX() {
//		return listaExpedientePrevisionX;
//	}
//
//
//	public void setListaExpedientePrevisionX(List listaExpedientePrevisionX) {
//		this.listaExpedientePrevisionX = listaExpedientePrevisionX;
//	}


	public List<ExpedientePrevision> getListaExpedientePrevision() {
		return listaExpedientePrevision;
	}


	public void setListaExpedientePrevision(
			List<ExpedientePrevision> listaExpedientePrevision) {
		this.listaExpedientePrevision = listaExpedientePrevision;
	}


	public List<Tabla> getListaTipoVinculo() {
		return listaTipoVinculo;
	}


	public void setListaTipoVinculo(List<Tabla> listaTipoVinculo) {
		this.listaTipoVinculo = listaTipoVinculo;
	}


	public List<ExpedientePrevision> getListaExpedientePrevisionSocio() {
		return listaExpedientePrevisionSocio;
	}


	public void setListaExpedientePrevisionSocio(
			List<ExpedientePrevision> listaExpedientePrevisionSocio) {
		this.listaExpedientePrevisionSocio = listaExpedientePrevisionSocio;
	}


	public List<Subsucursal> getListaSubsucursal() {
		return listaSubsucursal;
	}


	public void setListaSubsucursal(List<Subsucursal> listaSubsucursal) {
		this.listaSubsucursal = listaSubsucursal;
	}


	public List<Tabla> getListaTablaSucursal() {
		return listaTablaSucursal;
	}


	public void setListaTablaSucursal(List<Tabla> listaTablaSucursal) {
		this.listaTablaSucursal = listaTablaSucursal;
	}


	public PrevisionFacadeRemote getPrevisionFacade() {
		return previsionFacade;
	}


	public void setPrevisionFacade(PrevisionFacadeRemote previsionFacade) {
		this.previsionFacade = previsionFacade;
	}


	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}


	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
	}


	public AutorizaVerificaPrevision getBeanAutorizaVerificacion() {
		return beanAutorizaVerificacion;
	}


	public List<Tabla> getListaSubTipoSolicitudBusqueda() {
		return listaSubTipoSolicitudBusqueda;
	}


	public void setListaSubTipoSolicitudBusqueda(
			List<Tabla> listaSubTipoSolicitudBusqueda) {
		this.listaSubTipoSolicitudBusqueda = listaSubTipoSolicitudBusqueda;
	}


	/*public ExpedientePrevision getRegistroSeleccionadoBusqueda() {
		return registroSeleccionadoBusqueda;
	}


	public void setRegistroSeleccionadoBusqueda(
			ExpedientePrevision registroSeleccionadoBusqueda) {
		this.registroSeleccionadoBusqueda = registroSeleccionadoBusqueda;
	}*/


	public Persona getPersonaBusqueda() {
		return personaBusqueda;
	}


	public void setPersonaBusqueda(Persona personaBusqueda) {
		this.personaBusqueda = personaBusqueda;
	}


	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}


	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
	}


	public Integer getIntIdTipoPersona() {
		return intIdTipoPersona;
	}


	public void setIntIdTipoPersona(Integer intIdTipoPersona) {
		this.intIdTipoPersona = intIdTipoPersona;
	}


	public String getStrNroSolicitud() {
		return strNroSolicitud;
	}


	public void setStrNroSolicitud(String strNroSolicitud) {
		this.strNroSolicitud = strNroSolicitud;
	}


	public Integer getIntTipoSucursalBusq() {
		return intTipoSucursalBusq;
	}


	public void setIntTipoSucursalBusq(Integer intTipoSucursalBusq) {
		this.intTipoSucursalBusq = intTipoSucursalBusq;
	}


	public Integer getIntIdEstadoSolicitud() {
		return intIdEstadoSolicitud;
	}


	public void setIntIdEstadoSolicitud(Integer intIdEstadoSolicitud) {
		this.intIdEstadoSolicitud = intIdEstadoSolicitud;
	}


	public Date getDtFecInicio() {
		return dtFecInicio;
	}


	public void setDtFecInicio(Date dtFecInicio) {
		this.dtFecInicio = dtFecInicio;
	}


	public Date getDtFecFin() {
		return dtFecFin;
	}


	public void setDtFecFin(Date dtFecFin) {
		this.dtFecFin = dtFecFin;
	}


	public List<Estructura> getListEstructura() {
		return listEstructura;
	}


	public void setListEstructura(List<Estructura> listEstructura) {
		this.listEstructura = listEstructura;
	}


	public List<Subsucursal> getListSubSucursal() {
		return listSubSucursal;
	}


	public void setListSubSucursal(List<Subsucursal> listSubSucursal) {
		this.listSubSucursal = listSubSucursal;
	}


	public List<Archivo> getListaArchivo() {
		return listaArchivo;
	}


	public void setListaArchivo(List<Archivo> listaArchivo) {
		this.listaArchivo = listaArchivo;
	}


	public Tabla getTablaEstado() {
		return tablaEstado;
	}


	public void setTablaEstado(Tabla tablaEstado) {
		this.tablaEstado = tablaEstado;
	}

	public String getStrTxtMsgPerfil() {
		return strTxtMsgPerfil;
	}


	public void setStrTxtMsgPerfil(String strTxtMsgPerfil) {
		this.strTxtMsgPerfil = strTxtMsgPerfil;
	}


	public String getStrTxtMsgUsuario() {
		return strTxtMsgUsuario;
	}


	public void setStrTxtMsgUsuario(String strTxtMsgUsuario) {
		this.strTxtMsgUsuario = strTxtMsgUsuario;
	}


	public String getStrTxtMsgValidacion() {
		return strTxtMsgValidacion;
	}


	public void setStrTxtMsgValidacion(String strTxtMsgValidacion) {
		this.strTxtMsgValidacion = strTxtMsgValidacion;
	}


	public String getStrTxtMsgError() {
		return strTxtMsgError;
	}


	public void setStrTxtMsgError(String strTxtMsgError) {
		this.strTxtMsgError = strTxtMsgError;
	}


	public Boolean getFormAutorizacionPrevisionRendered() {
		return formAutorizacionPrevisionRendered;
	}


	public void setFormAutorizacionPrevisionRendered(
			Boolean formAutorizacionPrevisionRendered) {
		this.formAutorizacionPrevisionRendered = formAutorizacionPrevisionRendered;
	}


	public boolean isBlnIsRetiro() {
		return blnIsRetiro;
	}


	public void setBlnIsRetiro(boolean blnIsRetiro) {
		this.blnIsRetiro = blnIsRetiro;
	}


	public AutorizacionPrevisionFacadeRemote getAutorizacionFacade() {
		return autorizacionFacade;
	}


	public void setAutorizacionFacade(
			AutorizacionPrevisionFacadeRemote autorizacionFacade) {
		this.autorizacionFacade = autorizacionFacade;
	}


	public solicitudPrevisionService getSolicitudPresvisionService() {
		return solicitudPresvisionService;
	}


	public void setSolicitudPresvisionService(
			solicitudPrevisionService solicitudPresvisionService) {
		this.solicitudPresvisionService = solicitudPresvisionService;
	}


	public Boolean getBlnMostrarAutorizacion() {
		return blnMostrarAutorizacion;
	}


	public void setBlnMostrarAutorizacion(Boolean blnMostrarAutorizacion) {
		this.blnMostrarAutorizacion = blnMostrarAutorizacion;
	}


	public CuentaFacadeRemote getCuentaFacade() {
		return cuentaFacade;
	}


	public void setCuentaFacade(CuentaFacadeRemote cuentaFacade) {
		this.cuentaFacade = cuentaFacade;
	}


	public ConceptoFacadeRemote getConceptoFacade() {
		return conceptoFacade;
	}


	public void setConceptoFacade(ConceptoFacadeRemote conceptoFacade) {
		this.conceptoFacade = conceptoFacade;
	}


	public LibroDiarioFacadeRemote getLibroDiarioFacade() {
		return libroDiarioFacade;
	}


	public void setLibroDiarioFacade(LibroDiarioFacadeRemote libroDiarioFacade) {
		this.libroDiarioFacade = libroDiarioFacade;
	}


	public SocioComp getBeanSocioComp() {
		return beanSocioComp;
	}


	public void setBeanSocioComp(SocioComp beanSocioComp) {
		this.beanSocioComp = beanSocioComp;
	}


	public String getStrTxtMsgConfiguracion() {
		return strTxtMsgConfiguracion;
	}


	public void setStrTxtMsgConfiguracion(String strTxtMsgConfiguracion) {
		this.strTxtMsgConfiguracion = strTxtMsgConfiguracion;
	}


	public String getStrConfiguracionesEvaluadas() {
		return strConfiguracionesEvaluadas;
	}


	public void setStrConfiguracionesEvaluadas(String strConfiguracionesEvaluadas) {
		this.strConfiguracionesEvaluadas = strConfiguracionesEvaluadas;
	}


	public ConfServSolicitud getAutorizacionConfiguradaFinal() {
		return autorizacionConfiguradaFinal;
	}


	public void setAutorizacionConfiguradaFinal(
			ConfServSolicitud autorizacionConfiguradaFinal) {
		this.autorizacionConfiguradaFinal = autorizacionConfiguradaFinal;
	}


	public Boolean getBlnBloquearXCuenta() {
		return blnBloquearXCuenta;
	}


	public void setBlnBloquearXCuenta(Boolean blnBloquearXCuenta) {
		this.blnBloquearXCuenta = blnBloquearXCuenta;
	}


	public String getStrMensajeValidacionCuenta() {
		return strMensajeValidacionCuenta;
	}


	public void setStrMensajeValidacionCuenta(String strMensajeValidacionCuenta) {
		this.strMensajeValidacionCuenta = strMensajeValidacionCuenta;
	}


	public Integer getIntBusqTipo() {
		return intBusqTipo;
	}


	public void setIntBusqTipo(Integer intBusqTipo) {
		this.intBusqTipo = intBusqTipo;
	}


	public String getStrBusqCadena() {
		return strBusqCadena;
	}


	public void setStrBusqCadena(String strBusqCadena) {
		this.strBusqCadena = strBusqCadena;
	}


	public String getStrBusqNroSol() {
		return strBusqNroSol;
	}


	public void setStrBusqNroSol(String strBusqNroSol) {
		this.strBusqNroSol = strBusqNroSol;
	}


	public Integer getIntBusqSucursal() {
		return intBusqSucursal;
	}


	public void setIntBusqSucursal(Integer intBusqSucursal) {
		this.intBusqSucursal = intBusqSucursal;
	}


	public Integer getIntBusqEstado() {
		return intBusqEstado;
	}


	public void setIntBusqEstado(Integer intBusqEstado) {
		this.intBusqEstado = intBusqEstado;
	}


	public Integer getIntBusqTipoPrevision() {
		return intBusqTipoPrevision;
	}


	public void setIntBusqTipoPrevision(Integer intBusqTipoPrevision) {
		this.intBusqTipoPrevision = intBusqTipoPrevision;
	}


	public Integer getIntBusqSubTipoPrevision() {
		return intBusqSubTipoPrevision;
	}


	public void setIntBusqSubTipoPrevision(Integer intBusqSubTipoPrevision) {
		this.intBusqSubTipoPrevision = intBusqSubTipoPrevision;
	}


	public Date getDtBusqFechaEstadoDesde() {
		return dtBusqFechaEstadoDesde;
	}


	public void setDtBusqFechaEstadoDesde(Date dtBusqFechaEstadoDesde) {
		this.dtBusqFechaEstadoDesde = dtBusqFechaEstadoDesde;
	}


	public Date getDtBusqFechaEstadoHasta() {
		return dtBusqFechaEstadoHasta;
	}


	public void setDtBusqFechaEstadoHasta(Date dtBusqFechaEstadoHasta) {
		this.dtBusqFechaEstadoHasta = dtBusqFechaEstadoHasta;
	}


	public List<Sucursal> getListaSucursalBusqueda() {
		return listaSucursalBusqueda;
	}


	public void setListaSucursalBusqueda(List<Sucursal> listaSucursalBusqueda) {
		this.listaSucursalBusqueda = listaSucursalBusqueda;
	}


	public List<Subsucursal> getListaSubSucursaBusqueda() {
		return listaSubSucursaBusqueda;
	}


	public void setListaSubSucursaBusqueda(List<Subsucursal> listaSubSucursaBusqueda) {
		this.listaSubSucursaBusqueda = listaSubSucursaBusqueda;
	}


	public List<ExpedientePrevisionComp> getListaExpedientePrevisionComp() {
		return listaExpedientePrevisionComp;
	}


	public void setListaExpedientePrevisionComp(
			List<ExpedientePrevisionComp> listaExpedientePrevisionComp) {
		this.listaExpedientePrevisionComp = listaExpedientePrevisionComp;
	}


	public ExpedientePrevisionComp getRegistroSeleccionadoBusqueda() {
		return registroSeleccionadoBusqueda;
	}


	public void setRegistroSeleccionadoBusqueda(
			ExpedientePrevisionComp registroSeleccionadoBusqueda) {
		this.registroSeleccionadoBusqueda = registroSeleccionadoBusqueda;
	}

	public List<Tabla> getListaEstadoSolicitud() {
		return listaEstadoSolicitud;
	}

	public void setListaEstadoSolicitud(List<Tabla> listaEstadoSolicitud) {
		this.listaEstadoSolicitud = listaEstadoSolicitud;
	}

	

}
