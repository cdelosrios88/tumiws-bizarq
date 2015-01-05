package pe.com.tumi.servicio.prevision.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

public class BeneficiarioLiquidacion extends TumiDomain{

	private BeneficiarioLiquidacionId id;
	private Integer intItemViculo;
	private Integer intParaEstado;
	private BigDecimal bdPorcentajeBeneficio;
	private Integer intPersPersonaBeneficiario;
	private Integer intPersEmpresaEgreso;
	private Integer intItemEgresoGeneral;
	private Persona persona;
	private String 	strEtiqueta;
	
	// Campos utilizados en solicitud de liquidacion
	private BigDecimal bdMontoAporte;
	private BigDecimal bdMontoRetiro;
	private BigDecimal bdMontoTotal;
	private BigDecimal bdTotal;
	
	private Integer intTipoViculo;
	
	private CuentaDetalleBeneficio cuentaDetalleBeneficioApo;
	private CuentaDetalleBeneficio cuentaDetalleBeneficioRet;
	private BigDecimal bdPorcentajeBeneficioApo;
	private BigDecimal bdPorcentajeBeneficioRet;

	//JCHAVEZ 28.01.2014
	private Persona	personaApoderado;
	private	Archivo	archivoCartaPoder;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 13.08.2014 / 
	private Egreso egreso;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 03.12.2014 /
	private BigDecimal bdPorcentajeBeneficioAhorro;
	private BigDecimal bdMontoAhorro;
	
	public BeneficiarioLiquidacion(){
		id = new BeneficiarioLiquidacionId();
	}
	
	public BeneficiarioLiquidacionId getId() {
		return id;
	}
	public void setId(BeneficiarioLiquidacionId id) {
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
	
	public BigDecimal getBdMontoAporte() {
		return bdMontoAporte;
	}

	public void setBdMontoAporte(BigDecimal bdMontoAporte) {
		this.bdMontoAporte = bdMontoAporte;
	}

	public BigDecimal getBdMontoRetiro() {
		return bdMontoRetiro;
	}

	public void setBdMontoRetiro(BigDecimal bdMontoRetiro) {
		this.bdMontoRetiro = bdMontoRetiro;
	}

	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}

	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}

	public BigDecimal getBdTotal() {
		return bdTotal;
	}

	public void setBdTotal(BigDecimal bdTotal) {
		this.bdTotal = bdTotal;
	}

	public Integer getIntTipoViculo() {
		return intTipoViculo;
	}

	public void setIntTipoViculo(Integer intTipoViculo) {
		this.intTipoViculo = intTipoViculo;
	}

	public CuentaDetalleBeneficio getCuentaDetalleBeneficioApo() {
		return cuentaDetalleBeneficioApo;
	}

	public void setCuentaDetalleBeneficioApo(
			CuentaDetalleBeneficio cuentaDetalleBeneficioApo) {
		this.cuentaDetalleBeneficioApo = cuentaDetalleBeneficioApo;
	}

	public CuentaDetalleBeneficio getCuentaDetalleBeneficioRet() {
		return cuentaDetalleBeneficioRet;
	}

	public void setCuentaDetalleBeneficioRet(
			CuentaDetalleBeneficio cuentaDetalleBeneficioRet) {
		this.cuentaDetalleBeneficioRet = cuentaDetalleBeneficioRet;
	}

	public BigDecimal getBdPorcentajeBeneficioApo() {
		return bdPorcentajeBeneficioApo;
	}

	public void setBdPorcentajeBeneficioApo(BigDecimal bdPorcentajeBeneficioApo) {
		this.bdPorcentajeBeneficioApo = bdPorcentajeBeneficioApo;
	}

	public BigDecimal getBdPorcentajeBeneficioRet() {
		return bdPorcentajeBeneficioRet;
	}

	public void setBdPorcentajeBeneficioRet(BigDecimal bdPorcentajeBeneficioRet) {
		this.bdPorcentajeBeneficioRet = bdPorcentajeBeneficioRet;
	}

	@Override
	public String toString() {
		return "BeneficiarioLiquidacion [id=" + id + ", intItemViculo="
				+ intItemViculo + ", intParaEstado=" + intParaEstado
				+ ", bdPorcentajeBeneficio=" + bdPorcentajeBeneficio
				+ ", intPersPersonaBeneficiario=" + intPersPersonaBeneficiario
				+ ", intPersEmpresaEgreso=" + intPersEmpresaEgreso
				+ ", intItemEgresoGeneral=" + intItemEgresoGeneral + "]";
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

	public Egreso getEgreso() {
		return egreso;
	}
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	public BigDecimal getBdPorcentajeBeneficioAhorro() {
		return bdPorcentajeBeneficioAhorro;
	}
	public void setBdPorcentajeBeneficioAhorro(
			BigDecimal bdPorcentajeBeneficioAhorro) {
		this.bdPorcentajeBeneficioAhorro = bdPorcentajeBeneficioAhorro;
	}
	public BigDecimal getBdMontoAhorro() {
		return bdMontoAhorro;
	}
	public void setBdMontoAhorro(BigDecimal bdMontoAhorro) {
		this.bdMontoAhorro = bdMontoAhorro;
	}
}