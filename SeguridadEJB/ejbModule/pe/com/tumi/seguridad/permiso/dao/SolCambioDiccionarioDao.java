package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.SolCambioDiccionario;

public interface SolCambioDiccionarioDao extends TumiDao {
	public SolCambioDiccionario grabar(SolCambioDiccionario o) throws DAOException;
	public SolCambioDiccionario modificar(SolCambioDiccionario o) throws DAOException;
	public List<SolCambioDiccionario> getListaPorPk(Object o) throws DAOException;
}
