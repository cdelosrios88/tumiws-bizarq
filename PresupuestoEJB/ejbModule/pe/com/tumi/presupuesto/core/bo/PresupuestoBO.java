package pe.com.tumi.presupuesto.core.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.presupuesto.core.dao.PresupuestoDao;
import pe.com.tumi.presupuesto.core.dao.impl.PresupuestoDaoIbatis;
import pe.com.tumi.presupuesto.core.domain.Presupuesto;
import pe.com.tumi.presupuesto.core.domain.PresupuestoId;

public class PresupuestoBO {
	protected  static Logger log = Logger.getLogger(PresupuestoBO.class);
	private PresupuestoDao dao = (PresupuestoDao)TumiFactory.get(PresupuestoDaoIbatis.class);
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Presupuesto grabar(Presupuesto o) throws BusinessException{
		Presupuesto dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Presupuesto modificar(Presupuesto o) throws BusinessException{
		Presupuesto dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	 * Recupera Presupuesto por Presupuesto Id
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public Presupuesto getPresupuestoPorPK(PresupuestoId pId) throws BusinessException{
		Presupuesto domain = null;
		List<Presupuesto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPresupuestoPk", pId.getIntEmpresaPresupuestoPk());
			mapa.put("intPeriodoPresupuesto", pId.getIntPeriodoPresupuesto());
			mapa.put("intMesPresupuesto", pId.getIntMesPresupuesto());
			mapa.put("intEmpresaCuentaPk", pId.getIntEmpresaCuentaPk());
			mapa.put("intPeriodoCuenta", pId.getIntPeriodoCuenta());
			mapa.put("strNumeroCuenta", pId.getStrNumeroCuenta());
			mapa.put("intEmpresaSucursalPk", pId.getIntEmpresaSucursalPk());
			mapa.put("intIdSucursal", pId.getIntIdSucursal());
			mapa.put("intIdSubSucursal", pId.getIntIdSubSucursal());
			lista = dao.getListaPorPK(mapa);
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
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 22.10.2013
	 * Recupera lista por filtros de búsqueda
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<Presupuesto> getListaPresupuestoPorFiltros(PresupuestoId pId, Integer intTipoCuentaBusq) throws BusinessException{
		List<Presupuesto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPresupuestoPk", pId.getIntEmpresaPresupuestoPk());
			mapa.put("intPeriodoPresupuesto", pId.getIntPeriodoPresupuesto());
			mapa.put("intMesPresupuesto", pId.getIntMesPresupuesto());
			mapa.put("intEmpresaCuentaPk", pId.getIntEmpresaCuentaPk());
			mapa.put("intPeriodoCuenta", pId.getIntPeriodoCuenta());
			mapa.put("strNumeroCuenta", pId.getStrNumeroCuenta());
			mapa.put("intEmpresaSucursalPk", pId.getIntEmpresaSucursalPk());
			mapa.put("intIdSucursal", pId.getIntIdSucursal());
			mapa.put("intIdSubSucursal", pId.getIntIdSubSucursal());
			mapa.put("intTipoCuentaBusq", intTipoCuentaBusq);
			lista = dao.getListaPresupuestoPorFiltros(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013
	 * Recupera lista por Rango de Fechas
	 * @param intEmpresaPresupuestoPk
	 * @param intPeriodoPresupuesto
	 * @param intMesDesde
	 * @param intMesHasta
	 * @return
	 * @throws BusinessException
	 */
	public List<Presupuesto> getListaPorRangoFechas(Integer intEmpresaPresupuestoPk, Integer intPeriodoPresupuesto, Integer intMesDesde, Integer intMesHasta) throws BusinessException{
		List<Presupuesto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPresupuestoPk", intEmpresaPresupuestoPk);
			mapa.put("intPeriodoPresupuesto", intPeriodoPresupuesto);
			mapa.put("intMesDesde", intMesDesde);
			mapa.put("intMesHasta", intMesHasta);
			lista = dao.getListaPorRangoFechas(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013
	 * Recupera lista de meses existente en el año base por rango de fechas
	 * @param intEmpresaPresupuestoPk
	 * @param intPeriodoPresupuesto
	 * @param intMesDesde
	 * @param intMesHasta
	 * @return
	 * @throws BusinessException
	 */
	public List<Presupuesto> getMesesDelAnioBase(Integer intEmpresaPresupuestoPk, Integer intPeriodoPresupuesto, Integer intMesDesde, Integer intMesHasta) throws BusinessException{
		List<Presupuesto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPresupuestoPk", intEmpresaPresupuestoPk);
			mapa.put("intPeriodoPresupuesto", intPeriodoPresupuesto);
			mapa.put("intMesDesde", intMesDesde);
			mapa.put("intMesHasta", intMesHasta);
			lista = dao.getMesesDelAnioBase(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	//AUTOR Y FECHA CREACION: JCHAVEZ / 25.10.2013
	public Presupuesto eliminar(PresupuestoId o) throws BusinessException{
		Presupuesto dto = null;
		try{
			dto = dao.eliminar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 28.10.2013
	 * Recupera lista de Presupuestos del Año Base por todas las sucursales y subsucursales
	 * ACTIVAS y todas las cuentas que existan tanto en el Año Base como en el Año Proyectado
	 * que esten ACTIVAS y que reciban movimiento (PLCU_MOVIMIENTO_N=1)
	 * @param intEmpresa
	 * @param intPeriodo
	 * @param intMesDesde
	 * @param intMesHasta
	 * @return
	 * @throws BusinessException
	 */
	public List<Presupuesto> getLstPstoAnioBase(Integer intEmpresa, Integer intPeriodo, Integer intMesDesde, Integer intMesHasta) throws BusinessException{
		List<Presupuesto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPresupuestoPk", intEmpresa);
			mapa.put("intPeriodoPresupuesto", intPeriodo);
			mapa.put("intMesDesde", intMesDesde);
			mapa.put("intMesHasta", intMesHasta);
			lista = dao.getLstPstoAnioBase(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
