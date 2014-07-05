package pe.com.tumi.popup.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.ConstanteReporte;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.empresa.controller.EmpresaController;
import pe.com.tumi.empresa.domain.SucursalCodigo;
import pe.com.tumi.file.controller.FileUploadBean;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Ubigeo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.domain.DomicilioPK;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.popup.service.impl.DomicilioServiceImpl;

public class DomicilioController extends GenericController {
	private DomicilioServiceImpl			domicilioService;
	private Domicilio						beanDomicilio = new Domicilio();
	//private List 							beanListDomicilio = new ArrayList<Domicilio>();
	private List 							beanListDirecPerJuri = new ArrayList<Domicilio>();
	private List 							beanListDirecRepLegal = new ArrayList<Domicilio>();
	private List 							beanListDirecUsuario = new ArrayList<Domicilio>();
	private Boolean							formEnableDisableDomicilio = false; 
	private String 							strIdModalDomicilio = "pAgregarDomicilio"; 
	private String 							strIdListDomicilio = "pgListDomicilio,tbDirecciones";
	private String 							strIdBtnDireccion;
	private String 							strInsertUpdate;
	private List<Ubigeo>					listaUbigeoDepartamento = new ArrayList<Ubigeo>();
	private List<Ubigeo>					listaUbigeoProvincia = new ArrayList<Ubigeo>();
	private List<Ubigeo>					listaUbigeoDistrito = new ArrayList<Ubigeo>();
	private List<Domicilio>					listaDomicilio = new ArrayList<Domicilio>();
	private ArrayList						arrayDomicilio = new ArrayList();
	GeneralFacadeRemote 					facadeRemoteDpto;
	
	private EmpresaController			 	empresaController = null;
	
