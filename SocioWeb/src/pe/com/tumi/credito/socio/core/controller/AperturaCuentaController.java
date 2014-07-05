package pe.com.tumi.credito.socio.core.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegranteId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrantePermiso;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeLocal;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeLocal;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCaptacion;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.facade.ConvenioFacadeLocal;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeLocal;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeLocal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.facade.AuditoriaFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

/************************************************************************/
/* Nombre de la clase: AperturaCuentaController 					*/
/* Funcionalidad : Clase que apertura las nuevas cuentas de socios 	*/
/* Ref. : 															*/
/* Autor : Christian De los Ríos 									*/
/* Versión : 0.1 													*/
/* Fecha creación : 25/06/2012 										*/
/*********************************************************************** */
public class AperturaCuentaController {
	protected   static Logger 			log = Logger.getLogger(AperturaCuentaController.class);
	private 	SocioComp				socioNatuSelected;
	private 	SocioComp				beanSocioComp;
	private		SocioEstructura			beanSocioEstructura;
	private		Adenda					beanAdenda;
	private		Cuenta					beanCuenta;
	private		Integer					intCodigo;
	private		Integer					intSucursalSocio;
	private 	List<Estructura>		listEstructura;
	private 	List<Sucursal>			listSucursalSocio;
	private		Boolean					blnShowDivFormAperturaCuenta;
	private		Boolean					blnShowDivPanelControlsAperturaCuenta;
	private		List<Tabla>				listaTipoConformacionSocio;
	private		List<Tabla>				listaTipoSubCuentaSocio;
	private 	Integer 				intValorAportacion;
	private 	Integer 				intValorFondoSepelio;
	private 	Integer 				intValorFondoRetiro;
	private 	Integer 				intValorMantenimientoCta;
	
	private		Boolean					formTiposConceptoRendered;
	private		Boolean					blnDisabledBtnAperCta;
	private 	String					strAperturaCuenta;
	private		List<Persona> 			listaBeneficiarios;
	
	private 	SocioController 		socioController = null;
	private 	BeneficiarioController 	beneficiarioController = null;
	private 	TablaFacadeRemote 		tablaFacade;
	private 	PersonaFacadeRemote 	personaFacade;
	private 	SocioFacadeLocal 		socioFacade = null;
	private 	EstructuraFacadeLocal 	estructuraFacade = null;
	private 	ConvenioFacadeLocal 	convenioFacade = null;
	private 	CaptacionFacadeLocal 	captacionFacade = null;
	private 	CuentaFacadeLocal 		cuentaFacade = null;
	private 	ConceptoFacadeRemote 	conceptoFacade;
	
	//Mensajes de error
	private		String					msgTipoCuenta;
	private		String					msgTipoConformacion;
	private		String					msgSubTipoCuenta;
	private		String					msgTxtValorAportacion;
	private		String					msgTxtValorFondoSepelio;
	private		String					msgTxtValorFondoRetiro;
	private		String					msgTxtValorMantCuenta;
	private		String					msgTxtIntegrante;
	private		String					msgTxtPersonaActiva;
	//JCHAVEZ 16.04.2014
	private		Boolean					blnTipoCuentaNoSocio;
	/* Inicio - AuditoriaFacade - GTorresBrousset 24.abr.2014 */
	
	//atributos de sesión
	private 	Integer 				SESION_IDEMPRESA;
	private 	Integer 				SESION_IDUSUARIO;
	private 	Integer 				SESION_IDSUCURSAL;
	private 	Integer 				SESION_IDSUBSUCURSAL;
	
