package pe.com.tumi.servicio.solicitudPrestamo.domain.composite;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RequisitoCreditoComp2 extends TumiDomain {
	
	private Integer intEmpresa;
	private Integer intItemRequisito;
	private Integer intItemRequisitoDetalle;
	private String strDescripcionRequisito;
	private Integer intParaTipoCredito;
	private Integer intItemCredito;
	
	public Integer getIntEmpresa() {
		return intEmpresa;
	}
	public void setIntEmpresa(Integer intEmpresa) {
		this.intEmpresa = intEmpresa;
	}
	public Integer getIntItemRequisito() {
		return intItemRequisito;
	}
	public void setIntItemRequisito(Integer intItemRequisito) {
		this.intItemRequisito = intItemRequisito;
	}
	public Integer getIntItemRequisitoDetalle() {
		return intItemRequisitoDetalle;
	}
	public void setIntItemRequisitoDetalle(Integer intItemRequisitoDetalle) {
		this.intItemRequisitoDetalle = intItemRequisitoDetalle;
	}
	public String getStrDescripcionRequisito() {
		return strDescripcionRequisito;
	}
	public void setStrDescripcionRequisito(String strDescripcionRequisito) {
		this.strDescripcionRequisito = strDescripcionRequisito;
	}
	public Integer getIntParaTipoCredito() {
		return intParaTipoCredito;
	}
	public void setIntParaTipoCredito(Integer intParaTipoCredito) {
		this.intParaTipoCredito = intParaTipoCredito;
	}
	public Integer getIntItemCredito() {
		return intItemCredito;
	}
	public void setIntItemCredito(Integer intItemCredito) {
		this.intItemCredito = intItemCredito;
	}
}
