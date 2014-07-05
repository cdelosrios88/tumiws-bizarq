package pe.com.tumi.empresa.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.seguridad.permiso.domain.Computadora;

public class Area extends TumiDomain{
	private 	AreaId 		id;
	private 	String 		strDescripcion;
	private 	String 		strAbreviatura;
	private 	Integer 	intIdTipoArea;
	private 	Integer		intIdEstado;

	private 	List<AreaCodigo> 	listaAreaCodigo;
	private 	List<Computadora> 	listaComputadora;
	
	public Area(){
		super();
		setId(new AreaId());
	}
	
	public AreaId getId() {
		return id;
	}
	public void setId(AreaId id) {
		this.id = id;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Integer getIntIdTipoArea() {
		return intIdTipoArea;
	}
	public void setIntIdTipoArea(Integer intIdTipoArea) {
		this.intIdTipoArea = intIdTipoArea;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public String getStrAbreviatura() {
		return strAbreviatura;
	}
	public void setStrAbreviatura(String strAbreviatura) {
		this.strAbreviatura = strAbreviatura;
	}
	public List<AreaCodigo> getListaAreaCodigo() {
		return listaAreaCodigo;
	}
	public void setListaAreaCodigo(List<AreaCodigo> listaAreaCodigo) {
		this.listaAreaCodigo = listaAreaCodigo;
	}
	public List<Computadora> getListaComputadora() {
		return listaComputadora;
	}
	public void setListaComputadora(List<Computadora> listaComputadora) {
		this.listaComputadora = listaComputadora;
	}
	

	@Override
	public String toString() {
		return "Area [id=" + id + ", strDescripcion=" + strDescripcion
				+ ", strAbreviatura=" + strAbreviatura + ", intIdTipoArea="
				+ intIdTipoArea + ", intIdEstado=" + intIdEstado + "]";
	}
}
