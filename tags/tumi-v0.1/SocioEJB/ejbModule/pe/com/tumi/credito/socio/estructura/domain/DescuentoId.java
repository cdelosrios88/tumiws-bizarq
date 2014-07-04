package pe.com.tumi.credito.socio.estructura.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DescuentoId extends TumiDomain {

	private Integer intPeriodo;
	private Integer intMes;
	private Integer intNivel;
	private Integer intCodigo;
	private Integer intParaTipoArchivoPadronCod;
	private Integer intParaModalidadCod;
	private Integer intParaTipoSocio;
	private Integer intItemAdministraPadron;	
	private Integer intItemDescuento;
	
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
	public Integer getIntParaTipoArchivoPadronCod() {
		return intParaTipoArchivoPadronCod;
	}
	public void setIntParaTipoArchivoPadronCod(Integer intParaTipoArchivoCod) {
		this.intParaTipoArchivoPadronCod = intParaTipoArchivoCod;
	}
	public Integer getIntParaModalidadCod() {
		return intParaModalidadCod;
	}
	public void setIntParaModalidadCod(Integer intParaModalidadCod) {
		this.intParaModalidadCod = intParaModalidadCod;
	}
	public Integer getIntParaTipoSocio() {
		return intParaTipoSocio;
	}
	public void setIntParaTipoSocio(Integer intParaTipoSocio) {
		this.intParaTipoSocio = intParaTipoSocio;
	}
	public Integer getIntItemAdministraPadron() {
		return intItemAdministraPadron;
	}
	public void setIntItemAdministraPadron(Integer intItemAdministraPadron) {
		this.intItemAdministraPadron = intItemAdministraPadron;
	}
	public Integer getIntItemDescuento() {
		return intItemDescuento;
	}
	public void setIntItemDescuento(Integer intItemDescuento) {
		this.intItemDescuento = intItemDescuento;
	}
	@Override
	public String toString() {
		return "DescuentoId [intPeriodo=" + intPeriodo + ", intMes=" + intMes
				+ ", intNivel=" + intNivel + ", intCodigo=" + intCodigo
				+ ", intParaTipoArchivoCod=" + intParaTipoArchivoPadronCod
				+ ", intParaModalidadCod=" + intParaModalidadCod
				+ ", intParaTipoSocio=" + intParaTipoSocio
				+ ", intItemAdministraPadron=" + intItemAdministraPadron
				+ ", intItemDescuento=" + intItemDescuento + "]";
	}
	
	
	

}
