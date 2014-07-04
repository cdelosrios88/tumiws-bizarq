package pe.com.tumi.seguridad.permiso.service;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.bo.TransaccionBO;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;
import pe.com.tumi.seguridad.permiso.domain.TransaccionId;
import pe.com.tumi.seguridad.permiso.domain.composite.MenuComp;

public class MenuCompService {

	private TransaccionBO boTransaccion = (TransaccionBO)TumiFactory.get(TransaccionBO.class);
	
	public List<Transaccion> getListaMenuCompDeBusqueda(MenuComp pMenu) throws BusinessException{
		TransaccionId lId = null;
		String lStrIdTransaccion = null;
		List<Transaccion> listaTransaccion = null;
		List<Transaccion> listaTransaccionTemp = null;
		Transaccion transaccionLoop = null;
		
		if(pMenu.getListaStrIdTransaccion().get(0) == null ){
			listaTransaccion = boTransaccion.getListaTransaccionDeBusquedaPrincipal(null, pMenu.getIntTipoMenu(), pMenu.getIntIdEstado());
		}else{
			lId = new TransaccionId();
			lStrIdTransaccion = pMenu.getListaStrIdTransaccion().get(0);
			lId.setIntPersEmpresaPk(pMenu.getIntPersEmpresaPk());
			lId.setIntIdTransaccion(new Integer(lStrIdTransaccion));
			listaTransaccion = boTransaccion.getListaTransaccionDeBusquedaPrincipal(lId,pMenu.getIntTipoMenu(),pMenu.getIntIdEstado());
			if(listaTransaccion !=null && listaTransaccion.size()>0){
				transaccionLoop = listaTransaccion.get(0);	
				for(int i=1;i<pMenu.getListaStrIdTransaccion().size();i++){
					lStrIdTransaccion = pMenu.getListaStrIdTransaccion().get(i);
					if(lStrIdTransaccion != null){
						lId.setIntPersEmpresaPk(pMenu.getIntPersEmpresaPk());
						lId.setIntIdTransaccion(new Integer(lStrIdTransaccion));
						listaTransaccionTemp = boTransaccion.getListaTransaccionDeBusqueda(lId,
																						   pMenu.getIntTipoMenu(),
																						   pMenu.getIntIdEstado());
						if(listaTransaccionTemp!=null && listaTransaccionTemp.size()>0){
							transaccionLoop.setListaTransaccion(listaTransaccionTemp);
							transaccionLoop = transaccionLoop.getListaTransaccion().get(0);
						}
					}else
						break;
				}
			}
		}
		return listaTransaccion;
	}
	
	public List<MenuComp> getListaSubMenuComp(List<Transaccion> listaTransaccion,List<String> pListaStrIdTransaccion,List<String> pListaStrNombre)throws BusinessException{
		Transaccion lTransaccion = null;
		MenuComp  dtoMenuComp= null;
		List<MenuComp> lista = null;
		List<MenuComp> listaSubMenu = null;
		List<String> listaStrIdTransaccion = null;
		List<String> listaStrNombre = null;
		try{
			lista = new ArrayList<MenuComp>();
			for(int i=0;i<listaTransaccion.size();i++){
				lTransaccion = listaTransaccion.get(i);
				dtoMenuComp = new MenuComp();
				dtoMenuComp.setIntPersEmpresaPk(lTransaccion.getId().getIntPersEmpresaPk());
				
				listaStrIdTransaccion = new ArrayList<String>();
				if(pListaStrIdTransaccion != null){
					for(int j=0;j<pListaStrIdTransaccion.size();j++){
						listaStrIdTransaccion.add(pListaStrIdTransaccion.get(j));
					}
				}
				listaStrIdTransaccion.add(String.valueOf(lTransaccion.getId().getIntIdTransaccion()));
				dtoMenuComp.setListaStrIdTransaccion(listaStrIdTransaccion);
				
				listaStrNombre = new ArrayList<String>();
				if(pListaStrNombre!=null){
					for(int j=0;j<pListaStrNombre.size();j++){
						listaStrNombre.add(pListaStrNombre.get(j));
					}
				}
				listaStrNombre.add(lTransaccion.getStrNombre());
				dtoMenuComp.setListaStrNombre(listaStrNombre);
				
				dtoMenuComp.setIntTipoMenu(lTransaccion.getIntTipoMenu());
				dtoMenuComp.setIntIdEstado(lTransaccion.getIntIdEstado());
				lista.add(dtoMenuComp);
				if(lTransaccion.getListaTransaccion() != null && lTransaccion.getListaTransaccion().size()>0){
					listaSubMenu = getListaSubMenuComp(lTransaccion.getListaTransaccion(),listaStrIdTransaccion,listaStrNombre);
					lista.addAll(listaSubMenu);
				}
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
