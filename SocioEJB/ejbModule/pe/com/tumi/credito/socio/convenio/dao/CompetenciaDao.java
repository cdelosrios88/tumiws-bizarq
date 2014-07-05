package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.Competencia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CompetenciaDao extends TumiDao{
	public Competencia grabar(Competencia o) throws DAOException;
	public Competencia modificar(Competencia o) throws DAOException;
	public List<Competencia> getListaCompetenciaPorPK(Object o) throws DAOException;
	public List<Competencia> getListaCompetenciaPorPKConvenio(Object o) throws DAOException;
}
