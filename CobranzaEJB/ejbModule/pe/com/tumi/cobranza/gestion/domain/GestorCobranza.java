package pe.com.tumi.cobranza.gestion.domain;

import java.util.Date;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class GestorCobranza  extends TumiDomain{
	
	private GestorCobranzaId id;
	private Integer intTipoGestorCod;
	private Date  fechaInicio;
	private Date  fechaFin;
	private Sucursal sucursal;
	
	private Persona persona;
	private	Date dtFechaActual;
	
	public GestorCobranza(){
		super();
		setId(new GestorCobranzaId());
	}
	
	public GestorCobranzaId getId() {
		return id;
	}
	public void setId(GestorCobranzaId id) {
		this.id = id;
	}
	public Integer getIntTipoGestorCod() {
		return intTipoGestorCod;
	}
	public void setIntTipoGestorCod(Integer intTipoGestorCod) {
		this.intTipoGestorCod = intTipoGestorCod;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Date getDtFechaActual() {
		return dtFechaActual;
	}
	public void setDtFechaActual(Date dtFechaActual) {
		this.dtFechaActual = dtFechaActual;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "GestorCobranza [id=" + id + ", intTipoGestorCod="
				+ intTipoGestorCod + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", sucursal=" + sucursal
				+ ", dtFechaActual=" + dtFechaActual + "]";
	}
}