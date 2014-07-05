package pe.com.tumi.estadoCuenta.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DataBeanEstadoCuentaPlanillas extends TumiDomain {
	private Integer intPeriodo;
	private String strPeriodo;
	private Integer intAnioPlanilla;
	private Integer intMesPlanilla;
	private BigDecimal bdMontoEnvioHaberes;
	private BigDecimal bdMontoEnvioIncentivos;
	private BigDecimal bdMontoEnvioCas;	
	private BigDecimal bdMontoTotalEnviado;	
	private BigDecimal bdMontoEfectuadoHaberes;
	private BigDecimal bdMontoEfectuadoIncentivos;
	private BigDecimal bdMontoEfectuadoCas;	
	private BigDecimal bdMontoTotalEfectuado;	
	private BigDecimal bdMontoTotalDiferencia;
	private String strEstadoPagoHaberes;
	private String strEstadoPagoIncentivos;
	private String strEstadoPagoCas;
	
	//Diferencia Planillas
	private Integer intCtaConcepto;
	private String strExpediente;
	private BigDecimal bdMontoEnviado;
	private BigDecimal bdMontoEfectuado;
	private Integer intCodigoPrioridad;
	private Integer intOrdenPrioridad;
	
	private BigDecimal[] bdMontoDiferencia;
	private BigDecimal bdSumaDiferenciaPlanilla;	
	
	private Integer intTipoConceptoGral;
	private String strDescripcionDiferencia;
	
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}
	public Integer getIntAnioPlanilla() {
		return intAnioPlanilla;
	}
	public void setIntAnioPlanilla(Integer intAnioPlanilla) {
		this.intAnioPlanilla = intAnioPlanilla;
	}
	public Integer getIntMesPlanilla() {
		return intMesPlanilla;
	}
	public void setIntMesPlanilla(Integer intMesPlanilla) {
		this.intMesPlanilla = intMesPlanilla;
	}
	public BigDecimal getBdMontoEnvioHaberes() {
		return bdMontoEnvioHaberes;
	}
	public void setBdMontoEnvioHaberes(BigDecimal bdMontoEnvioHaberes) {
		this.bdMontoEnvioHaberes = bdMontoEnvioHaberes;
	}
	public BigDecimal getBdMontoEnvioIncentivos() {
		return bdMontoEnvioIncentivos;
	}
	public void setBdMontoEnvioIncentivos(BigDecimal bdMontoEnvioIncentivos) {
		this.bdMontoEnvioIncentivos = bdMontoEnvioIncentivos;
	}
	public BigDecimal getBdMontoEnvioCas() {
		return bdMontoEnvioCas;
	}
	public void setBdMontoEnvioCas(BigDecimal bdMontoEnvioCas) {
		this.bdMontoEnvioCas = bdMontoEnvioCas;
	}
	public BigDecimal getBdMontoTotalEnviado() {
		return bdMontoTotalEnviado;
	}
	public void setBdMontoTotalEnviado(BigDecimal bdMontoTotalEnviado) {
		this.bdMontoTotalEnviado = bdMontoTotalEnviado;
	}
	public BigDecimal getBdMontoEfectuadoHaberes() {
		return bdMontoEfectuadoHaberes;
	}
	public void setBdMontoEfectuadoHaberes(BigDecimal bdMontoEfectuadoHaberes) {
		this.bdMontoEfectuadoHaberes = bdMontoEfectuadoHaberes;
	}
	public BigDecimal getBdMontoEfectuadoIncentivos() {
		return bdMontoEfectuadoIncentivos;
	}
	public void setBdMontoEfectuadoIncentivos(BigDecimal bdMontoEfectuadoIncentivos) {
		this.bdMontoEfectuadoIncentivos = bdMontoEfectuadoIncentivos;
	}
	public BigDecimal getBdMontoEfectuadoCas() {
		return bdMontoEfectuadoCas;
	}
	public void setBdMontoEfectuadoCas(BigDecimal bdMontoEfectuadoCas) {
		this.bdMontoEfectuadoCas = bdMontoEfectuadoCas;
	}
	public BigDecimal getBdMontoTotalEfectuado() {
		return bdMontoTotalEfectuado;
	}
	public void setBdMontoTotalEfectuado(BigDecimal bdMontoTotalEfectuado) {
		this.bdMontoTotalEfectuado = bdMontoTotalEfectuado;
	}
	public BigDecimal getBdMontoTotalDiferencia() {
		return bdMontoTotalDiferencia;
	}
	public void setBdMontoTotalDiferencia(BigDecimal bdMontoTotalDiferencia) {
		this.bdMontoTotalDiferencia = bdMontoTotalDiferencia;
	}
	public String getStrEstadoPagoHaberes() {
		return strEstadoPagoHaberes;
	}
	public void setStrEstadoPagoHaberes(String strEstadoPagoHaberes) {
		this.strEstadoPagoHaberes = strEstadoPagoHaberes;
	}
	public String getStrEstadoPagoIncentivos() {
		return strEstadoPagoIncentivos;
	}
	public void setStrEstadoPagoIncentivos(String strEstadoPagoIncentivos) {
		this.strEstadoPagoIncentivos = strEstadoPagoIncentivos;
	}
	public String getStrEstadoPagoCas() {
		return strEstadoPagoCas;
	}
	public void setStrEstadoPagoCas(String strEstadoPagoCas) {
		this.strEstadoPagoCas = strEstadoPagoCas;
	}
	public Integer getIntCtaConcepto() {
		return intCtaConcepto;
	}
	public void setIntCtaConcepto(Integer intCtaConcepto) {
		this.intCtaConcepto = intCtaConcepto;
	}
	public String getStrExpediente() {
		return strExpediente;
	}
	public void setStrExpediente(String strExpediente) {
		this.strExpediente = strExpediente;
	}
	public BigDecimal getBdMontoEnviado() {
		return bdMontoEnviado;
	}
	public void setBdMontoEnviado(BigDecimal bdMontoEnviado) {
		this.bdMontoEnviado = bdMontoEnviado;
	}
	public BigDecimal getBdMontoEfectuado() {
		return bdMontoEfectuado;
	}
	public void setBdMontoEfectuado(BigDecimal bdMontoEfectuado) {
		this.bdMontoEfectuado = bdMontoEfectuado;
	}
	public Integer getIntCodigoPrioridad() {
		return intCodigoPrioridad;
	}
	public void setIntCodigoPrioridad(Integer intCodigoPrioridad) {
		this.intCodigoPrioridad = intCodigoPrioridad;
	}
	public Integer getIntOrdenPrioridad() {
		return intOrdenPrioridad;
	}
	public void setIntOrdenPrioridad(Integer intOrdenPrioridad) {
		this.intOrdenPrioridad = intOrdenPrioridad;
	}
	public BigDecimal[] getBdMontoDiferencia() {
		return bdMontoDiferencia;
	}
	public void setBdMontoDiferencia(BigDecimal[] bdMontoDiferencia) {
		this.bdMontoDiferencia = bdMontoDiferencia;
	}
	public String getStrPeriodo() {
		return strPeriodo;
	}
	public void setStrPeriodo(String strPeriodo) {
		this.strPeriodo = strPeriodo;
	}
	public BigDecimal getBdSumaDiferenciaPlanilla() {
		return bdSumaDiferenciaPlanilla;
	}
	public void setBdSumaDiferenciaPlanilla(BigDecimal bdSumaDiferenciaPlanilla) {
		this.bdSumaDiferenciaPlanilla = bdSumaDiferenciaPlanilla;
	}
	public String getStrDescripcionDiferencia() {
		return strDescripcionDiferencia;
	}
	public void setStrDescripcionDiferencia(String strDescripcionDiferencia) {
		this.strDescripcionDiferencia = strDescripcionDiferencia;
	}
	public Integer getIntTipoConceptoGral() {
		return intTipoConceptoGral;
	}
	public void setIntTipoConceptoGral(Integer intTipoConceptoGral) {
		this.intTipoConceptoGral = intTipoConceptoGral;
	}
}
