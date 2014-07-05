package pe.com.tumi.seguridad.permiso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DiasAccesosId extends TumiDomain{

	private Integer intPersEmpresa;
	private Integer intIdTipoSucursal;
	private Integer intItemDiaAccesos;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntIdTipoSucursal() {
		return intIdTipoSucursal;
	}
	public void setIntIdTipoSucursal(Integer intIdTipoSucursal) {
		this.intIdTipoSucursal = intIdTipoSucursal;
	}
	public Integer getIntItemDiaAccesos() {
		return intItemDiaAccesos;
	}
	public void setIntItemDiaAccesos(Integer intItemDiaAccesos) {
		this.intItemDiaAccesos = intItemDiaAccesos;
	}
	@Override
	public String toString() {
		return "DiasAccesosId [intPersEmpresa=" + intPersEmpresa
				+ ", intIdTipoSucursal=" + intIdTipoSucursal
				+ ", intItemDiaAccesos=" + intItemDiaAccesos + "]";
	}
	
	
}
