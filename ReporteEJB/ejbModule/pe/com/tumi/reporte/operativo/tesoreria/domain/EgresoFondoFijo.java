package pe.com.tumi.reporte.operativo.tesoreria.domain;


import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EgresoFondoFijo extends TumiDomain{
	private String strNroMovimiento;
	private Date dtFechaEgreso;
	private String strConcepto;
	private Double dblMontoReporte;
	private Integer intItemEgresoGeneral;
	public String getStrNroMovimiento() {
		return strNroMovimiento;
	}
	public void setStrNroMovimiento(String strNroMovimiento) {
		this.strNroMovimiento = strNroMovimiento;
	}
	public Date getDtFechaEgreso() {
		return dtFechaEgreso;
	}
	public void setDtFechaEgreso(Date dtFechaEgreso) {
		this.dtFechaEgreso = dtFechaEgreso;
	}
	public String getStrConcepto() {
		return strConcepto;
	}
	public void setStrConcepto(String strConcepto) {
		this.strConcepto = strConcepto;
	}
	public Double getDblMontoReporte() {
		return dblMontoReporte;
	}
	public void setDblMontoReporte(Double dblMontoReporte) {
		this.dblMontoReporte = dblMontoReporte;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	
}
