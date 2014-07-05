package pe.com.tumi.reporte.operativo.cobranza.centralriesgo.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.dao.FenacrepDao;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.dao.impl.FenacrepDaoIbatis;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.Fenacrep;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.ServicioDao;

public class FenacrepBO {
	protected  static Logger log = Logger.getLogger(ServicioDao.class);
	private FenacrepDao dao = (FenacrepDao)TumiFactory.get(FenacrepDaoIbatis.class);

	public List<Fenacrep> getListaFenacrep(Integer intPeriodo) throws BusinessException{
		List<Fenacrep> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPeriodo", intPeriodo);
			lista = dao.getListaFenacrep(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}