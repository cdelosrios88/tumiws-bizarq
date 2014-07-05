package pe.com.tumi.contabilidad.operaciones.domain;

import java.math.BigDecimal;

import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.persona.core.domain.Persona;

public class HojaManualDetalle extends TumiDomain {
	private HojaManualDetalleId id;
	private Integer intPersEmpresaCuentaPk;
	private Integer intContPeriodoCuenta;
	private String strContNumeroCuenta;
	private Integer intPersPersonaPk;
	private Integer intParaDocumentoGeneralCod;
	private String strHmdeSerieDocumento;
	private String strHmdeNumeroDocumento;
	private Integer intParaMonedaDocumento;
	private Integer intHmdeTipoCambio;
	private BigDecimal bdHmdeDebeSoles;
	private BigDecimal bdHmdeHaberSoles;
	private BigDecimal bdHmdeDebeExtranjero;
	private BigDecimal bdHmdeHaberExtranjero;
	private Integer intPersEmpresaSucursalPk;
	private Integer intSucuIdSucursalPk;
	private Integer intSudeIdSubsucursalPk;
	
	private HojaManual hojaManual;
	private PlanCuenta planCuenta;
	private Persona persona;
	private TipoCambio tipoCambio;
	
	private Integer intOpcionDebeHaber;
	private BigDecimal bdMontoSoles;
	private BigDecimal bdMonedaExtranjero;
	
	private Boolean isDeleted; //Indica si se va a eliminar físicamente. Se usa para mostrar u ocultar en el formulario.
	
	
	public HojaManualDetalle(){
		this.id = new HojaManualDetalleId();
	}
	public HojaManualDetalleId getId() {
		return id;
	}
	public void setId(HojaManualDetalleId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaCuentaPk() {
		return intPersEmpresaCuentaPk;
	}
	public void setIntPersEmpresaCuentaPk(Integer intPersEmpresaCuentaPk) {
		this.intPersEmpresaCuentaPk = intPersEmpresaCuentaPk;
	}
	public Integer getIntContPeriodoCuenta() {
		return intContPeriodoCuenta;
	}
	public void setIntContPeriodoCuenta(Integer intContPeriodoCuenta) {
		this.intContPeriodoCuenta = intContPeriodoCuenta;
	}
	public String getStrContNumeroCuenta() {
		return strContNumeroCuenta;
	}
	public void setStrContNumeroCuenta(String strContNumeroCuenta) {
		this.strContNumeroCuenta = strContNumeroCuenta;
	}
	public Integer getIntPersPersonaPk() {
		return intPersPersonaPk;
	}
	public void setIntPersPersonaPk(Integer intPersPersonaPk) {
		this.intPersPersonaPk = intPersPersonaPk;
	}
	public Integer getIntParaDocumentoGeneralCod() {
		return intParaDocumentoGeneralCod;
	}
	public void setIntParaDocumentoGeneralCod(Integer intParaDocumentoGeneralCod) {
		this.intParaDocumentoGeneralCod = intParaDocumentoGeneralCod;
	}
	public String getStrHmdeSerieDocumento() {
		return strHmdeSerieDocumento;
	}
	public void setStrHmdeSerieDocumento(String strHmdeSerieDocumento) {
		this.strHmdeSerieDocumento = strHmdeSerieDocumento;
	}
	public String getStrHmdeNumeroDocumento() {
		return strHmdeNumeroDocumento;
	}
	public void setStrHmdeNumeroDocumento(String strHmdeNumeroDocumento) {
		this.strHmdeNumeroDocumento = strHmdeNumeroDocumento;
	}
	public Integer getIntParaMonedaDocumento() {
		return intParaMonedaDocumento;
	}
	public void setIntParaMonedaDocumento(Integer intParaMonedaDocumento) {
		this.intParaMonedaDocumento = intParaMonedaDocumento;
	}
	public Integer getIntHmdeTipoCambio() {
		return intHmdeTipoCambio;
	}
	public void setIntHmdeTipoCambio(Integer intHmdeTipoCambio) {
		this.intHmdeTipoCambio = intHmdeTipoCambio;
	}
	public BigDecimal getBdHmdeDebeSoles() {
		return bdHmdeDebeSoles;
	}
	public void setBdHmdeDebeSoles(BigDecimal bdHmdeDebeSoles) {
		this.bdHmdeDebeSoles = bdHmdeDebeSoles;
	}
	public BigDecimal getBdHmdeHaberSoles() {
		return bdHmdeHaberSoles;
	}
	public void setBdHmdeHaberSoles(BigDecimal bdHmdeHaberSoles) {
		this.bdHmdeHaberSoles = bdHmdeHaberSoles;
	}
	public BigDecimal getBdHmdeDebeExtranjero() {
		return bdHmdeDebeExtranjero;
	}
	public void setBdHmdeDebeExtranjero(BigDecimal bdHmdeDebeExtranjero) {
		this.bdHmdeDebeExtranjero = bdHmdeDebeExtranjero;
	}
	public BigDecimal getBdHmdeHaberExtranjero() {
		return bdHmdeHaberExtranjero;
	}
	public void setBdHmdeHaberExtranjero(BigDecimal bdHmdeHaberExtranjero) {
		this.bdHmdeHaberExtranjero = bdHmdeHaberExtranjero;
	}
	public Integer getIntPersEmpresaSucursalPk() {
		return intPersEmpresaSucursalPk;
	}
	public void setIntPersEmpresaSucursalPk(Integer intPersEmpresaSucursalPk) {
		this.intPersEmpresaSucursalPk = intPersEmpresaSucursalPk;
	}
	public Integer getIntSucuIdSucursalPk() {
		return intSucuIdSucursalPk;
	}
	public void setIntSucuIdSucursalPk(Integer intSucuIdSucursalPk) {
		this.intSucuIdSucursalPk = intSucuIdSucursalPk;
	}
	public Integer getIntSudeIdSubsucursalPk() {
		return intSudeIdSubsucursalPk;
	}
	public void setIntSudeIdSubsucursalPk(Integer intSudeIdSubsucursalPk) {
		this.intSudeIdSubsucursalPk = intSudeIdSubsucursalPk;
	}
	public HojaManual getHojaManual() {
		return hojaManual;
	}
	public void setHojaManual(HojaManual hojaManual) {
		this.hojaManual = hojaManual;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public PlanCuenta getPlanCuenta() {
		return planCuenta;
	}
	public void setPlanCuenta(PlanCuenta planCuenta) {
		this.planCuenta = planCuenta;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public TipoCambio getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(TipoCambio tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	public Integer getIntOpcionDebeHaber() {
		return intOpcionDebeHaber;
	}
	public void setIntOpcionDebeHaber(Integer intOpcionDebeHaber) {
		this.intOpcionDebeHaber = intOpcionDebeHaber;
	}
	public BigDecimal getBdMontoSoles() {
		return bdMontoSoles;
	}
	public void setBdMontoSoles(BigDecimal bdMontoSoles) {
		this.bdMontoSoles = bdMontoSoles;
	}
	public BigDecimal getBdMonedaExtranjero() {
		return bdMonedaExtranjero;
	}
	public void setBdMonedaExtranjero(BigDecimal bdMonedaExtranjero) {
		this.bdMonedaExtranjero = bdMonedaExtranjero;
	}
}
