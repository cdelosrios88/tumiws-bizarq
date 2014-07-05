package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.List;

import pe.com.tumi.cobranza.planilla.dao.TransferenciaDao;

import pe.com.tumi.cobranza.planilla.domain.Transferencia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class TransferenciaDaoIbatis extends TumiDaoIbatis implements TransferenciaDao{

	public Transferencia grabar(Transferencia o) throws DAOException{
		Transferencia dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<Transferencia> getListaTransferencias(Object o) throws DAOException{
		List<Transferencia> lista = null;
		try{
//			
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaTransferencias",o);		
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}

}