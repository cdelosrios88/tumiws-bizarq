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
import pe.com.tumi.reporte.operativo.tesoreria.dao.MovEgresoDao;
import pe.com.tumi.reporte.operativo.tesoreria.dao.impl.MovEgresoDaoIbatis;
import pe.com.tumi.reporte.operativo.tesoreria.domain.EgresoFondoFijo;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgreso;

/**
 * Entidad de negocio (Bussiness Object) que gestiona la informacion
 * del reporte Egreso de Caja
 * 
 * @author Bizarq
 */
public class MovEgresoBO {
	protected  static Logger log = Logger.getLogger(MovEgresoBO.class);
	private MovEgresoDao dao = (MovEgresoDao) TumiFactory.get(MovEgresoDaoIbatis.class);
	
	/**
	 * Metodo que recupera la lista de Egresos realizados a caja
	 * segun los filtros ingresados.
	 * 
	 * @param intSucursal, Identificador de la sucursal.
	 * @param intAnio, Anio de consulta.
	 * @param intTipoFondoFijo, Indicador de tipo de fondo fijo.
	 * @return Lista de entidades del tipo EgresosCaja.
	 * 
	 * @throws BusinessException
	 */
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
	
	public MovEgreso getListEgresoById(MovEgreso o) throws BusinessException{
		MovEgreso domain = null;
		List<MovEgreso> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaEgreso", o.getIntEmpresaEgreso());
			mapa.put("intItemEgresoGeneral", o.getIntItemEgresoGeneral());
			lista = dao.getListEgresoById(mapa);
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
	
	/**
	 * Metodo que recupera la lista de Egresos de monto fijo realizados a caja
	 * segun los filtros ingresados.
	 * 
	 * @param objMovEgreso, Entidad de tipo Moviemiento Egreso.
	 * @return Lista de entidades del tipo EgresoFondoFijo.
	 * 
	 * @throws BusinessException
	 */
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
