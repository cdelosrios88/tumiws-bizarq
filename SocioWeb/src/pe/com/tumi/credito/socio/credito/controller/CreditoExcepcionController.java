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
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionAporteNoTrans;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionAporteNoTransId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionDiasCobranza;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionDiasCobranzaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoDescuentoFacadeLocal;
import pe.com.tumi.credito.socio.creditos.facade.CreditoExcepcionFacadeLocal;
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

public class CreditoExcepcionController {
	protected  static Logger 	log 			= Logger.getLogger(CreditoExcepcionController.class);
	private int 				rows = 5;
	private CreditoExcepcion	beanExcepcion = null;
	private String[] 			lstDobleCuota;
	private String[] 			lstExcepcionAporteNoTrans;
	private String[] 			lstDiasCobranza;
	private Boolean				fecFinCreditoRendered = true;
	private List<CreditoExcepcion> listaExcepcionCredito;
	private CreditoController	creditoController;
	
	private List<Tabla> 		listaTipoCaptacion;
	private String strCreditoExcepcion;
	
	public CreditoExcepcionController(){
		beanExcepcion = new CreditoExcepcion();
		beanExcepcion.setId(new CreditoExcepcionId());
	}
	
	//Getters y Setters
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public CreditoExcepcion getBeanExcepcion() {
		return beanExcepcion;
	}

	public void setBeanExcepcion(CreditoExcepcion beanExcepcion) {
		this.beanExcepcion = beanExcepcion;
	}

	public String[] getLstDobleCuota() {
		return lstDobleCuota;
	}

