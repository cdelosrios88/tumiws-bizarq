package pe.com.tumi.seguridad.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.richfaces.component.html.HtmlPanelMenu;

import pe.com.tumi.common.util.CommonUtils;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.servicio.seguridad.exception.SeguridadException;
import pe.com.tumi.framework.servicio.seguridad.factory.SeguridadFactory;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.facade.AuditoriaFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeLocal;
import pe.com.tumi.seguridad.login.bean.CambioMsg;
import pe.com.tumi.seguridad.login.bean.PortalMsg;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Session;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.facade.LoginFacadeLocal;
import pe.com.tumi.seguridad.login.validador.CambioValidador;
import pe.com.tumi.seguridad.login.validador.PortalValidador;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecial;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecialDetalle;
import pe.com.tumi.seguridad.permiso.domain.Computadora;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesos;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosId;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeLocal;

public class LoginController{
	
	/**
	 * @uml.property  name="usuario"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="pe.com.tumi.seguridad.login.domain.EmpresaUsuario"
	 */
	private Usuario usuario;
	/**
	 * @uml.property  name="listaTransaccion"
	 */
	private List<Transaccion> listaTransaccion;
	/**
	 * @uml.property  name="intIdEmpresa"
	 */
	private Integer intIdEmpresa;
	/**
	 * @uml.property  name="listaJuridicaEmpresa"
	 */
	private List<Juridica> listaJuridicaEmpresa;
	/**
	 * @uml.property  name="intIdSucursalPersona"
	 */
	private Integer intIdSucursalPersona;
	/**
	 * @uml.property  name="listaJuridicaSucursal"
	 */
	private List<Juridica> listaJuridicaSucursal;
	/**
	 * @uml.property  name="intIdSubSucursal"
	 */
	private Integer intIdSubSucursal;
	/**
	 * @uml.property  name="listaSubSucursal"
	 */
	private List<Subsucursal> listaSubSucursal;
	/**
	 * @uml.property  name="intIdPerfil"
	 */
	private Integer intIdPerfil;
	/**
	 * @uml.property  name="listaPerfil"
	 */
	private List<Perfil> listaPerfil;
	/**
	 * @uml.property  name="msgPortal"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private PortalMsg msgPortal;
	//private ArrayList<SelectItem> cboOpciones = new ArrayList<SelectItem>();
	//private List beanListOpciones = new ArrayList<OpcionMenu>();
	//protected static Logger log = Logger.getLogger(LoginController.class);
	/**
	 * @uml.property  name="newPassword"
	 */
	private String newPassword;
	/**
	 * @uml.property  name="verifyPassword"
	 */
	private String verifyPassword;
	/**
	/**
	 * @uml.property  name="fullName"
	 */
	private String fullName;
	/**
	 * @uml.property  name="lastLogin"
	 */
	private Date lastLogin;
	/**
	 * @uml.property  name="failAttempts"
	 */
	private int failAttempts;	
	/**
	 * @uml.property  name="intento"
	 */
	private int intento;
	//private LoginService loginService;		
	//private RolService rolService;
	//private UsuarioServiceImpl usuarioPerfilService;
	//private List<Empresa> listEmpresa = new ArrayList<Empresa>(); 
	//private List<Juridica> listaJuridicaEmpresa;
	//private List<Perfil> listPerfil= new ArrayList<Perfil>();
	
	/**
	 * @uml.property  name="codigo"
	 */
	private String codigo;	
	/**
	 * @uml.property  name="contrasena"
	 */
	private String contrasena;	
	/**
	 * @uml.property  name="empresa"
	 */
	private String empresa;	
	/**
	 * @uml.property  name="perfil"
	 */
	private String perfil;
	/**
	 * @uml.property  name="idUsuario"
	 */
	private String idUsuario;
	/**
	 * @uml.property  name="bolInicio"
	 */
	private Boolean bolInicio = false;
	/**
	 * @uml.property  name="bolCambioClave"
	 */
	private Boolean bolCambioClave = false;
	//private BeanSesion beanSesion = new BeanSesion();
	/**
	 * @uml.property  name="strUsuario"
	 */
	private String strUsuario; 
	/**
	 * @uml.property  name="strContrasena"
	 */
	private String strContrasena;
	/**
	 * @uml.property  name="strContrasenaNueva"
	 */
	private String strContrasenaNueva;
	/**
	 * @uml.property  name="strContrasenaValidacion"
	 */
	private String strContrasenaValidacion;
	/**
	 * @uml.property  name="msgCambio"
	 * @uml.associationEnd  
	 */
	private CambioMsg msgCambio;
	/**
	 * @uml.property  name="intNumeroIntentos"
	 */
	private int intNumeroIntentos = 0;
	
	/**
	 * @uml.property  name="panelMenu"
	 * @uml.associationEnd  
	 */
	private HtmlPanelMenu panelMenu;
	
	private PermisoFacadeLocal permisoFacade;
	private boolean limpiarMensaje;
	protected static Logger log = Logger.getLogger(LoginController.class);
	private int activaPopup;
	
	//Inicio: REQ14-001 - bizarq - 15/07/2014
	private final String STR_URL_PROPERTIES = "../../resource/MessageValidate_es.properties";
	private static final String STR_LBL_LOGIN = "login";
	private String strMessageValidMAC;
	private String strMacAddress;
	//Inicio: REQ14-002 - bizarq - 28/07/2014
	private String strMessageValidUserSession;
	//Fin: REQ14-002 - bizarq - 28/07/2014
	
	/**
	 * @return the strMessageValidMAC
	 */
	public String getStrMessageValidMAC() {
		return strMessageValidMAC;
	}

	/**
	 * @param strMessageValidMAC the strMessageValidMAC to set
	 */
	public void setStrMessageValidMAC(String strMessageValidMAC) {
		this.strMessageValidMAC = strMessageValidMAC;
	}

	/**
	 * @return the strMacAddress
	 */
	public String getStrMacAddress() {
		return strMacAddress;
	}

	/**
	 * @param strMacAddress the strMacAddress to set
	 */
	public void setStrMacAddress(String strMacAddress) {
		this.strMacAddress = strMacAddress;
	}
	//Fin: REQ14-001 - bizarq - 15/07/2014
	
	//Inicio: REQ14-002 - bizarq - 20/07/2014
	private LoginFacadeLocal loginFacade;
	//Fin: REQ14-002 - bizarq - 20/07/2014

	public LoginController(){
		usuario = new Usuario();
		msgPortal = new PortalMsg();
		try {
			permisoFacade = (PermisoFacadeLocal) EJBFactory.getLocal(PermisoFacadeLocal.class);
			//Inicio: REQ14-002 - bizarq - 20/07/2014
			loginFacade = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
			//Fin: REQ14-002 - bizarq - 20/07/2014
		} catch (EJBFactoryException e) {
			
			e.printStackTrace();
		}
		//iniciarEstCuenta();
		limpiarMensaje = true;
		
	}
	
