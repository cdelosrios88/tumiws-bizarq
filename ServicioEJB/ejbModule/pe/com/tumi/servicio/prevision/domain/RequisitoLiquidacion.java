package pe.com.tumi.servicio.prevision.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RequisitoLiquidacion extends TumiDomain{
	private RequisitoLiquidacionId id;
	private Integer intPersEmpresaPk;
	private Integer intItemReqAut;
	private Integer intItemReqAutEstDetalle;
	private	Integer intParaTipoArchivo;
	private	Integer intParaItemArchivo;
	private	Integer intParaItemHistorico;
	private	Integer intParaEstado;
	private	Timestamp tsFechaRequisito;
	private	Integer intPersonaBeneficiarioId;
	

	public RequisitoLiquidacionId getId() {
		return id;
	}
	public void setId(RequisitoLiquidacionId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntItemReqAut() {
		return intItemReqAut;
	}
	public void setIntItemReqAut(Integer intItemReqAut) {
		this.intItemReqAut = intItemReqAut;
	}
	public Integer getIntItemReqAutEstDetalle() {
		return intItemReqAutEstDetalle;
	}
	public void setIntItemReqAutEstDetalle(Integer intItemReqAutEstDetalle) {
		this.intItemReqAutEstDetalle = intItemReqAutEstDetalle;
	}
	public Integer getIntParaTipoArchivo() {
		return intParaTipoArchivo;
	}
	public void setIntParaTipoArchivo(Integer intParaTipoArchivo) {
		this.intParaTipoArchivo = intParaTipoArchivo;
	}
	public Integer getIntParaItemArchivo() {
		return intParaItemArchivo;
	}
	public void setIntParaItemArchivo(Integer intParaItemArchivo) {
		this.intParaItemArchivo = intParaItemArchivo;
	}
	public Integer getIntParaItemHistorico() {
		return intParaItemHistorico;
	}
	public void setIntParaItemHistorico(Integer intParaItemHistorico) {
		this.intParaItemHistorico = intParaItemHistorico;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Timestamp getTsFechaRequisito() {
		return tsFechaRequisito;
	}
	public void setTsFechaRequisito(Timestamp tsFechaRequisito) {
		this.tsFechaRequisito = tsFechaRequisito;
	}
	public Integer getIntPersonaBeneficiarioId() {
		return intPersonaBeneficiarioId;
	}
	public void setIntPersonaBeneficiarioId(Integer intPersonaBeneficiarioId) {
		this.intPersonaBeneficiarioId = intPersonaBeneficiarioId;
	}
	

}
