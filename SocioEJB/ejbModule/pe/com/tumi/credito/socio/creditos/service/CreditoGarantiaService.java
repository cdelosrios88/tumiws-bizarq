package pe.com.tumi.credito.socio.creditos.service;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoTipoGarantiaComp;
import pe.com.tumi.credito.socio.creditos.bo.CreditoGarantiaBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoGarantiaTipoValorVentaBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaCondicionHabilBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaCondicionLaboralBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaCondicionSocioBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTipoGarantiaSituacionLaboralBO;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaTipoValorVenta;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaTipoValorVentaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantia;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
	
public class CreditoGarantiaService {
	
	private CreditoGarantiaBO boCreditoGarantia = (CreditoGarantiaBO)TumiFactory.get(CreditoGarantiaBO.class);
	private CreditoTipoGarantiaBO boCreditoTipoGarantia = (CreditoTipoGarantiaBO)TumiFactory.get(CreditoTipoGarantiaBO.class);
	private CreditoTipoGarantiaCondicionSocioBO boCondicionSocioTipoGarantia = (CreditoTipoGarantiaCondicionSocioBO)TumiFactory.get(CreditoTipoGarantiaCondicionSocioBO.class);
	private CreditoTipoGarantiaCondicionHabilBO boCondicionHabilTipoGarantia = (CreditoTipoGarantiaCondicionHabilBO)TumiFactory.get(CreditoTipoGarantiaCondicionHabilBO.class);
	private CreditoTipoGarantiaCondicionLaboralBO boCondicionLaboralTipoGarantia = (CreditoTipoGarantiaCondicionLaboralBO)TumiFactory.get(CreditoTipoGarantiaCondicionLaboralBO.class);
	private CreditoTipoGarantiaSituacionLaboralBO boSituacionLaboralTipoGarantia = (CreditoTipoGarantiaSituacionLaboralBO)TumiFactory.get(CreditoTipoGarantiaSituacionLaboralBO.class);
	private CreditoGarantiaTipoValorVentaBO boTipoValorVenta = (CreditoGarantiaTipoValorVentaBO)TumiFactory.get(CreditoGarantiaTipoValorVentaBO.class);
	
	private CreditoTipoGarantiaCondicionSocioService condicionSocioTipoGarantiaService = (CreditoTipoGarantiaCondicionSocioService)TumiFactory.get(CreditoTipoGarantiaCondicionSocioService.class);
	private CreditoTipoGarantiaCondicionHabilService condicionHabilTipoGarantiaService = (CreditoTipoGarantiaCondicionHabilService)TumiFactory.get(CreditoTipoGarantiaCondicionHabilService.class);
	private CreditoTipoGarantiaSituacionLaboralService situacionLaboralTipoGarantiaService = (CreditoTipoGarantiaSituacionLaboralService)TumiFactory.get(CreditoTipoGarantiaSituacionLaboralService.class);
	private CreditoTipoGarantiaCondicionLaboralService condicionLaboralTipoGarantiaService = (CreditoTipoGarantiaCondicionLaboralService)TumiFactory.get(CreditoTipoGarantiaCondicionLaboralService.class);
	
