package pe.com.tumi.tesoreria.logistica.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraDetalleBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraDocumentoBO;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDocumento;


public class OrdenCompraService {

	protected static Logger log = Logger.getLogger(OrdenCompraService.class);
	
	OrdenCompraBO boOrdenCompra = (OrdenCompraBO)TumiFactory.get(OrdenCompraBO.class);
	OrdenCompraDetalleBO boOrdenCompraDetalle = (OrdenCompraDetalleBO)TumiFactory.get(OrdenCompraDetalleBO.class);
	OrdenCompraDocumentoBO boOrdenCompraDocumento = (OrdenCompraDocumentoBO)TumiFactory.get(OrdenCompraDocumentoBO.class);
	
	public OrdenCompra grabarOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
		try{			
			log.info(ordenCompra);			
			ordenCompra = boOrdenCompra.grabar(ordenCompra);
			
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				ordenCompraDetalle.getId().setIntPersEmpresa(ordenCompra.getId().getIntPersEmpresa());
				ordenCompraDetalle.getId().setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
				log.info(ordenCompraDetalle);		
				boOrdenCompraDetalle.grabar(ordenCompraDetalle);
			}
			
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				ordenCompraDocumento.getId().setIntPersEmpresa(ordenCompra.getId().getIntPersEmpresa());
				ordenCompraDocumento.getId().setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
				log.info(ordenCompraDocumento);
				boOrdenCompraDocumento.grabar(ordenCompraDocumento);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ordenCompra;
	}

	
	public List<OrdenCompra> buscarOrdenCompra(OrdenCompra ordenCompraFiltro, List<Persona> listaPersonaFiltro) throws BusinessException{
		List<OrdenCompra> listaOrdenCompra = null;
		try{			
			log.info(ordenCompraFiltro);
			listaOrdenCompra = boOrdenCompra.getPorBuscar(ordenCompraFiltro);
			for(OrdenCompra ordenCompra : listaOrdenCompra){
				ordenCompra.setListaOrdenCompraDetalle(boOrdenCompraDetalle.getPorOrdenCompra(ordenCompra));
				ordenCompra.setListaOrdenCompraDocumento(boOrdenCompraDocumento.getPorOrdenCompra(ordenCompra));
				ordenCompra.setBdMontoTotalDetalle(new BigDecimal(0));
				for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle())
					ordenCompra.setBdMontoTotalDetalle(ordenCompra.getBdMontoTotalDetalle().add(ordenCompraDetalle.getBdPrecioTotal()));				
			}
			
			if(listaPersonaFiltro==null || listaPersonaFiltro.isEmpty())
				return listaOrdenCompra;
			
			List<OrdenCompra> listaOrdenCompraTemp = new ArrayList<OrdenCompra>();
			for(Persona persona : listaPersonaFiltro){
				for(OrdenCompra ordenCompra : listaOrdenCompra)
					if(ordenCompra.getIntPersPersonaProveedor().equals(persona.getIntIdPersona()))
						listaOrdenCompraTemp.add(ordenCompra);				
			}
			listaOrdenCompra = listaOrdenCompraTemp;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaOrdenCompra;
	}
	
	private void manejarModificarOrdenCompraDetalle(OrdenCompra ordenCompra)throws Exception{
		List<OrdenCompraDetalle> listaIU = new ArrayList<OrdenCompraDetalle>();
		List<OrdenCompraDetalle> listaBD = boOrdenCompraDetalle.getPorOrdenCompra(ordenCompra);
		
		for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
			if(ordenCompraDetalle.getId().getIntItemOrdenCompraDetalle()==null){
				ordenCompraDetalle.getId().setIntPersEmpresa(ordenCompra.getId().getIntPersEmpresa());
				ordenCompraDetalle.getId().setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
				log.info(ordenCompraDetalle);
				boOrdenCompraDetalle.grabar(ordenCompraDetalle);
			}else{
				listaIU.add(ordenCompraDetalle);
			}
		}
		
		for(OrdenCompraDetalle ordenCompraDetalleBD : listaBD){
			boolean seEncuentraEnIU = Boolean.FALSE;
			for(OrdenCompraDetalle ordenCompraDetalleIU : listaIU){
				if(ordenCompraDetalleIU.getId().getIntItemOrdenCompraDetalle().equals(ordenCompraDetalleBD.getId().getIntItemOrdenCompraDetalle())){
					seEncuentraEnIU = Boolean.TRUE;
					break;
				}
			}
			if(!seEncuentraEnIU){
				ordenCompraDetalleBD.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boOrdenCompraDetalle.modificar(ordenCompraDetalleBD);
			}
		}
	}
	
	private void manejarModificarOrdenCompraDocumento(OrdenCompra ordenCompra)throws Exception{
		List<OrdenCompraDocumento> listaIU = new ArrayList<OrdenCompraDocumento>();
		List<OrdenCompraDocumento> listaBD = boOrdenCompraDocumento.getPorOrdenCompra(ordenCompra);
		
		for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
			if(ordenCompraDocumento.getId().getIntItemOrdenCompraDocumento()==null){
				ordenCompraDocumento.getId().setIntPersEmpresa(ordenCompra.getId().getIntPersEmpresa());
				ordenCompraDocumento.getId().setIntItemOrdenCompra(ordenCompra.getId().getIntItemOrdenCompra());
				log.info(ordenCompraDocumento);
				boOrdenCompraDocumento.grabar(ordenCompraDocumento);
			}else{
				listaIU.add(ordenCompraDocumento);
			}
		}
		
		for(OrdenCompraDocumento ordenCompraDocumentoBD : listaBD){
			boolean seEncuentraEnIU = Boolean.FALSE;
			for(OrdenCompraDocumento ordenCompraDocumentoIU : listaIU){
				if(ordenCompraDocumentoIU.getId().getIntItemOrdenCompraDocumento().equals(
						ordenCompraDocumentoBD.getId().getIntItemOrdenCompraDocumento())){
					seEncuentraEnIU = Boolean.TRUE;
					break;
				}
			}
			if(!seEncuentraEnIU){
				ordenCompraDocumentoBD.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boOrdenCompraDocumento.modificar(ordenCompraDocumentoBD);
			}
		}
	}	
	
	public OrdenCompra modificarOrdenCompra(OrdenCompra ordenCompra) throws BusinessException{
		try{
			log.info(ordenCompra);
			ordenCompra = boOrdenCompra.modificar(ordenCompra);
			
			manejarModificarOrdenCompraDetalle(ordenCompra);
			manejarModificarOrdenCompraDocumento(ordenCompra);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return ordenCompra;
	}
	
	public void verificarEstadoOrdenCompra(OrdenCompra ordenCompra)throws BusinessException{
		try{
			ordenCompra = boOrdenCompra.modificar(ordenCompra);
			
			boolean todoOrdenCompraDetalleSaldoCero = Boolean.TRUE;
			for(OrdenCompraDetalle ordenCompraDetalle : ordenCompra.getListaOrdenCompraDetalle()){
				if(!ordenCompraDetalle.getBdMontoSaldo().equals(new BigDecimal(0))){
					todoOrdenCompraDetalleSaldoCero = Boolean.FALSE;
					break;
				}
			}
			
			boolean todoOrdenCompraDocumentoSaldoCero = Boolean.TRUE;
			for(OrdenCompraDocumento ordenCompraDocumento : ordenCompra.getListaOrdenCompraDocumento()){
				if(!ordenCompraDocumento.getBdMontoPagado().equals(ordenCompraDocumento.getBdMontoIngresado())){
					todoOrdenCompraDocumentoSaldoCero = Boolean.FALSE;
					break;
				}
			}
			
			if(todoOrdenCompraDetalleSaldoCero && todoOrdenCompraDocumentoSaldoCero){
				ordenCompra.setIntParaEstadoOrden(Constante.PARAM_T_ESTADOORDEN_CERRADO);
				boOrdenCompra.modificar(ordenCompra);
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
}