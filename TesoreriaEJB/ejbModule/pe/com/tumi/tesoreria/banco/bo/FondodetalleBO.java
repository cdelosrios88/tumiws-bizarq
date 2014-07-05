package pe.com.tumi.tesoreria.banco.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.banco.dao.FondodetalleDao;
import pe.com.tumi.tesoreria.banco.dao.impl.FondodetalleDaoIbatis;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.Fondodetalle;

import pe.com.tumi.tesoreria.banco.domain.FondodetalleId;

public class FondodetalleBO{

	private FondodetalleDao dao = (FondodetalleDao)TumiFactory.get(FondodetalleDaoIbatis.class);

	public Fondodetalle grabar(Fondodetalle o) throws BusinessException{
		Fondodetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Fondodetalle modificar(Fondodetalle o) throws BusinessException{
     	Fondodetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Fondodetalle getPorPk(FondodetalleId pId) throws BusinessException{
		Fondodetalle domain = null;
		List<Fondodetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPk", pId.getIntEmpresaPk());
			mapa.put("intItembancofondo", pId.getIntItembancofondo());
			mapa.put("intItemfondodetalle", pId.getIntItemfondodetalle());
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
	
	public List<Fondodetalle> getPorBancoFondo(Bancofondo o) throws BusinessException{
		List<Fondodetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPk", o.getId().getIntEmpresaPk());
			mapa.put("intItembancofondo", o.getId().getIntItembancofondo());
			lista = dao.getListaPorBancoFondo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Fondodetalle> getPorSubSucursalPK(SubSucursalPK o) throws BusinessException{
		List<Fondodetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresasucursalPk", o.getIntPersEmpresaPk());
			mapa.put("intIdsucursal", o.getIntIdSucursal());
			mapa.put("intIdsubsucursal", o.getIntIdSubSucursal());
			lista = dao.getListaPorSubSucursalPK(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
