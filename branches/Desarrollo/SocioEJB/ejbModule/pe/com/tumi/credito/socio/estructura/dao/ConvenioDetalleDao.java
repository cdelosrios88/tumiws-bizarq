package pe.com.tumi.credito.socio.estructura.dao;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface ConvenioDetalleDao extends TumiDao{
	public ConvenioEstructuraDetalle grabar(ConvenioEstructuraDetalle o) throws DAOException;
	public ConvenioEstructuraDetalle modificar(ConvenioEstructuraDetalle o) throws DAOException;
	public List<ConvenioEstructuraDetalle> getListaConvenioDetallePorPK(Object o) throws DAOException;
	public List<ConvenioEstructuraDetalleComp> getListaConvenioDetallePorPKConvenio(Object o) throws DAOException;
	public List<ConvenioEstructuraDetalleComp> getConvenioDetallePorPKEstructuraDet(Object o) throws DAOException;
	public List<ConvenioEstructuraDetalle> getConvenioDetallePorPKEstructuraDetalle(Object o) throws DAOException;
	public List<ConvenioEstructuraDetalleComp> getListaConvenioEstructuraDetallePorEstructuraDetCompleto(Object o) throws DAOException;
}
