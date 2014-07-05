package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DiasAccesosDetalle extends TumiDomain{

	private DiasAccesosDetalleId id;
	private Timestamp tsHoraInicio;
	private Timestamp tsHoraFin;
	private Integer intIdEstado;
	private Integer intHoraInicio;
	private Integer intMinutoInicio;
	private Integer intHoraFin;
	private Integer intMinutoFin;
	
	public DiasAccesosDetalle(){
		super();
		id = new DiasAccesosDetalleId();
		intHoraInicio = 0;
		intMinutoInicio = 0;
		intHoraFin = 0;
		intMinutoFin = 0;
	}
	
	public DiasAccesosDetalleId getId() {
		return id;
	}
	public void setId(DiasAccesosDetalleId id) {
		this.id = id;
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
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}

	public Integer getIntHoraInicio() {
		return intHoraInicio;
	}
	public void setIntHoraInicio(Integer intHoraInicio) {
		this.intHoraInicio = intHoraInicio;
	}
	public Integer getIntMinutoInicio() {
		return intMinutoInicio;
	}
	public void setIntMinutoInicio(Integer intMinutoInicio) {
		this.intMinutoInicio = intMinutoInicio;
	}
	public Integer getIntHoraFin() {
		return intHoraFin;
	}
	public void setIntHoraFin(Integer intHoraFin) {
		this.intHoraFin = intHoraFin;
	}
	public Integer getIntMinutoFin() {
		return intMinutoFin;
	}
	public void setIntMinutoFin(Integer intMinutoFin) {
		this.intMinutoFin = intMinutoFin;
	}

	@Override
	public String toString() {
		return "DiasAccesosDetalle [id=" + id + ", dtHoraInicio="
				+ tsHoraInicio + ", dtHoraFin=" + tsHoraFin + ", intIdEstado="
				+ intIdEstado + ", intHoraInicio=" + intHoraInicio
				+ ", intMinutoInicio=" + intMinutoInicio + ", intHoraFin="
				+ intHoraFin + ", intMinutoFin=" + intMinutoFin + "]";
	}
	
}
