package pe.com.tumi.tesoreria.cierre.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueo;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoBillete;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;


public class CierreDiarioController {

	protected static Logger log = Logger.getLogger(CierreDiarioController.class);
	
	private EmpresaFacadeRemote empresaFacade;
	private PersonaFacadeRemote personaFacade;
	private EgresoFacadeLocal 	egresoFacade;
	private TablaFacadeRemote 	tablaFacade;
	private CierreDiarioArqueoFacadeLocal 	cierreDiarioArqueoFacade;
	
	private List<CierreDiarioArqueo>	listaCierreDiarioArqueo;
	private List<Sucursal>				listaSucursal;
	private List<Subsucursal>			listaSubsucursal;
	private List<Tabla>					listaTablaDenominacion;
	private List<Persona>				listaPersonaFiltro;
	
	private CierreDiarioArqueo 			cierreDiarioArqueo;
	private CierreDiarioArqueo 			cierreDiarioArqueoFiltro;
	private CierreDiarioArqueo 			registroSeleccionado;
	private CierreDiarioArqueoDetalle	cierreDiarioArqueoDetalleIngresos;
	
	private String 		mensajeOperacion;
	private Integer		intTipoDenominacionAgregar;
	private Integer		intCantidadDenominacionAgregar;
	private Integer		intTipoFiltroPersona;
	private String		strTextoFiltroPersona;
	private Date		dtFechaFiltro;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean sucursalSesionEsSede;
	private boolean	poseePermiso;
	
	//Agregado 10.12.2013 JCHAVEZ
	//Inicio de Sesión
	private Usuario usuarioSesion;
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;
	private Integer SESION_IDPERFIL;
	//
	private Integer intIdSucursal;
	private Boolean blnDisabledSucursal;
	private List<Tabla> listaSucursalBus;
	private Integer 	intValidaExistencia;
	//Popup Cambio de Suculsal
	private Integer intCambioIdSucursal;
	private Integer intCambioIdSubSucursal;
	
//	private List<Sucursal> listJuridicaSucursal;
	private List<Subsucursal> listJuridicaSubsucursal;
	
	private boolean poseePermisoAnular;
	
	public CierreDiarioController(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.TRANSACCION_CIERRE_CIERREDIARIO);
		if(usuarioSesion!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	public String getInicioPage() {
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.TRANSACCION_CIERRE_CIERREFONDOS);
		if(usuarioSesion!=null && poseePermiso){
			limpiarFormulario();
			listaCierreDiarioArqueo.clear();
			deshabilitarPanelInferior();
			intIdSucursal = SESION_IDSUCURSAL;
			
			if (SESION_IDSUCURSAL.equals(59)) {
				blnDisabledSucursal = Boolean.FALSE;
			}else blnDisabledSucursal = Boolean.TRUE;
			
			validarPerfilAnular();
			log.debug("Perfil Seleccionado"+SESION_IDPERFIL);
		}else log.error("--Usuario obtenido es NULL.");
		return "";
	}
	
	private void cargarUsuario(){
		usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		SESION_IDPERFIL = usuarioSesion.getPerfil().getId().getIntIdPerfil();
		
//		PERSONA_USUARIO = usuarioSesion.getIntPersPersonaPk();
//		EMPRESA_USUARIO = usuarioSesion.getPerfil().getId().getIntPersEmpresaPk();	
	}
	
	public void cargarValoresIniciales(){
		try{
			cargarUsuario();
			listaCierreDiarioArqueo = new ArrayList<CierreDiarioArqueo>();
			cierreDiarioArqueoFiltro = new CierreDiarioArqueo();
			
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			cierreDiarioArqueoFacade = (CierreDiarioArqueoFacadeLocal) EJBFactory.getLocal(CierreDiarioArqueoFacadeLocal.class);
			
			listaTablaDenominacion = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_DENOMINACIONBILLETEMONEDA));
			//Ordenamos por valor denominacion
			Collections.sort(listaTablaDenominacion, new Comparator<Tabla>(){
				public int compare(Tabla uno, Tabla otro) {
					return new Float(otro.getStrAbreviatura()).compareTo(new Float(uno.getStrAbreviatura()));
				}
			});
			
			cargarListaSucursal();
			cargarListaSucursalBus();
			validarPerfilAnular();
			intIdSucursal = SESION_IDSUCURSAL;
			//Si la Sucursal es Sede Central(59), esta podra ver el combo de Sucursal habilitados, las otras 
			//sedes solo veran su sucursal con el combo bloqueado
			if (SESION_IDSUCURSAL.equals(59)) {
				blnDisabledSucursal = Boolean.FALSE;
			}else blnDisabledSucursal = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
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
	
