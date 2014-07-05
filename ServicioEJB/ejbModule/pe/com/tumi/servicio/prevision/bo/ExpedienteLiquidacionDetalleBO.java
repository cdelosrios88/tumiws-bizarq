package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.ExpedienteLiquidacionDetalleDao;
import pe.com.tumi.servicio.prevision.dao.impl.ExpedienteLiquidacionDetalleDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalleId;

public class ExpedienteLiquidacionDetalleBO {
	
	private ExpedienteLiquidacionDetalleDao dao = (ExpedienteLiquidacionDetalleDao)TumiFactory.get(ExpedienteLiquidacionDetalleDaoIbatis.class);
	
	public ExpedienteLiquidacionDetalle grabar(ExpedienteLiquidacionDetalle o) throws BusinessException{
		ExpedienteLiquidacionDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ExpedienteLiquidacionDetalle modificar(ExpedienteLiquidacionDetalle o) throws BusinessException{
		ExpedienteLiquidacionDetalle dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ExpedienteLiquidacionDetalle getPorPk(ExpedienteLiquidacionDetalleId pId) throws BusinessException{
		ExpedienteLiquidacionDetalle domain = null;
		List<ExpedienteLiquidacionDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacion", pId.getIntPersEmpresaLiquidacion());
			mapa.put("intItemExpediente", pId.getIntItemExpediente());
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intCuenta", pId.getIntCuenta());
			mapa.put("intItemCuentaConcepto", pId.getIntItemCuentaConcepto());
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
	
	public List<ExpedienteLiquidacionDetalle> getPorCuentaId(CuentaId cuentaId) throws BusinessException{
		List<ExpedienteLiquidacionDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", cuentaId.getIntPersEmpresaPk());
			mapa.put("intCuenta", cuentaId.getIntCuenta());
			lista = dao.getListaPorCuenta(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ExpedienteLiquidacionDetalle> getPorExpedienteLiquidacion(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
		List<ExpedienteLiquidacionDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacion", expedienteLiquidacion.getId().getIntPersEmpresaPk());
			mapa.put("intItemExpediente", expedienteLiquidacion.getId().getIntItemExpediente());
			lista = dao.getListaPorExpediente(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}