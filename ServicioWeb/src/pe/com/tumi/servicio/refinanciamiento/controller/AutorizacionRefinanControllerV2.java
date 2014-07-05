package pe.com.tumi.servicio.refinanciamiento.controller;

import java.io.IOException;
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
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger; 

import pe.com.tumi.cobranza.cuentacte.facade.CuentacteFacadeRemote;
import pe.com.tumi.common.FileUtil;
import pe.com.tumi.common.MyFile;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadController;
import pe.com.tumi.fileupload.FileUploadControllerServicio;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
//import pe.com.tumi.movimiento.concepto.domain.Expediente;
//import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
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
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaVerificacion;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
//import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.AutorizaCreditoComp;
//import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CapacidadCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeLocal;
/**
 * autorizacion sin limpiar codigo , todo comentado
 * @author jchavez
 *
 */
public class AutorizacionRefinanControllerV2 {
	protected static Logger log = Logger.getLogger(AutorizacionRefinanController.class);	
	private Usuario usuario;
	private AutorizaCredito beanAutorizaCredito;
	private AutorizaVerificacion beanAutorizaVerificacion;
	private ExpedienteCreditoComp expedienteCreditoCompSelected;

	private Integer intIdTipoPersona;
	private Integer intBusqSolicPtmo;
	private String strNroSolicitud;
	private Integer intTipoSucursalBusq;
	private Integer intTotalSucursales;
	private Integer intIdDependencia;
	private Integer intIdTipoPrestamo;
	private Integer intIdEstadoPrestamo;
	private Date dtFecInicio;
	private Date dtFecFin;

	private Boolean formAutorizacionRefinanRendered;
	private String strTxtMsgPerfil;
	private String strTxtMsgUsuario;
	private String strTxtMsgVigencia;
	private String strTxtMsgError;
	private String strTxtMsgValidacion;

	private List<RequisitoCreditoComp> listaRequisitoCreditoComp;
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoOperacionPersona;
	private Archivo archivoInfoCorp;
	private Archivo archivoReniec;
	
	private List<AutorizaCreditoComp> listaAutorizaCreditoComp;
	private List<ExpedienteCreditoComp> listaAutorizacionCreditoComp = new ArrayList<ExpedienteCreditoComp>();
	private List<AutorizaVerificacion> listaAutorizaVerificacionAdjuntos;
	private List<ConfServSolicitud> listaAutorizacionConfigurada = null;
	
	private List<Tabla> listaTipoOperacion;
	private List<Sucursal> listSucursal;

	private ConfSolicitudFacadeLocal solicitudFacade = null;
	private GeneralFacadeRemote generalFacade = null;

	private SocioFacadeRemote socioFacade = null;
	private TablaFacadeRemote tablaFacade = null;
	private SolicitudPrestamoFacadeLocal solicitudPrestamoFacade = null;
	private PersonaFacadeRemote personaFacade = null;
	private LibroDiarioFacadeRemote libroDiarioFacade = null;
	private PermisoFacadeRemote permisoFacade = null;
	private ConceptoFacadeRemote conceptofacade = null;
	private CuentacteFacadeRemote cuentaCteFacade = null;
	private SocioComp beanSocioComp = null;
	private Date dtHoy = null;
	
	private String 	mensajeOperacion;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private SolicitudRefinanController solicitudRefinanController;
	
	// parametros de busqueda - CGD 17.10.2013
	private Integer intBusqTipo; 	
	private String strBusqCadena;		    
	private String strBusqNroSol;		   
	private Integer intBusqSucursal;			  
	private Integer intBusqEstado;		    
	private Date dtBusqFechaEstadoDesde;  
	private Date dtBusqFechaEstadoHasta;
	private EmpresaFacadeRemote empresaFacade = null;
	private	List<Sucursal> listaSucursal;
	private List<ExpedienteCreditoComp> listaDetalleRefinanciamiento;
	private Integer intBusqTipoCreditoEmpresa;
	private List<Tabla> listaTablaCreditoEmpresa;
	
	


	public AutorizacionRefinanControllerV2() {
		beanAutorizaCredito = new AutorizaCredito();
		beanAutorizaVerificacion = new AutorizaVerificacion();
		formAutorizacionRefinanRendered = false;
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		solicitudRefinanController = (SolicitudRefinanController) getSessionBean("solicitudRefinanController");

		try {
			solicitudFacade = (ConfSolicitudFacadeLocal) EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			solicitudPrestamoFacade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			conceptofacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			cuentaCteFacade = (CuentacteFacadeRemote) EJBFactory.getRemote(CuentacteFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			ordenarAlfabeticamenteSuc();
			listaTablaCreditoEmpresa = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 5);



		} catch (Exception e) {
			log.error("error: " + e.getMessage());
		} finally {
			inicio();
		}
			
	}
	
	
	public List<Sucursal> ordenarAlfabeticamenteSuc(){
		if(listaSucursal != null && !listaSucursal.isEmpty()){
			//Ordenamos por nombre
			Collections.sort(listaSucursal, new Comparator<Sucursal>(){
				public int compare(Sucursal uno, Sucursal otro) {
					return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
				}
			});	
		}
			return listaSucursal;
	}
	
	public void inicio() {
		usuario = new Usuario();
		// pe.com.tumi.seguridad.login.domain.Usuario
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
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
		
		Calendar fecHoy = Calendar.getInstance();
		dtHoy = fecHoy.getTime();
		
		
		try {
			listaTipoOperacion = new ArrayList<Tabla>();
				//tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONPRESTAMO), "B");
		
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}/* catch (BusinessException e) {
			e.printStackTrace();
		}*/
		
		
		limpiarFormAutorizacionRefinan();
		strTxtMsgError = "";
		strTxtMsgPerfil = "";
		strTxtMsgValidacion = "";
		strTxtMsgVigencia = "";
		formAutorizacionRefinanRendered = false;
	}
	
