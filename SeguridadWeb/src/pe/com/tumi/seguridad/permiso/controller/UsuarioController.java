package pe.com.tumi.seguridad.permiso.controller;

import java.util.Date;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.facade.AuditoriaFacadeRemote;
import pe.com.tumi.parametro.general.domain.Ubigeo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.contacto.domain.ComunicacionPK;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.domain.DomicilioPK;
import pe.com.tumi.empresa.service.impl.EmpresaServiceImpl;
import pe.com.tumi.file.domain.File;
import pe.com.tumi.file.service.impl.FileUploadServiceImpl;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeLocal;
import pe.com.tumi.seguridad.login.bean.ComunicacionMsg;
import pe.com.tumi.seguridad.login.bean.DomicilioMsg;
import pe.com.tumi.seguridad.login.bean.UsuarioMsg;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfil;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfilId;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursalId;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursalId;
import pe.com.tumi.seguridad.login.domain.composite.UsuarioComp;
import pe.com.tumi.seguridad.login.facade.LoginFacadeLocal;
import pe.com.tumi.seguridad.login.validador.LoginValidador;
import pe.com.tumi.seguridad.permiso.bean.PerfilMsg;
import pe.com.tumi.seguridad.permiso.bean.PermisoMsg;
import pe.com.tumi.seguridad.permiso.bean.SubMenuMsg;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeLocal;
import pe.com.tumi.seguridad.permiso.validador.MenuValidador;
import pe.com.tumi.seguridad.permiso.validador.PerfilValidador;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.usuario.domain.MenuPermiso;
import pe.com.tumi.seguridad.usuario.domain.PerfilUsuario;
import pe.com.tumi.seguridad.usuario.domain.Permiso;
import pe.com.tumi.seguridad.usuario.domain.SubSucursalUsuario;
import pe.com.tumi.seguridad.usuario.domain.SucursalUsuario;
import pe.com.tumi.usuario.service.impl.UsuarioServiceImpl;


/************************************************************************/
/* Nombre de la clase: UsuarioController                            */
/* Funcionalidad : Clase que que tiene los parametros de busqueda       */
/* y validaciones de los Usuarios - Perfiles                                   */
/* Ref. : 																*/
/* Autor : CDLR 														*/
/* Versión : V1 														*/
/* Fecha creación : 11/10/2011 											*/
/* **********************************************************************/

public class UsuarioController extends GenericController {
	
	//Variables locales
	private    UsuarioServiceImpl	usuarioPerfilService;
	//private    UsuarioService 		usuarioService = (UsuarioService)TumiFactory.get(UsuarioService.class);
	private    EmpresaServiceImpl	empresaService;
	private    FileUploadServiceImpl fileUploadService;
	private    int                	rows = 5;
	//List de Permiso
	private	   List					beanListPermisos;
	private	   List					beanListMenuesPermisos;
	//private	   Usuario				beanUsuario	= new Usuario();
	private	   AdminMenu			beanAdminMenu = new AdminMenu();
	private    PerfilUsuario		beanPerfilUsuario = new PerfilUsuario();
	private    SucursalUsuario		beanSucursalUsuario = new SucursalUsuario();
	private    SubSucursalUsuario	beanSubSucursalUsuario = new SubSucursalUsuario();
	private    Permiso				beanPermiso = new Permiso();
	/////Parámetros de entrada para búsqueda de Usuarios
	private	   Domicilio			beanDomicilio = new Domicilio();
	private	   Comunicacion			beanComunicacion = new Comunicacion();
	private	   String				txtEmpresaUsuario;
	private	   Integer				intCboEmpresa;
	private	   Integer				intCboPerfil;
	private	   Integer				cboTipoUsuario;
	private	   Integer				cboEstadoUsuario;
	private	   String				txtUsuarioPerfil;
	private	   String				txtUsuario;
	private	   int					cboTipoSucursal;
	private	   Integer				intCboEmpresaUsuario;
	private	   Integer				intCboSucursalUsuario;
	private	   Integer				intCboPerfilUsuario;
	private	   String				strCboSubSucursalUsuario;
	private	   String				hiddenIdUsuario;
	private	   String				hiddenStrEmpresa;
	private	   String				hiddenStrPerfil;
	private	   String				hiddenStrSucursal;
	private	   String				hiddenStrSubSucursal;
	private	   Date					daFechaIniPerfUsu;
	private	   Date					daFechaFinPerfUsu;
	private	   Date					daFechaIniSucUsu;
	private	   Date					daFechaFinSucUsu;
	private	   Date					daFechaIniSubSucUsu;
	private	   Date					daFechaFinSubSucUsu;
	private	   String				hiddenIdSucursal;
	private	   String				hiddenIdSubSucursal;
	private	   String				msgSucursalUsuario;
	private	   String				rbPerfUsu;
	private    Boolean				fecFinPerfUsuRendered = true;
	private	   String				rbSucUsu;
	private    Boolean				fecFinSucUsuRendered = true;
	private	   String				rbSubSucUsu;
	private    Boolean				fecFinSubSucUsuRendered = true;
	/////Parámetros de entrada para búsqueda de Permiso y para Formulario
	private	   Integer				cboEmpresaPermiso;
	private	   String				txtUsuarioPermiso;
	private	   Integer				cboTipoPerfilPermiso;
	private	   int					cboEstadoPerfilPermiso;
	private	   int					codPerfil;	
	private	   Date					daFechaIniPermiso;
	private	   Date					daFechaFinPermiso;
	private	   String				msgTxtEmpresaPermiso;
	private	   String				msgTxtUsuarioPermiso;
	private	   String				msgCboTipoPerfilPermiso;
	private	   String				msgCboEstadoPermiso;
	private	   String				msgTxtRanFecPermiso;
	private	   String 				strCboEmpresaPermiso;
	private	   String 				strCboPerfilPermiso;
	private	   String 				strCboMenuPermiso01;
	private	   String 				strCboMenuPermiso02;
	private	   String 				strCboMenuPermiso03;
	private	   String 				strCboMenuPermiso04;
	private	   Date					daFechaPermisoIni;
	private	   Date					daFechaPermisoFin;
	private		List<PerfilUsuario> listPerfilUsuario = new ArrayList<PerfilUsuario>();
	////////////////////////////////////////
	private	   String				msgTxtRanFec;
	private	   String				msgTxtPerfil;
	private	   String				msgCboEstado;
	private	   String				msgCboTipoPerfil;
	private	   String				msgTxtEmpresa;
	private	   String				msgSinPerfiles;
	private	   String				hiddenIdEmpresa;
	private	   String				hiddenIdPerfil;
	private	   String				strCboMenu01;
	private	   String				strCboMenu02;
	private	   String				strCboMenu03;
	private	   String				strCboMenu04;
	private    Boolean				usuarioRendered = false;
	private    Boolean				permisoRendered = false;
	private	   Boolean				showMsgError = false;
	
	//Parámetros para Permisos
	private    String				strCboUsuario;
	
	//Arrays para pantalla de Usuarios
	private	   ArrayList 			ArrayEmpresa = new ArrayList();
	private	   ArrayList 			ArrayPerfilUsuario = new ArrayList();
	private	   ArrayList 			ArraySucursalUsuario = new ArrayList();
	private	   ArrayList 			ArraySubSucursalUsuario = new ArrayList();
	private    ArrayList<SelectItem> cboUsuarioSoli	 = new ArrayList<SelectItem>();
	private    ArrayList<SelectItem> cboUsuario = new ArrayList<SelectItem>();	
	private    ArrayList<SelectItem> cboEmpresas = new ArrayList<SelectItem>();	
	private    ArrayList<SelectItem> cboPerfilUsuario = new ArrayList<SelectItem>();	
	private    String 				strNombreImagen;
	private	   Boolean				imgUsuario = false;
	private	   Boolean				chkDireccion = false;
	private	   Boolean				chkComunicacion = false;
	private	   Boolean				chkPerfilUsuario;
	private	   Boolean				chkSubSucursal;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private	   String				msgNomVia;		
	private	   String				msgNumVia;
	private	   String				msgNomZona;
	
	private UsuarioMsg msgUsuario;
	private List<Juridica> listaJuridicaEmpresa;
	private List<Perfil> listaPerfil;
	private String strIndexUsuario;
	private String strEstadoUsuario;
	private UsuarioComp usuarioComp;
	private List<UsuarioComp> listaUsuarioComp;
	private Usuario usuario;
	private String strTipoMantUsuario;
	private	String strValidarUsuario;
	
	private List<EmpresaUsuario> listaEmpresaUsuarioEliminado;
	private Map<String,List<Object>> mapaEliminado;
	private Boolean esPopPupValido;
	private Domicilio domicilio;
	private Comunicacion comunicacion;
	private Persona personaEliminado;
	private List<Ubigeo> listaUbigeoDepartamento;
	private List<Ubigeo> listaUbigeoProvincia;
	private List<Ubigeo> listaUbigeoDistrito;
	//-------------------------Atributos de Perfil-------------------------
	private Perfil filtroPerfil;
	private String strIndexPerfil;
	private String strEstadoPerfil;
	private List<Perfil> listaFiltroPerfil;
	private List<Perfil> listaBusquedaPerfil;
	private String strTipoMantPerfil;
	private Perfil perfil;
	private String strMsgPerfil;
	private PerfilMsg msgPerfil;
	private List<List<Transaccion>> listaMenu;
	private List<String> listaSelectRadioMenu;
	private PermisoPerfil permiso;
	private Boolean bolUsuario;
	private Boolean bolPerfil;
	private Boolean bolPermiso;
	
	public UsuarioController() {
		usuarioComp = new UsuarioComp();
		usuarioComp.setUsuario(new Usuario());
		usuarioComp.setEmpresaUsuario(new EmpresaUsuario());
		usuarioComp.getEmpresaUsuario().setId(new EmpresaUsuarioId());
		strTipoMantUsuario = Constante.MANTENIMIENTO_NINGUNO;
		inicio(null);
	}
	
