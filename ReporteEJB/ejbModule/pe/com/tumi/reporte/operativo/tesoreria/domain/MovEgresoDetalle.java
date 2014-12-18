package pe.com.tumi.reporte.operativo.tesoreria.domain;


import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class MovEgresoDetalle extends TumiDomain{
	private Integer intEmpresaEgreso;
	private Integer intItemEgresoGeneral;
	private Integer intParaMoneda;
	private String strDescMoneda;
	private String strDescEgreso;
	private Integer intIdSucursal;
	private String strSucursal;
	private BigDecimal bdMontoDebe;
	private BigDecimal bdMontoAbono;
	
	public Integer getIntEmpresaEgreso() {
		return intEmpresaEgreso;
	}
	public void setIntEmpresaEgreso(Integer intEmpresaEgreso) {
		this.intEmpresaEgreso = intEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public Integer getIntParaMoneda() {
		return intParaMoneda;
	}
	public void setIntParaMoneda(Integer intParaMoneda) {
		this.intParaMoneda = intParaMoneda;
	}
	public String getStrDescMoneda() {
		return strDescMoneda;
	}
	public void setStrDescMoneda(String strDescMoneda) {
		this.strDescMoneda = strDescMoneda;
	}
	public String getStrDescEgreso() {
		return strDescEgreso;
	}
	public void setStrDescEgreso(String strDescEgreso) {
		this.strDescEgreso = strDescEgreso;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public String getStrSucursal() {
		return strSucursal;
	}
	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}
	public BigDecimal getBdMontoDebe() {
		return bdMontoDebe;
	}
	public void setBdMontoDebe(BigDecimal bdMontoDebe) {
		this.bdMontoDebe = bdMontoDebe;
	}
	public BigDecimal getBdMontoAbono() {
		return bdMontoAbono;
	}
	public void setBdMontoAbono(BigDecimal bdMontoAbono) {
		this.bdMontoAbono = bdMontoAbono;
	}
}