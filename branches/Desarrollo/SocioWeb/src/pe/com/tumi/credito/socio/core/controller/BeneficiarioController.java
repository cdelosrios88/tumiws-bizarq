package pe.com.tumi.credito.socio.core.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.common.util.MyFile;
import pe.com.tumi.credito.popup.ComunicacionController;
import pe.com.tumi.credito.popup.CuentaBancariaController;
import pe.com.tumi.credito.popup.DomicilioController;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeLocal;
import pe.com.tumi.fileupload.FileUploadController;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.message.MessageController;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficioId;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.login.domain.Usuario;

/************************************************************************/
/* Nombre de la clase: AperturaCuentaController 					*/
/* Funcionalidad : Clase que apertura las nuevas cuentas de socios 	*/
/* Ref. : 															*/
/* Autor : Christian De los Ríos 									*/
/* Versión : 0.1 													*/
/* Fecha creación : 02/07/2012 										*/
/* ********************************************************************* */

public class BeneficiarioController {
	protected   static Logger 			log = Logger.getLogger(BeneficiarioController.class);
	private Persona 	personaBusqueda;
	private SocioComp	beanSocioComp;
	private Usuario 	usuario;
	//private List<Persona> listaBeneficiario;
	private List<CuentaDetalleBeneficio> listaBeneficiario;
	private Integer		intTipoVinculo;
	private Integer		intTipoDoc;
	private String		strNroDoc;
	
	private Boolean		chkAportes;
	private Boolean		chkFondoSepelio;
	private Boolean		chkFondoRetiro;
	private Boolean 	formEnableDisableAportes = true;
	private Boolean 	formEnableDisableFondoSep = true;
	private Boolean 	formEnableDisableFondoReten = true;
	private Boolean		blnShowDivFormNatural;
	private Boolean		blnShowFiltroPersonaNatu;
	private Boolean		blnNroDocumento;
	private Boolean		blnCancelar;
	
	private Integer		intDocIdentidadMaxLength;
	
	private BigDecimal	bdPorcAportes;
	private BigDecimal 	bdPorcFondoSepelio;
	private BigDecimal 	bdPorcFondoRetiro;
	
	private List<Tabla>	listDocumento;
	private List<Tabla>	listTipoVinculo;
	
	private MyFile		fileFotoSocio;
	private MyFile		fileFirmaSocio;
	
	private Integer		intPersEmpresaPk;
	private Integer		intCuenta;
	private Integer		intItemCuentaConcepto;
	private Integer		intItemBeneficio;
	
	private Boolean		validBeneficiario;
	
	private Natural		personaArchivo;
	private String		strIdPersona;
	
	//Mensajes de error
	private String		msgTipoVinculo;
	private String		msgTipoDoc;
	private String		msgNroDoc;
	private String		strBeneficiario;
	private String		msgTxtPorcentajeBeneficio;
	private String		msgTxtBeneficiarioInactivo;
	//Agregado 17/04/2013
	private String		msgTxtVinculoPersona;
	
	private SocioFacadeLocal socioFacade = null;
	private TablaFacadeRemote tablaFacade = null;
	private PersonaFacadeRemote personaFacade = null;
	private GeneralFacadeRemote generalFacade = null;
	private ConceptoFacadeRemote conceptoFacade = null;
	private AperturaCuentaController aperturaCuentaController = null;
	private CuentaBancariaController ctaBancariaController = null;
	private DomicilioController domicilioController = null;
	private ComunicacionController comunicacionController = null;
	private FileUploadController fileupload = null;
	
