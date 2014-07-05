package pe.com.tumi.credito.socio.credito.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaTipoValorVenta;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaTipoValorVentaId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoGarantiaFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;

/************************************************************************/
/* Nombre de la clase: CreditoGarantiaRealController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de Créditos de Garantías Reales */
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 30/04/2012 */
/* ********************************************************************* */

public class CreditoGarantiaRealController {
	protected  static Logger 		log 			= Logger.getLogger(CreditoGarantiaRealController.class);
	private int 					rows = 5;
	private CreditoGarantia			beanGarantiaReal = new CreditoGarantia();
	private List<Tabla> 			listaNaturalezaGarantia;
	private List<CreditoGarantia> 	listaGarantiaReal;
	private List<Tabla> 			listaClaseGarantia;
	private List<Tabla> 			listaSubClaseGarantia;
	private String					strCreditoGarantiaReal;
	private String[]				lstValorRealizacion;
	
	//Variables del formulario para grabar dentro del Tipo de Garantía
	private CreditoController	creditoController;
	
	//Getters y Setters
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public CreditoGarantia getBeanGarantiaReal() {
		return beanGarantiaReal;
	}
	public void setBeanGarantiaReal(CreditoGarantia beanGarantiaReal) {
		this.beanGarantiaReal = beanGarantiaReal;
	}
	public List<Tabla> getListaNaturalezaGarantia() {
		return listaNaturalezaGarantia;
	}
	public void setListaNaturalezaGarantia(List<Tabla> listaNaturalezaGarantia) {
		this.listaNaturalezaGarantia = listaNaturalezaGarantia;
	}
	public List<CreditoGarantia> getListaGarantiaReal() {
		return listaGarantiaReal;
	}
	public void setListaGarantiaReal(List<CreditoGarantia> listaGarantiaReal) {
		this.listaGarantiaReal = listaGarantiaReal;
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
	public String getStrCreditoGarantiaReal() {
		return strCreditoGarantiaReal;
	}
	public void setStrCreditoGarantiaReal(String strCreditoGarantiaReal) {
		this.strCreditoGarantiaReal = strCreditoGarantiaReal;
	}
	public String[] getLstValorRealizacion() {
		return lstValorRealizacion;
	}
	public void setLstValorRealizacion(String[] lstValorRealizacion) {
		this.lstValorRealizacion = lstValorRealizacion;
	}
	public CreditoController getCreditoController() {
		return creditoController;
	}
	public void setCreditoController(CreditoController creditoController) {
		this.creditoController = creditoController;
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
	public void habilitarGrabarGarantiaReal(ActionEvent event){
		log.info("---------entro al método habilitarGrabarGarantiaReal---------");
		limpiarFormGarantiaReal();
		strCreditoGarantiaReal = Constante.MANTENIMIENTO_GRABAR;
		reloadCboClaseGarantia(new Integer(Constante.CREDITOS_GARANTIA_REAL));
		log.info("strCreditoGarantiaReal: "+ strCreditoGarantiaReal);
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarCredito()     					      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarFormGarantiaReal(){
		CreditoGarantia garantiaReal = new CreditoGarantia();
		garantiaReal.setId(new CreditoGarantiaId());
		setBeanGarantiaReal(garantiaReal);
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
		limpiarFormGarantiaReal();
	}
	
	public void reloadCboClaseGarantia(Integer intIdTipoGarantia) {
		log.info("-----------------------Debugging CreditoGarantiaRealController.reloadCboClaseGarantia-----------------------------");
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
		log.info("intIdTipoPersona = "+intIdTipoClase);
		TablaFacadeRemote remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
	    listaSubClaseGarantia = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_SUBCLASE_GARANTIA), intIdTipoClase);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarGarantiaReal()        						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Listar las Garantías de Tipo Personal				*/
	/*  Retorno : Devuelve el listado de Garantías del crédito 		*/
	/**************************************************************/
	public void listarGarantiaReal(ActionEvent event) {
		CreditoGarantiaFacadeLocal facade = null;
		creditoController = (CreditoController)getSessionBean("creditoController");
		try {
			facade = (CreditoGarantiaFacadeLocal)EJBFactory.getLocal(CreditoGarantiaFacadeLocal.class);
			CreditoGarantia o = new CreditoGarantia();
			o.setId(new CreditoGarantiaId());
			o.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());
			o.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
			o.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
			o.getId().setIntParaTipoGarantiaCod(Constante.CREDITOS_GARANTIA_REAL);
			listaGarantiaReal = facade.getListaCreditoGarantia(o.getId());
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
    			
    			beanGarantiaReal = creditoGarantiaFacade.getCreditoGarantiaPorIdCreditoGarantia(creditoGarantia.getId());
    			reloadCboClaseGarantia(new Integer(Constante.CREDITOS_GARANTIA_REAL));
    			remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
    		    listaSubClaseGarantia = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_SUBCLASE_GARANTIA), beanGarantiaReal.getIntParaClaseCod());
    		    
    		    beanGarantiaReal.setBoOpcionValorVenta(beanGarantiaReal.getIntOpcionValorVenta()==1);
    		    beanGarantiaReal.setBoValuadorInscrito(beanGarantiaReal.getIntValuadorInscrito()==1);
    		    beanGarantiaReal.setBoTasacionComercial(beanGarantiaReal.getIntTasacionComercial()==1);
    		    beanGarantiaReal.setBoNegociacionEndoso(beanGarantiaReal.getIntNegociacionEndoso()==1);
    		    
    		    if(beanGarantiaReal.getListaTipoValorVenta()!=null && beanGarantiaReal.getListaTipoValorVenta().size()>0){
    		    	String[] listaTipoValorVenta = new String[beanGarantiaReal.getListaTipoValorVenta().size()];
    				for(int i=0;i<beanGarantiaReal.getListaTipoValorVenta().size();i++){
    					listaTipoValorVenta[i] = ""+ beanGarantiaReal.getListaTipoValorVenta().get(i).getId().getIntParaTipoValorVentaCod();
    				}
    				lstValorRealizacion = listaTipoValorVenta;
    		    }
    		    
    		    strCreditoGarantiaReal = Constante.MANTENIMIENTO_MODIFICAR;
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
	/*  Nombre :  grabarGarantiaReal()   	     					*/
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
	public void grabarGarantiaReal(ActionEvent event){
		CreditoGarantiaFacadeLocal facade = null;
		CreditoGarantiaTipoValorVenta creditoGarantiaTipoValorVenta = null;
		List<CreditoGarantiaTipoValorVenta> listaTipoValorVenta = new ArrayList<CreditoGarantiaTipoValorVenta>();
		creditoController = (CreditoController)getSessionBean("creditoController");
	    /*if(isValidoAportacion(beanGarantiaPersonal) == false){
	    	log.info("Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.");
	    	return;
	    }*/
		beanGarantiaReal.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());
		beanGarantiaReal.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
		beanGarantiaReal.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
		beanGarantiaReal.getId().setIntParaTipoGarantiaCod(Constante.CREDITOS_GARANTIA_REAL);
		beanGarantiaReal.setIntOpcionMinimoCta(0);
		beanGarantiaReal.setIntOpcionValorVenta(beanGarantiaReal.getBoOpcionValorVenta()==true?1:0);
		beanGarantiaReal.setIntValuadorInscrito(beanGarantiaReal.getBoValuadorInscrito()==true?1:0);
		beanGarantiaReal.setIntTasacionComercial(beanGarantiaReal.getBoTasacionComercial()==true?1:0);
		beanGarantiaReal.setIntNegociacionEndoso(beanGarantiaReal.getBoNegociacionEndoso()==true?1:0);
		beanGarantiaReal.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		if(lstValorRealizacion!=null && lstValorRealizacion.length>0){
			for(int i=0;i<lstValorRealizacion.length;i++){
				creditoGarantiaTipoValorVenta = new CreditoGarantiaTipoValorVenta();
				creditoGarantiaTipoValorVenta.setId(new CreditoGarantiaTipoValorVentaId());
				creditoGarantiaTipoValorVenta.getId().setIntParaTipoValorVentaCod(new Integer(lstValorRealizacion[i]));
				listaTipoValorVenta.add(creditoGarantiaTipoValorVenta);
			}
			beanGarantiaReal.setListaTipoValorVenta(listaTipoValorVenta);
		}
		
		try {
			facade = (CreditoGarantiaFacadeLocal)EJBFactory.getLocal(CreditoGarantiaFacadeLocal.class);
			facade.grabarCreditoGarantia(beanGarantiaReal);
			listarGarantiaReal(event);
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
	public void modificarGarantiaReal(ActionEvent event){
		log.info("------------entrando al método CreditoDescuentoController.modificarDescuento------------");
		CreditoGarantiaFacadeLocal facade = null;
		CreditoGarantiaTipoValorVenta creditoGarantiaTipoValorVenta = null;
		List<CreditoGarantiaTipoValorVenta> listaTipoValorVenta = new ArrayList<CreditoGarantiaTipoValorVenta>();
		creditoController = (CreditoController)getSessionBean("creditoController");
		
		beanGarantiaReal.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());
		beanGarantiaReal.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
		beanGarantiaReal.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
		beanGarantiaReal.getId().setIntParaTipoGarantiaCod(Constante.CREDITOS_GARANTIA_REAL);
		beanGarantiaReal.setIntOpcionMinimoCta(0);
		beanGarantiaReal.setIntOpcionValorVenta(beanGarantiaReal.getBoOpcionValorVenta()==true?1:0);
		beanGarantiaReal.setIntValuadorInscrito(beanGarantiaReal.getBoValuadorInscrito()==true?1:0);
		beanGarantiaReal.setIntTasacionComercial(beanGarantiaReal.getBoTasacionComercial()==true?1:0);
		beanGarantiaReal.setIntNegociacionEndoso(beanGarantiaReal.getBoNegociacionEndoso()==true?1:0);
		
		if(lstValorRealizacion!=null && lstValorRealizacion.length>0){
			for(int i=0;i<lstValorRealizacion.length;i++){
				creditoGarantiaTipoValorVenta = new CreditoGarantiaTipoValorVenta();
				creditoGarantiaTipoValorVenta.setId(new CreditoGarantiaTipoValorVentaId());
				creditoGarantiaTipoValorVenta.getId().setIntParaTipoValorVentaCod(new Integer(lstValorRealizacion[i]));
				listaTipoValorVenta.add(creditoGarantiaTipoValorVenta);
			}
			beanGarantiaReal.setListaTipoValorVenta(listaTipoValorVenta);
		}
		
		try {
			facade = (CreditoGarantiaFacadeLocal)EJBFactory.getLocal(CreditoGarantiaFacadeLocal.class);
			facade.modificarCreditoGarantia(beanGarantiaReal);
			listarGarantiaReal(event);
			limpiarFormGarantiaReal();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  eliminarGarantiaReal()     		   				*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Eliminar un registro grabado en la bd		 		*/
	/**/
	/***********************************************************************************/
	public void eliminarGarantiaReal(ActionEvent event){
    	log.info("-----------------------Debugging CreditoGarantiaRealController.eliminarGarantiaReal------------------------");
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
			listarGarantiaReal(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
}