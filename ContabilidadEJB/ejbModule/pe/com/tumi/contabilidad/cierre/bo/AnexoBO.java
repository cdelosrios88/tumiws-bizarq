package pe.com.tumi.contabilidad.cierre.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.AnexoDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.AnexoDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.Anexo;
import pe.com.tumi.contabilidad.cierre.domain.AnexoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class AnexoBO {
	private AnexoDao dao = (AnexoDao)TumiFactory.get(AnexoDaoIbatis.class);

	public Anexo grabar(Anexo o) throws BusinessException{
		Anexo dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public Anexo modificar(Anexo o) throws BusinessException{
  		Anexo dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Anexo getPorPk(AnexoId pId) throws BusinessException{
		Anexo domain = null;
		List<Anexo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAnexo",pId.getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",pId.getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 	pId.getIntParaTipoAnexo());
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

	public List<Anexo> getPorBuscar(Anexo o) throws BusinessException{
		List<Anexo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAnexo",		o.getId().getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",		o.getId().getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 		o.getId().getIntParaTipoAnexo());
			mapa.put("intParaTipoLibroAnexo", 	o.getIntParaTipoLibroAnexo());
			lista = dao.getListaPorBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void eliminar(Anexo o) throws BusinessException{
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAnexo",o.getId().getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",o.getId().getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo",   o.getId().getIntParaTipoAnexo());
			dao.eliminar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
}
