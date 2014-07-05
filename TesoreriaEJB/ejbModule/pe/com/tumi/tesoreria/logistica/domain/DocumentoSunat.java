package pe.com.tumi.tesoreria.logistica.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.persona.core.domain.Persona;

public class DocumentoSunat extends TumiDomain{

	private DocumentoSunatId id;
	private Integer intPersEmpresaOrden;
	private Integer intItemOrdenCompra;
	private Integer intPersEmpresaRequisicion;
	private Integer intItemRequisicion;
	private Timestamp	tsFechaProvision;
	private Integer intParaDocumentoGeneral;
	private Integer intParaTipoComprobante;
	private String 	strSerieDocumento;
	private String 	strNumeroDocumento;
	private Date 	dtFechaEmision;
	private Date 	dtFechaVencimiento;
	private Date 	dtFechaPago;
	private Integer intIndicadorInafecto;
	private Integer intIndicadorIGV;
	private Integer intIndicadorPercepcion;
	private Integer intIndicadorLetras;
	private String	strGlosa;
	private Integer intParaEstado;
	private Integer intParaEstadoPago;
	private Integer intPersEmpresaDocSunatEnlazado;
	private Integer intItemDocumentoSunatEnlazado;
	private Integer intPersEmpresaUsuario;
	private Integer intPersPersonaUsuario;
	private Timestamp tsFechaAnula;
	private Integer intPersEmpresaAnula;
	private Integer intPersPersonaAnula;
	private Integer intPersEmpresaEgreso;
	private Integer intItemEgresoGeneral;
	private Integer intEmpresaLibro;
	private Integer intPeriodoLibro;
	private Integer intCodigoLibro;
	
	//Agregado por cdelosrios, 01/11/2013
	private Integer 	intParaTipo;
	private Integer 	intItemArchivo;
	private Integer 	intItemHistorico;
	private Archivo		archivoDocumento;
	//Fin agregado por cdelosrios, 01/11/2013
	
	//Agregado por cdelosrios, 12/11/2013
	private Integer intSucuIdSucursal;
	private Integer intSubIdSubsucursal;
	//Fin agregado por cdelosrios, 12/11/2013
	
	//Agregado por cdelosrios, 04/01/2013
	private Timestamp tsFechaRegistro;
	//Fin agregado por cdelosrios, 04/01/2013
	
	private List<DocumentoSunatDetalle>	listaDocumentoSunatDetalle;
	private List<DocumentoSunatEgreso>	listaDocumentoSunatEgreso;
	private List<AdelantoSunat>			listaAdelantoSunat;
	private List<DocumentoSunat>		listaDocumentoSunatLetra;
	
	private DocumentoRequisicion	documentoRequisicion;
	private Persona					proveedor;
	private OrdenCompra				ordenCompra;
	private Integer					intParaMoneda;
	private Persona					personaGirar;
	
	private DocumentoSunatDetalle	detalleSubTotal;
	private DocumentoSunatDetalle	detalleDescuento;
	private DocumentoSunatDetalle	detalleValorVenta;
	private DocumentoSunatDetalle	detalleIGV;
	private DocumentoSunatDetalle	detalleOtros;
	private DocumentoSunatDetalle	detalleTotal;
	private DocumentoSunatDetalle	detallePercepcion;
	private DocumentoSunatDetalle	detalleDetraccion;
	private DocumentoSunatDetalle	detalleRetencion;
	private DocumentoSunatDetalle	detalleTotalGeneral;
	private DocumentoSunatDetalle	detalleLetra;
	
	private boolean	seleccionaInafecto;
	private boolean	seleccionaIGV;
	private boolean	seleccionaPercepcion;
	private boolean	seleccionaLetras;
	
