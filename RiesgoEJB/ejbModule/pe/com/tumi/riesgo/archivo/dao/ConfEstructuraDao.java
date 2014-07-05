package pe.com.tumi.riesgo.archivo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.archivo.domain.ConfEstructura;

public interface ConfEstructuraDao extends TumiDao{
	public ConfEstructura grabar(ConfEstructura o) throws DAOException;
	public ConfEstructura modificar(ConfEstructura o) throws DAOException;
	public List<ConfEstructura> getListaPorPk(Object o) throws DAOException;
	
	public List<ConfEstructura> getListaModTipoSoEmp(Object o) throws DAOException;
}
