package pe.com.tumi.seguridad.login.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfil;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfilId;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursalId;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursalId;
import pe.com.tumi.seguridad.login.domain.composite.UsuarioComp;

@Remote
public interface LoginFacadeRemote {
	public List<UsuarioComp> getListaUsuarioCompDeBusqueda(UsuarioComp pUsuario) throws BusinessException;
	public EmpresaUsuario getEmpresaUsuarioPorPk(EmpresaUsuarioId pId) throws BusinessException;
	public List<Perfil> getListaPerfilPorIdEmpresa(Integer pId) throws BusinessException;
	public Perfil getPerfilPorPk(PerfilId pId) throws BusinessException;
	public List<Perfil> getListaPerfilPorPkEmpresaUsuario(EmpresaUsuarioId pId) throws BusinessException;
	public List<Perfil> getListaPerfilPorPkEmpresaUsuarioYEstado(EmpresaUsuarioId pId,Integer intEstado) throws BusinessException;
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
		
}
