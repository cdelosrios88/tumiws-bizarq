package pe.com.tumi.credito.socio.convenio.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RetencionPlanilla extends TumiDomain {
	private RetencionPlanillaId id;
	private Integer		intNivel;
	private Integer		intCodigo;
	private Integer		intParaMesCod;
	private Integer		intAnio;
	private Integer		intParaTipoSocioCod;
	private Integer		intParaModalidadCod;
	private Integer		intParaEstPagoCod;
	
	public RetencionPlanillaId getId() {
		return id;
	}
	public void setId(RetencionPlanillaId id) {
		this.id = id;
	}
	public Integer getIntNivel() {
		return intNivel;
	}
	public void setIntNivel(Integer intNivel) {
		this.intNivel = intNivel;
	}
	public Integer getIntCodigo() {
		return intCodigo;
	}
	public void setIntCodigo(Integer intCodigo) {
		this.intCodigo = intCodigo;
	}
	public Integer getIntParaMesCod() {
		return intParaMesCod;
	}
	public void setIntParaMesCod(Integer intParaMesCod) {
		this.intParaMesCod = intParaMesCod;
	}
	public Integer getIntAnio() {
		return intAnio;
	}
	public void setIntAnio(Integer intAnio) {
		this.intAnio = intAnio;
	}
	public Integer getIntParaTipoSocioCod() {
		return intParaTipoSocioCod;
	}
	public void setIntParaTipoSocioCod(Integer intParaTipoSocioCod) {
		this.intParaTipoSocioCod = intParaTipoSocioCod;
	}
	public Integer getIntParaModalidadCod() {
		return intParaModalidadCod;
	}
	public void setIntParaModalidadCod(Integer intParaModalidadCod) {
		this.intParaModalidadCod = intParaModalidadCod;
	}
	public Integer getIntParaEstPagoCod() {
		return intParaEstPagoCod;
	}
	public void setIntParaEstPagoCod(Integer intParaEstPagoCod) {
		this.intParaEstPagoCod = intParaEstPagoCod;
	}
}
