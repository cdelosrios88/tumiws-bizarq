package pe.com.tumi.parametro.general.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class TarifaId extends TumiDomain{

	private Integer intPersEmpresaTarifa;
	private Integer intParaTipoTarifaCod;
	private Date dtParaTarifaDesde;
	
	public Integer getIntPersEmpresaTarifa() {
		return intPersEmpresaTarifa;
	}
	public void setIntPersEmpresaTarifa(Integer intPersEmpresaTarifa) {
		this.intPersEmpresaTarifa = intPersEmpresaTarifa;
	}
	public Integer getIntParaTipoTarifaCod() {
		return intParaTipoTarifaCod;
	}
	public void setIntParaTipoTarifaCod(Integer intParaTipoTarifaCod) {
		this.intParaTipoTarifaCod = intParaTipoTarifaCod;
	}
	public Date getDtParaTarifaDesde() {
		return dtParaTarifaDesde;
	}
	public void setDtParaTarifaDesde(Date dtParaTarifaDesde) {
		this.dtParaTarifaDesde = dtParaTarifaDesde;
	}
}
