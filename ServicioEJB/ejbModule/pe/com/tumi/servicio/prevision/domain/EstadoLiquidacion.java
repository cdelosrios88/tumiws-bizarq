package pe.com.tumi.servicio.prevision.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class EstadoLiquidacion extends TumiDomain{

	private EstadoLiquidacionId	id;
	private Integer intParaEstado;
	private Timestamp 	tsFechaEstado;
	private Integer intPersEmpresaEstado;
	private Integer intSucuIdSucursal;
	private Integer intSudeIdSubsucursal;
	private Integer intPersUsuarioEstado;
	private String 	strObservacion;
	private Integer intPersEmpresaLibro;
	private Integer intContPeriodoLibro;
	private Integer intContCodigoLibro;
	
	//filtro
	private Date dtFechaEstadoDesde;
	private Date dtFechaEstadoHasta;
	
	private Sucursal sucursal;
	private Subsucursal subsucursal;
	private Persona	persona;
	
	public EstadoLiquidacion(){
		id = new EstadoLiquidacionId();
	}
	
	public EstadoLiquidacionId getId() {
		return id;
	}
	public void setId(EstadoLiquidacionId id) {
		this.id = id;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Timestamp getTsFechaEstado() {
		return tsFechaEstado;
	}
	public void setTsFechaEstado(Timestamp tsFechaEstado) {
		this.tsFechaEstado = tsFechaEstado;
	}
	public Integer getIntPersEmpresaEstado() {
		return intPersEmpresaEstado;
	}
	public void setIntPersEmpresaEstado(Integer intPersEmpresaEstado) {
		this.intPersEmpresaEstado = intPersEmpresaEstado;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSudeIdSubsucursal() {
		return intSudeIdSubsucursal;
	}
	public void setIntSudeIdSubsucursal(Integer intSudeIdSubsucursal) {
		this.intSudeIdSubsucursal = intSudeIdSubsucursal;
	}
	public Integer getIntPersUsuarioEstado() {
		return intPersUsuarioEstado;
	}
	public void setIntPersUsuarioEstado(Integer intPersUsuarioEstado) {
		this.intPersUsuarioEstado = intPersUsuarioEstado;
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
	public Integer getIntContPeriodoLibro() {
		return intContPeriodoLibro;
	}
	public void setIntContPeriodoLibro(Integer intContPeriodoLibro) {
		this.intContPeriodoLibro = intContPeriodoLibro;
	}
	public Integer getIntContCodigoLibro() {
		return intContCodigoLibro;
	}
	public void setIntContCodigoLibro(Integer intContCodigoLibro) {
		this.intContCodigoLibro = intContCodigoLibro;
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
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "EstadoLiquidacion [id=" + id + ", intParaEstado=" + intParaEstado
				+ ", tsFechaEstado=" + tsFechaEstado
				+ ", intPersEmpresaEstado=" + intPersEmpresaEstado
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdSubsucursal=" + intSudeIdSubsucursal
				+ ", intPersUsuarioEstado=" + intPersUsuarioEstado
				+ ", strObservacion=" + strObservacion
				+ ", intPersEmpresaLibro=" + intPersEmpresaLibro
				+ ", intContPeriodoLibro=" + intContPeriodoLibro
				+ ", intContCodigoLibro=" + intContCodigoLibro + "]";
	}
	
}
