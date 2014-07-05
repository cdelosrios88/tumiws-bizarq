package pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;

public class CuentaComp extends TumiDomain {
	private Cuenta cuenta;
	private CuentaIntegrante cuentaIntegrante;
	private List<CuentaDetalleBeneficio> listaDetalleBeneficio;
	private BigDecimal bdTotalAporte;
	private BigDecimal bdTotalSepelio;
	private BigDecimal bdTotalRetiro;
	private String strDescripcionTipoCuenta;
	private String strDescripcionSituacionCuenta;
	private List<ExpedienteComp> listExpedienteMovimientoComp;
	private Integer intTamannoListaExp;
	
	// cgd - 31.08.2013
	private CuentaConcepto cuentaConceptoAportes;
	private CuentaConcepto cuentaConceptoRetiro;
	private CuentaConcepto cuentaConceptoSepelio;
	private CuentaConcepto cuentaConceptoMantenimientoCuenta;
	private CuentaConcepto cuentaConceptoDeposito;
	
	//JCHAVEZ 29.10.2013
	private String strFechaRenuncia; 

	public BigDecimal getBdTotalAporte() {
		return bdTotalAporte;
	}
	public void setBdTotalAporte(BigDecimal bdTotalAporte) {
		this.bdTotalAporte = bdTotalAporte;
	}
	public BigDecimal getBdTotalSepelio() {
		return bdTotalSepelio;
	}
	public void setBdTotalSepelio(BigDecimal bdTotalSepelio) {
		this.bdTotalSepelio = bdTotalSepelio;
	}
	public BigDecimal getBdTotalRetiro() {
		return bdTotalRetiro;
	}
	public void setBdTotalRetiro(BigDecimal bdTotalRetiro) {
		this.bdTotalRetiro = bdTotalRetiro;
	}
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	public List<CuentaDetalleBeneficio> getListaDetalleBeneficio() {
		return listaDetalleBeneficio;
	}
	
	public String getStrDescripcionTipoCuenta() {
		return strDescripcionTipoCuenta;
	}
	public void setStrDescripcionTipoCuenta(String strDescripcionTipoCuenta) {
		this.strDescripcionTipoCuenta = strDescripcionTipoCuenta;
	}
	public void setListaDetalleBeneficio(
			List<CuentaDetalleBeneficio> listaDetalleBeneficio) {
		this.listaDetalleBeneficio = listaDetalleBeneficio;
	}
	public List<ExpedienteComp> getListExpedienteMovimientoComp() {
		return listExpedienteMovimientoComp;
	}
	public void setListExpedienteMovimientoComp(
			List<ExpedienteComp> listExpedienteMovimientoComp) {
		this.listExpedienteMovimientoComp = listExpedienteMovimientoComp;
	}
	public Integer getIntTamannoListaExp() {
		return intTamannoListaExp;
	}
	public void setIntTamannoListaExp(Integer intTamannoListaExp) {
		this.intTamannoListaExp = intTamannoListaExp;
	}
	public CuentaIntegrante getCuentaIntegrante() {
		return cuentaIntegrante;
	}
	public void setCuentaIntegrante(CuentaIntegrante cuentaIntegrante) {
		this.cuentaIntegrante = cuentaIntegrante;
	}
	public String getStrDescripcionSituacionCuenta() {
		return strDescripcionSituacionCuenta;
	}
	public void setStrDescripcionSituacionCuenta(
			String strDescripcionSituacionCuenta) {
		this.strDescripcionSituacionCuenta = strDescripcionSituacionCuenta;
	}
	public CuentaConcepto getCuentaConceptoAportes() {
		return cuentaConceptoAportes;
	}
	public void setCuentaConceptoAportes(CuentaConcepto cuentaConceptoAportes) {
		this.cuentaConceptoAportes = cuentaConceptoAportes;
	}
	public CuentaConcepto getCuentaConceptoRetiro() {
		return cuentaConceptoRetiro;
	}
	public void setCuentaConceptoRetiro(CuentaConcepto cuentaConceptoRetiro) {
		this.cuentaConceptoRetiro = cuentaConceptoRetiro;
	}
	public CuentaConcepto getCuentaConceptoSepelio() {
		return cuentaConceptoSepelio;
	}
	public void setCuentaConceptoSepelio(CuentaConcepto cuentaConceptoSepelio) {
		this.cuentaConceptoSepelio = cuentaConceptoSepelio;
	}
	public CuentaConcepto getCuentaConceptoMantenimientoCuenta() {
		return cuentaConceptoMantenimientoCuenta;
	}
	public void setCuentaConceptoMantenimientoCuenta(
			CuentaConcepto cuentaConceptoMantenimientoCuenta) {
		this.cuentaConceptoMantenimientoCuenta = cuentaConceptoMantenimientoCuenta;
	}
	public CuentaConcepto getCuentaConceptoDeposito() {
		return cuentaConceptoDeposito;
	}
	public void setCuentaConceptoDeposito(CuentaConcepto cuentaConceptoDeposito) {
		this.cuentaConceptoDeposito = cuentaConceptoDeposito;
	}
	public String getStrFechaRenuncia() {
		return strFechaRenuncia;
	}
	public void setStrFechaRenuncia(String strFechaRenuncia) {
		this.strFechaRenuncia = strFechaRenuncia;
	}
	
}
