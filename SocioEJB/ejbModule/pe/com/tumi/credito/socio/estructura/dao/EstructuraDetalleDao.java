package pe.com.tumi.credito.socio.estructura.dao;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface EstructuraDetalleDao extends TumiDao{
	public EstructuraDetalle grabar(EstructuraDetalle o) throws DAOException;
	public EstructuraDetalle modificar(EstructuraDetalle o) throws DAOException;
	public List<EstructuraDetalle> getListaEstructuraDetallePorPK(Object o) throws DAOException;
	public List<EstructuraDetalle> getListaEstructuraDetalle(Object o) throws DAOException;
	public List<EstructuraDetalle> getListaEstructuraDetallePorIdEmpresaYIdNivelYIdSucursal(Object o) throws DAOException;
	public List<EstructuraDetalle> getConveEstrucDetAdministra(Object o) throws DAOException;
	public List<EstructuraDetalle> getConveEstrucDetPlanilla(Object o) throws DAOException;
	public List<EstructuraComp> getListaEstructuraCompPorTipoCovenio(Object o) throws DAOException;
	public List<EstructuraDetalle> getListaPorPkEstructuraPorTipoSocioYModalidad(Object o) throws DAOException;
	public List<EstructuraDetalle> getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(Object o) throws DAOException;
	public List<EstructuraDetalle> getListaPorPkEstructuraYCasoYTipoSocioYModalidad(Object o) throws DAOException;
	public List<EstructuraDetalle> getListaPorCodExterno(Object o) throws DAOException;
	public List<EstructuraDetalle> getListaPorEstructuraDetallePorCodSocioYAdministraYTipoSocYMod(Object o) throws DAOException;
	public List<EstructuraDetalle> getEstructuraDetallePorSucuSubsucuYCodigo(Object o) throws DAOException;
}
