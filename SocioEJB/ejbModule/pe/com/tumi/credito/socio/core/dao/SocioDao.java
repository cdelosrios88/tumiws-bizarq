package pe.com.tumi.credito.socio.core.dao;

import java.util.Date;
import java.util.List;

import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface SocioDao extends TumiDao{
	public List<Socio> getListaSocioBusqueda(Object o) throws DAOException;
	public Socio grabar(Socio o) throws DAOException;
	public Socio modificar(Socio o) throws DAOException;
	public List<Socio> getListaSocioPorPK(Object o) throws DAOException;
	public List<Socio> getListaSocioPorIdEstructuraYTipoSocio(Object o) throws DAOException;
	public List<Socio> getListaDeTitularCuentaPorPkEstructuraYTipoSocio(Object o) throws DAOException;
	public List<Socio> getListaPorEmpresaYINPersona(Object o) throws DAOException;
	public List<Socio> getLPorIdEstructuraTSMAE(Object o) throws DAOException;
	public List<Socio> getListaSocioPorFiltrosBusq(Object o) throws DAOException;
	public List<Socio> getListaSocioEnEfectuado(Object o) throws DAOException;
	//RODO
	public Integer getCantidadHijos(int idPersona);
	public Date getFechaIngreso(int idPersona);
	public String getUbigeoPorId(Integer idUbigeo) throws DAOException;
	
	public List<Socio> getListSocioPorNombres(Object o) throws DAOException;
	
}
