package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.InteresCanceladoDao;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;


public class InteresCanceladoDaoIbatis extends TumiDaoIbatis implements InteresCanceladoDao{

	public InteresCancelado grabar(InteresCancelado o) throws DAOException {
		InteresCancelado dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto=o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}
	
	public InteresCancelado modificar(InteresCancelado o) throws DAOException{
		InteresCancelado dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar",o);
			dto=o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<InteresCancelado> getListaPorExpediente(Object o) throws DAOException{
		List<InteresCancelado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<InteresCancelado> getListaPorPK(Object o) throws DAOException{
		List<InteresCancelado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recupera lista del ultimo Interes Cancelado en estado activo, es decir maximo   CMOV_ITEMMOVCTACTE_N
	 */
	public List<InteresCancelado> getListMaxPorExpediente(Object o) throws DAOException{
		List<InteresCancelado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListMaxPorExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}
