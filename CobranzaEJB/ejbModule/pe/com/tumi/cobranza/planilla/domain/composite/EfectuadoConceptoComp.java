package pe.com.tumi.cobranza.planilla.domain.composite;

import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class EfectuadoConceptoComp extends TumiDomain
	{
	
	private EfectuadoConcepto efectuadoConcepto;
	private Efectuado efectuado;
	private EstructuraDetalle estructuraDetalle;
	private Estructura estructura;
	private Integer intParaTipoSocio;
	private Integer intParaModalidadPlanilla;
	private Juridica juridicaUnidadEjecutora;
	private Juridica juridicaSucursal;
	private Integer intPeriodo;
	private Integer intPeriodoMes;
	private Integer intPeriodoAnio;
	
	private Sucursal sucursal;
	
	private Documento documento;
 	private Socio socio;
 	private CuentaIntegrante cuentaIntegrante;
 	private Efectuado yaEfectuado;
 	private Boolean blnAgregarSocio;
 	private Boolean blnAgregarNoSocio;
 	
	public EfectuadoConceptoComp()
	{
		efectuadoConcepto = new EfectuadoConcepto();
		efectuado = new Efectuado();
		estructuraDetalle = new EstructuraDetalle();
		estructura = new Estructura();
		sucursal = new Sucursal();
		documento = new Documento();
		socio = new  Socio();
		cuentaIntegrante = new CuentaIntegrante();
		yaEfectuado = new Efectuado();
	}
	public EfectuadoConcepto getEfectuadoConcepto() {
		return efectuadoConcepto;
	}
	public void setEfectuadoConcepto(EfectuadoConcepto efectuadoConcepto) {
		this.efectuadoConcepto = efectuadoConcepto;
	}
	public Efectuado getEfectuado() {
		return efectuado;
	}
	public void setEfectuado(Efectuado efectuado) {
		this.efectuado = efectuado;
	}
	public EstructuraDetalle getEstructuraDetalle() {
		return estructuraDetalle;
	}
	public void setEstructuraDetalle(EstructuraDetalle estructuraDetalle) {
		this.estructuraDetalle = estructuraDetalle;
	}
	public Estructura getEstructura() {
		return estructura;
	}
	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}
	public Integer getIntParaTipoSocio() {
		return intParaTipoSocio;
	}
	public void setIntParaTipoSocio(Integer intParaTipoSocio) {
		this.intParaTipoSocio = intParaTipoSocio;
	}
	public Integer getIntParaModalidadPlanilla() {
		return intParaModalidadPlanilla;
	}
	public void setIntParaModalidadPlanilla(Integer intParaModalidadPlanilla) {
		this.intParaModalidadPlanilla = intParaModalidadPlanilla;
	}
	public Juridica getJuridicaUnidadEjecutora() {
		return juridicaUnidadEjecutora;
	}
	public void setJuridicaUnidadEjecutora(Juridica juridicaUnidadEjecutora) {
		this.juridicaUnidadEjecutora = juridicaUnidadEjecutora;
	}
	public Juridica getJuridicaSucursal() {
		return juridicaSucursal;
	}
	public void setJuridicaSucursal(Juridica juridicaSucursal) {
		this.juridicaSucursal = juridicaSucursal;
	}
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}
	
	public Integer getIntPeriodoMes() {
		return intPeriodoMes;
	}
	public void setIntPeriodoMes(Integer intPeriodoMes) {
		this.intPeriodoMes = intPeriodoMes;
	}
	public Integer getIntPeriodoAnio() {
		return intPeriodoAnio;
	}
	public void setIntPeriodoAnio(Integer intPeriodoAnio) {
		this.intPeriodoAnio = intPeriodoAnio;
	}	
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}	
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	public CuentaIntegrante getCuentaIntegrante() {
		return cuentaIntegrante;
	}
	public void setCuentaIntegrante(CuentaIntegrante cuentaIntegrante) {
		this.cuentaIntegrante = cuentaIntegrante;
	}
	public Efectuado getYaEfectuado() {
		return yaEfectuado;
	}
	public void setYaEfectuado(Efectuado yaEfectuado) {
		this.yaEfectuado = yaEfectuado;
	}
	public Boolean getBlnAgregarSocio() {
		return blnAgregarSocio;
	}
	public void setBlnAgregarSocio(Boolean blnAgregarSocio) {
		this.blnAgregarSocio = blnAgregarSocio;
	}
	public Boolean getBlnAgregarNoSocio() {
		return blnAgregarNoSocio;
	}
	public void setBlnAgregarNoSocio(Boolean blnAgregarNoSocio) {
		this.blnAgregarNoSocio = blnAgregarNoSocio;
	}
	
}
