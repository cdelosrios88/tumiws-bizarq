package pe.com.tumi.common.domain;

import java.io.Serializable;
import java.util.Date;

import pe.com.tumi.common.util.DateHelper;

public abstract class EntidadBase implements Serializable {

	private static final long serialVersionUID = 6125789787242234720L;

	private Long id;
	private Date fechaCreacion;
	private Long usuarioCreacion;
	private Date fechaModificacion;
	private Long usuarioModificacion;
	private Boolean seleccionado;

	public EntidadBase() {
	}

	public Boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void auditDataPP() {
		setFechaCreacion(DateHelper.getFechaActual());
	}

	public void auditDataPU() {
		setFechaModificacion(DateHelper.getFechaActual());
	}

	public void setUsuarioCreacion(Long usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Long getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}
}