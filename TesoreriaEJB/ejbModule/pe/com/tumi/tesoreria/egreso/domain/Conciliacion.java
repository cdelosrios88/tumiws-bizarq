/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-006       			26/10/2014     		Bisarq        Nuevos Atributos        
*/
package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;

public class Conciliacion extends TumiDomain{

	private ConciliacionId id;
	private Timestamp tsFechaConciliacion;
	private Integer intPersEmpresa;
	private Integer intItemBancoFondo;
	private Integer intItemBancoCuenta;
	private BigDecimal bdMontoSaldoInicial;
	private BigDecimal bdMontoDebe;
	private BigDecimal bdMontoHaber;
	private Integer intRegistrosConciliados;
	private Integer intRegistrosNoConciliados;
	private Integer intPersEmpresaConcilia;
	private Integer intPersPersonaConcilia;
	private Integer intPersEmpresaAnula;
	private Integer intPersPersonaAnula;
	private Timestamp tsFechaAnula;
	private String 	strObservaciónAnula;
	private Integer intParaEstado;
	
	private List<ConciliacionDetalle> listaConciliacionDetalle;
	private Bancocuenta	bancoCuenta;
	private Integer intParaDocumentoGeneralFiltro;
	private Integer intEstadoCheckFiltro;
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	private Timestamp tsFechaConcilia;
	private String strBanco;	
	private String strTipoCuenta;	
	private String strMoneda;	
	private String strNumeroCuenta;	
	private BigDecimal bdSaldoAnterior;	
	private BigDecimal bdDebe;	
	private BigDecimal bdHaber;	
	private BigDecimal bdSaldoCaja;	
	private BigDecimal bdSaldoConciliacion;	
	private Integer intNroMovimientos;	
	private BigDecimal bdPorConciliar;
	private Usuario usuario;
	private List<ConciliacionDetalle> listaConciliacionDetalleVisual;
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
	public Conciliacion(){
		id = new ConciliacionId();
	}
	
