package pe.com.tumi.persona.empresa.facade;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.empresa.domain.Empresa;

@Remote
public interface EmpresaFacadeRemote {
	public Empresa getEmpresaPorPK(Integer pIntPK) throws BusinessException;
	/*public Juridica getJuridicaPorPK(Integer pIntPK) throws BusinessException;
	public List<Juridica> getListaJuridicaPorInPk(String pStrPK) throws BusinessException;
	public List<Juridica> getListaJuridicaPorInPkLikeRazon(String pCsvIdPersona,String pStrRazonSocial) throws BusinessException;
	public List<Juridica> getListaJuridicaDeEmpresa() throws BusinessException;*/
}
