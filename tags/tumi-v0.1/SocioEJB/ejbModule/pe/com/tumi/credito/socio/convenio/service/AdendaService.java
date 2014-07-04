package pe.com.tumi.credito.socio.convenio.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.convenio.bo.AdendaBO;
import pe.com.tumi.credito.socio.convenio.bo.AdendaCaptacionBO;
import pe.com.tumi.credito.socio.convenio.bo.AdendaCreditoBO;
import pe.com.tumi.credito.socio.convenio.bo.AdjuntoBO;
import pe.com.tumi.credito.socio.convenio.bo.CompetenciaBO;
import pe.com.tumi.credito.socio.convenio.bo.DonacionBO;
import pe.com.tumi.credito.socio.convenio.bo.FirmanteBO;
import pe.com.tumi.credito.socio.convenio.bo.PoblacionBO;
import pe.com.tumi.credito.socio.convenio.bo.PoblacionDetalleBO;
import pe.com.tumi.credito.socio.convenio.bo.RetencionPlanillalBO;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCaptacion;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCaptacionId;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCredito;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCreditoId;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.domain.Adjunto;
import pe.com.tumi.credito.socio.convenio.domain.AdjuntoId;
import pe.com.tumi.credito.socio.convenio.domain.Competencia;
import pe.com.tumi.credito.socio.convenio.domain.CompetenciaId;
import pe.com.tumi.credito.socio.convenio.domain.DonacionRegalia;
import pe.com.tumi.credito.socio.convenio.domain.DonacionRegaliaId;
import pe.com.tumi.credito.socio.convenio.domain.Firmante;
import pe.com.tumi.credito.socio.convenio.domain.FirmanteId;
import pe.com.tumi.credito.socio.convenio.domain.Poblacion;
import pe.com.tumi.credito.socio.convenio.domain.PoblacionDetalle;
import pe.com.tumi.credito.socio.convenio.domain.PoblacionDetalleId;
import pe.com.tumi.credito.socio.convenio.domain.PoblacionId;
import pe.com.tumi.credito.socio.convenio.domain.RetencionPlanilla;
import pe.com.tumi.credito.socio.convenio.domain.RetencionPlanillaId;
import pe.com.tumi.credito.socio.convenio.domain.composite.ConvenioComp;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaPlaneamientoComp;
import pe.com.tumi.credito.socio.creditos.domain.CondicionComp;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTopeCaptacion;
import pe.com.tumi.credito.socio.creditos.domain.Finalidad;
import pe.com.tumi.credito.socio.estructura.bo.ConvenioDetalleBO;
import pe.com.tumi.credito.socio.estructura.bo.EstructuraBO;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.creditos.domain.HojaPlaneamiento;
import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.empresa.domain.composite.ZonalComp;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
	
public class AdendaService {
	
	private AdendaBO boAdenda = (AdendaBO)TumiFactory.get(AdendaBO.class);
	private AdjuntoBO boAdjunto = (AdjuntoBO)TumiFactory.get(AdjuntoBO.class);
	private EstructuraBO boEstructura = (EstructuraBO)TumiFactory.get(EstructuraBO.class);
	private ConvenioDetalleBO boConvenioEstructDet = (ConvenioDetalleBO)TumiFactory.get(ConvenioDetalleBO.class);
	private PoblacionBO boPoblacion = (PoblacionBO)TumiFactory.get(PoblacionBO.class);
	private PoblacionDetalleBO boPoblacionDetalle = (PoblacionDetalleBO)TumiFactory.get(PoblacionDetalleBO.class);
	private CompetenciaBO boCompetencia = (CompetenciaBO)TumiFactory.get(CompetenciaBO.class);
	private RetencionPlanillalBO boRetencPlla = (RetencionPlanillalBO)TumiFactory.get(RetencionPlanillalBO.class);
	private DonacionBO boDonacion = (DonacionBO)TumiFactory.get(DonacionBO.class);
	private FirmanteBO boFirmante = (FirmanteBO)TumiFactory.get(FirmanteBO.class);
	private AdendaCaptacionBO boAdendaCaptacion = (AdendaCaptacionBO)TumiFactory.get(AdendaCaptacionBO.class);
	private AdendaCreditoBO boAdendaCredito = (AdendaCreditoBO)TumiFactory.get(AdendaCreditoBO.class);
	
	private GeneralFacadeRemote generalFacade = null;
	
