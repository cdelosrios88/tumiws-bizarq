package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatDoc extends TumiDomain{

	private DocumentoSunatDocId	id;
	private Integer intParaTipoDocumentoGeneral;
	private BigDecimal bdMontoDocumento;
	private Integer intParaEstadoPago;
	private Integer intPersEmpresaPago;
	private Integer intItemEgresoGeneral;
	
	public DocumentoSunatDoc(){
		id = new DocumentoSunatDocId();
	}

	public DocumentoSunatDocId getId() {
		return id;
	}

	public void setId(DocumentoSunatDocId id) {
		this.id = id;
	}

	public Integer getIntParaTipoDocumentoGeneral() {
		return intParaTipoDocumentoGeneral;
	}

	public void setIntParaTipoDocumentoGeneral(Integer intParaTipoDocumentoGeneral) {
		this.intParaTipoDocumentoGeneral = intParaTipoDocumentoGeneral;
	}

	public BigDecimal getBdMontoDocumento() {
		return bdMontoDocumento;
	}

	public void setBdMontoDocumento(BigDecimal bdMontoDocumento) {
		this.bdMontoDocumento = bdMontoDocumento;
	}

	public Integer getIntParaEstadoPago() {
		return intParaEstadoPago;
	}

	public void setIntParaEstadoPago(Integer intParaEstadoPago) {
		this.intParaEstadoPago = intParaEstadoPago;
	}

	public Integer getIntPersEmpresaPago() {
		return intPersEmpresaPago;
	}

	public void setIntPersEmpresaPago(Integer intPersEmpresaPago) {
		this.intPersEmpresaPago = intPersEmpresaPago;
	}

	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}

	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
}