package pe.com.tumi.credito.socio.convenio.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.captacion.bo.AfectoBO;
import pe.com.tumi.credito.socio.captacion.bo.CaptacionBO;
import pe.com.tumi.credito.socio.captacion.bo.ConceptoBO;
import pe.com.tumi.credito.socio.captacion.bo.CondicionBO;
import pe.com.tumi.credito.socio.captacion.bo.RequisitoBO;
import pe.com.tumi.credito.socio.captacion.bo.VinculoBO;
import pe.com.tumi.credito.socio.captacion.domain.Afecto;
import pe.com.tumi.credito.socio.captacion.domain.AfectoId;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Concepto;
import pe.com.tumi.credito.socio.captacion.domain.ConceptoId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.CondicionComp;
import pe.com.tumi.credito.socio.captacion.domain.CondicionId;
import pe.com.tumi.credito.socio.captacion.domain.Requisito;
import pe.com.tumi.credito.socio.captacion.domain.RequisitoId;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.credito.socio.captacion.domain.VinculoId;
import pe.com.tumi.credito.socio.convenio.domain.composite.CaptacionComp;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
public class CaptacionService {
	
	protected  static Logger log = Logger.getLogger(CaptacionService.class);
	
	private CaptacionBO boCaptacion = (CaptacionBO)TumiFactory.get(CaptacionBO.class);
	private CondicionBO boCondicion = (CondicionBO)TumiFactory.get(CondicionBO.class);
	private AfectoBO 	boAfecto 	= (AfectoBO)TumiFactory.get(AfectoBO.class);
	private VinculoBO 	boVinculo 	= (VinculoBO)TumiFactory.get(VinculoBO.class);
	private RequisitoBO boRequisito = (RequisitoBO)TumiFactory.get(RequisitoBO.class);
	private ConceptoBO  boConcepto  = (ConceptoBO)TumiFactory.get(ConceptoBO.class);
	
