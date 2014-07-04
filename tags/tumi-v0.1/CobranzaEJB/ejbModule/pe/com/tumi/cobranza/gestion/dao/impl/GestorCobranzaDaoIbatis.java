package pe.com.tumi.cobranza.gestion.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;



import pe.com.tumi.cobranza.gestion.dao.GestorCobranzaDao;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class GestorCobranzaDaoIbatis extends TumiDaoIbatis implements GestorCobranzaDao{
	
	protected  static Logger log = Logger.getLogger(GestorCobranzaDaoIbatis.class);
	
	public List<GestorCobranza> getListaGestorCobranza(Object o) throws DAOException{
		log.info("-----------------------Debugging GestorCobranzaDaoIbatis.getListaGestorCobranza-----------------------------");
		List<GestorCobranza> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaGestorCobranza", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public GestorCobranza grabar(GestorCobranza o) throws DAOException{
		GestorCobranza dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public GestorCobranza modificar(GestorCobranza o) throws DAOException{
		GestorCobranza dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<GestorCobranza> getListaPorPk(Object o) throws DAOException{
		List<GestorCobranza> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<GestorCobranza> getPorPersona(Object o) throws DAOException {
		List<GestorCobranza> lista = null;
		try{
			System.out.println("--PERSONAIN");
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPorPersona", o);
			System.out.println("--PERSONAOUT");
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
}
