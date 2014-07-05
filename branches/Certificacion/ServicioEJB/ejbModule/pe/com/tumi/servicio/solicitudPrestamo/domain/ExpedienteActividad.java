package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.math.BigDecimal;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ExpedienteActividad extends TumiDomain{
	 private ExpedienteActividadId id;
	 private Integer intParaTipoSolicitudCod;
	 private BigDecimal bdMontoCancelado;
	 
	 
	 
	public ExpedienteActividadId getId() {
		return id;
	}



	public void setId(ExpedienteActividadId id) {
		this.id = id;
	}



	public Integer getIntParaTipoSolicitudCod() {
		return intParaTipoSolicitudCod;
	}



	public void setIntParaTipoSolicitudCod(Integer intParaTipoSolicitudCod) {
		this.intParaTipoSolicitudCod = intParaTipoSolicitudCod;
	}



	public BigDecimal getBdMontoCancelado() {
		return bdMontoCancelado;
	}



	public void setBdMontoCancelado(BigDecimal bdMontoCancelado) {
		this.bdMontoCancelado = bdMontoCancelado;
	}



		public String toString() {
			return "ExpedienteActividad [id=" + id
					+ ", intParaTipoSolicitudCod=" + intParaTipoSolicitudCod + ", bdMontoCancelado="
					+ bdMontoCancelado + "]";
		}
	

}
