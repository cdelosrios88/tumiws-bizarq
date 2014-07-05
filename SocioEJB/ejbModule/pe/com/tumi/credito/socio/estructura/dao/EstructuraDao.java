package pe.com.tumi.credito.socio.estructura.dao;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface EstructuraDao extends TumiDao{
	public Estructura grabar(Estructura o) throws DAOException;
	public Estructura modificar(Estructura o) throws DAOException;
	public List<Estructura> getListaEstructuraPorPK(Object o) throws DAOException;
	public List<Estructura> getListaEstructuraBusqueda(Object o) throws DAOException;
	public List<Estructura> getListaEstructuraPorNivelYCodigoRel(Object o) throws DAOException;
	public List<Estructura> getListaEstructuraPorNivelYCodigo(Object o) throws DAOException;
	public List<Estructura> getListaPorIdEmpresaYIdNivelYIdCasoIdSucursal(Object o) throws DAOException;
	public List<Estructura> getListaPorIdEmpresaYIdCodigoYIdNivelYIdCasoIdSucursal(Object o) throws DAOException;
	public List<Estructura> getListaPorIdEmpresaYIdPersona(Object o) throws DAOException;
	public List<Estructura> getListaPorIdEmpresaYIdCasoIdSucursal(Object o) throws DAOException;
}
