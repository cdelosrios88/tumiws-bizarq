package pe.com.tumi.tesoreria.popup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;
import pe.com.tumi.persona.empresa.domain.TipoComprobante;

public class TipoComprobanteController {
	protected   static Logger 		    	log = Logger.getLogger(TipoComprobanteController.class);
	private List<TipoComprobante>			listComprobanteJuridica = null;
	private List<TipoComprobante>			listComprobanteProveedor = null;
	private List<TipoComprobante>			listTipoComprobSocioNatuEmp = null;
	private ArrayList<TipoComprobante>		arrayTipoComprobante = new ArrayList<TipoComprobante>();
	private String 							strIdModalPanel = null;
	private String 							pgListTipoComprobante = null;
	private String 							strCallingFormId;
	
	
	public TipoComprobanteController(){
		listComprobanteJuridica = new ArrayList<TipoComprobante>();
		listComprobanteProveedor = new ArrayList<TipoComprobante>();
		listTipoComprobSocioNatuEmp = new ArrayList<TipoComprobante>();
		arrayTipoComprobante = new ArrayList<TipoComprobante>();
		strIdModalPanel = "pAgregarTipoComprobante";
		pgListTipoComprobante = "pgDetalleJuridica,tbTipoComprobante";
		
	}
	
	// ---------------------------------------------------
	// Métodos de ComunicacionController
	// ---------------------------------------------------
	public void showTipoComprobJuridica(ActionEvent event){
		/*log.info("--------------------Debugging TipoComprobanteController.showTipoComprobJuridica-------------------");
		log.info("listActEconomicaJuridica.size: "+listComprobanteJuridica.size());*/
		listarTipoComprobante(listComprobanteJuridica);
		setStrCallingFormId("frmPerJuriConve");
		//log.info("listComprobanteJuridica.size: "+listComprobanteJuridica.size());
	}
	
	public void showTipoComprobJuridicaProveedor(ActionEvent event){
		/*log.info("--------------------Debugging TipoComprobanteController.showTipoComprobJuridica-------------------");
		log.info("listComprobanteProveedor: "+listComprobanteProveedor.size());*/		
		listarTipoComprobante(listComprobanteProveedor);
		setStrCallingFormId("frmProveedor");
		//log.info("listComprobanteProveedor: "+listComprobanteProveedor.size());
	}
	
	public void showTipoComprobSocioNatuEmp(ActionEvent event){
		log.info("--------------------Debugging TipoComprobanteController.showTipoComprobSocioNatuEmp-------------------");
		log.info("listTipoComprobSocioNatuEmp.size: "+listTipoComprobSocioNatuEmp.size());
		listarTipoComprobante(listTipoComprobSocioNatuEmp);
		setStrCallingFormId("frmSocioEmpresa");
		log.info("listTipoComprobSocioNatuEmp.size: "+listTipoComprobSocioNatuEmp.size());
	}
	
	public void seleccionarTipoComprobante(ActionEvent event){
		//log.info("--------------------Debugging TipoComprobanteController.seleccionarActEconomica-------------------");
		//log.info("strCallingFormId:"+strCallingFormId);
		if(strCallingFormId!=null && strCallingFormId.equals("frmPerJuriConve")){
			selectChecked(listComprobanteJuridica);
		
		}else if(strCallingFormId!=null && strCallingFormId.equals("frmSocioEmpresa")){
			selectChecked(listTipoComprobSocioNatuEmp);
		
		}else if(strCallingFormId!=null && strCallingFormId.equals("frmProveedor")){
			selectChecked(listComprobanteProveedor);
		}
	}
	
