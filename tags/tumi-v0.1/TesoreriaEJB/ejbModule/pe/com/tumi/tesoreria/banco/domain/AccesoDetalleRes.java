package pe.com.tumi.tesoreria.banco.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class AccesoDetalleRes extends TumiDomain implements Comparable<AccesoDetalleRes>{

	private AccesoDetalleResId id;
	private	Integer 	intPersEmpresaResponsable;
	private	Integer 	intPersPersonaResponsable;
	private	Integer 	intOrden;
	private	Integer 	intParaEstado;
	private	Timestamp 	tsFechaRegistro;
	private	Integer 	intPersEmpresaUsuario;
	private	Integer 	intPersPersonaUsuario;
	private	Timestamp 	tsFechaEliminacion;
	private	Integer 	intPersEmpresaElimina;
	private	Integer 	intPersPersonaElimina;
	
	//Interfaz
	private Persona		persona;
	
	public AccesoDetalleRes(){
		id = new AccesoDetalleResId();
	}
	
	public AccesoDetalleResId getId() {
		return id;
	}
	public void setId(AccesoDetalleResId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaResponsable() {
		return intPersEmpresaResponsable;
	}
	public void setIntPersEmpresaResponsable(Integer intPersEmpresaResponsable) {
		this.intPersEmpresaResponsable = intPersEmpresaResponsable;
	}
	public Integer getIntPersPersonaResponsable() {
		return intPersPersonaResponsable;
	}
	public void setIntPersPersonaResponsable(Integer intPersPersonaResponsable) {
		this.intPersPersonaResponsable = intPersPersonaResponsable;
	}
	public Integer getIntOrden() {
		return intOrden;
	}
	public void setIntOrden(Integer intOrden) {
		this.intOrden = intOrden;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersEmpresaUsuario() {
		return intPersEmpresaUsuario;
	}
	public void setIntPersEmpresaUsuario(Integer intPersEmpresaUsuario) {
		this.intPersEmpresaUsuario = intPersEmpresaUsuario;
	}
	public Integer getIntPersPersonaUsuario() {
		return intPersPersonaUsuario;
	}
	public void setIntPersPersonaUsuario(Integer intPersPersonaUsuario) {
		this.intPersPersonaUsuario = intPersPersonaUsuario;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Integer getIntPersEmpresaElimina() {
		return intPersEmpresaElimina;
	}
	public void setIntPersEmpresaElimina(Integer intPersEmpresaElimina) {
		this.intPersEmpresaElimina = intPersEmpresaElimina;
	}
	public Integer getIntPersPersonaElimina() {
		return intPersPersonaElimina;
	}
	public void setIntPersPersonaElimina(Integer intPersPersonaElimina) {
		this.intPersPersonaElimina = intPersPersonaElimina;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	public int compareTo(AccesoDetalleRes otro) {
        if(otro.getIntOrden()==null){
        	otro.setIntOrden(0);
        }
		return getIntOrden().compareTo(otro.getIntOrden());
    }
	@Override
	public String toString() {
		return "AccesoDetalleRes [id=" + id + ", intPersEmpresaResponsable="
				+ intPersEmpresaResponsable + ", intPersPersonaResponsable="
				+ intPersPersonaResponsable + ", intOrden=" + intOrden
				+ ", intParaEstado=" + intParaEstado + ", tsFechaRegistro="
				+ tsFechaRegistro + ", intPersEmpresaUsuario="
				+ intPersEmpresaUsuario + ", intPersPersonaUsuario="
				+ intPersPersonaUsuario + ", tsFechaEliminacion="
				+ tsFechaEliminacion + ", intPersEmpresaElimina="
				+ intPersEmpresaElimina + ", intPersPersonaElimina="
				+ intPersPersonaElimina + "]";
	}
}