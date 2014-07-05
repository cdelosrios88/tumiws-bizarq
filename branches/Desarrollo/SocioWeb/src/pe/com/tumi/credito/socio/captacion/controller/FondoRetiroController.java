package pe.com.tumi.credito.socio.captacion.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.CondicionComp;
import pe.com.tumi.credito.socio.captacion.domain.CondicionId;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.credito.socio.captacion.domain.VinculoId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeLocal;
import pe.com.tumi.credito.socio.convenio.domain.composite.CaptacionComp;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;

/************************************************************************/
/* Nombre de la clase: CreditoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* y validaciones de Fondo de Retiro */
/* Ref. : */
/* Autor : CDLRF */
/* Versión : V1 */
/* Fecha creación : 26/03/2012 */
/* ********************************************************************* */

public class FondoRetiroController {
	protected  static Logger 	log 			= Logger.getLogger(FondoRetiroController.class);
	private int 				rows = 5;
	private Captacion			beanCaptacion = new Captacion();
	private Condicion			beanCondicion = new Condicion();
	//private BeanSesion 		beanSesion = new BeanSesion();
	//Variables generales
	private Boolean 			formFondoRetiroRendered = false;
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
	private Boolean				chkTiempoMinAport;
	private Boolean				chkCuotasMinimas;
	private Boolean				chkTiempoDevol;
	private Boolean				chkEscalaBenef;
	private Boolean				enabDisabTiempoMinAport = true;
	private Boolean				enabDisabCuotasMinDeposit = true;
	private Boolean				enabDisabTiempoMinDevol = true;
	private Boolean				enabDisabEscBenef = true;
	private Integer				intTipoFecha;
	
	//Variables de activación y desactivación de controles
	private Boolean				chkNombFondoRetiro;
	private Boolean				enabDisabNombAporte = false;
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
	private String				rbPenalidad;
	private Boolean				chkPenalidadAnticip;
	private Boolean				enabDisabPenalidadAnticip = true;
	private Boolean				enabDisabPenalPorc = true;
	private Boolean				enabDisabPenalTasaMora = true;
	private Boolean				chkCeseBeneficioMeses;
	private Boolean				enabDisabCeseBenefMeses = true;
	
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
	private String				msgTxtTipoBeneficiario;
	private String				msgTxtTipoMotivo;
	
	private String 				strAportacion;
	//
	private	Integer				intTipoBeneficiario;
	private	Integer				intTipoMotivo;
	private	Integer				intTipoCuota;
	private	Integer				intCuotaCancelada;
	private	Boolean				bAportacion;
	private	Boolean				bInteres;
	private	Boolean				bPenalidad;
	
