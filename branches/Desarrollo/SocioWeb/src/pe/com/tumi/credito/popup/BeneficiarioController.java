package pe.com.tumi.credito.popup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.popup.domain.Beneficiario;
import pe.com.tumi.popup.domain.Comunicacion;
import pe.com.tumi.popup.domain.Domicilio;
import pe.com.tumi.popup.domain.VinculoBeneficio;
import pe.com.tumi.seguridad.domain.BeanSesion;

public class BeneficiarioController {
	protected   static Logger 		    log = Logger.getLogger(JuridicaController.class);
	protected 	static SimpleDateFormat 	sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	protected 	static SimpleDateFormat 	sdfshort = new SimpleDateFormat("dd/MM/yyyy");
	private Persona 					beanBeneficiario = new Persona();
	private BeanSesion 					beanSesion = new BeanSesion();
	private VinculoBeneficio 			beanVinculoBenef = new VinculoBeneficio();
	private Domicilio 					beanDomicilio = new Domicilio();
	private Comunicacion				beanComunicacion = new Comunicacion();
	private Integer 					intTipoVinculo;
	private Integer 					intTipoPersona;
	private Integer 					intTipoDoc;
	private String 						strNroDoc;
	private Date 						daFechaNacimiento;
	private Boolean 					chkAportes;
	private Boolean 					chkFondoSepelio;
	private Boolean 					chkFondoRetiro;
	private Float 						flPorcAportes = new Float(0.0);
	private Float 						flPorcFondoSepelio = new Float(0.0);
	private Float 						flPorcFondoRetiro = new Float(0.0);
	
	private Integer						hiddenIdPersona;
	private ArrayList					ArrayDomicilio = new ArrayList();
	
	//Descripciones de Combos
	private String						hiddenStrTipoVinculo;
	private String						hiddenStrTipoPersona;
	
	private static Integer				intIdPersona;
	///////////////////////////////
	private Boolean 					formBeneficiario = false;
	private Boolean 					formEnableDisableAportes = true;
	private Boolean 					formEnableDisableFondoSep = true;
	private Boolean 					formEnableDisableFondoReten = true;
	private Boolean 					imgFotoBenef = false;
	private Boolean 					imgFirmaBenef = false;
	private Boolean 					chkDomicilio;
	private Boolean 					chkComunicacion;
	private Boolean 					formEnableDisableDomicilio = true;
	private Boolean 					formEnableDisableComunicacion = true;
	// Mensajes de Error
	private String 						msgTipoVinculo;
	private String 						msgTipoDoc;
	private String 						msgNroDoc;
	private String 						msgApepat;
	private String 						msgApemat;
	private String 						msgNombres;
	
	//Referencia al Controlador de Popups
	private DomicilioController 		domicilioController = (DomicilioController) getSessionBean("domicilioController");
	private ComunicacionController 		comunicacionController = (ComunicacionController) getSessionBean("comunicacionController");

	// Getters y Setters

	public Persona getBeanBeneficiario() {
		return beanBeneficiario;
	}

	public void setBeanBeneficiario(Persona beanBeneficiario) {
		this.beanBeneficiario = beanBeneficiario;
	}

	public BeanSesion getBeanSesion() {
		return beanSesion;
	}

	public void setBeanSesion(BeanSesion beanSesion) {
		this.beanSesion = beanSesion;
	}

	public VinculoBeneficio getBeanVinculoBenef() {
		return beanVinculoBenef;
	}

	public void setBeanVinculoBenef(VinculoBeneficio beanVinculoBenef) {
		this.beanVinculoBenef = beanVinculoBenef;
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

	public Integer getIntTipoVinculo() {
		return intTipoVinculo;
	}

	public void setIntTipoVinculo(Integer intTipoVinculo) {
		this.intTipoVinculo = intTipoVinculo;
	}

	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}

	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
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

	public Date getDaFechaNacimiento() {
		return daFechaNacimiento;
	}

