package pe.com.tumi.cobranza.planilla.domain;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.domain.PerLaboral;

public class Enviomonto extends TumiDomain{
		private EnviomontoId id;
     	private java.math.BigDecimal bdMontoenvio;
     	private java.lang.Integer intTiposocioCod;
     	private java.lang.Integer intModalidadCod;
     	private java.lang.Integer intNivel;
     	private java.lang.Integer intCodigo;
     	private java.lang.Integer intTipoestructuraCod;
     	private java.lang.Integer intEmpresasucprocesaPk;
     	private java.lang.Integer intIdsucursalprocesaPk;
     	private java.lang.Integer intIdsubsucursalprocesaPk;
     	private java.lang.Integer intEmpresasucadministraPk;
     	private java.lang.Integer intIdsucursaladministraPk;
     	private java.lang.Integer intIdsubsucursaladministra;
     	private java.lang.Integer intEstadoCod;
     	private java.sql.Timestamp tsFecharegistro;
     	private java.lang.Integer intEmpresausuarioPk;
     	private java.lang.Integer intPersonausuarioPk;
     	private java.sql.Timestamp tsFechaeliminacion;
     	private java.lang.Integer intEmpresaeliminaPk;
     	private java.lang.Integer intPersonaeliminaPk;
     	private java.lang.Integer intIndiestructuraagregada;
     	private java.math.BigDecimal bdMontooriginal;
     	private Envioconcepto envioconcepto;
     	private Envioresumen envioresumen;
     	private List<Envioinflada> listaEnvioinflada;
     	
     	//Auxiliares
     	private Documento documento;
     	private Socio socio;
    	private Estructura estructura;
    	
    	private BigDecimal bdMontoEfectuado;
    	private BigDecimal bdDiferencia;
    	private BigDecimal bdEnvioTotal;
    	
    	private Juridica juridicaSucursal;
    	private EstructuraDetalle estructuraDetalle;
    	
    	//AUTOR Y FECHA CREACION: JCHAVEZ / 10-09-2013
     	private String strCobroPlanilla; //"C" SI EL EFECTUADO HA SIDO COBRADO "P" SI NO
     	//AUTOR Y FECHA CREACION: JCHAVEZ / 27-09-2013
     	private BigDecimal bdMontoenvioHaberes;
     	private String strCobroPlanillaHaberes; 
     	private BigDecimal bdMontoenvioIncentivos;
     	private String strCobroPlanillaIncentivos; 
     	private BigDecimal bdMontoenvioCas;
     	private String strCobroPlanillaCas; 
		
     	private List<Envioconcepto> listaEnvioConcepto;
     	
     	private PerLaboral perLaboral;
     	
     	private Boolean blnTieneNuevoEnvioresumen;
     	
		public Enviomonto()
		{
			id = new EnviomontoId();
		}
     	
     	public BigDecimal getBdEnvioTotal() {
			return bdEnvioTotal;
		}
		public void setBdEnvioTotal(BigDecimal bdEnvioTotal) {
			this.bdEnvioTotal = bdEnvioTotal;
		}
		public EnviomontoId getId(){
			return this.id;
		}
		public void setId(EnviomontoId id){
			this.id = id;
		}
		
