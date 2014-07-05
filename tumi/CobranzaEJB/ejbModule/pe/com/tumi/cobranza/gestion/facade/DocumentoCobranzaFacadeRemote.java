package pe.com.tumi.cobranza.gestion.facade;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import pe.com.tumi.cobranza.gestion.domain.DocumentoCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.gestion.domain.GestorCobranzaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Remote
public interface DocumentoCobranzaFacadeRemote {
	   public List<DocumentoCobranza> getListaDocumentoCobranza(Object obj) throws BusinessException;
		   	 	
		    
}
