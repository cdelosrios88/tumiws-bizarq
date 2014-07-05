package pe.com.tumi.persona.contacto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.contacto.domain.Comunicacion;

public interface ComunicacionDao extends TumiDao{
	public Comunicacion grabar(Comunicacion o) throws DAOException;
	public Comunicacion modificar(Comunicacion o) throws DAOException;
	public List<Comunicacion> getListaComunicacionPorPK(Object o) throws DAOException;
	public List<Comunicacion> getListaComunicacionPorIdPersona(Object o) throws DAOException;
}
