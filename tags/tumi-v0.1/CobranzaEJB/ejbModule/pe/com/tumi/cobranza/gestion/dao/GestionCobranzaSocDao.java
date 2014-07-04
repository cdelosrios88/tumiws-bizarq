package pe.com.tumi.cobranza.gestion.dao;

import java.util.List;

import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface GestionCobranzaSocDao extends TumiDao {
	
	public List<GestionCobranzaSoc> getListaGestionCobranzaSoc(Object o) throws DAOException;
	public GestionCobranzaSoc grabar(GestionCobranzaSoc o) throws DAOException;
	public GestionCobranzaSoc modificar(GestionCobranzaSoc o) throws DAOException;
	public List<GestionCobranzaSoc> getGestionCobranzaSocPorPK(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013 
	public List<GestionCobranzaSoc> getListaPorCuentaPkYPeriodo(Object o) throws DAOException;
}
