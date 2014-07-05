package pe.com.tumi.popup.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.ConstanteReporte;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.file.controller.FileUploadBean;
import pe.com.tumi.popup.controller.ComunicacionController;
import pe.com.tumi.popup.controller.DomicilioController;
import pe.com.tumi.popup.domain.Beneficiario;
import pe.com.tumi.popup.domain.Comunicacion;
import pe.com.tumi.popup.domain.Domicilio;
import pe.com.tumi.popup.domain.VinculoBeneficio;
import pe.com.tumi.popup.service.impl.BeneficiarioServiceImpl;
import pe.com.tumi.popup.service.impl.ComunicacionServiceImpl;
import pe.com.tumi.popup.service.impl.DomicilioServiceImpl;
import pe.com.tumi.seguridad.domain.BeanSesion;

public class BeneficiarioController extends GenericController {
	private BeneficiarioServiceImpl 	beneficiarioService;
	private DomicilioServiceImpl		domicilioService;
	private ComunicacionServiceImpl		comunicacionService;
	private Beneficiario 				beanBeneficiario = new Beneficiario();
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
	private DomicilioController 		domicilioController = (DomicilioController) getSpringBean("domicilioController");
	private ComunicacionController 		comunicacionController = (ComunicacionController) getSpringBean("comunicacionController");

	// Getters y Setters
	public BeneficiarioServiceImpl getBeneficiarioService() {
		return beneficiarioService;
	}

	public void setBeneficiarioService(
			BeneficiarioServiceImpl beneficiarioService) {
		this.beneficiarioService = beneficiarioService;
	}
	
	public DomicilioServiceImpl getDomicilioService() {
		return domicilioService;
	}

	public void setDomicilioService(DomicilioServiceImpl domicilioService) {
		this.domicilioService = domicilioService;
	}

	public ComunicacionServiceImpl getComunicacionService() {
		return comunicacionService;
	}

	public void setComunicacionService(ComunicacionServiceImpl comunicacionService) {
		this.comunicacionService = comunicacionService;
	}

	public Beneficiario getBeanBeneficiario() {
		return beanBeneficiario;
	}

