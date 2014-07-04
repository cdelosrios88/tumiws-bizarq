package pe.com.tumi.credito.socio.estructura.dao;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AdminPadronDao extends TumiDao{
	public AdminPadron grabar(AdminPadron o) throws DAOException;
	public AdminPadron modificar(AdminPadron o) throws DAOException;
	public List<AdminPadron> getListaAdminPadronPorPK(Object o) throws DAOException;
	public List<AdminPadron> getListaBusqueda(Object o) throws DAOException;
	public List<AdminPadron> getLista(Object o) throws DAOException;
	
	public List<AdminPadron> getTipSocioModPeriodoMes(Object o) throws DAOException;
	public List<AdminPadron> getListaMaximoPorAdminPadron(Object o) throws DAOException;
	
}
