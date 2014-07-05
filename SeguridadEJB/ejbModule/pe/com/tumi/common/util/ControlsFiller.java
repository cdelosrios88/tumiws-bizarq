package pe.com.tumi.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.empresa.domain.PerNatural;
import pe.com.tumi.empresa.service.impl.EmpresaServiceImpl;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.service.PerfilMofService;
import pe.com.tumi.seguridad.service.impl.AccesoEspecialServiceImpl;
import pe.com.tumi.seguridad.service.impl.AdminMenuServiceImpl;
import pe.com.tumi.seguridad.service.impl.PerfilMofServiceImpl;
import pe.com.tumi.seguridad.usuario.domain.Perfil;
import pe.com.tumi.usuario.service.impl.UsuarioServiceImpl;

public class ControlsFiller extends GenericController {

	private    EmpresaServiceImpl	 		empresaService;
	private    AdminMenuServiceImpl  		adminMenuService;
	private    UsuarioServiceImpl	 		usuarioPerfilService;
	private    AccesoEspecialServiceImpl	accesoEspecialService;
	private    PerfilMofServiceImpl	 		perfilMofService;
	private    ArrayList<SelectItem> 		cboEmpresas = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSucursales = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSubSucursales = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboAreas = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboRespZonal = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSucursalesZonal = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenu1 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenu2 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenu3 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenu4 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuPermiso1 = new ArrayList<SelectItem>();	
	private    ArrayList<SelectItem> 		cboMenuPermiso2 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuPermiso3 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuPermiso4 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuBusq1 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuBusq2 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuBusq3 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuBusq4 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSolMenu1 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSolMenu2 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSolMenu3 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSolMenu4 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboUsuarioSoli	 = new ArrayList<SelectItem>();
	private    String				 		strRadioMenu1;
	private    String				 		strRadioMenu2;
	private    String				 		strRadioMenu3;
	private    String				 		strRadioMenu4;
	private    String				 		hiddenIdEmpresa;
	private    String				 		hiddenIdEmpresaFormDoc;
	private    String				 		hiddenIdPerfilFormDoc;
	private    ArrayList<SelectItem> 		cboMenuPerfil1 = new ArrayList<SelectItem>();	
	private    ArrayList<SelectItem> cboPerfil = new ArrayList<SelectItem>(); 
	private    ArrayList<SelectItem> cboUsuario = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> cboPerfilUsuario = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> cboPerfiles = new ArrayList<SelectItem>();
	private    int					 		codEmpresa;
	private		List<Perfil>				listPerfil	= new ArrayList<Perfil>();
	private		List<PerNatural>			listPerNatural	= new ArrayList<PerNatural>();
	
	
	public void reloadCboPerfilUsuario(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging EmpresaController.reloadCboPerfilUsuario()-----------------------------");
		setService(usuarioPerfilService);
		//String idCboUsuario = (String)event.getNewValue();
		String idCboUsuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:idCboUsuario");
		log.info("idCboUsuario() = "+idCboUsuario);
		this.cboPerfilUsuario.clear();
		HashMap prmtBusqPerfil = new HashMap();
		prmtBusqPerfil.put("pIntIdUsuario", Integer.parseInt(idCboUsuario));
		prmtBusqPerfil.put("pIntIdEmpresa", Integer.parseInt(getHiddenIdEmpresa()));
	    
		ArrayList arrayPerfiles = new ArrayList();
	    arrayPerfiles = getService().listarPerfilesxUsuario(prmtBusqPerfil);
	    
	    for(int i=0; i<arrayPerfiles.size() ; i++){
        	HashMap hash = (HashMap) arrayPerfiles.get(i);
        	log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
			int idPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
			log.info("PERF_DESCRIPCION_V = "+hash.get("PERF_DESCRIPCION_V"));
			String strNomPerfil = "" + hash.get("PERF_DESCRIPCION_V");
			this.cboPerfilUsuario.add(new SelectItem(idPerfil,strNomPerfil));
        }
        SelectItem lblSelect = new SelectItem("selectNone","Seleccionar..");
        this.cboPerfilUsuario.add(0, lblSelect);
	    
		setCboPerfilUsuario(cboPerfilUsuario);
		log.info("Saliendo de UsuarioPerfilController.reloadCboPerfilUsuario()...");
	}
	
	public void reloadCboSubSucursales(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging EmpresaController.reloadCboSubSucursales()-----------------------------");
		setService(usuarioPerfilService);
		//String idEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:frmUsuario:idCboEmpresa");		
		String idEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:idCboEmpresa");
		Integer idCboSucursal = (Integer)event.getNewValue();
	    log.info("idEmpresa: "+idEmpresa);
	    log.info("idCboSucursal = "+idCboSucursal);
		this.cboSubSucursales.clear();
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdEmpresa", Integer.parseInt(idEmpresa));
		prmtBusq.put("pIntIdSucursal", idCboSucursal);
	    
		ArrayList arraySubSucursales = new ArrayList();
	    arraySubSucursales = getService().listarSubSucursales(prmtBusq);
	    
	    for(int i=0; i<arraySubSucursales.size() ; i++){
        	HashMap hash = (HashMap) arraySubSucursales.get(i);
        	log.info("SUDE_IDSUBSUCURSAL_N = "+hash.get("SUDE_IDSUBSUCURSAL_N"));
			int idSubSucursal = Integer.parseInt("" + hash.get("SUDE_IDSUBSUCURSAL_N"));
			log.info("SUDE_DESCRIPCION_V = "+hash.get("SUDE_DESCRIPCION_V"));
			String strNomSubsuc = "" + hash.get("SUDE_DESCRIPCION_V");
			this.cboSubSucursales.add(new SelectItem(idSubSucursal,strNomSubsuc));
        }
        SelectItem lblSelect = new SelectItem("selectNone","Seleccionar..");
        this.cboSubSucursales.add(0, lblSelect);
	    
		setCboSubSucursales(cboSubSucursales);
		log.info("Saliendo de EmpresaController.reloadCboSubSucursales()...");
	}
	
