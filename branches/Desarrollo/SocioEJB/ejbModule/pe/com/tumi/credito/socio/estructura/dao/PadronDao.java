package pe.com.tumi.credito.socio.estructura.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface PadronDao extends TumiDao{
	public Padron grabar(Padron o) throws DAOException;
	public Padron modificar(Padron o) throws DAOException;
	public List<Padron> getListaPadronPorPK(Object o) throws DAOException;
	public List<Padron> getListaBusqueda(Object o) throws DAOException;
	public List<Padron> getPadronPorLibElectoral(Object o) throws DAOException;
	
	public List<Padron> getPadronSOLOPorLibElectoral(Object o) throws DAOException;
	
}
