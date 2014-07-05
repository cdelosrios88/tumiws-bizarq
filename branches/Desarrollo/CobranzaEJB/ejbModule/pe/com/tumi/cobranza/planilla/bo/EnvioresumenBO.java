package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;


import pe.com.tumi.cobranza.planilla.dao.EnvioresumenDao;
import pe.com.tumi.cobranza.planilla.dao.impl.EnvioresumenDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;
import pe.com.tumi.cobranza.planilla.domain.EnvioresumenId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;

public class EnvioresumenBO{
	
	protected static Logger log = Logger.getLogger(EnvioresumenBO.class);
	private EnvioresumenDao dao = (EnvioresumenDao)TumiFactory.get(EnvioresumenDaoIbatis.class);

	public Envioresumen grabarEnvioresumen(Envioresumen o) throws BusinessException{
		Envioresumen dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Envioresumen modificarEnvioresumen(Envioresumen o) throws BusinessException{
     	Envioresumen dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Envioresumen getEnvioresumenPorPk(EnvioresumenId pId) throws BusinessException{
		Envioresumen domain = null;
		List<Envioresumen> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemenvioresumen", pId.getIntItemenvioresumen());
			mapa.put("intEmpresaPk", pId.getIntEmpresaPk());
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
	
	public List<Envioresumen> getListaPorEnitdadyPeriodo(Integer pIntEmpresaPk,
			                                       Integer pIntPeriodoplanilla,
			                                       Integer pIntTiposocioCod,
			                                       Integer pIntModalidadCod,
			                                       Integer pIntNivel,
			                                       Integer pIntCodigo) throws BusinessException{
		Envioresumen domain = null;
		List<Envioresumen> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPk",pIntEmpresaPk);
			mapa.put("intPeriodoplanilla", pIntPeriodoplanilla);
			mapa.put("intTiposocioCod", pIntTiposocioCod);
			mapa.put("intModalidadCod", pIntModalidadCod);
			mapa.put("intNivel", pIntNivel);
			mapa.put("intCodigo", pIntCodigo);
			
			lista = dao.getListaPorEnitdadyPeriodo(mapa);
		
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Envioresumen> getListaEnvioResumen(Integer pIntEmpresaPk,
					Integer pIntPeriodoplanilla, Integer pIntTiposocioCod,
					Integer pIntModalidadCod, Integer pintIdsucursalprocesaPk, Integer pintEstado)
					throws BusinessException {
		
		List<Envioresumen> lista = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresaPk", pIntEmpresaPk);
			mapa.put("intPeriodoplanilla", pIntPeriodoplanilla);
			mapa.put("intTiposocioCod", pIntTiposocioCod);
			mapa.put("intModalidadCod", pIntModalidadCod);
			mapa.put("intIdsucursalprocesaPk", pintIdsucursalprocesaPk);
			mapa.put("intEstado", pintEstado);
			lista = dao.getListaEnvioResumen(mapa);

		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	
	public List<Envioresumen> getListaSucursal(Integer pIntEmpresaPk,
			Integer pIntTiposocioCod, Integer pIntModalidadCod)			
			throws BusinessException {
		log.info("envioresumenbo.getListaSucursal");
		List<Envioresumen> lista = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresaPk", pIntEmpresaPk);			
			mapa.put("intTiposocioCod", pIntTiposocioCod);
			mapa.put("intModalidadCod", pIntModalidadCod);			

			lista = dao.getListaSucursal(mapa);

		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Envioresumen> getListaEnvioResumenUE(Integer pIntEmpresaPk,
			Integer pIntTiposocioCod, Integer pIntModalidadCod, Integer pIntSucursalProcesa)			
			throws BusinessException {
		log.info("envioresumenbo.getListaSucursal");
		List<Envioresumen> lista = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresaPk", pIntEmpresaPk);			
			mapa.put("intTiposocioCod", pIntTiposocioCod);
			mapa.put("intModalidadCod", pIntModalidadCod);			
			mapa.put("intSucursalCod", pIntSucursalProcesa);		
			lista = dao.getListaEnvioResumenUE(mapa);

		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocio(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intNivel", pk.getIntNivel());
			mapa.put("intCodigo", pk.getIntCodigo());
			mapa.put("intTiposocioCod", intTipoSocio);
			
			intEscalar = dao.getMaxPeriodoPorEmpresaYEstructuraYTipoSocio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	
	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioM(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio, Integer intModalidad) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intNivel", pk.getIntNivel());
			mapa.put("intCodigo", pk.getIntCodigo());
			mapa.put("intTiposocioCod", intTipoSocio);
			mapa.put("intModalidad", intModalidad);
			intEscalar = dao.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioM(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	
	
	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioCAS(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intNivel", pk.getIntNivel());
			mapa.put("intCodigo", pk.getIntCodigo());
			mapa.put("intTiposocioCod", intTipoSocio);			
			intEscalar = dao.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioCAS(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	public List<Envioresumen> getListaEnvioREfectuadoConArchivo(Integer pIntEmpresa, Integer pIntNivel,
																Integer pIntCodigo, Integer pIntTipoSocio,
																Integer pIntModalidad) throws BusinessException
																
	{
		List<Envioresumen> lista = null;
		try
		{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresa", pIntEmpresa);
			mapa.put("intNivel",pIntNivel);
			mapa.put("intCodigo",pIntCodigo);
			mapa.put("intTipoSocio",pIntTipoSocio);
			mapa.put("intModalidad",pIntModalidad);
			lista = dao.getListaEnvioREfectuadoConArchivo(mapa);
		}catch(DAOException e)
		{
			throw new BusinessException(e);
		}catch(Exception e)
		{
			throw new BusinessException(e);
		}
		return lista;
	}
	

	public List<Envioresumen> getListEnvRes(Integer pIntEmpresaPk,
								            Integer pIntPeriodoplanilla,
								            Integer pIntTiposocioCod,
								            Integer pIntModalidadCod,
								            Integer pIntNivel,
								            Integer pIntCodigo) throws BusinessException
																
	{
		List<Envioresumen> lista = null;
		try
		{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaPk",pIntEmpresaPk);
			mapa.put("intPeriodoplanilla", pIntPeriodoplanilla);
			mapa.put("intTiposocioCod", pIntTiposocioCod);
			mapa.put("intModalidadCod", pIntModalidadCod);
			mapa.put("intNivel", pIntNivel);
			mapa.put("intCodigo", pIntCodigo);
			lista = dao.getListEnvRes(mapa);
		}catch(DAOException e)
		{
			throw new BusinessException(e);
		}catch(Exception e)
		{
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
}
