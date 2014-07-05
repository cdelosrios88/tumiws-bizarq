package pe.com.tumi.movimiento.concepto.dao;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;



public interface InteresCanceladoDao extends TumiDao{
	public InteresCancelado grabar(InteresCancelado o) throws DAOException;
	public InteresCancelado modificar(InteresCancelado o) throws DAOException;
	public List<InteresCancelado> getListaPorPK(Object o) throws DAOException;
	public List<InteresCancelado> getListaPorExpediente(Object o) throws DAOException;
	public List<InteresCancelado> getListMaxPorExpediente(Object o) throws DAOException;
	
}
