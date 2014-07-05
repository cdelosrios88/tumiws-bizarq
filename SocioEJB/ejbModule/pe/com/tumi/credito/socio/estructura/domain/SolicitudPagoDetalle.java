package pe.com.tumi.credito.socio.estructura.domain;

import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SolicitudPagoDetalle extends TumiDomain{

	private SolicitudPagoDetalleId id;
	private Integer intPeriodo;
	private Integer intMes;
	private Integer intNivel;
	private Integer intCodigo;
	private Integer intParaTipoArchivoPadronCod;
	private Integer intParaModalidadCod;
	private Integer intParaTipoSocioCod;
	private Integer intItemAdministraPadron;
	private SolicitudPago solicitudPago;
	
	public SolicitudPagoDetalle(){
		super();
		id = new SolicitudPagoDetalleId();
		solicitudPago = new SolicitudPago();
	}
	
	public SolicitudPagoDetalleId getId() {
		return id;
	}
	public void setId(SolicitudPagoDetalleId id) {
		this.id = id;
	}

	public Integer getIntPeriodo() {
		return intPeriodo;
	}

	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}

	public Integer getIntMes() {
		return intMes;
	}

	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}

	public Integer getIntNivel() {
		return intNivel;
	}

	public void setIntNivel(Integer intNivel) {
		this.intNivel = intNivel;
	}

	public Integer getIntCodigo() {
		return intCodigo;
	}

	public void setIntCodigo(Integer intCodigo) {
		this.intCodigo = intCodigo;
	}

	public Integer getIntParaTipoArchivoPadronCod() {
		return intParaTipoArchivoPadronCod;
	}

	public void setIntParaTipoArchivoPadronCod(Integer intParaTipoArchivoCod) {
		this.intParaTipoArchivoPadronCod = intParaTipoArchivoCod;
	}

	public Integer getIntParaModalidadCod() {
		return intParaModalidadCod;
	}

	public void setIntParaModalidadCod(Integer intParaModalidadCod) {
		this.intParaModalidadCod = intParaModalidadCod;
	}

	public Integer getIntParaTipoSocioCod() {
		return intParaTipoSocioCod;
	}

	public void setIntParaTipoSocioCod(Integer intParaTipoSocioCod) {
		this.intParaTipoSocioCod = intParaTipoSocioCod;
	}

	public Integer getIntItemAdministraPadron() {
		return intItemAdministraPadron;
	}

	public void setIntItemAdministraPadron(Integer intItemAdministraPadron) {
		this.intItemAdministraPadron = intItemAdministraPadron;
	}

	public SolicitudPago getSolicitudPago() {
		return solicitudPago;
	}

	public void setSolicitudPago(SolicitudPago solicitudPago) {
		this.solicitudPago = solicitudPago;
	}

	@Override
	public String toString() {
		return "SolicitudPagoDetalle [id=" + id + ", intPeriodo=" + intPeriodo
				+ ", intMes=" + intMes + ", intNivel=" + intNivel
				+ ", intCodigo=" + intCodigo + ", intParaTipoArchivoPadronCod="
				+ intParaTipoArchivoPadronCod + ", intParaModalidadCod="
				+ intParaModalidadCod + ", intParaTipoSocioCod="
				+ intParaTipoSocioCod + ", intItemAdministraPadron="
				+ intItemAdministraPadron + ", solicitudPago=" + solicitudPago
				+ "]";
	}

	
	
}
