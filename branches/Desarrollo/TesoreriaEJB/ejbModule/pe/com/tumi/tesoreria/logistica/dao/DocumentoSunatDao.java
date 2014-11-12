package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;

public interface DocumentoSunatDao extends TumiDao{
	public DocumentoSunat grabar(DocumentoSunat pDto) throws DAOException;
	public DocumentoSunat modificar(DocumentoSunat o) throws DAOException;
	public List<DocumentoSunat> getListaPorPk(Object o) throws DAOException;
	public List<DocumentoSunat> getListaPorBuscar(Object o) throws DAOException;
	public List<DocumentoSunat> getListaPorOrdenCompra(Object o) throws DAOException;
	public List<DocumentoSunat> getListaEnlazados(Object o) throws DAOException;
	//Agregado por cdelosrios, 18/11/2013
	public List<DocumentoSunat> getListaPorOrdenCompraYTipoDocumento(Object o) throws DAOException;
	//Fin agregado por cdelosrios, 18/11/2013
	//Autor: jchavez / Tarea: Creación / Fecha: 24.10.2014
	public Integer getValidarCierreDocumento(Object o) throws DAOException;
	//Fin jchavez - 24.10.2014
}