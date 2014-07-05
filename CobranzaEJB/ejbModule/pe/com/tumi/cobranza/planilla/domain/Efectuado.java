package pe.com.tumi.cobranza.planilla.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Persona;

public class Efectuado extends TumiDomain{
		private EfectuadoId id;
		private java.sql.Timestamp tsFechaProceso;
		private java.lang.Integer intParaTipoIngresoDatoCod;
		private java.lang.Integer intCuentaPk;
		private java.lang.Integer intPeriodoPlanilla;
     	private java.math.BigDecimal bdMontoEfectuado;
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
     	
     	private EfectuadoResumen efectuadoResumen;
     	private Enviomonto envioMonto;
     	
     	private List<EfectuadoConcepto> listaEfectuadoConcepto;
     	//AUTOR Y FECHA CREACION: JCHAVEZ / 10-09-2013
     	private String strCobroPlanilla; //"C" SI EL EFECTUADO HA SIDO COBRADO "P" SI NO
     	//AUTOR Y FECHA CREACION: JCHAVEZ / 27-09-2013
     	private BigDecimal bdMontoEfectuadoHaberes;
     	private BigDecimal bdMontoEfectuadoIncentivos;
     	private BigDecimal bdMontoEfectuadoCas;
     	
     	private Documento documento;
     	private Socio socio;
     	private CuentaIntegrante cuentaIntegrante;
     	private Boolean blnLIC;
    	private Boolean blnDJUD;
    	private Boolean blnCartaAutorizacion;
    	private BigDecimal bdMontoEnvio;
    	private BigDecimal bdDiferencia;
    	private BigDecimal bdEfectuadoTotal;
    	private Boolean blnListaEnvioConcepto;
    	private Integer intPersonaIntegrante;
    	private Boolean blnAgregarNoSocio;
    	private java.math.BigDecimal bdMontoEnviadoDeEnviomonto;    	
    	private Persona persona;
    	private Boolean blnTieneSocio;
    	private Boolean blnNoTieneSocio;
    	
    	//auxiliar con archivo
    	private Boolean blnEfectuadoConcepto0;
    	
     	public Efectuado()
     	{
     		id = new EfectuadoId();
     		listaEfectuadoConcepto =  new ArrayList<EfectuadoConcepto>();
     	}

		public EfectuadoId getId() {
			return id;
		}

		public void setId(EfectuadoId id) {
			this.id = id;
		}

		public java.sql.Timestamp getTsFechaProceso() {
			return tsFechaProceso;
		}

		public void setTsFechaProceso(java.sql.Timestamp tsFechaProceso) {
			this.tsFechaProceso = tsFechaProceso;
		}

		public java.lang.Integer getIntParaTipoIngresoDatoCod() {
			return intParaTipoIngresoDatoCod;
		}

		public void setIntParaTipoIngresoDatoCod(
				java.lang.Integer intParaTipoIngresoDatoCod) {
			this.intParaTipoIngresoDatoCod = intParaTipoIngresoDatoCod;
		}

		public java.lang.Integer getIntCuentaPk() {
			return intCuentaPk;
		}

		public void setIntCuentaPk(java.lang.Integer intCuentaPk) {
			this.intCuentaPk = intCuentaPk;
		}

		public java.lang.Integer getIntPeriodoPlanilla() {
			return intPeriodoPlanilla;
		}

		public void setIntPeriodoPlanilla(java.lang.Integer intPeriodoPlanilla) {
			this.intPeriodoPlanilla = intPeriodoPlanilla;
		}

		public java.math.BigDecimal getBdMontoEfectuado() {
			return bdMontoEfectuado;
		}

		public void setBdMontoEfectuado(java.math.BigDecimal bdMontoEfectuado) {
			this.bdMontoEfectuado = bdMontoEfectuado;
		}

		public java.lang.Integer getIntTiposocioCod() {
			return intTiposocioCod;
		}

		public void setIntTiposocioCod(java.lang.Integer intTiposocioCod) {
			this.intTiposocioCod = intTiposocioCod;
		}

