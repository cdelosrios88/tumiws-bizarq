package pe.com.tumi.credito.socio.convenio.service;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.estructura.bo.ConvenioDetalleBO;
import pe.com.tumi.credito.socio.estructura.bo.EstructuraBO;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class ConvenioDetalleService {
	
	private ConvenioDetalleBO boConvenioDetalle = (ConvenioDetalleBO)TumiFactory.get(ConvenioDetalleBO.class);
	private EstructuraBO boEstructura = (EstructuraBO)TumiFactory.get(EstructuraBO.class);
	
	public List<ConvenioEstructuraDetalleComp> getListaConvenioEstructuraDetalleCompPorPkConvenio(AdendaId o) throws BusinessException{
		List<ConvenioEstructuraDetalleComp> lista = null;
		Juridica juridica = null;
		List<Tabla> listaModalidad = null;
		List<Tabla> listaTipoSocio = null;
		Tabla tabla = null;
		TablaFacadeRemote tablaFacade = null;
		try{
			lista = boConvenioDetalle.getListaConvenioEstructuraDetallePorPKConvenio(o);
			PersonaFacadeRemote facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			listaModalidad = new ArrayList<Tabla>();
			listaTipoSocio = new ArrayList<Tabla>();
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			if(lista!=null && lista.size()>0){
				for(ConvenioEstructuraDetalleComp dto : lista){
					dto.getEstructura().setJuridica(new Juridica());
					juridica = facadePersona.getJuridicaPorPK(dto.getEstructura().getIntPersPersonaPk());
					dto.getEstructura().setJuridica(juridica);
					listaModalidad = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
					String strIntIdModalidad = null;
					String strModalidad = null;
					for(int i=0;i<listaModalidad.size();i++){
						tabla = new Tabla();
						if(listaModalidad.get(i).getIntIdDetalle().equals(dto.getEstructuraDetalle().getIntParaModalidadCod())){
							strIntIdModalidad = ""+dto.getEstructuraDetalle().getIntParaModalidadCod();
							strModalidad = listaModalidad.get(i).getStrDescripcion();
						}
					}
					listaTipoSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
					String strIntIdTipoSocio = null;
					String strTipoSocio = null;
					for(int i=0;i<listaTipoSocio.size();i++){
						tabla = new Tabla();
						if(listaTipoSocio.get(i).getIntIdDetalle().equals(dto.getEstructuraDetalle().getIntParaTipoSocioCod())){
							strIntIdTipoSocio = "" + dto.getEstructuraDetalle().getIntParaTipoSocioCod();
							strTipoSocio = listaTipoSocio.get(i).getStrDescripcion();
						}
					}
					dto.setStrCsvPkModalidadTipoSocio(strIntIdModalidad + "," + strIntIdTipoSocio);
					dto.setStrModalidadTipoSocio(strModalidad + " - " + strTipoSocio + " - " + dto.getEstructura().getJuridica().getStrRazonSocial());
					
					/*
					if(dto.getEstructura().getId().getIntNivel().equals(Constante.PARAM_T_NIVELENTIDAD_DEPENDENCIA)){
						estructura = new Estructura();
						estructura.setId(new EstructuraId());
						estructura.getId().setIntCodigo(dto.getEstructura().getIntIdCodigoRel());
						estructura.getId().setIntNivel(dto.getEstructura().getIntNivelRel());
						estructura = boEstructura.getEstructuraPorPK(estructura.getId());
						juridica = facadePersona.getJuridicaPorPK(estructura.getIntPersPersonaPk());
						dto.setStrDependencia(juridica.getStrRazonSocial());
					}
					if(dto.getEstructura().getId().getIntNivel().equals(Constante.PARAM_T_NIVELENTIDAD_UNIDADEJECUTORA)){
						estructura = new Estructura();
						estructura.setId(new EstructuraId());
						estructura.getId().setIntCodigo(dto.getEstructura().getIntIdCodigoRel());
						estructura.getId().setIntNivel(dto.getEstructura().getIntNivelRel());
						estructura = boEstructura.getEstructuraPorPK(estructura.getId());
						juridica = facadePersona.getJuridicaPorPK(estructura.getIntPersPersonaPk());
						dto.setStrUnidadEjecutora(juridica.getStrRazonSocial());
					}*/
					
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
