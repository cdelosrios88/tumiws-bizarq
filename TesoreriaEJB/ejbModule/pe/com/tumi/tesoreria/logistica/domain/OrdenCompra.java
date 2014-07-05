package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.tumi.common.util.DocumentoRequisicion;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Detraccion;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;

public class OrdenCompra extends TumiDomain{

	private OrdenCompraId	id;
	private Integer intParaTipoDocumentoGeneral;
	private Timestamp tsFechaRegistro;
	private Integer intPersEmpresaCuadroComparativo;
	private Integer intItemCuadroComparativo;
	private Integer intPersEmpresaRequisicion;
	private Integer intItemRequisicion;
	private Integer intPersEmpresaContrato;
	private Integer intItemContrato;
	private Integer intPersEmpresaInformeGerencia;
	private Integer intItemInformeGerencia;
	private Integer intPersEmpresaProveedor;
	private Integer intPersPersonaProveedor;
	private Integer intParaTipoProceso;
	private Integer intParaEstadoOrden;
	private Timestamp tsFechaAtencionLog;
	private Timestamp tsFechaAtencionReal;
	private Timestamp tsPlazoEntrega;
	private String 	strLugarEntrega;
	private BigDecimal bdGarantiaProductoServicio;
	private Integer intParaTipoFrecuenca;
	private Integer intParaTipoDetraccion;;
	private Integer intParaFormaPago;
	private Integer intPersCuentaBancariaTransaccion;
	private Integer intPersCuentaBancariaDetraccion;
	private String 	strObservacion;
	private Integer intPersEmpresaLibro;
	private Integer intPeriodoLibro;
	private Integer intCodigoLibro;
	private Integer intPersEmpresaLibroExtorno;
	private Integer intPeriodoLibroExtorno;
	private Integer intCodigoLibroExtorno;
	private Timestamp tsFechaProvisionGas;
	private Integer intPersEmpresaLibroGasto;
	private Integer intPeriodoLibroGasto;
	private Integer intCodigoLibroGasto;
	private Integer intParaEstado;
	//Agregado por cdelosrios, 15/10/2013
	private Integer intPersEmpresaUsuarioPk;
	private Integer intPersPersonaUsuarioPk;
	private Timestamp tsFechaAnula;
	private Integer intPersEmpresaAnulaPk;
	private Integer intPersPersonaAnulaPk;
	//Fin agregado por cdelosrios, 15/10/2013
	
	private List<OrdenCompraDetalle> 	listaOrdenCompraDetalle;
	private List<OrdenCompraDocumento>	listaOrdenCompraDocumento;	
	private DocumentoRequisicion 		documentoRequisicion;
	private CuentaBancaria	cuentaBancariaPago;
	private CuentaBancaria	cuentaBancariaImpuesto;	
	private Persona			personaProveedor;
	private Date			dtFiltroDesde;
	private Date			dtFiltroHasta;
	private BigDecimal		bdMontoTotalDetalle;
	private BigDecimal		bdMontoTotalDocumento;
	private Integer			intDiasPlazo;
	private Integer			intParaTipoMoneda;
	private Detraccion		detraccion;
	private Proveedor		proveedor;
	private List<CuentaBancaria>	listaCuentaBancariaUsar;
	private String			strEtiqueta;
	
	
	public OrdenCompra(){
		id = new OrdenCompraId();
		listaOrdenCompraDetalle = new ArrayList<OrdenCompraDetalle>();
		listaOrdenCompraDocumento = new ArrayList<OrdenCompraDocumento>();
	}
	
