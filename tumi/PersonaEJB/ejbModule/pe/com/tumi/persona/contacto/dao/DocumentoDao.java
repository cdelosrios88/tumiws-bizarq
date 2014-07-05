package pe.com.tumi.persona.contacto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.contacto.domain.Documento;

public interface DocumentoDao extends TumiDao{
	public Documento grabar(Documento o) throws DAOException;
	public Documento modificar(Documento o) throws DAOException;
	public List<Documento> getListaPorPk(Object o) throws DAOException;
	public List<Documento> getListaPorIdPersonaYTipoIdentidad(Object o) throws DAOException;
	public List<Documento> getListaPorIdPersona(Object o) throws DAOException;
}
