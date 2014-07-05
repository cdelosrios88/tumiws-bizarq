package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.cobranza.planilla.dao.SolicitudCtaCteBloqueoDao;
import pe.com.tumi.cobranza.planilla.dao.impl.SolicitudCtaCteBloqueoDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteBloqueo;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;

public class SolicitudCtaCteBloqueoBO{

	private SolicitudCtaCteBloqueoDao dao = (SolicitudCtaCteBloqueoDao)TumiFactory.get(SolicitudCtaCteBloqueoDaoIbatis.class);

	public SolicitudCtaCteBloqueo grabarSolicitudCtaCteBloqueo(SolicitudCtaCteBloqueo o) throws BusinessException{
		SolicitudCtaCteBloqueo dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	public List<SolicitudCtaCteBloqueo> getListaPorTipoSolicitud(Integer intPersEmpresaPk,Integer intTipoSolicitud) throws BusinessException{
		List<SolicitudCtaCteBloqueo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresasolctacte",intPersEmpresaPk);
			mapa.put("intTipoSolicitudctacte",intTipoSolicitud);
			lista = dao.getListaPorTipoSol(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}


}