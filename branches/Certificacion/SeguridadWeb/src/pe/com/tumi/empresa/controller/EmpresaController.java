package pe.com.tumi.empresa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.AreaCodigo;
import pe.com.tumi.empresa.domain.AreaCodigoId;
import pe.com.tumi.empresa.domain.AreaId;
import pe.com.tumi.empresa.domain.PerNatural;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.empresa.domain.ZonalId;

import pe.com.tumi.empresa.domain.SucursalCodigo;
import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.empresa.service.impl.EmpresaServiceImpl;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.composite.AreaComp;
import pe.com.tumi.empresa.domain.composite.SucursalComp;
import pe.com.tumi.empresa.domain.composite.ZonalComp;
import pe.com.tumi.popup.controller.DomicilioController;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeLocal;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeLocal;

/************************************************************************/
/* Nombre de la clase: CertificadoController                            */
/* Funcionalidad : Clase que que tiene los parametros de busqueda       */
/* y validaciones de los Certificados                                   */
/* Ref. : 																*/
/* Autor : EDUL 														*/
/* Versión : V1 														*/
/* Fecha creación : 01/07/2011 											*/
/* **********************************************************************/

public class EmpresaController extends GenericController {
	
	//Variables locales
	protected  static Logger 		log 			= Logger.getLogger(EmpresaController.class);
	private    EmpresaServiceImpl	empresaService;	
	private    int                	rows = 5;
	private	   Empresa				beanEmpresa;
	private	   Sucursal				beanSucursal 	= new Sucursal();
	private	   Area					beanArea 		= new Area();
	private	   Zonal				beanZonal 		= new Zonal();
	private	   SucursalCodigo		beanSucursalCodigo = new SucursalCodigo();
	private	   Subsucursal			beanSubsucursal	= new Subsucursal();
	private	   AreaCodigo			beanAreacodigo	= new AreaCodigo();
	       
	private	   List<Empresa>		beanListEmpresas;
	private    List					beanListSucursales;
	private    List					beanListSucurZonal;

	private    List					beanListAreas;
	private    List					beanListZonal;

	private    List					beanListSucursalCodigo = new ArrayList<SucursalCodigo>();
	private    List					beanListSubsucursal = new ArrayList<Subsucursal>();
	private    List					beanListAreacodigo = new ArrayList<AreaCodigo>();
	private    ArrayList<SelectItem> 		cboSucursales = new ArrayList<SelectItem>();	
	private	   List					listEmpresas;
	private	   List					beanListTerceros;
	//private    List<Integer> 		listSucursalCodigo;
	//private    List<Integer> 		listSubsucursal;
	private    List<SucursalCodigo> listSucursalCodigo = new ArrayList<SucursalCodigo>();
	private    List<Subsucursal> 	listSubsucursal = new ArrayList<Subsucursal>();
	private    List<AreaCodigo>		listAreaCodigo;
	private    StringUtils 			langValidator 	= new StringUtils();
	private	   List					listHora;
	private    List					listMinutos;
	private    List					listSegundos;
	private	   SelectItem			itemHora;
	private    String 				strHoraSesion;
	private	   String				strMinutosSesion;
	private	   String				strSegundosSesion;
	private    String 				strHoraAlerta;
	private	   String				strMinutosAlerta;
	private	   String				strSegundosAlerta;
	private	   String				strRadioVigenciaClaves;
	private	   String				strRadioIntentosIngreso;
	
	//Variables del Estado de los Controles
	private    Boolean 				formEmpresaEnabled 	= true;
	private    Integer				cboEmpresa;
	private    Integer				intCboSucursalEmp;
	private    String				txtRuc;
	private    Integer				cboTipoEmpresa;
	private    Integer				cboEstadoEmpresa;
	private	   Integer				intCboEmpresaSuc;
	private    String 				txtNombreZonal;
	private	   String 				txtAbrevZonal;
	private	   Integer				cboTipoSucursal;
	private	   Integer				cboEstadoSucursal;

	private	   Integer 				intCboEmpresaSucursal;
	private	   Integer 				intCboEmpresa;
	private	   Integer 				intCboSucursal;
	//private	   Integer 				intCboEmpresaZonal;
	//private	   String 				strCboSucursalZonal;
	private	   Integer 				intCboRespZonal;
	//private	   Integer 				intCboTipoZonal;
	//private	   Integer 				intCboEstadoZonal;
	//Variables para la inserción de Terceros 
	private	   String 				strNombreTercero;
	private	   Integer				intCboActivoTercero;
	private	   String 				strCodigoTercero;
	//Variables para la inserción de SubSucursales
	private	   String 				strNombreSubSuc;
	private	   int					intCboActivoSubsuc;
	private	   String 				strSubSucAbrev;
	// Parámetros de entrada para el listado de Áreas
	private	   String				strCboEmpresaArea;
	private	   Integer				cboTipoArea;
	private	   Integer				cboEstadoArea;
	private	   Integer				cboSucursal;
	private	   Boolean				chkAreaCodigo;	
	private	   String				txtArea = "";
	private    HtmlSelectOneMenu 	selectMenuBound;
	private    HtmlSelectOneMenu 	selectMenuBound2;
	private	   List					listaEmpresas = new ArrayList();
	private	   List					listaSucursales = new ArrayList();
	//Variables para la inserción de SubSucursales
	private	   String 				strNombreAreaCod;
	private	   String				strCboActivoAreaCod;
	private	   String 				strCodigoArea;
	///////////////////////////////////////////////
	//Parámetros de entrada para el listado de "Zonal"
	private	   Integer				cboEmpresaZonal;
	private	   Integer				cboTipoZonal;
	private	   Integer				cboEstadoZonal;
	private	   String				txtZonal;
	private    int 					intContSucurAnexas;
	private    int 					intContSucurLibres;
	///////////////////////////////////////////////
	
	private	   Boolean				chkTerceroFiltro;
	private	   Boolean				chkSubsucursalFiltro;
	private	   Boolean				chkTercero;
	private	   Boolean				chkSubsucursal;
	private	   Boolean				chkCodigo;	
	private    Boolean 				formSucursalEnabled = true;
	private    Boolean 				formSucursalEnabled2 = true;
	private    Boolean 				formAreaEnabled = true;
	private    Boolean 				formAreaDisabled = false;
	private    Boolean 				formAreaEnabled2 = true;
	private    Boolean 				formZonalDisabled = true;
	
	private	   String 				colorTxtEnabled = "#f4f9ff";
	private    Boolean				empresaRendered = false;
	private    Boolean				sucursalRendered = false;
	private    Boolean				direccionRendered = false;
	private    Boolean				adjuntoRendered = false;
	private    Boolean				areaRendered = false;
	private    Boolean				zonalRendered = false;
	private    String				msgTxtEmpresa = "";
	private    String				msgTxtSucursal = "";
	private    String				msgTxtAbreviatura = "";
	private    String				msgTxtRuc = "";
	private    String				msgTipoEmpresa = "";
	private    String				msgTipoSucursal = "";
	private    String				msgEstadoEmp = "";
	private    String				msgTiempoSesion = "";
	private    String				msgAlertaSesion = "";
	private    String				msgVigenciaClaves = "";
	private    String				msgAlertaCaducidad = "";
	private    String				msgTiempoSesionError = "";
	private    String				msgVigenciaError = "";
	private    String				msgIntentosError = "";
	private    String				msgSinEmpresas = "";
	private    String				msgTxtEmpresaArea = "";
	private    String				msgTxtSucursalArea = "";
	private    String				msgTxtArea = "";
	private    String				msgTxtAbrevArea = "";
	private    String				msgEmpresaZonalError = "";
	private    String 				msgTipoZonalError = "";
	private    String 				msgTipoAreaError = "";	
	private    String 				msgEstadoZonalError = "";
	private    String 				msgEstadoAreaError = "";	
	private    String 				msgNombreZonalError = "";
	private    String 				msgAbrevZonalError = "";
	private    String 				msgRespZonalError = "";
	private    String 				msgSucurSondeoError = "";
	private    String 				msgAreaCodigoError = "";
	private    Boolean				renderSelectError = false;
	private    Boolean				renderTiempoSesionError = false;
	private    Boolean				renderTiempoSesionInvalido = false;
	private    Boolean				renderVigenciaError = false;
	private    Boolean				renderVigenciaInvalido = false;
	private    Boolean				renderIntentosError = false;
	private    String				hiddenIdSucursal;
	private	   String				hiddenIdArea;
	private	   String				hiddenIdEmpArea;
	private	   String				hiddenIdSucArea;
	private	   Boolean				showModalPanelEmpresa = false;
	private	   Boolean				showModalPanelArea = false;
	private	   Boolean				showModalPanelZonal = false;
	
	private	   ArrayList 			ArrayDomicilio = new ArrayList();
	private	   ArrayList 			ArrayTercero   = new ArrayList();
	private	   ArrayList 			ArraySubsucursal = new ArrayList();
	private	   ArrayList 			ArrayAreaCodigo = new ArrayList();
	private	   int					codArea;
	private	   String				msgNomVia;	
	private	   String				msgNumVia;
	private	   String				msgNomZona;
	
	private DomicilioController		domicilioController = (DomicilioController) getSpringBean("domicilioController");
	private Integer intTipoSucursal;
	private Integer intSucursalAnexo;
	private List<Juridica> listaJuridicaEmpresa;
	private List<Sucursal> listaJuridicaSucursal;
	private List<SucursalComp> listaSucursalComp;
	private List<ZonalComp> listaZonalComp;
	private List<Natural> listaNaturalUsuario;
	private List<Sucursal> listaSucursalDeZonal;
	private String strIdZonal;
	private String strTipoMantSucursal;
	private String strTipoMantZonal;
	private String strTipoMantArea;
	private Boolean bolEmpresa;
	private Boolean bolSucursal;
	private Boolean bolArea;
	private Boolean bolZonal;
	//-------------------------------------------------------------------------------------------------------------------------------------------
	//Métodos de EmpresaController para Mantenimientos
	//-------------------------------------------------------------------------------------------------------------------------------------------
	public EmpresaController() {
		strTipoMantArea = "1";
		inicio(null);
		listaJuridicaEmpresa = new ArrayList<Juridica>();
		beanListEmpresas = new ArrayList<Empresa>();
		
	}
	
