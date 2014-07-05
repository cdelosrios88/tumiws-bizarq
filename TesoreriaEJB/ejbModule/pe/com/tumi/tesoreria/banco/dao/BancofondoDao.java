package pe.com.tumi.tesoreria.banco.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;

public interface BancofondoDao extends TumiDao{
	public Bancofondo grabar(Bancofondo pDto) throws DAOException;
	public Bancofondo modificar(Bancofondo o) throws DAOException;
	public List<Bancofondo> getListaPorPk(Object o) throws DAOException;
	public List<Bancofondo> getListaPorBusqueda(Object o) throws DAOException;
	public List<Bancofondo> getListaPorTipoFondoFijoYMoneda(Object o) throws DAOException;
	public List<Bancofondo> getListaPorEmpresaYPersona(Object o) throws DAOException;
}	