	public void setLstDobleCuota(String[] lstDobleCuota) {
		this.lstDobleCuota = lstDobleCuota;
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

	public String[] getLstExcepcionAporteNoTrans() {
		return lstExcepcionAporteNoTrans;
	}

	public void setLstExcepcionAporteNoTrans(String[] lstExcepcionAporteNoTrans) {
		this.lstExcepcionAporteNoTrans = lstExcepcionAporteNoTrans;
	}

	public String[] getLstDiasCobranza() {
		return lstDiasCobranza;
	}

	public void setLstDiasCobranza(String[] lstDiasCobranza) {
		this.lstDiasCobranza = lstDiasCobranza;
	}

	public Boolean getFecFinCreditoRendered() {
		return fecFinCreditoRendered;
	}
	public void setFecFinCreditoRendered(Boolean fecFinCreditoRendered) {
		this.fecFinCreditoRendered = fecFinCreditoRendered;
	}

	public List<CreditoExcepcion> getListaExcepcionCredito() {
		return listaExcepcionCredito;
	}

	public void setListaExcepcionCredito(
			List<CreditoExcepcion> listaExcepcionCredito) {
		this.listaExcepcionCredito = listaExcepcionCredito;
	}

	public String getStrCreditoExcepcion() {
		return strCreditoExcepcion;
	}

	public void setStrCreditoExcepcion(String strCreditoExcepcion) {
		this.strCreditoExcepcion = strCreditoExcepcion;
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
	public void habilitarGrabarCreditoExcepcion(ActionEvent event) {
		log.info("---------entro al método habilitarGrabarCreditoDscto---------");
		limpiarFormCreditoExcepcion();
		reloadCboTipoCaptacion(Constante.CONFIGURACION_CREDITO_EXCEPCION);
		strCreditoExcepcion = Constante.MANTENIMIENTO_GRABAR;
	}
	
	public void reloadCboTipoCaptacion(String strIdTipoCaptacion) {
		log.info("-----------------------Debugging CreditoController.reloadCboTipoCaptacion-----------------------------");
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
	public void limpiarFormCreditoExcepcion(){
		log.info("---------entro al método limpiarFormCreditoDescuento---------");
		CreditoExcepcion creditoExcepcion = new CreditoExcepcion();
		creditoExcepcion.setId(new CreditoExcepcionId());
		setBeanExcepcion(creditoExcepcion);
		
		if(lstDobleCuota!=null){
			setLstDobleCuota(null);
		}
		if(lstExcepcionAporteNoTrans!=null){
			setLstExcepcionAporteNoTrans(null);
		}
		if(lstDiasCobranza!=null){
			setLstDiasCobranza(null);
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
		limpiarFormCreditoExcepcion();
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarExcepcion()        							*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Listar los descuentos que se sujetan al crédito	*/
	/*  Retorno : Devuelve el listado de Descuentos del crédito 	*/
	/**************************************************************/
	public void listarExcepcion(ActionEvent event) {
		log.info("-----------------------Debugging CreditoController.listarDescuento-----------------------------");
		CreditoExcepcionFacadeLocal facade = null;
		creditoController = (CreditoController)getSessionBean("creditoController");
		
		try {
			facade = (CreditoExcepcionFacadeLocal)EJBFactory.getLocal(CreditoExcepcionFacadeLocal.class);
			Credito o = new Credito();
			o.setId(new CreditoId());
			o.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());
			o.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
			o.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
			listaExcepcionCredito = facade.getListaCreditoExcepcion(o.getId());
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
	public void grabarExcepcion(ActionEvent event){
		CreditoExcepcionFacadeLocal facade = null;
		CreditoExcepcionAporteNoTrans creditoExcepcionAporteNoTrans = null;
		List<CreditoExcepcionAporteNoTrans> listaAporteNoTrans = new ArrayList<CreditoExcepcionAporteNoTrans>();
		CreditoExcepcionDiasCobranza creditoExcepcionDiasCobranza = null;
		List<CreditoExcepcionDiasCobranza> listaDiasCobranza = new ArrayList<CreditoExcepcionDiasCobranza>();
		creditoController = (CreditoController)getSessionBean("creditoController");
		/*if(isValidoAportacion(beanExcepcion) == false){
	    	log.info("Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.");
	    	return;
	    }*/
		
		beanExcepcion.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());//beanSesion.getIntIdEmpresa()
	    beanExcepcion.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
	    beanExcepcion.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
	    
	    beanExcepcion.setIntParaPeriodicidadGraciaCod(Constante.PARAM_T_MES);
	    
	    if(lstDobleCuota==null || lstDobleCuota.length==0){
	    	beanExcepcion.setIntParaCampoDobleCuotaCod(0);
	    } else if(lstDobleCuota.length>1){
	    	beanExcepcion.setIntParaCampoDobleCuotaCod(-1);
	    }else{
	    	if(lstDobleCuota!=null && lstDobleCuota.length>0){
		    	for(int i=0; i<lstDobleCuota.length;i++){
		    		if(lstDobleCuota[i].equals("1")){
		    			beanExcepcion.setIntParaCampoDobleCuotaCod(1);
		    		}else if(lstDobleCuota[i].equals("2")){
		    			beanExcepcion.setIntParaCampoDobleCuotaCod(2);
		    		}
		    	}
		    }
	    }
	    
	    if(lstExcepcionAporteNoTrans!=null && lstExcepcionAporteNoTrans.length>0){
			for(int i=0;i<lstExcepcionAporteNoTrans.length;i++){
				creditoExcepcionAporteNoTrans = new CreditoExcepcionAporteNoTrans();
				creditoExcepcionAporteNoTrans.setId(new CreditoExcepcionAporteNoTransId());
				creditoExcepcionAporteNoTrans.getId().setIntParaTipoCaptacionCod(new Integer(lstExcepcionAporteNoTrans[i]));
				listaAporteNoTrans.add(creditoExcepcionAporteNoTrans);
			}
			beanExcepcion.setListaAporteNoTrans(listaAporteNoTrans);
		}
	    
	    if(lstDiasCobranza!=null && lstDiasCobranza.length>0){
			for(int i=0;i<lstDiasCobranza.length;i++){
				creditoExcepcionDiasCobranza = new CreditoExcepcionDiasCobranza();
				creditoExcepcionDiasCobranza.setId(new CreditoExcepcionDiasCobranzaId());
				creditoExcepcionDiasCobranza.getId().setIntParaDiasCobranzaCod(new Integer(lstDiasCobranza[i]));
				listaDiasCobranza.add(creditoExcepcionDiasCobranza);
			}
			beanExcepcion.setListaDiasCobranza(listaDiasCobranza);
		}
	    
	    beanExcepcion.setIntAplicaCastigadas(beanExcepcion.getBoAplicaCastigadas()==true?1:0);
		
		try {
			facade = (CreditoExcepcionFacadeLocal)EJBFactory.getLocal(CreditoExcepcionFacadeLocal.class);
			facade.grabarCreditoExcepcion(beanExcepcion);
			listarExcepcion(event);
			limpiarFormCreditoExcepcion();
		} catch (EJBFactoryException e) {
			log.error(e);
		} catch (BusinessException e) {
			log.error(e);
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
	public void irModificarExcepcion(ActionEvent event) throws ParseException{
    	log.info("-----------------------Debugging CreditoExcepcionController.irModificarExcepcion-----------------------------");
    	CreditoExcepcionFacadeLocal creditoExcepcionFacade = null;
    	String pIntIdEmpresa = getRequestParameter("pIntIdEmpresa");
    	String pIntIdTipoCredito = getRequestParameter("pIntIdTipoCredito");
    	String pIntIdItemCredito = getRequestParameter("pIntIdItemCredito");
    	String pIntIdItemCreditoExcepcion = getRequestParameter("pIntIdItemCreditoExcepcion");
		CreditoExcepcion creditoExcepcion = null;
    	
    	try {
    		if(pIntIdItemCreditoExcepcion != null && !pIntIdItemCreditoExcepcion.trim().equals("")){
    			creditoExcepcion = new CreditoExcepcion();
    			creditoExcepcion.setId(new CreditoExcepcionId());
    			creditoExcepcion.getId().setIntPersEmpresaPk(new Integer(pIntIdEmpresa));
    			creditoExcepcion.getId().setIntParaTipoCreditoCod(new Integer(pIntIdTipoCredito));
    			creditoExcepcion.getId().setIntItemCredito(new Integer(pIntIdItemCredito));
    			creditoExcepcion.getId().setIntItemCreditoExcepcion(new Integer(pIntIdItemCreditoExcepcion));
    			creditoExcepcionFacade = (CreditoExcepcionFacadeLocal)EJBFactory.getLocal(CreditoExcepcionFacadeLocal.class);
    			beanExcepcion = creditoExcepcionFacade.getCreditoExcepcionPorIdCreditoExcepcion(creditoExcepcion.getId());
				
				if(beanExcepcion.getListaAporteNoTrans()!=null && beanExcepcion.getListaAporteNoTrans().size()>0){
					String[] listaAporteNoTrans = new String[beanExcepcion.getListaAporteNoTrans().size()];
					for(int i=0;i<beanExcepcion.getListaAporteNoTrans().size();i++){
						listaAporteNoTrans[i] = ""+ beanExcepcion.getListaAporteNoTrans().get(i).getId().getIntParaTipoCaptacionCod();
					}
					lstExcepcionAporteNoTrans = listaAporteNoTrans;
				}
				if(beanExcepcion.getListaDiasCobranza()!=null && beanExcepcion.getListaDiasCobranza().size()>0){
					String[] listaDiasCobranza = new String[beanExcepcion.getListaDiasCobranza().size()];
					for(int i=0;i<beanExcepcion.getListaDiasCobranza().size();i++){
						listaDiasCobranza[i] = ""+ beanExcepcion.getListaDiasCobranza().get(i).getId().getIntParaDiasCobranzaCod();
					}
					lstDiasCobranza = listaDiasCobranza;
				}
				
				if(beanExcepcion.getIntParaCampoDobleCuotaCod()==1){
					String[] listaDobleCuota = new String[2];
					listaDobleCuota[0] = "1";
					listaDobleCuota[1] = "";
					lstDobleCuota = listaDobleCuota;
				}else if(beanExcepcion.getIntParaCampoDobleCuotaCod()==2){
					String[] listaDobleCuota = new String[2];
					listaDobleCuota[0] = "";
					listaDobleCuota[1] = "2";
					lstDobleCuota = listaDobleCuota;
				}else{
					lstDobleCuota = new String[2];
					if(beanExcepcion.getIntParaCampoDobleCuotaCod()==-1){
						lstDobleCuota[0] = "1";
						lstDobleCuota[1] = "2";
					}
				}
				reloadCboTipoCaptacion(Constante.CONFIGURACION_CREDITO_EXCEPCION);
				beanExcepcion.setBoAplicaCastigadas(beanExcepcion.getIntAplicaCastigadas()==1);
				
				strCreditoExcepcion = Constante.MANTENIMIENTO_MODIFICAR;
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
	public void modificarExcepcion(ActionEvent event){
		log.info("------------entrando al método CreditoDescuentoController.modificarDescuento------------");
		CreditoExcepcionFacadeLocal facade = null;
		CreditoExcepcionAporteNoTrans creditoExcepcionAporteNoTrans = null;
		List<CreditoExcepcionAporteNoTrans> listaAporteNoTrans = new ArrayList<CreditoExcepcionAporteNoTrans>();
		CreditoExcepcionDiasCobranza creditoExcepcionDiasCobranza = null;
		List<CreditoExcepcionDiasCobranza> listaDiasCobranza = new ArrayList<CreditoExcepcionDiasCobranza>();
		creditoController = (CreditoController)getSessionBean("creditoController");
		
		beanExcepcion.getId().setIntPersEmpresaPk(creditoController.getBeanCredito().getId().getIntPersEmpresaPk());//beanSesion.getIntIdEmpresa()
	    beanExcepcion.getId().setIntParaTipoCreditoCod(creditoController.getBeanCredito().getId().getIntParaTipoCreditoCod());
	    beanExcepcion.getId().setIntItemCredito(creditoController.getBeanCredito().getId().getIntItemCredito());
		
	    if(lstDobleCuota==null || lstDobleCuota.length==0){
	    	beanExcepcion.setIntParaCampoDobleCuotaCod(0);
	    } else if(lstDobleCuota.length>1){
	    	beanExcepcion.setIntParaCampoDobleCuotaCod(-1);
	    }else{
	    	if(lstDobleCuota!=null && lstDobleCuota.length>0){
		    	for(int i=0; i<lstDobleCuota.length;i++){
		    		if(lstDobleCuota[i].equals("1")){
		    			beanExcepcion.setIntParaCampoDobleCuotaCod(1);
		    		}else if(lstDobleCuota[i].equals("2")){
		    			beanExcepcion.setIntParaCampoDobleCuotaCod(2);
		    		}
		    	}
		    }
	    }
	    
	    if(lstExcepcionAporteNoTrans!=null && lstExcepcionAporteNoTrans.length>0){
			for(int i=0;i<lstExcepcionAporteNoTrans.length;i++){
				creditoExcepcionAporteNoTrans = new CreditoExcepcionAporteNoTrans();
				creditoExcepcionAporteNoTrans.setId(new CreditoExcepcionAporteNoTransId());
				creditoExcepcionAporteNoTrans.getId().setIntParaTipoCaptacionCod(new Integer(lstExcepcionAporteNoTrans[i]));
				listaAporteNoTrans.add(creditoExcepcionAporteNoTrans);
			}
			beanExcepcion.setListaAporteNoTrans(listaAporteNoTrans);
		}
	    
	    if(lstDiasCobranza!=null && lstDiasCobranza.length>0){
			for(int i=0;i<lstDiasCobranza.length;i++){
				creditoExcepcionDiasCobranza = new CreditoExcepcionDiasCobranza();
				creditoExcepcionDiasCobranza.setId(new CreditoExcepcionDiasCobranzaId());
				creditoExcepcionDiasCobranza.getId().setIntParaDiasCobranzaCod(new Integer(lstDiasCobranza[i]));
				listaDiasCobranza.add(creditoExcepcionDiasCobranza);
			}
			beanExcepcion.setListaDiasCobranza(listaDiasCobranza);
		}
	    
		try {
			facade = (CreditoExcepcionFacadeLocal)EJBFactory.getLocal(CreditoExcepcionFacadeLocal.class);
			facade.modificarCreditoExcepcion(beanExcepcion);
			listarExcepcion(event);
			limpiarFormCreditoExcepcion();
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
	public void eliminarExcepcion(ActionEvent event){
    	log.info("-----------------------Debugging CreditoExcepcionController.eliminarExcepcion------------------------");
    	String pIntIdEmpresa = getRequestParameter("pIntIdEmpresa");
    	String pIntIdTipoCredito = getRequestParameter("pIntIdTipoCredito");
    	String pIntIdItemCredito = getRequestParameter("pIntIdItemCredito");
    	String pIntIdItemCreditoExcepcion = getRequestParameter("pIntIdItemCreditoExcepcion");
    	CreditoExcepcionFacadeLocal facade = null;
		CreditoExcepcion creditoExcepcion = null;
    	try {
    		creditoExcepcion = new CreditoExcepcion();
    		creditoExcepcion.setId(new CreditoExcepcionId());
    		creditoExcepcion.getId().setIntPersEmpresaPk(new Integer(pIntIdEmpresa));
    		creditoExcepcion.getId().setIntParaTipoCreditoCod(new Integer(pIntIdTipoCredito));
    		creditoExcepcion.getId().setIntItemCredito(new Integer(pIntIdItemCredito));
    		creditoExcepcion.getId().setIntItemCreditoExcepcion(new Integer(pIntIdItemCreditoExcepcion));
			facade = (CreditoExcepcionFacadeLocal)EJBFactory.getLocal(CreditoExcepcionFacadeLocal.class);
			facade.eliminarCreditoExcepcion(creditoExcepcion);
			listarExcepcion(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
}