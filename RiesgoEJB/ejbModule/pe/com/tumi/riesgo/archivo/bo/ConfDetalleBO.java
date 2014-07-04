package pe.com.tumi.riesgo.archivo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.archivo.dao.ConfDetalleDao;
import pe.com.tumi.riesgo.archivo.dao.impl.ConfDetalleDaoIbatis;
import pe.com.tumi.riesgo.archivo.domain.ConfDetalle;
import pe.com.tumi.riesgo.archivo.domain.ConfDetalleId;

public class ConfDetalleBO {
	
	private ConfDetalleDao dao = (ConfDetalleDao)TumiFactory.get(ConfDetalleDaoIbatis.class);
	
	public ConfDetalle grabar(ConfDetalle o) throws BusinessException{
		ConfDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfDetalle modificar(ConfDetalle o) throws BusinessException{
		ConfDetalle dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfDetalle getPorPk(ConfDetalleId pId) throws BusinessException{
		ConfDetalle domain = null;
		List<ConfDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemConfiguracion", pId.getIntItemConfiguracion());
			mapa.put("intItemConfiguracionDetalle", pId.getIntItemConfiguracionDetalle());
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
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<ConfDetalle> getPorIntItemConfiguracion(Integer intItemConfiguracion) throws BusinessException{
		List<ConfDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemConfiguracion", intItemConfiguracion);
			lista = dao.getListaPorIntItemConfiguracion(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);		
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
