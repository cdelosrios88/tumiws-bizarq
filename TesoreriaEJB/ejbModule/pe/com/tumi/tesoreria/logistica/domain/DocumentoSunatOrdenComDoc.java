package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatOrdenComDoc extends TumiDomain{
	private DocumentoSunatOrdenComDocId id;
	private Integer intPersEmpresaOrden;		//PERS_EMPRESAORDEN_N_PK
	private Integer intItemOrdenCompra;			//TESO_ITEMORDENCOMPRA_N
	private Integer intItemOrdenCompraDoc;		//TESO_ITEMORDENCOMPRADOC_N
	private Integer intPersEmpresaDocSunat;		//PERS_EMPRESADOCSUNAT_N_PK
	private Integer intItemDocumentoSunat;		//TESO_ITEMDOCSUN_N
	private BigDecimal bdMonto;					//EDSO_MONTO_N
	private Integer intParaEstado;				//PARA_ESTADO_N_COD
	private Integer intItemOrdenCompraDetalle;	//TESO_ITEMORDENCOMPRADET_N
	
	public DocumentoSunatOrdenComDocId getId() {
		return id;
	}
	public void setId(DocumentoSunatOrdenComDocId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaOrden() {
		return intPersEmpresaOrden;
	}
	public void setIntPersEmpresaOrden(Integer intPersEmpresaOrden) {
		this.intPersEmpresaOrden = intPersEmpresaOrden;
	}
	public Integer getIntItemOrdenCompra() {
		return intItemOrdenCompra;
	}
	public void setIntItemOrdenCompra(Integer intItemOrdenCompra) {
		this.intItemOrdenCompra = intItemOrdenCompra;
	}
	public Integer getIntItemOrdenCompraDoc() {
		return intItemOrdenCompraDoc;
	}
	public void setIntItemOrdenCompraDoc(Integer intItemOrdenCompraDoc) {
		this.intItemOrdenCompraDoc = intItemOrdenCompraDoc;
	}
	public Integer getIntPersEmpresaDocSunat() {
		return intPersEmpresaDocSunat;
	}
	public void setIntPersEmpresaDocSunat(Integer intPersEmpresaDocSunat) {
		this.intPersEmpresaDocSunat = intPersEmpresaDocSunat;
	}
	public Integer getIntItemDocumentoSunat() {
		return intItemDocumentoSunat;
	}
	public void setIntItemDocumentoSunat(Integer intItemDocumentoSunat) {
		this.intItemDocumentoSunat = intItemDocumentoSunat;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntItemOrdenCompraDetalle() {
		return intItemOrdenCompraDetalle;
	}
	public void setIntItemOrdenCompraDetalle(Integer intItemOrdenCompraDetalle) {
		this.intItemOrdenCompraDetalle = intItemOrdenCompraDetalle;
	}	
}
