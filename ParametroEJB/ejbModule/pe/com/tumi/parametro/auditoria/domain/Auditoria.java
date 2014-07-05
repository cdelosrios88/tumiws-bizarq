package pe.com.tumi.parametro.auditoria.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Auditoria extends TumiDomain{

		private java.lang.Integer intIdcodigo;
     	private java.lang.String strTabla;
     	private java.lang.String strColumna;
     	private java.lang.Integer intEmpresaPk;
     	private java.lang.String strLlave1;
     	private java.lang.String strLlave2;
     	private java.lang.String strLlave3;
     	private java.lang.String strLlave4;
     	private java.lang.String strLlave5;
     	private java.lang.String strLlave6;
     	private java.lang.String strLlave7;
     	private java.lang.String strLlave8;
     	private java.lang.String strLlave9;
     	private java.lang.String strLlave10;
     	private java.lang.Integer intTipo;
     	private java.lang.String strValoranterior;
     	private java.lang.String strValornuevo;
     	private java.sql.Timestamp tsFecharegistro;
     	private java.lang.Integer intPersonaPk;
     	
     	// cgd - 02.07.2013
     	private List<AuditoriaMotivo> listaAuditoriaMotivo;
		
		public java.lang.Integer getIntIdcodigo(){
			return this.intIdcodigo;
		}
		public void setIntIdcodigo(java.lang.Integer intIdcodigo){
			this.intIdcodigo = intIdcodigo;
		}
		
		public java.lang.String getStrTabla(){
			return this.strTabla;
		}
		public void setStrTabla(java.lang.String strTabla){
			this.strTabla = strTabla;
		}
		
		public java.lang.String getStrColumna(){
			return this.strColumna;
		}
		public void setStrColumna(java.lang.String strColumna){
			this.strColumna = strColumna;
		}
		
		public java.lang.Integer getIntEmpresaPk(){
			return this.intEmpresaPk;
		}
		public void setIntEmpresaPk(java.lang.Integer intEmpresaPk){
			this.intEmpresaPk = intEmpresaPk;
		}
		
		public java.lang.String getStrLlave1(){
			return this.strLlave1;
		}
		public void setStrLlave1(java.lang.String strLlave1){
			this.strLlave1 = strLlave1;
		}
		
		public java.lang.String getStrLlave2(){
			return this.strLlave2;
		}
		public void setStrLlave2(java.lang.String strLlave2){
			this.strLlave2 = strLlave2;
		}
		
		public java.lang.String getStrLlave3(){
			return this.strLlave3;
		}
		public void setStrLlave3(java.lang.String strLlave3){
			this.strLlave3 = strLlave3;
		}
		
		public java.lang.String getStrLlave4(){
			return this.strLlave4;
		}
		public void setStrLlave4(java.lang.String strLlave4){
			this.strLlave4 = strLlave4;
		}
		
		public java.lang.String getStrLlave5(){
			return this.strLlave5;
		}
		public void setStrLlave5(java.lang.String strLlave5){
			this.strLlave5 = strLlave5;
		}
		
		public java.lang.String getStrLlave6(){
			return this.strLlave6;
		}
		public void setStrLlave6(java.lang.String strLlave6){
			this.strLlave6 = strLlave6;
		}
		
		public java.lang.String getStrLlave7(){
			return this.strLlave7;
		}
		public void setStrLlave7(java.lang.String strLlave7){
			this.strLlave7 = strLlave7;
		}
		
		public java.lang.String getStrLlave8(){
			return this.strLlave8;
		}
		public void setStrLlave8(java.lang.String strLlave8){
			this.strLlave8 = strLlave8;
		}
		
		public java.lang.String getStrLlave9(){
			return this.strLlave9;
		}
		public void setStrLlave9(java.lang.String strLlave9){
			this.strLlave9 = strLlave9;
		}
		
		public java.lang.String getStrLlave10(){
			return this.strLlave10;
		}
		public void setStrLlave10(java.lang.String strLlave10){
			this.strLlave10 = strLlave10;
		}
		
		public java.lang.Integer getIntTipo(){
			return this.intTipo;
		}
		public void setIntTipo(java.lang.Integer intTipo){
			this.intTipo = intTipo;
		}
		
		public java.lang.String getStrValoranterior(){
			return this.strValoranterior;
		}
		public void setStrValoranterior(java.lang.String strValoranterior){
			this.strValoranterior = strValoranterior;
		}
		
		public java.lang.String getStrValornuevo(){
			return this.strValornuevo;
		}
		public void setStrValornuevo(java.lang.String strValornuevo){
			this.strValornuevo = strValornuevo;
		}
		
		public java.sql.Timestamp getTsFecharegistro(){
			return this.tsFecharegistro;
		}
		public void setTsFecharegistro(java.sql.Timestamp tsFecharegistro){
			this.tsFecharegistro = tsFecharegistro;
		}
		
		public java.lang.Integer getIntPersonaPk(){
			return this.intPersonaPk;
		}
		public void setIntPersonaPk(java.lang.Integer intPersonaPk){
			this.intPersonaPk = intPersonaPk;
		}
		public List<AuditoriaMotivo> getListaAuditoriaMotivo() {
			return listaAuditoriaMotivo;
		}
		public void setListaAuditoriaMotivo(List<AuditoriaMotivo> listaAuditoriaMotivo) {
			this.listaAuditoriaMotivo = listaAuditoriaMotivo;
		}
		
}
