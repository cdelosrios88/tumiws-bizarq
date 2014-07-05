package pe.com.tumi.credito.socio.captacion.dao;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Requisito;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface RequisitoDao extends TumiDao{
	public Requisito grabar(Requisito o) throws DAOException;
	public Requisito modificar(Requisito o) throws DAOException;
	public List<Requisito> getListaRequisitoPorPK(Object o) throws DAOException;
	public List<Requisito> getListaRequisitoPorPKCaptacion(Object o) throws DAOException;
	public Object eliminar(Object o) throws DAOException;
}
