package pe.com.tumi.parametro.auditoria.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.domain.AuditoriaMotivo;

public interface AuditoriaMotivoDao extends TumiDao {
	public AuditoriaMotivo grabar(AuditoriaMotivo o) throws DAOException;
	public AuditoriaMotivo modificar(AuditoriaMotivo o) throws DAOException;
	public List<AuditoriaMotivo> getListaPorPk(Object o) throws DAOException;
	public List<AuditoriaMotivo> getListaPorAuditoria(Object o) throws DAOException;
}
