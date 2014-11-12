package pe.com.tumi.tesoreria.logistica.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Tarifa;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.tesoreria.logistica.bo.DocumentoSunatBO;
import pe.com.tumi.tesoreria.logistica.bo.DocumentoSunatDetalleBO;
import pe.com.tumi.tesoreria.logistica.bo.DocumentoSunatDocBO;
import pe.com.tumi.tesoreria.logistica.bo.DocumentoSunatLetraBO;
import pe.com.tumi.tesoreria.logistica.bo.DocumentoSunatOrdenComDocBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraDetalleBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraDocumentoBO;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalleId;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDoc;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDocId;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatLetra;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatOrdenComDoc;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;

public class DocumentoSunatService {

	protected static Logger log = Logger.getLogger(DocumentoSunatService.class);
	
	DocumentoSunatBO boDocumentoSunat = (DocumentoSunatBO)TumiFactory.get(DocumentoSunatBO.class);
	DocumentoSunatDetalleBO boDocumentoSunatDetalle = (DocumentoSunatDetalleBO)TumiFactory.get(DocumentoSunatDetalleBO.class);
	OrdenCompraBO boOrdenCompra = (OrdenCompraBO)TumiFactory.get(OrdenCompraBO.class);
	OrdenCompraDetalleBO boOrdenCompraDetalle = (OrdenCompraDetalleBO)TumiFactory.get(OrdenCompraDetalleBO.class);
	OrdenCompraDocumentoBO boOrdenCompraDocumento = (OrdenCompraDocumentoBO)TumiFactory.get(OrdenCompraDocumentoBO.class);
	DocumentoSunatModeloService documentoSunatModeloService = (DocumentoSunatModeloService)TumiFactory.get(DocumentoSunatModeloService.class);
	//Agregado por cdelosrios, 01/11/2013
	DocumentoSunatDocBO boDocumentoSunatDoc = (DocumentoSunatDocBO)TumiFactory.get(DocumentoSunatDocBO.class);
	//Fin agregado por cdelosrios, 01/11/2013
	DocumentoSunatOrdenComDocBO boDocumentoSunatOrdenComDoc = (DocumentoSunatOrdenComDocBO)TumiFactory.get(DocumentoSunatOrdenComDocBO.class);
	DocumentoSunatLetraBO boDocumentoSunatLetra = (DocumentoSunatLetraBO)TumiFactory.get(DocumentoSunatLetraBO.class);
	
//	public List<DocumentoSunat> grabarDocumentoSunat(List<DocumentoSunat> listaDocumentoSunat, OrdenCompra ordenCompra) throws BusinessException{
//		//Agregado por cdelosrios, 01/11/2013
//		DocumentoSunatDoc documentoSunatDoc = null;
//		//Fin agregado por cdelosrios, 01/11/2013
//		//Agregado por cdelosrios, 12/11/2013
//		AdelantoSunat docSunatOrdenCompraDoc = null;
//		//Fin agregado por cdelosrios, 12/11/2013
//		try{
//			LibroDiarioFacadeRemote libroDiarioFacade =  (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
//			
//			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
//				log.info(ordenCompraDetalle);
//				boOrdenCompraDetalle.modificar(ordenCompraDetalle);
//			}
//			
//			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
//				log.info(ordenCompraDocumento);
//				boOrdenCompraDocumento.modificar(ordenCompraDocumento);
//			}
//			
//			for(DocumentoSunat documentoSunat : listaDocumentoSunat){
//				LibroDiario libroDiario = procesarLibroDiario(documentoSunat);
//				libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
//				
//				documentoSunat.setIntEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
//				documentoSunat.setIntPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
//				documentoSunat.setIntCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
//				log.info(documentoSunat);
//				documentoSunat = boDocumentoSunat.grabar(documentoSunat);
//				
//				for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunat.getListaDocumentoSunatDetalle()){
//					grabarDocumentoSunatDetalle(documentoSunat, documentoSunatDetalle, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PRODUCTO);
//				}
//				
//				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleSubTotal(), 
//							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_SUBTOTAL);				
//				
//				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleDescuento(), 
//							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DESCUENTO);
//				
//				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleValorVenta(), 
//							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_VALORVENTA);
//				
//				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleIGV(), 
//							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV);
//				
//				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleOtros(), 
//							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_OTROS);
//				
//				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleTotal(), 
//							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL);
//				
//				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetallePercepcion(), 
//							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION);
//				
//				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleDetraccion(), 
//							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION);
//				
//				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleRetencion(), 
//							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION);
//				
//				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(), 
//							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL);
//				
//				//Agregado por cdelosrios, 01/11/2013
//				if(documentoSunat.getDetallePercepcion().getBdMontoTotal()!=null
//						&& documentoSunat.getDetallePercepcion().getBdMontoTotal().compareTo(BigDecimal.ZERO)>0){
//					documentoSunatDoc = new DocumentoSunatDoc();
//					documentoSunatDoc.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
//					documentoSunatDoc.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
//					documentoSunatDoc.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION);
//					documentoSunatDoc.setBdMontoDocumento(documentoSunat.getDetallePercepcion().getBdMontoTotal());
//					documentoSunatDoc.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
//					documentoSunatDoc.setIntPersEmpresaPago(documentoSunat.getId().getIntPersEmpresa());
//					boDocumentoSunatDoc.grabar(documentoSunatDoc);
//				}
//				
//				if(documentoSunat.getDetalleDetraccion().getBdMontoTotal()!=null
//						&& documentoSunat.getDetalleDetraccion().getBdMontoTotal().compareTo(BigDecimal.ZERO)>0){
//					documentoSunatDoc = new DocumentoSunatDoc();
//					documentoSunatDoc.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
//					documentoSunatDoc.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
//					documentoSunatDoc.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION);
//					documentoSunatDoc.setBdMontoDocumento(documentoSunat.getDetalleDetraccion().getBdMontoTotal());
//					documentoSunatDoc.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
//					documentoSunatDoc.setIntPersEmpresaPago(documentoSunat.getId().getIntPersEmpresa());
//					boDocumentoSunatDoc.grabar(documentoSunatDoc);
//				}
//				//Fin agregado por cdelosrios, 01/11/2013
//				
//				for(AdelantoSunat adelantoSunat : documentoSunat.getListaAdelantoSunat()){
//					adelantoSunat.setDocumentoSunatId(documentoSunat.getId());
//					log.info(adelantoSunat);
//					boAdelantoSunat.grabar(adelantoSunat);
//				}
//				
//				//Agregado por cdelosrios, 12/11/2013
//				for(OrdenCompraDetalle ordenCompraDetalle : documentoSunat.getOrdenCompra().getListaOrdenCompraDetalle()){
//					if(ordenCompraDetalle.getBdMontoUsar()!=null && 
//							ordenCompraDetalle.getBdMontoUsar().compareTo(BigDecimal.ZERO)>0){
//						docSunatOrdenCompraDoc = new AdelantoSunat();
//						docSunatOrdenCompraDoc.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
//						//docSunatOrdenCompraDoc.setOrdenCompraDocumentoId(documentoSunat.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getId());
//						docSunatOrdenCompraDoc.setDocumentoSunatId(documentoSunat.getId());
//						docSunatOrdenCompraDoc.setBdMonto(documentoSunat.getDetalleTotal().getBdMontoTotal());
//						//docSunatOrdenCompraDoc.setBdMonto(ordenCompraDetalle.getBdMontoUsar());
//						docSunatOrdenCompraDoc.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//						docSunatOrdenCompraDoc.setIntItemOrdenCompraDet(ordenCompraDetalle.getId().getIntItemOrdenCompraDetalle());
//						boAdelantoSunat.grabar(docSunatOrdenCompraDoc);
//					}
//				}
//				//Fin agregado por cdelosrios, 12/11/2013
//			}
//		}catch(BusinessException e){
//			throw e;
//		}catch(Exception e){
//			throw new BusinessException(e);
//		}
//		return listaDocumentoSunat;
//	}
	
