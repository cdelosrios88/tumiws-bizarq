package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.math.BigDecimal;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;

public class EgresoDetalleInterfaz extends TumiDomain{

	private Integer intParaConcepto;
	private String	strNroSolicitud;
	private BigDecimal	bdCapital;
	private BigDecimal	bdInteres;
	private BigDecimal	bdGravamen;
	private BigDecimal 	bdAporte;
	private BigDecimal	bdOtros;
	private BigDecimal	bdSubTotal;
	
	//Prevision Social
	private BigDecimal	bdMora;
	private BigDecimal	bdGastosAdministrativos;
	
	private ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle;
	private ModeloDetalle modeloDetalle;
	
	//fondos fijos
	private Integer intOrden;
	private Integer intParaDocumentoGeneral;
	private String	strNroDocumento;
	private Persona	persona;
	private String	strDescripcion;
	private	BigDecimal	bdMonto;
	private	Sucursal	sucursal;
	private Subsucursal	subsucursal;
	private LibroDiario libroDiario;
	private DocumentoSunat	documentoSunat;
	
	private boolean esDiferencial;	
	
	//JCHAVEZ 17.02.2014
	private Archivo archivoAdjuntoGiro;
	
	public Integer getIntParaConcepto() {
		return intParaConcepto;
	}
	public void setIntParaConcepto(Integer intParaConcepto) {
		this.intParaConcepto = intParaConcepto;
	}
	public String getStrNroSolicitud() {
		return strNroSolicitud;
	}
	public void setStrNroSolicitud(String strNroSolicitud) {
		this.strNroSolicitud = strNroSolicitud;
	}
	public BigDecimal getBdCapital() {
		return bdCapital;
	}
	public void setBdCapital(BigDecimal bdCapital) {
		this.bdCapital = bdCapital;
	}
	public BigDecimal getBdInteres() {
		return bdInteres;
	}
	public void setBdInteres(BigDecimal bdInteres) {
		this.bdInteres = bdInteres;
	}
	public BigDecimal getBdGravamen() {
		return bdGravamen;
	}
	public void setBdGravamen(BigDecimal bdGravamen) {
		this.bdGravamen = bdGravamen;
	}
	public BigDecimal getBdAporte() {
		return bdAporte;
	}
	public void setBdAporte(BigDecimal bdAporte) {
		this.bdAporte = bdAporte;
	}
	public BigDecimal getBdOtros() {
		return bdOtros;
	}
	public void setBdOtros(BigDecimal bdOtros) {
		this.bdOtros = bdOtros;
	}
	public BigDecimal getBdSubTotal() {
		return bdSubTotal;
	}
	public void setBdSubTotal(BigDecimal bdSubTotal) {
		this.bdSubTotal = bdSubTotal;
	}
	public BigDecimal getBdMora() {
		return bdMora;
	}
	public void setBdMora(BigDecimal bdMora) {
		this.bdMora = bdMora;
	}
	public BigDecimal getBdGastosAdministrativos() {
		return bdGastosAdministrativos;
	}
	public void setBdGastosAdministrativos(BigDecimal bdGastosAdministrativos) {
		this.bdGastosAdministrativos = bdGastosAdministrativos;
	}
	public ExpedienteLiquidacionDetalle getExpedienteLiquidacionDetalle() {
		return expedienteLiquidacionDetalle;
	}
	public void setExpedienteLiquidacionDetalle(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle) {
		this.expedienteLiquidacionDetalle = expedienteLiquidacionDetalle;
	}
	public ModeloDetalle getModeloDetalle() {
		return modeloDetalle;
	}
	public void setModeloDetalle(ModeloDetalle modeloDetalle) {
		this.modeloDetalle = modeloDetalle;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public String getStrNroDocumento() {
		return strNroDocumento;
	}
	public void setStrNroDocumento(String strNroDocumento) {
		this.strNroDocumento = strNroDocumento;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public BigDecimal getBdMonto() {
		return bdMonto;
	}
	public void setBdMonto(BigDecimal bdMonto) {
		this.bdMonto = bdMonto;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Subsucursal getSubsucursal() {
		return subsucursal;
	}
	public void setSubsucursal(Subsucursal subsucursal) {
		this.subsucursal = subsucursal;
	}
	public Integer getIntOrden() {
		return intOrden;
	}
	public void setIntOrden(Integer intOrden) {
		this.intOrden = intOrden;
	}
	public LibroDiario getLibroDiario() {
		return libroDiario;
	}
	public void setLibroDiario(LibroDiario libroDiario) {
		this.libroDiario = libroDiario;
	}
	public DocumentoSunat getDocumentoSunat() {
		return documentoSunat;
	}
	public void setDocumentoSunat(DocumentoSunat documentoSunat) {
		this.documentoSunat = documentoSunat;
	}
	public boolean isEsDiferencial() {
		return esDiferencial;
	}
	public void setEsDiferencial(boolean esDiferencial) {
		this.esDiferencial = esDiferencial;
	}
	public Archivo getArchivoAdjuntoGiro() {
		return archivoAdjuntoGiro;
	}
	public void setArchivoAdjuntoGiro(Archivo archivoAdjuntoGiro) {
		this.archivoAdjuntoGiro = archivoAdjuntoGiro;
	}
}