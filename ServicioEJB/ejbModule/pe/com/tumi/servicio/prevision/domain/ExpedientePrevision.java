package pe.com.tumi.servicio.prevision.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
//import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

public class ExpedientePrevision extends TumiDomain{

	private ExpedientePrevisionId	id;
	private	Integer intParaDocumentoGeneral;
	private	Integer intParaSubTipoOperacion;
	private	Integer intPersEmpresa;
	private	Integer intParaTipoCaptacion;
	private	Integer intItem;
	private	Date 	dtFechaFallecimiento;
	private	Integer intBeneficiarioRel;
	private	Date 	dtFechaSustentoMedico;
	private	BigDecimal bdMontoBrutoBeneficio;
	private	BigDecimal bdMontoGastosADM;
	private	BigDecimal bdMontoInteresBeneficio;
	private	BigDecimal bdMontoNetoBeneficio;
	private	BigDecimal bdMontoCuotasFondo;
	private	Integer intNumeroCuotaFondo;
	private	String 	strObservacion;
	private	Integer intSucuIdSucursal;
	private	Integer intSudeIdsubsucursal;
	
	private List<EstadoPrevision> listaEstadoPrevision;
	private List<BeneficiarioPrevision> listaBeneficiarioPrevision;
	private Cuenta	cuenta;
	private EstadoPrevision	estadoPrevisionUltimo;
	private EstadoPrevision	estadoPrevisionPrimero;
	private EstadoPrevision	estadoPrevisionAprobado;
	private Persona	personaAdministra;
	private Captacion captacion;
	private Egreso	egreso;
	private BeneficiarioPrevision	beneficiarioPrevisionGirar;
	private Persona	personaGirar;
	private String	strGlosaEgreso;
	private List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz;
	private List<RequisitoPrevisionComp> listaRequisitoPrevisionComp;
	private List<FallecidoPrevision> listaFallecidoPrevision;
	private List<AutorizaPrevision> listaAutorizaPrevision;
	private List<AutorizaVerificaPrevision> listaAutorizaVerificaPrevision;
	
	//NUEVOS CAMPOS - CGD 18.09.2013
	private Integer		intPersEmpresaSucAdministra;
	private Integer		intSucuIdSucursalAdministra;
	private Integer		intSudeIdSubSucursalAdministra;
	
	// CGD - 23.10.2013
	private Integer		intItemEstadoUltimo;
	private Integer		intEstadoCreditoUltimo;
	private Integer		intSucursalEstadoUltimo;
	private Integer		intItemEstadoInicial;
	private Integer		intEstadoCreditoInicial;
	private Integer		intSucursalEstadoInicial;
	private String		strNroCuentaExpediente;
	private String		strNombreCompletoPersona;

	//JCHAVEZ 07.02.2014
	private Integer intParaTipoOperacion;
	private Archivo archivoGrio;
	
	//jchavez 19.05.2014
	private Movimiento movimiento;
	
	public ExpedientePrevision(){
		id = new ExpedientePrevisionId();
	}
	
