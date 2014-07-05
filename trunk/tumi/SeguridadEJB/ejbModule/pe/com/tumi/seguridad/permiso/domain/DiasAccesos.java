package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.empresa.domain.Empresa;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class DiasAccesos  extends TumiDomain {

	private DiasAccesosId id;
	private Timestamp tsFechaInicio;
	private Timestamp tsFechaFin;
	private Integer intFeriados;
	private String strMotivo;
	private Integer intParaTipoArchivo;
	private Integer intParaItemArchivo;
	private Integer intParaItemHistorico;
	private Integer intIdEstado;
	private Timestamp tsFechaRegistro;
	private Timestamp tsFechaEliminacion;
	private Juridica juridica;
	private Archivo archivo;
	

	public Juridica getJuridica() {
		return juridica;
	}

	public void setJuridica(Juridica juridica) {
		this.juridica = juridica;
	}

	public DiasAccesos(){
		super();
		id = new DiasAccesosId();
		juridica = new Juridica();
		archivo = new Archivo();
	}	

	public Integer getIntFeriados() {
		return intFeriados;
	}
	public void setIntFeriados(Integer intFeriados) {
		this.intFeriados = intFeriados;
	}
	public String getStrMotivo() {
		return strMotivo;
	}
	public void setStrMotivo(String strMotivo) {
		this.strMotivo = strMotivo;
	}
	public Integer getIntParaTipoArchivo() {
		return intParaTipoArchivo;
	}
	public void setIntParaTipoArchivo(Integer intParaTipoArchivo) {
		this.intParaTipoArchivo = intParaTipoArchivo;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public DiasAccesosId getId() {
		return id;
	}
	public void setId(DiasAccesosId id) {
		this.id = id;
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
	public Integer getIntParaItemArchivo() {
		return intParaItemArchivo;
	}
	public void setIntParaItemArchivo(Integer intParaItemArchivo) {
		this.intParaItemArchivo = intParaItemArchivo;
	}
	public Integer getIntParaItemHistorico() {
		return intParaItemHistorico;
	}
	public void setIntParaItemHistorico(Integer intParaItemHistorico) {
		this.intParaItemHistorico = intParaItemHistorico;
	}	
	public Archivo getArchivo() {
		return archivo;
	}
	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	@Override
	public String toString() {
		return "DiasAccesos [id=" + id + ", tsFechaInicio=" + tsFechaInicio
				+ ", tsFechaFin=" + tsFechaFin + ", intFeriados=" + intFeriados
				+ ", strMotivo=" + strMotivo + ", intParaTipoArchivo="
				+ intParaTipoArchivo + ", intParaItemArchivo="
				+ intParaItemArchivo + ", intParaItemHistorico="
				+ intParaItemHistorico + ", intIdEstado=" + intIdEstado
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", tsFechaEliminacion=" + tsFechaEliminacion + ", juridica="
				+ juridica + "]";
	}

}