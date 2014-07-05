package pe.com.tumi.cobranza.cierremensual.domain.composite;

import java.util.List;

import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacion;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class CierreCobranzaOperacionComp extends TumiDomain{
	private CierreCobranzaOperacion cierreCobranzaOperacion;
	private Tabla paramCobranzaOperacion;
	
	public CierreCobranzaOperacion getCierreCobranzaOperacion() {
		return cierreCobranzaOperacion;
	}
	public void setCierreCobranzaOperacion(
			CierreCobranzaOperacion cierreCobranzaOperacion) {
		this.cierreCobranzaOperacion = cierreCobranzaOperacion;
	}
	public Tabla getParamCobranzaOperacion() {
		return paramCobranzaOperacion;
	}
	public void setParamCobranzaOperacion(Tabla paramCobranzaOperacion) {
		this.paramCobranzaOperacion = paramCobranzaOperacion;
	}
}
