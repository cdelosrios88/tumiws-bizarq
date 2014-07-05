package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.cierre.fdoFijo.domain.ControlFondoFijoAnula;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;

public interface ControlFondosFijosDao extends TumiDao{
	public ControlFondosFijos grabar(ControlFondosFijos pDto) throws DAOException;
	public ControlFondosFijos modificar(ControlFondosFijos o) throws DAOException;
	public List<ControlFondosFijos> getListaPorPk(Object o) throws DAOException;
	public List<ControlFondosFijos> getListaParaItem(Object o) throws DAOException;
	public List<ControlFondosFijos> getListaPorBusqueda(Object o) throws DAOException;
	//Agregado 05.12.2013 JCHAVEZ
	public ControlFondoFijoAnula grabarAnulaCierre(ControlFondoFijoAnula o) throws DAOException;
	public List<ControlFondosFijos> getControlFondoFijo(Object o) throws DAOException;
	
}