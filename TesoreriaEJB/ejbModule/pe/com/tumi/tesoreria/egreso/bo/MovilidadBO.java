package pe.com.tumi.tesoreria.egreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.dao.MovilidadDao;
import pe.com.tumi.tesoreria.egreso.dao.impl.MovilidadDaoIbatis;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.domain.MovilidadId;


public class MovilidadBO{

	private MovilidadDao dao = (MovilidadDao)TumiFactory.get(MovilidadDaoIbatis.class);

	public Movilidad grabar(Movilidad o) throws BusinessException{
		Movilidad dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Movilidad modificar(Movilidad o) throws BusinessException{
  		Movilidad dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Movilidad getPorPk(MovilidadId pId) throws BusinessException{
		Movilidad domain = null;
		List<Movilidad> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaMovilidad", pId.getIntPersEmpresaMovilidad());
			mapa.put("intItemMovilidad", pId.getIntItemMovilidad());
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
	
	public List<Movilidad> getPorBusqueda(Movilidad o) throws BusinessException{
		List<Movilidad> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();			
			mapa.put("intPeriodo", o.getIntPeriodo());
			mapa.put("tsFechaDesde", o.getTsFechaDesde());
			mapa.put("tsFechaHasta", o.getTsFechaHasta());
			mapa.put("intParaEstado", o.getIntParaEstado());
			mapa.put("intPersPersonaUsuario", o.getIntPersPersonaUsuario());
			lista = dao.getListaPorBusqueda(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Movilidad> getPorPersona(Movilidad o) throws BusinessException{
		List<Movilidad> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaUsuario", o.getIntPersEmpresaUsuario());
			mapa.put("intPersPersonaUsuario", o.getIntPersPersonaUsuario());
			lista = dao.getListaPorPersona(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<Movilidad> getPorEgreso(Egreso egreso) throws BusinessException{
		List<Movilidad> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaEgreso", egreso.getId().getIntPersEmpresaEgreso());
			mapa.put("intItemEgresoGeneral", egreso.getId().getIntItemEgresoGeneral());
			lista = dao.getListaPorEgreso(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * Agregado 16.12.2013 JCHAVEZ
	 * Retorna lista de Planilla Movimiento según nuevos filtros de búsqueda
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<Movilidad> getPorFiltroBusqueda(Movilidad o) throws BusinessException{
		List<Movilidad> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intTipoBusqueda", o.getIntTipoBusqueda());
			mapa.put("intPersEmpresaMovilidad", o.getId().getIntPersEmpresaMovilidad());
			mapa.put("strTextoFiltro", o.getStrTextoFiltro());
			mapa.put("intSucuIdSucursal", o.getIntSucuIdSucursal());
			mapa.put("intPeriodo", o.getIntPeriodo());
			mapa.put("tsFechaDesde", o.getTsFechaDesde());
			mapa.put("tsFechaHasta", o.getTsFechaHasta());
			mapa.put("intParaEstadoPago", o.getIntParaEstadoPago());
			mapa.put("intParaEstado", o.getIntParaEstado());
			lista = dao.getPorFiltroBusqueda(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}