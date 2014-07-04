package pe.com.tumi.persona.empresa.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;
import pe.com.tumi.persona.empresa.domain.PerLaboral;

public interface PerLaboralDao extends TumiDao{
	public PerLaboral grabar(PerLaboral o) throws DAOException;
	public PerLaboral modificar(PerLaboral o) throws DAOException;
	public List<PerLaboral> getListaPerLaboralPorPK(Object o) throws DAOException;
	public List<PerLaboral> getListaPerLaboralPorIdPersona(Object o) throws DAOException;
}
