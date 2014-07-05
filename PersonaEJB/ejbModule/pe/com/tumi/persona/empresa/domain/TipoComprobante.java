package pe.com.tumi.persona.empresa.domain;

import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.tabla.domain.Tabla;

public class TipoComprobante extends TumiDomain{

	private TipoComprobantePK id;
	private Integer intTipoComprobanteCod;
	private Integer intEstadoCod;
	private Timestamp tsFechaEliminacion;
	private	Tabla	tabla;
	private Boolean blnChecked;
	private Juridica juridica;
	
	
	public TipoComprobantePK getId() {
		return id;
	}
	public void setId(TipoComprobantePK id) {
		this.id = id;
	}
	public Integer getIntTipoComprobanteCod() {
		return intTipoComprobanteCod;
	}
	public void setIntTipoComprobanteCod(Integer intTipoComprobanteCod) {
		this.intTipoComprobanteCod = intTipoComprobanteCod;
	}
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Tabla getTabla() {
		return tabla;
	}
	public void setTabla(Tabla tabla) {
		this.tabla = tabla;
	}
	public Boolean getBlnChecked() {
		return blnChecked;
	}
	public void setBlnChecked(Boolean blnChecked) {
		this.blnChecked = blnChecked;
	}
	public Juridica getJuridica() {
		return juridica;
	}
	public void setJuridica(Juridica juridica) {
		this.juridica = juridica;
	}
	@Override
	public String toString() {
		return "TipoComprobante [id=" + id + ", intTipoComprobanteCod="
				+ intTipoComprobanteCod + ", intEstadoCod=" + intEstadoCod
				+ ", tsFechaEliminacion=" + tsFechaEliminacion + "]";
	}
	
}