	public ConciliacionId getId() {
		return id;
	}
	public void setId(ConciliacionId id) {
		this.id = id;
	}
	public Timestamp getTsFechaConciliacion() {
		return tsFechaConciliacion;
	}
	public void setTsFechaConciliacion(Timestamp tsFechaConciliacion) {
		this.tsFechaConciliacion = tsFechaConciliacion;
	}
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntItemBancoFondo() {
		return intItemBancoFondo;
	}
	public void setIntItemBancoFondo(Integer intItemBancoFondo) {
		this.intItemBancoFondo = intItemBancoFondo;
	}
	public Integer getIntItemBancoCuenta() {
		return intItemBancoCuenta;
	}
	public void setIntItemBancoCuenta(Integer intItemBancoCuenta) {
		this.intItemBancoCuenta = intItemBancoCuenta;
	}
	public BigDecimal getBdMontoSaldoInicial() {
		return bdMontoSaldoInicial;
	}
	public void setBdMontoSaldoInicial(BigDecimal bdMontoSaldoInicial) {
		this.bdMontoSaldoInicial = bdMontoSaldoInicial;
	}
	public BigDecimal getBdMontoDebe() {
		return bdMontoDebe;
	}
	public void setBdMontoDebe(BigDecimal bdMontoDebe) {
		this.bdMontoDebe = bdMontoDebe;
	}
	public BigDecimal getBdMontoHaber() {
		return bdMontoHaber;
	}
	public void setBdMontoHaber(BigDecimal bdMontoHaber) {
		this.bdMontoHaber = bdMontoHaber;
	}
	public Integer getIntRegistrosConciliados() {
		return intRegistrosConciliados;
	}
	public void setIntRegistrosConciliados(Integer intRegistrosConciliados) {
		this.intRegistrosConciliados = intRegistrosConciliados;
	}
	public Integer getIntRegistrosNoConciliados() {
		return intRegistrosNoConciliados;
	}
	public void setIntRegistrosNoConciliados(Integer intRegistrosNoConciliados) {
		this.intRegistrosNoConciliados = intRegistrosNoConciliados;
	}
	public Integer getIntPersEmpresaConcilia() {
		return intPersEmpresaConcilia;
	}
	public void setIntPersEmpresaConcilia(Integer intPersEmpresaConcilia) {
		this.intPersEmpresaConcilia = intPersEmpresaConcilia;
	}
	public Integer getIntPersPersonaConcilia() {
		return intPersPersonaConcilia;
	}
	public void setIntPersPersonaConcilia(Integer intPersPersonaConcilia) {
		this.intPersPersonaConcilia = intPersPersonaConcilia;
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
	public String getStrObservaciónAnula() {
		return strObservaciónAnula;
	}
	public void setStrObservaciónAnula(String strObservaciónAnula) {
		this.strObservaciónAnula = strObservaciónAnula;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Bancocuenta getBancoCuenta() {
		return bancoCuenta;
	}
	public void setBancoCuenta(Bancocuenta bancoCuenta) {
		this.bancoCuenta = bancoCuenta;
	}
	public Integer getIntParaDocumentoGeneralFiltro() {
		return intParaDocumentoGeneralFiltro;
	}
	public void setIntParaDocumentoGeneralFiltro(Integer intParaDocumentoGeneralFiltro) {
		this.intParaDocumentoGeneralFiltro = intParaDocumentoGeneralFiltro;
	}
	public Integer getIntEstadoCheckFiltro() {
		return intEstadoCheckFiltro;
	}
	public void setIntEstadoCheckFiltro(Integer intEstadoCheckFiltro) {
		this.intEstadoCheckFiltro = intEstadoCheckFiltro;
	}	
	public List<ConciliacionDetalle> getListaConciliacionDetalle() {
		return listaConciliacionDetalle;
	}
	public void setListaConciliacionDetalle(List<ConciliacionDetalle> listaConciliacionDetalle) {
		this.listaConciliacionDetalle = listaConciliacionDetalle;
	}
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	
	public String getStrBanco() {
		return strBanco;
	}

	public void setStrBanco(String strBanco) {
		this.strBanco = strBanco;
	}

	public String getStrTipoCuenta() {
		return strTipoCuenta;
	}

	public void setStrTipoCuenta(String strTipoCuenta) {
		this.strTipoCuenta = strTipoCuenta;
	}

	public String getStrMoneda() {
		return strMoneda;
	}

	public void setStrMoneda(String strMoneda) {
		this.strMoneda = strMoneda;
	}

	public String getStrNumeroCuenta() {
		return strNumeroCuenta;
	}

	public void setStrNumeroCuenta(String strNumeroCuenta) {
		this.strNumeroCuenta = strNumeroCuenta;
	}

	public BigDecimal getBdSaldoAnterior() {
		return bdSaldoAnterior;
	}

	public void setBdSaldoAnterior(BigDecimal bdSaldoAnterior) {
		this.bdSaldoAnterior = bdSaldoAnterior;
	}

	public BigDecimal getBdDebe() {
		return bdDebe;
	}

	public void setBdDebe(BigDecimal bdDebe) {
		this.bdDebe = bdDebe;
	}

	public BigDecimal getBdHaber() {
		return bdHaber;
	}

	public void setBdHaber(BigDecimal bdHaber) {
		this.bdHaber = bdHaber;
	}

	public BigDecimal getBdSaldoCaja() {
		return bdSaldoCaja;
	}

	public void setBdSaldoCaja(BigDecimal bdSaldoCaja) {
		this.bdSaldoCaja = bdSaldoCaja;
	}

	public BigDecimal getBdSaldoConciliacion() {
		return bdSaldoConciliacion;
	}

	public void setBdSaldoConciliacion(BigDecimal bdSaldoConciliacion) {
		this.bdSaldoConciliacion = bdSaldoConciliacion;
	}

	public Integer getIntNroMovimientos() {
		return intNroMovimientos;
	}

	public void setIntNroMovimientos(Integer intNroMovimientos) {
		this.intNroMovimientos = intNroMovimientos;
	}

	public BigDecimal getBdPorConciliar() {
		return bdPorConciliar;
	}

	public void setBdPorConciliar(BigDecimal bdPorConciliar) {
		this.bdPorConciliar = bdPorConciliar;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

	public Timestamp getTsFechaConcilia() {
		return tsFechaConcilia;
	}

	public void setTsFechaConcilia(Timestamp tsFechaConcilia) {
		this.tsFechaConcilia = tsFechaConcilia;
	}
	

	/**
	 * @return the listaConciliacionDetalleVisual
	 */
	public List<ConciliacionDetalle> getListaConciliacionDetalleVisual() {
		return listaConciliacionDetalleVisual;
	}

	/**
	 * @param listaConciliacionDetalleVisual the listaConciliacionDetalleVisual to set
	 */
	public void setListaConciliacionDetalleVisual(
			List<ConciliacionDetalle> listaConciliacionDetalleVisual) {
		this.listaConciliacionDetalleVisual = listaConciliacionDetalleVisual;
	}
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	


	@Override
	public String toString() {
		return "Conciliacion [id=" + id + ", tsFechaConciliacion="
				+ tsFechaConciliacion + ", intPersEmpresa=" + intPersEmpresa
				+ ", intItemBancoFondo=" + intItemBancoFondo
				+ ", intItemBancoCuenta=" + intItemBancoCuenta
				+ ", bdMontoSaldoInicial=" + bdMontoSaldoInicial
				+ ", bdMontoDebe=" + bdMontoDebe + ", bdMontoHaber="
				+ bdMontoHaber + ", intRegistrosConciliados="
				+ intRegistrosConciliados + ", intRegistrosNoConciliados="
				+ intRegistrosNoConciliados + ", intPersEmpresaConcilia="
				+ intPersEmpresaConcilia + ", intPersPersonaConcilia="
				+ intPersPersonaConcilia + ", intPersEmpresaAnula="
				+ intPersEmpresaAnula + ", intPersPersonaAnula="
				+ intPersPersonaAnula + ", tsFechaAnula=" + tsFechaAnula
				+ ", strObservaciónAnula=" + strObservaciónAnula
				+ ", intParaEstado=" + intParaEstado + "]";
	}


}