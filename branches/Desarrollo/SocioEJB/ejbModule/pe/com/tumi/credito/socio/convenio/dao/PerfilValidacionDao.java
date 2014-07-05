package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.PerfilValidacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface PerfilValidacionDao extends TumiDao{
	public PerfilValidacion grabar(PerfilValidacion o) throws DAOException;
	public PerfilValidacion modificar(PerfilValidacion o) throws DAOException;
	public List<PerfilValidacion> getListaPerfilValidacionPorPK(Object o) throws DAOException;
	public List<PerfilValidacion> getListaPerfilValidacionPorEmpresaYPerfil(Object o) throws DAOException;
}
