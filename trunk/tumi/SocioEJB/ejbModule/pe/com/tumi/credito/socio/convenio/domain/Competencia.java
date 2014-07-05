package pe.com.tumi.credito.socio.convenio.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Competencia extends TumiDomain {

	private CompetenciaId id;
	//private Adenda adenda;
	private Integer intConvenio;
	private Integer intItemConvenio;
	private String strDescripcionEntidad;
	private Integer intCantidad;
	private BigDecimal bdPlazoPrestamo;
	private BigDecimal bdTasaPrestamo;
	private BigDecimal bdAporte;
	private String strServicio;
	private Integer intParaEstadoCod;
	
	public CompetenciaId getId() {
		return id;
	}
	public void setId(CompetenciaId id) {
		this.id = id;
	}
	public Integer getIntConvenio() {
		return intConvenio;
	}
	public void setIntConvenio(Integer intConvenio) {
		this.intConvenio = intConvenio;
	}
	public Integer getIntItemConvenio() {
		return intItemConvenio;
	}
	public void setIntItemConvenio(Integer intItemConvenio) {
		this.intItemConvenio = intItemConvenio;
	}
	public String getStrDescripcionEntidad() {
		return strDescripcionEntidad;
	}
	public void setStrDescripcionEntidad(String strDescripcionEntidad) {
		this.strDescripcionEntidad = strDescripcionEntidad;
	}
	public Integer getIntCantidad() {
		return intCantidad;
	}
	public void setIntCantidad(Integer intCantidad) {
		this.intCantidad = intCantidad;
	}
	public BigDecimal getBdPlazoPrestamo() {
		return bdPlazoPrestamo;
	}
	public void setBdPlazoPrestamo(BigDecimal bdPlazoPrestamo) {
		this.bdPlazoPrestamo = bdPlazoPrestamo;
	}
	public BigDecimal getBdTasaPrestamo() {
		return bdTasaPrestamo;
	}
	public void setBdTasaPrestamo(BigDecimal bdTasaPrestamo) {
		this.bdTasaPrestamo = bdTasaPrestamo;
	}
	public BigDecimal getBdAporte() {
		return bdAporte;
	}
	public void setBdAporte(BigDecimal bdAporte) {
		this.bdAporte = bdAporte;
	}
	public String getStrServicio() {
		return strServicio;
	}
	public void setStrServicio(String strServicio) {
		this.strServicio = strServicio;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	
}
