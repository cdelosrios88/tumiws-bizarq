package pe.com.tumi.servicio.solicitudPrestamo.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.refinanciamiento.controller.SolicitudRefinanController;
import pe.com.tumi.servicio.solicitudEspecial.controller.SolicitudEspecialController;
import pe.com.tumi.servicio.solicitudPrestamo.bo.GarantiaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadDescuento;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadDescuentoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CapacidadCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeLocal;

public class CapacidadPagoController {
	
	protected static Logger log = Logger.getLogger(CapacidadPagoController.class);
	private CapacidadCredito	beanCapacidadCredito;

	private Boolean				blnChkCartaAutorizacion;
	private Boolean				blnFormTipoIncentivoGral;
	private Boolean				blnFormTipoIncentivoProrrateo;
	private BigDecimal			bdMontoTumi;
	private BigDecimal			bdDsctosVarios;
	private BigDecimal			bdBaseTotYDsctos;
	private String				rowKey;
	private Integer				intPanelCapacidadPago;
	private Boolean				validCapacidadCredito;
	//private SolicitudPrestamoController solicitudPrestamoController = null;
	//Mensajes de error
	private String				msgTxtBaseTotalDsctos;
	private String				msgTxtDsctosVarios;
	private String				msgTxtCuotaFija;
	private Usuario 			usuario = null;
	private BigDecimal			bdIndDscto;
	private SolicitudPrestamoFacadeLocal solicitudPrestamoFacade = null;
	private EstructuraFacadeRemote	estructuraFacade = null;
	
	private String strMsgErrorCapacidad = "";
	private Boolean blnMostrarBoton  = Boolean.TRUE;

	
	public CapacidadPagoController(){
		beanCapacidadCredito = new CapacidadCredito();
		beanCapacidadCredito.setBdIndiceDescuento(new BigDecimal(0));
		beanCapacidadCredito.setListaCapacidadDscto(new ArrayList<CapacidadDescuento>());
		bdDsctosVarios = new BigDecimal(0);
		bdBaseTotYDsctos = new BigDecimal(0);
		blnFormTipoIncentivoGral = false;
		blnFormTipoIncentivoProrrateo = false;
		validCapacidadCredito = true;
		try {
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			solicitudPrestamoFacade = (SolicitudPrestamoFacadeLocal)EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			estructuraFacade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
		} catch(EJBFactoryException e){
			log.error("error: " + e.getMessage());
		}
	}
	
