package pe.com.tumi.credito.socio.core.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

//import com.ibm.security.util.calendar.BaseCalendar.Date;

import pe.com.tumi.credito.socio.core.dao.SocioDao;
import pe.com.tumi.credito.socio.core.dao.impl.SocioDaoIbatis;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class SocioBO {
	
	protected  static Logger log = Logger.getLogger(SocioBO.class);
	private SocioDao dao = (SocioDao)TumiFactory.get(SocioDaoIbatis.class);
	
	public List<Socio> getListaSocioBusqueda(Socio o) throws BusinessException{
		log.info("-----------------------Debugging SocioBO.getListaSocioBusqueda-----------------------------");
		Estructura domain = null;
		List<Socio> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntIdPersona", o.getId().getIntIdPersona());
			mapa.put("pIntIdEmpresa", o.getId().getIntIdEmpresa());
			mapa.put("pStrApePatSoc", o.getStrApePatSoc());
			mapa.put("pStrApeMatSoc", o.getStrApeMatSoc());
			mapa.put("pStrNombreSoc", o.getStrNombreSoc());
			lista = dao.getListaSocioBusqueda(o);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	//RVILLARREAL
	public Integer getCantidadHijos(int idPersona) {
		return dao.getCantidadHijos(idPersona);
	}
	
	public Date getFechaIngreso(int idPersona){
		return dao.getFechaIngreso(idPersona);
	}
	//RVILLARREAL
	public String getUbigeoPorId(Integer idUbigeo) throws BusinessException {
		try{
			return dao.getUbigeoPorId(idUbigeo);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
	}	
	//
	public Socio grabarSocio(Socio o) throws BusinessException{
		Socio dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Socio modificarSocio(Socio o) throws BusinessException{
		Socio dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Socio getSocioPorPK(SocioPK pk) throws BusinessException{
		Socio domain = null;
		List<Socio> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("pIntIdPersona", pk.getIntIdPersona());
			mapa.put("pIntIdEmpresa", pk.getIntIdEmpresa());					
			lista = dao.getListaSocioPorPK(mapa);
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
	
	public List<Socio> getListaSocioPorIdEstructuraYTipoSocio(EstructuraId pk,Integer intTipoSocio) throws BusinessException{
		List<Socio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntNivel",  pk.getIntNivel());
			mapa.put("pIntCodigo", pk.getIntCodigo());
			mapa.put("pTipoSocio", intTipoSocio);
			lista = dao.getListaSocioPorIdEstructuraYTipoSocio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<Socio> getListaSocioDeTitularPorIdEstructuraYTipoSocio(EstructuraId pk,Integer intTipoSocio) throws BusinessException{
		List<Socio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntNivel",  pk.getIntNivel());
			mapa.put("pIntCodigo", pk.getIntCodigo());
			mapa.put("pTipoSocio", intTipoSocio);
			lista = dao.getListaDeTitularCuentaPorPkEstructuraYTipoSocio(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Socio> getListaSocioPorEmpresaYINPersona(Integer intEmpresa,String strCsvPersona) throws BusinessException{
		List<Socio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdEmpresa",  intEmpresa);
			mapa.put("strCsvPersona", strCsvPersona);
			lista = dao.getListaPorEmpresaYINPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * Cobranza envioPlanilla listadodeSocios
	 * Lista por TipoSocio Modalidad solo haberes e incentivos, estado activos, empresa 
	 */
	
	public List<Socio> getLPorIdEstructuraTSMAE(SocioEstructura o) throws BusinessException{
		List<Socio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("pIntEmpresa",o.getId().getIntIdEmpresa());
			mapa.put("pIntNivel",o.getIntNivel());
			mapa.put("pIntCodigo",o.getIntCodigo());
			mapa.put("pTipoSocio",o.getIntTipoSocio());
			lista = dao.getLPorIdEstructuraTSMAE(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Socio> getListaSocioEnEfectuado(Socio o) throws BusinessException{
		log.info("-----------------------Debugging SocioBO.getListaSocioEnEfectuado-----------------------------");
		Estructura domain = null;
		List<Socio> lista = null;
		try{
			HashMap mapa = new HashMap();		
			mapa.put("pIntIdEmpresa", o.getId().getIntIdEmpresa());
			mapa.put("pStrApePatSoc", o.getStrApePatSoc());			
			lista = dao.getListaSocioEnEfectuado(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<Socio> getListaSocioPorFiltrosBusq(SocioComp o) throws BusinessException{
		List<Socio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intBusquedaTipo",o.getIntBusquedaTipo());
			mapa.put("strBusqCadena",o.getStrBusqCadena());
			mapa.put("dtBusqFechaRegDesde",o.getDtBusqFechaRegDesde());
			mapa.put("dtBusqFechaRegHasta",o.getDtBusqFechaRegHasta());
			mapa.put("intBusqSituacionCta",o.getIntBusqSituacionCta());
			lista = dao.getListaSocioPorFiltrosBusq(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	
	}
	public Socio getSocioPorNombres(String nombres) throws BusinessException
	{
		Socio domain = null;
		List<Socio> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("strNombres", nombres);								
			lista = dao.getListSocioPorNombres(mapa);
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
}
