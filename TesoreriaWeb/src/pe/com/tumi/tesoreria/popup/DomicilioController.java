package pe.com.tumi.tesoreria.popup;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.Ubigeo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.domain.DomicilioPK;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;

public class DomicilioController {
	protected   static Logger 		    	log = Logger.getLogger(DomicilioController.class);
	private 	Domicilio					beanDomicilio = null;
	private 	List<Domicilio> 			beanListDomicilio = null;
	private 	List<Domicilio> 			beanListDirecPerJuri = null;
	private 	List<Domicilio> 			beanListDirecProveedor = null;
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
	private 	String 						strFormIdJuriProveedor;
	private 	String 						strFormIdRepLegal;
	private 	String 						strFormIdSocioNatu;
	private 	String 						strFormIdSocioNatuEmp;
	private 	String 						strFormIdBeneficiario;
	private 	String 						strInsertUpdate;
	private 	List<Ubigeo>				listaUbigeoDepartamento = new ArrayList<Ubigeo>();
	private 	List<Ubigeo>				listaUbigeoProvincia = new ArrayList<Ubigeo>();
	private 	List<Ubigeo>				listaUbigeoDistrito = new ArrayList<Ubigeo>();
	private 	ArrayList<Domicilio>		arrayDomicilio = new ArrayList<Domicilio>();
	private		boolean						habilitarEditar;
	private		String						rowKeyPersite;
	
	
	public DomicilioController() {
		strIdModalDomicilio = "pAgregarDomicilio"; 
		strIdListDomicilio = "divFormSocioNatu,pgListDomicilio,tbDirecciones,divDomicilioRepLegal,tbDomicilioSocioNatu,pBeneficiario";
		
		strFormIdJuriConve = "frmPerJuriConve";
		strFormIdJuriProveedor = "frmPerJuriProveedor";
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
	}

	// ---------------------------------------------------
	// Métodos de DomicilioController
	// ---------------------------------------------------
	public void showDomicilioJuriConve(ActionEvent event) throws BusinessException, EJBFactoryException{
		//log.info("--------------------Debugging DomicilioController.showDomicilioJuriConve-------------------");
		limpiarFormDomicilio();
		setStrCallingFormId(strFormIdJuriConve);
		listaUbigeo(event);
	}
	
	public void showDomicilioJuriProveedor(ActionEvent event) throws BusinessException, EJBFactoryException{		
		limpiarFormDomicilio();
		setStrCallingFormId(strFormIdJuriProveedor);
		listaUbigeo(event);
		habilitarEditar = Boolean.TRUE;
		
		//**Inicio de manejo de archivos**/
		FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
	    fileUploadController.setObjArchivo(null);
	    //**Fin manejo de archivos**//
	}
	
	public void showDomicilioRepLegal(ActionEvent event) throws BusinessException, EJBFactoryException{
		//log.info("--------------------Debugging DomicilioController.showDomicilioRepLegal-------------------");
		limpiarFormDomicilio();
		setStrCallingFormId(strFormIdRepLegal);
		listaUbigeo(event);
	}
	
	public void showDomicilioSocioNatu(ActionEvent event) throws BusinessException, EJBFactoryException{
		//log.info("--------------------Debugging DomicilioController.showDomicilioSocioNatu-------------------");
		limpiarFormDomicilio();
		setStrCallingFormId(strFormIdSocioNatu);
		listaUbigeo(event);
	}
	
	public void showDomicilioSocioNatuEmp(ActionEvent event) throws BusinessException, EJBFactoryException{
		//log.info("--------------------Debugging DomicilioController.showDomicilioSocioNatuEmp-------------------");
		limpiarFormDomicilio();
		setStrCallingFormId(strFormIdSocioNatuEmp);
		listaUbigeo(event);
	}
	
	public void showDomicilioBeneficiario(ActionEvent event) throws BusinessException, EJBFactoryException{
		//log.info("--------------------Debugging DomicilioController.showDomicilioBeneficiario-------------------");
		limpiarFormDomicilio();
		setStrCallingFormId(strFormIdBeneficiario);
		listaUbigeo(event);
	}
	