	public void reloadCboPerfiles(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging UsuarioController.reloadCboPerfiles()-----------------------------");
		setService(usuarioPerfilService);
		Integer idCboEmpresa = 0;
		if(event != null){
			idCboEmpresa = (Integer)event.getNewValue();
		}else{
			idCboEmpresa = 0;
		}
		log.info("idCboEmpresa() = "+idCboEmpresa);
		this.cboPerfil.clear();
		HashMap prmtBusqPerfiles = new HashMap();
		prmtBusqPerfiles.put("pIntIdEmpresa", idCboEmpresa);
	    
		//ArrayList arrayPerfiles = new ArrayList();
	    listPerfil = getService().listarPerfiles(prmtBusqPerfiles);
	    log.info("listPerfil.size(): "+listPerfil.size());
	    /*
	    for(int i=0; i<arrayPerfiles.size() ; i++){
        	HashMap hash = (HashMap) arrayPerfiles.get(i);
        	log.info("PERF_IDPERFIL_N = "+hash.get("PERF_IDPERFIL_N"));
			int intIdPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
			log.info("PERF_DESCRIPCION_V = "+hash.get("PERF_DESCRIPCION_V"));
			String strDescPerfil = "" + hash.get("PERF_DESCRIPCION_V");
			//per.setStrDescripcion(strDescPerfil);
			this.cboPerfil.add(new SelectItem(intIdPerfil,strDescPerfil));
        }
        SelectItem lblSelect = new SelectItem("selectNone","Seleccionar..");
        this.cboPerfil.add(0, lblSelect);
        
        log.info("this.cboPerfil.size(): "+this.cboPerfil.size());
	    
		setCboPerfil(cboPerfil);
		log.info("Saliendo de UsuarioController.reloadCboPerfil()...");*/
		reloadCboSucursales(event);
	}
	
	public void reloadCboUsuario(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControlsFiller.reloadCboUsuario()-----------------------------");
		setService(usuarioPerfilService);
		int idCboEmpresa = 0;
		if(event!=null){
			idCboEmpresa = Integer.parseInt(""+event.getNewValue());
		}		
		log.info("idCboEmpresa() = "+idCboEmpresa);
		
		ArrayList<SelectItem> cboUsuariosEmp	 = new ArrayList<SelectItem>();
		HashMap prmtBusqUsuario = new HashMap();
		prmtBusqUsuario.put("pIntIdEmpresa", idCboEmpresa);
	    setHiddenIdEmpresa(""+idCboEmpresa);
	    
		ArrayList arrayUsuarios = new ArrayList();
	    arrayUsuarios = getService().listarUsuariosEmpresa(prmtBusqUsuario);
	    
	    for(int i=0; i<arrayUsuarios.size() ; i++){
        	HashMap hash = (HashMap) arrayUsuarios.get(i);
        	log.info("PERS_IDPERSONA_N = "+hash.get("PERS_IDPERSONA_N"));
			int idPersona = Integer.parseInt("" + hash.get("PERS_IDPERSONA_N"));
			log.info("USUA_USUARIO_V = "+hash.get("USUA_USUARIO_V"));
			String strNomUsuario = "" + hash.get("USUA_USUARIO_V");
			cboUsuariosEmp.add(new SelectItem(idPersona,strNomUsuario));
        }
        SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        cboUsuariosEmp.add(0, lblSelect);
	    
        UIComponent uiComponent = event.getComponent();
		log.info("uiComponent = "+uiComponent.getId());
		String cboId=uiComponent.getId();
		
		if(cboId.equals("cboEmpSoliBusq")){
			this.cboUsuarioSoli.clear();
			setCboUsuarioSoli(cboUsuariosEmp);
		}else if(cboId.equals("cboEmpresasSoli")){
			this.cboUsuario.clear();
			setCboUsuario(cboUsuariosEmp);
		}else{
			this.cboUsuario.clear();
			setCboUsuario(cboUsuariosEmp);
		}
        
		log.info("Saliendo de UsuarioPerfilController.reloadCboUsuario()...");
	}

	/*public ArrayList<SelectItem> getCboEmpresas() throws DaoException {
		log.info("-----------------------Debugging ControlsFiller.getCboEmpresas()-----------------------------");
		setService(empresaService);
		this.cboEmpresas.clear();
		ArrayList arrayEmpresas = new ArrayList();
		HashMap prmtBusqEmpresa = new HashMap();
		prmtBusqEmpresa.put("prmIdEmpresa", "");
		prmtBusqEmpresa.put("pTxtEmpresa", "");
		prmtBusqEmpresa.put("pTxtRuc", "");
		prmtBusqEmpresa.put("pCboTipoEmpresa", "0");
		prmtBusqEmpresa.put("pCboEstadoEmpresa", "0");
		
		//El metodo devuelve una sola fila
		log.info("Obteniendo array de Empresas.");
	*/
		/*arrayEmpresas = getService().listarEmpresas(prmtBusqEmpresa);
		log.info("Array de Empresas obtenido.");
        for(int i=0; i<arrayEmpresas.size() ; i++){
        	HashMap hash = (HashMap) arrayEmpresas.get(i);
        	log.info("hash.get(PERS_IDPERSONA_N) = "+hash.get("PERS_IDPERSONA_N"));
        	int intIdPersona=Integer.parseInt(""+hash.get("PERS_IDPERSONA_N"));
        	log.info("hash.get(JURI_RAZONSOCIAL_V) = "+hash.get("JURI_RAZONSOCIAL_V"));
        	String strRazonsocial = ""+hash.get("JURI_RAZONSOCIAL_V");
        	this.cboEmpresas.add(new SelectItem(intIdPersona,strRazonsocial));
        }*/
       /* SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        this.cboEmpresas.add(0, lblSelect);
		return cboEmpresas;
	}*/

	public void setCboEmpresas(ArrayList<SelectItem> cboEmpresas) {
		this.cboEmpresas = cboEmpresas;
	}
	
