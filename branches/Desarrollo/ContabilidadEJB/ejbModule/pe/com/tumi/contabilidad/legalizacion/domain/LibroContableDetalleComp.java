package pe.com.tumi.contabilidad.legalizacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

@SuppressWarnings("serial")
public class LibroContableDetalleComp  extends TumiDomain {

	private LibroContableDetalle libroContableDetalle;
	private String strLicdPeriodoNombre;
	private String strContLibroLegalizacion;

	//Constructor
	public LibroContableDetalleComp(){
		libroContableDetalle = new LibroContableDetalle();
	}
	
	//Getters & Setters
	public LibroContableDetalle getLibroContableDetalle() {
		return libroContableDetalle;
	}

	public void setLibroContableDetalle(LibroContableDetalle libroContableDetalle) {
		this.libroContableDetalle = libroContableDetalle;
	}

	public String getStrLicdPeriodoNombre() {
		return strLicdPeriodoNombre;
	}

	public void setStrLicdPeriodoNombre(String strLicdPeriodoNombre) {
		this.strLicdPeriodoNombre = strLicdPeriodoNombre;
	}

	public String getStrContLibroLegalizacion() {
		return strContLibroLegalizacion;
	}

	public void setStrContLibroLegalizacion(String strContLibroLegalizacion) {
		this.strContLibroLegalizacion = strContLibroLegalizacion;
	}
}
