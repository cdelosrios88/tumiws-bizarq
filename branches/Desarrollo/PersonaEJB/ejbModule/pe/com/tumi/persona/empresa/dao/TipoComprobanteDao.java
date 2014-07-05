package pe.com.tumi.persona.empresa.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;
import pe.com.tumi.persona.empresa.domain.TipoComprobante;

public interface TipoComprobanteDao extends TumiDao{
	public TipoComprobante grabar(TipoComprobante o) throws DAOException;
	public TipoComprobante modificar(TipoComprobante o) throws DAOException;
	public List<TipoComprobante> getListaTipoComprobantePorPK(Object o) throws DAOException;
	public List<TipoComprobante> getListaTipoComprobantePorIdPersona(Object o) throws DAOException;
}
