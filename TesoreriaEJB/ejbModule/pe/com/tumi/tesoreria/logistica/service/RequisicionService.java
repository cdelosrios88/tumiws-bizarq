package pe.com.tumi.tesoreria.logistica.service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DocumentoRequisicion;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.logistica.bo.ContratoBO;
import pe.com.tumi.tesoreria.logistica.bo.CuadroComparativoBO;
import pe.com.tumi.tesoreria.logistica.bo.CuadroComparativoProveedorBO;
import pe.com.tumi.tesoreria.logistica.bo.InformeGerenciaBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraBO;
import pe.com.tumi.tesoreria.logistica.bo.RequisicionBO;
import pe.com.tumi.tesoreria.logistica.bo.RequisicionDetalleBO;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerencia;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionDetalle;


public class RequisicionService {

	protected static Logger log = Logger.getLogger(RequisicionService.class);
	
	RequisicionBO boRequisicion = (RequisicionBO)TumiFactory.get(RequisicionBO.class);
	RequisicionDetalleBO boRequisicionDetalle = (RequisicionDetalleBO)TumiFactory.get(RequisicionDetalleBO.class);
	CuadroComparativoBO boCuadroComparativo = (CuadroComparativoBO)TumiFactory.get(CuadroComparativoBO.class);
	ContratoBO boContrato = (ContratoBO)TumiFactory.get(ContratoBO.class);
	InformeGerenciaBO boInformeGerencia = (InformeGerenciaBO)TumiFactory.get(InformeGerenciaBO.class);
	OrdenCompraBO boOrdenCompra = (OrdenCompraBO)TumiFactory.get(OrdenCompraBO.class);
	CuadroComparativoService cuadroComparativoService = (CuadroComparativoService)TumiFactory.get(CuadroComparativoService.class);
	CuadroComparativoProveedorBO boCuadroComparativoProveedor = (CuadroComparativoProveedorBO)TumiFactory.get(CuadroComparativoProveedorBO.class);
	
	
	public Requisicion grabarRequisicion(Requisicion requisicion) throws BusinessException{
		try{
			
			log.info(requisicion);
			boRequisicion.grabar(requisicion);
			
			for(RequisicionDetalle requisicionDetalle : requisicion.getListaRequisicionDetalle()){
				grabarRequisicionDetalle(requisicion, requisicionDetalle);
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return requisicion;
	}
	
	private RequisicionDetalle grabarRequisicionDetalle(Requisicion requisicion, RequisicionDetalle requisicionDetalle)throws Exception{
		requisicionDetalle.getId().setIntPersEmpresa(requisicion.getId().getIntPersEmpresa());
		requisicionDetalle.getId().setIntItemRequisicion(requisicion.getId().getIntItemRequisicion());
		
		/*requisicionDetalle.setIntPersEmpresaUsuario(requisicion.getIntPersEmpresaSolicitante());
		requisicionDetalle.setIntPersPersonaUsuario(requisicion.getIntPersPersonaSolicitante());
		*/requisicionDetalle.setTsFechaRegistro(new Timestamp(new Date().getTime()));
		
		log.info(requisicionDetalle);
		return boRequisicionDetalle.grabar(requisicionDetalle);
	}

	public Requisicion modificarRequisicion(Requisicion requisicion, Usuario usuario) throws BusinessException{
		try{
			
			log.info(requisicion);
			boRequisicion.modificar(requisicion);
			
			List<RequisicionDetalle> listaIU = new ArrayList<RequisicionDetalle>();
			List<RequisicionDetalle> listaBD = boRequisicionDetalle.getPorRequisicion(requisicion);
			
			for(RequisicionDetalle requisicionDetalle : requisicion.getListaRequisicionDetalle()){
				if(requisicionDetalle.getId().getIntItemRequisicion()==null){
					grabarRequisicionDetalle(requisicion, requisicionDetalle);
				}else{
					listaIU.add(requisicionDetalle);
				}
			}
			
			boolean seEncuentraEnIU;
			for(RequisicionDetalle requisicionDetalleBD : listaBD){
				seEncuentraEnIU = Boolean.FALSE;
				for(RequisicionDetalle requisicionDetalleIU : listaIU){
					if(requisicionDetalleBD.getId().getIntItemRequisicionDetalle().equals(requisicionDetalleIU.getId().getIntItemRequisicionDetalle())){
						seEncuentraEnIU = Boolean.TRUE;
						boRequisicionDetalle.modificar(requisicionDetalleIU);
						break;
					}
				}
				if(!seEncuentraEnIU){
					requisicionDetalleBD.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					requisicionDetalleBD.setIntPersEmpresaAnula(usuario.getPerfil().getId().getIntPersEmpresaPk());
					requisicionDetalleBD.setIntPersPersonaAnula(usuario.getPersona().getIntIdPersona());
					requisicionDetalleBD.setTsFechaAnula(new Timestamp(new Date().getTime()));
					boRequisicionDetalle.modificar(requisicionDetalleBD);
				}else{
					
				}
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return requisicion;
	}
	
	public List<Requisicion> buscarRequisicion(Requisicion requisicionFiltro) throws BusinessException{
		List<Requisicion> listaRequisicion = null;
		try{			
			log.info(requisicionFiltro);
			listaRequisicion = boRequisicion.getListaPorBuscar(requisicionFiltro);
			for(Requisicion requisicion : listaRequisicion){
				OrdenCompra ordenCompra = boOrdenCompra.getPorRequisicion(requisicion);
				if(ordenCompra!=null){
					ordenCompra.setIntDiasPlazo(obtenerDiasEntreFechas(ordenCompra.getTsFechaAtencionLog(),ordenCompra.getTsFechaAtencionReal()));
					requisicion.setOrdenCompra(ordenCompra);	
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaRequisicion;
	}
	
	public List<Requisicion> obtenerListaRequisicionReferencia(Integer intIdEmpresa, Integer intParaTipoAprobacion) throws BusinessException{
		List<Requisicion> listaRequisicion = null;
		try{
			
			listaRequisicion = boRequisicion.getListaParaReferencia(intIdEmpresa, intParaTipoAprobacion);		
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaRequisicion;
	}
	
	private Integer obtenerDiasEntreFechas(Timestamp tsFechaInicio, Timestamp tsFechaFin)throws Exception{
		if(tsFechaInicio ==null  || tsFechaFin == null)
			return 0;
		
		return (int)( (tsFechaFin.getTime() - tsFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
	}
	
	//Modificado por cdelosrios, 29/09/2013 - se agrego el param itemRequisicion
	public List<DocumentoRequisicion> obtenerListaDocumentoRequisicionParaOrden(Integer intIdEmpresa, Integer intParaTipoAprobacion, Integer intItemRequisicion) 
		throws BusinessException{
	//Fin modificado por cdelosrios, 29/09/2013
		List<DocumentoRequisicion> listaDocumentoRequisicion = new ArrayList<DocumentoRequisicion>();
		try{
			List<Requisicion> listaRequisicion =  boRequisicion.getListaParaOrdenCompra(intIdEmpresa, intParaTipoAprobacion, intItemRequisicion);
			
			for(Requisicion requisicion : listaRequisicion){
				if(requisicion.getIntParaEstadoAprobacionLogistica()==null 
				|| !requisicion.getIntParaEstadoAprobacionLogistica().equals(Constante.PARAM_T_ESTADOREQUISICION_APROBADO)
				|| !requisicion.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					continue;
				}				
				log.info(requisicion);
				
				listaDocumentoRequisicion.addAll(obtenerListaDocumentoRequisicionPorRequisicion(
						intIdEmpresa, requisicion, Constante.DOCUMENTOREQUISICION_LLAMADO_ORDENCOMPRA));
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaDocumentoRequisicion;
	}
	
	public List<DocumentoRequisicion> obtenerListaDocumentoRequisicionPorRequisicion(Integer intIdEmpresa, Requisicion requisicion, 
		Integer intLlamadoDesde) throws BusinessException{
		List<DocumentoRequisicion> listaDocumentoRequisicion = new ArrayList<DocumentoRequisicion>();
		try{
			if(requisicion.getIntParaTipoAprobacion().equals(Constante.PARAM_T_APROBACION_EVALUACIONPROVEEDORES)){				
				CuadroComparativo cuadroComparativoFiltro = new CuadroComparativo();
				cuadroComparativoFiltro.getId().setIntPersEmpresa(intIdEmpresa);
				cuadroComparativoFiltro.setIntPersEmpresaRequisicion(requisicion.getId().getIntPersEmpresa());
				cuadroComparativoFiltro.setIntItemRequisicion(requisicion.getId().getIntItemRequisicion());
				cuadroComparativoFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				cuadroComparativoFiltro.setIntParaEstadoAprobacion(Constante.PARAM_T_ESTADOAPROBACIONCUADRO_APROBADO);
				List<CuadroComparativo> lista = boCuadroComparativo.getPorBuscar(cuadroComparativoFiltro);
				for(CuadroComparativo cuadroComparativo : lista){
					if(cuadroComparativo.getIntParaEstadoAprobacion().equals(Constante.PARAM_T_ESTADOAPROBACIONCUADRO_PENDIENTE))
						continue;
					log.info(cuadroComparativo);
					cuadroComparativo.setListaCuadroComparativoProveedor(boCuadroComparativoProveedor.getPorCuadroComparativo(cuadroComparativo));
					cuadroComparativoService.cargarProveedorAprobado(cuadroComparativo);
					DocumentoRequisicion documentoRequisicion = new DocumentoRequisicion();
					documentoRequisicion.setIntParaTipoAprobacion(Constante.PARAM_T_APROBACION_EVALUACIONPROVEEDORES);
					documentoRequisicion.setRequisicion(requisicion);
					documentoRequisicion.setCuadroComparativo(cuadroComparativo);
					listaDocumentoRequisicion.add(documentoRequisicion);
				}
			
			}else if(requisicion.getIntParaTipoAprobacion().equals(Constante.PARAM_T_APROBACION_CONTRATO)){
				Contrato contratoFiltro = new Contrato();
				contratoFiltro.getId().setIntPersEmpresa(intIdEmpresa);
				contratoFiltro.setIntPersEmpresaRequisicion(requisicion.getId().getIntPersEmpresa());
				contratoFiltro.setIntItemRequisicion(requisicion.getId().getIntItemRequisicion());
				contratoFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				List<Contrato> lista = boContrato.getPorBuscar(contratoFiltro);
				for(Contrato contrato : lista){
					log.info(contrato);
					DocumentoRequisicion documentoRequisicion = new DocumentoRequisicion();
					documentoRequisicion.setIntParaTipoAprobacion(Constante.PARAM_T_APROBACION_CONTRATO);
					documentoRequisicion.setRequisicion(requisicion);
					documentoRequisicion.setContrato(contrato);
					listaDocumentoRequisicion.add(documentoRequisicion);
				}
				
			}else if(requisicion.getIntParaTipoAprobacion().equals(Constante.PARAM_T_APROBACION_INFORME)){
				InformeGerencia informeGerenciaFiltro = new InformeGerencia();
				informeGerenciaFiltro.getId().setIntPersEmpresa(intIdEmpresa);
				informeGerenciaFiltro.setIntPersEmpresaRequisicion(requisicion.getId().getIntPersEmpresa());
				informeGerenciaFiltro.setIntItemRequisicion(requisicion.getId().getIntItemRequisicion());
				informeGerenciaFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				List<InformeGerencia> lista = boInformeGerencia.getPorBuscar(informeGerenciaFiltro);
				for(InformeGerencia informeGerencia : lista){
					log.info(informeGerencia);
					DocumentoRequisicion documentoRequisicion = new DocumentoRequisicion();
					documentoRequisicion.setIntParaTipoAprobacion(Constante.PARAM_T_APROBACION_INFORME);
					documentoRequisicion.setRequisicion(requisicion);
					documentoRequisicion.setInformeGerencia(informeGerencia);
					listaDocumentoRequisicion.add(documentoRequisicion);
				}
			
			}else if(requisicion.getIntParaTipoAprobacion().equals(Constante.PARAM_T_APROBACION_ORDENCOMPRA)
				||	requisicion.getIntParaTipoAprobacion().equals(Constante.PARAM_T_APROBACION_CAJACHICA)){
				OrdenCompra ordenCompra = boOrdenCompra.getPorRequisicion(requisicion);
				log.info(ordenCompra);
				log.info(requisicion);
				DocumentoRequisicion documentoRequisicion = new DocumentoRequisicion();
				documentoRequisicion.setIntParaTipoAprobacion(requisicion.getIntParaTipoAprobacion());
				documentoRequisicion.setRequisicion(requisicion);				
				//No se debe añadir una requisicion que ya ha sido enlazada a una Orden de Compra.
				if(intLlamadoDesde.equals(Constante.DOCUMENTOREQUISICION_LLAMADO_DESDEREQUISICION) && ordenCompra!=null){
					listaDocumentoRequisicion.add(documentoRequisicion);
				}else if(intLlamadoDesde.equals(Constante.DOCUMENTOREQUISICION_LLAMADO_ORDENCOMPRA) && ordenCompra==null){
					listaDocumentoRequisicion.add(documentoRequisicion);
				}
			
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaDocumentoRequisicion;
	}
}