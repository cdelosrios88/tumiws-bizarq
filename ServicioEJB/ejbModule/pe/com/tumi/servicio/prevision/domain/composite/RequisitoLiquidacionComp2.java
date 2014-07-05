package pe.com.tumi.servicio.prevision.domain.composite;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class RequisitoLiquidacionComp2 extends TumiDomain{
	private Integer intEmpresa;
	private Integer intItemRequisito;
	private Integer intItemRequisitoDetalle;
	private String strDescripcionRequisito;
	private Integer intParaTipoOperacion;
	private Integer intParaSubTipoOperacion;
	
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
	public Integer getIntParaTipoOperacion() {
		return intParaTipoOperacion;
	}
	public void setIntParaTipoOperacion(Integer intParaTipoOperacion) {
		this.intParaTipoOperacion = intParaTipoOperacion;
	}
	public Integer getIntParaSubTipoOperacion() {
		return intParaSubTipoOperacion;
	}
	public void setIntParaSubTipoOperacion(Integer intParaSubTipoOperacion) {
		this.intParaSubTipoOperacion = intParaSubTipoOperacion;
	}
}
