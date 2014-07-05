package pe.com.tumi.cobranza.gestion.dao;

import java.util.List;

import pe.com.tumi.cobranza.gestion.domain.DocumentoCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface DocumentoCobranzaDao extends TumiDao {
	
	public List<DocumentoCobranza> getListaDocumentoCobranza(Object o) throws DAOException;
}
