package pe.com.tumi.tesoreria.logistica.facade;


import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.DocumentoRequisicion;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Tarifa;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.logistica.bo.AdelantoSunatBO;
import pe.com.tumi.tesoreria.logistica.bo.ContratoBO;
import pe.com.tumi.tesoreria.logistica.bo.CuadroComparativoBO;
import pe.com.tumi.tesoreria.logistica.bo.CuadroComparativoProveedorBO;
import pe.com.tumi.tesoreria.logistica.bo.DocumentoSunatBO;
import pe.com.tumi.tesoreria.logistica.bo.DocumentoSunatDetalleBO;
import pe.com.tumi.tesoreria.logistica.bo.InformeGerenciaBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraBO;
import pe.com.tumi.tesoreria.logistica.bo.ProveedorBO;
import pe.com.tumi.tesoreria.logistica.bo.ProveedorDetalleBO;
import pe.com.tumi.tesoreria.logistica.bo.RequisicionBO;
import pe.com.tumi.tesoreria.logistica.bo.RequisicionDetalleBO;
import pe.com.tumi.tesoreria.logistica.domain.AdelantoSunat;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;
import pe.com.tumi.tesoreria.logistica.domain.ContratoId;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedor;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerencia;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorId;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionDetalle;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionId;
import pe.com.tumi.tesoreria.logistica.service.ContratoService;
import pe.com.tumi.tesoreria.logistica.service.CuadroComparativoService;
import pe.com.tumi.tesoreria.logistica.service.DocumentoSunatService;
import pe.com.tumi.tesoreria.logistica.service.InformeGerenciaService;
import pe.com.tumi.tesoreria.logistica.service.OrdenCompraService;
import pe.com.tumi.tesoreria.logistica.service.ProveedorService;
import pe.com.tumi.tesoreria.logistica.service.RequisicionService;

@Stateless
public class LogisticaFacade extends TumiFacade implements LogisticaFacadeRemote, LogisticaFacadeLocal {
	
	protected  static Logger log = Logger.getLogger(LogisticaFacade.class);
	
