package pe.com.tumi.tesoreria.logistica.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Tarifa;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.tesoreria.logistica.bo.AdelantoSunatBO;
import pe.com.tumi.tesoreria.logistica.bo.DocumentoSunatBO;
import pe.com.tumi.tesoreria.logistica.bo.DocumentoSunatDetalleBO;
import pe.com.tumi.tesoreria.logistica.bo.DocumentoSunatDocBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraDetalleBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraDocumentoBO;
import pe.com.tumi.tesoreria.logistica.domain.AdelantoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDoc;
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
	AdelantoSunatBO boAdelantoSunat = (AdelantoSunatBO)TumiFactory.get(AdelantoSunatBO.class);
	DocumentoSunatModeloService documentoSunatModeloService = (DocumentoSunatModeloService)TumiFactory.get(DocumentoSunatModeloService.class);
	//Agregado por cdelosrios, 01/11/2013
	DocumentoSunatDocBO boDocumentoSunatDoc = (DocumentoSunatDocBO)TumiFactory.get(DocumentoSunatDocBO.class);
	//Fin agregado por cdelosrios, 01/11/2013
	
	public List<DocumentoSunat> grabarDocumentoSunat(List<DocumentoSunat> listaDocumentoSunat, OrdenCompra ordenCompra) throws BusinessException{
		//Agregado por cdelosrios, 01/11/2013
		DocumentoSunatDoc documentoSunatDoc = null;
		//Fin agregado por cdelosrios, 01/11/2013
		//Agregado por cdelosrios, 12/11/2013
		AdelantoSunat docSunatOrdenCompraDoc = null;
		//Fin agregado por cdelosrios, 12/11/2013
		try{
			LibroDiarioFacadeRemote libroDiarioFacade =  (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				log.info(ordenCompraDetalle);
				boOrdenCompraDetalle.modificar(ordenCompraDetalle);
			}
			
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				log.info(ordenCompraDocumento);
				boOrdenCompraDocumento.modificar(ordenCompraDocumento);
			}
			
			for(DocumentoSunat documentoSunat : listaDocumentoSunat){
				LibroDiario libroDiario = procesarLibroDiario(documentoSunat);
				libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
				
				documentoSunat.setIntEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
				documentoSunat.setIntPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
				documentoSunat.setIntCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
				log.info(documentoSunat);
				documentoSunat = boDocumentoSunat.grabar(documentoSunat);
				
				for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunat.getListaDocumentoSunatDetalle()){
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunatDetalle, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PRODUCTO);
				}
				
				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleSubTotal(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_SUBTOTAL);				
				
				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleDescuento(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DESCUENTO);
				
				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleValorVenta(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_VALORVENTA);
				
				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleIGV(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV);
				
				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleOtros(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_OTROS);
				
				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleTotal(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL);
				
				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetallePercepcion(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION);
				
				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleDetraccion(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION);
				
				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleRetencion(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION);
				
				grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(), 
							Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL);
				
				//Agregado por cdelosrios, 01/11/2013
				if(documentoSunat.getDetallePercepcion().getBdMontoTotal()!=null
						&& documentoSunat.getDetallePercepcion().getBdMontoTotal().compareTo(BigDecimal.ZERO)>0){
					documentoSunatDoc = new DocumentoSunatDoc();
					documentoSunatDoc.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
					documentoSunatDoc.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
					documentoSunatDoc.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION);
					documentoSunatDoc.setBdMontoDocumento(documentoSunat.getDetallePercepcion().getBdMontoTotal());
					documentoSunatDoc.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
					documentoSunatDoc.setIntPersEmpresaPago(documentoSunat.getId().getIntPersEmpresa());
					boDocumentoSunatDoc.grabar(documentoSunatDoc);
				}
				
				if(documentoSunat.getDetalleDetraccion().getBdMontoTotal()!=null
						&& documentoSunat.getDetalleDetraccion().getBdMontoTotal().compareTo(BigDecimal.ZERO)>0){
					documentoSunatDoc = new DocumentoSunatDoc();
					documentoSunatDoc.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
					documentoSunatDoc.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
					documentoSunatDoc.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION);
					documentoSunatDoc.setBdMontoDocumento(documentoSunat.getDetalleDetraccion().getBdMontoTotal());
					documentoSunatDoc.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
					documentoSunatDoc.setIntPersEmpresaPago(documentoSunat.getId().getIntPersEmpresa());
					boDocumentoSunatDoc.grabar(documentoSunatDoc);
				}
				//Fin agregado por cdelosrios, 01/11/2013
				
				for(AdelantoSunat adelantoSunat : documentoSunat.getListaAdelantoSunat()){
					adelantoSunat.setDocumentoSunatId(documentoSunat.getId());
					log.info(adelantoSunat);
					boAdelantoSunat.grabar(adelantoSunat);
				}
				
				//Agregado por cdelosrios, 12/11/2013
				for(OrdenCompraDetalle ordenCompraDetalle : documentoSunat.getOrdenCompra().getListaOrdenCompraDetalle()){
					if(ordenCompraDetalle.getBdMontoUsar()!=null && 
							ordenCompraDetalle.getBdMontoUsar().compareTo(BigDecimal.ZERO)>0){
						docSunatOrdenCompraDoc = new AdelantoSunat();
						docSunatOrdenCompraDoc.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
						//docSunatOrdenCompraDoc.setOrdenCompraDocumentoId(documentoSunat.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getId());
						docSunatOrdenCompraDoc.setDocumentoSunatId(documentoSunat.getId());
						docSunatOrdenCompraDoc.setBdMonto(documentoSunat.getDetalleTotal().getBdMontoTotal());
						//docSunatOrdenCompraDoc.setBdMonto(ordenCompraDetalle.getBdMontoUsar());
						docSunatOrdenCompraDoc.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						docSunatOrdenCompraDoc.setIntItemOrdenCompraDet(ordenCompraDetalle.getId().getIntItemOrdenCompraDetalle());
						boAdelantoSunat.grabar(docSunatOrdenCompraDoc);
					}
				}
				//Fin agregado por cdelosrios, 12/11/2013
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
	
	private LibroDiario generarLibroDiario(DocumentoSunat documentoSunat)throws Exception{
		LibroDiario libroDiario = new LibroDiario();
		//Agregado por cdelosrios, 21/10/2013
		libroDiario.setId(new LibroDiarioId());
		libroDiario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
		//Fin agregado por cdelosrios, 21/10/2013
		libroDiario.getId().setIntPersEmpresaLibro(documentoSunat.getId().getIntPersEmpresa());
		libroDiario.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());
		libroDiario.setStrGlosa(documentoSunat.getStrGlosa());
		libroDiario.setTsFechaRegistro(MyUtil.obtenerFechaActual());
		libroDiario.setTsFechaDocumento(documentoSunat.getTsFechaProvision());
		libroDiario.setIntPersEmpresaUsuario(documentoSunat.getIntPersEmpresaUsuario());
		libroDiario.setIntPersPersonaUsuario(documentoSunat.getIntPersPersonaUsuario());
		libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
		
		return libroDiario;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalle(DocumentoSunat documentoSunat, DocumentoSunatDetalle documentoSunatDetalle, 
			ModeloDetalle modeloDetalle)throws Exception{
		LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
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
		libroDiarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
		if(modeloDetalle.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE))
			libroDiarioDetalle.setBdDebeSoles(documentoSunatDetalle.getBdMontoTotal());
		else
			libroDiarioDetalle.setBdHaberSoles(documentoSunatDetalle.getBdMontoTotal());
		
		return libroDiarioDetalle;
	}
	
	private List<LibroDiarioDetalle> generarLibroDiarioDetalle(DocumentoSunat documentoSunat)throws Exception{
		List<LibroDiarioDetalle> listaLibroDiarioDetalle = new ArrayList<LibroDiarioDetalle>();		
		for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunat.getListaDocumentoSunatDetalle()){
			if(!documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PRODUCTO))
				continue;
			LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
			libroDiarioDetalle.setIntPersEmpresaCuenta(documentoSunatDetalle.getIntPersEmpresaCuenta());
			libroDiarioDetalle.setIntContPeriodo(documentoSunatDetalle.getIntContPeriodoCuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(documentoSunatDetalle.getStrContNumeroCuenta());
			libroDiarioDetalle.setIntPersPersona(documentoSunat.getOrdenCompra().getIntPersPersonaProveedor());
			libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
			libroDiarioDetalle.setStrSerieDocumento(documentoSunat.getStrSerieDocumento());
			libroDiarioDetalle.setStrNumeroDocumento(documentoSunat.getStrNumeroDocumento());
			libroDiarioDetalle.setIntPersEmpresaSucursal(documentoSunat.getId().getIntPersEmpresa());
			libroDiarioDetalle.setIntSucuIdSucursal(documentoSunatDetalle.getIntSucuIdSucursal());
			libroDiarioDetalle.setIntSudeIdSubSucursal(documentoSunatDetalle.getIntSudeIdSubsucursal());
			libroDiarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
			libroDiarioDetalle.setBdDebeSoles(documentoSunatDetalle.getBdMontoTotal());
			
			listaLibroDiarioDetalle.add(libroDiarioDetalle);
		}
		
		return listaLibroDiarioDetalle;
	}
	
	private LibroDiario procesarLibroDiario(DocumentoSunat documentoSunat)throws Exception{
		LibroDiario libroDiario = generarLibroDiario(documentoSunat);
		
		ModeloFacadeRemote modeloFacade =  (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		Modelo modelo = modeloFacade.obtenerTipoModeloActual
			(Constante.PARAM_T_TIPOMODELOCONTABLE_ORDENCOMPRA, documentoSunat.getId().getIntPersEmpresa()).get(0);
		log.info(modelo);
		ModeloDetalle modeloDetalleIGV = documentoSunatModeloService.obtenerModeloDetalleIGV(modelo);
		ModeloDetalle modeloDetalleIGVPercepcion = documentoSunatModeloService.obtenerModeloDetalleIGVPercepciones(modelo);
		ModeloDetalle modeloDetalleImpuestoRenta = documentoSunatModeloService.obtenerModeloDetalleImpuestoRenta(modelo);
		ModeloDetalle modeloDetalleFacturasPagar = documentoSunatModeloService.obtenerModeloDetalleFacturasPorPagar(modelo);
		
		log.info("modeloDetalleIGV           "+modeloDetalleIGV);
		log.info("modeloDetalleIGVPercepcion "+modeloDetalleIGVPercepcion);
		log.info("modeloDetalleImpuestoRenta "+modeloDetalleImpuestoRenta);
		log.info("modeloDetalleFacturasPagar "+modeloDetalleFacturasPagar);
		
		if(documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA)
		&& !documentoSunat.getDetalleIGV().getBdMontoTotal().equals(new BigDecimal(0))
		&& documentoSunat.getIntIndicadorIGV().equals(new Integer(1))
		){
			libroDiario.getListaLibroDiarioDetalle().add(
					generarLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleIGV(), modeloDetalleIGV));
		}
		
		//Agregado por cdelosrios, 01/11/2013
		if(documentoSunat.getDetallePercepcion()!=null && documentoSunat.getDetallePercepcion().getBdMontoTotal()==null){
			documentoSunat.getDetallePercepcion().setBdMontoTotal(BigDecimal.ZERO);
		}
		if(documentoSunat.getDetalleRetencion()!=null && documentoSunat.getDetalleRetencion().getBdMontoTotal()==null){
			documentoSunat.getDetalleRetencion().setBdMontoTotal(BigDecimal.ZERO);
		}
		//Fin agregado por cdelosrios, 01/11/2013
		
		if(documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA)
		//Modificado por cdelosrios, 16/11/2013
			//&& !documentoSunat.getDetallePercepcion().getBdMontoTotal().equals(new BigDecimal(0))
			&& (documentoSunat.getDetallePercepcion()!=null && !documentoSunat.getDetallePercepcion().getBdMontoTotal().equals(new BigDecimal(0)))
		//Fin modificado por cdelosrios, 16/11/2013
		&& documentoSunat.getIntIndicadorPercepcion().equals(new Integer(1))
		){
			libroDiario.getListaLibroDiarioDetalle().add(
					generarLibroDiarioDetalle(documentoSunat, documentoSunat.getDetallePercepcion(), modeloDetalleIGVPercepcion));
			if(modeloDetalleIGVPercepcion.getIntParaOpcionDebeHaber().equals(Constante.PARAM_T_OPCIONDEBEHABER_DEBE)){
				
			}
		}
		
		if(documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_RECIBOHONORARIOS)
		&& !documentoSunat.getDetalleRetencion().getBdMontoTotal().equals(new BigDecimal(0))
		){
			libroDiario.getListaLibroDiarioDetalle().add(
					generarLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleRetencion(), modeloDetalleImpuestoRenta));
		}
		
		if(detalleValido(documentoSunat.getDetalleTotalGeneral()) 
		&& validarTipoComprobante(documentoSunat, modeloDetalleFacturasPagar)){
			if(detalleValido(documentoSunat.getDetalleDetraccion())){
				documentoSunat.getDetalleTotalGeneral().getBdMontoTotal().add(documentoSunat.getDetalleDetraccion().getBdMontoTotal());
				//Modificado por cdelosrios, 27/08/2013
				libroDiario.getListaLibroDiarioDetalle().add(
						generarLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleTotal(), modeloDetalleFacturasPagar));
				documentoSunat.getDetalleTotal().getBdMontoTotal().subtract(documentoSunat.getDetalleDetraccion().getBdMontoTotal());
						//generarLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(), modeloDetalleFacturasPagar));
				//documentoSunat.getDetalleTotalGeneral().getBdMontoTotal().subtract(documentoSunat.getDetalleDetraccion().getBdMontoTotal());
				//Fin modificado por cdelosrios, 27/08/2013
			}else{
				libroDiario.getListaLibroDiarioDetalle().add(
						//generarLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(), modeloDetalleFacturasPagar));
						generarLibroDiarioDetalle(documentoSunat, documentoSunat.getDetalleTotal(), modeloDetalleFacturasPagar));
			}
		}
		
		libroDiario.getListaLibroDiarioDetalle().addAll(generarLibroDiarioDetalle(documentoSunat));
		
		return libroDiario;
	}
	
	private boolean validarTipoComprobante(DocumentoSunat documentoSunat, ModeloDetalle modeloDetalle){
		List<ModeloDetalleNivel> listaValidarTipoComprobante = new ArrayList<ModeloDetalleNivel>();
		for(ModeloDetalleNivel modeloDetalleNivel : modeloDetalle.getListModeloDetalleNivel())
			if(modeloDetalleNivel.getIntDatoTablas().equals(Constante.PARAM_T_TIPOCOMPROBANTE_))
				listaValidarTipoComprobante.add(modeloDetalleNivel);
		
		for(ModeloDetalleNivel modeloDetalleNivel : listaValidarTipoComprobante)
			if(documentoSunat.getIntParaTipoComprobante().equals(modeloDetalleNivel.getIntDatoArgumento()))
				return Boolean.TRUE;
		
		return Boolean.FALSE;
	}
	
	private void grabarDocumentoSunatDetalle(DocumentoSunat documentoSunat, DocumentoSunatDetalle documentoSunatDetalle, 
		Integer intTipoDocumentoSunat)throws Exception{
		
		documentoSunatDetalle.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
		documentoSunatDetalle.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
		documentoSunatDetalle.setIntParaTipoDocumentoSunat(intTipoDocumentoSunat);		

		log.info(documentoSunatDetalle.getIntParaTipoDocumentoSunat()+" "+documentoSunatDetalle.getBdMontoTotal());
		if(detalleValido(documentoSunatDetalle)){
			log.info("--"+documentoSunatDetalle);
			boDocumentoSunatDetalle.grabar(documentoSunatDetalle);
		}else{
			log.info("  "+documentoSunatDetalle);
		}
	}
	
	public List<DocumentoSunat> buscarDocumentoSunat(DocumentoSunat documentoSunatFiltro, List<Persona> listaPersona) throws BusinessException{
		List<DocumentoSunat> listaDocumentoSunat = null;
		try{
			log.info(documentoSunatFiltro);
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			listaDocumentoSunat = boDocumentoSunat.getPorBuscar(documentoSunatFiltro);
			
			for(DocumentoSunat documentoSunat : listaDocumentoSunat){
				documentoSunat.setOrdenCompra(boOrdenCompra.getPorDocumentoSunat(documentoSunat));
				Persona proveedor = personaFacade.devolverPersonaCargada(documentoSunat.getOrdenCompra().getIntPersPersonaProveedor());
				documentoSunat.getOrdenCompra().setPersonaProveedor(proveedor);
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
				for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunat.getListaDocumentoSunatDetalle()){
					if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DESCUENTO)){
						documentoSunat.setDetalleDescuento(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_VALORVENTA)){
						documentoSunat.setDetalleValorVenta(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV)){
						documentoSunat.setDetalleIGV(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_OTROS)){
						documentoSunat.setDetalleOtros(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL)){
						documentoSunat.setDetalleTotal(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION)){
						documentoSunat.setDetallePercepcion(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION)){
						documentoSunat.setDetalleDetraccion(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION)){
						documentoSunat.setDetalleRetencion(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL)){
						documentoSunat.setDetalleTotalGeneral(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_SUBTOTAL)){
						documentoSunat.setDetalleSubTotal(documentoSunatDetalle);
					}else if(documentoSunatDetalle.getIntParaTipoDocumentoSunat().equals(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PRODUCTO)){
						listaDocumentoSunatDetalle.add(documentoSunatDetalle);
					}
				}
				
				if(documentoSunat.getDetalleDescuento()==null)	documentoSunat.setDetalleDescuento(new DocumentoSunatDetalle());				
				if(documentoSunat.getDetalleValorVenta()==null)	documentoSunat.setDetalleValorVenta(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleIGV()==null)		documentoSunat.setDetalleIGV(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleOtros()==null)		documentoSunat.setDetalleOtros(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleTotal()==null)		documentoSunat.setDetalleTotal(new DocumentoSunatDetalle());
				if(documentoSunat.getDetallePercepcion()==null)	documentoSunat.setDetallePercepcion(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleDetraccion()==null)	documentoSunat.setDetalleDetraccion(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleRetencion()==null)	documentoSunat.setDetalleRetencion(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleTotalGeneral()==null)documentoSunat.setDetalleTotalGeneral(new DocumentoSunatDetalle());
				if(documentoSunat.getDetalleSubTotal()==null)	documentoSunat.setDetalleSubTotal(new DocumentoSunatDetalle());
				
				documentoSunat.setListaDocumentoSunatDetalle(listaDocumentoSunatDetalle);
				documentoSunat.getOrdenCompra().setListaOrdenCompraDetalle(
						boOrdenCompraDetalle.getPorOrdenCompra(documentoSunat.getOrdenCompra()));
				documentoSunat.setIntParaMoneda(documentoSunat.getOrdenCompra().getListaOrdenCompraDetalle().get(0).getIntParaTipoMoneda());
				
				documentoSunat.setListaDocumentoSunatLetra(boDocumentoSunat.getListaEnlazados(documentoSunat));
				for(DocumentoSunat documentoSunatLetra : documentoSunat.getListaDocumentoSunatLetra()){
					documentoSunatLetra.setDetalleLetra(boDocumentoSunatDetalle.getPorDocumentoSunat(documentoSunatLetra).get(0));
				}
				
				if(documentoSunat.getIntIndicadorInafecto().equals(Constante.OPCION_SELECCIONADA))
					documentoSunat.setSeleccionaInafecto(Boolean.TRUE);
				else
					documentoSunat.setSeleccionaInafecto(Boolean.FALSE);
				
				if(documentoSunat.getIntIndicadorIGV().equals(Constante.OPCION_SELECCIONADA))
					documentoSunat.setSeleccionaIGV(Boolean.TRUE);
				else
					documentoSunat.setSeleccionaIGV(Boolean.FALSE);
				
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
		DocumentoSunatDoc documentoSunatDoc = null;
		try{
			LibroDiarioFacadeRemote libroDiarioFacade =  (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				log.info(ordenCompraDetalle);
				boOrdenCompraDetalle.modificar(ordenCompraDetalle);
			}
			
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				log.info(ordenCompraDocumento);
				boOrdenCompraDocumento.modificar(ordenCompraDocumento);
			}
			
			for(DocumentoSunat documentoSunat : listaDocumentoSunat){
				if(documentoSunat.getId().getIntItemDocumentoSunat()!=null){ // Si es modificar
					//Agregado por cdelosrios, 18/11/2013
					if(listaDocSunatLetra!=null && !listaDocSunatLetra.isEmpty()){
						documentoSunat.setListaDocumentoSunatLetra(listaDocSunatLetra);
						documentoSunat.setOrdenCompra(ordenCompra);
						agregarDocumentoSunatLetra(documentoSunat);
					}
					//Fin agregado por cdelosrios, 18/11/2013
				} else { //Si es Nuevo
					LibroDiario libroDiario = procesarLibroDiario(documentoSunat);
					libroDiario = libroDiarioFacade.grabarLibroDiario(libroDiario);
					
					documentoSunat.setIntEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
					documentoSunat.setIntPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
					documentoSunat.setIntCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
					log.info(documentoSunat);
					documentoSunat = boDocumentoSunat.grabar(documentoSunat);
					
					for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunat.getListaDocumentoSunatDetalle()){
						grabarDocumentoSunatDetalle(documentoSunat, documentoSunatDetalle, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PRODUCTO);
					}
					
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleSubTotal(), 
								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_SUBTOTAL);				
					
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleDescuento(), 
								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DESCUENTO);
					
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleValorVenta(), 
								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_VALORVENTA);
					
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleIGV(), 
								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV);
					
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleOtros(), 
								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_OTROS);
					
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleTotal(), 
								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL);
					
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetallePercepcion(), 
								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION);
					
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleDetraccion(), 
								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION);
					
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleRetencion(), 
								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION);
					
					grabarDocumentoSunatDetalle(documentoSunat, documentoSunat.getDetalleTotalGeneral(),
								Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL);
					
					//Agregado por cdelosrios, 01/11/2013
					if(documentoSunat.getDetallePercepcion().getBdMontoTotal()!=null
							&& documentoSunat.getDetallePercepcion().getBdMontoTotal().compareTo(BigDecimal.ZERO)>0){
						documentoSunatDoc = new DocumentoSunatDoc();
						documentoSunatDoc.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
						documentoSunatDoc.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
						documentoSunatDoc.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION);
						documentoSunatDoc.setBdMontoDocumento(documentoSunat.getDetallePercepcion().getBdMontoTotal());
						documentoSunatDoc.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
						documentoSunatDoc.setIntPersEmpresaPago(documentoSunat.getId().getIntPersEmpresa());
						boDocumentoSunatDoc.grabar(documentoSunatDoc);
					}
					
					if(documentoSunat.getDetalleDetraccion().getBdMontoTotal()!=null
							&& documentoSunat.getDetalleDetraccion().getBdMontoTotal().compareTo(BigDecimal.ZERO)>0){
						documentoSunatDoc = new DocumentoSunatDoc();
						documentoSunatDoc.getId().setIntPersEmpresa(documentoSunat.getId().getIntPersEmpresa());
						documentoSunatDoc.getId().setIntItemDocumentoSunat(documentoSunat.getId().getIntItemDocumentoSunat());
						documentoSunatDoc.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION);
						documentoSunatDoc.setBdMontoDocumento(documentoSunat.getDetalleDetraccion().getBdMontoTotal());
						documentoSunatDoc.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
						documentoSunatDoc.setIntPersEmpresaPago(documentoSunat.getId().getIntPersEmpresa());
						boDocumentoSunatDoc.grabar(documentoSunatDoc);
					}
					//Fin agregado por cdelosrios, 01/11/2013
					
					for(AdelantoSunat adelantoSunat : documentoSunat.getListaAdelantoSunat()){
						adelantoSunat.setDocumentoSunatId(documentoSunat.getId());
						log.info(adelantoSunat);
						boAdelantoSunat.grabar(adelantoSunat);
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
	
	public DocumentoSunat agregarDocumentoSunatLetra(DocumentoSunat documentoSunat) throws BusinessException{
		List<DocumentoSunat> lstDocSunatTmp = null;
		try{
			for(DocumentoSunat documentoSunatLetra : documentoSunat.getListaDocumentoSunatLetra()){
				if(documentoSunatLetra.getId().getIntItemDocumentoSunat()!=null) continue;
					
				documentoSunatLetra.setIntPersEmpresaDocSunatEnlazado(documentoSunat.getId().getIntPersEmpresa());
				documentoSunatLetra.setIntItemDocumentoSunatEnlazado(documentoSunat.getId().getIntItemDocumentoSunat());
				documentoSunatLetra.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				//Agregado por cdelosrios, 18/11/2013
				documentoSunatLetra.setOrdenCompra(documentoSunat.getOrdenCompra());
				documentoSunatLetra.setIntPersEmpresaOrden(documentoSunat.getOrdenCompra().getId().getIntPersEmpresa());
				documentoSunatLetra.setIntItemOrdenCompra(documentoSunat.getOrdenCompra().getId().getIntItemOrdenCompra());
				//Fin agregado por cdelosrios, 18/11/2013
				log.info(documentoSunatLetra);
				boDocumentoSunat.grabar(documentoSunatLetra);
				
				DocumentoSunatDetalle documentoSunatDetalleLetra = documentoSunatLetra.getDetalleLetra();
				grabarDocumentoSunatDetalle(documentoSunatLetra, documentoSunatDetalleLetra, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_LETRA);
				//Agregado por cdelosrios, 18/11/2013
				if(documentoSunatLetra.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_LETRA_CAMBIO)){
					lstDocSunatTmp = boDocumentoSunat.getPorOrdenCompra(documentoSunat.getOrdenCompra());
					if(lstDocSunatTmp!=null && !lstDocSunatTmp.isEmpty()){
						for (DocumentoSunat documentoSunatTmp : lstDocSunatTmp) {
							documentoSunatTmp.setIntPersEmpresaDocSunatEnlazado(documentoSunatLetra.getId().getIntPersEmpresa());
							documentoSunatTmp.setIntItemDocumentoSunatEnlazado(documentoSunatLetra.getId().getIntItemDocumentoSunat());
							documentoSunatTmp.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_CANCELADO);
							boDocumentoSunat.modificar(documentoSunatTmp);
						}
					}
				}
				//Fin agregado por cdelosrios, 18/11/2013
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
			/*if(documentoSunat.isSeleccionaInafecto()
					&& documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA)){
				bdMontoSubtotal = bdMontoSubtotal.add(bdMontoIGV);
				documentoSunat.getDetalleIGV().setBdMontoTotal(new BigDecimal(0));
			} else {
				documentoSunat.getDetalleIGV().setBdMontoTotal(bdMontoIGV);
			}*/
			//Fin agregado por cdelosrios, 25/10/2013
			documentoSunat.getDetalleSubTotal().setBdMontoTotal(bdMontoSubtotal);			
			
			if(detalleValido(documentoSunat.getDetalleDescuento())){
				documentoSunat.getDetalleValorVenta().setBdMontoTotal(
					documentoSunat.getDetalleSubTotal().getBdMontoTotal().subtract(
					documentoSunat.getDetalleDescuento().getBdMontoTotal()));
			}else{
				documentoSunat.getDetalleValorVenta().setBdMontoTotal(documentoSunat.getDetalleSubTotal().getBdMontoTotal());
			}
			
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
			
			BigDecimal bdMontoPercepcion = null;
			//Modificado por cdelosrios, 20/10/2013
			//if(documentoSunat.isSeleccionaPercepcion())
			if(documentoSunat.isSeleccionaPercepcion() && documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA))
			//Fin modificado por cdelosrios, 20/10/2013
				//Inicio modificación, cdelosrios, 14/11/2013
				//bdMontoPercepcion = documentoSunat.getDetalleTotal().getBdMontoTotal().multiply(new BigDecimal(0.02));
				bdMontoPercepcion = documentoSunat.getDetalleValorVenta().getBdMontoTotal().multiply(new BigDecimal(0.02));
				//Fin modificación, cdelosrios, 14/11/2013
			else
				bdMontoPercepcion = new BigDecimal(0);
			
			documentoSunat.getDetallePercepcion().setBdMontoTotal(bdMontoPercepcion);
			
			
			BigDecimal bdMontoDetraccion = null;
			OrdenCompra ordenCompra = documentoSunat.getOrdenCompra();
			if(ordenCompra.getIntParaTipoDetraccion()!=null 
			&& !ordenCompra.getIntParaTipoDetraccion().equals(new Integer(0))		
			&& documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA)
			&& detalleValido(documentoSunat.getDetalleIGV())){
				bdMontoDetraccion = documentoSunat.getDetalleTotal().getBdMontoTotal().multiply(ordenCompra.getDetraccion().
						getBdPorcentaje().divide(new BigDecimal(100)));
				if(bdMontoDetraccion.compareTo(Constante.MONTO_MAXIMO_DETRACCION)>0)
					bdMontoDetraccion = Constante.MONTO_MAXIMO_DETRACCION;
			}else{
				bdMontoDetraccion = new BigDecimal(0);
			}
			documentoSunat.getDetalleDetraccion().setBdMontoTotal(bdMontoDetraccion);
			
			
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
			
			BigDecimal bdMontoTotalGeneral = documentoSunat.getDetalleTotal().getBdMontoTotal().
				add(bdMontoPercepcion).subtract(bdMontoDetraccion).subtract(bdMontoRetencion);			
			documentoSunat.getDetalleTotalGeneral().setBdMontoTotal(bdMontoTotalGeneral);
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
			
			//Agregado por cdelosrios, 13/11/2013
			BigDecimal bdMontoSinIGV = tarifaIGV.getBdValor().divide(new BigDecimal(100));
			bdMontoSinIGV = bdMontoSinIGV.add(BigDecimal.ONE);
			Double dbMontoSinIGV = documentoSunatDetalle.getBdMontoSinTipoCambio().doubleValue()/bdMontoSinIGV.doubleValue();
			//documentoSunatDetalle.setBdMontoSinIGV(documentoSunatDetalle.getBdMontoTotal().subtract(documentoSunatDetalle.getBdMontoIGV()));
			
			//Fin agregado por cdelosrios, 13/11/2013
			
			if(ordenCompraDetalle.getIntAfectoIGV().equals(new Integer(1))&& !documentoSunat.isSeleccionaInafecto()
					//Agregado por cdelosrios, 07/11/2013
					&& documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA)){
					//Fin agregado por cdelosrios, 07/11/2013
				//Modificado por cdelosrios, 12/11/2013
				//documentoSunatDetalle.setBdMontoIGV(
				//		documentoSunatDetalle.getBdMontoTotal().multiply(tarifaIGV.getBdValor().divide(new BigDecimal(100))));
				documentoSunatDetalle.setBdMontoSinIGV(MyUtil.round(new BigDecimal(dbMontoSinIGV), 2, true));
				documentoSunatDetalle.setBdMontoIGV(
						documentoSunatDetalle.getBdMontoSinTipoCambio().subtract(documentoSunatDetalle.getBdMontoSinIGV()));
				//Fin modificado por cdelosrios, 12/11/2013
				//documentoSunatDetalle.setBdMontoIGV(documentoSunatDetalle.getBdMontoTotal());
			
			}else{
				documentoSunatDetalle.setBdMontoIGV(new BigDecimal(0));
				//Agregado por cdelosrios, 12/11/2013
				documentoSunatDetalle.setBdMontoSinIGV(documentoSunatDetalle.getBdMontoSinTipoCambio());
				//Fin agregado por cdelosrios, 12/11/2013
			}
			
			//documentoSunatDetalle.setBdMontoSinIGV(documentoSunatDetalle.getBdMontoTotal().subtract(documentoSunatDetalle.getBdMontoIGV()));
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
					documentoSunat.setListaDocumentoSunatDetalle(boDocumentoSunatDetalle.getPorDocumentoSunat(documentoSunat));
					//documentoSunat.setDetalleSubTotal(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PRODUCTO));
					documentoSunat.setDetalleSubTotal(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_SUBTOTAL));
					documentoSunat.setDetalleValorVenta(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_VALORVENTA));
					documentoSunat.setDetalleIGV(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV));
					documentoSunat.setDetalleOtros(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_OTROS));
					documentoSunat.setDetalleTotal(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTAL));
					documentoSunat.setDetallePercepcion(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION));
					documentoSunat.setDetalleDetraccion(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_DETRACCION));
					documentoSunat.setDetalleRetencion(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION));
					documentoSunat.setDetalleTotalGeneral(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunat, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL));
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
		AdelantoSunat adelantoSunat = null;
		OrdenCompraDetalle ordenCompraDetalle = null;
		try {
			documentoSunat = boDocumentoSunat.modificar(o);
			adelantoSunat = boAdelantoSunat.getPorDocumentoSunat(o.getId());
			if(adelantoSunat!=null){
				adelantoSunat.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				adelantoSunat = boAdelantoSunat.modificar(adelantoSunat);
				
				ordenCompraDetalle = new OrdenCompraDetalle();
				ordenCompraDetalle.getId().setIntItemOrdenCompraDetalle(adelantoSunat.getIntItemOrdenCompraDet());
				ordenCompraDetalle = boOrdenCompraDetalle.getPorPk(ordenCompraDetalle.getId());
				if(ordenCompraDetalle!=null){
					ordenCompraDetalle.setBdMontoSaldo(ordenCompraDetalle.getBdMontoSaldo().add(o.getDetalleTotal().getBdMontoTotal()));
					ordenCompraDetalle = boOrdenCompraDetalle.modificar(ordenCompraDetalle);
				}
			}
			
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
					documentoSunatLetra.setDetalleLetra(boDocumentoSunatDetalle.getPorDocumentoSunatYTipoDocSunat(documentoSunatLetra, Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_LETRA));
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
}