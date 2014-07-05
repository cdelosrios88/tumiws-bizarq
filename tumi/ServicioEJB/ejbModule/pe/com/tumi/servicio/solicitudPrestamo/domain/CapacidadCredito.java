package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CapacidadCredito extends TumiDomain {
	private CapacidadCreditoId id;
	private Integer		intPersPersonaPk;
	private Integer		intItemSocioEstructura;
	private Integer		intParaTipoCapacidadCod;
	private Integer		intCartaAutorizacion;
	private BigDecimal	bdTotalIngresos;
	private BigDecimal	bdCuotaDescontar;
	private BigDecimal	bdIndiceDescuento;
	private BigDecimal	bdBaseCalculo;
	private BigDecimal	bdTotalDescuento;
	private BigDecimal	bdBaseTotal;
	private BigDecimal	bdDescuentoCapacidad;
	private Integer		intNroEntidades;
	private BigDecimal	bdCapacidadPago;
	private BigDecimal	bdCuotaFija;
	private Integer		intParaEstadoCod;
	private Timestamp 	tsFechaRegistro;
	private Integer		intPersPersonaUsuarioPk;
	private Timestamp 	tsFechaEliminacion;
	private Integer		intPersPersonaEliminarPk;
	
	private List<CapacidadDescuento> listaCapacidadDscto;
	
	public CapacidadCreditoId getId() {
		return id;
	}
	public void setId(CapacidadCreditoId id) {
		this.id = id;
	}
	public Integer getIntPersPersonaPk() {
		return intPersPersonaPk;
	}
	public void setIntPersPersonaPk(Integer intPersPersonaPk) {
		this.intPersPersonaPk = intPersPersonaPk;
	}
	public Integer getIntItemSocioEstructura() {
		return intItemSocioEstructura;
	}
	public void setIntItemSocioEstructura(Integer intItemSocioEstructura) {
		this.intItemSocioEstructura = intItemSocioEstructura;
	}
	public Integer getIntParaTipoCapacidadCod() {
		return intParaTipoCapacidadCod;
	}
	public void setIntParaTipoCapacidadCod(Integer intParaTipoCapacidadCod) {
		this.intParaTipoCapacidadCod = intParaTipoCapacidadCod;
	}
	public Integer getIntCartaAutorizacion() {
		return intCartaAutorizacion;
	}
	public void setIntCartaAutorizacion(Integer intCartaAutorizacion) {
		this.intCartaAutorizacion = intCartaAutorizacion;
	}
	public BigDecimal getBdTotalIngresos() {
		return bdTotalIngresos;
	}
	public void setBdTotalIngresos(BigDecimal bdTotalIngresos) {
		this.bdTotalIngresos = bdTotalIngresos;
	}
	public BigDecimal getBdCuotaDescontar() {
		return bdCuotaDescontar;
	}
	public void setBdCuotaDescontar(BigDecimal bdCuotaDescontar) {
		this.bdCuotaDescontar = bdCuotaDescontar;
	}
	public BigDecimal getBdIndiceDescuento() {
		return bdIndiceDescuento;
	}
	public void setBdIndiceDescuento(BigDecimal bdIndiceDescuento) {
		this.bdIndiceDescuento = bdIndiceDescuento;
	}
	public BigDecimal getBdBaseCalculo() {
		return bdBaseCalculo;
	}
	public void setBdBaseCalculo(BigDecimal bdBaseCalculo) {
		this.bdBaseCalculo = bdBaseCalculo;
	}
	public BigDecimal getBdTotalDescuento() {
		return bdTotalDescuento;
	}
	public void setBdTotalDescuento(BigDecimal bdTotalDescuento) {
		this.bdTotalDescuento = bdTotalDescuento;
	}
	public BigDecimal getBdBaseTotal() {
		return bdBaseTotal;
	}
	public void setBdBaseTotal(BigDecimal bdBaseTotal) {
		this.bdBaseTotal = bdBaseTotal;
	}
	public BigDecimal getBdDescuentoCapacidad() {
		return bdDescuentoCapacidad;
	}
	public void setBdDescuentoCapacidad(BigDecimal bdDescuentoCapacidad) {
		this.bdDescuentoCapacidad = bdDescuentoCapacidad;
	}
	public Integer getIntNroEntidades() {
		return intNroEntidades;
	}
	public void setIntNroEntidades(Integer intNroEntidades) {
		this.intNroEntidades = intNroEntidades;
	}
	public BigDecimal getBdCapacidadPago() {
		return bdCapacidadPago;
	}
	public void setBdCapacidadPago(BigDecimal bdCapacidadPago) {
		this.bdCapacidadPago = bdCapacidadPago;
	}
	public BigDecimal getBdCuotaFija() {
		return bdCuotaFija;
	}
	public void setBdCuotaFija(BigDecimal bdCuotaFija) {
		this.bdCuotaFija = bdCuotaFija;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public Timestamp getTsFechaEliminacion() {
		return tsFechaEliminacion;
	}
	public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
		this.tsFechaEliminacion = tsFechaEliminacion;
	}
	public Integer getIntPersPersonaEliminarPk() {
		return intPersPersonaEliminarPk;
	}
	public void setIntPersPersonaEliminarPk(Integer intPersPersonaEliminarPk) {
		this.intPersPersonaEliminarPk = intPersPersonaEliminarPk;
	}
	public List<CapacidadDescuento> getListaCapacidadDscto() {
		return listaCapacidadDscto;
	}
	public void setListaCapacidadDscto(List<CapacidadDescuento> listaCapacidadDscto) {
		this.listaCapacidadDscto = listaCapacidadDscto;
	}
}