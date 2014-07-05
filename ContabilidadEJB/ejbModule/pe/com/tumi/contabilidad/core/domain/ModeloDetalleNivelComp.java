package pe.com.tumi.contabilidad.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ModeloDetalleNivelComp extends TumiDomain{
 	private Integer intEmpresa;
 	private Integer intCodigoModelo;
 	private Integer intEmpresaCuenta;
 	private Integer intPeriodoCuenta;
 	private String strNumeroCuenta;
 	private String strDescCuenta;
 	private Integer intItem;
	private String strDescripcion;
	private Integer intDatoTablas;
	private Integer intDatoArgumento;
	private Integer intValor;
	private String strCampoConsumir;
	private Integer intParamDebeHaber;
	private String strDebe;
	private String strHaber;
	
	public Integer getIntEmpresa() {
		return intEmpresa;
	}
	public void setIntEmpresa(Integer intEmpresa) {
		this.intEmpresa = intEmpresa;
	}
	public Integer getIntCodigoModelo() {
		return intCodigoModelo;
	}
	public void setIntCodigoModelo(Integer intCodigoModelo) {
		this.intCodigoModelo = intCodigoModelo;
	}
	public Integer getIntEmpresaCuenta() {
		return intEmpresaCuenta;
	}
	public void setIntEmpresaCuenta(Integer intEmpresaCuenta) {
		this.intEmpresaCuenta = intEmpresaCuenta;
	}
	public Integer getIntPeriodoCuenta() {
		return intPeriodoCuenta;
	}
	public void setIntPeriodoCuenta(Integer intPeriodoCuenta) {
		this.intPeriodoCuenta = intPeriodoCuenta;
	}
	public String getStrNumeroCuenta() {
		return strNumeroCuenta;
	}
	public void setStrNumeroCuenta(String strNumeroCuenta) {
		this.strNumeroCuenta = strNumeroCuenta;
	}
	public Integer getIntItem() {
		return intItem;
	}
	public void setIntItem(Integer intItem) {
		this.intItem = intItem;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Integer getIntDatoTablas() {
		return intDatoTablas;
	}
	public void setIntDatoTablas(Integer intDatoTablas) {
		this.intDatoTablas = intDatoTablas;
	}
	public Integer getIntDatoArgumento() {
		return intDatoArgumento;
	}
	public void setIntDatoArgumento(Integer intDatoArgumento) {
		this.intDatoArgumento = intDatoArgumento;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
	public String getStrCampoConsumir() {
		return strCampoConsumir;
	}
	public void setStrCampoConsumir(String strCampoConsumir) {
		this.strCampoConsumir = strCampoConsumir;
	}
	public Integer getIntParamDebeHaber() {
		return intParamDebeHaber;
	}
	public void setIntParamDebeHaber(Integer intParamDebeHaber) {
		this.intParamDebeHaber = intParamDebeHaber;
	}
	public String getStrDebe() {
		return strDebe;
	}
	public void setStrDebe(String strDebe) {
		this.strDebe = strDebe;
	}
	public String getStrHaber() {
		return strHaber;
	}
	public void setStrHaber(String strHaber) {
		this.strHaber = strHaber;
	}
	public String getStrDescCuenta() {
		return strDescCuenta;
	}
	public void setStrDescCuenta(String strDescCuenta) {
		this.strDescCuenta = strDescCuenta;
	}
}
