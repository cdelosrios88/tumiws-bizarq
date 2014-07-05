package pe.com.tumi.contabilidad.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.contabilidad.core.dao.ModeloDetalleDao;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;

public interface ModeloDetalleDao extends TumiDao{
	public ModeloDetalle grabar(ModeloDetalle pDto) throws DAOException;
	public ModeloDetalle modificar(ModeloDetalle o) throws DAOException;
	public List<ModeloDetalle> getListaPorPk(Object o) throws DAOException;
	public List<ModeloDetalle> getListaPorModeloId(Object o) throws DAOException;
	public ModeloDetalle eliminar(ModeloDetalleId o) throws DAOException;
	
	public List<ModeloDetalle> getListaDebeOfCobranza(Object o) throws DAOException;
	
	public List<ModeloDetalle> getListaDebeOfCobranzaUSUARIO10(Object o) throws DAOException;
	
	public List<ModeloDetalle> getListaDebeOfCobranzaUSUARIONO10(Object o) throws DAOException;


}