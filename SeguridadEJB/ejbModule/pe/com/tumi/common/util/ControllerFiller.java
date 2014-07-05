package pe.com.tumi.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.creditos.domain.HojaPlaneamiento;
import pe.com.tumi.creditos.service.impl.HojaPlaneamientoServiceImpl;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Empresa;
import pe.com.tumi.empresa.domain.PerNatural;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.service.impl.EmpresaServiceImpl;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.domain.BeanSesion;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeLocal;
import pe.com.tumi.seguridad.service.impl.AccesoEspecialServiceImpl;
import pe.com.tumi.seguridad.service.impl.AdminMenuServiceImpl;
import pe.com.tumi.seguridad.usuario.domain.Perfil;
import pe.com.tumi.seguridad.usuario.domain.Usuario;
import pe.com.tumi.usuario.service.impl.UsuarioServiceImpl;

public class ControllerFiller extends GenericController {

	private     EmpresaServiceImpl	 		empresaService;
	private     AdminMenuServiceImpl  		adminMenuService;
	private     UsuarioServiceImpl	 		usuarioPerfilService;
	private     AccesoEspecialServiceImpl	accesoEspecialService;
	private     HojaPlaneamientoServiceImpl	hojaPlaneamientoService;
	private		Sucursal beanSucursal 		= new Sucursal();
	//private	    List<Sucursal>				listSucursal = new ArrayList<Sucursal>();
	private	    List<Sucursal>				listaSucursal;
	private	    List<Sucursal>				listSucursalZonal = new ArrayList<Sucursal>();
	private		List<Empresa>				listEmpresa = new ArrayList<Empresa>();
	private		List<Perfil>				listPerfil	= new ArrayList<Perfil>();
	private		List<Usuario>				listUsuario	= new ArrayList<Usuario>();
	private		List<Area>					listAreas	= new ArrayList<Area>();
	private    ArrayList<SelectItem> 		cboEmpresas = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSucursales = new ArrayList<SelectItem>();
	private    List<Sucursal> 				cboSucursalesAuto = new ArrayList<Sucursal>();
	private    List<Sucursal> 				cboSubSucursales = new ArrayList<Sucursal>();
	private    ArrayList<SelectItem> 		cboAreas = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboRespZonal = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSucursalesZonal = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenu1 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenu2 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenu3 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenu4 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuBusq1 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuBusq2 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuBusq3 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuBusq4 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSolMenu1 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSolMenu2 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSolMenu3 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboSolMenu4 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> 		cboMenuPerfil1 = new ArrayList<SelectItem>();
	private    List<Usuario> 				cboUsuarioSoli	 = new ArrayList<Usuario>();
	private    String				 		strRadioMenu1;
	private    String				 		strRadioMenu2;
	private    String				 		strRadioMenu3;
	private    String				 		strRadioMenu4;
	private    String				 		hiddenIdEmpresa;
	private    String				 		hiddenIdEmpresaFormDoc;
	private    String				 		hiddenIdPerfilFormDoc;
	
	private    ArrayList<SelectItem> cboPerfil = new ArrayList<SelectItem>();//Dependiente de otros parámetros 
	private    List<Usuario> 		 cboUsuario = new ArrayList<Usuario>();
	private    ArrayList<SelectItem> cboPerfilUsuario = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> cboPerfiles = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> cboConvenio = new ArrayList<SelectItem>();
	
	
	public void reloadCboMenuPerfil(ValueChangeEvent event) throws DaoException {
		log.info("--------------------Debugging ControlsFiller.reloadCboMenuPerfil()--------------------------");
		UIComponent uiComponent = event.getComponent();
		log.info("uiComponent = "+uiComponent.getId());
		String cboId=uiComponent.getId();
		log.info("cboId: "+cboId);
		Integer idPerfil = (Integer)event.getNewValue();
		setService(adminMenuService);
		
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
    	this.cboMenuPerfil1.clear();
    	this.cboMenu2.clear();
    	this.cboMenu3.clear();
    	this.cboMenu4.clear();
    	this.cboMenuPerfil1=arrayCbo;
		log.info("Saliendo de ControllerFiller.reloadCboMenuPerfil()...");
	}
	
