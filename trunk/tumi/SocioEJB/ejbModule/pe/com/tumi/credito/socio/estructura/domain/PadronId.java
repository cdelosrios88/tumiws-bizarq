package pe.com.tumi.credito.socio.estructura.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PadronId extends TumiDomain {

	private Integer intPeriodo;
	private Integer intMes;
	private Integer intNivel;
	private Integer intCodigo;
	private Integer intParaTipoArchivoPadronCod;
	private Integer intParaModalidadCod;
	private Integer intParaTipoSocioCod;
	private Integer intItemAdministraPadron;
	private Integer intItem;
	
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
	public Integer getIntItemAdministraPadron() {
		return intItemAdministraPadron;
	}
	public void setIntItemAdministraPadron(Integer intItemAdministraPadron) {
		this.intItemAdministraPadron = intItemAdministraPadron;
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
	public Integer getIntParaTipoSocioCod() {
		return intParaTipoSocioCod;
	}
	public void setIntParaTipoSocioCod(Integer intParaTipoSocioCod) {
		this.intParaTipoSocioCod = intParaTipoSocioCod;
	}
	public Integer getIntItem() {
		return intItem;
	}
	public void setIntItem(Integer intItem) {
		this.intItem = intItem;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PadronId [intPeriodo=");
		builder.append(intPeriodo);
		builder.append(", intMes=");
		builder.append(intMes);
		builder.append(", intNivel=");
		builder.append(intNivel);
		builder.append(", intCodigo=");
		builder.append(intCodigo);
		builder.append(", intParaTipoArchivoCod=");
		builder.append(intParaTipoArchivoPadronCod);
		builder.append(", intParaModalidadCod=");
		builder.append(intParaModalidadCod);
		builder.append(", intParaTipoSocioCod=");
		builder.append(intParaTipoSocioCod);
		builder.append(", intItemAdministraPadron=");
		builder.append(intItemAdministraPadron);
		builder.append(", intItem=");
		builder.append(intItem);
		builder.append("]");
		return builder.toString();
	}

}
