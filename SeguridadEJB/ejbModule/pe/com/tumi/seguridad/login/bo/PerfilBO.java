package pe.com.tumi.seguridad.login.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.dao.PerfilDao;
import pe.com.tumi.seguridad.login.dao.impl.PerfilDaoIbatis;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;

public class PerfilBO {

	private PerfilDao dao = (PerfilDao)TumiFactory.get(PerfilDaoIbatis.class);
	
	public Perfil grabarPerfil(Perfil o) throws BusinessException {
		Perfil dto = null;
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
	
	public Perfil modificarPerfil(Perfil o) throws BusinessException{
		Perfil dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Perfil getPerfilPorPk(PerfilId pId) throws BusinessException{
		List<Perfil> lista = null;
		Perfil domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdPerfil", pId.getIntIdPerfil());
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
	
	public List<Perfil> getListaPerfilPorPkEmpresaUsuario(EmpresaUsuarioId pId) throws BusinessException{
		List<Perfil> lista = null;
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
	
	public List<Perfil> getListaPerfilPorPkEmpresaUsuarioYEstado(EmpresaUsuarioId pId,Integer intEstado) throws BusinessException{
		List<Perfil> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intPersPersonaPk", pId.getIntPersPersonaPk());
			mapa.put("intIdEstado", intEstado);
			lista = dao.getListaPorPkEmpresaUsuarioYEstado(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Perfil> getListaPerfilPorIdEmpresa(Integer pId) throws BusinessException{
		List<Perfil> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId);
			lista = dao.getListaPorIdEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Perfil> getListaPerfilDeBusqueda(Perfil o) throws BusinessException{
		List<Perfil> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", o.getId().getIntPersEmpresaPk());
			mapa.put("intIdPerfil", o.getId().getIntIdPerfil());
			mapa.put("strDescripcion", o.getStrDescripcion());
			mapa.put("tsFechaRegistro", o.getTsFechaRegistro());
			mapa.put("intTipoPerfil", o.getIntTipoPerfil());
			if(o.getBlnIndeterminado() != null && o.getBlnIndeterminado().booleanValue() == true)
				mapa.put("intIndeterminado", new Integer(1));
			else
				mapa.put("intIndeterminado", new Integer(0));
			if(o.getBlnVigencia() != null && o.getBlnVigencia().booleanValue() == true)
				mapa.put("intVigencia", new Integer(1));
			else
				mapa.put("intVigencia", new Integer(0));
			mapa.put("dtDesde", o.getDtDesde());
			mapa.put("dtHasta", o.getDtHasta());
			mapa.put("intIdEstado", o.getIntIdEstado());
			lista = dao.getListaBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
