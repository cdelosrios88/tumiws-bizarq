package pe.com.tumi.contabilidad.legalizacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

@SuppressWarnings("serial")
public class LibroContableDetalle extends TumiDomain {
	private LibroContableDetalleId id;
	private Integer intPeriodo;
	private Integer intFolioInicio;
	private Integer intFolioFin;
	private Integer intItemLibroLegalizacion;
	private Integer intTipo;
	private Integer intItemArchivo;
	private Integer intItemHistorico;

	private LibroContable libroContable;
	
	public LibroContableDetalle(){
		id = new LibroContableDetalleId();
	}
	
	public LibroContableDetalleId getId() {
		return id;
	}
	public void setId(LibroContableDetalleId id) {
		this.id = id;
	}
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}
	public Integer getIntFolioInicio() {
		return intFolioInicio;
	}
	public void setIntFolioInicio(Integer intFolioInicio) {
		this.intFolioInicio = intFolioInicio;
	}
	public Integer getIntFolioFin() {
		return intFolioFin;
	}
	public void setIntFolioFin(Integer intFolioFin) {
		this.intFolioFin = intFolioFin;
	}
	public Integer getIntItemLibroLegalizacion() {
		return intItemLibroLegalizacion;
	}
	public void setIntItemLibroLegalizacion(Integer intItemLibroLegalizacion) {
		this.intItemLibroLegalizacion = intItemLibroLegalizacion;
	}
	public Integer getIntTipo() {
		return intTipo;
	}
	public void setIntTipo(Integer intTipo) {
		this.intTipo = intTipo;
	}
	public Integer getIntItemArchivo() {
		return intItemArchivo;
	}
	public void setIntItemArchivo(Integer intItemArchivo) {
		this.intItemArchivo = intItemArchivo;
	}
	public Integer getIntItemHistorico() {
		return intItemHistorico;
	}
	public void setIntItemHistorico(Integer intItemHistorico) {
		this.intItemHistorico = intItemHistorico;
	}
	public LibroContable getLibroContable() {
		return libroContable;
	}
	public void setLibroContable(LibroContable libroContable) {
		this.libroContable = libroContable;
	}
	@Override
	public String toString() {
		return "LibroContableDetalle [id=" + id + ", intPeriodo=" + intPeriodo
				+ ", intFolioInicio=" + intFolioInicio + ", intFolioFin="
				+ intFolioFin + ", intItemLibroLegalizacion="
				+ intItemLibroLegalizacion + ", intTipo=" + intTipo
				+ ", intItemArchivo=" + intItemArchivo + ", intItemHistorico="
				+ intItemHistorico + ", libroContable=" + libroContable + "]";
	}
}
