package pe.com.tumi.contabilidad.cierre.bo;


import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.LibroMayorDetalleDao;
import pe.com.tumi.contabilidad.cierre.dao.impl.LibroMayorDetalleDaoIbatis;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalleId;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LibroMayorDetalleBO {
	
	private LibroMayorDetalleDao dao = (LibroMayorDetalleDao)TumiFactory.get(LibroMayorDetalleDaoIbatis.class);

	public LibroMayorDetalle grabar(LibroMayorDetalle o) throws BusinessException{
		LibroMayorDetalle dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public LibroMayorDetalle modificar(LibroMayorDetalle o) throws BusinessException{
  		LibroMayorDetalle dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public LibroMayorDetalle getPorPk(LibroMayorDetalleId pId) throws BusinessException{
		LibroMayorDetalle domain = null;
		List<LibroMayorDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaMayor", pId.getIntPersEmpresaMayor());
			mapa.put("intContPeriodoMayor", pId.getIntContPeriodoMayor());
			mapa.put("intContMesMayor", pId.getIntContMesMayor());
			mapa.put("intPersEmpresaCuenta", pId.getIntPersEmpresaCuenta());
			mapa.put("intContPeriodoCuenta", pId.getIntContPeriodoCuenta());
			mapa.put("strContNumeroCuenta", pId.getStrContNumeroCuenta());
			mapa.put("intPersEmpresaSucursal", pId.getIntPersEmpresaSucursal());
			mapa.put("intSucuIdSucursal", pId.getIntSucuIdSucursal());
			mapa.put("intSudeIdSubSucursal", pId.getIntSudeIdSubSucursal());
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
	
	public List<LibroMayorDetalle> getPorLibroMayor(LibroMayor o) throws BusinessException{
		List<LibroMayorDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaMayor", o.getId().getIntPersEmpresaMayor());
			mapa.put("intContPeriodoMayor", o.getId().getIntContPeriodoMayor());
			mapa.put("intContMesMayor", o.getId().getIntContMesMayor());
			lista = dao.getListaPorLibroMayor(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public void eliminar(LibroMayorDetalleId pId) throws BusinessException{
		LibroMayorDetalle domain = null;
		List<LibroMayorDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaMayor", pId.getIntPersEmpresaMayor());
			mapa.put("intContPeriodoMayor", pId.getIntContPeriodoMayor());
			mapa.put("intContMesMayor", pId.getIntContMesMayor());
			mapa.put("intPersEmpresaCuenta", pId.getIntPersEmpresaCuenta());
			mapa.put("intContPeriodoCuenta", pId.getIntContPeriodoCuenta());
			mapa.put("strContNumeroCuenta", pId.getStrContNumeroCuenta());
			mapa.put("intPersEmpresaSucursal", pId.getIntPersEmpresaSucursal());
			mapa.put("intSucuIdSucursal", pId.getIntSucuIdSucursal());
			mapa.put("intSudeIdSubSucursal", pId.getIntSudeIdSubSucursal());
			dao.eliminar(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public List<LibroMayorDetalle> getPorLibroMayorYPlanCuenta(LibroMayor o, PlanCuenta p) throws BusinessException{
		List<LibroMayorDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaMayor", o.getId().getIntPersEmpresaMayor());
			mapa.put("intContPeriodoMayor", o.getId().getIntContPeriodoMayor());
			mapa.put("intContMesMayor", o.getId().getIntContMesMayor());
			mapa.put("intPersEmpresaCuenta", p.getId().getIntEmpresaCuentaPk());
			mapa.put("intContPeriodoCuenta", p.getId().getIntPeriodoCuenta());
			mapa.put("strContNumeroCuenta", p.getId().getStrNumeroCuenta());
			lista = dao.getListaPorLibroMayorYPlanCuenta(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}