	public void setBeanBeneficiario(Beneficiario beanBeneficiario) {
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
		Beneficiario ben = new Beneficiario();
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
	
	public void validarBeneficiario(ActionEvent event) throws DaoException, ParseException {
		log.info("-----------------------Debugging BeneficiarioController.validarBeneficiario-----------------------------");
		setPopupService(beneficiarioService);
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
		UIComponent cboTipoDocumento = getUIComponent("frmBeneficiario:cboTipoDoc");
		String lblCboTipoDocumento = getSelectOneLabel(cboTipoDocumento);
		
		if (bValidar == true) {
			ArrayList arrayBeneficiario = new ArrayList();
			HashMap prmtBusq = new HashMap();
			prmtBusq.put("pIntIdPersona", null);
			prmtBusq.put("pIntTipoDoc", getIntTipoDoc());
			prmtBusq.put("pStrNroDoc", getStrNroDoc());
			
			if(arrayBeneficiario.size() == 0){
				limpiarFormBeneficiario();
			}
			
			arrayBeneficiario = getPopupService().listarPersonaNatural(prmtBusq);
			Beneficiario ben = new Beneficiario();
			if(arrayBeneficiario.size() > 0){
				ben = (Beneficiario) arrayBeneficiario.get(0);
				log.info("PERS_IDPERSONA_N = " + ben.getIntIdPersona());
				log.info("NATU_APELLIDOPATERNO_V = " + ben.getStrApPaterno());
				log.info("NATU_APELLIDOMATERNO_V = " + ben.getStrApMaterno());
				log.info("NATU_NOMBRES_V = " + ben.getStrNombres());
				log.info("NATU_FECHANACIMIENTO_D = " + ben.getStrFecNac());
				log.info("NATU_IDTIPOIDENTIDAD_N = " + ben.getIntTipoDocumento());
				log.info("V_TIPOIDENTIDAD = " + ben.getStrTipoDocumento());
				log.info("NATU_NROIDENTIDAD_C = " + ben.getStrNroDoc());
				log.info("NATU_IDSEXO_N = " + ben.getIntIdSexo());
				log.info("NATU_IDESTADOCIVIL_N = " + ben.getIntEstCivil());
				log.info("VINC_IDTIPOVINCULO_N = " + ben.getIntIdTipoVinculo());
				log.info("NATU_FOTO_C = " 		+ ben.getStrFoto());
				log.info("NATU_FIRMA_C = " 		+ ben.getStrFirma());
				log.info("N_PORCAPORTES = " 	+ ben.getFlPorcAportes());
				log.info("N_PORCFONDOSEPELIO = " + ben.getFlPorcFondoSepelio());
				log.info("N_PORCFONDORETIRO = " + ben.getFlPorcFondoRetiro());
				
				String daFecFin = "" + ben.getStrFecNac() == null ? "" : ben.getStrFecNac();
				Date fecNac = (daFecFin == null || daFecFin.equals("") ? null : Constante.sdf.parse(daFecFin));
				setDaFechaNacimiento(fecNac);
				
				ben.setStrFoto(ConstanteReporte.RUTA_FOTOS + ben.getStrFoto());
				setImgFotoBenef(!ben.getStrFoto().equals(""));
				log.info("ben.getStrFoto(): "+ben.getStrFoto());
				
				ben.setStrFirma(ConstanteReporte.RUTA_FOTOS + ben.getStrFirma());
				setImgFirmaBenef(!ben.getStrFirma().equals(""));
				log.info("ben.getStrFirma(): "+ben.getStrFirma());
				
				setFlPorcAportes(ben.getFlPorcAportes());
				setFlPorcFondoSepelio(ben.getFlPorcFondoSepelio());
				setFlPorcFondoRetiro(ben.getFlPorcFondoRetiro());
				
				log.info("ben.getFlPorcAportes(): "+ben.getFlPorcAportes());
				setFormEnableDisableAportes(ben.getFlPorcAportes()==null||ben.getFlPorcAportes()==0.0);
				setChkAportes(ben.getFlPorcAportes()!=null && ben.getFlPorcAportes()!=0.0);
				log.info("ben.getFlPorcFondoSepelio(): "+ben.getFlPorcFondoSepelio());
				setFormEnableDisableFondoSep(ben.getFlPorcFondoSepelio()==null||ben.getFlPorcFondoSepelio()==0.0);
				setChkFondoSepelio(ben.getFlPorcFondoSepelio()!=null && ben.getFlPorcFondoSepelio()!=0.0);
				log.info("ben.getFlPorcFondoRetiro(): "+ben.getFlPorcFondoRetiro());
				setFormEnableDisableFondoReten(ben.getFlPorcFondoRetiro()==null||ben.getFlPorcFondoRetiro()==0.0);
				setChkFondoRetiro(ben.getFlPorcFondoRetiro()!=null && ben.getFlPorcFondoRetiro()!=0.0);
				
				setHiddenIdPersona(ben.getIntIdPersona());
				setIntIdPersona(ben.getIntIdPersona());
				
				//domicilioController.listarDomicilio(ben.getIntIdPersona(), null);
				comunicacionController.listarComunicacion(ben.getIntIdPersona(), null);
			}else{
				setDaFechaNacimiento(null);
				if(domicilioController.getListaDomicilio()!=null){
					domicilioController.getListaDomicilio().clear();
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
			ben.setStrTipoPersonaBenef(getHiddenStrTipoPersona()==null||getHiddenStrTipoPersona().equals("")?"Persona Natural":getHiddenStrTipoPersona());
			ben.setStrTipoVinculoBenef(getHiddenStrTipoVinculo());
			ben.setIntTipoDocumento(getIntTipoDoc());
			ben.setStrTipoDocBenef(lblCboTipoDocumento);
			ben.setStrNroDocBenef(lblCboTipoDocumento + ": " + getStrNroDoc());
			ben.setStrNroDoc(getStrNroDoc());
			setFormBeneficiario(true);
			setBeanBeneficiario(ben);
		}
	}
	
	public void grabarBeneficiario(ActionEvent event) throws DaoException, ParseException {
		log.info("---------------------Debugging UsuarioController.grabarHojaPlaneamiento-------------------");
		setPopupService(beneficiarioService);
		log.info("se ha seteado el service");
		
		Beneficiario ben = (Beneficiario) getBeanBeneficiario();
		
		log.info("FileUploadBean.getStrNombreFotoBeneficiario(): "+FileUploadBean.getStrNombreFotoBeneficiario());
		log.info("FileUploadBean.getStrNombreFirmaBeneficiario(): "+FileUploadBean.getStrNombreFirmaBeneficiario());
		String strFotoBeneficiario	= FileUploadBean.getStrNombreFotoBeneficiario();
		String strFirmaBeneficiario	= FileUploadBean.getStrNombreFirmaBeneficiario();
		
		Date daFecNacimiento = getDaFechaNacimiento();
		String daFechaFin = (daFecNacimiento == null ? "" : Constante.sdf.format(daFecNacimiento));
		ben.setStrFecNac(daFechaFin);
		
		ben.setIntIdTipoVinculo(getIntTipoVinculo());
		ben.setIntIdEmpresaBeneficiario(beanSesion.getIntIdEmpresa());
		ben.setIntIdPersona(ben.getIntIdPersona());
		
		ben.setIntIdEmpresaSocio(beanSesion.getIntIdEmpresa());
		ben.setIntIdPersonaSocio(2487);
		
		/*
		String strFoto = (ben.getStrFoto()==null||ben.getStrFoto().equals("")?"":ben.getStrFoto())
							.replace(ConstanteReporte.RUTA_FOTOS, "");
		String strFirma = (ben.getStrFirma()==null||ben.getStrFirma().equals("")?"":ben.getStrFirma())
							.replace(ConstanteReporte.RUTA_FOTOS, "");
		ben.setStrFoto(strFotoBeneficiario==null||strFotoBeneficiario.equals("")?strFoto:"");
		ben.setStrFirma(strFirmaBeneficiario==null||strFirmaBeneficiario.equals("")?strFirma:"");*/
		log.info("ben.getStrFoto(): "+ben.getStrFoto());
		log.info("ben.getStrFirma(): "+ben.getStrFirma());
		String strFoto = (ben.getStrFoto()==null||ben.getStrFoto().equals("")?"":ben.getStrFoto().
							replace(ConstanteReporte.RUTA_FOTOS, ""));
		String strFirma = (ben.getStrFirma()==null||ben.getStrFirma().equals("")?"":ben.getStrFirma().
							replace(ConstanteReporte.RUTA_FOTOS, ""));
		
		ben.setStrFoto(strFotoBeneficiario==null||strFotoBeneficiario.equals("")?strFoto:
						strFotoBeneficiario);
		ben.setStrFirma(strFirmaBeneficiario==null||strFirmaBeneficiario.equals("")?strFirma:
						strFirmaBeneficiario);
		
		Boolean bValidar = true;
		// if(hojaPlan.getnIdAmpliacion()==null){
		if (ben.getStrApPaterno().equals("")) {
			setMsgApepat("* El campo Apellido Paterno debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgApepat("");
		}
		if (ben.getStrApMaterno().equals("")) {
			setMsgApemat("* El campo Apellido Materno debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgApemat("");
		}
		if (ben.getStrNombres().equals("")) {
			setMsgNombres("* El campo Nombres debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgNombres("");
		}
		
		if (bValidar == true) {
			try {
				getPopupService().grabarBeneficiario(ben);
			} catch (DaoException e) {
				log.info("ERROR getCreditosService().grabarBeneficiario(usu:)"+ e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			VinculoBeneficio vinc = new VinculoBeneficio();
			vinc.setIntIdEmpresaBeneficiario(beanSesion.getIntIdEmpresa());
			vinc.setIntIdPersonaBeneficiario(ben.getIntIdPersona());
			vinc.setIntIdTipoVinculo(getIntTipoVinculo());
			vinc.setIntIdEmpresaVinculo(ben.getIntIdEmpresaSocio());
			vinc.setIntIdPersonaVinculo(ben.getIntIdPersonaSocio());
			
			log.info("vinc: " + vinc);
			
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
			
			HashMap prmtPersona = new HashMap();
			prmtPersona.put("pIntIdPersona", 	ben.getIntIdPersona());
			
			try{
				getPopupService().eliminarPersonaDet(prmtPersona);
			}catch(DaoException e){
				log.info("ERROR  getService().eliminarPersonaDet() " + e.getMessage());
				e.printStackTrace();
			}
			
			for(int i=0; i<domicilioController.getListaDomicilio().size(); i++){
				setPopupService(domicilioService);
				log.info("Se ha seteado el service de Domicilio");
				Domicilio dom = new Domicilio();
				//dom =(Domicilio) domicilioController.getListaDomicilio().get(i);
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
			validarBeneficiario(event);
			setMessageSuccess("Los datos se actualizaron satisfactoriamente ");
		}
	}
	
	public void enableDisableControls(ActionEvent event) throws DaoException {
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
}