	public void selectChecked(List<TipoComprobante> listaTipoComprobante){
		if(arrayTipoComprobante==null){
			return;
		}
		
		/*if(listaTipoComprobante!=null){
			listaTipoComprobante.clear();
		}
		
		TipoComprobante comp = null;
		//log.info("arrayTipoComprobante.size: "+arrayTipoComprobante.size());
		for(int i=0; i<arrayTipoComprobante.size(); i++){
			comp = arrayTipoComprobante.get(i);
			//log.info("TipoComprobante.getBlnChecked: "+comp.getBlnChecked());
			if(comp.getBlnChecked()!=null && comp.getBlnChecked()){
				//log.info("comprobante.tabla.intIdDetalle: "+comp.getTabla().getIntIdDetalle());
				comp.setIntTipoComprobanteCod(comp.getTabla().getIntIdDetalle());
				listaTipoComprobante.add(comp);
			}
		}*/
		
		boolean seEncuentraAgregado;
		for(TipoComprobante tipoComprobanteTemp : arrayTipoComprobante){
			if(tipoComprobanteTemp.getBlnChecked()){
				seEncuentraAgregado = Boolean.FALSE;
				for(TipoComprobante tipoComprobante : listaTipoComprobante){
					if(tipoComprobante.getIntTipoComprobanteCod().equals(tipoComprobanteTemp.getTabla().getIntIdDetalle())){
						seEncuentraAgregado = Boolean.TRUE;
						break;
					}
				}
				if(!seEncuentraAgregado){
					TipoComprobante tc = new TipoComprobante();
					tc.setIntTipoComprobanteCod(tipoComprobanteTemp.getTabla().getIntIdDetalle());
					tc.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaTipoComprobante.add(tc);
				}
			}else{
				seEncuentraAgregado = Boolean.FALSE;
				for(TipoComprobante tipoComprobante : listaTipoComprobante){
					if(tipoComprobante.getIntTipoComprobanteCod().equals(tipoComprobanteTemp.getTabla().getIntIdDetalle())){
						seEncuentraAgregado = Boolean.TRUE;
						tipoComprobante.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						break;
					}
				}
			}
		}
	}
	
	
	private boolean validarDuplicidadTipoComprobante(List<TipoComprobante> listaTipoComprobante, TipoComprobante comp){
		for(TipoComprobante tipoComprobante : listaTipoComprobante){
			if(tipoComprobante.getIntTipoComprobanteCod().equals(comp.getIntTipoComprobanteCod())){
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	
	public void selectChecked2(List<TipoComprobante> listaTipoComprobante){
		if(arrayTipoComprobante==null){
			return;
		}

		for(TipoComprobante comp : arrayTipoComprobante){			
			if(comp.getBlnChecked()!=null && comp.getBlnChecked()){				
				comp.setIntTipoComprobanteCod(comp.getTabla().getIntIdDetalle());
				if(validarDuplicidadTipoComprobante(listaTipoComprobante,comp)){
					listaTipoComprobante.add(comp);
				}				
			}
		}
	}
	
	
	public void limpiarArrayTipoComprobante(ActionEvent event){
		log.info("--------------------Debugging TipoComprobanteController.limpiarArrayTipoComprobante-------------------");
		if(arrayTipoComprobante!=null){
			arrayTipoComprobante.clear();
		}
		List<Tabla> listTabla = null;
		listTabla = getListaTipoComprobanteParam();
		
		//Limpiar y volver a llenar arrayTipoComprobante desde Parametros
		setArrayTipoComprobante(new ArrayList<TipoComprobante>());
		TipoComprobante comp = null;
		//Tabla tabla = null;
		for(int i=0; i<listTabla.size(); i++){
			comp = new TipoComprobante();
			comp.setTabla(listTabla.get(i));
			arrayTipoComprobante.add(comp);
		}
	}
	
	public void listarTipoComprobante(List<TipoComprobante> listTipoComprobante){
		//log.info("-----------------------Debugging ComunicacionController.listarTipoComprobante-----------------------------");
		//log.info("arrayTipoComprobante.size: "+arrayTipoComprobante.size());
		
	    List<Tabla> listTabla = getListaTipoComprobanteParam();
		
		//Limpiar y volver a llenar arrayTipoComprobante desde Parametros
	    arrayTipoComprobante = new ArrayList<TipoComprobante>();
		TipoComprobante comp = null;
		//Tabla tabla = null;
		for(int i=0; i<listTabla.size(); i++){
			comp = new TipoComprobante();
			comp.setTabla(listTabla.get(i));
			comp.setBlnChecked(Boolean.FALSE);
			arrayTipoComprobante.add(comp);
		}

		
		//TipoComprobante comprobante = null;
		//marcar con check aquellas que estan en listActEconomicaJuridica
		/*for(int i=0; i<listTipoComprobante.size(); i++){
			comprobante = listTipoComprobante.get(i);
			
			for(int j=0; j<arrayTipoComprobante.size(); j++){
				comp = arrayTipoComprobante.get(j);
				if(comp.getTabla().getIntIdDetalle().equals(comprobante.getTabla().getIntIdDetalle())){
					comp.setBlnChecked(true);
				}
			}
		}*/
		
		for(TipoComprobante tipoComprobante : listTipoComprobante){
			for(TipoComprobante tipoComprobanteTemp : arrayTipoComprobante){
				if(tipoComprobante.getIntTipoComprobanteCod().equals(tipoComprobanteTemp.getTabla().getIntIdDetalle())){
					//log.info("--match");
					tipoComprobanteTemp.setBlnChecked(Boolean.TRUE);
					break;
				}
			}
		}
	}
	
	public List<Tabla> getListaTipoComprobanteParam(){
		//log.info("-----------------------Debugging ComunicacionController.getListaTipoComprobanteParam-----------------------------");
		
	    List<Tabla> listTabla = null;
		try {
    		TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
    		listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCOMPROBANTE));
    		//log.info("listTabla.size: "+listTabla.size());
    		
    		
    		Collections.sort(listTabla, new Comparator<Tabla>(){
    			public int compare(Tabla uno, Tabla otro) {
    				return uno.getIntOrden().compareTo(otro.getIntOrden());
    			}
    		});
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listTabla;
	}
	
	protected void setMessageSuccess(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(msg));
	}
	protected void setMessageSuccess(String zone, String msg) {
		FacesContext.getCurrentInstance().addMessage(zone,new FacesMessage(msg));
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	
	//Getters and Setters
	public String getStrIdModalPanel() {
		return strIdModalPanel;
	}
	public void setStrIdModalPanel(String strIdModalPanel) {
		this.strIdModalPanel = strIdModalPanel;
	}
	public String getPgListTipoComprobante() {
		return pgListTipoComprobante;
	}
	public void setPgListTipoComprobante(String pgListTipoComprobante) {
		this.pgListTipoComprobante = pgListTipoComprobante;
	}
	public List<TipoComprobante> getListComprobanteJuridica() {
		return listComprobanteJuridica;
	}
	public void setListComprobanteJuridica(
			List<TipoComprobante> listComprobanteJuridica) {
		this.listComprobanteJuridica = listComprobanteJuridica;
	}
	public ArrayList<TipoComprobante> getArrayTipoComprobante() {
		return arrayTipoComprobante;
	}
	public void setArrayTipoComprobante(
			ArrayList<TipoComprobante> arrayTipoComprobante) {
		this.arrayTipoComprobante = arrayTipoComprobante;
	}
	public List<TipoComprobante> getListTipoComprobSocioNatuEmp() {
		return listTipoComprobSocioNatuEmp;
	}
	public void setListTipoComprobSocioNatuEmp(
			List<TipoComprobante> listTipoComprobSocioNatuEmp) {
		this.listTipoComprobSocioNatuEmp = listTipoComprobSocioNatuEmp;
	}
	public String getStrCallingFormId() {
		return strCallingFormId;
	}
	public void setStrCallingFormId(String strCallingFormId) {
		this.strCallingFormId = strCallingFormId;
	}
	public List<TipoComprobante> getListComprobanteProveedor() {
		return listComprobanteProveedor;
	}
	public void setListComprobanteProveedor(List<TipoComprobante> listComprobanteProveedor) {
		this.listComprobanteProveedor = listComprobanteProveedor;
	}
}