		public java.math.BigDecimal getBdMontoenvio(){
			return this.bdMontoenvio;
		}
		public void setBdMontoenvio(java.math.BigDecimal bdMontoenvio){
			this.bdMontoenvio = bdMontoenvio;
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
		
		public java.lang.Integer getIntTipoestructuraCod(){
			return this.intTipoestructuraCod;
		}
		public void setIntTipoestructuraCod(java.lang.Integer intTipoestructuraCod){
			this.intTipoestructuraCod = intTipoestructuraCod;
		}
		
		public java.lang.Integer getIntEmpresasucprocesaPk(){
			return this.intEmpresasucprocesaPk;
		}
		public void setIntEmpresasucprocesaPk(java.lang.Integer intEmpresasucprocesaPk){
			this.intEmpresasucprocesaPk = intEmpresasucprocesaPk;
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
		
		public java.lang.Integer getIntEmpresasucadministraPk(){
			return this.intEmpresasucadministraPk;
		}
		public void setIntEmpresasucadministraPk(java.lang.Integer intEmpresasucadministraPk){
			this.intEmpresasucadministraPk = intEmpresasucadministraPk;
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
		
		public java.lang.Integer getIntEmpresausuarioPk(){
			return this.intEmpresausuarioPk;
		}
		public void setIntEmpresausuarioPk(java.lang.Integer intEmpresausuarioPk){
			this.intEmpresausuarioPk = intEmpresausuarioPk;
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
		
		public java.lang.Integer getIntEmpresaeliminaPk(){
			return this.intEmpresaeliminaPk;
		}
		public void setIntEmpresaeliminaPk(java.lang.Integer intEmpresaeliminaPk){
			this.intEmpresaeliminaPk = intEmpresaeliminaPk;
		}
		
		public java.lang.Integer getIntPersonaeliminaPk(){
			return this.intPersonaeliminaPk;
		}
		public void setIntPersonaeliminaPk(java.lang.Integer intPersonaeliminaPk){
			this.intPersonaeliminaPk = intPersonaeliminaPk;
		}
		
		public java.lang.Integer getIntIndiestructuraagregada() {
			return intIndiestructuraagregada;
		}
		public void setIntIndiestructuraagregada(
				java.lang.Integer intIndiestructuraagregada) {
			this.intIndiestructuraagregada = intIndiestructuraagregada;
		}
		
		public java.math.BigDecimal getBdMontooriginal(){
			return this.bdMontooriginal;
		}
		public void setBdMontooriginal(java.math.BigDecimal bdMontooriginal){
			this.bdMontooriginal = bdMontooriginal;
		}
		
     	public Envioconcepto getEnvioconcepto(){
			return this.envioconcepto;
		}
		public void setEnvioconcepto(Envioconcepto envioconcepto){
			this.envioconcepto = envioconcepto;
		}
		
     	public Envioresumen getEnvioresumen(){
			return this.envioresumen;
		}
		public void setEnvioresumen(Envioresumen envioresumen){
			this.envioresumen = envioresumen;
		}
		
		public List<Envioinflada> getListaEnvioinflada(){
			return this.listaEnvioinflada;
		}
		public void setListaEnvioinflada(List<Envioinflada> listaEnvioinflada){
			this.listaEnvioinflada = listaEnvioinflada;
		}
		
		public Documento getDocumento() {
			return documento;
		}
		
		public void setDocumento(Documento documento) {
			this.documento = documento;
		}
		
		public Socio getSocio() {
			return socio;
		}
		
		public void setSocio(Socio socio) {
			this.socio = socio;
		}
		
		public Estructura getEstructura() {
			return estructura;
		}
		
		public void setEstructura(Estructura estructura) {
			this.estructura = estructura;
		}
		
		
		
		public BigDecimal getBdMontoEfectuado() {
			return bdMontoEfectuado;
		}
		public void setBdMontoEfectuado(BigDecimal bdMontoEfectuado) {
			this.bdMontoEfectuado = bdMontoEfectuado;
		}
		public BigDecimal getBdDiferencia() {
			return bdDiferencia;
		}
		public void setBdDiferencia(BigDecimal bdDiferencia) {
			this.bdDiferencia = bdDiferencia;
		}
		
		public Juridica getJuridicaSucursal() {
			return juridicaSucursal;
		}
		public void setJuridicaSucursal(Juridica juridicaSucursal) {
			this.juridicaSucursal = juridicaSucursal;
		}
		
		public EstructuraDetalle getEstructuraDetalle() {
			return estructuraDetalle;
		}
		
		public void setEstructuraDetalle(EstructuraDetalle estructuraDetalle) {
			this.estructuraDetalle = estructuraDetalle;
		}
		
	
		public String getStrCobroPlanilla() {
			return strCobroPlanilla;
		}
		public void setStrCobroPlanilla(String strCobroPlanilla) {
			this.strCobroPlanilla = strCobroPlanilla;
		}

		public BigDecimal getBdMontoenvioHaberes() {
			return bdMontoenvioHaberes;
		}

		public void setBdMontoenvioHaberes(BigDecimal bdMontoenvioHaberes) {
			this.bdMontoenvioHaberes = bdMontoenvioHaberes;
		}

		public String getStrCobroPlanillaHaberes() {
			return strCobroPlanillaHaberes;
		}

		public void setStrCobroPlanillaHaberes(String strCobroPlanillaHaberes) {
			this.strCobroPlanillaHaberes = strCobroPlanillaHaberes;
		}

		public BigDecimal getBdMontoenvioCas() {
			return bdMontoenvioCas;
		}

		public void setBdMontoenvioCas(BigDecimal bdMontoenvioCas) {
			this.bdMontoenvioCas = bdMontoenvioCas;
		}

		public String getStrCobroPlanillaCas() {
			return strCobroPlanillaCas;
		}

		public void setStrCobroPlanillaCas(String strCobroPlanillaCas) {
			this.strCobroPlanillaCas = strCobroPlanillaCas;
		}

		public BigDecimal getBdMontoenvioIncentivos() {
			return bdMontoenvioIncentivos;
		}

		public void setBdMontoenvioIncentivos(BigDecimal bdMontoenvioIncentivos) {
			this.bdMontoenvioIncentivos = bdMontoenvioIncentivos;
		}

		public String getStrCobroPlanillaIncentivos() {
			return strCobroPlanillaIncentivos;
		}

		public void setStrCobroPlanillaIncentivos(String strCobroPlanillaIncentivos) {
			this.strCobroPlanillaIncentivos = strCobroPlanillaIncentivos;
		}

		public List<Envioconcepto> getListaEnvioConcepto() {
			return listaEnvioConcepto;
		}

		public void setListaEnvioConcepto(List<Envioconcepto> listaEnvioConcepto) {
			this.listaEnvioConcepto = listaEnvioConcepto;
		}
				
		public PerLaboral getPerLaboral() {
			return perLaboral;
		}

		public void setPerLaboral(PerLaboral perLaboral) {
			this.perLaboral = perLaboral;
		}		
		
		
		public Boolean getBlnTieneNuevoEnvioresumen() {
			return blnTieneNuevoEnvioresumen;
		}

		public void setBlnTieneNuevoEnvioresumen(Boolean blnTieneNuevoEnvioresumen) {
			this.blnTieneNuevoEnvioresumen = blnTieneNuevoEnvioresumen;
		}

		@Override
		public String toString() {
			return "Enviomonto [id=" + id + ", bdMontoenvio=" + bdMontoenvio
					+ ", intTiposocioCod=" + intTiposocioCod
					+ ", intModalidadCod=" + intModalidadCod + ", intNivel="
					+ intNivel + ", intCodigo=" + intCodigo
					+ ", intTipoestructuraCod=" + intTipoestructuraCod
					+ ", intEstadoCod=" + intEstadoCod + "]";
		}	
		
		
}

	
	