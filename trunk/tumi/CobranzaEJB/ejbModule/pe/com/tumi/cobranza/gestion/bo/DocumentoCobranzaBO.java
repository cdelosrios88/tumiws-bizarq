package pe.com.tumi.cobranza.gestion.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.dao.DocumentoCobranzaDao;
import pe.com.tumi.cobranza.gestion.dao.impl.DocumentoCobranzaDaoIbatis;
import pe.com.tumi.cobranza.gestion.domain.DocumentoCobranza;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class DocumentoCobranzaBO {
	
protected  static Logger log = Logger.getLogger(DocumentoCobranzaBO.class);
private DocumentoCobranzaDao dao = (DocumentoCobranzaDao)TumiFactory.get(DocumentoCobranzaDaoIbatis.class);
	
	public List<DocumentoCobranza> getListaDocumentoCobranza(Object o) throws BusinessException{
		log.info("-----------------------Debugging DocumentoCobranzaBO.getListaDocumentoCobranza-----------------------------");
		List<DocumentoCobranza> lista = null;
		
		log.info("Seteando los parametros de busqueda de Documento Cobranza: ");
		log.info("-------------------------------------------------");
		DocumentoCobranza DocumentoCobranza = (DocumentoCobranza)o;
		HashMap map = new HashMap();
		map.put("intParaTipoCobranza",DocumentoCobranza.getIntParaTipoCobranza());
		
		try{
			lista = dao.getListaDocumentoCobranza(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	

}
