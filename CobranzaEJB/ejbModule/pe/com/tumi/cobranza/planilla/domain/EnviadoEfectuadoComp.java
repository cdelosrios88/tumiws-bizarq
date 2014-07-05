package pe.com.tumi.cobranza.planilla.domain;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.cobranza.prioridad.domain.PrioridadDescuento;

public class EnviadoEfectuadoComp {
	private Envioconcepto envioconcepto;
	private Enviomonto enviomonto;
	private Envioresumen envioresumen;
	private EfectuadoConcepto efectuadoConcepto;
	private Efectuado efectuado;
	private EfectuadoResumen efectuadoResumen;
	private PrioridadDescuento prioridadDescuento;
	private String strCobroPlanilla;
	private BigDecimal bdSumEnviomonto;
	private BigDecimal bdSumEfectuado;
	private Integer intPeriodoPlanilla;
	private String strPeriodoPlanilla;
	private String strPeriodoConFormato;
	private List<Envioconcepto> listaEnvioconcepto;
	private List<Enviomonto> listaEnviomonto;
	private List<Efectuado> listaEfectuado;
	private List<EfectuadoConcepto> listaEfectuadoConcepto;
	private List<PrioridadDescuento> listaPrioridadDescuento;
	private String[] lstMontoPorPeriodoYPrioridad;
	private BigDecimal[] lstBdMontoPorPeriodoYPrioridad;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 16-09-2013 (VARIABLES PARA CAPTURAR DATOS DE LA PRIORIDAD-USADAS EN EL FOREACH DE ESTADOCUENTABODY.JSP)
	private Integer intPrdeOrdenprioridad;
	private Integer intItemPrioridadDescuento;
	private String strDescPrioridad;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 18-09-2013
	private String strSumaDifMontoConcepto;
	private BigDecimal bdSumaDifMontoConcepto;
	public Envioconcepto getEnvioconcepto() {
		return envioconcepto;
	}
	public void setEnvioconcepto(Envioconcepto envioconcepto) {
		this.envioconcepto = envioconcepto;
	}
	public Enviomonto getEnviomonto() {
		return enviomonto;
	}
	public void setEnviomonto(Enviomonto enviomonto) {
		this.enviomonto = enviomonto;
	}
	public Envioresumen getEnvioresumen() {
		return envioresumen;
	}
	public void setEnvioresumen(Envioresumen envioresumen) {
		this.envioresumen = envioresumen;
	}
	public EfectuadoConcepto getEfectuadoConcepto() {
		return efectuadoConcepto;
	}
	public void setEfectuadoConcepto(EfectuadoConcepto efectuadoConcepto) {
		this.efectuadoConcepto = efectuadoConcepto;
	}
	public Efectuado getEfectuado() {
		return efectuado;
	}
	public void setEfectuado(Efectuado efectuado) {
		this.efectuado = efectuado;
	}
	public EfectuadoResumen getEfectuadoResumen() {
		return efectuadoResumen;
	}
	public void setEfectuadoResumen(EfectuadoResumen efectuadoResumen) {
		this.efectuadoResumen = efectuadoResumen;
	}
	public PrioridadDescuento getPrioridadDescuento() {
		return prioridadDescuento;
	}
	public void setPrioridadDescuento(PrioridadDescuento prioridadDescuento) {
		this.prioridadDescuento = prioridadDescuento;
	}
	public String getStrCobroPlanilla() {
		return strCobroPlanilla;
	}
	public void setStrCobroPlanilla(String strCobroPlanilla) {
		this.strCobroPlanilla = strCobroPlanilla;
	}
	public BigDecimal getBdSumEnviomonto() {
		return bdSumEnviomonto;
	}
	public void setBdSumEnviomonto(BigDecimal bdSumEnviomonto) {
		this.bdSumEnviomonto = bdSumEnviomonto;
	}
	public BigDecimal getBdSumEfectuado() {
		return bdSumEfectuado;
	}
	public void setBdSumEfectuado(BigDecimal bdSumEfectuado) {
		this.bdSumEfectuado = bdSumEfectuado;
	}
	public Integer getIntPeriodoPlanilla() {
		return intPeriodoPlanilla;
	}
	public void setIntPeriodoPlanilla(Integer intPeriodoPlanilla) {
		this.intPeriodoPlanilla = intPeriodoPlanilla;
	}
	public String getStrPeriodoPlanilla() {
		return strPeriodoPlanilla;
	}
	public void setStrPeriodoPlanilla(String strPeriodoPlanilla) {
		this.strPeriodoPlanilla = strPeriodoPlanilla;
	}
	public List<Envioconcepto> getListaEnvioconcepto() {
		return listaEnvioconcepto;
	}
	public void setListaEnvioconcepto(List<Envioconcepto> listaEnvioconcepto) {
		this.listaEnvioconcepto = listaEnvioconcepto;
	}
	public List<Enviomonto> getListaEnviomonto() {
		return listaEnviomonto;
	}
	public void setListaEnviomonto(List<Enviomonto> listaEnviomonto) {
		this.listaEnviomonto = listaEnviomonto;
	}
	public List<Efectuado> getListaEfectuado() {
		return listaEfectuado;
	}
	public void setListaEfectuado(List<Efectuado> listaEfectuado) {
		this.listaEfectuado = listaEfectuado;
	}
	public List<EfectuadoConcepto> getListaEfectuadoConcepto() {
		return listaEfectuadoConcepto;
	}
	public void setListaEfectuadoConcepto(
			List<EfectuadoConcepto> listaEfectuadoConcepto) {
		this.listaEfectuadoConcepto = listaEfectuadoConcepto;
	}
	public List<PrioridadDescuento> getListaPrioridadDescuento() {
		return listaPrioridadDescuento;
	}
	public void setListaPrioridadDescuento(
			List<PrioridadDescuento> listaPrioridadDescuento) {
		this.listaPrioridadDescuento = listaPrioridadDescuento;
	}
	public String[] getLstMontoPorPeriodoYPrioridad() {
		return lstMontoPorPeriodoYPrioridad;
	}
	public void setLstMontoPorPeriodoYPrioridad(
			String[] lstMontoPorPeriodoYPrioridad) {
		this.lstMontoPorPeriodoYPrioridad = lstMontoPorPeriodoYPrioridad;
	}
	public Integer getIntPrdeOrdenprioridad() {
		return intPrdeOrdenprioridad;
	}
	public void setIntPrdeOrdenprioridad(Integer intPrdeOrdenprioridad) {
		this.intPrdeOrdenprioridad = intPrdeOrdenprioridad;
	}
	public Integer getIntItemPrioridadDescuento() {
		return intItemPrioridadDescuento;
	}
	public void setIntItemPrioridadDescuento(Integer intItemPrioridadDescuento) {
		this.intItemPrioridadDescuento = intItemPrioridadDescuento;
	}
	public String getStrDescPrioridad() {
		return strDescPrioridad;
	}
	public void setStrDescPrioridad(String strDescPrioridad) {
		this.strDescPrioridad = strDescPrioridad;
	}
	public String getStrSumaDifMontoConcepto() {
		return strSumaDifMontoConcepto;
	}
	public void setStrSumaDifMontoConcepto(String strSumaDifMontoConcepto) {
		this.strSumaDifMontoConcepto = strSumaDifMontoConcepto;
	}
	public BigDecimal[] getLstBdMontoPorPeriodoYPrioridad() {
		return lstBdMontoPorPeriodoYPrioridad;
	}
	public void setLstBdMontoPorPeriodoYPrioridad(
			BigDecimal[] lstBdMontoPorPeriodoYPrioridad) {
		this.lstBdMontoPorPeriodoYPrioridad = lstBdMontoPorPeriodoYPrioridad;
	}
	public BigDecimal getBdSumaDifMontoConcepto() {
		return bdSumaDifMontoConcepto;
	}
	public void setBdSumaDifMontoConcepto(BigDecimal bdSumaDifMontoConcepto) {
		this.bdSumaDifMontoConcepto = bdSumaDifMontoConcepto;
	}
	public String getStrPeriodoConFormato() {
		return strPeriodoConFormato;
	}
	public void setStrPeriodoConFormato(String strPeriodoConFormato) {
		this.strPeriodoConFormato = strPeriodoConFormato;
	}
}
