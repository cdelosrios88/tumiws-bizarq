package pe.com.tumi.contabilidad.core.domain;

import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class PlanCuenta extends TumiDomain{

		private PlanCuentaId id;
     	private Integer intEmpresaOrigenPk;
     	private Integer intPeriodoOrigen;
     	private String strNumeroCuentaOrigen;
     	private Integer intEmpresaDestinoPk;
     	private Integer intPeriodoDestino;
     	private String strNumeroCuentaDestino;
     	private String strDescripcion;
     	private String strComentario;
     	private Integer intMovimiento;
     	private Integer intIdentificadorExtranjero;
     	private Timestamp tsFechaRegistro;
     	private Integer intEmpresaUsuarioPk;
     	private Integer intPersonaUsuarioPk;
     	private Integer intEstadoCod;
     	private Timestamp tsFechaEliminacion;
     	private Integer intEmpresaEliminarPk;
     	private Integer intPersonaEliminarPk;
     	private PlanCuenta plancuenta;
     	private List<PlanCuenta> listaPlanCuenta;
     	
     	private Boolean blnTieneCtaDestino;
     	private Boolean blnTieneMovimiento;
     	private Boolean blnTieneIdExtranjero;
		private Integer intTipoBusqueda;
     	private List<AccesoPlanCuenta> 	listaAccesoPlanCuenta;
     	private AccesoPlanCuenta		accesoPlanCuentaUsar;
     	
     	//Agregado por cdelosrios, 15/09/2013
     	private List<AnexoDetalleCuenta> listaAnexoDetalleCuenta;
     	//Fin agregado por cdelosrios, 15/09/2013
		
		public PlanCuentaId getId(){
			return this.id;
		}
		public void setId(PlanCuentaId id){
			this.id = id;
		}
		public java.lang.String getStrDescripcion(){
			return this.strDescripcion;
		}
		public void setStrDescripcion(java.lang.String strDescripcion){
			this.strDescripcion = strDescripcion;
		}
		public java.lang.String getStrComentario(){
			return this.strComentario;
		}
		public void setStrComentario(java.lang.String strComentario){
			this.strComentario = strComentario;
		}
		public java.lang.Integer getIntMovimiento(){
			return this.intMovimiento;
		}
		public void setIntMovimiento(java.lang.Integer intMovimiento){
			this.intMovimiento = intMovimiento;
		}
		public java.lang.Integer getIntEstadoCod(){
			return this.intEstadoCod;
		}
		public void setIntEstadoCod(java.lang.Integer intEstadoCod){
			this.intEstadoCod = intEstadoCod;
		}
     	public PlanCuenta getPlancuenta(){
			return this.plancuenta;
		}
		public void setPlancuenta(PlanCuenta plancuenta){
			this.plancuenta = plancuenta;
		}
		public java.lang.Integer getIntEmpresaOrigenPk() {
			return intEmpresaOrigenPk;
		}
		public void setIntEmpresaOrigenPk(java.lang.Integer intEmpresaOrigenPk) {
			this.intEmpresaOrigenPk = intEmpresaOrigenPk;
		}
		public java.lang.Integer getIntPeriodoOrigen() {
			return intPeriodoOrigen;
		}
		public void setIntPeriodoOrigen(java.lang.Integer intPeriodoOrigen) {
			this.intPeriodoOrigen = intPeriodoOrigen;
		}
		public java.lang.String getStrNumeroCuentaOrigen() {
			return strNumeroCuentaOrigen;
		}
		public void setStrNumeroCuentaOrigen(java.lang.String strNumeroCuentaOrigen) {
			this.strNumeroCuentaOrigen = strNumeroCuentaOrigen;
		}
		public java.lang.Integer getIntEmpresaDestinoPk() {
			return intEmpresaDestinoPk;
		}
		public void setIntEmpresaDestinoPk(java.lang.Integer intEmpresaDestinoPk) {
			this.intEmpresaDestinoPk = intEmpresaDestinoPk;
		}
		public java.lang.Integer getIntPeriodoDestino() {
			return intPeriodoDestino;
		}
		public void setIntPeriodoDestino(java.lang.Integer intPeriodoDestino) {
			this.intPeriodoDestino = intPeriodoDestino;
		}
		public java.lang.String getStrNumeroCuentaDestino() {
			return strNumeroCuentaDestino;
		}
		public void setStrNumeroCuentaDestino(java.lang.String strNumeroCuentaDestino) {
			this.strNumeroCuentaDestino = strNumeroCuentaDestino;
		}
		public java.lang.Integer getIntIdentificadorExtranjero() {
			return intIdentificadorExtranjero;
		}
		public void setIntIdentificadorExtranjero(
				java.lang.Integer intIdentificadorExtranjero) {
			this.intIdentificadorExtranjero = intIdentificadorExtranjero;
		}
		public java.sql.Timestamp getTsFechaRegistro() {
			return tsFechaRegistro;
		}
		public void setTsFechaRegistro(java.sql.Timestamp tsFechaRegistro) {
			this.tsFechaRegistro = tsFechaRegistro;
		}
		public java.lang.Integer getIntEmpresaUsuarioPk() {
			return intEmpresaUsuarioPk;
		}
		public void setIntEmpresaUsuarioPk(java.lang.Integer intEmpresaUsuarioPk) {
			this.intEmpresaUsuarioPk = intEmpresaUsuarioPk;
		}
		public java.lang.Integer getIntPersonaUsuarioPk() {
			return intPersonaUsuarioPk;
		}
		public void setIntPersonaUsuarioPk(java.lang.Integer intPersonaUsuarioPk) {
			this.intPersonaUsuarioPk = intPersonaUsuarioPk;
		}
		public java.sql.Timestamp getTsFechaEliminacion() {
			return tsFechaEliminacion;
		}
		public void setTsFechaEliminacion(java.sql.Timestamp tsFechaEliminacion) {
			this.tsFechaEliminacion = tsFechaEliminacion;
		}
		public java.lang.Integer getIntEmpresaEliminarPk() {
			return intEmpresaEliminarPk;
		}
		public void setIntEmpresaEliminarPk(java.lang.Integer intEmpresaEliminarPk) {
			this.intEmpresaEliminarPk = intEmpresaEliminarPk;
		}
		public java.lang.Integer getIntPersonaEliminarPk() {
			return intPersonaEliminarPk;
		}
		public void setIntPersonaEliminarPk(java.lang.Integer intPersonaEliminarPk) {
			this.intPersonaEliminarPk = intPersonaEliminarPk;
		}
		public List<PlanCuenta> getListaPlanCuenta() {
			return listaPlanCuenta;
		}
		public void setListaPlanCuenta(List<PlanCuenta> listaPlanCuenta) {
			this.listaPlanCuenta = listaPlanCuenta;
		}
		public Boolean getBlnTieneCtaDestino() {
			return blnTieneCtaDestino;
		}
		public void setBlnTieneCtaDestino(Boolean blnTieneCtaDestino) {
			this.blnTieneCtaDestino = blnTieneCtaDestino;
		}
		public Boolean getBlnTieneMovimiento() {
			return blnTieneMovimiento;
		}
		public void setBlnTieneMovimiento(Boolean blnTieneMovimiento) {
			this.blnTieneMovimiento = blnTieneMovimiento;
		}
		public Boolean getBlnTieneIdExtranjero() {
			return blnTieneIdExtranjero;
		}
		public void setBlnTieneIdExtranjero(Boolean blnTieneIdExtranjero) {
			this.blnTieneIdExtranjero = blnTieneIdExtranjero;
		}
		public Integer getIntTipoBusqueda() {
			return intTipoBusqueda;
		}
		public void setIntTipoBusqueda(Integer intTipoBusqueda) {
			this.intTipoBusqueda = intTipoBusqueda;
		}		
		public List<AccesoPlanCuenta> getListaAccesoPlanCuenta() {
			return listaAccesoPlanCuenta;
		}
		public void setListaAccesoPlanCuenta(List<AccesoPlanCuenta> listaAccesoPlanCuenta) {
			this.listaAccesoPlanCuenta = listaAccesoPlanCuenta;
		}
		public AccesoPlanCuenta getAccesoPlanCuentaUsar() {
			return accesoPlanCuentaUsar;
		}
		public void setAccesoPlanCuentaUsar(AccesoPlanCuenta accesoPlanCuentaUsar) {
			this.accesoPlanCuentaUsar = accesoPlanCuentaUsar;
		}
		
		@Override
		public String toString() {
			return "PlanCuenta [id=" + id + ", intEmpresaOrigenPk="
					+ intEmpresaOrigenPk + ", intPeriodoOrigen="
					+ intPeriodoOrigen + ", strNumeroCuentaOrigen="
					+ strNumeroCuentaOrigen + ", intEmpresaDestinoPk="
					+ intEmpresaDestinoPk + ", intPeriodoDestino="
					+ intPeriodoDestino + ", strNumeroCuentaDestino="
					+ strNumeroCuentaDestino + ", strDescripcion="
					+ strDescripcion + ", strComentario=" + strComentario
					+ ", intMovimiento=" + intMovimiento
					+ ", intIdentificadorExtranjero="
					+ intIdentificadorExtranjero + ", tsFechaRegistro="
					+ tsFechaRegistro + ", intEmpresaUsuarioPk="
					+ intEmpresaUsuarioPk + ", intPersonaUsuarioPk="
					+ intPersonaUsuarioPk + ", intEstadoCod=" + intEstadoCod
					+ ", tsFechaEliminacion=" + tsFechaEliminacion
					+ ", intEmpresaEliminarPk=" + intEmpresaEliminarPk
					+ ", intPersonaEliminarPk=" + intPersonaEliminarPk
					+ ", plancuenta=" + plancuenta + ", listaPlanCuenta="
					+ listaPlanCuenta + ", blnTieneCtaDestino="
					+ blnTieneCtaDestino + ", blnTieneMovimiento="
					+ blnTieneMovimiento + ", blnTieneIdExtranjero="
					+ blnTieneIdExtranjero + "]";
		}
		
		//Agregado por cdelosrios, 15/09/2013
		public List<AnexoDetalleCuenta> getListaAnexoDetalleCuenta() {
			return listaAnexoDetalleCuenta;
		}
		public void setListaAnexoDetalleCuenta(
				List<AnexoDetalleCuenta> listaAnexoDetalleCuenta) {
			this.listaAnexoDetalleCuenta = listaAnexoDetalleCuenta;
		}
		//Fin agregado por cdelosrios, 15/09/2013
}