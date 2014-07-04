package pe.com.tumi.servicio.solicitudPrestamo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.dao.GarantiaCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.impl.GarantiaCreditoDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCreditoId;

public class GarantiaCreditoBO {
	
	private GarantiaCreditoDao dao = (GarantiaCreditoDao)TumiFactory.get(GarantiaCreditoDaoIbatis.class);
	
	public GarantiaCredito grabar(GarantiaCredito o) throws BusinessException{
		GarantiaCredito dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public GarantiaCredito modificar(GarantiaCredito o) throws BusinessException{
		GarantiaCredito dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public GarantiaCredito getPorPk(GarantiaCreditoId pId) throws BusinessException{
		GarantiaCredito domain = null;
		List<GarantiaCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			mapa.put("intItemGarantia", 		pId.getIntItemGarantia());
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
	
	public List<GarantiaCredito> getListaPorPkExpedienteCredito(ExpedienteCreditoId pId) throws BusinessException{
		List<GarantiaCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			lista = dao.getListaPorExpedienteCredito(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Integer getCantidadPersonasGarantizadasPorPkPersona(Integer idPersona) throws BusinessException{
		Integer escalar = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersPersonaGarantePk", idPersona);
			escalar = dao.getCantidadPersonasGarantizadasPorPkPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return escalar;
	}
	
	
	public List<GarantiaCredito> getListaGarantiasPorPkPersona(Integer intPersEmpresaPk, Integer idPersona) throws BusinessException{
		List<GarantiaCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		intPersEmpresaPk);
			mapa.put("intPersPersonaGarantePk", idPersona);
			lista = dao.getListaGarantiasPorPkPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04-09-2013 
	 * OBTENER LISTA DE GARANTIZADOS POR:
	 * @param intPersEmpresaPk
	 * @param intPersonaCod
	 * @param intCuenta
	 * @return
	 * @throws BusinessException
	 */
	public List<GarantiaCredito> getListaGarantiasPorEmpPersCta(GarantiaCredito garantiaCredito) throws BusinessException{
		List<GarantiaCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaGarantePk",	garantiaCredito.getIntPersEmpresaGarantePk());
			mapa.put("intPersPersonaGarantePk", garantiaCredito.getIntPersPersonaGarantePk());
			mapa.put("intPersCuentaGarantePk",  garantiaCredito.getIntPersCuentaGarantePk());
			lista = dao.getListaGarantiasPorEmpPersCta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}