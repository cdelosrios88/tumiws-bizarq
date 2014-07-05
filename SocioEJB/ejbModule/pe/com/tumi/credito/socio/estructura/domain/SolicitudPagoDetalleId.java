package pe.com.tumi.credito.socio.estructura.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SolicitudPagoDetalleId extends TumiDomain{

	private Integer intNumero;
	private Integer intItemPago;
	
	public Integer getIntNumero() {
		return intNumero;
	}
	public void setIntNumero(Integer intNumero) {
		this.intNumero = intNumero;
	}
	public Integer getIntItemPago() {
		return intItemPago;
	}
	public void setIntItemPago(Integer intItemPago) {
		this.intItemPago = intItemPago;
	}
	@Override
	public String toString() {
		return "SolicitudPagoDetalleId [intNumero=" + intNumero
				+ ", intItemPago=" + intItemPago + "]";
	}
	
	
}
