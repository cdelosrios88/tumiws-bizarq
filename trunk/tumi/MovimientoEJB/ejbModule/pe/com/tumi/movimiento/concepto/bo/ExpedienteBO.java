package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.ExpedienteDao;
import pe.com.tumi.movimiento.concepto.dao.impl.ExpedienteDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;

public class ExpedienteBO {
	
	private ExpedienteDao dao = (ExpedienteDao)TumiFactory.get(ExpedienteDaoIbatis.class);
	
	public Expediente grabarExpediente(Expediente o) throws BusinessException{
		Expediente dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Expediente modificarExpediente(Expediente o) throws BusinessException{
		Expediente dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Expediente getExpedientePorPK(ExpedienteId pId) throws BusinessException{
		Expediente domain = null;
		List<Expediente> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemExpediente", pId.getIntItemExpediente());
			mapa.put("intItemExpedienteDetalle", pId.getIntItemExpedienteDetalle());
			lista = dao.getListaPorPK(mapa);
			if(lista!=null){
				 //domain = lista.get(0);
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
	
	public List<Expediente> getListaExpedienteConSaldoPorEmpresaYcuenta(Integer intEmpresa,Integer intCuenta) throws BusinessException{
		List<Expediente> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", intEmpresa);
			mapa.put("intCuentaPk", intCuenta);
			lista = dao.getListaConSaldoPorEmpresaYcuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * 
	 * @param intEmpresa
	 * @param intCuenta
	 * @return
	 * @throws BusinessException
	 */
	public List<Expediente> getListaExpedientePorEmpresaYCta(Integer intEmpresa,Integer intCuenta) throws BusinessException{
		List<Expediente> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", intEmpresa);
			mapa.put("intCuentaPk", intCuenta);
			lista = dao.getListaPorEmpresaYCta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera solo los tipos de credito (1) Prestamo (2)orden de credito y (5)Refinanciamineto
	 * @param intEmpresa
	 * @param intCuenta
	 * @return
	 * @throws BusinessException
	 */
	public List<Expediente> getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(Integer intEmpresa,Integer intCuenta) throws BusinessException{
		List<Expediente> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", intEmpresa);
			mapa.put("intCuentaPk", intCuenta);
			lista = dao.getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera los expediente de movimiento segun cuenta y expediente
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<Expediente> getExpedientePorCtaYExp(ExpedienteId pId) throws BusinessException{
		List<Expediente> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
			mapa.put("intItemExpediente", pId.getIntItemExpediente());
			lista = dao.getListaXEmpCtaExp(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	
	
	
}