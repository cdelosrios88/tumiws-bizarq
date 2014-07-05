package pe.com.tumi.seguridad.permiso.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeLocal;
import pe.com.tumi.seguridad.permiso.bo.ComputadoraAccesoBO;
import pe.com.tumi.seguridad.permiso.bo.ComputadoraBO;
import pe.com.tumi.seguridad.permiso.domain.Computadora;
import pe.com.tumi.seguridad.permiso.domain.ComputadoraAcceso;
import pe.com.tumi.seguridad.permiso.domain.ComputadoraAccesoId;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesos;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalle;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalleId;

public class ComputadoraService {

	protected static Logger log = Logger.getLogger(ComputadoraService.class);	
	private ComputadoraBO boComputadora = (ComputadoraBO)TumiFactory.get(ComputadoraBO.class);	
	private ComputadoraAccesoBO boComputadoraAcceso = (ComputadoraAccesoBO)TumiFactory.get(ComputadoraAccesoBO.class);
	
	public Computadora grabarComputadorayAccesos(Computadora computadora, List <ComputadoraAcceso>listaComputadoraAcceso) 
		throws BusinessException{
		
		try {
			computadora = boComputadora.grabarComputadora(computadora);
			for(ComputadoraAcceso compAcceso : listaComputadoraAcceso){
				compAcceso.getId().setIntIdComputadora(computadora.getId().getIntIdComputadora());
				boComputadoraAcceso.grabarComputadoraAcceso(compAcceso);
			}
		} catch (BusinessException e) {
			throw e;
		}
		return computadora;
	}
	
	public List<Computadora> buscarComputadoras(Computadora computadoraFiltro) 
		throws BusinessException{
	
		EmpresaFacadeLocal empresaFacade = null;
		Area area = null;
		Sucursal sucursal = null;
		List<Computadora> lista = null;
		try {
			List<Computadora> listaAux = new ArrayList<Computadora>();			
			empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
			lista = boComputadora.getComputadoraPorBusqueda(computadoraFiltro);
			for(Computadora comp : lista){
				area = new Area();
				sucursal = new Sucursal();
				sucursal.getId().setIntPersEmpresaPk(comp.getId().getIntPersEmpresaPk());
				sucursal.getId().setIntIdSucursal(comp.getId().getIntIdSucursal());
				area.getId().setIntPersEmpresaPk(comp.getId().getIntPersEmpresaPk());
				area.getId().setIntIdSucursalPk(comp.getId().getIntIdSucursal());
				area.getId().setIntIdArea(comp.getId().getIntIdArea());
				comp.setSucursal(empresaFacade.getSucursalPorPK(sucursal));
				comp.setArea(empresaFacade.getAreaPorPK(area));
				log.info("comp:"+comp);
				log.info("sucu:"+comp.getSucursal());
				log.info("area:"+comp.getArea().getStrAbreviatura());
				log.info(" ");
				listaAux.add(comp);
			}
			lista = listaAux;
		} catch (BusinessException e) {
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Computadora eliminarComputadora(Computadora computadora) 
		throws BusinessException{

		List<ComputadoraAcceso> lista = null;
		try {
			computadora.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			computadora = boComputadora.modificarComputadora(computadora);
			lista = boComputadoraAcceso.getComputadoraAccesoPorCabecera(computadora);
			for(ComputadoraAcceso compAcceso : lista){
				compAcceso.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				compAcceso = boComputadoraAcceso.modificarComputadoraAcceso(compAcceso);
			}
		} catch (BusinessException e) {
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return computadora;
	}
	
	public Computadora modificarComputadorayAccesos(Computadora computadora, List <ComputadoraAcceso>listaComputadoraAcceso) 
		throws BusinessException{
	
		try {
			computadora = boComputadora.modificarComputadora(computadora);
			List<ComputadoraAcceso> listaBD = boComputadoraAcceso.getComputadoraAccesoPorCabecera(computadora);
			//Obtenemos un set con los accesos registrados en la bd
			Set<Integer> conjuntoAccesosExistentes = new HashSet<Integer>();
			for(ComputadoraAcceso ca : listaBD){
				conjuntoAccesosExistentes.add(ca.getIntIdTipoAcceso());
			}
			//Comparamos si lo seleccionado en la interfaz corresponde con lo q hay en la bd.
			//Si existe en la bd se modifica, sino se graba.
			for(ComputadoraAcceso ca : listaComputadoraAcceso){
				if(conjuntoAccesosExistentes.contains(ca.getIntIdTipoAcceso())){
					boComputadoraAcceso.modificarComputadoraAcceso(ca);
					conjuntoAccesosExistentes.remove(ca.getIntIdEstado());
				}else{
					ca.getId().setIntIdComputadora(computadora.getId().getIntIdComputadora());
					boComputadoraAcceso.grabarComputadoraAcceso(ca);
				}
			}
			//Los detalles que han sido deseleccionados en la interfaz pasan a estado anulado.
			for(ComputadoraAcceso ca : listaBD){
				if(conjuntoAccesosExistentes.contains(ca.getIntIdTipoAcceso())){
					ca.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					ca.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
					ca = boComputadoraAcceso.modificarComputadoraAcceso(ca);
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return computadora;
	}
	
}