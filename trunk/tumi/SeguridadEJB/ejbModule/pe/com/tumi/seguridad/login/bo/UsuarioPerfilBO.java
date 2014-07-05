package pe.com.tumi.seguridad.login.bo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.dao.UsuarioPerfilDao;
import pe.com.tumi.seguridad.login.dao.impl.UsuarioPerfilDaoIbatis;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfil;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfilId;

public class UsuarioPerfilBO {

	private UsuarioPerfilDao dao = (UsuarioPerfilDao)TumiFactory.get(UsuarioPerfilDaoIbatis.class);
	
	public UsuarioPerfil grabarUsuarioPerfilPersona(UsuarioPerfil o) throws BusinessException {
		UsuarioPerfil dto = null;
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
	
	public UsuarioPerfil modificarUsuarioPerfil(UsuarioPerfil o) throws BusinessException{
		UsuarioPerfil dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public UsuarioPerfil getUsuarioPerfilPorPk(UsuarioPerfilId pId) throws BusinessException{
		List<UsuarioPerfil> lista = null;
		UsuarioPerfil domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intPersPersonaPk", pId.getIntPersPersonaPk());
			mapa.put("intIdPerfil", pId.getIntIdPerfil());
			mapa.put("dtDesde", pId.getDtDesde());
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
	
	public List<UsuarioPerfil> getListaUsuarioPerfilPorPkEmpresaUsuario(EmpresaUsuarioId pId) throws BusinessException{
		List<UsuarioPerfil> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intPersPersonaPk", pId.getIntPersPersonaPk());
			lista = dao.getListaPorPkEmpresaUsuario(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<UsuarioPerfil> getListaUsuarioPerfilPorPkEmpresaUsuarioYFechaEliminacion(EmpresaUsuarioId pId,Timestamp pTsFechaEliminacion) throws BusinessException{
		List<UsuarioPerfil> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intPersPersonaPk", pId.getIntPersPersonaPk());
			mapa.put("tsFechaEliminacion", pTsFechaEliminacion);
			lista = dao.getListaPorPkEmpresaUsuarioYFechaEliminacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
