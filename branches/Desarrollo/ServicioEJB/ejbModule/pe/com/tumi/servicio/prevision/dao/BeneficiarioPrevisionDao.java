package pe.com.tumi.servicio.prevision.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;

public interface BeneficiarioPrevisionDao extends TumiDao{
	public BeneficiarioPrevision grabar(BeneficiarioPrevision o) throws DAOException;
	public BeneficiarioPrevision modificar(BeneficiarioPrevision o) throws DAOException;
	public List<BeneficiarioPrevision> getListaPorPk(Object o) throws DAOException;
	public List<BeneficiarioPrevision> getListaPorExpediente(Object o) throws DAOException;
	public List<BeneficiarioPrevision> getListaPorEgreso(Object o) throws DAOException;
	public List<BeneficiarioPrevision> getListaNombreCompletoBeneficiario(Object o) throws DAOException;
	public List<BeneficiarioPrevision> getListaVinculo(Object o) throws DAOException;
}
