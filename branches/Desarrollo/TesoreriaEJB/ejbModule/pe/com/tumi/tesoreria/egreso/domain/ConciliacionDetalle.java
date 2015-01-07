/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-006       			28/10/2014     		Bisarq        Nuevos Atributos        
*/
package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;

public class ConciliacionDetalle extends TumiDomain{

	private ConciliacionDetalleId id;
	private Integer intPersEmpresaEgreso;
	private Integer intItemEgresoGeneral;
	private Integer intPersEmpresaIngreso;
	private Integer intItemIngresoGeneral;
	private Integer intIndicadorCheck;
	private Integer intIndicadorConci;
	private BigDecimal bdSaldoInicial;
	private BigDecimal bdMontoDebe;
	private BigDecimal bdMontoHaber;
	private Integer intPersEmpresaCheckConciliacion;
	private Integer intPersPersonaCheckConciliacion;
	private Timestamp tsFechaCheck;
	private Integer intNumeroOperacion;
	
	/* Inicio: REQ14-006 Bizarq - 28/10/2014 */
	private Ingreso ingreso;
	private Egreso	egreso;
	private Boolean blIndicadorCheck;
	private Boolean blIndicadorConci;
	private Boolean blValid;
	private String strDescripcionSucursalPaga;
	/* Fin: REQ14-006 Bizarq - 28/10/2014 */
	
	/* Inicio: REQ14-006 Bizarq - 28/10/2014 */
	private Integer intIdSucursalGira;
	private Integer intIdSubSucursalGira;
	private Integer intIdSucursalPaga;
	private Integer intIdSubSucursalPaga;
	/* Fin: REQ14-006 Bizarq - 28/10/2014 */
	
	/* Inicio: REQ14-006 Bizarq - 07/01/2015 */
	private BigDecimal bdSaldoFinal;
	/* Fin: REQ14-006 Bizarq - 07/01/2015 */
	
