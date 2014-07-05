package pe.com.tumi.seguridad.empresa.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.SucursalCodigo;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.ComunicacionPK;
import pe.com.tumi.seguridad.empresa.dao.SubSucursalDao;
import pe.com.tumi.seguridad.empresa.dao.impl.SubSucursalDaoIbatis;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;

public class SubSucursalBO {

	private SubSucursalDao dao = (SubSucursalDao)TumiFactory.get(SubSucursalDaoIbatis.class);
	
	public List<Subsucursal> getListaSubSucursalPorIdSucursal(Integer pId) throws BusinessException{
		List<Subsucursal> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdSucursal", pId);
			lista = dao.getListaSubSucursalPorIdSucursal(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Subsucursal> getListaSubSucursalPorIdSucursalYestado(Integer pId,Integer intEstado) throws BusinessException{
		List<Subsucursal> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdSucursal", pId);
			mapa.put("intEstado", intEstado);
			lista = dao.getListaPorIdSucursalYSt(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Subsucursal grabarSubSucursal(Subsucursal o) throws BusinessException{
		Subsucursal dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Subsucursal modificarSubSucursal(Subsucursal o) throws BusinessException{
		Subsucursal dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Subsucursal getSubSucursalPorPK(SubSucursalPK pPK) throws BusinessException{
		Subsucursal domain = null;
		List<Subsucursal> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdEmpresa", 	pPK.getIntPersEmpresaPk());
			mapa.put("intIdSucursal", 	pPK.getIntIdSucursal());
			mapa.put("intIdSubSucursal",pPK.getIntIdSubSucursal());
			lista = dao.getListaSubSucursalPorPK(mapa);
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
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public Subsucursal getSubSucursalPorIdSubSucursal(Integer intIdSubSucursal) throws BusinessException{
		Subsucursal domain = null;
		List<Subsucursal> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdSubSucursal",intIdSubSucursal);
			lista = dao.getListaPorIdSubSucursal(mapa);
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
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<Subsucursal> getListaSubSucursalPorPkEmpresaUsuario(EmpresaUsuarioId pId) throws BusinessException{
		List<Subsucursal> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdEmpresa", 	pId.getIntPersEmpresaPk());
			mapa.put("intIdPersona", 	pId.getIntPersPersonaPk());
			lista = dao.getListaSubSucursalPorPK(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Subsucursal> getListaSubSucursalPorPkEmpresaUsuarioYIdSucursalYEstado(EmpresaUsuarioId pId,Integer intIdSucursal,Integer intEstado) throws BusinessException{
		List<Subsucursal> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa",pId.getIntPersEmpresaPk());
			mapa.put("intIdPersona",pId.getIntPersPersonaPk());
			mapa.put("intIdSucursal",intIdSucursal);
			mapa.put("intEstado",intEstado);
			lista = dao.getListPorPkEmpresUsrYIdSucYSt(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Integer getCantidadSubSucursalPorPkSucursal(Integer pkSucursal) throws BusinessException{
		Integer escalar = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intIdSucursal", pkSucursal);
			escalar = dao.getCantidadSubSucursalPorPkSucursal(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return escalar;
	}
	
	
}
