package pe.com.tumi.cobranza.planilla.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class Envioresumen extends TumiDomain{
		private EnvioresumenId id;
     	private java.lang.Integer intDocumentogeneralCod;
     	private java.lang.Integer intPeriodoplanilla;
     	private java.math.BigDecimal bdMontototal;
     	private java.math.BigDecimal bdMontototalinflada;
     	private java.lang.Integer intNumeroafectados;
     	private java.lang.Integer intTiposocioCod;
     	private java.lang.Integer intModalidadCod;
     	private java.lang.Integer intNivel;
     	private java.lang.Integer intCodigo;
     	private java.lang.Integer intIdsucursalprocesaPk;
     	private java.lang.Integer intIdsubsucursalprocesaPk;
     	private java.lang.Integer intIdsucursaladministraPk;
     	private java.lang.Integer intIdsubsucursaladministra;
     	private java.lang.Integer intEstadoCod;
     	private java.sql.Timestamp tsFecharegistro;
     	private java.lang.Integer intPersonausuarioPk;
     	private java.sql.Timestamp tsFechaeliminacion;
     	private java.lang.Integer intPersonaeliminaPk;
     	private java.lang.Integer intTipoeliminacionplCod;
     	private java.lang.String strObservacion;
     	private java.lang.Integer intEmpresalibroPk;
     	private java.lang.Integer intPeriodolibro;
     	private java.lang.Integer intCodigolibro;
     	private Envioresumen envioResumen;
     	
     	private List<Enviomonto> listaEnviomonto;
     	
     	//Auxiliares
     	private Juridica juridicaUnidadEjecutora;
		private Juridica juridicaSucursal;
     	
		public EnvioresumenId getId(){
			return this.id;
		}
		public void setId(EnvioresumenId id){
			this.id = id;
		}
		
		public java.lang.Integer getIntDocumentogeneralCod(){
			return this.intDocumentogeneralCod;
		}
		public void setIntDocumentogeneralCod(java.lang.Integer intDocumentogeneralCod){
			this.intDocumentogeneralCod = intDocumentogeneralCod;
		}
		
		public java.lang.Integer getIntPeriodoplanilla(){
			return this.intPeriodoplanilla;
		}
		public void setIntPeriodoplanilla(java.lang.Integer intPeriodoplanilla){
			this.intPeriodoplanilla = intPeriodoplanilla;
		}
		
		public java.math.BigDecimal getBdMontototal(){
			return this.bdMontototal;
		}
		public void setBdMontototal(java.math.BigDecimal bdMontototal){
			this.bdMontototal = bdMontototal;
		}
		
		public java.math.BigDecimal getBdMontototalinflada(){
			return this.bdMontototalinflada;
		}
		public void setBdMontototalinflada(java.math.BigDecimal bdMontototalinflada){
			this.bdMontototalinflada = bdMontototalinflada;
		}
		
		public java.lang.Integer getIntNumeroafectados(){
			return this.intNumeroafectados;
		}
		public void setIntNumeroafectados(java.lang.Integer intNumeroafectados){
			this.intNumeroafectados = intNumeroafectados;
		}
		
		public java.lang.Integer getIntTiposocioCod(){
			return this.intTiposocioCod;
		}
		public void setIntTiposocioCod(java.lang.Integer intTiposocioCod){
			this.intTiposocioCod = intTiposocioCod;
		}
		
		public java.lang.Integer getIntModalidadCod(){
			return this.intModalidadCod;
		}
		public void setIntModalidadCod(java.lang.Integer intModalidadCod){
			this.intModalidadCod = intModalidadCod;
		}
		
		public java.lang.Integer getIntNivel(){
			return this.intNivel;
		}
		public void setIntNivel(java.lang.Integer intNivel){
			this.intNivel = intNivel;
		}
		
		public java.lang.Integer getIntCodigo(){
			return this.intCodigo;
		}
		public void setIntCodigo(java.lang.Integer intCodigo){
			this.intCodigo = intCodigo;
		}
		
		public java.lang.Integer getIntIdsucursalprocesaPk(){
			return this.intIdsucursalprocesaPk;
		}
		public void setIntIdsucursalprocesaPk(java.lang.Integer intIdsucursalprocesaPk){
			this.intIdsucursalprocesaPk = intIdsucursalprocesaPk;
		}
		
		public java.lang.Integer getIntIdsubsucursalprocesaPk(){
			return this.intIdsubsucursalprocesaPk;
		}
		public void setIntIdsubsucursalprocesaPk(java.lang.Integer intIdsubsucursalprocesaPk){
			this.intIdsubsucursalprocesaPk = intIdsubsucursalprocesaPk;
		}
		
		public java.lang.Integer getIntIdsucursaladministraPk(){
			return this.intIdsucursaladministraPk;
		}
		public void setIntIdsucursaladministraPk(java.lang.Integer intIdsucursaladministraPk){
			this.intIdsucursaladministraPk = intIdsucursaladministraPk;
		}
		
		public java.lang.Integer getIntIdsubsucursaladministra(){
			return this.intIdsubsucursaladministra;
		}
		public void setIntIdsubsucursaladministra(java.lang.Integer intIdsubsucursaladministra){
			this.intIdsubsucursaladministra = intIdsubsucursaladministra;
		}
		
		public java.lang.Integer getIntEstadoCod(){
			return this.intEstadoCod;
		}
		public void setIntEstadoCod(java.lang.Integer intEstadoCod){
			this.intEstadoCod = intEstadoCod;
		}
		
		public java.sql.Timestamp getTsFecharegistro(){
			return this.tsFecharegistro;
		}
		public void setTsFecharegistro(java.sql.Timestamp tsFecharegistro){
			this.tsFecharegistro = tsFecharegistro;
		}
		
		public java.lang.Integer getIntPersonausuarioPk(){
			return this.intPersonausuarioPk;
		}
		public void setIntPersonausuarioPk(java.lang.Integer intPersonausuarioPk){
			this.intPersonausuarioPk = intPersonausuarioPk;
		}
		
		public java.sql.Timestamp getTsFechaeliminacion(){
			return this.tsFechaeliminacion;
		}
		public void setTsFechaeliminacion(java.sql.Timestamp tsFechaeliminacion){
			this.tsFechaeliminacion = tsFechaeliminacion;
		}
		
		public java.lang.Integer getIntPersonaeliminaPk(){
			return this.intPersonaeliminaPk;
		}
		public void setIntPersonaeliminaPk(java.lang.Integer intPersonaeliminaPk){
			this.intPersonaeliminaPk = intPersonaeliminaPk;
		}
		
		public java.lang.Integer getIntTipoeliminacionplCod(){
			return this.intTipoeliminacionplCod;
		}
		public void setIntTipoeliminacionplCod(java.lang.Integer intTipoeliminacionplCod){
			this.intTipoeliminacionplCod = intTipoeliminacionplCod;
		}
		
		public java.lang.String getStrObservacion(){
			return this.strObservacion;
		}
		public void setStrObservacion(java.lang.String strObservacion){
			this.strObservacion = strObservacion;
		}
		
		public java.lang.Integer getIntEmpresalibroPk(){
			return this.intEmpresalibroPk;
		}
		public void setIntEmpresalibroPk(java.lang.Integer intEmpresalibroPk){
			this.intEmpresalibroPk = intEmpresalibroPk;
		}
		
		public java.lang.Integer getIntPeriodolibro(){
			return this.intPeriodolibro;
		}
		public void setIntPeriodolibro(java.lang.Integer intPeriodolibro){
			this.intPeriodolibro = intPeriodolibro;
		}
		
		public java.lang.Integer getIntCodigolibro(){
			return this.intCodigolibro;
		}
		public void setIntCodigolibro(java.lang.Integer intCodigolibro){
			this.intCodigolibro = intCodigolibro;
		}
		
		public List<Enviomonto> getListaEnviomonto(){
			return this.listaEnviomonto;
		}
		public void setListaEnviomonto(List<Enviomonto> listaEnviomonto){
			this.listaEnviomonto = listaEnviomonto;
		}
		
		public Envioresumen getEnvioResumen() {
			return envioResumen;
		}
		public void setEnvioResumen(Envioresumen envioResumen) {
			this.envioResumen = envioResumen;
		}
		
		public Juridica getJuridicaUnidadEjecutora() {
			return juridicaUnidadEjecutora;
		}
		
		public void setJuridicaUnidadEjecutora(Juridica juridicaUnidadEjecutora) {
			this.juridicaUnidadEjecutora = juridicaUnidadEjecutora;
		}
		
		public Juridica getJuridicaSucursal() {
			return juridicaSucursal;
		}
		
		public void setJuridicaSucursal(Juridica juridicaSucursal) {
			this.juridicaSucursal = juridicaSucursal;
		}
		
		@Override
		public String toString() {
			return "Envioresumen [id=" + id + ", intPeriodoplanilla="
					+ intPeriodoplanilla + ", bdMontototal=" + bdMontototal
					+ ", intNumeroafectados=" + intNumeroafectados
					+ ", intTiposocioCod=" + intTiposocioCod
					+ ", intModalidadCod=" + intModalidadCod + ", intNivel="
					+ intNivel + ", intCodigo=" + intCodigo
					+ ", intIdsucursaladministraPk="
					+ intIdsucursaladministraPk
					+ ", intIdsubsucursaladministra="
					+ intIdsubsucursaladministra + "]";
		}
}

