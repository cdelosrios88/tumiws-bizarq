package pe.com.tumi.contabilidad.perdidasSiniestro.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.impuesto.domain.Impuesto;
import pe.com.tumi.contabilidad.perdidasSiniestro.dao.PerdidasSiniestroDao;
import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestro;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class PerdidasSiniestroDaoIbatis extends TumiDaoIbatis implements PerdidasSiniestroDao{
	//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 15.08.2014 /
	public PerdidasSiniestro grabarPerdidas(PerdidasSiniestro o) throws DAOException{
		PerdidasSiniestro dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarSiniestro", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public PerdidasSiniestro modificarPerdidas(PerdidasSiniestro o) throws DAOException{
		PerdidasSiniestro dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<PerdidasSiniestro> getListaJuridicaRuc(Object o) throws DAOException {
		List<PerdidasSiniestro> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaJuricaRuc", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PerdidasSiniestro> getListaBuscar(Object o) throws DAOException {
		List<PerdidasSiniestro> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
