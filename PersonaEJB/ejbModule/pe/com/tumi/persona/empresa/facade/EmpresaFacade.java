package pe.com.tumi.persona.empresa.facade;

import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.empresa.bo.EmpresaBO;
import pe.com.tumi.persona.empresa.bo.JuridicaBO;
import pe.com.tumi.persona.empresa.domain.Empresa;

/**
 * Session Bean implementation class EmpresaFacade
 */
@Stateless
public class EmpresaFacade extends TumiFacade implements EmpresaFacadeRemote, EmpresaFacadeLocal {

	EmpresaBO boEmpresa = (EmpresaBO)TumiFactory.get(EmpresaBO.class);
	JuridicaBO  boJuridica = (JuridicaBO)TumiFactory.get(JuridicaBO.class);
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Empresa getEmpresaPorPK(Integer pIntPK) throws BusinessException{
		Empresa dto = null;
		try{
			dto = boEmpresa.getEmpresaPorPK(pIntPK);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	/*
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
	}*/
}
