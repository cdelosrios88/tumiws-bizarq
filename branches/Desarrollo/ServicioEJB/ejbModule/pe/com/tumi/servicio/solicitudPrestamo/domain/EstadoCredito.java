package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class EstadoCredito extends TumiDomain {
	private EstadoCreditoId id;
	private Integer intParaEstadoCreditoCod;
	private Timestamp tsFechaEstado;
	private Integer intPersEmpresaEstadoCod;
	private Integer intIdUsuSucursalPk;
	private Integer intIdUsuSubSucursalPk;
	private Integer intPersUsuarioEstadoPk;
	private String strObservacion;
	private Integer intPersEmpresaLibro;
	private Integer intPeriodoLibro;
	private Integer intCodigoLibro;
	
	private Sucursal	sucursal;
	private Subsucursal subsucursal;
	
	private Date dtFechaEstadoDesde;
	private Date dtFechaEstadoHasta;
	
	
	public EstadoCredito(){
		id = new EstadoCreditoId();
	}
	
	public EstadoCreditoId getId() {
		return id;
	}
	public void setId(EstadoCreditoId id) {
		this.id = id;
	}
	public Integer getIntParaEstadoCreditoCod() {
		return intParaEstadoCreditoCod;
	}
	public void setIntParaEstadoCreditoCod(Integer intParaEstadoCreditoCod) {
		this.intParaEstadoCreditoCod = intParaEstadoCreditoCod;
	}
	public Timestamp getTsFechaEstado() {
		return tsFechaEstado;
	}
	public void setTsFechaEstado(Timestamp tsFechaEstado) {
		this.tsFechaEstado = tsFechaEstado;
	}
	public Integer getIntPersEmpresaEstadoCod() {
		return intPersEmpresaEstadoCod;
	}
	public void setIntPersEmpresaEstadoCod(Integer intPersEmpresaEstadoCod) {
		this.intPersEmpresaEstadoCod = intPersEmpresaEstadoCod;
	}
	public Integer getIntIdUsuSucursalPk() {
		return intIdUsuSucursalPk;
	}
	public void setIntIdUsuSucursalPk(Integer intIdUsuSucursalPk) {
		this.intIdUsuSucursalPk = intIdUsuSucursalPk;
	}
	public Integer getIntIdUsuSubSucursalPk() {
		return intIdUsuSubSucursalPk;
	}
	public void setIntIdUsuSubSucursalPk(Integer intIdUsuSubSucursalPk) {
		this.intIdUsuSubSucursalPk = intIdUsuSubSucursalPk;
	}
	public Integer getIntPersUsuarioEstadoPk() {
		return intPersUsuarioEstadoPk;
	}
	public void setIntPersUsuarioEstadoPk(Integer intPersUsuarioEstadoPk) {
		this.intPersUsuarioEstadoPk = intPersUsuarioEstadoPk;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntPersEmpresaLibro() {
		return intPersEmpresaLibro;
	}
	public void setIntPersEmpresaLibro(Integer intPersEmpresaLibro) {
		this.intPersEmpresaLibro = intPersEmpresaLibro;
	}
	public Integer getIntPeriodoLibro() {
		return intPeriodoLibro;
	}
	public void setIntPeriodoLibro(Integer intPeriodoLibro) {
		this.intPeriodoLibro = intPeriodoLibro;
	}
	public Integer getIntCodigoLibro() {
		return intCodigoLibro;
	}
	public void setIntCodigoLibro(Integer intCodigoLibro) {
		this.intCodigoLibro = intCodigoLibro;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Subsucursal getSubsucursal() {
		return subsucursal;
	}
	public void setSubsucursal(Subsucursal subsucursal) {
		this.subsucursal = subsucursal;
	}	
	public Date getDtFechaEstadoDesde() {
		return dtFechaEstadoDesde;
	}
	public void setDtFechaEstadoDesde(Date dtFechaEstadoDesde) {
		this.dtFechaEstadoDesde = dtFechaEstadoDesde;
	}
	public Date getDtFechaEstadoHasta() {
		return dtFechaEstadoHasta;
	}
	public void setDtFechaEstadoHasta(Date dtFechaEstadoHasta) {
		this.dtFechaEstadoHasta = dtFechaEstadoHasta;
	}
	@Override
	public String toString() {
		return "EstadoCredito [id=" + id + ", intParaEstadoCreditoCod="
				+ intParaEstadoCreditoCod + ", tsFechaEstado=" + tsFechaEstado
				+ ", intPersEmpresaEstadoCod=" + intPersEmpresaEstadoCod
				+ ", intIdUsuSucursalPk=" + intIdUsuSucursalPk
				+ ", intIdUsuSubSucursalPk=" + intIdUsuSubSucursalPk
				+ ", intPersUsuarioEstadoPk=" + intPersUsuarioEstadoPk
				+ ", strObservacion=" + strObservacion
				+ ", intPersEmpresaLibro=" + intPersEmpresaLibro
				+ ", intPeriodoLibro=" + intPeriodoLibro + ", intCodigoLibro="
				+ intCodigoLibro + "]";
	}
}