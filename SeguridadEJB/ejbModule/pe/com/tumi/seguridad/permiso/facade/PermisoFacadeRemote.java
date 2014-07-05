package pe.com.tumi.seguridad.permiso.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecial;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecialDetalle;
import pe.com.tumi.seguridad.permiso.domain.Computadora;
import pe.com.tumi.seguridad.permiso.domain.ComputadoraAcceso;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesos;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalle;
import pe.com.tumi.seguridad.permiso.domain.Password;
import pe.com.tumi.seguridad.permiso.domain.PasswordId;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;
import pe.com.tumi.seguridad.permiso.domain.TransaccionId;
import pe.com.tumi.seguridad.permiso.domain.composite.MenuComp;

@Remote
public interface PermisoFacadeRemote {
	public List<Transaccion> grabarMenu(List<Transaccion> pLista) throws BusinessException;
	public Transaccion getMenu(Integer intPersEmpresaPk,List<String> listaStrIdTransaccion) throws BusinessException;
	public List<Transaccion> getMenuPorIdEmpresa(Integer pId) throws BusinessException;
	public List<Transaccion> getMenuPorIdPerfil(PerfilId pId) throws BusinessException;
	public Transaccion getTransaccionPorPk(TransaccionId pId) throws BusinessException;
	public List<Transaccion> getListaTransaccionDePrincipalPorIdEmpresa(Integer pId) throws BusinessException;
	public List<Transaccion> getListaTransaccionPorPkPadre(TransaccionId pId) throws BusinessException;
	public List<MenuComp> getListaMenuCompDeBusqueda(MenuComp pMenu) throws BusinessException;
	public Password getPasswordPorPk(PasswordId pId) throws BusinessException;
	public DiasAccesos grabarDiasAccesos(DiasAccesos o, List<DiasAccesosDetalle> l) throws BusinessException;
	public List<DiasAccesos> buscarDiasAccesosPorTipoSucursalYEstadoYEmpresa(DiasAccesos o) throws BusinessException;
	public DiasAccesos eliminarDiasAccesos(DiasAccesos o) throws BusinessException;
	public List<DiasAccesosDetalle> getListaDiasAccesosDetallePorCabecera(DiasAccesos o) throws BusinessException;
	public DiasAccesos modificarDiasAccesosYDetalle(DiasAccesos o, List<DiasAccesosDetalle> l) throws BusinessException;
	public DiasAccesos modificarDiasAccesos(DiasAccesos o) throws BusinessException;
	public Computadora grabarComputadorayAccesos(Computadora o, List<ComputadoraAcceso> l) throws BusinessException;
	public List<Computadora> buscarComputadora(Computadora o) throws BusinessException;
	public Computadora eliminarComputadora(Computadora o) throws BusinessException;
	public List<ComputadoraAcceso> getListaComputadoraAccesoPorCabecera(Computadora o) throws BusinessException;
	public Computadora modificarComputadorayAccesos(Computadora o, List<ComputadoraAcceso> l) throws BusinessException;
	public List<Perfil> getListaPerfilDeBusqueda(Perfil o) throws BusinessException;
	public List<Perfil> getListaPerfilPorIdEmpresa(Integer pId) throws BusinessException;
	public Perfil modificarPerfil(Perfil o) throws BusinessException;
	public Perfil grabarPerfilYPermiso(Perfil o) throws BusinessException;
	public Perfil modificarPerfilYPermiso(Perfil o) throws BusinessException;
	public Perfil getPerfilPorPk(PerfilId pId) throws BusinessException;
	public PermisoPerfil getPermisoPerfilPorPk(PermisoPerfilId pId) throws BusinessException;
    public Perfil getPerfilYListaPermisoPerfilPorPkPerfil(PerfilId pId) throws BusinessException;
	public List<AccesoEspecial> buscarAccesosEspeciales(AccesoEspecial o) throws BusinessException;
	public AccesoEspecial grabarAccesosEspeciales(AccesoEspecial o, List<AccesoEspecialDetalle> l) throws BusinessException;
	public AccesoEspecial modificarAccesoEspecialYDetalle(AccesoEspecial o, List<AccesoEspecialDetalle> l) throws BusinessException;
	public AccesoEspecial eliminarAccesoEspecial(AccesoEspecial o) throws BusinessException;
	public List<AccesoEspecialDetalle> getListaAccesoEspecialDetallePorCabecera(AccesoEspecial o) throws BusinessException;
	public Password getPasswordPorPkYPass(Password o) throws BusinessException;
	public boolean validarAccesoPorEmpresaYSucursal(DiasAccesos diasAccesos) throws BusinessException;
	public boolean validarAccesoPorEmpresaUsuario(AccesoEspecial accesoEspecial) throws BusinessException;

}