	public void reloadCboMenuEmpresa(ValueChangeEvent event) throws DaoException {
		log.info("--------------------Debugging ControlsFiller.reloadCboMenuEmpresa()--------------------------");
		setService(adminMenuService);
		UIComponent uiComponent = event.getComponent();
		log.info("uiComponent = "+uiComponent.getId());
		String cboId=uiComponent.getId();
		log.info("cboId: "+cboId);
		Integer	idEmpresa = (Integer)event.getNewValue();		
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdPadre", null);
		prmtBusq.put("pIntIdEmpresa", idEmpresa);
		prmtBusq.put("pIntIdPerfil", null);
		prmtBusq.put("pStrNivel", "1");
		
		ArrayList arrayMenu = new ArrayList();
	    arrayMenu = getService().listarMenuPerfil(prmtBusq);
	    log.info("arrayMenu: "+arrayMenu.size());
	    
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
        log.info("arrayCbo.size(): "+arrayCbo.size());
        
    	this.cboMenu1.clear();
    	this.cboMenu2.clear();
    	this.cboMenu3.clear();
    	this.cboMenu4.clear();
    	this.cboMenu1=arrayCbo;
		log.info("Saliendo de ControllerFiller.reloadCboMenuPerfil()...");
	}
	
	public void reloadUsuariosSucursalesxEmpresa(ValueChangeEvent event) throws DaoException {
		reloadCboUsuario(event);
		reloadCboSucursales(event);
	}
	
	public void reloadCboPerfilUsuario(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboPerfilUsuario()-----------------------------");
		setService(usuarioPerfilService);
		String idCboUsuario = (String)event.getNewValue();
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
			log.info("V_DESCRIPCION_PERF = "+hash.get("V_DESCRIPCION_PERF"));
			String strNomPerfil = "" + hash.get("V_DESCRIPCION_PERF");
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
		String idEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:frmUsuario:idCboEmpresa");
		log.info("idEmpresa: "+idEmpresa);
		log.info("BeanSesion.intIdEmpresa: "+BeanSesion.getIntIdEmpresa());
		idEmpresa = (idEmpresa==null)?BeanSesion.getIntIdEmpresa().toString():null;
		Integer idCboSucursal = (Integer) event.getNewValue();
	    log.info("idCboSucursal = "+idCboSucursal);
		this.cboSubSucursales.clear();
		HashMap prmtBusq = new HashMap();
		prmtBusq.put("pIntIdEmpresa", Integer.parseInt(idEmpresa));
		prmtBusq.put("pIntIdSucursal", idCboSucursal);
	    
		ArrayList arraySubSucursales = new ArrayList();
	    arraySubSucursales = getService().listarSubSucursales(prmtBusq);
	    
		setCboSubSucursales(arraySubSucursales);
		log.info("Saliendo de EmpresaController.reloadCboSubSucursales()...");
	}
	
	public void reloadCboPerfiles(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging UsuarioController.reloadCboPerfiles()-----------------------------");
		setService(usuarioPerfilService);
		Integer idCboEmpresa = (Integer)event.getNewValue();
		log.info("idCboEmpresa() = "+idCboEmpresa);
		this.cboPerfil.clear();
		HashMap prmtBusqPerfiles = new HashMap();
		prmtBusqPerfiles.put("pIntIdEmpresa", idCboEmpresa);
	    
		//ArrayList arrayPerfiles = new ArrayList();
	    listPerfil = getService().listarPerfiles(prmtBusqPerfiles);
	    log.info("arrayPerfiles.size(): "+listPerfil.size());
	    
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
		reloadCboSucursales(event);
	}
	
