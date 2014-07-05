package pe.com.tumi.contabilidad.core.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.contabilidad.cierre.domain.RatioDetalle;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeLocal;
import pe.com.tumi.contabilidad.parametro.controller.AnexoPopupController;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.message.controller.MessageController;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class PlanCuentasController {
	protected static Logger log = Logger.getLogger(PlanCuentasController.class);
	private Integer intFiltroSelect;
	private String strFiltroBusq;
	private String[] strFiltroCheck;
	private Boolean isValidPlanCuentas = null;
	private Boolean isDisabledTextBusq = null;
	private List<PlanCuenta> listPlanCuentas = null;
	private PlanCuenta planCuentasSelected = null;
	private Boolean blnShowDivFormPlanCuentas = null;
	private PlanCuenta beanPlanCuentas = null;
	private Boolean blnUpdating = null;
	private Integer intEstadoFinancieroBusq;
	private ArrayList<SelectItem> cboPeriodos = null;
	private List<Tabla> listEstadoFinanciero;
	
	//Propiedades del popup de búsqueda de cuentas de origen y destino
	private String strTituloPopupCuenta = "Selección de Cuenta Contable";
	private List<PlanCuenta> listCuentaOrigenDestino;
	private Boolean isDisabledTxtCuentaBusq;
	private Integer intCboTipoCuentaBusq;
	private Integer intCboPeriodoBusq;
	private String strCuentaBusq;
	private Boolean isCuentaOrigenODestino = null; //true si es de Origen o false si es de Destino
	
	//atributos de sesión
	private Integer intEmpresaSesion;
	private Integer intUsuarioSesion;
	
	//Added by cdelosrios on 13/09/2013
	private Integer intPeriodoCuenta;
	//private	Integer intNumeroOperando;
	private AnexoDetalle	anexoDetalleSeleccionado;
	private RatioDetalle 	ratioDetalleSeleccionado;
	private	String	strTextoFiltrar;
	//AnexoPopupController anexoPopupController = (AnexoPopupController)getSessionBean("anexoPopupController");
	//End added by cdelosrios on 13/09/2013
	
	public PlanCuentasController() throws BusinessException, EJBFactoryException{
		isDisabledTextBusq = true;
		fillCboPeriodos();
		isDisabledTxtCuentaBusq = true;
		loadListEstadoFinanciero();
		
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		intUsuarioSesion = usuarioSesion.getIntPersPersonaPk();
		intEmpresaSesion = usuarioSesion.getEmpresa().getIntIdEmpresa();
	}
	
	public void cleanBeanPlanCuentas(){
		log.info("-------------------------------------Debugging PlanCuentasController.cleanBeanPlanCuentas-------------------------------------");
		setBeanPlanCuentas(new PlanCuenta());
		getBeanPlanCuentas().setId(new PlanCuentaId());
		setBlnUpdating(false);
		//Agregado por cdelosrios, 16/09/2013
		AnexoPopupController anexoPopupController = (AnexoPopupController)getSessionBean("anexoPopupController");
		if(anexoPopupController.getListaAnexoDetalleCuenta() != null && 
				!anexoPopupController.getListaAnexoDetalleCuenta().isEmpty()){
			anexoPopupController.setListaAnexoDetalleCuenta(new ArrayList<AnexoDetalleCuenta>());
		}
		//Fin agregado por cdelosrios, 16/09/2013
	}
	
	public void buscarPlanCuentas(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging PlanCuentasController.buscarPlanCuentas-------------------------------------");
		busquedaPlanCuentas();
	}
	
	public void busquedaPlanCuentas() throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging PlanCuentasController.busquedaPlanCuentas-------------------------------------");
		PlanCuenta beanPlanCuentaBusq = new PlanCuenta();
		beanPlanCuentaBusq.setId(new PlanCuentaId());
		//Filtro combo para Descripcion, Numero de Cuenta
		if(getIntFiltroSelect().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION)){//Por Descripción
			beanPlanCuentaBusq.setStrDescripcion(strFiltroBusq);
		}else if(getIntFiltroSelect().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_CUENTACONTABLE)){//Por Número de Cuenta
			beanPlanCuentaBusq.getId().setStrNumeroCuenta(strFiltroBusq);
		}
		//Filtro checkbox para Nivel Operacional, Extranjero y Ctas. Destino
		if(getStrFiltroCheck()!=null){
			beanPlanCuentaBusq.setBlnTieneCtaDestino(false);
			for(int i=0; i<getStrFiltroCheck().length; i++){
				log.info("getStrFiltroCheck()["+i+"]: "+getStrFiltroCheck()[i]);
				if(getStrFiltroCheck()[i].equals(""+Constante.PARAM_T_FILTROCHECKPLANCUENTAS_NIVELOPERACIONAL)){
					beanPlanCuentaBusq.setIntMovimiento(1);
				}else if(getStrFiltroCheck()[i].equals(""+Constante.PARAM_T_FILTROCHECKPLANCUENTAS_EXTRANJERO)){
					beanPlanCuentaBusq.setIntIdentificadorExtranjero(1);
				}else if(getStrFiltroCheck()[i].equals(""+Constante.PARAM_T_FILTROCHECKPLANCUENTAS_CTASDESTINO)){
					beanPlanCuentaBusq.setBlnTieneCtaDestino(true);
				}
			}
		}
		
		//Added by cdelosrios on 13/09/2013
		beanPlanCuentaBusq.getId().setIntPeriodoCuenta((intPeriodoCuenta!=null && intPeriodoCuenta!=0)?intPeriodoCuenta:null);
		//End added by cdelosrios on 13/09/2013
		
		List<PlanCuenta> lista = null;
		PlanCuentaFacadeLocal planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
		lista = planCuentaFacade.getListaPlanCuentaBusqueda(beanPlanCuentaBusq);
		System.out.println("listPlanCuenta.size: "+lista.size());
		
		if(beanPlanCuentaBusq.getBlnTieneCtaDestino()){
			ArrayList<PlanCuenta> listAux = new ArrayList<PlanCuenta>();
			for(int i=0; i<lista.size(); i++){
				if(lista.get(i).getStrNumeroCuentaDestino()!=null){
					listAux.add(lista.get(i));
				}
			}
			lista = listAux;
		}
		
		setListPlanCuentas(lista);
	}
	
	public void obtenerPlanCuentas(ActionEvent event){
		log.info("-------------------------------------Debugging PlanCuentasController.obtenerPlanCuentas-------------------------------------");
		//Agregado por cdelosrios, 16/09/2013
		AnexoPopupController anexoPopupController = (AnexoPopupController)getSessionBean("anexoPopupController");
		//Fin agregado por cdelosrios, 16/09/2013
		if(getPlanCuentasSelected()!=null){
			setBlnShowDivFormPlanCuentas(true);
			if(planCuentasSelected.getListaAnexoDetalleCuenta()==null){
				anexoPopupController.setListaAnexoDetalleCuenta(new ArrayList<AnexoDetalleCuenta>());
			}
			setBeanPlanCuentas(getPlanCuentasSelected());
			setBlnUpdating(true);
		}
	}
	
	public void disableTextBusq(ActionEvent event){
		log.info("-------------------------------------Debugging PlanCuentasController.disableTextBusq-------------------------------------");
		log.info("pFiltroCombo: "+getRequestParameter("pFiltroCombo"));
		String pFiltroCombo = getRequestParameter("pFiltroCombo");
		if(pFiltroCombo.equals("0")){
			setStrFiltroBusq(null);
			setIsDisabledTextBusq(true);
		}else{
			setIsDisabledTextBusq(false);
		}
	}
	
	public void disableTxtCuentaBusq(ActionEvent event){
		log.info("-------------------------------------Debugging PlanCuentasController.disableTxtCuentaBusq-------------------------------------");
		log.info("pCboTipoCuentaBusq: "+getRequestParameter("pCboTipoCuentaBusq"));
		String pFiltroCombo = getRequestParameter("pCboTipoCuentaBusq");
		if(pFiltroCombo.equals("0")){
			setIsDisabledTxtCuentaBusq(true);
		}else{
			setIsDisabledTxtCuentaBusq(false);
		}
	}
	
	public void setSelectedPlanCuentas(ActionEvent event) throws EJBFactoryException{
		log.info("-------------------------------------Debugging PlanCuentasController.setSelectedPlanCuentas-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowPlanCuentas"));
		String selectedRow = getRequestParameter("rowPlanCuentas");
		PlanCuenta planCuentas = null;
		//Agregado por cdelosrios, 16/09/2013
		List<AnexoDetalleCuenta> lstAnexoDetalleCuenta = null;
		AnexoPopupController anexoPopupController = (AnexoPopupController)getSessionBean("anexoPopupController");
		PlanCuentaFacadeLocal planCuentasFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
		//Fin agregado por cdelosrios, 16/09/2013
		if(listPlanCuentas!=null){
			for(int i=0; i<listPlanCuentas.size(); i++){
				planCuentas = listPlanCuentas.get(i);
				//Agregado por cdelosrios, 16/09/2013
				try {
					lstAnexoDetalleCuenta = planCuentasFacade.getListaAnexoDetalleCuentaPorPlanCuenta(planCuentas);
					if(lstAnexoDetalleCuenta!=null && !lstAnexoDetalleCuenta.isEmpty()){
						planCuentas.setListaAnexoDetalleCuenta(lstAnexoDetalleCuenta);
						anexoPopupController.setListaAnexoDetalleCuenta(lstAnexoDetalleCuenta);
					}
				} catch (BusinessException e) {
					log.error(e.getMessage(), e);
				}
				//Fin agregado por cdelosrios, 16/09/2013
				if(i == Integer.parseInt(selectedRow)){
					setPlanCuentasSelected(planCuentas);
					break;
				}
			}
		}
	}
	
	public void eliminarPlanCuentas(ActionEvent event) throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging PlanCuentasController.eliminarPlanCuentas-------------------------------------");
		PlanCuenta planCuentas = null;
		if(listPlanCuentas!=null && getPlanCuentasSelected()!=null){
			log.info("planCuentasSelected.id.intEmpresaCuentaPk: "+getPlanCuentasSelected().getId().getIntEmpresaCuentaPk());
			log.info("planCuentasSelected.id.intPeriodoCuenta: "+getPlanCuentasSelected().getId().getIntPeriodoCuenta());
			log.info("planCuentasSelected.id.strNumeroCuenta: "+getPlanCuentasSelected().getId().getStrNumeroCuenta());
			
			for(int i=0; i<listPlanCuentas.size(); i++){
				planCuentas = listPlanCuentas.get(i);
				if(getPlanCuentasSelected().getId().getIntEmpresaCuentaPk().equals(planCuentas.getId().getIntEmpresaCuentaPk())
						&& getPlanCuentasSelected().getId().getIntPeriodoCuenta().equals(planCuentas.getId().getIntPeriodoCuenta())
						&& getPlanCuentasSelected().getId().getStrNumeroCuenta().equals(planCuentas.getId().getStrNumeroCuenta())){
					planCuentas.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					planCuentas.setTsFechaEliminacion(JFecha.obtenerTimestampDeFechayHoraActual());
					planCuentas.setIntEmpresaEliminarPk(intEmpresaSesion);
					planCuentas.setIntPersonaEliminarPk(intUsuarioSesion);
					PlanCuentaFacadeLocal planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
					planCuentas = planCuentaFacade.modificarPlanCuenta(planCuentas);
					System.out.println("Se actualizó el registro a estado anulado.");
					break;
				}
			}
		}
		
		busquedaPlanCuentas();
	}
	
	public void grabarPlanCuentas(ActionEvent event) throws EJBFactoryException{
		log.info("-------------------------------------Debugging PlanCuentasController.grabarPlanCuentas-------------------------------------");
		PlanCuenta planCuentas = null;
		AnexoPopupController anexoPopupController = (AnexoPopupController)getSessionBean("anexoPopupController");
		
		if(!validarPlanCuentas()){
			MessageController message = (MessageController)getSessionBean("messageController");
			message.setWarningMessage("Datos no válidos ingresados. Verifique los mensajes de error y corrija.");
			return;
		}
		
		PlanCuentaFacadeLocal planCuentasFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
		if(getBeanPlanCuentas()!=null && getBeanPlanCuentas().getId()!=null){
			//validar los valores boolean de Movimiento e Id Extranjero
			if(getBeanPlanCuentas().getBlnTieneMovimiento()!=null && getBeanPlanCuentas().getBlnTieneMovimiento()){
				getBeanPlanCuentas().setIntMovimiento(1);
			}else{
				getBeanPlanCuentas().setIntMovimiento(0);
			}
			
			if(getBeanPlanCuentas().getBlnTieneIdExtranjero()!=null && getBeanPlanCuentas().getBlnTieneIdExtranjero()){
				getBeanPlanCuentas().setIntIdentificadorExtranjero(1);
			}else{
				getBeanPlanCuentas().setIntIdentificadorExtranjero(0);
			}
						
			if(getBeanPlanCuentas().getId().getStrNumeroCuenta()!=null && getBeanPlanCuentas().getId().getIntPeriodoCuenta()!=null &&
					getBeanPlanCuentas().getId().getIntEmpresaCuentaPk()==null){
				//Grabar Tipo de Cambio
				getBeanPlanCuentas().getId().setIntEmpresaCuentaPk(intEmpresaSesion);
				getBeanPlanCuentas().setIntEmpresaUsuarioPk(intEmpresaSesion);
				getBeanPlanCuentas().setIntPersonaUsuarioPk(intUsuarioSesion);
				//Agregado por cdelosrios, 16/09/2013
				List<AnexoDetalleCuenta> lstAnexoDetalleCuenta = new ArrayList<AnexoDetalleCuenta>();
				if(anexoPopupController.getListaAnexoDetalleCuenta()!=null && 
						!anexoPopupController.getListaAnexoDetalleCuenta().isEmpty()){
					for(AnexoDetalleCuenta anexoDetalleCuenta : anexoPopupController.getListaAnexoDetalleCuenta()){
						anexoDetalleCuenta.getId().setIntPersEmpresaCuenta(beanPlanCuentas.getId().getIntEmpresaCuentaPk());
						anexoDetalleCuenta.getId().setIntContPeriodoCuenta(beanPlanCuentas.getId().getIntPeriodoCuenta());
						anexoDetalleCuenta.getId().setStrContNumeroCuenta(beanPlanCuentas.getId().getStrNumeroCuenta());
						//anexoDetalleCuenta.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						lstAnexoDetalleCuenta.add(anexoDetalleCuenta);
					}
					beanPlanCuentas.setListaAnexoDetalleCuenta(lstAnexoDetalleCuenta);
				}
				//Fin agregado por cdelosrios, 16/09/2013
				
				try {
					planCuentas = planCuentasFacade.grabarPlanCuenta(beanPlanCuentas);
				} catch (BusinessException e) {
					FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
					log.error(e);
				}
			}else if (getBeanPlanCuentas().getId().getStrNumeroCuenta()!=null && getBeanPlanCuentas().getId().getIntPeriodoCuenta()!=null &&
					getBeanPlanCuentas().getId().getIntEmpresaCuentaPk()!=null){
				
				//Agregado por cdelosrios, 16/09/2013
				List<AnexoDetalleCuenta> lstAnexoDetalleCuenta = new ArrayList<AnexoDetalleCuenta>();
				if(anexoPopupController.getListaAnexoDetalleCuenta()!=null && 
						!anexoPopupController.getListaAnexoDetalleCuenta().isEmpty()){
					for(AnexoDetalleCuenta anexoDetalleCuenta : anexoPopupController.getListaAnexoDetalleCuenta()){
						anexoDetalleCuenta.getId().setIntPersEmpresaCuenta(beanPlanCuentas.getId().getIntEmpresaCuentaPk());
						anexoDetalleCuenta.getId().setIntContPeriodoCuenta(beanPlanCuentas.getId().getIntPeriodoCuenta());
						anexoDetalleCuenta.getId().setStrContNumeroCuenta(beanPlanCuentas.getId().getStrNumeroCuenta());
						//anexoDetalleCuenta.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						lstAnexoDetalleCuenta.add(anexoDetalleCuenta);
					}
					beanPlanCuentas.setListaAnexoDetalleCuenta(lstAnexoDetalleCuenta);
				}
				//Fin agregado por cdelosrios, 16/09/2013
				
				try {
					planCuentas = planCuentasFacade.modificarPlanCuenta(beanPlanCuentas);
				} catch (BusinessException e) {
					FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
					log.error(e);
				}
			}
		}
		
		if(planCuentas!=null){
			FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
		}
		
		
		try {
			busquedaPlanCuentas();
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			log.error(e);
		}
	}
	
	public Boolean validarPlanCuentas(){
		log.info("-------------------------------------Debugging PlanCuentasController.validarPlanCuentas-------------------------------------");
		isValidPlanCuentas = true;
		
		if(getBeanPlanCuentas()!=null){
			if(getBeanPlanCuentas().getId()!=null && getBeanPlanCuentas().getId().getIntPeriodoCuenta()!=null
					&& getBeanPlanCuentas().getId().getIntPeriodoCuenta().equals(0)){
				setMessageError("Seleccione el Período.");
				isValidPlanCuentas = false;
			}
			
			if(getBeanPlanCuentas().getIntEstadoCod()!=null && getBeanPlanCuentas().getIntEstadoCod().equals(0)){
				setMessageError("Seleccione un Estado.");
				isValidPlanCuentas = false;
			}
			
			if(getBeanPlanCuentas().getId()!=null && getBeanPlanCuentas().getId().getStrNumeroCuenta()!=null
					&& getBeanPlanCuentas().getId().getStrNumeroCuenta().equals("")){
				setMessageError("El campo Cuenta Contable es obligatorio.");
				isValidPlanCuentas = false;
			}
			
			if(getBeanPlanCuentas().getStrNumeroCuentaOrigen()!=null && getBeanPlanCuentas().getStrNumeroCuentaDestino()!=null 
					&& getBeanPlanCuentas().getStrNumeroCuentaOrigen().equals(getBeanPlanCuentas().getStrNumeroCuentaDestino())){
				setMessageError("La cuenta de Origen y la cuenta Destino no pueden ser la misma.");
				isValidPlanCuentas = false;
			}
			
			//Agregado por cdelosrios, 22/09/2013
			AnexoPopupController anexoPopupController = (AnexoPopupController)getSessionBean("anexoPopupController");
			if(anexoPopupController.getListaAnexoDetalleCuenta()!=null){
				if(anexoPopupController.getListaAnexoDetalleCuenta().size()<=0){
					setMessageError("Debe ingresar por lo menos un Estado Financiero.");
					isValidPlanCuentas = false;
				}
			}
			//Fin agregado por cdelosrios, 22/09/2013
		}
		
		return isValidPlanCuentas;
	}
	
	public void nuevoPlanCuentas(ActionEvent event){
		log.info("-------------------------------------Debugging PlanCuentasController.nuevoPlanCuentas-------------------------------------");
		setBlnShowDivFormPlanCuentas(true);
		cleanBeanPlanCuentas();
	}
	
	public void cancelarNuevo(ActionEvent event){
		log.info("-------------------------------------Debugging PlanCuentasController.cancelarNuevo-------------------------------------");
		cleanBeanPlanCuentas();
		setBlnShowDivFormPlanCuentas(false);
	}
	
	public void filtrarCuentaOrigenDestino(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging PlanCuentasController.buscarCuentaOrigenDestino-------------------------------------");
		
		if(isCuentaOrigenODestino){
			searchCuentaOrigen();
		}else{
			searchCuentaDestino();
		}
	}
	
	public List<PlanCuenta> buscarCuentaOrigenDestino() throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging PlanCuentasController.buscarCuentaOrigenDestino-------------------------------------");
		PlanCuenta beanCuentaBusq = new PlanCuenta();
		beanCuentaBusq.setId(new PlanCuentaId());
		//Filtro combo para Descripcion, Numero de Cuenta
		if(getIntCboTipoCuentaBusq()!=null && getIntCboTipoCuentaBusq().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION)){//Por Descripción
			beanCuentaBusq.setStrDescripcion(getStrFiltroBusq());
		}else if(getIntCboTipoCuentaBusq()!=null && getIntCboTipoCuentaBusq().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_CUENTACONTABLE)){//Por Número de Cuenta
			beanCuentaBusq.getId().setStrNumeroCuenta(getStrFiltroBusq());
		}
		if(getIntCboPeriodoBusq()!=null && !getIntCboPeriodoBusq().equals(0))beanCuentaBusq.getId().setIntPeriodoCuenta(getIntCboPeriodoBusq());
		
		List<PlanCuenta> lista = null;
		PlanCuentaFacadeLocal planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
		lista = planCuentaFacade.getListaPlanCuentaBusqueda(beanCuentaBusq);
		System.out.println("listCuentaOrigenDestino.size: "+lista.size());
		return lista;
	}
	
	public void buscarCuentaOrigen(ActionEvent event) throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging PlanCuentasController.buscarCuentaOrigen-------------------------------------");
		searchCuentaOrigen();
		setIsCuentaOrigenODestino(true);//true si es de Origen
		setStrTituloPopupCuenta("Selección de Cuenta de Origen");
	}
	
	public void searchCuentaOrigen() throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging PlanCuentasController.searchCuentaOrigen-------------------------------------");
		List<PlanCuenta> lista = new ArrayList<PlanCuenta>();
		lista = buscarCuentaOrigenDestino();
		
		//Una cuenta de Origen no puede haber sido de Destino
		List<PlanCuenta> listAux = new ArrayList<PlanCuenta>();
		for(int i=0; i<lista.size(); i++){
			Boolean isOrigen = true;
			for(int j=0; j<lista.size(); j++){
				if(lista.get(i).getId().getStrNumeroCuenta().equals(lista.get(j).getStrNumeroCuentaDestino())){
					isOrigen = false;
				}
			}
			if(isOrigen){
				listAux.add(lista.get(i));
			}
		}
		log.info("listAux.size(): "+listAux.size());
		lista = listAux;
		
		//No debe mostrar aquellas que tengan cuenta de Origen o Destino,
		//ya que no tienen cuenta padre
		listAux = new ArrayList<PlanCuenta>();
		for(int i=0; i<lista.size(); i++){
			if(lista.get(i).getStrNumeroCuentaOrigen()==null
					&& lista.get(i).getStrNumeroCuentaDestino()==null){
				listAux.add(lista.get(i));
			}
		}
		log.info("listAux.size(): "+listAux.size());
		lista = listAux;
		
		setListCuentaOrigenDestino(lista);
	}
	
	public void buscarCuentaDestino(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging PlanCuentasController.buscarCuentaOrigen-------------------------------------");
		searchCuentaDestino();
		setIsCuentaOrigenODestino(false);//false si es de Destino
		setStrTituloPopupCuenta("Selección de Cuenta de Destino");
	}
	
	public void searchCuentaDestino() throws BusinessException, EJBFactoryException{
		List<PlanCuenta> lista = new ArrayList<PlanCuenta>();
		lista = buscarCuentaOrigenDestino();
		
		//Una cuenta de Destino no puede haber sido de Origen
		List<PlanCuenta> listAux = new ArrayList<PlanCuenta>();
		for(int i=0; i<lista.size(); i++){
			Boolean isDestino = true;
			for(int j=0; j<lista.size(); j++){
				if(lista.get(i).getId().getStrNumeroCuenta().equals(lista.get(j).getStrNumeroCuentaOrigen())){
					isDestino = false;
				}
			}
			if(isDestino){
				listAux.add(lista.get(i));
			}
		}
		lista = listAux;
		
		//Debe mostrar aquellas que no tengan cuenta de Origen o Destino,
		//ya que no tienen cuenta padre
		listAux = new ArrayList<PlanCuenta>();
		for(int i=0; i<lista.size(); i++){
			if(lista.get(i).getStrNumeroCuentaOrigen()==null
					&& lista.get(i).getStrNumeroCuentaDestino()==null){
				listAux.add(lista.get(i));
			}
		}
		lista = listAux;
		
		setListCuentaOrigenDestino(lista);
	}
	
	public void buscarEstadoFinanciero(ActionEvent event){
		log.info("-------------------------------------Debugging PlanCuentasController.buscarEstadoFinanciero-------------------------------------");
	}
	
	//Agregado por cdelosrios, 16/09/2013
	public void abrirPopUpElemento(ActionEvent event){
		try{
			AnexoPopupController anexoPopupController = (AnexoPopupController)getSessionBean("anexoPopupController");
			anexoPopupController.setIntPeriodo(beanPlanCuentas.getId().getIntPeriodoCuenta());
			anexoPopupController.setStrCallingFormId("frmPlanCuentas");
			anexoPopupController.setStrIdListAnexoDetalleCuenta("divTblEstadoFinanciero");
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void removeElemento(ActionEvent event) {
		AnexoPopupController anexoPopupController = (AnexoPopupController)getSessionBean("anexoPopupController");
		String rowKey = getRequestParameter("rowKeyElemento");
		AnexoDetalleCuenta anexoDetalleCuentaTmp = null;
		try {
			if (anexoPopupController.getListaAnexoDetalleCuenta() != null) {
				for (int i = 0; i < anexoPopupController.getListaAnexoDetalleCuenta().size(); i++) {
					if (Integer.parseInt(rowKey) == i) {
						AnexoDetalleCuenta anexoDetalleCuenta = anexoPopupController.getListaAnexoDetalleCuenta().get(i);
						if (anexoDetalleCuenta.getId()!= null && anexoDetalleCuenta.getId().getIntItemAnexoDetalleCuenta()!=null) {
							anexoDetalleCuentaTmp = anexoPopupController.getListaAnexoDetalleCuenta().get(i);
							anexoDetalleCuentaTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						}
						anexoPopupController.getListaAnexoDetalleCuenta().remove(i);
						break;
					}
				}
				if(anexoDetalleCuentaTmp!=null){
					anexoPopupController.getListaAnexoDetalleCuenta().add(anexoDetalleCuentaTmp);
				}
			}
		} catch (Exception e) {
			log.error("Error Exception en removeElemento ---> : " + e);
		}
	}
	//Fin agregado por cdelosrios, 16/09/2013
	
	public void fillCboPeriodos() throws EJBFactoryException, BusinessException{
		log.info("-----------------------Debugging ModeloController.getCboPeriodos()-----------------------------");
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
	
	public void seleccionarCuenta(ValueChangeEvent event){
		log.info("-----------------------Debugging PlanCuentasController.seleccionarCuenta()-----------------------------");
		String strCuenta = (String) event.getNewValue();
		log.info("strCuenta: "+strCuenta);
		
		if(isCuentaOrigenODestino){
			getBeanPlanCuentas().setStrNumeroCuentaOrigen(listCuentaOrigenDestino.get(Integer.valueOf(strCuenta)).getId().getStrNumeroCuenta());
		}else{
			getBeanPlanCuentas().setStrNumeroCuentaDestino(listCuentaOrigenDestino.get(Integer.valueOf(strCuenta)).getId().getStrNumeroCuenta());
		}
	}
	
	public void loadListEstadoFinanciero() {
		log.info("-------------------------------------Debugging PlanCuentasController.loadListDocumento-------------------------------------");
		TablaFacadeRemote facade = null;
		List<Tabla> listaEstadoFinanciero = null;
		try {
			facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaEstadoFinanciero = facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSFINANCIEROS), Constante.VISTA_ESTADOSFINANCIEROS_GRUPO_B);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListEstadoFinanciero(listaEstadoFinanciero);
	}
	
	//Metodos Utilitarios
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	
	protected void setMessageError(String error) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
	}
	
	//Getters && Setters
	public Integer getIntFiltroSelect() {
		return intFiltroSelect;
	}
	public void setIntFiltroSelect(Integer intFiltroSelect) {
		this.intFiltroSelect = intFiltroSelect;
	}
	public String getStrFiltroBusq() {
		return strFiltroBusq;
	}
	public void setStrFiltroBusq(String strFiltroBusq) {
		this.strFiltroBusq = strFiltroBusq;
	}
	public String[] getStrFiltroCheck() {
		return strFiltroCheck;
	}
	public void setStrFiltroCheck(String[] strFiltroCheck) {
		this.strFiltroCheck = strFiltroCheck;
	}
	public Boolean getIsValidPlanCuentas() {
		return isValidPlanCuentas;
	}
	public void setIsValidPlanCuentas(Boolean isValidPlanCuentas) {
		this.isValidPlanCuentas = isValidPlanCuentas;
	}
	public Boolean getIsDisabledTextBusq() {
		return isDisabledTextBusq;
	}
	public void setIsDisabledTextBusq(Boolean isDisabledTextBusq) {
		this.isDisabledTextBusq = isDisabledTextBusq;
	}
	public List<PlanCuenta> getListPlanCuentas() {
		return listPlanCuentas;
	}
	public void setListPlanCuentas(List<PlanCuenta> listPlanCuentas) {
		this.listPlanCuentas = listPlanCuentas;
	}
	public PlanCuenta getPlanCuentasSelected() {
		return planCuentasSelected;
	}
	public void setPlanCuentasSelected(PlanCuenta planCuentasSelected) {
		this.planCuentasSelected = planCuentasSelected;
	}
	public Boolean getBlnShowDivFormPlanCuentas() {
		return blnShowDivFormPlanCuentas;
	}
	public void setBlnShowDivFormPlanCuentas(Boolean blnShowDivFormPlanCuentas) {
		this.blnShowDivFormPlanCuentas = blnShowDivFormPlanCuentas;
	}
	public PlanCuenta getBeanPlanCuentas() {
		return beanPlanCuentas;
	}
	public void setBeanPlanCuentas(PlanCuenta beanPlanCuentas) {
		this.beanPlanCuentas = beanPlanCuentas;
	}
	public Boolean getBlnUpdating() {
		return blnUpdating;
	}
	public void setBlnUpdating(Boolean blnUpdating) {
		this.blnUpdating = blnUpdating;
	}
	public Integer getIntEstadoFinancieroBusq() {
		return intEstadoFinancieroBusq;
	}
	public void setIntEstadoFinancieroBusq(Integer intEstadoFinancieroBusq) {
		this.intEstadoFinancieroBusq = intEstadoFinancieroBusq;
	}
	public void setCboPeriodos(ArrayList<SelectItem> cboPeriodos) {
		this.cboPeriodos = cboPeriodos;
	}
	public ArrayList<SelectItem> getCboPeriodos() {
		return cboPeriodos;
	}
	public String getStrTituloPopupCuenta() {
		return strTituloPopupCuenta;
	}
	public void setStrTituloPopupCuenta(String strTituloPopupCuenta) {
		this.strTituloPopupCuenta = strTituloPopupCuenta;
	}
	public List<PlanCuenta> getListCuentaOrigenDestino() {
		return listCuentaOrigenDestino;
	}
	public void setListCuentaOrigenDestino(List<PlanCuenta> listCuentaOrigenDestino) {
		this.listCuentaOrigenDestino = listCuentaOrigenDestino;
	}
	public Integer getIntCboTipoCuentaBusq() {
		return intCboTipoCuentaBusq;
	}
	public void setIntCboTipoCuentaBusq(Integer intCboTipoCuentaBusq) {
		this.intCboTipoCuentaBusq = intCboTipoCuentaBusq;
	}
	public Integer getIntCboPeriodoBusq() {
		return intCboPeriodoBusq;
	}
	public void setIntCboPeriodoBusq(Integer intCboPeriodoBusq) {
		this.intCboPeriodoBusq = intCboPeriodoBusq;
	}
	public String getStrCuentaBusq() {
		return strCuentaBusq;
	}
	public void setStrCuentaBusq(String strCuentaBusq) {
		this.strCuentaBusq = strCuentaBusq;
	}
	public Boolean getIsCuentaOrigenODestino() {
		return isCuentaOrigenODestino;
	}
	public void setIsCuentaOrigenODestino(Boolean isCuentaOrigenODestino) {
		this.isCuentaOrigenODestino = isCuentaOrigenODestino;
	}
	public Boolean getIsDisabledTxtCuentaBusq() {
		return isDisabledTxtCuentaBusq;
	}
	public void setIsDisabledTxtCuentaBusq(Boolean isDisabledTxtCuentaBusq) {
		this.isDisabledTxtCuentaBusq = isDisabledTxtCuentaBusq;
	}
	public Integer getIntEmpresaSesion() {
		return intEmpresaSesion;
	}
	public void setIntEmpresaSesion(Integer intEmpresaSesion) {
		this.intEmpresaSesion = intEmpresaSesion;
	}
	public Integer getIntUsuarioSesion() {
		return intUsuarioSesion;
	}
	public void setIntUsuarioSesion(Integer intUsuarioSesion) {
		this.intUsuarioSesion = intUsuarioSesion;
	}
	public List<Tabla> getListEstadoFinanciero() {
		return listEstadoFinanciero;
	}
	public void setListEstadoFinanciero(List<Tabla> listEstadoFinanciero) {
		this.listEstadoFinanciero = listEstadoFinanciero;
	}
	
	//Added by cdelosrios on 13/09/2013
	public Integer getIntPeriodoCuenta() {
		return intPeriodoCuenta;
	}

	public void setIntPeriodoCuenta(Integer intPeriodoCuenta) {
		this.intPeriodoCuenta = intPeriodoCuenta;
	}

	public AnexoDetalle getAnexoDetalleSeleccionado() {
		return anexoDetalleSeleccionado;
	}

	public void setAnexoDetalleSeleccionado(AnexoDetalle anexoDetalleSeleccionado) {
		this.anexoDetalleSeleccionado = anexoDetalleSeleccionado;
	}

	public RatioDetalle getRatioDetalleSeleccionado() {
		return ratioDetalleSeleccionado;
	}

	public void setRatioDetalleSeleccionado(RatioDetalle ratioDetalleSeleccionado) {
		this.ratioDetalleSeleccionado = ratioDetalleSeleccionado;
	}

	public String getStrTextoFiltrar() {
		return strTextoFiltrar;
	}

	public void setStrTextoFiltrar(String strTextoFiltrar) {
		this.strTextoFiltrar = strTextoFiltrar;
	}
	//End added by cdelosrios on 13/09/2013
}
