package pe.com.tumi.tesoreria.ingreso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.ingreso.dao.DepositoIngresoDao;
import pe.com.tumi.tesoreria.ingreso.dao.impl.DepositoIngresoDaoIbatis;
import pe.com.tumi.tesoreria.ingreso.domain.DepositoIngreso;
import pe.com.tumi.tesoreria.ingreso.domain.DepositoIngresoId;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;


public class DepositoIngresoBO{

	private DepositoIngresoDao dao = (DepositoIngresoDao)TumiFactory.get(DepositoIngresoDaoIbatis.class);

	public DepositoIngreso grabar(DepositoIngreso o) throws BusinessException{
		DepositoIngreso dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public DepositoIngreso modificar(DepositoIngreso o) throws BusinessException{
  		DepositoIngreso dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public DepositoIngreso getPorId(DepositoIngresoId pId) throws BusinessException{
		DepositoIngreso domain = null;
		List<DepositoIngreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresaDeposito", pId.getIntIdEmpresaDeposito());
			mapa.put("intItemDepositoGeneral", pId.getIntItemDepositoGeneral());
			mapa.put("intItemDepositoIngreso", pId.getIntItemDepositoIngreso());
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
	
	public List<DepositoIngreso> getPorIngreso(Ingreso ingreso) throws BusinessException{
		List<DepositoIngreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa", ingreso.getId().getIntIdEmpresa());
			mapa.put("intIdIngresoGeneral", ingreso.getId().getIntIdIngresoGeneral());
			lista = dao.getListaPorIngreso(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<DepositoIngreso> getPorIngresoDeposito(Ingreso ingreso) throws BusinessException{
		List<DepositoIngreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresaDeposito", ingreso.getId().getIntIdEmpresa());
			mapa.put("intItemDepositoGeneral", ingreso.getId().getIntIdIngresoGeneral());
			lista = dao.getListaPorIngresoDeposito(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}