package pe.com.tumi.credito.socio.captacion.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.credito.socio.captacion.bo.CaptacionBO;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.credito.socio.captacion.domain.VinculoId;

@Local
public interface VinculoFacadeLocal {
	List<Vinculo> listarVinculo() throws BusinessException;
	List<Vinculo> listarVinculo(VinculoId o) throws BusinessException;		
	Vinculo grabarVinculo(Vinculo o) throws BusinessException;
	Vinculo modificarVinculo(Vinculo o) throws BusinessException;
	List<Vinculo> listarVinculoPorPKCaptacion(CaptacionId o) throws BusinessException;
	//Vinculo eliminarVinculo(Vinculo o) throws BusinessException;
}