	public List<CaptacionComp> getListaCaptacionCompDeBusquedaCaptacion(Captacion o) throws BusinessException{
		CaptacionComp dto = null;
		Captacion dtoCaptacion = null;
		List<CaptacionComp> lista = null;
		List<Captacion> listaCaptacion = null;
		List<Condicion> listaCondicion = null;
		List<Afecto> listaAfecto = null;
		List<Tabla> listaTablaCondSocio = null;
		List<Tabla> listaTablaAfecto = null;
		try{
			listaCaptacion = boCaptacion.getListaCaptacionDeBusqueda(o);
			TablaFacadeRemote tablaFacade = null;
			
			if(listaCaptacion != null && listaCaptacion.size()>0){
				lista = new ArrayList<CaptacionComp>();
				for(int i=0;i<listaCaptacion.size();i++){
					dto = new CaptacionComp();
					dtoCaptacion = listaCaptacion.get(i);
					dto.setCaptacion(dtoCaptacion);
					listaCondicion = boCondicion.getListaCondicionSocioPorPKCaptacion(dtoCaptacion.getId());
					
					tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
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
					dto.setStrCondicionSocio(listaCondicion.size()==listaTablaCondSocio.size()?"TODOS":csvPkCondicionSocio);
					
					listaAfecto = boAfecto.getListaAfectadasPorPKCaptacion(dtoCaptacion.getId());
					tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
					listaTablaAfecto = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCUENTA));
					String csvPkAfecto = null;
					for(int j=0;j<listaAfecto.size();j++){
						for(int k=0;k<listaTablaAfecto.size(); k++){
							if(listaAfecto.get(j).getId().getIntTipoCaptacionAfecto().equals(listaTablaAfecto.get(k).getIntIdDetalle())){
								if(csvPkAfecto == null)
									csvPkAfecto = String.valueOf(listaTablaAfecto.get(k).getStrDescripcion());
								else
									csvPkAfecto = csvPkAfecto + " / " +listaTablaAfecto.get(k).getStrDescripcion();
							}
						}
					}
					dto.setStrCtasConsideradas(listaAfecto.size()==listaTablaAfecto.size()?"TODOS":csvPkAfecto);
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
	
	public Captacion getCaptacionPorIdCaptacion(CaptacionId pId) throws BusinessException {
		Captacion captacion = null;
		CondicionComp condicionComp = null;
		Afecto afecto = null;
		Concepto concepto = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTablaCondicion = null;
		List<Condicion> listaCondicion = null;
		List<Condicion> listaCondicionTemp = null;
		List<CondicionComp> listaCondicionComp = new ArrayList<CondicionComp>();
		List<Afecto> listaAfecto = new ArrayList<Afecto>();
		List<Afecto> listaAfectoTemp = new ArrayList<Afecto>();
		List<Vinculo> listaVinculo = new ArrayList<Vinculo>();
		List<Requisito> listaRequisito = new ArrayList<Requisito>();
		List<Concepto> listaConcepto = new ArrayList<Concepto>();
		List<Tabla> listaTablaConcepto = null;
		List<Concepto> listaConceptoTemp = new ArrayList<Concepto>();
		try{
			captacion = boCaptacion.getCaptacionPorPK(pId);
			if(captacion!=null){
				tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				listaTablaCondicion = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
				listaCondicion = boCondicion.getListaPorPKCaptacion(pId);
				listaCondicionTemp = boCondicion.getListaCondicionSocioPorPKCaptacion(pId);
				for(int i=0;i<listaTablaCondicion.size();i++){
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
				}
				captacion.setListaCondicion(listaCondicionTemp);
				captacion.setListaCondicionComp(listaCondicionComp);
				
				listaAfecto = boAfecto.getListaAfectoPorPKCaptacion(pId);
				for(int i=0;i<listaAfecto.size();i++){
					afecto = listaAfecto.get(i);
					if(afecto.getIntIdValor()==1){
						listaAfectoTemp.add(afecto);
					}
				}
				captacion.setListaAfecto(listaAfectoTemp);
				
				listaVinculo = boVinculo.getListaVinculoPorPKCaptacion(pId);
				if(listaVinculo!=null && listaVinculo.size()>0){
					captacion.setListaVinculo(listaVinculo);
				}
				
				listaRequisito = boRequisito.getListaRequisitoPorPKCaptacion(pId);
				if(listaRequisito!=null && listaRequisito.size()>0){
					captacion.setListaRequisito(listaRequisito);
				}
				
				listaTablaConcepto = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));
				listaConcepto = boConcepto.getListaConceptoPorPKCaptacion(pId);
				for(int i=0;i<listaTablaConcepto.size();i++){
					for(int j=0;j<listaConcepto.size();j++){
						concepto = new Concepto();
						if(listaTablaConcepto.get(i).getIntIdDetalle().equals(listaConcepto.get(j).getId().getIntParaTipoConcepto())){
							concepto.setTabla(listaTablaConcepto.get(i));
							concepto.setId(listaConcepto.get(j).getId());
							concepto.setIntDia(listaConcepto.get(j).getIntDia());
							concepto.setIntMonto(listaConcepto.get(j).getIntMonto());
							concepto.setIntTipoMaxMinCod(listaConcepto.get(j).getIntTipoMaxMinCod());
							listaConceptoTemp.add(concepto);
						}
					}
				}
				captacion.setListaConcepto(listaConceptoTemp);
				/*listaConcepto = boConcepto.getListaConceptoPorPKCaptacion(pId);
				if(listaConcepto!=null && listaConcepto.size()>0){
					captacion.setListaConcepto(listaConcepto);
				}*/
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return captacion;
	}
	
	public Captacion grabarCaptacion(Captacion pCaptacion) throws BusinessException{
		Captacion captacion = null;
		Condicion condicion = null;
		List<CondicionComp> listaCondicionComp = null;
		List<Afecto> 		listaAfecto = null;
		List<Vinculo> 		listaVinculo = null;
		List<Requisito> 	listaRequisito = null;
		List<Concepto> 		listaConcepto = null;
		try{
			captacion = boCaptacion.grabarCaptacion(pCaptacion);
			
			condicion = new Condicion();
			condicion.setId(new CondicionId());
			condicion.getId().setIntPersEmpresaPk(pCaptacion.getId().getIntPersEmpresaPk());
			condicion.getId().setIntParaTipoCaptacionCod(pCaptacion.getId().getIntParaTipoCaptacionCod());
			condicion.getId().setIntItem(pCaptacion.getId().getIntItem());
			//condicion.getId().setIntParaCondicionSocioCod(pCaptacion.getCondicion().getId().getIntParaCondicionSocioCod());
			
			listaCondicionComp = pCaptacion.getListaCondicionComp();
			//Grabar Lista Condición de Captación
			if(listaCondicionComp!=null){
				grabarListaDinamicaCondicionComp(listaCondicionComp, condicion.getId());
			}
			//Grabar Lista de Afectos
			listaAfecto = pCaptacion.getListaAfecto();
			if(listaAfecto!=null){
				grabarListaDinamicaAfecto(listaAfecto, condicion.getId());
			}
			//Grabar Lista de Requisitos de Vínculo
			listaVinculo = pCaptacion.getListaVinculo();
			if(listaVinculo!=null){
				grabarListaDinamicaVinculo(listaVinculo, condicion.getId());
			}
			//Grabar Lista de Requisitos
			listaRequisito = pCaptacion.getListaRequisito();
			if(listaRequisito!=null){
				grabarListaDinamicaRequisito(listaRequisito, condicion.getId());
			}
			//Grabar Lista de Conceptos
			listaConcepto = pCaptacion.getListaConcepto();
			if(listaConcepto!=null){
				grabarListaDinamicaConcepto(listaConcepto, condicion.getId());
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return captacion;
	}
	
	public Captacion modificarCaptacion(Captacion pCaptacion) throws BusinessException{
		Captacion captacion = null;
		Condicion condicion = null;
		List<CondicionComp> listaCondicionComp = null;
		List<Afecto> 		listaAfecto = null;
		List<Vinculo> 		listaVinculo = null;
		List<Requisito> 	listaRequisito = null;
		List<Concepto> 		listaConcepto = null;
		try{
			captacion = boCaptacion.modificarCaptacion(pCaptacion);
			
			condicion = new Condicion();
			condicion.setId(new CondicionId());
			condicion.getId().setIntPersEmpresaPk(pCaptacion.getId().getIntPersEmpresaPk());
			condicion.getId().setIntParaTipoCaptacionCod(pCaptacion.getId().getIntParaTipoCaptacionCod());
			condicion.getId().setIntItem(pCaptacion.getId().getIntItem());
			//condicion.getId().setIntParaCondicionSocioCod(pCaptacion.getCondicion().getId().getIntParaCondicionSocioCod());
			
			listaCondicionComp = pCaptacion.getListaCondicionComp();
			//Grabar Lista Condición de Captación
			if(listaCondicionComp!=null){
				grabarListaDinamicaCondicionComp(listaCondicionComp, condicion.getId());
			}
			
			listaAfecto = pCaptacion.getListaAfecto();
			if(listaAfecto!=null){
				grabarListaDinamicaAfecto(listaAfecto, condicion.getId());
			}
			//Grabar Lista de Requisitos de Vínculo
			listaVinculo = pCaptacion.getListaVinculo();
			if(listaVinculo!=null){
				grabarListaDinamicaVinculo(listaVinculo, condicion.getId());
			}
			//Grabar Lista de Requisitos
			listaRequisito = pCaptacion.getListaRequisito();
			if(listaRequisito!=null){
				grabarListaDinamicaRequisito(listaRequisito, condicion.getId());
			}
			//Grabar Lista de Conceptos
			listaConcepto = pCaptacion.getListaConcepto();
			if(listaConcepto!=null){
				grabarListaDinamicaConcepto(listaConcepto, condicion.getId());
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return captacion;
	}
	
	public List<CondicionComp> grabarListaDinamicaCondicionComp(List<CondicionComp> listCondicionComp, CondicionId pPK) throws BusinessException{
		Condicion condicion = null;
		CondicionComp condicionComp = null;
		CondicionId pk = null;
		Condicion condicionTemp = null;
		try{
			for(int i=0; i<listCondicionComp.size(); i++){
				condicionComp = (CondicionComp) listCondicionComp.get(i);
				//condicionComp.setCondicion(new Condicion());
				//condicionComp.getCondicion().setId(new CondicionId());
				if(condicionComp.getCondicion()==null){
					pk = new CondicionId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntParaTipoCaptacionCod(pPK.getIntParaTipoCaptacionCod());
					pk.setIntItem(pPK.getIntItem());
					pk.setIntParaCondicionSocioCod(listCondicionComp.get(i).getTabla().getIntIdDetalle());
					condicionComp.setCondicion(new Condicion());
					condicionComp.getCondicion().setId(new CondicionId());
					condicionComp.getCondicion().setId(pk);
					condicionComp.getCondicion().setIntValor(listCondicionComp.get(i).getChkSocio()==true?1:0);
					condicion = boCondicion.grabarCondicion(condicionComp.getCondicion());
				}else{
					condicionTemp = boCondicion.getCondicionPorPK(condicionComp.getCondicion().getId());
					if(condicionTemp == null){
						condicion = boCondicion.grabarCondicion(condicionComp.getCondicion());
					}else{
						condicionComp.getCondicion().setIntValor(listCondicionComp.get(i).getChkSocio()==true?1:0);
						condicion = boCondicion.modificarCondicion(condicionComp.getCondicion());
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listCondicionComp;
	}
	
	public List<Afecto> grabarListaDinamicaAfecto(List<Afecto> lstAfecto, CondicionId pPK) throws BusinessException{
		Afecto afecto = null;
		AfectoId pk = null;
		Afecto afectoTemp = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTabla = new ArrayList<Tabla>();
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaTabla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCUENTA));
			for(int i=0;i<listaTabla.size();i++){
				afecto = new Afecto();
				pk = new AfectoId();
				pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				pk.setIntParaTipoCaptacionCod(pPK.getIntParaTipoCaptacionCod());
				pk.setIntItem(pPK.getIntItem());
				pk.setIntTipoCaptacionAfecto(listaTabla.get(i).getIntIdDetalle());
				afecto.setIntIdValor(0);
				for(int j=0;j<lstAfecto.size();j++){
					if(listaTabla.get(i).getIntIdDetalle().equals(lstAfecto.get(j).getId().getIntTipoCaptacionAfecto())){
						afecto.setIntIdValor(1);
					}
				}
				afecto.setId(pk);
				afectoTemp = boAfecto.getAfectoPorPK(afecto.getId());
				if(afectoTemp == null){
					afecto = boAfecto.grabarAfecto(afecto);
				}else{
					afecto = boAfecto.modificarAfecto(afecto);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstAfecto;
	}
	
	public List<Vinculo> grabarListaDinamicaVinculo(List<Vinculo> lstVinculo, CondicionId pPK) throws BusinessException{
		Vinculo vinculo = null;
		VinculoId pk = null;
		Vinculo vincTemp = null;
		try{
			for(int i=0; i<lstVinculo.size(); i++){
				vinculo = (Vinculo) lstVinculo.get(i);
				if(vinculo.getId()==null || vinculo.getId().getIntItemVinculo()==null){
				//if(vinculo.getId()==null){
					pk = new VinculoId();
					pk.setIntEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setParaTipoCaptacionCod(pPK.getIntParaTipoCaptacionCod());
					pk.setIntItem(pPK.getIntItem());
					pk.setParaTipoVinculoCod(vinculo.getId().getParaTipoVinculoCod());
					vinculo.setId(pk);
					vinculo = boVinculo.grabarVinculo(vinculo);
				}else{
					vincTemp = boVinculo.getVinculoPorPK(vinculo.getId());
					if(vincTemp == null){
						pk = new VinculoId();
						pk.setIntEmpresaPk(pPK.getIntPersEmpresaPk());
						pk.setParaTipoCaptacionCod(pPK.getIntParaTipoCaptacionCod());
						pk.setIntItem(pPK.getIntItem());
						pk.setParaTipoVinculoCod(vinculo.getId().getParaTipoVinculoCod());
						vinculo.setId(pk);
						vinculo = boVinculo.grabarVinculo(vinculo);
					}else{
						vinculo = boVinculo.modificarVinculo(vinculo);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstVinculo;
	}
	
	public List<Requisito> grabarListaDinamicaRequisito(List<Requisito> lstRequisito, CondicionId pPK) throws BusinessException{
		Requisito requisito = null;
		RequisitoId pk = null;
		Requisito reqTemp = null;
		//CaptacionId captacionId = null;
		//String csvPkRequisito = null;
		try{
			for(int i=0; i<lstRequisito.size(); i++){
				requisito = (Requisito) lstRequisito.get(i);
				if(requisito.getId()==null || requisito.getId().getIntItemRequisito()==null){
					pk = new RequisitoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntParaTipoCaptacionCod(pPK.getIntParaTipoCaptacionCod());
					pk.setIntItem(pPK.getIntItem());
					pk.setIntParaTipoRequisitoBenef(requisito.getId().getIntParaTipoRequisitoBenef());
					requisito.setId(pk);
					requisito = boRequisito.grabarRequisito(requisito);
				}else{
					reqTemp = boRequisito.getRequisitoPorPK(requisito.getId());
					if(reqTemp == null){
						pk = new RequisitoId();
						pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
						pk.setIntParaTipoCaptacionCod(pPK.getIntParaTipoCaptacionCod());
						pk.setIntItem(pPK.getIntItem());
						pk.setIntParaTipoRequisitoBenef(requisito.getId().getIntParaTipoRequisitoBenef());
						requisito.setId(pk);
						requisito = boRequisito.grabarRequisito(requisito);
					}else{
						requisito = boRequisito.modificarRequisito(requisito);
					}
				}
				/*if(csvPkRequisito == null)
					csvPkRequisito = String.valueOf(requisito.getId().getIntItemRequisito());
				else
					csvPkRequisito = csvPkRequisito + "," +requisito.getId().getIntItemRequisito();*/
			}
			/*if(strInsUpd.equals("U")){
				captacionId = new CaptacionId();
				captacionId.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
				captacionId.setIntParaTipoCaptacionCod(pPK.getIntParaTipoCaptacionCod());
				captacionId.setIntItem(pPK.getIntItem());
				if(csvPkRequisito!=null && csvPkRequisito.length()>0){
					boRequisito.eliminarRequisito(captacionId, csvPkRequisito);
				}
			}*/
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRequisito;
	}
	
	public List<Concepto> grabarListaDinamicaConcepto(List<Concepto> lstConcepto, CondicionId pPK) throws BusinessException{
		Concepto concepto = null;
		ConceptoId pk = null;
		Concepto conceptoTemp = null;
		try{
			for(int i=0; i<lstConcepto.size(); i++){
				concepto = (Concepto) lstConcepto.get(i);
				if(concepto.getId()==null || concepto.getId().getIntItem()==null){
					pk = new ConceptoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntParaTipoCaptacionCod(pPK.getIntParaTipoCaptacionCod());
					pk.setIntItem(pPK.getIntItem());
					pk.setIntParaTipoConcepto(concepto.getTabla().getIntIdDetalle());
					concepto.setId(pk);
					//concepto.setIntTipoMaxMinCod(Constante.PARAM_T_MAXIMO); // 05.08.2013 - cgd
					concepto.setIntTipoMaxMinCod(Constante.PARAM_T_MINIMO);
					concepto = boConcepto.grabarConcepto(concepto);
				}else{
					conceptoTemp = boConcepto.getConceptoPorPK(concepto.getId());
					if(conceptoTemp == null){
						concepto = boConcepto.grabarConcepto(concepto);
					}else{
						concepto = boConcepto.modificarConcepto(concepto);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstConcepto;
	}
}