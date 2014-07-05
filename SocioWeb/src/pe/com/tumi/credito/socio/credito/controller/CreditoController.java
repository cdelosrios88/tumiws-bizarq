package pe.com.tumi.credito.socio.credito.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.TabableView;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoComp;
import pe.com.tumi.credito.socio.creditos.domain.CondicionComp;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilId;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteresId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTopeCaptacion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTopeCaptacionId;
import pe.com.tumi.credito.socio.creditos.domain.Finalidad;
import pe.com.tumi.credito.socio.creditos.domain.FinalidadId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
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
/* Fecha creación : 09/04/2012 */
/* ********************************************************************* */

public class CreditoController {
	protected  static Logger 	log 			= Logger.getLogger(CreditoController.class);
	private int 				rows = 5;
	private Credito				beanCredito = new Credito();
	private Integer				intIdEstadoSolicCredito;
	private Integer				intIdEstadoCredito;
	private Integer				intTipoCredito;
	private String				strNombreCredito;
	private Integer				intTipoCondicionLaboral;
	private Integer				intCondicionSocio;
	private Integer				intTipoConfiguracion;
	private Boolean				chkFecha;
	private Integer				intTipoFecha;
	private Date				daFecIni;
	private Date				daFecFin;
	private Integer				intIdTipoPersona;
	private Boolean				chkGarantia;
	private Boolean				chkDescuento;
	private Boolean				chkExcepcion;
	private Boolean				chkActivo;
	private Boolean				chkCaduco;
	//
	private String rbCondicion;
	private String strCredito;
	private Boolean	formCreditoRendered = false;
	private Boolean formCondSocio;
	private Boolean enabDisabFechasCredito = true;
	
	private BigDecimal bdPorcTasa;
	private Integer	intMesesMax;
	private BigDecimal bdMontoMax;
	
	private String[]			lstTipoCondSocio;
	private String[]			lstCondicionCredito;
	private String[]			lstFinalidadCredito;
	private String[]			lstRanMontoMin;
	private String[]			lstRanMontoMax;
	private String rbFecIndeterm;
	private Date daFechaIni;
	private Date daFechaFin;
	private Boolean fecFinCreditoRendered = true;
	
	//Mensajes de Error
	private String msgTxtDescripcion;
	private String msgTxtTipoCredito;
	private String msgTxtFechaIni;
	private String msgTxtTipoPersona;
	private String msgTxtTipoSbs;
	private String msgTxtMoneda;
	private String msgTxtTipoCalcInteres;
	private String msgTxtEstado;
	private String msgTxtPorcTasa;
	private String msgTxtMesMaximo;
	private String msgTxtMontoMaximo;
	private String msgTxtTipoCondSocio;
	
	private List<Tabla> listaTipoCaptacion;
	private List<Tabla> listaTipoCaptacion2;
	
	private List<CreditoComp>	listaCreditoComp;
	private List<CondicionComp>	listaCondicionComp;
	private List<Tabla> listaTipoCreditoSbs;
	private List<Tabla> listaTipoCreditoEmpresa;
	private List<Tabla> listaTipoRol;
	private List<CreditoInteres> listaCreditoInteres;
	private List<CreditoDescuento> listaCreditoDescuento;
	
	private Boolean blnCredito;
	
	private CreditoDescuentoController	creditoDescuentoController;
	private CreditoExcepcionController	creditoExcepcionController;
	private CreditoGarantiaPersonalController	creditoGarantiaPersonalController;
	private CreditoGarantiaRealController	creditoGarantiaRealController;
	private CreditoGarantiaAutoliquidableController	creditoGarantiaAutoliquidableController;
	private CreditoGarantiaRapidaRealizacionController	creditoGarantiaRapidaRealizacionController;
	
	// CGD - 022013-06
	private List<Tabla> listaCondicionSocioLaboral;
	private Integer intCondicionSocioInteres;
	
	
	public CreditoController(){
		listaTipoCreditoSbs = new ArrayList<Tabla>();
		listaCreditoInteres = new ArrayList<CreditoInteres>();
		listaCreditoDescuento = new ArrayList<CreditoDescuento>();
		inicio(null);
	}
	
	public void inicio(ActionEvent event){
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		Usuario usuario = null;
		Integer MENU_CREDITO = 114;
		try{
			TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			id = new PermisoPerfilId();
			id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
			id.setIntIdTransaccion(MENU_CREDITO);
			id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
			PermisoFacadeRemote localPermiso = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnCredito = (permiso == null)?true:false;

			listaCondicionSocioLaboral = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
						
						
						
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}
	}
	
