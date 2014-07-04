package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesos;

public interface DiasAccesosDao extends TumiDao {
	
	public DiasAccesos grabar(DiasAccesos o) throws DAOException;
	
	public DiasAccesos modificar(DiasAccesos o) throws DAOException;
	
	public List<DiasAccesos> getListaPorPk(Object o) throws DAOException;
	
	public List<DiasAccesos> getListaPorTipoSucursalYEstado(Object o) throws DAOException;
	
	public List<DiasAccesos> getAccesoPorEmpresaYSucursal(Object o) throws DAOException;
	
}