	public void limpiarFormDomicilio(){
		//log.info("--------------------Debugging DomicilioController.limpiarFormDomicilio-------------------");
		//limpiar bean domicilio
		Domicilio domi = new Domicilio();
		setBeanDomicilio(domi);
	}
	
	public void limpiarArrayDomicilio(){
		//log.info("--------------------Debugging DomicilioController.limpiarFormDomicilio-------------------");
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
		/*log.info("-----------------------Debugging DomicilioController.listarDomicilio-----------------------------");
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de dirección: ");
		log.info("--------------------------------------------------");
		log.info("pIntIdpersona = "+ intIdPersona);
		*/
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
			/*log.info("listaDomicilio.size: "+listaDomicilio.size());
			for(int i=0; i<listaDomicilio.size(); i++){
				Domicilio dom = (Domicilio) arrayDomicilio.get(i);
				log.info("PERS_IDPERSONA_N   = " +dom.getId().getIntIdPersona());
				log.info("DOMI_IDDOMICILIO_N = " +dom.getId().getIntIdDomicilio());
				log.info("V_DIRECCION  		 = " +dom.getStrDireccion());
				log.info("DOMI_OBSERVACION_C = " +dom.getStrObservacion());
			}*/
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
		String strDescDpto = "";
		String strDescProvincia = "";
		String strDescDistrito = "";
		String strDescTipoVia = "";
		String strDescTipoZona = "";
		List<Tabla> lstTablaTipoVia;
		List<Tabla> lstTablaTipoZona;
		TablaFacadeRemote tablaFacade;
	  try{
		  tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
		  lstTablaTipoVia = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOVIA));
		  lstTablaTipoZona = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOZONA));
		//log.info("-----------------------Debugging DomicilioController.grabarDomicilio-------------------------");
		//log.info(beanDomicilio);  
		  Domicilio dom = new Domicilio();
	      dom = (Domicilio) getBeanDomicilio();

	      //Descripcion Tipo de Via
	      for (Tabla tabla : lstTablaTipoVia) {
	    	  if (tabla.getIntIdDetalle().equals(dom.getIntTipoViaCod())) {
	    		  strDescTipoVia = tabla.getStrAbreviatura();
	    		  break;
	    	  }
	      }
	      //Descripcion Departamento
	      for (Ubigeo dpto : listaUbigeoDepartamento) {
	    	  if (dpto.getIntIdUbigeo().equals(dom.getIntParaUbigeoPkDpto())) {
	    		  strDescDpto = dpto.getStrDescripcion();
	    		  break;
	    	  }
	      }
	      //Descripcion Provincia
	      for (Ubigeo prov : listaUbigeoProvincia) {
	    	  if (prov.getIntIdUbigeo().equals(dom.getIntParaUbigeoPkProvincia())) {
	    		  strDescProvincia = prov.getStrDescripcion();
	    		  break;
	    	  }
	      }
	      //Descripcion Distrito
	      for (Ubigeo dist : listaUbigeoDistrito) {
	    	  if (dist.getIntIdUbigeo().equals(dom.getIntParaUbigeoPkDistrito())) {
	    		  strDescDistrito = dist.getStrDescripcion();
	    		  break;
	    	  }
	      }
	      //Descripcion Tipo de Zona
	      for (Tabla tabla : lstTablaTipoZona) {
	    	  if (tabla.getIntIdDetalle().equals(dom.getIntTipoZonaCod())) {
	    		  strDescTipoZona = tabla.getStrAbreviatura();
	    		  break;
	    	  }
	      }
	      
	      String direccion = (strDescTipoVia + ". " + dom.getStrNombreVia()+ " Nro. "+ dom.getIntNumeroVia() + " "+ 
	    		  (dom.getStrInterior().trim()==""?(" - Int. "+dom.getStrInterior()+" "):"") +
	    		  strDescTipoZona + ". "+dom.getStrNombreZona()+
	    		  " - Referencia: "+ dom.getStrReferencia())
	    		  			+" - "+ strDescDpto
	    		  			+" - "+ strDescProvincia
	    		  			+" - "+ strDescDistrito;
	      dom.setStrDireccion(direccion);
	      
	      ArrayList arrDom = new ArrayList();
	      ArrayList listaDomicilio = new ArrayList();
	      
	      
	      //log.info(dom);
	      //**Inicio manejo de archivo adjunto**//
	      FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
	      Archivo archivo = fileUploadController.getObjArchivo(); 
	      //log.info(archivo);
	      if(archivo!=null && archivo.getId().getIntItemArchivo()!=null){
	    	  /*log.info("archivo tipo:"+archivo.getId().getIntParaTipoCod());
	    	  log.info("archivo item:"+archivo.getId().getIntItemArchivo());
	    	  log.info("archivo nomb:"+archivo.getStrNombrearchivo());*/
	    	  dom.setIntParaTipoArchivo(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CROQUIS);
	    	  dom.setIntParaItemArchivo(archivo.getId().getIntItemArchivo());
	    	  dom.setIntParaItemHistorico(archivo.getId().getIntItemHistorico());
	      }
	    //**Fin manejo de archivo adjunto**//
	      
	      //log.info("strCallingFormId: "+getStrCallingFormId());
	      if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdJuriConve)){
	    	  if(getBeanListDirecPerJuri()!=null){
	        	  arrDom = (ArrayList) getBeanListDirecPerJuri();
	          }
	    	  arrDom.add(dom);
	    	  
	    	  setBeanListDirecPerJuri(arrDom);
	      }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdRepLegal)){
	    	  if(getBeanListDirecRepLegal()!=null){
	        	  arrDom = (ArrayList) getBeanListDirecRepLegal();
	          }
	    	  arrDom.add(dom);
	    	  
	    	  setBeanListDirecRepLegal(arrDom);
		  }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdSocioNatu)){
	    	  if(getBeanListDirecSocioNatu()!=null){
	    		  log.info("beanListDirecSocioNatu.size: "+getBeanListDirecSocioNatu().size());
	        	  arrDom = (ArrayList) getBeanListDirecSocioNatu();
	          }
	    	  arrDom.add(dom);
	    	  
	    	  setBeanListDirecSocioNatu(arrDom);
		  }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdSocioNatuEmp)){
	    	  if(getListDirecSocioNatuEmp()!=null){
	        	  arrDom = (ArrayList) getListDirecSocioNatuEmp();
	          }
	    	  arrDom.add(dom);
	    	  
	    	  setListDirecSocioNatuEmp(arrDom);
		  }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdBeneficiario)){
	    	  if(getListDirecBeneficiario()!=null){
	        	  arrDom = (ArrayList) getListDirecBeneficiario();
	          }
	    	  arrDom.add(dom);
	    	  
	    	  setListDirecBeneficiario(arrDom);
		  }else if(getStrCallingFormId()!=null && getStrCallingFormId().equals(strFormIdJuriProveedor)){
			  if(beanListDirecProveedor!=null){
	        	  //log.info("beanListDirecProveedor!=null");
				  arrDom = (ArrayList) beanListDirecProveedor;
	          }else{
	        	  //log.info("beanListDirecProveedor==null");
	          }
			  /*log.info("habilitarEditar:"+habilitarEditar);
			  log.info("rowKeyPersite:"+rowKeyPersite);
			  log.info(beanDomicilio);*/
			  if(habilitarEditar && rowKeyPersite!= null && !rowKeyPersite.isEmpty()){
				  //log.info("--a");
				  if(beanListDirecProveedor!=null){
					  //log.info("beanListDirecProveedor:"+beanListDirecProveedor.size());
					  for(Domicilio d : beanListDirecProveedor){
				    		//log.info(d);
				    	}
					  	for(int i=0; i<beanListDirecProveedor.size(); i++){
							if(Integer.parseInt(rowKeyPersite)==i){
								//log.info("match!");
								beanListDirecProveedor.set(i, beanDomicilio);
							}
						}
					  	/*for(Domicilio d : beanListDirecProveedor){
				    		//log.info(d);
				    	}*/
				    }else{
				    	//log.info("beanListDirecProveedor:null");
				    }
			  }else{
				  arrDom.add(dom);
		    	  setBeanListDirecProveedor(arrDom);
			  }	    	  
	    	  
		  }
	  }catch (Exception e) {
		log.error(e.getMessage(),e);
	}
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
		//log.info("-----------------------Debugging DomicilioController.removeDireccion-----------------------------");
	    ArrayList arrayDom = new ArrayList();
	    for(int i=0; i<getBeanListDomicilio().size(); i++){
	    	Domicilio dom = (Domicilio) getBeanListDomicilio().get(i);
	    	if(dom.getChkDir() == false){
	    		arrayDom.add(dom);
	    	}
	    }
	    setBeanListDomicilio(arrayDom);
	}
	
	public void viewDomicilioJuridica(ActionEvent event){
		try{
			/*log.info("------------------Debugging DomicilioController.viewDomicilioJuridica-------------------");
		    log.info("Se ha seteado el Service");*/
		    String rowKey = getRequestParameter("rowKeyDomicilioJuridica");
			//log.info("rowKeyDomicilioJuridica = "+ rowKey);			
			viewDomicilio(beanListDirecPerJuri, rowKey);	
		}catch (Exception e) {
			log.error(e);
		}
	}
	
	public void viewDomicilioProveedor(ActionEvent event){
		try{
			//log.info("------------------Debugging viewDomicilioProveedor-------------------");
		    String rowKey = getRequestParameter("rowKey");
			rowKeyPersite = rowKey;
			viewDomicilio(beanListDirecProveedor, rowKey);
			
			
			//***Inicio manejo de archivos**//
			//log.info("intParaItemArchivo:"+beanDomicilio.getIntParaItemArchivo());
			if(beanDomicilio.getIntParaItemArchivo()!=null){
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
				ArchivoId archivoId = new ArchivoId();				
				archivoId.setIntItemArchivo(beanDomicilio.getIntParaItemArchivo());
				archivoId.setIntItemHistorico(beanDomicilio.getIntParaItemHistorico());
				archivoId.setIntParaTipoCod(beanDomicilio.getIntParaTipoArchivo());
				
				Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
				log.info("archivo:"+archivo.getStrNombrearchivo());
				beanDomicilio.setArchivo(archivo);
			}
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
		    fileUploadController.setObjArchivo(null);
		  //***Fin manejo de archivos**//
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void viewDomicilioNatural(ActionEvent event){
		try{
			//log.info("------------------Debugging DomicilioController.viewDomicilioNatural-------------------");
		    String rowKey = getRequestParameter("rowKeyDomicilioRepLegal");
			//log.info("rowKeyDomicilioNatural = "+ rowKey);
			
			viewDomicilio(beanListDirecRepLegal, rowKey);
		}catch (Exception e) {
			log.error(e);
		}
	}
	
	public void viewDomicilioSocioNatu(ActionEvent event){
		try{
			//log.info("------------------Debugging DomicilioController.viewDomicilioSocioNatu-------------------");
		    String rowKey = getRequestParameter("rowKeyDomicilioSocioNatu");
			//log.info("rowKeyDomicilioSocioNatu = "+ rowKey);
			
			viewDomicilio(beanListDirecSocioNatu, rowKey);
		}catch (Exception e) {
			log.error(e);
		}
	}
	
	public void viewDomiSocioNatuEmp(ActionEvent event){
		try{
			//log.info("------------------Debugging DomicilioController.viewDomicilioSocioNatuEmp-------------------");
		    String rowKey = getRequestParameter("rowKeyDomiSocioNatuEmp");
			//log.info("rowKeyDomiSocioNatuEmp = "+ rowKey);
			
			viewDomicilio(listDirecSocioNatuEmp, rowKey);
		}catch (Exception e) {
			log.error(e);
		}
	}
	
	public void viewDomiBeneficiario(ActionEvent event){
		try{
			//log.info("------------------Debugging DomicilioController.viewDomicilioBeneficiario-------------------");
			String rowKey = getRequestParameter("rowKeyDomicilioBeneficiario");
			//log.info("rowKeyDomicilioBeneficiario = "+ rowKey);
		
			viewDomicilio(listDirecBeneficiario, rowKey);
		}catch (Exception e) {
			log.error(e);
		}
	}
	
	public void viewDomicilio(List<Domicilio> listDomicilio, String rowKey) throws Exception{
		//log.info("------------------Debugging DomicilioController.viewDomicilio-------------------");
	    Domicilio domicilio = null;
		
	    if(listDomicilio!=null){
	    	for(int i=0; i<listDomicilio.size(); i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					domicilio = listDomicilio.get(i);
				}
			}
	    }
		
	    /*if(domicilio.getId()!=null && domicilio.getId().getIntIdDomicilio()!=null){
	    	habilitarEditar = Boolean.FALSE;
	    }else{
	    	habilitarEditar = Boolean.TRUE;
	    }*/
	    habilitarEditar = Boolean.TRUE;
		setBeanDomicilio(domicilio);
		listaUbigeo(null);
		/*log.info("dep:"+domicilio.getIntParaUbigeoPkDpto());
		log.info("pro:"+domicilio.getIntParaUbigeoPkProvincia());
		log.info("dis:"+domicilio.getIntParaUbigeoPkDistrito());
		*/
		seleccionarDepartamento();
		seleccionarProvincia();
		
		//setFormEnableDisableDomicilio(true);
	}
	
	public void quitarDomicilioJuridica(ActionEvent event){
		log.info("-------------------------------------Debugging quitarDomicilioJuridica-------------------------------------");
		String rowKey = getRequestParameter("rowKeyDomicilioJuridica");
		log.info("rowKeyDomicilioJuridica: "+rowKey);
		quitarDomicilio(beanListDirecPerJuri, rowKey);
	}
	
	public void quitarDomicilioProveedor(ActionEvent event){
		log.info("-------------------------------------Debugging quitarDomicilioJuridica-------------------------------------");
		String rowKey = getRequestParameter("rowKey");
		log.info("rowKey: "+rowKey);
		quitarDomicilio(beanListDirecProveedor, rowKey);
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
		//log.info("-------------------------------------Debugging DomicilioController.quitarDomicilio-------------------------------------");
		Domicilio domiTmp = null;
		if(listDomicilio!=null){
			for(int i=0; i<listDomicilio.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Domicilio domicilio = listDomicilio.get(i);
					//log.info("domicilio.id: "+domicilio.getId());
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
		//log.info("--------------------DomicilioController.listaUbigeo------------------------");
		listaUbigeoDepartamento = null;
		GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoDepartamento = remote.getListaUbigeoDeDepartamento();
	    //log.info("listaUbigeoDepartamento.size(): " + listaUbigeoDepartamento.size());
	    //listaUbigeoProvincia = remote.getListaUbigeoDeProvinciaPorIdUbigeo();
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
	
	public void reloadCboProvincia(ValueChangeEvent event) throws EJBFactoryException, BusinessException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboProvincia()-----------------------------");
		Integer intIdDpto = Integer.parseInt(""+event.getNewValue());
		log.info("intIdEmpresa = "+intIdDpto);
		GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoProvincia = remote.getListaUbigeoDeProvinciaPorIdUbigeo(intIdDpto);
		
	}
	
	public void reloadCboDistrito(ValueChangeEvent event) throws EJBFactoryException, BusinessException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboDistrito()-----------------------------");
		Integer intIdProvincia = Integer.parseInt(""+event.getNewValue());
		log.info("intIdEmpresa = "+intIdProvincia);
		GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoDistrito = remote.getListaUbigeoDeDistritoPorIdUbigeo(intIdProvincia);
		
	}
	
	public void cargarListaProvincia(Integer intIdDepartamento) throws Exception {
		GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoProvincia = remote.getListaUbigeoDeProvinciaPorIdUbigeo(intIdDepartamento);

	}
	
	public void cargarListaDistrito(Integer intIdProvincia) throws Exception {
		GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoDistrito = remote.getListaUbigeoDeDistritoPorIdUbigeo(intIdProvincia);
	}
	
	protected void setMessageSuccess(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(msg));
	}
	protected void setMessageSuccess(String zone, String msg) {
		FacesContext.getCurrentInstance().addMessage(zone,	new FacesMessage(msg));
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		return sesion.getAttribute(beanName);
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
	public List<Domicilio> getBeanListDirecProveedor() {
		return beanListDirecProveedor;
	}
	public void setBeanListDirecProveedor(List<Domicilio> beanListDirecProveedor) {
		this.beanListDirecProveedor = beanListDirecProveedor;
	}
	public boolean isHabilitarEditar() {
		return habilitarEditar;
	}
	public void setHabilitarEditar(boolean habilitarEditar) {
		this.habilitarEditar = habilitarEditar;
	}
}