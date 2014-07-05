package pe.com.tumi.tesoreria.ingreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class IngresoId extends TumiDomain{

	private Integer intIdEmpresa;
	private Integer intIdIngresoGeneral;
	
	
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
	
	@Override
	public String toString() {
		return "IngresoId [intIdEmpresa=" + intIdEmpresa
				+ ", intIdIngresoGeneral=" + intIdIngresoGeneral + "]";
	}
}
