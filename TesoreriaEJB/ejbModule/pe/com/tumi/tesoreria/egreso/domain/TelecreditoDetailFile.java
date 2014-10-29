/**
* Resumen.
* Objeto: TelecreditoDetailFile
* Descripción: Detalle del archivo de telecrédito
* Fecha de Creación: 28/10/2014.
* Requerimiento de Creación: REQ14-006
* Autor: Bizarq
*/
package pe.com.tumi.tesoreria.egreso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class TelecreditoDetailFile extends TumiDomain {
	private String strFecRegistro;
	private String strFecValuta;
	private String strDescOperacion;
	private String strMonto;
	private String strSaldo;
	private String strSucursal;
	private String strNroOperacion;
	private String strHoraOperacion;
	private String strUsuario;
	private String strUTC;
	private String strReferencia;
	
	public String getStrFecRegistro() {
		return strFecRegistro;
	}
	public void setStrFecRegistro(String strFecRegistro) {
		this.strFecRegistro = strFecRegistro;
	}
	public String getStrFecValuta() {
		return strFecValuta;
	}
	public void setStrFecValuta(String strFecValuta) {
		this.strFecValuta = strFecValuta;
	}
	public String getStrDescOperacion() {
		return strDescOperacion;
	}
	public void setStrDescOperacion(String strDescOperacion) {
		this.strDescOperacion = strDescOperacion;
	}
	public String getStrMonto() {
		return strMonto;
	}
	public void setStrMonto(String strMonto) {
		this.strMonto = strMonto;
	}
	public String getStrSaldo() {
		return strSaldo;
	}
	public void setStrSaldo(String strSaldo) {
		this.strSaldo = strSaldo;
	}
	public String getStrSucursal() {
		return strSucursal;
	}
	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}
	public String getStrNroOperacion() {
		return strNroOperacion;
	}
	public void setStrNroOperacion(String strNroOperacion) {
		this.strNroOperacion = strNroOperacion;
	}
	public String getStrHoraOperacion() {
		return strHoraOperacion;
	}
	public void setStrHoraOperacion(String strHoraOperacion) {
		this.strHoraOperacion = strHoraOperacion;
	}
	public String getStrUsuario() {
		return strUsuario;
	}
	public void setStrUsuario(String strUsuario) {
		this.strUsuario = strUsuario;
	}
	public String getStrUTC() {
		return strUTC;
	}
	public void setStrUTC(String strUTC) {
		this.strUTC = strUTC;
	}
	public String getStrReferencia() {
		return strReferencia;
	}
	public void setStrReferencia(String strReferencia) {
		this.strReferencia = strReferencia;
	}
}