package pe.com.tumi.contabilidad.reclamosDevoluciones.bo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.contabilidad.reclamosDevoluciones.dao.ReclamosDevolucionesDao;
import pe.com.tumi.contabilidad.reclamosDevoluciones.dao.impl.ReclamosDevolucionesDaoIbatis;
import pe.com.tumi.contabilidad.reclamosDevoluciones.domain.ReclamosDevoluciones;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 08.08.2014 /
public class ReclamosDevolucionesBo {
	private ReclamosDevolucionesDao dao = (ReclamosDevolucionesDao)TumiFactory.get(ReclamosDevolucionesDaoIbatis.class);
	
	public ReclamosDevoluciones grabarReclamaciones(ReclamosDevoluciones o) throws BusinessException{
		ReclamosDevoluciones dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
	public ReclamosDevoluciones modificarReclamos(ReclamosDevoluciones o) throws BusinessException{
		ReclamosDevoluciones dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
	public List<ReclamosDevoluciones> getListaParametro(ReclamosDevoluciones o) throws BusinessException{
		List<ReclamosDevoluciones> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntEstado", o.getIntEstado());
			lista = dao.getListaParametro(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	public List<ReclamosDevoluciones> getBuscar(ReclamosDevoluciones o) throws BusinessException{
		List<ReclamosDevoluciones> lista = null;
		BigDecimal bdMontoSaldo = BigDecimal.ZERO;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntDocumentoGeneral", o.getIntParaDocumentoGeneral());
			mapa.put("pIntReclamoDev", o.getIntReclamoDevolucion());
			mapa.put("ptsFechaRegistro", o.getTsRedeFechaRegistro());
			mapa.put("pIntEstadoCod", o.getIntParaEstadoCod());
			mapa.put("pIntEstadoCobro", o.getIntParaEstadoCobroCod());
			mapa.put("pIntEstadoPAgo", o.getIntParaEstadoPago());
			lista = dao.getBuscar(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
