package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

import pe.com.tumi.cobranza.planilla.dao.DescuentoIndebidoDao;
import pe.com.tumi.cobranza.planilla.dao.impl.DescuentoIndebidoDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.DescuentoIndebido;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConceptoId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;

public class DescuentoIndebidoBO{

	private DescuentoIndebidoDao dao = (DescuentoIndebidoDao)TumiFactory.get(DescuentoIndebidoDaoIbatis.class);

	public DescuentoIndebido grabarDescuentoIndebido(DescuentoIndebido o) throws BusinessException{
		DescuentoIndebido dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public DescuentoIndebido modificarDescuentoIndebido(DescuentoIndebido o) throws BusinessException{
     	DescuentoIndebido dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	
	public List<DescuentoIndebido> getListaPorEmpCptoEfeGnralyCuenta(Integer intIdEmpresa,EfectuadoConceptoId pid,Integer intCsocCuenta) throws BusinessException{
		List<DescuentoIndebido> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaDsctoindeb", 		intIdEmpresa);
			mapa.put("intCcobItemEfectuado", 			pid.getIntItemEfectuado());
			mapa.put("intCcobItemEfectuadoconcepto", 	pid.getIntItemEfectuadoConcepto());
			mapa.put("intCsocCuenta", 			    	intCsocCuenta);
			lista = dao.getListaPorEmpCptoEfeGnralyCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<DescuentoIndebido> getListPorEmpYCta(Integer intIdEmpresa,Integer intCsocCuenta) throws BusinessException{
		List<DescuentoIndebido> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaDsctoindeb", 		intIdEmpresa);
			mapa.put("intCsocCuenta", 			    	intCsocCuenta);
			lista = dao.getListPorEmpYCta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	

		
}