	public ArrayList<SelectItem> getCboSucursales() throws DaoException {
		log.info("-----------------------Debugging ControlsFiller.getCboSucursales()-----------------------------");
		setService(empresaService);
		//Integer intIdEmpresa = Integer.parseInt(""+event.getNewValue());
		//log.info("intIdEmpresa = "+intIdEmpresa);
		this.cboSucursales.clear();
		HashMap prmtBusqSucursales = new HashMap();
		prmtBusqSucursales.put("pIntIdpersona", null);
		prmtBusqSucursales.put("pTxtEmpresaSucursal", "");
		prmtBusqSucursales.put("pCboTipoSucursal", null);
		prmtBusqSucursales.put("pCboEstadoSucursal", null);
		prmtBusqSucursales.put("pTxtSucursal", "");
		prmtBusqSucursales.put("pIntIdempresa", codEmpresa);
	    
		ArrayList arraySucursales = new ArrayList();
	    arraySucursales = getService().listarSucursales(prmtBusqSucursales);
	    
	    for(int i=0; i<arraySucursales.size() ; i++){
        	HashMap hash = (HashMap) arraySucursales.get(i);
        	log.info("JURI_IDPERSONA_N = "+hash.get("JURI_IDPERSONA_N"));
			int idPersona = Integer.parseInt("" + hash.get("JURI_IDPERSONA_N"));
			log.info("JURI_NOMBRECOMERCIAL_V = "+hash.get("JURI_NOMBRECOMERCIAL_V"));
			String strNomComer = "" + hash.get("JURI_NOMBRECOMERCIAL_V");
			this.cboSucursales.add(new SelectItem(idPersona,strNomComer));
        }
        SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        this.cboSucursales.add(0, lblSelect);
	    
		setCboSucursales(cboSucursales);
		return cboSucursales;
	}
	
	public void setCboSucursales(ArrayList<SelectItem> cboSucursales) {
		this.cboSucursales = cboSucursales;
	}
	
	public ArrayList<SelectItem> getCboRespZonal() throws DaoException {
		log.info("-----------------------Debugging ControlsFiller.getCboRespZonal()-----------------------------");
		setService(empresaService);
		this.cboRespZonal.clear();
		
		HashMap prmtBusqPerNat = new HashMap();
	    ArrayList arrayPerNatural = new ArrayList();
	    //El metodo devuelve una sola fila
		log.info("Obteniendo array de Responsable.");
	    arrayPerNatural = getService().listarPerNatural(prmtBusqPerNat);
		log.info("Array de Responsable obtenido.");
		for(int i=0; i<arrayPerNatural.size(); i++){
			HashMap hash = (HashMap) arrayPerNatural.get(i);
			PerNatural pnatu = new PerNatural();
			log.info("PERS_IDPERSONA_N = "+hash.get("PERS_IDPERSONA_N"));
			int idPersona = Integer.parseInt("" + hash.get("PERS_IDPERSONA_N"));
			pnatu.setIntIdPersona(idPersona);
			log.info("strApepat = "+hash.get("NATU_APELLIDOPATERNO_V"));
			String strApepat = "" + hash.get("NATU_APELLIDOPATERNO_V");
			pnatu.setStrApePat(strApepat);
			log.info("strApemat = "+hash.get("NATU_APELLIDOMATERNO_V"));
			String strApemat = "" + hash.get("NATU_APELLIDOMATERNO_V");
			pnatu.setStrApeMat(strApemat);
			log.info("strNombres = "+hash.get("NATU_NOMBRES_V"));
			String strNombres = "" + hash.get("NATU_NOMBRES_V");
			pnatu.setStrNombres(strApemat);
			
			this.cboRespZonal.add(new SelectItem(idPersona, strNombres+" "+strApepat+" "+strApemat));
		}
        SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        this.cboRespZonal.add(0, lblSelect);
		return cboRespZonal;
	}

	public void setCboRespZonal(ArrayList<SelectItem> cboRespZonal) {
		this.cboRespZonal = cboRespZonal;
	}
	
	public ArrayList<SelectItem> getCboSucursalesZonal() throws DaoException {
		log.info("-----------------------Debugging ControlsFiller.getCboSucursalesZonal()-----------------------------");
		setService(empresaService);
		this.cboSucursalesZonal.clear();
		HashMap prmtBusqSucursales = new HashMap();
		prmtBusqSucursales.put("pIntIdpersona", null);
		prmtBusqSucursales.put("pTxtEmpresaSucursal", "");
		prmtBusqSucursales.put("pCboTipoSucursal", null);
		prmtBusqSucursales.put("pCboEstadoSucursal", null);
		prmtBusqSucursales.put("pTxtSucursal", "");
		prmtBusqSucursales.put("pIntIdempresa", null);
	    
		ArrayList arraySucursales = new ArrayList();
	    arraySucursales = getService().listarSucursales(prmtBusqSucursales);
	    
	    for(int i=0; i<arraySucursales.size() ; i++){
        	HashMap hash = (HashMap) arraySucursales.get(i);
        	log.info("JURI_IDPERSONA_N = "+hash.get("JURI_IDPERSONA_N"));
			int idPersona = Integer.parseInt("" + hash.get("JURI_IDPERSONA_N"));
			log.info("JURI_NOMBRECOMERCIAL_V = "+hash.get("JURI_NOMBRECOMERCIAL_V"));
			String strNomComer = "" + hash.get("JURI_NOMBRECOMERCIAL_V");
			this.cboSucursalesZonal.add(new SelectItem(idPersona,strNomComer));
        }
        SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        this.cboSucursalesZonal.add(0, lblSelect);
	    
		setCboSucursales(cboSucursales);
		log.info("Saliendo de ControlsFiller.getCboSucursalesZonal()...");
		return cboSucursalesZonal;
	}

	public void setCboSucursalesZonal(ArrayList<SelectItem> cboSucursalesZonal) {
		this.cboSucursalesZonal = cboSucursalesZonal;
	}
	
