package pe.com.tumi.credito.socio.convenio.domain;

import java.sql.Timestamp;

import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AdendaCaptacion extends TumiDomain {
	private AdendaCaptacionId id;
	private Captacion captacion;
	private Integer intParaEstadoCod;
	private Integer intPersPersonaRegistraPk;
	private Timestamp tsFechaRegistra;
	private Integer intPersPersonaEliminaPk;
	private Timestamp tsFechaEliminacion;
	
	public AdendaCaptacionId getId() {
		return id;
	}
	public void setId(AdendaCaptacionId id) {
		this.id = id;
	}
	public Captacion getCaptacion() {
		return captacion;
	}
	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
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
