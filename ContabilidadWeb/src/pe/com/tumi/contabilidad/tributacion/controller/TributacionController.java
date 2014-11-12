package pe.com.tumi.contabilidad.tributacion.controller;

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
import pe.com.tumi.contabilidad.impuesto.domain.Impuesto;
import pe.com.tumi.contabilidad.impuesto.domain.ImpuestoId;
import pe.com.tumi.contabilidad.impuesto.facade.ImpuestoFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;


public class TributacionController {
	protected static Logger log = Logger.getLogger(TributacionController.class);
	private Impuesto impuesto;
//	private Impuesto impuestoRect;
	private Impuesto impuestoTemp;
	private Boolean blnShowTributacion;
	private Boolean blnShowTributacionRectificacion;
	//bloqueados
	private Boolean blnShowTributacionBloqueado;
	private Boolean blnShowTributacionRectificacionBloqueado;
	//variables pra validar el formulario
	private Boolean isValidTributacion;
	private Boolean blnUpdating;
	
	//propiedades que capturan atributos de sesión
	private Integer IDUSUARIO_SESION;
	private Integer IDEMPRESA_SESION;
	
	private Integer intTipoTarifa;
	private Integer intMes;
	private List<Tabla> listaTipoTarifa;
	private List<Tabla> listaMes;
	private List<Tabla> listActivo;
	private List<Tabla> listActivoCab;
	private List<Tabla> listPago;
	private List<Tabla> listTipoRect;
	private List<Tabla> listTipoMoneda;
	private List<Tabla> listTipoPersona;
	private List<Tabla> listTabla;
	private List<Impuesto> listaPersonaJuri;
	private List<Impuesto> listaImpuesto;
	private List<Persona> listPersona;
	private List<Impuesto> lista;
	private List<Tabla> listaAnios;
	private final int cantidadAñosLista = 4;
	private String strEstado;
	private String daFechaReg;
	//POPUP BUSCAR PERSONA
	private Integer intCboTipoPersonaBusq;
	private Integer intCboFiltroPersonaBusq;
	private List<SelectItem> cboFiltroPersona = null;
	private List<SelectItem> cboFiltroPersona1 = null;
	private String strTxtPersonaBusq;
	private Boolean isDisabledTxtPersonaBusq;
	private Integer intCodigoPersona;
	private String strjuriRazonSocial;
	//validadores de los campos
	private String strEntidad;
	private String strTipo;
	private String strPersEncargado;
	private String strImporte;
	private String strTipoMoneda;
	private String strGlosa;
	private Timestamp tsfechas;
	private Integer intPeriodo;
	private Integer intEstadoCodCab;
	private Integer intPago;
	private Integer intCodigoPersonal;
	private String strNombreCompleto;
		
	private TablaFacadeRemote tablaFacade;
	private ImpuestoFacadeLocal impuestoFacade;
	//Autor: jchavez / Tarea: Creación / Fecha: 08.08.2014 / 
	private List<Impuesto> listaBusqImpuesto;
	private List<Impuesto> listaPersonal;
	private Usuario usuarioSesion;
	private Boolean blnShowPanelInferior;
	private Boolean blnVisible;
	private Boolean blnVerDatos;
	private Boolean blnModificarDatos;
	private Impuesto impBusq;
	private Boolean blnModificar;
	private Impuesto impRect;
	private Impuesto impuestoDeLaRectificatoria;
	
