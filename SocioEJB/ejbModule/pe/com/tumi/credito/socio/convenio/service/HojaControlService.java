package pe.com.tumi.credito.socio.convenio.service;

import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.CondicionComp;
import pe.com.tumi.credito.socio.captacion.domain.CondicionId;
import pe.com.tumi.credito.socio.convenio.bo.PerfilBO;
import pe.com.tumi.credito.socio.convenio.bo.PerfilDetalleBO;
import pe.com.tumi.credito.socio.convenio.bo.PerfilValidacionBO;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.Adjunto;
import pe.com.tumi.credito.socio.convenio.domain.Competencia;
import pe.com.tumi.credito.socio.convenio.domain.Perfil;
import pe.com.tumi.credito.socio.convenio.domain.PerfilDetalle;
import pe.com.tumi.credito.socio.convenio.domain.PerfilDetalleId;
import pe.com.tumi.credito.socio.convenio.domain.PerfilValidacion;
import pe.com.tumi.credito.socio.convenio.domain.Poblacion;
import pe.com.tumi.credito.socio.convenio.domain.composite.HojaControlComp;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class HojaControlService {
	private PerfilBO boPerfil = (PerfilBO)TumiFactory.get(PerfilBO.class);
	private PerfilValidacionBO boPerfilValidacion = (PerfilValidacionBO)TumiFactory.get(PerfilValidacionBO.class);
	private PerfilDetalleBO boPerfilDetalle = (PerfilDetalleBO)TumiFactory.get(PerfilDetalleBO.class);
	
	public List<HojaControlComp> getListaHojaControlComp(HojaControlComp o) throws BusinessException{
		Perfil perfil = null;
		List<PerfilValidacion> listaPerfilValidacion = null;
		List<PerfilDetalle> listaDetalle = null;
		List<HojaControlComp> listaComp = null;
		HojaControlComp hojaControlComp = null;
		try{
			//listaPerfilValidacion = new ArrayList<PerfilValidacion>();
			//listaDetalle = new ArrayList<PerfilDetalle>();
			listaComp = new ArrayList<HojaControlComp>();
			perfil = boPerfil.getPerfilPorPKAdenda(o.getPerfil());
			if(perfil!=null){
				listaDetalle = boPerfilDetalle.getListaPerfilDetallePorPKPerfil(perfil.getId().getIntItemAdendaPerfil());
				if(listaDetalle!=null && listaDetalle.size()>0){
					for(PerfilDetalle perfDet : listaDetalle){
						hojaControlComp = new HojaControlComp();
						hojaControlComp.setPerfil(new Perfil());
						hojaControlComp.setPerfilDetalle(perfDet);
						hojaControlComp.getPerfil().setStrObservacion(perfil.getStrObservacion());
						hojaControlComp.setChkValor(perfDet.getIntValor()==1?true:false);
						listaComp.add(hojaControlComp);
					}
				}else{
					hojaControlComp = new HojaControlComp();
					hojaControlComp.setPerfil(new Perfil());
					hojaControlComp.getPerfil().setStrObservacion(perfil.getStrObservacion());
					listaComp.add(hojaControlComp);
				}
			}else{
				listaPerfilValidacion = boPerfilValidacion.getListaPerfilValidacionPorEmpresaYPerfil(o.getPerfilValidacion().getId());
				if(listaPerfilValidacion!=null && listaPerfilValidacion.size()>0){
					for(PerfilValidacion perfVal : listaPerfilValidacion){
						hojaControlComp = new HojaControlComp();
						hojaControlComp.setPerfil(new Perfil());
						hojaControlComp.setPerfilValidacion(perfVal);
						listaComp.add(hojaControlComp);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaComp;
	}
	
	public Perfil grabarAdendaPerfil(Perfil pPerfil) throws BusinessException{
		Perfil perfil = null;
		//List<PerfilDetalle> listaPerfilDetalle;
		List<HojaControlComp> listaHojaControlComp = null;
		PerfilDetalle perfilDetalle = null;
		try{
			perfil = boPerfil.grabarPerfil(pPerfil);
			
			listaHojaControlComp = pPerfil.getListaHojaControlComp();
			if(listaHojaControlComp != null){
				perfilDetalle = new PerfilDetalle();
				perfilDetalle.setId(new PerfilDetalleId());
				perfilDetalle.getId().setIntItemAdendaPerfil(perfil.getId().getIntItemAdendaPerfil());
				perfilDetalle.getId().setIntPersEmpresaPk(perfil.getIntPersEmpresaPk());
				perfilDetalle.getId().setIntSeguPerfilPk(perfil.getIntSeguPerfilPk());
				grabarListaDinamicaPerfilDetalle(listaHojaControlComp, perfilDetalle.getId());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return perfil;
	}
	
	public List<HojaControlComp> grabarListaDinamicaPerfilDetalle(List<HojaControlComp> listHojaControlComp, PerfilDetalleId pPK) throws BusinessException{
		PerfilDetalle perfilDetalle = null;
		HojaControlComp hojaControlComp = null;
		PerfilDetalleId pk = null;
		PerfilDetalle perfilDetalleTemp = null;
		try{
			for(int i=0; i<listHojaControlComp.size(); i++){
				hojaControlComp = (HojaControlComp) listHojaControlComp.get(i);
				if(hojaControlComp.getPerfilDetalle()==null){
					pk = new PerfilDetalleId();
					pk.setIntPersEmpresaPk(pPK.getIntPersEmpresaPk());
					pk.setIntSeguPerfilPk(pPK.getIntSeguPerfilPk());
					pk.setIntItemAdendaPerfil(pPK.getIntItemAdendaPerfil());
					pk.setIntParaValidacionCod(listHojaControlComp.get(i).getPerfilValidacion().getId().getIntParaValidacionCod());
					hojaControlComp.setPerfilDetalle(new PerfilDetalle());
					hojaControlComp.getPerfilDetalle().setId(new PerfilDetalleId());
					hojaControlComp.getPerfilDetalle().setId(pk);
					hojaControlComp.getPerfilDetalle().setIntValor(listHojaControlComp.get(i).getChkValor()==true?1:0);
					perfilDetalle = boPerfilDetalle.grabarPerfilDetalle(hojaControlComp.getPerfilDetalle());
				}else{
					perfilDetalleTemp = boPerfilDetalle.getPerfilDetallePorPK(hojaControlComp.getPerfilDetalle().getId());
					if(perfilDetalleTemp == null){
						perfilDetalle = boPerfilDetalle.grabarPerfilDetalle(hojaControlComp.getPerfilDetalle());
					}else{
						hojaControlComp.getPerfilDetalle().setIntValor(listHojaControlComp.get(i).getChkValor()==true?1:0);
						perfilDetalle = boPerfilDetalle.modificarPerfilDetalle(hojaControlComp.getPerfilDetalle());
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listHojaControlComp;
	}
}
