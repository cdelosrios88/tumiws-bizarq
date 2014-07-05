package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoDetalle;

public interface CierreDiarioArqueoDetalleDao extends TumiDao{
	public CierreDiarioArqueoDetalle grabar(CierreDiarioArqueoDetalle pDto) throws DAOException;
	public CierreDiarioArqueoDetalle modificar(CierreDiarioArqueoDetalle o) throws DAOException;
	public List<CierreDiarioArqueoDetalle> getListaPorPk(Object o) throws DAOException;
	public List<CierreDiarioArqueoDetalle> getListaPorCierreDiarioArqueo(Object o) throws DAOException;
}