	//Autor: jchavez / Tarea: Modificación / Fecha: 08.08.2014 / 
	public TributacionController() throws BusinessException{
		cargarUsuario();
		if(usuarioSesion!=null){
			log.info("Entrando a cargarValoresIniciales()...");
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	//Autor: jchavez / Tarea: Creación / Fecha: 08.08.2014 / 
	public String getInicioPage() {
		cargarUsuario();		
		if(usuarioSesion!=null){
			limpiarFomularioTributacion();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}		
		return "";
	}

	//Autor: jchavez / Tarea: creación / Fecha: 08.08.2014 / 
	private void cargarUsuario(){
		usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		
		IDUSUARIO_SESION = usuarioSesion.getIntPersPersonaPk();
		IDEMPRESA_SESION = usuarioSesion.getPerfil().getId().getIntPersEmpresaPk();
	}
	
	//Autor: jchavez / Tarea: Modificación / Fecha: 08.08.2014 / 
	public void cargarValoresIniciales(){
		
		try {
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			impuestoFacade = (ImpuestoFacadeLocal) EJBFactory.getLocal(ImpuestoFacadeLocal.class);
			
			blnShowTributacion = false;
			blnShowTributacionRectificacion = false;
			blnModificarDatos = false;
			blnVerDatos = false;
			
			impuesto = new Impuesto();
			impuesto.setId(new ImpuestoId());
			isValidTributacion = true;
			blnUpdating = false;
			
			blnShowTributacionBloqueado = false;
			blnShowTributacionRectificacionBloqueado = false;
			listaTipoTarifa = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOTARIFA), "2");
			listTipoMoneda = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOMONEDA), "0");
			listActivo = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO), "2");
				setStrEstado(listActivo.get(1).getStrDescripcion());
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
				 Date date = new Date();
				 setDaFechaReg(dateFormat.format(date));
			listActivoCab = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO), "-1");
			listPago = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPO_PAGO), "-1");
			listTipoRect = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL_CHEQUERAEMPRESA), "0");
			listTipoPersona = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(Integer.valueOf(Constante.PARAM_T_OPCIONPERSONABUSQUEDA_NOMBRE_DNI), "2");
			lista = new ArrayList<Impuesto>();
			//Autor: jchavez / Tarea: Creación / Fecha: 08.08.2014 / 
			listaBusqImpuesto = new ArrayList<Impuesto>();
			listaPersonaJuri = new ArrayList<Impuesto>();
			listaPersonal = new ArrayList<Impuesto>();
			//Autor: jchavez / Tarea: Creación / Fecha: 12.08.2014 / 
			impBusq = new Impuesto();
			blnModificar = false;
			
			recargarCboFiltroPersona();
			recargarCboFiltroPersona1();
			cargarListaAnios();
			log.info("Resultado recargarCboFiltroPersona()..."+cboFiltroPersona);
			log.info("Resultado recargarCboFiltroPersona1()..."+cboFiltroPersona1);
		}catch (Exception e) {
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
	
	public void limpiarFomularioTributacion(){
		impuesto = new Impuesto();
		impuesto.setId(new ImpuestoId());
		listaBusqImpuesto.clear();
		listaPersonaJuri.clear();
		listaPersonal.clear();
		
		blnShowPanelInferior = false;
		blnShowTributacion = false;
		blnShowTributacionRectificacion = false;
		blnModificarDatos = false;
		blnVerDatos = false;
		
		blnUpdating = false;		
		blnVisible = true;
	}
	
//	public void searchImpuesto() throws BusinessException{
//		busquedaImpuesto();
//	}
	
	public void busquedaImpuesto() throws BusinessException{
		List<Impuesto> listaBusqImpuestoTemp = new ArrayList<Impuesto>();
		listaBusqImpuesto.clear();
		try {
			if (impBusq.getIntMeses()==-1) {
				impBusq.setIntImpuPeriodo(null);
			}else if(impBusq.getIntMeses()==1 || impBusq.getIntMeses()==2 || impBusq.getIntMeses()==3 || impBusq.getIntMeses()==4 || impBusq.getIntMeses()==5 || impBusq.getIntMeses()==6 || impBusq.getIntMeses()==7 || impBusq.getIntMeses()==8 || impBusq.getIntMeses()==9){
				impBusq.setIntImpuPeriodo(Integer.parseInt(impBusq.getIntImpuPeriodo()+"0"+impBusq.getIntMeses()));
			}else{
				impBusq.setIntImpuPeriodo(Integer.parseInt(impBusq.getIntImpuPeriodo()+""+impBusq.getIntMeses()));
			}
			if (impBusq.getIntTipoImpuestoCod().equals(0)) {
				impBusq.setIntTipoImpuestoCod(null);
			}
			if (impBusq.getIntParaEstadoCod().equals(0)) {
				impBusq.setIntParaEstadoCod(null);
			}
			if (impBusq.getIntParaEstadoPagoCod().equals(0)) {
				impBusq.setIntParaEstadoPagoCod(null);
			}
			listaBusqImpuestoTemp = impuestoFacade.getBuscar(impBusq);
			if (listaBusqImpuestoTemp!=null && !listaBusqImpuestoTemp.isEmpty()) {
				listaBusqImpuesto.addAll(listaBusqImpuestoTemp);
			}
			log.info("Lista resultante de la busqueda: "+listaBusqImpuesto);
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			log.error(e);
			e.printStackTrace();
		}
	}
	
	public void grabarTributacion() throws BusinessException{
		try {
			log.info("Impuesto generado: "+impuesto);
			saveImpuesto();
		} catch (Exception e) {
			log.info("Error grabarTributacion() --> "+e.getMessage()); 
		}
	}
	
	/*
	 * Autor: jchavez / Tarea: Modificación / Fecha: 09.08.2014
	 * Funcionalidad: se modifica para su grabacion correcta.
	 */
	public void saveImpuesto() throws BusinessException{

//		Impuesto beanImpuestoModificacion = null;
		try {
			if (blnShowTributacion) {
				if(!validarImpuesto()){
					return;
				}
			}else if (blnShowTributacionRectificacion) {
				if(!validarRectificacion()){
					return;
				}
			}			
			
			impuesto.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_TRIBUTACION);
			impuesto.setIntPersEmpresaEntidadPk(IDEMPRESA_SESION);
			impuesto.setIntPersEmpresaPersonaPk(IDEMPRESA_SESION);
			impuesto.setIntPersPersonaEncargadaPk(intCodigoPersonal);
			impuesto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			impuesto.setIntParaEstadoPagoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			impuesto.setIntPersEmpresaUsuarioPk(IDEMPRESA_SESION);
			impuesto.setIntPersPersonaUsuarioPk(IDUSUARIO_SESION);
			impuesto.setIntPersPersona(getIntCodigoPersona());
			impuesto.setTsImpuFechaRegistro(new Timestamp(new Date().getTime()));
			impuesto.setIntPersPersona(getIntCodigoPersona());
			
						
			if (blnShowTributacion) {
				if(impuesto.getIntMeses()==1 || impuesto.getIntMeses()==2 || impuesto.getIntMeses()==3 || impuesto.getIntMeses()==4 ||impuesto.getIntMeses()==5|| impuesto.getIntMeses()==6 || impuesto.getIntMeses()==7 || impuesto.getIntMeses()==8 || impuesto.getIntMeses()==9){
					String periodo = impuesto.getIntImpuPeriodo()+"0"+impuesto.getIntMeses();
					int periodo1 = Integer.parseInt(periodo);
					impuesto.setIntImpuPeriodo(periodo1);
				}else{
					String periodo = impuesto.getIntImpuPeriodo()+""+impuesto.getIntMeses();
					int periodo1 = Integer.parseInt(periodo);
					impuesto.setIntImpuPeriodo(periodo1);
				}
				impuesto.setIntTipoRectificatoriaCod(null);
			}else if (blnShowTributacionRectificacion) {
				impuesto.setIntImpuPeriodo(null);
				impuesto.setIntTipoImpuestoCod(null);
			}
			
			
			if(impuesto.getId()==null){
				if(impuesto.getId()==null || impuesto.getId().getIntItemImpuesto()==null){
					impuesto.setId(new ImpuestoId());
					impuesto.getId().setIntPersEmpresa(IDEMPRESA_SESION);	
					impuesto.setIntPersPersonaEntidadPk(blnShowTributacionRectificacion == false?impuesto.getIntPersPersona():impRect.getIntPersPersonaEntidadPk());
					impuesto.setStrJuriRazonsocial(getStrjuriRazonSocial());
					if (blnShowTributacionRectificacion) {
						impuesto.setIntPersEmpresaRectPk(IDEMPRESA_SESION);
						impuesto.setIntContItemImpuestoRectPk(impRect.getId().getIntItemImpuesto());
					}
					
					impuesto = impuestoFacade.grabarImpuesto(impuesto);
					
					if(impuesto!=null){
						blnVisible = true;
						FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
					}
				}
			}else{
				impuesto.setIntPersPersonaEncargadaPk(intCodigoPersonal);
				impuestoFacade.modificarImpuesto(impuesto);
				if(impuesto!=null){
					blnVisible = true;
					FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_MODIFICAR);
				}
			}
		} catch (Exception e) {
			log.info("Error en saveImpuesto() --> "+e.getMessage());
		}
	}

	public void saveRectificatoria() throws BusinessException, EJBFactoryException{
		Impuesto beanRectificacion = null;

		/*Aca modifica*/
		if(getImpuesto().getId()!=null){
			if(getImpuesto().getId().getIntPersEmpresa()!=null && getImpuesto().getId().getIntItemImpuesto()!=null){
				/*Forma el Periodo*/
				Calendar gcal = Calendar.getInstance();
	//			GregorianCalendar gcal = new GregorianCalendar();
				gcal.setTime(getImpuesto().getTsImpuFechaPresentacionD());
				String strPeriodo = gcal.get(Calendar.YEAR)+""+(gcal.get(Calendar.MONTH)+1);
				if(strPeriodo.length()<6){//Debe tener 6 caracteres
					strPeriodo = gcal.get(Calendar.YEAR)+"0"+(gcal.get(Calendar.MONTH)+1);
				}
				getImpuesto().setIntImpuPeriodo(Integer.valueOf(strPeriodo));
				/*Estado Pago*/
				int estado=1;
				getImpuesto().setIntParaEstadoPagoCod(estado);
				/*Estado Codigo*/
				int ent = 1;
				getImpuesto().setIntParaEstadoCod(ent);
				beanRectificacion = impuestoFacade.modificarImpuesto(impuesto);
				
				if(beanRectificacion!=null){
					FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_MODIFICAR);
				}
			}
		}else{
		//Validaciones
			if(!validarRectificacion()){
				return;
			}
		}
		if(getImpuesto().getId()==null || getImpuesto().getId().getIntItemImpuesto()==null){
			getImpuesto().setId(new ImpuestoId());
			getImpuesto().getId().setIntPersEmpresa(IDEMPRESA_SESION);
			getImpuesto().setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_TRIBUTACION);
			getImpuesto().setIntPersEmpresaEntidadPk(IDEMPRESA_SESION);
			getImpuesto().setIntPersEmpresaPersonaPk(IDEMPRESA_SESION);
			int ent = 1;
			getImpuesto().setIntParaEstadoCod(ent);
			getImpuesto().setIntPersEmpresaUsuarioPk(IDEMPRESA_SESION);
			getImpuesto().setIntPersPersonaUsuarioPk(IDUSUARIO_SESION);
			getImpuesto().setTsImpuFechaRegistro(new Timestamp(new Date().getTime()));
			int estado=1;
			getImpuesto().setIntParaEstadoPagoCod(estado);
			//Obteniendo el Periodo