		public java.lang.Integer getIntModalidadCod() {
			return intModalidadCod;
		}

		public void setIntModalidadCod(java.lang.Integer intModalidadCod) {
			this.intModalidadCod = intModalidadCod;
		}

		public java.lang.Integer getIntNivel() {
			return intNivel;
		}

		public void setIntNivel(java.lang.Integer intNivel) {
			this.intNivel = intNivel;
		}

		public java.lang.Integer getIntCodigo() {
			return intCodigo;
		}

		public void setIntCodigo(java.lang.Integer intCodigo) {
			this.intCodigo = intCodigo;
		}

		public java.lang.Integer getIntTipoestructuraCod() {
			return intTipoestructuraCod;
		}

		public void setIntTipoestructuraCod(java.lang.Integer intTipoestructuraCod) {
			this.intTipoestructuraCod = intTipoestructuraCod;
		}

		public java.lang.Integer getIntEmpresasucprocesaPk() {
			return intEmpresasucprocesaPk;
		}

		public void setIntEmpresasucprocesaPk(java.lang.Integer intEmpresasucprocesaPk) {
			this.intEmpresasucprocesaPk = intEmpresasucprocesaPk;
		}

		public java.lang.Integer getIntIdsucursalprocesaPk() {
			return intIdsucursalprocesaPk;
		}

		public void setIntIdsucursalprocesaPk(java.lang.Integer intIdsucursalprocesaPk) {
			this.intIdsucursalprocesaPk = intIdsucursalprocesaPk;
		}

		public java.lang.Integer getIntIdsubsucursalprocesaPk() {
			return intIdsubsucursalprocesaPk;
		}

		public void setIntIdsubsucursalprocesaPk(
				java.lang.Integer intIdsubsucursalprocesaPk) {
			this.intIdsubsucursalprocesaPk = intIdsubsucursalprocesaPk;
		}

		public java.lang.Integer getIntEmpresasucadministraPk() {
			return intEmpresasucadministraPk;
		}

		public void setIntEmpresasucadministraPk(
				java.lang.Integer intEmpresasucadministraPk) {
			this.intEmpresasucadministraPk = intEmpresasucadministraPk;
		}

		public java.lang.Integer getIntIdsucursaladministraPk() {
			return intIdsucursaladministraPk;
		}

		public void setIntIdsucursaladministraPk(
				java.lang.Integer intIdsucursaladministraPk) {
			this.intIdsucursaladministraPk = intIdsucursaladministraPk;
		}

		public java.lang.Integer getIntIdsubsucursaladministra() {
			return intIdsubsucursaladministra;
		}

		public void setIntIdsubsucursaladministra(
				java.lang.Integer intIdsubsucursaladministra) {
			this.intIdsubsucursaladministra = intIdsubsucursaladministra;
		}

		public java.lang.Integer getIntEstadoCod() {
			return intEstadoCod;
		}

		public void setIntEstadoCod(java.lang.Integer intEstadoCod) {
			this.intEstadoCod = intEstadoCod;
		}

		public java.sql.Timestamp getTsFecharegistro() {
			return tsFecharegistro;
		}

		public void setTsFecharegistro(java.sql.Timestamp tsFecharegistro) {
			this.tsFecharegistro = tsFecharegistro;
		}

		public java.lang.Integer getIntEmpresausuarioPk() {
			return intEmpresausuarioPk;
		}

		public void setIntEmpresausuarioPk(java.lang.Integer intEmpresausuarioPk) {
			this.intEmpresausuarioPk = intEmpresausuarioPk;
		}

		public java.lang.Integer getIntPersonausuarioPk() {
			return intPersonausuarioPk;
		}

		public void setIntPersonausuarioPk(java.lang.Integer intPersonausuarioPk) {
			this.intPersonausuarioPk = intPersonausuarioPk;
		}

		public java.sql.Timestamp getTsFechaeliminacion() {
			return tsFechaeliminacion;
		}

