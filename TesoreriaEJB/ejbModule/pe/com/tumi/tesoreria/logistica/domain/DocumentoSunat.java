package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.persona.core.domain.Persona;

public class DocumentoSunat extends TumiDomain{
	//BD
	private DocumentoSunatId id;
	private Integer 	intPersEmpresaOrden;			//PERS_EMPRESAORDEN_N_PK
	private Integer 	intItemOrdenCompra;				//TESO_ITEMORDENCOMPRA_N
	private Integer 	intPersEmpresaRequisicion;		//PERS_EMPRESAREQUISICION_N_PK
	private Integer 	intItemRequisicion;				//TESO_ITEMREQUISICION_N
	private Timestamp	tsFechaProvision;				//DOSU_FECHAPROVISION_D
	private Integer 	intParaDocumentoGeneral;		//PARA_DOCUMENTOGENERAL_N_COD
	private Integer 	intParaTipoComprobante;			//PARA_TIPOCOMPROBANTE_N_COD
	private String 		strSerieDocumento;				//DOSU_SERIEDOCUMENTO_V
	private String 		strNumeroDocumento;				//DOSU_NUMERODOC_V
	private Timestamp 	tsFechaRegistro;				//DOSU_FECHAREGISTRO_D
	private Date 		dtFechaEmision;					//DOSU_FECHAEMISION_D
	private Date 		dtFechaVencimiento;				//DOSU_FECHAVENCIMIENTO_D
	private Date 		dtFechaPago;					//DOSU_FECHAPAGO_D
	private Integer 	intIndicadorInafecto;			//DOSU_INDICADORINAFECTO_N			
	private Integer 	intIndicadorIGV;				//DOSU_INDICADORIGV_N			
	private Integer 	intIndicadorPercepcion;			//DOSU_INDICADORPERCEPCION_N			
	private Integer 	intIndicadorLetras;				//DOSU_INDICADORLETRAS_N			
	private String		strGlosa;						//DOSU_GLOSA_V			
	private Integer 	intParaEstado;					//PARA_ESTADO_N_COD			
	private Integer 	intParaEstadoPago;				//PARA_ESTADOPAGO_N_COD			
	private Integer 	intPersEmpresaUsuario;			//PERS_EMPRESAUSUARIO_N_PK
	private Integer 	intPersPersonaUsuario;			//PERS_PERSONAUSUARIO_N_PK			
	private Integer 	intSucuIdSucursal;				//SUCU_IDSUCURSAL_N			
	private Integer 	intSubIdSubsucursal;			//SUDE_IDSUBSUCURSAL_N			
	private Integer 	intPersEmpresaDocSunatAnula;	//PERS_EMPRESADOCSUNATAN_N_PK	
	private Integer 	intItemDocumentoSunatAnula;		//TESO_ITEMDOCSUNAN_N
	private Timestamp 	tsFechaAnula;					//DOSU_FECHAANULA_D
	private Integer 	intPersEmpresaAnula;			//PERS_EMPRESAANULA_N_PK	
	private Integer 	intPersPersonaAnula;			//PERS_PERSONAANULA_N_PK
	private Integer 	intPersEmpresaEgreso;			//PERS_EMPRESAEGRESO_N_PK
	private Integer 	intItemEgresoGeneral;			//TESO_ITEMEGRESOGENERAL_N	
	private Integer 	intEmpresaLibro;				//PERS_EMPRESALIBRO_N_PK
	private Integer 	intPeriodoLibro;				//CONT_PERIODOLIBRO_N
	private Integer 	intCodigoLibro;					//CONT_CODIGOLIBRO_N
	private Integer 	intParaTipo;					//PARA_TIPO_N_COD
	private Integer 	intItemArchivo;					//MAE_ITEMARCHIVO_N
	private Integer 	intItemHistorico;				//MAE_ITEMHISTORICO_N
	private Integer 	intPersEmpresaDocSunatEnlazado;	//PERS_EMPRESADOCSUNATREL_N_PK
	private Integer 	intItemDocumentoSunatEnlazado;	//TESO_ITEMDOCSUNREL_N	
	private Integer 	intPersEmpresaIngreso;			//PERS_EMPRESAINGRESO_N_PK
	private Integer 	intItemIngresoGeneral;			//TESO_ITEMINGRESOGENERAL_N
	
