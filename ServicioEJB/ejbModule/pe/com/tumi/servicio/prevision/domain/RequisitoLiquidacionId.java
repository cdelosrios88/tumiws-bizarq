package pe.com.tumi.servicio.prevision.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RequisitoLiquidacionId extends TumiDomain{
	private Integer 	intPersEmpresaLiquidacion;
	private Integer 	intItemExpediente;
	private Integer		intItemRequisito;
	
	public Integer getIntPersEmpresaLiquidacion() {
		return intPersEmpresaLiquidacion;
	}
	public void setIntPersEmpresaLiquidacion(Integer intPersEmpresaLiquidacion) {
		this.intPersEmpresaLiquidacion = intPersEmpresaLiquidacion;
	}
	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}
	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}
	public Integer getIntItemRequisito() {
		return intItemRequisito;
	}
	public void setIntItemRequisito(Integer intItemRequisito) {
		this.intItemRequisito = intItemRequisito;
	}
	
	
	
}
