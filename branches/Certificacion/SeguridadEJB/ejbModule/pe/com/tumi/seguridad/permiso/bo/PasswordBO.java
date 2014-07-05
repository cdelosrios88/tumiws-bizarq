package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.PasswordDao;
import pe.com.tumi.seguridad.permiso.dao.impl.PasswordDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.Password;
import pe.com.tumi.seguridad.permiso.domain.PasswordId;

public class PasswordBO {

	private PasswordDao dao = (PasswordDao)TumiFactory.get(PasswordDaoIbatis.class);
	
	public Password getPasswordPorPk(PasswordId pId) throws BusinessException{
		List<Password> lista = null;
		Password domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresaPk", pId.getIntEmpresaPk());
			mapa.put("intIdTransaccion", pId.getIntIdTransaccion());
			mapa.put("intIdPassword", pId.getIntIdPassword());
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
	
	public Password getPasswordPorPkYPass(Password o) throws BusinessException{
		List<Password> lista = null;
		Password domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresaPk", o.getId().getIntEmpresaPk());
			mapa.put("intIdTransaccion", o.getId().getIntIdTransaccion());
			mapa.put("strContrasena", o.getStrContrasena());
			lista = dao.getListaPorPkYPass(mapa);
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
	
}