	//ADICIONALES (cdelosrios - DIC2013 / MAR2014)
	private Archivo						archivoDocumento;
	private List<DocumentoSunatDetalle>	listaDocumentoSunatDetalle;
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
	private DocumentoSunatDetalle	detalleRetencion;
	private DocumentoSunatDetalle	detalleTotalGeneral;
	private DocumentoSunatDetalle	detalleLetra;
	
	private boolean	seleccionaInafecto;
	private boolean	seleccionaIGVContable;
	private boolean	seleccionaPercepcion;
//	private boolean	seleccionaLetras;
	
	private Date	dtFiltroEmisionDesde;
	private Date	dtFiltroEmisionHasta;
	private Date	dtFiltroVencimientoDesde;
	private Date	dtFiltroVencimientoHasta;
	private Date	dtFiltroProgramacionDesde;
	private Date	dtFiltroProgramacionHasta;
	private Integer	intTipoFiltroFecha;
	private Date	dtFechaDeGiro;
	
	private DocumentoSunatDoc	docPercepcion;
	private DocumentoSunatDoc	docDetraccion;
	
	private List<DocumentoSunatOrdenComDoc> listaDocumentoSunatOrdenComDoc;
	private BigDecimal bdMontoAplicar;
	
	private DocumentoSunatDetalle	detalleNotaCredito;
	private DocumentoSunatDetalle	detalleNotaDebito;
	
	private BigDecimal 	bdMontoTotalSinDetraccion;
	private List<DocumentoSunat> listaDocSunatRelacionadosConLetraDeCambio;
	private List<DocumentoSunat> listaDocSunatDocRelacionadosConLetraDeCambio;
	private List<DocumentoSunat> listaDocSunatRelacionadosConNotaCreditoYDebito;
	private Boolean blnGeneraDetraccion;
	private Integer rbDocSunatSelected;
	
	private List<DocumentoSunat>	listaDocumentoSunatNota;
	private Boolean blnGeneraDetraccionNota;
	private BigDecimal bdMontoSaldoTemp;
	private TipoCambio tipoCambio;
	
