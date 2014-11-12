package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatLetra;

public interface DocumentoSunatLetraDao extends TumiDao{
	public DocumentoSunatLetra grabar(DocumentoSunatLetra pDto) throws DAOException;
	public DocumentoSunatLetra modificar(DocumentoSunatLetra o) throws DAOException;
	public List<DocumentoSunatLetra> getListaPorPk(Object o) throws DAOException;
	public List<DocumentoSunatLetra> getListaPorDocumentoSunat(Object o) throws DAOException;
}
