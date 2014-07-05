package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;

public class EgresoDetalle extends TumiDomain{

	private	EgresoDetalleId	id;
	private	Integer	intParaDocumentoGeneral;
	private	Integer	intParaTipoComprobante;
	private	String	strSerieDocumento;
	private	String	strNumeroDocumento;
	private	String	strDescripcionEgreso;
	private	Integer	intPersEmpresaGirado;
	private	Integer	intPersonaGirado;
	private	Integer	intCuentaGirado;
	private	Integer	intSucuIdSucursalEgreso;
	private	Integer	intSudeIdSubsucursalEgreso;
	private	Integer	intParaTipoMoneda;
	private	BigDecimal	bdMontoDiferencial;
	private	BigDecimal	bdMontoCargo;
	private	BigDecimal	bdMontoAbono;
	private	Integer	intParaEstado;
	private	Timestamp	tsFechaRegistro;
	private	Integer	intPersEmpresaUsuario;
	private	Integer	intPersPersonaUsuario;
	private	Integer	intPersEmpresaLibroDestino;
	private	Integer	intContPeriodoLibroDestino;
	private	Integer	intContCodigoLibroDestino;
	private	Integer	intPersEmpresaCuenta;
	private	Integer	intContPeriodoCuenta;
	private	String	strContNumeroCuenta;
	private	Integer	intParaTipoFondoFijo;
	private	Integer	intItemPeriodoFondo;
	private	Integer	intSucuIdSucursal;
	private	Integer	intItemFondoFijo;
	
	//JCHAVEZ 29.01.2014
	private ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle; 
	
	
	public EgresoDetalle(){
		id = new EgresoDetalleId();
	}
	
