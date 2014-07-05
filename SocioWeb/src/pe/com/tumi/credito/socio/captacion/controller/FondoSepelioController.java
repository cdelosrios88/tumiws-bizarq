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

import com.ibm.ObjectQuery.crud.util.Array;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.CondicionComp;
import pe.com.tumi.credito.socio.captacion.domain.CondicionId;
import pe.com.tumi.credito.socio.captacion.domain.Requisito;
import pe.com.tumi.credito.socio.captacion.domain.RequisitoId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeLocal;
import pe.com.tumi.credito.socio.convenio.domain.Competencia;
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
/* y validaciones de Fondo de Sepelio */
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 27/03/2012 */
/* ********************************************************************* */

/**
 * @author Tumi
 *
 */
public class FondoSepelioController {
	protected  static Logger 	log 			= Logger.getLogger(FondoSepelioController.class);
	private int 				rows = 5;
	private Captacion			beanCaptacion = new Captacion();
	private Condicion			beanCondicion = new Condicion();
	//private BeanSesion 		beanSesion = new BeanSesion();
	//Variables generales
	private Boolean 			formFondoSepelioRendered = false;
	private Integer				intIdEstadoAportacion;
	private String				strNombreFondoSepelio;
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
	private Boolean				chkLimEdad;
	private Integer				intTipoFecha;
	
	//Variables de activación y desactivación de controles
	private Boolean				chkNombFondoSepelio;
	private Boolean				enabDisabNombFondoSepelio = false;
	private Boolean				enabDisabCondAporte = true;
	private Boolean				chkFechas;
	private Boolean				enabDisabFechasAport = true;
	private String				rbFecIndeterm;
	private Boolean				fecFinAportacionRendered = true;
	private Date				daFechaIni;
	private Date				daFechaFin;
	private Boolean				chkLimiteEdad;
	private Boolean				enabDisabLimiteEdad = true;
	private String				rbTasaInteres;
	private Integer				intTipoBeneficiario;
	private Integer				intRangoCuota;
	private Integer				intCuotaCancelada;
	private BigDecimal			bdBeneficio;
	private BigDecimal			bdGastoAdm;
	private Boolean				enabDisabValImporte = true;
	private Boolean				enabDisabValPorcentaje = true;
	
	private Boolean				chkProvision;
	private Boolean				enabDisabProvision = true;
	private Boolean				chkExtProvision;
	private Boolean				enabDisabExtProvision = true;
	private Boolean				chkRegularicCuota;
	private Boolean				enabDisabRegularicCuota = true;
	private Boolean				chkCeseBeneficio;
	private Boolean				enabDisabCeseBenef = true;
	private Boolean				chkSolicitudBeneficio;
	private Boolean				enabDisabSolicitudBenef = true;
	private Boolean				chkAprobacBeneficio;
	private Boolean				enabDisabAprobacBenef = true;
	private Boolean				chkAnulRechazoBeneficio;
	private Boolean				enabDisabAnulRechazoBenef = true;
	private Boolean				chkGiroBeneficio;
	private Boolean				enabDisabGiroBenef = true;
	private Boolean				chkCancelacion;
	private Boolean				enabDisabCancelacion = true;
	
	private Boolean				chkCeseBeneficioMeses;
	private Boolean				enabDisabCeseBenefMeses = true;
	private Boolean				enabDisabEscala = true;
	
	private Boolean				chkTiempoPresentSustento;
	private Boolean				enabDisabTiempoPresentSust = true;
	private Boolean				chkEscala;
	
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
	private String				msgTxtTipoBeneficiario;
	
	private String 				strAportacion;
	//
	private List<CaptacionComp>	listaCaptacionComp;
	private List<CondicionComp>	listaCondicionComp;
	private List<Requisito>		listaRequisitos = new ArrayList<Requisito>();
	
	private Boolean				blnFondoSepelio;
	private Boolean				blnFondoRetiro;
	private Boolean				blnAes;
	
	// CGD - 06.12.2013
	private List<Tabla> listaTipoBeneficiario = new ArrayList<Tabla>();
	

