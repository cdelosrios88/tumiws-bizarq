package pe.com.tumi.tesoreria.cierreLogisticaOper.dao;

import java.util.List;

import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestro;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.cierreLogisticaOper.domain.CierreLogisticaOper;

public interface CierreLogisticaOperDao extends TumiDao{

	//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 27.08.2014 /
	public CierreLogisticaOper grabarCierreLogistica(CierreLogisticaOper o) throws DAOException;
	public CierreLogisticaOper modificarCierreLogistica(CierreLogisticaOper o) throws DAOException;
	public List<CierreLogisticaOper> getListaCierreLogisticaVista(Object o) throws DAOException;
	public List<CierreLogisticaOper> getListaCierreLogisticaValidar(Object o) throws DAOException;
	public List<CierreLogisticaOper> getListaBuscarCierre(Object o) throws DAOException;
}
