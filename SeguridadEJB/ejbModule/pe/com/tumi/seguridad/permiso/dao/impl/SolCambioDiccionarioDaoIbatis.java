package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.SolCambioDiccionarioDao;
import pe.com.tumi.seguridad.permiso.domain.SolCambioDiccionario;

public class SolCambioDiccionarioDaoIbatis extends TumiDaoIbatis implements SolCambioDiccionarioDao {

	protected  static Logger log = Logger.getLogger(SolCambioDiccionarioDaoIbatis.class);
	
	public SolCambioDiccionario grabar(SolCambioDiccionario o) throws DAOException {
		SolCambioDiccionario dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public SolCambioDiccionario modificar(SolCambioDiccionario o) throws DAOException {
		SolCambioDiccionario dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<SolCambioDiccionario> getListaPorPk(Object o) throws DAOException {
		List<SolCambioDiccionario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