	public void iniciarEstCuenta() {
		EstadoCuentaController estCtaController = null;
		try {
			estCtaController = (EstadoCuentaController)getSessionBean("estadoCuentaController");
			if(estCtaController  != null){

			}else{
				estCtaController = new EstadoCuentaController();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void init(){
		setNewPassword("");
		setVerifyPassword("");
//		if (((Usuario)getBean()).getFechaCambioPass()==null){
//			((Usuario)getBean()).setFechaCambioPass(Constante.FECHA_INICIO);
//		}
	}

	public void changePassword(ActionEvent event){
		/*HashMap map = new HashMap();
		try {
			map = getLoginService().validateContrasenia(((Usuario)getSpringBean("usuarioEnSesion")), newPassword, verifyPassword, Constante.ID_SISTEMA);
		} catch (DaoException e) {
			setCatchError(e);
			log.debug(e.getMessage());
		}
		if (!map.get("flag").equals("false")){
			setMessageSuccess((String)map.get("message"));
		}else{
			setMessageError((String)map.get("message"));
			setNewPassword("");
			setVerifyPassword("");
		}*/
	}
	
	/*public String closeSession(){
//		getSession(false).setAttribute(Constante.SESSION_USER, null);
//		getSession(false).invalidate();
//		log.info("Fin de Sesion al " + DateHelper.getFechaActual());
		return "login";
	}*/
	public void inicio(ActionEvent event){
		if(!bolInicio){
			cerrarSession(event);
			bolInicio = true;
			intNumeroIntentos = 0;
		}
	}
	
	public String getInicializarPagina(){
		//Inicio: REQ14-002 - bizarq - 30/07/2014
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = null;
		session = ((HttpServletRequest) request).getSession();
		updateUserSession(session);
		//Fin: REQ14-002 - bizarq - 30/07/2014
		if(!bolInicio){
			cerrarSession();
			limpiar();
			bolInicio = true;
			intNumeroIntentos = 0;
		}
		
		return "";
	}
	
	public void cerrarSession(){
		log.info("ingreso a cerrar session");
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			
		try{
			log.info("pregunta si hay session");
			if(request.getSession() != null){
				//AGREGADO!!!!!
				log.info("si hay session");
				HttpSession session = ((HttpServletRequest) request).getSession();
				log.info("llamo a la session");
				session.invalidate();
				log.info("invalido la session");
			
				usuario = new Usuario();
				SeguridadFactory.cancelarTicket(request);
				log.info("cancelo el ticket");	
			}
			
		}catch (SeguridadException e) {
			e.printStackTrace();
		}	
		
	}
	
	public void limpiar(){
		intIdEmpresa = 0;
		intIdSucursalPersona = 0;
		intIdSubSucursal = 0;
		intIdPerfil = 0;
		listaJuridicaSucursal = null;
		listaSubSucursal = null;
		listaPerfil = null;
		msgPortal.setIdEmpresa(null);
		msgPortal.setIdSucursal(null);
		msgPortal.setIdSubSucursal(null);
		msgPortal.setIdPerfil(null);
		msgPortal.setUsuario(null);
		msgPortal.setContrasena(null);
		usuario.setStrUsuario("");
		activaPopup = 0;
		if(limpiarMensaje)
			msgPortal.setContrasena("");
	}
	
	public String autenticar(){
		limpiarMensaje = true;
		Usuario lUsuario = null;
		List<EmpresaUsuario> listaEmpresaUsuario = null;
		EmpresaUsuario empresaUsuario = null;
		String csvPkEmpresa = null;
		String outcome = null;
		LoginFacadeLocal localLogin = null;
		EmpresaFacadeLocal localEmpresa = null;
		PersonaFacadeRemote remotePersona = null;
		AuditoriaFacadeRemote remoteAuditoria = null;
		Auditoria auditoria = null;
		Timestamp tsFechaActual = null;
		Timestamp tsFechaRegistro = null;
		Timestamp tsFechaFinal = null;
		Timestamp tsFechaAlerta = null;
		Empresa empresa = null;
		boolean esClaveVigente = false;
		
		//MAC
		//conseguirMAC();
		
		//AGREGADO!!!!!
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		try {
			outcome = "portal.login";
			if(PortalValidador.validarUsuario(msgPortal, usuario)){
				localLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
				lUsuario = localLogin.getUsuarioPorCodigoYClave(usuario.getStrUsuario(), usuario.getStrContrasena());
				if(lUsuario != null){
					localEmpresa = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
					listaEmpresaUsuario = localEmpresa.getListaEmpresaUsuarioPorIdPersona(lUsuario.getIntPersPersonaPk());
					if(listaEmpresaUsuario != null && listaEmpresaUsuario.size()>0){
						for(int i=0;i<listaEmpresaUsuario.size();i++){
							if(csvPkEmpresa==null)
								csvPkEmpresa = String.valueOf(listaEmpresaUsuario.get(i).getId().getIntPersEmpresaPk());
							else
								csvPkEmpresa = csvPkEmpresa + "," + listaEmpresaUsuario.get(i).getId().getIntPersEmpresaPk();
						}
						empresaUsuario = listaEmpresaUsuario.get(0);
						if(empresaUsuario.getIntControlVigenciaClave()== null || empresaUsuario.getIntControlVigenciaClave().compareTo(0)==0){
							remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
							empresa = remotePersona.getEmpresaPorPk(empresaUsuario.getId().getIntPersEmpresaPk());
														
							if(empresa.getIntVigenciaClaves()!= null){
								remoteAuditoria = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
								auditoria = remoteAuditoria.getAuditoriaDeMaximoPkPorTablaYColumnaYLlave1("SEG_M_USUARIO", 
																										  "USUA_CONTRASEÑA_V", 
																										  String.valueOf(lUsuario.getIntPersPersonaPk()));
								if(auditoria!=null){
									tsFechaActual = new Timestamp(System.currentTimeMillis());
									tsFechaRegistro = auditoria.getTsFecharegistro();
									Calendar calendario = Calendar.getInstance();
							        calendario.setTimeInMillis(tsFechaRegistro.getTime());
							        calendario.add(Calendar.DATE, empresa.getIntVigenciaClaves());
							        tsFechaFinal = new Timestamp(calendario.getTimeInMillis());
							        if(tsFechaActual.before(tsFechaFinal)){
							        	esClaveVigente = true;
							        	if(empresa.getIntAlertaCaducidad()!=null){
								        	calendario.setTimeInMillis(tsFechaFinal.getTime());
								        	calendario.add(Calendar.DATE, -1*empresa.getIntAlertaCaducidad());
								        	tsFechaAlerta = new Timestamp(calendario.getTimeInMillis());
								        	if(tsFechaActual.after(tsFechaAlerta)){
								        		msgPortal.setContrasena("* Su clave está por vencer");
								        	}else{
								        		msgPortal.setContrasena(null);
								        	}
							        	}else{
							        		msgPortal.setContrasena(null);
							        	}
							        }else{
							        	esClaveVigente = false;
							        }
								}else{
									esClaveVigente = true;
								}
							}else{
								esClaveVigente = true;
							}
						}else if(empresaUsuario.getIntControlVigenciaClave()!= null && empresaUsuario.getIntControlVigenciaClave().compareTo(1)==0){
							if(empresaUsuario.getIntDiasVigencia()!=null){
								remoteAuditoria = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
								auditoria = remoteAuditoria.getAuditoriaDeMaximoPkPorTablaYColumnaYLlave1("SEG_M_USUARIO", 
																										  "USUA_CONTRASEÑA_V", 
																										  String.valueOf(lUsuario.getIntPersPersonaPk()));
								if(auditoria!=null){
									tsFechaActual = new Timestamp(System.currentTimeMillis());
									tsFechaRegistro = auditoria.getTsFecharegistro();
									Calendar calendario = Calendar.getInstance();
							        calendario.setTimeInMillis(tsFechaRegistro.getTime());
							        calendario.add(Calendar.DATE, empresaUsuario.getIntDiasVigencia());
							        tsFechaFinal = new Timestamp(calendario.getTimeInMillis());
							        if(tsFechaActual.before(tsFechaFinal)){
							        	esClaveVigente = true;
							        }else{
							        	esClaveVigente = false;
							        }
								}else{
									esClaveVigente = true;
								}
							}else{
								esClaveVigente = true;
							}
						}
						/**/
						if(esClaveVigente){
							remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
							listaJuridicaEmpresa = remotePersona.getListaJuridicaPorInPk(csvPkEmpresa);
							usuario = lUsuario;
							outcome = "portal.empresa";
							bolInicio = false;
							msgPortal.setUsuario(null);
							//msgPortal.setContrasena(null);
							esClaveVigente = false;
							
							session.setAttribute(Constante.USUARIO_LOGIN, lUsuario);
							
						}else{
							msgPortal.setContrasena("* Su clave ya caducó");
						}
					}else{
						msgPortal.setUsuario("* El Usuario no cuenta con empresas asociadas");
					}
				}else{
					if(usuario.getStrUsuario()!=null && !usuario.getStrUsuario().trim().equals("")){
						intNumeroIntentos++;
						lUsuario = localLogin.getUsuarioPorCodigo(usuario.getStrUsuario());
						if(lUsuario!= null){
							if(lUsuario.getIntIdEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO)){
								msgPortal.setContrasena("* Usuario inactivo por sobrepasar el N° de Intentos");
								intNumeroIntentos=0;
								return null;
							}else{
								localEmpresa = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
								listaEmpresaUsuario = localEmpresa.getListaEmpresaUsuarioPorIdPersona(lUsuario.getIntPersPersonaPk());
								empresaUsuario = listaEmpresaUsuario.get(0);
								remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
								empresa = remotePersona.getEmpresaPorPk(empresaUsuario.getId().getIntPersEmpresaPk());
								if(empresa.getIntIntentosIngreso()!= null && intNumeroIntentos > empresa.getIntIntentosIngreso()){
									lUsuario.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
									localLogin.modificarUsuario(lUsuario);
									msgPortal.setContrasena("* Usuario inactivo por sobrepasar el N° de Intentos");
									intNumeroIntentos = 0;
									return null;
								}else{
									msgPortal.setUsuario("* El Usuario y/o clave no son validas");
									msgPortal.setContrasena(null);
									return null;
								}
							}
						}else{
							msgPortal.setUsuario("* El Usuario y/o clave no son validas");
							msgPortal.setContrasena(null);
							return null;
						}
					}else{
						msgPortal.setUsuario("* El Usuario y/o clave no son validas");
						msgPortal.setContrasena(null);
						return null;
					}
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
		return outcome;
	}
	
	public void onChangeEmpresa(ActionEvent event){
		EmpresaFacadeLocal localEmpresa = null;
		LoginFacadeLocal localLogin = null;
		PersonaFacadeRemote remotePersona = null;
		List<Sucursal> lListaSucursal = null;
		List<Perfil> lListaPerfil = null;
		EmpresaUsuarioId id = null;
		String csvPkEmpresa = null;
		try{
			if(intIdEmpresa.compareTo(0)!=0){
				localEmpresa = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
				id = new EmpresaUsuarioId();
				id.setIntPersPersonaPk(usuario.getIntPersPersonaPk());
				id.setIntPersEmpresaPk(intIdEmpresa);
				//lListaSucursal = localEmpresa.getListaSucursalPorPkEmpresaUsuario(id);
				lListaSucursal = localEmpresa.getListaSucursalPorPkEmpresaUsuarioYEstado(id,Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				if(lListaSucursal != null && lListaSucursal.size()>0){
					for(int i=0;i<lListaSucursal.size();i++){
						if(csvPkEmpresa==null)
							csvPkEmpresa = String.valueOf(lListaSucursal.get(i).getIntPersPersonaPk());
						else
							csvPkEmpresa = csvPkEmpresa + "," + lListaSucursal.get(i).getIntPersPersonaPk();
					}
					if(csvPkEmpresa != null){
						remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
						listaJuridicaSucursal = remotePersona.getListaJuridicaPorInPk(csvPkEmpresa);
						msgPortal.setIdEmpresa(null);
						msgPortal.setIdSucursal(null);
						msgPortal.setIdSubSucursal(null);
					}
				}else{
					listaJuridicaSucursal = null;
					msgPortal.setIdSucursal("* La Empresa no cuenta con sucursales asociadas");
				}
				localLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
				//lListaPerfil = localLogin.getListaPerfilPorPkEmpresaUsuario(id);
				lListaPerfil = localLogin.getListaPerfilPorPkEmpresaUsuarioYEstado(id,Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				if(lListaPerfil!=null && lListaPerfil.size()>0){
					listaPerfil = lListaPerfil; 
					msgPortal.setIdPerfil(null);
				}else{
					listaPerfil = null;
					msgPortal.setIdPerfil("* La Empresa no cuenta con perfiles asociados");
				}	
			}else{
				listaJuridicaSucursal = null;
				listaSubSucursal = null;
				listaPerfil = null;
				msgPortal.setIdEmpresa(null);
				msgPortal.setIdSucursal(null);
				msgPortal.setIdSubSucursal(null);
				msgPortal.setIdPerfil(null);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}

	public void onChangeSucursal(ActionEvent event){
		EmpresaFacadeLocal localEmpresa = null;
		EmpresaUsuarioId id = null;
		List<Subsucursal> lListaSubSucursal = null;
		Sucursal sucursal = null;
		try{
			if(intIdSucursalPersona.compareTo(0)!=0){
				localEmpresa = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
				sucursal = localEmpresa.getSucursalPorIdPersona(intIdSucursalPersona);
				//lListaSubSucursal = localEmpresa.getListaSubSucursalPorIdSucursal(sucursal.getId().getIntIdSucursal());
				//lListaSubSucursal = localEmpresa.getListaSubSucursalPorIdSucursalYestado(sucursal.getId().getIntIdSucursal(),
																						 //Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				id = new EmpresaUsuarioId();
				id.setIntPersEmpresaPk(intIdEmpresa);
				id.setIntPersPersonaPk(usuario.getIntPersPersonaPk());
				lListaSubSucursal = localEmpresa.getListaSubSucursalPorPkEmpresaUsuarioYIdSucursalYEstado(id,
															sucursal.getId().getIntIdSucursal(),
															Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				if(lListaSubSucursal != null && lListaSubSucursal.size()>0){
					listaSubSucursal = lListaSubSucursal;
					msgPortal.setIdSucursal(null);
					msgPortal.setIdSubSucursal(null);
				}else{
					listaSubSucursal = null;
					msgPortal.setIdSucursal(null);
					msgPortal.setIdSubSucursal("* La Sucursal no cuenta con SubSucursales asociados");
				}
			}else{
				listaSubSucursal = null;
				msgPortal.setIdSucursal(null);
				msgPortal.setIdSubSucursal(null);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	//Inicio: REQ14-001 - bizarq - 15/07/2014
	/**
	 * @author Luis Polanco
	 * Descripción:
	 * Metodo que verifica si el usuario autenticado tiene permisos de cabina en el dia y hora actual (se verifican los
	 * dias de semana en caso se seleccione en el registro de cabina).
	 * 
	 * @return Indicador de pertenencia del usuario a cabina <code>bolUsuarioCabina</code>
	 */
	public boolean verificarUsuarioCabina() {
		AccesoEspecial accesoEspecialFiltro = null;
		List<AccesoEspecial> listaAccesosEspeciales = null;
		boolean bolUsuarioCabina = false;
		try {
			// Actualizar filtros de busqueda
			accesoEspecialFiltro = new AccesoEspecial();
			accesoEspecialFiltro.setIntPersEmpresa(intIdEmpresa);
			accesoEspecialFiltro.setIntParaTipoMotivo(null);
			accesoEspecialFiltro.setIntPersPersonaOpera(usuario.getIntPersPersonaPk());
			accesoEspecialFiltro.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

			// Obtener lista de MAC Address y permisos de cabina
			/*
			 * 1.- Primero se va a verificar si el usuario tiene permiso para acceder desde cabina. 
			 * 		1.1- Si tiene permiso, no se valida MAC y sigue el proceso de autenticacion 
			 * 		1.2- Si no tiene permiso, se valida MAC 
			 * 2.- En caso se cumpla la condicion 
			 * 		2.1 se procede a validar la MAC en la lista de MAC Address permitidas.
			 */
			listaAccesosEspeciales = this.permisoFacade.buscarAccesosEspeciales(accesoEspecialFiltro);

			// 1.- Buscar permisos para el usuario
			if (listaAccesosEspeciales != null && !listaAccesosEspeciales.isEmpty()) {
				for (AccesoEspecial objAccesoEspecialTmp : listaAccesosEspeciales) {
					// 1.1.- Buscar si la persona autenticada existe
					if (objAccesoEspecialTmp.getIntPersPersonaOpera().equals(usuario.getIntPersPersonaPk())) {
						// 1.1.1 Buscar que el dia de hoy (incluida hora) este en el rango de fecha inicio y fin
						Timestamp tsFechaHoy = new Timestamp(new Date().getTime());
						if (((objAccesoEspecialTmp.getTsFechaInicio().before(tsFechaHoy)) || (objAccesoEspecialTmp
								.getTsFechaInicio().equals(tsFechaHoy)))
								&& ((objAccesoEspecialTmp.getTsFechaFin().after(tsFechaHoy)))
								|| (objAccesoEspecialTmp.getTsFechaFin().equals(tsFechaHoy))) {
							// 1.1.1.1 Verificar existencia del detalle del acceso especial
							// (dia de semana)
							List<AccesoEspecialDetalle> listaAccesoEspecialDet = this.permisoFacade
									.getListaAccesoEspecialDetallePorCabecera(objAccesoEspecialTmp);
							if (listaAccesoEspecialDet != null && !listaAccesoEspecialDet.isEmpty()) {
								Calendar calFechaHoy = Calendar.getInstance();
								calFechaHoy.setTime(tsFechaHoy);
								int intDiaHoy = calFechaHoy.get(Calendar.DAY_OF_WEEK);

								// Convertir dia Java a dia ERP (Java - Domingo = 1
								if (intDiaHoy == Constante.INT_ONE) {
									intDiaHoy = Constante.INT_SEVEN;
								} else {
									intDiaHoy = intDiaHoy - Constante.INT_ONE;
								}
								for (AccesoEspecialDetalle objAccesoEspecialDetTmp : listaAccesoEspecialDet) {
									// 1.1.1.1.1 Verificar el dia en el detalle
									if (objAccesoEspecialDetTmp.getIntIdEstado().equals(
											Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
											&& objAccesoEspecialDetTmp.getId().getIntIdDiaSemana() != null
											&& objAccesoEspecialDetTmp.getId().getIntIdDiaSemana() == (intDiaHoy)) {
										bolUsuarioCabina = true;
										break;
									} else {
										// 1.1.1.1.2 No existe en el detalle
										bolUsuarioCabina = false;
									}
								}
							} else {
								// 1.1.1.2 Si no hay detalle especial -> Esta en el rango -> True (isCabina)
								bolUsuarioCabina = true;
								break;
							}
						} else {
							// 1.1.2 No esta en el rango -> False (isCabina)
							bolUsuarioCabina = false;
						}
					} else {
						// 1.2 Persona no esta en la lista -> False (isCabina)
						bolUsuarioCabina = false;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bolUsuarioCabina;
	}

	/**
	 * @author Bizarq Technologies
	 * Descripción:
	 * Metodo que verifica si la MAC Address del usuario autenticado se encuentra registrada en la lista Computadoras
	 * que pertenecen a la misma Empresa y Sucursal del usuario.
	 * 
	 * @return Indicador de pertenencia de la MAC Address con la lista de Computadoras <code>bolRegistroMac</code>
	 */
	public boolean verificarMacAddress() {
		EmpresaFacadeLocal localEmpresa = null;
		EmpresaUsuarioId empresaUsuarioId = null;
		Computadora computadoraFiltro = null;
		List<Computadora> listaComputadoras = new ArrayList<Computadora>();
		List<Sucursal> listaSucursal = new ArrayList<Sucursal>();
		boolean bolRegistroMac = false;
		int intIdSucursal = 0;
		try {
			empresaUsuarioId = new EmpresaUsuarioId();
			empresaUsuarioId.setIntPersPersonaPk(usuario.getIntPersPersonaPk());
			empresaUsuarioId.setIntPersEmpresaPk(intIdEmpresa);
			localEmpresa = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			listaSucursal = localEmpresa.getListaSucursalPorPkEmpresaUsuarioYEstado(empresaUsuarioId,Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			if(listaSucursal != null && !listaSucursal.isEmpty()){
				
				// Obtener el Id de la Sucursal
				for(Sucursal objSucursalTmp : listaSucursal){
					if(objSucursalTmp.getIntPersPersonaPk() != null 
							&& objSucursalTmp.getIntPersPersonaPk().equals(intIdSucursalPersona)){
						intIdSucursal = objSucursalTmp.getId().getIntIdSucursal();
						break;
					}
				}

				// Armando los filtros de busqueda
				computadoraFiltro = new Computadora();
				computadoraFiltro.getId().setIntPersEmpresaPk(intIdEmpresa);
				computadoraFiltro.getId().setIntIdSucursal(intIdSucursal);
				computadoraFiltro.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

				// Obtener lista de Computadoras registradas
				listaComputadoras = this.permisoFacade.buscarComputadora(computadoraFiltro);
				if (listaComputadoras != null && !listaComputadoras.isEmpty()) {
					for (Computadora objComputadoraTmp : listaComputadoras) {
						// 1-Verificar que la MAC se encuentre en la lista de computadoras
						if (objComputadoraTmp.getStrIdentificador() != null
								&& objComputadoraTmp.getStrIdentificador().equals(this.strMacAddress)) {
							bolRegistroMac = true;
							break;
						} else {
							bolRegistroMac = false;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bolRegistroMac;
	}
	
	/**
	 * @author Bizarq Technologies
	 * Descripción:
	 * Metodo que redirecciona el formulario empresa.jsp a login.jsp
	 * 
	 * @return Ruta de pagina login (mapping faces-config) <code>STR_LBL_LOGIN</code>
	 */
	public String cancelarSesion (ActionEvent event) {
		limpiar();
		cerrarSession();
		return STR_LBL_LOGIN;
	}
	
	/**
	 * @author Bizarq Technologies
	 * Descripción:
	 * Metodo que retorna una cadena de texto de un archivo properties
	 * 
	 * @return Mensaje de texto registrado en un archivo properties <code>strMsg</code>
	 */
	public String getProperties (String strLabel){
		String strMsg = "";
		InputStream is = LoginController.class.getResourceAsStream(STR_URL_PROPERTIES);
        Properties props = new Properties();
        try {
	        props.load(is);
	        is.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        strMsg = props.getProperty(strLabel);
        return strMsg;
	}
	//Fin: REQ14-001 - bizarq - 15/07/2014
	
	//Inicio: REQ14-002 - bizarq - 20/07/2014
	/**
	 * @author Christian De los Ríos - Bizarq
	 * Descripción:
	 * Método que permite registrar la sesión de un usuario una vez que pase las validaciones
	 * de login. Permitirá registrar la actividad o inactividad del usuario.
	 * 
	 */
	public void saveUserSession(Usuario usuario, HttpSession httpSession, boolean isUsuarioCabina){
		Session session = null;
		try {
			session = new Session();
			session.getId().setIntPersEmpresaPk(usuario.getEmpresa().getIntIdEmpresa());
			session.getId().setIntPersPersonaPk(usuario.getIntPersPersonaPk());
			session.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			session.setIntIdSucursal(CommonUtils.getSucursalIdByPkPersona(intIdSucursalPersona));
			session.setIntInAccesoRemoto(Constante.INT_ZERO);
			session.setIntIdWebSession(httpSession.getId());
			session.setStrMacAddress(this.strMacAddress);
			session.setIntIndCabina(isUsuarioCabina?Constante.INT_ONE:Constante.INT_ZERO);
			
			httpSession.setAttribute("objSession", session);
			loginFacade.grabarSession(session);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @author Christian De los Ríos - Bizarq
	 * Descripción:
	 * Método que permite registrar la sesión de un usuario una vez que pase las validaciones
	 * de login. Permitirá registrar la actividad o inactividad del usuario.
	 * 
	 */
	public void updateUserSession(HttpSession session){
		Session objSession = null;
		try {
			objSession = (Session) session.getAttribute("objSession");
			if(objSession != null) {
				objSession.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
				objSession.setTsFechaTermino(new Timestamp(new Date().getTime()));
				session.removeAttribute("objSession");
				loginFacade.modificarSession(objSession);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @author Christian De los Ríos - Bizarq
	 * Descripción:
	 * Método que permite validar la sesión de un usuario y retorna si está activo o no.
	 * 
	 */
	private boolean validateSession(Usuario usuario){
		boolean isActiveSession = Boolean.FALSE;
		Integer intUserActiveSession = null;
		
		try {
			intUserActiveSession = loginFacade.getCntActiveSessionsByUser(usuario.getIntPersPersonaPk());
			if(intUserActiveSession!=null && 
					intUserActiveSession.intValue() > Constante.INT_ZERO){
				isActiveSession = Boolean.TRUE;
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return isActiveSession;
	}
	//Fin: REQ14-002 - bizarq - 20/07/2014
	
	public String autorizar(){
		boolean sigueValidando = false;
		PermisoFacadeLocal localPermiso = null;
		LoginFacadeLocal localLogin = null;
		EmpresaFacadeLocal localEmpresa = null;
		PersonaFacadeRemote remotePersona = null;
		PerfilId id = null;
		Sucursal sucursal = null;
		Persona persona = null;
		EmpresaUsuarioId idEmpresaUsuario= null;
		String outcome = "portal.empresa";
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try{
			//Inicio: REQ14-001 - bizarq - 15/07/2014
			// 1-Validar si el usuario tiene permisos de cabina
			boolean bolUsuarioCabina = verificarUsuarioCabina();
			if(bolUsuarioCabina == true){
				//1.1 Usuario tiene permisos de Cabina -> No verificar MAC
				//No hacer nada!
			}else{
				//1.2 Usuario NO tiene perimsos de Cabina -> Verificar MAC
				if(this.strMacAddress != null && this.strMacAddress.length() > Constante.INT_ZERO){
					//1.2.1 Verificar MAC Address en el registro de computadoras
					boolean bolRegistroMac = verificarMacAddress();
					if(bolRegistroMac == false){
						//1.2.1.1. Mostrar mensaje de que no esta en la lista de MAC (Computadora)
						// y cerrar sesion
						limpiarMensaje = false;
						setStrMessageValidMAC (getProperties("label.nofoundMAC.message"));
						activaPopup = 2;
						return outcome;
					}
				}else{
					//1.2.2 Mostrar mensaje de que no existe MAC y cerrar sesion
					setStrMessageValidMAC (getProperties("label.notgetMAC.message"));
					limpiarMensaje = false;
					activaPopup = 2;
					return outcome;
				}
			}
			//Fin: REQ14-001 - bizarq - 15/07/2014
			
			if(PortalValidador.validarEmpresa(msgPortal, intIdEmpresa, intIdSucursalPersona, intIdSubSucursal, intIdPerfil)){
				System.out.println("validarUsuario");
				log.info(intIdEmpresa);
				log.info(intIdSucursalPersona);
				log.info(intIdSubSucursal);
				
				//Validamos primero por cliente
				if(!validarEntradaPorUsuario(intIdEmpresa, usuario.getIntPersPersonaPk(), Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					log.info("no tiene acceso por usuario");
					sigueValidando = true;
				}
				
				//solo si la primera validacion no paso entonces se realizará la segunda validación
				if(sigueValidando){
					if(!validarEntradaPorEmpresaSucursalFecha(intIdEmpresa, intIdSucursalPersona, Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
						log.info("no tiene accceso por empresa y sucursal");					
						limpiarMensaje = false;
						outcome = "portal.login";
						//FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap().put("mostrarMensaje", new String[]{"1"});
						//request.getSession().setAttribute("mostrarMensaje", "1");
						activaPopup = 1;
						return outcome;
					}
				}
				//Inicio: REQ14-002 - bizarq - 30/07/2014
				HttpSession session = ((HttpServletRequest) request).getSession();
				
				boolean isActiveSession = validateSession(usuario);
				if(isActiveSession){
					//Se obtiene la ultima sesion activa del usuario logeado
					Session objDtoSession =loginFacade.getSesionByUser(usuario.getIntPersPersonaPk());
					//Se compara la mac que se logeo ultimo vs la maquina en el logeo actual
					if(objDtoSession!=null && session.getAttribute("objSession")==null){
						if(!objDtoSession.getStrMacAddress().equals(this.strMacAddress)){
							// se obtiene la fecha de registro de su ultima sesion activa
							Timestamp tsFechaInicio = objDtoSession.getTsFechaRegistro();
							// se obtiene la fecha del sistema
							Timestamp tsFechaActual = new Timestamp(new Date().getTime());
							//se realiza la diferencia entre las fechas del sistema y la ultima del logeo 
							long lngTimeMiliSegundo = tsFechaActual.getTime() -tsFechaInicio.getTime();
							//la diferencia en segundos
							long lngSegundos = lngTimeMiliSegundo / 1000;
							//obtenemos las horas de la diferencia
							long lngHoras = lngSegundos / 3600;
							//restamos las horas para continuar con minutos
							lngSegundos -= lngHoras*3600; 
							//igual que el paso anterior
							long lngMinutos = lngSegundos /60;
							boolean blActualizarSesion = false;
							remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
							Empresa objEmpresa = remotePersona.getEmpresaPorPk(objDtoSession.getId().getIntPersEmpresaPk());
							long lngHoraConf = objEmpresa.getDtTiempoSesion().getHours();
							long lngMinConf = objEmpresa.getDtTiempoSesion().getMinutes();
							// compara si hora de la diferencia es mayot a la configurada 
							// de serlo se activa un flag que permitira desactivar la ultima sesion activa
							if(lngHoras > lngHoraConf)
							{
								blActualizarSesion = true;
							}
							// compara si el minuto de la diferencia es mayor a la configurada 
							// de serlo se activa un flag que permitira desactivar la ultima sesion activa
							if(lngHoras <= lngHoraConf && lngMinutos > lngMinConf){
								blActualizarSesion = true;
							}
							//desactiva la ultima sesion activa
							if(blActualizarSesion){
								objDtoSession.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
								objDtoSession.setTsFechaTermino(new Timestamp(new Date().getTime()));
								loginFacade.modificarSession(objDtoSession);
							}else {
								//si no muestra la alerta indicando que mantiene una sesion activa en otra pc
								//msgPortal.setUsuario("Ud. ya mantiene una sesión activa en otra PC.");
								activaPopup = 3;
								setStrMessageValidUserSession("Ud. ya mantiene una sesión activa en otra PC.");
								return outcome;
							}
						}else {
							//msgPortal.setUsuario("Ud. ya mantiene una sesión activa en otra PC.");
//							activaPopup = 3;
//							setStrMessageValidUserSession("Ud. ya mantiene una sesión activa en otra PC.");
//							return outcome;
							objDtoSession.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO);
							objDtoSession.setTsFechaTermino(new Timestamp(new Date().getTime()));
							loginFacade.modificarSession(objDtoSession);
						}
					}
				}
				//Fin: REQ14-002 - bizarq - 30/07/2014
				
//				if(1==1){
//					outcome = "portal.login";
//					limpiarMensaje = false;
//					FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("mostrarMensaje", "1");
//					return outcome;
//				}
					
				id = new PerfilId();
				id.setIntPersEmpresaPk(intIdEmpresa);
				id.setIntIdPerfil(intIdPerfil);
				localPermiso = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
				listaTransaccion = localPermiso.getMenuPorIdPerfil(id);
				/**inicio testing**/
				//listaTransaccion.get(0).setStrModulo("/RiesgoWeb");
				//listaTransaccion.get(0).setStrPagina("/pages/archivo/configuracion.jsf");
				//listaTransaccion.get(0).setStrModulo("/SeguridadWeb");
				//listaTransaccion.get(0).setStrPagina("/pages/Monitor/empresa.jsf");
				/**fin testing**/
				localEmpresa = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
				sucursal = localEmpresa.getSucursalPorIdPersona(intIdSucursalPersona);
				remotePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				sucursal.setJuridica(remotePersona.getJuridicaPorPK(intIdSucursalPersona));
				usuario.setSucursal(sucursal);
				//usuario.setSucursal(localEmpresa.getSucursalPorIdSucursal(sucursal.getId().getIntIdSucursal()));
				usuario.setSubSucursal(localEmpresa.getSubSucursalPorIdSubSucursal(intIdSubSucursal));
				localLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
				usuario.setPerfil(localLogin.getPerfilPorPk(id));
				idEmpresaUsuario = new EmpresaUsuarioId();
				idEmpresaUsuario.setIntPersEmpresaPk(intIdEmpresa);
				idEmpresaUsuario.setIntPersPersonaPk(usuario.getIntPersPersonaPk());
				usuario.setEmpresaUsuario(localEmpresa.getEmpresaUsuarioPorPk(idEmpresaUsuario));
				
				persona = remotePersona.getPersonaPorPK(usuario.getIntPersPersonaPk());
				if(persona.getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
					persona.setNatural(remotePersona.getNaturalPorPK(usuario.getIntPersPersonaPk()));
				}else{
					persona.setJuridica(remotePersona.getJuridicaPorPK(usuario.getIntPersPersonaPk()));
				}
				usuario.setPersona(persona);
				usuario.setEmpresa(remotePersona.getEmpresaPorPk(intIdEmpresa));
				
				SeguridadFactory.setTicket(request, usuario);
				
				//Inicio: REQ14-002 - bizarq - 20/07/2014
				//HttpSession session = ((HttpServletRequest) request).getSession();
				if(session.getAttribute("objSession")==null)
					saveUserSession(usuario, session, bolUsuarioCabina);
				//Fin: REQ14-002 - bizarq - 20/07/2014
				outcome = "portal.principal";
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}catch (SeguridadException e) {
			e.printStackTrace();
		}catch (EJBFactoryException e) {
			e.printStackTrace();
		}
		return outcome;
	}
	
	public void limpiar(ActionEvent event){
		intIdEmpresa = 0;
		intIdSucursalPersona = 0;
		intIdSubSucursal = 0;
		intIdPerfil = 0;
		listaJuridicaSucursal = null;
		listaSubSucursal = null;
		listaPerfil = null;
		msgPortal.setIdEmpresa(null);
		msgPortal.setIdSucursal(null);
		msgPortal.setIdSubSucursal(null);
		msgPortal.setIdPerfil(null);
		msgPortal.setUsuario(null);
		msgPortal.setContrasena(null);
		activaPopup = 0;
	}
	
	public void irEmpresa(ActionEvent event){
	}
	
	public void cambiarClave(ActionEvent event){
		HttpServletRequest request = null;
		Usuario lUsuario = null;
			request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			lUsuario = (Usuario)request.getSession().getAttribute("usuario");
			strUsuario = lUsuario.getStrUsuario(); 
			strContrasena = null;
			strContrasenaNueva = null;
			strContrasenaValidacion = null;
			msgCambio = new CambioMsg();
			bolCambioClave = false;
	}
	
	public void modificarCambioClave(ActionEvent event){
		HttpServletRequest request = null;
		Usuario lUsuario = null;
		Usuario usuarioCambio = null;
		LoginFacadeLocal localLogin = null;
		Auditoria lAuditoria = null;
		AuditoriaFacadeRemote remoteAuditoria = null;
		//String outcome = "portal.cambioclave"; 
		try{
			request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			lUsuario = (Usuario)request.getSession().getAttribute("usuario");
			if(CambioValidador.esValidoCambio(msgCambio, strContrasena, strContrasenaNueva, strContrasenaValidacion,lUsuario)){
				localLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
				usuarioCambio = localLogin.getUsuarioPorCodigoYClave(lUsuario.getStrUsuario(), lUsuario.getStrContrasena());
				usuarioCambio.setStrContrasena(strContrasenaNueva);
				localLogin.modificarUsuario(usuarioCambio);
				
				remoteAuditoria = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
				lAuditoria = new Auditoria();
				lAuditoria.setStrTabla("SEG_M_USUARIO");
				lAuditoria.setStrColumna("USUA_CONTRASEÑA_V");
				lAuditoria.setIntEmpresaPk(lUsuario.getPerfil().getId().getIntPersEmpresaPk());
				lAuditoria.setIntPersonaPk(lUsuario.getIntPersPersonaPk());
				lAuditoria.setStrLlave1(String.valueOf(usuarioCambio.getIntPersPersonaPk()));
				lAuditoria.setIntTipo(Constante.PARAM_T_TIPOAUDITORIA_UPDATE);
				lAuditoria.setStrValoranterior(strContrasena);
				lAuditoria.setStrValornuevo(strContrasenaNueva);
				remoteAuditoria.grabarAuditoria(lAuditoria);
				
				strUsuario = null; 
				strContrasena = null;
				strContrasenaNueva = null;
				strContrasenaValidacion = null;
				msgCambio = null;
				bolCambioClave = true;
				//outcome = "portal.modulo";
				//FacesContext.getCurrentInstance().getExternalContext().dispatch("/portal/autoriza/modulo.jsf"); 
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}/* catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	public String irModulo(){
		String outcome = "portal.modulo";
		bolCambioClave = false;
		return outcome;
	}
	
	public void cerrarSession(ActionEvent event){
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = null;
		try{
			session = ((HttpServletRequest) request).getSession();
			usuario = new Usuario();
			SeguridadFactory.cancelarTicket(request);
			limpiar(event);
			//Inicio: REQ14-002 - bizarq - 20/07/2014
			updateUserSession(session);
			//Fin: REQ14-002 - bizarq - 20/07/2014
			session.removeAttribute(Constante.USUARIO_LOGIN);
			session.invalidate();
			
		}catch (SeguridadException e) {
			e.printStackTrace();
		}
	}
	
	public void closeSession(){
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = null;
		try{
			session = ((HttpServletRequest) request).getSession();
			usuario = new Usuario();
			SeguridadFactory.cancelarTicket(request);
			//limpiar(event);
			updateUserSession(session);
			session.removeAttribute(Constante.USUARIO_LOGIN);
			session.invalidate();
		} catch (SeguridadException e) {
			e.printStackTrace();
		}
	}
	
	public String login() throws DaoException{
//		log.info("Empezando método Login...");
		//setService(usuarioPerfilService);
		HashMap map = new HashMap();
		HashMap mapLogin = new HashMap();
		HashMap mapLoginLog = new HashMap();
		HashMap prmtBusqMenu = new HashMap();
		ArrayList arrayUsuario = new ArrayList();		
		ArrayList arrayIntentos = new ArrayList();
		ArrayList arrayOpciones = new ArrayList();
		ArrayList listaOpciones = new ArrayList();		
//		Usuario usuario = (Usuario) getBeanBusqueda();	
		
		String intIdUsuario = "";
		String redirectTo = "login";
		String intNumIntentos = "";
				
		HashMap prmtBusqUsuario = new HashMap();
//		codigo = usuario.getCodigo();
//		contrasena = usuario.getContrasenha();
		empresa = "163";
		
//		if (usuario.getCodigo()==null || usuario.getCodigo().equals("") ||
//				usuario.getContrasenha()==null || usuario.getContrasenha().equals("") ) {
//			if(usuario.getCodigo()==null || usuario.getCodigo().equals("")){
//				setMessageError("Ingrese el nombre de usuario");
//			}
//			if(usuario.getContrasenha()==null || usuario.getContrasenha().equals("")){
//				setMessageError("Ingrese la contraseña");
//			}			
//			return "login";
//		}		
		
		try {
			EmpresaFacadeRemote facadeEmpresa =	(EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			pe.com.tumi.persona.empresa.domain.Empresa lEmpresa = facadeEmpresa.getEmpresaPorPK(Integer.parseInt(empresa)); 
			if(lEmpresa != null)
				intNumIntentos = String.valueOf(lEmpresa.getIntIntentosIngreso());
			else
				intNumIntentos = "0";
			/*
			arrayIntentos = getService().numIntentos(Integer.parseInt(""+empresa)); 	
			if(arrayIntentos.size() > 0){
				for(int i=0; i<arrayIntentos.size() ; i++){
		        	HashMap hash = (HashMap) arrayIntentos.get(i);
		        	intNumIntentos=""+hash.get("C_INTENTOSINGRESO");
		        }			
			}		
			*/
		} catch (BusinessException e1) {
			e1.printStackTrace();
		} catch (EJBFactoryException e1) {
			e1.printStackTrace();
		}
		
//		try {	
//			arrayUsuario = getService().loginUsuarios(codigo, contrasena,empresa);
//			
//			if(arrayUsuario.size() == 0){
//				redirectTo = "login";
//			}else if(arrayUsuario.size() > 0){
//				for(int i=0; i<arrayUsuario.size() ; i++){
//		        	HashMap hash = (HashMap) arrayUsuario.get(i);
//		        	intIdUsuario=""+hash.get("PERS_IDPERSONA_N");
//		        	String strUsuario = ""+hash.get("USUA_USUARIO_V");
//		        }
//				idUsuario = intIdUsuario;
//				cboEmpresasUsuario(intIdUsuario);
//				redirectTo = "login2";
//			}			
//
//			/**Login**/
//			
//			return redirectTo;		
//		} catch (DaoException e) {
////			setCatchError(e);
//			log.debug(e.getMessage());
//			return "login";
//		}
		return "login"; // temporal
	}
	
	public String login2() throws ParseException{
//		log.info("Empezando método Login...");
		//setService(usuarioPerfilService);
		HashMap map = new HashMap();
		HashMap mapLogin = new HashMap();
		HashMap mapLoginLog = new HashMap();
		HashMap prmtBusqMenu = new HashMap();
		ArrayList arrayUsuario = new ArrayList();
		
		ArrayList arrayRegistro = new ArrayList();
		ArrayList arrayOpciones = new ArrayList();
		ArrayList listaOpciones = new ArrayList();		
//		Usuario usuario = (Usuario) getBeanBusqueda();	
		
		String intIdUsuario = "";		
		String fecRegistro = "";
		String diasVigencia = "";
		String alertCaduca = "";
		String redirectTo = "login2";
				
		HashMap prmtBusqUsuario = new HashMap();
		
//		codigo = usuario.getCodigo();
//		beanSesion.setStrCodigoUsu(codigo);
//		contrasena = usuario.getContrasenha();		
//		beanSesion.setStrContrasena(contrasena);
//		empresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:cboEmpresa");
//		usuario.setEmpresa(Integer.parseInt(empresa.trim()));
//		beanSesion.setIntIdEmpresa(Integer.parseInt(empresa.trim()));
//		int codEmpresa = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:cboEmpresa"));
//		log.info("perfil = " + usuario.getPerfil());
//		perfil = "" + usuario.getPerfil();			
//		beanSesion.setIntIdPerfil(Integer.parseInt(perfil));
//		
//		Usuario usuarioEnSesion = (Usuario)getSpringBean(Constante.SESSION_USER);
//						
//		try {								
//			
//			arrayUsuario = getService().getDAO().loginUsuarios(codigo, contrasena,empresa);
//			log.info("arrayUsuario.size() :" + arrayUsuario.size());
//			
//			if(arrayUsuario.size() == 0){				
//				redirectTo = "login";
//			}else if(arrayUsuario.size() > 0){
//				for(int i=0; i<arrayUsuario.size() ; i++){
//		        	HashMap hash = (HashMap) arrayUsuario.get(i);
//		        	log.info("hash.get(PERS_IDPERSONA_N) = "+hash.get("PERS_IDPERSONA_N"));
//		        	intIdUsuario=""+hash.get("PERS_IDPERSONA_N");
//		        	beanSesion.setIntIdUsuario(Integer.parseInt(intIdUsuario));
//		        	log.info("hash.get(USUA_USUARIO_V) = "+hash.get("USUA_USUARIO_V"));
//		        	String strUsuario = ""+hash.get("USUA_USUARIO_V");
//		        }
//				idUsuario = intIdUsuario;			
//			}
//			
////			if (usuario.getEmpresa()==null || usuario.getEmpresa().equals(0) ||
////					usuario.getPerfil()==null || usuario.getPerfil().equals(0) ) {
////				if(usuario.getEmpresa()==null || usuario.getEmpresa().equals(0)){
////					setMessageError("Seleccione empresa");
////				}
////				if(usuario.getPerfil()==null || usuario.getPerfil().equals(0)){
////					setMessageError("Seleccione perfil");
////				}			
////				return "login2";
////			}		
//			
//			prmtBusqMenu.put("pIntIdPerfil", Integer.parseInt(""+perfil));
//			prmtBusqMenu.put("pIntIdUsuario", Integer.parseInt(""+intIdUsuario));
//			prmtBusqMenu.put("pStrIdPadre", "");
//			prmtBusqMenu.put("pIntIdEmpresa", Integer.parseInt(""+empresa));
//						
//			/*
//			arrayRegistro = getService().fecRegistro(Integer.parseInt(""+idUsuario)); 	
//			if(arrayRegistro.size() > 0){
//				for(int i=0; i<arrayRegistro.size() ; i++){
//		        	HashMap hash = (HashMap) arrayRegistro.get(i);
//		        	fecRegistro=""+hash.get("USUA_FECHAREGISTRO_D");
//		        	diasVigencia=""+hash.get("EMP_VIGENCIACLAVES_N");
//		        	alertCaduca=""+hash.get("EMP_ALERTACADUCIDAD_N");
//		        }			
//			}			
//			
//			
//			Calendar date = Calendar.getInstance(); 			
//			//String hora = (date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
//			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
//			Date fecRegistroAct = (Date)formato.parse(fecRegistro);
//			date.setTime(fecRegistroAct);
//			date.add(Calendar.DATE,Integer.parseInt(diasVigencia)); //le suma los dias 
//			//fecRegistroAct.add(Calendar.DAY_OF_MONTH, diasVigencia);
//			Date fecCompara = date.getTime();
//			Date hoy = new Date();
//			
//			if(fecCompara.after(hoy)){
//				redirectTo = "login";
//			}
//			
//			Calendar date1 = Calendar.getInstance();
//			date1.setTime(fecRegistroAct);
//			date1.add(Calendar.DATE, Integer.parseInt(alertCaduca));
//			Date fecAlertCaduca = date1.getTime();
//			
//			if(fecAlertCaduca.after(hoy)){
//				long diferencia = fecAlertCaduca.getTime() - hoy.getTime();
//				double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
//				//mensaje de cuantos dias le quedan
//			}
//			*/
//			
//			//Timer timer = new Timer();			
//
//		    // Dentro de 0 milisegundos avísame cada 1000 milisegundos 
//		    //timer.scheduleAtFixedRate(timerTask, 0, 1000); 
//			
////			arrayOpciones = getService().listarMenuPerfUsuario(prmtBusqMenu);
////			log.info("arrayOpciones.size() :" + arrayOpciones.size());
////			
////			for(int i=0; i<arrayOpciones.size() ; i++){
////	        	HashMap hash = (HashMap) arrayOpciones.get(i);
////	        	OpcionMenu opc = new OpcionMenu();
////	        	String strIdTranPadre = "" + hash.get("TRAN_IDTRANSACCIONPADRE_C");
////	        	opc.setIdPadre(strIdTranPadre);
////	        	String strNombre = "" + hash.get("TRAN_NOMBRE_V");
////	        	opc.setNomOpcion(strNombre);
////	        	if((hash.get("TRAN_IDTRANSACCIONPADRE_C") == null)||(hash.get("TRAN_IDTRANSACCIONPADRE_C") == "")){
////	        		listaOpciones.add(opc);
////	        	}
////	        }
//			setBeanListOpciones(listaOpciones);
//	        
//			redirectTo = "bienvenida";
//			/**Login**/
//
//			return redirectTo;
//		} catch (DaoException e) {
////			setCatchError(e);
//			return "login";
//		}
		return "login"; // temporal
	}
	
	public void cboEmpresasUsuario(String codUsuario) throws DaoException {
//		log.info("-----------------------Debugging ControlsFiller.getCboEmpresasUsuario()-----------------------------");
		//setService(usuarioPerfilService);
		String csvPkEmpresa = null;
		HashMap prmtBusqEmpresa = new HashMap();
		prmtBusqEmpresa.put("pIntIdPersona", codUsuario);
//		log.info("codUsuario: "+codUsuario);
		
		//El metodo devuelve una sola fila
		//ArrayList arrayEmpresas = new ArrayList();
//		try{
//			List<EmpresaUsuario> listaEmpresa = getService().listarEmpresasUsuario(prmtBusqEmpresa);
//			for(int i=0;i<listaEmpresa.size();i++){
//				if(csvPkEmpresa==null)
//					csvPkEmpresa = String.valueOf(listaEmpresa.get(i).getIntIdEmpresa());
//				else
//					csvPkEmpresa = csvPkEmpresa + "," + listaEmpresa.get(i).getIntIdEmpresa();
//			}
//			PersonaFacadeRemote facade =	(PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
//			listaJuridicaEmpresa = facade.getListaJuridicaPorInPk(csvPkEmpresa);
//		} catch (BusinessException e) {
//			e.printStackTrace();
//		} catch (EJBFactoryException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		/*for(int i=0; i<arrayEmpresas.size() ; i++){
        	Empresa emp = (Empresa) arrayEmpresas.get(i);
        	HashMap hash = (HashMap) arrayEmpresas.get(i);
        	log.info("hash.get(EMPR_IDEMPRESA_N) = "+hash.get("EMPR_IDEMPRESA_N"));
        	Integer intIdPersona = Integer.parseInt(""+hash.get("EMPR_IDEMPRESA_N"));
        	log.info("hash.get(V_EMPRESA) = "+hash.get("V_EMPRESA"));
        	String strRazonsocial = ""+hash.get("V_EMPRESA");
        	this.cboEmpresas.add(new SelectItem(intIdPersona,strRazonsocial));
        }
        SelectItem lblSelect = new SelectItem(0,"Seleccionar..");
        this.cboEmpresas.add(0, lblSelect);
		return cboEmpresas;*/
		//return listEmpresa;
	}
	
	public void reloadCboPerfusuario(ValueChangeEvent event) throws DaoException {
//		log.info("-----------------------Debugging UsuarioController.reloadCboPerfusuario()-----------------------------");
		ArrayList arrayUsuario = new ArrayList();
		//setService(usuarioPerfilService);
		String idCboSucursal = (String)event.getNewValue();
		String	idCboEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:cboEmpresa");
//		log.info("idCboEmpresa() = "+idCboEmpresa);
		String idUsuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:hiddenStrUsuario");
//		log.info("idUsuario = "+idUsuario);
		String idClave = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:hiddenStrContrasena");
//		log.info("idClave = "+idClave);
//		arrayUsuario = getService().getDAO().loginUsuarios(idUsuario, idClave,empresa);
//		String intIdUsuario = "";
//		if(arrayUsuario.size() > 0){
//			for(int i=0; i<arrayUsuario.size() ; i++){
//	        	HashMap hash = (HashMap) arrayUsuario.get(i);
//	        	log.info("hash.get(PERS_IDPERSONA_N) = "+hash.get("PERS_IDPERSONA_N"));
//	        	intIdUsuario=""+hash.get("PERS_IDPERSONA_N");	        	
//	        }							
//		}
		HashMap prmtBusqPerfiles = new HashMap();
		prmtBusqPerfiles.put("pIntIdEmpresa", Integer.parseInt(idCboEmpresa));
//		prmtBusqPerfiles.put("pIntIdUsuario", Integer.parseInt(intIdUsuario));
		//prmtBusqPerfiles.put("pIntIdSucursal", Integer.parseInt(idCboSucursal));
		prmtBusqPerfiles.put("pIntIdPerfil",null);
		prmtBusqPerfiles.put("pTxtPerfil",null);
		prmtBusqPerfiles.put("pCboTipoPerfil",null);
		prmtBusqPerfiles.put("pCboEstadoPerfil",null);
		prmtBusqPerfiles.put("pTxtFecIni",null);
		prmtBusqPerfiles.put("pTxtFecFin",null);
		
//		listPerfil = getService().listarPerfusuario(prmtBusqPerfiles);
	    //log.info("arrayPerfiles.size(): "+listPerfil.size());
	    
	}
	
	/**
	 * @return
	 * @uml.property  name="panelMenu"
	 */
	public HtmlPanelMenu getPanelMenu() {
        //List<Menu> aListN1 = new ArrayList<Menu>();   
		//setService(usuarioPerfilService);
		HashMap prmtBusqMenu = new HashMap();
		ArrayList arrayOpciones = new ArrayList();
		ArrayList arrayOpciones1 = new ArrayList();
		ArrayList arrayOpciones2 = new ArrayList();
		ArrayList arrayOpciones3 = new ArrayList();
		ArrayList arrayUsuario = new ArrayList();
//		Usuario usuarioEnSesion = (Usuario)getSpringBean(Constante.SESSION_USER);
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();
		String metodoBild = null;
		String codUsuario = "";
        panelMenu = new HtmlPanelMenu();   
        String idPadre = "";        
		String codigo 	  = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:hiddenStrUsuario") == null ? "0" :FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:hiddenStrUsuario");
		String contrasena = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:hiddenStrContrasena") == null ? "0" :FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:hiddenStrContrasena");
		String codEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:cboEmpresa") == null ? "0" :FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:cboEmpresa");
		String codPerfil  = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:cboPerfiles") == null ? "0" :FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmLoginUsuarios:cboPerfiles");
		
//		try {	
//			arrayUsuario = getService().getDAO().loginUsuarios(codigo, contrasena,empresa);
//			log.info("arrayUsuario.size() :" + arrayUsuario.size());
//		} catch (DaoException e) {
////			setCatchError(e);
//			log.debug(e.getMessage());
//		}
		
		for(int i=0; i<arrayUsuario.size() ; i++){
        	HashMap hash = (HashMap) arrayUsuario.get(i);
//        	log.info("hash.get(PERS_IDPERSONA_N) = "+hash.get("PERS_IDPERSONA_N"));
        	codUsuario=""+hash.get("PERS_IDPERSONA_N");
//        	log.info("hash.get(USUA_USUARIO_V) = "+hash.get("USUA_USUARIO_V"));
        	String strUsuario = ""+hash.get("USUA_USUARIO_V");
        }
		idUsuario = codUsuario;			
		
        
        panelMenu.setStyle("width:193px");
        panelMenu.setId("tumi");
        panelMenu.setMode("ajax");
        panelMenu.setIconExpandedGroup("disc");   
        panelMenu.setIconCollapsedGroup("disc");   
        panelMenu.setIconExpandedTopGroup("chevronUp");   
        panelMenu.setIconGroupTopPosition("right");   
        panelMenu.setIconCollapsedTopGroup("chevronDown");   
        panelMenu.setExpandSingle(true);
        panelMenu.setExpandMode("ajax");        
  
//        try {   
//        	prmtBusqMenu.put("pIntIdPerfil", Integer.parseInt(codPerfil));
//			prmtBusqMenu.put("pIntIdUsuario", Integer.parseInt(idUsuario));
//			prmtBusqMenu.put("pStrIdPadre", "");
//			prmtBusqMenu.put("pIntIdEmpresa", Integer.parseInt(codEmpresa));
//			log.info("arrayOpciones: "+arrayOpciones);
//			arrayOpciones = getService().listarMenuPerfUsuario(prmtBusqMenu);
//			log.info("opciones:");
//        } catch (Exception e) {   
//            e.printStackTrace();   
//        }   
//        
//        for(int i=0; i<arrayOpciones.size() ; i++){
//        	HashMap hash = (HashMap) arrayOpciones.get(i);
//        	HtmlPanelMenuGroup menuGroup = new HtmlPanelMenuGroup();
//        	if(hash.get("TRAN_IDTRANSACCIONPADRE_C") == null){ 	        		        	     
//	            menuGroup.setLabel((String)hash.get("TRAN_NOMBRE_V"));   
//	            idPadre = (String)hash.get("TRAN_IDTRANSACCION_C"); 
//	            try {
//	            prmtBusqMenu.put("pIntIdPerfil", Integer.parseInt(codPerfil));
//				prmtBusqMenu.put("pIntIdUsuario", Integer.parseInt(idUsuario));
//				prmtBusqMenu.put("pStrIdPadre", ""+idPadre);
//				prmtBusqMenu.put("pIntIdEmpresa", Integer.parseInt(codEmpresa));
//				
//				arrayOpciones1 = getService().listarMenuPerfUsuario(prmtBusqMenu);
//	            } catch (Exception e) {   
//	                e.printStackTrace();   
//	            } 
////	            if(arrayOpciones1.size()!=0){
////					for(int j=0; j<arrayOpciones1.size() ; j++){
////			        	HashMap hash1 = (HashMap) arrayOpciones1.get(j); 
////		            	HtmlPanelMenuGroup itemN2 = new HtmlPanelMenuGroup();   
////		            	HtmlPanelMenuItem itemN2b = new HtmlPanelMenuItem();   
////		                itemN2.setLabel((String)hash1.get("TRAN_NOMBRE_V"));   
////		                //itemN2b.setAction(app.createMethodBinding((String)hash1.get("TRAN_ACCION_V"), null));   
////		                itemN2b.setLabel((String)hash1.get("TRAN_NOMBRE_V"));   
////		                idPadre = (String)hash1.get("TRAN_IDTRANSACCION_C"); 
////		                try {
////		                prmtBusqMenu.put("pIntIdPerfil", Integer.parseInt(codPerfil));
////		    			prmtBusqMenu.put("pIntIdUsuario", Integer.parseInt(idUsuario));
////		    			prmtBusqMenu.put("pStrIdPadre", ""+idPadre);
////		    			prmtBusqMenu.put("pIntIdEmpresa", Integer.parseInt(codEmpresa));
////		    			
//////		    			arrayOpciones2 = getService().listarMenuPerfUsuario(prmtBusqMenu);
//////						} catch (Exception e) {   
//////				            e.printStackTrace();   
//////				        }
//////						if(arrayOpciones2.size()!=0){						
//////			    			for(int k=0; k<arrayOpciones2.size() ; k++){
//////			    	        	HashMap hash2 = (HashMap) arrayOpciones2.get(k); 
//////			                    HtmlPanelMenuItem itemN3 = new HtmlPanelMenuItem();   
//////			                    HtmlPanelMenuGroup itemN3b = new HtmlPanelMenuGroup();
//////			                    itemN3.setLabel((String)hash2.get("TRAN_NOMBRE_V"));   
//////			                    //itemN3.setAction(app.createMethodBinding((String)hash2.get("TRAN_ACCION_V"), null));   
//////			                    itemN3b.setLabel((String)hash2.get("TRAN_NOMBRE_V"));   
//////			                    idPadre = (String)hash2.get("TRAN_IDTRANSACCION_C");    
//////			                       
//////			                    try {
//////			    	            prmtBusqMenu.put("pIntIdPerfil", Integer.parseInt(codPerfil));
//////			    	            prmtBusqMenu.put("pIntIdUsuario", Integer.parseInt(idUsuario));
//////			    	    		prmtBusqMenu.put("pStrIdPadre", ""+idPadre);
//////			    	    		prmtBusqMenu.put("pIntIdEmpresa", Integer.parseInt(codEmpresa));
//////			    	    		
////////			    	    		arrayOpciones3 = getService().listarMenuPerfUsuario(prmtBusqMenu);
////////			    				} catch (Exception e) {   
////////			    			        e.printStackTrace();   
////////			    			    } 
////////			    				if(arrayOpciones3.size()!=0){			    								    				
////////				    				for(int l=0; l<arrayOpciones3.size() ; l++){
////////					    	        	HashMap hash3 = (HashMap) arrayOpciones3.get(l); 
////////					                    HtmlPanelMenuItem itemN4 = new HtmlPanelMenuItem();   
////////					                    itemN4.setLabel((String)hash3.get("TRAN_NOMBRE_V"));      				                    					                    
////////					                    metodoBild = String.valueOf("#{" + (String)hash3.get("TRAN_ACCION_V") + "}");//"#{AdminMenuBean.accionOp"+numMet+"}";					                    					                    
////////					                    MethodBinding binding = FacesContext.getCurrentInstance().getApplication().createMethodBinding(metodoBild, new Class[]{});					                    
////////					                    itemN4.setAction(binding);		
////////					                    itemN3b.getChildren().add(itemN4);
////////				    				} 
////////				    				itemN2.getChildren().add(itemN3b);
////////			    				}else{
////////				    				itemN2.getChildren().add(itemN3);
////////			    				}
//////			                }  
//////			    			menuGroup.getChildren().add(itemN2);
//////						}else{
//////							menuGroup.getChildren().add(itemN2b);
//////						}
////					}
////	            }   
////			panelMenu.getChildren().add(menuGroup);
//        	}        	 
//        }
        return panelMenu;   
    }
	
	public String cerrarPopupSession(){
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		if(session.getAttribute(Constante.USUARIO_LOGIN) == null){
			sendRedirect("/portal/autentica/login.jsf");
		}
		
		return "";
	}
	
	private void sendRedirect(String ruta) {

		FacesContext currentInstance = FacesContext.getCurrentInstance();

		HttpServletResponse response = (HttpServletResponse) currentInstance.getExternalContext().getResponse();
		try {
			currentInstance.responseComplete();

			response.sendRedirect(currentInstance.getExternalContext().getRequestContextPath() + ruta);
		} catch (IOException ioe) {
			
		}
	}
  
    /**
	 * @param panelMenu
	 * @uml.property  name="panelMenu"
	 */
    public void setPanelMenu(HtmlPanelMenu panelMenu) {   
        this.panelMenu = panelMenu;   
    }    
    
    public String empresa(){    	
		//setService(rolService);
		return "empresa"; 
    }		
    
    public String usuarioPerfil(){    	
		//setService(rolService);
		return "usuarioPerfil"; 
    }
    
    public String adminMenu(){    	
		//setService(rolService);
		return "adminMenu"; 
    }
    
    public String auditoriaSist(){    	
		//setService(rolService);
		return "auditoriaSist"; 
    }
    
    public String registroPc(){    	
		//setService(rolService);
		return "registroPc"; 
    }
    
    public String horarioIngreso(){    	
		//setService(rolService);
		return "horarioIngreso"; 
    }
    
    public String admFormDoc(){    	
		//setService(rolService);
		return "admFormDoc"; 
    }
    
    public String accesosEspeciales(){    	
		//setService(rolService);
	
		return "accesosEspeciales"; 
    }
    
    public String perfilMof(){    	
		//setService(rolService);
	
		return "perfilMof"; 
    }
    
    public String reglamentosPoliticas(){    	
		//setService(rolService);
	
		return "reglamentosPoliticas"; 
    }
       
    /*
    public MethodExpression getOutcome(String outcome) {   
        FacesContext fc = FacesContext.getCurrentInstance();   
        ELContext ec = fc.getELContext();     
        return fc.getApplication().getExpressionFactory().createMethodExpression(ec, "#{" + outcome + "}", String.class, new Class[] {});   
    } 
    */       

	public void clean(ActionEvent event){
		setVerifyPassword("");
		setNewPassword("");
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}

	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	private boolean validarEntradaPorEmpresaSucursalFecha(int empresa, int sucursal, int estado){
		
		boolean esPermitido = false;		
		DiasAccesos diasAccesos = new DiasAccesos();
		diasAccesos.setId(new DiasAccesosId());
		diasAccesos.getId().setIntPersEmpresa(empresa);
		diasAccesos.getId().setIntIdTipoSucursal(sucursal);
		diasAccesos.setIntIdEstado(estado);
		
		try {
			
			esPermitido = permisoFacade.validarAccesoPorEmpresaYSucursal(diasAccesos);						
			
		} catch (BusinessException e) {
			
			e.printStackTrace();
		}
		
		return esPermitido;
	}
	
	private boolean validarEntradaPorUsuario(int empresa, int usuario, int estado){
		
		boolean esPermitido = false;
		AccesoEspecial accesoEspecial = new AccesoEspecial();
		accesoEspecial.setIntPersEmpresa(empresa);
		accesoEspecial.setIntPersPersonaOpera(usuario);
		accesoEspecial.setIntIdEstado(estado);
		
		try {
			esPermitido = permisoFacade.validarAccesoPorEmpresaUsuario(accesoEspecial);
				
		} catch (BusinessException e) {
			
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void conseguirMAC(){
		NetworkInterface a; 
		
		try {
			a = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
			byte[] mac = a.getHardwareAddress();
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));  
			} 
			
			log.info("MAC: " + sb.toString());
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	/**
	 * @return
	 * @uml.property  name="newPassword"
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword
	 * @uml.property  name="newPassword"
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return
	 * @uml.property  name="verifyPassword"
	 */
	public String getVerifyPassword() {
		return verifyPassword;
	}
	/**
	 * @param verifyPassword
	 * @uml.property  name="verifyPassword"
	 */
	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}
	/**
	 * @param intento
	 * @uml.property  name="intento"
	 */
	public void setIntento(int intento) {
		this.intento = intento;
	}
	/**
	 * @return
	 * @uml.property  name="intento"
	 */
	public int getIntento() {
		return intento;
	}	

	/**
	 * @param fullName
	 * @uml.property  name="fullName"
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return
	 * @uml.property  name="fullName"
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param lastLogin
	 * @uml.property  name="lastLogin"
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return
	 * @uml.property  name="lastLogin"
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param failAttempts
	 * @uml.property  name="failAttempts"
	 */
	public void setFailAttempts(int failAttempts) {
		this.failAttempts = failAttempts;
	}

	/**
	 * @return
	 * @uml.property  name="failAttempts"
	 */
	public int getFailAttempts() {
		return failAttempts;
	}

	/*public RolService getRolService() {
		return rolService;
	}

	public void setRolService(RolService rolService) {
		this.rolService = rolService;
	}*/
	
	/**
	 * @return
	 * @uml.property  name="codigo"
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 * @uml.property  name="codigo"
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return
	 * @uml.property  name="contrasena"
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * @param contrasena
	 * @uml.property  name="contrasena"
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	/**
	 * @return
	 * @uml.property  name="empresa"
	 */
	public String getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 * @uml.property  name="empresa"
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	/**
	 * @return
	 * @uml.property  name="perfil"
	 */
	public String getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil
	 * @uml.property  name="perfil"
	 */
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	/**
	 * @return
	 * @uml.property  name="idUsuario"
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario
	 * @uml.property  name="idUsuario"
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/*public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}*/
	
	/*public ArrayList<SelectItem> getCboOpciones() {
		return cboOpciones;
	}

	public void setCboOpciones(ArrayList<SelectItem> cboOpciones) {
		this.cboOpciones = cboOpciones;
	}*/
	
	/*public List getBeanListOpciones() {
		return beanListOpciones;
	}

	public void setBeanListOpciones(List beanListOpciones) {
		this.beanListOpciones = beanListOpciones;
	}*/

	/*public UsuarioServiceImpl getUsuarioPerfilService() {
		return usuarioPerfilService;
	}

	public void setUsuarioPerfilService(UsuarioServiceImpl usuarioPerfilService) {
		this.usuarioPerfilService = usuarioPerfilService;
	}*/

	public List<Juridica> getListaJuridicaEmpresa() {
		return listaJuridicaEmpresa;
	}

	public void setListaJuridicaEmpresa(List<Juridica> listaJuridicaEmpresa) {
		this.listaJuridicaEmpresa = listaJuridicaEmpresa;
	}

	/*public List<Perfil> getListPerfil() {
		return listPerfil;
	}

	public void setListPerfil(List<Perfil> listPerfil) {
		this.listPerfil = listPerfil;
	}*/

	/**
	 * @return
	 * @uml.property  name="usuario"
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 * @uml.property  name="usuario"
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Transaccion> getListaTransaccion() {
		return listaTransaccion;
	}

	public void setListaTransaccion(List<Transaccion> listaTransaccion) {
		this.listaTransaccion = listaTransaccion;
	}

	/**
	 * @return
	 * @uml.property  name="intIdEmpresa"
	 */
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}

	/**
	 * @param intIdEmpresa
	 * @uml.property  name="intIdEmpresa"
	 */
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}

	/**
	 * @return
	 * @uml.property  name="intIdSucursalPersona"
	 */
	public Integer getIntIdSucursalPersona() {
		return intIdSucursalPersona;
	}
	/**
	 * @param intIdSucursalPersona
	 * @uml.property  name="intIdSucursalPersona"
	 */
	public void setIntIdSucursalPersona(Integer intIdSucursalPersona) {
		this.intIdSucursalPersona = intIdSucursalPersona;
	}

	public List<Juridica> getListaJuridicaSucursal() {
		return listaJuridicaSucursal;
	}

	public void setListaJuridicaSucursal(List<Juridica> listaJuridicaSucursal) {
		this.listaJuridicaSucursal = listaJuridicaSucursal;
	}

	/**
	 * @return
	 * @uml.property  name="intIdPerfil"
	 */
	public Integer getIntIdPerfil() {
		return intIdPerfil;
	}
	/**
	 * @param intIdPerfil
	 * @uml.property  name="intIdPerfil"
	 */
	public void setIntIdPerfil(Integer intIdPerfil) {
		this.intIdPerfil = intIdPerfil;
	}

	public List<Perfil> getListaPerfil() {
		return listaPerfil;
	}
	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

	/**
	 * @return
	 * @uml.property  name="intIdSubSucursal"
	 */
	public Integer getIntIdSubSucursal() {
		return intIdSubSucursal;
	}
	/**
	 * @param intIdSubSucursal
	 * @uml.property  name="intIdSubSucursal"
	 */
	public void setIntIdSubSucursal(Integer intIdSubSucursal) {
		this.intIdSubSucursal = intIdSubSucursal;
	}

	public List<Subsucursal> getListaSubSucursal() {
		return listaSubSucursal;
	}
	public void setListaSubSucursal(List<Subsucursal> listaSubSucursal) {
		this.listaSubSucursal = listaSubSucursal;
	}

	/**
	 * @return
	 * @uml.property  name="msgPortal"
	 */
	public PortalMsg getMsgPortal() {
		return msgPortal;
	}

	/**
	 * @param msgPortal
	 * @uml.property  name="msgPortal"
	 */
	public void setMsgPortal(PortalMsg msgPortal) {
		this.msgPortal = msgPortal;
	}

	/**
	 * @return
	 * @uml.property  name="strUsuario"
	 */
	public String getStrUsuario() {
		return strUsuario;
	}

	/**
	 * @param strUsuario
	 * @uml.property  name="strUsuario"
	 */
	public void setStrUsuario(String strUsuario) {
		this.strUsuario = strUsuario;
	}

	/**
	 * @return
	 * @uml.property  name="strContrasena"
	 */
	public String getStrContrasena() {
		return strContrasena;
	}

	/**
	 * @param strContrasena
	 * @uml.property  name="strContrasena"
	 */
	public void setStrContrasena(String strContrasena) {
		this.strContrasena = strContrasena;
	}

	/**
	 * @return
	 * @uml.property  name="strContrasenaNueva"
	 */
	public String getStrContrasenaNueva() {
		return strContrasenaNueva;
	}

	/**
	 * @param strContrasenaNueva
	 * @uml.property  name="strContrasenaNueva"
	 */
	public void setStrContrasenaNueva(String strContrasenaNueva) {
		this.strContrasenaNueva = strContrasenaNueva;
	}

	/**
	 * @return
	 * @uml.property  name="strContrasenaValidacion"
	 */
	public String getStrContrasenaValidacion() {
		return strContrasenaValidacion;
	}

	/**
	 * @param strContrasenaValidacion
	 * @uml.property  name="strContrasenaValidacion"
	 */
	public void setStrContrasenaValidacion(String strContrasenaValidacion) {
		this.strContrasenaValidacion = strContrasenaValidacion;
	}

	/**
	 * @return
	 * @uml.property  name="msgCambio"
	 */
	public CambioMsg getMsgCambio() {
		return msgCambio;
	}

	/**
	 * @param msgCambio
	 * @uml.property  name="msgCambio"
	 */
	public void setMsgCambio(CambioMsg msgCambio) {
		this.msgCambio = msgCambio;
	}

	public Boolean getBolCambioClave() {
		return bolCambioClave;
	}

	/**
	 * @param bolCambioClave
	 * @uml.property  name="bolCambioClave"
	 */
	public void setBolCambioClave(Boolean bolCambioClave) {
		this.bolCambioClave = bolCambioClave;
	}

	public int getActivaPopup() {
		return activaPopup;
	}

	public void setActivaPopup(int activaPopup) {
		this.activaPopup = activaPopup;
	}

	public String getStrMessageValidUserSession() {
		return strMessageValidUserSession;
	}

	public void setStrMessageValidUserSession(String strMessageValidUserSession) {
		this.strMessageValidUserSession = strMessageValidUserSession;
	}
	
}