	ProveedorService proveedorService = (ProveedorService)TumiFactory.get(ProveedorService.class);
	ProveedorBO boProveedor = (ProveedorBO)TumiFactory.get(ProveedorBO.class);
	ProveedorDetalleBO boProveedorDetalle = (ProveedorDetalleBO)TumiFactory.get(ProveedorDetalleBO.class);
	RequisicionService requisicionService = (RequisicionService)TumiFactory.get(RequisicionService.class);
	RequisicionBO boRequisicion = (RequisicionBO)TumiFactory.get(RequisicionBO.class);
	RequisicionDetalleBO boRequisicionDetalle = (RequisicionDetalleBO)TumiFactory.get(RequisicionDetalleBO.class);
	InformeGerenciaService informeGerenciaService = (InformeGerenciaService)TumiFactory.get(InformeGerenciaService.class);
	InformeGerenciaBO boInformeGerencia = (InformeGerenciaBO)TumiFactory.get(InformeGerenciaBO.class);	
	ContratoService contratoService = (ContratoService)TumiFactory.get(ContratoService.class);
	ContratoBO boContrato = (ContratoBO)TumiFactory.get(ContratoBO.class);
	CuadroComparativoService cuadroComparativoService = (CuadroComparativoService)TumiFactory.get(CuadroComparativoService.class);
	CuadroComparativoBO boCuadroComparativo = (CuadroComparativoBO)TumiFactory.get(CuadroComparativoBO.class);
	CuadroComparativoProveedorBO boCuadroComparativoProveedor = (CuadroComparativoProveedorBO)TumiFactory.get(CuadroComparativoProveedorBO.class);
	OrdenCompraService ordenCompraService = (OrdenCompraService)TumiFactory.get(OrdenCompraService.class);
	OrdenCompraBO boOrdenCompra = (OrdenCompraBO)TumiFactory.get(OrdenCompraBO.class);
	DocumentoSunatBO boDocumentoSunat = (DocumentoSunatBO)TumiFactory.get(DocumentoSunatBO.class);
	DocumentoSunatService documentoSunatService = (DocumentoSunatService)TumiFactory.get(DocumentoSunatService.class);
	DocumentoSunatDetalleBO boDocumentoSunatDetalle = (DocumentoSunatDetalleBO)TumiFactory.get(DocumentoSunatDetalleBO.class);
	AdelantoSunatBO boAdelantoSunat = (AdelantoSunatBO)TumiFactory.get(AdelantoSunatBO.class);
	
	
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Proveedor> buscarProveedor(Proveedor o) throws BusinessException{
    	List<Proveedor> lista = null;
		try{
			lista = boProveedor.getPorBusqueda(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Proveedor getProveedorPorPK(ProveedorId o) throws BusinessException{
    	Proveedor dto = null;
		try{
			dto = boProveedor.getPorPk(o);
			if(dto!=null && dto.getId().getIntPersPersona()!=null){
				//log.info(dto);
				dto.setListaProveedorDetalle(boProveedorDetalle.getPorProveedor(dto));
			}
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    public Proveedor grabarProveedor(Proveedor o) throws BusinessException{
    	Proveedor dto = null;
		try{
			dto = proveedorService.grabarProveedor(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public Proveedor modificarProveedor(Proveedor o) throws BusinessException{
    	Proveedor dto = null;
		try{
			dto = proveedorService.modificarProveedor(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

    public Requisicion grabarRequisicion(Requisicion requisicion) throws BusinessException{
    	Requisicion dto = null;
		try{
			dto = requisicionService.grabarRequisicion(requisicion);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Requisicion> buscarRequisicion(Requisicion requisicion) throws BusinessException{
    	List<Requisicion> lista = null;
		try{
			lista = requisicionService.buscarRequisicion(requisicion);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public Requisicion modificarRequisicion(Requisicion requisicion, Usuario usuario) throws BusinessException{
    	Requisicion dto = null;
		try{
			dto = requisicionService.modificarRequisicion(requisicion, usuario);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public Requisicion modificarRequisicionDirecto(Requisicion requisicion) throws BusinessException{
    	Requisicion dto = null;
		try{
			dto = boRequisicion.modificar(requisicion);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RequisicionDetalle> obtenerListaRequisicionDetallePorRequisicion(Requisicion requisicion) throws BusinessException{
    	List<RequisicionDetalle> lista = null;
		try{
			lista = boRequisicionDetalle.getPorRequisicion(requisicion);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Requisicion obtenerRequisicionPorId(RequisicionId requisicionId) throws BusinessException{
    	Requisicion dto = null;
		try{
			dto = boRequisicion.getPorPk(requisicionId);			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Requisicion> obtenerListaRequisicionReferencia(Integer intIdEmpresa, Integer intParaTipoAprobacion) 
    throws BusinessException{
    	List<Requisicion> lista = null;
		try{
			lista = requisicionService.obtenerListaRequisicionReferencia(intIdEmpresa, intParaTipoAprobacion);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public InformeGerencia grabarInformeGerencia(InformeGerencia informeGerencia) throws BusinessException{
    	InformeGerencia dto = null;
		try{
			dto = informeGerenciaService.grabarInformeGerencia(informeGerencia);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}   
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<InformeGerencia> buscarInformeGerencia(InformeGerencia informeGerencia) throws BusinessException{
    	List<InformeGerencia> lista = null;
		try{
			lista = informeGerenciaService.buscarInformeGerencia(informeGerencia);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public InformeGerencia modificarInformeGerenciaDirecto(InformeGerencia informeGerencia) throws BusinessException{
    	InformeGerencia dto = null;
		try{
			dto = boInformeGerencia.modificar(informeGerencia);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public Contrato grabarContrato(Contrato contrato) throws BusinessException{
    	Contrato dto = null;
		try{
			dto = contratoService.grabarContrato(contrato);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}   
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Contrato> buscarContrato(Contrato contrato) throws BusinessException{
    	List<Contrato> lista = null;
		try{
			lista = contratoService.buscarContrato(contrato);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public Contrato modificarContratoDirecto(Contrato contrato) throws BusinessException{
    	Contrato dto = null;
		try{
			dto = boContrato.modificar(contrato);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Contrato obtenerContratoPorId(ContratoId contratoId) throws BusinessException{
    	Contrato dto = null;
		try{
			dto = boContrato.getPorPk(contratoId);			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}

    public CuadroComparativo grabarCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException{
    	CuadroComparativo dto = null;
		try{
			dto = cuadroComparativoService.grabarCuadroComparativo(cuadroComparativo);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CuadroComparativo> buscarCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException{
    	List<CuadroComparativo> lista = null;
		try{
			lista = cuadroComparativoService.buscarCuadroComparativo(cuadroComparativo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public CuadroComparativo modificarCuadroComparativoDirecto(CuadroComparativo cuadroComparativo) throws BusinessException{
    	CuadroComparativo dto = null;
		try{
			dto = boCuadroComparativo.modificar(cuadroComparativo);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public CuadroComparativo modificarCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException{
    	CuadroComparativo dto = null;
		try{
			dto = cuadroComparativoService.modificarCuadroComparativo(cuadroComparativo);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    //Modificado por cdelosrios, 29/09/2013 - se agrego el param itemRequisicion
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentoRequisicion> obtenerListaDocumentoRequisicion(Integer intIdEmpresa, Integer intParaTipoAprobacion, Integer intItemRequisicion)
    throws BusinessException{
    
    	List<DocumentoRequisicion> lista = null;
		try{
			lista = requisicionService.obtenerListaDocumentoRequisicionParaOrden(intIdEmpresa, intParaTipoAprobacion, intItemRequisicion);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}    
    //Fin modificado por cdelosrios, 29/09/2013
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CuadroComparativoProveedor> obtenerListaCuadroComparativoProveedor(CuadroComparativo cuadroComparativo) throws BusinessException{
    	List<CuadroComparativoProveedor> lista = null;
		try{
			lista = boCuadroComparativoProveedor.getPorCuadroComparativo(cuadroComparativo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public OrdenCompra grabarOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
    	OrdenCompra dto = null;
		try{
			dto = ordenCompraService.grabarOrdenCompra(ordenCompra);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<OrdenCompra> buscarOrdenCompra(OrdenCompra ordenCompraFiltro, List<Persona> listaPersonaFiltro) throws BusinessException{
    	List<OrdenCompra> lista = null;
		try{
			lista = ordenCompraService.buscarOrdenCompra(ordenCompraFiltro, listaPersonaFiltro);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public OrdenCompra obtenerOrdenCompraPorContrato(Contrato contrato) throws BusinessException{
    	OrdenCompra dto = null;
		try{
			dto = boOrdenCompra.getPorContrato(contrato);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public OrdenCompra obtenerOrdenCompraPorInformeGerencia(InformeGerencia informeGerencia) throws BusinessException{
    	OrdenCompra dto = null;
		try{
			dto = boOrdenCompra.getPorInformeGerencia(informeGerencia);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public OrdenCompra obtenerOrdenCompraPorCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException{
    	OrdenCompra dto = null;
		try{
			dto = boOrdenCompra.getPorCuadroComparativo(cuadroComparativo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    public List<DocumentoSunat> grabarDocumentoSunat(List<DocumentoSunat> listaDocumentoSunat, OrdenCompra ordenCompra) throws BusinessException{
    	List<DocumentoSunat> lista = null;
		try{
			lista = documentoSunatService.grabarDocumentoSunat(listaDocumentoSunat, ordenCompra);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentoSunat> buscarDocumentoSunat(DocumentoSunat DocumentoSunatFiltro, List<Persona> listaPersona) throws BusinessException{
    	List<DocumentoSunat> lista = null;
		try{
			lista = documentoSunatService.buscarDocumentoSunat(DocumentoSunatFiltro, listaPersona);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    //Modificado por cdelosrios, 01/11/2013
    public List<DocumentoSunat> modificarDocumentoSunat(List<DocumentoSunat> listaDocumentoSunat, OrdenCompra ordenCompra, List<DocumentoSunat> listaDocSunatLetra) throws BusinessException{
    	//DocumentoSunat dto = null;
    	List<DocumentoSunat> lista = null;
		try{
			//dto = boDocumentoSunat.modificar(documentoSunat);
			lista = documentoSunatService.modificarDocumentoSunat(listaDocumentoSunat, ordenCompra, listaDocSunatLetra);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
    //Fin modificado por cdelosrios, 01/11/2013
    
    public DocumentoSunat eliminarDocumentoSunat(DocumentoSunat o) throws BusinessException{
    	DocumentoSunat documentoSunat = null;
    	try {
    		documentoSunat = documentoSunatService.eliminarDocumentoSunat(o);
		} catch(Exception e){
   			throw new BusinessException(e);
   		}
    	return documentoSunat;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentoSunat> getListaDocumentoSunatPorOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
    	List<DocumentoSunat> lista = null;
		try{
			lista = documentoSunatService.getListaDocumentoSunatPorOrdenCompra(ordenCompra);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public OrdenCompra modificarOrdenCompraDirecto(OrdenCompra ordenCompra) throws BusinessException{
    	OrdenCompra dto = null;
		try{
			dto = boOrdenCompra.modificar(ordenCompra);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}   
    
    public DocumentoSunat modificarDocumentoSunatDirecto(DocumentoSunat documentoSunat) throws BusinessException{
    	DocumentoSunat dto = null;
		try{
			dto = boDocumentoSunat.modificar(documentoSunat);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentoSunat> buscarDocumentoSunatParaGiroDesdeFondo(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
    	List<DocumentoSunat> lista = null;
		try{
			lista = documentoSunatService.buscarParaGiroDesdeFondo(intIdPersona, intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}    
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentoRequisicion> obtenerListaDocumentoRequisicionPorRequisicion(Integer intIdEmpresa, Requisicion requisicion,
		Integer intLlamadoDesde) throws BusinessException{
    	List<DocumentoRequisicion> lista = null;
		try{
			lista = requisicionService.obtenerListaDocumentoRequisicionPorRequisicion(intIdEmpresa, requisicion, intLlamadoDesde);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}      
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DocumentoSunat agregarDocumentoSunatLetra(DocumentoSunat documentoSunat) throws BusinessException{
    	DocumentoSunat dto = null;
		try{
			dto = documentoSunatService.agregarDocumentoSunatLetra(documentoSunat);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentoSunatDetalle> getListaDocumentoSunatDetallePorOrdenCompraDetalle(OrdenCompraDetalle ordenCompraDetalle)
    throws BusinessException{
    	List<DocumentoSunatDetalle> lista = null;
		try{
			lista = boDocumentoSunatDetalle.getPorOrdenCompraDetalle(ordenCompraDetalle);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AdelantoSunat> getListaAdelantoSunatPorOrdenCompraDocumento(OrdenCompraDocumento ordenCompraDocumento)throws BusinessException{
    	List<AdelantoSunat> lista = null;
		try{
			lista = boAdelantoSunat.getPorOrdenCompraDocumento(ordenCompraDocumento);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public OrdenCompra modificarOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
    	OrdenCompra dto = null;
		try{
			dto = ordenCompraService.modificarOrdenCompra(ordenCompra);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Persona obtenerPersonaProveedorDeContrato(Contrato contrato) throws BusinessException{
    	Persona dto = null;
		try{
			dto = contratoService.obtenerPersonaProveedorDeContrato(contrato);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Persona obtenerPersonaProveedorDeInformeGerencia(InformeGerencia informeGerencia) throws BusinessException{
    	Persona dto = null;
		try{
			dto = informeGerenciaService.obtenerPersonaProveedorDeInformeGerencia(informeGerencia);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Persona obtenerPersonaProveedorDeCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException{
    	Persona dto = null;
		try{
			dto = cuadroComparativoService.obtenerPersonaProveedorDesdeCuadroComparativo(cuadroComparativo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DocumentoSunat calcularMontosDocumentoSunat(DocumentoSunat documentoSunat, TipoCambio tipoCambio) throws BusinessException{
    	DocumentoSunat dto = null;
		try{
			dto = documentoSunatService.calcularMontos(documentoSunat, tipoCambio);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}    
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Tarifa cargarTarifaIGVDesdeDocumentoSunat(DocumentoSunat documentoSunat) throws BusinessException{
    	Tarifa dto = null;
		try{
			dto = documentoSunatService.cargarTarifaIGV(documentoSunat);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
    
    //Agregado por cdelosrios, 18/11/2013
    public List<DocumentoSunat> getListDocumentoSunatPorOrdenCompraYTipoDocumento(DocumentoSunat documentoSunat) throws BusinessException{
    	List<DocumentoSunat> lista = null;
		try{
			lista = documentoSunatService.getListDocumentoSunatPorOrdenCompraYTipoDocumento(documentoSunat);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
    }
    //Fin agregado por cdelosrios, 18/11/2013
    
    //Autor: jchavez / Tarea: Creación / Fecha: 03.10.2014
    public boolean validarMontoOrdenCompraDetalle(OrdenCompraDetalle ocdNuevo, BigDecimal bdMontoValidar) throws BusinessException{
    	Boolean blnValidacion = false;
		try{
			blnValidacion = ordenCompraService.validarMontoOrdenCompraDetalle(ocdNuevo, bdMontoValidar);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return blnValidacion;
    }
    //Fin jchavez - 03.10.2014
    
    //Autor: jchavez / Tarea: Creación / Fecha: 06.10.2014
    public List<OrdenCompra> buscarDocumentoAdelantoGarantiaParaGiroPorTesoreria(Integer intIdPersona, Integer intIdEmpresa, Integer intParaTipoDocumento) throws BusinessException{
    	List<OrdenCompra> lista = null;
		try{
			lista = ordenCompraService.buscarDocumentoAdelantoGarantiaParaGiroPorTesoreria(intIdPersona, intIdEmpresa, intParaTipoDocumento);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    //Fin jchavez - 06.10.2014
}