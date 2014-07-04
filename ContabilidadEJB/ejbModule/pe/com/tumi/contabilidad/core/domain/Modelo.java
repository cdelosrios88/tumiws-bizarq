package pe.com.tumi.contabilidad.core.domain;

import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Modelo extends TumiDomain{

		private ModeloId id;
		private Integer intTipoModeloContable;
		private String strDescripcion;
		private Timestamp tsFechaRegistro;
		private Integer intEmpresaUsuario;
		private Integer intPersonaUsuario;
		private Integer intEstado;
		private Timestamp tsFechaEliminacion;
		private List<ModeloDetalle> listModeloDetalle;
		
		private Integer intPeriodo;
		//para giros de prestamos
		private boolean esModeloDeCancelado;
		
		public Modelo(){
			id = new ModeloId();
		}
		
		public ModeloId getId() {
			return id;
		}
		public void setId(ModeloId id) {
			this.id = id;
		}
		public Integer getIntTipoModeloContable() {
			return intTipoModeloContable;
		}
		public void setIntTipoModeloContable(Integer intTipoModeloContable) {
			this.intTipoModeloContable = intTipoModeloContable;
		}
		public String getStrDescripcion() {
			return strDescripcion;
		}
		public void setStrDescripcion(String strDescripcion) {
			this.strDescripcion = strDescripcion;
		}
		public Timestamp getTsFechaRegistro() {
			return tsFechaRegistro;
		}
		public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
			this.tsFechaRegistro = tsFechaRegistro;
		}
		public Integer getIntEmpresaUsuario() {
			return intEmpresaUsuario;
		}
		public void setIntEmpresaUsuario(Integer intEmpresaUsuario) {
			this.intEmpresaUsuario = intEmpresaUsuario;
		}
		public Integer getIntPersonaUsuario() {
			return intPersonaUsuario;
		}
		public void setIntPersonaUsuario(Integer intPersonaUsuario) {
			this.intPersonaUsuario = intPersonaUsuario;
		}
		public Integer getIntEstado() {
			return intEstado;
		}
		public void setIntEstado(Integer intEstado) {
			this.intEstado = intEstado;
		}
		public Timestamp getTsFechaEliminacion() {
			return tsFechaEliminacion;
		}
		public void setTsFechaEliminacion(Timestamp tsFechaEliminacion) {
			this.tsFechaEliminacion = tsFechaEliminacion;
		}
		public List<ModeloDetalle> getListModeloDetalle() {
			return listModeloDetalle;
		}
		public void setListModeloDetalle(List<ModeloDetalle> listModeloDetalle) {
			this.listModeloDetalle = listModeloDetalle;
		}
		public Integer getIntPeriodo() {
			return intPeriodo;
		}
		public void setIntPeriodo(Integer intPeriodo) {
			this.intPeriodo = intPeriodo;
		}
		public boolean isEsModeloDeCancelado() {
			return esModeloDeCancelado;
		}
		public void setEsModeloDeCancelado(boolean esModeloDeCancelado) {
			this.esModeloDeCancelado = esModeloDeCancelado;
		}

		@Override
		public String toString() {
			return "Modelo [id=" + id + ", intTipoModeloContable="
					+ intTipoModeloContable + ", strDescripcion="
					+ strDescripcion + ", tsFechaRegistro=" + tsFechaRegistro
					+ ", intEmpresaUsuario=" + intEmpresaUsuario
					+ ", intPersonaUsuario=" + intPersonaUsuario
					+ ", intEstado=" + intEstado + ", tsFechaEliminacion="
					+ tsFechaEliminacion + ", intPeriodo=" + intPeriodo + "]";
		}
		
		
}