	public BeneficiarioController(){
		personaBusqueda = new Persona();
		personaBusqueda.setDocumento(new Documento());
		listaBeneficiario = new ArrayList<CuentaDetalleBeneficio>();
		
		beanSocioComp = new SocioComp();
		beanSocioComp.setPersona(new Persona());
		beanSocioComp.getPersona().setNatural(new Natural());
		beanSocioComp.getPersona().getNatural().setPerLaboral(new PerLaboral());
		beanSocioComp.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
		beanSocioComp.getPersona().setDocumento(new Documento());
		beanSocioComp.getPersona().setPersonaEmpresa(new PersonaEmpresa());
		beanSocioComp.getPersona().getPersonaEmpresa().setVinculo(new Vinculo());
		beanSocioComp.setPersonaRol(new PersonaRol());
		beanSocioComp.getPersonaRol().setId(new PersonaRolPK());
		beanSocioComp.setSocio(new Socio());
		beanSocioComp.getSocio().setSocioEstructura(new SocioEstructura());
		
		blnShowFiltroPersonaNatu = true;
		blnShowDivFormNatural = false;
		blnNroDocumento = false;
		blnCancelar = false;
		validBeneficiario = true;
		personaArchivo = new Natural();
		
		aperturaCuentaController = (AperturaCuentaController)getSessionBean("aperturaCuentaController");
		ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
		domicilioController = (DomicilioController)getSessionBean("domicilioController");
		comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		fileupload = (FileUploadController)getSessionBean("fileUploadController");
		
		try {
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			socioFacade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		} catch (EJBFactoryException e) {
			log.error("error: " + e);
		}
	}
	public SocioComp getBeanSocioComp() {
		return beanSocioComp;
	}
	public void setBeanSocioComp(SocioComp beanSocioComp) {
		this.beanSocioComp = beanSocioComp;
	}
	public List<CuentaDetalleBeneficio> getListaBeneficiario() {
		return listaBeneficiario;
	}
	public void setListaBeneficiario(List<CuentaDetalleBeneficio> listaBeneficiario) {
		this.listaBeneficiario = listaBeneficiario;
	}
	public Persona getPersonaBusqueda() {
		return personaBusqueda;
	}
	public void setPersonaBusqueda(Persona personaBusqueda) {
		this.personaBusqueda = personaBusqueda;
	}
	public Integer getIntTipoVinculo() {
		return intTipoVinculo;
	}
	public void setIntTipoVinculo(Integer intTipoVinculo) {
		this.intTipoVinculo = intTipoVinculo;
	}
	public Integer getIntTipoDoc() {
		return intTipoDoc;
	}
	public void setIntTipoDoc(Integer intTipoDoc) {
		this.intTipoDoc = intTipoDoc;
	}
	public String getStrNroDoc() {
		return strNroDoc;
	}
	public void setStrNroDoc(String strNroDoc) {
		this.strNroDoc = strNroDoc;
	}
	public Boolean getChkAportes() {
		return chkAportes;
	}
	public void setChkAportes(Boolean chkAportes) {
		this.chkAportes = chkAportes;
	}
	public Boolean getChkFondoSepelio() {
		return chkFondoSepelio;
	}
	public void setChkFondoSepelio(Boolean chkFondoSepelio) {
		this.chkFondoSepelio = chkFondoSepelio;
	}
	public Boolean getChkFondoRetiro() {
		return chkFondoRetiro;
	}
	public void setChkFondoRetiro(Boolean chkFondoRetiro) {
		this.chkFondoRetiro = chkFondoRetiro;
	}
	public Boolean getFormEnableDisableAportes() {
		return formEnableDisableAportes;
	}
	public void setFormEnableDisableAportes(Boolean formEnableDisableAportes) {
		this.formEnableDisableAportes = formEnableDisableAportes;
	}
	public Boolean getFormEnableDisableFondoSep() {
		return formEnableDisableFondoSep;
	}
	public void setFormEnableDisableFondoSep(Boolean formEnableDisableFondoSep) {
		this.formEnableDisableFondoSep = formEnableDisableFondoSep;
	}
	public Boolean getFormEnableDisableFondoReten() {
		return formEnableDisableFondoReten;
	}
	public void setFormEnableDisableFondoReten(Boolean formEnableDisableFondoReten) {
		this.formEnableDisableFondoReten = formEnableDisableFondoReten;
	}
	public Boolean getBlnShowDivFormNatural() {
		return blnShowDivFormNatural;
	}
	public void setBlnShowDivFormNatural(Boolean blnShowDivFormNatural) {
		this.blnShowDivFormNatural = blnShowDivFormNatural;
	}
	public Boolean getBlnShowFiltroPersonaNatu() {
		return blnShowFiltroPersonaNatu;
	}
	public void setBlnShowFiltroPersonaNatu(Boolean blnShowFiltroPersonaNatu) {
		this.blnShowFiltroPersonaNatu = blnShowFiltroPersonaNatu;
	}
	public Boolean getBlnNroDocumento() {
		return blnNroDocumento;
	}
	public void setBlnNroDocumento(Boolean blnNroDocumento) {
		this.blnNroDocumento = blnNroDocumento;
	}
	public Boolean getBlnCancelar() {
		return blnCancelar;
	}
	public void setBlnCancelar(Boolean blnCancelar) {
		this.blnCancelar = blnCancelar;
	}
	public Integer getIntDocIdentidadMaxLength() {
		return intDocIdentidadMaxLength;
	}
	public void setIntDocIdentidadMaxLength(Integer intDocIdentidadMaxLength) {
		this.intDocIdentidadMaxLength = intDocIdentidadMaxLength;
	}
	public BigDecimal getBdPorcAportes() {
		return bdPorcAportes;
	}
	public void setBdPorcAportes(BigDecimal bdPorcAportes) {
		this.bdPorcAportes = bdPorcAportes;
	}
	public BigDecimal getBdPorcFondoSepelio() {
		return bdPorcFondoSepelio;
	}
	public void setBdPorcFondoSepelio(BigDecimal bdPorcFondoSepelio) {
		this.bdPorcFondoSepelio = bdPorcFondoSepelio;
	}
	public BigDecimal getBdPorcFondoRetiro() {
		return bdPorcFondoRetiro;
	}
	public void setBdPorcFondoRetiro(BigDecimal bdPorcFondoRetiro) {
		this.bdPorcFondoRetiro = bdPorcFondoRetiro;
	}
	public List<Tabla> getListDocumento() {
		log.info("-------------------------------------Debugging SocioController.getListDocumento-------------------------------------");
		log.info("personaBusqueda.intTipoPersonaCod: "+getPersonaBusqueda().getIntTipoPersonaCod());
		Integer intIdTipoPersona = null;
		List<Tabla> listaDocumento = null;
		try {
			intIdTipoPersona = getPersonaBusqueda().getIntTipoPersonaCod();
			if(intIdTipoPersona!=null && intIdTipoPersona!=0){
				if(intIdTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
					listaDocumento = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO), Constante.VISTA_TIPOPERSONA_JURIDICA);
				}else{
					listaDocumento = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO), Constante.VISTA_TIPOPERSONA_NATURAL);
				}
				log.info("listaDocumento.size: "+listaDocumento.size());
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		this.listDocumento = listaDocumento;
		return this.listDocumento;
	}
	public void setListDocumento(List<Tabla> listDocumento) {
		this.listDocumento = listDocumento;
	}
	public List<Tabla> getListTipoVinculo() {
		try {
			listTipoVinculo = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOVINCULO), "A");
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return this.listTipoVinculo;
	}
	public void setListTipoVinculo(List<Tabla> listTipoVinculo) {
		this.listTipoVinculo = listTipoVinculo;
	}
	public MyFile getFileFotoSocio() {
		return fileFotoSocio;
	}
	public void setFileFotoSocio(MyFile fileFotoSocio) {
		this.fileFotoSocio = fileFotoSocio;
	}
	public MyFile getFileFirmaSocio() {
		return fileFirmaSocio;
	}
	public void setFileFirmaSocio(MyFile fileFirmaSocio) {
		this.fileFirmaSocio = fileFirmaSocio;
	}
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntCuenta() {
		return intCuenta;
	}
	public void setIntCuenta(Integer intCuenta) {
		this.intCuenta = intCuenta;
	}
	public Integer getIntItemCuentaConcepto() {
		return intItemCuentaConcepto;
	}
	public void setIntItemCuentaConcepto(Integer intItemCuentaConcepto) {
		this.intItemCuentaConcepto = intItemCuentaConcepto;
	}
	public Integer getIntItemBeneficio() {
		return intItemBeneficio;
	}
	public void setIntItemBeneficio(Integer intItemBeneficio) {
		this.intItemBeneficio = intItemBeneficio;
	}
	public Boolean getValidBeneficiario() {
		return validBeneficiario;
	}
	public void setValidBeneficiario(Boolean validBeneficiario) {
		this.validBeneficiario = validBeneficiario;
	}
	public Natural getPersonaArchivo() {
		return personaArchivo;
	}
	public void setPersonaArchivo(Natural personaArchivo) {
		this.personaArchivo = personaArchivo;
	}
	public String getStrIdPersona() {
		return strIdPersona;
	}
	public void setStrIdPersona(String strIdPersona) {
		this.strIdPersona = strIdPersona;
	}
	public String getMsgTipoVinculo() {
		return msgTipoVinculo;
	}
	public void setMsgTipoVinculo(String msgTipoVinculo) {
		this.msgTipoVinculo = msgTipoVinculo;
	}
	public String getMsgTipoDoc() {
		return msgTipoDoc;
	}
	public void setMsgTipoDoc(String msgTipoDoc) {
		this.msgTipoDoc = msgTipoDoc;
	}
	public String getMsgNroDoc() {
		return msgNroDoc;
	}
	public void setMsgNroDoc(String msgNroDoc) {
		this.msgNroDoc = msgNroDoc;
	}
	public String getStrBeneficiario() {
		return strBeneficiario;
	}
	public void setStrBeneficiario(String strBeneficiario) {
		this.strBeneficiario = strBeneficiario;
	}
	public String getMsgTxtPorcentajeBeneficio() {
		return msgTxtPorcentajeBeneficio;
	}
	public void setMsgTxtPorcentajeBeneficio(String msgTxtPorcentajeBeneficio) {
		this.msgTxtPorcentajeBeneficio = msgTxtPorcentajeBeneficio;
	}
	public String getMsgTxtBeneficiarioInactivo() {
		return msgTxtBeneficiarioInactivo;
	}
	public void setMsgTxtBeneficiarioInactivo(String msgTxtBeneficiarioInactivo) {
		this.msgTxtBeneficiarioInactivo = msgTxtBeneficiarioInactivo;
	}
	public String getMsgTxtVinculoPersona() {
		return msgTxtVinculoPersona;
	}
	public void setMsgTxtVinculoPersona(String msgTxtVinculoPersona) {
		this.msgTxtVinculoPersona = msgTxtVinculoPersona;
	}
	public SocioFacadeLocal getSocioFacade() {
		return socioFacade;
	}
	public void setSocioFacade(SocioFacadeLocal socioFacade) {
		this.socioFacade = socioFacade;
	}
	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}
	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}
	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}
	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}
	public GeneralFacadeRemote getGeneralFacade() {
		return generalFacade;
	}
	public void setGeneralFacade(GeneralFacadeRemote generalFacade) {
		this.generalFacade = generalFacade;
	}
	public ConceptoFacadeRemote getConceptoFacade() {
		return conceptoFacade;
	}
	public void setConceptoFacade(ConceptoFacadeRemote conceptoFacade) {
		this.conceptoFacade = conceptoFacade;
	}
	public AperturaCuentaController getAperturaCuentaController() {
		return aperturaCuentaController;
	}
	public void setAperturaCuentaController(
			AperturaCuentaController aperturaCuentaController) {
		this.aperturaCuentaController = aperturaCuentaController;
	}
	public CuentaBancariaController getCtaBancariaController() {
		return ctaBancariaController;
	}
	public void setCtaBancariaController(
			CuentaBancariaController ctaBancariaController) {
		this.ctaBancariaController = ctaBancariaController;
	}
	public DomicilioController getDomicilioController() {
		return domicilioController;
	}
	public void setDomicilioController(DomicilioController domicilioController) {
		this.domicilioController = domicilioController;
	}
	public ComunicacionController getComunicacionController() {
		return comunicacionController;
	}
	public void setComunicacionController(
			ComunicacionController comunicacionController) {
		this.comunicacionController = comunicacionController;
	}
	public FileUploadController getFileupload() {
		return fileupload;
	}
	public void setFileupload(FileUploadController fileupload) {
		this.fileupload = fileupload;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	public void cancelarBeneficiario(ActionEvent event){
		log.info("--------------BeneficiarioController.cancelarBeneficiario--------------");
		limpiarFormBeneficiario();
		blnShowFiltroPersonaNatu = true;
		blnShowDivFormNatural = false;
		strBeneficiario = Constante.MANTENIMIENTO_GRABAR;
	}
	
	public void limpiarFormBeneficiario(){
		personaBusqueda = new Persona();
		personaBusqueda.setDocumento(new Documento());
		
		beanSocioComp = new SocioComp();
		beanSocioComp.setPersona(new Persona());
		beanSocioComp.getPersona().setNatural(new Natural());
		beanSocioComp.getPersona().getNatural().setPerLaboral(new PerLaboral());
		beanSocioComp.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
		beanSocioComp.getPersona().setDocumento(new Documento());
		beanSocioComp.getPersona().setPersonaEmpresa(new PersonaEmpresa());
		beanSocioComp.getPersona().getPersonaEmpresa().setId(new PersonaEmpresaPK());
		beanSocioComp.getPersona().getPersonaEmpresa().setVinculo(new Vinculo());
		beanSocioComp.setPersonaRol(new PersonaRol());
		beanSocioComp.getPersonaRol().setId(new PersonaRolPK());
		beanSocioComp.setSocio(new Socio());
		beanSocioComp.getSocio().setSocioEstructura(new SocioEstructura());
		beanSocioComp.getPersona().setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_NATURAL);
		setFileFirmaSocio(null);
		setFileFotoSocio(null);
		blnCancelar = false;
		blnNroDocumento = false;
		intTipoVinculo = null;
		
		bdPorcAportes = null;
		bdPorcFondoSepelio = null;
		bdPorcFondoRetiro = null;
		
		if(ctaBancariaController.getListCtaBancariaSocioNatu()!=null){
			ctaBancariaController.setListCtaBancariaSocioNatu(null);
		}
		if(domicilioController.getListDirecBeneficiario()!=null){
			domicilioController.setListDirecBeneficiario(null);
		}
		if(comunicacionController.getListComuniBeneficiario()!=null){
			comunicacionController.setListComuniBeneficiario(null);
		}
	}
	
	public void enableDisableControls(ActionEvent event){
		log.info("----------------Debugging BeneficiarioController.enableDisableControls-------------------");
		setFormEnableDisableAportes(getChkAportes()!=true);
		setFormEnableDisableFondoSep(getChkFondoSepelio()!=true);
		setFormEnableDisableFondoReten(getChkFondoRetiro()!=true);
	}
	
	public void validarMensaje(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.validarMensaje-------------------------------------");
		MessageController message = (MessageController)getSessionBean("messageController");
		log.info("message: "+message);
		log.info("message.getBlnAccepted(): "+message.getBlnAccepted());
		if(message.getBlnAccepted()){
			setBlnShowFiltroPersonaNatu(false);
			setBlnShowDivFormNatural(true);
			limpiarFormBeneficiario();
			getBeanSocioComp().getPersona().setIntTipoPersonaCod(getPersonaBusqueda().getIntTipoPersonaCod()); //Tipo de Persona
		}else{
			setBlnShowDivFormNatural(false);
		}
	}
	
	public void validarBeneficiario(ActionEvent event) throws IOException{
		log.info("-------------------------------------Debugging socioController.validarSocioNatural-------------------------------------");
		log.info("getPersonaBusqueda().getIntTipoPersonaCod(): "+getPersonaBusqueda().getIntTipoPersonaCod());
		log.info("intTipoIdentidadCod: "+getPersonaBusqueda().getDocumento().getIntTipoIdentidadCod());
		log.info("strNumeroIdentidad: "+getPersonaBusqueda().getDocumento().getStrNumeroIdentidad());
		AperturaCuentaController aperturaCuentaController = null;
		if(personaBusqueda.getIntTipoPersonaCod().equals(0)){
			MessageController message = (MessageController)getSessionBean("messageController");
			message.setWarningMessage("Debe seleccionar el Tipo de Persona.");
			return;
		}else if(personaBusqueda.getIntTipoPersonaCod().toString().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
			return;
		}
		
		Integer intTipoDoc = getPersonaBusqueda().getDocumento().getIntTipoIdentidadCod();
		String strNumIdentidad = getPersonaBusqueda().getDocumento().getStrNumeroIdentidad();
		log.info("strNumIdentidad: " + strNumIdentidad);
		
		SocioComp socioComp = null;
		try {
			socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(intTipoDoc,strNumIdentidad,Constante.PARAM_EMPRESASESION);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		if(socioComp!=null){
			setBeanSocioComp(socioComp);
			log.info("getBeanSocioComp().getPersona(): "+getBeanSocioComp().getPersona());
			
			if(getBeanSocioComp().getPersona()==null){
				setBlnShowDivFormNatural(false);
				
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setWarningMessage("La persona buscada no se encuentra registrada. " +
						"Puede presionar Aceptar para hacer un nuevo registro.");
				return;
			}else{
				setBlnShowFiltroPersonaNatu(false);
				setBlnShowDivFormNatural(true);
				
				aperturaCuentaController = (AperturaCuentaController)getSessionBean("aperturaCuentaController");
				
				//Seteando roles
				if(socioComp.getPersona().getPersonaEmpresa()!=null &&
						socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()!=null){
					log.info("socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().size(): "+socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().size());
					for(PersonaRol rol : socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()){
						socioComp.setStrRoles(rol.getTabla().getStrDescripcion()+((socioComp.getStrRoles()==null)?"":","+socioComp.getStrRoles()));
					}
				}
				//Seteando list de Ctas Bancarias de Persona Natural
				if(socioComp.getPersona().getListaCuentaBancaria()!=null){
					ctaBancariaController.setListCtaBancariaSocioNatu(socioComp.getPersona().getListaCuentaBancaria());
				}
				
				//Seteando list de Direcciones de Persona Natura
				if(socioComp.getPersona().getListaDomicilio()!=null){
					domicilioController.setListDirecBeneficiario(socioComp.getPersona().getListaDomicilio());
				}
				
				//Seteando list de Comunicaciones de Persona Natural
				if(socioComp.getPersona().getListaComunicacion()!=null){
					comunicacionController.setListComuniBeneficiario(socioComp.getPersona().getListaComunicacion());
				}
				
				//Mostrar la Firma del Socio
				log.info("socioComp.getPersona().getNatural().getFirma(): " 
						+ socioComp.getPersona().getNatural().getFirma());
				if(socioComp.getPersona().getNatural().getFirma()!=null){
					Archivo firma = socioComp.getPersona().getNatural().getFirma();
					File archivo = new File(firma.getTipoarchivo().getStrRuta()+"\\"+firma.getStrNombrearchivo());
					if(archivo.exists()){
						log.info("Path Firma: "+firma.getTipoarchivo().getStrRuta()+"\\"+firma.getStrNombrearchivo());
						byte[] byteImg = FileUtil.getDataImage(firma.getTipoarchivo().getStrRuta()+"\\"+firma.getStrNombrearchivo());
						MyFile file = new MyFile();
				        file.setLength(byteImg.length);
				        file.setName(firma.getStrNombrearchivo());
				        file.setData(byteImg);
				        setFileFirmaSocio(file);
					}
				}
				
				//Mostrar la Foto del Socio
				log.info("socioComp.getPersona().getNatural().getFoto(): " + socioComp.getPersona().getNatural().getFoto());
				if(socioComp.getPersona().getNatural().getFoto()!=null){
					Archivo foto = socioComp.getPersona().getNatural().getFoto();
					File archivo = new File(foto.getTipoarchivo().getStrRuta()+"\\"+foto.getStrNombrearchivo());
					if(archivo.exists()){
						log.info("Path Foto: "+foto.getTipoarchivo().getStrRuta()+"\\"+foto.getStrNombrearchivo());
						byte[] byteImg = FileUtil.getDataImage(foto.getTipoarchivo().getStrRuta()+"\\"+foto.getStrNombrearchivo());
						MyFile file = new MyFile();
				        file.setLength(byteImg.length);
				        file.setName(foto.getStrNombrearchivo());
				        file.setData(byteImg);
				        setFileFotoSocio(file);
					}
				}
				
				if(socioComp.getPersona().getPersonaEmpresa().getListaVinculo()!=null){
					for(Vinculo vinculo : socioComp.getPersona().getPersonaEmpresa().getListaVinculo()){
						if(aperturaCuentaController.getBeanSocioComp().getPersona().getIntIdPersona().equals(vinculo.getIntPersonaVinculo())){
						//if((new Integer(93)).equals(vinculo.getIntPersonaVinculo())){
							vinculo.setPersona(socioComp.getPersona());
							socioComp.getPersona().getPersonaEmpresa().setVinculo(vinculo);
						}
					}
				}
				if(socioComp.getPersona().getPersonaEmpresa().getVinculo()==null)
					socioComp.getPersona().getPersonaEmpresa().setVinculo(new Vinculo());
				blnNroDocumento = true;
			}
		}
	}
	
	public void addOtroDocumento(ActionEvent event){
		MessageController message = (MessageController)getSessionBean("messageController");
		if(getBeanSocioComp().getStrDocIdentidad()==null || getBeanSocioComp().getStrDocIdentidad().equals("")){
			message.setWarningMessage("No ha ingresado ningún valor del documento de identidad.");
			return;
		}
		
		Documento documento = new Documento();
		documento.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		documento.setIntTipoIdentidadCod(getBeanSocioComp().getIntTipoDocIdentidad());
		documento.setStrNumeroIdentidad(getBeanSocioComp().getStrDocIdentidad());
		
		List<Tabla> listTabla = null;
		try {
			TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO));
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<listTabla.size(); i++){
			if(listTabla.get(i).getIntIdDetalle().equals(documento.getIntTipoIdentidadCod())){
				documento.setTabla(listTabla.get(i));
				break;
			}
		}
		
		if(getBeanSocioComp().getPersona().getListaDocumento()!=null){
			for(int i=0; i<getBeanSocioComp().getPersona().getListaDocumento().size(); i++){
				Documento doc = getBeanSocioComp().getPersona().getListaDocumento().get(i);
				if(documento.getIntTipoIdentidadCod().equals(doc.getIntTipoIdentidadCod())){
					message.setWarningMessage("Ya existe registrado un documento de tipo: "+documento.getTabla().getStrDescripcion()+".");
					return;
				}
			}
		}
		
		if(beanSocioComp.getPersona().getListaDocumento()==null){
			beanSocioComp.getPersona().setListaDocumento(new ArrayList<Documento>());
		}
		beanSocioComp.getPersona().getListaDocumento().add(documento);
		
		//Limpiar
		beanSocioComp.setIntTipoDocIdentidad(0); //Opción Seleccione
		beanSocioComp.setStrDocIdentidad("");
	}
	
	public void paintImage(OutputStream stream, Object object) throws IOException {
        stream.write(((MyFile)object).getData());
    }
	
	public void adjuntarFirma(ActionEvent event){
		log.info("-------------------------------------Debugging BeneficiarioController.adjuntarFirma-------------------------------------");
		FileUploadController fileupload = (FileUploadController)getSessionBean("fileUploadController");
		fileupload.setStrDescripcion("Seleccione el archivo que contiene la firma del socio.");
		fileupload.setFileType(FileUtil.imageTypes);
		
		//Si ya ha grabado el contrato(Archivo) anteriormente
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;
		if(getBeanSocioComp().getPersona().getNatural().getFirma()!=null &&
				getBeanSocioComp().getPersona().getNatural().getFirma().getId()!=null){
			intItemArchivo = getBeanSocioComp().getPersona().getNatural().getFirma().getId().getIntItemArchivo();
			intItemHistorico = getBeanSocioComp().getPersona().getNatural().getFirma().getId().getIntItemHistorico();
		}
		fileupload.setParamArchivo(intItemArchivo, intItemHistorico, Constante.PARAM_T_TIPOARCHIVOADJUNTO_FIRMAPERSONAS);
		fileupload.setStrJsFunction("putFileBeneficiario()");
	}
	
	public void adjuntarFoto(ActionEvent event){
		log.info("-------------------------------------Debugging BeneficiarioController.adjuntarFoto-------------------------------------");
		FileUploadController fileupload = (FileUploadController)getSessionBean("fileUploadController");
		fileupload.setStrDescripcion("Seleccione el archivo que contiene la foto del socio.");
		fileupload.setFileType(FileUtil.imageTypes);
		
		//Si ya ha grabado el contrato(Archivo) anteriormente
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;
		if(getBeanSocioComp().getPersona().getNatural().getFoto()!=null &&
				getBeanSocioComp().getPersona().getNatural().getFoto().getId()!=null){
			intItemArchivo = getBeanSocioComp().getPersona().getNatural().getFoto().getId().getIntItemArchivo();
			intItemHistorico = getBeanSocioComp().getPersona().getNatural().getFoto().getId().getIntItemHistorico();
		}
		fileupload.setParamArchivo(intItemArchivo, intItemHistorico, Constante.PARAM_T_TIPOARCHIVOADJUNTO_FOTOPERSONAS);
		fileupload.setStrJsFunction("putFileBeneficiario()");
	}
	
	public void putFile(ActionEvent event) throws BusinessException, EJBFactoryException, IOException{
		log.info("-------------------------------------Debugging BeneficiarioController.putFile-------------------------------------");
		FileUploadController fileupload = (FileUploadController)getSessionBean("fileUploadController");
		log.info("fileupload: " + fileupload);
		if(fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FIRMAPERSONAS)){
			//setear Firma 
			//beanSocioComp.getPersona().getNatural().setFirma(fileupload.getObjArchivo());
			personaArchivo.setFirma(fileupload.getObjArchivo());
			byte[] byteImg = fileupload.getDataImage();
			MyFile file = new MyFile();
	        file.setLength(byteImg.length);
	        file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
	        file.setData(byteImg);
	        setFileFirmaSocio(file);
		}else if(fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FOTOPERSONAS)){
			//setear Foto
			//beanSocioComp.getPersona().getNatural().setFoto(fileupload.getObjArchivo());
			personaArchivo.setFoto(fileupload.getObjArchivo());
			byte[] byteImg = fileupload.getDataImage();
			MyFile file = new MyFile();
	        file.setLength(byteImg.length);
	        file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
	        file.setData(byteImg);
	        setFileFotoSocio(file);
		}
	}
	
	public void getIdPersona() throws IOException{
		personaArchivo = new Natural();
		String strIdPersona = getRequestParameter("intIdPersona");
		this.strIdPersona = strIdPersona;
		Archivo archivo = null;
		try {
			personaArchivo = personaFacade.getNaturalPorPK(new Integer(strIdPersona));
			if(personaArchivo!=null){
				if(personaArchivo.getIntItemArchivoFirma()!=null){
					archivo = new Archivo();
					archivo.setId(new ArchivoId());
					archivo.getId().setIntItemArchivo(personaArchivo.getIntItemArchivoFirma());
					archivo.getId().setIntItemHistorico(personaArchivo.getIntItemHistoricoFirma());
					archivo.getId().setIntParaTipoCod(personaArchivo.getIntTipoArchivoFirma());
					archivo = generalFacade.getArchivoPorPK(archivo.getId());
					
					//Archivo firma = personaArchivo.getFirma();
					log.info("Path Firma: "+archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
					byte[] byteImg = FileUtil.getDataImage(archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
					MyFile file = new MyFile();
			        file.setLength(byteImg.length);
			        file.setName(archivo.getStrNombrearchivo());
			        file.setData(byteImg);
			        setFileFirmaSocio(file);
				}else{
					setFileFirmaSocio(null);
				}
				
				if(personaArchivo.getIntItemArchivoFoto()!=null){
					archivo = new Archivo();
					archivo.setId(new ArchivoId());
					archivo.getId().setIntItemArchivo(personaArchivo.getIntItemArchivoFoto());
					archivo.getId().setIntItemHistorico(personaArchivo.getIntItemHistoricoFoto());
					archivo.getId().setIntParaTipoCod(personaArchivo.getIntTipoArchivoFoto());
					archivo = generalFacade.getArchivoPorPK(archivo.getId());
					
					//Archivo foto = personaArchivo.getFoto();
					log.info("Path Foto: "+archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
					byte[] byteImg = FileUtil.getDataImage(archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
					MyFile file = new MyFile();
			        file.setLength(byteImg.length);
			        file.setName(archivo.getStrNombrearchivo());
			        file.setData(byteImg);
			        setFileFotoSocio(file);
				}else{
					setFileFotoSocio(null);
				}
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void grabarArchivoBeneficiario() throws BusinessException{
		Natural natural = null;
		try {
			natural = personaFacade.getNaturalPorPK(new Integer(this.strIdPersona));
			natural.setFirma(personaArchivo.getFirma());
			natural.setFoto(personaArchivo.getFoto());
			
			if(natural.getFirma()!=null){
				String strOldName = natural.getFirma().getStrNombrearchivo();
				
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				Archivo archivo = generalFacade.grabarArchivo(natural.getFirma());
				
				if(archivo!=null){
					natural.setIntTipoArchivoFirma(archivo.getId().getIntParaTipoCod());
					natural.setIntItemArchivoFirma(archivo.getId().getIntItemArchivo());
					natural.setIntItemHistoricoFirma(archivo.getId().getIntItemHistorico());
					
					personaFacade.modificarNatural(natural);
					FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
				}
			}
			
			if(natural.getFoto()!=null){
				String strOldName = natural.getFoto().getStrNombrearchivo();
				
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				Archivo archivo = generalFacade.grabarArchivo(natural.getFoto());
				if(archivo!=null){
					natural.setIntTipoArchivoFoto(archivo.getId().getIntParaTipoCod());
					natural.setIntItemArchivoFoto(archivo.getId().getIntItemArchivo());
					natural.setIntItemHistoricoFoto(archivo.getId().getIntItemHistorico());
					
					personaFacade.modificarNatural(natural);
					FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
				}
			}
		} catch (EJBFactoryException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private boolean isValidoBeneficiario(){
		validBeneficiario = true;
		Persona persona = null;
		
		if(bdPorcAportes==null & bdPorcFondoSepelio==null & bdPorcFondoRetiro==null){
			msgTxtPorcentajeBeneficio = "Se debe ingresar por lo menos un tipo de Beneficio.";
			validBeneficiario = false;
		}else{
			msgTxtPorcentajeBeneficio = "";
		}
		
		if(beanSocioComp.getPersona().getPersonaEmpresa().getVinculo().getIntTipoVinculoCod()==null || 
				beanSocioComp.getPersona().getPersonaEmpresa().getVinculo().getIntTipoVinculoCod()==0 ){
			msgTxtVinculoPersona = "Se debe ingresar un tipo de Vínculo.";
			validBeneficiario = false;
		}else{
			msgTxtVinculoPersona = "";
		}
		
		try {
			persona = personaFacade.getPersonaActiva(this.beanSocioComp.getPersona().getIntIdPersona());
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
		}
		if(persona==null){
			msgTxtBeneficiarioInactivo = "La persona seleccionada tiene no se encuentra activa. Favor de verificar la información.";
			validBeneficiario = false;
		}else{
			msgTxtBeneficiarioInactivo = "";
		}
		
		return validBeneficiario;
	}
	
	public void grabarBeneficiario(ActionEvent event){
		CuentaDetalleBeneficio cuentaDetalleBeneficio;
		try{
			if(isValidoBeneficiario() == false){
		    	log.info("Datos de Beneficiario no válidos. Se aborta el proceso de grabación de Beneficiario.");
		    	return;
		    }
			beanSocioComp.getPersona().setListaDomicilio(domicilioController.getListDirecBeneficiario());
			beanSocioComp.getPersona().setListaComunicacion(comunicacionController.getListComuniBeneficiario());
			if(bdPorcAportes!=null){
				cuentaDetalleBeneficio = new CuentaDetalleBeneficio();
				cuentaDetalleBeneficio.setId(new CuentaDetalleBeneficioId());
				cuentaDetalleBeneficio.setPersona(beanSocioComp.getPersona());
				cuentaDetalleBeneficio.setParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				cuentaDetalleBeneficio.setPersPersonausuarioPk(usuario.getIntPersPersonaPk());
				cuentaDetalleBeneficio.setTsFecharegistro(new Timestamp(new Date().getTime()));
				cuentaDetalleBeneficio.setBdPorcentaje(bdPorcAportes);
				cuentaDetalleBeneficio.setIntParaTipoConceptoCod(Constante.CAPTACION_APORTACIONES);
				listaBeneficiario.add(cuentaDetalleBeneficio);
			}
			if(bdPorcFondoSepelio!=null){
				cuentaDetalleBeneficio = new CuentaDetalleBeneficio();
				cuentaDetalleBeneficio.setId(new CuentaDetalleBeneficioId());
				cuentaDetalleBeneficio.setPersona(beanSocioComp.getPersona());
				cuentaDetalleBeneficio.setParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				cuentaDetalleBeneficio.setPersPersonausuarioPk(usuario.getIntPersPersonaPk());
				cuentaDetalleBeneficio.setTsFecharegistro(new Timestamp(new Date().getTime()));
				cuentaDetalleBeneficio.setBdPorcentaje(bdPorcFondoSepelio);
				cuentaDetalleBeneficio.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_SEPELIO);
				listaBeneficiario.add(cuentaDetalleBeneficio);
			}
			if(bdPorcFondoRetiro!=null){
				cuentaDetalleBeneficio = new CuentaDetalleBeneficio();
				cuentaDetalleBeneficio.setId(new CuentaDetalleBeneficioId());
				cuentaDetalleBeneficio.setPersona(beanSocioComp.getPersona());
				cuentaDetalleBeneficio.setParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				cuentaDetalleBeneficio.setPersPersonausuarioPk(usuario.getIntPersPersonaPk());
				cuentaDetalleBeneficio.setTsFecharegistro(new Timestamp(new Date().getTime()));
				cuentaDetalleBeneficio.setBdPorcentaje(bdPorcFondoRetiro);
				cuentaDetalleBeneficio.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
				listaBeneficiario.add(cuentaDetalleBeneficio);
			}
			log.info("listaBeneficiario.size(): " + listaBeneficiario.size());
			//listaPersonasBeneficiario.add(beanSocioComp.getPersona());
			
		}catch (Exception e) {
			log.error("error: " + e);
			e.printStackTrace();
		}
	}
	
	public void irModificarBeneficiario(ActionEvent event){
		String pIntPersEmpresaPk = getRequestParameter("pIntPersEmpresaPk");
		String pIntCuenta = getRequestParameter("pIntCuenta");
		String pIntItemCuentaConcepto = getRequestParameter("pIntItemCuentaConcepto");
		String pIntItemBeneficio = getRequestParameter("pIntItemBeneficio");
		CuentaDetalleBeneficio cuentaDetalleBeneficio = null;
		Archivo archivo = null;
		TipoArchivo tipoArchivo = null;
		try{
			intPersEmpresaPk = new Integer(pIntPersEmpresaPk);
			intCuenta = new Integer(pIntCuenta);
			intItemCuentaConcepto = new Integer(pIntItemCuentaConcepto);
			intItemBeneficio = new Integer(pIntItemBeneficio);
			
			cuentaDetalleBeneficio = new CuentaDetalleBeneficio();
			cuentaDetalleBeneficio.setId(new CuentaDetalleBeneficioId());
			cuentaDetalleBeneficio.getId().setIntPersEmpresaPk(new Integer(pIntPersEmpresaPk));
			cuentaDetalleBeneficio.getId().setIntCuentaPk(new Integer(pIntCuenta));
			cuentaDetalleBeneficio.getId().setIntItemCuentaConcepto(new Integer(pIntItemCuentaConcepto));
			cuentaDetalleBeneficio.getId().setIntItemBeneficio(new Integer(pIntItemBeneficio));
			cuentaDetalleBeneficio = conceptoFacade.getCuentaConceptoDetallePorPk(cuentaDetalleBeneficio);
			
			if(listaBeneficiario!=null){
				for(CuentaDetalleBeneficio cuentaBeneficio: listaBeneficiario){
					if(cuentaDetalleBeneficio.getId().getIntItemBeneficio().equals(cuentaBeneficio.getId().getIntItemBeneficio())){
						if(cuentaBeneficio.getIntParaTipoConceptoCod().equals(Constante.CAPTACION_APORTACIONES)){
							bdPorcAportes = cuentaBeneficio.getBdPorcentaje();
						}
						if(cuentaBeneficio.getIntParaTipoConceptoCod().equals(Constante.CAPTACION_FDO_SEPELIO)){
							bdPorcFondoSepelio = cuentaBeneficio.getBdPorcentaje();
						}
						if(cuentaBeneficio.getIntParaTipoConceptoCod().equals(Constante.CAPTACION_FDO_RETIRO)){
							bdPorcFondoRetiro = cuentaBeneficio.getBdPorcentaje();
						}
					}
					if(cuentaBeneficio.getPersona().getNatural().getIntTipoArchivoFirma()!=null
							&& cuentaBeneficio.getPersona().getNatural().getIntItemArchivoFirma()!=null){
						tipoArchivo = new TipoArchivo();
						tipoArchivo = generalFacade.getTipoArchivoPorPk(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FIRMAPERSONAS);
						
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						archivo.getId().setIntParaTipoCod(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FIRMAPERSONAS);
						archivo.getId().setIntItemArchivo(cuentaBeneficio.getPersona().getNatural().getIntItemArchivoFirma());
						archivo.getId().setIntItemHistorico(cuentaBeneficio.getPersona().getNatural().getIntItemHistoricoFirma());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						
						cuentaBeneficio.getPersona().getNatural().setFirma(archivo);
						
						byte[] byteImg = FileUtil.getDataImage(tipoArchivo.getStrRuta()+ "\\" + archivo.getStrNombrearchivo());
						MyFile file = new MyFile();
				        file.setLength(byteImg.length);
				        file.setName(archivo.getStrNombrearchivo());
				        file.setData(byteImg);
				        setFileFirmaSocio(file);
					}
					
					if(cuentaBeneficio.getPersona().getNatural().getIntTipoArchivoFoto()!=null
							&& cuentaBeneficio.getPersona().getNatural().getIntItemArchivoFoto()!=null){
						tipoArchivo = new TipoArchivo();
						tipoArchivo = generalFacade.getTipoArchivoPorPk(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FOTOPERSONAS);
						
						archivo = new Archivo();
						archivo.setId(new ArchivoId());
						archivo.getId().setIntParaTipoCod(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FOTOPERSONAS);
						archivo.getId().setIntItemArchivo(cuentaBeneficio.getPersona().getNatural().getIntItemArchivoFoto());
						archivo.getId().setIntItemHistorico(cuentaBeneficio.getPersona().getNatural().getIntItemHistoricoFoto());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						
						cuentaBeneficio.getPersona().getNatural().setFoto(archivo);
						
						byte[] byteImg = FileUtil.getDataImage(tipoArchivo.getStrRuta()+ "\\" + archivo.getStrNombrearchivo());
						MyFile file = new MyFile();
				        file.setLength(byteImg.length);
				        file.setName(archivo.getStrNombrearchivo());
				        file.setData(byteImg);
				        setFileFotoSocio(file);
					}
					
					beanSocioComp.setPersona(cuentaBeneficio.getPersona());
					if(beanSocioComp.getPersona().getListaCuentaBancaria()!=null){
						ctaBancariaController.setListCtaBancariaSocioNatu(beanSocioComp.getPersona().getListaCuentaBancaria());
					}
					if(beanSocioComp.getPersona().getListaDomicilio()!=null){
						domicilioController.setListDirecBeneficiario(beanSocioComp.getPersona().getListaDomicilio());
					}
					if(beanSocioComp.getPersona().getListaComunicacion()!=null){
						comunicacionController.setListComuniBeneficiario(beanSocioComp.getPersona().getListaComunicacion());
					}
				}
			}
			blnShowFiltroPersonaNatu = false;
			blnShowDivFormNatural = true;
			blnCancelar = true;
			blnNroDocumento = true;
			strBeneficiario = Constante.MANTENIMIENTO_MODIFICAR;
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	public void modificarBeneficiario(ActionEvent event){
		CuentaDetalleBeneficio cuentaDetalleBeneficio;
		Vinculo vinculo = null;
		//CuentaDetalleBeneficio cuentaDetalleBeneficio = null;
		try {
			cuentaDetalleBeneficio = new CuentaDetalleBeneficio();
			cuentaDetalleBeneficio.setId(new CuentaDetalleBeneficioId());
			cuentaDetalleBeneficio.getId().setIntPersEmpresaPk(intPersEmpresaPk);
			cuentaDetalleBeneficio.getId().setIntCuentaPk(intCuenta);
			cuentaDetalleBeneficio.getId().setIntItemCuentaConcepto(intItemCuentaConcepto);
			cuentaDetalleBeneficio.getId().setIntItemBeneficio(intItemBeneficio);
			cuentaDetalleBeneficio = conceptoFacade.getCuentaConceptoDetallePorPk(cuentaDetalleBeneficio);
			if(listaBeneficiario!=null){
				for(CuentaDetalleBeneficio cuentaBeneficio: listaBeneficiario){
					if(cuentaDetalleBeneficio.getId().getIntItemBeneficio().equals(cuentaBeneficio.getId().getIntItemBeneficio())){
						if(cuentaDetalleBeneficio.getId().getIntItemBeneficio().equals(cuentaBeneficio.getId().getIntItemBeneficio())){
							cuentaDetalleBeneficio.setPersona(beanSocioComp.getPersona());
							if(bdPorcAportes!=null){
								cuentaDetalleBeneficio = new CuentaDetalleBeneficio();
								cuentaDetalleBeneficio.setId(new CuentaDetalleBeneficioId());
								cuentaDetalleBeneficio.setPersona(beanSocioComp.getPersona());
								cuentaDetalleBeneficio.setParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								cuentaDetalleBeneficio.setPersPersonausuarioPk(usuario.getIntPersPersonaPk());
								cuentaDetalleBeneficio.setTsFecharegistro(new Timestamp(new Date().getTime()));
								
								cuentaDetalleBeneficio.setBdPorcentaje(bdPorcAportes);
								cuentaDetalleBeneficio.setIntParaTipoConceptoCod(Constante.CAPTACION_APORTACIONES);
								listaBeneficiario.add(cuentaDetalleBeneficio);
							}
							if(bdPorcFondoSepelio!=null){
								cuentaDetalleBeneficio = new CuentaDetalleBeneficio();
								cuentaDetalleBeneficio.setId(new CuentaDetalleBeneficioId());
								cuentaDetalleBeneficio.setPersona(beanSocioComp.getPersona());
								cuentaDetalleBeneficio.setParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								cuentaDetalleBeneficio.setPersPersonausuarioPk(usuario.getIntPersPersonaPk());
								cuentaDetalleBeneficio.setTsFecharegistro(new Timestamp(new Date().getTime()));
								cuentaDetalleBeneficio.setBdPorcentaje(bdPorcFondoSepelio);
								cuentaDetalleBeneficio.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_SEPELIO);
								listaBeneficiario.add(cuentaDetalleBeneficio);
							}
							if(bdPorcFondoRetiro!=null){
								cuentaDetalleBeneficio = new CuentaDetalleBeneficio();
								cuentaDetalleBeneficio.setId(new CuentaDetalleBeneficioId());
								cuentaDetalleBeneficio.setPersona(beanSocioComp.getPersona());
								cuentaDetalleBeneficio.setParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								cuentaDetalleBeneficio.setPersPersonausuarioPk(usuario.getIntPersPersonaPk());
								cuentaDetalleBeneficio.setTsFecharegistro(new Timestamp(new Date().getTime()));
								cuentaDetalleBeneficio.setBdPorcentaje(bdPorcFondoRetiro);
								cuentaDetalleBeneficio.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
								listaBeneficiario.add(cuentaDetalleBeneficio);
							}
							cuentaDetalleBeneficio = conceptoFacade.modificarCuentaDetalleBeneficio(cuentaDetalleBeneficio);
							personaFacade.modificarPersonaNaturalTotal(beanSocioComp.getPersona());
							if(beanSocioComp.getPersona().getPersonaEmpresa().getVinculo().getIntTipoVinculoCod()!=null){
								vinculo = beanSocioComp.getPersona().getPersonaEmpresa().getVinculo();
								vinculo = personaFacade.modificarVinculoPersona(vinculo);
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	public void removeBeneficiario(ActionEvent event){
		log.info("-------------------------------------BeneficiarioController.removeBeneficiario-------------------------------------");
		String rowKey = getRequestParameter("rowKeyBeneficiario");
		CuentaDetalleBeneficio cuentaDetalleBeneficioTmp = null;
		if(listaBeneficiario!=null){
			for(int i=0; i<listaBeneficiario.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					CuentaDetalleBeneficio cuentaDetalleBeneficio = listaBeneficiario.get(i);
					if(cuentaDetalleBeneficio.getId()!=null && cuentaDetalleBeneficio.getId().getIntItemBeneficio()!=null){
						cuentaDetalleBeneficioTmp = listaBeneficiario.get(i);
						cuentaDetalleBeneficioTmp.setParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaBeneficiario.remove(i);
					break;
				}
			}
			if(cuentaDetalleBeneficioTmp!=null){
				listaBeneficiario.add(cuentaDetalleBeneficioTmp);
			}
		}
	}
}