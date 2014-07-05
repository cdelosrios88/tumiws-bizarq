package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.Finalidad;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface FinalidadDao extends TumiDao{
	public Finalidad grabar(Finalidad o) throws DAOException;
	public Finalidad modificar(Finalidad o) throws DAOException;
	public List<Finalidad> getListaFinalidadPorPK(Object o) throws DAOException;
	public List<Finalidad> getListaFinalidadPorPKCredito(Object o) throws DAOException;
}
