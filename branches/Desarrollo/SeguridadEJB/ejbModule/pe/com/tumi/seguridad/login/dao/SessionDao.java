/************************************************************************/
/* Nombre de componente: SessionDao */
/* Descripción: Componente que implementa nuevos métodos para la obtención de datos del usuario activo
/* Cod. Req.: REQ14-002   */
/* Autor : Christian De los Ríos */
/* Versión : V1 */
/* Fecha creación : 30/07/2014 */
/* ********************************************************************* */

package pe.com.tumi.seguridad.login.dao;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.login.domain.Session;
import pe.com.tumi.seguridad.login.domain.SessionDB;
import pe.com.tumi.seguridad.login.domain.composite.SessionComp;

public interface SessionDao extends TumiDao {
	public Session grabar(Session o) throws DAOException;
	public Session modificar(Session o) throws DAOException;
	public Integer getCntActiveSessionsByUser(Object o) throws DAOException;
	public List<Session> getListSesionByUser (Object o) throws DAOException;
	//Inicio: REQ14-003 - bizarq - 10/08/2014
	public List<Session> getListaPorPk(Object o) throws DAOException;
	public List<Session> getListaSessionWeb(Object o) throws DAOException;
	public List<SessionComp> getListBlockDB(Object o) throws DAOException;
	public List<SessionDB> getListaSessionDB(Object o) throws DAOException;
	public Integer killBlockDB(Object o) throws DAOException;
	public Integer killSessionDB(Object o) throws DAOException;
	//Fin: REQ14-003 - bizarq - 10/08/2014
}
