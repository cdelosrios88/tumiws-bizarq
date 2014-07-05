package pe.com.tumi.credito.socio.captacion.dao;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CaptacionDao extends TumiDao{
	public Captacion grabar(Captacion o) throws DAOException;
	public List<Captacion> getListaParaFiltro(Object o) throws DAOException;
	public List<Captacion> getListaCaptacionPorPKCaptacion(Object o) throws DAOException;
	public CaptacionId eliminarCaptacion(CaptacionId o) throws DAOException;
	public Captacion modificarCaptacion(Captacion o) throws DAOException;
	public List<Captacion> getListaCaptacionPorPKCaptacionOpcional(Object o) throws DAOException;
}