		public void setTsFechaeliminacion(java.sql.Timestamp tsFechaeliminacion) {
			this.tsFechaeliminacion = tsFechaeliminacion;
		}

		public java.lang.Integer getIntEmpresaeliminaPk() {
			return intEmpresaeliminaPk;
		}

		public void setIntEmpresaeliminaPk(java.lang.Integer intEmpresaeliminaPk) {
			this.intEmpresaeliminaPk = intEmpresaeliminaPk;
		}

		public java.lang.Integer getIntPersonaeliminaPk() {
			return intPersonaeliminaPk;
		}

		public void setIntPersonaeliminaPk(java.lang.Integer intPersonaeliminaPk) {
			this.intPersonaeliminaPk = intPersonaeliminaPk;
		}

		public java.lang.Integer getIntIndiestructuraagregada() {
			return intIndiestructuraagregada;
		}
		public void setIntIndiestructuraagregada(
				java.lang.Integer intIndiestructuraagregada) {
			this.intIndiestructuraagregada = intIndiestructuraagregada;
		}

		public java.math.BigDecimal getBdMontooriginal() {
			return bdMontooriginal;
		}

		public void setBdMontooriginal(java.math.BigDecimal bdMontooriginal) {
			this.bdMontooriginal = bdMontooriginal;
		}

		public EfectuadoResumen getEfectuadoResumen() {
			return efectuadoResumen;
		}

		public void setEfectuadoResumen(EfectuadoResumen efectuadoResumen) {
			this.efectuadoResumen = efectuadoResumen;
		}

		public Enviomonto getEnvioMonto() {
			return envioMonto;
		}

		public void setEnvioMonto(Enviomonto envioMonto) {
			this.envioMonto = envioMonto;
		}

		public List<EfectuadoConcepto> getListaEfectuadoConcepto() {
			return listaEfectuadoConcepto;
		}

		public void setListaEfectuadoConcepto(
				List<EfectuadoConcepto> listaEfectuadoConcepto) {
			this.listaEfectuadoConcepto = listaEfectuadoConcepto;
		}

		public String getStrCobroPlanilla() {
			return strCobroPlanilla;
		}

		public void setStrCobroPlanilla(String strCobroPlanilla) {
			this.strCobroPlanilla = strCobroPlanilla;
		}

		public BigDecimal getBdMontoEfectuadoHaberes() {
			return bdMontoEfectuadoHaberes;
		}

		public void setBdMontoEfectuadoHaberes(BigDecimal bdMontoEfectuadoHaberes) {
			this.bdMontoEfectuadoHaberes = bdMontoEfectuadoHaberes;
		}

		public BigDecimal getBdMontoEfectuadoIncentivos() {
			return bdMontoEfectuadoIncentivos;
		}

		public void setBdMontoEfectuadoIncentivos(BigDecimal bdMontoEfectuadoIncentivos) {
			this.bdMontoEfectuadoIncentivos = bdMontoEfectuadoIncentivos;
		}

		public BigDecimal getBdMontoEfectuadoCas() {
			return bdMontoEfectuadoCas;
		}

