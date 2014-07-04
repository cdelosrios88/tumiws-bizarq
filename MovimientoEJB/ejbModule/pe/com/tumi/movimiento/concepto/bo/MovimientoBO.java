package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.MovimientoDao;
import pe.com.tumi.movimiento.concepto.dao.impl.MovimientoDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;

public class MovimientoBO {
	
	private MovimientoDao dao = (MovimientoDao)TumiFactory.get(MovimientoDaoIbatis.class);
	
	public Movimiento grabar(Movimiento o) throws BusinessException{
		Movimiento dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Movimiento modificar(Movimiento o) throws BusinessException{
		Movimiento dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Movimiento getPorPK(Integer intItemMovimiento) throws BusinessException{
		Movimiento domain = null;
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intItemMovimiento", intItemMovimiento);
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
	public List<Movimiento> getListaMovimientoPorCuentaEmpresaConcepto(Integer intPersEmpresa,Integer intCuenta, Integer intItemCuentaConcepto) throws BusinessException{
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intItemCuentaConcepto", intItemCuentaConcepto);
			lista = dao.getListaMovimientoPorCuentaEmpresaConcepto(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Movimiento> getListPeriodoMaxCuentaEmpresa(Integer intPersEmpresa,Integer intCuenta) throws BusinessException{
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intCuenta", intCuenta);
			lista = dao.getListPeriodoMaxCuentaEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	
	public List<Movimiento> getListaMovimientoPorCtaPersonaConceptoGeneral(Integer intPersEmpresa,Integer intCuenta,Integer intPersPersonaIntegrante, Integer intItemCuentaConceptoGen) throws BusinessException{
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intPersPersonaIntegrante", intPersPersonaIntegrante);
			mapa.put("intParaTipoConceptoGeneral", intItemCuentaConceptoGen);
			lista = dao.getListXCtaYPersYCptoGeneral(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<Movimiento> getListXCtaExpedienteYTipoMov(Integer intPersEmpresa,Integer intCuenta,Integer intPersPersonaIntegrante, 
			Integer intParaTipoMovimiento,Integer intItemExpediente,Integer intItemExpedienteDetalle,Integer intParaDocumentoGeneral ) throws BusinessException{
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intPersPersonaIntegrante", intPersPersonaIntegrante);
			mapa.put("intParaTipoMovimiento", intParaTipoMovimiento);
			mapa.put("intItemExpediente", intItemExpediente);
			mapa.put("intItemExpedienteDetalle", intItemExpedienteDetalle);
			mapa.put("intParaDocumentoGeneral", intParaDocumentoGeneral);
			lista = dao.getListXCtaExpedienteYTipoMov(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Movimiento> getListXCtaExpediente(Integer intPersEmpresa,Integer intCuenta,Integer intItemExpediente,Integer intItemExpedienteDetalle) throws BusinessException{
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intItemExpediente", intItemExpediente);
			mapa.put("intItemExpedienteDetalle", intItemExpedienteDetalle);
			lista = dao.getListXCtaExpediente(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//getPorPK
	public Movimiento getMaximoMovimiento() throws BusinessException{
		Movimiento domain = null;
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			lista = dao.getListaMaximoMovimiento(mapa);
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
	 * Recupera los movimeitos en base a Cta, 
	 * @param intPersEmpresa
	 * @param intCuenta
	 * @param intPersPersonaIntegrante
	 * @param intItemCuentaConceptoGen
	 * @return
	 * @throws BusinessException
	 */
	public List<Movimiento> getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(Integer intPersEmpresa,Integer intCuenta,Integer intItemCuentaConcepto, Integer intTipoConceptoGeneral) throws BusinessException{
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intItemCuentaConcepto", intItemCuentaConcepto);
			mapa.put("intParaTipoConceptoGeneral", intTipoConceptoGeneral);
			lista = dao.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera el ultimo movieminto de una cuenta concepto
	 * @param intPersEmpresa
	 * @param intCuenta
	 * @param intItemCuentaConcepto
	 * @param intTipoConceptoGral
	 * @return
	 * @throws BusinessException
	 */
	public List<Movimiento> getListaMaximoMovimientoPorCuentaConcepto(Integer intPersEmpresa,Integer intCuenta,Integer intItemCuentaConcepto, Integer intTipoConceptoGral) throws BusinessException{
			List<Movimiento> lista = null;
			try{
				HashMap<String,Object> mapa = new HashMap<String,Object>();
				mapa.put("intPersEmpresa", intPersEmpresa);
				mapa.put("intCuenta", intCuenta);
				mapa.put("intItemCuentaConcepto", intItemCuentaConcepto);
				mapa.put("intParaTipoConceptoGeneral", intTipoConceptoGral);
				
				lista = dao.getListaMaximoMovimientoPorCuentaConcepto(mapa);
			}catch(DAOException e){
				throw new BusinessException(e);
			}catch(Exception e) {
				throw new BusinessException(e);
			}
			return lista;
		}
	/**
	 * SE OBTIENE LISTA DE MOVIMIENTOCTACTO POR
	 * NRO DE CUENTA Y EMPRESA (ORDENADOS POR FECHAMOVIMIENTO Y TIPOMOVIMIENTO)
	 **/
	public List<Movimiento> getListXCuentaYEmpresa(Integer intPersEmpresa,Integer intCuenta) throws BusinessException{
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intCuenta", intCuenta);
			lista = dao.getListXCuentaYEmpresa(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	
	/**
	 * Recupera los moviemintos den base a la empresa, cuenta y itemctacto
	 * @param intPersEmpresa
	 * @param intCuenta
	 * @param intItemCuentaConcepto
	 * @return
	 * @throws BusinessException
	 */
	public List<Movimiento> getMaxMovXCtaEmpresaTipoMov(Integer intPersEmpresa,Integer intCuenta,Integer intItemCuentaConcepto) throws BusinessException{
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intParaTipoMovimiento", intItemCuentaConcepto);
			
			lista = dao.getMaxMovXCtaEmpresaTipoMov(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera el ultimo registro por id de expediente y  tipo concetpogeneral
	 * @param intPersEmpresa
	 * @param intCuenta
	 * @param intItemExpediente
	 * @param intItemExpedienteDetalle
	 * @param intTipoConceptoGral
	 * @return
	 * @throws BusinessException
	 */
	public Movimiento getMaxXExpYCtoGral(Integer intPersEmpresa,Integer intCuenta,Integer intItemExpediente, Integer intItemExpedienteDetalle, Integer intTipoConceptoGral) throws BusinessException{
		Movimiento domain = null;
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intItemExpediente", intItemExpediente);
			mapa.put("intItemExpedienteDetalle", intItemExpedienteDetalle);
			mapa.put("intParaTipoConceptoGeneral", intTipoConceptoGral);
			
			lista = dao.getMaxXExpYCtoGral(mapa);
			if(lista != null && !lista.isEmpty()){
				domain= new Movimiento();
				domain= lista.get(0);
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	/**
	 * Agregado 19.05.2014 jchavez
	 * @param intPersEmpresa
	 * @param intCuenta
	 * @param intItemExpediente
	 * @param intParaTipoConcepto
	 * @param intParaDocumentoGeneral
	 * @return
	 * @throws BusinessException
	 */
	public List<Movimiento> getListaMovVtaCtePorPagar(Integer intPersEmpresa,Integer intCuenta, Integer intItemExpediente, Integer intParaTipoConcepto, Integer intParaDocumentoGeneral) throws BusinessException{
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intItemExpediente", intItemExpediente);
			mapa.put("intParaTipoConcepto", intParaTipoConcepto);
			mapa.put("intParaDocumentoGeneral", intParaDocumentoGeneral);
			
			lista = dao.getListaMovVtaCtePorPagar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//rVillarreal
	public List<Movimiento> getMaxMovCtaCteXFecha(Integer intPersEmpresa) throws BusinessException{
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersIntegrante", intPersEmpresa);
					
			lista = dao.getMaxMovCtaCteXFecha(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	/**
	 * Agregado 05.06.2014 jchavez
	 * @param intPersEmpresa
	 * @param intCuenta
	 * @param intItemExpediente
	 * @param intParaDocumentoGeneral
	 * @return
	 * @throws BusinessException
	 */
	public List<Movimiento> getListaMovCtaCtePorPagarLiq(Integer intPersEmpresa,Integer intCuenta, Integer intItemExpediente, Integer intParaDocumentoGeneral) throws BusinessException{
		List<Movimiento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intPersEmpresa);
			mapa.put("intCuenta", intCuenta);
			mapa.put("intItemExpediente", intItemExpediente);
			mapa.put("intParaDocumentoGeneral", intParaDocumentoGeneral);
			
			lista = dao.getListaMovCtaCtePorPagarLiq(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

}