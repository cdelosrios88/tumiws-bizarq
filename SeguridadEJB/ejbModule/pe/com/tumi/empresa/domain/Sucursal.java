package pe.com.tumi.empresa.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.contacto.domain.Domicilio;

public class Sucursal extends TumiDomain{
	// Tabla SEG_SUCURSAL
    private	SucursalId id;
    private Integer intPersPersonaPk;
    private Integer intIdTipoSucursal;
    private Integer intIdEstado;
    private Integer	intIdZonal;
    private Integer intNroSubSucursales;
    private Integer intIdTercero;
    private Integer intIdSubSucursal;
    private String	strAnexado;
    private String	strSucurSondeo;
    private List<SucursalCodigo> listaSucursalCodigo;
    private List<Subsucursal> listaSubSucursal;
    private Zonal zonal;
	private Juridica juridica;
	private List<Area>	listaArea;
	
	public Sucursal(){
		super();
		id = new SucursalId();
	}
	
    //Getters y Setters
	public SucursalId getId() {
		return id;
	}
	public void setId(SucursalId id) {
		this.id = id;
	}
	public Integer getIntPersPersonaPk() {
		return intPersPersonaPk;
	}
	public void setIntPersPersonaPk(Integer intPersPersonaPk) {
		this.intPersPersonaPk = intPersPersonaPk;
	}
	public Integer getIntIdTipoSucursal() {
		return intIdTipoSucursal;
	}
	public void setIntIdTipoSucursal(Integer intIdTipoSucursal) {
		this.intIdTipoSucursal = intIdTipoSucursal;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Integer getIntIdZonal() {
		return intIdZonal;
	}
	public void setIntIdZonal(Integer intIdZonal) {
		this.intIdZonal = intIdZonal;
	}
	public Integer getIntNroSubSucursales() {
		return intNroSubSucursales;
	}
	public void setIntNroSubSucursales(Integer intNroSubSucursales) {
		this.intNroSubSucursales = intNroSubSucursales;
	}
	public Integer getIntIdTercero() {
		return intIdTercero;
	}
	public void setIntIdTercero(Integer intIdTercero) {
		this.intIdTercero = intIdTercero;
	}
	public Integer getIntIdSubSucursal() {
		return intIdSubSucursal;
	}
	public void setIntIdSubSucursal(Integer intIdSubSucursal) {
		this.intIdSubSucursal = intIdSubSucursal;
	}
	public String getStrAnexado() {
		return strAnexado;
	}
	public void setStrAnexado(String strAnexado) {
		this.strAnexado = strAnexado;
	}
	public String getStrSucurSondeo() {
		return strSucurSondeo;
	}
	public void setStrSucurSondeo(String strSucurSondeo) {
		this.strSucurSondeo = strSucurSondeo;
	}
	public List<SucursalCodigo> getListaSucursalCodigo() {
		return listaSucursalCodigo;
	}
	public void setListaSucursalCodigo(List<SucursalCodigo> listaSucursalCodigo) {
		this.listaSucursalCodigo = listaSucursalCodigo;
	}
	public List<Subsucursal> getListaSubSucursal() {
		return listaSubSucursal;
	}
	public void setListaSubSucursal(List<Subsucursal> listaSubSucursal) {
		this.listaSubSucursal = listaSubSucursal;
	}
	public Juridica getJuridica() {
		return juridica;
	}
	public void setJuridica(Juridica juridica) {
		this.juridica = juridica;
	}
	public Zonal getZonal() {
		return zonal;
	}
	public void setZonal(Zonal zonal) {
		this.zonal = zonal;
	}
	public List<Area> getListaArea() {
		return listaArea;
	}
	public void setListaArea(List<Area> listaArea) {
		this.listaArea = listaArea;
	}

	@Override
	public String toString() {
		return "Sucursal [id=" + id + ", intPersPersonaPk=" + intPersPersonaPk
				+ ", intIdTipoSucursal=" + intIdTipoSucursal + ", intIdEstado="
				+ intIdEstado + ", intIdZonal=" + intIdZonal
				+ ", intNroSubSucursales=" + intNroSubSucursales
				+ ", intIdTercero=" + intIdTercero + ", intIdSubSucursal="
				+ intIdSubSucursal + ", strAnexado=" + strAnexado
				+ ", strSucurSondeo=" + strSucurSondeo
				+ ", listaSucursalCodigo=" + listaSucursalCodigo
				+ ", listaSubSucursal=" + listaSubSucursal + ", zonal=" + zonal
				+ ", juridica=" + juridica + "]";
	}
	
}
