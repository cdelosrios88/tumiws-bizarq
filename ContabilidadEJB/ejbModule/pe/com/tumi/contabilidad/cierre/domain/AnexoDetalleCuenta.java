package pe.com.tumi.contabilidad.cierre.domain;


import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AnexoDetalleCuenta extends TumiDomain{
	AnexoDetalleCuentaId id;
	
	//Agregado por cdelosrios, 15/09/2013
	private AnexoDetalle anexoDetalle;
	private String strTexto;
	private Integer intParaEstadoCod;
	//Fin agregado por cdelosrios, 15/09/2013
	
	//interfaz
	PlanCuenta planCuenta;
	
	public AnexoDetalleCuenta(){
		id = new AnexoDetalleCuentaId();
	}
	public PlanCuenta getPlanCuenta() {
		return planCuenta;
	}
	public void setPlanCuenta(PlanCuenta planCuenta) {
		this.planCuenta = planCuenta;
	}
	public AnexoDetalleCuentaId getId() {
		return id;
	}
	public void setId(AnexoDetalleCuentaId id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "AnexoDetalleCuenta [id=" + id + "]";
	}
	
	//Agregado por cdelosrios, 15/09/2013
	public AnexoDetalle getAnexoDetalle() {
		return anexoDetalle;
	}
	public void setAnexoDetalle(AnexoDetalle anexoDetalle) {
		this.anexoDetalle = anexoDetalle;
	}
	public String getStrTexto() {
		return strTexto;
	}
	public void setStrTexto(String strTexto) {
		this.strTexto = strTexto;
	}	
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	//Fin agregado por cdelosrios, 15/09/2013
}