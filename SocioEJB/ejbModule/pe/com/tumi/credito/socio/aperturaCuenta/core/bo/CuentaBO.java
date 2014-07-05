package pe.com.tumi.credito.socio.aperturaCuenta.core.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.CuentaDao;
import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.impl.CuentaDaoIbatis;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CuentaBO {
	private CuentaDao dao = (CuentaDao)TumiFactory.get(CuentaDaoIbatis.class);
	
	public Cuenta grabar(Cuenta o) throws BusinessException{
		Cuenta dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Cuenta modificar(Cuenta o) throws BusinessException{
		Cuenta dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Cuenta getCuentaPorPK(CuentaId pPK) throws BusinessException{
		Cuenta domain = null;
		List<Cuenta> lista = null;
		try{
			HashMap<String, Integer> mapa = new HashMap<String, Integer>();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intCuenta", 		 pPK.getIntCuenta());
			lista = dao.getListaCuentaPorPK(mapa);
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
			System.out.println("DAOExceptionDAOException--> "+e);
			throw new BusinessException(e);
		}catch(BusinessException e1){
			System.out.println("BusinessExceptionBusinessException--> "+e1);
			throw e1;
		}catch(Exception e3) {
			System.out.println("ExceptionException--> "+e3);
			throw new BusinessException(e3);
		}
		return domain;
	}
	
	public Cuenta actualizarNroCuentaYSecuencia(Cuenta o) throws BusinessException{
		Cuenta dto = null;
		try{
			dto = dao.actualizarNroCuentaYSecuencia(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Cuenta getCuentaPorPKYSituacion(Cuenta cuenta) throws BusinessException{
		Cuenta domain = null;
		List<Cuenta> lista = null;
		try{
			HashMap<String, Integer> mapa = new HashMap<String, Integer>();
			mapa.put("intPersEmpresaPk", 			cuenta.getId().getIntPersEmpresaPk());
			mapa.put("intCuenta", 		 			cuenta.getId().getIntCuenta());
			mapa.put("intParaSituacionCuentaCod", 	cuenta.getIntParaSituacionCuentaCod());
			lista = dao.getListaCuentaPorPKYSituacion(mapa);
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
	 * Recupera la Cuenta Sin tener en cuenta el estado
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
	public List<Cuenta> getListaCuentaPorPkTodoEstado(CuentaId pPK) throws BusinessException{
		//Cuenta domain = null;
		List<Cuenta> lista = null;
		try{
			HashMap<String, Integer> mapa = new HashMap<String, Integer>();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intCuenta", 		 pPK.getIntCuenta());
			lista = dao.getListaCuentaPorPkTodoEstado(mapa);
			/*if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	*/
		}catch(DAOException e){
			System.out.println("DAOExceptionDAOException--> "+e);
			throw new BusinessException(e);
		}/*catch(BusinessException e1){
			System.out.println("BusinessExceptionBusinessException--> "+e1);
			throw e1;
		}*/catch(Exception e3) {
			System.out.println("ExceptionException--> "+e3);
			throw new BusinessException(e3);
		}
		return lista;
	}
	
}
