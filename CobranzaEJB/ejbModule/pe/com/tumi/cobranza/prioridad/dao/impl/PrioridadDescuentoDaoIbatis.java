package pe.com.tumi.cobranza.prioridad.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.prioridad.dao.PrioridadDescuentoDao;
import pe.com.tumi.cobranza.prioridad.domain.PrioridadDescuento;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class PrioridadDescuentoDaoIbatis extends TumiDaoIbatis implements  PrioridadDescuentoDao{
	
	protected  static Logger log = Logger.getLogger(PrioridadDescuentoDaoIbatis.class);
	
	public List<PrioridadDescuento> getListaPorConceptoGnral(Object o) throws DAOException{
		log.info("-----------------------Debugging PrioridadDescuentoDaoIbatis.getListaPorConceptoGnral-----------------------------");
		List<PrioridadDescuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorConceptoGnral", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 11-09-2013
	 * OBTENER PRIORIDADDESCUENTO POR TIPOCONCEPTOGRAL Y 
	 * MOVIMIENTO [CUENTACONCEPTODETALLE (intParaTipocaptacion - intcsocItem) O EXPEDIENTECREDITO (intParaTipoCredito - intCsocItemCredito)]
	 */
	public List<PrioridadDescuento> getListaPorTipoCptoGralCtaCptoExpediente(Object o) throws DAOException{
		log.info("-----------------------Debugging PrioridadDescuentoDaoIbatis.getListaPorTipoCptoGralCtaCptoExpediente-----------------------------");
		List<PrioridadDescuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorTipoCptoGralCtaCptoExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}
