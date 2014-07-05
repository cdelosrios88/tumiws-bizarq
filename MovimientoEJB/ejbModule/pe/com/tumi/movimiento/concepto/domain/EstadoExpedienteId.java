package pe.com.tumi.movimiento.concepto.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstadoExpedienteId extends TumiDomain{
	private Integer intEmpresaEstado;
	private Integer intItemEstado;
	
	
	
	public Integer getIntEmpresaEstado() {
		return intEmpresaEstado;
	}
	public void setIntEmpresaEstado(Integer intEmpresaEstado) {
		this.intEmpresaEstado = intEmpresaEstado;
	}
	public Integer getIntItemEstado() {
		return intItemEstado;
	}
	public void setIntItemEstado(Integer intItemEstado) {
		this.intItemEstado = intItemEstado;
	}

}
