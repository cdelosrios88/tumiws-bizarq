package pe.com.tumi.tesoreria.banco.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.banco.bo.AccesoBO;
import pe.com.tumi.tesoreria.banco.bo.AccesoDetalleBO;
import pe.com.tumi.tesoreria.banco.bo.AccesoDetalleResBO;
import pe.com.tumi.tesoreria.banco.bo.BancocuentaBO;
import pe.com.tumi.tesoreria.banco.bo.BancofondoBO;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalle;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalleRes;
import pe.com.tumi.tesoreria.banco.domain.BancocuentaId;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.BancofondoId;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;


public class AccesoService {

	protected static Logger log = Logger.getLogger(AccesoService.class);
	
	AccesoBO boAcceso = (AccesoBO)TumiFactory.get(AccesoBO.class);
	AccesoDetalleBO boAccesoDetalle = (AccesoDetalleBO)TumiFactory.get(AccesoDetalleBO.class);
	AccesoDetalleResBO boAccesoDetalleRes = (AccesoDetalleResBO)TumiFactory.get(AccesoDetalleResBO.class);
	BancocuentaBO boBancoCuenta = (BancocuentaBO)TumiFactory.get(BancocuentaBO.class);
	BancofondoBO boBancoFondo = (BancofondoBO)TumiFactory.get(BancofondoBO.class);
	
