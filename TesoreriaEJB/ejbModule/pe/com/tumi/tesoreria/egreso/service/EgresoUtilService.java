package pe.com.tumi.tesoreria.egreso.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.bo.ControlFondoFijoBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoDetalleBO;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.EgresoId;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;

public class EgresoUtilService {

	protected static Logger log = Logger.getLogger(EgresoUtilService.class);
	
	ControlFondoFijoBO boControlFondosFijos = (ControlFondoFijoBO)TumiFactory.get(ControlFondoFijoBO.class);
	EgresoBO boEgreso = (EgresoBO)TumiFactory.get(EgresoBO.class);
	EgresoDetalleBO boEgresoDetalle = (EgresoDetalleBO)TumiFactory.get(EgresoDetalleBO.class);

	
	private void procesarItems(ControlFondosFijos controlFondosFijos){
		controlFondosFijos.setStrNumeroApertura(
				obtenerPeriodoItem(	controlFondosFijos.getId().getIntItemPeriodoFondo(),
									controlFondosFijos.getId().getIntItemFondoFijo(),
									"000000"));
		if(controlFondosFijos.getEgreso().getId()!=null && controlFondosFijos.getEgreso().getId().getIntItemEgresoGeneral()!=null){
			controlFondosFijos.getEgreso().setStrNumeroEgreso(
					obtenerPeriodoItem(	controlFondosFijos.getEgreso().getIntItemPeriodoEgreso(), 
										controlFondosFijos.getEgreso().getIntItemEgreso(), 
										"000000"));
		}		
		if(controlFondosFijos.getEgreso().getLibroDiario()!=null){
			controlFondosFijos.getEgreso().getLibroDiario().setStrNumeroAsiento(
					obtenerPeriodoItem(	controlFondosFijos.getEgreso().getLibroDiario().getId().getIntContPeriodoLibro(),
										controlFondosFijos.getEgreso().getLibroDiario().getId().getIntContCodigoLibro(), 
										"000000"));
		}		
	}
	
	public Egreso procesarItems(Egreso egreso){
		egreso.setStrNumeroEgreso(
				obtenerPeriodoItem(	egreso.getIntItemPeriodoEgreso(), 
									egreso.getIntItemEgreso(), 
									"000000"));
			
		if(egreso.getLibroDiario()!=null){
			egreso.getLibroDiario().setStrNumeroAsiento(
					obtenerPeriodoItem(	egreso.getLibroDiario().getId().getIntContPeriodoLibro(),
										egreso.getLibroDiario().getId().getIntContCodigoLibro(), 
										"000000"));
		}
		return egreso;
	}
	
	private String obtenerPeriodoItem(Integer intPeriodo, Integer item, String patron){
		try{
			DecimalFormat formato = new DecimalFormat(patron);
			return intPeriodo+"-"+formato.format(Double.parseDouble(""+item));
		}catch (Exception e) {
			log.error(e.getMessage());
			return intPeriodo+"-"+item;
		}
	}
	
