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
import pe.com.tumi.seguridad.login.domain.SessionDB;

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
	
	//Inicio: REQ14-003 - bizarq - 10/08/2014
	public Session getSessionPorPk(Integer pId) throws BusinessException{
		List<Session> lista = null;
		Session domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intSessionPk", pId);
			lista = dao.getListaPorPk(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<Session> getListaSessionWeb(Session o) throws BusinessException{
		List<Session> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", o.getId().getIntPersEmpresaPk());
			mapa.put("intIdSucursal", o.getIntIdSucursal());
			mapa.put("strFullName", "");
			mapa.put("tsFechaRegistro", o.getTsFechaRegistro());
			mapa.put("tsFechaTermino", o.getTsFechaTermino());
			mapa.put("intIdEstado", o.getIntIdEstado());
			lista = dao.getListaSessionWeb(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SessionDB> getListBlockDB(String strSchema, String strProgram, String strObject) throws BusinessException{
		List<SessionDB> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("strSchema", strSchema);
			mapa.put("strProgram", strProgram);
			mapa.put("strObject", strObject);
			lista = dao.getListBlockDB(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SessionDB> getListaSessionDB(String strSchema, String strProgram) throws BusinessException{
		List<SessionDB> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("strSchema", strSchema);
			mapa.put("strProgram", strProgram);
			lista = dao.getListaSessionDB(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Integer killBlockDB(String strSID, String strSerial) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("strSID", strSID);
			mapa.put("strSerial", strSerial);
			intEscalar = dao.killBlockDB(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	
	public Integer killSessionDB(String strSID, String strSerial) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("strSID", strSID);
			mapa.put("strSerial", strSerial);
			intEscalar = dao.killSessionDB(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	//Fin: REQ14-003 - bizarq - 10/08/2014
}