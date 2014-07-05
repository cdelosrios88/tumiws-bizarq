package pe.com.tumi.credito.popup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DownloadFile;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.common.util.MyFile;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeLocal;
import pe.com.tumi.fileupload.FileUploadController;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.facade.AuditoriaFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.Ubigeo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.domain.DomicilioPK;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class DomicilioController {
	protected   static Logger 		    	log = Logger.getLogger(DomicilioController.class);
	private 	Domicilio					beanDomicilio = null;
	private 	List<Domicilio> 			beanListDomicilio = null;
	private 	List<Domicilio> 			beanListDirecPerJuri = null;
	private 	List<Domicilio>				beanListDirecRepLegal = null;
	private 	List<Domicilio>				beanListDirecSocioNatu = null;
	private 	List<Domicilio>				listDirecSocioNatuEmp = null;
	private 	List<Domicilio>				listDirecBeneficiario = null;
	private 	List<Domicilio>				beanListDirecUsuario = null;
	private 	Boolean						formEnableDisableDomicilio = false; 
	private 	String 						strIdModalDomicilio; 
	private 	String 						strIdListDomicilio;
	private 	String 						strCallingFormId;
	private 	String 						strFormIdJuriConve;
	private 	String 						strFormIdRepLegal;
	private 	String 						strFormIdSocioNatu;
	private 	String 						strFormIdSocioNatuEmp;
	private 	String 						strFormIdBeneficiario;
	private 	String 						strInsertUpdate;
	private 	List<Ubigeo>				listaUbigeoDepartamento = new ArrayList<Ubigeo>();
	private 	List<Ubigeo>				listaUbigeoProvincia = new ArrayList<Ubigeo>();
	private 	List<Ubigeo>				listaUbigeoDistrito = new ArrayList<Ubigeo>();
	private 	ArrayList<Domicilio>		arrayDomicilio = new ArrayList<Domicilio>();
	private 	MyFile						fileCroquis;
	private 	Archivo						archivoCroquis;
	/* Inicio - AuditoriaFacade - rVillarreal 23.jun.2014 */
	private AuditoriaFacadeRemote 		auditoriaFacade;
	/* Fin - AuditoriaFacade - rVillarreal 23.jun.2014 */
	//atributos de sesión
	private 	Integer 				SESION_IDEMPRESA;
	private 	Integer 				SESION_IDUSUARIO;
	
	public DomicilioController() {
		strIdModalDomicilio = "pAgregarDomicilio"; 
		strIdListDomicilio = "divFormSocioNatu,pgListDomicilio,tbDirecciones,divDomicilioRepLegal,tbDomicilioSocioNatu,pBeneficiario";
		
		strFormIdJuriConve = "frmPerJuriConve";
		strFormIdRepLegal = "frmRepLegal";
		strFormIdSocioNatu = "frmSocioNatural";
		strFormIdSocioNatuEmp = "frmSocioEmpresa";
		strFormIdBeneficiario = "frmBeneficiario";
		
		beanDomicilio = new Domicilio();
		beanDomicilio.setId(new DomicilioPK());
		beanListDomicilio = new ArrayList<Domicilio>();
		beanListDirecPerJuri = new ArrayList<Domicilio>();
		beanListDirecRepLegal = new ArrayList<Domicilio>();
		beanListDirecUsuario = new ArrayList<Domicilio>();
		
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		
		try {
			/* Inicio - Inicializar AuditoriaFacade - rVillarreal 23.jun.2014*/
			auditoriaFacade = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
			/* Fin - Inicializar AuditoriaFacade - rVillarreal 23.jun.2014*/
		} catch (EJBFactoryException e) {
			log.error("error: " + e);
		}
		
		//Agregado 23-01-2013
		try {
			listaUbigeo(null);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
		seleccionarDepartamento();
		seleccionarProvincia();
	}

	// ---------------------------------------------------
	// Métodos de DomicilioController
	// ---------------------------------------------------
	public void showDomicilioJuriConve(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("--------------------Debugging DomicilioController.showDomicilioJuriConve-------------------");
		limpiarFormDomicilio();
		setStrCallingFormId(strFormIdJuriConve);
		listaUbigeo(event);
	}
	
	public void showDomicilioRepLegal(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("--------------------Debugging DomicilioController.showDomicilioRepLegal-------------------");
		limpiarFormDomicilio();
		setStrCallingFormId(strFormIdRepLegal);
		listaUbigeo(event);
	}
	
	public void showDomicilioSocioNatu(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("--------------------Debugging DomicilioController.showDomicilioSocioNatu-------------------");
		limpiarFormDomicilio();
		setStrCallingFormId(strFormIdSocioNatu);
		listaUbigeo(event);
	}
	
	public void showDomicilioSocioNatuEmp(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("--------------------Debugging DomicilioController.showDomicilioSocioNatuEmp-------------------");
		limpiarFormDomicilio();
		setStrCallingFormId(strFormIdSocioNatuEmp);
		listaUbigeo(event);
	}
	
	public void showDomicilioBeneficiario(ActionEvent event) throws BusinessException, EJBFactoryException{
		log.info("--------------------Debugging DomicilioController.showDomicilioBeneficiario-------------------");
		limpiarFormDomicilio();
		setStrCallingFormId(strFormIdBeneficiario);
		listaUbigeo(event);
	}
	
	public void limpiarFormDomicilio(){
		log.info("--------------------Debugging DomicilioController.limpiarFormDomicilio-------------------");
		//limpiar bean domicilio
		Domicilio domi = new Domicilio();
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
	public void listarDomicilio(Integer intIdPersona, Integer intIdDomicilio){
		log.info("-----------------------Debugging DomicilioController.listarDomicilio-----------------------------");
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de dirección: ");
		log.info("--------------------------------------------------");
		log.info("pIntIdpersona = "+ intIdPersona);
		
		List<Domicilio> listaDomicilio = null;
		try {
			ContactoFacadeRemote contactoFacade = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
    		listaDomicilio = contactoFacade.getListaDomicilio(intIdPersona);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		if(listaDomicilio!=null){
			log.info("listaDomicilio.size: "+listaDomicilio.size());
			for(int i=0; i<listaDomicilio.size(); i++){
				Domicilio dom = (Domicilio) arrayDomicilio.get(i);
				log.info("PERS_IDPERSONA_N   = " +dom.getId().getIntIdPersona());
				log.info("DOMI_IDDOMICILIO_N = " +dom.getId().getIntIdDomicilio());
				log.info("V_DIRECCION  		 = " +dom.getStrDireccion());
				log.info("DOMI_OBSERVACION_C = " +dom.getStrObservacion());
			}
		}
		setMessageSuccess("Listado de domicilios ha sido satisfactorio");
		
		setBeanListDomicilio(listaDomicilio);
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
	  log.info("-----------------------Debugging DomicilioController.grabarDomicilio-------------------------");
      Domicilio dom = new Domicilio();
      dom = (Domicilio) getBeanDomicilio();
      
      String direccion = (dom.getStrNombreVia()+ " Nº "+ dom.getIntNumeroVia() + " "+ 
    		  			dom.getStrNombreZona()+" / Referencia: "+ dom.getStrReferencia());
      log.info("dom.getIntParaUbigeoPk(): "+ dom.getIntParaUbigeoPk());
      log.info("dom.getIntParaUbigeoPkDpto(): "+ dom.getIntParaUbigeoPkDpto());
      log.info("dom.getIntParaUbigeoPkProvincia(): "+ dom.getIntParaUbigeoPkProvincia());
      log.info("dom.getIntParaUbigeoPkDistrito(): "+ dom.getIntParaUbigeoPkDistrito());
      dom.setStrDireccion(direccion);
      
      ArrayList<Domicilio> arrDom = new ArrayList<Domicilio>();
      
      log.info("strCallingFormId: "+getStrCallingFormId());
      if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdJuriConve)){
    	  if(getBeanListDirecPerJuri()!=null){
        	  arrDom = (ArrayList<Domicilio>) getBeanListDirecPerJuri();
          }
    	  arrDom.add(dom);
    	  
    	  setBeanListDirecPerJuri(arrDom);
      }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdRepLegal)){
    	  if(getBeanListDirecRepLegal()!=null){
        	  arrDom = (ArrayList<Domicilio>) getBeanListDirecRepLegal();
          }
    	  arrDom.add(dom);
    	  
    	  setBeanListDirecRepLegal(arrDom);
	  }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdSocioNatu)){
    	  if(getBeanListDirecSocioNatu()!=null){
    		  log.info("beanListDirecSocioNatu.size: "+getBeanListDirecSocioNatu().size());
        	  arrDom = (ArrayList<Domicilio>) getBeanListDirecSocioNatu();
          }
    	  arrDom.add(dom);
    	  
    	  setBeanListDirecSocioNatu(arrDom);
	  }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdSocioNatuEmp)){
    	  if(getListDirecSocioNatuEmp()!=null){
        	  arrDom = (ArrayList<Domicilio>) getListDirecSocioNatuEmp();
          }
    	  arrDom.add(dom);
    	  
    	  setListDirecSocioNatuEmp(arrDom);
	  }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdBeneficiario)){
    	  if(getListDirecBeneficiario()!=null){
        	  arrDom = (ArrayList<Domicilio>) getListDirecBeneficiario();
          }
    	  arrDom.add(dom);
    	  
    	  setListDirecBeneficiario(arrDom);
	  }
      
      //

