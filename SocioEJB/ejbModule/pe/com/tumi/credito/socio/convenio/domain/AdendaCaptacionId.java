package pe.com.tumi.credito.socio.convenio.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AdendaCaptacionId extends TumiDomain {
	private Integer intConvenio;
	private Integer intItemConvenio;
	private Integer intPersEmpresaPk;
	private Integer intParaTipoCaptacionCod;
	private Integer intItem;
	
	public Integer getIntConvenio() {
		return intConvenio;
	}
	public void setIntConvenio(Integer intConvenio) {
		this.intConvenio = intConvenio;
	}
	public Integer getIntItemConvenio() {
		return intItemConvenio;
	}
	public void setIntItemConvenio(Integer intItemConvenio) {
		this.intItemConvenio = intItemConvenio;
	}
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
}
