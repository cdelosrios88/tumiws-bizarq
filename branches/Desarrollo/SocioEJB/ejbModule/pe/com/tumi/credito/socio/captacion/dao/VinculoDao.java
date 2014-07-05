package pe.com.tumi.credito.socio.captacion.dao;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface VinculoDao extends TumiDao{
	public Vinculo grabar(Vinculo o) throws DAOException;
	public Vinculo modificar(Vinculo o) throws DAOException;
	public List<Vinculo> getListaVinculos() throws DAOException;
	public List<Vinculo> getListaVinculoPorPK(Object o) throws DAOException;
	public List<Vinculo> getListaVinculoPorPKCaptacion(Object o) throws DAOException;
	public Object eliminar(Object o) throws DAOException;
}