	public void reloadCboUsuarioMenu(ValueChangeEvent event) throws DaoException{
		reloadCboUsuario(event);
		reloadCboMenuEmpresa(event);
	}
	
	public void reloadCboUsuario(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboUsuario()-----------------------------");
		setService(usuarioPerfilService);
		int idCboEmpresa = Integer.parseInt(""+event.getNewValue());
		log.info("idCboEmpresa() = "+idCboEmpresa);
		
		ArrayList<SelectItem> cboUsuariosEmp	 = new ArrayList<SelectItem>();
		HashMap prmtBusqUsuario = new HashMap();
		prmtBusqUsuario.put("pIntIdEmpresa", idCboEmpresa);
	    setHiddenIdEmpresa(""+idCboEmpresa);
	    
		ArrayList<Usuario> arrayUsuarios = new ArrayList<Usuario>();
	    arrayUsuarios = getService().listarUsuariosEmpresa(prmtBusqUsuario);
	    
        UIComponent uiComponent = event.getComponent();
		log.info("uiComponent = "+uiComponent.getId());
		String cboId=uiComponent.getId();
		
		if(cboId.equals("cboEmpSoliBusq")){
			this.cboUsuarioSoli.clear();
			setCboUsuarioSoli(arrayUsuarios);
		}else if(cboId.equals("cboEmpresasSoli")){
			this.cboUsuario.clear();
			setCboUsuario(arrayUsuarios);
		}else{
			this.cboUsuario.clear();
			setCboUsuario(arrayUsuarios);
		}
        
		log.info("Saliendo de UsuarioPerfilController.reloadCboUsuario()...");
	}

	/*public List<Empresa> getCboEmpresas() throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.getCboEmpresas()-----------------------------");
		setService(empresaService);
		this.cboEmpresas.clear();
		HashMap prmtBusqEmpresa = new HashMap();
		
		log.info("Obteniendo array de Empresas.");
		/////////listEmpresa = getService().listarEmpresas(prmtBusqEmpresa);
		log.info("listEmpresa.size(): " + listEmpresa.size());
		return listEmpresa;
	}*/

	public void setCboEmpresas(ArrayList<SelectItem> cboEmpresas) {
		this.cboEmpresas = cboEmpresas;
	}
	