	public List<DocumentoSunat> grabarDocumentoSunat(List<DocumentoSunat> listaDocumentoSunat, OrdenCompra ordenCompra) throws BusinessException{
//		DocumentoSunatDoc documentoSunatDoc = null;

		try{
			LibroDiarioFacadeRemote libroDiarioFacade =  (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			//MODIFICACION DE ORDEN DE COMPRA DETALLE...
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				log.info("---------- MODIFICACION ORDEN DE COMPRA DETALLE ----------");
//				log.info(ordenCompraDetalle.getBdCantidad());
//				log.info(ordenCompraDetalle.getIntParaUnidadMedida());
//				log.info(ordenCompraDetalle.getIntAfectoIGV());
//				log.info(ordenCompraDetalle.getIntParaTipoMoneda());
//				log.info(ordenCompraDetalle.getBdPrecioUnitario());
//				log.info(ordenCompraDetalle.getBdPrecioTotal());
//				log.info(ordenCompraDetalle.getBdMontoSaldo());
//				log.info(ordenCompraDetalle.getStrDescripcion());
//				log.info(ordenCompraDetalle.getIntSucuIdSucursal());
//				log.info(ordenCompraDetalle.getIntSudeIdSubsucursal());
//				log.info(ordenCompraDetalle.getIntIdArea());
//				log.info(ordenCompraDetalle.getStrObservacion());
//				log.info(ordenCompraDetalle.getIntParaEstado());
//				log.info(ordenCompraDetalle.getIntPersEmpresaUsuario());
//				log.info(ordenCompraDetalle.getIntPersPersonaUsuario());
//				log.info(ordenCompraDetalle.getTsFechaAnula());
//				log.info(ordenCompraDetalle.getIntPersEmpresaAnula());
//				log.info(ordenCompraDetalle.getIntPersPersonaAnula());
//				log.info(ordenCompraDetalle.getIntPersEmpresaCuenta());
//				log.info(ordenCompraDetalle.getIntContPeriodoCuenta());
//				log.info(ordenCompraDetalle.getStrContNumeroCuenta());
//				log.info(ordenCompraDetalle.getBdMontoUsar());
//				log.info(ordenCompraDetalle.getBdMontoSaldoTemp());
//				log.info("-------------------------------------------------------");
				boOrdenCompraDetalle.modificar(ordenCompraDetalle);
			}
			//MODIFICACION DE ORDEN DE COMPRA DOCUMENTO...
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				log.info("---------- MODIFICACION ORDEN DE COMPRA DOCUMENTO ----------");
//				log.info(ordenCompraDocumento.getIntParaDocumentoGeneral());
//				log.info(ordenCompraDocumento.getTsFechaDocumento());
//				log.info(ordenCompraDocumento.getIntParaTipoCancelacion());
//				log.info(ordenCompraDocumento.getBdMontoDocumento());
//				log.info(ordenCompraDocumento.getIntParaTipoMoneda());
//				log.info(ordenCompraDocumento.getIntSucuIdSucursal());
//				log.info(ordenCompraDocumento.getIntSudeIdSubsucursal());
//				log.info(ordenCompraDocumento.getIntIdArea());
//				log.info(ordenCompraDocumento.getStrObservacion());
//				log.info(ordenCompraDocumento.getIntPersEmpresaUsuario());
//				log.info(ordenCompraDocumento.getIntPersPersonaUsuario());
//				log.info(ordenCompraDocumento.getTsFechaAnula());
//				log.info(ordenCompraDocumento.getIntPersEmpresaAnula());
//				log.info(ordenCompraDocumento.getIntPersPersonaAnula());
//				log.info(ordenCompraDocumento.getBdMontoPagado());
//				log.info(ordenCompraDocumento.getBdMontoIngresado());
//				log.info(ordenCompraDocumento.getIntParaEstado());
//				log.info(ordenCompraDocumento.getBdMontoUsar());
//				log.info(ordenCompraDocumento.getBdMontoPagadoTemp());
//				log.info(ordenCompraDocumento.getBdMontoIngresadoTemp());
//				log.info(ordenCompraDocumento.getBdMontoUsarTotal());
//				log.info(ordenCompraDocumento.getBdMontoUsarDocumento());
//				log.info(ordenCompraDocumento.getTsFechaRegistro());
//				log.info(ordenCompraDocumento.getIntPersEmpresaEgreso());
//				log.info(ordenCompraDocumento.getIntItemEgresoGeneral());
//				log.info(ordenCompraDocumento.getIntParaEstadoPago());
//				log.info(ordenCompraDocumento.getIntPersEmpresaIngreso());
//				log.info(ordenCompraDocumento.getIntItemIngresoGeneral());
//				log.info(ordenCompraDocumento.getBdMontoSaldo());
//				log.info("-------------------------------------------------------");
				ordenCompraDocumento.setBdMontoSaldo(ordenCompraDocumento.getBdMontoPagadoTemp().subtract(ordenCompraDocumento.getBdMontoIngresado()));
				boOrdenCompraDocumento.modificar(ordenCompraDocumento);
			}
			
			for(DocumentoSunat documentoSunat : listaDocumentoSunat){
				LibroDiario libroDiario = procesarLibroDiarioDocumentoSunat(documentoSunat, ordenCompra);
				libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
				
				documentoSunat.setIntEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
				documentoSunat.setIntPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
				documentoSunat.setIntCodigoLibro(libroDiario.getId().getIntContCodigoLibro());

				log.info("---------- GRABACION DOCUMENTO SUNAT ----------");
//				log.info("PERS_EMPRESAORDEN_N_PK: "+documentoSunat.getIntPersEmpresaOrden());
//				log.info("TESO_ITEMORDENCOMPRA_N: "+documentoSunat.getIntItemOrdenCompra());
//				log.info("PERS_EMPRESAREQUISICION_N_PK: "+documentoSunat.getIntPersEmpresaRequisicion());
//				log.info("TESO_ITEMREQUISICION_N: "+documentoSunat.getIntItemRequisicion());
//				log.info("DOSU_FECHAPROVISION_D: "+documentoSunat.getTsFechaProvision());
//				log.info("PARA_DOCUMENTOGENERAL_N_COD: "+documentoSunat.getIntParaDocumentoGeneral());
//				log.info("PARA_TIPOCOMPROBANTE_N_COD: "+documentoSunat.getIntParaTipoComprobante());
//				log.info("DOSU_SERIEDOCUMENTO_V: "+documentoSunat.getStrSerieDocumento());
//				log.info("DOSU_NUMERODOC_V: "+documentoSunat.getStrNumeroDocumento());
//				log.info("DOSU_FECHAREGISTRO_D: "+documentoSunat.getTsFechaRegistro());
//				log.info("DOSU_FECHAEMISION_D: "+documentoSunat.getDtFechaEmision());
//				log.info("DOSU_FECHAVENCIMIENTO_D: "+documentoSunat.getDtFechaVencimiento());
//				log.info("DOSU_FECHAPAGO_D: "+documentoSunat.getDtFechaPago());
//				log.info("DOSU_INDICADORINAFECTO_N: "+documentoSunat.getIntIndicadorInafecto());
//				log.info("DOSU_INDICADORIGV_N: "+documentoSunat.getIntIndicadorIGV());
//				log.info("DOSU_INDICADORPERCEPCION_N: "+documentoSunat.getIntIndicadorPercepcion());
//				log.info("DOSU_INDICADORLETRAS_N: "+documentoSunat.getIntIndicadorLetras());
//				log.info("DOSU_GLOSA_V: "+documentoSunat.getStrGlosa());
//				log.info("PARA_ESTADO_N_COD: "+documentoSunat.getIntParaEstado());
//				log.info("PARA_ESTADOPAGO_N_COD: "+documentoSunat.getIntParaEstadoPago());
//				log.info("PERS_EMPRESAUSUARIO_N_PK: "+documentoSunat.getIntPersEmpresaUsuario());
//				log.info("PERS_PERSONAUSUARIO_N_PK: "+documentoSunat.getIntPersPersonaUsuario());
//				log.info("SUCU_IDSUCURSAL_N: "+documentoSunat.getIntSucuIdSucursal());
//				log.info("SUDE_IDSUBSUCURSAL_N: "+documentoSunat.getIntSubIdSubsucursal());
//				log.info("PERS_EMPRESADOCSUNATAN_N_PK: "+documentoSunat.getIntPersEmpresaDocSunatAnula());
//				log.info("TESO_ITEMDOCSUNAN_N: "+documentoSunat.getIntItemDocumentoSunatAnula());
//				log.info("DOSU_FECHAANULA_D: "+documentoSunat.getTsFechaAnula());
//				log.info("PERS_EMPRESAANULA_N_PK: "+documentoSunat.getIntPersEmpresaAnula());
//				log.info("PERS_PERSONAANULA_N_PK: "+documentoSunat.getIntPersPersonaAnula());
//				log.info("PERS_EMPRESAEGRESO_N_PK: "+documentoSunat.getIntPersEmpresaEgreso());
//				log.info("TESO_ITEMEGRESOGENERAL_N: "+documentoSunat.getIntItemEgresoGeneral());
//				log.info("PERS_EMPRESALIBRO_N_PK: "+documentoSunat.getIntEmpresaLibro());
//				log.info("CONT_PERIODOLIBRO_N: "+documentoSunat.getIntPeriodoLibro());
//				log.info("CONT_CODIGOLIBRO_N: "+documentoSunat.getIntCodigoLibro());
//				log.info("PARA_TIPO_N_COD: "+documentoSunat.getIntParaTipo());
//				log.info("MAE_ITEMARCHIVO_N: "+documentoSunat.getIntItemArchivo());
//				log.info("MAE_ITEMHISTORICO_N: "+documentoSunat.getIntItemHistorico());
//				log.info("PERS_EMPRESADOCSUNATREL_N_PK: "+documentoSunat.getIntPersEmpresaDocSunatEnlazado());
//				log.info("TESO_ITEMDOCSUNREL_N: "+documentoSunat.getIntItemDocumentoSunatEnlazado());	
//				log.info("PERS_EMPRESAINGRESO_N_PK: "+documentoSunat.getIntPersEmpresaIngreso());
//				log.info("TESO_ITEMINGRESOGENERAL_N: "+documentoSunat.getIntItemIngresoGeneral());
//				log.info("-------------------------------------------------------");
				documentoSunat = boDocumentoSunat.grabar(documentoSunat);

				
				//Grabación DocumentoSunatDetalle - Subtotal
				if (detalleValido(documentoSunat.getDetalleSubTotal())) {
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleSubTotal(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_SUBTOTAL);
				}
				
				//Grabación DocumentoSunatDetalle - Descuento
				if (detalleValido(documentoSunat.getDetalleDescuento())) {
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleDescuento(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DESCUENTO);
				}
				//Grabación DocumentoSunatDetalle - ValorVenta
				if (detalleValido(documentoSunat.getDetalleValorVenta())) {
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleValorVenta(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_VALORVENTA);
				}
				//Grabación DocumentoSunatDetalle - I.G.V.
				if (detalleValido(documentoSunat.getDetalleIGV())) {
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleIGV(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV);
				}
				//Grabación DocumentoSunatDetalle - Otros
				if (detalleValido(documentoSunat.getDetalleOtros())) {
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleOtros(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_OTROS);
				}
				//Grabación DocumentoSunatDetalle - Total1
				if (detalleValido(documentoSunat.getDetalleTotal())) {
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleTotal(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL);
				}
				//Grabación DocumentoSunatDetalle - Retención
				if (detalleValido(documentoSunat.getDetalleRetencion())) {
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleRetencion(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION);
				}
				//Grabación DocumentoSunatDetalle - Total General
				if (detalleValido(documentoSunat.getDetalleTotalGeneral())) {
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL);
				}
				//Grabación DocumentoSunatDoc - Percepción
				if (documentoValido(documentoSunat.getDocPercepcion())) {
					documentoSunat.setDocPercepcion(grabarDocumentoSunatDoc(documentoSunat, documentoSunat.getDocPercepcion(), Constante.PARAM_T_DOCUMENTOGENERAL_PERCEPCION));
				}
				//Grabación DocumentoSunatDoc - Detracción
				if (documentoValido(documentoSunat.getDocDetraccion())) {
					documentoSunat.setDocDetraccion(grabarDocumentoSunatDoc(documentoSunat, documentoSunat.getDocDetraccion(), Constante.PARAM_T_DOCUMENTOGENERAL_DETRACCION));
				}
				
				if (documentoSunat.getListaDocumentoSunatOrdenComDoc()!=null && !documentoSunat.getListaDocumentoSunatOrdenComDoc().isEmpty()) {
					for(DocumentoSunatOrdenComDoc docSunatOrdenCompraDoc : documentoSunat.getListaDocumentoSunatOrdenComDoc()){
						docSunatOrdenCompraDoc.setIntPersEmpresaDocSunat(documentoSunat.getId().getIntPersEmpresa());
						docSunatOrdenCompraDoc.setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
						log.info("------------ GRABACION DOCSUNORDENCOMDOC ----------------");
//						log.info("PERS_EMPRESAORDEN_N_PK: "+docSunatOrdenCompraDoc.getIntPersEmpresaOrden());		//
//						log.info("TESO_ITEMORDENCOMPRA_N: "+docSunatOrdenCompraDoc.getIntItemOrdenCompra());			//
//						log.info("TESO_ITEMORDENCOMPRADOC_N: "+docSunatOrdenCompraDoc.getIntItemOrdenCompraDoc());		//
//						log.info("PERS_EMPRESADOCSUNAT_N_PK: "+docSunatOrdenCompraDoc.getIntPersEmpresaDocSunat());		//
//						log.info("TESO_ITEMDOCSUN_N: "+docSunatOrdenCompraDoc.getIntItemDocumentoSunat());		//
//						log.info("EDSO_MONTO_N: "+docSunatOrdenCompraDoc.getBdMonto());					//
//						log.info("PARA_ESTADO_N_COD: "+docSunatOrdenCompraDoc.getIntParaEstado());				//
//						log.info("TESO_ITEMORDENCOMPRADET_N: "+docSunatOrdenCompraDoc.getIntItemOrdenCompraDetalle());	//
//						log.info("------------------------------------------------");
						boDocumentoSunatOrdenComDoc.grabar(docSunatOrdenCompraDoc);
					}
				}
				
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaDocumentoSunat;
	}
	