	public EgresoDetalleId getId() {
		return id;
	}
	public void setId(EgresoDetalleId id) {
		this.id = id;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Integer getIntParaTipoComprobante() {
		return intParaTipoComprobante;
	}
	public void setIntParaTipoComprobante(Integer intParaTipoComprobante) {
		this.intParaTipoComprobante = intParaTipoComprobante;
	}
	public String getStrSerieDocumento() {
		return strSerieDocumento;
	}
	public void setStrSerieDocumento(String strSerieDocumento) {
		this.strSerieDocumento = strSerieDocumento;
	}
	public String getStrNumeroDocumento() {
		return strNumeroDocumento;
	}
	public void setStrNumeroDocumento(String strNumeroDocumento) {
		this.strNumeroDocumento = strNumeroDocumento;
	}
	public String getStrDescripcionEgreso() {
		return strDescripcionEgreso;
	}
	public void setStrDescripcionEgreso(String strDescripcionEgreso) {
		this.strDescripcionEgreso = strDescripcionEgreso;
	}
	public Integer getIntPersEmpresaGirado() {
		return intPersEmpresaGirado;
	}
	public void setIntPersEmpresaGirado(Integer intPersEmpresaGirado) {
		this.intPersEmpresaGirado = intPersEmpresaGirado;
	}
	public Integer getIntPersonaGirado() {
		return intPersonaGirado;
	}
	public void setIntPersonaGirado(Integer intPersonaGirado) {
		this.intPersonaGirado = intPersonaGirado;
	}
	public Integer getIntCuentaGirado() {
		return intCuentaGirado;
	}
	public void setIntCuentaGirado(Integer intCuentaGirado) {
		this.intCuentaGirado = intCuentaGirado;
	}
	public Integer getIntSucuIdSucursalEgreso() {
		return intSucuIdSucursalEgreso;
	}
	public void setIntSucuIdSucursalEgreso(Integer intSucuIdSucursalEgreso) {
		this.intSucuIdSucursalEgreso = intSucuIdSucursalEgreso;
	}
	public Integer getIntSudeIdSubsucursalEgreso() {
		return intSudeIdSubsucursalEgreso;
	}
	public void setIntSudeIdSubsucursalEgreso(Integer intSudeIdSubsucursalEgreso) {
		this.intSudeIdSubsucursalEgreso = intSudeIdSubsucursalEgreso;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public BigDecimal getBdMontoDiferencial() {
		return bdMontoDiferencial;
	}
	public void setBdMontoDiferencial(BigDecimal bdMontoDiferencial) {
		this.bdMontoDiferencial = bdMontoDiferencial;
	}
	public BigDecimal getBdMontoCargo() {
		return bdMontoCargo;
	}
	public void setBdMontoCargo(BigDecimal bdMontoCargo) {
		this.bdMontoCargo = bdMontoCargo;
	}
	public BigDecimal getBdMontoAbono() {
		return bdMontoAbono;
	}
	public void setBdMontoAbono(BigDecimal bdMontoAbono) {
		this.bdMontoAbono = bdMontoAbono;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersEmpresaUsuario() {
		return intPersEmpresaUsuario;
	}
	public void setIntPersEmpresaUsuario(Integer intPersEmpresaUsuario) {
		this.intPersEmpresaUsuario = intPersEmpresaUsuario;
	}
	public Integer getIntPersPersonaUsuario() {
		return intPersPersonaUsuario;
	}
	public void setIntPersPersonaUsuario(Integer intPersPersonaUsuario) {
		this.intPersPersonaUsuario = intPersPersonaUsuario;
	}
	public Integer getIntPersEmpresaLibroDestino() {
		return intPersEmpresaLibroDestino;
	}
	public void setIntPersEmpresaLibroDestino(Integer intPersEmpresaLibroDestino) {
		this.intPersEmpresaLibroDestino = intPersEmpresaLibroDestino;
	}
	public Integer getIntContPeriodoLibroDestino() {
		return intContPeriodoLibroDestino;
	}
	public void setIntContPeriodoLibroDestino(Integer intContPeriodoLibroDestino) {
		this.intContPeriodoLibroDestino = intContPeriodoLibroDestino;
	}
	public Integer getIntContCodigoLibroDestino() {
		return intContCodigoLibroDestino;
	}
	public void setIntContCodigoLibroDestino(Integer intContCodigoLibroDestino) {
		this.intContCodigoLibroDestino = intContCodigoLibroDestino;
	}
	public Integer getIntPersEmpresaCuenta() {
		return intPersEmpresaCuenta;
	}
	public void setIntPersEmpresaCuenta(Integer intPersEmpresaCuenta) {
		this.intPersEmpresaCuenta = intPersEmpresaCuenta;
	}
	public Integer getIntContPeriodoCuenta() {
		return intContPeriodoCuenta;
	}
	public void setIntContPeriodoCuenta(Integer intContPeriodoCuenta) {
		this.intContPeriodoCuenta = intContPeriodoCuenta;
	}
	public String getStrContNumeroCuenta() {
		return strContNumeroCuenta;
	}
	public void setStrContNumeroCuenta(String strContNumeroCuenta) {
		this.strContNumeroCuenta = strContNumeroCuenta;
	}
	public Integer getIntParaTipoFondoFijo() {
		return intParaTipoFondoFijo;
	}
	public void setIntParaTipoFondoFijo(Integer intParaTipoFondoFijo) {
		this.intParaTipoFondoFijo = intParaTipoFondoFijo;
	}
	public Integer getIntItemPeriodoFondo() {
		return intItemPeriodoFondo;
	}
	public void setIntItemPeriodoFondo(Integer intItemPeriodoFondo) {
		this.intItemPeriodoFondo = intItemPeriodoFondo;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntItemFondoFijo() {
		return intItemFondoFijo;
	}
	public void setIntItemFondoFijo(Integer intItemFondoFijo) {
		this.intItemFondoFijo = intItemFondoFijo;
	}
	@Override
	public String toString() {
		return "EgresoDetalle [id=" + id + ", intParaDocumentoGeneral="
				+ intParaDocumentoGeneral + ", intParaTipoComprobante="
				+ intParaTipoComprobante + ", strSerieDocumento="
				+ strSerieDocumento + ", strNumeroDocumento="
				+ strNumeroDocumento + ", strDescripcionEgreso="
				+ strDescripcionEgreso + ", intPersEmpresaGirado="
				+ intPersEmpresaGirado + ", intPersonaGirado="
				+ intPersonaGirado + ", intCuentaGirado=" + intCuentaGirado
				+ ", intSucuIdSucursalEgreso=" + intSucuIdSucursalEgreso
				+ ", intSudeIdSubsucursalEgreso=" + intSudeIdSubsucursalEgreso
				+ ", intParaTipoMoneda=" + intParaTipoMoneda
				+ ", bdMontoDiferencial=" + bdMontoDiferencial
				+ ", bdMontoCargo=" + bdMontoCargo + ", bdMontoAbono="
				+ bdMontoAbono + ", intParaEstado=" + intParaEstado
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", intPersEmpresaLibroDestino=" + intPersEmpresaLibroDestino
				+ ", intContPeriodoLibroDestino=" + intContPeriodoLibroDestino
				+ ", intContCodigoLibroDestino=" + intContCodigoLibroDestino
				+ ", intPersEmpresaCuenta=" + intPersEmpresaCuenta
				+ ", intContPeriodoCuenta=" + intContPeriodoCuenta
				+ ", strContNumeroCuenta=" + strContNumeroCuenta
				+ ", intParaTipoFondoFijo=" + intParaTipoFondoFijo
				+ ", intItemPeriodoFondo=" + intItemPeriodoFondo
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intItemFondoFijo=" + intItemFondoFijo + "]";
	}

	public ExpedienteLiquidacionDetalle getExpedienteLiquidacionDetalle() {
		return expedienteLiquidacionDetalle;
	}

	public void setExpedienteLiquidacionDetalle(
			ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle) {
		this.expedienteLiquidacionDetalle = expedienteLiquidacionDetalle;
	}
	
	
}
