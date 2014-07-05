package pe.com.tumi.parametro.general.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class TipoCambioId extends TumiDomain{

	private Integer intPersEmpresa;
	private Date dtParaFecha;
	private Integer intParaClaseTipoCambio;
	private Integer intParaMoneda;
	
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Date getDtParaFecha() {
		return dtParaFecha;
	}
	public void setDtParaFecha(Date dtParaFecha) {
		this.dtParaFecha = dtParaFecha;
	}
	public Integer getIntParaClaseTipoCambio() {
		return intParaClaseTipoCambio;
	}
	public void setIntParaClaseTipoCambio(Integer intParaClaseTipoCambio) {
		this.intParaClaseTipoCambio = intParaClaseTipoCambio;
	}
	public Integer getIntParaMoneda() {
		return intParaMoneda;
	}
	public void setIntParaMoneda(Integer intParaMoneda) {
		this.intParaMoneda = intParaMoneda;
	}
	@Override
	public String toString() {
		return "TipoCambioId [intPersEmpresa=" + intPersEmpresa
				+ ", dtParaFecha=" + dtParaFecha + ", intParaClaseTipoCambio="
				+ intParaClaseTipoCambio + ", intParaMoneda=" + intParaMoneda
				+ "]";
	}	
}