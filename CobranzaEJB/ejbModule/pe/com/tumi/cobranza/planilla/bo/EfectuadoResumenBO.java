package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

import pe.com.tumi.cobranza.planilla.dao.EfectuadoResumenDao;
import pe.com.tumi.cobranza.planilla.dao.impl.EfectuadoResumenDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumenId;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;

public class EfectuadoResumenBO{

	private EfectuadoResumenDao dao = (EfectuadoResumenDao)TumiFactory.get(EfectuadoResumenDaoIbatis.class);

	public EfectuadoResumen grabar(EfectuadoResumen o) throws BusinessException{
		EfectuadoResumen dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

  	public EfectuadoResumen modificar(EfectuadoResumen o) throws BusinessException{
  		EfectuadoResumen dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public EfectuadoResumen getPorPk(EfectuadoResumenId efectuadoResumenId) throws BusinessException{
		EfectuadoResumen domain = null;
		List<EfectuadoResumen> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", efectuadoResumenId.getIntEmpresa());
			mapa.put("intItemEfectuadoResumen", efectuadoResumenId.getIntItemEfectuadoResumen());
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
	
	public List<EfectuadoResumen> getListaFaltaCancelar(Integer intIdEmpresa) throws BusinessException{
		List<EfectuadoResumen> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", intIdEmpresa);
			lista = dao.getListaFaltaCancelar(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	public List<EfectuadoResumen> getListaPorEntidadyPeriodo(Integer pIntEmpresaPk, Integer pIntPeriodoplanilla, 
															 Integer pIntTiposocioCod, Integer pIntModalidadCod,
															 Integer pIntNivel, Integer pIntCodigo) throws BusinessException{
		List<EfectuadoResumen> lista = null;
		EfectuadoResumen domain      = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresaPk", pIntEmpresaPk);
			mapa.put("intPeriodoplanilla", pIntPeriodoplanilla);
			mapa.put("intTiposocioCod", pIntTiposocioCod);
			mapa.put("intModalidadCod", pIntModalidadCod);
			mapa.put("intNivel", pIntNivel);
			mapa.put("intCodigo", pIntCodigo);
			lista = dao.getListaPorEntidadyPeriodo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<EfectuadoResumen> getListaEfectuadoResumen(
			Integer pIntEmpresaPk, Integer pIntPeriodoplanilla,
			Integer pIntTiposocioCod, Integer pIntModalidadCod,
			Integer pintIdsucursalprocesaPk, Integer pintEstado) throws BusinessException {

		List<EfectuadoResumen> lista = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresaPk", pIntEmpresaPk);
			mapa.put("intPeriodoplanilla", pIntPeriodoplanilla);
			mapa.put("intTiposocioCod", pIntTiposocioCod);
			mapa.put("intModalidadCod", pIntModalidadCod);
			mapa.put("intIdsucursalprocesaPk", pintIdsucursalprocesaPk);
			mapa.put("intEstado", pintEstado);
			lista = dao.getListaEfectuadoResumen(mapa);

		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	

	public String getNumeroCuenta(Integer intEmpresa,
								   Integer intItemEfectuadoResumen,
								   Integer intPeriodo,
								   Integer intParaTipoConcepto)throws BusinessException
	{
		String strEscalar = null;
		try
		{	
			HashMap<String,Object> mapa = new HashMap<String, Object>();			
			mapa.put("intEmpresa", intEmpresa);
			mapa.put("intItemEfectuadoResumen", intItemEfectuadoResumen);
			mapa.put("intPeriodo", intPeriodo);
			mapa.put("intParaTipoConcepto", intParaTipoConcepto);
			strEscalar = dao.getNumeroCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return strEscalar;
	}
	

	  
	public EfectuadoResumen getMaximoPeriodo(Integer empresa,
											Integer nivel,
											Integer codigo,
											Integer tipoSocio,
											Integer modalidad) throws BusinessException{
		EfectuadoResumen domain = null;
		List<EfectuadoResumen> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("empresa", empresa);
			mapa.put("nivel", nivel);
			mapa.put("codigo", codigo);
			mapa.put("tipoSocio", tipoSocio);
			mapa.put("modalidad", modalidad);
			lista = dao.getMaximoPeriodo(mapa);
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
	/**
	 * jchavez 19.06.2014
	 * Procedimiento que retorna lista de efectuados pendientes de pago, mostrando los montos totales despues
	 * de agruparlos por estructura detalle y sucursal procesa. Usado en Caja - Ingreso (planilla efectuada)
	 * @param pIntEmpresaPk
	 * @param pIntTiposocioCod
	 * @param pIntModalidadCod
	 * @param pIntNivel
	 * @param pIntCodigo
	 * @return
	 * @throws BusinessException
	 */
	public List<EfectuadoResumen> getLstPendientesPorEnitdad(Integer pIntEmpresaPk, Integer pIntTiposocioCod, Integer pIntModalidadCod, Integer pIntNivel, Integer pIntCodigo) throws BusinessException{
		List<EfectuadoResumen> lista = null;

		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresaPk", pIntEmpresaPk);
			mapa.put("intTiposocioCod", pIntTiposocioCod);
			mapa.put("intModalidadCod", pIntModalidadCod);
			mapa.put("intNivel", pIntNivel);
			mapa.put("intCodigo", pIntCodigo);
			lista = dao.getLstPendientesPorEnitdad(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

}