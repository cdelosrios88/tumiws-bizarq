package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerencia;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;

public class DocumentoRequisicion extends TumiDomain {

	private Integer intParaTipoAprobacion;
	
	private Requisicion requisicion;
	private Contrato contrato;
	private InformeGerencia informeGerencia;
	private CuadroComparativo cuadroComparativo;
	
	
	public Integer getIntParaTipoAprobacion() {
		return intParaTipoAprobacion;
	}
	public void setIntParaTipoAprobacion(Integer intParaTipoAprobacion) {
		this.intParaTipoAprobacion = intParaTipoAprobacion;
	}
	public Requisicion getRequisicion() {
		return requisicion;
	}
	public void setRequisicion(Requisicion requisicion) {
		this.requisicion = requisicion;
	}
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	public CuadroComparativo getCuadroComparativo() {
		return cuadroComparativo;
	}
	public void setCuadroComparativo(CuadroComparativo cuadroComparativo) {
		this.cuadroComparativo = cuadroComparativo;
	}
	public InformeGerencia getInformeGerencia() {
		return informeGerencia;
	}
	public void setInformeGerencia(InformeGerencia informeGerencia) {
		this.informeGerencia = informeGerencia;
	}
}