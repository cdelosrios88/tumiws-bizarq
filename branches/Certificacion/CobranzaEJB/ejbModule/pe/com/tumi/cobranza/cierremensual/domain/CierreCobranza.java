package pe.com.tumi.cobranza.cierremensual.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CierreCobranza extends TumiDomain {
	private Integer	intEmpresaCierreCobranza;
	private Integer	intParaTipoRegistro;
	private Integer	intParaPeriodoCierre;
	private List<CierreCobranzaOperacion> listCierreOperacion;
	private List<CierreCobranzaPlanilla> listCierrePlanilla;
	
	public Integer getIntEmpresaCierreCobranza() {
		return intEmpresaCierreCobranza;
	}
	public void setIntEmpresaCierreCobranza(Integer intEmpresaCierreCobranza) {
		this.intEmpresaCierreCobranza = intEmpresaCierreCobranza;
	}
	public Integer getIntParaTipoRegistro() {
		return intParaTipoRegistro;
	}
	public void setIntParaTipoRegistro(Integer intParaTipoRegistro) {
		this.intParaTipoRegistro = intParaTipoRegistro;
	}
	public Integer getIntParaPeriodoCierre() {
		return intParaPeriodoCierre;
	}
	public void setIntParaPeriodoCierre(Integer intParaPeriodoCierre) {
		this.intParaPeriodoCierre = intParaPeriodoCierre;
	}
	public List<CierreCobranzaOperacion> getListCierreOperacion() {
		return listCierreOperacion;
	}
	public void setListCierreOperacion(
			List<CierreCobranzaOperacion> listCierreOperacion) {
		this.listCierreOperacion = listCierreOperacion;
	}
	public List<CierreCobranzaPlanilla> getListCierrePlanilla() {
		return listCierrePlanilla;
	}
	public void setListCierrePlanilla(
			List<CierreCobranzaPlanilla> listCierrePlanilla) {
		this.listCierrePlanilla = listCierrePlanilla;
	}
}