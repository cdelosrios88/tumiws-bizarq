package pe.com.tumi.reporte.operativo.tesoreria.bo;

import java.util.HashMap;
import java.util.List;


import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.tesoreria.dao.MovEgresoDao;
import pe.com.tumi.reporte.operativo.tesoreria.dao.impl.MovEgresoDaoIbatis;
import pe.com.tumi.reporte.operativo.tesoreria.domain.EgresoFondoFijo;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgreso;

public class MovEgresoBO {
	protected  static Logger log = Logger.getLogger(MovEgresoBO.class);
	private MovEgresoDao dao = (MovEgresoDao) TumiFactory.get(MovEgresoDaoIbatis.class);
	public List<MovEgreso> getListFondoFijo(int intSucursal,int intAnio, int intTipoFondoFijo) throws BusinessException{
		List<MovEgreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdSucursal",intSucursal);
			mapa.put("intAnio", intAnio );
			mapa.put("intTipoFondoFijo", intTipoFondoFijo);
			lista = dao.getListFondoFijo(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	public List<EgresoFondoFijo> getEgresos (MovEgreso objMovEgreso) throws BusinessException {
		List<EgresoFondoFijo> lista  = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemFondo",objMovEgreso.getIntItemFondoFijo());
			mapa.put("intPeriodoFondo", objMovEgreso.getIntPeriodoEgreso() );
			mapa.put("dbMontoSaldo", null);
			lista = dao.getEgresos(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
