package pe.com.tumi.movimiento.concepto.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class BloqueoCuenta extends TumiDomain{

	private Integer intItemBloqueoCuenta;
	private Integer intPersEmpresaPk;
	private Integer intCuentaPk;
	private Integer intItemCuentaConcepto;
	private Integer intItemExpediente;
	private Integer intItemExpedienteDetalle;
	private Integer intParaTipoBloqueoCod;
	private Timestamp tsFechaInicio;
	private Timestamp tsFechaFin;
	private Integer intParaCodigoMotivoCod;
	private String strObservacion;
	private Timestamp tsFechaRegistro;
	private Integer intPersEmpresaUsuarioPk;
	private Integer intPersPersonaUsuarioPk;
	private Integer intParaEstadoCod;
	private Integer intPersEmpresaEliminaPk;
	private Integer intPersPersonaEliminaPk;
	private Timestamp tsFechaEliminar;
	private Integer intParaTipoConceptoCre;
	//autor rVillarreal
	private Integer montoFdoSepelio;
	
	private CuentaConcepto cuentaConcepto;

	public Integer getIntItemBloqueoCuenta() {
		return intItemBloqueoCuenta;
	}
	public void setIntItemBloqueoCuenta(Integer intItemBloqueoCuenta) {
		this.intItemBloqueoCuenta = intItemBloqueoCuenta;
	}

	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}

	public Integer getIntCuentaPk() {
		return intCuentaPk;
	}
	public void setIntCuentaPk(Integer intCuentaPk) {
		this.intCuentaPk = intCuentaPk;
	}

	public Integer getIntItemCuentaConcepto() {
		return intItemCuentaConcepto;
	}
	public void setIntItemCuentaConcepto(Integer intItemCuentaConcepto) {
		this.intItemCuentaConcepto = intItemCuentaConcepto;
	}

	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}
	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}

	public Integer getIntItemExpedienteDetalle() {
		return intItemExpedienteDetalle;
	}
	public void setIntItemExpedienteDetalle(Integer intItemExpedienteDetalle) {
		this.intItemExpedienteDetalle = intItemExpedienteDetalle;
	}

	public Integer getIntParaTipoBloqueoCod() {
		return intParaTipoBloqueoCod;
	}
	public void setIntParaTipoBloqueoCod(Integer intParaTipoBloqueoCod) {
		this.intParaTipoBloqueoCod = intParaTipoBloqueoCod;
	}

	public Timestamp getTsFechaInicio() {
		return tsFechaInicio;
	}
	public void setTsFechaInicio(Timestamp tsFechaInicio) {
		this.tsFechaInicio = tsFechaInicio;
	}

	public Timestamp getTsFechaFin() {
		return tsFechaFin;
	}
	public void setTsFechaFin(Timestamp tsFechaFin) {
		this.tsFechaFin = tsFechaFin;
	}

	public Integer getIntParaCodigoMotivoCod() {
		return intParaCodigoMotivoCod;
	}
	public void setIntParaCodigoMotivoCod(Integer intParaCodigoMotivoCod) {
		this.intParaCodigoMotivoCod = intParaCodigoMotivoCod;
	}

	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}

	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}

	public Integer getIntPersEmpresaUsuarioPk() {
		return intPersEmpresaUsuarioPk;
	}
	public void setIntPersEmpresaUsuarioPk(Integer intPersEmpresaUsuarioPk) {
		this.intPersEmpresaUsuarioPk = intPersEmpresaUsuarioPk;
	}

	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}

	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}

	public Integer getIntPersEmpresaEliminaPk() {
		return intPersEmpresaEliminaPk;
	}
	public void setIntPersEmpresaEliminaPk(Integer intPersEmpresaEliminaPk) {
		this.intPersEmpresaEliminaPk = intPersEmpresaEliminaPk;
	}

	public Integer getIntPersPersonaEliminaPk() {
		return intPersPersonaEliminaPk;
	}
	public void setIntPersPersonaEliminaPk(Integer intPersPersonaEliminaPk) {
		this.intPersPersonaEliminaPk = intPersPersonaEliminaPk;
	}

	public Timestamp getTsFechaEliminar() {
		return tsFechaEliminar;
	}
	public void setTsFechaEliminar(Timestamp tsFechaEliminar) {
		this.tsFechaEliminar = tsFechaEliminar;
	}

	public CuentaConcepto getCuentaConcepto() {
		return cuentaConcepto;
	}
	public void setCuentaConcepto(CuentaConcepto cuentaConcepto) {
		this.cuentaConcepto = cuentaConcepto;
	}
	public Integer getIntParaTipoConceptoCre() {
		return intParaTipoConceptoCre;
	}
	public void setIntParaTipoConceptoCre(Integer intParaTipoConceptoCre) {
		this.intParaTipoConceptoCre = intParaTipoConceptoCre;
	}
	public Integer getMontoFdoSepelio() {
		return montoFdoSepelio;
	}
	public void setMontoFdoSepelio(Integer montoFdoSepelio) {
		this.montoFdoSepelio = montoFdoSepelio;
	}
}