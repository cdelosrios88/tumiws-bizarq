package pe.com.tumi.credito.socio.captacion.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.CondicionComp;
import pe.com.tumi.credito.socio.captacion.domain.CondicionId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeLocal;
import pe.com.tumi.credito.socio.convenio.domain.composite.CaptacionComp;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;

/************************************************************************/
/* Nombre de la clase: CreditoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de Aportaciones */
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 08/02/2012 */
/* ********************************************************************* */

public class AportacionesController {
	protected  static Logger 	log 			= Logger.getLogger(AportacionesController.class);
	private int 				rows = 5;
	private List 				beanListAportaciones;
	private List 				beanListCondicion;
	private Captacion			beanCaptacion = new Captacion();
	private Condicion			beanCondicion = new Condicion();
	//private BeanSesion 		beanSesion = new BeanSesion();
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
	private Boolean				blnVigencia = true;
	private Integer				rbVigente;
	private String				rbCondicion;
	private String				strValAporte;
	private Boolean				chkTasaInt;
	private Boolean				chkLimEdad;
	private Integer				intTipoFecha;
	
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
	private Boolean				enabDisabValImporte = true;
	private Boolean				enabDisabValPorcentaje = true;
	
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
	private String				msgTxtCondicion;
	private String				msgTxtTipoDscto;
	private String				msgTxtTipoConfig;
	private String				msgTxtMoneda;
	private String				msgTxtAplicacion;
	private String				msgTxtTNA;
	
	private String 				strAportacion;
	//
	private List<CaptacionComp>	listaCaptacionComp;
	private List<CondicionComp>	listaCondicionComp;
	
	private Boolean				blnAportaciones;
	private Boolean				blnMantCuenta;
	
	public AportacionesController(){
		inicio(null);
	}
	
