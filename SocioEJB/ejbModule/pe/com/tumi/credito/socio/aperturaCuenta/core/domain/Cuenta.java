package pe.com.tumi.credito.socio.aperturaCuenta.core.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;

public class Cuenta extends TumiDomain {
	private CuentaId id;
	private String strNombreCuenta;
	private Integer	intIntegrantes;
	private Integer	intParaTipoCuentaCod;
	private Integer	intParaTipoConformacionCod;
	private Integer	intParaSubTipoCuentaCod;
	private Integer	intParaTipoMonedaCod;
	private Integer	intSecuenciaCuenta;
	private String strNumeroCuenta;
	private BigDecimal bdMontoPlanilla;
	private Integer	intParaTipoFrecuenciaCod;
	private Integer	intParaTipoPeriodicidadCod;
	private Date dtFechaInicioCobro;
	private Integer	intParaOpcionRenovacionCod;
	private Integer	intParaSituacionCuentaCod;
	private Integer	intParaCondicionCuentaCod;
	private Integer	intParaSubCondicionCuentaCod;
	private Integer	intIdUsuSucursal;
	private Integer	intIdUsuSubSucursal;
	private Integer	intPersonaUsuSucursal;
	private Timestamp tsFecRegUsuSucursal;
	private Timestamp tsCuentFecRegistro;
	
	//Auxiliar
	private Integer intIdPersona;
	private Credito	credito;
	
	private List<CuentaIntegrante> listaIntegrante;
	private List<CuentaConcepto> listaConcepto;

	public CuentaId getId() {
		return id;
	}
	public void setId(CuentaId id) {
		this.id = id;
	}
	public String getStrNombreCuenta() {
		return strNombreCuenta;
	}
	public void setStrNombreCuenta(String strNombreCuenta) {
		this.strNombreCuenta = strNombreCuenta;
	}
	public Integer getIntIntegrantes() {
		return intIntegrantes;
	}
	public void setIntIntegrantes(Integer intIntegrantes) {
		this.intIntegrantes = intIntegrantes;
	}
	public Integer getIntParaTipoCuentaCod() {
		return intParaTipoCuentaCod;
	}
	public void setIntParaTipoCuentaCod(Integer intParaTipoCuentaCod) {
		this.intParaTipoCuentaCod = intParaTipoCuentaCod;
	}
	public Integer getIntParaTipoConformacionCod() {
		return intParaTipoConformacionCod;
	}
	public void setIntParaTipoConformacionCod(Integer intParaTipoConformacionCod) {
		this.intParaTipoConformacionCod = intParaTipoConformacionCod;
	}
	public Integer getIntParaSubTipoCuentaCod() {
		return intParaSubTipoCuentaCod;
	}
	public void setIntParaSubTipoCuentaCod(Integer intParaSubTipoCuentaCod) {
		this.intParaSubTipoCuentaCod = intParaSubTipoCuentaCod;
	}
	public Integer getIntParaTipoMonedaCod() {
		return intParaTipoMonedaCod;
	}
	public void setIntParaTipoMonedaCod(Integer intParaTipoMonedaCod) {
		this.intParaTipoMonedaCod = intParaTipoMonedaCod;
	}
	public Integer getIntSecuenciaCuenta() {
		return intSecuenciaCuenta;
	}
	public void setIntSecuenciaCuenta(Integer intSecuenciaCuenta) {
		this.intSecuenciaCuenta = intSecuenciaCuenta;
	}
	public String getStrNumeroCuenta() {
		return strNumeroCuenta;
	}
	public void setStrNumeroCuenta(String strNumeroCuenta) {
		this.strNumeroCuenta = strNumeroCuenta;
	}
	public BigDecimal getBdMontoPlanilla() {
		return bdMontoPlanilla;
	}
	public void setBdMontoPlanilla(BigDecimal bdMontoPlanilla) {
		this.bdMontoPlanilla = bdMontoPlanilla;
	}
	public Integer getIntParaTipoFrecuenciaCod() {
		return intParaTipoFrecuenciaCod;
	}
	public void setIntParaTipoFrecuenciaCod(Integer intParaTipoFrecuenciaCod) {
		this.intParaTipoFrecuenciaCod = intParaTipoFrecuenciaCod;
	}
	public Integer getIntParaTipoPeriodicidadCod() {
		return intParaTipoPeriodicidadCod;
	}
	public void setIntParaTipoPeriodicidadCod(Integer intParaTipoPeriodicidadCod) {
		this.intParaTipoPeriodicidadCod = intParaTipoPeriodicidadCod;
	}
	public Date getDtFechaInicioCobro() {
		return dtFechaInicioCobro;
	}
	public void setDtFechaInicioCobro(Date dtFechaInicioCobro) {
		this.dtFechaInicioCobro = dtFechaInicioCobro;
	}
	public Integer getIntParaOpcionRenovacionCod() {
		return intParaOpcionRenovacionCod;
	}
	public void setIntParaOpcionRenovacionCod(Integer intParaOpcionRenovacionCod) {
		this.intParaOpcionRenovacionCod = intParaOpcionRenovacionCod;
	}
	public Integer getIntParaSituacionCuentaCod() {
		return intParaSituacionCuentaCod;
	}
	public void setIntParaSituacionCuentaCod(Integer intParaSituacionCuentaCod) {
		this.intParaSituacionCuentaCod = intParaSituacionCuentaCod;
	}
	public Integer getIntParaCondicionCuentaCod() {
		return intParaCondicionCuentaCod;
	}
	public void setIntParaCondicionCuentaCod(Integer intParaCondicionCuentaCod) {
		this.intParaCondicionCuentaCod = intParaCondicionCuentaCod;
	}
	public Integer getIntParaSubCondicionCuentaCod() {
		return intParaSubCondicionCuentaCod;
	}
	public void setIntParaSubCondicionCuentaCod(Integer intParaSubCondicionCuentaCod) {
		this.intParaSubCondicionCuentaCod = intParaSubCondicionCuentaCod;
	}
	public Integer getIntIdUsuSucursal() {
		return intIdUsuSucursal;
	}
	public void setIntIdUsuSucursal(Integer intIdUsuSucursal) {
		this.intIdUsuSucursal = intIdUsuSucursal;
	}
	public Integer getIntIdUsuSubSucursal() {
		return intIdUsuSubSucursal;
	}
	public void setIntIdUsuSubSucursal(Integer intIdUsuSubSucursal) {
		this.intIdUsuSubSucursal = intIdUsuSubSucursal;
	}
	public Integer getIntPersonaUsuSucursal() {
		return intPersonaUsuSucursal;
	}
	public void setIntPersonaUsuSucursal(Integer intPersonaUsuSucursal) {
		this.intPersonaUsuSucursal = intPersonaUsuSucursal;
	}
	public Timestamp getTsFecRegUsuSucursal() {
		return tsFecRegUsuSucursal;
	}
	public void setTsFecRegUsuSucursal(Timestamp tsFecRegUsuSucursal) {
		this.tsFecRegUsuSucursal = tsFecRegUsuSucursal;
	}
	public Timestamp getTsCuentFecRegistro() {
		return tsCuentFecRegistro;
	}
	public void setTsCuentFecRegistro(Timestamp tsCuentFecRegistro) {
		this.tsCuentFecRegistro = tsCuentFecRegistro;
	}
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public List<CuentaIntegrante> getListaIntegrante() {
		return listaIntegrante;
	}
	public void setListaIntegrante(List<CuentaIntegrante> listaIntegrante) {
		this.listaIntegrante = listaIntegrante;
	}
	public List<CuentaConcepto> getListaConcepto() {
		return listaConcepto;
	}
	public void setListaConcepto(List<CuentaConcepto> listaConcepto) {
		this.listaConcepto = listaConcepto;
	}
	public Credito getCredito() {
		return credito;
	}
	public void setCredito(Credito credito) {
		this.credito = credito;
	}