	public Integer obtenerMonedaDeCFF(ControlFondosFijos controlFondosFijos)throws BusinessException{
		try{
			Egreso egreso = boEgreso.getPorControlFondosFijos(controlFondosFijos);
			EgresoDetalle egresoDetalle = boEgresoDetalle.getPorEgreso(egreso).get(0);
			controlFondosFijos.setIntParaMoneda(egresoDetalle.getIntParaTipoMoneda());
			
			return controlFondosFijos.getIntParaMoneda();
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public Egreso obtenerEgresoYLibroDiario(EgresoId egresoId)throws BusinessException{
		Egreso egreso = null;
		try{
			LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			
			egreso = boEgreso.getPorPk(egresoId);
			LibroDiarioId libroDiarioId = new LibroDiarioId();
			libroDiarioId.setIntPersEmpresaLibro(egreso.getIntPersEmpresaLibro());
			libroDiarioId.setIntContPeriodoLibro(egreso.getIntContPeriodoLibro());
			libroDiarioId.setIntContCodigoLibro(egreso.getIntContCodigoLibro());
			LibroDiario libroDiario = libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);
			egreso.setLibroDiario(libroDiario);
			
			procesarItems(egreso);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	public List<ControlFondosFijos> obtenerListaNumeroApertura(Integer intTipoFondoFijo, Integer intAño, Integer intIdSucursal)	
	throws BusinessException{
		List<ControlFondosFijos> listaControl = new ArrayList<ControlFondosFijos>();
		try{
			List<ControlFondosFijos> listaBD = null;
			List<ControlFondosFijos> listaTemp = null;
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			ControlFondosFijos controlFiltro = new ControlFondosFijos();
			Integer intItemPeriodoFiltroDesde = Integer.parseInt(intAño+"00");
			Integer intItemPeriodoFiltroHasta = Integer.parseInt(intAño+"13");
			boolean realizarFiltroTotalSucursal = Boolean.FALSE;
			
			controlFiltro.getId().setIntParaTipoFondoFijo(intTipoFondoFijo);
			if(intIdSucursal.intValue()>0 || intIdSucursal.equals(Constante.PARAM_T_TOTALESSUCURSALES_SUCURSALES)){
				controlFiltro.getId().setIntSucuIdSucursal(intIdSucursal);
			}else{
				realizarFiltroTotalSucursal = Boolean.TRUE;
			}
			
			listaBD = boControlFondosFijos.getParaItem(controlFiltro);

			listaTemp = new ArrayList<ControlFondosFijos>();
			for(ControlFondosFijos cFF : listaBD){
				Integer intItemPeriodo = cFF.getId().getIntItemPeriodoFondo(); 
				if(intItemPeriodo.compareTo(intItemPeriodoFiltroDesde)>=0 
				&& intItemPeriodo.compareTo(intItemPeriodoFiltroHasta)<=0){
					listaTemp.add(cFF);
				}
			}
			
			if(realizarFiltroTotalSucursal){
				Integer intTipoSucursalFiltro = intIdSucursal;
				for(ControlFondosFijos cFF : listaTemp){
					Sucursal sucursal = new Sucursal();
					sucursal.getId().setIntPersEmpresaPk(cFF.getId().getIntSucuIdSucursal());
					sucursal.getId().setIntIdSucursal(cFF.getId().getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);
					if(sucursal.getIntIdTipoSucursal().equals(intTipoSucursalFiltro)){
						listaControl.add(cFF);
					}
				}				
			}else{
				listaControl = listaTemp;
			}
			
			for(ControlFondosFijos cFF : listaControl){
				procesarItems(cFF);
				//log.info(cFF);
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaControl;
	}
	
	
	public List<Egreso> buscarEgresoParaFondosFijos(List<Persona>listaPersona, Egreso egresoFiltro, 
		List<ControlFondosFijos> listaControlFondosFijos, Date dtFechaDesde, Date dtFechaHasta)throws BusinessException{
		List<Egreso> listaEgreso = new ArrayList<Egreso>();
		try{
			
			List<Egreso> listaEgresoTemp = new ArrayList<Egreso>();
			List<Egreso> listaEgresoTemp1 = new ArrayList<Egreso>();
			for(ControlFondosFijos control : listaControlFondosFijos){
				listaEgresoTemp = boEgreso.getListaPorControlFondosFijos(control);
				for(Egreso egreso : listaEgresoTemp){
					egreso.setControlFondosFijos(control);
					listaEgresoTemp1.add(egreso);
				}
			}
			listaEgreso = listaEgresoTemp1;
			
			
			if(listaPersona!=null && !listaPersona.isEmpty()){
				listaEgresoTemp = new ArrayList<Egreso>();
				for(Egreso egreso : listaEgreso){
					boolean egresoPoseePersona = Boolean.FALSE;
					for(Persona persona : listaPersona){
						if(egreso.getIntPersPersonaGirado().equals(persona.getIntIdPersona())){
							egresoPoseePersona = Boolean.TRUE;
							break;
						}
					}
					if(egresoPoseePersona){
						listaEgresoTemp.add(egreso);
					}
				}
				listaEgreso = listaEgresoTemp;
			}			
			
			listaEgresoTemp = new ArrayList<Egreso>();			
			for(Egreso egreso : listaEgreso){
				if(egresoFiltro.getIntParaEstado()!=null && egreso.getIntParaEstado().equals(egresoFiltro.getIntParaEstado())){
					listaEgresoTemp.add(egreso);
				}else if(egresoFiltro.getIntParaEstado()==null){
					listaEgresoTemp.add(egreso);
				}
			}
			listaEgreso = listaEgresoTemp;
			
			
			listaEgresoTemp = new ArrayList<Egreso>();			
			for(Egreso egreso : listaEgreso){
				boolean pasoDesde = Boolean.FALSE;
				boolean pasoHasta = Boolean.FALSE;
				if(dtFechaDesde != null && egreso.getDtFechaEgreso().compareTo(dtFechaDesde) >= 0){
					pasoDesde = Boolean.TRUE;
				}else if(dtFechaDesde == null){
					pasoDesde = Boolean.TRUE;
				}
				if(dtFechaHasta != null && egreso.getDtFechaEgreso().compareTo(dtFechaHasta) <= 0){
					pasoHasta = Boolean.TRUE;
				}else if(dtFechaHasta == null){
					pasoHasta = Boolean.TRUE;
				}
				if(pasoDesde && pasoHasta){
					listaEgresoTemp.add(egreso);
				}
			}
			listaEgreso = listaEgresoTemp;
			
			
			listaEgresoTemp = new ArrayList<Egreso>();			
			for(Egreso egreso : listaEgreso){
				egreso.setListaEgresoDetalle(boEgresoDetalle.getPorEgreso(egreso));
				if(egreso.getListaEgresoDetalle()==null || egreso.getListaEgresoDetalle().isEmpty()){
					continue;
				}
				EgresoDetalle egresoDetalle = egreso.getListaEgresoDetalle().get(0);
				if(egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)
				|| egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS) 
				|| egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)
				|| egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
				|| egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)
				|| egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)
				|| egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS)){
					procesarItems(egreso);
					listaEgresoTemp.add(egreso);
				}
				egreso.getControlFondosFijos().setIntParaMoneda(obtenerMonedaDeCFF(egreso.getControlFondosFijos()));
			}
			listaEgreso = listaEgresoTemp;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaEgreso;
	}
	
