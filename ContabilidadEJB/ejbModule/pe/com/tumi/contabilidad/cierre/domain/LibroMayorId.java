package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class LibroMayorId extends TumiDomain{

	private Integer intPersEmpresaMayor;
	private Integer intContPeriodoMayor;
	private Integer intContMesMayor;
	public Integer getIntPersEmpresaMayor() {
		return intPersEmpresaMayor;
	}
	public void setIntPersEmpresaMayor(Integer intPersEmpresaMayor) {
		this.intPersEmpresaMayor = intPersEmpresaMayor;
	}
	public Integer getIntContPeriodoMayor() {
		return intContPeriodoMayor;
	}
	public void setIntContPeriodoMayor(Integer intContPeriodoMayor) {
		this.intContPeriodoMayor = intContPeriodoMayor;
	}
	public Integer getIntContMesMayor() {
		return intContMesMayor;
	}
	public void setIntContMesMayor(Integer intContCodigoMayor) {
		this.intContMesMayor = intContCodigoMayor;
	}
	@Override
	public String toString() {
		return "LibroMayorId [intPersEmpresaMayor=" + intPersEmpresaMayor
				+ ", intContPeriodoMayor=" + intContPeriodoMayor
				+ ", intContMesMayor=" + intContMesMayor + "]";
	}
}