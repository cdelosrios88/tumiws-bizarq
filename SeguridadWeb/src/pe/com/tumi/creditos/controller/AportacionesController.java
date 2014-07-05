package pe.com.tumi.creditos.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.creditos.domain.Aportes;
import pe.com.tumi.creditos.domain.CondSocio;
import pe.com.tumi.creditos.service.impl.AportacionesServiceImpl;
import pe.com.tumi.seguridad.domain.BeanSesion;

/************************************************************************/
/* Nombre de la clase: AportacionesController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de Aportaciones */
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 08/02/2012 */
/* ********************************************************************* */

public class AportacionesController extends GenericController {
	private AportacionesServiceImpl aportacionesService;
	private int 				rows = 5;
	private List 				beanListAportaciones;
	private List 				beanListCondSocio;
	private Aportes				beanAportacion = new Aportes();
	private CondSocio			beanCondSocio = new CondSocio();
	private BeanSesion 			beanSesion = new BeanSesion();
	//Variables generales
	private Boolean 			formAportacionesRendered = false;
	private Integer				intIdEstadoAportacion;
	private String				strNombreAporte;
	private Integer				intIdCondicionAportacion;
	private Integer				intIdTipoConfig;
	private Date				daFecIni;
	private Date				daFecFin;
	private Integer				intIdTipoPersona;
	private Boolean				chkAportVigentes;
	private Boolean				chkAportCaduco;
	private String				rbCondSocio;
	private String				strValAporte;
	private Boolean				chkTasaInt;
	private Boolean				chkLimEdad;
	
	//Variables de activación y desactivación de controles
	private Boolean				chkNombAporte;
	private Boolean				enabDisabNombAporte = true;
	private Boolean				chkCondAporte;
	private Boolean				enabDisabCondAporte = true;
	private Boolean				chkFechas;
	private Boolean				enabDisabFechasAport = true;
	private String				rbFecIndeterm;
	private Boolean				fecFinAportacionRendered = true;
	private Date				daFechaIni;
	private Date				daFechaFin;
	private Boolean				chkTasaInteres;
	private Boolean				enabDisabTasaInt = true;
	private Boolean				enabDisabPorcInt = true;
	private Boolean				enabDisabTea = true;
	private Boolean				enabDisabTna = true;
	private Boolean				chkLimiteEdad;
	private Boolean				enabDisabLimiteEdad = true;
	private String				rbTasaInteres;
	
	private Boolean				chkProvision;
	private Boolean				enabDisabProvision = true;
	private Boolean				chkExtProvision;
	private Boolean				enabDisabExtProvision = true;
	private Boolean				chkCancelacion;
	private Boolean				enabDisabCancelacion = true;
	
	//Mensajes de Error
	private String				msgTxtDescripcion;
	private String				msgTxtFechaIni;
	private String				msgTxtEstadoAporte;
	private String				msgTxtTipoPersona;
	private String				msgTxtCondSocio;
	private String				msgTxtTipoDscto;
	private String				msgTxtTipoConfig;
	private String				msgTxtMoneda;
	private String				msgTxtAplicacion;
	private String				msgTxtTNA;
	
