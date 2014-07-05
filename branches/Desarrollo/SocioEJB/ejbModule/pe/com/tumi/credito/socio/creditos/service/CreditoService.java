package pe.com.tumi.credito.socio.creditos.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.soap.providers.com.Log;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoComp;
import pe.com.tumi.credito.socio.creditos.bo.CondicionCreditoBO;
import pe.com.tumi.credito.socio.creditos.bo.CondicionHabilBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoDescuentoBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoDescuentoCaptacionBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoExcepcionBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoGarantiaBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoInteresBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaCondicionHabilBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaCondicionLaboralBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaCondicionSocioBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaSituacionLaboralBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTopeCaptacionBO;
import pe.com.tumi.credito.socio.creditos.bo.FinalidadBO;
import pe.com.tumi.credito.socio.creditos.domain.CondicionComp;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilId;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteresId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTopeCaptacion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTopeCaptacionId;
import pe.com.tumi.credito.socio.creditos.domain.Finalidad;
import pe.com.tumi.credito.socio.creditos.domain.FinalidadId;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantia;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
	
public class CreditoService {
	
	private CreditoBO boCredito = (CreditoBO)TumiFactory.get(CreditoBO.class);
	private CondicionCreditoBO boCondicionCredito = (CondicionCreditoBO)TumiFactory.get(CondicionCreditoBO.class);
	private CondicionHabilBO boCondicionHabil = (CondicionHabilBO)TumiFactory.get(CondicionHabilBO.class);
	private CreditoTopeCaptacionBO boCreditoTopeCaptacion = (CreditoTopeCaptacionBO)TumiFactory.get(CreditoTopeCaptacionBO.class);
	private CreditoInteresBO boCreditoInteres = (CreditoInteresBO)TumiFactory.get(CreditoInteresBO.class);
	private FinalidadBO boFinalidad = (FinalidadBO)TumiFactory.get(FinalidadBO.class);
	private CreditoDescuentoBO boCreditoDescuento = (CreditoDescuentoBO)TumiFactory.get(CreditoDescuentoBO.class);
	private CreditoExcepcionBO boCreditoExcepcion = (CreditoExcepcionBO)TumiFactory.get(CreditoExcepcionBO.class);
	private CreditoGarantiaBO boCreditoGarantia = (CreditoGarantiaBO)TumiFactory.get(CreditoGarantiaBO.class);
	
	private CreditoTipoGarantiaBO boCreditoTipoGarantia = (CreditoTipoGarantiaBO)TumiFactory.get(CreditoTipoGarantiaBO.class);
	private CreditoTipoGarantiaCondicionLaboralBO boCreditoTipoGarantiaCondicionLaboral = (CreditoTipoGarantiaCondicionLaboralBO)TumiFactory.get(CreditoTipoGarantiaCondicionLaboralBO.class);
	private CreditoTipoGarantiaSituacionLaboralBO boCreditoTipoGarantiaSituacionLaboral = (CreditoTipoGarantiaSituacionLaboralBO)TumiFactory.get(CreditoTipoGarantiaSituacionLaboralBO.class);
	private CreditoTipoGarantiaCondicionHabilBO boCreditoTipoGarantiaCondicionHabil = (CreditoTipoGarantiaCondicionHabilBO)TumiFactory.get(CreditoTipoGarantiaCondicionHabilBO.class);
	private CreditoTipoGarantiaCondicionSocioBO boCreditoTipoGarantiaCondicionSocio = (CreditoTipoGarantiaCondicionSocioBO)TumiFactory.get(CreditoTipoGarantiaCondicionSocioBO.class);

	private CreditoDescuentoCaptacionBO boCreditoDescuentoCaptacion = (CreditoDescuentoCaptacionBO)TumiFactory.get(CreditoDescuentoCaptacionBO.class);
	
	//getListaCreditoDescuentoCaptacionPorPKCreditoDescuento
	
