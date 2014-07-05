package pe.com.tumi.parametro.auditoria.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.parametro.auditoria.dao.AuditoriaDao;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;

public interface AuditoriaDao extends TumiDao{
	public Auditoria grabar(Auditoria o) throws DAOException;
	public Auditoria modificar(Auditoria o) throws DAOException;
	public List<Auditoria> getListaPorPk(Object o) throws DAOException;
	public List<Auditoria> getListaDeMaxPkPorTabColLlave(Object o) throws DAOException;
}	
