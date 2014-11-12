package pe.com.tumi.tesoreria.ingreso.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;

public class Ingreso extends TumiDomain{

	private IngresoId	id;
	private Integer intParaTipoOperacion;
	private Integer intParaFormaPago;
	private Integer intParaDocumentoGeneral;
	private Integer intItemPeriodoIngreso;
	private Integer intItemIngreso;
	private Integer intSucuIdSucursal;
	private Integer intSudeIdSubsucursal;
	private Integer intParaTipoSuboperacion;
	private Timestamp tsFechaProceso;
	private Date 	dtFechaIngreso;
	private Integer intParaFondoFijo;
	private Integer intItemBancoFondo;
	private Integer intItemBancoCuenta;
	private String 	strNumeroCheque;
	private Integer intPersEmpresaGirado;
	private Integer intPersPersonaGirado;
	private Integer intCuentaGirado;
	private Integer intParaTipoMoneda;
	private BigDecimal bdTipoCambio;
	private BigDecimal bdMontoTotal;
	private String 	strObservacion;
	private Integer intParaEstado;
	private Integer intPersEmpresaLibro;
	private Integer intContPeriodoLibro;
	private Integer intContCodigoLibro;
	private Timestamp tsFechaRegistro;
	private Integer intPersEmpresaUsuario;
	private Integer intPersPersonaUsuario;
	private Integer intPersEmpresaIngresoAn;
	private Integer intItemIngresoGeneralAn;
	private Integer intParaTipoIngreso;
	private Integer intItemArchivoIngreso;
	private Integer intHistoricoIngreso;
	private Integer intParaTipoDeposito;
	private Integer intItemArchivoDeposito;
	private Integer intItemHistoricoDeposito;
	private Integer intPersEmpresa;
	private Integer intParaTipoFondoFijoCan;
	private Integer intItemPeriodoFondo;
	private Integer intIdSucursalCan;
	private Integer intItemFondoFijo;
	private String	strNumeroOperacion;
	private Integer	intParaEstadoDepositado;
	
	
	private List<IngresoDetalle> listaIngresoDetalle;
	private LibroDiario	libroDiario;
	private ReciboManual	reciboManual;
	private	String	strNumeroIngreso;
	private	String	strNumeroLibro;	
	private	Date	dtDechaDesde;
	private	Date	dtDechaHasta;
	private Persona	persona;
	private BigDecimal	bdMontoDepositar;
	private BigDecimal 	bdMontoDepositable;
	private List<DepositoIngreso> listaDepositoIngreso;
	private Bancofondo 	bancoFondo;
	private Archivo		archivoVoucher;
	
	//jchavez 08.07.2014
	private Integer intParaModalidadPago;
	private Integer intPeriodoSocio;
//	private String strMontoDepositar;
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	private IngresoDetalle ingresoDetConciliacion;
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
	
	public Ingreso(){
		id = new IngresoId();
		listaIngresoDetalle = new ArrayList<IngresoDetalle>();
	}	
	
