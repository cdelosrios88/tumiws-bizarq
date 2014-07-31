/************************************************************************/
/* Nombre de componente: SessionBO */
/* Descripción: Componente que implementa nuevos métodos 
/* Cod. Req.: REQ14-002   */
/* Autor : Christian De los Ríos */
/* Versión : V1.1 - modificación */
/* Fecha creación : 30/07/2014 */
/* ********************************************************************* */

package pe.com.tumi.seguridad.login.bo;

import java.util.HashMap;
import java.util.List;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.dao.SessionDao;
import pe.com.tumi.seguridad.login.dao.impl.SessionDaoIbatis;
import pe.com.tumi.seguridad.login.domain.Session;

public class SessionBO {

	private SessionDao dao = (SessionDao)TumiFactory.get(SessionDaoIbatis.class);
	
	public Session grabarSession(Session o) throws BusinessException {
		Session dto = null;
		try{
			o.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Session modificarSession(Session o) throws BusinessException{
		Session dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Integer getCntActiveSessionsByUser(Integer intIdPersona) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersPersonaPk", intIdPersona);
			intEscalar = dao.getCntActiveSessionsByUser(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	//Inicio: REQ14-002 - bizarq - 22/07/2014
	public Session getSesionByUser (Integer intIdPersona) throws BusinessException {
		Session dto = null;
		List<Session> lstSessionUser = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersPersonaPk", intIdPersona);
			lstSessionUser =  dao.getListSesionByUser(mapa);
			if(lstSessionUser != null){
				dto = lstSessionUser.get(0);
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	//Fin: REQ14-002 - bizarq - 22/07/2014
}