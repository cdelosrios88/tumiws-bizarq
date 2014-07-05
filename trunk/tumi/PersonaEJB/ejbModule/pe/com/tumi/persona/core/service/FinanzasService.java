package pe.com.tumi.persona.core.service;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.bo.CuentaBancariaBO;
import pe.com.tumi.persona.core.bo.CuentaBancariaFinBO;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaFin;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;

public class FinanzasService {
	
	protected  static Logger log = Logger.getLogger(FinanzasService.class);
	
	private CuentaBancariaBO boCtaBancaria = (CuentaBancariaBO)TumiFactory.get(CuentaBancariaBO.class);
	private CuentaBancariaFinBO boCtaBancariaFin = (CuentaBancariaFinBO)TumiFactory.get(CuentaBancariaFinBO.class);

	public List<CuentaBancaria> grabarListaDinamicaCtaBancaria(List<CuentaBancaria> lista, Integer intIdPersona) throws BusinessException{
		CuentaBancaria dto = null;
		CuentaBancariaPK pk = null;
		CuentaBancaria dtoTemp = null;
		List<CuentaBancariaFin> listaCuentaBancariaFin;
		try{
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				
				listaCuentaBancariaFin = dto.getListaCuentaBancariaFin();
				
				if(dto.getId() == null || dto.getId().getIntIdPersona() == null){
					pk = new CuentaBancariaPK();
					pk.setIntIdPersona(intIdPersona);
					dto.setId(pk);
					dto = boCtaBancaria.grabarCuentaBancaria(dto);
				}else{
					dtoTemp = boCtaBancaria.getCuentaBancariaPorPK(dto.getId());
					if(dtoTemp == null){
						dto.getId().setIntIdPersona(intIdPersona);
						dto = boCtaBancaria.grabarCuentaBancaria(dto);
					}else{
						dto = boCtaBancaria.modificarCuentaBancaria(dto);
					}
				}
				
				if(listaCuentaBancariaFin != null){
					grabarCuentaBancariaFin(dto, listaCuentaBancariaFin);
				}				
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void eliminarCtaBancariaPorIdPersona(Integer idPersona) throws BusinessException{
		CuentaBancaria dto = null;
		List<CuentaBancaria> lista = null;
		try{
			lista = boCtaBancaria.getListaCuentaBancariaPorIdPersona(idPersona);
			for(int i=0; i<lista.size(); i++){
				dto = lista.get(i);
				dto.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				boCtaBancaria.modificarCuentaBancaria(dto);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	private void grabarCuentaBancariaFin(CuentaBancaria cuentaBancaria, List<CuentaBancariaFin> listaCuentaBancariaFin) throws BusinessException{
		try{
			log.info(cuentaBancaria);
			List<CuentaBancariaFin> listaCuentaBancariaFinBD = boCtaBancariaFin.getListaPorCuentaBancaria(cuentaBancaria);
			for(CuentaBancariaFin cuentaBancariaFinBD : listaCuentaBancariaFinBD){
				log.info("eli:"+cuentaBancariaFinBD);
				boCtaBancariaFin.eliminar(cuentaBancariaFinBD.getId());
			}
			
			for(CuentaBancariaFin cuentaBancariaFin : listaCuentaBancariaFin){
				cuentaBancariaFin.getId().setIntIdPersona(cuentaBancaria.getId().getIntIdPersona());
				cuentaBancariaFin.getId().setIntIdCuentaBancaria(cuentaBancaria.getId().getIntIdCuentaBancaria());
				log.info("add:"+cuentaBancariaFin);
				boCtaBancariaFin.grabar(cuentaBancariaFin);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
}