package pe.com.tumi.tesoreria.logistica.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatOrdenComDocId extends TumiDomain{
	private Integer intPersEmpresaDocSunatOrden;	//PERS_EMPRESADOCSUNORD_N_PK
	private Integer intItemEmpresaDocSunatOrden;	//TESO_ITEMEMPRESADOCSUNORD_N			
	
	public Integer getIntPersEmpresaDocSunatOrden() {
		return intPersEmpresaDocSunatOrden;
	}
	public void setIntPersEmpresaDocSunatOrden(Integer intPersEmpresaDocSunatOrden) {
		this.intPersEmpresaDocSunatOrden = intPersEmpresaDocSunatOrden;
	}
	public Integer getIntItemEmpresaDocSunatOrden() {
		return intItemEmpresaDocSunatOrden;
	}
	public void setIntItemEmpresaDocSunatOrden(Integer intItemEmpresaDocSunatOrden) {
		this.intItemEmpresaDocSunatOrden = intItemEmpresaDocSunatOrden;
	}
}
