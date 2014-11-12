package pe.com.tumi.contabilidad.reclamosDevoluciones.dao;

import java.util.List;

import pe.com.tumi.contabilidad.reclamosDevoluciones.domain.ReclamosDevoluciones;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface ReclamosDevolucionesDao extends TumiDao{
	//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 08.08.2014 /
	public ReclamosDevoluciones grabar(ReclamosDevoluciones o) throws DAOException;
	public ReclamosDevoluciones modificar(ReclamosDevoluciones o) throws DAOException;
	public List<ReclamosDevoluciones> getListaParametro(Object o) throws DAOException;
	public List<ReclamosDevoluciones> getBuscar(Object o) throws DAOException;
}
