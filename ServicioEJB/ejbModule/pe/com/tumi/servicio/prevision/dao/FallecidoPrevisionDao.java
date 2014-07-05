package pe.com.tumi.servicio.prevision.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevision;

public interface FallecidoPrevisionDao {
	public FallecidoPrevision grabar(FallecidoPrevision o) throws DAOException;
	public FallecidoPrevision modificar(FallecidoPrevision o) throws DAOException;
	public List<FallecidoPrevision> getListaPorPk(Object o) throws DAOException;
	public List<FallecidoPrevision> getListaPorExpediente(Object o) throws DAOException;
	public List<FallecidoPrevision> getListaNombreCompletoAes(Object o) throws DAOException;
	public List<FallecidoPrevision> getListaVinculoAes(Object o) throws DAOException;
}
