package pe.com.tumi.seguridad.empresa.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.AreaCodigo;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.empresa.bo.AreaCodigoBO;
import pe.com.tumi.seguridad.empresa.dao.AreaCodigoDao;

public class AreaCodigoDaoIbatis extends TumiDaoIbatis implements AreaCodigoDao {

	protected  static Logger log = Logger.getLogger(AreaCodigoBO.class);
	
	public AreaCodigo grabar(AreaCodigo o) throws DAOException {
		log.info("-----------------------Debugging AreaCodigoDaoIbatis.grabar-----------------------------");
		AreaCodigo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AreaCodigo modificar(AreaCodigo o) throws DAOException {
		AreaCodigo dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<AreaCodigo> getListaAreaCodigo(Object o) throws DAOException {
		log.info("-----------------------Debugging AreaDaoIbatis.getListaArea-----------------------------");
		List<AreaCodigo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAreaCodigo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AreaCodigo> getAreaCodigoPorPK(Object o) throws DAOException {
		log.info("-----------------------Debugging AreaDaoIbatis.getAreaCodigoPorPK-----------------------------");
		List<AreaCodigo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getAreaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
