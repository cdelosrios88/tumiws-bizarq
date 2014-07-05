package pe.com.tumi.credito.socio.captacion.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Requisito extends TumiDomain {

	private RequisitoId id;
	private Captacion captacion;
	private Integer intNumeroCuota;
	private Integer intParaTipoMaxMinCod;
	private BigDecimal bdBeneficio;
	private BigDecimal bdGastoAdministrativo;
	private Integer intParaEstadoCod;
	private Boolean chkRequisito;
	
	public RequisitoId getId() {
		return id;
	}
	public void setId(RequisitoId id) {
		this.id = id;
	}
	public Captacion getCaptacion() {
		return captacion;
	}
	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}
	public Integer getIntNumeroCuota() {
		return intNumeroCuota;
	}
	public void setIntNumeroCuota(Integer intNumeroCuota) {
		this.intNumeroCuota = intNumeroCuota;
	}
	public Integer getIntParaTipoMaxMinCod() {
		return intParaTipoMaxMinCod;
	}
	public void setIntParaTipoMaxMinCod(Integer intParaTipoMaxMinCod) {
		this.intParaTipoMaxMinCod = intParaTipoMaxMinCod;
	}
	public BigDecimal getBdBeneficio() {
		return bdBeneficio;
	}
	public void setBdBeneficio(BigDecimal bdBeneficio) {
		this.bdBeneficio = bdBeneficio;
	}
	public BigDecimal getBdGastoAdministrativo() {
		return bdGastoAdministrativo;
	}
	public void setBdGastoAdministrativo(BigDecimal bdGastoAdministrativo) {
		this.bdGastoAdministrativo = bdGastoAdministrativo;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Boolean getChkRequisito() {
		return chkRequisito;
	}
	public void setChkRequisito(Boolean chkRequisito) {
		this.chkRequisito = chkRequisito;
	}
}
