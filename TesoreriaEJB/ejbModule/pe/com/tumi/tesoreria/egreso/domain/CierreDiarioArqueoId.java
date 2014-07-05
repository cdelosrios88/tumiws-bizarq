package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CierreDiarioArqueoId extends TumiDomain{

	private Integer 	intPersEmpresa;
	private Integer 	intParaTipoArqueo;
	private Integer		intSucuIdSucursal;
	private Integer		intSudeIdSubsucursal;
	private Integer		intItemCierreDiarioArqueo;
	
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntParaTipoArqueo() {
		return intParaTipoArqueo;
	}
	public void setIntParaTipoArqueo(Integer intParaTipoArqueo) {
		this.intParaTipoArqueo = intParaTipoArqueo;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSudeIdSubsucursal() {
		return intSudeIdSubsucursal;
	}
	public void setIntSudeIdSubsucursal(Integer intSudeIdSubsucursal) {
		this.intSudeIdSubsucursal = intSudeIdSubsucursal;
	}
	public Integer getIntItemCierreDiarioArqueo() {
		return intItemCierreDiarioArqueo;
	}
	public void setIntItemCierreDiarioArqueo(Integer intItemCierreDiarioArqueo) {
		this.intItemCierreDiarioArqueo = intItemCierreDiarioArqueo;
	}
	
	@Override
	public String toString() {
		return "CierreDiarioArqueoId [intPersEmpresa=" + intPersEmpresa
				+ ", intParaTipoArqueo=" + intParaTipoArqueo
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdSubsucursal=" + intSudeIdSubsucursal
				+ ", intItemCierreDiarioArqueo=" + intItemCierreDiarioArqueo
				+ "]";
	}	
}