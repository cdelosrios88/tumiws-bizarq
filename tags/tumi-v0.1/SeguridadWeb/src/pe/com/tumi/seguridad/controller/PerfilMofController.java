package pe.com.tumi.seguridad.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.ConstanteReporte;
import pe.com.tumi.common.util.DaoException;
//import pe.com.tumi.common.util.ControlsFiller;
import pe.com.tumi.seguridad.domain.PerfilMof;
import pe.com.tumi.seguridad.service.impl.PerfilMofServiceImpl;
import pe.com.tumi.seguridad.usuario.domain.Perfil;
import pe.com.tumi.usuario.service.impl.UsuarioServiceImpl;

public class PerfilMofController extends GenericController {
	private    	PerfilMofServiceImpl 	perfilMofService;
	private    UsuarioServiceImpl	 	usuarioPerfilService;
	private		List 					beanListPerfilMof;
	private		List 					beanListAdmDoc;
	private		int						rows = 5;
	private 	PerfilMof				beanPerfilMof = new PerfilMof();
	private 	String					cboEmpresa;
	private 	Integer					cboPerfil;
	private		Integer					cboEstado;
	private		Integer					cboEstadoAdm;
	private		Integer					intCboEmpresa;
	private		Integer					intCboPerfilEmpresa;
	private		Integer					intCboEmpresaOrg;
	private 	Boolean					chkPerfil;
	private		String					msgTxtEmpresa;
	private		String					msgTxtEstado;
	private		String					msgTxtPerfil;
	private		String					msgTxtEmpresaOrg;
	private		String					msgTxtArchivoOrg;
	private		String					msgTxtDocum;
	//Variables de Administración de Documentación
	private		String					strCboEmpresaAdm;
	private		String					strCboPerfilEmpAdm;
	private		String					strEmpresaAdm;
	private		String					strOrganigrama;
	private		String					strCboPerfilAdm;
	private		Boolean					chkTodosEnabled = true;
	private		Boolean					formPerfilMofEnabled = true;
	private 	Boolean 				strAdjuntoRendered = false;
	private		Boolean					perfilMofRendered = false;
	private		Boolean					cboPerfilRendered = true;
	private		List<Perfil>			listPerfil = new ArrayList<Perfil>();
	private    ArrayList<SelectItem> cboPerfil1 = new ArrayList<SelectItem>();		

	//Métodos paa Perfil / MOF
	public void habilitarGrabarPerfilMof(ActionEvent event) {
		log.info("--------------------Debugging PerfilMofController.habilitarGrabarPerfilMof----------------------");
		setFormPerfilMofEnabled(false);
		setChkTodosEnabled(false);
		setPerfilMofRendered(true);
		setStrAdjuntoRendered(false);
		limpiarFormPerfilMof();
	}
	
	public void habilitarActualizarPerfilMof(ActionEvent event) {
		log.info("--------------------Debugging PerfilMofController.habilitarActualizarPerfilMof------------------");
		setFormPerfilMofEnabled(false);
		setChkTodosEnabled(true);
		setCboPerfilRendered(true);
		setPerfilMofRendered(true);
		setStrAdjuntoRendered(true);
	}
	
	public void cancelarGrabarPerfilMof(ActionEvent event) {
		log.info("--------------------Debugging PerfilMofController.cancelarGrabarPerfilMof-----------------------");
		setFormPerfilMofEnabled(true);
		setStrAdjuntoRendered(false);
		limpiarFormPerfilMof();
	}
	
	public void limpiarFormPerfilMof() {
		PerfilMof perm = new PerfilMof();
		perm.setIntIdEmpresa(0);
		perm.setIntIdPerfil(0);
		perm.setIntIdEstado(0);
		perm.setStrArchivo("");
		setIntCboEmpresa(0);
		setIntCboPerfilEmpresa(0);
		setMsgTxtArchivoOrg("");
		setMsgTxtDocum("");
		setMsgTxtEmpresa("");
		setMsgTxtEmpresaOrg("");
		setMsgTxtEstado("");
		setMsgTxtPerfil("");
		setChkPerfil(false);
		setCboPerfilRendered(true);
		setBeanPerfilMof(perm);
	}
	
