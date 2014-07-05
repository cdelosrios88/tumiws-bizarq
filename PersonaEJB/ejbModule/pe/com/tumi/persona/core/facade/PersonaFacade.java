package pe.com.tumi.persona.core.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.bo.DocumentoBO;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.bo.CuentaBancariaBO;
import pe.com.tumi.persona.core.bo.CuentaBancariaFinBO;
import pe.com.tumi.persona.core.bo.NaturalBO;
import pe.com.tumi.persona.core.bo.PersonaBO;
import pe.com.tumi.persona.core.bo.PersonaEmpresaBO;
import pe.com.tumi.persona.core.bo.PersonaRolBO;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaFin;
import pe.com.tumi.persona.core.domain.CuentaBancariaFinId;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.service.PersonaDetalleService;
import pe.com.tumi.persona.core.service.PersonaFiltroService;
import pe.com.tumi.persona.core.service.PersonaJuridicaService;
import pe.com.tumi.persona.core.service.PersonaNaturalService;
import pe.com.tumi.persona.core.service.PersonaRolService;
import pe.com.tumi.persona.core.service.PersonaVinculadaService;
import pe.com.tumi.persona.empresa.bo.EmpresaBO;
import pe.com.tumi.persona.empresa.bo.EmpresaJuridicaBO;
import pe.com.tumi.persona.empresa.bo.JuridicaBO;
import pe.com.tumi.persona.empresa.bo.PerLaboralBO;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.empresa.service.EmpresaService;
import pe.com.tumi.persona.vinculo.bo.VinculoBO;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

@Stateless
public class PersonaFacade extends TumiFacade implements PersonaFacadeRemote, PersonaFacadeLocal {
	
