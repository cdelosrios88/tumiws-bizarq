package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;

public class ControlFondosFijos extends TumiDomain{

	private	ControlFondosFijosId	id;
	private	Integer		intSudeIdSubsucursal;
	private	Integer		intPersPersonaResponsable;
	private	BigDecimal	bdMontoGirado;
	private	BigDecimal	bdMontoApertura;
	private	BigDecimal	bdMontoUtilizado;
	private	BigDecimal	bdMontoSaldo;
	private	Integer		intEstadoFondo;
	private	Timestamp	tsFechaRegistro;
	private	Integer		intPersEmpresaUsuario;
	private	Integer		intPersPersonaUsuario;
	private	Timestamp	tsFechaCierre;
	private	Integer		intPersEmpresaCierre;
	private	Integer		intPersPersonaCierre;
	private	Integer		intPersEmpresaEgreso;
	private	Integer		intEgresoGeneral;	

	private	Egreso		egreso;
	private	Sucursal	sucursal;
	private	Subsucursal	subsucursal;
	private	String		strDescripcionPersona;
	//private	PlanCuenta	planCuenta;
	private	Acceso		acceso;
	private	String		strNumeroApertura;
	private	ControlFondosFijos	anterior;
	private	Integer		intItemFiltro;
	private	String		strDescripcion;
	private	String		strMontoLetras;
	private	Bancofondo	bancoFondo;
	
	//Moneda en la que esta el CFF
	private Integer		intParaMoneda;
	
	//Agregado 06.12.2013 JCHAVEZ
	private Integer 	intOrden;
	private String 		strDescTipoFondoFijo;
	private String 		strDescSucursal;
	private String 		strDescSubSucursal;
	private String 		strDescEstadoFondo;
	private String 		strFechaRegistro;
	private String 		strFechaCierre;
	private String 		strMovimientoAnterior;
	//Agregado 11.12.2013 JCHAVEZ
	private Integer 	intDocumentoGeneral;
	
	public ControlFondosFijos(){
		id = new ControlFondosFijosId();
		egreso = new Egreso();
	}
	
