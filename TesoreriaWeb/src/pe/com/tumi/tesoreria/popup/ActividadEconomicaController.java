package pe.com.tumi.tesoreria.popup;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;
import pe.com.tumi.persona.empresa.domain.ActividadEconomicaPK;

public class ActividadEconomicaController {
	protected   static Logger 		    	log = Logger.getLogger(ActividadEconomicaController.class);
	private 	ActividadEconomica				beanActEconomica = null;
	private 	List<ActividadEconomica>		listActEconomicaJuridica = null;
	private 	List<ActividadEconomica>		listActEconomicaProveedor = null;
	private 	List<ActividadEconomica>		listActiEcoSocioNatu = null;
	private 	List<ActividadEconomica>		listActiEcoSocioNatuEmp = null;
	private 	ArrayList<ActividadEconomica>	arrayActividadEconomica = new ArrayList<ActividadEconomica>();
	private 	String 							strActEconomicaBusq;
	private 	String 							strIdModalPanel = null;
	private 	String 							pgListActividadEconomica = null;
	private 	String 							strCallingFormId;
	private 	String 							strFormIdJuriConve;
	private 	String 							strFormIdSocioNatu;
	private 	String 							strFormIdSocioNatuEmp;
	private		String							strFormIdJuriProveedor;
	
	
	public ActividadEconomicaController(){
		beanActEconomica = new ActividadEconomica();
		beanActEconomica.setId(new ActividadEconomicaPK());
		listActEconomicaJuridica = new ArrayList<ActividadEconomica>();
		listActEconomicaProveedor = new ArrayList<ActividadEconomica>();
		listActiEcoSocioNatuEmp = new ArrayList<ActividadEconomica>();
		arrayActividadEconomica = new ArrayList<ActividadEconomica>();
		strActEconomicaBusq = "";
		strIdModalPanel = "pAgregarActividadEconomica";
		pgListActividadEconomica = "pgDetalleJuridica,tbActividadEconomica";
		
		strFormIdJuriConve = "frmPerJuriConve";
		strFormIdJuriProveedor = "frmProveedor";
		strFormIdSocioNatu = "frmSocioNatural";
		strFormIdSocioNatuEmp = "frmSocioEmpresa";
	}
	
	// ---------------------------------------------------
	// Métodos de ActividadEconomicaController
	// ---------------------------------------------------
	public void showActiEcoJuridica(ActionEvent event){
		log.info("--------------------Debugging ActividadEconomicaController.showActEconomica-------------------");
		setStrCallingFormId(strFormIdJuriConve);
		log.info("listActEconomicaJuridica.size: "+listActEconomicaJuridica.size());
		listarActividadEconomica(listActEconomicaJuridica);
	}
	
	public void showActiEcoJuridicaProveedor(ActionEvent event){
		//log.info("--------------------Debugging ActividadEconomicaController.showActEconomicaProveedor-------------------");
		setStrCallingFormId(strFormIdJuriProveedor);
		//log.info("listActEconomicaJuridicaProv.size: "+listActEconomicaProveedor.size());
		listarActividadEconomica(listActEconomicaProveedor);
	}
	
	public void showActiEcoSocioNatu(ActionEvent event){
		log.info("--------------------Debugging ActividadEconomicaController.showActiEcoSocioNatu-------------------");
		setStrCallingFormId(strFormIdSocioNatu);
		log.info("listActiEcoSocioNatu.size: "+listActiEcoSocioNatuEmp.size());
		listarActividadEconomica(listActiEcoSocioNatuEmp);
	}
	
	public void showActiEcoSocioNatuEmp(ActionEvent event){
		log.info("--------------------Debugging ActividadEconomicaController.showActiEcoSocioNatuEmp-------------------");
		setStrCallingFormId(strFormIdSocioNatuEmp);
		log.info("listActiEcoSocioNatuEmp.size: "+listActiEcoSocioNatuEmp.size());
		listarActividadEconomica(listActiEcoSocioNatuEmp);
	}
	
	public void seleccionarActEconomica(ActionEvent event){
		//log.info("--------------------Debugging ActividadEconomicaController.seleccionarActEconomica-------------------");
		
		if(strCallingFormId!=null && strCallingFormId.equals(strFormIdJuriConve)){
			selectChecked(listActEconomicaJuridica);
		
		}else if(strCallingFormId!=null && strCallingFormId.equals(strFormIdSocioNatu)){
			selectChecked(listActiEcoSocioNatu);
		
		}else if(strCallingFormId!=null && strCallingFormId.equals(strFormIdSocioNatuEmp)){
			selectChecked(listActiEcoSocioNatuEmp);
		
		}else if(strCallingFormId!=null && strCallingFormId.equals(strFormIdJuriProveedor)){
			selectChecked(listActEconomicaProveedor);
		}
	}
	
