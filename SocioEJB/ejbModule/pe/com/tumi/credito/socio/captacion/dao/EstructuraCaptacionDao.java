package pe.com.tumi.credito.socio.captacion.dao;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.EstructuraCaptacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface EstructuraCaptacionDao extends TumiDao{
	public EstructuraCaptacion grabar(EstructuraCaptacion o) throws DAOException;
	public EstructuraCaptacion modificar(EstructuraCaptacion o) throws DAOException;
	public List<EstructuraCaptacion> getListaEstructuraCaptacionPorPK(Object o) throws DAOException;
	public List<EstructuraCaptacion> getListaEstructuraCaptacionPorPKCaptacion(Object o) throws DAOException;
}
