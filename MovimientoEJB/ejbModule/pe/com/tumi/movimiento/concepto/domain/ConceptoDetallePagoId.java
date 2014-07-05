package pe.com.tumi.movimiento.concepto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConceptoDetallePagoId extends TumiDomain{
	private Integer intPersEmpresaPk;
	private Integer intCuentaPk;
	private Integer intItemCuentaConcepto; 
	private Integer intItemCtaCptoDet; 
	private Integer intItemConceptoPago; 
	private Integer intItemConceptoDetPago;
	private Integer intItemMovCtaCte;
	
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntCuentaPk() {
		return intCuentaPk;
	}
	public void setIntCuentaPk(Integer intCuentaPk) {
		this.intCuentaPk = intCuentaPk;
	}
	public Integer getIntItemCuentaConcepto() {
		return intItemCuentaConcepto;
	}
	public void setIntItemCuentaConcepto(Integer intItemCuentaConcepto) {
		this.intItemCuentaConcepto = intItemCuentaConcepto;
	}
	public Integer getIntItemCtaCptoDet() {
		return intItemCtaCptoDet;
	}
	public void setIntItemCtaCptoDet(Integer intItemCtaCptoDet) {
		this.intItemCtaCptoDet = intItemCtaCptoDet;
	}
	public Integer getIntItemConceptoPago() {
		return intItemConceptoPago;
	}
	public void setIntItemConceptoPago(Integer intItemConceptoPago) {
		this.intItemConceptoPago = intItemConceptoPago;
	}
	public Integer getIntItemConceptoDetPago() {
		return intItemConceptoDetPago;
	}
	public void setIntItemConceptoDetPago(Integer intItemConceptoDetPago) {
		this.intItemConceptoDetPago = intItemConceptoDetPago;
	}
	public Integer getIntItemMovCtaCte() {
		return intItemMovCtaCte;
	}
	public void setIntItemMovCtaCte(Integer intItemMovCtaCte) {
		this.intItemMovCtaCte = intItemMovCtaCte;
	} 
		
}
