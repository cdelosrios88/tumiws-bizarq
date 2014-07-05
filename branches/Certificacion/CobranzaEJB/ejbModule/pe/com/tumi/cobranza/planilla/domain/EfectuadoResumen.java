package pe.com.tumi.cobranza.planilla.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class EfectuadoResumen extends TumiDomain{
	private EfectuadoResumenId id;
	private Integer intParaDocumentoGeneralCod;
	private Integer intPeriodoPlanilla;
	private BigDecimal bdMontoTotal;
	private Integer intNumeroAfectados;	
 	private Integer intTiposocioCod;
 	private Integer intModalidadCod;
 	private Integer intNivel;
 	private Integer intCodigo;
 	private Integer intIdsucursalprocesaPk;
 	private Integer intIdsubsucursalprocesaPk;
 	private Integer intIdsucursaladministraPk;
 	private Integer intIdsubsucursaladministra;
 	private Integer intEstadoCod;
 	private Timestamp tsFecharegistro;
 	private Integer intPersonausuarioPk;
 	private Timestamp tsFechaeliminacion;
 	private Integer intPersonaeliminaPk;
 	private Integer intParaTipoEliminacionCod;
 	private String strObservacion;
 	private Integer	intPersEmpresaLibro;
 	private Integer intPeriodoLibro;
 	private Integer intCodigoLibro;
 	private Integer intEmpresaAn;
 	private Integer intItemEfectuadoResumenAn;
 	private Integer	intParaEstadoPago; 	
 	private Efectuado 	efectuado;
 	private	Persona		personaIngreso;
 	private List<CobroPlanillas>	listaCobroPlanillas;
 	private BigDecimal	bdMontoDisponibelIngresar;
 	private GestorCobranza	gestorCobranza;
 	
 	//atributos usados en ingreso de caja
 	private String	strObservacionIngreso;
 	private BigDecimal	bdMontIngresar;
 	private	Integer	intParaFormaPago;
 	private	String	strNumeroCheque;
 	
 	//Auxiliares
 	private Juridica juridicaUnidadEjecutora;
	private Juridica juridicaSucursal;
	private List<Efectuado> listaEfectuado;
 	
 	public EfectuadoResumen(){
 		id = new EfectuadoResumenId();
 		listaEfectuado = new ArrayList<Efectuado>();
 	}
 	
	public EfectuadoResumenId getId() {
		return id;
	}
	public void setId(EfectuadoResumenId id) {
		this.id = id;
	}
	
	public java.lang.Integer getIntParaDocumentoGeneralCod() {
		return intParaDocumentoGeneralCod;
	}
	public void setIntParaDocumentoGeneralCod(java.lang.Integer intParaDocumentoGeneralCod) {
		this.intParaDocumentoGeneralCod = intParaDocumentoGeneralCod;
	}
	public java.lang.Integer getIntPeriodoPlanilla() {
		return intPeriodoPlanilla;
	}
	public void setIntPeriodoPlanilla(java.lang.Integer intPeriodoPlanilla) {
		this.intPeriodoPlanilla = intPeriodoPlanilla;
	}
	public java.math.BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(java.math.BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public java.lang.Integer getIntNumeroAfectados() {
		return intNumeroAfectados;
	}
	public void setIntNumeroAfectados(java.lang.Integer intNumeroAfectados) {
		this.intNumeroAfectados = intNumeroAfectados;
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
	public java.lang.Integer getIntIdsucursaladministraPk() {
		return intIdsucursaladministraPk;
	}
	public void setIntIdsucursaladministraPk(java.lang.Integer intIdsucursaladministraPk) {
		this.intIdsucursaladministraPk = intIdsucursaladministraPk;
	}
	public java.lang.Integer getIntIdsubsucursaladministra() {
		return intIdsubsucursaladministra;
	}
	public void setIntIdsubsucursaladministra(java.lang.Integer intIdsubsucursaladministra) {
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
	public java.lang.Integer getIntPersonaeliminaPk() {
		return intPersonaeliminaPk;
	}
	public void setIntPersonaeliminaPk(java.lang.Integer intPersonaeliminaPk) {
		this.intPersonaeliminaPk = intPersonaeliminaPk;
	}
	public java.lang.Integer getIntParaTipoEliminacionCod() {
		return intParaTipoEliminacionCod;
	}
	public void setIntParaTipoEliminacionCod(
			java.lang.Integer intParaTipoEliminacionCod) {
		this.intParaTipoEliminacionCod = intParaTipoEliminacionCod;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntPeriodoLibro() {
		return intPeriodoLibro;
	}
	public void setIntPeriodoLibro(Integer intPeriodoLibro) {
		this.intPeriodoLibro = intPeriodoLibro;
	}
	public Integer getIntCodigoLibro() {
		return intCodigoLibro;
	}
	public void setIntCodigoLibro(Integer intCodigoLibro) {
		this.intCodigoLibro = intCodigoLibro;
	}
	public Efectuado getEfectuado() {
		return efectuado;
	}
	public void setEfectuado(Efectuado efectuado) {
		this.efectuado = efectuado;
	}
	public Integer getIntPersEmpresaLibro() {
		return intPersEmpresaLibro;
	}
	public void setIntPersEmpresaLibro(Integer intPersEmpresaLibro) {
		this.intPersEmpresaLibro = intPersEmpresaLibro;
	}
	public Integer getIntEmpresaAn() {
		return intEmpresaAn;
	}
	public void setIntEmpresaAn(Integer intEmpresaAn) {
		this.intEmpresaAn = intEmpresaAn;
	}
	public Integer getIntItemEfectuadoResumenAn() {
		return intItemEfectuadoResumenAn;
	}
	public void setIntItemEfectuadoResumenAn(Integer intItemEfectuadoResumenAn) {
		this.intItemEfectuadoResumenAn = intItemEfectuadoResumenAn;
	}
	public Integer getIntParaEstadoPago() {
		return intParaEstadoPago;
	}
	public void setIntParaEstadoPago(Integer intParaEstadoPago) {
		this.intParaEstadoPago = intParaEstadoPago;
	}
	public Persona getPersonaIngreso() {
		return personaIngreso;
	}
	public void setPersonaIngreso(Persona personaIngreso) {
		this.personaIngreso = personaIngreso;
	}
	public String getStrObservacionIngreso() {
		return strObservacionIngreso;
	}
	public void setStrObservacionIngreso(String strObservacionIngreso) {
		this.strObservacionIngreso = strObservacionIngreso;
	}
	public BigDecimal getBdMontIngresar() {
		return bdMontIngresar;
	}
	public void setBdMontIngresar(BigDecimal bdMontIngresar) {
		this.bdMontIngresar = bdMontIngresar;
	}
	public Integer getIntParaFormaPago() {
		return intParaFormaPago;
	}
	public void setIntParaFormaPago(Integer intParaFormaPago) {
		this.intParaFormaPago = intParaFormaPago;
	}
	public String getStrNumeroCheque() {
		return strNumeroCheque;
	}
	public void setStrNumeroCheque(String strNumeroCheque) {
		this.strNumeroCheque = strNumeroCheque;
	}
	public List<CobroPlanillas> getListaCobroPlanillas() {
		return listaCobroPlanillas;
	}
	public void setListaCobroPlanillas(List<CobroPlanillas> listaCobroPlanillas) {
		this.listaCobroPlanillas = listaCobroPlanillas;
	}
	public BigDecimal getBdMontoDisponibelIngresar() {
		return bdMontoDisponibelIngresar;
	}
	public void setBdMontoDisponibelIngresar(BigDecimal bdMontoDisponibelIngresar) {
		this.bdMontoDisponibelIngresar = bdMontoDisponibelIngresar;
	}
	public GestorCobranza getGestorCobranza() {
		return gestorCobranza;
	}
	public void setGestorCobranza(GestorCobranza gestorCobranza) {
		this.gestorCobranza = gestorCobranza;
	}
	
	public Juridica getJuridicaSucursal() {
		return juridicaSucursal;
	}
	public void setJuridicaSucursal(Juridica juridicaSucursal) {
		this.juridicaSucursal = juridicaSucursal;
	}
	public Juridica getJuridicaUnidadEjecutora() {
		return juridicaUnidadEjecutora;
	}
	public void setJuridicaUnidadEjecutora(Juridica juridicaUnidadEjecutora) {
		this.juridicaUnidadEjecutora = juridicaUnidadEjecutora;
	}
	
	public List<Efectuado> getListaEfectuado() {
		return listaEfectuado;
	}

	public void setListaEfectuado(List<Efectuado> listaEfectuado) {
		this.listaEfectuado = listaEfectuado;
	}
		

	@Override
	public String toString() {
		return "EfectuadoResumen [id=" + id + ", intParaDocumentoGeneralCod="
				+ intParaDocumentoGeneralCod + ", intPeriodoPlanilla="
				+ intPeriodoPlanilla + ", bdMontoTotal=" + bdMontoTotal
				+ ", intNumeroAfectados=" + intNumeroAfectados
				+ ", intTiposocioCod=" + intTiposocioCod + ", intModalidadCod="
				+ intModalidadCod + ", intNivel=" + intNivel + ", intCodigo="
				+ intCodigo + ", intIdsucursalprocesaPk="
				+ intIdsucursalprocesaPk + ", intIdsubsucursalprocesaPk="
				+ intIdsubsucursalprocesaPk + ", intIdsucursaladministraPk="
				+ intIdsucursaladministraPk + ", intIdsubsucursaladministra="
				+ intIdsubsucursaladministra + "]";
	}
}