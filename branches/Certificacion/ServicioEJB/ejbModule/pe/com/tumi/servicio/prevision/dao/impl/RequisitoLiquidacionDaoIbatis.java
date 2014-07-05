package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.RequisitoLiquidacionDao;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;

public class RequisitoLiquidacionDaoIbatis extends TumiDaoIbatis implements RequisitoLiquidacionDao{

		public RequisitoLiquidacion grabar(RequisitoLiquidacion o) throws DAOException{
			RequisitoLiquidacion dto = null;
			try{
				getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
				dto = o;
			}catch(Exception e) {
				throw new DAOException(e);
			}
			return dto;
		}
		
		public RequisitoLiquidacion modificar(RequisitoLiquidacion o) throws DAOException{
			RequisitoLiquidacion dto = null;
			try{
				getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
				dto = o;
			}catch(Exception e) {
				throw new DAOException(e);
			}
			return dto;
		}
		
		
		public List<RequisitoLiquidacion> getListaPorPk(Object o) throws DAOException{
			List<RequisitoLiquidacion> lista = null;
			try{
				lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
			}catch(Exception e) {
				throw new DAOException (e);
			}
			return lista;
		}
		
		public List<RequisitoLiquidacion> getListaPorExpediente(Object o) throws DAOException{
			List<RequisitoLiquidacion> lista = null;
			try{
				lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpediente", o);
			}catch(Exception e) {
				throw new DAOException (e);
			}
			return lista;
		}

		public List<RequisitoLiquidacion> getListaPorPkExpedienteLiquidacionYRequisitoDetalle(Object o) throws DAOException{
			List<RequisitoLiquidacion> lista = null;
			try{
				lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkExpedienteLiquidacionYRequisitoDetalle", o);
			}catch(Exception e) {
				throw new DAOException (e);
			}
			return lista;
		}	
}

