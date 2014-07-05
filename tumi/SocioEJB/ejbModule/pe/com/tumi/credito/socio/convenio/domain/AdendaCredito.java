package pe.com.tumi.credito.socio.convenio.domain;

import java.sql.Timestamp;

import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AdendaCredito extends TumiDomain {
	private AdendaCreditoId id;
	private Credito credito;
	private Integer intParaEstadoCod;
	private Integer intPersPersonaRegistraPk;
	private Timestamp tsFechaRegistra;
	private Integer intPersPersonaEliminaPk;
	private Timestamp tsFechaEliminacion;
	
	public AdendaCreditoId getId() {
		return id;
	}
	public void setId(AdendaCreditoId id) {
		this.id = id;
	}
	public Credito getCredito() {
		return credito;
	}
	public void setCredito(Credito credito) {
		this.credito = credito;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntPersPersonaRegistraPk() {
		return intPersPersonaRegistraPk;
	}
	public void setIntPersPersonaRegistraPk(Integer intPersPersonaRegistraPk) {
		this.intPersPersonaRegistraPk = intPersPersonaRegistraPk;
	}
	public Timestamp getTsFechaRegistra() {
		return tsFechaRegistra;
	}
	public void setTsFechaRegistra(Timestamp tsFechaRegistra) {
		this.tsFechaRegistra = tsFechaRegistra;
	}
	public Integer getIntPersPersonaEliminaPk() {
		return intPersPersonaEliminaPk;
	}
	public void setIntPersPersonaEliminaPk(Integer intPersPersonaEliminaPk) {
		this.intPersPersonaEliminaPk = intPersPersonaEliminaPk;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
}
