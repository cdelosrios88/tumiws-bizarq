package pe.com.tumi.contabilidad.cierre.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.LibroDiarioDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.LibroDiarioDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LibroDiarioBO {
	
	private LibroDiarioDao dao = (LibroDiarioDao)TumiFactory.get(LibroDiarioDaoIbatis.class);

	public LibroDiario grabar(LibroDiario o) throws BusinessException{
		LibroDiario dto = null;
		try{
			if(o.getStrGlosa() == null) o.setStrGlosa("Glosa x defecto 2-BO");
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public LibroDiario modificar(LibroDiario o) throws BusinessException{
  		LibroDiario dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public LibroDiario getPorPk(LibroDiarioId pId) throws BusinessException{
		LibroDiario domain = null;
		List<LibroDiario> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLibro", pId.getIntPersEmpresaLibro());
			mapa.put("intContPeriodoLibro", pId.getIntContPeriodoLibro());
			mapa.put("intContCodigoLibro", pId.getIntContCodigoLibro());
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

	public List<LibroDiario> buscarParaCodigoLibro(LibroDiarioId pId) throws BusinessException{
		List<LibroDiario> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLibro", pId.getIntPersEmpresaLibro());
			mapa.put("intContPeriodoLibro", pId.getIntContPeriodoLibro());
			lista = dao.buscarParaCodigo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public LibroDiario buscarUltimoParaCodigoLibro(LibroDiarioId pId) throws BusinessException{
		List<LibroDiario> lista = null;
		LibroDiario diario = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLibro", pId.getIntPersEmpresaLibro());
			mapa.put("intContPeriodoLibro", pId.getIntContPeriodoLibro());
			lista = dao.buscarUltimoParaCodigo(mapa);
			
			if(lista == null || lista.isEmpty()){
			    diario=	null;
			}else{
				diario=	lista.get(0);
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return diario;
	}

	public List<LibroDiario> getListaPorBuscar(LibroDiario libroDiario) throws BusinessException{
		List<LibroDiario> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLibro", libroDiario.getId().getIntPersEmpresaLibro());
			mapa.put("tsFechaRegistro", libroDiario.getTsFechaRegistro());
			mapa.put("intParaEstado", libroDiario.getIntParaEstado());
			lista = dao.getListaPorBuscar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}