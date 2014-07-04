package pe.com.tumi.movimiento.concepto.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConceptoDetallePago extends TumiDomain{
	private ConceptoDetallePagoId id;
	private Integer intTipoCargoAbono;
	private BigDecimal bdMonto;
	
	public ConceptoDetallePagoId getId() {
		return id;
	}
	public void setId(ConceptoDetallePagoId id) {
		this.id = id;
	}
	public Integer getIntTipoCargoAbono() {
		return intTipoCargoAbono;
	}
	public void setIntTipoCargoAbono(Integer intTipoCargoAbono) {
		this.intTipoCargoAbono = intTipoCargoAbono;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	
}