	public void inicio(ActionEvent event){
		PermisoPerfilId id = null;
		Integer MENU_USUARIO = 34;
		Integer MENU_PERFIL = 35;
		Integer MENU_PERMISO = 36;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario != null){
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				id.setIntIdTransaccion(MENU_USUARIO);
				id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
				PermisoFacadeLocal localPermiso = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				bolUsuario = (permiso == null)?true:false;
				id.setIntIdTransaccion(MENU_PERFIL);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				bolPerfil = (permiso == null)?true:false;
				id.setIntIdTransaccion(MENU_PERMISO);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				bolPermiso = (permiso == null)?true:false;
			}else{
				bolUsuario = false;
				bolPerfil = false;
				bolPermiso = false;
			}
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}
			
	}
	
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
	public int getCodPerfil() {
		return codPerfil;
	}
	public void setCodPerfil(int codPerfil) {
		this.codPerfil = codPerfil;
	}
	public UsuarioServiceImpl getUsuarioPerfilService() {
		return usuarioPerfilService;
	}
	public void setUsuarioPerfilService(UsuarioServiceImpl usuarioPerfilService) {
		this.usuarioPerfilService = usuarioPerfilService;
	}
	public EmpresaServiceImpl getEmpresaService() {
		return empresaService;
	}
	public void setEmpresaService(EmpresaServiceImpl empresaService) {
		this.empresaService = empresaService;
	}
	public FileUploadServiceImpl getFileUploadService() {
		return fileUploadService;
	}
	public void setFileUploadService(FileUploadServiceImpl fileUploadService) {
		this.fileUploadService = fileUploadService;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public List getBeanListMenuesPermisos() {
		return beanListMenuesPermisos;
	}
	public void setBeanListMenuesPermisos(List beanListMenuesPermisos) {
		this.beanListMenuesPermisos = beanListMenuesPermisos;
	}
	public List getBeanListPermisos() {
		return beanListPermisos;
	}
	public void setBeanListPermisos(List beanListPermisos) {
		this.beanListPermisos = beanListPermisos;
	}
	public AdminMenu getBeanAdminMenu() {
		return beanAdminMenu;
	}
	public void setBeanAdminMenu(AdminMenu beanAdminMenu) {
		this.beanAdminMenu = beanAdminMenu;
	}
	public PerfilUsuario getBeanPerfilUsuario() {
		return beanPerfilUsuario;
	}
	public void setBeanPerfilUsuario(PerfilUsuario beanPerfilUsuario) {
		this.beanPerfilUsuario = beanPerfilUsuario;
	}
	public SucursalUsuario getBeanSucursalUsuario() {
		return beanSucursalUsuario;
	}
	public void setBeanSucursalUsuario(SucursalUsuario beanSucursalUsuario) {
		this.beanSucursalUsuario = beanSucursalUsuario;
	}
	public SubSucursalUsuario getBeanSubSucursalUsuario() {
		return beanSubSucursalUsuario;
	}
	public void setBeanSubSucursalUsuario(SubSucursalUsuario beanSubSucursalUsuario) {
		this.beanSubSucursalUsuario = beanSubSucursalUsuario;
	}
	public Permiso getBeanPermiso() {
		return beanPermiso;
	}
	public void setBeanPermiso(Permiso beanPermiso) {
		this.beanPermiso = beanPermiso;
	}
	public Domicilio getBeanDomicilio() {
		return beanDomicilio;
	}
	public void setBeanDomicilio(Domicilio beanDomicilio) {
		this.beanDomicilio = beanDomicilio;
	}
	public Comunicacion getBeanComunicacion() {
		return beanComunicacion;
	}
	public void setBeanComunicacion(Comunicacion beanComunicacion) {
		this.beanComunicacion = beanComunicacion;
	}
	public String getTxtEmpresaUsuario() {
		return txtEmpresaUsuario;
	}
	public void setTxtEmpresaUsuario(String txtEmpresaUsuario) {
		this.txtEmpresaUsuario = txtEmpresaUsuario;
	}
	public Integer getIntCboEmpresa() {
		return intCboEmpresa;
	}
	public void setIntCboEmpresa(Integer intCboEmpresa) {
		this.intCboEmpresa = intCboEmpresa;
	}
	public Integer getIntCboPerfil() {
		return intCboPerfil;
	}
	public void setIntCboPerfil(Integer intCboPerfil) {
		this.intCboPerfil = intCboPerfil;
	}
	public Integer getCboTipoUsuario() {
		return cboTipoUsuario;
	}
	public void setCboTipoUsuario(Integer cboTipoUsuario) {
		this.cboTipoUsuario = cboTipoUsuario;
	}
	public Integer getCboEstadoUsuario() {
		return cboEstadoUsuario;
	}
	public void setCboEstadoUsuario(Integer cboEstadoUsuario) {
		this.cboEstadoUsuario = cboEstadoUsuario;
	}
	public String getTxtUsuarioPerfil() {
		return txtUsuarioPerfil;
	}
	public void setTxtUsuarioPerfil(String txtUsuarioPerfil) {
		this.txtUsuarioPerfil = txtUsuarioPerfil;
	}
	public String getTxtUsuario() {
		return txtUsuario;
	}
	public void setTxtUsuario(String txtUsuario) {
		this.txtUsuario = txtUsuario;
	}
	public int getCboTipoSucursal() {
		return cboTipoSucursal;
	}
	public void setCboTipoSucursal(int cboTipoSucursal) {
		this.cboTipoSucursal = cboTipoSucursal;
	}
	public Integer getIntCboEmpresaUsuario() {
		return intCboEmpresaUsuario;
	}
	public void setIntCboEmpresaUsuario(Integer intCboEmpresaUsuario) {
		this.intCboEmpresaUsuario = intCboEmpresaUsuario;
	}
	public Integer getIntCboSucursalUsuario() {
		return intCboSucursalUsuario;
	}
	public void setIntCboSucursalUsuario(Integer intCboSucursalUsuario) {
		this.intCboSucursalUsuario = intCboSucursalUsuario;
	}
	public Integer getIntCboPerfilUsuario() {
		return intCboPerfilUsuario;
	}
	public void setIntCboPerfilUsuario(Integer intCboPerfilUsuario) {
		this.intCboPerfilUsuario = intCboPerfilUsuario;
	}
	public String getStrCboSubSucursalUsuario() {
		return strCboSubSucursalUsuario;
	}
	public void setStrCboSubSucursalUsuario(String strCboSubSucursalUsuario) {
		this.strCboSubSucursalUsuario = strCboSubSucursalUsuario;
	}
	public String getHiddenIdUsuario() {
		return hiddenIdUsuario;
	}
	public void setHiddenIdUsuario(String hiddenIdUsuario) {
		this.hiddenIdUsuario = hiddenIdUsuario;
	}
	public String getHiddenStrEmpresa() {
		return hiddenStrEmpresa;
	}
	public void setHiddenStrEmpresa(String hiddenStrEmpresa) {
		this.hiddenStrEmpresa = hiddenStrEmpresa;
	}
	public String getHiddenStrPerfil() {
		return hiddenStrPerfil;
	}
	public void setHiddenStrPerfil(String hiddenStrPerfil) {
		this.hiddenStrPerfil = hiddenStrPerfil;
	}
	public String getHiddenStrSucursal() {
		return hiddenStrSucursal;
	}
	public void setHiddenStrSucursal(String hiddenStrSucursal) {
		this.hiddenStrSucursal = hiddenStrSucursal;
	}
	public String getHiddenStrSubSucursal() {
		return hiddenStrSubSucursal;
	}
	public void setHiddenStrSubSucursal(String hiddenStrSubSucursal) {
		this.hiddenStrSubSucursal = hiddenStrSubSucursal;
	}
	public Date getDaFechaIniPerfUsu() {
		return daFechaIniPerfUsu;
	}
	public void setDaFechaIniPerfUsu(Date daFechaIniPerfUsu) {
		this.daFechaIniPerfUsu = daFechaIniPerfUsu;
	}
	public Date getDaFechaFinPerfUsu() {
		return daFechaFinPerfUsu;
	}
	public void setDaFechaFinPerfUsu(Date daFechaFinPerfUsu) {
		this.daFechaFinPerfUsu = daFechaFinPerfUsu;
	}
	public Date getDaFechaIniSucUsu() {
		return daFechaIniSucUsu;
	}
	public void setDaFechaIniSucUsu(Date daFechaIniSucUsu) {
		this.daFechaIniSucUsu = daFechaIniSucUsu;
	}
	public Date getDaFechaFinSucUsu() {
		return daFechaFinSucUsu;
	}
	public void setDaFechaFinSucUsu(Date daFechaFinSucUsu) {
		this.daFechaFinSucUsu = daFechaFinSucUsu;
	}
	public Date getDaFechaIniSubSucUsu() {
		return daFechaIniSubSucUsu;
	}
	public void setDaFechaIniSubSucUsu(Date daFechaIniSubSucUsu) {
		this.daFechaIniSubSucUsu = daFechaIniSubSucUsu;
	}
	public Date getDaFechaFinSubSucUsu() {
		return daFechaFinSubSucUsu;
	}
	public void setDaFechaFinSubSucUsu(Date daFechaFinSubSucUsu) {
		this.daFechaFinSubSucUsu = daFechaFinSubSucUsu;
	}
	public String getHiddenIdSucursal() {
		return hiddenIdSucursal;
	}
	public void setHiddenIdSucursal(String hiddenIdSucursal) {
		this.hiddenIdSucursal = hiddenIdSucursal;
	}
	public String getHiddenIdSubSucursal() {
		return hiddenIdSubSucursal;
	}
	public void setHiddenIdSubSucursal(String hiddenIdSubSucursal) {
		this.hiddenIdSubSucursal = hiddenIdSubSucursal;
	}
	public String getMsgSucursalUsuario() {
		return msgSucursalUsuario;
	}
	public void setMsgSucursalUsuario(String msgSucursalUsuario) {
		this.msgSucursalUsuario = msgSucursalUsuario;
	}
	public String getRbPerfUsu() {
		return rbPerfUsu;
	}
	public void setRbPerfUsu(String rbPerfUsu) {
		this.rbPerfUsu = rbPerfUsu;
	}
	public Boolean getFecFinPerfUsuRendered() {
		return fecFinPerfUsuRendered;
	}
	public void setFecFinPerfUsuRendered(Boolean fecFinPerfUsuRendered) {
		this.fecFinPerfUsuRendered = fecFinPerfUsuRendered;
	}
	public String getRbSucUsu() {
		return rbSucUsu;
	}
	public void setRbSucUsu(String rbSucUsu) {
		this.rbSucUsu = rbSucUsu;
	}
	public Boolean getFecFinSucUsuRendered() {
		return fecFinSucUsuRendered;
	}
	public void setFecFinSucUsuRendered(Boolean fecFinSucUsuRendered) {
		this.fecFinSucUsuRendered = fecFinSucUsuRendered;
	}
	public String getRbSubSucUsu() {
		return rbSubSucUsu;
	}
	public void setRbSubSucUsu(String rbSubSucUsu) {
		this.rbSubSucUsu = rbSubSucUsu;
	}
	public Boolean getFecFinSubSucUsuRendered() {
		return fecFinSubSucUsuRendered;
	}
	public void setFecFinSubSucUsuRendered(Boolean fecFinSubSucUsuRendered) {
		this.fecFinSubSucUsuRendered = fecFinSubSucUsuRendered;
	}
	public Integer getCboEmpresaPermiso() {
		return cboEmpresaPermiso;
	}
	public void setCboEmpresaPermiso(Integer cboEmpresaPermiso) {
		this.cboEmpresaPermiso = cboEmpresaPermiso;
	}
	public String getTxtUsuarioPermiso() {
		return txtUsuarioPermiso;
	}
	public void setTxtUsuarioPermiso(String txtUsuarioPermiso) {
		this.txtUsuarioPermiso = txtUsuarioPermiso;
	}
	public Integer getCboTipoPerfilPermiso() {
		return cboTipoPerfilPermiso;
	}
	public void setCboTipoPerfilPermiso(Integer cboTipoPerfilPermiso) {
		this.cboTipoPerfilPermiso = cboTipoPerfilPermiso;
	}
	public int getCboEstadoPerfilPermiso() {
		return cboEstadoPerfilPermiso;
	}
	public void setCboEstadoPerfilPermiso(int cboEstadoPerfilPermiso) {
		this.cboEstadoPerfilPermiso = cboEstadoPerfilPermiso;
	}
	public Date getDaFechaIniPermiso() {
		return daFechaIniPermiso;
	}
	public void setDaFechaIniPermiso(Date daFechaIniPermiso) {
		this.daFechaIniPermiso = daFechaIniPermiso;
	}
	public Date getDaFechaFinPermiso() {
		return daFechaFinPermiso;
	}
	public void setDaFechaFinPermiso(Date daFechaFinPermiso) {
		this.daFechaFinPermiso = daFechaFinPermiso;
	}
	public String getMsgTxtEmpresaPermiso() {
		return msgTxtEmpresaPermiso;
	}
	public void setMsgTxtEmpresaPermiso(String msgTxtEmpresaPermiso) {
		this.msgTxtEmpresaPermiso = msgTxtEmpresaPermiso;
	}
	public String getMsgTxtUsuarioPermiso() {
		return msgTxtUsuarioPermiso;
	}
	public void setMsgTxtUsuarioPermiso(String msgTxtUsuarioPermiso) {
		this.msgTxtUsuarioPermiso = msgTxtUsuarioPermiso;
	}
	public String getMsgCboTipoPerfilPermiso() {
		return msgCboTipoPerfilPermiso;
	}
	public void setMsgCboTipoPerfilPermiso(String msgCboTipoPerfilPermiso) {
		this.msgCboTipoPerfilPermiso = msgCboTipoPerfilPermiso;
	}
	public String getMsgCboEstadoPermiso() {
		return msgCboEstadoPermiso;
	}
	public void setMsgCboEstadoPermiso(String msgCboEstadoPermiso) {
		this.msgCboEstadoPermiso = msgCboEstadoPermiso;
	}
	public String getMsgTxtRanFecPermiso() {
		return msgTxtRanFecPermiso;
	}
	public void setMsgTxtRanFecPermiso(String msgTxtRanFecPermiso) {
		this.msgTxtRanFecPermiso = msgTxtRanFecPermiso;
	}
	public String getStrCboEmpresaPermiso() {
		return strCboEmpresaPermiso;
	}
	public void setStrCboEmpresaPermiso(String strCboEmpresaPermiso) {
		this.strCboEmpresaPermiso = strCboEmpresaPermiso;
	}
	public String getStrCboPerfilPermiso() {
		return strCboPerfilPermiso;
	}
	public void setStrCboPerfilPermiso(String strCboPerfilPermiso) {
		this.strCboPerfilPermiso = strCboPerfilPermiso;
	}
	public String getStrCboMenuPermiso01() {
		return strCboMenuPermiso01;
	}
	public void setStrCboMenuPermiso01(String strCboMenuPermiso01) {
		this.strCboMenuPermiso01 = strCboMenuPermiso01;
	}
	public String getStrCboMenuPermiso02() {
		return strCboMenuPermiso02;
	}
	public void setStrCboMenuPermiso02(String strCboMenuPermiso02) {
		this.strCboMenuPermiso02 = strCboMenuPermiso02;
	}
	public String getStrCboMenuPermiso03() {
		return strCboMenuPermiso03;
	}
	public void setStrCboMenuPermiso03(String strCboMenuPermiso03) {
		this.strCboMenuPermiso03 = strCboMenuPermiso03;
	}
	public String getStrCboMenuPermiso04() {
		return strCboMenuPermiso04;
	}
	public void setStrCboMenuPermiso04(String strCboMenuPermiso04) {
		this.strCboMenuPermiso04 = strCboMenuPermiso04;
	}
	public Date getDaFechaPermisoIni() {
		return daFechaPermisoIni;
	}
	public void setDaFechaPermisoIni(Date daFechaPermisoIni) {
		this.daFechaPermisoIni = daFechaPermisoIni;
	}
	public Date getDaFechaPermisoFin() {
		return daFechaPermisoFin;
	}
	public void setDaFechaPermisoFin(Date daFechaPermisoFin) {
		this.daFechaPermisoFin = daFechaPermisoFin;
	}
	public List<PerfilUsuario> getListPerfilUsuario() {
		return listPerfilUsuario;
	}
	public void setListPerfilUsuario(List<PerfilUsuario> listPerfilUsuario) {
		this.listPerfilUsuario = listPerfilUsuario;
	}
	public String getMsgTxtRanFec() {
		return msgTxtRanFec;
	}
	public void setMsgTxtRanFec(String msgTxtRanFec) {
		this.msgTxtRanFec = msgTxtRanFec;
	}
	public String getMsgCboEstado() {
		return msgCboEstado;
	}
	public void setMsgCboEstado(String msgCboEstado) {
		this.msgCboEstado = msgCboEstado;
	}
	public String getMsgCboTipoPerfil() {
		return msgCboTipoPerfil;
	}
	public void setMsgCboTipoPerfil(String msgCboTipoPerfil) {
		this.msgCboTipoPerfil = msgCboTipoPerfil;
	}
	public String getMsgTxtEmpresa() {
		return msgTxtEmpresa;
	}
	public void setMsgTxtEmpresa(String msgTxtEmpresa) {
		this.msgTxtEmpresa = msgTxtEmpresa;
	}
	public String getMsgTxtPerfil() {
		return msgTxtPerfil;
	}
	public void setMsgTxtPerfil(String msgTxtPerfil) {
		this.msgTxtPerfil = msgTxtPerfil;
	}
	public String getMsgSinPerfiles() {
		return msgSinPerfiles;
	}
	public void setMsgSinPerfiles(String msgSinPerfiles) {
		this.msgSinPerfiles = msgSinPerfiles;
	}
	public String getHiddenIdEmpresa() {
		return hiddenIdEmpresa;
	}
	public void setHiddenIdEmpresa(String hiddenIdEmpresa) {
		this.hiddenIdEmpresa = hiddenIdEmpresa;
	}
	public String getHiddenIdPerfil() {
		return hiddenIdPerfil;
	}
	public void setHiddenIdPerfil(String hiddenIdPerfil) {
		this.hiddenIdPerfil = hiddenIdPerfil;
	}
	public String getStrCboMenu01() {
		return strCboMenu01;
	}
	public void setStrCboMenu01(String strCboMenu01) {
		this.strCboMenu01 = strCboMenu01;
	}
	public String getStrCboMenu02() {
		return strCboMenu02;
	}
	public void setStrCboMenu02(String strCboMenu02) {
		this.strCboMenu02 = strCboMenu02;
	}
	public String getStrCboMenu03() {
		return strCboMenu03;
	}
	public void setStrCboMenu03(String strCboMenu03) {
		this.strCboMenu03 = strCboMenu03;
	}
	public String getStrCboMenu04() {
		return strCboMenu04;
	}
	public void setStrCboMenu04(String strCboMenu04) {
		this.strCboMenu04 = strCboMenu04;
	}
	public Boolean getUsuarioRendered() {
		return usuarioRendered;
	}
	public void setUsuarioRendered(Boolean usuarioRendered) {
		this.usuarioRendered = usuarioRendered;
	}
	public Boolean getPermisoRendered() {
		return permisoRendered;
	}
	public void setPermisoRendered(Boolean permisoRendered) {
		this.permisoRendered = permisoRendered;
	}
	public Boolean getShowMsgError() {
		return showMsgError;
	}
	public void setShowMsgError(Boolean showMsgError) {
		this.showMsgError = showMsgError;
	}
	public String getStrCboUsuario() {
		return strCboUsuario;
	}
	public void setStrCboUsuario(String strCboUsuario) {
		this.strCboUsuario = strCboUsuario;
	}
	public ArrayList getArrayEmpresa() {
		return ArrayEmpresa;
	}
	public void setArrayEmpresa(ArrayList arrayEmpresa) {
		ArrayEmpresa = arrayEmpresa;
	}
	public ArrayList getArrayPerfilUsuario() {
		return ArrayPerfilUsuario;
	}
	public void setArrayPerfilUsuario(ArrayList arrayPerfilUsuario) {
		ArrayPerfilUsuario = arrayPerfilUsuario;
	}
	public ArrayList getArraySucursalUsuario() {
		return ArraySucursalUsuario;
	}
	public void setArraySucursalUsuario(ArrayList arraySucursalUsuario) {
		ArraySucursalUsuario = arraySucursalUsuario;
	}
	public ArrayList getArraySubSucursalUsuario() {
		return ArraySubSucursalUsuario;
	}
	public void setArraySubSucursalUsuario(ArrayList arraySubSucursalUsuario) {
		ArraySubSucursalUsuario = arraySubSucursalUsuario;
	}
	public String getStrNombreImagen() {
		return strNombreImagen;
	}
	public void setStrNombreImagen(String strNombreImagen) {
		this.strNombreImagen = strNombreImagen;
	}
	public Boolean getImgUsuario() {
		return imgUsuario;
	}
	public void setImgUsuario(Boolean imgUsuario) {
		this.imgUsuario = imgUsuario;
	}
	public Boolean getChkDireccion() {
		return chkDireccion;
	}
	public void setChkDireccion(Boolean chkDireccion) {
		this.chkDireccion = chkDireccion;
	}
	public Boolean getChkComunicacion() {
		return chkComunicacion;
	}
	public void setChkComunicacion(Boolean chkComunicacion) {
		this.chkComunicacion = chkComunicacion;
	}
	public Boolean getChkPerfilUsuario() {
		return chkPerfilUsuario;
	}
	public void setChkPerfilUsuario(Boolean chkPerfilUsuario) {
		this.chkPerfilUsuario = chkPerfilUsuario;
	}
	public Boolean getChkSubSucursal() {
		return chkSubSucursal;
	}
	public void setChkSubSucursal(Boolean chkSubSucursal) {
		this.chkSubSucursal = chkSubSucursal;
	}
	public ArrayList<SelectItem> getCboUsuarioSoli() {
		return cboUsuarioSoli;
	}
	public void setCboUsuarioSoli(ArrayList<SelectItem> cboUsuarioSoli) {
		this.cboUsuarioSoli = cboUsuarioSoli;
	}
	public ArrayList<SelectItem> getCboUsuario() {
		return cboUsuario;
	}
	public void setCboUsuario(ArrayList<SelectItem> cboUsuario) {
		this.cboUsuario = cboUsuario;
	}
	public ArrayList<SelectItem> getCboEmpresas() {
		return cboEmpresas;
	}
	public void setCboEmpresas(ArrayList<SelectItem> cboEmpresas) {
		this.cboEmpresas = cboEmpresas;
	}
	public ArrayList<SelectItem> getCboPerfilUsuario() {
		return cboPerfilUsuario;
	}
	public void setCboPerfilUsuario(ArrayList<SelectItem> cboPerfilUsuario) {
		this.cboPerfilUsuario = cboPerfilUsuario;
	}
	//-------------------------------------------------------------------------------------------------------------------------------------------
	//Métodos de UsuarioController para Mantenimientos
	//-------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void init(){
		
	}
	
	public void enableDisable3(ActionEvent event) throws DaoException{
		log.info("----------------Debugging EmpresaController.enableDisable-------------------");
		Perfil per = new Perfil();
	    //per = (Perfil) getBeanPerfil();	    
//	    boolean strRanFecha = per.getChkRanFec();
//		setFormPerfilEnabled2(strRanFecha==false);
	}
	
	public void enableDisable2(ActionEvent event) throws DaoException{
		log.info("----------------Debugging EmpresaController.enableDisable-------------------");
		Permiso perm = new Permiso();
	    perm = (Permiso) getBeanPermiso();	    
	    boolean strRanFecha = perm.getChkRanFecha();
//		setFormPermisoEnabled2(strRanFecha==false);
	}
	
	public void habilitarGrabarPermiso(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging UsuarioPerfilController.habilitarGrabarPermiso-----------------------------");
	    //setFormPermisoEnabled(false);
//	    setFormPermisoEnabled2(true);
	    setPermisoRendered(true);
	    limpiarFormPermiso();
	    if(beanListPermisos != null){
	    	beanListPermisos.clear();
	    }
	    //listarMenues1(event);
	}
	
	public void habilitarActualizarUsuario(ActionEvent event){	
		log.info("-----------------------Debugging EmpresaController.habilitarActualizarUsuario-----------------------------");
	    //setFormUsuarioEnabled(false);
	    setImgUsuario(true);
	    setUsuarioRendered(true);
	    //domicilioController.setStrInsertUpdate("U");
	    //comunicacionController.setStrInsertUpdate("U");
	}
	
	public void limpiarFormPerfil(){
//		Perfil per = new Perfil();
//		per.setIntIdEmpresa(0);
//		per.setIntIdPerfil(0);
//		per.setIntIdTipoPerfil(0);
//		per.setIntIdEstado(0);
//		per.setStrEmpresaPerfil("");
//		per.setStrDescripcion("");
//		per.setDaFecIni("");
//		per.setDaFecFin("");
//		setIntCboEmpresaPerfil(0);
//		setDaFechaIni(null);
//		setDaFechaFin(null);
		setHiddenIdEmpresa("");
		setHiddenIdPerfil("");
		setMsgTxtEmpresa("");
		setMsgCboTipoPerfil("");
		setMsgCboEstado("");
		setMsgTxtPerfil("");
		setMsgTxtRanFec("");
//		setBeanPerfil(per);
	}
	
	public void limpiarFormUsuario(){
//		Usuario usu = new Usuario();
//		usu.setPersona(new Persona());
//		usu.getPersona().setNatural(new Natural());
//		usu.getPersona().setJuridica(new Juridica());
//		usu.setIntIdPersona(0);
//		usu.setIntIdTipoUsuario(0);
//		usu.setIntIdTipoPersona(0);
//		usu.setIntIdEstado(0);
//		usu.setIntIdTipoDoc(0);
//		usu.setStrNroDoc("");
//		usu.setStrApepat("");
//		usu.setStrApemat("");
//		usu.setStrNombres("");
//		usu.setDaFecRegistro("");
		setIntCboEmpresaUsuario(0);
		setIntCboPerfilUsuario(0);
		setHiddenIdUsuario("");
		setHiddenIdPerfil("");
		setHiddenIdSucursal("");
		setHiddenIdSubSucursal("");
//		usu.setStrNombreUsuario("");
//		usu.setStrClaveUsuario("");
//		usu.setChkValidEsp(false);
//		usu.setChkValidHoraIng(false);
//		usu.setChkValidCambiarClave(false);
//		usu.setChkDireccion(false);
//		usu.setChkComunicacion(false);
//		usu.setRbVigClave("");
		setRbPerfUsu("");
		setRbSucUsu("");
		setRbSubSucUsu("");
		setFecFinPerfUsuRendered(true);
		setFecFinSucUsuRendered(true);
		setFecFinSubSucUsuRendered(true);
		setMsgSucursalUsuario("");
		setShowMsgError(false);
		setChkDireccion(false);
		setChkComunicacion(false);
		setChkPerfilUsuario(false);
		setChkSubSucursal(false);
	}
	
	public void limpiarFormPermiso(){
		Permiso perm = new Permiso();
		setBeanPermiso(perm);
	}
	
	public void cancelarGrabarPermiso(ActionEvent event){
		log.info("-----------------------Debugging UsuarioController.cancelarGrabarPermiso-----------------------------");
//		setFormPermisoEnabled(true);
	    setPermisoRendered(false);
	    limpiarFormPermiso();
	    if(beanListMenuesPermisos!=null) beanListMenuesPermisos.clear();
	}
	
	public void cancelarGrabarPerfil(ActionEvent event){
		log.info("-----------------------Debugging UsuarioController.cancelarGrabarPerfil-----------------------------");
//		setFormPerfilEnabled(true);
//	    setPerfilRendered(false);
	    limpiarFormPerfil();
	    //if(beanListMenues!=null) beanListMenues.clear();
	}
	
	public void listarPerfiles(ActionEvent event){
	    setService(usuarioPerfilService);
		Integer cboPerfilPerf = 0;
//		if(getIntCboPerfilPerf().equals("selectNone")){
//			cboPerfilPerf = 0;
//		}else{
//			cboPerfilPerf = getIntCboPerfilPerf();
//		}
		HashMap prmtBusqPerfil = new HashMap();
//		prmtBusqPerfil.put("pIntIdEmpresa", getIntCboEmpresaPerfil());
		//prmtBusqPerfil.put("pIntIdPerfil", Integer.parseInt(getStrCboPerfilPerf()=="selectNone"?"0":getStrCboPerfilPerf()));
		prmtBusqPerfil.put("pIntIdPerfil", cboPerfilPerf);
		prmtBusqPerfil.put("pTxtEmpresaPerfil", "");
		prmtBusqPerfil.put("pTxtPerfil", "");
//		prmtBusqPerfil.put("pCboTipoPerfil", getCboTipoPerfil());
//		prmtBusqPerfil.put("pCboEstadoPerfil", getCboEstadoPerfil());
//		log.info("pTxtFecIni = "+getDaFecIni());
//		String txtFecIni = (getDaFecIni()!=null)?sdf.format(getDaFecIni()):null;
//		prmtBusqPerfil.put("pTxtFecIni", txtFecIni);
//		log.info("pTxtFecFin = "+getDaFecIni());
//		String txtFecFin = (getDaFecFin()!=null)?sdf.format(getDaFecFin()):null;
//		prmtBusqPerfil.put("pTxtFecFin", txtFecFin);
		
	    ArrayList arrayPerfiles = new ArrayList();
	    ArrayList listaPerfiles = new ArrayList();
	    try {
	    	arrayPerfiles = getService().listarPerfiles(prmtBusqPerfil);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarPerfiles() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Listado de perfiles ha sido satisfactorio");
		
//		for(int i=0; i<arrayPerfiles.size(); i++){
//			Perfil per = (Perfil) arrayPerfiles.get(i);
//			per.setStrRanFechas(per.getDaFecIni() + " - " + (per.getDaFecFin()==null||per.getDaFecFin().equals("")?"":per.getDaFecFin()));
//			listaPerfiles.add(per);
//		}
		
//		setBeanListPerfiles(listaPerfiles);
		setBeanList(listaPerfiles);
		if(listaPerfiles.size()==0){
			setMsgSinPerfiles("No se encontraron coincidencias para la búsqueda.");
		}
	}
	
	/*public void eliminarPerfil(ActionEvent event) throws DaoException{
    	log.info("-----------------------Debugging UsuarioController.eliminarPerfil-----------------------------");
    	log.info("getHiddenIdEmpresa() = "+getHiddenIdEmpresa());
    	log.info("getHiddenIdPerfil() = "+getHiddenIdPerfil());
		String idEmpresa = getHiddenIdEmpresa();
		String idPerfil = getHiddenIdPerfil();
		HashMap prmtPerfil = new HashMap();
		prmtPerfil.put("prmIdEmpresa", Integer.parseInt(idEmpresa));
		prmtPerfil.put("prmIdPerfil", Integer.parseInt(idPerfil));
		getService().eliminarPerfil(prmtPerfil);
		log.info("Se ha eliminado el perfil: "+idPerfil);
		listarPerfiles(event);
    }*/
	
	public void eliminarPerfilDet(ActionEvent event) throws DaoException{
		Perfil per = new Perfil();
		log.info("-----------------------Debugging UsuarioController.eliminarPerfilDet-----------------------------");
		log.info("getHiddenIdEmpresa() = "+getHiddenIdEmpresa());
    	log.info("getHiddenIdPerfil() = "+getHiddenIdPerfil());
    	String idEmpresa = (getHiddenIdEmpresa().equals("")?"0":getHiddenIdEmpresa());
    	String idPerfil = (getHiddenIdPerfil().equals("")?"0":getHiddenIdPerfil());
		HashMap prmtPerfil = new HashMap();
		prmtPerfil.put("pIntIdEmpresa", Integer.parseInt(idEmpresa));
		prmtPerfil.put("pIntIdPerfil", Integer.parseInt(idPerfil));
		getService().eliminarPerfilDet(prmtPerfil);
    }
	
	public void listarMenues(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging UsuarioPerfilController.listarMenues-----------------------------");
	    setService(usuarioPerfilService);
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de seg_m_transacciones,seg_v_menu: ");
		log.info("-----------------------------------------------------------------------");
		String strPerfilId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPerfModalPanel:hiddenIdPerfil");
		String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPerfModalPanel:hiddenIdEmpresa");
		HashMap prmtBusq = new HashMap();
		HashMap prmtBusq1 = new HashMap();
		String idPerfil = (strPerfilId==null||strPerfilId.equals("")?"0":strPerfilId);
		String idEmpresa = (strEmpresaId==null||strEmpresaId.equals("")?"0":strEmpresaId);
		
		log.info("idPerfil: "+idPerfil);
		prmtBusq.put("pIntIdTipo",  1);
		prmtBusq.put("pIntIdPerfil", Integer.parseInt(idPerfil));
		prmtBusq.put("pStrIdPadre",  "");
		prmtBusq.put("pIntIdEmpresa", Integer.parseInt(idEmpresa));
		
	    ArrayList arrayAdminMenu = new ArrayList();
	    ArrayList arrayAdminMenu1 = new ArrayList();
	    ArrayList listaAdminMenu = new ArrayList();
	    ArrayList arrayMenu1 = new ArrayList();
	    ArrayList arrayMenu2 = new ArrayList();
	    ArrayList arrayMenu3 = new ArrayList();
	    ArrayList arrayMenu4 = new ArrayList();
	    ArrayList arrayMenu5 = new ArrayList();
	    try {
	    	arrayAdminMenu = getService().listarPerfilDetalle(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarPerfilDetalle() " + e.getMessage());
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
				log.info("menu.getStrIdTransaccionPadre() = "+menu.getStrIdTransaccionPadre());
			}
			log.info("TRAN_NIVEL_N = "+hash.get("TRAN_NIVEL_N"));
			int intMenuOrden = 0;
			if(hash.get("TRAN_NIVEL_N")!=null){
				intMenuOrden = Integer.parseInt("" + hash.get("TRAN_NIVEL_N"));
				menu.setIntMenuOrden(intMenuOrden);
				log.info("intMenuOrden = "+intMenuOrden);
			}
			log.info("TRAN_NOMBRE_V = "+hash.get("TRAN_NOMBRE_V"));
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			if(intMenuOrden==1){
				menu.setStrNombreM1(strNombre);
				arrayMenu1.add(menu);
			}else if(intMenuOrden==2){
				menu.setStrNombreM2(strNombre);
				arrayMenu2.add(menu);
			}else if(intMenuOrden==3){
				menu.setStrNombreM3(strNombre);
				arrayMenu3.add(menu);
			}else if(intMenuOrden==4){
				menu.setStrNombreM4(strNombre);
				arrayMenu4.add(menu);
			}
			log.info("TRAN_IDESTADO_N = "+hash.get("TRAN_IDESTADO_N"));
			int intIdEstado = Integer.parseInt("" + hash.get("TRAN_IDESTADO_N"));
			Boolean chkEstado = (intIdEstado == 1 ? true : false);  
			menu.setChkPerfil(chkEstado);
		}
		
		log.info("arrayMenu1.size() = "+arrayMenu1.size());
		log.info("arrayMenu2.size() = "+arrayMenu2.size());
		log.info("arrayMenu3.size() = "+arrayMenu3.size());
		log.info("arrayMenu4.size() = "+arrayMenu4.size());
		
		prmtBusq1.put("pIntIdTipo",  0);
		prmtBusq1.put("pIntIdPerfil", Integer.parseInt(idPerfil));
		prmtBusq1.put("pStrIdPadre",  "");
		prmtBusq1.put("pIntIdEmpresa", Integer.parseInt(idEmpresa));
		arrayAdminMenu1 = getService().listarPerfilDetalle(prmtBusq1);
		for(int i=0; i<arrayAdminMenu1.size(); i++){
			HashMap hash = (HashMap) arrayAdminMenu1.get(i);
			AdminMenu menu1 = new AdminMenu();
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			menu1.setStrNombreM1(strNombre);
			arrayMenu5.add(menu1);
		}
		
		for(int i=0; i<arrayMenu1.size(); i++){
			AdminMenu m1 = new AdminMenu();
			m1=(AdminMenu)arrayMenu1.get(i);
			log.info("m1.getStrNombreM1() = "+m1.getStrNombreM1());
			for(int m=0; m<arrayMenu5.size(); m++){
				AdminMenu n1 = new AdminMenu();
				n1=(AdminMenu)arrayMenu5.get(m);
				if(n1.getStrNombreM1().equals(m1.getStrNombreM1())){
					m1.setChkPerfil(true);
				}
			}
			listaAdminMenu.add(m1);
			for(int j=0; j<arrayMenu2.size(); j++){
				AdminMenu m2 = new AdminMenu();
				m2=(AdminMenu)arrayMenu2.get(j);				
				if(m2.getStrIdTransaccionPadre().equals(m1.getStrIdTransaccion())){
					log.info("m2.getStrNombreM2() = "+m2.getStrNombreM2());
					for(int n=0; n<arrayMenu5.size(); n++){
						AdminMenu n2 = new AdminMenu();
						n2=(AdminMenu)arrayMenu5.get(n);
						if(n2.getStrNombreM1().equals(m2.getStrNombreM2())){
							m2.setChkPerfil(true);
						}
					}
					listaAdminMenu.add(m2);
					for(int k=0; k<arrayMenu3.size(); k++){
						AdminMenu m3 = new AdminMenu();
						m3=(AdminMenu)arrayMenu3.get(k);
						if(m3.getStrIdTransaccionPadre().equals(m2.getStrIdTransaccion())){
							log.info("m3.getStrNombreM3() = "+m3.getStrNombreM3());
							for(int p=0; p<arrayMenu5.size(); p++){
								AdminMenu n3 = new AdminMenu();
								n3=(AdminMenu)arrayMenu5.get(p);
								if(n3.getStrNombreM1().equals(m3.getStrNombreM3())){
									m3.setChkPerfil(true);
								}
							}
							listaAdminMenu.add(m3);
							for(int l=0; l<arrayMenu4.size(); l++){
								AdminMenu m4 = new AdminMenu();
								m4=(AdminMenu)arrayMenu4.get(l);
								if(m4.getStrIdTransaccionPadre().equals(m3.getStrIdTransaccion())){
									log.info("m4.getStrNombreM4() = "+m4.getStrNombreM4());
									for(int q=0; q<arrayMenu5.size(); q++){
										AdminMenu n4 = new AdminMenu();
										n4=(AdminMenu)arrayMenu5.get(q);
										if(n4.getStrNombreM1().equals(m4.getStrNombreM4())){
											m4.setChkPerfil(true);
										}
									}
									listaAdminMenu.add(m4);
								}
							}
						}
					}
				}
			}
		}
		
//		setBeanListMenues(listaAdminMenu);
	}
	
	public void listarMenues1(ActionEvent event) throws DaoException {
		log.info("-----------------------Debugging UsuarioPerfilController.listarMenues-----------------------------");
	    setService(usuarioPerfilService);
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de seg_m_transacciones,seg_v_menu: ");
		log.info("-----------------------------------------------------------------------");
		String strPerfilId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPerfModalPanel:hiddenIdPerfil");
		String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPerfModalPanel:hiddenIdEmpresa");
		HashMap prmtBusq = new HashMap();
		HashMap prmtBusq1 = new HashMap();
		String idPerfil = (strPerfilId==null||strPerfilId.equals("")?"0":strPerfilId);
		String idEmpresa = (strEmpresaId==null||strEmpresaId.equals("")?"0":strEmpresaId);
		
		log.info("idPerfil: "+idPerfil);
		prmtBusq.put("pIntIdTipo",  1);
		prmtBusq.put("pIntIdPerfil", Integer.parseInt(idPerfil));
		prmtBusq.put("pStrIdPadre",  "");
		prmtBusq.put("pIntIdEmpresa", Integer.parseInt(idEmpresa));
		
	    ArrayList arrayAdminMenu = new ArrayList();
	    ArrayList arrayAdminMenu1 = new ArrayList();
	    ArrayList listaAdminMenu = new ArrayList();
	    ArrayList arrayMenu1 = new ArrayList();
	    ArrayList arrayMenu2 = new ArrayList();
	    ArrayList arrayMenu3 = new ArrayList();
	    ArrayList arrayMenu4 = new ArrayList();
	    ArrayList arrayMenu5 = new ArrayList();
	    try {
	    	arrayAdminMenu = getService().listarPerfilDetalle(prmtBusq);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarPerfilDetalle() " + e.getMessage());
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
				log.info("menu.getStrIdTransaccionPadre() = "+menu.getStrIdTransaccionPadre());
			}
			log.info("TRAN_NIVEL_N = "+hash.get("TRAN_NIVEL_N"));
			int intMenuOrden = 0;
			if(hash.get("TRAN_NIVEL_N")!=null){
				intMenuOrden = Integer.parseInt("" + hash.get("TRAN_NIVEL_N"));
				menu.setIntMenuOrden(intMenuOrden);
				log.info("intMenuOrden = "+intMenuOrden);
			}
			log.info("TRAN_NOMBRE_V = "+hash.get("TRAN_NOMBRE_V"));
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			if(intMenuOrden==1){
				menu.setStrNombreM1(strNombre);
				arrayMenu1.add(menu);
			}else if(intMenuOrden==2){
				menu.setStrNombreM2(strNombre);
				arrayMenu2.add(menu);
			}else if(intMenuOrden==3){
				menu.setStrNombreM3(strNombre);
				arrayMenu3.add(menu);
			}else if(intMenuOrden==4){
				menu.setStrNombreM4(strNombre);
				arrayMenu4.add(menu);
			}
			log.info("TRAN_IDESTADO_N = "+hash.get("TRAN_IDESTADO_N"));
			int intIdEstado = Integer.parseInt("" + hash.get("TRAN_IDESTADO_N"));
			Boolean chkEstado = (intIdEstado == 1 ? true : false);  
			menu.setChkPerfil(chkEstado);
		}
		
		log.info("arrayMenu1.size() = "+arrayMenu1.size());
		log.info("arrayMenu2.size() = "+arrayMenu2.size());
		log.info("arrayMenu3.size() = "+arrayMenu3.size());
		log.info("arrayMenu4.size() = "+arrayMenu4.size());
		
		prmtBusq1.put("pIntIdTipo",  0);
		prmtBusq1.put("pIntIdPerfil", Integer.parseInt(idPerfil));
		prmtBusq1.put("pStrIdPadre",  "");
		prmtBusq1.put("pIntIdEmpresa", Integer.parseInt(idEmpresa));
		arrayAdminMenu1 = getService().listarPerfilDetalle(prmtBusq1);
		for(int i=0; i<arrayAdminMenu1.size(); i++){
			HashMap hash = (HashMap) arrayAdminMenu1.get(i);
			AdminMenu menu1 = new AdminMenu();
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			menu1.setStrNombreM1(strNombre);
			arrayMenu5.add(menu1);
		}
		
		for(int i=0; i<arrayMenu1.size(); i++){
			AdminMenu m1 = new AdminMenu();
			m1=(AdminMenu)arrayMenu1.get(i);
			log.info("m1.getStrNombreM1() = "+m1.getStrNombreM1());
			for(int m=0; m<arrayMenu5.size(); m++){
				AdminMenu n1 = new AdminMenu();
				n1=(AdminMenu)arrayMenu5.get(m);
				if(n1.getStrNombreM1().equals(m1.getStrNombreM1())){
					m1.setChkPerfil(true);
				}
			}
			listaAdminMenu.add(m1);
			for(int j=0; j<arrayMenu2.size(); j++){
				AdminMenu m2 = new AdminMenu();
				m2=(AdminMenu)arrayMenu2.get(j);				
				if(m2.getStrIdTransaccionPadre().equals(m1.getStrIdTransaccion())){
					log.info("m2.getStrNombreM2() = "+m2.getStrNombreM2());
					for(int n=0; n<arrayMenu5.size(); n++){
						AdminMenu n2 = new AdminMenu();
						n2=(AdminMenu)arrayMenu5.get(n);
						if(n2.getStrNombreM1().equals(m2.getStrNombreM2())){
							m2.setChkPerfil(true);
						}
					}
					listaAdminMenu.add(m2);
					for(int k=0; k<arrayMenu3.size(); k++){
						AdminMenu m3 = new AdminMenu();
						m3=(AdminMenu)arrayMenu3.get(k);
						if(m3.getStrIdTransaccionPadre().equals(m2.getStrIdTransaccion())){
							log.info("m3.getStrNombreM3() = "+m3.getStrNombreM3());
							for(int p=0; p<arrayMenu5.size(); p++){
								AdminMenu n3 = new AdminMenu();
								n3=(AdminMenu)arrayMenu5.get(p);
								if(n3.getStrNombreM1().equals(m3.getStrNombreM3())){
									m3.setChkPerfil(true);
								}
							}
							listaAdminMenu.add(m3);
							for(int l=0; l<arrayMenu4.size(); l++){
								AdminMenu m4 = new AdminMenu();
								m4=(AdminMenu)arrayMenu4.get(l);
								if(m4.getStrIdTransaccionPadre().equals(m3.getStrIdTransaccion())){
									log.info("m4.getStrNombreM4() = "+m4.getStrNombreM4());
									for(int q=0; q<arrayMenu5.size(); q++){
										AdminMenu n4 = new AdminMenu();
										n4=(AdminMenu)arrayMenu5.get(q);
										if(n4.getStrNombreM1().equals(m4.getStrNombreM4())){
											m4.setChkPerfil(true);
										}
									}
									listaAdminMenu.add(m4);
								}
							}
						}
					}
				}
			}
		}
		
		setBeanListMenuesPermisos(listaAdminMenu);
	}
	
	public void agregaMenu01(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging UsuarioPerfilController.Menu01-----------------------------");
	    setService(usuarioPerfilService);
	    log.info("Se ha seteado el Service");
	    log.info("Seteando los parametros de busqueda de seg_m_transacciones,seg_v_menu: ");
		log.info("-----------------------------------------------------------------------");
		HashMap prmtBusq = new HashMap();
		log.info("event.getComponent(): "+ event.getComponent());
		UIComponent uiComponent = event.getComponent();
		String btnCbo = uiComponent.getId();
		String cboVal="";
		if(btnCbo.equals("btnAgregaMenu1")){
			cboVal = getStrCboMenu01();
		}else if(btnCbo.equals("btnAgregaMenu2")){
			cboVal = getStrCboMenu02();
		}else if(btnCbo.equals("btnAgregaMenu3")){
			cboVal = getStrCboMenu03();
		}else{
			cboVal = getStrCboMenu04();
		}
		log.info("Menu1: "+ getStrCboMenu01());
		log.info("cboVal: "+ cboVal);
		
//		List arrayExiste = getBeanListMenues();
		
		ArrayList arr2 = new ArrayList();
		String codigo ="";
		for(int i = cboVal.length()-1; i>=0; i--){
			char c = cboVal.charAt(i);
			if(!(c+"").equals("0")){
				cboVal = cboVal.substring(0,i+1);
				break;
			}
		}
		log.info("cboVal: "+cboVal);
//		for(int i = 0; i< arrayExiste.size(); i++){
//			AdminMenu m1 = new AdminMenu();
//			m1=(AdminMenu)arrayExiste.get(i);
//			log.info("m1.getStrIdTransaccion(): "+m1.getStrIdTransaccion());
//			if((m1.getStrIdTransaccion()).contains(cboVal) ){
//				m1.setChkPerfil(true);
//			}
//			log.info("codigo: "+codigo);
//			arr2.add(m1);
//		}
//		setBeanListMenues(arr2);
	}
	
	public void getListaPerfilDeEmpresa(ActionEvent event) {
		LoginFacadeLocal facade = null;
		Integer intIdEmpresa = null;
		EmpresaUsuario lEmpresaUsuario = null;
		try{
			intIdEmpresa = usuarioComp.getEmpresaUsuario().getId().getIntPersEmpresaPk();
			if(intIdEmpresa!=null && intIdEmpresa != 0){
		    	facade = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
		    	listaPerfil = facade.getListaPerfilPorIdEmpresa(intIdEmpresa);
			}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public void buscarUsuario(ActionEvent event) throws ParseException, BusinessException, EJBFactoryException{
		LoginFacadeLocal facade = null;
		Integer cboEmpresa = (getIntCboEmpresa()==null || getIntCboEmpresa().equals("selectNone")?null:getIntCboEmpresa());
		if(usuarioComp.getEmpresaUsuario().getId().getIntPersEmpresaPk()==0)
			usuarioComp.getEmpresaUsuario().getId().setIntPersEmpresaPk(null);
		if(usuarioComp.getUsuario().getIntTipoUsuario()==0)
			usuarioComp.getUsuario().setIntTipoUsuario(null);
		if(usuarioComp.getUsuario().getIntIdEstado()==0)
			usuarioComp.getUsuario().setIntIdEstado(null);
		if(usuarioComp.getIntIdPerfil()==0)
			usuarioComp.setIntIdPerfil(null);
		if(usuarioComp.getUsuario().getStrUsuario().trim().equals(""))
			usuarioComp.getUsuario().setStrUsuario(null);
		if(usuarioComp.getIntIdTipoSucursal()==0)
			usuarioComp.setIntIdTipoSucursal(null);
		
	    try{
	    	facade = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
	    	listaUsuarioComp = facade.getListaUsuarioCompDeBusqueda(usuarioComp);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public void seleccionarUsuarioDeGrilla(ActionEvent event) {
		int indexUsuario = 0;
		Usuario lUsuario = null; 
		if(strIndexUsuario!=null && !strIndexUsuario.trim().equals("")){
			indexUsuario = Integer.parseInt(strIndexUsuario);
			lUsuario = listaUsuarioComp.get(indexUsuario).getUsuario();
			strEstadoUsuario = String.valueOf(lUsuario.getIntIdEstado());
		}
	}
	
	public void agregarEmpresaUsuario(ActionEvent event) throws DaoException {
	    EmpresaUsuario lEmpresaUsuario= null; 
	    if(usuario.getListaEmpresaUsuario()==null && listaJuridicaEmpresa != null){
	    	usuario.setListaEmpresaUsuario(new ArrayList<EmpresaUsuario>());
	    }
	    if(usuario.getListaEmpresaUsuario()!=null){
		    lEmpresaUsuario = new EmpresaUsuario();
		    lEmpresaUsuario.setId(new EmpresaUsuarioId());
		    usuario.getListaEmpresaUsuario().add(lEmpresaUsuario);
	    }else{
	    	setMessageError("Debe contar con Empresas Registradas");
	    }
	}
	
	public void onchangeDeEmpresa(ActionEvent event) {
		LoginFacadeLocal facadeLogin = null;
		EmpresaFacadeLocal facadeEmpresa = null;
		EmpresaUsuario lEmpresaUsuario = null;
		EmpresaUsuario lEmpresaUsuarioLoop = null;
		EmpresaUsuario lEmpresaUsuarioOld = null;
		EmpresaUsuarioId lEmpresaUsuarioIdOld= null;
		Integer intIdEmpresaSelected = null;
		boolean esReplicado = false;
		int index = 0;
		try{
			index = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
			lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(index);
			intIdEmpresaSelected = lEmpresaUsuario.getId().getIntPersEmpresaPk(); 
			if(intIdEmpresaSelected!=null){
				for(int i=0;i<usuario.getListaEmpresaUsuario().size();i++){
		    		lEmpresaUsuarioLoop = usuario.getListaEmpresaUsuario().get(i);
		    		if(index != i &&
		    		   lEmpresaUsuarioLoop.getId()!=null && 
		    		   lEmpresaUsuarioLoop.getId().getIntPersEmpresaPk().compareTo(intIdEmpresaSelected) == 0){
		    			esReplicado = true;
		    			break;
		    		}
		    	}
				if(esReplicado){
					setMessageError("Debe elegir una Empresa Diferente");
					if(esPresenteEmpresaOld(index)){
						lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(index);
					}
					lEmpresaUsuario.getId().setIntPersEmpresaPk(new Integer(0));
				}else{
					esPresenteEmpresaOld(index);
					facadeLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
			    	facadeEmpresa = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
					if(intIdEmpresaSelected.compareTo(new Integer(0)) != 0){
				    	lEmpresaUsuario.setListaPerfil(facadeLogin.getListaPerfilPorIdEmpresa(intIdEmpresaSelected));
				    	lEmpresaUsuario.setListaSucursal(facadeEmpresa.getListaSucursalPorPkEmpresa(intIdEmpresaSelected));
					}else{
						lEmpresaUsuario.setListaPerfil(null);
						lEmpresaUsuario.setListaSucursal(null);
					}
					if(lEmpresaUsuario.getListaUsuarioPerfil()!=null)
						lEmpresaUsuario.getListaUsuarioPerfil().clear();
					if(lEmpresaUsuario.getListaUsuarioSucursal()!=null)
						lEmpresaUsuario.getListaUsuarioSucursal().clear();
					if(lEmpresaUsuario.getListaUsuarioSubSucursal()!=null)
						lEmpresaUsuario.getListaUsuarioSubSucursal().clear();
				}
			}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	private boolean esPresenteEmpresaOld(int index)throws BusinessException{
		LoginFacadeLocal facadeLogin = null;
		EmpresaUsuario lEmpresaUsuario = null;
		EmpresaUsuario lEmpresaUsuarioOld = null;
		EmpresaUsuarioId lEmpresaUsuarioIdOld= null;
		String pIntPersEmpresaPk = null;
		String pIntPersPersonaPk = null;
		boolean esPresenteEmpresaOld = false;
		
		try{
			pIntPersEmpresaPk = getRequestParameter("pIntPersEmpresaPk");
			pIntPersPersonaPk = getRequestParameter("pIntPersPersonaPk");
			facadeLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
			if(pIntPersPersonaPk!=null && !pIntPersPersonaPk.trim().equals("")){
				lEmpresaUsuarioIdOld = new EmpresaUsuarioId();
				lEmpresaUsuarioIdOld.setIntPersEmpresaPk(new Integer(pIntPersEmpresaPk));
				lEmpresaUsuarioIdOld.setIntPersPersonaPk(new Integer(pIntPersPersonaPk));
				lEmpresaUsuarioOld = facadeLogin.getEmpresaUsuarioPorPk(lEmpresaUsuarioIdOld);
				if(lEmpresaUsuarioOld!=null){
					if(listaEmpresaUsuarioEliminado==null)
					   listaEmpresaUsuarioEliminado = new ArrayList<EmpresaUsuario>();
					lEmpresaUsuarioOld.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					listaEmpresaUsuarioEliminado.add(lEmpresaUsuarioOld);
					
					lEmpresaUsuario = new EmpresaUsuario();
				    lEmpresaUsuario.setId(new EmpresaUsuarioId());
					usuario.getListaEmpresaUsuario().set(index,lEmpresaUsuario);
					esPresenteEmpresaOld = true;
				}
			}
		}catch(Exception e){
	    	throw new BusinessException(e);
	    }
		return esPresenteEmpresaOld;
	}
	
	public void checkControlHoraIngreso(ActionEvent event){
		EmpresaUsuario lEmpresaUsuario = null;
		int index = 0;
		index = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
		lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(index);
		if(lEmpresaUsuario.getBlnControlHoraIngreso()){
			lEmpresaUsuario.setIntControlHoraIngreso(new Integer(1));
		}else{
			lEmpresaUsuario.setIntControlHoraIngreso(new Integer(0));
		}
	}
	
	public void checkCambioClave(ActionEvent event){
		EmpresaUsuario lEmpresaUsuario = null;
		int index = 0;
		index = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
		lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(index);
		if(lEmpresaUsuario.getBlnCambioClave()){
			lEmpresaUsuario.setIntCambioClave(new Integer(1));
		}else{
			lEmpresaUsuario.setIntCambioClave(new Integer(0));
		}
	}

	public void checkControlVigenciaClave(ActionEvent event){
		EmpresaUsuario lEmpresaUsuario = null;
		int index = 0;
		index = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
		lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(index);
		if(lEmpresaUsuario.getBlnControlVigenciaClave()){
			lEmpresaUsuario.setIntControlVigenciaClave(new Integer(1));
		}else{
			lEmpresaUsuario.setIntControlVigenciaClave(new Integer(0));
			lEmpresaUsuario.setIntRadioControlVigenciaClave(null);
			lEmpresaUsuario.setIntDiasVigencia(null);
		}
	}
	
	public void radioDiasVigencia(ActionEvent event){
		EmpresaUsuario lEmpresaUsuario = null;
		int index = 0;
		index = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
		lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(index);
		if(lEmpresaUsuario.getIntRadioControlVigenciaClave().compareTo(new Integer(0))==0){
			lEmpresaUsuario.setIntDiasVigencia(null);
		}
	}
		
	public void eliminarEmpresaUsuario(ActionEvent event){
		LoginFacadeLocal facadeLogin = null;
		EmpresaUsuario lEmpresaUsuario = null;
		EmpresaUsuarioId lEmpresaUsuarioId = null;
		EmpresaUsuario lEmpresaUsuarioOld = null;
		Integer lIntPersEmpresaPk = null;
		Integer lIntPersPersonaPk = null;
		int index = 0;
		try{
			index = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
			lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(index);
			lIntPersEmpresaPk = lEmpresaUsuario.getId().getIntPersEmpresaPk();
			lIntPersPersonaPk = lEmpresaUsuario.getId().getIntPersPersonaPk();
			if(lIntPersEmpresaPk!=null && lIntPersPersonaPk !=null && lIntPersEmpresaPk.compareTo(new Integer(0)) != 0){
				lEmpresaUsuarioId = new EmpresaUsuarioId();
				lEmpresaUsuarioId.setIntPersEmpresaPk(new Integer(lIntPersEmpresaPk));
				lEmpresaUsuarioId.setIntPersPersonaPk(new Integer(lIntPersPersonaPk));
				lEmpresaUsuarioOld = facadeLogin.getEmpresaUsuarioPorPk(lEmpresaUsuarioId);
				if(lEmpresaUsuarioOld==null){
					usuario.getListaEmpresaUsuario().remove(index);
				}else{
					if(listaEmpresaUsuarioEliminado==null)
					   listaEmpresaUsuarioEliminado = new ArrayList<EmpresaUsuario>();
					lEmpresaUsuarioOld.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					listaEmpresaUsuarioEliminado.add(lEmpresaUsuarioOld);
					eliminarPerfilPorIdEmpresa(lEmpresaUsuario,lIntPersEmpresaPk);
					eliminarSucursalPorIdEmpresa(lEmpresaUsuario,lIntPersEmpresaPk);
				}
			}else{
				usuario.getListaEmpresaUsuario().remove(index);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void eliminarPerfilPorIdEmpresa(EmpresaUsuario lEmpresaUsuario, Integer pIntIdEmpresa){
	    UsuarioPerfil lUsuarioPerfilLoop = null;
	    List<Object> listaDeMapa = null;
	    if(lEmpresaUsuario.getListaUsuarioPerfil()!=null){
			for(int i=0;i<lEmpresaUsuario.getListaUsuarioPerfil().size();i++){
				lUsuarioPerfilLoop = lEmpresaUsuario.getListaUsuarioPerfil().get(i);
				if(lUsuarioPerfilLoop.getId().getIntPersEmpresaPk().compareTo(pIntIdEmpresa)==0){
					lEmpresaUsuario.getListaUsuarioPerfil().remove(i);
				}
			}
	    }
	    
	    listaDeMapa = mapaEliminado.get(pIntIdEmpresa + ".perfil");
	    if(listaDeMapa != null){
	      if(lEmpresaUsuario.getListaUsuarioPerfil()!=null){
		      for(int j=0;j<listaDeMapa.size();j++){
		        lUsuarioPerfilLoop = (UsuarioPerfil)listaDeMapa.get(j);
		        if(lUsuarioPerfilLoop.getId().getIntPersEmpresaPk().compareTo(pIntIdEmpresa)==0){
		        	listaDeMapa.remove(lUsuarioPerfilLoop);
		        }
		      }
	      }
	    }
	}
	
	private void eliminarSucursalPorIdEmpresa(EmpresaUsuario lEmpresaUsuario, Integer pIntIdEmpresa){
	    UsuarioSucursal lUsuarioSucursalLoop = null;
	    List<Object> listaDeMapa = null;
	    if(lEmpresaUsuario.getListaUsuarioSucursal()!=null){
			for(int i=0;i<lEmpresaUsuario.getListaUsuarioSucursal().size();i++){
				lUsuarioSucursalLoop = lEmpresaUsuario.getListaUsuarioSucursal().get(i);
				if(lUsuarioSucursalLoop.getId().getIntPersEmpresaPk().compareTo(pIntIdEmpresa)==0){
					eliminarSubSucursalPorIdSucursal(lEmpresaUsuario, lUsuarioSucursalLoop.getId().getIntIdSucursal());
					lEmpresaUsuario.getListaUsuarioSucursal().remove(i);
				}
			}
	    }
	    
	    listaDeMapa = mapaEliminado.get(pIntIdEmpresa + ".sucursal");
	    if(listaDeMapa != null){
	      if(lEmpresaUsuario.getListaUsuarioSucursal()!=null){
		      for(int j=0;j<listaDeMapa.size();j++){
		        lUsuarioSucursalLoop = (UsuarioSucursal)listaDeMapa.get(j);
		        if(lUsuarioSucursalLoop.getId().getIntPersEmpresaPk().compareTo(pIntIdEmpresa)==0){
		        	listaDeMapa.remove(lUsuarioSucursalLoop);
		        }
		      }
	      }
	    }
	}	
	
	public void agregarPerfilUsuario(ActionEvent event){
	    EmpresaUsuario lEmpresaUsuario = null;
	    UsuarioPerfil lUsuarioPerfil = null;
	    UsuarioPerfilId id = null;
	    int index = 0;
	    boolean esValidoAgregar = false;
	    index = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
	    lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(index);
	    if(lEmpresaUsuario.getId().getIntPersEmpresaPk().compareTo(new Integer(0))!=0){
	    	if(lEmpresaUsuario.getListaUsuarioPerfil()==null && lEmpresaUsuario.getListaPerfil()!=null){
		    	lEmpresaUsuario.setListaUsuarioPerfil(new ArrayList<UsuarioPerfil>());
		    }
	    	if(lEmpresaUsuario.getListaUsuarioPerfil()!=null && lEmpresaUsuario.getListaPerfil()!=null){
		    	if(lEmpresaUsuario.getListaUsuarioPerfil().size() < lEmpresaUsuario.getListaPerfil().size()){
		    		esValidoAgregar = true;
		    	}
		    	if(esValidoAgregar){
			    	lUsuarioPerfil = new UsuarioPerfil();
				    id = new UsuarioPerfilId();
				    id.setIntPersEmpresaPk(lEmpresaUsuario.getId().getIntPersEmpresaPk());
				    id.setIntPersPersonaPk(lEmpresaUsuario.getId().getIntPersPersonaPk());
				    lUsuarioPerfil.setId(id);
				    lEmpresaUsuario.getListaUsuarioPerfil().add(lUsuarioPerfil);
		    	}else{
		    		setMessageError("La cantidad de Perfiles debe ser a lo mas la cantidad de Perfiles asociados a la Empresa");
		    	}
		    }else{
		    	setMessageError("La Empresa Debe contar con Perfiles Registrados");
		    }
	    }else{
	    	setMessageError("Debe elegir una Empresa");
	    }
	}

	public void onchangeDePerfil(ActionEvent event) {
		EmpresaUsuario lEmpresaUsuario = null;
	    UsuarioPerfil lUsuarioPerfil = null;
	    UsuarioPerfil lUsuarioPerfilLoop = null;
	    int indexEmpresa = 0;
	    int indexPerfil = 0;
	    Integer intIdPerfilSelected = null;
	    boolean esReplicado = false;
	    try{
	    	indexEmpresa = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
	    	indexPerfil = Integer.parseInt(getRequestParameter("pIndexPerfil"));
		    lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(indexEmpresa);
		    lUsuarioPerfil = lEmpresaUsuario.getListaUsuarioPerfil().get(indexPerfil);
		    intIdPerfilSelected = lUsuarioPerfil.getId().getIntIdPerfil();
		    if(intIdPerfilSelected != null){
			    for(int i=0;i<lEmpresaUsuario.getListaUsuarioPerfil().size();i++){
			    	lUsuarioPerfilLoop = lEmpresaUsuario.getListaUsuarioPerfil().get(i);
		    		if(indexPerfil != i &&
		    		   lUsuarioPerfilLoop.getId()!=null && 
		    		   lUsuarioPerfilLoop.getId().getIntIdPerfil().compareTo(lUsuarioPerfil.getId().getIntIdPerfil()) == 0){
		    			esReplicado = true;
		    			break;
		    		}
		    	}
				if(esReplicado){
					setMessageError("Debe elegir una Perfil Diferente");
					if(esPresentePerfilOld(lEmpresaUsuario,indexPerfil)){
						lUsuarioPerfil = lEmpresaUsuario.getListaUsuarioPerfil().get(indexPerfil);
					}
					lUsuarioPerfil.getId().setIntIdPerfil(new Integer(0));
				}else{
					esPresentePerfilOld(lEmpresaUsuario,indexPerfil);
				}
		    }	
		}catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	private boolean esPresentePerfilOld(EmpresaUsuario lEmpresaUsuario,int indexPerfil){
		LoginFacadeLocal facadeLogin = null;
		UsuarioPerfil lUsuarioPerfil = null;
	    UsuarioPerfilId lUsuarioPerfilId = null;
		UsuarioPerfil lUsuarioPerfilOld = null;
	    UsuarioPerfilId lUsuarioPerfilIdOld = null;
		String pIntPersEmpresaPk = null;
	    String pIntPersPersonaPk = null;
	    String pIntIdPerfil = null;
	    String pDtDesde = null;
		boolean esPresentePerfilOld = false;
		String strkey = null;
		List<Object> listaDeMapa = null;
		try{
			pIntPersEmpresaPk = getRequestParameter("pIntPersEmpresaPk");
			pIntPersPersonaPk = getRequestParameter("pIntPersPersonaPk");
			pIntIdPerfil = getRequestParameter("pIntIdPerfil");
			pDtDesde = getRequestParameter("pDtDesde");
			if(pIntPersPersonaPk != null && !pIntPersPersonaPk.trim().equals("")&& 
			   pIntIdPerfil != null && !pIntIdPerfil.trim().equals("") &&
			   pDtDesde != null && !pDtDesde.trim().equals("")){
			   facadeLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
			   lUsuarioPerfilIdOld = new UsuarioPerfilId();
			   lUsuarioPerfilIdOld.setIntPersEmpresaPk(new Integer(pIntPersEmpresaPk));
			   lUsuarioPerfilIdOld.setIntPersPersonaPk(new Integer(pIntPersPersonaPk));
			   lUsuarioPerfilIdOld.setIntIdPerfil(new Integer(pIntIdPerfil));
			   lUsuarioPerfilIdOld.setDtDesde(new Date(pDtDesde));
			   lUsuarioPerfilOld = facadeLogin.getUsuarioPerfilPorPk(lUsuarioPerfilIdOld);
			   if(lUsuarioPerfilOld!=null){
				   if(mapaEliminado==null)
					   mapaEliminado = new HashMap<String,List<Object>>();
				   strkey = pIntPersEmpresaPk+".perfil";
				   if(mapaEliminado.containsKey(strkey)){
					   listaDeMapa = mapaEliminado.get(strkey);  
				   }else{
					   listaDeMapa = new ArrayList<Object>();
					   mapaEliminado.put(strkey,listaDeMapa);
				   }
				   lUsuarioPerfilOld.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				   listaDeMapa.add(lUsuarioPerfilOld);
				   
		    	   lUsuarioPerfil = new UsuarioPerfil();
		    	   lUsuarioPerfilId = new UsuarioPerfilId();
		    	   lUsuarioPerfilId.setIntPersEmpresaPk(lEmpresaUsuario.getId().getIntPersEmpresaPk());
		    	   lUsuarioPerfilId.setIntPersPersonaPk(lEmpresaUsuario.getId().getIntPersPersonaPk());
				   lUsuarioPerfil.setId(lUsuarioPerfilId);
				   lEmpresaUsuario.getListaUsuarioPerfil().set(indexPerfil,lUsuarioPerfil);
				   esPresentePerfilOld = true;
			   }
			}
		}catch(Exception e){
	    	e.printStackTrace();
	    }
		return esPresentePerfilOld;
	}

	public void seleccionarFechaDePerfilUsuario(ActionEvent event){
	    EmpresaUsuario lEmpresaUsuario = null;
	    UsuarioPerfil lUsuarioPerfil = null;
	    int indexEmpresa = 0;
	    int indexPerfil = 0;	
    	indexEmpresa = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
	    indexPerfil = Integer.parseInt(getRequestParameter("pIndexPerfil"));
	    lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(indexEmpresa);
	    lUsuarioPerfil = lEmpresaUsuario.getListaUsuarioPerfil().get(indexPerfil);
	    lUsuarioPerfil.setDtHasta(null);
	}
	
	public void eliminarPerfilUsuario(ActionEvent event){
	    LoginFacadeLocal facade = null;
	    EmpresaUsuario lEmpresaUsuario = null;
	    UsuarioPerfil lUsuarioPerfil = null;
	    UsuarioPerfil lUsuarioPerfilTemp = null;
	    int indexEmpresa = 0;
	    int indexPerfil = 0;
	    String strkey = null;
	    List<Object> listaDeMapa = null;
	    try{
	    	indexEmpresa = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
		    indexPerfil = Integer.parseInt(getRequestParameter("pIndexPerfil"));
		    lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(indexEmpresa);
		    lUsuarioPerfil = lEmpresaUsuario.getListaUsuarioPerfil().get(indexPerfil);
		    if(lUsuarioPerfil.getId().getIntPersPersonaPk()!=null && 
		       lUsuarioPerfil.getId().getDtDesde()!=null){
		    	facade = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
		    	lUsuarioPerfilTemp = facade.getUsuarioPerfilPorPk(lUsuarioPerfil.getId());
		    	if(lUsuarioPerfilTemp != null){
		    	   if(mapaEliminado==null)
						   mapaEliminado = new HashMap<String,List<Object>>();
				   strkey = lEmpresaUsuario.getId().getIntPersEmpresaPk()+".perfil";
				   if(mapaEliminado.containsKey(strkey)){
					   listaDeMapa = mapaEliminado.get(strkey);  
				   }else{
					   listaDeMapa = new ArrayList<Object>();
					   mapaEliminado.put(strkey,listaDeMapa);
				   }
		    	   lUsuarioPerfil.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
		    	   listaDeMapa.add(lUsuarioPerfil);
		    	}
		    }
		    lEmpresaUsuario.getListaUsuarioPerfil().remove(indexPerfil);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void agregaSucursalUsuario(ActionEvent event){
		EmpresaFacadeLocal facadeEmpresa = null;
	    EmpresaUsuario lEmpresaUsuario = null;
	    UsuarioSucursal lUsuarioSucursal = null;
	    UsuarioSucursalId id = null;
	    boolean esValidoAgregar = false;
	    int index = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
	    lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(index);
	    try{
		    if(lEmpresaUsuario.getId().getIntPersEmpresaPk().compareTo(new Integer(0))!=0){
		    	if(lEmpresaUsuario.getListaUsuarioSucursal()==null && lEmpresaUsuario.getListaSucursal()!=null){
			    	lEmpresaUsuario.setListaUsuarioSucursal(new ArrayList<UsuarioSucursal>());
			    }
		    	if(lEmpresaUsuario.getListaUsuarioSucursal().size() == 0 && lEmpresaUsuario.getListaSucursal()==null){
		    		facadeEmpresa = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
		    		lEmpresaUsuario.setListaSucursal(facadeEmpresa.getListaSucursalPorPkEmpresa(lEmpresaUsuario.getId().getIntPersEmpresaPk()));
		    	}
		    	if(lEmpresaUsuario.getListaUsuarioSucursal()!=null){
			    	if(lEmpresaUsuario.getListaUsuarioSucursal().size() < lEmpresaUsuario.getListaSucursal().size()){
		 	    		esValidoAgregar = true;
		 	    	}
		 	    	if(esValidoAgregar){
				    	lUsuarioSucursal = new UsuarioSucursal();
					    id = new UsuarioSucursalId();
					    id.setIntPersEmpresaPk(lEmpresaUsuario.getId().getIntPersEmpresaPk());
					    id.setIntPersPersonaPk(lEmpresaUsuario.getId().getIntPersPersonaPk());
					    lUsuarioSucursal.setId(id);
					    lEmpresaUsuario.getListaUsuarioSucursal().add(lUsuarioSucursal);
		 	    	}else{
		 	    		setMessageError("La cantidad de Sucursales debe ser a lo mas la cantidad de Sucursales asociados a la Empresa");
		 	    	}
			    }else{
			    	setMessageError("La Empresa Debe contar con Sucursales Registradas");
			    }
		    }else{
		    	setMessageError("Debe elegir una Empresa");
		    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public void onchangeDeSucursal(ActionEvent event) {
		EmpresaFacadeLocal facade = null;
		EmpresaUsuario lEmpresaUsuario = null;
		UsuarioSucursal lUsuarioSucursal = null;
		UsuarioSucursal lUsuarioSucursalLoop = null;
		UsuarioSubSucursal lUsuarioSubSucursalLoop = null;
		Integer intIdSucursalSelected = null;
		boolean esReplicado = false;
		try{
			int indexEmpresa = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
			int indexSucursal = Integer.parseInt(getRequestParameter("pIndexSucursal"));
			lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(indexEmpresa);
			lUsuarioSucursal = lEmpresaUsuario.getListaUsuarioSucursal().get(indexSucursal);
			intIdSucursalSelected = lUsuarioSucursal.getId().getIntIdSucursal();
			if(intIdSucursalSelected!=null){
				for(int i=0;i<lEmpresaUsuario.getListaUsuarioSucursal().size();i++){
					lUsuarioSucursalLoop = lEmpresaUsuario.getListaUsuarioSucursal().get(i);
					if(indexSucursal != i &&
					   lUsuarioSucursalLoop.getId()!=null && 
					   lUsuarioSucursalLoop.getId().getIntIdSucursal().compareTo(intIdSucursalSelected) == 0){
						esReplicado = true;
		    			break;
		    		}
				}
				if(esReplicado){
					setMessageError("Debe elegir una Sucursal Diferente");
					if(esPresenteSucursalOld(lEmpresaUsuario,indexSucursal)){
						lUsuarioSucursal = lEmpresaUsuario.getListaUsuarioSucursal().get(indexSucursal);
					}
					lUsuarioSucursal.getId().setIntIdSucursal(new Integer(0));
				}else{
					esPresenteSucursalOld(lEmpresaUsuario,indexSucursal);
					if(intIdSucursalSelected.compareTo(new Integer(0)) != 0){
						facade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
						lUsuarioSucursal.setListaSubSucursal(facade.getListaSubSucursalPorIdSucursal(lUsuarioSucursal.getId().getIntIdSucursal()));
						if(lEmpresaUsuario.getListaUsuarioSubSucursal()!=null){
							for(int i=0;i<lEmpresaUsuario.getListaUsuarioSubSucursal().size();i++){
								lUsuarioSubSucursalLoop = lEmpresaUsuario.getListaUsuarioSubSucursal().get(i);
								if(lUsuarioSubSucursalLoop.getId().getIntIdSucursal().compareTo(intIdSucursalSelected)==0){
									lEmpresaUsuario.getListaUsuarioSubSucursal().remove(lUsuarioSubSucursalLoop);
								}
							}
						}
					}else{
						lUsuarioSucursal.setListaSubSucursal(null);
					}
				}
			}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	private boolean esPresenteSucursalOld(EmpresaUsuario lEmpresaUsuario,int indexSucursal) throws BusinessException{
		LoginFacadeLocal facadeLogin = null;
		UsuarioSucursal lUsuarioSucursal = null;
		UsuarioSucursal lUsuarioSucursalLoop = null;
		UsuarioSucursalId lUsuarioSucursalId = null;
		UsuarioSucursal lUsuarioSucursalOld = null;
		UsuarioSucursalId lUsuarioSucursalIdOld = null;
		UsuarioSubSucursal lUsuarioSubSucursalLoop = null;
		String pIntPersEmpresaPk = null;
		String pIntIdSucursal = null;
		String pIntPersPersonaPk = null;
		String pDtFechaRegistro = null;
		boolean esPresenteSucursalOld = false;
		String strkey = null;
		List<Object> listaDeMapa = null;
		try{
			pIntPersEmpresaPk = getRequestParameter("pIntPersEmpresaPk");
			pIntIdSucursal = getRequestParameter("pIntIdSucursal");
			pIntPersPersonaPk = getRequestParameter("pIntPersPersonaPk");
			pDtFechaRegistro = getRequestParameter("pDtFechaRegistro");
			if(pIntPersPersonaPk != null && !pIntPersPersonaPk.trim().equals("") && 
			   pIntIdSucursal != null && !pIntIdSucursal.trim().equals("") &&
			   pDtFechaRegistro != null && !pDtFechaRegistro.trim().equals("")){
				lUsuarioSucursalIdOld = new UsuarioSucursalId();
				lUsuarioSucursalIdOld.setIntPersEmpresaPk(new Integer(pIntPersEmpresaPk));
				lUsuarioSucursalIdOld.setIntIdSucursal(new Integer(pIntIdSucursal));
				lUsuarioSucursalIdOld.setIntPersPersonaPk(new Integer(pIntPersPersonaPk));
				lUsuarioSucursalIdOld.setDtFechaRegistro(JFecha.convertirStringASqlTimestamp(pDtFechaRegistro));
				facadeLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
				lUsuarioSucursalOld = facadeLogin.getUsuarioSucursalPorPk(lUsuarioSucursalIdOld);
				if(lUsuarioSucursalOld!=null){
					if(mapaEliminado==null)
						   mapaEliminado = new HashMap<String,List<Object>>();
				    strkey = pIntPersEmpresaPk+".sucursal";
				    if(mapaEliminado.containsKey(strkey)){
					   listaDeMapa = mapaEliminado.get(strkey);  
				    }else{
					   listaDeMapa = new ArrayList<Object>();
					   mapaEliminado.put(strkey,listaDeMapa);
				    }
					lUsuarioSucursalOld.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					listaDeMapa.add(lUsuarioSucursalOld);
		    		
		    		lUsuarioSucursal = new UsuarioSucursal();
		    		lUsuarioSucursalId = new UsuarioSucursalId();
		    		lUsuarioSucursalId.setIntPersEmpresaPk(lEmpresaUsuario.getId().getIntPersEmpresaPk());
		    		lUsuarioSucursalId.setIntPersPersonaPk(lEmpresaUsuario.getId().getIntPersPersonaPk());
				    lUsuarioSucursal.setId(lUsuarioSucursalId);
				    lEmpresaUsuario.getListaUsuarioSucursal().set(indexSucursal,lUsuarioSucursal);
				    esPresenteSucursalOld = true;
				}
				
				for(int i=0;i<lEmpresaUsuario.getListaUsuarioSubSucursal().size();i++){
			    	lUsuarioSubSucursalLoop = lEmpresaUsuario.getListaUsuarioSubSucursal().get(i);
			    	if(lUsuarioSubSucursalLoop.getId().getIntIdSucursal().compareTo(new Integer(pIntIdSucursal))==0){
			    		lEmpresaUsuario.getListaUsuarioSubSucursal().remove(lUsuarioSubSucursalLoop);
			    	}
			    }
			}
		}catch(Exception e){
	    	throw new BusinessException(e);
	    }
		return esPresenteSucursalOld;
	}
	
	public void seleccionarFechaDeSucursalUsuario(ActionEvent event){
	    EmpresaUsuario lEmpresaUsuario = null;
	    UsuarioSucursal lUsuarioSucursal = null;
	    UsuarioSucursal lUsuarioSucursalTemp = null;
	    int indexEmpresa = 0;
	    int indexSucursal = 0;	
    	indexEmpresa = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
    	indexSucursal = Integer.parseInt(getRequestParameter("pIndexSucursal"));
	    lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(indexEmpresa);
	    lUsuarioSucursal = lEmpresaUsuario.getListaUsuarioSucursal().get(indexSucursal);
	    lUsuarioSucursal.setDtHasta(null);
	}
	
	public void eliminarSucursalUsuario(ActionEvent event){
	    LoginFacadeLocal facade = null;
	    EmpresaUsuario lEmpresaUsuario = null;
	    UsuarioSucursal lUsuarioSucursal = null;
	    UsuarioSucursal lUsuarioSucursalTemp = null;
	    UsuarioSubSucursal lUsuarioSubSucursalLoop = null;
	    int indexEmpresa = 0;
	    int indexSucursal = 0;
	    String strkey = null;
	    List<Object> listaDeMapa = null;
	    try{
	    	indexEmpresa = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
	    	indexSucursal = Integer.parseInt(getRequestParameter("pIndexSucursal"));
		    lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(indexEmpresa);
		    lUsuarioSucursal = lEmpresaUsuario.getListaUsuarioSucursal().get(indexSucursal);
		    if(lUsuarioSucursal.getId().getIntPersPersonaPk()!=null && 
		       lUsuarioSucursal.getId().getDtFechaRegistro()!=null){
		    	facade = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
		    	lUsuarioSucursalTemp = facade.getUsuarioSucursalPorPk(lUsuarioSucursal.getId());
		    	if(lUsuarioSucursalTemp != null){
		    		if(mapaEliminado==null)
						   mapaEliminado = new HashMap<String,List<Object>>();
				    strkey = lEmpresaUsuario.getId().getIntPersEmpresaPk()+".sucursal";
				    if(mapaEliminado.containsKey(strkey)){
					   listaDeMapa = mapaEliminado.get(strkey);  
				    }else{
					   listaDeMapa = new ArrayList<Object>();
					   mapaEliminado.put(strkey,listaDeMapa);
				    }
		    		lUsuarioSucursal.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
		    		listaDeMapa.add(lUsuarioSucursal);
		    		eliminarSubSucursalPorIdSucursal(lEmpresaUsuario,lUsuarioSucursal.getId().getIntIdSucursal());
		    	}
		    }
		    lEmpresaUsuario.getListaUsuarioSucursal().remove(indexSucursal);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	private void eliminarSubSucursalPorIdSucursal(EmpresaUsuario lEmpresaUsuario, Integer pIntIdSucursal){
	    UsuarioSubSucursal lUsuarioSubSucursalLoop = null;
	    List<Object> listaDeMapa = null;
	    if(lEmpresaUsuario.getListaUsuarioSubSucursal()!=null){
			for(int i=0;i<lEmpresaUsuario.getListaUsuarioSubSucursal().size();i++){
				lUsuarioSubSucursalLoop = lEmpresaUsuario.getListaUsuarioSubSucursal().get(i);
				if(lUsuarioSubSucursalLoop.getId().getIntIdSucursal().compareTo(pIntIdSucursal)==0){
					lEmpresaUsuario.getListaUsuarioSubSucursal().remove(i);
				}
			}
	    }
	    
	    listaDeMapa = mapaEliminado.get(lEmpresaUsuario.getId().getIntPersEmpresaPk() + ".sub-sucursal");
	    if(listaDeMapa != null){
	      if(lEmpresaUsuario.getListaUsuarioSubSucursal()!=null){
		      for(int j=0;j<listaDeMapa.size();j++){
		        lUsuarioSubSucursalLoop = (UsuarioSubSucursal)listaDeMapa.get(j);
		        if(lUsuarioSubSucursalLoop.getId().getIntIdSucursal().compareTo(pIntIdSucursal)==0){
		        	listaDeMapa.remove(lUsuarioSubSucursalLoop);
		        }
		      }
	      }
	    }
	}
	
	public void agregaSubSucursalUsuario(ActionEvent event){
	    EmpresaUsuario lEmpresaUsuario = null;
	    UsuarioSubSucursal lUsuarioSubSucursal = null;
	    UsuarioSubSucursal lUsuarioSubSucursalLoop = null;
	    UsuarioSubSucursalId id = null;
	    UsuarioSucursal lUsuarioSucursal = null;
	    boolean esValidoAgregar = false;
	    int indexEmpresa = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
	    int indexSucursal = Integer.parseInt(getRequestParameter("pIndexSucursal"));
	    int sizeListaSubSucursal = 0;
	    lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(indexEmpresa);
	    lUsuarioSucursal = lEmpresaUsuario.getListaUsuarioSucursal().get(indexSucursal);
	    if(lUsuarioSucursal.getId().getIntIdSucursal()!=0){
	    	if(lEmpresaUsuario.getListaUsuarioSubSucursal()==null && lUsuarioSucursal.getListaSubSucursal()!=null){
		    	lEmpresaUsuario.setListaUsuarioSubSucursal(new ArrayList<UsuarioSubSucursal>());
		    }
	    	if(lEmpresaUsuario.getListaUsuarioSubSucursal() != null && lUsuarioSucursal.getListaSubSucursal()!=null){
	    		for(int i=0;i<lEmpresaUsuario.getListaUsuarioSubSucursal().size();i++){
	    			lUsuarioSubSucursalLoop = lEmpresaUsuario.getListaUsuarioSubSucursal().get(i);
	    			if(lUsuarioSubSucursalLoop.getId().getIntIdSucursal().compareTo(lUsuarioSucursal.getId().getIntIdSucursal())==0)
	    			sizeListaSubSucursal++;
	    		}
	    		if(sizeListaSubSucursal < lUsuarioSucursal.getListaSubSucursal().size()){
	  	    		esValidoAgregar = true;
	  	    	}
	  	    	if(esValidoAgregar){
			    	lUsuarioSubSucursal = new UsuarioSubSucursal();
				    id = new UsuarioSubSucursalId();
				    id.setIntPersEmpresaPk(lEmpresaUsuario.getId().getIntPersEmpresaPk());
				    id.setIntPersPersonaPk(lEmpresaUsuario.getId().getIntPersPersonaPk());
				    id.setIntIdSucursal(lUsuarioSucursal.getId().getIntIdSucursal());
				    lUsuarioSubSucursal.setId(id);
				    lEmpresaUsuario.getListaUsuarioSubSucursal().add(lUsuarioSubSucursal);
	  	    	}else{
	  	    		setMessageError("La cantidad de Sub-Sucursales debe ser a lo mas la cantidad de Sub-Sucursales asociados a la Sucursal");
	  	    	}
		    }else{
		    	setMessageError("La Sucursal Debe contar con Sub-Sucursales Registradas");
		    }
	    }else{
	    	setMessageError("Debe elegir una Sucursal");
	    }
	}
	
	public void onchangeDeSubSucursal(ActionEvent event){
		EmpresaUsuario lEmpresaUsuario = null;
		UsuarioSucursal lUsuarioSucursal = null;
	    UsuarioSubSucursal lUsuarioSubSucursal = null;
	    UsuarioSubSucursal lUsuarioSubSucursalLoop = null;
	    int indexEmpresa = 0;
	    int indexSucursal = 0;
	    int indexSubSucursal = 0;
	    Integer intIdSubSucursalSelected = null;
	    boolean esReplicado = false;
	    try{
	    	indexEmpresa = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
	    	indexSucursal = Integer.parseInt(getRequestParameter("pIndexSucursal"));
	    	indexSubSucursal = Integer.parseInt(getRequestParameter("pIndexSubSucursal"));
		    lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(indexEmpresa);
		    lUsuarioSubSucursal = lEmpresaUsuario.getListaUsuarioSubSucursal().get(indexSubSucursal);
		    intIdSubSucursalSelected = lUsuarioSubSucursal.getId().getIntIdSubSucursal();
		    if(intIdSubSucursalSelected != null){
			    for(int i=0;i<lEmpresaUsuario.getListaUsuarioSubSucursal().size();i++){
			    	lUsuarioSubSucursalLoop = lEmpresaUsuario.getListaUsuarioSubSucursal().get(i);
			    	if(indexSubSucursal != i &&
			    	   lUsuarioSubSucursalLoop.getId()!=null && 
			    	   lUsuarioSubSucursalLoop.getId().getIntIdSubSucursal().compareTo(intIdSubSucursalSelected) == 0){
			    		esReplicado = true;
		    			break;
		    		}
			    }
			    if(esReplicado){
			    	setMessageError("Debe elegir una Sub-Sucursal Diferente");
			    	if(esPresenteSubSucursalOld(lEmpresaUsuario,indexSucursal,indexSubSucursal)){
			    		lUsuarioSubSucursal = lEmpresaUsuario.getListaUsuarioSubSucursal().get(indexSubSucursal);
			    	}
			    	lUsuarioSubSucursal.getId().setIntIdSubSucursal(new Integer(0));
			    }else{
			    	esPresenteSubSucursalOld(lEmpresaUsuario,indexSucursal,indexSubSucursal);
			    }
		    }  
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	private boolean esPresenteSubSucursalOld(EmpresaUsuario lEmpresaUsuario,int indexSucursal,int indexSubSucursal)throws BusinessException{
		LoginFacadeLocal facadeLogin = null;
		UsuarioSubSucursal lUsuarioSubSucursal = null;
	    UsuarioSubSucursalId lUsuarioSubSucursalId = null;
		UsuarioSubSucursal lUsuarioSubSucursalLoop = null;
	    UsuarioSubSucursal lUsuarioSubSucursalOld = null;
	    UsuarioSubSucursalId lUsuarioSubSucursalIdOld = null;
	    UsuarioSucursal lUsuarioSucursal = null;
		String pIntPersEmpresaPk = null;
	    String pIntIdSucursal = null;
	    String pIntIdSubSucursal = null;
	    String pDtFechaRegistro = null;
	    String pIntPersPersonaPk = null;
		boolean esPresenteSubSucursalOld = false;
		String strkey = null;
		List<Object> listaDeMapa = null;
		try{
			pIntPersEmpresaPk = getRequestParameter("pIntPersEmpresaPk");
	    	pIntIdSucursal = getRequestParameter("pIntIdSucursal");
	    	pIntIdSubSucursal = getRequestParameter("pIntIdSubSucursal");
	    	pDtFechaRegistro = getRequestParameter("pDtFechaRegistro");
	    	pIntPersPersonaPk = getRequestParameter("pIntPersPersonaPk");
	    	if(pIntPersPersonaPk != null && !pIntPersPersonaPk.trim().equals("") && 
	    	   pIntIdSubSucursal != null && !pIntIdSubSucursal.trim().equals("") &&
	    	   pDtFechaRegistro!= null && !pDtFechaRegistro.trim().equals("")){
	    		facadeLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
	    		lUsuarioSubSucursalIdOld = new UsuarioSubSucursalId();
	    		lUsuarioSubSucursalIdOld.setIntPersEmpresaPk(new Integer(pIntPersEmpresaPk));
	    		lUsuarioSubSucursalIdOld.setIntIdSucursal(new Integer(pIntIdSucursal));
	    		lUsuarioSubSucursalIdOld.setIntIdSubSucursal(new Integer(pIntIdSubSucursal));
	    		lUsuarioSubSucursalIdOld.setDtFechaRegistro(JFecha.convertirStringASqlTimestamp(pDtFechaRegistro));
	    		lUsuarioSubSucursalIdOld.setIntPersPersonaPk(new Integer(pIntPersPersonaPk));
	    		lUsuarioSubSucursalOld = facadeLogin.getUsuarioSubSucursalPorPk(lUsuarioSubSucursalIdOld);
	    		if(lUsuarioSubSucursalOld!=null){
	    			if(mapaEliminado==null)
						   mapaEliminado = new HashMap<String,List<Object>>();
				    strkey = pIntPersEmpresaPk+".sub-sucursal";
				    if(mapaEliminado.containsKey(strkey)){
					   listaDeMapa = mapaEliminado.get(strkey);  
				    }else{
					   listaDeMapa = new ArrayList<Object>();
					   mapaEliminado.put(strkey,listaDeMapa);
				    }
	    			lUsuarioSubSucursalOld.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
	    			listaDeMapa.add(lUsuarioSubSucursalOld);
		    		
		    		lUsuarioSucursal = lEmpresaUsuario.getListaUsuarioSucursal().get(indexSucursal);
		    		lUsuarioSubSucursal = new UsuarioSubSucursal();
		    		lUsuarioSubSucursalId = new UsuarioSubSucursalId();
		    		lUsuarioSubSucursalId.setIntPersEmpresaPk(lEmpresaUsuario.getId().getIntPersEmpresaPk());
		    		lUsuarioSubSucursalId.setIntPersPersonaPk(lEmpresaUsuario.getId().getIntPersPersonaPk());
		    		lUsuarioSubSucursalId.setIntIdSucursal(lUsuarioSucursal.getId().getIntIdSucursal());
				    lUsuarioSubSucursal.setId(lUsuarioSubSucursalId);
				    lEmpresaUsuario.getListaUsuarioSubSucursal().set(indexSubSucursal,lUsuarioSubSucursal);
				    esPresenteSubSucursalOld = true;
	    		}
	    	}
		}catch(Exception e){
			throw new BusinessException(e);
		}
    	return esPresenteSubSucursalOld;
	}
	
	public void seleccionarFechaDeSubSucursalUsuario(ActionEvent event){
	    EmpresaUsuario lEmpresaUsuario = null;
	    UsuarioSubSucursal lUsuarioSubSucursal = null;
	    int indexEmpresa = 0;
	    int indexSubSucursal = 0;	
    	indexEmpresa = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
    	indexSubSucursal = Integer.parseInt(getRequestParameter("pIndexSubSucursal"));
	    lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(indexEmpresa);
	    lUsuarioSubSucursal = lEmpresaUsuario.getListaUsuarioSubSucursal().get(indexSubSucursal);
	    lUsuarioSubSucursal.setDtHasta(null);
	}
	
	public void eliminarSubSucursalUsuario(ActionEvent event){
	    LoginFacadeLocal facade = null;
	    EmpresaUsuario lEmpresaUsuario = null;
	    UsuarioSubSucursal lUsuarioSubSucursal = null;
	    UsuarioSubSucursal lUsuarioSubSucursalTemp = null;
	    int indexEmpresa = 0;
	    int indexSubSucursal = 0;
	    String strkey = null;
	    List<Object> listaDeMapa = null;
	    try{
	    	indexEmpresa = Integer.parseInt(getRequestParameter("pIndexEmpresa"));
	    	indexSubSucursal = Integer.parseInt(getRequestParameter("pIndexSubSucursal"));
		    lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(indexEmpresa);
		    lUsuarioSubSucursal = lEmpresaUsuario.getListaUsuarioSubSucursal().get(indexSubSucursal);
		    if(lUsuarioSubSucursal.getId().getIntPersPersonaPk() != null  &&
		       lUsuarioSubSucursal.getId().getDtFechaRegistro() != null){
		    	facade = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
		    	lUsuarioSubSucursalTemp = facade.getUsuarioSubSucursalPorPk(lUsuarioSubSucursal.getId());
		    	if(lUsuarioSubSucursalTemp != null){
		    		if(mapaEliminado==null)
						   mapaEliminado = new HashMap<String,List<Object>>();
				    strkey = lEmpresaUsuario.getId().getIntPersEmpresaPk()+".sub-sucursal";
				    if(mapaEliminado.containsKey(strkey)){
					   listaDeMapa = mapaEliminado.get(strkey);  
				    }else{
					   listaDeMapa = new ArrayList<Object>();
					   mapaEliminado.put(strkey,listaDeMapa);
				    }
		    		lUsuarioSubSucursal.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
		    		listaDeMapa.add(lUsuarioSubSucursal);
		    	}
		    }
		    lEmpresaUsuario.getListaUsuarioSubSucursal().remove(indexSubSucursal);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void checkAgregarDomicilio(ActionEvent event){
		ContactoFacadeRemote facade = null;
		Domicilio domicilio = null;
		Domicilio domicilioOld = null;
		try{
			if(!chkDireccion){
				if(usuario.getPersona().getListaDomicilio()!= null && usuario.getPersona().getListaDomicilio().size()>0){
					for(int i=0;i<usuario.getPersona().getListaDomicilio().size();i++){
						domicilio = usuario.getPersona().getListaDomicilio().get(i);
						if(domicilio.getId().getIntIdPersona()!=null && domicilio.getId().getIntIdDomicilio()!=null){
							facade = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
							domicilioOld = facade.getDomicilioPorPK(domicilio.getId());
							if(domicilioOld!=null){
								if(personaEliminado == null)
									personaEliminado= new Persona();
								if(personaEliminado.getListaDomicilio()==null)
									personaEliminado.setListaDomicilio(new ArrayList<Domicilio>());
								domicilioOld.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
								personaEliminado.getListaDomicilio().add(domicilioOld);
							}
						}
					}
					usuario.getPersona().getListaDomicilio().clear();
				}
				domicilio = null;
				usuario.getPersona().setListaDomicilio(null);
				listaUbigeoDepartamento = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void irAgregarDomicilio(ActionEvent event){
		GeneralFacadeRemote remote = null;
		try{
			domicilio = new Domicilio();
			domicilio.setId(new DomicilioPK());
			if(listaUbigeoDepartamento == null){
				remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			    listaUbigeoDepartamento = remote.getListaUbigeoDeDepartamento();
			}
			msgUsuario.setMsgDomicilio(new DomicilioMsg());
			esPopPupValido = new Boolean(false);
		}catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public void validarDomicilio(ActionEvent event){
		esPopPupValido = LoginValidador.validarDomicilio(msgUsuario, domicilio);
	}
	
	public void agregarDomicilio(ActionEvent event){
		if(usuario.getPersona().getListaDomicilio()==null){
			usuario.getPersona().setListaDomicilio(new ArrayList<Domicilio>());
		}
		usuario.getPersona().getListaDomicilio().add(domicilio);
		msgUsuario.setMsgDomicilio(null);
		esPopPupValido = new Boolean(false);
	}
	
	public void cancelarDomicilio(ActionEvent event){
		domicilio = null;
		if(usuario.getPersona().getListaDomicilio()==null || usuario.getPersona().getListaDomicilio().size()==0){
			listaUbigeoDepartamento = null;
		}
		listaUbigeoProvincia = null;
		listaUbigeoDistrito = null;
		msgUsuario.setMsgDomicilio(null);
		esPopPupValido = new Boolean(false);
	}
	
	public void consultarDomicilio(ActionEvent event){
		GeneralFacadeRemote facadeGral = null;
		int pIndexDomicilio = 0;
		try{
			pIndexDomicilio = Integer.parseInt(getRequestParameter("pIndexDomicilio"));
			domicilio = usuario.getPersona().getListaDomicilio().get(pIndexDomicilio);
			if(domicilio.getIntParaUbigeoPkDpto()!=null){
				facadeGral = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
				if(listaUbigeoDepartamento==null){
					listaUbigeoDepartamento = facadeGral.getListaUbigeoDeDepartamento();
				}
				if(domicilio.getIntParaUbigeoPkProvincia()!=null){
					listaUbigeoProvincia = facadeGral.getListaUbigeoDeProvinciaPorIdUbigeo(domicilio.getIntParaUbigeoPkProvincia());
					if(domicilio.getIntParaUbigeoPkDistrito()!=null){
						listaUbigeoDistrito = facadeGral.getListaUbigeoDeDistritoPorIdUbigeo(domicilio.getIntParaUbigeoPkDistrito());
					}
				}
			}
		}catch(Exception e){
	    	e.printStackTrace();
	    }	
	}

	public void eliminarDomicilio(ActionEvent event){
		ContactoFacadeRemote facade = null;
		Domicilio domicilioOld = null;
		int pIndexDomicilio = 0;
		try{
			pIndexDomicilio = Integer.parseInt(getRequestParameter("pIndexDomicilio"));
			domicilio = usuario.getPersona().getListaDomicilio().get(pIndexDomicilio);
			if(domicilio.getId().getIntIdPersona()!=null && domicilio.getId().getIntIdDomicilio()!=null){
				facade = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
				domicilioOld = facade.getDomicilioPorPK(domicilio.getId());
				if(domicilioOld!=null){
					if(personaEliminado == null)
						personaEliminado= new Persona();
					if(personaEliminado.getListaDomicilio()==null)
						personaEliminado.setListaDomicilio(new ArrayList<Domicilio>());
					domicilioOld.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					personaEliminado.getListaDomicilio().add(domicilioOld);
				}
			}
			usuario.getPersona().getListaDomicilio().remove(pIndexDomicilio);
		}catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public void onchangeDeUbigeoDepartamento(ActionEvent event){
		GeneralFacadeRemote remote = null;
		Integer intParaUbigeoPkDpto = null;
		try{
			intParaUbigeoPkDpto = domicilio.getIntParaUbigeoPkDpto();
			if(intParaUbigeoPkDpto != null && intParaUbigeoPkDpto.compareTo(new Integer(0))!=0){
				remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			    listaUbigeoProvincia = remote.getListaUbigeoDeProvinciaPorIdUbigeo(intParaUbigeoPkDpto);
			}else{
				listaUbigeoProvincia = null;
				listaUbigeoDistrito = null;
			}
		}catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public void onchangeDeUbigeoProvincia(ActionEvent event){
		GeneralFacadeRemote remote = null;
		Integer intParaUbigeoPkProvincia = null;
		try{
			intParaUbigeoPkProvincia = domicilio.getIntParaUbigeoPkProvincia();
			if(intParaUbigeoPkProvincia!=null && intParaUbigeoPkProvincia.compareTo(new Integer(0)) != 0){
				remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			    listaUbigeoDistrito = remote.getListaUbigeoDeDistritoPorIdUbigeo(intParaUbigeoPkProvincia);
			}else{
				listaUbigeoDistrito = null;
			}
		}catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public void onclickCroquis(ActionEvent event){
		
	}
	
	public void checkAgregarComunicacion(ActionEvent event){
		ContactoFacadeRemote facade = null;
		Comunicacion comunicacion = null;
		Comunicacion comunicacionOld = null;
		try{
			if(!chkComunicacion){
				if(usuario.getPersona().getListaComunicacion()!= null && usuario.getPersona().getListaComunicacion().size()>0){
					for(int i=0;i<usuario.getPersona().getListaComunicacion().size();i++){
						comunicacion = usuario.getPersona().getListaComunicacion().get(i);
						if(comunicacion.getId().getIntIdPersona()!=null && comunicacion.getId().getIntIdComunicacion()!=null){
							facade = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
							comunicacionOld = facade.getComunicacionPorPK(comunicacion.getId());
							if(comunicacionOld!=null){
								if(personaEliminado == null)
									personaEliminado= new Persona();
								if(personaEliminado.getListaComunicacion()==null)
									personaEliminado.setListaComunicacion(new ArrayList<Comunicacion>());
								comunicacionOld.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
								personaEliminado.getListaComunicacion().add(comunicacionOld);
							}
						}
					}
					usuario.getPersona().getListaComunicacion().clear();
				}
				comunicacion = null;
				usuario.getPersona().setListaComunicacion(null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void irAgregarComunicacion(ActionEvent event){
		comunicacion = new Comunicacion();
		comunicacion.setId(new ComunicacionPK());
		msgUsuario.setMsgComunicacion(new ComunicacionMsg());
		esPopPupValido = new Boolean(false);
	}
	
	public void validarComunicacion(ActionEvent event){
		esPopPupValido = LoginValidador.validarComunicacion(msgUsuario, comunicacion);
	}
	
	public Boolean getEsPopPupValido() {
		return esPopPupValido;
	}

	public void setEsPopPupValido(Boolean esPopPupValido) {
		this.esPopPupValido = esPopPupValido;
	}

	public void agregarComunicacion(ActionEvent event){
		if(usuario.getPersona().getListaComunicacion()==null){
			usuario.getPersona().setListaComunicacion(new ArrayList<Comunicacion>());
		}
		usuario.getPersona().getListaComunicacion().add(comunicacion);
		comunicacion = null;
		msgUsuario.setMsgComunicacion(null);
	}
	
	public void cancelarComunicacion(ActionEvent event){
		comunicacion = null;
		msgUsuario.setMsgComunicacion(null);
	}
	
	public void onchangeDeTipoComunicacion(ActionEvent event){
		Integer intTipoComunicacion = comunicacion.getIntTipoComunicacionCod();
		comunicacion = new Comunicacion();
		comunicacion.setIntTipoComunicacionCod(intTipoComunicacion);
		comunicacion.setId(new ComunicacionPK());
	}
	
	public void consultarComunicacion(ActionEvent event){
		int pIndexComunicacion = 0;
		pIndexComunicacion = Integer.parseInt(getRequestParameter("pIndexComunicacion"));
		comunicacion = usuario.getPersona().getListaComunicacion().get(pIndexComunicacion);
	}

	public void eliminarComunicacion(ActionEvent event){
		ContactoFacadeRemote facade = null;
		Comunicacion comunicacionOld = null;
		int pIndexComunicacion = 0;
		try{
			pIndexComunicacion = Integer.parseInt(getRequestParameter("pIndexComunicacion"));
			comunicacion = usuario.getPersona().getListaComunicacion().get(pIndexComunicacion);
			if(comunicacion.getId().getIntIdPersona()!=null && comunicacion.getId().getIntIdComunicacion()!=null){
				facade = (ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
				comunicacionOld = facade.getComunicacionPorPK(comunicacion.getId());
				if(comunicacionOld!=null){
					if(personaEliminado == null)
						personaEliminado= new Persona();
					if(personaEliminado.getListaComunicacion()==null)
						personaEliminado.setListaComunicacion(new ArrayList<Comunicacion>());
					comunicacionOld.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					personaEliminado.getListaComunicacion().add(comunicacionOld);
				}
			}
			usuario.getPersona().getListaComunicacion().remove(pIndexComunicacion);
		}catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public void checkCasoEmergenciaDeComunicacion(ActionEvent event) {
		if(comunicacion.getChkCom())
			comunicacion.setIntCasoEmergencia(new Integer(1));
		else
			comunicacion.setIntCasoEmergencia(null);
	}

	public void irGrabarUsuario(ActionEvent event) throws DaoException{
	    usuario = new Usuario();
	    usuario.setPersona(new Persona());
	    usuario.getPersona().setListaDocumento(new ArrayList<Documento>());
		usuario.getPersona().getListaDocumento().add(new Documento());
		usuario.getPersona().getListaDocumento().get(0).setIntTipoIdentidadCod(new Integer(0));
		usuario.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
	    strTipoMantUsuario = Constante.MANTENIMIENTO_GRABAR;
	    strValidarUsuario = Constante.MANTENIMIENTO_VALIDAR;
	    chkDireccion = false;
		chkComunicacion = false;
		msgUsuario = new UsuarioMsg();
		listaEmpresaUsuarioEliminado = null;
		mapaEliminado = null;
		personaEliminado = null;
		listaUbigeoDepartamento = null;
		listaUbigeoProvincia = null;
		listaUbigeoDistrito = null;
	}
	
	public void irValidarUsuarioUnico(ActionEvent event){
		PersonaFacadeRemote facade = null;
		LoginFacadeLocal facadeLogin = null;
		Persona lPersona = null;
		Boolean bValidar = true;
		Integer intTipoIdentidad = null;
		String strNumeroIdentidad = null;
		Usuario lUsuario = null;
		try{
		    bValidar = LoginValidador.validarUsuarioUnico(msgUsuario,usuario);
		    if(bValidar == true){
		    	intTipoIdentidad = usuario.getPersona().getListaDocumento().get(0).getIntTipoIdentidadCod();
		    	strNumeroIdentidad = usuario.getPersona().getListaDocumento().get(0).getStrNumeroIdentidad(); 
		    	facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
		    	if(usuario.getPersona().getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
		    		lPersona = facade.getPersonaNaturalPorDocIdentidadYIdEmpresa(intTipoIdentidad,strNumeroIdentidad.trim(),Constante.PARAM_EMPRESASESION);
		    	}else{
		    		lPersona = facade.getPersonaJuridicaPorRuc(strNumeroIdentidad);
		    	}
		    	if(lPersona!=null){
		    		facadeLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
		    		lUsuario = facadeLogin.getUsuarioPersonaPorIdPersona(lPersona.getIntIdPersona());
		    		if(lUsuario!= null){
		    			usuario = lUsuario;
		    		}else{
		    			if(lPersona.getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
		    				usuario.setPersona(facade.getPersonaNaturalPorIdPersona(lPersona.getIntIdPersona()));		    				
		    			}else{
		    				usuario.setPersona(facade.getPersonaJuridicaPorIdPersona(lPersona.getIntIdPersona()));
		    				if(usuario.getPersona().getListaDocumento()==null || usuario.getPersona().getListaDocumento().size()==0){
		    					if(usuario.getPersona().getListaDocumento()==null)
		    						usuario.getPersona().setListaDocumento(new ArrayList<Documento>());
								usuario.getPersona().getListaDocumento().add(new Documento());
								usuario.getPersona().getListaDocumento().get(0).setIntTipoIdentidadCod(intTipoIdentidad);
								usuario.getPersona().getListaDocumento().get(0).setStrNumeroIdentidad(strNumeroIdentidad);
					    	}
		    			}
		    		}
		    		getDetalleUsuario(usuario);
				    strTipoMantUsuario = Constante.MANTENIMIENTO_MODIFICAR;
		    	}else{
		    		if(usuario.getPersona().getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
		    			usuario.getPersona().setStrRuc(null);
		    		}else{
		    			usuario.getPersona().setStrRuc(strNumeroIdentidad);
		    		}
		    		strTipoMantUsuario = Constante.MANTENIMIENTO_GRABAR;
		    	}
		    	limpiarUsuarioMatenimiento();
		    	
		    	strValidarUsuario = Constante.MANTENIMIENTO_NINGUNO;
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void cancelarGrabarUsuario(ActionEvent event){
		usuario = null;
		strTipoMantUsuario = Constante.MANTENIMIENTO_NINGUNO;
		msgUsuario = null; 
		listaEmpresaUsuarioEliminado = null;
		mapaEliminado = null;
		personaEliminado = null;
		listaUbigeoDepartamento = null;
		listaUbigeoProvincia = null;
		listaUbigeoDistrito = null;
		strValidarUsuario = Constante.MANTENIMIENTO_VALIDAR;
	}

	public void onchangeDeTipoPersona(ActionEvent event){
		if(usuario.getPersona().getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
			usuario.getPersona().setNatural(new Natural());
			usuario.getPersona().setJuridica(null);
			usuario.getPersona().getListaDocumento().get(0).setIntTipoIdentidadCod(Constante.PARAM_T_TIPODOCUMENTO_DNI);
		}else if(usuario.getPersona().getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_JURIDICA)==0){
			usuario.getPersona().setJuridica(new Juridica());
			usuario.getPersona().setNatural(null);
			usuario.getPersona().getListaDocumento().get(0).setIntTipoIdentidadCod(Constante.PARAM_T_TIPODOCUMENTO_RUC);
		}
	}
	
	public void grabarUsuario(ActionEvent event) throws DaoException, ParseException, BusinessException, EJBFactoryException{
	    Boolean bValidar = true;
	    Usuario lUsuario = null;
	    Integer intPersonaUsuario = null;
	    Auditoria lAuditoria = null;
	    AuditoriaFacadeRemote remoteAuditoria = null;
	    try {
			
	    	 bValidar = LoginValidador.validarUsuario(msgUsuario,usuario,Constante.MANTENIMIENTO_GRABAR);
	 	    if(bValidar==true){
	 	    	lUsuario = (Usuario)getRequest().getSession().getAttribute("usuario");
	 	    	if(lUsuario!=null){
	 	    		intPersonaUsuario = lUsuario.getIntPersPersonaPk();
	 	    	}
	 	    	LoginFacadeLocal loginFacade = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
	 	    	loginFacade.grabarUsuarioPersona(usuario,intPersonaUsuario);
	 	    	
	 	    	remoteAuditoria = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
	 			lAuditoria = new Auditoria();
	 			lAuditoria.setStrTabla("SEG_M_USUARIO");
	 			lAuditoria.setStrColumna("USUA_CONTRASEÑA_V");
	 			lAuditoria.setIntEmpresaPk(lUsuario.getPerfil().getId().getIntPersEmpresaPk());
	 			lAuditoria.setIntPersonaPk(intPersonaUsuario);
	 			lAuditoria.setStrLlave1(String.valueOf(usuario.getIntPersPersonaPk()));
	 			lAuditoria.setIntTipo(Constante.PARAM_T_TIPOAUDITORIA_INSERT);
	 			lAuditoria.setStrValornuevo(usuario.getStrContrasena());
	 			remoteAuditoria.grabarAuditoria(lAuditoria);
	 			
	 	    	msgUsuario = null;
	 	    	strTipoMantUsuario = Constante.MANTENIMIENTO_NINGUNO;
	 	    	setMessageSuccess("Los datos se grabaron satisfactoriamente ");
	 	    }
	 	    
		} catch (Exception e) {
			log.error("Error en grabarUsuario ---> "+e);
		}
	   
	}
	
	public void irConsultarUsuario(ActionEvent event){
		LoginFacadeLocal facade = null;
		Integer lIntIdPersona = null;
		try{
			if(strIndexUsuario!=null){
				facade = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
				lIntIdPersona = listaUsuarioComp.get(Integer.parseInt(strIndexUsuario)).getUsuario().getIntPersPersonaPk(); 
				usuario = facade.getUsuarioPersonaPorIdPersona(lIntIdPersona);
				usuario.setPersiste(new Boolean(true));
				getDetalleUsuario(usuario);
			    strTipoMantUsuario = Constante.MANTENIMIENTO_CONSULTAR;
			    limpiarUsuarioMatenimiento();
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void irModificarUsuario(ActionEvent event){
		LoginFacadeLocal facade = null;
		Integer lIntIdPersona = null;
		try{
			if(strIndexUsuario!=null){
				facade = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
				lIntIdPersona = listaUsuarioComp.get(Integer.parseInt(strIndexUsuario)).getUsuario().getIntPersPersonaPk(); 
				usuario = facade.getUsuarioPersonaPorIdPersona(lIntIdPersona);
				getDetalleUsuario(usuario);
			    strTipoMantUsuario = Constante.MANTENIMIENTO_MODIFICAR;
			    strValidarUsuario = Constante.MANTENIMIENTO_NINGUNO;
			    limpiarUsuarioMatenimiento();
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	private void limpiarUsuarioMatenimiento(){
		msgUsuario = new UsuarioMsg();
	    listaEmpresaUsuarioEliminado = null;
	    mapaEliminado = null;
	    personaEliminado = null;
	    listaUbigeoProvincia = null;
	    listaUbigeoDistrito = null;
	}
	
	private void getDetalleUsuario(Usuario usuario) throws EJBFactoryException, BusinessException{
		LoginFacadeLocal facadeLogin = null;
		EmpresaFacadeLocal facadeEmpresa = null;
		GeneralFacadeRemote facadeGral = null;
		EmpresaUsuario lEmpresaUsuario = null;
		UsuarioPerfil lUsuarioPerfil = null;
		UsuarioSucursal lUsuarioSucursal = null;
		UsuarioSubSucursal lUsuarioSubSucursal = null;
		Integer intIdEmpresa = null;
		
		usuario.setPersiste(new Boolean(true));
		if(usuario.getListaEmpresaUsuario()!=null && usuario.getListaEmpresaUsuario().size()>0){
			facadeLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
			facadeEmpresa = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			for(int i=0;i<usuario.getListaEmpresaUsuario().size();i++){
				lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(i);
				lEmpresaUsuario.setPersiste(new Boolean(true));
				intIdEmpresa = lEmpresaUsuario.getId().getIntPersEmpresaPk();
				if(lEmpresaUsuario.getListaUsuarioPerfil()!=null && lEmpresaUsuario.getListaUsuarioPerfil().size()>0){
					for(int j=0;j<lEmpresaUsuario.getListaUsuarioPerfil().size();j++){
						lUsuarioPerfil = lEmpresaUsuario.getListaUsuarioPerfil().get(j);
						lUsuarioPerfil.setPersiste(new Boolean(true));
						if(lUsuarioPerfil.getDtHasta()==null){
							lUsuarioPerfil.setBlnIndeterminado(new Boolean(true));
						}
					}
					lEmpresaUsuario.setListaPerfil(facadeLogin.getListaPerfilPorIdEmpresa(intIdEmpresa));
				}
				
				if(lEmpresaUsuario.getListaUsuarioSucursal()!=null && lEmpresaUsuario.getListaUsuarioSucursal().size()>0){
					for(int j=0;j<lEmpresaUsuario.getListaUsuarioSucursal().size();j++){
						lUsuarioSucursal = lEmpresaUsuario.getListaUsuarioSucursal().get(j);
						lUsuarioSucursal.setPersiste(new Boolean(true));
						if(lUsuarioSucursal.getDtHasta()==null){
							lUsuarioSucursal.setBlnIndeterminado(new Boolean(true));
						}
						lUsuarioSucursal.setListaSubSucursal(facadeEmpresa.getListaSubSucursalPorIdSucursal(lUsuarioSucursal.getId().getIntIdSucursal()));
					}
					lEmpresaUsuario.setListaSucursal(facadeEmpresa.getListaSucursalPorPkEmpresa(intIdEmpresa));
				}
				
				if(lEmpresaUsuario.getListaUsuarioSubSucursal()!=null && lEmpresaUsuario.getListaUsuarioSubSucursal().size()>0){
					for(int j=0;j<lEmpresaUsuario.getListaUsuarioSubSucursal().size();j++){
						lUsuarioSubSucursal = lEmpresaUsuario.getListaUsuarioSubSucursal().get(j);
						lUsuarioSubSucursal.setPersiste(new Boolean(true));
						if(lUsuarioSubSucursal.getDtHasta()==null){
							lUsuarioSubSucursal.setBlnIndeterminado(new Boolean(true));
						}
					}
				}
				
				if(lEmpresaUsuario.getIntControlHoraIngreso()!=null && lEmpresaUsuario.getIntControlHoraIngreso().compareTo(1)==0){
					lEmpresaUsuario.setBlnControlHoraIngreso(new Boolean(true));
				}else{
					lEmpresaUsuario.setBlnControlHoraIngreso(new Boolean(false));
				}
				if(lEmpresaUsuario.getIntCambioClave()!=null && lEmpresaUsuario.getIntCambioClave().compareTo(1)==0){
					lEmpresaUsuario.setBlnCambioClave(new Boolean(true));
				}else{
					lEmpresaUsuario.setBlnCambioClave(new Boolean(false));
				}
				if(lEmpresaUsuario.getIntControlVigenciaClave()!=null && lEmpresaUsuario.getIntControlVigenciaClave().compareTo(1)==0){
					lEmpresaUsuario.setBlnControlVigenciaClave(new Boolean(true));
				}else{
					lEmpresaUsuario.setBlnControlVigenciaClave(new Boolean(false));
				}
				
				if(lEmpresaUsuario.getIntDiasVigencia()!=null){
					lEmpresaUsuario.setIntRadioControlVigenciaClave(new Integer(1));
				}else{
					if(lEmpresaUsuario.getBlnControlVigenciaClave().booleanValue()){
						lEmpresaUsuario.setIntRadioControlVigenciaClave(new Integer(0));
					}else{
						lEmpresaUsuario.setIntRadioControlVigenciaClave(null);
					}
				}
			}
		}
		
		if(usuario.getPersona() != null){
		    if(usuario.getPersona().getListaDomicilio()!=null && usuario.getPersona().getListaDomicilio().size()>0){
		    	chkDireccion = true;
		    	if(listaUbigeoDepartamento == null){
		    		facadeGral = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
		    	    listaUbigeoDepartamento = facadeGral.getListaUbigeoDeDepartamento();
		    	}
		    }else{
		    	chkDireccion = false;
		    }
		    if(usuario.getPersona().getListaComunicacion()!=null && usuario.getPersona().getListaComunicacion().size()>0){
		    	chkComunicacion = true;
		    }else{
		    	chkComunicacion = false;
		    }
		}else{
			chkDireccion = false;
			chkComunicacion = false;
		}
		usuario.setStrContrasenaValidacion(usuario.getStrContrasena());
	}
	
	public void eliminarUsuario(ActionEvent event) throws DaoException{
		LoginFacadeLocal facadeLogin = null;
		Integer lIntIdPersona = null;
		Integer lIntIdPersonaElimina = null;
		Usuario usuario = null;
		try{
			if(strIndexUsuario != null && !strIndexUsuario.trim().equals("")){
				lIntIdPersona = listaUsuarioComp.get(Integer.parseInt(strIndexUsuario)).getUsuario().getIntPersPersonaPk();
				facadeLogin = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
				usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
				if(usuario!= null)
					lIntIdPersonaElimina = usuario.getIntPersPersonaPk();
				facadeLogin.eliminarIntegralUsuarioPersonaPorIdPersona(lIntIdPersona,lIntIdPersonaElimina);
				buscarUsuario(event);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void modificarUsuario(ActionEvent event) throws DaoException, EJBFactoryException, BusinessException{
		EmpresaUsuario lEmpresaUsuario = null; 
		Boolean bValidar = true;
	    Integer intIdEmpresa = null;
	    List<Object> listaDeMapa = null;
	    UsuarioPerfil lUsuarioPerfilLoop = null;
	    UsuarioSucursal lUsuarioSucursalLoop = null;
	    UsuarioSubSucursal lUsuarioSubSucursalLoop = null;
	    Usuario lUsuario = null;
	    Usuario lUsuarioOld = null;
	    Integer intIdPersonaModifica = null;
	    Auditoria lAuditoria = null;
	    AuditoriaFacadeRemote remoteAuditoria = null;
	    bValidar = LoginValidador.validarUsuario(msgUsuario,usuario,Constante.MANTENIMIENTO_MODIFICAR);
	    LoginFacadeLocal loginFacade = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
	    /*lUsuarioOld = loginFacade.getUsuarioPorPk(usuario.getIntPersPersonaPk());
	    if(lUsuarioOld != null && usuario.getStrContrasena()!=null && msgUsuario.getContrasena()==null){
	    	if(lUsuarioOld.getStrContrasena().equals(usuario.getStrContrasena())){
	    		msgUsuario.setContrasena("* La Contraseña no debe ser el mismo que el anterior");
	    		bValidar = false;
	    	}
	    }*/
	    if(bValidar==true){
	    	if(usuario.getListaEmpresaUsuario()==null)
	    		usuario.setListaEmpresaUsuario(new ArrayList<EmpresaUsuario>());
	    	if(listaEmpresaUsuarioEliminado!=null){
	    		usuario.getListaEmpresaUsuario().addAll(listaEmpresaUsuarioEliminado);
	    	}
	    	if(usuario.getListaEmpresaUsuario()!=null && mapaEliminado != null){
		    	for(int i=0;i<usuario.getListaEmpresaUsuario().size();i++){
		    		lEmpresaUsuario = usuario.getListaEmpresaUsuario().get(i);
		    		intIdEmpresa = lEmpresaUsuario.getId().getIntPersEmpresaPk(); 
		    		listaDeMapa = mapaEliminado.get(intIdEmpresa + ".perfil");
		    		if(listaDeMapa != null){
		    			if(lEmpresaUsuario.getListaUsuarioPerfil()==null)
			    			lEmpresaUsuario.setListaUsuarioPerfil(new ArrayList<UsuarioPerfil>());
			    		for(int j=0;j<listaDeMapa.size();j++){
			    			lUsuarioPerfilLoop = (UsuarioPerfil)listaDeMapa.get(j);
				    		if(lUsuarioPerfilLoop.getId().getIntPersEmpresaPk().compareTo(intIdEmpresa)==0){
				    			lEmpresaUsuario.getListaUsuarioPerfil().add(lUsuarioPerfilLoop);
				    			//break;
				    		}
			    		}
		    		}
		    		
		    		listaDeMapa = mapaEliminado.get(intIdEmpresa + ".sucursal");
		    		if(listaDeMapa != null){
		    			if(lEmpresaUsuario.getListaUsuarioSucursal()==null)
			    			lEmpresaUsuario.setListaUsuarioSucursal(new ArrayList<UsuarioSucursal>());
			    		for(int j=0;j<listaDeMapa.size();j++){
			    			lUsuarioSucursalLoop = (UsuarioSucursal)listaDeMapa.get(j);
				    		if(lUsuarioSucursalLoop.getId().getIntPersEmpresaPk().compareTo(intIdEmpresa)==0){
				    			lEmpresaUsuario.getListaUsuarioSucursal().add(lUsuarioSucursalLoop);
				    			//break;
				    		}
			    		}
		    		}
		    		
		    		listaDeMapa = mapaEliminado.get(intIdEmpresa + ".sub-sucursal");
		    		if(listaDeMapa != null){
		    			if(lEmpresaUsuario.getListaUsuarioSubSucursal()==null)
			    			lEmpresaUsuario.setListaUsuarioSubSucursal(new ArrayList<UsuarioSubSucursal>());
			    		for(int j=0;j<listaDeMapa.size();j++){
			    			lUsuarioSubSucursalLoop = (UsuarioSubSucursal)listaDeMapa.get(j);
				    		if(lUsuarioSubSucursalLoop.getId().getIntPersEmpresaPk().compareTo(intIdEmpresa)==0){
				    			lEmpresaUsuario.getListaUsuarioSubSucursal().add(lUsuarioSubSucursalLoop);
				    			//break;
				    		}
			    		}
		    		}
		    	}
	    	}
	    	if(usuario.getPersona().getListaDomicilio()==null)
	    		usuario.getPersona().setListaDomicilio(new ArrayList<Domicilio>());
	    	if(personaEliminado!=null && personaEliminado.getListaDomicilio()!=null){
	    		usuario.getPersona().getListaDomicilio().addAll(personaEliminado.getListaDomicilio());
	    	}
	    	if(usuario.getPersona().getListaComunicacion()==null)
	    		usuario.getPersona().setListaComunicacion(new ArrayList<Comunicacion>());
	    	if(personaEliminado!=null && personaEliminado.getListaComunicacion()!=null){
	    		usuario.getPersona().getListaComunicacion().addAll(personaEliminado.getListaComunicacion());
	    	}
	    	lUsuario = (Usuario)getRequest().getSession().getAttribute("usuario");
	    	if(lUsuario!=null){
	    		intIdPersonaModifica = lUsuario.getIntPersPersonaPk();
	    	}
	    	lUsuarioOld = loginFacade.getUsuarioPorPk(usuario.getIntPersPersonaPk()); 
	    	loginFacade.modificarUsuarioPersona(usuario,intIdPersonaModifica);
	    	setMessageSuccess("Los datos se modificaron satisfactoriamente ");
	    	if(!lUsuarioOld.getStrContrasena().equals(usuario.getStrContrasena())){
		    	remoteAuditoria = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
				lAuditoria = new Auditoria();
				lAuditoria.setStrTabla("SEG_M_USUARIO");
				lAuditoria.setStrColumna("USUA_CONTRASEÑA_V");
				lAuditoria.setIntEmpresaPk(lUsuario.getPerfil().getId().getIntPersEmpresaPk());
				lAuditoria.setIntPersonaPk(intIdPersonaModifica);
				lAuditoria.setStrLlave1(String.valueOf(usuario.getIntPersPersonaPk()));
				lAuditoria.setIntTipo(Constante.PARAM_T_TIPOAUDITORIA_UPDATE);
				lAuditoria.setStrValoranterior(lUsuarioOld.getStrContrasena());
				lAuditoria.setStrValornuevo(usuario.getStrContrasena());
				remoteAuditoria.grabarAuditoria(lAuditoria);
	    	}
	    	msgUsuario = null;
	    	strTipoMantUsuario = Constante.MANTENIMIENTO_NINGUNO;
	    }	
		
	}
	
	public void inicioPerfil(ActionEvent event) {
		filtroPerfil = new Perfil();
		filtroPerfil.setId(new PerfilId());
		filtroPerfil.setBlnIndeterminado(new Boolean(true));
		strTipoMantPerfil = Constante.MANTENIMIENTO_NINGUNO;
		listaMenu = null; 
		listaSelectRadioMenu = null;
		strMsgPerfil = null;
	}
	
	public void onchangeEmpresaDeFiltroPerfil(ActionEvent event) {
		Integer intIdEmpresaSelected = null;
		try{
			intIdEmpresaSelected = filtroPerfil.getId().getIntPersEmpresaPk();
			PermisoFacadeLocal facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
			listaFiltroPerfil = facade.getListaPerfilPorIdEmpresa(intIdEmpresaSelected);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}

	public void onClickIndeterminadoDeFiltroPerfil(ActionEvent event){
	    if(filtroPerfil.getBlnIndeterminado().booleanValue()==true){
	    	filtroPerfil.setBlnVigencia(new Boolean(true));
	    	filtroPerfil.setBlnIndeterminado(new Boolean(false));
	    }else{
	    	filtroPerfil.setBlnVigencia(null);
	    	filtroPerfil.setBlnIndeterminado(new Boolean(true));
	    }
	    filtroPerfil.setDtDesde(null);
    	filtroPerfil.setDtHasta(null);
	}
	
	public void buscarPerfil(ActionEvent event){
		PermisoFacadeLocal facade = null;
		if(filtroPerfil.getId().getIntPersEmpresaPk()==0)
			filtroPerfil.getId().setIntPersEmpresaPk(null);
		if(filtroPerfil.getIntTipoPerfil()==0)
			filtroPerfil.setIntTipoPerfil(null);
		if(filtroPerfil.getIntIdEstado()==0)
			filtroPerfil.setIntIdEstado(null);
		if(filtroPerfil.getId().getIntIdPerfil()==0)
			filtroPerfil.getId().setIntIdPerfil(null);
	    try{
	    	facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
	    	listaBusquedaPerfil = facade.getListaPerfilDeBusqueda(filtroPerfil);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public void seleccionarPerfilDeGrilla(ActionEvent event){
		int indexPerfil = 0;
		Perfil lPerfil = null; 
		if(strIndexPerfil!=null && !strIndexPerfil.trim().equals("")){
			indexPerfil = Integer.parseInt(strIndexPerfil);
			lPerfil = listaBusquedaPerfil.get(indexPerfil);
			strEstadoPerfil = String.valueOf(lPerfil.getIntIdEstado());
		}
	}
	
	public void irConsultarPerfil(ActionEvent event){
		try{
			if(strIndexPerfil!=null){
				irMantenimientoPerfil();
				strTipoMantPerfil = Constante.MANTENIMIENTO_CONSULTAR;
				msgPerfil = new PerfilMsg();
				strMsgPerfil = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void irGrabarPerfil(ActionEvent event){
		perfil = new Perfil();
		perfil.setId(new PerfilId());
		perfil.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
	    strTipoMantPerfil = Constante.MANTENIMIENTO_GRABAR;
		msgPerfil = new PerfilMsg();
		strMsgPerfil = null;
	}
	
	public void irModificarPerfil(ActionEvent event){
		try{
			if(strIndexPerfil!=null){
				irMantenimientoPerfil();
				strTipoMantPerfil = Constante.MANTENIMIENTO_MODIFICAR;
				msgPerfil = new PerfilMsg();
				strMsgPerfil = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	private void irMantenimientoPerfil() throws BusinessException{
		PermisoFacadeLocal facade = null;
		Integer lIntIdEmpresa = null;
		Integer lIntIdPerfil = null;
		PerfilId id = null;
		Transaccion dto = null;
		List<Transaccion> listaSubMenu = null;
		try{
			facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
			lIntIdEmpresa = listaBusquedaPerfil.get(Integer.parseInt(strIndexPerfil)).getId().getIntPersEmpresaPk();
			lIntIdPerfil = listaBusquedaPerfil.get(Integer.parseInt(strIndexPerfil)).getId().getIntIdPerfil();
			id = new PerfilId();
			id.setIntPersEmpresaPk(lIntIdEmpresa);
			id.setIntIdPerfil(lIntIdPerfil);
			perfil = facade.getPerfilYListaPermisoPerfilPorPkPerfil(id);
			
			listaMenu = new ArrayList<List<Transaccion>>();
			listaSelectRadioMenu = new ArrayList<String>();
			listaSubMenu = facade.getMenuPorIdEmpresa(lIntIdEmpresa); 
			listaMenu.add(listaSubMenu);
			listaSelectRadioMenu.add("0");
			 
			for(int i=0;i<listaSubMenu.size();i++){
				dto = listaSubMenu.get(i);
				if(dto.getPermisoPerfil()==null){
					asignarPermisoATransaccion(dto,perfil.getListaPermisoPerfil());
				}
			}
			dto = listaSubMenu.get(0);
			if(dto.getListaTransaccion()!= null && dto.getListaTransaccion().size()>0){
				agregarListaSubMenuPorNivel(dto.getListaTransaccion(),perfil.getListaPermisoPerfil());
			}
		}catch(EJBFactoryException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}
	}
	
	public void eliminarPerfil(ActionEvent event){
		int indexPerfil = 0;
		PermisoFacadeLocal facade = null;
		Perfil lPerfil = null; 
		try{
				if(strIndexPerfil!=null && !strIndexPerfil.trim().equals("")){
					facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
					indexPerfil = Integer.parseInt(strIndexPerfil);
					lPerfil = listaBusquedaPerfil.get(indexPerfil);
					strEstadoPerfil = String.valueOf(lPerfil.getIntIdEstado());
					lPerfil.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					facade.modificarPerfil(lPerfil);
			    	msgPerfil = null;
			    	strTipoMantPerfil = Constante.MANTENIMIENTO_NINGUNO;
			    	strEstadoPerfil = null;
			    	buscarPerfil(event);
			    	strMsgPerfil = "Los datos se eliminaron satisfactoriamente ";
				}else{
					strMsgPerfil = "No se Eligió un Perfil";
				}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void grabarPerfil(ActionEvent event){
		Boolean bValidar = true;
		Usuario lUsuario = null;
		try{
		    bValidar = PerfilValidador.validarPerfil(msgPerfil,perfil);
		    if(bValidar==true){
		    	PermisoFacadeLocal facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
		    	lUsuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		    	if(lUsuario != null)
		    		perfil.setIntPersPersonaUsuarioPk(lUsuario.getIntPersPersonaPk());
		    	facade.grabarPerfilYPermiso(perfil);
		    	msgPerfil = null;
		    	strTipoMantPerfil = Constante.MANTENIMIENTO_NINGUNO;
		    	buscarPerfil(event);
		    	strEstadoPerfil = null;
		    	strMsgPerfil = "Los datos se grabaron satisfactoriamente ";
		    }
		}catch(Exception e){
			log.error("Error en grabarPerfil ---> "+e);
			e.printStackTrace();
		}
	}
	
	public void modificarPerfil(ActionEvent event){
		Boolean bValidar = true;
		try{
		    bValidar = PerfilValidador.validarPerfil(msgPerfil,perfil);
		    if(bValidar==true){
		    	PermisoFacadeLocal facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
		    	facade.modificarPerfilYPermiso(perfil);
		    	msgPerfil = null;
		    	strTipoMantPerfil = Constante.MANTENIMIENTO_NINGUNO;
		    	buscarPerfil(event);
		    	strEstadoPerfil = null;
		    	strMsgPerfil = "Los datos se grabaron satisfactoriamente ";
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void cancelarPerfil(ActionEvent event){
		perfil = null;
		strTipoMantPerfil = Constante.MANTENIMIENTO_NINGUNO;
		msgPerfil = null;
		listaMenu = null; 
		listaSelectRadioMenu = null;
		strMsgPerfil = null;
	}
	
	public void onchangeEmpresaDeMantenimiento(ActionEvent event){
		PermisoFacadeLocal facade = null;
		Transaccion dto = null;
		List<Transaccion> listaSubMenu = null;
		try{
			facade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
			listaMenu = new ArrayList<List<Transaccion>>();
			listaSelectRadioMenu = new ArrayList<String>();
			listaSubMenu = facade.getMenuPorIdEmpresa(perfil.getId().getIntPersEmpresaPk());
			listaMenu.add(listaSubMenu);
			listaSelectRadioMenu.add("0");
			dto = listaSubMenu.get(0); 
			if(dto.getListaTransaccion()!= null && dto.getListaTransaccion().size()>0){
				agregarListaSubMenuPorNivel(dto.getListaTransaccion(),null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void asignarPermisoATransaccion(Transaccion transaccion,List<PermisoPerfil> listaPermiso){
		PermisoPerfil permiso = null;
		int indice = 0;
		if(listaPermiso != null && listaPermiso.size()>0){
			for(indice=0;indice<listaPermiso.size();indice++){
				permiso = listaPermiso.get(indice);
				if(transaccion.getId().getIntPersEmpresaPk().compareTo(permiso.getId().getIntPersEmpresaPk()) ==0 &&
				   transaccion.getId().getIntIdTransaccion().compareTo(permiso.getId().getIntIdTransaccion())==0){
					transaccion.setPermisoPerfil(permiso);
					break;
				}
			}
		}
	}
	
	private void agregarListaSubMenuPorNivel(List<Transaccion> listaTransaccion,List<PermisoPerfil> listaPermiso){
		Transaccion lTransaccion = null;
		Transaccion lTransaccionLoop = null;
		if(listaTransaccion.size()>0){
			listaMenu.add(listaTransaccion);
			listaSelectRadioMenu.add("0");
			lTransaccion = listaTransaccion.get(0);
			for(int i=0;i<listaTransaccion.size();i++){
				lTransaccionLoop = listaTransaccion.get(i);
				if(lTransaccionLoop.getPermisoPerfil()==null){
					asignarPermisoATransaccion(lTransaccionLoop,listaPermiso);
				}
			}
			if(lTransaccion.getListaTransaccion() !=null){
				if(contieneActivo(lTransaccion.getListaTransaccion())){
					agregarListaSubMenuPorNivel(lTransaccion.getListaTransaccion(),listaPermiso);
				}
			}	
		}
	}
	
	private boolean contieneActivo(List<Transaccion> listaTransaccion){
		boolean contieneActivo = false;
		Transaccion lSubMenu = null;
		for(int i=0;i<listaTransaccion.size();i++){
			lSubMenu = listaTransaccion.get(i);
			if(lSubMenu.getIntIdEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
				contieneActivo = true;
				break;
			}
		}
		return contieneActivo;
	}
	
	public void onclickRadioDeSubMenu(ActionEvent event){
		int lIndiceMenu = 0;
		int lIndiceSubMenu = 0;
		List<Transaccion> listaSubMenu = null;
		Transaccion lSubMenu = null;
		String strIndiceMenu = null;
		String strIndiceSubMenu = null;
		try{
			strIndiceMenu = getRequestParameter("pIndiceMenu");
			strIndiceSubMenu = getRequestParameter("pIndiceSubMenu");
			if(strIndiceMenu != null && strIndiceSubMenu != null){
				lIndiceMenu = Integer.parseInt(strIndiceMenu);
				lIndiceSubMenu = Integer.parseInt(strIndiceSubMenu);
				listaSubMenu = listaMenu.get(lIndiceMenu);
				lSubMenu = listaSubMenu.get(lIndiceSubMenu);
				limpiarListaSubMenuPorNivelActual(lIndiceMenu);
				if(lSubMenu.getListaTransaccion()!=null){
					if(contieneActivo(lSubMenu.getListaTransaccion())){
						agregarListaSubMenuPorNivel(lSubMenu.getListaTransaccion(),perfil.getListaPermisoPerfil());
					}
				}
			}
		}catch(Exception e){
			log.error(e);
		}
	}
	
	private void limpiarListaSubMenuPorNivelActual(int pNivelActual) throws Exception{
		int i = 0;
		try{
			i=pNivelActual+1;
			if(i<listaMenu.size()){
				do{
					listaMenu.remove(i);
					listaSelectRadioMenu.remove(i);
				}while(i<listaMenu.size());
			}
		}catch(Exception e){
			throw e;
		}
	}

	public void irPermiso(ActionEvent event){
		Integer lIndiceMenu = null;
		Integer lIndicesubMenu = null;
		String pStrValor = null;
		List<Transaccion> listaSubMenu = null;
		Transaccion subMenu = null;
		
		pStrValor = getRequestParameter("pIndiceMenu");
		lIndiceMenu = new Integer(pStrValor);
		pStrValor = listaSelectRadioMenu.get(lIndiceMenu.intValue());
		lIndicesubMenu = new Integer(pStrValor);
		
		listaSubMenu = listaMenu.get(lIndiceMenu.intValue());
		subMenu = listaSubMenu.get(lIndicesubMenu.intValue());
		//TODO controlar que sea un objeto persistente y no en memoria
		if(subMenu.getPermisoPerfil()==null){	
			permiso = new PermisoPerfil();
			permiso.setId(new PermisoPerfilId());
			permiso.setTransaccion(subMenu);
		}else{
			permiso = subMenu.getPermisoPerfil();
		}
		permiso.getId().setIntPersEmpresaPk(subMenu.getId().getIntPersEmpresaPk());
		permiso.getId().setIntIdTransaccion(subMenu.getId().getIntIdTransaccion());
		msgPerfil.setMsgPermiso(new PermisoMsg());
	}
	
	public void validarPermiso(ActionEvent event){
		esPopPupValido = PerfilValidador.validarPermiso(msgPerfil, permiso);
	}

	public void agregarPermiso(ActionEvent event){
		try{
			if(permiso.getTransaccion().getPermisoPerfil() == null){
				permiso.getTransaccion().setPermisoPerfil(permiso);
				if(perfil.getListaPermisoPerfil()==null)
					perfil.setListaPermisoPerfil(new ArrayList<PermisoPerfil>());
				perfil.getListaPermisoPerfil().add(permiso);
				asignarPermisoPadre(permiso.getTransaccion());
			}
			cerrarPermiso();
		}catch(Exception e){
			log.error(e);
		}
	}
	
	private void asignarPermisoPadre(Transaccion pSubMenu){
		PermisoPerfil lPermiso = null;
		List<Transaccion> listaSubMenuPadre = null;
		Transaccion pSubMenuPadre = null;
		String pStrValor = null;
	    int lIndiceSubMenuPadre = 0;

		if(pSubMenu.getIntNivel() > 1){
			listaSubMenuPadre = listaMenu.get(pSubMenu.getIntNivel().intValue()-2);
			pStrValor = listaSelectRadioMenu.get(pSubMenu.getIntNivel().intValue()-2);
			lIndiceSubMenuPadre = Integer.parseInt(pStrValor);
			pSubMenuPadre = listaSubMenuPadre.get(lIndiceSubMenuPadre);
			if(pSubMenuPadre.getPermisoPerfil()== null){
				lPermiso = new PermisoPerfil();
				lPermiso.setId(new PermisoPerfilId());
				lPermiso.getId().setIntPersEmpresaPk(pSubMenuPadre.getId().getIntPersEmpresaPk());
				lPermiso.getId().setIntIdTransaccion(pSubMenuPadre.getId().getIntIdTransaccion());
				lPermiso.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				perfil.getListaPermisoPerfil().add(lPermiso);
				pSubMenuPadre.setPermisoPerfil(lPermiso);
				asignarPermisoPadre(pSubMenuPadre);
			}
		}
	}
	
	public void modificarPermiso(ActionEvent event){
		List<Transaccion> listaSubMenu = null;
		Integer lIntIdNivel = null;
		int lIndiceMenuPadre = 0;
		int lIndiceSubMenuPadre = 0;
		List<Transaccion> listaSubMenuPadre = null;
		Transaccion lSubMenuPadre = null;
		try{
			cerrarPermiso();
		}catch(Exception e){
			log.error(e);
		}
	}
	
	public void cancelarPermiso(ActionEvent event){
		cerrarPermiso();
	}
	
	private void cerrarPermiso(){
		permiso = null;
		msgPerfil.setMsgPermiso(null);
		esPopPupValido = new Boolean(false);
	}
	
	public void listarPermisos(ActionEvent event){
	    setService(usuarioPerfilService);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		Date daFecIni = getDaFechaIniPermiso();
	    String daFechaIni = (daFecIni == null ? "" : sdf2.format(daFecIni));
	    Date daFecFin = getDaFechaFinPermiso();
	    String daFechaFin = (daFecFin == null ? "" : sdf2.format(daFecFin));
	    
	    HashMap prmtBusqPermiso = new HashMap();
		prmtBusqPermiso.put("pIntIdUsuario", 			null);
		prmtBusqPermiso.put("pIntIdEmpresa", 			getCboEmpresaPermiso()==0?null:getCboEmpresaPermiso());
		prmtBusqPermiso.put("pCboTipoPerfilPermiso", 	(getCboTipoPerfilPermiso()==0? null:getCboTipoPerfilPermiso()));
		prmtBusqPermiso.put("pCboEstadoPerfilPermiso", 	(getCboEstadoPerfilPermiso()==0?null:getCboEstadoPerfilPermiso()));
		prmtBusqPermiso.put("pTxtUsuario", 				getTxtUsuarioPermiso());
		prmtBusqPermiso.put("pDaFecIni", 				daFechaIni);
		prmtBusqPermiso.put("pDaFecFin", 				daFechaFin);
		
	    ArrayList arrayPermiso = new ArrayList();
	    ArrayList listaPermiso = new ArrayList();
	    try {
	    	arrayPermiso = getService().listarPermisos(prmtBusqPermiso);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarPermisos() " + e.getMessage());
			e.printStackTrace();
		}
		log.info("arraySucursal.size()"+arrayPermiso.size());
		setMessageSuccess("Listado de Permisos ha sido satisfactorio");
		
		for(int i=0; i<arrayPermiso.size(); i++){
			HashMap hash = (HashMap) arrayPermiso.get(i);
			Permiso per = new Permiso();
			int intIdPersona = Integer.parseInt("" + hash.get("PERS_IDPERSONA_N"));
			per.setIntIdPersona(intIdPersona);
			int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
			per.setIntIdEmpresa(intIdEmpresa);
			String strUsuario = "" + hash.get("USUA_USUARIO_V");
			per.setStrUsuario(strUsuario);
			String strNombreCompleto = "" + hash.get("V_NOMBRECOMPLETO");
			per.setStrNombreCompleto(strNombreCompleto);
			String strEmpresa = "" + hash.get("V_EMPRESA");
			per.setStrEmpresa(strEmpresa);
			String strDescPerfil = "" + hash.get("V_DESCRIPCION_PERF");
			per.setStrDescPerfil(strDescPerfil);
			String strFecRegistro = "" + hash.get("USUA_FECHAREGISTRO_D");
			per.setDaFecRegistro(strFecRegistro);			
			String strEstado = "" + hash.get("V_ESTADO");
			per.setStrEstado(strEstado);
			String strFecIni = "" + (hash.get("USPE_DESDE_D")==null?"":hash.get("USPE_DESDE_D"));
			String strFecFin = "" + (hash.get("USPE_HASTA_D")==null?"":hash.get("USPE_HASTA_D"));
			per.setStrRanFecha(strFecIni + " - " + strFecFin);
			
			listaPermiso.add(per);
		}
		setBeanListPermisos(listaPermiso);
	}
	
	public void reloadMenuesUsuario(ValueChangeEvent event) throws DaoException {
		setService(usuarioPerfilService);
		Permiso perm = new Permiso();
	    perm = (Permiso) getBeanPermiso();
		//String idCboPerfil = (event==null? "0":((String)event.getNewValue()));
		int idCboPerfil = perm.getIntIdPerfil();
		//String idPerfil = ((getStrCboPerfil()==null||getStrCboPerfil().equals(""))? ""+perm.getIntIdPerfil():getStrCboPerfil());
		String idPerfil = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:hiddenIdPerfilPerm");
		String idPersona = ((getStrCboUsuario()==null||getStrCboUsuario().equals(""))? ""+perm.getIntIdPersona():getStrCboUsuario());
		String idEmpresa = ((getStrCboEmpresaPermiso()==null||getStrCboEmpresaPermiso().equals(""))? ""+perm.getIntIdEmpresa():getStrCboEmpresaPermiso());
		if(idPerfil==null){
			idPerfil = ""+codPerfil;
		}
		HashMap prmtBusqPermiso = new HashMap();
		HashMap prmtBusqPermiso1 = new HashMap();
		if(idPerfil==null){
			idPerfil = ""+idCboPerfil; 
		}
		prmtBusqPermiso.put("pIntIdTipo",  1);
		prmtBusqPermiso.put("pIntIdPerfil",  Integer.parseInt(idPerfil));
		prmtBusqPermiso.put("pIntIdEmpresa", Integer.parseInt(idEmpresa));
		prmtBusqPermiso.put("pIntIdPersona", Integer.parseInt(idPersona));
	    
		ArrayList arrayAdminMenu = new ArrayList();
		ArrayList arrayAdminMenu1 = new ArrayList();
	    ArrayList listaAdminMenu = new ArrayList();
	    ArrayList arrayMenu1 = new ArrayList();
	    ArrayList arrayMenu2 = new ArrayList();
	    ArrayList arrayMenu3 = new ArrayList();
	    ArrayList arrayMenu4 = new ArrayList();
	    ArrayList arrayMenu5 = new ArrayList();
	    try {
	    	arrayAdminMenu = getService().listarMenuesPermiso(prmtBusqPermiso);
		} catch (DaoException e) {
			log.info("ERROR  getService().listarMenuesPermiso() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<arrayAdminMenu.size(); i++){
			HashMap hash = (HashMap) arrayAdminMenu.get(i);
			MenuPermiso menu = new MenuPermiso();
			int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
			menu.setIntIdEmpresa(intIdEmpresa);
			String strIdTransaccion = "" + hash.get("TRAN_IDTRANSACCION_C");
			menu.setStrIdTransaccion(strIdTransaccion);
			if(hash.get("TRAN_IDTRANSACCIONPADRE_C")!=null){
				String strIdTransaccionPadre = "" + hash.get("TRAN_IDTRANSACCIONPADRE_C");
				menu.setStrIdTransaccionPadre(strIdTransaccionPadre);
			}
			int intMenuOrden = 0;
			if(hash.get("TRAN_NIVEL_N")!=null){
				intMenuOrden = Integer.parseInt("" + hash.get("TRAN_NIVEL_N"));
				menu.setIntNivel(intMenuOrden);
			}
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			if(intMenuOrden==1){
				menu.setStrNombreM1(strNombre);
				arrayMenu1.add(menu);
			}else if(intMenuOrden==2){
				menu.setStrNombreM2(strNombre);
				arrayMenu2.add(menu);
			}else if(intMenuOrden==3){
				menu.setStrNombreM3(strNombre);
				arrayMenu3.add(menu);
			}else if(intMenuOrden==4){
				menu.setStrNombreM4(strNombre);
				arrayMenu4.add(menu);
			}
			int intIdEstado = Integer.parseInt("" + hash.get("TRAN_IDESTADO_N"));
			Boolean chkEstado = (intIdEstado == 1 ? true : false);
			menu.setChkPerfil(chkEstado);
		}
		
		prmtBusqPermiso1.put("pIntIdTipo",  0);
		prmtBusqPermiso1.put("pIntIdPerfil",  Integer.parseInt(idPerfil));
		prmtBusqPermiso1.put("pIntIdEmpresa", Integer.parseInt(idEmpresa));
		prmtBusqPermiso1.put("pIntIdPersona", Integer.parseInt(idPersona));
		arrayAdminMenu1 = getService().listarMenuesPermiso(prmtBusqPermiso1);
		for(int i=0; i<arrayAdminMenu1.size(); i++){
			HashMap hash = (HashMap) arrayAdminMenu1.get(i);
			MenuPermiso menu1 = new MenuPermiso();
			String strNombre = "" + hash.get("TRAN_NOMBRE_V");
			menu1.setStrNombreM1(strNombre);
			arrayMenu5.add(menu1);
		}
		
		for(int i=0; i<arrayMenu1.size(); i++){
			MenuPermiso m1 = new MenuPermiso();
			m1=(MenuPermiso)arrayMenu1.get(i);
			log.info("m1.getStrNombreM1() = "+m1.getStrNombreM1());			
			for(int m=0; m<arrayMenu5.size(); m++){
				MenuPermiso n1 = new MenuPermiso();
				n1=(MenuPermiso)arrayMenu5.get(m);
				if(n1.getStrNombreM1().equals(m1.getStrNombreM1())){
					m1.setChkPerfil(true);
				}
			}
			listaAdminMenu.add(m1);
			for(int j=0; j<arrayMenu2.size(); j++){
				MenuPermiso m2 = new MenuPermiso();
				m2=(MenuPermiso)arrayMenu2.get(j);
				if(m2.getStrIdTransaccionPadre().equals(m1.getStrIdTransaccion())){
					log.info("m2.getStrNombreM2() = "+m2.getStrNombreM2());
					for(int n=0; n<arrayMenu5.size(); n++){
						MenuPermiso n2 = new MenuPermiso();
						n2=(MenuPermiso)arrayMenu5.get(n);
						if(n2.getStrNombreM1().equals(m2.getStrNombreM2())){
							m2.setChkPerfil(true);
						}
					}
					listaAdminMenu.add(m2);
					for(int k=0; k<arrayMenu3.size(); k++){
						MenuPermiso m3 = new MenuPermiso();
						m3=(MenuPermiso)arrayMenu3.get(k);
						if(m3.getStrIdTransaccionPadre().equals(m2.getStrIdTransaccion())){
							log.info("m3.getStrNombreM3() = "+m3.getStrNombreM3());
							for(int p=0; p<arrayMenu5.size(); p++){
								MenuPermiso n3 = new MenuPermiso();
								n3=(MenuPermiso)arrayMenu5.get(p);
								if(n3.getStrNombreM1().equals(m3.getStrNombreM3())){
									m3.setChkPerfil(true);
								}
							}
							listaAdminMenu.add(m3);
							for(int l=0; l<arrayMenu4.size(); l++){
								MenuPermiso m4 = new MenuPermiso();
								m4=(MenuPermiso)arrayMenu4.get(l);
								if(m4.getStrIdTransaccionPadre().equals(m3.getStrIdTransaccion())){
									log.info("m4.getStrNombreM4() = "+m4.getStrNombreM4());
									for(int q=0; q<arrayMenu5.size(); q++){
										MenuPermiso n4 = new MenuPermiso();
										n4=(MenuPermiso)arrayMenu5.get(q);
										if(n4.getStrNombreM1().equals(m4.getStrNombreM4())){
											m4.setChkPerfil(true);
										}
									}
									listaAdminMenu.add(m4);
								}
							}
						}
					}
				}
			}
		}
		setBeanListMenuesPermisos(listaAdminMenu);
	}
	
	public void agregaMenuPermiso(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging UsuarioPerfilController.agregaMenuPermiso-----------------------------");
	    setService(usuarioPerfilService);
	    log.info("Se ha seteado el Service");
	    HashMap prmtBusq = new HashMap();
		log.info("event.getComponent(): "+ event.getComponent());
		UIComponent uiComponent = event.getComponent();
		String btnCbo = uiComponent.getId();
		String cboVal="";
		log.info("btnCbo: "+btnCbo);
		
		if(btnCbo.equals("btnAgregaMenuPermiso1")){
			cboVal = getStrCboMenuPermiso01();
		}else if(btnCbo.equals("btnAgregaMenuPermiso2")){
			cboVal = getStrCboMenuPermiso02();
		}else if(btnCbo.equals("btnAgregaMenuPermiso3")){
			cboVal = getStrCboMenuPermiso03();
		}else{
			cboVal = getStrCboMenuPermiso04();
		}
		
		List arrayExiste = getBeanListMenuesPermisos();
		
		ArrayList arr2 = new ArrayList();
		String codigo ="";
		for(int i = cboVal.length()-1; i>=0; i--){
			char c = cboVal.charAt(i);
			if(!(c+"").equals("0")){
				cboVal = cboVal.substring(0,i+1);
				break;
			}
		}
		log.info("cboVal: "+cboVal);
		for(int i = 0; i< arrayExiste.size(); i++){
			AdminMenu m1 = new AdminMenu();
			m1=(AdminMenu)arrayExiste.get(i);
			log.info("m1.getStrIdTransaccion(): "+m1.getStrIdTransaccion());
			if((m1.getStrIdTransaccion()).contains(cboVal) ){
				m1.setChkPerfil(true);
			}
			log.info("codigo: "+codigo);
			arr2.add(m1);
		}
		setBeanListMenuesPermisos(arr2);
	}
	
	public void grabarPermiso(ActionEvent event){
		log.info("-----------------------Debugging UsuarioController.grabarUsuario-----------------------------");
	    setService(usuarioPerfilService);
	    
	    log.info("Se ha seteado el Service");
	    Permiso perm = new Permiso();
	    perm = (Permiso) getBeanPermiso();
	    
	    Date daFecIni = getDaFechaPermisoIni();
	    String daFechaIni = (daFecIni == null ? "" : sdf.format(daFecIni));
	    perm.setDaFecIni(daFechaIni);
	      
	    Date daFecFin = getDaFechaPermisoFin();
	    String daFechaFin = (daFecFin == null ? "" : sdf.format(daFecFin));
	    perm.setDaFecFin(daFechaFin);
	    
	    Boolean bValidar = true;
	    
	    int intIdEmpresa = perm.getIntIdEmpresa();
	      if(intIdEmpresa==0){
	    	  setMsgTxtEmpresaPermiso("* Debe seleccionar una Empresa.");
	    	  bValidar = false;
	      }else{
	    	  setMsgTxtEmpresaPermiso("");
	      }
	      int intIdPersona = perm.getIntIdPersona();
	      if(intIdPersona == 0){
	    	  setMsgTxtUsuarioPermiso("* Debe seleccionar el Usuario.");
	    	  bValidar = false;
	      } else{
	    	  setMsgTxtUsuarioPermiso("");
	      }
	      int intIdPerfil = perm.getIntIdPerfil();
	      if(intIdPerfil == 0){
	    	  setMsgCboTipoPerfilPermiso("* Debe seleccionar el Perfil.");
	    	  bValidar = false;
	      } else{
	    	  setMsgCboTipoPerfilPermiso("");
	      }
	      int intIdEstado = perm.getIntIdEstado();
	      if(intIdEstado == 0){
	    	  setMsgCboEstadoPermiso("* Debe seleccionar el Estado.");
	    	  bValidar = false;
	      } else{
	    	  setMsgCboEstadoPermiso("");
	      }
	      if(daFecIni.compareTo(daFecFin) > 0){
	    	  setMsgTxtRanFecPermiso("Fecha inicio no debe ser mayor que fecha fin");
	    	  bValidar = false;
	      } else{
	    	  setMsgTxtRanFecPermiso("");
	      }	      
	      
	      
	    if(bValidar==true){
		    try {
		    	//Elimino el detalle de los Permisos
		    	HashMap prmtPerm = new HashMap();
		    	String idPersona = ((getStrCboUsuario()==null||getStrCboUsuario().equals(""))? ""+perm.getIntIdPersona():getStrCboUsuario());
		    	String idEmpresa = ((getStrCboEmpresaPermiso()==null||getStrCboEmpresaPermiso().equals(""))? ""+perm.getIntIdEmpresa():getStrCboEmpresaPermiso());
		    	String idPerfil = ((getStrCboPerfilPermiso()==null||getStrCboPerfilPermiso().equals(""))? ""+perm.getIntIdPerfil():getStrCboPerfilPermiso());
		    	
		    	prmtPerm.put("pIntIdEmpresa", Integer.parseInt(idEmpresa));
		    	prmtPerm.put("pIntIdPersona", Integer.parseInt(idPersona));
		    	getService().eliminarPermiso(prmtPerm);
		    	
		    	for(int i = 0; i<getBeanListMenuesPermisos().size(); i++){
		    		AdminMenu adm = (AdminMenu)getBeanListMenuesPermisos().get(i);
		  	    	//adm.setIntIdEmpresa(Integer.parseInt(getStrCboEmpresaPermiso()));
		  	    	//adm.setIntIdPersona(Integer.parseInt(idPersona));
		    		adm.setIntIdEmpresa(Integer.parseInt(idEmpresa));
		    		adm.setIntIdPersona(Integer.parseInt(idPersona));
		    		adm.setIntIdPerfil(Integer.parseInt(idPerfil));
		  	    	adm.setStrIdTransaccion(adm.getStrIdTransaccion()==null?"":adm.getStrIdTransaccion());
		  	    	if(adm.getChkPerfil()==true){
		  	    		//Date daFecIni = getDaFechaPermisoIni();
					    //String daFechaIni = (daFecIni == null ? "" : sdf.format(daFecIni));
					    adm.setDaFecIni(daFechaIni);
					    //Date daFecFin = getDaFechaPermisoFin();
					    //String daFechaFin = (daFecFin == null ? "" : sdf.format(daFecFin));
					    adm.setDaFecFin(daFechaFin);
					    getService().grabarPermiso(adm);
		  	    	}
		  	    	
		  	    	//getService().grabarPermiso(adm);
		  	    }
				
			} catch (DaoException e) {
				log.info("ERROR  getService().grabarPermiso(adm:) " + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	/*public void modificarPermiso(ActionEvent event) throws DaoException, ParseException{
		String strPersonaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPermModalPanel:hiddenIdPersonaPerm");
		String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPermModalPanel:hiddenIdEmpresaPerm");
		setService(usuarioPerfilService);
		
		int idPersona = Integer.parseInt(strPersonaId);
		int idEmpresa = Integer.parseInt(strEmpresaId);
		
		ArrayList arrayPermiso = new ArrayList();
		HashMap prmtBusqPermiso  = new HashMap();
		prmtBusqPermiso.put("pIntIdUsuario", idPersona);
		prmtBusqPermiso.put("pIntIdEmpresa", idEmpresa);
		
		arrayPermiso = getService().listarPermisos(prmtBusqPermiso);
		HashMap hash = (HashMap) arrayPermiso.get(0);
		
		Permiso per = new Permiso();
		int intIdPersona = Integer.parseInt("" + hash.get("PERS_IDPERSONA_N"));
		per.setIntIdPersona(intIdPersona);
		setStrCboUsuario(""+intIdPersona);
		int intIdEmpresa = Integer.parseInt("" + hash.get("EMPR_IDEMPRESA_N"));
		per.setIntIdEmpresa(intIdEmpresa);
		setStrCboEmpresaPermiso(""+intIdEmpresa);
		
//		String strUsuario = "" + hash.get("USUA_USUARIO_V");
//		per.setStrUsuario(strUsuario);		
//		String strNombreCompleto = "" + hash.get("V_NOMBRECOMPLETO");
//		per.setStrNombreCompleto(strNombreCompleto);		
//		String strDescPerfil = "" + hash.get("V_DESCRIPCION_PERF");
//		per.setStrDescPerfil(strDescPerfil);
//		String strEstado = "" + hash.get("V_ESTADO");
//		per.setStrEstado(strEstado);
		
		int intIdPerfil = Integer.parseInt("" + hash.get("PERF_IDPERFIL_N"));
		per.setIntIdPerfil(intIdPerfil);
		codPerfil = intIdPerfil;
		int intIdEstado = Integer.parseInt("" + hash.get("USPE_IDESTADO_N"));
		per.setIntIdEstado(intIdEstado);
		String strFecIni = "" + (hash.get("USPE_DESDE_D")==null?"":hash.get("USPE_DESDE_D"));
		Date fechaIni = (strFecIni.equals("") ? null : sdf.parse(strFecIni));
		setDaFechaPermisoIni(fechaIni);
		per.setDaFecIni(strFecIni);
		String strFecFin = "" + (hash.get("USPE_HASTA_D")==null?"":hash.get("USPE_HASTA_D"));
		Date fechaFin = (strFecFin.equals("") ? null : sdf.parse(strFecFin));
		setDaFechaPermisoFin(fechaFin);
		per.setDaFecFin(strFecFin);
		
		if(strFecIni.equals("") && strFecFin.equals(""))
			per.setChkRanFecha(false);
		else per.setChkRanFecha(true);
		
		setBeanPermiso(per);
	    setPermisoRendered(true);
//	    setFormPermisoEnabled(false);
//		habilitarActualizarPermiso(event);
		ValueChangeEvent event2 = null;
		ValueChangeEvent event3 = null;
		ValueChangeEvent event4 = null;
		reloadMenuesUsuario(event2);
		reloadCboUsuario(event3);
		reloadCboPerfilUsuario(event4);
		
		log.info("Datos de Permiso ("+hash.get("PERS_IDPERSONA_N")+") cargados exitosamente.");
	}*/
	
	public void reloadCboUsuario(ValueChangeEvent event) throws DaoException {
		setService(usuarioPerfilService);
		int idCboEmpresa = 0;
		if(event!=null){
			idCboEmpresa = Integer.parseInt(""+event.getNewValue());
		}		
		
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
        String cboId= "";
        if(event!=null){
	        UIComponent uiComponent = event.getComponent();
			log.info("uiComponent = "+uiComponent.getId());
			cboId=uiComponent.getId();
        }
		
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
	
	public void reloadCboPerfilUsuario(ValueChangeEvent event) throws DaoException {
		log.info("-----------------------Debugging EmpresaController.reloadCboPerfilUsuario()-----------------------------");
		setService(usuarioPerfilService);
		//String idCboUsuario = (String)event.getNewValue();
		String idCboUsuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:idCboUsuario");
		log.info("idCboUsuario() = "+idCboUsuario);
		if(idCboUsuario==null){
			idCboUsuario = "0";
		}

		//this.cboPerfilUsuario.clear();
		HashMap prmtBusqPerfil = new HashMap();
		prmtBusqPerfil.put("pIntIdUsuario", Integer.parseInt(idCboUsuario));
		prmtBusqPerfil.put("pIntIdEmpresa", Integer.parseInt(getHiddenIdEmpresa()));
	    
		//ArrayList arrayPerfiles = new ArrayList();
		listPerfilUsuario = getService().listarPerfilesxUsuario(prmtBusqPerfil);
	    log.info("listPerfilUsuario.size(): " + listPerfilUsuario.size());
		
		/*
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
		log.info("Saliendo de UsuarioPerfilController.reloadCboPerfilUsuario()...");*/
	}
	
	public void eliminarPermiso(ActionEvent event) throws DaoException{
    	log.info("-----------------------Debugging UsuarioController.eliminarPerfil-----------------------------");
    	String strEmpresaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPermModalPanel:hiddenIdEmpresaPerm");
    	String strPersonaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPermModalPanel:hiddenIdPersonaPerm");
		log.info("strEmpresaId:" 		+ strEmpresaId);
		log.info("strPersonaId:" 		+ strPersonaId);
		
		HashMap prmtPermiso = new HashMap();
		prmtPermiso.put("pIntIdEmpresa", Integer.parseInt(strEmpresaId));
		prmtPermiso.put("pIntIdPersona", Integer.parseInt(strPersonaId));
		getService().deletePermiso(prmtPermiso);
		log.info("Se ha eliminado el Permiso.");
		listarPermisos(event);
    }
	
	public void showFecFin(ValueChangeEvent event) throws DaoException{
		log.info("----------------Debugging EmpresaController.showFecFin-------------------");
		UIComponent uiComponent = event.getComponent();
		log.info("uiComponent = "+uiComponent.getId());
		String cboId=uiComponent.getId();
		log.info("cboId: "+cboId);
		setService(usuarioPerfilService);
		PhaseId id = event.getPhaseId();
		
		if(cboId.equals("radPerfUsu")){
			String idPerfUsu = (String)event.getNewValue();
			if(idPerfUsu != null){
				setFecFinPerfUsuRendered(idPerfUsu.equals("2"));
				setDaFechaFinPerfUsu(idPerfUsu.equals("2")?null:daFechaFinPerfUsu);
			}			
		}else if(cboId.equals("rbSucUsu")){
			String idSucUsu = (String)event.getNewValue();
			if(idSucUsu != null){
				setFecFinSucUsuRendered(idSucUsu.equals("2"));
				setDaFechaFinSucUsu(idSucUsu.equals("2")?null:daFechaFinSucUsu);
			}			
		}else if(cboId.equals("rbSubSucUsu")){
			String idSubSucUsu = (String)event.getNewValue();
			if(idSubSucUsu != null){
				setFecFinSubSucUsuRendered(idSubSucUsu.equals("2"));
				setDaFechaFinSubSucUsu(idSubSucUsu.equals("2")?null:daFechaFinSubSucUsu);
			}			
		}
	}
	
	public void listener(UploadEvent event)throws DaoException {
		log.info("----------------Debugging FileUploadController.listener-----------------");
		setService(fileUploadService);
		log.info("Se ha seteado el Service");
		UploadItem uploadItem = event.getUploadItem();
		String fileName = uploadItem.getFileName();
		java.io.File file = uploadItem.getFile();
		
		//Usuario usu= new Usuario();
		//usu = (Usuario) getBeanUsuario();
		//usuario.getPersona().getNatural().setStrFoto(fileName);
		log.info("fileName: "+fileName);
		//log.info("usu.getStrImagen(): "+usu.getStrImagen());
		
		String target = getServletContext().getRealPath("") + "images/photographs/"+fileName;
		log.info("target: "+target);
		// "D:/ProyectoTumi/tumi/src/main/webapp/images/photographs/" 
		File f = new File();
		f.setName(fileName);
		setFileName(fileName);
		
		log.info("target: "+target);
		log.info("file:   "+file);
		try {
			InputStream in = new FileInputStream(file);
			OutputStream out = new FileOutputStream(target);
			try {
			// Transfer bytes from in to out
			byte[] buf = new byte[1024*1024*10];//Máximo 10MB
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
				log.info("buf: "+ buf);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally {
				in.close();
				out.close();
			}
		 }catch(Exception e){
			e.printStackTrace();
		 }
		 
		 UIComponent componente = event.getComponent();
		 String uiName = componente.getId();
		 log.info("componente.getId(): "+componente.getId());
		 log.info("file:   "+file);
		 if(uiName.equals("uploadFoto")){
			 setStrNombreImagen(fileName);
			 
		 }
	}
	
	public void enableDisableControls(ActionEvent event) throws DaoException{
		log.info("----------------Debugging UsuarioController.enableDisableControls-------------------");
		//setFormUsuarioEnabled3(getChkDireccion()!=true);
		//setFormUsuarioEnabled2(getChkComunicacion()!=true);
	}

	public void inicioUsuario(){
		usuario = null;
		strTipoMantUsuario = Constante.MANTENIMIENTO_NINGUNO;
		listaUbigeoDepartamento = null;
		limpiarUsuarioMatenimiento();
	}
	
	public List<Juridica> getListaJuridicaEmpresa() throws ParseException, BusinessException{
		try {
			if(listaJuridicaEmpresa == null){
				PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				this.listaJuridicaEmpresa = facade.getListaJuridicaDeEmpresa();
				inicioUsuario();
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
		return this.listaJuridicaEmpresa;
	}
	
	public void setListaJuridicaEmpresa(List<Juridica> listaJuridicaEmpresa) {
		this.listaJuridicaEmpresa = listaJuridicaEmpresa;
	}

	public UsuarioComp getUsuarioComp() {
		return usuarioComp;
	}

	public void setUsuarioComp(UsuarioComp usuarioComp) {
		this.usuarioComp = usuarioComp;
	}

	public List<UsuarioComp> getListaUsuarioComp() {
		return listaUsuarioComp;
	}

	public void setListaUsuarioComp(List<UsuarioComp> listaUsuarioComp) {
		this.listaUsuarioComp = listaUsuarioComp;
	}

	public List<Perfil> getListaPerfil() {
		return listaPerfil;
	}
	public void setListaPerfil(
			List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

	public String getStrTipoMantUsuario() {
		return strTipoMantUsuario;
	}

	public void setStrTipoMantUsuario(String strTipoMantUsuario) {
		this.strTipoMantUsuario = strTipoMantUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
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

	public Comunicacion getComunicacion() {
		return comunicacion;
	}
	public void setComunicacion(Comunicacion comunicacion) {
		this.comunicacion = comunicacion;
	}

	public UsuarioMsg getMsgUsuario() {
		return msgUsuario;
	}
	public void setMsgUsuario(UsuarioMsg msgUsuario) {
		this.msgUsuario = msgUsuario;
	}

	public String getStrIndexUsuario() {
		return strIndexUsuario;
	}
	public void setStrIndexUsuario(String strIndexUsuario) {
		this.strIndexUsuario = strIndexUsuario;
	}

	public String getStrEstadoUsuario() {
		return strEstadoUsuario;
	}
	public void setStrEstadoUsuario(String strEstadoUsuario) {
		this.strEstadoUsuario = strEstadoUsuario;
	}

	public Perfil getFiltroPerfil() {
		return filtroPerfil;
	}
	public void setFiltroPerfil(Perfil filtroPerfil) {
		this.filtroPerfil = filtroPerfil;
	}

	public List<Perfil> getListaFiltroPerfil() {
		return listaFiltroPerfil;
	}
	public void setListaFiltroPerfil(List<Perfil> listaFiltroPerfil) {
		this.listaFiltroPerfil = listaFiltroPerfil;
	}

	public List<Perfil> getListaBusquedaPerfil() {
		return listaBusquedaPerfil;
	}
	public void setListaBusquedaPerfil(List<Perfil> listaBusquedaPerfil) {
		this.listaBusquedaPerfil = listaBusquedaPerfil;
	}

	public String getStrIndexPerfil() {
		return strIndexPerfil;
	}
	public void setStrIndexPerfil(String strIndexPerfil) {
		this.strIndexPerfil = strIndexPerfil;
	}

	public String getStrEstadoPerfil() {
		return strEstadoPerfil;
	}
	public void setStrEstadoPerfil(String strEstadoPerfil) {
		this.strEstadoPerfil = strEstadoPerfil;
	}

	public String getStrTipoMantPerfil() {
		return strTipoMantPerfil;
	}
	public void setStrTipoMantPerfil(String strTipoMantPerfil) {
		this.strTipoMantPerfil = strTipoMantPerfil;
	}

	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public PerfilMsg getMsgPerfil() {
		return msgPerfil;
	}
	public void setMsgPerfil(PerfilMsg msgPerfil) {
		this.msgPerfil = msgPerfil;
	}

	public List<List<Transaccion>> getListaMenu() {
		return listaMenu;
	}
	public void setListaMenu(List<List<Transaccion>> listaMenu) {
		this.listaMenu = listaMenu;
	}

	public List<String> getListaSelectRadioMenu() {
		return listaSelectRadioMenu;
	}
	public void setListaSelectRadioMenu(List<String> listaSelectRadioMenu) {
		this.listaSelectRadioMenu = listaSelectRadioMenu;
	}

	public PermisoPerfil getPermiso() {
		return permiso;
	}
	public void setPermiso(PermisoPerfil permiso) {
		this.permiso = permiso;
	}

	public String getStrValidarUsuario() {
		return strValidarUsuario;
	}
	public void setStrValidarUsuario(String strValidarUsuario) {
		this.strValidarUsuario = strValidarUsuario;
	}

	public Boolean getBolUsuario() {
		return bolUsuario;
	}
	public void setBolUsuario(Boolean bolUsuario) {
		this.bolUsuario = bolUsuario;
	}

	public Boolean getBolPerfil() {
		return bolPerfil;
	}
	public void setBolPerfil(Boolean bolPerfil) {
		this.bolPerfil = bolPerfil;
	}

	public Boolean getBolPermiso() {
		return bolPermiso;
	}
	public void setBolPermiso(Boolean bolPermiso) {
		this.bolPermiso = bolPermiso;
	}

	public String getStrMsgPerfil() {
		return strMsgPerfil;
	}
	public void setStrMsgPerfil(String strMsgPerfil) {
		this.strMsgPerfil = strMsgPerfil;
	}
}
