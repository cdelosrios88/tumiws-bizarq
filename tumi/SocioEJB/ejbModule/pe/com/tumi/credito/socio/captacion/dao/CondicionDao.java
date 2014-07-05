package pe.com.tumi.credito.socio.captacion.dao;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CondicionDao extends TumiDao{
	public Condicion grabar(Condicion o) throws DAOException;
	public Condicion modificar(Condicion o) throws DAOException;
	public List<Condicion> getListaCondicionPorPK(Object o) throws DAOException;
	public List<Condicion> getListaPorPkCaptacion(Object o) throws DAOException;
	public List<Condicion> getListaCondicionSocioPorPkCaptacion(Object o) throws DAOException;
}
