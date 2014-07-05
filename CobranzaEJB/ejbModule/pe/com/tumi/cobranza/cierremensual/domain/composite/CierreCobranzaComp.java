package pe.com.tumi.cobranza.cierremensual.domain.composite;

import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacion;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanilla;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class CierreCobranzaComp extends TumiDomain {
	private CierreCobranza cierreCobranza;
	private CierreCobranzaOperacion cierreCobranzaOperacion;
	private CierreCobranzaPlanilla cierreCobranzaPlanilla;
	private Integer intParaEstado;
	private Integer intMes;
	private Integer intAnio;
	private Persona usuario;
	private Integer intEstado;
	private String strFechaRegistro;
	
	public CierreCobranza getCierreCobranza() {
		return cierreCobranza;
	}
	public void setCierreCobranza(CierreCobranza cierreCobranza) {
		this.cierreCobranza = cierreCobranza;
	}
	public CierreCobranzaOperacion getCierreCobranzaOperacion() {
		return cierreCobranzaOperacion;
	}
	public void setCierreCobranzaOperacion(
			CierreCobranzaOperacion cierreCobranzaOperacion) {
		this.cierreCobranzaOperacion = cierreCobranzaOperacion;
	}
	public CierreCobranzaPlanilla getCierreCobranzaPlanilla() {
		return cierreCobranzaPlanilla;
	}
	public void setCierreCobranzaPlanilla(
			CierreCobranzaPlanilla cierreCobranzaPlanilla) {
		this.cierreCobranzaPlanilla = cierreCobranzaPlanilla;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntMes() {
		return intMes;
	}
	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}
	public Integer getIntAnio() {
		return intAnio;
	}
	public void setIntAnio(Integer intAnio) {
		this.intAnio = intAnio;
	}
	public Persona getUsuario() {
		return usuario;
	}
	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}
	public Integer getIntEstado() {
		return intEstado;
	}
	public void setIntEstado(Integer intEstado) {
		this.intEstado = intEstado;
	}
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}
	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}
}