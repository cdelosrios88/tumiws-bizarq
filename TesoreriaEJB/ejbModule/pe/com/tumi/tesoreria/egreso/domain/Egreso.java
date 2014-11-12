package pe.com.tumi.tesoreria.egreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;

public class Egreso extends TumiDomain{

	private	EgresoId 	id;
	private	Integer	intParaTipoOperacion;
	private	Integer	intParaFormaPago;
	private	Integer	intParaDocumentoGeneral;
	private	Integer	intItemPeriodoEgreso;
	private	Integer	intItemEgreso;
	private	Integer	intSucuIdSucursal;
	private	Integer	intSudeIdSubsucursal;
	private	Integer	intParaSubTipoOperacion;
	private	Timestamp	tsFechaProceso;
	private	Date	dtFechaEgreso;
	private	Integer	intParaTipoFondoFijo;
	private	Integer	intItemPeriodoFondo;
	private	Integer	intItemFondoFijo;
	private	Integer	intItemBancoFondo;
	private	Integer	intItemBancoCuenta;
	private	Integer	intItemBancoCuentaCheque;
	private	Integer	intNumeroPlanilla;
	private	Integer	intNumeroCheque;
	private	Integer	intNumeroTransferencia;
	private	Timestamp	tsFechaPagoDiferido;
	private	Integer	intPersEmpresaGirado;
	private	Integer	intPersPersonaGirado;
	private	Integer	intCuentaGirado;
//	se cambia a tipo string
//	private	Integer	intPersCuentaBancariaGirado;
	//Autor jchavez / Tarea: Se regresa al tipo de dato integer y se graba la llave de la cuenta / Fecha: 19.09.2014
//	private	String	strPersCuentaBancariaGirado;
	private	Integer	intPersCuentaBancariaGirado;
	//Fin jchavez - 19.09.2014
	
	private	Integer	intPersEmpresaBeneficiario;
	private	Integer	intPersPersonaBeneficiario;
	private	Integer	intPersCuentaBancariaBeneficiario;
	private	Integer	intPersPersonaApoderado;
	private	Integer	intPersEmpresaApoderado;
	private	BigDecimal	bdMontoTotal;
	private	String	strObservacion;
	private	Integer	intParaEstado;
	private	Integer	intPersEmpresaLibro;
	private	Integer	intContPeriodoLibro;
	private	Integer	intContCodigoLibro;
	private Timestamp tsFechaRegistro;
	private Integer intPersEmpresaUsuario;
	private Integer intPersPersonaUsuario;
	private Integer intPersEmpresaEgresoAnula;
	private Integer intPersPersonaEgresoAnula;
	private Integer intParaTipoApoderado;
	private	Integer	intItemArchivoApoderado;
	private	Integer	intItemHistoricoApoderado;
	private	Integer	intParaTipoGiro;
	private	Integer	intItemArchivoGiro;
	private	Integer	intItemHistoricoGiro;
	
	
	private	List<EgresoDetalle> listaEgresoDetalle;
	private	LibroDiario	libroDiario;
	private	String	strNumeroEgreso;
	private	Persona	personaApoderado;
	private ControlFondosFijos	controlFondosFijos;
	private Bancocuenta bancoCuenta;
	
	//JCHAVEZ 06.01.2014
	private Integer intErrorGeneracionEgreso; //flag de error en la generacion de egreso. 1.Generacion fallida 0.Generacion exitosa
	private String strMsgErrorGeneracionEgreso;
	
	//JCHAVEZ 07.01.2014
	private Integer intPersEmpresaLibroExtorno;
	private Integer intContPeriodoLibroExtorno;
	private Integer intContCodigoLibroExtorno;	
	private Integer intEsGiroPorSedeCentral;	
	private Boolean blnEsGiroPorSedeCentral;	
	private ExpedienteCredito expedienteCredito;	
	private String strNumeroLibro;
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	private EgresoDetalle egresoDetConciliacion;
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
	public Egreso(){
		id = new EgresoId();
		listaEgresoDetalle = new ArrayList<EgresoDetalle>();
	}
	