	public List<CreditoGarantia> getListaCreditoGarantia(CreditoGarantiaId o) throws BusinessException{
		List<CreditoGarantia> lista = null;
		try{
			lista = boCreditoGarantia.getListaCreditoGarantiaPorPKCreditoGarantia(o);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CreditoGarantia grabarCreditoGarantia(CreditoGarantia pCreditoGarantia) throws BusinessException{
		CreditoGarantia creditoGarantia = null;
		List<CreditoTipoGarantiaComp> listaTipoGarantiaComp = null;
		List<CreditoGarantiaTipoValorVenta> listaTipoValorVenta = null;
		try{
			creditoGarantia = boCreditoGarantia.grabarCreditoGarantia(pCreditoGarantia);
			
			listaTipoGarantiaComp = pCreditoGarantia.getListaTipoGarantiaComp();
			//Grabar Lista Tipo de Garantia
			if(listaTipoGarantiaComp!=null){
				grabarListaDinamicaTipoGarantiaComp(listaTipoGarantiaComp, creditoGarantia.getId());
			}
			
			listaTipoValorVenta = pCreditoGarantia.getListaTipoValorVenta();
			//Grabar Lista Tipo Valor Venta
			if(listaTipoValorVenta!=null){
				grabarListaDinamicaTipoValorVenta(listaTipoValorVenta, creditoGarantia.getId());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoGarantia;
	}
	
	public CreditoGarantia modificarCreditoGarantia(CreditoGarantia pCreditoGarantia) throws BusinessException{
		CreditoGarantia creditoGarantia = null;
		List<CreditoTipoGarantiaComp> listaTipoGarantiaComp = null;
		List<CreditoGarantiaTipoValorVenta> listaTipoValorVenta = null;
		try{
			creditoGarantia = boCreditoGarantia.modificarCreditoGarantia(pCreditoGarantia);
			
			listaTipoGarantiaComp = pCreditoGarantia.getListaTipoGarantiaComp();
			//Grabar Lista Tipo de Garantia
			if(listaTipoGarantiaComp!=null){
				grabarListaDinamicaTipoGarantiaComp(listaTipoGarantiaComp, creditoGarantia.getId());
			}
			
			listaTipoValorVenta = pCreditoGarantia.getListaTipoValorVenta();
			//Grabar Lista Tipo Valor Venta
			if(listaTipoValorVenta!=null){
				grabarListaDinamicaTipoValorVenta(listaTipoValorVenta, creditoGarantia.getId());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoGarantia;
	}
	
	public List<CreditoTipoGarantiaComp> grabarListaDinamicaTipoGarantiaComp(List<CreditoTipoGarantiaComp> lstTipoGarantiaComp, CreditoGarantiaId pPK) throws BusinessException{
		CreditoTipoGarantiaComp creditoTipoGarantiaComp = null;
		CreditoTipoGarantia creditoTipoGarantia = null;
		CreditoTipoGarantiaId pk = null;
		CreditoTipoGarantia creditoTipoGarantiaTemp = null;
		try{
			for(int i=0; i<lstTipoGarantiaComp.size(); i++){
				creditoTipoGarantiaComp = (CreditoTipoGarantiaComp) lstTipoGarantiaComp.get(i);
				if(creditoTipoGarantiaComp.getCreditoTipoGarantia().getId()==null || 
						creditoTipoGarantiaComp.getCreditoTipoGarantia().getId().getIntItemGarantiaTipo()==null){
					pk = new CreditoTipoGarantiaId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
					pk.setIntItemCredito(pPK.getIntItemCredito());
					pk.setIntParaTipoGarantiaCod(pPK.getIntParaTipoGarantiaCod());
					pk.setIntItemCreditoGarantia(pPK.getIntItemCreditoGarantia());
					creditoTipoGarantiaComp.getCreditoTipoGarantia().setId(pk);
					creditoTipoGarantiaComp.setCreditoTipoGarantia(boCreditoTipoGarantia.grabarCreditoTipoGarantia(creditoTipoGarantiaComp.getCreditoTipoGarantia()));
					//creditoTipoGarantia = boCreditoTipoGarantia.grabarCreditoTipoGarantia(creditoTipoGarantiaComp.getCreditoTipoGarantia());
					
					grabarCreditoTipoGarantiaTotal(creditoTipoGarantiaComp.getCreditoTipoGarantia());
				}else{
					creditoTipoGarantiaTemp = boCreditoTipoGarantia.getCreditoTipoGarantiaPorPK(creditoTipoGarantiaComp.getCreditoTipoGarantia().getId());
					if(creditoTipoGarantiaTemp == null){
						creditoTipoGarantia = boCreditoTipoGarantia.grabarCreditoTipoGarantia(creditoTipoGarantiaComp.getCreditoTipoGarantia());
						grabarCreditoTipoGarantiaTotal(creditoTipoGarantia);
					}else{
						creditoTipoGarantia = boCreditoTipoGarantia.modificarCreditoTipoGarantia(creditoTipoGarantiaComp.getCreditoTipoGarantia());
						grabarCreditoTipoGarantiaTotal(creditoTipoGarantia);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstTipoGarantiaComp;
	}
	
	public CreditoTipoGarantia grabarCreditoTipoGarantiaTotal(CreditoTipoGarantia pCreditoTipoGarantia) throws BusinessException{
		try{
			if(pCreditoTipoGarantia.getListaCondicionSocio()!=null && pCreditoTipoGarantia.getListaCondicionSocio().size()>0){
				condicionSocioTipoGarantiaService.grabarListaDinamicaCondicionSocioTipoCredito(pCreditoTipoGarantia.getListaCondicionSocio(), pCreditoTipoGarantia.getId());
			}
			if(pCreditoTipoGarantia.getListaTipoCondicion()!=null && pCreditoTipoGarantia.getListaTipoCondicion().size()>0){
				condicionHabilTipoGarantiaService.grabarListaDinamicaCondicionHabilTipoCredito(pCreditoTipoGarantia.getListaTipoCondicion(), pCreditoTipoGarantia.getId());
			}
			if(pCreditoTipoGarantia.getListaSituacionLaboral()!=null && pCreditoTipoGarantia.getListaSituacionLaboral().size()>0){
				situacionLaboralTipoGarantiaService.grabarListaDinamicaCondicionHabilTipoCredito(pCreditoTipoGarantia.getListaSituacionLaboral(), pCreditoTipoGarantia.getId());
			}
			if(pCreditoTipoGarantia.getListaCondicionLaboral()!=null && pCreditoTipoGarantia.getListaCondicionLaboral().size()>0){
				condicionLaboralTipoGarantiaService.grabarListaDinamicaCondicionHabilTipoCredito(pCreditoTipoGarantia.getListaCondicionLaboral(), pCreditoTipoGarantia.getId());
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return pCreditoTipoGarantia;
	}
	
	public CreditoGarantia getCreditoGarantiaPorIdCreditoGarantia(CreditoGarantiaId pId) throws BusinessException {
		CreditoGarantia creditoGarantia = null;
		List<CreditoTipoGarantia> listaCreditoTipoGarantia = new ArrayList<CreditoTipoGarantia>();
		List<CreditoTipoGarantiaComp> listaCreditoTipoGarantiaComp = new ArrayList<CreditoTipoGarantiaComp>();
		List<CondicionSocioTipoGarantia> listaCondicionSocio = new ArrayList<CondicionSocioTipoGarantia>();
		List<CondicionHabilTipoGarantia> listaCondicionHabil = new ArrayList<CondicionHabilTipoGarantia>();
		List<CondicionLaboralTipoGarantia> listaCondicionLaboral = new ArrayList<CondicionLaboralTipoGarantia>();
		List<SituacionLaboralTipoGarantia> listaSituacionLaboral = new ArrayList<SituacionLaboralTipoGarantia>();
		List<CreditoGarantiaTipoValorVenta> listaTipoValorVenta = new ArrayList<CreditoGarantiaTipoValorVenta>();
		List<CreditoGarantiaTipoValorVenta> listaTipoValorVentaTemp = new ArrayList<CreditoGarantiaTipoValorVenta>();
		CreditoTipoGarantiaComp creditoTipoGarantiaComp = null;
		CreditoTipoGarantia creditoTipoGarantia = null;
		CreditoGarantiaTipoValorVenta creditoGarantiaTipoValorVenta = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTablaCondSocio = new ArrayList<Tabla>();
		List<Tabla> listaTablaCondLaboral = new ArrayList<Tabla>();
		List<Tabla> listaTablaSituacionLaboral = new ArrayList<Tabla>();
		try{
			creditoGarantia = boCreditoGarantia.getCreditoGarantiaPorPK(pId);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTablaCondSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
			listaTablaCondLaboral = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONLABORAL));
			listaTablaSituacionLaboral = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			if(creditoGarantia!=null){
				listaTipoValorVenta = boTipoValorVenta.getListaTipoValorVentaPorPKCreditoGarantia(pId);
				if(listaTipoValorVenta!=null && listaTipoValorVenta.size()>0){
					for(int i=0;i<listaTipoValorVenta.size();i++){
						creditoGarantiaTipoValorVenta = listaTipoValorVenta.get(i);
						if(creditoGarantiaTipoValorVenta.getIntValor()==1){
							listaTipoValorVentaTemp.add(creditoGarantiaTipoValorVenta);
						}
					}
					creditoGarantia.setListaTipoValorVenta(listaTipoValorVentaTemp);
				}
				
				listaCreditoTipoGarantia = boCreditoTipoGarantia.getListaCreditoTipoGarantiaPorPKCreditoGarantia(pId);
				if(listaCreditoTipoGarantia!=null && listaCreditoTipoGarantia.size()>0){
					for(int i=0; i<listaCreditoTipoGarantia.size();i++){
						creditoTipoGarantiaComp = new CreditoTipoGarantiaComp();
						creditoTipoGarantiaComp.setCreditoTipoGarantia(new CreditoTipoGarantia());
						creditoTipoGarantia = listaCreditoTipoGarantia.get(i);
						if(creditoTipoGarantia.getListaCondicionSocio()==null){
							listaCondicionSocio = boCondicionSocioTipoGarantia.getListaCondicionSocioPorPKCreditoTipoGarantia(creditoTipoGarantia.getId());
							if(boCondicionSocioTipoGarantia.getListaCondicionSocioPorCreditoTipoGarantia(creditoTipoGarantia.getId())!=null){
								//creditoTipoGarantiaComp.getCreditoTipoGarantia().setListaCondicionSocio(boCondicionSocioTipoGarantia.getListaCondicionSocioPorCreditoTipoGarantia(creditoTipoGarantia.getId()));
								creditoTipoGarantia.setListaCondicionSocio(boCondicionSocioTipoGarantia.getListaCondicionSocioPorCreditoTipoGarantia(creditoTipoGarantia.getId()));
							}
						}
						
						if(creditoTipoGarantia.getListaTipoCondicion()==null){
							listaCondicionHabil = boCondicionHabilTipoGarantia.getListaCondicionHabilPorPKCreditoTipoGarantia(creditoTipoGarantia.getId());
							if(boCondicionHabilTipoGarantia.getListaCondicionHabilPorCreditoTipoGarantia(creditoTipoGarantia.getId())!=null){
								//creditoTipoGarantiaComp.getCreditoTipoGarantia().setListaTipoCondicion(boCondicionHabilTipoGarantia.getListaCondicionHabilPorCreditoTipoGarantia(creditoTipoGarantia.getId()));
								creditoTipoGarantia.setListaTipoCondicion(boCondicionHabilTipoGarantia.getListaCondicionHabilPorCreditoTipoGarantia(creditoTipoGarantia.getId()));
							}
						}
						//creditoTipoGarantiaComp.getCreditoTipoGarantia().setListaTipoCondicion(listaCondicionHabil);
						
						String csvStrCondSocio = null;
						if(listaCondicionSocio!=null && listaCondicionSocio.size()>0){
							for(int j=0;j<listaTablaCondSocio.size();j++){
								//for(int k=0;k<creditoTipoGarantiaComp.getCreditoTipoGarantia().getListaCondicionSocio().size();k++){
								for(int k=0;k<listaCondicionSocio.size();k++){
									if(listaTablaCondSocio.get(j).getIntIdDetalle().equals(listaCondicionSocio.get(k).getId().getIntParaCondicionSocioCod())){
										if(csvStrCondSocio == null)
											csvStrCondSocio = String.valueOf(listaTablaCondSocio.get(j).getStrDescripcion());
										else
											csvStrCondSocio = csvStrCondSocio + " / " +listaTablaCondSocio.get(j).getStrDescripcion();
									}
								}
							}
						}
						creditoTipoGarantiaComp.setStrCondicionSocio(csvStrCondSocio);
						//listaCondicionHabil = boCondicionHabilTipoGarantia.(creditoTipoGarantia.getId());
						
						if(creditoTipoGarantia.getListaCondicionLaboral()==null){
							listaCondicionLaboral = boCondicionLaboralTipoGarantia.getListaCondicionLaboralPorPKCreditoTipoGarantia(creditoTipoGarantia.getId());
							if(boCondicionLaboralTipoGarantia.getListaCondicionLaboralPorCreditoTipoGarantia(creditoTipoGarantia.getId())!=null){
								//creditoTipoGarantiaComp.getCreditoTipoGarantia().setListaCondicionLaboral(boCondicionLaboralTipoGarantia.getListaCondicionLaboralPorCreditoTipoGarantia(creditoTipoGarantia.getId()));
								creditoTipoGarantia.setListaCondicionLaboral(boCondicionLaboralTipoGarantia.getListaCondicionLaboralPorCreditoTipoGarantia(creditoTipoGarantia.getId()));
							}
						}
						//creditoTipoGarantiaComp.getCreditoTipoGarantia().setListaCondicionLaboral(listaCondicionLaboral);
						
						String csvStrCondLaboral = null;
						if(listaCondicionLaboral!=null){
							for(int j=0;j<listaTablaCondLaboral.size();j++){
								for(int k=0;k<listaCondicionLaboral.size();k++){
									if(listaTablaCondLaboral.get(j).getIntIdDetalle().equals(listaCondicionLaboral.get(k).getId().getIntParaCondicionLaboralCod())){
										if(csvStrCondLaboral == null)
											csvStrCondLaboral = String.valueOf(listaTablaCondLaboral.get(j).getStrDescripcion());
										else
											csvStrCondLaboral = csvStrCondLaboral + " / " +listaTablaCondLaboral.get(j).getStrDescripcion();
									}
								}
							}
						}
						creditoTipoGarantiaComp.setStrCondicionLaboral(csvStrCondLaboral);
						
						if(creditoTipoGarantia.getListaSituacionLaboral()==null){
							listaSituacionLaboral = boSituacionLaboralTipoGarantia.getListaSituacionLaboralPorPKCreditoTipoGarantia(creditoTipoGarantia.getId());
							if(boSituacionLaboralTipoGarantia.getListaSituacionLaboralPorCreditoTipoGarantia(creditoTipoGarantia.getId())!=null){
								//creditoTipoGarantiaComp.getCreditoTipoGarantia().setListaSituacionLaboral(boSituacionLaboralTipoGarantia.getListaSituacionLaboralPorCreditoTipoGarantia(creditoTipoGarantia.getId()));
								creditoTipoGarantia.setListaSituacionLaboral(boSituacionLaboralTipoGarantia.getListaSituacionLaboralPorCreditoTipoGarantia(creditoTipoGarantia.getId()));
							}
						}
						//creditoTipoGarantiaComp.getCreditoTipoGarantia().setListaSituacionLaboral(listaSituacionLaboral);
						
						String csvStrSituacionLaboral = null;
						if(listaSituacionLaboral!=null){
							for(int j=0;j<listaTablaSituacionLaboral.size();j++){
								for(int k=0;k<listaSituacionLaboral.size();k++){
									if(listaTablaSituacionLaboral.get(j).getIntIdDetalle().equals(listaSituacionLaboral.get(k).getId().getIntParaSituacionLaboralCod())){
										if(csvStrSituacionLaboral == null)
											csvStrSituacionLaboral = String.valueOf(listaTablaSituacionLaboral.get(j).getStrDescripcion());
										else
											csvStrSituacionLaboral = csvStrSituacionLaboral + " / " +listaTablaSituacionLaboral.get(j).getStrDescripcion();
									}
								}
							}
						}
						creditoTipoGarantiaComp.setStrSituacionLaboral(csvStrSituacionLaboral);
						
						creditoTipoGarantiaComp.setCreditoTipoGarantia(creditoTipoGarantia);
						listaCreditoTipoGarantiaComp.add(creditoTipoGarantiaComp);
						creditoGarantia.setListaTipoGarantiaComp(listaCreditoTipoGarantiaComp);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return creditoGarantia;
	}
	
	public List<CreditoGarantiaTipoValorVenta> grabarListaDinamicaTipoValorVenta(List<CreditoGarantiaTipoValorVenta> lstTipoValorVenta, CreditoGarantiaId pPK) throws BusinessException{
		CreditoGarantiaTipoValorVenta creditoGarantiaTipoValorVenta = null;
		CreditoGarantiaTipoValorVentaId pk = null;
		CreditoGarantiaTipoValorVenta creditoGarantiaTipoValorVentaTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_VALOR_VENTA_MERCADO));
			for(int i=0;i<listaTabla.size();i++){
				creditoGarantiaTipoValorVenta = new CreditoGarantiaTipoValorVenta();
				pk = new CreditoGarantiaTipoValorVentaId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCreditoCod(pPK.getIntParaTipoCreditoCod());
				pk.setIntItemCredito(pPK.getIntItemCredito());
				pk.setIntParaTipoGarantiaCod(pPK.getIntParaTipoGarantiaCod());
				pk.setIntItemCreditoGarantia(pPK.getIntItemCreditoGarantia());
				pk.setIntParaTipoValorVentaCod(listaTabla.get(i).getIntIdDetalle());
				creditoGarantiaTipoValorVenta.setIntValor(0);
				for(int j=0;j<lstTipoValorVenta.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstTipoValorVenta.get(j).getId().getIntParaTipoValorVentaCod())){
						creditoGarantiaTipoValorVenta.setIntValor(1);
					}
				}
				creditoGarantiaTipoValorVenta.setId(pk);
				creditoGarantiaTipoValorVentaTemp = boTipoValorVenta.getFinalidadPorPK(creditoGarantiaTipoValorVenta.getId());
				if(creditoGarantiaTipoValorVentaTemp == null){
					creditoGarantiaTipoValorVenta = boTipoValorVenta.grabarTipoValorVenta(creditoGarantiaTipoValorVenta);
				}else{
					creditoGarantiaTipoValorVenta = boTipoValorVenta.modificarTipoValorVenta(creditoGarantiaTipoValorVenta);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstTipoValorVenta;
	}
}