	public ConciliacionDetalleId getId() {
		return id;
	}
	public void setId(ConciliacionDetalleId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public Integer getIntPersEmpresaIngreso() {
		return intPersEmpresaIngreso;
	}
	public void setIntPersEmpresaIngreso(Integer intPersEmpresaIngreso) {
		this.intPersEmpresaIngreso = intPersEmpresaIngreso;
	}
	public Integer getIntItemIngresoGeneral() {
		return intItemIngresoGeneral;
	}
	public void setIntItemIngresoGeneral(Integer intItemIngresoGeneral) {
		this.intItemIngresoGeneral = intItemIngresoGeneral;
	}
	public Integer getIntIndicadorCheck() {
		return intIndicadorCheck;
	}
	public void setIntIndicadorCheck(Integer intIndicadorCheck) {
		this.intIndicadorCheck = intIndicadorCheck;
	}
	public Integer getIntIndicadorConci() {
		return intIndicadorConci;
	}
	public void setIntIndicadorConci(Integer intIndicadorConci) {
		this.intIndicadorConci = intIndicadorConci;
	}
	public BigDecimal getBdSaldoInicial() {
		return bdSaldoInicial;
	}
	public void setBdSaldoInicial(BigDecimal bdSaldoInicial) {
		this.bdSaldoInicial = bdSaldoInicial;
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
	public Integer getIntPersEmpresaCheckConciliacion() {
		return intPersEmpresaCheckConciliacion;
	}
	public void setIntPersEmpresaCheckConciliacion(Integer intPersEmpresaCheckConciliacion) {
		this.intPersEmpresaCheckConciliacion = intPersEmpresaCheckConciliacion;
	}
	public Integer getIntPersPersonaCheckConciliacion() {
		return intPersPersonaCheckConciliacion;
	}
	public void setIntPersPersonaCheckConciliacion(	Integer intPersPersonaCheckConciliacion) {
		this.intPersPersonaCheckConciliacion = intPersPersonaCheckConciliacion;
	}
	public Timestamp getTsFechaCheck() {
		return tsFechaCheck;
	}
	public void setTsFechaCheck(Timestamp tsFechaCheck) {
		this.tsFechaCheck = tsFechaCheck;
	}
	public Integer getIntNumeroOperacion() {
		return intNumeroOperacion;
	}
	public void setIntNumeroOperacion(Integer intNumeroOperacion) {
		this.intNumeroOperacion = intNumeroOperacion;
	}	
	public Ingreso getIngreso() {
		return ingreso;
	}
	public void setIngreso(Ingreso ingreso) {
		this.ingreso = ingreso;
	}
	public Egreso getEgreso() {
		return egreso;
	}
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	public Boolean getBlIndicadorCheck() {
		return blIndicadorCheck;
	}
	public void setBlIndicadorCheck(Boolean blIndicadorCheck) {
		this.blIndicadorCheck = blIndicadorCheck;
	}
	public Boolean getBlIndicadorConci() {
		return blIndicadorConci;
	}
	public void setBlIndicadorConci(Boolean blIndicadorConci) {
		this.blIndicadorConci = blIndicadorConci;
	}
	
	/* Inicio: REQ14-006 Bizarq - 28/10/2014 */
	public Boolean getBlValid() {
		return blValid;
	}
	public void setBlValid(Boolean blValid) {
		this.blValid = blValid;
	}
	
	/**
	 * @return the strDescripcionSucursalPaga
	 */
	public String getStrDescripcionSucursalPaga() {
		return strDescripcionSucursalPaga;
	}
	/**
	 * @param strDescripcionSucursalPaga the strDescripcionSucursalPaga to set
	 */
	public void setStrDescripcionSucursalPaga(String strDescripcionSucursalPaga) {
		this.strDescripcionSucursalPaga = strDescripcionSucursalPaga;
	}
	
	/* Fin: REQ14-006 Bizarq - 28/10/2014 */
	
	public Integer getIntIdSucursalGira() {
		return intIdSucursalGira;
	}
	public void setIntIdSucursalGira(Integer intIdSucursalGira) {
		this.intIdSucursalGira = intIdSucursalGira;
	}
	public Integer getIntIdSubSucursalGira() {
		return intIdSubSucursalGira;
	}
	public void setIntIdSubSucursalGira(Integer intIdSubSucursalGira) {
		this.intIdSubSucursalGira = intIdSubSucursalGira;
	}
	public Integer getIntIdSucursalPaga() {
		return intIdSucursalPaga;
	}
	public void setIntIdSucursalPaga(Integer intIdSucursalPaga) {
		this.intIdSucursalPaga = intIdSucursalPaga;
	}
	public Integer getIntIdSubSucursalPaga() {
		return intIdSubSucursalPaga;
	}
	public void setIntIdSubSucursalPaga(Integer intIdSubSucursalPaga) {
		this.intIdSubSucursalPaga = intIdSubSucursalPaga;
	}

	public BigDecimal getBdSaldoFinal() {
		return bdSaldoFinal;
	}
	public void setBdSaldoFinal(BigDecimal bdSaldoFinal) {
		this.bdSaldoFinal = bdSaldoFinal;
	}
	@Override
	public String toString() {
		return "ConciliacionDetalle [id=" + id + ", intPersEmpresaEgreso="
				+ intPersEmpresaEgreso + ", intItemEgresoGeneral="
				+ intItemEgresoGeneral + ", intPersEmpresaIngreso="
				+ intPersEmpresaIngreso + ", intItemIngresoGeneral="
				+ intItemIngresoGeneral + ", intIndicadorCheck="
				+ intIndicadorCheck + ", intIndicadorConci="
				+ intIndicadorConci + ", bdSaldoInicial=" + bdSaldoInicial
				+ ", bdMontoDebe=" + bdMontoDebe + ", bdMontoHaber="
				+ bdMontoHaber + ", intPersEmpresaCheckConciliacion="
				+ intPersEmpresaCheckConciliacion
				+ ", intPersPersonaCheckConciliacion="
				+ intPersPersonaCheckConciliacion + ", tsFechaCheck="
				+ tsFechaCheck + ", intNumeroOperacion=" + intNumeroOperacion
				+ "]";
	}
}