	public void cancelarGrabarAutorizacionRefinan(ActionEvent event) {
		limpiarFormAutorizacionRefinan();
		formAutorizacionRefinanRendered = false;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public String getLimpiarAutorizacion(){
		inicio();
		listaAutorizacionCreditoComp= new ArrayList<ExpedienteCreditoComp>();
		beanSocioComp = new SocioComp();
		beanAutorizaCredito = new AutorizaCredito();
		beanAutorizaVerificacion = new AutorizaVerificacion();
		return "";
	}
	
	/**
	 * 
	 */
	public void limpiarFormAutorizacionRefinan() {
		beanAutorizaCredito = new AutorizaCredito();
		beanAutorizaVerificacion = new AutorizaVerificacion();
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		mensajeOperacion = "";
		mostrarMensajeExito= Boolean.FALSE;
		mostrarMensajeError=Boolean.FALSE;
		archivoInfoCorp = null;
		archivoReniec = null;
	}
	
	/**
	 * Realiza la Busqueda de las Solictudes a Autorizar.
	 * 
	 * @param event
	 */
	/*public void listarAutorizacionRefinanciamiento(ActionEvent event) {
		log.info("-----------------------Debugging CreditoController.listarCredito-----------------------------");
		SolicitudPrestamoFacadeLocal facade = null;
		listaAutorizacionCreditoComp.clear();
		inicio();
		limpiarFormAutorizacionRefinan();
		// cancelarGrabarAutorizacionPrestamo(event);

		try {
			facade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			ExpedienteCreditoComp o = new ExpedienteCreditoComp();
			if (intIdTipoPersona != null && intIdTipoPersona != 0)
				o.setIntIdTipoPersona(intIdTipoPersona);
			if (intBusqSolicPtmo != null && intBusqSolicPtmo != 0)
				o.setIntBusqSolicPtmo(intBusqSolicPtmo);
			if (strNroSolicitud != null && !strNroSolicitud.equals(""))
				o.setStrNroSolicitud(strNroSolicitud);
			if (intTipoSucursalBusq != null && intTipoSucursalBusq != 0)
				o.setIntTipoSucursalBusq(intTipoSucursalBusq);
			if (intTotalSucursales != null && intTotalSucursales != 0)
				o.setIntTotalSucursales(intTotalSucursales);
			if (intIdDependencia != null && intIdDependencia != 0)
				o.setIntIdDependencia(intIdDependencia);
			if (intIdTipoPrestamo != null && intIdTipoPrestamo != 0)
				o.setIntIdTipoPrestamo(intIdTipoPrestamo);
			if (intIdEstadoPrestamo != null && intIdEstadoPrestamo != 0)
				o.setIntIdEstadoPrestamo(intIdEstadoPrestamo);
			if (dtFecInicio != null)
				o.setDtFechaInicio(dtFecInicio);
			if (dtFecFin != null)
				o.setDtFechaFin(dtFecFin);
			
			listaAutorizacionCreditoComp = facade.getListaExpedienteRefinanciamientoCompDeBusqueda(o);
			//listaExpedienteCreditoComp = facade.getListaExpedienteRefinanciamientoCompDeBusqueda(o);
			System.out.println("");
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}

	}*/
	
	/**
	 * 
	 */
	public void listarExpedientesRefinanciamiento(ActionEvent event) {
		log.info("-----------------------Debugging CreditoController.listarSolicitudPrestamo -----------------------------");
		SolicitudPrestamoFacadeLocal facade = null;
		ExpedienteCreditoComp expedienteCompBusq = null;

		try {
			facade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			cancelarGrabarAutorizacionRefinan(event);
			limpiarGrillaBusqueda();
			// getBuscarAutRefinanciamientoCompFiltros
			expedienteCompBusq = new ExpedienteCreditoComp();
			expedienteCompBusq.setIntBusqTipo(intBusqTipo);
			expedienteCompBusq.setStrBusqCadena(strBusqCadena);
			expedienteCompBusq.setStrBusqNroSol(strBusqNroSol);
			expedienteCompBusq.setIntBusqSucursal(intBusqSucursal);
			expedienteCompBusq.setIntBusqEstado(intBusqEstado);
			expedienteCompBusq.setDtBusqFechaEstadoDesde(dtBusqFechaEstadoDesde);
			expedienteCompBusq.setDtBusqFechaEstadoHasta(dtBusqFechaEstadoHasta);
			expedienteCompBusq.setIntBusqTipoCreditoEmpresa(intBusqTipoCreditoEmpresa);
			
			
			listaAutorizacionCreditoComp = facade.getListaBusqAutRefFiltros(expedienteCompBusq);
			log.info("busqueda satisfactoria !!!");
			
			/*listaAutorizacionCreditoComp = facade.getBuscarAutRefinanciamientoCompFiltros(intBusqTipo,strBusqCadena,strBusqNroSol,intBusqSucursal,
					intBusqEstado,dtBusqFechaEstadoDesde,dtBusqFechaEstadoHasta);*/
			
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void limpiarFiltros (ActionEvent event){
		
		try {
			intBusqTipo = 0; 	
			strBusqCadena = "";		    
			strBusqNroSol = "";		   
			intBusqSucursal = 0;			  
			intBusqEstado =0;		    
			dtBusqFechaEstadoDesde = null;  
			dtBusqFechaEstadoHasta = null;
			//private	List<Sucursal> listaSucursal;

		} catch (Exception e) {
			log.error("Error en limpiarFiltros ---> "+e);
		}
		
	}
	
	
	/**
	 * Limpia la grilla de busqueda de refinanciamientos
	 */
	public void limpiarGrillaBusqueda(){

		try {
			if(listaAutorizacionCreditoComp != null){
				listaAutorizacionCreditoComp.clear();
			}else{
				listaAutorizacionCreditoComp = new ArrayList<ExpedienteCreditoComp>();
			}

		} catch (Exception e) {
			log.error("Error en limpiarGrillaBusqueda ---> "+e);
		}
		
	}
	
	/**
	 * 
	 * @param event
	 */
	public void setSelectedExpedienteCredito(ActionEvent event) {
		log.info("-------------------------------------Debugging SocioController.setSelectedSocioNatu-------------------------------------");
		log.info("activeRowKey: " + getRequestParameter("rowExpedienteCredito"));
		String selectedRow = getRequestParameter("rowExpedienteCredito");
		ExpedienteCreditoComp expedienteCreditoComp = null;
		limpiarFormAutorizacionRefinan();
		inicio();
		// cancelarGrabarAutorizacionPrestamo(event);
		for (int i = 0; i < listaAutorizacionCreditoComp.size(); i++) {
			expedienteCreditoComp = listaAutorizacionCreditoComp.get(i);
			if (i == Integer.parseInt(selectedRow)) {
				setExpedienteCreditoCompSelected(expedienteCreditoComp);
				try {
					recuperarAdjuntosAutorizacion(expedienteCreditoComp);
				} catch (BusinessException e) {
					log.info("Error en setSelectedExpedienteCredito --> " + e);
				}
				break;
			}
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			expedienteCreditoCompSelected = (ExpedienteCreditoComp)event.getComponent().getAttributes().get("itemRefinan");
			//Integer intEstado = expedienteCreditoCompSelected.getEstadoCredito().getIntParaEstadoCreditoCod();
			Integer intEstado =  expedienteCreditoCompSelected.getExpedienteCredito().getIntEstadoCreditoUltimo();
//			Integer intTipoCaptacion = expedienteCreditoCompSelected.getExpedienteCredito().getIntParaTipoCreditoCod();	

				
			if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
				/*blnBotonActulizar = true;
				blnBotonVer = true;
				blnBotonEliminar = true;*/
			}
			if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0){
				/*blnBotonActulizar = true;
				blnBotonVer = true;
				blnBotonEliminar = false;*/
			}
			if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)==0){
				/*blnBotonActulizar = false;
				blnBotonVer = true;
				blnBotonEliminar = false;*/

			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	
	private void recuperarAdjuntosAutorizacion(
			ExpedienteCreditoComp expedienteSeleccionado)
			throws BusinessException {
		List<AutorizaVerificacion> listaVerificacionBD = null;
		// listaVerificacionBD = new ArrayList<AutorizaVerificacion>();
		try {

			listaVerificacionBD = solicitudPrestamoFacade.getListaVerifificacionesCreditoPorPkExpediente(expedienteCreditoCompSelected.getExpedienteCredito().getId());
			// expedienteCreditoCompSelected.getExpedienteCredito().getListaAutorizaVerificacion().clear();
			if (listaVerificacionBD.size() > 0) {
				beanAutorizaVerificacion = listaVerificacionBD.get(0);
				if (listaVerificacionBD.size() > 0) {
					// for(int k=0; k<listaVerificacionBD.size();k++){
					AutorizaVerificacion verificacion = new AutorizaVerificacion();
					verificacion = listaVerificacionBD.get(0);

					if (verificacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
						&& verificacion.getIntItemArchivoRen() != null
						&& verificacion.getIntItemHistoricoRen() != null
						&& verificacion.getIntParaTipoArchivoRenCod() != null) {

							Archivo archivoR = new Archivo();
							ArchivoId archivoIdR = new ArchivoId();
	
							archivoIdR.setIntItemArchivo(verificacion.getIntItemArchivoRen());
							archivoIdR.setIntItemHistorico(verificacion.getIntItemHistoricoRen());
							archivoIdR.setIntParaTipoCod(verificacion.getIntParaTipoArchivoRenCod());
							// archivo.setId(new ArchivoId());
	
							archivoR = generalFacade.getArchivoPorPK(archivoIdR);
							if (archivoR.getId().getIntParaTipoCod() != null
								&& archivoR.getId().getIntItemArchivo() != null
								&& archivoR.getId().getIntItemHistorico() != null) {
									archivoR.setRutaActual(archivoR.getTipoarchivo().getStrRuta());
									archivoR.setStrNombrearchivo(archivoR.getStrNombrearchivo());
									archivoReniec = archivoR;
							}

					}  else {archivoReniec = null;}

					if (verificacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
						&& verificacion.getIntItemArchivoInfo() != null
						&& verificacion.getIntItemHistoricoInfo() != null
						&& verificacion.getIntParaTipoArchivoInfoCod() != null) {

						Archivo archivoI = new Archivo();
						ArchivoId archivoIdI = new ArchivoId();

						archivoIdI.setIntItemArchivo(verificacion.getIntItemArchivoInfo());
						archivoIdI.setIntItemHistorico(verificacion.getIntItemHistoricoInfo());
						archivoIdI.setIntParaTipoCod(verificacion.getIntParaTipoArchivoInfoCod());
						// archivo.setId(new ArchivoId());

						archivoI = generalFacade.getArchivoPorPK(archivoIdI);
						if (archivoI.getId().getIntParaTipoCod() != null
							&& archivoI.getId().getIntItemArchivo() != null
							&& archivoI.getId().getIntItemHistorico() != null) {
							archivoI.setRutaActual(archivoI.getTipoarchivo().getStrRuta());
							archivoI.setStrNombrearchivo(archivoI.getStrNombrearchivo());
							archivoInfoCorp = archivoI;
						}

					} else {archivoInfoCorp = null;}
				}
			} else {
				beanAutorizaVerificacion = new AutorizaVerificacion();
				archivoInfoCorp = null;
				archivoReniec = null;

			}

		} catch (BusinessException e) {
			log.info("Error durante solicitudPrestamoFacade.getListaVerifificacionesCreditoPorPkExpediente --> "
					+ e);
		}
	}
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void reloadCboTipoMotivoEstado(ValueChangeEvent event) {
		Integer intIdOperacion = Integer.parseInt("" + event.getNewValue());
		log.info("intIdOperacion = " + intIdOperacion);
		try {
			listaTipoOperacion = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOMOTIVOESTADOAUTPTMO),intIdOperacion);
		} catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void showAutorizacionRefinanciamiento(ActionEvent event) {
		log.info("-----------------------Debugging CreditoController.showAutorizacionPrestamo-----------------------------");

		List<ConfServSolicitud> listaSolicitudAutorizada = null;
		ConfServSolicitud confServSolicitud = null;
		listaAutorizacionConfigurada = new ArrayList<ConfServSolicitud>();
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
//		Calendar clToday = Calendar.getInstance();
		try {
			setStrTxtMsgUsuario("");
			setStrTxtMsgPerfil("");
			if(listaAutorizaCreditoComp != null && !listaAutorizaCreditoComp.isEmpty()){
				listaAutorizaCreditoComp.clear();
			}
			
			confServSolicitud = new ConfServSolicitud();
			confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO);
			confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION);
			//confServSolicitud = new ConfServSolicitud();
			//confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO);
			listaSolicitudAutorizada = solicitudFacade.buscarConfSolicitudAutorizacion(confServSolicitud, null, null, null);

			if (listaSolicitudAutorizada != null && listaSolicitudAutorizada.size() > 0) {
				for (ConfServSolicitud solicitud : listaSolicitudAutorizada) {
					if(solicitud.getIntParaTipoRequertoAutorizaCod().compareTo(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION)==0){
						solicitud = validarConfiguracion(solicitud);
						if (solicitud != null) {
							listaAutorizacionConfigurada.add(solicitud);
						}
					}
				}
			}
			// colocado para pruebas
			//formAutorizacionRefinanRendered = true;
			
			if (listaAutorizacionConfigurada != null && listaAutorizacionConfigurada.size() > 0) {
				for (ConfServSolicitud solicitud : listaAutorizacionConfigurada) {

					// 1. validando lista de perfiles
					if (solicitud.getListaPerfil() != null && solicitud.getListaPerfil().size() > 0) {
						for (ConfServPerfil perfil : solicitud.getListaPerfil()) {
							log.info("perfil.getIntIdPerfilPk(): " + perfil.getIntIdPerfilPk());
							if (usuario.getPerfil().getId().getIntIdPerfil().compareTo(perfil.getIntIdPerfilPk())==0) {
								System.out.println("PERFIL APROBADO --> "+ perfil.getIntIdPerfilPk());
								formAutorizacionRefinanRendered= true;
								setStrTxtMsgPerfil("");
								listarEncargadosAutorizar();
								break;
							} else {
								formAutorizacionRefinanRendered = false;
								setStrTxtMsgPerfil("El Perfil no concuerda con el que se ha configurado en la Autorización de la Solicitud.");
							}
						}
					}
					// 2. validando lista de usuarios
					if (solicitud.getListaUsuario() != null
							&& solicitud.getListaUsuario().size() > 0) {
						for (ConfServUsuario $usuario : solicitud.getListaUsuario()) {
							log.info("$usuario.getIntPersUsuarioPk(): "	+ $usuario.getIntPersUsuarioPk());
							if (usuario.getIntPersPersonaPk().equals($usuario.getIntPersUsuarioPk())) {
								System.out.println("USUARIO APROBADO --> "+ usuario.getStrUsuario());
								formAutorizacionRefinanRendered = true;
								setStrTxtMsgUsuario("");
								if (listarEncargadosAutorizar() == true) {

								}
								break;
							} else {
								formAutorizacionRefinanRendered = false;
								setStrTxtMsgUsuario("El Usuario no concuerda con el que se ha configurado en la Autorización de la Solicitud.");
							}
						}
					}

					// 3. validando lista de Credito Empresa
						/*
					  if(solicitud.getListaCreditoEmpresa()!=null && solicitud.getListaCreditoEmpresa().size()>0){
						  for(ConfServCreditoEmpresa creditoEmpresa:  solicitud.getListaCreditoEmpresa()){
							  log.info("creditoEmpresa: " + creditoEmpresa.getIntValor());
							  if(expedienteCreditoCompSelected.getExpedienteCredito().getIntParaTipoCreditoCod ().compareTo(creditoEmpresa.getIntValor())==0){
								  formAutorizacionRefinanRendered = true;
								  setStrTxtMsgUsuario("");
								  if(listarEncargadosAutorizar()==true){
								  
								  } 
								  break; 
							  }else{ 
								  formAutorizacionRefinanRendered = false;
								  setStrTxtMsgUsuario("El Tipo de Prestamo no concuerda.");
							  } 
						  } 
					  }
					  */
				}
			}else{
				setStrTxtMsgUsuario("Ninguna configuración pasa exitosamente las validaciones.");
			}

		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public boolean listarEncargadosAutorizar() {
		boolean isValidEncaragadoAutorizar = true;
		List<AutorizaCredito> listaAutorizaCredito = new ArrayList<AutorizaCredito>();
		listaAutorizaCreditoComp = new ArrayList<AutorizaCreditoComp>();
		AutorizaCreditoComp autorizaCreditoComp = null;
		Persona persona = null;
		try {
			listaAutorizaCredito = solicitudPrestamoFacade.getListaAutorizaCreditoPorPkExpediente(expedienteCreditoCompSelected.getExpedienteCredito().getId());
			if (listaAutorizaCredito != null && listaAutorizaCredito.size() > 0) {
				for (AutorizaCredito autorizaCredito : listaAutorizaCredito) {
					if(autorizaCredito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						autorizaCreditoComp = new AutorizaCreditoComp();
						autorizaCreditoComp.setAutorizaCredito(autorizaCredito);
						persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaCredito.getIntPersUsuarioAutoriza());
						for (int k = 0; k < persona.getListaDocumento().size(); k++) {
							if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
								persona.setDocumento(persona.getListaDocumento().get(k));
								break;
							}
						}

						// recuperando el perfil del usuario
						Perfil perfil = null;
						PerfilId perfilId = null;
						autorizaCreditoComp.setPersona(persona);
						
						perfilId = new PerfilId();
						perfilId.setIntPersEmpresaPk(autorizaCreditoComp.getAutorizaCredito().getIntPersEmpresaAutoriza());
						perfilId.setIntIdPerfil(autorizaCreditoComp.getAutorizaCredito().getIntIdPerfilAutoriza());
						// recuperando el perfil del usuario
						perfil = permisoFacade.getPerfilYListaPermisoPerfilPorPkPerfil(perfilId);
						if(perfil != null){
							autorizaCreditoComp.setStrPerfil(perfil.getStrDescripcion());
						}
						listaAutorizaCreditoComp.add(autorizaCreditoComp);
					}
					
				}
			}

			//mostrarArchivosAdjuntos();

		} catch (BusinessException e) {
			e.printStackTrace();
		}

