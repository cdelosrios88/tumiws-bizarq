package pe.com.tumi.seguridad.empresa.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.AreaCodigo;
import pe.com.tumi.empresa.domain.AreaCodigoId;
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
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacade;

public class AreaCodigoService {
	
	protected  static Logger log = Logger.getLogger(EmpresaFacade.class);
	private AreaCodigoBO boAreaCodigo = (AreaCodigoBO)TumiFactory.get(AreaCodigoBO.class);
	
	public List<AreaCodigo> grabarDinamicoListaAreaCodigo(Area o) throws BusinessException {
		List<AreaCodigo> listaAreaCodigo = null;
		AreaCodigo areaCodigo = null;
		AreaCodigo areaCodigoTemp = null;
		
		try{
			listaAreaCodigo = o.getListaAreaCodigo();
			for(int i=0; i<listaAreaCodigo.size(); i++){
				areaCodigo = listaAreaCodigo.get(i);
				log.info("areaCodigo.strDescripcion: "+areaCodigo.getStrDescripcion());
				log.info("areaCodigo.intIdEstado: "+areaCodigo.getIntIdEstado());
				log.info("areaCodigo.strCodigo: "+areaCodigo.getStrCodigo());
				log.info("areaCodigo.id: "+areaCodigo.getId());
				if(areaCodigo.getId()==null){
					areaCodigo.setId(new AreaCodigoId());
					areaCodigo.getId().setIntIdEmpresaPk(o.getId().getIntPersEmpresaPk());
					areaCodigo.getId().setIntIdSucursalPk(o.getId().getIntIdSucursalPk());
					areaCodigo.getId().setIntIdAreaPk(o.getId().getIntIdArea());
					boAreaCodigo.grabarAreaCodigo(areaCodigo);
				}else{
					areaCodigoTemp = boAreaCodigo.getAreaCodigoPorPK(areaCodigo.getId());
					if(areaCodigoTemp == null){
						areaCodigo = boAreaCodigo.grabarAreaCodigo(areaCodigo);
					}else{
						areaCodigo = boAreaCodigo.modificarAreaCodigo(areaCodigo);
					}
				}
				log.info("areaCodigo.id.intIdTipoCodigoPk: "+areaCodigo.getId().getIntIdTipoCodigoPk());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaAreaCodigo;
	}
	
	public List<AreaCodigo> eliminarListaAreaCodigo(Area o) throws BusinessException {
		List<AreaCodigo> listaAreaCodigo = null;
		AreaCodigo areaCodigo = null;
		AreaCodigo areaCodigoTemp = null;
		
		try{
			listaAreaCodigo = o.getListaAreaCodigo();
			for(int i=0; i<listaAreaCodigo.size(); i++){
				areaCodigo = listaAreaCodigo.get(i);
				log.info("areaCodigo.strDescripcion: "+areaCodigo.getStrDescripcion());
				log.info("areaCodigo.intIdEstado: "+areaCodigo.getIntIdEstado());
				log.info("areaCodigo.strCodigo: "+areaCodigo.getStrCodigo());
				log.info("areaCodigo.id: "+areaCodigo.getId());
				
				areaCodigoTemp = boAreaCodigo.getAreaCodigoPorPK(areaCodigo.getId());
				if(areaCodigoTemp != null){
					areaCodigo.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					areaCodigo = boAreaCodigo.modificarAreaCodigo(areaCodigo);
				}
				log.info("areaCodigo.id.intIdTipoCodigoPk: "+areaCodigo.getId().getIntIdTipoCodigoPk());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaAreaCodigo;
	}
}
