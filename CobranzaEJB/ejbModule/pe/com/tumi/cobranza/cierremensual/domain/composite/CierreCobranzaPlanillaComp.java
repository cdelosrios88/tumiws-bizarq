package pe.com.tumi.cobranza.cierremensual.domain.composite;

import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanilla;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class CierreCobranzaPlanillaComp extends TumiDomain{
	private CierreCobranzaPlanilla cierreCobranzaPlanilla;
	private Tabla paramModalidad;
	private Tabla paramTipoSocio;
	
	public CierreCobranzaPlanilla getCierreCobranzaPlanilla() {
		return cierreCobranzaPlanilla;
	}
	public void setCierreCobranzaPlanilla(
			CierreCobranzaPlanilla cierreCobranzaPlanilla) {
		this.cierreCobranzaPlanilla = cierreCobranzaPlanilla;
	}
	public Tabla getParamModalidad() {
		return paramModalidad;
	}
	public void setParamModalidad(Tabla paramModalidad) {
		this.paramModalidad = paramModalidad;
	}
	public Tabla getParamTipoSocio() {
		return paramTipoSocio;
	}
	public void setParamTipoSocio(Tabla paramTipoSocio) {
		this.paramTipoSocio = paramTipoSocio;
	}	
}