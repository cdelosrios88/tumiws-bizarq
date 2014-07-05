package pe.com.tumi.servicio.solicitudPrestamo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.dao.CapacidadDescuentoDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.impl.CapacidadDescuentoDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadDescuento;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadDescuentoId;

public class CapacidadDescuentoBO {
	
	private CapacidadDescuentoDao dao = (CapacidadDescuentoDao)TumiFactory.get(CapacidadDescuentoDaoIbatis.class);
	
	public CapacidadDescuento grabar(CapacidadDescuento o) throws BusinessException{
		CapacidadDescuento dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CapacidadDescuento modificar(CapacidadDescuento o) throws BusinessException{
		CapacidadDescuento dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CapacidadDescuento getPorPk(CapacidadDescuentoId pId) throws BusinessException{
		CapacidadDescuento domain = null;
		List<CapacidadDescuento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 			pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 					pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 			pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 		pId.getIntItemDetExpediente());
			mapa.put("intItemCapacidad", 			pId.getIntItemCapacidad());
			mapa.put("intItemCapacidadDescuento", 	pId.getIntItemCapacidadDescuento());
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
	
	public List<CapacidadDescuento> getListaPorCapacidadCreditoPk(CapacidadCreditoId pId) throws BusinessException{
		List<CapacidadDescuento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 			pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 					pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 			pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 		pId.getIntItemDetExpediente());
			mapa.put("intItemCapacidad", 			pId.getIntItemCapacidad());
			lista = dao.getListaPorCapacidadCreditoPk(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void deletePorPk(CapacidadDescuentoId pId) throws BusinessException{
		
		try {
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 			pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 					pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 			pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 		pId.getIntItemDetExpediente());
			
			dao.deletePorPk(mapa);
		} catch(Exception e) {
			System.out.println("Error en deletePorPk ---> "+e);
			throw new BusinessException(e);
		}

	}
}