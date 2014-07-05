package pe.com.tumi.presupuesto.core.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Presupuesto extends TumiDomain{

	private PresupuestoId id;
	private BigDecimal bdMontoProyecSoles;
	private BigDecimal bdMontoProyecExtranjero;
	private BigDecimal bdMontoEjecSoles;
	private BigDecimal bdMontoEjecExtranjero;
	private Timestamp tsFechaRegistro;
	private Integer intParaEstadoCierre;
	private Integer intEmpresaUsuarioPk;
 	private Integer intPersonaUsuarioPk;
 	
 	//JCHAVEZ 21.10.2013
 	private String strDescripcionCuenta;
 	private String strDescripcionSubSucursal;
 	private String strEstadoCierre;
 	
 	//JCHAVEZ 28.10.2013

 	
	public PresupuestoId getId() {
		return id;
	}
	public void setId(PresupuestoId id) {
		this.id = id;
	}
	public BigDecimal getBdMontoProyecSoles() {
		return bdMontoProyecSoles;
	}
	public void setBdMontoProyecSoles(BigDecimal bdMontoProyecSoles) {
		this.bdMontoProyecSoles = bdMontoProyecSoles;
	}
	public BigDecimal getBdMontoProyecExtranjero() {
		return bdMontoProyecExtranjero;
	}
	public void setBdMontoProyecExtranjero(BigDecimal bdMontoProyecExtranjero) {
		this.bdMontoProyecExtranjero = bdMontoProyecExtranjero;
	}
	public BigDecimal getBdMontoEjecSoles() {
		return bdMontoEjecSoles;
	}
	public void setBdMontoEjecSoles(BigDecimal bdMontoEjecSoles) {
		this.bdMontoEjecSoles = bdMontoEjecSoles;
	}
	public BigDecimal getBdMontoEjecExtranjero() {
		return bdMontoEjecExtranjero;
	}
	public void setBdMontoEjecExtranjero(BigDecimal bdMontoEjecExtranjero) {
		this.bdMontoEjecExtranjero = bdMontoEjecExtranjero;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntParaEstadoCierre() {
		return intParaEstadoCierre;
	}
	public void setIntParaEstadoCierre(Integer intParaEstadoCierre) {
		this.intParaEstadoCierre = intParaEstadoCierre;
	}
	public Integer getIntEmpresaUsuarioPk() {
		return intEmpresaUsuarioPk;
	}
	public void setIntEmpresaUsuarioPk(Integer intEmpresaUsuarioPk) {
		this.intEmpresaUsuarioPk = intEmpresaUsuarioPk;
	}
	public Integer getIntPersonaUsuarioPk() {
		return intPersonaUsuarioPk;
	}
	public void setIntPersonaUsuarioPk(Integer intPersonaUsuarioPk) {
		this.intPersonaUsuarioPk = intPersonaUsuarioPk;
	}
	public String getStrDescripcionCuenta() {
		return strDescripcionCuenta;
	}
	public void setStrDescripcionCuenta(String strDescripcionCuenta) {
		this.strDescripcionCuenta = strDescripcionCuenta;
	}
	public String getStrDescripcionSubSucursal() {
		return strDescripcionSubSucursal;
	}
	public void setStrDescripcionSubSucursal(String strDescripcionSubSucursal) {
		this.strDescripcionSubSucursal = strDescripcionSubSucursal;
	}
	public String getStrEstadoCierre() {
		return strEstadoCierre;
	}
	public void setStrEstadoCierre(String strEstadoCierre) {
		this.strEstadoCierre = strEstadoCierre;
	}
}
