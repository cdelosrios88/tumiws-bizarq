package pe.com.tumi.persona.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

import java.util.Date;
import java.util.List;


public class Empresa extends TumiDomain{

	private Integer intIdEmpresa;
	private Integer intAlertaCaducidad;
	private Date dtAlertaSesion;
	private String strAlertaSesion;
	private Integer intControlCambioClave;
	private Boolean blnControlCambioClave;
	private Integer intControlHoraIngreso;
	private Boolean blnControlHoraIngreso;
	private Integer intControlRegistro;
	private Boolean blnControlRegistro;
	private Integer intEstadoCod;
	private Integer intIntentosIngreso;
	private Object Organigrama;
	private Date dtTiempoSesion;
	private String strTiempoSesion;
	private Integer intTipoConformacionCod;
	private Integer intVigenciaClaves;

	
	private List<Persona> listaPersona;
	private Juridica juridica;
	
	//Cant. de Sucursales por Empresa
	private Integer intCantidadSucursal;
	

	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}

	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}

	public Integer getIntAlertaCaducidad() {
		return intAlertaCaducidad;
	}

	public void setIntAlertaCaducidad(Integer intAlertaCaducidad) {
		this.intAlertaCaducidad = intAlertaCaducidad;
	}

	public Date getDtAlertaSesion() {
		return dtAlertaSesion;
	}

	public void setDtAlertaSesion(Date dtAlertaSesion) {
		this.dtAlertaSesion = dtAlertaSesion;
	}

	public Integer getIntControlCambioClave() {
		return intControlCambioClave;
	}

	public void setIntControlCambioClave(Integer intControlCambioClave) {
		this.intControlCambioClave = intControlCambioClave;
	}

	public Integer getIntControlHoraIngreso() {
		return intControlHoraIngreso;
	}

	public void setIntControlHoraIngreso(Integer intControlHoraIngreso) {
		this.intControlHoraIngreso = intControlHoraIngreso;
	}

	public Integer getIntControlRegistro() {
		return intControlRegistro;
	}

	public void setIntControlRegistro(Integer intControlRegistro) {
		this.intControlRegistro = intControlRegistro;
	}

	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}

	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}

	public Integer getIntIntentosIngreso() {
		return intIntentosIngreso;
	}

	public void setIntIntentosIngreso(Integer intIntentosIngreso) {
		this.intIntentosIngreso = intIntentosIngreso;
	}

	public Object getOrganigrama() {
		return Organigrama;
	}

	public void setOrganigrama(Object organigrama) {
		Organigrama = organigrama;
	}

	public Date getDtTiempoSesion() {
		return dtTiempoSesion;
	}

	public void setDtTiempoSesion(Date dtTiempoSesion) {
		this.dtTiempoSesion = dtTiempoSesion;
	}

	public Integer getIntTipoConformacionCod() {
		return intTipoConformacionCod;
	}

	public void setIntTipoConformacionCod(Integer intTipoConformacionCod) {
		this.intTipoConformacionCod = intTipoConformacionCod;
	}

	public Integer getIntVigenciaClaves() {
		return intVigenciaClaves;
	}

	public void setIntVigenciaClaves(Integer intVigenciaClaves) {
		this.intVigenciaClaves = intVigenciaClaves;
	}

	public List<Persona> getListaPersona() {
		return listaPersona;
	}

	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}

	public Juridica getJuridica() {
		return juridica;
	}

	public void setJuridica(Juridica juridica) {
		this.juridica = juridica;
	}

	public Integer getIntCantidadSucursal() {
		return intCantidadSucursal;
	}

	public void setIntCantidadSucursal(Integer intCantidadSucursal) {
		this.intCantidadSucursal = intCantidadSucursal;
	}

	public String getStrTiempoSesion() {
		return strTiempoSesion;
	}

	public void setStrTiempoSesion(String strTiempoSesion) {
		this.strTiempoSesion = strTiempoSesion;
	}

	public String getStrAlertaSesion() {
		return strAlertaSesion;
	}

	public void setStrAlertaSesion(String strAlertaSesion) {
		this.strAlertaSesion = strAlertaSesion;
	}

	public Boolean getBlnControlHoraIngreso() {
		return blnControlHoraIngreso;
	}

	public void setBlnControlHoraIngreso(Boolean blnControlHoraIngreso) {
		this.blnControlHoraIngreso = blnControlHoraIngreso;
	}

	public Boolean getBlnControlRegistro() {
		return blnControlRegistro;
	}

	public void setBlnControlRegistro(Boolean blnControlRegistro) {
		this.blnControlRegistro = blnControlRegistro;
	}

	public Boolean getBlnControlCambioClave() {
		return blnControlCambioClave;
	}

	public void setBlnControlCambioClave(Boolean blnControlCambioClave) {
		this.blnControlCambioClave = blnControlCambioClave;
	}
	
	@Override
	public String toString() {
		return "Empresa [intIdEmpresa=" + intIdEmpresa
				+ ", intAlertaCaducidad=" + intAlertaCaducidad
				+ ", dtAlertaSesion=" + dtAlertaSesion + ", strAlertaSesion="
				+ strAlertaSesion + ", intControlCambioClave="
				+ intControlCambioClave + ", blnControlCambioClave="
				+ blnControlCambioClave + ", intControlHoraIngreso="
				+ intControlHoraIngreso + ", blnControlHoraIngreso="
				+ blnControlHoraIngreso + ", intControlRegistro="
				+ intControlRegistro + ", blnControlRegistro="
				+ blnControlRegistro + ", intEstadoCod=" + intEstadoCod
				+ ", intIntentosIngreso=" + intIntentosIngreso
				+ ", strTiempoSesion=" + strTiempoSesion
				+ ", intTipoConformacionCod=" + intTipoConformacionCod
				+ ", intVigenciaClaves=" + intVigenciaClaves + "]";
	}
}