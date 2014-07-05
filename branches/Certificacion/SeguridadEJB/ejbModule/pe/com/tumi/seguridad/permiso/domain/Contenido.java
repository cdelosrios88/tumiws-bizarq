package pe.com.tumi.seguridad.permiso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Contenido extends TumiDomain {

	private ContenidoId id;
	private Contenido contenido;
	private Transaccion transaccion;
	public ContenidoId getId() {
		return id;
	}
	public void setId(ContenidoId id) {
		this.id = id;
	}
	public Contenido getContenido() {
		return contenido;
	}
	public void setContenido(Contenido contenido) {
		this.contenido = contenido;
	}
	public Transaccion getTransaccion() {
		return transaccion;
	}
	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}
	
}
