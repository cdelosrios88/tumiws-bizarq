package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.AccesoEspecialDao;
import pe.com.tumi.seguridad.permiso.dao.ComputadoraDao;
import pe.com.tumi.seguridad.permiso.dao.impl.AccesoEspecialDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.impl.ComputadoraDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecial;
import pe.com.tumi.seguridad.permiso.domain.Computadora;
import pe.com.tumi.seguridad.permiso.domain.ComputadoraId;


public class AccesoEspecialBO {

	private AccesoEspecialDao dao = (AccesoEspecialDao)TumiFactory.get(AccesoEspecialDaoIbatis.class);
	protected static Logger log = Logger.getLogger(AccesoEspecialBO.class);
	
	public AccesoEspecial grabar(AccesoEspecial o) throws BusinessException {
		AccesoEspecial dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AccesoEspecial modificar(AccesoEspecial o) throws BusinessException{
		AccesoEspecial dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AccesoEspecial getPorPk(Integer pId) throws BusinessException{
		List<AccesoEspecial> lista = null;
		AccesoEspecial domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intItemAcceso", pId);
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

	public List<AccesoEspecial> getPorBusqueda(AccesoEspecial o) throws BusinessException{
		List<AccesoEspecial> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intParaTipoAcceso", o.getIntParaTipoAcceso());
			mapa.put("intPersEmpresa", o.getIntPersEmpresa());
			mapa.put("intIdEstado", o.getIntIdEstado());
			mapa.put("intParaTipoMotivo", o.getIntParaTipoMotivo());
			mapa.put("intPersPersonaAutoriza", o.getIntPersPersonaAutoriza());
			mapa.put("intPersPersonaOpera", o.getIntPersPersonaOpera());
			mapa.put("tsFechaInicio", o.getTsFechaInicio());
			mapa.put("tsFechaFin", o.getTsFechaFin());
			lista = dao.getListaPorBusqueda(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<AccesoEspecial> getAccesoPorEmpresaUsuario(AccesoEspecial o) throws BusinessException{
		
		List<AccesoEspecial> lista = null;
		try{
			log.info("getAccesoPorEmpresaUsuario:" + o);
			
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresa", o.getIntPersEmpresa());
			mapa.put("intPersPersonaOpera", o.getIntPersPersonaOpera());
			mapa.put("intIdEstado",o.getIntIdEstado());
			
			lista = dao.getAccesoPorEmpresaUsuario(mapa);			
			
		}catch(DAOException e){
			throw new BusinessException(e);
			
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}
}