	public Acceso grabarAcceso(Acceso acceso) throws BusinessException{
		try{
			log.info(acceso);
			acceso = boAcceso.grabar(acceso);
			for(AccesoDetalle accesoDetalle : acceso.getListaAccesoDetalle()){
				grabarAccesoDetalle(accesoDetalle, acceso);
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return acceso;
	}
	
	private AccesoDetalleRes grabarAccesoDetalleRes(AccesoDetalleRes accesoDetalleRes, AccesoDetalle accesoDetalle) throws BusinessException{
		accesoDetalleRes.getId().setIntPersEmpresaAcceso(accesoDetalle.getId().getIntPersEmpresaAcceso());
		accesoDetalleRes.getId().setIntItemAcceso(accesoDetalle.getId().getIntItemAcceso());
		accesoDetalleRes.getId().setIntItemAccesoDetalle(accesoDetalle.getId().getIntItemAccesoDetalle());
		
		log.info(accesoDetalleRes);
		boAccesoDetalleRes.grabar(accesoDetalleRes);
		
		return accesoDetalleRes;
	}
	
	private AccesoDetalle grabarAccesoDetalle(AccesoDetalle accesoDetalle, Acceso acceso) throws BusinessException{
		
		accesoDetalle.getId().setIntPersEmpresaAcceso(acceso.getId().getIntPersEmpresaAcceso());
		accesoDetalle.getId().setIntItemAcceso(acceso.getId().getIntItemAcceso());
		
		log.info(accesoDetalle);
		boAccesoDetalle.grabar(accesoDetalle);
		
		for(AccesoDetalleRes accesoDetalleRes : accesoDetalle.getListaAccesoDetalleRes()){
			grabarAccesoDetalleRes(accesoDetalleRes, accesoDetalle);
		}
		return accesoDetalle;
	}
	
	private AccesoDetalle modificarAccesoDetalle(AccesoDetalle accesoDetalle, Acceso acceso) throws BusinessException{
		List<AccesoDetalleRes> listaAccesoDetalleRes = accesoDetalle.getListaAccesoDetalleRes();
		List<AccesoDetalleRes> listaAccesoDetalleResIU = new ArrayList<AccesoDetalleRes>();
		List<AccesoDetalleRes> listaAccesoDetalleResBD = boAccesoDetalleRes.getPorAccesoDetalle(accesoDetalle);
		
		log.info(accesoDetalle);
		boAccesoDetalle.modificar(accesoDetalle);
		
		for(AccesoDetalleRes accesoDetalleRes : listaAccesoDetalleRes){
			if(accesoDetalleRes.getId().getIntItemAccesoDetalleRes()==null){
				grabarAccesoDetalleRes(accesoDetalleRes, accesoDetalle);
			}else{
				listaAccesoDetalleResIU.add(accesoDetalleRes);
			}
		}
		
		boolean seEncuentraEnIU = Boolean.FALSE;
		for(AccesoDetalleRes accesoDetalleResBD : listaAccesoDetalleResBD){
			seEncuentraEnIU = Boolean.FALSE;
			for(AccesoDetalleRes accesoDetalleResIU : listaAccesoDetalleResIU){
				if(accesoDetalleResBD.getId().getIntItemAccesoDetalleRes().equals(accesoDetalleResIU.getId().getIntItemAccesoDetalleRes())){
					boAccesoDetalleRes.modificar(accesoDetalleResIU);
					seEncuentraEnIU = Boolean.TRUE;
					break;
				}
			}
			if(!seEncuentraEnIU){
				anularAccesoDetalleRes(accesoDetalleResBD, acceso);
			}
		}
		return accesoDetalle;
	}
	
	private AccesoDetalleRes anularAccesoDetalleRes(AccesoDetalleRes accesoDetalleRes, Acceso acceso) throws BusinessException{
		accesoDetalleRes.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
		accesoDetalleRes.setIntPersEmpresaElimina(acceso.getIntPersEmpresaModifica());
		accesoDetalleRes.setIntPersPersonaElimina(acceso.getIntPersPersonaModifica());
		accesoDetalleRes.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
		
		log.info(accesoDetalleRes);
		boAccesoDetalleRes.modificar(accesoDetalleRes);
		return accesoDetalleRes;
	}
	
	
	private AccesoDetalle anularAccesoDetalle(AccesoDetalle accesoDetalle, Acceso acceso) throws BusinessException{
		accesoDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
		accesoDetalle.setIntPersEmpresaElimina(acceso.getIntPersEmpresaModifica());
		accesoDetalle.setIntPersPersonaElimina(acceso.getIntPersPersonaModifica());
		accesoDetalle.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
		
		log.info(accesoDetalle);
		boAccesoDetalle.modificar(accesoDetalle);
		return accesoDetalle;
	}
	
	
	public Acceso validarAccesoParaApertura(Acceso accesoValidar, Integer intTipoFondoFijoValidar, Integer intTipoMoneda) 
		throws BusinessException{
		Acceso acceso = null;
		try{
			log.info("intTipoFondoFijoValidar:"+intTipoFondoFijoValidar);
			log.info("intTipoMoneda:"+intTipoMoneda);
			
			List<Acceso> listaAcceso = buscarAcceso(accesoValidar);
			if(listaAcceso == null || listaAcceso.isEmpty()){
				return acceso;
			}
			//log.info("listaAcceso.size:"+listaAcceso.size());
			acceso = listaAcceso.get(0);
			
			boolean poseeTipoFondoFijo = Boolean.FALSE;
			BancofondoId bancoFondoId;
			for(AccesoDetalle accesoDetalle : acceso.getListaAccesoDetalle()){
				if(accesoDetalle.getIntTipoAccesoDetalle().equals(Constante.TIPOACCESODETALLE_FONDOFIJO)){
					bancoFondoId = new BancofondoId();
					bancoFondoId.setIntEmpresaPk(accesoDetalle.getIntPersEmpresa());
					bancoFondoId.setIntItembancofondo(accesoDetalle.getIntItemBancoFondo());
					Bancofondo bancoFondo = boBancoFondo.getPorPk(bancoFondoId);
					log.info(bancoFondo);
					if(bancoFondo.getIntTipoFondoFijo().equals(intTipoFondoFijoValidar) 
					&& bancoFondo.getIntMonedaCod().equals(intTipoMoneda)){
						poseeTipoFondoFijo = Boolean.TRUE;
						accesoDetalle.setPoseeTipoFondoFijo(Boolean.TRUE);
						accesoDetalle.setListaAccesoDetalleRes(boAccesoDetalleRes.getPorAccesoDetalle(accesoDetalle));
						accesoDetalle.setFondoFijo(bancoFondo);
						break;
					}
				}
			}
			if(poseeTipoFondoFijo){
				return acceso;
			}else{
				return null;
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	
	public List<Acceso> buscarAcceso(Acceso accesoFiltro) throws BusinessException{
		List<Acceso> lista;
		try{
			log.info(accesoFiltro);
			lista = boAcceso.getPorBusqueda(accesoFiltro);
			int intCantTipoCuentaBancaria = 0;
			int intCantTipoFondoFijo = 0;
			BancocuentaId bancoCuentaId;
			
			for(Acceso acceso : lista){
				log.info(acceso);
				acceso.setListaAccesoDetalle(boAccesoDetalle.getPorAcceso(acceso));
				
				intCantTipoCuentaBancaria = 0;
				intCantTipoFondoFijo = 0;
				for(AccesoDetalle accesoDetalle : acceso.getListaAccesoDetalle()){
					log.info(accesoDetalle);
					if(accesoDetalle.getIntItemBancoCuenta()!=null){
						intCantTipoCuentaBancaria = intCantTipoCuentaBancaria + 1;
						accesoDetalle.setIntTipoAccesoDetalle(Constante.TIPOACCESODETALLE_CUENTABANCARIA);
						
						bancoCuentaId = new BancocuentaId();
						bancoCuentaId.setIntEmpresaPk(accesoDetalle.getIntPersEmpresa());
						bancoCuentaId.setIntItembancofondo(accesoDetalle.getIntItemBancoFondo());
						bancoCuentaId.setIntItembancocuenta(accesoDetalle.getIntItemBancoCuenta());
						accesoDetalle.setBancoCuenta(boBancoCuenta.getPorPk(bancoCuentaId));
						log.info(accesoDetalle.getBancoCuenta());
					}else{
						intCantTipoFondoFijo = intCantTipoFondoFijo + 1;
						accesoDetalle.setIntTipoAccesoDetalle(Constante.TIPOACCESODETALLE_FONDOFIJO);						
					}
				}
				acceso.setIntCantidadCuentaBancaria(intCantTipoCuentaBancaria);
				acceso.setIntCantidadFondoFijo(intCantTipoFondoFijo);
			}			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public Acceso modificarAcceso(Acceso acceso) throws BusinessException{
		try{
			List<AccesoDetalle> listaAccesoDetalle = acceso.getListaAccesoDetalle();
			List<AccesoDetalle> listaAccesoDetalleBD = boAccesoDetalle.getPorAcceso(acceso);
			List<AccesoDetalle> listaAccesoDetalleIU = new ArrayList<AccesoDetalle>();
			
			log.info(acceso);
			acceso = boAcceso.modificar(acceso);
			
			
			for(AccesoDetalle accesoDetalle : listaAccesoDetalle){
				if(accesoDetalle.getId().getIntItemAccesoDetalle()==null){
					grabarAccesoDetalle(accesoDetalle, acceso);
				}else{
					listaAccesoDetalleIU.add(accesoDetalle);
				}
			}
			
			boolean seEncuentraEnIU = Boolean.FALSE;
			for(AccesoDetalle accesoDetalleBD : listaAccesoDetalleBD){
				seEncuentraEnIU = Boolean.FALSE;
				for(AccesoDetalle accesoDetalleIU : listaAccesoDetalleIU){
					if(accesoDetalleBD.getId().getIntItemAccesoDetalle().equals(accesoDetalleIU.getId().getIntItemAccesoDetalle())){
						modificarAccesoDetalle(accesoDetalleIU,acceso);
						seEncuentraEnIU = Boolean.TRUE;
						break;
					}
				}
				if(!seEncuentraEnIU){
					anularAccesoDetalle(accesoDetalleBD,acceso);
				}
			}			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return acceso;
	}
	
	public Acceso obtenerAccesoPorControlFondosFijos(ControlFondosFijos controlFondosFijos) throws BusinessException{
		Acceso acceso = null;
		try{
			Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
			
			Acceso accesoFiltro = new Acceso();
			accesoFiltro.getId().setIntPersEmpresaAcceso(intIdEmpresa);
			accesoFiltro.setIntPersEmpresaSucursal(intIdEmpresa);
			accesoFiltro.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
			accesoFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			accesoFiltro.setIntSudeIdSubsucursal(controlFondosFijos.getIntSudeIdSubsucursal());
			
			List<Acceso> listaAcceso = buscarAcceso(accesoFiltro);
			if(listaAcceso==null || listaAcceso.size()!=1){
				throw new Exception("Inconsistenca del Acceso con el ControlFondosFijos");
			}
			
			acceso = listaAcceso.get(0);
			for(AccesoDetalle accesoDetalle : acceso.getListaAccesoDetalle()){
				if(accesoDetalle.getIntTipoAccesoDetalle().equals(Constante.TIPOACCESODETALLE_FONDOFIJO)){
					BancofondoId bancoFondoId = new BancofondoId();
					bancoFondoId.setIntEmpresaPk(accesoDetalle.getIntPersEmpresa());
					bancoFondoId.setIntItembancofondo(accesoDetalle.getIntItemBancoFondo());
					Bancofondo bancoFondo = boBancoFondo.getPorPk(bancoFondoId);
					if(bancoFondo.getIntTipoFondoFijo().equals(controlFondosFijos.getId().getIntParaTipoFondoFijo())){
						accesoDetalle.setFondoFijo(bancoFondo);
						acceso.setAccesoDetalleUsar(accesoDetalle);
						break;
					}
				}
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return acceso;
	}
	
}