package pe.com.tumi.credito.socio.creditos.service;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.creditos.bo.CreditoDescuentoBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoDescuentoCaptacionBO;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;

public class CreditoDescuentoService {
	
	private CreditoDescuentoBO boCreditoDescuento = (CreditoDescuentoBO)TumiFactory.get(CreditoDescuentoBO.class);
	private CreditoDescuentoCaptacionBO boCreditoDescuentoCaptacion = (CreditoDescuentoCaptacionBO)TumiFactory.get(CreditoDescuentoCaptacionBO.class);
	
	public List<CreditoDescuento> getListaCreditoDescuento(CreditoId o) throws BusinessException{
		List<CreditoDescuento> lista = null;
		try{
			lista = boCreditoDescuento.getListaCreditoDescuentoPorPKCredito(o);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CreditoDescuento grabarCreditoDescuento(CreditoDescuento pCreditoDescuento) throws BusinessException{
		CreditoDescuento creditoDescuento = null;
		List<CreditoDescuentoCaptacion> listaDescuentoCaptacion = null;
		try{
			creditoDescuento = boCreditoDescuento.grabarCreditoDescuento(pCreditoDescuento);
			
			//Grabar Lista Descuento Captaciones
			listaDescuentoCaptacion = pCreditoDescuento.getListaDescuento();
			if(listaDescuentoCaptacion!=null){
				grabarListaDinamicaCreditoDescuentoCaptacion(listaDescuentoCaptacion, creditoDescuento.getId());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoDescuento;
	}
	
	public CreditoDescuento modificarCreditoDescuento(CreditoDescuento pCreditoDescuento) throws BusinessException{
		CreditoDescuento creditoDescuento = null;
		List<CreditoDescuentoCaptacion> listaDescuentoCaptacion = null;
		try{
			creditoDescuento = boCreditoDescuento.modificarCreditoDescuento(pCreditoDescuento);
			
			//Grabar Lista Descuento Captaciones
			listaDescuentoCaptacion = pCreditoDescuento.getListaDescuento();
			if(listaDescuentoCaptacion!=null){
				grabarListaDinamicaCreditoDescuentoCaptacion(listaDescuentoCaptacion, creditoDescuento.getId());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoDescuento;
	}
	
	public CreditoDescuento getCreditoDescuentoPorIdCreditoDescuento(CreditoDescuentoId pId) throws BusinessException {
		CreditoDescuento creditoDescuento = null;
		List<CreditoDescuentoCaptacion> listaCreditoDsctoCaptacion = new ArrayList<CreditoDescuentoCaptacion>();
		List<CreditoDescuentoCaptacion> listaCreditoDsctoCaptacionTemp = new ArrayList<CreditoDescuentoCaptacion>();
		CreditoDescuentoCaptacion creditoDescuentoCaptacion = null;
		try{
			creditoDescuento = boCreditoDescuento.getCreditoDescuentoPorPK(pId);
			if(creditoDescuento!=null){
				listaCreditoDsctoCaptacion = boCreditoDescuentoCaptacion.getListaCreditoDescuentoCaptacionPorPKCreditoDescuento(pId);
				for(int i=0;i<listaCreditoDsctoCaptacion.size();i++){
					creditoDescuentoCaptacion = listaCreditoDsctoCaptacion.get(i);
					if(creditoDescuentoCaptacion.getIntValor()==1){
						listaCreditoDsctoCaptacionTemp.add(creditoDescuentoCaptacion);
					}
				}
				creditoDescuento.setListaDescuento(listaCreditoDsctoCaptacionTemp);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoDescuento;
	}
	
	public List<CreditoDescuentoCaptacion> grabarListaDinamicaCreditoDescuentoCaptacion(List<CreditoDescuentoCaptacion> lstCreditoDsctoCaptacion, CreditoDescuentoId pPK) throws BusinessException{
		CreditoDescuentoCaptacion creditoDescuentoCaptacion = null;
		CreditoDescuentoCaptacionId pk = null;
		CreditoDescuentoCaptacion creditoDescuentoCaptacionTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOCUENTA), Constante.CONFIGURACION_CREDITO_DESCUENTO);
			for(int i=0;i<listaTabla.size();i++){
				creditoDescuentoCaptacion = new CreditoDescuentoCaptacion();
				pk = new CreditoDescuentoCaptacionId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntItemCreditoDescuento(pPK.getIntItemCreditoDescuento());
				pk.setIntParaTipoCaptacionCod(listaTabla.get(i).getIntIdDetalle());
				creditoDescuentoCaptacion.setIntValor(0);
				for(int j=0;j<lstCreditoDsctoCaptacion.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstCreditoDsctoCaptacion.get(j).getId().getIntParaTipoCaptacionCod())){
						creditoDescuentoCaptacion.setIntValor(1);
					}
				}
				creditoDescuentoCaptacion.setId(pk);
				creditoDescuentoCaptacionTemp = boCreditoDescuentoCaptacion.getCreditoDescuentoCaptacionPorPK(creditoDescuentoCaptacion.getId());
				if(creditoDescuentoCaptacionTemp == null){
					creditoDescuentoCaptacion = boCreditoDescuentoCaptacion.grabarCreditoDescuento(creditoDescuentoCaptacion);
				}else{
					creditoDescuentoCaptacion = boCreditoDescuentoCaptacion.modificarCreditoDescuento(creditoDescuentoCaptacion);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCreditoDsctoCaptacion;
	}
}
