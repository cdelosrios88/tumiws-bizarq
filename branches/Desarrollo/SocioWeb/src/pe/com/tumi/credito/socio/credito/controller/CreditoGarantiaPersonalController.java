package pe.com.tumi.credito.socio.credito.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoTipoGarantiaComp;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoGarantiaFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;

/************************************************************************/
/* Nombre de la clase: CreditoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de Aportaciones */
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 09/04/2012 */
/* ********************************************************************* */

public class CreditoGarantiaPersonalController {
	protected  static Logger 	log 			= Logger.getLogger(CreditoGarantiaPersonalController.class);
	private int 				rows = 5;
	private CreditoGarantia		beanGarantiaPersonal = new CreditoGarantia();
	private List<Tabla> 		listaNaturalezaGarantia;
	private List<CreditoGarantia> listaGarantiaPersonal;
	private List<Tabla> 		listaClaseGarantia;
	private List<Tabla> 		listaSubClaseGarantia;
	private String				strCreditoGarantiaPersonal;
	
	//Variables del formulario para grabar dentro del Tipo de Garantía
	private Integer 			intIdTipoOpcion;
	private Integer 			intIdTipoObligatorio;
	private String[]			lstCondicionSocio;
	private String[]			lstTipoHabil;
	private String[]			lstCondicionLaboral;
	private String[]			lstSituacionLaboral;
	private Integer 			intEdadLimite;
	private Integer 			intTiempoMinContrato;
	private Integer 			intIdTipoTiempoMinContrato;
	private Integer 			intIdTipoDsctoJudicial;
	private BigDecimal 			bdPorcentajeAporteMin;
	private Integer 			intNumeroMaximoGarantia;
	private CreditoController	creditoController;
	
	private List<CreditoTipoGarantiaComp> listaTipoGarantiaComp = new ArrayList<CreditoTipoGarantiaComp>();
	
