package pe.com.tumi.seguridad.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.ConstanteReporte;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.empresa.domain.Empresa;
import pe.com.tumi.empresa.domain.PerNatural;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.file.controller.FileUploadBean;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.domain.DataObjects;
import pe.com.tumi.seguridad.domain.RegistroPc;
import pe.com.tumi.seguridad.domain.ReglamentoPolitica;
import pe.com.tumi.seguridad.domain.SolicitudCambio;
import pe.com.tumi.seguridad.service.ReglamentoPoliticaService;
import pe.com.tumi.seguridad.service.impl.AdminMenuServiceImpl;
import pe.com.tumi.seguridad.service.impl.ReglamentoPoliticaServiceImpl;
import pe.com.tumi.seguridad.usuario.domain.Perfil;

public class ReglamentoPoliticaController extends GenericController {
	private ReglamentoPoliticaServiceImpl 	reglamentoPoliticaService;
	private AdminMenuServiceImpl 			adminMenuService;
	private List 							beanListRegPolMenu = new ArrayList();
	private Integer							intCboEmpresasBusq;
	private Integer							intCboEstadoBusq;
	private String	 						strCboRelMenu1;
	private String 							strCboRelMenu2;
	private String	 						strCboRelMenu3;
	private String	 						strCboRelMenu4;
	private String 							strCboIdEmpresa;
	private Integer							intCboIdPerfil;
	private ReglamentoPolitica				beanRegPol = new ReglamentoPolitica();
	private Boolean 						blnRegPolRendered;
	private List 							itemListRadioMenu = new ArrayList<SelectItem>();
	private String		 					strRadioMenu;
	private Integer 						intDocReglamento = 3;
	private Integer 						intDocPolitica = 4;
	
	//Parámetros de búsqueda para Administración de Documentación
	private Integer		 					intCboEmpresa;
	private Integer		 					intCboEstado;
	private List 							beanListRegPolMenuAdmDoc = new ArrayList();
	public 	List<Perfil>					listPerfil = new ArrayList<Perfil>();
	private ArrayList<SelectItem> 			cboRelMenu1 = new ArrayList<SelectItem>();	

	public void modificarRegPol(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging ReglamentoPoliticaController.modificarRegPol-----------------------------");
		setService(reglamentoPoliticaService);
		String intIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmUpDelRegPol:hiddenIdEmpresa");
		String intIdPerfil = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmUpDelRegPol:hiddenIdPerfil");
		String strIdTransaccion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmUpDelRegPol:hiddenIdTransaccion");
		String strIdEstado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmUpDelRegPol:hiddenIdEstado");
		log.info("intIdEmpresa : "+intIdEmpresa);
		log.info("intIdPerfil : "+intIdPerfil);
		log.info("strIdTransaccion : "+strIdTransaccion);
		log.info("strIdEstado : "+strIdEstado);
		
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdEmpresa", (intIdEmpresa!=null)?Integer.parseInt(intIdEmpresa):null);
		prmtBusq.put("pIntIdPerfil", (intIdPerfil!=null)?Integer.parseInt(intIdPerfil):null);
		prmtBusq.put("pStrIdTransaccion", strIdTransaccion);
		prmtBusq.put("pIntIdEstado", Integer.parseInt(strIdEstado));
		
		ArrayList arrayRegPol = new ArrayList();
	    arrayRegPol = getService().listarRegPol(prmtBusq);
	    log.info("arrayRegPol.size(): "+arrayRegPol.size());
	    
	    ReglamentoPolitica regpol = new ReglamentoPolitica();
	    regpol = (ReglamentoPolitica) arrayRegPol.get(0);
		log.info("regpol.getIntIdEmpresa(): "+regpol.getIntIdEmpresa());
		log.info("regpol.getIntIdEstado(): "+regpol.getIntIdEstado());
		log.info("regpol.getIntIdPerfil(): "+regpol.getIntIdPerfil());
		log.info("regpol.getStrIdTransaccion(): "+regpol.getStrIdTransaccion());
		log.info("regpol.getStrReglamento(): "+regpol.getStrReglamento());
		log.info("regpol.getStrPolitica(): "+regpol.getStrPolitica());
		
		//Check Todos lo perfiles
		if(arrayRegPol.size()>1){
			regpol.setIntIdPerfil(0);
			regpol.setBlnPerfilTodos(true);
		}else{
			regpol.setBlnPerfilTodos(false);
		}
	    
		setBlnRegPolRendered(true);
	    setBeanRegPol(regpol);
	    
	    ValueChangeEvent event1 = null;	
	    reloadCboPerfiles(event1);
		reloadCboMenuPerfil(event1);
	}
	
