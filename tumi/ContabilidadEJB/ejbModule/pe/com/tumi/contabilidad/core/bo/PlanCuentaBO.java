package pe.com.tumi.contabilidad.core.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.contabilidad.core.dao.PlanCuentaDao;
import pe.com.tumi.contabilidad.core.dao.impl.PlanCuentaDaoIbatis;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;

public class PlanCuentaBO{

	private PlanCuentaDao dao = (PlanCuentaDao)TumiFactory.get(PlanCuentaDaoIbatis.class);
	protected static Logger log = Logger.getLogger(PlanCuentaBO.class);
	
	public PlanCuenta grabarPlanCuenta(PlanCuenta o) throws BusinessException{
		PlanCuenta dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public PlanCuenta modificarPlanCuenta(PlanCuenta o) throws BusinessException{
     	PlanCuenta dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public PlanCuenta getPlanCuentaPorPk(PlanCuentaId pId) throws BusinessException{
		PlanCuenta domain = null;
		List<PlanCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("strNumeroCuenta", pId.getStrNumeroCuenta());
			mapa.put("intPeriodoCuenta", pId.getIntPeriodoCuenta());
			mapa.put("intEmpresaCuentaPk", pId.getIntEmpresaCuentaPk());
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
	
	public List<PlanCuenta> getPlanCuentaBusqueda(PlanCuenta pPlanCuenta) throws BusinessException{
		List<PlanCuenta> lista = null;
		try{
			log.info("pPlanCuenta.getId().getStrNumeroCuenta(): "+pPlanCuenta.getId().getStrNumeroCuenta());
			log.info("pPlanCuenta.getStrDescripcion(): "+pPlanCuenta.getStrDescripcion());
			log.info("pPlanCuenta.getIntMovimiento(): "+pPlanCuenta.getIntMovimiento());
			log.info("pPlanCuenta.getIntIdentificadorExtranjero(): "+pPlanCuenta.getIntIdentificadorExtranjero());
			log.info("pPlanCuenta.getId().getIntPeriodoCuenta(): "+pPlanCuenta.getId().getIntPeriodoCuenta());
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pStrNumeroCuenta", pPlanCuenta.getId().getStrNumeroCuenta());
			mapa.put("pStrDescripcion", pPlanCuenta.getStrDescripcion());
			mapa.put("pIntMovimiento", pPlanCuenta.getIntMovimiento());
			mapa.put("pIntIdentificadorExtranjero", pPlanCuenta.getIntIdentificadorExtranjero());
			mapa.put("intPeriodoCuenta", pPlanCuenta.getId().getIntPeriodoCuenta());
			
			lista = dao.getBusqueda(mapa);
			if(lista!=null){
				for(int i=0; i<lista.size(); i++){
					if(lista.get(i).getIntMovimiento()!=null && lista.get(i).getIntMovimiento()==1){
						lista.get(i).setBlnTieneMovimiento(true);
					}else{
						lista.get(i).setBlnTieneMovimiento(false);
					}
					
					if(lista.get(i).getIntIdentificadorExtranjero()!=null && lista.get(i).getIntIdentificadorExtranjero()==1){
						lista.get(i).setBlnTieneIdExtranjero(true);
					}else{
						lista.get(i).setBlnTieneIdExtranjero(false);
					}
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PlanCuenta> findListCuentaOperacional(PlanCuenta pPlanCuenta) throws BusinessException{
		List<PlanCuenta> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntPeriodoCuenta", pPlanCuenta.getId().getIntPeriodoCuenta());
			mapa.put("pStrNumeroCuenta", pPlanCuenta.getId().getStrNumeroCuenta());
			mapa.put("pStrDescripcion", pPlanCuenta.getStrDescripcion());
			lista = dao.findListCuentaOperacional(mapa);
			if(lista!=null){
				for(int i=0; i<lista.size(); i++){
					if(lista.get(i).getIntMovimiento()!=null && lista.get(i).getIntMovimiento()==1){
						lista.get(i).setBlnTieneMovimiento(true);
					}else{
						lista.get(i).setBlnTieneMovimiento(false);
					}
					
					if(lista.get(i).getIntIdentificadorExtranjero()!=null && lista.get(i).getIntIdentificadorExtranjero()==1){
						lista.get(i).setBlnTieneIdExtranjero(true);
					}else{
						lista.get(i).setBlnTieneIdExtranjero(false);
					}
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Integer> getListaPeriodos() throws BusinessException{
		List<PlanCuenta> lista = null;
		List<Integer> periodos = null;
		try{
			HashMap mapa = new HashMap();
			lista = dao.getPeriodos(mapa);
			if(lista!=null){
				periodos = new ArrayList<Integer>();
				for(int i=0; i<lista.size(); i++){
					periodos.add(lista.get(i).getId().getIntPeriodoCuenta());
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return periodos;
	}
	
	public List<PlanCuenta> getListaPorEmpresaCuentaYPeriodoCuenta(
			Integer intEmpresaCuenta, Integer intPeriodoCuenta) 
			throws BusinessException{
		List<PlanCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPeriodoCuenta", intPeriodoCuenta);
			mapa.put("intEmpresaCuentaPk", intEmpresaCuenta);
			lista = dao.getListaPorEmpresaCuentaYPeriodoCuenta(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 23.10.2013
	 * Recupera lista Plan Cuenta por periodo base
	 * @param intPeriodoCuenta
	 * @return
	 * @throws BusinessException
	 */
	public List<PlanCuenta> getPlanCtaPorPeriodoBase(Integer intPeriodoCuenta, Integer intEmpresaCuentaPk) throws BusinessException{
		List<PlanCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPeriodoCuenta", intPeriodoCuenta);
			mapa.put("intEmpresaCuentaPk", intEmpresaCuentaPk);
			lista = dao.getPlanCtaPorPeriodoBase(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 23.10.2013
	 * Recupera lista Plan Cuenta por periodo proyectado, nro cuenta o descripcion (opcional)
	 * @param pPlanCuenta
	 * @return
	 * @throws BusinessException
	 */
	public List<PlanCuenta> getBusqPorNroCtaDesc(PlanCuenta pPlanCuenta) throws BusinessException{
		List<PlanCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaCuentaPk", pPlanCuenta.getId().getIntEmpresaCuentaPk());
			mapa.put("intPeriodoCuenta", pPlanCuenta.getId().getIntPeriodoCuenta());
			mapa.put("strNumeroCuenta", pPlanCuenta.getId().getStrNumeroCuenta());
			mapa.put("strDescripcion", pPlanCuenta.getStrDescripcion());
			lista = dao.getBusqPorNroCtaDesc(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
