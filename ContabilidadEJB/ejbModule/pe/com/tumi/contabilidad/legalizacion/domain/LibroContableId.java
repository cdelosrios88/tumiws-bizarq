package pe.com.tumi.contabilidad.legalizacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

@SuppressWarnings("serial")
public class LibroContableId extends TumiDomain {

 	private Integer intEmpresaPk;
 	private Integer intLibroContable;
 	
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
	
	@Override
	public String toString() {
		return "LibroContableId [intEmpresaPk=" + intEmpresaPk
				+ ", intLibroContable=" + intLibroContable + "]";
	}
}