	public ArrayList<SelectItem> getCboSucursales() throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.getCboSucursales()-----------------------------");
		return cboSucursales;
	}
	
	public void setCboSucursales(ArrayList<SelectItem> cboSucursales) {
		this.cboSucursales = cboSucursales;
	}
	
	/**/
	public ArrayList<SelectItem> getCboRespZonal() throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.getCboRespZonal()-----------------------------");
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
	/**/

	public void setCboRespZonal(ArrayList<SelectItem> cboRespZonal) {
		this.cboRespZonal = cboRespZonal;
	}
	
	public ArrayList<SelectItem> getCboSucursalesZonal() throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.getCboSucursalesZonal()-----------------------------");
		setService(empresaService);
		this.cboSucursalesZonal.clear();
		HashMap prmtBusqSucursales = new HashMap();
		prmtBusqSucursales.put("pIntIdpersona", null);
		
		//ArrayList arraySucursales = new ArrayList();
		listSucursalZonal = getService().listarSucursales(prmtBusqSucursales);
	    
	    for(int i=0; i<listSucursalZonal.size() ; i++){
	    	Sucursal suc = (Sucursal) listSucursalZonal.get(i);
	    	log.info("JURI_IDPERSONA_N: "		+ suc.getIntPersPersonaPk());
	    	log.info("JURI_NOMBRECOMERCIAL_V: "	+ suc.getJuridica().getStrNombreComercial());
	    	
	    	this.cboSucursalesZonal.add(new SelectItem(suc.getIntPersPersonaPk(),suc.getJuridica().getStrNombreComercial()));
	    	/*HashMap hash = (HashMap) arraySucursales.get(i);
        	log.info("PERS_IDPERSONA_N = "+hash.get("PERS_IDPERSONA_N"));
			int idPersona = Integer.parseInt("" + hash.get("PERS_IDPERSONA_N"));
			log.info("JURI_NOMBRECOMERCIAL_V = "+hash.get("JURI_NOMBRECOMERCIAL_V"));
			String strNomComer = "" + hash.get("JURI_NOMBRECOMERCIAL_V");
			this.cboSucursalesZonal.add(new SelectItem(idPersona,strNomComer));*/
        }
        SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        this.cboSucursalesZonal.add(0, lblSelect);
	    
		setCboSucursales(cboSucursales);
		log.info("Saliendo de ControllerFiller.getCboSucursalesZonal()...");
		return cboSucursalesZonal;
	}

	public void setCboSucursalesZonal(ArrayList<SelectItem> cboSucursalesZonal) {
		this.cboSucursalesZonal = cboSucursalesZonal;
	}
	
	public void reloadCboSucursales(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboSucursales()-----------------------------");
		setService(empresaService);
		Integer intIdEmpresa = Integer.parseInt(""+event.getNewValue());
		log.info("intIdEmpresa = "+intIdEmpresa);
		this.cboAreas.clear();
		HashMap prmtBusqSucursales = new HashMap();
		prmtBusqSucursales.put("pIntIdempresa", intIdEmpresa);
		List<Sucursal> lista = null;
		//PersonaFacadeRemote facade = null;
		EmpresaFacadeLocal facade = null;
		String csvPkPersona = null;
		try {
			/*lista = getService().listarSucursales(prmtBusqSucursales);
			for(int i=0;i<lista.size();i++){
				if(csvPkPersona == null)
					csvPkPersona = String.valueOf(lista.get(i).getIntPersPersonaPk()); 
				else	
					csvPkPersona = csvPkPersona + "," +lista.get(i).getIntPersPersonaPk();
			}
			facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			listaJuridicaSucursal = facade.getListaJuridicaPorInPk(csvPkPersona);*/
			facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			listaSucursal = facade.getListaSucursalPorPkEmpresa(intIdEmpresa);
			//log.info("listSucursal.size(): " + listaJuridicaSucursal.size());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		//listSucursal = getService().listarSucursales(prmtBusqSucursales);
	}
	
	public void reloadCboAreas(ValueChangeEvent event) throws DaoException {
		try {
			log.info("-----------------------Debugging ControlsFiller.reloadCboAreas()-----------------------------");
			setService(empresaService);
			Integer intIdSucursal = Integer.parseInt(""+event.getNewValue());
			log.info("idSucursal = "+intIdSucursal);
			this.cboAreas.clear();
			HashMap prmtBusqAreas = new HashMap();
			prmtBusqAreas.put("pCboTipoArea", null);
			prmtBusqAreas.put("pCboEstadoArea", null);
			prmtBusqAreas.put("pCboSucursal", intIdSucursal);
			prmtBusqAreas.put("pTxtArea", null);
			prmtBusqAreas.put("pIntIdArea", null);
		    
			ArrayList<Area> arrayAreas = new ArrayList<Area>();
		    arrayAreas = getService().listarAreas(prmtBusqAreas);
		    
			setListAreas(arrayAreas);
			log.info("Saliendo de ControllerFiller.reloadCboAreas()...");
		} catch (Exception e) {
			log.error("Error en reloadCboAreas ---> "+e);
		}
		
	}

	public ArrayList<SelectItem> getCboMenu1() throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.getCboMenu1-----------------------------");
		return cboMenu1;
	}
	
	public void setCboMenu1(ArrayList<SelectItem> cboEmpresas) {
		this.cboEmpresas = cboEmpresas;
	}
	
	public void reloadCboMenu2(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboMenu2()-----------------------------");
		UIComponent uiComponent = event.getComponent();
		this.cboMenu2.clear();
		this.cboMenu3.clear();
		this.cboMenu4.clear();
		ArrayList listaMenu = new ArrayList();
		listaMenu = getListaCboMenu(event);
		this.cboMenu2 = listaMenu;
	}
	
	public void reloadCboMenu3(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboMenu3()-----------------------------");
		UIComponent uiComponent = event.getComponent();
		this.cboMenu3.clear();
		this.cboMenu4.clear();
		ArrayList listaMenu = new ArrayList();
		listaMenu = getListaCboMenu(event);
		this.cboMenu3 = listaMenu;
	}
	
	public void reloadCboMenu4(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboMenu4()-----------------------------");
		UIComponent uiComponent = event.getComponent();
		this.cboMenu4.clear();
		ArrayList listaMenu = new ArrayList();
		listaMenu = getListaCboMenu(event);
		this.cboMenu4 = listaMenu;
	}
	
	public ArrayList getListaCboMenu(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.reloadCboMenu()-----------------------------");
		UIComponent uiComponent = event.getComponent();
		log.info("uiComponent = "+uiComponent.getId());
		String cboId=uiComponent.getId();
		setService(adminMenuService);
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
        arrayCbo.add(0,new SelectItem("0","Seleccionar.."));
		log.info("Saliendo de ControllerFiller.reloadCboMenu()...");
		return arrayCbo;
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
		log.info("-----------------------Debugging ControllerFiller.getCboMenuBusq1-----------------------------");
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
		log.info("-----------------------Debugging ControllerFiller.getCboSolMenu1-----------------------------");
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

	public HojaPlaneamientoServiceImpl getHojaPlaneamientoService() {
		return hojaPlaneamientoService;
	}

	public void setHojaPlaneamientoService(
			HojaPlaneamientoServiceImpl hojaPlaneamientoService) {
		this.hojaPlaneamientoService = hojaPlaneamientoService;
	}

	public Sucursal getBeanSucursal() {
		return beanSucursal;
	}

	public void setBeanSucursal(Sucursal beanSucursal) {
		this.beanSucursal = beanSucursal;
	}

	

	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}

	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	public List<Sucursal> getListSucursalZonal() {
		return listSucursalZonal;
	}

	public void setListSucursalZonal(List<Sucursal> listSucursalZonal) {
		this.listSucursalZonal = listSucursalZonal;
	}

	public List<Empresa> getListEmpresa() throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.getListEmpresa()-----------------------------");
		setService(empresaService);
		HashMap prmtBusqEmpresa = new HashMap();
				
		//ArrayList arrayEmpresas = new ArrayList();
		log.info("Obteniendo array de Empresas.");
		/////////listEmpresa = getService().listarEmpresas(prmtBusqEmpresa);
		log.info("listEmpresa.size(): " + listEmpresa.size());
		
		return listEmpresa;
	}

	public void setListEmpresa(List<Empresa> listEmpresa) {
		this.listEmpresa = listEmpresa;
	}

	public List<Perfil> getListPerfil() throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.getListPerfil()-----------------------------");
		setService(usuarioPerfilService);
		HashMap prmtBusqPerfil = new HashMap();
				
		//ArrayList arrayEmpresas = new ArrayList();
		log.info("Obteniendo array de Perfiles.");
		listPerfil = getService().listarPerfiles(prmtBusqPerfil);
		log.info("listEmpresa.size(): " + listPerfil.size());
		
		return listPerfil;
	}

	public void setListPerfil(List<Perfil> listPerfil) {
		this.listPerfil = listPerfil;
	}

	public List<Usuario> getListUsuario() throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.getCboEmpresas()-----------------------------");
		setService(usuarioPerfilService);
		
		HashMap prmtBusqUsuario = new HashMap();
		
		//El metodo devuelve una sola fila
		log.info("Obteniendo array de Empresas.");
		//ArrayList arrayEmpresas = new ArrayList();
		listUsuario = getService().listarUsuarios(prmtBusqUsuario);
		log.info("listUsuario.size(): " + listUsuario.size());
		
		return listUsuario;
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}

	public String getHiddenIdEmpresa() {
		return hiddenIdEmpresa;
	}

	public void setHiddenIdEmpresa(String hiddenIdEmpresa) {
		this.hiddenIdEmpresa = hiddenIdEmpresa;
	}

	public List<Usuario> getCboUsuario() throws DaoException {
		return cboUsuario;
	}

	public void setCboUsuario(List<Usuario> cboUsuario) {
		this.cboUsuario = cboUsuario;
	}

	public ArrayList<SelectItem> getCboConvenio() throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.getCboConvenio()-----------------------------");
		setCreditosService(hojaPlaneamientoService);
		this.cboConvenio.clear();
		ArrayList arrayConvenio  = new ArrayList();
		HashMap prmtBusqConvenio = new HashMap();
		
		//El metodo devuelve una sola fila
		log.info("Obteniendo array de Convenios.");
		arrayConvenio = getCreditosService().listarConvenio(prmtBusqConvenio);
		log.info("Array de Convenios obtenido.");
        for(int i=0; i<arrayConvenio.size() ; i++){
        	HojaPlaneamiento hojaPlan = (HojaPlaneamiento) arrayConvenio.get(i);
			this.cboConvenio.add(new SelectItem(hojaPlan.getnIdConvenio(),
												""+hojaPlan.getnIdConvenio()));
        }
        SelectItem lblSelect = new SelectItem(0,"Todos..");
        this.cboConvenio.add(0, lblSelect);
		return cboConvenio;
	}

	public void setCboConvenio(ArrayList<SelectItem> cboConvenio) {
		this.cboConvenio = cboConvenio;
	}

	public List<Usuario> getCboUsuarioSoli() {
		return cboUsuarioSoli;
	}

	public void setCboUsuarioSoli(List<Usuario> cboUsuarioSoli) {
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

	public List<Sucursal> getCboSubSucursales() throws DaoException {
		return cboSubSucursales;
	}

	public void setCboSubSucursales(ArrayList<Sucursal> cboSubSucursales) {
		this.cboSubSucursales = cboSubSucursales;
	}
	
	public ArrayList<SelectItem> getCboPerfil() throws DaoException {
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

	public ArrayList<SelectItem> getCboMenuPerfil1() {
		return cboMenuPerfil1;
	}

	public void setCboMenuPerfil1(ArrayList<SelectItem> cboMenuPerfil1) {
		this.cboMenuPerfil1 = cboMenuPerfil1;
	}

	public List<Sucursal> getCboSucursalesAuto() throws DaoException {
		log.info("-----------------------Debugging ControllerFiller.getCboSucursalesAuto()-----------------------------");
		setService(empresaService);
		log.info("BeanSesion.intIdEmpresa: "+BeanSesion.getIntIdEmpresa());
		Integer intIdEmpresa = BeanSesion.getIntIdEmpresa();
		this.cboSucursalesAuto.clear();
		HashMap prmtBusqSucursales = new HashMap();
		prmtBusqSucursales.put("pTxtEmpresaSucursal", "");
		prmtBusqSucursales.put("pTxtSucursal", "");
		prmtBusqSucursales.put("pIntIdempresa", intIdEmpresa);
	    
		ArrayList arraySucursales = new ArrayList();
	    arraySucursales = getService().listarSucursales(prmtBusqSucursales);
	    log.info("arraySucursales.size(): "+arraySucursales.size());
	    
	    setCboSucursalesAuto(arraySucursales);
		log.info("Saliendo de ControllerFiller.getCboSucursalesAuto()...");
		return cboSucursalesAuto;
	}

	public void setCboSucursalesAuto(List<Sucursal> cboSucursalesAuto) {
		this.cboSucursalesAuto = cboSucursalesAuto;
	}

	public List<Area> getListAreas() {
		return listAreas;
	}

	public void setListAreas(List<Area> listAreas) {
		this.listAreas = listAreas;
	}
	
}
