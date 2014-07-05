package pe.com.tumi.credito.socio.aperturaCuenta.core.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.credito.socio.aperturaCuenta.core.bo.CuentaBO;
import pe.com.tumi.credito.socio.aperturaCuenta.core.bo.CuentaIntegranteBO;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegranteId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.service.CuentaService;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

/**
 * Session Bean implementation class CuentaFacade
 */
@Stateless
public class CuentaFacade extends TumiFacade implements CuentaFacadeRemote, CuentaFacadeLocal {
       
	private CuentaService cuentaService = (CuentaService)TumiFactory.get(CuentaService.class);
	private CuentaBO boCuenta = (CuentaBO)TumiFactory.get(CuentaBO.class);
	private CuentaIntegranteBO boCuentaIntegrante = (CuentaIntegranteBO)TumiFactory.get(CuentaIntegranteBO.class);
	
    public Cuenta grabarCuenta(Cuenta o, Integer intIdPersona) throws BusinessException{
    	Cuenta cuenta = null;
    	try{
    		cuenta = cuentaService.grabarCuenta(o, intIdPersona);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
    	return cuenta;
    }
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CuentaIntegrante getCuentaIntegrantePorPkSocio(CuentaIntegrante o) throws BusinessException{
    	CuentaIntegrante cuentaIntegrante = null;
    	try{
    		cuentaIntegrante = cuentaService.getCuentaIntegrantePermisoPorIdCuentaIntegrante(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuentaIntegrante;
    }
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CuentaIntegrante getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(SocioPK pPK,Integer intParaTipoCuenta,Integer intParaTipoIntegrante) throws BusinessException{
    	CuentaIntegrante cuentaIntegrante = null;
    	try{
    		cuentaIntegrante = boCuentaIntegrante.getCuentaIntegrantePorPKSocioYTipoCuentaYTipoIntegrante(pPK, intParaTipoCuenta, intParaTipoIntegrante);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuentaIntegrante;
    }
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getCsvPersonaPorEmpresaYTipoIntegranteYINCuenta(Integer intEmpresa,Integer intTipoIntegrante,String strINCuenta) throws BusinessException{
    	String strEscalar = null;
    	try{
    		strEscalar = boCuentaIntegrante.getCsvPersonaPorEmpresaYTipoIntegranteYINCuenta(intEmpresa,intTipoIntegrante,strINCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return strEscalar;
    }
    
    /**
     * Recupera la cuenta( ListaIntegrante, ListaConcepto) sin importar la situaciond e la cuenta
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Cuenta getCuentaPorIdCuenta(Cuenta o) throws BusinessException{
    	Cuenta cuenta = null;
    	try{
    		cuenta = cuentaService.getCuentaPorIdCuenta(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuenta;
    }
    
    public Cuenta modificarCuenta(Cuenta o, Integer intIdPersona) throws BusinessException{
    	Cuenta cuenta = null;
    	try{
    		cuenta = cuentaService.modificarCuenta(o, intIdPersona);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
    	return cuenta;
    }
    
    public Cuenta modificarCuenta(Cuenta o) throws BusinessException{
    	Cuenta cuenta = null;
    	try{
    		cuenta = boCuenta.modificar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
    	return cuenta;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CuentaIntegrante> getCuentaIntegrantePorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
    	List<CuentaIntegrante> lista = null;
    	try{
    		lista = boCuentaIntegrante.getCuentaIntegrantePorIdPersona(intIdPersona, intIdEmpresa);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Cuenta getCuentaPorId(CuentaId cuentaId) throws BusinessException{
    	Cuenta dto = null;
    	try{
    		dto = boCuenta.getCuentaPorPK(cuentaId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Cuenta getCuentaActualPorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
    	Cuenta dto = null;
    	try{
    		System.out.println("intIdPersona:"+intIdPersona);
    		System.out.println("intIdEmpresa:"+intIdEmpresa);
    		dto = cuentaService.getCuentaActualPorIdPersona(intIdPersona, intIdEmpresa);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
    }
    
    public List<CuentaIntegrante> getListaCuentaIntegrantePorPKCuenta(CuentaId pPK) throws BusinessException{
		List<CuentaIntegrante> lista = null;
		try{
			lista = boCuentaIntegrante.getListaCuentaIntegrantePorPKCuenta(pPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
    
    public Cuenta getCuentaPorPkYSituacion(Cuenta cuenta) throws BusinessException{
    	Cuenta dto = null;
    	try{
    		dto = boCuenta.getCuentaPorPKYSituacion(cuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
    }
    
    
    /**
	 * Recupera List<CuentaIntegrante> en base a intPersEmpresaPk y intPersonaIntegrante.
	 * Y cuyo estado de cuenta sean 1.
	 * @param pPK
	 * @return
	 * @throws BusinessException
	 */
    public List<CuentaIntegrante> getCuentaIntegrantePorPkPersona(CuentaIntegranteId o) throws BusinessException{
    	List<CuentaIntegrante> lista = null;
    	try{
    		lista = boCuentaIntegrante.getCuentaIntegrantePorPkPersona(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
    }
    
    public List<CuentaIntegrante> getListaCuentaIntegrantePorCuenta(CuentaId o) throws BusinessException{
    	List<CuentaIntegrante> lista = null;
    	try{
    		lista = boCuentaIntegrante.getListaCuentaIntegrantePorCuenta(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
    }
    /*
	@Override
	public CuentaIntegrante getCuentaIntegrantePorPKSocioYTipoCuenta(
			SocioPK pPK, Integer intParaTipoCuenta) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	/**
	 * Recupera la cuenta sin importar el estado
	 */
	 public Cuenta getListaCuentaPorPkTodoEstado(CuentaId cuentaId) throws BusinessException{
		 Cuenta dto = null;
		 List<Cuenta> lista = null;
    	try{
    		lista = boCuenta.getListaCuentaPorPkTodoEstado(cuentaId);
    		if(lista != null && !lista.isEmpty()){
    			dto = new Cuenta();
    			dto = lista.get(0);
    		}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
    }
	 
	 /**
	  * AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013
	  * Recupera Cta. Integrante por Empresa, Persona Integrante, Tipo Cta. y Situacion Cta.
	  * @param pPK
	  * @param intParaTipoCuenta
	  * @param intParaSituacionCuentaCod
	  * @return
	  * @throws BusinessException
	  */
	 public List<CuentaIntegrante> getLstPorSocioPKTipoCtaSituacionCta(SocioPK pPK,Integer intParaTipoCuenta, Integer intParaSituacionCuentaCod) throws BusinessException{
    	List<CuentaIntegrante> lista = null;
    	try{
    		lista = boCuentaIntegrante.getLstPorSocioPKTipoCtaSituacionCta(pPK,intParaTipoCuenta,intParaSituacionCuentaCod);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
    }
	 //AUTOR Y FECHA CREACION: FYALICO / 05.12.2013
	 public Cuenta grabandoCuenta(Cuenta o) throws BusinessException{
	    	Cuenta cuenta = null;
	    	try{
	    		cuenta = boCuenta.grabar(o);
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
	    	return cuenta;
	    }
	 public CuentaIntegrante grabandoCuentaIntegrante(CuentaIntegrante o) throws BusinessException{
		 CuentaIntegrante cuentaIntegrante = null;
	    	try{
	    		cuentaIntegrante = boCuentaIntegrante.grabar(o);
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
	    	return cuentaIntegrante;
	    }
	 public CuentaIntegrante getCodPersonaOfCobranza(Integer empresa,
			 										Integer cuenta) throws BusinessException{
		 CuentaIntegrante cuentaIntegrante = null;
	    	try{
	    		cuentaIntegrante = boCuentaIntegrante.getCodPersonaOfCobranza(empresa, cuenta);
			}catch(BusinessException e){
				context.setRollbackOnly();
				throw e;
			}catch(Exception e){
				context.setRollbackOnly();
				throw new BusinessException(e);
			}
	    	return cuentaIntegrante;
	    }
	 
    
}