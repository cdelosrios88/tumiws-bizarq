package pe.com.tumi.seguridad.permiso.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Computadora  extends TumiDomain {

	private ComputadoraId id;
	private String strIdentificador;
	private Date dtFechaActivacion;
	private Integer intIdEstado;
	private Timestamp tsFechaEliminacion;
	private Sucursal sucursal;
	private Area area;
	private List<ComputadoraAcceso> listaComputadoraAcceso;
	
	public Computadora(){
		super();
		setId(new ComputadoraId());
	}
	
	public ComputadoraId getId() {
		return id;
	}
	public void setId(ComputadoraId id) {
		this.id = id;
	}
	public String getStrIdentificador() {
		return strIdentificador;
	}
	public void setStrIdentificador(String strIdentificador) {
		this.strIdentificador = strIdentificador;
	}
	public Date getDtFechaActivacion() {
		return dtFechaActivacion;
	}
	public void setDtFechaActivacion(Date dtFechaActivacion) {
		this.dtFechaActivacion = dtFechaActivacion;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public List<ComputadoraAcceso> getListaComputadoraAcceso() {
		return listaComputadoraAcceso;
	}
	public void setListaComputadoraAcceso(
			List<ComputadoraAcceso> listaComputadoraAcceso) {
		this.listaComputadoraAcceso = listaComputadoraAcceso;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}	
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	@Override
	public String toString() {
		return "Computadora [id=" + id + ", strIdentificador="
				+ strIdentificador + ", dtFechaActivacion=" + dtFechaActivacion
				+ ", intIdEstado=" + intIdEstado + ", tsFechaEliminacion="
				+ tsFechaEliminacion + ", area=" + area
				+ ", listaComputadoraAcceso=" + listaComputadoraAcceso + "]";
	}


}
