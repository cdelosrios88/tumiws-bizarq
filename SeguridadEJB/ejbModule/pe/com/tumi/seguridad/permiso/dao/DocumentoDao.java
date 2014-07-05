package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.Documento;

public interface DocumentoDao extends TumiDao {
	public Documento grabar(Documento o) throws DAOException;
	public Documento modificar(Documento o) throws DAOException;
	public List<Documento> getListaPorPk(Object o) throws DAOException;
}
