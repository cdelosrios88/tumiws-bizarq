package pe.com.tumi.servicio.prevision.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ExpedienteLiquidacionDetalleId extends TumiDomain{

	private Integer intPersEmpresaLiquidacion;
	private Integer intItemExpediente;
	private Integer intPersEmpresa;
	private Integer intCuenta;
	private Integer intItemCuentaConcepto;
	
	
	public Integer getIntPersEmpresaLiquidacion() {
		return intPersEmpresaLiquidacion;
	}
	public void setIntPersEmpresaLiquidacion(Integer intPersEmpresaLiquidacion) {
		this.intPersEmpresaLiquidacion = intPersEmpresaLiquidacion;
	}
	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}
	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntCuenta() {
		return intCuenta;
	}
	public void setIntCuenta(Integer intCuenta) {
		this.intCuenta = intCuenta;
	}
	public Integer getIntItemCuentaConcepto() {
		return intItemCuentaConcepto;
	}
	public void setIntItemCuentaConcepto(Integer intItemCuentaConcepto) {
		this.intItemCuentaConcepto = intItemCuentaConcepto;
	}
	@Override
	public String toString() {
		return "ExpedienteLiquidacionDetalleId [intPersEmpresaLiquidacion="
				+ intPersEmpresaLiquidacion + ", intItemExpediente="
				+ intItemExpediente + ", intPersEmpresa=" + intPersEmpresa
				+ ", intCuenta=" + intCuenta + ", intItemCuentaConcepto="
				+ intItemCuentaConcepto + "]";
	}
	
	
	
}