package pe.com.tumi.servicio.solicitudPrestamo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegranteId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.bo.GarantiaCreditoBO;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;

public class GarantiaCreditoService {
	
	protected static Logger log = Logger.getLogger(GarantiaCreditoService.class);
	
	private GarantiaCreditoBO boGarantiaCredito = (GarantiaCreditoBO)TumiFactory.get(GarantiaCreditoBO.class);

	
	public List<GarantiaCreditoComp> getListaGarantiaCreditoCompPorExpediente(ExpedienteCreditoId pId,
																			  Integer intTipoCuenta) throws BusinessException{
		
		List<GarantiaCreditoComp> lista = new ArrayList<GarantiaCreditoComp>();
		SocioComp socioComp = null;
		GarantiaCreditoComp dto = null;
		CuentaIntegrante cuentaIntegrante = null;
		Cuenta cuenta = null;
		List<SocioComp> listaSocioComp = null;
		List<CuentaConcepto> listaCuentaConcepto = null;
		
		try{
			
			SocioFacadeRemote socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			List<GarantiaCredito> listaGarantiaCredito  =	boGarantiaCredito.getListaPorPkExpedienteCredito(pId);
			CuentaFacadeRemote   cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			ConceptoFacadeRemote cuentaConceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
	    	
			for (GarantiaCredito garantiaCredito : listaGarantiaCredito) {
			   dto = new GarantiaCreditoComp();
			   dto.setGarantiaCredito(garantiaCredito);
			     PersonaFacadeRemote	personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
		  		   Integer intIdPersona = garantiaCredito.getIntPersPersonaGarantePk();
					        Persona	persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
							if(persona!=null){
								if(persona.getListaDocumento()!=null && persona.getListaDocumento().size()>0){
									for (Documento documento : persona.getListaDocumento()) {
										if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
											persona.setDocumento(documento);
											break;
										}
									}
								}
				    socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaTipoCuenta(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
				    															  			persona.getDocumento().getStrNumeroIdentidad(),
							    															Constante.PARAM_EMPRESASESION,
							    															intTipoCuenta);
				    
				    
				    cuentaIntegrante = new CuentaIntegrante();
	        		cuentaIntegrante.setId(new CuentaIntegranteId());
	        		cuentaIntegrante.getId().setIntPersEmpresaPk(garantiaCredito.getId().getIntPersEmpresaPk());
	        		cuentaIntegrante.getId().setIntPersonaIntegrante(persona.getIntIdPersona());
	        		cuentaIntegrante = cuentaFacade.getCuentaIntegrantePorPkSocioTipoCuenta(cuentaIntegrante, intTipoCuenta);
	        		
	        		
	        		if(cuentaIntegrante != null){
	        			cuenta = new Cuenta();
	            		cuenta.setId(new CuentaId());
	            		cuenta.getId().setIntPersEmpresaPk(garantiaCredito.getId().getIntPersEmpresaPk());
	            		cuenta.getId().setIntCuenta(cuentaIntegrante.getId().getIntCuenta());
	            		cuenta = cuentaFacade.getCuentaPorIdCuenta(cuenta);
	            		listaCuentaConcepto = cuentaConceptoFacade.getListaCuentaConceptoPorPkCuenta(cuenta.getId());
	            		if(listaCuentaConcepto!=null){
	            			cuenta.setListaConcepto(listaCuentaConcepto);
	            		}
	            		
	            		socioComp.setCuenta(cuenta);
	            		
	            		if (socioComp.getCuenta().getIntParaSituacionCuentaCod().equals(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)){
	            			if (listaSocioComp == null) listaSocioComp = new ArrayList<SocioComp>();
	            			
	            			dto.setSocioComp(socioComp);
	        				lista.add(dto);
	            		}
	            		
	        		}
				
			 }
		  }
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
}