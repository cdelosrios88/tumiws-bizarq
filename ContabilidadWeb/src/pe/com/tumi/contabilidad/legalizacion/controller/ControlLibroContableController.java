package pe.com.tumi.contabilidad.legalizacion.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.contabilidad.fileUpload.FileUploadControllerContabilidad;
import pe.com.tumi.contabilidad.legalizacion.auditoria.ControlLibroContableAuditoria;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalle;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalleComp;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalleId;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacionComp;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacionId;
import pe.com.tumi.contabilidad.legalizacion.facade.LegalizacionFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
//import pe.com.tumi.parametro.auditoria.facade.AuditoriaFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class ControlLibroContableController {

	protected static Logger log = Logger
			.getLogger(ControlLibroContableController.class); 

	// propiedades que capturan atributos de sesión
	private Integer 						IDUSUARIO_SESION;
	private Integer 						IDEMPRESA_SESION;

	private LibroContableDetalle			libroContableDetalle;
	private LibroContableDetalle			libroContableDetalleOld;
	private LibroContableDetalleComp		libroContableDetalleComp;
	private LibroLegalizacion 				libroLegalizacion;
	private LibroLegalizacion				libroLegalizacionOld;
	private LibroLegalizacionComp			libroLegalizacionComp;
	private LibroLegalizacionComp			personaSeleccionada;
	private LibroLegalizacionComp 			devLibroLegalizacionComp;
	private List<LibroLegalizacionComp>		listaLibrosLegalizaciones;
	private List<LibroLegalizacionComp>		listaLegalizaciones;
	private	List<LibroLegalizacionComp>		listaPersonaJuridica;
	private List<LibroLegalizacionComp> 	listaLegalizacionesSaldo;
	private	List<LibroContableDetalleComp>	listaLibroContableDetalle;
	private	List<LibroContableDetalleComp>	listaUltimaLegalizacion;
	private List<LibroContableDetalleComp>	listaLibroContableDetalleNulo;
	private LegalizacionFacadeLocal 		legalizacionFacadeLocal;
	private List<SelectItem> 				listYears;
	private Boolean 						blnShowDivLegalizacion;
	private Integer 						intAnio;
	private Integer 						intMes;
	private String 							strArchivoPDT;
	private String 							strNotariaPublica;
	private Integer 						intParaLibroContable;
	private	String							strFiltroTextoRazon;
	private	String							strFiltroTextoRuc;
	private Integer 						intUltimoFolioLegal;
	private String							strPkLibroLegalizacion;
	private String							strPkLibroContableDetalle;
	private Integer							intFolioInicio;
	private Integer							intFolioFin;
	private Integer							intFolioInicioLibro;
	private Integer							intFolioFinLibro;
	private Boolean							isAdjunto;
	private Integer							intBuscaParaLibroContable;
	private Boolean							isEnlazarNulo;
	private Boolean							isStrPkLibroLegalizacion;

	//Adjuntar Archivos
	private Archivo	archivoAdjuntoCertificadoLegalizacion;
	private Archivo	archivoAdjuntoDocumentoPDT;
	
	private GeneralFacadeRemote archivoFacade;
	
	//Variable para validar los errores al registrar en los Modales
	private String strMsgError; 
	private Integer intError; 

	//Validar Botones
	private boolean btnModificar;
	private boolean btnEliminar;
	private boolean btnDescargar;
	
	public ControlLibroContableController() throws BusinessException, EJBFactoryException {
		log.info("-------------------------------------Debugging ControlLibroContableController - CONSTRUCTOR -------------------------------------");
		legalizacionFacadeLocal = (LegalizacionFacadeLocal) EJBFactory.getLocal(LegalizacionFacadeLocal.class);
		archivoFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		Usuario usuarioSesion = (Usuario) FacesContextUtil.getRequest().getSession().getAttribute("usuario");
		IDUSUARIO_SESION = usuarioSesion.getIntPersPersonaPk();
		IDEMPRESA_SESION = usuarioSesion.getEmpresa().getIntIdEmpresa();
		cargarValoresIniciales();
	}

	public String getInicioPage() {
		log.info("-------------------------------------Debugging getInicioPage -------------------------------------");
		limpiarFormAgregarLegalizacion();
		limpiarFormAgregarLegalizacionPeriodo();
		cargarValoresIniciales();
		return "";
	}

	public void cargarValoresIniciales(){
		log.info("-------------------------------------Debugging cargarValoresIniciales -------------------------------------");
		libroContableDetalle = new LibroContableDetalle();
		libroContableDetalleOld = new LibroContableDetalle();
		libroContableDetalleComp = new LibroContableDetalleComp();

		libroLegalizacion = new LibroLegalizacion();
		libroLegalizacionOld = new LibroLegalizacion();
		libroLegalizacionComp = new LibroLegalizacionComp();

		devLibroLegalizacionComp = new LibroLegalizacionComp(); //Almacena LibroLegalizacion / LibroContableDetalle, para guardar

		setBlnShowDivLegalizacion(false);

		listaLibrosLegalizaciones = new ArrayList<LibroLegalizacionComp>();
		listaLegalizaciones = new ArrayList<LibroLegalizacionComp>();
		listaLibroContableDetalleNulo = new ArrayList<LibroContableDetalleComp>();
		listaLibroContableDetalle = new ArrayList<LibroContableDetalleComp>();
		listaUltimaLegalizacion = new ArrayList<LibroContableDetalleComp>();
		listaLegalizacionesSaldo = new ArrayList<LibroLegalizacionComp>();

		// Inicializamos los años
		listYears = new ArrayList<SelectItem>();
		setIsEnlazarNulo(false);
	}
	
	public void abrirPopUpBuscarPersonaJuridica(){
		try{
			setStrFiltroTextoRazon("");
			setStrFiltroTextoRuc("");
			
			listaPersonaJuridica = null;
			listaPersonaJuridica = new ArrayList<LibroLegalizacionComp>();
			System.out.println("abrirPopUpBuscarPersonaJuridica");
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}

	public void buscarPersonaJuridica(){
		try{
			listaPersonaJuridica = legalizacionFacadeLocal.getListaPersonaJuridica(getStrFiltroTextoRazon(), getStrFiltroTextoRuc() , IDEMPRESA_SESION);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}

	public void buscarLibrosLegalizacion(){
		try{
			if (getIntBuscaParaLibroContable() != null){
				if (getIntBuscaParaLibroContable() == 0){
					setIntBuscaParaLibroContable(null);
				}
			}
			listaLibrosLegalizaciones = legalizacionFacadeLocal.getListaLibrosLegalizaciones(IDEMPRESA_SESION, getIntBuscaParaLibroContable());
		}catch(BusinessException e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void seleccionarPersonaJuridica(ActionEvent event) {
		try {
			personaSeleccionada = (LibroLegalizacionComp) event.getComponent().getAttributes().get("item");
			setStrNotariaPublica(personaSeleccionada.getStrJuriRazonSocial());
			getLibroLegalizacion().setIntPersEmpresaLegal(personaSeleccionada.getLibroLegalizacion().getIntPersEmpresaLegal());
			getLibroLegalizacion().setIntPersPersona(personaSeleccionada.getLibroLegalizacion().getIntPersPersona());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void listadosControlLibroContable() throws BusinessException{
		//LISTADO DE TODAS LAS LEGALIZACIONES
		try{
			buscarLibrosLegalizacion();
			listadoLibroLegalizacion();
			listadoLibroContableDetalle();
		}catch(BusinessException e){
			log.error("Error en listadosControlLibroContable --> "+e);
		}
	}
	
	public void listadoLibroLegalizacion() throws BusinessException{
		if (FacesContextUtil.getRequestParameter("pTipoLibroContable") != null){
			Integer pTipoLibroContable = Integer.parseInt(FacesContextUtil.getRequestParameter("pTipoLibroContable"));
			setIntParaLibroContable(pTipoLibroContable);
		}
		listaLegalizaciones = legalizacionFacadeLocal.getListaLegalizaciones(IDEMPRESA_SESION, getIntParaLibroContable());
	}

	public void listadoLibroContableDetalle() throws BusinessException{
		if (FacesContextUtil.getRequestParameter("pTipoLibroContable") != null){
			Integer pTipoLibroContable = Integer.parseInt(FacesContextUtil.getRequestParameter("pTipoLibroContable"));
			setIntParaLibroContable(pTipoLibroContable);
		}
		listaLibroContableDetalle = legalizacionFacadeLocal.getListaLibroContableDetalle(IDEMPRESA_SESION, getIntParaLibroContable());
	}

	/**
	 * Autor: Javier Bermúdez T.
	 * Funcionalidad: Este Metodo se activa cuando seleccionamos un registro de la grilla de Legalizaciones.
	 * @autor Javier Bermúdez T.
	 * @version 1.0
	 * @param event
	 * @return retorna todos los valores del registro seleccionado.
	 * @throws BusinessException
	 */
	public void seleccionarLegalizacion(ActionEvent event){
		try {
			log.info("----seleccionarLegalizacion-----");
			setBtnEliminar(true);

			LibroLegalizacion libroOld = ((LibroLegalizacionComp)event.getComponent().getAttributes().get("itemLegalizacion")).getLibroLegalizacion();
			libroLegalizacionOld.getId().setIntPersEmpresa(libroOld.getId().getIntPersEmpresa());
			libroLegalizacionOld.getId().setIntParaLibroContable(libroOld.getId().getIntParaLibroContable());
			libroLegalizacionOld.getId().setIntItemLibroLegalizacion(libroOld.getId().getIntItemLibroLegalizacion());
			libroLegalizacionOld.setIntPersEmpresaLegal(libroOld.getIntPersEmpresaLegal());
			libroLegalizacionOld.setIntPersPersona(libroOld.getIntPersPersona());
			libroLegalizacionOld.setDtFechaLegalizacion(libroOld.getDtFechaLegalizacion());
			libroLegalizacionOld.setIntNroCertificado(libroOld.getIntNroCertificado());
			libroLegalizacionOld.setIntFolioInicio(libroOld.getIntFolioInicio());
			libroLegalizacionOld.setIntFolioFin(libroOld.getIntFolioFin());
			libroLegalizacionOld.setIntParaTipo(libroOld.getIntParaTipo());
			libroLegalizacionOld.setIntItemArchivo(libroOld.getIntItemArchivo());
			libroLegalizacionOld.setIntItemHistorico(libroOld.getIntItemHistorico());

			LibroLegalizacionComp libroComp = (LibroLegalizacionComp)event.getComponent().getAttributes().get("itemLegalizacion"); 
			libroLegalizacionComp.getLibroLegalizacion().getId().setIntPersEmpresa(libroComp.getLibroLegalizacion().getId().getIntPersEmpresa());
			libroLegalizacionComp.getLibroLegalizacion().getId().setIntParaLibroContable(libroComp.getLibroLegalizacion().getId().getIntParaLibroContable());
			libroLegalizacionComp.getLibroLegalizacion().getId().setIntItemLibroLegalizacion(libroComp.getLibroLegalizacion().getId().getIntItemLibroLegalizacion());
			libroLegalizacionComp.getLibroLegalizacion().setIntPersEmpresaLegal(libroComp.getLibroLegalizacion().getIntPersEmpresaLegal());
			libroLegalizacionComp.getLibroLegalizacion().setIntPersPersona(libroComp.getLibroLegalizacion().getIntPersPersona());
			libroLegalizacionComp.getLibroLegalizacion().setDtFechaLegalizacion(libroComp.getLibroLegalizacion().getDtFechaLegalizacion());
			libroLegalizacionComp.getLibroLegalizacion().setIntNroCertificado(libroComp.getLibroLegalizacion().getIntNroCertificado());
			libroLegalizacionComp.getLibroLegalizacion().setIntFolioInicio(libroComp.getLibroLegalizacion().getIntFolioInicio());
			libroLegalizacionComp.getLibroLegalizacion().setIntFolioFin(libroComp.getLibroLegalizacion().getIntFolioFin());
			libroLegalizacionComp.getLibroLegalizacion().setIntParaTipo(libroComp.getLibroLegalizacion().getIntParaTipo());
			libroLegalizacionComp.getLibroLegalizacion().setIntItemArchivo(libroComp.getLibroLegalizacion().getIntItemArchivo());
			libroLegalizacionComp.getLibroLegalizacion().setIntItemHistorico(libroComp.getLibroLegalizacion().getIntItemHistorico());
			libroLegalizacionComp.setStrJuriRazonSocial(libroComp.getStrJuriRazonSocial());
			libroLegalizacionComp.setStrPersRuc(libroComp.getStrPersRuc());

			setBtnModificar(true);
			
			setIntUltimoFolioLegal(legalizacionFacadeLocal.getUltimoFolio(IDEMPRESA_SESION, getIntParaLibroContable()));
			if (!libroLegalizacionOld.getIntFolioFin().equals(getIntUltimoFolioLegal())){
				setBtnEliminar(false);
			}			
		} catch (Exception e) {
			log.error("Error en seleccionarLegalizacion --> "+e);
		}
	}

	/**
	 * Autor: Javier Bermúdez T.
	 * Funcionalidad: Este Metodo se activa cuando seleccionamos un registro de la grilla de Libros Contables Detalle.
	 * @autor Javier Bermúdez T.
	 * @version 1.0
	 * @param event
	 * @return retorna todos los valores del registro seleccionado.
	 * @throws BusinessException
	 */
	public void seleccionarLibroContable(ActionEvent event){
		try {
			setBtnEliminar(false);
			
			LibroContableDetalle libroDetaOld = ((LibroContableDetalleComp)event.getComponent().getAttributes().get("itemLibro")).getLibroContableDetalle();
			libroContableDetalleOld.getId().setIntEmpresaPk(libroDetaOld.getId().getIntEmpresaPk());
			libroContableDetalleOld.getId().setIntLibroContable(libroDetaOld.getId().getIntLibroContable());
			libroContableDetalleOld.getId().setIntItemLibroContableDet(libroDetaOld.getId().getIntItemLibroContableDet());
			libroContableDetalleOld.setIntPeriodo(libroDetaOld.getIntPeriodo());
			libroContableDetalleOld.setIntFolioInicio(libroDetaOld.getIntFolioInicio());
			libroContableDetalleOld.setIntFolioFin(libroDetaOld.getIntFolioFin());
			libroContableDetalleOld.setIntItemLibroLegalizacion(libroDetaOld.getIntItemLibroLegalizacion());
			libroContableDetalleOld.setIntTipo(libroDetaOld.getIntTipo());
			libroContableDetalleOld.setIntItemArchivo(libroDetaOld.getIntItemArchivo());
			libroContableDetalleOld.setIntItemHistorico(libroDetaOld.getIntItemHistorico());

			LibroContableDetalleComp libroDetaComp = (LibroContableDetalleComp)event.getComponent().getAttributes().get("itemLibro");
			libroContableDetalleComp.getLibroContableDetalle().getId().setIntEmpresaPk(libroDetaComp.getLibroContableDetalle().getId().getIntEmpresaPk());
			libroContableDetalleComp.getLibroContableDetalle().getId().setIntLibroContable(libroDetaComp.getLibroContableDetalle().getId().getIntLibroContable());
			libroContableDetalleComp.getLibroContableDetalle().getId().setIntItemLibroContableDet(libroDetaComp.getLibroContableDetalle().getId().getIntItemLibroContableDet());
			libroContableDetalleComp.getLibroContableDetalle().setIntPeriodo(libroDetaComp.getLibroContableDetalle().getIntPeriodo());
			libroContableDetalleComp.getLibroContableDetalle().setIntFolioInicio(libroDetaComp.getLibroContableDetalle().getIntFolioInicio());
			libroContableDetalleComp.getLibroContableDetalle().setIntFolioFin(libroDetaComp.getLibroContableDetalle().getIntFolioFin());
			libroContableDetalleComp.getLibroContableDetalle().setIntItemLibroLegalizacion(libroDetaComp.getLibroContableDetalle().getIntItemLibroLegalizacion());
			libroContableDetalleComp.getLibroContableDetalle().setIntTipo(libroDetaComp.getLibroContableDetalle().getIntTipo());
			libroContableDetalleComp.getLibroContableDetalle().setIntItemArchivo(libroDetaComp.getLibroContableDetalle().getIntItemArchivo());
			libroContableDetalleComp.getLibroContableDetalle().setIntItemHistorico(libroDetaComp.getLibroContableDetalle().getIntItemHistorico());
			
			setBtnModificar(true);
			
			if (libroDetaComp.getLibroContableDetalle().getIntItemLibroLegalizacion() == null){
				setIntUltimoFolioLegal(legalizacionFacadeLocal.getUltimoFolio(libroDetaComp.getLibroContableDetalle().getId().getIntEmpresaPk(), libroDetaComp.getLibroContableDetalle().getId().getIntLibroContable()));
				if (libroDetaComp.getLibroContableDetalle().getIntFolioFin().equals(getIntUltimoFolioLegal())){//SI ES EL ULTIMO REGISTRO, NO PUEDE MODIFICAR EL FOLIO INICIAL
					setBtnEliminar(true);
				}
			}else{
				//PARA RECUPERAR EL FOLIO DE INICIO Y FINAL DEL LIBRO DE LEGALIZACION SELECCIONADO
				listaUltimaLegalizacion = null;
				listaUltimaLegalizacion = legalizacionFacadeLocal.getListaUltimoFolioDetalle(libroDetaComp.getLibroContableDetalle().getId().getIntEmpresaPk(), 
																								libroDetaComp.getLibroContableDetalle().getId().getIntLibroContable(), 
																									libroDetaComp.getLibroContableDetalle().getIntItemLibroLegalizacion());
				for(LibroContableDetalleComp libroultimo : listaUltimaLegalizacion){
					setIntFolioInicioLibro(libroultimo.getLibroContableDetalle().getIntFolioInicio());
					setIntFolioFinLibro(libroultimo.getLibroContableDetalle().getIntFolioFin());
				}
				//VERIFICAMOS SI ES EL ULTIMO REGISTRO DE UNA LEGALIZACION ASOCIADA A UN DETALLE
				if (libroDetaComp.getLibroContableDetalle().getIntFolioFin().equals(getIntFolioInicioLibro()) /* && libroContableDetalleOld.getIntFolioFin().equals(getIntFolioFinLibro())*/ ){
					setBtnEliminar(true);
				}			
			}
			
		} catch (Exception e) {
			log.error("Error en seleccionarLibroContable --> "+e);
		}
	}
	
	/**
	 * Autor: Javier Bermúdez T.
	 * Funcionalidad: Carga todos los campos que se desea modificar segun las necesidades del caso
	 * @autor Javier Bermúdez T.
	 * @version 1.0
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 */
	public void modificarValoresLegal(){
		try {
			log.info("----modificarValoresLegal-----");
			setIsEnlazarNulo(false);
			libroLegalizacion = new LibroLegalizacion();

			setLibroLegalizacion(getLibroLegalizacionComp().getLibroLegalizacion()); 
			setStrNotariaPublica(getLibroLegalizacionComp().getStrJuriRazonSocial() + " - Ruc : " + getLibroLegalizacionComp().getStrPersRuc());

			//obteniendo el nombre del archivo adjunto
			ArchivoId nombreArchivo = null;
			nombreArchivo = new ArchivoId();
			nombreArchivo.setIntParaTipoCod(getLibroLegalizacionComp().getLibroLegalizacion().getIntParaTipo());
			nombreArchivo.setIntItemArchivo(getLibroLegalizacionComp().getLibroLegalizacion().getIntItemArchivo());
			nombreArchivo.setIntItemHistorico(getLibroLegalizacionComp().getLibroLegalizacion().getIntItemHistorico());

			archivoAdjuntoCertificadoLegalizacion = null;
			archivoAdjuntoCertificadoLegalizacion = archivoFacade.getArchivoPorPK(nombreArchivo);
		} catch (BusinessException e) {
			log.info("Error : " + e);
		}
		
	}

	/**
	 * Autor: Javier Bermúdez T.
	 * Funcionalidad: Carga todos los campos que se desea modificar segun las necesidades del caso
	 * @autor Javier Bermúdez T.
	 * @version 1.0
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 */
	public void modificarValoresLibro(){
		try{
			setIsStrPkLibroLegalizacion(true);
			setIsAdjunto(true);
			libroContableDetalle = new LibroContableDetalle();

			setLibroContableDetalle(getLibroContableDetalleComp().getLibroContableDetalle());

			getLibroContableDetalleComp().getLibroContableDetalle().getIntPeriodo();

			//obteniendo el nombre del archivo adjunto
			ArchivoId nombreArchivo = null;
			nombreArchivo = new ArchivoId();
			nombreArchivo.setIntParaTipoCod(getLibroContableDetalle().getIntTipo());
			nombreArchivo.setIntItemArchivo(getLibroContableDetalle().getIntItemArchivo());
			nombreArchivo.setIntItemHistorico(getLibroContableDetalle().getIntItemHistorico());
	
			archivoAdjuntoDocumentoPDT = null;
			archivoAdjuntoDocumentoPDT = archivoFacade.getArchivoPorPK(nombreArchivo);
			
			listaLegalizacionesSaldo = null;
			listaLegalizacionesSaldo = new ArrayList<LibroLegalizacionComp>();
			
			for(LibroLegalizacionComp librolegal : listaLegalizaciones){
				listaLegalizacionesSaldo.add(librolegal);
				
				if (librolegal.getLibroLegalizacion().getId().getIntPersEmpresa().equals(getLibroContableDetalle().getId().getIntEmpresaPk())
						&& librolegal.getLibroLegalizacion().getId().getIntParaLibroContable().equals(getLibroContableDetalle().getId().getIntLibroContable())){
					if (librolegal.getIntParaTipoLibro().equals(Constante.PARAM_T_LIBROSELECTRONICOS)){
						setIsAdjunto(false);
					}
				}
			}
			
			if (getLibroContableDetalle().getIntItemLibroLegalizacion() != null){
				setStrPkLibroLegalizacion(getLibroContableDetalle().getId().getIntEmpresaPk()+"-"+getLibroContableDetalle().getId().getIntLibroContable()+"-"+getLibroContableDetalle().getIntItemLibroLegalizacion());
			}else{
				setStrPkLibroLegalizacion("0");
			}
		}catch(Exception e){
			log.info("Error : " + e);
		}
	}
	
	public void limpiarFormAgregarLegalizacion(){
		log.info("-------------------------------------Debugging ControlLibroContableController.limpiarFormAgregarLegalizacion-------------------------------------");
		personaSeleccionada = null;
		setStrNotariaPublica(null);
		setArchivoAdjuntoCertificadoLegalizacion(null);
		setStrMsgError("");
	}

	public void nuevoControlLibro(){
		log.info("-------------------------------------Debugging ControlLibroContableController.nuevoControlLibro-------------------------------------");
		setBlnShowDivLegalizacion(true);
		listaLegalizaciones = new ArrayList<LibroLegalizacionComp>();
		listaLegalizaciones = new ArrayList<LibroLegalizacionComp>();
		listaLibroContableDetalle = new ArrayList<LibroContableDetalleComp>();
		listaUltimaLegalizacion = new ArrayList<LibroContableDetalleComp>();

		setIntParaLibroContable(0);
	}

	public void cancelarControlLibro(){
		log.info("-------------------------------------Debugging ControlLibroContableController.cancelarControlLibro-------------------------------------");
		setBlnShowDivLegalizacion(false);
		listaLegalizaciones = null;
		listaLibroContableDetalleNulo = null;
		listaLibroContableDetalle = null;
		listaUltimaLegalizacion = null;
	}

	public void agregarLegalizacion(){
		log.info("-------------------------------------Debugging ControlLibroContableController.agregarLegalizacion-------------------------------------");
		try{
			if (getIntParaLibroContable() != 0){
				setLibroLegalizacion(new LibroLegalizacion());
				getLibroLegalizacion().setId(new LibroLegalizacionId());
				setDevLibroLegalizacionComp(new LibroLegalizacionComp());
				limpiarFormAgregarLegalizacion();
				setIsEnlazarNulo(true);
				//CARGAR LOS VALORES PARA REGISTRAR LOS NULOS
				listaLibroContableDetalleNulo = legalizacionFacadeLocal.getListaLibroContaDetaNulo(IDEMPRESA_SESION, getIntParaLibroContable());
				setStrPkLibroContableDetalle("0");
			}else{
				FacesContextUtil.setMessageError("Debe seleccionar El Tipo de Libro Contable");
			}
		}catch(BusinessException e){
			log.info("Error :" + e);
		}
	}

	/**
	 * Autor: Javier Bermúdez T.
	 * Funcionalidad: Valida los campos al momento de registrar en la tabla de CON_LIBROLEGALIZACION
	 * @autor Javier Bermúdez T.
	 * @version 1.0
	 * @return en caso no se cumpla las condiciones retorna todos los valores faltantes
	 * @throws BusinessException
	 */	
	public boolean validarGrabarLegalizacion() throws BusinessException {
		setStrMsgError("");
		
		if (getIntParaLibroContable() == 0){
			setStrMsgError("Debe seleccionar un tipo de Libro Contable");
			return false;
		}
		if (libroLegalizacion.getIntPersEmpresaLegal() == null || libroLegalizacion.getIntPersPersona() == null){
			setStrMsgError("Debe seleccionar una Notaria Pública");
			return false;
		}
		if (libroLegalizacion.getDtFechaLegalizacion() == null){
			setStrMsgError("Debe seleccionar la fecha de legalización");
			return false;
		}
		if (libroLegalizacion.getIntNroCertificado() == null){
			setStrMsgError("Debe ingresar el nro. de certificado");
			return false;
		}
		if (libroLegalizacion.getIntFolioInicio() == null){
			setStrMsgError("Debe ingresar el nro. de folio inicial");
			return false;
		}
		if (libroLegalizacion.getIntFolioFin() == null){
			setStrMsgError("Debe ingresar el nro. de folio final");
			return false;
		}
		if (libroLegalizacion.getIntFolioInicio() >= libroLegalizacion.getIntFolioFin()){
			setStrMsgError("El nro. de folio final no puede ser menor ó igual al nro. inicial");
			return false;
		}
		if (libroLegalizacion.getIntParaTipo() == null || libroLegalizacion.getIntItemArchivo() == null || libroLegalizacion.getIntItemHistorico() == null ){
			setStrMsgError("Debe adjuntar el certificado de legalización");
			return false;
		}

		//CUANDO SE ENLAZA CON UN LIBRO CONTABLE DETALLE
		if (getStrPkLibroContableDetalle() != null && getStrPkLibroContableDetalle().length() > 1){
			//validar que el rango inicial sea igual al item NULO
			String[] split = getStrPkLibroContableDetalle().split("-");
			for(LibroContableDetalleComp librocontadeta : listaLibroContableDetalle){
				if (librocontadeta.getLibroContableDetalle().getId().getIntEmpresaPk().equals(Integer.parseInt(split[0]))
						&& librocontadeta.getLibroContableDetalle().getId().getIntLibroContable().equals(Integer.parseInt(split[1]))
						&& librocontadeta.getLibroContableDetalle().getId().getIntItemLibroContableDet().equals(Integer.parseInt(split[2]))){
					setIntFolioInicio(librocontadeta.getLibroContableDetalle().getIntFolioInicio());
					setIntFolioFin(librocontadeta.getLibroContableDetalle().getIntFolioFin());
					break;
				}
			}
			
			if ( !libroLegalizacion.getIntFolioInicio().equals(getIntFolioInicio()) || !libroLegalizacion.getIntFolioFin().equals(getIntFolioFin()) ){
				setStrMsgError("El rango ingresado no es correcto, rango válido del " + getIntFolioInicio() + " al " + getIntFolioFin());
				return false;
			}
		}else{
			setIntUltimoFolioLegal(legalizacionFacadeLocal.getUltimoFolio(IDEMPRESA_SESION, getIntParaLibroContable()));
			//CUANDO SE REGISTRA UNA LEGALIZACION
			if (libroLegalizacion.getId().getIntItemLibroLegalizacion() == null){
				if (libroLegalizacion.getIntFolioInicio() != (getIntUltimoFolioLegal()+1)){
					setStrMsgError("El número inicial no es válido, debe ser " + (getIntUltimoFolioLegal()+1));
					return false;
				}
			}else{
				//CUANDO SE MODIFICA UNA LEGALIZACION
				//Solo puede modificar el rango final cuando sea igual al último número registrado
				if (!libroLegalizacionOld.getIntFolioFin().equals(getIntUltimoFolioLegal())){
					if (!libroLegalizacion.getIntFolioInicio().equals(libroLegalizacionOld.getIntFolioInicio()) || !libroLegalizacion.getIntFolioFin().equals(libroLegalizacionOld.getIntFolioFin()) ){
						setStrMsgError("No puede modificar el Rango Inicial y Final, debe ser " + libroLegalizacionOld.getIntFolioInicio() + " " + libroLegalizacionOld.getIntFolioFin());
						return false;
					}
				}else{//CUANDO ES EL ULTIMO REGISTRO SI PUEDE MODIFICAR PERO SOLO EL RANGO FINAL
					if (!libroLegalizacion.getIntFolioInicio().equals(libroLegalizacionOld.getIntFolioInicio())){
						setStrMsgError("No puede modificar el Rango Inicial, debe ser " + libroLegalizacionOld.getIntFolioInicio());
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Autor: Javier Bermúdez T.
	 * Funcionalidad: Genera un nuevo registro del proceso de Legalización de Libros Contables
	 * @autor Javier Bermúdez T.
	 * @version 1.0
	 * @return en caso no se cumpla las condiciones retorna todos los valores faltantes
	 * @throws BusinessException
	 * @throws EJBFactoryException 
	 */	
	public void grabarLegalizacion() throws Exception{
		log.info("-------------------------------------Debugging ControlLibroContableController.grabarLegalizacion-------------------------------------");
		try {
			LibroLegalizacion libroLegalizacionNew = null;

			/* Inicio - Auditoria - jbermudez 10.09.2014 */
			ControlLibroContableAuditoria libroAuditoria = new ControlLibroContableAuditoria();
			List<Auditoria> listaAuditoriaLegal = new ArrayList<Auditoria>();
			/* Fin Inicio - Auditoria - jbermudez 10.09.2014 */

			if (!validarGrabarLegalizacion())
				return;

			if (libroLegalizacion.getId().getIntItemLibroLegalizacion() == null){

				libroLegalizacion.getId().setIntPersEmpresa(IDEMPRESA_SESION);
				libroLegalizacion.getId().setIntParaLibroContable(getIntParaLibroContable());

				if (getStrPkLibroContableDetalle().length() > 1){
					String[] split = getStrPkLibroContableDetalle().split("-");
					for(LibroContableDetalleComp librocontadeta : listaLibroContableDetalle){
						if (librocontadeta.getLibroContableDetalle().getId().getIntEmpresaPk().equals(Integer.parseInt(split[0]))
								&& librocontadeta.getLibroContableDetalle().getId().getIntLibroContable().equals(Integer.parseInt(split[1]))
								&& librocontadeta.getLibroContableDetalle().getId().getIntItemLibroContableDet().equals(Integer.parseInt(split[2]))){
							devLibroLegalizacionComp.setLibroContableDetalle(librocontadeta.getLibroContableDetalle());
							break;
						}
					}
				}

				devLibroLegalizacionComp.setLibroLegalizacion(libroLegalizacion);
				//grabar Nuevos Registros
				libroLegalizacionNew = legalizacionFacadeLocal.grabarLibroLegalizacion(devLibroLegalizacionComp);

				/*Guardar en Auditoria*/
				listaAuditoriaLegal = libroAuditoria.generarAuditoriaLegal(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, libroLegalizacionNew, null);
				for(Auditoria auditoriaLegal : listaAuditoriaLegal){
					libroAuditoria.grabarAuditoria(auditoriaLegal);
				}
				/*Fin Guardar en Auditoria*/
				FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
			}else{
				//modificar Actualización de registros
				log.info("-------- MODIFICAR --------");
				libroLegalizacionNew = legalizacionFacadeLocal.modificarLibroLegalizacion(libroLegalizacion);
				/*Guardar en Auditoria*/
				listaAuditoriaLegal = libroAuditoria.generarAuditoriaLegal(Constante.PARAM_T_AUDITORIA_TIPO_UPDATE, libroLegalizacionNew, getLibroLegalizacionOld());
				for(Auditoria auditoriaLegal : listaAuditoriaLegal){
					libroAuditoria.grabarAuditoria(auditoriaLegal);
				}
				/*Fin Guardar en Auditoria*/

				FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_MODIFICAR);
			}

			listadosControlLibroContable(); 
			limpiarFormAgregarLegalizacion(); 
		} catch (Exception e) {
			log.info("Error: "+e.getMessage());
		}
	}
	
	/**
	 * Autor: Javier Bermúdez T.
	 * Funcionalidad:
	 * @autor Javier Bermúdez T.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intContItemReclamoDevolucion
	 * @return Cambia el estado de 1 a 3 no elimina fisicamente de la base de datos   
	 * @throws BusinessException
	 */
	public void eliminarRegistro() throws BusinessException, EJBFactoryException{
		legalizacionFacadeLocal.eliminar(libroLegalizacionComp); 
		listadosControlLibroContable(); 
		FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ELIMINAR);
	}

	public void cancelarLegalizacion(ActionEvent event) throws BusinessException, EJBFactoryException {
		log.info("-------------------------------------Debugging ControlLibroContableController.cancelarLegalizacion-------------------------------------");
		limpiarFormAgregarLegalizacion();
	}

	public void agregarLegalizacionPeriodo(ActionEvent event) throws BusinessException, EJBFactoryException {
		log.info("-------------------------------------Debugging ControlLibroContableController.agregarLegalizacionPeriodo-------------------------------------");
		if (getIntParaLibroContable() != 0){
			setIsStrPkLibroLegalizacion(false);
			setLibroContableDetalle(new LibroContableDetalle());
			getLibroContableDetalle().setId(new LibroContableDetalleId());
			
			listaLegalizacionesSaldo = null;
			listaLegalizacionesSaldo = new ArrayList<LibroLegalizacionComp>();
			
			limpiarFormAgregarLegalizacionPeriodo();
			listadoLibroLegalizacion();
			setIsAdjunto(true);

			for(LibroLegalizacionComp librolegal : listaLegalizaciones){
				if (librolegal.getIntSaldolegalizacion() > 0){
					listaLegalizacionesSaldo.add(librolegal);
				}
				
				if (librolegal.getLibroLegalizacion().getId().getIntPersEmpresa().equals(IDEMPRESA_SESION)
						&& librolegal.getLibroLegalizacion().getId().getIntParaLibroContable().equals(getIntParaLibroContable())){
					if (librolegal.getIntParaTipoLibro().equals(Constante.PARAM_T_LIBROSELECTRONICOS)){
						setIsAdjunto(false);
					}
				}
			}
		}else{
			FacesContextUtil.setMessageError("Debe seleccionar El Tipo de Libro Contable");
		}
	}

	/**
	 * Autor: Javier Bermúdez T.
	 * Funcionalidad: Valida los campos al momento de registrar en la tabla de CON_LIBROCONTABLEDETALLE
	 * @autor Javier Bermúdez T.
	 * @version 1.0
	 * @return en caso no se cumpla las condiciones retorna todos los valores faltantes
	 * @throws BusinessException
	 */	
	public boolean validarGrabarLegalizacionPeriodo() throws BusinessException, EJBFactoryException {
		setStrMsgError("");

		if (getIntParaLibroContable() == 0){
			setStrMsgError("Debe seleccionar un tipo de Libro Contable");
			return false;
		}
		if (libroContableDetalle.getIntFolioInicio() == null){
			setStrMsgError("Debe ingresar el nro. de folio inicial");
			return false;
		}
		if (libroContableDetalle.getIntFolioFin() == null){
			setStrMsgError("Debe ingresar el nro. de folio final");
			return false;
		}
		if (libroContableDetalle.getIntFolioInicio() >= libroContableDetalle.getIntFolioFin()){
			setStrMsgError("El nro. de folio final no puede ser menor ó igual al nro. inicial");
			return false;
		}
		if (getIsAdjunto()){
			if (libroContableDetalle.getIntTipo()== null || libroContableDetalle.getIntItemArchivo() == null || libroContableDetalle.getIntItemHistorico() == null ){
				setStrMsgError("Debe adjuntar la Declaración del PDT");
				return false;
			}
		}
		if (libroContableDetalle.getId().getIntItemLibroContableDet() == null && getStrPkLibroLegalizacion().length() == 1){//CUANDO SE AGREGA UN DETALLE SIN UNA LEGALIZACION ASOCIADA
			setIntUltimoFolioLegal(legalizacionFacadeLocal.getUltimoFolio(libroContableDetalle.getId().getIntEmpresaPk(), libroContableDetalle.getId().getIntLibroContable()));
			if (libroContableDetalle.getIntFolioInicio() != (getIntUltimoFolioLegal()+1)){
				setStrMsgError("El número inicial no es válido, debe ser " + (getIntUltimoFolioLegal()+1));
				return false;
			}
		}

		if (libroContableDetalle.getId().getIntItemLibroContableDet() == null){//CUANDO ES UN NUEVO REGISTRO DE LIBRO CONTABLE DETALLE
			if (getStrPkLibroLegalizacion().length() > 1){
				//PARA RECUPERAR EL FOLIO DE INICIO Y FINAL DEL LIBRO DE LEGALIZACION SELECCIONADO
				listaUltimaLegalizacion = legalizacionFacadeLocal.getListaUltimoFolioDetalle(libroContableDetalle.getId().getIntEmpresaPk(), libroContableDetalle.getId().getIntLibroContable(), libroContableDetalle.getIntItemLibroLegalizacion());
				for(LibroContableDetalleComp libroultimo : listaUltimaLegalizacion){
					setIntFolioInicioLibro(libroultimo.getLibroContableDetalle().getIntFolioInicio());
					setIntFolioFinLibro(libroultimo.getLibroContableDetalle().getIntFolioFin());
				}
	
				if (libroContableDetalle.getIntFolioInicio() != (getIntFolioInicioLibro()+1)){
					setStrMsgError("El número inicial no es válido, debe ser " + (getIntFolioInicioLibro()+1));
					return false;
				}
				if (libroContableDetalle.getIntFolioFin() > getIntFolioFinLibro()){
					setStrMsgError("El número final no es válido, debe ser menor igual a " + getIntFolioFinLibro());
					return false;
				}
			}
		}else{
			//CUANDO SE MODIFICA UN LIBRO CONTABLE DETALLE
			if (libroContableDetalle.getIntItemLibroLegalizacion() == null){
				setIntUltimoFolioLegal(legalizacionFacadeLocal.getUltimoFolio(libroContableDetalle.getId().getIntEmpresaPk(), libroContableDetalle.getId().getIntLibroContable()));
				if (libroContableDetalleOld.getIntFolioFin().equals(getIntUltimoFolioLegal())){//SI ES EL ULTIMO REGISTRO, NO PUEDE MODIFICAR EL FOLIO INICIAL
					if (!libroContableDetalleOld.getIntFolioInicio().equals(libroContableDetalle.getIntFolioInicio())){
						setStrMsgError("El núnero inicial no puede modificar, debe ser igual a " + libroContableDetalleOld.getIntFolioInicio());
						return false;
					}
				}else{
					if (!libroContableDetalleOld.getIntFolioInicio().equals(libroContableDetalle.getIntFolioInicio()) || !libroContableDetalleOld.getIntFolioFin().equals(libroContableDetalle.getIntFolioFin())){
						setStrMsgError("No puede modificar los numeros de Folio, debe ser igual a Inicio : " + libroContableDetalleOld.getIntFolioInicio() + " Fin : " + libroContableDetalleOld.getIntFolioFin());
						return false;
					}
				}
			}else{// SE ENCUENTRA ENLAZADO A UNA LEGALIZACION
				//PARA RECUPERAR EL FOLIO DE INICIO Y FINAL DEL LIBRO DE LEGALIZACION SELECCIONADO
				listaUltimaLegalizacion = legalizacionFacadeLocal.getListaUltimoFolioDetalle(libroContableDetalle.getId().getIntEmpresaPk(), libroContableDetalle.getId().getIntLibroContable(), libroContableDetalle.getIntItemLibroLegalizacion());
				for(LibroContableDetalleComp libroultimo : listaUltimaLegalizacion){
					setIntFolioInicioLibro(libroultimo.getLibroContableDetalle().getIntFolioInicio());
					setIntFolioFinLibro(libroultimo.getLibroContableDetalle().getIntFolioFin());
				}
				//VERIFICAMOS SI ES EL ULTIMO REGISTRO DE UNA LEGALIZACION ASOCIADA A UN DETALLE
				if (libroContableDetalleOld.getIntFolioFin().equals(getIntFolioInicioLibro()) /* && libroContableDetalleOld.getIntFolioFin().equals(getIntFolioFinLibro())*/ ){
					if (!libroContableDetalle.getIntFolioInicio().equals(libroContableDetalleOld.getIntFolioInicio())){
						setStrMsgError("El número inicial no puede modificar, debe ser " + libroContableDetalleOld.getIntFolioInicio());
						return false;
					}
					if (libroContableDetalle.getIntFolioFin() > getIntFolioFinLibro()){
						setStrMsgError("El número final no es válido, debe ser menor igual a " + getIntFolioFinLibro());
						return false;
					}
				}else{
					if (!libroContableDetalleOld.getIntFolioInicio().equals(libroContableDetalle.getIntFolioInicio()) || !libroContableDetalleOld.getIntFolioFin().equals(libroContableDetalle.getIntFolioFin())){
						setStrMsgError("No puede modificar los numeros de Folio, debe ser igual a Inicio : " + libroContableDetalleOld.getIntFolioInicio() + " Fin : " + libroContableDetalleOld.getIntFolioFin());
						return false;
					}
				}
			}
		}
		
		return true;
	}	
	public void grabarLegalizacionPeriodo(ActionEvent event) throws BusinessException, EJBFactoryException {
		log.info("-------------------------------------Debugging ControlLibroContableController.grabarLegalizacionPeriodo-------------------------------------");
		try {
			LibroContableDetalle libroContableDetalleNew = null;
			
			/* Inicio - Auditoria - jbermudez 10.09.2014 */
			ControlLibroContableAuditoria libroAuditoria = new ControlLibroContableAuditoria();
			List<Auditoria> listaAuditoriaLibro = new ArrayList<Auditoria>();
			/* Fin Inicio - Auditoria - jbermudez 10.09.2014 */

			
			if (libroContableDetalle.getId() == null || libroContableDetalle.getId().getIntEmpresaPk() == null || libroContableDetalle.getId().getIntLibroContable() == null || libroContableDetalle.getId().getIntItemLibroContableDet() == null){
				
				if (getStrPkLibroLegalizacion().equals("0")){
					libroContableDetalle.getId().setIntEmpresaPk(IDEMPRESA_SESION);
					libroContableDetalle.getId().setIntLibroContable(getIntParaLibroContable());
					libroContableDetalle.setIntItemLibroLegalizacion(null);
				}else{
					String[] split = getStrPkLibroLegalizacion().split("-");
					libroContableDetalle.getId().setIntEmpresaPk(Integer.parseInt(split[0]));
					libroContableDetalle.getId().setIntLibroContable(Integer.parseInt(split[1]));
					libroContableDetalle.setIntItemLibroLegalizacion(Integer.parseInt(split[2]));
				}
				
				if (!validarGrabarLegalizacionPeriodo())
					return;
	
				libroContableDetalle.setIntPeriodo(Integer.parseInt((getIntAnio().toString()+String.format("%2s", getIntMes().toString()).replace(' ', '0'))));
				
				libroContableDetalleNew = legalizacionFacadeLocal.grabarLibroContableDetalle(libroContableDetalle);
				/*Guardar en Auditoria*/
				listaAuditoriaLibro = libroAuditoria.generarAuditoriaLibro(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, libroContableDetalleNew, null);
				for(Auditoria auditoriaLibro : listaAuditoriaLibro){
					libroAuditoria.grabarAuditoria(auditoriaLibro);
				}
				/*Fin Guardar en Auditoria*/
				
				listadosControlLibroContable(); 
				limpiarFormAgregarLegalizacionPeriodo();
				FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
			}else{
				//modificar Actualización de registros
				log.info("-------- MODIFICAR --------");
				if (!validarGrabarLegalizacionPeriodo())
					return;
				
				libroContableDetalleNew = legalizacionFacadeLocal.modificarLibroContableDetalle(libroContableDetalle);
				/*Guardar en Auditoria*/
				listaAuditoriaLibro = libroAuditoria.generarAuditoriaLibro(Constante.PARAM_T_AUDITORIA_TIPO_UPDATE, libroContableDetalleNew, getLibroContableDetalleOld());
				for(Auditoria auditoriaLibro : listaAuditoriaLibro){
					libroAuditoria.grabarAuditoria(auditoriaLibro);
				}
				/*Fin Guardar en Auditoria*/
				listadosControlLibroContable(); 
				limpiarFormAgregarLegalizacionPeriodo();
				FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_MODIFICAR);
			}
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
			log.error(e);
		}
	}

	/**
	 * Autor: Javier Bermúdez T.
	 * Funcionalidad: Elimina fisicamente de la base de datos, y manda a la auditoria   
	 * @autor Javier Bermúdez T.
	 * @version 1.0
	 * @param libroContableDetalleComp 
	 * @return 
	 * @throws BusinessException
	 */
	public void eliminarRegistroLibro() throws BusinessException, EJBFactoryException{
		legalizacionFacadeLocal.eliminar(libroContableDetalleComp); 
		listadosControlLibroContable(); 
		FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ELIMINAR); 
	}

	public void cancelarLegalizacionPeriodo(ActionEvent event) throws BusinessException, EJBFactoryException {
		log.info("-------------------------------------Debugging ControlLibroContableController.cancelarLegalizacionPeriodo-------------------------------------");
		limpiarFormAgregarLegalizacionPeriodo();
	}

	public void limpiarFormAgregarLegalizacionPeriodo() {
		log.info("-------------------------------------Debugging ModeloController.cleanBeanModelo-------------------------------------");
		setArchivoAdjuntoDocumentoPDT(null);
		setStrMsgError("");
		setStrPkLibroLegalizacion(null);
	}

	public List<SelectItem> getListYears() {
		List<SelectItem> listYearsTemp = new ArrayList<SelectItem>();
		try {
			for (int i = -1; i <= 1; i++) {
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR) + i;
				listYearsTemp.add(i + 1, new SelectItem(year));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		this.listYears = listYearsTemp;

		return listYears;
	}
	
	
	public void aceptarAdjuntarCertificadoLegalizacion(){
		try{
			FileUploadControllerContabilidad fileUploadControllerServicio = (FileUploadControllerContabilidad)getSessionBean("fileUploadControllerContabilidad");
			archivoAdjuntoCertificadoLegalizacion = fileUploadControllerServicio.getArchivoCertificadoLeg();

			libroLegalizacion.setIntParaTipo(archivoAdjuntoCertificadoLegalizacion.getId().getIntParaTipoCod());
			libroLegalizacion.setIntItemArchivo(archivoAdjuntoCertificadoLegalizacion.getId().getIntItemArchivo());
			libroLegalizacion.setIntItemHistorico(archivoAdjuntoCertificadoLegalizacion.getId().getIntItemHistorico());

			log.info("Nombre del Archivo recuperado: "+archivoAdjuntoCertificadoLegalizacion.getStrNombrearchivo());
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void quitarAdjuntoCertificadoLegalizacion(){
		try{
			archivoAdjuntoCertificadoLegalizacion = null;
			libroLegalizacion.setIntParaTipo(null);
			libroLegalizacion.setIntItemArchivo(null);
			libroLegalizacion.setIntItemHistorico(null);

			((FileUploadControllerContabilidad)getSessionBean("fileUploadControllerContabilidad")).setArchivoCertificadoLeg(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public void aceptarAdjuntarDocumentoPDT(){
		try{
			FileUploadControllerContabilidad fileUploadControllerServicio = (FileUploadControllerContabilidad)getSessionBean("fileUploadControllerContabilidad");
			archivoAdjuntoDocumentoPDT = fileUploadControllerServicio.getArchivoDeclaracionPDT();

			libroContableDetalle.setIntTipo(archivoAdjuntoDocumentoPDT.getId().getIntParaTipoCod());
			libroContableDetalle.setIntItemArchivo(archivoAdjuntoDocumentoPDT.getId().getIntItemArchivo());
			libroContableDetalle.setIntItemHistorico(archivoAdjuntoDocumentoPDT.getId().getIntItemHistorico());

			log.info("Nombre del Archivo recuperado: "+archivoAdjuntoDocumentoPDT.getStrNombrearchivo());
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}

	public void quitarAdjuntoDocumentoPDT(){
		try{
			archivoAdjuntoDocumentoPDT = null;
			libroContableDetalle.setIntTipo(null);
			libroContableDetalle.setIntItemArchivo(null);
			libroContableDetalle.setIntItemHistorico(null);
			
			((FileUploadControllerContabilidad)getSessionBean("fileUploadControllerContabilidad")).setArchivoDeclaracionPDT(null); 
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}

	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		ControlLibroContableController.log = log;
	}

	public Integer getIDUSUARIO_SESION() {
		return IDUSUARIO_SESION;
	}

	public void setIDUSUARIO_SESION(Integer iDUSUARIO_SESION) {
		IDUSUARIO_SESION = iDUSUARIO_SESION;
	}

	public Integer getIDEMPRESA_SESION() {
		return IDEMPRESA_SESION;
	}

	public void setIDEMPRESA_SESION(Integer iDEMPRESA_SESION) {
		IDEMPRESA_SESION = iDEMPRESA_SESION;
	}

	public LibroContableDetalle getLibroContableDetalle() {
		return libroContableDetalle;
	}

	public void setLibroContableDetalle(LibroContableDetalle libroContableDetalle) {
		this.libroContableDetalle = libroContableDetalle;
	}

	public LibroLegalizacion getLibroLegalizacion() {
		return libroLegalizacion;
	}

	public void setLibroLegalizacion(LibroLegalizacion libroLegalizacion) {
		this.libroLegalizacion = libroLegalizacion;
	}

	public LibroLegalizacionComp getLibroLegalizacionComp() {
		return libroLegalizacionComp;
	}

	public void setLibroLegalizacionComp(LibroLegalizacionComp libroLegalizacionComp) {
		this.libroLegalizacionComp = libroLegalizacionComp;
	}

	public List<LibroLegalizacionComp> getListaPersonaJuridica() {
		return listaPersonaJuridica;
	}

	public void setListaPersonaJuridica(
			List<LibroLegalizacionComp> listaPersonaJuridica) {
		this.listaPersonaJuridica = listaPersonaJuridica;
	}

	public LibroLegalizacionComp getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(LibroLegalizacionComp personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public Boolean getBlnShowDivLegalizacion() {
		return blnShowDivLegalizacion;
	}

	public void setBlnShowDivLegalizacion(Boolean blnShowDivLegalizacion) {
		this.blnShowDivLegalizacion = blnShowDivLegalizacion;
	}

	public Integer getIntAnio() {
		return intAnio;
	}

	public void setIntAnio(Integer intAnio) {
		this.intAnio = intAnio;
	}

	public Integer getIntMes() {
		return intMes;
	}

	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}

	public String getStrArchivoPDT() {
		return strArchivoPDT;
	}

	public void setStrArchivoPDT(String strArchivoPDT) {
		this.strArchivoPDT = strArchivoPDT;
	}

	public String getStrNotariaPublica() {
		return strNotariaPublica;
	}

	public void setStrNotariaPublica(String strNotariaPublica) {
		this.strNotariaPublica = strNotariaPublica;
	}

	public Integer getIntParaLibroContable() {
		return intParaLibroContable;
	}

	public void setIntParaLibroContable(Integer intParaLibroContable) {
		this.intParaLibroContable = intParaLibroContable;
	}

	public String getStrFiltroTextoRazon() {
		return strFiltroTextoRazon;
	}

	public void setStrFiltroTextoRazon(String strFiltroTextoRazon) {
		this.strFiltroTextoRazon = strFiltroTextoRazon;
	}

	public String getStrFiltroTextoRuc() {
		return strFiltroTextoRuc;
	}

	public void setStrFiltroTextoRuc(String strFiltroTextoRuc) {
		this.strFiltroTextoRuc = strFiltroTextoRuc;
	}

	public List<LibroLegalizacionComp> getListaLegalizaciones() {
		return listaLegalizaciones;
	}

	public void setListaLegalizaciones(
			List<LibroLegalizacionComp> listaLegalizaciones) {
		this.listaLegalizaciones = listaLegalizaciones;
	}

	public Archivo getArchivoAdjuntoCertificadoLegalizacion() {
		return archivoAdjuntoCertificadoLegalizacion;
	}

	public void setArchivoAdjuntoCertificadoLegalizacion(
			Archivo archivoAdjuntoCertificadoLegalizacion) {
		this.archivoAdjuntoCertificadoLegalizacion = archivoAdjuntoCertificadoLegalizacion;
	}

	public Archivo getArchivoAdjuntoDocumentoPDT() {
		return archivoAdjuntoDocumentoPDT;
	}

	public void setArchivoAdjuntoDocumentoPDT(Archivo archivoAdjuntoDocumentoPDT) {
		this.archivoAdjuntoDocumentoPDT = archivoAdjuntoDocumentoPDT;
	}

	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}

	public List<LibroContableDetalleComp> getListaLibroContableDetalle() {
		return listaLibroContableDetalle;
	}

	public void setListaLibroContableDetalle(
			List<LibroContableDetalleComp> listaLibroContableDetalle) {
		this.listaLibroContableDetalle = listaLibroContableDetalle;
	}

	public String getStrMsgError() {
		return strMsgError;
	}

	public void setStrMsgError(String strMsgError) {
		this.strMsgError = strMsgError;
	}

	public Integer getIntError() {
		return intError;
	}

	public void setIntError(Integer intError) {
		this.intError = intError;
	}

	public LegalizacionFacadeLocal getLegalizacionFacadeLocal() {
		return legalizacionFacadeLocal;
	}

	public void setLegalizacionFacadeLocal(
			LegalizacionFacadeLocal legalizacionFacadeLocal) {
		this.legalizacionFacadeLocal = legalizacionFacadeLocal;
	}

	public Integer getIntUltimoFolioLegal() {
		return intUltimoFolioLegal;
	}

	public void setIntUltimoFolioLegal(Integer intUltimoFolioLegal) {
		this.intUltimoFolioLegal = intUltimoFolioLegal;
	}

	public String getStrPkLibroLegalizacion() {
		return strPkLibroLegalizacion;
	}

	public void setStrPkLibroLegalizacion(String strPkLibroLegalizacion) {
		this.strPkLibroLegalizacion = strPkLibroLegalizacion;
	}

	public Integer getIntFolioInicio() {
		return intFolioInicio;
	}

	public void setIntFolioInicio(Integer intFolioInicio) {
		this.intFolioInicio = intFolioInicio;
	}

	public Integer getIntFolioFin() {
		return intFolioFin;
	}

	public void setIntFolioFin(Integer intFolioFin) {
		this.intFolioFin = intFolioFin;
	}

	public Integer getIntFolioInicioLibro() {
		return intFolioInicioLibro;
	}

	public void setIntFolioInicioLibro(Integer intFolioInicioLibro) {
		this.intFolioInicioLibro = intFolioInicioLibro;
	}

	public Integer getIntFolioFinLibro() {
		return intFolioFinLibro;
	}

	public void setIntFolioFinLibro(Integer intFolioFinLibro) {
		this.intFolioFinLibro = intFolioFinLibro;
	}

	public Boolean getIsAdjunto() {
		return isAdjunto;
	}

	public void setIsAdjunto(Boolean isAdjunto) {
		this.isAdjunto = isAdjunto;
	}

	public List<LibroLegalizacionComp> getListaLibrosLegalizaciones() {
		return listaLibrosLegalizaciones;
	}

	public void setListaLibrosLegalizaciones(
			List<LibroLegalizacionComp> listaLibrosLegalizaciones) {
		this.listaLibrosLegalizaciones = listaLibrosLegalizaciones;
	}

	public Integer getIntBuscaParaLibroContable() {
		return intBuscaParaLibroContable;
	}

	public void setIntBuscaParaLibroContable(Integer intBuscaParaLibroContable) {
		this.intBuscaParaLibroContable = intBuscaParaLibroContable;
	}

	public List<LibroContableDetalleComp> getListaUltimaLegalizacion() {
		return listaUltimaLegalizacion;
	}

	public void setListaUltimaLegalizacion(
			List<LibroContableDetalleComp> listaUltimaLegalizacion) {
		this.listaUltimaLegalizacion = listaUltimaLegalizacion;
	}

	public boolean isBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(boolean btnModificar) {
		this.btnModificar = btnModificar;
	}

	public boolean isBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(boolean btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public boolean isBtnDescargar() {
		return btnDescargar;
	}

	public void setBtnDescargar(boolean btnDescargar) {
		this.btnDescargar = btnDescargar;
	}

	public GeneralFacadeRemote getArchivoFacade() {
		return archivoFacade;
	}

	public void setArchivoFacade(GeneralFacadeRemote archivoFacade) {
		this.archivoFacade = archivoFacade;
	}

	public List<LibroContableDetalleComp> getListaLibroContableDetalleNulo() {
		return listaLibroContableDetalleNulo;
	}

	public void setListaLibroContableDetalleNulo(
			List<LibroContableDetalleComp> listaLibroContableDetalleNulo) {
		this.listaLibroContableDetalleNulo = listaLibroContableDetalleNulo;
	}

	public Boolean getIsEnlazarNulo() {
		return isEnlazarNulo;
	}

	public void setIsEnlazarNulo(Boolean isEnlazarNulo) {
		this.isEnlazarNulo = isEnlazarNulo;
	}

	public String getStrPkLibroContableDetalle() {
		return strPkLibroContableDetalle;
	}

	public void setStrPkLibroContableDetalle(String strPkLibroContableDetalle) {
		this.strPkLibroContableDetalle = strPkLibroContableDetalle;
	}

	public LibroLegalizacionComp getDevLibroLegalizacionComp() {
		return devLibroLegalizacionComp;
	}

	public void setDevLibroLegalizacionComp(
			LibroLegalizacionComp devLibroLegalizacionComp) {
		this.devLibroLegalizacionComp = devLibroLegalizacionComp;
	}

	public LibroContableDetalleComp getLibroContableDetalleComp() {
		return libroContableDetalleComp;
	}

	public void setLibroContableDetalleComp(
			LibroContableDetalleComp libroContableDetalleComp) {
		this.libroContableDetalleComp = libroContableDetalleComp;
	}

	public List<LibroLegalizacionComp> getListaLegalizacionesSaldo() {
		return listaLegalizacionesSaldo;
	}

	public void setListaLegalizacionesSaldo(
			List<LibroLegalizacionComp> listaLegalizacionesSaldo) {
		this.listaLegalizacionesSaldo = listaLegalizacionesSaldo;
	}

	public Boolean getIsStrPkLibroLegalizacion() {
		return isStrPkLibroLegalizacion;
	}

	public void setIsStrPkLibroLegalizacion(Boolean isStrPkLibroLegalizacion) {
		this.isStrPkLibroLegalizacion = isStrPkLibroLegalizacion;
	}

	public LibroLegalizacion getLibroLegalizacionOld() {
		return libroLegalizacionOld;
	}

	public void setLibroLegalizacionOld(LibroLegalizacion libroLegalizacionOld) {
		this.libroLegalizacionOld = libroLegalizacionOld;
	}

	public LibroContableDetalle getLibroContableDetalleOld() {
		return libroContableDetalleOld;
	}

	public void setLibroContableDetalleOld(
			LibroContableDetalle libroContableDetalleOld) {
		this.libroContableDetalleOld = libroContableDetalleOld;
	}
}