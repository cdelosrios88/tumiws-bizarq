package pe.com.tumi.tesoreria.banco.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.banco.dao.BancofondoDao;
import pe.com.tumi.tesoreria.banco.dao.impl.BancofondoDaoIbatis;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;

import pe.com.tumi.tesoreria.banco.domain.BancofondoId;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;

public class BancofondoBO{

	private BancofondoDao dao = (BancofondoDao)TumiFactory.get(BancofondoDaoIbatis.class);

	public Bancofondo grabar(Bancofondo o) throws BusinessException{
		Bancofondo dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Bancofondo modificar(Bancofondo o) throws BusinessException{
     	Bancofondo dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Bancofondo getPorPk(BancofondoId pId) throws BusinessException{
		Bancofondo domain = null;
		List<Bancofondo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPk", pId.getIntEmpresaPk());
			mapa.put("intItembancofondo", pId.getIntItembancofondo());
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
	
	public List<Bancofondo> getPorBusqueda(Bancofondo o) throws BusinessException{
		List<Bancofondo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEstadoCod", o.getIntEstadoCod());
			mapa.put("intMonedaCod", o.getIntMonedaCod());
			lista = dao.getListaPorBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Bancofondo getPorTipoFondoFijoYMoneda(Bancofondo o) throws BusinessException{
		Bancofondo domain = null;
		List<Bancofondo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPk", o.getId().getIntEmpresaPk());
			mapa.put("intTipoFondoFijo", o.getIntTipoFondoFijo());
			mapa.put("intMonedaCod", o.getIntMonedaCod());
			lista = dao.getListaPorTipoFondoFijoYMoneda(mapa);
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
	
	public Bancofondo getPorBancoCuenta(Bancocuenta o) throws BusinessException{
		Bancofondo bancoFondo = null;
		try{
			BancofondoId bancoFondoId = new BancofondoId();
			bancoFondoId.setIntEmpresaPk(o.getId().getIntEmpresaPk());
			bancoFondoId.setIntItembancofondo(o.getId().getIntItembancofondo());
			
			bancoFondo = getPorPk(bancoFondoId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return bancoFondo;
	}
	
	public List<Bancofondo> getPorEmpresayPersonaBanco(Integer intEmpresa, Integer intPersona) throws BusinessException{
		List<Bancofondo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresabancoPk", intEmpresa);
			mapa.put("intPersonabancoPk",intPersona);
			lista = dao.getListaPorEmpresaYPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Bancofondo getPorIngreso(Ingreso ingreso) throws BusinessException{
		Bancofondo dto = null;
		try{
			BancofondoId bancoFondoId = new BancofondoId();
			bancoFondoId.setIntEmpresaPk(ingreso.getId().getIntIdEmpresa());
			bancoFondoId.setIntItembancofondo(ingreso.getIntItemBancoFondo());
			dto = getPorPk(bancoFondoId);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
}
