package pe.com.tumi.creditos.domain;

public class CondSocio {
	private Integer		intIdEmpresa;
	private Integer		intIdTipoCaptacion;
	private Integer		intIdCodigo;
	private Integer		intIdCondSocio;
	private Integer		intIdValor;
	private String		strDescripcion;
	private Boolean		chkSocio;
	
	//Getters y Setters
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Integer getIntIdTipoCaptacion() {
		return intIdTipoCaptacion;
	}
	public void setIntIdTipoCaptacion(Integer intIdTipoCaptacion) {
		this.intIdTipoCaptacion = intIdTipoCaptacion;
	}
	public Integer getIntIdCodigo() {
		return intIdCodigo;
	}
	public void setIntIdCodigo(Integer intIdCodigo) {
		this.intIdCodigo = intIdCodigo;
	}
	public Integer getIntIdCondSocio() {
		return intIdCondSocio;
	}
	public void setIntIdCondSocio(Integer intIdCondSocio) {
		this.intIdCondSocio = intIdCondSocio;
	}
	public Integer getIntIdValor() {
		return intIdValor;
	}
	public void setIntIdValor(Integer intIdValor) {
		this.intIdValor = intIdValor;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Boolean getChkSocio() {
		return chkSocio;
	}
	public void setChkSocio(Boolean chkSocio) {
		this.chkSocio = chkSocio;
	}
}
