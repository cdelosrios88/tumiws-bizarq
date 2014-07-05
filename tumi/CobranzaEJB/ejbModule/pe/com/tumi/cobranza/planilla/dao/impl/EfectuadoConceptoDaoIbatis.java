package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.cobranza.planilla.dao.EfectuadoConceptoDao;
import pe.com.tumi.cobranza.planilla.dao.EfectuadoDao;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;

public class EfectuadoConceptoDaoIbatis extends TumiDaoIbatis implements EfectuadoConceptoDao{

	
	public List<EfectuadoConcepto> getListaPorEfectuado(Object o) throws DAOException{
		List<EfectuadoConcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEfectuado", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
    /** CREADO 06/08/2013 
     * SE OBTINIE EFECTUADOCONCEPTO POR PK DE EFECTUADO Y EXPEDIENTE
     * **/	
	public List<EfectuadoConcepto> getListaPorEfectuadoYExpediente(Object o) throws DAOException{
		List<EfectuadoConcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEfectuadoYExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 14-09-2013
     * OBTENER EFECTUADOCONCEPTO POR PK DE EFECTUADO Y (EXPEDIENTE O CUENTACONCEPTO) Y TIPOCONCEPTOGRAL
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<EfectuadoConcepto> getListaPorEfectuadoPKEnvioConcepto(Object o) throws DAOException{
		List<EfectuadoConcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEfectuadoPKEnvioConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public EfectuadoConcepto grabar(EfectuadoConcepto o) throws DAOException
	{
		EfectuadoConcepto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar",o);
		}catch(Exception e)
		{
			throw new DAOException(e);
		}
		return dto;
	}
	public EfectuadoConcepto grabarSub(EfectuadoConcepto o) throws DAOException
	{
		EfectuadoConcepto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarSub",o);
		}catch(Exception e)
		{
			throw new DAOException(e);
		}
		return dto;
	}
	
	public EfectuadoConcepto modificar(EfectuadoConcepto o) throws DAOException
	{
		EfectuadoConcepto dto = null;
		try
		{
			getSqlMapClientTemplate().update(getNameSpace()+ ".modificar",o);
			dto = o;
		}catch(Exception e)
		{
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<EfectuadoConcepto> montoExpedientePrestamo(Object o) throws DAOException{
		List<EfectuadoConcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".montoExpedientePrestamo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<EfectuadoConcepto> montoExpedienteInteres(Object o) throws DAOException{
		List<EfectuadoConcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".montoExpedienteInteres", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<EfectuadoConcepto> montoCuentaPorPagar(Object o) throws DAOException{
		List<EfectuadoConcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".montoCuentaPorPagar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}


	public List<EfectuadoConcepto> prestamoInteres(Object o) throws DAOException{
		List<EfectuadoConcepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".prestamoInteres", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}	




