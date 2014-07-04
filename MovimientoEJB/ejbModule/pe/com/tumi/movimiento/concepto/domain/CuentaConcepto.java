package pe.com.tumi.movimiento.concepto.domain;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaConcepto extends TumiDomain{

	private CuentaConceptoId id;
	private BigDecimal bdSaldo;
	private List<CuentaDetalleBeneficio> listaCuentaDetalleBeneficio;
	private List<CuentaConceptoDetalle> listaCuentaConceptoDetalle;
	private CuentaConceptoDetalle detalle;
	private BloqueoCuenta bloqueo;
	private List<EstadoExpediente> listaEstadosExpediente;

	
	public CuentaConceptoId getId() {
		return id;
	}
	public void setId(CuentaConceptoId id) {
		this.id = id;
	}
	public BigDecimal getBdSaldo() {
		return bdSaldo;
	}
	public void setBdSaldo(BigDecimal bdSaldo) {
		this.bdSaldo = bdSaldo;
	}
	public List<CuentaDetalleBeneficio> getListaCuentaDetalleBeneficio() {
		return listaCuentaDetalleBeneficio;
	}
	public void setListaCuentaDetalleBeneficio(
			List<CuentaDetalleBeneficio> listaCuentaDetalleBeneficio) {
		this.listaCuentaDetalleBeneficio = listaCuentaDetalleBeneficio;
	}
	public List<CuentaConceptoDetalle> getListaCuentaConceptoDetalle() {
		return listaCuentaConceptoDetalle;
	}
	public void setListaCuentaConceptoDetalle(
			List<CuentaConceptoDetalle> listaCuentaConceptoDetalle) {
		this.listaCuentaConceptoDetalle = listaCuentaConceptoDetalle;
	}
	public CuentaConceptoDetalle getDetalle() {
		return detalle;
	}
	public void setDetalle(CuentaConceptoDetalle detalle) {
		this.detalle = detalle;
	}
	public BloqueoCuenta getBloqueo() {
		return bloqueo;
	}
	public void setBloqueo(BloqueoCuenta bloqueo) {
		this.bloqueo = bloqueo;
	}
	public List<EstadoExpediente> getListaEstadosExpediente() {
		return listaEstadosExpediente;
	}
	public void setListaEstadosExpediente(
			List<EstadoExpediente> listaEstadosExpediente) {
		this.listaEstadosExpediente = listaEstadosExpediente;
	}
	

	
	@Override
	public String toString() {
		return "CuentaConcepto [id=" + id + ", bdSaldo="
				+ bdSaldo + "]";
	}
	
}
