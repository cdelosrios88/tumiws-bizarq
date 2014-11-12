package pe.com.tumi.contabilidad.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.contabilidad.core.dao.ModeloDetalleNivelDao;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelComp;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelId;

public interface ModeloDetalleNivelDao extends TumiDao{
	public ModeloDetalleNivel grabar(ModeloDetalleNivel pDto) throws DAOException;
	public ModeloDetalleNivel modificar(ModeloDetalleNivel o) throws DAOException;
	public List<ModeloDetalleNivel> getListaPorPk(Object o) throws DAOException;
	public List<ModeloDetalleNivel> getListaPorModeloDetalleId(Object o) throws DAOException;
	public ModeloDetalleNivel eliminar(ModeloDetalleNivelId o) throws DAOException;
	//Agregado 30.12.2013 JCHAVEZ
	public List<ModeloDetalleNivelComp> getModeloGiroPrestamo(Object o) throws DAOException;
	//Agregado 02.01.2014 JCHAVEZ
	public List<ModeloDetalleNivelId> getPkModeloXReprestamo(Object o) throws DAOException;
	public List<ModeloDetalleNivelComp> getCampoXPkModelo(Object o) throws DAOException;
	//Agregado 21.01.2014 JCHAVEZ
	public List<ModeloDetalleNivelComp> getModeloGiroPrevision(Object o) throws DAOException;
	//Agregado 23.01.2014 JCHAVEZ
	public List<ModeloDetalleNivelComp> getModeloPlanillaMovilidad(Object o) throws DAOException;
	public String getCuentaPorPagar(Object o) throws DAOException;
	
	public List<ModeloDetalleNivel> getNumeroCuentaPrestamo(Object o) throws DAOException;
	
	public List<ModeloDetalleNivel> getNroCtaPrestamoSinCategoria(Object o) throws DAOException;
	
	//jchavez 27.05.2014
	public List<ModeloDetalleNivelComp> getModeloProvisionRetiro(Object o) throws DAOException;
	public List<ModeloDetalleNivelComp> getModeloProvRetiroInteres(Object o) throws DAOException;
	//Autor: fyalico / Tarea: Creación / Fecha: 11.09.2014 
	public String getCuentaPorCobrar(Object o) throws DAOException;
	 
}	
