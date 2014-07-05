package pe.com.tumi.servicio.solicitudPrestamo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.dao.CancelacionCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.ExpedienteCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.impl.CancelacionCreditoDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.impl.ExpedienteCreditoDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;

public class CancelacionCreditoBO {
	
	private CancelacionCreditoDao dao = (CancelacionCreditoDao)TumiFactory.get(CancelacionCreditoDaoIbatis.class);
	

	public List<CancelacionCredito> getListaPorExpedienteCredito(ExpedienteCredito o) throws BusinessException{
		List<CancelacionCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		o.getId().getIntPersEmpresaPk());
			mapa.put("intCuenta", 				o.getId().getIntCuentaPk());
			mapa.put("intItemExpediente", 		o.getId().getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	o.getId().getIntItemDetExpediente());
			lista = dao.getListaPorExpedienteCredito(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	
	public CancelacionCredito grabar(CancelacionCredito o) throws BusinessException{
		CancelacionCredito dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CancelacionCredito modificar(CancelacionCredito o) throws BusinessException{
		CancelacionCredito dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CancelacionCredito getPorPk(CancelacionCreditoId pId) throws BusinessException{
		CancelacionCredito domain = null;
		List<CancelacionCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();		   	 
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("id.intItemCancelacion",   pId.getIntItemCancelacion());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
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


}
