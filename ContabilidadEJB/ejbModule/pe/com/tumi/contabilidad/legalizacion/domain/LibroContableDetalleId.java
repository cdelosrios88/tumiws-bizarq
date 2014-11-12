package pe.com.tumi.contabilidad.legalizacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

@SuppressWarnings("serial")
public class LibroContableDetalleId extends TumiDomain {
	private Integer intEmpresaPk;
	private Integer intLibroContable;
	private Integer intItemLibroContableDet;
	
	public Integer getIntEmpresaPk() {
		return intEmpresaPk;
	}
	public void setIntEmpresaPk(Integer intEmpresaPk) {
		this.intEmpresaPk = intEmpresaPk;
	}
	public Integer getIntLibroContable() {
		return intLibroContable;
	}
	public void setIntLibroContable(Integer intLibroContable) {
		this.intLibroContable = intLibroContable;
	}
	public Integer getIntItemLibroContableDet() {
		return intItemLibroContableDet;
	}
	public void setIntItemLibroContableDet(Integer intItemLibroContableDet) {
		this.intItemLibroContableDet = intItemLibroContableDet;
	}

	@Override
	public String toString() {
		return "LibroContableDetalleId [intEmpresaPk=" + intEmpresaPk
				+ ", intLibroContable=" + intLibroContable
				+ ", intItemLibroContableDet=" + intItemLibroContableDet + "]";
	}

}
