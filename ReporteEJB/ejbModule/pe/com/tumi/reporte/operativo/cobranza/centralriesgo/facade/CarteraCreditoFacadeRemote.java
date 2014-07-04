package pe.com.tumi.reporte.operativo.cobranza.centralriesgo.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.CarteraCredito;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.Fenacrep;

@Remote
public interface CarteraCreditoFacadeRemote {
	public List<CarteraCredito> getListaCarteraCredito(Integer intIdSucursal, Integer intIdSubSucursal, Integer intParaTipoSocio, 
			  Integer intParaModalidad, Integer intParaTipoCredito, Integer intParaSubTipoCredito, 
			  Integer intParaClasificacionDeudor, Integer intPeriodo) 
					  throws BusinessException;
	
	public List<Fenacrep> getListaFenacrep(Integer intPeriodo) throws BusinessException;
}
