package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevision;

public interface AutorizaVerificaPrevisionDao extends TumiDao{
	public AutorizaVerificaPrevision grabar(AutorizaVerificaPrevision o) throws DAOException;
	public AutorizaVerificaPrevision modificar(AutorizaVerificaPrevision o) throws DAOException;
	public List<AutorizaVerificaPrevision> getListaPorPk(Object o) throws DAOException;
	public List<AutorizaVerificaPrevision> getListaPorExpedientePrevision(Object o) throws DAOException;
}