	public OrdenCompraId getId() {
		return id;
	}
	public void setId(OrdenCompraId id) {
		this.id = id;
	}
	public Integer getIntParaTipoDocumentoGeneral() {
		return intParaTipoDocumentoGeneral;
	}
	public void setIntParaTipoDocumentoGeneral(Integer intParaTipoDocumentoGeneral) {
		this.intParaTipoDocumentoGeneral = intParaTipoDocumentoGeneral;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntPersEmpresaCuadroComparativo() {
		return intPersEmpresaCuadroComparativo;
	}
	public void setIntPersEmpresaCuadroComparativo(
			Integer intPersEmpresaCuadroComparativo) {
		this.intPersEmpresaCuadroComparativo = intPersEmpresaCuadroComparativo;
	}
	public Integer getIntItemCuadroComparativo() {
		return intItemCuadroComparativo;
	}
	public void setIntItemCuadroComparativo(Integer intItemCuadroComparativo) {
		this.intItemCuadroComparativo = intItemCuadroComparativo;
	}
	public Integer getIntPersEmpresaRequisicion() {
		return intPersEmpresaRequisicion;
	}
	public void setIntPersEmpresaRequisicion(Integer intPersEmpresaRequisicion) {
		this.intPersEmpresaRequisicion = intPersEmpresaRequisicion;
	}
	public Integer getIntItemRequisicion() {
		return intItemRequisicion;
	}
	public void setIntItemRequisicion(Integer intItemRequisicion) {
		this.intItemRequisicion = intItemRequisicion;
	}
	public Integer getIntPersEmpresaContrato() {
		return intPersEmpresaContrato;
	}
	public void setIntPersEmpresaContrato(Integer intPersEmpresaContrato) {
		this.intPersEmpresaContrato = intPersEmpresaContrato;
	}
	public Integer getIntItemContrato() {
		return intItemContrato;
	}
	public void setIntItemContrato(Integer intItemContrato) {
		this.intItemContrato = intItemContrato;
	}
	public Integer getIntPersEmpresaInformeGerencia() {
		return intPersEmpresaInformeGerencia;
	}
	public void setIntPersEmpresaInformeGerencia(Integer intPersEmpresaInformeGerencia) {
		this.intPersEmpresaInformeGerencia = intPersEmpresaInformeGerencia;
	}
	public Integer getIntItemInformeGerencia() {
		return intItemInformeGerencia;
	}
	public void setIntItemInformeGerencia(Integer intItemInformeGerencia) {
		this.intItemInformeGerencia = intItemInformeGerencia;
	}
	public Integer getIntPersEmpresaProveedor() {
		return intPersEmpresaProveedor;
	}
	public void setIntPersEmpresaProveedor(Integer intPersEmpresaProveedor) {
		this.intPersEmpresaProveedor = intPersEmpresaProveedor;
	}
	public Integer getIntPersPersonaProveedor() {
		return intPersPersonaProveedor;
	}
	public void setIntPersPersonaProveedor(Integer intPersPersonaProveedor) {
		this.intPersPersonaProveedor = intPersPersonaProveedor;
	}
	public Integer getIntParaTipoProceso() {
		return intParaTipoProceso;
	}
	public void setIntParaTipoProceso(Integer intParaTipoProceso) {
		this.intParaTipoProceso = intParaTipoProceso;
	}
	public Integer getIntParaEstadoOrden() {
		return intParaEstadoOrden;
	}
	public void setIntParaEstadoOrden(Integer intParaEstadoOrden) {
		this.intParaEstadoOrden = intParaEstadoOrden;
	}
	public Timestamp getTsFechaAtencionLog() {
		return tsFechaAtencionLog;
	}
	public void setTsFechaAtencionLog(Timestamp tsFechaAtencionLog) {
		this.tsFechaAtencionLog = tsFechaAtencionLog;
	}
	public Timestamp getTsFechaAtencionReal() {
		return tsFechaAtencionReal;
	}
	public void setTsFechaAtencionReal(Timestamp tsFechaAtencionReal) {
		this.tsFechaAtencionReal = tsFechaAtencionReal;
	}
	public Timestamp getTsPlazoEntrega() {
		return tsPlazoEntrega;
	}
	public void setTsPlazoEntrega(Timestamp tsPlazoEntrega) {
		this.tsPlazoEntrega = tsPlazoEntrega;
	}
	public String getStrLugarEntrega() {
		return strLugarEntrega;
	}
	public void setStrLugarEntrega(String strLugarEntrega) {
		this.strLugarEntrega = strLugarEntrega;
	}
	public BigDecimal getBdGarantiaProductoServicio() {
		return bdGarantiaProductoServicio;
	}
	public void setBdGarantiaProductoServicio(BigDecimal bdGarantiaProductoServicio) {
		this.bdGarantiaProductoServicio = bdGarantiaProductoServicio;
	}
	public Integer getIntParaTipoFrecuenca() {
		return intParaTipoFrecuenca;
	}
	public void setIntParaTipoFrecuenca(Integer intParaTipoFrecuenca) {
		this.intParaTipoFrecuenca = intParaTipoFrecuenca;
	}
	public Integer getIntParaTipoDetraccion() {
		return intParaTipoDetraccion;
	}
	public void setIntParaTipoDetraccion(Integer intParaTipoDetraccion) {
		this.intParaTipoDetraccion = intParaTipoDetraccion;
	}
	public Integer getIntParaFormaPago() {
		return intParaFormaPago;
	}
	public void setIntParaFormaPago(Integer intParaFormaPago) {
		this.intParaFormaPago = intParaFormaPago;
	}
	public Integer getIntPersCuentaBancariaTransaccion() {
		return intPersCuentaBancariaTransaccion;
	}
	public void setIntPersCuentaBancariaTransaccion(Integer intPersCuentaBancariaTransaccion) {
		this.intPersCuentaBancariaTransaccion = intPersCuentaBancariaTransaccion;
	}
	public Integer getIntPersCuentaBancariaDetraccion() {
		return intPersCuentaBancariaDetraccion;
	}
	public void setIntPersCuentaBancariaDetraccion(Integer intPersCuentaBancariaDetraccion) {
		this.intPersCuentaBancariaDetraccion = intPersCuentaBancariaDetraccion;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntPersEmpresaLibro() {
		return intPersEmpresaLibro;
	}
	public void setIntPersEmpresaLibro(Integer intPersEmpresaLibro) {
		this.intPersEmpresaLibro = intPersEmpresaLibro;
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
	public Integer getIntPersEmpresaLibroExtorno() {
		return intPersEmpresaLibroExtorno;
	}
	public void setIntPersEmpresaLibroExtorno(Integer intPersEmpresaLibroExtorno) {
		this.intPersEmpresaLibroExtorno = intPersEmpresaLibroExtorno;
	}
	public Integer getIntPeriodoLibroExtorno() {
		return intPeriodoLibroExtorno;
	}
	public void setIntPeriodoLibroExtorno(Integer intPeriodoLibroExtorno) {
		this.intPeriodoLibroExtorno = intPeriodoLibroExtorno;
	}
	public Integer getIntCodigoLibroExtorno() {
		return intCodigoLibroExtorno;
	}
	public void setIntCodigoLibroExtorno(Integer intCodigoLibroExtorno) {
		this.intCodigoLibroExtorno = intCodigoLibroExtorno;
	}
	public Timestamp getTsFechaProvisionGas() {
		return tsFechaProvisionGas;
	}
	public void setTsFechaProvisionGas(Timestamp tsFechaProvisionGas) {
		this.tsFechaProvisionGas = tsFechaProvisionGas;
	}
	public Integer getIntPersEmpresaLibroGasto() {
		return intPersEmpresaLibroGasto;
	}
	public void setIntPersEmpresaLibroGasto(Integer intPersEmpresaLibroGasto) {
		this.intPersEmpresaLibroGasto = intPersEmpresaLibroGasto;
	}
	public Integer getIntPeriodoLibroGasto() {
		return intPeriodoLibroGasto;
	}
	public void setIntPeriodoLibroGasto(Integer intPeriodoLibroGasto) {
		this.intPeriodoLibroGasto = intPeriodoLibroGasto;
	}
	public Integer getIntCodigoLibroGasto() {
		return intCodigoLibroGasto;
	}
	public void setIntCodigoLibroGasto(Integer intCodigoLibroGasto) {
		this.intCodigoLibroGasto = intCodigoLibroGasto;
	}
	public List<OrdenCompraDetalle> getListaOrdenCompraDetalle() {
		return listaOrdenCompraDetalle;
	}
	public void setListaOrdenCompraDetalle(	List<OrdenCompraDetalle> listaOrdenCompraDetalle) {
		this.listaOrdenCompraDetalle = listaOrdenCompraDetalle;
	}
	public List<OrdenCompraDocumento> getListaOrdenCompraDocumento() {
		return listaOrdenCompraDocumento;
	}
	public void setListaOrdenCompraDocumento(List<OrdenCompraDocumento> listaOrdenCompraDocumento) {
		this.listaOrdenCompraDocumento = listaOrdenCompraDocumento;
	}
	public DocumentoRequisicion getDocumentoRequisicion() {
		return documentoRequisicion;
	}
	public void setDocumentoRequisicion(DocumentoRequisicion documentoRequisicion) {
		this.documentoRequisicion = documentoRequisicion;
	}
	public CuentaBancaria getCuentaBancariaPago() {
		return cuentaBancariaPago;
	}
	public void setCuentaBancariaPago(CuentaBancaria cuentaBancariaPago) {
		this.cuentaBancariaPago = cuentaBancariaPago;
	}
	public CuentaBancaria getCuentaBancariaImpuesto() {
		return cuentaBancariaImpuesto;
	}
	public void setCuentaBancariaImpuesto(CuentaBancaria cuentaBancariaImpuesto) {
		this.cuentaBancariaImpuesto = cuentaBancariaImpuesto;
	}
	public Persona getPersonaProveedor() {
		return personaProveedor;
	}
	public void setPersonaProveedor(Persona personaProveedor) {
		this.personaProveedor = personaProveedor;
	}
	public Date getDtFiltroDesde() {
		return dtFiltroDesde;
	}
	public void setDtFiltroDesde(Date dtFiltroDesde) {
		this.dtFiltroDesde = dtFiltroDesde;
	}
	public Date getDtFiltroHasta() {
		return dtFiltroHasta;
	}
	public void setDtFiltroHasta(Date dtFiltroHasta) {
		this.dtFiltroHasta = dtFiltroHasta;
	}
	public BigDecimal getBdMontoTotalDetalle() {
		return bdMontoTotalDetalle;
	}
	public void setBdMontoTotalDetalle(BigDecimal bdMontoTotalDetalle) {
		this.bdMontoTotalDetalle = bdMontoTotalDetalle;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	//Agregado por cdelosrios, 15/10/2013
	public Integer getIntPersEmpresaUsuarioPk() {
		return intPersEmpresaUsuarioPk;
	}
	public void setIntPersEmpresaUsuarioPk(Integer intPersEmpresaUsuarioPk) {
		this.intPersEmpresaUsuarioPk = intPersEmpresaUsuarioPk;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public Timestamp getTsFechaAnula() {
		return tsFechaAnula;
	}
	public void setTsFechaAnula(Timestamp tsFechaAnula) {
		this.tsFechaAnula = tsFechaAnula;
	}
	public Integer getIntPersEmpresaAnulaPk() {
		return intPersEmpresaAnulaPk;
	}
	public void setIntPersEmpresaAnulaPk(Integer intPersEmpresaAnulaPk) {
		this.intPersEmpresaAnulaPk = intPersEmpresaAnulaPk;
	}
	public Integer getIntPersPersonaAnulaPk() {
		return intPersPersonaAnulaPk;
	}
	public void setIntPersPersonaAnulaPk(Integer intPersPersonaAnulaPk) {
		this.intPersPersonaAnulaPk = intPersPersonaAnulaPk;
	}
	//Fin agregado por cdelosrios, 15/10/2013
	public Integer getIntDiasPlazo() {
		return intDiasPlazo;
	}
	public void setIntDiasPlazo(Integer intDiasPlazo) {
		this.intDiasPlazo = intDiasPlazo;
	}
	public Detraccion getDetraccion() {
		return detraccion;
	}
	public void setDetraccion(Detraccion detraccion) {
		this.detraccion = detraccion;
	}
	public List<CuentaBancaria> getListaCuentaBancariaUsar() {
		return listaCuentaBancariaUsar;
	}
	public void setListaCuentaBancariaUsar(List<CuentaBancaria> listaCuentaBancariaUsar) {
		this.listaCuentaBancariaUsar = listaCuentaBancariaUsar;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public BigDecimal getBdMontoTotalDocumento() {
		return bdMontoTotalDocumento;
	}
	public void setBdMontoTotalDocumento(BigDecimal bdMontoTotalDocumento) {
		this.bdMontoTotalDocumento = bdMontoTotalDocumento;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	public String getStrEtiqueta() {
		return strEtiqueta;
	}
	public void setStrEtiqueta(String strEtiqueta) {
		this.strEtiqueta = strEtiqueta;
	}

	@Override
	public String toString() {
		return "OrdenCompra [id=" + id + ", intParaTipoDocumentoGeneral="
				+ intParaTipoDocumentoGeneral + ", tsFechaRegistro="
				+ tsFechaRegistro + ", intPersEmpresaCuadroComparativo="
				+ intPersEmpresaCuadroComparativo
				+ ", intItemCuadroComparativo=" + intItemCuadroComparativo
				+ ", intPersEmpresaRequisicion=" + intPersEmpresaRequisicion
				+ ", intItemRequisicion=" + intItemRequisicion
				+ ", intPersEmpresaContrato=" + intPersEmpresaContrato
				+ ", intItemContrato=" + intItemContrato
				+ ", intPersEmpresaInformeGerencia="
				+ intPersEmpresaInformeGerencia + ", intItemInformeGerencia="
				+ intItemInformeGerencia + ", intPersEmpresaProveedor="
				+ intPersEmpresaProveedor + ", intPersPersonaProveedor="
				+ intPersPersonaProveedor + ", intParaTipoProceso="
				+ intParaTipoProceso + ", intParaEstadoOrden="
				+ intParaEstadoOrden + ", tsFechaAtencionLog="
				+ tsFechaAtencionLog + ", tsFechaAtencionReal="
				+ tsFechaAtencionReal + ", tsPlazoEntrega=" + tsPlazoEntrega
				+ ", strLugarEntrega=" + strLugarEntrega
				+ ", bdGarantiaProductoServicio=" + bdGarantiaProductoServicio
				+ ", intParaTipoFrecuenca=" + intParaTipoFrecuenca
				+ ", intParaTipoDetraccion=" + intParaTipoDetraccion
				+ ", intParaFormaPago=" + intParaFormaPago
				+ ", intPersCuentaBancariaTransaccion="
				+ intPersCuentaBancariaTransaccion
				+ ", intPersCuentaBancariaDetraccion="
				+ intPersCuentaBancariaDetraccion + ", strObservacion="
				+ strObservacion + ", intPersEmpresaLibro="
				+ intPersEmpresaLibro + ", intPeriodoLibro=" + intPeriodoLibro
				+ ", intCodigoLibro=" + intCodigoLibro
				+ ", intPersEmpresaLibroExtorno=" + intPersEmpresaLibroExtorno
				+ ", intPeriodoLibroExtorno=" + intPeriodoLibroExtorno
				+ ", intCodigoLibroExtorno=" + intCodigoLibroExtorno
				+ ", tsFechaProvisionGas=" + tsFechaProvisionGas
				+ ", intPersEmpresaLibroGasto=" + intPersEmpresaLibroGasto
				+ ", intPeriodoLibroGasto=" + intPeriodoLibroGasto
				+ ", intCodigoLibroGasto=" + intCodigoLibroGasto + "]";
	}
}