	//Getters y Setters
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public CreditoGarantia getBeanGarantiaPersonal() {
		return beanGarantiaPersonal;
	}
	public void setBeanGarantiaPersonal(CreditoGarantia beanGarantiaPersonal) {
		this.beanGarantiaPersonal = beanGarantiaPersonal;
	}
	public List<Tabla> getListaNaturalezaGarantia() {
		return listaNaturalezaGarantia;
	}
	public void setListaNaturalezaGarantia(List<Tabla> listaNaturalezaGarantia) {
		this.listaNaturalezaGarantia = listaNaturalezaGarantia;
	}
	public List<CreditoGarantia> getListaGarantiaPersonal() {
		return listaGarantiaPersonal;
	}
	public void setListaGarantiaPersonal(List<CreditoGarantia> listaGarantiaPersonal) {
		this.listaGarantiaPersonal = listaGarantiaPersonal;
	}
	public List<Tabla> getListaClaseGarantia() {
		return listaClaseGarantia;
	}
	public void setListaClaseGarantia(List<Tabla> listaClaseGarantia) {
		this.listaClaseGarantia = listaClaseGarantia;
	}
	public List<Tabla> getListaSubClaseGarantia() {
		return listaSubClaseGarantia;
	}
	public void setListaSubClaseGarantia(List<Tabla> listaSubClaseGarantia) {
		this.listaSubClaseGarantia = listaSubClaseGarantia;
	}
	public String getStrCreditoGarantiaPersonal() {
		return strCreditoGarantiaPersonal;
	}
	public void setStrCreditoGarantiaPersonal(String strCreditoGarantiaPersonal) {
		this.strCreditoGarantiaPersonal = strCreditoGarantiaPersonal;
	}
	public Integer getIntIdTipoOpcion() {
		return intIdTipoOpcion;
	}
	public void setIntIdTipoOpcion(Integer intIdTipoOpcion) {
		this.intIdTipoOpcion = intIdTipoOpcion;
	}
	public Integer getIntIdTipoObligatorio() {
		return intIdTipoObligatorio;
	}
	public void setIntIdTipoObligatorio(Integer intIdTipoObligatorio) {
		this.intIdTipoObligatorio = intIdTipoObligatorio;
	}
	public String[] getLstCondicionSocio() {
		return lstCondicionSocio;
	}
	public void setLstCondicionSocio(String[] lstCondicionSocio) {
		this.lstCondicionSocio = lstCondicionSocio;
	}
	public String[] getLstTipoHabil() {
		return lstTipoHabil;
	}
	public void setLstTipoHabil(String[] lstTipoHabil) {
		this.lstTipoHabil = lstTipoHabil;
	}
	public String[] getLstCondicionLaboral() {
		return lstCondicionLaboral;
	}
	public void setLstCondicionLaboral(String[] lstCondicionLaboral) {
		this.lstCondicionLaboral = lstCondicionLaboral;
	}
	public String[] getLstSituacionLaboral() {
		return lstSituacionLaboral;
	}
	public void setLstSituacionLaboral(String[] lstSituacionLaboral) {
		this.lstSituacionLaboral = lstSituacionLaboral;
	}
	public Integer getIntEdadLimite() {
		return intEdadLimite;
	}
	public void setIntEdadLimite(Integer intEdadLimite) {
		this.intEdadLimite = intEdadLimite;
	}
	public Integer getIntTiempoMinContrato() {
		return intTiempoMinContrato;
	}
	public void setIntTiempoMinContrato(Integer intTiempoMinContrato) {
		this.intTiempoMinContrato = intTiempoMinContrato;
	}
	public Integer getIntIdTipoTiempoMinContrato() {
		return intIdTipoTiempoMinContrato;
	}
	public void setIntIdTipoTiempoMinContrato(Integer intIdTipoTiempoMinContrato) {
		this.intIdTipoTiempoMinContrato = intIdTipoTiempoMinContrato;
	}
	public Integer getIntIdTipoDsctoJudicial() {
		return intIdTipoDsctoJudicial;
	}
	public void setIntIdTipoDsctoJudicial(Integer intIdTipoDsctoJudicial) {
		this.intIdTipoDsctoJudicial = intIdTipoDsctoJudicial;
	}
	public BigDecimal getBdPorcentajeAporteMin() {
		return bdPorcentajeAporteMin;
	}
	public void setBdPorcentajeAporteMin(BigDecimal bdPorcentajeAporteMin) {
		this.bdPorcentajeAporteMin = bdPorcentajeAporteMin;
	}
	public Integer getIntNumeroMaximoGarantia() {
		return intNumeroMaximoGarantia;
	}
	public void setIntNumeroMaximoGarantia(Integer intNumeroMaximoGarantia) {
		this.intNumeroMaximoGarantia = intNumeroMaximoGarantia;
	}
	public List<CreditoTipoGarantiaComp> getListaTipoGarantiaComp() {
		return listaTipoGarantiaComp;
	}
	public void setListaTipoGarantiaComp(
			List<CreditoTipoGarantiaComp> listaTipoGarantiaComp) {
		this.listaTipoGarantiaComp = listaTipoGarantiaComp;
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	// Métodos a implementar
	/**************************************************************/
	/*  Nombre :  limpiarCredito()     					      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void habilitarGrabarGarantiaPersonal(ActionEvent event){
		log.info("---------entro al método habilitarGrabarCreditoDscto---------");
		limpiarFormGarantiaPersonal();
		strCreditoGarantiaPersonal = Constante.MANTENIMIENTO_GRABAR;
		reloadCboClaseGarantia(new Integer(Constante.CREDITOS_GARANTIA_PERSONAL));
		log.info("strCreditoGarantiaPersonal: "+ strCreditoGarantiaPersonal);
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarCredito()     					      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarFormGarantiaPersonal(){
		CreditoGarantia garantiaPersonal = new CreditoGarantia();
		garantiaPersonal.setId(new CreditoGarantiaId());
		setBeanGarantiaPersonal(garantiaPersonal);
		setIntIdTipoOpcion(null);
		setIntIdTipoObligatorio(null);
		setIntEdadLimite(null);
		setIntTiempoMinContrato(null);
		setIntIdTipoTiempoMinContrato(null);
		setIntIdTipoDsctoJudicial(null);
		setBdPorcentajeAporteMin(null);
		setIntNumeroMaximoGarantia(null);
		if(lstCondicionSocio!=null){
			setLstCondicionSocio(null);
		}
		if(lstTipoHabil!=null){
			setLstTipoHabil(null);
		}
		if(lstCondicionLaboral!=null){
			setLstCondicionLaboral(null);
		}
		if(lstSituacionLaboral!=null){
			setLstSituacionLaboral(null);
		}
		if(listaTipoGarantiaComp!=null){
			listaTipoGarantiaComp.clear();
		}
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  cancelarGrabarCredito() 		          			*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*                                                    	 		*/
	/*  Objetivo: Cancelar la nueva Hoja de Planeamiento     		*/
	/*            Poblacional.                               	  	*/
	/*  Retorno : Se oculta el Formulario de Aportaciones		 	*/
	/**************************************************************/
	public void cancelarGrabarCredito(ActionEvent event) {
		limpiarFormGarantiaPersonal();
	}
	
	public void reloadCboClaseGarantia(Integer intIdTipoGarantia) {
		log.info("-----------------------Debugging CreditoController.reloadCboTipoCreditoSbs()-----------------------------");
		TablaFacadeRemote remote;
		try {
			remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			try {
				listaClaseGarantia = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_CLASE_GARANTIA), intIdTipoGarantia);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reloadCboSubClase(ValueChangeEvent event) throws EJBFactoryException, BusinessException {
		log.info("-----------------------Debugging CreditoController.reloadCboSubClase()-----------------------------");
		Integer intIdTipoClase = Integer.parseInt(""+event.getNewValue());
		log.info("intIdTipoClase = "+intIdTipoClase);
		TablaFacadeRemote remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
	    listaSubClaseGarantia = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_SUBCLASE_GARANTIA), intIdTipoClase);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarGarantiaPersonal()        					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Listar las Garantías de Tipo Personal				*/
	/*  Retorno : Devuelve el listado de Garantías del crédito 		*/
	/**************************************************************/
	public void listarGarantiaPersonal(ActionEvent event) {
		CreditoGarantiaFacadeLocal facade = null;
		creditoController = (CreditoController)getSessionBean("creditoController");
		try {
			facade = (CreditoGarantiaFacadeLocal)EJBFactory.getLocal(CreditoGarantiaFacadeLocal.class);
			CreditoGarantia o = new CreditoGarantia();
			o.setId(new CreditoGarantiaId());
			o.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());
			o.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
			o.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
			o.getId().setIntParaTipoGarantiaCod(Constante.CREDITOS_GARANTIA_PERSONAL);
			listaGarantiaPersonal = facade.getListaCreditoGarantia(o.getId());
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	/**************************************************************/
	/*                                                    	 			*/
	/*  Nombre :  irModificarDescuento()        						*/
	/*                                                    	 			*/
	/*  Parametros. :  event       descripcion            	 			*/
	/*                         						     	 			*/
	/*  Objetivo: Obtener los datos grabados para la parte de Garantías	*/
	/*  Retorno : Devuelve la garantía ingresada previamente 			*/
	/**************************************************************/
	public void irModificarGarantia(ActionEvent event) throws ParseException{
    	log.info("-----------------------Debugging CreditoController.irModificarGarantia-----------------------------");
    	CreditoGarantiaFacadeLocal creditoGarantiaFacade = null;
    	TablaFacadeRemote remote = null;
    	String pIntIdEmpresa = getRequestParameter("pIntIdEmpresa");
    	String pIntIdTipoCredito = getRequestParameter("pIntIdTipoCredito");
    	String pIntIdItemCredito = getRequestParameter("pIntIdItemCredito");
    	String pIntIdTipoGarantia= getRequestParameter("pIntIdTipoGarantia");
    	String pIntIdItemCreditoGarantia = getRequestParameter("pIntIdItemCreditoGarantia");
		CreditoGarantia creditoGarantia = null;
    	
    	try {
    		if(pIntIdItemCreditoGarantia != null && !pIntIdItemCreditoGarantia.trim().equals("")){
    			creditoGarantia = new CreditoGarantia();
    			creditoGarantia.setId(new CreditoGarantiaId());
    			creditoGarantia.getId().setIntPersEmpresaPk(new Integer(pIntIdEmpresa));
    			creditoGarantia.getId().setIntParaTipoCreditoCod(new Integer(pIntIdTipoCredito));
    			creditoGarantia.getId().setIntItemCredito(new Integer(pIntIdItemCredito));
    			creditoGarantia.getId().setIntParaTipoGarantiaCod(new Integer(pIntIdTipoGarantia));
    			creditoGarantia.getId().setIntItemCreditoGarantia(new Integer(pIntIdItemCreditoGarantia));
    			creditoGarantiaFacade = (CreditoGarantiaFacadeLocal)EJBFactory.getLocal(CreditoGarantiaFacadeLocal.class);
    			
    			beanGarantiaPersonal = creditoGarantiaFacade.getCreditoGarantiaPorIdCreditoGarantia(creditoGarantia.getId());
    			reloadCboClaseGarantia(new Integer(Constante.CREDITOS_GARANTIA_PERSONAL));
    			remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
    		    listaSubClaseGarantia = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_SUBCLASE_GARANTIA), beanGarantiaPersonal.getIntParaClaseCod());
    		    
    		    if(beanGarantiaPersonal.getListaTipoGarantiaComp()!=null && beanGarantiaPersonal.getListaTipoGarantiaComp().size()>0){
    		    	listaTipoGarantiaComp = beanGarantiaPersonal.getListaTipoGarantiaComp();
    		    }
				strCreditoGarantiaPersonal = Constante.MANTENIMIENTO_MODIFICAR;
    		}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  grabarGarantiaPersonal()        					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Guardar los datos ingresados en la grilla de Tipo	*/
	/*            de Garantía					     	 			*/
	/*                         						     	 		*/
	/*  Retorno : Datos grabados correctamente en la tabla de 	 	*/
	/*            CSO_CCGARANTIASTIPOS				     	 		*/
	/**/
	/***********************************************************************************/
	public void grabarGarantiaPersonal(ActionEvent event){
		CreditoGarantiaFacadeLocal facade = null;
		creditoController = (CreditoController)getSessionBean("creditoController");
	    /*if(isValidoAportacion(beanGarantiaPersonal) == false){
	    	log.info("Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.");
	    	return;
	    }*/
		beanGarantiaPersonal.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());
		beanGarantiaPersonal.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
		beanGarantiaPersonal.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
		beanGarantiaPersonal.getId().setIntParaTipoGarantiaCod(Constante.CREDITOS_GARANTIA_PERSONAL);
		beanGarantiaPersonal.setIntOpcionMinimoCta(0);
		beanGarantiaPersonal.setIntOpcionValorVenta(0);
		beanGarantiaPersonal.setIntValuadorInscrito(0);
		beanGarantiaPersonal.setIntTasacionComercial(0);
		beanGarantiaPersonal.setIntNegociacionEndoso(0);
		beanGarantiaPersonal.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		if(listaTipoGarantiaComp!=null && listaTipoGarantiaComp.size()>0){
			beanGarantiaPersonal.setListaTipoGarantiaComp(listaTipoGarantiaComp);
		}
		
		try {
			facade = (CreditoGarantiaFacadeLocal)EJBFactory.getLocal(CreditoGarantiaFacadeLocal.class);
			facade.grabarCreditoGarantia(beanGarantiaPersonal);
			listarGarantiaPersonal(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  modificarGarantia()      							*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Guardar los datos ingresados en la ventan de 		*/
	/*            Creditos 						     	 			*/
	/*                         						     	 		*/
	/*  Retorno : Datos grabados correctamente en la tabla de 	 	*/
	/*            CSO_CONFCREDITOS					     	 		*/
	/**/
	/***********************************************************************************/
	public void modificarGarantiaPersonal(ActionEvent event){
		log.info("------------entrando al método CreditoDescuentoController.modificarDescuento------------");
		CreditoGarantiaFacadeLocal facade = null;
		creditoController = (CreditoController)getSessionBean("creditoController");
		
		beanGarantiaPersonal.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());
		beanGarantiaPersonal.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
		beanGarantiaPersonal.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
		beanGarantiaPersonal.getId().setIntParaTipoGarantiaCod(Constante.CREDITOS_GARANTIA_PERSONAL);
		beanGarantiaPersonal.setIntOpcionMinimoCta(0);
		beanGarantiaPersonal.setIntOpcionValorVenta(0);
		beanGarantiaPersonal.setIntValuadorInscrito(0);
		beanGarantiaPersonal.setIntTasacionComercial(0);
		beanGarantiaPersonal.setIntNegociacionEndoso(0);
		if(listaTipoGarantiaComp!=null && listaTipoGarantiaComp.size()>0){
			beanGarantiaPersonal.setListaTipoGarantiaComp(listaTipoGarantiaComp);
		}
		
		try {
			facade = (CreditoGarantiaFacadeLocal)EJBFactory.getLocal(CreditoGarantiaFacadeLocal.class);
			facade.modificarCreditoGarantia(beanGarantiaPersonal);
			listarGarantiaPersonal(event);
			limpiarFormGarantiaPersonal();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 			*/
	/*  Nombre :  addTipoGarantia()        								*/
	/*                                                    	 			*/
	/*  Parametros. :  event       descripcion            	 			*/
	/*                         						     	 			*/
	/*  Objetivo: Agregar los datos toda la información para un tipo	*/
	/*            de garante a la grilla de Tipos de Garantía			*/
	/*                         						     	 			*/
	/*  Retorno : Los datos aparecen en la grilla de Tipo de Garantía 	*/
	/**/
	/**
	 * @throws EJBFactoryException 
	 * @throws BusinessException 
	 * @throws NumberFormatException *********************************************************************************/
	public void addTipoGarantia(ActionEvent event) throws EJBFactoryException, NumberFormatException, BusinessException{
		log.info("--------------CreditoGarantiaPersonalController.addTipoGarantia-------------");
		CreditoTipoGarantiaComp creditoTipoGarantiaComp = null;
		List<CondicionSocioTipoGarantia> listaCondicionSocio = new ArrayList<CondicionSocioTipoGarantia>();
		List<CondicionHabilTipoGarantia> listaTipoHabil = new ArrayList<CondicionHabilTipoGarantia>();
		List<CondicionLaboralTipoGarantia> listaCondicionLaboral = new ArrayList<CondicionLaboralTipoGarantia>();
		List<SituacionLaboralTipoGarantia> listaSituacionLaboral = new ArrayList<SituacionLaboralTipoGarantia>();
		CondicionSocioTipoGarantia condicionSocioTipoGarantia = null;
		CondicionHabilTipoGarantia condicionHabilTipoGarantia = null;
		CondicionLaboralTipoGarantia condicionLaboralTipoGarantia = null;
		SituacionLaboralTipoGarantia situacionLaboralTipoGarantia = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTablaCondSocio = new ArrayList<Tabla>();
		List<Tabla> listaTablaCondLaboral = new ArrayList<Tabla>();
		List<Tabla> listaTablaSituacionLaboral = new ArrayList<Tabla>();
		
		Boolean bValidar = true;
		
		/*if(getIntTipoBeneficiario()!=null && getIntTipoBeneficiario()==0){
			setMsgTxtTipoBeneficiario("* Debe ingresar el tipo de Beneficiario");
			bValidar = false;
		}else{
			setMsgTxtTipoBeneficiario("");
		}*/
		
		if(bValidar == true){
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTablaCondSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
			listaTablaCondLaboral = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONLABORAL));
			listaTablaSituacionLaboral = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			try{
				creditoTipoGarantiaComp = new CreditoTipoGarantiaComp();
				creditoTipoGarantiaComp.setCreditoTipoGarantia(new CreditoTipoGarantia());
				creditoTipoGarantiaComp.getCreditoTipoGarantia().setId(new CreditoTipoGarantiaId());
				creditoTipoGarantiaComp.getCreditoTipoGarantia().setIntParaTipoOpcionCod(getIntIdTipoOpcion());
				creditoTipoGarantiaComp.getCreditoTipoGarantia().setIntParaTipoObligatorioCod(getIntIdTipoObligatorio());
				if(lstCondicionSocio!=null && lstCondicionSocio.length>0){
					for(int i=0;i<lstCondicionSocio.length;i++){
						condicionSocioTipoGarantia = new CondicionSocioTipoGarantia();
						condicionSocioTipoGarantia.setId(new CondicionSocioTipoGarantiaId());
						condicionSocioTipoGarantia.getId().setIntParaCondicionSocioCod(new Integer(lstCondicionSocio[i]));
						listaCondicionSocio.add(condicionSocioTipoGarantia);
					}
					creditoTipoGarantiaComp.getCreditoTipoGarantia().setListaCondicionSocio(listaCondicionSocio);
				}
				String csvStrCondSocio = null;
				if(creditoTipoGarantiaComp.getCreditoTipoGarantia().getListaCondicionSocio()!=null){
					for(int i=0;i<listaTablaCondSocio.size();i++){
						for(int j=0;j<creditoTipoGarantiaComp.getCreditoTipoGarantia().getListaCondicionSocio().size();j++){
							if(listaTablaCondSocio.get(i).getIntIdDetalle().equals(creditoTipoGarantiaComp.getCreditoTipoGarantia().getListaCondicionSocio().get(j).getId().getIntParaCondicionSocioCod())){
								if(csvStrCondSocio == null)
									csvStrCondSocio = String.valueOf(listaTablaCondSocio.get(i).getStrDescripcion());
								else
									csvStrCondSocio = csvStrCondSocio + " / " +listaTablaCondSocio.get(i).getStrDescripcion();
							}
						}
					}
				}
				creditoTipoGarantiaComp.setStrCondicionSocio(csvStrCondSocio);
				
				if(lstTipoHabil!=null && lstTipoHabil.length>0){
					for(int i=0;i<lstTipoHabil.length;i++){
						condicionHabilTipoGarantia = new CondicionHabilTipoGarantia();
						condicionHabilTipoGarantia.setId(new CondicionHabilTipoGarantiaId());
						condicionHabilTipoGarantia.getId().setIntParaTipoHabilCod(new Integer(lstTipoHabil[i]));
						listaTipoHabil.add(condicionHabilTipoGarantia);
					}
					creditoTipoGarantiaComp.getCreditoTipoGarantia().setListaTipoCondicion(listaTipoHabil);
				}
				
				if(lstCondicionLaboral!=null && lstCondicionLaboral.length>0){
					for(int i=0;i<lstCondicionLaboral.length;i++){
						condicionLaboralTipoGarantia = new CondicionLaboralTipoGarantia();
						condicionLaboralTipoGarantia.setId(new CondicionLaboralTipoGarantiaId());
						condicionLaboralTipoGarantia.getId().setIntParaCondicionLaboralCod(new Integer(lstCondicionLaboral[i]));
						listaCondicionLaboral.add(condicionLaboralTipoGarantia);
					}
					creditoTipoGarantiaComp.getCreditoTipoGarantia().setListaCondicionLaboral(listaCondicionLaboral);
				}
				
				String csvStrCondLaboral = null;
				if(creditoTipoGarantiaComp.getCreditoTipoGarantia().getListaCondicionLaboral()!=null){
					for(int i=0;i<listaTablaCondLaboral.size();i++){
						for(int j=0;j<creditoTipoGarantiaComp.getCreditoTipoGarantia().getListaCondicionLaboral().size();j++){
							if(listaTablaCondLaboral.get(i).getIntIdDetalle().equals(creditoTipoGarantiaComp.getCreditoTipoGarantia().getListaCondicionLaboral().get(j).getId().getIntParaCondicionLaboralCod())){
								if(csvStrCondLaboral == null)
									csvStrCondLaboral = String.valueOf(listaTablaCondLaboral.get(i).getStrDescripcion());
								else
									csvStrCondLaboral = csvStrCondLaboral + " / " +listaTablaCondLaboral.get(i).getStrDescripcion();
							}
						}
					}
				}
				creditoTipoGarantiaComp.setStrCondicionLaboral(csvStrCondLaboral);
				
				if(lstSituacionLaboral!=null && lstSituacionLaboral.length>0){
					for(int i=0;i<lstSituacionLaboral.length;i++){
						situacionLaboralTipoGarantia = new SituacionLaboralTipoGarantia();
						situacionLaboralTipoGarantia.setId(new SituacionLaboralTipoGarantiaId());
						situacionLaboralTipoGarantia.getId().setIntParaSituacionLaboralCod(new Integer(lstSituacionLaboral[i]));
						listaSituacionLaboral.add(situacionLaboralTipoGarantia);
					}
					creditoTipoGarantiaComp.getCreditoTipoGarantia().setListaSituacionLaboral(listaSituacionLaboral);
				}
				
				String csvStrSituacionLaboral = null;
				if(creditoTipoGarantiaComp.getCreditoTipoGarantia().getListaSituacionLaboral()!=null){
					for(int i=0;i<listaTablaSituacionLaboral.size();i++){
						for(int j=0;j<creditoTipoGarantiaComp.getCreditoTipoGarantia().getListaSituacionLaboral().size();j++){
							if(listaTablaSituacionLaboral.get(i).getIntIdDetalle().equals(creditoTipoGarantiaComp.getCreditoTipoGarantia().getListaSituacionLaboral().get(j).getId().getIntParaSituacionLaboralCod())){
								if(csvStrSituacionLaboral == null)
									csvStrSituacionLaboral = String.valueOf(listaTablaSituacionLaboral.get(i).getStrDescripcion());
								else
									csvStrSituacionLaboral = csvStrSituacionLaboral + " / " +listaTablaSituacionLaboral.get(i).getStrDescripcion();
							}
						}
					}
				}
				creditoTipoGarantiaComp.setStrSituacionLaboral(csvStrSituacionLaboral);
				creditoTipoGarantiaComp.getCreditoTipoGarantia().setIntEdadLimite(getIntEdadLimite());
				creditoTipoGarantiaComp.getCreditoTipoGarantia().setIntTiempoMinimoContrato(getIntTiempoMinContrato());
				creditoTipoGarantiaComp.getCreditoTipoGarantia().setIntParaTipoTiempoContratoCod(getIntIdTipoTiempoMinContrato());
				creditoTipoGarantiaComp.getCreditoTipoGarantia().setIntParaTipoDsctoJudicialCod(getIntIdTipoDsctoJudicial());
				creditoTipoGarantiaComp.getCreditoTipoGarantia().setBdPorcentajeAporteMinimo(getBdPorcentajeAporteMin());
				creditoTipoGarantiaComp.getCreditoTipoGarantia().setIntNumeroMaximoGarantia(getIntNumeroMaximoGarantia());
				listaTipoGarantiaComp.add(creditoTipoGarantiaComp);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void removeTipoGarantia(ActionEvent event){
		log.info("--------------------CreditoGarantiaPersonalController.removeTipoGarantia-----------------------");
		String rowKey = getRequestParameter("rowKeyTipoGarantia");
		CreditoTipoGarantiaComp creditoTipoGarantiaCompTemp = null;
		if(listaTipoGarantiaComp!=null){
			for(int i=0; i<listaTipoGarantiaComp.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					CreditoTipoGarantiaComp creditoTipoGarantiaComp = listaTipoGarantiaComp.get(i);
					if(creditoTipoGarantiaComp.getCreditoTipoGarantia().getId()!=null && creditoTipoGarantiaComp.getCreditoTipoGarantia().getId().getIntItemGarantiaTipo()!=null){
						creditoTipoGarantiaCompTemp = listaTipoGarantiaComp.get(i);
						creditoTipoGarantiaCompTemp.getCreditoTipoGarantia().setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaTipoGarantiaComp.remove(i);
					break;
				}
			}
			if(creditoTipoGarantiaCompTemp!=null){
				listaTipoGarantiaComp.add(creditoTipoGarantiaCompTemp);
			}
		}
	}
	/*public void removeTipoGarantia(ActionEvent event){
		log.info("------------------------CreditoController.removeRangoInteres----------------------------");
		List<CreditoTipoGarantiaComp> arrayCreditoTipoGarantia = new ArrayList();
	    for(int i=0; i<getListaTipoGarantiaComp().size(); i++){
	    	CreditoTipoGarantiaComp creditoTipoGarantiaComp = new CreditoTipoGarantiaComp();
	    	creditoTipoGarantiaComp = (CreditoTipoGarantiaComp) getListaTipoGarantiaComp().get(i);
	    	if(creditoTipoGarantiaComp.getChkTipoGarantia() == false){
	    		arrayCreditoTipoGarantia.add(creditoTipoGarantiaComp);
	    	}
	    }
	    listaTipoGarantiaComp.clear();
	    setListaTipoGarantiaComp(arrayCreditoTipoGarantia);
	}*/
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  eliminarGarantiaPersonal()        				*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Eliminar un registro grabado en la bd		 		*/
	/**/
	/***********************************************************************************/
	public void eliminarGarantiaPersonal(ActionEvent event){
    	log.info("-----------------------Debugging CreditoDescuentoController.eliminarDescuento------------------------");
    	String pIntIdEmpresa = getRequestParameter("pIntIdEmpresa");
    	String pIntIdTipoCredito = getRequestParameter("pIntIdTipoCredito");
    	String pIntIdItemCredito = getRequestParameter("pIntIdItemCredito");
    	String pIntIdTipoGarantia= getRequestParameter("pIntIdTipoGarantia");
    	String pIntIdItemCreditoGarantia = getRequestParameter("pIntIdItemCreditoGarantia");
		CreditoGarantiaFacadeLocal facade = null;
		CreditoGarantia creditoGarantia = null;
    	try {
    		creditoGarantia = new CreditoGarantia();
    		creditoGarantia.setId(new CreditoGarantiaId());
    		creditoGarantia.getId().setIntPersEmpresaPk(new Integer(pIntIdEmpresa));
    		creditoGarantia.getId().setIntParaTipoCreditoCod(new Integer(pIntIdTipoCredito));
    		creditoGarantia.getId().setIntItemCredito(new Integer(pIntIdItemCredito));
    		creditoGarantia.getId().setIntParaTipoGarantiaCod(new Integer(pIntIdTipoGarantia));
    		creditoGarantia.getId().setIntItemCreditoGarantia(new Integer(pIntIdItemCreditoGarantia));
    		
			facade = (CreditoGarantiaFacadeLocal)EJBFactory.getLocal(CreditoGarantiaFacadeLocal.class);
			facade.eliminarCreditoGarantia(creditoGarantia);
			listarGarantiaPersonal(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
}