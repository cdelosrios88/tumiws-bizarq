package pe.com.tumi.contabilidad.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ModeloDetalleNivel extends TumiDomain{

		private ModeloDetalleNivelId id;
		private String strDescripcion;
		private Integer intParaTipoRegistro;
		private Integer intDatoTablas;
		private Integer intDatoArgumento;
		private Integer intValor;
		private ModeloDetalle modeloDetalle;
		
		//otras propiedades
		private Boolean isDeleted;
		
		//JCHAVEZ 02.01.2014
		private String strDescCuenta;
		//JCHAVEZ 22.01.2014
		private Integer intTipoModeloContable;
		
		public ModeloDetalleNivelId getId() {
			return id;
		}
		public void setId(ModeloDetalleNivelId id) {
			this.id = id;
		}
		public String getStrDescripcion() {
			return strDescripcion;
		}
		public void setStrDescripcion(String strDescripcion) {
			this.strDescripcion = strDescripcion;
		}
		public Integer getIntParaTipoRegistro() {
			return intParaTipoRegistro;
		}
		public void setIntParaTipoRegistro(Integer intParaTipoRegistro) {
			this.intParaTipoRegistro = intParaTipoRegistro;
		}
		public Integer getIntDatoTablas() {
			return intDatoTablas;
		}
		public void setIntDatoTablas(Integer intDatoTablas) {
			this.intDatoTablas = intDatoTablas;
		}
		public Integer getIntDatoArgumento() {
			return intDatoArgumento;
		}
		public void setIntDatoArgumento(Integer intDatoArgumento) {
			this.intDatoArgumento = intDatoArgumento;
		}
		public Integer getIntValor() {
			return intValor;
		}
		public void setIntValor(Integer intValor) {
			this.intValor = intValor;
		}
		public ModeloDetalle getModeloDetalle() {
			return modeloDetalle;
		}
		public void setModeloDetalle(ModeloDetalle modeloDetalle) {
			this.modeloDetalle = modeloDetalle;
		}
		public Boolean getIsDeleted() {
			return isDeleted;
		}
		public void setIsDeleted(Boolean isDeleted) {
			this.isDeleted = isDeleted;
		}
		@Override
		public String toString() {
			return "ModeloDetalleNivel [id=" + id + ", strDescripcion="
					+ strDescripcion + ", intParaTipoRegistro="
					+ intParaTipoRegistro + ", intDatoTablas=" + intDatoTablas
					+ ", intDatoArgumento=" + intDatoArgumento + ", intValor="
					+ intValor + "]";
		}
		public String getStrDescCuenta() {
			return strDescCuenta;
		}
		public void setStrDescCuenta(String strDescCuenta) {
			this.strDescCuenta = strDescCuenta;
		}
		public Integer getIntTipoModeloContable() {
			return intTipoModeloContable;
		}
		public void setIntTipoModeloContable(Integer intTipoModeloContable) {
			this.intTipoModeloContable = intTipoModeloContable;
		}		
}
