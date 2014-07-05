package pe.com.tumi.credito.socio.creditos.service;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.creditos.bo.CreditoExcepcionAporteNoTransBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoExcepcionBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoExcepcionDiasCobranzaBO;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionAporteNoTrans;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionAporteNoTransId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionDiasCobranza;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionDiasCobranzaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;

public class CreditoExcepcionService {
	
	private CreditoExcepcionBO boCreditoExcepcion = (CreditoExcepcionBO)TumiFactory.get(CreditoExcepcionBO.class);
	private CreditoExcepcionAporteNoTransBO boCreditoExcepcionAporteNoTrans = (CreditoExcepcionAporteNoTransBO)TumiFactory.get(CreditoExcepcionAporteNoTransBO.class);
	private CreditoExcepcionDiasCobranzaBO boCreditoExcepcionDiasCobranza = (CreditoExcepcionDiasCobranzaBO)TumiFactory.get(CreditoExcepcionDiasCobranzaBO.class);
	
	public List<CreditoExcepcion> getListaCreditoExcepcion(CreditoId o) throws BusinessException{
		List<CreditoExcepcion> lista = null;
		try{
			lista = boCreditoExcepcion.getListaCreditoExcepcionPorPKCredito(o);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CreditoExcepcion grabarCreditoExcepcion(CreditoExcepcion pCreditoExcepcion) throws BusinessException{
		CreditoExcepcion creditoExcepcion = null;
		List<CreditoExcepcionAporteNoTrans> listaAporteNoTrans = null;
		List<CreditoExcepcionDiasCobranza> listaDiasCobranza = null;
		try{
			creditoExcepcion = boCreditoExcepcion.grabarCreditoExcepcion(pCreditoExcepcion);
			//Grabar Lista Descuento Captaciones
			listaAporteNoTrans = pCreditoExcepcion.getListaAporteNoTrans();
			if(listaAporteNoTrans!=null){
				grabarListaDinamicaCreditoExcepcionAporteNoTrans(listaAporteNoTrans, creditoExcepcion.getId());
			}
			
			//Grabar Lista Días de Cobranza
			listaDiasCobranza = pCreditoExcepcion.getListaDiasCobranza();
			if(listaDiasCobranza!=null){
				grabarListaDinamicaCreditoExcepcionDiasCobranza(listaDiasCobranza, creditoExcepcion.getId());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoExcepcion;
	}
	
	public CreditoExcepcion modificarCreditoExcepcion(CreditoExcepcion pCreditoExcepcion) throws BusinessException{
		CreditoExcepcion creditoExcepcion = null;
		List<CreditoExcepcionAporteNoTrans> listaAporteNoTrans = null;
		List<CreditoExcepcionDiasCobranza> 	listaDiasCobranza = null;
		try{
			creditoExcepcion = boCreditoExcepcion.modificarCreditoExcepcion(pCreditoExcepcion);
			
			//Grabar Lista Descuento Captaciones
			listaAporteNoTrans = pCreditoExcepcion.getListaAporteNoTrans();
			if(listaAporteNoTrans!=null){
				grabarListaDinamicaCreditoExcepcionAporteNoTrans(listaAporteNoTrans, creditoExcepcion.getId());
			}
			
			//Grabar Lista Días de Cobranza
			listaDiasCobranza = pCreditoExcepcion.getListaDiasCobranza();
			if(listaDiasCobranza!=null){
				grabarListaDinamicaCreditoExcepcionDiasCobranza(listaDiasCobranza, creditoExcepcion.getId());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoExcepcion;
	}
	
	public CreditoExcepcion getCreditoExcepcionPorIdCreditoExcepcion(CreditoExcepcionId pId) throws BusinessException {
		CreditoExcepcion creditoExcepcion = null;
		List<CreditoExcepcionAporteNoTrans> listaAporteNoTrans = new ArrayList<CreditoExcepcionAporteNoTrans>();
		List<CreditoExcepcionAporteNoTrans> listaAporteNoTransTemp = new ArrayList<CreditoExcepcionAporteNoTrans>();
		CreditoExcepcionAporteNoTrans creditoExcepcionAporteNoTrans = null;
		List<CreditoExcepcionDiasCobranza> listaDiasCobranza = new ArrayList<CreditoExcepcionDiasCobranza>();
		List<CreditoExcepcionDiasCobranza> listaDiasCobranzaTemp = new ArrayList<CreditoExcepcionDiasCobranza>();
		CreditoExcepcionDiasCobranza creditoExcepcionDiasCobranza = null;
		try{
			creditoExcepcion = boCreditoExcepcion.getCreditoExcepcionPorPK(pId);
			if(creditoExcepcion!=null){
				listaAporteNoTrans = boCreditoExcepcionAporteNoTrans.getListaAporteNoTransPorPKCreditoExcepcion(pId);
				if(listaAporteNoTrans!=null && listaAporteNoTrans.size()>0){
					for(int i=0;i<listaAporteNoTrans.size();i++){
						creditoExcepcionAporteNoTrans = listaAporteNoTrans.get(i);
						if(creditoExcepcionAporteNoTrans.getIntValor()==1){
							listaAporteNoTransTemp.add(creditoExcepcionAporteNoTrans);
						}
					}
					creditoExcepcion.setListaAporteNoTrans(listaAporteNoTransTemp);
				}
				
				listaDiasCobranza = boCreditoExcepcionDiasCobranza.getListaDiasCobranzaPorPKCreditoExcepcion(pId);
				if(listaDiasCobranza!=null && listaDiasCobranza.size()>0){
					for(int i=0;i<listaDiasCobranza.size();i++){
						creditoExcepcionDiasCobranza = listaDiasCobranza.get(i);
						if(creditoExcepcionDiasCobranza.getIntValor()==1){
							listaDiasCobranzaTemp.add(creditoExcepcionDiasCobranza);
						}
					}
					creditoExcepcion.setListaDiasCobranza(listaDiasCobranzaTemp);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoExcepcion;
	}
	
	public List<CreditoExcepcionAporteNoTrans> grabarListaDinamicaCreditoExcepcionAporteNoTrans(List<CreditoExcepcionAporteNoTrans> lstAporteNoTrans, CreditoExcepcionId pPK) throws BusinessException{
		CreditoExcepcionAporteNoTrans creditoExcepcionAporteNoTrans = null;
		CreditoExcepcionAporteNoTransId pk = null;
		CreditoExcepcionAporteNoTrans creditoExcepcionAporteNoTransTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOCUENTA), Constante.CONFIGURACION_CREDITO_EXCEPCION);
			for(int i=0;i<listaTabla.size();i++){
				creditoExcepcionAporteNoTrans = new CreditoExcepcionAporteNoTrans();
				pk = new CreditoExcepcionAporteNoTransId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntItemCreditoExcepcion(pPK.getIntItemCreditoExcepcion());
				pk.setIntParaTipoCaptacionCod(listaTabla.get(i).getIntIdDetalle());
				creditoExcepcionAporteNoTrans.setIntValor(0);
				for(int j=0;j<lstAporteNoTrans.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstAporteNoTrans.get(j).getId().getIntParaTipoCaptacionCod())){
						creditoExcepcionAporteNoTrans.setIntValor(1);
					}
				}
				creditoExcepcionAporteNoTrans.setId(pk);
				creditoExcepcionAporteNoTransTemp = boCreditoExcepcionAporteNoTrans.getAporteNoTransPorPK(creditoExcepcionAporteNoTrans.getId());
				if(creditoExcepcionAporteNoTransTemp == null){
					creditoExcepcionAporteNoTrans = boCreditoExcepcionAporteNoTrans.grabarCreditoExepcionAporteNoTrans(creditoExcepcionAporteNoTrans);
				}else{
					creditoExcepcionAporteNoTrans = boCreditoExcepcionAporteNoTrans.modificarCreditoExepcionAporteNoTrans(creditoExcepcionAporteNoTrans);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstAporteNoTrans;
	}
	
	public List<CreditoExcepcionDiasCobranza> grabarListaDinamicaCreditoExcepcionDiasCobranza(List<CreditoExcepcionDiasCobranza> lstDiasCobranza, CreditoExcepcionId pPK) throws BusinessException{
		CreditoExcepcionDiasCobranza creditoExcepcionDiasCobranza = null;
		CreditoExcepcionDiasCobranzaId pk = null;
		CreditoExcepcionDiasCobranza creditoExcepcionDiasCobranzaTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_DIAS_COBRANZA));
			for(int i=0;i<listaTabla.size();i++){
				creditoExcepcionDiasCobranza = new CreditoExcepcionDiasCobranza();
				pk = new CreditoExcepcionDiasCobranzaId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntItemCreditoExcepcion(pPK.getIntItemCreditoExcepcion());
				pk.setIntParaDiasCobranzaCod(listaTabla.get(i).getIntIdDetalle());
				creditoExcepcionDiasCobranza.setIntValor(0);
				for(int j=0;j<lstDiasCobranza.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstDiasCobranza.get(j).getId().getIntParaDiasCobranzaCod())){
						creditoExcepcionDiasCobranza.setIntValor(1);
					}
				}
				creditoExcepcionDiasCobranza.setId(pk);
				creditoExcepcionDiasCobranzaTemp = boCreditoExcepcionDiasCobranza.getAporteNoTransPorPK(creditoExcepcionDiasCobranza.getId());
				if(creditoExcepcionDiasCobranzaTemp == null){
					creditoExcepcionDiasCobranza = boCreditoExcepcionDiasCobranza.grabarCreditoExepcionDiasCobranza(creditoExcepcionDiasCobranza);
				}else{
					creditoExcepcionDiasCobranza = boCreditoExcepcionDiasCobranza.modificarCreditoExepcionDiasCobranza(creditoExcepcionDiasCobranza);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstDiasCobranza;
	}
}