package pe.com.tumi.seguridad.permiso.domain;

public class DiasAccesosDetalleId {

	private Integer intPersEmpresa;
	private Integer intIdTipoSucursal;
	private Integer intIdDiaSemana;
	private Integer intItemDiasAccesos;
	
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
	public Integer getIntIdDiaSemana() {
		return intIdDiaSemana;
	}
	public void setIntIdDiaSemana(Integer intIdDiaSemana) {
		this.intIdDiaSemana = intIdDiaSemana;
	}
	public Integer getIntItemDiasAccesos() {
		return intItemDiasAccesos;
	}
	public void setIntItemDiasAccesos(Integer intItemDiasAccesos) {
		this.intItemDiasAccesos = intItemDiasAccesos;
	}
	@Override
	public String toString() {
		return "DiasAccesosDetalleId [intPersEmpresa=" + intPersEmpresa
				+ ", intIdTipoSucursal=" + intIdTipoSucursal
				+ ", intIdDiaSemana=" + intIdDiaSemana
				+ ", intItemDiasAccesos=" + intItemDiasAccesos + "]";
	}
	
	
	
}
