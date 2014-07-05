package pe.com.tumi.credito.socio.captacion.dao;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Contable;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface ContableDao extends TumiDao{
	public Contable grabar(Contable o) throws DAOException;
	public Contable modificar(Contable o) throws DAOException;
	public List<Contable> getListaContablePorPK(Object o) throws DAOException;
	public List<Contable> getListaContablePorPKCaptacion(Object o) throws DAOException;
}
