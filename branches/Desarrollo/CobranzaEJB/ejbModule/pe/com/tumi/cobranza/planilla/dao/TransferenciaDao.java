package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.cobranza.planilla.domain.Transferencia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface TransferenciaDao extends TumiDao{
	public Transferencia grabar(Transferencia pDto) throws DAOException;
	public List<Transferencia>  getListaTransferencias(Object o) throws DAOException;
	
}	
