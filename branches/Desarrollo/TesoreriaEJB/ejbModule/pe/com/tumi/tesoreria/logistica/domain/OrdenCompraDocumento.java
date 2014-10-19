package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class OrdenCompraDocumento extends TumiDomain{

	private OrdenCompraDocumentoId id;
	private Integer intParaDocumentoGeneral;
	private Timestamp tsFechaDocumento;
	private Integer intParaTipoCancelacion;
	private BigDecimal bdMontoDocumento;
	private Integer intParaTipoMoneda;
	private Integer intSucuIdSucursal;
	private Integer intSudeIdSubsucursal;
	private Integer intIdArea;
	private String 	strObservacion;
	private Integer intPersEmpresaUsuario;
	private Integer intPersPersonaUsuario;
	private Timestamp tsFechaAnula;
	private Integer intPersEmpresaAnula;
	private Integer intPersPersonaAnula;
	private BigDecimal bdMontoPagado;
	private BigDecimal bdMontoIngresado;
	private Integer	intParaEstado;
	
	private Sucursal	sucursal;
	private Area		area;
	private BigDecimal	bdMontoUsar;
	
	private BigDecimal 	bdMontoPagadoTemp;
	private BigDecimal 	bdMontoIngresadoTemp;
	private BigDecimal	bdMontoUsarTotal;
	private BigDecimal	bdMontoUsarDocumento;
	
	//Autor: jchavez / Tarea: Creación de nuevos campos / Fecha: 04.10.2014
	private Timestamp tsFechaRegistro;
	private Integer intPersEmpresaEgreso;
	private Integer intItemEgresoGeneral;
	private Integer intParaEstadoPago;
	private Integer intPersEmpresaIngreso;
	private Integer intItemIngresoGeneral;
	private BigDecimal bdMontoSaldo;
	//Fin jchavez - 04.10.2014
	
	public OrdenCompraDocumento(){
		id = new OrdenCompraDocumentoId();
	}
	
	public OrdenCompraDocumentoId getId() {
		return id;
	}
	public void setId(OrdenCompraDocumentoId id) {
		this.id = id;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Timestamp getTsFechaDocumento() {
		return tsFechaDocumento;
	}
	public void setTsFechaDocumento(Timestamp tsFechaDocumento) {
		this.tsFechaDocumento = tsFechaDocumento;
	}
	public Integer getIntParaTipoCancelacion() {
		return intParaTipoCancelacion;
	}
	public void setIntParaTipoCancelacion(Integer intParaTipoCancelacion) {
		this.intParaTipoCancelacion = intParaTipoCancelacion;
	}
	public BigDecimal getBdMontoDocumento() {
		return bdMontoDocumento;
	}
	public void setBdMontoDocumento(BigDecimal bdMontoDocumento) {
		this.bdMontoDocumento = bdMontoDocumento;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSudeIdSubsucursal() {
		return intSudeIdSubsucursal;
	}
	public void setIntSudeIdSubsucursal(Integer intSudeIdSubsucursal) {
		this.intSudeIdSubsucursal = intSudeIdSubsucursal;
	}
	public Integer getIntIdArea() {
		return intIdArea;
	}
	public void setIntIdArea(Integer intIdArea) {
		this.intIdArea = intIdArea;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
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
	public Timestamp getTsFechaAnula() {
		return tsFechaAnula;
	}
	public void setTsFechaAnula(Timestamp tsFechaAnula) {
		this.tsFechaAnula = tsFechaAnula;
	}
	public Integer getIntPersEmpresaAnula() {
		return intPersEmpresaAnula;
	}
	public void setIntPersEmpresaAnula(Integer intPersEmpresaAnula) {
		this.intPersEmpresaAnula = intPersEmpresaAnula;
	}
	public Integer getIntPersPersonaAnula() {
		return intPersPersonaAnula;
	}
	public void setIntPersPersonaAnula(Integer intPersPersonaAnula) {
		this.intPersPersonaAnula = intPersPersonaAnula;
	}
	public BigDecimal getBdMontoPagado() {
		return bdMontoPagado;
	}
	public void setBdMontoPagado(BigDecimal bdMontoPagado) {
		this.bdMontoPagado = bdMontoPagado;
	}
	public BigDecimal getBdMontoIngresado() {
		return bdMontoIngresado;
	}
	public void setBdMontoIngresado(BigDecimal bdMontoIngresado) {
		this.bdMontoIngresado = bdMontoIngresado;
	}	
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public BigDecimal getBdMontoUsar() {
		return bdMontoUsar;
	}
	public void setBdMontoUsar(BigDecimal bdMontoUsar) {
		this.bdMontoUsar = bdMontoUsar;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public BigDecimal getBdMontoPagadoTemp() {
		return bdMontoPagadoTemp;
	}
	public void setBdMontoPagadoTemp(BigDecimal bdMontoPagadoTemp) {
		this.bdMontoPagadoTemp = bdMontoPagadoTemp;
	}
	public BigDecimal getBdMontoIngresadoTemp() {
		return bdMontoIngresadoTemp;
	}
	public void setBdMontoIngresadoTemp(BigDecimal bdMontoIngresadoTemp) {
		this.bdMontoIngresadoTemp = bdMontoIngresadoTemp;
	}
	public BigDecimal getBdMontoUsarTotal() {
		return bdMontoUsarTotal;
	}
	public void setBdMontoUsarTotal(BigDecimal bdMontoUsarTotal) {
		this.bdMontoUsarTotal = bdMontoUsarTotal;
	}
	public BigDecimal getBdMontoUsarDocumento() {
		return bdMontoUsarDocumento;
	}
	public void setBdMontoUsarDocumento(BigDecimal bdMontoUsarDocumento) {
		this.bdMontoUsarDocumento = bdMontoUsarDocumento;
	}

	@Override
	public String toString() {
		return "OrdenCompraDocumento [id=" + id + ", intParaDocumentoGeneral="
				+ intParaDocumentoGeneral + ", tsFechaDocumento="
				+ tsFechaDocumento + ", intParaTipoCancelacion="
				+ intParaTipoCancelacion + ", bdMontoDocumento="
				+ bdMontoDocumento + ", intParaTipoMoneda=" + intParaTipoMoneda
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdSubsucursal=" + intSudeIdSubsucursal
				+ ", intIdArea=" + intIdArea + ", strObservacion="
				+ strObservacion + ", intPersEmpresaUsuario="
				+ intPersEmpresaUsuario + ", intPersPersonaUsuario="
				+ intPersPersonaUsuario + ", tsFechaAnula=" + tsFechaAnula
				+ ", intPersEmpresaAnula=" + intPersEmpresaAnula
				+ ", intPersPersonaAnula=" + intPersPersonaAnula
				+ ", bdMontoPagado=" + bdMontoPagado
				+ ", bdMontoIngresado=" + bdMontoIngresado + "]";
	}
	//Autor: jchavez / Tarea: Creación de nuevos campos / Fecha: 04.10.2014
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public Integer getIntParaEstadoPago() {
		return intParaEstadoPago;
	}
	public void setIntParaEstadoPago(Integer intParaEstadoPago) {
		this.intParaEstadoPago = intParaEstadoPago;
	}
	public Integer getIntPersEmpresaIngreso() {
		return intPersEmpresaIngreso;
	}
	public void setIntPersEmpresaIngreso(Integer intPersEmpresaIngreso) {
		this.intPersEmpresaIngreso = intPersEmpresaIngreso;
	}
	public Integer getIntItemIngresoGeneral() {
		return intItemIngresoGeneral;
	}
	public void setIntItemIngresoGeneral(Integer intItemIngresoGeneral) {
		this.intItemIngresoGeneral = intItemIngresoGeneral;
	}
	public BigDecimal getBdMontoSaldo() {
		return bdMontoSaldo;
	}
	public void setBdMontoSaldo(BigDecimal bdMontoSaldo) {
		this.bdMontoSaldo = bdMontoSaldo;
	}
	//Fin jchavez - 04.10.2014
}
