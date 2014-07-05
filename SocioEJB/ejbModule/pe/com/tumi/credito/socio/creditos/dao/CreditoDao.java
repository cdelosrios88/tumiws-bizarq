package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoDao extends TumiDao{
	public Credito grabar(Credito o) throws DAOException;
	public Credito modificar(Credito o) throws DAOException;
	public List<Credito> getListaCreditoPorPK(Object o) throws DAOException;
	public List<Credito> getListaParaFiltro(Object o) throws DAOException;
	public CreditoId eliminarCredito(CreditoId o) throws DAOException;
	//public Object eliminar(Object o) throws DAOException;
	public List<Credito> getlistaCreditoPorCredito(Object o) throws DAOException;
}
