package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.CobroPlanillas;

public interface CobroPlanillasDao extends TumiDao{
	public CobroPlanillas grabar(CobroPlanillas pDto) throws DAOException;
	public CobroPlanillas modificar(CobroPlanillas o) throws DAOException;
	public List<CobroPlanillas> getListaPorPk(Object o) throws DAOException;
	public List<CobroPlanillas> getListaPorEfectuadoResumen(Object o) throws DAOException;
}