package pe.com.tumi.movimiento.concepto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionado;

public interface InteresProvisionadoDao extends TumiDao{
	
	public InteresProvisionado grabar(InteresProvisionado pDto) throws DAOException;
//	public InteresProvisionado modificar(InteresProvisionado o) throws DAOException;
//	public List<InteresProvisionado> getListaPorPK(Object o) throws DAOException;
	
}
