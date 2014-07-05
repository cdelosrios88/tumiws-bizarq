package pe.com.tumi.credito.socio.creditos.domain;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoExcepcion extends TumiDomain {
	private CreditoExcepcionId 	id;
	private BigDecimal			bdTasaSobreGiro;
	private Integer				intPeriodoGracia;
	private Integer				intParaPeriodicidadGraciaCod;
	private Integer				intParaCampoDobleCuotaCod;
	private Integer				intIndicadorDobleCuota;
	private Integer				intPrimerCredito;
	private Integer				intPrimerCreditoEnvioPlla;
	private BigDecimal				intPorcentajeLimiteCuota;
	private Integer				intCampoLimiteCuota;
	private BigDecimal			bdPorcentajeNoTrans;
	private Integer				intParaConceptoNoTrans;
	private Integer				intEdadLimite;
	private Integer				intParaTipoMaxMin;
	private BigDecimal			bdImporteEvaluacion;
	private Integer				intDiasTolerancia;
	private Boolean				boAplicaCastigadas;
	private Integer				intAplicaCastigadas;
	
	private List<CreditoExcepcionAporteNoTrans> listaAporteNoTrans;
	private List<CreditoExcepcionDiasCobranza> listaDiasCobranza;
	
	public CreditoExcepcionId getId() {
		return id;
	}
	public void setId(CreditoExcepcionId id) {
		this.id = id;
	}
	public BigDecimal getBdTasaSobreGiro() {
		return bdTasaSobreGiro;
	}
	public void setBdTasaSobreGiro(BigDecimal bdTasaSobreGiro) {
		this.bdTasaSobreGiro = bdTasaSobreGiro;
	}
	public Integer getIntPeriodoGracia() {
		return intPeriodoGracia;
	}
	public void setIntPeriodoGracia(Integer intPeriodoGracia) {
		this.intPeriodoGracia = intPeriodoGracia;
	}
	public Integer getIntParaPeriodicidadGraciaCod() {
		return intParaPeriodicidadGraciaCod;
	}
	public void setIntParaPeriodicidadGraciaCod(Integer intParaPeriodicidadGraciaCod) {
		this.intParaPeriodicidadGraciaCod = intParaPeriodicidadGraciaCod;
	}
	public Integer getIntParaCampoDobleCuotaCod() {
		return intParaCampoDobleCuotaCod;
	}
	public void setIntParaCampoDobleCuotaCod(Integer intParaCampoDobleCuotaCod) {
		this.intParaCampoDobleCuotaCod = intParaCampoDobleCuotaCod;
	}
	public Integer getIntIndicadorDobleCuota() {
		return intIndicadorDobleCuota;
	}
	public void setIntIndicadorDobleCuota(Integer intIndicadorDobleCuota) {
		this.intIndicadorDobleCuota = intIndicadorDobleCuota;
	}
	public Integer getIntPrimerCredito() {
		return intPrimerCredito;
	}
	public void setIntPrimerCredito(Integer intPrimerCredito) {
		this.intPrimerCredito = intPrimerCredito;
	}
	public Integer getIntPrimerCreditoEnvioPlla() {
		return intPrimerCreditoEnvioPlla;
	}
	public void setIntPrimerCreditoEnvioPlla(Integer intPrimerCreditoEnvioPlla) {
		this.intPrimerCreditoEnvioPlla = intPrimerCreditoEnvioPlla;
	}
	public BigDecimal getIntPorcentajeLimiteCuota() {
		return intPorcentajeLimiteCuota;
	}
	public void setIntPorcentajeLimiteCuota(BigDecimal intPorcentajeLimiteCuota) {
		this.intPorcentajeLimiteCuota = intPorcentajeLimiteCuota;
	}
	public Integer getIntCampoLimiteCuota() {
		return intCampoLimiteCuota;
	}
	public void setIntCampoLimiteCuota(Integer intCampoLimiteCuota) {
		this.intCampoLimiteCuota = intCampoLimiteCuota;
	}
	public BigDecimal getBdPorcentajeNoTrans() {
		return bdPorcentajeNoTrans;
	}
	public void setBdPorcentajeNoTrans(BigDecimal bdPorcentajeNoTrans) {
		this.bdPorcentajeNoTrans = bdPorcentajeNoTrans;
	}
	public Integer getIntParaConceptoNoTrans() {
		return intParaConceptoNoTrans;
	}
	public void setIntParaConceptoNoTrans(Integer intParaConceptoNoTrans) {
		this.intParaConceptoNoTrans = intParaConceptoNoTrans;
	}
	public Integer getIntEdadLimite() {
		return intEdadLimite;
	}
	public void setIntEdadLimite(Integer intEdadLimite) {
		this.intEdadLimite = intEdadLimite;
	}
	public Integer getIntParaTipoMaxMin() {
		return intParaTipoMaxMin;
	}
	public void setIntParaTipoMaxMin(Integer intParaTipoMaxMin) {
		this.intParaTipoMaxMin = intParaTipoMaxMin;
	}
	public BigDecimal getBdImporteEvaluacion() {
		return bdImporteEvaluacion;
	}
	public void setBdImporteEvaluacion(BigDecimal bdImporteEvaluacion) {
		this.bdImporteEvaluacion = bdImporteEvaluacion;
	}
	public Integer getIntDiasTolerancia() {
		return intDiasTolerancia;
	}
	public void setIntDiasTolerancia(Integer intDiasTolerancia) {
		this.intDiasTolerancia = intDiasTolerancia;
	}
	public Boolean getBoAplicaCastigadas() {
		return boAplicaCastigadas;
	}
	public void setBoAplicaCastigadas(Boolean boAplicaCastigadas) {
		this.boAplicaCastigadas = boAplicaCastigadas;
	}
	public Integer getIntAplicaCastigadas() {
		return intAplicaCastigadas;
	}
	public void setIntAplicaCastigadas(Integer intAplicaCastigadas) {
		this.intAplicaCastigadas = intAplicaCastigadas;
	}
	public List<CreditoExcepcionAporteNoTrans> getListaAporteNoTrans() {
		return listaAporteNoTrans;
	}
	public void setListaAporteNoTrans(
			List<CreditoExcepcionAporteNoTrans> listaAporteNoTrans) {
		this.listaAporteNoTrans = listaAporteNoTrans;
	}
	public List<CreditoExcepcionDiasCobranza> getListaDiasCobranza() {
		return listaDiasCobranza;
	}
	public void setListaDiasCobranza(
			List<CreditoExcepcionDiasCobranza> listaDiasCobranza) {
		this.listaDiasCobranza = listaDiasCobranza;
	}
}
