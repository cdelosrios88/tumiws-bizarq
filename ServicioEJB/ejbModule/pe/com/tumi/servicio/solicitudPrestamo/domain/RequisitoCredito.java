package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RequisitoCredito extends TumiDomain {
	private RequisitoCreditoId id;
	private Integer intPersEmpresaRequisitoPk;
	private Integer intItemReqAut;
	private Integer intItemReqAutDet;
	private Integer intParaTipoArchivoCod;
	private Integer intParaItemArchivo;
	private Integer intParaItemHistorico;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaRequisito;
	
	public RequisitoCreditoId getId() {
		return id;
	}
	public void setId(RequisitoCreditoId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaRequisitoPk() {
		return intPersEmpresaRequisitoPk;
	}
	public void setIntPersEmpresaRequisitoPk(Integer intPersEmpresaRequisitoPk) {
		this.intPersEmpresaRequisitoPk = intPersEmpresaRequisitoPk;
	}
	public Integer getIntItemReqAut() {
		return intItemReqAut;
	}
	public void setIntItemReqAut(Integer intItemReqAut) {
		this.intItemReqAut = intItemReqAut;
	}
	public Integer getIntItemReqAutDet() {
		return intItemReqAutDet;
	}
	public void setIntItemReqAutDet(Integer intItemReqAutDet) {
		this.intItemReqAutDet = intItemReqAutDet;
	}
	public Integer getIntParaTipoArchivoCod() {
		return intParaTipoArchivoCod;
	}
	public void setIntParaTipoArchivoCod(Integer intParaTipoArchivoCod) {
		this.intParaTipoArchivoCod = intParaTipoArchivoCod;
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
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Timestamp getTsFechaRequisito() {
		return tsFechaRequisito;
	}
	public void setTsFechaRequisito(Timestamp tsFechaRequisito) {
		this.tsFechaRequisito = tsFechaRequisito;
	}
}