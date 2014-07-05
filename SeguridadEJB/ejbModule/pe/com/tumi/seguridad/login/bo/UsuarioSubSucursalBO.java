package pe.com.tumi.seguridad.login.bo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.dao.UsuarioSubSucursalDao;
import pe.com.tumi.seguridad.login.dao.impl.UsuarioSubSucursalDaoIbatis;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursalId;

public class UsuarioSubSucursalBO {

	private UsuarioSubSucursalDao dao = (UsuarioSubSucursalDao)TumiFactory.get(UsuarioSubSucursalDaoIbatis.class);
	
	public UsuarioSubSucursal grabarUsuarioSubSucursal(UsuarioSubSucursal o) throws BusinessException {
		UsuarioSubSucursal dto = null;
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
	
	public UsuarioSubSucursal modificarUsuarioSubSucursal(UsuarioSubSucursal o) throws BusinessException{
		UsuarioSubSucursal dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public UsuarioSubSucursal getUsuarioSubSucursalPorPk(UsuarioSubSucursalId pId) throws BusinessException{
		List<UsuarioSubSucursal> lista = null;
		UsuarioSubSucursal domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdSucursal", pId.getIntIdSucursal());
			mapa.put("intIdSubSucursal", pId.getIntIdSubSucursal());
			mapa.put("dtFechaRegistro", pId.getDtFechaRegistro());
			mapa.put("intPersPersonaPk", pId.getIntPersPersonaPk());
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
	
	public List<UsuarioSubSucursal> getUsuarioSubSucursalPorPkEmpresaUsuario(EmpresaUsuarioId pEmpresaUsuarioId) throws BusinessException{
		List<UsuarioSubSucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pEmpresaUsuarioId.getIntPersEmpresaPk());
			mapa.put("intPersPersonaPk", pEmpresaUsuarioId.getIntPersPersonaPk());
			lista = dao.getListaPorPkEmpresaUsuario(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<UsuarioSubSucursal> getUsuarioSubSucursalPorPkEmpresaUsuarioYFechaEliminacion(EmpresaUsuarioId pEmpresaUsuarioId,Timestamp pTsFechaEliminacion) throws BusinessException{
		List<UsuarioSubSucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pEmpresaUsuarioId.getIntPersEmpresaPk());
			mapa.put("intPersPersonaPk", pEmpresaUsuarioId.getIntPersPersonaPk());
			mapa.put("tsFechaEliminacion", pTsFechaEliminacion);
			lista = dao.getListaPorPkEmpresaUsuarioYFechaEliminacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<UsuarioSubSucursal> getUsuarioSubSucursalPorPkEmpresaUsuarioYIdSucursal(EmpresaUsuarioId pEmpresaUsuarioId,Integer pIdSucursal) throws BusinessException{
		List<UsuarioSubSucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pEmpresaUsuarioId.getIntPersEmpresaPk());
			mapa.put("intPersPersonaPk", pEmpresaUsuarioId.getIntPersPersonaPk());
			mapa.put("intIdSucursal", pIdSucursal);
			lista = dao.getListaPorPkEmpresaUsrYIdSuc(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public  List<UsuarioSubSucursal> getListaPorSucYSubSucursal(UsuarioSubSucursalId pId) throws BusinessException{
			List<UsuarioSubSucursal> lista = null;
			try{
				HashMap<String, Object> mapa = new HashMap<String, Object>();
				mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
				mapa.put("intIdSucursal", pId.getIntIdSucursal());
				mapa.put("intIdSubSucursal", pId.getIntIdSubSucursal());
				lista = dao.getListaPorSucYSubSucursal(mapa);
				
			}catch(DAOException e){
				throw new BusinessException(e);
			}catch(Exception e) {
				throw new BusinessException(e);
			}
			return lista;
	}
				
	
}
