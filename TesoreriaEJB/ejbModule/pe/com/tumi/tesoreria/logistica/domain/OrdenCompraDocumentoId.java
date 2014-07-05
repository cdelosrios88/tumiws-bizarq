package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class OrdenCompraDocumentoId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intItemOrdenCompra;
	private Integer intItemOrdenCompraDocumento;
	
	
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
	public Integer getIntItemOrdenCompraDocumento() {
		return intItemOrdenCompraDocumento;
	}
	public void setIntItemOrdenCompraDocumento(Integer intItemOrdenCompraDocumento) {
		this.intItemOrdenCompraDocumento = intItemOrdenCompraDocumento;
	}
	
	public boolean areEqual(OrdenCompraDocumentoId ordenCompraDocumentoId){
		if(this.intPersEmpresa.equals(ordenCompraDocumentoId.getIntPersEmpresa())
		&& this.intItemOrdenCompra.equals(ordenCompraDocumentoId.getIntItemOrdenCompra())
		&& this.intItemOrdenCompraDocumento.equals(ordenCompraDocumentoId.getIntItemOrdenCompraDocumento())){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@Override
	public String toString() {
		return "OrdenCompraDocumentoId [intPersEmpresa=" + intPersEmpresa
				+ ", intItemOrdenCompra=" + intItemOrdenCompra
				+ ", intItemOrdenCompraDocumento="
				+ intItemOrdenCompraDocumento + "]";
	}
	
	
}
