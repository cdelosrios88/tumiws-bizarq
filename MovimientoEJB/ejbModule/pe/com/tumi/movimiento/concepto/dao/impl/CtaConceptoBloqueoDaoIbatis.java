package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.CtaConceptoBloqueoDao;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;

public class CtaConceptoBloqueoDaoIbatis extends TumiDaoIbatis implements CtaConceptoBloqueoDao{
	
	public List<CuentaConcepto> getListaPorEmpresaYCuenta(Object o) throws DAOException{
		List<CuentaConcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpresaYCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}