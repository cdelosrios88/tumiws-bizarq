package pe.com.tumi.tesoreria.logistica.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.tesoreria.logistica.bo.CuadroComparativoBO;
import pe.com.tumi.tesoreria.logistica.bo.CuadroComparativoProveedorBO;
import pe.com.tumi.tesoreria.logistica.bo.CuadroComparativoProveedorDetalleBO;
import pe.com.tumi.tesoreria.logistica.bo.RequisicionBO;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedor;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedorDetalle;


public class CuadroComparativoService {

	protected static Logger log = Logger.getLogger(CuadroComparativoService.class);
	
	CuadroComparativoBO boCuadroComparativo = (CuadroComparativoBO)TumiFactory.get(CuadroComparativoBO.class);
	CuadroComparativoProveedorBO boCuadroComparativoProveedor = (CuadroComparativoProveedorBO)TumiFactory.get(CuadroComparativoProveedorBO.class);
	CuadroComparativoProveedorDetalleBO boCuadroComparativoProveedorDetalle = (CuadroComparativoProveedorDetalleBO)TumiFactory.get(CuadroComparativoProveedorDetalleBO.class);
	RequisicionBO boRequisicion = (RequisicionBO)TumiFactory.get(RequisicionBO.class);
	
	
	public CuadroComparativo grabarCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException{
		try{
					
			log.info(cuadroComparativo);
			
			cuadroComparativo = boCuadroComparativo.grabar(cuadroComparativo);			
			
			for(CuadroComparativoProveedor cuadroComparativoProveedor : cuadroComparativo.getListaCuadroComparativoProveedor()){
				cuadroComparativoProveedor.getId().setIntPersEmpresa(cuadroComparativo.getId().getIntPersEmpresa());
				cuadroComparativoProveedor.getId().setIntItemCuadroComparativo(cuadroComparativo.getId().getIntItemCuadroComparativo());
				
				log.info(cuadroComparativoProveedor);
				boCuadroComparativoProveedor.grabar(cuadroComparativoProveedor);
				
				for(CuadroComparativoProveedorDetalle cuadroComparativoProveedorDetalle : cuadroComparativoProveedor.getListaCuadroComparativoProveedorDetalle()){
					cuadroComparativoProveedorDetalle.getId().setIntPersEmpresa(cuadroComparativoProveedor.getId().getIntPersEmpresa());
					cuadroComparativoProveedorDetalle.getId().setIntItemCuadroComparativo(cuadroComparativoProveedor.getId().getIntItemCuadroComparativo());
					cuadroComparativoProveedorDetalle.getId().setIntItemCuadroComparativoProveedor(cuadroComparativoProveedor.getId().getIntItemCuadroComparativoProveedor());
					
					log.info(cuadroComparativoProveedorDetalle);
					boCuadroComparativoProveedorDetalle.grabar(cuadroComparativoProveedorDetalle);					
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuadroComparativo;
	}

	
	public List<CuadroComparativo> buscarCuadroComparativo(CuadroComparativo cuadroComparativoFiltro) throws BusinessException{
		List<CuadroComparativo> listaCuadroComparativo = null;
		try{
			log.info(cuadroComparativoFiltro);
			listaCuadroComparativo = boCuadroComparativo.getPorBuscar(cuadroComparativoFiltro);
			
			if(listaCuadroComparativo==null)
				return null;
			
			for(CuadroComparativo cuadroComparativo : listaCuadroComparativo){
				//log.info(cuadroComparativo);
				cuadroComparativo.setRequisicion(boRequisicion.getPorCuadroComparativo(cuadroComparativo));
				cuadroComparativo.setListaCuadroComparativoProveedor(boCuadroComparativoProveedor.getPorCuadroComparativo(cuadroComparativo));
				for(CuadroComparativoProveedor cuadroComparativoProveedor : cuadroComparativo.getListaCuadroComparativoProveedor()){
					//log.info(cuadroComparativoProveedor);
					cuadroComparativoProveedor.setListaCuadroComparativoProveedorDetalle(
							boCuadroComparativoProveedorDetalle.getPorCuadroComparativoProveedor(cuadroComparativoProveedor));
				}
				cargarProveedorAprobado(cuadroComparativo);
			}
			
			//filtro por sucursal de requisicion
			if(cuadroComparativoFiltro.getIntSucuIdSucursalFiltro() != null){
				List<CuadroComparativo> listaTemp = new ArrayList<CuadroComparativo>();
				for(CuadroComparativo cuadroComparativo : listaCuadroComparativo){
					if(cuadroComparativo.getRequisicion().getIntSucuIdSucursal().equals(cuadroComparativoFiltro.getIntSucuIdSucursalFiltro())){
						listaTemp.add(cuadroComparativo);
						break;
					}
				}
				listaCuadroComparativo = listaTemp;
			}
			
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaCuadroComparativo;
	}
	
	public void cargarProveedorAprobado(CuadroComparativo cuadroComparativo)throws Exception{
		//Modificado por cdelosrios, 14/10/2013
		//if(cuadroComparativo.getIntParaEstadoAprobacion().equals(Constante.PARAM_T_ESTADOAPROBACIONCUADRO_PENDIENTE)){
		//Fin Modificado por cdelosrios, 14/10/2013
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			for(CuadroComparativoProveedor cuadroComparativoProveedor : cuadroComparativo.getListaCuadroComparativoProveedor()){
				log.info(cuadroComparativoProveedor);
				if(cuadroComparativoProveedor.getIntParaEstadoSeleccion().equals(Constante.PARAM_T_SELECCIONPROVEEDOR_SELECCIONADO)){							
					log.info("--match");
					cuadroComparativo.setProveedorAprobado(cuadroComparativoProveedor);
					break;
				}
			}
			CuadroComparativoProveedor proveedorAprobado = cuadroComparativo.getProveedorAprobado();
			log.info(proveedorAprobado);
			//Agregado por cdelosrios, 14/10/2013
			if(proveedorAprobado!=null){
			//Fin agregado por cdelosrios, 14/10/2013
				proveedorAprobado.setPersona(personaFacade.getPersonaJuridicaPorIdPersona(proveedorAprobado.getIntPersPersonaProveedor()));
				log.info(proveedorAprobado.getPersona());
				cuadroComparativo.setProveedorAprobado(proveedorAprobado);
			//Agregado por cdelosrios, 14/10/2013
			}
			//Fin agregado por cdelosrios, 14/10/2013
		//Modificado por cdelosrios, 14/10/2013
		//}
		//Fin modificado por cdelosrios, 14/10/2013
	}
	
	public CuadroComparativo modificarCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException{
		try{
					
			log.info(cuadroComparativo);
			
			boCuadroComparativoProveedor.modificar(cuadroComparativo.getProveedorAprobado());
			
			cuadroComparativo = boCuadroComparativo.modificar(cuadroComparativo);			
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return cuadroComparativo;
	}
	
	public Persona obtenerPersonaProveedorDesdeCuadroComparativo(CuadroComparativo cuadroComparativo) throws BusinessException{
		Persona personaProveedor = null;
		try{
			PersonaFacadeRemote personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			for(CuadroComparativoProveedor cuadroComparativoProveedor : cuadroComparativo.getListaCuadroComparativoProveedor()){
				if(cuadroComparativoProveedor.getIntParaEstadoSeleccion().equals(Constante.PARAM_T_SELECCIONPROVEEDOR_SELECCIONADO)){							
					cuadroComparativo.setProveedorAprobado(cuadroComparativoProveedor);
					break;
				}
			}
			CuadroComparativoProveedor proveedorAprobado = cuadroComparativo.getProveedorAprobado();
			personaProveedor = personaFacade.getPersonaJuridicaPorIdPersona(proveedorAprobado.getIntPersPersonaProveedor());
			personaProveedor.setStrEtiqueta(personaProveedor.getIntIdPersona()+"-"+personaProveedor.getJuridica().getStrRazonSocial()
					+"-RUC:"+personaProveedor.getStrRuc());
			
			proveedorAprobado.setPersona(personaProveedor);		
			cuadroComparativo.setProveedorAprobado(proveedorAprobado);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return personaProveedor;
	}
}