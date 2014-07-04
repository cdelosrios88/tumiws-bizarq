package pe.com.tumi.reporte.operativo.cobranza.centralriesgo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.dao.CarteraCreditoDao;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.CarteraCredito;

public class CarteraCreditoDaoIbatis extends TumiDaoIbatis implements CarteraCreditoDao{

	public List<CarteraCredito> getListaCarteraCredito(Object o) throws DAOException {
		List<CarteraCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCarteraCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}