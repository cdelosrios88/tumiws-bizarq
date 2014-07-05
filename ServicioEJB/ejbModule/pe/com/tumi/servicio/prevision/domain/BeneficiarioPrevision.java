package pe.com.tumi.servicio.prevision.domain;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

public class BeneficiarioPrevision extends TumiDomain{

	private BeneficiarioPrevisionId id;
	private Integer intItemViculo;
	private Integer intParaEstado;
	private BigDecimal bdPorcentajeBeneficio;
	private Integer intPersPersonaBeneficiario;
	private Integer intPersEmpresaEgreso;
	private Integer intItemEgresoGeneral;
	private Persona persona;
	private String 	strEtiqueta;
	private Egreso	egreso;
	private Persona	personaApoderado;
	private	Archivo	archivoCartaPoder;
	private boolean esUltimoBeneficiarioAGirar;
	
	private BigDecimal bdMontoTotal;
	private BigDecimal bdGastosAdministrativos;
	private BigDecimal bdMontoNeto;
	private CuentaDetalleBeneficio cuentaDetalleBeneficio;
	
	private Integer intTipoViculo;
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 23-09-2013
	private String strNomApeBeneficiario;
	
	//Autor rVillarreal / 04.06.2014
	private String strApePaterno;
	private String strApeMaterno;
	private String strNombre;
	private Integer intItemVinculoCod;
	private Integer intTipoVinculoCod;
	
	public Integer getIntTipoViculo() {
		return intTipoViculo;
	}
	public void setIntTipoViculo(Integer intTipoViculo) {
		this.intTipoViculo = intTipoViculo;
	}
	public BeneficiarioPrevisionId getId() {
		return id;
	}
	public void setId(BeneficiarioPrevisionId id) {
		this.id = id;
	}
	public Integer getIntItemViculo() {
		return intItemViculo;
	}
	public void setIntItemViculo(Integer intItemViculo) {
		this.intItemViculo = intItemViculo;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public BigDecimal getBdPorcentajeBeneficio() {
		return bdPorcentajeBeneficio;
	}
	public void setBdPorcentajeBeneficio(BigDecimal bdPorcentajeBeneficio) {
		this.bdPorcentajeBeneficio = bdPorcentajeBeneficio;
	}
	public Integer getIntPersPersonaBeneficiario() {
		return intPersPersonaBeneficiario;
	}
	public void setIntPersPersonaBeneficiario(Integer intPersPersonaBeneficiario) {
		this.intPersPersonaBeneficiario = intPersPersonaBeneficiario;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getStrEtiqueta() {
		return strEtiqueta;
	}
	public void setStrEtiqueta(String strEtiqueta) {
		this.strEtiqueta = strEtiqueta;
	}
	public Egreso getEgreso() {
		return egreso;
	}
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	public Persona getPersonaApoderado() {
		return personaApoderado;
	}
	public void setPersonaApoderado(Persona personaApoderado) {
		this.personaApoderado = personaApoderado;
	}
	public Archivo getArchivoCartaPoder() {
		return archivoCartaPoder;
	}
	public void setArchivoCartaPoder(Archivo archivoCartaPoder) {
		this.archivoCartaPoder = archivoCartaPoder;
	}	
	public boolean isEsUltimoBeneficiarioAGirar() {
		return esUltimoBeneficiarioAGirar;
	}
	public void setEsUltimoBeneficiarioAGirar(boolean esUltimoBeneficiarioAGirar) {
		this.esUltimoBeneficiarioAGirar = esUltimoBeneficiarioAGirar;
	}
	
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public BigDecimal getBdGastosAdministrativos() {
		return bdGastosAdministrativos;
	}
	public void setBdGastosAdministrativos(BigDecimal bdGastosAdministrativos) {
		this.bdGastosAdministrativos = bdGastosAdministrativos;
	}
	public BigDecimal getBdMontoNeto() {
		return bdMontoNeto;
	}
	public void setBdMontoNeto(BigDecimal bdMontoNeto) {
		this.bdMontoNeto = bdMontoNeto;
	}
	
	
	public CuentaDetalleBeneficio getCuentaDetalleBeneficio() {
		return cuentaDetalleBeneficio;
	}
	public void setCuentaDetalleBeneficio(
			CuentaDetalleBeneficio cuentaDetalleBeneficio) {
		this.cuentaDetalleBeneficio = cuentaDetalleBeneficio;
	}
	@Override
	public String toString() {
		return "BeneficiarioPrevision [id=" + id + ", intItemViculo="
				+ intItemViculo + ", intParaEstado=" + intParaEstado
				+ ", bdPorcentajeBeneficio=" + bdPorcentajeBeneficio
				+ ", intPersPersonaBeneficiario=" + intPersPersonaBeneficiario
				+ ", intPersEmpresaEgreso=" + intPersEmpresaEgreso
				+ ", intItemEgresoGeneral=" + intItemEgresoGeneral + "]";
	}
	public String getStrNomApeBeneficiario() {
		return strNomApeBeneficiario;
	}
	public void setStrNomApeBeneficiario(String strNomApeBeneficiario) {
		this.strNomApeBeneficiario = strNomApeBeneficiario;
	}
	public String getStrApePaterno() {
		return strApePaterno;
	}
	public void setStrApePaterno(String strApePaterno) {
		this.strApePaterno = strApePaterno;
	}
	public String getStrApeMaterno() {
		return strApeMaterno;
	}
	public void setStrApeMaterno(String strApeMaterno) {
		this.strApeMaterno = strApeMaterno;
	}
	public String getStrNombre() {
		return strNombre;
	}
	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}
	public Integer getIntItemVinculoCod() {
		return intItemVinculoCod;
	}
	public void setIntItemVinculoCod(Integer intItemVinculoCod) {
		this.intItemVinculoCod = intItemVinculoCod;
	}
	public Integer getIntTipoVinculoCod() {
		return intTipoVinculoCod;
	}
	public void setIntTipoVinculoCod(Integer intTipoVinculoCod) {
		this.intTipoVinculoCod = intTipoVinculoCod;
	}

}