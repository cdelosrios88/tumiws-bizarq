package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.cobranza.planilla.dao.DevolucionDao;
import pe.com.tumi.cobranza.planilla.dao.impl.DevolucionDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.Devolucion;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class DevolucionBO {
	
	private DevolucionDao dao = (DevolucionDao)TumiFactory.get(DevolucionDaoIbatis.class);
	
	public Devolucion grabar(Devolucion o) throws BusinessException{
		Devolucion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Devolucion modificar(Devolucion o) throws BusinessException{
		Devolucion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<Devolucion> getListaPorSolicitudCtaCteTipo(Devolucion o) throws BusinessException{
		List<Devolucion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaSolCtaCte", 		o.getIntPersEmpresaSolCtaCte() );
			mapa.put("intItemSolCtaCte", 			o.getIntCcobItemSolCtaCte());
			mapa.put("intParaTipoSolicitudCtaCte", 	o.getIntParaTipoSolicitudCtaCte());

			lista = dao.getListaPorSolicitudCtaCteTipo(mapa);

		}catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}

