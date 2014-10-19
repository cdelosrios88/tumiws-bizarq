package pe.com.tumi.tesoreria.ingreso.domain;

import java.math.BigDecimal;
import java.util.Date;

import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;

public class IngresoCajaComp {
	//jchavez 10.07.2014
	private Integer intIngCajaIdEmpresa;
	private Integer intIngCajaIdCuenta;
	private Integer intIngCajaId1;			//puede tomar el valor de item_expediente o item_cuentaconcepto
	private Integer intIngCajaId2;			//puede tomar el valor de item_expedientedetalle o item_cuentaconceptodetalle
	private Integer intIngCajaParaTipoX;	//puede tomar el valor de para_tipocredito o para_tipocaptacion
	private Integer intIngCajaItemX;		//puede tomar el valor de item_credito o item_captacion
	private String strIngCajaDescTipo;
	private String strIngCajaDescTipoCredito;
	private String strIngCajaNroSolicitud;
	private BigDecimal bdIngCajaMontoTotal;
	private String strIngCajaCuotas;
	private BigDecimal bdIngCajaSaldoCredito;
	private BigDecimal bdIngCajaAmortizacion;
	private BigDecimal bdIngCajaInteres;
	private InteresCancelado ultimoInteresCancelado;
	private Date dtFechaInicioInteresCancelado;
	private Integer intDiasEntreFechasInteresCancelado;
	private BigDecimal bdIngCajaSumCapitalInteres;
	private BigDecimal bdIngCajaMontoPagado;
	private BigDecimal bdIngCajaPorcentajeInteres;
	private Integer intIngCajaOrdenAmortizacion;
	private Integer intIngCajaOrdenInteres;
	private BigDecimal bdIngCajaDiferencia;
	private Integer intIngCajaFlagAmortizacionInteres;
	private String strIngCajaSocioSucursalAdministra;
	private String strIngCajaDescTipoConceptoGeneral;
	private Integer intIngCajaIdSucursalAdministra;
	private Integer intIngCajaIdSubSucursalAdministra;
	private Integer intIngCajaParaTipoPagoCuenta;
	private ConceptoPago ingCajaConceptoPago;
	//jchavez 20.07.2014
	private CuentaConceptoDetalle ingCajaCuentaConceptoDetalle;
	
