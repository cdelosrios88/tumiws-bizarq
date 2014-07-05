package pe.com.tumi.seguridad.permiso.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.bo.PerfilBO;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.permiso.bo.AccesoEspecialDetalleBO;
import pe.com.tumi.seguridad.permiso.bo.ComputadoraAccesoBO;
import pe.com.tumi.seguridad.permiso.bo.ComputadoraBO;
import pe.com.tumi.seguridad.permiso.bo.DiasAccesosBO;
import pe.com.tumi.seguridad.permiso.bo.DiasAccesosDetalleBO;
import pe.com.tumi.seguridad.permiso.bo.PasswordBO;
import pe.com.tumi.seguridad.permiso.bo.PermisoPerfilBO;
import pe.com.tumi.seguridad.permiso.bo.TransaccionBO;
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
import pe.com.tumi.seguridad.permiso.service.AccesoEspecialService;
import pe.com.tumi.seguridad.permiso.service.ComputadoraService;
import pe.com.tumi.seguridad.permiso.service.HorarioService;
import pe.com.tumi.seguridad.permiso.service.MenuCompService;
import pe.com.tumi.seguridad.permiso.service.PerfilService;
import pe.com.tumi.seguridad.permiso.service.PermisoService;
import pe.com.tumi.seguridad.permiso.service.SubMenuService;

@Stateless
public class PermisoFacade extends TumiFacade implements PermisoFacadeRemote, PermisoFacadeLocal {
	private PermisoService permisoService = (PermisoService)TumiFactory.get(PermisoService.class);
	private TransaccionBO boTransaccion = (TransaccionBO)TumiFactory.get(TransaccionBO.class);
	private MenuCompService menuCompService = (MenuCompService)TumiFactory.get(MenuCompService.class);
	private SubMenuService subMenuService = (SubMenuService)TumiFactory.get(SubMenuService.class);
	private PasswordBO boPassword = (PasswordBO)TumiFactory.get(PasswordBO.class);
	private HorarioService horarioService = (HorarioService)TumiFactory.get(HorarioService.class);
	private DiasAccesosDetalleBO boDiasAccesosDetalle = (DiasAccesosDetalleBO)TumiFactory.get(DiasAccesosDetalleBO.class);
	private DiasAccesosBO boDiasAccesos = (DiasAccesosBO)TumiFactory.get(DiasAccesosBO.class);
	private PerfilBO boPerfil = (PerfilBO)TumiFactory.get(PerfilBO.class);
	private PermisoPerfilBO boPermisoPerfil = (PermisoPerfilBO)TumiFactory.get(PermisoPerfilBO.class);
	private PerfilService perfilService = (PerfilService)TumiFactory.get(PerfilService.class);
	private ComputadoraService computadoraService = (ComputadoraService)TumiFactory.get(ComputadoraService.class);
	private ComputadoraBO boComputadora = (ComputadoraBO)TumiFactory.get(ComputadoraBO.class);
	private ComputadoraAccesoBO boComputadoraAcceso = (ComputadoraAccesoBO)TumiFactory.get(ComputadoraAccesoBO.class);
	private AccesoEspecialService accesoEspecialService = (AccesoEspecialService)TumiFactory.get(AccesoEspecialService.class);
	private AccesoEspecialDetalleBO boAccesoEspecialDetalle = (AccesoEspecialDetalleBO)TumiFactory.get(AccesoEspecialDetalleBO.class);
	
