package pe.com.tumi.contabilidad.impuesto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.impuesto.dao.ImpuestoDao;
import pe.com.tumi.contabilidad.impuesto.dao.impl.ImpuestoDaoIbatis;
import pe.com.tumi.contabilidad.impuesto.domain.Impuesto;
import pe.com.tumi.contabilidad.impuesto.domain.ImpuestoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class ImpuestoBO {

	private ImpuestoDao dao = (ImpuestoDao)TumiFactory.get(ImpuestoDaoIbatis.class);
	
	public Impuesto grabarImpuesto(Impuesto o) throws BusinessException{
		Impuesto dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
	public Impuesto modificarImpuesto(Impuesto o) throws BusinessException{
		Impuesto dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
	
	public List<Impuesto> getListaPersonaJuridica(Impuesto o) throws BusinessException{
		Impuesto domain = null;
		List<Impuesto> lista = null;
		
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntPersPersona", o.getIntPersPersona());
			mapa.put("pStrPersRuc", o.getStrPersRuc());
			lista = dao.getListaPersonaJuridica(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}
	public List<Impuesto> getListaNombreDniRol(Impuesto o) throws BusinessException{
		Impuesto domain = null;
		List<Impuesto> lista = null;
		
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntBusqTipo", o.getIntImpuPeriodo());
			mapa.put("pstrFiltroBusq", o.getStrNombreCompleto());
			lista = dao.getListaNombreDniRol(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//Autor: jchavez / Tarea: Modificación / Fecha: 12.08.2014 / 
	public List<Impuesto> getBuscar(Impuesto o) throws BusinessException{
		List<Impuesto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();

			mapa.put("pIntTipoImpuesto", o.getIntTipoImpuestoCod());
			mapa.put("pIntImpuPeriodo", o.getIntImpuPeriodo());
			mapa.put("pIntParaEstadoPagoCod", o.getIntParaEstadoPagoCod());
			mapa.put("pIntEstadoCod", o.getIntParaEstadoCod());
			lista = dao.getBuscar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Impuesto> getListaImpuesto(Impuesto o) throws BusinessException{
		List<Impuesto> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntTipoImpuesto", o.getIntTipoImpuestoCod());
			lista = dao.getListaImpuesto(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//Autor: jchavez / Tarea: Creación / Fecha: 18.08.2014 / 
	public Impuesto getListaPorPk(ImpuestoId pId) throws BusinessException{
		Impuesto domain = null;
		List<Impuesto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intItemImpuesto", pId.getIntItemImpuesto());
			
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
}
