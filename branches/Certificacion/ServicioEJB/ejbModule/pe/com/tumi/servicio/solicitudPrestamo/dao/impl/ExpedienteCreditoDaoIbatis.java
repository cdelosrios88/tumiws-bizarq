package pe.com.tumi.servicio.solicitudPrestamo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.ExpedienteCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;

public class ExpedienteCreditoDaoIbatis extends TumiDaoIbatis implements ExpedienteCreditoDao{
	
	public ExpedienteCredito grabar(ExpedienteCredito o) throws DAOException {
		ExpedienteCredito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ExpedienteCredito modificar(ExpedienteCredito o) throws DAOException {
		ExpedienteCredito dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ExpedienteCredito> getListaPorPk(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ExpedienteCredito> getListaBusquedaPorExpCredComp(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusquedaPorExpCredComp", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ExpedienteCredito> getListaBusquedaAutorizacionPorExpCredComp(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusquedaAutorizacionPorExpCredComp", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ExpedienteCredito> getListaPorCuenta(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	public ExpedienteCredito grabarRefinanciamiento(ExpedienteCredito o) throws DAOException {
		ExpedienteCredito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarRefinanciamiento", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ExpedienteCredito> getListaBusquedaPorExpRefComp(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusquedaPorExpRefComp", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	public List<ExpedienteCredito> getListaPorExpediente(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ExpedienteCredito> getListaBusquedaPorExpActComp(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusquedaPorExpActComp", o);
		}catch(Exception e) {
			System.out.println("Error en getListaBusquedaPorExpActComp--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recupera los expedientes de credito segun filtros de busqueda
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedienteCredito> getListaBusqRefinanFiltros(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqRefinanFiltros", o);
		}catch(Exception e) {
			System.out.println("Error en getListaBusqRefinanFiltros--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recupera los expedientes de autorizacion refinanciamienti segun filtros de grilla.
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedienteCredito> getListaBusqAutRefFiltros(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqAutRefFiltros", o);
		}catch(Exception e) {
			System.out.println("Error en getListaBusqAutRefFiltros--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera los expedientes de credito segun filtros de grilla.
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedienteCredito> getListaBusqCreditoFiltros(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqCreditoFiltros", o);
		}catch(Exception e) {
			System.out.println("Error en getListaBusqCreditoFiltros--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera los expedientes de autorizacion credito segun filtros de grilla.
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedienteCredito> getListaBusqCreditosAutFiltros(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqCreditosAutFiltros", o);
		}catch(Exception e) {
			System.out.println("Error en getListaBusqCreditosAutFiltros--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera los expedientes de credito segun filtros de grilla.
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedienteCredito> getListaBusqActividadFiltros(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqActividadFiltros", o);
		}catch(Exception e) {
			System.out.println("Error en getListaBusqActividadFiltros--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}

	/**
	 * Recupera los expedientes de actividad para autorizar segun filtros de grilla.
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedienteCredito> getListaBusqActAutFiltros(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqActAutFiltros", o);
		}catch(Exception e) {
			System.out.println("Error en getListaBusqActAutFiltros--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recupera los expedientes de credito especial segun filtros de grilla.
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedienteCredito> getListaBusqCreditoEspecialFiltros(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqCreditoEspecialFiltros", o);
		}catch(Exception e) {
			System.out.println("Error en getListaBusqCreditoEspecialFiltros--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * Agregado 31.12.2013 JCHAVEZ
	 * Procedimiento que recupera la Categoria de Riesgo del préstamo a cancelar en Giro REPRESTAMO
	 */
	public List<ExpedienteCredito> getRiesgoExpCredACancelar(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getRiesgoExpCredACancelar", o);
		}catch(Exception e) {
			System.out.println("Error en getRiesgoExpCredACancelar--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}	
	
	
	/**
	 * Recuepera el maximo expediente de credito para re4financiamiento.
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<ExpedienteCredito> getMaxExpedienteRefinan(Object o) throws DAOException{
		List<ExpedienteCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMaxExpedienteRefinan", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Agregado 31.01.2014 JCHAVEZ
	 * Procedimiento que recupera los Requisitos necesarios para el giro en Giro REPRESTAMO
	 */
	public List<RequisitoCreditoComp> getRequisitoGiroPrestamo(Object o) throws DAOException{
		List<RequisitoCreditoComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getRequisitoGiroPrestamo", o);
		}catch(Exception e) {
			System.out.println("Error en getRequisitoGiroPrestamo--->  "+e);
			throw new DAOException (e);
		}
		return lista;
	}	
	
	
}