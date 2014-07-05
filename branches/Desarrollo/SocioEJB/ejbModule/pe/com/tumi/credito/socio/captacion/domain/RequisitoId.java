package pe.com.tumi.credito.socio.captacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RequisitoId extends TumiDomain {

	private Integer intPersEmpresaPk;
	private Integer intParaTipoCaptacionCod;
	private Integer intItem;
	private Integer intParaTipoRequisitoBenef;
	private Integer intItemRequisito;
	
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntParaTipoCaptacionCod() {
		return intParaTipoCaptacionCod;
	}
	public void setIntParaTipoCaptacionCod(Integer intParaTipoCaptacionCod) {
		this.intParaTipoCaptacionCod = intParaTipoCaptacionCod;
	}
	public Integer getIntItem() {
		return intItem;
	}
	public void setIntItem(Integer intItem) {
		this.intItem = intItem;
	}
	public Integer getIntParaTipoRequisitoBenef() {
		return intParaTipoRequisitoBenef;
	}
	public void setIntParaTipoRequisitoBenef(Integer intParaTipoRequisitoBenef) {
		this.intParaTipoRequisitoBenef = intParaTipoRequisitoBenef;
	}
	public Integer getIntItemRequisito() {
		return intItemRequisito;
	}
	public void setIntItemRequisito(Integer intItemRequisito) {
		this.intItemRequisito = intItemRequisito;
	}

}
