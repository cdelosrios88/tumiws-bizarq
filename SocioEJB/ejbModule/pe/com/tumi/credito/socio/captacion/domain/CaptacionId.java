package pe.com.tumi.credito.socio.captacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CaptacionId extends TumiDomain {

	private Integer intPersEmpresaPk;
	private Integer intParaTipoCaptacionCod;
	private Integer intItem;
	
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
