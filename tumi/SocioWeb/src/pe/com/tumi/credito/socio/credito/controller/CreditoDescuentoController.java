package pe.com.tumi.credito.socio.credito.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoDescuentoFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;

/************************************************************************/
/* Nombre de la clase: CreditoDescuentoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de Descuentos de crédito */
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 18/04/2012 */
/* ********************************************************************* */

public class CreditoDescuentoController {
	protected  static Logger 	log 			= Logger.getLogger(CreditoDescuentoController.class);
	private int 				rows = 5;
	private CreditoDescuento	beanDescuento = null;
	private String[] 			lstDsctoCaptacion;
	private String				rbFecFinIndeterm;
	private Boolean				fecFinCreditoRendered = true;
	private List<CreditoDescuento> listaDescuentoCredito;
	private CreditoController	creditoController;
	
	private List<Tabla>			listaTipoCaptacion;
	private String strCreditoDescuento;
	
	public CreditoDescuentoController(){
		beanDescuento = new CreditoDescuento();
		beanDescuento.setId(new CreditoDescuentoId());
	}
	
	//Getters y Setters
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public CreditoDescuento getBeanDescuento() {
		return beanDescuento;
	}
	public void setBeanDescuento(CreditoDescuento beanDescuento) {
		this.beanDescuento = beanDescuento;
	}
	public String[] getLstDsctoCaptacion() {
		return lstDsctoCaptacion;
	}
	public void setLstDsctoCaptacion(String[] lstDsctoCaptacion) {
		this.lstDsctoCaptacion = lstDsctoCaptacion;
	}
	public String getRbFecFinIndeterm() {
		return rbFecFinIndeterm;
	}
	public void setRbFecFinIndeterm(String rbFecFinIndeterm) {
		this.rbFecFinIndeterm = rbFecFinIndeterm;
	}
	public Boolean getFecFinCreditoRendered() {
		return fecFinCreditoRendered;
	}
	public void setFecFinCreditoRendered(Boolean fecFinCreditoRendered) {
		this.fecFinCreditoRendered = fecFinCreditoRendered;
	}
	public List<CreditoDescuento> getListaDescuentoCredito() {
		return listaDescuentoCredito;
	}
	public void setListaDescuentoCredito(
			List<CreditoDescuento> listaDescuentoCredito) {
		this.listaDescuentoCredito = listaDescuentoCredito;
	}
	
	public CreditoController getCreditoController() {
		return creditoController;
	}

	public void setCreditoController(CreditoController creditoController) {
		this.creditoController = creditoController;
	}

	public List<Tabla> getListaTipoCaptacion() {
		return listaTipoCaptacion;
	}

	public void setListaTipoCaptacion(List<Tabla> listaTipoCaptacion) {
		this.listaTipoCaptacion = listaTipoCaptacion;
	}

	public String getStrCreditoDescuento() {
		return strCreditoDescuento;
	}

