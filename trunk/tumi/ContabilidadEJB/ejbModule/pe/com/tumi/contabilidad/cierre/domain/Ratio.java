package pe.com.tumi.contabilidad.cierre.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Ratio extends TumiDomain{

	private RatioId	id;
	private	Integer	intParaTipoRatio;
	private String 	strNombreRatio;
	private	String	strDescripcionRatio;
	private Integer	intParaPeriodicidad;
	private	Integer	intParaUnidadMedida;
	private	Integer	intParaMoneda;
	private	Timestamp	tsFechaRegistro;
	private	Integer	intParaEstado;
	private	Integer	intParaTipoOperacion;
	
	private List<RatioDetalle> listaRatioDetalle;
	
	
	public Ratio(){
		id = new RatioId();
		listaRatioDetalle = new ArrayList<RatioDetalle>();
	}
	
	public RatioId getId() {
		return id;
	}
	public void setId(RatioId id) {
		this.id = id;
	}
	public Integer getIntParaTipoRatio() {
		return intParaTipoRatio;
	}
	public void setIntParaTipoRatio(Integer intParaTipoRatio) {
		this.intParaTipoRatio = intParaTipoRatio;
	}
	public String getStrNombreRatio() {
		return strNombreRatio;
	}
	public void setStrNombreRatio(String strNombreRatio) {
		this.strNombreRatio = strNombreRatio;
	}
	public String getStrDescripcionRatio() {
		return strDescripcionRatio;
	}
	public void setStrDescripcionRatio(String strDescripcionRatio) {
		this.strDescripcionRatio = strDescripcionRatio;
	}
	public Integer getIntParaPeriodicidad() {
		return intParaPeriodicidad;
	}
	public void setIntParaPeriodicidad(Integer intParaPeriodicidad) {
		this.intParaPeriodicidad = intParaPeriodicidad;
	}
	public Integer getIntParaUnidadMedida() {
		return intParaUnidadMedida;
	}
	public void setIntParaUnidadMedida(Integer intParaUnidadMedida) {
		this.intParaUnidadMedida = intParaUnidadMedida;
	}
	public Integer getIntParaMoneda() {
		return intParaMoneda;
	}
	public void setIntParaMoneda(Integer intParaMoneda) {
		this.intParaMoneda = intParaMoneda;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public List<RatioDetalle> getListaRatioDetalle() {
		return listaRatioDetalle;
	}
	public void setListaRatioDetalle(List<RatioDetalle> listaRatioDetalle) {
		this.listaRatioDetalle = listaRatioDetalle;
	}
	public Integer getIntParaTipoOperacion() {
		return intParaTipoOperacion;
	}
	public void setIntParaTipoOperacion(Integer intParaTipoOperacion) {
		this.intParaTipoOperacion = intParaTipoOperacion;
	}
	@Override
	public String toString() {
		return "Ratio [id=" + id + ", intParaTipoRatio=" + intParaTipoRatio
				+ ", strNombreRatio=" + strNombreRatio
				+ ", strDescripcionRatio=" + strDescripcionRatio
				+ ", intParaPeriodicidad=" + intParaPeriodicidad
				+ ", intParaUnidadMedida=" + intParaUnidadMedida
				+ ", intParaMoneda=" + intParaMoneda + ", tsFechaRegistro="
				+ tsFechaRegistro + ", intParaEstado=" + intParaEstado + "]";
	}
}
