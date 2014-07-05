package pe.com.tumi.reporte.operativo.cobranza.centralriesgo.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.dao.CarteraCreditoDao;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.dao.impl.CarteraCreditoDaoIbatis;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.CarteraCredito;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.ServicioDao;

public class CarteraCreditoBO {
	protected  static Logger log = Logger.getLogger(ServicioDao.class);
	private CarteraCreditoDao dao = (CarteraCreditoDao)TumiFactory.get(CarteraCreditoDaoIbatis.class);

	public List<CarteraCredito> getListaCarteraCredito(Integer intIdSucursal, Integer intIdSubSucursal, Integer intParaTipoSocio, 
													   Integer intParaModalidad, Integer intParaTipoCredito, Integer intParaSubTipoCredito, 
													   Integer intParaClasificacionDeudor, Integer intPeriodo) throws BusinessException{
		List<CarteraCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intTipoSocio", intParaTipoSocio);
			mapa.put("intModalidad", intParaModalidad);
			mapa.put("intTipoCredito", intParaTipoCredito);
			mapa.put("intSubTipoCredito", intParaSubTipoCredito);
			mapa.put("intClasificacionDeudor", intParaClasificacionDeudor);
			mapa.put("intPeriodo", intPeriodo);
			lista = dao.getListaCarteraCredito(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}