package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class GarantiaCredito extends TumiDomain {
	private GarantiaCreditoId id;
	private Integer intParaTipoGarantiaCod;
	private Integer intItemCreditoGarantia;
	private Integer intPersEmpresaGarantePk;
	private Integer intPersCuentaGarantePk;
	private Integer intPersPersonaGarantePk;
	private Integer intIdGaranteSucursalPk;
	private Integer intIdGaranteSubSucursalPk;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaRegistro;
	private Integer intPersPersonaUsuarioPk;
	private Timestamp tsFechaEliminacion;
	private Integer intPersPersonaEliminaPk;
	
	public GarantiaCreditoId getId() {
		return id;
	}
	public void setId(GarantiaCreditoId id) {
		this.id = id;
	}
	public Integer getIntParaTipoGarantiaCod() {
		return intParaTipoGarantiaCod;
	}
	public void setIntParaTipoGarantiaCod(Integer intParaTipoGarantiaCod) {
		this.intParaTipoGarantiaCod = intParaTipoGarantiaCod;
	}
	public Integer getIntItemCreditoGarantia() {
		return intItemCreditoGarantia;
	}
	public void setIntItemCreditoGarantia(Integer intItemCreditoGarantia) {
		this.intItemCreditoGarantia = intItemCreditoGarantia;
	}
	public Integer getIntPersEmpresaGarantePk() {
		return intPersEmpresaGarantePk;
	}
	public void setIntPersEmpresaGarantePk(Integer intPersEmpresaGarantePk) {
		this.intPersEmpresaGarantePk = intPersEmpresaGarantePk;
	}
	public Integer getIntPersCuentaGarantePk() {
		return intPersCuentaGarantePk;
	}
	public void setIntPersCuentaGarantePk(Integer intPersCuentaGarantePk) {
		this.intPersCuentaGarantePk = intPersCuentaGarantePk;
	}
	public Integer getIntPersPersonaGarantePk() {
		return intPersPersonaGarantePk;
	}
	public void setIntPersPersonaGarantePk(Integer intPersPersonaGarantePk) {
		this.intPersPersonaGarantePk = intPersPersonaGarantePk;
	}
	public Integer getIntIdGaranteSucursalPk() {
		return intIdGaranteSucursalPk;
	}
	public void setIntIdGaranteSucursalPk(Integer intIdGaranteSucursalPk) {
		this.intIdGaranteSucursalPk = intIdGaranteSucursalPk;
	}
	public Integer getIntIdGaranteSubSucursalPk() {
		return intIdGaranteSubSucursalPk;
	}
	public void setIntIdGaranteSubSucursalPk(Integer intIdGaranteSubSucursalPk) {
		this.intIdGaranteSubSucursalPk = intIdGaranteSubSucursalPk;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Integer getIntPersPersonaEliminaPk() {
		return intPersPersonaEliminaPk;
	}
	public void setIntPersPersonaEliminaPk(Integer intPersPersonaEliminaPk) {
		this.intPersPersonaEliminaPk = intPersPersonaEliminaPk;
	}
}
