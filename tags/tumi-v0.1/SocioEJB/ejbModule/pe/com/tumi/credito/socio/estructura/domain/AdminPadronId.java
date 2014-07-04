package pe.com.tumi.credito.socio.estructura.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AdminPadronId extends TumiDomain {

	private Integer intPeriodo;
	private Integer intMes;
	private Integer intNivel;
	private Integer intCodigo;
	private Integer intParaTipoArchivoPadronCod;
	private Integer intParaModalidadCod;
	private Integer intParaTipoSocioCod;
	private Integer intItemAdministraPadron;
	
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}
	public Integer getIntMes() {
		return intMes;
	}
	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}
	public Integer getIntNivel() {
		return intNivel;
	}
	public void setIntNivel(Integer intNivel) {
		this.intNivel = intNivel;
	}
	public Integer getIntCodigo() {
		return intCodigo;
	}
	public void setIntCodigo(Integer intCodigo) {
		this.intCodigo = intCodigo;
	}
	public Integer getIntParaModalidadCod() {
		return intParaModalidadCod;
	}
	public void setIntParaModalidadCod(Integer intParaModalidadCod) {
		this.intParaModalidadCod = intParaModalidadCod;
	}

	public Integer getIntParaTipoSocioCod() {
		return intParaTipoSocioCod;
	}
	public void setIntParaTipoSocioCod(Integer intParaTipoSocioCod) {
		this.intParaTipoSocioCod = intParaTipoSocioCod;
	}
	public Integer getIntItemAdministraPadron() {
		return intItemAdministraPadron;
	}
	public void setIntItemAdministraPadron(Integer intItemAdministraPadron) {
		this.intItemAdministraPadron = intItemAdministraPadron;
	}
	public Integer getIntParaTipoArchivoPadronCod() {
		return intParaTipoArchivoPadronCod;
	}
	public void setIntParaTipoArchivoPadronCod(Integer intParaTipoArchivoPadronCod) {
		this.intParaTipoArchivoPadronCod = intParaTipoArchivoPadronCod;
	}
	@Override
	public String toString() {
		return "AdminPadronId [intPeriodo=" + intPeriodo + ", intMes=" + intMes
				+ ", intNivel=" + intNivel + ", intCodigo=" + intCodigo
				+ ", intParaTipoArchivoPadronCod="
				+ intParaTipoArchivoPadronCod + ", intParaModalidadCod="
				+ intParaModalidadCod + ", intParaTipoSocioCod="
				+ intParaTipoSocioCod + ", intItemAdministraPadron="
				+ intItemAdministraPadron + "]";
	}
	
}