	/**
	 * Recupera los creditos que cumplen con algun campo del credito
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<CreditoComp> getListaCreditoCompDeBusquedaCredito(Credito o) throws BusinessException{
		CreditoComp dto = null;
		Credito dtoCredito = null;
		List<CreditoComp> lista = null;
		List<Credito> listaCredito = null;
		List<CondicionCredito> listaCondicion = null;
		List<CondicionHabil> listaCondicionHabil = null;
		List<CondicionHabil> listaTipoCondSocioTemp = null;
		List<Tabla> listaTablaCondSocio = null;
		TablaFacadeRemote tablaFacade = null;
		
		
		try{
			listaCredito = boCredito.getListaCreditoDeBusqueda(o);
			
			if(listaCredito != null && listaCredito.size()>0){
				tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				lista = new ArrayList<CreditoComp>();
				
				for (Credito credito : listaCredito){
				//for(int i=0;i<listaCredito.size();i++){
					dto = new CreditoComp();
					dtoCredito =credito;
					
					listaCondicion = boCondicionCredito.getListaCondicionSocioPorPKCredito(dtoCredito.getId());

					if(listaCondicion != null && ! listaCondicion.isEmpty()){
						listaTablaCondSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
						String csvPkCondicionSocio = null;
						for(int j=0;j<listaCondicion.size();j++){
							for(int k=0;k<listaTablaCondSocio.size(); k++){
								if(listaCondicion.get(j).getId().getIntParaCondicionSocioCod().equals(listaTablaCondSocio.get(k).getIntIdDetalle())){
									if(csvPkCondicionSocio == null)
										csvPkCondicionSocio = String.valueOf(listaTablaCondSocio.get(k).getStrDescripcion());
									else
										csvPkCondicionSocio = csvPkCondicionSocio + " / " +listaTablaCondSocio.get(k).getStrDescripcion();
								}
							}
						}
						credito.setListaCondicion(listaCondicion);
						dto.setStrCondicionSocio(listaCondicion.size()==listaTablaCondSocio.size()?"TODOS":csvPkCondicionSocio);	
					}
					
					listaCondicionHabil = boCondicionHabil.getListaPorPKCredito(credito.getId());
					
					if(listaCondicionHabil != null && !listaCondicionHabil.isEmpty()){
						listaTipoCondSocioTemp = new ArrayList<CondicionHabil>();
						for (CondicionHabil condHabil : listaCondicionHabil) {
							if(condHabil.getIntValor()==1){
								listaTipoCondSocioTemp.add(condHabil);
							}		
						}
						credito.setListaCondicionHabil(listaTipoCondSocioTemp);
						
					}
					
					dto.setCredito(dtoCredito);
					lista.add(dto);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Credito grabarCredito(Credito pCredito) throws BusinessException{
		Credito credito = null;
		List<CondicionCredito> listaCondicionCredito = null;
		List<CondicionHabil> listaCondicionHabil = null;
		List<CreditoTopeCaptacion> listaRangoMontoMin = null;
		List<CreditoTopeCaptacion> listaRangoMontoMax = null;
		List<CreditoInteres> listaCreditoInteres = null;
		List<Finalidad> listaFinalidad = null;
		try{
			credito = boCredito.grabarCredito(pCredito);
			
			listaCondicionCredito = pCredito.getListaCondicion();
			//Grabar Lista Condición de Captación
			if(listaCondicionCredito!=null){
				grabarListaDinamicaCondicionCredito(listaCondicionCredito, credito.getId());
			}
			
			listaCondicionHabil = pCredito.getListaCondicionHabil();
			//Grabar Lista Condición Hábil
			if(listaCondicionHabil!=null){
				grabarListaDinamicaCondicionHabil(listaCondicionHabil, credito.getId());
			}
			
			//Grabar Lista Rango de Montos (mínimo)
			listaRangoMontoMin = pCredito.getListaRangoMontoMin();
			if(listaRangoMontoMin!=null){
				grabarListaDinamicaRangoMonto(listaRangoMontoMin, credito.getId());
			}
			
			//Grabar Lista Rango de Montos (máximo)
			listaRangoMontoMax = pCredito.getListaRangoMontoMax();
			if(listaRangoMontoMax!=null){
				grabarListaDinamicaRangoMonto(listaRangoMontoMax, credito.getId());
			}
			
			//Grabar Lista Crédito Interés
			listaCreditoInteres = pCredito.getListaCreditoInteres();
			if(listaCreditoInteres!=null){
				grabarListaDinamicaCreditoInteres(listaCreditoInteres, credito.getId());
			}
			
			//Grabar Lista Finalidad
			listaFinalidad = pCredito.getListaFinalidad();
			if(listaFinalidad!=null){
				grabarListaDinamicaFinalidad(listaFinalidad, credito.getId());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return credito;
	}
	
	public Credito modificarCredito(Credito pCredito) throws BusinessException{
		Credito credito = null;
		List<CondicionCredito> listaCondicionCredito = null;
		List<CondicionHabil> listaCondicionHabil = null;
		List<CreditoTopeCaptacion> listaRangoMontoMin = null;
		List<CreditoTopeCaptacion> listaRangoMontoMax = null;
		List<CreditoInteres> listaCreditoInteres = null;
		List<Finalidad> listaFinalidad = null;
		try{
			credito = boCredito.modificarCredito(pCredito);
			
			listaCondicionCredito = pCredito.getListaCondicion();
			//Grabar Lista Condición de Crédito
			if(listaCondicionCredito!=null){
				grabarListaDinamicaCondicionCredito(listaCondicionCredito, credito.getId());
			}
			
			listaCondicionHabil = pCredito.getListaCondicionHabil();
			//Grabar Lista Condición Hábil
			if(listaCondicionHabil!=null){
				grabarListaDinamicaCondicionHabil(listaCondicionHabil, credito.getId());
			}
			
			//Grabar Lista Rango de Montos (mínimo)
			listaRangoMontoMin = pCredito.getListaRangoMontoMin();
			if(listaRangoMontoMin!=null){
				//if(listaRangoMontoMin.isEmpty()){
					//CreditoTopeCaptacionId pPK = new CreditoTopeCaptacionId();
					//pPK.setIntItemCredito(credito.getId().getIntItemCredito());
					//pPK.setIntParaTipoCaptacion(0); // 1 3 5 10
					//pPK.setIntParaTipoCreditoCod(credito.getId().getIntParaTipoCreditoCod());
					//pPK.setIntParaTipoMinMaxCod(Constante.PARAM_T_MINIMO);
					//pPK.setIntPersEmpresaPk(credito.getId().getIntPersEmpresaPk());
					
					// borrar boCreditoTopeCaptacion
					//boCreditoTopeCaptacion.deletePorPk(pPK);
				//}else{
					grabarListaDinamicaRangoMonto(listaRangoMontoMin, credito.getId());
				//}
				
				
			}
			
			//Grabar Lista Rango de Montos (máximo)
			listaRangoMontoMax = pCredito.getListaRangoMontoMax();
			if(listaRangoMontoMax!=null){
				//if(listaRangoMontoMax.isEmpty()){
					// borrar
					//CreditoTopeCaptacionId pPK = new CreditoTopeCaptacionId();
					//pPK.setIntItemCredito(credito.getId().getIntItemCredito());
					//pPK.setIntParaTipoCaptacion(null); // 1 3 5 10
					//pPK.setIntParaTipoCreditoCod(credito.getId().getIntParaTipoCreditoCod());
					//pPK.setIntParaTipoMinMaxCod(Constante.PARAM_T_MAXIMO);
					//pPK.setIntPersEmpresaPk(credito.getId().getIntPersEmpresaPk());
					
					//boCreditoTopeCaptacion.deletePorPk(pPK);
				//}else{
					grabarListaDinamicaRangoMonto(listaRangoMontoMax, credito.getId());
				//}	
			}
			
			//Grabar Lista Crédito Interés
			listaCreditoInteres = pCredito.getListaCreditoInteres();
			if(listaCreditoInteres!=null){
				grabarListaDinamicaCreditoInteres(listaCreditoInteres, credito.getId());
			}
			
			//Grabar Lista Finalidad
			listaFinalidad = pCredito.getListaFinalidad();
			if(listaFinalidad!=null){
				//grabarListaDinamicaFinalidad(listaFinalidad, credito.getId(), "U");
				grabarListaDinamicaFinalidad(listaFinalidad, credito.getId());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return credito;
	}
	
	public Credito getCreditoPorIdCredito(CreditoId pId) throws BusinessException {
		Credito credito = null;
		CondicionComp condicionComp = null;
		CreditoGarantiaId creditoGarantiaPersonalId = null;
		//TablaFacadeRemote tablaFacade = null;
		//List<Tabla> listaTablaCondicion = null;
		CondicionCredito condicionCredito = null;
		List<CondicionCredito> listaCondicion = null;
		List<CondicionCredito> listaCondicionTemp = null;
		List<CondicionHabil> listaTipoCondSocio = new ArrayList<CondicionHabil>();
		List<CondicionHabil> listaTipoCondSocioTemp = new ArrayList<CondicionHabil>();
		List<CreditoTopeCaptacion> listaCreditoTopeCaptacionMin = new ArrayList<CreditoTopeCaptacion>();
		List<CreditoTopeCaptacion> listaCreditoTopeCaptacionMinTemp = new ArrayList<CreditoTopeCaptacion>();
		List<CreditoTopeCaptacion> listaCreditoTopeCaptacionMax = new ArrayList<CreditoTopeCaptacion>();
		List<CreditoTopeCaptacion> listaCreditoTopeCaptacionMaxTemp = new ArrayList<CreditoTopeCaptacion>();
		List<Finalidad> listaFinalidad = new ArrayList<Finalidad>();
		List<Finalidad> listaFinalidadTemp = new ArrayList<Finalidad>();
		List<CreditoInteres> listaCreditoInteres = new ArrayList<CreditoInteres>();
		List<CreditoDescuento> listaCreditoDescuento = new ArrayList<CreditoDescuento>();
		List<CreditoExcepcion> listaCreditoExcepcion = new ArrayList<CreditoExcepcion>();
		List<CreditoGarantia> listaCreditoGarantiaPersonal = new ArrayList<CreditoGarantia>();
		List<CreditoGarantia> listaCreditoGarantiaReal = new ArrayList<CreditoGarantia>();
		List<CreditoGarantia> listaCreditoGarantiaAutoliquidable = new ArrayList<CreditoGarantia>();
		List<CreditoGarantia> listaCreditoGarantiaRapidaRealiz = new ArrayList<CreditoGarantia>();
		CreditoTopeCaptacion creditoTopeCaptacionMin = null;
		CreditoTopeCaptacion creditoTopeCaptacionMax = null;
		CondicionHabil condicionHabil = null;
		Finalidad finalidad = null;
		List<CreditoDescuentoCaptacion> listaCreditoDescCapt = new ArrayList<CreditoDescuentoCaptacion>();
		try{
			credito = boCredito.getCreditoPorPK(pId);
			if(credito!=null){
				
				///----------------------------||||
				
				creditoGarantiaPersonalId = new CreditoGarantiaId();
				//tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				//listaTablaCondicion = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
				listaCondicion = boCondicionCredito.getListaPorPKCredito(pId);
				listaCondicionTemp = new ArrayList<CondicionCredito>();
				if(listaCondicion!=null && !listaCondicion.isEmpty()){
					for(int i=0;i<listaCondicion.size();i++){
						condicionCredito = listaCondicion.get(i);
						if(condicionCredito.getIntValor()==1){
							listaCondicionTemp.add(condicionCredito);
						}
					}
				}
				credito.setListaCondicion(listaCondicionTemp);
				
				/*for(int i=0;i<listaTablaCondicion.size();i++){
					for(int j=0;j<listaCondicion.size();j++){
						condicionComp = new CondicionComp();
						if(listaTablaCondicion.get(i).getIntIdDetalle().equals(listaCondicion.get(j).getId().getIntParaCondicionSocioCod())){
							condicionComp.setChkSocio(listaCondicion.get(j).getIntValor()==1);
							condicionComp.setTabla(listaTablaCondicion.get(i));
							condicionComp.setCondicion(listaCondicion.get(j));
							condicionComp.getCondicion().setId(listaCondicion.get(j).getId());
							listaCondicionComp.add(condicionComp);
						}
					}
				}*/
				//credito.setListaCondicion(listaCondicionTemp);
				//credito.setListaCondicionComp(listaCondicionComp);
				