	public void listarReglamentosPoliticas(ActionEvent event){
		log.info("-----------------------Debugging ReglamentoPoliticaController.listarReglamentosPoliticas-----------------------------");
	    setService(reglamentoPoliticaService);
	    log.info("Se ha seteado el Service");
		
	    log.info("Seteando los parametros de busqueda de seg_m_transacciones: ");
		log.info("-----------------------------------------------------------------------");
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdEmpresa", (getIntCboEmpresasBusq()==0)?null:getIntCboEmpresasBusq());
		prmtBusq.put("pIntIdEstado", (getIntCboEstadoBusq()==0)?null:getIntCboEstadoBusq());
	    
	    ArrayList arrayRegPol = new ArrayList();
	    ArrayList listaRegPol = new ArrayList();
	    try {
	    	arrayRegPol = getService().listarRegPol(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarRegPol() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("arrayRegPol.size(): "+arrayRegPol.size());
		for(int i=0; i<arrayRegPol.size(); i++){
			ReglamentoPolitica regpol = (ReglamentoPolitica) arrayRegPol.get(i);
			log.info("ID_EMPRESA = "+regpol.getIntIdEmpresa());
			log.info("ID_PERFIL = "+regpol.getIntIdPerfil());
			log.info("DESC_PERFIL = "+regpol.getStrPerfil());
			log.info("REGLAMENTO = "+regpol.getStrReglamento());
			log.info("POLITICA = "+regpol.getStrPolitica());
			log.info("TRAN_IDTRANSACCION_C = "+regpol.getStrIdTransaccion());
			log.info("TRAN_IDTRANSACCIONPADRE_C = "+regpol.getStrIdTransaccionPadre());
			log.info("DESC_TRANSACCION"+regpol.getStrTransaccion());
			log.info("TRAN_NIVEL_N = "+regpol.getIntNivelMenu());
			log.info("TIPO_DOC = "+regpol.getIntIdTipoDocumento());
			
			Integer intMenuNivel = regpol.getIntNivelMenu();
			intMenuNivel = (intMenuNivel!=null)?intMenuNivel:0;
			
			String strTransaccion = regpol.getStrTransaccion();
			if(intMenuNivel==1){
				regpol.setStrMenu1(strTransaccion);
			}else if(intMenuNivel==2){
				regpol.setStrMenu2(strTransaccion);
			}else if(intMenuNivel==3){
				regpol.setStrMenu3(strTransaccion);
			}else if(intMenuNivel==4){
				regpol.setStrMenu4(strTransaccion);
			}
			
			listaRegPol.add(regpol);
		}
		
		log.info("listaRegPol.size(): "+listaRegPol.size());
		setBeanListRegPolMenu(listaRegPol);
	}
	
	public void grabarRegPol(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging reglamentoPoliticaController.grabarRegPol-----------------------------");
		setService(reglamentoPoliticaService);
		ReglamentoPolitica regPol = new ReglamentoPolitica();
		regPol = getBeanRegPol();
		log.info("FileUploadBean.getStrNombreReglamento(): "+FileUploadBean.getStrNombreReglamento());
		log.info("FileUploadBean.getStrNombrePolitica(): "+FileUploadBean.getStrNombrePolitica());
		String strReglamento = FileUploadBean.getStrNombreReglamento();
		String strPolitica = FileUploadBean.getStrNombrePolitica();
		String strIdMenu = "";
		log.info("getStrRadioMenu(): "+getStrRadioMenu());
		if(getStrRadioMenu().equals("1")){
			strIdMenu = getStrCboRelMenu1();
		}else if(getStrRadioMenu().equals("2")){
			strIdMenu = getStrCboRelMenu2();
		}else if(getStrRadioMenu().equals("3")){
			strIdMenu = getStrCboRelMenu3();
		}else if(getStrRadioMenu().equals("4")){
			strIdMenu = getStrCboRelMenu4();
		}
		
		Integer idEmpUsuario = 163;
		Integer idUsuario = 2405;
		log.info("strReglamento: "+strReglamento);
		log.info("strPolitica: "+strPolitica);
		log.info("strIdMenu: "+strIdMenu);
		log.info("idEmpUsuario: "+idEmpUsuario);
		log.info("idUsuario: "+idUsuario);
		log.info("regPol.getBlnPerfilTodos(): "+regPol.getBlnPerfilTodos());
		regPol.setStrIdTransaccion(strIdMenu);
		regPol.setIntIdEmpUsu(idEmpUsuario);
		regPol.setIntIdUsuario(idUsuario);
		if(regPol.getBlnPerfilTodos()==true){
			regPol.setIntPerfilTodos(1);
		}else{
			regPol.setIntPerfilTodos(0);
		}
		
		log.info("regPol.getIntIdEmpresa(): "+regPol.getIntIdEmpresa());
		log.info("regPol.getIntIdEstado(): "+regPol.getIntIdEstado());
		log.info("regPol.getIntPerfilTodos(): "+regPol.getIntPerfilTodos());
		log.info("regPol.getIntIdPerfil(): "+regPol.getIntIdPerfil());
		
		//Guardando reglamento
		ReglamentoPolitica reglamento = new ReglamentoPolitica();
		reglamento = regPol;
		reglamento.setIntIdTipoDocumento(intDocReglamento);
		reglamento.setStrArchivo(strReglamento);
		
		getService().grabarRegPol(reglamento);
		log.info("Se ejecutó: getService().grabarRegPol(reglamento)");
		
		//Guardando politica
		ReglamentoPolitica politica = new ReglamentoPolitica();
		politica = regPol;
		politica.setIntIdTipoDocumento(intDocPolitica);
		politica.setStrArchivo(strPolitica);
		
		getService().grabarRegPol(politica);
		setMessageSuccess("Registro de documentos exitoso.");
		log.info("Se ejecutó: getService().grabarRegPol(politica)");
		listarReglamentosPoliticas(event);
	}
	
	public void eliminarRegPol(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging ReglamentoPoliticaController.eliminarRegPol-----------------------------");
		setService(reglamentoPoliticaService);
		String intIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmUpDelRegPol:hiddenIdEmpresa");
		String intIdPerfil = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmUpDelRegPol:hiddenIdPerfil");
		String strIdTransaccion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmUpDelRegPol:hiddenIdTransaccion");
		log.info("intIdEmpresa : "+intIdEmpresa);
		log.info("intIdPerfil : "+intIdPerfil);
		log.info("strIdTransaccion : "+strIdTransaccion);
		
		ReglamentoPolitica regpol = new ReglamentoPolitica();
		regpol.setIntIdEmpresa((intIdEmpresa!=null)?Integer.parseInt(intIdEmpresa):null);
		regpol.setIntIdPerfil((intIdPerfil!=null)?Integer.parseInt(intIdPerfil):null);
		regpol.setStrIdTransaccion(strIdTransaccion);
		
		getService().eliminarRegPol(regpol);
		log.info("Se ha eliminado el documento reglamento/politica.");
		listarReglamentosPoliticas(event);
	}
	
	public void reloadCboPerfiles(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging UsuarioController.reloadCboPerfiles()-----------------------------");
		setService(reglamentoPoliticaService);
		Integer idCboEmpresa = 0;
		if(event!=null){
			idCboEmpresa = (Integer)event.getNewValue();
			log.info("idCboEmpresa() = "+idCboEmpresa);
		}
		HashMap prmtBusqPerfiles = new HashMap();
		prmtBusqPerfiles.put("pIntIdEmpresa", idCboEmpresa);
		
		//ArrayList arrayPerfiles = new ArrayList();
	    listPerfil = getService().listarPerfiles2(prmtBusqPerfiles);
	    log.info("listPerfil.size(): "+listPerfil.size());
	    
	    /*for(int i=0; i<arrayPerfiles.size() ; i++){
	    	HashMap hash = (HashMap) arrayPerfiles.get(i);
        	log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
			int intIdPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
			log.info("PERF_DESCRIPCION_V = "+hash.get("PERF_DESCRIPCION_V"));
			String strDescPerfil = "" + hash.get("PERF_DESCRIPCION_V");
			//per.setStrDescripcion(strDescPerfil);
			this.cboPerfil.add(new SelectItem(intIdPerfil,strDescPerfil));
        }
        SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        this.cboPerfil.add(0, lblSelect);
        log.info("this.cboPerfil.size(): "+this.cboPerfil.size());
		setCboPerfil(cboPerfil);
		log.info("Saliendo de UsuarioController.reloadCboPerfil()...");*/
	}
	
	public void reloadCboMenuPerfil(ValueChangeEvent event) throws DaoException {
		log.info("--------------------Debugging ControlsFiller.reloadCboMenuPerfil()--------------------------");
		setService(adminMenuService);
		String idCboMenu = "";
		String cboId = "";
		Integer idPerfil = 1;
		if(event!=null){
			UIComponent uiComponent = event.getComponent();
			log.info("uiComponent = "+uiComponent.getId());
			cboId=uiComponent.getId();
			log.info("cboId: "+cboId);
			idPerfil = (Integer)event.getNewValue();
		}
		
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", null);
		prmtBusq.put("pIntIdEmpresa", 163);
		prmtBusq.put("pIntIdPerfil", idPerfil==0?null:idPerfil);
		prmtBusq.put("pStrNivel", "1");
		
		ArrayList arrayMenu = new ArrayList();
	    arrayMenu = getService().listarMenuPerfil(prmtBusq);
	    
	    ArrayList<SelectItem> arrayCbo = new ArrayList<SelectItem>();
	    
	    for(int i=0; i<arrayMenu.size() ; i++){
	    	HashMap hash = (HashMap) arrayMenu.get(i);
			AdminMenu menu = new AdminMenu();
			log.info("TRAN_IDTRANSACCION_C = "+hash.get("TRAN_IDTRANSACCION_C"));
			String strIdTransaccion = "" + hash.get("TRAN_IDTRANSACCION_C");
			menu.setStrIdTransaccion(strIdTransaccion);
			log.info("TRAN_NOMBRE_V = "+hash.get("TRAN_NOMBRE_V"));
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			menu.setStrNombre(strNombre);
			arrayCbo.add(new SelectItem(menu.getStrIdTransaccion(),menu.getStrNombre()));
        }
        SelectItem lblSelect = new SelectItem("0","Seleccionar..");
        arrayCbo.add(0, lblSelect);
        
        log.info("Cargando cboMenuPerfil1...");
    	this.cboRelMenu1.clear();
    	this.cboRelMenu1=arrayCbo;
		log.info("Saliendo de ControllerFiller.reloadCboMenuPerfil()...");
	}
	
	public void habilitarGrabarRegPol(ActionEvent event){
		log.info("-----------------------Debugging reglamentoPoliticaController.habilitarGrabarRegPol()-----------------------------");
		setBlnRegPolRendered(true);
		limpiarFormRegPol();
	}
	
	public void limpiarFormRegPol(){
		log.info("-----------------------Debugging reglamentoPoliticaController.limpiarFormRegPol----------------------------------");
		ReglamentoPolitica regPol = new ReglamentoPolitica();
		setBeanRegPol(regPol);
	}
	
	public void cancelarGrabarRegPol(ActionEvent event){
		log.info("-----------------------Debugging reglamentoPoliticaController.cancelarGrabarRegPol-------------------------------------");
		limpiarFormRegPol();
		setBlnRegPolRendered(false);
	}
	
	public void downloadReglamento(ActionEvent event){
		log.info("-----------------------Debugging ReglamentoPoliticaController.downloadReglamento()-----------------------------");
	    String strReglamento = getRequestParameter("strReglamento");
	    log.info("strReglamento: "+strReglamento);
	    
	    downloadArchivo(event, strReglamento);
	}
	
	public void downloadPolitica(ActionEvent event){
		log.info("-----------------------Debugging ReglamentoPoliticaController.downloadPolitica()-----------------------------");
	    String strPolitica = getRequestParameter("strPolitica");
	    log.info("strPolitica: "+strPolitica);
	    
	    downloadArchivo(event, strPolitica);
	}
	
	public void downloadArchivo(ActionEvent event, String fileName){
		log.info("-----------------------Debugging ReglamentoPoliticaController.downloadArchivo()-----------------------------");
	    String strArchivo = fileName;
	    
	    try{
	        setFileName(strArchivo);
	        setPathToFile(ConstanteReporte.RUTA_UPLOADED + strArchivo);
	        super.downloadFile(event);
	        
	    }catch(Exception e){
	        log.info("error  en los archivos " + e.getMessage());
	        setMessageError("No se pudo descargar el archivo, compruebe si existe '" + getFileName()+ "'.");
	    }
	}
	
	public void listarReglamAdmDoc(ActionEvent event){
		log.info("-----------------------Debugging ReglamentoPoliticaController.listarReglamAdmDoc-----------------------------");
	    setService(reglamentoPoliticaService);
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de seg_m_transacciones: ");
		log.info("-----------------------------------------------------------------------");
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdEmpresa", (getIntCboEmpresa()==0)?null:getIntCboEmpresa());
		prmtBusq.put("pIntIdEstado",  (getIntCboEstado()==0)?null:getIntCboEstado());
	    
	    ArrayList arrayRegPolAdm = new ArrayList();
	    ArrayList listaRegPolAdm = new ArrayList();
	    try {
	    	arrayRegPolAdm = getService().listarRegPol(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarRegPol() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("arrayRegPol.size(): "+arrayRegPolAdm.size());
		for(int i=0; i<arrayRegPolAdm.size(); i++){
			ReglamentoPolitica regpol = (ReglamentoPolitica) arrayRegPolAdm.get(i);
			log.info("ID_EMPRESA = "+regpol.getIntIdEmpresa());
			log.info("ID_PERFIL = "+regpol.getIntIdPerfil());
			log.info("DESC_PERFIL = "+regpol.getStrPerfil());
			log.info("REGLAMENTO = "+regpol.getStrReglamento());
			log.info("POLITICA = "+regpol.getStrPolitica());
			log.info("TRAN_IDTRANSACCION_C = "+regpol.getStrIdTransaccion());
			log.info("TRAN_IDTRANSACCIONPADRE_C = "+regpol.getStrIdTransaccionPadre());
			log.info("DESC_TRANSACCION"+regpol.getStrTransaccion());
			log.info("TRAN_NIVEL_N = "+regpol.getIntNivelMenu());
			log.info("TIPO_DOC = "+regpol.getIntIdTipoDocumento());
			log.info("FECHA_REGISTRO = "+regpol.getStrDaFecRegistro());
			
			Integer intMenuNivel = regpol.getIntNivelMenu();
			intMenuNivel = (intMenuNivel!=null)?intMenuNivel:0;
			
			String strTransaccion = regpol.getStrTransaccion();
			if(intMenuNivel==1){
				regpol.setStrMenu1(strTransaccion);
			}else if(intMenuNivel==2){
				regpol.setStrMenu2(strTransaccion);
			}else if(intMenuNivel==3){
				regpol.setStrMenu3(strTransaccion);
			}else if(intMenuNivel==4){
				regpol.setStrMenu4(strTransaccion);
			}
			
			listaRegPolAdm.add(regpol);
		}
		
		log.info("listaRegPol.size(): "+listaRegPolAdm.size());
		setBeanListRegPolMenuAdmDoc(listaRegPolAdm);
	}
	
	public void editRegPol(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging ReglamentoPoliticaController.modificarRegPol-----------------------------");
		setService(reglamentoPoliticaService);
		String intIdEmpresa = getRequestParameter("intIdEmpresa");
		String intIdPerfil = getRequestParameter("intIdPerfil");
		String strIdTransaccion = getRequestParameter("strIdTransaccion");
		log.info("intIdEmpresa : "+intIdEmpresa);
		log.info("intIdPerfil : "+intIdPerfil);
		log.info("strIdTransaccion : "+strIdTransaccion);
		
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdEmpresa", (intIdEmpresa!=null)?Integer.parseInt(intIdEmpresa):null);
		prmtBusq.put("pIntIdPerfil", (intIdPerfil!=null)?Integer.parseInt(intIdPerfil):null);
		prmtBusq.put("pStrIdTransaccion", strIdTransaccion);
		
		ArrayList arrayRegPol = new ArrayList();
	    arrayRegPol = getService().listarRegPol(prmtBusq);
	    log.info("arrayRegPol.size(): "+arrayRegPol.size());
	    
	    ReglamentoPolitica regpol = new ReglamentoPolitica();
	    regpol = (ReglamentoPolitica) arrayRegPol.get(0);
		log.info("regpol.getIntIdEmpresa(): "+regpol.getIntIdEmpresa());
		log.info("regpol.getIntIdEstado(): "+regpol.getIntIdEstado());
		log.info("regpol.getIntIdPerfil(): "+regpol.getIntIdPerfil());
		log.info("regpol.getStrIdTransaccion(): "+regpol.getStrIdTransaccion());
		log.info("regpol.getStrReglamento(): "+regpol.getStrReglamento());
		log.info("regpol.getStrPolitica(): "+regpol.getStrPolitica());
		
		setBeanRegPol(regpol);
	}

	//-----------------------------------------------------------------------------------------------------------------------------
	//Getters y Setters
	//-----------------------------------------------------------------------------------------------------------------------------
	public ReglamentoPoliticaServiceImpl getReglamentoPoliticaService() {
		return reglamentoPoliticaService;
	}

	public void setReglamentoPoliticaService(
			ReglamentoPoliticaServiceImpl reglamentoPoliticaService) {
		this.reglamentoPoliticaService = reglamentoPoliticaService;
	}

	public AdminMenuServiceImpl getAdminMenuService() {
		return adminMenuService;
	}

	public void setAdminMenuService(AdminMenuServiceImpl adminMenuService) {
		this.adminMenuService = adminMenuService;
	}

	public List getBeanListRegPolMenu() {
		return beanListRegPolMenu;
	}

	public void setBeanListRegPolMenu(List beanListRegPolMenu) {
		this.beanListRegPolMenu = beanListRegPolMenu;
	}

	public Integer getIntCboEmpresasBusq() {
		return intCboEmpresasBusq;
	}

	public void setIntCboEmpresasBusq(Integer intCboEmpresasBusq) {
		this.intCboEmpresasBusq = intCboEmpresasBusq;
	}

	public Integer getIntCboEstadoBusq() {
		return intCboEstadoBusq;
	}

	public void setIntCboEstadoBusq(Integer intCboEstadoBusq) {
		this.intCboEstadoBusq = intCboEstadoBusq;
	}

	public String getStrCboRelMenu1() {
		return strCboRelMenu1;
	}

	public void setStrCboRelMenu1(String strCboRelMenu1) {
		this.strCboRelMenu1 = strCboRelMenu1;
	}

	public String getStrCboRelMenu2() {
		return strCboRelMenu2;
	}

	public void setStrCboRelMenu2(String strCboRelMenu2) {
		this.strCboRelMenu2 = strCboRelMenu2;
	}

	public String getStrCboRelMenu3() {
		return strCboRelMenu3;
	}

	public void setStrCboRelMenu3(String strCboRelMenu3) {
		this.strCboRelMenu3 = strCboRelMenu3;
	}

	public String getStrCboRelMenu4() {
		return strCboRelMenu4;
	}

	public void setStrCboRelMenu4(String strCboRelMenu4) {
		this.strCboRelMenu4 = strCboRelMenu4;
	}

	public String getStrCboIdEmpresa() {
		return strCboIdEmpresa;
	}

	public void setStrCboIdEmpresa(String strCboIdEmpresa) {
		this.strCboIdEmpresa = strCboIdEmpresa;
	}

	public Integer getIntCboIdPerfil() {
		return intCboIdPerfil;
	}

	public void setIntCboIdPerfil(Integer intCboIdPerfil) {
		this.intCboIdPerfil = intCboIdPerfil;
	}

	public ReglamentoPolitica getBeanRegPol() {
		return beanRegPol;
	}

	public void setBeanRegPol(ReglamentoPolitica beanRegPol) {
		this.beanRegPol = beanRegPol;
	}

	public Boolean getBlnRegPolRendered() {
		return blnRegPolRendered;
	}

	public void setBlnRegPolRendered(Boolean blnRegPolRendered) {
		this.blnRegPolRendered = blnRegPolRendered;
	}

	public List getItemListRadioMenu() {
		log.info("-----------------------Debugging reglamentoPoliticaController.getItemListRadioMenu()-----------------------------");
		this.itemListRadioMenu.clear();
        
        for(int i=1; i<=4; i++){
        	String strLabel = "";
        	switch(i){
        	case 1 : strLabel="Menu 1"; break;
        	case 2 : strLabel="Menu 2"; break;
        	case 3 : strLabel="Menu 3"; break;
        	case 4 : strLabel="Menu 4"; break;
        	}
        	this.itemListRadioMenu.add(new SelectItem(i,strLabel,"",false,false));
        	SelectItem item = new SelectItem();
        }
        
		return itemListRadioMenu;
	}

	public void setItemListRadioMenu(List itemListRadioMenu) {
		this.itemListRadioMenu = itemListRadioMenu;
	}

	public String getStrRadioMenu() {
		return strRadioMenu;
	}

	public void setStrRadioMenu(String strRadioMenu) {
		this.strRadioMenu = strRadioMenu;
	}

	public Integer getIntCboEmpresa() {
		return intCboEmpresa;
	}

	public void setIntCboEmpresa(Integer intCboEmpresa) {
		this.intCboEmpresa = intCboEmpresa;
	}

	public Integer getIntCboEstado() {
		return intCboEstado;
	}

	public void setIntCboEstado(Integer intCboEstado) {
		this.intCboEstado = intCboEstado;
	}

	public List getBeanListRegPolMenuAdmDoc() {
		return beanListRegPolMenuAdmDoc;
	}

	public void setBeanListRegPolMenuAdmDoc(List beanListRegPolMenuAdmDoc) {
		this.beanListRegPolMenuAdmDoc = beanListRegPolMenuAdmDoc;
	}
	
	public List<Perfil> getListPerfil() {
		return listPerfil;
	}

	public void setListPerfil(List<Perfil> listPerfil) {
		this.listPerfil = listPerfil;
	}

	public ArrayList<SelectItem> getCboRelMenu1() {
		return cboRelMenu1;
	}

	public void setCboRelMenu1(ArrayList<SelectItem> cboRelMenu1) {
		this.cboRelMenu1 = cboRelMenu1;
	}
	
}