	private List<Tabla>			listaTipoRelacion;
	private List<CaptacionComp>	listaCaptacionComp;
	private List<CondicionComp>	listaCondicionComp;
	private List<Vinculo>		listaRequisitosVinculo = new ArrayList<Vinculo>();
	private	ArrayList 			ArrayVinculo = new ArrayList();
	// CGD - 06.12.2013
	private List<Tabla>			listaTipoBeneficiario = new ArrayList<Tabla>();
	
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
	public Condicion getBeanCondicion() {
		return beanCondicion;
	}
	public void setBeanCondicion(Condicion beanCondicion) {
		this.beanCondicion = beanCondicion;
	}
	public Boolean getFormFondoRetiroRendered() {
		return formFondoRetiroRendered;
	}
	public void setFormFondoRetiroRendered(Boolean formFondoRetiroRendered) {
		this.formFondoRetiroRendered = formFondoRetiroRendered;
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
	public Boolean getChkTiempoMinAport() {
		return chkTiempoMinAport;
	}
	public void setChkTiempoMinAport(Boolean chkTiempoMinAport) {
		this.chkTiempoMinAport = chkTiempoMinAport;
	}
	public Boolean getChkCuotasMinimas() {
		return chkCuotasMinimas;
	}
	public void setChkCuotasMinimas(Boolean chkCuotasMinimas) {
		this.chkCuotasMinimas = chkCuotasMinimas;
	}
	public Boolean getChkTiempoDevol() {
		return chkTiempoDevol;
	}
	public void setChkTiempoDevol(Boolean chkTiempoDevol) {
		this.chkTiempoDevol = chkTiempoDevol;
	}
	public Boolean getChkEscalaBenef() {
		return chkEscalaBenef;
	}
	public void setChkEscalaBenef(Boolean chkEscalaBenef) {
		this.chkEscalaBenef = chkEscalaBenef;
	}
	public Boolean getEnabDisabTiempoMinAport() {
		return enabDisabTiempoMinAport;
	}
	public void setEnabDisabTiempoMinAport(Boolean enabDisabTiempoMinAport) {
		this.enabDisabTiempoMinAport = enabDisabTiempoMinAport;
	}
	public Boolean getEnabDisabCuotasMinDeposit() {
		return enabDisabCuotasMinDeposit;
	}
	public void setEnabDisabCuotasMinDeposit(Boolean enabDisabCuotasMinDeposit) {
		this.enabDisabCuotasMinDeposit = enabDisabCuotasMinDeposit;
	}
	public Boolean getEnabDisabTiempoMinDevol() {
		return enabDisabTiempoMinDevol;
	}
	public void setEnabDisabTiempoMinDevol(Boolean enabDisabTiempoMinDevol) {
		this.enabDisabTiempoMinDevol = enabDisabTiempoMinDevol;
	}
	public Boolean getEnabDisabEscBenef() {
		return enabDisabEscBenef;
	}
	public void setEnabDisabEscBenef(Boolean enabDisabEscBenef) {
		this.enabDisabEscBenef = enabDisabEscBenef;
	}
	public Integer getIntTipoFecha() {
		return intTipoFecha;
	}
	public void setIntTipoFecha(Integer intTipoFecha) {
		this.intTipoFecha = intTipoFecha;
	}
	public Boolean getChkNombFondoRetiro() {
		return chkNombFondoRetiro;
	}
	public void setChkNombFondoRetiro(Boolean chkNombFondoRetiro) {
		this.chkNombFondoRetiro = chkNombFondoRetiro;
	}
	public Boolean getEnabDisabNombAporte() {
		return enabDisabNombAporte;
	}
	public void setEnabDisabNombAporte(Boolean enabDisabNombAporte) {
		this.enabDisabNombAporte = enabDisabNombAporte;
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
	public Boolean getChkPenalidadAnticip() {
		return chkPenalidadAnticip;
	}
	public void setChkPenalidadAnticip(Boolean chkPenalidadAnticip) {
		this.chkPenalidadAnticip = chkPenalidadAnticip;
	}
	public Boolean getEnabDisabPenalidadAnticip() {
		return enabDisabPenalidadAnticip;
	}
	public void setEnabDisabPenalidadAnticip(Boolean enabDisabPenalidadAnticip) {
		this.enabDisabPenalidadAnticip = enabDisabPenalidadAnticip;
	}
	public Boolean getEnabDisabPenalPorc() {
		return enabDisabPenalPorc;
	}
	public void setEnabDisabPenalPorc(Boolean enabDisabPenalPorc) {
		this.enabDisabPenalPorc = enabDisabPenalPorc;
	}
	public Boolean getEnabDisabPenalTasaMora() {
		return enabDisabPenalTasaMora;
	}
	public void setEnabDisabPenalTasaMora(Boolean enabDisabPenalTasaMora) {
		this.enabDisabPenalTasaMora = enabDisabPenalTasaMora;
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
	public String getRbPenalidad() {
		return rbPenalidad;
	}
	public void setRbPenalidad(String rbPenalidad) {
		this.rbPenalidad = rbPenalidad;
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
	public String getMsgTxtTipoBeneficiario() {
		return msgTxtTipoBeneficiario;
	}
	public void setMsgTxtTipoBeneficiario(String msgTxtTipoBeneficiario) {
		this.msgTxtTipoBeneficiario = msgTxtTipoBeneficiario;
	}
	public String getMsgTxtTipoMotivo() {
		return msgTxtTipoMotivo;
	}
	public void setMsgTxtTipoMotivo(String msgTxtTipoMotivo) {
		this.msgTxtTipoMotivo = msgTxtTipoMotivo;
	}
	public String getStrAportacion() {
		return strAportacion;
	}
	public void setStrAportacion(String strAportacion) {
		this.strAportacion = strAportacion;
	}
	public Integer getIntTipoBeneficiario() {
		return intTipoBeneficiario;
	}
	public void setIntTipoBeneficiario(Integer intTipoBeneficiario) {
		this.intTipoBeneficiario = intTipoBeneficiario;
	}
	public Integer getIntTipoMotivo() {
		return intTipoMotivo;
	}
	public void setIntTipoMotivo(Integer intTipoMotivo) {
		this.intTipoMotivo = intTipoMotivo;
	}
	public Integer getIntTipoCuota() {
		return intTipoCuota;
	}
	public void setIntTipoCuota(Integer intTipoCuota) {
		this.intTipoCuota = intTipoCuota;
	}
	public Integer getIntCuotaCancelada() {
		return intCuotaCancelada;
	}
	public void setIntCuotaCancelada(Integer intCuotaCancelada) {
		this.intCuotaCancelada = intCuotaCancelada;
	}
	public Boolean getbAportacion() {
		return bAportacion;
	}
	public void setbAportacion(Boolean bAportacion) {
		this.bAportacion = bAportacion;
	}
	public Boolean getbInteres() {
		return bInteres;
	}
	public void setbInteres(Boolean bInteres) {
		this.bInteres = bInteres;
	}
	public Boolean getbPenalidad() {
		return bPenalidad;
	}
	public void setbPenalidad(Boolean bPenalidad) {
		this.bPenalidad = bPenalidad;
	}
	public ArrayList getArrayVinculo() {
		return ArrayVinculo;
	}
	public void setArrayVinculo(ArrayList arrayVinculo) {
		ArrayVinculo = arrayVinculo;
	}
	public List<Tabla> getListaTipoRelacion() {
		TablaFacadeRemote remote;
		try {
			remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			try {
				listaTipoRelacion = remote.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOROL), "C");
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
		return listaTipoRelacion;
	}


	public List<Tabla> getListaTipoBeneficiario() {
		return listaTipoBeneficiario;
	}
	public void setListaTipoRelacion(List<Tabla> listaTipoRelacion) {
		this.listaTipoRelacion = listaTipoRelacion;
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
	public List<Vinculo> getListaRequisitosVinculo() {
		return listaRequisitosVinculo;
	}
	public void setListaRequisitosVinculo(List<Vinculo> listaRequisitosVinculo) {
		this.listaRequisitosVinculo = listaRequisitosVinculo;
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}

	public void setListaTipoBeneficiario(List<Tabla> listaTipoBeneficiario) {
		this.listaTipoBeneficiario = listaTipoBeneficiario;
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
		setFormFondoRetiroRendered(true);
		limpiarFondoRetiro();
		strAportacion = Constante.MANTENIMIENTO_GRABAR;
	}
	
	/**************************************************************/
	/*  Nombre :  limpiarAportaciones()     			      	*/
	/*                                                    	 	*/
	/*  Parametros. :  Ninguno					           	 	*/
	/*  Objetivo: Limpiar el Formulario de Aportaciones			*/
	/*  Retorno : El formulario de Aportaciones vacío 			*/
	/**************************************************************/
	public void limpiarFondoRetiro(){
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
		if(listaRequisitosVinculo!=null){
			listaRequisitosVinculo.clear();
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
		setChkPenalidadAnticip(false);
		setRbPenalidad(null);
		setChkTiempoMinAport(false);
		setChkCuotasMinimas(false);
		setChkTiempoDevol(false);
		setChkEscalaBenef(false);
		setIntTipoBeneficiario(0);
		setIntTipoMotivo(0);
		setIntTipoCuota(0);
		setIntCuotaCancelada(null);
		setbAportacion(false);
		setbInteres(false);
		setbPenalidad(false);
		setEnabDisabLimiteEdad(true);
		setEnabDisabPenalidadAnticip(true);
		setEnabDisabPenalidadAnticip(true);
		setEnabDisabPenalPorc(true);
		setEnabDisabPenalTasaMora(true);
		setEnabDisabTiempoMinAport(true);
		setEnabDisabCuotasMinDeposit(true);
		setEnabDisabTiempoMinDevol(true);
		setEnabDisabEscBenef(true);
		setChkCeseBeneficio(false);
		setEnabDisabCeseBenef(true);
		setChkProvision(false);
		setChkExtProvision(false);
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
		setFormFondoRetiroRendered(false);
		limpiarFondoRetiro();
	}
	
	/**************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  listarFondoRetiro()        						*/
	/*                                                    	 		*/
	/*  Parametros. :  event       descripcion            	 		*/
	/*                         						     	 		*/
	/*  Objetivo: Listar las Hojas de Planeamiento     				*/
	/*  Retorno : Devuelve el listado de las Hojas de Planeamiento 	*/
	/**************************************************************/
	public void listarFondoRetiro(ActionEvent event) {
		log.info("-----------------------Debugging FondoRetiroController.listarFondoRetiro-----------------------------");
		CaptacionFacadeLocal facade = null;
		try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			Captacion o = new Captacion();
			o.setId(new CaptacionId());
			o.setCondicion(new Condicion());
			o.getCondicion().setId(new CondicionId());
			o.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
			o.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_FDO_RETIRO);
			if(intTipoFecha!=null && intTipoFecha!=0)
				o.setIntTipoFecha(intTipoFecha);
			if(intIdEstadoAportacion!=null && intIdEstadoAportacion!=0)
			o.setIntParaEstadoSolicitudCod(intIdEstadoAportacion);
			o.setStrDescripcion(strNombreAporte);
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
			/*String strFechaIni = (getDaFecIni()!=null)?Constante.sdf.format(getDaFecIni()):null;
			String strFechaFin = (getDaFecFin()!=null)?Constante.sdf.format(getDaFecFin()):null;
			o.setStrDtFechaIni(strFechaIni);
		    o.setStrDtFechaFin(strFechaFin);*/
			if(intIdTipoPersona!=null && intIdTipoPersona!=0)
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
		log.info("-----------------------Debugging FondoRetiroController.enableDisableControls-----------------------------");
		
		try {
			
			TablaFacadeRemote remote;
			remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTipoBeneficiario = remote.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOVINCULO), "A");

			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Captacion aport = new Captacion();
		aport = (Captacion) getBeanCaptacion();
		
		setEnabDisabNombAporte(getChkNombFondoRetiro()==true);
		setStrNombreAporte(getChkNombFondoRetiro()==true?"":getStrNombreAporte());
		setEnabDisabFechasAport(getChkFechas()!=true);
		setDaFecIni(getChkFechas()!=true?null:getDaFecIni());
		setDaFecFin(getChkFechas()!=true?null:getDaFecFin());
		setBlnVigencia(chkAportVigentes!=true);
		setRbVigente(chkAportVigentes==true?1:null);
		
		setEnabDisabTasaInt(getChkTasaInteres()!=true);
		setEnabDisabPorcInt(getChkTasaInteres()==true);
		setEnabDisabTea(getChkTasaInteres()==true);
		setEnabDisabTna(getChkTasaInteres()!=true);
		
		setEnabDisabPenalidadAnticip(getChkPenalidadAnticip()!=true);
		if(getRbPenalidad()!=null){
			setEnabDisabPenalPorc(!getRbPenalidad().equals("1"));
			setEnabDisabPenalTasaMora(!getRbPenalidad().equals("2"));
		}
		if(getChkPenalidadAnticip()==false){
			setRbPenalidad(null);
			setEnabDisabPenalPorc(true);
			setEnabDisabPenalTasaMora(true);
		}
		setEnabDisabLimiteEdad(getChkLimiteEdad()!=true);
		setEnabDisabCeseBenefMeses(getChkCeseBeneficioMeses()!=true);
		
		setEnabDisabProvision(getChkProvision()!=true);
		setEnabDisabExtProvision(getChkExtProvision()!=true);
		setEnabDisabRegularicCuota(getChkRegularicCuota()!=true);
		setEnabDisabCeseBenef(getChkCeseBeneficio()!=true);
		setEnabDisabSolicitudBenef(getChkSolicitudBeneficio()!=true);
		setEnabDisabAprobacBenef(getChkAprobacBeneficio()!=true);
		setEnabDisabAnulRechazoBenef(getChkAnulRechazoBeneficio()!=true);
		setEnabDisabGiroBenef(getChkGiroBeneficio()!=true);
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
		
		if(getRbPenalidad()!=null && getRbPenalidad().equals("1")){
			aport.setBdPenalidadPorcentaje(new BigDecimal(0));
			aport.setBdPenalidadTasaMora(new BigDecimal(0));
		}else{
			aport.setBdPenalidadPorcentaje(new BigDecimal(0));
			aport.setBdPenalidadTasaMora(new BigDecimal(0));
		}
		
		setEnabDisabValImporte(getStrValAporte()!=null && !getStrValAporte().equals("1"));
		setEnabDisabValPorcentaje(getStrValAporte()!=null && !getStrValAporte().equals("2"));
		
		setEnabDisabTiempoMinAport(getChkTiempoMinAport()!=true);
		setEnabDisabCuotasMinDeposit(getChkCuotasMinimas()!=true);
		setEnabDisabTiempoMinDevol(getChkTiempoDevol()!=true);
		setEnabDisabEscBenef(getChkEscalaBenef()!=true);
		
		if(getChkLimiteEdad()!=null && getChkLimiteEdad()==false){
			aport.setIntEdadLimite(null);
		}
		aport.setBdValorConfiguracion(getStrValAporte().equals("1")?aport.getBdValorConfiguracion():null);
		aport.setBdPorcConfiguracion(getStrValAporte().equals("2")?aport.getBdPorcConfiguracion():null);
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
	
	public void irModificarFondoRetiro(ActionEvent event) throws ParseException{
    	log.info("-----------------------Debugging CreditoController.modificarAportaciones-----------------------------");
    	CaptacionFacadeLocal captacionFacade = null;
    	String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmFondoRetiroModalPanel:hiddenIdEmpresa");
		String strIdTipoCaptacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmFondoRetiroModalPanel:hiddenIdTipoCaptacion");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmFondoRetiroModalPanel:hiddenIdCodigo");
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
				
				setChkTasaInteres(beanCaptacion.getBdTna()!=null || beanCaptacion.getBdTea()!=null);
				setRbTasaInteres(beanCaptacion.getBdTem()!=null?"1":"2");
				setEnabDisabTasaInt(getRbTasaInteres()==null);
				setEnabDisabPorcInt(beanCaptacion.getBdTem().compareTo(BigDecimal.ZERO)==0);
				setEnabDisabTea(beanCaptacion.getBdTea().compareTo(BigDecimal.ZERO)==0);
				setEnabDisabTna(beanCaptacion.getBdTna()==null || beanCaptacion.getBdTna().compareTo(BigDecimal.ZERO)==0);
				
				setStrValAporte(beanCaptacion.getBdValorConfiguracion()!=null?"1":"2");
				setEnabDisabValImporte(beanCaptacion.getBdValorConfiguracion()==null);
				setEnabDisabValPorcentaje(beanCaptacion.getBdPorcConfiguracion()==null);
				
				setEnabDisabLimiteEdad(beanCaptacion.getIntEdadLimite()==null);
				setChkLimiteEdad(beanCaptacion.getIntEdadLimite()!=null);
				setRbPenalidad((beanCaptacion.getBdPenalidadPorcentaje()==null && beanCaptacion.getBdPenalidadPorcentaje().compareTo(BigDecimal.ZERO)==0)?"1":"2");
				setRbCondicion(beanCaptacion.getListaCondicion().size()==4?"1":"2");
				if(beanCaptacion.getListaCondicionComp()!=null && beanCaptacion.getListaCondicionComp().size()>0){
					listaCondicionComp = beanCaptacion.getListaCondicionComp();
				}
				
				setChkPenalidadAnticip(getRbPenalidad()!=null);
				setEnabDisabPenalidadAnticip(beanCaptacion.getBdPenalidadPorcentaje()==null ||  beanCaptacion.getBdPenalidadTasaMora()==null);
				setEnabDisabPenalPorc(beanCaptacion.getBdPenalidadPorcentaje()==null || beanCaptacion.getBdPenalidadPorcentaje().compareTo(BigDecimal.ZERO)==0);
				setEnabDisabPenalTasaMora(beanCaptacion.getBdPenalidadTasaMora()==null || beanCaptacion.getBdPenalidadTasaMora().compareTo(BigDecimal.ZERO)==0);
				setChkTiempoMinAport(beanCaptacion.getIntTiempoAportacion()!=null && beanCaptacion.getIntTiempoAportacion()!=0);
				setEnabDisabTiempoMinAport(beanCaptacion.getIntTiempoAportacion()==null);
				setChkCuotasMinimas(beanCaptacion.getIntCuota()!=null && beanCaptacion.getIntCuota()!=0);
				setEnabDisabCuotasMinDeposit(beanCaptacion.getIntCuota()==null);
				setChkTiempoDevol(beanCaptacion.getIntTiempoDevolucion()!=null && beanCaptacion.getIntTiempoDevolucion()!=0);
				setEnabDisabTiempoMinDevol(beanCaptacion.getIntTiempoDevolucion()==null);
				setChkEscalaBenef(false);
				if(beanCaptacion.getListaVinculo()!=null && beanCaptacion.getListaVinculo().size()>0){
					listaRequisitosVinculo = beanCaptacion.getListaVinculo();
				}
				
				setChkCeseBeneficioMeses(beanCaptacion.getIntCeseBeneficio()!=null);
				setEnabDisabCeseBenefMeses(beanCaptacion.getIntCeseBeneficio()==null);
				
				setFormFondoRetiroRendered(true);
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
	/*  Nombre :  grabarFondoRetiro()        						*/
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
	public void grabarFondoRetiro(ActionEvent event){
		CaptacionFacadeLocal facade = null;
		
	    if(isValidoAportacion(beanCaptacion) == false){
	    	log.info("Datos de Fondo de Retiro no válidos. Se aborta el proceso de grabación de Fondo de Retiro.");
	    	return;
	    }
	    //beanCaptacion.setId(new CaptacionId());
	    beanCaptacion.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);//beanSesion.getIntIdEmpresa()
	    beanCaptacion.getId().setIntParaTipoCaptacionCod(Constante.CAPTACION_FDO_RETIRO);
	    beanCaptacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_INT);
	    beanCaptacion.setIntParaEstadoSolicitudCod(Constante.PARAM_T_ESTADODOCUMENTO_PENDIENTE);
	    
		Date daFecIni = getDaFechaIni();
		String daFechaIni = (daFecIni == null ? "" : Constante.sdf.format(daFecIni));
		beanCaptacion.setStrDtFechaIni(daFechaIni);
		beanCaptacion.setDtInicio(daFecIni);
		
		Date daFecFin = getDaFechaFin();
		String daFechaFin = (daFecFin == null ? "" : Constante.sdf.format(daFecFin));
		beanCaptacion.setStrDtFechaFin(daFechaFin);
		beanCaptacion.setDtFin(daFecFin);
		
		beanCaptacion.setIntPeriodicAportacionCod(Constante.PARAM_T_MES);
		beanCaptacion.setIntParaTipoMaxMinAportCod(Constante.PARAM_T_MINIMO);
		
		beanCaptacion.setIntParaPeriodicCuotasCod(Constante.PARAM_T_MES);
		beanCaptacion.setIntParaTipoMaxMinCuotaCod(Constante.PARAM_T_MINIMO);
		
		beanCaptacion.setIntParaTipoMaxMinDevolucionCod(Constante.PARAM_T_MINIMO);
		
		if(listaCondicionComp!=null){
			beanCaptacion.setListaCondicionComp(listaCondicionComp);
		}
		
		if(listaRequisitosVinculo!=null){
			beanCaptacion.setListaVinculo(listaRequisitosVinculo);
		}
	    
    	try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.grabarCaptacion(beanCaptacion);
			limpiarFondoRetiro();
			formFondoRetiroRendered = false;
			listarFondoRetiro(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************/
	/*                                                    	 		*/
	/*  Nombre :  modificarFondoRetiro()        						*/
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
	
	public void modificarFondoRetiro(ActionEvent event){
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
		
		if(listaRequisitosVinculo!=null){
			beanCaptacion.setListaVinculo(listaRequisitosVinculo);
		}
		
    	try {
			facade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			facade.modificarCaptacion(beanCaptacion);
			limpiarFondoRetiro();
			formFondoRetiroRendered = false;
			listarFondoRetiro(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
	
	public void addReqVinculo(ActionEvent event){
		log.info("--------------FondoRetiroController.addReqVinculo-------------");
		Vinculo vinculo = new Vinculo();
		vinculo.setId(new VinculoId());
		Boolean bValidar = true;
		
		if(getIntTipoBeneficiario()!=null && getIntTipoBeneficiario()==0){
			setMsgTxtTipoBeneficiario("* Debe ingresar el tipo de Beneficiario");
			bValidar = false;
		}else{
			setMsgTxtTipoBeneficiario("");
		}
		if(getIntTipoMotivo()!=null && getIntTipoMotivo()==0){
			setMsgTxtTipoMotivo("* Debe ingresar el tipo de Motivo");
			bValidar = false;
		}else{
			setMsgTxtTipoMotivo("");
		}
		
		if(bValidar == true){
			vinculo.getId().setParaTipoVinculoCod(getIntTipoBeneficiario());
			vinculo.setIntParaMotivo(getIntTipoMotivo());
			vinculo.setIntParaTipoCuotaCod(getIntTipoCuota());
			vinculo.setIntCuotaCancelada(getIntCuotaCancelada());
			vinculo.setIntAportacion(getbAportacion()==true?1:0);
			vinculo.setIntInteres(getbInteres()==true?1:0);
			vinculo.setIntPenalidad(getbPenalidad()==true?1:0);
			listaRequisitosVinculo.add(vinculo);
		}
	}
	
	public void removeReqVinculo(ActionEvent event){
		log.info("Se ingreso al método removeReqVinculo");
		String rowKey = getRequestParameter("rowKeyVinculo");
		Vinculo vinculoTmp = null;
		if(listaRequisitosVinculo!=null){
			for(int i=0; i<listaRequisitosVinculo.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Vinculo vinculo = listaRequisitosVinculo.get(i);
					if(vinculo.getId()!=null && vinculo.getId().getIntItemVinculo()!=null){
						vinculoTmp = listaRequisitosVinculo.get(i);
						vinculoTmp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listaRequisitosVinculo.remove(i);
					break;
				}
			}
			if(vinculoTmp!=null){
				listaRequisitosVinculo.add(vinculoTmp);
			}
		}
	}
	
	/*public void removeReqVinculo(ActionEvent event){
		log.info("------------------------FondoRetiroController.removeReqVinculo----------------------------");
		List<Vinculo> arrayReqVinculo = new ArrayList();
	    for(int i=0; i<getListaRequisitosVinculo().size(); i++){
	    	Vinculo vinculo = new Vinculo();
	    	vinculo = (Vinculo) getListaRequisitosVinculo().get(i);
	    	if(vinculo.getChkVinculo() == false){
	    		arrayReqVinculo.add(vinculo);
	    	}
	    }
	    listaRequisitosVinculo.clear();
	    setListaRequisitosVinculo(arrayReqVinculo);
	}*/
	
	public void eliminarFondoRetiro(ActionEvent event){
    	log.info("-----------------------Debugging EmpresaController.eliminarZonal-----------------------------");
    	String strIdEmpresa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmFondoRetiroModalPanel:hiddenIdEmpresa");
		String strIdTipoCaptacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmFondoRetiroModalPanel:hiddenIdTipoCaptacion");
		String strIdCodigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frmFondoRetiroModalPanel:hiddenIdCodigo");
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
			limpiarFondoRetiro();
			listarFondoRetiro(event);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
    }
}