				listaTipoCondSocio = boCondicionHabil.getListaPorPKCredito(pId);
				if(listaTipoCondSocio!=null && !listaTipoCondSocio.isEmpty()){
					for(int i=0;i<listaTipoCondSocio.size();i++){
						condicionHabil = listaTipoCondSocio.get(i);
						if(condicionHabil.getIntValor()==1){
							listaTipoCondSocioTemp.add(condicionHabil);
						}
					}
				}
				credito.setListaCondicionHabil(listaTipoCondSocioTemp);
				
				listaCreditoTopeCaptacionMin = boCreditoTopeCaptacion.getListaPorPKCreditoTipoMin(pId);
				if(listaCreditoTopeCaptacionMin != null && !listaCreditoTopeCaptacionMin.isEmpty()){
					for(int i=0;i<listaCreditoTopeCaptacionMin.size();i++){
						creditoTopeCaptacionMin = listaCreditoTopeCaptacionMin.get(i);
						if(creditoTopeCaptacionMin.getIntValor()==1){
							listaCreditoTopeCaptacionMinTemp.add(creditoTopeCaptacionMin);
						}
					}	
				}
				credito.setListaRangoMontoMin(listaCreditoTopeCaptacionMinTemp);
				
				
				listaCreditoTopeCaptacionMax = boCreditoTopeCaptacion.getListaPorPKCreditoTipoMax(pId);
				if(listaCreditoTopeCaptacionMax !=null && !listaCreditoTopeCaptacionMax.isEmpty()){
					for(int i=0;i<listaCreditoTopeCaptacionMax.size();i++){
						creditoTopeCaptacionMax = listaCreditoTopeCaptacionMax.get(i);
						if(creditoTopeCaptacionMax.getIntValor()==1){
							listaCreditoTopeCaptacionMaxTemp.add(creditoTopeCaptacionMax);
						}
					}
				}
				credito.setListaRangoMontoMax(listaCreditoTopeCaptacionMaxTemp);
				
				
				listaCreditoInteres = boCreditoInteres.getListaPorPKCredito(pId);
				if(listaCreditoInteres!=null && listaCreditoInteres.size()>0){
					credito.setListaCreditoInteres(listaCreditoInteres);
				}
				
