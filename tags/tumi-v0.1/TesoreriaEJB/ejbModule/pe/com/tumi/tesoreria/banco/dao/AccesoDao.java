package pe.com.tumi.tesoreria.banco.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;

public interface AccesoDao extends TumiDao{
	public Acceso grabar(Acceso pDto) throws DAOException;
	public Acceso modificar(Acceso o) throws DAOException;
	public List<Acceso> getListaPorPk(Object o) throws DAOException;
	public List<Acceso> getListaPorBusqueda(Object o) throws DAOException;
}	
