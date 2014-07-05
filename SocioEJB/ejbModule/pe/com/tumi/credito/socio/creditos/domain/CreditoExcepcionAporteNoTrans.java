package pe.com.tumi.credito.socio.creditos.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CreditoExcepcionAporteNoTrans extends TumiDomain {
	private CreditoExcepcionAporteNoTransId  id;
	private Integer 						intValor;
	
	public CreditoExcepcionAporteNoTransId getId() {
		return id;
	}
	public void setId(CreditoExcepcionAporteNoTransId id) {
		this.id = id;
	}
	public Integer getIntValor() {
		return intValor;
	}
	public void setIntValor(Integer intValor) {
		this.intValor = intValor;
	}
}
