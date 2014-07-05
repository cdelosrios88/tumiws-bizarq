package pe.com.tumi.cobranza.gestion.dao;

import java.util.List;

import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface GestorCobranzaDao extends TumiDao {
	
	public List<GestorCobranza> getListaGestorCobranza(Object o) throws DAOException;
	public GestorCobranza grabar(GestorCobranza o) throws DAOException;
	public GestorCobranza modificar(GestorCobranza o) throws DAOException;
	public List<GestorCobranza> getListaPorPk(Object o) throws DAOException;
	public List<GestorCobranza> getPorPersona(Object o) throws DAOException;
}