//			GregorianCalendar gcal = new GregorianCalendar();
			Calendar gcal = Calendar.getInstance();
			gcal.setTime(getImpuesto().getTsImpuFechaPresentacionD());
			String strPeriodo = gcal.get(Calendar.YEAR)+""+(gcal.get(Calendar.MONTH)+1);
			if(strPeriodo.length()<6){//Debe tener 6 caracteres
				strPeriodo = gcal.get(Calendar.YEAR)+"0"+(gcal.get(Calendar.MONTH)+1);
			}
			getImpuesto().setIntImpuPeriodo(Integer.valueOf(strPeriodo));
			
			try {
				impuesto = impuestoFacade.grabarImpuesto(getImpuesto());
				FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
					
			} catch (BusinessException e) {
				FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
				log.error(e);
			}
		}
//		setImpuestoRect(new Impuesto());
		setImpuesto(new Impuesto());
//		cleanBeanTributacion();
//		cancelNew();
//		busquedaImpuesto();
		setBlnShowTributacionRectificacion(false);
	}

	public boolean validarImpuesto(){
		
		boolean isValidImpuesto = true;
		FacesContextUtil.setMessageError("");
		
		if(getImpuesto()!=null){
		if (impuesto.getIntPersPersona()==null){
			isValidImpuesto = false;
			FacesContextUtil.setMessageError("Debe seleccionar la Entidad es un Campo Obligatorio");
		}
		if (impuesto.getIntTipoImpuestoCod()==null || getImpuesto().getIntTipoImpuestoCod()==0){
			isValidImpuesto = false;
			FacesContextUtil.setMessageError("Debe seleccionar el tipo es un Campo Obligatorio");
		}
		if (intCodigoPersonal==null){
			isValidImpuesto = false;
			FacesContextUtil.setMessageError("Debe seleccionar al Personal Encargado es un Campo Obligatorio");
		}
		if (impuesto.getTsImpuFechaPresentacionD()==null){
			isValidImpuesto = false;
			FacesContextUtil.setMessageError("Debe seleccionar una fecha es un Campo Obligatorio");
		}
		if (impuesto.getIntMeses()==null){
			isValidImpuesto = false;
			FacesContextUtil.setMessageError("Debe seleccionar el Mes es un Campo Obligatorio");
		}
		if (impuesto.getIntImpuPeriodo()==null || getImpuesto().getIntImpuPeriodo()==0){
			isValidImpuesto = false;
			FacesContextUtil.setMessageError("Debe seleccionar el Año es un Campo Obligatorio");
		}
		if (impuesto.getBdImpuMonto()==null){
			isValidImpuesto = false;
			FacesContextUtil.setMessageError("Debe de Ingresar el Importe es un Campo Obligatorio");
		}
		if (impuesto.getIntParaTipoMonedaCod()==null || getImpuesto().getIntParaTipoMonedaCod()==0){
			isValidImpuesto = false;
			FacesContextUtil.setMessageError("Debe de Selecionar el Tipo de Moneda es un Campo Obligatorio");
		}
		if (impuesto.getStrImpuGlosa().trim()=="" || getImpuesto().getStrImpuGlosa().isEmpty()){
			isValidImpuesto = false;
			FacesContextUtil.setMessageError("Debe de Ingresar la Glosa es un Campo Obligatorio");
		}
	}
		return isValidImpuesto;
}
	public boolean validarRectificacion(){
//		getImpuesto().setIntPersPersonaEncargadaPk(getImpuesto().getIntPersPerson());
	
		boolean isValidRectificacion = true;
		FacesContextUtil.setMessageError("");
		if(getImpuesto()!=null){
		if (getImpuesto().getIntPersPersonaEntidadPk()==null){
			isValidRectificacion = false;
			FacesContextUtil.setMessageError("Debe seleccionar el Impuesto es un Campo Obligatorio");
		}
		if (getImpuesto().getIntTipoRectificatoriaCod()==0){
			isValidRectificacion = false;
			FacesContextUtil.setMessageError("Debe seleccionar el Tipo es un Campo Obligatorio");
		}
		if (intCodigoPersonal==null){
			isValidRectificacion = false;
			FacesContextUtil.setMessageError("Debe seleccionar al Personal Encargado es un Campo Obligatorio");
		}
		if (getImpuesto().getTsImpuFechaPresentacionD()==null){
			isValidRectificacion = false;
			FacesContextUtil.setMessageError("Debe seleccionar una fecha es un Campo Obligatorio");
		}
		if (getImpuesto().getBdImpuMonto()==null){
			isValidRectificacion = false;
			FacesContextUtil.setMessageError("Debe de Ingresar el Importe es un Campo Obligatorio");
		}
		if (getImpuesto().getIntParaTipoMonedaCod()==null || getImpuesto().getIntParaTipoMonedaCod()==0){
			isValidRectificacion = false;
			FacesContextUtil.setMessageError("Debe de Seleccionar el tipo de Moneda es un Campo Obligatorio");
		}
		if (getImpuesto().getStrImpuGlosa().trim()=="" || getImpuesto().getStrImpuGlosa().isEmpty()){
			isValidRectificacion = false;
			FacesContextUtil.setMessageError("Debe de Ingresar la Glosa es un Campo Obligatorio");
				}
		}
		return isValidRectificacion;
	}


	public void showTributacion(){
		blnShowPanelInferior = true;
		blnShowTributacion = true;
		blnShowTributacionRectificacion = false;
		blnVisible = false;
		
		strNombreCompleto = "";
		impuesto = new Impuesto();
	}
	public void showTributacionRectificacion(){
		blnShowPanelInferior = true;		
		blnShowTributacion = false;
		blnShowTributacionRectificacion = true;
		blnVisible = false;
		
		strNombreCompleto = "";
		impuesto = new Impuesto();
	}
	
	public void newTributacionBloqueado(){
		setBlnShowTributacionBloqueado(true);
	}
	
	public void newTributacionRectificacionBloqueado(){
		setBlnShowTributacionBloqueado(false);
		setBlnShowTributacionRectificacionBloqueado(true);
	}

	public List<Impuesto> buscarPersonaJuridica() throws EJBFactoryException, BusinessException{
		Impuesto imp = new Impuesto();
		listaPersonaJuri = impuestoFacade.getListaPersonaJuridica(imp);
		return listaPersonaJuri;
	}
	
	public void buscarImpuesto() throws BusinessException, EJBFactoryException{
		setListaImpuesto(Impuesto());
	}
	
	public List<Impuesto> Impuesto() throws EJBFactoryException, BusinessException{
		Impuesto imp = new Impuesto();
		listaImpuesto = impuestoFacade.getListaImpuesto(imp);
		return listaImpuesto;
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

	public void recargarCboFiltroPersona1() throws BusinessException{
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
			
			List<Tabla> listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_OPCIONPERSONABUSQUEDA));
			
			if(listTabla!=null){
				for(Tabla tabla : listTabla){
					if(intTipoPersona!=null && intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
						if(tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE) ||
								tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
							cboFiltroPersona1.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else if(intTipoPersona!=null && intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
						if(tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE) ||
								tabla.getIntIdDetalle().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)){
							cboFiltroPersona1.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
						}
					}else{
						cboFiltroPersona1.add(new SelectItem(tabla.getIntIdDetalle(), tabla.getStrDescripcion()));
					}
				}
			}
			log.info("liostacombo"+cboFiltroPersona1);
		} catch (Exception e) {
			log.info("debug combo");
		}
		
	}
	/**/
	
	public void seleccionarPersona(){
		log.info("-----------------------Debugging HojaManualController.seleccionarPersona()-----------------------------");
		String strIdPersona = (String) FacesContextUtil.getRequestParameter("pRowPersona");
		log.info("strIdPersona: "+strIdPersona);
		
		setImpuesto(listaPersonaJuri.get(Integer.valueOf(strIdPersona)));
		for (int entero = Integer.parseInt(strIdPersona); entero < listaPersonaJuri.size(); entero++) {
			setIntCodigoPersona(listaPersonaJuri.get(entero).getIntPersPersona());
			setStrjuriRazonSocial(listaPersonaJuri.get(entero).getStrJuriRazonsocial());
		}
	}
	
	public void seleccionarPersonaRol(){
		log.info("-----------------------Debugging HojaManualController.seleccionarCuenta()-----------------------------");
		String strCuenta = (String) FacesContextUtil.getRequestParameter("pRowCuentaContable");
		Impuesto imp = null;
		log.info("strCuenta: "+strCuenta);
//		setImpuesto(listaPersonaJuri.get(Integer.valueOf(strCuenta)));

		imp = listaPersonal.get(Integer.valueOf(strCuenta));
		intCodigoPersonal = imp.getIntPersPerson();
		setStrNombreCompleto(imp.getStrNombreCompleto());
	}
	
	public void seleccionarImpuesto(){
		log.info("-----------------------Debugging HojaManualController.seleccionarCuenta()-----------------------------");
		String strImpuesto = (String) FacesContextUtil.getRequestParameter("pRowImpuesto");
//		Impuesto imp = null;
		log.info("strCuenta: "+strImpuesto);
		impRect = listaImpuesto.get(Integer.valueOf(strImpuesto));
		String strSimboloMoneda = "";
		try {			
			if (impRect.getIntParaTipoMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) {
				strSimboloMoneda = "S/. ";
			}else if (impRect.getIntParaTipoMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_DOLARES)) {
				strSimboloMoneda = "$ ";
			}else if (impRect.getIntParaTipoMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_EXTRANJERA)) {
				strSimboloMoneda = "€ ";
			}
			impuesto.setIntPersPersonaEntidadPk(impRect.getIntPersPersonaEntidadPk());
			impRect.getId().setIntPersEmpresa(IDEMPRESA_SESION);
			impRect = impuestoFacade.getListaPorPk(impRect.getId());
			impuesto.setStrConcatenado(impRect.getId().getIntItemImpuesto()+" - Periodo: "+impRect.getIntImpuPeriodo()+" - Importe :"+strSimboloMoneda+" "+impRect.getBdImpuMonto()); //+impRect.getStrDescTipoImpuesto()+" - "
		} catch (Exception e) {
			log.info("Error en seleccionarImpuesto() --> "+e.getMessage());
		}
	}
	
	public void seleccionarPersRect(){
		log.info("-----------------------Debugging HojaManualController.seleccionarCuenta()-----------------------------");
		String strRect = (String) FacesContextUtil.getRequestParameter("pRowRect");
		log.info("strCuenta: "+strRect);
		setImpuesto(listaPersonaJuri.get(Integer.valueOf(strRect)));
	}
	
