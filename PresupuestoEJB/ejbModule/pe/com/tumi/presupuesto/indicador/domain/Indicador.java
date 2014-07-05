package pe.com.tumi.presupuesto.indicador.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Indicador extends TumiDomain{
	
	private IndicadorId id;	
	private Integer intParaTipoValor;
	private BigDecimal bdMontoProyectado;
	private BigDecimal bdMontoEjecutado;
	private Timestamp tsFechaRegistro;
	private Integer intParaEstadoCierre;
	private Integer intEmpresaUsuarioPk;
 	private Integer intPersonaUsuarioPk;
 	
 	//ADICIONALES JCHAVEZ 11.10.2013
 	private String strTipoIndicador;
 	private String strEstadoCierre;
 	private String strTipoValor;
 	private String strMesIndicador;
 	private String strIdSubSucursal;

	public IndicadorId getId() {
		return id;
	}
	public void setId(IndicadorId id) {
		this.id = id;
	}
	public Integer getIntParaTipoValor() {
		return intParaTipoValor;
	}
	public void setIntParaTipoValor(Integer intParaTipoValor) {
		this.intParaTipoValor = intParaTipoValor;
	}
	public BigDecimal getBdMontoProyectado() {
		return bdMontoProyectado;
	}
	public void setBdMontoProyectado(BigDecimal bdMontoProyectado) {
		this.bdMontoProyectado = bdMontoProyectado;
	}
	public BigDecimal getBdMontoEjecutado() {
		return bdMontoEjecutado;
	}
	public void setBdMontoEjecutado(BigDecimal bdMontoEjecutado) {
		this.bdMontoEjecutado = bdMontoEjecutado;
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
	public String getStrTipoIndicador() {
		return strTipoIndicador;
	}
	public void setStrTipoIndicador(String strTipoIndicador) {
		this.strTipoIndicador = strTipoIndicador;
	}
	public String getStrEstadoCierre() {
		return strEstadoCierre;
	}
	public void setStrEstadoCierre(String strEstadoCierre) {
		this.strEstadoCierre = strEstadoCierre;
	}
	public String getStrTipoValor() {
		return strTipoValor;
	}
	public void setStrTipoValor(String strTipoValor) {
		this.strTipoValor = strTipoValor;
	}
	public String getStrMesIndicador() {
		return strMesIndicador;
	}
	public void setStrMesIndicador(String strMesIndicador) {
		this.strMesIndicador = strMesIndicador;
	}
	public String getStrIdSubSucursal() {
		return strIdSubSucursal;
	}
	public void setStrIdSubSucursal(String strIdSubSucursal) {
		this.strIdSubSucursal = strIdSubSucursal;
	}
}
