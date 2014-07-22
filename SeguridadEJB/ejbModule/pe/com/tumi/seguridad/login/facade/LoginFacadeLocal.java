package pe.com.tumi.seguridad.login.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.framework.negocio.exception.BusinessException;
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

@Local
public interface LoginFacadeLocal {
	public List<UsuarioComp> getListaUsuarioCompDeBusqueda(UsuarioComp pUsuario) throws BusinessException;
	public EmpresaUsuario getEmpresaUsuarioPorPk(EmpresaUsuarioId pId) throws BusinessException;
	public List<Perfil> getListaPerfilPorIdEmpresa(Integer pId) throws BusinessException;
	public List<Perfil> getListaPerfilPorPkEmpresaUsuario(EmpresaUsuarioId pId) throws BusinessException;
	public List<Perfil> getListaPerfilPorPkEmpresaUsuarioYEstado(EmpresaUsuarioId pId,Integer intEstado) throws BusinessException;
	public Perfil getPerfilPorPk(PerfilId pId) throws BusinessException;
	public UsuarioPerfil getUsuarioPerfilPorPk(UsuarioPerfilId pId) throws BusinessException;
	public UsuarioSucursal modificarUsuarioSucursal(UsuarioSucursal o) throws BusinessException;
	public UsuarioSucursal getUsuarioSucursalPorPk(UsuarioSucursalId pId) throws BusinessException;
	public List<UsuarioSucursal> getListaUsuarioSucursalPorPkEmpresaUsuario(EmpresaUsuarioId pId) throws BusinessException;
	public UsuarioSubSucursal getUsuarioSubSucursalPorPk(UsuarioSubSucursalId pId) throws BusinessException;
	public Usuario grabarUsuarioPersona(Usuario o,Integer intPersonaUsuario)throws BusinessException;
	public Usuario modificarUsuarioPersona(Usuario o,Integer intIdPersonaModifica)throws BusinessException;
	public void eliminarIntegralUsuarioPersonaPorIdPersona(Integer pIntIdPersona,Integer pIntIdPersonaElimina)throws BusinessException;
	public Usuario getUsuarioPersonaPorIdPersona(Integer pIntIdPersona) throws BusinessException;
	public Usuario getUsuarioPorCodigo(String strCodigo) throws BusinessException;
	public Usuario getUsuarioPorCodigoYClave(String strCodigo, String strClave) throws BusinessException;
	public Usuario getUsuarioPorPk(Integer pId) throws BusinessException;
	public Usuario modificarUsuario(Usuario o) throws BusinessException;
	public  List<UsuarioSubSucursal> getListaPorSucYSubSucursal(UsuarioSubSucursalId pId) throws BusinessException;
	//Inicio: REQ14-002 - cdelosrios - 20/07/2014
	/**
	 * @author Christian De los R�os - Bizarq
	 * @param session <object>Session</object>
	 * 
	 * Descripci�n:
	 * M�todo que permite grabar en la tabla SEG_V_SESSION la sesi�n satisfactoria del usuario
	 * 
	 * @return session <object>Session</object>
	 */
	public Session grabarSession(Session o)throws BusinessException;
	
	/**
	 * @author Christian De los R�os - Bizarq
	 * @param <Integer>intIdPersona</Integer>
	 * 
	 * Descripci�n:
	 * M�todo que devuelve si un usuario mantiene una sesi�n activa o no.
	 * 
	 * @return  <Integer>intEscalar</Integer>
	 */
	public Integer getCntActiveSessionsByUser(Integer intIdPersona) throws BusinessException;
	//Fin: REQ14-002 - cdelosrios - 20/07/2014
}
