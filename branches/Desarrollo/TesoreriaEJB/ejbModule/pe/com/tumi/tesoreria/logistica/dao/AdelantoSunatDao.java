package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.AdelantoSunat;

public interface AdelantoSunatDao extends TumiDao{
	public AdelantoSunat grabar(AdelantoSunat pDto) throws DAOException;
	public AdelantoSunat modificar(AdelantoSunat o) throws DAOException;
	public List<AdelantoSunat> getListaPorPk(Object o) throws DAOException;
	public List<AdelantoSunat> getListaPorOrdenCompraDocumento(Object o) throws DAOException;
	//Agregado por cdelosrios, 13/11/2013
	public List<AdelantoSunat> getListaPorDocumentoSunat(Object o) throws DAOException;
	//Fin agregado por cdelosrios, 13/11/2013
}