	public FondoSepelioController(){
		inicio(null);
	}
	
	public void inicio(ActionEvent event){
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		Usuario usuario = null;
		Integer MENU_FONDOSEPELIO = 162;
		Integer MENU_FONDORETIRO = 163;
		Integer MENU_AES = 164;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			id = new PermisoPerfilId();
			id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
			id.setIntIdTransaccion(MENU_FONDOSEPELIO);
			id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
			PermisoFacadeRemote localPermiso = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnFondoSepelio = (permiso == null)?true:false;
			id.setIntIdTransaccion(MENU_FONDORETIRO);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnFondoRetiro = (permiso == null)?true:false;
			id.setIntIdTransaccion(MENU_AES);
			permiso = localPermiso.getPermisoPerfilPorPk(id);
			blnAes = (permiso == null)?true:false;
			
			TablaFacadeRemote remote;
			remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTipoBeneficiario = remote.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOVINCULO), "A");

		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}
	}
	
	//Getters y Setters
	
	public void setListaTipoBeneficiario(List<Tabla> listaTipoBeneficiario) {
		this.listaTipoBeneficiario = listaTipoBeneficiario;
	}

	public List<Tabla> getListaTipoBeneficiario() {
		return listaTipoBeneficiario;
	}

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
	public Condicion getBeanCondicion() {
		return beanCondicion;
	}
	public void setBeanCondicion(Condicion beanCondicion) {
		this.beanCondicion = beanCondicion;
	}
	public Boolean getFormFondoSepelioRendered() {
		return formFondoSepelioRendered;
	}
	public void setFormFondoSepelioRendered(Boolean formFondoSepelioRendered) {
		this.formFondoSepelioRendered = formFondoSepelioRendered;
	}
	public Integer getIntIdEstadoAportacion() {
		return intIdEstadoAportacion;
	}
	public void setIntIdEstadoAportacion(Integer intIdEstadoAportacion) {
		this.intIdEstadoAportacion = intIdEstadoAportacion;
	}
	public String getStrNombreFondoSepelio() {
		return strNombreFondoSepelio;
	}
	public void setStrNombreFondoSepelio(String strNombreFondoSepelio) {
		this.strNombreFondoSepelio = strNombreFondoSepelio;
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
	public Boolean getChkNombFondoSepelio() {
		return chkNombFondoSepelio;
	}
	public void setChkNombFondoSepelio(Boolean chkNombFondoSepelio) {
		this.chkNombFondoSepelio = chkNombFondoSepelio;
	}
	public Boolean getEnabDisabNombFondoSepelio() {
		return enabDisabNombFondoSepelio;
	}
	public void setEnabDisabNombFondoSepelio(Boolean enabDisabNombFondoSepelio) {
		this.enabDisabNombFondoSepelio = enabDisabNombFondoSepelio;
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
	public Integer getIntTipoBeneficiario() {
		return intTipoBeneficiario;
	}
	public void setIntTipoBeneficiario(Integer intTipoBeneficiario) {
		this.intTipoBeneficiario = intTipoBeneficiario;
	}
	public Integer getIntRangoCuota() {
		return intRangoCuota;
	}
	public void setIntRangoCuota(Integer intRangoCuota) {
		this.intRangoCuota = intRangoCuota;
	}
	public Integer getIntCuotaCancelada() {
		return intCuotaCancelada;
	}
	public void setIntCuotaCancelada(Integer intCuotaCancelada) {
		this.intCuotaCancelada = intCuotaCancelada;
	}
	public BigDecimal getBdBeneficio() {
		return bdBeneficio;
	}
	public void setBdBeneficio(BigDecimal bdBeneficio) {
		this.bdBeneficio = bdBeneficio;
	}
	public BigDecimal getBdGastoAdm() {
		return bdGastoAdm;
	}
	public void setBdGastoAdm(BigDecimal bdGastoAdm) {
		this.bdGastoAdm = bdGastoAdm;
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
	public Boolean getChkRegularicCuota() {
		return chkRegularicCuota;
	}
	public void setChkRegularicCuota(Boolean chkRegularicCuota) {
		this.chkRegularicCuota = chkRegularicCuota;
	}
	public Boolean getEnabDisabRegularicCuota() {
		return enabDisabRegularicCuota;
	}
	public void setEnabDisabRegularicCuota(Boolean enabDisabRegularicCuota) {
		this.enabDisabRegularicCuota = enabDisabRegularicCuota;
	}
	public Boolean getChkCeseBeneficio() {
		return chkCeseBeneficio;
	}
	public void setChkCeseBeneficio(Boolean chkCeseBeneficio) {
		this.chkCeseBeneficio = chkCeseBeneficio;
	}
	public Boolean getEnabDisabCeseBenef() {
		return enabDisabCeseBenef;
	}
	public void setEnabDisabCeseBenef(Boolean enabDisabCeseBenef) {
		this.enabDisabCeseBenef = enabDisabCeseBenef;
	}
	public Boolean getChkSolicitudBeneficio() {
		return chkSolicitudBeneficio;
	}
	public void setChkSolicitudBeneficio(Boolean chkSolicitudBeneficio) {
		this.chkSolicitudBeneficio = chkSolicitudBeneficio;
	}
	public Boolean getEnabDisabSolicitudBenef() {
		return enabDisabSolicitudBenef;
	}
	public void setEnabDisabSolicitudBenef(Boolean enabDisabSolicitudBenef) {
		this.enabDisabSolicitudBenef = enabDisabSolicitudBenef;
	}
	public Boolean getChkAprobacBeneficio() {
		return chkAprobacBeneficio;
	}
	public void setChkAprobacBeneficio(Boolean chkAprobacBeneficio) {
		this.chkAprobacBeneficio = chkAprobacBeneficio;
	}
	public Boolean getEnabDisabAprobacBenef() {
		return enabDisabAprobacBenef;
	}
	public void setEnabDisabAprobacBenef(Boolean enabDisabAprobacBenef) {
		this.enabDisabAprobacBenef = enabDisabAprobacBenef;
	}
	public Boolean getChkAnulRechazoBeneficio() {
		return chkAnulRechazoBeneficio;
	}
	public void setChkAnulRechazoBeneficio(Boolean chkAnulRechazoBeneficio) {
		this.chkAnulRechazoBeneficio = chkAnulRechazoBeneficio;
	}
	public Boolean getEnabDisabAnulRechazoBenef() {
		return enabDisabAnulRechazoBenef;
	}
	public void setEnabDisabAnulRechazoBenef(Boolean enabDisabAnulRechazoBenef) {
		this.enabDisabAnulRechazoBenef = enabDisabAnulRechazoBenef;
	}
	public Boolean getChkGiroBeneficio() {
		return chkGiroBeneficio;
	}
	public void setChkGiroBeneficio(Boolean chkGiroBeneficio) {
		this.chkGiroBeneficio = chkGiroBeneficio;
	}
	public Boolean getEnabDisabGiroBenef() {
		return enabDisabGiroBenef;
	}
	public void setEnabDisabGiroBenef(Boolean enabDisabGiroBenef) {
		this.enabDisabGiroBenef = enabDisabGiroBenef;
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
	public Boolean getChkCeseBeneficioMeses() {
		return chkCeseBeneficioMeses;
	}
	public void setChkCeseBeneficioMeses(Boolean chkCeseBeneficioMeses) {
		this.chkCeseBeneficioMeses = chkCeseBeneficioMeses;
	}
	public Boolean getEnabDisabCeseBenefMeses() {
		return enabDisabCeseBenefMeses;
	}
	public void setEnabDisabCeseBenefMeses(Boolean enabDisabCeseBenefMeses) {
		this.enabDisabCeseBenefMeses = enabDisabCeseBenefMeses;
	}
	public Boolean getEnabDisabEscala() {
		return enabDisabEscala;
	}
	public void setEnabDisabEscala(Boolean enabDisabEscala) {
		this.enabDisabEscala = enabDisabEscala;
	}
	public Boolean getChkTiempoPresentSustento() {
		return chkTiempoPresentSustento;
	}
	public void setChkTiempoPresentSustento(Boolean chkTiempoPresentSustento) {
		this.chkTiempoPresentSustento = chkTiempoPresentSustento;
	}
	public Boolean getEnabDisabTiempoPresentSust() {
		return enabDisabTiempoPresentSust;
	}
	public void setEnabDisabTiempoPresentSust(Boolean enabDisabTiempoPresentSust) {
		this.enabDisabTiempoPresentSust = enabDisabTiempoPresentSust;
	}
	public Boolean getChkEscala() {
		return chkEscala;
	}
	public void setChkEscala(Boolean chkEscala) {
		this.chkEscala = chkEscala;
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
	public String getMsgTxtTipoBeneficiario() {
		return msgTxtTipoBeneficiario;
	}
	public void setMsgTxtTipoBeneficiario(String msgTxtTipoBeneficiario) {
		this.msgTxtTipoBeneficiario = msgTxtTipoBeneficiario;
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
	public List<Requisito> getListaRequisitos() {
		return listaRequisitos;
	}
	public void setListaRequisitos(List<Requisito> listaRequisitos) {
		this.listaRequisitos = listaRequisitos;
	}
	public Boolean getBlnFondoSepelio() {
		return blnFondoSepelio;
	}
	public void setBlnFondoSepelio(Boolean blnFondoSepelio) {
		this.blnFondoSepelio = blnFondoSepelio;
	}
	public Boolean getBlnFondoRetiro() {
		return blnFondoRetiro;
	}
	public void setBlnFondoRetiro(Boolean blnFondoRetiro) {
		this.blnFondoRetiro = blnFondoRetiro;
	}
	public Boolean getBlnAes() {
		return blnAes;
	}
	public void setBlnAes(Boolean blnAes) {
		this.blnAes = blnAes;
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	// Métodos a implementar
	/**************************************************************/
	/*  Nombre :  habilitarGrabarFondoSepelio()     		      		*/
	/*                                                    	 		*/
	/*  Parametros. :  Ninguno					           	 		*/
	/*  Objetivo: Habilitar el Formulario para el llenado del mimso */
	/*  Retorno : El formulario habilitado para su respectivo llenado */
	/**************************************************************/
	public void habilitarGrabarFondoSepelio(ActionEvent event) {
		setFormFondoSepelioRendered(true);
		limpiarFondoSepelio();
		strAportacion = Constante.MANTENIMIENTO_GRABAR;
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarAportaciones()     			      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarFondoSepelio(){
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
		if(listaRequisitos!=null){
			listaRequisitos.clear();
		}
		setRbFecIndeterm("");
		setDaFechaIni(null);
		setDaFechaFin(null);
		setRbCondicion("");
		setStrValAporte("");
		setRbTasaInteres("");
		setFecFinAportacionRendered(true);
		setChkLimiteEdad(false);
		setEnabDisabLimiteEdad(true);
		setEnabDisabEscala(true);
		
		setChkTiempoPresentSustento(false);
		setChkEscala(false);
		setEnabDisabTiempoPresentSust(true);
		setChkRegularicCuota(false);
		setChkCeseBeneficioMeses(false);
		setChkSolicitudBeneficio(false);
		setChkAprobacBeneficio(false);
		setChkAnulRechazoBeneficio(false);
		setChkGiroBeneficio(false);
		setChkCancelacion(false);
		setEnabDisabProvision(true);
		setEnabDisabExtProvision(true);
		setEnabDisabRegularicCuota(true);
		setEnabDisabCeseBenefMeses(true);
		setEnabDisabSolicitudBenef(true);
		setEnabDisabAprobacBenef(true);
		setEnabDisabAnulRechazoBenef(true);
		setEnabDisabGiroBenef(true);
		setEnabDisabCancelacion(true);
		
		setEnabDisabValImporte(true);
		setEnabDisabValPorcentaje(true);
		
		setIntTipoBeneficiario(0);
		setIntRangoCuota(0);
		setIntCuotaCancelada(null);
		setBdBeneficio(null);
		setBdGastoAdm(null);
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
	public void cancelarGrabarFondoSepelio(ActionEvent event) {
		setFormFondoSepelioRendered(false);
		limpiarFondoSepelio();
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarFondoSepelio()        						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**************************************************************/
	public void listarFondoSepelio(ActionEvent event) {
		log.info("-----------------------Debugging FondoSepelioController.listarFondoSepelio-----------------------------");
		CaptacionFacadeLocal facade = null;
		try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			Captacion o = new Captacion();
			o.setId(new CaptacionId());
			o.setCondicion(new Condicion());
			o.getCondicion().setId(new CondicionId());
			o.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
			o.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_FDO_SEPELIO);
			if(intTipoFecha!=null && intTipoFecha!=0)
				o.setIntTipoFecha(intTipoFecha);
			if(intIdEstadoAportacion!=null && intIdEstadoAportacion!=0)
			o.setIntParaEstadoSolicitudCod(intIdEstadoAportacion);
			o.setStrDescripcion(strNombreFondoSepelio);
			if(intIdCondicionAportacion!=null && intIdCondicionAportacion!=0)
			o.getCondicion().getId().setIntParaCondicionSocioCod(intIdCondicionAportacion);
			if(intIdTipoConfig!=null && intIdTipoConfig!=0)
			o.setIntParaTipoConfiguracionCod(intIdTipoConfig);
			
			String strFechaIni = (getDaFecIni()!=null)?Constante.sdf.format(getDaFecIni()):null;
			String strFechaFin = (getDaFecFin()!=null)?Constante.sdf.format(getDaFecFin()):null;
			o.setStrDtFechaIni(strFechaIni);
			o.setStrDtFechaFin(strFechaFin);
			
			o.setDtInicio(daFecIni);
			o.setDtFin(daFecFin);
			if(intIdTipoPersona!=null && intIdTipoPersona!=0)
				o.setIntParaTipopersonaCod(intIdTipoPersona);
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
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  enableDisableControls()        					*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/ 
	/*  Objetivo: Habilitar y Desabilitar determinados controles    */
	/*            de acuerdo a determinados cambios de estado. 		*/
	/*                         						     	 		*/
	/*  Retorno : Devuelve habilitado o deshabilitado algún control	*/
	/*            seleccionado 						     	 		*/
	/**************************************************************/
	public void enableDisableControls(ActionEvent event) {
		setEnabDisabNombFondoSepelio(getChkNombFondoSepelio()==true);
		setStrNombreFondoSepelio(getChkNombFondoSepelio()==true?"":getStrNombreFondoSepelio());
		setEnabDisabFechasAport(getChkFechas()!=true);
		setDaFecIni(getChkFechas()!=true?null:getDaFecIni());
		setDaFecFin(getChkFechas()!=true?null:getDaFecFin());
		setBlnVigencia(chkAportVigentes!=true);
		setRbVigente(chkAportVigentes==true?1:null);
		setEnabDisabLimiteEdad(getChkLimiteEdad()!=null && getChkLimiteEdad()!=true);

		setEnabDisabTiempoPresentSust(getChkTiempoPresentSustento()!=null && getChkTiempoPresentSustento()!=true);
		setEnabDisabEscala(getChkEscala()!=null && getChkEscala()!=true);
		setEnabDisabCeseBenefMeses(getChkCeseBeneficioMeses()!=null && getChkCeseBeneficioMeses()!=true);
		
		setEnabDisabProvision(getChkProvision()!=true);
		setEnabDisabExtProvision(getChkExtProvision()!=true);
		setEnabDisabCancelacion(getChkCancelacion()!=true);
		setEnabDisabRegularicCuota(getChkRegularicCuota()!=true);
		setEnabDisabCeseBenef(getChkCeseBeneficio()!=true);
		setEnabDisabSolicitudBenef(getChkSolicitudBeneficio()!=true);
		setEnabDisabAprobacBenef(getChkAprobacBeneficio()!=true);
		setEnabDisabAnulRechazoBenef(getChkAnulRechazoBeneficio()!=true);
		setEnabDisabGiroBenef(getChkGiroBeneficio()!=true);
		setEnabDisabCancelacion(getChkCancelacion()!=true);
		
		setEnabDisabValImporte(getStrValAporte()!=null && !getStrValAporte().equals("1"));
		setEnabDisabValPorcentaje(getStrValAporte()!=null && !getStrValAporte().equals("2"));
		
		
		if(getRbFecIndeterm()!=null){
			setFecFinAportacionRendered(getRbFecIndeterm().equals("1"));
			setDaFechaFin(null);
		}
		
		Captacion aport = new Captacion();
		aport = (Captacion) getBeanCaptacion();
		
		if(getChkLimiteEdad()!=null && getChkLimiteEdad()==false){
			aport.setIntEdadLimite(null);
		}
		aport.setBdValorConfiguracion(getStrValAporte().equals("1")?aport.getBdValorConfiguracion():null);
		aport.setBdPorcConfiguracion(getStrValAporte().equals("2")?aport.getBdPorcConfiguracion():null);
		setBeanCaptacion(aport);
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
	
	public void irModificarFondoSepelio(ActionEvent event) throws ParseException{
    	log.info("-----------------------Debugging CreditoController.irModificarFondoSepelio-----------------------------");
    	CaptacionFacadeLocal captacionFacade = null;
    	String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmFondoSepelioModalPanel:hiddenIdEmpresa");
		String strIdTipoCaptacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmFondoSepelioModalPanel:hiddenIdTipoCaptacion");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmFondoSepelioModalPanel:hiddenIdCodigo");
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
				
				setEnabDisabTiempoPresentSust(beanCaptacion.getIntTiempoSustento()==null);
				setChkTiempoPresentSustento(beanCaptacion.getIntTiempoSustento()!=null);
				
				setEnabDisabLimiteEdad(beanCaptacion.getIntEdadLimite()==null);
				setChkLimiteEdad(beanCaptacion.getIntEdadLimite()!=null);
				setChkCeseBeneficioMeses(beanCaptacion.getIntCeseBeneficio()!=null);
				setEnabDisabCeseBenefMeses(beanCaptacion.getIntCeseBeneficio()==null);
				
				setStrValAporte(beanCaptacion.getBdValorConfiguracion()!=null?"1":"2");
				setEnabDisabValImporte(beanCaptacion.getBdValorConfiguracion()==null);
				setEnabDisabValPorcentaje(beanCaptacion.getBdPorcConfiguracion()==null);
				
				setRbCondicion(beanCaptacion.getListaCondicion().size()==4?"1":"2");
				if(beanCaptacion.getListaCondicionComp()!=null && beanCaptacion.getListaCondicionComp().size()>0){
					listaCondicionComp = beanCaptacion.getListaCondicionComp();
				}
				
				if(beanCaptacion.getListaRequisito()!=null && beanCaptacion.getListaRequisito().size()>0){
					listaRequisitos = beanCaptacion.getListaRequisito();
				}
				
				setFormFondoSepelioRendered(true);
				//strAportacion = Constante.MANTENIMIENTO_MODIFICAR;
				setStrAportacion((beanCaptacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO) || 
						beanCaptacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO) )?
						Constante.MANTENIMIENTO_ELIMINAR:Constante.MANTENIMIENTO_MODIFICAR);
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
	/*  Nombre :  grabarFondoSepelio()        						*/
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
	public void grabarFondoSepelio(ActionEvent event){
		CaptacionFacadeLocal facade = null;
		
	    if(isValidoAportacion(beanCaptacion) == false){
	    	log.info("Datos de zonal no válidos. Se aborta el proceso de grabación de zonal.");
	    	return;
	    }
	    //beanCaptacion.setId(new CaptacionId());
	    beanCaptacion.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);//beanSesion.getIntIdEmpresa()
	    beanCaptacion.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_FDO_SEPELIO);
	    beanCaptacion.setIntParaEstadoSolicitudCod(Constante.PARAM_T_ESTADOSOLICPAGO_PENDIENTE);
	    beanCaptacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
	    
		Date daFecIni = getDaFechaIni();
		String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
		beanCaptacion.setStrDtFechaIni(daFechaIni);
		beanCaptacion.setDtInicio(daFecIni);
		
		Date daFecFin = getDaFechaFin();
		String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
		beanCaptacion.setStrDtFechaFin(daFechaFin);
		beanCaptacion.setDtFin(daFecFin);
		
		beanCaptacion.setIntPeriodicSustento(Constante.PARAM_T_DIA);
		
		if(listaCondicionComp!=null){
			beanCaptacion.setListaCondicionComp(listaCondicionComp);
		}
		
		if(listaRequisitos!=null){
			beanCaptacion.setListaRequisito(listaRequisitos);
		}
	    
    	try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.grabarCaptacion(beanCaptacion);
			limpiarFondoSepelio();
			formFondoSepelioRendered = false;
			listarFondoSepelio(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  modificarFondoSepelio()        					*/
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
	
	public void modificarFondoSepelio(ActionEvent event){
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
		
		if(listaRequisitos!=null){
			beanCaptacion.setListaRequisito(listaRequisitos);
		}
    	
    	try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.modificarCaptacion(beanCaptacion);
			limpiarFondoSepelio();
			formFondoSepelioRendered = false;
			listarFondoSepelio(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  addReqVinculo()        							*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Agregar los Requisitos de Beneficio al Fondo de 	*/
	/*            Sepelio 						     	 			*/
	/*                         						     	 		*/
	/**/
	/***********************************************************************************/
	public void addReqVinculo(ActionEvent event){
		log.info("--------------FondoSepelioController.addReqVinculo-------------");
		Requisito requisito = new Requisito();
		requisito.setId(new RequisitoId());
		Boolean bValidar = true;
		
		if(getIntTipoBeneficiario()!=null && getIntTipoBeneficiario()==0){
			setMsgTxtTipoBeneficiario("* Debe ingresar el tipo de Beneficiario");
			bValidar = false;
		}else{
			setMsgTxtTipoBeneficiario("");
		}
		
		if(bValidar == true){
			requisito.getId().setIntParaTipoRequisitoBenef(getIntTipoBeneficiario());
			requisito.setIntParaTipoMaxMinCod(getIntRangoCuota());
			requisito.setIntNumeroCuota(getIntCuotaCancelada());
			requisito.setBdBeneficio(getBdBeneficio());
			requisito.setBdGastoAdministrativo(getBdGastoAdm());
			listaRequisitos.add(requisito);
		}
	}
	
	public void removeReqVinculo(ActionEvent event){
		log.info("Se ingreso al método removeReqVinculo");
		String rowKey = getRequestParameter("rowKeyRequisito");
		Requisito requisitoTmp = null;
		if(listaRequisitos!=null){
			for(int i=0; i<listaRequisitos.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Requisito requisito = listaRequisitos.get(i);
					if(requisito.getId()!=null && requisito.getId().getIntItemRequisito()!=null){
						requisitoTmp = listaRequisitos.get(i);
						requisitoTmp.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaRequisitos.remove(i);
					break;
				}
			}
			if(requisitoTmp!=null){
				listaRequisitos.add(requisitoTmp);
			}
		}
	}
	
	/*public void removeReqVinculo(ActionEvent event){
		log.info("------------------------FondoSepelioController.removeRequisito----------------------------");
		List<Requisito> arrayRequisito = new ArrayList();
	    for(int i=0; i<getListaRequisitos().size(); i++){
	    	Requisito requisito = new Requisito();
	    	requisito = (Requisito) getListaRequisitos().get(i);
	    	if(requisito.getChkRequisito() == false){
	    		arrayRequisito.add(requisito);
	    	}
	    }
	    listaRequisitos.clear();
	    setListaRequisitos(arrayRequisito);
	}*/
	
	public void eliminarFondoSepelio(ActionEvent event){
    	log.info("-----------------------Debugging FondoSepelioController.eliminarFondoSepelio-----------------------------");
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
			limpiarFondoSepelio();
			listarFondoSepelio(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
}