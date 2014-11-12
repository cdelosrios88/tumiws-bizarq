package pe.comtumi.contabilidad.reclamosDevoluciones.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import pe.com.tumi.contabilidad.impuesto.domain.Impuesto;
import pe.com.tumi.contabilidad.impuesto.facade.ImpuestoFacadeLocal;
import pe.com.tumi.contabilidad.reclamosDevoluciones.domain.ReclamosDevoluciones;
import pe.com.tumi.contabilidad.reclamosDevoluciones.domain.ReclamosDevolucionesId;
import pe.com.tumi.contabilidad.reclamosDevoluciones.facade.ReclamosDevolucionesFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class ReclamosDevolucionesController {
//	protected 	static Logger 	log;
	protected static Logger log = Logger.getLogger(ReclamosDevolucionesController.class);
	private ReclamosDevoluciones reclamoDev;
	private ReclamosDevoluciones reclamoBusq;
	private Impuesto impuesto;
	
	private List<Tabla> listActivo;
	
	private List<Tabla> listTipoDevolucion;
	private List<Tabla> listTipoReclamo;
	private List<Tabla> listTabla;
	private List<Tabla> listMoneda;
	private List<Tabla> listaAnios;
	private List<Tabla> listActivoCab;
	private List<Tabla> listPago;
	private final int cantidadAñosLista = 4;
	private List<ReclamosDevoluciones> listParametro;
	//Lista para la busqueda
	private List<ReclamosDevoluciones> listBuscar;
	private List<Impuesto> listaPersona;
	private List<SelectItem> cboFiltroReclamo = null;
	private List<SelectItem> cboFiltroReclamoCab = null;
	private Integer intCboTipoReclamoBusq;
	private Integer intCboFiltroReclamoBusq;
	private Boolean isDisabledTxtReclamoBusq;
	//POPUP BUSCAR PERSONA
	private Integer intCboTipoPersonaBusq;
	private Integer intCboFiltroPersonaBusq;
	private List<SelectItem> cboFiltroPersona = null;
	private String strTxtPersonaBusq;
	private Boolean isDisabledTxtPersonaBusq;
	//SESIÓN
	private Usuario usuarioSesion;
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;
	
	private String daFechaReg;
	private String strEstado;
	private Integer intPeriodo;
	private Integer intMeses;
	private Integer intMoneda;
	private String strGlosa;
	private BigDecimal bdMonto;
	//Datos de la Persona
	private Integer intCodigoPer;
	private String strConcatenado;
	
	
	//c 
	private Boolean blnShowPanelInferior;
	private Boolean blnVisible;
	private Boolean blnVerDatos;
	private Boolean blnModificarDatos;
	//variables pra validar el formulario
	private Boolean isValidTributacion;
	private Boolean blnUpdating;
	
	//Adjuntar Archivos
	private Archivo	archivoAdjuntoReclamoDev;
	private boolean deshabilitarNuevo;
	//variables para la busqueda
	private Integer intEstado;
	private Integer intEstadoPago;
	private Timestamp tsFechaRegistro;
	private Integer intTipoParam;
	private Integer intTipoRecla;
	
	//Validar Botones
	private boolean btnVer;
	private boolean btnModificar;
	private boolean btnEliminar;
	
	private TablaFacadeRemote tablaFacade;
	private GeneralFacadeRemote archivoFacade;
	private ReclamosDevolucionesFacadeLocal reclamoFacade;
	
	public ReclamosDevolucionesController(){
		log = Logger.getLogger(this.getClass());
		cargarUsuario();
		if (usuarioSesion!=null) {
			cargarValoresIniciales();
		}else log.error("--Usuario obtenido es NULL.");		
	}
	//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 10.08.2014 / 
	public String getInicioPage() {
		cargarUsuario();		
		if(usuarioSesion!=null){
			limpiarFomularioReclamo();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}		
		return "";
	}
	
	private void cargarUsuario(){
		usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
	}
	
	public void cargarValoresIniciales(){
		ReclamosDevoluciones recla = new ReclamosDevoluciones();
		recla.getIntEstado();
		 try {
			 archivoFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			 tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			 reclamoFacade = (ReclamosDevolucionesFacadeLocal) EJBFactory.getLocal(ReclamosDevolucionesFacadeLocal.class);
			 blnModificarDatos = false;
			 blnVerDatos = false;
			 
			 btnVer = false;
			 btnModificar = false;
			 btnEliminar = false;
			 blnVisible = Boolean.FALSE;
			 reclamoDev = new ReclamosDevoluciones();
			 reclamoDev.setId(new ReclamosDevolucionesId());
			 
			 reclamoBusq = new ReclamosDevoluciones();
			 
				isValidTributacion = true;
				blnUpdating = false;
				listActivo = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO), "2");
				listMoneda = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOMONEDA), "0");
				setStrEstado(listActivo.get(1).getStrDescripcion());
				listParametro = reclamoFacade.getListaParametro(recla);
				listActivoCab = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO), "-1");
				listPago = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_TIPO_PAGO_RECLAMO_DEVOLUCION));
				listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS));
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
				 Date date = new Date();
				 setDaFechaReg(dateFormat.format(date));
				 
				 listaPersona = new ArrayList<Impuesto>();
				 listBuscar = new ArrayList<ReclamosDevoluciones>();
				 recargarCboFiltroReclamos();
				 recargarCboFiltroPersona();
				 recargarCboFiltroReclamosCab();
				 cargarListaAnios();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaAnios(){
		listaAnios = new ArrayList<Tabla>();
		Calendar cal=Calendar.getInstance();
		Tabla tabla = null;
		for(int i=0;i<cantidadAñosLista;i++){
			tabla = new Tabla();
			int year = cal.get(Calendar.YEAR);
			cal.add(Calendar.YEAR, 1);
			tabla.setIntIdDetalle(year);
			tabla.setStrDescripcion(""+year);
			listaAnios.add(tabla);
		}		
	}
	
	public void limpiarFomularioReclamo(){
		blnShowPanelInferior = false;
		blnVerDatos = false;
		blnUpdating = false;
		blnVisible = Boolean.FALSE;
		blnModificarDatos = false;
		reclamoDev = new ReclamosDevoluciones();
		setIntMeses(null);
		setIntPeriodo(null);
		setStrConcatenado("");
		setArchivoAdjuntoReclamoDev(null);
		listBuscar = new ArrayList<ReclamosDevoluciones>();
		getReclamoBusq().setIntParaEstadoCod(0);
		getReclamoBusq().setIntParaEstadoPago(0);
		getReclamoBusq().setIntParaDocumentoGeneral(0);
		getReclamoBusq().setTsRedeFechaRegistro(null);
		cboFiltroReclamoCab = new ArrayList<SelectItem>();
	}
	
	public void motrarPanel(){
		blnShowPanelInferior = true;
		blnVisible = false;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		 Date date = new Date();
		 setDaFechaReg(dateFormat.format(date));
	}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Genera un nuevo registro y/o modifica un registro existente
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return en caso no se cumpla las condiciones retorna todos los valores faltantes
	 * @throws BusinessException
	 */	
	public void guardarReclamos() throws BusinessException{
		ReclamosDevoluciones reclamo = null;
		if(reclamoDev.getId()==null || reclamoDev.getId().getIntContItemReclamoDevolucion()==null){
			if(!validarReclamo()){
	 			return;
	 	     }
			reclamoDev.setId(new ReclamosDevolucionesId());
			reclamoDev.getId().setIntPersEmpresa(SESION_IDEMPRESA);
			reclamoDev.setIntPersEmpresaAfecto(SESION_IDEMPRESA);
			reclamoDev.setIntPersPersonaAfecto(getIntCodigoPer());
			reclamoDev.setIntPersEmpresaUsuario(SESION_IDEMPRESA);
			reclamoDev.setIntPersPersonaUsuario(SESION_IDUSUARIO);
			reclamoDev.setIntEstado(1);
			if(getIntMeses()==1 || getIntMeses()==2 || getIntMeses()==3 || getIntMeses()==4 || getIntMeses()==5 || getIntMeses()==6 || getIntMeses()==7 || getIntMeses()==8 || getIntMeses()==9){
				String periodo = getIntPeriodo()+"0"+getIntMeses();
				int periodo1 = Integer.parseInt(periodo);
				reclamoDev.setIntRedePlanillaAfectada(periodo1);
			}else{
				String periodo = getIntPeriodo()+""+getIntMeses();
				int periodo1 = Integer.parseInt(periodo);
				reclamoDev.setIntRedePlanillaAfectada(periodo1);
			}
			java.util.Date date= new java.util.Date();
			reclamoDev.setTsRedeFechaRegistro(new Timestamp(date.getTime()));
			reclamoDev.setIntParaEstadoCod(1);
			//DATOS DEL ARCHIVO ADJUNTO
			reclamoDev.setIntParaTipoCod(getArchivoAdjuntoReclamoDev().getId().getIntParaTipoCod());
			reclamoDev.setIntMaeItemArchivo(getArchivoAdjuntoReclamoDev().getId().getIntItemArchivo());
			reclamoDev.setIntMaeItemHistorico(getArchivoAdjuntoReclamoDev().getId().getIntItemHistorico());
			//Datos de los estado del pago
			if(reclamoDev.getIntParaDocumentoGeneral().equals(600)){
				reclamoDev.setIntParaEstadoPago(1);
			}else{
				reclamoDev.setIntParaEstadoCobroCod(1);
			}
			try {
				reclamoFacade.grabarReclamos(reclamoDev);
				if(reclamoDev!=null){
					blnVisible = true;
					FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
				}
			} catch (Exception e) {
				FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
				log.error(e);
			}
			setIntCodigoPer(null);
		}else{
			if(!validarReclamoModificar()){
	 			return;
	 	     }
			if(getIntCodigoPer()!=null){	
				if(getIntCodigoPer()!=reclamoDev.getIntPersPersonaAfecto()){
					reclamoDev.setIntPersPersonaAfecto(getIntCodigoPer());
				}else{
					reclamoDev.getIntPersPersonaAfecto();
				}
			}else{
				reclamoDev.getIntPersPersonaAfecto();
			}
			reclamoDev.setIntParaEstadoCod(1);
		if(getArchivoAdjuntoReclamoDev().getId()!=null){	
			if(getArchivoAdjuntoReclamoDev().getId().getIntItemArchivo()!=reclamoDev.getIntMaeItemArchivo()){
				reclamoDev.setIntMaeItemArchivo(getArchivoAdjuntoReclamoDev().getId().getIntItemArchivo());
			}
		}else{
			reclamoDev.getIntMaeItemArchivo();
		}
		if(getIntMeses()==1 || getIntMeses()==2 || getIntMeses()==3 || getIntMeses()==4 || getIntMeses()==5 || getIntMeses()==6 || getIntMeses()==7 || getIntMeses()==8 || getIntMeses()==9){
			String periodo = getIntPeriodo()+"0"+getIntMeses();
			int periodo1 = Integer.parseInt(periodo);
			reclamoDev.setIntRedePlanillaAfectada(periodo1);
		}else{
			String periodo = getIntPeriodo()+""+getIntMeses();
			int periodo1 = Integer.parseInt(periodo);
			reclamoDev.setIntRedePlanillaAfectada(periodo1);
		}
		/*Validando campos del modificar*/
		/**/
			reclamo = reclamoFacade.modificarReclamos(reclamoDev);
			if(reclamo!=null){
				FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_MODIFICAR);
//				listaBusqImpuesto = new ArrayList<Impuesto>();
			}
			reclamoDev = new ReclamosDevoluciones();
			setIntMeses(null);
			setIntPeriodo(null);
			setStrConcatenado("");
			setIntCodigoPer(null);
			setArchivoAdjuntoReclamoDev(null);
			blnShowPanelInferior = false;
		}
	}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Valida los campos al momento de registrar un nuevo registro.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return en caso no se cumpla las condiciones retorna todos los valores faltantes
	 * @throws BusinessException
	 */	
	public boolean validarReclamo(){
		boolean isValidReclamo = true;
		FacesContextUtil.setMessageError("");
//		if(reclamo!=null){
		if (reclamoDev.getIntParaDocumentoGeneral().equals(0)){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar El Tipo de Documento es un Campo Obligatorio");
		}
		if (reclamoDev.getIntReclamoDevolucion().equals(0)){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar El Tipo de Reclamo (Devolución) es un Campo Obligatorio");
		}
		if (getIntCodigoPer()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar a la Personal Afectado");
		}
		if(reclamoDev.getBdRedeMonto()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe de Ingresar el Monto. El Monto es Obligatorio");
		}
		if(reclamoDev.getIntTipoMoneda().equals(0)){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar El Tipo de Moneda es un Campo Obligatorio");
		}
		if(getIntMeses()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar El Mes es un Campo Obligatorio");
		}
		if(getIntPeriodo().equals(0) || getIntPeriodo()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar El Año es un Campo Obligatorio");
		}
		if(getArchivoAdjuntoReclamoDev()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe de Adjuntar un Sustento de Reclamo");
		}
		if(reclamoDev.getStrRedeGlosa().isEmpty() || reclamoDev.getStrRedeGlosa()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe de Ingresar una Descripción en el Campo es Obligatorio");
		}
		return isValidReclamo;
}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Valida los campos al momento de modificar los datos del registro seleccionado
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @return en caso no se cumpla las condiciones retorna todos los valores faltantes
	 * @throws BusinessException
	 */	
	public boolean validarReclamoModificar(){
		boolean isValidReclamo = true;
		FacesContextUtil.setMessageError("");
//		if(reclamo!=null){
		if (reclamoDev.getIntParaDocumentoGeneral().equals(0)){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar El Tipo de Documento es un Campo Obligatorio");
		}
		if (reclamoDev.getIntReclamoDevolucion().equals(0)){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar El Tipo de Reclamo (Devolución) es un Campo Obligatorio");
		}
		if (reclamoDev.getIntPersPersonaAfecto()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar a la Personal Afectado");
		}
		if(reclamoDev.getBdRedeMonto()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe de Ingresar el Monto. El Monto es Obligatorio");
		}
		if(reclamoDev.getIntTipoMoneda().equals(0)){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar El Tipo de Moneda es un Campo Obligatorio");
		}
		if(getIntMeses()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar El Mes es un Campo Obligatorio");
		}
		if(getIntPeriodo().equals(0) || getIntPeriodo()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe seleccionar El Año es un Campo Obligatorio");
		}
		if(getArchivoAdjuntoReclamoDev()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe de Adjuntar un Sustento de Reclamo");
		}
		if(reclamoDev.getStrRedeGlosa().isEmpty() || reclamoDev.getStrRedeGlosa()==null){
			isValidReclamo = false;
			FacesContextUtil.setMessageError("Debe de Ingresar una Descripción en el Campo es Obligatorio");
		}
		return isValidReclamo;
}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Actualiza Combo
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intContItemReclamoDevolucion
	 * @return retorna valos del combo
	 * @throws BusinessException
	 */
	public void recargarCboFiltroReclamos() throws BusinessException{
		String pIntTipoReclamo = FacesContextUtil.getRequestParameter("pTipoReclamo");
		System.out.println("pIntTipoReclamo: "+pIntTipoReclamo);
		TablaFacadeRemote tablaFacade= null;
		cboFiltroReclamo = new ArrayList<SelectItem>();
		if(pIntTipoReclamo==null){
			return;
		}
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);	
			Integer intTipoReclamo = Integer.valueOf(pIntTipoReclamo);
			
			listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS));
			
			if(listTabla!=null){
				for(Tabla tabla : listTabla){
						if(intTipoReclamo!=null && tabla.getStrIdAgrupamientoB().equals("1")){
							if(intTipoReclamo.equals(600)){
								cboFiltroReclamo.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
							}
						}else if(intTipoReclamo!=null && tabla.getStrIdAgrupamientoB().equals("2")){
							if(intTipoReclamo.equals(620)){
								cboFiltroReclamo.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
							}
						}else{
							cboFiltroReclamo.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
				}
		}
			log.info("liostacombo"+cboFiltroReclamo);
		} catch (Exception e) {
			log.info("debug combo");
		}
	}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Actualiza Combo
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intContItemReclamoDevolucion
	 * @return retorna valos del combo
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 */
	//combo Cabecera
	public void recargarCboFiltroReclamosCab() throws BusinessException{
		String pIntTipoReclamo = FacesContextUtil.getRequestParameter("pTipoReclamoCab");
		System.out.println("pTipoReclamoCab: "+pIntTipoReclamo);
		TablaFacadeRemote tablaFacade= null;
		cboFiltroReclamoCab = new ArrayList<SelectItem>();
		if(pIntTipoReclamo==null){
			return;
		}
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);	
			Integer intTipoReclamo = Integer.valueOf(pIntTipoReclamo);
			
			listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS));
			
			if(listTabla!=null){
				for(Tabla tabla : listTabla){
						if(intTipoReclamo!=null && tabla.getStrIdAgrupamientoB().equals("1")){
							if(intTipoReclamo.equals(600)){
								cboFiltroReclamoCab.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
							}
						}else if(intTipoReclamo!=null && tabla.getStrIdAgrupamientoB().equals("2")){
							if(intTipoReclamo.equals(620)){
								cboFiltroReclamoCab.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
							}
						}else{
							cboFiltroReclamoCab.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
				}
		}
			log.info("liostacombo"+cboFiltroReclamoCab);
		} catch (Exception e) {
			log.info("debug combo");
		}
		
	}
	
	public void disableTxtReclamoBusqCab(){
		log.info("pFiltroReclamoBusq: "+FacesContextUtil.getRequestParameter("pFiltroReclamoBusq"));
		String pFiltroCombo = FacesContextUtil.getRequestParameter("pFiltroReclamoBusq");
		if(pFiltroCombo.equals("0")){
			setIsDisabledTxtReclamoBusq(true);
		}else{
			setIsDisabledTxtReclamoBusq(false);
		}
	}
	
	public void disableTxtReclamoBusq(){
		log.info("pFiltroReclamoBusq: "+FacesContextUtil.getRequestParameter("pFiltroReclamoBusq"));
		String pFiltroCombo = FacesContextUtil.getRequestParameter("pFiltroReclamoBusq");
		if(pFiltroCombo.equals("0")){
			setIsDisabledTxtReclamoBusq(true);
		}else{
			setIsDisabledTxtReclamoBusq(false);
		}
	}