	private Date	dtFiltroEmisionDesde;
	private Date	dtFiltroEmisionHasta;
	private Date	dtFiltroVencimientoDesde;
	private Date	dtFiltroVencimientoHasta;
	private Date	dtFiltroProgramacionDesde;
	private Date	dtFiltroProgramacionHasta;
	private Integer	intTipoFiltroFecha;
	private Date	dtFechaDeGiro;
	
	public DocumentoSunat(){
		id = new DocumentoSunatId();
		listaDocumentoSunatDetalle = new ArrayList<DocumentoSunatDetalle>();
		listaDocumentoSunatEgreso = new ArrayList<DocumentoSunatEgreso>();
		listaAdelantoSunat = new ArrayList<AdelantoSunat>();
	}
	
	public DocumentoSunat(DocumentoSunat otroDocumentoSunat){
		this.id = otroDocumentoSunat.getId();
		this.dtFechaEmision = otroDocumentoSunat.getDtFechaEmision();
		this.strGlosa = otroDocumentoSunat.getStrGlosa();
		this.intParaTipoComprobante = otroDocumentoSunat.getIntParaTipoComprobante();
		this.listaDocumentoSunatDetalle = otroDocumentoSunat.getListaDocumentoSunatDetalle();
		this.detalleSubTotal = otroDocumentoSunat.getDetalleSubTotal();
		this.detalleDescuento = otroDocumentoSunat.getDetalleDescuento();
		this.detalleValorVenta = otroDocumentoSunat.getDetalleValorVenta();
		this.detalleIGV = otroDocumentoSunat.getDetalleIGV();
		this.detalleOtros = otroDocumentoSunat.getDetalleOtros();
		this.detalleTotal = otroDocumentoSunat.getDetalleTotal();
		this.detallePercepcion = otroDocumentoSunat.getDetallePercepcion();
		this.detalleDetraccion = otroDocumentoSunat.getDetalleDetraccion();
		this.detalleRetencion = otroDocumentoSunat.getDetalleRetencion();
		this.detalleTotalGeneral = otroDocumentoSunat.getDetalleTotalGeneral();
		this.detalleLetra = otroDocumentoSunat.getDetalleLetra();
		
		this.seleccionaInafecto = otroDocumentoSunat.isSeleccionaInafecto();
		this.seleccionaPercepcion = otroDocumentoSunat.isSeleccionaPercepcion();
		this.ordenCompra = otroDocumentoSunat.getOrdenCompra();
	}
	