//		SocioFacadeLocal facade = null;
//		try {
//			facade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
//		List<Auditoria> listaAuditoriaDomicilio = new ArrayList<Auditoria>();
//		for(Domicilio audiDomi : arrDom){
//			if(audiDomi.getId().getIntIdPersona() != null) {
//				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
//				listaAuditoriaDomicilio = generarAuditoriaDomicilio(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, arrDom);
//			} 
//			for(Auditoria auditoriaDomicilio : listaAuditoriaDomicilio) {
//				grabarAuditoria(auditoriaDomicilio);
//			}
//		}
//	}catch (EJBFactoryException e) {
//		e.printStackTrace();
//	}catch (BusinessException e) {
//		e.printStackTrace();
//	}
      
      
      
	}
	
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
	    ArrayList<Domicilio> arrayDom = new ArrayList<Domicilio>();
	    for(int i=0; i<getBeanListDomicilio().size(); i++){
	    	Domicilio dom = (Domicilio) getBeanListDomicilio().get(i);
	    	if(dom.getChkDir() == false){
	    		arrayDom.add(dom);
	    	}
	    }
	    setBeanListDomicilio(arrayDom);
	}
	
	public void viewDomicilioJuridica(ActionEvent event){
		log.info("------------------Debugging DomicilioController.viewDomicilioJuridica-------------------");
	    log.info("Se ha seteado el Service");
	    String rowKey = getRequestParameter("rowKeyDomicilioJuridica");
		log.info("rowKeyDomicilioJuridica = "+ rowKey);
		
		viewDomicilio(beanListDirecPerJuri, rowKey);
	}
	
	public void viewDomicilioNatural(ActionEvent event){
		log.info("------------------Debugging DomicilioController.viewDomicilioNatural-------------------");
	    String rowKey = getRequestParameter("rowKeyDomicilioRepLegal");
		log.info("rowKeyDomicilioNatural = "+ rowKey);
		
		viewDomicilio(beanListDirecRepLegal, rowKey);
	}
	
	public void viewDomicilioSocioNatu(ActionEvent event){
		log.info("------------------Debugging DomicilioController.viewDomicilioSocioNatu-------------------");
	    String rowKey = getRequestParameter("rowKeyDomicilioSocioNatu");
		log.info("rowKeyDomicilioSocioNatu = "+ rowKey);
		
		viewDomicilio(beanListDirecSocioNatu, rowKey);
	}
	
	public void viewDomiSocioNatuEmp(ActionEvent event){
		log.info("------------------Debugging DomicilioController.viewDomicilioSocioNatuEmp-------------------");
	    String rowKey = getRequestParameter("rowKeyDomiSocioNatuEmp");
		log.info("rowKeyDomiSocioNatuEmp = "+ rowKey);
		
		viewDomicilio(listDirecSocioNatuEmp, rowKey);
	}
	
	public void viewDomiBeneficiario(ActionEvent event){
		log.info("------------------Debugging DomicilioController.viewDomicilioBeneficiario-------------------");
	    String rowKey = getRequestParameter("rowKeyDomicilioBeneficiario");
		log.info("rowKeyDomicilioBeneficiario = "+ rowKey);
		
		viewDomicilio(listDirecBeneficiario, rowKey);
	}
	
	public void viewDomicilio(List<Domicilio> listDomicilio, String rowKey){
		log.info("------------------Debugging DomicilioController.viewDomicilio-------------------");
	    Domicilio domicilio = null;
		
	    if(listDomicilio!=null){
	    	for(int i=0; i<listDomicilio.size(); i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					domicilio = listDomicilio.get(i);
				}
			}
	    }
		
		setBeanDomicilio(domicilio);
		
		try {
			listaUbigeo(null);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
		log.info("dep:"+domicilio.getIntParaUbigeoPkDpto());
		log.info("pro:"+domicilio.getIntParaUbigeoPkProvincia());
		log.info("dis:"+domicilio.getIntParaUbigeoPkDistrito());
		
		seleccionarDepartamento();
		seleccionarProvincia();
		//setFormEnableDisableDomicilio(true);
	}
	
	public void seleccionarDepartamento(){
		try{
			//log.info("--seleccionarDepartamento");
			GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
		    listaUbigeoProvincia = remote.getListaUbigeoDeProvinciaPorIdUbigeo(beanDomicilio.getIntParaUbigeoPkDpto());
			listaUbigeoDistrito = new ArrayList<Ubigeo>();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarProvincia(){
		try{
			//log.info("--seleccionarProvincia");
			GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
		    listaUbigeoDistrito = remote.getListaUbigeoDeDistritoPorIdUbigeo(beanDomicilio.getIntParaUbigeoPkProvincia());
		   /* for(Ubigeo ub : listaUbigeoDistrito){
		    	log.info(ub.getStrDescripcion()+" "+ub.getIntIdUbigeo());
		    }*/
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void quitarDomicilioJuridica(ActionEvent event){
		log.info("-------------------------------------Debugging quitarDomicilioJuridica-------------------------------------");
		String rowKey = getRequestParameter("rowKeyDomicilioJuridica");
		log.info("rowKeyDomicilioJuridica: "+rowKey);
		quitarDomicilio(beanListDirecPerJuri, rowKey);
	}
	
	public void quitarDomicilioRepLegal(ActionEvent event){
		log.info("-------------------------------------Debugging quitarDomicilioRepLegal-------------------------------------");
		String rowKey = getRequestParameter("rowKeyDomicilioRepLegal");
		log.info("rowKeyDomicilioRepLegal: "+rowKey);
		quitarDomicilio(beanListDirecRepLegal, rowKey);
	}
	
	public void quitarDomicilioSocioNatu(ActionEvent event){
		log.info("-------------------------------------Debugging DomicilioController.quitarDomicilioSocioNatu-------------------------------------");
		String rowKey = getRequestParameter("rowKeyDomicilioSocioNatu");
		log.info("rowKeyDomicilioSocioNatu: "+rowKey);
		quitarDomicilio(beanListDirecSocioNatu, rowKey);
	}
	
	public void quitarDomiSocioNatuEmp(ActionEvent event){
		log.info("-------------------------------------Debugging DomicilioController.quitarDomiSocioNatuEmp-------------------------------------");
		String rowKey = getRequestParameter("rowKeyDomiSocioNatuEmp");
		log.info("rowKeyDomiSocioNatuEmp: "+rowKey);
		quitarDomicilio(listDirecSocioNatuEmp, rowKey);
	}
	
	public void quitarDomiBeneficiario(ActionEvent event){
		log.info("-------------------------------------Debugging DomicilioController.quitarDomiBeneficiario-------------------------------------");
		String rowKey = getRequestParameter("rowKeyDomicilioBeneficiario");
		log.info("rowKeyDomicilioBeneficiario: "+rowKey);
		quitarDomicilio(listDirecBeneficiario, rowKey);
	}
	
	public void quitarDomicilio(List<Domicilio> listDomicilio, String rowKey){
		log.info("-------------------------------------Debugging DomicilioController.quitarDomicilio-------------------------------------");
		Domicilio domiTmp = null;
		if(listDomicilio!=null){
			for(int i=0; i<listDomicilio.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Domicilio domicilio = listDomicilio.get(i);
					log.info("domicilio.id: "+domicilio.getId());
					if(domicilio.getId()!=null && domicilio.getId().getIntIdDomicilio()!=null){
						domiTmp = listDomicilio.get(i);
						domiTmp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listDomicilio.remove(i);
					break;
				}
			}
			if(domiTmp!=null && domiTmp.getId().getIntIdDomicilio()!=null){
				listDomicilio.add(domiTmp);
			}
		}
	}
	
	public void listaUbigeo(ActionEvent event) throws EJBFactoryException, BusinessException{
		log.info("--------------------DomicilioController.listaUbigeo------------------------");
		listaUbigeoDepartamento = null;
		GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoDepartamento = remote.getListaUbigeoDeDepartamento();
	    log.info("listaUbigeoDepartamento.size(): " + listaUbigeoDepartamento.size());
	    //listaUbigeoProvincia = remote.getListaUbigeoDeProvinciaPorIdUbigeo();
	}
	
	public void reloadCboProvincia(ValueChangeEvent event) throws EJBFactoryException, BusinessException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboProvincia()-----------------------------");
		Integer intIdDpto = Integer.parseInt(""+event.getNewValue());
		log.info("intIdDpto = "+intIdDpto);
		GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoProvincia = remote.getListaUbigeoDeProvinciaPorIdUbigeo(intIdDpto);
		
	}
	
	public void reloadCboDistrito(ValueChangeEvent event) throws EJBFactoryException, BusinessException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboDistrito()-----------------------------");
		Integer intIdProvincia = Integer.parseInt(""+event.getNewValue());
		log.info("intIdProvincia = "+intIdProvincia);
		GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoDistrito = remote.getListaUbigeoDeDistritoPorIdUbigeo(intIdProvincia);
	}
	
	public void setCboDistrito(ValueChangeEvent event) throws EJBFactoryException, BusinessException {
		Integer intIdDistrito = Integer.parseInt("" + event.getNewValue());
		beanDomicilio.setIntParaUbigeoPkDistrito(intIdDistrito);
	}
	
	public void adjuntarCroquis(ActionEvent event){
		log.info("-------------------------------------Debugging DomicilioController.adjuntarCroquis-------------------------------------");
		FileUploadController fileupload = (FileUploadController)getSessionBean("fileUploadController");
		fileupload.setStrDescripcion("Seleccione el contrato correspondiente al socio.");
		fileupload.setFileType(FileUtil.allDocumentTypes);
		
		//Si ya ha grabado el contrato(Archivo) anteriormente
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;
		if(beanDomicilio.getCroquis()!=null &&
				beanDomicilio.getCroquis().getId()!=null){
			intItemArchivo = beanDomicilio.getCroquis().getId().getIntItemArchivo();
			intItemHistorico = beanDomicilio.getCroquis().getId().getIntItemHistorico();
		}
		fileupload.setParamArchivo(intItemArchivo, intItemHistorico, Constante.PARAM_T_TIPOARCHIVOADJUNTO_CROQUIS);
		fileupload.setStrJsFunction("putFileDomicilio()");
	}
	
	public void putFile(ActionEvent event) throws BusinessException, EJBFactoryException, IOException{
		log.info("-------------------------------------Debugging BeneficiarioController.putFile-------------------------------------");
		FileUploadController fileupload = (FileUploadController)getSessionBean("fileUploadController");
		log.info("fileupload: " + fileupload);
		if(fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CROQUIS)){
			log.info("fileupload.getObjArchivo().getStrNombrearchivo(): " + fileupload.getObjArchivo().getStrNombrearchivo());
			setArchivoCroquis(fileupload.getObjArchivo());
			beanDomicilio.setCroquis(fileupload.getObjArchivo());
		}
	}
	
	public void paintImage(OutputStream stream, Object object) throws IOException {
        stream.write(((MyFile)object).getData());
    }
	
	public void descargarCroquis(){
		log.info("-----------------DomicilioController.descargarArchivo---------------");
		String rutaActual = getRequestParameter("strRutaActual");
		log.info("rutaActual: "+ rutaActual);
		try {
			DownloadFile.downloadFile("C:\\tumi\\ArchivosAdjuntos\\Imagenes\\Personas\\Croquis\\"+rutaActual);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verCroquis(ActionEvent event) throws IOException{
		log.info("-------------------------------------Debugging SocioController.verContratoPerNatu-------------------------------------");
		Domicilio domicilio = (Domicilio)event.getComponent().getAttributes().get("item");
		//Mostrar la Foto del Socio
		if(domicilio!=null && domicilio.getCroquis()!=null){
			Archivo croquis = domicilio.getCroquis();
			log.info("Path Croquis: "+croquis.getTipoarchivo().getStrRuta()+"\\"+croquis.getStrNombrearchivo());
			File file = new File(croquis.getTipoarchivo().getStrRuta()+"\\"+croquis.getStrNombrearchivo());
			log.info("file.exists(): "+file.exists());
	        byte[] bytes = getBytesFromFile(file);
	        
	        FacesContext faces = FacesContext.getCurrentInstance();
	        HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
	        getResponse().setContentType("application/jpg");
	        getResponse().setContentLength(bytes.length);
	        getResponse().setHeader( "Content-disposition", "inline; filename=\""+file.getName()+"\"");
	        System.out.println("file.getName(): "+file.getName());
	        try {
		        ServletOutputStream out;
		        out = response.getOutputStream();
		        out.write(bytes);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	        faces.responseComplete();
		}
	}
	
	public static byte[] getBytesFromFile(File file) throws IOException {        
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            throw new IOException("File is too large!");
        }

        byte[] bytes = new byte[(int)length];
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        return bytes;
    }
	
	public List<Auditoria> generarAuditoriaDomicilio(Integer intTipoCambio, ArrayList<Domicilio> arrDom) throws BusinessException{
		log.info("Inicio");
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			// Inserción de Nuevos Registros
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_INSERT)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
				/* Inicio Tabla PER_DOMICILIO */
//				for(Domicilio audiDomPrim : arrDom){
//				if (audiDomPrim.getId().getIntIdPersona() != null)
					for(Domicilio audiDomicilio : arrDom) {
						// PERS_PERSONA_N
						if(audiDomicilio.getId().getIntIdPersona() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PERS_PERSONA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getId().getIntIdPersona());

							lista.add(auditoria);
						}
						// PERS_DOMICILIO_N
						if(audiDomicilio.getId().getIntIdDomicilio() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PERS_DOMICILIO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getId().getIntIdDomicilio());

							lista.add(auditoria);
						}
						// DOMI_TIPODOMICILIO_N_COD
						if(audiDomicilio.getIntTipoDomicilioCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPODOMICILIO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntTipoDomicilioCod());

							lista.add(auditoria);
						}
						// DOMI_TIPODIRECCION_N_COD
						if(audiDomicilio.getIntTipoDireccionCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPODIRECCION_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntTipoDireccionCod());

							lista.add(auditoria);
						}
						// DOMI_TIPOVIVIENDA_N_COD
						if(audiDomicilio.getIntTipoViviendaCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPOVIVIENDA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntTipoViviendaCod());

							lista.add(auditoria);
						}
						// PARA_UBIGEO_N_PK
						if(audiDomicilio.getIntParaUbigeoPkDistrito() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_UBIGEO_N_PK);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntParaUbigeoPkDistrito());

							lista.add(auditoria);
						}
						// DOMI_TIPOVIA_N_COD
						if(audiDomicilio.getIntTipoViaCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPOVIA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntTipoViaCod());

							lista.add(auditoria);
						}
						// DOMI_NOMBREVIA_V
						if(audiDomicilio.getStrNombreVia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_NOMBREVIA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getStrNombreVia());

							lista.add(auditoria);
						}
						// DOMI_NUMEROVIA_N
						if(audiDomicilio.getIntNumeroVia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_NUMEROVIA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntNumeroVia());

							lista.add(auditoria);
						}
						// DOMI_INTERIOR_V
						if(audiDomicilio.getStrInterior() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_INTERIOR_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getStrInterior());

							lista.add(auditoria);
						}

						// DOMI_TIPOZONA_N_COD
						if(audiDomicilio.getIntTipoZonaCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPOZONA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntTipoZonaCod());

							lista.add(auditoria);
						}

						// DOMI_REFERENCIA_V
						if(audiDomicilio.getStrReferencia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_REFERENCIA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getStrReferencia());

							lista.add(auditoria);
						}

						// DOMI_ENVIACORRESPONDENCIA_N
						if(audiDomicilio.getIntEnviaCorrespondencia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_ENVIACORRESPONDENCIA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntEnviaCorrespondencia());

							lista.add(auditoria);
						}
						// DOMI_OBSERVACION_V
						if(audiDomicilio.getStrObservacion() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_OBSERVACION_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getStrObservacion());

							lista.add(auditoria);
						}
						// DOMI_ESTADO_N_COD
						if(audiDomicilio.getIntEstadoCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_ESTADO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntEstadoCod());

							lista.add(auditoria);
						}
						// DOMI_NOMBREZONA_V
						if(audiDomicilio.getStrNombreZona() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_NOMBREZONA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getStrNombreZona());

							lista.add(auditoria);
						}
						// DOMI_FECHAELIMINACION_D
						if(audiDomicilio.getTsFechaEliminacion() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_FECHAELIMINACION_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getTsFechaEliminacion());

							lista.add(auditoria);
						}
						// PARA_TIPOARCHIVO_N_COD
						if(audiDomicilio.getIntParaTipoArchivo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_TIPOARCHIVO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntParaTipoArchivo());

							lista.add(auditoria);
						}
						// PARA_ITEMARCHIVO_N
						if(audiDomicilio.getIntParaItemArchivo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_ITEMARCHIVO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntParaItemArchivo());

							lista.add(auditoria);
						}
						// PARA_ITEMHISTORICO_N
						if(audiDomicilio.getIntParaItemHistorico() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + audiDomicilio.getId().getIntIdPersona());
							listaLlaves.add("" + audiDomicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_ITEMHISTORICO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + audiDomicilio.getIntParaItemHistorico());

							lista.add(auditoria);
						}
						
					}