	public void inicio(ActionEvent event){
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		Usuario usuario = null;
		Integer MENU_EMPRESA = 17;
		Integer MENU_SUCURSAL = 18;
		Integer MENU_AREA = 19;
		Integer MENU_ZONAL = 20;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario != null){
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				id.setIntIdTransaccion(MENU_EMPRESA);
				id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
				PermisoFacadeLocal localPermiso = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				bolEmpresa = (permiso == null)?true:false;
				id.setIntIdTransaccion(MENU_SUCURSAL);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				bolSucursal = (permiso == null)?true:false;
				id.setIntIdTransaccion(MENU_AREA);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				bolArea = (permiso == null)?true:false;
				id.setIntIdTransaccion(MENU_ZONAL);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				bolZonal = (permiso == null)?true:false;
			}else{
				  bolEmpresa = false;
				  bolSucursal= false;
				  bolArea= false;
				  bolZonal= false;
			}
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}		
	}
	
	public String getInicializarPagina(){
		getBeanListEmpresas().clear();		
		
		setCboEmpresa(0);
		getListaJuridicaEmpresa().clear();
		obtenerListaJuridicaEmpresa();
		setTxtRuc("");
		setCboTipoEmpresa(0);
		setCboEstadoEmpresa(0);
		setFormEmpresaEnabled(true);
	    setEmpresaRendered(false);
	    limpiarFormEmpresa();
		
		return "";
	}
	
	
	@Override
	public void init(){
		log.info("-----------------------Debugging EmpresaController.init()-----------------------------");
		ArrayList lsHoras = new ArrayList();
		int intH = 0;
		for(int i=0; i<13; i++){
			i++;
		}
		limpiarFormEmpresa();
		limpiarFormSucursal();
		setListHora(lsHoras);
		setCboEmpresa(null);
		strTipoMantArea = "1";
	}
	
	public void obtenerListaJuridicaEmpresa(){	
		
		try {
			this.listaJuridicaEmpresa.clear();
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			this.listaJuridicaEmpresa = facade.getListaJuridicaDeEmpresa();
			
		}catch (EJBFactoryException e) {
			log.error(e);		
		
		}catch(BusinessException be){
			log.error(be);
		}
		
	}
	
	public List<Juridica> getListaJuridicaEmpresa(){
//		try {
//			if(listaJuridicaEmpresa == null){
//				PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
//				this.listaJuridicaEmpresa = facade.getListaJuridicaDeEmpresa();
//			}
//		} catch (EJBFactoryException e) {
//			log.error(e);
//		}
		return this.listaJuridicaEmpresa;
	}
	public void setListaJuridicaEmpresa(List<Juridica> listaJuridicaEmpresa) {
		this.listaJuridicaEmpresa = listaJuridicaEmpresa;
	}

	public List<Sucursal> getListaJuridicaSucursal() {
		return listaJuridicaSucursal;
	}

	public void setListaJuridicaSucursal(List<Sucursal> listaJuridicaSucursal) {
		this.listaJuridicaSucursal = listaJuridicaSucursal;
	}

	public void reloadCboSucursales(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging EmpresaController.reloadCboSucursales-----------------------------");

		setService(empresaService);
		Integer intIdEmpresa = Integer.parseInt(""+event.getNewValue());
		log.info("intIdEmpresa = "+intIdEmpresa);
		List<Sucursal> lista = null;
		EmpresaFacadeLocal facade = null;
		try {
			facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			listaJuridicaSucursal = facade.getListaSucursalPorPkEmpresa(intIdEmpresa);
			log.info("listSucursal.size(): " + listaJuridicaSucursal.size());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		//listSucursal = getService().listarSucursales(prmtBusqSucursales);
	}

	public void grabarEmpresa(ActionEvent event) throws ParseException, BusinessException{
		
		  log.info("-----------------------Debugging EmpresaController.grabarEmpresa-----------------------------");

	      log.info("Se ha seteado el Service");
	      Empresa emp = new Empresa();
	      
	      //Obteniendo beanEmpresa
	      emp = (Empresa) getBeanEmpresa();
	      log.info("Obteniendo beanEmpresa...");
	      log.info("intIdEmpresa: "+emp.getIntIdEmpresa());
	      log.info("strRazonSocial: "+emp.getJuridica().getStrRazonSocial());
	      log.info("strSiglas: "+emp.getJuridica().getStrSiglas());
	      log.info("strRuc: "+emp.getJuridica().getPersona().getStrRuc());
	      log.info("intTipoConformacionCod: "+emp.getIntTipoConformacionCod());
	      log.info("intEstadoCod: "+emp.getIntEstadoCod());
	      log.info("strHoraSesion:StrMinutosSesion:StrSegundosSesion: "+getStrHoraSesion()+":"+getStrMinutosSesion()+":"+getStrSegundosSesion());
	      log.info("strTiempoSesion: "+emp.getStrTiempoSesion());
	      log.info("strHoraAlerta:StrMinutosAlerta:StrSegundosAlerta: "+getStrHoraAlerta()+":"+getStrMinutosAlerta()+":"+getStrSegundosAlerta());
	      log.info("strAlertaSesion: "+emp.getStrAlertaSesion());
	      log.info("getStrRadioVigenciaClaves(): "+getStrRadioVigenciaClaves());
	      log.info("intVigenciaClaves: "+emp.getIntVigenciaClaves());
	      log.info("intAlertaCaducidad: "+emp.getIntAlertaCaducidad());
	      log.info("getStrRadioIntentosIngreso(): "+getStrRadioIntentosIngreso());
	      log.info("intIntentosIngreso: "+emp.getIntIntentosIngreso());
	      log.info("blnControlHoraIngreso: "+emp.getBlnControlHoraIngreso());
	      log.info("blnControlRegistro: "+emp.getBlnControlRegistro());
	      log.info("blnControlCambioClave: "+emp.getBlnControlCambioClave());
	      log.info("intTipoPersonaCod: "+emp.getJuridica().getPersona().getIntTipoPersonaCod());
	      
	      //Validaciones de los campos
	      String validHeader="Verificar los siguientes campos al grabar:";
	      Boolean bValidar = true;
	      String validRazonsocial = emp.getJuridica().getStrRazonSocial();
	      if(validRazonsocial.equals("")){
	    	  setMsgTxtEmpresa("* Debe completar el campo Empresa.");
	    	  bValidar = false;
	    	  
	      }else{
	    	  setMsgTxtEmpresa("");
	      }
	      String validSiglas = emp.getJuridica().getStrSiglas();
	      if(validSiglas.equals("")){
	    	  setMsgTxtAbreviatura("* Debe completar el campo Abreviatura.");
	    	  bValidar = false;
	      }else{
	    	  setMsgTxtAbreviatura("");
	    	  validSiglas="";
	      }
	      String validRuc = emp.getJuridica().getPersona().getStrRuc();
	      if(!(validRuc.length()==11)||!StringUtils.isNumeric(validRuc)){
	    	  setMsgTxtRuc("* Ruc debe ser numérico de 11 caracteres.");
	    	  bValidar = false;
	      }else{
	    	  setMsgTxtRuc("");
	    	  validRuc="";
	      }
	      int intTipoEmp = emp.getIntTipoConformacionCod();
	      if(intTipoEmp==0){
	    	  setMsgTipoEmpresa("* Debe seleccionar un tipo de empresa.");
	    	  bValidar = false;
	      }else{
	    	  setMsgTipoEmpresa("");
	      }
	      int intEstadoEmp = emp.getIntEstadoCod();
	      if(intEstadoEmp==0){
	    	  setMsgEstadoEmp("* Debe seleccionar un estado de empresa.");
	    	  bValidar = false;
	      }else{
	    	  setMsgEstadoEmp("");
	      }
	      
	      if(intTipoEmp==0||intEstadoEmp==0){
	    	  setRenderSelectError(true);
	    	  log.info("renderSelectError = "+getRenderSelectError());
	      }else{
	    	  setRenderSelectError(false);
	    	  log.info("renderSelectError = "+getRenderSelectError());
	      }
	      
	      String strSesionTime = getStrHoraSesion()+":"+getStrMinutosSesion()+":"+getStrSegundosSesion();
	      Boolean isValidTiempoSesion = validarFormatoHHMMSS(strSesionTime);
	      if(isValidTiempoSesion==false){
	    	  setMsgTiempoSesion("* \"Tiempos de Sesión\" debe tener el formato hh:mm:ss.");
	    	  bValidar = false;
	      }else{
	    	  setMsgTiempoSesion("");
	      }
	      emp.setStrTiempoSesion(strSesionTime);
	      
	      String strAlertTime = getStrHoraAlerta()+":"+getStrMinutosAlerta()+":"+getStrSegundosAlerta();
	      Boolean isValidAlertaSesion = validarFormatoHHMMSS(strAlertTime);
	      if(isValidAlertaSesion==false){
	    	  setMsgAlertaSesion("* \"Alerta de Sesión\" debe tener el formato hh:mm:ss.");
	    	  bValidar = false;
	      }else{
	    	  setMsgAlertaSesion("");
	      }
	      emp.setStrAlertaSesion(strAlertTime);
	      
	      if(isValidTiempoSesion==false||isValidAlertaSesion==false){
	    	  setRenderTiempoSesionError(true);
	    	  setMsgTiempoSesionError("");
	    	  setRenderTiempoSesionInvalido(false);
	      }else{
	    	  setRenderTiempoSesionError(false);
	    	  
	    	  if(!compararTiemposHHMMSS(emp.getStrTiempoSesion(),emp.getStrAlertaSesion())){
		    	  setMsgTiempoSesionError("* El tiempo en \"Alerta de Sesión\" debe ser menor que en \"Tiempos de Sesión\".");
		    	  bValidar = false;
		    	  setRenderTiempoSesionInvalido(true);
		    	  log.info("renderTiempoSesionInvalido = "+getRenderTiempoSesionInvalido());
		      }else{
		    	  setMsgTiempoSesionError("");
		    	  setRenderTiempoSesionInvalido(false);
		    	  log.info("renderTiempoSesionInvalido = "+getRenderTiempoSesionInvalido());
		      }
	      }
	      
	      //Valida los radio de Vigencia e Intentos de Ingreso
	      if(getStrRadioVigenciaClaves().equals("indeterminado")){
	    	  emp.setIntVigenciaClaves(null);
	    	  emp.setIntAlertaCaducidad(null);
	      }
	      if(getStrRadioIntentosIngreso().equals("indeterminado")){
	    	  emp.setIntIntentosIngreso(null);
	      }
	      
	      Integer validVigenciaClaves = emp.getIntVigenciaClaves();
	      if(validVigenciaClaves!=null){
	    	  if(StringUtils.isNumeric(""+validVigenciaClaves)){
	    		  setMsgVigenciaClaves("");
		      }else{
		    	  setMsgVigenciaClaves("* \"Vigencia de Claves\" debe ser numèrico.");
		    	  bValidar = false;
		      }
	      }
	      
	      Integer validAlertacaducidad = emp.getIntAlertaCaducidad();
	      if(validAlertacaducidad!=null){
	    	  if(StringUtils.isNumeric(""+validAlertacaducidad)){
	    		  setMsgAlertaCaducidad("");
		      }else{
		    	  setMsgAlertaCaducidad("* \"Alerta de Caducidad\" debe ser numèrico.");
		    	  bValidar = false;
		      }
	      }
	      
	      if(validAlertacaducidad!=null && validAlertacaducidad!=null && (validVigenciaClaves<validAlertacaducidad)){
	    	  setMsgVigenciaError("El tiempo en \"Alerta de Caducidad\" debe ser menor que en \"Vigencia de Claves\".");
	    	  bValidar = false;
	    	  setRenderVigenciaInvalido(true);
	      }else{
	    	  setRenderVigenciaInvalido(false);
	      }
	      
	      if(validVigenciaClaves!=null && validAlertacaducidad!=null){
	    	  setRenderVigenciaError(false);
    	  }else{
    		  setRenderVigenciaError(true);  
    	  }
	      
	      Integer validIntentosIngreso = emp.getIntIntentosIngreso();
	      if(validIntentosIngreso!=null){
	    	  if(StringUtils.isNumeric(""+validIntentosIngreso)){
	    		  setMsgIntentosError("");
	    		  setRenderIntentosError(false);
		      }else{
		    	  setMsgIntentosError("* \"Intentos de Ingreso\" debe ser numérico.");
		    	  bValidar = false;
		    	  setRenderIntentosError(true);
		      }
	      }else{
	    	  setRenderIntentosError(false);
	      }
	      
	      //Parametros Generales
	      if(emp.getBlnControlHoraIngreso()==true){
	    	emp.setIntControlHoraIngreso(1);  
	      }else{
	    	  emp.setIntControlHoraIngreso(0);  
	      }
	      
	      if(emp.getBlnControlRegistro()==true){
	    	  emp.setIntControlRegistro(1);
	      }else{
	    	  emp.setIntControlRegistro(0);
	      }
	      
	      if(emp.getBlnControlCambioClave()==true){
	    	  emp.setIntControlCambioClave(1);
	      }else{
	    	  emp.setIntControlCambioClave(0);
	      }
	      
	      //Valores por default
	      emp.getJuridica().getPersona().setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_JURIDICA); //es Persona Juridica
	      emp.getJuridica().getPersona().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO); //es Estado de Persona Activo
	    
	    if(bValidar==true){
		    try {
		    	PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				personaFacade.grabarEmpresa(emp);
			} catch (EJBFactoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			setMessageSuccess("Los datos se actualizaron satisfactoriamente ");
			listarEmpresas(event);
			setFormEmpresaEnabled(true);
		    setEmpresaRendered(false);
		    limpiarFormEmpresa();
		    setCboEmpresa(0);
		    setCboTipoEmpresa(0);
		    setTxtRuc("");
		    setCboEstadoEmpresa(0);		    
		    obtenerListaJuridicaEmpresa();
		}
		
	}	
	
	public void getListaResponsableySucursalDeZonal(ActionEvent event) throws ParseException, DaoException{
		EmpresaFacadeLocal facade = null;
		Integer intIdEmpresa = null;
		try {
			intIdEmpresa = beanZonal.getId().getIntPersEmpresaPk();
			if(intIdEmpresa!=0){
				facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
				listaNaturalUsuario = facade.getListaNaturalDeUsuarioPorIdEmpresa(intIdEmpresa);
				//if(beanZonal.getId()!=null && beanZonal.getId().getIntIdzonal()!=null)
				beanZonal.setListaSucursal(facade.getListaSucursalSinZonalPorPkEmpresa(intIdEmpresa));
				listaSucursalDeZonal = null; 
			}else{
				beanZonal.setIntPersPersonaResponsablePk(null);
				listaNaturalUsuario = null;
				beanZonal.getSucursal().getId().setIntIdSucursal(null);
				beanZonal.setListaSucursal(null);
				listaSucursalDeZonal = null;
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void getListaSucursalPorTipo(ActionEvent event){
		EmpresaFacadeLocal facade = null;
		int intNroAnexadas = 0;
		int intNroLibres = 0;
		Integer intIdEmpresa = null;
		try {
			intIdEmpresa = beanZonal.getId().getIntPersEmpresaPk();
			if(intIdEmpresa!=null && intIdEmpresa != 0){
				if(intTipoSucursal != 0){
					facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
					if(strTipoMantZonal.equals(Constante.MANTENIMIENTO_GRABAR))
						listaSucursalDeZonal = facade.getListaSucursalZonalPorPkEmpresaYTipo(intIdEmpresa,intTipoSucursal);
					else if(strTipoMantZonal.equals(Constante.MANTENIMIENTO_MODIFICAR))
						listaSucursalDeZonal = facade.getListaSucursalZonalPorPkEmpresaYIdZonalYTipo(intIdEmpresa,
																							 beanZonal.getId().getIntIdzonal(),
																							 intTipoSucursal);
					for(int i=0;i<listaSucursalDeZonal.size();i++){
						if(listaSucursalDeZonal.get(i).getZonal()!=null){
							intNroAnexadas++;
						}else{
							intNroLibres++;
						}
					}
				}else{
					listaSucursalDeZonal = null;
					intSucursalAnexo = 0;
				}
			}else
				beanZonal.getId().setIntPersEmpresaPk(0);
			setIntContSucurAnexas(intNroAnexadas);
			setIntContSucurLibres(intNroLibres);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void getListaSucursalPorTipoYAnexo(ActionEvent event){
		EmpresaFacadeLocal facade = null;
		int intNroAnexadas = 0;
		int intNroLibres = 0;
		Integer intIdEmpresa = null;
		try {
			intIdEmpresa = beanZonal.getId().getIntPersEmpresaPk();
			if(intIdEmpresa !=null && intIdEmpresa!= 0){
				if(intSucursalAnexo!=0 && intTipoSucursal != 0){
					facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
					if(intSucursalAnexo== Constante.PARAM_T_SUCURSALZONAL_ANEXADA){
						if(strTipoMantZonal.equals(Constante.MANTENIMIENTO_GRABAR))
							listaSucursalDeZonal = facade.getListaSucursalZonalPorPkEmpresaYTipoDeAne(intIdEmpresa,intTipoSucursal);
						else if(strTipoMantZonal.equals(Constante.MANTENIMIENTO_MODIFICAR))
							listaSucursalDeZonal = facade.getListaSucursalZonalPorPkEmpresaYIdZonalYTipoDeAne(intIdEmpresa,
																									  beanZonal.getId().getIntIdzonal(),
																									  intTipoSucursal);
						if(listaSucursalDeZonal!=null)
						intNroAnexadas = listaSucursalDeZonal.size(); 
					}else{
						if(strTipoMantZonal.equals(Constante.MANTENIMIENTO_GRABAR))
							listaSucursalDeZonal = facade.getListaSucursalZonalPorPkEmpresaYTipoDeLib(intIdEmpresa,intTipoSucursal);
						else if(strTipoMantZonal.equals(Constante.MANTENIMIENTO_MODIFICAR))
							listaSucursalDeZonal = facade.getListaSucursalZonalPorPkEmpresaYIdZonalYTipoDeLib(intIdEmpresa,
																									  beanZonal.getId().getIntIdzonal(),	
																									  intTipoSucursal);
						if(listaSucursalDeZonal!=null)
						intNroLibres = listaSucursalDeZonal.size();
					}
				}else{
					intSucursalAnexo = 0;
				}
			}else{
				beanZonal.getId().setIntPersEmpresaPk(0);
				intSucursalAnexo = 0;
				intTipoSucursal = 0;
			}
			setIntContSucurAnexas(intNroAnexadas);
			setIntContSucurLibres(intNroLibres);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		} 
		setMessageSuccess("Listado de sucursales ha sido satisfactorio");
		setIntContSucurAnexas(intNroAnexadas);
		setIntContSucurLibres(intNroLibres);
	}
	
	private Boolean isValidoZonal(Zonal beanZonal){
		Boolean validZonal = true;
	    
	    if(beanZonal.getId().getIntPersEmpresaPk()==0){
	    	setMsgEmpresaZonalError("Debe seleccionar una Empresa.");
	    	validZonal = false;
	    }else{
	    	setMsgEmpresaZonalError("");
	    }
	    if(beanZonal.getIntIdTipoZonal()==0){
	    	setMsgTipoZonalError("Debe seleccionar un Tipo de Zonal.");
	    	validZonal = false;
	    }else{
	    	setMsgTipoZonalError("");
	    }
	    if(beanZonal.getIntIdEstado()==0){
	    	setMsgEstadoZonalError("Debe seleccionar un Estado de Zonal.");
	    	validZonal = false;
	    }else{
	    	setMsgEstadoZonalError("");
	    }
	    if(beanZonal.getJuridica().getStrRazonSocial().trim().equals("")){
	    	setMsgNombreZonalError("Debe un escribir un nombre de Zonal.");
	    	validZonal = false;
	    }else{
	    	setMsgNombreZonalError("");
	    }
	    if(beanZonal.getJuridica().getStrSiglas().trim().equals("")){
	    	setMsgAbrevZonalError("Debe escribir una abreviatura para la Zonal.");
	    	validZonal = false;
	    }else{
	    	setMsgAbrevZonalError("");
	    }
	    if(beanZonal.getIntPersPersonaResponsablePk()==0){
	    	setMsgRespZonalError("Debe seleccionar un responsable para la zonal.");
	    	validZonal = false;
	    }else{
	    	setMsgRespZonalError("");
	    }
	    if(beanZonal.getSucursal().getId().getIntIdSucursal()== 0){
	    	setMsgSucurSondeoError("Debe seleccionar una sucursal de sondeo.");
	    	validZonal = false;
	    }else{
	    	setMsgSucurSondeoError("");
	    }
	    return validZonal;
	}
	
	public void grabarZonal(ActionEvent event) throws ParseException, DaoException{
		/*
		log.info("-----------------------Debugging EmpresaController.grabarZonal-----------------------------");
	    setService(empresaService);
	    log.info("Se ha seteado el Service");
	    Zonal zon = new Zonal();
	    zon = (Zonal) getBeanZonal();
	    
	    log.info("zon.getIntIdZonal() = "		+zon.getIntIdZonal());
	    log.info("zon.getStrNombreZonal() = "	+zon.getStrNombreZonal());
	    log.info("zon.getStrAbreviatura() = "	+zon.getStrAbreviatura());
	    log.info("getStrCboEmpresaZonal() = "	+getIntCboEmpresaZonal());
	    log.info("getStrCboTipoZonal() = "	 	+getIntCboTipoZonal());
	    log.info("getStrCboRespZonal() = "	 	+getIntCboRespZonal());
	    log.info("getStrCboEstadoZonal() = " 	+getIntCboEstadoZonal());
	    log.info("zon.getIntIdZonalOut() = " 	+zon.getIntIdZonalOut());
	    */
	    
	   EmpresaFacadeLocal facade = null;
	    if(isValidoZonal(beanZonal) == false){
	    	log.info("Datos de zonal no válidos. Se aborta el proceso de grabación de zonal.");
	    	return;
	    }
	    /*
	    zon.setIntIdEmpresa(getIntCboEmpresaZonal());
	    zon.setIntIdPersona(getIntCboEmpresaZonal());
	    zon.setIntIdTipoZonal(getIntCboTipoZonal());
	    zon.setIntIdResponsable(getIntCboRespZonal());
	    zon.setIntIdSucursal(Integer.parseInt(getStrCboSucursalZonal()));
	    zon.setIntIdEstadoZonal(getIntCboEstadoZonal());
	    */
	      
    	try {
			facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			facade.grabarZonalYListaSucursal(beanZonal, listaSucursalDeZonal);
			//getService().grabarZonal(zon);
			setMessageSuccess("Los datos de zonal se actualizaron satisfactoriamente ");
			limpiarFormZonal();
			listarZonal(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	    	
		/*
		log.info("zon.getIntIdZonalOut(): "+zon.getIntIdZonalOut());
		if(zon.getIntIdZonalOut()!=0){
			zon.setIntIdZonal(zon.getIntIdZonalOut());
		}
		
		//Registrar Detalle de Sucursal Zonal
		ArrayList arraySucZonal = new ArrayList();
		arraySucZonal = (ArrayList) getBeanListSucurZonal();
		*/
		//String strVal = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:frmZonal:tblSucurZonal:0:chkActivacion");
		//log.info("strVal: "+strVal);
		/*
		log.info("arraySucZonal.size(): "+arraySucZonal.size());
		log.info("Id de Zonal: "+zon.getIntIdZonal());
		for(int i=0; i<arraySucZonal.size(); i++){
			Sucursal suc = new Sucursal();
			suc = (Sucursal)arraySucZonal.get(i);
			//if(checks[i]==true){
				//suc.setIntIdZonal(zon.getIntIdZonal());
			//}
			if(suc.getBlnSucurActiv()==true){
				suc.setIntIdZonal(zon.getIntIdZonal());
			}
			log.info("Id de Sucursal: "+suc.getIntIdsucursal());
			suc.setIntIdPersona(suc.getIntIdsucursal());
			getService().grabarSucursal(suc);
		}*/
		
		/*beanListSucurZonal.clear();
	
		setMessageSuccess("Los datos de zonal se actualizaron satisfactoriamente ");
		listarZonal(event);
		setShowModalPanelZonal(false);*/
		
	}
	
	public void modificarEmpresa(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging EmpresaController.modificarEmpresa-----------------------------");
		String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmEmprModalPanel:hiddenId");
		log.info("strEmpresaId: "+strEmpresaId);
		
		Empresa emp = new Empresa();
		Integer idPersona = Integer.parseInt(strEmpresaId.trim());
		log.info("idPersona: "+idPersona);
		
		try {
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			emp = personaFacade.getEmpresaJuridicaPorPK(idPersona);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("PERS_IDPERSONA_N = "+emp.getIntIdEmpresa());
		log.info("JURI_RAZONSOCIAL_V = "+emp.getJuridica().getStrRazonSocial());
		log.info("JURI_SIGLAS_V = "+emp.getJuridica().getStrSiglas());
		log.info("PERS_RUC_N = "+emp.getJuridica().getPersona().getStrRuc());
		log.info("JURI_IDTIPOEMPRESA_N = "+emp.getJuridica().getIntTipoEmpresaCod());
		log.info("EMPR_IDESTADO_N = "+emp.getIntEstadoCod());
		log.info("EMPR_TIEMPOSESION = "+emp.getStrTiempoSesion());
		log.info("EMPR_ALERTASESION = "+emp.getStrAlertaSesion());
		log.info("EMPR_VIGENCIACLAVES_C = "+emp.getIntVigenciaClaves());
		if(emp.getIntVigenciaClaves()==null){
			setStrRadioVigenciaClaves("indeterminado");
		}else{
			setStrRadioVigenciaClaves("determinado");
		}
		log.info("EMPR_ALERTACADUCIDAD_C = "+emp.getIntAlertaCaducidad());
		log.info("EMPR_INTENTOSINGRESO_C = "+emp.getIntIntentosIngreso());
		if(emp.getIntVigenciaClaves()==null){
			setStrRadioIntentosIngreso("indeterminado");
		}else{
			setStrRadioIntentosIngreso("determinado");
		}
		log.info("EMPR_CONTROLHORAINGRESO_C = "+emp.getIntControlHoraIngreso());
		log.info("EMPR_CONTROLREGISTRO_C = "+emp.getIntControlRegistro());
		log.info("EMPR_CONTROLCAMBIOCLAVE_C = "+emp.getIntControlCambioClave());
		
		//Parametros Generales de Control
		if(emp.getIntControlHoraIngreso().equals(1)){
			emp.setBlnControlHoraIngreso(true);
		}else{
			emp.setBlnControlHoraIngreso(false);
		}
		if(emp.getIntControlRegistro().equals(1)){
			emp.setBlnControlRegistro(true);
		}else{
			emp.setBlnControlRegistro(false);
		}
		if(emp.getIntControlCambioClave().equals(1)){
			emp.setBlnControlCambioClave(true);
		}else{
			emp.setBlnControlCambioClave(false);
		}
		
		//Tiempo de sesion
		String strTimeSession[] = emp.getStrTiempoSesion().trim().split(":"); 
		String strHourSession = strTimeSession[0];
		String strMinuteSession = strTimeSession[1];
		String strSecondsSession = strTimeSession[2];
		setStrHoraSesion(strHourSession);
		setStrMinutosSesion(strMinuteSession);
		setStrSegundosSesion(strSecondsSession);
		log.info("Tiempo Sesión: "+getStrHoraSesion()+":"+getStrMinutosSesion()+":"+getStrSegundosSesion());
		
		//Alerta de sesion
		String strAlertSession[] = emp.getStrAlertaSesion().trim().split(":");
		String strHourAlert = strAlertSession[0];
		String strMinuteAlert = strAlertSession[1];
		String strSecondsAlert = strAlertSession[2];
		setStrHoraAlerta(strHourAlert);
		setStrMinutosAlerta(strMinuteAlert);
		setStrSegundosAlerta(strSecondsAlert);
		log.info("Alerta Sesión: "+getStrHoraAlerta()+":"+getStrMinutosAlerta()+":"+getStrSegundosAlerta());
		
		setBeanEmpresa(emp);
		habilitarActualizarEmpresa(event);
		setShowModalPanelEmpresa(false);
		log.info("Datos de empresa ("+emp.getIntIdEmpresa()+")cargados exitosamente.");
	}
	
	public void hardCodeIdArea(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.hardCodeIdArea-----------------------------");
		String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:frmArea:hiddenIdEmpArea");
		String strSucursalId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:frmArea:hiddenIdSucArea");
		String strAreaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:frmArea:hiddenIdArea");
		log.info("strEmpresaId 	= "+strEmpresaId);
		log.info("strSucursalId = "+strSucursalId);
		log.info("strAreaId 	= "+strAreaId);
		setHiddenIdEmpArea(strEmpresaId);
		setHiddenIdSucArea(strSucursalId);
		setHiddenIdArea(strAreaId.trim());
		setShowModalPanelArea(true);
	}
	
	public void eliminarEmpresa(ActionEvent event) throws DaoException, BusinessException{
		log.info("-----------------------Debugging EmpresaController.eliminarEmpresa-----------------------------");
		
		//String idPersona = String.valueOf(event.getComponent().getAttributes().get("prmIdEmpresa"));
		String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmEmprModalPanel:hiddenId");
		log.info("strEmpresaId: "+strEmpresaId);
		HashMap prmtEmpresa = new HashMap();
		prmtEmpresa.put("prmIdEmpresa", strEmpresaId);
		Empresa emp = null;
		try {
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			emp = personaFacade.getEmpresaJuridicaPorPK(Integer.parseInt(strEmpresaId.trim()));
		    emp.getJuridica().getPersona().setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_JURIDICA); //es Persona Juridica
		    emp.getJuridica().getPersona().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO); //es Estado de Persona Activo
			emp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO); //es Estado de Empresa Anulado
			personaFacade.grabarEmpresa(emp);
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Se ha eliminado la empresa: "+strEmpresaId);
		listarEmpresas(event);
		setShowModalPanelEmpresa(false);
	}
	
	public void listarEmpresas(ActionEvent event) throws BusinessException{
		log.info("-----------------------Debugging EmpresaController.listarEmpresas-----------------------------");
	    setService(empresaService);
	    log.info("Se ha seteado el Service");
		
	    log.info("Seteando los parametros de busqueda de empresas: ");
		log.info("-------------------------------------------------");
		log.info("getCboEmpresa(): "+getCboEmpresa());
		log.info("getTxtRuc(): "+getTxtRuc().trim());
		log.info("getCboTipoEmpresa(): "+getCboTipoEmpresa());
		log.info("getCboEstadoEmpresa(): "+getCboEstadoEmpresa());
		Empresa pEmpresa = new Empresa();
		pEmpresa.setJuridica(new Juridica());
		pEmpresa.getJuridica().setPersona(new Persona());
		pEmpresa.setIntIdEmpresa(getCboEmpresa()==0?null:getCboEmpresa());
		pEmpresa.getJuridica().setStrRazonSocial(null);
		pEmpresa.getJuridica().getPersona().setStrRuc(getTxtRuc().trim());
		pEmpresa.setIntTipoConformacionCod(getCboTipoEmpresa()==0?null:getCboTipoEmpresa());
		pEmpresa.setIntEstadoCod(getCboEstadoEmpresa()==0?null:getCboEstadoEmpresa());
	    
	    ArrayList<Empresa> listaEmpresa = new ArrayList<Empresa>();
	    
	    try {
	    	EmpresaFacadeLocal empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			listaEmpresa = (ArrayList) empresaFacade.getListaEmpresa(pEmpresa);
			log.info("listaEmpresa.size: " + listaEmpresa.size());
		
	    } catch(BusinessException e){
			throw e;
		
	    }catch(Exception e){
			throw new BusinessException(e);
		}
		
//		Empresa emp = null;
//		for(int i=0; i<listaEmpresa.size(); i++){
//			emp = (Empresa) listaEmpresa.get(i);
//			log.info("intIdEmpresa: "	+ emp.getIntIdEmpresa());
//			log.info("strRazonSocial: "	+ emp.getJuridica().getStrRazonSocial());
//			log.info("intTipoConformacionCod: "+ emp.getIntTipoConformacionCod());
//			log.info("intEstadoCod:"		+ emp.getIntEstadoCod());
//			log.info("strSiglas: "		+ emp.getJuridica().getStrSiglas());
//			log.info("NROSUCURSALES:"		+ emp.getIntCantidadSucursal());
//		}
		
		setBeanListEmpresas(listaEmpresa);
		setBeanList(listaEmpresa);
		if(listaEmpresa.size()==0){
			setMsgSinEmpresas("No se encontraron coincidencias para la búsqueda.");
		}
	}
	
	private Boolean isValidoSucursal(Sucursal beanSucursal){
		Boolean validSucursal = true;
		int intTipoSuc = beanSucursal.getIntIdTipoSucursal();
	    if(intTipoSuc==0){
	    	setMsgTipoSucursal("* Debe seleccionar un Tipo de Sucursal.");
	    	validSucursal = false;
	    }else{
	        setMsgTipoSucursal("");
	    }
	    String validNomComercial = beanSucursal.getJuridica().getStrRazonSocial();
	    if(validNomComercial == null || validNomComercial.equals("")){
	        setMsgTxtSucursal("* Debe completar el campo Nombre de Sucursal.");
	        validSucursal = false;
	    } else{
	    	setMsgTxtSucursal("");
	    }
	    String validAbrev = beanSucursal.getJuridica().getStrSiglas();
	    if(validAbrev==null || validAbrev.equals("")){
	        setMsgTxtAbreviatura("* Debe completar el campo Abreviatura de Sucursal.");
	        validSucursal = false;
	    }else{
	    	setMsgTxtAbreviatura("");
	    }
	    return validSucursal;
	}
	
	public void addSucursalCodigo(ActionEvent event){
		SucursalCodigo sucursalCodigo = null;
		Boolean bValidar = true;
		
		if(bValidar == true){
			sucursalCodigo = new SucursalCodigo();
			listSucursalCodigo.add(sucursalCodigo);
		}
	}
	
	public void removeSucursalCodigo(ActionEvent event){
		List<SucursalCodigo> arraySucursalCodigo = new ArrayList();
	    for(int i=0; i<getListSucursalCodigo().size(); i++){
	    	SucursalCodigo sucursalCodigo = new SucursalCodigo();
	    	sucursalCodigo = (SucursalCodigo) getListSucursalCodigo().get(i);
	    	if(sucursalCodigo.getChkCodigoTercero() == false){
	    		arraySucursalCodigo.add(sucursalCodigo);
	    	}
	    }
	    listSucursalCodigo.clear();
	    setListSucursalCodigo(arraySucursalCodigo);
	}
	
	public void addSubSucursal(ActionEvent event){
		Subsucursal subSucursal = null;
		Boolean bValidar = true;
		
		if(bValidar == true){
			subSucursal = new Subsucursal();
			listSubsucursal.add(subSucursal);
		}
	}
	
	public void removeSubsucursal(ActionEvent event){
		List<Subsucursal> arraySucursalCodigo = new ArrayList();
	    for(int i=0; i<getListSucursalCodigo().size(); i++){
	    	Subsucursal subSucursal = new Subsucursal();
	    	subSucursal = (Subsucursal) getListSubsucursal().get(i);
	    	if(subSucursal.getChkSubSucursal() == false){
	    		arraySucursalCodigo.add(subSucursal);
	    	}
	    }
	    listSubsucursal.clear();
	    setListSubsucursal(arraySucursalCodigo);
	}
	
	public void grabarSucursal(ActionEvent event) throws DaoException{
		EmpresaFacadeLocal empresaFacade = null;
	    if(isValidoSucursal(beanSucursal) == false){
	    	log.info("Datos de Crédito no válidos. Se aborta el proceso de grabación de Sucursal.");
	    	return;
	    }
	    beanSucursal.getJuridica().getPersona().setListaDomicilio(domicilioController.getListaDomicilio());
	    beanSucursal.getJuridica().getPersona().setIntTipoPersonaCod(2);
	    beanSucursal.getJuridica().setStrNombreComercial(beanSucursal.getJuridica().getStrRazonSocial());
	    
		if(listSucursalCodigo!=null){
			beanSucursal.setListaSucursalCodigo(listSucursalCodigo);
		}
		if(listSubsucursal!=null){
			beanSucursal.setListaSubSucursal(listSubsucursal);
		}
		
		try {
			empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			empresaFacade.grabarSucursal(beanSucursal);
			//limpiarFormCredito();
			listarSucursales(event);
		} catch (EJBFactoryException e) {
			log.error("Error EJBFactoryException en grabarSucursal ---> "+e);
			e.printStackTrace();
		} catch (BusinessException e) {
			log.error("Error BusinessException en grabarSucursal ---> "+e);
			e.printStackTrace();
		}
	}
	/*
	public void grabarSucursal(ActionEvent event) throws EJBFactoryException, BusinessException, DaoException{
		log.info("-----------------------Debugging EmpresaController.grabarSucursal-----------------------------");
	    Sucursal suc = new Sucursal();
	    suc = (Sucursal) getBeanSucursal();
	    suc.getJuridica().getPersona().setListaDomicilio(domicilioController.getBeanListDomicilio());
	    suc.getJuridica().getPersona().setIntTipoPersonaCod(2);
	    suc.getJuridica().setStrNombreComercial(suc.getJuridica().getStrRazonSocial());
	    suc.setListaSucursalCodigo(ArrayTercero);
	    suc.setListaSubSucursal(ArraySubsucursal);
	    Boolean bValidar = true;
	    
	    int intTipoSuc = suc.getIntIdTipoSucursal();
	    if(intTipoSuc==0){
	    	setMsgTipoSucursal("* Debe seleccionar un Tipo de Sucursal.");
	    	bValidar = false;
	    }else{
	        setMsgTipoSucursal("");
	    }
	    String validNomComercial = suc.getJuridica().getStrRazonSocial();
	    if(validNomComercial == null || validNomComercial.equals("")){
	        setMsgTxtSucursal("* Debe completar el campo Nombre de Sucursal.");
	    	bValidar = false;
	    } else{
	        validNomComercial="";
	    }
	    String validAbrev = suc.getJuridica().getStrSiglas();
	    if(validAbrev==null || validAbrev.equals("")){
	        setMsgTxtAbreviatura("* Debe completar el campo Abreviatura de Sucursal.");
	    	bValidar = false;
	    }else{
	        validAbrev="";
	    }
	    
	    if(bValidar==true){
	    	try {
		    	EmpresaFacadeLocal empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
				empresaFacade.grabarSucursal(suc);
		    	
				log.info("suc.getIntIdSucursal(): "+suc.getId().getIntIdSucursal());
			} catch (EJBFactoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setHiddenIdSucursal(""+suc.getId().getIntIdSucursal());
			listarSucursales(event);
			
			setMessageSuccess("Los datos se actualizaron satisfactoriamente ");
	    }
	}*/
	
	public void irModificarSucursal(ActionEvent event){
    	EmpresaFacadeLocal facade = null;
    	String strIdSucursal = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmSucModalPanel:hiddenIdSucursal");
    	log.info("strIdSucursal: "+strIdSucursal);
    	try {
    		if(strIdSucursal != null && !strIdSucursal.trim().equals("")){
				facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
				beanSucursal = facade.getSucursalPorIdSucursal(new Integer(strIdSucursal));
				if(beanSucursal.getJuridica()!=null && beanSucursal.getJuridica().getPersona().getListaDomicilio()!=null){
					domicilioController.setListaDomicilio(beanSucursal.getJuridica().getPersona().getListaDomicilio());
				}
				if(beanSucursal.getListaSucursalCodigo()!=null){
					listSucursalCodigo = beanSucursal.getListaSucursalCodigo();
				}
				if(beanSucursal.getListaSubSucursal()!=null){
					listSubsucursal = beanSucursal.getListaSubSucursal();
				}
				setSucursalRendered(true);
				strTipoMantSucursal = Constante.MANTENIMIENTO_MODIFICAR;
    		}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void modificarSucursal(ActionEvent event) throws DaoException, BusinessException, EJBFactoryException{
		log.info("-----------------------Debugging EmpresaController.modificarSucursal-----------------------------");
		EmpresaFacadeLocal empresaFacade = null;
	    if(isValidoSucursal(beanSucursal) == false){
	    	log.info("Datos de Crédito no válidos. Se aborta el proceso de grabación de Sucursal.");
	    	return;
	    }
	    
	    beanSucursal.getJuridica().getPersona().setListaDomicilio(domicilioController.getListaDomicilio());
	    beanSucursal.getJuridica().getPersona().setIntTipoPersonaCod(2);
	    beanSucursal.getJuridica().setStrNombreComercial(beanSucursal.getJuridica().getStrRazonSocial());
	    
	    if(listSucursalCodigo!=null){
			beanSucursal.setListaSucursalCodigo(listSucursalCodigo);
		}
		if(listSubsucursal!=null){
			beanSucursal.setListaSubSucursal(listSubsucursal);
		}
    	try {
    		empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			empresaFacade.modificarSucursal(beanSucursal);
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listarSucursales(event);
		setMessageSuccess("Los datos se actualizaron satisfactoriamente ");
	}
	
	public void eliminarSucursal(ActionEvent event) throws DaoException{
    	log.info("-----------------------Debugging EmpresaController.eliminarSucursal-----------------------------");
    	String strSucursalId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmSucModalPanel:hiddenIdSucursal");
    	log.info("strSucursalId: "+ strSucursalId);
		EmpresaFacadeLocal empresaFacade = null;
		Sucursal sucursal = null;
    	try {
    		sucursal = new Sucursal();
    		sucursal.setId(new SucursalId());
    		sucursal.setIntPersPersonaPk(new Integer(strSucursalId));
    		empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
    		empresaFacade.eliminarSucursal(sucursal);
			limpiarFormSucursal();
			listarSucursales(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
	
	public void listarSucursales(ActionEvent event) throws BusinessException, EJBFactoryException, DaoException{
		log.info("-----------------------Debugging EmpresaController.listarSucursales-----------------------------");
		pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote facade = null;
		try {
			facade = (pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote)EJBFactory.getRemote(pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote.class);
			Sucursal o = new Sucursal();
			o.setId(new SucursalId());
			o.setJuridica(new Juridica());
			if(intCboEmpresaSuc!=0)
			o.getId().setIntPersEmpresaPk(intCboEmpresaSuc);
			if(cboTipoSucursal!=0)
			o.setIntIdTipoSucursal(cboTipoSucursal);
			if(cboEstadoSucursal!=0)
			o.setIntIdEstado(cboEstadoSucursal);
			if(intCboSucursalEmp!=0)
				o.setIntPersPersonaPk(intCboSucursalEmp);
			o.setIntIdTercero(chkTerceroFiltro==true?1:0);
			o.setIntNroSubSucursales(chkSubsucursalFiltro==true?1:0);
			
			listaSucursalComp = facade.getListaSucursalCompDeBusquedaSucursal(o);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	public void selectTipoSucurZonal(ValueChangeEvent event){
		listarSucursalesZonal("cboFiltTipoSucur",event.getNewValue());
	}
	
	public void selectFiltSucurAnexas(ValueChangeEvent event){
		listarSucursalesZonal("cboFiltSucurAnexas",event.getNewValue());
	}
	
	public void listarSucursalesZonal(String controlName, Object filterValue){
		/*
		log.info("-----------------------Debugging EmpresaController.listarSucursalesZonal-----------------------------");
	    setService(empresaService);
	    log.info("Se ha seteado el Service");
		
	    log.info("Seteando los parametros de busqueda de sucursales: ");
		log.info("-------------------------------------------------");
		HashMap prmtBusqSucursales = new HashMap();
		log.info("getStrCboEmpresaSuc = "+getIntCboEmpresaSuc());
		log.info("pCboFiltTipoSucur = "	 +filterValue);
		log.info("pCboEstadoSucursal = " +getCboEstadoSucursal());
		log.info("pTxtSucursal = "		 +getIntCboSucursalEmp());
		Zonal zon = new Zonal();
		zon = (Zonal) getBeanZonal();
	    log.info(""+zon.getIntIdZonal());
		String strTipoSucursal = (String)filterValue;
		if(strTipoSucursal.equals("0")){
			strTipoSucursal=null;
		}
		
		if(controlName.equals("cboFiltTipoSucur")){
			prmtBusqSucursales.put("pIntIdpersona", null);
			prmtBusqSucursales.put("pTxtEmpresaSucursal", null);
			prmtBusqSucursales.put("pCboTipoSucursal", strTipoSucursal);
			prmtBusqSucursales.put("pCboEstadoSucursal", null);
			prmtBusqSucursales.put("pTxtSucursal", null);
			prmtBusqSucursales.put("pIntIdempresa", null);
		}else if(controlName.equals("cboFiltSucurAnexas")){
			prmtBusqSucursales.put("pIntIdpersona", null);
			prmtBusqSucursales.put("pTxtEmpresaSucursal", null);
			prmtBusqSucursales.put("pCboTipoSucursal", null);
			prmtBusqSucursales.put("pCboEstadoSucursal", null);
			prmtBusqSucursales.put("pTxtSucursal", null);
			prmtBusqSucursales.put("pIntIdempresa", null);
		}else{
			prmtBusqSucursales.put("pIntIdpersona", null);
			prmtBusqSucursales.put("pTxtEmpresaSucursal", null);
			prmtBusqSucursales.put("pCboTipoSucursal", null);
			prmtBusqSucursales.put("pCboEstadoSucursal", null);
			prmtBusqSucursales.put("pTxtSucursal", null);
			prmtBusqSucursales.put("pIntIdempresa", null);
		}
	    
	    ArrayList arraySucursales = new ArrayList();
	    ArrayList listaSucursales = new ArrayList();
	    try {
	    	arraySucursales = getService().listarSucursales(prmtBusqSucursales);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarSucursales() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMessageSuccess("Listado de sucursales ha sido satisfactorio");
		
		//Nro de Sucursales Anexadas y Libres
		int intNroAnexadas = 0;
		int intNroLibres = 0;
		int codZona = 0;
		for(int i=0; i<arraySucursales.size(); i++){
			HashMap hash = (HashMap) arraySucursales.get(i);
			Sucursal suc = new Sucursal();
			log.info("JURI_NOMBRECOMERCIAL_V = "+hash.get("JURI_NOMBRECOMERCIAL_V"));
			String strNomComer = "" + hash.get("JURI_NOMBRECOMERCIAL_V");
			suc.getJuridica().setStrNombreComercial(strNomComer);
			log.info("SUCU_IDESTADO_N = "+hash.get("SUCU_IDESTADO_N"));
			int intIdEstado = Integer.parseInt("" + hash.get("SUCU_IDESTADO_N"));
			suc.setIntIdEstado(intIdEstado);
			log.info("SUCU_IDSUCURSAL_N = "+hash.get("SUCU_IDSUCURSAL_N"));
			int intIdSucursal = Integer.parseInt("" + hash.get("SUCU_IDSUCURSAL_N"));
			suc.setIntIdSucursal(intIdSucursal);
			log.info("SUCU_IDEMPRESA_N = "+hash.get("SUCU_IDEMPRESA_N"));
			int strIdEmpresa = Integer.parseInt(""+hash.get("SUCU_IDEMPRESA_N"));
			suc.setIntIdEmpresa(strIdEmpresa);
			log.info("ZONA_IDZONAL_N = "+hash.get("ZONA_IDZONAL_N"));
			if(hash.get("ZONA_IDZONAL_N")!=null){
				int intIdZonal = Integer.parseInt("" + hash.get("ZONA_IDZONAL_N"));
				suc.setIntIdZonal(intIdZonal);
				log.info("ZONA_NOMBREZONA_V = "+hash.get("ZONA_NOMBREZONA_V"));
				String strNombreZonal = "" + hash.get("ZONA_NOMBREZONA_V");
				suc.setStrNombreZonal(strNombreZonal);
			}else{
				suc.setStrNombreZonal("");
			}
			if(hash.get("ZONA_IDZONAL_N")!=null){
				codZona = Integer.parseInt(""+hash.get("ZONA_IDZONAL_N"));
			}			
			if(zon.getIntIdZonal()==codZona){
				suc.setBlnSucurActiv(true);
			}else{
				suc.setBlnSucurActiv(false);
			}			
			
			if(controlName.equals("cboFiltSucurAnexas")){
				int intAnexado = Integer.parseInt(""+filterValue);
				if(intAnexado==0){
					listaSucursales.add(suc);
					if(hash.get("ZONA_IDZONAL_N")!=null){
						intNroAnexadas++;
					}else{
						intNroLibres++;
					}
				}else if(intAnexado==1){
					if(hash.get("ZONA_IDZONAL_N")!=null){
						listaSucursales.add(suc);
						intNroAnexadas++;
					}
				}else if(intAnexado==2){
					if(hash.get("ZONA_IDZONAL_N")==null){
						listaSucursales.add(suc);
						intNroLibres++;
					}
				}
			}else{
				listaSucursales.add(suc);
				if(hash.get("ZONA_IDZONAL_N")!=null){
					intNroAnexadas++;
				}else{
					intNroLibres++;
				}
			}
		}
		setIntContSucurAnexas(intNroAnexadas);
		setIntContSucurLibres(intNroLibres);
		
		log.info("listaSucursales.size(): "+listaSucursales.size());
		setBeanListSucurZonal(listaSucursales);
		obtenerZonales();
		*/
	}
	
	//-------------------------------------------------------------------------------------------------------------------------------------------
	// Metodos para los controles
	//-------------------------------------------------------------------------------------------------------------------------------------------
	public void habilitarGrabarEmpresa(ActionEvent event){
		
		log.info("-----------------------Debugging EmpresaController.habilitarGrabarEmpresa-----------------------------");
	    setFormEmpresaEnabled(false);
	    setEmpresaRendered(true);
	    limpiarFormEmpresa();
	    setStrHoraSesion("00");
	    setStrMinutosSesion("00");
	    setStrSegundosSesion("00");
	    setStrHoraAlerta("00");
	    setStrMinutosAlerta("00");
	    setStrSegundosAlerta("00");
	}	
	
	public void irGrabarZonal(ActionEvent event){
	    limpiarFormZonal();
	    setFormZonalDisabled(false);
	    setZonalRendered(true);
	    beanZonal = new Zonal();
	    beanZonal.setId(new ZonalId());
	    beanZonal.setJuridica(new Juridica());
	    beanZonal.setSucursal(new Sucursal());
	    beanZonal.getSucursal().setId(new SucursalId());
	    beanZonal.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
	    strTipoMantZonal = Constante.MANTENIMIENTO_GRABAR; 
	}	
	
	public void habilitarActualizarEmpresa(ActionEvent event){	
		log.info("-----------------------Debugging EmpresaController.habilitarActualizarEmpresa-----------------------------");
	    setFormEmpresaEnabled(false);
	    setEmpresaRendered(true);
	}
	
	public void cancelarGrabarEmpresa(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.cancelarGrabarEmpresa-----------------------------");
	    setFormEmpresaEnabled(true);
	    setEmpresaRendered(false);
	    limpiarFormEmpresa();
	    setCboEmpresa(0);
	    setCboTipoEmpresa(0);
	    setTxtRuc("");
	    setCboEstadoEmpresa(0);
	    getBeanListEmpresas().clear();
	    
	}
	
	public void cancelarGrabarZonal(ActionEvent event){
		/*log.info("-----------------------Debugging EmpresaController.cancelarGrabarZonal-----------------------------");
	    setFormZonalDisabled(true);
	    setZonalRendered(false);
	    limpiarFormZonal();*/
	    limpiarFormZonal();
	    listarZonal(event);
	}	
	
	public void limpiarFormEmpresa(){
		beanEmpresa = new Empresa();
		beanEmpresa.setJuridica(new Juridica());
		beanEmpresa.getJuridica().setPersona(new Persona());
	    setMsgTxtEmpresa("");
		setMsgTxtAbreviatura("");
		setMsgTxtRuc("");
		setMsgTipoEmpresa("");
		setMsgEstadoEmp("");
		setMsgTiempoSesion("");
		setMsgAlertaSesion("");
		setMsgVigenciaClaves("");
		setMsgAlertaCaducidad("");
		setMsgTiempoSesionError("");
		setMsgVigenciaError("");
		setMsgIntentosError("");
		setMsgSinEmpresas("");
	}
	
	public void limpiarFormZonal(){
	    /*Zonal zon = new Zonal();
	    setBeanZonal(zon);
	    //setIntCboEmpresaZonal(0);
	    setIntCboRespZonal(0);
	    //setStrCboSucursalZonal("0");
	    */
	    setMsgEmpresaZonalError("");
	    setMsgTipoZonalError("");
	    setMsgEstadoZonalError("");
	    setMsgNombreZonalError("");
	    setMsgAbrevZonalError("");
	    setMsgRespZonalError("");
	    setMsgSucurSondeoError("");
		setShowModalPanelZonal(false);
		beanZonal = null;
		zonalRendered = false;
		intTipoSucursal = 0;
		intSucursalAnexo = 0;
		intContSucurAnexas = 0;
		intContSucurLibres = 0;
		listaSucursalDeZonal = null;
	}
	
	public void habilitarGrabarSucursal(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.habilitarGrabarSucursal-----------------------------");
	    setFormSucursalEnabled(true);
	    setFormSucursalEnabled2(true);
	    setSucursalRendered(true);
	    limpiarFormSucursal();
	    domicilioController.setStrInsertUpdate("I");
	    strTipoMantSucursal = Constante.MANTENIMIENTO_GRABAR;
	}
	
	public void habilitarGrabarArea(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.habilitarGrabarArea-----------------------------");
	    setFormAreaEnabled(true);
	    //setFormAreaEnabled2(true);
	    setFormAreaDisabled(false);
	    setAreaRendered(true);
	    limpiarFormArea();
	    setStrTipoMantArea(Constante.MANTENIMIENTO_GRABAR);
	}
	
	public void limpiarFormSucursal(){
		log.info("-----------------------Debugging SucursalController.limpiarFormSucursal-----------------------------");
		Sucursal suc = new Sucursal();
		suc.setId(new SucursalId());
		suc.setJuridica(new Juridica());
		suc.getJuridica().setPersona(new Persona());
		setMsgTxtSucursal("");
	    setMsgTxtAbreviatura("");
		setBeanSucursal(suc);
		setIntCboEmpresaSucursal(0);
		if(beanListSucursales !=null){
			beanListSucursales.clear();
		}
		if(domicilioController.getListaDomicilio()!=null){
			domicilioController.getListaDomicilio().clear();
		}
		if(domicilioController.getArrayDomicilio()!=null){
			domicilioController.limpiarArrayDomicilio();
		}
		if(beanListSucursalCodigo != null){
			beanListSucursalCodigo.clear();
		}
		if(beanListSubsucursal != null){
			beanListSubsucursal.clear();
		}
		if(beanListSubsucursal != null){
			beanListSubsucursal.clear();
		}
		if(listSubsucursal != null){
			listSubsucursal.clear();
		}
		if(listSucursalCodigo != null){
			listSucursalCodigo.clear();
		}
		if(ArrayTercero != null){
			ArrayTercero.clear();
		}
		if(ArraySubsucursal != null){
			ArraySubsucursal.clear();
		}
	}
	
	public void limpiarFormArea(){
		log.info("-----------------------Debugging SucursalController.limpiarFormArea-----------------------------");
		Area area = new Area();
		area.setId(new AreaId());
		area.setListaAreaCodigo(new ArrayList<AreaCodigo>());
		area.setIntIdEstado(0);
		area.setStrDescripcion("");
		setMsgTxtEmpresaArea("");
		setMsgTxtSucursalArea("");
		setMsgTxtArea("");
		setMsgTxtAbrevArea("");
		setIntCboEmpresa(0);
		setIntCboSucursal(0);
		setMsgAreaCodigoError("");
		
		setBeanArea(area);
		/*
		if(beanListAreacodigo != null){
			beanListAreacodigo.clear();
		}
		if(listAreaCodigo != null){
			listAreaCodigo.clear();
		}*/
	}
	
	public void limpiarFormAreaCodigo(){
    	AreaCodigo arcod = new AreaCodigo();
    	arcod.setId(new AreaCodigoId());
    	setMsgAreaCodigoError("");
    	setBeanAreacodigo(arcod);
    }
	
	public void cancelarGrabarSucursal(ActionEvent event){
		log.info("-----------------------Debugging UsuarioController.cancelarGrabarSucursal-----------------------------");
	    setFormSucursalEnabled(true);
	    setFormSucursalEnabled2(true);
	    setSucursalRendered(false);
	    limpiarFormSucursal();
	}
	
	public void cancelarGrabarArea(ActionEvent event){
		log.info("-----------------------Debugging UsuarioController.cancelarGrabarArea-----------------------------");
	    setFormAreaEnabled(true);
	    setFormAreaEnabled2(true);
	    setAreaRendered(false);
	    limpiarFormArea();
	}
	
	public Boolean validarFormatoHHMMSS(String validTiempoSesion){
		log.info("-----------------------Debugging EmpresaController.validarFormatoHHMMSS(String validTiempoSesion)-----------------------------");
	      String[] arrayTiempo = validTiempoSesion.split(":");
	      if(validTiempoSesion.equals("")){
	    	  return false;
	      }else if(!(validTiempoSesion.length()==8)||!(arrayTiempo.length==3)){
	    	  return false;
	      }else{
	    	  String hh = arrayTiempo[0];
		      String mm = arrayTiempo[1];
		      String ss = arrayTiempo[2];
		      log.info("Formato HH:MM:SS = "+hh+":"+mm+":"+ss);
		      if(!(hh.length()==2)||!(mm.length()==2)||!(ss.length()==2)||
		    		  !StringUtils.isNumeric(hh)||!StringUtils.isNumeric(mm)||!StringUtils.isNumeric(ss)){
		    	  return false;
		      }else{
		    	  int horas = Integer.parseInt(hh);
			      int minutos = Integer.parseInt(mm);
			      int segundos = Integer.parseInt(ss);
			      if(horas>24||minutos>59||segundos>59){
			    	  return false;
			      }else{
			    	  return true;
			      }
		      }
	      }
	}
	
	public Boolean compararTiemposHHMMSS(String t1, String t2) throws ParseException{
		log.info("----------------Debugging EmpresaController.compararTiemposHHMMSS(String t1, String t2)----------------");
		log.info("T1 = "+t1);
		log.info("T2 = "+t2);
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
		Date d1 = new Date();
		d1 = df.parse(t1);
		log.info("D1 = "+d1);
		Date d2 = new Date();
		d2 = df.parse(t2);
		log.info("D2 = "+d2);
		if(d1.compareTo(d2)>0){
			log.info("compararTiemposHHMMSS(String t1, String t2) = "+true);
			return true;
		}else{
			log.info("compararTiemposHHMMSS(String t1, String t2) = "+false);
			return false;
		}
	}
	
	public void selectRadioIntentosIngreso(){
		log.info("----------------Debugging EmpresaController.selectRadioIntentosIngreso()----------------");
		String strIntentos = getStrRadioIntentosIngreso();
		Empresa emp = new Empresa();
		emp = getBeanEmpresa();
		if(strIntentos.equals("indeterminado")){
			emp.setIntIntentosIngreso(null);
		}
		setBeanEmpresa(emp);
	}
	/*
    public void grabarTercero(ActionEvent event){
		  log.info("-----------------------Debugging EmpresaController.grabarTercero-----------------------------");
		  setService(empresaService);
	      log.info("Se ha seteado el Service");
	      SucursalCodigo succod = new SucursalCodigo();
	      //ter = (Tercero)getBeanTercero();
	      Boolean bValidar = true;
	      
	      succod.setIntIdTipoCodigo(Integer.parseInt(getStrNombreTercero()));
	      succod.setStrIdCodigo(getStrCodigoTercero());
	      succod.setIntEstadoCod(getIntCboActivoTercero());
	      if(getStrNombreTercero().equals("")){
	    	  bValidar = false;
	    	  setMessageError("El campo Tipo de Código debe de ser completado.");
	      }else {
	    	  bValidar = true;
	      }
	      if(getStrCodigoTercero().equals("")){
	    	  bValidar = false;
	    	  setMessageError("El campo Código de Tercero debe ser completado.");
	      }else {
	    	  bValidar = true;
	      }
	      if(bValidar == true){
	    	  ArrayTercero.add(succod);
		      listarTerceros(event);
		      setStrNombreTercero("");
		      setIntCboActivoTercero(1);
		      setStrCodigoTercero("");
	      }
	}*/
	
	public void listarTerceros(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.listarTercero-----------------------------");
	    setService(empresaService);
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de terceros: ");
		log.info("-------------------------------------------------");
		HashMap prmtBusqTercero = new HashMap();
		int idPersona = Integer.parseInt(getHiddenIdSucursal()==null||getHiddenIdSucursal().equals("")?"0":getHiddenIdSucursal());
		log.info("pIntIdpersona = "+ idPersona);
		prmtBusqTercero.put("pIntIdpersona", idPersona);
		
	    //ArrayList arrayTercero = new ArrayList();
	    ArrayList listaSucursalCodigo = new ArrayList();
	    /*try {
	    	arrayTercero = getService().listarTercero(prmtBusqTercero);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarTercero() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*setMessageSuccess("Listado de terceros ha sido satisfactorio");
		for(int i=0; i<arrayTercero.size(); i++){
			HashMap hash = (HashMap) arrayTercero.get(i);
			SucursalCodigo succod = new SucursalCodigo();
			log.info("TERC_IDPERSONA_N = "+hash.get("TERC_IDPERSONA_N"));
			int idpersona = Integer.parseInt("" + hash.get("TERC_IDPERSONA_N"));
			succod.setIntIdSucursal(idpersona);
			log.info("TERC_NOMBRE_C = "+hash.get("TERC_NOMBRE_C"));
			String strNombre = "" + hash.get("TERC_NOMBRE_C");
			succod.setStrNombre(strNombre);
			log.info("TERC_ACTIVO_N = "+hash.get("TERC_ACTIVO_N"));
			int intActivo = Integer.parseInt(""+hash.get("TERC_ACTIVO_N"));
			succod.setIntActivo(intActivo);
			log.info("TERC_CODIGOTER_C = "+hash.get("TERC_CODIGOTER_C"));
			String strCodigoter = "" + hash.get("TERC_CODIGOTER_C");
			succod.setStrCodigo(strCodigoter);
			listaSucursalCodigo.add(succod);
		}*/
		for(int i=0; i<getArrayTercero().size(); i++){
			SucursalCodigo succod = new SucursalCodigo();
			succod = (SucursalCodigo) getArrayTercero().get(i);
			listaSucursalCodigo.add(succod);
		}
		
		setBeanListSucursalCodigo(listaSucursalCodigo);
	}
    
    public void grabarSubsucursal(ActionEvent event){
		  log.info("-----------------------Debugging EmpresaController.showPanelDomicilio-----------------------------");
		  setService(empresaService);
	      log.info("Se ha seteado el Service");
	      Subsucursal sub = new Subsucursal();
	      //sub = (Subsucursal)getBeanSubsucursal();
	      Boolean bValidar = true;
	      
	      sub.setStrDescripcion(getStrNombreSubSuc());
	      sub.setIntIdEstado(getIntCboActivoSubsuc());
	      //sub.setStrAbreviatura(getStrSubSucAbrev());
	      if(getStrNombreSubSuc().equals("")){
	    	  setMessageError("El campo Nombre de SubSucursal debe ser ingresado");
	    	  bValidar = false;
	      }else {
	    	  bValidar = true;
	      }
	      
	      if(bValidar == true){
	    	  ArraySubsucursal.add(sub);
		      listarSubsucursales(event);
		      setStrNombreSubSuc("");
		      setIntCboActivoSubsuc(1);
	      }
	}
    
    public void listarSubsucursales(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.listarSubsucursal-----------------------------");
	    setService(empresaService);
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de subsucursales: ");
		log.info("-------------------------------------------------");
		HashMap prmtBusqSubsucursal = new HashMap();

		//String idEmpresa  = (getStrCboEmpresaSucursal()==null|| getStrCboEmpresaSucursal().equals("") ? "0" : getStrCboEmpresaSucursal());
		String idPersona = (getHiddenIdSucursal()==null || getHiddenIdSucursal().equals("") ? "0" :getHiddenIdSucursal());

		//prmtBusqSubsucursal.put("pIntIdEmpresa",  Integer.parseInt(idEmpresa));
		prmtBusqSubsucursal.put("pIntIdpersona", Integer.parseInt(idPersona));
		
	    ArrayList arraySubsucursal = new ArrayList();
	    ArrayList listaSubsucursal = new ArrayList();
	    /*try {
	    	arraySubsucursal = getService().listarSubsucursal(prmtBusqSubsucursal);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarSubsucursal() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMessageSuccess("Listado de Subsucursales ha sido satisfactorio");
		
		for(int i=0; i<arraySubsucursal.size(); i++){
			HashMap hash = (HashMap) arraySubsucursal.get(i);
			Subsucursal sub = new Subsucursal();
			log.info("EMPR_IDEMPRESA_N = "+hash.get("EMPR_IDEMPRESA_N"));
			int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
			sub.setIntIdEmpresa(intIdEmpresa);
			log.info("SUCU_IDSUCURSAL_N = "+hash.get("SUCU_IDSUCURSAL_N"));
			int intIdSucursal = Integer.parseInt("" + hash.get("SUCU_IDSUCURSAL_N"));
			sub.setIntIdSucursal(intIdSucursal);
			log.info("SUDE_IDSUBSUCURSAL_N = "+hash.get("SUDE_IDSUBSUCURSAL_N"));
			int intIdSubSucursal = Integer.parseInt("" + hash.get("SUDE_IDSUBSUCURSAL_N"));
			sub.setIntIdSubSucursal(intIdSubSucursal);
			log.info("SUDE_DESCRIPCION_V = "+hash.get("SUDE_DESCRIPCION_V"));
			String strNombre = "" + hash.get("SUDE_DESCRIPCION_V");
			sub.setStrNombre(strNombre);
			log.info("SUDE_IDESTADO_N = "+hash.get("SUDE_IDESTADO_N"));
			int intActivo = Integer.parseInt(""+hash.get("SUDE_IDESTADO_N"));
			sub.setIntIdEstado(intActivo);
			listaSubsucursal.add(sub);
		}*/
		for(int i=0; i<getArraySubsucursal().size(); i++){
			Subsucursal sub = new Subsucursal();
			sub = (Subsucursal) getArraySubsucursal().get(i);
			listaSubsucursal.add(sub);
		}
		setBeanListSubsucursal(listaSubsucursal);
	}

    public void obtenerArea(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging EmpresaController.obtenerArea-----------------------------");
		String strIdArea = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdArea");
		String strIdSucursal = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdSucArea");
		String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdEmpArea");		
		log.info("strIdArea: "+strIdArea);
		log.info("strIdSucursal: "+strIdSucursal);
		log.info("strIdEmpresa: "+strIdEmpresa);
		if(strIdArea==null || strIdSucursal==null || strIdEmpresa==null){
			log.info("La llave no puede ser nula. Se sale de modificarArea...");
			return;
		}
		
		Area area = new Area();
		area.setId(new AreaId());
		area.getId().setIntPersEmpresaPk(Integer.parseInt(strIdEmpresa));
		area.getId().setIntIdSucursalPk(Integer.parseInt(strIdSucursal));
		area.getId().setIntIdArea(Integer.parseInt(strIdArea));
		
		try {
			EmpresaFacadeLocal empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			area = empresaFacade.getAreaPorPK(area);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
		
		log.info("C_IDAREA = "+area.getId().getIntIdArea());
		log.info("AREA_DESCRIPCION_V = "+area.getStrDescripcion());
		log.info("EMPR_IDEMPRESA_N = "+area.getId().getIntPersEmpresaPk());
		log.info("SUCU_IDSUCURSAL_N = "+area.getId().getIntIdSucursalPk());
		log.info("AREA_IDESTADO_N = "+area.getIntIdEstado());
		log.info("C_IDTIPOAREA = "+area.getIntIdTipoArea());
		log.info("AREA_ABREVIATURA_C = "+area.getStrAbreviatura());
		
		log.info("area.listaAreaCodigo.size: "+area.getListaAreaCodigo().size());
		
		setFormAreaEnabled(true);
		setBeanArea(area);
		//setAreaRendered(true);
		setStrTipoMantArea(Constante.MANTENIMIENTO_MODIFICAR);
		//habilitarGrabarSucursal(event);
		
		/*if(listAreacodigo != null){
			listAreacodigo.clear();
		}*/		
		log.info("Datos de area ("+area.getId().getIntIdArea()+")cargados exitosamente.");
	}
    
    
    
    public Boolean isValidArea(Area area){
    	log.info("-----------------------Debugging EmpresaController.isValidArea-----------------------------");
	      
    	log.info("area.intIdTipoArea: "+area.getIntIdTipoArea());
    	log.info("area.intIdEstado: "+area.getIntIdEstado());
    	log.info("area.id.intPersEmpresaPk: "+area.getId().getIntPersEmpresaPk());
    	log.info("area.id.intIdSucursalPk: "+area.getId().getIntIdSucursalPk());
    	log.info("area.strDescripcion: "+area.getStrDescripcion());
    	log.info("area.strAbreviatura: "+area.getStrAbreviatura());
  	    
  	  	Boolean bValidar = true;
  	  	Integer validTipoarea = area.getIntIdTipoArea();//area.getStrIdTipoArea();
  	  		if(validTipoarea == 0){
  	  			setMsgTipoAreaError("* Debe completar el campo Tipo Area.");
  	  			bValidar = false;
  	  		}else{
  	  			setMsgTipoAreaError("");
  	  		}
	      
	    Integer validEstadoarea = area.getIntIdEstado();
	    if(validEstadoarea == 0){
	    	setMsgEstadoAreaError("* Debe completar el campo Estado Area.");
	    	bValidar = false;
	    }else{
	    	setMsgEstadoAreaError("");
	    }
  	  	      
	    Integer idEmpresa = area.getId().getIntPersEmpresaPk(); // area.getIntIdEmpresa();
	    if(idEmpresa == 0){
	    	setMsgTxtEmpresaArea("* Debe completar el campo Empresa.");
	    	bValidar = false;
	    }else{
	    	setMsgTxtEmpresaArea("");
	    }
	    Integer validSucursal = area.getId().getIntIdSucursalPk(); // area.getIntIdSucursal();
	    if(validSucursal == 0){
	    	setMsgTxtSucursalArea("* Debe completar el campo Sucursal.");
	    	bValidar = false;
	    }else{
	    	setMsgTxtSucursalArea("");
	    }
	    String validNombre = area.getStrDescripcion();
	    if(validNombre.equals("")){
	    	setMsgTxtArea("* Debe completar el campo Nombre de Área.");
	    	bValidar = false;
	    }else{
	    	setMsgTxtArea("");
	    }
	    String validAbreviatura = area.getStrAbreviatura(); //area.getStrAbreviatura();
	    if(validAbreviatura.equals("")){
	    	setMsgTxtAbrevArea("* Debe completar el campo Abreviatura de Área.");
	    	bValidar = false;
	    }else{
	    	setMsgTxtAbrevArea("");
	    }
	      
	    //validar lista AreaCodigo
	    log.info("area.getListaAreaCodigo().size(): "+area.getListaAreaCodigo().size());
	    if(area.getListaAreaCodigo().size()>0){
	    	
	    	if (bValidar){
	    		bValidar = validarAreaCodigo(area.getListaAreaCodigo().get(area.getListaAreaCodigo().size()-1));
	    	}
	    	
	    }
    	return bValidar;
    }
    
    public void grabarArea(ActionEvent event){
    	log.info("-----------------------Debugging EmpresaController.grabarArea-----------------------------");
	    Area area = new Area();
	    area = (Area) getBeanArea();
	    
	    Boolean bValidArea = isValidArea(area);
	    log.info("Datos de Area validos: "+bValidArea);
	      
	    if(bValidArea==true){
	    	try {
	    		EmpresaFacadeLocal empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
	    		
	    		area = empresaFacade.grabarArea(area);
				log.info("area.id.intIdArea: "+area.getId().getIntIdArea());
				
				
			} catch (EJBFactoryException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			
			limpiarFormArea();
			listarAreas(event);
			setAreaRendered(false);
			setMessageSuccess("Los datos se grabaron satisfactoriamente ");
	    }
	}
    
    public void modificarArea(ActionEvent event){
    	log.info("-----------------------Debugging EmpresaController.modificarArea-----------------------------");
	    Area area = new Area();
	    area = (Area) getBeanArea();
	    
	    Boolean bValidArea = isValidArea(area);
	    log.info("Datos de Area validos: "+bValidArea);
	    
	    if(bValidArea==true){
	    	try {
	    		EmpresaFacadeLocal empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
	    		area = empresaFacade.modificarArea(area);
	    		
				log.info("area.id.intIdArea: "+area.getId().getIntIdArea());
			} catch (EJBFactoryException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}

			limpiarFormArea();
			listarAreas(event);
			setAreaRendered(false);	
			setMessageSuccess("Los datos se actualizaron satisfactoriamente ");
	    }
	}
    
    public void eliminarArea(ActionEvent event) throws DaoException{
    	log.info("-----------------------Debugging EmpresaController.eliminarArea-----------------------------");
		String strIdArea = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdArea");
		String strIdSucursal = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdSucArea");
		String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdEmpArea");		
		log.info("strIdArea: "+strIdArea);
		log.info("strIdSucursal: "+strIdSucursal);
		log.info("strIdEmpresa: "+strIdEmpresa);
		
		if(strIdArea==null || strIdSucursal==null || strIdEmpresa==null){
			log.info("La llave no puede ser nula. Se sale de modificarArea...");
			return;
		}
		
		Area area = new Area();
		area.setId(new AreaId());
		area.getId().setIntPersEmpresaPk(Integer.parseInt(strIdEmpresa));
		area.getId().setIntIdSucursalPk(Integer.parseInt(strIdSucursal));
		area.getId().setIntIdArea(Integer.parseInt(strIdArea));
		
		try {
			EmpresaFacadeLocal empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			area = empresaFacade.getAreaPorPK(area);
			area = empresaFacade.eliminarArea(area);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
		setAreaRendered(false);
		listarAreas(event);
		log.info("Datos de area ("+area.getId().getIntIdArea()+")modificados exitosamente: idEstado = Anulado");
	}
    
    public void listarAreas(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.listarAreas-----------------------------");
		
	    log.info("Seteando los parametros de busqueda de áreas: ");
		log.info("-------------------------------------------------");
		log.info("intCboEmpresaSuc = "+ getIntCboEmpresaSuc());
		log.info("cboTipoArea = "+ getCboTipoArea());
		log.info("cboEstadoArea = "+ getCboEstadoArea());
		log.info("cboSucursal = "+ getCboSucursal());
		log.info("txtArea = "+ getTxtArea());
		log.info("pIntIdArea = "+ null);
		log.info("chkAreaCodigo = "+ getChkAreaCodigo());
		
		Area busqArea = new Area();
		busqArea.setId(new AreaId());
		busqArea.getId().setIntPersEmpresaPk(getIntCboEmpresaSuc().equals(0)?null:getIntCboEmpresaSuc());
		busqArea.getId().setIntIdArea(null);
		busqArea.getId().setIntIdSucursalPk(getCboSucursal().equals(0)?null:getCboSucursal());
		busqArea.setIntIdTipoArea(getCboTipoArea()==0?null:getCboTipoArea());
		busqArea.setStrDescripcion(getTxtArea().equals("")?null:getTxtArea());
		busqArea.setIntIdEstado(getCboEstadoArea()==0?null:getCboEstadoArea());
		busqArea.setChecked(chkAreaCodigo);
	    
	    ArrayList listaAreas = new ArrayList();
		try {
			EmpresaFacadeLocal empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			listaAreas = (ArrayList) empresaFacade.getListaArea(busqArea);
			
			
			
			log.info("listaEmpresa.size: "+listaAreas.size());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setMessageSuccess("Listado de areas ha sido satisfactorio");
		
		for(int i=0; i<listaAreas.size(); i++){
			AreaComp areaComp = (AreaComp) listaAreas.get(i);
			
			log.info("IDAREA = "+areaComp.getArea().getId().getIntIdArea());
			log.info("AREA_DESCRIPCION = "+areaComp.getArea().getStrDescripcion());
			log.info("EMPR_IDEMPRESA_N = "+areaComp.getArea().getId().getIntPersEmpresaPk());
			log.info("JURI_RAZONSOCIAL_V = "+areaComp.getEmpresa().getJuridica().getStrRazonSocial());
			log.info("SUCU_IDSUCURSAL_N = "+areaComp.getArea().getId().getIntIdSucursalPk());
			log.info("V_SUCURSAL = "+areaComp.getSucursal().getJuridica().getStrRazonSocial());
			log.info("AREA_IDESTADO_N = "+areaComp.getArea().getIntIdEstado());
			log.info("IDTIPOAREA = "+areaComp.getArea().getIntIdTipoArea());
			
			
		}
		
		setBeanListAreas(listaAreas);
		/*String idArea = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdArea");
		if(idArea != null){
			listarAreaCodigo(event);
		}*/		
		//limpiarFormArea();
	}
    
    public void listarAreaCodigo(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.listarAreaCodigo-----------------------------");
	    setService(empresaService);
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de área código: ");
		log.info("-------------------------------------------------");
		HashMap prmtBusqAreaCodigo = new HashMap();
		int idArea = 0;
		int idSucursal = 0;
		int idEmpresa = 0;
		if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdArea")!=null && 
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdSucArea")!=null && 
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdEmpArea")!=null){
			idArea = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdArea"));
			idSucursal = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdSucArea"));
			idEmpresa = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdEmpArea"));
		}	
		if(getIntCboEmpresa()!=null && getIntCboSucursal()!=null){
			idEmpresa = getIntCboEmpresa();
			idSucursal = getIntCboSucursal();
			idArea = codArea;
		}		
		//int idEmpresa 	= Integer.parseInt(getHiddenIdEmpArea() == null ? "0" :getHiddenIdEmpArea());
		//int idSucursal 	= Integer.parseInt(getHiddenIdSucArea() == null ? "0" :getHiddenIdSucArea());
		//int idArea 		= Integer.parseInt(getHiddenIdArea() == null ? "0" :getHiddenIdArea());
		log.info("pIntIdEmpresa  = "+ idEmpresa);
		log.info("pIntIdSucursal = "+ idSucursal);
		log.info("pIntIdArea	 = "+ idArea);
		prmtBusqAreaCodigo.put("pIntIdEmpresa",  idEmpresa);
		prmtBusqAreaCodigo.put("pIntIdSucursal", idSucursal);
		prmtBusqAreaCodigo.put("pIntIdArea", 	 idArea);
		
	    ArrayList arrayAreaCodigo = new ArrayList();
	    ArrayList listaAreaCodigo = new ArrayList();
	    try {
	    	arrayAreaCodigo = getService().listarAreaCodigo(prmtBusqAreaCodigo);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarAreaCodigo() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMessageSuccess("Listado de domicilios ha sido satisfactorio");
		
		for(int i=0; i<arrayAreaCodigo.size(); i++){
			HashMap hash = (HashMap) arrayAreaCodigo.get(i);
			AreaCodigo arcod = new AreaCodigo();
			log.info("EMPR_IDEMPRESA_N = "+arcod.getId().getIntIdEmpresaPk());
			log.info("SUCU_IDSUCURSAL_N = "+arcod.getId().getIntIdSucursalPk());
			log.info("AREA_IDAREA_N = "+arcod.getId().getIntIdAreaPk());
			log.info("ARCO_IDTIPOCODIGO_N = "+arcod.getId().getIntIdTipoCodigoPk());
			log.info("ARCO_CODIGO_N = "+arcod.getStrCodigo());
			listaAreaCodigo.add(arcod);
		}
		for(int i=0;i<getArrayAreaCodigo().size();i++){
			AreaCodigo arcod = new AreaCodigo();
			arcod = (AreaCodigo) getArrayAreaCodigo().get(i);
			listaAreaCodigo.add(arcod);
		}
		
		setBeanListAreacodigo(listaAreaCodigo);
	}
    
    public Boolean validarAreaCodigo(AreaCodigo areaCodigo){
    	log.info("-----------------------Debugging EmpresaController.validarAreaCodigo-----------------------------"+areaCodigo.getStrDescripcion());
    	boolean bValidar = true;
	    if(areaCodigo.getStrDescripcion().trim().equals("")){
	    	setMessageError("* Debe completar el campo Nombre de Área.");
	    	bValidar = false;
	    }else{
	    	setMessageError("");
	    }
	    if(areaCodigo.getStrCodigo().trim().equals("")){
	    	setMessageError("* Debe completar el campo Código de Área.");
	    	bValidar = false;
	    }else{
	    	setMessageError("");
	    }
	    return bValidar;
	}
    
    public void addAreaCodigo(ActionEvent event) {
    	log.info("-----------------------Debugging EmpresaController.addAreaCodigo-----------------------------");
    	List<AreaCodigo> listaAreaCodigo = getBeanArea().getListaAreaCodigo();
    	if(listaAreaCodigo==null){
    		listaAreaCodigo = new ArrayList<AreaCodigo>();
    	}
    	log.info("listaAreaCodigo.size: "+listaAreaCodigo.size());
    	if(listaAreaCodigo.size()>0){
    		AreaCodigo areaCodigo = listaAreaCodigo.get(listaAreaCodigo.size()-1);
    		if(validarAreaCodigo(areaCodigo)==false){    	
    			return;
    		}
    	}
    	listaAreaCodigo.add(new AreaCodigo());
    	//setShowModalPanelDomicilio(false);
    	//limpiarFormAreaCodigo();
    }
    
    public void listarZonal(ActionEvent event){
    	log.info("-----------------------Debugging EmpresaController.listarZonal-----------------------------");
    	obtenerZonales();
    }
    
    public void obtenerZonales(){
		log.info("-----------------------Debugging EmpresaController.obtenerZonales-----------------------------");
		String lStrInPk = null;
		pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote facade = null;
		try {
			facade = (pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote)EJBFactory.getRemote(pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote.class);
			Zonal o = new Zonal();
			o.setId(new ZonalId());
			o.setJuridica(new Juridica());
			if(cboEmpresaZonal!=0){
				o.getId().setIntPersEmpresaPk(cboEmpresaZonal);
			}else{
				o.getId().setIntPersEmpresaPk(null);
			}
			if(cboTipoZonal!=0){
				o.setIntIdTipoZonal(cboTipoZonal);
			}else{
				o.setIntIdTipoZonal(null);
			}
			if(cboEstadoZonal!=0){
				o.setIntIdEstado(cboEstadoZonal);
			}else{
				o.setIntIdEstado(null);
			}
			if(txtZonal != null && !txtZonal.trim().equals("")){
				o.getJuridica().setStrRazonSocial(txtZonal);
			}else{
				o.getJuridica().setStrRazonSocial(null);
			}	
			listaZonalComp = facade.getListaZonalCompDeBusquedaZonal(o);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	    /*setService(empresaService);
	    log.info("Se ha seteado el Service");
		
	    log.info("Seteando los parametros de busqueda de áreas: ");
		log.info("-------------------------------------------------");
		HashMap prmtBusqZonal = new HashMap();
		log.info("pTxtEmpresaZonal ="+ getCboEmpresaZonal());
		log.info("pCboTipoZonal = 	"+ getCboTipoZonal());
		log.info("pCboEstadoZonal = "+ getCboEstadoZonal());
		log.info("pTxtZonal = 		"+ getTxtZonal());
		String codEmpresa = "";
		if(getCboEmpresaZonal()==0){
			codEmpresa = "";
		}else{
			codEmpresa = ""+getCboEmpresaZonal();
		}
		
		prmtBusqZonal.put("pIntIdPersona", 		null);
		prmtBusqZonal.put("pIntIdZonal", 		null);
		prmtBusqZonal.put("pTxtEmpresaZonal", 	codEmpresa);//getTxtEmpresaZonal());
		prmtBusqZonal.put("pCboTipoZonal", 		getCboTipoZonal().equals("0")?null:getCboTipoZonal());
		prmtBusqZonal.put("pCboEstadoZonal", 	getCboEstadoZonal().equals("0")?null:getCboEstadoZonal());
		prmtBusqZonal.put("pTxtZonal", 			getTxtZonal());
	    
	    ArrayList arrayZonal = new ArrayList();
	    ArrayList listaZonal = new ArrayList();
	    try {
	    	arrayZonal = getService().listarZonal(prmtBusqZonal);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarZonal() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("arrayZonal.size(): "+arrayZonal.size());
		setMessageSuccess("Listado de Zonales ha sido satisfactorio");
		*/
		/*
		for(int i=0; i<arrayZonal.size(); i++){
			HashMap hash = (HashMap) arrayZonal.get(i);
			Zonal zon = new Zonal();
			log.info("ZONA_IDEMPRESA_N = "+hash.get("ZONA_IDEMPRESA_N"));
			int idEmpresa = Integer.parseInt("" + hash.get("ZONA_IDEMPRESA_N"));
			zon.setIntIdEmpresa(idEmpresa);
			log.info("ZONA_IDZONAL_N = "+hash.get("ZONA_IDZONAL_N"));
			int idZonal = Integer.parseInt("" + hash.get("ZONA_IDZONAL_N"));
			zon.setIntIdZonal(idZonal);
			log.info("ZONA_NOMBREZONA_V = "+hash.get("ZONA_NOMBREZONA_V"));
			String strNombreZona = "" + hash.get("ZONA_NOMBREZONA_V");
			zon.setStrNombreZonal(strNombreZona);
			log.info("ZONA_IDTIPOZONAL_N = "+hash.get("ZONA_IDTIPOZONAL_N"));
			int idTipoZonal = Integer.parseInt("" + hash.get("ZONA_IDTIPOZONAL_N"));
			zon.setIntIdTipoZonal(idTipoZonal);
			log.info("ZONA_ABREVIATURA_V = "+hash.get("ZONA_ABREVIATURA_V"));
			String strAbreviatura = "" + hash.get("ZONA_ABREVIATURA_V");
			zon.setStrAbreviatura(strAbreviatura);
			log.info("V_EMPRESA = "+hash.get("V_EMPRESA"));
			String strEmpresa = "" + hash.get("V_EMPRESA");
			zon.setStrNombreEmpresa(strEmpresa);
			log.info("NRO_SUCURSALES = "+hash.get("NRO_SUCURSALES"));
			int intNroSucursales = Integer.parseInt("" + hash.get("NRO_SUCURSALES"));
			zon.setIntNroSucursales(intNroSucursales);
			log.info("TIPO_ZONA = "+hash.get("TIPO_ZONA"));
			String strTipozona = "" + hash.get("TIPO_ZONA");
			zon.setStrTipozona(strTipozona);
			listaZonal.add(zon);
		}*/
		//setBeanListZonal(listaZonal);
	}
    
    public void listarPerNatural(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.listarPerNatural-----------------------------");
	    setService(empresaService);
	    log.info("Se ha seteado el Service");
		
	    log.info("Seteando los parametros de busqueda de per_natural: ");
		log.info("----------------------------------------------------");
		HashMap prmtBusqPerNat = new HashMap();
	    
	    ArrayList arrayPerNatural = new ArrayList();
	    ArrayList listaPerNatural = new ArrayList();
	    try {
	    	arrayPerNatural = getService().listarPerNatural(prmtBusqPerNat);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarPerNatural() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMessageSuccess("Listado de Personas Naturales ha sido satisfactorio");
		
		for(int i=0; i<arrayPerNatural.size(); i++){
			HashMap hash = (HashMap) arrayPerNatural.get(i);
			PerNatural pnatu = new PerNatural();
			log.info("PERS_IDPERSONA_N = "+hash.get("PERS_IDPERSONA_N"));
			int idPersona = Integer.parseInt("" + hash.get("PERS_IDPERSONA_N"));
			pnatu.setIntIdPersona(idPersona);
			log.info("strApepat = "+hash.get("NATU_APELLIDOPATERNO_C"));
			String strApepat = "" + hash.get("NATU_APELLIDOPATERNO_C");
			pnatu.setStrApePat(strApepat);
			log.info("strApemat = "+hash.get("NATU_APELLIDOMATERNO_C"));
			String strApemat = "" + hash.get("NATU_APELLIDOMATERNO_C");
			pnatu.setStrApeMat(strApemat);
			log.info("strNombres = "+hash.get("NATU_NOMBRES_C"));
			String strNombres = "" + hash.get("NATU_NOMBRES_C");
			pnatu.setStrNombres(strApemat);
			
			listaPerNatural.add(pnatu);
		}
		setBeanListZonal(listaPerNatural);
	}
    
    public void inicioZonal(ActionEvent event){
    	limpiarFormZonal();
    }
    
    public void irModificarZonal(ActionEvent event){
    	EmpresaFacadeLocal facade = null;
    	Integer intIdEmpresa = null;
    	//String strIdZonal = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmZonalPanel:hiddenIdZonal");
    	String lStrIdZonal = null;
    	try {
    		lStrIdZonal = strIdZonal;
    		if(lStrIdZonal != null && !lStrIdZonal.trim().equals("")){
				facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
				beanZonal = facade.getZonalSucursalPorIdZonal(new Integer(lStrIdZonal));
				if(beanZonal != null){
					intIdEmpresa = beanZonal.getId().getIntPersEmpresaPk();
					if(intIdEmpresa!=0){
						facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
						listaNaturalUsuario = facade.getListaNaturalDeUsuarioPorIdEmpresa(intIdEmpresa);
						beanZonal.setListaSucursal(facade.getListaSucursalSinZonalPorPkEmpresa(intIdEmpresa));
						beanZonal.getListaSucursal().addAll(facade.getListaSucursalPorPkZonal(new Integer(lStrIdZonal)));
						listaSucursalDeZonal = null;  
					}
					setZonalRendered(true);
					strTipoMantZonal = Constante.MANTENIMIENTO_MODIFICAR;
				}
    		}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
    
    
    public void irModificarArea(ActionEvent event){
		log.info("-----------------------Debugging EmpresaController.irModificarArea-----------------------------");
		String strIdArea = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdArea");
		String strIdSucursal = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdSucArea");
		String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAreaModalPanel:hiddenIdEmpArea");		
		log.info("strIdArea: "+strIdArea);
		log.info("strIdSucursal: "+strIdSucursal);
		log.info("strIdEmpresa: "+strIdEmpresa);
		if(strIdArea==null || strIdSucursal==null || strIdEmpresa==null){
			log.info("La llave no puede ser nula. Se sale de modificarArea...");
			return;
		}
		
		Area area = new Area();
		area.setId(new AreaId());
		area.getId().setIntPersEmpresaPk(Integer.parseInt(strIdEmpresa));
		area.getId().setIntIdSucursalPk(Integer.parseInt(strIdSucursal));
		area.getId().setIntIdArea(Integer.parseInt(strIdArea));
		
		try {
			EmpresaFacadeLocal empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			area = empresaFacade.getAreaPorPK(area);
			listaJuridicaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(area.getId().getIntPersEmpresaPk());
			
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
		
		log.info("C_IDAREA = "+area.getId().getIntIdArea());
		log.info("AREA_DESCRIPCION_V = "+area.getStrDescripcion());
		log.info("EMPR_IDEMPRESA_N = "+area.getId().getIntPersEmpresaPk());
		log.info("SUCU_IDSUCURSAL_N = "+area.getId().getIntIdSucursalPk());
		log.info("AREA_IDESTADO_N = "+area.getIntIdEstado());
		log.info("C_IDTIPOAREA = "+area.getIntIdTipoArea());
		log.info("AREA_ABREVIATURA_C = "+area.getStrAbreviatura());
		
		log.info("area.listaAreaCodigo.size: "+area.getListaAreaCodigo().size());
		
		
		 if (area.getIntIdEstado().equals(3)){
			 setFormAreaDisabled(true);
		 }
		 else{
			 setFormAreaDisabled(false);
		 }
			 
		setFormAreaEnabled(true);
		setBeanArea(area);
		setAreaRendered(true);
		setStrTipoMantArea(Constante.MANTENIMIENTO_MODIFICAR);
		//habilitarGrabarSucursal(event);
		
		/*if(listAreacodigo != null){
			listAreacodigo.clear();
		}*/		
		log.info("Datos de area ("+area.getId().getIntIdArea()+")cargados exitosamente.");
	}
    
    /*
    public void onchangueSucursalSondeo(ActionEvent event){
    	//TODO se tiene idPersona de la sucursal falta traer el id de la sucursal cuyda idpersona corresponda
    	beanZonal.sucursal.id.intIdSucursal
    }
    */
    public void eliminarZonal(ActionEvent event) throws DaoException{
		String strIdZonal = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmZonalPanel:hiddenIdZonal");
		Integer intIdzonal = null;
		EmpresaFacadeLocal facade = null;
    	try {
    		intIdzonal = new Integer(strIdZonal);
			facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			facade.eliminarZonalPorIdZonal(intIdzonal);
			setMessageSuccess("Los datos de zonal se eliminaron satisfactoriamente ");
			limpiarFormZonal();
			listarZonal(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
    
    public void modificarZonal(ActionEvent event){
    	EmpresaFacadeLocal facade = null;
    	if(isValidoZonal(beanZonal) == false){
    		log.info("Datos de zonal no válidos. Se aborta el proceso de grabación de zonal.");
    		return;
    	}
    	try {
			facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			facade.modificarZonalYListaSucursal(beanZonal, listaSucursalDeZonal);
			setMessageSuccess("Los datos de zonal se actualizaron satisfactoriamente ");
			limpiarFormZonal();
			listarZonal(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
    
    public void enableDisable(ActionEvent event) throws DaoException{
		log.info("----------------Debugging EmpresaController.enableDisable-------------------");
		setFormSucursalEnabled(getChkTercero()==false);
	}
    
    public void enableDisable1(ActionEvent event) throws DaoException{
		log.info("----------------Debugging EmpresaController.enableDisable-------------------");
		setFormSucursalEnabled2(getChkSubsucursal()==false);
	}
    
    public void enableDisable2(ActionEvent event) throws DaoException{
		log.info("----------------Debugging EmpresaController.enableDisable-------------------");
		setFormAreaEnabled(getChkCodigo()==false);
	}
    /*
    
    public void reloadCboSucursales(ValueChangeEvent event) throws DaoException{
    	
	}
    
    public void cargarListaSucursales(String codigoEmpresa){
		SelectItem opcionSeleccionar = new SelectItem(Constante.NO_OPTION_SELECTED, Constante.OPTION_SELECT);
		String condicion = "fabc_nid = '"+codigoEmpresa+"' ";
		//listarSucursales = getService().listarSucursales();
		GenericService listarSucursales = (GenericService)getSpringBean("listarSucursales");
		List empresaChildren;
		try {
			empresaChildren = listarSucursales.findByNamedQuery(condicion);
			listaSucursales = new ArrayList();
			if(empresaChildren.size()>0){
				listaSucursales.addAll(ItemHelper.listToListOfSelectItems(empresaChildren, Constante.PERSISTENCE_FIELD_ID , "descripcion"));
			}
			listaSucursales.add(opcionSeleccionar);
			listaSucursales = ItemHelper.sortSelectItemList(listaSucursales);
		} catch (DaoException e) {
			setMessageError("No se realizó conexión con BD");
			e.printStackTrace();
		}
	}*/
    
	public String getMsgNomVia() {
		return msgNomVia;
	}

	public void setMsgNomVia(String msgNomVia) {
		this.msgNomVia = msgNomVia;
	}

	public String getMsgNumVia() {
		return msgNumVia;
	}

	public void setMsgNumVia(String msgNumVia) {
		this.msgNumVia = msgNumVia;
	}

	public String getMsgNomZona() {
		return msgNomZona;
	}

	public void setMsgNomZona(String msgNomZona) {
		this.msgNomZona = msgNomZona;
	}
	
	public Boolean getChkCodigo() {
		return chkCodigo;
	}

	public void setChkCodigo(Boolean chkCodigo) {
		this.chkCodigo = chkCodigo;
	}
	
	public int getCodArea() {
		return codArea;
	}

	public void setCodArea(int codArea) {
		this.codArea = codArea;
	}

	public EmpresaServiceImpl getEmpresaService() {
		return empresaService;
	}

	public void setEmpresaService(EmpresaServiceImpl empresaService) {
		this.empresaService = empresaService;
	}
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	public Boolean getFormEmpresaEnabled() {
		return formEmpresaEnabled;
	}

	public void setFormEmpresaEnabled(Boolean formEmpresaEnabled) {
		this.formEmpresaEnabled = formEmpresaEnabled;
	}

	public Integer getCboEmpresa() {
		return cboEmpresa;
	}

	public void setCboEmpresa(Integer cboEmpresa) {
		this.cboEmpresa = cboEmpresa;
	}

	public Integer getIntCboSucursalEmp() {
		return intCboSucursalEmp;
	}

	public void setIntCboSucursalEmp(Integer intCboSucursalEmp) {
		this.intCboSucursalEmp = intCboSucursalEmp;
	}

	public String getTxtRuc() {
		return txtRuc;
	}

	public void setTxtRuc(String txtRuc) {
		this.txtRuc = txtRuc;
	}

	public Integer getCboTipoEmpresa() {
		return cboTipoEmpresa;
	}

	public void setCboTipoEmpresa(Integer cboTipoEmpresa) {
		this.cboTipoEmpresa = cboTipoEmpresa;
	}
	public Integer getCboEstadoEmpresa() {
		return cboEstadoEmpresa;
	}

	public void setCboEstadoEmpresa(Integer cboEstadoEmpresa) {
		this.cboEstadoEmpresa = cboEstadoEmpresa;
	}

	public Integer getIntCboEmpresaSuc() {
		return intCboEmpresaSuc;
	}

	public void setIntCboEmpresaSuc(Integer intCboEmpresaSuc) {
		this.intCboEmpresaSuc = intCboEmpresaSuc;
	}

	public Empresa getBeanEmpresa() {
		return beanEmpresa;
	}

	public void setBeanEmpresa(Empresa beanEmpresa) {
		this.beanEmpresa = beanEmpresa;
	}
	public Sucursal getBeanSucursal() {
		return beanSucursal;
	}

	public void setBeanSucursal(Sucursal beanSucursal) {
		this.beanSucursal = beanSucursal;
	}

	public Area getBeanArea() {
		return beanArea;
	}

	public void setBeanArea(Area beanArea) {
		this.beanArea = beanArea;
	}

	public Zonal getBeanZonal() {
		return beanZonal;
	}

	public void setBeanZonal(Zonal beanZonal) {
		this.beanZonal = beanZonal;
	}

	public SucursalCodigo getBeanSucursalCodigo() {
		return beanSucursalCodigo;
	}

	public void setBeanSucursalCodigo(SucursalCodigo beanSucursalCodigo) {
		this.beanSucursalCodigo = beanSucursalCodigo;
	}

	public Subsucursal getBeanSubsucursal() {
		return beanSubsucursal;
	}

	public void setBeanSubsucursal(Subsucursal beanSubsucursal) {
		this.beanSubsucursal = beanSubsucursal;
	}

	public AreaCodigo getBeanAreacodigo() {
		return beanAreacodigo;
	}

	public void setBeanAreacodigo(AreaCodigo beanAreacodigo) {
		this.beanAreacodigo = beanAreacodigo;
	}
	
	public Boolean getFormSucursalEnabled() {
		return formSucursalEnabled;
	}

	public void setFormSucursalEnabled(Boolean formSucursalEnabled) {
		this.formSucursalEnabled = formSucursalEnabled;
	}
	
	public Boolean getFormSucursalEnabled2() {
		return formSucursalEnabled2;
	}

	public void setFormSucursalEnabled2(Boolean formSucursalEnabled2) {
		this.formSucursalEnabled2 = formSucursalEnabled2;
	}

	public Boolean getFormAreaEnabled() {
		return formAreaEnabled;
	}

	public void setFormAreaEnabled(Boolean formAreaEnabled) {
		this.formAreaEnabled = formAreaEnabled;
	}

	public Boolean getFormAreaEnabled2() {
		return formAreaEnabled2;
	}

	public void setFormAreaEnabled2(Boolean formAreaEnabled2) {
		this.formAreaEnabled2 = formAreaEnabled2;
	}

	public List getBeanListTerceros() {
		return beanListTerceros;
	}

	public void setBeanListTerceros(List beanListTerceros) {
		this.beanListTerceros = beanListTerceros;
	}

	public List getBeanListSucursales() {
		return beanListSucursales;
	}

	public void setBeanListSucursales(List beanListSucursales) {
		this.beanListSucursales = beanListSucursales;
	}
	
	public List getBeanListSucurZonal() {
		return beanListSucurZonal;
	}

	public List getBeanListAreas() {
		return beanListAreas;
	}

	public void setBeanListAreas(List beanListAreas) {
		this.beanListAreas = beanListAreas;
	}

	public List getBeanListSubsucursal() {
		return beanListSubsucursal;
	}

	public void setBeanListSubsucursal(List beanListSubsucursal) {
		this.beanListSubsucursal = beanListSubsucursal;
	}

	public List getBeanListAreacodigo() {
		return beanListAreacodigo;
	}

	public void setBeanListAreacodigo(List beanListAreacodigo) {
		this.beanListAreacodigo = beanListAreacodigo;
	}

	public List<AreaCodigo> getListAreaCodigo() {
		return listAreaCodigo;
	}

	public void setListAreaCodigo(List<AreaCodigo> listAreaCodigo) {
		this.listAreaCodigo = listAreaCodigo;
	}

	public String getColorTxtEnabled() {
		return colorTxtEnabled;
	}

	public void setColorTxtEnabled(String colorTxtEnabled) {
		this.colorTxtEnabled = colorTxtEnabled;
	}

	public Boolean getEmpresaRendered() {
		return empresaRendered;
	}

	public void setEmpresaRendered(Boolean empresaRendered) {
		this.empresaRendered = empresaRendered;
	}
	
	public Boolean getDireccionRendered() {
		return direccionRendered;
	}

	public void setDireccionRendered(Boolean direccionRendered) {
		this.direccionRendered = direccionRendered;
	}

	public Boolean getAdjuntoRendered() {
		return adjuntoRendered;
	}

	public void setAdjuntoRendered(Boolean adjuntoRendered) {
		this.adjuntoRendered = adjuntoRendered;
	}

	public Boolean getSucursalRendered() {
		return sucursalRendered;
	}

	public void setSucursalRendered(Boolean sucursalRendered) {
		this.sucursalRendered = sucursalRendered;
	}

	public Boolean getAreaRendered() {
		return areaRendered;
	}

	public void setAreaRendered(Boolean areaRendered) {
		this.areaRendered = areaRendered;
	}

	public String getMsgTxtEmpresa() {
		return msgTxtEmpresa;
	}

	public void setMsgTxtEmpresa(String msgTxtEmpresa) {
		this.msgTxtEmpresa = msgTxtEmpresa;
	}
	
	public String getMsgTxtAbreviatura() {
		return msgTxtAbreviatura;
	}

	public void setMsgTxtAbreviatura(String msgTxtAbreviatura) {
		this.msgTxtAbreviatura = msgTxtAbreviatura;
	}

	public String getMsgTxtRuc() {
		return msgTxtRuc;
	}

	public void setMsgTxtRuc(String msgTxtRuc) {
		this.msgTxtRuc = msgTxtRuc;
	}

	public String getMsgTipoEmpresa() {
		return msgTipoEmpresa;
	}

	public void setMsgTipoEmpresa(String msgTipoEmpresa) {
		this.msgTipoEmpresa = msgTipoEmpresa;
	}

	public String getMsgTipoSucursal() {
		return msgTipoSucursal;
	}

	public void setMsgTipoSucursal(String msgTipoSucursal) {
		this.msgTipoSucursal = msgTipoSucursal;
	}

	public String getMsgEstadoEmp() {
		return msgEstadoEmp;
	}

	public void setMsgEstadoEmp(String msgEstadoEmp) {
		this.msgEstadoEmp = msgEstadoEmp;
	}

	public String getMsgTiempoSesion() {
		return msgTiempoSesion;
	}

	public void setMsgTiempoSesion(String msgTiempoSesion) {
		this.msgTiempoSesion = msgTiempoSesion;
	}

	public String getSetMsgAlertaSesion() {
		return msgAlertaSesion;
	}

	public void setMsgAlertaSesion(String msgAlertaSesion) {
		this.msgAlertaSesion = msgAlertaSesion;
	}

	public String getMsgVigenciaClaves() {
		return msgVigenciaClaves;
	}

	public void setMsgVigenciaClaves(String msgVigenciaClaves) {
		this.msgVigenciaClaves = msgVigenciaClaves;
	}

	public String getMsgAlertaCaducidad() {
		return msgAlertaCaducidad;
	}

	public void setMsgAlertaCaducidad(String msgAlertaCaducidad) {
		this.msgAlertaCaducidad = msgAlertaCaducidad;
	}
	
	public String getMsgTiempoSesionError() {
		return msgTiempoSesionError;
	}

	public void setMsgTiempoSesionError(String msgTiempoSesionError) {
		this.msgTiempoSesionError = msgTiempoSesionError;
	}

	public String getMsgAlertaSesion() {
		return msgAlertaSesion;
	}

	public Boolean getRenderSelectError() {
		return renderSelectError;
	}

	public void setRenderSelectError(Boolean renderSelectError) {
		this.renderSelectError = renderSelectError;
	}

	public Boolean getRenderTiempoSesionError() {
		return renderTiempoSesionError;
	}

	public void setRenderTiempoSesionError(Boolean renderTiempoSesionError) {
		this.renderTiempoSesionError = renderTiempoSesionError;
	}

	public Boolean getRenderTiempoSesionInvalido() {
		return renderTiempoSesionInvalido;
	}

	public void setRenderTiempoSesionInvalido(Boolean renderTiempoSesionInvalido) {
		this.renderTiempoSesionInvalido = renderTiempoSesionInvalido;
	}

	public Boolean getRenderVigenciaError() {
		return renderVigenciaError;
	}

	public void setRenderVigenciaError(Boolean renderVigenciaError) {
		this.renderVigenciaError = renderVigenciaError;
	}

	public String getMsgVigenciaError() {
		return msgVigenciaError;
	}

	public void setMsgVigenciaError(String msgVigenciaError) {
		this.msgVigenciaError = msgVigenciaError;
	}

	public Boolean getRenderVigenciaInvalido() {
		return renderVigenciaInvalido;
	}

	public void setRenderVigenciaInvalido(Boolean renderVigenciaInvalido) {
		this.renderVigenciaInvalido = renderVigenciaInvalido;
	}

	public String getMsgIntentosError() {
		return msgIntentosError;
	}

	public void setMsgIntentosError(String msgIntentosError) {
		this.msgIntentosError = msgIntentosError;
	}

	public Boolean getRenderIntentosError() {
		return renderIntentosError;
	}

	public void setRenderIntentosError(Boolean renderIntentosError) {
		this.renderIntentosError = renderIntentosError;
	}

	public String getHiddenIdArea() {
		return hiddenIdArea;
	}

	public void setHiddenIdArea(String hiddenIdArea) {
		this.hiddenIdArea = hiddenIdArea;
	}

	public String getHiddenIdSucursal() {
		return hiddenIdSucursal;
	}

	public void setHiddenIdSucursal(String hiddenIdSucursal) {
		this.hiddenIdSucursal = hiddenIdSucursal;
	}

	public String getHiddenIdEmpArea() {
		return hiddenIdEmpArea;
	}

	public void setHiddenIdEmpArea(String hiddenIdEmpArea) {
		this.hiddenIdEmpArea = hiddenIdEmpArea;
	}

	public String getHiddenIdSucArea() {
		return hiddenIdSucArea;
	}

	public void setHiddenIdSucArea(String hiddenIdSucArea) {
		this.hiddenIdSucArea = hiddenIdSucArea;
	}

	public Boolean getShowModalPanelEmpresa() {
		return showModalPanelEmpresa;
	}

	public void setShowModalPanelEmpresa(Boolean showModalPanelEmpresa) {
		this.showModalPanelEmpresa = showModalPanelEmpresa;
	}

	public Boolean getShowModalPanelArea() {
		return showModalPanelArea;
	}

	public void setShowModalPanelArea(Boolean showModalPanelArea) {
		this.showModalPanelArea = showModalPanelArea;
	}

	public Integer getCboEstadoSucursal() {
		return cboEstadoSucursal;
	}

	public void setCboEstadoSucursal(Integer cboEstadoSucursal) {
		this.cboEstadoSucursal = cboEstadoSucursal;
	}

	public String getStrCboEmpresaArea() {
		return strCboEmpresaArea;
	}

	public void setStrCboEmpresaArea(String strCboEmpresaArea) {
		this.strCboEmpresaArea = strCboEmpresaArea;
	}

	public Integer getCboTipoArea() {
		return cboTipoArea;
	}

	public void setCboTipoArea(Integer cboTipoArea) {
		this.cboTipoArea = cboTipoArea;
	}

	public Integer getCboEstadoArea() {
		return cboEstadoArea;
	}

	public void setCboEstadoArea(Integer cboEstadoArea) {
		this.cboEstadoArea = cboEstadoArea;
	}

	public Integer getCboSucursal() {
		return cboSucursal;
	}

	public void setCboSucursal(Integer cboSucursal) {
		this.cboSucursal = cboSucursal;
	}
	
	public Boolean getChkAreaCodigo() {
		return chkAreaCodigo;
	}

	public void setChkAreaCodigo(Boolean chkAreaCodigo) {
		this.chkAreaCodigo = chkAreaCodigo;
	}

	public String getTxtArea() {
		return txtArea;
	}

	public void setTxtArea(String txtArea) {
		this.txtArea = txtArea;
	}

	public HtmlSelectOneMenu getSelectMenuBound() {
		return selectMenuBound;
	}

	public void setSelectMenuBound(HtmlSelectOneMenu selectMenuBound) {
		this.selectMenuBound = selectMenuBound;
	}

	public HtmlSelectOneMenu getSelectMenuBound2() {
		return selectMenuBound2;
	}

	public void setSelectMenuBound2(HtmlSelectOneMenu selectMenuBound2) {
		this.selectMenuBound2 = selectMenuBound2;
	}

	public List getListaEmpresas() {
		return listaEmpresas;
	}

	public void setListaEmpresas(List listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}

	public List getListaSucursales() {
		return listaSucursales;
	}

	public void setListaSucursales(List listaSucursales) {
		this.listaSucursales = listaSucursales;
	}

	public String getStrNombreAreaCod() {
		return strNombreAreaCod;
	}

	public void setStrNombreAreaCod(String strNombreAreaCod) {
		this.strNombreAreaCod = strNombreAreaCod;
	}

	public String getStrCboActivoAreaCod() {
		return strCboActivoAreaCod;
	}

	public void setStrCboActivoAreaCod(String strCboActivoAreaCod) {
		this.strCboActivoAreaCod = strCboActivoAreaCod;
	}

	public String getStrCodigoArea() {
		return strCodigoArea;
	}

	public void setStrCodigoArea(String strCodigoArea) {
		this.strCodigoArea = strCodigoArea;
	}

	public Integer getCboEmpresaZonal() {
		return cboEmpresaZonal;
	}

	public void setCboEmpresaZonal(Integer cboEmpresaZonal) {
		this.cboEmpresaZonal = cboEmpresaZonal;
	}

	public Integer getCboTipoZonal() {
		return cboTipoZonal;
	}

	public void setCboTipoZonal(Integer cboTipoZonal) {
		this.cboTipoZonal = cboTipoZonal;
	}

	public Integer getCboEstadoZonal() {
		return cboEstadoZonal;
	}

	public void setCboEstadoZonal(Integer cboEstadoZonal) {
		this.cboEstadoZonal = cboEstadoZonal;
	}

	public String getTxtZonal() {
		return txtZonal;
	}

	public void setTxtZonal(String txtZonal) {
		this.txtZonal = txtZonal;
	}

	public Boolean getChkTerceroFiltro() {
		return chkTerceroFiltro;
	}

	public void setChkTerceroFiltro(Boolean chkTerceroFiltro) {
		this.chkTerceroFiltro = chkTerceroFiltro;
	}

	public Boolean getChkSubsucursalFiltro() {
		return chkSubsucursalFiltro;
	}

	public void setChkSubsucursalFiltro(Boolean chkSubsucursalFiltro) {
		this.chkSubsucursalFiltro = chkSubsucursalFiltro;
	}

	public Boolean getChkTercero() {
		return chkTercero;
	}

	public void setChkTercero(Boolean chkTercero) {
		this.chkTercero = chkTercero;
	}

	public Boolean getChkSubsucursal() {
		return chkSubsucursal;
	}

	public void setChkSubsucursal(Boolean chkSubsucursal) {
		this.chkSubsucursal = chkSubsucursal;
	}

	public String getMsgTxtSucursal() {
		return msgTxtSucursal;
	}

	public void setMsgTxtSucursal(String msgTxtSucursal) {
		this.msgTxtSucursal = msgTxtSucursal;
	}

	public void setStrMinutosSesion(String strMinutosSesion) {
		this.strMinutosSesion = strMinutosSesion;
	}

	public String getStrSegundosSesion() {
		return strSegundosSesion;
	}

	public void setStrSegundosSesion(String strSegundosSesion) {
		this.strSegundosSesion = strSegundosSesion;
	}

	public String getStrHoraAlerta() {
		return strHoraAlerta;
	}

	public void setStrHoraAlerta(String strHoraAlerta) {
		this.strHoraAlerta = strHoraAlerta;
	}

	public String getStrMinutosAlerta() {
		return strMinutosAlerta;
	}

	public void setStrMinutosAlerta(String strMinutosAlerta) {
		this.strMinutosAlerta = strMinutosAlerta;
	}

	public String getStrSegundosAlerta() {
		return strSegundosAlerta;
	}

	public void setStrSegundosAlerta(String strSegundosAlerta) {
		this.strSegundosAlerta = strSegundosAlerta;
	}

	public String getStrRadioVigenciaClaves() {
		return strRadioVigenciaClaves;
	}

	public void setStrRadioVigenciaClaves(String strRadioVigenciaClaves) {
		this.strRadioVigenciaClaves = strRadioVigenciaClaves;
	}

	public String getStrRadioIntentosIngreso() {
		return strRadioIntentosIngreso;
	}

	public void setStrRadioIntentosIngreso(String strRadioIntentosIngreso) {
		this.strRadioIntentosIngreso = strRadioIntentosIngreso;
	}

	public List getListEmpresas() {
		return listEmpresas;
	}

	public void setListEmpresas(List listEmpresas) {
		this.listEmpresas = listEmpresas;
	}

	public List getListHora() {
		return listHora;
	}

	public void setListHora(List listHora) {
		this.listHora = listHora;
	}

	public List getListMinutos() {
		return listMinutos;
	}

	public void setListMinutos(List listMinutos) {
		this.listMinutos = listMinutos;
	}

	public List getListSegundos() {
		return listSegundos;
	}

	public void setListSegundos(List listSegundos) {
		this.listSegundos = listSegundos;
	}

	public SelectItem getItemHora() {
		return itemHora;
	}

	public void setItemHora(SelectItem itemHora) {
		this.itemHora = itemHora;
	}

	public String getStrHoraSesion() {
		return strHoraSesion;
	}

	public void setStrHoraSesion(String strHoraSesion) {
		this.strHoraSesion = strHoraSesion;
	}

	public String getMsgSinEmpresas() {
		return msgSinEmpresas;
	}

	public void setMsgSinEmpresas(String msgSinEmpresas) {
		this.msgSinEmpresas = msgSinEmpresas;
	}

	public String getMsgTxtEmpresaArea() {
		return msgTxtEmpresaArea;
	}

	public void setMsgTxtEmpresaArea(String msgTxtEmpresaArea) {
		this.msgTxtEmpresaArea = msgTxtEmpresaArea;
	}

	public String getMsgTxtSucursalArea() {
		return msgTxtSucursalArea;
	}

	public void setMsgTxtSucursalArea(String msgTxtSucursalArea) {
		this.msgTxtSucursalArea = msgTxtSucursalArea;
	}

	public String getMsgTxtArea() {
		return msgTxtArea;
	}

	public void setMsgTxtArea(String msgTxtArea) {
		this.msgTxtArea = msgTxtArea;
	}

	public String getMsgTxtAbrevArea() {
		return msgTxtAbrevArea;
	}

	public void setMsgTxtAbrevArea(String msgTxtAbrevArea) {
		this.msgTxtAbrevArea = msgTxtAbrevArea;
	}

	public String getStrMinutosSesion() {
		return strMinutosSesion;
	}

	public List getBeanListZonal() {
		return beanListZonal;
	}

	public void setBeanListZonal(List beanListZonal) {
		this.beanListZonal = beanListZonal;
	}

	public List getBeanListSucursalCodigo() {
		return beanListSucursalCodigo;
	}

	public void setBeanListSucursalCodigo(List beanListSucursalCodigo) {
		this.beanListSucursalCodigo = beanListSucursalCodigo;
	}

	public Integer getIntCboEmpresa() {
		return intCboEmpresa;
	}

	public void setIntCboEmpresa(Integer intCboEmpresa) {
		this.intCboEmpresa = intCboEmpresa;
	}

	public Integer getIntCboEmpresaSucursal() {
		return intCboEmpresaSucursal;
	}

	public void setIntCboEmpresaSucursal(Integer intCboEmpresaSucursal) {
		this.intCboEmpresaSucursal = intCboEmpresaSucursal;
	}
    /*
	public Integer getIntCboEmpresaZonal() {
		return intCboEmpresaZonal;
	}

	public void setIntCboEmpresaZonal(Integer intCboEmpresaZonal) {
		this.intCboEmpresaZonal = intCboEmpresaZonal;
	}

	public String getStrCboSucursalZonal() {
		return strCboSucursalZonal;
	}

	public void setStrCboSucursalZonal(String strCboSucursalZonal) {
		this.strCboSucursalZonal = strCboSucursalZonal;
	}*/

	public void setBeanListSucurZonal(List beanListSucurZonal) {
		this.beanListSucurZonal = beanListSucurZonal;
	}
	
	public Integer getIntCboRespZonal() {
		return intCboRespZonal;
	}

	public void setIntCboRespZonal(Integer intCboRespZonal) {
		this.intCboRespZonal = intCboRespZonal;
	}
	/*
	public Integer getIntCboTipoZonal() {
		return intCboTipoZonal;
	}

	public void setIntCboTipoZonal(Integer intCboTipoZonal) {
		this.intCboTipoZonal = intCboTipoZonal;
	}

	public Integer getIntCboEstadoZonal() {
		return intCboEstadoZonal;
	}

	public void setIntCboEstadoZonal(Integer intCboEstadoZonal) {
		this.intCboEstadoZonal = intCboEstadoZonal;
	}*/

	public String getStrNombreTercero() {
		return strNombreTercero;
	}

	public void setStrNombreTercero(String strNombreTercero) {
		this.strNombreTercero = strNombreTercero;
	}

	public Integer getIntCboActivoTercero() {
		return intCboActivoTercero;
	}

	public void setIntCboActivoTercero(Integer intCboActivoTercero) {
		this.intCboActivoTercero = intCboActivoTercero;
	}

	public String getStrCodigoTercero() {
		return strCodigoTercero;
	}

	public void setStrCodigoTercero(String strCodigoTercero) {
		this.strCodigoTercero = strCodigoTercero;
	}

	public String getStrNombreSubSuc() {
		return strNombreSubSuc;
	}

	public void setStrNombreSubSuc(String strNombreSubSuc) {
		this.strNombreSubSuc = strNombreSubSuc;
	}

	public int getIntCboActivoSubsuc() {
		return intCboActivoSubsuc;
	}

	public void setIntCboActivoSubsuc(int intCboActivoSubsuc) {
		this.intCboActivoSubsuc = intCboActivoSubsuc;
	}

	public String getStrSubSucAbrev() {
		return strSubSucAbrev;
	}

	public void setStrSubSucAbrev(String strSubSucAbrev) {
		this.strSubSucAbrev = strSubSucAbrev;
	}
	
	public Integer getIntCboSucursal() {
		return intCboSucursal;
	}

	public void setIntCboSucursal(Integer intCboSucursal) {
		this.intCboSucursal = intCboSucursal;
	}

	public Boolean getFormZonalDisabled() {
		return formZonalDisabled;
	}

	public void setFormZonalDisabled(Boolean formZonalDisabled) {
		this.formZonalDisabled = formZonalDisabled;
	}

	public Boolean getZonalRendered() {
		return zonalRendered;
	}

	public void setZonalRendered(Boolean zonalRendered) {
		this.zonalRendered = zonalRendered;
	}

	public String getTxtNombreZonal() {
		return txtNombreZonal;
	}

	public void setTxtNombreZonal(String txtNombreZonal) {
		this.txtNombreZonal = txtNombreZonal;
	}

	public String getTxtAbrevZonal() {
		return txtAbrevZonal;
	}

	public void setTxtAbrevZonal(String txtAbrevZonal) {
		this.txtAbrevZonal = txtAbrevZonal;
	}

	public Integer getCboTipoSucursal() {
		return cboTipoSucursal;
	}

	public void setCboTipoSucursal(Integer cboTipoSucursal) {
		this.cboTipoSucursal = cboTipoSucursal;
	}

	public Boolean getShowModalPanelZonal() {
		return showModalPanelZonal;
	}

	public void setShowModalPanelZonal(Boolean showModalPanelZonal) {
		this.showModalPanelZonal = showModalPanelZonal;
	}

	public String getMsgEmpresaZonalError() {
		return msgEmpresaZonalError;
	}

	public void setMsgEmpresaZonalError(String msgEmpresaZonalError) {
		this.msgEmpresaZonalError = msgEmpresaZonalError;
	}

	public String getMsgTipoZonalError() {
		return msgTipoZonalError;
	}

	public void setMsgTipoZonalError(String msgTipoZonalError) {
		this.msgTipoZonalError = msgTipoZonalError;
	}

	public String getMsgEstadoZonalError() {
		return msgEstadoZonalError;
	}

	public void setMsgEstadoZonalError(String msgEstadoZonalError) {
		this.msgEstadoZonalError = msgEstadoZonalError;
	}

	public String getMsgNombreZonalError() {
		return msgNombreZonalError;
	}

	public void setMsgNombreZonalError(String msgNombreZonalError) {
		this.msgNombreZonalError = msgNombreZonalError;
	}

	public String getMsgAbrevZonalError() {
		return msgAbrevZonalError;
	}

	public void setMsgAbrevZonalError(String msgAbrevZonalError) {
		this.msgAbrevZonalError = msgAbrevZonalError;
	}

	public String getMsgRespZonalError() {
		return msgRespZonalError;
	}

	public void setMsgRespZonalError(String msgRespZonalError) {
		this.msgRespZonalError = msgRespZonalError;
	}

	public String getMsgSucurSondeoError() {
		return msgSucurSondeoError;
	}

	public void setMsgSucurSondeoError(String msgSucurSondeoError) {
		this.msgSucurSondeoError = msgSucurSondeoError;
	}

	public String getMsgAreaCodigoError() {
		return msgAreaCodigoError;
	}

	public void setMsgAreaCodigoError(String msgAreaCodigoError) {
		this.msgAreaCodigoError = msgAreaCodigoError;
	}

	public ArrayList getArrayTercero() {
		return ArrayTercero;
	}

	public void setArrayTercero(ArrayList arrayTercero) {
		ArrayTercero = arrayTercero;
	}

	public ArrayList getArraySubsucursal() {
		return ArraySubsucursal;
	}

	public void setArraySubsucursal(ArrayList arraySubsucursal) {
		ArraySubsucursal = arraySubsucursal;
	}

	public ArrayList getArrayAreaCodigo() {
		return ArrayAreaCodigo;
	}

	public void setArrayAreaCodigo(ArrayList arrayAreaCodigo) {
		ArrayAreaCodigo = arrayAreaCodigo;
	}

	public int getIntContSucurAnexas() {
		return intContSucurAnexas;
	}

	public void setIntContSucurAnexas(int intContSucurAnexas) {
		this.intContSucurAnexas = intContSucurAnexas;
	}

	public int getIntContSucurLibres() {
		return intContSucurLibres;
	}

	public void setIntContSucurLibres(int intContSucurLibres) {
		this.intContSucurLibres = intContSucurLibres;
	}
	
	public String getMsgTipoAreaError() {
		return msgTipoAreaError;
	}

	public void setMsgTipoAreaError(String msgTipoAreaError) {
		this.msgTipoAreaError = msgTipoAreaError;
	}
	
	public String getMsgEstadoAreaError() {
		return msgEstadoAreaError;
	}

	public void setMsgEstadoAreaError(String msgEstadoAreaError) {
		this.msgEstadoAreaError = msgEstadoAreaError;
	}
	
	public ArrayList<SelectItem> getCboSucursales() {
		return cboSucursales;
	}

	public void setCboSucursales(ArrayList<SelectItem> cboSucursales) {
		this.cboSucursales = cboSucursales;
	}

	public List<SucursalComp> getListaSucursalComp() {
		return listaSucursalComp;
	}

	public void setListaSucursalComp(List<SucursalComp> listaSucursalComp) {
		this.listaSucursalComp = listaSucursalComp;
	}

	public List<ZonalComp> getListaZonalComp() {
		return listaZonalComp;
	}

	public void setListaZonalComp(List<ZonalComp> listaZonalComp) {
		this.listaZonalComp = listaZonalComp;
	}

	public Integer getIntTipoSucursal() {
		return intTipoSucursal;
	}

	public void setIntTipoSucursal(Integer intTipoSucursal) {
		this.intTipoSucursal = intTipoSucursal;
	}

	public Integer getIntSucursalAnexo() {
		return intSucursalAnexo;
	}

	public void setIntSucursalAnexo(Integer intSucursalAnexo) {
		this.intSucursalAnexo = intSucursalAnexo;
	}

	public List<Natural> getListaNaturalUsuario() {
		return listaNaturalUsuario;
	}

	public void setListaNaturalUsuario(List<Natural> listaNaturalUsuario) {
		this.listaNaturalUsuario = listaNaturalUsuario;
	}

	public List<Sucursal> getListaSucursalDeZonal() {
		return listaSucursalDeZonal;
	}

	public void setListaSucursalDeZonal(List<Sucursal> listaSucursalDeZonal) {
		this.listaSucursalDeZonal = listaSucursalDeZonal;
	}

	public String getStrTipoMantSucursal() {
		return strTipoMantSucursal;
	}

	public void setStrTipoMantSucursal(String strTipoMantSucursal) {
		this.strTipoMantSucursal = strTipoMantSucursal;
	}

	public String getStrTipoMantZonal() {
		return strTipoMantZonal;
	}

	public void setStrTipoMantZonal(String strTipoMantZonal) {
		this.strTipoMantZonal = strTipoMantZonal;
	}

	public String getStrTipoMantArea() {
		return strTipoMantArea;
	}

	public void setStrTipoMantArea(String strTipoMantArea) {
		this.strTipoMantArea = strTipoMantArea;
	}

	public List<Empresa> getBeanListEmpresas() {
		return beanListEmpresas;
	}

	public void setBeanListEmpresas(List<Empresa> beanListEmpresas) {
		this.beanListEmpresas = beanListEmpresas;
	}

	public List<SucursalCodigo> getListSucursalCodigo() {
		return listSucursalCodigo;
	}

	public void setListSucursalCodigo(List<SucursalCodigo> listSucursalCodigo) {
		this.listSucursalCodigo = listSucursalCodigo;
	}

	public List<Subsucursal> getListSubsucursal() {
		return listSubsucursal;
	}

	public void setListSubsucursal(List<Subsucursal> listSubsucursal) {
		this.listSubsucursal = listSubsucursal;
	}

	public String getStrIdZonal() {
		return strIdZonal;
	}
	public void setStrIdZonal(String strIdZonal) {
		this.strIdZonal = strIdZonal;
	}

	public Boolean getBolEmpresa() {
		return bolEmpresa;
	}
	public void setBolEmpresa(Boolean bolEmpresa) {
		this.bolEmpresa = bolEmpresa;
	}

	public Boolean getBolSucursal() {
		return bolSucursal;
	}
	public void setBolSucursal(Boolean bolSucursal) {
		this.bolSucursal = bolSucursal;
	}

	public Boolean getBolArea() {
		return bolArea;
	}
	public void setBolArea(Boolean bolArea) {
		this.bolArea = bolArea;
	}

	public Boolean getBolZonal() {
		return bolZonal;
	}
	public void setBolZonal(Boolean bolZonal) {
		this.bolZonal = bolZonal;
	}
	
	
	
    public Boolean getFormAreaDisabled() {
		return formAreaDisabled;
	}

	public void setFormAreaDisabled(Boolean formAreaDisabled) {
		this.formAreaDisabled = formAreaDisabled;
	}

	//AGREGADO FRC
	public void seleccionarRegistro(ActionEvent event){
		log.info("-------------------------------------Debugging empresaController.seleccionarRegistro-------------------------------------");
		Integer itemAreaEstado = (Integer)event.getComponent().getAttributes().get("itemAreaEstado");
		 log.info("---itemAreaEstado:"+itemAreaEstado);
		 if (itemAreaEstado.equals(0)){
			 setFormAreaDisabled(false);
		 }
		 else{
			 setFormAreaDisabled(true);
		 }
			 
	}	

}