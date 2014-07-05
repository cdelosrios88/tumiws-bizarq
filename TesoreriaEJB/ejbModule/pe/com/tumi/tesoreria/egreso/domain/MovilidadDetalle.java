package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelComp;
import pe.com.tumi.framework.negocio.domain.TumiDomain;


public class MovilidadDetalle extends TumiDomain{

	private MovilidadDetalleId id;
	private Date		dtFechaMovilidad;
	private Integer		intParaTipoMovilidad;
	private String		strMotivo;
	private String		strDestino;
	private BigDecimal	bdMonto;
	private Integer		intParaEstado;
	private Timestamp	tsFechaRegistro;
	private Integer		intPersEmpresaUsuario;
	private Integer		intPersPersonaUsuario;
	private Timestamp	tsFechaEliminacion;
	private Integer		intPersEmpresaEliminacion;
	private Integer		intPersPersonaEliminacion;
	
	//JCHAVEZ 23.01.2014
	private List<ModeloDetalleNivelComp> listaModeloDetalleNivelComp;
	
	public MovilidadDetalle(){
		id = new MovilidadDetalleId();
	}
	
	public MovilidadDetalleId getId() {
		return id;
	}
	public void setId(MovilidadDetalleId id) {
		this.id = id;
	}
	public Date getDtFechaMovilidad() {
		return dtFechaMovilidad;
	}
	public void setDtFechaMovilidad(Date dtFechaMovilidad) {
		this.dtFechaMovilidad = dtFechaMovilidad;
	}
	public Integer getIntParaTipoMovilidad() {
		return intParaTipoMovilidad;
	}
	public void setIntParaTipoMovilidad(Integer intParaTipoMovilidad) {
		this.intParaTipoMovilidad = intParaTipoMovilidad;
	}
	public String getStrMotivo() {
		return strMotivo;
	}
	public void setStrMotivo(String strMotivo) {
		this.strMotivo = strMotivo;
	}
	public String getStrDestino() {
		return strDestino;
	}
	public void setStrDestino(String strDestino) {
		this.strDestino = strDestino;
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
	public Integer getIntPersEmpresaEliminacion() {
		return intPersEmpresaEliminacion;
	}
	public void setIntPersEmpresaEliminacion(Integer intPersEmpresaEliminacion) {
		this.intPersEmpresaEliminacion = intPersEmpresaEliminacion;
	}
	public Integer getIntPersPersonaEliminacion() {
		return intPersPersonaEliminacion;
	}
	public void setIntPersPersonaEliminacion(Integer intPersPersonaEliminacion) {
		this.intPersPersonaEliminacion = intPersPersonaEliminacion;
	}
	@Override
	public String toString() {
		return "MovilidadDetalle [id=" + id + ", dtFechaMovilidad="
				+ dtFechaMovilidad + ", intParaTipoMovilidad="
				+ intParaTipoMovilidad + ", strMotivo=" + strMotivo
				+ ", strDestino=" + strDestino + ", bdMonto=" + bdMonto
				+ ", intParaEstado=" + intParaEstado + ", tsFechaRegistro="
				+ tsFechaRegistro + ", intPersEmpresaUsuario="
				+ intPersEmpresaUsuario + ", intPersPersonaUsuario="
				+ intPersPersonaUsuario + ", tsFechaEliminacion="
				+ tsFechaEliminacion + ", intPersEmpresaEliminacion="
				+ intPersEmpresaEliminacion + ", intPersPersonaEliminacion="
				+ intPersPersonaEliminacion + "]";
	}

	public List<ModeloDetalleNivelComp> getListaModeloDetalleNivelComp() {
		return listaModeloDetalleNivelComp;
	}

	public void setListaModeloDetalleNivelComp(
			List<ModeloDetalleNivelComp> listaModeloDetalleNivelComp) {
		this.listaModeloDetalleNivelComp = listaModeloDetalleNivelComp;
	}
	
}
