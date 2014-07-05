package pe.com.tumi.estadoCuenta.domain;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DataBeanEstadoCuentaPrevisionSocial extends TumiDomain {
	
	//Concepto Pago PK
	private Integer intPersEmpresaPk;
	private Integer intCuentaPk;
	private Integer intItemCtaCpto;
	private Integer intItemCtaCptoDet;
	private Integer intItemConceptoPago;
	
	//Beneficios Otrogados
	private String strFechaSolicitud;
	private String intNumeroSolicitud;
	private String strDescripcionTipoBeneficio;
	private BigDecimal bdMontoNeto;
	private Integer intCodVinculoFallecido;
	private String strNombreFallecido;
	private Integer intCodVinculoBeneficiario;
	private String strNombreBeneficiario;
	
	//Pago Cuotas
	private Integer intItemCuentaConcepto;
	private Integer intItemCuentaConceptoDetalle;
	private Integer intPeriodoInicio; //Formato "YYYYMM"
	private Integer intPeriodoFin; //Formato "YYYYMM"
	private Integer intAnioInicio;
	private Integer intMesInicio;
	private Integer intAnioFin;
	private Integer intMesFin;
	private BigDecimal bdCuotaMensual;
	private BigDecimal bdSumaMontoPago;
	
	private Integer intNumeroCuotas;
	private BigDecimal bdMontoProvisionado;
	private BigDecimal bdMontoPendiente;
	private BigDecimal bdMontoCancelado;
	private BigDecimal bdMontoAcumulado;
	
	private List<DataBeanEstadoCuentaPrevisionSocial> lstConceptoPago;
	
	//Popup Fondo de Sepelio
	private String strFechaMovimiento;
	private String strDescTipoCargoAbono;
	private Integer intPeriodoFechaMovimiento;
	private Integer intPeriodoFechaCptoPago;
	private String strDescTipoMovimiento;
	private String strNumeroDocumento;
	private BigDecimal bdMontoMovimiento;
	
	//Popup Fondo de Retiro
	
	private Integer intTipoConceptoGral;
	private String strDescTipoConceptoGral;
	private BigDecimal bdMontoMovimientoEne;
	private BigDecimal bdMontoMovimientoFeb;
	private BigDecimal bdMontoMovimientoMar;
	private BigDecimal bdMontoMovimientoAbr;
	private BigDecimal bdMontoMovimientoMay;
	private BigDecimal bdMontoMovimientoJun;
	private BigDecimal bdMontoMovimientoJul;
	private BigDecimal bdMontoMovimientoAgo;
	private BigDecimal bdMontoMovimientoSet;
	private BigDecimal bdMontoMovimientoOct;
	private BigDecimal bdMontoMovimientoNov;
	private BigDecimal bdMontoMovimientoDic;
	private BigDecimal bdMontoTotal;	
	
	public String getStrFechaSolicitud() {
		return strFechaSolicitud;
	}
	public void setStrFechaSolicitud(String strFechaSolicitud) {
		this.strFechaSolicitud = strFechaSolicitud;
	}
	public String getIntNumeroSolicitud() {
		return intNumeroSolicitud;
	}
	public void setIntNumeroSolicitud(String intNumeroSolicitud) {
		this.intNumeroSolicitud = intNumeroSolicitud;
	}
	public String getStrDescripcionTipoBeneficio() {
		return strDescripcionTipoBeneficio;
	}
	public void setStrDescripcionTipoBeneficio(String strDescripcionTipoBeneficio) {
		this.strDescripcionTipoBeneficio = strDescripcionTipoBeneficio;
	}
	public BigDecimal getBdMontoNeto() {
		return bdMontoNeto;
	}
	public void setBdMontoNeto(BigDecimal bdMontoNeto) {
		this.bdMontoNeto = bdMontoNeto;
	}
	public Integer getIntCodVinculoFallecido() {
		return intCodVinculoFallecido;
	}
	public void setIntCodVinculoFallecido(Integer intCodVinculoFallecido) {
		this.intCodVinculoFallecido = intCodVinculoFallecido;
	}
	public String getStrNombreFallecido() {
		return strNombreFallecido;
	}
	public void setStrNombreFallecido(String strNombreFallecido) {
		this.strNombreFallecido = strNombreFallecido;
	}
	public Integer getIntCodVinculoBeneficiario() {
		return intCodVinculoBeneficiario;
	}
	public void setIntCodVinculoBeneficiario(Integer intCodVinculoBeneficiario) {
		this.intCodVinculoBeneficiario = intCodVinculoBeneficiario;
	}
	public String getStrNombreBeneficiario() {
		return strNombreBeneficiario;
	}
	public void setStrNombreBeneficiario(String strNombreBeneficiario) {
		this.strNombreBeneficiario = strNombreBeneficiario;
	}
	public Integer getIntItemCuentaConcepto() {
		return intItemCuentaConcepto;
	}
	public void setIntItemCuentaConcepto(Integer intItemCuentaConcepto) {
		this.intItemCuentaConcepto = intItemCuentaConcepto;
	}
	public Integer getIntItemCuentaConceptoDetalle() {
		return intItemCuentaConceptoDetalle;
	}
	public void setIntItemCuentaConceptoDetalle(Integer intItemCuentaConceptoDetalle) {
		this.intItemCuentaConceptoDetalle = intItemCuentaConceptoDetalle;
	}
	public Integer getIntPeriodoInicio() {
		return intPeriodoInicio;
	}
	public void setIntPeriodoInicio(Integer intPeriodoInicio) {
		this.intPeriodoInicio = intPeriodoInicio;
	}
	public Integer getIntPeriodoFin() {
		return intPeriodoFin;
	}
	public void setIntPeriodoFin(Integer intPeriodoFin) {
		this.intPeriodoFin = intPeriodoFin;
	}
	public Integer getIntAnioInicio() {
		return intAnioInicio;
	}
	public void setIntAnioInicio(Integer intAnioInicio) {
		this.intAnioInicio = intAnioInicio;
	}
	public Integer getIntMesInicio() {
		return intMesInicio;
	}
	public void setIntMesInicio(Integer intMesInicio) {
		this.intMesInicio = intMesInicio;
	}
	public Integer getIntAnioFin() {
		return intAnioFin;
	}
	public void setIntAnioFin(Integer intAnioFin) {
		this.intAnioFin = intAnioFin;
	}
	public Integer getIntMesFin() {
		return intMesFin;
	}
	public void setIntMesFin(Integer intMesFin) {
		this.intMesFin = intMesFin;
	}
	public BigDecimal getBdCuotaMensual() {
		return bdCuotaMensual;
	}
	public void setBdCuotaMensual(BigDecimal bdCuotaMensual) {
		this.bdCuotaMensual = bdCuotaMensual;
	}
	public BigDecimal getBdSumaMontoPago() {
		return bdSumaMontoPago;
	}
	public void setBdSumaMontoPago(BigDecimal bdSumaMontoPago) {
		this.bdSumaMontoPago = bdSumaMontoPago;
	}
	public Integer getIntNumeroCuotas() {
		return intNumeroCuotas;
	}
	public void setIntNumeroCuotas(Integer intNumeroCuotas) {
		this.intNumeroCuotas = intNumeroCuotas;
	}
	public BigDecimal getBdMontoProvisionado() {
		return bdMontoProvisionado;
	}
	public void setBdMontoProvisionado(BigDecimal bdMontoProvisionado) {
		this.bdMontoProvisionado = bdMontoProvisionado;
	}
	public BigDecimal getBdMontoPendiente() {
		return bdMontoPendiente;
	}
	public void setBdMontoPendiente(BigDecimal bdMontoPendiente) {
		this.bdMontoPendiente = bdMontoPendiente;
	}
	public BigDecimal getBdMontoCancelado() {
		return bdMontoCancelado;
	}
	public void setBdMontoCancelado(BigDecimal bdMontoCancelado) {
		this.bdMontoCancelado = bdMontoCancelado;
	}
	public BigDecimal getBdMontoAcumulado() {
		return bdMontoAcumulado;
	}
	public void setBdMontoAcumulado(BigDecimal bdMontoAcumulado) {
		this.bdMontoAcumulado = bdMontoAcumulado;
	}
	public String getStrFechaMovimiento() {
		return strFechaMovimiento;
	}
	public void setStrFechaMovimiento(String strFechaMovimiento) {
		this.strFechaMovimiento = strFechaMovimiento;
	}
	public String getStrDescTipoCargoAbono() {
		return strDescTipoCargoAbono;
	}
	public void setStrDescTipoCargoAbono(String strDescTipoCargoAbono) {
		this.strDescTipoCargoAbono = strDescTipoCargoAbono;
	}
	public Integer getIntPeriodoFechaMovimiento() {
		return intPeriodoFechaMovimiento;
	}
	public void setIntPeriodoFechaMovimiento(Integer intPeriodoFechaMovimiento) {
		this.intPeriodoFechaMovimiento = intPeriodoFechaMovimiento;
	}
	public Integer getIntPeriodoFechaCptoPago() {
		return intPeriodoFechaCptoPago;
	}
	public void setIntPeriodoFechaCptoPago(Integer intPeriodoFechaCptoPago) {
		this.intPeriodoFechaCptoPago = intPeriodoFechaCptoPago;
	}
	public String getStrDescTipoMovimiento() {
		return strDescTipoMovimiento;
	}
	public void setStrDescTipoMovimiento(String strDescTipoMovimiento) {
		this.strDescTipoMovimiento = strDescTipoMovimiento;
	}
	public String getStrNumeroDocumento() {
		return strNumeroDocumento;
	}
	public void setStrNumeroDocumento(String strNumeroDocumento) {
		this.strNumeroDocumento = strNumeroDocumento;
	}
	public BigDecimal getBdMontoMovimiento() {
		return bdMontoMovimiento;
	}
	public void setBdMontoMovimiento(BigDecimal bdMontoMovimiento) {
		this.bdMontoMovimiento = bdMontoMovimiento;
	}
	public String getStrDescTipoConceptoGral() {
		return strDescTipoConceptoGral;
	}
	public void setStrDescTipoConceptoGral(String strDescTipoConceptoGral) {
		this.strDescTipoConceptoGral = strDescTipoConceptoGral;
	}
	public BigDecimal getBdMontoMovimientoEne() {
		return bdMontoMovimientoEne;
	}
	public void setBdMontoMovimientoEne(BigDecimal bdMontoMovimientoEne) {
		this.bdMontoMovimientoEne = bdMontoMovimientoEne;
	}
	public BigDecimal getBdMontoMovimientoFeb() {
		return bdMontoMovimientoFeb;
	}
	public void setBdMontoMovimientoFeb(BigDecimal bdMontoMovimientoFeb) {
		this.bdMontoMovimientoFeb = bdMontoMovimientoFeb;
	}
	public BigDecimal getBdMontoMovimientoMar() {
		return bdMontoMovimientoMar;
	}
	public void setBdMontoMovimientoMar(BigDecimal bdMontoMovimientoMar) {
		this.bdMontoMovimientoMar = bdMontoMovimientoMar;
	}
	public BigDecimal getBdMontoMovimientoAbr() {
		return bdMontoMovimientoAbr;
	}
	public void setBdMontoMovimientoAbr(BigDecimal bdMontoMovimientoAbr) {
		this.bdMontoMovimientoAbr = bdMontoMovimientoAbr;
	}
	public BigDecimal getBdMontoMovimientoMay() {
		return bdMontoMovimientoMay;
	}
	public void setBdMontoMovimientoMay(BigDecimal bdMontoMovimientoMay) {
		this.bdMontoMovimientoMay = bdMontoMovimientoMay;
	}
	public BigDecimal getBdMontoMovimientoJun() {
		return bdMontoMovimientoJun;
	}
	public void setBdMontoMovimientoJun(BigDecimal bdMontoMovimientoJun) {
		this.bdMontoMovimientoJun = bdMontoMovimientoJun;
	}
	public BigDecimal getBdMontoMovimientoJul() {
		return bdMontoMovimientoJul;
	}
	public void setBdMontoMovimientoJul(BigDecimal bdMontoMovimientoJul) {
		this.bdMontoMovimientoJul = bdMontoMovimientoJul;
	}
	public BigDecimal getBdMontoMovimientoAgo() {
		return bdMontoMovimientoAgo;
	}
	public void setBdMontoMovimientoAgo(BigDecimal bdMontoMovimientoAgo) {
		this.bdMontoMovimientoAgo = bdMontoMovimientoAgo;
	}
	public BigDecimal getBdMontoMovimientoSet() {
		return bdMontoMovimientoSet;
	}
	public void setBdMontoMovimientoSet(BigDecimal bdMontoMovimientoSet) {
		this.bdMontoMovimientoSet = bdMontoMovimientoSet;
	}
	public BigDecimal getBdMontoMovimientoOct() {
		return bdMontoMovimientoOct;
	}
	public void setBdMontoMovimientoOct(BigDecimal bdMontoMovimientoOct) {
		this.bdMontoMovimientoOct = bdMontoMovimientoOct;
	}
	public BigDecimal getBdMontoMovimientoNov() {
		return bdMontoMovimientoNov;
	}
	public void setBdMontoMovimientoNov(BigDecimal bdMontoMovimientoNov) {
		this.bdMontoMovimientoNov = bdMontoMovimientoNov;
	}
	public BigDecimal getBdMontoMovimientoDic() {
		return bdMontoMovimientoDic;
	}
	public void setBdMontoMovimientoDic(BigDecimal bdMontoMovimientoDic) {
		this.bdMontoMovimientoDic = bdMontoMovimientoDic;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntCuentaPk() {
		return intCuentaPk;
	}
	public void setIntCuentaPk(Integer intCuentaPk) {
		this.intCuentaPk = intCuentaPk;
	}
	public Integer getIntItemCtaCpto() {
		return intItemCtaCpto;
	}
	public void setIntItemCtaCpto(Integer intItemCtaCpto) {
		this.intItemCtaCpto = intItemCtaCpto;
	}
	public Integer getIntItemCtaCptoDet() {
		return intItemCtaCptoDet;
	}
	public void setIntItemCtaCptoDet(Integer intItemCtaCptoDet) {
		this.intItemCtaCptoDet = intItemCtaCptoDet;
	}
	public Integer getIntItemConceptoPago() {
		return intItemConceptoPago;
	}
	public void setIntItemConceptoPago(Integer intItemConceptoPago) {
		this.intItemConceptoPago = intItemConceptoPago;
	}
	public List<DataBeanEstadoCuentaPrevisionSocial> getLstConceptoPago() {
		return lstConceptoPago;
	}
	public void setLstConceptoPago(
			List<DataBeanEstadoCuentaPrevisionSocial> lstConceptoPago) {
		this.lstConceptoPago = lstConceptoPago;
	}
	public Integer getIntTipoConceptoGral() {
		return intTipoConceptoGral;
	}
	public void setIntTipoConceptoGral(Integer intTipoConceptoGral) {
		this.intTipoConceptoGral = intTipoConceptoGral;
	}
}
