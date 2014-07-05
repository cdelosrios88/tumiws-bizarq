package pe.com.tumi.movimiento.concepto.domain;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConceptoPago extends TumiDomain {
	
	private ConceptoPagoId id;
	private Integer intPeriodo; // ??????  modelo Timestamp
	private BigDecimal bdMontoPago;
	private BigDecimal bdMontoSaldo;
	private Integer intEstadoCod;
	private List<ConceptoDetallePago> listaConceptoDetallePago;
	
	public ConceptoPagoId getId() {
		return id;
	}
	public void setId(ConceptoPagoId id) {
		this.id = id;
	}
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}
	public BigDecimal getBdMontoPago() {
		return bdMontoPago;
	}
	public void setBdMontoPago(BigDecimal bdMontoPago) {
		this.bdMontoPago = bdMontoPago;
	}
	public BigDecimal getBdMontoSaldo() {
		return bdMontoSaldo;
	}
	public void setBdMontoSaldo(BigDecimal bdMontoSaldo) {
		this.bdMontoSaldo = bdMontoSaldo;
	}
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public List<ConceptoDetallePago> getListaConceptoDetallePago() {
		return listaConceptoDetallePago;
	}
	public void setListaConceptoDetallePago(
			List<ConceptoDetallePago> listaConceptoDetallePago) {
		this.listaConceptoDetallePago = listaConceptoDetallePago;
	}
	
}
