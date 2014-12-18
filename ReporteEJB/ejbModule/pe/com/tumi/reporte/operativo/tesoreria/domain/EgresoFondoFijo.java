package pe.com.tumi.reporte.operativo.tesoreria.domain;


import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EgresoFondoFijo extends TumiDomain{
	private String strNroMovimiento;
	private String strFechaEgreso;
	private String strConcepto;
	private BigDecimal bdMontoReporte;
	private Integer intItemEgresoGeneral;
	
	
	public BigDecimal getBdMontoReporte() {
		return bdMontoReporte;
	}
	public void setBdMontoReporte(BigDecimal bdMontoReporte) {
		this.bdMontoReporte = bdMontoReporte;
	}
	public String getStrFechaEgreso() {
		return strFechaEgreso;
	}
	public void setStrFechaEgreso(String strFechaEgreso) {
		this.strFechaEgreso = strFechaEgreso;
	}
	public String getStrNroMovimiento() {
		return strNroMovimiento;
	}
	public void setStrNroMovimiento(String strNroMovimiento) {
		this.strNroMovimiento = strNroMovimiento;
	}
	public String getStrConcepto() {
		return strConcepto;
	}
	public void setStrConcepto(String strConcepto) {
		this.strConcepto = strConcepto;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	
}
