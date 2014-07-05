package pe.com.tumi.cobranza.gestion.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.dao.DocumentoCobranzaDao;
import pe.com.tumi.cobranza.gestion.domain.DocumentoCobranza;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class DocumentoCobranzaDaoIbatis extends TumiDaoIbatis implements DocumentoCobranzaDao{
	
	protected  static Logger log = Logger.getLogger(DocumentoCobranzaDaoIbatis.class);
	
	public List<DocumentoCobranza> getListaDocumentoCobranza(Object o) throws DAOException{
		log.info("-----------------------Debugging DocumentoCobranzaDaoIbatis.getLista-----------------------------");
		List<DocumentoCobranza> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLista", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	

}
