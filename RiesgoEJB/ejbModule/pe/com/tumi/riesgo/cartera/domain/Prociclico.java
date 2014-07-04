package pe.com.tumi.riesgo.cartera.domain;

import java.math.BigDecimal;

public class Prociclico {

	private ProciclicoId id;
	private Integer intParaTipoCategoriaRiesgoCod;
	private BigDecimal bdMontoProvision;
	private BigDecimal bdMontoConstitucion2;
	private BigDecimal bdMontoConstitucion4;
	private BigDecimal bdMontoConstitucion6;
	private Integer intParaEstadoCod;
	
	public Prociclico(){
		id = new ProciclicoId();
	}
	
	public ProciclicoId getId() {
		return id;
	}
	public void setId(ProciclicoId id) {
		this.id = id;
	}
	public Integer getIntParaTipoCategoriaRiesgoCod() {
		return intParaTipoCategoriaRiesgoCod;
	}
	public void setIntParaTipoCategoriaRiesgoCod(
			Integer intParaTipoCategoriaRiesgoCod) {
		this.intParaTipoCategoriaRiesgoCod = intParaTipoCategoriaRiesgoCod;
	}
	public BigDecimal getBdMontoProvision() {
		return bdMontoProvision;
	}
	public void setBdMontoProvision(BigDecimal bdMontoProvision) {
		this.bdMontoProvision = bdMontoProvision;
	}
	public BigDecimal getBdMontoConstitucion2() {
		return bdMontoConstitucion2;
	}
	public void setBdMontoConstitucion2(BigDecimal bdMontoConstitucion2) {
		this.bdMontoConstitucion2 = bdMontoConstitucion2;
	}
	public BigDecimal getBdMontoConstitucion4() {
		return bdMontoConstitucion4;
	}
	public void setBdMontoConstitucion4(BigDecimal bdMontoConstitucion4) {
		this.bdMontoConstitucion4 = bdMontoConstitucion4;
	}
	public BigDecimal getBdMontoConstitucion6() {
		return bdMontoConstitucion6;
	}
	public void setBdMontoConstitucion6(BigDecimal bdMontoConstitucion6) {
		this.bdMontoConstitucion6 = bdMontoConstitucion6;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	@Override
	public String toString() {
		return "Prociclico [id=" + id + ", intParaTipoCategoriaRiesgoCod="
				+ intParaTipoCategoriaRiesgoCod + ", bdMontoProvision="
				+ bdMontoProvision + ", bdMontoConstitucion2="
				+ bdMontoConstitucion2 + ", bdMontoConstitucion4="
				+ bdMontoConstitucion4 + ", bdMontoConstitucion6="
				+ bdMontoConstitucion6 + ", intParaEstadoCod="
				+ intParaEstadoCod + "]";
	}
	
	
}
