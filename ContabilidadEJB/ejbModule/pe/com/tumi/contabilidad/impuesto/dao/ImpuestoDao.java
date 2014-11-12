package pe.com.tumi.contabilidad.impuesto.dao;

import java.util.List;

import pe.com.tumi.contabilidad.impuesto.domain.Impuesto;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface ImpuestoDao extends TumiDao{
	public Impuesto grabar(Impuesto o) throws DAOException;
	public Impuesto modificar(Impuesto o) throws DAOException;
	public List<Impuesto> getListaPersonaJuridica(Object o) throws DAOException;
	public List<Impuesto> getListaNombreDniRol(Object o) throws DAOException;
	public List<Impuesto> getBuscar(Object o) throws DAOException;
	public List<Impuesto> getListaImpuesto(Object o) throws DAOException;
	//Autor: jchavez / Tarea: Creación / Fecha: 18.08.2014 / 
	public List<Impuesto> getListaPorPk(Object o) throws DAOException;
}