	public EgresoId getId() {
		return id;
	}
	public void setId(EgresoId id) {
		this.id = id;
	}
	public Integer getIntParaTipoOperacion() {
		return intParaTipoOperacion;
	}
	public void setIntParaTipoOperacion(Integer intParaTipoOperacion) {
		this.intParaTipoOperacion = intParaTipoOperacion;
	}
	public Integer getIntParaFormaPago() {
		return intParaFormaPago;
	}
	public void setIntParaFormaPago(Integer intParaFormaPago) {
		this.intParaFormaPago = intParaFormaPago;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Integer getIntItemPeriodoEgreso() {
		return intItemPeriodoEgreso;
	}
	public void setIntItemPeriodoEgreso(Integer intItemPeriodoEgreso) {
		this.intItemPeriodoEgreso = intItemPeriodoEgreso;
	}
	public Integer getIntItemEgreso() {
		return intItemEgreso;
	}
	public void setIntItemEgreso(Integer intItemEgreso) {
		this.intItemEgreso = intItemEgreso;
	}
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSudeIdSubsucursal() {
		return intSudeIdSubsucursal;
	}
	public void setIntSudeIdSubsucursal(Integer intSudeIdSubsucursal) {
		this.intSudeIdSubsucursal = intSudeIdSubsucursal;
	}
	public Integer getIntParaSubTipoOperacion() {
		return intParaSubTipoOperacion;
	}
	public void setIntParaSubTipoOperacion(Integer intParaSubTipoOperacion) {
		this.intParaSubTipoOperacion = intParaSubTipoOperacion;
	}
	public Timestamp getTsFechaProceso() {
		return tsFechaProceso;
	}
	public void setTsFechaProceso(Timestamp tsFechaProceso) {
		this.tsFechaProceso = tsFechaProceso;
	}
	public Date getDtFechaEgreso() {
		return dtFechaEgreso;
	}
	public void setDtFechaEgreso(Date dtFechaEgreso) {
		this.dtFechaEgreso = dtFechaEgreso;
	}
	public Integer getIntParaTipoFondoFijo() {
		return intParaTipoFondoFijo;
	}
	public void setIntParaTipoFondoFijo(Integer intParaTipoFondoFijo) {
		this.intParaTipoFondoFijo = intParaTipoFondoFijo;
	}
	public Integer getIntItemPeriodoFondo() {
		return intItemPeriodoFondo;
	}
	public void setIntItemPeriodoFondo(Integer intItemPeriodoFondo) {
		this.intItemPeriodoFondo = intItemPeriodoFondo;
	}
	public Integer getIntItemFondoFijo() {
		return intItemFondoFijo;
	}
	public void setIntItemFondoFijo(Integer intItemFondoFijo) {
		this.intItemFondoFijo = intItemFondoFijo;
	}
	public Integer getIntItemBancoFondo() {
		return intItemBancoFondo;
	}
	public void setIntItemBancoFondo(Integer intItemBancoFondo) {
		this.intItemBancoFondo = intItemBancoFondo;
	}
	public Integer getIntItemBancoCuenta() {
		return intItemBancoCuenta;
	}
	public void setIntItemBancoCuenta(Integer intItemBancoCuenta) {
		this.intItemBancoCuenta = intItemBancoCuenta;
	}
	public Integer getIntItemBancoCuentaCheque() {
		return intItemBancoCuentaCheque;
	}
	public void setIntItemBancoCuentaCheque(Integer intItemBancoCuentaCheque) {
		this.intItemBancoCuentaCheque = intItemBancoCuentaCheque;
	}
	public Integer getIntNumeroPlanilla() {
		return intNumeroPlanilla;
	}
	public void setIntNumeroPlanilla(Integer intNumeroPlanilla) {
		this.intNumeroPlanilla = intNumeroPlanilla;
	}
	public Integer getIntNumeroCheque() {
		return intNumeroCheque;
	}
	public void setIntNumeroCheque(Integer intNumeroCheque) {
		this.intNumeroCheque = intNumeroCheque;
	}
	public Integer getIntNumeroTransferencia() {
		return intNumeroTransferencia;
	}
	public void setIntNumeroTransferencia(Integer intNumeroTransferencia) {
		this.intNumeroTransferencia = intNumeroTransferencia;
	}
	public Timestamp getTsFechaPagoDiferido() {
		return tsFechaPagoDiferido;
	}
	public void setTsFechaPagoDiferido(Timestamp tsFechaPagoDiferido) {
		this.tsFechaPagoDiferido = tsFechaPagoDiferido;
	}
	public Integer getIntPersEmpresaGirado() {
		return intPersEmpresaGirado;
	}
	public void setIntPersEmpresaGirado(Integer intPersEmpresaGirado) {
		this.intPersEmpresaGirado = intPersEmpresaGirado;
	}
	public Integer getIntPersPersonaGirado() {
		return intPersPersonaGirado;
	}
	public void setIntPersPersonaGirado(Integer intPersPersonaGirado) {
		this.intPersPersonaGirado = intPersPersonaGirado;
	}
	public Integer getIntCuentaGirado() {
		return intCuentaGirado;
	}
	public void setIntCuentaGirado(Integer intCuentaGirado) {
		this.intCuentaGirado = intCuentaGirado;
	}
	public Integer getIntPersEmpresaBeneficiario() {
		return intPersEmpresaBeneficiario;
	}
	public void setIntPersEmpresaBeneficiario(Integer intPersEmpresaBeneficiario) {
		this.intPersEmpresaBeneficiario = intPersEmpresaBeneficiario;
	}
	public Integer getIntPersPersonaBeneficiario() {
		return intPersPersonaBeneficiario;
	}
	public void setIntPersPersonaBeneficiario(Integer intPersPersonaBeneficiario) {
		this.intPersPersonaBeneficiario = intPersPersonaBeneficiario;
	}
	public Integer getIntPersCuentaBancariaBeneficiario() {
		return intPersCuentaBancariaBeneficiario;
	}
	public void setIntPersCuentaBancariaBeneficiario(
			Integer intPersCuentaBancariaBeneficiario) {
		this.intPersCuentaBancariaBeneficiario = intPersCuentaBancariaBeneficiario;
	}
	public Integer getIntPersPersonaApoderado() {
		return intPersPersonaApoderado;
	}
	public void setIntPersPersonaApoderado(Integer intPersPersonaApoderado) {
		this.intPersPersonaApoderado = intPersPersonaApoderado;
	}
	public Integer getIntPersEmpresaApoderado() {
		return intPersEmpresaApoderado;
	}
	public void setIntPersEmpresaApoderado(Integer intPersEmpresaApoderado) {
		this.intPersEmpresaApoderado = intPersEmpresaApoderado;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntPersEmpresaLibro() {
		return intPersEmpresaLibro;
	}
	public void setIntPersEmpresaLibro(Integer intPersEmpresaLibro) {
		this.intPersEmpresaLibro = intPersEmpresaLibro;
	}
	public Integer getIntContPeriodoLibro() {
		return intContPeriodoLibro;
	}
	public void setIntContPeriodoLibro(Integer intContPeriodoLibro) {
		this.intContPeriodoLibro = intContPeriodoLibro;
	}
	public Integer getIntContCodigoLibro() {
		return intContCodigoLibro;
	}
	public void setIntContCodigoLibro(Integer intContCodigoLibro) {
		this.intContCodigoLibro = intContCodigoLibro;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersEmpresaUsuario() {
		return intPersEmpresaUsuario;
	}
	public void setIntPersEmpresaUsuario(Integer intPersEmpresaUsuario) {
		this.intPersEmpresaUsuario = intPersEmpresaUsuario;
	}
	public Integer getIntPersPersonaUsuario() {
		return intPersPersonaUsuario;
	}
	public void setIntPersPersonaUsuario(Integer intPersPersonaUsuario) {
		this.intPersPersonaUsuario = intPersPersonaUsuario;
	}
	public Integer getIntPersEmpresaEgresoAnula() {
		return intPersEmpresaEgresoAnula;
	}
	public void setIntPersEmpresaEgresoAnula(Integer intPersEmpresaEgresoAnula) {
		this.intPersEmpresaEgresoAnula = intPersEmpresaEgresoAnula;
	}
	public Integer getIntPersPersonaEgresoAnula() {
		return intPersPersonaEgresoAnula;
	}
	public void setIntPersPersonaEgresoAnula(Integer intPersPersonaEgresoAnula) {
		this.intPersPersonaEgresoAnula = intPersPersonaEgresoAnula;
	}
	public Integer getIntParaTipoApoderado() {
		return intParaTipoApoderado;
	}
	public void setIntParaTipoApoderado(Integer intParaTipoApoderado) {
		this.intParaTipoApoderado = intParaTipoApoderado;
	}
	public Integer getIntItemArchivoApoderado() {
		return intItemArchivoApoderado;
	}
	public void setIntItemArchivoApoderado(Integer intItemArchivoApoderado) {
		this.intItemArchivoApoderado = intItemArchivoApoderado;
	}
	public Integer getIntItemHistoricoApoderado() {
		return intItemHistoricoApoderado;
	}
	public void setIntItemHistoricoApoderado(Integer intItemHistoricoApoderado) {
		this.intItemHistoricoApoderado = intItemHistoricoApoderado;
	}
	public Integer getIntParaTipoGiro() {
		return intParaTipoGiro;
	}
	public void setIntParaTipoGiro(Integer intParaTipoGiro) {
		this.intParaTipoGiro = intParaTipoGiro;
	}
	public Integer getIntItemArchivoGiro() {
		return intItemArchivoGiro;
	}
	public void setIntItemArchivoGiro(Integer intItemArchivoGiro) {
		this.intItemArchivoGiro = intItemArchivoGiro;
	}
	public Integer getIntItemHistoricoGiro() {
		return intItemHistoricoGiro;
	}
	public void setIntItemHistoricoGiro(Integer intItemHistoricoGiro) {
		this.intItemHistoricoGiro = intItemHistoricoGiro;
	}
	public List<EgresoDetalle> getListaEgresoDetalle() {
		return listaEgresoDetalle;
	}
	public void setListaEgresoDetalle(List<EgresoDetalle> listaEgresoDetalle) {
		this.listaEgresoDetalle = listaEgresoDetalle;
	}	
//	public String getStrPersCuentaBancariaGirado() {
//		return strPersCuentaBancariaGirado;
//	}
//	public void setStrPersCuentaBancariaGirado(String strPersCuentaBancariaGirado) {
//		this.strPersCuentaBancariaGirado = strPersCuentaBancariaGirado;
//	}
	public LibroDiario getLibroDiario() {
		return libroDiario;
	}
	public void setLibroDiario(LibroDiario libroDiario) {
		this.libroDiario = libroDiario;
	}
	public String getStrNumeroEgreso() {
		return strNumeroEgreso;
	}
	public void setStrNumeroEgreso(String strNumeroEgreso) {
		this.strNumeroEgreso = strNumeroEgreso;
	}
	public Persona getPersonaApoderado() {
		return personaApoderado;
	}
	public void setPersonaApoderado(Persona personaApoderado) {
		this.personaApoderado = personaApoderado;
	}
	public ControlFondosFijos getControlFondosFijos() {
		return controlFondosFijos;
	}
	public void setControlFondosFijos(ControlFondosFijos controlFondosFijos) {
		this.controlFondosFijos = controlFondosFijos;
	}
	public Bancocuenta getBancoCuenta() {
		return bancoCuenta;
	}
	public void setBancoCuenta(Bancocuenta bancoCuenta) {
		this.bancoCuenta = bancoCuenta;
	}

	@Override
	public String toString() {
		return "Egreso [id=" + id + ", intParaTipoOperacion="
				+ intParaTipoOperacion + ", intParaFormaPago="
				+ intParaFormaPago + ", intParaDocumentoGeneral="
				+ intParaDocumentoGeneral + ", intItemPeriodoEgreso="
				+ intItemPeriodoEgreso + ", intItemEgreso=" + intItemEgreso
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdSubsucursal=" + intSudeIdSubsucursal
				+ ", intParaSubTipoOperacion=" + intParaSubTipoOperacion
				+ ", tsFechaProceso=" + tsFechaProceso + ", dtFechaEgreso="
				+ dtFechaEgreso + ", intParaTipoFondoFijo="
				+ intParaTipoFondoFijo + ", intItemPeriodoFondo="
				+ intItemPeriodoFondo + ", intItemFondoFijo="
				+ intItemFondoFijo + ", intItemBancoFondo=" + intItemBancoFondo
				+ ", intItemBancoCuenta=" + intItemBancoCuenta
				+ ", intItemBancoCuentaCheque=" + intItemBancoCuentaCheque
				+ ", intNumeroPlanilla=" + intNumeroPlanilla
				+ ", intNumeroCheque=" + intNumeroCheque
				+ ", intNumeroTransferencia=" + intNumeroTransferencia
				+ ", tsFechaPagoDiferido=" + tsFechaPagoDiferido
				+ ", intPersEmpresaGirado=" + intPersEmpresaGirado
				+ ", intPersPersonaGirado=" + intPersPersonaGirado
				+ ", intCuentaGirado=" + intCuentaGirado
				+ ", intPersCuentaBancariaGirado="
				+ intPersCuentaBancariaGirado + ", intPersEmpresaBeneficiario="
				+ intPersEmpresaBeneficiario + ", intPersPersonaBeneficiario="
				+ intPersPersonaBeneficiario
				+ ", intPersCuentaBancariaBeneficiario="
				+ intPersCuentaBancariaBeneficiario
				+ ", intPersPersonaApoderado=" + intPersPersonaApoderado
				+ ", intPersEmpresaApoderado=" + intPersEmpresaApoderado
				+ ", bdMontoTotal=" + bdMontoTotal + ", strObservacion="
				+ strObservacion + ", intParaEstado=" + intParaEstado
				+ ", intPersEmpresaLibro=" + intPersEmpresaLibro
				+ ", intContPeriodoLibro=" + intContPeriodoLibro
				+ ", intContCodigoLibro=" + intContCodigoLibro
				+ ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", intPersEmpresaEgresoAnula=" + intPersEmpresaEgresoAnula
				+ ", intPersPersonaEgresoAnula=" + intPersPersonaEgresoAnula
				+ ", intParaTipoApoderado=" + intParaTipoApoderado
				+ ", intItemArchivoApoderado=" + intItemArchivoApoderado
				+ ", intItemHistoricoApoderado=" + intItemHistoricoApoderado
				+ ", intParaTipoGiro=" + intParaTipoGiro
				+ ", intItemArchivoGiro=" + intItemArchivoGiro
				+ ", intItemHistoricoGiro=" + intItemHistoricoGiro + "]";
	}

	public Integer getIntErrorGeneracionEgreso() {
		return intErrorGeneracionEgreso;
	}
	public void setIntErrorGeneracionEgreso(Integer intErrorGeneracionEgreso) {
		this.intErrorGeneracionEgreso = intErrorGeneracionEgreso;
	}
	public String getStrMsgErrorGeneracionEgreso() {
		return strMsgErrorGeneracionEgreso;
	}
	public void setStrMsgErrorGeneracionEgreso(String strMsgErrorGeneracionEgreso) {
		this.strMsgErrorGeneracionEgreso = strMsgErrorGeneracionEgreso;
	}
	public Integer getIntPersEmpresaLibroExtorno() {
		return intPersEmpresaLibroExtorno;
	}
	public void setIntPersEmpresaLibroExtorno(Integer intPersEmpresaLibroExtorno) {
		this.intPersEmpresaLibroExtorno = intPersEmpresaLibroExtorno;
	}
	public Integer getIntContPeriodoLibroExtorno() {
		return intContPeriodoLibroExtorno;
	}
	public void setIntContPeriodoLibroExtorno(Integer intContPeriodoLibroExtorno) {
		this.intContPeriodoLibroExtorno = intContPeriodoLibroExtorno;
	}
	public Integer getIntContCodigoLibroExtorno() {
		return intContCodigoLibroExtorno;
	}
	public void setIntContCodigoLibroExtorno(Integer intContCodigoLibroExtorno) {
		this.intContCodigoLibroExtorno = intContCodigoLibroExtorno;
	}
	public Integer getIntEsGiroPorSedeCentral() {
		return intEsGiroPorSedeCentral;
	}
	public void setIntEsGiroPorSedeCentral(Integer intEsGiroPorSedeCentral) {
		this.intEsGiroPorSedeCentral = intEsGiroPorSedeCentral;
	}
	public Boolean getBlnEsGiroPorSedeCentral() {
		return blnEsGiroPorSedeCentral;
	}
	public void setBlnEsGiroPorSedeCentral(Boolean blnEsGiroPorSedeCentral) {
		this.blnEsGiroPorSedeCentral = blnEsGiroPorSedeCentral;
	}
	public ExpedienteCredito getExpedienteCredito() {
		return expedienteCredito;
	}
	public void setExpedienteCredito(ExpedienteCredito expedienteCredito) {
		this.expedienteCredito = expedienteCredito;
	}
	public String getStrNumeroLibro() {
		return strNumeroLibro;
	}
	public void setStrNumeroLibro(String strNumeroLibro) {
		this.strNumeroLibro = strNumeroLibro;
	}
	//Autor jchavez / Tarea: Creación / Fecha: 19.09.2014
	public Integer getIntPersCuentaBancariaGirado() {
		return intPersCuentaBancariaGirado;
	}
	public void setIntPersCuentaBancariaGirado(Integer intPersCuentaBancariaGirado) {
		this.intPersCuentaBancariaGirado = intPersCuentaBancariaGirado;
	}	
	//Fin jchavez - 19.09.2014
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	public EgresoDetalle getEgresoDetConciliacion() {
		return egresoDetConciliacion;
	}

	public void setEgresoDetConciliacion(EgresoDetalle egresoDetConciliacion) {
		this.egresoDetConciliacion = egresoDetConciliacion;
	}
	
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
}