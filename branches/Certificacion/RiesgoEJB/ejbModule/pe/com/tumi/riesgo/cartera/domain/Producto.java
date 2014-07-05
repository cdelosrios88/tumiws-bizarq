package pe.com.tumi.riesgo.cartera.domain;

public class Producto {

	private ProductoId id;
	private Integer intParaEstadoCod;
	
	public Producto(){
		id = new ProductoId();
	}

	public ProductoId getId() {
		return id;
	}

	public void setId(ProductoId id) {
		this.id = id;
	}

	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}

	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", intParaEstadoCod=" + intParaEstadoCod
				+ "]";
	}
	
}