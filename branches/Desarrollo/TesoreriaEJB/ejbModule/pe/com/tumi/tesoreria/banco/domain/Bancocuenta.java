package pe.com.tumi.tesoreria.banco.domain;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.CuentaBancaria;

public class Bancocuenta extends TumiDomain{
		private BancocuentaId id;
     	private java.lang.String strNombrecuenta;
     	private java.lang.Integer intPersona;
     	private java.lang.Integer intCuentabancaria;
     	private java.lang.Integer intEmpresacuentaPk;
     	private java.lang.Integer intPeriodocuenta;
     	private java.lang.String strNumerocuenta;
     	private java.math.BigDecimal bdMontosobregiro;
     	private Bancofondo bancofondo;
     	private List<Bancocuentacheque> listaBancocuentacheque;
     	
     	private	CuentaBancaria	cuentaBancaria;
     	private PlanCuenta		planCuenta;
		
     	//interfaz
     	//correlativo de interfaz usado para identificar a BancoCuenta cuando aun no posee un correlativo de BD
     	private	Integer	intItemInterfaz;
     	private	String	strEtiqueta;
     	
     	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
     	private String strTipoCuenta;
     	private String strMoneda;
     	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
     	
     	public Bancocuenta(){
     		id = new BancocuentaId();
     		listaBancocuentacheque = new ArrayList<Bancocuentacheque>();
     		cuentaBancaria = new CuentaBancaria();
     	}
     	
		public BancocuentaId getId(){
			return this.id;
		}
		public void setId(BancocuentaId id){
			this.id = id;
		}
		
		public java.lang.String getStrNombrecuenta(){
			return this.strNombrecuenta;
		}
		public void setStrNombrecuenta(java.lang.String strNombrecuenta){
			this.strNombrecuenta = strNombrecuenta;
		}
		
		public java.lang.Integer getIntPersona(){
			return this.intPersona;
		}
		public void setIntPersona(java.lang.Integer intPersona){
			this.intPersona = intPersona;
		}
		
		public java.lang.Integer getIntCuentabancaria(){
			return this.intCuentabancaria;
		}
		public void setIntCuentabancaria(java.lang.Integer intCuentabancaria){
			this.intCuentabancaria = intCuentabancaria;
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
		
		public java.math.BigDecimal getBdMontosobregiro(){
			return this.bdMontosobregiro;
		}
		public void setBdMontosobregiro(java.math.BigDecimal bdMontosobregiro){
			this.bdMontosobregiro = bdMontosobregiro;
		}
		
     	public Bancofondo getBancofondo(){
			return this.bancofondo;
		}
		public void setBancofondo(Bancofondo bancofondo){
			this.bancofondo = bancofondo;
		}
		
		public List<Bancocuentacheque> getListaBancocuentacheque(){
			return this.listaBancocuentacheque;
		}
		public void setListaBancocuentacheque(List<Bancocuentacheque> listaBancocuentacheque){
			this.listaBancocuentacheque = listaBancocuentacheque;
		}
		public PlanCuenta getPlanCuenta() {
			return planCuenta;
		}
		public void setPlanCuenta(PlanCuenta planCuenta) {
			this.planCuenta = planCuenta;
		}
		public CuentaBancaria getCuentaBancaria() {
			return cuentaBancaria;
		}
		public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
			this.cuentaBancaria = cuentaBancaria;
		}
		public Integer getIntItemInterfaz() {
			return intItemInterfaz;
		}
		public void setIntItemInterfaz(Integer intItemInterfaz) {
			this.intItemInterfaz = intItemInterfaz;
		}
		public String getStrEtiqueta() {
			return strEtiqueta;
		}
		public void setStrEtiqueta(String strEtiqueta) {
			this.strEtiqueta = strEtiqueta;
		}
		@Override
		public String toString() {
			return "Bancocuenta [id=" + id + ", strNombrecuenta="
					+ strNombrecuenta + ", intPersona=" + intPersona
					+ ", intCuentabancaria=" + intCuentabancaria
					+ ", intEmpresacuentaPk=" + intEmpresacuentaPk
					+ ", intPeriodocuenta=" + intPeriodocuenta
					+ ", strNumerocuenta=" + strNumerocuenta
					+ ", bdMontosobregiro=" + bdMontosobregiro + "]";
		}

		/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
		public String getStrTipoCuenta() {
			return strTipoCuenta;
		}

		public void setStrTipoCuenta(String strTipoCuenta) {
			this.strTipoCuenta = strTipoCuenta;
		}

		public String getStrMoneda() {
			return strMoneda;
		}

		public void setStrMoneda(String strMoneda) {
			this.strMoneda = strMoneda;
		}
		/* Fin: REQ14-006 Bizarq - 26/10/2014 */
}
