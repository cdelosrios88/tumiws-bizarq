package pe.com.tumi.tesoreria.banco.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.banco.dao.BancocuentaDao;
import pe.com.tumi.tesoreria.banco.dao.impl.BancocuentaDaoIbatis;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;

import pe.com.tumi.tesoreria.banco.domain.BancocuentaId;

public class BancocuentaBO{

	private BancocuentaDao dao = (BancocuentaDao)TumiFactory.get(BancocuentaDaoIbatis.class);
	
	public Bancocuenta grabar(Bancocuenta o) throws BusinessException{
		Bancocuenta dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Bancocuenta modificar(Bancocuenta o) throws BusinessException{
     	Bancocuenta dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Bancocuenta getPorPk(BancocuentaId pId) throws BusinessException{
		Bancocuenta domain = null;
		List<Bancocuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPk", pId.getIntEmpresaPk());
			mapa.put("intItembancocuenta", pId.getIntItembancocuenta());
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
	
	public List<Bancocuenta> getPorBancoFondo(Bancofondo o) throws BusinessException{
		List<Bancocuenta> lista = null;
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
	
	public Bancocuenta getPorBancoCuentaCheque(Bancocuentacheque o) throws BusinessException{
		Bancocuenta bancocuenta = null;
		try{
			BancocuentaId bancoCuentaId = new BancocuentaId();
			bancoCuentaId.setIntEmpresaPk(o.getId().getIntEmpresaPk());
			bancoCuentaId.setIntItembancofondo(o.getId().getIntItembancofondo());
			bancoCuentaId.setIntItembancocuenta(o.getId().getIntItembancocuenta());
			
			bancocuenta = getPorPk(bancoCuentaId);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return bancocuenta;
	}
	
	public List<Bancocuenta> getPorPlanCuenta(LibroDiarioDetalle libroDiarioDetalle) throws BusinessException{
		List<Bancocuenta> lista = null;
		try{			
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPk", libroDiarioDetalle.getId().getIntPersEmpresaLibro());
			mapa.put("intEmpresacuentaPk", libroDiarioDetalle.getIntPersEmpresaCuenta());
			mapa.put("intPeriodocuenta", libroDiarioDetalle.getIntContPeriodo());
			mapa.put("strNumerocuenta", libroDiarioDetalle.getStrContNumeroCuenta());
			lista = dao.getListaPorPlanCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
