package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class AccesoEspecialDetalle extends TumiDomain{
	
	private AccesoEspecialDetalleId	id;
	private Integer 	intIdEstado;
	private Timestamp	tsHoraInicio;
	private Timestamp	tsHoraFin;
	private Tabla		dia;
	
	public AccesoEspecialDetalle(){
		super();
		id = new AccesoEspecialDetalleId();
		dia = new Tabla();
	}
	
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Timestamp getTsHoraInicio() {
		return tsHoraInicio;
	}
	public void setTsHoraInicio(Timestamp tsHoraInicio) {
		this.tsHoraInicio = tsHoraInicio;
	}
	public Timestamp getTsHoraFin() {
		return tsHoraFin;
	}
	public void setTsHoraFin(Timestamp tsHoraFin) {
		this.tsHoraFin = tsHoraFin;
	}
	public AccesoEspecialDetalleId getId() {
		return id;
	}
	public void setId(AccesoEspecialDetalleId id) {
		this.id = id;
	}
	
	public Tabla getDia() {
		return dia;
	}

	public void setDia(Tabla dia) {
		this.dia = dia;
	}

	@Override
	public String toString() {
		return "AccesoEspecialDetalle [id=" + id + ", intIdEstado="
				+ intIdEstado + ", tsHoraInicio=" + tsHoraInicio
				+ ", tsHoraFin=" + tsHoraFin + "]";
	}
}