//				}
			}
		} catch (Exception e) {
			log.error("Error en generarAuditoriaPersona --> " + e);
		}
		log.info("Fin");
		return lista;
	}
	
	/* Método que graba en la tabla Auditoria */
	public Auditoria grabarAuditoria(Auditoria auditoria)throws BusinessException {
		log.info("Inicio");
		try {
			auditoriaFacade.grabarAuditoria(auditoria);
		} catch (Exception e) {
			log.error("Error al grabar en Auditoria: " + e);
		}
		log.info("Fin");
		return auditoria;
	}
	
	/* Método que carga los datos comunes de la estructura Auditoria */
	public Auditoria beanAuditoria(Integer intTipoCambio, List<String> llaves, String tabla) {
		log.info("Inicio");
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = auditoriaFacade.beanAuditoria(SESION_IDEMPRESA, SESION_IDUSUARIO, intTipoCambio, llaves, tabla);
		} catch (Exception e) {
			log.error("Error al grabar en Auditoria: " + e);
		}
		log.info("Fin");
		return auditoria;
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
	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	//Getters and Setters
	public Domicilio getBeanDomicilio() {
		return beanDomicilio;
	}
	public void setBeanDomicilio(Domicilio beanDomicilio) {
		this.beanDomicilio = beanDomicilio;
	}
	public List<Domicilio> getBeanListDomicilio() {
		return beanListDomicilio;
	}
	public void setBeanListDomicilio(List<Domicilio> beanListDomicilio) {
		this.beanListDomicilio = beanListDomicilio;
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
	public String getStrInsertUpdate() {
		return strInsertUpdate;
	}
	public void setStrInsertUpdate(String strInsertUpdate) {
		this.strInsertUpdate = strInsertUpdate;
	}
	public List<Domicilio> getBeanListDirecPerJuri() {
		return beanListDirecPerJuri;
	}
	public void setBeanListDirecPerJuri(List<Domicilio> beanListDirecPerJuri) {
		this.beanListDirecPerJuri = beanListDirecPerJuri;
	}
	public List<Domicilio> getBeanListDirecRepLegal() {
		return beanListDirecRepLegal;
	}
	public void setBeanListDirecRepLegal(List<Domicilio> beanListDirecRepLegal) {
		this.beanListDirecRepLegal = beanListDirecRepLegal;
	}
	public List<Domicilio> getBeanListDirecUsuario() {
		return beanListDirecUsuario;
	}
	public void setBeanListDirecUsuario(List<Domicilio> beanListDirecUsuario) {
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
	public ArrayList<Domicilio> getArrayDomicilio() {
		return arrayDomicilio;
	}
	public void setArrayDomicilio(ArrayList<Domicilio> arrayDomicilio) {
		this.arrayDomicilio = arrayDomicilio;
	}
	public List<Domicilio> getBeanListDirecSocioNatu() {
		return beanListDirecSocioNatu;
	}
	public void setBeanListDirecSocioNatu(List<Domicilio> beanListDirecSocioNatu) {
		this.beanListDirecSocioNatu = beanListDirecSocioNatu;
	}
	public String getStrCallingFormId() {
		return strCallingFormId;
	}
	public void setStrCallingFormId(String strCallingFormId) {
		this.strCallingFormId = strCallingFormId;
	}
	public String getStrFormIdJuriConve() {
		return strFormIdJuriConve;
	}
	public void setStrFormIdJuriConve(String strFormIdJuriConve) {
		this.strFormIdJuriConve = strFormIdJuriConve;
	}
	public String getStrFormIdRepLegal() {
		return strFormIdRepLegal;
	}
	public void setStrFormIdRepLegal(String strFormIdRepLegal) {
		this.strFormIdRepLegal = strFormIdRepLegal;
	}
	public String getStrFormIdSocioNatu() {
		return strFormIdSocioNatu;
	}
	public void setStrFormIdSocioNatu(String strFormIdSocioNatu) {
		this.strFormIdSocioNatu = strFormIdSocioNatu;
	}
	public String getStrFormIdBeneficiario() {
		return strFormIdBeneficiario;
	}
	public void setStrFormIdBeneficiario(String strFormIdBeneficiario) {
		this.strFormIdBeneficiario = strFormIdBeneficiario;
	}
	public String getStrFormIdSocioNatuEmp() {
		return strFormIdSocioNatuEmp;
	}
	public void setStrFormIdSocioNatuEmp(String strFormIdSocioNatuEmp) {
		this.strFormIdSocioNatuEmp = strFormIdSocioNatuEmp;
	}
	public List<Domicilio> getListDirecSocioNatuEmp() {
		return listDirecSocioNatuEmp;
	}
	public void setListDirecSocioNatuEmp(List<Domicilio> listDirecSocioNatuEmp) {
		this.listDirecSocioNatuEmp = listDirecSocioNatuEmp;
	}
	public List<Domicilio> getListDirecBeneficiario() {
		return listDirecBeneficiario;
	}
	public void setListDirecBeneficiario(List<Domicilio> listDirecBeneficiario) {
		this.listDirecBeneficiario = listDirecBeneficiario;
	}
	public MyFile getFileCroquis() {
		return fileCroquis;
	}
	public void setFileCroquis(MyFile fileCroquis) {
		this.fileCroquis = fileCroquis;
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	public Archivo getArchivoCroquis() {
		return archivoCroquis;
	}
	public void setArchivoCroquis(Archivo archivoCroquis) {
		this.archivoCroquis = archivoCroquis;
	}

	public Integer getSESION_IDEMPRESA() {
		return SESION_IDEMPRESA;
	}

	public void setSESION_IDEMPRESA(Integer sESION_IDEMPRESA) {
		SESION_IDEMPRESA = sESION_IDEMPRESA;
	}

	public Integer getSESION_IDUSUARIO() {
		return SESION_IDUSUARIO;
	}

	public void setSESION_IDUSUARIO(Integer sESION_IDUSUARIO) {
		SESION_IDUSUARIO = sESION_IDUSUARIO;
	}
}