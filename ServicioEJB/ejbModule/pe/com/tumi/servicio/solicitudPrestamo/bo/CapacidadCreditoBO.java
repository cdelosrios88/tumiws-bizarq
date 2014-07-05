package pe.com.tumi.servicio.solicitudPrestamo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.dao.CapacidadCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.impl.CapacidadCreditoDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;

public class CapacidadCreditoBO {
	
	private CapacidadCreditoDao dao = (CapacidadCreditoDao)TumiFactory.get(CapacidadCreditoDaoIbatis.class);
	
	public CapacidadCredito grabar(CapacidadCredito o) throws BusinessException{
		CapacidadCredito dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			System.out.println("DAOException --->"+e);
			throw new BusinessException(e);
		}catch(Exception e) {
			System.out.println("Exception ---> "+e);
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CapacidadCredito modificar(CapacidadCredito o) throws BusinessException{
		CapacidadCredito dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CapacidadCredito getPorPk(CapacidadCreditoId pId) throws BusinessException{
		CapacidadCredito domain = null;
		List<CapacidadCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			mapa.put("intItemCapacidad", 		pId.getIntItemCapacidad());
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
	
	public List<CapacidadCredito> getListaPorPkExpedienteCredito(ExpedienteCreditoId pId) throws BusinessException{
		List<CapacidadCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			lista = dao.getListaPorPkExpedienteCredito(mapa);
		} catch(DAOException e){
			throw new BusinessException(e);
		} catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<CapacidadCredito> getListaCapacidadCreditoPorPkExpedienteYSocioEstructura(ExpedienteCreditoId pId,SocioEstructuraPK pIdSocio) throws BusinessException{
		List<CapacidadCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			mapa.put("intPersPersonaPk", 		pIdSocio.getIntIdPersona());
			mapa.put("intItemSocioEstructura", 	pIdSocio.getIntItemSocioEstructura());
			lista = dao.getListaPorPkExpedienteYSocioEstructura(mapa);
		} catch(DAOException e){
			throw new BusinessException(e);
		} catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void deletePorPk(CapacidadCreditoId pId) throws BusinessException{
		
		try {
			
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 			pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			
			dao.deletePorPk(mapa);
		} catch(Exception e) {
			throw new BusinessException(e);
		}

	}
}