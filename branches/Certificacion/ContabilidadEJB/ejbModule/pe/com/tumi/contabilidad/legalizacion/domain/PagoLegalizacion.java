package pe.com.tumi.contabilidad.legalizacion.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PagoLegalizacion extends TumiDomain{

	private PagoLegalizacionId id;
	private Integer		intParaTipoDocumento;
	private Integer 	intParaTipoMoneda;
	private BigDecimal 	bdMonto;
	private Integer 	intParaEstado;
	private Integer		intParaEstadoPago;
	private Integer		intPersEmpresaLibro;
	private Integer		intContPeriodoLibro;
	private Integer 	intContCodigoLibro;
	private Integer		intPersEmpresaLibroAnula;
	private Integer 	intContPeriodoLibroAnula;
	private Integer		intContCodigoLibroAnula;
	
	private LibroLegalizacion	libroLegalizacion;
	
	public PagoLegalizacion(){
		id = new PagoLegalizacionId();
	}
	
	public PagoLegalizacionId getId() {
		return id;
	}
	public void setId(PagoLegalizacionId id) {
		this.id = id;
	}
	public Integer getIntParaTipoDocumento() {
		return intParaTipoDocumento;
	}
	public void setIntParaTipoDocumento(Integer intParaTipoDocumento) {
		this.intParaTipoDocumento = intParaTipoDocumento;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntParaEstadoPago() {
		return intParaEstadoPago;
	}
	public void setIntParaEstadoPago(Integer intParaEstadoPago) {
		this.intParaEstadoPago = intParaEstadoPago;
	}
	public Integer getIntPersEmpresaLibro() {
		return intPersEmpresaLibro;
	}
	public void setIntPersEmpresaLibro(Integer intPersEmpresaLibro) {
		this.intPersEmpresaLibro = intPersEmpresaLibro;
	}
	public Integer getIntContPeriodoLibro() {
		return intContPeriodoLibro;
	}
	public void setIntContPeriodoLibro(Integer intContPeriodoLibro) {
		this.intContPeriodoLibro = intContPeriodoLibro;
	}
	public Integer getIntContCodigoLibro() {
		return intContCodigoLibro;
	}
	public void setIntContCodigoLibro(Integer intContCodigoLibro) {
		this.intContCodigoLibro = intContCodigoLibro;
	}
	public Integer getIntPersEmpresaLibroAnula() {
		return intPersEmpresaLibroAnula;
	}
	public void setIntPersEmpresaLibroAnula(Integer intPersEmpresaLibroAnula) {
		this.intPersEmpresaLibroAnula = intPersEmpresaLibroAnula;
	}
	public Integer getIntContPeriodoLibroAnula() {
		return intContPeriodoLibroAnula;
	}
	public void setIntContPeriodoLibroAnula(Integer intContPeriodoLibroAnula) {
		this.intContPeriodoLibroAnula = intContPeriodoLibroAnula;
	}
	public Integer getIntContCodigoLibroAnula() {
		return intContCodigoLibroAnula;
	}
	public void setIntContCodigoLibroAnula(Integer intContCodigoLibroAnula) {
		this.intContCodigoLibroAnula = intContCodigoLibroAnula;
	}
	public LibroLegalizacion getLibroLegalizacion() {
		return libroLegalizacion;
	}
	public void setLibroLegalizacion(LibroLegalizacion libroLegalizacion) {
		this.libroLegalizacion = libroLegalizacion;
	}
	@Override
	public String toString() {
		return "PagoLegalizacion [id=" + id + ", intParaTipoDocumento="
				+ intParaTipoDocumento + ", intParaTipoMoneda="
				+ intParaTipoMoneda + ", bdMonto=" + bdMonto
				+ ", intParaEstado=" + intParaEstado + ", intParaEstadoPago="
				+ intParaEstadoPago + ", intPersEmpresaLibro="
				+ intPersEmpresaLibro + ", intContPeriodoLibro="
				+ intContPeriodoLibro + ", intContCodigoLibro="
				+ intContCodigoLibro + ", intPersEmpresaLibroAnula="
				+ intPersEmpresaLibroAnula + ", intContPeriodoLibroAnula="
				+ intContPeriodoLibroAnula + ", intContCodigoLibroAnula="
				+ intContCodigoLibroAnula + "]";
	}
	
}
