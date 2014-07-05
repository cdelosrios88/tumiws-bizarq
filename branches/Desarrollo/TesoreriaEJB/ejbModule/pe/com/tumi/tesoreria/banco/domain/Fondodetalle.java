package pe.com.tumi.tesoreria.banco.domain;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Fondodetalle extends TumiDomain{
		private FondodetalleId id;
     	private java.lang.Integer intCodigodetalle;
     	private java.lang.Integer intTotalsucursalCod;
     	private java.lang.Integer intEmpresasucursalPk;
     	private java.lang.Integer intIdsucursal;
     	private java.lang.Integer intIdsubsucursal;
     	private java.lang.Integer intEmpresacuentaPk;
     	private java.lang.Integer intPeriodocuenta;
     	private java.lang.String strNumerocuenta;
     	private java.lang.Integer intDocumentogeneralCod;
     	//private java.lang.Integer intSubdocumentogeneralCod;
     	private java.lang.Integer intTipocomprobanteCod;
     	private java.math.BigDecimal bdMontomaxdocumento;
     	private java.math.BigDecimal bdMontosobregiro;
     	private java.lang.Integer intEstadoCod;
     	private Bancofondo bancofondo;
		
     	//Interfaz
     	private Sucursal sucursal;
     	private Subsucursal subSucursal;
     	private PlanCuenta planCuenta;
     	private	String	strEtiquetaSucursal;
     	private	String	strEtiquetaSubsucursal;
     	
     	public Fondodetalle(){
     		id = new FondodetalleId();
     	}
		public FondodetalleId getId(){
			return this.id;
		}
		public void setId(FondodetalleId id){
			this.id = id;
		}
		
		public java.lang.Integer getIntCodigodetalle(){
			return this.intCodigodetalle;
		}
		public void setIntCodigodetalle(java.lang.Integer intCodigodetalle){
			this.intCodigodetalle = intCodigodetalle;
		}
		
		public java.lang.Integer getIntTotalsucursalCod(){
			return this.intTotalsucursalCod;
		}
		public void setIntTotalsucursalCod(java.lang.Integer intTotalsucursalCod){
			this.intTotalsucursalCod = intTotalsucursalCod;
		}
		
		public java.lang.Integer getIntEmpresasucursalPk(){
			return this.intEmpresasucursalPk;
		}
		public void setIntEmpresasucursalPk(java.lang.Integer intEmpresasucursalPk){
			this.intEmpresasucursalPk = intEmpresasucursalPk;
		}
		
		public java.lang.Integer getIntIdsucursal(){
			return this.intIdsucursal;
		}
		public void setIntIdsucursal(java.lang.Integer intIdsucursal){
			this.intIdsucursal = intIdsucursal;
		}
		
		public java.lang.Integer getIntIdsubsucursal(){
			return this.intIdsubsucursal;
		}
		public void setIntIdsubsucursal(java.lang.Integer intIdsubsucursal){
			this.intIdsubsucursal = intIdsubsucursal;
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
		
		public java.lang.Integer getIntDocumentogeneralCod(){
			return this.intDocumentogeneralCod;
		}
		public void setIntDocumentogeneralCod(java.lang.Integer intDocumentogeneralCod){
			this.intDocumentogeneralCod = intDocumentogeneralCod;
		}
		
		/*public java.lang.Integer getIntSubdocumentogeneralCod(){
			return this.intSubdocumentogeneralCod;
		}
		public void setIntSubdocumentogeneralCod(java.lang.Integer intSubdocumentogeneralCod){
			this.intSubdocumentogeneralCod = intSubdocumentogeneralCod;
		}*/
		
		public java.lang.Integer getIntTipocomprobanteCod(){
			return this.intTipocomprobanteCod;
		}
		public void setIntTipocomprobanteCod(java.lang.Integer intTipocomprobanteCod){
			this.intTipocomprobanteCod = intTipocomprobanteCod;
		}
		
		public java.math.BigDecimal getBdMontomaxdocumento(){
			return this.bdMontomaxdocumento;
		}
		public void setBdMontomaxdocumento(java.math.BigDecimal bdMontomaxdocumento){
			this.bdMontomaxdocumento = bdMontomaxdocumento;
		}
		
		public java.math.BigDecimal getBdMontosobregiro(){
			return this.bdMontosobregiro;
		}
		public void setBdMontosobregiro(java.math.BigDecimal bdMontosobregiro){
			this.bdMontosobregiro = bdMontosobregiro;
		}
		
		public java.lang.Integer getIntEstadoCod(){
			return this.intEstadoCod;
		}
		public void setIntEstadoCod(java.lang.Integer intEstadoCod){
			this.intEstadoCod = intEstadoCod;
		}
		
     	public Bancofondo getBancofondo(){
			return this.bancofondo;
		}
		public void setBancofondo(Bancofondo bancofondo){
			this.bancofondo = bancofondo;
		}
		public Sucursal getSucursal() {
			return sucursal;
		}
		public void setSucursal(Sucursal sucursal) {
			this.sucursal = sucursal;
		}
		public Subsucursal getSubSucursal() {
			return subSucursal;
		}
		public void setSubSucursal(Subsucursal subSucursal) {
			this.subSucursal = subSucursal;
		}
		public PlanCuenta getPlanCuenta() {
			return planCuenta;
		}
		public void setPlanCuenta(PlanCuenta planCuenta) {
			this.planCuenta = planCuenta;
		}
		public String getStrEtiquetaSucursal() {
			return strEtiquetaSucursal;
		}
		public void setStrEtiquetaSucursal(String strEtiquetaSucursal) {
			this.strEtiquetaSucursal = strEtiquetaSucursal;
		}
		public String getStrEtiquetaSubsucursal() {
			return strEtiquetaSubsucursal;
		}
		public void setStrEtiquetaSubsucursal(String strEtiquetaSubsucursal) {
			this.strEtiquetaSubsucursal = strEtiquetaSubsucursal;
		}
		@Override
		public String toString() {
			return "Fondodetalle [id=" + id + ", intCodigodetalle="
					+ intCodigodetalle + ", intTotalsucursalCod="
					+ intTotalsucursalCod + ", intEmpresasucursalPk="
					+ intEmpresasucursalPk + ", intIdsucursal=" + intIdsucursal
					+ ", intIdsubsucursal=" + intIdsubsucursal
					+ ", intEmpresacuentaPk=" + intEmpresacuentaPk
					+ ", intPeriodocuenta=" + intPeriodocuenta
					+ ", strNumerocuenta=" + strNumerocuenta
					+ ", intDocumentogeneralCod=" + intDocumentogeneralCod
					/*+ ", intSubdocumentogeneralCod="
					+ intSubdocumentogeneralCod */+ ", intTipocomprobanteCod="
					+ intTipocomprobanteCod + ", bdMontomaxdocumento="
					+ bdMontomaxdocumento + ", bdMontosobregiro="
					+ bdMontosobregiro + ", intEstadoCod=" + intEstadoCod + "]";
		}
		
}
