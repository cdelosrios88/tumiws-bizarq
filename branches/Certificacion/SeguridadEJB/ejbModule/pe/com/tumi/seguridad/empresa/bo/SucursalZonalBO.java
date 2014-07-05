package pe.com.tumi.seguridad.empresa.bo;

import java.util.HashMap;

import java.util.List;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.dao.SucursalZonalDao;
import pe.com.tumi.seguridad.empresa.dao.impl.SucursalZonalDaoIbatis;

public class SucursalZonalBO {
	
private SucursalZonalDao dao = (SucursalZonalDao)TumiFactory.get(SucursalZonalDaoIbatis.class);
	
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
	
	public List<Sucursal> getListaSucursalPorPkEmpresaYTipo(Integer pIntPK,Integer pIntTipo) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pIntPK);
			mapa.put("intIdTipoSucursal", pIntTipo);
			lista = dao.getListaSucursalPorPkEmpresaYTipo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaYTipoDeAne(Integer pIntPK,Integer pIntTipo) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pIntPK);
			mapa.put("intIdTipoSucursal", pIntTipo);
			lista = dao.getListaSucursalPorPkEmpresaYTipoDeAne(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaYTipoDeLib(Integer pIntPK,Integer pIntTipo) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pIntPK);
			mapa.put("intIdTipoSucursal", pIntTipo);
			lista = dao.getListaSucursalPorPkEmpresaYTipoDeLib(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaYIdZonalYTipo(Integer pIntIdEmpresa,Integer pIntIdZonal,Integer pIntTipo) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pIntIdEmpresa);
			mapa.put("intIdZonal", pIntIdZonal);
			mapa.put("intIdTipoSucursal", pIntTipo);
			lista = dao.getListaSucursalPorPkEmpresaYIdZonalYTipo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaYIdZonalYTipoDeAne(Integer pIntIdEmpresa,Integer pIntIdZonal,Integer pIntTipo) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pIntIdEmpresa);
			mapa.put("intIdZonal", pIntIdZonal);
			mapa.put("intIdTipoSucursal", pIntTipo);
			lista = dao.getListaSucursalPorPkEmpresaYIdZonalYTipoDeAne(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Sucursal> getListaSucursalPorPkEmpresaYIdZonalYTipoDeLib(Integer pIntIdEmpresa,Integer pIntIdZonal,Integer pIntTipo) throws BusinessException{
		List<Sucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdEmpresa", pIntIdEmpresa);
			mapa.put("intIdZonal", pIntIdZonal);
			mapa.put("intIdTipoSucursal", pIntTipo);
			lista = dao.getListaSucursalPorPkEmpresaYIdZonalYTipoDeLib(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
