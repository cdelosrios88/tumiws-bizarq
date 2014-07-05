package pe.com.tumi.credito.socio.aperturaCuenta.core.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaIntegrantePermiso extends TumiDomain {
	private CuentaIntegrantePermisoId id;
	private Integer intParaTipoPermisoCod;
	private BigDecimal bdMontoMinimo;
	private BigDecimal bdMontoMaximo;
	private Timestamp tsFechaRegistro;
	private Integer intParaEstadoCod;
	private Timestamp tsFechaEliminacion;
	
	public CuentaIntegrantePermisoId getId() {
		return id;
	}
	public void setId(CuentaIntegrantePermisoId id) {
		this.id = id;
	}
	public Integer getIntParaTipoPermisoCod() {
		return intParaTipoPermisoCod;
	}
	public void setIntParaTipoPermisoCod(Integer intParaTipoPermisoCod) {
		this.intParaTipoPermisoCod = intParaTipoPermisoCod;
	}
	public BigDecimal getBdMontoMinimo() {
		return bdMontoMinimo;
	}
	public void setBdMontoMinimo(BigDecimal bdMontoMinimo) {
		this.bdMontoMinimo = bdMontoMinimo;
	}
	public BigDecimal getBdMontoMaximo() {
		return bdMontoMaximo;
	}
	public void setBdMontoMaximo(BigDecimal bdMontoMaximo) {
		this.bdMontoMaximo = bdMontoMaximo;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
}
