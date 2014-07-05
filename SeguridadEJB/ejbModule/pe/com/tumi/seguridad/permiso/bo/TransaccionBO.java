package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.permiso.dao.TransaccionDao;
import pe.com.tumi.seguridad.permiso.dao.impl.TransaccionDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;
import pe.com.tumi.seguridad.permiso.domain.TransaccionId;

public class TransaccionBO {

	private TransaccionDao dao = (TransaccionDao)TumiFactory.get(TransaccionDaoIbatis.class);
	
	public Transaccion grabarTransaccion(Transaccion o) throws BusinessException {
		Transaccion dto = null;
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
	
	public Transaccion modificarTransaccion(Transaccion o) throws BusinessException{
		Transaccion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Transaccion getTransaccionPorPk(TransaccionId pId) throws BusinessException{
		List<Transaccion> lista = null;
		Transaccion domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdTransaccion", pId.getIntIdTransaccion());
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
	
	public List<Transaccion> getListaTransaccionDePrincipalPorIdEmpresa(Integer pId) throws BusinessException{
		List<Transaccion> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId);
			lista = dao.getListaPrincipalPorIdEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaTransaccionDePrincipalPorIdPerfil(PerfilId pId) throws BusinessException{
		List<Transaccion> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdPerfil", pId.getIntIdPerfil());
			lista = dao.getListaPrincipalPorIdPerfil(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaTransaccionPorPkPadre(TransaccionId pId) throws BusinessException{
		List<Transaccion> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdTransaccion", pId.getIntIdTransaccion());
			lista = dao.getListaPorPkPadre(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaTransaccionPorPkPadreYIdPerfil(TransaccionId pId, Integer intIdPerfil) throws BusinessException{
		List<Transaccion> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdTransaccion", pId.getIntIdTransaccion());
			mapa.put("intIdPerfil", intIdPerfil);
			lista = dao.getListaPorPkPadreYIdPerfil(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaTransaccionDeBusquedaPrincipal(TransaccionId pId,Integer intTipoMenu,Integer intIdEstado) throws BusinessException{
		List<Transaccion> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			if(pId==null){
				mapa.put("intPersEmpresaPk",null);
				mapa.put("intIdTransaccion",null);
			}else{
				mapa.put("intPersEmpresaPk",pId.getIntPersEmpresaPk());
				mapa.put("intIdTransaccion",pId.getIntIdTransaccion());
			}
			mapa.put("intTipoMenu", intTipoMenu);
			mapa.put("intIdEstado", intIdEstado);
			lista = dao.getListaDeBusquedaPrincipal(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaTransaccionDeBusqueda(TransaccionId pId,Integer intTipoMenu,Integer intIdEstado) throws BusinessException{
		List<Transaccion> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			if(pId==null){
				mapa.put("intPersEmpresaPk",null);
				mapa.put("intIdTransaccion",null);
			}else{
				mapa.put("intPersEmpresaPk",pId.getIntPersEmpresaPk());
				mapa.put("intIdTransaccion",pId.getIntIdTransaccion());
			}
			mapa.put("intTipoMenu", intTipoMenu);
			mapa.put("intIdEstado", intIdEstado);
			lista = dao.getListaDeBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaTransaccionDeBusquedaPorPkPadre(TransaccionId pId,Integer intTipoMenu,Integer intIdEstado) throws BusinessException{
		List<Transaccion> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPadrePk", pId.getIntPersEmpresaPk());
			mapa.put("intIdTransaccionPadre", pId.getIntIdTransaccion());
			mapa.put("intTipoMenu", intTipoMenu);
			mapa.put("intIdEstado", intIdEstado);
			lista = dao.getListaDeBusquedaPorPkPadre(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