	protected  static Logger log = Logger.getLogger(PersonaFacade.class);
	private PersonaBO boPersona = (PersonaBO)TumiFactory.get(PersonaBO.class);
	private NaturalBO boNatural = (NaturalBO)TumiFactory.get(NaturalBO.class);
	private JuridicaBO boJuridica = (JuridicaBO)TumiFactory.get(JuridicaBO.class);
	private PerLaboralBO boPerLaboral = (PerLaboralBO)TumiFactory.get(PerLaboralBO.class);
	private PersonaNaturalService personaNaturalService = (PersonaNaturalService)TumiFactory.get(PersonaNaturalService.class);
	private PersonaJuridicaService personaJuridicaService = (PersonaJuridicaService)TumiFactory.get(PersonaJuridicaService.class);
	private PersonaVinculadaService vinculadaService = (PersonaVinculadaService)TumiFactory.get(PersonaVinculadaService.class);
	private EmpresaJuridicaBO boEmpresaJuridica = (EmpresaJuridicaBO)TumiFactory.get(EmpresaJuridicaBO.class);
	private CuentaBancariaBO boCuentaBancaria = (CuentaBancariaBO)TumiFactory.get(CuentaBancariaBO.class);
	private EmpresaBO boEmpresa = (EmpresaBO)TumiFactory.get(EmpresaBO.class);
	private EmpresaService empresaService = (EmpresaService)TumiFactory.get(EmpresaService.class);
	private PersonaRolBO boPersonaRol = (PersonaRolBO)TumiFactory.get(PersonaRolBO.class);
	private PersonaVinculadaService personaVinculaService = (PersonaVinculadaService)TumiFactory.get(PersonaVinculadaService.class);
	private PersonaRolService personaRolService = (PersonaRolService)TumiFactory.get(PersonaRolService.class);
	private VinculoBO boVinculo = (VinculoBO)TumiFactory.get(VinculoBO.class);
	private PersonaEmpresaBO boPersonaEmpresa = (PersonaEmpresaBO)TumiFactory.get(PersonaEmpresaBO.class);
	private DocumentoBO boDocumento = (DocumentoBO)TumiFactory.get(DocumentoBO.class);
	private CuentaBancariaFinBO boCuentaBancariaFin = (CuentaBancariaFinBO)TumiFactory.get(CuentaBancariaFinBO.class);
	private PersonaFiltroService personaFiltroService = (PersonaFiltroService)TumiFactory.get(PersonaFiltroService.class);
	private PersonaDetalleService personaDetalleService = (PersonaDetalleService)TumiFactory.get(PersonaDetalleService.class);

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona modificarPersona(Persona persona) throws BusinessException{
		Persona dto = null;
		try{
			dto = boPersona.modificarPersona(persona);
		}catch(BusinessException e){
			System.out.println("BusinessException BusinessException --> "+e);
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	/**
	 * Recupera Persona y ademas una lista de todos sus Docuemntos
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getPersonaPorPK(Integer pIntPK) throws BusinessException{
		Persona dto = null;
		List<Documento> lstDocumentos = null;
		try{
			dto = boPersona.getPersonaPorPK(pIntPK);
			if(dto != null){
				lstDocumentos = boDocumento.getListaDocumentoPorIdPersona(pIntPK);
				if(lstDocumentos != null && !lstDocumentos.isEmpty()){
					dto.setListaDocumento(lstDocumentos);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	/**
	 * Recupera Natural
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Natural getNaturalPorPK(Integer pIntPK) throws BusinessException{
		Natural dto = null;
		try{
			dto = boNatural.getNaturalPorPK(pIntPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getPersonaNaturalPorDocIdentidadYIdEmpresa(Integer intTipoIdentidad ,String strNroIdentidad, Integer intIdEmpresaSistema) throws BusinessException{
		Persona dto = null;
		try{
			dto = personaNaturalService.getPersonaNaturalPorDocIdentidadYIdEmpresa(intTipoIdentidad, strNroIdentidad, intIdEmpresaSistema);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getPersonaNaturalPorDocIdentidadYIdEmpresaYTipoVinculo(Integer intTipoIdentidad ,String strNroIdentidad, Integer intIdEmpresaSistema, Integer intTipoVinculo) throws BusinessException{
		log.info("-------------------------------------Debugging PersonaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresaYTipoVinculo-------------------------------------");
		
		Persona dto = null;
		try{
			dto = personaNaturalService.getPersonaNaturalPorDocIdentidadYIdEmpresa(intTipoIdentidad, strNroIdentidad, intIdEmpresaSistema);
			
			if(dto!=null){
				//Obtener las personas relacionadas
				PersonaEmpresaPK personaEmpresaPK = new PersonaEmpresaPK();
				personaEmpresaPK.setIntIdEmpresa(intIdEmpresaSistema);
				personaEmpresaPK.setIntIdPersona(dto.getIntIdPersona());
				dto.setListaPersona(personaVinculaService.getListaPersonaJuridicaPorPKPersonaEmpresaYTipoVinculo(personaEmpresaPK, intTipoVinculo));
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getPersonaNaturalPorIdPersona(Integer pIntIdPersona) throws BusinessException{
		Persona dto = null;
		try{
			dto = personaNaturalService.getPerNaturalYPerLaboralPorIdPersona(pIntIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getPersonaNaturalPorPersonaEmpresaPKYTipoVinculo(PersonaEmpresaPK pk, Integer pIntTipoVinculo) throws BusinessException{
		Persona dto = null;
		try{
			dto = personaNaturalService.getPersonaNaturalTotalPorIdPersonaYIdEmpresa(pk.getIntIdPersona(),pk.getIntIdEmpresa());
			//Obtener los roles
			List<PersonaRol> listaPersonaRol = personaRolService.getListaPersonaRolPorPKPersonaEmpresa(pk);
			PersonaEmpresa personaEmpresa = new PersonaEmpresa();
			personaEmpresa.setId(pk);
			personaEmpresa.setListaPersonaRol(listaPersonaRol);
			dto.setPersonaEmpresa(personaEmpresa);
			//Obtener las personas relacionadas
			dto.setListaPersona(personaVinculaService.getListaPersonaJuridicaPorPKPersonaEmpresaYTipoVinculo(pk, pIntTipoVinculo));
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona grabarPersonaNaturalTotal(Persona persona) throws BusinessException{
		log.info("-------------------------------------Debugging grabarPersonaNaturalTotal-------------------------------------");
		Persona dto = null;
		try{
			dto = personaNaturalService.grabarPersonaNaturalYPerLaboral(persona);
			if(persona.getListaPersona()!=null){
				vinculadaService.grabarListaDinamicaPersonaVinculada(persona.getListaPersona(), dto.getIntIdPersona());
			}
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona grabarPersonaNatural(Persona o)throws BusinessException{
		Persona dto = null;
		try{
			dto = personaNaturalService.grabarPersonaNatural(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona modificarPersonaNaturalTotal(Persona persona) throws BusinessException{
		log.info("-------------------------------------Debugging modificarPersonaNaturalTotal-------------------------------------");
		Persona dto = null;
		try{
			dto = personaNaturalService.modificarPersonaNaturalYPerLaboral(persona);
			log.info("dto.intIdPersona(): "+dto.getIntIdPersona());
			if(persona.getListaPersona()!=null){
				log.info("persona.listaPersona.size: "+persona.getListaPersona().size());
				vinculadaService.grabarListaDinamicaPersonaVinculada(persona.getListaPersona(), dto.getIntIdPersona());
			}
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona modificarPersonaNatural(Persona o)throws BusinessException{
		Persona dto = null;
		try{
			dto = personaNaturalService.modificarPersonaNatural(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

	/*public void eliminarPersonaNaturalPorIdPersona(Integer pIntIdPersona)throws BusinessException{
		try{
			personaNaturalService.eliminarPersonaNaturalPorIdPersona(pIntIdPersona);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
	}*/
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Natural> getListaNaturalPorInPk(String pStrPK) throws BusinessException{
		List<Natural> lista = null;
		try{
			lista = boNatural.getListaNaturalPorInPk(pStrPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Persona> getFullListPerNaturalBusqueda(Persona o) throws BusinessException{
		List<Persona> lista = null;
		try{
			lista = personaNaturalService.getFullListPerNaturalBusqueda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Persona> getListPerNaturalBusqueda(Persona o) throws BusinessException{
		List<Persona> lista = null;
		try{
			lista = personaNaturalService.getListPerNaturalBusqueda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Empresa grabarEmpresa(Empresa o) throws BusinessException {
		log.info("-----------------------Debugging PersonaFacade.grabarEmpresa-----------------------------");
		Empresa emp = null;
		try{
			emp = empresaService.grabarEmpresa(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return emp;
	}
	
	
	public Empresa eliminarEmpresa(Empresa o) throws BusinessException {
		Empresa emp = null;
		try{
			o.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			emp = boEmpresa.modificarEmpresa(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return emp;
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Empresa getEmpresaPorPk(Integer intPk) throws BusinessException{
		Empresa dto = null;
		try{
			dto = boEmpresa.getEmpresaPorPK(intPk);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Empresa> getListaEmpresa(Empresa o) throws BusinessException{
		log.info("-----------------------Debugging PersonaFacade.getListaEmpresa-----------------------------");
		List<Empresa> lista = null;
		try{
			lista = boEmpresaJuridica.getListaEmpresa(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getPersonaJuridicaPorIdPersonaYIdEmpresa(Integer pIntIdPersona, Integer intIdEmpresaSistema) throws BusinessException{
		Persona dto = null;
		try{
			System.out.println("INICIO getPersonaJuridicaPorIdPersonaYIdEmpresa");
			dto = personaJuridicaService.getPersonaJuridicaPorIdPersonaYIdEmpresa(pIntIdPersona, intIdEmpresaSistema);
			System.out.println("FIN    getPersonaJuridicaPorIdPersonaYIdEmpresa");
		}catch(BusinessException e){
			System.out.println("EXCEPCION getPersonaJuridicaPorIdPersonaYIdEmpresa");
			System.out.println(e.getMessage());
			throw e;
		}catch(Exception e){
			System.out.println("EXCEPCION getPersonaJuridicaPorIdPersonaYIdEmpresa");
			System.out.println(e.getMessage());
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getPersonaJuridicaPorIdPersona(Integer pIntIdPersona) throws BusinessException{
		Persona dto = null;
		try{
			dto = personaJuridicaService.getPersonaJuridicaPorIdPersona(pIntIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona grabarPersonaJuridica(Persona persona) throws BusinessException{
		Persona dto = null;
		try{
			dto = personaJuridicaService.grabarPersonaJuridica(persona);
			if(persona.getListaPersona()!=null){
				vinculadaService.grabarListaDinamicaPersonaVinculada(persona.getListaPersona(), dto.getIntIdPersona());
			}
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona modificarPersonaJuridica(Persona persona) throws BusinessException{
		Persona dto = null;
		try{
			dto = personaJuridicaService.modificarPersonaJuridica(persona);
			if(persona.getListaPersona()!=null){
				vinculadaService.grabarListaDinamicaPersonaVinculada(persona.getListaPersona(), dto.getIntIdPersona());
			}
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona grabarPersonaJuridicaTotal(Persona persona) throws BusinessException{
		log.info("-------------------------------------Debugging grabarPersonaJuridicaTotal-------------------------------------");
		Persona dto = null;
		try{
			dto = personaJuridicaService.grabarPersonaJuridicaTotal(persona);
			if(persona.getListaPersona()!=null){
				vinculadaService.grabarListaDinamicaPersonaVinculada(persona.getListaPersona(), dto.getIntIdPersona());
			}
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona modificarPersonaJuridicaTotal(Persona persona) throws BusinessException{
		log.info("-------------------------------------Debugging PersonaFacade.modificarPerJuridica-------------------------------------");
		Persona dto = null;
		try{
			log.info("persona.juridica.strFichaRegPublico: "+persona.getJuridica().getStrFichaRegPublico());
			boPersona.modificarPersona(persona);
			if(persona.getJuridica()!=null && persona.getJuridica().getIntIdPersona()==null){
				log.info(persona.getJuridica());
				persona.getJuridica().setIntIdPersona(persona.getIntIdPersona());
				boJuridica.grabarJuridica(persona.getJuridica());
			}
			dto = personaJuridicaService.modificarPersonaJuridicaTotal(persona);
			if(persona.getListaPersona()!=null){
				vinculadaService.grabarListaDinamicaPersonaVinculada(persona.getListaPersona(), dto.getIntIdPersona());
			}
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	/*public void eliminarPersonaJuridicaPorIdPersona(Integer pIntIdPersona)throws BusinessException{
		try{
			personaJuridicaService.eliminarPersonaJuridicaPorIdPersona(pIntIdPersona);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
	}*/

	public Juridica modificarJuridica(Juridica juridica) throws BusinessException{
		Juridica o = null;
		try{
			o = boJuridica.modificarJuridica(juridica);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return o;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Empresa getEmpresaJuridicaPorPK(Integer pIntPK) throws BusinessException{
		Empresa empresa = null;
		try{
			empresa = boEmpresaJuridica.getEmpresaJuridicaPorPK(pIntPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return empresa;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Juridica getJuridicaPorPK(Integer pIntPK) throws BusinessException{
		Juridica dto = null;
		try{
			dto = boJuridica.getJuridicaPorPK(pIntPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<Persona> getListPerJuridicaBusqueda(Persona o) throws BusinessException{
		List<Persona> lista = null;
		try{
			lista = personaJuridicaService.getListPerJuridicaBusqueda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Persona> getBusquedaPerJuridicaSinSucursales(Persona o) throws BusinessException{
		List<Persona> lista = null;
		try{
			lista = personaJuridicaService.getBusquedaPerJuridicaSinSucursales(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Persona getPersonaJuridicaPorRuc(String strRuc) throws BusinessException{
		Persona dto = null;
		try{
			dto = personaJuridicaService.getPersonaJuridicaPorRuc(strRuc);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona getPersonaJuridicaYListaPersonaPorRucYIdEmpresa(String strRuc,Integer intIdEmpresaSistema) throws BusinessException{
		Persona dto = null;
		try{
			dto = personaJuridicaService.getPersonaJuridicaPorRucYIdEmpresa(strRuc, intIdEmpresaSistema);
			PersonaEmpresaPK pk = new PersonaEmpresaPK();
			pk.setIntIdEmpresa(intIdEmpresaSistema);
			pk.setIntIdPersona(dto.getIntIdPersona());
			dto.setListaPersona(vinculadaService.getListaPersonaNaturalPorPersonaEmpresaPK(pk));
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Persona getPersonaJuridicaYListaPersonaPorRucYIdEmpresa2(String strRuc,Integer intIdEmpresaSistema) throws BusinessException{
		Persona dto = null;
		try{
			dto = personaJuridicaService.getPersonaJuridicaPorRucYIdEmpresa2(strRuc, intIdEmpresaSistema);
			if(dto!=null){
				PersonaEmpresaPK pk = new PersonaEmpresaPK();
				pk.setIntIdEmpresa(intIdEmpresaSistema);
				pk.setIntIdPersona(dto.getIntIdPersona());
				dto.setListaPersona(vinculadaService.getListaPersonaNaturalPorPersonaEmpresaPK(pk));
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<Persona> getListaPersonaNaturalPorPKPersonaEmpresaYTipoVinculo(Integer intIdPersona,Integer intTipoVinculo,Integer intIdEmpresaSistema) throws BusinessException{
		List<Persona> lista = null;
		try{
			PersonaEmpresaPK pk = new PersonaEmpresaPK();
			pk.setIntIdEmpresa(intIdEmpresaSistema);
			pk.setIntIdPersona(intIdPersona);
			lista = vinculadaService.getListaPersonaNaturalPorPKPersonaEmpresaYTipoVinculo(pk,intTipoVinculo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Juridica> getListaJuridicaPorInPk(String pStrPK) throws BusinessException{
		List<Juridica> lista = null;
		try{
			lista = boJuridica.getListaJuridicaPorInPk(pStrPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Juridica> getListaJuridicaPorInPkLikeRazon(String pCsvIdPersona,String pStrRazonSocial) throws BusinessException{
		List<Juridica> lista = null;
		try{
			lista = boJuridica.getListaJuridicaPorInPkLikeRazon(pCsvIdPersona, pStrRazonSocial);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Juridica> getListaJuridicaDeEmpresa() throws BusinessException{
		List<Juridica> lista = null;
		try{
			lista = boJuridica.getListaJuridicaDeEmpresa();
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CuentaBancaria getCuentaBancariaPorPK(CuentaBancariaPK id) throws BusinessException{
		CuentaBancaria dto = null;
		try{
			dto = boCuentaBancaria.getCuentaBancariaPorPK(id);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PersonaRol grabarPersonaRol(PersonaRol personaRol) throws BusinessException{
		PersonaRol dto = null;
		try{
			dto = boPersonaRol.grabarPersonaRol(personaRol);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PersonaRol modificarPersonaRolPorPerEmpYRol(PersonaRol personaRol) throws BusinessException{
		PersonaRol dto = null;
		try{
			dto = boPersonaRol.modificarPersonaRolPorPerEmpYRol(personaRol);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaRol> grabarListaDinamicaPersonaRol(List<PersonaRol> lista, Integer intIdPersona) throws BusinessException{
		List<PersonaRol> list = null;
		try{
			list = personaRolService.grabarListaDinamicaPersonaRol(lista, intIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return list;
	}
	
	public Vinculo getVinculoPorPk(Integer intItemVinculo)throws BusinessException{
		Vinculo dto = null;
		try{
			dto = personaVinculaService.getVinculoPorPk(intItemVinculo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Vinculo getVinculoPersona(Vinculo o)throws BusinessException{
		Vinculo dto = null;
		try{
			dto = boVinculo.getVinculoPorPKPersEmpresaYPkPersona(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Vinculo grabarVinculoPersona(Vinculo o) throws BusinessException{
		Vinculo dto = null;
		try{
			dto = boVinculo.grabarVinculo(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Vinculo modificarVinculoPersona(Vinculo o) throws BusinessException{
		Vinculo dto = null;
		try{
			dto = boVinculo.modificarVinculo(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaBancaria grabarCuentaBancaria(CuentaBancaria o) throws BusinessException{
		
		CuentaBancaria dto = null;
		try{
			
			dto = boCuentaBancaria.grabarCuentaBancaria(o);

		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	
	
	public CuentaBancaria modificarCuentaBancaria(CuentaBancaria o) throws BusinessException{
		CuentaBancaria dto = null;
		try{
			dto = boCuentaBancaria.modificarCuentaBancaria(o);
		}catch(BusinessException e){
			context.setRollbackOnly();			
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Juridica> getListaJuridicaPorRazonSocial(String strRazonSocial) throws BusinessException{
		List<Juridica> lista = null;
		try{
			lista = boJuridica.getListaPorRazonSocial(strRazonSocial);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Juridica> getListaJuridicaPorNombreComercial(String strNombreComercial) throws BusinessException{
		List<Juridica> lista = null;
		try{
			lista = boJuridica.getListaPorNombreComercial(strNombreComercial);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getPersonaPorRuc(String strRuc) throws BusinessException{
		Persona dto = null;
		try{
			dto = boPersona.getPersonaPorRuc(strRuc);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getPersona(String strRuc) throws BusinessException{
		Persona dto = null;
		try{
			dto = boPersona.getPersonaPorRuc(strRuc);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Natural> getListaNaturalPorBusqueda(Natural o) throws BusinessException{
		List<Natural> lista = null;
		try{
			lista = boNatural.getListaNaturalBusqueda(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaEmpresa> getListaPersonaEmpresaPorIdPersona(Integer pId) throws BusinessException{
		List<PersonaEmpresa> lista = null;
		try{
			lista = boPersonaEmpresa.getListaPersonaEmpresaPorIdPersona(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Natural grabarNatural(Natural o) throws BusinessException{
		Natural dto = null;
		try{
			dto = boNatural.grabarNatural(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Documento grabarDocumento(Documento o) throws BusinessException{
		Documento dto = null;
		try{
			dto = boDocumento.grabarDocumento(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Natural modificarNatural(Natural o) throws BusinessException{
		Natural dto = null;
		try{
			dto = boNatural.modificarNatural(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PersonaRol> getListaPersonaRolPorPKPersonaEmpresa(PersonaEmpresaPK pk) throws BusinessException{
		List<PersonaRol> lista = null;
		try{
			log.info("--getListaPersonaRolPorPKPersonaEmpresa");
			lista = personaRolService.getListaPersonaRolPorPKPersonaEmpresa(pk);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CuentaBancariaFin grabarCuentaBancariaFin(CuentaBancariaFin o) throws BusinessException{
		CuentaBancariaFin dto = null;
		try{
			dto = boCuentaBancariaFin.grabar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaBancariaFin> getListaCuentaBancariaFinPorCuentaBancaria(CuentaBancaria o) throws BusinessException{
		List<CuentaBancariaFin> lista = null;
		try{
			lista = boCuentaBancariaFin.getListaPorCuentaBancaria(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public void eliminarCuentaBancariaFin(CuentaBancariaFinId o) throws BusinessException{
		try{
			boCuentaBancariaFin.eliminar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaBancaria> getListaCuentaBancariaPorStrNumero(String strNroCuentaBancaria) throws BusinessException{
		List<CuentaBancaria> lista = null;
		try{
			log.info("strNroCuentaBancaria:"+strNroCuentaBancaria);
			System.out.println("strNroCuentaBancaria:"+strNroCuentaBancaria);
			lista = boCuentaBancaria.getListaPorStrNroCuentaBancaria(strNroCuentaBancaria);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Vinculo getVinculoPorId(Integer intIdVinculo) throws BusinessException{
		Vinculo vinculo = null;
		try{
			vinculo = boVinculo.getVinculoPorPK(intIdVinculo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return vinculo;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Persona> getListaBuscarPersonaNatural(Integer pIntIdEmpresa,String pStrNombres,Integer pIntTipoIdentidadCod, String pStrNumeroIdentidad,Integer pIntParaRolPk) throws BusinessException{
		List<Persona> lista = null;
		try{
			lista = personaNaturalService.getListaBuscarPersonaNatural(pIntIdEmpresa, pStrNombres, pIntTipoIdentidadCod, pStrNumeroIdentidad, pIntParaRolPk);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Vinculo> getVinculoPorPk(PersonaEmpresaPK pPK)
			throws BusinessException {
		List<Vinculo> lista = null;
		try{
			lista = boVinculo.getVinculoPorPK(pPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona devolverPersonaCargada(Integer intIdPersona) throws BusinessException{
		Persona persona = null;
		try{
			persona = getPersonaPorPK(intIdPersona);
			if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				persona = getPersonaNaturalPorIdPersona(persona.getIntIdPersona());			
				agregarDocumentoDNI(persona);
				agregarNombreCompleto(persona);			
			
			}else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				persona.setJuridica(getJuridicaPorPK(persona.getIntIdPersona()));			
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	public void agregarNombreCompleto(Persona persona)throws BusinessException{
		try{
			persona.getNatural().setStrNombreCompleto(
			persona.getNatural().getStrNombres()+" "+
			persona.getNatural().getStrApellidoPaterno()+" "+
			persona.getNatural().getStrApellidoMaterno());
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public void agregarDocumentoDNI(Persona persona)throws BusinessException{
		try{
			for(Documento documento : persona.getListaDocumento()){
				if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				persona.setDocumento(documento);
				}
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getPersonaActiva(Integer intIdPersona) throws BusinessException{
		Persona dto = null;
		try{
			dto = boPersona.getPersonaActivaPorIdPersona(intIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Persona> buscarListaPersonaParaFiltro(Integer intTipoBusqueda, String strFiltro) throws BusinessException{
		List<Persona> listaPersona = new ArrayList<Persona>();
		try{
			listaPersona = personaFiltroService.buscarListaPersonaParaFiltro(intTipoBusqueda, strFiltro);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaPersona;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getPersonaDetalladaPorIdPersonaYEmpresa(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		Persona persona = null;
		try{
			persona = boPersona.getPersonaPorPK(intIdPersona);
			personaDetalleService.getDetalleDePersonaPorIdPersona(persona, persona.getIntIdPersona());
			
			//Obteniendo la lista de PersonaRol
			PersonaEmpresaPK personaEmpresaPk = new PersonaEmpresaPK();
			personaEmpresaPk.setIntIdEmpresa(intIdEmpresa);
			personaEmpresaPk.setIntIdPersona(persona.getIntIdPersona());
			List<PersonaRol> listaPersonaRol = personaRolService.getListaPersonaRolPorPKPersonaEmpresa(personaEmpresaPk);			
			
			PersonaEmpresa personaEmpresa = new PersonaEmpresa();
			personaEmpresa.setId(personaEmpresaPk);
			personaEmpresa.setListaPersonaRol(listaPersonaRol);
			
			persona.setPersonaEmpresa(personaEmpresa);
			persona.setStrRoles(MyUtil.obtenerStrRoles(persona));
			persona.setListaPersona(vinculadaService.getListaPersonaNaturalPorPersonaEmpresaPK(personaEmpresaPk));
			
			if(persona.getListaPersona()!=null)
			for(Persona personaVinculada : persona.getListaPersona())
				MyUtil.agregarNombreCompleto(personaVinculada);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return persona;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Juridica getJuridicaDetalladaPorIdPersona(Integer intIdPersona) throws BusinessException{
		Juridica juridica = null;
		try{
			juridica = personaJuridicaService.getJuridicaDetalladaPorIdPersona(intIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return juridica;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Natural getNaturalDetalladaPorIdPersona(Integer intIdPersona) throws BusinessException{
		Natural natural = null;
		try{
			natural = personaNaturalService.getNaturalDetalladaPorIdPersona(intIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return natural;
	}	
	
	
	
	/**
	 * Recupera SOLO la persona natural. EN base al id de persona.
	 * @param intIdPersona
	 * @return
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Natural getSoloPersonaNaturalPorIdPersona(Integer intIdPersona) throws BusinessException{
		Natural natural = null;
		try{
			natural = personaNaturalService.getSoloPersonaNaturalPorIdPersona(intIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return natural;
	}
	
	/**
	 * Recupera SOLO la persona Juridica . EN base al id de persona.
	 * @param intIdPersona
	 * @return
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Juridica getSoloPersonaJuridicaPorPersona(Integer intIdPersona) throws BusinessException{
		Juridica juridica = null;
		try{
			juridica = personaJuridicaService.getSoloPersonaJuridicaPorPersona(intIdPersona);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return juridica;
	}	

	/**
	 * Recupera SOLO la persona en base al id 
	 * @param pIntPK
	 * @return
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Persona getSoloPersonaPorPK(Integer pIntPK) throws BusinessException{
		Persona dto = null;
		try{
			dto = boPersona.getPersonaPorPK(pIntPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}

	@Override
	public List<PerLaboral> getListaPerLaboralPorIdPersona(Integer pIntPK) throws BusinessException{
		List<PerLaboral> lista = null;
		try{
			lista = boPerLaboral.getListaPerLaboralPorIdPersona(pIntPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera Persona y ademas una lista de todos sus Docuemntos
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Documento> getLstDocumentoPorPKPersona(Integer pIntPK) throws BusinessException{
		Persona dto = null;
		List<Documento> lstDocumentos = null;
		try{
				lstDocumentos = boDocumento.getListaDocumentoPorIdPersona(pIntPK);

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstDocumentos;
	}
	
	//FYC


	public List<Juridica> listaJuridicaWithFile(String strProg,
												String strCodEst) throws BusinessException
	{
		List<Juridica> listaJuridica = null;
		try
		{
			listaJuridica = boJuridica.listaJuridicaWithFile(strProg,
															 strCodEst);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaJuridica;
	}
	
	//FLYC
	public List<PersonaRol> listaPersonaRolOfCobranza(PersonaEmpresaPK pPK) throws BusinessException
	{
		List<PersonaRol> listaPersonaRol = null;
		try
		{
			listaPersonaRol = boPersonaRol.getListaPersonaRolPorPKPersonaEmpresa(pPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaPersonaRol;
	}
	
}