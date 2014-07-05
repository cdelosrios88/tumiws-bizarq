package pe.com.tumi.seguridad.domain;

import java.util.Date;

public class LoginLog {
	
	private Long id;
	private String tipoIntento;
	private Long usuario_id;
	private String ip;
	private String host;
	private Date fechaIntento;
	private Long sistema_id;
	
	public Long getId() {
		return id;
	}

	public Long setId(Long id) {
		return this.id = id;
	}
	
	public String getTipoIntento() {
		return tipoIntento;
	}
	public void setTipoIntento(String tipoIntento) {
		this.tipoIntento = tipoIntento;
	}

	public Long getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setFechaIntento(Date fechaIntento) {
		this.fechaIntento = fechaIntento;
	}

	public Date getFechaIntento() {
		return fechaIntento;
	}

	public void setSistema_id(Long sistema_id) {
		this.sistema_id = sistema_id;
	}

	public Long getSistema_id() {
		return sistema_id;
	}	
}