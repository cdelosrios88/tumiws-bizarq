package pe.com.tumi.servicio.prevision.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AutorizaLiquidacion extends TumiDomain{
	private AutorizaLiquidacionId id;

	private Integer 	intPersEmpresaPk;
	private Integer 	intParaEstadoAutorizar;
	private Integer 	intParaTipoAureobCod;
	private Integer 	intPersEmpresaAutoriza;
	private Integer		intPersUsuarioAutoriza;
	private Timestamp 	tsFechaAutorizacion;
	private String 	  	strObservacion;
	private Integer 	intIdPerfilAutoriza;
	private Integer 	intParaEstado;
	
	
	public AutorizaLiquidacionId getId() {
		return id;
	}
	public void setId(AutorizaLiquidacionId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntParaEstadoAutorizar() {
		return intParaEstadoAutorizar;
	}
	public void setIntParaEstadoAutorizar(Integer intParaEstadoAutorizar) {
		this.intParaEstadoAutorizar = intParaEstadoAutorizar;
	}
	public Integer getIntParaTipoAureobCod() {
		return intParaTipoAureobCod;
	}
	public void setIntParaTipoAureobCod(Integer intParaTipoAureobCod) {
		this.intParaTipoAureobCod = intParaTipoAureobCod;
	}
	public Integer getIntPersEmpresaAutoriza() {
		return intPersEmpresaAutoriza;
	}
	public void setIntPersEmpresaAutoriza(Integer intPersEmpresaAutoriza) {
		this.intPersEmpresaAutoriza = intPersEmpresaAutoriza;
	}
	public Integer getIntPersUsuarioAutoriza() {
		return intPersUsuarioAutoriza;
	}
	public void setIntPersUsuarioAutoriza(Integer intPersUsuarioAutoriza) {
		this.intPersUsuarioAutoriza = intPersUsuarioAutoriza;
	}
	public Timestamp getTsFechaAutorizacion() {
		return tsFechaAutorizacion;
	}
	public void setTsFechaAutorizacion(Timestamp tsFechaAutorizacion) {
		this.tsFechaAutorizacion = tsFechaAutorizacion;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntIdPerfilAutoriza() {
		return intIdPerfilAutoriza;
	}
	public void setIntIdPerfilAutoriza(Integer intIdPerfilAutoriza) {
		this.intIdPerfilAutoriza = intIdPerfilAutoriza;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	
	
}
