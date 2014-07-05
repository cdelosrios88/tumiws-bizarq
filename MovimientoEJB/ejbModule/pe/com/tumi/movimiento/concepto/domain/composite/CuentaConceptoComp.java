package pe.com.tumi.movimiento.concepto.domain.composite;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;

public class CuentaConceptoComp  extends TumiDomain{
	private CuentaConcepto cuentaConcepto;
	private String strDescripcionCuenta;
	private String strNumeroCuenta;
	private String strDescripcionConcepto;
	
	// aplicables en liquidacion
	private BigDecimal bdPorcentajeBeneficio;
	private BigDecimal bdPorcentajeTotal;
	private BigDecimal bdMonto;
	private Captacion captacion;
	
	// Solo para ser usado en la validacion contra el modelo contable de liquidacion de cuentas.
	private Integer intParaConceptoGeneralModelo;
	private Integer intParaTipoCaptacionModelo;
	
	private List<CuentaConceptoDetalle> lstCuentaConceptoDetalle;
	
	
	public CuentaConcepto getCuentaConcepto() {
		return cuentaConcepto;
	}
	public void setCuentaConcepto(CuentaConcepto cuentaConcepto) {
		this.cuentaConcepto = cuentaConcepto;
	}
	public String getStrDescripcionCuenta() {
		return strDescripcionCuenta;
	}
	public void setStrDescripcionCuenta(String strDescripcionCuenta) {
		this.strDescripcionCuenta = strDescripcionCuenta;
	}
	public String getStrNumeroCuenta() {
		return strNumeroCuenta;
	}
	public void setStrNumeroCuenta(String strNumeroCuenta) {
		this.strNumeroCuenta = strNumeroCuenta;
	}
	public String getStrDescripcionConcepto() {
		return strDescripcionConcepto;
	}
	public void setStrDescripcionConcepto(String strDescripcionConcepto) {
		this.strDescripcionConcepto = strDescripcionConcepto;
	}
	public BigDecimal getBdPorcentajeBeneficio() {
		return bdPorcentajeBeneficio;
	}
	public void setBdPorcentajeBeneficio(BigDecimal bdPorcentajeBeneficio) {
		this.bdPorcentajeBeneficio = bdPorcentajeBeneficio;
	}
	public BigDecimal getBdPorcentajeTotal() {
		return bdPorcentajeTotal;
	}
	public void setBdPorcentajeTotal(BigDecimal bdPorcentajeTotal) {
		this.bdPorcentajeTotal = bdPorcentajeTotal;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public Captacion getCaptacion() {
		return captacion;
	}
	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}
	public Integer getIntParaConceptoGeneralModelo() {
		return intParaConceptoGeneralModelo;
	}
	public void setIntParaConceptoGeneralModelo(Integer intParaConceptoGeneralModelo) {
		this.intParaConceptoGeneralModelo = intParaConceptoGeneralModelo;
	}
	public Integer getIntParaTipoCaptacionModelo() {
		return intParaTipoCaptacionModelo;
	}
	public void setIntParaTipoCaptacionModelo(Integer intParaTipoCaptacionModelo) {
		this.intParaTipoCaptacionModelo = intParaTipoCaptacionModelo;
	}
	public List<CuentaConceptoDetalle> getLstCuentaConceptoDetalle() {
		return lstCuentaConceptoDetalle;
	}
	public void setLstCuentaConceptoDetalle(
			List<CuentaConceptoDetalle> lstCuentaConceptoDetalle) {
		this.lstCuentaConceptoDetalle = lstCuentaConceptoDetalle;
	}	
}
