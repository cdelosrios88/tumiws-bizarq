package pe.com.tumi.seguridad.login.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.dao.UsuarioDao;
import pe.com.tumi.seguridad.login.dao.impl.UsuarioDaoIbatis;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class UsuarioBO {

	private UsuarioDao dao = (UsuarioDao)TumiFactory.get(UsuarioDaoIbatis.class);
	
	public Usuario grabarUsuario(Usuario o) throws BusinessException {
		Usuario dto = null;
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
	
	public Usuario modificarUsuario(Usuario o) throws BusinessException{
		Usuario dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Usuario getUsuarioPorCodigo(String strCodigo) throws BusinessException{
		List<Usuario> lista = null;
		Usuario domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("strUsuario", strCodigo);
			lista = dao.getListaPorCodigo(mapa);
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
	
	public Usuario getUsuarioPorCodigoYClave(String strCodigo,String strClave) throws BusinessException{
		List<Usuario> lista = null;
		Usuario domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("strUsuario", strCodigo);
			mapa.put("strContrasena", strClave);
			lista = dao.getListaPorCodigoYClave(mapa);
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
	
	public Usuario getUsuarioPorPk(Integer pId) throws BusinessException{
		List<Usuario> lista = null;
		Usuario domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersPersonaPk", pId);
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
}