	public void inicio(ActionEvent event){
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		Usuario usuario = null;
		Integer MENU_APORTES = 160;
		Integer MENU_MANTCUENTA = 161;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			id = new PermisoPerfilId();
			id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
			id.setIntIdTransaccion(MENU_APORTES);
			id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
			PermisoFacadeRemote localPermiso = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnAportaciones = (permiso == null)?true:false;
			id.setIntIdTransaccion(MENU_MANTCUENTA);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnMantCuenta = (permiso == null)?true:false;
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}
	}
	
	//Getters y Setters
	public Captacion getBeanCaptacion() {
		return beanCaptacion;
	}
	public void setBeanCaptacion(Captacion beanCaptacion) {
		this.beanCaptacion = beanCaptacion;
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
	public List getBeanListCondicion() {
		return beanListCondicion;
	}
	public void setBeanListCondicion(List beanListCondicion) {
		this.beanListCondicion = beanListCondicion;
	}public Condicion getBeanCondicion() {
		return beanCondicion;
	}
	public void setBeanCondicion(Condicion beanCondicion) {
		this.beanCondicion = beanCondicion;
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
	public Boolean getBlnVigencia() {
		return blnVigencia;
	}
	public void setBlnVigencia(Boolean blnVigencia) {
		this.blnVigencia = blnVigencia;
	}
	public Integer getRbVigente() {
		return rbVigente;
	}
	public void setRbVigente(Integer rbVigente) {
		this.rbVigente = rbVigente;
	}
	public String getRbCondicion() {
		return rbCondicion;
	}
	public void setRbCondicion(String rbCondicion) {
		this.rbCondicion = rbCondicion;
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
	public Integer getIntTipoFecha() {
		return intTipoFecha;
	}
	public void setIntTipoFecha(Integer intTipoFecha) {
		this.intTipoFecha = intTipoFecha;
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
	public Boolean getEnabDisabValImporte() {
		return enabDisabValImporte;
	}
	public void setEnabDisabValImporte(Boolean enabDisabValImporte) {
		this.enabDisabValImporte = enabDisabValImporte;
	}
	public Boolean getEnabDisabValPorcentaje() {
		return enabDisabValPorcentaje;
	}
	public void setEnabDisabValPorcentaje(Boolean enabDisabValPorcentaje) {
		this.enabDisabValPorcentaje = enabDisabValPorcentaje;
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
	public String getMsgTxtCondicion() {
		return msgTxtCondicion;
	}
	public void setMsgTxtCondicion(String msgTxtCondicion) {
		this.msgTxtCondicion = msgTxtCondicion;
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
	public String getStrAportacion() {
		return strAportacion;
	}
	public void setStrAportacion(String strAportacion) {
		this.strAportacion = strAportacion;
	}
	public List<CaptacionComp> getListaCaptacionComp() {
		return listaCaptacionComp;
	}
	public void setListaCaptacionComp(List<CaptacionComp> listaCaptacionComp) {
		this.listaCaptacionComp = listaCaptacionComp;
	}
	public List<CondicionComp> getListaCondicionComp() {
		return listaCondicionComp;
	}
	public void setListaCondicionComp(List<CondicionComp> listaCondicionComp) {
		this.listaCondicionComp = listaCondicionComp;
	}
	public Boolean getBlnAportaciones() {
		return blnAportaciones;
	}
	public void setBlnAportaciones(Boolean blnAportaciones) {
		this.blnAportaciones = blnAportaciones;
	}
	public Boolean getBlnMantCuenta() {
		return blnMantCuenta;
	}
	public void setBlnMantCuenta(Boolean blnMantCuenta) {
		this.blnMantCuenta = blnMantCuenta;
	}
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	// Métodos a implementar
	/**************************************************************/
	/*  Nombre :  habilitarGrabarAportaciones()     		      		*/
	/*                                                    	 		*/
	/*  Parametros. :  Ninguno					           	 		*/
	/*  Objetivo: Habilitar el Formulario para el llenado del mimso */
	/*  Retorno : El formulario habilitado para su respectivo llenado */
	/**************************************************************/
	public void habilitarGrabarAportaciones(ActionEvent event) {
		setFormAportacionesRendered(true);
		limpiarAportaciones();
		strAportacion = Constante.MANTENIMIENTO_GRABAR;
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarAportaciones()     			      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarAportaciones(){
		Captacion aport = new Captacion();
		aport.setId(new CaptacionId());
		setBeanCaptacion(aport);
		setMsgTxtDescripcion("");
		setMsgTxtFechaIni("");
		setMsgTxtEstadoAporte("");
		setMsgTxtTipoPersona("");
		setMsgTxtCondicion("");
		setMsgTxtTipoDscto("");
		setMsgTxtTipoConfig("");
		setMsgTxtMoneda("");
		setMsgTxtAplicacion("");
		if(listaCondicionComp!=null){
			listaCondicionComp.clear();
		}
		setRbFecIndeterm("");
		setDaFechaIni(null);
		setDaFechaFin(null);
		setRbCondicion("");
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
		setEnabDisabValImporte(true);
		setEnabDisabValPorcentaje(true);
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
		log.info("-----------------------Debugging CreditoController.listarAportaciones-----------------------------");
		CaptacionFacadeLocal facade = null;
		try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			Captacion o = new Captacion();
			o.setId(new CaptacionId());
			o.setCondicion(new Condicion());
			o.getCondicion().setId(new CondicionId());
			o.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
			o.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_APORTACIONES);
			if(intTipoFecha!=null && intTipoFecha!=0)
				o.setIntTipoFecha(intTipoFecha);
			if(intIdEstadoAportacion!=0)
			o.setIntParaEstadoSolicitudCod(intIdEstadoAportacion);
			o.setStrDescripcion(strNombreAporte);
			if(intIdCondicionAportacion!=null && intIdCondicionAportacion!=0)
			o.getCondicion().getId().setIntParaCondicionSocioCod(intIdCondicionAportacion);
			if(intIdTipoConfig!=0)
			o.setIntParaTipoConfiguracionCod(intIdTipoConfig);
			
			String strFechaIni = (getDaFecIni()!=null)?Constante.sdf.format(getDaFecIni()):null;
			String strFechaFin = (getDaFecFin()!=null)?Constante.sdf.format(getDaFecFin()):null;
			o.setStrDtFechaIni(strFechaIni);
			o.setStrDtFechaFin(strFechaFin);
			o.setDtInicio(daFecIni);
			o.setDtFin(daFecFin);
			if(intIdTipoPersona!=-1)
				o.setIntParaTipopersonaCod(intIdTipoPersona);
			o.setIntTasaInteres(getChkTasaInt()==true?1:0);
			o.setIntLimiteEdad(getChkLimEdad() ==true?1:0);
			o.setIntVigencia(chkAportVigentes==true?1:null);
			o.setIntAportacionVigente(getRbVigente());
			
			listaCaptacionComp = facade.getListaCaptacionCompDeBusquedaCaptacion(o);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	/*public void listarAportaciones(ActionEvent event) {
		
	    Captacion aport = new Captacion();
	    aport.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_APORTACIONES);
	    aport.setIntParaEstadoSolicitudCod(getIntIdEstadoAportacion());
	    aport.setStrDescripcion(getStrNombreAporte());
	    aport.setIntParaTipoConfiguracionCod(getIntIdTipoConfig());
	    String strFechaIni = (getDaFecIni()!=null)?Constante.sdf.format(getDaFecIni()):null;
		String strFechaFin = (getDaFecFin()!=null)?Constante.sdf.format(getDaFecFin()):null;
		
		aport.setStrDtFechaIni(strFechaIni);
	    aport.setStrDtFechaFin(strFechaFin);
	    //aport.setIntIdTipoPersona(getIntIdTipoPersona());
	    //aport.setIntIdCondicionSocio(getIntIdCondicionAportacion()!=null ? getIntIdCondicionAportacion():null);
	    
	    aport.setIntChkTasaInteres(getChkTasaInt()!=null && getChkTasaInt()==true?1:0);
	    aport.setIntChkEdadLimite(getChkLimEdad()!=null && getChkLimEdad()==true?1:0);
	    aport.setIntChkAportVigentes(getChkAportVigentes()!=null && getChkAportVigentes()==true?1:0);
	    aport.setIntChkAportCaduco(getChkAportCaduco()!=null && getChkAportCaduco()==true?1:0);
	    
	    ArrayList listaAportaciones = new ArrayList();
	    /*try {
	    	listaAportaciones = getCreditosService().listarAportaciones(aport);
		} catch (DaoException e) {
			log.info("ERROR getCreditosService().listarAportaciones() " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		setBeanListAportaciones(listaAportaciones);
	}*/
	
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
		setEnabDisabNombAporte(getChkNombAporte()!=true);
		setStrNombreAporte(getChkNombAporte()!=true?"":getStrNombreAporte());
		setEnabDisabCondAporte(getChkCondAporte()!=true);
		setIntIdCondicionAportacion(getChkCondAporte()!=true?0:getIntIdCondicionAportacion());
		setEnabDisabFechasAport(getChkFechas()!=true);
		setDaFecIni(getChkFechas()!=true?null:getDaFecIni());
		setDaFecFin(getChkFechas()!=true?null:getDaFecFin());
		setBlnVigencia(chkAportVigentes!=true);
		setRbVigente(chkAportVigentes==true?1:null);
		setEnabDisabTasaInt(getChkTasaInteres()!=true);
		setEnabDisabPorcInt(getChkTasaInteres()==true);
		setEnabDisabTea(getChkTasaInteres()==true);
		setEnabDisabTna(getChkTasaInteres()!=true);
		setEnabDisabLimiteEdad(getChkLimiteEdad()!=true);
		
		setEnabDisabProvision(getChkProvision()!=true);
		setEnabDisabExtProvision(getChkExtProvision()!=true);
		setEnabDisabCancelacion(getChkCancelacion()!=true);
		
		if(getStrValAporte()!=null){
			setEnabDisabValImporte(!getStrValAporte().equals("1"));
			setEnabDisabValPorcentaje(!getStrValAporte().equals("2"));
		}
		
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
		
		Captacion aport = new Captacion();
		aport = (Captacion) getBeanCaptacion();
		if(getRbTasaInteres()!=null && getRbTasaInteres().equals("1")){
			aport.setBdTem(new BigDecimal(0.0));
			aport.setIntTasaNaturaleza(null);
			aport.setIntTasaFormula(null);
			aport.setBdTea(new BigDecimal(0.0));
		}else{
			aport.setBdTem(new BigDecimal(0.0));
			aport.setIntTasaNaturaleza(null);
			aport.setIntTasaFormula(null);
			aport.setBdTea(new BigDecimal(0.0));
		}
		if(getChkLimiteEdad()!=null && getChkLimiteEdad()==false){
			aport.setIntEdadLimite(null);
		}
		aport.setBdValorConfiguracion(getStrValAporte()!=null && getStrValAporte().equals("1")?aport.getBdValorConfiguracion():null);
		aport.setBdPorcConfiguracion(getStrValAporte()!=null && getStrValAporte().equals("2")?aport.getBdPorcConfiguracion():null);
		setBeanCaptacion(aport);
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
		log.info("----------------Debugging CreditoController.showFecFin-------------------");
		String idFecHorario = (String)event.getNewValue();
		setFecFinAportacionRendered(idFecHorario.equals("1"));
		setDaFechaFin(idFecHorario.equals("1")?null:getDaFechaFin());
	}*/
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarCondicion()        							*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**
	 * @throws DaoException ************************************************************/
	public void listarCondicion(ActionEvent event){
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdCodigo");
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = null;
		CondicionComp condicioncomp = null;
		List<CondicionComp> listCondicionComp = new ArrayList<CondicionComp>();
		
		if(getRbCondicion().equals("2")){
			try{
				tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
				for(int i=0;i<listaTabla.size(); i++){
					condicioncomp = new CondicionComp();
					condicioncomp.setTabla(new Tabla());
					condicioncomp.getTabla().setIntIdDetalle(listaTabla.get(i).getIntIdDetalle());
					condicioncomp.getTabla().setStrDescripcion(listaTabla.get(i).getStrDescripcion());
					listCondicionComp.add(condicioncomp);
				}
				listaCondicionComp = listCondicionComp;
			}catch(Exception e){
				e.printStackTrace();
			}
		}else {
			try{
				tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
				for(int i=0;i<listaTabla.size(); i++){
					condicioncomp = new CondicionComp();
					condicioncomp.setTabla(new Tabla());
					condicioncomp.getTabla().setIntIdDetalle(listaTabla.get(i).getIntIdDetalle());
					condicioncomp.getTabla().setStrDescripcion(listaTabla.get(i).getStrDescripcion());
					condicioncomp.setChkSocio(true);
					listCondicionComp.add(condicioncomp);
				}
				listaCondicionComp = listCondicionComp;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private Boolean isValidoAportacion(Captacion beanCaptacion){
		Boolean validCaptacion = true;
		if (beanCaptacion.getStrDescripcion().equals("")) {
			setMsgTxtDescripcion("* El campo Nombre del Aporte debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtDescripcion("");
		}
		Date daFecIni = getDaFechaIni();
		String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
		beanCaptacion.setStrDtFechaIni(daFechaIni);
		Date daFecFin = getDaFechaFin();
		String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
		//beanCaptacion.setStrDtFechaFin(daFechaFin);
		if (beanCaptacion.getStrDtFechaIni()==null) {
			setMsgTxtFechaIni("* El campo Fecha de Inicio debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtFechaIni("");
		}
		if(daFecIni.equals(daFecFin)){
			setMsgTxtFechaIni("* Las Fechas son iguales.");
			validCaptacion = false;
		}else{
			setMsgTxtFechaIni("");
		}
		if(daFecFin!=null){
			if(daFecIni.after(daFecFin)){
				setMsgTxtFechaIni("* La Fecha de Fin es menor a la Fecha de Inicio.");
				validCaptacion = false;
			}else{
				setMsgTxtFechaIni("");
			}
		}
		
		if (beanCaptacion.getIntParaTipoDescuentoCod()== 0) {
			setMsgTxtTipoDscto("* El campo Tipo de Descuento debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtTipoDscto("");
		}
		if (beanCaptacion.getIntParaTipoConfiguracionCod()== 0) {
			setMsgTxtTipoDscto("* El campo Tipo de Configuración debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtTipoConfig("");
		}
		if (beanCaptacion.getIntParaMonedaCod()== 0) {
			setMsgTxtMoneda("* El Tipo de Moneda debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtMoneda("");
		}
		if(getChkTasaInteres()==true && (beanCaptacion.getBdTna().compareTo(BigDecimal.ZERO)==0)){
			setMsgTxtTNA("* Debe ingresar el campo TNA (Tasa Nóminal Anual)");
			validCaptacion = false;			
		}else{
			setMsgTxtTNA("");
		}
	    return validCaptacion;
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  irModificarAportacion()      						*/
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
	
	public void irModificarAportaciones(ActionEvent event) throws ParseException{
    	log.info("-----------------------Debugging CreditoController.modificarAportaciones-----------------------------");
    	CaptacionFacadeLocal captacionFacade = null;
    	Integer intIdEmpresa = null;
    	String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdEmpresa");
		String strIdTipoCaptacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdTipoCaptacion");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdCodigo");
		log.info("strIdEmpresa 		 : "+strIdEmpresa);
		log.info("strIdTipoCaptacion : "+strIdTipoCaptacion);
		log.info("strIdCodigo 		 : "+strIdCodigo);
		
		CaptacionId captacionId = new CaptacionId();
		captacionId.setIntPersEmpresaPk(new Integer(strIdEmpresa));
		captacionId.setIntParaTipoCaptacionCod(new Integer(strIdTipoCaptacion));
		captacionId.setIntItem(new Integer(strIdCodigo));
    	try {
    		if(strIdCodigo != null && !strIdCodigo.trim().equals("")){
    			captacionFacade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
				beanCaptacion = captacionFacade.getCaptacionPorIdCaptacion(captacionId);
				
				String daFecIni = "" + (beanCaptacion.getStrDtFechaIni() == null ? "" : beanCaptacion.getStrDtFechaIni());
				Date fecIni = (daFecIni == null || daFecIni.equals("") ? null : Constante.sdf.parse(daFecIni));
				setDaFechaIni(fecIni);
				
				String daFecFin = "" + (beanCaptacion.getStrDtFechaFin() == null ? "" : beanCaptacion.getStrDtFechaFin());
				Date fecFin = (daFecFin == null || daFecFin.equals("") ? null : Constante.sdf.parse(daFecFin));
				setDaFechaFin(fecFin);
				
				setFecFinAportacionRendered(fecFin!=null);
				setRbFecIndeterm(fecFin!=null?"1":"2");
				
				setChkTasaInteres(beanCaptacion.getBdTna()!=null);
				setEnabDisabTasaInt(beanCaptacion.getBdTna()==null || beanCaptacion.getBdTna().compareTo(BigDecimal.ZERO)==0);
				setRbTasaInteres(beanCaptacion.getBdTem()!=null?"1":"2");
				setEnabDisabPorcInt(beanCaptacion.getBdTem().compareTo(BigDecimal.ZERO)==0);
				setEnabDisabTea(beanCaptacion.getBdTea().compareTo(BigDecimal.ZERO)==0);
				setEnabDisabTna(beanCaptacion.getBdTna()==null || beanCaptacion.getBdTna().compareTo(BigDecimal.ZERO)==0);
				setEnabDisabValImporte(beanCaptacion.getBdValorConfiguracion()==null);
				setEnabDisabValPorcentaje(beanCaptacion.getBdPorcConfiguracion()==null);
				setStrValAporte(beanCaptacion.getBdValorConfiguracion()!=null?"1":"2");
				setEnabDisabLimiteEdad(beanCaptacion.getIntEdadLimite()==null);
				setChkLimiteEdad(beanCaptacion.getIntEdadLimite()!=null);
				
				setRbCondicion(beanCaptacion.getListaCondicion().size()==4?"1":"2");
				listaCondicionComp = beanCaptacion.getListaCondicionComp();
				
				setFormAportacionesRendered(true);
				log.info("beanCaptacion.getIntParaEstadoCod(): "+beanCaptacion.getIntParaEstadoCod());
				setStrAportacion((beanCaptacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) || 
						beanCaptacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO) )?
						Constante.MANTENIMIENTO_ELIMINAR:Constante.MANTENIMIENTO_MODIFICAR);
				log.info("getStrAportacion(): "+getStrAportacion());
				//strAportacion = Constante.MANTENIMIENTO_MODIFICAR;
    		}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
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
	/**/
	/***********************************************************************************/
	public void grabarAportacion(ActionEvent event){
		CaptacionFacadeLocal facade = null;
		
	    if(isValidoAportacion(beanCaptacion) == false){
	    	log.info("Datos de zonal no válidos. Se aborta el proceso de grabación de zonal.");
	    	return;
	    }
	    //beanCaptacion.setId(new CaptacionId());
	    beanCaptacion.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);//beanSesion.getIntIdEmpresa()
	    beanCaptacion.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_APORTACIONES);
	    beanCaptacion.setIntParaEstadoSolicitudCod(Constante.PARAM_T_ESTADOSOLICPAGO_PENDIENTE);
	    beanCaptacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
	    
	    log.info("");
		
		Date daFecIni = getDaFechaIni();
		String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
		beanCaptacion.setStrDtFechaIni(daFechaIni);
		beanCaptacion.setDtInicio(daFecIni);
		
		Date daFecFin = getDaFechaFin();
		String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
		beanCaptacion.setStrDtFechaFin(daFechaFin);
		beanCaptacion.setDtFin(daFecFin);
		
		if(listaCondicionComp!=null){
			beanCaptacion.setListaCondicionComp(listaCondicionComp);
		}
	    
    	try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.grabarCaptacion(beanCaptacion);
			limpiarAportaciones();
			listarAportaciones(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  modificarAportacion()        						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Guardar los datos modificados de la ventana de	*/
	/*            Aportaciones 						     	 		*/
	/*                         						     	 		*/
	/*  Retorno : Datos grabados correctamente en la tabla de 	 	*/
	/*            CRE_M_CONFCAPTACION				     	 		*/
	/**/
	/***********************************************************************************/
	
	public void modificarAportacion(ActionEvent event){
    	CaptacionFacadeLocal facade = null;
    	if(isValidoAportacion(beanCaptacion) == false){
    		log.info("Datos de Captación no válidos. Se aborta el proceso de grabación de Aportación.");
    		return;
    	}
    	
    	Date daFecIni = getDaFechaIni();
		String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
		//beanCaptacion.setStrDtFechaIni(daFechaIni);
		beanCaptacion.setDtInicio(daFecIni);
		
		Date daFecFin = getDaFechaFin();
		String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
		//beanCaptacion.setStrDtFechaFin(daFechaFin);
		beanCaptacion.setDtFin(daFecFin);
		
		if(listaCondicionComp!=null){
			beanCaptacion.setListaCondicionComp(listaCondicionComp);
		}
    	
    	try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.modificarCaptacion(beanCaptacion);
			limpiarAportaciones();
			listarAportaciones(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
	
	public void eliminarAportacion(ActionEvent event){
    	log.info("-----------------------Debugging EmpresaController.eliminarZonal-----------------------------");
    	String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdEmpresa");
		String strIdTipoCaptacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdTipoCaptacion");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmAportacionModalPanel:hiddenIdCodigo");
		log.info("strIdEmpresa 		 : "+strIdEmpresa);
		log.info("strIdTipoCaptacion : "+strIdTipoCaptacion);
		log.info("strIdCodigo 		 : "+strIdCodigo);
		CaptacionFacadeLocal facade = null;
		Captacion captacion = null;
    	try {
    		captacion = new Captacion();
    		captacion.setId(new CaptacionId());
    		captacion.getId().setIntPersEmpresaPk(new Integer(strIdEmpresa));
    		captacion.getId().setIntParaTipoCaptacionCod(new Integer(strIdTipoCaptacion));
    		captacion.getId().setIntItem(new Integer(strIdCodigo));
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.eliminarCaptacion(captacion.getId());
			limpiarAportaciones();
			listarAportaciones(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
}