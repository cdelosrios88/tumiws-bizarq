package pe.com.tumi.seguridad.login.bo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.dao.EmpresaUsuarioDao;
import pe.com.tumi.seguridad.login.dao.impl.EmpresaUsuarioDaoIbatis;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;

public class EmpresaUsuarioBO {
	
	private EmpresaUsuarioDao dao = (EmpresaUsuarioDao)TumiFactory.get(EmpresaUsuarioDaoIbatis.class);
	
	public EmpresaUsuario grabarEmpresaUsuario(EmpresaUsuario o) throws BusinessException {
		EmpresaUsuario dto = null;
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
	
	public EmpresaUsuario modificarEmpresaUsuario(EmpresaUsuario o) throws BusinessException{
		EmpresaUsuario dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EmpresaUsuario getEmpresaUsuarioPorPk(EmpresaUsuarioId pPk) throws BusinessException{
		List<EmpresaUsuario> lista = null;
		EmpresaUsuario domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pPk.getIntPersEmpresaPk());
			mapa.put("intPersPersonaPk", pPk.getIntPersPersonaPk());
			lista = dao.getListaEmpresaUsuarioPorPk(mapa);
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
	
	public List<EmpresaUsuario> getListaEmpresaUsuarioPorIdEmpresa(Integer pPk) throws BusinessException{
		List<EmpresaUsuario> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pPk);
			lista = dao.getListaEmpresaUsuarioPorIdEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EmpresaUsuario> getListaEmpresaUsuarioPorIdPersona(Integer pPk) throws BusinessException{
		List<EmpresaUsuario> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersPersonaPk", pPk);
			lista = dao.getListaPorIdPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EmpresaUsuario> getListaEmpresaUsuarioPorIdPersonaYFechaEliminacion(Integer pPk,Timestamp pTsFechaEliminacion) throws BusinessException{
		List<EmpresaUsuario> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersPersonaPk", pPk);
			mapa.put("tsFechaEliminacion", pTsFechaEliminacion);
			lista = dao.getListaPorIdPersonaYFechaEliminacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
