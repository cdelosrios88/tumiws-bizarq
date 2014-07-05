package pe.com.tumi.cobranza.cierremensual.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CierreCobranzaOperacion extends TumiDomain {
	private CierreCobranzaOperacionId id;
	private Integer intParaEstadoCierre;
	private Integer intEmpresaRegistro;
	private Integer intPersonaRegistro;
	private Timestamp tsFechaRegistro;
	
	public CierreCobranzaOperacionId getId() {
		return id;
	}
	public void setId(CierreCobranzaOperacionId id) {
		this.id = id;
	}
	public Integer getIntParaEstadoCierre() {
		return intParaEstadoCierre;
	}
	public void setIntParaEstadoCierre(Integer intParaEstadoCierre) {
		this.intParaEstadoCierre = intParaEstadoCierre;
	}
	public Integer getIntEmpresaRegistro() {
		return intEmpresaRegistro;
	}
	public void setIntEmpresaRegistro(Integer intEmpresaRegistro) {
		this.intEmpresaRegistro = intEmpresaRegistro;
	}
	public Integer getIntPersonaRegistro() {
		return intPersonaRegistro;
	}
	public void setIntPersonaRegistro(Integer intPersonaRegistro) {
		this.intPersonaRegistro = intPersonaRegistro;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
}