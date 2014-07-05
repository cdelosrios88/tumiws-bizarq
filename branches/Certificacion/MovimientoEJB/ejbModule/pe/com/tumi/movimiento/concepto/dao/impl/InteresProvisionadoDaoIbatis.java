package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.InteresProvisionadoDao;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionado;

public class InteresProvisionadoDaoIbatis extends TumiDaoIbatis implements InteresProvisionadoDao{
		
	public InteresProvisionado grabar(InteresProvisionado o) throws DAOException{
	
		InteresProvisionado dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e){
			System.out.println("---------------------------------------------------------------------"+e);
			throw new DAOException(e);
		}
		return dto;
	}
	
//	public InteresProvisionado modificar(InteresProvisionado o) throws DAOException{
//		InteresProvisionado dto = null;
//		try{
//			getSqlMapClientTemplate().update(getNameSpace() + ".modificar",o);
//			dto=o;
//		}catch(Exception e){
//			throw new DAOException(e);
//		}
//		return dto;
//	}
//	public List<InteresProvisionado> getListaPorPK(Object o) throws DAOException{
//		List<InteresProvisionado> lista = null;
//		try{
//			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
//		}catch(Exception e) {
//			throw new DAOException (e);
//		}
//		return lista;
//	}
}