				listaFinalidad = boFinalidad.getListaFinalidadPorPKCredito(pId);
				if(listaFinalidad != null && !listaFinalidad.isEmpty()){
					for(int i=0;i<listaFinalidad.size();i++){
						finalidad = listaFinalidad.get(i);
						if(finalidad.getIntValor()==1){
							listaFinalidadTemp.add(finalidad);
						}
					}
				}
				credito.setListaFinalidad(listaFinalidadTemp);
				// CGD - 05.12.2013
				listaCreditoDescuento = boCreditoDescuento.getListaCreditoDescuentoPorPKCredito(pId);
				if(listaCreditoDescuento!=null && listaCreditoDescuento.size()>0){
				List<CreditoDescuento> lstCreditoDescuentoFinal = new ArrayList<CreditoDescuento>();
				
					for (CreditoDescuento creditoDescuento : listaCreditoDescuento) {
						List<CreditoDescuentoCaptacion> lstCredDescCap = null;
						lstCredDescCap = boCreditoDescuentoCaptacion.getListaCreditoDescuentoCaptacionPorPKCreditoDescuento(creditoDescuento.getId());
						if(lstCredDescCap != null && !lstCredDescCap.isEmpty()){
							creditoDescuento.setListaDescuento(lstCredDescCap);
						}
						lstCreditoDescuentoFinal.add(creditoDescuento);
					}
					credito.setListaDescuento(lstCreditoDescuentoFinal);
				}
				
				listaCreditoExcepcion = boCreditoExcepcion.getListaCreditoExcepcionPorPKCredito(pId);
				if(listaCreditoExcepcion!=null && listaCreditoExcepcion.size()>0){
					credito.setListaExcepcion(listaCreditoExcepcion);
				}
				
				creditoGarantiaPersonalId.setIntPersEmpresaPk(pId.getIntPersEmpresaPk());
				creditoGarantiaPersonalId.setIntParaTipoCreditoCod(pId.getIntParaTipoCreditoCod());
				creditoGarantiaPersonalId.setIntItemCredito(pId.getIntItemCredito());
				creditoGarantiaPersonalId.setIntParaTipoGarantiaCod(Constante.CREDITOS_GARANTIA_PERSONAL);
				listaCreditoGarantiaPersonal = boCreditoGarantia.getListaCreditoGarantiaPorPKCreditoGarantia(creditoGarantiaPersonalId);
				if(listaCreditoGarantiaPersonal!=null && listaCreditoGarantiaPersonal.size()>0){
					credito.setListaGarantiaPersonal(listaCreditoGarantiaPersonal);
				}
				
				creditoGarantiaPersonalId.setIntParaTipoGarantiaCod(Constante.CREDITOS_GARANTIA_REAL);
				listaCreditoGarantiaReal = boCreditoGarantia.getListaCreditoGarantiaPorPKCreditoGarantia(creditoGarantiaPersonalId);
				if(listaCreditoGarantiaReal!=null && listaCreditoGarantiaReal.size()>0){
					credito.setListaGarantiaReal(listaCreditoGarantiaReal);
				}
				
				creditoGarantiaPersonalId.setIntParaTipoGarantiaCod(Constante.CREDITOS_AUTOLIQUIDABLE);
				listaCreditoGarantiaAutoliquidable = boCreditoGarantia.getListaCreditoGarantiaPorPKCreditoGarantia(creditoGarantiaPersonalId);
				if(listaCreditoGarantiaAutoliquidable!=null && listaCreditoGarantiaAutoliquidable.size()>0){
					credito.setListaGarantiaAutoliquidable(listaCreditoGarantiaAutoliquidable);
				}
				
