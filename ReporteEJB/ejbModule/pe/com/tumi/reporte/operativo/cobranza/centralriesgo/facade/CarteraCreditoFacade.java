package pe.com.tumi.reporte.operativo.cobranza.centralriesgo.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.bo.CarteraCreditoBO;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.bo.FenacrepBO;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.CarteraCredito;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.Fenacrep;

@Stateless
public class CarteraCreditoFacade extends TumiFacade implements CarteraCreditoFacadeRemote, CarteraCreditoFacadeLocal {
	protected  static Logger log = Logger.getLogger(CarteraCreditoFacade.class);
	private CarteraCreditoBO boCarteraCredito = (CarteraCreditoBO)TumiFactory.get(CarteraCreditoBO.class);
	private FenacrepBO boFenacrep = (FenacrepBO)TumiFactory.get(FenacrepBO.class);

	public List<CarteraCredito> getListaCarteraCredito(Integer intIdSucursal, Integer intIdSubSucursal, Integer intParaTipoSocio, 
														  Integer intParaModalidad, Integer intParaTipoCredito, Integer intParaSubTipoCredito, 
														  Integer intParaClasificacionDeudor, Integer intPeriodo) 
																  throws BusinessException{
		List<CarteraCredito> lista = null;
		try {
			lista = boCarteraCredito.getListaCarteraCredito(intIdSucursal, intIdSubSucursal, intParaTipoSocio, intParaModalidad, intParaTipoCredito, intParaSubTipoCredito, intParaClasificacionDeudor, intPeriodo);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Fenacrep> getListaFenacrep(Integer intPeriodo) throws BusinessException{
		List<Fenacrep> lista = null;
		try {
			lista = boFenacrep.getListaFenacrep(intPeriodo);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
}