		public void setBdMontoEfectuadoCas(BigDecimal bdMontoEfectuadoCas) {
			this.bdMontoEfectuadoCas = bdMontoEfectuadoCas;
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

		public CuentaIntegrante getCuentaIntegrante() {
			return cuentaIntegrante;
		}

		public void setCuentaIntegrante(CuentaIntegrante cuentaIntegrante) {
			this.cuentaIntegrante = cuentaIntegrante;
		}

		public Boolean getBlnLIC() {
			return blnLIC;
		}

		public void setBlnLIC(Boolean blnLIC) {
			this.blnLIC = blnLIC;
		}

		public Boolean getBlnDJUD() {
			return blnDJUD;
		}

		public void setBlnDJUD(Boolean blnDJUD) {
			this.blnDJUD = blnDJUD;
		}

		public Boolean getBlnCartaAutorizacion() {
			return blnCartaAutorizacion;
		}

		public void setBlnCartaAutorizacion(Boolean blnCartaAutorizacion) {
			this.blnCartaAutorizacion = blnCartaAutorizacion;
		}

		public BigDecimal getBdMontoEnvio() {
			return bdMontoEnvio;
		}

		public void setBdMontoEnvio(BigDecimal bdMontoEnvio) {
			this.bdMontoEnvio = bdMontoEnvio;
		}

		public BigDecimal getBdDiferencia() {
			return bdDiferencia;
		}

		public void setBdDiferencia(BigDecimal bdDiferencia) {
			this.bdDiferencia = bdDiferencia;
		}

		public BigDecimal getBdEfectuadoTotal() {
			return bdEfectuadoTotal;
		}

		public void setBdEfectuadoTotal(BigDecimal bdEfectuadoTotal) {
			this.bdEfectuadoTotal = bdEfectuadoTotal;
		}	
		
		public Boolean getBlnListaEnvioConcepto() {
			return blnListaEnvioConcepto;
		}

		public void setBlnListaEnvioConcepto(Boolean blnListaEnvioConcepto) {
			this.blnListaEnvioConcepto = blnListaEnvioConcepto;
		}
		
		public java.math.BigDecimal getBdMontoEnviadoDeEnviomonto() {
			
			return bdMontoEnviadoDeEnviomonto;
		}

		public void setBdMontoEnviadoDeEnviomonto(
				java.math.BigDecimal bdMontoEnviadoDeEnviomonto) {
			this.bdMontoEnviadoDeEnviomonto = bdMontoEnviadoDeEnviomonto;
		}
	
		public Integer getIntPersonaIntegrante() {
			return intPersonaIntegrante;
		}

		public void setIntPersonaIntegrante(Integer intPersonaIntegrante) {
			this.intPersonaIntegrante = intPersonaIntegrante;
		}
		
		
		public Boolean getBlnAgregarNoSocio() {
			return blnAgregarNoSocio;
		}

		public void setBlnAgregarNoSocio(Boolean blnAgregarNoSocio) {
			this.blnAgregarNoSocio = blnAgregarNoSocio;
		}
				
		public Persona getPersona() {
			return persona;
		}

		public void setPersona(Persona persona) {
			this.persona = persona;
		}

		
		public Boolean getBlnTieneSocio() {
			return blnTieneSocio;
		}

		public void setBlnTieneSocio(Boolean blnTieneSocio) {
			this.blnTieneSocio = blnTieneSocio;
		}
		
		public Boolean getBlnNoTieneSocio() {
			return blnNoTieneSocio;
		}

		public void setBlnNoTieneSocio(Boolean blnNoTieneSocio) {
			this.blnNoTieneSocio = blnNoTieneSocio;
		}
		
		public Boolean getBlnEfectuadoConcepto0() {
			return blnEfectuadoConcepto0;
		}

		public void setBlnEfectuadoConcepto0(Boolean blnEfectuadoConcepto0) {
			this.blnEfectuadoConcepto0 = blnEfectuadoConcepto0;
		}

		@Override
		public String toString() {
			return "Efectuado [id=" + id + ", intCuentaPk=" + intCuentaPk
					+ ", intPeriodoPlanilla=" + intPeriodoPlanilla
					+ ", bdMontoEfectuado=" + bdMontoEfectuado
					+ ", intTiposocioCod=" + intTiposocioCod
					+ ", intModalidadCod=" + intModalidadCod + ", intNivel="
					+ intNivel + ", intCodigo=" + intCodigo
					+ ", intIdsucursalprocesaPk=" + intIdsucursalprocesaPk
					+ ", intIdsubsucursalprocesaPk="
					+ intIdsubsucursalprocesaPk
					+ ", intEmpresasucadministraPk="
					+ intEmpresasucadministraPk
					+ ", intIdsucursaladministraPk="
					+ intIdsucursaladministraPk + ", intPersonaIntegrante="
					+ intPersonaIntegrante + "]";
		}

		
	
}
