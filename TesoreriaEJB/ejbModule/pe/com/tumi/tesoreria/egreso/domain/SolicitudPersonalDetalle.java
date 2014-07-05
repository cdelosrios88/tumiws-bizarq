package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;

import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class SolicitudPersonalDetalle extends TumiDomain{
	
	private SolicitudPersonalDetalleId id;
	private Integer intParaDocumentoGeneral;
	private Integer intPersEmpresaAbonado;
	private Integer intPersPersonaAbonado;
	private BigDecimal bdMonto;
	private Integer intSucuIdSucursal;
	private Integer intSudeIdSubsucursal;
	private Integer intIdArea;
	private Integer intPersEmpresaCuenta;
	private Integer intContPeriodoCuenta;
	private String 	strContNumeroCuenta;
	private Integer intParaOpcionDebeHaber;
	private Integer intNumeroDocumento;	
	
	private Persona persona;
	private Sucursal sucursal;
	private Subsucursal subsucursal;
	private Area	area;
	private PlanCuenta planCuenta;
	
	public SolicitudPersonalDetalle(){
		id = new SolicitudPersonalDetalleId();
	}
	
	public SolicitudPersonalDetalleId getId() {
		return id;
	}
	public void setId(SolicitudPersonalDetalleId id) {
		this.id = id;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Integer getIntPersEmpresaAbonado() {
		return intPersEmpresaAbonado;
	}
	public void setIntPersEmpresaAbonado(Integer intPersEmpresaAbonado) {
		this.intPersEmpresaAbonado = intPersEmpresaAbonado;
	}
	public Integer getIntPersPersonaAbonado() {
		return intPersPersonaAbonado;
	}
	public void setIntPersPersonaAbonado(Integer intPersPersonaAbonado) {
		this.intPersPersonaAbonado = intPersPersonaAbonado;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
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
	public Integer getIntParaOpcionDebeHaber() {
		return intParaOpcionDebeHaber;
	}
	public void setIntParaOpcionDebeHaber(Integer intParaOpcionDebeHaber) {
		this.intParaOpcionDebeHaber = intParaOpcionDebeHaber;
	}
	public Integer getIntNumeroDocumento() {
		return intNumeroDocumento;
	}
	public void setIntNumeroDocumento(Integer intNumeroDocumento) {
		this.intNumeroDocumento = intNumeroDocumento;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
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
	public Subsucursal getSubsucursal() {
		return subsucursal;
	}
	public void setSubsucursal(Subsucursal subsucursal) {
		this.subsucursal = subsucursal;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "SolicitudPersonalDetalle [id=" + id
				+ ", intParaDocumentoGeneral=" + intParaDocumentoGeneral
				+ ", intPersEmpresaAbonado=" + intPersEmpresaAbonado
				+ ", intPersPersonaAbonado=" + intPersPersonaAbonado
				+ ", bdMonto=" + bdMonto + ", intSucuIdSucursal="
				+ intSucuIdSucursal + ", intSudeIdSubsucursal="
				+ intSudeIdSubsucursal + ", intIdArea=" + intIdArea
				+ ", intPersEmpresaCuenta=" + intPersEmpresaCuenta
				+ ", intContPeriodoCuenta=" + intContPeriodoCuenta
				+ ", strContNumeroCuenta=" + strContNumeroCuenta
				+ ", intParaOpcionDebeHaber=" + intParaOpcionDebeHaber
				+ ", intNumeroDocumento=" + intNumeroDocumento + "]";
	}
}