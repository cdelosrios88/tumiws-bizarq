package pe.com.tumi.contabilidad.core.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class AccesoPlanCuenta extends TumiDomain{

	private AccesoPlanCuentaId id;
	private Integer intPersEmpresa;
	private Integer intSucuIdSucursal;
	private Integer	intSudeIdSubsucursal;
	private Integer	intIdPerfil;
	private Integer intParaEstado;
	private List<AccesoPlanCuentaDetalle> listaAccesoPlanCuentaDetalle;
	
	public AccesoPlanCuenta(){
		id = new AccesoPlanCuentaId();
	}

	public AccesoPlanCuentaId getId() {
		return id;
	}
	public void setId(AccesoPlanCuentaId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSudeIdSubsucursal() {
		return intSudeIdSubsucursal;
	}
	public void setIntSudeIdSubsucursal(Integer intSudeIdSubsucursal) {
		this.intSudeIdSubsucursal = intSudeIdSubsucursal;
	}
	public Integer getIntIdPerfil() {
		return intIdPerfil;
	}
	public void setIntIdPerfil(Integer intIdPerfil) {
		this.intIdPerfil = intIdPerfil;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public List<AccesoPlanCuentaDetalle> getListaAccesoPlanCuentaDetalle() {
		return listaAccesoPlanCuentaDetalle;
	}
	public void setListaAccesoPlanCuentaDetalle(List<AccesoPlanCuentaDetalle> listaAccesoPlanCuentaDetalle) {
		this.listaAccesoPlanCuentaDetalle = listaAccesoPlanCuentaDetalle;
	}

	@Override
	public String toString() {
		return "AccesoPlanCuenta [id=" + id + ", intPersEmpresa="
				+ intPersEmpresa + ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdSubsucursal=" + intSudeIdSubsucursal
				+ ", intIdPerfil=" + intIdPerfil + ", intParaEstado="
				+ intParaEstado + "]";
	}
}