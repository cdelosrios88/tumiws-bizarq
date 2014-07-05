package pe.com.tumi.cobranza.planilla.domain;

import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Natural;

public class EstadoSolicitudCtaCte extends TumiDomain{
	
	EstadoSolicitudCtaCteId id;
	
	private Integer intParaEstadoSolCtaCte;
	private Date    dtEsccFechaEstado;
	private Integer intPersEmpresaEstado;
	private Integer intSucuIduSusucursal;
	private Integer intSudeIduSusubsucursal;
	private Integer intPersUsuarioEstado;
	private String  strEsccObservacion;
	
	//Auxiliar 
	
	private Natural usuarioSolicitud;
    private Natural usuarioAtencion;
    
	
	public EstadoSolicitudCtaCte(){
		id = new EstadoSolicitudCtaCteId();
	}
	
	public EstadoSolicitudCtaCteId getId() {
		return id;
	}
	public void setId(EstadoSolicitudCtaCteId id) {
		this.id = id;
	}
	public Integer getIntParaEstadoSolCtaCte() {
		return intParaEstadoSolCtaCte;
	}
	public void setIntParaEstadoSolCtaCte(Integer intParaEstadoSolCtaCte) {
		this.intParaEstadoSolCtaCte = intParaEstadoSolCtaCte;
	}
	public Date getDtEsccFechaEstado() {
		return dtEsccFechaEstado;
	}
	public void setDtEsccFechaEstado(Date dtEsccFechaEstado) {
		this.dtEsccFechaEstado = dtEsccFechaEstado;
	}
	public Integer getIntPersEmpresaEstado() {
		return intPersEmpresaEstado;
	}
	public void setIntPersEmpresaEstado(Integer intPersEmpresaEstado) {
		this.intPersEmpresaEstado = intPersEmpresaEstado;
	}
	public Integer getIntSucuIduSusucursal() {
		return intSucuIduSusucursal;
	}
	public void setIntSucuIduSusucursal(Integer intSucuIduSusucursal) {
		this.intSucuIduSusucursal = intSucuIduSusucursal;
	}
	public Integer getIntSudeIduSusubsucursal() {
		return intSudeIduSusubsucursal;
	}
	public void setIntSudeIduSusubsucursal(Integer intSudeIduSusubsucursal) {
		this.intSudeIduSusubsucursal = intSudeIduSusubsucursal;
	}
	public Integer getIntPersUsuarioEstado() {
		return intPersUsuarioEstado;
	}
	public void setIntPersUsuarioEstado(Integer intPersUsuarioEstado) {
		this.intPersUsuarioEstado = intPersUsuarioEstado;
	}

	public String getStrEsccObservacion() {
		return strEsccObservacion;
	}

	public void setStrEsccObservacion(String strEsccObservacion) {
		this.strEsccObservacion = strEsccObservacion;
	}

	public Natural getUsuarioSolicitud() {
		return usuarioSolicitud;
	}

	public void setUsuarioSolicitud(Natural usuarioSolicitud) {
		this.usuarioSolicitud = usuarioSolicitud;
	}

	public Natural getUsuarioAtencion() {
		return usuarioAtencion;
	}

	public void setUsuarioAtencion(Natural usuarioAtencion) {
		this.usuarioAtencion = usuarioAtencion;
	}
	
	
	
	
	
	
}