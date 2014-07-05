package pe.com.tumi.credito.socio.estructura.domain;

import java.math.BigDecimal;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SolicitudPago extends TumiDomain {

	private Integer intNumero;
	private Integer intPersEmpresaPK;
	private Integer intPersPersonsaPK;
	private Integer intParaMonedaCod;
	private BigDecimal bdMonto;
	private Integer intPersEmpresaUsuarioPK;
	private Integer intPersPersonsaUsuarioPK;
	private Integer intParaEstadoPagoCod;
	private Date dtFechaRegistro;
	private Integer intParaEstadoCod;
	private Date dtFechaEliminacion;
	private Integer intPersEmpresaEliminar;
	private Integer intPersPersonaEliminar;
	public Integer getIntNumero() {
		return intNumero;
	}
	public void setIntNumero(Integer intNumero) {
		this.intNumero = intNumero;
	}
	public Integer getIntPersEmpresaPK() {
		return intPersEmpresaPK;
	}
	public void setIntPersEmpresaPK(Integer intPersEmpresaPK) {
		this.intPersEmpresaPK = intPersEmpresaPK;
	}
	public Integer getIntPersPersonsaPK() {
		return intPersPersonsaPK;
	}
	public void setIntPersPersonsaPK(Integer intPersPersonsaPK) {
		this.intPersPersonsaPK = intPersPersonsaPK;
	}
	public Integer getIntParaMonedaCod() {
		return intParaMonedaCod;
	}
	public void setIntParaMonedaCod(Integer intParaMonedaCod) {
		this.intParaMonedaCod = intParaMonedaCod;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public Integer getIntPersEmpresaUsuarioPK() {
		return intPersEmpresaUsuarioPK;
	}
	public void setIntPersEmpresaUsuarioPK(Integer intPersEmpresaUsuarioPK) {
		this.intPersEmpresaUsuarioPK = intPersEmpresaUsuarioPK;
	}
	public Integer getIntPersPersonsaUsuarioPK() {
		return intPersPersonsaUsuarioPK;
	}
	public void setIntPersPersonsaUsuarioPK(Integer intPersPersonsaUsuarioPK) {
		this.intPersPersonsaUsuarioPK = intPersPersonsaUsuarioPK;
	}
	public Integer getIntParaEstadoPagoCod() {
		return intParaEstadoPagoCod;
	}
	public void setIntParaEstadoPagoCod(Integer intParaEstadoPagoCod) {
		this.intParaEstadoPagoCod = intParaEstadoPagoCod;
	}
	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Date getDtFechaEliminacion() {
		return dtFechaEliminacion;
	}
	public void setDtFechaEliminacion(Date dtFechaEliminacion) {
		this.dtFechaEliminacion = dtFechaEliminacion;
	}
	public Integer getIntPersEmpresaEliminar() {
		return intPersEmpresaEliminar;
	}
	public void setIntPersEmpresaEliminar(Integer intPersEmpresaEliminar) {
		this.intPersEmpresaEliminar = intPersEmpresaEliminar;
	}
	public Integer getIntPersPersonaEliminar() {
		return intPersPersonaEliminar;
	}
	public void setIntPersPersonaEliminar(Integer intPersPersonaEliminar) {
		this.intPersPersonaEliminar = intPersPersonaEliminar;
	}
	@Override
	public String toString() {
		return "SolicitudPago [intNumero=" + intNumero + ", intPersEmpresaPK="
				+ intPersEmpresaPK + ", intPersPersonsaPK=" + intPersPersonsaPK
				+ ", intParaMonedaCod=" + intParaMonedaCod + ", bdMonto="
				+ bdMonto + ", intPersEmpresaUsuarioPK="
				+ intPersEmpresaUsuarioPK + ", intPersPersonsaUsuarioPK="
				+ intPersPersonsaUsuarioPK + ", intParaEstadoPagoCod="
				+ intParaEstadoPagoCod + ", dtFechaRegistro=" + dtFechaRegistro
				+ ", intParaEstadoCod=" + intParaEstadoCod
				+ ", dtFechaEliminacion=" + dtFechaEliminacion
				+ ", intPersEmpresaEliminar=" + intPersEmpresaEliminar
				+ ", intPersPersonaEliminar=" + intPersPersonaEliminar + "]";
	}
	
	
}
