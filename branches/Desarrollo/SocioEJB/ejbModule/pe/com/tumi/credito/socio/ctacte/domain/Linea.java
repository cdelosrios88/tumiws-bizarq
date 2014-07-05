package pe.com.tumi.credito.socio.ctacte.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Linea extends TumiDomain {

	private LineaId id;
	private CtaCte ctaCte;
	private BigDecimal bdMonto;
	private Integer intPorcentaje;
	private Integer intTipoCaptacionAfecto;
	
	public LineaId getId() {
		return id;
	}
	public void setId(LineaId id) {
		this.id = id;
	}
	public CtaCte getCtaCte() {
		return ctaCte;
	}
	public void setCtaCte(CtaCte ctaCte) {
		this.ctaCte = ctaCte;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public Integer getIntPorcentaje() {
		return intPorcentaje;
	}
	public void setIntPorcentaje(Integer intPorcentaje) {
		this.intPorcentaje = intPorcentaje;
	}
	public Integer getIntTipoCaptacionAfecto() {
		return intTipoCaptacionAfecto;
	}
	public void setIntTipoCaptacionAfecto(Integer intTipoCaptacionAfecto) {
		this.intTipoCaptacionAfecto = intTipoCaptacionAfecto;
	}
	
}
