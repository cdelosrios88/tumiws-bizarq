package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class OrdenCompraId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intItemOrdenCompra;
	
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemOrdenCompra() {
		return intItemOrdenCompra;
	}
	public void setIntItemOrdenCompra(Integer intItemOrdenCompra) {
		this.intItemOrdenCompra = intItemOrdenCompra;
	}
	@Override
	public String toString() {
		return "OrdenCompraId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemOrdenCompra=" + intItemOrdenCompra + "]";
	}
}
