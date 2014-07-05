package pe.com.tumi.empresa.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class Zonal extends TumiDomain {
	private ZonalId id;
	private Sucursal sucursal;
	private Integer intPersPersonaPk;
	private Integer intIdTipoZonal;
	private Integer intPersPersonaResponsablePk;
	private Integer intIdEstado;
	private List<Sucursal> listaSucursal;
	
	private Juridica juridica;
	
	public ZonalId getId() {
		return id;
	}
	public void setId(ZonalId id) {
		this.id = id;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Integer getIntPersPersonaPk() {
		return intPersPersonaPk;
	}
	public void setIntPersPersonaPk(Integer intPersPersonaPk) {
		this.intPersPersonaPk = intPersPersonaPk;
	}
	public Integer getIntIdTipoZonal() {
		return intIdTipoZonal;
	}
	public void setIntIdTipoZonal(Integer intIdTipoZonal) {
		this.intIdTipoZonal = intIdTipoZonal;
	}
	public Integer getIntPersPersonaResponsablePk() {
		return intPersPersonaResponsablePk;
	}
	public void setIntPersPersonaResponsablePk(Integer intPersPersonaResponsablePk) {
		this.intPersPersonaResponsablePk = intPersPersonaResponsablePk;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}
	public Juridica getJuridica() {
		return juridica;
	}
	public void setJuridica(Juridica juridica) {
		this.juridica = juridica;
	}

	/*private int 	intIdEmpresa;
	private int 	intIdZonal;
	private int 	intIdPersona;
	private int 	intIdTipoZonal;
	private int 	intIdResponsable;
	private int 	intIdSucursal;
	private int 	intIdEstadoZonal;
	private String 	strNombreZonal;
	private String	strAbreviatura;
	private String	strNombreEmpresa ;
	private int		intNroSucursales;
	private int 	intIdZonalOut;
	private String	strTipozona;
	
	
	public String getStrTipozona() {
		return strTipozona;
	}
	public void setStrTipozona(String strTipozona) {
		this.strTipozona = strTipozona;
	}
	public String getStrNombreEmpresa() {
		return strNombreEmpresa;
	}
	public void setStrNombreEmpresa(String strNombreEmpresa) {
		this.strNombreEmpresa = strNombreEmpresa;
	}
	public int getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(int intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public int getIntIdZonal() {
		return intIdZonal;
	}
	public void setIntIdZonal(int intIdZonal) {
		this.intIdZonal = intIdZonal;
	}
	public int getIntIdTipoZonal() {
		return intIdTipoZonal;
	}
	public void setIntIdTipoZonal(int intIdTipoZonal) {
		this.intIdTipoZonal = intIdTipoZonal;
	}
	public int getIntIdResponsable() {
		return intIdResponsable;
	}
	public void setIntIdResponsable(int intIdResponsable) {
		this.intIdResponsable = intIdResponsable;
	}
	public int getIntIdEstadoZonal() {
		return intIdEstadoZonal;
	}
	public void setIntIdEstadoZonal(int intIdEstadoZonal) {
		this.intIdEstadoZonal = intIdEstadoZonal;
	}
	public String getStrNombreZonal() {
		return strNombreZonal;
	}
	public void setStrNombreZonal(String strNombreZonal) {
		this.strNombreZonal = strNombreZonal;
	}
	public String getStrAbreviatura() {
		return strAbreviatura;
	}
	public void setStrAbreviatura(String strAbreviatura) {
		this.strAbreviatura = strAbreviatura;
	}
	public int getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(int intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public int getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(int intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public int getIntNroSucursales() {
		return intNroSucursales;
	}
	public void setIntNroSucursales(int intNroSucursales) {
		this.intNroSucursales = intNroSucursales;
	}
	public int getIntIdZonalOut() {
		return intIdZonalOut;
	}
	public void setIntIdZonalOut(int intIdZonalOut) {
		this.intIdZonalOut = intIdZonalOut;
	}
*/	
}
