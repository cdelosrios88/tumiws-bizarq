package pe.com.tumi.contabilidad.cierre.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.AnexoDetalleCuentaDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.AnexoDetalleCuentaDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuentaId;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class AnexoDetalleCuentaBO {
	private AnexoDetalleCuentaDao dao = (AnexoDetalleCuentaDao)TumiFactory.get(AnexoDetalleCuentaDaoIbatis.class);

	public AnexoDetalleCuenta grabar(AnexoDetalleCuenta o) throws BusinessException{
		AnexoDetalleCuenta dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
	public AnexoDetalleCuenta modificar(AnexoDetalleCuenta o) throws BusinessException{
		AnexoDetalleCuenta dto = null;
		try{
		    dto = dao.modificar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
  
	public AnexoDetalleCuenta getPorPk(AnexoDetalleCuentaId pId) throws BusinessException{
		AnexoDetalleCuenta domain = null;
		List<AnexoDetalleCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaCuenta",	pId.getIntPersEmpresaCuenta());
			mapa.put("intContPeriodoCuenta",	pId.getIntContPeriodoCuenta());
			mapa.put("strContNumeroCuenta", 	pId.getStrContNumeroCuenta());
			mapa.put("intPersEmpresaAnexo",		pId.getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",		pId.getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 		pId.getIntParaTipoAnexo());
			mapa.put("intItemAnexoDetalle",		pId.getIntItemAnexoDetalle());
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
	
	public List<AnexoDetalleCuenta> getPorAnexoDetalle(AnexoDetalle o) throws BusinessException{
		List<AnexoDetalleCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaAnexo",	o.getId().getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",	o.getId().getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 	o.getId().getIntParaTipoAnexo());
			mapa.put("intItemAnexoDetalle",	o.getId().getIntItemAnexoDetalle());
			lista = dao.getListaPorAnexoDetalle(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//Agregado por cdelosrios, 16/09/2013
	public List<AnexoDetalleCuenta> getPorPlanCuenta(PlanCuenta o) throws BusinessException{
		List<AnexoDetalleCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaCuenta",	o.getId().getIntEmpresaCuentaPk());
			mapa.put("intContPeriodoCuenta",	o.getId().getIntPeriodoCuenta());
			mapa.put("strContNumeroCuenta", 	o.getId().getStrNumeroCuenta());
			lista = dao.getListaPorPlanCuenta(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	//Fin agregado por cdelosrios, 16/09/2013
	
	public void eliminar(AnexoDetalleCuenta o) throws BusinessException{
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaCuenta",o.getId().getIntPersEmpresaCuenta());
			mapa.put("intContPeriodoCuenta",o.getId().getIntContPeriodoCuenta());
			mapa.put("strContNumeroCuenta", o.getId().getStrContNumeroCuenta());			
			mapa.put("intPersEmpresaAnexo",	o.getId().getIntPersEmpresaAnexo());
			mapa.put("intContPeriodoAnexo",	o.getId().getIntContPeriodoAnexo());
			mapa.put("intParaTipoAnexo", 	o.getId().getIntParaTipoAnexo());
			mapa.put("intItemAnexoDetalle", o.getId().getIntItemAnexoDetalle());
			dao.eliminar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
	}
}