				creditoGarantiaPersonalId.setIntParaTipoGarantiaCod(Constante.CREDITOS_RAPIDA_REALIZACION);
				listaCreditoGarantiaRapidaRealiz = boCreditoGarantia.getListaCreditoGarantiaPorPKCreditoGarantia(creditoGarantiaPersonalId);
				if(listaCreditoGarantiaRapidaRealiz!=null && listaCreditoGarantiaRapidaRealiz.size()>0){
					credito.setListaGarantiaRapidaRealizacion(listaCreditoGarantiaRapidaRealiz);
				}
			
				
				/////------------------|||||
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return credito;
	}
	
	/*public List<CondicionComp> grabarListaDinamicaCondicionComp(List<CondicionComp> listCondicionComp, CreditoId pPK) throws BusinessException{
		CondicionCredito condicionCredito = null;
		CondicionComp condicionComp = null;
		CondicionCreditoId pk = null;
		CondicionCredito condicionTemp = null;
		try{
			for(int i=0; i<listCondicionComp.size(); i++){
				condicionComp = (CondicionComp) listCondicionComp.get(i);
				if(condicionComp.getCondicion()==null){
					pk = new CondicionCreditoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
					pk.setIntItemCredito(pPK.getIntItemCredito());
					pk.setIntParaCondicionSocioCod(listCondicionComp.get(i).getTabla().getIntIdDetalle());
					condicionComp.setCondicion(new CondicionCredito());
					condicionComp.getCondicion().setId(new CondicionCreditoId());
					condicionComp.getCondicion().setId(pk);
					condicionComp.getCondicion().setIntValor(listCondicionComp.get(i).getChkSocio()==true?1:0);
					condicionCredito = boCondicionCredito.grabarCondicion(condicionComp.getCondicion());
				}else{
					condicionTemp = boCondicionCredito.getCondicionPorPK(condicionComp.getCondicion().getId());
					if(condicionTemp == null){
						condicionCredito = boCondicionCredito.grabarCondicion(condicionComp.getCondicion());
					}else{
						condicionComp.getCondicion().setIntValor(listCondicionComp.get(i).getChkSocio()==true?1:0);
						condicionCredito = boCondicionCredito.modificarCondicion(condicionComp.getCondicion());
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listCondicionComp;
	}*/
	
	public List<CondicionCredito> grabarListaDinamicaCondicionCredito(List<CondicionCredito> lstCondicionCredito, CreditoId pPK) throws BusinessException{
		CondicionCredito condicionCredito = null;
		CondicionCreditoId pk = null;
		CondicionCredito condicionCreditoTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			//CGD-032013-06
			//listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_FINALIDAD_CREDITO));
			listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
			
			for(int i=0;i<listaTabla.size();i++){
				condicionCredito = new CondicionCredito();
				pk = new CondicionCreditoId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntParaCondicionSocioCod(listaTabla.get(i).getIntIdDetalle());
				condicionCredito.setIntValor(0);
				for(int j=0;j<lstCondicionCredito.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstCondicionCredito.get(j).getId().getIntParaCondicionSocioCod())){
						condicionCredito.setIntValor(1);
						break;
					}
				}
				condicionCredito.setId(pk);
				condicionCreditoTemp = boCondicionCredito.getCondicionPorPK(condicionCredito.getId());
				if(condicionCreditoTemp == null){
					condicionCredito = boCondicionCredito.grabarCondicion(condicionCredito);
				}else{
					condicionCredito = boCondicionCredito.modificarCondicion(condicionCredito);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCondicionCredito;
	}
	
	public List<CondicionHabil> grabarListaDinamicaCondicionHabil(List<CondicionHabil> lstCondicionHabil, CreditoId pPK) throws BusinessException{
		CondicionHabil condicionHabil = null;
		CondicionHabilId pk = null;
		CondicionHabil condicionHabilTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_CONDSOCIO));
			for(int i=0;i<listaTabla.size();i++){
				condicionHabil = new CondicionHabil();
				pk = new CondicionHabilId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntParaTipoHabilCod(listaTabla.get(i).getIntIdDetalle());
				condicionHabil.setIntValor(0);
				for(int j=0;j<lstCondicionHabil.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstCondicionHabil.get(j).getId().getIntParaTipoHabilCod())){
						condicionHabil.setIntValor(1);
					}
				}
				condicionHabil.setId(pk);
				condicionHabilTemp = boCondicionHabil.getCondicionHabilPorPK(condicionHabil.getId());
				if(condicionHabilTemp == null){
					condicionHabil = boCondicionHabil.grabarCondicionHabil(condicionHabil);
				}else{
					condicionHabil = boCondicionHabil.modificarCondicionHabil(condicionHabil);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCondicionHabil;
	}
	
	public List<CreditoTopeCaptacion> grabarListaDinamicaRangoMonto(List<CreditoTopeCaptacion> lstRangoMonto, CreditoId pPK) throws BusinessException{
		CreditoTopeCaptacion creditoTopeCaptacion = null;
		CreditoTopeCaptacionId pk = null;
		CreditoTopeCaptacion creditoTopeCaptacionTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOCUENTA), Constante.CONFIGURACION_CREDITO);
			for(int i=0;i<listaTabla.size();i++){
				creditoTopeCaptacion = new CreditoTopeCaptacion();
				pk = new CreditoTopeCaptacionId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntParaTipoCaptacion(listaTabla.get(i).getIntIdDetalle());
				creditoTopeCaptacion.setIntValor(0);
				for(int j=0;j<lstRangoMonto.size();j++){
					pk.setIntParaTipoMinMaxCod(lstRangoMonto.get(j).getId().getIntParaTipoMinMaxCod());
					if(listaTabla.get(i).getIntIdDetalle().equals(lstRangoMonto.get(j).getId().getIntParaTipoCaptacion())){
						creditoTopeCaptacion.setIntValor(1);
						break;
					}
				}
				creditoTopeCaptacion.setId(pk);
				creditoTopeCaptacionTemp = boCreditoTopeCaptacion.getCreditoTopeCaptacionPorPK(creditoTopeCaptacion.getId());
				if(creditoTopeCaptacionTemp == null){
					creditoTopeCaptacion = boCreditoTopeCaptacion.grabarCreditoTopeCaptacion(creditoTopeCaptacion);
				}else{
					creditoTopeCaptacion = boCreditoTopeCaptacion.modificarCreditoTopeCaptacion(creditoTopeCaptacion);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRangoMonto;
	}
	 
	public List<CreditoInteres> grabarListaDinamicaCreditoInteres(List<CreditoInteres> lstCreditoInteres, CreditoId pPK) throws BusinessException{
		CreditoInteres creditoInteres = null;
		CreditoInteresId pk = null;
		CreditoInteres creditoInteresTemp = null;
		try{
			for(int i=0; i<lstCreditoInteres.size(); i++){
				creditoInteres = (CreditoInteres) lstCreditoInteres.get(i);
				if(creditoInteres.getId()==null || creditoInteres.getId().getIntItemInteres()==null){
					pk = new CreditoInteresId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
					pk.setIntItemCredito(pPK.getIntItemCredito());
					pk.setIntItemInteres(creditoInteres.getId().getIntParaTipoCreditoCod());
					creditoInteres.setId(pk);
					creditoInteres = boCreditoInteres.grabarCreditoInteres(creditoInteres);
				}else{
					creditoInteresTemp = boCreditoInteres.getCreditoInteresPorPK(creditoInteres.getId());
					if(creditoInteresTemp == null){
						creditoInteres = boCreditoInteres.grabarCreditoInteres(creditoInteres);
					}else{
						creditoInteres = boCreditoInteres.modificarCreditoInteres(creditoInteres);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCreditoInteres;
	}
	
	public List<Finalidad> grabarListaDinamicaFinalidad(List<Finalidad> lstFinalidad, CreditoId pPK) throws BusinessException{
		Finalidad finalidad = null;
		FinalidadId pk = null;
		Finalidad finalidadTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_FINALIDAD_CREDITO));
			for(int i=0;i<listaTabla.size();i++){
				finalidad = new Finalidad();
				pk = new FinalidadId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntParaFinalidadCod(listaTabla.get(i).getIntIdDetalle());
				finalidad.setIntValor(0);
				for(int j=0;j<lstFinalidad.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstFinalidad.get(j).getId().getIntParaFinalidadCod())){
						finalidad.setIntValor(1);
					}
				}
				finalidad.setId(pk);
				finalidadTemp = boFinalidad.getFinalidadPorPK(finalidad.getId());
				if(finalidadTemp == null){
					finalidad = boFinalidad.grabarFinalidad(finalidad);
				}else{
					finalidad = boFinalidad.modificarFinalidad(finalidad);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstFinalidad;
	}
	
	public Credito getConfCreditoPorPkCredito(CreditoId pId) throws BusinessException {
		Credito credito = null;
		try {
			credito = boCredito.getCreditoPorPK(pId);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return credito;
	
	}
	
	
	public List<Credito> getlistaCreditoPorCredito(Credito pCredito) throws BusinessException {
		 List<Credito> lstCreditosRecuperados = new ArrayList<Credito>();
		try{
			lstCreditosRecuperados = boCredito.getlistaCreditoPorCredito(pCredito);

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCreditosRecuperados;
	}
	
	/**
	 * 
	 * @param pPk
	 * @param socioComp
	 * @return
	 * @throws BusinessException
	 */
	public List<CreditoInteres> getlistaCreditoInteresPorPKCreditoFiltradoPorCondicionSocio(CreditoId pPk, SocioComp socioComp) throws BusinessException{
		 List<CreditoInteres> lstCreditosInteres = null;
		 List<CreditoInteres> lstCreditosRecuperados = null;
			try{
				lstCreditosInteres = boCreditoInteres.getListaPorPKCredito(pPk);
				if(lstCreditosInteres != null  && !lstCreditosInteres.isEmpty()){
					
					for (CreditoInteres interes : lstCreditosInteres) {
						if(interes.getIntParaTipoSocio().compareTo(socioComp.getSocio().getSocioEstructura().getIntTipoSocio())==0){
							lstCreditosRecuperados = new ArrayList<CreditoInteres>();
							lstCreditosRecuperados.add(interes);
							break;
						}
					}	
				}

			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return lstCreditosRecuperados;
	}
	
	
	
	 public List<CreditoComp> getListaCreditoValidarSolicitud(Credito o) throws BusinessException{
			CreditoComp dto = null;
			List<CreditoComp> lista = null;
			List<Credito> listaCredito = null;
			List<CondicionCredito> listaCondicion = null;
			List<CondicionHabil> listaCondicionHabil = null;
			List<CondicionHabil> listaTipoCondSocioTemp = null;

			try{
				listaCredito =  boCredito.getlistaCreditoPorCredito(o);
				
				if(listaCredito != null && !listaCredito.isEmpty()){
					lista = new ArrayList<CreditoComp>();
					
					for (Credito credito : listaCredito){
						dto = new CreditoComp();
						
						listaCondicion = boCondicionCredito.getListaCondicionSocioPorPKCredito(credito.getId());
						if(listaCondicion != null && !listaCondicion.isEmpty()){
							credito.setListaCondicion(listaCondicion);
						}

						listaCondicionHabil = boCondicionHabil.getListaPorPKCredito(credito.getId());
						if(listaCondicionHabil != null && !listaCondicionHabil.isEmpty()){
							listaTipoCondSocioTemp = new ArrayList<CondicionHabil>();
							for (CondicionHabil condHabil : listaCondicionHabil) {
								if(condHabil.getIntValor()==1){
									listaTipoCondSocioTemp.add(condHabil);
								}		
							}
							credito.setListaCondicionHabil(listaTipoCondSocioTemp);	
						}
						
						dto.setCredito(credito);
						lista.add(dto);
					}
				}
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return lista;
		}
	 
	 
	 
	 /**
	  * Se recupera Credito garantia del tipo personal.
	  * @param Credito o
	  * @return CreditoGarantia
	  * @throws BusinessException
	  */
	 public CreditoGarantia recuperarGarantiasPersonales(Credito o) throws BusinessException{
		 
		 List<CreditoGarantia> listaCreditoGarantia = null;
		 CreditoGarantia creditoGarantiaPersonal = null;
		 List<CreditoTipoGarantia> listaCreditoTipoGarantia = null;
		 List<CreditoTipoGarantia> listaCreditoTipoGarantiaResult = null;
		 
		 List<SituacionLaboralTipoGarantia> listaSituacionLaboralTipoGarantia = null;
		 List<CondicionHabilTipoGarantia> listaCondicionHabilTipoGarantia = null;
		 List<CondicionSocioTipoGarantia> listaCondicionSocioTipoGarantia = null;
		 List<CondicionLaboralTipoGarantia> listaCondicionLaboralTipoGarantia = null;
		 
		 CreditoGarantiaId creditoGarantiaId = new CreditoGarantiaId();
		 CreditoTipoGarantiaId creditoTipoGarantiaId = new CreditoTipoGarantiaId();
		// CreditoTipoGarantia creditoTipoGarantiaActiva = new CreditoTipoGarantia();
		// CreditoTipoGarantia creditoTipoGarantia = new CreditoTipoGarantia();

		 try {
			 creditoGarantiaId.setIntItemCredito(o.getId().getIntItemCredito());
			 creditoGarantiaId.setIntParaTipoCreditoCod(o.getId().getIntParaTipoCreditoCod());
			 creditoGarantiaId.setIntItemCredito(o.getId().getIntItemCredito());
			 creditoGarantiaId.setIntParaTipoGarantiaCod(Constante.CREDITOS_GARANTIA_PERSONAL);
			 creditoGarantiaId.setIntPersEmpresaPk(o.getId().getIntPersEmpresaPk());

			 listaCreditoGarantia = boCreditoGarantia.getListaCreditoGarantiaPorPKCreditoGarantia(creditoGarantiaId);
			 if(listaCreditoGarantia != null && !listaCreditoGarantia.isEmpty()){
				 CreditoGarantia creditoGarantiaActiva = null;
				 for (CreditoGarantia creditoGarantia : listaCreditoGarantia) {
					 if(creditoGarantia.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						 creditoGarantiaActiva = new CreditoGarantia();
						 creditoGarantiaActiva = creditoGarantia;
						 break;
					 }
				}
				 creditoGarantiaId = creditoGarantiaActiva.getId();
				 
				 creditoGarantiaPersonal = new CreditoGarantia();
				 creditoGarantiaPersonal.setId(new CreditoGarantiaId());
				 creditoGarantiaPersonal.setId(creditoGarantiaId);
				 
				 // Recuperamos Credito Tipo Garantia
				 listaCreditoTipoGarantia = boCreditoTipoGarantia.getListaCreditoTipoGarantiaPorPKCreditoGarantia(creditoGarantiaPersonal.getId());
				 
				 // 12.09.2013 - CGD
				 /* if(listaCreditoTipoGarantia != null && !listaCreditoTipoGarantia.isEmpty()){
					 
					 CreditoTipoGarantia creditoTipoGarantiaActiva = null;
					 for (CreditoTipoGarantia creditoTipoGarantia : listaCreditoTipoGarantia) {
						 if(creditoTipoGarantia.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							 creditoTipoGarantiaActiva = new CreditoTipoGarantia();
							 creditoTipoGarantiaActiva = creditoTipoGarantia;
							 break;
						 }
					}
					 
					 //creditoTipoGarantia = new CreditoTipoGarantia();
					 //creditoTipoGarantia = listaCreditoTipoGarantia.get(0);
					 if(creditoTipoGarantiaActiva.getId() != null){
						 creditoTipoGarantiaActiva.setListaCondicionLaboral(new ArrayList<CondicionLaboralTipoGarantia>());
						 creditoTipoGarantiaActiva.setListaCondicionSocio(new ArrayList<CondicionSocioTipoGarantia>());
						 creditoTipoGarantiaActiva.setListaSituacionLaboral(new ArrayList<SituacionLaboralTipoGarantia>());
						 creditoTipoGarantiaActiva.setListaTipoCondicion(new ArrayList<CondicionHabilTipoGarantia>());
						 
						// Recuperamos SituacionLaboralTipoGarantia
						 listaSituacionLaboralTipoGarantia = boCreditoTipoGarantiaSituacionLaboral.getListaSituacionLaboralPorCreditoTipoGarantia(creditoTipoGarantiaActiva.getId());
						 if(listaSituacionLaboralTipoGarantia != null && !listaSituacionLaboralTipoGarantia.isEmpty()){
							 creditoTipoGarantiaActiva.getListaSituacionLaboral().addAll(listaSituacionLaboralTipoGarantia); 
						 }
						 
						// Recuperamos CondicionHabilTipoGarantia
						 listaCondicionHabilTipoGarantia = boCreditoTipoGarantiaCondicionHabil.getListaCondicionHabilPorCreditoTipoGarantia(creditoTipoGarantiaActiva.getId());
						 if(listaCondicionHabilTipoGarantia != null && !listaCondicionHabilTipoGarantia.isEmpty()){
							 creditoTipoGarantiaActiva.getListaTipoCondicion().addAll(listaCondicionHabilTipoGarantia); 
						 }
						 
						// Recuperamos CondicionSocioTipoGarantia
						listaCondicionSocioTipoGarantia = boCreditoTipoGarantiaCondicionSocio.getListaCondicionSocioPorCreditoTipoGarantia(creditoTipoGarantiaActiva.getId());
						if(listaCondicionSocioTipoGarantia != null && !listaCondicionSocioTipoGarantia.isEmpty()){
							creditoTipoGarantiaActiva.getListaCondicionSocio().addAll(listaCondicionSocioTipoGarantia);
						}
						 
						// Recuperamos CondicionLaboralTipoGarantia
						 listaCondicionLaboralTipoGarantia = boCreditoTipoGarantiaCondicionLaboral.getListaCondicionLaboralPorCreditoTipoGarantia(creditoTipoGarantiaActiva.getId());
						 if(listaCondicionLaboralTipoGarantia != null && !listaCondicionLaboralTipoGarantia.isEmpty()){
							 creditoTipoGarantiaActiva.getListaCondicionLaboral().addAll(listaCondicionLaboralTipoGarantia);
						 } 
						 creditoGarantiaPersonal.setCreditoTipoGarantia(creditoTipoGarantiaActiva); 
					 }
				 }*/
				 
				 if(listaCreditoTipoGarantia != null && !listaCreditoTipoGarantia.isEmpty()){
					 listaCreditoTipoGarantiaResult = new ArrayList<CreditoTipoGarantia>();
					 
					 CreditoTipoGarantia creditoTipoGarantiaActiva = null;
					 for (CreditoTipoGarantia creditoTipoGarantia : listaCreditoTipoGarantia) {
						 if(creditoTipoGarantia.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							 creditoTipoGarantiaActiva = new CreditoTipoGarantia();
							 creditoTipoGarantiaActiva = creditoTipoGarantia;
							 
							 //creditoTipoGarantia = new CreditoTipoGarantia();
							 //creditoTipoGarantia = listaCreditoTipoGarantia.get(0);
							 if(creditoTipoGarantiaActiva.getId() != null){
								 creditoTipoGarantiaActiva.setListaCondicionLaboral(new ArrayList<CondicionLaboralTipoGarantia>());
								 creditoTipoGarantiaActiva.setListaCondicionSocio(new ArrayList<CondicionSocioTipoGarantia>());
								 creditoTipoGarantiaActiva.setListaSituacionLaboral(new ArrayList<SituacionLaboralTipoGarantia>());
								 creditoTipoGarantiaActiva.setListaTipoCondicion(new ArrayList<CondicionHabilTipoGarantia>());
								 
								// Recuperamos SituacionLaboralTipoGarantia
								 listaSituacionLaboralTipoGarantia = boCreditoTipoGarantiaSituacionLaboral.getListaSituacionLaboralPorCreditoTipoGarantia(creditoTipoGarantiaActiva.getId());
								 if(listaSituacionLaboralTipoGarantia != null && !listaSituacionLaboralTipoGarantia.isEmpty()){
									 creditoTipoGarantiaActiva.getListaSituacionLaboral().addAll(listaSituacionLaboralTipoGarantia); 
								 }
								 
								// Recuperamos CondicionHabilTipoGarantia
								 listaCondicionHabilTipoGarantia = boCreditoTipoGarantiaCondicionHabil.getListaCondicionHabilPorCreditoTipoGarantia(creditoTipoGarantiaActiva.getId());
								 if(listaCondicionHabilTipoGarantia != null && !listaCondicionHabilTipoGarantia.isEmpty()){
									 creditoTipoGarantiaActiva.getListaTipoCondicion().addAll(listaCondicionHabilTipoGarantia); 
								 }
								 
								// Recuperamos CondicionSocioTipoGarantia
								listaCondicionSocioTipoGarantia = boCreditoTipoGarantiaCondicionSocio.getListaCondicionSocioPorCreditoTipoGarantia(creditoTipoGarantiaActiva.getId());
								if(listaCondicionSocioTipoGarantia != null && !listaCondicionSocioTipoGarantia.isEmpty()){
									creditoTipoGarantiaActiva.getListaCondicionSocio().addAll(listaCondicionSocioTipoGarantia);
								}
								 
								// Recuperamos CondicionLaboralTipoGarantia
								 listaCondicionLaboralTipoGarantia = boCreditoTipoGarantiaCondicionLaboral.getListaCondicionLaboralPorCreditoTipoGarantia(creditoTipoGarantiaActiva.getId());
								 if(listaCondicionLaboralTipoGarantia != null && !listaCondicionLaboralTipoGarantia.isEmpty()){
									 creditoTipoGarantiaActiva.getListaCondicionLaboral().addAll(listaCondicionLaboralTipoGarantia);
								 } 
								 
								 listaCreditoTipoGarantiaResult.add(creditoTipoGarantiaActiva);
							 }

						 }
					}
					 
					 // Se agrega la lista de Tipo Garantias y el nro de garantes configirados para el refinanciamiento.
					 if(listaCreditoTipoGarantiaResult!= null && !listaCreditoTipoGarantiaResult.isEmpty()){
						 Integer intNro = listaCreditoTipoGarantiaResult.size();
						 creditoGarantiaPersonal.setListaTipoGarantia(listaCreditoTipoGarantiaResult);
						 creditoGarantiaPersonal.setIntNroGarantesConfigurados(intNro);
						 
					 }else{
						 creditoGarantiaPersonal.setIntNroGarantesConfigurados(new Integer(0));
					 }
				 }
			 }

		 }catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
		}
			return creditoGarantiaPersonal; 
	 }
	 
	 
	 /**
	  * Reccupera todoas las conf de credito.
	  * @return
	  * @throws BusinessException
	  */
		public List<Credito> getlistaCofCredito() throws BusinessException {
		 List<Credito> lstCreditosRecuperados = new ArrayList<Credito>();
		try{
			lstCreditosRecuperados = boCredito.getlistaCofCredito();

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCreditosRecuperados;
	}
	 

}
