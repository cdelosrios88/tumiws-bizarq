package pe.com.tumi.seguridad.empresa.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.AreaCodigo;
import pe.com.tumi.empresa.domain.AreaCodigoId;
import pe.com.tumi.empresa.domain.AreaId;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.composite.AreaComp;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.empresa.bo.AreaBO;
import pe.com.tumi.seguridad.empresa.bo.AreaCodigoBO;
import pe.com.tumi.seguridad.empresa.bo.SucursalBO;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacade;

public class AreaService {
	
	protected  static Logger log = Logger.getLogger(EmpresaFacade.class);
	
	private AreaBO boArea = (AreaBO)TumiFactory.get(AreaBO.class);
	private AreaCodigoBO boAreaCodigo = (AreaCodigoBO)TumiFactory.get(AreaCodigoBO.class);
	private AreaCodigoService areaCodigoService = (AreaCodigoService)TumiFactory.get(AreaCodigoService.class);
	private SucursalService sucursalService = (SucursalService)TumiFactory.get(SucursalService.class);
	private SucursalBO boSucursal = (SucursalBO)TumiFactory.get(SucursalBO.class);
	
	

	public List<AreaComp> getListaArea(Area o) throws BusinessException{
		log.info("-----------------------Debugging AreaService.getListaSucursal-----------------------------");
		List<AreaComp> lista = null;
		List<Area> listaArea = null;
		List<AreaCodigo> listaAreaCodigo = null;
		
		Area area = null;
		Juridica empresaJuridica = null;
		Juridica sucursalJuridica = null;
		
		try{
			listaArea = boArea.getListaArea(o);
			log.info("listaArea.size: "+listaArea.size());
			log.info("listaArea.1: "+listaArea.get(0).getId().getIntIdSucursalPk());
			lista = new ArrayList<AreaComp>();
			
			PersonaFacadeRemote facade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			for(int i=0; i<listaArea.size(); i++){
				area = listaArea.get(i);
				empresaJuridica   = facade.getJuridicaPorPK(area.getId().getIntPersEmpresaPk());
				Sucursal sucursal = boSucursal.getSucursalPorPk(area.getId().getIntIdSucursalPk());
				         sucursal = sucursalService.getSucursalPorPk(sucursal);
				sucursalJuridica  = sucursal.getJuridica();
				AreaComp areaComp = new AreaComp();
				areaComp.setArea(area);
				areaComp.setEmpresa(new Empresa());
				areaComp.getEmpresa().setJuridica(new Juridica());
				areaComp.setSucursal(new Sucursal());
				areaComp.getSucursal().setJuridica(new Juridica());
				areaComp.getEmpresa().getJuridica().setStrRazonSocial(empresaJuridica.getStrRazonSocial());
				areaComp.getSucursal().getJuridica().setStrRazonSocial(sucursalJuridica.getStrRazonSocial());
				
				if(o.getChecked() != null){
					if (o.getChecked()){
						
						listaAreaCodigo = boAreaCodigo.getListaAreaCodigo(area);
						if (listaAreaCodigo != null && listaAreaCodigo.size() > 0){
							
							lista.add(areaComp);
						}
						
					} else{	
						
						lista.add(areaComp);
						
					}
				}else{
					lista.add(areaComp);
				}
				 
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Area getAreaPorPK(Area o) throws BusinessException{
		log.info("-----------------------Debugging AreaService.getAreaPorPK-----------------------------");
		List<AreaCodigo> listaAreaCodigo = null;
		Area area = null;
		
		try{
			area = boArea.getAreaPorPK(o);
			log.info("Se obtuvo AreaPorPK...");
			listaAreaCodigo = boAreaCodigo.getListaAreaCodigo(o);
			log.info("listaAreaCodigo.size: "+listaAreaCodigo.size());
			area.setListaAreaCodigo(listaAreaCodigo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return area;
	}

	public Area grabarArea(Area o) throws BusinessException {
		log.info("-----------------------Debugging AreaService.grabarArea-----------------------------");
		Area area = null;
		try{
			area = boArea.grabarArea(o);
			log.info(""+o.getId().getIntIdArea());
			areaCodigoService.grabarDinamicoListaAreaCodigo(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return area;
	}

	public Area modificarArea(Area o) throws BusinessException {
		log.info("-----------------------Debugging AreaService.modificarArea-----------------------------");
		Area area = null;
		try{
			area = boArea.modificarArea(o);
			log.info(""+o.getId().getIntIdArea());
			areaCodigoService.grabarDinamicoListaAreaCodigo(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return area;
	}

	public Area eliminarArea(Area o) throws BusinessException {
		log.info("-----------------------Debugging AreaService.eliminarArea-----------------------------");
		Area area = null;
		try{
			o.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			area = boArea.modificarArea(o);
			log.info("area.id.intIdArea: "+o.getId().getIntIdArea());
			areaCodigoService.eliminarListaAreaCodigo(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return area;
	}
	
	public List<Area> getListaAreaPorSucursal(Sucursal sucursal) throws BusinessException{
		List<Area> listaArea = null;
		Area area = null;
		
		try{
			area = new Area();
			area.setId(new AreaId());
			area.getId().setIntPersEmpresaPk(sucursal.getId().getIntPersEmpresaPk());
			area.getId().setIntIdSucursalPk(sucursal.getId().getIntIdSucursal());
			listaArea = boArea.getListaArea(area);
			log.info("listaArea.size: "+listaArea.size());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaArea;
	}
	
}
