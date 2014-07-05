package pe.com.tumi.cobranza.cierremensual.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.cierremensual.bo.CierreCobranzaBO;
import pe.com.tumi.cobranza.cierremensual.bo.CierreCobranzaOperacionBO;
import pe.com.tumi.cobranza.cierremensual.bo.CierreCobranzaPlanillaBO;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranza;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaOperacion;
import pe.com.tumi.cobranza.cierremensual.domain.CierreCobranzaPlanilla;
import pe.com.tumi.cobranza.cierremensual.domain.composite.CierreCobranzaComp;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;

public class CierreCobranzaService {
	protected static Logger log = Logger.getLogger(CierreCobranzaService.class);
	
	private CierreCobranzaBO 	     boCierreCobranza = (CierreCobranzaBO)TumiFactory.get(CierreCobranzaBO.class);
	private CierreCobranzaOperacionBO boCierreCobranzaOperacion = (CierreCobranzaOperacionBO)TumiFactory.get(CierreCobranzaOperacionBO.class);
	private CierreCobranzaPlanillaBO boCierreCobranzaPlanilla = (CierreCobranzaPlanillaBO)TumiFactory.get(CierreCobranzaPlanillaBO.class);
	
	public List<CierreCobranzaComp> getListaCierreCobranza(CierreCobranzaComp o) throws BusinessException {
		List<CierreCobranzaComp> lista = null;
		try {
			lista = boCierreCobranza.getListaCierreCobranza(o);
			
			if(lista!=null && !lista.isEmpty()){
				for (CierreCobranzaComp cierreCobranzaComp : lista) {
					cierreCobranzaComp.setIntAnio(new Integer(cierreCobranzaComp.getCierreCobranza().getIntParaPeriodoCierre().toString().substring(0, 4)));
					cierreCobranzaComp.setIntMes(new Integer(cierreCobranzaComp.getCierreCobranza().getIntParaPeriodoCierre().toString().substring(4)));
					String fecReg = null;
					Persona persona = null;
					if(cierreCobranzaComp.getCierreCobranza().getIntParaTipoRegistro().equals(Constante.PARAM_T_TIPO_CIERRE_COBRANZA_OPERACION)
							&& (cierreCobranzaComp.getCierreCobranzaOperacion().getIntParaEstadoCierre().equals(Constante.PARAM_T_TIPOESTADOCIERRE_CERRADO)
									|| cierreCobranzaComp.getCierreCobranzaOperacion().getIntParaEstadoCierre().equals(Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO))){
						fecReg = Constante.sdf2.format(cierreCobranzaComp.getCierreCobranzaOperacion().getTsFechaRegistro());
						cierreCobranzaComp.setStrFechaRegistro(fecReg);
						
					}else if(cierreCobranzaComp.getCierreCobranza().getIntParaTipoRegistro().equals(Constante.PARAM_T_TIPO_CIERRE_COBRANZA_PLANILLA_ENVIADA)
							&& (cierreCobranzaComp.getCierreCobranzaPlanilla().getIntParaEstadoCierre().equals(Constante.PARAM_T_TIPOESTADOCIERRE_CERRADO)
									|| cierreCobranzaComp.getCierreCobranzaPlanilla().getIntParaEstadoCierre().equals(Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO))){
						fecReg = Constante.sdf2.format(cierreCobranzaComp.getCierreCobranzaPlanilla().getTsFechaRegistro());
						cierreCobranzaComp.setStrFechaRegistro(fecReg);
					}else if(cierreCobranzaComp.getCierreCobranza().getIntParaTipoRegistro().equals(Constante.PARAM_T_TIPO_CIERRE_COBRANZA_PLANILLA_EFECTUADA)
							&& (cierreCobranzaComp.getCierreCobranzaPlanilla().getIntParaEstadoCierre().equals(Constante.PARAM_T_TIPOESTADOCIERRE_CERRADO)
									|| cierreCobranzaComp.getCierreCobranzaPlanilla().getIntParaEstadoCierre().equals(Constante.PARAM_T_TIPOESTADOCIERRE_ABIERTO))){
						fecReg = Constante.sdf2.format(cierreCobranzaComp.getCierreCobranzaPlanilla().getTsFechaRegistro());
						cierreCobranzaComp.setStrFechaRegistro(fecReg);
					}else {
						cierreCobranzaComp.setStrFechaRegistro(fecReg);
					}
					PersonaFacadeRemote personaFaceRemote = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
					if(cierreCobranzaComp.getCierreCobranzaOperacion()!=null){
						persona = personaFaceRemote.getPersonaNaturalPorIdPersona(cierreCobranzaComp.getCierreCobranzaOperacion().getIntPersonaRegistro());
						cierreCobranzaComp.setUsuario(persona);
						cierreCobranzaComp.setIntEstado(cierreCobranzaComp.getCierreCobranzaOperacion().getIntParaEstadoCierre());
					} else if(cierreCobranzaComp.getCierreCobranzaPlanilla()!=null){
						persona = personaFaceRemote.getPersonaNaturalPorIdPersona(cierreCobranzaComp.getCierreCobranzaPlanilla().getIntPersonaRegistro());
						cierreCobranzaComp.setUsuario(persona);
						cierreCobranzaComp.setIntEstado(cierreCobranzaComp.getCierreCobranzaPlanilla().getIntParaEstadoCierre());
					} else {
						cierreCobranzaComp.setUsuario(null);
						cierreCobranzaComp.setIntEstado(null);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return lista;
	}
	
	public CierreCobranza grabarCierreCobranza(CierreCobranza o) throws BusinessException {
		CierreCobranza cierreCobranza = null;
		
		try{
			cierreCobranza = boCierreCobranza.grabarCierreCobranza(o);
			if(o.getListCierreOperacion()!=null && o.getListCierreOperacion().size()>0){
				grabarListaDinamicaCierreOperacion(o.getListCierreOperacion());
			}
			
			if(o.getListCierrePlanilla()!=null && !o.getListCierrePlanilla().isEmpty()){
				grabarListaDinamicaCierrePlanilla(o.getListCierrePlanilla());
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cierreCobranza;
	}
	
	public CierreCobranza modificarCierreCobranza(CierreCobranza o) throws BusinessException {
		CierreCobranza cierreCobranza = null;
		try{
			cierreCobranza = boCierreCobranza.modificarCierreCobranza(o);
			if(o.getListCierreOperacion()!=null && o.getListCierreOperacion().size()>0){
				grabarListaDinamicaCierreOperacion(o.getListCierreOperacion());
			}
			
			if(o.getListCierrePlanilla()!=null && !o.getListCierrePlanilla().isEmpty()){
				grabarListaDinamicaCierrePlanilla(o.getListCierrePlanilla());
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cierreCobranza;
	}
	
	public List<CierreCobranzaOperacion> grabarListaDinamicaCierreOperacion(List<CierreCobranzaOperacion> lstCierreCobranzaOperacion) throws BusinessException{
		CierreCobranzaOperacion cierreCobranzaOperacionTemp = null;
		try{
			for(CierreCobranzaOperacion cobranzaOperacion : lstCierreCobranzaOperacion){
				cierreCobranzaOperacionTemp = boCierreCobranzaOperacion.getCierreCobranzaOperacionPorPK(cobranzaOperacion.getId());
				if(cierreCobranzaOperacionTemp == null){
					if(!cobranzaOperacion.getIntParaEstadoCierre().equals(0)){
						cobranzaOperacion.setTsFechaRegistro(new Timestamp(new Date().getTime()));
					}
					boCierreCobranzaOperacion.grabarCierreCobranzaOperacion(cobranzaOperacion);
				}else{
					if(!cobranzaOperacion.getIntParaEstadoCierre().equals(cierreCobranzaOperacionTemp.getIntParaEstadoCierre())){
						cobranzaOperacion.setTsFechaRegistro(new Timestamp(new Date().getTime()));
					}else{
						cobranzaOperacion.setTsFechaRegistro(cierreCobranzaOperacionTemp.getTsFechaRegistro());
					}
					boCierreCobranzaOperacion.modificarCierreCobranzaOperacion(cobranzaOperacion);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCierreCobranzaOperacion;
	}
	
	public List<CierreCobranzaPlanilla> grabarListaDinamicaCierrePlanilla(List<CierreCobranzaPlanilla> lstCierreCobranzaPlanilla) throws BusinessException{
		CierreCobranzaPlanilla cierreCobranzaPlanillaTemp = null;
		
		try{
			for(CierreCobranzaPlanilla cobranzaPlanilla : lstCierreCobranzaPlanilla){
				cierreCobranzaPlanillaTemp = boCierreCobranzaPlanilla.getCierreCobranzaPlanillaPorPK(cobranzaPlanilla);
				if(cierreCobranzaPlanillaTemp == null){
					if(!cobranzaPlanilla.getIntParaEstadoCierre().equals(0)){
						cobranzaPlanilla.setTsFechaRegistro(new Timestamp(new Date().getTime()));
					}
					boCierreCobranzaPlanilla.grabarCierreCobranzaPlanilla(cobranzaPlanilla);
				}else{
					if(!cobranzaPlanilla.getIntParaEstadoCierre().equals(cierreCobranzaPlanillaTemp.getIntParaEstadoCierre())){
						cobranzaPlanilla.setTsFechaRegistro(new Timestamp(new Date().getTime()));
					}else{
						cobranzaPlanilla.setTsFechaRegistro(cierreCobranzaPlanillaTemp.getTsFechaRegistro());
					}
					boCierreCobranzaPlanilla.modificarCierreCobranzaPlanilla(cobranzaPlanilla);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCierreCobranzaPlanilla;
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
}
