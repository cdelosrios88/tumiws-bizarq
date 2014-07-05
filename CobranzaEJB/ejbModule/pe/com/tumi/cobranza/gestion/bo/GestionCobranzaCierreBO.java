package pe.com.tumi.cobranza.gestion.bo;
import java.util.Date;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.dao.GestionCobranzaCierreDao;
import pe.com.tumi.cobranza.gestion.dao.impl.GestionCobranzaCierreDaoIbatis;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaCierre;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class GestionCobranzaCierreBO {
	
protected  static Logger log = Logger.getLogger(GestionCobranzaCierreBO.class);
private GestionCobranzaCierreDao dao = (GestionCobranzaCierreDao)TumiFactory.get(GestionCobranzaCierreDaoIbatis.class);
	public GestionCobranzaCierre grabarGestionCobranzaCierre(GestionCobranzaCierre o) throws BusinessException {
		GestionCobranzaCierre dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Date obtUltimaFechaGestion(GestionCobranzaCierre o) throws BusinessException 	{
	
		
		Date dto = null;
		try{
			dto = dao.obtUltimaFechaGestion(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;

	}
	
	
	

}