	public void listarPerfilMof(ActionEvent event){
		log.info("-----------------Debugging PerfilMofController.listarPerfilMof------------------");
	    setService(perfilMofService);
	    log.info("Se ha seteado el Service");
		
	    log.info("Seteando los parametros de busqueda de Perfil/MOF: ");
		log.info("-------------------------------------------------");
		log.info("getCboEmpresa(): "+getCboEmpresa());		
		log.info("getCboPerfil():  "+getCboPerfil());
		log.info("getCboEstado():  "+getCboEstado());
		HashMap prmtBusqAdmForm = new HashMap();
		Integer cboPerfil  = (getCboPerfil()==null  || getCboPerfil().equals("selectNone")?0:getCboPerfil());
		//prmtBusqAdmForm.put("pIntIdEmpresa", 		Integer.parseInt((getCboEmpresa()==null||getCboEmpresa().equals("0")?null:getCboEmpresa())));
		prmtBusqAdmForm.put("pCboEstado", 			(getCboEstado()==0?null:getCboEstado()));
		prmtBusqAdmForm.put("pIntIdEmpresa", 		Integer.parseInt(getCboEmpresa()));
		prmtBusqAdmForm.put("pIntIdPerfil", 		cboPerfil);		
		prmtBusqAdmForm.put("pIntIdVersion", 		null);
		
	    ArrayList arrayPerfilMof = new ArrayList();
	    ArrayList listaPerfilMof = new ArrayList();
	    try {
	    	arrayPerfilMof = getService().listarPerfilMof(prmtBusqAdmForm);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarPerfilMof() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Listado de Perfil/MOF ha sido satisfactorio");
		
		for(int i=0; i<arrayPerfilMof.size(); i++){
			HashMap hash = (HashMap) arrayPerfilMof.get(i);
			PerfilMof perm = new PerfilMof();
			log.info("EMPR_IDEMPRESA_N = "+hash.get("EMPR_IDEMPRESA_N"));
			int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
			perm.setIntIdEmpresa(intIdEmpresa);
			log.info("JURI_RAZONSOCIAL_V = "+hash.get("JURI_RAZONSOCIAL_V"));
			String strEmpresa = "" + hash.get("JURI_RAZONSOCIAL_V");
			perm.setStrEmpresa(strEmpresa);
			log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
			int intIdPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
			perm.setIntIdPerfil(intIdPerfil);
			log.info("PERF_DESCRIPCION_V = "+hash.get("PERF_DESCRIPCION_V"));
			String strPerfil = ""+hash.get("PERF_DESCRIPCION_V");
			perm.setStrPerfil(strPerfil);
			log.info("MOF_VERSION_N = "+hash.get("MOF_VERSION_N"));
			Integer intNroVersion = Integer.parseInt("" + hash.get("MOF_VERSION_N"));
			perm.setIntVersion(intNroVersion);
			log.info("MOF_ARCHIVO_B = "+hash.get("MOF_ARCHIVO_B"));
			String strArchivo = ""+hash.get("MOF_ARCHIVO_B");
			perm.setStrArchivo(strArchivo);
			log.info("MOF_FECHAREGISTRO_D = "+(hash.get("MOF_FECHAREGISTRO_D") == null ?"":hash.get("MOF_FECHAREGISTRO_D")));
			String strFecReg = ""+(hash.get("MOF_FECHAREGISTRO_D") == null ?"":hash.get("MOF_FECHAREGISTRO_D"));
			perm.setDaFecRegistro(strFecReg);
			log.info("MOF_IDESTADO_N = "+hash.get("MOF_IDESTADO_N"));
			int intIdEstado = Integer.parseInt("" + hash.get("MOF_IDESTADO_N"));
			perm.setIntIdEstado(intIdEstado);
			log.info("V_ESTADO = "+hash.get("V_ESTADO"));
			String strEstado = ""+hash.get("V_ESTADO");
			perm.setStrEstado(strEstado);
			
			listaPerfilMof.add(perm);
		}
		setBeanListPerfilMof(listaPerfilMof);
	}
	
	public void modificarPerfilMof(ActionEvent event) throws DaoException {
		log.info("-----------------Debugging PerfilMofController.modificarPerfMof-----------------------"); 
		//ControlsFiller controlsFiller = new ControlsFiller();
		String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelPerfMofModalPanel:hiddenIdEmpresa");
		String strPerfilId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelPerfMofModalPanel:hiddenIdPerfil");
		String strVersionId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelPerfMofModalPanel:hiddenIdVersion");
		setService(perfilMofService);
		log.info("Se ha seteado el service");
		log.info("strEmpresaId  = "+strEmpresaId);
		log.info("strPerfilId   = "+strPerfilId);
		log.info("strTransaccionId  = "+strVersionId);
		PerfilMof perm = new PerfilMof();
		ArrayList arrayAdmFomDoc = new ArrayList();
		HashMap prmtBusqPerfilMof  = new HashMap();
		prmtBusqPerfilMof.put("pIntIdEmpresa", 		Integer.parseInt(strEmpresaId));
		prmtBusqPerfilMof.put("pIntIdPerfil", 		Integer.parseInt(strPerfilId));
		prmtBusqPerfilMof.put("pIntIdVersion", 		Integer.parseInt(strVersionId));
		
		//El metodo devuelve una sola fila
		arrayAdmFomDoc = getService().listarPerfilMof(prmtBusqPerfilMof);
		HashMap hash = (HashMap) arrayAdmFomDoc.get(0);
		
		log.info("EMPR_IDEMPRESA_N = "+hash.get("EMPR_IDEMPRESA_N"));
		int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
		perm.setIntIdEmpresa(intIdEmpresa);
		setIntCboEmpresa(intIdEmpresa);
		log.info("V_RAZONSOCIAL = "+hash.get("V_RAZONSOCIAL"));
		String strEmpresa = "" + hash.get("V_RAZONSOCIAL");
		perm.setStrEmpresa(strEmpresa);
		log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
		int intIdPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
		perm.setIntIdPerfil(intIdPerfil);
		setIntCboPerfilEmpresa(intIdPerfil);
		log.info("V_DESCRIPCION_PERF = "+hash.get("V_DESCRIPCION_PERF"));
		String strPerfil = ""+hash.get("V_DESCRIPCION_PERF");
		perm.setStrPerfil(strPerfil);
		log.info("MOF_VERSION_N = "+hash.get("MOF_VERSION_N"));
		Integer intNroVersion = Integer.parseInt("" + hash.get("MOF_VERSION_N"));
		perm.setIntVersion(intNroVersion);
		log.info("MOF_ARCHIVO_B = "+hash.get("MOF_ARCHIVO_B"));
		String strArchivo = ""+hash.get("MOF_ARCHIVO_B");
		perm.setStrArchivo(strArchivo);
		log.info("MOF_IDESTADO_N = "+hash.get("MOF_IDESTADO_N"));
		int intIdEstado = Integer.parseInt("" + hash.get("MOF_IDESTADO_N"));
		perm.setIntIdEstado(intIdEstado);
		ValueChangeEvent event1 = null;
		reloadCboPerfiles(event1);
		setChkPerfil(false);
		setBeanPerfilMof(perm);
	    setPerfilMofRendered(true);
	    setFormPerfilMofEnabled(false);
	    habilitarActualizarPerfilMof(event);
	}
	
	public void grabarPerfilMof(ActionEvent event) throws DaoException{
		log.info("-------------------Debugging PerfilMofController.grabarPerfilMof------------------------");
	    setService(perfilMofService);
	    log.info("Se ha seteado el Service");
	    String strFileNameDoc = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:hiddenStrFileDocName");
		log.info("strFileNameDoc:  				"+strFileNameDoc);
		log.info("getStrCboEmpresa(): 			"+getIntCboEmpresa());
		log.info("getStrCboPerfilEmpresa(): 	"+(getIntCboPerfilEmpresa().equals("selectNone")?0:getIntCboPerfilEmpresa()));
		log.info("getChkPerfil(): 				"+(getChkPerfil()==true?1:0));
	    PerfilMof perm = new PerfilMof();
	    perm = (PerfilMof) getBeanPerfilMof();
	    perm.setIntIdEmpresa(getIntCboEmpresa());
	    perm.setIntIdPerfil(getIntCboPerfilEmpresa().equals("selectNone")?0:getIntCboPerfilEmpresa());
	    perm.setStrArchivo(strFileNameDoc.equals("")? perm.getStrArchivo():strFileNameDoc);
	    perm.setIntPerfil(getChkPerfil()==true?1:0);
	    
	    Boolean bValidar = true;
	    
	      int intIdEmpresa = perm.getIntIdEmpresa();
	      if(intIdEmpresa==0){
	    	  setMsgTxtEmpresa("* Debe seleccionar una Empresa.");
	    	  bValidar = false;
	      }else{
	    	  setMsgTxtEmpresa("");
	      }
	      int intIdEstado = perm.getIntIdEstado();
	      if(intIdEstado == 0){
	    	  setMsgTxtEstado("* Debe completar el campo Estado.");
	    	  bValidar = false;
	      } else{
	    	  setMsgTxtEstado("");
	      }
	      int intIdPerfil = perm.getIntIdPerfil();
	      if(getChkPerfil()==false){
	    	  if(intIdPerfil == 0){
		    	  setMsgTxtPerfil("* Debe seleccionar un Tipo de Perfil.");
		    	  bValidar = false;
		      } else{
		    	  setMsgTxtPerfil("");
		      }
	      }
	      if(strFileNameDoc.equals("")){
	    	  setMsgTxtDocum("* Debe subir un archivo tipo .pdf");
	    	  bValidar = false;
	      }else{
	    	  setMsgTxtDocum("");
	      }
	      
	      if(bValidar==true){
		    try {
		    	if(getChkPerfil()==true){
		    		setCboPerfilRendered(false);
		    	}
	  	    	getService().grabarPerfilMof(perm);
			} catch (DaoException e) {
				log.info("ERROR  getService().grabarPerfilMof(perm:) " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listarPerfilMof(event);
			setMessageSuccess("Los datos se actualizaron satisfactoriamente ");
	      }
	}
	
	public void eliminarPerfilMof(ActionEvent event) throws DaoException{
		log.info("-----------------Debugging PerfilMofController.eliminarPerfilMof------------------------");
	    setService(perfilMofService);
	    log.info("Se ha seteado el Service");
	    String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelPerfMofModalPanel:hiddenIdEmpresa");
		String strPerfilId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelPerfMofModalPanel:hiddenIdPerfil");
		String strVersionId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelPerfMofModalPanel:hiddenIdVersion");
		
	    HashMap prmtPerfilMof  = new HashMap();
	    prmtPerfilMof.put("pIntIdEmpresa", 		Integer.parseInt(strEmpresaId));
	    prmtPerfilMof.put("pIntIdPerfil", 		Integer.parseInt(strPerfilId));
	    prmtPerfilMof.put("pIntIdVersion", 		Integer.parseInt(strVersionId));
	    
    	try {
  	    	getService().eliminarPerfilMof(prmtPerfilMof);
		} catch (DaoException e) {
			log.info("ERROR  getService().eliminarPerfilMof(perm:) " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listarPerfilMof(event);
		setMessageSuccess("Registro Bloqueado correctamente");
	}
	
	public void reloadCboPerfiles(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging UsuarioController.reloadCboPerfiles()-----------------------------");
		setService(perfilMofService);		
		Integer idCboEmpresa = 0;
		if(event != null){
			idCboEmpresa = (Integer)event.getNewValue();
		}else{
			idCboEmpresa = 0;
		}
		//this.cboPerfil1.clear();
		HashMap prmtBusqPerfiles = new HashMap();
		prmtBusqPerfiles.put("pIntIdEmpresa", idCboEmpresa);
	    
		//ArrayList arrayPerfiles = new ArrayList();
	    listPerfil = getService().listarPerfiles1(prmtBusqPerfiles);
	    log.info("arrayPerfiles.size(): "+listPerfil.size());
	    /*
	    for(int i=0; i<arrayPerfiles.size() ; i++){
        	HashMap hash = (HashMap) arrayPerfiles.get(i);
        	log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
			int intIdPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
			log.info("PERF_DESCRIPCION_V = "+hash.get("PERF_DESCRIPCION_V"));
			String strDescPerfil = "" + hash.get("PERF_DESCRIPCION_V");
			//per.setStrDescripcion(strDescPerfil);
			this.cboPerfil1.add(new SelectItem(intIdPerfil,strDescPerfil));
        }
        SelectItem lblSelect = new SelectItem("selectNone","Seleccionar..");
        this.cboPerfil1.add(0, lblSelect);
        
        log.info("this.cboPerfil.size(): "+this.cboPerfil1.size());
	    
		setCboPerfil1(cboPerfil1);
		log.info("Saliendo de UsuarioController.reloadCboPerfil()...");*/
		//reloadCboSucursales(event);
	}
	
	public void downloadArc(ActionEvent event){
	    log.info("Ingreso en la descarga ");
	    String strArc = getRequestParameter("paramArc");
	    log.info("paramArc: "+strArc);
	    
	    log.info("strAdjunto: "+strArc);
	    try{
	        setFileName(strArc);
	        setPathToFile(getServletContext().getRealPath("") + ConstanteReporte.RUTA_UPLOADED + strArc);
	        super.downloadFile(event);
	        
	    }catch(Exception e){
	        log.info("error  en los archivos " + e.getMessage());
	        setMessageError("No se pudo descargar el archivo, compruebe si existe '" + getFileName()+ "'.");
	    }
	}
	
	public void grabarOrganigrama(ActionEvent event) throws DaoException{
		log.info("-----------------Debugging PerfilMofController.grabarOrganigrama------------------------");
	    setService(perfilMofService);
	    log.info("Se ha seteado el Service");
	    String strFileName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPanelOrg:hiddenStrFileNameOrg");
		log.info("getIntCboEmpresaOrg(): 		"+getIntCboEmpresaOrg());
		log.info("strFileName:  				"+strFileName);
		PerfilMof perm = new PerfilMof();
	    perm = (PerfilMof) getBeanPerfilMof();
	    perm.setIntIdEmpresaOrg(getIntCboEmpresaOrg());
	    perm.setStrArchOrg(strFileName);
	    
	    Boolean bValidar = true;
	    
	    int intIdEmpresaOrg = perm.getIntIdEmpresaOrg();
	    if(intIdEmpresaOrg==0){
	    	setMsgTxtEmpresaOrg("* Debe seleccionar una Empresa.");
	    	bValidar = false;
	    }else{
	        setMsgTxtEmpresa("");
	    }
	    if(strFileName.equals("")){
	    	setMsgTxtArchivoOrg("* Debe elegir un Archivo Tipo PDF para Grabar el Documento.");
	    	bValidar = false;
	    }else{
	    	setMsgTxtArchivoOrg("");
	    }
	    if(bValidar==true){
	    	try {
	  	    	getService().updateOrganigrama(perm);
			} catch (DaoException e) {
				log.info("ERROR  getService().grabarFormDoc(formdoc:) " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setMessageSuccess("Los datos se actualizaron satisfactoriamente ");
	    }
	}
	
	public void listarAdmDoc(ActionEvent event){
		log.info("-----------------Debugging PerfilMofController.listarPerfilMof------------------");
	    setService(perfilMofService);
	    log.info("Se ha seteado el Service");
		
	    log.info("Seteando los parametros de busqueda de Perfil/MOF: ");
		log.info("-------------------------------------------------");
		HashMap prmtBusqAdmForm = new HashMap();		
		String cboPerfilAdm  = (getStrCboPerfilEmpAdm()==null  || getStrCboPerfilEmpAdm().equals("selectNone")?"0":getStrCboPerfilEmpAdm());
		prmtBusqAdmForm.put("pCboEstado", 			(getCboEstadoAdm()==0?null:getCboEstadoAdm()));
		//prmtBusqAdmForm.put("pIntIdEmpresa", 		(getStrCboEmpresaAdm()==null||getStrCboEmpresaAdm().equals("0")?null:getStrCboEmpresaAdm()));
		prmtBusqAdmForm.put("pIntIdEmpresa", 		Integer.parseInt((getStrCboEmpresaAdm()==null||getStrCboEmpresaAdm().equals("0")?"0":getStrCboEmpresaAdm())));
		prmtBusqAdmForm.put("pIntIdPerfil", 		Integer.parseInt(cboPerfilAdm));
		prmtBusqAdmForm.put("pIntIdVersion", 		null);
		
	    ArrayList arrayPerfilMof = new ArrayList();
	    ArrayList listaPerfilMof = new ArrayList();
	    try {
	    	arrayPerfilMof = getService().listarPerfilMof(prmtBusqAdmForm);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarPerfilMof() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Listado de Perfil/MOF ha sido satisfactorio");
		
		for(int i=0; i<arrayPerfilMof.size(); i++){
			HashMap hash = (HashMap) arrayPerfilMof.get(i);
			PerfilMof perm = new PerfilMof();
			log.info("EMPR_IDEMPRESA_N = "+hash.get("EMPR_IDEMPRESA_N"));
			int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
			perm.setIntIdEmpresa(intIdEmpresa);
			log.info("JURI_RAZONSOCIAL_V = "+hash.get("JURI_RAZONSOCIAL_V"));
			String strEmpresa = "" + hash.get("JURI_RAZONSOCIAL_V");
			perm.setStrEmpresa(strEmpresa);
			log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
			int intIdPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
			perm.setIntIdPerfil(intIdPerfil);
			log.info("PERF_DESCRIPCION_V = "+hash.get("PERF_DESCRIPCION_V"));
			String strPerfil = ""+hash.get("PERF_DESCRIPCION_V");
			perm.setStrPerfil(strPerfil);
			log.info("MOF_VERSION_N = "+hash.get("MOF_VERSION_N"));
			Integer intNroVersion = Integer.parseInt("" + hash.get("MOF_VERSION_N"));
			perm.setIntVersion(intNroVersion);
			log.info("MOF_ARCHIVO_B = "+hash.get("MOF_ARCHIVO_B"));
			String strArchivo = ""+hash.get("MOF_ARCHIVO_B");
			perm.setStrArchivo(strArchivo);
			log.info("MOF_FECHAREGISTRO_D = "+(hash.get("MOF_FECHAREGISTRO_D") == null ?"":hash.get("MOF_FECHAREGISTRO_D")));
			String strFecReg = ""+(hash.get("MOF_FECHAREGISTRO_D") == null ?"":hash.get("MOF_FECHAREGISTRO_D"));
			perm.setDaFecRegistro(strFecReg);
			log.info("MOF_IDESTADO_N = "+hash.get("MOF_IDESTADO_N"));
			int intIdEstado = Integer.parseInt("" + hash.get("MOF_IDESTADO_N"));
			perm.setIntIdEstado(intIdEstado);
			log.info("V_ESTADO = "+hash.get("V_ESTADO"));
			String strEstado = ""+hash.get("V_ESTADO");
			perm.setStrEstado(strEstado);
			
			listaPerfilMof.add(perm);
		}
		setBeanListAdmDoc(listaPerfilMof);
	}
	
	public void editAdmDoc(ActionEvent event) throws DaoException{
		log.info("-----------------Debugging PerfilMofController.editAdmDoc-----------------------");
		String strEmpresaId = getRequestParameter("intIdEmpresa");
		String strPerfilId = getRequestParameter("intIdPerfil");
		String strVersionId = getRequestParameter("intVersion");
		setService(perfilMofService);
		log.info("Se ha seteado el service");
		log.info("strEmpresaId  = "+strEmpresaId);
		log.info("strPerfilId   = "+strPerfilId);
		log.info("strTransaccionId  = "+strVersionId);
		PerfilMof perm = new PerfilMof();
		ArrayList arrayAdmFomDoc = new ArrayList();
		HashMap prmtBusqPerfilMof  = new HashMap();
		prmtBusqPerfilMof.put("pIntIdEmpresa", 		Integer.parseInt(strEmpresaId));
		prmtBusqPerfilMof.put("pIntIdPerfil", 		Integer.parseInt(strPerfilId));
		prmtBusqPerfilMof.put("pIntIdVersion", 		Integer.parseInt(strVersionId));
		
		//El metodo devuelve una sola fila
		arrayAdmFomDoc = getService().listarPerfilMof(prmtBusqPerfilMof);
		HashMap hash = (HashMap) arrayAdmFomDoc.get(0);
		
		log.info("JURI_RAZONSOCIAL_V = "+hash.get("JURI_RAZONSOCIAL_V"));
		String strEmpresa = "" + hash.get("JURI_RAZONSOCIAL_V");
		perm.setStrEmpresa(strEmpresa);
		setStrEmpresaAdm(strEmpresa);
		log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
		int intIdPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
		perm.setIntIdPerfil(intIdPerfil);
		setStrCboPerfilAdm(""+intIdPerfil);
		log.info("PERF_DESCRIPCION_V = "+hash.get("PERF_DESCRIPCION_V"));
		String strPerfil = ""+hash.get("PERF_DESCRIPCION_V");
		perm.setStrPerfil(strPerfil);
		log.info("MOF_VERSION_N = "+hash.get("MOF_VERSION_N"));
		Integer intNroVersion = Integer.parseInt("" + hash.get("MOF_VERSION_N"));
		perm.setIntVersion(intNroVersion);
		log.info("MOF_ARCHIVO_B = "+hash.get("MOF_ARCHIVO_B"));
		String strArchivo = ""+hash.get("MOF_ARCHIVO_B");
		perm.setStrArchivo(strArchivo);
		log.info("MOF_IDESTADO_N = "+hash.get("MOF_IDESTADO_N"));
		int intIdEstado = Integer.parseInt("" + hash.get("MOF_IDESTADO_N"));
		perm.setIntIdEstado(intIdEstado);
		log.info("EMPR_ORGANIGRAMA_B = "+hash.get("EMPR_ORGANIGRAMA_B"));
		String strOrganigrama = ""+hash.get("EMPR_ORGANIGRAMA_B");
		perm.setStrArchOrg(strOrganigrama);
		setStrOrganigrama(strOrganigrama);
		setBeanPerfilMof(perm);
	}	
	
	
	//Getters and Setters.
	public PerfilMofServiceImpl getPerfilMofService() {
		return perfilMofService;
	}
	public void setPerfilMofService(PerfilMofServiceImpl perfilMofService) {
		this.perfilMofService = perfilMofService;
	}
	public List getBeanListPerfilMof() {
		return beanListPerfilMof;
	}
	public void setBeanListPerfilMof(List beanListPerfilMof) {
		this.beanListPerfilMof = beanListPerfilMof;
	}
	public List getBeanListAdmDoc() {
		return beanListAdmDoc;
	}
	public void setBeanListAdmDoc(List beanListAdmDoc) {
		this.beanListAdmDoc = beanListAdmDoc;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public PerfilMof getBeanPerfilMof() {
		return beanPerfilMof;
	}
	public void setBeanPerfilMof(PerfilMof beanPerfilMof) {
		this.beanPerfilMof = beanPerfilMof;
	}
	public String getCboEmpresa() {
		return cboEmpresa;
	}
	public void setCboEmpresa(String cboEmpresa) {
		this.cboEmpresa = cboEmpresa;
	}
	public Integer getCboPerfil() {
		return cboPerfil;
	}
	public void setCboPerfil(Integer cboPerfil) {
		this.cboPerfil = cboPerfil;
	}
	public Integer getCboEstado() {
		return cboEstado;
	}
	public void setCboEstado(Integer cboEstado) {
		this.cboEstado = cboEstado;
	}
	public Integer getCboEstadoAdm() {
		return cboEstadoAdm;
	}
	public void setCboEstadoAdm(Integer cboEstadoAdm) {
		this.cboEstadoAdm = cboEstadoAdm;
	}
	public Integer getIntCboEmpresa() {
		return intCboEmpresa;
	}
	public void setIntCboEmpresa(Integer intCboEmpresa) {
		this.intCboEmpresa = intCboEmpresa;
	}
	public Integer getIntCboPerfilEmpresa() {
		return intCboPerfilEmpresa;
	}
	public void setIntCboPerfilEmpresa(Integer intCboPerfilEmpresa) {
		this.intCboPerfilEmpresa = intCboPerfilEmpresa;
	}
	public Integer getIntCboEmpresaOrg() {
		return intCboEmpresaOrg;
	}
	public void setIntCboEmpresaOrg(Integer intCboEmpresaOrg) {
		this.intCboEmpresaOrg = intCboEmpresaOrg;
	}
	public Boolean getChkPerfil() {
		return chkPerfil;
	}
	public void setChkPerfil(Boolean chkPerfil) {
		this.chkPerfil = chkPerfil;
	}
	public String getMsgTxtEmpresa() {
		return msgTxtEmpresa;
	}
	public void setMsgTxtEmpresa(String msgTxtEmpresa) {
		this.msgTxtEmpresa = msgTxtEmpresa;
	}
	public String getMsgTxtEstado() {
		return msgTxtEstado;
	}
	public void setMsgTxtEstado(String msgTxtEstado) {
		this.msgTxtEstado = msgTxtEstado;
	}
	public String getMsgTxtPerfil() {
		return msgTxtPerfil;
	}
	public void setMsgTxtPerfil(String msgTxtPerfil) {
		this.msgTxtPerfil = msgTxtPerfil;
	}
	public String getMsgTxtEmpresaOrg() {
		return msgTxtEmpresaOrg;
	}
	public void setMsgTxtEmpresaOrg(String msgTxtEmpresaOrg) {
		this.msgTxtEmpresaOrg = msgTxtEmpresaOrg;
	}
	public String getMsgTxtArchivoOrg() {
		return msgTxtArchivoOrg;
	}
	public void setMsgTxtArchivoOrg(String msgTxtArchivoOrg) {
		this.msgTxtArchivoOrg = msgTxtArchivoOrg;
	}
	public String getMsgTxtDocum() {
		return msgTxtDocum;
	}
	public void setMsgTxtDocum(String msgTxtDocum) {
		this.msgTxtDocum = msgTxtDocum;
	}
	public String getStrCboEmpresaAdm() {
		return strCboEmpresaAdm;
	}
	public void setStrCboEmpresaAdm(String strCboEmpresaAdm) {
		this.strCboEmpresaAdm = strCboEmpresaAdm;
	}
	public String getStrCboPerfilEmpAdm() {
		return strCboPerfilEmpAdm;
	}
	public void setStrCboPerfilEmpAdm(String strCboPerfilEmpAdm) {
		this.strCboPerfilEmpAdm = strCboPerfilEmpAdm;
	}
	public Boolean getChkTodosEnabled() {
		return chkTodosEnabled;
	}
	public void setChkTodosEnabled(Boolean chkTodosEnabled) {
		this.chkTodosEnabled = chkTodosEnabled;
	}
	public String getStrEmpresaAdm() {
		return strEmpresaAdm;
	}
	public void setStrEmpresaAdm(String strEmpresaAdm) {
		this.strEmpresaAdm = strEmpresaAdm;
	}
	public String getStrOrganigrama() {
		return strOrganigrama;
	}
	public void setStrOrganigrama(String strOrganigrama) {
		this.strOrganigrama = strOrganigrama;
	}
	public String getStrCboPerfilAdm() {
		return strCboPerfilAdm;
	}
	public void setStrCboPerfilAdm(String strCboPerfilAdm) {
		this.strCboPerfilAdm = strCboPerfilAdm;
	}
	public Boolean getFormPerfilMofEnabled() {
		return formPerfilMofEnabled;
	}
	public void setFormPerfilMofEnabled(Boolean formPerfilMofEnabled) {
		this.formPerfilMofEnabled = formPerfilMofEnabled;
	}
	public Boolean getStrAdjuntoRendered() {
		return strAdjuntoRendered;
	}
	public void setStrAdjuntoRendered(Boolean strAdjuntoRendered) {
		this.strAdjuntoRendered = strAdjuntoRendered;
	}
	public Boolean getPerfilMofRendered() {
		return perfilMofRendered;
	}
	public void setPerfilMofRendered(Boolean perfilMofRendered) {
		this.perfilMofRendered = perfilMofRendered;
	}
	public Boolean getCboPerfilRendered() {
		return cboPerfilRendered;
	}
	public void setCboPerfilRendered(Boolean cboPerfilRendered) {
		this.cboPerfilRendered = cboPerfilRendered;
	}
	public List<Perfil> getListPerfil() {
		return listPerfil;
	}
	public void setListPerfil(List<Perfil> listPerfil) {
		this.listPerfil = listPerfil;
	}
	public ArrayList<SelectItem> getCboPerfil1() {
		return cboPerfil1;
	}

	public void setCboPerfil1(ArrayList<SelectItem> cboPerfil1) {
		this.cboPerfil1 = cboPerfil1;
	}
}