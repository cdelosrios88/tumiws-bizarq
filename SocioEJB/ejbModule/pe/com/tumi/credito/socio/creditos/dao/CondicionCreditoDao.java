package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CondicionCreditoDao extends TumiDao{
	public CondicionCredito grabar(CondicionCredito o) throws DAOException;
	public CondicionCredito modificar(CondicionCredito o) throws DAOException;
	public List<CondicionCredito> getListaCondicionPorPK(Object o) throws DAOException;
	public List<CondicionCredito> getListaPorPkCaptacion(Object o) throws DAOException;
	public List<CondicionCredito> getListaCondicionSocioPorPkCaptacion(Object o) throws DAOException;
}