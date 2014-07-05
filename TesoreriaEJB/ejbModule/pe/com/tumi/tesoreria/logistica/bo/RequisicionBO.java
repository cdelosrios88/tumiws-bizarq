package pe.com.tumi.tesoreria.logistica.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.logistica.dao.RequisicionDao;
import pe.com.tumi.tesoreria.logistica.dao.impl.RequisicionDaoIbatis;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionId;


public class RequisicionBO{

	private RequisicionDao dao = (RequisicionDao)TumiFactory.get(RequisicionDaoIbatis.class);

	public Requisicion grabar(Requisicion o) throws BusinessException{
		Requisicion dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Requisicion modificar(Requisicion o) throws BusinessException{
  		Requisicion dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Requisicion getPorPk(RequisicionId pId) throws BusinessException{
		Requisicion domain = null;
		List<Requisicion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemRequisicion", pId.getIntItemRequisicion());
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
	
	public List<Requisicion> getListaPorBuscar(Requisicion requisicion) throws BusinessException{
		List<Requisicion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", requisicion.getId().getIntPersEmpresa());
			mapa.put("intParaTipoAprobacion", requisicion.getIntParaTipoAprobacion());
			mapa.put("intSucuIdSucursal", requisicion.getIntSucuIdSucursal());
			//Agregado por cdelosrios, 28/09/2013
			mapa.put("dtFecRequisicionIni", requisicion.getTsFechaRequisicionIni());
			mapa.put("dtFecRequisicionFin", requisicion.getTsFechaRequisicionFin());
			mapa.put("intItemRequisicion", requisicion.getId().getIntItemRequisicion());
			//Fin agregado por cdelosrios, 28/09/2013
			lista = dao.getListaPorBuscar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Requisicion> getListaParaReferencia(Integer intIdEmpresa, Integer intParaTipoAprobacion) throws BusinessException{
		List<Requisicion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intIdEmpresa);
			mapa.put("intParaTipoAprobacion", intParaTipoAprobacion);
			lista = dao.getListaParaReferencia(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Requisicion getPorCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException{
		Requisicion domain = null;
		try{
			RequisicionId requisicionId = new RequisicionId();
			requisicionId.setIntPersEmpresa(cuadroComparativo.getIntPersEmpresaRequisicion());
			requisicionId.setIntItemRequisicion(cuadroComparativo.getIntItemRequisicion());
			
			domain = getPorPk(requisicionId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	//Modificado por cdelosrios, 29/09/2013 - se agrego el param itemRequisicion
	public List<Requisicion> getListaParaOrdenCompra(Integer intIdEmpresa, Integer intParaTipoAprobacion, Integer intItemRequisicion) throws BusinessException{
	//Fin modificado por cdelosrios, 29/09/2013
		List<Requisicion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intIdEmpresa);
			mapa.put("intParaTipoAprobacion", intParaTipoAprobacion);
			mapa.put("intItemRequisicion", intItemRequisicion);
			lista = dao.getListaParaOrdenCompra(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}