	public void setStrCreditoDescuento(String strCreditoDescuento) {
		this.strCreditoDescuento = strCreditoDescuento;
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
	/*  Nombre :  habilitarGrabarCreditoDscto()     			*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Habilitar el Formulario de Descuentos			*/
	/*  Retorno : El formulario de Descuentos vacío 			*/
	/**************************************************************/
	public void habilitarGrabarCreditoDscto(ActionEvent event) {
		log.info("---------entro al método habilitarGrabarCreditoDscto---------");
		limpiarFormCreditoDescuento();
		reloadCboTipoCaptacion(Constante.CONFIGURACION_CREDITO_DESCUENTO);
		strCreditoDescuento = Constante.MANTENIMIENTO_GRABAR;
	}
	
	public void reloadCboTipoCaptacion(String strIdTipoCaptacion) {
		log.info("-----------------------Debugging CreditoDescuentoController.reloadCboTipoCaptacion-----------------------------");
		TablaFacadeRemote remote;
		try {
			remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			try {
				listaTipoCaptacion = remote.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOCUENTA), strIdTipoCaptacion);
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
	/**************************************************************/
	/*  Nombre :  limpiarCredito()     					      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarFormCreditoDescuento(){
		log.info("---------entro al método limpiarFormCreditoDescuento---------");
		CreditoDescuento creditoDescuento = new CreditoDescuento();
		creditoDescuento.setId(new CreditoDescuentoId());
		setBeanDescuento(creditoDescuento);
		
		if(lstDsctoCaptacion!=null){
			setLstDsctoCaptacion(null);
		}
		setRbFecFinIndeterm("");
		setFecFinCreditoRendered(true);
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
		limpiarFormCreditoDescuento();
	}
	
	public void enableDisableControls(ActionEvent event) {
		if(getRbFecFinIndeterm()!=null){
			setFecFinCreditoRendered(getRbFecFinIndeterm().equals("1"));
			//setDaFechaFin(null);
		}
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarDescuento()        							*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Listar los descuentos que se sujetan al crédito	*/
	/*  Retorno : Devuelve el listado de Descuentos del crédito 	*/
	/**************************************************************/
	public void listarDescuento(ActionEvent event) {
		log.info("-----------------------Debugging CreditoController.listarDescuento-----------------------------");
		CreditoDescuentoFacadeLocal facade = null;
		creditoController = (CreditoController)getSessionBean("creditoController");
		
		try {
			facade = (CreditoDescuentoFacadeLocal)EJBFactory.getLocal(CreditoDescuentoFacadeLocal.class);
			Credito o = new Credito();
			o.setId(new CreditoId());
			o.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());
			o.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
			o.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
			listaDescuentoCredito = facade.getListaCreditoDescuento(o.getId());
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  grabarCredito()        							*/
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
	public void grabarDescuento(ActionEvent event){
		CreditoDescuentoFacadeLocal facade = null;
		CreditoDescuentoCaptacion creditoDescuentoCaptacion = null;
		List<CreditoDescuentoCaptacion> listaDescuento = new ArrayList<CreditoDescuentoCaptacion>();
		creditoController = (CreditoController)getSessionBean("creditoController");
		/*if(isValidoAportacion(beanDescuento) == false){
	    	log.info("Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.");
	    	return;
	    }*/
		
		beanDescuento.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());//beanSesion.getIntIdEmpresa()
	    beanDescuento.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
	    beanDescuento.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
		
		if(lstDsctoCaptacion!=null && lstDsctoCaptacion.length>0){
			for(int i=0;i<lstDsctoCaptacion.length;i++){
				creditoDescuentoCaptacion = new CreditoDescuentoCaptacion();
				creditoDescuentoCaptacion.setId(new CreditoDescuentoCaptacionId());
				creditoDescuentoCaptacion.getId().setIntParaTipoCaptacionCod(new Integer(lstDsctoCaptacion[i]));
				listaDescuento.add(creditoDescuentoCaptacion);
			}
			beanDescuento.setListaDescuento(listaDescuento);
		}
		
		try {
			facade = (CreditoDescuentoFacadeLocal)EJBFactory.getLocal(CreditoDescuentoFacadeLocal.class);
			facade.grabarCreditoDescuento(beanDescuento);
			listarDescuento(event);
			limpiarFormCreditoDescuento();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  irModificarDescuento()        					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Modificar los datos grabados en el popup de 		*/
	/*            descuento    						     	 		*/
	/**/
	/***********************************************************************************/
	public void irModificarDescuento(ActionEvent event) throws ParseException{
    	log.info("-----------------------Debugging CreditoController.irModificarCredito-----------------------------");
    	CreditoDescuentoFacadeLocal creditoDescuentoFacade = null;
    	String pIntIdEmpresa = getRequestParameter("pIntIdEmpresa");
    	String pIntIdTipoCredito = getRequestParameter("pIntIdTipoCredito");
    	String pIntIdItemCredito = getRequestParameter("pIntIdItemCredito");
    	String pIntIdItemCreditoDescuento = getRequestParameter("pIntIdItemCreditoDescuento");
		CreditoDescuento creditoDescuento = null;
    	
    	try {
    		if(pIntIdItemCreditoDescuento != null && !pIntIdItemCreditoDescuento.trim().equals("")){
    			creditoDescuento = new CreditoDescuento();
    			creditoDescuento.setId(new CreditoDescuentoId());
    			creditoDescuento.getId().setIntPersEmpresaPk(new Integer(pIntIdEmpresa));
    			creditoDescuento.getId().setIntParaTipoCreditoCod(new Integer(pIntIdTipoCredito));
    			creditoDescuento.getId().setIntItemCredito(new Integer(pIntIdItemCredito));
    			creditoDescuento.getId().setIntItemCreditoDescuento(new Integer(pIntIdItemCreditoDescuento));
    			creditoDescuentoFacade = (CreditoDescuentoFacadeLocal)EJBFactory.getLocal(CreditoDescuentoFacadeLocal.class);
				beanDescuento = creditoDescuentoFacade.getCreditoDescuentoPorIdCreditoDescuento(creditoDescuento.getId());
				
				String[] listaDescuentoCaptacion = new String[beanDescuento.getListaDescuento().size()];
				for(int i=0;i<beanDescuento.getListaDescuento().size();i++){
					listaDescuentoCaptacion[i] = ""+ beanDescuento.getListaDescuento().get(i).getId().getIntParaTipoCaptacionCod();
				}
				lstDsctoCaptacion = listaDescuentoCaptacion;
				
				setRbFecFinIndeterm(beanDescuento.getDtFechaFin()!=null?"1":"2");
				setFecFinCreditoRendered(beanDescuento.getDtFechaFin()!=null);
				
				reloadCboTipoCaptacion(Constante.CONFIGURACION_CREDITO_DESCUENTO);
				strCreditoDescuento = Constante.MANTENIMIENTO_MODIFICAR;
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
	/*  Nombre :  grabarCredito()        							*/
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
	public void modificarDescuento(ActionEvent event){
		log.info("------------entrando al método CreditoDescuentoController.modificarDescuento------------");
		CreditoDescuentoFacadeLocal facade = null;
		CreditoDescuentoCaptacion creditoDescuentoCaptacion = null;
		List<CreditoDescuentoCaptacion> listaDescuento = new ArrayList<CreditoDescuentoCaptacion>();
		creditoController = (CreditoController)getSessionBean("creditoController");
		
		beanDescuento.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());//beanSesion.getIntIdEmpresa()
	    beanDescuento.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
	    beanDescuento.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
		
		if(lstDsctoCaptacion!=null && lstDsctoCaptacion.length>0){
			for(int i=0;i<lstDsctoCaptacion.length;i++){
				creditoDescuentoCaptacion = new CreditoDescuentoCaptacion();
				creditoDescuentoCaptacion.setId(new CreditoDescuentoCaptacionId());
				creditoDescuentoCaptacion.getId().setIntParaTipoCaptacionCod(new Integer(lstDsctoCaptacion[i]));
				listaDescuento.add(creditoDescuentoCaptacion);
			}
			beanDescuento.setListaDescuento(listaDescuento);
		}
		
		try {
			facade = (CreditoDescuentoFacadeLocal)EJBFactory.getLocal(CreditoDescuentoFacadeLocal.class);
			facade.modificarCreditoDescuento(beanDescuento);
			listarDescuento(event);
			limpiarFormCreditoDescuento();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  eliminarDescuento()        						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Eliminar un registro grabado en la bd		 		*/
	/**/
	/***********************************************************************************/
	public void eliminarDescuento(ActionEvent event){
    	log.info("-----------------------Debugging CreditoDescuentoController.eliminarDescuento------------------------");
    	String pIntIdEmpresa = getRequestParameter("pIntIdEmpresa");
    	String pIntIdTipoCredito = getRequestParameter("pIntIdTipoCredito");
    	String pIntIdItemCredito = getRequestParameter("pIntIdItemCredito");
    	String pIntIdItemCreditoDescuento = getRequestParameter("pIntIdItemCreditoDescuento");
		CreditoDescuentoFacadeLocal facade = null;
		CreditoDescuento creditoDescuento = null;
    	try {
    		creditoDescuento = new CreditoDescuento();
    		creditoDescuento.setId(new CreditoDescuentoId());
    		creditoDescuento.getId().setIntPersEmpresaPk(new Integer(pIntIdEmpresa));
    		creditoDescuento.getId().setIntParaTipoCreditoCod(new Integer(pIntIdTipoCredito));
    		creditoDescuento.getId().setIntItemCredito(new Integer(pIntIdItemCredito));
    		creditoDescuento.getId().setIntItemCreditoDescuento(new Integer(pIntIdItemCreditoDescuento));
			facade = (CreditoDescuentoFacadeLocal)EJBFactory.getLocal(CreditoDescuentoFacadeLocal.class);
			facade.eliminarCreditoDescuento(creditoDescuento);
			listarDescuento(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
}