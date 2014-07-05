package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class CierreDiarioArqueo extends TumiDomain{

	private CierreDiarioArqueoId	id;
	private Timestamp	tsFechaCierreArqueo;
	private Integer 	intPersEmpresaResponsable;
	private Integer 	intPersPersonaResponsable;
	private String 		strObservacion;
	private Timestamp	tsFechaRegistro;
	private Integer 	intParaEstadoCierre;
	private BigDecimal 	bdMontoTotalEfectivo;
	private BigDecimal 	bdMontoTotalFondo;
	private BigDecimal 	bdMontoDiferencia;
	private Integer 	intPersEmpresaCierreAn;
	private Integer 	intPersPersonaCierreAn;
	private String 		strObersvacionAnula;
	private Timestamp 	tsFechaCierreAnula;
	
	private List<CierreDiarioArqueoDetalle> listaCierreDiarioArqueoDetalle;
	private List<CierreDiarioArqueoBillete> listaCierreDiarioArqueoBillete;
	
	private Sucursal	sucursal;
	private Persona		persona;
	
	public CierreDiarioArqueo(){
		id = new CierreDiarioArqueoId();
		listaCierreDiarioArqueoDetalle = new ArrayList<CierreDiarioArqueoDetalle>();
		listaCierreDiarioArqueoBillete = new ArrayList<CierreDiarioArqueoBillete>();
	}

	public CierreDiarioArqueoId getId() {
		return id;
	}
	public void setId(CierreDiarioArqueoId id) {
		this.id = id;
	}
	public Timestamp getTsFechaCierreArqueo() {
		return tsFechaCierreArqueo;
	}
	public void setTsFechaCierreArqueo(Timestamp tsFechaCierreArqueo) {
		this.tsFechaCierreArqueo = tsFechaCierreArqueo;
	}
	public Integer getIntPersEmpresaResponsable() {
		return intPersEmpresaResponsable;
	}
	public void setIntPersEmpresaResponsable(Integer intPersEmpresaResponsable) {
		this.intPersEmpresaResponsable = intPersEmpresaResponsable;
	}
	public Integer getIntPersPersonaResponsable() {
		return intPersPersonaResponsable;
	}
	public void setIntPersPersonaResponsable(Integer intPersPersonaResponsable) {
		this.intPersPersonaResponsable = intPersPersonaResponsable;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
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
	public BigDecimal getBdMontoTotalEfectivo() {
		return bdMontoTotalEfectivo;
	}
	public void setBdMontoTotalEfectivo(BigDecimal bdMontoTotalEfectivo) {
		this.bdMontoTotalEfectivo = bdMontoTotalEfectivo;
	}
	public BigDecimal getBdMontoTotalFondo() {
		return bdMontoTotalFondo;
	}
	public void setBdMontoTotalFondo(BigDecimal bdMontoTotalFondo) {
		this.bdMontoTotalFondo = bdMontoTotalFondo;
	}
	public BigDecimal getBdMontoDiferencia() {
		return bdMontoDiferencia;
	}
	public void setBdMontoDiferencia(BigDecimal bdMontoDiferencia) {
		this.bdMontoDiferencia = bdMontoDiferencia;
	}
	public Integer getIntPersEmpresaCierreAn() {
		return intPersEmpresaCierreAn;
	}
	public void setIntPersEmpresaCierreAn(Integer intPersEmpresaCierreAn) {
		this.intPersEmpresaCierreAn = intPersEmpresaCierreAn;
	}
	public Integer getIntPersPersonaCierreAn() {
		return intPersPersonaCierreAn;
	}
	public void setIntPersPersonaCierreAn(Integer intPersPersonaCierreAn) {
		this.intPersPersonaCierreAn = intPersPersonaCierreAn;
	}
	public String getStrObersvacionAnula() {
		return strObersvacionAnula;
	}
	public void setStrObersvacionAnula(String strObersvacionAnula) {
		this.strObersvacionAnula = strObersvacionAnula;
	}
	public Timestamp getTsFechaCierreAnula() {
		return tsFechaCierreAnula;
	}
	public void setTsFechaCierreAnula(Timestamp tsFechaCierreAnula) {
		this.tsFechaCierreAnula = tsFechaCierreAnula;
	}
	public List<CierreDiarioArqueoDetalle> getListaCierreDiarioArqueoDetalle() {
		return listaCierreDiarioArqueoDetalle;
	}
	public void setListaCierreDiarioArqueoDetalle(List<CierreDiarioArqueoDetalle> listaCierreDiarioArqueoDetalle) {
		this.listaCierreDiarioArqueoDetalle = listaCierreDiarioArqueoDetalle;
	}
	public List<CierreDiarioArqueoBillete> getListaCierreDiarioArqueoBillete() {
		return listaCierreDiarioArqueoBillete;
	}
	public void setListaCierreDiarioArqueoBillete(List<CierreDiarioArqueoBillete> listaCierreDiarioArqueoBillete) {
		this.listaCierreDiarioArqueoBillete = listaCierreDiarioArqueoBillete;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "CierreDiarioArqueo [id=" + id + ", tsFechaCierreArqueo="
				+ tsFechaCierreArqueo + ", intPersEmpresaResponsable="
				+ intPersEmpresaResponsable + ", intPersPersonaResponsable="
				+ intPersPersonaResponsable + ", strObservacion="
				+ strObservacion + ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intParaEstadoCierre=" + intParaEstadoCierre
				+ ", bdMontoTotalEfectivo=" + bdMontoTotalEfectivo
				+ ", bdMontoTotalFondo=" + bdMontoTotalFondo
				+ ", bdMontoDiferencia=" + bdMontoDiferencia
				+ ", intPersEmpresaCierreAn=" + intPersEmpresaCierreAn
				+ ", intPersPersonaCierreAn=" + intPersPersonaCierreAn
				+ ", strObersvacionAnula=" + strObersvacionAnula
				+ ", tsFechaCierreAnula=" + tsFechaCierreAnula + "]";
	}
	
	
}