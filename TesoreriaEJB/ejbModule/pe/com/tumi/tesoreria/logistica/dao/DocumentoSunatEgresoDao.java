package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatEgreso;

public interface DocumentoSunatEgresoDao extends TumiDao{
	public DocumentoSunatEgreso grabar(DocumentoSunatEgreso pDto) throws DAOException;
	public DocumentoSunatEgreso modificar(DocumentoSunatEgreso o) throws DAOException;
	public List<DocumentoSunatEgreso> getListaPorPk(Object o) throws DAOException;
	public List<DocumentoSunatEgreso> getListaPorDocumentoSunat(Object o) throws DAOException;
}