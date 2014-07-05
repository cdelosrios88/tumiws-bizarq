package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;

public interface DocumentoSunatDetalleDao extends TumiDao{
	public DocumentoSunatDetalle grabar(DocumentoSunatDetalle pDto) throws DAOException;
	public DocumentoSunatDetalle modificar(DocumentoSunatDetalle o) throws DAOException;
	public List<DocumentoSunatDetalle> getListaPorPk(Object o) throws DAOException;
	public List<DocumentoSunatDetalle> getListaPorDocumentoSunat(Object o) throws DAOException;
	public List<DocumentoSunatDetalle> getListaPorOrdenCompraDetalle(Object o) throws DAOException;
	//Agregado por cdelosrios, 01/11/2013
	public List<DocumentoSunatDetalle> getListaPorDocumentoSunatYTipoDocSunat(Object o) throws DAOException;
	//Fin agregado por cdelosrios, 01/11/2013
}