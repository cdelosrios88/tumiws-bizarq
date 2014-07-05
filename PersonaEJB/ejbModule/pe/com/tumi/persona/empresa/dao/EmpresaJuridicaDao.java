package pe.com.tumi.persona.empresa.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.empresa.domain.Empresa;

public interface EmpresaJuridicaDao extends TumiDao{
	
	public List<Empresa> getListaPorPK(Object o) throws DAOException;
	public List<Empresa> getListaEmpresa(Object o) throws DAOException;
}