	//Getters and Setters
	public Domicilio getBeanDomicilio() {
		return beanDomicilio;
	}
	public DomicilioServiceImpl getDomicilioService() {
		return domicilioService;
	}
	public void setDomicilioService(DomicilioServiceImpl domicilioService) {
		this.domicilioService = domicilioService;
	}
	public void setBeanDomicilio(Domicilio beanDomicilio) {
		this.beanDomicilio = beanDomicilio;
	}
	public Boolean getFormEnableDisableDomicilio() {
		return formEnableDisableDomicilio;
	}
	public void setFormEnableDisableDomicilio(Boolean formEnableDisableDomicilio) {
		this.formEnableDisableDomicilio = formEnableDisableDomicilio;
	}
	public String getStrIdModalDomicilio() {
		return strIdModalDomicilio;
	}
	public void setStrIdModalDomicilio(String strIdModalDomicilio) {
		this.strIdModalDomicilio = strIdModalDomicilio;
	}
	public String getStrIdListDomicilio() {
		return strIdListDomicilio;
	}
	public void setStrIdListDomicilio(String strIdListDomicilio) {
		this.strIdListDomicilio = strIdListDomicilio;
	}
	public String getStrIdBtnDireccion() {
		return strIdBtnDireccion;
	}
	public void setStrIdBtnDireccion(String strIdBtnDireccion) {
		this.strIdBtnDireccion = strIdBtnDireccion;
	}
	public String getStrInsertUpdate() {
		return strInsertUpdate;
	}
	public void setStrInsertUpdate(String strInsertUpdate) {
		this.strInsertUpdate = strInsertUpdate;
	}
	public List getBeanListDirecPerJuri() {
		return beanListDirecPerJuri;
	}
	public void setBeanListDirecPerJuri(List beanListDirecPerJuri) {
		this.beanListDirecPerJuri = beanListDirecPerJuri;
	}
	public List getBeanListDirecRepLegal() {
		return beanListDirecRepLegal;
	}
	public void setBeanListDirecRepLegal(List beanListDirecRepLegal) {
		this.beanListDirecRepLegal = beanListDirecRepLegal;
	}
	public List getBeanListDirecUsuario() {
		return beanListDirecUsuario;
	}
	public void setBeanListDirecUsuario(List beanListDirecUsuario) {
		this.beanListDirecUsuario = beanListDirecUsuario;
	}
	public List<Ubigeo> getListaUbigeoDepartamento() {
		return listaUbigeoDepartamento;
	}
	public void setListaUbigeoDepartamento(List<Ubigeo> listaUbigeoDepartamento) {
		this.listaUbigeoDepartamento = listaUbigeoDepartamento;
	}
	public List<Ubigeo> getListaUbigeoProvincia() {
		return listaUbigeoProvincia;
	}
	public void setListaUbigeoProvincia(List<Ubigeo> listaUbigeoProvincia) {
		this.listaUbigeoProvincia = listaUbigeoProvincia;
	}
	public List<Ubigeo> getListaUbigeoDistrito() {
		return listaUbigeoDistrito;
	}
	public void setListaUbigeoDistrito(List<Ubigeo> listaUbigeoDistrito) {
		this.listaUbigeoDistrito = listaUbigeoDistrito;
	}
	public List<Domicilio> getListaDomicilio() {
		return listaDomicilio;
	}
	public void setListaDomicilio(List<Domicilio> listaDomicilio) {
		this.listaDomicilio = listaDomicilio;
	}
	public ArrayList getArrayDomicilio() {
		return arrayDomicilio;
	}
	public void setArrayDomicilio(ArrayList arrayDomicilio) {
		this.arrayDomicilio = arrayDomicilio;
	}
	// ---------------------------------------------------
	// Métodos de DomicilioController
	// ---------------------------------------------------
	public void agregarDireccion(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("--------------------Debugging DomicilioController.agregarDireccion-------------------");
		limpiarFormDomicilio();
		/*
		UIComponent uicomp = event.getComponent();
		String idcomp = uicomp.getId();
		log.info("idcomp"+idcomp);
		
		if(idcomp.equals("btnPerJuriDireccion")){
			setStrIdBtnDireccion("btnPerJuriDireccion");
		}else if(idcomp.equals("btnRepLegalDireccion")){
			setStrIdBtnDireccion("btnRepLegalDireccion");
		}else if(idcomp.equals("btnAgregarUsuarioDomicilio")){
			setStrIdBtnDireccion("btnAgregarUsuarioDomicilio");
		}*/
		listaUbigeo(event);
	}
	
	public void limpiarFormDomicilio(){
		log.info("--------------------Debugging DomicilioController.limpiarFormDomicilio-------------------");
		//limpiar bean domicilio
		Domicilio domi = new Domicilio();
		domi.setId(new DomicilioPK());
		setBeanDomicilio(domi);
	}
	
