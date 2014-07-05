package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevision;

public interface AutorizaPrevisionDao extends TumiDao{
	public AutorizaPrevision grabar(AutorizaPrevision o) throws DAOException;
	public AutorizaPrevision modificar(AutorizaPrevision o) throws DAOException;
	public List<AutorizaPrevision> getListaPorPk(Object o) throws DAOException;
	public List<AutorizaPrevision> getListaPorExpedientePrevision(Object o) throws DAOException;
}
