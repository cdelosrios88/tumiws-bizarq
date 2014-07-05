package pe.com.tumi.movimiento.concepto.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Movimiento extends TumiDomain {

	private Integer intItemMovimiento;
	private Timestamp	tsFechaMovimiento;
	private Integer intPersEmpresa;
	private Integer intCuenta;
	private Integer intPersPersonaIntegrante;
	private Integer intItemCuentaConcepto;
	private Integer intItemExpediente;
	private Integer intItemExpedienteDetalle;
	private Integer intParaTipoConceptoGeneral;
	private Integer intParaTipoMovimiento;
	private Integer intParaDocumentoGeneral;
	private String	strSerieDocumento;
	private String	strNumeroDocumento;
	private Integer intParaTipoCargoAbono;
	private BigDecimal	bdMontoMovimiento ;
	private BigDecimal 	bdMontoSaldo;
	private Integer	intPeriodoPlanilla;
	private Integer	intPersEmpresaUsuario;
	private Integer	intPersPersonaUsuario;
	private Integer intIndicadorAnulado;
	private Integer intItemMovimientoAnulado;
	
	//JCHAVEZ 07.01.2014
	private Integer		intItemEgresoGeneral;
	private Integer		intPersEmpresaEgreso;
	//rVillarreal 27.05.2014
	private Timestamp tsFechaMovimientoMax;
	
	private Date dtFechaInicio;
	private Date dtFechaFin;
	
	// Nuevas tablas 
	private List<ConceptoDetallePago> listaConceptoDetallePago;
	//Nuevo campo jchavez 16.05.2014
	private Integer intItemMovimientoRel;
	
	public Integer getIntItemMovimiento() {
		return intItemMovimiento;
	}
	public void setIntItemMovimiento(Integer intItemMovimiento) {
		this.intItemMovimiento = intItemMovimiento;
	}
	public Timestamp getTsFechaMovimiento() {
		return tsFechaMovimiento;
	}
	public void setTsFechaMovimiento(Timestamp tsFechaMovimiento) {
		this.tsFechaMovimiento = tsFechaMovimiento;
	}
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntCuenta() {
		return intCuenta;
	}
	public void setIntCuenta(Integer intCuenta) {
		this.intCuenta = intCuenta;
	}
	public Integer getIntPersPersonaIntegrante() {
		return intPersPersonaIntegrante;
	}
	public void setIntPersPersonaIntegrante(Integer intPersPersonaIntegrante) {
		this.intPersPersonaIntegrante = intPersPersonaIntegrante;
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
	public Integer getIntParaTipoConceptoGeneral() {
		return intParaTipoConceptoGeneral;
	}
	public void setIntParaTipoConceptoGeneral(Integer intParaTipoConceptoGeneral) {
		this.intParaTipoConceptoGeneral = intParaTipoConceptoGeneral;
	}
	public Integer getIntParaTipoMovimiento() {
		return intParaTipoMovimiento;
	}
	public void setIntParaTipoMovimiento(Integer intParaTipoMovimiento) {
		this.intParaTipoMovimiento = intParaTipoMovimiento;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
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
	public Integer getIntParaTipoCargoAbono() {
		return intParaTipoCargoAbono;
	}
	public void setIntParaTipoCargoAbono(Integer intParaTipoCargoAbono) {
		this.intParaTipoCargoAbono = intParaTipoCargoAbono;
	}
	public BigDecimal getBdMontoMovimiento() {
		return bdMontoMovimiento;
	}
	public void setBdMontoMovimiento(BigDecimal bdMontoMovimiento) {
		this.bdMontoMovimiento = bdMontoMovimiento;
	}
	public BigDecimal getBdMontoSaldo() {
		return bdMontoSaldo;
	}
	public void setBdMontoSaldo(BigDecimal bdMontoSaldo) {
		this.bdMontoSaldo = bdMontoSaldo;
	}
	public Integer getIntPeriodoPlanilla() {
		return intPeriodoPlanilla;
	}
	public void setIntPeriodoPlanilla(Integer intPeriodoPlanilla) {
		this.intPeriodoPlanilla = intPeriodoPlanilla;
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
	public Integer getIntIndicadorAnulado() {
		return intIndicadorAnulado;
	}
	public void setIntIndicadorAnulado(Integer intIndicadorAnulado) {
		this.intIndicadorAnulado = intIndicadorAnulado;
	}
	public Integer getIntItemMovimientoAnulado() {
		return intItemMovimientoAnulado;
	}
	public void setIntItemMovimientoAnulado(Integer intItemMovimientoAnulado) {
		this.intItemMovimientoAnulado = intItemMovimientoAnulado;
	}

	public Date getDtFechaInicio() {
		return dtFechaInicio;
	}
	public void setDtFechaInicio(Date dtFechaInicio) {
		this.dtFechaInicio = dtFechaInicio;
	}
	public Date getDtFechaFin() {
		return dtFechaFin;
	}
	public void setDtFechaFin(Date dtFechaFin) {
		this.dtFechaFin = dtFechaFin;
	}
	public List<ConceptoDetallePago> getListaConceptoDetallePago() {
		return listaConceptoDetallePago;
	}
	public void setListaConceptoDetallePago(
			List<ConceptoDetallePago> listaConceptoDetallePago) {
		this.listaConceptoDetallePago = listaConceptoDetallePago;
	}
	@Override
	public String toString() {
		return "Movimiento [intItemMovimiento=" + intItemMovimiento
				+ ", tsFechaMovimiento=" + tsFechaMovimiento
				+ ", intPersEmpresa=" + intPersEmpresa + ", intCuenta="
				+ intCuenta + ", intPersPersonaIntegrante="
				+ intPersPersonaIntegrante + ", intItemCuentaConcepto="
				+ intItemCuentaConcepto + ", intItemExpediente="
				+ intItemExpediente + ", intItemExpedienteDetalle="
				+ intItemExpedienteDetalle + ", intParaTipoConceptoGeneral="
				+ intParaTipoConceptoGeneral + ", intParaTipoMovimiento="
				+ intParaTipoMovimiento + ", intParaDocumentoGeneral="
				+ intParaDocumentoGeneral + ", strSerieDocumento="
				+ strSerieDocumento + ", strNumeroDocumento="
				+ strNumeroDocumento + ", intParaTipoCargoAbono="
				+ intParaTipoCargoAbono + ", bdMontoMovimiento="
				+ bdMontoMovimiento + ", bdMontoSaldo=" + bdMontoSaldo
				+ ", intPeriodoPlanilla=" + intPeriodoPlanilla
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", intIndicadorAnulado=" + intIndicadorAnulado
				+ ", intItemMovimientoAnulado=" + intItemMovimientoAnulado
				+ "]";
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemMovimientoRel() {
		return intItemMovimientoRel;
	}
	public void setIntItemMovimientoRel(Integer intItemMovimientoRel) {
		this.intItemMovimientoRel = intItemMovimientoRel;
	}
	public Timestamp getTsFechaMovimientoMax() {
		return tsFechaMovimientoMax;
	}
	public void setTsFechaMovimientoMax(Timestamp tsFechaMovimientoMax) {
		this.tsFechaMovimientoMax = tsFechaMovimientoMax;
	}
	
	
}
