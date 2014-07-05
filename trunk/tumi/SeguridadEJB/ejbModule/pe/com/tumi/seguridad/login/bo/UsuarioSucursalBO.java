package pe.com.tumi.seguridad.login.bo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.dao.UsuarioSucursalDao;
import pe.com.tumi.seguridad.login.dao.impl.UsuarioSucursalDaoIbatis;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursalId;

public class UsuarioSucursalBO {

	private UsuarioSucursalDao dao = (UsuarioSucursalDao)TumiFactory.get(UsuarioSucursalDaoIbatis.class);
	
	public UsuarioSucursal grabarUsuarioSucursal(UsuarioSucursal o) throws BusinessException {
		UsuarioSucursal dto = null;
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
	
	public UsuarioSucursal modificarUsuarioSucursal(UsuarioSucursal o) throws BusinessException{
		UsuarioSucursal dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public UsuarioSucursal getUsuarioSucursalPorPk(UsuarioSucursalId pId) throws BusinessException{
		List<UsuarioSucursal> lista = null;
		UsuarioSucursal domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdSucursal", pId.getIntIdSucursal());
			mapa.put("intPersPersonaPk", pId.getIntPersPersonaPk());
			mapa.put("dtFechaRegistro", pId.getDtFechaRegistro());
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
	
	public List<UsuarioSucursal> getListaUsuarioSucursalPorPkEmpresaUsuario(EmpresaUsuarioId pId) throws BusinessException{
		List<UsuarioSucursal> lista = null;
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
	
	public List<UsuarioSucursal> getListaUsuarioSucursalPkEmpresaUsuarioYFechaEliminacion(EmpresaUsuarioId pId,Timestamp pTsFechaEliminacion) throws BusinessException{
		List<UsuarioSucursal> lista = null;
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
