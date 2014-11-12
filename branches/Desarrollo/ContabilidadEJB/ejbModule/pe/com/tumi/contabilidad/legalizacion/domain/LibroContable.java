package pe.com.tumi.contabilidad.legalizacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import java.sql.Timestamp;
import java.util.List;

@SuppressWarnings("serial")
public class LibroContable extends TumiDomain {

	private LibroContableId id;
	private Integer intTipoLibro;
	private Integer intPeriodoLibro;
	private Integer intTiempoLibre;
	private Integer intTipoTiempo;
	private Integer intPaginasLibro;
	private Timestamp tsFechaInicio;
	private Timestamp tsFechaFin;
	private List<LibroContableDetalle> listLibroContableDetalle;
	private List<LibroLegalizacion> listLibroLegalizacion;
	
	public LibroContable(){
		id = new LibroContableId();
	}
	
	public LibroContableId getId() {
		return id;
	}
	public void setId(LibroContableId id) {
		this.id = id;
	}
	public Integer getIntTipoLibro() {
		return intTipoLibro;
	}
	public void setIntTipoLibro(Integer intTipoLibro) {
		this.intTipoLibro = intTipoLibro;
	}
	public Integer getIntPeriodoLibro() {
		return intPeriodoLibro;
	}
	public void setIntPeriodoLibro(Integer intPeriodoLibro) {
		this.intPeriodoLibro = intPeriodoLibro;
	}
	public Integer getIntTiempoLibre() {
		return intTiempoLibre;
	}
	public void setIntTiempoLibre(Integer intTiempoLibre) {
		this.intTiempoLibre = intTiempoLibre;
	}
	public Integer getIntTipoTiempo() {
		return intTipoTiempo;
	}
	public void setIntTipoTiempo(Integer intTipoTiempo) {
		this.intTipoTiempo = intTipoTiempo;
	}
	public Integer getIntPaginasLibro() {
		return intPaginasLibro;
	}
	public void setIntPaginasLibro(Integer intPaginasLibro) {
		this.intPaginasLibro = intPaginasLibro;
	}
	public Timestamp getTsFechaInicio() {
		return tsFechaInicio;
	}
	public void setTsFechaInicio(Timestamp tsFechaInicio) {
		this.tsFechaInicio = tsFechaInicio;
	}
	public Timestamp getTsFechaFin() {
		return tsFechaFin;
	}
	public void setTsFechaFin(Timestamp tsFechaFin) {
		this.tsFechaFin = tsFechaFin;
	}

	public List<LibroContableDetalle> getListLibroContableDetalle() {
		return listLibroContableDetalle;
	}

	public void setListLibroContableDetalle(
			List<LibroContableDetalle> listLibroContableDetalle) {
		this.listLibroContableDetalle = listLibroContableDetalle;
	}

	public List<LibroLegalizacion> getListLibroLegalizacion() {
		return listLibroLegalizacion;
	}

	public void setListLibroLegalizacion(
			List<LibroLegalizacion> listLibroLegalizacion) {
		this.listLibroLegalizacion = listLibroLegalizacion;
	}
	
	@Override
	public String toString() {
		return "LibroContable [id=" + id + ", intTipoLibro=" + intTipoLibro
				+ ", intPeriodoLibro=" + intPeriodoLibro + ", intTiempoLibre="
				+ intTiempoLibre + ", intTipoTiempo=" + intTipoTiempo
				+ ", intPaginasLibro=" + intPaginasLibro + ", tsFechaInicio="
				+ tsFechaInicio + ", tsFechaFin=" + tsFechaFin
				+ ", listLibroContableDetalle=" + listLibroContableDetalle
				+ "]";
	}
}