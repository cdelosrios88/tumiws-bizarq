package pe.com.tumi.contabilidad.operaciones.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class HojaManualId extends TumiDomain {
	private Integer intPersEmpresaHojaPk;
	private Integer intContPeriodoHoja;
	private Integer intContCodigoHoja;
	
	
	public Integer getIntPersEmpresaHojaPk() {
		return intPersEmpresaHojaPk;
	}
	public void setIntPersEmpresaHojaPk(Integer intPersEmpresaHojaPk) {
		this.intPersEmpresaHojaPk = intPersEmpresaHojaPk;
	}
	public Integer getIntContPeriodoHoja() {
		return intContPeriodoHoja;
	}
	public void setIntContPeriodoHoja(Integer intContPeriodoHoja) {
		this.intContPeriodoHoja = intContPeriodoHoja;
	}
	public Integer getIntContCodigoHoja() {
		return intContCodigoHoja;
	}
	public void setIntContCodigoHoja(Integer intContCodigoHoja) {
		this.intContCodigoHoja = intContCodigoHoja;
	}
}
