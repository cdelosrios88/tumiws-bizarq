package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.DonacionDao;
import pe.com.tumi.credito.socio.convenio.domain.DonacionRegalia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class DonacionDaoIbatis extends TumiDaoIbatis implements DonacionDao{
	
	public DonacionRegalia grabar(DonacionRegalia o) throws DAOException {
		DonacionRegalia dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public DonacionRegalia modificar(DonacionRegalia o) throws DAOException {
		DonacionRegalia dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<DonacionRegalia> getListaDonacionRegaliaPorPK(Object o) throws DAOException{
		List<DonacionRegalia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DonacionRegalia> getListaDonacionRegaliaPorPKAdenda(Object o) throws DAOException{
		List<DonacionRegalia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkAdenda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}