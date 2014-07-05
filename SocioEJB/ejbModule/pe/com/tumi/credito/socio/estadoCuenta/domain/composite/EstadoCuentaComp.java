package pe.com.tumi.credito.socio.estadoCuenta.domain.composite;

import java.math.BigDecimal;

import pe.com.tumi.movimiento.concepto.domain.Movimiento;

public class EstadoCuentaComp {
	private Movimiento movimiento;
	private String strFecha;
	private String strDescripcion;
	private String strBdTasaInteres;
	
	
	private String strBdMontoTotal;
	private String strCuotas;
	private String strBdSaldoCredito;
	private String strBdDiferencia;
	private String strBdUltimoEnvio;
	private String strSolicitud;
	private Integer intParaTipoMovimiento;
	
	private String strTipoAddSubtract;
	private String strMontoMovPrestamos;
	private String strMontoMovCreditos;
	private String strMontoMovInteres;
	private String strMontoMovActividad;
	private String strMontoMovMulta;
	private String strMontoMovAportes;
	private String strMontoMovFdoSepelio;
	private String strMontoMovFdoRetiro;
	private String strMontoMovMntCuenta;
	private String strMontoMovCtaXPagar;
	private String strMontoMovCtaXCobrar;
	private String strSumatoriaFilas;
	private String strEstadoPrestamo;
	private String strCuotaMensual;
	
	private BigDecimal bdMontoTotal;
	private BigDecimal bdTasaInteres;
	private BigDecimal bdSaldoCredito;
	private BigDecimal bdDiferencia;
	private BigDecimal bdUltimoEnvio;
	
	
	private BigDecimal bdMontoMovAportes;	
	private BigDecimal bdMontoMovPrestamos;
	private BigDecimal bdMontoMovCreditos;
	private BigDecimal bdMontoMovInteres;
	private BigDecimal bdMontoMovMntCuenta;
	private BigDecimal bdMontoMovActividad;
	private BigDecimal bdMontoMovMulta;	
	private BigDecimal bdMontoMovFdoSepelio;
	private BigDecimal bdMontoMovFdoRetiro;	
	private BigDecimal bdMontoMovCtaXPagar;
	private BigDecimal bdMontoMovCtaXCobrar;
	
	private BigDecimal bdSumatoriaFilas;
	