	//BD
	public DocumentoSunat(){
		id = new DocumentoSunatId();
		listaDocumentoSunatOrdenComDoc = new ArrayList<DocumentoSunatOrdenComDoc>();
		listaDocSunatRelacionadosConLetraDeCambio = new ArrayList<DocumentoSunat>();
		listaDocSunatRelacionadosConNotaCreditoYDebito = new ArrayList<DocumentoSunat>();
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
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
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
	public Integer getIntPersEmpresaDocSunatAnula() {
		return intPersEmpresaDocSunatAnula;
	}
	public void setIntPersEmpresaDocSunatAnula(Integer intPersEmpresaDocSunatAnula) {
		this.intPersEmpresaDocSunatAnula = intPersEmpresaDocSunatAnula;
	}
	public Integer getIntItemDocumentoSunatAnula() {
		return intItemDocumentoSunatAnula;
	}
	public void setIntItemDocumentoSunatAnula(Integer intItemDocumentoSunatAnula) {
		this.intItemDocumentoSunatAnula = intItemDocumentoSunatAnula;
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
	public Integer getIntPersEmpresaDocSunatEnlazado() {
		return intPersEmpresaDocSunatEnlazado;
	}
	public void setIntPersEmpresaDocSunatEnlazado(
			Integer intPersEmpresaDocSunatEnlazado) {
		this.intPersEmpresaDocSunatEnlazado = intPersEmpresaDocSunatEnlazado;
	}
	public Integer getIntItemDocumentoSunatEnlazado() {
		return intItemDocumentoSunatEnlazado;
	}
	public void setIntItemDocumentoSunatEnlazado(
			Integer intItemDocumentoSunatEnlazado) {
		this.intItemDocumentoSunatEnlazado = intItemDocumentoSunatEnlazado;
	}
	public Integer getIntPersEmpresaIngreso() {
		return intPersEmpresaIngreso;
	}
	public void setIntPersEmpresaIngreso(Integer intPersEmpresaIngreso) {
		this.intPersEmpresaIngreso = intPersEmpresaIngreso;
	}
	public Integer getIntItemIngresoGeneral() {
		return intItemIngresoGeneral;
	}
	public void setIntItemIngresoGeneral(Integer intItemIngresoGeneral) {
		this.intItemIngresoGeneral = intItemIngresoGeneral;
	}	
	//ADICIONALES
	public Archivo getArchivoDocumento() {
		return archivoDocumento;
	}
	public void setArchivoDocumento(Archivo archivoDocumento) {
		this.archivoDocumento = archivoDocumento;
	}
	public List<DocumentoSunatDetalle> getListaDocumentoSunatDetalle() {
		return listaDocumentoSunatDetalle;
	}
	public void setListaDocumentoSunatDetalle(
			List<DocumentoSunatDetalle> listaDocumentoSunatDetalle) {
		this.listaDocumentoSunatDetalle = listaDocumentoSunatDetalle;
	}
	public List<DocumentoSunat> getListaDocumentoSunatLetra() {
		return listaDocumentoSunatLetra;
	}
	public void setListaDocumentoSunatLetra(
			List<DocumentoSunat> listaDocumentoSunatLetra) {
		this.listaDocumentoSunatLetra = listaDocumentoSunatLetra;
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
	public Integer getIntParaMoneda() {
		return intParaMoneda;
	}
	public void setIntParaMoneda(Integer intParaMoneda) {
		this.intParaMoneda = intParaMoneda;
	}
	public Persona getPersonaGirar() {
		return personaGirar;
	}
	public void setPersonaGirar(Persona personaGirar) {
		this.personaGirar = personaGirar;
	}
	public DocumentoSunatDetalle getDetalleSubTotal() {
		return detalleSubTotal;
	}
	public void setDetalleSubTotal(DocumentoSunatDetalle detalleSubTotal) {
		this.detalleSubTotal = detalleSubTotal;
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
//	public DocumentoSunatDetalle getDetallePercepcion() {
//		return detallePercepcion;
//	}
//	public void setDetallePercepcion(DocumentoSunatDetalle detallePercepcion) {
//		this.detallePercepcion = detallePercepcion;
//	}
//	public DocumentoSunatDetalle getDetalleDetraccion() {
//		return detalleDetraccion;
//	}
//	public void setDetalleDetraccion(DocumentoSunatDetalle detalleDetraccion) {
//		this.detalleDetraccion = detalleDetraccion;
//	}
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
	public DocumentoSunatDetalle getDetalleLetra() {
		return detalleLetra;
	}
	public void setDetalleLetra(DocumentoSunatDetalle detalleLetra) {
		this.detalleLetra = detalleLetra;
	}
	public boolean isSeleccionaInafecto() {
		return seleccionaInafecto;
	}
	public void setSeleccionaInafecto(boolean seleccionaInafecto) {
		this.seleccionaInafecto = seleccionaInafecto;
	}
	public boolean isSeleccionaIGVContable() {
		return seleccionaIGVContable;
	}
	public void setSeleccionaIGVContable(boolean seleccionaIGVContable) {
		this.seleccionaIGVContable = seleccionaIGVContable;
	}
	public boolean isSeleccionaPercepcion() {
		return seleccionaPercepcion;
	}
	public void setSeleccionaPercepcion(boolean seleccionaPercepcion) {
		this.seleccionaPercepcion = seleccionaPercepcion;
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
	public Date getDtFechaDeGiro() {
		return dtFechaDeGiro;
	}
	public void setDtFechaDeGiro(Date dtFechaDeGiro) {
		this.dtFechaDeGiro = dtFechaDeGiro;
	}
	public DocumentoSunatDoc getDocPercepcion() {
		return docPercepcion;
	}
	public void setDocPercepcion(DocumentoSunatDoc docPercepcion) {
		this.docPercepcion = docPercepcion;
	}
	public DocumentoSunatDoc getDocDetraccion() {
		return docDetraccion;
	}
	public void setDocDetraccion(DocumentoSunatDoc docDetraccion) {
		this.docDetraccion = docDetraccion;
	}
	public List<DocumentoSunatOrdenComDoc> getListaDocumentoSunatOrdenComDoc() {
		return listaDocumentoSunatOrdenComDoc;
	}
	public void setListaDocumentoSunatOrdenComDoc(
			List<DocumentoSunatOrdenComDoc> listaDocumentoSunatOrdenComDoc) {
		this.listaDocumentoSunatOrdenComDoc = listaDocumentoSunatOrdenComDoc;
	}
	public BigDecimal getBdMontoAplicar() {
		return bdMontoAplicar;
	}
	public void setBdMontoAplicar(BigDecimal bdMontoAplicar) {
		this.bdMontoAplicar = bdMontoAplicar;
	}
	public DocumentoSunatDetalle getDetalleNotaCredito() {
		return detalleNotaCredito;
	}
	public void setDetalleNotaCredito(DocumentoSunatDetalle detalleNotaCredito) {
		this.detalleNotaCredito = detalleNotaCredito;
	}
	public DocumentoSunatDetalle getDetalleNotaDebito() {
		return detalleNotaDebito;
	}
	public void setDetalleNotaDebito(DocumentoSunatDetalle detalleNotaDebito) {
		this.detalleNotaDebito = detalleNotaDebito;
	}
	public BigDecimal getBdMontoTotalSinDetraccion() {
		return bdMontoTotalSinDetraccion;
	}
	public void setBdMontoTotalSinDetraccion(BigDecimal bdMontoTotalSinDetraccion) {
		this.bdMontoTotalSinDetraccion = bdMontoTotalSinDetraccion;
	}
	public List<DocumentoSunat> getListaDocSunatRelacionadosConLetraDeCambio() {
		return listaDocSunatRelacionadosConLetraDeCambio;
	}
	public void setListaDocSunatRelacionadosConLetraDeCambio(
			List<DocumentoSunat> listaDocSunatRelacionadosConLetraDeCambio) {
		this.listaDocSunatRelacionadosConLetraDeCambio = listaDocSunatRelacionadosConLetraDeCambio;
	}
	public List<DocumentoSunat> getListaDocSunatRelacionadosConNotaCreditoYDebito() {
		return listaDocSunatRelacionadosConNotaCreditoYDebito;
	}
	public void setListaDocSunatRelacionadosConNotaCreditoYDebito(
			List<DocumentoSunat> listaDocSunatRelacionadosConNotaCreditoYDebito) {
		this.listaDocSunatRelacionadosConNotaCreditoYDebito = listaDocSunatRelacionadosConNotaCreditoYDebito;
	}
	public List<DocumentoSunat> getListaDocSunatDocRelacionadosConLetraDeCambio() {
		return listaDocSunatDocRelacionadosConLetraDeCambio;
	}
	public void setListaDocSunatDocRelacionadosConLetraDeCambio(
			List<DocumentoSunat> listaDocSunatDocRelacionadosConLetraDeCambio) {
		this.listaDocSunatDocRelacionadosConLetraDeCambio = listaDocSunatDocRelacionadosConLetraDeCambio;
	}
	public Boolean getBlnGeneraDetraccion() {
		return blnGeneraDetraccion;
	}
	public void setBlnGeneraDetraccion(Boolean blnGeneraDetraccion) {
		this.blnGeneraDetraccion = blnGeneraDetraccion;
	}
	public Integer getRbDocSunatSelected() {
		return rbDocSunatSelected;
	}
	public void setRbDocSunatSelected(Integer rbDocSunatSelected) {
		this.rbDocSunatSelected = rbDocSunatSelected;
	}
	public List<DocumentoSunat> getListaDocumentoSunatNota() {
		return listaDocumentoSunatNota;
	}
	public void setListaDocumentoSunatNota(
			List<DocumentoSunat> listaDocumentoSunatNota) {
		this.listaDocumentoSunatNota = listaDocumentoSunatNota;
	}
	public Boolean getBlnGeneraDetraccionNota() {
		return blnGeneraDetraccionNota;
	}
	public void setBlnGeneraDetraccionNota(Boolean blnGeneraDetraccionNota) {
		this.blnGeneraDetraccionNota = blnGeneraDetraccionNota;
	}
	public BigDecimal getBdMontoSaldoTemp() {
		return bdMontoSaldoTemp;
	}
	public void setBdMontoSaldoTemp(BigDecimal bdMontoSaldoTemp) {
		this.bdMontoSaldoTemp = bdMontoSaldoTemp;
	}
	public TipoCambio getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(TipoCambio tipoCambio) {
		this.tipoCambio = tipoCambio;
	}	
}