	public IngresoId getId() {
		return id;
	}
	public void setId(IngresoId id) {
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
	public Integer getIntItemPeriodoIngreso() {
		return intItemPeriodoIngreso;
	}
	public void setIntItemPeriodoIngreso(Integer intItemPeriodoIngreso) {
		this.intItemPeriodoIngreso = intItemPeriodoIngreso;
	}
	public Integer getIntItemIngreso() {
		return intItemIngreso;
	}
	public void setIntItemIngreso(Integer intItemIngreso) {
		this.intItemIngreso = intItemIngreso;
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
	public Integer getIntParaTipoSuboperacion() {
		return intParaTipoSuboperacion;
	}
	public void setIntParaTipoSuboperacion(Integer intParaTipoSuboperacion) {
		this.intParaTipoSuboperacion = intParaTipoSuboperacion;
	}
	public Integer getIntParaFondoFijo() {
		return intParaFondoFijo;
	}
	public void setIntParaFondoFijo(Integer intParaFondoFijo) {
		this.intParaFondoFijo = intParaFondoFijo;
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
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
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
	public Integer getIntPersEmpresaIngresoAn() {
		return intPersEmpresaIngresoAn;
	}
	public void setIntPersEmpresaIngresoAn(Integer intPersEmpresaIngresoAn) {
		this.intPersEmpresaIngresoAn = intPersEmpresaIngresoAn;
	}
	public Integer getIntItemIngresoGeneralAn() {
		return intItemIngresoGeneralAn;
	}
	public void setIntItemIngresoGeneralAn(Integer intItemIngresoGeneralAn) {
		this.intItemIngresoGeneralAn = intItemIngresoGeneralAn;
	}
	public Integer getIntParaTipoIngreso() {
		return intParaTipoIngreso;
	}
	public void setIntParaTipoIngreso(Integer intParaTipoIngreso) {
		this.intParaTipoIngreso = intParaTipoIngreso;
	}
	public Integer getIntItemArchivoIngreso() {
		return intItemArchivoIngreso;
	}
	public void setIntItemArchivoIngreso(Integer intItemArchivoIngreso) {
		this.intItemArchivoIngreso = intItemArchivoIngreso;
	}
	public Integer getIntHistoricoIngreso() {
		return intHistoricoIngreso;
	}
	public void setIntHistoricoIngreso(Integer intHistoricoIngreso) {
		this.intHistoricoIngreso = intHistoricoIngreso;
	}
	public Integer getIntParaTipoDeposito() {
		return intParaTipoDeposito;
	}
	public void setIntParaTipoDeposito(Integer intParaTipoDeposito) {
		this.intParaTipoDeposito = intParaTipoDeposito;
	}
	public Integer getIntItemArchivoDeposito() {
		return intItemArchivoDeposito;
	}
	public void setIntItemArchivoDeposito(Integer intItemArchivoDeposito) {
		this.intItemArchivoDeposito = intItemArchivoDeposito;
	}
	public Integer getIntItemHistoricoDeposito() {
		return intItemHistoricoDeposito;
	}
	public void setIntItemHistoricoDeposito(Integer intItemHistoricoDeposito) {
		this.intItemHistoricoDeposito = intItemHistoricoDeposito;
	}
	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}
	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}
	public Integer getIntParaTipoFondoFijoCan() {
		return intParaTipoFondoFijoCan;
	}
	public void setIntParaTipoFondoFijoCan(Integer intParaTipoFondoFijoCan) {
		this.intParaTipoFondoFijoCan = intParaTipoFondoFijoCan;
	}
	public Integer getIntItemPeriodoFondo() {
		return intItemPeriodoFondo;
	}
	public void setIntItemPeriodoFondo(Integer intItemPeriodoFondo) {
		this.intItemPeriodoFondo = intItemPeriodoFondo;
	}
	public Integer getIntIdSucursalCan() {
		return intIdSucursalCan;
	}
	public void setIntIdSucursalCan(Integer intIdSucursalCan) {
		this.intIdSucursalCan = intIdSucursalCan;
	}
	public Integer getIntItemFondoFijo() {
		return intItemFondoFijo;
	}
	public void setIntItemFondoFijo(Integer intItemFondoFijo) {
		this.intItemFondoFijo = intItemFondoFijo;
	}
	public Timestamp getTsFechaProceso() {
		return tsFechaProceso;
	}
	public void setTsFechaProceso(Timestamp tsFechaProceso) {
		this.tsFechaProceso = tsFechaProceso;
	}
	public Date getDtFechaIngreso() {
		return dtFechaIngreso;
	}
	public void setDtFechaIngreso(Date dtFechaIngreso) {
		this.dtFechaIngreso = dtFechaIngreso;
	}
	public String getStrNumeroCheque() {
		return strNumeroCheque;
	}
	public void setStrNumeroCheque(String strNumeroCheque) {
		this.strNumeroCheque = strNumeroCheque;
	}
	public BigDecimal getBdTipoCambio() {
		return bdTipoCambio;
	}
	public void setBdTipoCambio(BigDecimal bdTipoCambio) {
		this.bdTipoCambio = bdTipoCambio;
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
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}	
	public List<IngresoDetalle> getListaIngresoDetalle() {
		return listaIngresoDetalle;
	}
	public void setListaIngresoDetalle(List<IngresoDetalle> listaIngresoDetalle) {
		this.listaIngresoDetalle = listaIngresoDetalle;
	}
	public LibroDiario getLibroDiario() {
		return libroDiario;
	}
	public void setLibroDiario(LibroDiario libroDiario) {
		this.libroDiario = libroDiario;
	}
	public ReciboManual getReciboManual() {
		return reciboManual;
	}
	public void setReciboManual(ReciboManual reciboManual) {
		this.reciboManual = reciboManual;
	}
	public String getStrNumeroIngreso() {
		return strNumeroIngreso;
	}
	public void setStrNumeroIngreso(String strNumeroIngreso) {
		this.strNumeroIngreso = strNumeroIngreso;
	}
	public Date getDtDechaDesde() {
		return dtDechaDesde;
	}
	public void setDtDechaDesde(Date dtDechaDesde) {
		this.dtDechaDesde = dtDechaDesde;
	}
	public Date getDtDechaHasta() {
		return dtDechaHasta;
	}
	public void setDtDechaHasta(Date dtDechaHasta) {
		this.dtDechaHasta = dtDechaHasta;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Integer getIntParaEstadoDepositado() {
		return intParaEstadoDepositado;
	}
	public void setIntParaEstadoDepositado(Integer intParaEstadoDepositado) {
		this.intParaEstadoDepositado = intParaEstadoDepositado;
	}
	public String getStrNumeroOperacion() {
		return strNumeroOperacion;
	}
	public void setStrNumeroOperacion(String strNumeroOperacion) {
		this.strNumeroOperacion = strNumeroOperacion;
	}
	public BigDecimal getBdMontoDepositar() {
		return bdMontoDepositar;
	}
	public void setBdMontoDepositar(BigDecimal bdMontoDepositar) {
		this.bdMontoDepositar = bdMontoDepositar;
	}
	public BigDecimal getBdMontoDepositable() {
		return bdMontoDepositable;
	}
	public void setBdMontoDepositable(BigDecimal bdMontoDepositable) {
		this.bdMontoDepositable = bdMontoDepositable;
	}
	public List<DepositoIngreso> getListaDepositoIngreso() {
		return listaDepositoIngreso;
	}
	public void setListaDepositoIngreso(List<DepositoIngreso> listaDepositoIngreso) {
		this.listaDepositoIngreso = listaDepositoIngreso;
	}
	public Bancofondo getBancoFondo() {
		return bancoFondo;
	}
	public void setBancoFondo(Bancofondo bancoFondo) {
		this.bancoFondo = bancoFondo;
	}
	public Archivo getArchivoVoucher() {
		return archivoVoucher;
	}
	public void setArchivoVoucher(Archivo archivoVoucher) {
		this.archivoVoucher = archivoVoucher;
	}
	@Override
	public String toString() {
		return "Ingreso [id=" + id + ", intParaTipoOperacion="
				+ intParaTipoOperacion + ", intParaFormaPago="
				+ intParaFormaPago + ", intParaDocumentoGeneral="
				+ intParaDocumentoGeneral + ", intItemPeriodoIngreso="
				+ intItemPeriodoIngreso + ", intItemIngreso=" + intItemIngreso
				+ ", intSucuIdSucursal=" + intSucuIdSucursal
				+ ", intSudeIdSubsucursal=" + intSudeIdSubsucursal
				+ ", intParaTipoSuboperacion=" + intParaTipoSuboperacion
				+ ", tsFechaProceso=" + tsFechaProceso + ", dtFechaIngreso="
				+ dtFechaIngreso + ", intParaFondoFijo=" + intParaFondoFijo
				+ ", intItemBancoFondo=" + intItemBancoFondo
				+ ", intItemBancoCuenta=" + intItemBancoCuenta
				+ ", strNumeroCheque=" + strNumeroCheque
				+ ", intPersEmpresaGirado=" + intPersEmpresaGirado
				+ ", intPersPersonaGirado=" + intPersPersonaGirado
				+ ", intCuentaGirado=" + intCuentaGirado
				+ ", intParaTipoMoneda=" + intParaTipoMoneda
				+ ", bdTipoCambio=" + bdTipoCambio + ", bdMontoTotal="
				+ bdMontoTotal + ", strObservacion=" + strObservacion
				+ ", intParaEstado=" + intParaEstado + ", intPersEmpresaLibro="
				+ intPersEmpresaLibro + ", intContPeriodoLibro="
				+ intContPeriodoLibro + ", intContCodigoLibro="
				+ intContCodigoLibro + ", tsFechaRegistro=" + tsFechaRegistro
				+ ", intPersEmpresaUsuario=" + intPersEmpresaUsuario
				+ ", intPersPersonaUsuario=" + intPersPersonaUsuario
				+ ", intPersEmpresaIngresoAn=" + intPersEmpresaIngresoAn
				+ ", intItemIngresoGeneralAn=" + intItemIngresoGeneralAn
				+ ", intParaTipoIngreso=" + intParaTipoIngreso
				+ ", intItemArchivoIngreso=" + intItemArchivoIngreso
				+ ", intHistoricoIngreso=" + intHistoricoIngreso
				+ ", intParaTipoDeposito=" + intParaTipoDeposito
				+ ", intItemArchivoDeposito=" + intItemArchivoDeposito
				+ ", intItemHistoricoDeposito=" + intItemHistoricoDeposito
				+ ", intPersEmpresa=" + intPersEmpresa
				+ ", intParaTipoFondoFijoCan=" + intParaTipoFondoFijoCan
				+ ", intItemPeriodoFondo=" + intItemPeriodoFondo
				+ ", intIdSucursalCan=" + intIdSucursalCan + ", intItemFondoFijo="
				+ intItemFondoFijo + "]";
	}
	public Integer getIntParaModalidadPago() {
		return intParaModalidadPago;
	}
	public void setIntParaModalidadPago(Integer intParaModalidadPago) {
		this.intParaModalidadPago = intParaModalidadPago;
	}
	public Integer getIntPeriodoSocio() {
		return intPeriodoSocio;
	}
	public void setIntPeriodoSocio(Integer intPeriodoSocio) {
		this.intPeriodoSocio = intPeriodoSocio;
	}
	public String getStrNumeroLibro() {
		return strNumeroLibro;
	}
	public void setStrNumeroLibro(String strNumeroLibro) {
		this.strNumeroLibro = strNumeroLibro;
	}
//	public String getStrMontoDepositar() {
//		return strMontoDepositar;
//	}
//	public void setStrMontoDepositar(String strMontoDepositar) {
//		this.strMontoDepositar = strMontoDepositar;
//	}
	
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	public IngresoDetalle getIngresoDetConciliacion() {
		return ingresoDetConciliacion;
	}

	public void setIngresoDetConciliacion(IngresoDetalle ingresoDetConciliacion) {
		this.ingresoDetConciliacion = ingresoDetConciliacion;
	}
	
	/* Fin: REQ14-006 Bizarq - 26/10/2014 */
}