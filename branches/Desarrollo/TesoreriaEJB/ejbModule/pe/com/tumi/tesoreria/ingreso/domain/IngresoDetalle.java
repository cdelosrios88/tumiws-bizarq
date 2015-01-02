package pe.com.tumi.tesoreria.ingreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class IngresoDetalle extends TumiDomain{

	private IngresoDetalleId	id;
	private Integer intParaDocumentoGeneral;
	private Integer intParaTipoComprobante;
	private String 	strSerieDocumento;
	private String 	strNumeroDocumento;
	private String 	strDescripcionIngreso;
	private Integer intPersEmpresaGirado;
	private Integer intPersPersonaGirado;
	private Integer intCuentaGirado;
	private Integer intParaTipoPagoCuenta;
	private Integer intSucuIdSucursalIn;
	private Integer intSucuIdSubsucursalIn;
	private Integer intParaTipoMoneda;
	private BigDecimal 	bdAjusteDeposito;
	private BigDecimal 	bdMontoCargo;
	private BigDecimal 	bdMontoAbono;
	private Integer intParaEstado;
	private Timestamp 	tsFechaRegistro;
	private Integer intPersEmpresaUsuario;
	private Integer intPersPersonaUsuario;
	private Integer intPersEmpresaCuenta;
	private Integer intContPeriodoCuenta;
	private String 	strContNumeroCuenta;
	private Integer intPersEmpresa;
	private Integer intParaTipoFondoFijo;
	private Integer intItemPeriodoFondo;
	private Integer intSucuIdSucursal;
	private Integer intItemFondoFijo;
	
	//Autor y Fecha: Rodolfo Villarreal Acuña 28/11/2014
	private String strDescripcionAgencia;
	private String strDesTipoComprobante;
	private String strMontoCargoReport;
	
	public IngresoDetalle(){
		id = new IngresoDetalleId();
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
	public Integer getIntPersEmpresaGirado() {
		return intPersEmpresaGirado;
	}
	public void setIntPersEmpresaGirado(Integer intPersEmpresaGirado) {
		this.intPersEmpresaGirado = intPersEmpresaGirado;
	}
	public Integer getIntPersPersonaGirado() {
		return intPersPersonaGirado;
	}
	public void setIntPersPersonaGirado(Integer intPersPersonaGirado) {
		this.intPersPersonaGirado = intPersPersonaGirado;
	}
	public Integer getIntCuentaGirado() {
		return intCuentaGirado;
	}
	public void setIntCuentaGirado(Integer intCuentaGirado) {
		this.intCuentaGirado = intCuentaGirado;
	}
	public Integer getIntParaTipoPagoCuenta() {
		return intParaTipoPagoCuenta;
	}
	public void setIntParaTipoPagoCuenta(Integer intParaTipoPagoCuenta) {
		this.intParaTipoPagoCuenta = intParaTipoPagoCuenta;
	}
	public Integer getIntSucuIdSucursalIn() {
		return intSucuIdSucursalIn;
	}
	public void setIntSucuIdSucursalIn(Integer intSucuIdSucursalIn) {
		this.intSucuIdSucursalIn = intSucuIdSucursalIn;
	}
	public Integer getIntSucuIdSubsucursalIn() {
		return intSucuIdSubsucursalIn;
	}
	public void setIntSucuIdSubsucursalIn(Integer intSucuIdSubsucursalIn) {
		this.intSucuIdSubsucursalIn = intSucuIdSubsucursalIn;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
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
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
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
	public IngresoDetalleId getId() {
		return id;
	}
	public void setId(IngresoDetalleId id) {
		this.id = id;
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
	public String getStrDescripcionIngreso() {
		return strDescripcionIngreso;
	}
	public void setStrDescripcionIngreso(String strDescripcionIngreso) {
		this.strDescripcionIngreso = strDescripcionIngreso;
	}
	public BigDecimal getBdAjusteDeposito() {
		return bdAjusteDeposito;
	}
	public void setBdAjusteDeposito(BigDecimal bdAjusteDeposito) {
		this.bdAjusteDeposito = bdAjusteDeposito;
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
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public String getStrContNumeroCuenta() {
		return strContNumeroCuenta;
	}
	public void setStrContNumeroCuenta(String strContNumeroCuenta) {
		this.strContNumeroCuenta = strContNumeroCuenta;
	}

	@Override
	public String toString() {
		return "IngresoDetalle [id=" + id + ", intParaDocumentoGeneral="
				+ intParaDocumentoGeneral + ", intParaTipoComprobante="
				+ intParaTipoComprobante + ", strSerieDocumento="
				+ strSerieDocumento + ", strNumeroDocumento="
				+ strNumeroDocumento + ", strDescripcionIngreso="
				+ strDescripcionIngreso + ", intPersEmpresaGirado="
				+ intPersEmpresaGirado + ", intPersPersonaGirado="
				+ intPersPersonaGirado + ", intCuentaGirado=" + intCuentaGirado
				+ ", intParaTipoPagoCuenta=" + intParaTipoPagoCuenta
				+ ", intSucuIdSucursalIn=" + intSucuIdSucursalIn
				+ ", intSucuIdSubsucursalIn=" + intSucuIdSubsucursalIn
				+ ", intParaTipoMoneda=" + intParaTipoMoneda
				+ ", bdAjusteDeposito=" + bdAjusteDeposito + ", bdMontoCargo="
				+ bdMontoCargo + ", bdMontoAbono=" + bdMontoAbono
				+ ", intParaEstado=" + intParaEstado + ", tsFechaRegistro="
				+ tsFechaRegistro + ", intPersEmpresaUsuario="
				+ intPersEmpresaUsuario + ", intPersPersonaUsuario="
				+ intPersPersonaUsuario + ", intPersEmpresaCuenta="
				+ intPersEmpresaCuenta + ", intContPeriodoCuenta="
				+ intContPeriodoCuenta + ", strContNumeroCuenta="
				+ strContNumeroCuenta + ", intPersEmpresa=" + intPersEmpresa
				+ ", intParaTipoFondoFijo=" + intParaTipoFondoFijo
				+ ", intItemPeriodoFondo=" + intItemPeriodoFondo
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intItemFondoFijo=" + intItemFondoFijo + "]";
	}

	public String getStrDesTipoComprobante() {
		return strDesTipoComprobante;
	}

	public void setStrDesTipoComprobante(String strDesTipoComprobante) {
		this.strDesTipoComprobante = strDesTipoComprobante;
	}

	public String getStrDescripcionAgencia() {
		return strDescripcionAgencia;
	}

	public void setStrDescripcionAgencia(String strDescripcionAgencia) {
		this.strDescripcionAgencia = strDescripcionAgencia;
	}

	public String getStrMontoCargoReport() {
		return strMontoCargoReport;
	}

	public void setStrMontoCargoReport(String strMontoCargoReport) {
		this.strMontoCargoReport = strMontoCargoReport;
	}

}