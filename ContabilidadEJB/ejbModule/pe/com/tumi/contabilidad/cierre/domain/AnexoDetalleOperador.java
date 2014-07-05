package pe.com.tumi.contabilidad.cierre.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AnexoDetalleOperador extends TumiDomain{

	private AnexoDetalleOperadorId id;
	private	Integer	intItemAnexoDetalleOperador;
	private	Integer	intTipoOperacion;
	
	//interfaz
	private AnexoDetalle anexoDetalleReferencia;
	
	public AnexoDetalleOperador(){
		super();
		id = new AnexoDetalleOperadorId();
	}

	public AnexoDetalleOperadorId getId() {
		return id;
	}
	public void setId(AnexoDetalleOperadorId id) {
		this.id = id;
	}
	public Integer getIntItemAnexoDetalleOperador() {
		return intItemAnexoDetalleOperador;
	}
	public void setIntItemAnexoDetalleOperador(Integer intItemAnexoDetalleOperador) {
		this.intItemAnexoDetalleOperador = intItemAnexoDetalleOperador;
	}
	public Integer getIntTipoOperacion() {
		return intTipoOperacion;
	}
	public void setIntTipoOperacion(Integer intTipoOperacion) {
		this.intTipoOperacion = intTipoOperacion;
	}
	public AnexoDetalle getAnexoDetalleReferencia() {
		return anexoDetalleReferencia;
	}
	public void setAnexoDetalleReferencia(AnexoDetalle anexoDetalleReferencia) {
		this.anexoDetalleReferencia = anexoDetalleReferencia;
	}

	@Override
	public String toString() {
		return "AnexoDetalleOperador [id=" + id
				+ ", intItemAnexoDetalleOperador="
				+ intItemAnexoDetalleOperador + ", intTipoOperacion="
				+ intTipoOperacion + "]";
	}
}