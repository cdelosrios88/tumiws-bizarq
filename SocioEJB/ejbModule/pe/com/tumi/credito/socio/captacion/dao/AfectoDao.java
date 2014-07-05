package pe.com.tumi.credito.socio.captacion.dao;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Afecto;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AfectoDao extends TumiDao{
	public Afecto grabar(Afecto o) throws DAOException;
	public Afecto modificar(Afecto o) throws DAOException;
	public List<Afecto> getListaAfectoPorPK(Object o) throws DAOException;
	public List<Afecto> getListaAfectoPorPKCaptacion(Object o) throws DAOException;
	public List<Afecto> getListaAfectadasPorPKCaptacion(Object o) throws DAOException;
}
