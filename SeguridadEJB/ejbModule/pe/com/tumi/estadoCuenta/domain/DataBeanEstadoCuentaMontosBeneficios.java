package pe.com.tumi.estadoCuenta.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DataBeanEstadoCuentaMontosBeneficios extends TumiDomain {
	private Integer intCuenta;
	private BigDecimal bdCtaAporte;
	private BigDecimal bdSaldoAporte;
	private BigDecimal bdMontoFdoRetiro;
	private BigDecimal bdMontoFdoSepelio;
	private BigDecimal bdMontoMantenimiento;
	private BigDecimal bdMontoUltimoEnvioAportes;
	private BigDecimal bdMontoUltimoEnvioFdoSepelio;
	private BigDecimal bdMontoUltimoEnvioFdoRetiro;
	private BigDecimal bdMontoUltimoEnvioMant;
	
	public Integer getIntCuenta() {
		return intCuenta;
	}
	public void setIntCuenta(Integer intCuenta) {
		this.intCuenta = intCuenta;
	}
	public BigDecimal getBdCtaAporte() {
		return bdCtaAporte;
	}
	public void setBdCtaAporte(BigDecimal bdCtaAporte) {
		this.bdCtaAporte = bdCtaAporte;
	}
	public BigDecimal getBdSaldoAporte() {
		return bdSaldoAporte;
	}
	public void setBdSaldoAporte(BigDecimal bdSaldoAporte) {
		this.bdSaldoAporte = bdSaldoAporte;
	}
	public BigDecimal getBdMontoFdoRetiro() {
		return bdMontoFdoRetiro;
	}
	public void setBdMontoFdoRetiro(BigDecimal bdMontoFdoRetiro) {
		this.bdMontoFdoRetiro = bdMontoFdoRetiro;
	}
	public BigDecimal getBdMontoFdoSepelio() {
		return bdMontoFdoSepelio;
	}
	public void setBdMontoFdoSepelio(BigDecimal bdMontoFdoSepelio) {
		this.bdMontoFdoSepelio = bdMontoFdoSepelio;
	}
	public BigDecimal getBdMontoMantenimiento() {
		return bdMontoMantenimiento;
	}
	public void setBdMontoMantenimiento(BigDecimal bdMontoMantenimiento) {
		this.bdMontoMantenimiento = bdMontoMantenimiento;
	}
	public BigDecimal getBdMontoUltimoEnvioAportes() {
		return bdMontoUltimoEnvioAportes;
	}
	public void setBdMontoUltimoEnvioAportes(BigDecimal bdMontoUltimoEnvioAportes) {
		this.bdMontoUltimoEnvioAportes = bdMontoUltimoEnvioAportes;
	}
	public BigDecimal getBdMontoUltimoEnvioFdoSepelio() {
		return bdMontoUltimoEnvioFdoSepelio;
	}
	public void setBdMontoUltimoEnvioFdoSepelio(
			BigDecimal bdMontoUltimoEnvioFdoSepelio) {
		this.bdMontoUltimoEnvioFdoSepelio = bdMontoUltimoEnvioFdoSepelio;
	}
	public BigDecimal getBdMontoUltimoEnvioFdoRetiro() {
		return bdMontoUltimoEnvioFdoRetiro;
	}
	public void setBdMontoUltimoEnvioFdoRetiro(
			BigDecimal bdMontoUltimoEnvioFdoRetiro) {
		this.bdMontoUltimoEnvioFdoRetiro = bdMontoUltimoEnvioFdoRetiro;
	}
	public BigDecimal getBdMontoUltimoEnvioMant() {
		return bdMontoUltimoEnvioMant;
	}
	public void setBdMontoUltimoEnvioMant(BigDecimal bdMontoUltimoEnvioMant) {
		this.bdMontoUltimoEnvioMant = bdMontoUltimoEnvioMant;
	}
}