	public ExpedientePrevisionId getId() {
		return id;
	}
	public void setId(ExpedientePrevisionId id) {
		this.id = id;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Integer getIntParaSubTipoOperacion() {
		return intParaSubTipoOperacion;
	}
	public void setIntParaSubTipoOperacion(Integer intParaSubTipoOperacion) {
		this.intParaSubTipoOperacion = intParaSubTipoOperacion;
	}
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntParaTipoCaptacion() {
		return intParaTipoCaptacion;
	}
	public void setIntParaTipoCaptacion(Integer intParaTipoCaptacion) {
		this.intParaTipoCaptacion = intParaTipoCaptacion;
	}
	public Integer getIntItem() {
		return intItem;
	}
	public void setIntItem(Integer intItem) {
		this.intItem = intItem;
	}
	public Date getDtFechaFallecimiento() {
		return dtFechaFallecimiento;
	}
	public void setDtFechaFallecimiento(Date dtFechaFallecimiento) {
		this.dtFechaFallecimiento = dtFechaFallecimiento;
	}
	public Integer getIntBeneficiarioRel() {
		return intBeneficiarioRel;
	}
	public void setIntBeneficiarioRel(Integer intBeneficiarioRel) {
		this.intBeneficiarioRel = intBeneficiarioRel;
	}
	public Date getDtFechaSustentoMedico() {
		return dtFechaSustentoMedico;
	}
	public void setDtFechaSustentoMedico(Date dtFechaSustentoMedico) {
		this.dtFechaSustentoMedico = dtFechaSustentoMedico;
	}
	public BigDecimal getBdMontoBrutoBeneficio() {
		return bdMontoBrutoBeneficio;
	}
	public void setBdMontoBrutoBeneficio(BigDecimal bdMontoBrutoBeneficio) {
		this.bdMontoBrutoBeneficio = bdMontoBrutoBeneficio;
	}
	public BigDecimal getBdMontoGastosADM() {
		return bdMontoGastosADM;
	}
	public void setBdMontoGastosADM(BigDecimal bdMontoGastosADM) {
		this.bdMontoGastosADM = bdMontoGastosADM;
	}
	public BigDecimal getBdMontoInteresBeneficio() {
		return bdMontoInteresBeneficio;
	}
	public void setBdMontoInteresBeneficio(BigDecimal bdMontoInteresBeneficio) {
		this.bdMontoInteresBeneficio = bdMontoInteresBeneficio;
	}
	public BigDecimal getBdMontoNetoBeneficio() {
		return bdMontoNetoBeneficio;
	}
	public void setBdMontoNetoBeneficio(BigDecimal bdMontoNetoBeneficio) {
		this.bdMontoNetoBeneficio = bdMontoNetoBeneficio;
	}
	public BigDecimal getBdMontoCuotasFondo() {
		return bdMontoCuotasFondo;
	}
	public void setBdMontoCuotasFondo(BigDecimal bdMontoCuotasFondo) {
		this.bdMontoCuotasFondo = bdMontoCuotasFondo;
	}
	public Integer getIntNumeroCuotaFondo() {
		return intNumeroCuotaFondo;
	}
	public void setIntNumeroCuotaFondo(Integer intNumeroCuotaFondo) {
		this.intNumeroCuotaFondo = intNumeroCuotaFondo;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	public List<EstadoPrevision> getListaEstadoPrevision() {
		return listaEstadoPrevision;
	}
	public void setListaEstadoPrevision(List<EstadoPrevision> listaEstadoPrevision) {
		this.listaEstadoPrevision = listaEstadoPrevision;
	}
	public List<BeneficiarioPrevision> getListaBeneficiarioPrevision() {
		return listaBeneficiarioPrevision;
	}
	public void setListaBeneficiarioPrevision(List<BeneficiarioPrevision> listaBeneficiarioPrevision) {
		this.listaBeneficiarioPrevision = listaBeneficiarioPrevision;
	}
	public EstadoPrevision getEstadoPrevisionUltimo() {
		return estadoPrevisionUltimo;
	}
	public void setEstadoPrevisionUltimo(EstadoPrevision estadoPrevisionUltimo) {
		this.estadoPrevisionUltimo = estadoPrevisionUltimo;
	}
	
	public EstadoPrevision getEstadoPrevisionPrimero() {
		return estadoPrevisionPrimero;
	}

	public void setEstadoPrevisionPrimero(EstadoPrevision estadoPrevisionPrimero) {
		this.estadoPrevisionPrimero = estadoPrevisionPrimero;
	}

	public EstadoPrevision getEstadoPrevisionAprobado() {
		return estadoPrevisionAprobado;
	}
	public void setEstadoPrevisionAprobado(EstadoPrevision estadoPrevisionAprobado) {
		this.estadoPrevisionAprobado = estadoPrevisionAprobado;
	}
	public Persona getPersonaAdministra() {
		return personaAdministra;
	}
	public void setPersonaAdministra(Persona personaAdministra) {
		this.personaAdministra = personaAdministra;
	}
	public Integer getIntSudeIdsubsucursal() {
		return intSudeIdsubsucursal;
	}
	public void setIntSudeIdsubsucursal(Integer intSudeIdsubsucursal) {
		this.intSudeIdsubsucursal = intSudeIdsubsucursal;
	}
	public Captacion getCaptacion() {
		return captacion;
	}
	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}
	public Egreso getEgreso() {
		return egreso;
	}
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	public BeneficiarioPrevision getBeneficiarioPrevisionGirar() {
		return beneficiarioPrevisionGirar;
	}
	public void setBeneficiarioPrevisionGirar(BeneficiarioPrevision beneficiarioPrevisionGirar) {
		this.beneficiarioPrevisionGirar = beneficiarioPrevisionGirar;
	}
	public Persona getPersonaGirar() {
		return personaGirar;
	}
	public void setPersonaGirar(Persona personaGirar) {
		this.personaGirar = personaGirar;
	}	
	public String getStrGlosaEgreso() {
		return strGlosaEgreso;
	}
	public void setStrGlosaEgreso(String strGlosaEgreso) {
		this.strGlosaEgreso = strGlosaEgreso;
	}
	public List<EgresoDetalleInterfaz> getListaEgresoDetalleInterfaz() {
		return listaEgresoDetalleInterfaz;
	}
	public void setListaEgresoDetalleInterfaz(
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz) {
		this.listaEgresoDetalleInterfaz = listaEgresoDetalleInterfaz;
	}
	public List<RequisitoPrevisionComp> getListaRequisitoPrevisionComp() {
		return listaRequisitoPrevisionComp;
	}
	public void setListaRequisitoPrevisionComp(
			List<RequisitoPrevisionComp> listaRequisitoPrevisionComp) {
		this.listaRequisitoPrevisionComp = listaRequisitoPrevisionComp;
	}

	public List<FallecidoPrevision> getListaFallecidoPrevision() {
		return listaFallecidoPrevision;
	}

	public void setListaFallecidoPrevision(
			List<FallecidoPrevision> listaFallecidoPrevision) {
		this.listaFallecidoPrevision = listaFallecidoPrevision;
	}

	public List<AutorizaPrevision> getListaAutorizaPrevision() {
		return listaAutorizaPrevision;
	}

	public void setListaAutorizaPrevision(
			List<AutorizaPrevision> listaAutorizaPrevision) {
		this.listaAutorizaPrevision = listaAutorizaPrevision;
	}

	public List<AutorizaVerificaPrevision> getListaAutorizaVerificaPrevision() {
		return listaAutorizaVerificaPrevision;
	}

	public void setListaAutorizaVerificaPrevision(
			List<AutorizaVerificaPrevision> listaAutorizaVerificaPrevision) {
		this.listaAutorizaVerificaPrevision = listaAutorizaVerificaPrevision;
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

	@Override
	public String toString() {
		return "ExpedientePrevision [id=" + id + ", intParaDocumentoGeneral="
				+ intParaDocumentoGeneral + ", intParaSubTipoOperacion="
				+ intParaSubTipoOperacion + ", intPersEmpresa="
				+ intPersEmpresa + ", intParaTipoCaptacion="
				+ intParaTipoCaptacion + ", intItem=" + intItem
				+ ", dtFechaFallecimiento=" + dtFechaFallecimiento
				+ ", intBeneficiarioRel=" + intBeneficiarioRel
				+ ", dtFechaSustentoMedico=" + dtFechaSustentoMedico
				+ ", bdMontoBrutoBeneficio=" + bdMontoBrutoBeneficio
				+ ", bdMontoGastosADM=" + bdMontoGastosADM
				+ ", bdMontoInteresBeneficio=" + bdMontoInteresBeneficio
				+ ", bdMontoNetoBeneficio=" + bdMontoNetoBeneficio
				+ ", bdMontoCuotasFondo=" + bdMontoCuotasFondo
				+ ", intNumeroCuotaFondo=" + intNumeroCuotaFondo
				+ ", strObservacion=" + strObservacion + ", intSucuIdSucursal="
				+ intSucuIdSucursal + ", intSudeIdsubsucursal="
				+ intSudeIdsubsucursal + "]";
	}

	public Integer getIntParaTipoOperacion() {
		return intParaTipoOperacion;
	}
	public void setIntParaTipoOperacion(Integer intParaTipoOperacion) {
		this.intParaTipoOperacion = intParaTipoOperacion;
	}

	public Archivo getArchivoGrio() {
		return archivoGrio;
	}

	public void setArchivoGrio(Archivo archivoGrio) {
		this.archivoGrio = archivoGrio;
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}	
}