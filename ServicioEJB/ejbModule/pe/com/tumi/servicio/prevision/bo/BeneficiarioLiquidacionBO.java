package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.BeneficiarioLiquidacionDao;
import pe.com.tumi.servicio.prevision.dao.impl.BeneficiarioLiquidacionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

public class BeneficiarioLiquidacionBO {
	
	private BeneficiarioLiquidacionDao dao = (BeneficiarioLiquidacionDao)TumiFactory.get(BeneficiarioLiquidacionDaoIbatis.class);
	
	public BeneficiarioLiquidacion grabar(BeneficiarioLiquidacion o) throws BusinessException{
		BeneficiarioLiquidacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public BeneficiarioLiquidacion modificar(BeneficiarioLiquidacion o) throws BusinessException{
		BeneficiarioLiquidacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public BeneficiarioLiquidacion getPorPk(BeneficiarioLiquidacionId pId) throws BusinessException{
		BeneficiarioLiquidacion domain = null;
		List<BeneficiarioLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacion", pId.getIntPersEmpresaLiquidacion());
			mapa.put("intItemExpediente", pId.getIntItemExpediente());
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intCuenta", pId.getIntCuenta());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
			mapa.put("intItemBeneficiario", pId.getIntItemBeneficiario());
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
	
	public List<BeneficiarioLiquidacion> getPorExpedienteLiquidacionDetalle(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle) throws BusinessException{
		List<BeneficiarioLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacion", expedienteLiquidacionDetalle.getId().getIntPersEmpresaLiquidacion());
			mapa.put("intItemExpediente", expedienteLiquidacionDetalle.getId().getIntItemExpediente());
			mapa.put("intPersEmpresa", expedienteLiquidacionDetalle.getId().getIntPersEmpresa());
			mapa.put("intCuenta", expedienteLiquidacionDetalle.getId().getIntCuenta());
			mapa.put("intItemCuentaConcepto", expedienteLiquidacionDetalle.getId().getIntItemCuentaConcepto());
			lista = dao.getListaPorExpedienteLiquidacionDetalle(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<BeneficiarioLiquidacion> getPorEgreso(Egreso egreso) throws BusinessException{
		List<BeneficiarioLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();			
			mapa.put("intPersEmpresaEgreso", egreso.getId().getIntPersEmpresaEgreso());
			mapa.put("intItemEgresoGeneral", egreso.getId().getIntItemEgresoGeneral());
			lista = dao.getListaPorEgreso(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	
	public List<BeneficiarioLiquidacion> getListaPorExpedienteLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
		List<BeneficiarioLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacion", expedienteLiquidacion.getId().getIntPersEmpresaPk());
			mapa.put("intItemExpediente", expedienteLiquidacion.getId().getIntItemExpediente());
			
			lista = dao.getListaPorExpedienteLiquidacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void deletePorExpediente(ExpedienteLiquidacionId expedienteLiquidacionId) throws BusinessException{
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacion", expedienteLiquidacionId.getIntPersEmpresaPk());
			mapa.put("intItemExpediente", expedienteLiquidacionId.getIntItemExpediente());
			
			dao.deletePorExpediente(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}
	
}