	//Getters y Setters
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public Credito getBeanCredito() {
		return beanCredito;
	}
	public void setBeanCredito(Credito beanCredito) {
		this.beanCredito = beanCredito;
	}
	public Integer getIntIdEstadoSolicCredito() {
		return intIdEstadoSolicCredito;
	}
	public void setIntIdEstadoSolicCredito(Integer intIdEstadoSolicCredito) {
		this.intIdEstadoSolicCredito = intIdEstadoSolicCredito;
	}
	public Integer getIntIdEstadoCredito() {
		return intIdEstadoCredito;
	}
	public void setIntIdEstadoCredito(Integer intIdEstadoCredito) {
		this.intIdEstadoCredito = intIdEstadoCredito;
	}
	public Integer getIntTipoCredito() {
		return intTipoCredito;
	}
	public void setIntTipoCredito(Integer intTipoCredito) {
		this.intTipoCredito = intTipoCredito;
	}
	public String getStrNombreCredito() {
		return strNombreCredito;
	}
	public void setStrNombreCredito(String strNombreCredito) {
		this.strNombreCredito = strNombreCredito;
	}
	public Integer getIntTipoCondicionLaboral() {
		return intTipoCondicionLaboral;
	}
	public void setIntTipoCondicionLaboral(Integer intTipoCondicionLaboral) {
		this.intTipoCondicionLaboral = intTipoCondicionLaboral;
	}
	public Integer getIntCondicionSocio() {
		return intCondicionSocio;
	}
	public void setIntCondicionSocio(Integer intCondicionSocio) {
		this.intCondicionSocio = intCondicionSocio;
	}
	public Integer getIntTipoConfiguracion() {
		return intTipoConfiguracion;
	}
	public void setIntTipoConfiguracion(Integer intTipoConfiguracion) {
		this.intTipoConfiguracion = intTipoConfiguracion;
	}
	public Boolean getChkFecha() {
		return chkFecha;
	}
	public void setChkFecha(Boolean chkFecha) {
		this.chkFecha = chkFecha;
	}
	public Integer getIntTipoFecha() {
		return intTipoFecha;
	}
	public void setIntTipoFecha(Integer intTipoFecha) {
		this.intTipoFecha = intTipoFecha;
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
	public Integer getIntIdTipoPersona() {
		return intIdTipoPersona;
	}
	public void setIntIdTipoPersona(Integer intIdTipoPersona) {
		this.intIdTipoPersona = intIdTipoPersona;
	}
	public Boolean getChkGarantia() {
		return chkGarantia;
	}
	public void setChkGarantia(Boolean chkGarantia) {
		this.chkGarantia = chkGarantia;
	}
	public Boolean getChkDescuento() {
		return chkDescuento;
	}
	public void setChkDescuento(Boolean chkDescuento) {
		this.chkDescuento = chkDescuento;
	}
	public Boolean getChkExcepcion() {
		return chkExcepcion;
	}
	public void setChkExcepcion(Boolean chkExcepcion) {
		this.chkExcepcion = chkExcepcion;
	}
	public Boolean getChkActivo() {
		return chkActivo;
	}
	public void setChkActivo(Boolean chkActivo) {
		this.chkActivo = chkActivo;
	}
	public Boolean getChkCaduco() {
		return chkCaduco;
	}
	public void setChkCaduco(Boolean chkCaduco) {
		this.chkCaduco = chkCaduco;
	}
	public String getRbCondicion() {
		return rbCondicion;
	}
	public void setRbCondicion(String rbCondicion) {
		this.rbCondicion = rbCondicion;
	}
	public String getStrCredito() {
		return strCredito;
	}
	public void setStrCredito(String strCredito) {
		this.strCredito = strCredito;
	}
	public Boolean getFormCreditoRendered() {
		return formCreditoRendered;
	}
	public void setFormCreditoRendered(Boolean formCreditoRendered) {
		this.formCreditoRendered = formCreditoRendered;
	}
	public Boolean getFormCondSocio() {
		return formCondSocio;
	}
	public void setFormCondSocio(Boolean formCondSocio) {
		this.formCondSocio = formCondSocio;
	}
	public Boolean getEnabDisabFechasCredito() {
		return enabDisabFechasCredito;
	}
	public void setEnabDisabFechasCredito(Boolean enabDisabFechasCredito) {
		this.enabDisabFechasCredito = enabDisabFechasCredito;
	}
	public BigDecimal getBdPorcTasa() {
		return bdPorcTasa;
	}
	public void setBdPorcTasa(BigDecimal bdPorcTasa) {
		this.bdPorcTasa = bdPorcTasa;
	}
	public Integer getIntMesesMax() {
		return intMesesMax;
	}
	public void setIntMesesMax(Integer intMesesMax) {
		this.intMesesMax = intMesesMax;
	}
	public BigDecimal getBdMontoMax() {
		return bdMontoMax;
	}
	public void setBdMontoMax(BigDecimal bdMontoMax) {
		this.bdMontoMax = bdMontoMax;
	}
	public String[] getLstTipoCondSocio() {
		return lstTipoCondSocio;
	}
	public void setLstTipoCondSocio(String[] lstTipoCondSocio) {
		this.lstTipoCondSocio = lstTipoCondSocio;
	}
	public String[] getLstCondicionCredito() {
		return lstCondicionCredito;
	}
	public void setLstCondicionCredito(String[] lstCondicionCredito) {
		this.lstCondicionCredito = lstCondicionCredito;
	}
	public String[] getLstFinalidadCredito() {
		return lstFinalidadCredito;
	}
	public void setLstFinalidadCredito(String[] lstFinalidadCredito) {
		this.lstFinalidadCredito = lstFinalidadCredito;
	}
	public String[] getLstRanMontoMin() {
		return lstRanMontoMin;
	}
	public void setLstRanMontoMin(String[] lstRanMontoMin) {
		this.lstRanMontoMin = lstRanMontoMin;
	}
	public String[] getLstRanMontoMax() {
		return lstRanMontoMax;
	}
	public void setLstRanMontoMax(String[] lstRanMontoMax) {
		this.lstRanMontoMax = lstRanMontoMax;
	}
	public String getRbFecIndeterm() {
		return rbFecIndeterm;
	}
	public void setRbFecIndeterm(String rbFecIndeterm) {
		this.rbFecIndeterm = rbFecIndeterm;
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
	public Boolean getFecFinCreditoRendered() {
		return fecFinCreditoRendered;
	}
	public void setFecFinCreditoRendered(Boolean fecFinCreditoRendered) {
		this.fecFinCreditoRendered = fecFinCreditoRendered;
	}
	public String getMsgTxtFechaIni() {
		return msgTxtFechaIni;
	}
	public void setMsgTxtFechaIni(String msgTxtFechaIni) {
		this.msgTxtFechaIni = msgTxtFechaIni;
	}
	public String getMsgTxtDescripcion() {
		return msgTxtDescripcion;
	}
	public void setMsgTxtDescripcion(String msgTxtDescripcion) {
		this.msgTxtDescripcion = msgTxtDescripcion;
	}
	public String getMsgTxtTipoCredito() {
		return msgTxtTipoCredito;
	}
	public void setMsgTxtTipoCredito(String msgTxtTipoCredito) {
		this.msgTxtTipoCredito = msgTxtTipoCredito;
	}
	public String getMsgTxtTipoPersona() {
		return msgTxtTipoPersona;
	}
	public void setMsgTxtTipoPersona(String msgTxtTipoPersona) {
		this.msgTxtTipoPersona = msgTxtTipoPersona;
	}
	public String getMsgTxtTipoSbs() {
		return msgTxtTipoSbs;
	}
	public void setMsgTxtTipoSbs(String msgTxtTipoSbs) {
		this.msgTxtTipoSbs = msgTxtTipoSbs;
	}
	public String getMsgTxtMoneda() {
		return msgTxtMoneda;
	}
	public void setMsgTxtMoneda(String msgTxtMoneda) {
		this.msgTxtMoneda = msgTxtMoneda;
	}
	public String getMsgTxtTipoCalcInteres() {
		return msgTxtTipoCalcInteres;
	}
	public void setMsgTxtTipoCalcInteres(String msgTxtTipoCalcInteres) {
		this.msgTxtTipoCalcInteres = msgTxtTipoCalcInteres;
	}
	public String getMsgTxtEstado() {
		return msgTxtEstado;
	}
	public void setMsgTxtEstado(String msgTxtEstado) {
		this.msgTxtEstado = msgTxtEstado;
	}
	public String getMsgTxtPorcTasa() {
		return msgTxtPorcTasa;
	}
	public void setMsgTxtPorcTasa(String msgTxtPorcTasa) {
		this.msgTxtPorcTasa = msgTxtPorcTasa;
	}
	public String getMsgTxtMesMaximo() {
		return msgTxtMesMaximo;
	}
	public void setMsgTxtMesMaximo(String msgTxtMesMaximo) {
		this.msgTxtMesMaximo = msgTxtMesMaximo;
	}
	public String getMsgTxtMontoMaximo() {
		return msgTxtMontoMaximo;
	}
	public void setMsgTxtMontoMaximo(String msgTxtMontoMaximo) {
		this.msgTxtMontoMaximo = msgTxtMontoMaximo;
	}
	public String getMsgTxtTipoCondSocio() {
		return msgTxtTipoCondSocio;
	}
	public void setMsgTxtTipoCondSocio(String msgTxtTipoCondSocio) {
		this.msgTxtTipoCondSocio = msgTxtTipoCondSocio;
	}
	public List<Tabla> getListaTipoCaptacion() {
		return listaTipoCaptacion;
	}
	public void setListaTipoCaptacion(List<Tabla> listaTipoCaptacion) {
		this.listaTipoCaptacion = listaTipoCaptacion;
	}
	public List<CreditoComp> getListaCreditoComp() {
		return listaCreditoComp;
	}
	public void setListaCreditoComp(List<CreditoComp> listaCreditoComp) {
		this.listaCreditoComp = listaCreditoComp;
	}
	public List<CondicionComp> getListaCondicionComp() {
		return listaCondicionComp;
	}
	public void setListaCondicionComp(List<CondicionComp> listaCondicionComp) {
		this.listaCondicionComp = listaCondicionComp;
	}
	public List<Tabla> getListaTipoCreditoSbs() {
		return listaTipoCreditoSbs;
	}
	public void setListaTipoCreditoSbs(List<Tabla> listaTipoCreditoSbs) {
		this.listaTipoCreditoSbs = listaTipoCreditoSbs;
	}
	public List<Tabla> getListaTipoCreditoEmpresa() {
		return listaTipoCreditoEmpresa;
	}
	public void setListaTipoCreditoEmpresa(List<Tabla> listaTipoCreditoEmpresa) {
		this.listaTipoCreditoEmpresa = listaTipoCreditoEmpresa;
	}

	/*public List<Tabla> getlistaCondicionSocioLaboral() {
		try {
				TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				this.listaCondicionSocioLaboral = facade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));

		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if(listaCondicionSocioLaboral!=null){
			log.info("listaCondicionSocioLaboral.size: "+listaCondicionSocioLaboral.size());
		}
		return listaCondicionSocioLaboral;
	}

	public void setListaCondicionSocioLaboral(List<Tabla> listaCondicionSocioLaboral) {
		this.listaCondicionSocioLaboral = listaCondicionSocioLaboral;
	}*/

	public List<Tabla> getListaTipoCaptacion2() {
		return listaTipoCaptacion2;
	}

	public void setListaTipoCaptacion2(List<Tabla> listaTipoCaptacion2) {
		this.listaTipoCaptacion2 = listaTipoCaptacion2;
	}

	public List<Tabla> getListaTipoRol() {
		try {
			if(listaTipoRol == null){
				TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				this.listaTipoRol = facade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOROL), "C");
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if(listaTipoRol!=null){
			log.info("listaTipoRol.size: "+listaTipoRol.size());
		}
		return listaTipoRol;
	}
	public List<Tabla> getListaCondicionSocioLaboral() {
		return listaCondicionSocioLaboral;
	}

	public void setListaCondicionSocioLaboral(List<Tabla> listaCondicionSocioLaboral) {
		this.listaCondicionSocioLaboral = listaCondicionSocioLaboral;
	}

	public void setListaTipoRol(List<Tabla> listaTipoRol) {
		this.listaTipoRol = listaTipoRol;
	}
	public List<CreditoInteres> getListaCreditoInteres() {
		return listaCreditoInteres;
	}
	public void setListaCreditoInteres(List<CreditoInteres> listaCreditoInteres) {
		this.listaCreditoInteres = listaCreditoInteres;
	}
	public List<CreditoDescuento> getListaCreditoDescuento() {
		return listaCreditoDescuento;
	}
	public void setListaCreditoDescuento(
			List<CreditoDescuento> listaCreditoDescuento) {
		this.listaCreditoDescuento = listaCreditoDescuento;
	}
	public Boolean getBlnCredito() {
		return blnCredito;
	}
	public void setBlnCredito(Boolean blnCredito) {
		this.blnCredito = blnCredito;
	}
	public CreditoDescuentoController getCreditoDescuentoController() {
		return creditoDescuentoController;
	}
	public void setCreditoDescuentoController(
			CreditoDescuentoController creditoDescuentoController) {
		this.creditoDescuentoController = creditoDescuentoController;
	}
	public CreditoExcepcionController getCreditoExcepcionController() {
		return creditoExcepcionController;
	}
	public void setCreditoExcepcionController(
			CreditoExcepcionController creditoExcepcionController) {
		this.creditoExcepcionController = creditoExcepcionController;
	}
	public CreditoGarantiaPersonalController getCreditoGarantiaPersonalController() {
		return creditoGarantiaPersonalController;
	}
	public void setCreditoGarantiaPersonalController(
			CreditoGarantiaPersonalController creditoGarantiaPersonalController) {
		this.creditoGarantiaPersonalController = creditoGarantiaPersonalController;
	}
	public CreditoGarantiaRealController getCreditoGarantiaRealController() {
		return creditoGarantiaRealController;
	}
	public void setCreditoGarantiaRealController(
			CreditoGarantiaRealController creditoGarantiaRealController) {
		this.creditoGarantiaRealController = creditoGarantiaRealController;
	}
	public CreditoGarantiaAutoliquidableController getCreditoGarantiaAutoliquidableController() {
		return creditoGarantiaAutoliquidableController;
	}
	public void setCreditoGarantiaAutoliquidableController(
			CreditoGarantiaAutoliquidableController creditoGarantiaAutoliquidableController) {
		this.creditoGarantiaAutoliquidableController = creditoGarantiaAutoliquidableController;
	}
	public CreditoGarantiaRapidaRealizacionController getCreditoGarantiaRapidaRealizacionController() {
		return creditoGarantiaRapidaRealizacionController;
	}
	public void setCreditoGarantiaRapidaRealizacionController(
			CreditoGarantiaRapidaRealizacionController creditoGarantiaRapidaRealizacionController) {
		this.creditoGarantiaRapidaRealizacionController = creditoGarantiaRapidaRealizacionController;
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
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
	
	public Integer getIntCondicionSocioInteres() {
		return intCondicionSocioInteres;
	}

	public void setIntCondicionSocioInteres(Integer intCondicionSocioInteres) {
		this.intCondicionSocioInteres = intCondicionSocioInteres;
	}


	// Métodos a implementar
	/**************************************************************/
	/*  Nombre :  habilitarGrabarAportaciones()     		      		*/
	/*                                                    	 			*/
	/*  Parametros. :  Ninguno					           	 			*/
	/*  Objetivo: Habilitar el Formulario para el llenado del mimso 	*/
	/*  Retorno : El formulario habilitado para su respectivo llenado 	*/
	/**************************************************************/
	
	public void habilitarGrabarCredito(ActionEvent event) {
		setFormCreditoRendered(true);
		setFormCondSocio(false);
		limpiarFormCredito();
		reloadCboTipoCaptacion(Constante.CONFIGURACION_CREDITO);
		strCredito = Constante.MANTENIMIENTO_GRABAR;
	}
	
	public void reloadCboTipoCaptacion(String strIdTipoCaptacion) {
		log.info("-----------------------Debugging CreditoController.reloadCboTipoCaptacion-----------------------------");
		TablaFacadeRemote remote;
		try {
			remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			try {
				listaTipoCaptacion = remote.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOCUENTA), strIdTipoCaptacion);
				listaTipoCaptacion2 = remote.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOCUENTA), strIdTipoCaptacion);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarCredito()     					      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarFormCredito(){
		creditoDescuentoController = (CreditoDescuentoController)getSessionBean("creditoDescuentoController");
		creditoGarantiaPersonalController = (CreditoGarantiaPersonalController)getSessionBean("creditoGarantiaPersonalController");
		creditoGarantiaRealController = (CreditoGarantiaRealController)getSessionBean("creditoGarantiaRealController");
		creditoGarantiaRapidaRealizacionController = (CreditoGarantiaRapidaRealizacionController)getSessionBean("creditoGarantiaRapidaRealizacionController");
		creditoGarantiaAutoliquidableController = (CreditoGarantiaAutoliquidableController)getSessionBean("creditoGarantiaAutoliquidableController");
		creditoExcepcionController = (CreditoExcepcionController)getSessionBean("creditoExcepcionController");
		Credito cred = new Credito();
		cred.setId(new CreditoId());
		cred.setCondicionCredito(new CondicionCredito());
		cred.getCondicionCredito().setId(new CondicionCreditoId());
		setBeanCredito(cred);
		setDaFechaIni(null);
		setDaFechaFin(null);
		setRbCondicion("");
		setRbFecIndeterm("");
		
		setMsgTxtDescripcion("");
		setMsgTxtEstado("");
		setMsgTxtFechaIni("");
		setMsgTxtMesMaximo("");
		setMsgTxtMoneda("");
		setMsgTxtMontoMaximo("");
		setMsgTxtTipoCondSocio("");
		setMsgTxtPorcTasa("");
		setMsgTxtTipoCalcInteres("");
		setMsgTxtTipoCredito("");
		setMsgTxtTipoPersona("");
		setMsgTxtTipoSbs("");
		
		setBdMontoMax(null);
		setIntMesesMax(null);
		setBdPorcTasa(null);
		setIntCondicionSocioInteres(null);
		if(listaCondicionComp!=null){
			listaCondicionComp.clear();
		}
		if(lstTipoCondSocio!=null){
			setLstTipoCondSocio(null);
		}
		if(lstCondicionCredito!=null){
			setLstCondicionCredito(null);
		}
		if(lstRanMontoMin!=null){
			setLstRanMontoMin(null);
		}
		if(lstRanMontoMax!=null){
			setLstRanMontoMax(null);
		}
		if(listaCreditoInteres!=null){
			listaCreditoInteres.clear();
		}
		if(lstFinalidadCredito!=null){
			setLstFinalidadCredito(null);
		}
		if(creditoDescuentoController.getListaDescuentoCredito()!=null){
			creditoDescuentoController.getListaDescuentoCredito().clear();
		}
		if(creditoDescuentoController.getLstDsctoCaptacion()!=null){
			creditoDescuentoController.setLstDsctoCaptacion(null);
		}
		if(creditoGarantiaPersonalController.getListaGarantiaPersonal()!=null){
			creditoGarantiaPersonalController.getListaGarantiaPersonal().clear();
		}
		if(creditoGarantiaRealController.getListaGarantiaReal()!=null){
			creditoGarantiaRealController.getListaGarantiaReal().clear();
		}
		if(creditoGarantiaRapidaRealizacionController.getListaGarantiaRapidaRealizacion()!=null){
			creditoGarantiaRapidaRealizacionController.getListaGarantiaRapidaRealizacion().clear();
		}
		if(creditoGarantiaAutoliquidableController.getListaGarantiaAutoliquidable()!=null){
			creditoGarantiaAutoliquidableController.getListaGarantiaAutoliquidable().clear();
		}
		if(creditoExcepcionController.getListaExcepcionCredito()!=null){
			creditoExcepcionController.getListaExcepcionCredito().clear();
		}
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  cancelarGrabarCredito() 		          			*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*                                                    	 		*/
	/*  Objetivo: Cancelar la nueva Hoja de Planeamiento     		*/
	/*            Poblacional.                               	  	*/
	/*  Retorno : Se oculta el Formulario de Aportaciones		 	*/
	/**************************************************************/
	public void cancelarGrabarCredito(ActionEvent event) {
		setFormCreditoRendered(false);
		limpiarFormCredito();
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarCredito()        							*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**************************************************************/
	public void listarCredito(ActionEvent event) {
		log.info("-----------------------Debugging CreditoController.listarCredito-----------------------------");
		CreditoFacadeLocal facade = null;
		try {
			facade = (CreditoFacadeLocal)EJBFactory.getLocal(CreditoFacadeLocal.class);
			Credito o = new Credito();
			o.setId(new CreditoId());
			o.setCondicionCredito(new CondicionCredito());
			o.getCondicionCredito().setId(new CondicionCreditoId());
			o.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
			//o.getId().setIntParaTipoCreditoCod(Constante.CAPTACION_APORTACIONES);
			log.info("intIdEstadoSolicCredito: " + intIdEstadoSolicCredito);
			if(intIdEstadoSolicCredito!=null && intIdEstadoSolicCredito!=0)
				o.setIntParaEstadoSolicitudCod(intIdEstadoSolicCredito);
			log.info("intIdEstadoCredito: " + intIdEstadoCredito);
			if(intIdEstadoCredito!=null && intIdEstadoCredito!=0)
				o.setIntParaEstadoCod(intIdEstadoCredito);
			if(intTipoCredito!=null && intTipoCredito!=0)
				o.getId().setIntParaTipoCreditoCod(intTipoCredito);
			o.setStrDescripcion(strNombreCredito);
			if(intTipoCondicionLaboral!=null && intTipoCondicionLaboral!=0)
				o.setIntParaCondicionLaboralCod(intTipoCondicionLaboral);
			if(intCondicionSocio!=null && intCondicionSocio!=0)
				o.getCondicionCredito().getId().setIntParaCondicionSocioCod(intCondicionSocio);
			//if(intTipoConfiguracion!=null && intTipoConfiguracion!=0)
				//o.setintt(intTipoConfiguracion);
			if(intTipoFecha!=null && intTipoFecha!=0)
				o.setIntTipoFecha(intTipoFecha);
			
			String strFechaIni = (getDaFecIni()!=null)?Constante.sdf.format(getDaFecIni()):null;
			String strFechaFin = (getDaFecFin()!=null)?Constante.sdf.format(getDaFecFin()):null;
			o.setStrDtFechaIni(strFechaIni);
			o.setStrDtFechaFin(strFechaFin);
			o.setDtFechaIni(daFecIni);
			o.setDtFechaFin(daFecFin);
			if(intIdTipoPersona!=null && intIdTipoPersona!=0)
				o.setIntParaTipoPersonaCod(intIdTipoPersona);
			o.setIntGarantia(getChkGarantia()==true?1:0);
			o.setIntDescuento(getChkDescuento() ==true?1:0);
			o.setIntExcepcion(getChkExcepcion()==true?1:0);
			o.setIntActivo(getChkActivo()==true?1:0);
			o.setIntCaduco(getChkCaduco()==true?1:0);
			
			listaCreditoComp = facade.getListaCreditoCompDeBusquedaCredito(o);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
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
		setEnabDisabFechasCredito(getChkFecha()!=true);
		setDaFecIni(getChkFecha()!=true?null:getDaFecIni());
		setDaFecFin(getChkFecha()!=true?null:getDaFecFin());
		
		if(getRbFecIndeterm()!=null){
			setFecFinCreditoRendered(getRbFecIndeterm().equals("1"));
			setDaFechaFin(null);
		}
	}
	
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
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmCreditoModalPanel:hiddenIdCodigo");
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
	
	public void reloadCboTipoCreditoSbs(ValueChangeEvent event) throws EJBFactoryException, BusinessException {
		log.info("-----------------------Debugging CreditoController.reloadCboTipoCreditoSbs()-----------------------------");
		if(event.getNewValue().toString().equals("0")){
			if(listaTipoCreditoSbs!=null){
				listaTipoCreditoSbs.clear();
			}
			return;
		}
		List<Tabla> lista = null;
		if(listaTipoCreditoSbs!=null){
			listaTipoCreditoSbs.clear();
		}
		creditoGarantiaPersonalController = (CreditoGarantiaPersonalController)getSessionBean("creditoGarantiaPersonalController");
		creditoGarantiaRealController = (CreditoGarantiaRealController)getSessionBean("creditoGarantiaRealController");
		creditoGarantiaAutoliquidableController = (CreditoGarantiaAutoliquidableController)getSessionBean("creditoGarantiaAutoliquidableController");
		
		Integer intIdTipoPersona = Integer.parseInt(""+event.getNewValue());
		log.info("intIdTipoPersona = "+intIdTipoPersona);
		TablaFacadeRemote remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
		lista = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPO_CREDITOSBS), intIdTipoPersona);
		for(int i=0; i<lista.size(); i++){
			Tabla tabla = lista.get(i);
			listaTipoCreditoSbs.add(tabla);
		}
		lista = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPO_CREDITOSBS), Constante.PARAM_T_TIPOPERSONA_AMBOS);
		for(int i=0; i<lista.size(); i++){
			Tabla tabla = lista.get(i);
			listaTipoCreditoSbs.add(tabla);
		}
		
	    creditoGarantiaPersonalController.setListaNaturalezaGarantia(remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_NATURALEZA_GARANTIA), intIdTipoPersona));
	    creditoGarantiaRealController.setListaNaturalezaGarantia(remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_NATURALEZA_GARANTIA), intIdTipoPersona));
	    creditoGarantiaAutoliquidableController.setListaNaturalezaGarantia(remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_NATURALEZA_GARANTIA), intIdTipoPersona));
	}
	
	public void reloadCboTipoCreditoEmpresa(ValueChangeEvent event) throws EJBFactoryException, BusinessException{
		Integer intIdTipoCredito = Integer.parseInt(""+event.getNewValue());
		log.info("intIdTipoCredito = "+intIdTipoCredito);
		TablaFacadeRemote remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
	    listaTipoCreditoEmpresa = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), intIdTipoCredito);
	}
	
	private Boolean isValidoAportacion(Credito beanCredito){
		Boolean validCaptacion = true;
		if (beanCredito.getStrDescripcion().equals("")) {
			setMsgTxtDescripcion("* El campo Nombre del Crédito debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtDescripcion("");
		}
		if (beanCredito.getId().getIntParaTipoCreditoCod()==0) {
			setMsgTxtTipoCredito("* El campo Tipo de Crédito debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtTipoCredito("");
		}
		Date daFecIni = getDaFechaIni();
		String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
		beanCredito.setStrDtFechaIni(daFechaIni);
		Date daFecFin = getDaFechaFin();
		String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
		//beanCaptacion.setStrDtFechaFin(daFechaFin);
		if (beanCredito.getStrDtFechaIni()==null) {
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
		
		if (beanCredito.getIntParaEstadoCod()== 0) {
			setMsgTxtEstado("* El campo Estado del Aporte debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtEstado("");
		}
		if (beanCredito.getIntParaTipoPersonaCod()== 0) {
			setMsgTxtTipoPersona("* El campo Tipo de Persona debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtTipoPersona("");
		}
		if (beanCredito.getIntParaTipoSbsCod()== 0) {
			setMsgTxtTipoSbs("* El campo Tipo de Crédito (SBS) debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtTipoSbs("");
		}
		if (beanCredito.getIntParaMonedaCod()== 0) {
			setMsgTxtMoneda("* El campo Tipo de Moneda debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtMoneda("");
		}
		if (beanCredito.getIntParaTipoCalculoInteresCod()== 0) {
			setMsgTxtTipoCalcInteres("* El campo Cálculo de Interés debe ser ingresado.");
			validCaptacion = false;
		} else {
			setMsgTxtTipoCalcInteres("");
		}
		if(lstTipoCondSocio!=null && lstTipoCondSocio.length>0){
			setMsgTxtTipoCondSocio("* Debe elegir por lo menos un tipo de Condición de Socio");
		}
		if(lstCondicionCredito!=null && lstCondicionCredito.length>0){
			setMsgTxtTipoCondSocio("* Debe elegir por lo menos un tipo de Condición de Socio");
		}
		
	    return validCaptacion;
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  grabarCredito()        							*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Guardar los datos ingresados en la ventan de 		*/
	/*            Creditos 						     	 			*/
	/*                         						     	 		*/
	/*  Retorno : Datos grabados correctamente en la tabla de 	 	*/
	/*            CSO_CONFCREDITOS					     	 		*/
	/**/
	/***********************************************************************************/
	public void grabarCredito(ActionEvent event){
		CreditoFacadeLocal facade = null;
		List<CondicionHabil> listaCondicionHabil = new ArrayList<CondicionHabil>();
		List<CondicionCredito> listaCondicionCredito = new ArrayList<CondicionCredito>();
		List<CreditoTopeCaptacion> listaRangoMontoMin = new ArrayList<CreditoTopeCaptacion>();
		List<CreditoTopeCaptacion> listaRangoMontoMax = new ArrayList<CreditoTopeCaptacion>();
		List<Finalidad> listaFinalidad = new ArrayList<Finalidad>();
		CondicionHabil condicionHabil = null;
		CondicionCredito condicionCredito = null;
		CreditoTopeCaptacion creditoTopeCaptacionMin = null;
		CreditoTopeCaptacion creditoTopeCaptacionMax = null;
		Finalidad finalidad = null;
	    if(isValidoAportacion(beanCredito) == false){
	    	log.info("Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.");
	    	return;
	    }
	    //beanCaptacion.setId(new CaptacionId());
		beanCredito.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);//beanSesion.getIntIdEmpresa()
		//beanCredito.getId().setIntParaTipoCreditoCod(Constante.CAPTACION_AES);
		beanCredito.setIntParaEstadoSolicitudCod(1);
	    
		Date daFecIni = getDaFechaIni();
		String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
		beanCredito.setStrDtFechaIni(daFechaIni);
		beanCredito.setDtFechaIni(daFecIni);
		
		Date daFecFin = getDaFechaFin();
		String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
		beanCredito.setStrDtFechaFin(daFechaFin);
		beanCredito.setDtFechaFin(daFecFin);
		
		beanCredito.setIntParaTipoCaptacionMaximoCod(Constante.PARAM_T_MAXIMO);
		beanCredito.setIntParaTipoCaptacionMinimoCod(Constante.PARAM_T_MINIMO);
		
		if(listaCondicionComp!=null){
			beanCredito.setListaCondicionComp(listaCondicionComp);
		}
		
		if(lstTipoCondSocio!=null && lstTipoCondSocio.length>0){
			for(int i=0;i<lstTipoCondSocio.length;i++){
				condicionHabil = new CondicionHabil();
				condicionHabil.setId(new CondicionHabilId());
				condicionHabil.getId().setIntParaTipoHabilCod(new Integer(lstTipoCondSocio[i]));
				listaCondicionHabil.add(condicionHabil);
			}
			beanCredito.setListaCondicionHabil(listaCondicionHabil);
		}
		
		if(lstCondicionCredito!=null && lstCondicionCredito.length>0){
			for(int i=0;i<lstCondicionCredito.length;i++){
				condicionCredito = new CondicionCredito();
				condicionCredito.setId(new CondicionCreditoId());
				condicionCredito.getId().setIntParaCondicionSocioCod(new Integer(lstCondicionCredito[i]));
				listaCondicionCredito.add(condicionCredito);
			}
			beanCredito.setListaCondicion(listaCondicionCredito);
		}
		
		if(lstRanMontoMin!=null && lstRanMontoMin.length>0){
			for(int i=0;i<lstRanMontoMin.length;i++){
				creditoTopeCaptacionMin = new CreditoTopeCaptacion();
				creditoTopeCaptacionMin.setId(new CreditoTopeCaptacionId());
				creditoTopeCaptacionMin.getId().setIntParaTipoMinMaxCod(Constante.PARAM_T_MINIMO);
				creditoTopeCaptacionMin.getId().setIntParaTipoCaptacion(new Integer(lstRanMontoMin[i]));
				listaRangoMontoMin.add(creditoTopeCaptacionMin);
			}
			beanCredito.setListaRangoMontoMin(listaRangoMontoMin);
		}
		/*
		for(int k=0; k<lstRanMontoMax.length ; k++){	
		}
		*/
		if(lstRanMontoMax!=null && lstRanMontoMax.length>0){
			for(int i=0;i<lstRanMontoMax.length;i++){
				creditoTopeCaptacionMax = new CreditoTopeCaptacion();
				creditoTopeCaptacionMax.setId(new CreditoTopeCaptacionId());
				creditoTopeCaptacionMax.getId().setIntParaTipoMinMaxCod(Constante.PARAM_T_MAXIMO);
				creditoTopeCaptacionMax.getId().setIntParaTipoCaptacion(new Integer(lstRanMontoMax[i]));
				listaRangoMontoMax.add(creditoTopeCaptacionMax);
			}
			beanCredito.setListaRangoMontoMax(listaRangoMontoMax);
		}
		
		if(listaCreditoInteres!=null && listaCreditoInteres.size()>0){
			beanCredito.setListaCreditoInteres(listaCreditoInteres);
		}
		
		if(lstFinalidadCredito!=null && lstFinalidadCredito.length>0){
			for(int i=0;i<lstFinalidadCredito.length;i++){
				finalidad = new Finalidad();
				finalidad.setId(new FinalidadId());
				finalidad.getId().setIntParaFinalidadCod(new Integer(lstFinalidadCredito[i]));
				listaFinalidad.add(finalidad);
			}
			beanCredito.setListaFinalidad(listaFinalidad);
		}
		
		try {
			facade = (CreditoFacadeLocal)EJBFactory.getLocal(CreditoFacadeLocal.class);
			facade.grabarCredito(beanCredito);
			limpiarFormCredito();
			listarCredito(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  irModificarCredito()        						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Agregar el Rango de Interés 						*/
	/*                         						     	 		*/
	/**/
	/***********************************************************************************/
	public void irModificarCredito(ActionEvent event) throws ParseException{
    	log.info("-----------------------Debugging CreditoController.irModificarCredito-----------------------------");
    	CreditoFacadeLocal creditoFacade = null;
    	String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmCreditoModalPanel:hiddenIdEmpresa");
		String strIdTipoCredito = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmCreditoModalPanel:hiddenIdTipoCredito");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmCreditoModalPanel:hiddenIdCodigo");
		log.info("strIdEmpresa 		 : "+strIdEmpresa);
		log.info("strIdTipoCaptacion : "+strIdTipoCredito);
		log.info("strIdCodigo 		 : "+strIdCodigo);
		
		CreditoId creditoId = new CreditoId();
		creditoId.setIntPersEmpresaPk(new Integer(strIdEmpresa));
		creditoId.setIntParaTipoCreditoCod(new Integer(strIdTipoCredito));
		creditoId.setIntItemCredito(new Integer(strIdCodigo));
		
		creditoDescuentoController = (CreditoDescuentoController)getSessionBean("creditoDescuentoController");
		creditoExcepcionController = (CreditoExcepcionController)getSessionBean("creditoExcepcionController");
		creditoGarantiaPersonalController = (CreditoGarantiaPersonalController)getSessionBean("creditoGarantiaPersonalController");
		creditoGarantiaRealController = (CreditoGarantiaRealController)getSessionBean("creditoGarantiaRealController");
		creditoGarantiaAutoliquidableController = (CreditoGarantiaAutoliquidableController)getSessionBean("creditoGarantiaAutoliquidableController");
		creditoGarantiaRapidaRealizacionController = (CreditoGarantiaRapidaRealizacionController)getSessionBean("creditoGarantiaRapidaRealizacionController");
    	try {
    		if(strIdCodigo != null && !strIdCodigo.trim().equals("")){
    			creditoFacade = (CreditoFacadeLocal)EJBFactory.getLocal(CreditoFacadeLocal.class);
				beanCredito   = creditoFacade.getCreditoPorIdCredito(creditoId);
				
				log.info("beanCaptacion.getIntParaEstadoSolicitudCod(): "+beanCredito.getIntParaEstadoSolicitudCod());
				
				String daFecIni = "" + (beanCredito.getStrDtFechaIni() == null ? "" : beanCredito.getStrDtFechaIni());
				Date fecIni = (daFecIni == null || daFecIni.equals("") ? null : Constante.sdf.parse(daFecIni));
				setDaFechaIni(fecIni);
				
				String daFecFin = "" + (beanCredito.getStrDtFechaFin() == null ? "" : beanCredito.getStrDtFechaFin());
				Date fecFin = (daFecFin == null || daFecFin.equals("") ? null : Constante.sdf.parse(daFecFin));
				setDaFechaFin(fecFin);
				
				setFecFinCreditoRendered(fecFin!=null);
				setRbFecIndeterm(fecFin!=null?"1":"2");
				
				//setRbCondicion(beanCredito.getListaCondicionComp().size()==beanCredito.getListaCondicion().size()?"1":"2");
				//log.info("beanCredito.getListaCondicionComp().size(): "+beanCredito.getListaCondicionComp().size());
				//listaCondicionComp = beanCredito.getListaCondicionComp();
				
				String[] listaTipoCondSocio = new String[beanCredito.getListaCondicionHabil().size()];
				for(int i=0;i<beanCredito.getListaCondicionHabil().size();i++){
					listaTipoCondSocio[i] = ""+ beanCredito.getListaCondicionHabil().get(i).getId().getIntParaTipoHabilCod();
				}
				lstTipoCondSocio = listaTipoCondSocio;
				
				String[] listaCondicionCredito = new String[beanCredito.getListaCondicion().size()];
				for(int i=0;i<beanCredito.getListaCondicion().size();i++){
					listaCondicionCredito[i] = ""+ beanCredito.getListaCondicion().get(i).getId().getIntParaCondicionSocioCod();
				}
				lstCondicionCredito = listaCondicionCredito;
				
				/*if(beanCredito.getListaRangoMontoMin().isEmpty()){
					
				}*/
				System.out.println("XXXXXXXXXXXXXXXXX"+beanCredito.getListaRangoMontoMin());
				String[] listaRangoMontoMin = new String[beanCredito.getListaRangoMontoMin().size()];
				for(int i=0;i<beanCredito.getListaRangoMontoMin().size();i++){
					listaRangoMontoMin[i] = ""+ beanCredito.getListaRangoMontoMin().get(i).getId().getIntParaTipoCaptacion();
				}
				lstRanMontoMin = listaRangoMontoMin;
				
				System.out.println("YYYYYYYYYYYYYYYYYY"+beanCredito.getListaRangoMontoMax());
				String[] listaRangoMontoMax = new String[beanCredito.getListaRangoMontoMax().size()];
				for(int i=0;i<beanCredito.getListaRangoMontoMax().size();i++){
					listaRangoMontoMax[i] = ""+ beanCredito.getListaRangoMontoMax().get(i).getId().getIntParaTipoCaptacion();
				}
				lstRanMontoMax= listaRangoMontoMax;
				
				listaCreditoInteres = beanCredito.getListaCreditoInteres();
				
				String[] listaFinalidad = new String[beanCredito.getListaFinalidad().size()];
				for(int i=0;i<beanCredito.getListaFinalidad().size();i++){
					listaFinalidad[i] = ""+ beanCredito.getListaFinalidad().get(i).getId().getIntParaFinalidadCod();
				}
				lstFinalidadCredito = listaFinalidad;
				
				if(beanCredito.getListaDescuento()!=null && beanCredito.getListaDescuento().size()>0){
					creditoDescuentoController.setListaDescuentoCredito(beanCredito.getListaDescuento());
				}
				if(beanCredito.getListaExcepcion()!=null && beanCredito.getListaExcepcion().size()>0){
					creditoExcepcionController.setListaExcepcionCredito(beanCredito.getListaExcepcion());
				}
				if(beanCredito.getListaGarantiaPersonal()!=null && beanCredito.getListaGarantiaPersonal().size()>0){
					creditoGarantiaPersonalController.setListaGarantiaPersonal(beanCredito.getListaGarantiaPersonal());
				}
				if(beanCredito.getListaGarantiaReal()!=null && beanCredito.getListaGarantiaReal().size()>0){
					creditoGarantiaRealController.setListaGarantiaReal(beanCredito.getListaGarantiaReal());
				}
				if(beanCredito.getListaGarantiaAutoliquidable()!=null && beanCredito.getListaGarantiaAutoliquidable().size()>0){
					creditoGarantiaAutoliquidableController.setListaGarantiaAutoliquidable(beanCredito.getListaGarantiaAutoliquidable());
				}
				if(beanCredito.getListaGarantiaRapidaRealizacion()!=null && beanCredito.getListaGarantiaRapidaRealizacion().size()>0){
					creditoGarantiaRapidaRealizacionController.setListaGarantiaRapidaRealizacion(beanCredito.getListaGarantiaRapidaRealizacion());
				}
				
				setFormCreditoRendered(true);
				//setFormCondSocio(listaCondicionComp!=null && listaCondicionComp.size()>0);
				setFormCondSocio(true);
				//strAportacion = Constante.MANTENIMIENTO_MODIFICAR;
				setStrCredito((beanCredito.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) ||
						beanCredito.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO))?
						Constante.MANTENIMIENTO_ELIMINAR:Constante.MANTENIMIENTO_MODIFICAR);
				reloadCboTipoCaptacion(Constante.CONFIGURACION_CREDITO);
				TablaFacadeRemote remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			    listaTipoCreditoSbs = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPO_CREDITOSBS), beanCredito.getIntParaTipoPersonaCod());
			    listaTipoCreditoEmpresa = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), beanCredito.getId().getIntParaTipoCreditoCod());
			    creditoGarantiaPersonalController.setListaNaturalezaGarantia(remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_NATURALEZA_GARANTIA), beanCredito.getIntParaTipoPersonaCod()));
			    creditoGarantiaRealController.setListaNaturalezaGarantia(remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_NATURALEZA_GARANTIA), beanCredito.getIntParaTipoPersonaCod()));
			    creditoGarantiaAutoliquidableController.setListaNaturalezaGarantia(remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_NATURALEZA_GARANTIA), beanCredito.getIntParaTipoPersonaCod()));
			    creditoGarantiaRapidaRealizacionController.setListaNaturalezaGarantia(remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_NATURALEZA_GARANTIA), beanCredito.getIntParaTipoPersonaCod()));
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
	/*  Nombre :  modificarCredito()        						*/
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
	
	public void modificarCredito(ActionEvent event){
		CreditoFacadeLocal facade = null;
		List<CondicionCredito> listaCondicionCredito = new ArrayList<CondicionCredito>();
    	List<CondicionHabil> listaTipoCondSocio = new ArrayList<CondicionHabil>();
    	List<CreditoTopeCaptacion> listaRangoMontoMin = new ArrayList<CreditoTopeCaptacion>();
    	List<CreditoTopeCaptacion> listaRangoMontoMax = new ArrayList<CreditoTopeCaptacion>();
    	List<Finalidad> listaFinalidad = new ArrayList<Finalidad>();
    	CreditoTopeCaptacion creditoTopeCaptacionMin = null;
    	CreditoTopeCaptacion creditoTopeCaptacionMax = null;
    	CondicionHabil condicionHabil = null;
    	CondicionCredito condicionCredito = null;
    	Finalidad finalidad = null;
    	
    	try {
    		facade = (CreditoFacadeLocal)EJBFactory.getLocal(CreditoFacadeLocal.class);
    		
	    	if(isValidoAportacion(beanCredito) == false){
	    		log.info("Datos de Captación no válidos. Se aborta el proceso de grabación de Aportación.");
	    		return;
	    	}
	    	
	    	Date daFecIni = getDaFechaIni();
			String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
			//beanCaptacion.setStrDtFechaIni(daFechaIni);
			beanCredito.setDtFechaIni(daFecIni);
			
			Date daFecFin = getDaFechaFin();
			String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
			//beanCaptacion.setStrDtFechaFin(daFechaFin);
			beanCredito.setDtFechaFin(daFecFin);
			
			if(listaCondicionComp!=null){
				beanCredito.setListaCondicionComp(listaCondicionComp);
			}
			
			if(lstTipoCondSocio!=null && lstTipoCondSocio.length>0){
				for(int i=0;i<lstTipoCondSocio.length;i++){
					condicionHabil = new CondicionHabil();
					condicionHabil.setId(new CondicionHabilId());
					condicionHabil.getId().setIntParaTipoHabilCod(new Integer(lstTipoCondSocio[i]));
					listaTipoCondSocio.add(condicionHabil);
				}
				beanCredito.setListaCondicionHabil(listaTipoCondSocio);
			}
			
			if(lstCondicionCredito!=null && lstCondicionCredito.length>0){
				for(int i=0;i<lstCondicionCredito.length;i++){
					condicionCredito = new CondicionCredito();
					condicionCredito.setId(new CondicionCreditoId());
					condicionCredito.getId().setIntParaCondicionSocioCod(new Integer(lstCondicionCredito[i]));
					listaCondicionCredito.add(condicionCredito);
				}
				beanCredito.setListaCondicion(listaCondicionCredito);
			}
			
			System.out.println("lstRanMontoMinlstRanMontoMin---> "+lstRanMontoMin);
			if(lstRanMontoMin!=null && lstRanMontoMin.length>0){
				for(int i=0;i<lstRanMontoMin.length;i++){
					creditoTopeCaptacionMin = new CreditoTopeCaptacion();
					creditoTopeCaptacionMin.setId(new CreditoTopeCaptacionId());
					creditoTopeCaptacionMin.getId().setIntParaTipoMinMaxCod(Constante.PARAM_T_MINIMO);
					creditoTopeCaptacionMin.getId().setIntParaTipoCaptacion(new Integer(lstRanMontoMin[i]));
					listaRangoMontoMin.add(creditoTopeCaptacionMin);
				}
				beanCredito.setListaRangoMontoMin(listaRangoMontoMin);
			}else{
				CreditoTopeCaptacionId pPK = new CreditoTopeCaptacionId();
				pPK.setIntItemCredito(beanCredito.getId().getIntItemCredito());
				pPK.setIntParaTipoCaptacion(0); // 1 3 5 10
				pPK.setIntParaTipoCreditoCod(beanCredito.getId().getIntParaTipoCreditoCod());
				pPK.setIntParaTipoMinMaxCod(Constante.PARAM_T_MINIMO);
				pPK.setIntPersEmpresaPk(beanCredito.getId().getIntPersEmpresaPk());
				
				facade.deleteCreditoTopeCaptacionPorPkPor(pPK);
				beanCredito.setListaRangoMontoMin(null);
			}
			
			System.out.println("lstRanMontoMaxlstRanMontoMaxlstRanMontoMax---> "+lstRanMontoMax);
			if(lstRanMontoMax!=null && lstRanMontoMax.length>0){
				for(int i=0;i<lstRanMontoMax.length;i++){
					creditoTopeCaptacionMax = new CreditoTopeCaptacion();
					creditoTopeCaptacionMax.setId(new CreditoTopeCaptacionId());
					creditoTopeCaptacionMax.getId().setIntParaTipoMinMaxCod(Constante.PARAM_T_MAXIMO);
					creditoTopeCaptacionMax.getId().setIntParaTipoCaptacion(new Integer(lstRanMontoMax[i]));
					listaRangoMontoMax.add(creditoTopeCaptacionMax);
				}
				beanCredito.setListaRangoMontoMax(listaRangoMontoMax);
			}else{
				CreditoTopeCaptacionId pPK = new CreditoTopeCaptacionId();
				pPK.setIntItemCredito(beanCredito.getId().getIntItemCredito());
				pPK.setIntParaTipoCaptacion(0); // 1 3 5 10
				pPK.setIntParaTipoCreditoCod(beanCredito.getId().getIntParaTipoCreditoCod());
				pPK.setIntParaTipoMinMaxCod(Constante.PARAM_T_MAXIMO);
				pPK.setIntPersEmpresaPk(beanCredito.getId().getIntPersEmpresaPk());
				
				facade.deleteCreditoTopeCaptacionPorPkPor(pPK);
				beanCredito.setListaRangoMontoMax(null);
			}
			
			if(listaCreditoInteres!=null){
				beanCredito.setListaCreditoInteres(listaCreditoInteres);
			}
			
			if(lstFinalidadCredito!=null && lstFinalidadCredito.length>0){
				for(int i=0;i<lstFinalidadCredito.length;i++){
					finalidad = new Finalidad();
					finalidad.setId(new FinalidadId());
					finalidad.getId().setIntParaFinalidadCod(new Integer(lstFinalidadCredito[i]));
					listaFinalidad.add(finalidad);
				}
				beanCredito.setListaFinalidad(listaFinalidad);
			}
	
				facade.modificarCredito(beanCredito);
				limpiarFormCredito();
				listarCredito(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
	
	public void eliminarCredito(ActionEvent event){
    	log.info("-----------------------Debugging FondoSepelioController.eliminarFondoSepelio-----------------------------");
    	String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmCreditoModalPanel:hiddenIdEmpresa");
		String strIdTipoCredito = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmCreditoModalPanel:hiddenIdTipoCredito");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmCreditoModalPanel:hiddenIdCodigo");
		log.info("strIdEmpresa 		 : "+strIdEmpresa);
		log.info("strIdTipoCaptacion : "+strIdTipoCredito);
		log.info("strIdCodigo 		 : "+strIdCodigo);
		CreditoFacadeLocal facade = null;
		Credito credito = null;
    	try {
    		credito = new Credito();
    		credito.setId(new CreditoId());
    		credito.getId().setIntPersEmpresaPk(new Integer(strIdEmpresa));
    		credito.getId().setIntParaTipoCreditoCod(new Integer(strIdTipoCredito));
    		credito.getId().setIntItemCredito(new Integer(strIdCodigo));
			facade = (CreditoFacadeLocal)EJBFactory.getLocal(CreditoFacadeLocal.class);
			facade.eliminarCredito(credito.getId());
			limpiarFormCredito();
			listarCredito(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  addRangoInteres()        							*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Agregar el Rango de Interés 						*/
	/*                         						     	 		*/
	/**/
	/***********************************************************************************/
	public void addRangoInteres(ActionEvent event){
		log.info("--------------FondoSepelioController.addReqVinculo-------------");
		CreditoInteres creditoInteres = new CreditoInteres();
		creditoInteres.setId(new CreditoInteresId());
		Boolean bValidar = true;
		
		if(getBdPorcTasa()!=null && getBdPorcTasa().compareTo(BigDecimal.ZERO)==0){
			setMsgTxtPorcTasa("* Debe ingresar la Tasa");
			bValidar = false;
		}else{
			setMsgTxtPorcTasa("");
		}
		if(getIntMesesMax()!=null && getIntMesesMax()==0){
			setMsgTxtMesMaximo("* Debe ingresar el Número de meses máximo");
			bValidar = false;
		}else{
			setMsgTxtMesMaximo("");
		}
		if(getBdMontoMax()!=null && getBdMontoMax().compareTo(BigDecimal.ZERO)==0){
			setMsgTxtMontoMaximo("* Debe ingresar el Monto máximo");
			bValidar = false;
		}else{
			setMsgTxtMontoMaximo("");
		}
		
		if(bValidar == true){
			if(listaCreditoInteres==null) listaCreditoInteres = new ArrayList<CreditoInteres>();
			creditoInteres.setBdTasaInteres(getBdPorcTasa());
			creditoInteres.setIntMesMaximo(getIntMesesMax());
			creditoInteres.setBdMontoMaximo(getBdMontoMax());
			creditoInteres.setIntParaTipoSocio(getIntCondicionSocioInteres());
			listaCreditoInteres.add(creditoInteres);
		}
	}
	
	public void removeRangoInteres(ActionEvent event){
		log.info("-------------------------------------CreditoController.removeRangoInteres-------------------------------------");
		String rowKey = getRequestParameter("rowKeyRangoInteres");
		CreditoInteres creditoInteresTmp = null;
		if(listaCreditoInteres!=null){
			for(int i=0; i<listaCreditoInteres.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					CreditoInteres creditoInteres = listaCreditoInteres.get(i);
					if(creditoInteres.getId()!=null && creditoInteres.getId().getIntItemInteres()!=null){
						creditoInteresTmp = listaCreditoInteres.get(i);
						creditoInteresTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaCreditoInteres.remove(i);
					break;
				}
			}
			if(creditoInteresTmp!=null){
				listaCreditoInteres.add(creditoInteresTmp);
			}
		}
	}
	/*public void removeRangoInteres(ActionEvent event){
		log.info("------------------------CreditoController.removeRangoInteres----------------------------");
		List<CreditoInteres> arrayCreditoInteres = new ArrayList();
	    for(int i=0; i<getListaCreditoInteres().size(); i++){
	    	CreditoInteres creditoInteres = new CreditoInteres();
	    	creditoInteres = (CreditoInteres) getListaCreditoInteres().get(i);
	    	if(creditoInteres.getChkCreditoInteres() == false){
	    		arrayCreditoInteres.add(creditoInteres);
	    	}
	    }
	    listaCreditoInteres.clear();
	    setListaCreditoInteres(arrayCreditoInteres);
	}*/
}