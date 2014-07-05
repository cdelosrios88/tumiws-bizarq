package pe.com.tumi.credito.socio.estructura.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.Terceros;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface TercerosDao extends TumiDao{
	public List<Terceros> getListaFilaTercerosPorDNI(Object o) throws DAOException;
	
	public List<Terceros> getListaColumnaTercerosPorDNI(Object o) throws DAOException;
	
	public List<Terceros> getPorItemDNI(Object o) throws DAOException;
}
