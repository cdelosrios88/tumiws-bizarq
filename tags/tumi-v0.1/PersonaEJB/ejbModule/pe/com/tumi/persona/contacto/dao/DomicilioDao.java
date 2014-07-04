package pe.com.tumi.persona.contacto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.contacto.domain.Domicilio;

public interface DomicilioDao extends TumiDao{
	public Domicilio grabar(Domicilio o) throws DAOException;
	public Domicilio modificar(Domicilio o) throws DAOException;
	public List<Domicilio> getListaDomicilioPorPK(Object o) throws DAOException;
	public List<Domicilio> getListaDomicilioPorIdPersona(Object o) throws DAOException;
	public Domicilio eliminarDomicilio(Domicilio o) throws DAOException;
}
