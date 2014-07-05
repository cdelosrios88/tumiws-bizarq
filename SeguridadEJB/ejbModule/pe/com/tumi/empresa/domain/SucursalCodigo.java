package pe.com.tumi.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SucursalCodigo extends TumiDomain{
	private Sucursal	sucursal;
	private Integer		intIdTipoCodigo;
	private String		strIdCodigo;
	private Integer		intEstadoCod;
	private Boolean 	chkCodigoTercero;
	
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Integer getIntIdTipoCodigo() {
		return intIdTipoCodigo;
	}
	public void setIntIdTipoCodigo(Integer intIdTipoCodigo) {
		this.intIdTipoCodigo = intIdTipoCodigo;
	}
	public String getStrIdCodigo() {
		return strIdCodigo;
	}
	public void setStrIdCodigo(String strIdCodigo) {
		this.strIdCodigo = strIdCodigo;
	}
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public Boolean getChkCodigoTercero() {
		return chkCodigoTercero;
	}
	public void setChkCodigoTercero(Boolean chkCodigoTercero) {
		this.chkCodigoTercero = chkCodigoTercero;
	}
}
