package pe.com.tumi.cobranza.gestion.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.cobranza.gestion.bo.DocumentoCobranzaBO;
import pe.com.tumi.cobranza.gestion.domain.DocumentoCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaId;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranzaId;
import pe.com.tumi.cobranza.gestion.service.GestionCobranzaService;
import pe.com.tumi.cobranza.gestion.service.GestionCobranzaSocService;
import pe.com.tumi.persona.core.domain.Natural;

@Stateless
public class DocumentoCobranzaFacade extends TumiFacade implements DocumentoCobranzaFacadeRemote, DocumentoCobranzaFacadeLocal {
    
	protected  static Logger log = Logger.getLogger(DocumentoCobranzaFacade.class);
	private DocumentoCobranzaBO docCobranzaBo = (DocumentoCobranzaBO)TumiFactory.get(DocumentoCobranzaBO.class);
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<DocumentoCobranza> getListaDocumentoCobranza(Object obj) throws BusinessException{
    	log.info("-----------------------Debugging DocumentoCobranzaFacade.getListaDocumentoCobranza-----------------------------");
    	List<DocumentoCobranza>  lista;
		try{
			   lista = docCobranzaBo.getListaDocumentoCobranza(obj);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
	
    	     
}
