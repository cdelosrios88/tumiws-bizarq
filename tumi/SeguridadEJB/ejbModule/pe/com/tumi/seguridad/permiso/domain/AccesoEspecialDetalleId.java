package pe.com.tumi.seguridad.permiso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AccesoEspecialDetalleId extends TumiDomain{

	private Integer intItemAcceso;
	private Integer	intIdDiaSemana;
	public Integer getIntItemAcceso() {
		return intItemAcceso;
	}
	public void setIntItemAcceso(Integer intItemAcceso) {
		this.intItemAcceso = intItemAcceso;
	}
	public Integer getIntIdDiaSemana() {
		return intIdDiaSemana;
	}
	public void setIntIdDiaSemana(Integer intIdDiaSemana) {
		this.intIdDiaSemana = intIdDiaSemana;
	}
	@Override
	public String toString() {
		return "AccesoEspecialDetalleId [intItemAcceso=" + intItemAcceso
				+ ", intIdDiaSemana=" + intIdDiaSemana + "]";
	}
	
}