	@Override
	public String toString() {
		return "Cuenta [id=" + id + ", strNombreCuenta=" + strNombreCuenta
				+ ", intIntegrantes=" + intIntegrantes
				+ ", intParaTipoCuentaCod=" + intParaTipoCuentaCod
				+ ", intParaTipoConformacionCod=" + intParaTipoConformacionCod
				+ ", intParaSubTipoCuentaCod=" + intParaSubTipoCuentaCod
				+ ", intParaTipoMonedaCod=" + intParaTipoMonedaCod
				+ ", intSecuenciaCuenta=" + intSecuenciaCuenta
				+ ", strNumeroCuenta=" + strNumeroCuenta + ", bdMontoPlanilla="
				+ bdMontoPlanilla + ", intParaTipoFrecuenciaCod="
				+ intParaTipoFrecuenciaCod + ", intParaTipoPeriodicidadCod="
				+ intParaTipoPeriodicidadCod + ", dtFechaInicioCobro="
				+ dtFechaInicioCobro + ", intParaOpcionRenovacionCod="
				+ intParaOpcionRenovacionCod + ", intParaSituacionCuentaCod="
				+ intParaSituacionCuentaCod + ", intParaCondicionCuentaCod="
				+ intParaCondicionCuentaCod + ", intParaSubCondicionCuentaCod="
				+ intParaSubCondicionCuentaCod + ", intIdUsuSucursal="
				+ intIdUsuSucursal + ", intIdUsuSubSucursal="
				+ intIdUsuSubSucursal + ", intPersonaUsuSucursal="
				+ intPersonaUsuSucursal + ", tsFecRegUsuSucursal="
				+ tsFecRegUsuSucursal + ", tsCuentFecRegistro="
				+ tsCuentFecRegistro + "]";
	}
	
}