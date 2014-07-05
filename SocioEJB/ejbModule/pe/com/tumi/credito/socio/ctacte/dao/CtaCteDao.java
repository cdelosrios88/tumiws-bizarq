package pe.com.tumi.credito.socio.ctacte.dao;

import java.util.List;

import pe.com.tumi.credito.socio.ctacte.domain.CtaCte;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CtaCteDao extends TumiDao{
	public CtaCte grabar(CtaCte o) throws DAOException;
	public CtaCte modificar(CtaCte o) throws DAOException;
	public List<CtaCte> getListaCtaCtePorPK(Object o) throws DAOException;
}
