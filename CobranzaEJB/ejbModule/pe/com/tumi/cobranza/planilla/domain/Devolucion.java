package pe.com.tumi.cobranza.planilla.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Devolucion extends TumiDomain{
	private DevolucionId id;
	private Timestamp tsDevoFecha;
	private Integer intParaDocumentoGeneral;
	private Integer intCuenta;
	private BigDecimal bdDevoMonto;
	private Integer intParaEstado;
	private Integer intParaEstadoPago;
	private Integer intItemEgresoGeneral;
	private Integer intPersEmpresaEgreso;
	private Integer intPersEmpresaLibro;
	private Integer intContPeriodo;
	private Integer intContCodigoLibro;
	private Integer intPersEmpresaSolCtaCte;
	private Integer intCcobItemSolCtaCte;
	private Integer intParaTipoSolicitudCtaCte;
	
	public DevolucionId getId() {
		return id;
	}
	public void setId(DevolucionId id) {
		this.id = id;
	}
	public Timestamp getTsDevoFecha() {
		return tsDevoFecha;
	}
	public void setTsDevoFecha(Timestamp tsDevoFecha) {
		this.tsDevoFecha = tsDevoFecha;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Integer getIntCuenta() {
		return intCuenta;
	}
	public void setIntCuenta(Integer intCuenta) {
		this.intCuenta = intCuenta;
	}
	public BigDecimal getBdDevoMonto() {
		return bdDevoMonto;
	}
	public void setBdDevoMonto(BigDecimal bdDevoMonto) {
		this.bdDevoMonto = bdDevoMonto;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntParaEstadoPago() {
		return intParaEstadoPago;
	}
	public void setIntParaEstadoPago(Integer intParaEstadoPago) {
		this.intParaEstadoPago = intParaEstadoPago;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntPersEmpresaLibro() {
		return intPersEmpresaLibro;
	}
	public void setIntPersEmpresaLibro(Integer intPersEmpresaLibro) {
		this.intPersEmpresaLibro = intPersEmpresaLibro;
	}
	public Integer getIntContPeriodo() {
		return intContPeriodo;
	}
	public void setIntContPeriodo(Integer intContPeriodo) {
		this.intContPeriodo = intContPeriodo;
	}
	public Integer getIntContCodigoLibro() {
		return intContCodigoLibro;
	}
	public void setIntContCodigoLibro(Integer intContCodigoLibro) {
		this.intContCodigoLibro = intContCodigoLibro;
	}
	public Integer getIntPersEmpresaSolCtaCte() {
		return intPersEmpresaSolCtaCte;
	}
	public void setIntPersEmpresaSolCtaCte(Integer intPersEmpresaSolCtaCte) {
		this.intPersEmpresaSolCtaCte = intPersEmpresaSolCtaCte;
	}
	public Integer getIntCcobItemSolCtaCte() {
		return intCcobItemSolCtaCte;
	}
	public void setIntCcobItemSolCtaCte(Integer intCcobItemSolCtaCte) {
		this.intCcobItemSolCtaCte = intCcobItemSolCtaCte;
	}
	public Integer getIntParaTipoSolicitudCtaCte() {
		return intParaTipoSolicitudCtaCte;
	}
	public void setIntParaTipoSolicitudCtaCte(Integer intParaTipoSolicitudCtaCte) {
		this.intParaTipoSolicitudCtaCte = intParaTipoSolicitudCtaCte;
	}

}
