package pe.com.tumi.estadoCuenta.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DataBeanEstadoCuentaDsctoTerceros extends TumiDomain {
	private Integer intAnio;
	private Integer intMes;
	private Integer intParaModalidadCod;
	private String strPeriodo; //Año y mes concatenados
	private String strDsteCpto;
	private String strNomCpto;
	private Integer intMonto;
	private String strNroDocIden;
	
	
	private String[] strMontoDscto;
	private BigDecimal bdSumMontoDscto;
	
	public Integer getIntAnio() {
		return intAnio;
	}
	public void setIntAnio(Integer intAnio) {
		this.intAnio = intAnio;
	}
	public Integer getIntMes() {
		return intMes;
	}
	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}
	public Integer getIntParaModalidadCod() {
		return intParaModalidadCod;
	}
	public void setIntParaModalidadCod(Integer intParaModalidadCod) {
		this.intParaModalidadCod = intParaModalidadCod;
	}
	public String getStrPeriodo() {
		return strPeriodo;
	}
	public void setStrPeriodo(String strPeriodo) {
		this.strPeriodo = strPeriodo;
	}
	public String getStrDsteCpto() {
		return strDsteCpto;
	}
	public void setStrDsteCpto(String strDsteCpto) {
		this.strDsteCpto = strDsteCpto;
	}
	public Integer getIntMonto() {
		return intMonto;
	}
	public void setIntMonto(Integer intMonto) {
		this.intMonto = intMonto;
	}
	public String getStrNroDocIden() {
		return strNroDocIden;
	}
	public void setStrNroDocIden(String strNroDocIden) {
		this.strNroDocIden = strNroDocIden;
	}
	public String[] getStrMontoDscto() {
		return strMontoDscto;
	}
	public void setStrMontoDscto(String[] strMontoDscto) {
		this.strMontoDscto = strMontoDscto;
	}
	public BigDecimal getBdSumMontoDscto() {
		return bdSumMontoDscto;
	}
	public void setBdSumMontoDscto(BigDecimal bdSumMontoDscto) {
		this.bdSumMontoDscto = bdSumMontoDscto;
	}
	public String getStrNomCpto() {
		return strNomCpto;
	}
	public void setStrNomCpto(String strNomCpto) {
		this.strNomCpto = strNomCpto;
	}
}
