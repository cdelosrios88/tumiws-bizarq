package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;

import pe.com.tumi.cobranza.planilla.dao.EnvioconceptoDao;
import pe.com.tumi.cobranza.planilla.dao.impl.EnvioconceptoDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.EnvioconceptoId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;


public class EnvioconceptoBO{

	private EnvioconceptoDao dao = (EnvioconceptoDao)TumiFactory.get(EnvioconceptoDaoIbatis.class);

	public Envioconcepto grabarEnvioconcepto(Envioconcepto o) throws BusinessException{
		Envioconcepto dto = null;
		try{
		    dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}

	public Envioconcepto grabarSubEnvioconcepto(Envioconcepto o) throws BusinessException{
		Envioconcepto dto = null;
		try{
		    dto = dao.grabarSub(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
  	public Envioconcepto modificarEnvioconcepto(Envioconcepto o) throws BusinessException{
     	Envioconcepto dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }
  
	public Envioconcepto getEnvioconceptoPorPk(EnvioconceptoId pId) throws BusinessException{
		Envioconcepto domain = null;
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemenvioconcepto", pId.getIntItemenvioconcepto());
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
	
	public Envioconcepto getEnvioconceptoPorPkMaxPeriodo(EnvioconceptoId pPK) throws BusinessException{
		Envioconcepto domain = null;
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", 		pPK.getIntEmpresacuentaPk());
			mapa.put("intItemenvioconcepto", 	pPK.getIntItemenvioconcepto());
			mapa.put("intItemenvioconceptoDet", pPK.getIntItemenvioconceptoDet());
			lista = dao.getListaPorMaxPeriodo(mapa);
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
	
	public String getCsvCuentaPorEmpresaYTipoSocioYModalidadYPeriodo(Integer intEmpresa,Integer intTipoSocio,Integer intModalidad,Integer intPeriodo) throws BusinessException{
		String strEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intTiposocioCod", intTipoSocio);
			mapa.put("intModalidadCod", intModalidad);
			mapa.put("intPeriodoplanilla", intPeriodo);
			strEscalar = dao.getCsvCuentaPorEmpresaYTipoSocioYModalidadYPeriodo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return strEscalar;
	}
	
	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioYModalidad(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intNivel", pk.getIntNivel());
			mapa.put("intCodigo", pk.getIntCodigo());
			mapa.put("intTiposocioCod", intTipoSocio);
			mapa.put("intModalidadCod", intModalidad);
			intEscalar = dao.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidad(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
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
	
	public Integer getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad, Integer intTipoEstructura) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intNivel", pk.getIntNivel());
			mapa.put("intCodigo", pk.getIntCodigo());
			mapa.put("intTiposocioCod", intTipoSocio);
			mapa.put("intModalidadCod", intModalidad);
			mapa.put("intTipoEstructura", intTipoEstructura);
			
			intEscalar = dao.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidad(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	
	public List<Envioconcepto> getListaEnvioconceptoPorEmprPeriodoNroCta(Integer intEmpresa, Integer intPeriodo,
							   Integer nroCta) throws BusinessException{
		
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intPeriodoplanilla", intPeriodo); 
			mapa.put("intCuentaPk", nroCta);		
			lista = dao.getListaPorEmpresaPeriodoYNroCta(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}
	
	public List<Envioconcepto> getListaEnvioconceptoPorEmprPeriodoNroCtaNivelCodigo(
			Integer intEmpresa, Integer intPeriodo, Integer nroCta,
			Integer intNivel, Integer intCodigo) throws BusinessException {

		List<Envioconcepto> lista = null;
		try {
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intPeriodoplanilla", intPeriodo);
			mapa.put("intCuentaPk", nroCta);
			mapa.put("intNivel", intNivel);
			mapa.put("intCodigo", intCodigo);
			lista = dao.getListaPorEmpresaPeriodoYNroCtaNivelCodigo(mapa);

		} catch (DAOException e) {
			throw new BusinessException(e);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

		return lista;
	}
	
	/**
	 * 
	 * @param intEmpresa
	 * @param nroCta
	 * @return
	 * @throws BusinessException
	 */
	public List<Envioconcepto> getListaEnvioconceptoPorEmprNroCta(Integer intEmpresa, Integer nroCta) throws BusinessException{
		
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);			
			mapa.put("intCuentaPk", nroCta);
			
			lista = dao.getListaPorEmpresaNroCta(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}
	
	public List<Envioconcepto> getListaXPerCtaItemCto(Integer intPeriodo, Integer nroCta, Integer intItemenvioconcepto, Integer intEstado ) throws BusinessException{
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPeriodoplanilla", intPeriodo);			
			mapa.put("intCuentaPk", nroCta);
			mapa.put("intItemenvioconcepto", intItemenvioconcepto);
			mapa.put("intParaEstadoCod", intEstado);
			
			lista = dao.getListaXPerCtaItemCto(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}
	
	public List<Envioconcepto> getEnvioconceptoPorItemEnvioConcepto(EnvioconceptoId pId) throws BusinessException{
		
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemenvioconcepto", pId.getIntItemenvioconcepto());
			mapa.put("intEmpresacuentaPk", pId.getIntEmpresacuentaPk());
			lista = dao.getEnvioconceptoPorItemEnvioConcepto(mapa);
				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/** CREADO 05/08/2013 **/
	public List<Envioconcepto> getListaEnvioconceptoPorPkExpedienteCredito(ExpedienteId pId) throws BusinessException{
		
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", pId.getIntPersEmpresaPk());			
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemexpediente", pId.getIntItemExpediente());
			mapa.put("intItemdetexpediente",pId.getIntItemExpedienteDetalle());
			lista = dao.getListaEnvioconceptoPorPkExpedienteCredito(mapa);
		
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}
	/** CREADO 08/08/2013 **/
	public List<Envioconcepto> getListaEnvioconceptoPorCtaCptoDetYPer(CuentaConceptoDetalleId pId, Integer intPeriodo) throws BusinessException{
		
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", pId.getIntPersEmpresaPk());			
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemcuentaconcepto", pId.getIntItemCuentaConcepto());
			mapa.put("intPeriodoplanilla", intPeriodo);
			lista = dao.getListaEnvioconceptoPorCtaCptoDetYPer(mapa);
		
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 09-09-2013 
	 * OBTENER LISTA ENVIOCONCEPTO POR CUENTA Y PERIODO
	 * @param intEmpresa
	 * @param intCuenta
	 * @param intPeriodo
	 * @return
	 * @throws BusinessException
	 */
	public List<Envioconcepto> getListaEnvioconceptoPorCtaYPer(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException{
		
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);			
			mapa.put("intCuentaPk", intCuenta);		
			mapa.put("intPeriodoplanilla", intPeriodo);
			lista = dao.getListaEnvioconceptoPorCtaYPer(mapa);
		
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 10-09-2013 
	 * OBTENER LISTA ENVIOCONCEPTO POR CUENTA, DEL PERIODO INGRESADO EN ADELANTE (DISTINCT POR PERIODO)
	 * @param intEmpresa
	 * @param intCuenta
	 * @param intPeriodo
	 * @return
	 * @throws BusinessException
	 */
	public List<Envioconcepto> getListaPorCuentaYPeriodo(Integer intEmpresa, Integer intCuenta, Integer intPeriodo) throws BusinessException{
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);			
			mapa.put("intCuentaPk", intCuenta);		
			mapa.put("intPeriodoplanilla", intPeriodo);
			lista = dao.getListaPorCuentaYPeriodo(mapa);
		
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}
	
	/**
	 * Recupera los 
	 * @param intEmpresa
	 * @param intPeriodo
	 * @param nroCta
	 * @param intEstado
	 * @return
	 * @throws BusinessException
	 */
	public Envioconcepto getEnvioConceptoPorEmpPerCta(Integer intEmpresa, Integer intPeriodo, Integer nroCta) throws BusinessException{
		List<Envioconcepto> lstEnvio = null;
		Envioconcepto envio = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intPeriodoplanilla", intPeriodo);
			mapa.put("intCuentaPk", nroCta);
			lstEnvio = dao.getListaPorEmpPerCta(mapa);
			if(lstEnvio != null && !lstEnvio.isEmpty()){
				envio = new Envioconcepto();
				envio = lstEnvio.get(0);
			}
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return envio;
	}
	
	public Integer getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioCAS(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad) throws BusinessException{
		Integer intEscalar = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);
			mapa.put("intNivel", pk.getIntNivel());
			mapa.put("intCodigo", pk.getIntCodigo());
			mapa.put("intTiposocioCod", intTipoSocio);
			mapa.put("intModalidadCod", intModalidad);
			intEscalar = dao.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioCAS(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return intEscalar;
	}
	
	
	/**
	 * Recupera lista de ultimo envio concepto en base a emprsa y cuenta
	 * @param intEmpresa
	 * @param intCuenta
	 * @return
	 * @throws BusinessException
	 */
	public List<Envioconcepto> getListaEnvioMinimoPorEmpCtaYEstado(Integer intEmpresa, Integer intCuenta)
	throws BusinessException{
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresacuentaPk", intEmpresa);			
			mapa.put("intCuentaPk", intCuenta);
			lista = dao.getListaEnvioMinimoPorEmpCtaYEstado(mapa);
		
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
		return lista;
	}
	/**
	 * Mediante un itemEnvioresumen y un nombre de socio traigo una cuenta del enviado
	 * @return
	 */

	
	public Envioconcepto getCuentaOfConArchivo(String nombreSocio,
												Integer empresa,
												Integer nivel,
												Integer codigo,
												Integer tipoSocio,
												Integer modalidad,
												Integer periodo) throws BusinessException{
		Envioconcepto domain = null;
		List<Envioconcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();			
			mapa.put("nombreSocio", nombreSocio);
			mapa.put("empresa", empresa);
			mapa.put("nivel", nivel);
			mapa.put("codigo", codigo);
			mapa.put("tipoSocio", tipoSocio);
			mapa.put("modalidad", modalidad);
			mapa.put("periodo", periodo);
			lista = dao.getCuentaOfConArchivo(mapa);
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
	public List<Envioconcepto> getListaCuentaEnConArchivo(Integer empresa,
															Integer nivel,
															Integer codigo,
															Integer tipoSocio,
															Integer modalidad,
															Integer periodo)
		throws BusinessException
		{
			List<Envioconcepto> lista = null;
			try
			{
				HashMap<String, Object> mapa = new HashMap<String, Object>();
				mapa.put("empresa", empresa);
				mapa.put("nivel", nivel);
				mapa.put("codigo", codigo);
				mapa.put("tipoSocio", tipoSocio);
				mapa.put("modalidad", modalidad);
				mapa.put("periodo", periodo);
				lista = dao.getListaCuentaEnConArchivo(mapa);
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