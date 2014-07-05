package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.CtaConceptoBloqueoDao;
import pe.com.tumi.movimiento.concepto.dao.impl.CtaConceptoBloqueoDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;

public class CtaConceptoBloqueoBO {
	
	private CtaConceptoBloqueoDao dao = (CtaConceptoBloqueoDao)TumiFactory.get(CtaConceptoBloqueoDaoIbatis.class);
	
	public List<CuentaConcepto> getListaPorEmpresaYCuenta(Integer intEmpresa,Integer intCuenta) throws BusinessException{
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
}