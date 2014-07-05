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

public class CreditoGarantiaAutoliquidableController {
	protected  static Logger 		log 			= Logger.getLogger(CreditoGarantiaAutoliquidableController.class);
	private int 					rows = 5;
	private CreditoGarantia			beanGarantiaAutoliquidable = new CreditoGarantia();
	private List<Tabla> 			listaNaturalezaGarantia;
	private List<CreditoGarantia> 	listaGarantiaAutoliquidable;
	private String					strCreditoGarantiaAutoliquidable;
	private String					rbTipoCreditoAutoliquidable;
	private Boolean					boCtaDepositoRendered = false;
	private Boolean					boDerechoCartaRendered = false;
	//Variables del formulario para grabar dentro del Tipo de Garantía
	private CreditoController	creditoController;
	
	//Getters y Setters
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public CreditoGarantia getBeanGarantiaAutoliquidable() {
		return beanGarantiaAutoliquidable;
	}
	public void setBeanGarantiaAutoliquidable(
			CreditoGarantia beanGarantiaAutoliquidable) {
		this.beanGarantiaAutoliquidable = beanGarantiaAutoliquidable;
	}
	public List<Tabla> getListaNaturalezaGarantia() {
		return listaNaturalezaGarantia;
	}
	public void setListaNaturalezaGarantia(List<Tabla> listaNaturalezaGarantia) {
		this.listaNaturalezaGarantia = listaNaturalezaGarantia;
	}
	public List<CreditoGarantia> getListaGarantiaAutoliquidable() {
		return listaGarantiaAutoliquidable;
	}
	public void setListaGarantiaAutoliquidable(
			List<CreditoGarantia> listaGarantiaAutoliquidable) {
		this.listaGarantiaAutoliquidable = listaGarantiaAutoliquidable;
	}
	public String getStrCreditoGarantiaAutoliquidable() {
		return strCreditoGarantiaAutoliquidable;
	}
	public void setStrCreditoGarantiaAutoliquidable(
			String strCreditoGarantiaAutoliquidable) {
		this.strCreditoGarantiaAutoliquidable = strCreditoGarantiaAutoliquidable;
	}
	public String getRbTipoCreditoAutoliquidable() {
		return rbTipoCreditoAutoliquidable;
	}
	public void setRbTipoCreditoAutoliquidable(String rbTipoCreditoAutoliquidable) {
		this.rbTipoCreditoAutoliquidable = rbTipoCreditoAutoliquidable;
	}
	public Boolean getBoCtaDepositoRendered() {
		return boCtaDepositoRendered;
	}
	public void setBoCtaDepositoRendered(Boolean boCtaDepositoRendered) {
		this.boCtaDepositoRendered = boCtaDepositoRendered;
	}
	public Boolean getBoDerechoCartaRendered() {
		return boDerechoCartaRendered;
	}
	public void setBoDerechoCartaRendered(Boolean boDerechoCartaRendered) {
		this.boDerechoCartaRendered = boDerechoCartaRendered;
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
	public void habilitarGrabarGarantiaAutoliquidable(ActionEvent event){
		log.info("---------entro al método CreditoGarantiaAutoliquidableController.habilitarGrabarGarantiaAutoliquidable---------");
		limpiarFormGarantiaAutoliquidable();
		strCreditoGarantiaAutoliquidable = Constante.MANTENIMIENTO_GRABAR;
	}
	
	public void enableDisableGarantiaAutoliq(ActionEvent event){
		if(getRbTipoCreditoAutoliquidable()!=null){
			setBoCtaDepositoRendered(getRbTipoCreditoAutoliquidable().equals("1"));
			beanGarantiaAutoliquidable.setBoOpcionMinimoCta(getRbTipoCreditoAutoliquidable().equals("2"));
			beanGarantiaAutoliquidable.setBdPorcCuenta(getRbTipoCreditoAutoliquidable().equals("2")?null:beanGarantiaAutoliquidable.getBdPorcCuenta());
			setBoDerechoCartaRendered(getRbTipoCreditoAutoliquidable().equals("2"));
			beanGarantiaAutoliquidable.setIntParaDerechoCarta(getRbTipoCreditoAutoliquidable().equals("1")?0:beanGarantiaAutoliquidable.getIntParaDerechoCarta());
		}
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarCredito()     					      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarFormGarantiaAutoliquidable(){
		CreditoGarantia garantiaAutoliquidable = new CreditoGarantia();
		garantiaAutoliquidable.setId(new CreditoGarantiaId());
		setBeanGarantiaAutoliquidable(garantiaAutoliquidable);
		setRbTipoCreditoAutoliquidable("");
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
		limpiarFormGarantiaAutoliquidable();
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarGarantiaAutoliquidable()        			*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Listar las Garantías de Tipo Personal				*/
	/*  Retorno : Devuelve el listado de Garantías del crédito 		*/
	/**************************************************************/
	public void listarGarantiaAutoliquidable(ActionEvent event) {
		CreditoGarantiaFacadeLocal facade = null;
		creditoController = (CreditoController)getSessionBean("creditoController");
		try {
			facade = (CreditoGarantiaFacadeLocal)EJBFactory.getLocal(CreditoGarantiaFacadeLocal.class);
			CreditoGarantia o = new CreditoGarantia();
			o.setId(new CreditoGarantiaId());
			o.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());
			o.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
			o.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
			o.getId().setIntParaTipoGarantiaCod(Constante.CREDITOS_AUTOLIQUIDABLE);
			listaGarantiaAutoliquidable = facade.getListaCreditoGarantia(o.getId());
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
    			
    			/*beanGarantiaAutoliquidable = creditoGarantiaFacade.getCreditoGarantiaPorIdCreditoGarantia(creditoGarantia.getId());
    		    if(beanGarantiaAutoliquidable.getIntDocAdjunto()!=null){
    		    	beanGarantiaAutoliquidable.setBoDocAdjunto(beanGarantiaAutoliquidable.getIntDocAdjunto()==1?true:false);
    		    }*/
    		    beanGarantiaAutoliquidable.setBoOpcionMinimoCta(beanGarantiaAutoliquidable.getIntOpcionMinimoCta()==1);
    		    setRbTipoCreditoAutoliquidable(beanGarantiaAutoliquidable.getIntParaDerechoCarta()!=null && beanGarantiaAutoliquidable.getIntParaDerechoCarta()!=0?"2":"1");
    		    setBoCtaDepositoRendered(beanGarantiaAutoliquidable.getIntParaDerechoCarta()==0);
    		    setBoDerechoCartaRendered(beanGarantiaAutoliquidable.getIntParaDerechoCarta()!=0);
    		    strCreditoGarantiaAutoliquidable = Constante.MANTENIMIENTO_MODIFICAR;
    		}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  grabarGarantiaAutoliquidable()   	     			*/
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
	public void grabarGarantiaAutoliquidable(ActionEvent event){
		CreditoGarantiaFacadeLocal facade = null;
		creditoController = (CreditoController)getSessionBean("creditoController");
	    /*if(isValidoAportacion(beanGarantiaPersonal) == false){
	    	log.info("Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.");
	    	return;
	    }*/
		beanGarantiaAutoliquidable.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());
		beanGarantiaAutoliquidable.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
		beanGarantiaAutoliquidable.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
		beanGarantiaAutoliquidable.getId().setIntParaTipoGarantiaCod(Constante.CREDITOS_AUTOLIQUIDABLE);
		//beanGarantiaAutoliquidable.setIntDocAdjunto(beanGarantiaAutoliquidable.getBoDocAdjunto()==true?1:0);
		beanGarantiaAutoliquidable.setIntOpcionMinimoCta(beanGarantiaAutoliquidable.getBoOpcionMinimoCta()==true?1:0);
		beanGarantiaAutoliquidable.setIntOpcionValorVenta(0);
		beanGarantiaAutoliquidable.setIntValuadorInscrito(0);
		beanGarantiaAutoliquidable.setIntTasacionComercial(0);
		beanGarantiaAutoliquidable.setIntNegociacionEndoso(0);
		beanGarantiaAutoliquidable.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		try {
			facade = (CreditoGarantiaFacadeLocal)EJBFactory.getLocal(CreditoGarantiaFacadeLocal.class);
			facade.grabarCreditoGarantia(beanGarantiaAutoliquidable);
			listarGarantiaAutoliquidable(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  modificarGarantiaAutoliquidable()   				*/
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
	public void modificarGarantiaAutoliquidable(ActionEvent event){
		log.info("------------entrando al método CreditoGarantiaAutoliquidableController.modificarGarantiaAutoliquidable------------");
		CreditoGarantiaFacadeLocal facade = null;
		creditoController = (CreditoController)getSessionBean("creditoController");
		
		beanGarantiaAutoliquidable.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());
		beanGarantiaAutoliquidable.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
		beanGarantiaAutoliquidable.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
		beanGarantiaAutoliquidable.getId().setIntParaTipoGarantiaCod(Constante.CREDITOS_AUTOLIQUIDABLE);
		//beanGarantiaAutoliquidable.setIntDocAdjunto(beanGarantiaAutoliquidable.getBoDocAdjunto()==true?1:0);
		beanGarantiaAutoliquidable.setIntOpcionMinimoCta(beanGarantiaAutoliquidable.getBoOpcionMinimoCta()==true?1:0);
		beanGarantiaAutoliquidable.setIntOpcionValorVenta(0);
		beanGarantiaAutoliquidable.setIntValuadorInscrito(0);
		beanGarantiaAutoliquidable.setIntTasacionComercial(0);
		beanGarantiaAutoliquidable.setIntNegociacionEndoso(0);
		
		try {
			facade = (CreditoGarantiaFacadeLocal)EJBFactory.getLocal(CreditoGarantiaFacadeLocal.class);
			facade.modificarCreditoGarantia(beanGarantiaAutoliquidable);
			listarGarantiaAutoliquidable(event);
			limpiarFormGarantiaAutoliquidable();
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
	public void eliminarGarantiaAutoliquidable(ActionEvent event){
    	log.info("-----------------------Debugging CreditoGarantiaAutoliquidableController.eliminarGarantiaAutoliquidable------------------------");
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
			listarGarantiaAutoliquidable(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
}