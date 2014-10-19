package pe.com.tumi.tesoreria.cierreLogisticaOper.domain.composite;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.CierreLogisticaOper;

public class CierreLogistica extends TumiDomain{

	private Integer intPersEmpresaCietes;
	private Integer intPeriodoCierre;
	private Integer intParaTipoRegistroLogis;
	private List<CierreLogisticaOper> listaCierreLogistica;
	
	public Integer getIntPersEmpresaCietes() {
		return intPersEmpresaCietes;
	}
	public void setIntPersEmpresaCietes(Integer intPersEmpresaCietes) {
		this.intPersEmpresaCietes = intPersEmpresaCietes;
	}
	public Integer getIntPeriodoCierre() {
		return intPeriodoCierre;
	}
	public void setIntPeriodoCierre(Integer intPeriodoCierre) {
		this.intPeriodoCierre = intPeriodoCierre;
	}
	public Integer getIntParaTipoRegistroLogis() {
		return intParaTipoRegistroLogis;
	}
	public void setIntParaTipoRegistroLogis(Integer intParaTipoRegistroLogis) {
		this.intParaTipoRegistroLogis = intParaTipoRegistroLogis;
	}
	public List<CierreLogisticaOper> getListaCierreLogistica() {
		return listaCierreLogistica;
	}
	public void setListaCierreLogistica(
			List<CierreLogisticaOper> listaCierreLogistica) {
		this.listaCierreLogistica = listaCierreLogistica;
	}
	
}