	public ControlFondosFijosId getId() {
		return id;
	}
	public void setId(ControlFondosFijosId id) {
		this.id = id;
	}
	public Integer getIntSudeIdSubsucursal() {
		return intSudeIdSubsucursal;
	}
	public void setIntSudeIdSubsucursal(Integer intSudeIdSubsucursal) {
		this.intSudeIdSubsucursal = intSudeIdSubsucursal;
	}
	public Integer getIntPersPersonaResponsable() {
		return intPersPersonaResponsable;
	}
	public void setIntPersPersonaResponsable(Integer intPersPersonaResponsable) {
		this.intPersPersonaResponsable = intPersPersonaResponsable;
	}
	public BigDecimal getBdMontoGirado() {
		return bdMontoGirado;
	}
	public void setBdMontoGirado(BigDecimal bdMontoGirado) {
		this.bdMontoGirado = bdMontoGirado;
	}
	public BigDecimal getBdMontoApertura() {
		return bdMontoApertura;
	}
	public void setBdMontoApertura(BigDecimal bdMontoApertura) {
		this.bdMontoApertura = bdMontoApertura;
	}
	public BigDecimal getBdMontoUtilizado() {
		return bdMontoUtilizado;
	}
	public void setBdMontoUtilizado(BigDecimal bdMontoUtilizado) {
		this.bdMontoUtilizado = bdMontoUtilizado;
	}
	public BigDecimal getBdMontoSaldo() {
		return bdMontoSaldo;
	}
	public void setBdMontoSaldo(BigDecimal bdMontoSaldo) {
		this.bdMontoSaldo = bdMontoSaldo;
	}
	public Integer getIntEstadoFondo() {
		return intEstadoFondo;
	}
	public void setIntEstadoFondo(Integer intEstadoFondo) {
		this.intEstadoFondo = intEstadoFondo;
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
	public Timestamp getTsFechaCierre() {
		return tsFechaCierre;
	}
	public void setTsFechaCierre(Timestamp tsFechaCierre) {
		this.tsFechaCierre = tsFechaCierre;
	}
	public Integer getIntPersEmpresaCierre() {
		return intPersEmpresaCierre;
	}
	public void setIntPersEmpresaCierre(Integer intPersEmpresaCierre) {
		this.intPersEmpresaCierre = intPersEmpresaCierre;
	}
	public Integer getIntPersPersonaCierre() {
		return intPersPersonaCierre;
	}
	public void setIntPersPersonaCierre(Integer intPersPersonaCierre) {
		this.intPersPersonaCierre = intPersPersonaCierre;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntEgresoGeneral() {
		return intEgresoGeneral;
	}
	public void setIntEgresoGeneral(Integer intEgresoGeneral) {
		this.intEgresoGeneral = intEgresoGeneral;
	}	
	public Egreso getEgreso() {
		return egreso;
	}
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Subsucursal getSubsucursal() {
		return subsucursal;
	}
	public void setSubsucursal(Subsucursal subsucursal) {
		this.subsucursal = subsucursal;
	}
	public String getStrDescripcionPersona() {
		return strDescripcionPersona;
	}
	public void setStrDescripcionPersona(String strDescripcionPersona) {
		this.strDescripcionPersona = strDescripcionPersona;
	}
	/*public PlanCuenta getPlanCuenta() {
		return planCuenta;
	}
	public void setPlanCuenta(PlanCuenta planCuenta) {
		this.planCuenta = planCuenta;
	}*/
	public Acceso getAcceso() {
		return acceso;
	}
	public void setAcceso(Acceso acceso) {
		this.acceso = acceso;
	}
	public String getStrNumeroApertura() {
		return strNumeroApertura;
	}
	public void setStrNumeroApertura(String strNumeroApertura) {
		this.strNumeroApertura = strNumeroApertura;
	}
	public ControlFondosFijos getAnterior() {
		return anterior;
	}
	public void setAnterior(ControlFondosFijos anterior) {
		this.anterior = anterior;
	}
	public Integer getIntItemFiltro() {
		return intItemFiltro;
	}
	public void setIntItemFiltro(Integer intItemFiltro) {
		this.intItemFiltro = intItemFiltro;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public String getStrMontoLetras() {
		return strMontoLetras;
	}
	public void setStrMontoLetras(String strMontoLetras) {
		this.strMontoLetras = strMontoLetras;
	}
	public Integer getIntParaMoneda() {
		return intParaMoneda;
	}
	public void setIntParaMoneda(Integer intParaMoneda) {
		this.intParaMoneda = intParaMoneda;
	}
	public Bancofondo getBancoFondo() {
		return bancoFondo;
	}
	public void setBancoFondo(Bancofondo bancoFondo) {
		this.bancoFondo = bancoFondo;
	}

	@Override
	public String toString() {
		return "ControlFondosFijos [id=" + id + ", intSudeIdSubsucursal="
				+ intSudeIdSubsucursal + ", intPersPersonaResponsable="
				+ intPersPersonaResponsable + ", bdMontoGirado="
				+ bdMontoGirado + ", bdMontoApertura=" + bdMontoApertura
				+ ", bdMontoUtilizado=" + bdMontoUtilizado + ", bdMontoSaldo="
				+ bdMontoSaldo + ", intEstadoFondo=" + intEstadoFondo
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", tsFechaCierre=" + tsFechaCierre
				+ ", intPersEmpresaCierre=" + intPersEmpresaCierre
				+ ", intPersPersonaCierre=" + intPersPersonaCierre
				+ ", intPersEmpresaEgreso=" + intPersEmpresaEgreso
				+ ", intEgresoGeneral=" + intEgresoGeneral + "]";
	}

	public Integer getIntOrden() {
		return intOrden;
	}

	public void setIntOrden(Integer intOrden) {
		this.intOrden = intOrden;
	}

	public String getStrDescTipoFondoFijo() {
		return strDescTipoFondoFijo;
	}

	public void setStrDescTipoFondoFijo(String strDescTipoFondoFijo) {
		this.strDescTipoFondoFijo = strDescTipoFondoFijo;
	}

	public String getStrDescSucursal() {
		return strDescSucursal;
	}

	public void setStrDescSucursal(String strDescSucursal) {
		this.strDescSucursal = strDescSucursal;
	}

	public String getStrDescSubSucursal() {
		return strDescSubSucursal;
	}

	public void setStrDescSubSucursal(String strDescSubSucursal) {
		this.strDescSubSucursal = strDescSubSucursal;
	}

	public String getStrDescEstadoFondo() {
		return strDescEstadoFondo;
	}

	public void setStrDescEstadoFondo(String strDescEstadoFondo) {
		this.strDescEstadoFondo = strDescEstadoFondo;
	}

	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}

	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}

	public String getStrFechaCierre() {
		return strFechaCierre;
	}

	public void setStrFechaCierre(String strFechaCierre) {
		this.strFechaCierre = strFechaCierre;
	}

	public String getStrMovimientoAnterior() {
		return strMovimientoAnterior;
	}

	public void setStrMovimientoAnterior(String strMovimientoAnterior) {
		this.strMovimientoAnterior = strMovimientoAnterior;
	}

	public Integer getIntDocumentoGeneral() {
		return intDocumentoGeneral;
	}

	public void setIntDocumentoGeneral(Integer intDocumentoGeneral) {
		this.intDocumentoGeneral = intDocumentoGeneral;
	}	
}