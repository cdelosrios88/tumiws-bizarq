package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class OrdenCompraDetalle extends TumiDomain{

	private OrdenCompraDetalleId	id;
	private BigDecimal	bdCantidad;
	private Integer	intParaUnidadMedida;
	private Integer	intAfectoIGV;
	private Integer	intParaTipoMoneda;
	private BigDecimal	bdPrecioUnitario;
	private BigDecimal	bdPrecioTotal;
	private BigDecimal	bdMontoSaldo;
	private String	strDescripcion;
	private Integer	intSucuIdSucursal;
	private Integer	intSudeIdSubsucursal;
	private Integer	intIdArea;
	private String	strObservacion;
	private Integer	intParaEstado;
	private Integer	intPersEmpresaUsuario;
	private Integer	intPersPersonaUsuario;
	private Timestamp	tsFechaAnula;
	private Integer	intPersEmpresaAnula;
	private Integer	intPersPersonaAnula;
	private Integer	intPersEmpresaCuenta;
	private Integer	intContPeriodoCuenta;
	private String	strContNumeroCuenta;
	
	private Sucursal 	sucursal;
	private Area		area;
	private PlanCuenta	planCuenta;
	private boolean		sePuedeEliminar;
	
	private BigDecimal	bdMontoUsar;
	private BigDecimal	bdMontoSaldoTemp;
	
	public OrdenCompraDetalle(){
		id = new OrdenCompraDetalleId();
	}
	
	public OrdenCompraDetalleId getId() {
		return id;
	}
	public void setId(OrdenCompraDetalleId id) {
		this.id = id;
	}
	public BigDecimal getBdCantidad() {
		return bdCantidad;
	}
	public void setBdCantidad(BigDecimal bdCantidad) {
		this.bdCantidad = bdCantidad;
	}
	public Integer getIntParaUnidadMedida() {
		return intParaUnidadMedida;
	}
	public void setIntParaUnidadMedida(Integer intParaUnidadMedida) {
		this.intParaUnidadMedida = intParaUnidadMedida;
	}
	public Integer getIntAfectoIGV() {
		return intAfectoIGV;
	}
	public void setIntAfectoIGV(Integer intAfectoIGV) {
		this.intAfectoIGV = intAfectoIGV;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public BigDecimal getBdPrecioUnitario() {
		return bdPrecioUnitario;
	}
	public void setBdPrecioUnitario(BigDecimal bdPrecioUnitario) {
		this.bdPrecioUnitario = bdPrecioUnitario;
	}
	public BigDecimal getBdPrecioTotal() {
		return bdPrecioTotal;
	}
	public void setBdPrecioTotal(BigDecimal bdPrecioTotal) {
		this.bdPrecioTotal = bdPrecioTotal;
	}
	public BigDecimal getBdMontoSaldo() {
		return bdMontoSaldo;
	}
	public void setBdMontoSaldo(BigDecimal bdMontoSaldo) {
		this.bdMontoSaldo = bdMontoSaldo;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
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
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public PlanCuenta getPlanCuenta() {
		return planCuenta;
	}
	public void setPlanCuenta(PlanCuenta planCuenta) {
		this.planCuenta = planCuenta;
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
	public boolean isSePuedeEliminar() {
		return sePuedeEliminar;
	}
	public void setSePuedeEliminar(boolean sePuedeEliminar) {
		this.sePuedeEliminar = sePuedeEliminar;
	}
	public BigDecimal getBdMontoSaldoTemp() {
		return bdMontoSaldoTemp;
	}
	public void setBdMontoSaldoTemp(BigDecimal bdMontoSaldoTemp) {
		this.bdMontoSaldoTemp = bdMontoSaldoTemp;
	}

	@Override
	public String toString() {
		return "OrdenCompraDetalle [id=" + id + ", bdCantidad=" + bdCantidad
				+ ", intParaUnidadMedida=" + intParaUnidadMedida
				+ ", intAfectoIGV=" + intAfectoIGV + ", intParaTipoMoneda="
				+ intParaTipoMoneda + ", bdPrecioUnitario=" + bdPrecioUnitario
				+ ", bdPrecioTotal=" + bdPrecioTotal + ", bdMontoSaldo="
				+ bdMontoSaldo + ", strDescripcion=" + strDescripcion
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdSubsucursal=" + intSudeIdSubsucursal
				+ ", intIdArea=" + intIdArea + ", strObservacion="
				+ strObservacion + ", intParaEstado=" + intParaEstado
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", tsFechaAnula=" + tsFechaAnula + ", intPersEmpresaAnula="
				+ intPersEmpresaAnula + ", intPersPersonaAnula="
				+ intPersPersonaAnula + ", intPersEmpresaCuenta="
				+ intPersEmpresaCuenta + ", intContPeriodoCuenta="
				+ intContPeriodoCuenta + ", strContNumeroCuenta="
				+ strContNumeroCuenta + "]";
	}	
}