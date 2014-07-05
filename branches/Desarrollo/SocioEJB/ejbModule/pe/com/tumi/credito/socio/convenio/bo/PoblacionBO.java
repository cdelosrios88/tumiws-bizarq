package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.PoblacionDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.PoblacionDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Poblacion;
import pe.com.tumi.credito.socio.convenio.domain.PoblacionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.Finalidad;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class PoblacionBO {
	
	private PoblacionDao dao = (PoblacionDao)TumiFactory.get(PoblacionDaoIbatis.class);
	
	public Poblacion grabarPoblacion(Poblacion o) throws BusinessException{
		Poblacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Poblacion modificarPoblacion(Poblacion o) throws BusinessException{
		Poblacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Poblacion getPoblacionPorPK(PoblacionId pPK) throws BusinessException{
		Poblacion domain = null;
		List<Poblacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemPoblacion", 	pPK.getIntItemPoblacion());
			lista = dao.getListaPoblacionPorPK(mapa);
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
	
	public List<Poblacion> getListaPoblacionPorPKConvenio(AdendaId pPK) throws BusinessException{
		List<Poblacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intConvenio", 		pPK.getIntConvenio());
			mapa.put("intItemConvenio", 	pPK.getIntItemConvenio());
			
			lista = dao.getListaPoblacionPorPKConvenio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}