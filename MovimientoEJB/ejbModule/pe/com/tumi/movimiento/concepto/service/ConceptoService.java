package pe.com.tumi.movimiento.concepto.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.soap.providers.com.Log;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.bo.ConceptoDetallePagoBO;
import pe.com.tumi.movimiento.concepto.bo.ConceptoPagoBO;
import pe.com.tumi.movimiento.concepto.bo.CronogramaBO;
import pe.com.tumi.movimiento.concepto.bo.CuentaConceptoBO;
import pe.com.tumi.movimiento.concepto.bo.CuentaConceptoDetalleBO;
import pe.com.tumi.movimiento.concepto.bo.CuentaDetalleBeneficioBO;
import pe.com.tumi.movimiento.concepto.bo.EstadoExpedienteBO;
import pe.com.tumi.movimiento.concepto.bo.ExpedienteBO;
import pe.com.tumi.movimiento.concepto.bo.InteresCanceladoBO;
import pe.com.tumi.movimiento.concepto.bo.InteresProvisionadoBO;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficioId;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionado;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeLocal;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.DocumentoPK;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

public class ConceptoService {
	private CuentaConceptoBO boConcepto = (CuentaConceptoBO)TumiFactory.get(CuentaConceptoBO.class);
	private CuentaConceptoDetalleBO boConceptoDetalle = (CuentaConceptoDetalleBO)TumiFactory.get(CuentaConceptoDetalleBO.class);
	private CuentaDetalleBeneficioBO boCuentaDetalleBeneficio = (CuentaDetalleBeneficioBO)TumiFactory.get(CuentaDetalleBeneficioBO.class);
	private ExpedienteBO boExpediente = (ExpedienteBO)TumiFactory.get(ExpedienteBO.class);
	private CronogramaBO boCronograma = (CronogramaBO)TumiFactory.get(CronogramaBO.class);
	private EstadoExpedienteBO boEstado = (EstadoExpedienteBO)TumiFactory.get(EstadoExpedienteBO.class);
	private InteresProvisionadoBO boProvisionado  = (InteresProvisionadoBO)TumiFactory.get(InteresProvisionadoBO.class);
	private ConceptoPagoBO boConceptoPago = (ConceptoPagoBO)TumiFactory.get(ConceptoPagoBO.class);	
	private ConceptoDetallePagoBO boConceptoDetallePago = (ConceptoDetallePagoBO)TumiFactory.get(ConceptoDetallePagoBO.class);
	private InteresCanceladoBO boInteresCancelado = (InteresCanceladoBO)TumiFactory.get(InteresCanceladoBO.class);
	private EstadoExpedienteBO boEstadoExpediente = (EstadoExpedienteBO)TumiFactory.get(EstadoExpedienteBO.class);
		
