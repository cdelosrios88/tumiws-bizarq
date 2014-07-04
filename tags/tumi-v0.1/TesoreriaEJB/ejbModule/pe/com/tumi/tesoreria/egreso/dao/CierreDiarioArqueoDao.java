package pe.com.tumi.tesoreria.egreso.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueo;

public interface CierreDiarioArqueoDao extends TumiDao{
	public CierreDiarioArqueo grabar(CierreDiarioArqueo pDto) throws DAOException;
	public CierreDiarioArqueo modificar(CierreDiarioArqueo o) throws DAOException;
	public List<CierreDiarioArqueo> getListaPorPk(Object o) throws DAOException;
	public List<CierreDiarioArqueo> getListaPorBuscar(Object o) throws DAOException;
	public List<CierreDiarioArqueo> getListaParaDiaAnterior(Object o) throws DAOException;
	public List<HashMap> getListaFechas(Object o) throws DAOException;
}