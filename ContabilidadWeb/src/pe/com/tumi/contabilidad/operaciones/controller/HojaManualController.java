package pe.com.tumi.contabilidad.operaciones.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
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
	private List<Persona> listPersona;
	private List<SelectItem> cboFiltroPersona = null;
	private Boolean isDisabledTxtPersonaBusq;
	
	public HojaManualController() throws BusinessException, EJBFactoryException{
		hojaManualDetBusq = new HojaManualDetalle();
		hojaManualDetBusq.setHojaManual(new HojaManual());
		hojaManualDetBusq.getHojaManual().setId(new HojaManualId());
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
	
	public void searchHojaManual(ActionEvent event) throws EJBFactoryException, BusinessException{
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
	
	public void saveHojaManual(ActionEvent event) throws EJBFactoryException {
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
				getHojaManual().getLibroDiario().getId().setIntPersEmpresaLibro(Constante.PARAM_EMPRESASESION);
				getHojaManual().getLibroDiario().setIntPersEmpresaUsuario(Constante.PARAM_EMPRESASESION);
				getHojaManual().getLibroDiario().setIntPersPersonaUsuario(Constante.PARAM_USUARIOSESION);
				try {
					beanHojaManual = hojaManualFacade.grabarHojaManual(getHojaManual());
				} catch (BusinessException e) {
					FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
					log.error(e);
				}
			}else if (getHojaManual().getId().getIntPersEmpresaHojaPk()!=null && getHojaManual().getId().getIntContPeriodoHoja()!=null &&
					getHojaManual().getId().getIntContCodigoHoja()!=null){
				//Actualizar Hoja Manual
				try {
					beanHojaManual = hojaManualFacade.modificarHojaManual(getHojaManual());
				} catch (BusinessException e) {
					FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
					log.error(e);
				}
			}
		}
		
		if(hojaManual!=null){
			FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
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
		log.info("-------------------------------------Debugging HojaManualController.openHojaManualDetalle-------------------------------------");
		if(getHojaManual().getTsHomaFechaRegistro()==null || getHojaManual().getStrHomaGlosa()==null){
			MessageController message = (MessageController)FacesContextUtil.getSessionBean("messageController");
			message.setWarningMessage("Primero debe ingresar la Fecha de Registro y la Glosa.");
			return;
		}
		cleanHojaManualDetalle();
	}
	
	public void cleanHojaManualDetalle() throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging HojaManualController.openHojaManualDetalle-------------------------------------");
		hojaManualDetalle = new HojaManualDetalle();
		hojaManualDetalle.setPersona(new Persona());
		hojaManualDetalle.getPersona().setNatural(new Natural());
		hojaManualDetalle.getPersona().setJuridica(new Juridica());
		
		loadListSucursal();
	}
	
	public void addHojaManualDetalle(ActionEvent event){
		log.info("-------------------------------------Debugging HojaManualController.addHojaManualDetalle-------------------------------------");
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
		beanCuentaBusq.setId(new PlanCuentaId());
		//Filtro combo para Descripcion, Numero de Cuenta
		if(getIntCboTipoCuentaBusq()!=null && getIntCboTipoCuentaBusq().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION)){//Por Descripción
			beanCuentaBusq.setStrDescripcion(getStrCuentaBusq());
		}else if(getIntCboTipoCuentaBusq()!=null && getIntCboTipoCuentaBusq().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_CUENTACONTABLE)){//Por Número de Cuenta
			beanCuentaBusq.getId().setStrNumeroCuenta(getStrCuentaBusq());
		}
		
		//Filtrar por el Período
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(getHojaManual().getTsHomaFechaRegistro());
		if(getIntCboPeriodoBusq()!=null && !getIntCboPeriodoBusq().equals(0))beanCuentaBusq.getId().setIntPeriodoCuenta(gcal.get(Calendar.YEAR));
		
		//Obtener sólo los planes de cuenta operacionales
		beanCuentaBusq.setIntMovimiento(Constante.TABLA_PLANCUENTA_MOVIMIENTO_OPERACIONAL);
		
		List<PlanCuenta> lista = null;
		PlanCuentaFacadeLocal planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
		lista = planCuentaFacade.getListaPlanCuentaBusqueda(beanCuentaBusq);
		System.out.println("listCuentaOrigenDestino.size: "+lista.size());
		return lista;
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
						beanPersonaBusq.getNatural().setStrNombres(getStrTxtPersonaBusq());
					}else if(getIntCboFiltroPersonaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
						beanPersonaBusq.setDocumento(new Documento());
						beanPersonaBusq.getDocumento().setIntTipoIdentidadCod(Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI));
						beanPersonaBusq.getDocumento().setStrNumeroIdentidad(getStrTxtPersonaBusq());
					}
				}
				PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				listPersona = personaFacade.getListPerNaturalBusqueda(beanPersonaBusq);
			}else if(getIntCboTipoPersonaBusq().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				beanPersonaBusq.setJuridica(new Juridica());
				if(getIntCboFiltroPersonaBusq()!=null && !getIntCboFiltroPersonaBusq().equals(0)){
					if(getIntCboFiltroPersonaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
						beanPersonaBusq.getJuridica().setStrRazonSocial(getStrTxtPersonaBusq());
					}else if(getIntCboFiltroPersonaBusq().equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)){
						beanPersonaBusq.setStrRuc(getStrTxtPersonaBusq());
					}
				}
				PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				listPersona = personaFacade.getBusquedaPerJuridicaSinSucursales(beanPersonaBusq);
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
}
