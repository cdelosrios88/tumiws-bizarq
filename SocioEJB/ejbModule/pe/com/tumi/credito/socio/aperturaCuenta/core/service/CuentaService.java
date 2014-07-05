package pe.com.tumi.credito.socio.aperturaCuenta.core.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.bo.CuentaBO;
import pe.com.tumi.credito.socio.aperturaCuenta.core.bo.CuentaIntegranteBO;
import pe.com.tumi.credito.socio.aperturaCuenta.core.bo.CuentaIntegrantePermisoBO;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegranteId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrantePermiso;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrantePermisoId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;

public class CuentaService {
	
	protected static Logger log = Logger.getLogger(CuentaService.class);
	
	private CuentaBO boCuenta = (CuentaBO)TumiFactory.get(CuentaBO.class);
	private CuentaIntegranteBO boCuentaIntegrante = (CuentaIntegranteBO)TumiFactory.get(CuentaIntegranteBO.class);
	private CuentaIntegrantePermisoBO boCuentaIntegrantePermiso = (CuentaIntegrantePermisoBO)TumiFactory.get(CuentaIntegrantePermisoBO.class);
	
	public Cuenta grabarCuenta(Cuenta pCuenta, Integer intIdPersona) throws BusinessException{
		Cuenta cuenta = null;
		List<CuentaIntegrante> listaCuentaIntegrante = null;
		List<CuentaConcepto> listaConcepto = null;
		try{
			cuenta = boCuenta.grabar(pCuenta);
			
			listaCuentaIntegrante = pCuenta.getListaIntegrante();
			//Grabar Lista Cuenta Integrante
			if(listaCuentaIntegrante!=null){
				grabarListaDinamicaCuentaIntegrante(listaCuentaIntegrante, cuenta.getId(), intIdPersona);
			}
			cuenta.setIntIdPersona(intIdPersona);
			boCuenta.actualizarNroCuentaYSecuencia(cuenta);
			
			listaConcepto = pCuenta.getListaConcepto();
			//Grabar Lista de Conceptos de Cuenta
			if(listaConcepto!=null){
				grabarCuentaConcepto(listaConcepto, cuenta.getId(), intIdPersona);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuenta;
	}
	
	public Cuenta modificarCuenta(Cuenta pCuenta, Integer intIdPersona) throws BusinessException{
		Cuenta cuenta = null;
		List<CuentaIntegrante> listaCuentaIntegrante = null;
		List<CuentaConcepto> listaConcepto = null;
		try{
			cuenta = boCuenta.modificar(pCuenta);
			
			listaCuentaIntegrante = pCuenta.getListaIntegrante();
			//Grabar Lista Cuenta Integrante
			if(listaCuentaIntegrante!=null){
				grabarListaDinamicaCuentaIntegrante(listaCuentaIntegrante, cuenta.getId(), intIdPersona);
			}
			listaConcepto = pCuenta.getListaConcepto();
			//Grabar Lista de Conceptos de Cuenta
			if(listaConcepto!=null){
				modificarCuentaConcepto(listaConcepto, cuenta.getId(), intIdPersona);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuenta;
	}
	
	public List<CuentaIntegrante> grabarListaDinamicaCuentaIntegrante(List<CuentaIntegrante> lstCuentaIntegrante, CuentaId pPK, Integer intIdPersona) throws BusinessException{
		CuentaIntegrante cuentaIntegrante = null;
		CuentaIntegranteId pk = null;
		CuentaIntegrante cuentaIntegranteTemp = null;
		try{
			for(int i=0; i<lstCuentaIntegrante.size(); i++){
				cuentaIntegrante = (CuentaIntegrante) lstCuentaIntegrante.get(i);
				if(cuentaIntegrante.getId()==null){
					pk = new CuentaIntegranteId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuenta(pPK.getIntCuenta());
					pk.setIntPersonaIntegrante(intIdPersona);
					cuentaIntegrante.setId(pk);
					cuentaIntegrante = boCuentaIntegrante.grabar(cuentaIntegrante);
					grabarListaDinamicaCuentaIntegrantePermiso(cuentaIntegrante.getListaCuentaIntegrantePermiso(), cuentaIntegrante.getId());
				}else{
					cuentaIntegranteTemp = boCuentaIntegrante.getCuentaIntegrantePorPK(cuentaIntegrante.getId());
					if(cuentaIntegranteTemp == null){
						cuentaIntegrante = boCuentaIntegrante.grabar(cuentaIntegrante);
						grabarListaDinamicaCuentaIntegrantePermiso(cuentaIntegrante.getListaCuentaIntegrantePermiso(), cuentaIntegrante.getId());
					}else{
						cuentaIntegrante = boCuentaIntegrante.modificar(cuentaIntegrante);
						grabarListaDinamicaCuentaIntegrantePermiso(cuentaIntegrante.getListaCuentaIntegrantePermiso(), cuentaIntegrante.getId());
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCuentaIntegrante;
	}
	
	public List<CuentaIntegrantePermiso> grabarListaDinamicaCuentaIntegrantePermiso(List<CuentaIntegrantePermiso> lstCuentaIntegrantePermiso, CuentaIntegranteId pPK) throws BusinessException{
		CuentaIntegrantePermiso cuentaIntegrantePermiso = null;
		CuentaIntegrantePermisoId pk = null;
		CuentaIntegrantePermiso cuentaIntegrantePermisoTemp = null;
		try{
			for(int i=0; i<lstCuentaIntegrantePermiso.size(); i++){
				cuentaIntegrantePermiso = (CuentaIntegrantePermiso) lstCuentaIntegrantePermiso.get(i);
				if(cuentaIntegrantePermiso.getId()==null){
					pk = new CuentaIntegrantePermisoId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntCuenta(pPK.getIntCuenta());
					pk.setIntPersonaIntegrante(pPK.getIntPersonaIntegrante());
					cuentaIntegrantePermiso.setId(pk);
					cuentaIntegrantePermiso = boCuentaIntegrantePermiso.grabar(cuentaIntegrantePermiso);
				}else{
					cuentaIntegrantePermisoTemp = boCuentaIntegrantePermiso.getCuentaPorPK(cuentaIntegrantePermiso.getId());
					if(cuentaIntegrantePermisoTemp == null){
						cuentaIntegrantePermiso = boCuentaIntegrantePermiso.grabar(cuentaIntegrantePermiso);
					}else{
						cuentaIntegrantePermiso = boCuentaIntegrantePermiso.modificar(cuentaIntegrantePermiso);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstCuentaIntegrantePermiso;
	}
	
	public void grabarCuentaConcepto(List<CuentaConcepto> lstCuentaConcepto, CuentaId pk, Integer intIdPersona) throws BusinessException{
		ConceptoFacadeRemote conceptoFacade = null;
		
		try{
    		conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
    		for(CuentaConcepto concepto : lstCuentaConcepto){
    			concepto.setId(new CuentaConceptoId());
    			concepto.getId().setIntPersEmpresaPk(pk.getIntPersEmpresaPk());
    			concepto.getId().setIntCuentaPk(pk.getIntCuenta());
    			concepto = conceptoFacade.grabarCuentaConcepto(concepto, intIdPersona);
    		}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public void modificarCuentaConcepto(List<CuentaConcepto> lstCuentaConcepto, CuentaId pk, Integer intIdPersona) throws BusinessException{
		ConceptoFacadeRemote conceptoFacade = null;
		
		try{
    		conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
    		for(CuentaConcepto concepto : lstCuentaConcepto){
    			concepto = conceptoFacade.modificarCuentaConcepto(concepto, intIdPersona);
    		}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	/**
	 * Recupera la cuenta( ListaIntegrante, ListaConcepto) sin importar la situaciond e la cuenta
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public Cuenta getCuentaPorIdCuenta(Cuenta o) throws BusinessException{
		Cuenta cuenta = null;
		List<Cuenta> listCuenta = null;
		List<CuentaIntegrante> listaIntegrante = null;
		List<CuentaConcepto> listaConcepto = null;
		List<CuentaIntegrantePermiso> listaIntegrantePermiso = null;
		ConceptoFacadeRemote conceptoFacade = null;
		try{
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			//cgd - 28.08.2013
			//cuenta = boCuenta.getCuentaPorPK(o.getId());
			// cgd - 11.09.2013
			listCuenta = boCuenta.getListaCuentaPorPkTodoEstado(o.getId());
			if(listCuenta != null && !listCuenta.isEmpty()){
				//for (Cuenta cuentaList : listCuenta) {
					//if(cuentaList.getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						cuenta = new Cuenta();
						cuenta = listCuenta.get(0);
						//break;
					//}
				//}
			}
			
			if(cuenta!=null){
				listaIntegrante = boCuentaIntegrante.getListaCuentaIntegrantePorPKCuenta(o.getId());
				if(listaIntegrante!=null){
					for (CuentaIntegrante cuentaIntegrante : listaIntegrante) {
						listaIntegrantePermiso = boCuentaIntegrantePermiso.getListaCuentaIntegrantePermisoPorCuentaIntegrante(cuentaIntegrante.getId());
						cuentaIntegrante.setListaCuentaIntegrantePermiso(listaIntegrantePermiso);
					}
					cuenta.setListaIntegrante(listaIntegrante);
				}
				
				listaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuenta.getId());
				if(listaConcepto!=null){
					cuenta.setListaConcepto(listaConcepto);
				}
			}
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuenta;
	}
	
	public Cuenta getCuentaActualPorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		Cuenta cuentaActual = null;
		try{

			List<CuentaIntegrante> listaCuentaIntegrante = boCuentaIntegrante.getCuentaIntegrantePorIdPersona(intIdPersona, intIdEmpresa);
			List<Cuenta> listaCuenta = new ArrayList<Cuenta>();
		
			HashSet<Integer> hashSetIntCuenta = new HashSet<Integer>();
			for(CuentaIntegrante cuentaIntegrante : listaCuentaIntegrante){
				if(cuentaIntegrante.getIntParaTipoIntegranteCod().equals(Constante.TIPOINTEGRANTE_ADMINISTRADOR)){
					hashSetIntCuenta.add(cuentaIntegrante.getId().getIntCuenta());
				}
			}
			
			for(Integer intCuenta : hashSetIntCuenta){
				CuentaId cuentaId = new CuentaId();
				cuentaId.setIntPersEmpresaPk(intIdEmpresa);
				cuentaId.setIntCuenta(intCuenta);
				Cuenta cuenta = new Cuenta();
				cuenta.setId(cuentaId);
				cuenta = boCuenta.getCuentaPorPK(cuentaId);
				if(cuenta!=null){
					listaCuenta.add(cuenta);					
				}				
			}
			
			for(Cuenta cuenta : listaCuenta){
				if(cuenta.getIntParaSituacionCuentaCod().equals(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)){
					cuentaActual = cuenta;
					break;
				}
			}			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuentaActual;
	}
	
	public CuentaIntegrante getCuentaIntegrantePermisoPorIdCuentaIntegrante(CuentaIntegrante o) throws BusinessException{
		CuentaIntegrante cuentaIntegrante = null;
		List<CuentaIntegrantePermiso> listaIntegrantePermiso = null;
		try{
			//CGD - 11.09.2013
			//cuentaIntegrante = boCuentaIntegrante.getCuentaIntegrantePorPKSocio(o.getId());
			cuentaIntegrante = boCuentaIntegrante.getIdCtaActivaXPkSocio(o.getId());
			if(cuentaIntegrante!=null){
				listaIntegrantePermiso = boCuentaIntegrantePermiso.getListaCuentaIntegrantePermisoPorCuentaIntegrante(cuentaIntegrante.getId());
				if(listaIntegrantePermiso!=null){
					cuentaIntegrante.setListaCuentaIntegrantePermiso(listaIntegrantePermiso);
				}
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuentaIntegrante;
	}


}