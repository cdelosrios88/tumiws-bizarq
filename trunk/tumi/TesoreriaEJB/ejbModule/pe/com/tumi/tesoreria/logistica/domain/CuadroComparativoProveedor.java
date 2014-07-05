package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;

public class CuadroComparativoProveedor extends TumiDomain{

	private CuadroComparativoProveedorId	id;
	private Integer intPersEmpresaProveedor;
	private Integer intPersPersonaProveedor;
	private Timestamp tsPlazoEntrega;
	private String strLugarEntrega;
	private String strGarantia;
	private String strCondicionPago;
	private String strDescuento;
	private String strObservacion;
	private Integer intParaTipoMoneda;
	private BigDecimal bdPrecioTotal;
	private Integer intParaTipo;
	private Integer intItemArchivo;
	private Integer intItemHistorico;
	private Integer intParaEstadoSeleccion;
	
	private Persona	persona;
	private List<CuadroComparativoProveedorDetalle> listaCuadroComparativoProveedorDetalle;
	private Archivo	archivoDocumento;
	private String strEtiqueta;
	
	public CuadroComparativoProveedor(){
		id = new CuadroComparativoProveedorId();
		listaCuadroComparativoProveedorDetalle = new ArrayList<CuadroComparativoProveedorDetalle>();
	}
	
	public CuadroComparativoProveedorId getId() {
		return id;
	}
	public void setId(CuadroComparativoProveedorId id) {
		this.id = id;
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
	public String getStrGarantia() {
		return strGarantia;
	}
	public void setStrGarantia(String strGarantia) {
		this.strGarantia = strGarantia;
	}
	public String getStrCondicionPago() {
		return strCondicionPago;
	}
	public void setStrCondicionPago(String strCondicionPago) {
		this.strCondicionPago = strCondicionPago;
	}
	public String getStrDescuento() {
		return strDescuento;
	}
	public void setStrDescuento(String strDescuento) {
		this.strDescuento = strDescuento;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntParaTipoMoneda() {
		return intParaTipoMoneda;
	}
	public void setIntParaTipoMoneda(Integer intParaTipoMoneda) {
		this.intParaTipoMoneda = intParaTipoMoneda;
	}
	public BigDecimal getBdPrecioTotal() {
		return bdPrecioTotal;
	}
	public void setBdPrecioTotal(BigDecimal bdPrecioTotal) {
		this.bdPrecioTotal = bdPrecioTotal;
	}
	public Integer getIntParaTipo() {
		return intParaTipo;
	}
	public void setIntParaTipo(Integer intParaTipo) {
		this.intParaTipo = intParaTipo;
	}
	public Integer getIntItemArchivo() {
		return intItemArchivo;
	}
	public void setIntItemArchivo(Integer intItemArchivo) {
		this.intItemArchivo = intItemArchivo;
	}
	public Integer getIntItemHistorico() {
		return intItemHistorico;
	}
	public void setIntItemHistorico(Integer intItemHistorico) {
		this.intItemHistorico = intItemHistorico;
	}
	public Integer getIntParaEstadoSeleccion() {
		return intParaEstadoSeleccion;
	}
	public void setIntParaEstadoSeleccion(Integer intParaEstadoSeleccion) {
		this.intParaEstadoSeleccion = intParaEstadoSeleccion;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public List<CuadroComparativoProveedorDetalle> getListaCuadroComparativoProveedorDetalle() {
		return listaCuadroComparativoProveedorDetalle;
	}
	public void setListaCuadroComparativoProveedorDetalle(List<CuadroComparativoProveedorDetalle> listaCuadroComparativoProveedorDetalle) {
		this.listaCuadroComparativoProveedorDetalle = listaCuadroComparativoProveedorDetalle;
	}
	public Archivo getArchivoDocumento() {
		return archivoDocumento;
	}
	public void setArchivoDocumento(Archivo archivoDocumento) {
		this.archivoDocumento = archivoDocumento;
	}	
	public String getStrEtiqueta() {
		return strEtiqueta;
	}
	public void setStrEtiqueta(String strEtiqueta) {
		this.strEtiqueta = strEtiqueta;
	}
	

	@Override
	public String toString() {
		return "CuadroComparativoProveedor [id=" + id
				+ ", intPersEmpresaProveedor=" + intPersEmpresaProveedor
				+ ", intPersPersonaProveedor=" + intPersPersonaProveedor
				+ ", tsPlazoEntrega=" + tsPlazoEntrega + ", strLugarEntrega="
				+ strLugarEntrega + ", strGarantia=" + strGarantia
				+ ", strCondicionPago=" + strCondicionPago + ", strDescuento="
				+ strDescuento + ", strObservacion=" + strObservacion
				+ ", intParaTipoMoneda=" + intParaTipoMoneda
				+ ", bdPrecioTotal=" + bdPrecioTotal + ", intParaTipo="
				+ intParaTipo + ", intItemArchivo=" + intItemArchivo
				+ ", intItemHistorico=" + intItemHistorico
				+ ", intParaEstadoSeleccion=" + intParaEstadoSeleccion + "]";
	}
}