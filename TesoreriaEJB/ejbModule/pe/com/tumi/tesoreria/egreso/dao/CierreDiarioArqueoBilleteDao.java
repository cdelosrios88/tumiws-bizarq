package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoBillete;

public interface CierreDiarioArqueoBilleteDao extends TumiDao{
	public CierreDiarioArqueoBillete grabar(CierreDiarioArqueoBillete pDto) throws DAOException;
	public CierreDiarioArqueoBillete modificar(CierreDiarioArqueoBillete o) throws DAOException;
	public List<CierreDiarioArqueoBillete> getListaPorPk(Object o) throws DAOException;
	public List<CierreDiarioArqueoBillete> getListaPorCierreDiarioArqueo(Object o) throws DAOException;
}