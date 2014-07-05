package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.PerfilDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface PerfilDetalleDao extends TumiDao{
	public PerfilDetalle grabar(PerfilDetalle o) throws DAOException;
	public PerfilDetalle modificar(PerfilDetalle o) throws DAOException;
	public List<PerfilDetalle> getListaPerfilDetallePorPK(Object o) throws DAOException;
	public List<PerfilDetalle> getListaPerfilDetallePorPKPerfil(Object o) throws DAOException;
}
