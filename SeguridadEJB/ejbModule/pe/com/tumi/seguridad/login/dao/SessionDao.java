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

public interface SessionDao extends TumiDao {
	public Session grabar(Session o) throws DAOException;
	public Session modificar(Session o) throws DAOException;
	public Integer getCntActiveSessionsByUser(Object o) throws DAOException;
	public List<Session> getListSesionByUser (Object o) throws DAOException;
}