	public CuentaConcepto grabarCuentaConcepto(CuentaConcepto pCuentaConcepto, Integer intIdPersona) throws BusinessException{
		CuentaConcepto cuentaConcepto = null;
		CuentaConceptoDetalle cuentaConceptoDetalle = null;
		//List<CuentaConceptoDetalle> listaConceptoDetalle = null;
		List<CuentaDetalleBeneficio> listaCuentaDetalleBeneficio = null;
		try{
			cuentaConcepto = boConcepto.grabarCuentaConcepto(pCuentaConcepto);
			
			cuentaConceptoDetalle = pCuentaConcepto.getDetalle();
			//Grabar Lista Cuenta Detalle
			if(cuentaConceptoDetalle!=null){
				cuentaConceptoDetalle.setId(new CuentaConceptoDetalleId());
				cuentaConceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
				cuentaConceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
				cuentaConceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());
				cuentaConceptoDetalle = boConceptoDetalle.grabarCuentaConceptoDetalle(cuentaConceptoDetalle);
			}
			//Grabar Lista Cuenta Detalle Beneficiario
			listaCuentaDetalleBeneficio = pCuentaConcepto.getListaCuentaDetalleBeneficio();
			if(listaCuentaDetalleBeneficio!=null){
				grabarListaDinamicaCuentaDetalleBeneficio(listaCuentaDetalleBeneficio, cuentaConcepto.getId(), intIdPersona);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuentaConcepto;
	}
	
	
	public CuentaConcepto modificarCuentaConcepto(CuentaConcepto pCuentaConcepto, Integer intIdPersona) throws BusinessException{
		CuentaConcepto cuentaConcepto = null;
		//List<CuentaConceptoDetalle> listaConceptoDetalle = null;
		CuentaConceptoDetalle cuentaConceptoDetalle = null;
		List<CuentaDetalleBeneficio> listaCuentaDetalleBeneficio = null;
		Boolean blnGrabarBenef = Boolean.TRUE;
		try{
			cuentaConcepto = boConcepto.modificarCuentaConcepto(pCuentaConcepto);
			
			//cuentaConceptoDetalle = pCuentaConcepto.getDetalle();
			cuentaConceptoDetalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);
			//Grabar Lista Cuenta Detalle
			if(cuentaConceptoDetalle!=null){
				/*cuentaConceptoDetalle.setId(new CuentaConceptoDetalleId());
				cuentaConceptoDetalle.getId().setIntPersEmpresaPk(cuentaConcepto.getId().getIntPersEmpresaPk());
				cuentaConceptoDetalle.getId().setIntCuentaPk(cuentaConcepto.getId().getIntCuentaPk());
				cuentaConceptoDetalle.getId().setIntItemCuentaConcepto(cuentaConcepto.getId().getIntItemCuentaConcepto());*/
				cuentaConceptoDetalle = boConceptoDetalle.modificarCuentaConceptoDetalle(cuentaConceptoDetalle);
				
				if(cuentaConceptoDetalle != null){
					if(cuentaConceptoDetalle.getIntParaTipoConceptoCod().compareTo(Constante.CAPTACION_MANT_CUENTA)!=0){
						blnGrabarBenef = Boolean.FALSE;
						// cgd - 25.07.2013 - se valida q sea distinto  a Mantenimiento de cuenta.
						//Grabar Lista Cuenta Detalle Beneficiario
						listaCuentaDetalleBeneficio = pCuentaConcepto.getListaCuentaDetalleBeneficio();
						if(listaCuentaDetalleBeneficio!=null){
							grabarListaDinamicaCuentaDetalleBeneficio(listaCuentaDetalleBeneficio, cuentaConcepto.getId(), intIdPersona);
						}
					}
				}

			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuentaConcepto;
	}
	
	/*
	public CuentaConceptoDetalle grabarConceptoDetalle(CuentaConceptoDetalle conceptoDetalle, CuentaConceptoId pPK) throws BusinessException{
		CuentaConceptoDetalle dto = null;
		
		dto = boConcepto.grabarCuentaConcepto(pCuentaConcepto);
		
		return dto;
	}*/
	/*public List<CuentaConceptoDetalle> grabarListaDinamicaCuentaConceptoDetalle(List<CuentaConceptoDetalle> lstCuentaConceptoDetalle, CuentaConceptoId pPK) throws BusinessException{
		CuentaConceptoDetalle cuentaConceptoDetalle = null;
		CuentaConceptoDetalleId pk = null;
		CuentaConceptoDetalle cuentaIntegranteTemp = null;
		try{
			for(int i=0; i<lstCuentaConceptoDetalle.size(); i++){
				cuentaConceptoDetalle = (CuentaConceptoDetalle) lstCuentaConceptoDetalle.get(i);
				if(cuentaConceptoDetalle.getId()==null || cuentaConceptoDetalle.getId().getIntItemCtaCptoDet()==null){
					pk = new CuentaConceptoDetalleId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemCuentaConcepto(pPK.getIntItemCuentaConcepto());
					cuentaConceptoDetalle.setId(pk);
					
					cuentaConceptoDetalle = boConceptoDetalle.grabarCuentaConceptoDetalle(cuentaConceptoDetalle);
				}else{
					cuentaIntegranteTemp = boConceptoDetalle.getCuentaConceptoDetallePorPK(cuentaConceptoDetalle.getId());
					if(cuentaIntegranteTemp == null){
						cuentaConceptoDetalle = boConceptoDetalle.grabarCuentaConceptoDetalle(cuentaConceptoDetalle);
					}else{
						cuentaConceptoDetalle = boConceptoDetalle.modificarCuentaConceptoDetalle(cuentaConceptoDetalle);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCuentaConceptoDetalle;
	}*/
	
	public List<CuentaDetalleBeneficio> grabarListaDinamicaCuentaDetalleBeneficio(List<CuentaDetalleBeneficio> lstCuentaDetalleBeneficio, CuentaConceptoId pPK, Integer intIdSocioPrincipal) throws BusinessException{
		CuentaDetalleBeneficio cuentaDetalleBeneficio = null;
		CuentaDetalleBeneficioId pk = null;
		CuentaDetalleBeneficio cuentaDetalleTemp = null;
		Persona persona = null;
		PersonaFacadeRemote personaFacade = null;
		Vinculo vinculoTemp = null;
		Vinculo vinculo = null;
		Documento documento = null;
		List<CuentaDetalleBeneficio> listaCuentaDetalleBeneficioRegistrado = null;
		ConceptoFacadeLocal conceptoFacadeLocal = null;
		try{
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			conceptoFacadeLocal = (ConceptoFacadeLocal)EJBFactory.getLocal(ConceptoFacadeLocal.class);

			for(int i=0; i<lstCuentaDetalleBeneficio.size(); i++){
				cuentaDetalleBeneficio = (CuentaDetalleBeneficio) lstCuentaDetalleBeneficio.get(i);
				System.out.println("XXXXXXXXXXX grabarListaDinamicaCuentaDetalleBeneficio XXXXXXXXXXXXXXX"+ i +"XXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println(""+cuentaDetalleBeneficio.getIntItemVinculo());
				System.out.println(""+cuentaDetalleBeneficio.getIntItemConcepto());
				System.out.println(""+cuentaDetalleBeneficio.getBdPorcentaje());
				System.out.println(""+cuentaDetalleBeneficio.getPersona().getIntIdPersona());
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

				if(cuentaDetalleBeneficio.getId()==null || cuentaDetalleBeneficio.getId().getIntItemBeneficio()==null){
					pk = new CuentaDetalleBeneficioId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuentaPk(pPK.getIntCuentaPk());
					pk.setIntItemCuentaConcepto(pPK.getIntItemCuentaConcepto());
					cuentaDetalleBeneficio.setId(pk);
					cuentaDetalleBeneficio.setTsFecharegistro(new Timestamp((new Date()).getTime()));
					//Si la persona no existe en su totalidad en la bd
					if(cuentaDetalleBeneficio.getPersona().getIntIdPersona()==null){
						documento = new Documento();
						documento.setId(new DocumentoPK());
						documento.setIntTipoIdentidadCod(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
						documento.setStrNumeroIdentidad(cuentaDetalleBeneficio.getPersona().getDocumento().getStrNumeroIdentidad());
						documento.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						if(cuentaDetalleBeneficio.getPersona().getListaDocumento()==null){
							cuentaDetalleBeneficio.getPersona().setListaDocumento(new ArrayList<Documento>());
							cuentaDetalleBeneficio.getPersona().getListaDocumento().add(documento);
						}
						persona = personaFacade.grabarPersonaNaturalTotal(cuentaDetalleBeneficio.getPersona());
					}else{
						// CGD - COMENTADO POR PRUEBAS ... XXX
						//if(i==0)
						persona = personaFacade.modificarPersonaNaturalTotal(cuentaDetalleBeneficio.getPersona());
					}
					
					if(persona==null) persona = new Persona();
					//La persona existe pero no en la tabla de Vínculo
					vinculoTemp = new Vinculo();
					vinculoTemp.setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
					vinculoTemp.setIntIdPersona(intIdSocioPrincipal);
					vinculoTemp.setIntEmpresaVinculo(Constante.PARAM_EMPRESASESION);
					vinculoTemp.setIntPersonaVinculo(persona.getIntIdPersona()==null?cuentaDetalleBeneficio.getPersona().getIntIdPersona():persona.getIntIdPersona());
					vinculoTemp = personaFacade.getVinculoPersona(vinculoTemp);
					if(vinculoTemp==null){
						vinculo = new Vinculo();
						vinculo.setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
						vinculo.setIntIdPersona(intIdSocioPrincipal);
						vinculo.setIntTipoVinculoCod(cuentaDetalleBeneficio.getPersona().getPersonaEmpresa().getVinculo().getIntTipoVinculoCod());
						vinculo.setIntEmpresaVinculo(Constante.PARAM_EMPRESASESION);
						vinculo.setIntPersonaVinculo(persona.getIntIdPersona()==null?cuentaDetalleBeneficio.getPersona().getIntIdPersona():persona.getIntIdPersona());
						vinculo = personaFacade.grabarVinculoPersona(vinculo);
						cuentaDetalleBeneficio.setIntItemVinculo(vinculo.getIntItemVinculo());
					}else{
						vinculo = new Vinculo();
						vinculo.setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
						vinculo.setIntIdPersona(intIdSocioPrincipal);
						vinculo.setIntEmpresaVinculo(Constante.PARAM_EMPRESASESION);
						vinculo.setIntPersonaVinculo(persona.getIntIdPersona()==null?cuentaDetalleBeneficio.getPersona().getIntIdPersona():persona.getIntIdPersona());
						vinculo = personaFacade.getVinculoPersona(vinculo);
						cuentaDetalleBeneficio.setIntItemVinculo(vinculo.getIntItemVinculo());
					}
					
					if(cuentaDetalleBeneficio.getPersona().getPersonaEmpresa().getVinculo().getIntItemVinculo()!=null){
						persona = cuentaDetalleBeneficio.getPersona();
						cuentaDetalleBeneficio.setIntItemVinculo(cuentaDetalleBeneficio.getPersona().getPersonaEmpresa().getVinculo().getIntItemVinculo());
					}
					
					cuentaDetalleBeneficio = boCuentaDetalleBeneficio.grabarCuentaDetalleBeneficio(cuentaDetalleBeneficio);
				}else{
					cuentaDetalleTemp = boCuentaDetalleBeneficio.getCuentaDetalleBeneficioPorPK(cuentaDetalleBeneficio.getId());
					if(cuentaDetalleTemp == null){
						if(cuentaDetalleBeneficio.getPersona().getIntIdPersona()==null){
							persona = personaFacade.modificarPersonaNaturalTotal(cuentaDetalleBeneficio.getPersona());
						}
						if(persona==null) persona = new Persona();
						//La persona existe pero no en la tabla de Vínculo
						vinculoTemp = new Vinculo();
						vinculoTemp.setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
						vinculoTemp.setIntIdPersona(intIdSocioPrincipal);
						vinculoTemp.setIntEmpresaVinculo(Constante.PARAM_EMPRESASESION);
						vinculoTemp.setIntPersonaVinculo(persona.getIntIdPersona()==null?cuentaDetalleBeneficio.getPersona().getIntIdPersona():persona.getIntIdPersona());
						vinculoTemp = personaFacade.getVinculoPersona(vinculoTemp);
						if(vinculoTemp==null){
							vinculo = new Vinculo();
							vinculo.setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
							vinculo.setIntIdPersona(intIdSocioPrincipal);
							vinculo.setIntTipoVinculoCod(cuentaDetalleBeneficio.getPersona().getPersonaEmpresa().getVinculo().getIntTipoVinculoCod());
							vinculo.setIntEmpresaVinculo(Constante.PARAM_EMPRESASESION);
							vinculo.setIntPersonaVinculo(persona.getIntIdPersona()==null?cuentaDetalleBeneficio.getPersona().getIntIdPersona():persona.getIntIdPersona());
							vinculo = personaFacade.grabarVinculoPersona(vinculo);
							cuentaDetalleBeneficio.setIntItemVinculo(vinculo.getIntItemVinculo());
						}
						
						if(cuentaDetalleBeneficio.getPersona().getPersonaEmpresa().getVinculo().getIntItemVinculo()!=null){
							persona = cuentaDetalleBeneficio.getPersona();
							cuentaDetalleBeneficio.setIntItemVinculo(cuentaDetalleBeneficio.getPersona().getPersonaEmpresa().getVinculo().getIntItemVinculo());
						}
						
						cuentaDetalleBeneficio = boCuentaDetalleBeneficio.grabarCuentaDetalleBeneficio(cuentaDetalleBeneficio);
					}else{
						cuentaDetalleBeneficio = boCuentaDetalleBeneficio.modificarCuentaDetalleBeneficio(cuentaDetalleBeneficio);
					}
				}
			}
		}
		catch(BusinessException e){
			throw e;
		}
			catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCuentaDetalleBeneficio;
	}
	
	public CuentaConcepto getCuentaConceptoPorPk(CuentaConceptoId pId) throws BusinessException{
		CuentaConcepto cuentaConcepto = null;
		List<CuentaConceptoDetalle> listaConceptoDetalle = null;
		List<CuentaDetalleBeneficio> listaCuentaDetalleBeneficio = null;
		try{
			cuentaConcepto = boConcepto.getCuentaConceptoPorPK(pId);
			if(cuentaConcepto != null){
				listaConceptoDetalle = boConceptoDetalle.getListaCuentaConceptoPorPKCuenta(pId);
				if(listaConceptoDetalle!=null && listaConceptoDetalle.size()>0){
					cuentaConcepto.setListaCuentaConceptoDetalle(listaConceptoDetalle);
					cuentaConcepto.setDetalle(listaConceptoDetalle.get(0));
				}
				listaCuentaDetalleBeneficio = boCuentaDetalleBeneficio.getListaCuentaDetalleBeneficioPorPKCuenta(pId.getIntPersEmpresaPk(), pId.getIntCuentaPk());
				if(listaCuentaDetalleBeneficio!=null && listaCuentaDetalleBeneficio.size()>0){
					cuentaConcepto.setListaCuentaDetalleBeneficio(listaCuentaDetalleBeneficio);
				}
			}
			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return cuentaConcepto;
	}
	
	public CuentaDetalleBeneficio modificarCuentaDetalleBeneficio(CuentaDetalleBeneficio o) throws BusinessException{
		CuentaDetalleBeneficio dto = null;
		try{
			dto = boCuentaDetalleBeneficio.modificarCuentaDetalleBeneficio(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaConcepto getCuentaConceptoYUltimoDetallaPorId(CuentaConceptoId cuentaConceptoId) throws BusinessException{
		CuentaConcepto cuentaConcepto = null;
		List<CuentaConceptoDetalle> listaCuentaConceptoDetalle = null;
		try{
			CaptacionFacadeRemote captacionFacade = (CaptacionFacadeRemote) EJBFactory.getRemote(CaptacionFacadeRemote.class);
			
			cuentaConcepto = boConcepto.getCuentaConceptoPorPK(cuentaConceptoId);
			if(cuentaConcepto == null){
				return cuentaConcepto;			
			}
			listaCuentaConceptoDetalle = boConceptoDetalle.getListaCuentaConceptoPorPKCuenta(cuentaConceptoId);
			if(listaCuentaConceptoDetalle!=null && listaCuentaConceptoDetalle.size()>0){
				cuentaConcepto.setListaCuentaConceptoDetalle(listaCuentaConceptoDetalle);
				CuentaConceptoDetalle ultimoCuentaConceptoDetalle = null;
				if(listaCuentaConceptoDetalle.size()==1){
					ultimoCuentaConceptoDetalle = listaCuentaConceptoDetalle.get(0);					
				}else{
					//Ordenamos por fecha inicio
					Collections.sort(listaCuentaConceptoDetalle, new Comparator<CuentaConceptoDetalle>(){
						public int compare(CuentaConceptoDetalle uno, CuentaConceptoDetalle otro) {
							return uno.getTsInicio().compareTo(otro.getTsInicio());
						}
					});
					ultimoCuentaConceptoDetalle = listaCuentaConceptoDetalle.get(listaCuentaConceptoDetalle.size()-1);
				}
				CaptacionId captacionId = new CaptacionId();
				captacionId.setIntPersEmpresaPk(ultimoCuentaConceptoDetalle.getId().getIntPersEmpresaPk());
				captacionId.setIntParaTipoCaptacionCod(ultimoCuentaConceptoDetalle.getIntParaTipoConceptoCod());
				captacionId.setIntItem(ultimoCuentaConceptoDetalle.getIntItemConcepto());
				
				ultimoCuentaConceptoDetalle.setCaptacion(captacionFacade.listarCaptacionPorPK(captacionId));
				cuentaConcepto.setDetalle(ultimoCuentaConceptoDetalle);
			}
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return cuentaConcepto;
	}
	
	
	
	/**
	 * Graba El expediente, Estado Inicial y Cronograma.
	 * @param expedienteMov
	 * @return
	 * @throws BusinessException
	 */
	public Expediente grabarExpedienteCompleto(Expediente expedienteMov)throws BusinessException{
		List<EstadoExpediente> listaEstadosExpediente = null;
		List<Cronograma> listaCronograma = null;
		List<EstadoExpediente> listaEstadosExpedienteTemp = null;
		List<Cronograma> listaCronogramaTemp = null;

		try {
			
			if(expedienteMov != null){
				expedienteMov = boExpediente.grabarExpediente(expedienteMov);
				
				listaCronograma = expedienteMov.getListaCronograma();
				//Grabamos el crnograma
				if(listaCronograma != null && !listaCronograma.isEmpty()){
					listaCronogramaTemp = new ArrayList<Cronograma>();
					for (Cronograma cronograma : listaCronograma) {
						
						cronograma = boCronograma.grabarCronograma(cronograma);
						if(cronograma != null){
							listaCronogramaTemp.add(cronograma);
						}
					}
					expedienteMov.getListaCronograma().clear();
					expedienteMov.setListaCronograma(listaCronogramaTemp);
				}
				
				listaEstadosExpediente = expedienteMov.getListaEstadosExpediente();
				//Grabamos estados
				if(listaEstadosExpediente != null && !listaEstadosExpediente.isEmpty()){
					listaEstadosExpedienteTemp = new ArrayList<EstadoExpediente>();
					for (EstadoExpediente estado : listaEstadosExpediente) {
						estado = boEstado.grabar(estado);
						if(estado != null){
							listaEstadosExpedienteTemp.add(estado);
						}
					}
					expedienteMov.getListaEstadosExpediente().clear();
					expedienteMov.setListaEstadosExpediente(listaEstadosExpedienteTemp);
				}	
			}

		}catch(BusinessException e){
			System.out.println("Error BusinessException - en grabarExpedienteCompleto ---> "+e);
   			throw e;
   		}catch(Exception e){
   			System.out.println("Error Exception - en grabarExpedienteCompleto ---> "+e);
   			throw new BusinessException(e);
   		}

		return expedienteMov;
	}
	
	public InteresProvisionado grabarInteresProvisionado(InteresProvisionado interesProvisionado) throws BusinessException{
		InteresProvisionado interesP=null;
		try{
			interesP = boProvisionado.grabarInteresProvisionado(interesProvisionado);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return interesP;
	}
	
	public ConceptoPago grabarConceptoPago(ConceptoPago conceptoPago) throws BusinessException{
		ConceptoPago conceptoP=null;
		try{
			conceptoP = boConceptoPago.grabar(conceptoPago);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return conceptoP;
	}
	
	public ConceptoDetallePago grabarConceptoDetallePago(ConceptoDetallePago conceptoDetallePago) throws BusinessException{
		ConceptoDetallePago detalleP=null;
		try{
			detalleP = boConceptoDetallePago.grabar(conceptoDetallePago);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return detalleP;
	}
	public InteresCancelado grabarInteresCancelado(InteresCancelado interesCancelado) throws BusinessException{
		InteresCancelado interesC=null;
		try{
			interesC = boInteresCancelado.grabar(interesCancelado);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return interesC;
	}
	
	public EstadoExpediente grabarEstadoExpediente(EstadoExpediente estadoExpediente) throws BusinessException{		
		
		EstadoExpediente estadoE=null;
		try{
			estadoE = boEstadoExpediente.grabar(estadoExpediente);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return estadoE;
	}
	
	/**
	 * 
	 * @param cuentaDetalleBeneficio
	 * @return
	 * @throws BusinessException
	 */
	public CuentaDetalleBeneficio grabarCuentaDetalleBeneficio(CuentaDetalleBeneficio cuentaDetalleBeneficio) throws BusinessException{
		CuentaDetalleBeneficio dto = null;
		try{
			dto = boCuentaDetalleBeneficio.grabarCuentaDetalleBeneficio(cuentaDetalleBeneficio);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
}