	public void reloadCboSucursales(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControlsFiller.reloadCboSucursales()-----------------------------");
		setService(empresaService);
		Integer intIdEmpresa = Integer.parseInt(""+event.getNewValue());
		log.info("intIdEmpresa = "+intIdEmpresa);
		codEmpresa = intIdEmpresa;
		this.cboSucursales.clear();
		this.cboAreas.clear();
		HashMap prmtBusqSucursales = new HashMap();
		prmtBusqSucursales.put("pIntIdpersona", null);
		prmtBusqSucursales.put("pTxtEmpresaSucursal", "");
		prmtBusqSucursales.put("pCboTipoSucursal", null);
		prmtBusqSucursales.put("pCboEstadoSucursal", null);
		prmtBusqSucursales.put("pTxtSucursal", "");
		prmtBusqSucursales.put("pIntIdempresa", intIdEmpresa);
	    
		ArrayList arraySucursales = new ArrayList();
	    arraySucursales = getService().listarSucursales(prmtBusqSucursales);
	    
	    for(int i=0; i<arraySucursales.size() ; i++){
        	HashMap hash = (HashMap) arraySucursales.get(i);
        	log.info("JURI_IDPERSONA_N = "+hash.get("JURI_IDPERSONA_N"));
			int idPersona = Integer.parseInt("" + hash.get("JURI_IDPERSONA_N"));
			log.info("JURI_NOMBRECOMERCIAL_V = "+hash.get("JURI_NOMBRECOMERCIAL_V"));
			String strNomComer = "" + hash.get("JURI_NOMBRECOMERCIAL_V");
			this.cboSucursales.add(new SelectItem(idPersona,strNomComer));
        }
        SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        this.cboSucursales.add(0, lblSelect);
	    
		setCboSucursales(cboSucursales);
		log.info("Saliendo de ControlsFiller.reloadCboSucursales()...");
	}
	
	public void reloadCboAreas(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControlsFiller.reloadCboAreas()-----------------------------");
		setService(empresaService);
		Integer intIdSucursal = Integer.parseInt(""+event.getNewValue());
		log.info("idSucursal = "+intIdSucursal);
		this.cboAreas.clear();
		HashMap prmtBusqAreas = new HashMap();
		prmtBusqAreas.put("pTxtEmpresa", null);
		prmtBusqAreas.put("pCboTipoArea", null);
		prmtBusqAreas.put("pCboEstadoArea", null);
		prmtBusqAreas.put("pCboSucursal", intIdSucursal);
		prmtBusqAreas.put("pTxtArea", null);
		prmtBusqAreas.put("pIntIdArea", null);
	    
		ArrayList arrayAreas = new ArrayList();
	    arrayAreas = getService().listarAreas(prmtBusqAreas);
	    
	    for(int i=0; i<arrayAreas.size() ; i++){
        	HashMap hash = (HashMap) arrayAreas.get(i);
        	log.info("C_IDAREA = "+hash.get("C_IDAREA"));
			int intIdArea = Integer.parseInt("" + hash.get("C_IDAREA"));
			log.info("AREA_DESCRIPCION_V = "+hash.get("AREA_DESCRIPCION_V"));
			String strNomArea = "" + hash.get("AREA_DESCRIPCION_V");
			this.cboAreas.add(new SelectItem(intIdArea,strNomArea));
        }
        SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        this.cboAreas.add(0, lblSelect);
	    
		setCboAreas(cboAreas);
		log.info("Saliendo de ControlsFiller.reloadCboAreas()...");
	}

	public ArrayList<SelectItem> getCboMenu1() throws DaoException {
		log.info("-----------------------Debugging ControlsFiller.getCboMenu1-----------------------------");
		llenarCboMenu1(this.cboMenu1);
		return cboMenu1;
	}
	
	public void setCboMenu1(ArrayList<SelectItem> cboEmpresas) {
		this.cboEmpresas = cboEmpresas;
	}
	