//	public void searchPersona(ActionEvent event) throws BusinessException, EJBFactoryException{
//		log.info("-------------------------------------Debugging HojaManualController.searchPersona-------------------------------------");
//		listaPersonaJuri.addAll(buscarPersona());		
////		setListaPersonaJuri(buscarPersona());
//	}
	//Autor: jchavez / Tarea: Modificación / Fecha: 08.08.2014 /
//	public void searchPersonal() throws BusinessException{
//		try {
//			buscarPersonal();	
//		} catch (Exception e) {
//			log.info("Error en searchPersonal() --> "+e.getMessage());
//		}
////		listaPersonal.clear();
//			
////		setListaPersonaJuri(buscarPersona());
//	}
	
	public void searchPersona() throws BusinessException, EJBFactoryException{
		listaPersonaJuri.addAll(buscarPersonaJuridica());
//		setListaPersonaJuri(buscarPersonaJuridica());
	}
	
	public void searchImpuestoSelect() throws BusinessException{
		Impuesto imp = new Impuesto();
		try {
			listaPersonaJuri = impuestoFacade.getListaPersonaJuridica(imp);
			log.info(listaPersonaJuri);
		} catch (Exception e) {
			log.info("Error en seleccionarImpuesto() --> "+e.getMessage());
		}		
//		setListaPersonaJuri(buscarPersonaJuridica());
	}
	/*
	 * Autor: jchavez / Tarea: Modificación / Fecha: 08.08.2014 /
	 * Funcionalidad: Se soluciona errores de carga y limpieza 
	 */
	public void buscarPersonal() throws BusinessException{
		Impuesto beanPersonaBusq = new Impuesto();
//		List<Impuesto> lstImp = null;
		try {
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
//					lstImp = impuestoFacade.getListaNombreDniRol(beanPersonaBusq);
					listaPersonal = impuestoFacade.getListaNombreDniRol(beanPersonaBusq); //.addAll(lstImp);
				}
			}
		} catch (Exception e) {
			log.info("Error en buscarPersona() --> "+e.getMessage());
		}
	}
	/**/
	public List<Impuesto> buscarPersona2() throws EJBFactoryException, BusinessException{
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
				
				listaPersonaJuri = personaFacade.getListaNombreDniRol(beanPersonaBusq);
				
			}
		}
