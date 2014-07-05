package pe.com.tumi.tesoreria.egreso.domain;

import java.util.Date;

import pe.com.tumi.common.util.AjusteMonto;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SaldoId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intSucuIdSucursal;
	private Integer intSudeIdSucursal;
	private Date 	dtFechaSaldo;
	private	Integer	intItemSaldo;
	
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSudeIdSucursal() {
		return intSudeIdSucursal;
	}
	public void setIntSudeIdSucursal(Integer intSudeIdSucursal) {
		this.intSudeIdSucursal = intSudeIdSucursal;
	}
	public Date getDtFechaSaldo() {
		return dtFechaSaldo;
	}
	public void setDtFechaSaldo(Date dtFechaSaldo) {
		this.dtFechaSaldo = dtFechaSaldo;
	}
	public Integer getIntItemSaldo() {
		return intItemSaldo;
	}
	public void setIntItemSaldo(Integer intItemSaldo) {
		this.intItemSaldo = intItemSaldo;
	}
	@Override
	public String toString() {
		return "SaldoId [intPersEmpresa=" + intPersEmpresa
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdSucursal=" + intSudeIdSucursal
				+ ", dtFechaSaldo=" + AjusteMonto.formatoFecha(dtFechaSaldo) + ", intItemSaldo="
				+ intItemSaldo + "]";
	}
	
}