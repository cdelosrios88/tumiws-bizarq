package pe.com.tumi.riesgo.cartera.bo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.archivo.domain.Configuracion;
import pe.com.tumi.riesgo.cartera.dao.CarteraCreditoDao;
import pe.com.tumi.riesgo.cartera.dao.CarteraDao;
import pe.com.tumi.riesgo.cartera.dao.impl.CarteraCreditoDaoIbatis;
import pe.com.tumi.riesgo.cartera.dao.impl.CarteraDaoIbatis;
import pe.com.tumi.riesgo.cartera.domain.Cartera;
import pe.com.tumi.riesgo.cartera.domain.CarteraCredito;

public class CarteraCreditoBO {
	
	private CarteraCreditoDao dao = (CarteraCreditoDao)TumiFactory.get(CarteraCreditoDaoIbatis.class);
	
	public CarteraCredito getPorMaxPeriodo(Integer intEmpresa) throws BusinessException{
		CarteraCredito domain = null;
		List<CarteraCredito> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresacartera_n_pk", intEmpresa);
			
			lista = dao.getListaPorMaxPeriodo(mapa);
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