	private AuditoriaFacadeRemote 		auditoriaFacade;
	/* Fin - AuditoriaFacade - GTorresBrousset 24.abr.2014 */
	//------------------------------------------------------------------------------------------------------------
	//Mantenimiento de Apertura de Cuenta
	//------------------------------------------------------------------------------------------------------------
	public AperturaCuentaController(){
		blnShowDivFormAperturaCuenta = false;
		blnShowDivPanelControlsAperturaCuenta = false;
		socioController = (SocioController)getSessionBean("socioController");
		beneficiarioController = (BeneficiarioController)getSessionBean("beneficiarioController");
		formTiposConceptoRendered = false;
		blnDisabledBtnAperCta = false;
		beanSocioEstructura = new SocioEstructura();
		beanAdenda = new Adenda();
		beanCuenta = new Cuenta();
		listaBeneficiarios = new ArrayList<Persona>();
		blnTipoCuentaNoSocio = true;

		/* Inicio - Inicializar AuditoriaFacade - GTorresBrousset 24.abr.2014*/
		//atributos de sesión
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		/* Fin - Inicializar AuditoriaFacade - GTorresBrousset 24.abr.2014*/
		
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			socioFacade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
			estructuraFacade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			convenioFacade = (ConvenioFacadeLocal)EJBFactory.getLocal(ConvenioFacadeLocal.class);
			captacionFacade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			cuentaFacade = (CuentaFacadeLocal)EJBFactory.getLocal(CuentaFacadeLocal.class);
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			/* Inicio - Inicializar AuditoriaFacade - GTorresBrousset 24.abr.2014*/
			auditoriaFacade = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
			/* Fin - Inicializar AuditoriaFacade - GTorresBrousset 24.abr.2014*/
		} catch (EJBFactoryException e) {
			log.error("error: " + e.getMessage());
		}
	}
	
	public SocioComp getSocioNatuSelected() {
		return socioNatuSelected;
	}
	public void setSocioNatuSelected(SocioComp socioNatuSelected) {
		this.socioNatuSelected = socioNatuSelected;
	}
	public SocioComp getBeanSocioComp() {
		return beanSocioComp;
	}
	public void setBeanSocioComp(SocioComp beanSocioComp) {
		this.beanSocioComp = beanSocioComp;
	}
	public SocioEstructura getBeanSocioEstructura() {
		return beanSocioEstructura;
	}
	public void setBeanSocioEstructura(SocioEstructura beanSocioEstructura) {
		this.beanSocioEstructura = beanSocioEstructura;
	}
	public Adenda getBeanAdenda() {
		return beanAdenda;
	}
	public void setBeanAdenda(Adenda beanAdenda) {
		this.beanAdenda = beanAdenda;
	}
	public Cuenta getBeanCuenta() {
		return beanCuenta;
	}
	public void setBeanCuenta(Cuenta beanCuenta) {
		this.beanCuenta = beanCuenta;
	}
	public Integer getIntCodigo() {
		return intCodigo;
	}
	public void setIntCodigo(Integer intCodigo) {
		this.intCodigo = intCodigo;
	}
	public Integer getIntSucursalSocio() {
		return intSucursalSocio;
	}
	public void setIntSucursalSocio(Integer intSucursalSocio) {
		this.intSucursalSocio = intSucursalSocio;
	}
	public List<Estructura> getListEstructura() {
		try {
			if(listEstructura == null){
				EstructuraFacadeLocal facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
				this.listEstructura = facade.getListaEstructuraPorNivelYCodigoRel(null,null);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if(listEstructura!=null){
			log.info("beanListInstitucion.size: "+listEstructura.size());
		}
		return listEstructura;
	}
	public void setListEstructura(List<Estructura> listEstructura) {
		this.listEstructura = listEstructura;
	}
	public List<Sucursal> getListSucursalSocio() {
		try {
			if(listSucursalSocio == null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listSucursalSocio = facade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if(listSucursalSocio!=null){
			log.info("listSucursal.size: "+listSucursalSocio.size());
		}
		return listSucursalSocio;
	}
	public void setListSucursalSocio(List<Sucursal> listSucursalSocio) {
		this.listSucursalSocio = listSucursalSocio;
	}
	public Boolean getBlnShowDivFormAperturaCuenta() {
		return blnShowDivFormAperturaCuenta;
	}
	public void setBlnShowDivFormAperturaCuenta(Boolean blnShowDivFormAperturaCuenta) {
		this.blnShowDivFormAperturaCuenta = blnShowDivFormAperturaCuenta;
	}
	public Boolean getBlnShowDivPanelControlsAperturaCuenta() {
		return blnShowDivPanelControlsAperturaCuenta;
	}
	public void setBlnShowDivPanelControlsAperturaCuenta(
			Boolean blnShowDivPanelControlsAperturaCuenta) {
		this.blnShowDivPanelControlsAperturaCuenta = blnShowDivPanelControlsAperturaCuenta;
	}
	public List<Tabla> getListaTipoConformacionSocio() {
		return listaTipoConformacionSocio;
	}
	public void setListaTipoConformacionSocio(List<Tabla> listaTipoConformacionSocio) {
		this.listaTipoConformacionSocio = listaTipoConformacionSocio;
	}
	public List<Tabla> getListaTipoSubCuentaSocio() {
		return listaTipoSubCuentaSocio;
	}
	public void setListaTipoSubCuentaSocio(List<Tabla> listaTipoSubCuentaSocio) {
		this.listaTipoSubCuentaSocio = listaTipoSubCuentaSocio;
	}
	public Integer getIntValorAportacion() {
		return intValorAportacion;
	}
	public void setIntValorAportacion(Integer intValorAportacion) {
		this.intValorAportacion = intValorAportacion;
	}
	public Integer getIntValorFondoSepelio() {
		return intValorFondoSepelio;
	}
	public void setIntValorFondoSepelio(Integer intValorFondoSepelio) {
		this.intValorFondoSepelio = intValorFondoSepelio;
	}
	public Integer getIntValorFondoRetiro() {
		return intValorFondoRetiro;
	}
	public void setIntValorFondoRetiro(Integer intValorFondoRetiro) {
		this.intValorFondoRetiro = intValorFondoRetiro;
	}
	public Integer getIntValorMantenimientoCta() {
		return intValorMantenimientoCta;
	}
	public void setIntValorMantenimientoCta(Integer intValorMantenimientoCta) {
		this.intValorMantenimientoCta = intValorMantenimientoCta;
	}
	public Boolean getFormTiposConceptoRendered() {
		return formTiposConceptoRendered;
	}
	public void setFormTiposConceptoRendered(Boolean formTiposConceptoRendered) {
		this.formTiposConceptoRendered = formTiposConceptoRendered;
	}
	public Boolean getBlnDisabledBtnAperCta() {
		return blnDisabledBtnAperCta;
	}
	public void setBlnDisabledBtnAperCta(Boolean blnDisabledBtnAperCta) {
		this.blnDisabledBtnAperCta = blnDisabledBtnAperCta;
	}
	public String getStrAperturaCuenta() {
		return strAperturaCuenta;
	}
	public void setStrAperturaCuenta(String strAperturaCuenta) {
		this.strAperturaCuenta = strAperturaCuenta;
	}
	public List<Persona> getListaBeneficiarios() {
		return listaBeneficiarios;
	}
	public void setListaBeneficiarios(List<Persona> listaBeneficiarios) {
		this.listaBeneficiarios = listaBeneficiarios;
	}
	public SocioController getSocioController() {
		return socioController;
	}
	public void setSocioController(SocioController socioController) {
		this.socioController = socioController;
	}
	public BeneficiarioController getBeneficiarioController() {
		return beneficiarioController;
	}
	public void setBeneficiarioController(
			BeneficiarioController beneficiarioController) {
		this.beneficiarioController = beneficiarioController;
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
	public SocioFacadeLocal getSocioFacade() {
		return socioFacade;
	}
	public void setSocioFacade(SocioFacadeLocal socioFacade) {
		this.socioFacade = socioFacade;
	}
	public EstructuraFacadeLocal getEstructuraFacade() {
		return estructuraFacade;
	}
	public void setEstructuraFacade(EstructuraFacadeLocal estructuraFacade) {
		this.estructuraFacade = estructuraFacade;
	}
	public ConvenioFacadeLocal getConvenioFacade() {
		return convenioFacade;
	}
	public void setConvenioFacade(ConvenioFacadeLocal convenioFacade) {
		this.convenioFacade = convenioFacade;
	}
	public CaptacionFacadeLocal getCaptacionFacade() {
		return captacionFacade;
	}
	public void setCaptacionFacade(CaptacionFacadeLocal captacionFacade) {
		this.captacionFacade = captacionFacade;
	}
	public CuentaFacadeLocal getCuentaFacade() {
		return cuentaFacade;
	}
	public void setCuentaFacade(CuentaFacadeLocal cuentaFacade) {
		this.cuentaFacade = cuentaFacade;
	}
	public ConceptoFacadeRemote getConceptoFacade() {
		return conceptoFacade;
	}
	public void setConceptoFacade(ConceptoFacadeRemote conceptoFacade) {
		this.conceptoFacade = conceptoFacade;
	}
	public String getMsgTipoCuenta() {
		return msgTipoCuenta;
	}
	public void setMsgTipoCuenta(String msgTipoCuenta) {
		this.msgTipoCuenta = msgTipoCuenta;
	}
	public String getMsgTipoConformacion() {
		return msgTipoConformacion;
	}
	public void setMsgTipoConformacion(String msgTipoConformacion) {
		this.msgTipoConformacion = msgTipoConformacion;
	}
	public String getMsgSubTipoCuenta() {
		return msgSubTipoCuenta;
	}
	public void setMsgSubTipoCuenta(String msgSubTipoCuenta) {
		this.msgSubTipoCuenta = msgSubTipoCuenta;
	}
	public String getMsgTxtValorAportacion() {
		return msgTxtValorAportacion;
	}
	public void setMsgTxtValorAportacion(String msgTxtValorAportacion) {
		this.msgTxtValorAportacion = msgTxtValorAportacion;
	}
	public String getMsgTxtValorFondoSepelio() {
		return msgTxtValorFondoSepelio;
	}
	public void setMsgTxtValorFondoSepelio(String msgTxtValorFondoSepelio) {
		this.msgTxtValorFondoSepelio = msgTxtValorFondoSepelio;
	}
	public String getMsgTxtValorFondoRetiro() {
		return msgTxtValorFondoRetiro;
	}
	public void setMsgTxtValorFondoRetiro(String msgTxtValorFondoRetiro) {
		this.msgTxtValorFondoRetiro = msgTxtValorFondoRetiro;
	}
	public String getMsgTxtValorMantCuenta() {
		return msgTxtValorMantCuenta;
	}
	public void setMsgTxtValorMantCuenta(String msgTxtValorMantCuenta) {
		this.msgTxtValorMantCuenta = msgTxtValorMantCuenta;
	}
	public String getMsgTxtIntegrante() {
		return msgTxtIntegrante;
	}
	public void setMsgTxtIntegrante(String msgTxtIntegrante) {
		this.msgTxtIntegrante = msgTxtIntegrante;
	}
	public String getMsgTxtPersonaActiva() {
		return msgTxtPersonaActiva;
	}
	public void setMsgTxtPersonaActiva(String msgTxtPersonaActiva) {
		this.msgTxtPersonaActiva = msgTxtPersonaActiva;
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	
	public Boolean getBlnTipoCuentaNoSocio() {
		return blnTipoCuentaNoSocio;
	}
	public void setBlnTipoCuentaNoSocio(Boolean blnTipoCuentaNoSocio) {
		this.blnTipoCuentaNoSocio = blnTipoCuentaNoSocio;
	}

	//------------------------------------------------------------------------------------------------------------
	//Métodos de la Clase AperturaCuentaController
	//------------------------------------------------------------------------------------------------------------
	public void showFormAperturaCuenta(ActionEvent event){
		SocioComp socioComp = null;
		try {
			socioComp = socioFacade.getSocioNatural(socioNatuSelected.getSocio().getId());
		}  catch (NumberFormatException e) {
			log.error("error: " + e.getMessage());
		} catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}
		
		//Lista de Roles
		if(socioComp.getPersona().getPersonaEmpresa()!=null && 
				socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()!=null){
			log.info("socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().size(): "+socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().size());
			for(PersonaRol rol : socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()){
				socioComp.setStrRoles(rol.getTabla().getStrDescripcion()+((socioComp.getStrRoles()==null)?"":","+socioComp.getStrRoles()));
			}
		}
		
		for(SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()){
			if(socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
				intCodigo = socioEstructura.getIntCodigo();
				intSucursalSocio = socioEstructura.getIntIdSucursalAdministra();
				setBeanSocioEstructura(socioEstructura);
			}
		}
		
		setBeanSocioComp(socioComp);
		blnShowDivFormAperturaCuenta = true;
		blnShowDivPanelControlsAperturaCuenta = true;
		blnDisabledBtnAperCta = false;
		socioController.setBlnShowDivPanelControlsSocio(false);
		socioController.setBlnShowDivFormSocio(false);
		strAperturaCuenta = Constante.MANTENIMIENTO_GRABAR;
	}
	
	//Agregado 18/02/2013
	public void showNewAperturaCuenta(ActionEvent event){
		blnShowDivFormAperturaCuenta = false;
		blnShowDivPanelControlsAperturaCuenta = false;
		
		socioController.setBlnShowDivFormSocio(false);
		socioController.setBlnShowDivFormSocioNatu(false);
	}
	
	public void hideFormAperturaCuenta(ActionEvent event){
		limpiarFormAperturaCuenta();
		blnShowDivFormAperturaCuenta = false;
		blnShowDivPanelControlsAperturaCuenta = false;
		//Agregado 18/02/2013
		socioController.setBlnShowDivFormSocio(false);
		socioController.setBlnShowDivFormSocioNatu(false);
		//Agregado cdelosrios, 31/10/2013
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		if(getSessionBean("aperturaCuentaController")!=null){
			sesion.removeAttribute("aperturaCuentaController");
		}
		if(getSessionBean("socioController")!=null){
			sesion.removeAttribute("socioController");
		}
		if(getSessionBean("beneficiarioController")!=null){
			sesion.removeAttribute("beneficiarioController");
		}
		//Fin agregado cdelosrios, 31/10/2013
	}
	
	public void limpiarFormAperturaCuenta(){
		beanSocioComp = new SocioComp();
		beanSocioComp.setPersona(new Persona());
		beanSocioComp.getPersona().setNatural(new Natural());
		beanSocioComp.getPersona().setDocumento(new Documento());
		if(listSucursalSocio!=null){
			listSucursalSocio.clear();
		}
		beanCuenta = new Cuenta();
		beanCuenta.setListaConcepto(null);
		beanCuenta.setListaIntegrante(null);
		intValorAportacion = null;
		intValorMantenimientoCta = null;
		intValorFondoRetiro = null;
		intValorFondoSepelio = null;
		intCodigo = null;
		intSucursalSocio = null;
		msgTipoCuenta = "";
		msgTipoConformacion = "";
		msgSubTipoCuenta = "";
		beanAdenda = new Adenda();
		if(beanAdenda.getListaAdendaCaptacionAportacion()!=null){
			beanAdenda.getListaAdendaCaptacionAportacion().clear();
		}
		if(beanAdenda.getListaAdendaCaptacionMantCuenta()!=null){
			beanAdenda.getListaAdendaCaptacionMantCuenta().clear();
		}
		if(beanAdenda.getListaAdendaCaptacionFondoSepelio()!=null){
			beanAdenda.getListaAdendaCaptacionFondoSepelio().clear();
		}
		if(beanAdenda.getListaAdendaCaptacionFondoRetiro()!=null){
			beanAdenda.getListaAdendaCaptacionFondoRetiro().clear();
		}
		if(beneficiarioController.getListaBeneficiario()!=null){
			beneficiarioController.setListaBeneficiario(null);
		}
		msgTipoCuenta= "";
		msgTipoConformacion= "";
		msgSubTipoCuenta= "";
		msgTxtValorAportacion= "";
		msgTxtValorFondoSepelio= "";
		msgTxtValorFondoRetiro= "";
		msgTxtValorMantCuenta= "";
		msgTxtIntegrante = "";
		msgTxtPersonaActiva = "";
	}
	
	public void setSelectedSocioNatu(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.setSelectedSocioNatu-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowSocioApertCta"));
		String selectedRow = getRequestParameter("rowSocioApertCta");
		SocioComp socioComp = null;
		for(int i=0; i<socioController.getListaSocioBusqueda().size(); i++){
			socioComp = socioController.getListaSocioBusqueda().get(i);
			if(i == Integer.parseInt(selectedRow)){;
				setSocioNatuSelected(socioComp);
				break;
			}
		}
	}
	
	public void reloadCboTipoConformacionCta(ValueChangeEvent event){
		Integer intIdTipoCuenta = Integer.parseInt(""+event.getNewValue());
		log.info("intIdTipoCuenta = "+intIdTipoCuenta);
		try {
			//JCHAVEZ 16.04.2014 validacion para "NO SOCIO"
			if (intIdTipoCuenta.equals(Constante.PARAM_T_TIPOCUENTASOCIO_NOSOCIO)) {
				blnTipoCuentaNoSocio = false;
			}else blnTipoCuentaNoSocio = true;

			listaTipoConformacionSocio = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCONFORMACIONCTA), intIdTipoCuenta);
		} catch (NumberFormatException e) {
			log.error("error: " + e.getMessage());
		} catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}
	}
	
	public void reloadCboTipoSubCtaSocio(ValueChangeEvent event){
		Integer intIdTipoConformacionCta = Integer.parseInt(""+event.getNewValue());
		log.info("intIdTipoCuenta = "+intIdTipoConformacionCta);
		try {
			listaTipoSubCuentaSocio = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOSUBCUENTASOCIO), intIdTipoConformacionCta);
		} catch (NumberFormatException e) {
			log.error("error: " + e.getMessage());
		} catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}
	}
	
	public void aperturarCuenta(ActionEvent event) throws ParseException{
		log.info("------------------------Debugging AperturaCuentaController.aperturarCuenta------------------------");
		EstructuraDetalle estructuraDetalle = null;
		ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
		Adenda adenda = null;
		Date today = new Date();
		String strToday = Constante.sdf.format(today);
		Date dtToday = Constante.sdf.parse(strToday);
		Captacion captacion = null;
		try {
			//limpiarFormAperturaCuenta();
			if(beanSocioEstructura!=null){
				estructuraDetalle = new EstructuraDetalle();
				estructuraDetalle.setId(new EstructuraDetalleId());
				estructuraDetalle.getId().setIntNivel(beanSocioEstructura.getIntNivel());
				estructuraDetalle.getId().setIntCodigo(beanSocioEstructura.getIntCodigo());
				estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), beanSocioEstructura.getIntTipoSocio(), beanSocioEstructura.getIntModalidad());
				
				//convenioEstructuraDetalle = new ConvenioEstructuraDetalle();
				convenioEstructuraDetalle = estructuraFacade.getConvenioEstructuraDetallePorPkEstructuraDetalle(estructuraDetalle.getId());
				if(convenioEstructuraDetalle!=null){
					
				}
				adenda = new Adenda();
				adenda.setId(new AdendaId());
				adenda.getId().setIntConvenio(convenioEstructuraDetalle.getId().getIntConvenio());
				adenda.getId().setIntItemConvenio(convenioEstructuraDetalle.getId().getIntItemConvenio());
				beanAdenda = convenioFacade.getConvenioPorIdConvenio(adenda.getId());
				
				//if(dtToday.after(beanAdenda.getDtInicio()) && dtToday.before(beanAdenda.getDtCese())){
				if( (dtToday.after(beanAdenda.getDtInicio()) && (beanAdenda.getDtCese()==null)) || 
						(dtToday.after(beanAdenda.getDtInicio()) && dtToday.before(beanAdenda.getDtCese())) ){
					if(beanAdenda.getListaAdendaCaptacionAportacion()!=null){
						for(AdendaCaptacion aportacion : beanAdenda.getListaAdendaCaptacionAportacion()){
							captacion = new Captacion();
							captacion.setId(new CaptacionId());
							captacion.getId().setIntPersEmpresaPk(aportacion.getId().getIntPersEmpresaPk());
							captacion.getId().setIntParaTipoCaptacionCod(aportacion.getId().getIntParaTipoCaptacionCod());
							captacion.getId().setIntItem(aportacion.getId().getIntItem());
							captacion = captacionFacade.getCaptacionPorIdCaptacion(captacion.getId());
							aportacion.setCaptacion(captacion);
						}
					}
					if(beanAdenda.getListaAdendaCaptacionFondoSepelio()!=null){
						for(AdendaCaptacion fondoSepelio : beanAdenda.getListaAdendaCaptacionFondoSepelio()){
							captacion = new Captacion();
							captacion.setId(new CaptacionId());
							captacion.getId().setIntPersEmpresaPk(fondoSepelio.getId().getIntPersEmpresaPk());
							captacion.getId().setIntParaTipoCaptacionCod(fondoSepelio.getId().getIntParaTipoCaptacionCod());
							captacion.getId().setIntItem(fondoSepelio.getId().getIntItem());
							captacion = captacionFacade.getCaptacionPorIdCaptacion(captacion.getId());
							fondoSepelio.setCaptacion(captacion);
						}
					}
					if(beanAdenda.getListaAdendaCaptacionFondoRetiro()!=null){
						for(AdendaCaptacion fondoRetiro : beanAdenda.getListaAdendaCaptacionFondoRetiro()){
							captacion = new Captacion();
							captacion.setId(new CaptacionId());
							captacion.getId().setIntPersEmpresaPk(fondoRetiro.getId().getIntPersEmpresaPk());
							captacion.getId().setIntParaTipoCaptacionCod(fondoRetiro.getId().getIntParaTipoCaptacionCod());
							captacion.getId().setIntItem(fondoRetiro.getId().getIntItem());
							captacion = captacionFacade.getCaptacionPorIdCaptacion(captacion.getId());
							fondoRetiro.setCaptacion(captacion);
						}
					}
					if(beanAdenda.getListaAdendaCaptacionMantCuenta()!=null){
						for(AdendaCaptacion mantCuenta : beanAdenda.getListaAdendaCaptacionMantCuenta()){
							captacion = new Captacion();
							captacion.setId(new CaptacionId());
							captacion.getId().setIntPersEmpresaPk(mantCuenta.getId().getIntPersEmpresaPk());
							captacion.getId().setIntParaTipoCaptacionCod(mantCuenta.getId().getIntParaTipoCaptacionCod());
							captacion.getId().setIntItem(mantCuenta.getId().getIntItem());
							captacion = captacionFacade.getCaptacionPorIdCaptacion(captacion.getId());
							mantCuenta.setCaptacion(captacion);
						}
					}
					/*
					log.info(socioNatuSelected.getSocio().getId().getIntIdPersona());
					cuentaIntegrante = new CuentaIntegrante();
					cuentaIntegrante.setId(new CuentaIntegranteId());
					cuentaIntegrante.getId().setIntPersEmpresaPk(socioNatuSelected.getSocio().getId().getIntIdEmpresa());
					cuentaIntegrante.getId().setIntPersonaIntegrante(socioNatuSelected.getSocio().getId().getIntIdPersona());
					cuentaIntegrante = cuentaFacade.getCuentaIntegrantePorPkSocio(cuentaIntegrante);
					
					cuenta = new Cuenta();
					cuenta.setId(new CuentaId());
					cuenta.getId().setIntCuenta(cuentaIntegrante.getId().getIntCuenta());
					cuenta.getId().setIntPersEmpresaPk(cuentaIntegrante.getId().getIntPersEmpresaPk());
					beanSocioComp.setCuenta(cuenta);*/
					
					setFormTiposConceptoRendered(true);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//log.error("error: "+e);
		}
	}
	
	private Boolean isValidoCuenta(Cuenta beanCuenta){
		Boolean validCuenta = true;
		
		if(beanCuenta.getIntParaTipoCuentaCod()==null || beanCuenta.getIntParaTipoCuentaCod()==0){
			setMsgTipoCuenta("Debe ingresar una Tipo de Cuenta");
			validCuenta = false;
		}else{
			setMsgTipoCuenta("");
		}
		if (!beanCuenta.getIntParaTipoCuentaCod().equals(Constante.PARAM_T_TIPOCUENTASOCIO_NOSOCIO)) {
			if(beanCuenta.getIntParaTipoConformacionCod()==null || beanCuenta.getIntParaTipoConformacionCod()==0){
				setMsgTipoConformacion("Debe ingresar una Tipo de Conformación de Cta.");
				validCuenta = false;
			}else{
				setMsgTipoConformacion("");
			}
			if(intValorAportacion==null){
				setMsgTxtValorAportacion("Debe elegir un Valor de Aportación.");
				validCuenta = false;
			}else{
				setMsgTxtValorAportacion("");
			}
			if(intValorFondoSepelio==null){
				setMsgTxtValorFondoSepelio("Debe elegir un tipo de Fondo de Sepelio.");
				validCuenta = false;
			}else{
				setMsgTxtValorFondoSepelio("");
			}/*
			if(intValorFondoRetiro==null){
				setMsgTxtValorFondoRetiro("Debe elegir un tipo de Fondo de Retiro.");
				validCuenta = false;
			}else{
				setMsgTxtValorFondoRetiro("");
			}*/
			if(intValorMantenimientoCta==null){
				setMsgTxtValorMantCuenta("Debe elegir un tipo de Mant. de Cuenta");
				validCuenta = false;
			}else{
				setMsgTxtValorMantCuenta("");
			}
		}
		
		
		return validCuenta;
	}
	
	public void grabarCuenta(ActionEvent event){
		List<CuentaIntegrante> listaCuentaIntegrante = new ArrayList<CuentaIntegrante>();
		List<CuentaConcepto> listaConcepto = new ArrayList<CuentaConcepto>();
		Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		CuentaConcepto concepto = null;
		
		if(isValidoCuenta(beanCuenta) == false){
	    	log.info("Datos de Cuenta no válidos. Se aborta el proceso de grabación de Cuenta.");
	    	return;
	    }
		
		/**
		 * @author Christian De los Ríos
		 * Req: 
		 * 1.	Solo debe permitir ingresar una cuenta de tipo socio y situación vigente
		 * 2.	Solo a personas de estado activo.
		 * 3.	No permitir ingresar beneficiarios muertos.
		 * 
		 * Agregado 04-03-2013
		 * 
		 */
    	List<CuentaIntegrante> listaCtaIntegrante = null;
		CuentaIntegrante cuentaIntegrante = null;
		Persona persona = null;
		cuentaIntegrante = new CuentaIntegrante();
		cuentaIntegrante.setId(new CuentaIntegranteId());
		cuentaIntegrante.getId().setIntPersEmpresaPk(socioNatuSelected.getSocio().getId().getIntIdEmpresa());
		cuentaIntegrante.getId().setIntPersonaIntegrante(socioNatuSelected.getSocio().getId().getIntIdPersona());
		try {
			listaCtaIntegrante = cuentaFacade.getCuentaIntegrantePorPkPersona(cuentaIntegrante.getId());
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
		}
		if(listaCtaIntegrante!=null && listaCtaIntegrante.size()>0){
			setMsgTxtIntegrante("La persona seleccionada tiene una Cuenta Aperturada o Por Liquidar.");
			return;
		}else{
			setMsgTxtIntegrante("");
		}
		
		try {
			persona = personaFacade.getPersonaActiva(socioNatuSelected.getSocio().getId().getIntIdPersona());
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
		}
		if(persona==null){
			setMsgTxtPersonaActiva("La persona seleccionada tiene no se encuentra activa. Favor de verificar la información.");
			return;
		}else{
			setMsgTxtPersonaActiva("");
		}
		
		//////////////////Fin /////////////////////
		
		int cntIntegrantes = 1;
		/*if(beneficiarioController.getListaBeneficiario()!=null){
			LinkedHashSet lista = new LinkedHashSet<String>();
			for(CuentaDetalleBeneficio cuentaDetalle : beneficiarioController.getListaBeneficiario()){
				lista.add(""+cuentaDetalle.getPersona().getIntIdPersona());
			}
			cntIntegrantes = cntIntegrantes + lista.size();
		}*/
		beanCuenta.setId(new CuentaId());
		beanCuenta.getId().setIntPersEmpresaPk(usuario.getEmpresa().getIntIdEmpresa());
		beanCuenta.setIntIntegrantes(cntIntegrantes);
		beanCuenta.setIntParaTipoMonedaCod(Constante.PARAM_T_TIPOMONEDA_SOLES);
		beanCuenta.setIntParaSituacionCuentaCod(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
		beanCuenta.setIntParaCondicionCuentaCod(Constante.PARAM_T_CONDICIONSOCIO_HABIL);
		beanCuenta.setIntParaSubCondicionCuentaCod(Constante.PARAM_T_TIPO_CONDSOCIO_REGULAR);
		beanCuenta.setBdMontoPlanilla(new BigDecimal(0));
		
		beanCuenta.setIntIdUsuSucursal(usuario.getSucursal().getId().getIntIdSucursal());
		beanCuenta.setIntIdUsuSubSucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
		beanCuenta.setIntPersonaUsuSucursal(usuario.getSucursal().getIntPersPersonaPk());
		beanCuenta.setTsFecRegUsuSucursal(new Timestamp(new Date().getTime()));
		beanCuenta.setTsCuentFecRegistro(new Timestamp(new Date().getTime()));
		
		if (beanCuenta.getIntParaTipoConformacionCod()==null) {
			beanCuenta.setIntParaTipoConformacionCod(Constante.PARAM_T_TIPOCONFORMACION_INDIVIDUAL);
		}
		cuentaIntegrante = new CuentaIntegrante();
		List<CuentaIntegrantePermiso> listaCuentaIntegrantePermiso = new ArrayList<CuentaIntegrantePermiso>();
		CuentaIntegrantePermiso cuentaIntegrantePermiso = new CuentaIntegrantePermiso();
		cuentaIntegrante.setIntParaTipoIntegranteCod(Constante.PARAM_T_TIPOINTEGRANTECUENTA_PRINCIPAL);
		cuentaIntegrante.setTsFechaIngreso(new Timestamp(new Date().getTime()));
		cuentaIntegrante.setIntPersonaUsuario(usuario.getIntPersPersonaPk());
		cuentaIntegrantePermiso.setIntParaTipoPermisoCod(1);//el valor '1' esta temporalmente, hasta q se agregue en parametros
		cuentaIntegrantePermiso.setTsFechaRegistro(new Timestamp(new Date().getTime()));
		cuentaIntegrantePermiso.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		listaCuentaIntegrantePermiso.add(cuentaIntegrantePermiso);
		cuentaIntegrante.setListaCuentaIntegrantePermiso(listaCuentaIntegrantePermiso);
		listaCuentaIntegrante.add(cuentaIntegrante);
		beanCuenta.setListaIntegrante(listaCuentaIntegrante);
		
		/*concepto.setBdSaldo(new BigDecimal(0));
		listaConcepto.add(concepto);*/
		if (!beanCuenta.getIntParaTipoCuentaCod().equals(Constante.PARAM_T_TIPOCUENTASOCIO_NOSOCIO)) {
			if(intValorAportacion!=null){
				concepto = new CuentaConcepto();
				concepto.setId(new CuentaConceptoId());
				concepto.setDetalle(new CuentaConceptoDetalle());
				concepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
				//concepto.getDetalle().setId(new CuentaConceptoDetalleId());
				concepto.setBdSaldo(new BigDecimal(0));
				
				concepto.getDetalle().setTsInicio(new Timestamp(new Date().getTime()));
				concepto.getDetalle().setBdMontoInicial(new BigDecimal(0));
				concepto.getDetalle().setIntParaTipoConceptoCod(Constante.CAPTACION_APORTACIONES);
				concepto.getDetalle().setIntItemConcepto(intValorAportacion);
				concepto.getDetalle().setIntSucursalUsuarioPk(usuario.getSucursal().getId().getIntIdSucursal());
				concepto.getDetalle().setIntSubsucursalUsuarioPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				concepto.getDetalle().setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
				concepto.getDetalle().setBdSaldoDetalle(BigDecimal.ZERO);
				if(beanAdenda.getListaAdendaCaptacionAportacion()!=null){
					for(AdendaCaptacion aportes : beanAdenda.getListaAdendaCaptacionAportacion()){
						if(intValorAportacion.equals(aportes.getCaptacion().getId().getIntItem())){
							concepto.getDetalle().setBdMontoConcepto(aportes.getCaptacion().getBdValorConfiguracion());
							concepto.getDetalle().setIntParaTipoDescuentoCod(aportes.getCaptacion().getIntParaTipoDescuentoCod());
						}
					}
				}
				
				if(beneficiarioController.getBdPorcAportes()!=null){
					if(beneficiarioController.getListaBeneficiario()!=null){
						for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
							if(beanAdenda.getListaAdendaCaptacionAportacion()!=null){
								for(AdendaCaptacion aportes : beanAdenda.getListaAdendaCaptacionAportacion()){
									if(detalleBeneficio.getIntParaTipoConceptoCod().equals(aportes.getCaptacion().getId().getIntParaTipoCaptacionCod())){
										detalleBeneficio.setIntItemConcepto(intValorAportacion);
										concepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
										break;
									}
								}
							}
						}
					}
				}
				listaConcepto.add(concepto);
			}
			
			if(intValorFondoSepelio!=null){
				concepto = new CuentaConcepto();
				concepto.setId(new CuentaConceptoId());
				concepto.setDetalle(new CuentaConceptoDetalle());
				concepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
				//concepto.getDetalle().setId(new CuentaConceptoDetalleId());
				concepto.setBdSaldo(new BigDecimal(0));
				concepto.getDetalle().setTsInicio(new Timestamp(new Date().getTime()));
				concepto.getDetalle().setBdMontoInicial(new BigDecimal(0));
				concepto.getDetalle().setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_SEPELIO);
				concepto.getDetalle().setIntItemConcepto(intValorFondoSepelio);
				concepto.getDetalle().setIntSucursalUsuarioPk(usuario.getSucursal().getId().getIntIdSucursal());
				concepto.getDetalle().setIntSubsucursalUsuarioPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				concepto.getDetalle().setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
				concepto.getDetalle().setBdSaldoDetalle(BigDecimal.ZERO);
				//concepto.getDetalle().setIntParaTipoDescuentoCod(intValorFondoSepelio);
				if(beanAdenda.getListaAdendaCaptacionFondoSepelio()!=null){
					for(AdendaCaptacion fondoSepelio : beanAdenda.getListaAdendaCaptacionFondoSepelio()){
						if(intValorFondoSepelio.equals(fondoSepelio.getCaptacion().getId().getIntItem())){
							concepto.getDetalle().setBdMontoConcepto(fondoSepelio.getCaptacion().getBdValorConfiguracion());
							concepto.getDetalle().setIntParaTipoDescuentoCod(fondoSepelio.getCaptacion().getIntParaTipoDescuentoCod());
						}
					}
				}
				
				if(beneficiarioController.getListaBeneficiario()!=null){
					for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
						if(beanAdenda.getListaAdendaCaptacionFondoSepelio()!=null){
							for(AdendaCaptacion fondoSepelio : beanAdenda.getListaAdendaCaptacionFondoSepelio()){
								if(detalleBeneficio.getIntParaTipoConceptoCod().equals(fondoSepelio.getCaptacion().getId().getIntParaTipoCaptacionCod())){
									detalleBeneficio.setIntItemConcepto(intValorFondoSepelio);
									concepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
									break;
								}
							}
						}
					}
				}
				listaConcepto.add(concepto);
			}
			if(intValorFondoRetiro!=null){
				concepto = new CuentaConcepto();
				concepto.setId(new CuentaConceptoId());
				concepto.setDetalle(new CuentaConceptoDetalle());
				concepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
				//concepto.getDetalle().setId(new CuentaConceptoDetalleId());
				concepto.setBdSaldo(BigDecimal.ZERO);
				concepto.getDetalle().setTsInicio(new Timestamp(new Date().getTime()));
				concepto.getDetalle().setBdMontoInicial(BigDecimal.ZERO);
				concepto.getDetalle().setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
				concepto.getDetalle().setIntItemConcepto(intValorFondoRetiro);
				concepto.getDetalle().setIntSucursalUsuarioPk(usuario.getSucursal().getId().getIntIdSucursal());
				concepto.getDetalle().setIntSubsucursalUsuarioPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				concepto.getDetalle().setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
				concepto.getDetalle().setBdSaldoDetalle(BigDecimal.ZERO);
				//concepto.getDetalle().setIntParaTipoDescuentoCod(intValorFondoRetiro);
				if(beanAdenda.getListaAdendaCaptacionFondoRetiro()!=null){
					for(AdendaCaptacion fondoRetiro : beanAdenda.getListaAdendaCaptacionFondoRetiro()){
						if(intValorFondoRetiro.equals(fondoRetiro.getCaptacion().getId().getIntItem())){
							concepto.getDetalle().setBdMontoConcepto(fondoRetiro.getCaptacion().getBdValorConfiguracion());
							concepto.getDetalle().setIntParaTipoDescuentoCod(fondoRetiro.getCaptacion().getIntParaTipoDescuentoCod());
						}
					}
				}
				
				if(beneficiarioController.getListaBeneficiario()!=null){
					for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
						if(beanAdenda.getListaAdendaCaptacionFondoRetiro()!=null){
							for(AdendaCaptacion fondoRetiro : beanAdenda.getListaAdendaCaptacionFondoRetiro()){
								if(detalleBeneficio.getIntParaTipoConceptoCod().equals(fondoRetiro.getCaptacion().getId().getIntParaTipoCaptacionCod())){
									detalleBeneficio.setIntItemConcepto(intValorFondoRetiro);
									concepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
									break;
								}
							}
						}
					}
				}
				listaConcepto.add(concepto);
			}
			if(intValorMantenimientoCta!=null){
				concepto = new CuentaConcepto();
				concepto.setId(new CuentaConceptoId());
				concepto.setDetalle(new CuentaConceptoDetalle());
				//concepto.getDetalle().setId(new CuentaConceptoDetalleId());
				concepto.setBdSaldo(new BigDecimal(0));
				concepto.getDetalle().setTsInicio(new Timestamp(new Date().getTime()));
				concepto.getDetalle().setBdMontoInicial(new BigDecimal(0));
				concepto.getDetalle().setIntParaTipoConceptoCod(Constante.CAPTACION_MANT_CUENTA);
				concepto.getDetalle().setIntItemConcepto(intValorMantenimientoCta);
				concepto.getDetalle().setIntSucursalUsuarioPk(usuario.getSucursal().getId().getIntIdSucursal());
				concepto.getDetalle().setIntSubsucursalUsuarioPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				concepto.getDetalle().setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
				concepto.getDetalle().setBdSaldoDetalle(BigDecimal.ZERO);
				//concepto.getDetalle().setIntParaTipoDescuentoCod(intValorMantenimientoCta);
				if(beanAdenda.getListaAdendaCaptacionMantCuenta()!=null){
					for(AdendaCaptacion mantCuenta : beanAdenda.getListaAdendaCaptacionMantCuenta()){
						if(intValorMantenimientoCta.equals(mantCuenta.getCaptacion().getId().getIntItem())){
							concepto.getDetalle().setBdMontoConcepto(mantCuenta.getCaptacion().getBdValorConfiguracion());
							concepto.getDetalle().setIntParaTipoDescuentoCod(mantCuenta.getCaptacion().getIntParaTipoDescuentoCod());
						}
					}
				}
				
				/*
				if(beanAdenda.getListaAdendaCaptacionMantCuenta()!=null){
					for(AdendaCaptacion mantCuenta : beanAdenda.getListaAdendaCaptacionMantCuenta()){
						if(mantCuenta.getCaptacion().getId().getIntParaTipoCaptacionCod().equals(Constante.CAPTACION_MANT_CUENTA)){
							//concepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
							break;
						}
					}
				}*/
				listaConcepto.add(concepto);
			}
			beanCuenta.setListaConcepto(listaConcepto);
		}
		
		try {
			cuentaFacade.grabarCuenta(beanCuenta, beanSocioComp.getPersona().getIntIdPersona());
			/* Inicio - Auditoria - GTorresBrousset 24.abr.2014 */
			List<Auditoria> listaAuditoriaCuenta = new ArrayList<Auditoria>();
			listaAuditoriaCuenta = generarAuditoriaCuenta(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, beanCuenta);
			for(Auditoria auditoria : listaAuditoriaCuenta) {
				grabarAuditoria(auditoria);
			}
			
			List<Auditoria> listaAuditoriaPersona = new ArrayList<Auditoria>();
			for(CuentaConcepto cuentaConcepto : beanCuenta.getListaConcepto()) {
				if (cuentaConcepto.getListaCuentaDetalleBeneficio()!=null && !cuentaConcepto.getListaCuentaDetalleBeneficio().isEmpty()) {
					for(CuentaDetalleBeneficio cuentaDetalleBeneficio : cuentaConcepto.getListaCuentaDetalleBeneficio()) {
						listaAuditoriaPersona = socioController.generarAuditoriaPersona(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, cuentaDetalleBeneficio.getPersona());
						for (Auditoria auditoria : listaAuditoriaPersona) {
							socioController.grabarAuditoria(auditoria);
						}
					}
				}else{
					break;
				}
				
			}
			/* Fin - Auditoria - GTorresBrousset 24.abr.2014 */
			limpiarFormAperturaCuenta();
			formTiposConceptoRendered = false;
			blnShowDivFormAperturaCuenta = false;
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void irModificarAperturaCuenta(ActionEvent event) throws ParseException{
		SocioComp socioComp = null;
		CuentaIntegrante cuentaIntegrante = null;
		Cuenta cuenta = null;
		Vinculo vinculo = null;
		blnTipoCuentaNoSocio = true;
		{
			beneficiarioController.setListaBeneficiario(new ArrayList<CuentaDetalleBeneficio>());
		}
		
		try {
			socioComp = socioFacade.getSocioNatural(socioNatuSelected.getSocio().getId());
		}   catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}
		
		//Lista de Roles
		if(socioComp.getPersona().getPersonaEmpresa()!=null && 
				socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()!=null){
			log.info("socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().size(): "+socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().size());
			for(PersonaRol rol : socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()){
				socioComp.setStrRoles(rol.getTabla().getStrDescripcion()+((socioComp.getStrRoles()==null)?"":","+socioComp.getStrRoles()));
			}
		}
		
		for(SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()){
			if(socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
				intCodigo = socioEstructura.getIntCodigo();
				intSucursalSocio = socioEstructura.getIntIdSucursalAdministra();
				setBeanSocioEstructura(socioEstructura);
			}
		}
		
		setBeanSocioComp(socioComp);
		try {
			log.info(socioComp.getSocio().getId().getIntIdPersona());
			cuentaIntegrante = new CuentaIntegrante();
			cuentaIntegrante.setId(new CuentaIntegranteId());
			cuentaIntegrante.getId().setIntPersEmpresaPk(socioComp.getSocio().getId().getIntIdEmpresa());
			cuentaIntegrante.getId().setIntPersonaIntegrante(socioComp.getSocio().getId().getIntIdPersona());
			cuentaIntegrante = cuentaFacade.getCuentaIntegrantePorPkSocio(cuentaIntegrante);
			
			cuenta = new Cuenta();
			cuenta.setId(new CuentaId());
			cuenta.getId().setIntCuenta(cuentaIntegrante.getId().getIntCuenta());
			cuenta.getId().setIntPersEmpresaPk(cuentaIntegrante.getId().getIntPersEmpresaPk());
			beanCuenta = cuentaFacade.getCuentaPorIdCuenta(cuenta);
			//Visualizacion de cuenta no socio
			if (beanCuenta!=null) {
				if (beanCuenta.getIntParaTipoCuentaCod().equals(Constante.PARAM_T_TIPOCUENTASOCIO_NOSOCIO)) {
					blnTipoCuentaNoSocio = false;
				}
			}
			//beanCuenta.getListaIntegrante().get(0).setListaCuentaIntegrantePermiso(cuentaIntegrante.getListaCuentaIntegrantePermiso());
			
			log.info("cuenta.getIntParaCondicionCuentaCod(): " + beanCuenta.getIntParaCondicionCuentaCod());
			log.info("cuenta.getIntParaSubCondicionCuentaCod(): " + beanCuenta.getIntParaSubCondicionCuentaCod());
			beanSocioComp.setCuenta(beanCuenta);
			
			try {
				listaTipoConformacionSocio = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCONFORMACIONCTA), beanCuenta.getIntParaTipoCuentaCod());
				listaTipoSubCuentaSocio = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOSUBCUENTASOCIO), beanCuenta.getIntParaTipoConformacionCod());
			} catch (BusinessException e) {
				log.error("error: " + e.getMessage());
			}
			aperturarCuenta(event);
			if(beanAdenda.getListaAdendaCaptacionAportacion()!=null){
				for(AdendaCaptacion aportes : beanAdenda.getListaAdendaCaptacionAportacion()){
					if(beanCuenta.getListaConcepto()!=null){
						for(CuentaConcepto concepto : beanCuenta.getListaConcepto()){
							if(concepto.getListaCuentaConceptoDetalle()!=null){
								for(CuentaConceptoDetalle conceptoDetalle : concepto.getListaCuentaConceptoDetalle()){
									if(aportes.getCaptacion().getId().getIntItem().equals(conceptoDetalle.getIntItemConcepto())){
										intValorAportacion = conceptoDetalle.getIntItemConcepto();
									}
								}
							}
						}
					}
				}
			}
			
			if(beanAdenda.getListaAdendaCaptacionMantCuenta()!=null){
				for(AdendaCaptacion aportes : beanAdenda.getListaAdendaCaptacionMantCuenta()){
					if(beanCuenta.getListaConcepto()!=null){
						for(CuentaConcepto concepto : beanCuenta.getListaConcepto()){
							if(concepto.getListaCuentaConceptoDetalle()!=null){
								for(CuentaConceptoDetalle conceptoDetalle : concepto.getListaCuentaConceptoDetalle()){
									if(aportes.getCaptacion().getId().getIntItem().equals(conceptoDetalle.getIntItemConcepto())){
										intValorMantenimientoCta = conceptoDetalle.getIntItemConcepto();
									}
								}
							}
						}
					}
				}
			}
			
			if(beanAdenda.getListaAdendaCaptacionFondoSepelio()!=null){
				for(AdendaCaptacion aportes : beanAdenda.getListaAdendaCaptacionFondoSepelio()){
					if(beanCuenta.getListaConcepto()!=null){
						for(CuentaConcepto concepto : beanCuenta.getListaConcepto()){
							if(concepto.getListaCuentaConceptoDetalle()!=null){
								for(CuentaConceptoDetalle conceptoDetalle : concepto.getListaCuentaConceptoDetalle()){
									if(aportes.getCaptacion().getId().getIntItem().equals(conceptoDetalle.getIntItemConcepto())){
										intValorFondoSepelio = conceptoDetalle.getIntItemConcepto();
									}
								}
							}
						}
					}
				}
			}
			
			if(beanAdenda.getListaAdendaCaptacionFondoRetiro()!=null){
				for(AdendaCaptacion aportes : beanAdenda.getListaAdendaCaptacionFondoRetiro()){
					if(beanCuenta.getListaConcepto()!=null){
						for(CuentaConcepto concepto : beanCuenta.getListaConcepto()){
							if(concepto.getListaCuentaConceptoDetalle()!=null){
								for(CuentaConceptoDetalle conceptoDetalle : concepto.getListaCuentaConceptoDetalle()){
									if(aportes.getCaptacion()!=null){
										if(aportes.getCaptacion().getId().getIntItem().equals(conceptoDetalle.getIntItemConcepto())){
											intValorFondoRetiro = conceptoDetalle.getIntItemConcepto();
										}
									}
								}
							}
						}
					}
				}
			}
			
			if(beanCuenta.getListaConcepto()!=null){
				for(CuentaConcepto cuentaConcepto : beanCuenta.getListaConcepto()){
					if(cuentaConcepto.getListaCuentaDetalleBeneficio()!=null){
						for(CuentaDetalleBeneficio cuentaDetalleBeneficio : cuentaConcepto.getListaCuentaDetalleBeneficio()){
							vinculo = new Vinculo();
							vinculo.setPersona(new Persona());
							vinculo = personaFacade.getVinculoPorPk(cuentaDetalleBeneficio.getIntItemVinculo());
							//cuentaDetalleBeneficio.setVinculo(vinculo);
							cuentaDetalleBeneficio.setPersona(vinculo.getPersona());
							
						}
						beneficiarioController.setListaBeneficiario(cuentaConcepto.getListaCuentaDetalleBeneficio());
					}
				}
			}
			
			socioController.setBlnShowDivPanelControlsSocio(false);
			socioController.setBlnShowDivFormSocio(false);
			blnDisabledBtnAperCta = true;
			
			setStrAperturaCuenta(Constante.MANTENIMIENTO_MODIFICAR);
			setBlnShowDivFormAperturaCuenta(true);
			setBlnShowDivPanelControlsAperturaCuenta(true);
			setFormTiposConceptoRendered(true);
		}   catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param event
	 * @throws BusinessException
	 */
	@SuppressWarnings("unused")
	public void modificarCuenta(ActionEvent event) throws BusinessException{
		CuentaConceptoDetalle conceptoDetalle = null;
		Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		//List<CuentaConcepto> listaConcepto = new ArrayList<CuentaConcepto>();
		CuentaConcepto concepto = null;
		if(isValidoCuenta(beanCuenta) == false){
	    	log.info("Datos de Cuenta no válidos. Se aborta el proceso de grabación de Cuenta.");
	    	return;
	    }
		
		int cntIntegrantes = 1;
		/*
		if(beneficiarioController.getListaBeneficiario()!=null){
			LinkedHashSet lista = new LinkedHashSet<String>();
			for(CuentaDetalleBeneficio cuentaDetalle : beneficiarioController.getListaBeneficiario()){
				lista.add(""+cuentaDetalle.getPersona().getIntIdPersona());
			}
			cntIntegrantes = cntIntegrantes + lista.size();
		}*/
		beanCuenta.setIntIntegrantes(cntIntegrantes);
		
		Integer intIdEmpresa = null;
		Integer intIdCuenta = null;
		Integer intItemCuentaConcepto = null;

		if(beanCuenta.getListaConcepto()!=null){
			for(CuentaConcepto cuentaConcepto : beanCuenta.getListaConcepto()){
				intIdEmpresa = cuentaConcepto.getId().getIntPersEmpresaPk();
				intIdCuenta = cuentaConcepto.getId().getIntCuentaPk();
				intItemCuentaConcepto = cuentaConcepto.getId().getIntItemCuentaConcepto();
				
				if(intValorAportacion!=null){
					conceptoDetalle = new CuentaConceptoDetalle();
					conceptoDetalle.setId(new CuentaConceptoDetalleId());
					//cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
					conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
					conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
					conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
					conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_APORTACIONES);
					conceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(conceptoDetalle);
					if(conceptoDetalle!=null){
						if(beneficiarioController.getListaBeneficiario()!=null){
							for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
								if(detalleBeneficio.getIntParaTipoConceptoCod().compareTo(conceptoDetalle.getIntParaTipoConceptoCod())==0
									&& detalleBeneficio.getId().getIntItemBeneficio()==null){
									detalleBeneficio.setIntItemConcepto(intValorAportacion);
									if(cuentaConcepto.getListaCuentaDetalleBeneficio()==null || cuentaConcepto.getListaCuentaDetalleBeneficio().isEmpty()) cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
									cuentaConcepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
									//cgd - 25.07.2013
									//break;
								}
							}
						}
					}
				}
				
				if(intValorFondoSepelio!=null){
					conceptoDetalle = new CuentaConceptoDetalle();
					conceptoDetalle.setId(new CuentaConceptoDetalleId());
					//cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
					conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
					conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
					conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
					conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_SEPELIO);
					conceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(conceptoDetalle);
					if(conceptoDetalle!=null){
						if(beneficiarioController.getListaBeneficiario()!=null){
							for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
								if(detalleBeneficio.getIntParaTipoConceptoCod().compareTo(conceptoDetalle.getIntParaTipoConceptoCod())==0
										&& detalleBeneficio.getId().getIntItemBeneficio()==null){
									detalleBeneficio.setIntItemConcepto(intValorFondoSepelio);
									if(cuentaConcepto.getListaCuentaDetalleBeneficio()==null || cuentaConcepto.getListaCuentaDetalleBeneficio().isEmpty()) cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
									cuentaConcepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
									//cgd - 25.07.2013
									//break;
								}
							}
						}
					}
				}
				
				if(intValorMantenimientoCta!=null){
					conceptoDetalle = new CuentaConceptoDetalle();
					conceptoDetalle.setId(new CuentaConceptoDetalleId());
					//cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
					conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
					conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
					conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
					conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_MANT_CUENTA);
					conceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(conceptoDetalle);
					//cgd - 25.07.2013
					// i --->
					//if(conceptoDetalle!=null){
						//if(beneficiarioController.getListaBeneficiario()!=null){
							//for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
								//if(detalleBeneficio.getIntParaTipoConceptoCod().equals(conceptoDetalle.getIntParaTipoConceptoCod())
								//		&& detalleBeneficio.getId().getIntItemBeneficio()==null){
									//detalleBeneficio.setIntItemConcepto(intValorFondoSepelio);
									//if(cuentaConcepto.getListaCuentaDetalleBeneficio()==null) cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
									//cuentaConcepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
									//break;
								//}
							//}
						//}
					//}
					// f -->
				}
				
				if(intValorFondoRetiro!=null){
					conceptoDetalle = new CuentaConceptoDetalle();
					conceptoDetalle.setId(new CuentaConceptoDetalleId());
					//cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
					conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
					conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
					conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
					conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
					conceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(conceptoDetalle);
					if(conceptoDetalle!=null){
						if(beneficiarioController.getListaBeneficiario()!=null){
							for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
								if(detalleBeneficio.getIntParaTipoConceptoCod().compareTo(conceptoDetalle.getIntParaTipoConceptoCod())==0
										&& detalleBeneficio.getId().getIntItemBeneficio()==null){
									detalleBeneficio.setIntItemConcepto(intValorFondoRetiro);
									if(cuentaConcepto.getListaCuentaDetalleBeneficio()==null || cuentaConcepto.getListaCuentaDetalleBeneficio().isEmpty()) cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
									cuentaConcepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
									//break;
								}
							}
						}
					}
				}
				
				/*
				if(cuentaConcepto.getListaCuentaConceptoDetalle()!=null){
					for(CuentaConceptoDetalle $conceptoDetalle : cuentaConcepto.getListaCuentaConceptoDetalle()){
						if($conceptoDetalle.getIntParaTipoConceptoCod().equals(Constante.CAPTACION_MANT_CUENTA)){
							if(cuentaConcepto.getListaCuentaDetalleBeneficio()!=null){
								cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
							}
						}
					}
				}*/
				
				
				if(beneficiarioController.getListaBeneficiario()!=null && cuentaConcepto.getListaCuentaDetalleBeneficio()!=null){
					//listBenef:
					for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
						for(CuentaDetalleBeneficio cuentaDetalleBeneficio : cuentaConcepto.getListaCuentaDetalleBeneficio()){
							log.info("detalleBeneficio.getId(): " + detalleBeneficio.getId());
							log.info("detalleBeneficio.getId().getIntCuentaPk(): " + detalleBeneficio.getId().getIntCuentaPk());
							log.info("cuentaDetalleBeneficio.getId(): " + cuentaDetalleBeneficio.getId());
							if(detalleBeneficio.getId().getIntCuentaPk()!=null){
								if(detalleBeneficio.getId().getIntCuentaPk().equals(cuentaDetalleBeneficio.getId().getIntCuentaPk())
										&& detalleBeneficio.getId().getIntItemCuentaConcepto().equals(cuentaDetalleBeneficio.getId().getIntItemCuentaConcepto())
											&& detalleBeneficio.getId().getIntItemBeneficio().equals(cuentaDetalleBeneficio.getId().getIntItemBeneficio())
												&& detalleBeneficio.getParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
									cuentaDetalleBeneficio.setParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
									//break listBenef;
								}
							}
						}
					}
				}
			}
			
			
			//Agregado 17/04/2013
			//cgd - 25.07.2013
			// i --->
			/*CuentaConceptoDetalle conceptoDetalleTemp = new CuentaConceptoDetalle();
			conceptoDetalleTemp.setId(new CuentaConceptoDetalleId());
			conceptoDetalleTemp.getId().setIntPersEmpresaPk(intIdEmpresa);
			conceptoDetalleTemp.getId().setIntCuentaPk(intIdCuenta);
			conceptoDetalleTemp.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
			conceptoDetalleTemp = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(conceptoDetalleTemp);
			
			if(intValorFondoRetiro!=null && conceptoDetalleTemp==null){
				concepto = new CuentaConcepto();
				concepto.setId(new CuentaConceptoId());
				concepto.setDetalle(new CuentaConceptoDetalle());
				concepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
				//concepto.getDetalle().setId(new CuentaConceptoDetalleId());
				concepto.getId().setIntCuentaPk(intIdCuenta);
				concepto.getId().setIntPersEmpresaPk(intIdEmpresa);
				
				concepto.setBdSaldo(BigDecimal.ZERO);
				concepto.getDetalle().setId(new CuentaConceptoDetalleId());
				concepto.getDetalle().setTsInicio(new Timestamp(new Date().getTime()));
				concepto.getDetalle().setBdMontoInicial(BigDecimal.ZERO);
				concepto.getDetalle().setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
				concepto.getDetalle().setIntItemConcepto(intValorFondoRetiro);
				concepto.getDetalle().setIntSucursalUsuarioPk(usuario.getSucursal().getId().getIntIdSucursal());
				concepto.getDetalle().setIntSubsucursalUsuarioPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				concepto.getDetalle().setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
				concepto.getDetalle().setBdSaldoDetalle(BigDecimal.ZERO);
				//concepto.getDetalle().setIntParaTipoDescuentoCod(intValorFondoRetiro);
				if(beanAdenda.getListaAdendaCaptacionFondoRetiro()!=null){
					for(AdendaCaptacion fondoRetiro : beanAdenda.getListaAdendaCaptacionFondoRetiro()){
						if(intValorFondoRetiro.equals(fondoRetiro.getCaptacion().getId().getIntItem())){
							concepto.getDetalle().setBdMontoConcepto(fondoRetiro.getCaptacion().getBdValorConfiguracion());
							concepto.getDetalle().setIntParaTipoDescuentoCod(fondoRetiro.getCaptacion().getIntParaTipoDescuentoCod());
							//break;
						}
					}
				}
				
				if(beneficiarioController.getListaBeneficiario()!=null){
					for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
						if(beanAdenda.getListaAdendaCaptacionFondoRetiro()!=null){
							for(AdendaCaptacion fondoRetiro : beanAdenda.getListaAdendaCaptacionFondoRetiro()){
								if(detalleBeneficio.getIntParaTipoConceptoCod().equals(fondoRetiro.getCaptacion().getId().getIntParaTipoCaptacionCod())){
									detalleBeneficio.setIntItemConcepto(intValorFondoRetiro);
									concepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
									//break;
								}
							}
						}
					}
				}
				beanCuenta.getListaConcepto().add(concepto);
			}*/
			// f ---> 
		}

		try {
			beanCuenta = cuentaFacade.modificarCuenta(beanCuenta, beanSocioComp.getPersona().getIntIdPersona());
			limpiarFormAperturaCuenta();
			formTiposConceptoRendered = false;
			blnShowDivFormAperturaCuenta = false;
		} catch (BusinessException e) {
			e.printStackTrace();
		}/*catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void renombradoArchivo(List<Persona> listaBeneficiarios) throws BusinessException, EJBFactoryException{
		if(listaBeneficiarios!=null && listaBeneficiarios.size()>0){
			for (Persona persona : listaBeneficiarios) {
				if(persona.getNatural().getFirma()!=null){
					String strOldName = persona.getNatural().getFirma().getStrNombrearchivo();
					
					GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
					Archivo archivo = generalFacade.grabarArchivo(persona.getNatural().getFirma());
					
					if(archivo!=null){
						persona.getNatural().setIntTipoArchivoFirma(archivo.getId().getIntParaTipoCod());
						persona.getNatural().setIntItemArchivoFirma(archivo.getId().getIntItemArchivo());
						persona.getNatural().setIntItemHistoricoFirma(archivo.getId().getIntItemHistorico());
						
						personaFacade.modificarNatural(persona.getNatural());
						
						FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
					}
				}
				
				if(persona.getNatural().getFoto()!=null){
					String strOldName = persona.getNatural().getFoto().getStrNombrearchivo();
					
					GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
					Archivo archivo = generalFacade.grabarArchivo(persona.getNatural().getFoto());
					
					if(archivo!=null){
						persona.getNatural().setIntTipoArchivoFoto(archivo.getId().getIntParaTipoCod());
						persona.getNatural().setIntItemArchivoFoto(archivo.getId().getIntItemArchivo());
						persona.getNatural().setIntItemHistoricoFoto(archivo.getId().getIntItemHistorico());
						
						personaFacade.modificarNatural(persona.getNatural());
						
						FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta()+"\\"+strOldName, archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
					}
				}
			}
		}
	}

    /* Inicio - Auditoria - GTorresBrousset 02.may.2014 */
    /* Se implementa la Auditoría para los Nuevos Registros y las Modificaciones */
	public List<Auditoria> generarAuditoriaCuenta(Integer intTipoCambio, Cuenta cuenta) throws BusinessException{
		log.info("Inicio");
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			// Inserción de Nuevos Registros
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_INSERT)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
				
				/* Inicio tabla CSO_CUENTA */
				// PERS_EMPRESA_N_PK
				if(cuenta.getId().getIntPersEmpresaPk() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PERS_EMPRESA_N_PK);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getId().getIntPersEmpresaPk());
					
					lista.add(auditoria);
				}

				// CSOC_CUENTA_N
				if(cuenta.getId().getIntCuenta() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_CSOC_CUENTA_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getId().getIntCuenta());
					
					lista.add(auditoria);
				}

				// CUEN_NOMBRECUENTA_V
				if(cuenta.getStrNombreCuenta() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_CUEN_NOMBRECUENTA_V);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getStrNombreCuenta());
					
					lista.add(auditoria);
				}

				// CUEN_INTEGRANTES_N
				if(cuenta.getIntIntegrantes() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_CUEN_INTEGRANTES_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntIntegrantes());
					
					lista.add(auditoria);
				}

				// PARA_TIPOCUENTA_N_COD
				if(cuenta.getIntParaTipoCuentaCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PARA_TIPOCUENTA_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntParaTipoCuentaCod());
					
					lista.add(auditoria);
				}

				// PARA_TIPOCONFORMACION_N_COD
				if(cuenta.getIntParaTipoConformacionCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PARA_TIPOCONFORMACION_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntParaTipoConformacionCod());
					
					lista.add(auditoria);
				}

				// PARA_SUBTIPOCTA_N_COD
				if(cuenta.getIntParaSubTipoCuentaCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PARA_SUBTIPOCTA_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntParaSubTipoCuentaCod());
					
					lista.add(auditoria);
				}

				// PARA_TIPOMONEDA_N_COD
				if(cuenta.getIntParaTipoMonedaCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PARA_TIPOMONEDA_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntParaTipoMonedaCod());
					
					lista.add(auditoria);
				}

				// CUEN_SECUENCIACUENTA_N
				if(cuenta.getIntSecuenciaCuenta() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_CUEN_SECUENCIACUENTA_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntSecuenciaCuenta());
					
					lista.add(auditoria);
				}

				// CUEN_NUMEROCUENTA_V
				if(cuenta.getStrNumeroCuenta() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_CUEN_NUMEROCUENTA_V);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getStrNumeroCuenta());
					
					lista.add(auditoria);
				}

				// CUEN_MONTOPLANILLLA_N
				if(cuenta.getBdMontoPlanilla() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_CUEN_MONTOPLANILLLA_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getBdMontoPlanilla());
					
					lista.add(auditoria);
				}

				// PARA_TIPOFRECUENCIA_N_COD
				if(cuenta.getIntParaTipoFrecuenciaCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PARA_TIPOFRECUENCIA_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntParaTipoFrecuenciaCod());
					
					lista.add(auditoria);
				}

				// PARA_PERIODICIDAD_N_COD
				if(cuenta.getIntParaTipoPeriodicidadCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PARA_PERIODICIDAD_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntParaTipoPeriodicidadCod());
					
					lista.add(auditoria);
				}

				// CUEN_FECHAINICIOCOBRO_D
				if(cuenta.getDtFechaInicioCobro() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_CUEN_FECHAINICIOCOBRO_D);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getDtFechaInicioCobro());
					
					lista.add(auditoria);
				}

				// PARA_OPCIONRENOVACION_N_COD
				if(cuenta.getIntParaOpcionRenovacionCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PARA_OPCIONRENOVACION_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntParaOpcionRenovacionCod());
					
					lista.add(auditoria);
				}

				// PARA_SITUACIONCUENTA_N_COD
				if(cuenta.getIntParaSituacionCuentaCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PARA_SITUACIONCUENTA_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntParaSituacionCuentaCod());
					
					lista.add(auditoria);
				}

				// PARA_CONDICIONCUENTA_N_COD
				if(cuenta.getIntParaCondicionCuentaCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PARA_CONDICIONCUENTA_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntParaCondicionCuentaCod());
					
					lista.add(auditoria);
				}

				// PARA_SUBCONDICIONCUENTA_N_COD
				if(cuenta.getIntParaSubCondicionCuentaCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PARA_SUBCONDICIONCUENTA_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntParaSubCondicionCuentaCod());
					
					lista.add(auditoria);
				}

				// SUCU_IDUSUSUCURSAL_N_PK
				if(cuenta.getIntIdUsuSucursal() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_SUCU_IDUSUSUCURSAL_N_PK);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntIdUsuSucursal());
					
					lista.add(auditoria);
				}

				// SUDE_IDUSUSUBSUCURSAL_N_PK
				if(cuenta.getIntIdUsuSubSucursal() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_SUDE_IDUSUSUBSUCURSAL_N_PK);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntIdUsuSubSucursal());
					
					lista.add(auditoria);
				}

				// PERS_PERSONAUSUSUCURSAL_N_PK
				if(cuenta.getIntPersonaUsuSucursal() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_PERS_PERSONAUSUSUCURSAL_N_PK);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getIntPersonaUsuSucursal());
					
					lista.add(auditoria);
				}

				// USSU_FECREGUSUSUCURSAL_D_PK
				if(cuenta.getTsFecRegUsuSucursal() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_USSU_FECREGUSUSUCURSAL_D_PK);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getTsFecRegUsuSucursal());
					
					lista.add(auditoria);
				}

				// CUEN_FECHAREGISTRO_D
				if(cuenta.getTsCuentFecRegistro() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + cuenta.getId().getIntPersEmpresaPk());
					listaLlaves.add("" + cuenta.getId().getIntCuenta());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTA_CUEN_FECHAREGISTRO_D);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + cuenta.getTsCuentFecRegistro());
					
					lista.add(auditoria);
				}
				
				/* Inicio Tabla CSO_CUENTAINTEGRANTE */
				if (cuenta.getListaIntegrante() != null && cuenta.getListaIntegrante().size() > 0)
					for(CuentaIntegrante cuentaIntegrante : cuenta.getListaIntegrante()) {
						// PERS_EMPRESA_N_PK
						if(cuentaIntegrante.getId().getIntPersEmpresaPk() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersEmpresaPk());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntCuenta());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersonaIntegrante());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTE);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTE_PERS_EMPRESA_N_PK);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaIntegrante.getId().getIntPersEmpresaPk());

							lista.add(auditoria);
						}

						// CSOC_CUENTA_N
						if(cuentaIntegrante.getId().getIntCuenta() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersEmpresaPk());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntCuenta());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersonaIntegrante());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTE);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTE_CSOC_CUENTA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaIntegrante.getId().getIntCuenta());

							lista.add(auditoria);
						}

						// PERS_PERSONAINTEGRANTE_N_PK
						if(cuentaIntegrante.getId().getIntPersonaIntegrante() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersEmpresaPk());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntCuenta());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersonaIntegrante());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTE);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTE_PERS_PERSONAINTEGRANTE_N_PK);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaIntegrante.getId().getIntPersonaIntegrante());

							lista.add(auditoria);
						}

						// PARA_TIPOINTEGRANTE_N_COD
						if(cuentaIntegrante.getIntParaTipoIntegranteCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersEmpresaPk());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntCuenta());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersonaIntegrante());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTE);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTE_PARA_TIPOINTEGRANTE_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaIntegrante.getIntParaTipoIntegranteCod());

							lista.add(auditoria);
						}

						// CUIN_FECHAINGRESO_D
						if(cuentaIntegrante.getTsFechaIngreso() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersEmpresaPk());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntCuenta());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersonaIntegrante());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTE);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTE_CUIN_FECHAINGRESO_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaIntegrante.getTsFechaIngreso());

							lista.add(auditoria);
						}

						// CUIN_FECHARENUNCIA_D
						if(cuentaIntegrante.getTsFechaRenuncia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersEmpresaPk());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntCuenta());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersonaIntegrante());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTE);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTE_CUIN_FECHARENUNCIA_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaIntegrante.getTsFechaRenuncia());

							lista.add(auditoria);
						}

						// PERS_PERSONAUSUARIO_N_PK
						if(cuentaIntegrante.getIntPersonaUsuario() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersEmpresaPk());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntCuenta());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersonaIntegrante());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTE);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTE_PERS_PERSONAUSUARIO_N_PK);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaIntegrante.getIntPersonaUsuario());

							lista.add(auditoria);
						}

						// PERS_PERSONAFECRENUNCIA_N_PK
						if(cuentaIntegrante.getIntPersonaFecRenuncia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersEmpresaPk());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntCuenta());
							listaLlaves.add("" + cuentaIntegrante.getId().getIntPersonaIntegrante());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTE);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTE_PERS_PERSONAFECRENUN_N_PK);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaIntegrante.getIntPersonaFecRenuncia());

							lista.add(auditoria);
						}
						
						/* Inicio Tabla CSO_CUENTAINTEGRANTEPERMISO */
						if (cuentaIntegrante.getListaCuentaIntegrantePermiso() != null && cuentaIntegrante.getListaCuentaIntegrantePermiso().size() > 0)
							for(CuentaIntegrantePermiso cuentaIntegrantePermiso : cuentaIntegrante.getListaCuentaIntegrantePermiso()) {
								// PERS_EMPRESA_N_PK
								if(cuentaIntegrantePermiso.getId().getIntPersEmpresaPk() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersEmpresaPk());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntCuenta());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersonaIntegrante());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntItem());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTEPERMISO);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEPERMI_PERS_EMPRESA_N_PK);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaIntegrantePermiso.getId().getIntPersEmpresaPk());

									lista.add(auditoria);
								}
								
								// CSOC_CUENTA_N
								if(cuentaIntegrantePermiso.getId().getIntCuenta() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersEmpresaPk());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntCuenta());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersonaIntegrante());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntItem());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTEPERMISO);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEPERMI_CSOC_CUENTA_N);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaIntegrantePermiso.getId().getIntCuenta());

									lista.add(auditoria);
								}
								
								// PERS_PERSONAINTEGRANTE_N_PK
								if(cuentaIntegrantePermiso.getId().getIntPersonaIntegrante() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersEmpresaPk());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntCuenta());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersonaIntegrante());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntItem());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTEPERMISO);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEPERMI_PERS_PERSONAINTE_N_PK);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaIntegrantePermiso.getId().getIntPersonaIntegrante());

									lista.add(auditoria);
								}
								
								// CIPE_ITEM_N
								if(cuentaIntegrantePermiso.getId().getIntItem() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersEmpresaPk());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntCuenta());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersonaIntegrante());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntItem());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTEPERMISO);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEPERMI_CIPE_ITEM_N);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaIntegrantePermiso.getId().getIntItem());

									lista.add(auditoria);
								}
								
								// PARA_TIPOPERMISO_N_COD
								if(cuentaIntegrantePermiso.getIntParaTipoPermisoCod() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersEmpresaPk());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntCuenta());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersonaIntegrante());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntItem());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTEPERMISO);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEPERMI_PARA_TIPOPERMISO_N_COD);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaIntegrantePermiso.getIntParaTipoPermisoCod());

									lista.add(auditoria);
								}
								
								// CIPE_MONTOMINIMO_N
								if(cuentaIntegrantePermiso.getBdMontoMinimo() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersEmpresaPk());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntCuenta());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersonaIntegrante());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntItem());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTEPERMISO);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEPERMI_CIPE_MONTOMINIMO_N);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaIntegrantePermiso.getBdMontoMinimo());

									lista.add(auditoria);
								}
								
								// CIPE_MONTOMAXIMO_N
								if(cuentaIntegrantePermiso.getBdMontoMaximo() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersEmpresaPk());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntCuenta());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersonaIntegrante());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntItem());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTEPERMISO);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEPERMI_CIPE_MONTOMAXIMO_N);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaIntegrantePermiso.getBdMontoMaximo());

									lista.add(auditoria);
								}
								
								// CIPE_FECHAREGISTRO_D
								if(cuentaIntegrantePermiso.getTsFechaRegistro() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersEmpresaPk());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntCuenta());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersonaIntegrante());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntItem());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTEPERMISO);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEPERMI_CIPE_FECHAREGI_D);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaIntegrantePermiso.getTsFechaRegistro());

									lista.add(auditoria);
								}
								
								// PARA_ESTADO_N_COD
								if(cuentaIntegrantePermiso.getIntParaTipoPermisoCod() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersEmpresaPk());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntCuenta());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersonaIntegrante());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntItem());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTEPERMISO);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEPERMI_PARA_ESTADO_N_COD);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaIntegrantePermiso.getIntParaEstadoCod());

									lista.add(auditoria);
								}
								
								// CIPE_FECHAELIMINACION_D
								if(cuentaIntegrantePermiso.getTsFechaEliminacion() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersEmpresaPk());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntCuenta());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntPersonaIntegrante());
									listaLlaves.add("" + cuentaIntegrantePermiso.getId().getIntItem());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEGRANTEPERMISO);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_CUENTAINTEPERMI_CIPE_FECHAELIMINA_D);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaIntegrantePermiso.getTsFechaEliminacion());

									lista.add(auditoria);
								}
							}
					}
			}
		} catch (Exception e) {
			log.error("Error en generarAuditoriaCuenta --> " + e);
		}
		log.info("Fin");
		return lista;
	}
	/* Método que carga los datos comunes de la estructura Auditoria */
	public Auditoria beanAuditoria(Integer intTipoCambio, List<String> llaves, String tabla) {
		log.info("Inicio");
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = auditoriaFacade.beanAuditoria(SESION_IDEMPRESA, SESION_IDUSUARIO, intTipoCambio, llaves, tabla);
		} catch (Exception e) {
			log.error("Error al crear el Bean de Auditoria: " + e);
		}
		log.info("Fin");
		return auditoria;
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

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		AperturaCuentaController.log = log;
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

	public Integer getSESION_IDSUCURSAL() {
		return SESION_IDSUCURSAL;
	}

	public void setSESION_IDSUCURSAL(Integer sESION_IDSUCURSAL) {
		SESION_IDSUCURSAL = sESION_IDSUCURSAL;
	}

	public Integer getSESION_IDSUBSUCURSAL() {
		return SESION_IDSUBSUCURSAL;
	}

	public void setSESION_IDSUBSUCURSAL(Integer sESION_IDSUBSUCURSAL) {
		SESION_IDSUBSUCURSAL = sESION_IDSUBSUCURSAL;
	}

	public AuditoriaFacadeRemote getAuditoriaFacade() {
		return auditoriaFacade;
	}

	public void setAuditoriaFacade(AuditoriaFacadeRemote auditoriaFacade) {
		this.auditoriaFacade = auditoriaFacade;
	}
    /* Fin - Auditoria - GTorresBrousset 02.may.2014 */
	
	/*public void modificarCuenta(ActionEvent event) throws BusinessException{
		CuentaConceptoDetalle conceptoDetalle = null;
		Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(isValidoCuenta(beanCuenta) == false){
	    	log.info("Datos de Cuenta no válidos. Se aborta el proceso de grabación de Cuenta.");
	    	return;
	    }
		
		if(beanCuenta.getListaConcepto()!=null){
			for(CuentaConcepto cuentaConcepto : beanCuenta.getListaConcepto()){
				if(intValorAportacion!=null){
					for(CuentaConceptoDetalle cuentaConceptoDetalle : cuentaConcepto.getListaCuentaConceptoDetalle()){
						if(cuentaConceptoDetalle.getIntParaTipoConceptoCod().equals(Constante.CAPTACION_APORTACIONES)){
							cuentaConceptoDetalle.setIntItemConcepto(intValorAportacion);
						}
					}
					conceptoDetalle = new CuentaConceptoDetalle();
					conceptoDetalle.setId(new CuentaConceptoDetalleId());
					cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
					conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
					conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
					conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
					conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_APORTACIONES);
					conceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(conceptoDetalle);
					if(conceptoDetalle==null){
						conceptoDetalle = new CuentaConceptoDetalle();
						conceptoDetalle.setId(new CuentaConceptoDetalleId());
						conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
						conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
						conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
						conceptoDetalle.setTsInicio(new Timestamp(new Date().getTime()));
						conceptoDetalle.setBdMontoInicial(new BigDecimal(0));
						conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_APORTACIONES);
						conceptoDetalle.setIntItemConcepto(intValorAportacion);
						conceptoDetalle.setIntSucursalUsuarioPk(usuario.getSucursal().getId().getIntIdSucursal());
						conceptoDetalle.setIntSubsucursalUsuarioPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						conceptoDetalle.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
						if(beneficiarioController.getListaBeneficiario()!=null){
							for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
								if(beanAdenda.getListaAdendaCaptacionAportacion()!=null){
									for(AdendaCaptacion aportacion : beanAdenda.getListaAdendaCaptacionAportacion()){
										if(detalleBeneficio.getIntParaTipoConceptoCod().equals(aportacion.getCaptacion().getId().getIntParaTipoCaptacionCod())
												&& detalleBeneficio.getId().getIntItemBeneficio()==null){
											conceptoDetalle.setIntParaTipoDescuentoCod(aportacion.getCaptacion().getIntParaTipoDescuentoCod());
											if(intValorAportacion.equals(aportacion.getCaptacion().getId().getIntItem())){
												conceptoDetalle.setBdMontoConcepto(aportacion.getCaptacion().getBdValorConfiguracion());
											}
											conceptoDetalle.setBdSaldoDetalle(new BigDecimal(0));
											cuentaConcepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
											break;
										}
									}
								}
							}
						}
						cuentaConcepto.getListaCuentaConceptoDetalle().add(conceptoDetalle);
					}
				}
				if(intValorFondoSepelio!=null){
					for(CuentaConceptoDetalle cuentaConceptoDetalle : cuentaConcepto.getListaCuentaConceptoDetalle()){
						if(cuentaConceptoDetalle.getIntParaTipoConceptoCod().equals(Constante.CAPTACION_FDO_SEPELIO)){
							cuentaConceptoDetalle.setIntItemConcepto(intValorFondoSepelio);
						}
					}
					conceptoDetalle = new CuentaConceptoDetalle();
					conceptoDetalle.setId(new CuentaConceptoDetalleId());
					cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
					conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
					conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
					conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
					conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_SEPELIO);
					conceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(conceptoDetalle);
					if(conceptoDetalle==null){
						conceptoDetalle = new CuentaConceptoDetalle();
						conceptoDetalle.setId(new CuentaConceptoDetalleId());
						conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
						conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
						conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
						conceptoDetalle.setTsInicio(new Timestamp(new Date().getTime()));
						conceptoDetalle.setBdMontoInicial(new BigDecimal(0));
						conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_SEPELIO);
						conceptoDetalle.setIntItemConcepto(intValorFondoSepelio);
						conceptoDetalle.setIntSucursalUsuarioPk(usuario.getSucursal().getId().getIntIdSucursal());
						conceptoDetalle.setIntSubsucursalUsuarioPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						conceptoDetalle.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
						if(beneficiarioController.getListaBeneficiario()!=null){
							for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
								if(beanAdenda.getListaAdendaCaptacionFondoSepelio()!=null){
									for(AdendaCaptacion fondoSepelio : beanAdenda.getListaAdendaCaptacionFondoSepelio()){
										if(detalleBeneficio.getIntParaTipoConceptoCod().equals(fondoSepelio.getCaptacion().getId().getIntParaTipoCaptacionCod())
												&& detalleBeneficio.getId().getIntItemBeneficio()==null){
											conceptoDetalle.setIntParaTipoDescuentoCod(fondoSepelio.getCaptacion().getIntParaTipoDescuentoCod());
											if(intValorFondoSepelio.equals(fondoSepelio.getCaptacion().getId().getIntItem())){
												conceptoDetalle.setBdMontoConcepto(fondoSepelio.getCaptacion().getBdValorConfiguracion());
											}
											conceptoDetalle.setBdSaldoDetalle(new BigDecimal(0));
											cuentaConcepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
											break;
										}
									}
								}
							}
						}
						cuentaConcepto.getListaCuentaConceptoDetalle().add(conceptoDetalle);
					}
				}
				if(intValorFondoRetiro!=null){
					for(CuentaConceptoDetalle cuentaConceptoDetalle : cuentaConcepto.getListaCuentaConceptoDetalle()){
						if(cuentaConceptoDetalle.getIntParaTipoConceptoCod().equals(Constante.CAPTACION_FDO_RETIRO)){
							cuentaConceptoDetalle.setIntItemConcepto(intValorFondoRetiro);
						}
					}
					conceptoDetalle = new CuentaConceptoDetalle();
					conceptoDetalle.setId(new CuentaConceptoDetalleId());
					cuentaConcepto.setListaCuentaDetalleBeneficio(new ArrayList<CuentaDetalleBeneficio>());
					conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
					conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
					conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
					conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
					conceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(conceptoDetalle);
					if(conceptoDetalle==null){
						conceptoDetalle = new CuentaConceptoDetalle();
						conceptoDetalle.setId(new CuentaConceptoDetalleId());
						conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
						conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
						conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
						conceptoDetalle.setTsInicio(new Timestamp(new Date().getTime()));
						conceptoDetalle.setBdMontoInicial(new BigDecimal(0));
						conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
						conceptoDetalle.setIntItemConcepto(intValorFondoRetiro);
						conceptoDetalle.setIntSucursalUsuarioPk(usuario.getSucursal().getId().getIntIdSucursal());
						conceptoDetalle.setIntSubsucursalUsuarioPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						conceptoDetalle.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
						if(beneficiarioController.getListaBeneficiario()!=null){
							for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
								if(beanAdenda.getListaAdendaCaptacionFondoRetiro()!=null){
									for(AdendaCaptacion fondoRetiro : beanAdenda.getListaAdendaCaptacionFondoRetiro()){
										if(detalleBeneficio.getIntParaTipoConceptoCod().equals(fondoRetiro.getCaptacion().getId().getIntParaTipoCaptacionCod())
												&& detalleBeneficio.getId().getIntItemBeneficio()==null){
											conceptoDetalle.setIntParaTipoDescuentoCod(fondoRetiro.getCaptacion().getIntParaTipoDescuentoCod());
											if(intValorFondoRetiro.equals(fondoRetiro.getCaptacion().getId().getIntItem())){
												conceptoDetalle.setBdMontoConcepto(fondoRetiro.getCaptacion().getBdValorConfiguracion());
											}
											conceptoDetalle.setBdSaldoDetalle(new BigDecimal(0));
											cuentaConcepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
											break;
										}
									}
								}
							}
						}
						cuentaConcepto.getListaCuentaConceptoDetalle().add(conceptoDetalle);
					}
				}
				if(intValorMantenimientoCta!=null){
					for(CuentaConceptoDetalle cuentaConceptoDetalle : cuentaConcepto.getListaCuentaConceptoDetalle()){
						if(cuentaConceptoDetalle.getIntParaTipoConceptoCod().equals(Constante.CAPTACION_MANT_CUENTA)){
							cuentaConceptoDetalle.setIntItemConcepto(intValorMantenimientoCta);
							break;
						}
					}
					conceptoDetalle = new CuentaConceptoDetalle();
					conceptoDetalle.setId(new CuentaConceptoDetalleId());
					conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
					conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
					conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
					conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_MANT_CUENTA);
					conceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(conceptoDetalle);
					if(conceptoDetalle==null){
						conceptoDetalle = new CuentaConceptoDetalle();
						conceptoDetalle.setId(new CuentaConceptoDetalleId());
						conceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
						conceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
						conceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
						conceptoDetalle.setTsInicio(new Timestamp(new Date().getTime()));
						conceptoDetalle.setBdMontoInicial(new BigDecimal(0));
						conceptoDetalle.setIntParaTipoConceptoCod(Constante.CAPTACION_MANT_CUENTA);
						conceptoDetalle.setIntItemConcepto(intValorMantenimientoCta);
						conceptoDetalle.setIntSucursalUsuarioPk(usuario.getSucursal().getId().getIntIdSucursal());
						conceptoDetalle.setIntSubsucursalUsuarioPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						conceptoDetalle.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
						if(beneficiarioController.getListaBeneficiario()!=null){
							for(CuentaDetalleBeneficio detalleBeneficio : beneficiarioController.getListaBeneficiario()){
								if(beanAdenda.getListaAdendaCaptacionMantCuenta()!=null){
									for(AdendaCaptacion mantCuenta : beanAdenda.getListaAdendaCaptacionMantCuenta()){
										if(detalleBeneficio.getIntParaTipoConceptoCod().equals(mantCuenta.getCaptacion().getId().getIntParaTipoCaptacionCod())
												&& detalleBeneficio.getId().getIntItemBeneficio()==null){
											conceptoDetalle.setIntParaTipoDescuentoCod(mantCuenta.getCaptacion().getIntParaTipoDescuentoCod());
											if(intValorMantenimientoCta.equals(mantCuenta.getCaptacion().getId().getIntItem())){
												conceptoDetalle.setBdMontoConcepto(mantCuenta.getCaptacion().getBdValorConfiguracion());
											}
											conceptoDetalle.setBdSaldoDetalle(new BigDecimal(0));
											//cuentaConcepto.getListaCuentaDetalleBeneficio().add(detalleBeneficio);
											break;
										}
									}
								}
							}
						}
						cuentaConcepto.getListaCuentaConceptoDetalle().add(conceptoDetalle);
					}
				}
			}
		}
		
		try {
			cuentaFacade.modificarCuenta(beanCuenta, beanSocioComp.getPersona().getIntIdPersona());
			limpiarFormAperturaCuenta();
			formTiposConceptoRendered = false;
			blnShowDivFormAperturaCuenta = false;
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}*/
}