//		cleanBeanTributacion();
		return listaPersonaJuri;
	}
	public void limpiarImpuesto(){
		listaImpuesto = new ArrayList<Impuesto>();
	}//listaPersonaJuri
	
	public void limpiar(){
		intCboTipoPersonaBusq = 0;
		listaPersonaJuri.clear();
		listaPersonal.clear();
		
		cboFiltroPersona = new ArrayList<SelectItem>();
//		listTabla = new ArrayList<Tabla>();
		strTxtPersonaBusq = "";
	}
	
	public void seleccionarTributo(ActionEvent event){
//		setBlnShowTributacion(true);
		try {
			impuesto = (Impuesto)event.getComponent().getAttributes().get("itemTribuBusq");
			if (impuesto.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)) {
				if (impuesto.getIntParaEstadoPagoCod().equals(Constante.PARAM_T_ESTADOPAGO_PENDIENTE)) {
					blnVerDatos = true;
					blnModificarDatos = true;
				}else if (impuesto.getIntParaEstadoPagoCod().equals(Constante.PARAM_T_ESTADOPAGO_CANCELADO)) {
					blnVerDatos = true;
					blnModificarDatos = false;
				}
			}else if (impuesto.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)) {
				blnVerDatos = true;
				blnModificarDatos = false;
			}
		} catch (Exception e) {
			log.error("Error en seleccionarTributo --> "+e);
	}
}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad:
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intItemImpuesto
	 * @return Si es Impuesto Retorna el impusto lleno para mostrar en el panel de impuesto / Rectificatoria Retorna el impuesto lleno para mostrar en el panel de rectificatoria  
	 * @throws BusinessException
	 */
	public void mostrar(ActionEvent event) throws EJBFactoryException, BusinessException{
		Impuesto imp = new Impuesto();
		if(impuesto.getStrDescTipoImpuesto().equals("Impuesto")){	
			if(impuesto.getStrDescEstadoPago().equals("Pendiente")){
				impuesto.setIntParaEstadoPagoCod(1);
			}//intTipoImpuestoCod
			if(impuesto.getStrPersRuc().equals("IGV")){
				impuesto.setIntTipoImpuestoCod(1);
			}
	//		getImpuesto().setId(new ImpuestoId());
	//		getImpuesto().getId().setIntPersEmpresa(IDEMPRESA_SESION);
			impuesto.getId().setIntPersEmpresa(IDEMPRESA_SESION);
			impuesto.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_TRIBUTACION);
			impuesto.setIntPersEmpresaEntidadPk(IDEMPRESA_SESION);
			impuesto.setIntPersEmpresaPersonaPk(IDEMPRESA_SESION);
			/*Partir Periodo*/
			/*Se obtiene el año*/
			int periodo = impuesto.getIntImpuPeriodo();
			String periodoString = Integer.toString(periodo);
			String año = periodoString.substring (0,4);
			String mes = periodoString.substring (4,6);
			int añoEnt = Integer.parseInt(año);
			impuesto.setIntImpuPeriodo(añoEnt);
			/*Se ontiene el mes*/
			if(mes.equals("01") || mes.equals("02") || mes.equals("03") || mes.equals("04") || mes.equals("05") || mes.equals("06") || mes.equals("07") || mes.equals("08") || mes.equals("09")){
				String mesDig = mes.substring (1,2);
				int mesEnt = Integer.parseInt(mesDig);
				impuesto.setIntMeses(mesEnt);
			}else{
				int mesEnt = Integer.parseInt(mes);
				impuesto.setIntMeses(mesEnt);
			}
			/*Se da Formato a la fecha de precentacion*/
			listaImpuesto = impuestoFacade.getListaImpuesto(imp);
			
			for (Impuesto listImpuesto : listaImpuesto) {
				if(impuesto.getId().getIntItemImpuesto().equals(listImpuesto.getId().getIntItemImpuesto())){
					impuesto.setIntParaTipoMonedaCod(listImpuesto.getIntParaTipoMonedaCod());
					impuesto.setTsImpuFechaPresentacionD(listImpuesto.getTsImpuFechaPresentacionD());
					impuesto.setStrImpuGlosa(listImpuesto.getStrImpuGlosa());
				}
			}
			setBlnShowTributacion(true);
		}else if(impuesto.getStrDescTipoImpuesto().equals("Rectificatoria")){
			if(impuesto.getStrDescEstadoPago().equals("Pendiente")){
				impuesto.setIntParaEstadoPagoCod(1);
			}//intTipoImpuestoCod
			if(impuesto.getStrPersRuc().equals("IGV")){
				impuesto.setIntTipoRectificatoriaCod(1);
			}
			Impuesto impuestoRect = new Impuesto();
			impuestoRect.setStrJuriRazonsocial(impuesto.getStrJuriRazonsocial());
			impuesto.getId().setIntPersEmpresa(IDEMPRESA_SESION);
			impuesto.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_TRIBUTACION);
			impuesto.setIntPersEmpresaEntidadPk(IDEMPRESA_SESION);
			impuesto.setIntPersEmpresaPersonaPk(IDEMPRESA_SESION);
			/*Se da Formato a la fecha de precentacion*/
			listaImpuesto = impuestoFacade.getListaImpuesto(imp);
			
			for (Impuesto listImpuesto : listaImpuesto) {
				if(impuesto.getId().getIntItemImpuesto().equals(listImpuesto.getId().getIntItemImpuesto())){
					impuesto.setIntParaTipoMonedaCod(listImpuesto.getIntParaTipoMonedaCod());
					impuesto.setTsImpuFechaPresentacionD(listImpuesto.getTsImpuFechaPresentacionD());
					impuesto.setStrImpuGlosa(listImpuesto.getStrImpuGlosa());
					impuesto.setIntPersPersonaEntidadPk(listImpuesto.getIntPersPersonaEntidadPk());
	//				impuestoRect.getId().setIntItemImpuesto(listImpuesto.getId().getIntItemImpuesto());
	//				impuestoRect.setStrDescTipoImpuesto(listImpuesto.getStrDescTipoImpuesto());
	//				impuestoRect.setIntImpuPeriodo(listImpuesto.getIntImpuPeriodo());
	//				impuestoRect.setBdImpuMonto(listImpuesto.getBdImpuMonto());
				}
			}
			System.out.println("Llego aca " + impuesto);
			System.out.println("Llego aca " + impuestoRect);
			setBlnShowTributacionRectificacion(true);
		}
	}
	
	public void modificarRegistro() throws BusinessException{
		log.info("lista busqueda impuesto: "+listaBusqImpuesto);
		String strSimboloMoneda = "";
		blnShowPanelInferior = false;
		blnShowTributacion = false;
		blnShowTributacionRectificacion = false;
		blnVisible = false;
		blnModificar = true;
		try {
			if (impuesto.getIntTipoImpuestoCod()!=null && impuesto.getIntTipoRectificatoriaCod()==null) {
				blnShowPanelInferior = true;
				blnShowTributacion = true;
				blnShowTributacionRectificacion = false;
				blnVisible = false;
				blnModificar = true;
				
				intCodigoPersonal = impuesto.getIntPersPersonaEncargadaPk();
				impuesto.setIntPersPersona(impuesto.getIntPersPersonaEntidadPk());
				intCodigoPersona = impuesto.getIntPersPersonaEntidadPk();
				strNombreCompleto = impuesto.getStrNombreCompleto();
				Integer añoEnt = Integer.parseInt(impuesto.getIntImpuPeriodo().toString().substring (0,4));
				Integer mesEnt = Integer.parseInt(impuesto.getIntImpuPeriodo().toString().substring (4,6));
				
				impuesto.setIntImpuPeriodo(añoEnt);
				impuesto.setIntMeses(mesEnt);
				
			}else if (impuesto.getIntTipoImpuestoCod()==null && impuesto.getIntTipoRectificatoriaCod()!=null) {
				blnShowPanelInferior = true;
				blnShowTributacion = false;
				blnShowTributacionRectificacion = true;
				blnVisible = false;
				blnModificar = true;
				
				strNombreCompleto = impuesto.getStrNombreCompleto();
				
				Impuesto impRect = new Impuesto();
				impRect.setId(new ImpuestoId());
				impRect.getId().setIntPersEmpresa(IDEMPRESA_SESION);
				impRect.getId().setIntItemImpuesto(impuesto.getIntContItemImpuestoRectPk());
				impuestoDeLaRectificatoria = impuestoFacade.getListaPorPk(impRect.getId());
				intCodigoPersona = impuestoDeLaRectificatoria.getIntPersPersonaEntidadPk();
				if (impuestoDeLaRectificatoria.getIntParaTipoMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) {
					strSimboloMoneda = "S/. ";
				}else if (impuestoDeLaRectificatoria.getIntParaTipoMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_DOLARES)) {
					strSimboloMoneda = "$ ";
				}else if (impuestoDeLaRectificatoria.getIntParaTipoMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_EXTRANJERA)) {
					strSimboloMoneda = "€ ";
				}
				
				impuesto.setStrConcatenado(impuestoDeLaRectificatoria.getId().getIntItemImpuesto()+" - Periodo: "+impuestoDeLaRectificatoria.getIntImpuPeriodo()+"- Importe :"+strSimboloMoneda+" "+impuestoDeLaRectificatoria.getBdImpuMonto());
			}
		} catch (Exception e) {
			log.info("Error en modificarRegistro() --> "+e.getMessage());
		}
	}
	
	/**
	 * Autor: Rodolfo Villarreal A. / Tarea: Creación / Fecha: 
	 * Funcionalidad: Solo cambia el estado de 1 a 3
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intItemImpuesto
	 * @return Cambia de Estado de 1 a 3 al momento de eliminar  
	 * @throws BusinessException
	 * @throws EJBFactoryException 
	 */
	public void eliminarRegistro() throws BusinessException{
		try {
			impuesto.setIntParaEstadoCod(3);
			impuestoFacade.modificarImpuesto(impuesto);
			busquedaImpuesto();
		} catch (Exception e) {
			log.info("Error en eliminarRegistro() --> "+e.getMessage()); 
		}
//		Impuesto imp = new Impuesto();
//		Impuesto beanImpuesto = null;
//		Impuesto beanRectificatoria = null;
//		if(impuesto.getStrDescTipoImpuesto().equals("Impuesto")){
//			impuesto.getId().setIntPersEmpresa(IDEMPRESA_SESION);
//			impuesto.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_TRIBUTACION);
//			impuesto.setIntPersEmpresaEntidadPk(IDEMPRESA_SESION);
//			impuesto.setIntPersEmpresaPersonaPk(IDEMPRESA_SESION);
//			if(impuesto.getStrDescEstadoPago().equals("Pendiente")){
//				impuesto.setIntParaEstadoPagoCod(1);
//			}else if(impuesto.getStrDescEstadoPago().equals("Cancelado")){
//				impuesto.setIntParaEstadoPagoCod(2);
//			}
//			if(impuesto.getStrPersRuc().equals("IGV")){
//				impuesto.setIntTipoImpuestoCod(1);
//			}
//			getImpuesto().setIntParaEstadoCod(3);
//			listaImpuesto = impuestoFacade.getListaImpuesto(imp);
//			
//			for (Impuesto listImpuesto : listaImpuesto) {
//				if(impuesto.getId().getIntItemImpuesto().equals(listImpuesto.getId().getIntItemImpuesto())){
//					impuesto.setIntParaTipoMonedaCod(listImpuesto.getIntParaTipoMonedaCod());
//					impuesto.setTsImpuFechaPresentacionD(listImpuesto.getTsImpuFechaPresentacionD());
//					impuesto.setStrImpuGlosa(listImpuesto.getStrImpuGlosa());
//				}
//			}
//			//beanImpuesto = 
//			impuestoFacade.modificarImpuesto(impuesto);
//		}else if(impuesto.getStrDescTipoImpuesto().equals("Rectificatoria")){
//			impuesto.getId().setIntPersEmpresa(IDEMPRESA_SESION);
//			impuesto.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_TRIBUTACION);
//			impuesto.setIntPersEmpresaEntidadPk(IDEMPRESA_SESION);
//			impuesto.setIntPersEmpresaPersonaPk(IDEMPRESA_SESION);
//			if(impuesto.getStrDescEstadoPago().equals("Pendiente")){
//				impuesto.setIntParaEstadoPagoCod(1);
//			}else if(impuesto.getStrDescEstadoPago().equals("Cancelado")){
//				impuesto.setIntParaEstadoPagoCod(2);
//			}
//			if(impuesto.getStrPersRuc().equals("IGV")){
//				impuesto.setIntTipoRectificatoriaCod(1);
//			}
//			impuesto.setIntParaEstadoCod(3);
//			listaImpuesto = impuestoFacade.getListaImpuesto(imp);
//			
//			for (Impuesto listImpuesto : listaImpuesto) {
//				if(impuesto.getId().getIntItemImpuesto().equals(listImpuesto.getId().getIntItemImpuesto())){
//					impuesto.setIntParaTipoMonedaCod(listImpuesto.getIntParaTipoMonedaCod());
//					impuesto.setTsImpuFechaPresentacionD(listImpuesto.getTsImpuFechaPresentacionD());
//					impuesto.setStrImpuGlosa(listImpuesto.getStrImpuGlosa());
//				}
//			}
//			//beanRectificatoria = 
//			
//			impuestoFacade.modificarImpuesto(impuesto);
//		}
		
	}
	/**
	 * Autor: Rodolfo Villarreal A.
	 * Funcionalidad:Muestra el panel con los registros bloqueados segun la validacion
	 * -------------------------------------------------------------------------------
	 * Autor: jchavez / Tarea: Modificación / Fecha: 12.08.2014 /
	 * Funcionalidad: Se modifica metodo de visualización.
	 * @autor Rodolfo Villarreal A.
	 * @version 1.0
	 * @param intPersEmpresa
	 * @param intItemImpuesto
	 * @throws BusinessException
	 */
	public void verCampos() throws BusinessException{
		log.info("lista busqueda impuesto: "+listaBusqImpuesto);
		String strSimboloMoneda = "";
		blnShowPanelInferior = false;
		blnShowTributacion = false;
		blnShowTributacionRectificacion = false;
		blnVisible = false;
		blnModificar = false;
		try {
			if (impuesto.getIntTipoImpuestoCod()!=null && impuesto.getIntTipoRectificatoriaCod()==null) {
				blnShowPanelInferior = true;
				blnShowTributacion = true;
				blnShowTributacionRectificacion = false;
				blnVisible = true;
				blnModificar = false;
				
				strNombreCompleto = impuesto.getStrNombreCompleto();
				Integer añoEnt = Integer.parseInt(impuesto.getIntImpuPeriodo().toString().substring (0,4));
				Integer mesEnt = Integer.parseInt(impuesto.getIntImpuPeriodo().toString().substring (4,6));
				
				impuesto.setIntImpuPeriodo(añoEnt);
				impuesto.setIntMeses(mesEnt);
				
			}else if (impuesto.getIntTipoImpuestoCod()==null && impuesto.getIntTipoRectificatoriaCod()!=null) {
				blnShowPanelInferior = true;
				blnShowTributacion = false;
				blnShowTributacionRectificacion = true;
				blnVisible = true;
				blnModificar = false;
				
				strNombreCompleto = impuesto.getStrNombreCompleto();
				Impuesto impRect = new Impuesto();
				impRect.setId(new ImpuestoId());
				impRect.getId().setIntPersEmpresa(IDEMPRESA_SESION);
				impRect.getId().setIntItemImpuesto(impuesto.getIntContItemImpuestoRectPk());
				impuestoDeLaRectificatoria = impuestoFacade.getListaPorPk(impRect.getId());
				
				if (impuestoDeLaRectificatoria.getIntParaTipoMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) {
					strSimboloMoneda = "S/. ";
				}else if (impuestoDeLaRectificatoria.getIntParaTipoMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_DOLARES)) {
					strSimboloMoneda = "$ ";
				}else if (impuestoDeLaRectificatoria.getIntParaTipoMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_EXTRANJERA)) {
					strSimboloMoneda = "€ ";
				}

				impuesto.setStrConcatenado(impuestoDeLaRectificatoria.getId().getIntItemImpuesto()+" - Periodo: "+impuestoDeLaRectificatoria.getIntImpuPeriodo()+" - Importe :"+strSimboloMoneda+" "+impuestoDeLaRectificatoria.getBdImpuMonto());
			}
		} catch (Exception e) {
			log.info("Error en verCampos() --> "+e.getMessage());
		}		
	}
	
