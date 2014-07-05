package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.Firmante;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface FirmanteDao extends TumiDao{
	public Firmante grabar(Firmante o) throws DAOException;
	public Firmante modificar(Firmante o) throws DAOException;
	public List<Firmante> getListaFirmantePorPK(Object o) throws DAOException;
	public List<Firmante> getListaFirmantePorPKAdenda(Object o) throws DAOException;
}
