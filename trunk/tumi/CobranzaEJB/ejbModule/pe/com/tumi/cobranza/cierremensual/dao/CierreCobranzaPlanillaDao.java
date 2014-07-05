package pe.com.tumi.cobranza.cierremensual.dao;

import java.util.List;

import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanilla;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CierreCobranzaPlanillaDao extends TumiDao {
	public List<CierreCobranzaPlanilla> getListaCierrePlanillaPorPkCierreCobranza(Object o) throws DAOException;
	public List<CierreCobranzaPlanilla> getCierreCobranzaPlanillaPorPK(Object o) throws DAOException;
	public List<CierreCobranzaPlanilla> getListaCierreCobranzaPlanillaPorCobranza(Object o) throws DAOException;
	public CierreCobranzaPlanilla grabar(CierreCobranzaPlanilla o) throws DAOException;
	public CierreCobranzaPlanilla modificar(CierreCobranzaPlanilla o) throws DAOException;
	
	public List<CierreCobranzaPlanilla> getCierreCobranzaPlanillaValidarEnvio(Object o) throws DAOException;
}
