package pe.com.tumi.creditos.controller;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import pe.com.tumi.admFormDoc.domain.AdmFormDoc;
import pe.com.tumi.admFormDoc.service.impl.AdmFormDocServiceImpl;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.creditos.domain.Aportes;
import pe.com.tumi.creditos.domain.CondSocio;
import pe.com.tumi.creditos.service.impl.MantCuentasServiceImpl;
import pe.com.tumi.seguridad.domain.BeanSesion;

/************************************************************************/
/* Nombre de la clase: MantCuentasController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de MantCuentas */
/* Ref. : */
/* Autor : Paul Rivera */
/* Versión : V1 */
/* Fecha creación : 08/02/2012 */
/* ********************************************************************* */

public class MantCuentasController extends GenericController {
	private AdmFormDocServiceImpl 	admFormDocService;
	private AdmFormDoc 				beanFormDoc = new AdmFormDoc();
	
	private MantCuentasServiceImpl mantCuentasService;
	private int rows = 5;
	private List<Aportes> beanListMantCuentas;
	
	private List beanListCondSocio;
	private Aportes beanMantCuenta = new Aportes();
	private CondSocio beanCondSocio = new CondSocio();
	private BeanSesion beanSesion = new BeanSesion();
	// Variables generales
	private Boolean formMantCuentasRendered = false;
	private Integer intIdEstadoMantCuenta;
	
	private Integer intEstadoUniversal;
	
	
	private String strNombreMantCuenta;
	private Integer intIdCondicionMantCuenta;
	private Integer intIdTipoConfig;
	//para q renderize el tipo de condiciòn laboral, este datro todavía no lo veo en la DB
	private Integer intIdTipoCondLab;
	
	private Date daFecIni;
	private Date daFecFin;
	private Integer intIdTipoPersona;
	private Boolean chkMantCuentaVigentes;
	private Boolean chkMantCuentaCaduco;
	private String rbCondSocio;
	private String strValManCuenta;
	
	// Variables de activación y desactivación de controles
	private Boolean chkNombMantCuenta;
	private Boolean enabDisabNombMantCuenta = true;
	
	private Boolean chkTipoCaptacionAfectaMantCuenta;
	private Boolean chkCondMantCuenta;
	private Boolean chkTodosMantCuenta;
	private Boolean enabDisabCondMantCuenta = true;
	private Boolean enabDisabTodosMantCuenta = true;
	private Boolean chkFechas;
	private Boolean enabDisabFechasMantCuenta = true;
	private String rbFecIndeterm;
	private Boolean fecFinMantCuentaRendered = true;
	private Date daFechaIni;
	private Date daFechaFin;
	private Boolean chkLimiteEdad;
	
	//Para el combo de estados
	private Integer intCboEstadosSesion;
	
	private    	ArrayList<SelectItem> 	cboEstados = new ArrayList<SelectItem>(); //1	
	
	//Mensajes de Error
	private String				msgTxtDescripcion;
	private String				msgTxtFechaIni;
	private String				msgTxtEstadoMantCuenta;
	private String				msgTxtTipoPersona;
	private String				msgTxtCondicionLaboral;
	private String				msgTxtTipoDscto;
	private String				msgTxtTipoConfig;
	private String				msgTxtMoneda;
	private String				msgTxtAplicacion;
	private String				msgTxtTNA;
	
	
	//para el estado de la Solicitud
	private String MsgTxtEstadoSolicitud;

	//para el estado de la solicitud
	private Integer intIdEstSolicitud; 

	public String getStrValManCuenta() {
		return strValManCuenta;
	}

	public void setStrValManCuenta(String strValManCuenta) {
		this.strValManCuenta = strValManCuenta;
	}

	public AdmFormDoc getBeanFormDoc() {
		return beanFormDoc;
	}

	public void setBeanFormDoc(AdmFormDoc beanFormDoc) {
		this.beanFormDoc = beanFormDoc;
	}

	public Integer getIntCboEstadosSesion() {
		return intCboEstadosSesion;
	}

	public void setIntCboEstadosSesion(Integer intCboEstadosSesion) {
		this.intCboEstadosSesion = intCboEstadosSesion;
	}

	// Referencia a ParametersController
	//private ParametersController parametersController = (ParametersController) getSpringBean("parametersController");

	// Getters y Setters
	public MantCuentasServiceImpl getMantCuentasService() {
		return mantCuentasService;
	}

