package pe.com.tumi.contabilidad.legalizacion.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.legalizacion.dao.LibroLegalizacionDao;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacionComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class LibroLegalizacionDaoIbatis extends TumiDaoIbatis implements LibroLegalizacionDao{

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	public LibroLegalizacion grabar(LibroLegalizacion o) throws DAOException {
		LibroLegalizacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	public LibroLegalizacion modificar(LibroLegalizacion o) throws DAOException{
		LibroLegalizacion dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 08.09.2014
	public void eliminar(Object o) throws DAOException{
		try{
			getSqlMapClientTemplate().queryForList(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		};
	}

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<LibroLegalizacionComp> getListaPersonaJuridica(Object o) throws DAOException{
		List<LibroLegalizacionComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPersonaJuridica", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<LibroLegalizacionComp> getListaLegalizaciones(Object o) throws DAOException{
		List<LibroLegalizacionComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaLegalizaciones", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	@SuppressWarnings("unchecked")
	public Integer getUltimoFolio(Object o) throws DAOException {
		Integer ultimoFolio = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getUltimoFolio",o);
			m = (HashMap<String, Object>)o;
			ultimoFolio = (Integer)m.get("intUltimoFolioLegal");
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return ultimoFolio;
	}

	//Autor : jbermudez	/ Tarea : Creación	/ Fecha : 03.09.2014
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<LibroLegalizacionComp> getListaLibrosLegalizaciones(Object o) throws DAOException{
		List<LibroLegalizacionComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaLibrosLegalizaciones", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
