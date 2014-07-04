package pe.com.tumi.credito.socio.estructura.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Descuento extends TumiDomain {

	private DescuentoId id;
	private String strDsteCpto;
	private String strDstePeriodo;
	private String strDsteNombre;
	private Integer intMonto;
	private String strTipoPla;
	private String strCodEje;
	private String strLibEle;
	private String strNomCpto;
	private String strPro;
	private String strProgSub;
	private String strCodEst;
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 17-09-2013
	private String[] strMontoDscto;
	private String strSumMontoDscto;
	private BigDecimal bdSumMontoDscto;

	public Descuento(){
		id = new DescuentoId();
	}
	public DescuentoId getId() {
		return id;
	}
	public void setId(DescuentoId id) {
		this.id = id;
	}
	public Integer getIntMonto() {
		return intMonto;
	}
	public void setIntMonto(Integer intMonto) {
		this.intMonto = intMonto;
	}
	public String getStrProgSub() {
		return strProgSub;
	}
	public void setStrProgSub(String strProgSub) {
		this.strProgSub = strProgSub;
	}
	public String getStrCodEst() {
		return strCodEst;
	}
	public void setStrCodEst(String strCodEst) {
		this.strCodEst = strCodEst;
	}
	public String getStrTipoPla() {
		return strTipoPla;
	}
	public void setStrTipoPla(String strTipoPla) {
		this.strTipoPla = strTipoPla;
	}
	public String getStrPro() {
		return strPro;
	}
	public void setStrPro(String strPro) {
		this.strPro = strPro;
	}
	public String getStrCodEje() {
		return strCodEje;
	}
	public void setStrCodEje(String strCodEje) {
		this.strCodEje = strCodEje;
	}
	public String getStrNomCpto() {
		return strNomCpto;
	}
	public void setStrNomCpto(String strNomCpto) {
		this.strNomCpto = strNomCpto;
	}
	public String getStrLibEle() {
		return strLibEle;
	}
	public void setStrLibEle(String strLibEle) {
		this.strLibEle = strLibEle;
	}
	public String getStrDsteCpto() {
		return strDsteCpto;
	}
	public void setStrDsteCpto(String strDsteCpto) {
		this.strDsteCpto = strDsteCpto;
	}
	public String getStrDstePeriodo() {
		return strDstePeriodo;
	}
	public void setStrDstePeriodo(String strDstePeriodo) {
		this.strDstePeriodo = strDstePeriodo;
	}
	public String getStrDsteNombre() {
		return strDsteNombre;
	}
	public void setStrDsteNombre(String strDsteNombre) {
		this.strDsteNombre = strDsteNombre;
	}
	@Override
	public String toString() {
		return "Descuento [id=" + id + ", strDsteCpto=" + strDsteCpto
				+ ", strDstePeriodo=" + strDstePeriodo + ", strDsteNombre="
				+ strDsteNombre + ", intMonto=" + intMonto + ", strProgSub="
				+ strProgSub + ", strCodEst=" + strCodEst + ", strTipoPla="
				+ strTipoPla + ", strPro=" + strPro + ", strCodEje="
				+ strCodEje + ", strLibEle=" + strLibEle + ", strNomCpto="
				+ strNomCpto + "]";
	}
	public String[] getStrMontoDscto() {
		return strMontoDscto;
	}
	public void setStrMontoDscto(String[] strMontoDscto) {
		this.strMontoDscto = strMontoDscto;
	}
	public String getStrSumMontoDscto() {
		return strSumMontoDscto;
	}
	public void setStrSumMontoDscto(String strSumMontoDscto) {
		this.strSumMontoDscto = strSumMontoDscto;
	}
	public BigDecimal getBdSumMontoDscto() {
		return bdSumMontoDscto;
	}
	public void setBdSumMontoDscto(BigDecimal bdSumMontoDscto) {
		this.bdSumMontoDscto = bdSumMontoDscto;
	}
	
}
