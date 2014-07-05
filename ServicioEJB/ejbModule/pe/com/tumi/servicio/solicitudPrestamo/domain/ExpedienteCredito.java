package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelComp;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CapacidadCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

public class ExpedienteCredito extends TumiDomain {
	private ExpedienteCreditoId id;
	private Integer		intParaSubTipoOperacionCod;
	private Integer		intPersEmpresaCreditoPk;
	private Integer		intParaTipoCreditoCod;
	private Integer		intItemCredito;
	private BigDecimal	bdPorcentajeInteres;
	private BigDecimal	bdPorcentajeGravamen;
	private BigDecimal	bdPorcentajeAporte;
	private BigDecimal	bdMontoTotal;
	private BigDecimal	bdMontoGravamen;
	private BigDecimal	bdMontoAporte;
	private BigDecimal	bdMontoInteresAtrasado;
	private BigDecimal	bdMontoMoraAtrasada;
	private BigDecimal	bdMontoSolicitado;
	private Integer		intNumeroCuota;
	private Integer		intParaFinalidadCod;
	private Integer		intPersEmpresaRefPk;
	private Integer		intCuentaRefPk;
	private Integer		intItemExpedienteRef;
	private Integer		intItemDetExpedienteRef;
	private Integer		intParaDocumentoGeneralCod;
	private String		strObservacion;
	
	//NUEVOS CAMPOS - CGD 18.09.2013
	private Integer		intPersEmpresaSucAdministra;
	private Integer		intSucuIdSucursalAdministra;
	private Integer		intSudeIdSubSucursalAdministra;

	private List<CapacidadCreditoComp> listaCapacidadCreditoComp;
	private List<CapacidadCredito> listaCapacidadCredito;
	private List<EstadoCredito> listaEstadoCredito;
	private List<CronogramaCredito> listaCronogramaCredito;
	private List<GarantiaCredito> listaGarantiaCredito;
	private List<RequisitoCreditoComp> listaRequisitoCreditoComp;
	private List<AutorizaCredito> listaAutorizaCredito;
	private List<AutorizaVerificacion> listaAutorizaVerificacion;
	private List<CancelacionCredito> listaCancelacionCredito;
	private List<ExpedienteActividad> listaExpedienteActividad;
	
	// Atributos adicionales - Comp
	private EstadoCredito estadoCreditoUltimo;
	private	Cuenta	cuenta;
	private Persona	personaAdministra;
	private Egreso	egreso;
	private EstadoCredito estadoCreditoAprobado;
	private Persona	personaGirar;
	private Persona personaApoderado;
	private	Archivo	archivoCartaPoder;
	private	String	strGlosaEgreso;
	private EstadoCredito estadoCreditoPrimero;
	
	//10.05.2013 - CGD
	private SocioComp socioComp;
	//11.05.2013
	private List<Estructura> listaEstructuraSocio;
	
	//21.10.2013 - filtros
	private Integer intItemEstadoUltimo;
	private Integer intEstadoCreditoUltimo;
	private String strDesccripcionEstadoUltimo;
	private Integer intSucursalEstadoUltimo;
	
	private Integer intItemEstadoInicial;
	private Integer intEstadoCreditoInicial;
	private String strDesccripcionEstadoInicial;
	private Integer intSucursalEstadoInicial;
	private String strNroCuentaExpediente;
	private String strNombreCompletoPersona;
	private String strDescripcionCreditoEmpresa;
	private Integer intParaTipoCreditoEmpresa;

	//JCHAVEZ 31.12.2013 Campo para Categoria de Riesgo 
	private Integer intPeriodoCartera;
	private Integer intParaTipoCategoriaRiesgo;
	private List<ModeloDetalleNivelComp> listaModeloDetalleNivelComp;
	private ExpedienteCredito expedienteCreditoCancelacion;
	private List<CronogramaCredito> listaCronogramaCreditoRegenerado;

	private Boolean generaAporte;
	
