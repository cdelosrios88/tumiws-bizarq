package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.EstadoExpedienteDao;
import pe.com.tumi.movimiento.concepto.dao.impl.EstadoExpedienteDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;


public class EstadoExpedienteBO {

	private EstadoExpedienteDao  dao = (EstadoExpedienteDao)TumiFactory.get(EstadoExpedienteDaoIbatis .class);
	
	public EstadoExpediente grabar(EstadoExpediente o) throws BusinessException{
		EstadoExpediente dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstadoExpediente modificar(EstadoExpediente o) throws BusinessException{
		EstadoExpediente dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstadoExpediente getPorPk(EstadoExpedienteId pId) throws BusinessException{
		EstadoExpediente domain = null;
		List<EstadoExpediente> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaEstado", 		pId.getIntEmpresaEstado() );
			mapa.put("intItemEstado", 				pId.getIntItemEstado());

			lista = dao.getListaPorPK(mapa);
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

	
	public List<EstadoExpediente> getListaPorPkExpedienteCredito(ExpedienteId pId) throws BusinessException{
		List<EstadoExpediente> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 				pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemExpedienteDetalle());
			lista = dao.getListaPorExpediente(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera el ultimo estado del expediente en movimiento.
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public EstadoExpediente getMaxEstadoExpPorPkExpediente(ExpedienteId pId) throws BusinessException{
		EstadoExpediente domain = null;
		List<EstadoExpediente> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", 				pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemExpedienteDetalle());
			lista = dao.getMaxEstadoExpPorPkExpediente(mapa);
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
