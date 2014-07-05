package pe.com.tumi.cobranza.planilla.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.Expediente;

import pe.com.tumi.cobranza.planilla.dao.EfectuadoConceptoDao;
import pe.com.tumi.cobranza.planilla.dao.impl.EfectuadoConceptoDaoIbatis;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoId;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;


public class EfectuadoConceptoBO{

	private EfectuadoConceptoDao dao = (EfectuadoConceptoDao)TumiFactory.get(EfectuadoConceptoDaoIbatis.class);

	public List<EfectuadoConcepto> getListaPorEfectuado(EfectuadoId pid) throws BusinessException{
		List<EfectuadoConcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaCuentaEnvioPk", 	pid.getIntEmpresacuentaPk());
			mapa.put("intItemefectuado", 			pid.getIntItemefectuado());
			lista = dao.getListaPorEfectuado(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
    /**
     * AUTOR Y FECHA CREACION: JCHAVEZ / 06-08-2013
     * OBTENER EFECTUADOCONCEPTO POR PK DE EFECTUADO Y EXPEDIENTE
     * **/	
	public List<EfectuadoConcepto> getListaPorEfectuadoYExpediente(EfectuadoId pid, Expediente expediente) throws BusinessException{
		List<EfectuadoConcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaCuentaEnvioPk", 	pid.getIntEmpresacuentaPk());
			mapa.put("intItemefectuado", 			pid.getIntItemefectuado());
			mapa.put("intItemExpediente", 			expediente.getId().getIntItemExpediente());
			mapa.put("intItemDetExpediente", 		expediente.getId().getIntItemExpedienteDetalle());
			lista = dao.getListaPorEfectuadoYExpediente(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 14-09-2013
     * OBTENER EFECTUADOCONCEPTO POR PK DE EFECTUADO Y (EXPEDIENTE O CUENTACONCEPTO) Y TIPOCONCEPTOGRAL
	 * @param pid
	 * @param expediente
	 * @param intItemCuentaConcepto
	 * @param intTipoConceptoGeneral
	 * @return
	 * @throws BusinessException
	 */
	public List<EfectuadoConcepto> getListaPorEfectuadoPKEnvioConcepto (EfectuadoId pid, Envioconcepto envioConcepto) throws BusinessException{
		List<EfectuadoConcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaCuentaEnvioPk", 	pid.getIntEmpresacuentaPk());
			mapa.put("intItemefectuado", 			pid.getIntItemefectuado());
			mapa.put("intItemExpediente", 			envioConcepto.getIntItemexpediente());
			mapa.put("intItemDetExpediente", 		envioConcepto.getIntItemdetexpediente());
			mapa.put("intItemCuentaConcepto", 		envioConcepto.getIntItemcuentaconcepto());
			mapa.put("intTipoConceptoGeneralCod", 	envioConcepto.getIntTipoconceptogeneralCod());
			lista = dao.getListaPorEfectuadoPKEnvioConcepto(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	public EfectuadoConcepto grabarEfectuadoConcepto(EfectuadoConcepto o) throws BusinessException
	{
		EfectuadoConcepto dto = null;
		try
		{
			dto = dao.grabar(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	public EfectuadoConcepto grabarSubEfectuadoConcepto(EfectuadoConcepto o) throws BusinessException
	{
		EfectuadoConcepto dto = null;
		try
		{
			dto = dao.grabarSub(o);
		}catch(DAOException e){
	  		throw new BusinessException(e);
	    }catch(Exception e){
	    	throw new BusinessException(e);
	    }
	    return dto;
	}
	
	public EfectuadoConcepto modificarEfectuado(EfectuadoConcepto o) throws BusinessException{
		EfectuadoConcepto dto = null;
     	try{
		     dto = dao.modificar(o);
	  	}catch(DAOException e){
	  		 throw new BusinessException(e);
	    }catch(Exception e){
	    	 throw new BusinessException(e);
	    }
	    return dto;
    }

	public List<EfectuadoConcepto> montoExpedientePrestamo (Integer intItemEfectuadoResumen,
															Integer intParaTipoCredito,
															Integer intItemCredito) throws BusinessException{
		List<EfectuadoConcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemEfectuadoResumen", 	intItemEfectuadoResumen);
			mapa.put("intParaTipoCredito", 			intParaTipoCredito);
			mapa.put("intItemCredito", 			intItemCredito);
			lista = dao.montoExpedientePrestamo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<EfectuadoConcepto> montoExpedienteInteres (Integer intItemEfectuadoResumen) throws BusinessException{
		List<EfectuadoConcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemEfectuadoResumen", 	intItemEfectuadoResumen);			
			lista = dao.montoExpedienteInteres(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<EfectuadoConcepto> montoCuentaPorPagar (Integer intItemEfectuadoResumen) throws BusinessException{
		List<EfectuadoConcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemEfectuadoResumen", 	intItemEfectuadoResumen);			
			lista = dao.montoCuentaPorPagar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<EfectuadoConcepto> prestamoInteres (Integer intItemEfectuadoResumen) throws BusinessException{
		List<EfectuadoConcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemEfectuadoResumen", 	intItemEfectuadoResumen);				
			lista = dao.prestamoInteres(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}