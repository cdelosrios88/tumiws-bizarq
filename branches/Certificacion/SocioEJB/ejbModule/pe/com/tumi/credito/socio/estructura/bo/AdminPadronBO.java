package pe.com.tumi.credito.socio.estructura.bo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.estructura.dao.AdminPadronDao;
import pe.com.tumi.credito.socio.estructura.dao.impl.AdminPadronDaoIbatis;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadronId;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.PadronId;
import pe.com.tumi.credito.socio.estructura.service.AdminPadronService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class AdminPadronBO {
	
	private AdminPadronDao dao = (AdminPadronDao)TumiFactory.get(AdminPadronDaoIbatis.class);
	protected  	static Logger log = Logger.getLogger(AdminPadronBO.class);
	
	public AdminPadron grabarAdminPadron(AdminPadron o) throws BusinessException{
		AdminPadron dto = null;
		log.info("a grabar:"+o);
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AdminPadron modificarAdminPadron(AdminPadron o) throws BusinessException{
		AdminPadron dto = null;
		try{
			log.info("-- antes:"+o);
			dto = dao.modificar(o);
			log.info("-- luego:"+dto);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AdminPadron getAdminPadronPorPK(AdminPadronId pPK) throws BusinessException{
		AdminPadron domain = null;
		List<AdminPadron> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPeriodo", pPK.getIntPeriodo());
			mapa.put("intMes", pPK.getIntMes());
			mapa.put("intNivel", pPK.getIntNivel());
			mapa.put("intCodigo", pPK.getIntCodigo());
			mapa.put("intParaTipoArchivoPadronCod", pPK.getIntParaTipoArchivoPadronCod());
			mapa.put("intParaTipoModalidadCod", pPK.getIntParaModalidadCod());
			mapa.put("intParaTipoSocioCod", pPK.getIntParaTipoSocioCod());
			mapa.put("intItemAdministraPadron", pPK.getIntItemAdministraPadron());
			lista = dao.getListaAdminPadronPorPK(mapa);
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
	
	public List<AdminPadron> getAdminPadronBusqueda(AdminPadron adminPadron) throws BusinessException{
		List<AdminPadron> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPeriodo", adminPadron.getId().getIntPeriodo());
			mapa.put("intMes", adminPadron.getId().getIntMes());
			mapa.put("intNivel", adminPadron.getId().getIntNivel());			
			mapa.put("intCodigo", adminPadron.getId().getIntCodigo());
			mapa.put("intParaTipoArchivoPadronCod", adminPadron.getId().getIntParaTipoArchivoPadronCod());
			mapa.put("intParaTipoModalidadCod", adminPadron.getId().getIntParaModalidadCod());
			mapa.put("intParaTipoSocioCod", adminPadron.getId().getIntParaTipoSocioCod());
			mapa.put("intParaEstadoCod", adminPadron.getIntParaEstadoCod());
			
			lista = dao.getListaBusqueda(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<AdminPadron> getListaAdminPadron() throws BusinessException{
		List<AdminPadron> lista = null;
		try{
			HashMap mapa = new HashMap();
			lista = dao.getLista(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * 
	 */
	public List<AdminPadron> getTipSocioModPeriodoMes(AdminPadron adminPadron) throws BusinessException{
		List<AdminPadron> lista = null;
		try{
			HashMap mapa = new HashMap();			
			mapa.put("intParaTipoModalidadCod", adminPadron.getId().getIntParaModalidadCod());
			mapa.put("intParaTipoSocioCod", adminPadron.getId().getIntParaTipoSocioCod());
			mapa.put("intParaEstadoCod", adminPadron.getIntParaEstadoCod());
			
			lista = dao.getTipSocioModPeriodoMes(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera ultimo registro de AdminPadron
	 * @param adminPadron
	 * @return
	 * @throws BusinessException
	 */
	public AdminPadron getMaximoAdminPadronPorAdminPadron(AdminPadron adminPadron) throws BusinessException{
		List<AdminPadron> lista = null;
		AdminPadron domain = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPeriodo", adminPadron.getId().getIntPeriodo());
			mapa.put("intMes", adminPadron.getId().getIntMes());
			mapa.put("intNivel", adminPadron.getId().getIntNivel());			
			mapa.put("intCodigo", adminPadron.getId().getIntCodigo());
			mapa.put("intParaTipoArchivoPadronCod", adminPadron.getId().getIntParaTipoArchivoPadronCod());
			mapa.put("intParaTipoModalidadCod", adminPadron.getId().getIntParaModalidadCod());
			mapa.put("intParaTipoSocioCod", adminPadron.getId().getIntParaTipoSocioCod());
			mapa.put("intParaEstadoCod", adminPadron.getIntParaEstadoCod());
			
			lista = dao.getListaMaximoPorAdminPadron(mapa);	
			if(lista != null && !lista.isEmpty()){
				domain = new AdminPadron();
				domain = lista.get(0);
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
}