	//getter's and setter's
	public Integer getIntIngCajaIdEmpresa() {
		return intIngCajaIdEmpresa;
	}
	public void setIntIngCajaIdEmpresa(Integer intIngCajaIdEmpresa) {
		this.intIngCajaIdEmpresa = intIngCajaIdEmpresa;
	}
	public Integer getIntIngCajaIdCuenta() {
		return intIngCajaIdCuenta;
	}
	public void setIntIngCajaIdCuenta(Integer intIngCajaIdCuenta) {
		this.intIngCajaIdCuenta = intIngCajaIdCuenta;
	}
	public Integer getIntIngCajaId1() {
		return intIngCajaId1;
	}
	public void setIntIngCajaId1(Integer intIngCajaId1) {
		this.intIngCajaId1 = intIngCajaId1;
	}
	public Integer getIntIngCajaId2() {
		return intIngCajaId2;
	}
	public void setIntIngCajaId2(Integer intIngCajaId2) {
		this.intIngCajaId2 = intIngCajaId2;
	}
	public Integer getIntIngCajaParaTipoX() {
		return intIngCajaParaTipoX;
	}
	public void setIntIngCajaParaTipoX(Integer intIngCajaParaTipoX) {
		this.intIngCajaParaTipoX = intIngCajaParaTipoX;
	}
	public Integer getIntIngCajaItemX() {
		return intIngCajaItemX;
	}
	public void setIntIngCajaItemX(Integer intIngCajaItemX) {
		this.intIngCajaItemX = intIngCajaItemX;
	}
	public String getStrIngCajaDescTipo() {
		return strIngCajaDescTipo;
	}
	public void setStrIngCajaDescTipo(String strIngCajaDescTipo) {
		this.strIngCajaDescTipo = strIngCajaDescTipo;
	}
	public String getStrIngCajaDescTipoCredito() {
		return strIngCajaDescTipoCredito;
	}
	public void setStrIngCajaDescTipoCredito(String strIngCajaDescTipoCredito) {
		this.strIngCajaDescTipoCredito = strIngCajaDescTipoCredito;
	}
	public String getStrIngCajaNroSolicitud() {
		return strIngCajaNroSolicitud;
	}
	public void setStrIngCajaNroSolicitud(String strIngCajaNroSolicitud) {
		this.strIngCajaNroSolicitud = strIngCajaNroSolicitud;
	}
	public BigDecimal getBdIngCajaMontoTotal() {
		return bdIngCajaMontoTotal;
	}
	public void setBdIngCajaMontoTotal(BigDecimal bdIngCajaMontoTotal) {
		this.bdIngCajaMontoTotal = bdIngCajaMontoTotal;
	}
	public String getStrIngCajaCuotas() {
		return strIngCajaCuotas;
	}
	public void setStrIngCajaCuotas(String strIngCajaCuotas) {
		this.strIngCajaCuotas = strIngCajaCuotas;
	}
	public BigDecimal getBdIngCajaSaldoCredito() {
		return bdIngCajaSaldoCredito;
	}
	public void setBdIngCajaSaldoCredito(BigDecimal bdIngCajaSaldoCredito) {
		this.bdIngCajaSaldoCredito = bdIngCajaSaldoCredito;
	}
	public BigDecimal getBdIngCajaAmortizacion() {
		return bdIngCajaAmortizacion;
	}
	public void setBdIngCajaAmortizacion(BigDecimal bdIngCajaAmortizacion) {
		this.bdIngCajaAmortizacion = bdIngCajaAmortizacion;
	}
	public BigDecimal getBdIngCajaInteres() {
		return bdIngCajaInteres;
	}
	public void setBdIngCajaInteres(BigDecimal bdIngCajaInteres) {
		this.bdIngCajaInteres = bdIngCajaInteres;
	}
	public InteresCancelado getUltimoInteresCancelado() {
		return ultimoInteresCancelado;
	}
	public void setUltimoInteresCancelado(InteresCancelado ultimoInteresCancelado) {
		this.ultimoInteresCancelado = ultimoInteresCancelado;
	}
	public Date getDtFechaInicioInteresCancelado() {
		return dtFechaInicioInteresCancelado;
	}
	public void setDtFechaInicioInteresCancelado(Date dtFechaInicioInteresCancelado) {
		this.dtFechaInicioInteresCancelado = dtFechaInicioInteresCancelado;
	}
	public Integer getIntDiasEntreFechasInteresCancelado() {
		return intDiasEntreFechasInteresCancelado;
	}
	public void setIntDiasEntreFechasInteresCancelado(
			Integer intDiasEntreFechasInteresCancelado) {
		this.intDiasEntreFechasInteresCancelado = intDiasEntreFechasInteresCancelado;
	}
	public BigDecimal getBdIngCajaSumCapitalInteres() {
		return bdIngCajaSumCapitalInteres;
	}
	public void setBdIngCajaSumCapitalInteres(BigDecimal bdIngCajaSumCapitalInteres) {
		this.bdIngCajaSumCapitalInteres = bdIngCajaSumCapitalInteres;
	}
	public BigDecimal getBdIngCajaMontoPagado() {
		return bdIngCajaMontoPagado;
	}
	public void setBdIngCajaMontoPagado(BigDecimal bdIngCajaMontoPagado) {
		this.bdIngCajaMontoPagado = bdIngCajaMontoPagado;
	}
	public BigDecimal getBdIngCajaPorcentajeInteres() {
		return bdIngCajaPorcentajeInteres;
	}
	public void setBdIngCajaPorcentajeInteres(BigDecimal bdIngCajaPorcentajeInteres) {
		this.bdIngCajaPorcentajeInteres = bdIngCajaPorcentajeInteres;
	}
	public Integer getIntIngCajaOrdenAmortizacion() {
		return intIngCajaOrdenAmortizacion;
	}
	public void setIntIngCajaOrdenAmortizacion(Integer intIngCajaOrdenAmortizacion) {
		this.intIngCajaOrdenAmortizacion = intIngCajaOrdenAmortizacion;
	}
	public Integer getIntIngCajaOrdenInteres() {
		return intIngCajaOrdenInteres;
	}
	public void setIntIngCajaOrdenInteres(Integer intIngCajaOrdenInteres) {
		this.intIngCajaOrdenInteres = intIngCajaOrdenInteres;
	}
	public BigDecimal getBdIngCajaDiferencia() {
		return bdIngCajaDiferencia;
	}
	public void setBdIngCajaDiferencia(BigDecimal bdIngCajaDiferencia) {
		this.bdIngCajaDiferencia = bdIngCajaDiferencia;
	}
	public Integer getIntIngCajaFlagAmortizacionInteres() {
		return intIngCajaFlagAmortizacionInteres;
	}
	public void setIntIngCajaFlagAmortizacionInteres(
			Integer intIngCajaFlagAmortizacionInteres) {
		this.intIngCajaFlagAmortizacionInteres = intIngCajaFlagAmortizacionInteres;
	}
	public String getStrIngCajaSocioSucursalAdministra() {
		return strIngCajaSocioSucursalAdministra;
	}
	public void setStrIngCajaSocioSucursalAdministra(
			String strIngCajaSocioSucursalAdministra) {
		this.strIngCajaSocioSucursalAdministra = strIngCajaSocioSucursalAdministra;
	}
	public String getStrIngCajaDescTipoConceptoGeneral() {
		return strIngCajaDescTipoConceptoGeneral;
	}
	public void setStrIngCajaDescTipoConceptoGeneral(
			String strIngCajaDescTipoConceptoGeneral) {
		this.strIngCajaDescTipoConceptoGeneral = strIngCajaDescTipoConceptoGeneral;
	}
	public Integer getIntIngCajaIdSucursalAdministra() {
		return intIngCajaIdSucursalAdministra;
	}
	public void setIntIngCajaIdSucursalAdministra(
			Integer intIngCajaIdSucursalAdministra) {
		this.intIngCajaIdSucursalAdministra = intIngCajaIdSucursalAdministra;
	}
	public Integer getIntIngCajaIdSubSucursalAdministra() {
		return intIngCajaIdSubSucursalAdministra;
	}
	public void setIntIngCajaIdSubSucursalAdministra(
			Integer intIngCajaIdSubSucursalAdministra) {
		this.intIngCajaIdSubSucursalAdministra = intIngCajaIdSubSucursalAdministra;
	}
	public Integer getIntIngCajaParaTipoPagoCuenta() {
		return intIngCajaParaTipoPagoCuenta;
	}
	public void setIntIngCajaParaTipoPagoCuenta(Integer intIngCajaParaTipoPagoCuenta) {
		this.intIngCajaParaTipoPagoCuenta = intIngCajaParaTipoPagoCuenta;
	}
	public ConceptoPago getIngCajaConceptoPago() {
		return ingCajaConceptoPago;
	}
	public void setIngCajaConceptoPago(ConceptoPago ingCajaConceptoPago) {
		this.ingCajaConceptoPago = ingCajaConceptoPago;
	}
	public CuentaConceptoDetalle getIngCajaCuentaConceptoDetalle() {
		return ingCajaCuentaConceptoDetalle;
	}
	public void setIngCajaCuentaConceptoDetalle(
			CuentaConceptoDetalle ingCajaCuentaConceptoDetalle) {
		this.ingCajaCuentaConceptoDetalle = ingCajaCuentaConceptoDetalle;
	}
}
