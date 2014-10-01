package pe.com.tumi.contabilidad.cierreContabilidad.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierreContabilidad.domain.CierreContabilidad;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CierreContabilidadDao extends TumiDao{
	//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 19.08.2014 /
	public CierreContabilidad grabarCierreCon(CierreContabilidad o) throws DAOException;
	public CierreContabilidad modificarCierreCon(CierreContabilidad o) throws DAOException;
	public List<CierreContabilidad> getListaBuscarCierre(Object o) throws DAOException;
	public List<CierreContabilidad> getListaCierre(Object o) throws DAOException;
}
