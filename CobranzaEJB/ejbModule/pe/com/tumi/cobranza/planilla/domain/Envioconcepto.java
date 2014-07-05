package pe.com.tumi.cobranza.planilla.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Envioconcepto extends TumiDomain{
		private EnvioconceptoId id;
     	private java.lang.Integer intPeriodoplanilla;
     	private java.lang.Integer intCuentaPk;
     	private java.lang.Integer intItemcuentaconcepto;
     	private java.lang.Integer intItemexpediente;
     	private java.lang.Integer intItemdetexpediente;
     	private java.lang.Integer intTipoconceptogeneralCod;
     	private java.lang.Integer intIndidestacado;
     	private java.lang.Integer intIndilicencia;
     	private java.lang.Integer intIndidescjudi;
     	private java.lang.Integer intIndicienpor;
     	private java.math.BigDecimal bdMontoconcepto;
     	private java.lang.Integer intParaEstadoCod;
     	private List<Enviomonto> listaEnviomonto;
		
     	private java.lang.Integer intTempAnioPlanilla;
     	private java.lang.Integer intTempMesPlanilla;
     	private java.lang.Integer intTempEfectuado;
     	
     	//AUTOR Y FECHA CREACION: JCHAVEZ / 18-09-2013
     	private java.math.BigDecimal bdDiferenciaMontoconcepto;
     	
     	private Integer intOrdenPrioridad;
     	private Boolean blnFiltrado;
     	private Integer intParaTipoCreditoCod;
     	
     	
     	public java.lang.Integer getIntTempEfectuado() {
			return intTempEfectuado;
		}
     	
     	public void setIntTempEfectuado(java.lang.Integer intTempEfectuado) {
			this.intTempEfectuado = intTempEfectuado;
		}
     	
		public EnvioconceptoId getId(){
			return this.id;
		}
		public void setId(EnvioconceptoId id){
			this.id = id;
		}
		
		public java.lang.Integer getIntPeriodoplanilla(){
			return this.intPeriodoplanilla;
		}
		public void setIntPeriodoplanilla(java.lang.Integer intPeriodoplanilla){
			this.intPeriodoplanilla = intPeriodoplanilla;
		}
		
		public java.lang.Integer getIntCuentaPk(){
			return this.intCuentaPk;
		}
		public void setIntCuentaPk(java.lang.Integer intCuentaPk){
			this.intCuentaPk = intCuentaPk;
		}
		
		public java.lang.Integer getIntItemcuentaconcepto(){
			return this.intItemcuentaconcepto;
		}
		public void setIntItemcuentaconcepto(java.lang.Integer intItemcuentaconcepto){
			this.intItemcuentaconcepto = intItemcuentaconcepto;
		}
		
		public java.lang.Integer getIntItemexpediente(){
			return this.intItemexpediente;
		}
		public void setIntItemexpediente(java.lang.Integer intItemexpediente){
			this.intItemexpediente = intItemexpediente;
		}
		
		public java.lang.Integer getIntItemdetexpediente(){
			return this.intItemdetexpediente;
		}
		public void setIntItemdetexpediente(java.lang.Integer intItemdetexpediente){
			this.intItemdetexpediente = intItemdetexpediente;
		}
		
		public java.lang.Integer getIntTipoconceptogeneralCod(){
			return this.intTipoconceptogeneralCod;
		}
		public void setIntTipoconceptogeneralCod(java.lang.Integer intTipoconceptogeneralCod){
			this.intTipoconceptogeneralCod = intTipoconceptogeneralCod;
		}
		
		public java.lang.Integer getIntIndidestacado(){
			return this.intIndidestacado;
		}
		public void setIntIndidestacado(java.lang.Integer intIndidestacado){
			this.intIndidestacado = intIndidestacado;
		}
		
		public java.lang.Integer getIntIndilicencia(){
			return this.intIndilicencia;
		}
		public void setIntIndilicencia(java.lang.Integer intIndilicencia){
			this.intIndilicencia = intIndilicencia;
		}
		
		public java.lang.Integer getIntIndidescjudi(){
			return this.intIndidescjudi;
		}
		public void setIntIndidescjudi(java.lang.Integer intIndidescjudi){
			this.intIndidescjudi = intIndidescjudi;
		}
		
		public java.lang.Integer getIntIndicienpor(){
			return this.intIndicienpor;
		}
		public void setIntIndicienpor(java.lang.Integer intIndicienpor){
			this.intIndicienpor = intIndicienpor;
		}
		
		public java.math.BigDecimal getBdMontoconcepto(){
			return this.bdMontoconcepto;
		}
		public void setBdMontoconcepto(java.math.BigDecimal bdMontoconcepto){
			this.bdMontoconcepto = bdMontoconcepto;
		}
		
		public java.lang.Integer getIntParaEstadoCod() {
			return intParaEstadoCod;
		}
		public void setIntParaEstadoCod(java.lang.Integer intParaEstadoCod) {
			this.intParaEstadoCod = intParaEstadoCod;
		}
		
		public List<Enviomonto> getListaEnviomonto(){
			return this.listaEnviomonto;
		}
		public void setListaEnviomonto(List<Enviomonto> listaEnviomonto){
			this.listaEnviomonto = listaEnviomonto;
		}
		
		public java.lang.Integer getIntTempAnioPlanilla() {
			return intTempAnioPlanilla;
		}
		public void setIntTempAnioPlanilla(java.lang.Integer intTempAnioPlanilla) {
			this.intTempAnioPlanilla = intTempAnioPlanilla;
		}
		
		public java.lang.Integer getIntTempMesPlanilla() {
			return intTempMesPlanilla;
		}
		public void setIntTempMesPlanilla(java.lang.Integer intTempMesPlanilla) {
			this.intTempMesPlanilla = intTempMesPlanilla;
		}

		public java.math.BigDecimal getBdDiferenciaMontoconcepto() {
			return bdDiferenciaMontoconcepto;
		}

		public void setBdDiferenciaMontoconcepto(
				java.math.BigDecimal bdDiferenciaMontoconcepto) {
			this.bdDiferenciaMontoconcepto = bdDiferenciaMontoconcepto;
		}

		public Integer getIntOrdenPrioridad() {
			return intOrdenPrioridad;
		}

		public void setIntOrdenPrioridad(Integer intOrdenPrioridad) {
			this.intOrdenPrioridad = intOrdenPrioridad;
		}
				
		public Boolean getBlnFiltrado() {
			return blnFiltrado;
		}

		public void setBlnFiltrado(Boolean blnFiltrado) {
			this.blnFiltrado = blnFiltrado;
		}
		
		
		
		public Integer getIntParaTipoCreditoCod() {
			return intParaTipoCreditoCod;
		}

		public void setIntParaTipoCreditoCod(Integer intParaTipoCreditoCod) {
			this.intParaTipoCreditoCod = intParaTipoCreditoCod;
		}

		@Override
		public String toString() {
			return "Envioconcepto [intItemcuentaconcepto="
					+ intItemcuentaconcepto + ", intItemexpediente="
					+ intItemexpediente + ", intItemdetexpediente="
					+ intItemdetexpediente + ", bdMontoconcepto="
					+ bdMontoconcepto + "]";
		}

		
		

	
				
}




	