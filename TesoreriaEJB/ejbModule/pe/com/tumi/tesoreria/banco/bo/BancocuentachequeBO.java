package pe.com.tumi.tesoreria.banco.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.banco.dao.BancocuentachequeDao;
import pe.com.tumi.tesoreria.banco.dao.impl.BancocuentachequeDaoIbatis;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;

import pe.com.tumi.tesoreria.banco.domain.BancocuentachequeId;

public class BancocuentachequeBO{

	private BancocuentachequeDao dao = (BancocuentachequeDao)TumiFactory.get(BancocuentachequeDaoIbatis.class);

	public Bancocuentacheque grabar(Bancocuentacheque o) throws BusinessException{
		Bancocuentacheque dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Bancocuentacheque modificar(Bancocuentacheque o) throws BusinessException{
     	Bancocuentacheque dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Bancocuentacheque getPorPk(BancocuentachequeId pId) throws BusinessException{
		Bancocuentacheque domain = null;
		List<Bancocuentacheque> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPk", pId.getIntEmpresaPk());
			mapa.put("intItembancocuenta", pId.getIntItembancocuenta());
			mapa.put("intItembancofondo", pId.getIntItembancofondo());
			mapa.put("intItembancuencheque", pId.getIntItembancuencheque());
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
	
	public List<Bancocuentacheque> getPorBancoCuenta(Bancocuenta o) throws BusinessException{
		List<Bancocuentacheque> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPk", o.getId().getIntEmpresaPk());
			mapa.put("intItembancofondo", o.getId().getIntItembancofondo());
			mapa.put("intItembancocuenta", o.getId().getIntItembancocuenta());
			lista = dao.getListaPorBancoCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