//	public void verCampos() throws BusinessException{
//		Impuesto imp = new Impuesto();
//		try {
//			if (impuesto.getIntTipoImpuestoCod()!=null && impuesto.getIntTipoRectificatoriaCod()==null) {
//				
//
//
//				/*Se obtiene el año*/
//				int periodo = impuesto.getIntImpuPeriodo();
//				String periodoString = Integer.toString(periodo);
//				String año = periodoString.substring (0,4);
//				String mes = periodoString.substring (4,6);
//				int añoEnt = Integer.parseInt(año);
//				impuesto.setIntImpuPeriodo(añoEnt);
//				/*Se ontiene el mes*/
//				if(mes.equals("01") || mes.equals("02") || mes.equals("03") || mes.equals("04") || mes.equals("05") || mes.equals("06") || mes.equals("07") || mes.equals("08") || mes.equals("09")){
//					String mesDig = mes.substring (1,2);
//					int mesEnt = Integer.parseInt(mesDig);
//					impuesto.setIntMeses(mesEnt);
//				}else{
//					int mesEnt = Integer.parseInt(mes);
//					impuesto.setIntMeses(mesEnt);
//				}
//				/*Se da Formato a la fecha de precentacion*/
//				listaImpuesto = impuestoFacade.getListaImpuesto(imp);
//				
//				for (Impuesto listImpuesto : listaImpuesto) {
//					if(impuesto.getId().getIntItemImpuesto().equals(listImpuesto.getId().getIntItemImpuesto())){
//						impuesto.setIntParaTipoMonedaCod(listImpuesto.getIntParaTipoMonedaCod());
//						impuesto.setTsImpuFechaPresentacionD(listImpuesto.getTsImpuFechaPresentacionD());
//						impuesto.setStrImpuGlosa(listImpuesto.getStrImpuGlosa());
//					}
//				}
//				setBlnShowTributacion(true);
//				
//			}else if (impuesto.getIntTipoImpuestoCod()==null && impuesto.getIntTipoRectificatoriaCod()!=null) {
//				if(impuesto.getStrDescEstadoPago().equals("Pendiente")){
//					impuesto.setIntParaEstadoPagoCod(1);
//				}//intTipoImpuestoCod
//				if(impuesto.getStrPersRuc().equals("IGV")){
//					impuesto.setIntTipoRectificatoriaCod(1);
//				}
//				Impuesto impuestoRect = new Impuesto();
//				impuestoRect.setStrJuriRazonsocial(impuesto.getStrJuriRazonsocial());
//				impuesto.getId().setIntPersEmpresa(IDEMPRESA_SESION);
//				impuesto.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_TRIBUTACION);
//				impuesto.setIntPersEmpresaEntidadPk(IDEMPRESA_SESION);
//				impuesto.setIntPersEmpresaPersonaPk(IDEMPRESA_SESION);
//				/*Se da Formato a la fecha de precentacion*/
//				listaImpuesto = impuestoFacade.getListaImpuesto(imp);
//				
//				for (Impuesto listImpuesto : listaImpuesto) {
//					if(impuesto.getId().getIntItemImpuesto().equals(listImpuesto.getId().getIntItemImpuesto())){
//						impuesto.setIntParaTipoMonedaCod(listImpuesto.getIntParaTipoMonedaCod());
//						impuesto.setTsImpuFechaPresentacionD(listImpuesto.getTsImpuFechaPresentacionD());
//						impuesto.setStrImpuGlosa(listImpuesto.getStrImpuGlosa());
//					}
//				}
//				System.out.println("Llego aca " + impuesto);
//				setBlnShowTributacionRectificacion(true);
//			}
//		} catch (Exception e) {
//			log.info("Error en verCampos() --> "+e.getMessage());
//		}		
//	}
	
	/*geters y seters*/
	public Boolean getBlnShowTributacion() {
		return blnShowTributacion;
	}


	public void setBlnShowTributacion(Boolean blnShowTributacion) {
		this.blnShowTributacion = blnShowTributacion;
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
	public Boolean getBlnShowTributacionRectificacion() {
		return blnShowTributacionRectificacion;
	}
	public void setBlnShowTributacionRectificacion(
			Boolean blnShowTributacionRectificacion) {
		this.blnShowTributacionRectificacion = blnShowTributacionRectificacion;
	}
	public Impuesto getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}
	public Integer getIntTipoTarifa() {
		return intTipoTarifa;
	}
	public void setIntTipoTarifa(Integer intTipoTarifa) {
		this.intTipoTarifa = intTipoTarifa;
	}
	public List<Tabla> getListaTipoTarifa() {
		return listaTipoTarifa;
	}
	public void setListaTipoTarifa(List<Tabla> listaTipoTarifa) {
		this.listaTipoTarifa = listaTipoTarifa;
	}
