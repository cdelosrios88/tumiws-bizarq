/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-005       			19/10/2014     Christian De los Ríos        Se agregó el atributo 'strMotivoAnula'        
*/
package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;

public class Saldo extends TumiDomain{

	private SaldoId	id;
	private Integer intEmpresaPk;
	private Integer	intItemBancoCuenta;
	private Integer intItemBancoFondo;
	private Integer	intParaTipoFondoFijo;
	private BigDecimal	bdSaldoInicial;
	private BigDecimal	bdMovimientos;
	private Timestamp	tsFechaRegistro;
	private Integer		intPersEmpresaUsuario;
	private Integer		intPersPersonaUsuario;
	private Integer		intParaEstado;
	private Integer		intPersEmpresaAnula;
	private Integer		intPersPersonaAnula;
	private Timestamp	tsFechaAnula;
	//Inicio: REQ14-005 - bizarq - 19/10/2014
	private String 	strMotivoAnula;
	//Fin: REQ14-005 - bizarq - 19/10/2014
	
	private List<LibroDiarioDetalle> listaLibroDiarioDetalle;
	private List<Ingreso>	listaIngreso;
	private List<Egreso>	listaEgreso;
	private	Date	dtFechaDesde;
	private Date	dtFechaHasta;
	private	String	strEtiqueta;
	private	String	strEtiquetaSucursal;
	
	public Saldo(){
		id = new SaldoId();
	}
	
	public SaldoId getId() {
		return id;
	}
	public void setId(SaldoId id) {
		this.id = id;
	}
	public Integer getIntEmpresaPk() {
		return intEmpresaPk;
	}
	public void setIntEmpresaPk(Integer intEmpresaPk) {
		this.intEmpresaPk = intEmpresaPk;
	}
	public Integer getIntItemBancoCuenta() {
		return intItemBancoCuenta;
	}
	public void setIntItemBancoCuenta(Integer intItemBancoCuenta) {
		this.intItemBancoCuenta = intItemBancoCuenta;
	}
	public Integer getIntItemBancoFondo() {
		return intItemBancoFondo;
	}
	public void setIntItemBancoFondo(Integer intItemBancoFondo) {
		this.intItemBancoFondo = intItemBancoFondo;
	}
	public Integer getIntParaTipoFondoFijo() {
		return intParaTipoFondoFijo;
	}
	public void setIntParaTipoFondoFijo(Integer intParaTipoFondoFijo) {
		this.intParaTipoFondoFijo = intParaTipoFondoFijo;
	}
	public BigDecimal getBdSaldoInicial() {
		return bdSaldoInicial;
	}
	public void setBdSaldoInicial(BigDecimal bdSaldoInicial) {
		this.bdSaldoInicial = bdSaldoInicial;
	}
	public BigDecimal getBdMovimientos() {
		return bdMovimientos;
	}
	public void setBdMovimientos(BigDecimal bdMovimientos) {
		this.bdMovimientos = bdMovimientos;
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
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
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
	public Timestamp getTsFechaAnula() {
		return tsFechaAnula;
	}
	public void setTsFechaAnula(Timestamp tsFechaAnula) {
		this.tsFechaAnula = tsFechaAnula;
	}
	public List<LibroDiarioDetalle> getListaLibroDiarioDetalle() {
		return listaLibroDiarioDetalle;
	}
	public void setListaLibroDiarioDetalle(List<LibroDiarioDetalle> listaLibroDiarioDetalle) {
		this.listaLibroDiarioDetalle = listaLibroDiarioDetalle;
	}
	public List<Ingreso> getListaIngreso() {
		return listaIngreso;
	}
	public void setListaIngreso(List<Ingreso> listaIngreso) {
		this.listaIngreso = listaIngreso;
	}
	public List<Egreso> getListaEgreso() {
		return listaEgreso;
	}
	public void setListaEgreso(List<Egreso> listaEgreso) {
		this.listaEgreso = listaEgreso;
	}
	public Date getDtFechaDesde() {
		return dtFechaDesde;
	}
	public void setDtFechaDesde(Date dtFechaDesde) {
		this.dtFechaDesde = dtFechaDesde;
	}
	public Date getDtFechaHasta() {
		return dtFechaHasta;
	}
	public void setDtFechaHasta(Date dtFechaHasta) {
		this.dtFechaHasta = dtFechaHasta;
	}
	public String getStrEtiqueta() {
		return strEtiqueta;
	}
	public void setStrEtiqueta(String strEtiqueta) {
		this.strEtiqueta = strEtiqueta;
	}
	public String getStrEtiquetaSucursal() {
		return strEtiquetaSucursal;
	}
	public void setStrEtiquetaSucursal(String strEtiquetaSucursal) {
		this.strEtiquetaSucursal = strEtiquetaSucursal;
	}
	//Inicio: REQ14-005 - bizarq - 19/10/2014
	public String getStrMotivoAnula() {
		return strMotivoAnula;
	}
	public void setStrMotivoAnula(String strMotivoAnula) {
		this.strMotivoAnula = strMotivoAnula;
	}
	//Fin: REQ14-005 - bizarq - 19/10/2014

	@Override
	public String toString() {
		return "Saldo [id=" + id + ", intEmpresaPk=" + intEmpresaPk
				+ ", intItemBancoCuenta=" + intItemBancoCuenta
				+ ", intItemBancoFondo=" + intItemBancoFondo
				+ ", intParaTipoFondoFijo=" + intParaTipoFondoFijo
				+ ", bdSaldoInicial=" + bdSaldoInicial + ", bdMovimientos="
				+ bdMovimientos + ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", intParaEstado=" + intParaEstado + ", intPersEmpresaAnula="
				+ intPersEmpresaAnula + ", intPersPersonaAnula="
				+ intPersPersonaAnula + ", tsFechaAnula=" + tsFechaAnula + "]";
	}
}