	//Getters y Setters
	public AportacionesServiceImpl getAportacionesService() {
		return aportacionesService;
	}
	public void setAportacionesService(AportacionesServiceImpl aportacionesService) {
		this.aportacionesService = aportacionesService;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public List getBeanListAportaciones() {
		return beanListAportaciones;
	}
	public void setBeanListAportaciones(List beanListAportaciones) {
		this.beanListAportaciones = beanListAportaciones;
	}
	public List getBeanListCondSocio() {
		return beanListCondSocio;
	}
	public void setBeanListCondSocio(List beanListCondSocio) {
		this.beanListCondSocio = beanListCondSocio;
	}
	public Aportes getBeanAportacion() {
		return beanAportacion;
	}
	public void setBeanAportacion(Aportes beanAportacion) {
		this.beanAportacion = beanAportacion;
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
	public Boolean getFormAportacionesRendered() {
		return formAportacionesRendered;
	}
	public void setFormAportacionesRendered(Boolean formAportacionesRendered) {
		this.formAportacionesRendered = formAportacionesRendered;
	}
	public Integer getIntIdEstadoAportacion() {
		return intIdEstadoAportacion;
	}
	public void setIntIdEstadoAportacion(Integer intIdEstadoAportacion) {
		this.intIdEstadoAportacion = intIdEstadoAportacion;
	}
	public String getStrNombreAporte() {
		return strNombreAporte;
	}
	public void setStrNombreAporte(String strNombreAporte) {
		this.strNombreAporte = strNombreAporte;
	}
	public Integer getIntIdCondicionAportacion() {
		return intIdCondicionAportacion;
	}
	public void setIntIdCondicionAportacion(Integer intIdCondicionAportacion) {
		this.intIdCondicionAportacion = intIdCondicionAportacion;
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
	public Boolean getChkAportVigentes() {
		return chkAportVigentes;
	}
	public void setChkAportVigentes(Boolean chkAportVigentes) {
		this.chkAportVigentes = chkAportVigentes;
	}
	public Boolean getChkAportCaduco() {
		return chkAportCaduco;
	}
	public void setChkAportCaduco(Boolean chkAportCaduco) {
		this.chkAportCaduco = chkAportCaduco;
	}
	public String getRbCondSocio() {
		return rbCondSocio;
	}
	public void setRbCondSocio(String rbCondSocio) {
		this.rbCondSocio = rbCondSocio;
	}
	public String getStrValAporte() {
		return strValAporte;
	}
	public void setStrValAporte(String strValAporte) {
		this.strValAporte = strValAporte;
	}
	public Boolean getChkTasaInt() {
		return chkTasaInt;
	}
	public void setChkTasaInt(Boolean chkTasaInt) {
		this.chkTasaInt = chkTasaInt;
	}
	public Boolean getChkLimEdad() {
		return chkLimEdad;
	}
	public void setChkLimEdad(Boolean chkLimEdad) {
		this.chkLimEdad = chkLimEdad;
	}
	public Boolean getChkNombAporte() {
		return chkNombAporte;
	}
	public void setChkNombAporte(Boolean chkNombAporte) {
		this.chkNombAporte = chkNombAporte;
	}public Boolean getEnabDisabNombAporte() {
		return enabDisabNombAporte;
	}
	public void setEnabDisabNombAporte(Boolean enabDisabNombAporte) {
		this.enabDisabNombAporte = enabDisabNombAporte;
	}
	public Boolean getChkCondAporte() {
		return chkCondAporte;
	}
	public void setChkCondAporte(Boolean chkCondAporte) {
		this.chkCondAporte = chkCondAporte;
	}
	public Boolean getEnabDisabCondAporte() {
		return enabDisabCondAporte;
	}
	public void setEnabDisabCondAporte(Boolean enabDisabCondAporte) {
		this.enabDisabCondAporte = enabDisabCondAporte;
	}
	public Boolean getChkFechas() {
		return chkFechas;
	}
	public void setChkFechas(Boolean chkFechas) {
		this.chkFechas = chkFechas;
	}
	public Boolean getEnabDisabFechasAport() {
		return enabDisabFechasAport;
	}
	public void setEnabDisabFechasAport(Boolean enabDisabFechasAport) {
		this.enabDisabFechasAport = enabDisabFechasAport;
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
	public Boolean getFecFinAportacionRendered() {
		return fecFinAportacionRendered;
	}
	public void setFecFinAportacionRendered(Boolean fecFinAportacionRendered) {
		this.fecFinAportacionRendered = fecFinAportacionRendered;
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
	public Boolean getEnabDisabPorcInt() {
		return enabDisabPorcInt;
	}
	public void setEnabDisabPorcInt(Boolean enabDisabPorcInt) {
		this.enabDisabPorcInt = enabDisabPorcInt;
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
	public Boolean getChkLimiteEdad() {
		return chkLimiteEdad;
	}
	public void setChkLimiteEdad(Boolean chkLimiteEdad) {
		this.chkLimiteEdad = chkLimiteEdad;
	}
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
	public Boolean getChkProvision() {
		return chkProvision;
	}
	public void setChkProvision(Boolean chkProvision) {
		this.chkProvision = chkProvision;
	}
	public Boolean getEnabDisabProvision() {
		return enabDisabProvision;
	}
	public void setEnabDisabProvision(Boolean enabDisabProvision) {
		this.enabDisabProvision = enabDisabProvision;
	}
	public Boolean getChkExtProvision() {
		return chkExtProvision;
	}
	public void setChkExtProvision(Boolean chkExtProvision) {
		this.chkExtProvision = chkExtProvision;
	}
	public Boolean getEnabDisabExtProvision() {
		return enabDisabExtProvision;
	}
	public void setEnabDisabExtProvision(Boolean enabDisabExtProvision) {
		this.enabDisabExtProvision = enabDisabExtProvision;
	}
	public Boolean getChkCancelacion() {
		return chkCancelacion;
	}
	public void setChkCancelacion(Boolean chkCancelacion) {
		this.chkCancelacion = chkCancelacion;
	}
	public Boolean getEnabDisabCancelacion() {
		return enabDisabCancelacion;
	}
	public void setEnabDisabCancelacion(Boolean enabDisabCancelacion) {
		this.enabDisabCancelacion = enabDisabCancelacion;
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
	public String getMsgTxtEstadoAporte() {
		return msgTxtEstadoAporte;
	}
	public void setMsgTxtEstadoAporte(String msgTxtEstadoAporte) {
		this.msgTxtEstadoAporte = msgTxtEstadoAporte;
	}
	public String getMsgTxtTipoPersona() {
		return msgTxtTipoPersona;
	}
	public void setMsgTxtTipoPersona(String msgTxtTipoPersona) {
		this.msgTxtTipoPersona = msgTxtTipoPersona;
	}
	public String getMsgTxtCondSocio() {
		return msgTxtCondSocio;
	}
	public void setMsgTxtCondSocio(String msgTxtCondSocio) {
		this.msgTxtCondSocio = msgTxtCondSocio;
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
	// Métodos a implementar
	/**************************************************************/
	/*  Nombre :  habilitarGrabarHojaPlan()     		      		*/
	/*                                                    	 		*/
	/*  Parametros. :  Ninguno					           	 		*/
	/*  Objetivo: Habilitar el Formulario para el llenado del mimso */
	/*  Retorno : El formulario habilitado para su respectivo llenado */
	/**************************************************************/
	public void habilitarGrabarAportaciones(ActionEvent event) {
		log.info("---------------Debugging AportacionesController.habilitarGrabarAportaciones------------------");
		setFormAportacionesRendered(true);
		limpiarAportaciones();
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarAportaciones()     			      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarAportaciones(){
		Aportes aport = new Aportes();
		setBeanAportacion(aport);
		setMsgTxtDescripcion("");
		setMsgTxtFechaIni("");
		setMsgTxtEstadoAporte("");
		setMsgTxtTipoPersona("");
		setMsgTxtCondSocio("");
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
		setStrValAporte("");
		setRbTasaInteres("");
		setFecFinAportacionRendered(true);
		setChkTasaInteres(false);
		setEnabDisabTasaInt(true);
		setEnabDisabPorcInt(true);
		setEnabDisabTea(true);
		setEnabDisabTna(true);
		setChkLimiteEdad(false);
		setEnabDisabLimiteEdad(true);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  cancelarGrabarAportaciones() 		          		*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*                                                    	 		*/
	/*  Objetivo: Cancelar la nueva Hoja de Planeamiento     		*/
	/*            Poblacional.                               	  	*/
	/*  Retorno : Se oculta el Formulario de Aportaciones		 	*/
	/**************************************************************/
	public void cancelarGrabarAportaciones(ActionEvent event) {
		log.info("------------------Debugging AdmFormDocController.cancelarGrabarAportaciones---------------------");
		setFormAportacionesRendered(false);
		limpiarAportaciones();
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarAportaciones()        						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**************************************************************/
	public void listarAportaciones(ActionEvent event) {
		log.info("---------------------Debugging AportacionesController.listarAportaciones---------------------------");
		setCreditosService(aportacionesService);
		log.info("Se ha seteado el Service");
		
	    Aportes aport = new Aportes();
	    
	    aport.setIntIdTipoCaptacion(Constante.CAPTACION_APORTACIONES);
	    
	    aport.setIntIdEstSolicitud(getIntIdEstadoAportacion());
	    aport.setStrDescripcion(getStrNombreAporte());
	    
	    aport.setIntIdTipoConfig(getIntIdTipoConfig());
	    String strFechaIni = (getDaFecIni()!=null)?Constante.sdf.format(getDaFecIni()):null;
		String strFechaFin = (getDaFecFin()!=null)?Constante.sdf.format(getDaFecFin()):null;
		
		aport.setDaFecIni(strFechaIni);
	    aport.setDaFecFin(strFechaFin);
	    aport.setIntIdTipoPersona(getIntIdTipoPersona());
	    aport.setIntIdCondicionSocio(getIntIdCondicionAportacion()!=null ? getIntIdCondicionAportacion():null);
	    
	    aport.setIntChkTasaInteres(getChkTasaInt()!=null && getChkTasaInt()==true?1:0);
	    aport.setIntChkEdadLimite(getChkLimEdad()!=null && getChkLimEdad()==true?1:0);
	    aport.setIntChkAportVigentes(getChkAportVigentes()!=null && getChkAportVigentes()==true?1:0);
	    aport.setIntChkAportCaduco(getChkAportCaduco()!=null && getChkAportCaduco()==true?1:0);
	    
	    ArrayList listaAportaciones = new ArrayList();
	    try {
	    	listaAportaciones = getCreditosService().listarAportaciones(aport);
		} catch (DaoException e) {
			log.info("ERROR getCreditosService().listarAportaciones() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		setBeanListAportaciones(listaAportaciones);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  enableDisableControls()        					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Habilitar y Desabilitar determinados controles    */
	/*            de acuerdo a determinados cambios de estado. 		*/
	/*                         						     	 		*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**************************************************************/
	public void enableDisableControls(ActionEvent event) {
		log.info("--------------Debugging AportacionesController.enableDisableControls------------------");
		setEnabDisabNombAporte(getChkNombAporte()!=true);
		setStrNombreAporte(getChkNombAporte()!=true?"":getStrNombreAporte());
		setEnabDisabCondAporte(getChkCondAporte()!=true);
		setIntIdCondicionAportacion(getChkCondAporte()!=true?0:getIntIdCondicionAportacion());
		setEnabDisabFechasAport(getChkFechas()!=true);
		setDaFecIni(getChkFechas()!=true?null:getDaFecIni());
		setDaFecFin(getChkFechas()!=true?null:getDaFecFin());
		
		setEnabDisabTasaInt(getChkTasaInteres()!=true);
		setEnabDisabPorcInt(getChkTasaInteres()==true);
		setEnabDisabTea(getChkTasaInteres()==true);
		setEnabDisabTna(getChkTasaInteres()!=true);
		setEnabDisabLimiteEdad(getChkLimiteEdad()!=true);
		
		setEnabDisabProvision(getChkProvision()!=true);
		setEnabDisabExtProvision(getChkExtProvision()!=true);
		setEnabDisabCancelacion(getChkCancelacion()!=true);
		
		if(getRbTasaInteres()!=null){
			setEnabDisabPorcInt(!getRbTasaInteres().equals("1"));
			setEnabDisabTea(!getRbTasaInteres().equals("2"));
		}
		
		if(getRbFecIndeterm()!=null){
			setFecFinAportacionRendered(getRbFecIndeterm().equals("1"));
			setDaFechaFin(null);
		}
		
		if(getChkTasaInteres()==false){
			setRbTasaInteres(null);
			setEnabDisabTasaInt(true);
			setEnabDisabPorcInt(true);
			setEnabDisabTea(true);
			setEnabDisabTna(true);
		}
		
		Aportes aport = new Aportes();
		aport = (Aportes) getBeanAportacion();
		if(getRbTasaInteres()!=null && getRbTasaInteres().equals("1")){
			aport.setFlTem(new Float(0.0));
			aport.setIntIdTasaNaturaleza(null);
			aport.setIntIdTasaFormula(null);
			aport.setFlTea(new Float(0.0));
		}else{
			aport.setFlTem(new Float(0.0));
			aport.setIntIdTasaNaturaleza(null);
			aport.setIntIdTasaFormula(null);
			aport.setFlTea(new Float(0.0));
		}
		if(getChkLimiteEdad()!=null && getChkLimiteEdad()==false){
			aport.setIntEdadLimite(null);
		}
		setBeanAportacion(aport);
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarControlProceso()        					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**************************************************************/
	/*public void showFecFin(ValueChangeEvent event) throws DaoException{
		log.info("----------------Debugging AportacionesController.showFecFin-------------------");
		String idFecHorario = (String)event.getNewValue();
		setFecFinAportacionRendered(idFecHorario.equals("1"));
		setDaFechaFin(idFecHorario.equals("1")?null:getDaFechaFin());
	}*/
	
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
	public void listarCondSocio(ActionEvent event) throws DaoException{
		log.info("----------------Debugging AportacionesController.listarCondSocio------------------");
		setCreditosService(aportacionesService);
		log.info("Se ha seteado el Service");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdCodigo");
		
		if(getRbCondSocio().equals("2")){
			ArrayList listaCondSocio = new ArrayList();
			ArrayList arrayCondSocio = new ArrayList();
			
			HashMap prmtParametros = new HashMap();
			prmtParametros.put("pIntIdEmpresa", 	  beanSesion.getIntIdEmpresa());
			prmtParametros.put("pIntIdTipoCaptacion", Constante.CAPTACION_APORTACIONES);
			prmtParametros.put("pIntIdCodigo", 		  (strIdCodigo!=null?Integer.parseInt(strIdCodigo):null));
			
			//Obteniendo lista
			log.info("Obteniendo array de Parametros.");
			arrayCondSocio = getCreditosService().listarCondSocio(prmtParametros);
			log.info("arrayMotivos.size(): "+arrayCondSocio.size());
	        for(int i=0; i<arrayCondSocio.size() ; i++){
	        	CondSocio condsoc = (CondSocio) arrayCondSocio.get(i);
	        	log.info("condsoc.getIntIdValor(): "	+condsoc.getIntIdValor());
	        	log.info("condsoc.getStrDescripcion(): "+condsoc.getStrDescripcion());
	        	condsoc.setChkSocio(condsoc.getIntIdValor()==1);
	        	listaCondSocio.add(condsoc);
	        }
			setBeanListCondSocio(listaCondSocio);
		}else {
			if(beanListCondSocio != null){
				beanListCondSocio.clear();
			}
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  grabarAportaciones()        						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Guardar los datos ingresados en la ventan de 		*/
	/*            Aportaciones 						     	 		*/
	/*                         						     	 		*/
	/*  Retorno : Datos grabados correctamente en la tabla de 	 	*/
	/*            CRE_M_CONFCAPTACION				     	 		*/
	/**
	 * @throws DaoException ************************************************************/
	public void grabarAportaciones(ActionEvent event) throws DaoException{
		log.info("----------------Debugging AportacionesController.grabarAportaciones------------------");
		setCreditosService(aportacionesService);
		log.info("Se ha seteado el Service");
		Aportes aport = (Aportes) getBeanAportacion();
		
		aport.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
		aport.setIntIdTipoCaptacion(Constante.CAPTACION_APORTACIONES);
		aport.setIntIdEstSolicitud(1);
		
		Date daFecIni = getDaFechaIni();
		String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
		aport.setDaFecIni(daFechaIni);
		
		Date daFecFin = getDaFechaFin();
		String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
		aport.setDaFecFin(daFechaFin);
		
		Boolean bValidar = true;
		if (aport.getStrDescripcion().equals("")) {
			setMsgTxtDescripcion("* El campo Nombre del Aporte debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtDescripcion("");
		}
		if (aport.getDaFecIni()==null) {
			setMsgTxtFechaIni("* El campo Fecha de Inicio debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtFechaIni("");
		}
		if(daFecIni.equals(daFecFin)){
			setMsgTxtFechaIni("* Las Fechas son iguales.");
			bValidar = false;
		}else{
			setMsgTxtFechaIni("");
		}
		if(daFecIni.after(daFecFin)){
			setMsgTxtFechaIni("* La Fecha de Fin es menor a la Fecha de Inicio.");
			bValidar = false;
		}else{
			setMsgTxtFechaIni("");
		}
		
		if (aport.getIntIdEstado()== 0) {
			setMsgTxtEstadoAporte("* El campo Estado del Aporte debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtEstadoAporte("");
		}
		if (aport.getIntIdTipoPersona()== 0) {
			setMsgTxtTipoPersona("* El campo Tipo de Persona debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtTipoPersona("");
		}
		if (aport.getIntIdTipoDcto()== 0) {
			setMsgTxtTipoDscto("* El campo Tipo de Descuento debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtTipoDscto("");
		}
		if (aport.getIntIdTipoConfig()== 0) {
			setMsgTxtTipoDscto("* El campo Tipo de Configuración debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtTipoConfig("");
		}
		if (aport.getIntIdMoneda()== 0) {
			setMsgTxtMoneda("* El Tipo de Moneda debe ser ingresado.");
			bValidar = false;
		} else {
			setMsgTxtMoneda("");
		}
		if(getChkTasaInteres()==true && aport.getFlTna()==0){
			setMsgTxtTNA("* Debe ingresar el campo TNA (Tasa Nóminal Anual)");
			bValidar = false;			
		}else{
			setMsgTxtTNA("");
		}
		
		if (bValidar == true) {
			try {
				getCreditosService().grabarConfCaptacion(aport);
			} catch (DaoException e) {
				log.info("ERROR getCreditosService().grabarConfCaptacion(usu:)"+ e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Eliminando el Detalle de la Condición del Socio
			try{
				getCreditosService().eliminarCondSocio(aport);
			}catch(DaoException e){
				log.info("ERROR  getCreditosService().eliminarCondSocio() " + e.getMessage());
				e.printStackTrace();
			}
			
			if(getRbCondSocio().equals("1")){
				ArrayList lstCondSoc = new ArrayList();
				
				HashMap prmtCondSoc = new HashMap();
				prmtCondSoc.put("pIntIdEmpresa", 		beanSesion.getIntIdEmpresa());
				prmtCondSoc.put("pIntIdTipoCaptacion", 	Constante.CAPTACION_APORTACIONES);
				prmtCondSoc.put("pIntIdCodigo", 		null);
				lstCondSoc = getCreditosService().listarCondSocio(prmtCondSoc);
				
				log.info("lstCondSoc.size(): "+lstCondSoc.size());
				for(int i=0;i<lstCondSoc.size(); i++){
					CondSocio cond = (CondSocio) lstCondSoc.get(i);
					cond.setIntIdEmpresa(beanSesion.getIntIdEmpresa());
					cond.setIntIdTipoCaptacion(Constante.CAPTACION_APORTACIONES);
					cond.setIntIdCodigo(aport.getIntIdCodigo());
					cond.setIntIdValor(1);
					
					getCreditosService().grabarCondicionSocio(cond);
				}
			}
			
			for(int i=0; i<getBeanListCondSocio().size(); i++){
				CondSocio cond = (CondSocio) getBeanListCondSocio().get(i);
				cond.setIntIdEmpresa(aport.getIntIdEmpresa());
				cond.setIntIdTipoCaptacion(Constante.CAPTACION_APORTACIONES);
				cond.setIntIdCodigo(aport.getIntIdCodigo());
				cond.setIntIdValor(cond.getChkSocio()==true?1:0);
				try {
					getCreditosService().grabarCondicionSocio(cond);
				} catch (DaoException e) {
					log.info("ERROR  getService().grabarCondicionSocio(dom:) " + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			listarAportaciones(event);
			setMessageSuccess("Los datos se actualizaron satisfactoriamente.");
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
	public void modificarAportaciones(ActionEvent event) throws DaoException, ParseException{
		log.info("-----------------Debugging registroPcController.modificarAportaciones----------------------");
		setCreditosService(aportacionesService);
		String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdEmpresa");
		String strIdTipoCaptacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdTipoCaptacion");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdCodigo");
		log.info("strIdEmpresa 		 : "+strIdEmpresa);
		log.info("strIdTipoCaptacion : "+strIdTipoCaptacion);
		log.info("strIdCodigo 		 : "+strIdCodigo);
		
		Aportes aport = new Aportes();
		aport.setIntIdEmpresa((strIdEmpresa!=null)?Integer.parseInt(strIdEmpresa):null);
		aport.setIntIdTipoCaptacion((strIdTipoCaptacion!=null)?Integer.parseInt(strIdTipoCaptacion):null);
		aport.setIntIdCodigo((strIdCodigo!=null)?Integer.parseInt(strIdCodigo):null);
		
		/*HashMap prmtAportacion = new HashMap();
		prmtAportacion.put("pIntIdEmpresa", 	 (strIdEmpresa!=null)?Integer.parseInt(strIdEmpresa):null);
		prmtAportacion.put("pIntIdTipoCaptacion",(strIdTipoCaptacion!=null)?Integer.parseInt(strIdTipoCaptacion):null);
		prmtAportacion.put("pIntIdCodigo", 		 (strIdCodigo!=null)?Integer.parseInt(strIdCodigo):null);*/
		
		ArrayList listaAportaciones = new ArrayList();
		listaAportaciones = getCreditosService().listarAportaciones(aport);
		log.info("listaAportaciones.size(): "+listaAportaciones.size());
		
		Aportes lstAport = new Aportes();
		lstAport = (Aportes)listaAportaciones.get(0);
		log.info("aport.getIntIdEmpresa(): 		"+lstAport.getIntIdEmpresa());
		log.info("aport.getIntIdTipoCaptacion():"+lstAport.getIntIdTipoCaptacion());
		log.info("aport.getIntIdCodigo(): 		"+lstAport.getIntIdCodigo());
		
		setFormAportacionesRendered(true);
		
		String daFecIni = "" + lstAport.getDaFecIni() == null ? "" : lstAport.getDaFecIni();
		Date fecIni = (daFecIni == null || daFecIni.equals("") ? null : Constante.sdf.parse(daFecIni));
		setDaFechaIni(fecIni);
		
		String daFecFin = "" + lstAport.getDaFecFin() == null ? "" : lstAport.getDaFecFin();
		Date fecFin = (daFecFin == null || daFecFin.equals("") ? null : Constante.sdf.parse(daFecFin));
		setDaFechaFin(fecFin);
		
		setFecFinAportacionRendered(fecFin!=null);
		setRbFecIndeterm(fecFin!=null?"1":"2");
		
		setChkTasaInteres(lstAport.getFlTna()!=null);
		setEnabDisabTasaInt(lstAport.getFlTna()==null || lstAport.getFlTna()==0);
		setRbTasaInteres(lstAport.getFlTem()!=null?"1":"2");
		setEnabDisabPorcInt(lstAport.getFlTem()==0);
		setEnabDisabTea(lstAport.getFlTea()==0);
		setEnabDisabTna(lstAport.getFlTna()==null || lstAport.getFlTna()==0);
		
		setEnabDisabLimiteEdad(lstAport.getIntEdadLimite()==null);
		setChkLimiteEdad(lstAport.getIntEdadLimite()!=null);
		setRbCondSocio(lstAport.getIntCntCondSoc()==4?"1":"2");
		
		setBeanAportacion(lstAport);
		listarCondSocio(event);
	}
	
	public void eliminarAportacion(ActionEvent event) throws DaoException{
		log.info("----------------Debugging AportacionesController.eliminarAportacion------------------");
		Aportes aport = (Aportes) getBeanAportacion();
		
		getCreditosService().eliminarAportacion(aport);
    }
	
}