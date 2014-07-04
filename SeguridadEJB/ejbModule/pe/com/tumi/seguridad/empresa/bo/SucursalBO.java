package pe.com.tumi.seguridad.empresa.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.dao.SucursalDao;
import pe.com.tumi.seguridad.empresa.dao.impl.SucursalDaoIbatis;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;

public class SucursalBO {
	
private SucursalDao dao = (SucursalDao)TumiFactory.get(SucursalDaoIbatis.class);
	
	public Sucursal grabarSucursal(Sucursal o) throws BusinessException {
		Sucursal dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Sucursal modificarSucursal(Sucursal o) throws BusinessException{
		Sucursal dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Sucursal getSucursalPorPk(Integer pId) throws BusinessException{
		List<Sucursal> lista = null;
		Sucursal domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdSucursal", 	pId);
			lista = dao.getListaSucursalPorPk(mapa);
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
	
	public Sucursal getPorPkYIdTipoSucursal(Integer idSucursal, Integer idTipoSucursal) throws BusinessException{
		List<Sucursal> lista = null;
		Sucursal domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("idSucursal", 	idSucursal);
			mapa.put("idTipoSucursal", 	idTipoSucursal);
			lista = dao.getPorPkYIdTipoSucursal(mapa);
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
	
	public Sucursal getSucursalPorIdPersona(Integer pIntId) throws BusinessException{
		List<Sucursal> lista = null;
		Sucursal domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdPersona", pIntId);
			lista = dao.getListaSucursalPorIdPersona(mapa);
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
	
	public List<Sucursal> getListaSucursalPorPkZonal(Integer pIntIdZonal) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdZonal", pIntIdZonal);
			lista = dao.getListaSucursalPorPkZonal(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<Sucursal> getListaSucursalPorPkEmpresa(Integer pIntPK) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pIntPK);
			lista = dao.getListaSucursalPorPkEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorEmpresaYTipoSucursal(Integer pIntPK,Integer pTipo) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pIntPK);
			mapa.put("intIdTipoSucursal", pTipo);
			lista = dao.getListaPorEmpresaYTipoSucursal(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorEmpresaYTodoTipoSucursal(Integer pIntPK) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pIntPK);
			lista = dao.getListaPorEmpresaYTodoTipoSucursal(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalSinZonalPorPkEmpresa(Integer pIntPK) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pIntPK);
			lista = dao.getListaSucursalSinZonalPorPkEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaUsuario(EmpresaUsuarioId pPk) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pPk.getIntPersEmpresaPk());
			mapa.put("intIdPersona", pPk.getIntPersPersonaPk());
			lista = dao.getListaSucursalPorPkEmpresaUsuario(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaUsuarioYEstado(EmpresaUsuarioId pPk,Integer intEstado) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pPk.getIntPersEmpresaPk());
			mapa.put("intIdPersona", pPk.getIntPersPersonaPk());
			mapa.put("intEstado", intEstado);
			lista = dao.getListaPorPkEmpresaUsuarioYSt(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Integer getCantidadSucursalPorPkZonal(Integer pkZonal) throws BusinessException{
		Integer escalar = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdZonal", pkZonal);
			escalar = dao.getCantidadSucursalPorPkZonal(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return escalar;
	}
	
	public List<Sucursal> getListaSucursalDeBusqueda(Sucursal pSucursal) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			if(pSucursal.getId() != null){
				mapa.put("intPersEmpresaPk", pSucursal.getId().getIntPersEmpresaPk());
				mapa.put("intIdSucursal", pSucursal.getIntPersPersonaPk());
			}else{
				mapa.put("intPersEmpresaPk", null);
				mapa.put("intIdSucursal", null);
			}
			mapa.put("intIdTipoSucursal", 	pSucursal.getIntIdTipoSucursal());
			mapa.put("intIdEstado", 		pSucursal.getIntIdEstado());
			mapa.put("intIdTercero", 		pSucursal.getIntIdTercero());
			mapa.put("intSubSucursales", 	pSucursal.getIntNroSubSucursales());
			lista = dao.getListaSucursalDeBusqueda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Sucursal eliminarSucursal(Sucursal o) throws BusinessException{
		Sucursal dto = null;
		try{
			dto = dao.eliminarSucursal(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
}
