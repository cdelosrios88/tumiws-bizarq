package pe.com.tumi.movimiento.concepto.bo;

import java.util.HashMap;
import java.util.List;


import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.dao.BloqueoCuentaDao;
import pe.com.tumi.movimiento.concepto.dao.impl.BloqueoCuentaDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;

public class BloqueoCuentaBO {
	
	private BloqueoCuentaDao dao = (BloqueoCuentaDao)TumiFactory.get(BloqueoCuentaDaoIbatis.class);
	
	public BloqueoCuenta grabarBloqueoCuenta(BloqueoCuenta o) throws BusinessException{
		BloqueoCuenta dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<BloqueoCuenta> getListaPorNroCuentaYMotivo(Integer intPersEmpresaPk,Integer intCuentaPk,Integer intParaCodigoMotivoCod) throws BusinessException{
		List<BloqueoCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 	intPersEmpresaPk);
			mapa.put("intCuentaPk", 	intCuentaPk);
			mapa.put("intParaCodigoMotivoCod", 		intParaCodigoMotivoCod);
			lista = dao.getListaPorNroCuentaYMotivo(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<BloqueoCuenta> getListaPorNroCuenta(Integer intPersEmpresaPk,Integer intCuentaPk) throws BusinessException{
		List<BloqueoCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk",intPersEmpresaPk);
			mapa.put("intCuentaPk",intCuentaPk);
			lista = dao.getListaPorNroCuenta(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	public List<BloqueoCuenta> getListaFondoSepelioMonto(Integer intEmpresa,Integer intCuenta) throws BusinessException{
		List<BloqueoCuenta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk",intEmpresa);
			mapa.put("intCuentaPk", 	intCuenta);
			lista = dao.getListaFondoSepelio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}