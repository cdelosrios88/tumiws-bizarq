package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AutorizaCredito extends TumiDomain {
	private AutorizaCreditoId id;
	private Integer intParaEstadoAutorizar;
	private Integer intParaTipoAureobCod;
	private Integer intPersEmpresaAutoriza;
	private Integer intPersUsuarioAutoriza;
	private Integer intIdPerfilAutoriza;
	private Timestamp tsFechaAutorizacion;
	public Integer getIntIdPerfilAutoriza() {
		return intIdPerfilAutoriza;
	}
	public void setIntIdPerfilAutoriza(Integer intIdPerfilAutoriza) {
		this.intIdPerfilAutoriza = intIdPerfilAutoriza;
	}
	private String strObservacion;
	private Integer intParaEstadoCod;
	
	public AutorizaCreditoId getId() {
		return id;
	}
	public void setId(AutorizaCreditoId id) {
		this.id = id;
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
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
}