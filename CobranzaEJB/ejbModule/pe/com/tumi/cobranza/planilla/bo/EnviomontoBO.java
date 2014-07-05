package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;


import pe.com.tumi.cobranza.planilla.dao.EnviomontoDao;
import pe.com.tumi.cobranza.planilla.dao.impl.EnviomontoDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.EnviomontoId;
import pe.com.tumi.cobranza.planilla.domain.EnvioresumenId;


public class EnviomontoBO{
	
	private EnviomontoDao dao = (EnviomontoDao)TumiFactory.get(EnviomontoDaoIbatis.class);

	public Enviomonto grabarEnviomonto(Enviomonto o) throws BusinessException{
		Enviomonto dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public Enviomonto modificarEnviomonto(Enviomonto o) throws BusinessException{
     	Enviomonto dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  	
  	
	public Enviomonto getEnviomontoPorPk(EnviomontoId pId) throws BusinessException{
		Enviomonto domain = null;
		List<Enviomonto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemenvioconcepto", pId.getIntItemenvioconcepto());
			mapa.put("intItemenviomonto", pId.getIntItemenviomonto());
			mapa.put("intEmpresacuentaPk", pId.getIntEmpresacuentaPk());
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
	
	
	
	public List<Enviomonto> getListaEnviomontoDeBuscar(SucursalId pIdSucursal,Integer pIntTiposocioCod,Integer pIntModalidadCod,Integer pIntPeriodoplanilla,Integer pIntEstadoCod) throws BusinessException{
		
		
		List<Enviomonto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresasucprocesaPk", pIdSucursal.getIntPersEmpresaPk());
			mapa.put("intIdsucursalprocesaPk", pIdSucursal.getIntIdSucursal());
			mapa.put("intTiposocioCod", pIntTiposocioCod);
			mapa.put("intModalidadCod", pIntModalidadCod);
			mapa.put("intPeriodoplanilla", pIntPeriodoplanilla);
			mapa.put("intEstadoCod", pIntEstadoCod);
			lista = dao.getListaDeBuscar(mapa);	
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Enviomonto> getListaEnviomonto(SucursalId pIdSucursal,Integer pIntTiposocioCod,Integer pIntModalidadCod,Integer pIntEstadoCod) throws BusinessException{
		
		List<Enviomonto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresasucprocesaPk", pIdSucursal.getIntPersEmpresaPk());
			mapa.put("intIdsucursalprocesaPk", pIdSucursal.getIntIdSucursal());
			mapa.put("intTiposocioCod", pIntTiposocioCod);
			mapa.put("intModalidadCod", pIntModalidadCod);			
			mapa.put("intEstadoCod", pIntEstadoCod);
			lista = dao.getLista(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/** CREADO 06-08-2013 **/
	public List<Enviomonto> getListaPorEnvioConcepto(Envioconcepto envioConcepto) throws BusinessException{
		
		List<Enviomonto> lista = null;
		try{
	     	HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemenvioconcepto", envioConcepto.getId().getIntItemenvioconcepto());
			mapa.put("intEmpresacuentaPk", envioConcepto.getId().getIntEmpresacuentaPk());
			lista = dao.getListaPorEnvioConcepto(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	public List<Enviomonto> getListaEnvioMontoPlanillaEfectuada(Enviomonto o, Integer intMaxEnviado) throws BusinessException
	{
		List<Enviomonto> lista = null;
		try
		{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresa", o.getId().getIntEmpresacuentaPk());
			mapa.put("intNivel", o.getIntNivel());
			mapa.put("intCodigo", o.getIntCodigo());
			mapa.put("intTipoSocio", o.getIntTiposocioCod());
			mapa.put("intModalidad", o.getIntModalidadCod());
			mapa.put("intSucursal",o.getIntIdsucursaladministraPk());
			mapa.put("intSubSucursal",o.getIntIdsubsucursaladministra());
			mapa.put("intMaxPeriodoEnviado", intMaxEnviado);
			lista = dao.getListaEnvioMontoPlanillaEfectuada(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Enviomonto> getListaItemConcepto(Enviomonto o, Integer intMaxEnviado) throws BusinessException
	{
		List<Enviomonto> lista = null;
		try
		{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresa", o.getId().getIntEmpresacuentaPk());
			mapa.put("intNivel", o.getIntNivel());
			mapa.put("intCodigo", o.getIntCodigo());
			mapa.put("intTipoSocio", o.getIntTiposocioCod());			
			mapa.put("intMaxPeriodoEnviado", intMaxEnviado);
			mapa.put("intsucursalAdministra", o.getIntIdsucursaladministraPk());
			mapa.put("intSubsucursalAdministra",o.getIntIdsubsucursaladministra());
			mapa.put("intModalidad", o.getIntModalidadCod());
			lista = dao.getListaItemConcepto(mapa);
		}
		catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * para exportar a .txt traerme el listado de enviomontos con el itemenvioresuemn
	 */
	public List<Enviomonto> getListaEnviomontoXItemEnvioresumen(EnvioresumenId o) throws BusinessException
	{
		List<Enviomonto> lista = null;
		try
		{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intItemEnvioresumen", o.getIntItemenvioresumen());			
			
			lista = dao.getListaEnviomontoXItemEnvioresumen(mapa);
		}
		catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera envio monto en base a : 
	 * intEmpresacuentaPk, intItemenvioconcepto, intTiposocioCod, 
	 * intModalidadCod, intNivel, intCodigo y intTipoestructuraCod.
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<Enviomonto> getListaXItemEnvioCtoGral(Enviomonto o) throws BusinessException
	{
		List<Enviomonto> lista = null;
		try
		{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresacuentaPk", o.getId().getIntEmpresacuentaPk());
			mapa.put("intItemenvioconcepto", o.getId().getIntItemenvioconcepto());
			mapa.put("intTiposocioCod", o.getIntTiposocioCod());
			mapa.put("intModalidadCod", o.getIntModalidadCod());
			mapa.put("intNivel", o.getIntNivel());
			mapa.put("intCodigo", o.getIntCodigo());
			mapa.put("intTipoestructuraCod", o.getIntTipoestructuraCod());
			lista = dao.getListaXItemEnvioCtoGral(mapa);
		}
		catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * Empresa, periodo, cuenta, tiposocio, modalidad, nivel, codigo
	 * para la parte de efectuadoconArchivo
	 * @return
	 */
	public List<Enviomonto> getEnviomontoPorInt(Integer empresa,
												Integer periodo,
												Integer cuenta,
												Integer tipoSocio,
												Integer modalidad,
												Integer nivelUE,
												Integer codigoUE)
												throws BusinessException {
		
		List<Enviomonto> lista = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresa", empresa);
			mapa.put("intPeriodo", periodo);
			mapa.put("intCuenta", cuenta);
			mapa.put("intTipoSocio", tipoSocio);
			mapa.put("intModalidad", modalidad);
			mapa.put("intNivelUE", nivelUE);
			mapa.put("intCodigoUE", codigoUE);

			lista = dao.getEnviomontoPorInt(mapa);
			
		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}


}
