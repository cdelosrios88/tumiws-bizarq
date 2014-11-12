package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatDetalle extends TumiDomain{
	//BD
	private DocumentoSunatDetalleId	id;
	private Integer 			intParaTipoCptoDocumentoSunat;	//PARA_TIPOCPTODOCSUNAT_N_COD
	private Integer 			intPersEmpresaOrdenCompra;		//PERS_EMPRESAORDEN_N_PK
	private Integer 			intItemOrdenCompra;				//TESO_ITEMORDENCOMPRA_N
	private Integer 			intItemOrdenCompraDetalle;		//TESO_ITEMORDENCOMPRADET_N
	private Integer 			intAfectoIGV;					//ORCD_AFECTOIGV_N
	private Integer 			intParaTipoMoneda;				//PARA_TIPOMONEDA_N_COD
	private BigDecimal 			bdMontoSinTipoCambio;			//ORCD_MONTOSINTIPOCAMBIO_N
	private BigDecimal 			bdMontoTotal;					//ORCD_MONTOTOTAL_N
	private String 				strDescripcion;					//ORCD_DESCRIPCION_V
	private Integer 			intSucuIdSucursal;				//SUCU_IDSUCURSAL_N
	private Integer 			intSudeIdSubsucursal;			//SUDE_IDSUBSUCURSAL_N
	private Integer 			intIdArea;						//AREA_IDAREA_N
	private Integer 			intParaEstado;					//PARA_ESTADO_N_COD
	private Integer 			intPersEmpresaCuenta;			//PERS_EMPRESACUENTA_N_PK
	private Integer 			intContPeriodoCuenta;			//CONT_PERIODOCUENTA_N
	private String				strContNumeroCuenta;			//CONT_NUMEROCUENTA_V
	//ADICIONALES
	private BigDecimal			bdMontoSinIGV;
	private BigDecimal			bdMontoIGV;	
	private OrdenCompraDetalle	ordenCompraDetalle;
	private BigDecimal			bdMontoGrabarEnLibro;
	private List<DocumentoSunatDetalle> listaDetalleSubTotal;
	
	private BigDecimal bdMontoAplicar;
	private BigDecimal bdMontoSaldoTemp;
	
	public DocumentoSunatDetalle(){
		id = new DocumentoSunatDetalleId();
		listaDetalleSubTotal = new ArrayList<DocumentoSunatDetalle>();
	}
	public DocumentoSunatDetalleId getId() {
		return id;
	}
	public void setId(DocumentoSunatDetalleId id) {
		this.id = id;
	}
	public Integer getIntParaTipoCptoDocumentoSunat() {
		return intParaTipoCptoDocumentoSunat;
	}
	public void setIntParaTipoCptoDocumentoSunat(
			Integer intParaTipoCptoDocumentoSunat) {
		this.intParaTipoCptoDocumentoSunat = intParaTipoCptoDocumentoSunat;
	}
	public Integer getIntPersEmpresaOrdenCompra() {
		return intPersEmpresaOrdenCompra;
	}
	public void setIntPersEmpresaOrdenCompra(Integer intPersEmpresaOrdenCompra) {
		this.intPersEmpresaOrdenCompra = intPersEmpresaOrdenCompra;
	}
	public Integer getIntItemOrdenCompra() {
		return intItemOrdenCompra;
	}
	public void setIntItemOrdenCompra(Integer intItemOrdenCompra) {
		this.intItemOrdenCompra = intItemOrdenCompra;
	}
	public Integer getIntItemOrdenCompraDetalle() {
		return intItemOrdenCompraDetalle;
	}
	public void setIntItemOrdenCompraDetalle(Integer intItemOrdenCompraDetalle) {
		this.intItemOrdenCompraDetalle = intItemOrdenCompraDetalle;
	}
	public Integer getIntAfectoIGV() {
		return intAfectoIGV;
	}
	public void setIntAfectoIGV(Integer intAfectoIGV) {
		this.intAfectoIGV = intAfectoIGV;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public BigDecimal getBdMontoSinTipoCambio() {
		return bdMontoSinTipoCambio;
	}
	public void setBdMontoSinTipoCambio(BigDecimal bdMontoSinTipoCambio) {
		this.bdMontoSinTipoCambio = bdMontoSinTipoCambio;
	}
	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}
	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
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
	public Integer getIntIdArea() {
		return intIdArea;
	}
	public void setIntIdArea(Integer intIdArea) {
		this.intIdArea = intIdArea;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntPersEmpresaCuenta() {
		return intPersEmpresaCuenta;
	}
	public void setIntPersEmpresaCuenta(Integer intPersEmpresaCuenta) {
		this.intPersEmpresaCuenta = intPersEmpresaCuenta;
	}
	public Integer getIntContPeriodoCuenta() {
		return intContPeriodoCuenta;
	}
	public void setIntContPeriodoCuenta(Integer intContPeriodoCuenta) {
		this.intContPeriodoCuenta = intContPeriodoCuenta;
	}
	public String getStrContNumeroCuenta() {
		return strContNumeroCuenta;
	}
	public void setStrContNumeroCuenta(String strContNumeroCuenta) {
		this.strContNumeroCuenta = strContNumeroCuenta;
	}
	public BigDecimal getBdMontoSinIGV() {
		return bdMontoSinIGV;
	}
	public void setBdMontoSinIGV(BigDecimal bdMontoSinIGV) {
		this.bdMontoSinIGV = bdMontoSinIGV;
	}
	public BigDecimal getBdMontoIGV() {
		return bdMontoIGV;
	}
	public void setBdMontoIGV(BigDecimal bdMontoIGV) {
		this.bdMontoIGV = bdMontoIGV;
	}
	public OrdenCompraDetalle getOrdenCompraDetalle() {
		return ordenCompraDetalle;
	}
	public void setOrdenCompraDetalle(OrdenCompraDetalle ordenCompraDetalle) {
		this.ordenCompraDetalle = ordenCompraDetalle;
	}
	public BigDecimal getBdMontoGrabarEnLibro() {
		return bdMontoGrabarEnLibro;
	}
	public void setBdMontoGrabarEnLibro(BigDecimal bdMontoGrabarEnLibro) {
		this.bdMontoGrabarEnLibro = bdMontoGrabarEnLibro;
	}
	public List<DocumentoSunatDetalle> getListaDetalleSubTotal() {
		return listaDetalleSubTotal;
	}
	public void setListaDetalleSubTotal(
			List<DocumentoSunatDetalle> listaDetalleSubTotal) {
		this.listaDetalleSubTotal = listaDetalleSubTotal;
	}
	public BigDecimal getBdMontoAplicar() {
		return bdMontoAplicar;
	}
	public void setBdMontoAplicar(BigDecimal bdMontoAplicar) {
		this.bdMontoAplicar = bdMontoAplicar;
	}
	public BigDecimal getBdMontoSaldoTemp() {
		return bdMontoSaldoTemp;
	}
	public void setBdMontoSaldoTemp(BigDecimal bdMontoSaldoTemp) {
		this.bdMontoSaldoTemp = bdMontoSaldoTemp;
	}
}