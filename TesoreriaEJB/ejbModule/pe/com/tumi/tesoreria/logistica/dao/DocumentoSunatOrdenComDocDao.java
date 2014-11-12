package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatOrdenComDoc;

public interface DocumentoSunatOrdenComDocDao extends TumiDao{
	public DocumentoSunatOrdenComDoc grabar(DocumentoSunatOrdenComDoc pDto) throws DAOException;
	public DocumentoSunatOrdenComDoc modificar(DocumentoSunatOrdenComDoc o) throws DAOException;
	public List<DocumentoSunatOrdenComDoc> getListaPorPk(Object o) throws DAOException;
	//Autor: jchavez / Tarea: Creación / Fecha: 26.10.2014
	public List<DocumentoSunatOrdenComDoc> getListaPorOrdenCompraDoc(Object o) throws DAOException;
}
