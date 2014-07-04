package pe.com.tumi.cobranza.cierremensual.dao;

import java.util.List;

import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.composite.CierreCobranzaComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CierreCobranzaDao extends TumiDao {
	public List<CierreCobranzaComp> getListaCierreCobranza(Object o) throws DAOException;
	public List<CierreCobranza> getCierreCobranzaPorPK(Object o) throws DAOException;
	public CierreCobranza grabar(CierreCobranza o) throws DAOException;
	public CierreCobranza modificar(CierreCobranza o) throws DAOException;
}