	public Integer getIntParaTipoMovimiento() {
		return intParaTipoMovimiento;
	}
	public void setIntParaTipoMovimiento(Integer intParaTipoMovimiento) {
		this.intParaTipoMovimiento = intParaTipoMovimiento;
	}
	public Movimiento getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}
	public String getStrFecha() {
		return strFecha;
	}
	public void setStrFecha(String strFecha) {
		this.strFecha = strFecha;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public String getStrBdTasaInteres() {
		return strBdTasaInteres;
	}
	public void setStrBdTasaInteres(String strBdTasaInteres) {
		this.strBdTasaInteres = strBdTasaInteres;
	}
	public String getStrBdMontoTotal() {
		return strBdMontoTotal;
	}
	public void setStrBdMontoTotal(String strBdMontoTotal) {
		this.strBdMontoTotal = strBdMontoTotal;
	}
	public String getStrCuotas() {
		return strCuotas;
	}
	public void setStrCuotas(String strCuotas) {
		this.strCuotas = strCuotas;
	}
	public String getStrBdSaldoCredito() {
		return strBdSaldoCredito;
	}
	public void setStrBdSaldoCredito(String strBdSaldoCredito) {
		this.strBdSaldoCredito = strBdSaldoCredito;
	}
	public String getStrBdDiferencia() {
		return strBdDiferencia;
	}
	public void setStrBdDiferencia(String strBdDiferencia) {
		this.strBdDiferencia = strBdDiferencia;
	}
	public String getStrBdUltimoEnvio() {
		return strBdUltimoEnvio;
	}
	public void setStrBdUltimoEnvio(String strBdUltimoEnvio) {
		this.strBdUltimoEnvio = strBdUltimoEnvio;
	}
	public String getStrMontoMovPrestamos() {
		return strMontoMovPrestamos;
	}
	public void setStrMontoMovPrestamos(String strMontoMovPrestamos) {
		this.strMontoMovPrestamos = strMontoMovPrestamos;
	}
	public String getStrMontoMovCreditos() {
		return strMontoMovCreditos;
	}
	public void setStrMontoMovCreditos(String strMontoMovCreditos) {
		this.strMontoMovCreditos = strMontoMovCreditos;
	}
	public String getStrMontoMovInteres() {
		return strMontoMovInteres;
	}
	public void setStrMontoMovInteres(String strMontoMovInteres) {
		this.strMontoMovInteres = strMontoMovInteres;
	}
	public String getStrMontoMovActividad() {
		return strMontoMovActividad;
	}
	public void setStrMontoMovActividad(String strMontoMovActividad) {
		this.strMontoMovActividad = strMontoMovActividad;
	}
	public String getStrMontoMovMulta() {
		return strMontoMovMulta;
	}
	public void setStrMontoMovMulta(String strMontoMovMulta) {
		this.strMontoMovMulta = strMontoMovMulta;
	}
	public String getStrMontoMovAportes() {
		return strMontoMovAportes;
	}
	public void setStrMontoMovAportes(String strMontoMovAportes) {
		this.strMontoMovAportes = strMontoMovAportes;
	}
	public String getStrMontoMovFdoSepelio() {
		return strMontoMovFdoSepelio;
	}
	public void setStrMontoMovFdoSepelio(String strMontoMovFdoSepelio) {
		this.strMontoMovFdoSepelio = strMontoMovFdoSepelio;
	}
	public String getStrMontoMovFdoRetiro() {
		return strMontoMovFdoRetiro;
	}
	public void setStrMontoMovFdoRetiro(String strMontoMovFdoRetiro) {
		this.strMontoMovFdoRetiro = strMontoMovFdoRetiro;
	}
	public String getStrMontoMovMntCuenta() {
		return strMontoMovMntCuenta;
	}
	public void setStrMontoMovMntCuenta(String strMontoMovMntCuenta) {
		this.strMontoMovMntCuenta = strMontoMovMntCuenta;
	}
	public String getStrMontoMovCtaXPagar() {
		return strMontoMovCtaXPagar;
	}
	public void setStrMontoMovCtaXPagar(String strMontoMovCtaXPagar) {
		this.strMontoMovCtaXPagar = strMontoMovCtaXPagar;
	}
	public String getStrMontoMovCtaXCobrar() {
		return strMontoMovCtaXCobrar;
	}
	public void setStrMontoMovCtaXCobrar(String strMontoMovCtaXCobrar) {
		this.strMontoMovCtaXCobrar = strMontoMovCtaXCobrar;
	}
	public String getStrTipoAddSubtract() {
		return strTipoAddSubtract;
	}
	public void setStrTipoAddSubtract(String strTipoAddSubtract) {
		this.strTipoAddSubtract = strTipoAddSubtract;
	}
	public String getStrSumatoriaFilas() {
		return strSumatoriaFilas;
	}
	public void setStrSumatoriaFilas(String strSumatoriaFilas) {
		this.strSumatoriaFilas = strSumatoriaFilas;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public String getStrSolicitud() {
		return strSolicitud;
	}
	public void setStrSolicitud(String strSolicitud) {
		this.strSolicitud = strSolicitud;
	}
	public String getStrEstadoPrestamo() {
		return strEstadoPrestamo;
	}
	public void setStrEstadoPrestamo(String strEstadoPrestamo) {
		this.strEstadoPrestamo = strEstadoPrestamo;
	}
	public String getStrCuotaMensual() {
		return strCuotaMensual;
	}
	public void setStrCuotaMensual(String strCuotaMensual) {
		this.strCuotaMensual = strCuotaMensual;
	}
	public BigDecimal getBdTasaInteres() {
		return bdTasaInteres;
	}
	public void setBdTasaInteres(BigDecimal bdTasaInteres) {
		this.bdTasaInteres = bdTasaInteres;
	}
	public BigDecimal getBdSaldoCredito() {
		return bdSaldoCredito;
	}
	public void setBdSaldoCredito(BigDecimal bdSaldoCredito) {
		this.bdSaldoCredito = bdSaldoCredito;
	}
	public BigDecimal getBdDiferencia() {
		return bdDiferencia;
	}
	public void setBdDiferencia(BigDecimal bdDiferencia) {
		this.bdDiferencia = bdDiferencia;
	}
	public BigDecimal getBdUltimoEnvio() {
		return bdUltimoEnvio;
	}
	public void setBdUltimoEnvio(BigDecimal bdUltimoEnvio) {
		this.bdUltimoEnvio = bdUltimoEnvio;
	}
	public BigDecimal getBdMontoMovAportes() {
		return bdMontoMovAportes;
	}
	public void setBdMontoMovAportes(BigDecimal bdMontoMovAportes) {
		this.bdMontoMovAportes = bdMontoMovAportes;
	}
	public BigDecimal getBdMontoMovPrestamos() {
		return bdMontoMovPrestamos;
	}
	public void setBdMontoMovPrestamos(BigDecimal bdMontoMovPrestamos) {
		this.bdMontoMovPrestamos = bdMontoMovPrestamos;
	}
	public BigDecimal getBdMontoMovCreditos() {
		return bdMontoMovCreditos;
	}
	public void setBdMontoMovCreditos(BigDecimal bdMontoMovCreditos) {
		this.bdMontoMovCreditos = bdMontoMovCreditos;
	}
	public BigDecimal getBdMontoMovInteres() {
		return bdMontoMovInteres;
	}
	public void setBdMontoMovInteres(BigDecimal bdMontoMovInteres) {
		this.bdMontoMovInteres = bdMontoMovInteres;
	}
	public BigDecimal getBdMontoMovMntCuenta() {
		return bdMontoMovMntCuenta;
	}
	public void setBdMontoMovMntCuenta(BigDecimal bdMontoMovMntCuenta) {
		this.bdMontoMovMntCuenta = bdMontoMovMntCuenta;
	}
	public BigDecimal getBdMontoMovActividad() {
		return bdMontoMovActividad;
	}
	public void setBdMontoMovActividad(BigDecimal bdMontoMovActividad) {
		this.bdMontoMovActividad = bdMontoMovActividad;
	}
	public BigDecimal getBdMontoMovMulta() {
		return bdMontoMovMulta;
	}
	public void setBdMontoMovMulta(BigDecimal bdMontoMovMulta) {
		this.bdMontoMovMulta = bdMontoMovMulta;
	}
	public BigDecimal getBdMontoMovFdoSepelio() {
		return bdMontoMovFdoSepelio;
	}
	public void setBdMontoMovFdoSepelio(BigDecimal bdMontoMovFdoSepelio) {
		this.bdMontoMovFdoSepelio = bdMontoMovFdoSepelio;
	}
	public BigDecimal getBdMontoMovFdoRetiro() {
		return bdMontoMovFdoRetiro;
	}
	public void setBdMontoMovFdoRetiro(BigDecimal bdMontoMovFdoRetiro) {
		this.bdMontoMovFdoRetiro = bdMontoMovFdoRetiro;
	}
	public BigDecimal getBdMontoMovCtaXPagar() {
		return bdMontoMovCtaXPagar;
	}
	public void setBdMontoMovCtaXPagar(BigDecimal bdMontoMovCtaXPagar) {
		this.bdMontoMovCtaXPagar = bdMontoMovCtaXPagar;
	}
	public BigDecimal getBdMontoMovCtaXCobrar() {
		return bdMontoMovCtaXCobrar;
	}
	public void setBdMontoMovCtaXCobrar(BigDecimal bdMontoMovCtaXCobrar) {
		this.bdMontoMovCtaXCobrar = bdMontoMovCtaXCobrar;
	}
	public BigDecimal getBdSumatoriaFilas() {
		return bdSumatoriaFilas;
	}
	public void setBdSumatoriaFilas(BigDecimal bdSumatoriaFilas) {
		this.bdSumatoriaFilas = bdSumatoriaFilas;
	}
}
