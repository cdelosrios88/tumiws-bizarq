package pe.com.tumi.tesoreria.banco.domain;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;

public class Bancocuentacheque extends TumiDomain{
		private BancocuentachequeId id;
     	private java.lang.String strSerie;
     	private java.lang.String strControl;
     	private java.lang.Integer intNumeroinicio;
     	private java.lang.Integer intNumerofin;
     	private java.lang.Integer intTipoCod;
     	private java.lang.Integer intItemarchivo;
     	private java.lang.Integer intItemhistorico;
     	private java.lang.Integer intEmpresacuentaPk;
     	private java.lang.Integer intPeriodocuenta;
     	private java.lang.String strNumerocuenta;
     	private java.lang.Integer intIdiomaCod;
     	private java.lang.String strObservacion;
     	private java.lang.Integer intEstadoCod;
     	private Bancocuenta bancoCuenta;
		
     	//Interfaz
     	private	Archivo archivo;
     	private	String	strEtiqueta;
     	
     	public Bancocuentacheque(){
     		id = new BancocuentachequeId();
     		bancoCuenta = new Bancocuenta();
     		archivo = new Archivo();
     		archivo.setId(new ArchivoId());
     	}
     	
		public BancocuentachequeId getId(){
			return this.id;
		}
		public void setId(BancocuentachequeId id){
			this.id = id;
		}
		
		public java.lang.String getStrSerie(){
			return this.strSerie;
		}
		public void setStrSerie(java.lang.String strSerie){
			this.strSerie = strSerie;
		}
		
		public java.lang.String getStrControl(){
			return this.strControl;
		}
		public void setStrControl(java.lang.String strControl){
			this.strControl = strControl;
		}
		
		public java.lang.Integer getIntNumeroinicio(){
			return this.intNumeroinicio;
		}
		public void setIntNumeroinicio(java.lang.Integer intNumeroinicio){
			this.intNumeroinicio = intNumeroinicio;
		}
		
		public java.lang.Integer getIntNumerofin(){
			return this.intNumerofin;
		}
		public void setIntNumerofin(java.lang.Integer intNumerofin){
			this.intNumerofin = intNumerofin;
		}
		
		public java.lang.Integer getIntTipoCod(){
			return this.intTipoCod;
		}
		public void setIntTipoCod(java.lang.Integer intTipoCod){
			this.intTipoCod = intTipoCod;
		}
		
		public java.lang.Integer getIntItemarchivo(){
			return this.intItemarchivo;
		}
		public void setIntItemarchivo(java.lang.Integer intItemarchivo){
			this.intItemarchivo = intItemarchivo;
		}
		
		public java.lang.Integer getIntItemhistorico(){
			return this.intItemhistorico;
		}
		public void setIntItemhistorico(java.lang.Integer intItemhistorico){
			this.intItemhistorico = intItemhistorico;
		}
		
		public java.lang.Integer getIntEmpresacuentaPk(){
			return this.intEmpresacuentaPk;
		}
		public void setIntEmpresacuentaPk(java.lang.Integer intEmpresacuentaPk){
			this.intEmpresacuentaPk = intEmpresacuentaPk;
		}
		
		public java.lang.Integer getIntPeriodocuenta(){
			return this.intPeriodocuenta;
		}
		public void setIntPeriodocuenta(java.lang.Integer intPeriodocuenta){
			this.intPeriodocuenta = intPeriodocuenta;
		}
		
		public java.lang.String getStrNumerocuenta(){
			return this.strNumerocuenta;
		}
		public void setStrNumerocuenta(java.lang.String strNumerocuenta){
			this.strNumerocuenta = strNumerocuenta;
		}
		
		public java.lang.Integer getIntIdiomaCod(){
			return this.intIdiomaCod;
		}
		public void setIntIdiomaCod(java.lang.Integer intIdiomaCod){
			this.intIdiomaCod = intIdiomaCod;
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
		public Bancocuenta getBancoCuenta() {
			return bancoCuenta;
		}
		public void setBancoCuenta(Bancocuenta bancoCuenta) {
			this.bancoCuenta = bancoCuenta;
		}
		public Archivo getArchivo() {
			return archivo;
		}
		public void setArchivo(Archivo archivo) {
			this.archivo = archivo;
		}
		public String getStrEtiqueta() {
			return strEtiqueta;
		}

		public void setStrEtiqueta(String strEtiqueta) {
			this.strEtiqueta = strEtiqueta;
		}

		@Override
		public String toString() {
			return "Bancocuentacheque [id=" + id + ", strSerie=" + strSerie
					+ ", strControl=" + strControl + ", intNumeroinicio="
					+ intNumeroinicio + ", intNumerofin=" + intNumerofin
					+ ", intTipoCod=" + intTipoCod + ", intItemarchivo="
					+ intItemarchivo + ", intItemhistorico=" + intItemhistorico
					+ ", intEmpresacuentaPk=" + intEmpresacuentaPk
					+ ", intPeriodocuenta=" + intPeriodocuenta
					+ ", strNumerocuenta=" + strNumerocuenta
					+ ", intIdiomaCod=" + intIdiomaCod + ", strObservacion="
					+ strObservacion + ", intEstadoCod=" + intEstadoCod + "]";
		}
		
}
