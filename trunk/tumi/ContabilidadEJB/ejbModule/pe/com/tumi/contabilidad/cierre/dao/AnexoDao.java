package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.Anexo;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AnexoDao extends TumiDao{

	public Anexo grabar(Anexo o) throws DAOException;
	public Anexo modificar(Anexo o) throws DAOException;
	public List<Anexo> getListaPorPk(Object o) throws DAOException;
	public List<Anexo> getListaPorBusqueda(Object o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
}