	public DocumentoSunatId getId() {
		return id;
	}
	public void setId(DocumentoSunatId id) {
		this.id = id;
	}
	public Integer getIntPersEmpresaOrden() {
		return intPersEmpresaOrden;
	}
	public void setIntPersEmpresaOrden(Integer intPersEmpresaOrden) {
		this.intPersEmpresaOrden = intPersEmpresaOrden;
	}
	public Integer getIntItemOrdenCompra() {
		return intItemOrdenCompra;
	}
	public void setIntItemOrdenCompra(Integer intItemOrdenCompra) {
		this.intItemOrdenCompra = intItemOrdenCompra;
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
	public Timestamp getTsFechaProvision() {
		return tsFechaProvision;
	}
	public void setTsFechaProvision(Timestamp tsFechaProvision) {
		this.tsFechaProvision = tsFechaProvision;
	}
	public Integer getIntParaDocumentoGeneral() {
		return intParaDocumentoGeneral;
	}
	public void setIntParaDocumentoGeneral(Integer intParaDocumentoGeneral) {
		this.intParaDocumentoGeneral = intParaDocumentoGeneral;
	}
	public Integer getIntParaTipoComprobante() {
		return intParaTipoComprobante;
	}
	public void setIntParaTipoComprobante(Integer intParaTipoComprobante) {
		this.intParaTipoComprobante = intParaTipoComprobante;
	}
	public String getStrSerieDocumento() {
		return strSerieDocumento;
	}
	public void setStrSerieDocumento(String strSerieDocumento) {
		this.strSerieDocumento = strSerieDocumento;
	}
	public String getStrNumeroDocumento() {
		return strNumeroDocumento;
	}
	public void setStrNumeroDocumento(String strNumeroDocumento) {
		this.strNumeroDocumento = strNumeroDocumento;
	}
	public Date getDtFechaEmision() {
		return dtFechaEmision;
	}
	public void setDtFechaEmision(Date dtFechaEmision) {
		this.dtFechaEmision = dtFechaEmision;
	}
	public Date getDtFechaVencimiento() {
		return dtFechaVencimiento;
	}
	public void setDtFechaVencimiento(Date dtFechaVencimiento) {
		this.dtFechaVencimiento = dtFechaVencimiento;
	}
	public Date getDtFechaPago() {
		return dtFechaPago;
	}
	public void setDtFechaPago(Date dtFechaPago) {
		this.dtFechaPago = dtFechaPago;
	}
	public Integer getIntIndicadorInafecto() {
		return intIndicadorInafecto;
	}
	public void setIntIndicadorInafecto(Integer intIndicadorInafecto) {
		this.intIndicadorInafecto = intIndicadorInafecto;
	}
	public Integer getIntIndicadorIGV() {
		return intIndicadorIGV;
	}
	public void setIntIndicadorIGV(Integer intIndicadorIGV) {
		this.intIndicadorIGV = intIndicadorIGV;
	}
	public Integer getIntIndicadorPercepcion() {
		return intIndicadorPercepcion;
	}
	public void setIntIndicadorPercepcion(Integer intIndicadorPercepcion) {
		this.intIndicadorPercepcion = intIndicadorPercepcion;
	}
	public Integer getIntIndicadorLetras() {
		return intIndicadorLetras;
	}
	public void setIntIndicadorLetras(Integer intIndicadorLetras) {
		this.intIndicadorLetras = intIndicadorLetras;
	}
	public String getStrGlosa() {
		return strGlosa;
	}
	public void setStrGlosa(String strGlosa) {
		this.strGlosa = strGlosa;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntParaEstadoPago() {
		return intParaEstadoPago;
	}
	public void setIntParaEstadoPago(Integer intParaEstadoPago) {
		this.intParaEstadoPago = intParaEstadoPago;
	}
	public Integer getIntPersEmpresaDocSunatEnlazado() {
		return intPersEmpresaDocSunatEnlazado;
	}
	public void setIntPersEmpresaDocSunatEnlazado(Integer intPersEmpresaDocSunatEnlazado) {
		this.intPersEmpresaDocSunatEnlazado = intPersEmpresaDocSunatEnlazado;
	}
	public Integer getIntItemDocumentoSunatEnlazado() {
		return intItemDocumentoSunatEnlazado;
	}
	public void setIntItemDocumentoSunatEnlazado(Integer intItemDocumentoSunatEnlazado) {
		this.intItemDocumentoSunatEnlazado = intItemDocumentoSunatEnlazado;
	}
	public Integer getIntPersEmpresaUsuario() {
		return intPersEmpresaUsuario;
	}
	public void setIntPersEmpresaUsuario(Integer intPersEmpresaUsuario) {
		this.intPersEmpresaUsuario = intPersEmpresaUsuario;
	}
	public Timestamp getTsFechaAnula() {
		return tsFechaAnula;
	}
	public void setTsFechaAnula(Timestamp tsFechaAnula) {
		this.tsFechaAnula = tsFechaAnula;
	}
	public Integer getIntPersEmpresaAnula() {
		return intPersEmpresaAnula;
	}
	public void setIntPersEmpresaAnula(Integer intPersEmpresaAnula) {
		this.intPersEmpresaAnula = intPersEmpresaAnula;
	}
	public Integer getIntPersPersonaAnula() {
		return intPersPersonaAnula;
	}
	public void setIntPersPersonaAnula(Integer intPersPersonaAnula) {
		this.intPersPersonaAnula = intPersPersonaAnula;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public Integer getIntEmpresaLibro() {
		return intEmpresaLibro;
	}
	public void setIntEmpresaLibro(Integer intEmpresaLibro) {
		this.intEmpresaLibro = intEmpresaLibro;
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
	//Agregado por cdelosrios, 01/11/2013
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
	public Archivo getArchivoDocumento() {
		return archivoDocumento;
	}
	public void setArchivoDocumento(Archivo archivoDocumento) {
		this.archivoDocumento = archivoDocumento;
	}
	//Fin agregado por cdelosrios, 01/11/2013
	//Agregado por cdelosrios, 12/11/2013
	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}
	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}
	public Integer getIntSubIdSubsucursal() {
		return intSubIdSubsucursal;
	}
	public void setIntSubIdSubsucursal(Integer intSubIdSubsucursal) {
		this.intSubIdSubsucursal = intSubIdSubsucursal;
	}
	//Fin agregado por cdelosrios, 12/11/2013
	public List<DocumentoSunatEgreso> getListaDocumentoSunatEgreso() {
		return listaDocumentoSunatEgreso;
	}
	public void setListaDocumentoSunatEgreso(List<DocumentoSunatEgreso> listaDocumentoSunatEgreso) {
		this.listaDocumentoSunatEgreso = listaDocumentoSunatEgreso;
	}
	public Integer getIntPersPersonaUsuario() {
		return intPersPersonaUsuario;
	}
	public void setIntPersPersonaUsuario(Integer intPersPersonaUsuario) {
		this.intPersPersonaUsuario = intPersPersonaUsuario;
	}
	public boolean isSeleccionaInafecto() {
		return seleccionaInafecto;
	}
	public void setSeleccionaInafecto(boolean seleccionaInafecto) {
		this.seleccionaInafecto = seleccionaInafecto;
	}
	public boolean isSeleccionaIGV() {
		return seleccionaIGV;
	}
	public void setSeleccionaIGV(boolean seleccionaIGV) {
		this.seleccionaIGV = seleccionaIGV;
	}
	public boolean isSeleccionaPercepcion() {
		return seleccionaPercepcion;
	}
	public void setSeleccionaPercepcion(boolean seleccionaPercepcion) {
		this.seleccionaPercepcion = seleccionaPercepcion;
	}
	public boolean isSeleccionaLetras() {
		return seleccionaLetras;
	}
	public void setSeleccionaLetras(boolean seleccionaLetras) {
		this.seleccionaLetras = seleccionaLetras;
	}
	public DocumentoRequisicion getDocumentoRequisicion() {
		return documentoRequisicion;
	}
	public void setDocumentoRequisicion(DocumentoRequisicion documentoRequisicion) {
		this.documentoRequisicion = documentoRequisicion;
	}
	public Persona getProveedor() {
		return proveedor;
	}
	public void setProveedor(Persona proveedor) {
		this.proveedor = proveedor;
	}
	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}
	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	public List<DocumentoSunatDetalle> getListaDocumentoSunatDetalle() {
		return listaDocumentoSunatDetalle;
	}
	public void setListaDocumentoSunatDetalle(List<DocumentoSunatDetalle> listaDocumentoSunatDetalle) {
		this.listaDocumentoSunatDetalle = listaDocumentoSunatDetalle;
	}
	public DocumentoSunatDetalle getDetalleDescuento() {
		return detalleDescuento;
	}
	public void setDetalleDescuento(DocumentoSunatDetalle detalleDescuento) {
		this.detalleDescuento = detalleDescuento;
	}
	public DocumentoSunatDetalle getDetalleValorVenta() {
		return detalleValorVenta;
	}
	public void setDetalleValorVenta(DocumentoSunatDetalle detalleValorVenta) {
		this.detalleValorVenta = detalleValorVenta;
	}
	public DocumentoSunatDetalle getDetalleIGV() {
		return detalleIGV;
	}
	public void setDetalleIGV(DocumentoSunatDetalle detalleIGV) {
		this.detalleIGV = detalleIGV;
	}
	public DocumentoSunatDetalle getDetalleOtros() {
		return detalleOtros;
	}
	public void setDetalleOtros(DocumentoSunatDetalle detalleOtros) {
		this.detalleOtros = detalleOtros;
	}
	public DocumentoSunatDetalle getDetalleTotal() {
		return detalleTotal;
	}
	public void setDetalleTotal(DocumentoSunatDetalle detalleTotal) {
		this.detalleTotal = detalleTotal;
	}
	public DocumentoSunatDetalle getDetallePercepcion() {
		return detallePercepcion;
	}
	public void setDetallePercepcion(DocumentoSunatDetalle detallePercepcion) {
		this.detallePercepcion = detallePercepcion;
	}
	public DocumentoSunatDetalle getDetalleDetraccion() {
		return detalleDetraccion;
	}
	public void setDetalleDetraccion(DocumentoSunatDetalle detalleDetraccion) {
		this.detalleDetraccion = detalleDetraccion;
	}
	public DocumentoSunatDetalle getDetalleRetencion() {
		return detalleRetencion;
	}
	public void setDetalleRetencion(DocumentoSunatDetalle detalleRetencion) {
		this.detalleRetencion = detalleRetencion;
	}
	public DocumentoSunatDetalle getDetalleTotalGeneral() {
		return detalleTotalGeneral;
	}
	public void setDetalleTotalGeneral(DocumentoSunatDetalle detalleTotalGeneral) {
		this.detalleTotalGeneral = detalleTotalGeneral;
	}
	public Integer getIntParaMoneda() {
		return intParaMoneda;
	}
	public void setIntParaMoneda(Integer intParaMoneda) {
		this.intParaMoneda = intParaMoneda;
	}
	public DocumentoSunatDetalle getDetalleSubTotal() {
		return detalleSubTotal;
	}
	public void setDetalleSubTotal(DocumentoSunatDetalle detalleSubTotal) {
		this.detalleSubTotal = detalleSubTotal;
	}
	public Date getDtFiltroEmisionDesde() {
		return dtFiltroEmisionDesde;
	}
	public void setDtFiltroEmisionDesde(Date dtFiltroEmisionDesde) {
		this.dtFiltroEmisionDesde = dtFiltroEmisionDesde;
	}
	public Date getDtFiltroEmisionHasta() {
		return dtFiltroEmisionHasta;
	}
	public void setDtFiltroEmisionHasta(Date dtFiltroEmisionHasta) {
		this.dtFiltroEmisionHasta = dtFiltroEmisionHasta;
	}
	public Date getDtFiltroVencimientoDesde() {
		return dtFiltroVencimientoDesde;
	}
	public void setDtFiltroVencimientoDesde(Date dtFiltroVencimientoDesde) {
		this.dtFiltroVencimientoDesde = dtFiltroVencimientoDesde;
	}
	public Date getDtFiltroVencimientoHasta() {
		return dtFiltroVencimientoHasta;
	}
	public void setDtFiltroVencimientoHasta(Date dtFiltroVencimientoHasta) {
		this.dtFiltroVencimientoHasta = dtFiltroVencimientoHasta;
	}
	public Date getDtFiltroProgramacionDesde() {
		return dtFiltroProgramacionDesde;
	}
	public void setDtFiltroProgramacionDesde(Date dtFiltroProgramacionDesde) {
		this.dtFiltroProgramacionDesde = dtFiltroProgramacionDesde;
	}
	public Date getDtFiltroProgramacionHasta() {
		return dtFiltroProgramacionHasta;
	}
	public void setDtFiltroProgramacionHasta(Date dtFiltroProgramacionHasta) {
		this.dtFiltroProgramacionHasta = dtFiltroProgramacionHasta;
	}	
	public Integer getIntTipoFiltroFecha() {
		return intTipoFiltroFecha;
	}
	public void setIntTipoFiltroFecha(Integer intTipoFiltroFecha) {
		this.intTipoFiltroFecha = intTipoFiltroFecha;
	}
	public Persona getPersonaGirar() {
		return personaGirar;
	}
	public void setPersonaGirar(Persona personaGirar) {
		this.personaGirar = personaGirar;
	}
	public List<AdelantoSunat> getListaAdelantoSunat() {
		return listaAdelantoSunat;
	}
	public void setListaAdelantoSunat(List<AdelantoSunat> listaAdelantoSunat) {
		this.listaAdelantoSunat = listaAdelantoSunat;
	}
	public List<DocumentoSunat> getListaDocumentoSunatLetra() {
		return listaDocumentoSunatLetra;
	}
	public void setListaDocumentoSunatLetra(List<DocumentoSunat> listaDocumentoSunatLetra) {
		this.listaDocumentoSunatLetra = listaDocumentoSunatLetra;
	}
	public DocumentoSunatDetalle getDetalleLetra() {
		return detalleLetra;
	}
	public void setDetalleLetra(DocumentoSunatDetalle detalleLetra) {
		this.detalleLetra = detalleLetra;
	}
	public Date getDtFechaDeGiro() {
		return dtFechaDeGiro;
	}
	public void setDtFechaDeGiro(Date dtFechaDeGiro) {
		this.dtFechaDeGiro = dtFechaDeGiro;
	}
	//Agregado por cdelosrios, 04/01/2013
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	//Fin agregado por cdelosrios, 04/01/2013

	@Override
	public String toString() {
		return "DocumentoSunat [id=" + id + ", intPersEmpresaOrden="
				+ intPersEmpresaOrden + ", intItemOrdenCompra="
				+ intItemOrdenCompra + ", intPersEmpresaRequisicion="
				+ intPersEmpresaRequisicion + ", intItemRequisicion="
				+ intItemRequisicion + ", tsFechaProvision=" + tsFechaProvision
				+ ", intParaDocumentoGeneral=" + intParaDocumentoGeneral
				+ ", intParaTipoComprobante=" + intParaTipoComprobante
				+ ", strSerieDocumento=" + strSerieDocumento
				+ ", strNumeroDocumento=" + strNumeroDocumento
				+ ", dtFechaEmision=" + dtFechaEmision
				+ ", dtFechaVencimiento=" + dtFechaVencimiento
				+ ", dtFechaPago=" + dtFechaPago + ", intIndicadorInafecto="
				+ intIndicadorInafecto + ", intIndicadorIGV=" + intIndicadorIGV
				+ ", intIndicadorPercepcion=" + intIndicadorPercepcion
				+ ", intIndicadorLetras=" + intIndicadorLetras + ", strGlosa="
				+ strGlosa + ", intParaEstado=" + intParaEstado
				+ ", intParaEstadoPago=" + intParaEstadoPago
				+ ", intPersEmpresaDocSunatEnlazado="
				+ intPersEmpresaDocSunatEnlazado
				+ ", intItemDocumentoSunatEnlazado="
				+ intItemDocumentoSunatEnlazado + ", intPersEmpresaUsuario="
				+ intPersEmpresaUsuario + ", intPersPersonaUsuario="
				+ intPersPersonaUsuario + ", tsFechaAnula=" + tsFechaAnula
				+ ", intPersEmpresaAnula=" + intPersEmpresaAnula
				+ ", intPersPersonaAnula=" + intPersPersonaAnula
				+ ", intPersEmpresaEgreso=" + intPersEmpresaEgreso
				+ ", intItemEgresoGeneral=" + intItemEgresoGeneral
				+ ", intEmpresaLibro=" + intEmpresaLibro + ", intPeriodoLibro="
				+ intPeriodoLibro + ", intCodigoLibro=" + intCodigoLibro + "]";
	}
}