	//
	private List<RequisitoCredito> lstRequisitoCreditos;
	private Archivo archivoGiro;
	//
	private Integer intEsGiroPorSedeCentral;
	public ExpedienteCreditoId getId() {
		return id;
	}
	public void setId(ExpedienteCreditoId id) {
		this.id = id;
	}
	public Integer getIntParaSubTipoOperacionCod() {
		return intParaSubTipoOperacionCod;
	}
	public void setIntParaSubTipoOperacionCod(Integer intParaSubTipoOperacionCod) {
		this.intParaSubTipoOperacionCod = intParaSubTipoOperacionCod;
	}
	public Integer getIntPersEmpresaCreditoPk() {
		return intPersEmpresaCreditoPk;
	}
	public void setIntPersEmpresaCreditoPk(Integer intPersEmpresaCreditoPk) {
		this.intPersEmpresaCreditoPk = intPersEmpresaCreditoPk;
	}
	public Integer getIntParaTipoCreditoCod() {
		return intParaTipoCreditoCod;
	}
	public void setIntParaTipoCreditoCod(Integer intParaTipoCreditoCod) {
		this.intParaTipoCreditoCod = intParaTipoCreditoCod;
	}
	public Integer getIntItemCredito() {
		return intItemCredito;
	}
	public void setIntItemCredito(Integer intItemCredito) {
		this.intItemCredito = intItemCredito;
	}
	public BigDecimal getBdPorcentajeInteres() {
		return bdPorcentajeInteres;
	}
	public void setBdPorcentajeInteres(BigDecimal bdPorcentajeInteres) {
		this.bdPorcentajeInteres = bdPorcentajeInteres;
	}
	public BigDecimal getBdPorcentajeGravamen() {
		return bdPorcentajeGravamen;
	}
	public void setBdPorcentajeGravamen(BigDecimal bdPorcentajeGravamen) {
		this.bdPorcentajeGravamen = bdPorcentajeGravamen;
	}
	public BigDecimal getBdPorcentajeAporte() {
		return bdPorcentajeAporte;
	}
	public void setBdPorcentajeAporte(BigDecimal bdPorcentajeAporte) {
		this.bdPorcentajeAporte = bdPorcentajeAporte;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public BigDecimal getBdMontoGravamen() {
		return bdMontoGravamen;
	}
	public void setBdMontoGravamen(BigDecimal bdMontoGravamen) {
		this.bdMontoGravamen = bdMontoGravamen;
	}
	public BigDecimal getBdMontoAporte() {
		return bdMontoAporte;
	}
	public void setBdMontoAporte(BigDecimal bdMontoAporte) {
		this.bdMontoAporte = bdMontoAporte;
	}
	public BigDecimal getBdMontoInteresAtrasado() {
		return bdMontoInteresAtrasado;
	}
	public void setBdMontoInteresAtrasado(BigDecimal bdMontoInteresAtrasado) {
		this.bdMontoInteresAtrasado = bdMontoInteresAtrasado;
	}
	public BigDecimal getBdMontoMoraAtrasada() {
		return bdMontoMoraAtrasada;
	}
	public void setBdMontoMoraAtrasada(BigDecimal bdMontoMoraAtrasada) {
		this.bdMontoMoraAtrasada = bdMontoMoraAtrasada;
	}
	public BigDecimal getBdMontoSolicitado() {
		return bdMontoSolicitado;
	}
	public void setBdMontoSolicitado(BigDecimal bdMontoSolicitado) {
		this.bdMontoSolicitado = bdMontoSolicitado;
	}
	public Integer getIntNumeroCuota() {
		return intNumeroCuota;
	}
	public void setIntNumeroCuota(Integer intNumeroCuota) {
		this.intNumeroCuota = intNumeroCuota;
	}
	public Integer getIntParaFinalidadCod() {
		return intParaFinalidadCod;
	}
	public void setIntParaFinalidadCod(Integer intParaFinalidadCod) {
		this.intParaFinalidadCod = intParaFinalidadCod;
	}
	public Integer getIntPersEmpresaRefPk() {
		return intPersEmpresaRefPk;
	}
	public void setIntPersEmpresaRefPk(Integer intPersEmpresaRefPk) {
		this.intPersEmpresaRefPk = intPersEmpresaRefPk;
	}
	public Integer getIntCuentaRefPk() {
		return intCuentaRefPk;
	}
	public void setIntCuentaRefPk(Integer intCuentaRefPk) {
		this.intCuentaRefPk = intCuentaRefPk;
	}
	public Integer getIntItemExpedienteRef() {
		return intItemExpedienteRef;
	}
	public void setIntItemExpedienteRef(Integer intItemExpedienteRef) {
		this.intItemExpedienteRef = intItemExpedienteRef;
	}
	public Integer getIntItemDetExpedienteRef() {
		return intItemDetExpedienteRef;
	}
	public void setIntItemDetExpedienteRef(Integer intItemDetExpedienteRef) {
		this.intItemDetExpedienteRef = intItemDetExpedienteRef;
	}
	public Integer getIntParaDocumentoGeneralCod() {
		return intParaDocumentoGeneralCod;
	}
	public void setIntParaDocumentoGeneralCod(Integer intParaDocumentoGeneralCod) {
		this.intParaDocumentoGeneralCod = intParaDocumentoGeneralCod;
	}
	public List<CapacidadCreditoComp> getListaCapacidadCreditoComp() {
		return listaCapacidadCreditoComp;
	}
	public void setListaCapacidadCreditoComp(List<CapacidadCreditoComp> listaCapacidadCreditoComp) {
		this.listaCapacidadCreditoComp = listaCapacidadCreditoComp;
	}
	public List<CapacidadCredito> getListaCapacidadCredito() {
		return listaCapacidadCredito;
	}
	public void setListaCapacidadCredito(List<CapacidadCredito> listaCapacidadCredito) {
		this.listaCapacidadCredito = listaCapacidadCredito;
	}
	public List<EstadoCredito> getListaEstadoCredito() {
		return listaEstadoCredito;
	}
	public void setListaEstadoCredito(List<EstadoCredito> listaEstadoCredito) {
		this.listaEstadoCredito = listaEstadoCredito;
	}
	public List<CronogramaCredito> getListaCronogramaCredito() {
		return listaCronogramaCredito;
	}
	public void setListaCronogramaCredito(List<CronogramaCredito> listaCronogramaCredito) {
		this.listaCronogramaCredito = listaCronogramaCredito;
	}
	public List<GarantiaCredito> getListaGarantiaCredito() {
		return listaGarantiaCredito;
	}
	public void setListaGarantiaCredito(List<GarantiaCredito> listaGarantiaCredito) {
		this.listaGarantiaCredito = listaGarantiaCredito;
	}
	public List<RequisitoCreditoComp> getListaRequisitoCreditoComp() {
		return listaRequisitoCreditoComp;
	}
	public void setListaRequisitoCreditoComp(List<RequisitoCreditoComp> listaRequisitoCreditoComp) {
		this.listaRequisitoCreditoComp = listaRequisitoCreditoComp;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public List<AutorizaCredito> getListaAutorizaCredito() {
		return listaAutorizaCredito;
	}
	public void setListaAutorizaCredito(List<AutorizaCredito> listaAutorizaCredito) {
		this.listaAutorizaCredito = listaAutorizaCredito;
	}
	public List<AutorizaVerificacion> getListaAutorizaVerificacion() {
		return listaAutorizaVerificacion;
	}
	public void setListaAutorizaVerificacion(List<AutorizaVerificacion> listaAutorizaVerificacion) {
		this.listaAutorizaVerificacion = listaAutorizaVerificacion;
	}
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	public Persona getPersonaAdministra() {
		return personaAdministra;
	}
	public void setPersonaAdministra(Persona personaAdministra) {
		this.personaAdministra = personaAdministra;
	}
	public Egreso getEgreso() {
		return egreso;
	}
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	public EstadoCredito getEstadoCreditoUltimo() {
		return estadoCreditoUltimo;
	}
	public void setEstadoCreditoUltimo(EstadoCredito estadoCreditoUltimo) {
		this.estadoCreditoUltimo = estadoCreditoUltimo;
	}	
	public List<CancelacionCredito> getListaCancelacionCredito() {
		return listaCancelacionCredito;
	}
	public void setListaCancelacionCredito(List<CancelacionCredito> listaCancelacionCredito) {
		this.listaCancelacionCredito = listaCancelacionCredito;
	}	
	public EstadoCredito getEstadoCreditoAprobado() {
		return estadoCreditoAprobado;
	}
	public void setEstadoCreditoAprobado(EstadoCredito estadoCreditoAprobado) {
		this.estadoCreditoAprobado = estadoCreditoAprobado;
	}	
	public Persona getPersonaGirar() {
		return personaGirar;
	}
	public void setPersonaGirar(Persona personaGirar) {
		this.personaGirar = personaGirar;
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
	public String getStrGlosaEgreso() {
		return strGlosaEgreso;
	}
	public void setStrGlosaEgreso(String strGlosaEgreso) {
		this.strGlosaEgreso = strGlosaEgreso;
	}
	
	public List<ExpedienteActividad> getListaExpedienteActividad() {
		return listaExpedienteActividad;
	}
	public void setListaExpedienteActividad(
			List<ExpedienteActividad> listaExpedienteActividad) {
		this.listaExpedienteActividad = listaExpedienteActividad;
	}

	public EstadoCredito getEstadoCreditoPrimero() {
		return estadoCreditoPrimero;
	}
	public void setEstadoCreditoPrimero(EstadoCredito estadoCreditoPrimero) {
		this.estadoCreditoPrimero = estadoCreditoPrimero;
	}
	
	public SocioComp getSocioComp() {
		return socioComp;
	}
	public void setSocioComp(SocioComp socioComp) {
		this.socioComp = socioComp;
	}
	
	public List<Estructura> getListaEstructuraSocio() {
		return listaEstructuraSocio;
	}
	public void setListaEstructuraSocio(List<Estructura> listaEstructuraSocio) {
		this.listaEstructuraSocio = listaEstructuraSocio;
	}
	public Integer getIntPersEmpresaSucAdministra() {
		return intPersEmpresaSucAdministra;
	}
	public void setIntPersEmpresaSucAdministra(Integer intPersEmpresaSucAdministra) {
		this.intPersEmpresaSucAdministra = intPersEmpresaSucAdministra;
	}
	public Integer getIntSucuIdSucursalAdministra() {
		return intSucuIdSucursalAdministra;
	}
	public void setIntSucuIdSucursalAdministra(Integer intSucuIdSucursalAdministra) {
		this.intSucuIdSucursalAdministra = intSucuIdSucursalAdministra;
	}
	public Integer getIntSudeIdSubSucursalAdministra() {
		return intSudeIdSubSucursalAdministra;
	}
	public void setIntSudeIdSubSucursalAdministra(
			Integer intSudeIdSubSucursalAdministra) {
		this.intSudeIdSubSucursalAdministra = intSudeIdSubSucursalAdministra;
	}
	public Integer getIntItemEstadoUltimo() {
		return intItemEstadoUltimo;
	}
	public void setIntItemEstadoUltimo(Integer intItemEstadoUltimo) {
		this.intItemEstadoUltimo = intItemEstadoUltimo;
	}
	public Integer getIntEstadoCreditoUltimo() {
		return intEstadoCreditoUltimo;
	}
	public void setIntEstadoCreditoUltimo(Integer intEstadoCreditoUltimo) {
		this.intEstadoCreditoUltimo = intEstadoCreditoUltimo;
	}
	public String getStrDesccripcionEstadoUltimo() {
		return strDesccripcionEstadoUltimo;
	}
	public void setStrDesccripcionEstadoUltimo(String strDesccripcionEstadoUltimo) {
		this.strDesccripcionEstadoUltimo = strDesccripcionEstadoUltimo;
	}
	public Integer getIntSucursalEstadoUltimo() {
		return intSucursalEstadoUltimo;
	}
	public void setIntSucursalEstadoUltimo(Integer intSucursalEstadoUltimo) {
		this.intSucursalEstadoUltimo = intSucursalEstadoUltimo;
	}
	public Integer getIntItemEstadoInicial() {
		return intItemEstadoInicial;
	}
	public void setIntItemEstadoInicial(Integer intItemEstadoInicial) {
		this.intItemEstadoInicial = intItemEstadoInicial;
	}
	public Integer getIntEstadoCreditoInicial() {
		return intEstadoCreditoInicial;
	}
	public void setIntEstadoCreditoInicial(Integer intEstadoCreditoInicial) {
		this.intEstadoCreditoInicial = intEstadoCreditoInicial;
	}
	public String getStrDesccripcionEstadoInicial() {
		return strDesccripcionEstadoInicial;
	}
	public void setStrDesccripcionEstadoInicial(String strDesccripcionEstadoInicial) {
		this.strDesccripcionEstadoInicial = strDesccripcionEstadoInicial;
	}
	public Integer getIntSucursalEstadoInicial() {
		return intSucursalEstadoInicial;
	}
	public void setIntSucursalEstadoInicial(Integer intSucursalEstadoInicial) {
		this.intSucursalEstadoInicial = intSucursalEstadoInicial;
	}
	public String getStrNroCuentaExpediente() {
		return strNroCuentaExpediente;
	}
	public void setStrNroCuentaExpediente(String strNroCuentaExpediente) {
		this.strNroCuentaExpediente = strNroCuentaExpediente;
	}
	public String getStrNombreCompletoPersona() {
		return strNombreCompletoPersona;
	}
	public void setStrNombreCompletoPersona(String strNombreCompletoPersona) {
		this.strNombreCompletoPersona = strNombreCompletoPersona;
	}
	public String getStrDescripcionCreditoEmpresa() {
		return strDescripcionCreditoEmpresa;
	}
	public void setStrDescripcionCreditoEmpresa(String strDescripcionCreditoEmpresa) {
		this.strDescripcionCreditoEmpresa = strDescripcionCreditoEmpresa;
	}
	public Integer getIntParaTipoCreditoEmpresa() {
		return intParaTipoCreditoEmpresa;
	}
	public void setIntParaTipoCreditoEmpresa(Integer intParaTipoCreditoEmpresa) {
		this.intParaTipoCreditoEmpresa = intParaTipoCreditoEmpresa;
	}
	@Override
	public String toString() {
		return "ExpedienteCredito [id=" + id + ", intParaSubTipoOperacionCod="
				+ intParaSubTipoOperacionCod + ", intPersEmpresaCreditoPk="
				+ intPersEmpresaCreditoPk + ", intParaTipoCreditoCod="
				+ intParaTipoCreditoCod + ", intItemCredito=" + intItemCredito
				+ ", bdPorcentajeInteres=" + bdPorcentajeInteres
				+ ", bdPorcentajeGravamen=" + bdPorcentajeGravamen
				+ ", bdPorcentajeAporte=" + bdPorcentajeAporte
				+ ", bdMontoTotal=" + bdMontoTotal + ", bdMontoGravamen="
				+ bdMontoGravamen + ", bdMontoAporte=" + bdMontoAporte
				+ ", bdMontoInteresAtrasado=" + bdMontoInteresAtrasado
				+ ", bdMontoMoraAtrasada=" + bdMontoMoraAtrasada
				+ ", bdMontoSolicitado=" + bdMontoSolicitado
				+ ", intNumeroCuota=" + intNumeroCuota
				+ ", intParaFinalidadCod=" + intParaFinalidadCod
				+ ", intPersEmpresaRefPk=" + intPersEmpresaRefPk
				+ ", intCuentaRefPk=" + intCuentaRefPk
				+ ", intItemExpedienteRef=" + intItemExpedienteRef
				+ ", intItemDetExpedienteRef=" + intItemDetExpedienteRef
				+ ", intParaDocumentoGeneralCod=" + intParaDocumentoGeneralCod
				+ ", strObservacion=" + strObservacion + "]";
	}
	public Integer getIntPeriodoCartera() {
		return intPeriodoCartera;
	}
	public void setIntPeriodoCartera(Integer intPeriodoCartera) {
		this.intPeriodoCartera = intPeriodoCartera;
	}
	public Integer getIntParaTipoCategoriaRiesgo() {
		return intParaTipoCategoriaRiesgo;
	}
	public void setIntParaTipoCategoriaRiesgo(Integer intParaTipoCategoriaRiesgo) {
		this.intParaTipoCategoriaRiesgo = intParaTipoCategoriaRiesgo;
	}
	public List<ModeloDetalleNivelComp> getListaModeloDetalleNivelComp() {
		return listaModeloDetalleNivelComp;
	}
	public void setListaModeloDetalleNivelComp(
			List<ModeloDetalleNivelComp> listaModeloDetalleNivelComp) {
		this.listaModeloDetalleNivelComp = listaModeloDetalleNivelComp;
	}
	public ExpedienteCredito getExpedienteCreditoCancelacion() {
		return expedienteCreditoCancelacion;
	}
	public void setExpedienteCreditoCancelacion(
			ExpedienteCredito expedienteCreditoCancelacion) {
		this.expedienteCreditoCancelacion = expedienteCreditoCancelacion;
	}
	public List<CronogramaCredito> getListaCronogramaCreditoRegenerado() {
		return listaCronogramaCreditoRegenerado;
	}
	public void setListaCronogramaCreditoRegenerado(
			List<CronogramaCredito> listaCronogramaCreditoRegenerado) {
		this.listaCronogramaCreditoRegenerado = listaCronogramaCreditoRegenerado;
	}
	public Boolean getGeneraAporte() {
		return generaAporte;
	}
	public void setGeneraAporte(Boolean generaAporte) {
		this.generaAporte = generaAporte;
	}
	public List<RequisitoCredito> getLstRequisitoCreditos() {
		return lstRequisitoCreditos;
	}
	public void setLstRequisitoCreditos(List<RequisitoCredito> lstRequisitoCreditos) {
		this.lstRequisitoCreditos = lstRequisitoCreditos;
	}
	public Archivo getArchivoGiro() {
		return archivoGiro;
	}
	public void setArchivoGiro(Archivo archivoGiro) {
		this.archivoGiro = archivoGiro;
	}
	public Integer getIntEsGiroPorSedeCentral() {
		return intEsGiroPorSedeCentral;
	}
	public void setIntEsGiroPorSedeCentral(Integer intEsGiroPorSedeCentral) {
		this.intEsGiroPorSedeCentral = intEsGiroPorSedeCentral;
	}
}