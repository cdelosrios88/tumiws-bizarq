package pe.com.tumi.credito.socio.estructura.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Terceros extends TumiDomain{
	private TercerosId id;
	private String strCpto;
	private String strPeriodo; //MesAño
	private String strNombre;
	private Integer intMonto;
	//private String strMonto;
	private String strProgSub;
	private String strCodEst;
	private String strTipoPla;
	private String strPro;
	private String strCodEje;
	private String strLibeje;
	private String strNomCpto;
	
	private String[] lsMontos;
	private String strMes;
	
	private BigDecimal bdMonto;
	private BigDecimal bdMontoTotal;
	
	public TercerosId getId() {
		return id;
	}
	public void setId(TercerosId id) {
		this.id = id;
	}
	public String getStrCpto() {
		return strCpto;
	}
	public void setStrCpto(String strCpto) {
		this.strCpto = strCpto;
	}
	public String getStrPeriodo() {
		return strPeriodo;
	}
	public void setStrPeriodo(String strPeriodo) {
		this.strPeriodo = strPeriodo;
	}
	public String getStrNombre() {
		return strNombre;
	}
	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
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
	public String getStrLibeje() {
		return strLibeje;
	}
	public void setStrLibeje(String strLibeje) {
		this.strLibeje = strLibeje;
	}
	public String getStrNomCpto() {
		return strNomCpto;
	}
	public void setStrNomCpto(String strNomCpto) {
		this.strNomCpto = strNomCpto;
	}
	public String[] getLsMontos() {
		return lsMontos;
	}
	public void setLsMontos(String[] lsMontos) {
		this.lsMontos = lsMontos;
	}
	public String getStrMes() {
		return strMes;
	}
	public void setStrMes(String strMes) {
		this.strMes = strMes;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	
}
