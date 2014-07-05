package pe.com.tumi.persona.empresa.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.empresa.domain.Empresa;

public interface EmpresaDao extends TumiDao{
	public Empresa grabar(Empresa o) throws DAOException;
	public Empresa modificar(Empresa o) throws DAOException;
	public List<Empresa> getListaEmpresaPorPK(Object o) throws DAOException;
	public List<Empresa> getListaTodos(Object o) throws DAOException;
}
