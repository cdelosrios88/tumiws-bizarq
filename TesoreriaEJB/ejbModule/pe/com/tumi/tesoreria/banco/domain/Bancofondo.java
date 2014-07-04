package pe.com.tumi.tesoreria.banco.domain;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class Bancofondo extends TumiDomain{
		private BancofondoId id;
     	private java.lang.Integer intBancoCod;
     	private java.lang.Integer intEmpresabancoPk;
     	private java.lang.Integer intPersonabancoPk;
     	private java.lang.Integer intTipoFondoFijo;
     	private java.lang.Integer intMonedaCod;
     	private java.lang.String strAbreviatura;
     	private java.lang.String strObservacion;
     	private java.lang.Integer intEstadoCod;
     	private java.sql.Timestamp tsFecharegistro;
     	private List<Bancocuenta> listaBancocuenta;
     	private List<Fondodetalle> listaFondodetalle;
		
     	private Fondodetalle	fondoDetalleUsar;
     	//interfaz
     	private Integer intTipoBancoFondoFiltro;
     	private	Integer	intCantidadBancoCuenta;
     	private boolean poseeSobregiro;
     	private String	strEtiqueta;
     	
     	private Bancocuenta 	bancoCuentaSeleccionada;
     	private PersonaEmpresa 	personaEmpresa;
     	
     	public Bancofondo(){
     		id = new BancofondoId();
     		listaBancocuenta = new ArrayList<Bancocuenta>();
     		listaFondodetalle = new ArrayList<Fondodetalle>();
     		personaEmpresa = new PersonaEmpresa();
     		personaEmpresa.setPersona(new Persona());
     		personaEmpresa.getPersona().setJuridica(new Juridica());
     	}
     	
		public BancofondoId getId(){
			return this.id;
		}
		public void setId(BancofondoId id){
			this.id = id;
		}
		
		public java.lang.Integer getIntBancoCod(){
			return this.intBancoCod;
		}
		public void setIntBancoCod(java.lang.Integer intBancoCod){
			this.intBancoCod = intBancoCod;
		}
		
		public java.lang.Integer getIntEmpresabancoPk(){
			return this.intEmpresabancoPk;
		}
		public void setIntEmpresabancoPk(java.lang.Integer intEmpresabancoPk){
			this.intEmpresabancoPk = intEmpresabancoPk;
		}
		
		public java.lang.Integer getIntPersonabancoPk(){
			return this.intPersonabancoPk;
		}
		public void setIntPersonabancoPk(java.lang.Integer intPersonabancoPk){
			this.intPersonabancoPk = intPersonabancoPk;
		}		
		public java.lang.Integer getIntTipoFondoFijo() {
			return intTipoFondoFijo;
		}
		public void setIntTipoFondoFijo(java.lang.Integer intTipoFondoFijo) {
			this.intTipoFondoFijo = intTipoFondoFijo;
		}
		public java.lang.Integer getIntMonedaCod(){
			return this.intMonedaCod;
		}
		public void setIntMonedaCod(java.lang.Integer intMonedaCod){
			this.intMonedaCod = intMonedaCod;
		}
		
		public java.lang.String getStrAbreviatura(){
			return this.strAbreviatura;
		}
		public void setStrAbreviatura(java.lang.String strAbreviatura){
			this.strAbreviatura = strAbreviatura;
		}
		
		public java.lang.String getStrObservacion(){
			return this.strObservacion;
		}
		public void setStrObservacion(java.lang.String strObservacion){
			this.strObservacion = strObservacion;
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
		
		public List<Bancocuenta> getListaBancocuenta(){
			return this.listaBancocuenta;
		}
		public void setListaBancocuenta(List<Bancocuenta> listaBancocuenta){
			this.listaBancocuenta = listaBancocuenta;
		}
		public List<Fondodetalle> getListaFondodetalle(){
			return this.listaFondodetalle;
		}
		public void setListaFondodetalle(List<Fondodetalle> listaFondodetalle){
			this.listaFondodetalle = listaFondodetalle;
		}
		public Integer getIntTipoBancoFondoFiltro() {
			return intTipoBancoFondoFiltro;
		}
		public void setIntTipoBancoFondoFiltro(Integer intTipoBancoFondoFiltro) {
			this.intTipoBancoFondoFiltro = intTipoBancoFondoFiltro;
		}
		public Integer getIntCantidadBancoCuenta() {
			return intCantidadBancoCuenta;
		}
		public void setIntCantidadBancoCuenta(Integer intCantidadBancoCuenta) {
			this.intCantidadBancoCuenta = intCantidadBancoCuenta;
		}
		public boolean isPoseeSobregiro() {
			return poseeSobregiro;
		}
		public void setPoseeSobregiro(boolean poseeSobregiro) {
			this.poseeSobregiro = poseeSobregiro;
		}
		public String getStrEtiqueta() {
			return strEtiqueta;
		}
		public void setStrEtiqueta(String strEtiqueta) {
			this.strEtiqueta = strEtiqueta;
		}
		public Fondodetalle getFondoDetalleUsar() {
			return fondoDetalleUsar;
		}
		public void setFondoDetalleUsar(Fondodetalle fondoDetalleUsar) {
			this.fondoDetalleUsar = fondoDetalleUsar;
		}
		public Bancocuenta getBancoCuentaSeleccionada() {
			return bancoCuentaSeleccionada;
		}

		public void setBancoCuentaSeleccionada(Bancocuenta bancoCuentaSeleccionada) {
			this.bancoCuentaSeleccionada = bancoCuentaSeleccionada;
		}

		@Override
		public String toString() {
			return "Bancofondo [id=" + id + ", intBancoCod=" + intBancoCod
					+ ", intEmpresabancoPk=" + intEmpresabancoPk
					+ ", intPersonabancoPk=" + intPersonabancoPk
					+ ", intTipoFondoFijo=" + intTipoFondoFijo
					+ ", intMonedaCod=" + intMonedaCod + ", strAbreviatura="
					+ strAbreviatura + ", strObservacion=" + strObservacion
					+ ", intEstadoCod=" + intEstadoCod + ", tsFecharegistro="
					+ tsFecharegistro + "]";
		}
		public PersonaEmpresa getPersonaEmpresa() {
			return personaEmpresa;
		}
		public void setPersonaEmpresa(PersonaEmpresa personaEmpresa) {
			this.personaEmpresa = personaEmpresa;
		}
}
