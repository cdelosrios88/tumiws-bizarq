package pe.com.tumi.servicio.solicitudPrestamo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.dao.AutorizaCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.ExpedienteActividadDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.impl.AutorizaCreditoDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.impl.ExpedienteActividadDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteActividad;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;

public class ExpedienteActividadBO {

	
private ExpedienteActividadDao dao = (ExpedienteActividadDao)TumiFactory.get(ExpedienteActividadDaoIbatis.class);
	
	public ExpedienteActividad grabar(ExpedienteActividad o) throws BusinessException{
		ExpedienteActividad dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			System.out.println("error en ExpedienteActividadDao grabar DAOException ---> "+e);
			throw new BusinessException(e);
		}catch(Exception e) {
			System.out.println("error en ExpedienteActividadDao grabar BusinessException ---> "+e);
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ExpedienteActividad modificar(ExpedienteActividad o) throws BusinessException{
		ExpedienteActividad dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}

	public List<ExpedienteActividad> getListaPorPkExpedienteCredito(ExpedienteCreditoId pId) throws BusinessException{
		List<ExpedienteActividad> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 			pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			
			lista = dao.getListaPorExpedienteCredito(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
