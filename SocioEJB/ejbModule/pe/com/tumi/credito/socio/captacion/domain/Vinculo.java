package pe.com.tumi.credito.socio.captacion.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Vinculo extends TumiDomain {

	private VinculoId id;
	private Captacion captacion;
	private Integer intParaTipoCuotaCod;
	private Integer intCuotaCancelada;
	private BigDecimal bdBeneficio;
	private BigDecimal bdGastoAdministrativo;
	private Integer intAportacion;
	private Integer intInteres;
	private Integer intPenalidad;
	private Integer intParaMotivo;
	private Integer intEstadoCod;
	private Boolean chkVinculo;
	
	public VinculoId getId() {
		return id;
	}
	public void setId(VinculoId id) {
		this.id = id;
	}
	public Captacion getCaptacion() {
		return captacion;
	}
	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}
	public Integer getIntParaTipoCuotaCod() {
		return intParaTipoCuotaCod;
	}
	public void setIntParaTipoCuotaCod(Integer intParaTipoCuotaCod) {
		this.intParaTipoCuotaCod = intParaTipoCuotaCod;
	}
	public Integer getIntCuotaCancelada() {
		return intCuotaCancelada;
	}
	public void setIntCuotaCancelada(Integer intCuotaCancelada) {
		this.intCuotaCancelada = intCuotaCancelada;
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
	public Integer getIntAportacion() {
		return intAportacion;
	}
	public void setIntAportacion(Integer intAportacion) {
		this.intAportacion = intAportacion;
	}
	public Integer getIntInteres() {
		return intInteres;
	}
	public void setIntInteres(Integer intInteres) {
		this.intInteres = intInteres;
	}
	public Integer getIntPenalidad() {
		return intPenalidad;
	}
	public void setIntPenalidad(Integer intPenalidad) {
		this.intPenalidad = intPenalidad;
	}
	public Integer getIntParaMotivo() {
		return intParaMotivo;
	}
	public void setIntParaMotivo(Integer intParaMotivo) {
		this.intParaMotivo = intParaMotivo;
	}
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public Boolean getChkVinculo() {
		return chkVinculo;
	}
	public void setChkVinculo(Boolean chkVinculo) {
		this.chkVinculo = chkVinculo;
	}
}
