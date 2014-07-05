package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatEgreso extends TumiDomain{

	private DocumentoSunatEgresoId	id;
	private Integer intParaTipoDocumentoGeneral;
	private Integer bdMontoDescuento;
	private Integer intParaEstadoPago;
	private Integer intPersEmpresaEgreso;
	private Integer intItemEgresoGeneral;
	
	public DocumentoSunatEgreso(){
		id = new DocumentoSunatEgresoId();
	}
	
	public DocumentoSunatEgresoId getId() {
		return id;
	}
	public void setId(DocumentoSunatEgresoId id) {
		this.id = id;
	}
	public Integer getIntParaTipoDocumentoGeneral() {
		return intParaTipoDocumentoGeneral;
	}
	public void setIntParaTipoDocumentoGeneral(Integer intParaTipoDocumentoGeneral) {
		this.intParaTipoDocumentoGeneral = intParaTipoDocumentoGeneral;
	}
	public Integer getBdMontoDescuento() {
		return bdMontoDescuento;
	}
	public void setBdMontoDescuento(Integer bdMontoDescuento) {
		this.bdMontoDescuento = bdMontoDescuento;
	}
	public Integer getIntParaEstadoPago() {
		return intParaEstadoPago;
	}
	public void setIntParaEstadoPago(Integer intParaEstadoPago) {
		this.intParaEstadoPago = intParaEstadoPago;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	
	@Override
	public String toString() {
		return "DocumentoSunatEgreso [id=" + id
				+ ", intParaTipoDocumentoGeneral="
				+ intParaTipoDocumentoGeneral + ", bdMontoDescuento="
				+ bdMontoDescuento + ", intParaEstadoPago=" + intParaEstadoPago
				+ ", intPersEmpresaEgreso=" + intPersEmpresaEgreso
				+ ", intItemEgresoGeneral=" + intItemEgresoGeneral + "]";
	}
}
