package pe.com.tumi.popup.controller;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.popup.service.impl.ComunicacionServiceImpl;

public class ComunicacionController extends GenericController {
	private ComunicacionServiceImpl			comunicacionService;
	private Comunicacion					beanComunicacion = new Comunicacion();
	private Boolean							formCasoEmerg = true;
	private List 							beanListComunicacion = new ArrayList<Comunicacion>();
	private List 							beanListComuniPerJuri = new ArrayList<Comunicacion>();
	private List 							beanListComuniRepLegal = new ArrayList<Comunicacion>();
	private Boolean							chkDescripcion;
	private Boolean							chkCasoEmerg;
	private String 							strInsertUpdate;
	private String 							strIdModalPanel = "pAgregarComunicacion"; 
	private String 							pgListComunicacion = "pgListComunicacion,tbComunicaciones";
	private Boolean							enableDisableFormComunicacion = false;
	private Boolean							enableDisableDescComunicacion = true;
	private String 							strIdBtnComunicacion;
	private ArrayList						arrayComunicacion = new ArrayList();
	
	//Getters and Setters
	public ComunicacionServiceImpl getComunicacionService() {
		return comunicacionService;
	}
	public void setComunicacionService(ComunicacionServiceImpl comunicacionService) {
		this.comunicacionService = comunicacionService;
	}
	public Comunicacion getBeanComunicacion() {
		return beanComunicacion;
	}
	public void setBeanComunicacion(Comunicacion beanComunicacion) {
		this.beanComunicacion = beanComunicacion;
	}
	public Boolean getFormCasoEmerg() {
		return formCasoEmerg;
	}
	public void setFormCasoEmerg(Boolean formCasoEmerg) {
		this.formCasoEmerg = formCasoEmerg;
	}
	public List getBeanListComunicacion() {
		return beanListComunicacion;
	}
	public void setBeanListComunicacion(List beanListComunicacion) {
		this.beanListComunicacion = beanListComunicacion;
	}
	public Boolean getChkDescripcion() {
		return chkDescripcion;
	}
	public void setChkDescripcion(Boolean chkDescripcion) {
		this.chkDescripcion = chkDescripcion;
	}
	public Boolean getChkCasoEmerg() {
		return chkCasoEmerg;
	}
	public void setChkCasoEmerg(Boolean chkCasoEmerg) {
		this.chkCasoEmerg = chkCasoEmerg;
	}
	public String getStrInsertUpdate() {
		return strInsertUpdate;
	}
	public void setStrInsertUpdate(String strInsertUpdate) {
		this.strInsertUpdate = strInsertUpdate;
	}
	public String getStrIdModalPanel() {
		return strIdModalPanel;
	}
	public void setStrIdModalPanel(String strIdModalPanel) {
		this.strIdModalPanel = strIdModalPanel;
	}
	public String getPgListComunicacion() {
		return pgListComunicacion;
	}
	public void setPgListComunicacion(String pgListComunicacion) {
		this.pgListComunicacion = pgListComunicacion;
	}
	public Boolean getEnableDisableFormComunicacion() {
		return enableDisableFormComunicacion;
	}
	public void setEnableDisableFormComunicacion(
			Boolean enableDisableFormComunicacion) {
		this.enableDisableFormComunicacion = enableDisableFormComunicacion;
	}
	public Boolean getEnableDisableDescComunicacion() {
		return enableDisableDescComunicacion;
	}
	public void setEnableDisableDescComunicacion(
			Boolean enableDisableDescComunicacion) {
		this.enableDisableDescComunicacion = enableDisableDescComunicacion;
	}
	public List getBeanListComuniPerJuri() {
		return beanListComuniPerJuri;
	}
	public void setBeanListComuniPerJuri(List beanListComuniPerJuri) {
		this.beanListComuniPerJuri = beanListComuniPerJuri;
	}
	public List getBeanListComuniRepLegal() {
		return beanListComuniRepLegal;
	}
	public void setBeanListComuniRepLegal(List beanListComuniRepLegal) {
		this.beanListComuniRepLegal = beanListComuniRepLegal;
	}
	public String getStrIdBtnComunicacion() {
		return strIdBtnComunicacion;
	}
	public void setStrIdBtnComunicacion(String strIdBtnComunicacion) {
		this.strIdBtnComunicacion = strIdBtnComunicacion;
	}
	public ArrayList getArrayComunicacion() {
		return arrayComunicacion;
	}
	public void setArrayComunicacion(ArrayList arrayComunicacion) {
		this.arrayComunicacion = arrayComunicacion;
	}
	// ---------------------------------------------------
	// Métodos de ComunicacionController
	// ---------------------------------------------------
	public void agregarComunicacion(ActionEvent event){
		log.info("--------------------Debugging ComunicacionController.agregarComunicacion-------------------");
		limpiarFormComunicacion();
		
		UIComponent uicomp = event.getComponent();
		String idcomp = uicomp.getId();
		log.info("idcomp"+idcomp);
		
		if(idcomp.equals("btnPerJuriComunicacion")){
			setStrIdBtnComunicacion("btnPerJuriComunicacion");
		}else if(idcomp.equals("btnRepLegalComunicacion")){
			setStrIdBtnComunicacion("btnRepLegalComunicacion");
		}
		
	}
	
