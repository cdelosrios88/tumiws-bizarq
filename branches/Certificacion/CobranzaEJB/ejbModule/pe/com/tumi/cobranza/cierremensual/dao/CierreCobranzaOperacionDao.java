package pe.com.tumi.cobranza.cierremensual.dao;

import java.util.List;

import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CierreCobranzaOperacionDao extends TumiDao {
	public List<CierreCobranzaOperacion> getListaCierreOperacionPorPkCierreCobranza(Object o) throws DAOException;
	public List<CierreCobranzaOperacion> getCierreCobranzaOperacionPorPK(Object o) throws DAOException;
	public CierreCobranzaOperacion grabar(CierreCobranzaOperacion o) throws DAOException;
	public CierreCobranzaOperacion modificar(CierreCobranzaOperacion o) throws DAOException;
}
