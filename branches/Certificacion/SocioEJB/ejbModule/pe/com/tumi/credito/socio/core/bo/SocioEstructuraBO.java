package pe.com.tumi.credito.socio.core.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.dao.SocioDao;
import pe.com.tumi.credito.socio.core.dao.SocioEstructuraDao;
import pe.com.tumi.credito.socio.core.dao.impl.SocioDaoIbatis;
import pe.com.tumi.credito.socio.core.dao.impl.SocioEstructuraDaoIbatis;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.estructura.dao.EstructuraDao;
import pe.com.tumi.credito.socio.estructura.dao.impl.EstructuraDaoIbatis;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.service.EstructuraService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.ComunicacionPK;

public class SocioEstructuraBO {
	
	protected  static Logger log = Logger.getLogger(SocioEstructuraBO.class);
	private SocioEstructuraDao dao = (SocioEstructuraDao)TumiFactory.get(SocioEstructuraDaoIbatis.class);
	
	public List<SocioEstructura> getListaSocioEstructuraBusq(SocioEstructura o) throws BusinessException{
		log.info("-----------------------Debugging SocioBO.getListaSocioEstructuraBusq-----------------------------");
		Estructura domain = null;
		List<SocioEstructura> lista = null;
		try{
			log.info("o.getIntIdSucursalAdministra(): "+o.getIntIdSucursalAdministra());
			log.info("o.getIntIdSucursalUsuario(): "+o.getIntIdSucursalUsuario());
			HashMap mapa = new HashMap();
			mapa.put("pIntIdPersona", o.getId().getIntIdPersona());
			mapa.put("pIntItemSocioEstructura", o.getId().getIntItemSocioEstructura());
			mapa.put("pIntEmpresaSucUsuario", o.getIntEmpresaSucUsuario());
			mapa.put("pIntIdSucursalUsuario", o.getIntIdSucursalUsuario());
			mapa.put("pIntIdSubSucursalUsuario", o.getIntIdSubSucursalUsuario());
			mapa.put("pIntEmpresaSucAdministra", o.getIntEmpresaSucAdministra());
			mapa.put("pIntIdSucursalAdministra", o.getIntIdSucursalAdministra());
			mapa.put("pIntIdSubsucurAdministra", o.getIntIdSubsucurAdministra());
			mapa.put("pIntTipoSocio", o.getIntTipoSocio());
			mapa.put("pIntModalidad", o.getIntModalidad());
			mapa.put("pIntNivel", o.getIntNivel());
			mapa.put("pIntCodigo", o.getIntCodigo());
			mapa.put("pIntTipoEstructura", o.getIntTipoEstructura());
			mapa.put("pDtFechaRegistro", o.getDtFechaRegistro());
			mapa.put("pIntEmpresaUsuario", o.getIntEmpresaUsuario());
			mapa.put("pIntPersonaUsuario", o.getIntPersonaUsuario());
			mapa.put("pIntEstado", o.getIntEstadoCod());
			mapa.put("pDtFechaEliminacion", o.getTsFechaEliminacion());
			mapa.put("pIntEmpresaEliminar", o.getIntEmpresaEliminar());
			mapa.put("pIntPersonaEliminar", o.getIntPersonaEliminar());
			lista = dao.getListaSocioEstructuraBusq(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public SocioEstructura grabarSocioEstructura(SocioEstructura o) throws BusinessException{
		SocioEstructura dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SocioEstructura modificarSocioEstructura(SocioEstructura o) throws BusinessException{
		SocioEstructura dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SocioEstructura getSocioEstructuraPorPK(SocioEstructuraPK pPK) throws BusinessException{
		SocioEstructura domain = null;
		List<SocioEstructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntIdPersona", pPK.getIntIdPersona());
			mapa.put("pIntIdEmpresa", pPK.getIntIdEmpresa());
			mapa.put("pIntItemSocioEstructura", pPK.getIntItemSocioEstructura());
			lista = dao.getListaSocioEstructuraPorPK(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public SocioEstructura getSocioEstructuraPorPkSocioYPkEstructuraYTipoEstructura(SocioPK pkSocio,EstructuraId pkEstructura,Integer intTipoEstructura) throws BusinessException{
		SocioEstructura domain = null;
		List<SocioEstructura> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntIdPersona", pkSocio.getIntIdPersona());
			mapa.put("pIntIdEmpresa", pkSocio.getIntIdEmpresa());
			mapa.put("pIntNivel", pkEstructura.getIntNivel());
			mapa.put("pIntCodigo", pkEstructura.getIntCodigo());
			mapa.put("pIntTipoEstructura", intTipoEstructura);
			lista = dao.getListaPorPkSocioYPkEstructuraYTipoEstructura(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<SocioEstructura> getListaSocioEstructuraPorSocioPK(SocioPK pk) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntIdPersona", pk.getIntIdPersona());
			mapa.put("pIntIdEmpresa", pk.getIntIdEmpresa());
			
			System.out.println("idPersona"+pk.getIntIdPersona());
			System.out.println("idEmpresa"+pk.getIntIdEmpresa());
			
			lista = dao.getListaSocioEstructuraPorSocioPK(mapa);	
			System.out.println("listaSocioEsstr"+lista);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public SocioEstructura getSocioEstructuraDeOrigenPorPkSocio(SocioPK pkSocio) throws BusinessException{
		SocioEstructura domain = null;
		List<SocioEstructura> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntIdPersona", pkSocio.getIntIdPersona());
			mapa.put("pIntIdEmpresa", pkSocio.getIntIdEmpresa());
			mapa.put("pIntTipoEstructura", Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN);
			lista = dao.getListaSocioEstructuraPorPkSocioYTipoEstructura(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public SocioEstructura getSocioEstructuraPorPkSocioYPkEstructura(SocioPK pkSocio,EstructuraId pkEstructura) throws BusinessException{
		SocioEstructura domain = null;
		List<SocioEstructura> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntIdPersona", pkSocio.getIntIdPersona());
			mapa.put("pIntIdEmpresa", pkSocio.getIntIdEmpresa());
			mapa.put("pIntNivel", pkEstructura.getIntNivel());
			mapa.put("pIntCodigo", pkEstructura.getIntCodigo());			
			lista = dao.getListaPorPkSocioYPkEstructura(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	/**
	 * Recupera Socio Estructura por persona_n_pk, pers_empresa_n_pk y para_estado_n_cod
	 * @param intPersona
	 * @param intEmpresa
	 * @param intEstado
	 * @return
	 * @throws BusinessException
	 */
	public List<SocioEstructura> getListaSocioEstructuraPorSocioPKyActivo(Integer intPersona,
				Integer intEmpresa, Integer intEstado) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntIdPersona", intPersona);
			mapa.put("pIntIdEmpresa", intEmpresa);
			mapa.put("pintActivo", intEstado);
			lista = dao.getListaSocioEstructuraPorSocioPKyActivo(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public SocioEstructura getSocioEstructuraPorPKTipoSocio(SocioEstructuraPK pPK, Integer intTipoSocio) throws BusinessException{
		SocioEstructura domain = null;
		List<SocioEstructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntIdPersona", pPK.getIntIdPersona());
			mapa.put("pIntIdEmpresa", pPK.getIntIdEmpresa());
			mapa.put("pIntItemSocioEstructura", pPK.getIntItemSocioEstructura());
			mapa.put("pIntTipoSocio", intTipoSocio);
			lista = dao.getListaSocioEstructuraPorPKTipoSocio(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	/*public List<SocioEstructura> getListaSocEstPorSocioPKActivoTipoSocio(Integer intPersona,
			Integer intEmpresa, Integer intEstado, Integer intTipoSocio) throws BusinessException{
	List<SocioEstructura> lista = null;
	try{
		HashMap mapa = new HashMap();
		mapa.put("pIntIdPersona", intPersona);
		mapa.put("pIntIdEmpresa", intEmpresa);
		mapa.put("pintActivo", intEstado);
		mapa.put("pIntTipoSocio", intTipoSocio);
		lista = dao.getListaSocEstPorSocioPKActivoTipoSocio(mapa);	
		}catch(DAOException e){
		throw new BusinessException(e);
		}catch(Exception e) {
		throw new BusinessException(e);
		}
		return lista;
	}*/
	
	public List<SocioEstructura> getListaXSocioPKActivoTipoSocio(Integer intPersona,
			Integer intEmpresa, Integer intEstado, Integer intTipoSocio,
			Integer intNivel, Integer intCodigo) throws BusinessException{
	List<SocioEstructura> lista = null;
	try{
		HashMap mapa = new HashMap();
		mapa.put("pIntIdPersona", intPersona);
		mapa.put("pIntIdEmpresa", intEmpresa);
		mapa.put("pintActivo", intEstado);
		mapa.put("pIntTipoSocio", intTipoSocio);
		mapa.put("pIntNivel", intNivel);
		mapa.put("pIntCodigo", intCodigo);
		lista = dao.getListaXSocioPKActivoTipoSocio(mapa);	
		}catch(DAOException e){
		throw new BusinessException(e);
		}catch(Exception e) {
		throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaXNivelCodigoNoCas(SocioEstructura o) throws BusinessException{
	List<SocioEstructura> lista = null;
	try{
		HashMap mapa = new HashMap();
		mapa.put("pIntNivel", o.getIntNivel());
		mapa.put("pIntCodigo", o.getIntCodigo());		
		mapa.put("pIntIdEmpresa",o.getId().getIntIdEmpresa());
		mapa.put("pintActivo", o.getIntEstadoCod());
		mapa.put("pIntTipoSocio", o.getIntTipoSocio());
		lista = dao.getListaXNivelCodigoNoCas(mapa);	
		}catch(DAOException e){
		throw new BusinessException(e);
		}catch(Exception e) {
		throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaXNivelCodigosoloCas(SocioEstructura o) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntNivel", o.getIntNivel());
			mapa.put("pIntCodigo", o.getIntCodigo());		
			mapa.put("pIntIdEmpresa",o.getId().getIntIdEmpresa());
			mapa.put("pintActivo", o.getIntEstadoCod());
			mapa.put("pIntTipoSocio", o.getIntTipoSocio());
			mapa.put("pIntSucursalAdministra",o.getIntIdSucursalAdministra() );
			mapa.put("pIntSubSucursalAdministra",o.getIntIdSubsucurAdministra() );
			lista = dao.getListaXNivelCodigosoloCas(mapa);	
			}catch(DAOException e){
			throw new BusinessException(e);
			}catch(Exception e) {
			throw new BusinessException(e);
			}
			return lista;
		}
	
	public List<SocioEstructura> getListaSocioEstructuraAgregarSocioEfectuado(Integer intPersona,
			Integer intEmpresa, Integer intEstado, Integer intTipoSocio) throws BusinessException{
	List<SocioEstructura> lista = null;
	try{
		HashMap mapa = new HashMap();
		mapa.put("pIntIdPersona", intPersona);
		mapa.put("pIntIdEmpresa", intEmpresa);
		mapa.put("pintActivo", intEstado);
		mapa.put("pIntTipoSocio", intTipoSocio);
		lista = dao.getListaSocioEstructuraAgregarSocioEfectuado(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
	  }
	return lista;
	}
	
	
	/**
	 * franko yalico Cobranza efectuadoCon archivo	
	 */
	public List<SocioEstructura> getListaSocioEstructuraPorPkSocioYPkEstructura(SocioPK pkSocio,
																	  EstructuraId pkEstructura)
																	  throws BusinessException{
		List<SocioEstructura> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntIdPersona", pkSocio.getIntIdPersona());
			mapa.put("pIntIdEmpresa", pkSocio.getIntIdEmpresa());
			mapa.put("pIntNivel", pkEstructura.getIntNivel());
			mapa.put("pIntCodigo", pkEstructura.getIntCodigo());			
			lista = dao.getLstBySocioAndEst(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SocioEstructura> getListaXAdminySubAdminHABERINCENT(SocioEstructura o) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntNivel", o.getIntNivel());
			mapa.put("pIntCodigo", o.getIntCodigo());		
			mapa.put("pIntIdEmpresa",o.getId().getIntIdEmpresa());			
			mapa.put("pIntTipoSocio", o.getIntTipoSocio());
			lista = dao.getListaXAdminySubAdminHABERINCENT(mapa);	
			}catch(DAOException e){
			throw new BusinessException(e);
			}catch(Exception e) {
			throw new BusinessException(e);
			}
			return lista;
		}
	
	public List<SocioEstructura> getListaXNivelCodigosoloHaberIncentivo(SocioEstructura o) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntNivel", o.getIntNivel());
			mapa.put("pIntCodigo", o.getIntCodigo());		
			mapa.put("pIntIdEmpresa",o.getId().getIntIdEmpresa());
			mapa.put("pintActivo", o.getIntEstadoCod());
			mapa.put("pIntTipoSocio", o.getIntTipoSocio());
			//inicio agregadoporFranko
			mapa.put("pIntSucursalAdministra",o.getIntIdSucursalAdministra());
			mapa.put("pIntSubSucursalAdministra",o.getIntIdSubsucurAdministra());
			//fin agregadoFranko
			lista = dao.getListaXNivelCodigosoloHaberIncentivo(mapa);	
			}catch(DAOException e){
			throw new BusinessException(e);
			}catch(Exception e) {
			throw new BusinessException(e);
			}
			return lista;
		}
	
	public List<SocioEstructura> getListaPorCodPersonaOfEnviado(SocioEstructura o) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntNivel", o.getIntNivel());
			mapa.put("pIntCodigo", o.getIntCodigo());		
			mapa.put("pIntIdEmpresa",o.getId().getIntIdEmpresa());
			mapa.put("pintActivo", o.getIntEstadoCod());
			mapa.put("pIntTipoSocio", o.getIntTipoSocio());			
			mapa.put("pIntSucursalAdministra",o.getIntIdSucursalAdministra());
			mapa.put("pIntSubSucursalAdministra",o.getIntIdSubsucurAdministra());
			mapa.put("pIntPersona",o.getId().getIntIdPersona());
			lista = dao.getListaPorCodPersonaOfEnviado(mapa);	
			}catch(DAOException e){
			throw new BusinessException(e);
			}catch(Exception e) {
			throw new BusinessException(e);
			}
			return lista;
		}
	public List<SocioEstructura> getListaXAdminySubAdminSOLOCAS(SocioEstructura o) throws BusinessException{
		List<SocioEstructura> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntNivel", o.getIntNivel());
			mapa.put("pIntCodigo", o.getIntCodigo());		
			mapa.put("pIntIdEmpresa",o.getId().getIntIdEmpresa());			
			mapa.put("pIntTipoSocio", o.getIntTipoSocio());
			lista = dao.getListaXAdminySubAdminSOLOCAS(mapa);	
			}catch(DAOException e){
			throw new BusinessException(e);
			}catch(Exception e) {
			throw new BusinessException(e);
			}
			return lista;
		}
}
