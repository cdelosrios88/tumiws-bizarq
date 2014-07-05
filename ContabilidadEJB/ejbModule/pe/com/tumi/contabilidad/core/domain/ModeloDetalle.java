package pe.com.tumi.contabilidad.core.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ModeloDetalle extends TumiDomain{

		private ModeloDetalleId id;
		private Integer intParaOpcionDebeHaber;
		private List<ModeloDetalleNivel> listModeloDetalleNivel;
		private Modelo modelo;
		private PlanCuenta planCuenta;
		private Integer intParaDocumentoGral;
		
		//otras propiedades
		private Boolean isDeleted;
		
		public ModeloDetalleId getId() {
			return id;
		}
		public void setId(ModeloDetalleId id) {
			this.id = id;
		}
		public Integer getIntParaOpcionDebeHaber() {
			return intParaOpcionDebeHaber;
		}
		public void setIntParaOpcionDebeHaber(Integer intParaOpcionDebeHaber) {
			this.intParaOpcionDebeHaber = intParaOpcionDebeHaber;
		}
		public List<ModeloDetalleNivel> getListModeloDetalleNivel() {
			return listModeloDetalleNivel;
		}
		public void setListModeloDetalleNivel(List<ModeloDetalleNivel> listModeloDetalleNivel) {
			this.listModeloDetalleNivel = listModeloDetalleNivel;
		}
		public Modelo getModelo() {
			return modelo;
		}
		public void setModelo(Modelo modelo) {
			this.modelo = modelo;
		}
		public PlanCuenta getPlanCuenta() {
			return planCuenta;
		}
		public void setPlanCuenta(PlanCuenta planCuenta) {
			this.planCuenta = planCuenta;
		}
		public Boolean getIsDeleted() {
			return isDeleted;
		}
		public void setIsDeleted(Boolean isDeleted) {
			this.isDeleted = isDeleted;
		}
		public Integer getIntParaDocumentoGral() {
			return intParaDocumentoGral;
		}
		public void setIntParaDocumentoGral(Integer intParaDocumentoGral) {
			this.intParaDocumentoGral = intParaDocumentoGral;
		}
		@Override
		public String toString() {
			return "ModeloDetalle [id=" + id + ", intParaOpcionDebeHaber="
					+ intParaOpcionDebeHaber + "]";
		}
		
}
