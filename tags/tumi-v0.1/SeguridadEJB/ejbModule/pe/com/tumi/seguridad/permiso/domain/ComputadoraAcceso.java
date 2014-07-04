package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class ComputadoraAcceso  extends TumiDomain {

	private ComputadoraAccesoId id;
	private Integer intIdTipoAcceso;
	private Integer intIdEstado;
	private Timestamp tsFechaEliminacion;
	private Tabla acceso;
	private Computadora computadora;
	
	public ComputadoraAcceso(){
		setId(new ComputadoraAccesoId());
	}
	public ComputadoraAccesoId getId() {
		return id;
	}
	public void setId(ComputadoraAccesoId id) {
		this.id = id;
	}
	public Computadora getComputadora() {
		return computadora;
	}
	public void setComputadora(Computadora computadora) {
		this.computadora = computadora;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Tabla getAcceso() {
		return acceso;
	}
	public void setAcceso(Tabla acceso) {
		this.acceso = acceso;
	}
	public Integer getIntIdTipoAcceso() {
		return intIdTipoAcceso;
	}
	public void setIntIdTipoAcceso(Integer intIdTipoAcceso) {
		this.intIdTipoAcceso = intIdTipoAcceso;
	}
	@Override
	public String toString() {
		return "ComputadoraAcceso [id=" + id + ", intIdTipoAcceso="
				+ intIdTipoAcceso + ", intIdEstado=" + intIdEstado
				+ ", tsFechaEliminacion=" + tsFechaEliminacion + ", acceso="
				+ acceso + ", computadora=" + computadora + "]";
	}
	
	
}
