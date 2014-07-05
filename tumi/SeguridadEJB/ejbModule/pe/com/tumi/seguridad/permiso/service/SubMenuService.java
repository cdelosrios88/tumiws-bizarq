package pe.com.tumi.seguridad.permiso.service;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.bo.TransaccionBO;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;

public class SubMenuService {

	private TransaccionBO boTransaccion = (TransaccionBO)TumiFactory.get(TransaccionBO.class);
	
	public void getListaSubMenuDeBusqueda(List<Transaccion> listaSubMenuPadre) throws BusinessException{
		Transaccion subMenu = null;
		List<Transaccion> listaSubMenu = null;
		try{
			
			for(int i=0;i<listaSubMenuPadre.size();i++){
				subMenu = listaSubMenuPadre.get(i);
				subMenu.setPersiste(new Boolean(true));
				listaSubMenu = boTransaccion.getListaTransaccionDeBusquedaPorPkPadre(subMenu.getId(), 
																					 subMenu.getIntTipoMenu(), 
																				     subMenu.getIntIdEstado());
				if(listaSubMenu != null && listaSubMenu.size()>0){
					getListaSubMenuDeBusqueda(listaSubMenu);
					subMenu.setListaTransaccion(listaSubMenu);
				}
			}
		}catch(BusinessException e){
			throw e;
		}
	}
	
}