	private boolean detalleValido(DocumentoSunatDetalle documentoSunatDetalle){
		if(documentoSunatDetalle != null
		&& documentoSunatDetalle.getBdMontoTotal()!=null 
		&& documentoSunatDetalle.getBdMontoTotal().compareTo(new BigDecimal(0))!=0){
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	private boolean documentoValido(DocumentoSunatDoc documentoSunatDoc){
		if (documentoSunatDoc != null
		&& documentoSunatDoc.getBdMontoDocumento()!=null 
		&& documentoSunatDoc.getBdMontoDocumento().compareTo(new BigDecimal(0))!=0){
			return true;
		}
		return false;
	}
	//Autor: jchavez / Tarea: Modificación / Fecha: 28.10.2014
	private LibroDiario generarLibroDiario(DocumentoSunat documentoSunat)throws Exception{
		LibroDiario libroDiario = new LibroDiario();
		libroDiario.setId(new LibroDiarioId());
		libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
		libroDiario.getId().setIntPersEmpresaLibro(documentoSunat.getId().getIntPersEmpresa());
		libroDiario.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());
		libroDiario.setStrGlosa(documentoSunat.getStrGlosa());
		libroDiario.setTsFechaRegistro(MyUtil.obtenerFechaActual());
		libroDiario.setTsFechaDocumento(new Timestamp(documentoSunat.getDtFechaEmision().getTime()));
		libroDiario.setIntPersEmpresaUsuario(documentoSunat.getIntPersEmpresaUsuario());
		libroDiario.setIntPersPersonaUsuario(documentoSunat.getIntPersPersonaUsuario());
		libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
		return libroDiario;
	}
	
	//Autor: jchavez / Tarea: Modificación / Fecha: 28.10.2014
	private LibroDiarioDetalle generarLibroDiarioDetalle(DocumentoSunat documentoSunat, DocumentoSunatDetalle documentoSunatDetalle, ModeloDetalle modeloDetalle)throws Exception{
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
		TipoCambio tipoCambio = null;
		try {
			GeneralFacadeRemote generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			
//			libroDiarioDetalle.setId(new LibroDiarioDetalleId());
			libroDiarioDetalle.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
			libroDiarioDetalle.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
			libroDiarioDetalle.setIntPersPersona(documentoSunat.getOrdenCompra().getIntPersPersonaProveedor());
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
			libroDiarioDetalle.setStrSerieDocumento(documentoSunat.getStrSerieDocumento());
			libroDiarioDetalle.setStrNumeroDocumento(documentoSunat.getStrNumeroDocumento());
			libroDiarioDetalle.setIntPersEmpresaSucursal(documentoSunat.getId().getIntPersEmpresa());
			libroDiarioDetalle.setIntSucuIdSucursal(documentoSunatDetalle.getIntSucuIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(documentoSunatDetalle.getIntSudeIdSubsucursal());
			
			libroDiarioDetalle.setIntParaMonedaDocumento(documentoSunat.getIntParaMoneda());
			libroDiarioDetalle.setStrComentario(modeloDetalle.getPlanCuenta().getStrDescripcion());
			
			if (documentoSunatDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) {
				if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
					libroDiarioDetalle.setBdDebeSoles(documentoSunatDetalle.getBdMontoTotal());
				}else {
					libroDiarioDetalle.setBdHaberSoles(documentoSunatDetalle.getBdMontoTotal());
				}
				libroDiarioDetalle.setBdHaberExtranjero(null);
				libroDiarioDetalle.setBdDebeExtranjero(null);
			}else {
				tipoCambio = generalFacade.getTipoCambioPorMonedaYFecha(documentoSunatDetalle.getIntParaTipoMoneda(), documentoSunat.getDtFechaEmision(), documentoSunat.getId().getIntPersEmpresa());
				if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
					libroDiarioDetalle.setBdDebeExtranjero(documentoSunatDetalle.getBdMontoTotal().multiply(tipoCambio.getBdPromedio()));
				}else {
					libroDiarioDetalle.setBdHaberExtranjero(documentoSunatDetalle.getBdMontoTotal().multiply(tipoCambio.getBdPromedio()));
				}
				libroDiarioDetalle.setBdDebeSoles(null);
				libroDiarioDetalle.setBdHaberSoles(null);
			}
			
			libroDiarioDetalle.setIntParaTipoComprobante(documentoSunat.getIntParaTipoComprobante());
		} catch (Exception e) {
			log.info(e.getMessage());
		}		
		return libroDiarioDetalle;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalleOrdenCompra(DocumentoSunat documentoSunat, DocumentoSunatDetalle docSunatDetalle)throws Exception{
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
		PlanCuenta planCta = new PlanCuenta();
		
		TipoCambio tipoCambio = null;
		try {
			GeneralFacadeRemote generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			
//			libroDiarioDetalle.setId(new LibroDiarioDetalleId());
			libroDiarioDetalle.setIntPersEmpresaCuenta(docSunatDetalle.getOrdenCompraDetalle().getIntPersEmpresaCuenta());
			libroDiarioDetalle.setIntContPeriodo(docSunatDetalle.getOrdenCompraDetalle().getIntContPeriodoCuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(docSunatDetalle.getOrdenCompraDetalle().getStrContNumeroCuenta());
			libroDiarioDetalle.setIntPersPersona(documentoSunat.getOrdenCompra().getIntPersPersonaProveedor());
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
			libroDiarioDetalle.setStrSerieDocumento(documentoSunat.getStrSerieDocumento());
			libroDiarioDetalle.setStrNumeroDocumento(documentoSunat.getStrNumeroDocumento());
			libroDiarioDetalle.setIntPersEmpresaSucursal(documentoSunat.getId().getIntPersEmpresa());
			libroDiarioDetalle.setIntSucuIdSucursal(docSunatDetalle.getIntSucuIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(docSunatDetalle.getIntSudeIdSubsucursal());
			libroDiarioDetalle.setIntParaMonedaDocumento(documentoSunat.getIntParaMoneda());
			//Obtenemos el Plan de Cuenta...
			PlanCuentaId pId = new PlanCuentaId();
			pId.setIntEmpresaCuentaPk(docSunatDetalle.getOrdenCompraDetalle().getIntPersEmpresaCuenta());
			pId.setIntPeriodoCuenta(docSunatDetalle.getOrdenCompraDetalle().getIntContPeriodoCuenta());
			pId.setStrNumeroCuenta(docSunatDetalle.getOrdenCompraDetalle().getStrContNumeroCuenta());
			planCta = planCuentaFacade.getPlanCuentaPorPk(pId);
			libroDiarioDetalle.setStrComentario(planCta.getStrDescripcion());
			
			if (docSunatDetalle.getIntParaTipoMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) {
				libroDiarioDetalle.setBdDebeSoles(docSunatDetalle.getBdMontoGrabarEnLibro());
				libroDiarioDetalle.setBdHaberSoles(null);
				libroDiarioDetalle.setBdHaberExtranjero(null);
				libroDiarioDetalle.setBdDebeExtranjero(null);
			}else {
				tipoCambio = generalFacade.getTipoCambioPorMonedaYFecha(docSunatDetalle.getIntParaTipoMoneda(), documentoSunat.getDtFechaEmision(), documentoSunat.getId().getIntPersEmpresa());
				libroDiarioDetalle.setBdDebeSoles(null);
				libroDiarioDetalle.setBdHaberSoles(null);
				libroDiarioDetalle.setBdDebeExtranjero(docSunatDetalle.getBdMontoGrabarEnLibro().multiply(tipoCambio.getBdPromedio()));
				libroDiarioDetalle.setBdHaberExtranjero(null);
			}
			libroDiarioDetalle.setIntParaTipoComprobante(documentoSunat.getIntParaTipoComprobante());
		} catch (Exception e) {
			log.info(e.getMessage());
		}		
		return libroDiarioDetalle;
	}
	
	private LibroDiario procesarLibroDiarioDocumentoSunat(DocumentoSunat documentoSunat, OrdenCompra ordenCompra)throws Exception{
		LibroDiario libroDiario = generarLibroDiario(documentoSunat);
		Integer intParaTipoRequisicion = null;
		List<LibroDiarioDetalle> libroDiarioDetalleTemp = new ArrayList<LibroDiarioDetalle>();  
		Boolean blnIGVContable = false;
		try {
			ModeloFacadeRemote modeloFacade =  (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			intParaTipoRequisicion = ordenCompra.getDocumentoRequisicion().getRequisicion().getIntParaTipoRequisicion();
			log.info("TIPO DE REQUISICION: "+intParaTipoRequisicion);

			Modelo modelo = modeloFacade.obtenerTipoModeloActual
			(Constante.PARAM_T_MODELOCONTABLE_DOCUMENTOSUNAT, documentoSunat.getId().getIntPersEmpresa()).get(0);
			log.info("MODELO CONTABLE: "+modelo);

			
			if(documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_RECIBOHONORARIOS)){				
				if (detalleValido(documentoSunat.getDetalleRetencion())) {
					log.info("----- LLD RETENCION -----");
					libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleRetencion(), null, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION, documentoSunat.getIntParaTipoComprobante()));
//					printLibroDiarioDetalle(obtenerLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleRetencion(), null, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION, documentoSunat.getIntParaTipoComprobante()));
					log.info("----- LLD TOTAL GENERAL -----");
					libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
//					printLibroDiarioDetalle(obtenerLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
				}else { 
					log.info("----- LLD TOTAL GENERAL -----");
					libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL, documentoSunat.getIntParaTipoComprobante()));
//					printLibroDiarioDetalle(obtenerLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL, documentoSunat.getIntParaTipoComprobante()));
				}
			}else {
				if (documentoSunat.getIntIndicadorIGV()!=null && documentoSunat.getIntIndicadorIGV().equals(1)) {
					blnIGVContable = true;
					documentoSunat.getDetalleIGV().setIntAfectoIGV(1);
					log.info("----- LLD SUNAT -----");
					//En caso de tener monto en detalleOtros, sumarselo al IGV (OBS 28.10.2014)
					if (detalleValido(documentoSunat.getDetalleOtros())) {
						documentoSunat.getDetalleIGV().setBdMontoTotal(documentoSunat.getDetalleIGV().getBdMontoTotal().add(documentoSunat.getDetalleOtros().getBdMontoTotal()));
						libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleIGV(), null, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV, documentoSunat.getIntParaTipoComprobante()));
//						printLibroDiarioDetalle(obtenerLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleIGV(), null, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV, documentoSunat.getIntParaTipoComprobante()));
						documentoSunat.getDetalleIGV().setBdMontoTotal(documentoSunat.getDetalleIGV().getBdMontoTotal().subtract(documentoSunat.getDetalleOtros().getBdMontoTotal()));
					}else {
						libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleIGV(), null, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV, documentoSunat.getIntParaTipoComprobante()));
//						printLibroDiarioDetalle(obtenerLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleIGV(), null, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV, documentoSunat.getIntParaTipoComprobante()));
					}
					
					log.info("----- LLD TOTAL GENERAL -----");
					libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
//					printLibroDiarioDetalle(obtenerLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
				}else{
					log.info("----- LLD TOTAL GENERAL -----");
					libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
//					printLibroDiarioDetalle(obtenerLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
				}
			}
			
			BigDecimal bdMontoSinIgv = BigDecimal.ZERO;
			BigDecimal bdMontoTotal = BigDecimal.ZERO;
			DocumentoSunatDetalle docSunDetAnt = null; 
			List<DocumentoSunatDetalle> lstDocSunatDetalleTemp = new ArrayList<DocumentoSunatDetalle>();
			for (int i = 0; i < documentoSunat.getListaDocumentoSunatDetalle().size(); i++) {
				DocumentoSunatDetalle docSunDet = documentoSunat.getListaDocumentoSunatDetalle().get(i);
				
				if (i==0) {
					bdMontoSinIgv = bdMontoSinIgv.add(docSunDet.getBdMontoSinIGV());
					bdMontoTotal = bdMontoTotal.add(docSunDet.getBdMontoTotal());
					docSunDetAnt = documentoSunat.getListaDocumentoSunatDetalle().get(i);
				}else {
					if (docSunDetAnt.getIntSucuIdSucursal().equals(docSunDet.getIntSucuIdSucursal())
							&& docSunDetAnt.getIntSudeIdSubsucursal().equals(docSunDet.getIntSudeIdSubsucursal())) {
						bdMontoSinIgv = bdMontoSinIgv.add(docSunDet.getBdMontoSinIGV());
						bdMontoTotal = bdMontoTotal.add(docSunDet.getBdMontoTotal());
						docSunDetAnt = documentoSunat.getListaDocumentoSunatDetalle().get(i);
					}else {
						docSunDetAnt.setBdMontoGrabarEnLibro(blnIGVContable?bdMontoSinIgv:bdMontoTotal);
						lstDocSunatDetalleTemp.add(docSunDetAnt);
						bdMontoSinIgv = BigDecimal.ZERO;
						bdMontoSinIgv = bdMontoSinIgv.add(docSunDet.getBdMontoSinIGV());
						bdMontoTotal = BigDecimal.ZERO;
						bdMontoTotal = bdMontoTotal.add(docSunDet.getBdMontoTotal());
					}
				}
				if (i==documentoSunat.getListaDocumentoSunatDetalle().size()-1) {
					docSunDet.setBdMontoGrabarEnLibro(blnIGVContable?bdMontoSinIgv:bdMontoTotal);
					lstDocSunatDetalleTemp.add(docSunDet);
				}
			}
			for (DocumentoSunatDetalle docSunatDetalle : lstDocSunatDetalleTemp) {
				log.info("----- ORDEN DE COMPRA -----");
				libroDiarioDetalleTemp.add(generarLibroDiarioDetalleOrdenCompra(documentoSunat, docSunatDetalle));	
//				printLibroDiarioDetalle(generarLibroDiarioDetalleOrdenCompra(documentoSunat, docSunatDetalle));
			}
			
			libroDiario.setListaLibroDiarioDetalle(libroDiarioDetalleTemp);
		} catch (Exception e) {
			log.info(e.getMessage());
		}		
		return libroDiario;
	}
	
