package pe.com.tumi.movimiento.concepto.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaConceptoDetalle extends TumiDomain{

	private CuentaConceptoDetalleId id;
	private CuentaConcepto cuentaConcepto;
	private Timestamp tsInicio;
	private Timestamp tsFin;
	private BigDecimal bdMontoInicial;
	private Integer intParaTipoConceptoCod;
	private Integer intItemConcepto;
	private Integer intSucursalUsuarioPk;
	private Integer intSubsucursalUsuarioPk;
	private Integer intPersPersonaUsuarioPk;
	private BigDecimal bdSaldoDetalle;
	private BigDecimal bdMontoConcepto;
	private Integer intParaTipoDescuentoCod;
	
	private Captacion captacion;
	
	// Creado para proceso de solicitud de prevision - Se calculara temporalmente el peridod de vigencia de ctactodet
	//private Integer intPeriodoVigenciaTemp;
	private Timestamp tsInicioVigenciaTemp;
	private Timestamp tsFinVigenciaTemp;
	
	// Se generaron nuevas tablas: ConceptoPago y ConceptoDetallePago
	private List<ConceptoPago> listaConceptoPago;
	
	//Se genera nuevo campo para saber el motivo de cierre de la cuenta
	private Integer intParaTipoFinConcepto;
	
	public CuentaConceptoDetalleId getId() {
		return id;
	}
	public void setId(CuentaConceptoDetalleId id) {
		this.id = id;
	}
	public CuentaConcepto getCuentaConcepto() {
		return cuentaConcepto;
	}
	public void setCuentaConcepto(CuentaConcepto cuentaConcepto) {
		this.cuentaConcepto = cuentaConcepto;
	}
	public Timestamp getTsInicio() {
		return tsInicio;
	}
	public void setTsInicio(Timestamp tsInicio) {
		this.tsInicio = tsInicio;
	}
	public Timestamp getTsFin() {
		return tsFin;
	}
	public void setTsFin(Timestamp tsFin) {
		this.tsFin = tsFin;
	}
	public BigDecimal getBdMontoInicial() {
		return bdMontoInicial;
	}
	public void setBdMontoInicial(BigDecimal bdMontoInicial) {
		this.bdMontoInicial = bdMontoInicial;
	}
	public Integer getIntParaTipoConceptoCod() {
		return intParaTipoConceptoCod;
	}
	public void setIntParaTipoConceptoCod(Integer intParaTipoConceptoCod) {
		this.intParaTipoConceptoCod = intParaTipoConceptoCod;
	}
	public Integer getIntItemConcepto() {
		return intItemConcepto;
	}
	public void setIntItemConcepto(Integer intItemConcepto) {
		this.intItemConcepto = intItemConcepto;
	}
	public Integer getIntSucursalUsuarioPk() {
		return intSucursalUsuarioPk;
	}
	public void setIntSucursalUsuarioPk(Integer intSucursalUsuarioPk) {
		this.intSucursalUsuarioPk = intSucursalUsuarioPk;
	}
	public Integer getIntSubsucursalUsuarioPk() {
		return intSubsucursalUsuarioPk;
	}
	public void setIntSubsucursalUsuarioPk(Integer intSubsucursalUsuarioPk) {
		this.intSubsucursalUsuarioPk = intSubsucursalUsuarioPk;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public BigDecimal getBdSaldoDetalle() {
		return bdSaldoDetalle;
	}
	public void setBdSaldoDetalle(BigDecimal bdSaldoDetalle) {
		this.bdSaldoDetalle = bdSaldoDetalle;
	}
	public BigDecimal getBdMontoConcepto() {
		return bdMontoConcepto;
	}
	public void setBdMontoConcepto(BigDecimal bdMontoConcepto) {
		this.bdMontoConcepto = bdMontoConcepto;
	}
	public Integer getIntParaTipoDescuentoCod() {
		return intParaTipoDescuentoCod;
	}
	public void setIntParaTipoDescuentoCod(Integer intParaTipoDescuentoCod) {
		this.intParaTipoDescuentoCod = intParaTipoDescuentoCod;
	}
	public Captacion getCaptacion() {
		return captacion;
	}
	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}
	public Timestamp getTsInicioVigenciaTemp() {
		return tsInicioVigenciaTemp;
	}
	public void setTsInicioVigenciaTemp(Timestamp tsInicioVigenciaTemp) {
		this.tsInicioVigenciaTemp = tsInicioVigenciaTemp;
	}
	public Timestamp getTsFinVigenciaTemp() {
		return tsFinVigenciaTemp;
	}
	public void setTsFinVigenciaTemp(Timestamp tsFinVigenciaTemp) {
		this.tsFinVigenciaTemp = tsFinVigenciaTemp;
	}
	public List<ConceptoPago> getListaConceptoPago() {
		return listaConceptoPago;
	}
	public void setListaConceptoPago(List<ConceptoPago> listaConceptoPago) {
		this.listaConceptoPago = listaConceptoPago;
	}
	public Integer getIntParaTipoFinConcepto() {
		return intParaTipoFinConcepto;
	}
	public void setIntParaTipoFinConcepto(Integer intParaTipoFinConcepto) {
		this.intParaTipoFinConcepto = intParaTipoFinConcepto;
	}
	@Override
	public String toString() {
		return "CuentaConceptoDetalle [id=" + id + ", tsInicio=" + tsInicio
				+ ", tsFin=" + tsFin + ", bdMontoInicial=" + bdMontoInicial
				+ ", intParaTipoConceptoCod=" + intParaTipoConceptoCod
				+ ", intItemConcepto=" + intItemConcepto
				+ ", intSucursalUsuarioPk=" + intSucursalUsuarioPk
				+ ", intSubsucursalUsuarioPk=" + intSubsucursalUsuarioPk
				+ ", intPersPersonaUsuarioPk=" + intPersPersonaUsuarioPk
				+ ", bdSaldoDetalle=" + bdSaldoDetalle 
				+ ", bdMontoConcepto=" + bdMontoConcepto 
				+ ", intParaTipoDescuentoCod=" + intParaTipoDescuentoCod 
				+ ", tsInicioVigenciaTemp=" + tsInicioVigenciaTemp
				+ ", tsFinVigenciaTemp=" + tsFinVigenciaTemp
				+ ", intParaTipoFinConcepto=" + intParaTipoFinConcepto +"]";
	}
	
}
