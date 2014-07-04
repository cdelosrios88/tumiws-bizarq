package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.Perfil;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface PerfilDao extends TumiDao{
	public Perfil grabar(Perfil o) throws DAOException;
	public Perfil modificar(Perfil o) throws DAOException;
	public List<Perfil> getListaPerfilPorPK(Object o) throws DAOException;
	public List<Perfil> getListaPerfilPorPKAdenda(Object o) throws DAOException;
}
