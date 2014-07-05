package pe.com.tumi.seguridad.empresa.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.empresa.dao.AreaDao;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacade;

public class AreaDaoIbatis extends TumiDaoIbatis implements AreaDao{
	
	protected  static Logger log = Logger.getLogger(EmpresaFacade.class);
	
	public List<Area> getListaArea(Object o) throws DAOException{
		//log.info("-----------------------Debugging AreaDaoIbatis.getListaArea-----------------------------");
		List<Area> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaArea", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Area> getAreaPorPK(Object o) throws DAOException {
		//log.info("-----------------------Debugging AreaDaoIbatis.getAreaPorPK-----------------------------");
		List<Area> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getAreaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Area grabar(Area o) throws DAOException {
		//log.info("-----------------------Debugging AreaDaoIbatis.getListaArea-----------------------------");
		Area dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Area modificar(Area o) throws DAOException {
		Area dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
}