	public void setDaFechaNacimiento(Date daFechaNacimiento) {
		this.daFechaNacimiento = daFechaNacimiento;
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

	public Float getFlPorcAportes() {
		return flPorcAportes;
	}

	public void setFlPorcAportes(Float flPorcAportes) {
		this.flPorcAportes = flPorcAportes;
	}

	public Float getFlPorcFondoSepelio() {
		return flPorcFondoSepelio;
	}

	public void setFlPorcFondoSepelio(Float flPorcFondoSepelio) {
		this.flPorcFondoSepelio = flPorcFondoSepelio;
	}

	public Float getFlPorcFondoRetiro() {
		return flPorcFondoRetiro;
	}

	public void setFlPorcFondoRetiro(Float flPorcFondoRetiro) {
		this.flPorcFondoRetiro = flPorcFondoRetiro;
	}

	public Integer getHiddenIdPersona() {
		return hiddenIdPersona;
	}

	public void setHiddenIdPersona(Integer hiddenIdPersona) {
		this.hiddenIdPersona = hiddenIdPersona;
	}

	public ArrayList getArrayDomicilio() {
		return ArrayDomicilio;
	}

	public void setArrayDomicilio(ArrayList arrayDomicilio) {
		ArrayDomicilio = arrayDomicilio;
	}

	public String getHiddenStrTipoVinculo() {
		return hiddenStrTipoVinculo;
	}

	public void setHiddenStrTipoVinculo(String hiddenStrTipoVinculo) {
		this.hiddenStrTipoVinculo = hiddenStrTipoVinculo;
	}

	public String getHiddenStrTipoPersona() {
		return hiddenStrTipoPersona;
	}

	public void setHiddenStrTipoPersona(String hiddenStrTipoPersona) {
		this.hiddenStrTipoPersona = hiddenStrTipoPersona;
	}

	public static Integer getIntIdPersona() {
		return intIdPersona;
	}

	public static void setIntIdPersona(Integer intIdPersona) {
		BeneficiarioController.intIdPersona = intIdPersona;
	}

	public Boolean getFormBeneficiario() {
		return formBeneficiario;
	}

	public void setFormBeneficiario(Boolean formBeneficiario) {
		this.formBeneficiario = formBeneficiario;
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

	public Boolean getImgFotoBenef() {
		return imgFotoBenef;
	}

	public void setImgFotoBenef(Boolean imgFotoBenef) {
		this.imgFotoBenef = imgFotoBenef;
	}

	public Boolean getImgFirmaBenef() {
		return imgFirmaBenef;
	}

	public void setImgFirmaBenef(Boolean imgFirmaBenef) {
		this.imgFirmaBenef = imgFirmaBenef;
	}

	public Boolean getChkDomicilio() {
		return chkDomicilio;
	}

	public void setChkDomicilio(Boolean chkDomicilio) {
		this.chkDomicilio = chkDomicilio;
	}

	public Boolean getChkComunicacion() {
		return chkComunicacion;
	}

	public void setChkComunicacion(Boolean chkComunicacion) {
		this.chkComunicacion = chkComunicacion;
	}

	public Boolean getFormEnableDisableDomicilio() {
		return formEnableDisableDomicilio;
	}

	public void setFormEnableDisableDomicilio(Boolean formEnableDisableDomicilio) {
		this.formEnableDisableDomicilio = formEnableDisableDomicilio;
	}

	public Boolean getFormEnableDisableComunicacion() {
		return formEnableDisableComunicacion;
	}

	public void setFormEnableDisableComunicacion(
			Boolean formEnableDisableComunicacion) {
		this.formEnableDisableComunicacion = formEnableDisableComunicacion;
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

	public String getMsgApepat() {
		return msgApepat;
	}

	public void setMsgApepat(String msgApepat) {
		this.msgApepat = msgApepat;
	}

	public String getMsgApemat() {
		return msgApemat;
	}

	public void setMsgApemat(String msgApemat) {
		this.msgApemat = msgApemat;
	}

	public String getMsgNombres() {
		return msgNombres;
	}

	public void setMsgNombres(String msgNombres) {
		this.msgNombres = msgNombres;
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
	
	// ---------------------------------------------------
	// Métodos de BeneficiarioController
	// ---------------------------------------------------
	//@Override
	/*public void init(){
		log.info("-----------------------Debugging BeneficiarioController.init-----------------------------");
		log.info("entro....");
		Beneficiario ben = new Beneficiario();
		setBeanBeneficiario(ben);
	}*/
	
	public void cancelarBeneficiario(ActionEvent event){
		setFormBeneficiario(false);
		limpiarFormBeneficiario();
	}
	
	public void limpiarFormBeneficiario(){
		Persona ben = new Persona();
		ben.setNatural(new Natural());
		setBeanBeneficiario(ben);
		VinculoBeneficio vinc = new VinculoBeneficio();
		setBeanVinculoBenef(vinc);
		setFormEnableDisableAportes(true);
		setFormEnableDisableFondoSep(true);
		setFormEnableDisableFondoReten(true);
		setChkAportes(false);
		setChkFondoSepelio(false);
		setChkFondoRetiro(false);
	}
	
	public void validarBeneficiario(ActionEvent event) throws ParseException {
		log.info("-----------------------Debugging BeneficiarioController.validarBeneficiario-----------------------------");
		log.info("Se ha seteado el Service");
		Boolean bValidar = true;
		
		if (getIntTipoDoc() == null || getIntTipoDoc() == 0) {
			bValidar = false;
			setMsgTipoDoc(" * Debe seleccionar el Tipo de Documento.");
		} else {
			setMsgTipoDoc("");
		}
		if (getStrNroDoc() == null || getStrNroDoc().equals("")) {
			bValidar = false;
			setMsgNroDoc(" * Debe ingresar el Nro. de Documento.");
		} else {
			setMsgNroDoc("");
		}
		
		if(getIntTipoVinculo()==0){
			bValidar = false;
			setMsgTipoVinculo(" * Debe seleccionar un Tipo de Vínculo");
		}else{
			setMsgTipoVinculo("");
		}
		
		// Obteniendo Labels de los SelectOneMenu
		//UIComponent cboTipoDocumento = getUIComponent("frmBeneficiario:cboTipoDoc");
		//String lblCboTipoDocumento = getSelectOneLabel(cboTipoDocumento);
		
		if (bValidar == true) {
			Persona perNatural = new Persona();
			perNatural.setNatural(new Natural());
			//perNatural.getNatural().setIntTipoIdentidadCod(getIntTipoDoc());
			//perNatural.getNatural().setStrNroIdentidad(getStrNroDoc());
			
			try {
	    		PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
	    		//natural = personaFacade.getListaNaturalPorDocIdentidad(natural);
	    		perNatural = personaFacade.getPersonaNaturalPorIdPersona(getIntTipoDoc());
	    		log.info("Se obtuvo natural.intIdPersona: "+perNatural.getIntIdPersona());
			} catch (EJBFactoryException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			if(perNatural != null){
				log.info("PERS_IDPERSONA_N = " + perNatural.getIntIdPersona());
				log.info("NATU_APELLIDOPATERNO_V = " + perNatural.getNatural().getStrApellidoPaterno());
				log.info("NATU_APELLIDOMATERNO_V = " + perNatural.getNatural().getStrApellidoMaterno());
				log.info("NATU_NOMBRES_V = " + perNatural.getNatural().getStrNombres());
				log.info("NATU_FECHANACIMIENTO_D = " + perNatural.getNatural().getDtFechaNacimiento());
				//log.info("NATU_IDTIPOIDENTIDAD_N = " + perNatural.getNatural().getIntTipoIdentidadCod());
				//log.info("NATU_NROIDENTIDAD_C = " + perNatural.getNatural().getStrNroIdentidad());
				log.info("NATU_IDSEXO_N = " + perNatural.getNatural().getIntSexoCod());
				log.info("NATU_IDESTADOCIVIL_N = " + perNatural.getNatural().getIntEstadoCivilCod());
				//log.info("VINC_IDTIPOVINCULO_N = " + ben.getIntIdTipoVinculo());
				//log.info("NATU_FOTO_C = " 		+ perNatural.getNatural().getStrFoto());
				//log.info("NATU_FIRMA_C = " 		+ perNatural.getNatural().getStrFirma());
				//log.info("N_PORCAPORTES = " 	+ ben.getFlPorcAportes());
				//log.info("N_PORCFONDOSEPELIO = " + ben.getFlPorcFondoSepelio());
				//log.info("N_PORCFONDORETIRO = " + ben.getFlPorcFondoRetiro());
				
				String daFecFin = "" + sdf.format(perNatural.getNatural().getDtFechaNacimiento()) == null ? "" : sdf.format(perNatural.getNatural().getDtFechaNacimiento());
				Date fecNac = (daFecFin == null || daFecFin.equals("") ? null : Constante.sdf.parse(daFecFin));
				setDaFechaNacimiento(fecNac);
				
				//natural.setStrFoto(ConstanteReporte.RUTA_FOTOS + natural.getStrFoto());
				//setImgFotoBenef(!perNatural.getNatural().getStrFoto().equals(""));
				//log.info("ben.getStrFoto(): "+perNatural.getNatural().getStrFoto());
				
				//natural.setStrFirma(ConstanteReporte.RUTA_FOTOS + natural.getStrFirma());
				//setImgFirmaBenef(!perNatural.getNatural().getStrFirma().equals(""));
				//log.info("ben.getStrFirma(): "+perNatural.getNatural().getStrFirma());
				
				//setFlPorcAportes(n.getFlPorcAportes());
				//setFlPorcFondoSepelio(ben.getFlPorcFondoSepelio());
				//setFlPorcFondoRetiro(ben.getFlPorcFondoRetiro());
				/*
				log.info("ben.getFlPorcAportes(): "+ben.getFlPorcAportes());
				setFormEnableDisableAportes(ben.getFlPorcAportes()==null||ben.getFlPorcAportes()==0.0);
				setChkAportes(ben.getFlPorcAportes()!=null && ben.getFlPorcAportes()!=0.0);
				log.info("ben.getFlPorcFondoSepelio(): "+ben.getFlPorcFondoSepelio());
				setFormEnableDisableFondoSep(ben.getFlPorcFondoSepelio()==null||ben.getFlPorcFondoSepelio()==0.0);
				setChkFondoSepelio(ben.getFlPorcFondoSepelio()!=null && ben.getFlPorcFondoSepelio()!=0.0);
				log.info("ben.getFlPorcFondoRetiro(): "+ben.getFlPorcFondoRetiro());
				setFormEnableDisableFondoReten(ben.getFlPorcFondoRetiro()==null||ben.getFlPorcFondoRetiro()==0.0);
				setChkFondoRetiro(ben.getFlPorcFondoRetiro()!=null && ben.getFlPorcFondoRetiro()!=0.0);
				*/
				setHiddenIdPersona(perNatural.getIntIdPersona());
				setIntIdPersona(perNatural.getIntIdPersona());
				
				domicilioController.listarDomicilio(perNatural.getIntIdPersona(), null);
				comunicacionController.listarComunicacion(perNatural.getIntIdPersona(), null);
			}else{
				limpiarFormBeneficiario();
				
				setDaFechaNacimiento(null);
				if(domicilioController.getBeanListDomicilio()!=null){
					domicilioController.getBeanListDomicilio().clear();
				}
				if(comunicacionController.getBeanListComunicacion()!=null){
					comunicacionController.getBeanListComunicacion().clear();
				}
				//domicilioController.limpiarFormDomicilio();
				//comunicacionController.limpiarFormComunicacion();
				setFlPorcAportes(new Float(0.0));
				setFlPorcFondoSepelio(new Float(0.0));
				setFlPorcFondoRetiro(new Float(0.0));
				setImgFirmaBenef(false);
				setImgFotoBenef(false);
			}
			
			//ben.setStrTipoPersonaBenef(getHiddenStrTipoPersona()==null||getHiddenStrTipoPersona().equals("")?"Persona Natural":getHiddenStrTipoPersona());
			//ben.setStrTipoVinculoBenef(getHiddenStrTipoVinculo());
			//perNatural.getNatural().setIntTipoIdentidadCod(getIntTipoDoc());
			//ben.setStrTipoDocBenef(lblCboTipoDocumento);
			//ben.setStrNroDocBenef(lblCboTipoDocumento + ": " + getStrNroDoc());
			//perNatural.getNatural().setStrNroIdentidad(getStrNroDoc());
			setFormBeneficiario(true);
			setBeanBeneficiario(perNatural);
		}
	}
	
	public void grabarBeneficiario(ActionEvent event) throws ParseException {
		log.info("---------------------Debugging UsuarioController.grabarHojaPlaneamiento-------------------");
		log.info("se ha seteado el service");
		
		Persona ben = (Persona) getBeanBeneficiario();
		
		//log.info("FileUploadBean.getStrNombreFotoBeneficiario(): "+FileUploadBean.getStrNombreFotoBeneficiario());
		//log.info("FileUploadBean.getStrNombreFirmaBeneficiario(): "+FileUploadBean.getStrNombreFirmaBeneficiario());
		String strFotoBeneficiario	= ""; //FileUploadBean.getStrNombreFotoBeneficiario();
		String strFirmaBeneficiario	= ""; //FileUploadBean.getStrNombreFirmaBeneficiario();
		
		Date daFecNacimiento = getDaFechaNacimiento();
		//String daFechaFin = (daFecNacimiento == null ? "" : Constante.sdf.format(daFecNacimiento));
		ben.getNatural().setDtFechaNacimiento(daFecNacimiento);
		
		//ben.setIntIdTipoVinculo(getIntTipoVinculo());
		//ben.setIntIdEmpresaBeneficiario(beanSesion.getIntIdEmpresa());
		ben.setIntIdPersona(ben.getIntIdPersona());
		
		//ben.setIntIdEmpresaSocio(beanSesion.getIntIdEmpresa());
		//ben.setIntIdPersonaSocio(2487);
		
		//log.info("ben.getStrFoto(): "+ben.getNatural().getStrFoto());
		//log.info("ben.getStrFirma(): "+ben.getNatural().getStrFirma());
		String strFoto = ""; /*(ben.getStrFoto()==null||ben.getStrFoto().equals("")?"":ben.getStrFoto().
							replace(ConstanteReporte.RUTA_FOTOS, ""));*/
		String strFirma = ""; /*(ben.getStrFirma()==null||ben.getStrFirma().equals("")?"":ben.getStrFirma().
							replace(ConstanteReporte.RUTA_FOTOS, ""));*/
		/*
		ben.getNatural().setStrFoto(strFotoBeneficiario==null||strFotoBeneficiario.equals("")?strFoto:
						strFotoBeneficiario);
		ben.getNatural().setStrFirma(strFirmaBeneficiario==null||strFirmaBeneficiario.equals("")?strFirma:
						strFirmaBeneficiario);
		*/
		Boolean bValidar = true;
		// if(hojaPlan.getnIdAmpliacion()==null){
		if (ben.getNatural().getStrApellidoPaterno().equals("")) {
			setMsgApepat("* El campo Apellido Paterno debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgApepat("");
		}
		if (ben.getNatural().getStrApellidoMaterno().equals("")) {
			setMsgApemat("* El campo Apellido Materno debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgApemat("");
		}
		if (ben.getNatural().getStrNombres().equals("")) {
			setMsgNombres("* El campo Nombres debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgNombres("");
		}
		
		if (bValidar == true) {
			try {
	    		PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
	    		ben = personaFacade.grabarPersonaNatural(ben);
	    		log.info("natural.getIntIdPersona: "+ben.getIntIdPersona());
			} catch (EJBFactoryException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			VinculoBeneficio vinc = new VinculoBeneficio();
			vinc.setIntIdEmpresaBeneficiario(beanSesion.getIntIdEmpresa());
			vinc.setIntIdPersonaBeneficiario(ben.getIntIdPersona());
			vinc.setIntIdTipoVinculo(getIntTipoVinculo());
			//vinc.setIntIdEmpresaVinculo(ben.getIntIdEmpresaSocio());
			//vinc.setIntIdPersonaVinculo(ben.getIntIdPersonaSocio());
			
			log.info("vinc: " + vinc);
			/*
			if (getChkAportes() != null) {
				vinc.setIntIdTipoBeneficio(1);
				vinc.setFlPorcentaje(getFlPorcAportes());
				try {
					getPopupService().grabarVinculoBeneficio(vinc);
				} catch (DaoException e) {
					log.info("ERROR  getCreditosService().grabarVinculoBeneficio(vinc:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (getChkFondoSepelio() != null) {
				vinc.setIntIdTipoBeneficio(2);
				log.info("getFlPorcFondoSepelio(): "+getFlPorcFondoSepelio());
				vinc.setFlPorcentaje(getFlPorcFondoSepelio());
				try {
					getPopupService().grabarVinculoBeneficio(vinc);
				} catch (DaoException e) {
					log.info("ERROR  getCreditosService().grabarVinculoBeneficio(vinc:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (getChkFondoRetiro() != null) {
				vinc.setIntIdTipoBeneficio(3);
				vinc.setFlPorcentaje(getFlPorcFondoRetiro());
				log.info("getFlPorcFondoRetiro(): "+getFlPorcFondoRetiro());
				try {
					getPopupService().grabarVinculoBeneficio(vinc);
				} catch (DaoException e) {
					log.info("ERROR  getCreditosService().grabarVinculoBeneficio(vinc:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			*/
			/*
			HashMap prmtPersona = new HashMap();
			prmtPersona.put("pIntIdPersona", 	ben.getIntIdPersona());
			
			try{
				getPopupService().eliminarPersonaDet(prmtPersona);
			}catch(DaoException e){
				log.info("ERROR  getService().eliminarPersonaDet() " + e.getMessage());
				e.printStackTrace();
			}
			
			for(int i=0; i<domicilioController.getBeanListDomicilio().size(); i++){
				setPopupService(domicilioService);
				log.info("Se ha seteado el service de Domicilio");
				Domicilio dom = new Domicilio();
				dom =(Domicilio) domicilioController.getBeanListDomicilio().get(i);
				dom.setIntIdPersona(ben.getIntIdPersona());
				try {
					getPopupService().grabarDomicilio(dom);
				} catch (DaoException e) {
					log.info("ERROR  getService().grabarDomicilio(dom:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(int i=0; i<comunicacionController.getBeanListComunicacion().size(); i++){
				setPopupService(comunicacionService);
				log.info("Se ha seteado el service de Comunicacion");
				Comunicacion com = new Comunicacion();
				com =(Comunicacion) comunicacionController.getBeanListComunicacion().get(i);
				com.setIntIdPersona(ben.getIntIdPersona());
				try {
					getPopupService().grabarComunicacion(com);
				} catch (DaoException e) {
					log.info("ERROR  getService().grabarComunicacion(com:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			*/
			validarBeneficiario(event);
			setMessageSuccess("Los datos se actualizaron satisfactoriamente ");
		}
	}
	
	public void enableDisableControls(ActionEvent event) {
		log.info("----------------Debugging BeneficiarioController.enableDisableControls-------------------");
		setFormEnableDisableAportes(getChkAportes()!=true);
		setFlPorcAportes(getChkAportes()!=true?0:getFlPorcAportes());
		setFormEnableDisableFondoSep(getChkFondoSepelio()!=true);
		setFlPorcFondoSepelio(getChkFondoSepelio()!=true?0:getFlPorcFondoSepelio());
		setFormEnableDisableFondoReten(getChkFondoRetiro()!=true);
		setFlPorcFondoRetiro(getChkFondoRetiro()!=true?0:getFlPorcFondoRetiro());
		setFormEnableDisableDomicilio(getChkDomicilio()!=true);
		setFormEnableDisableComunicacion(getChkComunicacion()!=true);
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		return sesion.getAttribute(beanName);
	}
	
	protected void setMessageSuccess(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(msg));
	}
}