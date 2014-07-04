package pe.com.tumi.credito.socio.core.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.aperturaCuenta.core.bo.CuentaIntegranteBO;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.core.bo.SocioBO;
import pe.com.tumi.credito.socio.core.bo.SocioEstructuraBO;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.service.SocioService;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;

@Stateless
public class SocioFacade extends TumiFacade implements SocioFacadeRemote, SocioFacadeLocal {

	protected  static Logger log = Logger.getLogger(SocioFacade.class);
	private SocioService socioService = (SocioService)TumiFactory.get(SocioService.class);
	private SocioBO boSocio = (SocioBO)TumiFactory.get(SocioBO.class);
	private SocioEstructuraBO boSocioEstructura = (SocioEstructuraBO)TumiFactory.get(SocioEstructuraBO.class);
	private CuentaIntegranteBO boCuentaIntegrante = (CuentaIntegranteBO)TumiFactory.get(CuentaIntegranteBO.class);
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SocioComp> getListaSocioComp(SocioComp o) throws BusinessException {
		log.info("-----------------------Debugging SocioFacade.getListaSocioComp-----------------------------");
		List<SocioComp> lista = null;
		try{
			lista = socioService.getListaSocioComp(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public SocioComp grabarSocioNatural(SocioComp o, List<PersonaRol> listaRol) throws BusinessException {
		log.info("-----------------------Debugging SocioFacade.grabarSocioNatural-----------------------------");
		SocioComp socioComp = null;
		//List<PersonaRol> listaPersonaRol = new ArrayList<PersonaRol>();
		try{
			socioComp = socioService.grabarSocioNatural(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return socioComp;
	}
	
	public SocioComp modificarSocioNatural(SocioComp o, List<PersonaRol> listaRol) throws BusinessException {
		log.info("-----------------------Debugging SocioFacade.modificarSocioNatural-----------------------------");
		SocioComp socioComp = null;
		List<PersonaRol> listaPersonaRol = null;
		
		try{
			socioComp = socioService.modificarSocioNatural(o);
			if(listaRol!=null)log.info("listaRol.size: "+listaRol.size());
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			listaPersonaRol = personaFacade.grabarListaDinamicaPersonaRol(listaRol, socioComp.getSocio().getId().getIntIdPersona());
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return socioComp;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SocioComp getSocioNatural(SocioPK o) throws BusinessException {
		SocioComp socioComp = null;
		try{
			socioComp = socioService.getSocioNatural(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socioComp;
	}

	@Override
	public SocioComp getSocioNatuPorDocIdentidadYIdEmpresaYTipoVinculo(Integer intTipoDoc,String strNumIdentidad,Integer pIntEmpresa,Integer pIntTipoVinculo) throws BusinessException {
		SocioComp socio = null;
		try{
			socio  = socioService.getSocioNatuPorDocIdentidadYIdEmpresaYTipoVinculo(intTipoDoc,strNumIdentidad,pIntEmpresa,pIntTipoVinculo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socio;
	}
	
	/**
	 * Recupera Socio Comp [ Persona (Doc, listaDoc), Socio (lisSociEstructura), Cuenta (listCuentaCOncepto)]
	 */
	public SocioComp getSocioNatuPorDocIdentidadYIdEmpresa(Integer intTipoDoc,String strNumIdentidad,Integer pIntEmpresa) throws BusinessException {
		SocioComp socio = null;
		try{
			socio  = socioService.getSocioNatuPorDocIdentidadYIdEmpresa(intTipoDoc,strNumIdentidad,pIntEmpresa);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socio;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SocioEstructura getSocioEstructuraPorPk(SocioEstructuraPK pk) throws BusinessException{
		SocioEstructura socioEstructura = null;
		try {
			socioEstructura = boSocioEstructura.getSocioEstructuraPorPK(pk);
		} catch(BusinessException e){
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return socioEstructura;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SocioEstructura getSocioEstructuraPorPkSocioYPkEstructuraYTipoEstructura(SocioPK pkSocio,EstructuraId pkEstructura,Integer intTipoEstructura) throws BusinessException{
		SocioEstructura socioEstructura = null;
		try {
			socioEstructura = boSocioEstructura.getSocioEstructuraPorPkSocioYPkEstructuraYTipoEstructura(pkSocio,pkEstructura,intTipoEstructura);
		} catch(BusinessException e){
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return socioEstructura;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SocioEstructura getSocioEstructuraDeOrigenPorPkSocio(SocioPK pkSocio) throws BusinessException{
		SocioEstructura socioEstructura = null;
		try {
			socioEstructura = boSocioEstructura.getSocioEstructuraDeOrigenPorPkSocio(pkSocio);
		} catch(BusinessException e){
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return socioEstructura;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Socio> getListaSocioPorIdEstructuraYTipoSocio(EstructuraId pk,Integer intTipoSocio) throws BusinessException {
		List<Socio> lista = null;
		try{
			lista = boSocio.getListaSocioPorIdEstructuraYTipoSocio(pk,intTipoSocio);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Socio> getListaSocioDeTitularPorIdEstructuraYTipoSocio(EstructuraId pk,Integer intTipoSocio) throws BusinessException {
		List<Socio> lista = null;
		try{
			lista = boSocio.getListaSocioDeTitularPorIdEstructuraYTipoSocio(pk, intTipoSocio);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Socio> getListaSocioPorEmpresaYTipoIntegranteYINCuenta(Integer intEmpresa,Integer intTipoIntegrante,String strINCuenta) throws BusinessException{
		List<Socio> lista = null;
		try{
			lista = socioService.getListaSocioPorEmpresaYTipoIntegranteYINCuenta(intEmpresa,intTipoIntegrante,strINCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SocioEstructura> getListaSocioEstrucuraPorIdPersona(Integer intIdPersona,Integer intIdEmpresa) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{
			SocioPK socioPk = new SocioPK();
			socioPk.setIntIdPersona(intIdPersona);
			socioPk.setIntIdEmpresa(intIdEmpresa);
			lista = boSocioEstructura.getListaSocioEstructuraPorSocioPK(socioPk);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SocioComp> getListaBuscarSocioConCuentaVigente(Integer intEmpresa,Integer intTipoRol,String nombre,String dni) throws BusinessException{
		List<SocioComp> lista = null;
		try{
			
			lista = socioService.getListaBuscarSocioConCuentaVigente(intEmpresa, intTipoRol, nombre, dni);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public SocioEstructura modificarSocioEstructura(SocioEstructura o) throws BusinessException {
		log.info("-----------------------Debugging modificarSocioEstructura-----------------------------");
		SocioEstructura socioEstructura = null;
	
		try{
			socioEstructura = boSocioEstructura.modificarSocioEstructura(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return socioEstructura;
	}
	
	public SocioEstructura grabarSocioEstructura(SocioEstructura o) throws BusinessException {
		log.info("-----------------------Debugging grabarSocioEstructura-----------------------------");
		SocioEstructura socioEstructura = null;
	
		try{
			socioEstructura = boSocioEstructura.grabarSocioEstructura(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return socioEstructura;
	}
	
	
	/**
	 * List<CuentaIntegrante> getListaCuentaIntegrantePorCuenta(Integer intEmpresa,Integer intCuenta)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaIntegrante> getListaCuentaIntegrantePorCuenta(Integer intEmpresa,Integer intCuenta) throws BusinessException{
		List<CuentaIntegrante> lista = null;
		CuentaId cuentaId = new CuentaId();
		try{
			cuentaId.setIntCuenta(intCuenta);
			cuentaId.setIntPersEmpresaPk(intEmpresa);
			lista = boCuentaIntegrante.getListaCuentaIntegrantePorCuenta(cuentaId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SocioEstructura getSocioEstructuraPorPkSocioYPkEstructura(SocioPK pkSocio,EstructuraId pkEstructura) throws BusinessException{
		SocioEstructura socioEstructura = null;
		try {
			socioEstructura = boSocioEstructura.getSocioEstructuraPorPkSocioYPkEstructura(pkSocio,pkEstructura);
		} catch(BusinessException e){
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return socioEstructura;
	}

	public Socio getSocioPorPK(SocioPK pk) throws BusinessException {
		Socio socio = null;
		try{
			socio = boSocio.getSocioPorPK(pk);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socio;
	}
	
	
	
	/**
	 * Recupera el sociocomp y la cuenta solicitada
	 * @param intTipoDoc
	 * @param strNumIdentidad
	 * @param pIntEmpresa
	 * @param cuentaExpediente
	 * @return
	 * @throws BusinessException
	 */
	public SocioComp getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(Integer intTipoDoc,String strNumIdentidad,Integer pIntEmpresa, Cuenta cuentaExpediente) throws BusinessException {
		SocioComp socio = null;
		try{
			socio  = socioService.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(intTipoDoc,strNumIdentidad,pIntEmpresa, cuentaExpediente);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socio;
	}
	

	
	/**
	 * Recupera Socio Estructura por persona_n_pk, pers_empresa_n_pk y para_estado_n_cod
	 */
	public List<SocioEstructura> getListaSocioEstructuraPorSocioPKyActivo(Integer intPersona,Integer intEmpresa, Integer intEstado) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{

			lista = boSocioEstructura.getListaSocioEstructuraPorSocioPKyActivo(intPersona,intEmpresa, intEstado);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SocioEstructura getSocioEstructuraPorPKTipoSocio(SocioEstructuraPK pk, Integer intTipoSocio) throws BusinessException{
		SocioEstructura socioEstructura = null;
		try {
			socioEstructura = boSocioEstructura.getSocioEstructuraPorPKTipoSocio(pk, intTipoSocio);
		} catch(BusinessException e){
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return socioEstructura;
	}
	
	/*@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SocioEstructura> getListaSocEstPorSocioPKActivoTipoSocio(Integer intPersona,
			Integer intEmpresa, Integer intEstado,Integer intTipoSocio) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{
			
			lista = boSocioEstructura.getListaSocEstPorSocioPKActivoTipoSocio(intPersona,intEmpresa, intEstado, intTipoSocio);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}*/
	
	public List<SocioEstructura> getListaXSocioPKActivoTipoSocio(Integer intPersona,
			Integer intEmpresa, Integer intEstado,Integer intTipoSocio,
			Integer intNivel, Integer intCodigo) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{
			
			lista = boSocioEstructura.getListaXSocioPKActivoTipoSocio
					(intPersona,intEmpresa, intEstado, intTipoSocio, intNivel, intCodigo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	
	
	
	/**
	 * Recupera Socio Comp [ Persona (Doc, listaDoc), Socio (lisSociEstructura), Cuenta (listCuentaCOncepto)]
	 * @param intTipoDoc
	 * @param strNumIdentidad
	 * @param pIntEmpresa
	 * @param intCuenta
	 * @return
	 * @throws BusinessException
	 */
	public SocioComp getSocioCompXDocEmpCuenta(Integer intTipoDoc,String strNumIdentidad,Integer pIntEmpresa, Integer intCuenta) throws BusinessException {
		SocioComp socio = null;
		try{
			socio  = socioService.getSocioCompXDocEmpCuenta(intTipoDoc,strNumIdentidad,pIntEmpresa,intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socio;
	}
	public List<SocioEstructura> getListaXNivelCodigoNoCas(SocioEstructura o) throws BusinessException
	{
		List<SocioEstructura> lista = null;
		try{
			lista = boSocioEstructura.getListaXNivelCodigoNoCas(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public List<SocioEstructura> getListaXNivelCodigosoloCas(SocioEstructura o) throws BusinessException
	{
		List<SocioEstructura> lista = null;
		try{
			lista = boSocioEstructura.getListaXNivelCodigosoloCas(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * Cobranza envioPlanilla listadodeSocios
	 * Lista por TipoSocio Modalidad solo haberes e incentivos, estado activos, empresa 
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Socio> getLPorIdEstructuraTSMAE(SocioEstructura o) throws BusinessException {
		List<Socio> lista = null;
		try{
			lista = boSocio.getLPorIdEstructuraTSMAE(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera los socios segun filtrors de busqueda
	 */
	public List<SocioComp> getListaSocioPorFiltrosBusq (SocioComp socioCompBusq)throws BusinessException{
		List<SocioComp> lstSocioComp = null;
		try {
			lstSocioComp= socioService.getListaSocioPorFiltrosBusq(socioCompBusq);
		} catch (Exception e) {
			log.error(""+e);
		}
		return lstSocioComp;

	}
	
	/**
	 * 
	 * @param socioCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public SocioComp completarSocioCompBusqueda (Socio socioBusq)throws BusinessException{
		SocioComp socioComp = null;
		try {
			socioComp= socioService.completarSocioCompBusqueda(socioBusq);
		} catch (Exception e) {
			log.error(""+e);
		}
		return socioComp;

	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Socio> getListaSocioEnEfectuado(Socio o) throws BusinessException {
		List<Socio> lista = null;
		try{
			lista = boSocio.getListaSocioEnEfectuado(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaSocioEstructuraAgregarSocioEfectuado
			(Integer intPersona,Integer intEmpresa, Integer intEstado, Integer intTipoSocio) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{

			lista = boSocioEstructura.getListaSocioEstructuraAgregarSocioEfectuado(intPersona,intEmpresa, intEstado, intTipoSocio);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Socio modificarSocio(Socio o) throws BusinessException {
		Socio socio = null;
		try{
			socio = boSocio.modificarSocio(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socio;
	}
	
	public Socio grabarSocio(Socio o) throws BusinessException {
		Socio socio = null;
		try{
			socio = boSocio.grabarSocio(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socio;
	}
	
	public Socio getSocioPorNombres(String nombres) throws BusinessException {
		Socio socio = null;
		try{
			socio = boSocio.getSocioPorNombres(nombres);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return socio;
	}
	

	public List<SocioEstructura> getListaSocioEstructuraPorPkSocioYPkEstructura(SocioPK pkSocio,
																				EstructuraId pkEstructura)
																				throws BusinessException{
		List<SocioEstructura> lista = null;
		try {
			lista = boSocioEstructura.getListaSocioEstructuraPorPkSocioYPkEstructura(pkSocio,pkEstructura);
		} catch(BusinessException e){
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaXAdminySubAdminHABERINCENT(SocioEstructura o) throws BusinessException
	{
		List<SocioEstructura> lista = null;
		try{
			lista= boSocioEstructura.getListaXAdminySubAdminHABERINCENT(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaXNivelCodigosoloHaberIncentivo(SocioEstructura o) throws BusinessException
	{
		List<SocioEstructura> lista = null;
		try{
			lista = boSocioEstructura.getListaXNivelCodigosoloHaberIncentivo(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaPorCodPersonaOfEnviado(SocioEstructura o) throws BusinessException
	{
		List<SocioEstructura> lista = null;
		try{
			lista = boSocioEstructura.getListaPorCodPersonaOfEnviado(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaXAdminySubAdminSOLOCAS(SocioEstructura o) throws BusinessException
	{
		List<SocioEstructura> lista = null;
		try{
			lista= boSocioEstructura.getListaXAdminySubAdminSOLOCAS(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