	public void setMantCuentasService(
			MantCuentasServiceImpl mantCuentasService) {
		this.mantCuentasService = mantCuentasService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public List getBeanListMantCuentas() {
		return beanListMantCuentas;
	}

	public void setBeanListMantCuentas(List<Aportes> _beanListMantCuentas) {
		this.beanListMantCuentas = _beanListMantCuentas;
	}

	public List getBeanListCondSocio() {
		return beanListCondSocio;
	}

	public void setBeanListCondSocio(List beanListCondSocio) {
		this.beanListCondSocio = beanListCondSocio;
	}

	public Aportes getBeanMantCuenta() {
		return beanMantCuenta;
	}

	public void setBeanMantCuenta(Aportes beanMantCuenta) {
		this.beanMantCuenta = beanMantCuenta;
	}

	public CondSocio getBeanCondSocio() {
		return beanCondSocio;
	}

	public void setBeanCondSocio(CondSocio beanCondSocio) {
		this.beanCondSocio = beanCondSocio;
	}

	public BeanSesion getBeanSesion() {
		return beanSesion;
	}

	public void setBeanSesion(BeanSesion beanSesion) {
		this.beanSesion = beanSesion;
	}

	public Boolean getFormMantCuentasRendered() {
		return formMantCuentasRendered;
	}

	public void setFormMantCuentasRendered(Boolean formMantCuentasRendered) {
		this.formMantCuentasRendered = formMantCuentasRendered;
	}

	public Integer getIntIdEstadoMantCuenta() {
		return intIdEstadoMantCuenta;
	}

	public void setIntIdEstadoMantCuenta(Integer intIdEstadoMantCuenta) {
		this.intIdEstadoMantCuenta = intIdEstadoMantCuenta;
	}

	
	
	
	public Integer getIntIdTipoCondLab() {
		return intIdTipoCondLab;
	}

	public void setIntIdTipoCondLab(Integer intIdTipoCondLab) {
		this.intIdTipoCondLab = intIdTipoCondLab;
	}

	public String getStrNombreMantCuenta() {
		return strNombreMantCuenta;
	}

	public void setStrNombreMantCuenta(String strNombreMantCuenta) {
		this.strNombreMantCuenta = strNombreMantCuenta;
	}

	public Integer getIntIdCondicionMantCuenta() {
		return intIdCondicionMantCuenta;
	}

	public void setIntIdCondicionMantCuenta(
			Integer intIdCondicionMantCuenta) {
		this.intIdCondicionMantCuenta = intIdCondicionMantCuenta;
	}

	public Integer getIntIdTipoConfig() {
		return intIdTipoConfig;
	}

	public void setIntIdTipoConfig(Integer intIdTipoConfig) {
		this.intIdTipoConfig = intIdTipoConfig;
	}

	public Integer getIntIdTipoPersona() {
		return intIdTipoPersona;
	}

	public void setIntIdTipoPersona(Integer intIdTipoPersona) {
		this.intIdTipoPersona = intIdTipoPersona;
	}


	public Boolean getChkMantCuentaVigentes() {
		return chkMantCuentaVigentes;
	}

	public void setChkMantCuentaVigentes(Boolean chkMantCuentaVigentes) {
		this.chkMantCuentaVigentes = chkMantCuentaVigentes;
	}

	public Boolean getChkMantCuentaCaduco() {
		return chkMantCuentaCaduco;
	}

	public void setChkMantCuentaCaduco(Boolean chkMantCuentaCaduco) {
		this.chkMantCuentaCaduco = chkMantCuentaCaduco;
	}

	public String getRbCondSocio() {
		return rbCondSocio;
	}

	public void setRbCondSocio(String rbCondSocio) {
		this.rbCondSocio = rbCondSocio;
	}

	public Boolean getChkNombMantCuenta() {
		return chkNombMantCuenta;
	}

	public void setChkNombMantCuenta(Boolean chkNombMantCuenta) {
		this.chkNombMantCuenta = chkNombMantCuenta;
	}

	public Boolean getEnabDisabNombMantCuenta() {
		return enabDisabNombMantCuenta;
	}

	public void setEnabDisabNombMantCuenta(Boolean enabDisabNombMantCuenta) {
		this.enabDisabNombMantCuenta = enabDisabNombMantCuenta;
	}

	public Boolean getChkCondMantCuenta() {
		return chkCondMantCuenta;
	}

	public void setChkCondMantCuenta(Boolean chkCondMantCuenta) {
		this.chkCondMantCuenta = chkCondMantCuenta;
	}

	
	
	
	
	public Boolean getChkTipoCaptacionAfectaMantCuenta() {
		return chkTipoCaptacionAfectaMantCuenta;
	}

	public void setChkTipoCaptacionAfectaMantCuenta(
			Boolean chkTipoCaptacionAfectaMantCuenta) {
		this.chkTipoCaptacionAfectaMantCuenta = chkTipoCaptacionAfectaMantCuenta;
	}

	public Boolean getChkTodosMantCuenta() {
		return chkTodosMantCuenta;
	}

	public void setChkTodosMantCuenta(Boolean chkTodosMantCuenta) {
		this.chkTodosMantCuenta = chkTodosMantCuenta;
	}

	public Boolean getEnabDisabCondMantCuenta() {
		return enabDisabCondMantCuenta;
	}

	public void setEnabDisabCondMantCuenta(Boolean enabDisabCondMantCuenta) {
		this.enabDisabCondMantCuenta = enabDisabCondMantCuenta;
	}

	public Boolean getEnabDisabTodosMantCuenta() {
		return enabDisabTodosMantCuenta;
	}

	public void setEnabDisabTodosMantCuenta(Boolean enabDisabTodosMantCuenta) {
		this.enabDisabTodosMantCuenta = enabDisabTodosMantCuenta;
	}

	public Boolean getChkFechas() {
		return chkFechas;
	}

	public void setChkFechas(Boolean chkFechas) {
		this.chkFechas = chkFechas;
	}

	public Boolean getEnabDisabFechasMantCuenta() {
		return enabDisabFechasMantCuenta;
	}

	public void setEnabDisabFechasMantCuenta(Boolean enabDisabFechasMantCuenta) {
		this.enabDisabFechasMantCuenta = enabDisabFechasMantCuenta;
	}

	public Date getDaFecIni() {
		return daFecIni;
	}

	public void setDaFecIni(Date daFecIni) {
		this.daFecIni = daFecIni;
	}

	public Date getDaFecFin() {
		return daFecFin;
	}

	public void setDaFecFin(Date daFecFin) {
		this.daFecFin = daFecFin;
	}

	public String getRbFecIndeterm() {
		return rbFecIndeterm;
	}

	public void setRbFecIndeterm(String rbFecIndeterm) {
		this.rbFecIndeterm = rbFecIndeterm;
	}

	public Boolean getFecFinMantCuentaRendered() {
		return fecFinMantCuentaRendered;
	}

	public void setFecFinMantCuentaRendered(
			Boolean fecFinMantCuentaRendered) {
		this.fecFinMantCuentaRendered = fecFinMantCuentaRendered;
	}

	public Date getDaFechaIni() {
		return daFechaIni;
	}

	public void setDaFechaIni(Date daFechaIni) {
		this.daFechaIni = daFechaIni;
	}

	public Date getDaFechaFin() {
		return daFechaFin;
	}

	public void setDaFechaFin(Date daFechaFin) {
		this.daFechaFin = daFechaFin;
	}
/*
	public Boolean getChkTasaInteres() {
		return chkTasaInteres;
	}

	public void setChkTasaInteres(Boolean chkTasaInteres) {
		this.chkTasaInteres = chkTasaInteres;
	}

	public Boolean getEnabDisabTasaInt() {
		return enabDisabTasaInt;
	}

	public void setEnabDisabTasaInt(Boolean enabDisabTasaInt) {
		this.enabDisabTasaInt = enabDisabTasaInt;
	}

	public Boolean getEnabDisabTea() {
		return enabDisabTea;
	}

	public void setEnabDisabTea(Boolean enabDisabTea) {
		this.enabDisabTea = enabDisabTea;
	}

	public Boolean getEnabDisabTna() {
		return enabDisabTna;
	}

	public void setEnabDisabTna(Boolean enabDisabTna) {
		this.enabDisabTna = enabDisabTna;
	}
*/
	public Boolean getChkLimiteEdad() {
		return chkLimiteEdad;
	}

	public void setChkLimiteEdad(Boolean chkLimiteEdad) {
		this.chkLimiteEdad = chkLimiteEdad;
	}
/*
	public Boolean getEnabDisabLimiteEdad() {
		return enabDisabLimiteEdad;
	}

	public void setEnabDisabLimiteEdad(Boolean enabDisabLimiteEdad) {
		this.enabDisabLimiteEdad = enabDisabLimiteEdad;
	}

	public String getRbTasaInteres() {
		return rbTasaInteres;
	}

	public void setRbTasaInteres(String rbTasaInteres) {
		this.rbTasaInteres = rbTasaInteres;
	}
*/
	
	
public AdmFormDocServiceImpl getAdmFormDocService() {
		return admFormDocService;
	}

	public void setAdmFormDocService(AdmFormDocServiceImpl admFormDocService) {
		this.admFormDocService = admFormDocService;
	}

	public Integer getIntEstadoUniversal() {
		return intEstadoUniversal;
	}

	public void setIntEstadoUniversal(Integer intEstadoUniversal) {
		this.intEstadoUniversal = intEstadoUniversal;
	}

	public ArrayList<SelectItem> getCboEstados() {
		return cboEstados;
	}

	public void setCboEstados(ArrayList<SelectItem> cboEstados) {
		this.cboEstados = cboEstados;
	}

	public String getMsgTxtDescripcion() {
		return msgTxtDescripcion;
	}

	public void setMsgTxtDescripcion(String msgTxtDescripcion) {
		this.msgTxtDescripcion = msgTxtDescripcion;
	}

	public String getMsgTxtFechaIni() {
		return msgTxtFechaIni;
	}

	public void setMsgTxtFechaIni(String msgTxtFechaIni) {
		this.msgTxtFechaIni = msgTxtFechaIni;
	}


	public String getMsgTxtEstadoMantCuenta() {
		return msgTxtEstadoMantCuenta;
	}

	public void setMsgTxtEstadoMantCuenta(String msgTxtEstadoMantCuenta) {
		this.msgTxtEstadoMantCuenta = msgTxtEstadoMantCuenta;
	}

	public String getMsgTxtTipoPersona() {
		return msgTxtTipoPersona;
	}

	public void setMsgTxtTipoPersona(String msgTxtTipoPersona) {
		this.msgTxtTipoPersona = msgTxtTipoPersona;
	}



	public String getMsgTxtCondicionLaboral() {
		return msgTxtCondicionLaboral;
	}

	public void setMsgTxtCondicionLaboral(String msgTxtCondicionLaboral) {
		this.msgTxtCondicionLaboral = msgTxtCondicionLaboral;
	}

	public String getMsgTxtTipoDscto() {
		return msgTxtTipoDscto;
	}

	public void setMsgTxtTipoDscto(String msgTxtTipoDscto) {
		this.msgTxtTipoDscto = msgTxtTipoDscto;
	}

	public String getMsgTxtTipoConfig() {
		return msgTxtTipoConfig;
	}

	public void setMsgTxtTipoConfig(String msgTxtTipoConfig) {
		this.msgTxtTipoConfig = msgTxtTipoConfig;
	}

	public String getMsgTxtMoneda() {
		return msgTxtMoneda;
	}

	public void setMsgTxtMoneda(String msgTxtMoneda) {
		this.msgTxtMoneda = msgTxtMoneda;
	}

	public String getMsgTxtAplicacion() {
		return msgTxtAplicacion;
	}

	public void setMsgTxtAplicacion(String msgTxtAplicacion) {
		this.msgTxtAplicacion = msgTxtAplicacion;
	}

	public String getMsgTxtTNA() {
		return msgTxtTNA;
	}

	public void setMsgTxtTNA(String msgTxtTNA) {
		this.msgTxtTNA = msgTxtTNA;
	}

	
	
	
	public String getMsgTxtEstadoSolicitud() {
		return MsgTxtEstadoSolicitud;
	}

	public void setMsgTxtEstadoSolicitud(String msgTxtEstadoSolicitud) {
		MsgTxtEstadoSolicitud = msgTxtEstadoSolicitud;
	}

	public Integer getIntIdEstSolicitud() {
		return intIdEstSolicitud;
	}

	public void setIntIdEstSolicitud(Integer intIdEstSolicitud) {
		this.intIdEstSolicitud = intIdEstSolicitud;
	}

	/*
	public ParametersController getParametersController() {
		return parametersController;
	}

	public void setParametersController(
			ParametersController parametersController) {
		this.parametersController = parametersController;
	}
*/
	// Métodos a implementar
	/**************************************************************/
	/* Nombre : habilitarGrabarHojaPlan() */
	/*                                                    	 		*/
	/* Parametros. : Ninguno */
	/* Objetivo: Habilitar el Formulario para el llenado del mimso */
	/* Retorno : El formulario habilitado para su respectivo llenado */
	/**************************************************************/
	public void habilitarGrabarMantCuentas(ActionEvent event) {
		log.info("---------------Debugging MantCuentasController.habilitarGrabarMantCuentas------------------");
		setFormMantCuentasRendered(true);
		setMsgTxtEstadoSolicitud("");
		System.out.println("getIntIdEstSolicitud() ANTES   "+getIntIdEstSolicitud());
		this.setIntIdEstSolicitud(1);
		System.out.println("getIntIdEstSolicitud() DESPUES "+getIntIdEstSolicitud());
		System.out.println("");
		limpiarMantCuentas();
	}
	
	
	/**************************************************************/
	/*  Nombre :  limpiarAportaciones()     			      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarMantCuentas(){
		Aportes aport = new Aportes();
		setBeanMantCuenta(aport);
		setMsgTxtDescripcion("");
		setMsgTxtFechaIni("");
		setMsgTxtEstadoMantCuenta("");
		setMsgTxtTipoPersona("");
		//setMsgTxtCondSocio("");
		setMsgTxtCondicionLaboral("");
		setMsgTxtTipoDscto("");
		setMsgTxtTipoConfig("");
		setMsgTxtMoneda("");
		setMsgTxtAplicacion("");
		if(beanListCondSocio!=null){
			beanListCondSocio.clear();
		}
		setRbFecIndeterm("");
		setDaFechaIni(null);
		setDaFechaFin(null);
		setRbCondSocio("");
		setStrValManCuenta("");
		//setRbTasaInteres("");
		setFecFinMantCuentaRendered(true);
		//setChkTasaInteres(false);
		//setEnabDisabTasaInt(true);
		//setEnabDisabPorcInt(true);
		//setEnabDisabTea(true);
		//setEnabDisabTna(true);
		setChkLimiteEdad(false);
		//setEnabDisabLimiteEdad(true);
	}
	

	/**************************************************************/
	/*                                                    	 		*/
	/* Nombre : cancelarGrabarMantCuentas() */
	/*                                                    	 		*/
	/* Parametros. : event descripcion */
	/*                         						     	 		*/
	/*                                                    	 		*/
	/* Objetivo: Cancelar la nueva Hoja de Planeamiento */
	/* Poblacional. */
	/* Retorno : Se oculta el Formulario de MantCuentas */
	/**************************************************************/
	public void cancelarGrabarMantCuentas(ActionEvent event) {
		log.info("---------------------Debugging AdmFormDocController.cancelarGrabarHojaPlaneamiento-----------------------------");
		setFormMantCuentasRendered(false);
		limpiarMantCuentas();
	}

	/**************************************************************/
	/*                                                    	 		*/
	/* Nombre : listar Mantenimiento de Cuentas() */
	/*                                                    	 		*/
	/* Parametros. : event descripcion */
	/*                         						     	 		*/
	/* Objetivo: Listar para la grilla del JSF y avanzar */
	/* Retorno : Devuelve el listado de las Cuentas par hacer el mantenimiento en la pantalla */
	/**************************************************************/
	public void listarMantCuentas(ActionEvent event){
		log.info("---------------------Debugging MantCuentasController.listarMantCuentas---------------------------");
		setCreditosService(mantCuentasService);
		log.info("Se ha seteado el Service");
	    //Hace q se habilite la primera búsqueda 
		this.chkTodosMantCuenta=true;
		this.setChkTodosMantCuenta(chkTodosMantCuenta);
	    setEnabDisabTodosMantCuenta(getChkTodosMantCuenta());		
	    
	    
	    
		Aportes aporte = new Aportes();	
	    aporte.setIntIdTipoCaptacion(Constante.CAPTACION_MANT_CUENTA);
		
	    
	    
	    //aporte.setIntIdEstSolicitud(getIntIdEstadoMantCuenta());//era antes de tener intIdEstSolicitud
	    

		
		
		
		
		
		
		aporte.setStrDescripcion(getStrNombreMantCuenta());
//		aporte.setIntIdTipoCondicionLaboral(getIntIdTipoCondLab());
		
		
		String strFecIni = (getDaFecIni() != null) ? Constante.sdf.format(getDaFecIni()) : null;
		String strFecFin = (getDaFecFin() != null) ? Constante.sdf.format(getDaFecFin()) : null;
		log.info("strFechaIni: " + strFecIni);
		log.info("strFechaFin: " + strFecFin);
		
		log.info("getIntIdTipoPersona(): " + getIntIdTipoPersona());
		aporte.setDaFecIni(strFecIni);
		aporte.setDaFecFin(strFecFin);
		
		aporte.setIntIdEstSolicitud(getIntIdEstSolicitud()==0? null:getIntIdEstSolicitud());
		
		//Estas lineas hacen q no s emuestre la lista
		//aporte.setIntIdCondicionSocio(aporte.getIntIdCondicionSocio()!=null ? aporte.getIntIdCondicionSocio():null);		
		

		
		System.out.println("LISTAR intIdCondicionMantCuenta = "+intIdCondicionMantCuenta);
		System.out.println("LISTAR getIntIdCondicionMantCuenta() = "+getIntIdCondicionMantCuenta()+" sigue cero?" );//es cero 0
		System.out.println("aporte.getIntIdCondicionSocio() = "+aporte.getIntIdCondicionSocio());		
	    //aporte.setIntIdCondicionSocio(getIntIdCondicionMantCuenta()!=null ? getIntIdCondicionMantCuenta():null);
	
		
	    //aporte.setIntIdCondicionSocio(getIntIdCondicionMantCuenta()!=null ? getIntIdCondicionMantCuenta():0);
	    
	    setIntIdCondicionMantCuenta(getIntIdCondicionMantCuenta()!=null ? getIntIdCondicionMantCuenta():0);
	    
	    //aporte.setIntIdCondicionSocio(getIntIdCondicionMantCuenta()!=null ? getIntIdCondicionMantCuenta():null);
		aporte.setIntIdCondicionSocio(getIntIdCondicionMantCuenta()==0 ? null:getIntIdCondicionMantCuenta());
		
		
		aporte.setIntIdTipoConfig(getIntIdTipoConfig()==0 ? null:getIntIdTipoConfig());		
		aporte.setIntIdTipoPersona(getIntIdTipoPersona()==0 ? null:getIntIdTipoPersona());
			System.out.println("getIntIdTipoCondLab(): " + getIntIdTipoCondLab());
			System.out.println("aporte.getIntIdTipoCondicionLaboral(): " + aporte.getIntIdTipoCondicionLaboral());
	    		


    
		
		aporte.setIntIdTipoCondicionLaboral(getIntIdTipoCondLab()==0 ? null:getIntIdTipoCondLab());

		
		System.out.println("aporte.getIntIdTipoCondicionLaboral(): " + aporte.getIntIdTipoCondicionLaboral());
		//
		
	    aporte.setIntChkAportVigentes(getChkMantCuentaVigentes()!=null && getChkMantCuentaVigentes()==true?1:0);
	    aporte.setIntChkAportCaduco(getChkMantCuentaCaduco()!=null && getChkMantCuentaCaduco()==true?1:0);		
	    
		/*
	    //aporte.setIntChkTasaInteres(getChkTasaInt()!=null && getChkTasaInt()==true?1:0);
	    //aporte.setIntChkEdadLimite(getChkLimEdad()!=null && getChkLimEdad()==true?1:0);
	    */
	    //setIntIdCondicionMantCuenta(getChkTipoCaptacionAfectaMantCuenta()!=true?0:getIntIdCondicionMantCuenta());	    
	    
	    

		  System.out.println("/ / / / / / / / / / / / / / / / / / / // / / / / / /");
		    System.out.println("/ / / / / / / / / / / / / / / / / / / // / / / / / /");
		    System.out.println("/ / / / / / / / / / / / / / / / / / / // / / / / / /");	    	    
		System.out.println("getChkTipoCaptacionAfectaMantCuenta(): " + getChkTipoCaptacionAfectaMantCuenta());
		System.out.println("aporte.getIntIdTipoCaptacionAfecta(): " + aporte.getIntIdTipoCaptacionAfecta());	    
	    System.out.println("/ / / / / / / / / / / / / / / / / / / // / / / / / /");
	    System.out.println("/ / / / / / / / / / / / / / / / / / / // / / / / / /");
	    System.out.println("/ / / / / / / / / / / / / / / / / / / // / / / / / /");		
	    
	    
	   // aporte.setIntIdTipoCaptacionAfecta(getChkTipoCaptacionAfectaMantCuenta()!=true?1:0);
	    
		
		List<Aportes> listaMantCuentas = new ArrayList<Aportes>();
		try {
			listaMantCuentas = getCreditosService().listarMantCuentas(aporte);
			
			System.out.println("=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,========================================");
			for(Aportes mantCuenta:listaMantCuentas){
			    System.out.println("IdEmpresa :\t"+mantCuenta.getIntIdEmpresa());			    
			    /*System.out.println("IdPerfil :\t"+formdoc.getIntIdPerfil());
			    System.out.println("IdTransaccion :\t"+formdoc.getStrIdTransaccion());	    
			    System.out.println("IdTipoDoc :\t"+formdoc.getIntIdTipoDoc());
			    System.out.println("IdTipoDemo :\t"+formdoc.getIntIdTipoDemo());
			    System.out.println("IntIdTipoDemo :\t"+formdoc.getStrArchDoc());
			    System.out.println("StrArchDemo :\t"+formdoc.getStrArchDemo());
			    System.out.println("IntPerfil :\t"+formdoc.getIntPerfil());*/
			    System.out.println("IntIdEstado :\t"+mantCuenta.getIntIdEstado());
			    /*System.out.println("IntIdEstSolicitud :\t"+mantCuenta.getIntIdEstSolicitud());
			    System.out.println("FlValor :\t"+mantCuenta.getFlValor());
			    System.out.println("FlValorConfig :\t"+mantCuenta.getFlValorConfig());*/
			    System.out.println("IntIdTipoConfig :\t"+mantCuenta.getIntIdTipoConfig());
			    System.out.println("getIntIdTipoCaptacionAfecta :\t"+mantCuenta.getIntIdTipoCaptacionAfecta());//null
			    System.out.println("=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,=,========================================");				
			}
		} catch (DaoException e) {
			log.info("ERROR getCreditosService().listarMantCuentas() "+ e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		setBeanListMantCuentas(listaMantCuentas);
	}

	/**************************************************************/
	/*                                                    	 		*/
	/* Nombre : listarControlProceso() */
	/*                                                    	 		*/
	/* Parametros. : event descripcion */
	/*                         						     	 		*/
	/* Objetivo: Listar las Hojas de Planeamiento */
	/* Retorno : Devuelve el listado de las Hojas de Planeamiento */
	/**************************************************************/
	public void enableDisableControls(ActionEvent event) {
		log.info("--------------Debugging MantCuentasController.enableDisableControls------------------");
		//setEnabDisabNombMantCuenta(getChkNombMantCuenta() != true);
		//
		
		
		setEnabDisabFechasMantCuenta(getChkFechas() != true);
		setDaFecIni(getChkFechas() == true ? null : getDaFecIni());
		setDaFecFin(getChkFechas() == true ? null : getDaFecFin());
		setEnabDisabNombMantCuenta(getChkNombMantCuenta()!=true);
		setStrNombreMantCuenta(getChkNombMantCuenta()!=true?"":getStrNombreMantCuenta());
		setEnabDisabTodosMantCuenta(getChkTodosMantCuenta()!=true);	
				

		
		System.out.println("ENABLE DISABLE  1 intIdCondicionMantCuenta = "+intIdCondicionMantCuenta);
		System.out.println("ENABLE DISABLE  1 getIntIdCondicionMantCuenta() = "+getIntIdCondicionMantCuenta() );				
		setEnabDisabCondMantCuenta(getChkCondMantCuenta()!=true);
		setIntIdCondicionMantCuenta(getChkCondMantCuenta()!=true?0:getIntIdCondicionMantCuenta());
		System.out.println("ENABLE DISABLE  2 intIdCondicionMantCuenta = "+intIdCondicionMantCuenta);
		System.out.println("ENABLE DISABLE  2 getIntIdCondicionMantCuenta() = "+getIntIdCondicionMantCuenta() );				
		
		//setEnabDisabTasaInt(getChkTasaInteres()!=true);
		////setEnabDisabPorcInt(getChkTasaInteres()==true);
		//setEnabDisabTea(getChkTasaInteres()==true);
		//setEnabDisabTna(getChkTasaInteres()!=true);
		//setEnabDisabLimiteEdad(getChkLimiteEdad()!=true);
		
		////setEnabDisabProvision(getChkProvision()!=true);
		////setEnabDisabExtProvision(getChkExtProvision()!=true);
		////setEnabDisabCancelacion(getChkCancelacion()!=true);
		/*
		if(getRbTasaInteres()!=null){
			//setEnabDisabPorcInt(!getRbTasaInteres().equals("1"));
			setEnabDisabTea(!getRbTasaInteres().equals("2"));
		}*/
		
		if(getRbFecIndeterm()!=null){
			setFecFinMantCuentaRendered(getRbFecIndeterm().equals("1"));
			setDaFechaFin(null);
		}
		/*
		if(getChkTasaInteres()==false){
			setRbTasaInteres(null);
			setEnabDisabTasaInt(true);
			//setEnabDisabPorcInt(true);
			setEnabDisabTea(true);
			setEnabDisabTna(true);
		}
		*/
		Aportes aport = new Aportes();
		aport = (Aportes) getBeanMantCuenta();
		/*
		if(getRbTasaInteres()!=null && getRbTasaInteres().equals("1")){
			aport.setFlTem(new Float(0.0));
			aport.setIntIdTasaNaturaleza(null);
			aport.setIntIdTasaFormula(null);
			aport.setFlTea(new Float(0.0));
		}else{*/
			aport.setFlTem(new Float(0.0));
			aport.setIntIdTasaNaturaleza(null);
			aport.setIntIdTasaFormula(null);
			aport.setFlTea(new Float(0.0));
		//}
	
		if(getChkLimiteEdad()!=null && getChkLimiteEdad()==false){
			aport.setIntEdadLimite(null);
		}
		setBeanMantCuenta(aport);		
	}
	/**/

	

	/**************************************************************/
	/*                                                    	 		*/
	/* Nombre : listarControlProceso() */
	/*                                                    	 		*/
	/* Parametros. : event descripcion */
	/*                         						     	 		*/
	/* Objetivo: Listar las Hojas de Planeamiento */
	/* Retorno : Devuelve el listado de las Hojas de Planeamiento */
	/**************************************************************/
	/*public void showFecFin(ValueChangeEvent event) throws DaoException {
		log.info("----------------Debugging MantCuentasController.showFecFin-------------------");
		String idFecHorario = (String) event.getNewValue();
		setFecFinMantCuentaRendered(idFecHorario.equals("1"));
		setDaFechaFin(idFecHorario.equals("1") ? null : getDaFechaFin());
		setDaFechaFin(idFecHorario.equals("1") ? null : getDaFecFin());
	}*/

	/**************************************************************/
	/*                                                    	 		*/
	/* Nombre : listarCondSocio() */
	/*                                                    	 		*/
	/* Parametros. : event descripcion */
	/*                         						     	 		*/
	/* Objetivo: Listar las Hojas de Planeamiento */
	/* Retorno : Devuelve el listado de las Hojas de Planeamiento */
	/**************************************************************
	public void listarCondSocio(ActionEvent event) {
		log
				.info("----------------Debugging MantCuentasController.listarCondSocio------------------");
		setCreditosService(mantCuentasService);
		log.info("Se ha seteado el Service");

		if (getRbCondSocio().equals("2")) {
			ArrayList listaCondSocio = new ArrayList();
			try {
				listaCondSocio = parametersController.obtenerListaParametros(65);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < listaCondSocio.size(); i++) {
				CondSocio cond = (CondSocio) listaCondSocio.get(i);
				listaCondSocio.add(cond);
			}

			setBeanListCondSocio(listaCondSocio);
		} else
			setBeanListCondSocio(null);

	}
	*/
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarCondSocio()        							*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**
	 * @throws DaoException ************************************************************/	
	public void listarCondicionSocio(ActionEvent event) throws DaoException{
		System.out.println("R E S O L  C E R");
		log.info("----------------Debugging AportacionesController.listarCondSocio------------------");
		setCreditosService(mantCuentasService);
		log.info("Se ha seteado el Service");
		//String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMantCuentaModalPanel:hiddenIdCodigo");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMantCuentaModalPanel:hiddenIdCodigo");
		
	    System.out.println("/ / /getRbCondSocio() =====  / /");
	    System.out.println("/ / / / /getRbCondSocio() = "+getRbCondSocio()+" / / / // / / / / / /");
		
				
		if(getRbCondSocio().equals("2")){
			List<CondSocio> listaCondSocio = new ArrayList<CondSocio>();
			List<CondSocio> arrayCondSocio = new ArrayList<CondSocio>();
			
			Map<String, Integer> prmtParametros = new HashMap<String, Integer>();	    
			prmtParametros.put("pIntIdEmpresa", 	  beanSesion.getIntIdEmpresa());
			System.out.println("pIntIdEmpresa		="+beanSesion.getIntIdEmpresa());
			prmtParametros.put("pIntIdTipoCaptacion", Constante.CAPTACION_MANT_CUENTA);
			System.out.println("pIntIdTipoCaptacion ="+ Constante.CAPTACION_MANT_CUENTA);
			prmtParametros.put("pIntIdCodigo", 		  (strIdCodigo!=null?Integer.parseInt(strIdCodigo):null));
			System.out.println("pIntIdCodigo		="+ 		  (strIdCodigo!=null?Integer.parseInt(strIdCodigo):null));
			//Obteniendo lista
			log.info("Obteniendo array de Parametros.");			
			arrayCondSocio = getCreditosService().listarCondicionSocio(prmtParametros);
			System.out.println("arrayCondSocio = "+arrayCondSocio);
			log.info("arrayCondSocio.size(): "+arrayCondSocio.size());			
	        for(int i=0; i<arrayCondSocio.size() ; i++){
	        	CondSocio condsoc = (CondSocio) arrayCondSocio.get(i);
	        	System.out.println("CondSocio condsoc = "+condsoc);
	        	//log.info("condsoc.getIntIdValor(): "	+condsoc.getIntIdValor());
	        	//log.info("condsoc.getStrDescripcion(): "+condsoc.getStrDescripcion());
	        	System.out.println("condsoc.getIntIdValor(): "	+condsoc.getIntIdValor());
	        	System.out.println("condsoc.getStrDescripcion(): "+condsoc.getStrDescripcion());
	        	
	        	condsoc.setChkSocio(condsoc.getIntIdValor()==1);
	        	listaCondSocio.add(condsoc);
	        }
			setBeanListCondSocio(listaCondSocio);
			System.out.println("getBeanListCondSocio = "+this.getBeanListCondSocio());
		}else {
			if(beanListCondSocio != null){
				beanListCondSocio.clear();
			}
		}
	}	
	
	
	public void grabarMantCuentas(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging MantCuentasController.grabarMantCuentas-----------------------------");
		setCreditosService(mantCuentasService);
	    setService(admFormDocService);
	    log.info("Se ha seteado el Service");
	    String strFileNameDoc = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:hiddenStrFileDocName");
	    String strFileNameDemo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmPrincipal:hiddenStrFileDemoName");		
		log.info("strFileNameDoc:  				"+strFileNameDoc);
		log.info("strFileNameDemo: 				"+strFileNameDemo);
		Integer idTipoDoc = 0;
		Integer idTipoDemo = 0;
		//strFileNameDoc="";
		//strFileNameDemo="";
	    strFileNameDoc="1";
	    strFileNameDemo="2";		
		log.info("strFileNameDoc:  				"+strFileNameDoc);
		log.info("strFileNameDemo: 				"+strFileNameDemo);
		idTipoDoc  = 1; //plrt
		idTipoDemo = 2; //plrt
		
		if(!strFileNameDoc.equals("")){
			System.out.println("PASO1");
			idTipoDoc  = 1; 
		}
		if(!strFileNameDemo.equals("")){
			System.out.println("PASO2");
			idTipoDemo = 2;
		}
		
		AdmFormDoc formdoc = new AdmFormDoc();
		System.out.println("AdmFormDoc formdoc = "+formdoc);
	    formdoc = (AdmFormDoc) getBeanFormDoc();		
	    
	    
	    //Aportes aportes = new Aportes();	    
	    Aportes aporte = (Aportes) this.getBeanMantCuenta();
	    aporte.setIntIdEmpresa(163);
	    formdoc.setIntIdEmpresa(163);
	    formdoc.setIntIdPerfil(-1);//-1 ó 0 4
	    formdoc.setStrIdTransaccion("001002014021");//001002014000 002001004003 001002015000
	    formdoc.setIntIdTipoDoc(1);//1 4 3
	    formdoc.setIntIdTipoDemo(1);
	    formdoc.setStrArchDoc("");
	    formdoc.setStrArchDemo("");
	    formdoc.setIntPerfil(1);//1 ó 0
	    //aportes.setIntIdEstado(1);
	    //formdoc.setIntIdEstado(1);
	    
	    
	    Boolean bValidar = true;
		if (aporte.getStrDescripcion().equals("")) {
			setMsgTxtDescripcion("* El campo nombre del mantenimiento debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtDescripcion("");
		}	    
		if (aporte.getIntIdEstado()== 0) {
			setMsgTxtEstadoMantCuenta("* El campo estado del mantenimiento debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtEstadoMantCuenta("");
		}
		System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
		
        aporte.setStrFecIni((getDaFechaIni() != null) ? Constante.sdf.format(getDaFechaIni()) : null);
		System.out.println("validacion getStrFecIni :\t"+aporte.getStrFecIni());
		aporte.setStrFecFin((getDaFechaFin() != null) ? Constante.sdf.format(getDaFechaFin()) : null);
		System.out.println("getStrFecFin :\t"+aporte.getStrFecFin());				    
		
		
		if (aporte.getStrFecIni()==null) {
			setMsgTxtFechaIni("* El campo Fecha de Inicio debe ser ingresado.");
			bValidar = false;
		}else if(daFechaIni.equals(daFechaFin)){
			setMsgTxtFechaIni("* Las Fechas son iguales.");
			bValidar = false;
		}else{
			setMsgTxtFechaIni("");
		}
		if(aporte.getStrFecFin()!= null &&	daFechaIni.after(daFechaFin)){
			setMsgTxtFechaIni("* La Fecha de Fin es menor a la Fecha de Inicio.");
			bValidar = false;
		}else{
			setMsgTxtFechaIni("");
		}
		if (aporte.getIntIdTipoPersona()== 0) {
			setMsgTxtTipoPersona("* El campo Tipo de Persona debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtTipoPersona("");
		}
		if (aporte.getIntIdTipoCondicionLaboral()== 0) {
			setMsgTxtCondicionLaboral("* El campo Condición Laboral debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtCondicionLaboral("");
		}		
		if (aporte.getIntIdTipoDcto()== 0) {
			setMsgTxtTipoDscto("* El campo Tipo de Descuento debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtTipoDscto("");
		}
		if (aporte.getIntIdMoneda()== 0) {
			setMsgTxtMoneda("* El Tipo de Moneda debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtMoneda("");
		}		
		if (aporte.getIntIdTipoConfig()== 0) {
			setMsgTxtTipoConfig("* El campo Tipo de Configuración debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtTipoConfig("");
		}		
	    int intIdEmpresa = aporte.getIntIdEmpresa();
	    if(intIdEmpresa==0){
	    	System.out.println("* Debe seleccionar una Empresa.");
	    	bValidar = false;
	    }else{
	        System.out.println("intIdEmpresa = "+intIdEmpresa);
	    }

		/*
		System.out.println("/ / /getRbCondSocio() =====  / /");
	    System.out.println("/ / /getRbCondSocio() =====  / /");
	    System.out.println("/ / / / /getRbCondSocio() = "+getRbCondSocio()+" / / / // / / / / / /");
	    
	    
		aporte.setIntIdEstSolicitud(aporte.getIntIdEstSolicitud()!=null ? aporte.getIntIdEstSolicitud():1);
		setIntIdEstSolicitud(aporte.getIntIdEstSolicitud());
		System.out.println("getIntIdEstSolicitud(): " + getIntIdEstSolicitud());
		//aporte.setIntIdEstSolicitud(getIntIdEstSolicitud());
		System.out.println("MODIF ESTADO DE LA SOLICITUD :\t"+aporte.getIntIdEstSolicitud());
		System.out.println("getIntIdEstSolicitud() METODO MODIFICAR "+getIntIdEstSolicitud());
		int estSolicitud=aporte.getIntIdEstSolicitud();	    
	    
	    */
	    
	    
	    int intIdPerfil = formdoc.getIntIdPerfil();
	    //plrt
	    strFileNameDoc="1";
	    strFileNameDemo="2";
	    /*if(strFileNameDoc.equals("") && strFileNameDemo.equals("")){
	    	//setMsgTxtDocum("Debe elegir por lo menos un Tipo de Documento.");
	    	bValidar = false;
	    }else{
	    	//setMsgTxtDocum("");
	    }*/
	    aporte.setIntIdTipoCaptacion(Constante.CAPTACION_MANT_CUENTA);
 

	    
		

	    
	    
	    if(bValidar==true){
		  try {
			  if(!strFileNameDoc.equals("") && !strFileNameDemo.equals("")){
				    System.out.println("IdEmpresa :\t"+aporte.getIntIdEmpresa());
				    System.out.println("IdTipoCaptacion :\t"+aporte.getIntIdTipoCaptacion());
				    
				    
	//			    formdoc.setIntIdCodigo(tamanLista+2);//ESTO DEBE mejorar !!!!
				    //formdoc.setIntIdCodigo(12);//ESTO DEBE mejorar !!!!
				    //Se ha tenido q actualizar prar q se puedan visualizar todos los registros normalmente,
				    //se cambió el 2 por el 1 al campo COCA_IDTIPOCONFIGURACION_N .
				    aporte.setIntIdCondSocio(1);
				    aporte.setIntIdValor(1);
				    				    
				    System.out.println("IdCondicionSocio :\t"+aporte.getIntIdCondSocio());
				    System.out.println("IdValor :\t"+aporte.getIntIdValor());
				    

				    System.out.println("IdCodigo :\t"+aporte.getIntIdCodigo());
				    
				    aporte.setStrDescripcion(beanMantCuenta.getStrDescripcion());
				    System.out.println("getStrDescripcion :\t"+aporte.getStrDescripcion());
			        aporte.setStrFecIni((getDaFechaIni() != null) ? Constante.sdf.format(getDaFechaIni()) : null);			        
					System.out.println("getStrFecIni :\t"+aporte.getStrFecIni());
					aporte.setStrFecFin((getDaFechaFin() != null) ? Constante.sdf.format(getDaFechaFin()) : null);
					System.out.println("getStrFecFin :\t"+aporte.getStrFecFin());				    
				    
					
					aporte.setIntIdTipoPersona(beanMantCuenta.getIntIdTipoPersona());					
			    	System.out.println("getIntIdTipoPersona :\t"+aporte.getIntIdTipoPersona());
					//mantCuenta.setIntIdRol();
			    	System.out.println("getIntIdRol :\t"+aporte.getIntIdRol());
			    	aporte.setIntIdTipoCondicionLaboral(beanMantCuenta.getIntIdTipoCondicionLaboral());
					System.out.println("getIntIdTipoCondicionLaboral :\t"+aporte.getIntIdTipoCondicionLaboral());
			    	
			    	
			    	aporte.setIntIdTipoDcto(beanMantCuenta.getIntIdTipoDcto());
					System.out.println("getIntIdTipoDscto :\t"+aporte.getIntIdTipoDcto());
					aporte.setIntIdTipoConfig(beanMantCuenta.getIntIdTipoConfig());		 
					System.out.println("getIntIdTipoConfig :\t"+aporte.getIntIdTipoConfig());
					aporte.setIntFlValorConfig(12);		 
					System.out.println("getIntFlValorConfig :\t"+aporte.getIntFlValorConfig());
					aporte.setIntIdAplicacion(1);		 
					System.out.println("IntIdAplicacion :\t"+aporte.getIntIdAplicacion());
					aporte.setIntIdMoneda(beanMantCuenta.getIntIdMoneda());		 
					System.out.println("getIntIdMoneda :\t"+aporte.getIntIdMoneda());					
					//mantCuenta.setIntFltem(0);
					System.out.println("getIntFltem :\t"+aporte.getIntFltem());
					//mantCuenta.setIntIdTasaNaturaleza());		 
					System.out.println("getIntIdTasaNaturaleza :\t"+aporte.getIntIdTasaNaturaleza());
					//mantCuenta.setIntIdTasaFormula());		 
					System.out.println("getIntIdTasaFormula :\t"+aporte.getIntIdTasaFormula());
					aporte.setIntFltea(0);		
					System.out.println("getIntFltea :\t"+aporte.getIntFltea());
					//mantCuenta.setIntFltna());
					System.out.println("getIntFltna :\t"+aporte.getIntFltna());
					//mantCuenta.setIntEdadLimite());
					System.out.println("getIntEdadLimite :\t"+aporte.getIntEdadLimite());
					
					System.out.println("getIntIdEstSolicitud() DESPUES "+getIntIdEstSolicitud());
					
					
					aporte.setIntIdEstSolicitud(aporte.getIntIdEstSolicitud()!=null ? getIntIdEstSolicitud():1);
					//aporte.setIntIdEstSolicitud(getIntIdEstSolicitud());
					System.out.println("getIntIdEstadoSolic :\t"+aporte.getIntIdEstSolicitud());
					System.out.println("ESTADO DE LA SOLICITUD :\t"+aporte.getIntIdEstSolicitud());
					
					int estSolicitud=aporte.getIntIdEstSolicitud();
					switch(estSolicitud){
						case 1:setMsgTxtEstadoSolicitud("PENDIENTE");
						case 2:setMsgTxtEstadoSolicitud("COMPLETO");
						case 3:setMsgTxtEstadoSolicitud("ANULADA");
						default:setMsgTxtEstadoSolicitud("PENDIENTE");
					}
					
					
					
					
					
					aporte.setIntIdEstado(beanMantCuenta.getIntIdEstado());
					System.out.println("getIntIdEstado :\t"+aporte.getIntIdEstado());
					//System.out.println("getIntIdEstado :\t"+mantCuenta.getIntIdEstado().getStrAbreviatura());
					
					//aportes.setIntTipoCondicionLaboral(beanMantCuenta.getIntTipoCondicionLaboral());
					
				    
				    //plrt grabar la tabla CRE_M_CONFCAPTACION
					getMantCuentasService().grabarMantCuentas(aporte);
					
					
					
					/*
					
					//Eliminando el Detalle de la Condición del Socio
					try{
						getCreditosService().eliminarCondSocio(aporte);
					}catch(DaoException e){
						log.info("ERROR  getCreditosService().eliminarCondSocio() " + e.getMessage());
						e.printStackTrace();
					}
					
					System.out.println("/ / /getRbCondSocio() =====  / /");
				    System.out.println("/ / /getRbCondSocio() =====  / /");
				    System.out.println("/ / / / /getRbCondSocio() = "+getRbCondSocio()+" / / / // / / / / / /");
										
					if(getRbCondSocio().equals("1")){
						List lstCondSoc = new ArrayList();
						
						Map<String, Integer> prmtCondSoc = new HashMap<String, Integer>();
						prmtCondSoc.put("pIntIdEmpresa", 		beanSesion.getIntIdEmpresa());
						prmtCondSoc.put("pIntIdTipoCaptacion", 	Constante.CAPTACION_MANT_CUENTA);
						prmtCondSoc.put("pIntIdCodigo", 		null);
						lstCondSoc = getCreditosService().listarCondSocio(prmtCondSoc);
						
						log.info("lstCondSoc.size(): "+lstCondSoc.size());
						for(int i=0;i<lstCondSoc.size(); i++){
							CondSocio cond = (CondSocio) lstCondSoc.get(i);
							cond.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
							cond.setIntIdTipoCaptacion(Constante.CAPTACION_MANT_CUENTA);
							cond.setIntIdCodigo(aporte.getIntIdCodigo());
							cond.setIntIdValor(1);
							
							//getCreditosService().grabarCondicionSocio(cond);
							getMantCuentasService().grabarCondSocioMantCuentas(aporte);
							
						}
					}
					
					
					
					//LEGAL : 
					 * getMantCuentasService().grabarCondSocioMantCuentas(aporte);
					 */
					getMantCuentasService().grabarCondSocioMantCuentas(aporte);
					
					
					/*
					for(int i=0; i<getBeanListCondSocio().size(); i++){
						CondSocio cond = (CondSocio) getBeanListCondSocio().get(i);
						cond.setIntIdEmpresa(aporte.getIntIdEmpresa());
						cond.setIntIdTipoCaptacion(Constante.CAPTACION_MANT_CUENTA);
						cond.setIntIdCodigo(aporte.getIntIdCodigo());
						cond.setIntIdValor(cond.getChkSocio()==true?1:0);
						
						try {
							//getCreditosService().grabarCondicionSocio(cond);
							getMantCuentasService().grabarCondSocioMantCuentas(aporte);														
						} catch (DaoException e) {
							log.info("ERROR  getService().grabarCondicionSocio(dom:) " + e.getMessage());
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}*/
					
				
					aporte.setIntIdTipoCaptacionAfecta(1);
					getMantCuentasService().grabarCondCapAfectasMantCuentas(aporte);

				  
				  //getService().grabarFormDoc(formdoc);
				  //getService().grabarFormDemo(formdoc);
				  
			  }else if(!strFileNameDoc.equals("") && strFileNameDemo.equals("")){
				  System.out.println("____________________________________________________3");
				  getService().grabarFormDoc(formdoc);
	  	      }else{ //if(strFileNameDoc.equals("") && !strFileNameDemo.equals("")){
	  	    	System.out.println("____________________________________________________4");
	  	    	  getService().grabarFormDemo(formdoc);
	  	      }
		  } catch (DaoException e) {
			  log.info("ERROR  getService().grabarFormDoc(formdoc:) " + e.getMessage());
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  	listarMantCuentas(event);
			setMessageSuccess("Los datos se actualizaron satisfactoriamente ");
	    }
	}
	
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  modificarAportaciones()      						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Guardar los datos ingresados en la ventan de 		*/
	/*            Aportaciones 						     	 		*/
	/*                         						     	 		*/
	/*  Retorno : Datos grabados correctamente en la tabla de 	 	*/
	/*            CRE_M_CONFCAPTACION				     	 		*/
	/**
	 * @throws DaoException 
	 * @throws ParseException ************************************************************/
	public void modificarMantCuenta(ActionEvent event) throws DaoException, ParseException{	
		log.info("-----------------MantCuentasController.modificarMantCuenta----------------------");
		setCreditosService(mantCuentasService);
		log.info("Se ha seteado el Service");	
		
		String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMantCuentaModalPanel:hiddenIdEmpresa");
		String strIdTipoCaptacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMantCuentaModalPanel:hiddenIdTipoCaptacion");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMantCuentaModalPanel:hiddenIdCodigo");
		log.info("strIdEmpresa 		 : "+strIdEmpresa);
		log.info("strIdTipoCaptacion : "+strIdTipoCaptacion);
		log.info("strIdCodigo 		 : "+strIdCodigo);
		
		System.out.println("strIdEmpresa 		 : "+strIdEmpresa);
		System.out.println("strIdTipoCaptacion : "+strIdTipoCaptacion);
		System.out.println("strIdCodigo 		 : "+strIdCodigo);
		
		//System.out.println("aporte.getStrFecIni()\t\t: "+aport.getStrFecIni());
		//prmtAportacion.put("pStrFecIni", 		 (strIdCodigo!=null)?Integer.parseInt(strIdCodigo):null);
		//prmtAportacion.put("pIntIdTipoCondicionLaboral", 		 (strIdCodigo!=null)?Integer.parseInt(strIdCodigo):null);
		
		
		Aportes aport = new Aportes();
		aport.setIntIdEmpresa((strIdEmpresa!=null)?Integer.parseInt(strIdEmpresa):null);
		aport.setIntIdTipoCaptacion((strIdTipoCaptacion!=null)?Integer.parseInt(strIdTipoCaptacion):null);
		aport.setIntIdCodigo((strIdCodigo!=null)?Integer.parseInt(strIdCodigo):null);		
		
		
		String daFecIni0 = "" + aport.getDaFecIni() == null ? "" : aport.getDaFecIni();
		Date fecIni0 = (daFecIni == null || daFecIni.equals("") ? null : Constante.sdf.parse(daFecIni0));
		setDaFechaIni(fecIni0);		
		String daFecFin0 = "" + aport.getDaFecFin() == null ? "" : aport.getDaFecFin();
		Date fecFin0 = (daFecFin == null || daFecFin.equals("") ? null : Constante.sdf.parse(daFecFin0));
		setDaFechaFin(fecFin0);		
        aport.setStrFecIni(daFecIni0);
		System.out.println("aport getStrFecIni :\t"+aport.getStrFecIni());
		aport.setStrFecFin(daFecFin0);
		System.out.println("aport getStrFecFin :\t"+aport.getStrFecFin());		
		
		List<Aportes> listaMantCuentas = new ArrayList<Aportes>();
		listaMantCuentas = getCreditosService().listarMantCuentas(aport);
		
		
		
		Aportes aporte = (Aportes)listaMantCuentas.get(0);	
		
		String daFecIni = "" + aporte.getDaFecIni() == null ? "" : aporte.getDaFecIni();
		Date fecIni = (daFecIni == null || daFecIni.equals("") ? null : Constante.sdf.parse(daFecIni));
		setDaFechaIni(fecIni);		
		String daFecFin = "" + aporte.getDaFecFin() == null ? "" : aporte.getDaFecFin();
		Date fecFin = (daFecFin == null || daFecFin.equals("") ? null : Constante.sdf.parse(daFecFin));
		setDaFechaFin(fecFin);

		
        aporte.setStrFecIni(daFecIni);
		System.out.println("getStrFecIni :\t"+aporte.getStrFecIni());
		aporte.setStrFecFin(daFecFin);
		System.out.println("getStrFecFin :\t"+aporte.getStrFecFin());
		
		aporte.setIntIdEstSolicitud(aporte.getIntIdEstSolicitud()!=null ? aporte.getIntIdEstSolicitud():1);
		setIntIdEstSolicitud(aporte.getIntIdEstSolicitud());
		System.out.println("getIntIdEstSolicitud(): " + getIntIdEstSolicitud());
		//aporte.setIntIdEstSolicitud(getIntIdEstSolicitud());
		System.out.println("MODIF ESTADO DE LA SOLICITUD :\t"+aporte.getIntIdEstSolicitud());
		System.out.println("getIntIdEstSolicitud() METODO MODIFICAR "+getIntIdEstSolicitud());
		int estSolicitud=aporte.getIntIdEstSolicitud();
		switch(estSolicitud){
			case 1:setMsgTxtEstadoSolicitud("PENDIENTE");
			case 2:setMsgTxtEstadoSolicitud("COMPLETO");
			case 3:setMsgTxtEstadoSolicitud("ANULADA");
			default:setMsgTxtEstadoSolicitud("PENDIENTE");
		}		
		
		
		
		////setFecFinMantCuentaRendered(fecFin!=null);
		////setRbFecIndeterm(fecFin!=null?"1":"2");
		
		//setChkTasaInteres(aporte.getFlTna()!=null);
		//setEnabDisabTasaInt(aporte.getFlTna()==null || aporte.getFlTna()==0);
		//setRbTasaInteres(aporte.getFlTem()!=null?"1":"2");
		//setEnabDisabPorcInt(lstAport.getFlTem()==0);
		//setEnabDisabTea(aporte.getFlTea()==0);
		//setEnabDisabTna(aporte.getFlTna()==null || aporte.getFlTna()==0);
		
		//setEnabDisabLimiteEdad(aporte.getIntEdadLimite()==null);
		setChkLimiteEdad(aporte.getIntEdadLimite()!=null);
		setFormMantCuentasRendered(true);	
		
		//setRbCondSocio(aporte.getIntCntCondSoc()==4?"1":"2");VER ESTO
		
		setBeanMantCuenta(aporte);
		listarCondicionSocio(event);
		
	
		
	}

	public void eliminarMantCuenta(ActionEvent event) throws DaoException{
		log.info("----------------Debugging MantCuentasController.eliminarMantCuenta------------------");		
		String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMantCuentaModalPanel:hiddenIdEmpresa");
		String strIdTipoCaptacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMantCuentaModalPanel:hiddenIdTipoCaptacion");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmMantCuentaModalPanel:hiddenIdCodigo");
		log.info("strIdEmpresa 		 : "+strIdEmpresa);
		log.info("strIdTipoCaptacion : "+strIdTipoCaptacion);
		log.info("strIdCodigo 		 : "+strIdCodigo);				
		Aportes aporte = new Aportes();
		aporte.setIntIdEmpresa((strIdEmpresa!=null)?Integer.parseInt(strIdEmpresa):null);
		aporte.setIntIdTipoCaptacion((strIdTipoCaptacion!=null)?Integer.parseInt(strIdTipoCaptacion):null);
		aporte.setIntIdCodigo((strIdCodigo!=null)?Integer.parseInt(strIdCodigo):null);
		getCreditosService().eliminarMantCuenta(aporte);
    }	

	
}