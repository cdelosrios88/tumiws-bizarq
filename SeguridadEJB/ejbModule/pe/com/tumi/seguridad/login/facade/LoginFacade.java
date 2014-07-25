package pe.com.tumi.seguridad.login.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.bo.EmpresaUsuarioBO;
import pe.com.tumi.seguridad.login.bo.PerfilBO;
import pe.com.tumi.seguridad.login.bo.SessionBO;
import pe.com.tumi.seguridad.login.bo.UsuarioBO;
import pe.com.tumi.seguridad.login.bo.UsuarioPerfilBO;
import pe.com.tumi.seguridad.login.bo.UsuarioSubSucursalBO;
import pe.com.tumi.seguridad.login.bo.UsuarioSucursalBO;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Session;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfil;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfilId;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursalId;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursalId;
import pe.com.tumi.seguridad.login.domain.composite.UsuarioComp;
import pe.com.tumi.seguridad.login.service.LoginService;
import pe.com.tumi.seguridad.login.service.UsuarioCompService;

@Stateless
public class LoginFacade extends TumiFacade implements LoginFacadeRemote, LoginFacadeLocal {

	private static Logger log = Logger.getLogger(LoginFacade.class);
	private UsuarioBO boUsuario = (UsuarioBO)TumiFactory.get(UsuarioBO.class);
	private UsuarioCompService serviceUsuarioComp = (UsuarioCompService)TumiFactory.get(UsuarioCompService.class); 
	private PerfilBO boPerfil = (PerfilBO)TumiFactory.get(PerfilBO.class);
	private UsuarioPerfilBO boUsuarioPerfil = (UsuarioPerfilBO)TumiFactory.get(UsuarioPerfilBO.class);
	private UsuarioSucursalBO boUsuarioSucursal = (UsuarioSucursalBO)TumiFactory.get(UsuarioSucursalBO.class);
	private UsuarioSubSucursalBO boUsuarioSubSucursal = (UsuarioSubSucursalBO)TumiFactory.get(UsuarioSubSucursalBO.class);
	private EmpresaUsuarioBO boEmpresaUsuario = (EmpresaUsuarioBO)TumiFactory.get(EmpresaUsuarioBO.class);
	private LoginService loginService = (LoginService)TumiFactory.get(LoginService.class);
	//Inicio: REQ14-002 - cdelosrios - 20/07/2014
	private SessionBO boSession = (SessionBO)TumiFactory.get(SessionBO.class);
	//Fin: REQ14-002 - cdelosrios - 20/07/2014
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UsuarioComp> getListaUsuarioCompDeBusqueda(UsuarioComp pUsuario) throws BusinessException{
		List<UsuarioComp> lista = null;
		try{
			lista = serviceUsuarioComp.getListaUsuarioCompDeBusqueda(pUsuario);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EmpresaUsuario getEmpresaUsuarioPorPk(EmpresaUsuarioId pId) throws BusinessException{
		EmpresaUsuario domain = null;
		try{
			domain = boEmpresaUsuario.getEmpresaUsuarioPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Perfil> getListaPerfilPorIdEmpresa(Integer pId) throws BusinessException{
		List<Perfil> lista = null;
		try{
			lista = boPerfil.getListaPerfilPorIdEmpresa(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Perfil getPerfilPorPk(PerfilId pId) throws BusinessException{
		Perfil domain = null;
		try{
			domain = boPerfil.getPerfilPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Perfil> getListaPerfilPorPkEmpresaUsuario(EmpresaUsuarioId pId) throws BusinessException{
		List<Perfil> lista = null;
		try{
			lista = boPerfil.getListaPerfilPorPkEmpresaUsuario(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Perfil> getListaPerfilPorPkEmpresaUsuarioYEstado(EmpresaUsuarioId pId,Integer intEstado) throws BusinessException{
		List<Perfil> lista = null;
		try{
			lista = boPerfil.getListaPerfilPorPkEmpresaUsuarioYEstado(pId,intEstado);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UsuarioPerfil getUsuarioPerfilPorPk(UsuarioPerfilId pId) throws BusinessException{
		UsuarioPerfil domain = null;
		try{
			domain = boUsuarioPerfil.getUsuarioPerfilPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public UsuarioSucursal modificarUsuarioSucursal(UsuarioSucursal o) throws BusinessException{
		UsuarioSucursal domain = null;
		try{
			domain = boUsuarioSucursal.modificarUsuarioSucursal(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return domain;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UsuarioSucursal getUsuarioSucursalPorPk(UsuarioSucursalId pId) throws BusinessException{
		UsuarioSucursal domain = null;
		try{
			domain = boUsuarioSucursal.getUsuarioSucursalPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UsuarioSucursal> getListaUsuarioSucursalPorPkEmpresaUsuario(EmpresaUsuarioId pId) throws BusinessException{
		List<UsuarioSucursal> lista = null;
		try{
			lista = boUsuarioSucursal.getListaUsuarioSucursalPorPkEmpresaUsuario(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UsuarioSubSucursal getUsuarioSubSucursalPorPk(UsuarioSubSucursalId pId) throws BusinessException{
		UsuarioSubSucursal domain = null;
		try{
			domain = boUsuarioSubSucursal.getUsuarioSubSucursalPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public Usuario grabarUsuarioPersona(Usuario o,Integer intPersonaUsuario)throws BusinessException{
		Usuario dto = null;
		try{
			dto = loginService.grabarUsuarioPersona(o,intPersonaUsuario);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Usuario modificarUsuarioPersona(Usuario o,Integer intIdPersonaModifica)throws BusinessException{
		Usuario dto = null;
		try{
			dto = loginService.modificarUsuarioPersona(o,intIdPersonaModifica);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public void eliminarIntegralUsuarioPersonaPorIdPersona(Integer intIdPersona,Integer pIntIdPersonaElimina)throws BusinessException{
		try{
			loginService.eliminarIntegralUsuarioPersonaPorIdPersona(intIdPersona,pIntIdPersonaElimina);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Usuario getUsuarioPersonaPorIdPersona(Integer pIntIdPersona) throws BusinessException{
		Usuario domain = null;
		try{
			domain = loginService.getUsuarioPersonaPorIdPersona(pIntIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Usuario getUsuarioPorPk(Integer pId) throws BusinessException{
		Usuario domain = null;
		try{
			domain = boUsuario.getUsuarioPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Usuario getUsuarioPorCodigo(String strCodigo) throws BusinessException{
		Usuario domain = null;
		try{
			domain = boUsuario.getUsuarioPorCodigo(strCodigo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Usuario getUsuarioPorCodigoYClave(String strCodigo, String strClave) throws BusinessException{
		Usuario domain = null;
		try{
			domain = boUsuario.getUsuarioPorCodigoYClave(strCodigo, strClave);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public Usuario modificarUsuario(Usuario o) throws BusinessException{
		Usuario domain = null;
		try{
			domain = boUsuario.modificarUsuario(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public  List<UsuarioSubSucursal> getListaPorSucYSubSucursal(UsuarioSubSucursalId pId) throws BusinessException{
		
		List<UsuarioSubSucursal> lista = null;
		try{
			lista = loginService.getListaPorSucYSubSucursal(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
		}
		
		return lista;
		
	}
	
	//Inicio: REQ14-002 - cdelosrios - 20/07/2014
	/**
	 * @author Christian De los Ríos - Bizarq
	 * @param session <object>Session</object>
	 * 
	 * Descripción:
	 * Método que permite grabar en la tabla SEG_V_SESSION la sesión satisfactoria del usuario
	 * 
	 * @return session <object>Session</object>
	 */
	public Session grabarSession(Session o)throws BusinessException{
		Session dto = null;
		try{
			dto = boSession.grabarSession(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	/**
	 * @author Christian De los Ríos - Bizarq
	 * @param <Integer>intIdPersona</Integer>
	 * 
	 * Descripción:
	 * Método que devuelve si un usuario mantiene una sesión activa o no.
	 * 
	 * @return  <Integer>intEscalar</Integer>
	 */
	public Integer getCntActiveSessionsByUser(Integer intIdPersona) throws BusinessException{
		Integer intEscalar = null;
		try{
			intEscalar = boSession.getCntActiveSessionsByUser(intIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	
	/**
	 * @author Christian De los Ríos - Bizarq
	 * @param session <object>Session</object>
	 * 
	 * Descripción:
	 * Método que permite modificar en la tabla SEG_V_SESSION 
	 * en el momento que el usuario decide terminar su sesión
	 * 
	 * @return session <object>Session</object>
	 */
	public Session modificarSession(Session o)throws BusinessException{
		Session dto = null;
		try{
			dto = boSession.modificarSession(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	//Fin: REQ14-002 - cdelosrios - 20/07/2014
	
	//Inicio: REQ14-002 - cdelosrios - 20/07/2014
	public Session getSesionByUser (Integer intIdPersona) throws BusinessException{ 
		Session dto = null;
		try{
			dto = boSession.getSesionByUser(intIdPersona);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	//Fin: REQ14-002 - cdelosrios - 20/07/2014
	
}