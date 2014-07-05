package pe.com.tumi.tesoreria.ingreso.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DepositoIngreso extends TumiDomain{

	private DepositoIngresoId	id;
	private Integer intIdEmpresa;
	private Integer intIdIngresoGeneral;
	private BigDecimal bdMontoCancelado;
	
	private Ingreso	ingreso;
	
	public DepositoIngreso(){
		id = new DepositoIngresoId();
	}
	
	public DepositoIngresoId getId() {
		return id;
	}
	public void setId(DepositoIngresoId id) {
		this.id = id;
	}
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Integer getIntIdIngresoGeneral() {
		return intIdIngresoGeneral;
	}
	public void setIntIdIngresoGeneral(Integer intIdIngresoGeneral) {
		this.intIdIngresoGeneral = intIdIngresoGeneral;
	}
	public BigDecimal getBdMontoCancelado() {
		return bdMontoCancelado;
	}
	public void setBdMontoCancelado(BigDecimal bdMontoCancelado) {
		this.bdMontoCancelado = bdMontoCancelado;
	}
	public Ingreso getIngreso() {
		return ingreso;
	}

	public void setIngreso(Ingreso ingreso) {
		this.ingreso = ingreso;
	}
	@Override
	public String toString() {
		return "DepositoIngreso [id=" + id + ", intIdEmpresa=" + intIdEmpresa
				+ ", intIdIngresoGeneral=" + intIdIngresoGeneral
				+ ", bdMontoCancelado=" + bdMontoCancelado + "]";
	}
	
}