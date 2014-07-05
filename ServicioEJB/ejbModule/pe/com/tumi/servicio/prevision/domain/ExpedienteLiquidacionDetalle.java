package pe.com.tumi.servicio.prevision.domain;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;

public class ExpedienteLiquidacionDetalle extends TumiDomain{

	private ExpedienteLiquidacionDetalleId	id;
	private	BigDecimal 	bdSaldo;
	
	private Cuenta cuenta;
	private List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacion;
	private CuentaConcepto cuentaConcepto;
	
	private BeneficiarioLiquidacion beneficiarioLiquidacion;
	
	public ExpedienteLiquidacionDetalle(){
		id = new ExpedienteLiquidacionDetalleId();
	}
	public ExpedienteLiquidacionDetalleId getId() {
		return id;
	}
	public void setId(ExpedienteLiquidacionDetalleId id) {
		this.id = id;
	}
	public BigDecimal getBdSaldo() {
		return bdSaldo;
	}
	public void setBdSaldo(BigDecimal bdSaldo) {
		this.bdSaldo = bdSaldo;
	}
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	public List<BeneficiarioLiquidacion> getListaBeneficiarioLiquidacion() {
		return listaBeneficiarioLiquidacion;
	}
	public void setListaBeneficiarioLiquidacion(List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacion) {
		this.listaBeneficiarioLiquidacion = listaBeneficiarioLiquidacion;
	}
	public CuentaConcepto getCuentaConcepto() {
		return cuentaConcepto;
	}
	public void setCuentaConcepto(CuentaConcepto cuentaConcepto) {
		this.cuentaConcepto = cuentaConcepto;
	}
	@Override
	public String toString() {
		return "ExpedienteLiquidacionDetalle [id=" + id + ", bdSaldo="
				+ bdSaldo + "]";
	}
	public BeneficiarioLiquidacion getBeneficiarioLiquidacion() {
		return beneficiarioLiquidacion;
	}
	public void setBeneficiarioLiquidacion(
			BeneficiarioLiquidacion beneficiarioLiquidacion) {
		this.beneficiarioLiquidacion = beneficiarioLiquidacion;
	}
}