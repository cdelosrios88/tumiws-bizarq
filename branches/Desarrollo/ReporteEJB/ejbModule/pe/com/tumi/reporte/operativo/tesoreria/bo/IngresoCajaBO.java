package pe.com.tumi.reporte.operativo.tesoreria.bo;
/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-009       			15/12/2014     Christian De los Ríos        Creaciòn de componente         
*/

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.tesoreria.dao.IngresoCajaDao;
import pe.com.tumi.reporte.operativo.tesoreria.dao.impl.IngresoCajaDaoIbatis;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;

/**
 * Entidad de negocio (Bussiness Object) que gestiona la informacion
 * del reporte Ingreso de Caja
 * 
 * @author Bizarq
 */
public class IngresoCajaBO {
	protected  static Logger log = Logger.getLogger(IngresoCajaBO.class);
	private IngresoCajaDao dao = (IngresoCajaDao)TumiFactory.get(IngresoCajaDaoIbatis.class);
	
	/**
	 * Metodo que recupera la lista de Ingresos realizados a caja
	 * segun los filtros ingresados.
	 * 
	 * @param o, Entidad Bean IngresoCaja.
	 * @return Lista de entidades del tipo IngresoCaja.
	 * 
	 * @throws BusinessException
	 */
	public List<IngresoCaja> getListaIngresosByTipoIngreso(IngresoCaja o) throws BusinessException{
		List<IngresoCaja> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intTipoIngreso", o.getIntParaTipoDocGeneral());
			mapa.put("intIdSucursal", o.getIntIdSucursal());
			mapa.put("dtFechaInicio", o.getDtFecIni());
			mapa.put("dtFechaFin", o.getDtFecFin());
			mapa.put("strAnio", o.getIntAnioIngreso());
			mapa.put("strMes", o.getIntMesIngreso());
			lista = dao.getListaIngresosByTipoIngreso(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
