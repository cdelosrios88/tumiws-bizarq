package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.convenio.dao.AdendaDao;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.composite.ConvenioComp;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaPlaneamientoComp;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class AdendaDaoIbatis extends TumiDaoIbatis implements AdendaDao{
	
	public Adenda grabar(Adenda o) throws DAOException {
		Adenda dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Adenda modificar(Adenda o) throws DAOException {
		Adenda dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Adenda> getListaAdendaPorPK(Object o) throws DAOException{
		List<Adenda> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<HojaPlaneamientoComp> getListaParaFiltro(Object o) throws DAOException{
		List<HojaPlaneamientoComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParaFiltro", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Adenda eliminarAdenda(Adenda o) throws DAOException {
		Adenda dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".eliminar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Adenda aprobarRechazarAdenda(Adenda o) throws DAOException {
		Adenda dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".aprobarRechazar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConvenioComp> getListaConvenio(Object o) throws DAOException{
		List<ConvenioComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaConvenio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConvenioComp> getListaConvenioDet(Object o) throws DAOException{
		List<ConvenioComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaConvenioDet", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConvenioComp> getListaConvenioPorTipoConvenio(Object o) throws DAOException{
		List<ConvenioComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaConvenioPorTipoConvenio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Adenda grabarConvenio(Adenda o) throws DAOException {
		//Adenda dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarConvenio", o);
			//dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return o;
	}
	
	public Adenda modificarConvenio(Adenda o) throws DAOException {
		//Adenda dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".modificarConvenio", o);
			//dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return o;
	}
}