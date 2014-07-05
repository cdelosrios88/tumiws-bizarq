package pe.com.tumi.estadoCuenta.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DataBeanEstadoCuentaDetalleMovimiento extends TumiDomain {
	private String strFechaMovimiento;
	private String strTipoNumero;
	private BigDecimal bdMontoAporte;
	private BigDecimal bdMontoMantenimiento;
	private BigDecimal bdMontoFondoSepelio;
	private BigDecimal bdMontoFondoRetiro;
	private BigDecimal bdMontoPrestamo;
	private BigDecimal bdMontoCredito;
	private BigDecimal bdMontoActividad;
	private BigDecimal bdMontoMulta;
	private BigDecimal bdMontoInteres;
	private BigDecimal bdMontoCtaPorPagar;
	private BigDecimal bdMontoCtaPorCobrar;
	//Sumatoria de las columnas
	private BigDecimal bdSumaMontoAporte;
	private BigDecimal bdSumaMontoMantenimiento;
	private BigDecimal bdSumaMontoFondoSepelio;
	private BigDecimal bdSumaMontoFondoRetiro;
	private BigDecimal bdSumaMontoPrestamo;
	private BigDecimal bdSumaMontoCredito;
	private BigDecimal bdSumaMontoActividad;
	private BigDecimal bdSumaMontoMulta;
	private BigDecimal bdSumaMontoInteres;
	private BigDecimal bdSumaMontoCtaPorPagar;
	private BigDecimal bdSumaMontoCtaPorCobrar;
	//Sumatoria de los montos por fila
	private BigDecimal bdSumaMontoFila;
	//Atributos para la organización de la data
	private Integer intEmpresa;
	private Integer intCuenta;
	private Integer intPersona;
	private Integer intItemCuentaConcepto;
	private Integer intItemExpediente;
	private Integer intItemExpedienteDetalle;
	private Integer intParaTipoMovimiento;
	private Integer intParaTipoConceptoGeneral;
	private String strNumeroDocumento;
	private Integer intParaTipoCargoAbono;
	//Signo de los montos
	private Integer intSignoMontoAporte;
	private Integer intSignoMontoMantenimiento;
	private Integer intSignoMontoFondoSepelio;
	private Integer intSignoMontoFondoRetiro;
	private Integer intSignoMontoPrestamo;
	private Integer intSignoMontoCredito;
	private Integer intSignoMontoActividad;
	private Integer intSignoMontoMulta;
	private Integer intSignoMontoInteres;
	private Integer intSignoMontoCtaPorPagar;
	private Integer intSignoMontoCtaPorCobrar;
	
	//Atributos para el reporte
	private String strBdMontoAporte;
	private String strBdMontoMantenimiento;
	private String strBdMontoFondoSepelio;
	private String strBdMontoFondoRetiro;
	private String strBdMontoPrestamo;
	private String strBdMontoCredito;
	private String strBdMontoActividad;
	private String strBdMontoMulta;
	private String strBdMontoInteres;
	private String strBdMontoCtaPorPagar;
	private String strBdMontoCtaPorCobrar;
	private String strBdSumaMontoFila;
	
	public String getStrFechaMovimiento() {
		return strFechaMovimiento;
	}
	public void setStrFechaMovimiento(String strFechaMovimiento) {
		this.strFechaMovimiento = strFechaMovimiento;
	}
	public String getStrTipoNumero() {
		return strTipoNumero;
	}
	public void setStrTipoNumero(String strTipoNumero) {
		this.strTipoNumero = strTipoNumero;
	}
	public BigDecimal getBdMontoAporte() {
		return bdMontoAporte;
	}
	public void setBdMontoAporte(BigDecimal bdMontoAporte) {
		this.bdMontoAporte = bdMontoAporte;
	}
	public BigDecimal getBdMontoMantenimiento() {
		return bdMontoMantenimiento;
	}
	public void setBdMontoMantenimiento(BigDecimal bdMontoMantenimiento) {
		this.bdMontoMantenimiento = bdMontoMantenimiento;
	}
	public BigDecimal getBdMontoFondoSepelio() {
		return bdMontoFondoSepelio;
	}
	public void setBdMontoFondoSepelio(BigDecimal bdMontoFondoSepelio) {
		this.bdMontoFondoSepelio = bdMontoFondoSepelio;
	}
	public BigDecimal getBdMontoFondoRetiro() {
		return bdMontoFondoRetiro;
	}
	public void setBdMontoFondoRetiro(BigDecimal bdMontoFondoRetiro) {
		this.bdMontoFondoRetiro = bdMontoFondoRetiro;
	}
	public BigDecimal getBdMontoPrestamo() {
		return bdMontoPrestamo;
	}
	public void setBdMontoPrestamo(BigDecimal bdMontoPrestamo) {
		this.bdMontoPrestamo = bdMontoPrestamo;
	}
	public BigDecimal getBdMontoCredito() {
		return bdMontoCredito;
	}
	public void setBdMontoCredito(BigDecimal bdMontoCredito) {
		this.bdMontoCredito = bdMontoCredito;
	}
	public BigDecimal getBdMontoActividad() {
		return bdMontoActividad;
	}
	public void setBdMontoActividad(BigDecimal bdMontoActividad) {
		this.bdMontoActividad = bdMontoActividad;
	}
	public BigDecimal getBdMontoMulta() {
		return bdMontoMulta;
	}
	public void setBdMontoMulta(BigDecimal bdMontoMulta) {
		this.bdMontoMulta = bdMontoMulta;
	}
	public BigDecimal getBdMontoInteres() {
		return bdMontoInteres;
	}
	public void setBdMontoInteres(BigDecimal bdMontoInteres) {
		this.bdMontoInteres = bdMontoInteres;
	}
	public BigDecimal getBdMontoCtaPorPagar() {
		return bdMontoCtaPorPagar;
	}
	public void setBdMontoCtaPorPagar(BigDecimal bdMontoCtaPorPagar) {
		this.bdMontoCtaPorPagar = bdMontoCtaPorPagar;
	}
	public BigDecimal getBdMontoCtaPorCobrar() {
		return bdMontoCtaPorCobrar;
	}
	public void setBdMontoCtaPorCobrar(BigDecimal bdMontoCtaPorCobrar) {
		this.bdMontoCtaPorCobrar = bdMontoCtaPorCobrar;
	}
	public BigDecimal getBdSumaMontoAporte() {
		return bdSumaMontoAporte;
	}
	public void setBdSumaMontoAporte(BigDecimal bdSumaMontoAporte) {
		this.bdSumaMontoAporte = bdSumaMontoAporte;
	}
	public BigDecimal getBdSumaMontoMantenimiento() {
		return bdSumaMontoMantenimiento;
	}
	public void setBdSumaMontoMantenimiento(BigDecimal bdSumaMontoMantenimiento) {
		this.bdSumaMontoMantenimiento = bdSumaMontoMantenimiento;
	}
	public BigDecimal getBdSumaMontoFondoSepelio() {
		return bdSumaMontoFondoSepelio;
	}
	public void setBdSumaMontoFondoSepelio(BigDecimal bdSumaMontoFondoSepelio) {
		this.bdSumaMontoFondoSepelio = bdSumaMontoFondoSepelio;
	}
	public BigDecimal getBdSumaMontoFondoRetiro() {
		return bdSumaMontoFondoRetiro;
	}
	public void setBdSumaMontoFondoRetiro(BigDecimal bdSumaMontoFondoRetiro) {
		this.bdSumaMontoFondoRetiro = bdSumaMontoFondoRetiro;
	}
	public BigDecimal getBdSumaMontoPrestamo() {
		return bdSumaMontoPrestamo;
	}
	public void setBdSumaMontoPrestamo(BigDecimal bdSumaMontoPrestamo) {
		this.bdSumaMontoPrestamo = bdSumaMontoPrestamo;
	}
	public BigDecimal getBdSumaMontoCredito() {
		return bdSumaMontoCredito;
	}
	public void setBdSumaMontoCredito(BigDecimal bdSumaMontoCredito) {
		this.bdSumaMontoCredito = bdSumaMontoCredito;
	}
	public BigDecimal getBdSumaMontoActividad() {
		return bdSumaMontoActividad;
	}
	public void setBdSumaMontoActividad(BigDecimal bdSumaMontoActividad) {
		this.bdSumaMontoActividad = bdSumaMontoActividad;
	}
	public BigDecimal getBdSumaMontoMulta() {
		return bdSumaMontoMulta;
	}
	public void setBdSumaMontoMulta(BigDecimal bdSumaMontoMulta) {
		this.bdSumaMontoMulta = bdSumaMontoMulta;
	}
	public BigDecimal getBdSumaMontoInteres() {
		return bdSumaMontoInteres;
	}
	public void setBdSumaMontoInteres(BigDecimal bdSumaMontoInteres) {
		this.bdSumaMontoInteres = bdSumaMontoInteres;
	}
	public BigDecimal getBdSumaMontoCtaPorPagar() {
		return bdSumaMontoCtaPorPagar;
	}
	public void setBdSumaMontoCtaPorPagar(BigDecimal bdSumaMontoCtaPorPagar) {
		this.bdSumaMontoCtaPorPagar = bdSumaMontoCtaPorPagar;
	}
	public BigDecimal getBdSumaMontoCtaPorCobrar() {
		return bdSumaMontoCtaPorCobrar;
	}
	public void setBdSumaMontoCtaPorCobrar(BigDecimal bdSumaMontoCtaPorCobrar) {
		this.bdSumaMontoCtaPorCobrar = bdSumaMontoCtaPorCobrar;
	}
	public BigDecimal getBdSumaMontoFila() {
		return bdSumaMontoFila;
	}
	public void setBdSumaMontoFila(BigDecimal bdSumaMontoFila) {
		this.bdSumaMontoFila = bdSumaMontoFila;
	}
	public Integer getIntEmpresa() {
		return intEmpresa;
	}
	public void setIntEmpresa(Integer intEmpresa) {
		this.intEmpresa = intEmpresa;
	}
	public Integer getIntCuenta() {
		return intCuenta;
	}
	public void setIntCuenta(Integer intCuenta) {
		this.intCuenta = intCuenta;
	}
	public Integer getIntPersona() {
		return intPersona;
	}
	public void setIntPersona(Integer intPersona) {
		this.intPersona = intPersona;
	}
	public Integer getIntItemCuentaConcepto() {
		return intItemCuentaConcepto;
	}
	public void setIntItemCuentaConcepto(Integer intItemCuentaConcepto) {
		this.intItemCuentaConcepto = intItemCuentaConcepto;
	}
	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}
	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}
	public Integer getIntItemExpedienteDetalle() {
		return intItemExpedienteDetalle;
	}
	public void setIntItemExpedienteDetalle(Integer intItemExpedienteDetalle) {
		this.intItemExpedienteDetalle = intItemExpedienteDetalle;
	}
	public Integer getIntParaTipoMovimiento() {
		return intParaTipoMovimiento;
	}
	public void setIntParaTipoMovimiento(Integer intParaTipoMovimiento) {
		this.intParaTipoMovimiento = intParaTipoMovimiento;
	}
	public Integer getIntParaTipoConceptoGeneral() {
		return intParaTipoConceptoGeneral;
	}
	public void setIntParaTipoConceptoGeneral(Integer intParaTipoConceptoGeneral) {
		this.intParaTipoConceptoGeneral = intParaTipoConceptoGeneral;
	}
	public String getStrNumeroDocumento() {
		return strNumeroDocumento;
	}
	public void setStrNumeroDocumento(String strNumeroDocumento) {
		this.strNumeroDocumento = strNumeroDocumento;
	}
	public Integer getIntParaTipoCargoAbono() {
		return intParaTipoCargoAbono;
	}
	public void setIntParaTipoCargoAbono(Integer intParaTipoCargoAbono) {
		this.intParaTipoCargoAbono = intParaTipoCargoAbono;
	}
	public String getStrBdMontoAporte() {
		return strBdMontoAporte;
	}
	public void setStrBdMontoAporte(String strBdMontoAporte) {
		this.strBdMontoAporte = strBdMontoAporte;
	}
	public String getStrBdMontoMantenimiento() {
		return strBdMontoMantenimiento;
	}
	public void setStrBdMontoMantenimiento(String strBdMontoMantenimiento) {
		this.strBdMontoMantenimiento = strBdMontoMantenimiento;
	}
	public String getStrBdMontoFondoSepelio() {
		return strBdMontoFondoSepelio;
	}
	public void setStrBdMontoFondoSepelio(String strBdMontoFondoSepelio) {
		this.strBdMontoFondoSepelio = strBdMontoFondoSepelio;
	}
	public String getStrBdMontoFondoRetiro() {
		return strBdMontoFondoRetiro;
	}
	public void setStrBdMontoFondoRetiro(String strBdMontoFondoRetiro) {
		this.strBdMontoFondoRetiro = strBdMontoFondoRetiro;
	}
	public String getStrBdMontoPrestamo() {
		return strBdMontoPrestamo;
	}
	public void setStrBdMontoPrestamo(String strBdMontoPrestamo) {
		this.strBdMontoPrestamo = strBdMontoPrestamo;
	}
	public String getStrBdMontoCredito() {
		return strBdMontoCredito;
	}
	public void setStrBdMontoCredito(String strBdMontoCredito) {
		this.strBdMontoCredito = strBdMontoCredito;
	}
	public String getStrBdMontoActividad() {
		return strBdMontoActividad;
	}
	public void setStrBdMontoActividad(String strBdMontoActividad) {
		this.strBdMontoActividad = strBdMontoActividad;
	}
	public String getStrBdMontoMulta() {
		return strBdMontoMulta;
	}
	public void setStrBdMontoMulta(String strBdMontoMulta) {
		this.strBdMontoMulta = strBdMontoMulta;
	}
	public String getStrBdMontoInteres() {
		return strBdMontoInteres;
	}
	public void setStrBdMontoInteres(String strBdMontoInteres) {
		this.strBdMontoInteres = strBdMontoInteres;
	}
	public String getStrBdMontoCtaPorPagar() {
		return strBdMontoCtaPorPagar;
	}
	public void setStrBdMontoCtaPorPagar(String strBdMontoCtaPorPagar) {
		this.strBdMontoCtaPorPagar = strBdMontoCtaPorPagar;
	}
	public String getStrBdMontoCtaPorCobrar() {
		return strBdMontoCtaPorCobrar;
	}
	public void setStrBdMontoCtaPorCobrar(String strBdMontoCtaPorCobrar) {
		this.strBdMontoCtaPorCobrar = strBdMontoCtaPorCobrar;
	}
	public String getStrBdSumaMontoFila() {
		return strBdSumaMontoFila;
	}
	public void setStrBdSumaMontoFila(String strBdSumaMontoFila) {
		this.strBdSumaMontoFila = strBdSumaMontoFila;
	}
	public Integer getIntSignoMontoAporte() {
		return intSignoMontoAporte;
	}
	public void setIntSignoMontoAporte(Integer intSignoMontoAporte) {
		this.intSignoMontoAporte = intSignoMontoAporte;
	}
	public Integer getIntSignoMontoMantenimiento() {
		return intSignoMontoMantenimiento;
	}
	public void setIntSignoMontoMantenimiento(Integer intSignoMontoMantenimiento) {
		this.intSignoMontoMantenimiento = intSignoMontoMantenimiento;
	}
	public Integer getIntSignoMontoFondoSepelio() {
		return intSignoMontoFondoSepelio;
	}
	public void setIntSignoMontoFondoSepelio(Integer intSignoMontoFondoSepelio) {
		this.intSignoMontoFondoSepelio = intSignoMontoFondoSepelio;
	}
	public Integer getIntSignoMontoFondoRetiro() {
		return intSignoMontoFondoRetiro;
	}
	public void setIntSignoMontoFondoRetiro(Integer intSignoMontoFondoRetiro) {
		this.intSignoMontoFondoRetiro = intSignoMontoFondoRetiro;
	}
	public Integer getIntSignoMontoPrestamo() {
		return intSignoMontoPrestamo;
	}
	public void setIntSignoMontoPrestamo(Integer intSignoMontoPrestamo) {
		this.intSignoMontoPrestamo = intSignoMontoPrestamo;
	}
	public Integer getIntSignoMontoCredito() {
		return intSignoMontoCredito;
	}
	public void setIntSignoMontoCredito(Integer intSignoMontoCredito) {
		this.intSignoMontoCredito = intSignoMontoCredito;
	}
	public Integer getIntSignoMontoActividad() {
		return intSignoMontoActividad;
	}
	public void setIntSignoMontoActividad(Integer intSignoMontoActividad) {
		this.intSignoMontoActividad = intSignoMontoActividad;
	}
	public Integer getIntSignoMontoMulta() {
		return intSignoMontoMulta;
	}
	public void setIntSignoMontoMulta(Integer intSignoMontoMulta) {
		this.intSignoMontoMulta = intSignoMontoMulta;
	}
	public Integer getIntSignoMontoInteres() {
		return intSignoMontoInteres;
	}
	public void setIntSignoMontoInteres(Integer intSignoMontoInteres) {
		this.intSignoMontoInteres = intSignoMontoInteres;
	}
	public Integer getIntSignoMontoCtaPorPagar() {
		return intSignoMontoCtaPorPagar;
	}
	public void setIntSignoMontoCtaPorPagar(Integer intSignoMontoCtaPorPagar) {
		this.intSignoMontoCtaPorPagar = intSignoMontoCtaPorPagar;
	}
	public Integer getIntSignoMontoCtaPorCobrar() {
		return intSignoMontoCtaPorCobrar;
	}
	public void setIntSignoMontoCtaPorCobrar(Integer intSignoMontoCtaPorCobrar) {
		this.intSignoMontoCtaPorCobrar = intSignoMontoCtaPorCobrar;
	}
}
