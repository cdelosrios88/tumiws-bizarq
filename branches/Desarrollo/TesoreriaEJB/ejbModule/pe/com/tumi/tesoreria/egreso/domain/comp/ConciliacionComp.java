/************************************************************************
/* Nombre de componente: ConciliacionComp 
 * Descripción: Clase Compuesta de Conciliacion
 * del aplicativo - Modulo Tesoreria
 * Cod. Req.: REQ14-006  
 * Autor : Bizarq Technologies 
 * Versión : v1.0 - Creacion de componente 
 * Fecha creación : 18/10/2014 
/* ********************************************************************* */
package pe.com.tumi.tesoreria.egreso.domain.comp;

import java.math.BigDecimal;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;

public class ConciliacionComp extends TumiDomain {
	

	//Filtros de la grilla de busqueda de concniliacion y reutilizados para anulacion
	private Date dtBusqFechaDesde;
	
	private Date dtBusqFechaHasta;
	
	private Integer intBusqPersEmpresa;

	private Integer intBusqItemBancoFondo;
	
	private Integer intBusqItemBancoCuenta;

	
	
	// Campos de Resultado de busqueda
	
	private Integer intEmpresaConciliacion;
	
	private Integer intItemConciliacion;	
	
	private String strFechaConciliacion;
	
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
	
	// Tabla Resumen
	private BigDecimal bdResumenSaldoAnterior;
	private BigDecimal bdResumenDebe;
	private BigDecimal bdResumenHaber;
	private BigDecimal bdResumenSaldoCaja;
	private BigDecimal bdResumenSaldoConciliacion;
	private Integer intResumenNroMov;
	private BigDecimal bdResumenPorConciliar;
	/* Inicio: REQ14-006 Bizarq - 01/11/2014 */
	private Conciliacion conciliacion;
	private Date dtFechaAnulDesde;
	private String strObservacionAnula;
	/* Fin: REQ14-006 Bizarq - 01/11/2014 */

	
	public Date getDtBusqFechaDesde() {
		return dtBusqFechaDesde;
	}

	public void setDtBusqFechaDesde(Date dtBusqFechaDesde) {
		this.dtBusqFechaDesde = dtBusqFechaDesde;
	}

	public Date getDtBusqFechaHasta() {
		return dtBusqFechaHasta;
	}

	public void setDtBusqFechaHasta(Date dtBusqFechaHasta) {
		this.dtBusqFechaHasta = dtBusqFechaHasta;
	}

	public Integer getIntBusqPersEmpresa() {
		return intBusqPersEmpresa;
	}

	public void setIntBusqPersEmpresa(Integer intBusqPersEmpresa) {
		this.intBusqPersEmpresa = intBusqPersEmpresa;
	}

	public Integer getIntBusqItemBancoFondo() {
		return intBusqItemBancoFondo;
	}

	public void setIntBusqItemBancoFondo(Integer intBusqItemBancoFondo) {
		this.intBusqItemBancoFondo = intBusqItemBancoFondo;
	}

	public Integer getIntBusqItemBancoCuenta() {
		return intBusqItemBancoCuenta;
	}

	public void setIntBusqItemBancoCuenta(Integer intBusqItemBancoCuenta) {
		this.intBusqItemBancoCuenta = intBusqItemBancoCuenta;
	}

	public Integer getIntEmpresaConciliacion() {
		return intEmpresaConciliacion;
	}

	public void setIntEmpresaConciliacion(Integer intEmpresaConciliacion) {
		this.intEmpresaConciliacion = intEmpresaConciliacion;
	}

	public Integer getIntItemConciliacion() {
		return intItemConciliacion;
	}

	public void setIntItemConciliacion(Integer intItemConciliacion) {
		this.intItemConciliacion = intItemConciliacion;
	}

	public String getStrFechaConciliacion() {
		return strFechaConciliacion;
	}

	public void setStrFechaConciliacion(String strFechaConciliacion) {
		this.strFechaConciliacion = strFechaConciliacion;
	}

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

	public BigDecimal getBdResumenSaldoAnterior() {
		return bdResumenSaldoAnterior;
	}

	public void setBdResumenSaldoAnterior(BigDecimal bdResumenSaldoAnterior) {
		this.bdResumenSaldoAnterior = bdResumenSaldoAnterior;
	}

	public BigDecimal getBdResumenDebe() {
		return bdResumenDebe;
	}

	public void setBdResumenDebe(BigDecimal bdResumenDebe) {
		this.bdResumenDebe = bdResumenDebe;
	}

	public BigDecimal getBdResumenHaber() {
		return bdResumenHaber;
	}

	public void setBdResumenHaber(BigDecimal bdResumenHaber) {
		this.bdResumenHaber = bdResumenHaber;
	}

	public BigDecimal getBdResumenSaldoCaja() {
		return bdResumenSaldoCaja;
	}

	public void setBdResumenSaldoCaja(BigDecimal bdResumenSaldoCaja) {
		this.bdResumenSaldoCaja = bdResumenSaldoCaja;
	}

	public BigDecimal getBdResumenSaldoConciliacion() {
		return bdResumenSaldoConciliacion;
	}

	public void setBdResumenSaldoConciliacion(BigDecimal bdResumenSaldoConciliacion) {
		this.bdResumenSaldoConciliacion = bdResumenSaldoConciliacion;
	}

	public Integer getIntResumenNroMov() {
		return intResumenNroMov;
	}

	public void setIntResumenNroMov(Integer intResumenNroMov) {
		this.intResumenNroMov = intResumenNroMov;
	}

	public BigDecimal getBdResumenPorConciliar() {
		return bdResumenPorConciliar;
	}

	public void setBdResumenPorConciliar(BigDecimal bdResumenPorConciliar) {
		this.bdResumenPorConciliar = bdResumenPorConciliar;
	}
	
	
	public Conciliacion getConciliacion() {
		return conciliacion;
	}

	public void setConciliacion(Conciliacion conciliacion) {
		this.conciliacion = conciliacion;
	}

	public Date getDtFechaAnulDesde() {
		return dtFechaAnulDesde;
	}

	public void setDtFechaAnulDesde(Date dtFechaAnulDesde) {
		this.dtFechaAnulDesde = dtFechaAnulDesde;
	}

	public String getStrObservacionAnula() {
		return strObservacionAnula;
	}

	public void setStrObservacionAnula(String strObservacionAnula) {
		this.strObservacionAnula = strObservacionAnula;
	}


}