	public void limpiarArrayDomicilio(){
		log.info("--------------------Debugging DomicilioController.limpiarFormDomicilio-------------------");
		//limpiar bean domicilio
		if(arrayDomicilio!=null){
			arrayDomicilio.clear();
		}
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarDomicilio()           						*/
	/*                                                    	 		*/
	/*  Parametros. :  Integer intIdPersona               	 		*/
	/*                 intIdPersona = el Id de la persona a la cual */ 
	/*                 se listará el o los Domicilios que tenga     */
	/*                 registrados                         	 		*/
	/*  Objetivo: Grabar un Nuevo Domicilio de acuerdo al Id de la  */
	/*            Persona                                  	 		*/
	/*  Retorno : Nuevo Domicilio generado correctamente	 		*/
	/**************************************************************/
	public void listarDomicilio(Integer intIdPersona){
		log.info("-----------------------Debugging CreditoController.listarDescuento-----------------------------");
		ContactoFacadeRemote facade = null;
		try {
			facade = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			Domicilio o = new Domicilio();
			o.setId(new DomicilioPK());
			o.getId().setIntIdPersona(intIdPersona);
			listaDomicilio = facade.getListaDomicilio(intIdPersona);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  grabarDomicilio()           						*/
	/*                                                    	 		*/
	/*  Parametros. :  Integer intIdPersona               	 		*/
	/*                 intIdPersona = el Id de la persona a la cual */ 
	/*                 se le agregará un nuevo domicilio            */
	/*                                                    	 		*/
	/*  Objetivo: Grabar un Nuevo Domicilio de acuerdo al Id de la Persona */
	/*  Retorno : Nuevo Domicilio generado correctamente	 		*/
	/**************************************************************/
	
	public void grabarDomicilio(ActionEvent event){
		log.info("--------------DomicilioController.grabarDomicilio-------------");
		empresaController = (EmpresaController) getSpringBean("empresaController");
		Domicilio domicilio = new Domicilio();
		domicilio.setId(new DomicilioPK());
		
		String strCroquisDomicilio	= FileUploadBean.getStrCroquisDomicilio();
		log.info("strCroquisDomicilio: "+ strCroquisDomicilio);
		
		if(empresaController.getBeanSucursal().getIntPersPersonaPk()!=null){
			domicilio.getId().setIntIdPersona(empresaController.getBeanSucursal().getIntPersPersonaPk());
		}
		
		domicilio.setIntTipoDomicilioCod(beanDomicilio.getIntTipoDomicilioCod());
		domicilio.setIntTipoDireccionCod(beanDomicilio.getIntTipoDireccionCod());
		domicilio.setIntTipoViviendaCod(beanDomicilio.getIntTipoViviendaCod());
		if(beanDomicilio.getIntParaUbigeoPkDpto()!=0 && beanDomicilio.getIntParaUbigeoPkProvincia()==0 && beanDomicilio.getIntParaUbigeoPkDistrito()==0)
			domicilio.setIntParaUbigeoPk(beanDomicilio.getIntParaUbigeoPkDpto());
		else if(beanDomicilio.getIntParaUbigeoPkDpto()!=0 && beanDomicilio.getIntParaUbigeoPkProvincia()!=0 && beanDomicilio.getIntParaUbigeoPkDistrito()==0)
			domicilio.setIntParaUbigeoPk(beanDomicilio.getIntParaUbigeoPkProvincia());
		else if(beanDomicilio.getIntParaUbigeoPkDpto()!=0 && beanDomicilio.getIntParaUbigeoPkProvincia()!=0 && beanDomicilio.getIntParaUbigeoPkDistrito()!=0)
			domicilio.setIntParaUbigeoPk(beanDomicilio.getIntParaUbigeoPkDistrito());
		else
			domicilio.setIntParaUbigeoPk(null);
		domicilio.setIntTipoViaCod(beanDomicilio.getIntTipoViaCod());
		domicilio.setStrNombreVia(beanDomicilio.getStrNombreVia());
		domicilio.setIntNumeroVia(beanDomicilio.getIntNumeroVia());
		domicilio.setStrInterior(beanDomicilio.getStrInterior());
		domicilio.setIntTipoZonaCod(beanDomicilio.getIntTipoZonaCod());
		domicilio.setStrReferencia(beanDomicilio.getStrReferencia());
		//domicilio.setStrRutaCroquis(beanDomicilio.getStrRutaCroquis());
		domicilio.setIntEnviaCorrespondencia(beanDomicilio.getFgCorrespondencia()==true?1:0);
		domicilio.setStrObservacion(beanDomicilio.getStrObservacion());
		domicilio.setStrNombreZona(beanDomicilio.getStrNombreZona());
		
		//String strFoto = (domicilio.getStrRutaCroquis()==null||domicilio.getStrRutaCroquis().equals("")?"":domicilio.getStrRutaCroquis().replace(ConstanteReporte.RUTA_FOTOS, ""));
		/*domicilio.setStrRutaCroquis(strCroquisDomicilio==null||strCroquisDomicilio.equals("")?"":strCroquisDomicilio);
		String direccion = (beanDomicilio.getStrNombreVia()+ " Nº "+ beanDomicilio.getIntNumeroVia() + " "+ 
				beanDomicilio.getStrNombreZona()+" / Referencia: "+ beanDomicilio.getStrReferencia());
		domicilio.setStrDireccion(direccion);
		//listaDomicilioTemp.add(domicilio);
		
		listaDomicilio.add(domicilio);
		setListaDomicilio(listaDomicilio);
		log.info("listaDomicilio.size(): " + listaDomicilio.size());
		*/
	}
	
	/*public void grabarDomicilio(ActionEvent event){
	  log.info("-----------------------Debugging DomicilioController.grabarDomicilio-------------------------");
	  setPopupService(domicilioService);
      log.info("Se ha seteado el Service");
      Domicilio dom = new Domicilio();
      dom = (Domicilio) getBeanDomicilio();
      
      String direccion = (dom.getStrNombreVia()+ " Nº "+ dom.getIntNumeroVia() + " "+ 
    		  			dom.getStrNombreZona()+" / Referencia: "+ dom.getStrReferencia());
      dom.setStrDireccion(direccion);
      
      ArrayList arrDom = new ArrayList();
      ArrayList listaDomicilio = new ArrayList();
      
      log.info("strIdBtnDireccion: "+getStrIdBtnDireccion());
      if(getStrIdBtnDireccion()!=null && getStrIdBtnDireccion().equals("btnPerJuriDireccion")){
    	  if(getBeanListDirecPerJuri()!=null){
        	  arrDom = (ArrayList) getBeanListDirecPerJuri();
          }
    	  arrDom.add(dom);
    	  
    	  setBeanListDirecPerJuri(arrDom);
      }else if(getStrIdBtnDireccion()!=null && getStrIdBtnDireccion().equals("btnRepLegalDireccion")){
    	  if(getBeanListDirecRepLegal()!=null){
        	  arrDom = (ArrayList) getBeanListDirecRepLegal();
          }
    	  arrDom.add(dom);
    	  
    	  setBeanListDirecRepLegal(arrDom);
	  }
      arrayDomicilio.add(dom);
      log.info("getBeanListDomicilio(): "+getBeanListDomicilio());
      log.info("getStrInsertUpdate(): 	"+getStrInsertUpdate());
      if(getBeanListDomicilio()!=null && getStrInsertUpdate().equals("I") && getBeanListDomicilio().size()==0){
    	  for(int i=0; i<getBeanListDomicilio().size(); i++){
        	  Domicilio domi = (Domicilio) getBeanListDomicilio().get(i);
        	  listaDomicilio.add(domi);
          }
      }
      
      if(getBeanListDomicilio()!=null && getStrInsertUpdate().equals("U")){
    	  for(int i=0; i<getBeanListDomicilio().size(); i++){
        	  Domicilio domi = (Domicilio) getBeanListDomicilio().get(i);
        	  listaDomicilio.add(domi);
          }
      }
      
      for(int i=0;i<arrayDomicilio.size(); i++){
    	  Domicilio domi = (Domicilio) arrayDomicilio.get(i);
    	  listaDomicilio.add(domi);
      }
      
      setBeanListDomicilio(listaDomicilio);
      log.info("BeneficiarioController.getIntIdPersona(): " + BeneficiarioController.getIntIdPersona());
      Integer intIdPersona = BeneficiarioController.getIntIdPersona();
      log.info("intIdPersona: " + intIdPersona);
	}*/
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  removeDomicilio()           						*/
	/*                                                    	 		*/
	/*  Parametros. :  Integer intIdPersona               	 		*/
	/*                 intIdPersona = el Id de la persona a la cual */ 
	/*                 se le agregará un nuevo domicilio            */
	/*                                                    	 		*/
	/*  Objetivo: Grabar un Nuevo Domicilio de acuerdo al Id de la Persona */
	/*  Retorno : Nuevo Domicilio generado correctamente	 		*/
	/**************************************************************/
	public void removeDomicilio(ActionEvent event){
		log.info("-----------------------Debugging DomicilioController.removeDireccion-----------------------------");
		List<Domicilio> arrayDom = new ArrayList();
	    for(int i=0; i<getListaDomicilio().size(); i++){
	    	Domicilio dom = (Domicilio) getListaDomicilio().get(i);
	    	if(dom.getChkDir() == false){
	    		arrayDom.add(dom);
	    	}
	    }
	    setListaDomicilio(arrayDom);
	}
	
	public void irViewDomicilio(ActionEvent event){
    	log.info("-----------------------Debugging DomicilioController.irViewDomicilio-----------------------------");
    	ContactoFacadeRemote contactoFacadeRemote = null;
    	String pIntIdPersona	= getRequestParameter("pIntIdPersona");
    	String pIntIdDomicilio 	= getRequestParameter("pIntIdDomicilio");
    	
		Domicilio domicilio = null;
    	
    	try {
    		if(pIntIdDomicilio != null && !pIntIdDomicilio.trim().equals("")){
    			domicilio = new Domicilio();
    			domicilio.setId(new DomicilioPK());
    			domicilio.getId().setIntIdPersona(new Integer(pIntIdPersona));
    			domicilio.getId().setIntIdDomicilio(new Integer(pIntIdDomicilio));
    			
    			contactoFacadeRemote = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
				beanDomicilio = contactoFacadeRemote.getDomicilioPorPK(domicilio.getId());
				//beanDomicilio.setStrRutaCroquis(ConstanteReporte.RUTA_FOTOS + beanDomicilio.getStrRutaCroquis());
    		}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void eliminarDomicilio(ActionEvent event){
    	log.info("-----------------------Debugging DomicilioController.deleteDomicilio-----------------------------");
    	ContactoFacadeRemote contactoFacadeRemote = null;
    	String pIntIdPersona	= getRequestParameter("pIntIdPersona");
    	String pIntIdDomicilio 	= getRequestParameter("pIntIdDomicilio");
    	
		Domicilio domicilio = null;
    	
    	try {
    		if(pIntIdDomicilio != null && !pIntIdDomicilio.trim().equals("")){
    			domicilio = new Domicilio();
    			domicilio.setId(new DomicilioPK());
    			domicilio.getId().setIntIdPersona(new Integer(pIntIdPersona));
    			domicilio.getId().setIntIdDomicilio(new Integer(pIntIdDomicilio));
    			
    			contactoFacadeRemote = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
				beanDomicilio = contactoFacadeRemote.eliminarDomicilio(domicilio);
				listarDomicilio(new Integer(pIntIdPersona));
    		}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void listaUbigeo(ActionEvent event) throws EJBFactoryException, BusinessException{
		log.info("--------------------DomicilioController.listaUbigeo------------------------");
		listaUbigeoDepartamento = null;
		GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoDepartamento = remote.getListaUbigeoDeDepartamento();
	}
	
	public void reloadCboProvincia(ValueChangeEvent event) throws DaoException, EJBFactoryException, BusinessException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboProvincia()-----------------------------");
		Integer intIdDpto = Integer.parseInt(""+event.getNewValue());
		log.info("intIdEmpresa = "+intIdDpto);
		GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoProvincia = remote.getListaUbigeoDeProvinciaPorIdUbigeo(intIdDpto);
		
	}
	
	public void reloadCboDistrito(ValueChangeEvent event) throws DaoException, EJBFactoryException, BusinessException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboDistrito()-----------------------------");
		Integer intIdProvincia = Integer.parseInt(""+event.getNewValue());
		log.info("intIdEmpresa = "+intIdProvincia);
		GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoDistrito = remote.getListaUbigeoDeDistritoPorIdUbigeo(intIdProvincia);
		
	}
	
}