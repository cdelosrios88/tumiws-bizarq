package pe.com.tumi.riesgo.cartera.domain;

public class ProductoId {

	private Integer intItemCartera;
	private Integer intParaTipoOperacionCod;
	private Integer intParaTipoConceptoCod;
	public Integer getIntItemCartera() {
		return intItemCartera;
	}
	public void setIntItemCartera(Integer intItemCartera) {
		this.intItemCartera = intItemCartera;
	}
	public Integer getIntParaTipoOperacionCod() {
		return intParaTipoOperacionCod;
	}
	public void setIntParaTipoOperacionCod(Integer intParaTipoOperacionCod) {
		this.intParaTipoOperacionCod = intParaTipoOperacionCod;
	}
	public Integer getIntParaTipoConceptoCod() {
		return intParaTipoConceptoCod;
	}
	public void setIntParaTipoConceptoCod(Integer intParaTipoConcepto) {
		this.intParaTipoConceptoCod = intParaTipoConcepto;
	}
	@Override
	public String toString() {
		return "ProductoId [intItemCartera=" + intItemCartera
				+ ", intParaTipoOperacionCod=" + intParaTipoOperacionCod
				+ ", intParaTipoConcepto=" + intParaTipoConceptoCod + "]";
	}
}