	public void selectChecked(List<ActividadEconomica> listaActiEconomica){
		if(arrayActividadEconomica==null){
			return;
		}
		
		if(listaActiEconomica!=null){
			listaActiEconomica.clear();
		}
		
		ActividadEconomica act = null;
		//log.info("arrayActividadEconomica.size: "+arrayActividadEconomica.size());
		for(int i=0; i<arrayActividadEconomica.size(); i++){
			act = arrayActividadEconomica.get(i);
			if(act.getBlnChecked()!=null && act.getBlnChecked()){
				//log.info("actividad.tabla.intIdDetalle: "+act.getTabla().getIntIdDetalle());
				act.setIntActividadEconomicaCod(act.getTabla().getIntIdDetalle());
				listaActiEconomica.add(act);
			}
		}
	}
	
	private boolean validarDuplicidadActividad(List<ActividadEconomica> lista, ActividadEconomica actividad){
		for(ActividadEconomica actividadEconomica : lista){
			if(actividadEconomica.getIntActividadEconomicaCod().equals(actividad.getIntActividadEconomicaCod())){
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	public void selectChecked2(List<ActividadEconomica> listaActiEconomica){
		if(arrayActividadEconomica==null){
			return;
		}
		
		for(ActividadEconomica act : arrayActividadEconomica){			
			if(act.getBlnChecked()!=null && act.getBlnChecked()){
				act.setIntActividadEconomicaCod(act.getTabla().getIntIdDetalle());
				if(validarDuplicidadActividad(listaActiEconomica,act)){
					listaActiEconomica.add(act);
				}
			}
		}
	}
	
	public void limpiarArrayActividadEconomica(ActionEvent event){
		log.info("--------------------Debugging ActividadEconomicaController.limpiarArrayActividadEconomica-------------------");
		if(arrayActividadEconomica!=null){
			arrayActividadEconomica.clear();
		}
		
		List<Tabla> listTabla = null;
		listTabla = getListaActEconomicaParam();
		
		//Limpiar y volver a llenar arrayActividadEconomica desde Parametros
		setArrayActividadEconomica(new ArrayList<ActividadEconomica>());
		ActividadEconomica act = null;
		//Tabla tabla = null;
		for(int i=0; i<listTabla.size(); i++){
			act = new ActividadEconomica();
			act.setTabla(listTabla.get(i));
			arrayActividadEconomica.add(act);
		}
	}
	
	public void listarActividadEconomica2(List<ActividadEconomica> listActEconomica){
		log.info("-----------------------Debugging ComunicacionController.listarActividadEconomica-----------------------------");
		log.info("arrayActividadEconomica.size: "+arrayActividadEconomica.size());
		
	    List<Tabla> listTabla = getListaActEconomicaParam();
		
		//Limpiar y volver a llenar arrayActividadEconomica desde Parametros
		arrayActividadEconomica = new ArrayList<ActividadEconomica>();
		//Tabla tabla = null;
		for(Tabla tabla : listTabla){
			ActividadEconomica act = new ActividadEconomica();
			act.setTabla(tabla);
			arrayActividadEconomica.add(act);
		}
		
		//marcar con check aquellas que estan en listActEconomicaJuridica
		for(ActividadEconomica actividad : listActEconomica){			
			for(ActividadEconomica act : arrayActividadEconomica){
				if(act.getTabla().getIntIdDetalle().equals(actividad.getIntActividadEconomicaCod())){
					log.info("cheked:"+actividad.getIntActividadEconomicaCod());
					act.setBlnChecked(true);
				}
			}
		}
	}
	
	
	public void listarActividadEconomica(List<ActividadEconomica> listActEconomica){
		//log.info("-----------------------Debugging ComunicacionController.listarActividadEconomica-----------------------------");
		//log.info("arrayActividadEconomica.size: "+arrayActividadEconomica.size());
		
	    List<Tabla> listTabla = null;
		listTabla = getListaActEconomicaParam();
		
		//Limpiar y volver a llenar arrayActividadEconomica desde Parametros
		setArrayActividadEconomica(new ArrayList<ActividadEconomica>());
		ActividadEconomica act = null;
		//Tabla tabla = null;
		for(int i=0; i<listTabla.size(); i++){
			act = new ActividadEconomica();
			act.setTabla(listTabla.get(i));
			arrayActividadEconomica.add(act);
		}
		
		ActividadEconomica actividad = null;
		//marcar con check aquellas que estan en listActEconomicaJuridica
		for(int i=0; i<listActEconomica.size(); i++){
			actividad = listActEconomica.get(i);
			
			for(int j=0; j<arrayActividadEconomica.size(); j++){
				act = arrayActividadEconomica.get(j);
				if(act.getTabla().getIntIdDetalle().equals(actividad.getTabla().getIntIdDetalle())){
					act.setBlnChecked(true);
				}
			}
		}
	}
	
	public void buscarActEconomicaPorDesc(ActionEvent event){
		/*log.info("-----------------------Debugging ComunicacionController.buscarActEconomicaPorDesc-----------------------------");
		log.info("strActEconomicaBusq: "+strActEconomicaBusq);*/
		
		ActividadEconomica actividad = null;
	    List<Tabla> listTabla = null;
		listTabla = getListaActEconomicaParam();
		
		//Limpiar
		setArrayActividadEconomica(new ArrayList<ActividadEconomica>());
		
		for(int i=0; i<listTabla.size(); i++){
			if(listTabla.get(i).getStrDescripcion().toLowerCase().contains(strActEconomicaBusq.toLowerCase())){
				actividad = new ActividadEconomica();
				actividad.setTabla(listTabla.get(i));
				arrayActividadEconomica.add(actividad);
			}
		}
		//log.info("arrayActividadEconomica.size: "+arrayActividadEconomica.size());
	}
	
	public List<Tabla> getListaActEconomicaParam(){
		//log.info("-----------------------Debugging ComunicacionController.getListaActEconomicaParam-----------------------------");
		
	    List<Tabla> listTabla = null;
		try {
    		TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
    		listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_ACTIVIDAD_ECONOMICA));
    		//log.info("listTabla.size: "+listTabla.size());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listTabla;
	}
	
	protected void setMessageSuccess(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(msg));
	}
	protected void setMessageSuccess(String zone, String msg) {
		FacesContext.getCurrentInstance().addMessage(zone,
				new FacesMessage(msg));
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	//Getters and Setters
	public ActividadEconomica getBeanActEconomica() {
		return beanActEconomica;
	}
	public void setBeanActEconomica(ActividadEconomica beanActEconomica) {
		this.beanActEconomica = beanActEconomica;
	}
	public List<ActividadEconomica> getListActEconomicaJuridica() {
		return listActEconomicaJuridica;
	}
	public void setListActEconomicaJuridica(
			List<ActividadEconomica> listActEconomicaJuridica) {
		this.listActEconomicaJuridica = listActEconomicaJuridica;
	}
	public ArrayList<ActividadEconomica> getArrayActividadEconomica() {
		return arrayActividadEconomica;
	}
	public void setArrayActividadEconomica(
			ArrayList<ActividadEconomica> arrayActividadEconomica) {
		this.arrayActividadEconomica = arrayActividadEconomica;
	}
	public String getStrActEconomicaBusq() {
		return strActEconomicaBusq;
	}
	public void setStrActEconomicaBusq(String strActEconomicaBusq) {
		this.strActEconomicaBusq = strActEconomicaBusq;
	}
	public String getStrIdModalPanel() {
		return strIdModalPanel;
	}
	public void setStrIdModalPanel(String strIdModalPanel) {
		this.strIdModalPanel = strIdModalPanel;
	}
	public List<ActividadEconomica> getListActiEcoSocioNatuEmp() {
		return listActiEcoSocioNatuEmp;
	}
	public void setListActiEcoSocioNatuEmp(
			List<ActividadEconomica> listActiEcoSocioNatuEmp) {
		this.listActiEcoSocioNatuEmp = listActiEcoSocioNatuEmp;
	}
	public String getStrCallingFormId() {
		return strCallingFormId;
	}
	public void setStrCallingFormId(String strCallingFormId) {
		this.strCallingFormId = strCallingFormId;
	}
	public String getPgListActividadEconomica() {
		return pgListActividadEconomica;
	}
	public void setPgListActividadEconomica(String pgListActividadEconomica) {
		this.pgListActividadEconomica = pgListActividadEconomica;
	}
	public String getStrFormIdSocioNatu() {
		return strFormIdSocioNatu;
	}
	public void setStrFormIdSocioNatu(String strFormIdSocioNatu) {
		this.strFormIdSocioNatu = strFormIdSocioNatu;
	}
	public List<ActividadEconomica> getListActiEcoSocioNatu() {
		return listActiEcoSocioNatu;
	}
	public void setListActiEcoSocioNatu(
			List<ActividadEconomica> listActiEcoSocioNatu) {
		this.listActiEcoSocioNatu = listActiEcoSocioNatu;
	}
	public String getStrFormIdJuriConve() {
		return strFormIdJuriConve;
	}
	public void setStrFormIdJuriConve(String strFormIdJuriConve) {
		this.strFormIdJuriConve = strFormIdJuriConve;
	}
	public String getStrFormIdSocioNatuEmp() {
		return strFormIdSocioNatuEmp;
	}
	public void setStrFormIdSocioNatuEmp(String strFormIdSocioNatuEmp) {
		this.strFormIdSocioNatuEmp = strFormIdSocioNatuEmp;
	}
	public List<ActividadEconomica> getListActEconomicaProveedor() {
		return listActEconomicaProveedor;
	}
	public void setListActEconomicaProveedor(
			List<ActividadEconomica> listActEconomicaProveedor) {
		this.listActEconomicaProveedor = listActEconomicaProveedor;
	}
}