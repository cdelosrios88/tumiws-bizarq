package pe.com.tumi.credito.socio.estructura.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.openjpa.lib.log.Log;

import pe.com.tumi.credito.socio.estructura.dao.AdminPadronDao;
import pe.com.tumi.credito.socio.estructura.dao.SolicitudPagoDao;
import pe.com.tumi.credito.socio.estructura.dao.impl.AdminPadronDaoIbatis;
import pe.com.tumi.credito.socio.estructura.dao.impl.SolicitudPagoDaoIbatis;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadronId;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.PadronId;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPago;
import pe.com.tumi.credito.socio.estructura.service.AdminPadronService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class SolicitudPagoBO {
	
	protected  	static Logger log = Logger.getLogger(SolicitudPagoBO.class);
	private SolicitudPagoDao dao = (SolicitudPagoDao)TumiFactory.get(SolicitudPagoDaoIbatis.class);
	
	public SolicitudPago getPorPK(Integer solicitudPagoPK) throws BusinessException{
		SolicitudPago domain = null;
		List<SolicitudPago> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intNumero", solicitudPagoPK);
			log.info("a buscar:"+solicitudPagoPK);
			lista = dao.getSolicitudPagoPorPK(mapa);
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
	
	public SolicitudPago grabar(SolicitudPago o) throws BusinessException{
		SolicitudPago dto = null;
		log.info("a grabar:"+o);
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SolicitudPago modificar(SolicitudPago o) throws BusinessException{
		SolicitudPago dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	
	
}
