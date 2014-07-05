package pe.com.tumi.cobranza.planilla.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import pe.com.tumi.common.domain.EntidadBase;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;

public class Transferencia extends TumiDomain{
	
    private TransferenciaId id;
	private Timestamp tsTranFecha;
	private Integer intParaDocumentoGeneral;
	private Integer intPersEmpresaSolctacte;
	private Integer intCcobItemSolctacte;
	private Integer intParaTipoSolicitudctacte;
	private Integer intPersEmpresaLibro;
	private Integer intContPeriodoLibro;
	private Integer intContCodigoLibro;
	private Integer intTranPeriodo;
	
	public TransferenciaId getId() {
		return id;
	}
	public void setId(TransferenciaId id) {
		this.id = id;
	}
	public Timestamp getTsTranFecha() {
		return tsTranFecha;
	}
	public void setTsTranFecha(Timestamp tsTranFecha) {
		this.tsTranFecha = tsTranFecha;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Integer getIntPersEmpresaSolctacte() {
		return intPersEmpresaSolctacte;
	}
	public void setIntPersEmpresaSolctacte(Integer intPersEmpresaSolctacte) {
		this.intPersEmpresaSolctacte = intPersEmpresaSolctacte;
	}
	public Integer getIntCcobItemSolctacte() {
		return intCcobItemSolctacte;
	}
	public void setIntCcobItemSolctacte(Integer intCcobItemSolctacte) {
		this.intCcobItemSolctacte = intCcobItemSolctacte;
	}
	public Integer getIntParaTipoSolicitudctacte() {
		return intParaTipoSolicitudctacte;
	}
	public void setIntParaTipoSolicitudctacte(Integer intParaTipoSolicitudctacte) {
		this.intParaTipoSolicitudctacte = intParaTipoSolicitudctacte;
	}
	public Integer getIntPersEmpresaLibro() {
		return intPersEmpresaLibro;
	}
	public void setIntPersEmpresaLibro(Integer intPersEmpresaLibro) {
		this.intPersEmpresaLibro = intPersEmpresaLibro;
	}
	public Integer getIntContPeriodoLibro() {
		return intContPeriodoLibro;
	}
	public void setIntContPeriodoLibro(Integer intContPeriodoLibro) {
		this.intContPeriodoLibro = intContPeriodoLibro;
	}
	public Integer getIntContCodigoLibro() {
		return intContCodigoLibro;
	}
	public void setIntContCodigoLibro(Integer intContCodigoLibro) {
		this.intContCodigoLibro = intContCodigoLibro;
	}
	public Integer getIntTranPeriodo() {
		return intTranPeriodo;
	}
	public void setIntTranPeriodo(Integer intTranPeriodo) {
		this.intTranPeriodo = intTranPeriodo;
	}
	
	
}