//	JCHAVEZ - Observacion
//	public List<Impuesto> getListaPersonaJuri() {
//		return listaPersonaJuri;
//	}
//
//	public void setListaPersonaJuri(List<Impuesto> listaPersonaJuri) {
//		this.listaPersonaJuri = listaPersonaJuri;
//	}

	public Integer getIntMes() {
		return intMes;
	}

	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}

	public List<Tabla> getListaMes() {
		return listaMes;
	}

	public void setListaMes(List<Tabla> listaMes) {
		this.listaMes = listaMes;
	}


	public List<Tabla> getlistActivo() {
		return listActivo;
	}

	public void setlistActivo(List<Tabla> listActivo) {
		this.listActivo = listActivo;
	}

	public String getStrEstado() {
		return strEstado;
	}

	public void setStrEstado(String strEstado) {
		this.strEstado = strEstado;
	}

	public String getDaFechaReg() {
		return daFechaReg;
	}

	public void setDaFechaReg(String daFechaReg) {
		this.daFechaReg = daFechaReg;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		TributacionController.log = log;
	}

	public List<Tabla> getListActivo() {
		return listActivo;
	}

	public void setListActivo(List<Tabla> listActivo) {
		this.listActivo = listActivo;
	}

	public List<Persona> getListPersona() {
		return listPersona;
	}

	public void setListPersona(List<Persona> listPersona) {
		this.listPersona = listPersona;
	}

	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}

	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}

	public int getCantidadAñosLista() {
		return cantidadAñosLista;
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

	public Integer getIntCodigoPersona() {
		return intCodigoPersona;
	}

	public void setIntCodigoPersona(Integer intCodigoPersona) {
		this.intCodigoPersona = intCodigoPersona;
	}

	public String getStrEntidad() {
		return strEntidad;
	}

	public void setStrEntidad(String strEntidad) {
		this.strEntidad = strEntidad;
	}

	public String getStrTipo() {
		return strTipo;
	}

	public void setStrTipo(String strTipo) {
		this.strTipo = strTipo;
	}

	public String getStrPersEncargado() {
		return strPersEncargado;
	}

	public void setStrPersEncargado(String strPersEncargado) {
		this.strPersEncargado = strPersEncargado;
	}

	public String getStrImporte() {
		return strImporte;
	}

	public void setStrImporte(String strImporte) {
		this.strImporte = strImporte;
	}

	public String getStrTipoMoneda() {
		return strTipoMoneda;
	}

	public void setStrTipoMoneda(String strTipoMoneda) {
		this.strTipoMoneda = strTipoMoneda;
	}

	public String getStrGlosa() {
		return strGlosa;
	}

	public void setStrGlosa(String strGlosa) {
		this.strGlosa = strGlosa;
	}

	public Timestamp getTsfechas() {
		return tsfechas;
	}

	public void setTsfechas(Timestamp tsfechas) {
		this.tsfechas = tsfechas;
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

	public Integer getIntPeriodo() {
		return intPeriodo;
	}

	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}

	public Integer getIntEstadoCodCab() {
		return intEstadoCodCab;
	}

	public void setIntEstadoCodCab(Integer intEstadoCodCab) {
		this.intEstadoCodCab = intEstadoCodCab;
	}

	public Integer getIntPago() {
		return intPago;
	}

	public void setIntPago(Integer intPago) {
		this.intPago = intPago;
	}

	public List<Impuesto> getListaImpuesto() {
		return listaImpuesto;
	}

	public void setListaImpuesto(List<Impuesto> listaImpuesto) {
		this.listaImpuesto = listaImpuesto;
	}

	public List<Tabla> getListTipoRect() {
		return listTipoRect;
	}

	public void setListTipoRect(List<Tabla> listTipoRect) {
		this.listTipoRect = listTipoRect;
	}

//	public Impuesto getImpuestoRect() {
//		return impuestoRect;
//	}
//
//	public void setImpuestoRect(Impuesto impuestoRect) {
//		this.impuestoRect = impuestoRect;
//	}

	public List<Tabla> getListTipoMoneda() {
		return listTipoMoneda;
	}

	public void setListTipoMoneda(List<Tabla> listTipoMoneda) {
		this.listTipoMoneda = listTipoMoneda;
	}

	public List<Tabla> getListTipoPersona() {
		return listTipoPersona;
	}

	public void setListTipoPersona(List<Tabla> listTipoPersona) {
		this.listTipoPersona = listTipoPersona;
	}

	public List<SelectItem> getCboFiltroPersona1() {
		return cboFiltroPersona1;
	}

	public void setCboFiltroPersona1(List<SelectItem> cboFiltroPersona1) {
		this.cboFiltroPersona1 = cboFiltroPersona1;
	}

	public List<Tabla> getListTabla() {
		return listTabla;
	}

	public void setListTabla(List<Tabla> listTabla) {
		this.listTabla = listTabla;
	}

	public ImpuestoFacadeLocal getImpuestoFacade() {
		return impuestoFacade;
	}

	public void setImpuestoFacade(ImpuestoFacadeLocal impuestoFacade) {
		this.impuestoFacade = impuestoFacade;
	}

	public Impuesto getImpuestoTemp() {
		return impuestoTemp;
	}

	public void setImpuestoTemp(Impuesto impuestoTemp) {
		this.impuestoTemp = impuestoTemp;
	}

	public List<Impuesto> getLista() {
		return lista;
	}

	public void setLista(List<Impuesto> lista) {
		this.lista = lista;
	}

	public Boolean getBlnShowTributacionBloqueado() {
		return blnShowTributacionBloqueado;
	}

	public void setBlnShowTributacionBloqueado(Boolean blnShowTributacionBloqueado) {
		this.blnShowTributacionBloqueado = blnShowTributacionBloqueado;
	}

	public Boolean getBlnShowTributacionRectificacionBloqueado() {
		return blnShowTributacionRectificacionBloqueado;
	}

	public void setBlnShowTributacionRectificacionBloqueado(
			Boolean blnShowTributacionRectificacionBloqueado) {
		this.blnShowTributacionRectificacionBloqueado = blnShowTributacionRectificacionBloqueado;
	}

	public String getStrjuriRazonSocial() {
		return strjuriRazonSocial;
	}

	public void setStrjuriRazonSocial(String strjuriRazonSocial) {
		this.strjuriRazonSocial = strjuriRazonSocial;
	}

	public String getStrNombreCompleto() {
		return strNombreCompleto;
	}

	public void setStrNombreCompleto(String strNombreCompleto) {
		this.strNombreCompleto = strNombreCompleto;
	}

	//Autor: jchavez / Tarea: Creación / Fecha: 08.08.2014 / 
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
	public List<Tabla> getListaAnios() {
		return listaAnios;
	}
	public void setListaAnios(List<Tabla> listaAnios) {
		this.listaAnios = listaAnios;
	}
	public List<Impuesto> getListaBusqImpuesto() {
		return listaBusqImpuesto;
	}
	public void setListaBusqImpuesto(List<Impuesto> listaBusqImpuesto) {
		this.listaBusqImpuesto = listaBusqImpuesto;
	}
	public List<Impuesto> getListaPersonaJuri() {
		return listaPersonaJuri;
	}
	public void setListaPersonaJuri(List<Impuesto> listaPersonaJuri) {
		this.listaPersonaJuri = listaPersonaJuri;
	}
	public List<Impuesto> getListaPersonal() {
		return listaPersonal;
	}
	public void setListaPersonal(List<Impuesto> listaPersonal) {
		this.listaPersonal = listaPersonal;
	}
	public Boolean getBlnShowPanelInferior() {
		return blnShowPanelInferior;
	}
	public void setBlnShowPanelInferior(Boolean blnShowPanelInferior) {
		this.blnShowPanelInferior = blnShowPanelInferior;
	}
	public Boolean getBlnVisible() {
		return blnVisible;
	}
	public void setBlnVisible(Boolean blnVisible) {
		this.blnVisible = blnVisible;
	}
	public Integer getIntCodigoPersonal() {
		return intCodigoPersonal;
	}
	public void setIntCodigoPersonal(Integer intCodigoPersonal) {
		this.intCodigoPersonal = intCodigoPersonal;
	}
	//Autor: jchavez / Tarea: Creación / Fecha: 11.08.2014 / 
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
	//Autor: jchavez / Tarea: Creación / Fecha: 12.08.2014 / 
	public Impuesto getImpBusq() {
		return impBusq;
	}
	public void setImpBusq(Impuesto impBusq) {
		this.impBusq = impBusq;
	}
	public Boolean getBlnModificar() {
		return blnModificar;
	}
	public void setBlnModificar(Boolean blnModificar) {
		this.blnModificar = blnModificar;
	}
	//Autor: jchavez / Tarea: Creación / Fecha: 18.08.2014 / 
	public Impuesto getImpRect() {
		return impRect;
	}
	public void setImpRect(Impuesto impRect) {
		this.impRect = impRect;
	}
	public Impuesto getImpuestoDeLaRectificatoria() {
		return impuestoDeLaRectificatoria;
	}
	public void setImpuestoDeLaRectificatoria(Impuesto impuestoDeLaRectificatoria) {
		this.impuestoDeLaRectificatoria = impuestoDeLaRectificatoria;
	}
}