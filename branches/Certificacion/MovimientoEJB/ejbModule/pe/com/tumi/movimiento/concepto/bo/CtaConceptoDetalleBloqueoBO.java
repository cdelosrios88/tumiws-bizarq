package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.CtaConceptoDetalleBloqueoDao;
import pe.com.tumi.movimiento.concepto.dao.impl.CtaConceptoDetalleBloqueoDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;

public class CtaConceptoDetalleBloqueoBO {
	
	private CtaConceptoDetalleBloqueoDao dao = (CtaConceptoDetalleBloqueoDao)TumiFactory.get(CtaConceptoDetalleBloqueoDaoIbatis.class);
	
	public List<CuentaConcepto> getListaCuentaConceptoPorEmpresaYCuenta(Integer intEmpresa,Integer intCuenta) throws BusinessException{
		List<CuentaConcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk",intEmpresa);
			mapa.put("intCuentaPk", 	intCuenta);
			lista = dao.getListaPorEmpresaYCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	//cobranza
	public List<CuentaConcepto> getListaCuentaConceptoEmpresaCuentaOfCob(Integer intEmpresa,
																		Integer intCuenta) throws BusinessException{
		List<CuentaConcepto> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk",intEmpresa);
			mapa.put("intCuentaPk", 	intCuenta);
			lista = dao.getListaEmpresaCuentaOfCob(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}