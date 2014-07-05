package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.cobranza.planilla.dao.EnviomontoDao;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;

public class EnviomontoDaoIbatis extends TumiDaoIbatis implements EnviomontoDao{

	public Enviomonto grabar(Enviomonto o) throws DAOException{
		Enviomonto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Enviomonto modificar(Enviomonto o) throws DAOException{
		Enviomonto dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Enviomonto> getListaPorPk(Object o) throws DAOException{
		List<Enviomonto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Enviomonto> getListaDeBuscar(Object o) throws DAOException{
		List<Enviomonto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDeBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<Enviomonto> getLista(Object o) throws DAOException{
		List<Enviomonto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLista", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/** CREADO 06-08-2013 **/
	public List<Enviomonto> getListaPorEnvioConcepto(Object o) throws DAOException{
		List<Enviomonto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEnvioConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<Enviomonto> getListaEnvioMontoPlanillaEfectuada(Object o)throws DAOException
	{
		List<Enviomonto> lista = null;
		try
		{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEnvioMontoPlanillaEfectuada", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Enviomonto> getListaItemConcepto(Object o)throws DAOException
	{
		List<Enviomonto> lista = null;
		try
		{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaItemConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<Enviomonto> getListaEnviomontoXItemEnvioresumen(Object o)throws DAOException
	{
		List<Enviomonto> lista = null;
		try
		{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEnviomontoXItemEnvioresumen", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Enviomonto> getListaXItemEnvioCtoGral(Object o)throws DAOException
	{
		List<Enviomonto> lista = null;
		try
		{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaXItemEnvioCtoGral", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	@Override
	public List<Enviomonto> getEnviomontoPorInt(Object o) throws DAOException {
		List<Enviomonto> lista = null;
		
		try
		{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace()+".getEnviomontoPorInt", o);
		}catch(Exception e)
		{
			throw new DAOException(e);
		}
		return lista;
	}

}	