	public void limpiarFormComunicacion(){
		log.info("--------------------Debugging ComunicacionController.limpiarFormComunicacion-------------------");
		//Limpiar el bean comunicacion
		Comunicacion comu = new Comunicacion();
		setBeanComunicacion(comu);
	}
	
	public void limpiarArrayComunicacion(){
		log.info("--------------------Debugging ComunicacionController.limpiarArrayComunicacion-------------------");
		if(arrayComunicacion!=null){
			arrayComunicacion.clear();
		}
	}
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarComunicacion()           						*/
	/*                                                    	 		*/
	/*  Parametros. :  Integer intIdPersona               	 		*/
	/*                 intIdPersona = el Id de la persona a la cual */ 
	/*                 se listará el o los Comunicacions que tenga     */
	/*                 registrados                         	 		*/
	/*  Objetivo: Grabar un Nuevo Comunicacion de acuerdo al Id de la  */
	/*            Persona                                  	 		*/
	/*  Retorno : Nuevo Comunicacion generado correctamente	 		*/
	/**************************************************************/
	public void listarComunicacion(Integer intIdPersona, Integer intIdComunicacion){
		log.info("-----------------------Debugging ComunicacionController.listarComunicacion-----------------------------");
	    setPopupService(comunicacionService);
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de comunicación: ");
		log.info("-----------------------------------------------------");
		HashMap prmtBusqComunicacion = new HashMap();
		log.info("pIntIdpersona = "+ intIdPersona);
		prmtBusqComunicacion.put("pIntIdPersona", 		intIdPersona);
		prmtBusqComunicacion.put("pIntIdComunicacion", 	intIdComunicacion);
		
	    ArrayList arrayComunicacion = new ArrayList();
	    ArrayList listaComunicacion = new ArrayList();
	    
    	try {
	    	arrayComunicacion = getPopupService().listarComunicacion(prmtBusqComunicacion);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarComunicacion() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMessageSuccess("Listado de Comunicacions ha sido satisfactorio");
		
		for(int i=0; i<arrayComunicacion.size(); i++){
			Comunicacion com = (Comunicacion) arrayComunicacion.get(i);
			log.info("COMU_IDPERSONA_N   			= " +com.getId().getIntIdPersona());
			log.info("COMU_IDCOMUNICACION_N 		= " +com.getId().getIntIdComunicacion());
			log.info("COMU_IDTIPOCOMUNICACION_N 	= " +com.getIntTipoComunicacionCod());
			log.info("COMU_IDSUBTIPOCOMUNICACION_N 	= " +com.getIntSubTipoComunicacionCod());
			log.info("COMU_IDTIPOLINEA_N 			= " +com.getIntTipoLineaCod());
			log.info("COMU_NUMERO_N 				= " +com.getIntNumero());
			log.info("COMU_ANEXO_N 					= " +com.getIntAnexo());
			log.info("COMU_DESCRIPCION_C 			= " +com.getStrDescripcion());
			log.info("COMU_CASOEMERGENCIA_N 		= " +com.getIntCasoEmergencia());
			log.info("COMU_IDESTADO_N 				= " +com.getIntEstadoCod());
			com.setStrDato("Tipo Línea: " + (com.getIntTipoComunicacionCod() == 1? "Teléfono" : "Fax" ) + " - Nro.: " + com.getIntNumero());
			
			listaComunicacion.add(com);
		}
		
		log.info("getBeanListComunicacion().size(): "+getBeanListComunicacion().size());
		setBeanListComunicacion(listaComunicacion);
	}
	
	/**************************************************************/
	/*                                                    	 					*/
	/*  Nombre :  grabarComunicacion()           								*/
	/*                                                    	 					*/
	/*  Parametros. :  Integer intIdPersona               	 					*/
	/*                 intIdPersona = el Id de la persona a la cual				*/ 
	/*                 se le agregará un nuevo Comunicacion            			*/
	/*                                                    	 					*/
	/*  Objetivo: Grabar un Nuevo Comunicacion de acuerdo al Id de la Persona 	*/
	/*  Retorno : Nuevo Comunicacion generado correctamente	 		*/
	/**************************************************************/
	public void grabarComunicacion(ActionEvent event){
	  log.info("-----------------------Debugging ComunicacionController.grabarComunicacion-----------------------------");
	  setPopupService(comunicacionService);
      log.info("Se ha seteado el Service");
      Comunicacion com = new Comunicacion();
      com = (Comunicacion) getBeanComunicacion();
      com.setIntCasoEmergencia(getChkCasoEmerg()==true?1:0);
      
      String descCom = ("Tipo Línea: "+ (com.getIntTipoComunicacionCod() == 1 ? "Claro" : 
    	  								 com.getIntTipoComunicacionCod() == 2 ? "Fijo" : 
    	  								 com.getIntTipoComunicacionCod() == 3 ? "Movistar" :
    	  								 com.getIntTipoComunicacionCod() == 4 ? "Nextel" :"") + 
    	  								 " - Nro.: " + com.getIntNumero());
      com.setStrComunicacion(descCom);
      
      ArrayList arrCom = new ArrayList();
      ArrayList listaComunicacion = new ArrayList();
      
      //setBeanListComunicacion(arrCom);
      log.info("strIdBtnComunicacion: "+getStrIdBtnComunicacion());
      if(getStrIdBtnComunicacion()!=null && getStrIdBtnComunicacion().equals("btnPerJuriComunicacion")){
    	  if(getBeanListComuniPerJuri()!=null){
        	  arrCom = (ArrayList) getBeanListComuniPerJuri();
          }
    	  arrCom.add(com);
    	  
    	  setBeanListComuniPerJuri(arrCom);
      }else if(getStrIdBtnComunicacion()!=null && getStrIdBtnComunicacion().equals("btnRepLegalComunicacion")){
    	  if(getBeanListComuniRepLegal()!=null){
        	  arrCom = (ArrayList) getBeanListComuniRepLegal();
          }
    	  arrCom.add(com);
    	  
    	  setBeanListComuniRepLegal(arrCom);
	  }
      arrayComunicacion.add(com);
      if(getBeanListComunicacion()!=null && getStrInsertUpdate().equals("I") && getBeanListComunicacion().size()==0){
    	  for(int i=0; i<getBeanListComunicacion().size(); i++){
    		  Comunicacion comu = (Comunicacion) getBeanListComunicacion().get(i);
    		  listaComunicacion.add(comu);
          }
      }
      
      if(getBeanListComunicacion()!=null && getStrInsertUpdate().equals("U")){
    	  for(int i=0; i<getBeanListComunicacion().size(); i++){
    		  Comunicacion comu = (Comunicacion) getBeanListComunicacion().get(i);
    		  listaComunicacion.add(comu);
          }
      }
      
      for(int i=0;i<arrayComunicacion.size(); i++){
    	  Comunicacion comu = (Comunicacion) arrayComunicacion.get(i);
    	  listaComunicacion.add(comu);
      }
      setBeanListComunicacion(listaComunicacion);
      
      log.info("BeneficiarioController.getIntIdPersona(): " + BeneficiarioController.getIntIdPersona());
      Integer intIdPersona = BeneficiarioController.getIntIdPersona();
      log.info("intIdPersona: " + intIdPersona);
      //listarComunicacion(intIdPersona);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  removeComunicacion()           					*/
	/*                                                    	 		*/
	/*  Parametros. :  Integer intIdPersona               	 		*/
	/*                 intIdPersona = el Id de la persona a la cual */ 
	/*                 se le agregará un nuevo Comunicacion         */
	/*                                                    	 		*/
	/*  Objetivo: Grabar un Nuevo Comunicacion de acuerdo al Id de la Persona */
	/*  Retorno : Nuevo Comunicacion generado correctamente	 		*/
	/**************************************************************/
	public void removeComunicacion(ActionEvent event){
		log.info("-------------------Debugging ComunicacionController.removeComunicacion-----------------------");
		setPopupService(comunicacionService);
	    log.info("Se ha seteado el Service");
	    ArrayList arrayCom = new ArrayList();
	    ArrayList arrTemp = new ArrayList();
	    for(int i=0; i<getBeanListComunicacion().size(); i++){
	    	Comunicacion com = new Comunicacion();
	    	com = (Comunicacion) getBeanListComunicacion().get(i);
	    	if(com.getChkCom() == false){
	    		arrayCom.add(com);
	    	}
	    }
	    setBeanListComunicacion(arrayCom);
	    
	}
	
	public void enableDisableControls(ActionEvent event) throws DaoException {
		log.info("----------------Debugging ComunicacionController.enableDisableControls-------------------");
		setFormCasoEmerg(getChkCasoEmerg()!=true);
		setEnableDisableDescComunicacion(getChkDescripcion()!=true);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  viewComunicacion()           						*/
	/*                                                    	 		*/
	/*  Parametros. :  event				               	 		*/
	/*                                                    	 		*/
	/*  Objetivo: Visualizar los datos del listado de Comunicaciones*/
	/*  Retorno : Se visualizan todos los datos de la Comunicación	*/
	/*            seleccionada.                            	 		*/
	/**************************************************************/
	public void viewComunicacion(ActionEvent event){
		log.info("------------------Debugging ComunicacionController.viewComunicacion-------------------");
	    setPopupService(comunicacionService);
	    log.info("Se ha seteado el Service");
	    String pIntIdPersona = getRequestParameter("pIntIdPersona");
	    String pIntIdComunicacion = getRequestParameter("pIntIdComunicacion");
	    
	    log.info("Seteando los parametros de busqueda de dirección: ");
	    HashMap prmtBusqComunicacion = new HashMap();
		log.info("pIntIdpersona = "+ pIntIdPersona);
		prmtBusqComunicacion.put("pIntIdPersona", 		Integer.parseInt(pIntIdPersona));
		prmtBusqComunicacion.put("pIntIdComunicacion",  Integer.parseInt(pIntIdComunicacion));
		
	    ArrayList arrayComunicacion = new ArrayList();
	    ArrayList listaComunicacion = new ArrayList();
	    
    	try {
	    	arrayComunicacion = getPopupService().listarComunicacion(prmtBusqComunicacion);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarComunicacion() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMessageSuccess("Listado de Comunicacions ha sido satisfactorio");
		
		for(int i=0; i<arrayComunicacion.size(); i++){
			Comunicacion com = (Comunicacion) arrayComunicacion.get(i);
			log.info("COMU_IDPERSONA_N   			= " +com.getId().getIntIdPersona());
			log.info("COMU_IDCOMUNICACION_N 		= " +com.getId().getIntIdComunicacion());
			log.info("COMU_IDTIPOCOMUNICACION_N 	= " +com.getIntTipoComunicacionCod());
			log.info("COMU_IDSUBTIPOCOMUNICACION_N 	= " +com.getIntSubTipoComunicacionCod());
			log.info("COMU_IDTIPOLINEA_N 			= " +com.getIntTipoLineaCod());
			log.info("COMU_NUMERO_N 				= " +com.getIntNumero());
			log.info("COMU_ANEXO_N 					= " +com.getIntAnexo());
			log.info("COMU_DESCRIPCION_C 			= " +com.getStrDescripcion());
			log.info("COMU_CASOEMERGENCIA_N 		= " +com.getIntCasoEmergencia());
			log.info("COMU_IDESTADO_N 				= " +com.getIntEstadoCod());
			com.setStrDato("Tipo Línea: " + (com.getIntTipoComunicacionCod() == 1? "Teléfono" : "Fax" ) + " - Nro.: " + com.getIntNumero());
			
			setBeanComunicacion(com);
		}
		setEnableDisableFormComunicacion(pIntIdComunicacion!=null);
	}
	
}