	public void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
	}
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE;
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}
	
	private void limpiarFormulario(){
		intTipoDenominacionAgregar = 0;
		intCantidadDenominacionAgregar = null;
		ocultarMensaje();
		cargarUsuario();
		cierreDiarioArqueoDetalleIngresos = new CierreDiarioArqueoDetalle();
//		if(usuarioSesion.getSucursal().getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_SEDECENTRAL)){
//			sucursalSesionEsSede = Boolean.TRUE;
//		}else{
			sucursalSesionEsSede = Boolean.FALSE;
//		}
	}
	//Evento del boton "Nuevo"
	public void habilitarPanelInferior(){
		try{
			cargarUsuario();
			registrarNuevo = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			habilitarGrabar = Boolean.FALSE;
			limpiarFormulario();
			
			//Carga por defecto la sucursal y subsursal de LOGUEO
			Integer intIdSucursal = usuarioSesion.getSucursal().getId().getIntIdSucursal();
			Integer intIdSubsucursal = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
			
			usuarioSesion.setPersona(devolverPersonaCargada(usuarioSesion.getPersona().getIntIdPersona()));
			cierreDiarioArqueo = new CierreDiarioArqueo();
			cierreDiarioArqueo.getId().setIntPersEmpresa(SESION_IDEMPRESA);
			cierreDiarioArqueo.getId().setIntParaTipoArqueo(Constante.PARAM_T_TIPOARQUEO_CIERRECAJA);
			cierreDiarioArqueo.getId().setIntSucuIdSucursal(intIdSucursal);
			seleccionarSucursal();
			cierreDiarioArqueo.getId().setIntSudeIdSubsucursal(intIdSubsucursal);
			
			/*
			 * Validación si existe algun cierre para la sucursal seleccionada
			 * Si no existiera ningun cierre, se realiza una búsqueda en Control de Fondos Fijos y en Ingreso, dependiendo de la fecha 
			 * del registro se calculará la fecha de cierre diario y arqueo. 
			 */
			if(cierreDiarioArqueoFacade.existeCierreDiarioArqueo(SESION_IDEMPRESA, intIdSucursal, null)){
				//Validación si se ha realizado el cierre del dia anterior
				if(!cierreDiarioArqueoFacade.existeCierreDiaAnterior(SESION_IDEMPRESA, intIdSucursal, intIdSubsucursal)){
					mostrarMensaje(Boolean.FALSE, "No ha cerrado la caja de la sucursal para el dia anterior");
				}
				//Validación si se ha realizado el cieere del día actual
				if(cierreDiarioArqueoFacade.existeCierreDiaActual(SESION_IDEMPRESA, intIdSucursal, intIdSubsucursal)){
					mostrarMensaje(Boolean.FALSE, "Ya se ha cerrado la caja de la sucursal para el dia actual");
					return;
				}
			}
			seleccionarTipoArqueo();
			if (intValidaExistencia.equals(0)) {
				mostrarPanelInferior = Boolean.TRUE;
				habilitarGrabar = Boolean.TRUE;
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarTipoArqueo(){
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
		intValidaExistencia = 0;
		try{
			if(cierreDiarioArqueo.getId().getIntParaTipoArqueo().equals(Constante.PARAM_T_TIPOARQUEO_CIERRECAJA)){
				Date dtFechaAcerrar = cierreDiarioArqueoFacade.obtenerFechaACerrar(cierreDiarioArqueo);
				if(dtFechaAcerrar==null){
					//Agregado 13.12.2013 JCHAVEZ	
					List<ControlFondosFijos> listaControlFondosFijos = egresoFacade.obtenerControlFondosFijosACerrar(
							SESION_IDEMPRESA, cierreDiarioArqueo.getId().getIntSucuIdSucursal(), cierreDiarioArqueo.getId().getIntSudeIdSubsucursal());
					CierreDiarioArqueoDetalle ingresos = cierreDiarioArqueoFacade.obtenerCierreDiarioArqueoDetalleIngresos(cierreDiarioArqueo);

					if ((listaControlFondosFijos==null || listaControlFondosFijos.isEmpty()) && ingresos.getStrFechaRegistro()==null) {
						mostrarMensaje(Boolean.FALSE, "No existen Fondos Fijos ni movimientos para esta sucursal");
						intValidaExistencia = 1;
						return;
					}
					if (listaControlFondosFijos!=null && !listaControlFondosFijos.isEmpty()) {
						Integer cont = 0;
						Timestamp tsFechaCierre = null;
						for(ControlFondosFijos cff : listaControlFondosFijos){
							if (ingresos.getStrFechaRegistro()!=null) {
								if (cff.getTsFechaRegistro().compareTo(new Timestamp(formatoDeFecha.parse(ingresos.getStrFechaRegistro()).getTime()))>0) {
									cierreDiarioArqueo.setTsFechaCierreArqueo(cff.getTsFechaRegistro());
								}else{
									cierreDiarioArqueo.setTsFechaCierreArqueo(new Timestamp(formatoDeFecha.parse(ingresos.getStrFechaRegistro()).getTime()));
								}
							}else{
								if (cont.equals(0)) {
									cierreDiarioArqueo.setTsFechaCierreArqueo(cff.getTsFechaRegistro());
									tsFechaCierre = cff.getTsFechaRegistro();
									cont++;
								}else{
									if (cff.getTsFechaRegistro().compareTo(tsFechaCierre)>0) {
										tsFechaCierre = cff.getTsFechaRegistro();
										cierreDiarioArqueo.setTsFechaCierreArqueo(cff.getTsFechaRegistro());
									}
								}
							}
						}
					}else {
						cierreDiarioArqueo.setTsFechaCierreArqueo(new Timestamp(formatoDeFecha.parse(ingresos.getStrFechaRegistro()).getTime()));
					}
				}else{
					cierreDiarioArqueo.setTsFechaCierreArqueo(new Timestamp(dtFechaAcerrar.getTime()));
				}
				
			}else if(cierreDiarioArqueo.getId().getIntParaTipoArqueo().equals(Constante.PARAM_T_TIPOARQUEO_SUPERVISION)){
				cierreDiarioArqueo.setTsFechaCierreArqueo(new Timestamp(new Date().getTime()));
			}

			seleccionarSubsucursal();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void actualizarCierreDiario(){
		try {
			cierreDiarioArqueo.getId().setIntSucuIdSucursal(intCambioIdSucursal);
			listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(cierreDiarioArqueo.getId().getIntSucuIdSucursal());
			cierreDiarioArqueo.getId().setIntSudeIdSubsucursal(intCambioIdSubSucursal);
			
			seleccionarTipoArqueo();
			if (intValidaExistencia.equals(1)) {
				mostrarPanelInferior = Boolean.FALSE;
				habilitarGrabar = Boolean.FALSE;
			}	
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void actualizarSucursal(){
		intCambioIdSucursal = 0;
		intCambioIdSubSucursal = 0;
	}
		
	public void getListSubSucursal() {
		log.info("-------------------------------------Debugging CierreDiarioController.getListSubSucursal-------------------------------------");
		EmpresaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			intIdSucursal = Integer.valueOf(getRequestParameter("pCboSucursal"));
			
			if(intIdSucursal!=0){
				facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(intIdSucursal);
				log.info("listaSubsucursal.size: "+listaSubsucursal.size());
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListJuridicaSubsucursal(listaSubsucursal);
	}
	
	public void seleccionarSucursal(){
		try{
			listaSubsucursal = new ArrayList<Subsucursal>();
			if(cierreDiarioArqueo.getId().getIntSucuIdSucursal().intValue()>0){
				listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(cierreDiarioArqueo.getId().getIntSucuIdSucursal());
			}
			if(registrarNuevo){
				cierreDiarioArqueo.getId().setIntSudeIdSubsucursal(0);
			}
			if(cierreDiarioArqueo.getId().getIntItemCierreDiarioArqueo() == null){
				cierreDiarioArqueo.setListaCierreDiarioArqueoDetalle(new ArrayList<CierreDiarioArqueoDetalle>());
				cierreDiarioArqueoDetalleIngresos = new CierreDiarioArqueoDetalle();
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarSubsucursal(){
		try{
			cierreDiarioArqueo.setListaCierreDiarioArqueoDetalle(new ArrayList<CierreDiarioArqueoDetalle>());
			if(cierreDiarioArqueo.getId().getIntSudeIdSubsucursal().equals(new Integer(0))){				
				cierreDiarioArqueoDetalleIngresos = new CierreDiarioArqueoDetalle();
				return;
			}
			
			List<ControlFondosFijos> listaControlFondosFijos = egresoFacade.obtenerControlFondosFijosACerrar(
					SESION_IDEMPRESA, cierreDiarioArqueo.getId().getIntSucuIdSucursal(), cierreDiarioArqueo.getId().getIntSudeIdSubsucursal());
			//Agregado 12.12.2013 JCHAVEZ
			if (listaControlFondosFijos!=null && !listaControlFondosFijos.isEmpty()) {
				for(ControlFondosFijos cff : listaControlFondosFijos){
					CierreDiarioArqueoDetalle cierreDiarioArqueoDetalle = new CierreDiarioArqueoDetalle();
					cierreDiarioArqueoDetalle.setIntPersEmpresa(cff.getId().getIntPersEmpresa());
					cierreDiarioArqueoDetalle.setIntParaTipoFondoFijo(cff.getId().getIntParaTipoFondoFijo());
					cierreDiarioArqueoDetalle.setIntItemPeriodoFondo(cff.getId().getIntItemPeriodoFondo());
					cierreDiarioArqueoDetalle.setIntItemFondoFijo(cff.getId().getIntItemFondoFijo());		
					cierreDiarioArqueoDetalle.setBdMontoApertura(cff.getBdMontoApertura());
					cierreDiarioArqueoDetalle.setBdMontoRendido(cff.getBdMontoUtilizado());
					cierreDiarioArqueoDetalle.setStrNumeroApertura(cff.getStrNumeroApertura());
					cierreDiarioArqueoDetalle.setBdMontoSaldoEfectivo(cff.getBdMontoSaldo());
					
					cierreDiarioArqueo.getListaCierreDiarioArqueoDetalle().add(cierreDiarioArqueoDetalle);
				}
			}
			cierreDiarioArqueoDetalleIngresos = cierreDiarioArqueoFacade.calcularCierreDiarioArqueoDetalleIngresos(cierreDiarioArqueo);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void calcularIngresos() throws Exception{
		
		BigDecimal bdMontoTotalFondo = new BigDecimal(0);
		BigDecimal bdMontoTotalEfectivo = new BigDecimal(0);
		for(CierreDiarioArqueoDetalle  cierreDiarioArqueoDetalle : cierreDiarioArqueo.getListaCierreDiarioArqueoDetalle()){
			bdMontoTotalFondo = bdMontoTotalFondo.add(cierreDiarioArqueoDetalle.getBdMontoSaldoEfectivo());
		}
		for(CierreDiarioArqueoBillete cierreDiarioArqueoBillete : cierreDiarioArqueo.getListaCierreDiarioArqueoBillete()){
			bdMontoTotalEfectivo = bdMontoTotalEfectivo.add(cierreDiarioArqueoBillete.getBdMonto());
		}
		
		if(cierreDiarioArqueoDetalleIngresos!=null && cierreDiarioArqueoDetalleIngresos.getBdMontoSaldoEfectivo()!=null)
			bdMontoTotalEfectivo = bdMontoTotalEfectivo.add(cierreDiarioArqueoDetalleIngresos.getBdMontoSaldoEfectivo());
		
		cierreDiarioArqueo.setBdMontoTotalEfectivo(bdMontoTotalEfectivo);
		cierreDiarioArqueo.setBdMontoTotalFondo(bdMontoTotalFondo);
		cierreDiarioArqueo.setBdMontoDiferencia(cierreDiarioArqueo.getBdMontoTotalFondo().subtract(cierreDiarioArqueo.getBdMontoTotalEfectivo()));
	}
	
	public void buscar(){
		try{			
			if(dtFechaFiltro!=null){
				cierreDiarioArqueoFiltro.setTsFechaCierreArqueo(new Timestamp(dtFechaFiltro.getTime()));
			}else{
				cierreDiarioArqueoFiltro.setTsFechaCierreArqueo(null);
			}
			
			listaPersonaFiltro = buscarPersonaFiltro();
			
			cierreDiarioArqueoFiltro.setIntParaEstadoCierre(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			cierreDiarioArqueoFiltro.getId().setIntPersEmpresa(SESION_IDEMPRESA);
			cierreDiarioArqueoFiltro.getId().setIntSucuIdSucursal(intIdSucursal);
			listaCierreDiarioArqueo = egresoFacade.buscarCierreDiarioArqueo(cierreDiarioArqueoFiltro, listaPersonaFiltro);
			
			for(CierreDiarioArqueo cierreDiarioArqueoTemp : listaCierreDiarioArqueo){
				for(Sucursal sucursal : listaSucursal){
					if(cierreDiarioArqueoTemp.getId().getIntSucuIdSucursal().equals(sucursal.getId().getIntIdSucursal())){
						cierreDiarioArqueoTemp.setSucursal(sucursal);
						break;
					}
				}
				cierreDiarioArqueoTemp.setPersona(devolverPersonaCargada(cierreDiarioArqueoTemp.getIntPersPersonaResponsable()));
			}
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la búsqueda.");
			log.error(e.getMessage(),e);
		}
	}

	private List<Persona> buscarPersonaFiltro(){
		List<Persona> listaPersona = new ArrayList<Persona>();
		try{
			if(strTextoFiltroPersona==null || strTextoFiltroPersona.isEmpty()){
				return listaPersona;
			}
			
			Persona persona = null;			
			if(intTipoFiltroPersona.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
				Natural natural = new Natural();
				natural.setStrNombres(strTextoFiltroPersona);
				List<Natural> listaNatural = personaFacade.getListaNaturalPorBusqueda(natural);
				for(Natural natu : listaNatural){
					listaPersona.add(personaFacade.getPersonaPorPK(natu.getIntIdPersona()));
				}
			}else if(intTipoFiltroPersona.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
				persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
						Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
						strTextoFiltroPersona, 
						SESION_IDEMPRESA);
				if(persona!=null)listaPersona.add(persona);
			}
			
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return listaPersona;
	}
	
	public void anularCierre(){
		try{
			cargarUsuario();
			CierreDiarioArqueo cierreDiarioArqueoAnular = registroSeleccionado;
			cierreDiarioArqueoAnular.setIntParaEstadoCierre(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			cierreDiarioArqueoAnular.setTsFechaCierreAnula(new Timestamp(new Date().getTime()));
			cierreDiarioArqueoAnular.setIntPersEmpresaCierreAn(SESION_IDEMPRESA);
			cierreDiarioArqueoAnular.setIntPersPersonaCierreAn(SESION_IDUSUARIO);
			cierreDiarioArqueoFacade.modificarCierreDiarioArqueo(cierreDiarioArqueoAnular);			
			
			buscar();
			deshabilitarPanelInferior();
			mostrarMensaje(Boolean.FALSE,"Se anuló correctamente el cierre");
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrió un error durante la anulación del cierre");
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (CierreDiarioArqueo)event.getComponent().getAttributes().get("item");
			log.info("reg selec:"+registroSeleccionado);						
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verRegistro(){
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try{
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			intTipoDenominacionAgregar = 0;
			intCantidadDenominacionAgregar = 0;
			
			//cierreDiarioArqueo = (CierreDiarioArqueo)event.getComponent().getAttributes().get("item");
			cierreDiarioArqueo = registroSeleccionado;
			
			cierreDiarioArqueoDetalleIngresos = null;
			for(CierreDiarioArqueoDetalle cierreDiarioArqueoDetalle : cierreDiarioArqueo.getListaCierreDiarioArqueoDetalle()){
				if(cierreDiarioArqueoDetalle.getStrNumeroApertura()==null){
					cierreDiarioArqueoDetalleIngresos = cierreDiarioArqueoDetalle;
				}
			}
			cierreDiarioArqueo.getListaCierreDiarioArqueoDetalle().remove(cierreDiarioArqueoDetalleIngresos);			
			
			seleccionarSucursal();
		}catch (Exception e) {
			mensaje = "Ocurrio un error en la selección de egreso.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
//	private boolean cadenaVacia(String strCadena){
//		if(strCadena==null || strCadena.isEmpty()){
//			return Boolean.TRUE;
//		}
//		return Boolean.FALSE;
//	}
//	
//	private boolean validar(String strCadena, BigDecimal bdMonto){
//		if(cadenaVacia(strCadena) && bdMonto!=null){
//			return Boolean.FALSE;
//		}else if(!cadenaVacia(strCadena) && bdMonto==null){
//			return Boolean.FALSE;
//		}
//		return Boolean.TRUE;
//	}
	
	public void grabar(){
		log.info("--grabar");
		Boolean exito = Boolean.FALSE;
		String mensaje = "";
		try {
			//validaciones
			Integer intIdSucursal = cierreDiarioArqueo.getId().getIntSucuIdSucursal();
			Integer intIdSubsucursal = cierreDiarioArqueo.getId().getIntSudeIdSubsucursal();			
			if(cierreDiarioArqueoFacade.existeCierreDiaActual(SESION_IDEMPRESA, intIdSucursal, intIdSubsucursal)){
				mostrarMensaje(Boolean.FALSE, "Ya se ha cerrado la caja de la sucursal para el dia actual");return;
			}
			
			if(cierreDiarioArqueo.getStrObservacion()==null  || cierreDiarioArqueo.getStrObservacion().isEmpty()){
				mensaje = "Debe ingresar una obersvación.";	return;
			}
			
//			for(CierreDiarioArqueoDetalle cierreDiarioArqueoDetalle : cierreDiarioArqueo.getListaCierreDiarioArqueoDetalle()){
//				if(cierreDiarioArqueoDetalle.getStrDetalleRendido()==null || cierreDiarioArqueoDetalle.getStrDetalleRendido().isEmpty()){
//					mensaje = "Debe ingresar la descripción de rendición de todos los fondos fijos.";return;
//				}
//				if(!validar(cierreDiarioArqueoDetalle.getStrDetalleReciboSunat(),cierreDiarioArqueoDetalle.getBdMontoReciboSunat())){
//					mensaje = "Debe ingresar la descripción y monto de recibos por sunat del fondo fijo con apertura "+cierreDiarioArqueoDetalle.getStrNumeroApertura();return;
//				}
//				if(!validar(cierreDiarioArqueoDetalle.getStrDetalleValeRendir(),cierreDiarioArqueoDetalle.getBdMontoValesRendir())){
//					mensaje = "Debe ingresar la descripción y monto de vales a rendir del fondo fijo con apertura "+cierreDiarioArqueoDetalle.getStrNumeroApertura();return;
//				}
//			}
			
			if(cierreDiarioArqueo.getListaCierreDiarioArqueoBillete()==null || cierreDiarioArqueo.getListaCierreDiarioArqueoBillete().isEmpty()){
				mensaje = "Debe ingresar una lista de detalles de billetes y monedas.";return;
			}
			//fin validaciones
			
			calcularIngresos();
			log.info(cierreDiarioArqueo);
			if(cierreDiarioArqueo.getBdMontoDiferencia().intValue()!=0){
				mensaje = "Existe una diferencia entre los montos de fondos fijos y los montos arqueados. ";
			}
						
			cierreDiarioArqueo.getListaCierreDiarioArqueoDetalle().add(cierreDiarioArqueoDetalleIngresos);
			cierreDiarioArqueo.setIntPersEmpresaResponsable(SESION_IDEMPRESA);
			cierreDiarioArqueo.setIntPersPersonaResponsable(usuarioSesion.getIntPersPersonaPk());
			cierreDiarioArqueo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			cierreDiarioArqueo.setIntParaEstadoCierre(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			cierreDiarioArqueo = egresoFacade.grabarCierreDiarioArqueo(cierreDiarioArqueo);
			
			mensaje = mensaje+"Se registrò correctamente el cierre diario y arqueo.";
			
			buscar();
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mensaje = "Ocurrio un error durante el proceso de cierre de fondos.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}

	private void cargarListaSucursal() throws Exception{
		listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
		List<Sucursal> listaSucursalTemp = listaSucursal;
		//Ordenamos por nombre
		Collections.sort(listaSucursalTemp, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});
		
	}
	
	private void cargarListaSucursalBus() throws NumberFormatException, BusinessException{
		listaSucursalBus = new ArrayList<Tabla>();
//		listaSucursalBus = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
		Sucursal sucursal;
		Tabla tabla;
		for(Object o : listaSucursal){
			 sucursal = (Sucursal)o;
			 tabla = new Tabla();
			 tabla.setIntIdDetalle(sucursal.getId().getIntIdSucursal());
			 tabla.setStrDescripcion(sucursal.getJuridica().getStrSiglas());
			 listaSucursalBus.add(tabla);
		}
	}
	
	public void validarPerfilAnular(){
		poseePermisoAnular = false;
		if (SESION_IDPERFIL.equals(Constante.PARAM_T_PERFIL_TESORERO)) {
			poseePermisoAnular = true;
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
	
	private BigDecimal obtenerValorDenominacion(Integer intTipoDenominacion)throws Exception{
		BigDecimal bdValorDenominacion = null;
		for(Tabla tabla : listaTablaDenominacion){
			if(tabla.getIntIdDetalle().equals(intTipoDenominacion)){
				bdValorDenominacion = new BigDecimal(tabla.getStrAbreviatura());
				break;
			}
		}
		return bdValorDenominacion;
	}
	
	public void agregarDenominacionBilleteMoneda(){
		try{
			if(intTipoDenominacionAgregar.equals(new Integer(0))){
				return;
			}
			
			boolean denominacionYaAgregada = Boolean.FALSE;
			for(CierreDiarioArqueoBillete cierreDiarioArqueoBillete : cierreDiarioArqueo.getListaCierreDiarioArqueoBillete()){
				if(cierreDiarioArqueoBillete.getId().getIntParaTipoMonedaBillete().equals(intTipoDenominacionAgregar)){
					cierreDiarioArqueoBillete.setIntCantidad(cierreDiarioArqueoBillete.getIntCantidad() + intCantidadDenominacionAgregar);
					cierreDiarioArqueoBillete.setBdMonto(obtenerValorDenominacion(intTipoDenominacionAgregar).multiply(
							new BigDecimal(cierreDiarioArqueoBillete.getIntCantidad())));
					denominacionYaAgregada = Boolean.TRUE;
					break;
				}
			}
			if(!denominacionYaAgregada){
				CierreDiarioArqueoBillete cierreDiarioArqueoBillete = new CierreDiarioArqueoBillete();
				cierreDiarioArqueoBillete.getId().setIntParaTipoMonedaBillete(intTipoDenominacionAgregar);
				cierreDiarioArqueoBillete.setIntCantidad(intCantidadDenominacionAgregar);
				cierreDiarioArqueoBillete.setBdMonto(obtenerValorDenominacion(intTipoDenominacionAgregar).multiply(
						new BigDecimal(cierreDiarioArqueoBillete.getIntCantidad())));
				cierreDiarioArqueo.getListaCierreDiarioArqueoBillete().add(cierreDiarioArqueoBillete);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void calcularSaldoEfectivo(ActionEvent event){
		try{
			CierreDiarioArqueoDetalle cierreDiarioArqueoDetalle = (CierreDiarioArqueoDetalle)event.getComponent().getAttributes().get("item");
			log.info("--calcularSaldoEfectivo:"+cierreDiarioArqueoDetalle.getStrNumeroApertura());
			BigDecimal bdSaldoEfectivo = cierreDiarioArqueoDetalle.getBdMontoApertura().subtract(cierreDiarioArqueoDetalle.getBdMontoRendido());
			log.info("subtotal:"+bdSaldoEfectivo);
			if(cierreDiarioArqueoDetalle.getBdMontoReciboSunat()!=null){
				bdSaldoEfectivo = bdSaldoEfectivo.subtract(cierreDiarioArqueoDetalle.getBdMontoReciboSunat());
				log.info("bdSaldoEfectivo:"+bdSaldoEfectivo);
			}
			if(cierreDiarioArqueoDetalle.getBdMontoValesRendir()!=null){
				bdSaldoEfectivo = bdSaldoEfectivo.subtract(cierreDiarioArqueoDetalle.getBdMontoValesRendir());
				log.info("bdSaldoEfectivo:"+bdSaldoEfectivo);
			}
			cierreDiarioArqueoDetalle.setBdMontoSaldoEfectivo(bdSaldoEfectivo);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
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
	public Usuario getUsuarioSesion() {
		return usuarioSesion;
	}
	public void setUsuarioSesion(Usuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}
	public CierreDiarioArqueo getCierreDiarioArqueo() {
		return cierreDiarioArqueo;
	}
	public void setCierreDiarioArqueo(CierreDiarioArqueo cierreDiarioArqueo) {
		this.cierreDiarioArqueo = cierreDiarioArqueo;
	}
	public List<CierreDiarioArqueo> getListaCierreDiarioArqueo() {
		return listaCierreDiarioArqueo;
	}
	public void setListaCierreDiarioArqueo(List<CierreDiarioArqueo> listaCierreDiarioArqueo) {
		this.listaCierreDiarioArqueo = listaCierreDiarioArqueo;
	}
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}	
	public List<Subsucursal> getListaSubsucursal() {
		return listaSubsucursal;
	}
	public void setListaSubsucursal(List<Subsucursal> listaSubsucursal) {
		this.listaSubsucursal = listaSubsucursal;
	}
	public Integer getIntTipoDenominacionAgregar() {
		return intTipoDenominacionAgregar;
	}
	public void setIntTipoDenominacionAgregar(Integer intTipoDenominacionAgregar) {
		this.intTipoDenominacionAgregar = intTipoDenominacionAgregar;
	}
	public Integer getIntCantidadDenominacionAgregar() {
		return intCantidadDenominacionAgregar;
	}
	public void setIntCantidadDenominacionAgregar(Integer intCantidadDenominacionAgregar) {
		this.intCantidadDenominacionAgregar = intCantidadDenominacionAgregar;
	}
	public CierreDiarioArqueoDetalle getCierreDiarioArqueoDetalleIngresos() {
		return cierreDiarioArqueoDetalleIngresos;
	}
	public void setCierreDiarioArqueoDetalleIngresos(CierreDiarioArqueoDetalle cierreDiarioArqueoDetalleIngresos) {
		this.cierreDiarioArqueoDetalleIngresos = cierreDiarioArqueoDetalleIngresos;
	}
	public CierreDiarioArqueo getCierreDiarioArqueoFiltro() {
		return cierreDiarioArqueoFiltro;
	}
	public void setCierreDiarioArqueoFiltro(CierreDiarioArqueo cierreDiarioArqueoFiltro) {
		this.cierreDiarioArqueoFiltro = cierreDiarioArqueoFiltro;
	}
	public Integer getIntTipoFiltroPersona() {
		return intTipoFiltroPersona;
	}
	public void setIntTipoFiltroPersona(Integer intTipoFiltroPersona) {
		this.intTipoFiltroPersona = intTipoFiltroPersona;
	}
	public String getStrTextoFiltroPersona() {
		return strTextoFiltroPersona;
	}
	public void setStrTextoFiltroPersona(String strTextoFiltroPersona) {
		this.strTextoFiltroPersona = strTextoFiltroPersona;
	}
	public Date getDtFechaFiltro() {
		return dtFechaFiltro;
	}
	public void setDtFechaFiltro(Date dtFechaFiltro) {
		this.dtFechaFiltro = dtFechaFiltro;
	}
	public boolean isSucursalSesionEsSede() {
		return sucursalSesionEsSede;
	}
	public void setSucursalSesionEsSede(boolean sucursalSesionEsSede) {
		this.sucursalSesionEsSede = sucursalSesionEsSede;
	}
	public List<Tabla> getListaTablaDenominacion() {
		return listaTablaDenominacion;
	}
	public void setListaTablaDenominacion(List<Tabla> listaTablaDenominacion) {
		this.listaTablaDenominacion = listaTablaDenominacion;
	}
	public CierreDiarioArqueo getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(CierreDiarioArqueo registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	//Agregado JCHAVEZ 10.12.2013
	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		CierreDiarioController.log = log;
	}

	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}

	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
	}

	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}

	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}

	public EgresoFacadeLocal getEgresoFacade() {
		return egresoFacade;
	}

	public void setEgresoFacade(EgresoFacadeLocal egresoFacade) {
		this.egresoFacade = egresoFacade;
	}

	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}

	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}

	public CierreDiarioArqueoFacadeLocal getCierreDiarioArqueoFacade() {
		return cierreDiarioArqueoFacade;
	}

	public void setCierreDiarioArqueoFacade(
			CierreDiarioArqueoFacadeLocal cierreDiarioArqueoFacade) {
		this.cierreDiarioArqueoFacade = cierreDiarioArqueoFacade;
	}

	public List<Persona> getListaPersonaFiltro() {
		return listaPersonaFiltro;
	}

	public void setListaPersonaFiltro(List<Persona> listaPersonaFiltro) {
		this.listaPersonaFiltro = listaPersonaFiltro;
	}

	public Integer getSESION_IDEMPRESA() {
		return SESION_IDEMPRESA;
	}

	public void setSESION_IDEMPRESA(Integer sESION_IDEMPRESA) {
		SESION_IDEMPRESA = sESION_IDEMPRESA;
	}

	public Integer getSESION_IDUSUARIO() {
		return SESION_IDUSUARIO;
	}

	public void setSESION_IDUSUARIO(Integer sESION_IDUSUARIO) {
		SESION_IDUSUARIO = sESION_IDUSUARIO;
	}

	public Integer getSESION_IDSUCURSAL() {
		return SESION_IDSUCURSAL;
	}

	public void setSESION_IDSUCURSAL(Integer sESION_IDSUCURSAL) {
		SESION_IDSUCURSAL = sESION_IDSUCURSAL;
	}

	public Integer getSESION_IDSUBSUCURSAL() {
		return SESION_IDSUBSUCURSAL;
	}

	public void setSESION_IDSUBSUCURSAL(Integer sESION_IDSUBSUCURSAL) {
		SESION_IDSUBSUCURSAL = sESION_IDSUBSUCURSAL;
	}

	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}

	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}

	public Boolean getBlnDisabledSucursal() {
		return blnDisabledSucursal;
	}

	public void setBlnDisabledSucursal(Boolean blnDisabledSucursal) {
		this.blnDisabledSucursal = blnDisabledSucursal;
	}

	public List<Tabla> getListaSucursalBus() {
		return listaSucursalBus;
	}

	public void setListaSucursalBus(List<Tabla> listaSucursalBus) {
		this.listaSucursalBus = listaSucursalBus;
	}

	public Integer getIntValidaExistencia() {
		return intValidaExistencia;
	}

	public void setIntValidaExistencia(Integer intValidaExistencia) {
		this.intValidaExistencia = intValidaExistencia;
	}

	public Integer getIntCambioIdSucursal() {
		return intCambioIdSucursal;
	}

	public void setIntCambioIdSucursal(Integer intCambioIdSucursal) {
		this.intCambioIdSucursal = intCambioIdSucursal;
	}

	public Integer getIntCambioIdSubSucursal() {
		return intCambioIdSubSucursal;
	}

	public void setIntCambioIdSubSucursal(Integer intCambioIdSubSucursal) {
		this.intCambioIdSubSucursal = intCambioIdSubSucursal;
	}

	public List<Subsucursal> getListJuridicaSubsucursal() {
		return listJuridicaSubsucursal;
	}

	public void setListJuridicaSubsucursal(List<Subsucursal> listJuridicaSubsucursal) {
		this.listJuridicaSubsucursal = listJuridicaSubsucursal;
	}

	public Integer getSESION_IDPERFIL() {
		return SESION_IDPERFIL;
	}

	public void setSESION_IDPERFIL(Integer sESION_IDPERFIL) {
		SESION_IDPERFIL = sESION_IDPERFIL;
	}

	public boolean isPoseePermisoAnular() {
		return poseePermisoAnular;
	}

	public void setPoseePermisoAnular(boolean poseePermisoAnular) {
		this.poseePermisoAnular = poseePermisoAnular;
	}
}