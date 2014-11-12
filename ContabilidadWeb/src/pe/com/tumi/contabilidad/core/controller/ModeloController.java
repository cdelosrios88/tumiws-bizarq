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
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelId;
import pe.com.tumi.contabilidad.core.domain.ModeloId;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeLocal;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.message.controller.MessageController;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class ModeloController {
	protected static Logger log = Logger.getLogger(ModeloController.class);
	private Modelo beanBusqueda;
	private Modelo beanModelo;
	private Boolean blnUpdating;
	private List<Modelo> listModelo;
	private Modelo modeloSelected;
	private Boolean blnShowDivFormModelo;
	private Boolean isDisabledTextBusq;
	private Boolean isValidModelo;
	private Boolean isValidModeloDetalle;
	
	private ArrayList<SelectItem> cboPeriodos = null;
	private Integer intPeriooModelo;
	private List<ModeloDetalle> listModeloDetalle = null;
	private Integer intPeriodoModelo;
	
	//variables del popup para seleccionar una cuenta
	private Integer intCboTipoCuentaBusq;
	private Integer intCboPeriodoBusq;
	private Boolean isDisabledTxtCuentaBusq;
	private String  strCuentaBusq;
	private List<PlanCuenta> listPlanCuenta;
	private ModeloDetalleNivel beanModeloDetalleNivel;
	private List<ModeloDetalleNivel> listModeloDetalleNivel;
	private ModeloDetalle modeloDetalleSelected;
	private Boolean isDisabledTipoRegistro;
	private Boolean isEditableModeloDetNivel;
	private Integer intRowModeloDetalle;
	private Boolean isUpdatingModeloDetalle;
	private Integer intRowModeloDetNivel;
	
	//atributos de Sesión
	private Integer intUsuarioSesion;
	private Integer intEmpresaSesion;
	
	//atributos mensajes de validacion
	
	private String msgDescripcionDetalleaError;
	private String msgTipoMovimientoDetError;
	private String msgDetalleNivelDetError;
	private String msgParaTipoRegistroError;
    private String msgDatoTablasError;
    private String msgDatoArgumento;
    private String msgIntValor;
    private String msgNroCuentaError;
    private String msgNroCuentaContraPartidadError;
    
	
	public ModeloController() throws BusinessException, EJBFactoryException{
		beanBusqueda = new Modelo();
		beanBusqueda.setId(new ModeloId());
		
		beanModeloDetalleNivel = new ModeloDetalleNivel();
		beanModeloDetalleNivel.setModeloDetalle(new ModeloDetalle());
		
		isDisabledTxtCuentaBusq = true;
		fillCboPeriodos();
		isDisabledTipoRegistro = null;
		
		listModeloDetalleNivel = null;
		isEditableModeloDetNivel = true;
		
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		intUsuarioSesion = usuarioSesion.getIntPersPersonaPk();
		intEmpresaSesion = usuarioSesion.getEmpresa().getIntIdEmpresa();
	}
	
	public void cleanBeanModelo(){
		log.info("-------------------------------------Debugging ModeloController.cleanBeanModelo-------------------------------------");
		setBeanModelo(new Modelo());
		getBeanModelo().setId(new ModeloId());
		setBlnUpdating(false);
	}
	
	public void buscarModelo(ActionEvent event) throws BusinessException, EJBFactoryException{
		try{
		log.info("-------------------------------------Debugging ModeloController.buscarModelo-------------------------------------");
		busquedaModelo();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void busquedaModelo() throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging ModeloController.busquedaModelo-------------------------------------");
		List<Modelo> lista = null;
		
	
		
		
		if(getBeanBusqueda().getId().getIntEmpresaPk()!=null && getBeanBusqueda().getId().getIntEmpresaPk().equals(0)){
			getBeanBusqueda().getId().setIntEmpresaPk(null);
		}
		if(getBeanBusqueda().getIntTipoModeloContable()!=null && getBeanBusqueda().getIntTipoModeloContable().equals(0)){
			getBeanBusqueda().setIntTipoModeloContable(null);
		}
		if(getBeanBusqueda().getIntEstado()!=null && getBeanBusqueda().getIntEstado().equals(0)){
			getBeanBusqueda().setIntEstado(null);
		}
		if(getBeanBusqueda().getStrDescripcion()!=null && getBeanBusqueda().getStrDescripcion().equals("")){
			getBeanBusqueda().setStrDescripcion(null);
		}
		
		getBeanBusqueda().getId().setIntEmpresaPk(intEmpresaSesion);
		ModeloFacadeLocal modeloFacade = (ModeloFacadeLocal) EJBFactory.getLocal(ModeloFacadeLocal.class);
		lista = modeloFacade.getListaModeloBusqueda(getBeanBusqueda());
		System.out.println("listModelo.size: "+lista.size());
		
		setListModelo(lista);
	}
	
	public void obtenerModelo(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.obtenerModelo-------------------------------------");
		if(getModeloSelected()!=null){
			setBlnShowDivFormModelo(true);
			setBeanModelo(getModeloSelected());
			setBlnUpdating(true);
		}
	}
	
	public void disableTextBusq(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.disableTextBusq-------------------------------------");
		log.info("pTipoModelo: "+getRequestParameter("pTipoModelo"));
		String pTipoModelo = getRequestParameter("pTipoModelo");
		if(pTipoModelo.equals("0")){
			setIsDisabledTextBusq(true);
		}else{
			setIsDisabledTextBusq(false);
		}
	}
	
	public void setSelectedModelo(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.setSelectedModelo-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowModelo"));
		String selectedRow = getRequestParameter("rowModelo");
		if(listModelo!=null){
			setModeloSelected(listModelo.get(Integer.valueOf(selectedRow)));
		}
	}
	
	public void eliminarModelo(ActionEvent event) throws EJBFactoryException, BusinessException{
		log.info("-------------------------------------Debugging ModeloController.eliminarModelo-------------------------------------");
		Modelo modelo = null;
		if(listModelo!=null && getModeloSelected()!=null){
			log.info("modeloSelected.id.intEmpresaPk: "+getModeloSelected().getId().getIntEmpresaPk());
			log.info("modeloSelected.id.intCodigoModelo: "+getModeloSelected().getId().getIntCodigoModelo());
			
			for(int i=0; i<listModelo.size(); i++){
				modelo = listModelo.get(i);
				if(getModeloSelected().getId().getIntEmpresaPk().equals(modelo.getId().getIntEmpresaPk())
						&& getModeloSelected().getId().getIntCodigoModelo().equals(modelo.getId().getIntCodigoModelo())){
					modelo.setIntEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					modelo.setTsFechaEliminacion(JFecha.obtenerTimestampDeFechayHoraActual());
					ModeloFacadeLocal modeloFacade = (ModeloFacadeLocal) EJBFactory.getLocal(ModeloFacadeLocal.class);
					modelo = modeloFacade.modificarModelo(modelo);
					System.out.println("Se actualizó el registro a estado anulado.");
					break;
				}
			}
		}
		
		busquedaModelo();
	}
	
	public void grabarModelo(ActionEvent event) throws EJBFactoryException{
		log.info("-------------------------------------Debugging ModeloController.grabarModelo-------------------------------------");
		Modelo modelo = null;
		
		if(!validarModelo()){
			MessageController message = (MessageController)getSessionBean("messageController");
			message.setWarningMessage("Datos no válidos ingresados. Verifque los mensajes de error y corrija.");
			return;
		}
		
		ModeloFacadeLocal modeloFacade = (ModeloFacadeLocal) EJBFactory.getLocal(ModeloFacadeLocal.class);
		if(getBeanModelo()!=null && getBeanModelo().getId()!=null){
			//validar los valores boolean de Movimiento e Id Extranjero
			log.info(beanModelo);			
			if(getBeanModelo().getId().getIntEmpresaPk()==null && getBeanModelo().getId().getIntCodigoModelo()==null){
				log.info("--grabar");
				//Grabar Modelo
				getBeanModelo().getId().setIntEmpresaPk(intEmpresaSesion);
				getBeanModelo().setIntEmpresaUsuario(intEmpresaSesion);
				getBeanModelo().setIntPersonaUsuario(intUsuarioSesion);
				try {					
					modelo = modeloFacade.grabarModelo(getBeanModelo());
				} catch (BusinessException e) {
					log.error(e.getMessage(),e);
					FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
				}
			}else if (getBeanModelo().getId().getIntEmpresaPk()!=null && getBeanModelo().getId().getIntCodigoModelo()!=null){
				log.info("--modificar");
				//Actualizar Tipo de Cambio
				try {
					modelo = modeloFacade.modificarModelo(getBeanModelo());
				} catch (Exception e) {
					log.error(e.getMessage(),e);
					FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);					
				}
			}
		}
		
		if(modelo!=null){
			setBlnShowDivFormModelo(false);
			FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
		}
		
		try {
			busquedaModelo();
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			log.error(e);
		}
	}
	
	public Boolean validarModelo(){
		log.info("-------------------------------------Debugging ModeloController.validarModelo-------------------------------------");
		isValidModelo = true;
		FacesContextUtil.setMessageError("");
		
		if(getBeanModelo()!=null){
			
			if (getBeanModelo().getIntTipoModeloContable().equals(0)){
				isValidModelo = false;
				FacesContextUtil.setMessageError("Debe seleccionar el campo Tipo de Modelo.");
			}
			
			if (getBeanModelo().getIntPeriodo().equals(0)){
				isValidModelo = false;
				FacesContextUtil.setMessageError("Debe seleccionar el Periodo.");
			}			
			
			if (getBeanModelo().getIntEstado().equals(0)){
				isValidModelo = false;
				FacesContextUtil.setMessageError("Debe seleccionar el Estado.");
			}
			
			if (getBeanModelo().getStrDescripcion().equals("")){
				isValidModelo = false;
				FacesContextUtil.setMessageError("Debe completar el Campo Descripción.");
			}
			
			if (getBeanModelo().getListModeloDetalle() == null){
				
				isValidModelo = false;
				FacesContextUtil.setMessageError("Debe agregar cuentas con el boton Agregar Cuenta.");
			}
			
			
			if (!existeNroCuentaContraPartida()){
				isValidModelo = false;
				FacesContextUtil.setMessageError("Debe existir al menos una cuenta contra partida.");
			}
			
			
			
		}
		
		return isValidModelo;
	}
	
	public void nuevoModelo(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.nuevoModelo-------------------------------------");
		setBlnShowDivFormModelo(true);
		cleanBeanModelo();
	}
	
	public void cancelarNuevo(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging ModeloController.cancelarNuevo-------------------------------------");
		cleanBeanModelo();
		setBlnShowDivFormModelo(false);
		busquedaModelo();
	}
	
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
	
	public void disableTxtCuentaBusq(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.disableTxtCuentaBusq-------------------------------------");
		log.info("pCboTipoCuentaBusq: "+getRequestParameter("pCboTipoCuentaBusq"));
		String pFiltroCombo = getRequestParameter("pCboTipoCuentaBusq");
		if(pFiltroCombo.equals("0")){
			setIsDisabledTxtCuentaBusq(true);
			setStrCuentaBusq("");
		}else{
			setIsDisabledTxtCuentaBusq(false);
		}
	}
	
	public void seleccionarCuenta(ValueChangeEvent event){
		log.info("-----------------------Debugging ModeloController.seleccionarCuenta()-----------------------------");
		String strCuenta = (String) event.getNewValue();
		log.info("strCuenta: "+strCuenta);
		if(listPlanCuenta!=null){
			PlanCuenta planCuenta = listPlanCuenta.get(Integer.valueOf(strCuenta));
			setModeloDetalleSelected(new ModeloDetalle());
			getModeloDetalleSelected().setId(new ModeloDetalleId());
			getModeloDetalleSelected().getId().setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
			getModeloDetalleSelected().getId().setIntContPeriodoCuenta(planCuenta.getId().getIntPeriodoCuenta());
			getModeloDetalleSelected().getId().setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());
			getModeloDetalleSelected().setPlanCuenta(planCuenta);
		}
		cleanModeloDetalleNivel();
	}
	
	public void cleanModeloDetalleNivel(ActionEvent event){
		log.info("-----------------------Debugging ModeloController.cleanModeloDetalleNivel()-----------------------------");
		cleanModeloDetalleNivel();
	}
	
	public void cleanModeloDetalleNivel(){
		log.info("-----------------------Debugging ModeloController.cleanModeloDetalleNivel()-----------------------------");
		setBeanModeloDetalleNivel(new ModeloDetalleNivel());
		isDisabledTipoRegistro = null;
		isEditableModeloDetNivel = true;
		isUpdatingModeloDetalle = false;
	}
	
	public void buscarPlanCuenta(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("-------------------------------------Debugging ModeloController.buscarPlanCuentas-------------------------------------");
		limpiarMsgsError();
		PlanCuenta beanCuentaBusq = new PlanCuenta();
		beanCuentaBusq.setId(new PlanCuentaId());
		//Filtro combo para Descripcion, Numero de Cuenta
		if(getIntCboTipoCuentaBusq()!=null && getIntCboTipoCuentaBusq().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION)){//Por Descripción
			beanCuentaBusq.setStrDescripcion(getStrCuentaBusq());
		}else if(getIntCboTipoCuentaBusq()!=null && getIntCboTipoCuentaBusq().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_CUENTACONTABLE)){//Por Número de Cuenta
			beanCuentaBusq.getId().setStrNumeroCuenta(getStrCuentaBusq());
		}
		if(getIntCboPeriodoBusq()!=null && !getIntCboPeriodoBusq().equals(0))beanCuentaBusq.getId().setIntPeriodoCuenta(getIntCboPeriodoBusq());
		
		//Autor: jchavez / Tarea: Creación / Fecha: 12.09.2014 / Se agrega nueva validación: PLCU_MOVIMIENTO = 1 
		List<PlanCuenta> listaTemp = null;
		List<PlanCuenta> lista = new ArrayList<PlanCuenta>();
		PlanCuentaFacadeLocal planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
		listaTemp = planCuentaFacade.getListaPlanCuentaBusqueda(beanCuentaBusq);
		if (listaTemp!=null && !listaTemp.isEmpty()) {
			for (PlanCuenta planCuenta : listaTemp) {
				if (planCuenta.getIntMovimiento().equals(1)) {
					lista.add(planCuenta);
				}
			}
		}
		//Fin jchavez 12.09.2014
		System.out.println("listCuentaOrigenDestino.size: "+lista.size());
		
		setListPlanCuenta(lista);
	}
	
	public void buscarOtraCuenta(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.buscarOtraCuenta-------------------------------------");
	}
	
	public void addModeloDetalleNivel(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.addModeloDetalleNivel-------------------------------------");
		
		boolean bValidar = true;
		
			
		
		if(getBeanModeloDetalleNivel().getId()==null){
			getBeanModeloDetalleNivel().setId(new ModeloDetalleNivelId());
			getBeanModeloDetalleNivel().getId().setIntEmpresaPk(getModeloDetalleSelected().getId().getIntEmpresaPk());
			getBeanModeloDetalleNivel().getId().setIntCodigoModelo(getModeloDetalleSelected().getId().getIntCodigoModelo());
			getBeanModeloDetalleNivel().getId().setIntPersEmpresaCuenta(getModeloDetalleSelected().getId().getIntPersEmpresaCuenta());
			getBeanModeloDetalleNivel().getId().setIntContPeriodoCuenta(getModeloDetalleSelected().getId().getIntContPeriodoCuenta());
			getBeanModeloDetalleNivel().getId().setStrContNumeroCuenta(getModeloDetalleSelected().getId().getStrContNumeroCuenta());
		}
		
		if(getModeloDetalleSelected().getListModeloDetalleNivel()==null){
			getModeloDetalleSelected().setListModeloDetalleNivel(new ArrayList<ModeloDetalleNivel>());
		}
		bValidar = validarModeloDetalleNivel(getBeanModeloDetalleNivel());
		if (bValidar){
			getModeloDetalleSelected().getListModeloDetalleNivel().add(getBeanModeloDetalleNivel());
			cleanModeloDetalleNivel();
		}
		 
	}
	public Boolean validarModeloDetalle(ModeloDetalle modeloDetalle){
    	log.info("-----------------------Debugging EmpresaController.validarModeloDetalle----------------------------");
    	boolean bValidar = true;
    	setMsgTipoMovimientoDetError("");
    	setMsgDetalleNivelDetError("");
    	setMsgNroCuentaError("");
    	
	    if(modeloDetalle.getIntParaOpcionDebeHaber().equals(0)){
	    	setMsgTipoMovimientoDetError("* Debe seleccionar el campo Tipo Movimiento.");
	    	bValidar = false;
	    	return bValidar;
	    }else{
	    	setMsgTipoMovimientoDetError("");
	    	bValidar = true;
	    }
	    if(existeNroCuentaModeloDetalle(modeloDetalle)){
	    	setMsgNroCuentaError("* No debe agregar mas de una cuenta repetida. Seleccione otra cuenta.");
	    	bValidar = false;
	    	return bValidar;
	    }else{
	    	setMsgNroCuentaError("");
	    	bValidar = true;
	    }
	    if(modeloDetalle.getListModeloDetalleNivel() == null){
	    	setMsgDetalleNivelDetError("* Debe agregar un Tipo Registro.");
	    	bValidar = false;
	    	return bValidar;
	    }else{
	    	setMsgDetalleNivelDetError("");
	    	bValidar = true;
	    }
	    
	   
	    return bValidar;
	    
	}
	
	public void limpiarMsgsError(){
		
		setMsgTipoMovimientoDetError("");
    	setMsgDetalleNivelDetError("");
    	setMsgNroCuentaError("");
    	setMsgDescripcionDetalleaError("");
    	setMsgParaTipoRegistroError("");
		
	}
	
	
	
	
	public Boolean validarModeloDetalleNivel(ModeloDetalleNivel modeloDetalleNivel){
    	log.info("-----------------------Debugging EmpresaController.validarModeloDetalleNivel----------------------------");
    	boolean bValidar = true;
    	setMsgTipoMovimientoDetError("");
    	setMsgDetalleNivelDetError("");
    	setMsgNroCuentaError("");
    	setMsgDescripcionDetalleaError("");
    	setMsgParaTipoRegistroError("");
    
	    if(modeloDetalleNivel.getStrDescripcion().trim().equals("")){
	    	setMsgDescripcionDetalleaError("* Debe completar el campo Descripciòn.");
	    	bValidar = false;
	    }else{
	    	setMsgDescripcionDetalleaError("");
	    }
	    if(modeloDetalleNivel.getIntParaTipoRegistro() == null){
	    	setMsgParaTipoRegistroError("* Debe Seleccionar el tipo de Registro.");
	    	bValidar = false;
	    }else{
	    	setMsgParaTipoRegistroError("");
	    	
	    	
	    	if (modeloDetalleNivel.getIntParaTipoRegistro().equals(2)){
	    		if (modeloDetalleNivel.getIntDatoTablas() == null){
	    			setMsgDatoTablasError("Debe completar el campo Codigo de la Tabla");
	    			bValidar = false;
	    		}
	    		else{
	    			setMsgDatoTablasError("");
	    		}
	    		
                if (modeloDetalleNivel.getIntDatoArgumento() == null){
                	setMsgDatoArgumento("Debe completar el campo Argumento");
                	bValidar = false;
	    		}
	    		else{
	    			setMsgDatoArgumento("");
	    		}
	    	}
	    	else{
	    		  if (modeloDetalleNivel.getIntValor() == null){
	    			  
	    				setMsgIntValor("Debe completar el campo Valor");
	    				bValidar = false;
		    		}
		    	  else{
		    		    setMsgIntValor("");
	    		  }
	    	}
	    }
	  
	    return bValidar;
	}
	
	public void disableTipoRegistro(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.disableTipoRegistro-------------------------------------");
		log.info("pRdoTipoRegistro: "+getRequestParameter("pRdoTipoRegistro"));
		String pTipoRegistro = getRequestParameter("pRdoTipoRegistro");
		if(Integer.valueOf(pTipoRegistro).equals(Constante.PARAM_T_TIPOREGISTROMODELO_VALORFIJO)){
			setIsDisabledTipoRegistro(true);
		}else if(Integer.valueOf(pTipoRegistro).equals(Constante.PARAM_T_TIPOREGISTROMODELO_TABLA)){
			setIsDisabledTipoRegistro(false);
		}
	}
	
	public void addModeloDetalle(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.addModeloDetalle-------------------------------------");
		
		boolean bValidar = true;
		if(getBeanModelo().getListModeloDetalle()==null){
			getBeanModelo().setListModeloDetalle(new ArrayList<ModeloDetalle>());
		}
		
		bValidar = validarModeloDetalle(getModeloDetalleSelected());
		if(bValidar){
			setIsValidModeloDetalle(true);
			getBeanModelo().getListModeloDetalle().add(getModeloDetalleSelected());
		}
		else{
			setIsValidModeloDetalle(false);
		}
			
			
		
		
		
	}
	

	
	public void setSelectedModeloDetalle(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.setSelectedModeloDetalle-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowModeloDetalle"));
		String selectedRow = getRequestParameter("rowModeloDetalle");
		if(beanModelo!=null && beanModelo.getListModeloDetalle()!=null && beanModelo.getListModeloDetalle().size()>0){
			setModeloDetalleSelected(beanModelo.getListModeloDetalle().get(Integer.valueOf(selectedRow)));
		}
		setIntRowModeloDetalle(Integer.valueOf(selectedRow.trim()));
	}
	
	public void verModeloDetalleNivel(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.verModeloDetalleNivel-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowModeloDetalle"));
		String selectedRow = getRequestParameter("rowModeloDetalle");
		if(beanModelo!=null && beanModelo.getListModeloDetalle()!=null && beanModelo.getListModeloDetalle().size()>0){
			setModeloDetalleSelected(beanModelo.getListModeloDetalle().get(Integer.valueOf(selectedRow)));
		}
		
		isEditableModeloDetNivel = false;
	}
	
	public void onConfirmDeleteModeloDet(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.onConfirmDeleteModeloDet-------------------------------------");
		MessageController message = (MessageController)getSessionBean("messageController");
		message.setWarningMessage("¿Desea eliminar esta cuenta del modelo?");
		message.setStrFunctionAccept("acceptMessage()");
	}
	
	public void deleteModeloDetalle(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.onConfirmDeleteModeloDet-------------------------------------");
		
		if(beanModelo!=null && beanModelo.getListModeloDetalle()!=null && beanModelo.getListModeloDetalle().size()>0){
			beanModelo.getListModeloDetalle().get(intRowModeloDetalle).setIsDeleted(true);
			/*ModeloDetalle modeloDetalle = beanModelo.getListModeloDetalle().get(intRowModeloDetalle);
			beanModelo.getListModeloDetalle().remove(intRowModeloDetalle);
			beanModelo.getListModeloDetalle().add(modeloDetalle);
			*/
			setModeloDetalleSelected(null);
			setIntRowModeloDetalle(null);
			
			for(ModeloDetalle modeloDetalle1 : beanModelo.getListModeloDetalle()){
				log.info(modeloDetalle1.getIsDeleted()+" "+modeloDetalle1);
			}
		}
	}
	
	public void obtenerModeloDetalle(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.obtenerModeloDetalle-------------------------------------");
		if(getModeloDetalleSelected()!=null){
			cleanModeloDetalleNivel();
			isUpdatingModeloDetalle = true;
		}
	}
	
	public void onDeleteModeloDetNivel(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.onDeleteModeloDetNivel-------------------------------------");
		MessageController message = (MessageController)getSessionBean("messageController");
		message.setWarningMessage("¿Desea eliminar este Detalle de la cuenta?");
		message.setStrFunctionAccept("eliminarModeloDetNivel()");
	}
	
	public void updateModeloDetalle(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.updateModeloDetalle-------------------------------------");
		getBeanModelo().getListModeloDetalle().remove(intRowModeloDetalle);
		getBeanModelo().getListModeloDetalle().add(intRowModeloDetalle, getModeloDetalleSelected());
		cleanModeloDetalleNivel(); 
	}
	
	public void deleteModeloDetNivel(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.deleteModeloDetNivel-------------------------------------");
		if(modeloDetalleSelected!=null && modeloDetalleSelected.getListModeloDetalleNivel()!=null 
				&& modeloDetalleSelected.getListModeloDetalleNivel().size()>0){
			modeloDetalleSelected.getListModeloDetalleNivel().get(intRowModeloDetNivel).setIsDeleted(true);
			ModeloDetalleNivel modeloDetalleNivel = modeloDetalleSelected.getListModeloDetalleNivel().get(intRowModeloDetNivel);
			modeloDetalleSelected.getListModeloDetalleNivel().remove(intRowModeloDetNivel);
			modeloDetalleSelected.getListModeloDetalleNivel().add(modeloDetalleNivel);
			
			setIntRowModeloDetNivel(null);
		}
	}
	
	public void setSelectedModeloDetNivel(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.setSelectedModeloDetNivel-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowModeloDetNivel"));
		String selectedRow = getRequestParameter("rowModeloDetNivel");
		if(modeloDetalleSelected!=null && modeloDetalleSelected.getListModeloDetalleNivel()!=null){
			setIntRowModeloDetNivel(Integer.valueOf(selectedRow));
		}
	}
	
	public boolean existeNroCuentaModeloDetalle(ModeloDetalle modeloDetalle)
	{
		boolean bolExisteCuenta = false;
		List<ModeloDetalle> listModeloDetalle = getBeanModelo().getListModeloDetalle();
		
		
		for (ModeloDetalle modeloDetalle2 : listModeloDetalle) {
		    
			
			if ((modeloDetalle.getId().getStrContNumeroCuenta().equals(modeloDetalle2.getId().getStrContNumeroCuenta()))
			    ){
				bolExisteCuenta = true;
			}
		}
		return bolExisteCuenta;
	}
	
	public boolean existeNroCuentaContraPartida()
	{
		boolean bolExisteCuentaDebe = false;
		boolean bolExisteCuentaHaber = false;
		boolean bolExisteContraPartidad = false;
		
		
		List<ModeloDetalle> listModeloDetalle = getBeanModelo().getListModeloDetalle();
		
		 if (listModeloDetalle != null)
			for (ModeloDetalle modeloDetalle : listModeloDetalle) {
			    
			    if (modeloDetalle.getIsDeleted() == null || modeloDetalle.getIsDeleted() == false){
			    	
			    	if (modeloDetalle.getIntParaOpcionDebeHaber() != null && modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE))
					{
						bolExisteCuentaDebe = true;
					}
					
					if (modeloDetalle.getIntParaOpcionDebeHaber() != null && modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_HABER))
					{
						bolExisteCuentaHaber = true;
					}
			    }	
			}
		
		 if (bolExisteCuentaDebe == true &&  bolExisteCuentaHaber == true){
			 
			 bolExisteContraPartidad = true;
		 }
		 
		return bolExisteContraPartidad;
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
	public Modelo getBeanModelo() {
		return beanModelo;
	}
	public void setBeanModelo(Modelo beanModelo) {
		this.beanModelo = beanModelo;
	}
	public Modelo getBeanBusqueda() {
		return beanBusqueda;
	}
	public void setBeanBusqueda(Modelo beanBusqueda) {
		this.beanBusqueda = beanBusqueda;
	}
	public Boolean getBlnUpdating() {
		return blnUpdating;
	}
	public void setBlnUpdating(Boolean blnUpdating) {
		this.blnUpdating = blnUpdating;
	}
	public List<Modelo> getListModelo() {
		return listModelo;
	}
	public void setListModelo(List<Modelo> listModelo) {
		this.listModelo = listModelo;
	}
	public Modelo getModeloSelected() {
		return modeloSelected;
	}
	public void setModeloSelected(Modelo modeloSelected) {
		this.modeloSelected = modeloSelected;
	}
	public Boolean getBlnShowDivFormModelo() {
		return blnShowDivFormModelo;
	}
	public void setBlnShowDivFormModelo(Boolean blnShowDivFormModelo) {
		this.blnShowDivFormModelo = blnShowDivFormModelo;
	}
	public Boolean getIsDisabledTextBusq() {
		return isDisabledTextBusq;
	}
	public void setIsDisabledTextBusq(Boolean isDisabledTextBusq) {
		this.isDisabledTextBusq = isDisabledTextBusq;
	}
	public Boolean getIsValidModelo() {
		return isValidModelo;
	}
	public void setIsValidModelo(Boolean isValidModelo) {
		this.isValidModelo = isValidModelo;
	}
	public ArrayList<SelectItem> getCboPeriodos() {
		return cboPeriodos;
	}
	public void setCboPeriodos(ArrayList<SelectItem> cboPeriodos) {
		this.cboPeriodos = cboPeriodos;
	}
	public Integer getIntPeriooModelo() {
		return intPeriooModelo;
	}
	public void setIntPeriooModelo(Integer intPeriooModelo) {
		this.intPeriooModelo = intPeriooModelo;
	}
	public List<ModeloDetalle> getListModeloDetalle() {
		return listModeloDetalle;
	}
	public void setListModeloDetalle(List<ModeloDetalle> listModeloDetalle) {
		this.listModeloDetalle = listModeloDetalle;
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
	public Boolean getIsDisabledTxtCuentaBusq() {
		return isDisabledTxtCuentaBusq;
	}
	public void setIsDisabledTxtCuentaBusq(Boolean isDisabledTxtCuentaBusq) {
		this.isDisabledTxtCuentaBusq = isDisabledTxtCuentaBusq;
	}
	public String getStrCuentaBusq() {
		return strCuentaBusq;
	}
	public void setStrCuentaBusq(String strCuentaBusq) {
		this.strCuentaBusq = strCuentaBusq;
	}
	public List<PlanCuenta> getListPlanCuenta() {
		return listPlanCuenta;
	}
	public void setListPlanCuenta(List<PlanCuenta> listPlanCuenta) {
		this.listPlanCuenta = listPlanCuenta;
	}
	public ModeloDetalleNivel getBeanModeloDetalleNivel() {
		return beanModeloDetalleNivel;
	}
	public void setBeanModeloDetalleNivel(ModeloDetalleNivel beanModeloDetalleNivel) {
		this.beanModeloDetalleNivel = beanModeloDetalleNivel;
	}
	public List<ModeloDetalleNivel> getListModeloDetalleNivel() {
		return listModeloDetalleNivel;
	}
	public void setListModeloDetalleNivel(
			List<ModeloDetalleNivel> listModeloDetalleNivel) {
		this.listModeloDetalleNivel = listModeloDetalleNivel;
	}
	public Integer getIntPeriodoModelo() {
		return intPeriodoModelo;
	}
	public void setIntPeriodoModelo(Integer intPeriodoModelo) {
		this.intPeriodoModelo = intPeriodoModelo;
	}
	public ModeloDetalle getModeloDetalleSelected() {
		return modeloDetalleSelected;
	}
	public void setModeloDetalleSelected(ModeloDetalle modeloDetalleSelected) {
		this.modeloDetalleSelected = modeloDetalleSelected;
	}
	public Boolean getIsDisabledTipoRegistro() {
		return isDisabledTipoRegistro;
	}
	public void setIsDisabledTipoRegistro(Boolean isDisabledTipoRegistro) {
		this.isDisabledTipoRegistro = isDisabledTipoRegistro;
	}
	public Boolean getIsEditableModeloDetNivel() {
		return isEditableModeloDetNivel;
	}
	public void setIsEditableModeloDetNivel(Boolean isEditableModeloDetNivel) {
		this.isEditableModeloDetNivel = isEditableModeloDetNivel;
	}
	public Integer getIntRowModeloDetalle() {
		return intRowModeloDetalle;
	}
	public void setIntRowModeloDetalle(Integer intRowModeloDetalle) {
		this.intRowModeloDetalle = intRowModeloDetalle;
	}
	public Boolean getIsUpdatingModeloDetalle() {
		return isUpdatingModeloDetalle;
	}
	public void setIsUpdatingModeloDetalle(Boolean isUpdatingModeloDetalle) {
		this.isUpdatingModeloDetalle = isUpdatingModeloDetalle;
	}
	public Integer getIntRowModeloDetNivel() {
		return intRowModeloDetNivel;
	}
	public void setIntRowModeloDetNivel(Integer intRowModeloDetNivel) {
		this.intRowModeloDetNivel = intRowModeloDetNivel;
	}
	public Integer getIntUsuarioSesion() {
		return intUsuarioSesion;
	}
	public void setIntUsuarioSesion(Integer intUsuarioSesion) {
		this.intUsuarioSesion = intUsuarioSesion;
	}
	public Integer getIntEmpresaSesion() {
		return intEmpresaSesion;
	}
	public void setIntEmpresaSesion(Integer intEmpresaSesion) {
		this.intEmpresaSesion = intEmpresaSesion;
	}

	public String getMsgDescripcionDetalleaError() {
		return msgDescripcionDetalleaError;
	}

	public void setMsgDescripcionDetalleaError(String msgDescripcionDetalleaError) {
		this.msgDescripcionDetalleaError = msgDescripcionDetalleaError;
	}

	public Boolean getIsValidModeloDetalle() {
		return isValidModeloDetalle;
	}

	public void setIsValidModeloDetalle(Boolean isValidModeloDetalle) {
		this.isValidModeloDetalle = isValidModeloDetalle;
	}

	public String getMsgTipoMovimientoDetError() {
		return msgTipoMovimientoDetError;
	}

	public void setMsgTipoMovimientoDetError(String msgTipoMovimientoDetError) {
		this.msgTipoMovimientoDetError = msgTipoMovimientoDetError;
	}

	public String getMsgDetalleNivelDetError() {
		return msgDetalleNivelDetError;
	}

	public void setMsgDetalleNivelDetError(String msgDetalleNivelDetError) {
		this.msgDetalleNivelDetError = msgDetalleNivelDetError;
	}

	public String getMsgParaTipoRegistroError() {
		return msgParaTipoRegistroError;
	}

	public void setMsgParaTipoRegistroError(String msgParaTipoRegistroError) {
		this.msgParaTipoRegistroError = msgParaTipoRegistroError;
	}

	public String getMsgDatoTablasError() {
		return msgDatoTablasError;
	}

	public void setMsgDatoTablasError(String msgDatoTablasError) {
		this.msgDatoTablasError = msgDatoTablasError;
	}

	public String getMsgDatoArgumento() {
		return msgDatoArgumento;
	}

	public void setMsgDatoArgumento(String msgDatoArgumento) {
		this.msgDatoArgumento = msgDatoArgumento;
	}

	public String getMsgIntValor() {
		return msgIntValor;
	}

	public void setMsgIntValor(String msgIntValor) {
		this.msgIntValor = msgIntValor;
	}

	public String getMsgNroCuentaError() {
		return msgNroCuentaError;
	}

	public void setMsgNroCuentaError(String msgNroCuentaError) {
		this.msgNroCuentaError = msgNroCuentaError;
	}

	public String getMsgNroCuentaContraPartidadError() {
		return msgNroCuentaContraPartidadError;
	}

	public void setMsgNroCuentaContraPartidadError(
			String msgNroCuentaContraPartidadError) {
		this.msgNroCuentaContraPartidadError = msgNroCuentaContraPartidadError;
	}
	
	
	
}
