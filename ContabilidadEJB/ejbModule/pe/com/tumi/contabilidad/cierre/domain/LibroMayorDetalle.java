package pe.com.tumi.contabilidad.cierre.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class LibroMayorDetalle extends TumiDomain{

	private LibroMayorDetalleId id;
	private BigDecimal bdDebeSolesSaldo;
	private BigDecimal bdHaberSolesSaldo;
	private BigDecimal bdDebeExtranjeroSaldo;
	private BigDecimal bdHaberExtranjeroSaldo;
	private BigDecimal bdDebeSoles;
	private BigDecimal bdHaberSoles;
	private BigDecimal bdDebeExtranjero;
	private BigDecimal bdHaberExtranjero;

	
	private List<LibroDiarioDetalle> listaLibroDiarioDetalle;
	
	public LibroMayorDetalle(){
		id = new LibroMayorDetalleId();
		listaLibroDiarioDetalle = new ArrayList<LibroDiarioDetalle>();
	}
	
	public LibroMayorDetalleId getId() {
		return id;
	}
	public void setId(LibroMayorDetalleId id) {
		this.id = id;
	}
	public BigDecimal getBdDebeSolesSaldo() {
		return bdDebeSolesSaldo;
	}
	public void setBdDebeSolesSaldo(BigDecimal bdDebeSolesSaldo) {
		this.bdDebeSolesSaldo = bdDebeSolesSaldo;
	}
	public BigDecimal getBdHaberSolesSaldo() {
		return bdHaberSolesSaldo;
	}
	public void setBdHaberSolesSaldo(BigDecimal bdHaberSolesSaldo) {
		this.bdHaberSolesSaldo = bdHaberSolesSaldo;
	}
	public BigDecimal getBdDebeExtranjeroSaldo() {
		return bdDebeExtranjeroSaldo;
	}
	public void setBdDebeExtranjeroSaldo(BigDecimal bdDebeExtranjeroSaldo) {
		this.bdDebeExtranjeroSaldo = bdDebeExtranjeroSaldo;
	}
	public BigDecimal getBdHaberExtranjeroSaldo() {
		return bdHaberExtranjeroSaldo;
	}
	public void setBdHaberExtranjeroSaldo(BigDecimal bdHaberExtranjeroSaldo) {
		this.bdHaberExtranjeroSaldo = bdHaberExtranjeroSaldo;
	}
	public BigDecimal getBdDebeSoles() {
		return bdDebeSoles;
	}
	public void setBdDebeSoles(BigDecimal bdDebeSoles) {
		this.bdDebeSoles = bdDebeSoles;
	}
	public BigDecimal getBdHaberSoles() {
		return bdHaberSoles;
	}
	public void setBdHaberSoles(BigDecimal bdHaberSoles) {
		this.bdHaberSoles = bdHaberSoles;
	}
	public BigDecimal getBdDebeExtranjero() {
		return bdDebeExtranjero;
	}
	public void setBdDebeExtranjero(BigDecimal bdDebeExtranjero) {
		this.bdDebeExtranjero = bdDebeExtranjero;
	}
	public BigDecimal getBdHaberExtranjero() {
		return bdHaberExtranjero;
	}
	public void setBdHaberExtranjero(BigDecimal bdHaberExtranjero) {
		this.bdHaberExtranjero = bdHaberExtranjero;
	}
	public List<LibroDiarioDetalle> getListaLibroDiarioDetalle() {
		return listaLibroDiarioDetalle;
	}
	public void setListaLibroDiarioDetalle(
			List<LibroDiarioDetalle> listaLibroDiarioDetalle) {
		this.listaLibroDiarioDetalle = listaLibroDiarioDetalle;
	}
	@Override
	public String toString() {
		return "LibroMayorDetalle [id=" + id + ", bdDebeSolesSaldo="
				+ bdDebeSolesSaldo + ", bdHaberSolesSaldo=" + bdHaberSolesSaldo
				+ ", bdDebeExtranjeroSaldo=" + bdDebeExtranjeroSaldo
				+ ", bdHaberExtranjeroSaldo=" + bdHaberExtranjeroSaldo
				+ ", bdDebeSoles=" + bdDebeSoles + ", bdHaberSoles="
				+ bdHaberSoles + ", bdDebeExtranjero=" + bdDebeExtranjero
				+ ", bdHaberExtranjero=" + bdHaberExtranjero
				;
	}
	
}
