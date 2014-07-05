package pe.com.tumi.servicio.solicitudPrestamo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.dao.ExpedienteCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.impl.ExpedienteCreditoDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;

public class ExpedienteCreditoBO {
	
	private ExpedienteCreditoDao dao = (ExpedienteCreditoDao)TumiFactory.get(ExpedienteCreditoDaoIbatis.class);
	
	public ExpedienteCredito grabar(ExpedienteCredito o) throws BusinessException{
		ExpedienteCredito dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ExpedienteCredito modificar(ExpedienteCredito o) throws BusinessException{
		ExpedienteCredito dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ExpedienteCredito getPorPk(ExpedienteCreditoId pId) throws BusinessException{
		ExpedienteCredito domain = null;
		List<ExpedienteCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
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
	
	public List<ExpedienteCredito> getListaBusquedaPorExpCredComp(Integer intTipoCredito) throws BusinessException{
		List<ExpedienteCredito> lista = null;
		HashMap<String,Object> mapa = new HashMap<String,Object>();

		try{
			mapa.put("intParaTipoCreditoCod", intTipoCredito);
			
			lista = dao.getListaBusquedaPorExpCredComp(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ExpedienteCredito> getListaBusquedaAutorizacionPorExpCredComp() throws BusinessException{
		List<ExpedienteCredito> lista = null;
		HashMap<String,Object> mapa = new HashMap<String,Object>();
		try{
			lista = dao.getListaBusquedaAutorizacionPorExpCredComp(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ExpedienteCredito> getListaPorCuenta(Cuenta cuenta) throws BusinessException{
		List<ExpedienteCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk",	cuenta.getId().getIntPersEmpresaPk());
			mapa.put("intCuenta", 			cuenta.getId().getIntCuenta());
			lista = dao.getListaPorCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public ExpedienteCredito grabarRefinanciamiento(ExpedienteCredito o) throws BusinessException{
		ExpedienteCredito dto = null;
		try{
			dto = dao.grabarRefinanciamiento(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<ExpedienteCredito> getListaBusquedaPorExpRefComp() throws BusinessException{
		List<ExpedienteCredito> lista = null;
		HashMap<String,Object> mapa = new HashMap<String,Object>();
		try{
			lista = dao.getListaBusquedaPorExpRefComp(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<ExpedienteCredito> getListaPorExpediente(ExpedienteCredito expedienteCredito) throws BusinessException{
		List<ExpedienteCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk",	expedienteCredito.getId().getIntPersEmpresaPk());
			mapa.put("intCuenta", 			expedienteCredito.getId().getIntCuentaPk());
			mapa.put("intItemExpediente", 	expedienteCredito.getId().getIntItemExpediente());
			
			lista = dao.getListaPorExpediente(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ExpedienteCredito> getListaBusquedaPorExpActComp() throws BusinessException{
		List<ExpedienteCredito> lista = null;
		HashMap<String,Object> mapa = new HashMap<String,Object>();
		try{
			lista = dao.getListaBusquedaPorExpActComp(mapa);
		}catch(DAOException e){
			System.out.println("Error en DAOException--->  "+e);
			throw new BusinessException(e);
		}catch(Exception e) {
			System.out.println("Error en Exception--->  "+e);
			throw new BusinessException(e);
		}
		return lista;
	}

	/**
	 * Recupera los expedientes de refinanciamineto segun filtros de busqueda.
	 * @param expedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCredito> getListaBusqRefinanFiltros(ExpedienteCreditoComp expedienteCompBusq) throws BusinessException{
		List<ExpedienteCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intBusqTipo",				expedienteCompBusq.getIntBusqTipo());
			mapa.put("strBusqCadena", 			expedienteCompBusq.getStrBusqCadena());	
			mapa.put("strBusqNroSol", 			expedienteCompBusq.getStrBusqNroSol());
			mapa.put("intBusqSucursal", 		expedienteCompBusq.getIntBusqSucursal());
			mapa.put("intBusqEstado", 			expedienteCompBusq.getIntBusqEstado());
			mapa.put("dtBusqFechaEstadoDesde", 	expedienteCompBusq.getDtBusqFechaEstadoDesde());
			mapa.put("dtBusqFechaEstadoHasta", 	expedienteCompBusq.getDtBusqFechaEstadoHasta());
			mapa.put("intBusqTipoCreditoEmpresa", expedienteCompBusq.getIntBusqTipoCreditoEmpresa());
			
			lista = dao.getListaBusqRefinanFiltros(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera los expedientes de autorizacion de refinanciamineto segun filtros de busqueda.
	 * @param expedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCredito> getListaBusqAutRefFiltros(ExpedienteCreditoComp expedienteCompBusq) throws BusinessException{
		List<ExpedienteCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intBusqTipo",				expedienteCompBusq.getIntBusqTipo());
			mapa.put("strBusqCadena", 			expedienteCompBusq.getStrBusqCadena());	
			mapa.put("strBusqNroSol", 			expedienteCompBusq.getStrBusqNroSol());
			mapa.put("intBusqSucursal", 		expedienteCompBusq.getIntBusqSucursal());
			mapa.put("intBusqEstado", 			expedienteCompBusq.getIntBusqEstado());
			mapa.put("dtBusqFechaEstadoDesde", 	expedienteCompBusq.getDtBusqFechaEstadoDesde());
			mapa.put("dtBusqFechaEstadoHasta", 	expedienteCompBusq.getDtBusqFechaEstadoHasta());
			mapa.put("intBusqTipoCreditoEmpresa", expedienteCompBusq.getIntBusqTipoCreditoEmpresa());
			
			lista = dao.getListaBusqAutRefFiltros(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	
	/**
	 * Recupera los expedientes de credito segun filtros de busqueda.
	 * @param expedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCredito> getListaBusqCreditoFiltros(ExpedienteCreditoComp expedienteCompBusq) throws BusinessException{
		List<ExpedienteCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intBusqTipo",					expedienteCompBusq.getIntBusqTipo());
			mapa.put("strBusqCadena", 				expedienteCompBusq.getStrBusqCadena());	
			mapa.put("strBusqNroSol", 				expedienteCompBusq.getStrBusqNroSol());
			mapa.put("intBusqSucursal", 			expedienteCompBusq.getIntBusqSucursal());
			mapa.put("intBusqEstado", 				expedienteCompBusq.getIntBusqEstado());
			mapa.put("dtBusqFechaEstadoDesde", 		expedienteCompBusq.getDtBusqFechaEstadoDesde());
			mapa.put("dtBusqFechaEstadoHasta", 		expedienteCompBusq.getDtBusqFechaEstadoHasta());
			mapa.put("intBusqTipoCreditoEmpresa", 	expedienteCompBusq.getIntBusqTipoCreditoEmpresa());
			
			lista = dao.getListaBusqCreditoFiltros(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera los expedientes de autorizacion credito segun filtros de busqueda.
	 * @param expedienteCredito
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCredito> getListaBusqCreditosAutFiltros(ExpedienteCreditoComp expedienteCompBusq) throws BusinessException{
		List<ExpedienteCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intBusqTipo",					expedienteCompBusq.getIntBusqTipo());
			mapa.put("strBusqCadena", 				expedienteCompBusq.getStrBusqCadena());	
			mapa.put("strBusqNroSol", 				expedienteCompBusq.getStrBusqNroSol());
			mapa.put("intBusqSucursal", 			expedienteCompBusq.getIntBusqSucursal());
			mapa.put("intBusqEstado", 				expedienteCompBusq.getIntBusqEstado());
			mapa.put("dtBusqFechaEstadoDesde", 		expedienteCompBusq.getDtBusqFechaEstadoDesde());
			mapa.put("dtBusqFechaEstadoHasta", 		expedienteCompBusq.getDtBusqFechaEstadoHasta());
			mapa.put("intBusqTipoCreditoEmpresa", 	expedienteCompBusq.getIntBusqTipoCreditoEmpresa());
			
			lista = dao.getListaBusqCreditosAutFiltros(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
		/**
		 * Recupera los expedientes de actividad segun filtros de busqueda.
		 * @param expedienteCredito
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteCredito> getListaBusqActividadFiltros(ExpedienteCreditoComp expedienteCompBusq) throws BusinessException{
			List<ExpedienteCredito> lista = null;
			try{
				HashMap<String,Object> mapa = new HashMap<String,Object>();
				mapa.put("intBusqTipo",					expedienteCompBusq.getIntBusqTipo());
				mapa.put("strBusqCadena", 				expedienteCompBusq.getStrBusqCadena());	
				mapa.put("strBusqNroSol", 				expedienteCompBusq.getStrBusqNroSol());
				mapa.put("intBusqSucursal", 			expedienteCompBusq.getIntBusqSucursal());
				mapa.put("intBusqEstado", 				expedienteCompBusq.getIntBusqEstado());
				mapa.put("dtBusqFechaEstadoDesde", 		expedienteCompBusq.getDtBusqFechaEstadoDesde());
				mapa.put("dtBusqFechaEstadoHasta", 		expedienteCompBusq.getDtBusqFechaEstadoHasta());
				mapa.put("intBusqTipoCreditoEmpresa", 	expedienteCompBusq.getIntBusqTipoCreditoEmpresa());
				mapa.put("iintBusqItemCredito", 		expedienteCompBusq.getIntBusqItemCredito());
				
				lista = dao.getListaBusqActividadFiltros(mapa);
			}catch(DAOException e){
				throw new BusinessException(e);
			}catch(Exception e) {
				throw new BusinessException(e);
			}
			return lista;
		}
		
		
		/**
		 * Recupera los expedientes de actividad por autorizar segun filtros de busqueda.
		 * @param expedienteCredito
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteCredito> getListaBusqActAutFiltros(ExpedienteCreditoComp expedienteCompBusq) throws BusinessException{
			List<ExpedienteCredito> lista = null;
			try{
				HashMap<String,Object> mapa = new HashMap<String,Object>();
				mapa.put("intBusqTipo",					expedienteCompBusq.getIntBusqTipo());
				mapa.put("strBusqCadena", 				expedienteCompBusq.getStrBusqCadena());	
				mapa.put("strBusqNroSol", 				expedienteCompBusq.getStrBusqNroSol());
				mapa.put("intBusqSucursal", 			expedienteCompBusq.getIntBusqSucursal());
				mapa.put("intBusqEstado", 				expedienteCompBusq.getIntBusqEstado());
				mapa.put("dtBusqFechaEstadoDesde", 		expedienteCompBusq.getDtBusqFechaEstadoDesde());
				mapa.put("dtBusqFechaEstadoHasta", 		expedienteCompBusq.getDtBusqFechaEstadoHasta());
				mapa.put("intBusqTipoCreditoEmpresa", 	expedienteCompBusq.getIntBusqTipoCreditoEmpresa());
				mapa.put("iintBusqItemCredito", 		expedienteCompBusq.getIntBusqItemCredito());
				
				lista = dao.getListaBusqActAutFiltros(mapa);
			}catch(DAOException e){
				throw new BusinessException(e);
			}catch(Exception e) {
				throw new BusinessException(e);
			}
			return lista;
		}
	
	
		
		/**
		 * Recupera los expedientes de credito especial segun filtros de busqueda.
		 * @param expedienteCredito
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteCredito> getListaBusqCreditoEspecialFiltros(ExpedienteCreditoComp expedienteCompBusq) throws BusinessException{
			List<ExpedienteCredito> lista = null;
			try{
				HashMap<String,Object> mapa = new HashMap<String,Object>();
				mapa.put("intBusqTipo",					expedienteCompBusq.getIntBusqTipo());
				mapa.put("strBusqCadena", 				expedienteCompBusq.getStrBusqCadena());	
				mapa.put("strBusqNroSol", 				expedienteCompBusq.getStrBusqNroSol());
				mapa.put("intBusqSucursal", 			expedienteCompBusq.getIntBusqSucursal());
				mapa.put("intBusqEstado", 				expedienteCompBusq.getIntBusqEstado());
				mapa.put("dtBusqFechaEstadoDesde", 		expedienteCompBusq.getDtBusqFechaEstadoDesde());
				mapa.put("dtBusqFechaEstadoHasta", 		expedienteCompBusq.getDtBusqFechaEstadoHasta());
				mapa.put("intBusqTipoCreditoEmpresa", 	expedienteCompBusq.getIntBusqTipoCreditoEmpresa());
				
				lista = dao.getListaBusqCreditoEspecialFiltros(mapa);
			}catch(DAOException e){
				throw new BusinessException(e);
			}catch(Exception e) {
				throw new BusinessException(e);
			}
			return lista;
		}
		/**
		 * Agregado 31.12.2013 JCHAVEZ
		 * Procedimiento que recupera la Categoria de Riesgo del préstamo a cancelar en Giro REPRESTAMO
		 * @param pId
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteCredito> getRiesgoExpCredACancelar(ExpedienteCreditoId pId) throws BusinessException{
			List<ExpedienteCredito> lista = null;
			try{
				HashMap<String,Object> mapa = new HashMap<String,Object>();
				mapa.put("intEmpresa",				pId.getIntPersEmpresaPk());
				mapa.put("intCuenta", 				pId.getIntCuentaPk());	
				mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
				mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
				lista = dao.getRiesgoExpCredACancelar(mapa);
			}catch(DAOException e){
				throw new BusinessException(e);
			}catch(Exception e) {
				throw new BusinessException(e);
			}
			return lista;
		}		

		/**
		 * Recupera los expedientes de credito especial segun filtros de grilla.
		 * @param intPersEmpresaPk
		 * @param intCuenta
		 * @param intItemExpediente
		 * @return
		 * @throws BusinessException
		 */
		public List<ExpedienteCredito> getMaxExpedienteRefinan(Integer intPersEmpresaPk, Integer intCuenta, Integer intItemExpediente ) throws BusinessException{
			List<ExpedienteCredito> lista = null;
			try{
				HashMap<String,Object> mapa = new HashMap<String,Object>();
				mapa.put("intPersEmpresaPk",	intPersEmpresaPk);
				mapa.put("intCuenta", 			intCuenta);
				mapa.put("intItemExpediente", 	intItemExpediente);
				
				lista = dao.getMaxExpedienteRefinan(mapa);
			}catch(DAOException e){
				throw new BusinessException(e);
			}catch(Exception e) {
				throw new BusinessException(e);
			}
			return lista;
		}
		/**
		 * JCHAVEZ 31.01.2014
		 * Procedimiento que recupera los Requisitos necesarios para el giro en Giro REPRESTAMO
		 * @param expCred
		 * @return
		 * @throws BusinessException
		 */
		public List<RequisitoCreditoComp> getRequisitoGiroPrestamo(ExpedienteCredito expCred) throws BusinessException{
			List<RequisitoCreditoComp> lista = null;
			try{
				HashMap<String,Object> mapa = new HashMap<String,Object>();
				mapa.put("intEmpresa",			expCred.getId().getIntPersEmpresaPk());
				mapa.put("intParaTipoCredito", 	expCred.getIntParaTipoCreditoCod());	
				mapa.put("intItemCredito", 		expCred.getIntItemCredito());
				lista = dao.getRequisitoGiroPrestamo(mapa);
			}catch(DAOException e){
				throw new BusinessException(e);
			}catch(Exception e) {
				throw new BusinessException(e);
			}
			return lista;
		}	
		
}
