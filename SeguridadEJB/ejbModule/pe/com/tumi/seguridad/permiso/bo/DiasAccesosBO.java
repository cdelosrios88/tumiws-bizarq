package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.DiasAccesosDao;
import pe.com.tumi.seguridad.permiso.dao.DocumentoDao;
import pe.com.tumi.seguridad.permiso.dao.impl.DiasAccesosDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.impl.DocumentoDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesos;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosId;
import pe.com.tumi.seguridad.permiso.domain.Documento;
import pe.com.tumi.seguridad.permiso.domain.DocumentoId;

public class DiasAccesosBO {

	private DiasAccesosDao dao = (DiasAccesosDao)TumiFactory.get(DiasAccesosDaoIbatis.class);
	protected static Logger log = Logger.getLogger(DiasAccesosBO.class);	
	
	public DiasAccesos grabar(DiasAccesos o) throws BusinessException {
		DiasAccesos dto = null;
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
	
	public DiasAccesos modificar(DiasAccesos o) throws BusinessException{
		DiasAccesos dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public DiasAccesos getListaPorPk(DiasAccesosId pId) throws BusinessException{
		List<DiasAccesos> lista = null;
		DiasAccesos domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intIdTipoSucursal", pId.getIntIdTipoSucursal());
			mapa.put("intItemDiaAccesos", pId.getIntItemDiaAccesos());
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
	
	public List<DiasAccesos> getListaPorTipoSucursalYEstado(DiasAccesos o) throws BusinessException{
		List<DiasAccesos> lista = null;
		try{
			log.info("a buscar:"+o);
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresa", o.getId().getIntPersEmpresa());
			mapa.put("intIdTipoSucursal", o.getId().getIntIdTipoSucursal());
			mapa.put("intIdEstado",o.getIntIdEstado());
			lista = dao.getListaPorTipoSucursalYEstado(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<DiasAccesos> getAccesoPorEmpresaYSucursal(DiasAccesos o) throws BusinessException{
		
		List<DiasAccesos> lista = null;
		try{
			log.info("validarAccesoPorEmpresaYSucursal:" + o);
			
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresa", o.getId().getIntPersEmpresa());
			mapa.put("intIdTipoSucursal", o.getId().getIntIdTipoSucursal());
			mapa.put("intIdEstado",o.getIntIdEstado());
			
			lista = dao.getAccesoPorEmpresaYSucursal(mapa);			
			
		}catch(DAOException e){
			throw new BusinessException(e);
			
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}
}