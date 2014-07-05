package pe.com.tumi.riesgo.cartera.domain;

import java.math.BigDecimal;

public class Provision {

	private ProvisionId id;
	private Integer intParaTipoCategoriaRiesgoCod;
	private BigDecimal bdMontoProvision;
	private Integer intParaEstadoCod;
	
	public Provision(){
		id = new ProvisionId();
	}

	public ProvisionId getId() {
		return id;
	}

	public void setId(ProvisionId id) {
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

	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}

	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}

	@Override
	public String toString() {
		return "Provision [id=" + id + ", intParaTipoCategoriaRiesgoCod="
				+ intParaTipoCategoriaRiesgoCod + ", bdMontoProvision="
				+ bdMontoProvision + ", intParaEstadoCod=" + intParaEstadoCod
				+ "]";
	}
	
	
}