//PERSONA
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Actualiza Combo
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intContItemReclamoDevolucion
	 * @return retorna valos del combo
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 */
	public void reloadCboFiltroPersona(ActionEvent event) throws EJBFactoryException, NumberFormatException, BusinessException{
		recargarCboFiltroPersona();
	}
	
	public void recargarCboFiltroPersona() throws BusinessException{
		String pIntTipoPersona = FacesContextUtil.getRequestParameter("pTipoPersona");
		System.out.println("pIntTipoPersona: "+pIntTipoPersona);
		TablaFacadeRemote tablaFacade= null;
		cboFiltroPersona = new ArrayList<SelectItem>();
		if(pIntTipoPersona==null){
			return;
		}
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);	
			Integer intTipoPersona = Integer.valueOf(pIntTipoPersona);
			
			listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_OPCIONPERSONABUSQUEDA));
			
			if(listTabla!=null){
				for(Tabla tabla : listTabla){
					if(intTipoPersona!=null && intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
						if(tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE) ||
								tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
							cboFiltroPersona.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else if(intTipoPersona!=null && intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
						if(tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE) ||
								tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)){
							cboFiltroPersona.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else{
						cboFiltroPersona.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
					}
				}
			}
			log.info("liostacombo"+cboFiltroPersona);
		} catch (Exception e) {
			log.info("debug combo");
		}
		
	}
	
	public void disableTxtPersonaBusq(ActionEvent event){
		log.info("pFiltroPersonaBusq: "+FacesContextUtil.getRequestParameter("pFiltroPersonaBusq"));
		String pFiltroCombo = FacesContextUtil.getRequestParameter("pFiltroPersonaBusq");
		if(pFiltroCombo.equals("0")){
			setIsDisabledTxtPersonaBusq(true);
		}else{
			setIsDisabledTxtPersonaBusq(false);
		}
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Realiza la busqueda de la persona por:
	 * Valor 1: Nombre y/o Apellido
	 * Valor 2: Numero de DNI
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intContItemReclamoDevolucion
	 * @return retorna una lista con los nombres coincidentes o dni.
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 */
	public List<Impuesto> buscarPersona() throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging HojaManualController.buscarPersona-------------------------------------");
		Impuesto beanPersonaBusq = new Impuesto();
		if(getIntCboTipoPersonaBusq()!=null && !getIntCboTipoPersonaBusq().equals(0)){
			if(getIntCboTipoPersonaBusq().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				if(getIntCboFiltroPersonaBusq()!=null && !getIntCboFiltroPersonaBusq().equals(0)){
					if(getIntCboFiltroPersonaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
						beanPersonaBusq.setIntImpuPeriodo(getIntCboFiltroPersonaBusq());
						beanPersonaBusq.setStrNombreCompleto(getStrTxtPersonaBusq());
					}else if(getIntCboFiltroPersonaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
						beanPersonaBusq.setIntImpuPeriodo(getIntCboFiltroPersonaBusq());
						beanPersonaBusq.setStrNombreCompleto(getStrTxtPersonaBusq());
					}
				}
				ImpuestoFacadeLocal personaFacade = (ImpuestoFacadeLocal)EJBFactory.getLocal(ImpuestoFacadeLocal.class);
				
				listaPersona = personaFacade.getListaNombreDniRol(beanPersonaBusq);
				
			}
		}
//		cleanBeanTributacion();
		return listaPersona;
	}
	
	public void aceptarAdjuntarReclamoDevolucion(){
		try{
			FileUploadControllerContabilidad fileUploadControllerServicio = (FileUploadControllerContabilidad)getSessionBean("fileUploadControllerContabilidad");
			archivoAdjuntoReclamoDev = fileUploadControllerServicio.getArchivoReclamoDev();
			log.info("Nombre del Archivo recuperado: "+archivoAdjuntoReclamoDev.getStrNombrearchivo());
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void quitarAdjuntoReclamo(){
		try{
			archivoAdjuntoReclamoDev = null;
			((FileUploadControllerContabilidad)getSessionBean("fileUploadControllerContabilidad")).setArchivoReclamoDev(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Este evento se activa cada ves que se seleeciona a una persona del popup
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intContItemReclamoDevolucion
	 * @return Muestra el nombre de la persona en el panel principal
	 */
	public void seleccionarPersRect(){
		log.info("-----------------------Debugging HojaManualController.seleccionarCuenta()-----------------------------");
		String strRect = (String) FacesContextUtil.getRequestParameter("pRowRect");
		log.info("strCuenta: "+strRect);
		setImpuesto(listaPersona.get(Integer.valueOf(strRect)));
		setStrConcatenado(getImpuesto().getStrNombreCompleto());//+" -  DNI : "+getImpuesto().getStrNumeroDocumento());
		setIntCodigoPer(getImpuesto().getIntPersPerson());
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Realiza la busqueda en la base de datos con los siguientes valores.
	 * valor 1:pIntDocumentoGeneral
	 * valor 2:pIntReclamoDev
	 * valor 3:ptsFechaRegistro
	 * valor 4:pIntEstadoCod
	 * valor 5:pIntEstadoCobro
	 * valor 6:pIntEstadoPAgo
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intContItemReclamoDevolucion
	 * @return retorna una lista de la base de datos con los datos que deceeemos.
	 * @throws EJBFactoryException
	 */
	public void buscarReclamo() throws EJBFactoryException{
		try {
			if(reclamoBusq.getIntParaDocumentoGeneral().equals(0)){
				reclamoBusq.setIntParaDocumentoGeneral(null);
			}
			if(reclamoBusq.getIntReclamoDevolucion().equals(0)){
				reclamoBusq.setIntReclamoDevolucion(null);
			}
			if(reclamoBusq.getIntParaEstadoCod().equals(0)){
				reclamoBusq.setIntParaEstadoCod(null);
			}
			if(reclamoBusq.getIntParaEstadoPago().equals(0)){
				reclamoBusq.setIntParaEstadoPago(null);
			}
//			String fecha = Constante.sdf.format(reclamoBusq.getTsRedeFechaRegistro());
			listBuscar = reclamoFacade.getBuscar(reclamoBusq);
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			log.error(e);
			e.printStackTrace();
		}
		System.out.println("lista Impuesto: "+listBuscar);
		if(listBuscar!=null)System.out.println("Lista Impuesto: "+listBuscar.size());
	}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Este Metodo se activa cuando seleccionamos un registro de la grilla que nos da al momento de buscar.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intContItemReclamoDevolucion
	 * @return retorna todos los valores del registro seleccionado.
	 * @throws BusinessException
	 */
	public void seleccionarReclamo(ActionEvent event){
		btnVer = false;
		btnModificar = false;
		btnEliminar = false;
		
		try {
			reclamoDev = (ReclamosDevoluciones)event.getComponent().getAttributes().get("itemReclamoBusq");
			
		if(reclamoDev.getIntParaEstadoPago()!=null){	
			if(reclamoDev.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) && 
					reclamoDev.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_PENDIENTE)){
				btnVer = true;
				btnModificar = true;
				btnEliminar = true;
				blnVisible = true;
			}else if(reclamoDev.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) &&
					reclamoDev.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)){
				btnVer = true;
				btnModificar = false;
				btnEliminar = false;
				blnVisible = false;
			}else if(reclamoDev.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) &&
					reclamoDev.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_PENDIENTE)){
				btnVer = true;
				btnModificar = false;
				btnEliminar = false;
				blnVisible = false;
			}else if(reclamoDev.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) &&
					reclamoDev.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)){
				btnVer = true;
				btnModificar = false;
				btnEliminar = false;
				blnVisible = false;
			}
		}else{
			if(reclamoDev.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) && 
					reclamoDev.getIntParaEstadoCobroCod().equals(Constante.PARAM_T_ESTADOPAGO_PENDIENTE)){
				btnVer = true;
				btnModificar = true;
				btnEliminar = true;
				blnVisible = true;
			}else if(reclamoDev.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) &&
					reclamoDev.getIntParaEstadoCobroCod().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)){
				btnVer = true;
				btnModificar = false;
				btnEliminar = false;
				blnVisible = false;
			}else if(reclamoDev.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) &&
					reclamoDev.getIntParaEstadoCobroCod().equals(Constante.PARAM_T_ESTADOPAGO_PENDIENTE)){
				btnVer = true;
				btnModificar = false;
				btnEliminar = false;
				blnVisible = false;
			}else if(reclamoDev.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) &&
					reclamoDev.getIntParaEstadoCobroCod().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)){
				btnVer = true;
				btnModificar = false;
				btnEliminar = false;
				blnVisible = false;
			}
		}

			cboFiltroReclamo = new ArrayList<SelectItem>();
			
			
		} catch (Exception e) {
			log.error("Error en seleccionarTributo --> "+e);
	}
}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad: Modifica todos los campos que se desea modificar segun las necesidades del caso
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intContItemReclamoDevolucion
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 */
	public void modificarValores() throws BusinessException, EJBFactoryException{
		blnShowPanelInferior = true;
		blnVisible = false;
		
		ArchivoId nombreArchivo = null;
		
		reclamoDev.getId().setIntPersEmpresa(SESION_IDEMPRESA);
		reclamoDev.setIntPersEmpresaAfecto(SESION_IDEMPRESA);
		setStrConcatenado(reclamoDev.getStrNombreCompleto());
		reclamoDev.setIntPersEmpresaUsuario(SESION_IDEMPRESA);
		
		int periodo = reclamoDev.getIntRedePlanillaAfectada();
		String periodoString = Integer.toString(periodo);
		String año = periodoString.substring (0,4);
		String mes = periodoString.substring (4,6);
		int añoEnt = Integer.parseInt(año);
		setIntPeriodo(añoEnt);
		/*Se obtiene el mes*/
		if(mes.equals("01") || mes.equals("02") || mes.equals("03") || mes.equals("04") || mes.equals("05") || mes.equals("06") || mes.equals("07") || mes.equals("08") || mes.equals("09")){
			String mesDig = mes.substring (1,2);
			int mesEnt = Integer.parseInt(mesDig);
			setIntMeses(mesEnt);
		}else{
			int mesEnt = Integer.parseInt(mes);
			setIntMeses(mesEnt);
		}
		//obteniendo el nombre del archivo adjunto
		nombreArchivo = new ArchivoId();
		nombreArchivo.setIntParaTipoCod(reclamoDev.getIntParaTipoCod());
		nombreArchivo.setIntItemArchivo(reclamoDev.getIntMaeItemArchivo());
		nombreArchivo.setIntItemHistorico(reclamoDev.getIntMaeItemHistorico());
		
		Archivo nombre = archivoFacade.getArchivoPorPK(nombreArchivo);
		
		archivoAdjuntoReclamoDev = new Archivo();
		getArchivoAdjuntoReclamoDev().setStrNombrearchivo(nombre.getStrNombrearchivo());
		
		//REFRESCAR COMBO
		listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS));
		
		if(listTabla!=null){
			for(Tabla tabla : listTabla){
					if(reclamoDev.getIntParaDocumentoGeneral()!=null && tabla.getStrIdAgrupamientoB().equals("1")){
						if(reclamoDev.getIntParaDocumentoGeneral().equals(600)){
							cboFiltroReclamo.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else if(reclamoDev.getIntParaDocumentoGeneral()!=null && tabla.getStrIdAgrupamientoB().equals("2")){
						if(reclamoDev.getIntParaDocumentoGeneral().equals(620)){
							cboFiltroReclamo.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else{
						cboFiltroReclamo.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
					}
			}
		String fechaRegistro = Constante.sdf.format(reclamoDev.getTsRedeFechaRegistro());
		setDaFechaReg(fechaRegistro);
	}
		blnShowPanelInferior = true;
		blnVisible = false;
}
	
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad:Solo nos permite visualizar el registro mas no realizar alguna modificación y/o eliminacion.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intContItemReclamoDevolucion
	 * @return retorna el panel donde solo se podra visualizar los datos del registro   
	 * @throws BusinessException
	 */
	public void verValores() throws BusinessException{
		log.info("lista busqueda impuesto: ");
		blnShowPanelInferior = false;
		blnVisible = false;
		ArchivoId nombreArchivo = null;
		
		try {
			blnShowPanelInferior = true;
			blnVisible = Boolean.TRUE;
			int periodo = reclamoDev.getIntRedePlanillaAfectada();
			String periodoString = Integer.toString(periodo);
			String año = periodoString.substring (0,4);
			String mes = periodoString.substring (4,6);
			int añoEnt = Integer.parseInt(año);
			setIntPeriodo(añoEnt);
			/*Se obtiene el mes*/
			if(mes.equals("01") || mes.equals("02") || mes.equals("03") || mes.equals("04") || mes.equals("05") || mes.equals("06") || mes.equals("07") || mes.equals("08") || mes.equals("09")){
				String mesDig = mes.substring (1,2);
				int mesEnt = Integer.parseInt(mesDig);
				setIntMeses(mesEnt);
			}else{
				int mesEnt = Integer.parseInt(mes);
				setIntMeses(mesEnt);
			}
			setStrConcatenado(reclamoDev.getStrNombreCompleto());
			//obteniendo el nombre del archivo adjunto
			nombreArchivo = new ArchivoId();
			nombreArchivo.setIntParaTipoCod(reclamoDev.getIntParaTipoCod());
			nombreArchivo.setIntItemArchivo(reclamoDev.getIntMaeItemArchivo());
			nombreArchivo.setIntItemHistorico(reclamoDev.getIntMaeItemHistorico());
			
			Archivo nombre = archivoFacade.getArchivoPorPK(nombreArchivo);
			
			String fechaRegistro = Constante.sdf.format(reclamoDev.getTsRedeFechaRegistro());
			setDaFechaReg(fechaRegistro);
			
			archivoAdjuntoReclamoDev = new Archivo();
			getArchivoAdjuntoReclamoDev().setStrNombrearchivo(nombre.getStrNombrearchivo());
			//REFRESCAR COMBO
			listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS));
			
			if(listTabla!=null){
				for(Tabla tabla : listTabla){
						if(reclamoDev.getIntParaDocumentoGeneral()!=null && tabla.getStrIdAgrupamientoB().equals("1")){
							if(reclamoDev.getIntParaDocumentoGeneral().equals(600)){
								cboFiltroReclamo.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
							}
						}else if(reclamoDev.getIntParaDocumentoGeneral()!=null && tabla.getStrIdAgrupamientoB().equals("2")){
							if(reclamoDev.getIntParaDocumentoGeneral().equals(620)){
								cboFiltroReclamo.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
							}
						}else{
							cboFiltroReclamo.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
				}
		}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad:
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intContItemReclamoDevolucion
	 * @return Cambia el estado de 1 a 3 no elimina fisicamente de la base de datos   
	 * @throws BusinessException
	 */
	
	public void eliminarRegistro() throws BusinessException{
		ReclamosDevoluciones recla = new ReclamosDevoluciones();
		
		reclamoDev.getId().setIntPersEmpresa(SESION_IDEMPRESA);
		reclamoDev.setIntPersEmpresaAfecto(SESION_IDEMPRESA);
		reclamoDev.setIntPersEmpresaUsuario(SESION_IDEMPRESA);
		reclamoDev.setIntParaEstadoCod(3);
		recla = reclamoFacade.modificarReclamos(reclamoDev);
		if(recla!=null){
			FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ELIMINAR);
		}
	}
	/*limpia popup persona*/
	
	public void limpiar(){
		intCboTipoPersonaBusq = 0;
		cboFiltroPersona = new ArrayList<SelectItem>();
		listaPersona.clear();
		listaPersona = new ArrayList<Impuesto>();
		strTxtPersonaBusq = "";
	}
	
	//Getters y Setters
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
		ReclamosDevolucionesController.log = log;
	}
	
	public Usuario getUsuarioSesion() {
		return usuarioSesion;
	}
	public void setUsuarioSesion(Usuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
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

	public Boolean getBlnShowPanelInferior() {
		return blnShowPanelInferior;
	}

	public void setBlnShowPanelInferior(Boolean blnShowPanelInferior) {
		this.blnShowPanelInferior = blnShowPanelInferior;
	}

	public Boolean getIsValidTributacion() {
		return isValidTributacion;
	}

	public void setIsValidTributacion(Boolean isValidTributacion) {
		this.isValidTributacion = isValidTributacion;
	}

	public Boolean getBlnUpdating() {
		return blnUpdating;
	}

	public void setBlnUpdating(Boolean blnUpdating) {
		this.blnUpdating = blnUpdating;
	}

	public String getDaFechaReg() {
		return daFechaReg;
	}

	public void setDaFechaReg(String daFechaReg) {
		this.daFechaReg = daFechaReg;
	}

	public String getStrEstado() {
		return strEstado;
	}

	public void setStrEstado(String strEstado) {
		this.strEstado = strEstado;
	}
	public List<Tabla> getListActivo() {
		return listActivo;
	}
	public void setListActivo(List<Tabla> listActivo) {
		this.listActivo = listActivo;
	}

	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}
	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}

	public List<Tabla> getListTipoDevolucion() {
		return listTipoDevolucion;
	}
	public void setListTipoDevolucion(List<Tabla> listTipoDevolucion) {
		this.listTipoDevolucion = listTipoDevolucion;
	}
	public List<Tabla> getListTipoReclamo() {
		return listTipoReclamo;
	}
	public void setListTipoReclamo(List<Tabla> listTipoReclamo) {
		this.listTipoReclamo = listTipoReclamo;
	}
	public List<ReclamosDevoluciones> getListParametro() {
		return listParametro;
	}
	public void setListParametro(List<ReclamosDevoluciones> listParametro) {
		this.listParametro = listParametro;
	}
	public ReclamosDevolucionesFacadeLocal getReclamoFacade() {
		return reclamoFacade;
	}
	public void setReclamoFacade(ReclamosDevolucionesFacadeLocal reclamoFacade) {
		this.reclamoFacade = reclamoFacade;
	}

	public List<SelectItem> getCboFiltroReclamo() {
		return cboFiltroReclamo;
	}
	public void setCboFiltroReclamo(List<SelectItem> cboFiltroReclamo) {
		this.cboFiltroReclamo = cboFiltroReclamo;
	}
	public List<Tabla> getListTabla() {
		return listTabla;
	}
	public void setListTabla(List<Tabla> listTabla) {
		this.listTabla = listTabla;
	}
	public Integer getIntCboTipoReclamoBusq() {
		return intCboTipoReclamoBusq;
	}
	public void setIntCboTipoReclamoBusq(Integer intCboTipoReclamoBusq) {
		this.intCboTipoReclamoBusq = intCboTipoReclamoBusq;
	}
	public Integer getIntCboFiltroReclamoBusq() {
		return intCboFiltroReclamoBusq;
	}
	public void setIntCboFiltroReclamoBusq(Integer intCboFiltroReclamoBusq) {
		this.intCboFiltroReclamoBusq = intCboFiltroReclamoBusq;
	}
	public Boolean getIsDisabledTxtReclamoBusq() {
		return isDisabledTxtReclamoBusq;
	}
	public void setIsDisabledTxtReclamoBusq(Boolean isDisabledTxtReclamoBusq) {
		this.isDisabledTxtReclamoBusq = isDisabledTxtReclamoBusq;
	}
	public Integer getIntCboTipoPersonaBusq() {
		return intCboTipoPersonaBusq;
	}
	public void setIntCboTipoPersonaBusq(Integer intCboTipoPersonaBusq) {
		this.intCboTipoPersonaBusq = intCboTipoPersonaBusq;
	}
	public Integer getIntCboFiltroPersonaBusq() {
		return intCboFiltroPersonaBusq;
	}
	public void setIntCboFiltroPersonaBusq(Integer intCboFiltroPersonaBusq) {
		this.intCboFiltroPersonaBusq = intCboFiltroPersonaBusq;
	}
	public List<SelectItem> getCboFiltroPersona() {
		return cboFiltroPersona;
	}
	public void setCboFiltroPersona(List<SelectItem> cboFiltroPersona) {
		this.cboFiltroPersona = cboFiltroPersona;
	}
	public String getStrTxtPersonaBusq() {
		return strTxtPersonaBusq;
	}
	public void setStrTxtPersonaBusq(String strTxtPersonaBusq) {
		this.strTxtPersonaBusq = strTxtPersonaBusq;
	}
	public Boolean getIsDisabledTxtPersonaBusq() {
		return isDisabledTxtPersonaBusq;
	}
	public void setIsDisabledTxtPersonaBusq(Boolean isDisabledTxtPersonaBusq) {
		this.isDisabledTxtPersonaBusq = isDisabledTxtPersonaBusq;
	}
	public List<Tabla> getListaAnios() {
		return listaAnios;
	}
	public void setListaAnios(List<Tabla> listaAnios) {
		this.listaAnios = listaAnios;
	}
	public List<Impuesto> getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(List<Impuesto> listaPersona) {
		this.listaPersona = listaPersona;
	}
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}
	public Integer getIntMeses() {
		return intMeses;
	}
	public void setIntMeses(Integer intMeses) {
		this.intMeses = intMeses;
	}
	public Integer getIntMoneda() {
		return intMoneda;
	}
	public void setIntMoneda(Integer intMoneda) {
		this.intMoneda = intMoneda;
	}
	public int getCantidadAñosLista() {
		return cantidadAñosLista;
	}
	public List<Tabla> getListMoneda() {
		return listMoneda;
	}
	public void setListMoneda(List<Tabla> listMoneda) {
		this.listMoneda = listMoneda;
	}
	public String getStrGlosa() {
		return strGlosa;
	}
	public void setStrGlosa(String strGlosa) {
		this.strGlosa = strGlosa;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public ReclamosDevoluciones getReclamoDev() {
		return reclamoDev;
	}
	public void setReclamoDev(ReclamosDevoluciones reclamoDev) {
		this.reclamoDev = reclamoDev;
	}
	public Archivo getArchivoAdjuntoReclamoDev() {
		return archivoAdjuntoReclamoDev;
	}
	public void setArchivoAdjuntoReclamoDev(Archivo archivoAdjuntoReclamoDev) {
		this.archivoAdjuntoReclamoDev = archivoAdjuntoReclamoDev;
	}
	public boolean isDeshabilitarNuevo() {
		return deshabilitarNuevo;
	}
	public void setDeshabilitarNuevo(boolean deshabilitarNuevo) {
		this.deshabilitarNuevo = deshabilitarNuevo;
	}
	public Impuesto getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}
	public Integer getIntCodigoPer() {
		return intCodigoPer;
	}
	public void setIntCodigoPer(Integer intCodigoPer) {
		this.intCodigoPer = intCodigoPer;
	}
	public String getStrConcatenado() {
		return strConcatenado;
	}
	public void setStrConcatenado(String strConcatenado) {
		this.strConcatenado = strConcatenado;
	}
	public List<Tabla> getListActivoCab() {
		return listActivoCab;
	}
	public void setListActivoCab(List<Tabla> listActivoCab) {
		this.listActivoCab = listActivoCab;
	}
	public List<Tabla> getListPago() {
		return listPago;
	}
	public void setListPago(List<Tabla> listPago) {
		this.listPago = listPago;
	}
	public Integer getIntEstado() {
		return intEstado;
	}
	public void setIntEstado(Integer intEstado) {
		this.intEstado = intEstado;
	}
	public Integer getIntEstadoPago() {
		return intEstadoPago;
	}
	public void setIntEstadoPago(Integer intEstadoPago) {
		this.intEstadoPago = intEstadoPago;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntTipoParam() {
		return intTipoParam;
	}
	public void setIntTipoParam(Integer intTipoParam) {
		this.intTipoParam = intTipoParam;
	}
	public Integer getIntTipoRecla() {
		return intTipoRecla;
	}
	public void setIntTipoRecla(Integer intTipoRecla) {
		this.intTipoRecla = intTipoRecla;
	}
	public List<ReclamosDevoluciones> getListBuscar() {
		return listBuscar;
	}
	public void setListBuscar(List<ReclamosDevoluciones> listBuscar) {
		this.listBuscar = listBuscar;
	}
	public ReclamosDevoluciones getReclamoBusq() {
		return reclamoBusq;
	}
	public void setReclamoBusq(ReclamosDevoluciones reclamoBusq) {
		this.reclamoBusq = reclamoBusq;
	}
	public Boolean getBlnVisible() {
		return blnVisible;
	}
	public void setBlnVisible(Boolean blnVisible) {
		this.blnVisible = blnVisible;
	}
	public Boolean getBlnVerDatos() {
		return blnVerDatos;
	}
	public void setBlnVerDatos(Boolean blnVerDatos) {
		this.blnVerDatos = blnVerDatos;
	}
	public Boolean getBlnModificarDatos() {
		return blnModificarDatos;
	}
	public void setBlnModificarDatos(Boolean blnModificarDatos) {
		this.blnModificarDatos = blnModificarDatos;
	}
	public boolean isBtnVer() {
		return btnVer;
	}
	public void setBtnVer(boolean btnVer) {
		this.btnVer = btnVer;
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
	public GeneralFacadeRemote getArchivoFacade() {
		return archivoFacade;
	}
	public void setArchivoFacade(GeneralFacadeRemote archivoFacade) {
		this.archivoFacade = archivoFacade;
	}
	public List<SelectItem> getCboFiltroReclamoCab() {
		return cboFiltroReclamoCab;
	}
	public void setCboFiltroReclamoCab(List<SelectItem> cboFiltroReclamoCab) {
		this.cboFiltroReclamoCab = cboFiltroReclamoCab;
	}
}
