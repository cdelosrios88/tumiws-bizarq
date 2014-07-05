package pe.com.tumi.servicio.prevision.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.CuentaConceptoComp;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

public class ExpedienteLiquidacion extends TumiDomain{

	private ExpedienteLiquidacionId	id;
	private	Integer intParaDocumentoGeneral;
	private	Integer intParaSubTipoOperacion;
	private	Date 	dtFechaRenuncia;
	private	Date 	dtFechaProgramacion;
	private	Integer intPeriodoUltimoDescuento;
	private	BigDecimal 	bdMontoBrutoLiquidacion;
	private	Integer intParaMotivoRenuncia;
	private	String 	strObservacion;
	private	Integer intSucuIdSucursal;
	private	Integer intSudeIdsubsucursal;
	private Date dtFechaRecepcion;
	
	//NUEVOS CAMPOS - CGD 18.09.2013
	private Integer		intPersEmpresaSucAdministra;
	private Integer		intSucuIdSucursalAdministra;
	private Integer		intSudeIdSubSucursalAdministra;
	
	private List<EstadoLiquidacion> listaEstadoLiquidacion;
	private List<Persona> listaPersona;
	private List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle;
	private	EstadoLiquidacion	estadoLiquidacionUltimo;
	private EstadoLiquidacion	estadoLiquidacionPrimero;


	private Persona	personaAdministra;
	private String	strEtiquetaListaCuentas;
	private Egreso	egreso;
	private List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz;
	private Persona personaGirar;
	private boolean esUltimoBeneficiarioAGirar;
	private Integer intIdPersonaBeneficiarioGirar;
	private	String	strGlosaEgreso;
	
	//private List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacion;
	private List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp;
	
	private	String	strUserRegistro;
	private	String	strFechaUserRegistro;
	private SocioComp socioComp;
	
	private List<AutorizaLiquidacion> listaAutorizaLiquidacion;
	private List<AutorizaVerificaLiquidacion> listaAutorizaVerificaLiquidacion;
	
	
	// CGD -24.10.2013
	private Integer		intItemEstadoUltimo;
	private Integer		intEstadoCreditoUltimo;
	private Integer		intSucursalEstadoUltimo;
	private Integer		intItemEstadoInicial;
	private Integer		intEstadoCreditoInicial;
	private Integer		intSucursalEstadoInicial;
	private String		strNroCuentaExpediente;
	private String		strNombreCompletoPersona;

    //JCHAVEZ 28.01.2014
	private BeneficiarioLiquidacion beneficiarioLiquidacionGirar;
	private Cuenta cuentaLiquidar;
	private Archivo archivoGrio;
	
	//jchavez 19.05.2014
	private Movimiento movimiento;
	private CuentaConceptoComp cuentaConceptoComp;
	
	
	public SocioComp getSocioComp() {
		return socioComp;
	}

	public void setSocioComp(SocioComp socioComp) {
		this.socioComp = socioComp;
	}

	public String getStrUserRegistro() {
		return strUserRegistro;
	}

	public void setStrUserRegistro(String strUserRegistro) {
		this.strUserRegistro = strUserRegistro;
	}

	public String getStrFechaUserRegistro() {
		return strFechaUserRegistro;
	}

	public void setStrFechaUserRegistro(String strFechaUserRegistro) {
		this.strFechaUserRegistro = strFechaUserRegistro;
	}

	public ExpedienteLiquidacion(){
		id = new ExpedienteLiquidacionId();
	}