	public List<Egreso> buscarEgresoParaCaja(List<Persona>listaPersona, Egreso egresoFiltro, Date dtDesdeFiltro, Date dtHastaFiltro)
	throws BusinessException{
		List<Egreso> listaEgreso = new ArrayList<Egreso>();
		try{
			listaEgreso = boEgreso.getListaPorBuscar(egresoFiltro, dtDesdeFiltro, dtHastaFiltro);
			List<Egreso> listaEgresoTemp = new ArrayList<Egreso>();
			
			for(Egreso egreso : listaEgreso){
				if(egreso.getIntItemBancoCuenta()!=null 
				&& (egreso.getIntParaSubTipoOperacion().equals(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION)
				 || egreso.getIntParaSubTipoOperacion().equals(Constante.PARAM_T_TIPODESUBOPERACION_ACUENTA))){
					listaEgresoTemp.add(egreso);
				}
			}
			listaEgreso = listaEgresoTemp;
			
			if(listaPersona!=null && !listaPersona.isEmpty()){
				listaEgresoTemp = new ArrayList<Egreso>();
				for(Egreso egreso : listaEgreso){
					boolean egresoPoseePersona = Boolean.FALSE;
					for(Persona persona : listaPersona){
						if(egreso.getIntPersPersonaGirado().equals(persona.getIntIdPersona())){
							egresoPoseePersona = Boolean.TRUE;
							break;
						}
					}
					if(egresoPoseePersona){
						listaEgresoTemp.add(egreso);
					}
				}
				listaEgreso = listaEgresoTemp;
			}
			
			for(Egreso egreso : listaEgreso){
				egreso.setListaEgresoDetalle(boEgresoDetalle.getPorEgreso(egreso));
				if(egreso.getListaEgresoDetalle()==null || egreso.getListaEgresoDetalle().isEmpty()){
					continue;
				}
				EgresoDetalle egresoDetalle = egreso.getListaEgresoDetalle().get(0);
				if(egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)
				|| egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS) 
				|| egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)
				|| egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
				|| egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)
				|| egresoDetalle.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
					procesarItems(egreso);
				}
			}
			
			if(listaEgreso!=null && !listaEgreso.isEmpty()){
				TablaFacadeRemote tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
				List<Tabla> listaTablaBanco = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_BANCOS));
				List<Bancofondo> listaBanco = cargarListaBanco();
				for(Egreso egreso : listaEgreso){
					for(Bancofondo banco : listaBanco){
						for(Bancocuenta bancoCuenta : banco.getListaBancocuenta()){
							if(egreso.getIntItemBancoFondo().equals(bancoCuenta.getId().getIntItembancofondo()) && 
								egreso.getIntItemBancoCuenta().equals(bancoCuenta.getId().getIntItembancocuenta())){
								bancoCuenta.setBancofondo(banco);
								bancoCuenta.setStrEtiqueta(obtenerEtiquetaBanco(banco.getIntBancoCod(),listaTablaBanco)+"-"+bancoCuenta.getCuentaBancaria().getStrNroCuentaBancaria());
								egreso.setBancoCuenta(bancoCuenta);
							}
						}
					}
				}
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaEgreso;
	}
	
	private String obtenerEtiquetaBanco(Integer intBancoCod, List<Tabla> listaTablaBanco) throws Exception{		
		for(Tabla tabla : listaTablaBanco){
			if(tabla.getIntIdDetalle().equals(intBancoCod)){
				return tabla.getStrDescripcion();
			}
		}
		return null;
	}
	
	private List<Bancofondo> cargarListaBanco()throws Exception{
		BancoFacadeLocal bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
		Bancofondo bancoFondoFiltro = new Bancofondo();
		bancoFondoFiltro.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_BANCO);
		bancoFondoFiltro.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		return bancoFacade.buscarBancoFondo(bancoFondoFiltro);
	}
	
	
	public Egreso obtenerEgresoPorExpedienteCredito(ExpedienteCredito expedienteCredito)throws BusinessException{
		Egreso egreso = null;
		try{
			Integer intIdEmpresa = expedienteCredito.getId().getIntPersEmpresaPk();
			Egreso egresoFiltro = new Egreso();
			egresoFiltro.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egresoFiltro.setIntCuentaGirado(expedienteCredito.getId().getIntCuentaPk());
			
			EgresoDetalle egresoDetalleFiltro = new EgresoDetalle();
			egresoDetalleFiltro.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egresoDetalleFiltro.setStrNumeroDocumento(
					expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
			
			List<EgresoDetalle> listaEgresoDetalle = boEgresoDetalle.getListaPorBuscar(egresoDetalleFiltro);
			HashSet<Integer> hashIdEgreso = new HashSet<Integer>();
			if (listaEgresoDetalle!=null && !listaEgresoDetalle.isEmpty()) {
				for(EgresoDetalle egresoDetalle : listaEgresoDetalle){
					hashIdEgreso.add(egresoDetalle.getId().getIntItemEgresoGeneral());
				}
			}
			List<Egreso> listaEgreso = new ArrayList<Egreso>();
			if (hashIdEgreso!=null && !hashIdEgreso.isEmpty()) {
				for(Integer intIdEgreso : hashIdEgreso){
					EgresoId egresoId = new EgresoId();
					egresoId.setIntPersEmpresaEgreso(intIdEmpresa);
					egresoId.setIntItemEgresoGeneral(intIdEgreso);
					listaEgreso.add(boEgreso.getPorPk(egresoId));
				}
			}
			if (listaEgreso!=null && !listaEgreso.isEmpty()) {
				for(Egreso egresoBD : listaEgreso){
					if(egresoBD.getIntCuentaGirado().equals(egresoFiltro.getIntCuentaGirado())){
						egreso = egresoBD;
						break;
					}
				}
			}else{
				egreso = new Egreso();
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	public Archivo getArchivoPorEgreso(Egreso egreso)throws BusinessException{
		Archivo archivo = null;
		try{
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);			
			if(egreso.getIntParaTipoApoderado()==null){
				return archivo;
			}
			
			ArchivoId archivoId = new ArchivoId();
			archivoId.setIntParaTipoCod(egreso.getIntParaTipoApoderado());
			archivoId.setIntItemArchivo(egreso.getIntItemArchivoApoderado());
			archivoId.setIntItemHistorico(egreso.getIntItemHistoricoApoderado());
			archivo = generalFacade.getArchivoPorPK(archivoId);			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return archivo;
	}
	
	public Egreso obtenerEgresoPorMovilidad(Movilidad movilidad)throws BusinessException{
		Egreso egreso = null;
		try{
			EgresoId egresoId = new EgresoId();
			egresoId.setIntPersEmpresaEgreso(movilidad.getIntPersEmpresaEgreso());
			egresoId.setIntItemEgresoGeneral(movilidad.getIntItemEgresoGeneral());
			
			egreso = boEgreso.getPorPk(egresoId);
			
			procesarItems(egreso);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	public List<Egreso> buscarEgresoParaTelecredito(Egreso egresoFiltro)throws BusinessException{
		List<Egreso> listaEgreso = new ArrayList<Egreso>();
		try{
			log.info(egresoFiltro);
			listaEgreso = boEgreso.getListaParaTelecredito(egresoFiltro);
			for(Egreso egreso : listaEgreso){
				procesarItems(egreso);
				log.info(egreso);
			}
			if(egresoFiltro.getStrNumeroEgreso()!=null && !egresoFiltro.getStrNumeroEgreso().isEmpty()){
				List<Egreso> listaEgresoTemp = new ArrayList<Egreso>();
				for(Egreso egresoTemp : listaEgreso){
					if(egresoTemp.getStrNumeroEgreso().contains(egresoFiltro.getStrNumeroEgreso())){
						listaEgresoTemp.add(egresoTemp);
					}
				}
				listaEgreso = listaEgresoTemp;				
			}
			for(Egreso egreso : listaEgreso){
				egreso.setPersonaApoderado(devolverPersonaCargada(egreso.getIntPersPersonaGirado()));
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaEgreso;
	}
	
	private Persona devolverPersonaCargada(Integer intIdPersona) throws Exception{
		//log.info(intIdPersona);
		PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
		Persona persona = personaFacade.getPersonaPorPK(intIdPersona);
		if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
			persona = personaFacade.getPersonaNaturalPorIdPersona(persona.getIntIdPersona());			
			agregarDocumentoDNI(persona);
			agregarNombreCompleto(persona);			
		
		}else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
			persona.setJuridica(personaFacade.getJuridicaPorPK(persona.getIntIdPersona()));			
		}		
		return persona;
	}
	
	private void agregarNombreCompleto(Persona persona){
		persona.getNatural().setStrNombreCompleto(
				persona.getNatural().getStrNombres()+" "+
				persona.getNatural().getStrApellidoPaterno()+" "+
				persona.getNatural().getStrApellidoMaterno());
	}
	
	private void agregarDocumentoDNI(Persona persona){
		for(Documento documento : persona.getListaDocumento()){
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				persona.setDocumento(documento);
			}
		}
	}
	
	public ControlFondosFijos obtenerControlFondosFijosPorEgresoDetalle(EgresoDetalle egresoDetalle)throws Exception{
		ControlFondosFijos controlFondosFijos = null;
		try{
			ControlFondosFijosId controlFondosFijosId = new ControlFondosFijosId();
			controlFondosFijosId.setIntPersEmpresa(egresoDetalle.getId().getIntPersEmpresaEgreso());
			controlFondosFijosId.setIntParaTipoFondoFijo(egresoDetalle.getIntParaTipoFondoFijo());
			controlFondosFijosId.setIntItemPeriodoFondo(egresoDetalle.getIntItemPeriodoFondo());
			controlFondosFijosId.setIntSucuIdSucursal(egresoDetalle.getIntSucuIdSucursal());
			controlFondosFijosId.setIntItemFondoFijo(egresoDetalle.getIntItemFondoFijo());
			
			controlFondosFijos = boControlFondosFijos.getPorPk(controlFondosFijosId);
		}catch(Exception e){
			throw e;
		}
		return controlFondosFijos;
	}
}