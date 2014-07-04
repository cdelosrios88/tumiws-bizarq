package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.PoblacionDetalleDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.PoblacionDetalleDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.Poblacion;
import pe.com.tumi.credito.socio.convenio.domain.PoblacionDetalle;
import pe.com.tumi.credito.socio.convenio.domain.PoblacionDetalleId;
import pe.com.tumi.credito.socio.convenio.domain.PoblacionId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class PoblacionDetalleBO {
	
	private PoblacionDetalleDao dao = (PoblacionDetalleDao)TumiFactory.get(PoblacionDetalleDaoIbatis.class);
	
	public PoblacionDetalle grabarPoblacionDetalle(PoblacionDetalle o) throws BusinessException{
		PoblacionDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PoblacionDetalle modificarPoblacionDetalle(PoblacionDetalle o) throws BusinessException{
		PoblacionDetalle dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PoblacionDetalle getPoblacionDetallePorPK(PoblacionDetalleId pPK) throws BusinessException{
		PoblacionDetalle domain = null;
		List<PoblacionDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemPoblacion", 	pPK.getIntItemPoblacion());
			mapa.put("intTipoTrabajador", 	pPK.getIntTipoTrabajador());
			mapa.put("intTipoSocio", 		pPK.getIntTipoSocio());
			lista = dao.getListaPoblacionDetallePorPK(mapa);
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
	
	public List<PoblacionDetalle> getListaPoblacionDetallePorPKPoblacion(PoblacionId pPK) throws BusinessException{
		List<PoblacionDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemPoblacion", 		pPK.getIntItemPoblacion());
			
			lista = dao.getListaPoblacionDetallePorPKPoblacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
