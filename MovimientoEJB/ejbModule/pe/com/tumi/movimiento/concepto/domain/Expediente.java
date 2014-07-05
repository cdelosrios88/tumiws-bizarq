package pe.com.tumi.movimiento.concepto.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Expediente extends TumiDomain{

	private ExpedienteId id;
	private Integer intPersEmpresaCreditoPk;
	private Integer intParaTipoCreditoCod;
	private Integer intItemCredito;
	private BigDecimal bdPorcentajeInteres;
	private BigDecimal bdPorcentajeGravamen;
	private BigDecimal bdPorcentajeAporte;
	private BigDecimal bdMontoInteresAtrazado;
	private BigDecimal bdMontoMoraAtrazado;
	private BigDecimal bdMontoSolicitado;
	private BigDecimal bdMontoTotal;
	private Integer intNumeroCuota;
	private BigDecimal bdSaldoCredito;
	private BigDecimal bdSaldoInteres;
	private BigDecimal bdSaldoMora;
	private BigDecimal bdPorcentajeMora;
	
	//NUEVOS CAMPOS - CGD 18.09.2013
	private Integer		intPersEmpresaSucAdministra;
	private Integer		intSucuIdSucursalAdministra;
	private Integer		intSudeIdSubSucursalAdministra;
	
	private List<BloqueoCuenta> listaBloqueoCuenta;
	private List<Cronograma> listaCronograma;
	
	//Auxiliar
	private String strDescripcion;
	private BigDecimal bdSaldoCreditoSoles;
	private BigDecimal bdSaldoInteresSoles;
	private BigDecimal bdSaldoMoraSoles;
	private Integer    intOrdenPrioridad;
	private BigDecimal bdMontoSaldoDetalle;
	private BigDecimal bdMontoAbono;
	
	private List<EstadoExpediente> listaEstadosExpediente;
	private List<InteresProvisionado> listaInteresProvisionado; 
	private List<InteresCancelado> listaInteresCancelado;
	
	private Movimiento movimiento;
	
	//Auxiliar para Cobranza efectuado agregarEnviado 
	private Integer intMes;
	private BigDecimal bdprimeraCuota;
	private BigDecimal bdTotalAmortizacion;
	private BigDecimal bdTotalInteres;
	private Integer intAnio;
	
	public Expediente(){
		id = new ExpedienteId();
		listaInteresProvisionado = new ArrayList<InteresProvisionado>();
	}
	
	public ExpedienteId getId() {
		return id;
	}
	public void setId(ExpedienteId id) {
		this.id = id;
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
	public BigDecimal getBdMontoInteresAtrazado() {
		return bdMontoInteresAtrazado;
	}
	public void setBdMontoInteresAtrazado(BigDecimal bdMontoInteresAtrazado) {
		this.bdMontoInteresAtrazado = bdMontoInteresAtrazado;
	}
	public BigDecimal getBdMontoMoraAtrazado() {
		return bdMontoMoraAtrazado;
	}
	public void setBdMontoMoraAtrazado(BigDecimal bdMontoMoraAtrazado) {
		this.bdMontoMoraAtrazado = bdMontoMoraAtrazado;
	}
	public BigDecimal getBdPorcentajeAporte() {
		return bdPorcentajeAporte;
	}
	public void setBdPorcentajeAporte(BigDecimal bdPorcentajeAporte) {
		this.bdPorcentajeAporte = bdPorcentajeAporte;
	}
	public BigDecimal getBdMontoSolicitado() {
		return bdMontoSolicitado;
	}
	public void setBdMontoSolicitado(BigDecimal bdMontoSolicitado) {
		this.bdMontoSolicitado = bdMontoSolicitado;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public Integer getIntNumeroCuota() {
		return intNumeroCuota;
	}
	public void setIntNumeroCuota(Integer intNumeroCuota) {
		this.intNumeroCuota = intNumeroCuota;
	}
	public BigDecimal getBdSaldoCredito() {
		return bdSaldoCredito;
	}
	public void setBdSaldoCredito(BigDecimal bdSaldoCredito) {
		this.bdSaldoCredito = bdSaldoCredito;
	}
	public BigDecimal getBdSaldoInteres() {
		return bdSaldoInteres;
	}
	public void setBdSaldoInteres(BigDecimal bdSaldoInteres) {
		this.bdSaldoInteres = bdSaldoInteres;
	}
	public BigDecimal getBdSaldoMora() {
		return bdSaldoMora;
	}
	public void setBdSaldoMora(BigDecimal bdSaldoMora) {
		this.bdSaldoMora = bdSaldoMora;
	}
	public List<BloqueoCuenta> getListaBloqueoCuenta() {
		return listaBloqueoCuenta;
	}
	public void setListaBloqueoCuenta(List<BloqueoCuenta> listaBloqueoCuenta) {
		this.listaBloqueoCuenta = listaBloqueoCuenta;
	}
	public List<Cronograma> getListaCronograma() {
		return listaCronograma;
	}
	public void setListaCronograma(List<Cronograma> listaCronograma) {
		this.listaCronograma = listaCronograma;
	}
	
	public BigDecimal getBdPorcentajeMora() {
		return bdPorcentajeMora;
	}
	public void setBdPorcentajeMora(BigDecimal bdPorcentajeMora) {
		this.bdPorcentajeMora = bdPorcentajeMora;
	}
	
	public String getStrDescripcion() {
		return strDescripcion;
	}

	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}

	
	public BigDecimal getBdSaldoCreditoSoles() {
		return bdSaldoCreditoSoles;
	}

	public void setBdSaldoCreditoSoles(BigDecimal bdSaldoCreditoSoles) {
		this.bdSaldoCreditoSoles = bdSaldoCreditoSoles;
	}

	
	
	public BigDecimal getBdSaldoInteresSoles() {
		return bdSaldoInteresSoles;
	}

	public void setBdSaldoInteresSoles(BigDecimal bdSaldoInteresSoles) {
		this.bdSaldoInteresSoles = bdSaldoInteresSoles;
	}

	public BigDecimal getBdSaldoMoraSoles() {
		return bdSaldoMoraSoles;
	}

	public void setBdSaldoMoraSoles(BigDecimal bdSaldoMoraSoles) {
		this.bdSaldoMoraSoles = bdSaldoMoraSoles;
	}
	
	

	public Integer getIntOrdenPrioridad() {
		return intOrdenPrioridad;
	}

	public void setIntOrdenPrioridad(Integer intOrdenPrioridad) {
		this.intOrdenPrioridad = intOrdenPrioridad;
	}

	
	
	public BigDecimal getBdMontoAbono() {
		return bdMontoAbono;
	}

	public void setBdMontoAbono(BigDecimal bdMontoAbono) {
		this.bdMontoAbono = bdMontoAbono;
	}
	
	public BigDecimal getBdMontoSaldoDetalle() {
		return bdMontoSaldoDetalle;
	}

	public void setBdMontoSaldoDetalle(BigDecimal bdMontoSaldoDetalle) {
		this.bdMontoSaldoDetalle = bdMontoSaldoDetalle;
	}

	public List<EstadoExpediente> getListaEstadosExpediente() {
		return listaEstadosExpediente;
	}

	public void setListaEstadosExpediente(
			List<EstadoExpediente> listaEstadosExpediente) {
		this.listaEstadosExpediente = listaEstadosExpediente;
	}
	
	public List<InteresProvisionado> getListaInteresProvisionado() {
		return listaInteresProvisionado;
	}
	
	public void setListaInteresProvisionado(
			List<InteresProvisionado> listaInteresProvisionado) {
		this.listaInteresProvisionado = listaInteresProvisionado;
	}
	
	public List<InteresCancelado> getListaInteresCancelado() {
		return listaInteresCancelado;
	}
	
	public void setListaInteresCancelado(
			List<InteresCancelado> listaInteresCancelado) {
		this.listaInteresCancelado = listaInteresCancelado;
	}
	
	public Movimiento getMovimiento() {
		return movimiento;
	}
	
	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
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
	
	public Integer getIntMes() {
		return intMes;
	}

	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}
	
	
	
	public BigDecimal getBdprimeraCuota() {
		return bdprimeraCuota;
	}

	public void setBdprimeraCuota(BigDecimal bdprimeraCuota) {
		this.bdprimeraCuota = bdprimeraCuota;
	}
	
	public BigDecimal getBdTotalAmortizacion() {
		return bdTotalAmortizacion;
	}

	public void setBdTotalAmortizacion(BigDecimal bdTotalAmortizacion) {
		this.bdTotalAmortizacion = bdTotalAmortizacion;
	}

	public BigDecimal getBdTotalInteres() {
		return bdTotalInteres;
	}

	public void setBdTotalInteres(BigDecimal bdTotalInteres) {
		this.bdTotalInteres = bdTotalInteres;
	}
	
		
	public Integer getIntAnio() {
		return intAnio;
	}

	public void setIntAnio(Integer intAnio) {
		this.intAnio = intAnio;
	}

	@Override
	public String toString() {
		return "Expediente [id=" + id + ", intPersEmpresaCreditoPk="
				+ intPersEmpresaCreditoPk + ", intParaTipoCreditoCod="
				+ intParaTipoCreditoCod + ", intItemCredito=" + intItemCredito
				+ ", bdPorcentajeInteres=" + bdPorcentajeInteres
				+ ", bdPorcentajeGravamen=" + bdPorcentajeGravamen
				+ ", bdMontoInteresAtrazado=" + bdMontoInteresAtrazado
				+ ", bdMontoMoraAtrazado=" + bdMontoMoraAtrazado
				+ ", bdPorcentajeAporte=" + bdPorcentajeAporte
				+ ", bdMontoSolicitado=" + bdMontoSolicitado
				+ ", bdMontoTotal=" + bdMontoTotal + ", intNumeroCuota="
				+ intNumeroCuota + ", bdSaldoCredito=" + bdSaldoCredito
				+ ", bdSaldoInteres=" + bdSaldoInteres + ", bdSaldoMora="
				+ bdSaldoMora + ", bdPorcentajeMora=" + bdPorcentajeMora +     
				", strDescripcion= " + strDescripcion + ", bdSaldoCreditoSoles= " + bdSaldoCreditoSoles +", bdMontoAbono= " + bdMontoAbono +"]";
	}
}

