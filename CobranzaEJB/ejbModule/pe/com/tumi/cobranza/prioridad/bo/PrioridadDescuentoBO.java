package pe.com.tumi.cobranza.prioridad.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.prioridad.dao.PrioridadDescuentoDao;
import pe.com.tumi.cobranza.prioridad.dao.impl.PrioridadDescuentoDaoIbatis;
import pe.com.tumi.cobranza.prioridad.domain.PrioridadDescuento;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.Expediente;

public class PrioridadDescuentoBO {
	
protected  static Logger log = Logger.getLogger(PrioridadDescuentoBO.class);
private PrioridadDescuentoDao dao = (PrioridadDescuentoDao)TumiFactory.get(PrioridadDescuentoDaoIbatis.class);
	
 public List<PrioridadDescuento> getListaPorConceptoGnral(Integer intPersEmpresa, Integer intParaTipoconceptogeneral) throws BusinessException{
		log.info("-----------------------Debugging PrioridadDescuentoBO.getListaPrioridadDescuento-----------------------------");
		List<PrioridadDescuento> lista = null;
		HashMap map = new HashMap();
		map.put("intPersEmpresa",intPersEmpresa);
		map.put("intParaTipoconceptogeneral",intParaTipoconceptogeneral);
		
		try{
			lista = dao.getListaPorConceptoGnral(map);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		log.debug(lista.size());
		return lista;
	}
	
 	/**
 	 * AUTOR Y FECHA CREACION: JCHAVEZ / 11-09-2013
	 * OBTENER PRIORIDADDESCUENTO POR TIPOCONCEPTOGRAL Y 
	 * MOVIMIENTO [CUENTACONCEPTODETALLE (intParaTipocaptacion - intcsocItem) O EXPEDIENTECREDITO (intParaTipoCredito - intCsocItemCredito)]
	 * @param envioconcepto
	 * @return
	 * @throws BusinessException
	 */
 	public PrioridadDescuento getPrioridadPorTipoCptoGralCtaCptoExpediente(Envioconcepto envioconcepto, Integer intParaTipomovimiento, CuentaConceptoDetalle ctaCptoDet, Expediente expediente) throws BusinessException{
		log.info("-----------------------Debugging PrioridadDescuentoBO.obtenerPrioridadPorTipoCptoGralCtaCptoExpediente-----------------------------");
		PrioridadDescuento dto = null;
		List<PrioridadDescuento> lista = null;
		try {
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("intPersEmpresa",				envioconcepto.getId().getIntEmpresacuentaPk());
			map.put("intParaTipoconceptogeneral",	envioconcepto.getIntTipoconceptogeneralCod());
			map.put("intParaTipomovimiento",		intParaTipomovimiento);
			map.put("intParaTipocaptacion",			ctaCptoDet.getIntParaTipoConceptoCod());
			map.put("intcsocItem",					ctaCptoDet.getIntItemConcepto());
			map.put("intParaTipoCredito",			expediente.getIntParaTipoCreditoCod());
			map.put("intCsocItemCredito",			expediente.getIntItemCredito());
		
			lista = dao.getListaPorTipoCptoGralCtaCptoExpediente(map);
			if(lista!=null){
				if(lista.size()==1){
					dto = lista.get(0);
				}else if(lista.size()==0){
					dto = null;
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
		return dto;
 	}
}
