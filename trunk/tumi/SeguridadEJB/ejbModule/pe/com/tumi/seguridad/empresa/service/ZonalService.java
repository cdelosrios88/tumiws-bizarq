package pe.com.tumi.seguridad.empresa.service;

import org.apache.log4j.Logger;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.bo.ZonalSucursalBO;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacade;

public class ZonalService {
	
	protected  static Logger log = Logger.getLogger(EmpresaFacade.class);
	
	private ZonalSucursalBO boZonalSucursal = (ZonalSucursalBO)TumiFactory.get(ZonalSucursalBO.class);

	public Zonal getZonalSucursalPorIdZonal(Integer pId) throws BusinessException{
		Zonal zonal = null;
		Sucursal sucursal = null;
		try{
			zonal = boZonalSucursal.getZonalSucursalPorIdZonal(pId);
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			if(zonal!=null){
				zonal.setJuridica(facade.getJuridicaPorPK(zonal.getIntPersPersonaPk()));
				sucursal = zonal.getSucursal(); 
				sucursal.setJuridica(facade.getJuridicaPorPK(sucursal.getIntPersPersonaPk()));
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return zonal;
	}
	
}
