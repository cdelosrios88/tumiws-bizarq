package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDoc;

public interface DocumentoSunatDocDao extends TumiDao{
	public DocumentoSunatDoc grabar(DocumentoSunatDoc pDto) throws DAOException;
	public DocumentoSunatDoc modificar(DocumentoSunatDoc o) throws DAOException;
	public List<DocumentoSunatDoc> getListaPorPk(Object o) throws DAOException;
	public List<DocumentoSunatDoc> getListaPorDocumentoSunat(Object o) throws DAOException;
	public List<DocumentoSunatDoc> getListaPorDocSunatYTipoDoc(Object o) throws DAOException;
}