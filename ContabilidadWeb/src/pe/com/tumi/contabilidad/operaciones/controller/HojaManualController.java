package pe.com.tumi.contabilidad.operaciones.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierreContabilidad.domain.CierreContabilidad;
import pe.com.tumi.contabilidad.cierreContabilidad.domain.CierreContabilidadId;
import pe.com.tumi.contabilidad.cierreContabilidad.facade.CierreContabilidadFacadeLocal;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeLocal;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManual;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalle;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalleId;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualId;
import pe.com.tumi.contabilidad.operaciones.facade.HojaManualFacadeLocal;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.message.controller.MessageController;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class HojaManualController {
	protected static Logger log = Logger.getLogger(HojaManualController.class);
	private HojaManualDetalle hojaManualDetBusq;
	private HojaManual hojaManual;
	private List<HojaManual> listHojaManual;
	private HojaManual selectedHojaManual;
	private HojaManualDetalle SelectedHojaManualDet;
	
	//variables del popup
	private HojaManualDetalle hojaManualDetalle;
	private List<Sucursal> listSucursal;
	private List<Subsucursal> listSubsucursal;
	private Boolean isMonedaExtranjera;
	
	//variables para validar el formulario
	private Boolean blnShowDivFormHojaManual;
	private Boolean isValidHojaManual;
	private Boolean blnUpdating;
	
	//propiedades que capturan atributos de sesión
	private Integer IDUSUARIO_SESION;
	private Integer IDEMPRESA_SESION;
	
	private int action;
	
	//propiedad que hace referencia al id del Menú (tabla SEGURIDAD_101.SEG_M_TRANSACCIONES)
	private Integer IDMENU_NOTASCONTABLES; 
	
	//propiedades del popup de búsqueda de Cuentas Contables Operacionales
	private ArrayList<SelectItem> cboPeriodos = null;
	private String strTituloPopupCuenta = "Selección de Cuenta Contable";
	private List<PlanCuenta> listCuentaOperacional;
	private Boolean isDisabledTxtCuentaBusq;
	private Integer intCboTipoCuentaBusq;
	private Integer intCboPeriodoBusq;
	private String strCuentaBusq;
	
	//propiedades del popup de búsqueda de Persona
	private Integer intCboTipoPersonaBusq;
	private Integer intCboFiltroPersonaBusq;
	private String strTxtPersonaBusq;
	private String strApePaterno;
	private String strApeMaterno;
	private List<Persona> listPersona;
	private List<SelectItem> cboFiltroPersona = null;
	private Boolean isDisabledTxtPersonaBusq;
	
	//Agregado por Rodolfo Villarreal
	private Integer intPeriodoCuenta;
	private String strPlop;
	private String strNumero;
	private String strNombre;
	private String strSucursal;
	private String strSubSucursal;
	private String strSerieDocumento;
	private String strNumeroDocumento;
	private String strMonedaDocumento;
	private String strOpcionDebeHaber;
	private String strMontoSoles;
	private Boolean blnShowDivFormHojaManualDet;
	private Integer intRowHojaManual;
	private CierreContabilidad cierre;
	List<CierreContabilidad> listaCierre;
	
	public HojaManualController() throws BusinessException, EJBFactoryException{
		hojaManualDetBusq = new HojaManualDetalle();
		hojaManualDetBusq.setHojaManual(new HojaManual());
		hojaManualDetBusq.getHojaManual().setId(new HojaManualId());
		cierre = new CierreContabilidad();
		cierre.setId(new CierreContabilidadId());
		cleanBeanHojaManual();
		isValidHojaManual = true;
		blnUpdating = false;
		
		Usuario usuarioSesion = (Usuario)FacesContextUtil.getRequest().getSession().getAttribute("usuario");
		IDUSUARIO_SESION = usuarioSesion.getIntPersPersonaPk();
		IDEMPRESA_SESION = usuarioSesion.getEmpresa().getIntIdEmpresa();
		
		fillCboPeriodos();
		isDisabledTxtCuentaBusq = true;
		
		recargarCboFiltroPersona();
		isDisabledTxtPersonaBusq = true;
	}
	
	public void cleanBeanHojaManual(){
		setHojaManual(new HojaManual());
		getHojaManual().setId(new HojaManualId());
		getHojaManual().setLibroDiario(new LibroDiario());
		getHojaManual().getLibroDiario().setId(new LibroDiarioId());
		setBlnUpdating(false);
	}
	
	public void searchHojaManual() throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging HojaManualController.buscarHojaManual-------------------------------------");
		busquedaHojaManual();
	}
	
	public void busquedaHojaManual() throws EJBFactoryException{
		List<HojaManual> lista = null;
		
		HojaManualFacadeLocal hojaManualFacade = (HojaManualFacadeLocal) EJBFactory.getLocal(HojaManualFacadeLocal.class);
		try {
			lista = hojaManualFacade.getListHojaManualBusqueda(getHojaManualDetBusq());
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			log.error(e);
			e.printStackTrace();
		}
		System.out.println("listHojaManual: "+lista);
		if(lista!=null)System.out.println("listHojaManual.size: "+lista.size());
		setListHojaManual(lista);
	}
	
	public void getHojaManual(ActionEvent event){
		log.info("---------------------------------Debugging HojaManualController.getHojaManual(ActionEvent event)---------------------------------");
		if(getSelectedHojaManual()!=null){
			setBlnShowDivFormHojaManual(true);
			setHojaManual(getSelectedHojaManual());
			setBlnUpdating(true);
		}
	}
	
	public void saveHojaManual(ActionEvent event) throws EJBFactoryException, BusinessException {
		log.info("-------------------------------------Debugging TipoCambioController.grabarHojaManual-------------------------------------");
		HojaManual beanHojaManual = null;
		
		if(!validarHojaManual()){
			MessageController message = (MessageController)FacesContextUtil.getSessionBean("messageController");
			message.setWarningMessage("Datos no válidos ingresados. Verifque los mensajes de error y corrija.");
			return;
		}
		
		HojaManualFacadeLocal hojaManualFacade = (HojaManualFacadeLocal) EJBFactory.getLocal(HojaManualFacadeLocal.class);
		if(getHojaManual()!=null && getHojaManual().getId()!=null){
						
			if(getHojaManual().getId().getIntPersEmpresaHojaPk()==null && getHojaManual().getId().getIntContPeriodoHoja()==null &&
					getHojaManual().getId().getIntContCodigoHoja()==null){
				//Grabar Hoja Manual
				getHojaManual().getId().setIntPersEmpresaHojaPk(Constante.PARAM_EMPRESASESION); //la Empresa en Sesion
				//Obteniendo el Periodo
				GregorianCalendar gcal = new GregorianCalendar();
				gcal.setTime(getHojaManual().getTsHomaFechaRegistro());
				String strPeriodo = gcal.get(Calendar.YEAR)+""+(gcal.get(Calendar.MONTH)+1);
				if(strPeriodo.length()<6){//Debe tener 6 caracteres
					strPeriodo = gcal.get(Calendar.YEAR)+"0"+(gcal.get(Calendar.MONTH)+1);
				}
				getHojaManual().getId().setIntContPeriodoHoja(Integer.valueOf(strPeriodo));
				//Propiedades de Sesion para LibroDiario
				getHojaManual().setLibroDiario(new LibroDiario());
				/*Agregado por Rodolfo Villarreal 15/07/2014*/
				getHojaManual().getLibroDiario().setId(new LibroDiarioId());
				getHojaManual().getLibroDiario().getId().setIntPersEmpresaLibro(IDEMPRESA_SESION);
				getHojaManual().getLibroDiario().setIntPersEmpresaUsuario(IDEMPRESA_SESION);
				getHojaManual().getLibroDiario().setIntPersPersonaUsuario(IDUSUARIO_SESION);
				try {
					//Graba los valores	
					beanHojaManual = hojaManualFacade.grabarHojaManual(getHojaManual());
						
					if(hojaManual!=null){
						FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
					}
				} catch (BusinessException e) {
					FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
					log.error(e);
				}
			}else if (getHojaManual().getId().getIntPersEmpresaHojaPk()!=null && getHojaManual().getId().getIntContPeriodoHoja()!=null &&
					getHojaManual().getId().getIntContCodigoHoja()!=null){
				//Actualizar Hoja Manual
				try {
					beanHojaManual = hojaManualFacade.modificarHojaManual(getHojaManual());
					if(hojaManual!=null){
						FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_MODIFICAR);
					}
				} catch (BusinessException e) {
					FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
					log.error(e);
				}
			}
		}
	
		busquedaHojaManual();
		setBlnShowDivFormHojaManual(false);
	}
	
	public Boolean validarHojaManual(){
		log.info("-------------------------------------Debugging HojaManualController.validarHojaManual-------------------------------------");
		isValidHojaManual = true;
		
		if(getHojaManual()!=null){
			if(getHojaManual().getTsHomaFechaRegistro()==null){
				FacesContextUtil.setMessageError("Ingrese la fecha de registro.");
				isValidHojaManual = false;
			}
			
			if(getHojaManual().getListHojaManualDetalle()==null || getHojaManual().getListHojaManualDetalle().size()<2){
				FacesContextUtil.setMessageError("Número de cuentas inválido. Deben haber dos o más cuentas por Nota Contable.");
				isValidHojaManual= false;
			}else{
				BigDecimal sumaDebe = new BigDecimal(0);
				BigDecimal sumaHaber = new BigDecimal(0);
				for(HojaManualDetalle hmde : getHojaManual().getListHojaManualDetalle()){
					if(getOpcionDebeHaber(hmde).equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
						sumaDebe = sumaDebe.add(hmde.getBdHmdeDebeSoles());
					}else if(getOpcionDebeHaber(hmde).equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
						sumaHaber = sumaHaber.add(hmde.getBdHmdeHaberSoles());
					}
				}
				System.out.println("sumaDebe: "+sumaDebe);
				System.out.println("sumaHaber: "+sumaHaber);
				if(!sumaDebe.equals(sumaHaber)){
					FacesContextUtil.setMessageError("La suma de cuentas en la columna DEBE no coincide con la suma en la columna HABER.");
					isValidHojaManual = false;
				}
			}
		}
		
		return isValidHojaManual;
	}
	
	public Integer getOpcionDebeHaber(HojaManualDetalle hmde){
		log.info("-------------------------------------Debugging HojaManualController.getOpcionDebeHaber-------------------------------------");
		Integer intOpcionDebeHaber = null;
		
		if((hmde.getBdHmdeDebeSoles()!=null || hmde.getBdHmdeDebeExtranjero()!=null) && 
				(hmde.getBdHmdeHaberSoles()==null && hmde.getBdHmdeHaberExtranjero()==null)){
			intOpcionDebeHaber = Constante.PARAM_T_OPCIONDEBEHABER_DEBE;
		}else if((hmde.getBdHmdeHaberSoles()!=null || hmde.getBdHmdeHaberExtranjero()!=null) &&
				(hmde.getBdHmdeDebeSoles()==null && hmde.getBdHmdeDebeExtranjero()==null)){
			intOpcionDebeHaber = Constante.PARAM_T_OPCIONDEBEHABER_HABER;
		}
		System.out.println("intOpcionDebeHaber: "+intOpcionDebeHaber);
		return intOpcionDebeHaber;
	}
	
	public void newHojaManual(ActionEvent event){
		setBlnShowDivFormHojaManual(true);
		cleanBeanHojaManual();
	}

	public void cancelNew(ActionEvent event){
		setBlnShowDivFormHojaManual(false);
	}
	
	public void setSelectedHojaManual(ActionEvent event){
		log.info("-------------------------------------Debugging HojaManualController.setSelectedHojaManual-------------------------------------");
		log.info("activeRowKey: "+FacesContextUtil.getRequestParameter("rowHojaManualBusq"));
		String selectedRow = FacesContextUtil.getRequestParameter("rowHojaManualBusq");
		
		if(listHojaManual!=null && listHojaManual.size()>Integer.valueOf(selectedRow)){
			setSelectedHojaManual(listHojaManual.get(Integer.valueOf(selectedRow)));
		}
	}
	
	//Metodos del popup
	
	public void newHojaManualDetalle(ActionEvent event) throws BusinessException, EJBFactoryException{
		this.action = 1; // Nuevo
		log.info("-------------------------------------Debugging HojaManualController.openHojaManualDetalle-------------------------------------");
		CierreContabilidadFacadeLocal cierreFacade = (CierreContabilidadFacadeLocal)EJBFactory.getLocal(CierreContabilidadFacadeLocal.class);
		
		//Obteniendo el Periodo
		GregorianCalendar gcal1 = new GregorianCalendar();
	if(getHojaManual().getTsHomaFechaRegistro()==null || getHojaManual().getStrHomaGlosa()==null){
		MessageController message = (MessageController)FacesContextUtil.getSessionBean("messageController");
		message.setWarningMessage("Primero debe ingresar la Fecha de Registro y la Glosa.");
		return;
	}else{
		gcal1.setTime(getHojaManual().getTsHomaFechaRegistro());
		String strPeriodo1 = gcal1.get(Calendar.YEAR)+""+(gcal1.get(Calendar.MONTH)+1);
		if(strPeriodo1.length()<6){//Debe tener 6 caracteres
			strPeriodo1 = gcal1.get(Calendar.YEAR)+"0"+(gcal1.get(Calendar.MONTH)+1);
		}
		Integer periodoCierre = Integer.valueOf(strPeriodo1);
		cierre.setId(new CierreContabilidadId());
		cierre.getId().setIntPersEmpresaCieCob(IDEMPRESA_SESION);
		cierre.getId().setIntCcobPeriodoCierre(periodoCierre);
		
		listaCierre = cierreFacade.getListaCierre(cierre);
		if(!listaCierre.isEmpty()){
				if(listaCierre.get(0).getId().getIntCcobPeriodoCierre().equals(periodoCierre) && listaCierre.get(0).getId().getIntEstadoCierreCod()==2){
					cierre.getId().setIntEstadoCierreCod(listaCierre.get(0).getId().getIntEstadoCierreCod());
					MessageController message = (MessageController)FacesContextUtil.getSessionBean("messageController");
					message.setWarningMessage("El periodo se encuentra en estado Cerrado no se puede Realizar el Registro de la Nota Contable");
					hojaManual.setTsFechaRegistroDesde(null);
					hojaManual.setStrHomaGlosa("");
					return;
			}
		}
	}
	cleanHojaManualDetalle();
}
	public void cleanHojaManualDetalle() throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging HojaManualController.openHojaManualDetalle-------------------------------------");
		hojaManualDetalle = new HojaManualDetalle();
		hojaManualDetalle.setPersona(new Persona());
		
		getHojaManualDetalle().setId(new HojaManualDetalleId());
		getHojaManualDetalle().getId().setIntPersEmpresaHojaPk(IDEMPRESA_SESION);
		
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(hojaManual.getTsHomaFechaRegistro());
		String strPeriodo = gcal.get(Calendar.YEAR)+""+(gcal.get(Calendar.MONTH)+1);
		if(strPeriodo.length()<6){//Debe tener 6 caracteres
			strPeriodo = gcal.get(Calendar.YEAR)+"0"+(gcal.get(Calendar.MONTH)+1);
		}
		getHojaManualDetalle().getId().setIntContPeriodoHoja(Integer.valueOf(strPeriodo));
		
		hojaManualDetalle.getPersona().setNatural(new Natural());
		hojaManualDetalle.getPersona().setJuridica(new Juridica());
		loadListSucursal();
		strPlop = "";
		strNumero= "";
		strNombre= "";
		strSucursal = "";
		strSubSucursal = "";
		strSerieDocumento = "";
		strNumeroDocumento = "";
		strMonedaDocumento = "";
		strOpcionDebeHaber = "";
		strMontoSoles = "";
	}
	
	public void grabarManualDetalle(ActionEvent event) {
		strPlop = "";
		strNumero= "";
		strNombre= "";
		strSucursal = "";
		strSubSucursal = "";
		strSerieDocumento = "";
		strNumeroDocumento = "";
		strMonedaDocumento = "";
		strOpcionDebeHaber = "";
		strMontoSoles = "";
		
		if(hojaManualDetalle.getPlanCuenta()==null){
			strNumero = "Ingresar Cuenta Contable";
			return;
		}
		
		if(hojaManualDetalle.getPersona().getStrEtiqueta()==null){
			strNombre= "Ingresar Persona";
			return;
		}
		
		if (hojaManualDetalle.getIntParaDocumentoGeneralCod().equals(0)) {
			strPlop = "Ingresar Tipo Documento";
			return;
		}
		if(hojaManualDetalle.getIntSucuIdSucursalPk().equals(0)){
			strSucursal = "Ingresar Sucursal";
			return;
		}
		if(hojaManualDetalle.getIntSudeIdSubsucursalPk().equals(0)){
			strSubSucursal = "Ingresar Subsucursal";
			return;
		}
		if(hojaManualDetalle.getStrHmdeSerieDocumento().isEmpty()){
			strSerieDocumento = "Ingresar Serie";
			return;
		}
		if(hojaManualDetalle.getStrHmdeNumeroDocumento().isEmpty()){
			strNumeroDocumento = "Ingresar Número";
			return;
		}
		if(hojaManualDetalle.getIntParaMonedaDocumento().equals(0)){
			strMonedaDocumento = "Ingresar Tipo de Moneda";
			return;
		}
		if(hojaManualDetalle.getBdMontoSoles()==null){
			strMontoSoles = "Ingresar Monto Soles";
		}
		if(hojaManualDetalle.getIntOpcionDebeHaber().equals(0)){
			strOpcionDebeHaber = "Ingresar Tipo";
			return;
		}
		
		//Seteando el monto si es Debe o Haber en Soles o en Dólares
		if(hojaManualDetalle.getIntOpcionDebeHaber()!=null && !hojaManualDetalle.getIntOpcionDebeHaber().equals(0)){
			if(hojaManualDetalle.getIntOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
				hojaManualDetalle.setBdHmdeDebeSoles(hojaManualDetalle.getBdMontoSoles());
			}else if(hojaManualDetalle.getIntOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
				hojaManualDetalle.setBdHmdeHaberSoles(hojaManualDetalle.getBdMontoSoles());
			}
		}
		
		switch (action) {
			case 1:
				// registrar un nuevo detalle
//				List<HojaManualDetalle> listaHojaManualDetalle = getHojaManual().getListHojaManualDetalle();
				
				if( getHojaManual().getListHojaManualDetalle() == null){
					getHojaManual().setListHojaManualDetalle(new ArrayList<HojaManualDetalle>());
				}
				
				getHojaManual().getListHojaManualDetalle().add(hojaManualDetalle);
				
				break;
				
			default:
				//
		}
		
		System.out.println("Tamaño" +getHojaManual().getListHojaManualDetalle().size());
	}
	
	public void addHojaManualDetalle(ActionEvent event){
		strPlop = "";
		strNumero = "";
		strNombre= "";
		strSucursal = "";
		strSubSucursal = "";
		strSerieDocumento = "";
		strNumeroDocumento = "";
		strMonedaDocumento = "";
		strOpcionDebeHaber = "";
		strMontoSoles = "";
			
//		blnok=true;
		log.info("-------------------------------------Debugging HojaManualController.addHojaManualDetalle-------------------------------------");
		if(hojaManualDetalle.getPlanCuenta()==null){
			strNumero = "Ingresar Cuenta Contable";
			return;
		}
		if(hojaManualDetalle.getPersona().getNatural().getStrNombres()==null && hojaManualDetalle.getPersona().getNatural().getStrApellidoPaterno()==null && hojaManualDetalle.getPersona().getNatural().getStrApellidoMaterno()==null){
			strNombre= "Ingresar Persona";
			return;
		}
		
		if (hojaManualDetalle.getIntParaDocumentoGeneralCod().equals(0)) {
			strPlop = "Ingresar Tipo Documento";
			return;
		}
		if(hojaManualDetalle.getIntSucuIdSucursalPk().equals(0)){
			strSucursal = "Ingresar Sucursal";
			return;
		}
		if(hojaManualDetalle.getIntSudeIdSubsucursalPk().equals(0)){
			strSubSucursal = "Ingresar Subsucursal";
			return;
		}
		if(hojaManualDetalle.getStrHmdeSerieDocumento().isEmpty()){
			strSerieDocumento = "Ingresar Serie";
			return;
		}
		if(hojaManualDetalle.getStrHmdeNumeroDocumento().isEmpty()){
			strNumeroDocumento = "Ingresar Número";
			return;
		}
		if(hojaManualDetalle.getIntParaMonedaDocumento().equals(0)){
			strMonedaDocumento = "Ingresar Tipo de Moneda";
			return;
		}
		if(hojaManualDetalle.getBdMontoSoles()==null){
			strMontoSoles = "Ingresar Monto Soles";
		}
		if(hojaManualDetalle.getIntOpcionDebeHaber().equals(0)){
			strOpcionDebeHaber = "Ingresar Tipo";
			return;
		}
		
		if(getHojaManual().getListHojaManualDetalle()==null){
			getHojaManual().setListHojaManualDetalle(new ArrayList<HojaManualDetalle>());
		}
		
		if(getHojaManualDetalle()!=null){
			getHojaManualDetalle().setId(new HojaManualDetalleId());
			getHojaManualDetalle().getId().setIntPersEmpresaHojaPk(IDEMPRESA_SESION);
			//Obteniendo el Periodo
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.setTime(hojaManual.getTsHomaFechaRegistro());
			String strPeriodo = gcal.get(Calendar.YEAR)+""+(gcal.get(Calendar.MONTH)+1);
			if(strPeriodo.length()<6){//Debe tener 6 caracteres
				strPeriodo = gcal.get(Calendar.YEAR)+"0"+(gcal.get(Calendar.MONTH)+1);
			}
			getHojaManualDetalle().getId().setIntContPeriodoHoja(Integer.valueOf(strPeriodo));
			
			//Seteando el monto si es Debe o Haber en Soles o en Dólares
			if(hojaManualDetalle.getIntOpcionDebeHaber()!=null && !hojaManualDetalle.getIntOpcionDebeHaber().equals(0)){
				if(hojaManualDetalle.getIntOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
					hojaManualDetalle.setBdHmdeDebeSoles(hojaManualDetalle.getBdMontoSoles());
				}else if(hojaManualDetalle.getIntOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER)){
					hojaManualDetalle.setBdHmdeHaberSoles(hojaManualDetalle.getBdMontoSoles());
				}
			}
		}
		
		if (getHojaManualDetalle().getId() == null)
			getHojaManual().getListHojaManualDetalle().add(getHojaManualDetalle());
	}
	
	public void loadListSucursal() throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging HojaManualController.loadListSucursal-------------------------------------");
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
		List<Sucursal> listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(IDEMPRESA_SESION);
		setListSucursal(listaSucursal);
	}
	
	public void reloadListSubsucursal(ActionEvent event) throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging HojaManualController.reloadListSubsucursal-------------------------------------");
		String strIdSucursal = FacesContextUtil.getRequestParameter("pIdSucursal");
		System.out.println("strIdSucursal: "+strIdSucursal);
		EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
		List<Subsucursal> listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursalYestado(Integer.valueOf(strIdSucursal), Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		setListSubsucursal(listaSubsucursal);
	}
	
	public void seleccionarCuenta(ActionEvent event){
		log.info("-----------------------Debugging HojaManualController.seleccionarCuenta()-----------------------------");
		String strCuenta = (String) FacesContextUtil.getRequestParameter("pRowCuentaContable");
		log.info("strCuenta: "+strCuenta);
		hojaManualDetalle.setPlanCuenta(listCuentaOperacional.get(Integer.valueOf(strCuenta)));
	}
	
	public void renderPorTipoMoneda(ActionEvent event){
		log.info("-----------------------Debugging HojaManualController.renderPorTipoMoneda()-----------------------------");
		String pIdTipoMoneda = (String) FacesContextUtil.getRequestParameter("pIdTipoMoneda");
		log.info("pIdTipoMoneda: "+pIdTipoMoneda);
		
		if(pIdTipoMoneda==null || Integer.valueOf(pIdTipoMoneda).equals(0) ||
				Integer.valueOf(pIdTipoMoneda).equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			setIsMonedaExtranjera(false);
		}else if(Integer.valueOf(pIdTipoMoneda).equals(Constante.PARAM_T_TIPOMONEDA_DOLARES)||
				Integer.valueOf(pIdTipoMoneda).equals(Constante.PARAM_T_TIPOMONEDA_EXTRANJERA)){
			setIsMonedaExtranjera(true);
		}
		
		hojaManualDetalle.setIntParaMonedaDocumento(Integer.valueOf(pIdTipoMoneda));
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------
	//Métodos para la busqueda de Cuentas Contables Operacionales
	//--------------------------------------------------------------------------------------------------------------------------------------------
	public void disableTxtCuentaBusq(ActionEvent event){
		log.info("-------------------------------------Debugging HojaManualController.disableTxtCuentaBusq-------------------------------------");
		log.info("pCboTipoCuentaBusq: "+FacesContextUtil.getRequestParameter("pCboTipoCuentaBusq"));
		String pFiltroCombo = FacesContextUtil.getRequestParameter("pCboTipoCuentaBusq");
		if(pFiltroCombo.equals("0")){
			setIsDisabledTxtCuentaBusq(true);
		}else{
			setIsDisabledTxtCuentaBusq(false);
		}
	}
	
	public void searchCuentaContable(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging HojaManualController.buscarCuentaOrigenDestino-------------------------------------");
		setListCuentaOperacional(buscarCuentaContable());
	}
	
	public List<PlanCuenta> buscarCuentaContable() throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging HojaManualController.buscarCuentaOrigenDestino-------------------------------------");
		PlanCuenta beanCuentaBusq = new PlanCuenta();
		List<PlanCuenta> listPlanCuenta = new ArrayList<PlanCuenta>();
		
		beanCuentaBusq.setId(new PlanCuentaId());
		//Filtro combo para Descripcion, Numero de Cuenta
		if(getIntCboTipoCuentaBusq()!=null && getIntCboTipoCuentaBusq().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION)){//Por Descripción
			beanCuentaBusq.setStrDescripcion(getStrCuentaBusq());
		}else if(getIntCboTipoCuentaBusq()!=null && getIntCboTipoCuentaBusq().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_CUENTACONTABLE)){//Por Número de Cuenta
			beanCuentaBusq.getId().setStrNumeroCuenta(getStrCuentaBusq());
		}
		
		//Filtrar por el Período
