package pe.com.tumi.tesoreria.ingreso.domain;

import java.math.BigDecimal;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class IngresoDetalleInterfaz extends TumiDomain{

	private Integer intDocumentoGeneral;
	private String	strNroDocumento;
	private Persona	persona;
	private Sucursal	sucursal;
	private	Subsucursal	subsucursal;
	private	String	strDescripcion;
	private	BigDecimal	bdSubtotal;
	private BigDecimal	bdMonto;
	
	private	Integer	intOrden;
	
	private LibroDiario libroDiario;
	
	//Agregado 09.12.2013 JCHAVEZ
	private String strDescSucursal;
	public Integer getIntDocumentoGeneral() {
		return intDocumentoGeneral;
	}
	public void setIntDocumentoGeneral(Integer intDocumentoGeneral) {
		this.intDocumentoGeneral = intDocumentoGeneral;
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
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public BigDecimal getBdSubtotal() {
		return bdSubtotal;
	}
	public void setBdSubtotal(BigDecimal bdSubtotal) {
		this.bdSubtotal = bdSubtotal;
	}
	public Integer getIntOrden() {
		return intOrden;
	}
	public void setIntOrden(Integer intOrden) {
		this.intOrden = intOrden;
	}
	public Subsucursal getSubsucursal() {
		return subsucursal;
	}
	public void setSubsucursal(Subsucursal subsucursal) {
		this.subsucursal = subsucursal;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public String getStrNroDocumento() {
		return strNroDocumento;
	}
	public void setStrNroDocumento(String strNroDocumento) {
		this.strNroDocumento = strNroDocumento;
	}
	public LibroDiario getLibroDiario() {
		return libroDiario;
	}
	public void setLibroDiario(LibroDiario libroDiario) {
		this.libroDiario = libroDiario;
	}
	public String getStrDescSucursal() {
		return strDescSucursal;
	}
	public void setStrDescSucursal(String strDescSucursal) {
		this.strDescSucursal = strDescSucursal;
	}
}