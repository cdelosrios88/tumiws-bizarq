package pe.com.tumi.seguridad.permiso.service;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.bo.PermisoPerfilBO;
import pe.com.tumi.seguridad.permiso.bo.TransaccionBO;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;
import pe.com.tumi.seguridad.permiso.domain.TransaccionId;

public class PermisoService {
	private TransaccionBO boTransaccion = (TransaccionBO)TumiFactory.get(TransaccionBO.class);
	private PermisoPerfilBO boPermisoPerfil =(PermisoPerfilBO)TumiFactory.get(PermisoPerfilBO.class);
	
	public List<Transaccion> grabarListaDinamicaMenu(List<Transaccion> lista) throws BusinessException{
		Transaccion dto = null;
		Transaccion dtoTemp = null;
		try{
			for(int i=0;i<lista.size();i++){
				dto = lista.get(i);
				if(dto.getId() == null || dto.getId().getIntIdTransaccion() == null){
					dto = boTransaccion.grabarTransaccion(dto);
				}else{
					dtoTemp = boTransaccion.getTransaccionPorPk(dto.getId());
					if(dtoTemp == null){
						dto = boTransaccion.grabarTransaccion(dto);
					}else{
						dto = boTransaccion.modificarTransaccion(dto);
					}
				}
				if(dto.getListaTransaccion()!=null && dto.getListaTransaccion().size()!=0){
					grabarListaDinamicaSubMenu(dto.getListaTransaccion(),dto.getId());
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Transaccion> getMenu(Integer intPersEmpresaPk,List<String> listaStrIdTransaccion) throws BusinessException{
		List<Transaccion> listaMenu = null;
		List<Transaccion> listaSubMenu = null;
		TransaccionId lId = null;
		Transaccion transaccionTemp = null;
		try{
			lId = new TransaccionId();
			for(int i=0;i<listaStrIdTransaccion.size();i++){
				lId.setIntPersEmpresaPk(intPersEmpresaPk);
				lId.setIntIdTransaccion(new Integer(listaStrIdTransaccion.get(i)));
				transaccionTemp = boTransaccion.getTransaccionPorPk(lId);
				if(transaccionTemp != null){
					transaccionTemp.setChecked(new Boolean(true));
					transaccionTemp.setPersiste(new Boolean(true));
					if(i==0){
						listaMenu=new ArrayList<Transaccion>();
					}
					listaMenu.add(transaccionTemp);
					listaSubMenu = new ArrayList<Transaccion>();
					listaSubMenu.add(transaccionTemp);
					if(i>0){
						listaMenu.get(i-1).setListaTransaccion(listaSubMenu);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaMenu;
	}
	
	public List<Transaccion> getListaTransaccionPorPkPadre(TransaccionId pId) throws BusinessException{
		List<Transaccion> lista = null;
		List<Transaccion> listaSubNivel = null;
		Transaccion lTransaccion = null;
		try{
			lista = boTransaccion.getListaTransaccionPorPkPadre(pId);
			if(lista!=null){
				for(int i=0;i<lista.size();i++){
					lTransaccion = lista.get(i);
					listaSubNivel = getListaTransaccionPorPkPadre(lTransaccion.getId());
					lTransaccion.setListaTransaccion(listaSubNivel);
				}
			}	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
 
	public List<Transaccion> getListaTransaccionPorPkPadreYIdPerfil(TransaccionId pId,Integer intIdPerfil) throws BusinessException{
		List<Transaccion> lista = null;
		List<Transaccion> listaSubNivel = null;
		Transaccion lTransaccion = null;
		try{
			lista = boTransaccion.getListaTransaccionPorPkPadreYIdPerfil(pId,intIdPerfil);
			if(lista!=null){
				for(int i=0;i<lista.size();i++){
					lTransaccion = lista.get(i);
					if(lTransaccion.getStrModulo() == null && lTransaccion.getStrPagina() == null){
						listaSubNivel = getListaTransaccionPorPkPadreYIdPerfil(lTransaccion.getId(),intIdPerfil);
						lTransaccion.setListaTransaccion(listaSubNivel);
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
	
	public List<Transaccion> grabarListaDinamicaSubMenu(List<Transaccion> lista, TransaccionId pId) throws BusinessException{
		Transaccion dto = null;
		Transaccion dtoTemp = null;
		try{
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				if(dto.getId() == null || dto.getId().getIntIdTransaccion() == null){
					if(dto.getTransaccion()==null)dto.setTransaccion(new Transaccion());
					dto.getTransaccion().setId(pId);
					dto = boTransaccion.grabarTransaccion(dto);
				}else{
					dtoTemp = boTransaccion.getTransaccionPorPk(dto.getId());
					if(dtoTemp == null){
						if(dto.getTransaccion()==null)dto.setTransaccion(new Transaccion());
						dto.getTransaccion().setId(pId);
						dto = boTransaccion.grabarTransaccion(dto);
					}else{
						dto = boTransaccion.modificarTransaccion(dto);
					}
				}
				if(dto.getListaTransaccion() != null && dto.getListaTransaccion().size()!=0){
					grabarListaDinamicaSubMenu(dto.getListaTransaccion(),dto.getId());
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PermisoPerfil> grabarListaDinamicaPermisoPerfil(List<PermisoPerfil> lista, Integer intIdPerfil) throws BusinessException{
		PermisoPerfil dto = null;
		PermisoPerfil dtoTemp = null;
		try{
			for(int i=0;i<lista.size();i++){
				dto = lista.get(i);
				if(dto.getId() == null || dto.getId().getIntIdTransaccion() == null || dto.getId().getIntIdPerfil() == null){
					dto.getId().setIntIdPerfil(intIdPerfil);
					dto = boPermisoPerfil.grabarPermisoPerfil(dto);
				}else{
					dtoTemp = boPermisoPerfil.getPermisoPerfilPorPk(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntIdPerfil(intIdPerfil);
						dto = boPermisoPerfil.grabarPermisoPerfil(dto);
					}else{
						dto = boPermisoPerfil.modificarPermisoPerfil(dto);
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
}
