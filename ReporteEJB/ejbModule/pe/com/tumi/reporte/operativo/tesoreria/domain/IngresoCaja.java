package pe.com.tumi.reporte.operativo.tesoreria.domain;

import java.math.BigDecimal;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class IngresoCaja extends TumiDomain{
	private Integer intParaTipoDocGeneral;
	private Integer intIdSucursal;
	private Date dtFecIni;
	private Date dtFecFin;
	private String strNroIngreso;
	private String strNroAsiento;
	private String strRecibi;
	private String strFechaIngreso;
	private Integer intMesIngreso;
	private Integer intAnioIngreso;
	private BigDecimal bdMontoTotal;
	private String strFechaAnulado;
	private String strDetalle;
	private String strNombreCuenta;
	private String strNumeroCuenta;
	private String strNombreBanco;
	private String strDeposito;
	
	public Integer getIntParaTipoDocGeneral() {
		return intParaTipoDocGeneral;
	}
	public void setIntParaTipoDocGeneral(Integer intParaTipoDocGeneral) {
		this.intParaTipoDocGeneral = intParaTipoDocGeneral;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public Date getDtFecIni() {
		return dtFecIni;
	}
	public void setDtFecIni(Date dtFecIni) {
		this.dtFecIni = dtFecIni;
	}
	public Date getDtFecFin() {
		return dtFecFin;
	}
	public void setDtFecFin(Date dtFecFin) {
		this.dtFecFin = dtFecFin;
	}
	public String getStrNroIngreso() {
		return strNroIngreso;
	}
	public void setStrNroIngreso(String strNroIngreso) {
		this.strNroIngreso = strNroIngreso;
	}
	public String getStrRecibi() {
		return strRecibi;
	}
	public void setStrRecibi(String strRecibi) {
		this.strRecibi = strRecibi;
	}
	public String getStrFechaIngreso() {
		return strFechaIngreso;
	}
	public void setStrFechaIngreso(String strFechaIngreso) {
		this.strFechaIngreso = strFechaIngreso;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public String getStrFechaAnulado() {
		return strFechaAnulado;
	}
	public void setStrFechaAnulado(String strFechaAnulado) {
		this.strFechaAnulado = strFechaAnulado;
	}
	public String getStrDetalle() {
		return strDetalle;
	}
	public void setStrDetalle(String strDetalle) {
		this.strDetalle = strDetalle;
	}
	public String getStrDeposito() {
		return strDeposito;
	}
	public void setStrDeposito(String strDeposito) {
		this.strDeposito = strDeposito;
	}
	public String getStrNroAsiento() {
		return strNroAsiento;
	}
	public void setStrNroAsiento(String strNroAsiento) {
		this.strNroAsiento = strNroAsiento;
	}
	public Integer getIntMesIngreso() {
		return intMesIngreso;
	}
	public void setIntMesIngreso(Integer intMesIngreso) {
		this.intMesIngreso = intMesIngreso;
	}
	public Integer getIntAnioIngreso() {
		return intAnioIngreso;
	}
	public void setIntAnioIngreso(Integer intAnioIngreso) {
		this.intAnioIngreso = intAnioIngreso;
	}
	public String getStrNombreCuenta() {
		return strNombreCuenta;
	}
	public void setStrNombreCuenta(String strNombreCuenta) {
		this.strNombreCuenta = strNombreCuenta;
	}
	public String getStrNumeroCuenta() {
		return strNumeroCuenta;
	}
	public void setStrNumeroCuenta(String strNumeroCuenta) {
		this.strNumeroCuenta = strNumeroCuenta;
	}
	public String getStrNombreBanco() {
		return strNombreBanco;
	}
	public void setStrNombreBanco(String strNombreBanco) {
		this.strNombreBanco = strNombreBanco;
	}
}
