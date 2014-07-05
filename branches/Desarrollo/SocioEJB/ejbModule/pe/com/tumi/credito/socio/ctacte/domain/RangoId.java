package pe.com.tumi.credito.socio.ctacte.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RangoId extends TumiDomain {

	private Integer intPersEmpresaPk;
	private Integer intParaTipoCtacteCod;
	private Integer intItemCtacte;
	private Integer intItem;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntParaTipoCtacteCod() {
		return intParaTipoCtacteCod;
	}
	public void setIntParaTipoCtacteCod(Integer intParaTipoCtacteCod) {
		this.intParaTipoCtacteCod = intParaTipoCtacteCod;
	}
	public Integer getIntItemCtacte() {
		return intItemCtacte;
	}
	public void setIntItemCtacte(Integer intItemCtacte) {
		this.intItemCtacte = intItemCtacte;
	}
	public Integer getIntItem() {
		return intItem;
	}
	public void setIntItem(Integer intItem) {
		this.intItem = intItem;
	}

}