//	private boolean validarTipoComprobante(DocumentoSunat documentoSunat, ModeloDetalle modeloDetalle){
//		List<ModeloDetalleNivel> listaValidarTipoComprobante = new ArrayList<ModeloDetalleNivel>();
//		for(ModeloDetalleNivel modeloDetalleNivel : modeloDetalle.getListModeloDetalleNivel())
//			if(modeloDetalleNivel.getIntDatoTablas().equals(Constante.PARAM_T_TIPOCOMPROBANTE_))
//				listaValidarTipoComprobante.add(modeloDetalleNivel);
//		
//		for(ModeloDetalleNivel modeloDetalleNivel : listaValidarTipoComprobante)
//			if(documentoSunat.getIntParaTipoComprobante().equals(modeloDetalleNivel.getIntDatoArgumento()))
//				return Boolean.TRUE;
//		
//		return Boolean.FALSE;
//	}
	
	private DocumentoSunatDetalle grabarDocumentoSunatDetalleLetra(DocumentoSunat documentoSunat, DocumentoSunatDetalle detalleProcesa)throws Exception{
		DocumentoSunatDetalle docSunatDet = new DocumentoSunatDetalle();
		TipoCambio tipoCambio = null;
		try {
			GeneralFacadeRemote generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			
//			docSunatDet.setId(new DocumentoSunatDetalleId());
			docSunatDet.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
			docSunatDet.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
			
			docSunatDet.setIntParaTipoCptoDocumentoSunat(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_LETRA);		
			docSunatDet.setIntPersEmpresaOrdenCompra(null);
			docSunatDet.setIntItemOrdenCompra(null);
			docSunatDet.setIntItemOrdenCompraDetalle(null);
			docSunatDet.setIntAfectoIGV(null);
			docSunatDet.setIntParaTipoMoneda(documentoSunat.getIntParaMoneda());
			
			if (documentoSunat.getIntParaMoneda().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) {
				docSunatDet.setBdMontoSinTipoCambio(null);
			}else {
				tipoCambio = generalFacade.getTipoCambioPorMonedaYFecha(documentoSunat.getIntParaMoneda(), documentoSunat.getDtFechaEmision(), documentoSunat.getId().getIntPersEmpresa());
				docSunatDet.setBdMontoSinTipoCambio(detalleProcesa.getBdMontoSinTipoCambio().multiply(tipoCambio.getBdPromedio()));
			}
			
			docSunatDet.setBdMontoTotal(detalleProcesa.getBdMontoSinTipoCambio());
			docSunatDet.setStrDescripcion(detalleProcesa.getStrDescripcion());
			docSunatDet.setIntSucuIdSucursal(detalleProcesa.getIntSucuIdSucursal());
			docSunatDet.setIntSudeIdSubsucursal(detalleProcesa.getIntSudeIdSubsucursal());
			docSunatDet.setIntIdArea(detalleProcesa.getIntIdArea());
			docSunatDet.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			docSunatDet.setIntPersEmpresaCuenta(null);
			docSunatDet.setIntContPeriodoCuenta(null);
			docSunatDet.setStrContNumeroCuenta(null);
			
			docSunatDet = boDocumentoSunatDetalle.grabar(docSunatDet);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return docSunatDet;
	}
	/**
	 * Autor: jchavez / Tarea: Modificacion / Fecha: 27.10.2014
	 * Funcionalidad: Realiza la generación del Documento Sunat Detalle y su posterior grabación
	 * @param documentoSunat
	 * @param documentoSunatDetalle
	 * @param detalleProcesa
	 * @param intTipoDocumentoSunat
	 * @param ordenCompra
	 * @throws Exception
	 */
	private void grabarDocumentoSunatDetalle(DocumentoSunat documentoSunat, DocumentoSunatDetalle detalleProcesa, 
			Integer intTipoDocumentoSunat)throws Exception{
		
		try {
			if (intTipoDocumentoSunat.equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_SUBTOTAL)) {
				if (documentoSunat.getListaDocumentoSunatDetalle()!=null && !documentoSunat.getListaDocumentoSunatDetalle().isEmpty()) {
					for (DocumentoSunatDetalle o : documentoSunat.getListaDocumentoSunatDetalle()) {
						DocumentoSunatDetalle docSunDet = generarDocumentoSunatDetalle(documentoSunat, o, null, o.getOrdenCompraDetalle(), intTipoDocumentoSunat);
						log.info("---------- GRABACION DOCUMENTO SUNAT DETALLE "+intTipoDocumentoSunat+"+ ----------");
//						log.info("PARA_TIPOCPTODOCSUNAT_N_COD: "+docSunDet.getIntParaTipoCptoDocumentoSunat());
//						log.info("PERS_EMPRESAORDEN_N_PK: "+docSunDet.getIntPersEmpresaOrdenCompra());
//						log.info("TESO_ITEMORDENCOMPRA_N: "+docSunDet.getIntItemOrdenCompra());
//						log.info("TESO_ITEMORDENCOMPRADET_N: "+docSunDet.getIntItemOrdenCompraDetalle());
//						log.info("ORCD_AFECTOIGV_N: "+docSunDet.getIntAfectoIGV());
//						log.info("PARA_TIPOMONEDA_N_COD: "+docSunDet.getIntParaTipoMoneda());
//						log.info("ORCD_MONTOSINTIPOCAMBIO_N: "+docSunDet.getBdMontoSinTipoCambio());
//						log.info("ORCD_MONTOTOTAL_N: "+docSunDet.getBdMontoTotal());
//						log.info("ORCD_DESCRIPCION_V: "+docSunDet.getStrDescripcion());
//						log.info("SUCU_IDSUCURSAL_N: "+docSunDet.getIntSucuIdSucursal());
//						log.info("SUDE_IDSUBSUCURSAL_N: "+docSunDet.getIntSudeIdSubsucursal());
//						log.info("AREA_IDAREA_N: "+docSunDet.getIntIdArea());
//						log.info("PARA_ESTADO_N_COD: "+docSunDet.getIntParaEstado());
//						log.info("PERS_EMPRESACUENTA_N_PK: "+docSunDet.getIntPersEmpresaCuenta());
//						log.info("CONT_PERIODOCUENTA_N: "+docSunDet.getIntContPeriodoCuenta());	
//						log.info("CONT_NUMEROCUENTA_V: "+docSunDet.getStrContNumeroCuenta());
//						log.info("-------------------------------------------------------");
						if(detalleValido(docSunDet)) boDocumentoSunatDetalle.grabar(docSunDet);
					}
				}
			}else{
				DocumentoSunatDetalle docSunDet = generarDocumentoSunatDetalle(documentoSunat, null, detalleProcesa, null, intTipoDocumentoSunat);
				log.info("---------- GRABACION DOCUMENTO SUNAT DETALLE "+intTipoDocumentoSunat+"+ ----------");
//				log.info("PARA_TIPOCPTODOCSUNAT_N_COD: "+docSunDet.getIntParaTipoCptoDocumentoSunat());
//				log.info("PERS_EMPRESAORDEN_N_PK: "+docSunDet.getIntPersEmpresaOrdenCompra());
//				log.info("TESO_ITEMORDENCOMPRA_N: "+docSunDet.getIntItemOrdenCompra());
//				log.info("TESO_ITEMORDENCOMPRADET_N: "+docSunDet.getIntItemOrdenCompraDetalle());
//				log.info("ORCD_AFECTOIGV_N: "+docSunDet.getIntAfectoIGV());
//				log.info("PARA_TIPOMONEDA_N_COD: "+docSunDet.getIntParaTipoMoneda());
//				log.info("ORCD_MONTOSINTIPOCAMBIO_N: "+docSunDet.getBdMontoSinTipoCambio());
//				log.info("ORCD_MONTOTOTAL_N: "+docSunDet.getBdMontoTotal());
//				log.info("ORCD_DESCRIPCION_V: "+docSunDet.getStrDescripcion());
//				log.info("SUCU_IDSUCURSAL_N: "+docSunDet.getIntSucuIdSucursal());	
//				log.info("SUDE_IDSUBSUCURSAL_N: "+docSunDet.getIntSudeIdSubsucursal());
//				log.info("AREA_IDAREA_N: "+docSunDet.getIntIdArea());
//				log.info("PARA_ESTADO_N_COD: "+docSunDet.getIntParaEstado());
//				log.info("PERS_EMPRESACUENTA_N_PK: "+docSunDet.getIntPersEmpresaCuenta());
//				log.info("CONT_PERIODOCUENTA_N: "+docSunDet.getIntContPeriodoCuenta());
//				log.info("CONT_NUMEROCUENTA_V: "+docSunDet.getStrContNumeroCuenta());
//				log.info("-------------------------------------------------------");
				if(detalleValido(docSunDet)) boDocumentoSunatDetalle.grabar(docSunDet);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	
	/**
	 * Autor: jchavez / Tarea: Creación / Fecha: 28.11.2014
	 * Funcionalidad: Se realiza la generación y grabación del DocumentoSunatDoc (Caso Detracción y Percepción).
	 * @param documentoSunat
	 * @param documentoProcesa
	 * @param intTipoDocumentoGral
	 * @return
	 * @throws Exception
	 */
	private DocumentoSunatDoc grabarDocumentoSunatDoc(DocumentoSunat documentoSunat, DocumentoSunatDoc documentoProcesa, Integer intTipoDocumentoGral)throws Exception{
		DocumentoSunatDoc docSunatDoc = new DocumentoSunatDoc();
		try {
			//GENERACION DEL OBJETO...
			docSunatDoc.setId(new DocumentoSunatDocId());
			docSunatDoc.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
			docSunatDoc.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
			docSunatDoc.setBdMontoDocumento(documentoProcesa.getBdMontoDocumento());	
			docSunatDoc.setIntParaTipoDocumentoGeneral(intTipoDocumentoGral);
			docSunatDoc.setTsFechaRegistro(MyUtil.obtenerFechaActual());	
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 7);
			docSunatDoc.setDtFechaVencimiento(new Date(cal.getTimeInMillis()));				
			docSunatDoc.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			docSunatDoc.setIntParaEstadoPagoDocSunat(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);	
//			docSunatDoc.setIntPersEmpresaEgresoDocSunat(null);	
//			docSunatDoc.setIntItemEgresoGeneralDocSuant(null);
//			docSunatDoc.setIntParaTipo(null);				
//			docSunatDoc.setIntItemArchivo(null);			
//			docSunatDoc.setIntItemHistorico(null);		
//			docSunatDoc.setIntParaEstadoPago(null);		
//			docSunatDoc.setIntPersEmpresaEgreso(null);	
//			docSunatDoc.setIntItemEgresoGeneral(null);	
			docSunatDoc.setIntPersEmpresaUsuario(documentoSunat.getIntPersEmpresaUsuario());		
			docSunatDoc.setIntPersPersonaUsuario(documentoSunat.getIntPersPersonaUsuario());			
//			docSunatDoc.setIntPersEmpresaAnula(null);		
//			docSunatDoc.setIntPersPersonaAnula(null);			
//			docSunatDoc.setTsFechaAnula(null);
//			docSunatDoc.setIntPersEmpresaCanjea(null);	
//			docSunatDoc.setIntPersPersonaCanjea(null);			
//			docSunatDoc.setStrObservacionCanjea(null);			
//			docSunatDoc.setTsFechaCanjea(null);			
//			docSunatDoc.setIntParaTipoCanjea(null);		
//			docSunatDoc.setIntItemArchivoCanjea(null);	
//			docSunatDoc.setIntItemHistoricoCanjea(null);		
//			docSunatDoc.setIntPersEmpresaIngreso(null);		
//			docSunatDoc.setIntItemIngresoGeneral(null);
			log.info("---------- GRABACION DOCUMENTO SUNAT DOC ----------");
			log.info(docSunatDoc.getId().getIntPersEmpresa());
			log.info(docSunatDoc.getId().getIntItemDocumentoSunat());
			log.info("PARA_TIPODOCUMENTOGENERAL_N_CO: "+docSunatDoc.getIntParaTipoDocumentoGeneral());
			log.info("DOSD_MONTODOCUMENTO_N: "+docSunatDoc.getBdMontoDocumento());
			log.info("DOSD_FECHAREGISTRO_D: "+docSunatDoc.getTsFechaRegistro());
			log.info("DOSD_FECHAVENCIMIENTO_D: "+docSunatDoc.getDtFechaVencimiento());
			log.info("PARA_ESTADO_N_COD: "+docSunatDoc.getIntParaEstado());
			log.info("PARA_ESTADOPAGODS_N_COD: "+docSunatDoc.getIntParaEstadoPagoDocSunat());
			log.info("PERS_EMPRESAUSUARIO_N_PK: "+docSunatDoc.getIntPersEmpresaUsuario());
			log.info("PERS_PERSONAUSUARIO_N_PK: "+docSunatDoc.getIntPersPersonaUsuario());
			log.info("-------------------------------------------------------");
			docSunatDoc = boDocumentoSunatDoc.grabar(docSunatDoc);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return docSunatDoc;
	}
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 27.10.2014
	 * Funcionalidad: Realiza la generación del Documento Sunat Detalle segun tipo de documento
	 * @param documentoSunat
	 * @param documentoSunatDetalle
	 * @param detalleProcesa
	 * @param ordenComDet
	 * @param intTipoDocumentoSunat
	 * @return
	 */
	public DocumentoSunatDetalle generarDocumentoSunatDetalle(DocumentoSunat documentoSunat, DocumentoSunatDetalle documentoSunatDetalle, 
			DocumentoSunatDetalle detalleProcesa ,OrdenCompraDetalle ordenComDet, Integer intTipoDocumentoSunat){
		DocumentoSunatDetalle docSunatDet = new DocumentoSunatDetalle();
		try {			
			docSunatDet.setId(new DocumentoSunatDetalleId());
			docSunatDet.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
			docSunatDet.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
			
			docSunatDet.setIntParaTipoCptoDocumentoSunat(intTipoDocumentoSunat);		
			docSunatDet.setIntPersEmpresaOrdenCompra(ordenComDet!=null?ordenComDet.getId().getIntPersEmpresa():null);
			docSunatDet.setIntItemOrdenCompra(ordenComDet!=null?ordenComDet.getId().getIntItemOrdenCompra():null);
			docSunatDet.setIntItemOrdenCompraDetalle(ordenComDet!=null?ordenComDet.getId().getIntItemOrdenCompraDetalle():null);
			docSunatDet.setIntAfectoIGV(ordenComDet!=null?ordenComDet.getIntAfectoIGV():null);
			docSunatDet.setIntParaTipoMoneda(documentoSunatDetalle!=null?documentoSunatDetalle.getIntParaTipoMoneda():detalleProcesa.getIntParaTipoMoneda());
			docSunatDet.setBdMontoSinTipoCambio(null);
			docSunatDet.setBdMontoTotal(documentoSunatDetalle!=null?documentoSunatDetalle.getBdMontoSinIGV():detalleProcesa.getBdMontoTotal());
			docSunatDet.setStrDescripcion(ordenComDet!=null?ordenComDet.getStrDescripcion():null);
			docSunatDet.setIntSucuIdSucursal(ordenComDet!=null?ordenComDet.getIntSucuIdSucursal():detalleProcesa.getIntSucuIdSucursal());
			docSunatDet.setIntSudeIdSubsucursal(ordenComDet!=null?ordenComDet.getIntSudeIdSubsucursal():detalleProcesa.getIntSudeIdSubsucursal());
			docSunatDet.setIntIdArea(ordenComDet!=null?ordenComDet.getIntIdArea():null);
			docSunatDet.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			docSunatDet.setIntPersEmpresaCuenta(ordenComDet!=null?ordenComDet.getIntPersEmpresaCuenta():null);
			docSunatDet.setIntContPeriodoCuenta(ordenComDet!=null?ordenComDet.getIntContPeriodoCuenta():null);
			docSunatDet.setStrContNumeroCuenta(ordenComDet!=null?ordenComDet.getStrContNumeroCuenta():null);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return docSunatDet;
	}
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 20.10.2014
	 * Funcionalidad: Se carga objeto para mostrar en la grilla tras la busqueda de DocumentoSunat
	 * @param documentoSunatFiltro
	 * @param listaPersona
	 * @return
	 * @throws BusinessException
	 */
	public List<DocumentoSunat> buscarDocumentoSunat(DocumentoSunat documentoSunatFiltro, List<Persona> listaPersona) throws BusinessException{
		List<DocumentoSunat> listaDocumentoSunat = new ArrayList<DocumentoSunat>();
		List<DocumentoSunat> listaDocumentoSunatTmp = null;
		/* 
		 * Objetos cargados: DocumentoSunat - OrdenCompra - PersonaProveedor, OrdenCompraDetalle
		 * 									- DocumentoSunatDetalle
		 * 									
		 */
		try{
			log.info(documentoSunatFiltro);
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			listaDocumentoSunatTmp = boDocumentoSunat.getPorBuscar(documentoSunatFiltro);
			
			for(DocumentoSunat documentoSunat : listaDocumentoSunatTmp){
				if (!(documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_LETRA_CAMBIO))) {
					documentoSunat.setOrdenCompra(boOrdenCompra.getPorDocumentoSunat(documentoSunat));
					Persona proveedor = personaFacade.devolverPersonaCargada(documentoSunat.getOrdenCompra().getIntPersPersonaProveedor());
					documentoSunat.getOrdenCompra().setPersonaProveedor(proveedor);
					listaDocumentoSunat.add(documentoSunat);
				}				
			}
			
			if(listaPersona!=null  && !listaPersona.isEmpty()){
				List<DocumentoSunat> listaTemp = new ArrayList<DocumentoSunat>();
				for(DocumentoSunat documentoSunat : listaDocumentoSunat){
					for(Persona persona : listaPersona){
						if(documentoSunat.getOrdenCompra().getIntPersPersonaProveedor().equals(persona.getIntIdPersona())){
							listaTemp.add(documentoSunat);
							break;
						}
					}
				}
				listaDocumentoSunat = listaTemp;
			}
			
			for(DocumentoSunat documentoSunat : listaDocumentoSunat){
				documentoSunat.setListaDocumentoSunatDetalle(boDocumentoSunatDetalle.getPorDocumentoSunat(documentoSunat));
				List<DocumentoSunatDetalle> listaDocumentoSunatDetalle = new ArrayList<DocumentoSunatDetalle>();
				List<DocumentoSunatDetalle> listaDocumentoSunatDetalleSubTotal = new ArrayList<DocumentoSunatDetalle>();
				List<DocumentoSunatLetra> listaDocumentoSunatLetra = new ArrayList<DocumentoSunatLetra>();
				for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunat.getListaDocumentoSunatDetalle()){
					if(documentoSunatDetalle.getIntParaTipoCptoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DESCUENTO)){
						documentoSunat.setDetalleDescuento(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoCptoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_VALORVENTA)){
						documentoSunat.setDetalleValorVenta(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoCptoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV)){
						documentoSunat.setDetalleIGV(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoCptoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_OTROS)){
						documentoSunat.setDetalleOtros(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoCptoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL)){
						documentoSunat.setDetalleTotal(documentoSunatDetalle);
//					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION)){
//						documentoSunat.setDetallePercepcion(documentoSunatDetalle);
//					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION)){
//						documentoSunat.setDetalleDetraccion(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoCptoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION)){
						documentoSunat.setDetalleRetencion(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoCptoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL)){
						documentoSunat.setDetalleTotalGeneral(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoCptoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_SUBTOTAL)){
						listaDocumentoSunatDetalleSubTotal.add(documentoSunatDetalle);
//					}else if(documentoSunatDetalle.getIntParaTipoCptoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PRODUCTO)){
//						listaDocumentoSunatDetalle.add(documentoSunatDetalle);
//					}else if (documentoSunatDetalle.getIntParaTipoCptoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_LETRA)) {
//						listaDocumentoSunatDetalleLetra.add(documentoSunatDetalle);
					}
				}
				
				if(documentoSunat.getDetalleDescuento()==null)	documentoSunat.setDetalleDescuento(new DocumentoSunatDetalle());				
				if(documentoSunat.getDetalleValorVenta()==null)	documentoSunat.setDetalleValorVenta(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleIGV()==null)		documentoSunat.setDetalleIGV(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleOtros()==null)		documentoSunat.setDetalleOtros(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleTotal()==null)		documentoSunat.setDetalleTotal(new DocumentoSunatDetalle());
				//Autor: jchavez / Tarea: Creacion / Fecha: 30.10.2014
				if(documentoSunat.getDocPercepcion()==null)		documentoSunat.setDocPercepcion(new DocumentoSunatDoc());
				if(documentoSunat.getDocDetraccion()==null)		documentoSunat.setDocDetraccion(new DocumentoSunatDoc());
				//Fin jchavez - 31.10.2014
				if(documentoSunat.getDetalleRetencion()==null)	documentoSunat.setDetalleRetencion(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleTotalGeneral()==null)documentoSunat.setDetalleTotalGeneral(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleSubTotal()==null)	documentoSunat.setDetalleSubTotal(new DocumentoSunatDetalle());
				
				documentoSunat.setListaDocumentoSunatDetalle(listaDocumentoSunatDetalle);
//				documentoSunat.setListaDocumentoSunatDetalleLetra(listaDocumentoSunatDetalleLetra);
				
				documentoSunat.getOrdenCompra().setListaOrdenCompraDetalle(
						boOrdenCompraDetalle.getPorOrdenCompra(documentoSunat.getOrdenCompra()));
				documentoSunat.setIntParaMoneda(documentoSunat.getOrdenCompra().getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());
				
				documentoSunat.setListaDocumentoSunatLetra(new ArrayList<DocumentoSunat>());
				
				listaDocumentoSunatLetra = boDocumentoSunatLetra.getListaPorDocumentoSunat(documentoSunat);
				if (listaDocumentoSunatLetra!=null && !listaDocumentoSunatLetra.isEmpty()) {
					for (DocumentoSunatLetra documentoSunatLetra : listaDocumentoSunatLetra) {
						DocumentoSunat pId = new DocumentoSunat();
						pId.getId().setIntPersEmpresa(documentoSunatLetra.getIntPersEmpresaDocSunatLetra());
						pId.getId().setIntItemDocumentoSunat(documentoSunatLetra.getIntItemDocSunatLetraEnlazada());
						documentoSunat.getListaDocumentoSunatLetra().add(boDocumentoSunat.getPorPk(pId.getId()));
					}
				}
//				documentoSunat.setListaDocumentoSunatLetra();
				for(DocumentoSunat documentoSunatLetra : documentoSunat.getListaDocumentoSunatLetra()){
					documentoSunatLetra.setDetalleLetra(boDocumentoSunatDetalle.getPorDocumentoSunat(documentoSunatLetra).get(0));
				}
				
				if(documentoSunat.getIntIndicadorInafecto().equals(Constante.OPCION_SELECCIONADA))
					documentoSunat.setSeleccionaInafecto(Boolean.TRUE);
				else
					documentoSunat.setSeleccionaInafecto(Boolean.FALSE);
				
				if(documentoSunat.getIntIndicadorIGV().equals(Constante.OPCION_SELECCIONADA))
					documentoSunat.setSeleccionaIGVContable(Boolean.TRUE);
				else
					documentoSunat.setSeleccionaIGVContable(Boolean.FALSE);
				
				if(documentoSunat.getIntIndicadorPercepcion().equals(Constante.OPCION_SELECCIONADA))
					documentoSunat.setSeleccionaPercepcion(Boolean.TRUE);
				else
					documentoSunat.setSeleccionaPercepcion(Boolean.FALSE);
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaDocumentoSunat;
	}
	
	//Agregado por cdelosrios, 01/11/2013
	/*
	public DocumentoSunat modificarDocumentoSunat(DocumentoSunat documentoSunat ) throws BusinessException{
		try{
			documentoSunat = boDocumentoSunat.modificar(documentoSunat);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return documentoSunat;
	}*/
	public List<DocumentoSunat> modificarDocumentoSunat(List<DocumentoSunat> listaDocumentoSunat, OrdenCompra ordenCompra, List<DocumentoSunat> listaDocSunatLetra) throws BusinessException{
//		DocumentoSunatDoc documentoSunatDoc = null;
//		try{
//			LibroDiarioFacadeRemote libroDiarioFacade =  (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
//			
//			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
//				log.info(ordenCompraDetalle);
//				boOrdenCompraDetalle.modificar(ordenCompraDetalle);
//			}
//			
//			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
//				log.info(ordenCompraDocumento);
//				boOrdenCompraDocumento.modificar(ordenCompraDocumento);
//			}
//			
//			for(DocumentoSunat documentoSunat : listaDocumentoSunat){
//				if(documentoSunat.getId().getIntItemDocumentoSunat()!=null){ // Si es modificar
//					//Agregado por cdelosrios, 18/11/2013
//					if(listaDocSunatLetra!=null && !listaDocSunatLetra.isEmpty()){
//						documentoSunat.setListaDocumentoSunatLetra(listaDocSunatLetra);
//						documentoSunat.setOrdenCompra(ordenCompra);
//						agregarDocumentoSunatLetra(documentoSunat);
//					}
//					//Fin agregado por cdelosrios, 18/11/2013
//				} else { //Si es Nuevo
//					LibroDiario libroDiario = procesarLibroDiario(documentoSunat);
//					libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
//					
//					documentoSunat.setIntEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
//					documentoSunat.setIntPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
//					documentoSunat.setIntCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
//					log.info(documentoSunat);
//					documentoSunat = boDocumentoSunat.grabar(documentoSunat);
//					
//					for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunat.getListaDocumentoSunatDetalle()){
//						grabarDocumentoSunatDetalle(documentoSunat, documentoSunatDetalle, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PRODUCTO);
//					}
//					
//					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleSubTotal(), 
//								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_SUBTOTAL);				
//					
//					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleDescuento(), 
//								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DESCUENTO);
//					
//					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleValorVenta(), 
//								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_VALORVENTA);
//					
//					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleIGV(), 
//								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV);
//					
//					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleOtros(), 
//								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_OTROS);
//					
//					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleTotal(), 
//								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL);
//					
//					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetallePercepcion(), 
//								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION);
//					
//					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleDetraccion(), 
//								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION);
//					
//					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleRetencion(), 
//								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION);
//					
//					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(),
//								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL);
//					
//					//Agregado por cdelosrios, 01/11/2013
//					if(documentoSunat.getDetallePercepcion().getBdMontoTotal()!=null
//							&& documentoSunat.getDetallePercepcion().getBdMontoTotal().compareTo(BigDecimal.ZERO)>0){
//						documentoSunatDoc = new DocumentoSunatDoc();
//						documentoSunatDoc.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
//						documentoSunatDoc.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
//						documentoSunatDoc.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION);
//						documentoSunatDoc.setBdMontoDocumento(documentoSunat.getDetallePercepcion().getBdMontoTotal());
//						documentoSunatDoc.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
//						documentoSunatDoc.setIntPersEmpresaPago(documentoSunat.getId().getIntPersEmpresa());
//						boDocumentoSunatDoc.grabar(documentoSunatDoc);
//					}
//					
//					if(documentoSunat.getDetalleDetraccion().getBdMontoTotal()!=null
//							&& documentoSunat.getDetalleDetraccion().getBdMontoTotal().compareTo(BigDecimal.ZERO)>0){
//						documentoSunatDoc = new DocumentoSunatDoc();
//						documentoSunatDoc.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
//						documentoSunatDoc.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
//						documentoSunatDoc.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION);
//						documentoSunatDoc.setBdMontoDocumento(documentoSunat.getDetalleDetraccion().getBdMontoTotal());
//						documentoSunatDoc.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
//						documentoSunatDoc.setIntPersEmpresaPago(documentoSunat.getId().getIntPersEmpresa());
//						boDocumentoSunatDoc.grabar(documentoSunatDoc);
//					}
//					//Fin agregado por cdelosrios, 01/11/2013
//					
//					for(AdelantoSunat adelantoSunat : documentoSunat.getListaAdelantoSunat()){
//						adelantoSunat.setDocumentoSunatId(documentoSunat.getId());
//						log.info(adelantoSunat);
//						boAdelantoSunat.grabar(adelantoSunat);
//					}
//				}
//			}
//			
//		}catch(BusinessException e){
//			throw e;
//		}catch(Exception e){
//			throw new BusinessException(e);
//		}
		return listaDocumentoSunat;
	}
	//Fin agregado por cdelosrios, 01/11/2013
	
	public List<DocumentoSunat> buscarParaGiroDesdeFondo(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		List<DocumentoSunat> listaDocumentoSunat = new ArrayList<DocumentoSunat>();
		try{
			DocumentoSunat documentoSunatFiltro = new DocumentoSunat();
			documentoSunatFiltro.getId().setIntPersEmpresa(intIdEmpresa);
			documentoSunatFiltro.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);
			documentoSunatFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			List<Persona> listaPersona = new ArrayList<Persona>();
			Persona persona = new Persona();
			persona.setIntIdPersona(intIdPersona);
			listaPersona.add(persona);
			
			List<DocumentoSunat> listaDocumentoSunatTemp = buscarDocumentoSunat(documentoSunatFiltro, listaPersona);
			
			//No se toman en cuenta aquellos documentoSunatTemp que poseen Letras o ya esten asociados a un egreso
			for(DocumentoSunat documentoSunatTemp : listaDocumentoSunatTemp)
				if((documentoSunatTemp.getListaDocumentoSunatLetra()==null 
				|| documentoSunatTemp.getListaDocumentoSunatLetra().isEmpty())
				&& documentoSunatTemp.getIntItemEgresoGeneral()==null)
					listaDocumentoSunat.add(documentoSunatTemp);
					
			for(DocumentoSunat documentoSunatTemp : listaDocumentoSunat)
				for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunatTemp.getListaDocumentoSunatDetalle())
					documentoSunatDetalle.setOrdenCompraDetalle(boOrdenCompraDetalle.getPorDocumentoSunatDetalle(documentoSunatDetalle));
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaDocumentoSunat;
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 06.11.2014
	 * Funcionalidad: Genera y graba la Letra de Cambio.
	 * @param documentoSunat
	 * @return
	 * @throws BusinessException
	 */
	public DocumentoSunat agregarDocumentoSunatLetra(DocumentoSunat documentoSunat) throws BusinessException{
		try{
			LibroDiarioFacadeRemote libroDiarioFacade =  (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			
			for(DocumentoSunat documentoSunatLetra : documentoSunat.getListaDocumentoSunatLetra()){
				if(documentoSunatLetra.getId().getIntItemDocumentoSunat()!=null) continue;
					
//				documentoSunatLetra.getId().getIntPersEmpresa();
//				documentoSunatLetra.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//				documentoSunatLetra.setOrdenCompra(documentoSunat.getOrdenCompra());
//				documentoSunatLetra.setIntPersEmpresaOrden(documentoSunat.getOrdenCompra().getId().getIntPersEmpresa());
//				documentoSunatLetra.setIntItemOrdenCompra(documentoSunat.getOrdenCompra().getId().getIntItemOrdenCompra());

				LibroDiario libroDiario = procesarLibroDiarioLetraDeCambio(documentoSunatLetra, documentoSunatLetra.getOrdenCompra());
				libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
				
				documentoSunatLetra.setIntEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
				documentoSunatLetra.setIntPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
				documentoSunatLetra.setIntCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
				//GRABACION DOCUMENTO SUNAT...
				log.info(documentoSunatLetra);
				documentoSunatLetra = boDocumentoSunat.grabar(documentoSunatLetra);
				//GRABACION DOCUMENTO SUNAT DETALLE...
				DocumentoSunatDetalle documentoSunatDetalleLetra = documentoSunatLetra.getDetalleLetra();
				documentoSunatDetalleLetra = grabarDocumentoSunatDetalleLetra(documentoSunatLetra, documentoSunatDetalleLetra);
				//GRABACION DOCUMENTO SUNAT LETRA...
				if (documentoSunatLetra.getListaDocSunatRelacionadosConLetraDeCambio()!=null && !documentoSunatLetra.getListaDocSunatRelacionadosConLetraDeCambio().isEmpty()) {
					for (DocumentoSunat docSunatRel : documentoSunatLetra.getListaDocSunatRelacionadosConLetraDeCambio()) {
						DocumentoSunatLetra docSunatLetra = new DocumentoSunatLetra();
						docSunatLetra.getId().setIntPersEmpresaDocSunat(docSunatRel.getId().getIntPersEmpresa());
						docSunatLetra.getId().setIntItemDocumentoSunat(docSunatRel.getId().getIntItemDocumentoSunat());
						docSunatLetra.setIntPersEmpresaDocSunatLetra(documentoSunatLetra.getId().getIntPersEmpresa());
						docSunatLetra.setIntItemDocSunatLetraEnlazada(documentoSunatLetra.getId().getIntItemDocumentoSunat());
						docSunatLetra.setBdMontoCancelado(docSunatRel.getBdMontoAplicar());
						if (documentoValido(docSunatRel.getDocPercepcion())) {
							docSunatLetra.setIntItemDocumentoSunatDoc(docSunatRel.getDocPercepcion().getId().getIntItemDocumentoSunatDoc());
						}
						docSunatLetra = boDocumentoSunatLetra.grabar(docSunatLetra);
						//ACTUALIZA DOCUMENTO SUNAT...
						if(documentoSunatLetra.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_LETRA_CAMBIO)){
							DocumentoSunat docSunatTmp = boDocumentoSunat.getPorPk(docSunatRel.getId());
							docSunatTmp.setIntIndicadorLetras(Constante.PARAM_T_INDICADORLETRAS_CONLETRAS);
							docSunatTmp.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
							boDocumentoSunat.modificar(docSunatTmp);
						}
						//ACTUALIZA SUCURSAL DEL DOCUMENTO SUNAT GENERADO CON LA SUCURSAL DEL DOC SUNAT RELACIONADO...
						documentoSunatDetalleLetra.setIntSucuIdSucursal(docSunatRel.getIntSucuIdSucursal());
						documentoSunatDetalleLetra.setIntSudeIdSubsucursal(docSunatRel.getIntSubIdSubsucursal());
						for (LibroDiarioDetalle o : libroDiario.getListaLibroDiarioDetalle()) {
							if (o.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) {
								if (o.getBdDebeSoles()!=null) {
									documentoSunatDetalleLetra.setIntPersEmpresaCuenta(o.getIntPersEmpresaCuenta());
									documentoSunatDetalleLetra.setIntContPeriodoCuenta(o.getIntContPeriodo());
									documentoSunatDetalleLetra.setStrContNumeroCuenta(o.getStrContNumeroCuenta());
									break;
								}
							}else {
								if (o.getBdDebeExtranjero()!=null) {
									documentoSunatDetalleLetra.setIntPersEmpresaCuenta(o.getIntPersEmpresaCuenta());
									documentoSunatDetalleLetra.setIntContPeriodoCuenta(o.getIntContPeriodo());
									documentoSunatDetalleLetra.setStrContNumeroCuenta(o.getStrContNumeroCuenta());
									break;
								}
							}
						}
						documentoSunatDetalleLetra = boDocumentoSunatDetalle.modificar(documentoSunatDetalleLetra);
					}
				}
				
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return documentoSunat;
	}
	
	public DocumentoSunat calcularMontos(DocumentoSunat documentoSunat, TipoCambio tipoCambio) throws BusinessException{
		try{
			log.info("--calcularMontos");			
			Tarifa tarifaIGV = cargarTarifaIGV(documentoSunat);
			//TipoCambio tipoCambio = cargarTipoCambio(documentoSunat);
			
			BigDecimal bdMontoSubtotal = new BigDecimal(0);
			BigDecimal bdMontoIGV = new BigDecimal(0);
			for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunat.getListaDocumentoSunatDetalle()){
				documentoSunatDetalle = calcularIGV(documentoSunatDetalle, tipoCambio, documentoSunat, tarifaIGV);
				bdMontoSubtotal = bdMontoSubtotal.add(documentoSunatDetalle.getBdMontoSinIGV());
				bdMontoIGV = bdMontoIGV.add(documentoSunatDetalle.getBdMontoIGV());
			}
			
			//Agregado por cdelosrios, 25/10/2013
			if(!documentoSunat.isSeleccionaInafecto()){
				documentoSunat.getDetalleIGV().setBdMontoTotal(bdMontoIGV);
			}else{
				bdMontoSubtotal = bdMontoSubtotal.add(bdMontoIGV);
				documentoSunat.getDetalleIGV().setBdMontoTotal(new BigDecimal(0));
			}
			//SUBTOTAL
			documentoSunat.getDetalleSubTotal().setBdMontoTotal(bdMontoSubtotal);			
			
			if(detalleValido(documentoSunat.getDetalleDescuento())){
				documentoSunat.getDetalleValorVenta().setBdMontoTotal(
					documentoSunat.getDetalleSubTotal().getBdMontoTotal().subtract(
					documentoSunat.getDetalleDescuento().getBdMontoTotal()));
			}else{
				documentoSunat.getDetalleValorVenta().setBdMontoTotal(documentoSunat.getDetalleSubTotal().getBdMontoTotal());
			}
			//OTROS
			if(documentoSunat.getDetalleOtros()!=null && documentoSunat.getDetalleOtros().getBdMontoTotal()!=null){
				documentoSunat.getDetalleTotal().setBdMontoTotal(
					documentoSunat.getDetalleValorVenta().getBdMontoTotal().add(
					documentoSunat.getDetalleIGV().getBdMontoTotal().add(
					documentoSunat.getDetalleOtros().getBdMontoTotal())));
			}else{
				documentoSunat.getDetalleTotal().setBdMontoTotal(
					documentoSunat.getDetalleValorVenta().getBdMontoTotal().add(
					documentoSunat.getDetalleIGV().getBdMontoTotal()));
			}
			
			//Agregado por cdelosrios, 07/11/2013
			/*if(documentoSunat.getDetalleValorVenta()!=null 
					&& documentoSunat.getDetalleValorVenta().getBdMontoTotal()!=null
					&& documentoSunat.getDetalleValorVenta().getBdMontoTotal().compareTo(BigDecimal.ZERO)>0
					&& documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA)){
				documentoSunat.getDetalleValorVenta().setBdMontoTotal(
						documentoSunat.getDetalleTotal().getBdMontoTotal().
						divide((tarifaIGV.getBdValor().divide(new BigDecimal(100)).add(BigDecimal.ONE)),
								RoundingMode.HALF_UP));
				documentoSunat.getDetalleIGV().setBdMontoTotal(
						documentoSunat.getDetalleTotal().getBdMontoTotal().subtract(
								documentoSunat.getDetalleValorVenta().getBdMontoTotal()));
			}*/
			//Fin agregado por cdelosrios, 07/11/2013
			
			//PERCEPCION
			BigDecimal bdMontoPercepcion = null;
			if(documentoSunat.isSeleccionaPercepcion() && documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA))
//				bdMontoPercepcion = documentoSunat.getDetalleValorVenta().getBdMontoTotal().multiply(new BigDecimal(0.02));
				bdMontoPercepcion = documentoSunat.getDetalleTotal().getBdMontoTotal().multiply(new BigDecimal(0.02));
			else
				bdMontoPercepcion = new BigDecimal(0);
			
			documentoSunat.getDocPercepcion().setBdMontoDocumento(bdMontoPercepcion);
			
			//DETRACCION
			BigDecimal bdMontoDetraccion = null;
			OrdenCompra ordenCompra = documentoSunat.getOrdenCompra();
			if(ordenCompra.getIntParaTipoDetraccion()!=null 
				&& !ordenCompra.getIntParaTipoDetraccion().equals(new Integer(0))		
				&& documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA)
				//Autor: jchavez / Tarea: Modificacion / Fecha: 27.10.2014
				&& documentoSunat.getDetalleTotal().getBdMontoTotal().compareTo(Constante.MONTO_MAXIMO_DETRACCION)==1
				//Fin jchavez / 27.10.2014
				&& detalleValido(documentoSunat.getDetalleIGV())){
					bdMontoDetraccion = documentoSunat.getDetalleTotal().getBdMontoTotal().multiply(ordenCompra.getDetraccion().
							getBdPorcentaje().divide(new BigDecimal(100)));
					if(bdMontoDetraccion.compareTo(Constante.MONTO_MAXIMO_DETRACCION)>0){
						bdMontoDetraccion = Constante.MONTO_MAXIMO_DETRACCION;
					}
			}else{
				bdMontoDetraccion = new BigDecimal(0);
			}
			documentoSunat.getDocDetraccion().setBdMontoDocumento(bdMontoDetraccion);
			
			//RETENCION
			BigDecimal bdMontoRetencion = null;
			Proveedor proveedor = documentoSunat.getOrdenCompra().getProveedor();
			if(proveedor.getIntRetencionCuarta()!=null 
			&& documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_RECIBOHONORARIOS)){
				if(proveedor.getIntRetencionCuarta().equals(Constante.PARAM_T_RETENCION_NO)){
					bdMontoRetencion = new BigDecimal(0);
				}else if(proveedor.getIntRetencionCuarta().equals(Constante.PARAM_T_RETENCION_SI_CONDICION)
						&& documentoSunat.getDetalleTotal().getBdMontoTotal().compareTo(new BigDecimal(1500))>0){
					bdMontoRetencion = documentoSunat.getDetalleTotal().getBdMontoTotal().multiply(new BigDecimal(0.1));
				}else if(proveedor.getIntRetencionCuarta().equals(Constante.PARAM_T_RETENCION_SI_INDISTINTO)){
					bdMontoRetencion = documentoSunat.getDetalleTotal().getBdMontoTotal().multiply(new BigDecimal(0.1));
				}
			}else{
				bdMontoRetencion = new BigDecimal(0);
			}
			documentoSunat.getDetalleRetencion().setBdMontoTotal(bdMontoRetencion);
			
			//Autor: jchavez / Tarea: nueva validacion / Fecha: 27.10.2014
			//TOTAL GENERAL
			BigDecimal bdMontoTotalGeneral = null;
//			if (documentoSunat.getDocDetraccion().getBdMontoDocumento().compareTo(BigDecimal.ZERO)!=0
//					|| documentoSunat.getDocPercepcion().getBdMontoDocumento().compareTo(BigDecimal.ZERO)!=0) {
//				bdMontoTotalGeneral = documentoSunat.getDetalleTotal().getBdMontoTotal();
//			}
			if (documentoSunat.getDetalleRetencion().getBdMontoTotal().compareTo(BigDecimal.ZERO)!=0) {
				bdMontoTotalGeneral = documentoSunat.getDetalleTotal().getBdMontoTotal().subtract(bdMontoRetencion);
			}else {
				documentoSunat.getDetalleTotalGeneral().setBdMontoTotal(documentoSunat.getDetalleTotal().getBdMontoTotal());
			}
			log.info("MONTO TOTAL GENERAL: "+bdMontoTotalGeneral);
//			BigDecimal bdMontoTotalGeneral = documentoSunat.getDetalleTotal().getBdMontoTotal().
//				add(bdMontoPercepcion).subtract(bdMontoDetraccion).subtract(bdMontoRetencion);			
//			documentoSunat.getDetalleTotalGeneral().setBdMontoTotal(bdMontoTotalGeneral);
			//Fin jchavez - 27.10.2014
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return documentoSunat;
	}

	public DocumentoSunatDetalle calcularIGV(DocumentoSunatDetalle documentoSunatDetalle, TipoCambio tipoCambio, DocumentoSunat documentoSunat,
	Tarifa tarifaIGV) throws Exception{
		try{
			OrdenCompraDetalle ordenCompraDetalle = documentoSunatDetalle.getOrdenCompraDetalle();
			documentoSunatDetalle.setBdMontoTotal(documentoSunatDetalle.getBdMontoSinTipoCambio().multiply(tipoCambio.getBdPromedio()));
			
			BigDecimal bdMontoSinIGV = tarifaIGV.getBdValor().divide(new BigDecimal(100));
			bdMontoSinIGV = bdMontoSinIGV.add(BigDecimal.ONE);
			Double dbMontoSinIGV = documentoSunatDetalle.getBdMontoSinTipoCambio().doubleValue()/bdMontoSinIGV.doubleValue();
			
			if(ordenCompraDetalle.getIntAfectoIGV().equals(new Integer(1))&& !documentoSunat.isSeleccionaInafecto()
					&& (documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA) || documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO))){
				documentoSunatDetalle.setBdMontoSinIGV(MyUtil.round(new BigDecimal(dbMontoSinIGV), 2, true));
				documentoSunatDetalle.setBdMontoIGV(documentoSunatDetalle.getBdMontoSinTipoCambio().subtract(documentoSunatDetalle.getBdMontoSinIGV()));
			}else{
				documentoSunatDetalle.setBdMontoIGV(new BigDecimal(0));
				documentoSunatDetalle.setBdMontoSinIGV(documentoSunatDetalle.getBdMontoSinTipoCambio());
			}

			return documentoSunatDetalle;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public Tarifa cargarTarifaIGV(DocumentoSunat documentoSunat)throws Exception{
		try{
			GeneralFacadeRemote generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			Tarifa tarifaIGV = generalFacade.getTarifaIGV(documentoSunat.getId().getIntPersEmpresa(), documentoSunat.getDtFechaEmision());
			if(tarifaIGV==null){
				tarifaIGV = new Tarifa();
				tarifaIGV.setBdValor(new BigDecimal(0));
			}
			return tarifaIGV;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	//Agregado por cdelosrios, 01/11/2013
	public List<DocumentoSunat> getListaDocumentoSunatPorOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
		List<DocumentoSunat> lista = null;
		try {
			ordenCompra.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			lista = boDocumentoSunat.getPorOrdenCompra(ordenCompra);
			
			if(lista!=null){
				for (DocumentoSunat documentoSunat : lista) {
					DocumentoSunatDetalle detalleSubTotalTemp = new DocumentoSunatDetalle();
					BigDecimal bdMontoSubTotal = BigDecimal.ZERO;
					documentoSunat.setListaDocumentoSunatDetalle(boDocumentoSunatDetalle.getPorDocumentoSunat(documentoSunat));
					//DETALLE SUBTOTAL...
					List<DocumentoSunatDetalle> lstDetalleSubTotal = boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_SUBTOTAL);
					if (lstDetalleSubTotal!=null && !lstDetalleSubTotal.isEmpty()) {
						for (DocumentoSunatDetalle o : lstDetalleSubTotal) {
							bdMontoSubTotal = bdMontoSubTotal.add(o.getBdMontoTotal());
							detalleSubTotalTemp.getListaDetalleSubTotal().add(o);
						}
						detalleSubTotalTemp.setBdMontoTotal(bdMontoSubTotal);
					}
					documentoSunat.setDetalleSubTotal(detalleSubTotalTemp);
					//DETALLE VALOR VENTA...
					List<DocumentoSunatDetalle> lstDetalleValorVenta = boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_VALORVENTA);
					documentoSunat.setDetalleValorVenta(
							(lstDetalleValorVenta!=null && !lstDetalleValorVenta.isEmpty())?lstDetalleValorVenta.get(0):null);
					//DETALLE IGV...
					List<DocumentoSunatDetalle> lstDetalleIGV = boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV);
					documentoSunat.setDetalleIGV(
							(lstDetalleIGV!=null && !lstDetalleIGV.isEmpty())?lstDetalleIGV.get(0):null);
					//DETALLE OTROS...
					List<DocumentoSunatDetalle> lstDetalleOtros = boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_OTROS);
					documentoSunat.setDetalleOtros(
							(lstDetalleOtros!=null && !lstDetalleOtros.isEmpty())?lstDetalleOtros.get(0):null);
					//DETALLE TOTAL...
					List<DocumentoSunatDetalle> lstDetalleTotal = boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL);
					documentoSunat.setDetalleTotal(
							(lstDetalleTotal!=null && !lstDetalleTotal.isEmpty())?lstDetalleTotal.get(0):null);
					//Autor: jchavez / Tarea: Modificacion / Fecha: 27.10.2014
					documentoSunat.setDocPercepcion(boDocumentoSunatDoc.getPorDocumentoSunatYTipoDoc(documentoSunat, Constante.PARAM_T_DOCUMENTOGENERAL_PERCEPCION));
					documentoSunat.setDocDetraccion(boDocumentoSunatDoc.getPorDocumentoSunatYTipoDoc(documentoSunat, Constante.PARAM_T_DOCUMENTOGENERAL_DETRACCION));
//					documentoSunat.setDocPercepcion(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION));
//					documentoSunat.setDocDetraccion(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION));
					//Fin jchavez - 27.10.2014
					//DETALLE RETENCION...
					List<DocumentoSunatDetalle> lstDetalleRetencion = boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION);
					documentoSunat.setDetalleRetencion(
							(lstDetalleRetencion!=null && !lstDetalleRetencion.isEmpty())?lstDetalleRetencion.get(0):null);
					//DETALLE TOTAL GENERAL...
					List<DocumentoSunatDetalle> lstDetalleTotalGeneral = boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL);
					documentoSunat.setDetalleTotalGeneral(
							(lstDetalleTotalGeneral!=null && !lstDetalleTotalGeneral.isEmpty())?lstDetalleTotalGeneral.get(0):null);
					documentoSunat.setIntParaMoneda(ordenCompra.getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());
					//documentoSunat.setListaAdelantoSunat(boAdelantoSunat.getPorOrdenCompraDocumento(ordenCompra));
				}
			}
		} catch(BusinessException e){
   			throw e;
   		} catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
	//Fin agregado por cdelosrios, 01/11/2013
	
	//Agregado por cdelosrios, 04/11/2013
	public DocumentoSunat eliminarDocumentoSunat(DocumentoSunat o) throws BusinessException{
		DocumentoSunat documentoSunat = null;
//		OrdenCompraDetalle ordenCompraDetalle = null;
		try {
			documentoSunat = boDocumentoSunat.modificar(o);
//			if(adelantoSunat!=null){
//				adelantoSunat.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
//				adelantoSunat = boAdelantoSunat.modificar(adelantoSunat);
//				
//				ordenCompraDetalle = new OrdenCompraDetalle();
//				ordenCompraDetalle.getId().setIntItemOrdenCompraDetalle(adelantoSunat.getIntItemOrdenCompraDet());
//				ordenCompraDetalle = boOrdenCompraDetalle.getPorPk(ordenCompraDetalle.getId());
//				if(ordenCompraDetalle!=null){
//					ordenCompraDetalle.setBdMontoSaldo(ordenCompraDetalle.getBdMontoSaldo().add(o.getDetalleTotal().getBdMontoTotal()));
//					ordenCompraDetalle = boOrdenCompraDetalle.modificar(ordenCompraDetalle);
//				}
//			}
			
		} catch(Exception e){
   			throw new BusinessException(e);
   		}
		return documentoSunat;
	}
	//Fin agregado por cdelosrios, 04/11/2013
	
	//Agregado por cdelosrios, 18/11/2013
    public List<DocumentoSunat> getListDocumentoSunatPorOrdenCompraYTipoDocumento(DocumentoSunat documentoSunat) throws BusinessException{
    	List<DocumentoSunat> lista = null;
		try{
			lista = boDocumentoSunat.getListaPorOrdenCompraYTipoDocumento(documentoSunat);
			if(lista!=null && !lista.isEmpty()){
				for (DocumentoSunat documentoSunatLetra : lista) {
					documentoSunatLetra.setDetalleLetra(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunatLetra, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_LETRA).get(0));
					documentoSunatLetra.setIntParaMoneda(documentoSunat.getOrdenCompra().getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());
				}
			}
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
    }
    //Fin agregado por cdelosrios, 18/11/2013
    public LibroDiarioDetalle obtenerLibroDiarioDetalleDocumentoSunat(DocumentoSunat documentoSunat, DocumentoSunatDetalle detalleProcesa, Integer intTipoRequisicion, Modelo modelo, 
    		Integer intTipoCptoDocSunat, Integer intTipoComprobante) throws BusinessException{
    	LibroDiarioDetalle libroDiarioDet = null;
    	Boolean blnTipoCptoDocSunat = false;
    	Boolean blnTipoComprobante = false;
    	Boolean blnTipoRequisicion = false;
    	try {
			for (ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()) {
				if (detalleProcesa.getIntAfectoIGV()!=null && detalleProcesa.getIntAfectoIGV().equals(1)) {
					for (ModeloDetalleNivel mdn : modeloDetalle.getListModeloDetalleNivel()) {
						if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELOCONTABLE_DOCUMENTOSUNAT_INDICADORIGV)) {
							libroDiarioDet = generarLibroDiarioDetalle(documentoSunat, detalleProcesa, modeloDetalle);
							break;
						}
					}
				}else{
					if (intTipoRequisicion!=null) {
						for (ModeloDetalleNivel mdn : modeloDetalle.getListModeloDetalleNivel()) {
							if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELOCONTABLE_DOCUMENTOSUNAT_TIPOREQUISICION)
									&& mdn.getIntValor().equals(intTipoRequisicion)) {
								blnTipoRequisicion = true;
								break;
							}
						}
						if (blnTipoRequisicion) {
							for (ModeloDetalleNivel mdn : modeloDetalle.getListModeloDetalleNivel()) {
								if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELOCONTABLE_DOCUMENTOSUNAT_TIPOCPTODOCSUNAT)
										&& mdn.getIntValor().equals(intTipoCptoDocSunat)) {
									blnTipoCptoDocSunat = true;
									break;
								}
							}
							if (blnTipoCptoDocSunat) {
								for (ModeloDetalleNivel mdn : modeloDetalle.getListModeloDetalleNivel()) {
									if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELOCONTABLE_DOCUMENTOSUNAT_TIPOCOMPROBANTE)
											&& mdn.getIntValor().equals(intTipoComprobante)) {
										blnTipoComprobante = true;
										break;
									}
								}
								if (blnTipoComprobante) {
									libroDiarioDet = generarLibroDiarioDetalle(documentoSunat, detalleProcesa, modeloDetalle);
								}
							}
						}						
					}else {
						for (ModeloDetalleNivel mdn : modeloDetalle.getListModeloDetalleNivel()) {
							if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELOCONTABLE_DOCUMENTOSUNAT_TIPOCPTODOCSUNAT)
									&& mdn.getIntValor().equals(intTipoCptoDocSunat)) {
								blnTipoCptoDocSunat = true;
								break;
							}
						}
						if (blnTipoCptoDocSunat) {
							for (ModeloDetalleNivel mdn : modeloDetalle.getListModeloDetalleNivel()) {
								if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELOCONTABLE_DOCUMENTOSUNAT_TIPOCOMPROBANTE)
										&& mdn.getIntValor().equals(intTipoComprobante)) {
									blnTipoComprobante = true;
									break;
								}
							}
							if (blnTipoComprobante) {
								libroDiarioDet = generarLibroDiarioDetalle(documentoSunat, detalleProcesa, modeloDetalle);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return libroDiarioDet;
    }
    
    public List<LibroDiarioDetalle> obtenerLibroDiarioDetalleLetraDeCambio(DocumentoSunat documentoSunat, DocumentoSunatDetalle detalleProcesa, Integer intTipoRequisicion, Modelo modelo) throws BusinessException{
    	List<LibroDiarioDetalle> listLibroDiarioDet = new ArrayList<LibroDiarioDetalle>();
    	Boolean blnTipoRequisicion = false;
    	try {
			for (ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()) {
				blnTipoRequisicion = false;
				for (ModeloDetalleNivel mdn : modeloDetalle.getListModeloDetalleNivel()) {
					if (mdn.getStrDescripcion().equalsIgnoreCase(Constante.PARAM_T_MODELOCONTABLE_LETRADECAMBIO_TIPOREQUISICION)
							&& mdn.getIntValor().equals(intTipoRequisicion)) {
						blnTipoRequisicion = true;
						break;
					}
				}
				if (blnTipoRequisicion) {
					listLibroDiarioDet.add(generarLibroDiarioDetalle(documentoSunat, detalleProcesa, modeloDetalle));
				}						
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return listLibroDiarioDet;
    }
    
	private LibroDiario procesarLibroDiarioLetraDeCambio(DocumentoSunat documentoSunat, OrdenCompra ordenCompra)throws Exception{
		LibroDiario libroDiario = generarLibroDiario(documentoSunat);
		Integer intParaTipoRequisicion = null;
		try {
			ModeloFacadeRemote modeloFacade =  (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			intParaTipoRequisicion = ordenCompra.getDocumentoRequisicion().getRequisicion().getIntParaTipoRequisicion();
			log.info("TIPO DE REQUISICION: "+intParaTipoRequisicion);
			log.info("moneda: "+documentoSunat.getIntParaMoneda());

			Modelo modelo = modeloFacade.obtenerTipoModeloActual
			(Constante.PARAM_T_MODELOCONTABLE_LETRADECAMBIO, documentoSunat.getId().getIntPersEmpresa()).get(0);
			log.info("MODELO CONTABLE: "+modelo);

			libroDiario.getListaLibroDiarioDetalle().addAll(obtenerLibroDiarioDetalleLetraDeCambio(documentoSunat, documentoSunat.getDetalleLetra(), intParaTipoRequisicion, modelo));
		} catch (Exception e) {
			log.info(e.getMessage());
		}		
		return libroDiario;
	}

	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 06.11.2014
	 * Funcionalidad: Genera y graba la Nota de Crédito o Debito segun sea el caso.
	 * @param documentoSunat
	 * @return
	 * @throws BusinessException
	 */
	public DocumentoSunat agregarDocumentoSunatNota(DocumentoSunat documentoSunat) throws BusinessException{
		try{
			LibroDiarioFacadeRemote libroDiarioFacade =  (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			
			for(DocumentoSunat documentoSunatNota : documentoSunat.getListaDocumentoSunatNota()){
				if(documentoSunatNota.getId().getIntItemDocumentoSunat()!=null) continue;

				if (documentoSunat.getIntIndicadorIGV().equals(Constante.PARAM_T_INDICADORIGV_GENERA)) {
					documentoSunat.setSeleccionaIGVContable(true);
				}else {
					documentoSunat.setSeleccionaIGVContable(false);
				}
				
				LibroDiario libroDiario = procesarLibroDiarioNota(documentoSunatNota, documentoSunatNota.getOrdenCompra());
//				libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
				
//				documentoSunatNota.setIntEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
//				documentoSunatNota.setIntPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
//				documentoSunatNota.setIntCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
//				//GRABACION DOCUMENTO SUNAT...
//				log.info(documentoSunatNota);
//				documentoSunatNota = boDocumentoSunat.grabar(documentoSunatNota);
//				//GRABACION DOCUMENTO SUNAT DETALLE...
//				DocumentoSunatDetalle documentoSunatDetalleLetra = documentoSunatNota.getDetalleLetra();
//				documentoSunatDetalleLetra = grabarDocumentoSunatDetalleLetra(documentoSunatNota, documentoSunatDetalleLetra);
//				//GRABACION DOCUMENTO SUNAT LETRA...
//				if (documentoSunatNota.getListaDocSunatRelacionadosConLetraDeCambio()!=null && !documentoSunatNota.getListaDocSunatRelacionadosConLetraDeCambio().isEmpty()) {
//					for (DocumentoSunat docSunatRel : documentoSunatNota.getListaDocSunatRelacionadosConLetraDeCambio()) {
//						DocumentoSunatLetra docSunatLetra = new DocumentoSunatLetra();
//						docSunatLetra.getId().setIntPersEmpresaDocSunat(docSunatRel.getId().getIntPersEmpresa());
//						docSunatLetra.getId().setIntItemDocumentoSunat(docSunatRel.getId().getIntItemDocumentoSunat());
//						docSunatLetra.setIntPersEmpresaDocSunatLetra(documentoSunatNota.getId().getIntPersEmpresa());
//						docSunatLetra.setIntItemDocSunatLetraEnlazada(documentoSunatNota.getId().getIntItemDocumentoSunat());
//						docSunatLetra.setBdMontoCancelado(docSunatRel.getBdMontoAplicar());
//						if (documentoValido(docSunatRel.getDocPercepcion())) {
//							docSunatLetra.setIntItemDocumentoSunatDoc(docSunatRel.getDocPercepcion().getId().getIntItemDocumentoSunatDoc());
//						}
//						docSunatLetra = boDocumentoSunatLetra.grabar(docSunatLetra);
//						//ACTUALIZA DOCUMENTO SUNAT...
//						if(documentoSunatNota.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_LETRA_CAMBIO)){
//							DocumentoSunat docSunatTmp = boDocumentoSunat.getPorPk(docSunatRel.getId());
//							docSunatTmp.setIntIndicadorLetras(Constante.PARAM_T_INDICADORLETRAS_CONLETRAS);
//							docSunatTmp.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
//							boDocumentoSunat.modificar(docSunatTmp);
//						}
//						//ACTUALIZA SUCURSAL DEL DOCUMENTO SUNAT GENERADO CON LA SUCURSAL DEL DOC SUNAT RELACIONADO...
//						documentoSunatDetalleLetra.setIntSucuIdSucursal(docSunatRel.getIntSucuIdSucursal());
//						documentoSunatDetalleLetra.setIntSudeIdSubsucursal(docSunatRel.getIntSubIdSubsucursal());
//						for (LibroDiarioDetalle o : libroDiario.getListaLibroDiarioDetalle()) {
//							if (o.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) {
//								if (o.getBdDebeSoles()!=null) {
//									documentoSunatDetalleLetra.setIntPersEmpresaCuenta(o.getIntPersEmpresaCuenta());
//									documentoSunatDetalleLetra.setIntContPeriodoCuenta(o.getIntContPeriodo());
//									documentoSunatDetalleLetra.setStrContNumeroCuenta(o.getStrContNumeroCuenta());
//									break;
//								}
//							}else {
//								if (o.getBdDebeExtranjero()!=null) {
//									documentoSunatDetalleLetra.setIntPersEmpresaCuenta(o.getIntPersEmpresaCuenta());
//									documentoSunatDetalleLetra.setIntContPeriodoCuenta(o.getIntContPeriodo());
//									documentoSunatDetalleLetra.setStrContNumeroCuenta(o.getStrContNumeroCuenta());
//									break;
//								}
//							}
//						}
//						documentoSunatDetalleLetra = boDocumentoSunatDetalle.modificar(documentoSunatDetalleLetra);
//					}
//				}
				
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return documentoSunat;
	}

	private LibroDiario procesarLibroDiarioNota(DocumentoSunat documentoSunat, OrdenCompra ordenCompra)throws Exception{
		LibroDiario libroDiario = generarLibroDiario(documentoSunat);
		Integer intParaTipoRequisicion = null;
		List<LibroDiarioDetalle> libroDiarioDetalleTemp = new ArrayList<LibroDiarioDetalle>();
		Boolean blnIGVContable = false;
//		try {
//			ModeloFacadeRemote modeloFacade =  (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
//			intParaTipoRequisicion = ordenCompra.getDocumentoRequisicion().getRequisicion().getIntParaTipoRequisicion();
//			log.info("TIPO DE REQUISICION: "+intParaTipoRequisicion);
//			log.info("moneda: "+documentoSunat.getIntParaMoneda());
//
//			Modelo modelo = modeloFacade.obtenerTipoModeloActual
//			(Constante.PARAM_T_MODELOCONTABLE_LETRADECAMBIO, documentoSunat.getId().getIntPersEmpresa()).get(0);
//			log.info("MODELO CONTABLE: "+modelo);
//
//			libroDiario.getListaLibroDiarioDetalle().addAll(obtenerLibroDiarioDetalleLetraDeCambio(documentoSunat, documentoSunat.getDetalleLetra(), intParaTipoRequisicion, modelo));
//		} catch (Exception e) {
//			log.info(e.getMessage());
//		}	
		ModeloFacadeRemote modeloFacade =  (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		intParaTipoRequisicion = ordenCompra.getDocumentoRequisicion().getRequisicion().getIntParaTipoRequisicion();
		log.info("TIPO DE REQUISICION: "+intParaTipoRequisicion);

		Modelo modelo = null;
		if (documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO)) {
			modelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_MODELOCONTABLE_NOTACREDITO, documentoSunat.getId().getIntPersEmpresa()).get(0);
		}else if (documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTADEBITO)) {
			modelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_MODELOCONTABLE_NOTADEBITO, documentoSunat.getId().getIntPersEmpresa()).get(0);
		}


		
		log.info("MODELO CONTABLE: "+modelo);
//
//		
		if(documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_RECIBOHONORARIOS)){				
//			if (detalleValido(documentoSunat.getDetalleRetencion())) {
//				log.info("----- LLD RETENCION -----");
//				libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleRetencion(), null, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION, documentoSunat.getIntParaTipoComprobante()));
				log.info("----- LLD TOTAL GENERAL-----");
//				libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
				printLibroDiarioDetalle(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
//			}
//			else { 
//				log.info("----- LLD TOTAL GENERAL -----");
//				libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL, documentoSunat.getIntParaTipoComprobante()));
////				printLibroDiarioDetalle(obtenerLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL, documentoSunat.getIntParaTipoComprobante()));
//			}
		}else {
			if (documentoSunat.getIntIndicadorIGV()!=null && documentoSunat.getIntIndicadorIGV().equals(1)) {
				blnIGVContable = true;
				Tarifa tarifaIGV = cargarTarifaIGV(documentoSunat);
//				if (documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTACREDITO)) {
//					calcularIGV(documentoSunat.getDetalleNotaCredito(), documentoSunat.getTipoCambio(), documentoSunat, tarifaIGV);
//				}else if (documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_NOTADEBITO)) {
//					calcularIGV(documentoSunat.getDetalleNotaDebito(), documentoSunat.getTipoCambio(), documentoSunat, tarifaIGV);
//				}
				documentoSunat.setDetalleIGV(new DocumentoSunatDetalle());
				documentoSunat.getDetalleIGV().setBdMontoTotal(BigDecimal.ZERO);
				BigDecimal bdMontoSubtotal = new BigDecimal(0);
				BigDecimal bdMontoIGV = new BigDecimal(0);
				if (documentoSunat.getListaDocumentoSunatDetalle()!=null && !documentoSunat.getListaDocumentoSunatDetalle().isEmpty()) {
					for (DocumentoSunatDetalle documentoSunatDetalle : documentoSunat.getListaDocumentoSunatDetalle()) {
						if (documentoSunatDetalle.getOrdenCompraDetalle().getIntAfectoIGV().equals(Constante.INT_AFECTOIGV)) {
							documentoSunatDetalle = calcularIGV(documentoSunatDetalle, documentoSunat.getTipoCambio(), documentoSunat, tarifaIGV);
							bdMontoSubtotal = bdMontoSubtotal.add(documentoSunatDetalle.getBdMontoSinIGV());
							bdMontoIGV = bdMontoIGV.add(documentoSunatDetalle.getBdMontoIGV());
//							documentoSunat.getDetalleIGV().setBdMontoTotal(documentoSunat.getDetalleIGV().getBdMontoTotal().add(documentoSunat.getDetalleIGV().getBdMontoIGV()));
						}
					}
				}
				if(!documentoSunat.isSeleccionaInafecto()){
					documentoSunat.getDetalleIGV().setBdMontoTotal(bdMontoIGV);
				}else{
					bdMontoSubtotal = bdMontoSubtotal.add(bdMontoIGV);
					documentoSunat.getDetalleIGV().setBdMontoTotal(new BigDecimal(0));
				}
				
				
				documentoSunat.getDetalleIGV().setIntAfectoIGV(1);
				log.info("----- LLD SUNAT -----");
//					libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleIGV(), null, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV, documentoSunat.getIntParaTipoComprobante()));
					printLibroDiarioDetalle(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleIGV(), null, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV, documentoSunat.getIntParaTipoComprobante()));

				
				log.info("----- LLD TOTAL GENERAL -----");
//				libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
				printLibroDiarioDetalle(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
			}else{
				log.info("----- LLD TOTAL GENERAL -----");
//				libroDiarioDetalleTemp.add(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
				printLibroDiarioDetalle(obtenerLibroDiarioDetalleDocumentoSunat(documentoSunat, documentoSunat.getDetalleTotalGeneral(), intParaTipoRequisicion, modelo, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL, documentoSunat.getIntParaTipoComprobante()));
			}
		}
//		
//		BigDecimal bdMontoSinIgv = BigDecimal.ZERO;
//		BigDecimal bdMontoTotal = BigDecimal.ZERO;
//		DocumentoSunatDetalle docSunDetAnt = null; 
//		List<DocumentoSunatDetalle> lstDocSunatDetalleTemp = new ArrayList<DocumentoSunatDetalle>();
//		for (int i = 0; i < documentoSunat.getListaDocumentoSunatDetalle().size(); i++) {
//			DocumentoSunatDetalle docSunDet = documentoSunat.getListaDocumentoSunatDetalle().get(i);
//			
//			if (i==0) {
//				bdMontoSinIgv = bdMontoSinIgv.add(docSunDet.getBdMontoSinIGV());
//				bdMontoTotal = bdMontoTotal.add(docSunDet.getBdMontoTotal());
//				docSunDetAnt = documentoSunat.getListaDocumentoSunatDetalle().get(i);
//			}else {
//				if (docSunDetAnt.getIntSucuIdSucursal().equals(docSunDet.getIntSucuIdSucursal())
//						&& docSunDetAnt.getIntSudeIdSubsucursal().equals(docSunDet.getIntSudeIdSubsucursal())) {
//					bdMontoSinIgv = bdMontoSinIgv.add(docSunDet.getBdMontoSinIGV());
//					bdMontoTotal = bdMontoTotal.add(docSunDet.getBdMontoTotal());
//					docSunDetAnt = documentoSunat.getListaDocumentoSunatDetalle().get(i);
//				}else {
//					docSunDetAnt.setBdMontoGrabarEnLibro(blnIGVContable?bdMontoSinIgv:bdMontoTotal);
//					lstDocSunatDetalleTemp.add(docSunDetAnt);
//					bdMontoSinIgv = BigDecimal.ZERO;
//					bdMontoSinIgv = bdMontoSinIgv.add(docSunDet.getBdMontoSinIGV());
//					bdMontoTotal = BigDecimal.ZERO;
//					bdMontoTotal = bdMontoTotal.add(docSunDet.getBdMontoTotal());
//				}
//			}
//			if (i==documentoSunat.getListaDocumentoSunatDetalle().size()-1) {
//				docSunDet.setBdMontoGrabarEnLibro(blnIGVContable?bdMontoSinIgv:bdMontoTotal);
//				lstDocSunatDetalleTemp.add(docSunDet);
//			}
//		}
//		for (DocumentoSunatDetalle docSunatDetalle : lstDocSunatDetalleTemp) {
//			log.info("----- ORDEN DE COMPRA -----");
//			libroDiarioDetalleTemp.add(generarLibroDiarioDetalleOrdenCompra(documentoSunat, docSunatDetalle));	
////			printLibroDiarioDetalle(generarLibroDiarioDetalleOrdenCompra(documentoSunat, docSunatDetalle));
//		}
//		
//		libroDiario.setListaLibroDiarioDetalle(libroDiarioDetalleTemp);
		return libroDiario;
	}
    private void printLibroDiarioDetalle(LibroDiarioDetalle libroDiarioDetalle){
    	log.info("EMPRESACUENTA: "+libroDiarioDetalle.getIntPersEmpresaCuenta());
    	log.info("CONTPERIODO: "+libroDiarioDetalle.getIntContPeriodo());
    	log.info("NROCTA: "+libroDiarioDetalle.getStrContNumeroCuenta());
    	log.info("PERSONA: "+libroDiarioDetalle.getIntPersPersona());
    	log.info("DOC GRAL: "+libroDiarioDetalle.getIntParaDocumentoGeneral());
    	log.info("SERI: "+libroDiarioDetalle.getStrSerieDocumento());
    	log.info("NRO DOC: "+libroDiarioDetalle.getStrNumeroDocumento());
    	log.info("EMPRESA SUCURSAL: "+libroDiarioDetalle.getIntPersEmpresaSucursal());
    	log.info("SUCURSAL: "+libroDiarioDetalle.getIntSucuIdSucursal());
    	log.info("SUBSUCURSAL: "+libroDiarioDetalle.getIntSudeIdSubSucursal());
    	log.info("MODENA: "+libroDiarioDetalle.getIntParaMonedaDocumento());
    	log.info("COMENTARIO: "+libroDiarioDetalle.getStrComentario());
    	log.info("DEBE: "+libroDiarioDetalle.getBdDebeSoles());
    	log.info("HABER: "+libroDiarioDetalle.getBdHaberSoles());
    }
}