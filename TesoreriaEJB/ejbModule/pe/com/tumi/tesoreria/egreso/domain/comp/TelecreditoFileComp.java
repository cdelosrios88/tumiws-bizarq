/**
* Resumen.
* Objeto: TelecreditoFileComp
* Descripci�n: Objeto principal que contiene los atributos del archivo de Telecr�dito
* Fecha de Creaci�n: 28/10/2014.
* Requerimiento de Creaci�n: REQ14-006
* Autor: Bizarq
*/
package pe.com.tumi.tesoreria.egreso.domain.comp;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.tesoreria.egreso.domain.TelecreditoDetailFile;

public class TelecreditoFileComp extends TumiDomain{
	private String strNroCuenta;
	private String strMoneda;
	private String strTipoCuenta;
	private List<TelecreditoDetailFile> lstTelecreditoFileDetail;
	
	public String getStrNroCuenta() {
		return strNroCuenta;
	}
	public void setStrNroCuenta(String strNroCuenta) {
		this.strNroCuenta = strNroCuenta;
	}
	public String getStrMoneda() {
		return strMoneda;
	}
	public void setStrMoneda(String strMoneda) {
		this.strMoneda = strMoneda;
	}
	public String getStrTipoCuenta() {
		return strTipoCuenta;
	}
	public void setStrTipoCuenta(String strTipoCuenta) {
		this.strTipoCuenta = strTipoCuenta;
	}
	public List<TelecreditoDetailFile> getLstTelecreditoFileDetail() {
		return lstTelecreditoFileDetail;
	}
	public void setLstTelecreditoFileDetail(
			List<TelecreditoDetailFile> lstTelecreditoFileDetail) {
		this.lstTelecreditoFileDetail = lstTelecreditoFileDetail;
	}
}