	public ExpedienteLiquidacionId getId() {
		return id;
	}
	public void setId(ExpedienteLiquidacionId id) {
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
	public Date getDtFechaRenuncia() {
		return dtFechaRenuncia;
	}
	public void setDtFechaRenuncia(Date dtFechaRenuncia) {
		this.dtFechaRenuncia = dtFechaRenuncia;
	}
	public Date getDtFechaProgramacion() {
		return dtFechaProgramacion;
	}
	public void setDtFechaProgramacion(Date dtFechaProgramacion) {
		this.dtFechaProgramacion = dtFechaProgramacion;
	}
	public Integer getIntPeriodoUltimoDescuento() {
		return intPeriodoUltimoDescuento;
	}
	public void setIntPeriodoUltimoDescuento(Integer intPeriodoUltimoDescuento) {
		this.intPeriodoUltimoDescuento = intPeriodoUltimoDescuento;
	}
	public BigDecimal getBdMontoBrutoLiquidacion() {
		return bdMontoBrutoLiquidacion;
	}
	public void setBdMontoBrutoLiquidacion(BigDecimal bdMontoBrutoLiquidacion) {
		this.bdMontoBrutoLiquidacion = bdMontoBrutoLiquidacion;
	}
	public Integer getIntParaMotivoRenuncia() {
		return intParaMotivoRenuncia;
	}
	public void setIntParaMotivoRenuncia(Integer intParaMotivoRenuncia) {
		this.intParaMotivoRenuncia = intParaMotivoRenuncia;
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
	public Integer getIntSudeIdsubsucursal() {
		return intSudeIdsubsucursal;
	}
	public void setIntSudeIdsubsucursal(Integer intSudeIdsubsucursal) {
		this.intSudeIdsubsucursal = intSudeIdsubsucursal;
	}
	public List<EstadoLiquidacion> getListaEstadoLiquidacion() {
		return listaEstadoLiquidacion;
	}
	public void setListaEstadoLiquidacion(
			List<EstadoLiquidacion> listaEstadoLiquidacion) {
		this.listaEstadoLiquidacion = listaEstadoLiquidacion;
	}
	public EstadoLiquidacion getEstadoLiquidacionUltimo() {
		return estadoLiquidacionUltimo;
	}
	public void setEstadoLiquidacionUltimo(EstadoLiquidacion estadoLiquidacionUltimo) {
		this.estadoLiquidacionUltimo = estadoLiquidacionUltimo;
	}
	public Persona getPersonaAdministra() {
		return personaAdministra;
	}
	public void setPersonaAdministra(Persona personaAdministra) {
		this.personaAdministra = personaAdministra;
	}
	public List<ExpedienteLiquidacionDetalle> getListaExpedienteLiquidacionDetalle() {
		return listaExpedienteLiquidacionDetalle;
	}
	public void setListaExpedienteLiquidacionDetalle(List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle) {
		this.listaExpedienteLiquidacionDetalle = listaExpedienteLiquidacionDetalle;
	}
	public String getStrEtiquetaListaCuentas() {
		return strEtiquetaListaCuentas;
	}
	public void setStrEtiquetaListaCuentas(String strEtiquetaListaCuentas) {
		this.strEtiquetaListaCuentas = strEtiquetaListaCuentas;
	}
	public Egreso getEgreso() {
		return egreso;
	}
	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	public List<Persona> getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}
	public List<EgresoDetalleInterfaz> getListaEgresoDetalleInterfaz() {
		return listaEgresoDetalleInterfaz;
	}
	public void setListaEgresoDetalleInterfaz(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz) {
		this.listaEgresoDetalleInterfaz = listaEgresoDetalleInterfaz;
	}
	public Persona getPersonaGirar() {
		return personaGirar;
	}
	public void setPersonaGirar(Persona personaGirar) {
		this.personaGirar = personaGirar;
	}
	public boolean isEsUltimoBeneficiarioAGirar() {
		return esUltimoBeneficiarioAGirar;
	}
	public void setEsUltimoBeneficiarioAGirar(boolean esUltimoBeneficiarioAGirar) {
		this.esUltimoBeneficiarioAGirar = esUltimoBeneficiarioAGirar;
	}
	public Integer getIntIdPersonaBeneficiarioGirar() {
		return intIdPersonaBeneficiarioGirar;
	}
	public void setIntIdPersonaBeneficiarioGirar(Integer intIdPersonaBeneficiarioGirar) {
		this.intIdPersonaBeneficiarioGirar = intIdPersonaBeneficiarioGirar;
	}
	public String getStrGlosaEgreso() {
		return strGlosaEgreso;
	}
	public void setStrGlosaEgreso(String strGlosaEgreso) {
		this.strGlosaEgreso = strGlosaEgreso;
	}
	/*
	public List<BeneficiarioLiquidacion> getListaBeneficiarioLiquidacion() {
		return listaBeneficiarioLiquidacion;
	}

	public void setListaBeneficiarioLiquidacion(
			List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacion) {
		this.listaBeneficiarioLiquidacion = listaBeneficiarioLiquidacion;
	}
	*/
	public Date getDtFechaRecepcion() {
		return dtFechaRecepcion;
	}

	public void setDtFechaRecepcion(Date dtFechaRecepcion) {
		this.dtFechaRecepcion = dtFechaRecepcion;
	}

	public List<RequisitoLiquidacionComp> getListaRequisitoLiquidacionComp() {
		return listaRequisitoLiquidacionComp;
	}

	public void setListaRequisitoLiquidacionComp(
			List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp) {
		this.listaRequisitoLiquidacionComp = listaRequisitoLiquidacionComp;
	}

	public List<AutorizaLiquidacion> getListaAutorizaLiquidacion() {
		return listaAutorizaLiquidacion;
	}

	public void setListaAutorizaLiquidacion(
			List<AutorizaLiquidacion> listaAutorizaLiquidacion) {
		this.listaAutorizaLiquidacion = listaAutorizaLiquidacion;
	}

	public List<AutorizaVerificaLiquidacion> getListaAutorizaVerificaLiquidacion() {
		return listaAutorizaVerificaLiquidacion;
	}

	public void setListaAutorizaVerificaLiquidacion(
			List<AutorizaVerificaLiquidacion> listaAutorizaVerificaLiquidacion) {
		this.listaAutorizaVerificaLiquidacion = listaAutorizaVerificaLiquidacion;
	}

	public EstadoLiquidacion getEstadoLiquidacionPrimero() {
		return estadoLiquidacionPrimero;
	}

	public void setEstadoLiquidacionPrimero(
			EstadoLiquidacion estadoLiquidacionPrimero) {
		this.estadoLiquidacionPrimero = estadoLiquidacionPrimero;
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
		return "ExpedienteLiquidacion [id=" + id + ", intParaDocumentoGeneral="
				+ intParaDocumentoGeneral + ", intParaSubTipoOperacion="
				+ intParaSubTipoOperacion + ", dtFechaRenuncia="
				+ dtFechaRenuncia + ", dtFechaProgramacion="
				+ dtFechaProgramacion + ", intPeriodoUltimoDescuento="
				+ intPeriodoUltimoDescuento + ", bdMontoBrutoLiquidacion="
				+ bdMontoBrutoLiquidacion + ", intParaMotivoRenuncia="
				+ intParaMotivoRenuncia + ", strObservacion=" + strObservacion
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdsubsucursal=" + intSudeIdsubsucursal + "]";
	}
	//JCHAVEZ 28.01.2014
	public BeneficiarioLiquidacion getBeneficiarioLiquidacionGirar() {
		return beneficiarioLiquidacionGirar;
	}
	public void setBeneficiarioLiquidacionGirar(
			BeneficiarioLiquidacion beneficiarioLiquidacionGirar) {
		this.beneficiarioLiquidacionGirar = beneficiarioLiquidacionGirar;
	}
	public Cuenta getCuentaLiquidar() {
		return cuentaLiquidar;
	}
	public void setCuentaLiquidar(Cuenta cuentaLiquidar) {
		this.cuentaLiquidar = cuentaLiquidar;
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

	public CuentaConceptoComp getCuentaConceptoComp() {
		return cuentaConceptoComp;
	}

	public void setCuentaConceptoComp(CuentaConceptoComp cuentaConceptoComp) {
		this.cuentaConceptoComp = cuentaConceptoComp;
	}
	
}