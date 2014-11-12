package pe.com.tumi.contabilidad.legalizacion.domain;

import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

@SuppressWarnings("serial")
public class LibroLegalizacion extends TumiDomain{

	private LibroLegalizacionId	id;
	private	Integer	intPersEmpresaLegal;
	private	Integer	intPersPersona;
	private Date	dtFechaLegalizacion;
	private	Integer	intNroCertificado;
	private Integer	intFolioInicio;
	private	Integer intFolioFin;
	private Integer intParaTipo;
	private Integer intItemArchivo;
	private Integer intItemHistorico;

	public LibroLegalizacion(){
		id = new LibroLegalizacionId();
	}

	public LibroLegalizacionId getId() {
		return id;
	}

	public void setId(LibroLegalizacionId id) {
		this.id = id;
	}

	public Integer getIntPersEmpresaLegal() {
		return intPersEmpresaLegal;
	}
	public void setIntPersEmpresaLegal(Integer intPersEmpresaLegal) {
		this.intPersEmpresaLegal = intPersEmpresaLegal;
	}
	public Integer getIntPersPersona() {
		return intPersPersona;
	}
	public void setIntPersPersona(Integer intPersPersona) {
		this.intPersPersona = intPersPersona;
	}
	public Date getDtFechaLegalizacion() {
		return dtFechaLegalizacion;
	}
	public void setDtFechaLegalizacion(Date dtFechaLegalizacion) {
		this.dtFechaLegalizacion = dtFechaLegalizacion;
	}
	public Integer getIntNroCertificado() {
		return intNroCertificado;
	}
	public void setIntNroCertificado(Integer intNroCertificado) {
		this.intNroCertificado = intNroCertificado;
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
	public Integer getIntParaTipo() {
		return intParaTipo;
	}
	public void setIntParaTipo(Integer intParaTipo) {
		this.intParaTipo = intParaTipo;
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

	@Override
	public String toString() {
		return "LibroLegalizacion [id=" + id + ", intPersEmpresaLegal="
				+ intPersEmpresaLegal + ", intPersPersona=" + intPersPersona
				+ ", dtFechaLegalizacion=" + dtFechaLegalizacion
				+ ", intNroCertificado=" + intNroCertificado
				+ ", intFolioInicio=" + intFolioInicio + ", intFolioFin="
				+ intFolioFin + ", intParaTipo=" + intParaTipo
				+ ", intItemArchivo=" + intItemArchivo + ", intItemHistorico="
				+ intItemHistorico + "]";
	}
}
