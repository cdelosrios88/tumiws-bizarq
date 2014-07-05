package pe.com.tumi.credito.socio.captacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class VinculoId extends TumiDomain{

	private Integer intEmpresaPk;
	private Integer paraTipoCaptacionCod;
	private Integer intItem;
	private Integer paraTipoVinculoCod;
	private Integer intItemVinculo;
	
	public Integer getIntEmpresaPk() {
		return intEmpresaPk;
	}
	public void setIntEmpresaPk(Integer intEmpresaPk) {
		this.intEmpresaPk = intEmpresaPk;
	}
	public Integer getParaTipoCaptacionCod() {
		return paraTipoCaptacionCod;
	}
	public void setParaTipoCaptacionCod(Integer paraTipoCaptacionCod) {
		this.paraTipoCaptacionCod = paraTipoCaptacionCod;
	}
	public Integer getIntItem() {
		return intItem;
	}
	public void setIntItem(Integer intItem) {
		this.intItem = intItem;
	}
	public Integer getParaTipoVinculoCod() {
		return paraTipoVinculoCod;
	}
	public void setParaTipoVinculoCod(Integer paraTipoVinculoCod) {
		this.paraTipoVinculoCod = paraTipoVinculoCod;
	}
	public Integer getIntItemVinculo() {
		return intItemVinculo;
	}
	public void setIntItemVinculo(Integer intItemVinculo) {
		this.intItemVinculo = intItemVinculo;
	}

}
