package pe.com.tumi.seguridad.permiso.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SolicitudCambio extends TumiDomain {

	private SolicitudCambioId id;
	private Transaccion transaccion;
	private String strDescripcion;
	private String strJustificacion;
	private String strFinalidad;
	//private String strAnexo;
	private List<SolCambioDiccionario> listaSolicitudCambioDetalle;
	public SolicitudCambioId getId() {
		return id;
	}
	public void setId(SolicitudCambioId id) {
		this.id = id;
	}
	public Transaccion getTransaccion() {
		return transaccion;
	}
	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public String getStrJustificacion() {
		return strJustificacion;
	}
	public void setStrJustificacion(String strJustificacion) {
		this.strJustificacion = strJustificacion;
	}
	public String getStrFinalidad() {
		return strFinalidad;
	}
	public void setStrFinalidad(String strFinalidad) {
		this.strFinalidad = strFinalidad;
	}
	/*public String getStrAnexo() {
		return strAnexo;
	}
	public void setStrAnexo(String strAnexo) {
		this.strAnexo = strAnexo;
	}*/
	public List<SolCambioDiccionario> getListaSolicitudCambioDetalle() {
		return listaSolicitudCambioDetalle;
	}
	public void setListaSolicitudCambioDetalle(
			List<SolCambioDiccionario> listaSolicitudCambioDetalle) {
		this.listaSolicitudCambioDetalle = listaSolicitudCambioDetalle;
	}

}
