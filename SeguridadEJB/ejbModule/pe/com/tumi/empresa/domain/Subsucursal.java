package pe.com.tumi.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Subsucursal extends TumiDomain{
	private	SubSucursalPK 	id;
	private String			strDescripcion;
	private Integer			intIdEstado;
	private Boolean			chkSubSucursal;
	
	//Getters y Setters
	public SubSucursalPK getId() {
		return id;
	}
	public void setId(SubSucursalPK id) {
		this.id = id;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Boolean getChkSubSucursal() {
		return chkSubSucursal;
	}
	public void setChkSubSucursal(Boolean chkSubSucursal) {
		this.chkSubSucursal = chkSubSucursal;
	}
	@Override
	public String toString() {
		return "Subsucursal [id=" + id + ", strDescripcion=" + strDescripcion
				+ ", intIdEstado=" + intIdEstado + ", chkSubSucursal="
				+ chkSubSucursal + "]";
	}
}