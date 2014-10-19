package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.OrdenCompraDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.OrdenCompraDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerencia;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraId;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;


public class OrdenCompraBO{

	private OrdenCompraDao dao = (OrdenCompraDao)TumiFactory.get(OrdenCompraDaoIbatis.class);
	protected static Logger log = Logger.getLogger(OrdenCompraBO.class);
	public OrdenCompra grabar(OrdenCompra o) throws BusinessException{
		OrdenCompra dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public OrdenCompra modificar(OrdenCompra o) throws BusinessException{
  		OrdenCompra dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public OrdenCompra getPorPk(OrdenCompraId pId) throws BusinessException{
		OrdenCompra domain = null;
		List<OrdenCompra> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemOrdenCompra", pId.getIntItemOrdenCompra());
			lista = dao.getListaPorPk(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<OrdenCompra> getPorBuscar(OrdenCompra ordenCompra) throws BusinessException{
		List<OrdenCompra> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", ordenCompra.getId().getIntPersEmpresa());
			mapa.put("intParaEstadoOrden", ordenCompra.getIntParaEstadoOrden());
			mapa.put("intParaEstado", ordenCompra.getIntParaEstado());
			mapa.put("dtFiltroDesde", ordenCompra.getDtFiltroDesde());
			mapa.put("dtFiltroHasta", ordenCompra.getDtFiltroHasta());
			mapa.put("intPersEmpresaRequisicion", ordenCompra.getIntPersEmpresaRequisicion());
			mapa.put("intItemRequisicion", ordenCompra.getIntItemRequisicion());
			mapa.put("intPersEmpresaContrato", ordenCompra.getIntPersEmpresaContrato());
			mapa.put("intItemContrato", ordenCompra.getIntItemContrato());
			mapa.put("intPersEmpresaInformeGerencia", ordenCompra.getIntPersEmpresaInformeGerencia());
			mapa.put("intItemInformeGerencia", ordenCompra.getIntItemInformeGerencia());
			mapa.put("intPersEmpresaCuadroComparativo", ordenCompra.getIntPersEmpresaCuadroComparativo());
			mapa.put("intItemCuadroComparativo", ordenCompra.getIntItemCuadroComparativo());
			//Agregado por cdelosrios, 29/09/2013
			mapa.put("intItemOrdenCompra", ordenCompra.getId().getIntItemOrdenCompra());
			//Fin agregado por cdelosrios, 29/09/2013
			//Autor: jchavez / Tarea: Creacion / Fecha: 06.10.2014
			mapa.put("intPersEmpresaProveedor", ordenCompra.getIntPersEmpresaProveedor());
			mapa.put("intPersPersonaProveedor", ordenCompra.getIntPersPersonaProveedor());
			//Fin jchavez - 06.10.2014
			lista = dao.getListaPorBuscar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public OrdenCompra getPorRequisicion(Requisicion requisicion) throws BusinessException{
		OrdenCompra domain = null;
		List<OrdenCompra> lista = null;
		try{
			log.info(requisicion.getId());
			OrdenCompra ordenCompra = new OrdenCompra();
			ordenCompra.getId().setIntPersEmpresa(requisicion.getId().getIntPersEmpresa());
			ordenCompra.setIntPersEmpresaRequisicion(requisicion.getId().getIntPersEmpresa());
			ordenCompra.setIntItemRequisicion(requisicion.getId().getIntItemRequisicion());
			ordenCompra.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			lista = getPorBuscar(ordenCompra);
			if(lista!=null){
				for(OrdenCompra ordenCompra1 : lista){
					log.info(ordenCompra1);
				}
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public OrdenCompra getPorContrato(Contrato contrato) throws BusinessException{
		OrdenCompra domain = null;
		List<OrdenCompra> lista = null;
		try{
			log.info(contrato.getId());
			OrdenCompra ordenCompra = new OrdenCompra();
			ordenCompra.getId().setIntPersEmpresa(contrato.getId().getIntPersEmpresa());
			ordenCompra.setIntPersEmpresaContrato(contrato.getId().getIntPersEmpresa());
			ordenCompra.setIntItemContrato(contrato.getId().getIntItemContrato());
			ordenCompra.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			lista = getPorBuscar(ordenCompra);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public OrdenCompra getPorInformeGerencia(InformeGerencia informeGerencia) throws BusinessException{
		OrdenCompra domain = null;
		List<OrdenCompra> lista = null;
		try{
			OrdenCompra ordenCompra = new OrdenCompra();
			ordenCompra.getId().setIntPersEmpresa(informeGerencia.getId().getIntPersEmpresa());
			ordenCompra.setIntPersEmpresaInformeGerencia(informeGerencia.getId().getIntPersEmpresa());
			ordenCompra.setIntItemInformeGerencia(informeGerencia.getId().getIntItemInformeGerencia());
			ordenCompra.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			lista = getPorBuscar(ordenCompra);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public OrdenCompra getPorCuadroComparativo(CuadroComparativo cuadoComparativo) throws BusinessException{
		OrdenCompra domain = null;
		List<OrdenCompra> lista = null;
		try{
			OrdenCompra ordenCompra = new OrdenCompra();
			ordenCompra.getId().setIntPersEmpresa(cuadoComparativo.getId().getIntPersEmpresa());
			ordenCompra.setIntPersEmpresaCuadroComparativo(cuadoComparativo.getId().getIntPersEmpresa());
			ordenCompra.setIntItemCuadroComparativo(cuadoComparativo.getId().getIntItemCuadroComparativo());
			ordenCompra.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			lista = getPorBuscar(ordenCompra);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public OrdenCompra getPorDocumentoSunat(DocumentoSunat documentoSunat) throws BusinessException{
		try{
			OrdenCompraId ordenCompraId = new OrdenCompraId();
			ordenCompraId.setIntPersEmpresa(documentoSunat.getIntPersEmpresaOrden());
			ordenCompraId.setIntItemOrdenCompra(documentoSunat.getIntItemOrdenCompra());
			return getPorPk(ordenCompraId);			
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}	
}