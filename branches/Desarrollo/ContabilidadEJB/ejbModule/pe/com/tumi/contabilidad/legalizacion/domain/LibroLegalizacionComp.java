package pe.com.tumi.contabilidad.legalizacion.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

@SuppressWarnings("serial")
public class LibroLegalizacionComp extends TumiDomain{
	private LibroLegalizacion libroLegalizacion;
	private String strJuriRazonSocial;
	private String strPersRuc;
	private Integer intSaldolegalizacion;
	private Integer intParaTipoLibro;
	private String strNombreLibro;
	private Integer intSaldoPendiente;
	private String strPeriodoNombre;
	private LibroContableDetalle libroContableDetalle;
	

	public LibroLegalizacionComp(){
		libroLegalizacion = new LibroLegalizacion();
		libroContableDetalle = new LibroContableDetalle();
	}
	
	public LibroLegalizacion getLibroLegalizacion() {
		return libroLegalizacion;
	}

	public void setLibroLegalizacion(LibroLegalizacion libroLegalizacion) {
		this.libroLegalizacion = libroLegalizacion;
	}

	public Integer getIntSaldolegalizacion() {
		return intSaldolegalizacion;
	}

	public void setIntSaldolegalizacion(Integer intSaldolegalizacion) {
		this.intSaldolegalizacion = intSaldolegalizacion;
	}

	public String getStrJuriRazonSocial() {
		return strJuriRazonSocial;
	}

	public void setStrJuriRazonSocial(String strJuriRazonSocial) {
		this.strJuriRazonSocial = strJuriRazonSocial;
	}

	public String getStrPersRuc() {
		return strPersRuc;
	}

	public void setStrPersRuc(String strPersRuc) {
		this.strPersRuc = strPersRuc;
	}

	public Integer getIntParaTipoLibro() {
		return intParaTipoLibro;
	}

	public void setIntParaTipoLibro(Integer intParaTipoLibro) {
		this.intParaTipoLibro = intParaTipoLibro;
	}

	public String getStrNombreLibro() {
		return strNombreLibro;
	}

	public void setStrNombreLibro(String strNombreLibro) {
		this.strNombreLibro = strNombreLibro;
	}

	public Integer getIntSaldoPendiente() {
		return intSaldoPendiente;
	}

	public void setIntSaldoPendiente(Integer intSaldoPendiente) {
		this.intSaldoPendiente = intSaldoPendiente;
	}

	public String getStrPeriodoNombre() {
		return strPeriodoNombre;
	}

	public void setStrPeriodoNombre(String strPeriodoNombre) {
		this.strPeriodoNombre = strPeriodoNombre;
	}

	public LibroContableDetalle getLibroContableDetalle() {
		return libroContableDetalle;
	}

	public void setLibroContableDetalle(LibroContableDetalle libroContableDetalle) {
		this.libroContableDetalle = libroContableDetalle;
	}
}
