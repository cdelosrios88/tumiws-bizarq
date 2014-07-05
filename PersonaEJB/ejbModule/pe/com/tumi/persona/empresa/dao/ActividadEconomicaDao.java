package pe.com.tumi.persona.empresa.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;

public interface ActividadEconomicaDao extends TumiDao{
	public ActividadEconomica grabar(ActividadEconomica o) throws DAOException;
	public ActividadEconomica modificar(ActividadEconomica o) throws DAOException;
	public List<ActividadEconomica> getListaActividadEconomicaPorPK(Object o) throws DAOException;
	public List<ActividadEconomica> getListaActividadEconomicaPorIdPersona(Object o) throws DAOException;
}
