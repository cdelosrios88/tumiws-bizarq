package pe.com.tumi.credito.socio.ctacte.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Rango extends TumiDomain {

	private RangoId id;
	private CtaCte ctaCte;
	private Integer intparaTipoRangoTasaCod;
	private BigDecimal bdPorcentaje;
	private Integer intMesTope;
	private BigDecimal bdMontoTope;
	
	public RangoId getId() {
		return id;
	}
	public void setId(RangoId id) {
		this.id = id;
	}
	public CtaCte getCtaCte() {
		return ctaCte;
	}
	public void setCtaCte(CtaCte ctaCte) {
		this.ctaCte = ctaCte;
	}
	public Integer getIntparaTipoRangoTasaCod() {
		return intparaTipoRangoTasaCod;
	}
	public void setIntparaTipoRangoTasaCod(Integer intparaTipoRangoTasaCod) {
		this.intparaTipoRangoTasaCod = intparaTipoRangoTasaCod;
	}
	public BigDecimal getBdPorcentaje() {
		return bdPorcentaje;
	}
	public void setBdPorcentaje(BigDecimal bdPorcentaje) {
		this.bdPorcentaje = bdPorcentaje;
	}
	public Integer getIntMesTope() {
		return intMesTope;
	}
	public void setIntMesTope(Integer intMesTope) {
		this.intMesTope = intMesTope;
	}
	public BigDecimal getBdMontoTope() {
		return bdMontoTope;
	}
	public void setBdMontoTope(BigDecimal bdMontoTope) {
		this.bdMontoTope = bdMontoTope;
	}
}
