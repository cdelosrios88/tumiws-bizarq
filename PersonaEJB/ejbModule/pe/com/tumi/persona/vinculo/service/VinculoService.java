package pe.com.tumi.persona.vinculo.service;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.vinculo.bo.VinculoBO;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

public class VinculoService {
	
	private VinculoBO boVinculo = (VinculoBO)TumiFactory.get(VinculoBO.class);
	//private VinculoBeneficioBO boVinculoBeneficio = (VinculoBeneficioBO)TumiFactory.get(VinculoBeneficioBO.class);
	
	
	/*public List<VinculoBeneficio> grabarListaVinculoBeneficio(List<VinculoBeneficio> listVinculoBeneficio) throws BusinessException{
		try{
			for(int i=0; i<listVinculoBeneficio.size(); i++){
				VinculoBeneficio vincben = (VinculoBeneficio) listVinculoBeneficio.get(i);
				VinculoBeneficioPK pk = new VinculoBeneficioPK();
				pk.setIntIdEmpresa(vincben.getId().getIntIdEmpresa());
				pk.setIntIdPersona(vincben.getId().getIntIdPersona());
				pk.setIntTipoVinculoCod(vincben.getId().getIntTipoVinculoCod());
				pk.setIntEmpresaVinculo(vincben.getId().getIntEmpresaVinculo());
				pk.setIntPersonaVinculo(vincben.getId().getIntPersonaVinculo());
				vincben.setId(pk);
				boVinculoBeneficio.grabarVinculoBeneficio(vincben);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listVinculoBeneficio;
	}*/
	
	public Vinculo grabarDinamicoVinculo(Vinculo dto,Integer intIdPersona,Integer intIdPersonaVinculada) throws BusinessException{
		Vinculo dtoTemp = null;
		try{
				if(dto.getIntItemVinculo() == null || 
				   dto.getIntPersonaVinculo() == null){
					dto.setIntIdPersona(intIdPersona);
					dto.setIntPersonaVinculo(intIdPersonaVinculada);
					dto = boVinculo.grabarVinculo(dto);
				}else{
					dtoTemp = boVinculo.getVinculoPorPK(dto.getIntItemVinculo());
					if(dtoTemp == null){
						dto.setIntIdPersona(intIdPersona);
						dto.setIntPersonaVinculo(intIdPersonaVinculada);
						dto = boVinculo.grabarVinculo(dto);
					}else{
						dto = boVinculo.modificarVinculo(dto);
					}
				}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
}
