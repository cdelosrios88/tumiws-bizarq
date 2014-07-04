package pe.com.tumi.tesoreria.banco.domain;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Acceso extends TumiDomain{

	private	AccesoId	id;
	private Integer	intPersEmpresaSucursal;
	private	Integer intSucuIdSucursal;
	private	Integer intSudeIdSubsucursal;
	private	Integer intParaEstado;
	private	String	strObservacion;
	private	List<AccesoDetalle> listaAccesoDetalle;
	
	//interfaz
	private	Integer	intCantidadCuentaBancaria;
	private	Integer	intCantidadFondoFijo;
	private	String 	strEtiquetaSucursal;
	private	String 	strEtiquetaSubsucursal;	

	private Integer 	intPersEmpresaModifica;
	private Integer 	intPersPersonaModifica;
	private	Sucursal	sucursal;
	private	Subsucursal	subsucursal;
	private AccesoDetalle	accesoDetalleUsar;
	
	public Acceso(){
		id = new AccesoId();
		listaAccesoDetalle = new ArrayList<AccesoDetalle>();
	}
	
	public AccesoId getId() {
		return id;
	}
	public void setId(AccesoId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaSucursal() {
		return intPersEmpresaSucursal;
	}
	public void setIntPersEmpresaSucursal(Integer intPersEmpresaSucursal) {
		this.intPersEmpresaSucursal = intPersEmpresaSucursal;
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
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public List<AccesoDetalle> getListaAccesoDetalle() {
		return listaAccesoDetalle;
	}
	public void setListaAccesoDetalle(List<AccesoDetalle> listaAccesoDetalle) {
		this.listaAccesoDetalle = listaAccesoDetalle;
	}
	public Integer getIntCantidadCuentaBancaria() {
		return intCantidadCuentaBancaria;
	}
	public void setIntCantidadCuentaBancaria(Integer intCantidadCuentaBancaria) {
		this.intCantidadCuentaBancaria = intCantidadCuentaBancaria;
	}
	public Integer getIntCantidadFondoFijo() {
		return intCantidadFondoFijo;
	}
	public void setIntCantidadFondoFijo(Integer intCantidadFondoFijo) {
		this.intCantidadFondoFijo = intCantidadFondoFijo;
	}
	public String getStrEtiquetaSucursal() {
		return strEtiquetaSucursal;
	}
	public void setStrEtiquetaSucursal(String strEtiquetaSucursal) {
		this.strEtiquetaSucursal = strEtiquetaSucursal;
	}
	public String getStrEtiquetaSubsucursal() {
		return strEtiquetaSubsucursal;
	}
	public void setStrEtiquetaSubsucursal(String strEtiquetaSubsucursal) {
		this.strEtiquetaSubsucursal = strEtiquetaSubsucursal;
	}
	public Integer getIntPersEmpresaModifica() {
		return intPersEmpresaModifica;
	}
	public void setIntPersEmpresaModifica(Integer intPersEmpresaModifica) {
		this.intPersEmpresaModifica = intPersEmpresaModifica;
	}
	public Integer getIntPersPersonaModifica() {
		return intPersPersonaModifica;
	}
	public void setIntPersPersonaModifica(Integer intPersPersonaModifica) {
		this.intPersPersonaModifica = intPersPersonaModifica;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Subsucursal getSubsucursal() {
		return subsucursal;
	}
	public void setSubsucursal(Subsucursal subsucursal) {
		this.subsucursal = subsucursal;
	}
	public AccesoDetalle getAccesoDetalleUsar() {
		return accesoDetalleUsar;
	}
	public void setAccesoDetalleUsar(AccesoDetalle accesoDetalleUsar) {
		this.accesoDetalleUsar = accesoDetalleUsar;
	}

	@Override
	public String toString() {
		return "Acceso [id=" + id + ", intPersEmpresaSucursal="
				+ intPersEmpresaSucursal + ", intSucuIdSucursal="
				+ intSucuIdSucursal + ", intSudeIdSubsucursal="
				+ intSudeIdSubsucursal + ", intParaEstado=" + intParaEstado
				+ ", strObservacion=" + strObservacion + "]";
	}
	
}
