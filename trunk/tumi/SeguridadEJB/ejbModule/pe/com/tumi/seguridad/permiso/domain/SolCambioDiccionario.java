package pe.com.tumi.seguridad.permiso.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SolCambioDiccionario extends TumiDomain {

	private SolCambioDiccionarioId id;
	private Diccionario diccionario;
	private SolicitudCambio solicitudCambio;
	public SolCambioDiccionarioId getId() {
		return id;
	}
	public void setId(SolCambioDiccionarioId id) {
		this.id = id;
	}
	public Diccionario getDiccionario() {
		return diccionario;
	}
	public void setDiccionario(Diccionario diccionario) {
		this.diccionario = diccionario;
	}
	public SolicitudCambio getSolicitudCambio() {
		return solicitudCambio;
	}
	public void setSolicitudCambio(SolicitudCambio solicitudCambio) {
		this.solicitudCambio = solicitudCambio;
	}
	
}
