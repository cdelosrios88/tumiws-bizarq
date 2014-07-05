package pe.com.tumi.riesgo.cartera.domain;

public class Tiempo {

	private TiempoId id;
	private Integer intItemCartera;
	private Integer intItemTipoCategoriaRiesgoCod;	
	private Integer intTiempo;
	private Integer intParaEstadoCod;
	// CGD-SE RETIRARON ATRIBUTOS DE PRODUCTO
	//private Integer intParaTipoOperacion;
	//private Integer intParaTipoConcepto;
	
	public Tiempo(){
		id = new TiempoId();
	}
	public TiempoId getId() {
		return id;
	}
	public void setId(TiempoId id) {
		this.id = id;
	}
	public Integer getIntItemCartera() {
		return intItemCartera;
	}
	public void setIntItemCartera(Integer intItemCartera) {
		this.intItemCartera = intItemCartera;
	}
	public Integer getIntItemTipoCategoriaRiesgoCod() {
		return intItemTipoCategoriaRiesgoCod;
	}
	public void setIntItemTipoCategoriaRiesgoCod(
			Integer intItemTipoCategoriaRiesgoCod) {
		this.intItemTipoCategoriaRiesgoCod = intItemTipoCategoriaRiesgoCod;
	}
	public Integer getIntTiempo() {
		return intTiempo;
	}
	public void setIntTiempo(Integer intTiempo) {
		this.intTiempo = intTiempo;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}

	@Override
	public String toString() {
		return "Tiempo [id=" + id + ", intItemCartera=" + intItemCartera
				+ ", intItemTipoCategoriaRiesgoCod="
				+ intItemTipoCategoriaRiesgoCod + ", intTiempo=" + intTiempo
				+ ", intParaEstadoCod=" + intParaEstadoCod+ "]";
				//+ ", intParaTipoOperacion=" + intParaTipoOperacion
				//+ ", intParaTipoConcepto=" + intParaTipoConcepto + "]";
	}
}