//		GregorianCalendar gcal = new GregorianCalendar();
//		gcal.setTime(getHojaManual().getTsHomaFechaRegistro());
		if(getIntCboPeriodoBusq()!=null && !getIntCboPeriodoBusq().equals(0))beanCuentaBusq.getId().setIntPeriodoCuenta(getIntCboPeriodoBusq());
		
		//Obtener sólo los planes de cuenta operacionales
		beanCuentaBusq.setIntMovimiento(Constante.TABLA_PLANCUENTA_MOVIMIENTO_OPERACIONAL);
		
		List<PlanCuenta> lista = null;
		PlanCuentaFacadeLocal planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
		lista = planCuentaFacade.getListaPlanCuentaBusqueda(beanCuentaBusq);
		//Agregado Por Rodolfo Villarreal Acuña fecha 22/07/2014
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(getHojaManual().getTsHomaFechaRegistro());
		int strPeriodo = gcal.get(Calendar.YEAR);
		
		for (PlanCuenta planCuenta : lista) {
			if(planCuenta.getId().getIntPeriodoCuenta().equals(strPeriodo)){
				listPlanCuenta.add(planCuenta);
			}
		}
		System.out.println("listCuentaOrigenDestino.size: "+listPlanCuenta.size());
		return listPlanCuenta;
	}
	
	public void limpiarCuenta(){
		listCuentaOperacional = new ArrayList<PlanCuenta>();
		intCboTipoCuentaBusq = 0;
		intCboPeriodoBusq = 0;
		strCuentaBusq ="";
	}
	
	public void limpiarPersona(){
		listPersona = new ArrayList<Persona>();
		intCboTipoPersonaBusq = 0;
		cboFiltroPersona = new ArrayList<SelectItem>();
		setStrTxtPersonaBusq("");
	}
	
	public void fillCboPeriodos() throws EJBFactoryException, BusinessException{
		log.info("-----------------------Debugging HojaManualController.getCboPeriodos()-----------------------------");
		ArrayList<SelectItem> listPeriodos = new ArrayList<SelectItem>();
		if(this.cboPeriodos!=null)this.cboPeriodos.clear();
		PlanCuentaFacadeLocal planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
		List<Integer> periodos = planCuentaFacade.getListaPeriodos();
		log.info("periodos.size: "+(periodos!=null?periodos.size():null));
		GregorianCalendar calendar = new GregorianCalendar();
		Boolean tieneAnioActual = false;;
		for(int i=0; i<periodos.size(); i++){
			if(periodos.get(i).equals(calendar.get(Calendar.YEAR))){
				tieneAnioActual = true;
			}
			listPeriodos.add(0, new SelectItem(periodos.get(i), ""+periodos.get(i)));
		}
		if(!tieneAnioActual){
			if(listPeriodos.size()>0){
				listPeriodos.add(0, new SelectItem(calendar.get(Calendar.YEAR), ""+calendar.get(Calendar.YEAR)));
			}else{
				listPeriodos.add(new SelectItem(calendar.get(Calendar.YEAR), ""+calendar.get(Calendar.YEAR)));
			}
		}
		this.cboPeriodos = listPeriodos;
		this.cboPeriodos.add(0, new SelectItem(0, "Seleccione.."));
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------
	//Métodos para la busqueda de Personas
	//--------------------------------------------------------------------------------------------------------------------------------------------
	public void disableTxtPersonaBusq(ActionEvent event){
		log.info("-------------------------------------Debugging HojaManualController.disableTxtPersonaBusq-------------------------------------");
		log.info("pFiltroPersonaBusq: "+FacesContextUtil.getRequestParameter("pFiltroPersonaBusq"));
		String pFiltroCombo = FacesContextUtil.getRequestParameter("pFiltroPersonaBusq");
		if(pFiltroCombo.equals("0")){
			setIsDisabledTxtPersonaBusq(true);
		}else{
			setIsDisabledTxtPersonaBusq(false);
		}
	}
	
	public void searchPersona(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging HojaManualController.searchPersona-------------------------------------");
		setListPersona(buscarPersona());
	}
	
	public List<Persona> buscarPersona() throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging HojaManualController.buscarPersona-------------------------------------");
		Persona beanPersonaBusq = new Persona();
		List<Persona> listPersona = null;
		//Filtro combo para Descripcion, Numero de Cuenta
		if(getIntCboTipoPersonaBusq()!=null && !getIntCboTipoPersonaBusq().equals(0)){
			beanPersonaBusq.setIntTipoPersonaCod(getIntCboTipoPersonaBusq());
			if(getIntCboTipoPersonaBusq().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				beanPersonaBusq.setNatural(new Natural());
				if(getIntCboFiltroPersonaBusq()!=null && !getIntCboFiltroPersonaBusq().equals(0)){
					if(getIntCboFiltroPersonaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
						beanPersonaBusq.setStrRoles(getStrTxtPersonaBusq());
						beanPersonaBusq.setIntEstadoCod(getIntCboFiltroPersonaBusq());
					}else if(getIntCboFiltroPersonaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
						beanPersonaBusq.setDocumento(new Documento());
						beanPersonaBusq.getDocumento().setIntTipoIdentidadCod(Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI));
						beanPersonaBusq.setStrRoles(getStrTxtPersonaBusq());
						beanPersonaBusq.setIntEstadoCod(getIntCboFiltroPersonaBusq());
					}
				}
				PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
//				listPersona = personaFacade.getListPerNaturalBusqueda(beanPersonaBusq);
				
				listPersona = personaFacade.getBuscarNombrYdni(beanPersonaBusq);
				
			}else if(getIntCboTipoPersonaBusq().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				beanPersonaBusq.setJuridica(new Juridica());
				if(getIntCboFiltroPersonaBusq()!=null && !getIntCboFiltroPersonaBusq().equals(0)){
					if(getIntCboFiltroPersonaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
						beanPersonaBusq.setStrRoles(getStrTxtPersonaBusq());
						beanPersonaBusq.setIntEstadoCod(3);
					}else if(getIntCboFiltroPersonaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)){
						beanPersonaBusq.setStrRoles(getStrTxtPersonaBusq());
						beanPersonaBusq.setIntEstadoCod(getIntCboFiltroPersonaBusq());
					}
				}
				PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
//				listPersona = personaFacade.getBusquedaPerJuridicaSinSucursales(beanPersonaBusq);
				listPersona = personaFacade.getBuscarNombrYdni(beanPersonaBusq);
			}
		}
		
		return listPersona;
	}
	
	public void reloadCboFiltroPersona(ActionEvent event) throws EJBFactoryException, NumberFormatException, BusinessException{
		recargarCboFiltroPersona();
	}
	
	public void recargarCboFiltroPersona() throws EJBFactoryException, NumberFormatException, BusinessException{
		String pIntTipoPersona = FacesContextUtil.getRequestParameter("pTipoPersona");
		System.out.println("pIntTipoPersona: "+pIntTipoPersona);
		cboFiltroPersona = new ArrayList<SelectItem>();
		if(pIntTipoPersona==null){
			return;
		}
		Integer intTipoPersona = Integer.valueOf(pIntTipoPersona);
		TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
		List<Tabla> listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_OPCIONPERSONABUSQUEDA));
		
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
	}
	
	public void seleccionarPersona(ActionEvent event){
		log.info("-----------------------Debugging HojaManualController.seleccionarPersona()-----------------------------");
		String strIdPersona = (String) FacesContextUtil.getRequestParameter("pRowPersona");
		log.info("strIdPersona: "+strIdPersona);
		
		hojaManualDetalle.setPersona(listPersona.get(Integer.valueOf(strIdPersona)));
	}

	//Autor Rodolfo Villarreal Acuña fecha 17/07/2014   intRowHojaManual
	public void setSelectedHojaManualDet(ActionEvent event){
		String selectedRow = FacesContextUtil.getRequestParameter("rowHojaManualDetalle");
			if(hojaManual.getListHojaManualDetalle().size()>Integer.valueOf(selectedRow)){
				setSelectedHojaManualDet(hojaManual.getListHojaManualDetalle().get(Integer.valueOf(selectedRow)));
			}
			setIntRowHojaManual(Integer.valueOf(selectedRow.trim()));
	}
	
	public void getHojaManualDetalle(ActionEvent event) throws BusinessException{
		EmpresaFacadeRemote empresaFacade;
		try {
			
			this.action = 2; //Modificar;
			
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			List<Sucursal> listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(IDEMPRESA_SESION);
			setListSucursal(listaSucursal);
			
			List<Subsucursal> listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursalYestado(getSelectedHojaManualDet().getIntSucuIdSucursalPk(), Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			setListSubsucursal(listaSubsucursal);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
		
		if(getSelectedHojaManualDet()!=null){
			if(getSelectedHojaManualDet().getBdHmdeDebeSoles()!=null){
				getSelectedHojaManualDet().setBdMontoSoles(getSelectedHojaManualDet().getBdHmdeDebeSoles());
				getSelectedHojaManualDet().setIntOpcionDebeHaber(Constante.PARAM_T_OPCIONDEBEHABER_DEBE);
			}
			if(getSelectedHojaManualDet().getBdHmdeHaberSoles()!=null){
				getSelectedHojaManualDet().setBdMontoSoles(getSelectedHojaManualDet().getBdHmdeHaberSoles());
				getSelectedHojaManualDet().setIntOpcionDebeHaber(Constante.PARAM_T_OPCIONDEBEHABER_HABER);
			}
			setBlnShowDivFormHojaManualDet(true);
			setHojaManualDetalle(getSelectedHojaManualDet());
			setBlnUpdating(true);
			strPlop = "";
			strNumero= "";
			strNombre= "";
			strSucursal = "";
			strSubSucursal = "";
			strSerieDocumento = "";
			strNumeroDocumento = "";
			strMonedaDocumento = "";
			strOpcionDebeHaber = "";
			strMontoSoles = "";
		}
	}
	
	
	public void onConfirmDeleteModeloDet(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.onConfirmDeleteModeloDet-------------------------------------");
		MessageController message = (MessageController)getSessionBean("messageController");
		message.setWarningMessage("¿Esta seguro de eliminar el registro?");
		message.setStrFunctionAccept("acceptMessage()");
	}
	//UTILITARIOS
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	//beanModelo.getListModeloDetalle().get(intRowModeloDetalle).setIsDeleted(true);
	public void deleteHojaManualDetalle(ActionEvent event){
		if(hojaManual!=null && hojaManual.getListHojaManualDetalle()!=null && hojaManual.getListHojaManualDetalle().size()>0){
			hojaManual.getListHojaManualDetalle().get(intRowHojaManual).setIsDeleted(true);
			setHojaManualDetalle(null);
			setIntRowHojaManual(null);
			
			for (HojaManualDetalle hojaManual1: hojaManual.getListHojaManualDetalle()) {
				log.info(hojaManual1.getIsDeleted()+ " "+hojaManual1);
			}
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------
	//Getters && Setters
	//--------------------------------------------------------------------------------------------------------------------------------------------
	public HojaManualDetalle getHojaManualDetBusq() {
		return hojaManualDetBusq;
	}
	public void setHojaManualDetBusq(HojaManualDetalle hojaManualDetBusq) {
		this.hojaManualDetBusq = hojaManualDetBusq;
	}
	public HojaManual getHojaManual() {
		return hojaManual;
	}
	public void setHojaManual(HojaManual hojaManual) {
		this.hojaManual = hojaManual;
	}
	public Boolean getBlnUpdating() {
		return blnUpdating;
	}
	public void setBlnUpdating(Boolean blnUpdating) {
		this.blnUpdating = blnUpdating;
	}
	public Boolean getIsValidHojaManual() {
		return isValidHojaManual;
	}
	public void setIsValidHojaManual(Boolean isValidHojaManual) {
		this.isValidHojaManual = isValidHojaManual;
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
	public Integer getIDMENU_NOTASCONTABLES() {
		return IDMENU_NOTASCONTABLES;
	}
	public void setIDMENU_NOTASCONTABLES(Integer iDMENU_NOTASCONTABLES) {
		IDMENU_NOTASCONTABLES = iDMENU_NOTASCONTABLES;
	}
	public List<HojaManual> getListHojaManual() {
		return listHojaManual;
	}
	public void setListHojaManual(List<HojaManual> listHojaManual) {
		this.listHojaManual = listHojaManual;
	}
	public HojaManual getSelectedHojaManual() {
		return selectedHojaManual;
	}
	public void setSelectedHojaManual(HojaManual selectedHojaManual) {
		this.selectedHojaManual = selectedHojaManual;
	}
	public Boolean getBlnShowDivFormHojaManual() {
		return blnShowDivFormHojaManual;
	}
	public void setBlnShowDivFormHojaManual(Boolean blnShowDivFormHojaManual) {
		this.blnShowDivFormHojaManual = blnShowDivFormHojaManual;
	}
	public HojaManualDetalle getHojaManualDetalle() {
		return hojaManualDetalle;
	}
	public void setHojaManualDetalle(HojaManualDetalle hojaManualDetalle) {
		this.hojaManualDetalle = hojaManualDetalle;
	}
	public List<Sucursal> getListSucursal() {
		return listSucursal;
	}
	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}
	public List<Subsucursal> getListSubsucursal() {
		return listSubsucursal;
	}
	public void setListSubsucursal(List<Subsucursal> listSubsucursal) {
		this.listSubsucursal = listSubsucursal;
	}
	public Boolean getIsDisabledTxtCuentaBusq() {
		return isDisabledTxtCuentaBusq;
	}
	public void setIsDisabledTxtCuentaBusq(Boolean isDisabledTxtCuentaBusq) {
		this.isDisabledTxtCuentaBusq = isDisabledTxtCuentaBusq;
	}
	public List<PlanCuenta> getListCuentaOperacional() {
		return listCuentaOperacional;
	}
	public void setListCuentaOperacional(List<PlanCuenta> listCuentaOperacional) {
		this.listCuentaOperacional = listCuentaOperacional;
	}
	public Integer getIntCboTipoCuentaBusq() {
		return intCboTipoCuentaBusq;
	}
	public void setIntCboTipoCuentaBusq(Integer intCboTipoCuentaBusq) {
		this.intCboTipoCuentaBusq = intCboTipoCuentaBusq;
	}
	public String getStrCuentaBusq() {
		return strCuentaBusq;
	}
	public void setStrCuentaBusq(String strCuentaBusq) {
		this.strCuentaBusq = strCuentaBusq;
	}
	public Integer getIntCboPeriodoBusq() {
		return intCboPeriodoBusq;
	}
	public void setIntCboPeriodoBusq(Integer intCboPeriodoBusq) {
		this.intCboPeriodoBusq = intCboPeriodoBusq;
	}
	public ArrayList<SelectItem> getCboPeriodos() {
		return cboPeriodos;
	}
	public void setCboPeriodos(ArrayList<SelectItem> cboPeriodos) {
		this.cboPeriodos = cboPeriodos;
	}
	public String getStrTituloPopupCuenta() {
		return strTituloPopupCuenta;
	}
	public void setStrTituloPopupCuenta(String strTituloPopupCuenta) {
		this.strTituloPopupCuenta = strTituloPopupCuenta;
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
	public String getStrTxtPersonaBusq() {
		return strTxtPersonaBusq;
	}
	public void setStrTxtPersonaBusq(String strTxtPersonaBusq) {
		this.strTxtPersonaBusq = strTxtPersonaBusq;
	}
	public List<Persona> getListPersona() {
		return listPersona;
	}
	public void setListPersona(List<Persona> listPersona) {
		this.listPersona = listPersona;
	}
	public List<SelectItem> getCboFiltroPersona() {
		return cboFiltroPersona;
	}
	public void setCboFiltroPersona(List<SelectItem> cboFiltroPersona) {
		this.cboFiltroPersona = cboFiltroPersona;
	}
	public Boolean getIsDisabledTxtPersonaBusq() {
		return isDisabledTxtPersonaBusq;
	}
	public void setIsDisabledTxtPersonaBusq(Boolean isDisabledTxtPersonaBusq) {
		this.isDisabledTxtPersonaBusq = isDisabledTxtPersonaBusq;
	}
	public Boolean getIsMonedaExtranjera() {
		return isMonedaExtranjera;
	}
	public void setIsMonedaExtranjera(Boolean isMonedaExtranjera) {
		this.isMonedaExtranjera = isMonedaExtranjera;
	}
//Agregado por Rodolfo Villarreal
	public Integer getIntPeriodoCuenta() {
		return intPeriodoCuenta;
	}

	public void setIntPeriodoCuenta(Integer intPeriodoCuenta) {
		this.intPeriodoCuenta = intPeriodoCuenta;
	}

	public String getStrApePaterno() {
		return strApePaterno;
	}

	public void setStrApePaterno(String strApePaterno) {
		this.strApePaterno = strApePaterno;
	}

	public String getStrApeMaterno() {
		return strApeMaterno;
	}

	public void setStrApeMaterno(String strApeMaterno) {
		this.strApeMaterno = strApeMaterno;
	}

	public String getStrPlop() {
		return strPlop;
	}

	public void setStrPlop(String strPlop) {
		this.strPlop = strPlop;
	}

	public String getStrNumero() {
		return strNumero;
	}

	public void setStrNumero(String strNumero) {
		this.strNumero = strNumero;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}

	public String getStrSucursal() {
		return strSucursal;
	}

	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}

	public String getStrSubSucursal() {
		return strSubSucursal;
	}

	public void setStrSubSucursal(String strSubSucursal) {
		this.strSubSucursal = strSubSucursal;
	}

	public String getStrSerieDocumento() {
		return strSerieDocumento;
	}

	public void setStrSerieDocumento(String strSerieDocumento) {
		this.strSerieDocumento = strSerieDocumento;
	}

	public String getStrNumeroDocumento() {
		return strNumeroDocumento;
	}

	public void setStrNumeroDocumento(String strNumeroDocumento) {
		this.strNumeroDocumento = strNumeroDocumento;
	}

	public String getStrMonedaDocumento() {
		return strMonedaDocumento;
	}

	public void setStrMonedaDocumento(String strMonedaDocumento) {
		this.strMonedaDocumento = strMonedaDocumento;
	}

	public String getStrOpcionDebeHaber() {
		return strOpcionDebeHaber;
	}

	public void setStrOpcionDebeHaber(String strOpcionDebeHaber) {
		this.strOpcionDebeHaber = strOpcionDebeHaber;
	}

	public String getStrMontoSoles() {
		return strMontoSoles;
	}

	public void setStrMontoSoles(String strMontoSoles) {
		this.strMontoSoles = strMontoSoles;
	}

	public HojaManualDetalle getSelectedHojaManualDet() {
		return SelectedHojaManualDet;
	}

	public void setSelectedHojaManualDet(HojaManualDetalle selectedHojaManualDet) {
		SelectedHojaManualDet = selectedHojaManualDet;
	}

	public Boolean getBlnShowDivFormHojaManualDet() {
		return blnShowDivFormHojaManualDet;
	}

	public void setBlnShowDivFormHojaManualDet(Boolean blnShowDivFormHojaManualDet) {
		this.blnShowDivFormHojaManualDet = blnShowDivFormHojaManualDet;
	}

	public Integer getIntRowHojaManual() {
		return intRowHojaManual;
	}

	public void setIntRowHojaManual(Integer intRowHojaManual) {
		this.intRowHojaManual = intRowHojaManual;
	}

	public CierreContabilidad getCierre() {
		return cierre;
	}

	public void setCierre(CierreContabilidad cierre) {
		this.cierre = cierre;
	}

	public List<CierreContabilidad> getListaCierre() {
		return listaCierre;
	}

	public void setListaCierre(List<CierreContabilidad> listaCierre) {
		this.listaCierre = listaCierre;
	}
}
