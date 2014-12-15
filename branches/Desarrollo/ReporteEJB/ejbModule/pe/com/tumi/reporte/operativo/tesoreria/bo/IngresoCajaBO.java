package pe.com.tumi.reporte.operativo.tesoreria.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.tesoreria.dao.IngresoCajaDao;
import pe.com.tumi.reporte.operativo.tesoreria.dao.impl.IngresoCajaDaoIbatis;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;

public class IngresoCajaBO {
	protected  static Logger log = Logger.getLogger(IngresoCajaBO.class);
	private IngresoCajaDao dao = (IngresoCajaDao)TumiFactory.get(IngresoCajaDaoIbatis.class);
	
	public List<IngresoCaja> getListaIngresosByTipoIngreso(IngresoCaja o) throws BusinessException{
		List<IngresoCaja> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdSucursal", o.getIntIdSucursal());
			mapa.put("intIdTipoIngreso", o.getIntParaTipoDocGeneral());
			mapa.put("dtFechaInicio", o.getDtFecIni());
			mapa.put("dtFechaFin", o.getDtFecFin());
			lista = dao.getListaIngresosByTipoIngreso(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
