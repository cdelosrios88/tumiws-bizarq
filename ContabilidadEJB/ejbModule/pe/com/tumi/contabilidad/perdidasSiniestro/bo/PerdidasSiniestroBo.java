package pe.com.tumi.contabilidad.perdidasSiniestro.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.perdidasSiniestro.dao.PerdidasSiniestroDao;
import pe.com.tumi.contabilidad.perdidasSiniestro.dao.impl.PerdidasSiniestroDaoIbatis;
import pe.com.tumi.contabilidad.perdidasSiniestro.domain.PerdidasSiniestro;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class PerdidasSiniestroBo {
	private PerdidasSiniestroDao dao = (PerdidasSiniestroDao)TumiFactory.get(PerdidasSiniestroDaoIbatis.class);
	
	public PerdidasSiniestro grabarPerdidas(PerdidasSiniestro o) throws BusinessException{
		PerdidasSiniestro dto = null;
		try{
		    dto = dao.grabarPerdidas(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
	public PerdidasSiniestro modificarPerdidasSiniestro(PerdidasSiniestro o) throws BusinessException{
		PerdidasSiniestro dto = null;
     	try{
		     dto = dao.modificarPerdidas(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
	
	public List<PerdidasSiniestro> getListaJuridicaRuc(PerdidasSiniestro o) throws BusinessException{
		PerdidasSiniestro domain = null;
		List<PerdidasSiniestro> lista = null;
		
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntBusqTipo", o.getIntCodigoBuscar());
			mapa.put("pstrFiltroBusq", o.getStrNombreBuscar());
			lista = dao.getListaJuridicaRuc(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PerdidasSiniestro> getListaBuscar(PerdidasSiniestro o) throws BusinessException{
		PerdidasSiniestro domain = null;
		List<PerdidasSiniestro> lista = null;
		if(o.getIntParaEstadoCod()==0){
			o.setIntParaEstadoCod(null);
		}if(o.getIntParaEstadoCobroCod()==0){
			o.setIntParaEstadoCobroCod(null);
		}
			
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntBusqTipo", o.getIntCodigoBuscar());
			mapa.put("pstrFiltroBusq", o.getStrNombreBuscar());
			mapa.put("piniFechaSiniestro", o.getDtSiniFechaSiniestro());
			mapa.put("pintParaEstadoCod", o.getIntParaEstadoCod());
			mapa.put("pintParaEstadoCobroCod", o.getIntParaEstadoCobroCod());
			lista = dao.getListaBuscar(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
