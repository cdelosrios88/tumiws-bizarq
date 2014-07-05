package pe.com.tumi.reporte.operativo.cobranza.centralriesgo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.dao.FenacrepDao;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.Fenacrep;

public class FenacrepDaoIbatis extends TumiDaoIbatis implements FenacrepDao{

	public List<Fenacrep> getListaFenacrep(Object o) throws DAOException {
		List<Fenacrep> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getFenacrep", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}