		return isValidEncaragadoAutorizar;
	}
	
	
	/**
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void grabarAutorizacionRefinan(ActionEvent event) throws Exception {
		ExpedienteCredito expedienteCreditoAut = null;
		List<AutorizaCredito> listaAutorizaCredito = new ArrayList<AutorizaCredito>();
		List<AutorizaVerificacion> listaAutorizaVerificacion = new ArrayList<AutorizaVerificacion>();
//		EstadoCredito estadoCredito = null;
		Integer nrolista = null;
		Integer intNroPerfiles = null;
		Integer intNroUsuarios = null;
		boolean blnValidarUsuario = false;
		boolean blnValidarPerfil = false;
//		boolean blnContinuarOperacion = false;
		// String strTipoValidacion = "";
		Integer intTipoValidacion = new Integer(0); // o-usuario 1-perfil
		boolean blnNoExiste = true;
		boolean blnSeRegistaronTodos = false;
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
//		List<AutorizaCreditoComp> autorizaCreditoCompRecuperados = null;
		libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
		
		Boolean exito = Boolean.FALSE;
		String mensaje = "";

		try {
			refrescarEncargadosAutorizar();
			if (isValidoAutorizacion(beanAutorizaCredito) == false) {
				strTxtMsgValidacion = strTxtMsgValidacion
						+ "No se puede continuar con el proceso de grabación. ";
				return;
			}

			nrolista = listaAutorizacionConfigurada.size(); // numero de autorizaciones configuradas
			log.info("Tamaño de la lista: "+nrolista);
			intNroUsuarios = listaAutorizacionConfigurada.get(0).getListaUsuario().size(); // numero de usuarios q deben autorizar
			intNroPerfiles = listaAutorizacionConfigurada.get(0).getListaPerfil().size(); // numero de perfiless que deben  autorizar
	
			if (intNroUsuarios.intValue() > 0) {
				blnValidarUsuario = true;
				// intTipoValidacion = 0 ;
			}
			if (intNroPerfiles.intValue() > 0) {
				blnValidarPerfil = true;
				intTipoValidacion = new Integer(1);
			}

			beanAutorizaCredito.setIntPersEmpresaAutoriza(usuario.getEmpresa().getIntIdEmpresa());
			beanAutorizaCredito.setIntPersUsuarioAutoriza(usuario.getIntPersPersonaPk());
			beanAutorizaCredito.setIntIdPerfilAutoriza(usuario.getPerfil().getId().getIntIdPerfil());
			beanAutorizaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			beanAutorizaVerificacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

			if (archivoInfoCorp != null) {
				beanAutorizaVerificacion.setIntItemArchivoInfo(archivoInfoCorp.getId().getIntItemArchivo());
				beanAutorizaVerificacion.setIntItemHistoricoInfo(archivoInfoCorp.getId().getIntItemHistorico());
				beanAutorizaVerificacion.setIntParaTipoArchivoInfoCod(archivoInfoCorp.getId().getIntParaTipoCod());

			}
			if (archivoReniec != null) {
				beanAutorizaVerificacion.setIntItemArchivoRen(archivoReniec.getId().getIntItemArchivo());
				beanAutorizaVerificacion.setIntItemHistoricoRen(archivoReniec.getId().getIntItemHistorico());
				beanAutorizaVerificacion.setIntParaTipoArchivoRenCod(archivoReniec.getId().getIntParaTipoCod());
			}

			expedienteCreditoAut = expedienteCreditoCompSelected.getExpedienteCredito();

			listaAutorizaCredito.add(beanAutorizaCredito);
			listaAutorizaVerificacion.add(beanAutorizaVerificacion);
			expedienteCreditoAut.setListaAutorizaCredito(listaAutorizaCredito);
			expedienteCreditoAut.setListaAutorizaVerificacion(listaAutorizaVerificacion);

			if (beanAutorizaCredito.getIntParaEstadoAutorizar().intValue() == Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO) {

				//Se modifica ya que expedienteCreditoCompSelected tiene el objeto EstadoCredito en nulo...
//				if (expedienteCreditoCompSelected.getEstadoCredito().getIntParaEstadoCreditoCod().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD) {
				if (expedienteCreditoCompSelected.getExpedienteCredito().getIntEstadoCreditoUltimo().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD) {
					// Validamos Usuario

					if (blnValidarUsuario) {
						if (blnNoExiste) {
							blnSeRegistaronTodos =  faltaSoloUno(intTipoValidacion,listaAutorizacionConfigurada);
							if (blnSeRegistaronTodos) {
								ExpedienteCredito expedienteCReditoActualizado = new ExpedienteCredito();
								expedienteCReditoActualizado= recuperarNuevoExpedienteRefinanciadoRecalculado(event);
								
								//CGD-13.01.2014
								mensaje = solicitudPrestamoFacade.generarProcesosAutorizacionRefinanciamiento(expedienteCReditoActualizado, expedienteCreditoAut, usuario, beanSocioComp);
								//mensaje = solicitudPrestamoFacade.generarProcesosAutorizacionRefinanciamiento(expedienteCReditoActualizado, expedienteCreditoAut, usuario);
									if(mensaje.equals("")){
										exito = Boolean.TRUE;
									}
							}
							/*solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCreditoAut);
							blnSeRegistaronTodos = faltaSoloUno(intTipoValidacion, listaAutorizacionConfigurada);
							//seRegistraronTodosLosUsuarios(intTipoValidacion,listaAutorizacionConfigurada);

							//if(blnSeRegistaronTodos){
								ExpedienteCredito expedienteCReditoActualizado = new ExpedienteCredito();
								expedienteCReditoActualizado= recuperarNuevoExpedienteRefinanciadoRecalculado(event);
								
								if(expedienteCReditoActualizado != null ){
									System.out.println("getBdMontoInteresAtrasado ---> " + expedienteCReditoActualizado.getBdMontoInteresAtrasado());
									for (CronogramaCredito crono : expedienteCReditoActualizado.getListaCronogramaCredito()) {
										System.out.println("NRO - "+crono.getIntNroCuota());
										System.out.println(" MONTO CAP - "+crono.getBdMontoCapital());
										System.out.println(" MONTO CONCEPTO - "+crono.getBdMontoConcepto());
										System.out.println(" FECHA VENC "+ Constante.sdf.format(crono.getTsFechaVencimiento()) );
										System.out.println("*********************************************************************");
									}
									
								}*/
						} else {
							strTxtMsgPerfil = strTxtMsgUsuario+ "Ya existe una Autorización registrada con el usuario: "+ usuario.getStrUsuario()+ ". No se puede continuar con el grabado.";
						}

					}
					// Validamos Perfil
					else if (blnValidarPerfil) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							//solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
							blnSeRegistaronTodos =  faltaSoloUno(intTipoValidacion,listaAutorizacionConfigurada);
							if (blnSeRegistaronTodos) {
								ExpedienteCredito expedienteCReditoActualizado = new ExpedienteCredito();
								expedienteCReditoActualizado= recuperarNuevoExpedienteRefinanciadoRecalculado(event);
								mensaje = solicitudPrestamoFacade.generarProcesosAutorizacionRefinanciamiento(expedienteCReditoActualizado, expedienteCreditoAut, usuario, beanSocioComp);
									if(mensaje.equals("")){
										exito = Boolean.TRUE;
									}
							}
							
						} else {
							strTxtMsgPerfil = strTxtMsgPerfil+ "Ya existe una Autorización con el Perfil "
									+ usuario.getPerfil().getStrDescripcion()
									+ " id: "
									+ usuario.getPerfil().getId()
											.getIntIdPerfil()
									+ ". No se puede continuar con el grabado.";
						}
					}

				} else {
					strTxtMsgError = "La Solicitud solo puede Autorizarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
					// cancelarGrabarAutorizacionPrestamo(event);
					return;
				}

				// Validamos la Operacion - OBSERVAR
				// ==================================================================================================================================
			} else if (beanAutorizaCredito.getIntParaEstadoAutorizar()
					.intValue() == Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_OBSERVAR_PRESTAMO) {
				
				//Se modifica ya que expedienteCreditoCompSelected tiene el objeto EstadoCredito en nulo...
//				if (expedienteCreditoCompSelected.getEstadoCredito()
//						.getIntParaEstadoCreditoCod().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD) {
				if (expedienteCreditoCompSelected.getExpedienteCredito().getIntEstadoCreditoUltimo().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD) {
					// Validamos Usuario
					if (blnValidarUsuario) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCreditoAut);
							solicitudPrestamoFacade.eliminarVerificaAutorizacionAdjuntosPorObservacion(expedienteCreditoAut, 
									// intTipoCredito.compareTo(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)
																									Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO,
																									//Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO , 
																									Constante.SUBTIPO_OPERACION_REFINANCIAMIENTO);

							
							cambioEstadoCredito(expedienteCreditoAut,Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO);

						} else {
							strTxtMsgUsuario = strTxtMsgUsuario + "Ya existe una Autorización para el Usuario logueado. ";
							//+ usuario.getPerfil().getStrDescripcion() + " ("+ usuario.getPerfil().getId().getIntIdPerfil()+ "). ";
						}

					}
					// Validamos Perfil
					else if (blnValidarPerfil) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCreditoAut);
							solicitudPrestamoFacade.eliminarVerificaAutorizacionAdjuntosPorObservacion(expedienteCreditoAut, 
									// intTipoCredito.compareTo(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)
																									Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO,
																									//Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO , 
																									Constante.SUBTIPO_OPERACION_REFINANCIAMIENTO);

							
							cambioEstadoCredito(expedienteCreditoAut,Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO);

						} else {
							strTxtMsgPerfil = strTxtMsgPerfil + "Ya existe una Autorización con el Perfil "
							+ usuario.getPerfil().getStrDescripcion() + " ("+ usuario.getPerfil().getId().getIntIdPerfil()+ "). ";
						}

					}

				} else {
					strTxtMsgError = "La Solicitud solo puede Observarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
					return;
				}

				// Validamos la Operacion - RECHAZAR
				// ==================================================================================================================================
			} else if (beanAutorizaCredito.getIntParaEstadoAutorizar()
					.intValue() == Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_PRESTAMO) {
				//Se modifica ya que expedienteCreditoCompSelected tiene el objeto EstadoCredito en nulo...
//				if (expedienteCreditoCompSelected.getEstadoCredito().getIntParaEstadoCreditoCod().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD) {
				if (expedienteCreditoCompSelected.getExpedienteCredito().getIntEstadoCreditoUltimo().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD) {
					// Validamos Usuario
					if (blnValidarUsuario) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCreditoAut);
							cambioEstadoCredito(expedienteCreditoAut,Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO);

						} else {
							strTxtMsgPerfil = strTxtMsgPerfil
									+ "Ya existe una Autorización registarda con el usuario: "
									+ usuario.getStrUsuario()
									+ ". No se puede continuar con el grabado.";
						}

					}
					// Validamos Perfil
					else if (blnValidarPerfil) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCreditoAut);
							cambioEstadoCredito(expedienteCreditoAut,Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO);

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
					strTxtMsgError = "La Solicitud solo puede Aprobarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
				}

			}

			limpiarFormAutorizacionRefinan();
			cancelarGrabarAutorizacionRefinan(event);
			//listarAutorizacionRefinanciamiento(event);

		}catch (Exception e){
			mensaje = "Ocurrio un error durante el proceso de Autorización de Refinanciamiento.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}

	/**
	 * 
	 * @param exito
	 * @param mensaje
	 */
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
	
	
	/**
	 * Valida que si con el registro de la presente autorizacion se inica los registros de liquidacion y cambio de estado.
	 * @param intTipoValidacion
	 * @param listaAutorizacionConfigurada
	 * @return
	 */
	private boolean faltaSoloUno(Integer intTipoValidacion, List<ConfServSolicitud> listaAutorizacionConfigurada) {
//		Integer intNroUsuariosConf = null;
//		Integer intNroPerfilesConf = null;
		BigDecimal bdNroUsuariosConf = null;
		BigDecimal bdNroPerfilesConf = null;
		
		boolean blnEsElUltimo = false;
		Integer intRecuperados = 0;
	
		if (listaAutorizacionConfigurada != null) {
	
			bdNroUsuariosConf = BigDecimal.ZERO;
			bdNroPerfilesConf = BigDecimal.ZERO;
			
			for (ConfServSolicitud confServSolicitud : listaAutorizacionConfigurada) {
				if(confServSolicitud.getListaPerfil() != null && !confServSolicitud.getListaPerfil().isEmpty()){
					bdNroPerfilesConf = bdNroPerfilesConf.add(new BigDecimal(confServSolicitud.getListaPerfil().size()));	
				}
				
				if(confServSolicitud.getListaUsuario() != null && !confServSolicitud.getListaUsuario().isEmpty()){
					bdNroUsuariosConf = bdNroUsuariosConf.add(new BigDecimal(confServSolicitud.getListaUsuario().size()));	
				}	
			}
			
			//intNroUsuariosConf = listaAutorizacionConfigurada.get(0).getListaUsuario().size(); // numero de usuarios q deben autorizar
			//intNroPerfilesConf = listaAutorizacionConfigurada.get(0).getListaPerfil().size(); // numero de perfiless que deben autorizar
	
			refrescarEncargadosAutorizar();
			intRecuperados = listaAutorizaCreditoComp.size();
	
			if (intTipoValidacion.compareTo(1)==0) { // perfil
				if (bdNroPerfilesConf.compareTo(new BigDecimal(intRecuperados)) == 1) {
					blnEsElUltimo = true;
				}
	
			} else if (intTipoValidacion.compareTo(0)== 0) { // usuario
				if (bdNroUsuariosConf.compareTo(new BigDecimal(intRecuperados)) == 1) {
					blnEsElUltimo = true;
				}
			}
	
		}
		return blnEsElUltimo;
	}
	
	/**
	 * Modificacion: 24.02.2014 Se comenta el metodo dado que no es invocado por ningun otro.
	 * @param intTipoValidacion
	 * @param listaAutorizacionConfigurada
	 * @return
	 */
//	private boolean seRegistraronTodosLosUsuariosXXX(Integer intTipoValidacion,
//		List<ConfServSolicitud> listaAutorizacionConfigurada) {
//		Integer intNroUsuariosConf = null;
//		Integer intNroPerfilesConf = null;
//		boolean blnTodos = false;
//		Integer intRecuperados = 0;
//
//		if (listaAutorizacionConfigurada != null) {
//			intNroUsuariosConf = listaAutorizacionConfigurada.get(0).getListaUsuario().size(); // numero de usuarios q deben
//												// autorizar
//			intNroPerfilesConf = listaAutorizacionConfigurada.get(0).getListaPerfil().size(); // numero de perfiless que deben
//												// autorizar
//
//			refrescarEncargadosAutorizar();
//			intRecuperados = listaAutorizaCreditoComp.size();
//
//			if (intTipoValidacion.intValue() == 1) { // perfil
//				if (intNroPerfilesConf.intValue() == (intRecuperados)) {
//					blnTodos = true;
//				}
//
//			} else if (intTipoValidacion.intValue() == 0) { // usuario
//				if (intNroUsuariosConf.intValue() == (intRecuperados)) {
//					blnTodos = true;
//				}
//			}
//
//		}
//
//		return blnTodos;
//	}
//	
	/** Inicia el proceso
	 * Modificación: 24.02.2014 se comenta procedimiento dado que ningun metodo lo invoca
	 * @use1:generarSolicitudCtaCteRefinan(beanSocioComp, usuario, strPeriodo, requisitoCred, expedienteMov);
	 * @use2:generarExpedienteMovimiento();
	 * @use3: cambioEstadoExpedientePorRefinanciar();
	 * @param expedienteCredito
	 * @param operacion
	 * @param event
	 */
//		private LibroDiario inicioProcesoGeneracionLibroDiario(ExpedienteCredito expedienteCreditoNuevo, Integer operacion, ActionEvent event) {
//			RequisitoCredito requisitoCred = null; 
//			String strPeriodo = null;
//			Integer intAnno = null;
//			Integer intMes = null;
//			String strMes = null;
//			ExpedienteId expId = null;
//			Expediente expedienteMovAnterior = null;
//			Integer intExpDet = null;
//			Calendar miCal = Calendar.getInstance();
////			int dia = miCal.get(Calendar.DATE);
//			int mes = miCal.get(Calendar.MONTH);
//			int anno = miCal.get(Calendar.YEAR);
//			LibroDiario libroDiario = null;
//	
//			try {
//				intAnno = anno;
//				intMes = mes;
//				if(intMes.compareTo(new Integer(10))<0){
//					strMes = "0"+intMes;
//				}else{
//					strMes = ""+intMes;
//				}
//				
//				strPeriodo = ""+intAnno+strMes;
//				recuperarDatosSocio(event);
//				intExpDet = expedienteCreditoNuevo.getId().getIntItemDetExpediente();
//				intExpDet--;
//				
//				expId = new ExpedienteId();
//				expId.setIntCuentaPk(expedienteCreditoNuevo.getId().getIntCuentaPk());
//				expId.setIntItemExpediente(expedienteCreditoNuevo.getId().getIntItemExpediente());
//				expId.setIntItemExpedienteDetalle(intExpDet);
//				expId.setIntPersEmpresaPk(expedienteCreditoNuevo.getId().getIntPersEmpresaPk());
//				
//				expedienteMovAnterior = conceptofacade.getExpedientePorPK(expId);
//				requisitoCred = solicitudPrestamoFacade.recuperarRequisitoRefinanciamiento(expedienteCreditoNuevo);
//				if(expedienteMovAnterior != null && requisitoCred != null){
//
//					//SocioComp socioComp, String strPeriodo, RequisitoCredito requisitoCred, Usuario usuario, 
//					//Expediente expedienteMovAnterior, ExpedienteCredito expedienteCreditoNuevo
//					libroDiario = cuentaCteFacade.generarProcesosDeRefinanciamiento(beanSocioComp, strPeriodo, requisitoCred, usuario, 
//							expedienteMovAnterior, expedienteCreditoNuevo, expedienteCreditoCompSelected);
//					
//					if(libroDiario!= null){
//						cambioEstadoCreditoAut(expedienteCreditoNuevo, libroDiario);
//					}
//					/*libroDiario = cuentaCteFacade.generarProcesosDeRefinanciamiento(expedienteCreditoNuevo, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO, 
//							Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO,  event,  
//							usuario, beanSocioComp,  expedienteCreditoCompSelected,strPeriodo,
//							requisitoCred, expedienteMovAnterior);*/
//					
//					/*libroDiario = solicitudPrestamoFacade.generarProcesoAutorizacionRefinanciamientoTotal(expedienteCreditoNuevo, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO, 
//																							Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO,  event,  
//																							usuario, beanSocioComp,  expedienteCreditoCompSelected,strPeriodo,
//																							requisitoCred, expedienteMovAnterior);*/
//				}else{
//					// No se puede completar el proceso de autorizacion
//				}
//				
//				
//			} catch (Exception e) {
//				System.out.println("Error en irGenerarLibro --> "+e);
//			}
//			return libroDiario;
//		}
		 	
	/**
	 * Recupera SocioComp en base a la solicitud selecionada
	 * @param event
	 */
	public void recuperarDatosSocio(ActionEvent event) {
		SocioComp socioComp = null;
		// CuentaConcepto cuentaConcepto = null
//		CapacidadCreditoComp capacidadCreditoComp = null;
//		Integer intTipoDoc = null;
//		String strNumIdentidad = null;
		CuentaComp cuentaComp = new CuentaComp();
		Integer intIdPersona= null;
		Integer intCuenta= null;
		Integer intEmpresa= null;
		Persona persona = null;
		List<Documento> listaDocumento = null;
		List<CuentaIntegrante> listaCtaIntSocio = null;
		CuentaIntegrante ctaIntSocio = null;
		//bdAportes = BigDecimal.ZERO;
		String strDni = null;
		try {
			intCuenta = expedienteCreditoCompSelected.getExpedienteCredito().getId().getIntCuentaPk();
			intEmpresa = expedienteCreditoCompSelected.getExpedienteCredito().getId().getIntPersEmpresaPk();
			//List<CuentaIntegrante> getListaCuentaIntegrantePorCuenta(Integer intEmpresa,Integer intCuenta)
			listaCtaIntSocio = socioFacade.getListaCuentaIntegrantePorCuenta(intEmpresa, intCuenta);
			
			if(listaCtaIntSocio != null){
				for (CuentaIntegrante cuentaIntegrante : listaCtaIntSocio) {
					if(cuentaIntegrante.getIntParaTipoIntegranteCod().compareTo(new Integer(1))==0){
						ctaIntSocio = new CuentaIntegrante();
						ctaIntSocio = cuentaIntegrante;
						break;
					}
				}
			}
			
			if(ctaIntSocio.getIntPersonaUsuario() != null){
				intIdPersona = ctaIntSocio.getId().getIntPersonaIntegrante();
				persona = personaFacade.getPersonaPorPK(intIdPersona);
			}
			
			if(persona != null){
				listaDocumento = persona.getListaDocumento();
				for (Documento documento : listaDocumento) {
					if(documento.getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI))==0){
						strDni = documento.getStrNumeroIdentidad();
						break;	
					}
				}
				
				if(strDni != null){
					socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), strDni, Constante.PARAM_EMPRESASESION);
					if (socioComp != null) {
						if (socioComp.getCuenta() != null) {

							for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
								if(socioEstructura.getIntEstadoCod().intValue() == Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO.intValue()){
									if (socioEstructura.getIntTipoEstructura().intValue() == Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.intValue()) {
										socioComp.getSocio().setSocioEstructura(socioEstructura);
									}
								}	
							}
							socioComp.setCuentaComp(cuentaComp);
							beanSocioComp = socioComp;
						}
					}	
				}	
			}
		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();	
		}
	}
	
	public void grabarAutorizacionPrestamoold(ActionEvent event) {
		ExpedienteCredito expedienteCredito = null;
		List<AutorizaCredito> listaAutorizaCredito = new ArrayList<AutorizaCredito>();
		List<AutorizaVerificacion> listaAutorizaVerificacion = new ArrayList<AutorizaVerificacion>();
		Usuario usuario = (Usuario) getRequest().getSession().getAttribute(
				"usuario");

		try {
			beanAutorizaCredito.setIntPersEmpresaAutoriza(usuario.getEmpresa().getIntIdEmpresa());
			beanAutorizaCredito.setIntPersUsuarioAutoriza(usuario.getIntPersPersonaPk());
			beanAutorizaCredito.setIntIdPerfilAutoriza(usuario.getPerfil().getId().getIntIdPerfil());
			beanAutorizaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

			beanAutorizaVerificacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

			expedienteCredito = expedienteCreditoCompSelected.getExpedienteCredito();
			listaAutorizaCredito.add(beanAutorizaCredito);
			listaAutorizaVerificacion.add(beanAutorizaVerificacion);
			expedienteCredito.setListaAutorizaCredito(listaAutorizaCredito);
			expedienteCredito.setListaAutorizaVerificacion(listaAutorizaVerificacion);

			solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
			limpiarFormAutorizacionRefinan();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void adjuntarDocumento(ActionEvent event) {
		log.info("-------------------------------------Debugging BeneficiarioController.adjuntarFirma-------------------------------------");
		String strParaTipoDescripcion = getRequestParameter("intParaTipoDescripcion");
		String strParaTipoOperacionPersona = getRequestParameter("intParaTipoOperacionPersona");
		log.info("strParaTipoDescripcion: " + strParaTipoDescripcion);
		log.info("strParaTipoOperacionPersona: " + strParaTipoOperacionPersona);
		Integer intParaTipoDescripcion = new Integer(strParaTipoDescripcion);
		Integer intParaTipoOperacionPersona = new Integer(strParaTipoOperacionPersona);

		this.intParaTipoDescripcion = intParaTipoDescripcion;
		this.intParaTipoOperacionPersona = intParaTipoOperacionPersona;

		FileUploadController fileupload = (FileUploadController) getSessionBean("fileUploadController");
		// FileUploadController fileupload = new FileUploadController();
		fileupload.setStrDescripcion("Seleccione el archivo que desea adjuntar.");
		fileupload.setFileType(FileUtil.imageTypes);
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;

		if (listaRequisitoCreditoComp != null) {
			for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {

				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_INFOCORP)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
				// &&
				// requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				) {

					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_INFOCORP);
				}

				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_RENIEC)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
				// &&
				// requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
				) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_RENIEC);

				}
			}
		}
		// fileupload.setStrJsFunction("putFile");
		fileupload.setStrJsFunction("putFileDocAdjunto()");
	}
	
	
	
	public void putFile(ActionEvent event) throws BusinessException, EJBFactoryException, IOException {
	log.info("-------------------------------------Debugging autorizacionPrestamoController.putFile-------------------------------------");
	
	FileUploadController fileupload = (FileUploadController) getSessionBean("fileUploadController");
	if (listaRequisitoCreditoComp != null) {
		for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {
	

			if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_INFOCORP)
				// && requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
	
					requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
					log.info("byteImg.length: "+ fileupload.getDataImage().length);
					byte[] byteImg = fileupload.getDataImage();
					MyFile file = new MyFile();
					file.setLength(byteImg.length);
					file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
					file.setData(byteImg);
					requisitoCreditoComp.setFileDocAdjunto(file);
					break;
			}

			
			if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_RENIEC)
				// && requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
	
					requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
					log.info("byteImg.length: "+ fileupload.getDataImage().length);
					byte[] byteImg = fileupload.getDataImage();
					MyFile file = new MyFile();
					file.setLength(byteImg.length);
					file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
					file.setData(byteImg);
					requisitoCreditoComp.setFileDocAdjunto(file);
					break;
				}
				// }
			}
		}
	}
	
	
	/**
	 * Valida las configuraciones de solicitud. Valida: Vigencia, tipo/subtipo,
	 * Monto Desde/Hasta, Cuota Desde/Hasta. Devuelve las que pasan estas
	 * validaciones.
	 * 
	 * @param listaSolicitudAutorizada
	 * @return
	 */

	public ConfServSolicitud validarConfiguracion(
		ConfServSolicitud confServSolicitud) {
		Boolean hasMontDesde = false;
		Boolean hasMontHasta = false;
//		Boolean hasCuotaDesde = false;
//		Boolean hasCuotaHasta = false;
		Integer nroValidaciones = null;
		Integer contAprob = new Integer(0);
		Boolean boAprueba = new Boolean(false);
		ConfServSolicitud solicitud = null;
		Calendar clToday = Calendar.getInstance();

		String strResumen = "";

		solicitud = confServSolicitud;
		strResumen = solicitud.getId().getIntItemSolicitud()+"- No pasa las validaciones de: ";

		nroValidaciones = new Integer(3); // Se inicializa en 3 xq se toma en cuenta (1)Vigencia, (2) Estado, (3)tipo, TipoSubtipo.
		
		if (solicitud.getBdMontoDesde() != null) {
			hasMontDesde = true;
			nroValidaciones++;
		}
		if (solicitud.getBdMontoHasta() != null) {
			hasMontHasta = true;
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
		
		
		Integer intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO;
		// 3. Validando el tipo y subtipo
		if (expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod().compareTo(solicitud.getIntParaSubtipoOperacionCod())==0
			&& (solicitud.getIntParaTipoOperacionCod().compareTo(intTipoOperacion))==0) {
			contAprob++;
		}else{
			strResumen = strResumen + " 3. Validando el tipo y subtipo. ";
		}
		
		// 4. Validando Monto Desde
		if (hasMontDesde){
				if (expedienteCreditoCompSelected.getExpedienteCredito().getBdMontoTotal().compareTo(solicitud.getBdMontoDesde()) >= 0){
					contAprob++;
				}else{
					strResumen = strResumen + " 4. Validando Monto Desde. ";
				}
		}
		// 5. Validando Monto Hasta
		if (hasMontHasta){
			if (expedienteCreditoCompSelected.getExpedienteCredito().getBdMontoTotal().compareTo(solicitud.getBdMontoHasta()) <= 0){
				contAprob++;
			}else{
				strResumen = strResumen + " 5. Validando Monto Hasta. ";
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
		
		//9. se valida que sea del tipo (2)autorizacion y no (1) requisito
		/*if (solicitud.getIntParaTipoRequertoAutorizaCod().compareTo(new Integer(2))==0)
			contAprob++;*/
		
		System.out.println("================================================================");
		System.out.println(" SOLICITUD " + solicitud.getId().getIntItemSolicitud());
		System.out.println(" NRO DE VALIDACIONES EXISTENTES " + nroValidaciones);
		System.out.println(" NRO DE VALIDACIONES APROBADAS " + contAprob);
		System.out.println(" OBSERVACION " + strResumen);
		System.out.println("===========================================================");


		if (nroValidaciones.compareTo(contAprob)==0)
			boAprueba = true;
		if (boAprueba) {
			return solicitud;
		} else {
			return null;
		}
	}
	
	private Boolean isValidoAutorizacion(AutorizaCredito autorizaCredito) {
		Boolean validAutorizaPrestamo = true;
		strTxtMsgValidacion = "";
		if (autorizaCredito.getIntParaEstadoAutorizar() == 0) {
			strTxtMsgValidacion = strTxtMsgValidacion+ "* Seleccionar Operación. ";
			validAutorizaPrestamo = false;
		}
		if (autorizaCredito.getIntParaTipoAureobCod() == 0) {
			strTxtMsgValidacion = strTxtMsgValidacion+ "* Seleccionar Tipo de Operación. ";
			validAutorizaPrestamo = false;
		}

		// -------< validacion agregada 08112012 ----->
		/*
		 * if (listaGarantiaCreditoComp == null) {
		 * setMsgTxtListaCapacidadPago(msgTxtListaCapacidadPago +
		 * "* Deben Garantes Solidarios."); validExpedienteCredito = false; }
		 * else { setMsgTxtListaCapacidadPago(msgTxtListaCapacidadPago); }
		 */

		return validAutorizaPrestamo;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<AutorizaCreditoComp> recuperarEncargadosAutorizar(
		ExpedienteCreditoComp expedienteCreditoComp) {
		List<AutorizaCredito> listaAutorizaCredito = new ArrayList<AutorizaCredito>();
		List<AutorizaCreditoComp> listaAutorizaCreditoComp = null;
		AutorizaCreditoComp autorizaCreditoComp = null;
		Persona persona = null;
		try {
			listaAutorizaCredito = solicitudPrestamoFacade.getListaAutorizaCreditoPorPkExpediente(expedienteCreditoComp.getExpedienteCredito().getId());
			if (listaAutorizaCredito != null && listaAutorizaCredito.size() > 0) {
				listaAutorizaCreditoComp = new ArrayList<AutorizaCreditoComp>();
				for (AutorizaCredito autorizaCredito : listaAutorizaCredito) {
					autorizaCreditoComp = new AutorizaCreditoComp();
					autorizaCreditoComp.setAutorizaCredito(autorizaCredito);
					persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaCredito.getIntPersUsuarioAutoriza());
					for (int k = 0; k < persona.getListaDocumento().size(); k++) {
						if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
							persona.setDocumento(persona.getListaDocumento().get(k));
							break;
						}
					}
					autorizaCreditoComp.setPersona(persona);
					listaAutorizaCreditoComp.add(autorizaCreditoComp);
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listaAutorizaCreditoComp;
	}
	
	
	public void refrescarEncargadosAutorizar() {
		List<AutorizaCredito> listaAutorizaCredito = new ArrayList<AutorizaCredito>();
		listaAutorizaCreditoComp = new ArrayList<AutorizaCreditoComp>();
		AutorizaCreditoComp autorizaCreditoComp = null;
		Persona persona = null;
		try {
			listaAutorizaCredito = solicitudPrestamoFacade.getListaAutorizaCreditoPorPkExpediente(expedienteCreditoCompSelected.getExpedienteCredito().getId());
			if (listaAutorizaCredito != null && listaAutorizaCredito.size() > 0) {
				for (AutorizaCredito autorizaCredito : listaAutorizaCredito) {
					if(autorizaCredito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						autorizaCreditoComp = new AutorizaCreditoComp();
						autorizaCreditoComp.setAutorizaCredito(autorizaCredito);
						persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaCredito.getIntPersUsuarioAutoriza());
						for (int k = 0; k < persona.getListaDocumento().size(); k++) {
							if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
								persona.setDocumento(persona.getListaDocumento().get(k));
								break;
							}
						}
						autorizaCreditoComp.setPersona(persona);
						listaAutorizaCreditoComp.add(autorizaCreditoComp);
					}
					
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}
	
	
	public void mostrarArchivosAdjuntosNoUsado() {
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
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		RequisitoCreditoComp requisitoCreditoComp;
		try {
			dtToday = Constante.sdf.parse(strToday);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			SocioComp beanSocioComp = new SocioComp();
			beanSocioComp = expedienteCreditoCompSelected.getSocioComp();

			facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			confServSolicitud = new ConfServSolicitud();
			listaDocAdjuntos = facade.buscarConfSolicitudRequisito(confServSolicitud, null, dtToday, 1);
			
			if (listaDocAdjuntos != null && listaDocAdjuntos.size() > 0) {
				forSolicitud: for (ConfServSolicitud solicitud : listaDocAdjuntos) {
					if (solicitud.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_PRESTAMO)) {
						if (solicitud.getIntParaSubtipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO)) {
							if (solicitud.getListaEstructuraDetalle() != null) {
								for (ConfServEstructuraDetalle estructuraDetalle : solicitud.getListaEstructuraDetalle()) {
									estructuraDet = new EstructuraDetalle();
									estructuraDet.setId(new EstructuraDetalleId());
									// getSocioEstructuraDeOrigenPorPkSocio
									// **********************************************************************
									SocioEstructura socioEsctructura = new SocioEstructura();
									socioEsctructura = socioFacade.getSocioEstructuraDeOrigenPorPkSocio(beanSocioComp.getSocio().getId());

									estructuraDet.getId().setIntNivel(socioEsctructura.getIntNivel());
									estructuraDet.getId().setIntCodigo(socioEsctructura.getIntCodigo());
									listaEstructuraDet = estructuraFacade.getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(
													estructuraDet.getId(),socioEsctructura.getIntTipoSocio(),
													socioEsctructura.getIntModalidad());

									if (listaEstructuraDet != null && listaEstructuraDet.size() > 0) {
										for (EstructuraDetalle estructDetalle : listaEstructuraDet) {
											
											if (estructuraDetalle.getIntCodigoPk().equals(socioEsctructura.getIntCodigo())
													&& estructuraDetalle.getIntNivelPk().equals(socioEsctructura.getIntNivel())
													&& estructuraDetalle.getIntCaso().equals(estructDetalle.getId().getIntCaso())
													&& estructuraDetalle.getIntItemCaso().equals(estructDetalle.getId().getIntItemCaso())) {
												
												if (solicitud.getListaDetalle() != null && solicitud.getListaDetalle().size() > 0) {

													List<RequisitoCreditoComp> listaRequisitoCreditoCompTemp = new ArrayList<RequisitoCreditoComp>();
													for (ConfServDetalle detalle : solicitud.getListaDetalle()) {
														System.out.println("detalle.getId().getIntItemDetalle()"+ detalle.getId().getIntItemDetalle());
														System.out.println("detalle.getId().getIntItemSolicitud()"+ detalle.getId().getIntItemSolicitud());
														System.out.println("detalle.getId().getIntPersEmpresaPk()"+ detalle.getId().getIntPersEmpresaPk());

														if (detalle.getId().getIntPersEmpresaPk().equals(estructuraDetalle.getId().getIntPersEmpresaPk())
															&& detalle.getId().getIntItemSolicitud().equals(estructuraDetalle.getId().getIntItemSolicitud())) {

															requisitoCreditoComp = new RequisitoCreditoComp();
															requisitoCreditoComp.setDetalle(detalle);
															listaRequisitoCreditoCompTemp.add(requisitoCreditoComp);
														}
													}

													List<Tabla> listaTablaRequisitos = new ArrayList<Tabla>();

													// validamos que solo se muestre las de agrupamioento A.
													listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO),"B");
													for (int i = 0; i < listaTablaRequisitos.size(); i++) {
														for (int j = 0; j < listaRequisitoCreditoCompTemp.size(); j++) {
															if ((listaRequisitoCreditoCompTemp.get(j).getDetalle().getIntParaTipoDescripcion().intValue()) 
																== (listaTablaRequisitos.get(i).getIntIdDetalle().intValue())) {
																	listaRequisitoCreditoComp.add(listaRequisitoCreditoCompTemp.get(j));
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
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	
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
	
	public Timestamp obtenerFechaActual() {
		return new Timestamp(new Date().getTime());
	}

	public String getTipoValidacion() {
		Integer nrolista = null;
		Integer intNroPerfiles = null;
		Integer intNroUsuarios = null;
		String strTipoValidacion = null;

		nrolista = listaAutorizacionConfigurada.size();
		log.info("Tamaño de la lista: "+nrolista);
		intNroUsuarios = listaAutorizacionConfigurada.get(0).getListaUsuario().size();
		intNroPerfiles = listaAutorizacionConfigurada.get(0).getListaPerfil().size();

		if (intNroUsuarios.compareTo(0) > 0) {
			strTipoValidacion = "U";
		}
		if (intNroPerfiles.compareTo(0) > 0) {
			strTipoValidacion = "P";
		}

		return strTipoValidacion;
	}
	
	
	/**
	 * Indica si el usuario ya realizo una autorizacion.
	 * 
	 * @return boolean blnNoExiste
	 */
	public boolean existeRegistro(Integer intTipoValidacion) {
		boolean blnNoExiste = true;

		if (listaAutorizaCreditoComp.size() > 0 && listaAutorizaCreditoComp != null) {
			for (int k = 0; k < listaAutorizaCreditoComp.size(); k++) {
				AutorizaCredito autorizaCredito = new AutorizaCredito();
				autorizaCredito = listaAutorizaCreditoComp.get(k).getAutorizaCredito();

				if (intTipoValidacion.intValue() == 0) {// usuario
					if (autorizaCredito.getIntPersUsuarioAutoriza().intValue() == (usuario.getPerfil().getId().getIntIdPerfil().intValue())
						&& autorizaCredito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0) {
						blnNoExiste = false;
						break;
					}

				} else if (intTipoValidacion.intValue() == 1) { // perfil
					if (autorizaCredito.getIntIdPerfilAutoriza().intValue() == (usuario.getPerfil().getId().getIntIdPerfil().intValue())
						&& autorizaCredito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0) {
						blnNoExiste = false;
						break;
					}
				}
			}
		}

		return blnNoExiste;
	}
	
	
	public void aceptarAdjuntarInfoCorp() {
		System.out.println("GDF??? WTF???--------->");
		FileUploadControllerServicio fileUploadController = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");

		try {
			archivoInfoCorp = fileUploadController.getArchivoInfoCorp();
			fileUploadController = new FileUploadControllerServicio();
			// recuperarAdjuntosAutorizacion(expedienteCreditoCompSelected);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	public void aceptarAdjuntarReniec() {
		FileUploadControllerServicio fileUploadController = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		try {
			archivoReniec = fileUploadController.getArchivoReniec();
			fileUploadController = new FileUploadControllerServicio();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void quitarInfoCorp() {
		try {
			archivoInfoCorp = null;
			((FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio"))
					.setArchivoInfoCorp(null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void quitarReniec() {
		try {
			archivoReniec = null;
			((FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio"))
					.setArchivoReniec(null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	public void irModificarSolicitudRefinanAutoriza(ActionEvent event){
		System.out.println("eventeventevent --> "+event);
		System.out.println("registroSeleccionadoregistroSeleccionadoregistroSeleccionado --> "+expedienteCreditoCompSelected);
		System.out.println();
		/*SolicitudRefinanController*/ solicitudRefinanController = (SolicitudRefinanController) getSessionBean("solicitudRefinanController");
		try {
			solicitudRefinanController.irModificarSolicitudRefinanAutoriza2(event, expedienteCreditoCompSelected);
		} catch (ParseException e) {
			log.error("Error en AutorizaRefinanController.irModificarSolicitudRefinanAutoriza ---> "+e);
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * 
	 * @return
	 */
	public ExpedienteCredito recuperarNuevoExpedienteRefinanciadoRecalculado(ActionEvent event){
		ExpedienteCredito expedienteRecalculado = null;
		try {
			
			SolicitudRefinanController solicitudRefinanController = (SolicitudRefinanController) getSessionBean("solicitudRefinanController");
			//Artificio para que regenere solicitud en base a la fecha de la autorizacion
			expedienteRecalculado = solicitudRefinanController.irModificarSolicitudPrestamoRecalculado(expedienteCreditoCompSelected, event);
			
			
		} catch (Exception e) {
		
		log.error("Error en recuperarNuevoExpedienteRefinanciadoRecalculado --->  "+e);
		
		}
		return expedienteRecalculado;
		
		
	}
	
	/**
	 * Cambia estado al expediente de refinanciamiento - aprobado + girado
	 * Modificación: 24.02.2014 se comenta procedimiento dado que el metodo que lo invoca
	 * "inicioProcesoGeneracionLibroDiario", no es invocado por nadie.
	 * @param expedienteCredito
	 * @param diario
	 * @throws Exception
	 */
//	private void cambioEstadoCreditoAut(ExpedienteCredito expedienteCredito, LibroDiario diario) throws Exception {
//			
//		EstadoCredito estadoCreditoApro = new EstadoCredito();
//		EstadoCredito estadoCreditoGiro= new EstadoCredito();
//			EstadoCreditoId estadoCreditoId = null;
//			
//
//			estadoCreditoId = new EstadoCreditoId();
//			estadoCreditoId.setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
//			estadoCreditoId.setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
//			estadoCreditoId.setIntItemEstado(null);
//			estadoCreditoId.setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
//			estadoCreditoId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
//			try {
//				estadoCreditoApro.setId(estadoCreditoId);
//				estadoCreditoApro.setTsFechaEstado(new Timestamp(new Date().getTime()));
//				estadoCreditoApro.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
//				estadoCreditoApro.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
//				estadoCreditoApro.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
//				estadoCreditoApro.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
//				estadoCreditoApro.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
//				//Integer intCodigoLibroDiario = null;
//				//intCodigoLibroDiario = diario.getId().getIntContCodigoLibro().intValue();
//				estadoCreditoApro.setIntCodigoLibro(null);
//				estadoCreditoApro.setIntPeriodoLibro(obtenerPeriodoActual());
//				estadoCreditoApro.setIntPersEmpresaLibro(Constante.PARAM_EMPRESASESION);
//				estadoCreditoApro = solicitudPrestamoFacade.grabarEstadoAutorizadoRefinan(estadoCreditoApro);
//				//------------------>
//				estadoCreditoGiro.setId(estadoCreditoId);
//				estadoCreditoGiro.setTsFechaEstado(new Timestamp(new Date().getTime()));
//				estadoCreditoGiro.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
//				estadoCreditoGiro.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
//				estadoCreditoGiro.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
//				estadoCreditoGiro.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
//				estadoCreditoGiro.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO);
//				Integer intCodigoLibroDiario = null;
//				intCodigoLibroDiario = diario.getId().getIntContCodigoLibro().intValue();
//				estadoCreditoGiro.setIntCodigoLibro(intCodigoLibroDiario);
//				estadoCreditoGiro.setIntPeriodoLibro(obtenerPeriodoActual());
//				estadoCreditoGiro.setIntPersEmpresaLibro(Constante.PARAM_EMPRESASESION);
//				estadoCreditoGiro = solicitudPrestamoFacade.grabarEstadoAutorizadoRefinan(estadoCreditoGiro);
//
//				
//			} catch (Exception e) {
//				log.error("Error en cambioEstadoCredito --> "+e);
//				e.printStackTrace();
//			}
//		}
	
	/**
	 * Cambia el estado de credito anterior a cancelado
	 * @param expedienteCredito
	 * @throws Exception
	 */
//	private void cambioEstadoCreditoAnterior(ExpedienteCredito expedienteCredito, LibroDiario diario) throws Exception {
//		
//		EstadoCredito estadoCredito = new EstadoCredito();
//			EstadoCreditoId estadoCreditoId = null;
//			
//
//			estadoCreditoId = new EstadoCreditoId();
//			estadoCreditoId.setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
//			estadoCreditoId.setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
//			estadoCreditoId.setIntItemEstado(null);
//			estadoCreditoId.setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
//			estadoCreditoId.setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
//			try {
//				estadoCredito.setId(estadoCreditoId);
//				estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
//				estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
//				estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
//				estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
//				estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
//				estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_CANCELADO);
//				Integer intCodigoLibroDiario = null;
//				intCodigoLibroDiario = diario.getId().getIntContCodigoLibro().intValue();
//				//}
//
//				// estadoCredito.setIntCodigoLibro(14);
//				estadoCredito.setIntCodigoLibro(intCodigoLibroDiario);
//				estadoCredito.setIntPeriodoLibro(obtenerPeriodoActual());
//				estadoCredito.setIntPersEmpresaLibro(Constante.PARAM_EMPRESASESION);
//				//expedienteCredito.getListaEstadoCredito().add(estadoCredito);
//				//solicitudPrestamoFacade.modificarExpedienteCredito(expedienteCredito);
//				estadoCredito = solicitudPrestamoFacade.grabarEstadoAutorizadoRefinan(estadoCredito);
//			} catch (Exception e) {
//				log.error("Error en cambioEstadoCredito --> "+e);
//				e.printStackTrace();
//			}
//		}
	/**
	 * Cambia de estado al expediente, agregando un registro de Estado.
	 * 
	 * @param expedienteCredito
	 * @throws Exception
	 */
	private void cambioEstadoCredito(ExpedienteCredito expedienteCredito,
		Integer intParaEstadoCreditoCod) throws Exception {
		EstadoCredito estadoCredito = new EstadoCredito();
		EstadoCreditoId estadoCreditoId = null;

		try {
			estadoCredito.setId(estadoCreditoId);
			estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
			estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
			estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
			estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
			estadoCredito.setIntParaEstadoCreditoCod(intParaEstadoCreditoCod);

			LibroDiario ultimoLibro = null;
			LibroDiarioId libroDiarioId = new LibroDiarioId();
			libroDiarioId.setIntContCodigoLibro(null);
			libroDiarioId.setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiarioId.setIntPersEmpresaLibro(Constante.PARAM_EMPRESASESION);

			// recuperamos el ultimo registro del libro diario en el presente
			// periodo. Por el codigo de libro.
			ultimoLibro = libroDiarioFacade.getLibroDiarioUltimoPorPk(libroDiarioId);
			


			int intCodigoLibroDiario = -1;
			// recuperar el ultimo libro
			if (ultimoLibro == null) {
				intCodigoLibroDiario = 1;
			} else {
				intCodigoLibroDiario = ultimoLibro.getId().getIntContCodigoLibro().intValue() + 1;
			}

			// estadoCredito.setIntCodigoLibro(14);
			estadoCredito.setIntCodigoLibro(intCodigoLibroDiario);
			estadoCredito.setIntPeriodoLibro(obtenerPeriodoActual());
			estadoCredito.setIntPersEmpresaLibro(Constante.PARAM_EMPRESASESION);
			expedienteCredito.getListaEstadoCredito().add(estadoCredito);
			solicitudPrestamoFacade.modificarExpedienteCredito(expedienteCredito);
		} catch (Exception e) {
			log.error("Error en cambioEstadoCredito --> "+e);
			e.printStackTrace();
		}
	}
	/*public void recuperarDatosCartera(ActionEvent event){
		Integer empresa = 2;
		try {
			//solicitudPrestamoFacade.getDatosDeCartera(empresa);
			solicitudPrestamoFacade.generarAsientoRefinanciamiento();
		} catch (BusinessException e) {
			log.error("BusinessException recuperarDatosCartera --> "+e);
			e.printStackTrace();
		}
		
	}*/
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	public List<Sucursal> getListSucursal() {
		try {
			if (listSucursal == null) {
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listSucursal = facade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listSucursal;
	}	
	public static Logger getLog() {
		return log;
	}
	public static void setLog(Logger log) {
		AutorizacionRefinanController.log = log;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public AutorizaCredito getBeanAutorizaCredito() {
		return beanAutorizaCredito;
	}
	public void setBeanAutorizaCredito(AutorizaCredito beanAutorizaCredito) {
		this.beanAutorizaCredito = beanAutorizaCredito;
	}
	public AutorizaVerificacion getBeanAutorizaVerificacion() {
		return beanAutorizaVerificacion;
	}
	public void setBeanAutorizaVerificacion(
			AutorizaVerificacion beanAutorizaVerificacion) {
		this.beanAutorizaVerificacion = beanAutorizaVerificacion;
	}
	public ExpedienteCreditoComp getExpedienteCreditoCompSelected() {
		return expedienteCreditoCompSelected;
	}
	public void setExpedienteCreditoCompSelected(
			ExpedienteCreditoComp expedienteCreditoCompSelected) {
		this.expedienteCreditoCompSelected = expedienteCreditoCompSelected;
	}
	public Integer getIntIdTipoPersona() {
		return intIdTipoPersona;
	}
	public void setIntIdTipoPersona(Integer intIdTipoPersona) {
		this.intIdTipoPersona = intIdTipoPersona;
	}
	public Integer getIntBusqSolicPtmo() {
		return intBusqSolicPtmo;
	}
	public void setIntBusqSolicPtmo(Integer intBusqSolicPtmo) {
		this.intBusqSolicPtmo = intBusqSolicPtmo;
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
	public Integer getIntTotalSucursales() {
		return intTotalSucursales;
	}
	public void setIntTotalSucursales(Integer intTotalSucursales) {
		this.intTotalSucursales = intTotalSucursales;
	}
	public Integer getIntIdDependencia() {
		return intIdDependencia;
	}
	public void setIntIdDependencia(Integer intIdDependencia) {
		this.intIdDependencia = intIdDependencia;
	}
	public Integer getIntIdTipoPrestamo() {
		return intIdTipoPrestamo;
	}
	public void setIntIdTipoPrestamo(Integer intIdTipoPrestamo) {
		this.intIdTipoPrestamo = intIdTipoPrestamo;
	}
	public Integer getIntIdEstadoPrestamo() {
		return intIdEstadoPrestamo;
	}
	public void setIntIdEstadoPrestamo(Integer intIdEstadoPrestamo) {
		this.intIdEstadoPrestamo = intIdEstadoPrestamo;
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
	public String getStrTxtMsgVigencia() {
		return strTxtMsgVigencia;
	}
	public void setStrTxtMsgVigencia(String strTxtMsgVigencia) {
		this.strTxtMsgVigencia = strTxtMsgVigencia;
	}
	public String getStrTxtMsgError() {
		return strTxtMsgError;
	}
	public void setStrTxtMsgError(String strTxtMsgError) {
		this.strTxtMsgError = strTxtMsgError;
	}
	public String getStrTxtMsgValidacion() {
		return strTxtMsgValidacion;
	}
	public void setStrTxtMsgValidacion(String strTxtMsgValidacion) {
		this.strTxtMsgValidacion = strTxtMsgValidacion;
	}
	public List<RequisitoCreditoComp> getListaRequisitoCreditoComp() {
		return listaRequisitoCreditoComp;
	}
	public void setListaRequisitoCreditoComp(
			List<RequisitoCreditoComp> listaRequisitoCreditoComp) {
		this.listaRequisitoCreditoComp = listaRequisitoCreditoComp;
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
	public Archivo getArchivoInfoCorp() {
		return archivoInfoCorp;
	}
	public void setArchivoInfoCorp(Archivo archivoInfoCorp) {
		this.archivoInfoCorp = archivoInfoCorp;
	}
	public Archivo getArchivoReniec() {
		return archivoReniec;
	}
	public void setArchivoReniec(Archivo archivoReniec) {
		this.archivoReniec = archivoReniec;
	}
	public List<AutorizaCreditoComp> getListaAutorizaCreditoComp() {
		return listaAutorizaCreditoComp;
	}
	public void setListaAutorizaCreditoComp(
			List<AutorizaCreditoComp> listaAutorizaCreditoComp) {
		this.listaAutorizaCreditoComp = listaAutorizaCreditoComp;
	}
	public List<ExpedienteCreditoComp> getListaAutorizacionCreditoComp() {
		return listaAutorizacionCreditoComp;
	}
	public void setListaAutorizacionCreditoComp(
			List<ExpedienteCreditoComp> listaAutorizacionCreditoComp) {
		this.listaAutorizacionCreditoComp = listaAutorizacionCreditoComp;
	}
	public List<AutorizaVerificacion> getListaAutorizaVerificacionAdjuntos() {
		return listaAutorizaVerificacionAdjuntos;
	}
	public void setListaAutorizaVerificacionAdjuntos(
			List<AutorizaVerificacion> listaAutorizaVerificacionAdjuntos) {
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
	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
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
	public SolicitudPrestamoFacadeLocal getSolicitudPrestamoFacade() {
		return solicitudPrestamoFacade;
	}
	public void setSolicitudPrestamoFacade(
			SolicitudPrestamoFacadeLocal solicitudPrestamoFacade) {
		this.solicitudPrestamoFacade = solicitudPrestamoFacade;
	}
	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}
	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}
	public LibroDiarioFacadeRemote getLibroDiarioFacade() {
		return libroDiarioFacade;
	}
	public void setLibroDiarioFacade(LibroDiarioFacadeRemote libroDiarioFacade) {
		this.libroDiarioFacade = libroDiarioFacade;
	}
	public PermisoFacadeRemote getPermisoFacade() {
		return permisoFacade;
	}
	public void setPermisoFacade(PermisoFacadeRemote permisoFacade) {
		this.permisoFacade = permisoFacade;
	}
	public Boolean getFormAutorizacionRefinanRendered() {
		return formAutorizacionRefinanRendered;
	}
	public void setFormAutorizacionRefinanRendered(
			Boolean formAutorizacionRefinanRendered) {
		this.formAutorizacionRefinanRendered = formAutorizacionRefinanRendered;
	}	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	public ConceptoFacadeRemote getConceptofacade() {
		return conceptofacade;
	}
	public void setConceptofacade(ConceptoFacadeRemote conceptofacade) {
		this.conceptofacade = conceptofacade;
	}
	public SocioComp getBeanSocioComp() {
		return beanSocioComp;
	}
	public void setBeanSocioComp(SocioComp beanSocioComp) {
		this.beanSocioComp = beanSocioComp;
	}
	public Date getDtHoy() {
		return dtHoy;
	}
	public void setDtHoy(Date dtHoy) {
		this.dtHoy = dtHoy;
	}
	public CuentacteFacadeRemote getCuentaCteFacade() {
		return cuentaCteFacade;
	}
	public void setCuentaCteFacade(CuentacteFacadeRemote cuentaCteFacade) {
		this.cuentaCteFacade = cuentaCteFacade;
	}
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
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
	public SolicitudRefinanController getSolicitudRefinanController() {
		return solicitudRefinanController;
	}
	public void setSolicitudRefinanController(
			SolicitudRefinanController solicitudRefinanController) {
		this.solicitudRefinanController = solicitudRefinanController;
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
	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}
	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
	}
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}
	public List<ExpedienteCreditoComp> getListaDetalleRefinanciamiento() {
		return listaDetalleRefinanciamiento;
	}
	public void setListaDetalleRefinanciamiento(
			List<ExpedienteCreditoComp> listaDetalleRefinanciamiento) {
		this.listaDetalleRefinanciamiento = listaDetalleRefinanciamiento;
	}
	public Integer getIntBusqTipoCreditoEmpresa() {
		return intBusqTipoCreditoEmpresa;
	}
	public void setIntBusqTipoCreditoEmpresa(Integer intBusqTipoCreditoEmpresa) {
		this.intBusqTipoCreditoEmpresa = intBusqTipoCreditoEmpresa;
	}
	public List<Tabla> getListaTablaCreditoEmpresa() {
		return listaTablaCreditoEmpresa;
	}
	public void setListaTablaCreditoEmpresa(List<Tabla> listaTablaCreditoEmpresa) {
		this.listaTablaCreditoEmpresa = listaTablaCreditoEmpresa;
	}
}
