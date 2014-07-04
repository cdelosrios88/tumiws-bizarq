package pe.com.tumi.riesgo.cartera.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.cartera.dao.CarteraCreditoDao;
import pe.com.tumi.riesgo.cartera.dao.CarteraDao;
import pe.com.tumi.riesgo.cartera.domain.Cartera;
import pe.com.tumi.riesgo.cartera.domain.CarteraCredito;

public class CarteraCreditoDaoIbatis extends TumiDaoIbatis implements CarteraCreditoDao{
	
	public List<CarteraCredito> getListaPorMaxPeriodo(Object o) throws DAOException{
		List<CarteraCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorMaxPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
}