	public CapacidadCredito getBeanCapacidadCredito() {
		return beanCapacidadCredito;
	}
	public void setBeanCapacidadCredito(CapacidadCredito beanCapacidadCredito) {
		this.beanCapacidadCredito = beanCapacidadCredito;
	}
	public Boolean getBlnChkCartaAutorizacion() {
		return blnChkCartaAutorizacion;
	}
	public void setBlnChkCartaAutorizacion(Boolean blnChkCartaAutorizacion) {
		this.blnChkCartaAutorizacion = blnChkCartaAutorizacion;
	}
	public Boolean getBlnFormTipoIncentivoGral() {
		return blnFormTipoIncentivoGral;
	}
	public void setBlnFormTipoIncentivoGral(Boolean blnFormTipoIncentivoGral) {
		this.blnFormTipoIncentivoGral = blnFormTipoIncentivoGral;
	}
	public Boolean getBlnFormTipoIncentivoProrrateo() {
		return blnFormTipoIncentivoProrrateo;
	}
	public void setBlnFormTipoIncentivoProrrateo(
			Boolean blnFormTipoIncentivoProrrateo) {
		this.blnFormTipoIncentivoProrrateo = blnFormTipoIncentivoProrrateo;
	}
	public BigDecimal getBdMontoTumi() {
		return bdMontoTumi;
	}
	public void setBdMontoTumi(BigDecimal bdMontoTumi) {
		this.bdMontoTumi = bdMontoTumi;
	}
	public BigDecimal getBdDsctosVarios() {
		return bdDsctosVarios;
	}
	public void setBdDsctosVarios(BigDecimal bdDsctosVarios) {
		this.bdDsctosVarios = bdDsctosVarios;
	}
	public BigDecimal getBdBaseTotYDsctos() {
		return bdBaseTotYDsctos;
	}
	public void setBdBaseTotYDsctos(BigDecimal bdBaseTotYDsctos) {
		this.bdBaseTotYDsctos = bdBaseTotYDsctos;
	}
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public Integer getIntPanelCapacidadPago() {
		return intPanelCapacidadPago;
	}
	public void setIntPanelCapacidadPago(Integer intPanelCapacidadPago) {
		this.intPanelCapacidadPago = intPanelCapacidadPago;
	}
	public Boolean getValidCapacidadCredito() {
		return validCapacidadCredito;
	}
	public void setValidCapacidadCredito(Boolean validCapacidadCredito) {
		this.validCapacidadCredito = validCapacidadCredito;
	}
	public String getMsgTxtBaseTotalDsctos() {
		return msgTxtBaseTotalDsctos;
	}
	public void setMsgTxtBaseTotalDsctos(String msgTxtBaseTotalDsctos) {
		this.msgTxtBaseTotalDsctos = msgTxtBaseTotalDsctos;
	}
	public String getMsgTxtDsctosVarios() {
		return msgTxtDsctosVarios;
	}
	public void setMsgTxtDsctosVarios(String msgTxtDsctosVarios) {
		this.msgTxtDsctosVarios = msgTxtDsctosVarios;
	}
	public String getMsgTxtCuotaFija() {
		return msgTxtCuotaFija;
	}
	public void setMsgTxtCuotaFija(String msgTxtCuotaFija) {
		this.msgTxtCuotaFija = msgTxtCuotaFija;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public BigDecimal getBdIndDscto() {
		return bdIndDscto;
	}
	public void setBdIndDscto(BigDecimal bdIndDscto) {
		this.bdIndDscto = bdIndDscto;
	}
	public SolicitudPrestamoFacadeLocal getSolicitudPrestamoFacade() {
		return solicitudPrestamoFacade;
	}
	public void setSolicitudPrestamoFacade(
			SolicitudPrestamoFacadeLocal solicitudPrestamoFacade) {
		this.solicitudPrestamoFacade = solicitudPrestamoFacade;
	}
	public EstructuraFacadeRemote getEstructuraFacade() {
		return estructuraFacade;
	}
	public void setEstructuraFacade(EstructuraFacadeRemote estructuraFacade) {
		this.estructuraFacade = estructuraFacade;
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	//------------
	
	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		CapacidadPagoController.log = log;
	}

	public String getStrMsgErrorCapacidad() {
		return strMsgErrorCapacidad;
	}

	public void setStrMsgErrorCapacidad(String strMsgErrorCapacidad) {
		this.strMsgErrorCapacidad = strMsgErrorCapacidad;
	}

	public Boolean getBlnMostrarBoton() {
		return blnMostrarBoton;
	}

	public void setBlnMostrarBoton(Boolean blnMostrarBoton) {
		this.blnMostrarBoton = blnMostrarBoton;
	}

	/**
	 * 
	 */
	public void limpiarFormCapacidadPago(){
		beanCapacidadCredito = new CapacidadCredito();
		beanCapacidadCredito.setListaCapacidadDscto(new ArrayList<CapacidadDescuento>());
		bdMontoTumi = new BigDecimal(0);
		blnChkCartaAutorizacion = null;
		bdDsctosVarios = new BigDecimal(0);
		bdBaseTotYDsctos = new BigDecimal(0);
	}
	
	/**
	 * 
	 */
	public void limpiarMsgsError(){
		msgTxtCuotaFija = "";
		msgTxtBaseTotalDsctos = "";
		msgTxtDsctosVarios = "";
	}
	
	/**
	 * 
	 * @param event
	 */
	public void showTipoIncentivo(ActionEvent event){
		log.info("intTipoIncentivo: " + beanCapacidadCredito.getIntParaTipoCapacidadCod());
		try{
			setBlnFormTipoIncentivoGral(beanCapacidadCredito.getIntParaTipoCapacidadCod().equals(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_GENERAL));
			setBlnFormTipoIncentivoProrrateo(beanCapacidadCredito.getIntParaTipoCapacidadCod().equals(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_PRORRATEO));
			limpiarFormCapacidadPago();
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void addDescuentoVarios(ActionEvent event){
		CapacidadDescuento capacidadDescuento;
		//List<CapacidadDescuento> listaCapacidadDscto = new ArrayList<CapacidadDescuento>();
		try{
			capacidadDescuento = new CapacidadDescuento();
			capacidadDescuento.setId(new CapacidadDescuentoId());
			capacidadDescuento.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			capacidadDescuento.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			capacidadDescuento.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
			beanCapacidadCredito.getListaCapacidadDscto().add(capacidadDescuento);
			beanCapacidadCredito.setIntNroEntidades(beanCapacidadCredito.getListaCapacidadDscto().size());
		}catch(Exception e){
			log.error("error: "+e);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void removeCapacidadDscto(ActionEvent event){
		String rowKey = getRequestParameter("rowKeyCapacidadDscto");
		CapacidadDescuento capacidadDescuentoTmp = null;
		if(beanCapacidadCredito.getListaCapacidadDscto()!=null){
			for(int i=0; i<beanCapacidadCredito.getListaCapacidadDscto().size(); i++){
				if(Integer.parseInt(rowKey)==i){
					CapacidadDescuento capacidadDescuento = beanCapacidadCredito.getListaCapacidadDscto().get(i);
					if(capacidadDescuento.getId()!=null && capacidadDescuento.getId().getIntItemCapacidadDescuento()!=null){
						capacidadDescuentoTmp = beanCapacidadCredito.getListaCapacidadDscto().get(i);
						capacidadDescuentoTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					beanCapacidadCredito.getListaCapacidadDscto().remove(i);
					//beanCapacidadCredito.setIntNroEntidades(beanCapacidadCredito.getListaCapacidadDscto().size());
					break;
				}
			}
			if(capacidadDescuentoTmp!=null){
				beanCapacidadCredito.getListaCapacidadDscto().add(capacidadDescuentoTmp);
				//beanCapacidadCredito.setIntNroEntidades(beanCapacidadCredito.getListaCapacidadDscto().size());
			}
			calcularDsctos(event);
		}
	}
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void removeCapacidadDscto2(ActionEvent event){
		SolicitudPrestamoController solicitudPrestamoController =  null;
		CapacidadDescuento capacidadDescuentoTmp = null;
		
		try {
			solicitudPrestamoController = (SolicitudPrestamoController)getSessionBean("solicitudPrestamoController");
			String rowKey = getRequestParameter("rowKeyCapacidadDscto");
			
			if(beanCapacidadCredito.getListaCapacidadDscto()!=null){
				for(int i=0; i<beanCapacidadCredito.getListaCapacidadDscto().size(); i++){
					if(Integer.parseInt(rowKey)==i){
						CapacidadDescuento capacidadDescuento = beanCapacidadCredito.getListaCapacidadDscto().get(i);
						if(capacidadDescuento.getId()!=null && capacidadDescuento.getId().getIntItemCapacidadDescuento()!=null){
							capacidadDescuentoTmp = beanCapacidadCredito.getListaCapacidadDscto().get(i);
							capacidadDescuentoTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						}
						beanCapacidadCredito.getListaCapacidadDscto().remove(i);
						//beanCapacidadCredito.setIntNroEntidades(beanCapacidadCredito.getListaCapacidadDscto().size());
						break;
					}
				}
				if(capacidadDescuentoTmp!=null){
					beanCapacidadCredito.getListaCapacidadDscto().add(capacidadDescuentoTmp);
					//beanCapacidadCredito.setIntNroEntidades(beanCapacidadCredito.getListaCapacidadDscto().size());
				}
				calcularDsctos(event);
			}
			calcularCapacidadPago(event);
			
		} catch (NumberFormatException e) {
			log.error("Error en removeCapacidadDscto2 ---> "+e);
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void getBaseCalculo(ActionEvent event){
		String strTotalIngreso = getRequestParameter("bdTotalIngreso");
		String strMontoTumi = getRequestParameter("bdMontoTumi");
		try{
			if(strTotalIngreso!=null){
				BigDecimal bdTotalIngreso = new BigDecimal(strTotalIngreso.equals("")?"0":strTotalIngreso);
				beanCapacidadCredito.setBdTotalIngresos(bdTotalIngreso);
				beanCapacidadCredito.setBdBaseCalculo(bdTotalIngreso);
			}
			if(strMontoTumi!=null){
				BigDecimal bdMontoTumi = new BigDecimal(strMontoTumi.equals("")?"0":strMontoTumi);
				this.bdMontoTumi = bdMontoTumi;
				beanCapacidadCredito.setBdBaseCalculo(bdMontoTumi);
			}
			if(beanCapacidadCredito.getBdTotalIngresos()!=null && this.bdMontoTumi!=null){
				beanCapacidadCredito.setBdBaseCalculo(beanCapacidadCredito.getBdTotalIngresos().add(this.bdMontoTumi));
			}
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void enableDisableControls(ActionEvent event){
		BigDecimal indDesc = new BigDecimal(0);
		try {
			indDesc=recuperarIndiceDescuentoporDefecto(event);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	beanCapacidadCredito.setBdIndiceDescuento(blnChkCartaAutorizacion==true?(new BigDecimal(100)):(indDesc));
		getBaseCalculoPagoHaberes(event);
	}
	
	/**
	 * 
	 * @param event
	 */
	public void enableDisableControlsRef(ActionEvent event){
		BigDecimal indDesc = new BigDecimal(0);
		try {
			indDesc=recuperarIndiceDescuentoporDefectoRef(event);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	beanCapacidadCredito.setBdIndiceDescuento(blnChkCartaAutorizacion==true?(new BigDecimal(100)):(indDesc));
		getBaseCalculoPagoHaberesRef(event);
	}
	
	/**
	 * 
	 * @param event
	 */
	public void enableDisableControlsEsp(ActionEvent event){
		BigDecimal indDesc = new BigDecimal(0);
		try {
			indDesc=recuperarIndiceDescuentoporDefectoEsp(event);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	beanCapacidadCredito.setBdIndiceDescuento(blnChkCartaAutorizacion==true?(new BigDecimal(100)):(indDesc));
		getBaseCalculoPagoHaberesRef(event);
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void getBaseCalculoPagoHaberesRef(ActionEvent event){
		log.info("----------CapacidadPagoController.getBaseCalculoPagoHaberes----------");
		String strTotalIngreso = getRequestParameter("bdTotalIngreso");
		try{
			if(strTotalIngreso!=null){
				BigDecimal bdTotalIngreso = new BigDecimal(strTotalIngreso.equals("")?"0":strTotalIngreso);
				beanCapacidadCredito.setBdTotalIngresos(bdTotalIngreso);
				beanCapacidadCredito.setBdBaseCalculo(bdTotalIngreso);
			}
			if(beanCapacidadCredito.getBdTotalIngresos()!=null && beanCapacidadCredito.getBdIndiceDescuento()!=null){
				beanCapacidadCredito.setBdBaseCalculo(beanCapacidadCredito.getBdTotalIngresos().multiply(beanCapacidadCredito.getBdIndiceDescuento()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
			}
		}catch(Exception e){
			log.error("error: " + e);
		}finally{
			getBaseTotalDscto(event);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void getBaseCalculoPagoHaberes(ActionEvent event){
		log.info("----------CapacidadPagoController.getBaseCalculoPagoHaberes----------");
		String strTotalIngreso = getRequestParameter("bdTotalIngreso");
		try{
			if(strTotalIngreso!=null){
				BigDecimal bdTotalIngreso = new BigDecimal(strTotalIngreso.equals("")?"0":strTotalIngreso);
				beanCapacidadCredito.setBdTotalIngresos(bdTotalIngreso);
				beanCapacidadCredito.setBdBaseCalculo(bdTotalIngreso);
			}
			if(beanCapacidadCredito.getBdTotalIngresos()!=null && beanCapacidadCredito.getBdIndiceDescuento()!=null){
				beanCapacidadCredito.setBdBaseCalculo(beanCapacidadCredito.getBdTotalIngresos().multiply(beanCapacidadCredito.getBdIndiceDescuento()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
			}
		}catch(Exception e){
			log.error("error: " + e);
		}finally{
			getBaseTotalDsctoRef(event);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void getBaseTotalDsctoRef(ActionEvent event){
		log.info("----------CapacidadPagoController.getBaseTotalDscto----------");
		String strBaseTotalDscto = getRequestParameter("bdBaseTotalDscto");
		try {
			if(strBaseTotalDscto!=null){
				BigDecimal bdBaseTotalDscto = new BigDecimal(strBaseTotalDscto.equals("")?"0":strBaseTotalDscto);
				beanCapacidadCredito.setBdTotalDescuento(bdBaseTotalDscto);
				beanCapacidadCredito.setBdBaseTotal(bdBaseTotalDscto);
			}
			System.out.println("beanCapacidadCredito.getBdBaseCalculo(): " + beanCapacidadCredito.getBdBaseCalculo());
			if(beanCapacidadCredito.getBdBaseCalculo()!=null && beanCapacidadCredito.getBdTotalDescuento()!=null){
				beanCapacidadCredito.setBdBaseTotal(beanCapacidadCredito.getBdBaseCalculo().subtract(beanCapacidadCredito.getBdTotalDescuento()));
			}
			System.out.println("beanCapacidadCredito.getBdBaseTotal(): " + beanCapacidadCredito.getBdBaseTotal());
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void getBaseTotalDscto(ActionEvent event){
		log.info("----------CapacidadPagoController.getBaseTotalDscto----------");
		String strBaseTotalDscto = getRequestParameter("bdBaseTotalDscto");
		try {
			if(strBaseTotalDscto!=null){
				BigDecimal bdBaseTotalDscto = new BigDecimal(strBaseTotalDscto.equals("")?"0":strBaseTotalDscto);
				beanCapacidadCredito.setBdTotalDescuento(bdBaseTotalDscto);
				beanCapacidadCredito.setBdBaseTotal(bdBaseTotalDscto);
			}
			System.out.println("beanCapacidadCredito.getBdBaseCalculo(): " + beanCapacidadCredito.getBdBaseCalculo());
			if(beanCapacidadCredito.getBdBaseCalculo()!=null && beanCapacidadCredito.getBdTotalDescuento()!=null){
				beanCapacidadCredito.setBdBaseTotal(beanCapacidadCredito.getBdBaseCalculo().subtract(beanCapacidadCredito.getBdTotalDescuento()));
			}
			System.out.println("beanCapacidadCredito.getBdBaseTotal(): " + beanCapacidadCredito.getBdBaseTotal());
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void calcularDsctos(ActionEvent event){
		log.info("----------CapacidadPagoController.calcularDsctos----------");
		BigDecimal bdTempMonto = new BigDecimal(0);
		if(beanCapacidadCredito.getListaCapacidadDscto()!=null && beanCapacidadCredito.getListaCapacidadDscto().size()>0){
			for(CapacidadDescuento capacDscto : beanCapacidadCredito.getListaCapacidadDscto()){
				if(capacDscto.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO))
				bdTempMonto = bdTempMonto.add(capacDscto.getBdMonto());
			}
			bdDsctosVarios = bdTempMonto;
		}else{
			
			bdDsctosVarios = BigDecimal.ZERO;
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	
	public void calcularCapacidadPago(ActionEvent event){
		log.info("----------CapacidadPagoController.calcularCapacidadPago----------");
		Integer intNroEntidades = 0;
		SolicitudPrestamoController solicitudPrestamoController = null;
		try {
			solicitudPrestamoController =(SolicitudPrestamoController)getSessionBean("solicitudPrestamoController");
			calcularDsctos(event);
			bdBaseTotYDsctos = beanCapacidadCredito.getBdBaseTotal().add(bdDsctosVarios);
			beanCapacidadCredito.setIntNroEntidades(beanCapacidadCredito.getListaCapacidadDscto().size());
			
			if( beanCapacidadCredito.getIntNroEntidades() == 0){
				blnMostrarBoton = Boolean.TRUE;
				strMsgErrorCapacidad = " * Se debe ingresar al menos un Descuento";
				intNroEntidades = 1;
			}else{
				strMsgErrorCapacidad = "";
				blnMostrarBoton = Boolean.FALSE;
				intNroEntidades = beanCapacidadCredito.getIntNroEntidades();
			}
			beanCapacidadCredito.setBdCapacidadPago(bdBaseTotYDsctos.divide(new BigDecimal(intNroEntidades), 2, RoundingMode.HALF_UP));

			/*if(beanCapacidadCredito.getListaCapacidadDscto()== null || beanCapacidadCredito.getListaCapacidadDscto().isEmpty()){
				beanCapacidadCredito.setBdCapacidadPago(bdBaseTotYDsctos);	
			}*/
		
		
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	
	public void calcularCapacidadPagoEspecial(ActionEvent event){
		Integer intNroEntidades = 0;
		SolicitudEspecialController solicitudEspecialController = null;
		try {
			solicitudEspecialController =(SolicitudEspecialController)getSessionBean("solicitudEspecialController");
			calcularDsctos(event);
			bdBaseTotYDsctos = beanCapacidadCredito.getBdBaseTotal().add(bdDsctosVarios);
			beanCapacidadCredito.setIntNroEntidades(beanCapacidadCredito.getListaCapacidadDscto().size());
			
			if( beanCapacidadCredito.getIntNroEntidades() == 0){
				blnMostrarBoton = Boolean.TRUE;
				strMsgErrorCapacidad = " * Se debe ingresar al menos un Descuento";
				intNroEntidades = 1;
			}else{
				strMsgErrorCapacidad = "";
				blnMostrarBoton = Boolean.FALSE;
				intNroEntidades = beanCapacidadCredito.getIntNroEntidades();
			}
			beanCapacidadCredito.setBdCapacidadPago(bdBaseTotYDsctos.divide(new BigDecimal(intNroEntidades), 2, RoundingMode.HALF_UP));

			/*if(beanCapacidadCredito.getListaCapacidadDscto()== null || beanCapacidadCredito.getListaCapacidadDscto().isEmpty()){
				beanCapacidadCredito.setBdCapacidadPago(bdBaseTotYDsctos);	
			}*/
		
		
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void calcularPagoIncentGral(ActionEvent event){
		try {
			beanCapacidadCredito.setBdCapacidadPago(beanCapacidadCredito.getBdBaseCalculo().multiply(beanCapacidadCredito.getBdIndiceDescuento()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
		} catch (Exception e) {
			log.error("error" + e);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void calcularCapacPagoIncentProrrateo(ActionEvent event){
		try {
			beanCapacidadCredito.setBdCapacidadPago(beanCapacidadCredito.getBdTotalIngresos().divide(new BigDecimal(beanCapacidadCredito.getIntNroEntidades()),2, RoundingMode.HALF_UP));
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void irModificarCapacidadCredito(ActionEvent event){
		String strModalidad = getRequestParameter("pIntModalidad");
		String rowKey = getRequestParameter("rowKeyCapacidadCredito");
		SolicitudPrestamoController solicitudPrestamoController = (SolicitudPrestamoController)getSessionBean("solicitudPrestamoController");
		this.rowKey = rowKey;
		intPanelCapacidadPago = new Integer(strModalidad);
		try {
			viewCapacidadCredito(solicitudPrestamoController.getListaCapacidadCreditoComp(), rowKey);
			limpiarMsgsError();
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void irModificarCapacidadCreditoEspecial(ActionEvent event){
		String strModalidad = getRequestParameter("pIntModalidad");
		String rowKey = getRequestParameter("rowKeyCapacidadCredito");
		SolicitudEspecialController solicitudEspecialController = (SolicitudEspecialController)getSessionBean("solicitudEspecialController");
		this.rowKey = rowKey;
		intPanelCapacidadPago = new Integer(strModalidad);
		try {
			viewCapacidadCreditoEspecial(solicitudEspecialController.getListaCapacidadCreditoComp(), rowKey);
			limpiarMsgsError();
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	/**
	 * Para Refinanciamiento
	 * @param event
	 */
	public void irModificarCapacidadCreditoRef(ActionEvent event){
		String strModalidad = getRequestParameter("pIntModalidad");
		String rowKey = getRequestParameter("rowKeyCapacidadCreditoRef");
		SolicitudRefinanController solicitudRefinanController = (SolicitudRefinanController)getSessionBean("solicitudRefinanController");
		this.rowKey = rowKey;
		intPanelCapacidadPago = new Integer(strModalidad);
		try {
			viewCapacidadCreditoRef(solicitudRefinanController.getListaCapacidadCreditoComp(), rowKey);
			limpiarMsgsError();
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	/**
	 * 
	 * @param listaCapacidadCredito
	 * @param rowKey
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 */
	public void viewCapacidadCreditoRef(List<CapacidadCreditoComp> listaCapacidadCredito, String rowKey) throws BusinessException, EJBFactoryException{
		log.info("------------------Debugging SolicitudPrestamoController.viewCapacidadCredito-------------------");
	    CapacidadCreditoComp capacidadCreditoComp = null;
	    SolicitudRefinanController solicitudRefinanController = (SolicitudRefinanController)getSessionBean("solicitudRefinanController");
	    SocioComp socioComp = null;
		this.rowKey = rowKey;
		BigDecimal indDesc = new BigDecimal(0);
		
	    if(listaCapacidadCredito!=null){
	    	for(int i=0; i<listaCapacidadCredito.size(); i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					capacidadCreditoComp = listaCapacidadCredito.get(i);
				}
			}
	    }
	    if(capacidadCreditoComp.getCapacidadCredito()!=null){
	    	setBlnChkCartaAutorizacion(capacidadCreditoComp.getCapacidadCredito().getIntCartaAutorizacion()==1?true:false);
	    	setBeanCapacidadCredito(capacidadCreditoComp.getCapacidadCredito());
	    	if(capacidadCreditoComp.getCapacidadCredito().getIntParaTipoCapacidadCod().equals(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_GENERAL)){
	    		blnFormTipoIncentivoGral = true;
	    		blnFormTipoIncentivoProrrateo = false;
	    	}
	    	if(capacidadCreditoComp.getCapacidadCredito().getIntParaTipoCapacidadCod().equals(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_PRORRATEO)){
	    		blnFormTipoIncentivoProrrateo = true;
	    		blnFormTipoIncentivoGral = false;
	    	}
	    	ActionEvent event = null;
	    	calcularCapacidadPago(event);
	    	socioComp = solicitudRefinanController.getBeanSocioComp();
	    	
	    }else{
	    	ActionEvent event = null;
	    	limpiarFormCapacidadPago();
	    	indDesc=recuperarIndiceDescuentoporDefectoRef(event);
	    	beanCapacidadCredito.setBdIndiceDescuento(indDesc);
	    }
	}
	
	/**
	 * 
	 * @param listaCapacidadCredito
	 * @param rowKey
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 */
	public void viewCapacidadCredito(List<CapacidadCreditoComp> listaCapacidadCredito, String rowKey) throws BusinessException, EJBFactoryException{
		log.info("------------------Debugging SolicitudPrestamoController.viewCapacidadCredito-------------------");
	    CapacidadCreditoComp capacidadCreditoComp = null;
	    SolicitudPrestamoController solicitudPrestamoController = (SolicitudPrestamoController)getSessionBean("solicitudPrestamoController");
	    SocioComp socioComp = null;
		this.rowKey = rowKey;
		BigDecimal indDesc = new BigDecimal(0);
		
	    if(listaCapacidadCredito!=null){
	    	for(int i=0; i<listaCapacidadCredito.size(); i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					capacidadCreditoComp = listaCapacidadCredito.get(i);
				}
			}
	    }
	    if(capacidadCreditoComp.getCapacidadCredito()!=null){
	    	setBlnChkCartaAutorizacion(capacidadCreditoComp.getCapacidadCredito().getIntCartaAutorizacion()==1?true:false);
	    	setBeanCapacidadCredito(capacidadCreditoComp.getCapacidadCredito());
	    	if(capacidadCreditoComp.getCapacidadCredito().getIntParaTipoCapacidadCod().equals(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_GENERAL)){
	    		blnFormTipoIncentivoGral = true;
	    		blnFormTipoIncentivoProrrateo = false;
	    	}
	    	if(capacidadCreditoComp.getCapacidadCredito().getIntParaTipoCapacidadCod().equals(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_PRORRATEO)){
	    		blnFormTipoIncentivoProrrateo = true;
	    		blnFormTipoIncentivoGral = false;
	    	}
	    	ActionEvent event = null;
	    	calcularCapacidadPago(event);
	    	socioComp = solicitudPrestamoController.getBeanSocioComp();
	    	
	    }else{
	    	ActionEvent event = null;
	    	limpiarFormCapacidadPago();
	    	indDesc=recuperarIndiceDescuentoporDefecto(event);
	    	beanCapacidadCredito.setBdIndiceDescuento(indDesc);
	    }
	}
	
	
	/**
	 * 
	 * @param listaCapacidadCredito
	 * @param rowKey
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 */
	public void viewCapacidadCreditoEspecial(List<CapacidadCreditoComp> listaCapacidadCredito, String rowKey) throws BusinessException, EJBFactoryException{
	    CapacidadCreditoComp capacidadCreditoComp = null;
	    SolicitudEspecialController solicitudEspecialController = (SolicitudEspecialController)getSessionBean("solicitudEspecialController");
	    SocioComp socioComp = null;
		this.rowKey = rowKey;
		BigDecimal indDesc = new BigDecimal(0);
		
	    try {
			if(listaCapacidadCredito!=null){
				for(int i=0; i<listaCapacidadCredito.size(); i++){
					if(rowKey!=null && Integer.parseInt(rowKey)==i){
						capacidadCreditoComp = listaCapacidadCredito.get(i);
					}
				}
			}
			if(capacidadCreditoComp.getCapacidadCredito()!=null){
				setBlnChkCartaAutorizacion(capacidadCreditoComp.getCapacidadCredito().getIntCartaAutorizacion()==1?true:false);
				setBeanCapacidadCredito(capacidadCreditoComp.getCapacidadCredito());
				if(capacidadCreditoComp.getCapacidadCredito().getIntParaTipoCapacidadCod().equals(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_GENERAL)){
					blnFormTipoIncentivoGral = true;
					blnFormTipoIncentivoProrrateo = false;
				}
				if(capacidadCreditoComp.getCapacidadCredito().getIntParaTipoCapacidadCod().equals(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_PRORRATEO)){
					blnFormTipoIncentivoProrrateo = true;
					blnFormTipoIncentivoGral = false;
				}
				ActionEvent event = null;
				calcularCapacidadPagoEspecial(event);
				socioComp = solicitudEspecialController.getBeanSocioComp();
				
			}else{
				ActionEvent event = null;
				limpiarFormCapacidadPago();
				indDesc=recuperarIndiceDescuentoporDefectoEspecial(event);
				if(beanCapacidadCredito == null) beanCapacidadCredito= new CapacidadCredito();
				beanCapacidadCredito.setBdIndiceDescuento(indDesc);
			}
		} catch (Exception e) {
			log.error("Error en viewCapacidadCreditoEspecial --->  "+e);
		}
	}
	
	/*private Boolean isValidoCapacidadPago(CapacidadCredito beanCapacidadCredito){
		validCapacidadCredito = true;
		if (beanCapacidadCredito.getBdCuotaFija()==null) {
			setMsgTxtCuotaFija("* Debe ingresar una cuota fija.");
			validCapacidadCredito = false;
		} else {
			setMsgTxtCuotaFija("");
		}
		if ((beanCapacidadCredito.getBdCuotaFija().compareTo(beanCapacidadCredito.getBdCapacidadPago())>=0)) {
			setMsgTxtCuotaFija("* La cuota fija no puede ser mayor a la capacidad de pago");
			validCapacidadCredito = false;
		} else {
			setMsgTxtCuotaFija("");
		}
		if(beanCapacidadCredito.getBdCapacidadPago().multiply(new BigDecimal(90)).divide(
						new BigDecimal(100),2,RoundingMode.HALF_UP).compareTo(beanCapacidadCredito.getBdCuotaFija())<=0){
			setMsgTxtCuotaFija("* La cuota fija no puede exeder al 90% de la Capac. de Pago (S/." + beanCapacidadCredito.getBdCapacidadPago().multiply(new BigDecimal(90)).divide(
					new BigDecimal(100),2,RoundingMode.HALF_UP) + ").");
			validCapacidadCredito = false;
		} else {
			setMsgTxtCuotaFija("");
		}
		
	    return validCapacidadCredito;
	}*/
	

	public void grabarCapacidadCredito(ActionEvent event){
		log.info("----------CapacidadPagoController.grabarCapacidadPago----------");
		String idBtnCapacidadCredito = event.getComponent().getId();
		log.info("idBtnCapacidadCredito: " + idBtnCapacidadCredito);
		CapacidadCreditoComp capacidadCreditoComp = null;
		try {
			SolicitudPrestamoController solicitudPrestamoController = (SolicitudPrestamoController)getSessionBean("solicitudPrestamoController");
			/*if(isValidoCapacidadPago(beanCapacidadCredito) == false){
				log.info("validCapacidadCredito: " + validCapacidadCredito);
				log.info("Datos de Capacidad de Crédito no válidos. Se aborta el proceso de grabación de Crédito.");
				return;
			}*/
			
			if(idBtnCapacidadCredito!=null && idBtnCapacidadCredito.equals("btnCapacidadPagoHaberes")){
				beanCapacidadCredito.setIntParaTipoCapacidadCod(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_HABERES);
			}
			if(idBtnCapacidadCredito!=null && idBtnCapacidadCredito.equals("btnCapacidadPagoCas")){
				beanCapacidadCredito.setIntParaTipoCapacidadCod(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_CAS);
			}
			// modificado 07/02/2013
			if(beanCapacidadCredito.getBdBaseCalculo()== null){
				beanCapacidadCredito.setBdBaseCalculo(BigDecimal.ZERO);
			}

			beanCapacidadCredito.setIntCartaAutorizacion(blnChkCartaAutorizacion==true?1:0);
			beanCapacidadCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			beanCapacidadCredito.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			beanCapacidadCredito.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
			
			//for(CapacidadCreditoComp capacidadCreditoComp : solicitudPrestamoController.getListaCapacidadCreditoComp()){
			for(int i=0; i<solicitudPrestamoController.getListaCapacidadCreditoComp().size(); i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					capacidadCreditoComp = solicitudPrestamoController.getListaCapacidadCreditoComp().get(i);
					capacidadCreditoComp.setCapacidadCredito(beanCapacidadCredito);
				}
			}
			if(solicitudPrestamoController != null){
				//CGD-21.01.2014-YT
				solicitudPrestamoController.evaluarPrestamoModificar(event);
			}
			
		} catch (NumberFormatException e) {
			log.error("Error NumberFormatException en grabarCapacidadCredito ---> "+e);
			e.printStackTrace();
		} catch (ParseException e) {
			log.error("Error ParseException en grabarCapacidadCredito ---> "+e);
			e.printStackTrace();
		}
	}
	
	public void grabarCapacidadCreditoRef(ActionEvent event){
		log.info("----------CapacidadPagoController.grabarCapacidadPago----------");
		String idBtnCapacidadCredito = event.getComponent().getId();
		log.info("idBtnCapacidadCredito: " + idBtnCapacidadCredito);
		CapacidadCreditoComp capacidadCreditoComp = null;
		SolicitudRefinanController solicitudRefinanController = (SolicitudRefinanController)getSessionBean("solicitudRefinanController");
		/*if(isValidoCapacidadPago(beanCapacidadCredito) == false){
			log.info("validCapacidadCredito: " + validCapacidadCredito);
	    	log.info("Datos de Capacidad de Crédito no válidos. Se aborta el proceso de grabación de Crédito.");
	    	return;
	    }*/
		
		if(idBtnCapacidadCredito!=null && idBtnCapacidadCredito.equals("btnCapacidadPagoHaberes")){
			beanCapacidadCredito.setIntParaTipoCapacidadCod(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_HABERES);
		}
		if(idBtnCapacidadCredito!=null && idBtnCapacidadCredito.equals("btnCapacidadPagoCas")){
			beanCapacidadCredito.setIntParaTipoCapacidadCod(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_CAS);
		}
		// modificado 07/02/2013
		if(beanCapacidadCredito.getBdBaseCalculo()== null){
			beanCapacidadCredito.setBdBaseCalculo(BigDecimal.ZERO);
		}

		beanCapacidadCredito.setIntCartaAutorizacion(blnChkCartaAutorizacion==true?1:0);
		beanCapacidadCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		beanCapacidadCredito.setTsFechaRegistro(new Timestamp(new Date().getTime()));
		beanCapacidadCredito.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
		
		//for(CapacidadCreditoComp capacidadCreditoComp : solicitudPrestamoController.getListaCapacidadCreditoComp()){
		for(int i=0; i<solicitudRefinanController.getListaCapacidadCreditoComp().size(); i++){
			if(rowKey!=null && Integer.parseInt(rowKey)==i){
				capacidadCreditoComp = solicitudRefinanController.getListaCapacidadCreditoComp().get(i);
				capacidadCreditoComp.setCapacidadCredito(beanCapacidadCredito);
			}
		}
		
		if(solicitudRefinanController != null){
			//CGD-24.01.2014-YT
			solicitudRefinanController.evaluarRefinanciamiento(event);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void grabarCapacidadCreditoEspecial(ActionEvent event){
		String idBtnCapacidadCredito = event.getComponent().getId();
		log.info("idBtnCapacidadCredito: " + idBtnCapacidadCredito);
		CapacidadCreditoComp capacidadCreditoComp = null;
		SolicitudEspecialController solicitudEspecialController = (SolicitudEspecialController)getSessionBean("solicitudEspecialController");
		/*if(isValidoCapacidadPago(beanCapacidadCredito) == false){
			log.info("validCapacidadCredito: " + validCapacidadCredito);
	    	log.info("Datos de Capacidad de Crédito no válidos. Se aborta el proceso de grabación de Crédito.");
	    	return;
	    }*/
		
		try {
			if(idBtnCapacidadCredito!=null && idBtnCapacidadCredito.equals("btnCapacidadPagoHaberes")){
				beanCapacidadCredito.setIntParaTipoCapacidadCod(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_HABERES);
			}
			if(idBtnCapacidadCredito!=null && idBtnCapacidadCredito.equals("btnCapacidadPagoCas")){
				beanCapacidadCredito.setIntParaTipoCapacidadCod(Constante.PARAM_T_TIPOCAPACIDADINCENTIVO_CAS);
			}
			// modificado 07/02/2013
			if(beanCapacidadCredito.getBdBaseCalculo()== null){
				beanCapacidadCredito.setBdBaseCalculo(BigDecimal.ZERO);
			}

			beanCapacidadCredito.setIntCartaAutorizacion(blnChkCartaAutorizacion==true?1:0);
			beanCapacidadCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			beanCapacidadCredito.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			beanCapacidadCredito.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
			
			//for(CapacidadCreditoComp capacidadCreditoComp : solicitudPrestamoController.getListaCapacidadCreditoComp()){
			for(int i=0; i<solicitudEspecialController.getListaCapacidadCreditoComp().size(); i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					capacidadCreditoComp = solicitudEspecialController.getListaCapacidadCreditoComp().get(i);
					capacidadCreditoComp.setCapacidadCredito(beanCapacidadCredito);
				}
			}
			
			if(solicitudEspecialController != null){
				//CGD-24.01.2014-YT
				solicitudEspecialController.evaluarPrestamo(event);
			}
		} catch (Exception e) {
			log.error("Error en grabarCapacidadCreditoEspecial ---> "+e);
			e.printStackTrace();
		}
	}
	
	/********************************************************************/
	/*                                                            		*/
	/*  Nombre :  recuperarIndiceDescuentoporDefecto()            		*/
	/*                                                            		*/
	/*  Parametros. :  ActionEvent event                          		*/ 
	/*                                                            		*/
	/*  Objetivo:  recuperar el Indice de Descuento configurado en BD 	*/
	/*                                                            		*/
	/*  Retorno :  BigDecimal indice                              		*/
	/********************************************************************/

	public BigDecimal recuperarIndiceDescuentoporDefecto(ActionEvent event) throws EJBFactoryException, BusinessException {
        BigDecimal indice= new BigDecimal(0);
        log.info("------------------------Debugging CapacidadPagoController.recuperarIndiceDescuentoporDefecto------------------------");
        EstructuraDetalle estructuraDetalle = null;
        ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
        SocioComp socioComp = null;
        estructuraDetalle = new EstructuraDetalle();
        convenioEstructuraDetalle = new ConvenioEstructuraDetalle();
        socioComp = new SocioComp();
        
        SolicitudPrestamoController solicitudPrestamoController = (SolicitudPrestamoController)getSessionBean("solicitudPrestamoController");  
        socioComp = solicitudPrestamoController.getBeanSocioComp();

        estructuraDetalle.setId(new EstructuraDetalleId());
        estructuraDetalle.getId().setIntNivel(socioComp.getSocio().getSocioEstructura().getIntNivel());
        estructuraDetalle.getId().setIntCodigo(socioComp.getSocio().getSocioEstructura().getIntCodigo());
        estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), socioComp.getSocio().getSocioEstructura().getIntTipoSocio(), socioComp.getSocio().getSocioEstructura().getIntModalidad());
    	convenioEstructuraDetalle = estructuraFacade.getConvenioEstructuraDetallePorPkEstructuraDetalle(estructuraDetalle.getId());
        indice = convenioEstructuraDetalle.getBdIndiceDescuento();  
        
     return indice;
	}
	
	/**
	 * 
	 * @param event
	 * @return
	 * @throws EJBFactoryException
	 * @throws BusinessException
	 */
	public BigDecimal recuperarIndiceDescuentoporDefectoEspecial(ActionEvent event) throws EJBFactoryException, BusinessException {
        BigDecimal indice= new BigDecimal(0);
        EstructuraDetalle estructuraDetalle = null;
        ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
        SocioComp socioComp = null;
        estructuraDetalle = new EstructuraDetalle();
        convenioEstructuraDetalle = new ConvenioEstructuraDetalle();
        socioComp = new SocioComp();
        
        SolicitudEspecialController solicitudEspecialController = (SolicitudEspecialController)getSessionBean("solicitudEspecialController");  
        socioComp = solicitudEspecialController.getBeanSocioComp();

        estructuraDetalle.setId(new EstructuraDetalleId());
        estructuraDetalle.getId().setIntNivel(socioComp.getSocio().getSocioEstructura().getIntNivel());
        estructuraDetalle.getId().setIntCodigo(socioComp.getSocio().getSocioEstructura().getIntCodigo());
        estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), socioComp.getSocio().getSocioEstructura().getIntTipoSocio(), socioComp.getSocio().getSocioEstructura().getIntModalidad());
    	convenioEstructuraDetalle = estructuraFacade.getConvenioEstructuraDetallePorPkEstructuraDetalle(estructuraDetalle.getId());
        indice = convenioEstructuraDetalle.getBdIndiceDescuento();  
        
     return indice;
	}
	
	public BigDecimal recuperarIndiceDescuentoporDefectoRef(ActionEvent event) throws EJBFactoryException, BusinessException {
        BigDecimal indice= new BigDecimal(0);
        log.info("------------------------Debugging CapacidadPagoController.recuperarIndiceDescuentoporDefecto------------------------");
        EstructuraDetalle estructuraDetalle = null;
        ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
        SocioComp socioComp = null;
        estructuraDetalle = new EstructuraDetalle();
        convenioEstructuraDetalle = new ConvenioEstructuraDetalle();
        socioComp = new SocioComp();
        
        SolicitudRefinanController solicitudRefinanController = (SolicitudRefinanController)getSessionBean("solicitudRefinanController");  
        socioComp = solicitudRefinanController.getBeanSocioComp();

        estructuraDetalle.setId(new EstructuraDetalleId());
        estructuraDetalle.getId().setIntNivel(socioComp.getSocio().getSocioEstructura().getIntNivel());
        estructuraDetalle.getId().setIntCodigo(socioComp.getSocio().getSocioEstructura().getIntCodigo());
        estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), socioComp.getSocio().getSocioEstructura().getIntTipoSocio(), socioComp.getSocio().getSocioEstructura().getIntModalidad());
    	convenioEstructuraDetalle = estructuraFacade.getConvenioEstructuraDetallePorPkEstructuraDetalle(estructuraDetalle.getId());
        indice = convenioEstructuraDetalle.getBdIndiceDescuento();  
        
     return indice;
	}
	
	public BigDecimal recuperarIndiceDescuentoporDefectoEsp(ActionEvent event) throws EJBFactoryException, BusinessException {
        BigDecimal indice= new BigDecimal(0);
        log.info("------------------------Debugging CapacidadPagoController.recuperarIndiceDescuentoporDefecto------------------------");
        EstructuraDetalle estructuraDetalle = null;
        ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
        SocioComp socioComp = null;
        estructuraDetalle = new EstructuraDetalle();
        convenioEstructuraDetalle = new ConvenioEstructuraDetalle();
        socioComp = new SocioComp();
        
        SolicitudEspecialController solicitudEspecialController = (SolicitudEspecialController)getSessionBean("solicitudEspecialController");  
        socioComp = solicitudEspecialController.getBeanSocioComp();

        estructuraDetalle.setId(new EstructuraDetalleId());
        estructuraDetalle.getId().setIntNivel(socioComp.getSocio().getSocioEstructura().getIntNivel());
        estructuraDetalle.getId().setIntCodigo(socioComp.getSocio().getSocioEstructura().getIntCodigo());
        estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), socioComp.getSocio().getSocioEstructura().getIntTipoSocio(), socioComp.getSocio().getSocioEstructura().getIntModalidad());
    	convenioEstructuraDetalle = estructuraFacade.getConvenioEstructuraDetallePorPkEstructuraDetalle(estructuraDetalle.getId());
        indice = convenioEstructuraDetalle.getBdIndiceDescuento();  
        
     return indice;
	}
	
	
}