	public List<Transaccion> grabarMenu(List<Transaccion> pLista) throws BusinessException{
		List<Transaccion> lista = null;
		try{
			lista = permisoService.grabarListaDinamicaMenu(pLista);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Transaccion getMenu(Integer intPersEmpresaPk,List<String> listaStrIdTransaccion) throws BusinessException{
		Transaccion dto = null;
		List<Transaccion> lista = null;
		List<Transaccion> listaTemp = null;
		try{
			//lo objetos que pertenecen a los niveles seleccionados en pantalla 
			//estan con la opcion de checked asignado a true
			lista = permisoService.getMenu(intPersEmpresaPk,listaStrIdTransaccion);
			if(lista!=null && lista.size()>0){
				listaTemp = new ArrayList<Transaccion>();
				listaTemp.add(lista.get(lista.size()-1));
				subMenuService.getListaSubMenuDeBusqueda(listaTemp);
				dto = lista.get(0);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Transaccion> getMenuPorIdEmpresa(Integer pId) throws BusinessException{
		Transaccion dto = null;
		List<Transaccion> lista = null;
		List<Transaccion> listaTemp = null;
		try{
			lista = boTransaccion.getListaTransaccionDePrincipalPorIdEmpresa(pId);
			if(lista!=null && lista.size()>0){
				for(int i=0;i<lista.size();i++){
					dto = lista.get(i);
					listaTemp = permisoService.getListaTransaccionPorPkPadre(dto.getId());
					if(listaTemp!=null && listaTemp.size()>0){
						dto.setListaTransaccion(listaTemp);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Transaccion> getMenuPorIdPerfil(PerfilId pId) throws BusinessException{
		Transaccion dto = null;
		List<Transaccion> lista = null;
		List<Transaccion> listaTemp = null;
		try{
			lista = boTransaccion.getListaTransaccionDePrincipalPorIdPerfil(pId);
			if(lista!=null && lista.size()>0){
				for(int i=0;i<lista.size();i++){
					dto = lista.get(i);
					listaTemp = permisoService.getListaTransaccionPorPkPadreYIdPerfil(dto.getId(),pId.getIntIdPerfil());
					if(listaTemp!=null && listaTemp.size()>0){
						dto.setListaTransaccion(listaTemp);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Transaccion getTransaccionPorPk(TransaccionId pId) throws BusinessException{
		Transaccion dto = null;
		try{
			dto = boTransaccion.getTransaccionPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Transaccion> getListaTransaccionDePrincipalPorIdEmpresa(Integer pId) throws BusinessException{
		List<Transaccion> lista = null;
		try{
			lista = boTransaccion.getListaTransaccionDePrincipalPorIdEmpresa(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Transaccion> getListaTransaccionPorPkPadre(TransaccionId pId) throws BusinessException{
		List<Transaccion> lista = null;
		try{
			lista = boTransaccion.getListaTransaccionPorPkPadre(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<MenuComp> getListaMenuCompDeBusqueda(MenuComp pMenu) throws BusinessException{
		List<MenuComp> lista = null;
		List<Transaccion> listaTransaccion = null;
		List<Transaccion> listaTransaccionLoop = null;
		try{
			listaTransaccion = menuCompService.getListaMenuCompDeBusqueda(pMenu);
			if(listaTransaccion!=null && listaTransaccion.size()>0){
				if(pMenu.getListaStrIdTransaccion().get(0) == null ){
					subMenuService.getListaSubMenuDeBusqueda(listaTransaccion);
				}else{
					listaTransaccionLoop = listaTransaccion; 
					for(int i=0;i<pMenu.getListaStrIdTransaccion().size();i++){
						if(listaTransaccionLoop.get(0).getListaTransaccion() != null){
							listaTransaccionLoop = listaTransaccionLoop.get(0).getListaTransaccion();
							if(listaTransaccionLoop==null || listaTransaccionLoop.size()==0){
								break;
							}
						}else
							break;
					}
					subMenuService.getListaSubMenuDeBusqueda(listaTransaccionLoop);
				}
				lista = menuCompService.getListaSubMenuComp(listaTransaccion,null,null);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Password getPasswordPorPk(PasswordId pId) throws BusinessException{
		Password dto = null;
		try{
			dto = boPassword.getPasswordPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}

	@Override
	public DiasAccesos grabarDiasAccesos(DiasAccesos o, List<DiasAccesosDetalle> l)
			throws BusinessException {
		DiasAccesos da = null;
		try{
			da = horarioService.grabarDiasAccesosYDetalle(o,l);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return da;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DiasAccesos> buscarDiasAccesosPorTipoSucursalYEstadoYEmpresa(
			DiasAccesos o) throws BusinessException {
		List<DiasAccesos> lista = null;
		try{
			lista = horarioService.buscarDiasAccesosPorTipoSucursalYEstadoYEmpresa(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DiasAccesos eliminarDiasAccesos(DiasAccesos o)
			throws BusinessException {
		try{
			o = horarioService.eliminarDiasAccesos(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return o;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<DiasAccesosDetalle> getListaDiasAccesosDetallePorCabecera(
			DiasAccesos o) throws BusinessException {
		List<DiasAccesosDetalle> lista = null;
		try{
			lista = boDiasAccesosDetalle.getListaPorCabecera(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public DiasAccesos modificarDiasAccesosYDetalle(DiasAccesos o, List<DiasAccesosDetalle> l)
			throws BusinessException {
		DiasAccesos da = null;
		try{
			da = horarioService.modificarDiasAccesosYDetalle(o, l);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return da;
	}
	
	public DiasAccesos modificarDiasAccesos(DiasAccesos o)
			throws BusinessException {
		DiasAccesos da = null;
		try{
			da = boDiasAccesos.modificar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return da;
	}

	public Computadora grabarComputadorayAccesos(Computadora o,
			List<ComputadoraAcceso> l) throws BusinessException {
		Computadora c = null;
		try{
			c = computadoraService.grabarComputadorayAccesos(o, l);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return c;
	}

	public List<Computadora> buscarComputadora(Computadora o)
			throws BusinessException {
		List<Computadora> l = null;
		try{
			l = computadoraService.buscarComputadoras(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return l;
	}

	public Computadora eliminarComputadora(Computadora o)
			throws BusinessException {
		Computadora c = null;
		try{
			c = computadoraService.eliminarComputadora(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return c;
	}

	public List<ComputadoraAcceso> getListaComputadoraAccesoPorCabecera(Computadora o) 
		throws BusinessException {
		List<ComputadoraAcceso> l = null;
		try{
			l = boComputadoraAcceso.getComputadoraAccesoPorCabecera(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return l;
	}

	public Computadora modificarComputadorayAccesos(Computadora o,
			List<ComputadoraAcceso> l) throws BusinessException {
		Computadora c = null;
		try{
			c = computadoraService.modificarComputadorayAccesos(o, l);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return c;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Perfil> getListaPerfilDeBusqueda(Perfil o) throws BusinessException {
		List<Perfil> lista = null;
		try{
			lista = boPerfil.getListaPerfilDeBusqueda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Perfil> getListaPerfilPorIdEmpresa(Integer pId) throws BusinessException {
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
	
	public Perfil grabarPerfilYPermiso(Perfil o) throws BusinessException {
		Perfil dto = null;
		try{
			dto = perfilService.grabarPerfilYPermiso(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Perfil modificarPerfil(Perfil o) throws BusinessException {
		Perfil dto = null;
		try{
			dto = boPerfil.modificarPerfil(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Perfil modificarPerfilYPermiso(Perfil o) throws BusinessException {
		Perfil dto = null;
		try{
			dto = boPerfil.modificarPerfil(o);
			permisoService.grabarListaDinamicaPermisoPerfil(dto.getListaPermisoPerfil(),dto.getId().getIntIdPerfil());
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Perfil getPerfilPorPk(PerfilId pId) throws BusinessException {
		Perfil dto = null;
		try{
			dto = boPerfil.getPerfilPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PermisoPerfil getPermisoPerfilPorPk(PermisoPerfilId pId) throws BusinessException{
		PermisoPerfil dto = null;
		try{
			dto = boPermisoPerfil.getPermisoPerfilPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Perfil getPerfilYListaPermisoPerfilPorPkPerfil(PerfilId pId) throws BusinessException{
		Perfil dto = null;
		try{
			dto = boPerfil.getPerfilPorPk(pId);
			dto.setListaPermisoPerfil(boPermisoPerfil.getListaPermisoPerfilPorPkPerfil(dto.getId()));
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@Override
	public AccesoEspecial grabarAccesosEspeciales(AccesoEspecial o,
			List<AccesoEspecialDetalle> l) throws BusinessException {
		AccesoEspecial c = null;
		try{
			c = accesoEspecialService.grabarAccesosEspeciales(o, l);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return c;
	}
	
	public List<AccesoEspecial> buscarAccesosEspeciales(AccesoEspecial o) 
		throws BusinessException {
		List<AccesoEspecial> l = null;
		try{
			l = accesoEspecialService.buscarAccesosEspeciales(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return l;
	}
	
	public AccesoEspecial eliminarAccesoEspecial(AccesoEspecial o)
		throws BusinessException {
		AccesoEspecial c = null;
		try{
			c = accesoEspecialService.eliminarAccesoEspecial(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return c;
	}
	
	public List<AccesoEspecialDetalle> getListaAccesoEspecialDetallePorCabecera(AccesoEspecial o) 
		throws BusinessException {
		List<AccesoEspecialDetalle> l = null;
		try{
			l = boAccesoEspecialDetalle.getPorCabecera(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return l;
	}
	
	public AccesoEspecial modificarAccesoEspecialYDetalle(AccesoEspecial o,
			List<AccesoEspecialDetalle> l) throws BusinessException {
		AccesoEspecial c = null;
		try{
			c = accesoEspecialService.modificarAccesoEspecialYDetalle(o, l);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return c;
	}
	
	/*Inicio, cdelosrios, 30-06-2013*/
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Password getPasswordPorPkYPass(Password o) throws BusinessException{
		Password dto = null;
		try{
			dto = boPassword.getPasswordPorPkYPass(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	/*Fin, cdelosrios, 30-06-2013*/
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean validarAccesoPorEmpresaYSucursal(DiasAccesos diasAccesos) throws BusinessException {
		boolean esPermitido = false;
		try{
			esPermitido = horarioService.validarAccesoPorEmpresaYSucursal(diasAccesos);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return esPermitido;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean validarAccesoPorEmpresaUsuario(AccesoEspecial accesoEspecial) throws BusinessException{
		
		boolean esPermitido = false;
		try{
			esPermitido = accesoEspecialService.validarAccesoPorEmpresaUsuario(accesoEspecial);
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return esPermitido;
	}
	
}