	public AdendaService(){
		try {
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<HojaPlaneamientoComp> getListaHojaPlaneamientoCompDeBusquedaAdenda(HojaPlaneamientoComp o) throws BusinessException{
		HojaPlaneamientoComp dto = null;
		List<HojaPlaneamientoComp> lista = null;
		List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDetComp = null;
		List<HojaPlaneamientoComp> listaHojaPlaneamiento = new ArrayList<HojaPlaneamientoComp>();
		Estructura estructura = null;
		Juridica juridica = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTablaNivel = null;
		List<Tabla> listaTablaModalidad = null;
		//PersonaFacadeRemote personaFacade = null;
		//List<Juridica> listaJuridica = null;
		//String csvPkPersona = null;
		try{
			lista = boAdenda.getListaHojaPlaneamientoCompDeBusqueda(o);
			/*if(lista != null && lista.size()>0){
				for(int i=0;i<lista.size();i++){
					if(csvPkPersona == null)
						csvPkPersona = String.valueOf(lista.get(i).getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona());
					else
						csvPkPersona = csvPkPersona + "," +lista.get(i).getEstructuraDetalle().getEstructura().getJuridica().getIntIdPersona();
				}
				juridica = o.getEstructuraDetalle().getEstructura().getJuridica();
				personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				listaJuridica = personaFacade.getListaJuridicaPorInPkLikeRazon(csvPkPersona,juridica.getStrRazonSocial());
				
			}
			*/
			if(lista != null && lista.size()>0){
				PersonaFacadeRemote facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				for(int i=0;i<lista.size();i++){
					estructura = new Estructura();
					dto = lista.get(i);
					dto.setEstructuraDetalle(new EstructuraDetalle());
					listaConvenioEstructuraDetComp = boConvenioEstructDet.getListaConvenioEstructuraDetallePorPKConvenio(dto.getAdenda().getId());
					tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
					listaTablaNivel = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_NIVELENTIDAD));
					String csvPkNivel = null;
					if(listaConvenioEstructuraDetComp!=null && !listaConvenioEstructuraDetComp.isEmpty()){
						for(int j=0;j<listaConvenioEstructuraDetComp.size();j++){
							ConvenioEstructuraDetalleComp convenioEstrucDetComp = listaConvenioEstructuraDetComp.get(j);
							if(convenioEstrucDetComp.getEstructura()!=null && convenioEstrucDetComp.getEstructuraDetalle()!=null){
								for(int k=0;k<listaTablaNivel.size(); k++){
									if(convenioEstrucDetComp.getEstructura().getId().getIntNivel().equals(listaTablaNivel.get(k).getIntIdDetalle())){
										if(csvPkNivel == null)
											csvPkNivel = String.valueOf(listaTablaNivel.get(k).getStrDescripcion());
										else
											csvPkNivel = csvPkNivel + " / " +listaTablaNivel.get(k).getStrDescripcion();
									}
								}
							}
						}
						dto.setStrNivel(csvPkNivel);

					
						tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
						listaTablaModalidad = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
						String csvPkModalidad = null;
						for(int j=0;j<listaConvenioEstructuraDetComp.size();j++){
							ConvenioEstructuraDetalleComp convenioEstrucDetComp = listaConvenioEstructuraDetComp.get(j);
							if(convenioEstrucDetComp.getEstructura()!=null && convenioEstrucDetComp.getEstructuraDetalle()!=null){
								for(int k=0;k<listaTablaModalidad.size(); k++){
									if(convenioEstrucDetComp.getEstructuraDetalle().getIntParaModalidadCod()!=null){
										if(convenioEstrucDetComp.getEstructuraDetalle().getIntParaModalidadCod().equals(listaTablaModalidad.get(k).getIntIdDetalle())){
											if(csvPkModalidad == null)
												csvPkModalidad = String.valueOf(listaTablaModalidad.get(k).getStrDescripcion());
											else
												csvPkModalidad = csvPkModalidad + " / " +listaTablaModalidad.get(k).getStrDescripcion();
										}
									}
								}
							}
						}
						dto.setStrModalidad(csvPkModalidad);
						
						dto.getEstructuraDetalle().setEstructura(new Estructura());
						estructura.setId(new EstructuraId());
						if(listaConvenioEstructuraDetComp.get(0).getEstructura()!=null){
							estructura.getId().setIntNivel(listaConvenioEstructuraDetComp.get(0).getEstructura().getId().getIntNivel());
							estructura.getId().setIntCodigo(listaConvenioEstructuraDetComp.get(0).getEstructura().getId().getIntCodigo());
						}
						estructura = boEstructura.getEstructuraPorPK(estructura.getId());
						if(estructura!=null) juridica = facadePersona.getJuridicaPorPK(estructura.getIntPersPersonaPk());
						dto.getEstructuraDetalle().getEstructura().setJuridica(juridica);
						//Modificado por cdelosrios, 04/12/2013
						if(dto.getAdenda()!=null && dto.getAdenda().getDtFechaHojaPendiente()!=null){
							dto.getAdenda().setStrFechaRegistro(Constante.sdf2.format(dto.getAdenda().getDtFechaHojaPendiente()));
						}
						//Fin modificado por cdelosrios, 04/12/2013
						listaHojaPlaneamiento.add(dto);
					}
				}
				lista = listaHojaPlaneamiento;
				//Agregado por cdelosrios, 04/12/2013
				/*if(lista!=null){
					for (HojaPlaneamientoComp hojaPlaneamientoComp : lista) {
						if(o.getEstructuraDetalle().getEstructura().getJuridica().getStrRazonSocial()!=null &&
								!o.getEstructuraDetalle().getEstructura().getJuridica().getStrRazonSocial().equals("")){
							facadePersona.getListaJuridicaPorInPkLikeRazon(arg0, arg1)
						}
					}
				}*/
				//Fin agregado por cdelosrios, 04/12/2013
			}
		}catch(BusinessException e){
			System.out.println("Error BusinessException en getListaHojaPlaneamientoCompDeBusquedaAdenda ---> "+e);
			throw e;
		}catch(Exception e){
			System.out.println("Error Exception en getListaHojaPlaneamientoCompDeBusquedaAdenda ---> "+e);
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ConvenioComp> getListaConvenioCompDeBusqueda(ConvenioComp o) throws BusinessException{
		ConvenioComp dto = null;
		List<ConvenioComp> lista = null;
		List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDetComp = null;
		List<ConvenioComp> listaHojaPlaneamiento = new ArrayList<ConvenioComp>();
		Estructura estructura = null;
		Juridica juridica = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTablaNivel = null;
		List<Tabla> listaTablaModalidad = null;
		try{
			lista = boAdenda.getListaConvenioComp(o);
			
			if(lista != null && lista.size()>0){
				PersonaFacadeRemote facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				for(int i=0;i<lista.size();i++){
					estructura = new Estructura();
					dto = lista.get(i);
					dto.setEstructuraDetalle(new EstructuraDetalle());
					listaConvenioEstructuraDetComp = boConvenioEstructDet.getListaConvenioEstructuraDetallePorPKConvenio(dto.getAdenda().getId());
					tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
					listaTablaNivel = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_NIVELENTIDAD));
					String csvPkNivel = null;
					if (listaConvenioEstructuraDetComp!=null && !listaConvenioEstructuraDetComp.isEmpty()) {
						for(int j=0;j<listaConvenioEstructuraDetComp.size();j++){
							for(int k=0;k<listaTablaNivel.size(); k++){
								if(listaConvenioEstructuraDetComp.get(j).getEstructura().getId().getIntNivel().equals(listaTablaNivel.get(k).getIntIdDetalle())){
									if(csvPkNivel == null)
										csvPkNivel = String.valueOf(listaTablaNivel.get(k).getStrDescripcion());
									else
										csvPkNivel = csvPkNivel + " / " +listaTablaNivel.get(k).getStrDescripcion();
								}
							}
						}
						dto.setStrNivel(csvPkNivel);
						
						tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
						listaTablaModalidad = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
						String csvPkModalidad = null;
						
						for(int j=0;j<listaConvenioEstructuraDetComp.size();j++){
							for(int k=0;k<listaTablaModalidad.size(); k++){
								if(listaConvenioEstructuraDetComp.get(j).getEstructuraDetalle().getIntParaModalidadCod()!=null){
									if(listaConvenioEstructuraDetComp.get(j).getEstructuraDetalle().getIntParaModalidadCod().equals(listaTablaModalidad.get(k).getIntIdDetalle())){
										if(csvPkModalidad == null)
											csvPkModalidad = String.valueOf(listaTablaModalidad.get(k).getStrDescripcion());
										else
											csvPkModalidad = csvPkModalidad + " / " +listaTablaModalidad.get(k).getStrDescripcion();
									}
								}
							}
						}
						dto.setStrModalidad(csvPkModalidad);
						dto.getEstructuraDetalle().setEstructura(new Estructura());
						estructura.setId(new EstructuraId());
						estructura.getId().setIntNivel(listaConvenioEstructuraDetComp.get(0).getEstructura().getId().getIntNivel());
						estructura.getId().setIntCodigo(listaConvenioEstructuraDetComp.get(0).getEstructura().getId().getIntCodigo());
						estructura = boEstructura.getEstructuraPorPK(estructura.getId());
						juridica = facadePersona.getJuridicaPorPK(estructura.getIntPersPersonaPk());
						dto.getEstructuraDetalle().getEstructura().setJuridica(juridica);
						dto.getAdenda().setStrFechaRegistro(Constante.sdf2.format(dto.getAdenda().getDtFechaHojaPendiente()));
						dto.getAdenda().setStrDtInicio(Constante.sdf.format(dto.getAdenda().getDtInicio()));
						dto.getAdenda().setStrDtCese(dto.getAdenda().getDtCese()==null?"":Constante.sdf.format(dto.getAdenda().getDtCese()));
						listaHojaPlaneamiento.add(dto);
					}
				}
				lista = listaHojaPlaneamiento;
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Adenda grabarAdenda(Adenda pAdenda) throws BusinessException{
		Adenda adenda = null;
		List<Adjunto> listaAdjunto;
		List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDetalleComp = null;
		List<Poblacion> 					listaPoblacion = null;
		List<Competencia> 					listaCompetencia = null;
		try{
			adenda = boAdenda.grabarAdenda(pAdenda);
			
			listaAdjunto = pAdenda.getListaAdjunto();
			if(listaAdjunto != null){
				grabarListaDinamicaAdendaAdjunta(listaAdjunto, adenda.getId());
			}
			listaConvenioEstructuraDetalleComp = pAdenda.getListaConvenioDetalleComp();
			//Grabar Lista Convenio Estructura Detalle
			if(listaConvenioEstructuraDetalleComp!=null){
				grabarListaDinamicaConvenioEstructDetalle(listaConvenioEstructuraDetalleComp, adenda.getId());
			}
			//Grabar Lista de Poblacion
			listaPoblacion = pAdenda.getListaPoblacion();
			if(listaPoblacion!=null){
				grabarListaDinamicaPoblacion(listaPoblacion, adenda.getId());
			}
			//Grabar Lista de Competencias
			listaCompetencia = pAdenda.getListaCompetencia();
			if(listaCompetencia!=null){
				grabarListaDinamicaCompetencia(listaCompetencia, adenda.getId());
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return adenda;
	}
	
	public Adenda modificarAdenda(Adenda pAdenda) throws BusinessException{
		Adenda adenda = null;
		List<Adjunto> listaAdjunto;
		List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDetalleComp = null;
		List<Poblacion> 					listaPoblacion = null;
		List<Competencia> 					listaCompetencia = null;
		try{
			adenda = boAdenda.modificarAdenda(pAdenda);
			
			listaAdjunto = pAdenda.getListaAdjunto();
			if(listaAdjunto != null){
				listaAdjunto = grabarListaDinamicaAdendaAdjunta(listaAdjunto, adenda.getId());
				//if(adenda.getListaAdjunto() == null || adenda.getListaAdjunto().isEmpty()) adenda.setListaAdjunto(new ArrayList<Adjunto>());
				//else adenda.getListaAdjunto().clear();
				adenda.setListaAdjunto(listaAdjunto);
				}
			listaConvenioEstructuraDetalleComp = pAdenda.getListaConvenioDetalleComp();
			//Grabar Lista Convenio Estructura Detalle
			if(listaConvenioEstructuraDetalleComp!=null){
				grabarListaDinamicaConvenioEstructDetalle(listaConvenioEstructuraDetalleComp, adenda.getId());
			}
			//Grabar Lista de Poblacion
			listaPoblacion = pAdenda.getListaPoblacion();
			if(listaPoblacion!=null){
				grabarListaDinamicaPoblacion(listaPoblacion, adenda.getId());
			}
			//Grabar Lista de Competencias
			listaCompetencia = pAdenda.getListaCompetencia();
			if(listaCompetencia!=null){
				grabarListaDinamicaCompetencia(listaCompetencia, adenda.getId());
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return adenda;
	}
	
	public List<Archivo> grabarListaArchivoDeAdjunto(List<Adjunto> lista, String strCartaPresentacion, String strConvenioSugerido, String strAdendaSugerida) throws BusinessException{
		List<Archivo> listaArchivo = null;
		Archivo archivo = null;
		try{
			listaArchivo = new ArrayList<Archivo>();
			for(Adjunto adjunto:lista){
				archivo = new Archivo();
				archivo.setId(new ArchivoId());
				if(strCartaPresentacion != null ){
					if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPRESENTACION)){
						archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
						archivo.setStrNombrearchivo(strCartaPresentacion);
						archivo.setIntParaEstadoCod(new Integer(Constante.PARAM_T_ESTADOUNIVERSAL));
						archivo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
						archivo = generalFacade.grabarArchivo(archivo);
						listaArchivo.add(archivo);
					}
				}
				
				if(strConvenioSugerido != null ){
					if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONVENIOSUGERIDO)){
						archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
						archivo.setStrNombrearchivo(strConvenioSugerido);
						archivo.setIntParaEstadoCod(new Integer(Constante.PARAM_T_ESTADOUNIVERSAL));
						archivo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
						archivo = generalFacade.grabarArchivo(archivo);
						listaArchivo.add(archivo);
					}
				}
				
				if(strAdendaSugerida != null ){
					if(adjunto.getIntParaTipoArchivoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADENDASUGERIDA)){
						archivo.getId().setIntParaTipoCod(adjunto.getIntParaTipoArchivoCod());
						archivo.setStrNombrearchivo(strAdendaSugerida);
						archivo.setIntParaEstadoCod(new Integer(Constante.PARAM_T_ESTADOUNIVERSAL));
						archivo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
						archivo = generalFacade.grabarArchivo(archivo);
						listaArchivo.add(archivo);
					}	
				}
				
				
				//listaArchivo = listaArchivo;
			}
			System.out.println("listaArchivo.size(): "+ listaArchivo.size());
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaArchivo;
	}
	
	public List<Adjunto> grabarListaDinamicaAdendaAdjunta(List<Adjunto> lstConvenioAdjunto, AdendaId pPK) throws BusinessException{
		Adjunto adjunto = null;
		AdjuntoId pk = null;
		Adjunto adjuntoTemp = null;
		try{
			for(int i=0; i<lstConvenioAdjunto.size(); i++){
				adjunto = lstConvenioAdjunto.get(i);
				System.out.println("ADJUNTO --->"+i +" / "+ adjunto.getId().getIntConvenio()+"*"+adjunto.getId().getIntItemConvenio()+"*"+adjunto.getId().getIntCodigo()+"........"  + adjunto.getIntItemArchivo() + "-"+adjunto.getIntParaItemHistorico()+"-"+adjunto.getIntParaTipoArchivoCod());
				if(adjunto.getId()==null || adjunto.getId().getIntCodigo()==null){
					pk = new AdjuntoId();
					pk.setIntConvenio(pPK.getIntConvenio());
					pk.setIntItemConvenio(pPK.getIntItemConvenio());
					pk.setIntCodigo(adjunto.getId().getIntCodigo());
					adjunto.setId(pk);
					adjunto = boAdjunto.grabarAdjunto(adjunto);
				}else{
					adjuntoTemp = boAdjunto.getAdjuntoPorPK(adjunto.getId());
					if(adjuntoTemp == null){
						adjunto = boAdjunto.grabarAdjunto(adjunto);
					}else{
						adjunto = boAdjunto.modificarAdjunto(adjunto);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstConvenioAdjunto;
	}
	
	public List<ConvenioEstructuraDetalleComp> grabarListaDinamicaConvenioEstructDetalle(List<ConvenioEstructuraDetalleComp> lstConvenioEstructDetComp, AdendaId pPK) throws BusinessException{
		ConvenioEstructuraDetalleComp convenioEstructuraDetalleComp = null;
		ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
		ConvenioEstructuraDetalleId pk = null;
		//ConvenioEstructuraDetalleComp convenioEstructuraDetalleCompTemp = null;
		try{
			for(int i=0; i<lstConvenioEstructDetComp.size(); i++){
				convenioEstructuraDetalleComp = (ConvenioEstructuraDetalleComp) lstConvenioEstructDetComp.get(i);
				if(convenioEstructuraDetalleComp.getConvenioEstructuraDetalle()==null)
					convenioEstructuraDetalleComp.setConvenioEstructuraDetalle(new ConvenioEstructuraDetalle());
				if(convenioEstructuraDetalleComp.getAdenda()!=null){ //|| convenioEstructuraDetalleComp.getEstructura().getId().getIntNivel()==null){
					convenioEstructuraDetalle = new ConvenioEstructuraDetalle();
					pk = new ConvenioEstructuraDetalleId();
					if(convenioEstructuraDetalleComp.getEstructuraDetalle()==null){
						convenioEstructuraDetalleComp.setEstructuraDetalle(new EstructuraDetalle());
						convenioEstructuraDetalleComp.getEstructuraDetalle().setId(new EstructuraDetalleId());
					}
					pk.setIntConvenio(pPK.getIntConvenio());
					pk.setIntItemConvenio(pPK.getIntItemConvenio());
					pk.setIntNivel(convenioEstructuraDetalleComp.getEstructura().getId().getIntNivel());
					pk.setIntCodigo(convenioEstructuraDetalleComp.getEstructura().getId().getIntCodigo());
					pk.setIntCaso(convenioEstructuraDetalleComp.getEstructuraDetalle().getId().getIntCaso()==null?0:convenioEstructuraDetalleComp.getEstructuraDetalle().getId().getIntCaso());
					pk.setIntItemCaso(convenioEstructuraDetalleComp.getEstructuraDetalle().getId().getIntItemCaso()==null?0:convenioEstructuraDetalleComp.getEstructuraDetalle().getId().getIntItemCaso());
					convenioEstructuraDetalleComp.getConvenioEstructuraDetalle().setId(pk);
					convenioEstructuraDetalleComp.getConvenioEstructuraDetalle().setIntSocio(0);
					convenioEstructuraDetalle = boConvenioEstructDet.grabarConvenioDetalle(convenioEstructuraDetalleComp.getConvenioEstructuraDetalle());
				}else{
					pk = new ConvenioEstructuraDetalleId();
					pk.setIntConvenio(pPK.getIntConvenio());
					pk.setIntItemConvenio(pPK.getIntItemConvenio());
					pk.setIntNivel(convenioEstructuraDetalleComp.getEstructura().getId().getIntNivel());
					pk.setIntCodigo(convenioEstructuraDetalleComp.getEstructura().getId().getIntCodigo());
					pk.setIntCaso(convenioEstructuraDetalleComp.getEstructuraDetalle().getId().getIntCaso());
					pk.setIntItemCaso(convenioEstructuraDetalleComp.getEstructuraDetalle().getId().getIntItemCaso());
					//convenioEstructuraDetalleComp.getConvenioEstructuraDetalle().setId(pk);
					convenioEstructuraDetalle = boConvenioEstructDet.getConvenioDetallePorPK(pk);
					if(convenioEstructuraDetalle == null){
						convenioEstructuraDetalle = boConvenioEstructDet.grabarConvenioDetalle(convenioEstructuraDetalleComp.getConvenioEstructuraDetalle());
					}else{
						convenioEstructuraDetalle = boConvenioEstructDet.modificarConvenioDetalle(convenioEstructuraDetalleComp.getConvenioEstructuraDetalle());
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstConvenioEstructDetComp;
	}
	
	public List<Poblacion> grabarListaDinamicaPoblacion(List<Poblacion> lstPoblacion, AdendaId pPK) throws BusinessException{
		Poblacion poblacion = null;
		PoblacionId pk = null;
		Poblacion poblacionTemp = null;
		try{
			for(int i=0; i<lstPoblacion.size(); i++){
				poblacion = (Poblacion) lstPoblacion.get(i);
				if(poblacion.getId()==null){
					pk = new PoblacionId();
					//pk.setIntItemPoblacion(pPK.getIntConvenio());
					poblacion.setId(pk);
					poblacion.setIntConvenio(pPK.getIntConvenio());
					poblacion.setIntItemConvenio(pPK.getIntItemConvenio());
					poblacion = boPoblacion.grabarPoblacion(poblacion);
					if(poblacion.getListaPoblacionDetalle()!=null && poblacion.getListaPoblacionDetalle().size()>0){
						grabarListaDinamicaPoblacionDetalle(poblacion.getListaPoblacionDetalle(), poblacion.getId());
					}
				}else{
					poblacionTemp = boPoblacion.getPoblacionPorPK(poblacion.getId());
					if(poblacionTemp == null){
						poblacion = boPoblacion.grabarPoblacion(poblacion);
						if(poblacion.getListaPoblacionDetalle()!=null && poblacion.getListaPoblacionDetalle().size()>0){
							grabarListaDinamicaPoblacionDetalle(poblacion.getListaPoblacionDetalle(), poblacion.getId());
						}
					}else{
						poblacion = boPoblacion.modificarPoblacion(poblacion);
						if(poblacion.getListaPoblacionDetalle()!=null && poblacion.getListaPoblacionDetalle().size()>0){
							grabarListaDinamicaPoblacionDetalle(poblacion.getListaPoblacionDetalle(), poblacion.getId());
						}
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstPoblacion;
	}
	
	public List<PoblacionDetalle> grabarListaDinamicaPoblacionDetalle(List<PoblacionDetalle> lstPoblacionDetalle, PoblacionId pPK) throws BusinessException{
		PoblacionDetalle poblacionDetalle = null;
		PoblacionDetalleId pk = null;
		PoblacionDetalle poblacionDetalleTemp = null;
		try{
			for(int i=0; i<lstPoblacionDetalle.size(); i++){
				poblacionDetalle = (PoblacionDetalle) lstPoblacionDetalle.get(i);
				if(poblacionDetalle.getId().getIntItemPoblacion()==null){
					pk = new PoblacionDetalleId();
					pk.setIntItemPoblacion(pPK.getIntItemPoblacion());
					pk.setIntTipoTrabajador(poblacionDetalle.getId().getIntTipoTrabajador());
					pk.setIntTipoSocio(poblacionDetalle.getId().getIntTipoSocio());
					poblacionDetalle.setId(pk);
					poblacionDetalle = boPoblacionDetalle.grabarPoblacionDetalle(poblacionDetalle);
				}else{
					poblacionDetalleTemp = boPoblacionDetalle.getPoblacionDetallePorPK(poblacionDetalle.getId());
					if(poblacionDetalleTemp == null){
						poblacionDetalle = boPoblacionDetalle.grabarPoblacionDetalle(poblacionDetalle);
					}else{
						poblacionDetalle = boPoblacionDetalle.modificarPoblacionDetalle(poblacionDetalle);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstPoblacionDetalle;
	}
	
	public List<Competencia> grabarListaDinamicaCompetencia(List<Competencia> lstCompetencia, AdendaId pPK) throws BusinessException{
		Competencia competencia = null;
		CompetenciaId pk = null;
		Competencia competenciaTemp = null;
		try{
			for(int i=0; i<lstCompetencia.size(); i++){
				competencia = (Competencia) lstCompetencia.get(i);
				if(competencia.getId()==null){
					pk = new CompetenciaId();
					//pk.setIntConvenio(pPK.getIntConvenio());
					competencia.setId(pk);
					competencia.setIntConvenio(pPK.getIntConvenio());
					competencia.setIntItemConvenio(pPK.getIntItemConvenio());
					competencia = boCompetencia.grabarCompetencia(competencia);
				}else{
					competenciaTemp = boCompetencia.getCompetenciaPorPK(competencia.getId());
					if(competenciaTemp == null){
						competencia = boCompetencia.grabarCompetencia(competencia);
					}else{
						competencia = boCompetencia.modificarCompetencia(competencia);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCompetencia;
	}
	
	public Adenda getAdendaPorIdAdenda(AdendaId pPK) throws BusinessException {
		Adenda adenda = null;
		List<Adjunto> listaAdendaAdjunto = null;
		List<Poblacion> listaPoblacion = null;
		List<Poblacion> listaPoblacionTemp = new ArrayList<Poblacion>();
		Poblacion poblacion = null;
		List<PoblacionDetalle> listaPoblacionDetalle = null;
		List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDetalleComp = null;
		List<Competencia> listaCompetencia = null;
		Juridica juridica = null;
		try{
			adenda = boAdenda.getAdendaPorPK(pPK);
			if(adenda!=null){
				listaAdendaAdjunto = boAdjunto.getListaAdjuntoPorPKAdenda(pPK);
				if(listaAdendaAdjunto!=null && listaAdendaAdjunto.size()>0){
					adenda.setListaAdjunto(listaAdendaAdjunto);
				}
				listaPoblacion = boPoblacion.getListaPoblacionPorPKConvenio(adenda.getId());
				if(listaPoblacion!=null && listaPoblacion.size()>0){
					for(int i=0;i<listaPoblacion.size();i++){
						poblacion = listaPoblacion.get(i);
						listaPoblacionDetalle = boPoblacionDetalle.getListaPoblacionDetallePorPKPoblacion(poblacion.getId());
						if(listaPoblacionDetalle!=null && listaPoblacionDetalle.size()>0){
							poblacion.setListaPoblacionDetalle(listaPoblacionDetalle);
						}
						listaPoblacionTemp.add(poblacion);
					}
					listaPoblacion = listaPoblacionTemp;
					adenda.setListaPoblacion(listaPoblacion);
				}
				
				listaConvenioEstructuraDetalleComp = boConvenioEstructDet.getListaConvenioEstructuraDetallePorPKConvenio(adenda.getId());
				PersonaFacadeRemote facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				if(listaConvenioEstructuraDetalleComp!=null && listaConvenioEstructuraDetalleComp.size()>0){
					for(ConvenioEstructuraDetalleComp convenioEstructuraDetalleComp: listaConvenioEstructuraDetalleComp){
						if(convenioEstructuraDetalleComp.getEstructura()!=null){
							juridica = facadePersona.getJuridicaPorPK(convenioEstructuraDetalleComp.getEstructura().getIntPersPersonaPk());
							convenioEstructuraDetalleComp.getEstructura().setJuridica(juridica);
						}
					}
					adenda.setListaConvenioDetalleComp(listaConvenioEstructuraDetalleComp);
				}
				
				listaCompetencia = boCompetencia.getListaCompetenciaPorPKConvenio(adenda.getId());
				if(listaCompetencia!=null && listaCompetencia.size()>0){
					adenda.setListaCompetencia(listaCompetencia);
				}
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return adenda;
	}
	
	public List<ConvenioComp> getListaConvenioPorTipoConvenio(ConvenioComp o) throws BusinessException{
		ConvenioComp dto = null;
		List<ConvenioComp> lista = null;
		List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDetComp = null;
		List<ConvenioComp> listaHojaPlaneamiento = new ArrayList<ConvenioComp>();
		Estructura estructura = null;
		Juridica juridica = null;
		TablaFacadeRemote tablaFacade = null;
		List<Tabla> listaTablaNivel = null;
		List<Tabla> listaTablaModalidad = null;
		try{
			lista = boAdenda.getListaConvenioPorTipoConvenio(o);
			
			if(lista != null && lista.size()>0){
				PersonaFacadeRemote facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				for(int i=0;i<lista.size();i++){
					estructura = new Estructura();
					dto = lista.get(i);
					dto.setEstructuraDetalle(new EstructuraDetalle());
					listaConvenioEstructuraDetComp = boConvenioEstructDet.getListaConvenioEstructuraDetallePorPKConvenio(dto.getAdenda().getId());
					
					dto.getEstructuraDetalle().setEstructura(new Estructura());
					estructura.setId(new EstructuraId());
					estructura.getId().setIntNivel(listaConvenioEstructuraDetComp.get(0).getEstructura().getId().getIntNivel());
					estructura.getId().setIntCodigo(listaConvenioEstructuraDetComp.get(0).getEstructura().getId().getIntCodigo());
					estructura = boEstructura.getEstructuraPorPK(estructura.getId());
					juridica = facadePersona.getJuridicaPorPK(estructura.getIntPersPersonaPk());
					dto.getEstructuraDetalle().getEstructura().setJuridica(juridica);
					dto.getAdenda().setStrFechaRegistro(Constante.sdf2.format(dto.getAdenda().getDtFechaHojaPendiente()));
					dto.getAdenda().setStrDtInicio(Constante.sdf.format(dto.getAdenda().getDtInicio()));
					dto.getAdenda().setStrDtCese(dto.getAdenda().getDtCese()==null?"":Constante.sdf.format(dto.getAdenda().getDtCese()));
					listaHojaPlaneamiento.add(dto);
				}
				lista = listaHojaPlaneamiento;
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Persona> getListaRepLegalPorIdPerNaturalYIdPerJuridica(Integer intIdPersona,Integer intTipoVinculo,Integer intIdEmpresaSistema) throws BusinessException{
		List<Persona> lista = null;
		Persona perJuridica = null;
		try{
			PersonaFacadeRemote facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			lista = facadePersona.getListaPersonaNaturalPorPKPersonaEmpresaYTipoVinculo(intIdPersona, intTipoVinculo, intIdEmpresaSistema);
			if(lista!=null){
				for(int i=0; i<lista.size(); i++){
					Persona persona = lista.get(i);
					for(int j=0; j<persona.getListaDocumento().size(); j++){
						if(persona.getListaDocumento().get(j).getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
							persona.setDocumento(persona.getListaDocumento().get(j));
							break;
						}
					}
					perJuridica = facadePersona.getPersonaJuridicaPorIdPersona(intIdPersona);
					persona.setJuridica(perJuridica.getJuridica());
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Adenda grabarConvenio(Adenda pAdenda) throws BusinessException{
		Adenda adenda = null;
		List<RetencionPlanilla> listaRetencPlla;
		List<DonacionRegalia> 	listaDonacionRegalia = null;
		List<Firmante> 			listaFirmantes = null;
		List<AdendaCaptacion> 	listaCaptacionAportes = null;
		List<AdendaCaptacion> 	listaCaptacionFondoSepelio = null;
		List<AdendaCaptacion> 	listaCaptacionFondoRetiro = null;
		List<AdendaCaptacion> 	listaCaptacionMantCuenta = null;
		List<AdendaCredito> 	listaCreditos = null;
		try{
			adenda = boAdenda.grabarConvenio(pAdenda);
			
			listaRetencPlla = pAdenda.getListaRetencionPlla();
			if(listaRetencPlla != null){
				grabarListaDinamicaRetencPlla(listaRetencPlla, adenda.getId());
			}
			listaDonacionRegalia = pAdenda.getListaDonacion();
			//Grabar Lista de Donaciones
			if(listaDonacionRegalia!=null){
				grabarListaDinamicaDonacion(listaDonacionRegalia, adenda.getId());
			}
			//Grabar Lista de Firmantes
			listaFirmantes = pAdenda.getListaFirmante();
			if(listaFirmantes!=null){
				grabarListaDinamicaFirmante(listaFirmantes, adenda.getId());
			}
			//Grabar Lista de Captaciones de Aportes
			listaCaptacionAportes = pAdenda.getListaAdendaCaptacionAportacion();
			if(listaCaptacionAportes!=null){
				grabarListaDinamicaAdendaCaptacion(listaCaptacionAportes, adenda.getId());
			}
			//Grabar Lista de Captaciones de Fondo de Sepelio
			listaCaptacionFondoSepelio = pAdenda.getListaAdendaCaptacionFondoSepelio();
			if(listaCaptacionFondoSepelio!=null){
				grabarListaDinamicaAdendaCaptacion(listaCaptacionFondoSepelio, adenda.getId());
			}
			//Grabar Lista de Captaciones de Fondo de Retiro
			listaCaptacionFondoRetiro = pAdenda.getListaAdendaCaptacionFondoRetiro();
			if(listaCaptacionFondoRetiro!=null){
				grabarListaDinamicaAdendaCaptacion(listaCaptacionFondoRetiro, adenda.getId());
			}
			//Grabar Lista de Captaciones de Mant. de Cuenta
			listaCaptacionMantCuenta = pAdenda.getListaAdendaCaptacionMantCuenta();
			if(listaCaptacionMantCuenta!=null){
				grabarListaDinamicaAdendaCaptacion(listaCaptacionMantCuenta, adenda.getId());
			}
			//Grabar Lista de Créditos
			listaCreditos = pAdenda.getListaAdendaCreditos();
			if(listaCreditos!=null){
				grabarListaDinamicaAdendaCredito(listaCreditos, adenda.getId());
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return adenda;
	}
	
	public Adenda modificarConvenio(Adenda pAdenda) throws BusinessException{
		Adenda adenda = null;
		List<RetencionPlanilla> listaRetencPlla;
		List<DonacionRegalia> 	listaDonacionRegalia = null;
		List<Firmante> 			listaFirmantes = null;
		List<AdendaCaptacion> 	listaCaptacionAportes = null;
		List<AdendaCaptacion> 	listaCaptacionFondoSepelio = null;
		List<AdendaCaptacion> 	listaCaptacionFondoRetiro = null;
		List<AdendaCaptacion> 	listaCaptacionMantCuenta = null;
		List<AdendaCredito> 	listaCreditos = null;
		try{
			adenda = boAdenda.modificarConvenio(pAdenda);
			
			listaRetencPlla = pAdenda.getListaRetencionPlla();
			if(listaRetencPlla != null){
				grabarListaDinamicaRetencPlla(listaRetencPlla, adenda.getId());
			}
			listaDonacionRegalia = pAdenda.getListaDonacion();
			//Grabar Lista de Donaciones
			if(listaDonacionRegalia!=null){
				grabarListaDinamicaDonacion(listaDonacionRegalia, adenda.getId());
			}
			//Grabar Lista de Firmantes
			listaFirmantes = pAdenda.getListaFirmante();
			if(listaFirmantes!=null){
				grabarListaDinamicaFirmante(listaFirmantes, adenda.getId());
			}
			//Grabar Lista de Captaciones de Aportes
			listaCaptacionAportes = pAdenda.getListaAdendaCaptacionAportacion();
			if(listaCaptacionAportes!=null){
				grabarListaDinamicaAdendaCaptacion(listaCaptacionAportes, adenda.getId());
			}
			//Grabar Lista de Captaciones de Fondo de Sepelio
			listaCaptacionFondoSepelio = pAdenda.getListaAdendaCaptacionFondoSepelio();
			if(listaCaptacionFondoSepelio!=null){
				grabarListaDinamicaAdendaCaptacion(listaCaptacionFondoSepelio, adenda.getId());
			}
			//Grabar Lista de Captaciones de Fondo de Retiro
			listaCaptacionFondoRetiro = pAdenda.getListaAdendaCaptacionFondoRetiro();
			if(listaCaptacionFondoRetiro!=null){
				grabarListaDinamicaAdendaCaptacion(listaCaptacionFondoRetiro, adenda.getId());
			}
			//Grabar Lista de Captaciones de Mant. de Cuenta
			listaCaptacionMantCuenta = pAdenda.getListaAdendaCaptacionMantCuenta();
			if(listaCaptacionMantCuenta!=null){
				grabarListaDinamicaAdendaCaptacion(listaCaptacionMantCuenta, adenda.getId());
			}
			//Grabar Lista de Créditos
			listaCreditos = pAdenda.getListaAdendaCreditos();
			if(listaCreditos!=null){
				grabarListaDinamicaAdendaCredito(listaCreditos, adenda.getId());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return adenda;
	}
	
	public List<RetencionPlanilla> grabarListaDinamicaRetencPlla(List<RetencionPlanilla> lstRentenPlla, AdendaId pPK) throws BusinessException{
		RetencionPlanilla retenPlla = null;
		RetencionPlanillaId pk = null;
		RetencionPlanilla retenPllaTemp = null;
		try{
			for(int i=0; i<lstRentenPlla.size(); i++){
				retenPlla = (RetencionPlanilla) lstRentenPlla.get(i);
				if(retenPlla.getId()==null || retenPlla.getId().getIntItemRetPlla()==null){
					pk = new RetencionPlanillaId();
					pk.setIntConvenio(pPK.getIntConvenio());
					pk.setIntItemConvenio(pPK.getIntItemConvenio());
					//pk.setIntCodigo(retenPlla.getId().getIntCodigo());
					retenPlla.setId(pk);
					retenPlla = boRetencPlla.grabarRetencionPlla(retenPlla);
				}else{
					retenPllaTemp = boRetencPlla.getRetencionPlanillaPorPK(retenPlla.getId());
					if(retenPllaTemp == null){
						retenPlla = boRetencPlla.grabarRetencionPlla(retenPlla);
					}else{
						retenPlla = boRetencPlla.modificarPerfil(retenPlla);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRentenPlla;
	}
	
	public List<DonacionRegalia> grabarListaDinamicaDonacion(List<DonacionRegalia> lstDonacionRegalia, AdendaId pPK) throws BusinessException{
		DonacionRegalia donacionRegalia = null;
		DonacionRegaliaId pk = null;
		DonacionRegalia donacionRegaliaTemp = null;
		try{
			for(int i=0; i<lstDonacionRegalia.size(); i++){
				donacionRegalia = (DonacionRegalia) lstDonacionRegalia.get(i);
				if(donacionRegalia.getId()==null || donacionRegalia.getId().getIntItemDonacion()==null){
					pk = new DonacionRegaliaId();
					pk.setIntConvenio(pPK.getIntConvenio());
					pk.setIntItemConvenio(pPK.getIntItemConvenio());
					//pk.setIntCodigo(retenPlla.getId().getIntCodigo());
					donacionRegalia.setId(pk);
					donacionRegalia = boDonacion.grabarDonacionRegalia(donacionRegalia);
				}else{
					donacionRegaliaTemp = boDonacion.getDonacionPorPK(donacionRegalia.getId());
					if(donacionRegaliaTemp == null){
						donacionRegalia = boDonacion.grabarDonacionRegalia(donacionRegalia);
					}else{
						donacionRegalia = boDonacion.modificarDonacion(donacionRegalia);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstDonacionRegalia;
	}
	
	public List<Firmante> grabarListaDinamicaFirmante(List<Firmante> lstFirmante, AdendaId pPK) throws BusinessException{
		Firmante firmante = null;
		FirmanteId pk = null;
		Firmante firmanteTemp = null;
		try{
			for(int i=0; i<lstFirmante.size(); i++){
				firmante = (Firmante) lstFirmante.get(i);
				if(firmante.getId()==null || firmante.getId().getIntItemAdendaFirmante()==null){
					pk = new FirmanteId();
					pk.setIntConvenio(pPK.getIntConvenio());
					pk.setIntItemConvenio(pPK.getIntItemConvenio());
					//pk.setIntCodigo(retenPlla.getId().getIntCodigo());
					firmante.setId(pk);
					firmante = boFirmante.grabarFirmante(firmante);
				}else{
					firmanteTemp = boFirmante.getFirmantePorPK(firmante.getId());
					if(firmanteTemp == null){
						firmante = boFirmante.grabarFirmante(firmante);
					}else{
						firmante = boFirmante.modificarFirmante(firmante);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstFirmante;
	}
	
	public List<AdendaCaptacion> grabarListaDinamicaAdendaCaptacion(List<AdendaCaptacion> lstAdendaCaptacion, AdendaId pPK) throws BusinessException{
		AdendaCaptacion adendaCaptacion = null;
		AdendaCaptacionId pk = null;
		AdendaCaptacion adendaCaptacionTemp = null;
		try{
			for(int i=0; i<lstAdendaCaptacion.size(); i++){
				adendaCaptacion = (AdendaCaptacion) lstAdendaCaptacion.get(i);
				//if(adendaCaptacion.getCaptacion().getId()==null){
				if(adendaCaptacion.getId()==null || adendaCaptacion.getId().getIntItem()==null){
					pk = new AdendaCaptacionId();
					pk.setIntConvenio(pPK.getIntConvenio());
					pk.setIntItemConvenio(pPK.getIntItemConvenio());
					pk.setIntPersEmpresaPk(adendaCaptacion.getCaptacion().getId().getIntPersEmpresaPk());
					pk.setIntParaTipoCaptacionCod(adendaCaptacion.getCaptacion().getId().getIntParaTipoCaptacionCod());
					pk.setIntItem(adendaCaptacion.getCaptacion().getId().getIntItem());
					adendaCaptacion.setId(pk);
					adendaCaptacion = boAdendaCaptacion.grabarAdendaCaptacion(adendaCaptacion);
				}else{
					adendaCaptacionTemp = boAdendaCaptacion.getAdendaCaptacionPorPK(adendaCaptacion.getId());
					if(adendaCaptacionTemp == null){
						adendaCaptacion = boAdendaCaptacion.grabarAdendaCaptacion(adendaCaptacion);
					}else{
						adendaCaptacion = boAdendaCaptacion.modificarAdendaCaptacion(adendaCaptacion);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstAdendaCaptacion;
	}
	
	public List<AdendaCredito> grabarListaDinamicaAdendaCredito(List<AdendaCredito> lstAdendaCredito, AdendaId pPK) throws BusinessException{
		AdendaCredito adendaCredito = null;
		AdendaCreditoId pk = null;
		AdendaCredito adendaCreditoTemp = null;
		try{
			for(int i=0; i<lstAdendaCredito.size(); i++){
				adendaCredito = (AdendaCredito) lstAdendaCredito.get(i);
				if(adendaCredito.getId()==null || adendaCredito.getId().getIntItemConvenio()==null){
					pk = new AdendaCreditoId();
					pk.setIntConvenio(pPK.getIntConvenio());
					pk.setIntItemConvenio(pPK.getIntItemConvenio());
					pk.setIntPersEmpresaPk(adendaCredito.getCredito().getId().getIntPersEmpresaPk());
					pk.setIntParaTipoCreditoCod(adendaCredito.getCredito().getId().getIntParaTipoCreditoCod());
					pk.setIntItemCredito(adendaCredito.getCredito().getId().getIntItemCredito());
					adendaCredito.setId(pk);
					adendaCredito = boAdendaCredito.grabarAdendaCredito(adendaCredito);
				}else{
					if(adendaCredito.getId()==null){
						adendaCredito.setId(new AdendaCreditoId());
					}
					adendaCreditoTemp = boAdendaCredito.getAdendaCreditoPorPK(adendaCredito.getId());
					if(adendaCreditoTemp == null){
						pk = new AdendaCreditoId();
						pk.setIntConvenio(pPK.getIntConvenio());
						pk.setIntItemConvenio(pPK.getIntItemConvenio());
						pk.setIntPersEmpresaPk(adendaCredito.getCredito().getId().getIntPersEmpresaPk());
						pk.setIntParaTipoCreditoCod(adendaCredito.getCredito().getId().getIntParaTipoCreditoCod());
						pk.setIntItemCredito(adendaCredito.getCredito().getId().getIntItemCredito());
						adendaCredito.setId(pk);
						adendaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						adendaCredito = boAdendaCredito.grabarAdendaCredito(adendaCredito);
					}else{
						adendaCredito = boAdendaCredito.modificarAdendaCredito(adendaCredito);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstAdendaCredito;
	}
	
	public Adenda getConvenioPorIdConvenio(AdendaId pPK) throws BusinessException {
		Adenda adenda = null;
		List<Adjunto> listaAdendaAdjunto = null;
		List<RetencionPlanilla> listaRetencionPlla = null;
		List<DonacionRegalia> listaDonacion = null;
		List<Firmante> listaFirmantes = null;
		List<AdendaCaptacion> listaCaptacionAportacion = null;
		List<AdendaCaptacion> listaCaptacionFondoSepelio = null;
		List<AdendaCaptacion> listaCaptacionFondoRetiro = null;
		List<AdendaCaptacion> listaCaptacionMantCuenta = null;
		List<AdendaCredito> listaAdendaCredito = null;
		AdendaCaptacionId adendaCaptacionId = null;
		List<Persona> listaRepresentanteLegalEmpresa = null;
		//Persona persona = null;
		Persona perJuridica = null;
		try{
			adenda = boAdenda.getAdendaPorPK(pPK);
			if(adenda!=null){
				PersonaFacadeRemote facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				listaRetencionPlla = boRetencPlla.getListaRetencionPlanillaPorPKAdenda(adenda.getId());
				if(listaRetencionPlla!=null && listaRetencionPlla.size()>0){
					adenda.setListaRetencionPlla(listaRetencionPlla);
				}
				
				listaAdendaAdjunto = boAdjunto.getListaAdjuntoPorPKAdenda(pPK);
				if(listaAdendaAdjunto!=null && listaAdendaAdjunto.size()>0){
					adenda.setListaAdjunto(listaAdendaAdjunto);
				}
				
				listaDonacion = boDonacion.getListaDonacionPorPKAdenda(adenda.getId());
				if(listaDonacion!=null && listaDonacion.size()>0){
					adenda.setListaDonacion(listaDonacion);
				}
				
				listaFirmantes = boFirmante.getListaFirmantePorPKAdenda(adenda.getId());
				if(listaFirmantes!=null && listaFirmantes.size()>0){
					for(Firmante firmante : listaFirmantes){
						listaRepresentanteLegalEmpresa = facadePersona.getListaPersonaNaturalPorPKPersonaEmpresaYTipoVinculo(firmante.getIntPersPersonaEntidadPk(), Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL, Constante.PARAM_EMPRESASESION);
						if(listaRepresentanteLegalEmpresa!=null){
							for(int i=0; i<listaRepresentanteLegalEmpresa.size(); i++){
								Persona persona = listaRepresentanteLegalEmpresa.get(i);
								if(firmante.getIntPersPersonaFirmantePk().equals(persona.getIntIdPersona())){
									for(int j=0; j<persona.getListaDocumento().size(); j++){
										if(persona.getListaDocumento().get(j).getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
											persona.setDocumento(persona.getListaDocumento().get(j));
											break;
										}
									}
									perJuridica = facadePersona.getPersonaJuridicaPorIdPersona(firmante.getIntPersPersonaEntidadPk());
									persona.setJuridica(perJuridica.getJuridica());
									firmante.setPersona(persona);
								}
							}
						}
					}
					adenda.setListaFirmante(listaFirmantes);
				}
				
				adendaCaptacionId = new AdendaCaptacionId();
				adendaCaptacionId.setIntConvenio(adenda.getId().getIntConvenio());
				adendaCaptacionId.setIntItemConvenio(adenda.getId().getIntItemConvenio());
				adendaCaptacionId.setIntPersEmpresaPk(adenda.getIntPersEmpresaPk());
				adendaCaptacionId.setIntParaTipoCaptacionCod(Constante.CAPTACION_APORTACIONES);
				listaCaptacionAportacion = boAdendaCaptacion.getListaAdendaCaptacionPorPKAdenda(adendaCaptacionId);
				if(listaCaptacionAportacion!=null && listaCaptacionAportacion.size()>0){
					adenda.setListaAdendaCaptacionAportacion(listaCaptacionAportacion);
				}
				adendaCaptacionId = new AdendaCaptacionId();
				adendaCaptacionId.setIntConvenio(adenda.getId().getIntConvenio());
				adendaCaptacionId.setIntItemConvenio(adenda.getId().getIntItemConvenio());
				adendaCaptacionId.setIntPersEmpresaPk(adenda.getIntPersEmpresaPk());
				adendaCaptacionId.setIntParaTipoCaptacionCod(Constante.CAPTACION_FDO_SEPELIO);
				listaCaptacionFondoSepelio = boAdendaCaptacion.getListaAdendaCaptacionPorPKAdenda(adendaCaptacionId);
				if(listaCaptacionFondoSepelio!=null && listaCaptacionFondoSepelio.size()>0){
					adenda.setListaAdendaCaptacionFondoSepelio(listaCaptacionFondoSepelio);
				}
				adendaCaptacionId = new AdendaCaptacionId();
				adendaCaptacionId.setIntConvenio(adenda.getId().getIntConvenio());
				adendaCaptacionId.setIntItemConvenio(adenda.getId().getIntItemConvenio());
				adendaCaptacionId.setIntPersEmpresaPk(adenda.getIntPersEmpresaPk());
				adendaCaptacionId.setIntParaTipoCaptacionCod(Constante.CAPTACION_FDO_RETIRO);
				listaCaptacionFondoRetiro = boAdendaCaptacion.getListaAdendaCaptacionPorPKAdenda(adendaCaptacionId);
				if(listaCaptacionFondoRetiro!=null && listaCaptacionFondoRetiro.size()>0){
					adenda.setListaAdendaCaptacionFondoRetiro(listaCaptacionFondoRetiro);
				}
				adendaCaptacionId = new AdendaCaptacionId();
				adendaCaptacionId.setIntConvenio(adenda.getId().getIntConvenio());
				adendaCaptacionId.setIntItemConvenio(adenda.getId().getIntItemConvenio());
				adendaCaptacionId.setIntPersEmpresaPk(adenda.getIntPersEmpresaPk());
				adendaCaptacionId.setIntParaTipoCaptacionCod(Constante.CAPTACION_MANT_CUENTA);
				listaCaptacionMantCuenta = boAdendaCaptacion.getListaAdendaCaptacionPorPKAdenda(adendaCaptacionId);
				if(listaCaptacionMantCuenta!=null && listaCaptacionMantCuenta.size()>0){
					adenda.setListaAdendaCaptacionMantCuenta(listaCaptacionMantCuenta);
				}
				
				listaAdendaCredito = boAdendaCredito.getListaAdendaCreditoPorPKAdenda(adenda.getId());
				if(listaAdendaCredito!=null && listaAdendaCredito.size()>0){
					adenda.setListaAdendaCreditos(listaAdendaCredito);
				}
				
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return adenda;
	}
}