	public void llenarCboMenu1(ArrayList<SelectItem> cboMenu){
		log.info("-----------------------Debugging ControlsFiller.llenarCboMenu1-----------------------------");
	    setService(adminMenuService);
	    log.info("Se ha seteado el Service");
	    cboMenu.clear();
		
	    log.info("Seteando los parametros de busqueda de seg_m_transacciones: ");
		log.info("-----------------------------------------------------------------------");
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", null);
		prmtBusq.put("pIntIdEmpresa", null);
		prmtBusq.put("pIntIdTipoMenu", null);
		prmtBusq.put("pIntIdEstado", null);
	    
	    ArrayList arrayAdminMenu = new ArrayList();
	    try {
	    	arrayAdminMenu = getService().listarAdminMenu(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarAdminMenu() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<arrayAdminMenu.size(); i++){
			HashMap hash = (HashMap) arrayAdminMenu.get(i);
			AdminMenu menu = new AdminMenu();
			log.info("EMPR_IDEMPRESA_N = "+hash.get("EMPR_IDEMPRESA_N"));
			int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
			menu.setIntIdEmpresa(intIdEmpresa);
			log.info("TRAN_IDTRANSACCION_C = "+hash.get("TRAN_IDTRANSACCION_C"));
			String strIdTransaccion = "" + hash.get("TRAN_IDTRANSACCION_C");
			menu.setStrIdTransaccion(strIdTransaccion);
			log.info("TRAN_IDTRANSACCIONPADRE_C = "+hash.get("TRAN_IDTRANSACCIONPADRE_C"));
			if(hash.get("TRAN_IDTRANSACCIONPADRE_C")!=null){
				String strIdTransaccionPadre = "" + hash.get("TRAN_IDTRANSACCIONPADRE_C");
				menu.setStrIdTransaccionPadre(strIdTransaccionPadre);
			}
			log.info("TRAN_NIVEL_N = "+hash.get("TRAN_NIVEL_N"));
			int intMenuOrden = 0;
			if(hash.get("TRAN_NIVEL_N")!=null){
				intMenuOrden = Integer.parseInt("" + hash.get("TRAN_NIVEL_N"));
				menu.setIntMenuOrden(intMenuOrden);
			}
			log.info("TRAN_NOMBRE_V = "+hash.get("TRAN_NOMBRE_V"));
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			
			if(intMenuOrden==1){
				menu.setStrNombreM1(strNombre);
				cboMenu.add(new SelectItem(menu.getStrIdTransaccion(),menu.getStrNombreM1()));
			}
		}
		
        SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        cboMenu.add(0, lblSelect);
	}
	
	public void reloadCboMenu(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControlsFiller.reloadCboMenu()-----------------------------");
		UIComponent uiComponent = event.getComponent();
		log.info("uiComponent = "+uiComponent.getId());
		String cboId=uiComponent.getId();
		setService(adminMenuService);
		PhaseId id = event.getPhaseId();
		id.toString();
		String idCboMenu = (String)event.getNewValue();
		
		log.info("idCboMenu() = "+idCboMenu);
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", idCboMenu);
	    
		ArrayList arrayMenu = new ArrayList();
	    arrayMenu = getService().listarAdminMenu(prmtBusq);
	    
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
        
        log.info("reloadCboMenu...");
        if((cboId.equals("cboMenu1")) || (cboId.equals("cboMenuPerfil1"))){
        	log.info("Cargando cboMenu1...");
        	this.cboMenu2.clear();
			setStrRadioMenu1(idCboMenu);
        	this.cboMenu2=arrayCbo;
        	this.cboMenu2.add(0, lblSelect);
        	setCboMenu2(cboMenu2);
		}else if(cboId.equals("cboMenu2")){
			log.info("Cargando cboMenu2...");
			this.cboMenu3.clear();
			setStrRadioMenu2(idCboMenu);
			this.cboMenu3=arrayCbo;
			this.cboMenu3.add(0, lblSelect);
			setCboMenu3(cboMenu3);
		}else if(cboId.equals("cboMenu3")){
			log.info("Cargando cboMenu3...");
			this.cboMenu4.clear();
			setStrRadioMenu3(idCboMenu);
			this.cboMenu4=arrayCbo;
			this.cboMenu4.add(0, lblSelect);
			setCboMenu4(cboMenu4);
		}else if(cboId.equals("cboMenuPermiso1")){
			log.info("Cargando cboMenuPermiso1...");
			this.cboMenuPermiso2.clear();
			setStrRadioMenu2(idCboMenu);
			this.cboMenuPermiso2=arrayCbo;
			this.cboMenuPermiso2.add(0, lblSelect);
			setCboMenuPermiso2(cboMenuPermiso2);
		}else if(cboId.equals("cboMenuPermiso2")){
			log.info("Cargando cboMenuPermiso2...");
			this.cboMenuPermiso3.clear();
			setStrRadioMenu3(idCboMenu);
			this.cboMenuPermiso3=arrayCbo;
			this.cboMenuPermiso3.add(0, lblSelect);
			setCboMenuPermiso3(cboMenuPermiso3);
		}else if(cboId.equals("cboMenuPermiso3")){
			log.info("Cargando cboMenuPermiso3...");
			this.cboMenuPermiso4.clear();
			setStrRadioMenu4(idCboMenu);
			this.cboMenuPermiso4=arrayCbo;
			this.cboMenuPermiso4.add(0, lblSelect);
			setCboMenuPermiso4(cboMenuPermiso4);
		}else if(cboId.equals("cboMenuBusq1")){
			log.info("Cargando cboMenuBusq1...");
        	this.cboMenuBusq2.clear();
        	this.cboMenuBusq2=arrayCbo;
        	this.cboMenuBusq2.add(0, lblSelect);
        	setCboMenuBusq2(cboMenuBusq2);
		}else if(cboId.equals("cboMenuBusq2")){
			log.info("Cargando cboMenuBusq2...");
			this.cboMenuBusq3.clear();
			this.cboMenuBusq3=arrayCbo;
			this.cboMenuBusq3.add(0, lblSelect);
			setCboMenuBusq3(cboMenuBusq3);
		}if(cboId.equals("cboMenuBusq3")){
			log.info("Cargando cboMenuBusq3...");
			this.cboMenuBusq4.clear();
			this.cboMenuBusq4=arrayCbo;
			this.cboMenuBusq4.add(0, lblSelect);
			setCboMenuBusq4(cboMenuBusq4);
		}else if(cboId.equals("cboSolMenu1")){
			log.info("Cargando cboSolMenu1...");
        	this.cboSolMenu2.clear();
        	this.cboSolMenu3.clear();
        	this.cboSolMenu4.clear();
        	this.cboSolMenu2=arrayCbo;
        	this.cboSolMenu2.add(0, lblSelect);
        	this.cboSolMenu3.add(0, lblSelect);
        	this.cboSolMenu4.add(0, lblSelect);
        	setCboSolMenu2(cboSolMenu2);
		}else if(cboId.equals("cboSolMenu2")){
			log.info("Cargando cboSolMenu2...");
			this.cboSolMenu3.clear();
			this.cboSolMenu4.clear();
			this.cboSolMenu3=arrayCbo;
			this.cboSolMenu3.add(0, lblSelect);
			this.cboSolMenu4.add(0, lblSelect);
			setCboSolMenu3(cboSolMenu3);
		}if(cboId.equals("cboSolMenu3")){
			log.info("Cargando cboSolMenu3...");
			this.cboSolMenu4.clear();
			this.cboSolMenu4=arrayCbo;
			this.cboSolMenu4.add(0, lblSelect);
			setCboMenuBusq4(cboSolMenu4);
		}
		log.info("Saliendo de ControlsFiller.reloadCboMenu()...");
	}
	
	public void reloadCboMenuPerfil(ValueChangeEvent event) throws DaoException {
		log.info("--------------------Debugging ControlsFiller.reloadCboMenuPerfil()--------------------------");
		UIComponent uiComponent = event.getComponent();
		log.info("uiComponent = "+uiComponent.getId());
		String cboId=uiComponent.getId();
		log.info("cboId: "+cboId);
		setService(adminMenuService);
		PhaseId id = event.getPhaseId();
		id.toString();
		String idCboMenu =(String)event.getNewValue();
		
		if(cboId.equals("idCboPerfilEmpresa")){
			setHiddenIdPerfilFormDoc(idCboMenu==null?"":idCboMenu);
			idCboMenu = "";
		}else {
			//setHiddenIdPerfilFormDoc("");
			idCboMenu = (String)event.getNewValue();
		}
		
		//log.info("idCboMenu(): "+idCboMenu==null||idCboMenu.equals("")?"":idCboMenu);
		//log.info("getHiddenIdEmpresaFormDoc(): "+getHiddenIdEmpresaFormDoc());
		//log.info("getHiddenIdPerfilFormDoc(): "+getHiddenIdPerfilFormDoc()==null||getHiddenIdPerfilFormDoc().equals("")?"1":getHiddenIdPerfilFormDoc());
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", idCboMenu==null||idCboMenu.equals("")?"":idCboMenu);
		prmtBusq.put("pIntIdEmpresa", Integer.parseInt(getHiddenIdEmpresaFormDoc()==null || getHiddenIdEmpresaFormDoc().equals("")?"0":getHiddenIdEmpresaFormDoc()));
		//String cboEmpresa = (getStrCboEmpresa()==null || getStrCboEmpresa().equals("selectNone")?null:getStrCboEmpresa());
		prmtBusq.put("pIntIdPerfil", Integer.parseInt(getHiddenIdPerfilFormDoc()==null||getHiddenIdPerfilFormDoc().equals("")||getHiddenIdPerfilFormDoc().equals("selectNone")?"1":getHiddenIdPerfilFormDoc()));
		prmtBusq.put("pStrNivel", 	 cboId==null||cboId.equals("idCboPerfilEmpresa")?"1":"");
		
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
        
        if(cboId.equals("idCboPerfilEmpresa")){
        	log.info("Cargando cboMenuPerfil1...");
        	this.cboMenuPerfil1.clear();
			setStrRadioMenu1(idCboMenu);
        	this.cboMenuPerfil1=arrayCbo;
        	this.cboMenuPerfil1.add(0, lblSelect);
        	setCboMenuPerfil1(cboMenuPerfil1);
		}
        log.info("reloadCboMenuPerfil...");
        if(cboId.equals("cboMenuPerfil1")){
        	log.info("Cargando cboMenuPerfil1...");
        	log.info("idCboMenu: "+idCboMenu);
        	this.cboMenu2.clear();
			setStrRadioMenu1(idCboMenu);
        	this.cboMenu2=arrayCbo;
        	this.cboMenu2.add(0, lblSelect);
        	setCboMenu2(cboMenu2);
		}else if(cboId.equals("cboMenuPerfil2")){
			log.info("Cargando cboMenu2...");
			this.cboMenu3.clear();
			setStrRadioMenu2(idCboMenu);
			this.cboMenu3=arrayCbo;
			this.cboMenu3.add(0, lblSelect);
			setCboMenu3(cboMenu3);
		}else if(cboId.equals("cboMenuPerfil3")){
			log.info("Cargando cboMenu3...");
			this.cboMenu4.clear();
			setStrRadioMenu3(idCboMenu);
			this.cboMenu4=arrayCbo;
			this.cboMenu4.add(0, lblSelect);
			setCboMenu4(cboMenu4);
		}/*else if(cboId.equals("cboMenuBusq1")){
			log.info("Cargando cboMenuBusq1...");
        	this.cboMenuBusq2.clear();
        	this.cboMenuBusq2=arrayCbo;
        	this.cboMenuBusq2.add(0, lblSelect);
        	setCboMenuBusq2(cboMenuBusq2);
		}else if(cboId.equals("cboMenuBusq2")){
			log.info("Cargando cboMenuBusq2...");
			this.cboMenuBusq3.clear();
			this.cboMenuBusq3=arrayCbo;
			this.cboMenuBusq3.add(0, lblSelect);
			setCboMenuBusq3(cboMenuBusq3);
		}if(cboId.equals("cboMenuBusq3")){
			log.info("Cargando cboMenuBusq3...");
			this.cboMenuBusq4.clear();
			this.cboMenuBusq4=arrayCbo;
			this.cboMenuBusq4.add(0, lblSelect);
			setCboMenuBusq4(cboMenuBusq4);
		}*/
		log.info("Saliendo de ControlsFiller.reloadCboMenu()...");
	}
	
	public ArrayList<SelectItem> getCboMenu2() {
	    return cboMenu2;
	}

	public void setCboMenu2(ArrayList<SelectItem> cboMenu2) {
		this.cboMenu2 = cboMenu2;
	}

	public EmpresaServiceImpl getEmpresaService() {
		return empresaService;
	}

	public void setEmpresaService(EmpresaServiceImpl empresaService) {
		this.empresaService = empresaService;
	}
	
	public AdminMenuServiceImpl getAdminMenuService() {
		return adminMenuService;
	}

	public void setAdminMenuService(AdminMenuServiceImpl adminMenuService) {
		this.adminMenuService = adminMenuService;
	}

	public ArrayList<SelectItem> getCboMenu3() {
		return cboMenu3;
	}

	public void setCboMenu3(ArrayList<SelectItem> cboMenu3) {
		this.cboMenu3 = cboMenu3;
	}

	public ArrayList<SelectItem> getCboMenu4() {
		return cboMenu4;
	}

	public void setCboMenu4(ArrayList<SelectItem> cboMenu4) {
		this.cboMenu4 = cboMenu4;
	}

	public String getStrRadioMenu1() {
		return strRadioMenu1;
	}

	public void setStrRadioMenu1(String strRadioMenu1) {
		this.strRadioMenu1 = strRadioMenu1;
	}

	public String getStrRadioMenu2() {
		return strRadioMenu2;
	}

	public void setStrRadioMenu2(String strRadioMenu2) {
		this.strRadioMenu2 = strRadioMenu2;
	}

	public String getStrRadioMenu3() {
		return strRadioMenu3;
	}

	public void setStrRadioMenu3(String strRadioMenu3) {
		this.strRadioMenu3 = strRadioMenu3;
	}

	public String getStrRadioMenu4() {
		return strRadioMenu4;
	}

	public void setStrRadioMenu4(String strRadioMenu4) {
		this.strRadioMenu4 = strRadioMenu4;
	}

	public ArrayList<SelectItem> getCboMenuBusq1() {
		log.info("-----------------------Debugging ControlsFiller.getCboMenuBusq1-----------------------------");
		llenarCboMenu1(this.cboMenuBusq1);
		return cboMenuBusq1;
	}

	public void setCboMenuBusq1(ArrayList<SelectItem> cboMenuBusq1) {
		this.cboMenuBusq1 = cboMenuBusq1;
	}

	public ArrayList<SelectItem> getCboMenuBusq2() {
		return cboMenuBusq2;
	}

	public void setCboMenuBusq2(ArrayList<SelectItem> cboMenuBusq2) {
		this.cboMenuBusq2 = cboMenuBusq2;
	}

	public ArrayList<SelectItem> getCboMenuBusq3() {
		return cboMenuBusq3;
	}

	public void setCboMenuBusq3(ArrayList<SelectItem> cboMenuBusq3) {
		this.cboMenuBusq3 = cboMenuBusq3;
	}

	public ArrayList<SelectItem> getCboMenuBusq4() {
		return cboMenuBusq4;
	}

	public void setCboMenuBusq4(ArrayList<SelectItem> cboMenuBusq4) {
		this.cboMenuBusq4 = cboMenuBusq4;
	}

	public ArrayList<SelectItem> getCboSolMenu1() {
		log.info("-----------------------Debugging ControlsFiller.getCboSolMenu1-----------------------------");
		llenarCboMenu1(this.cboSolMenu1);
		return cboSolMenu1;
	}

	public void setCboSolMenu1(ArrayList<SelectItem> cboSolMenu1) {
		this.cboSolMenu1 = cboSolMenu1;
	}

	public ArrayList<SelectItem> getCboSolMenu2() {
		return cboSolMenu2;
	}

	public void setCboSolMenu2(ArrayList<SelectItem> cboSolMenu2) {
		this.cboSolMenu2 = cboSolMenu2;
	}

	public ArrayList<SelectItem> getCboSolMenu3() {
		return cboSolMenu3;
	}

	public void setCboSolMenu3(ArrayList<SelectItem> cboSolMenu3) {
		this.cboSolMenu3 = cboSolMenu3;
	}

	public ArrayList<SelectItem> getCboSolMenu4() {
		return cboSolMenu4;
	}

	public void setCboSolMenu4(ArrayList<SelectItem> cboSolMenu4) {
		this.cboSolMenu4 = cboSolMenu4;
	}

	public UsuarioServiceImpl getUsuarioPerfilService() {
		return usuarioPerfilService;
	}

	public void setUsuarioPerfilService(UsuarioServiceImpl usuarioPerfilService) {
		this.usuarioPerfilService = usuarioPerfilService;
	}

	public String getHiddenIdEmpresa() {
		return hiddenIdEmpresa;
	}

	public void setHiddenIdEmpresa(String hiddenIdEmpresa) {
		this.hiddenIdEmpresa = hiddenIdEmpresa;
	}

	public String getHiddenIdEmpresaFormDoc() {
		return hiddenIdEmpresaFormDoc;
	}

	public void setHiddenIdEmpresaFormDoc(String hiddenIdEmpresaFormDoc) {
		this.hiddenIdEmpresaFormDoc = hiddenIdEmpresaFormDoc;
	}

	public String getHiddenIdPerfilFormDoc() {
		return hiddenIdPerfilFormDoc;
	}

	public void setHiddenIdPerfilFormDoc(String hiddenIdPerfilFormDoc) {
		this.hiddenIdPerfilFormDoc = hiddenIdPerfilFormDoc;
	}

	public ArrayList<SelectItem> getCboMenuPerfil1() {
		return cboMenuPerfil1;
	}

	public void setCboMenuPerfil1(ArrayList<SelectItem> cboMenuPerfil1) {
		this.cboMenuPerfil1 = cboMenuPerfil1;
	}

	public ArrayList<SelectItem> getCboUsuario() {
		return cboUsuario;
	}

	public void setCboUsuario(ArrayList<SelectItem> cboUsuario) {
		this.cboUsuario = cboUsuario;
	}

	public ArrayList<SelectItem> getCboUsuarioSoli() {
		return cboUsuarioSoli;
	}

	public void setCboUsuarioSoli(ArrayList<SelectItem> cboUsuarioSoli) {
		this.cboUsuarioSoli = cboUsuarioSoli;
	}

	public ArrayList<SelectItem> getCboAreas() {
		return cboAreas;
	}

	public void setCboAreas(ArrayList<SelectItem> cboAreas) {
		this.cboAreas = cboAreas;
	}

	public AccesoEspecialServiceImpl getAccesoEspecialService() {
		return accesoEspecialService;
	}

	public void setAccesoEspecialService(
			AccesoEspecialServiceImpl accesoEspecialService) {
		this.accesoEspecialService = accesoEspecialService;
	}
	
	public PerfilMofServiceImpl getPerfilMofService() {
		return perfilMofService;
	}

	public void setPerfilMofService(PerfilMofServiceImpl perfilMofService) {
		this.perfilMofService = perfilMofService;
	}

	public ArrayList<SelectItem> getCboSubSucursales() throws DaoException {
		return cboSubSucursales;
	}

	public void setCboSubSucursales(ArrayList<SelectItem> cboSubSucursales) {
		this.cboSubSucursales = cboSubSucursales;
	}
	
	public ArrayList<SelectItem> getCboMenuPermiso1() {
		return cboMenuPermiso1;
	}

	public void setCboMenuPermiso1(ArrayList<SelectItem> cboMenuPermiso1) {
		this.cboMenuPermiso1 = cboMenuPermiso1;
	}

	public ArrayList<SelectItem> getCboMenuPermiso2() {
		return cboMenuPermiso2;
	}

	public void setCboMenuPermiso2(ArrayList<SelectItem> cboMenuPermiso2) {
		this.cboMenuPermiso2 = cboMenuPermiso2;
	}

	public ArrayList<SelectItem> getCboMenuPermiso3() {
		return cboMenuPermiso3;
	}

	public void setCboMenuPermiso3(ArrayList<SelectItem> cboMenuPermiso3) {
		this.cboMenuPermiso3 = cboMenuPermiso3;
	}

	public ArrayList<SelectItem> getCboMenuPermiso4() {
		return cboMenuPermiso4;
	}
	
	public int getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(int codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public List<Perfil> getListPerfil() {
		return listPerfil;
	}

	public void setListPerfil(List<Perfil> listPerfil) {
		this.listPerfil = listPerfil;
	}

	public List<PerNatural> getListPerNatural() throws DaoException {
		log.info("--------------Debugging ControlsFiller.getListPerNatural()-------------------");
		setService(empresaService);
		
		HashMap prmtBusqPerNat = new HashMap();
	    listPerNatural = getService().listarPerNatural(prmtBusqPerNat);
		log.info("listPerNatural.size(): "+listPerNatural.size());
		
		return listPerNatural;
	}

	public void setListPerNatural(List<PerNatural> listPerNatural) {
		this.listPerNatural = listPerNatural;
	}

	public void setCboMenuPermiso4(ArrayList<SelectItem> cboMenuPermiso4) {
		this.cboMenuPermiso4 = cboMenuPermiso4;
	}
	
	public ArrayList<SelectItem> getCboPerfil() throws DaoException {
		log.info("-----------------------Debugging ControlsFiller.getCboPerfil()-----------------------------");
		/*setService(perfilMofService);
		Integer intIdEmpresa = 0;
		Integer intIdEmpresa1 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:idCboEmpresa") == null ? "0" :FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:idCboEmpresa"));
		Integer intIdEmpresa2 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelPerfMofModalPanel:hiddenIdEmpresa") == null ? "0" :FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmRelPerfMofModalPanel:hiddenIdEmpresa"));
		if(intIdEmpresa1 != 0){
			intIdEmpresa = intIdEmpresa1;
		}
		else if (intIdEmpresa2 != 0){
			intIdEmpresa = intIdEmpresa2;
		}
		
		log.info("intIdEmpresa = "+intIdEmpresa);
		this.cboPerfil.clear();
		HashMap prmtBusqPerfil = new HashMap();
		prmtBusqPerfil.put("pIntIdEmpresa", intIdEmpresa);
		ArrayList arrayPerfiles = new ArrayList();
		arrayPerfiles = getService().listarPerfiles(prmtBusqPerfil);

		for(int i=0; i<arrayPerfiles.size() ; i++){
			HashMap hash = (HashMap) arrayPerfiles.get(i);
	
			log.info("N_IDPERFIL_PERF = "+hash.get("N_IDPERFIL_PERF"));
			int idPerfil = Integer.parseInt("" + hash.get("N_IDPERFIL_PERF"));
			log.info("V_DESCRIPCION_PERF = "+hash.get("V_DESCRIPCION_PERF"));
			String strNomPerfil = "" + hash.get("V_DESCRIPCION_PERF");
			this.cboPerfil.add(new SelectItem(idPerfil,strNomPerfil));
		}

		SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
		this.cboPerfil.add(0, lblSelect);
		setCboPerfil(cboPerfil);
		log.info("Saliendo de ControlsFiller.getCboPerfil()...");*/
		return cboPerfil;
	}

	public void setCboPerfil(ArrayList<SelectItem> cboPerfil) {
		this.cboPerfil = cboPerfil;
	}
	
	public ArrayList<SelectItem> getCboPerfilUsuario() {
		return cboPerfilUsuario;
	}

	public void setCboPerfilUsuario(ArrayList<SelectItem> cboPerfilUsuario) {
		this.cboPerfilUsuario = cboPerfilUsuario;
	}

	public ArrayList<SelectItem> getCboPerfiles() throws DaoException {
		log.info("-----------------------Debugging ControlsFiller.getCboPerfiles()-----------------------------");
		setService(usuarioPerfilService);
		//this.cboPerfiles.clear();
		ArrayList arrayPerfiles = new ArrayList();
		HashMap prmtBusqPerfil = new HashMap();
		
		//El metodo devuelve una sola fila
		log.info("Obteniendo array de Perfiles.");
		arrayPerfiles = getService().listarPerfiles(prmtBusqPerfil);
		log.info("Array de Empresas obtenido.");
        for(int i=0; i<arrayPerfiles.size() ; i++){
        	HashMap hash = (HashMap) arrayPerfiles.get(i);
        	log.info("hash.get(PERF_IDPERFIL_N) = "+hash.get("PERF_IDPERFIL_N"));
        	int intIdPerfil = Integer.parseInt(""+hash.get("PERF_IDPERFIL_N"));
        	log.info("hash.get(PERF_DESCRIPCION_V) = "+hash.get("PERF_DESCRIPCION_V"));
        	String strPerfil = ""+hash.get("PERF_DESCRIPCION_V");
        	this.cboPerfiles.add(new SelectItem(intIdPerfil,strPerfil));
        }
        SelectItem lblSelect = new SelectItem("0","Seleccionar..");
        this.cboPerfiles.add(0, lblSelect);
		return cboPerfiles;
	}

	public void setCboPerfiles(ArrayList<SelectItem> cboPerfiles) {
		this.cboPerfiles = cboPerfiles;
	}
}
