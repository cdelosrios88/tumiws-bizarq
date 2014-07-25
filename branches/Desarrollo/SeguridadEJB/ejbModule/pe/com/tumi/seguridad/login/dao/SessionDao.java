package pe.com.tumi.seguridad.login.dao;
//Inicio: REQ14-002 - bizarq - 22/07/2014
import java.util.List;
//Fin: REQ14-002 - bizarq - 22/07/2014
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.login.domain.Session;

public interface SessionDao extends TumiDao {
	public Session grabar(Session o) throws DAOException;
	public Session modificar(Session o) throws DAOException;
	public Integer getCntActiveSessionsByUser(Object o) throws DAOException;
	//Inicio: REQ14-002 - bizarq - 22/07/2014
	public List<Session> getListSesionByUser (Object o) throws DAOException;
	//Fin: REQ14-002 - bizarq - 22/07/2014
}
