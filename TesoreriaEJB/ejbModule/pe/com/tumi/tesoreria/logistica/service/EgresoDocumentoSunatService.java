package pe.com.tumi.tesoreria.logistica.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.bo.EgresoBO;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;
import pe.com.tumi.tesoreria.logistica.bo.DocumentoSunatBO;
import pe.com.tumi.tesoreria.logistica.bo.OrdenCompraDetalleBO;
import pe.com.tumi.tesoreria.logistica.bo.ProveedorBO;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunatDetalle;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;

public class EgresoDocumentoSunatService {

	protected static Logger log = Logger.getLogger(EgresoDocumentoSunatService.class);
	DocumentoSunatService documentoSunatService = (DocumentoSunatService)TumiFactory.get(DocumentoSunatService.class);
	DocumentoSunatModeloService documentoSunatModeloService = (DocumentoSunatModeloService)TumiFactory.get(DocumentoSunatModeloService.class);
	OrdenCompraDetalleBO boOrdenCompraDetalle = (OrdenCompraDetalleBO)TumiFactory.get(OrdenCompraDetalleBO.class);
	DocumentoSunatBO boDocumentoSunat = (DocumentoSunatBO)TumiFactory.get(DocumentoSunatBO.class);
	EgresoBO boEgreso = (EgresoBO)TumiFactory.get(EgresoBO.class);
	ProveedorBO boProveedor = (ProveedorBO)TumiFactory.get(ProveedorBO.class);
	
	private final Integer EDI_NORMAL = 1;
	private final Integer EDI_DIFERENCIAL = 2;
	
	
	private EgresoDetalleInterfaz generarEgresoDetalleInterfaz(DocumentoSunat documentoSunat, Integer intTipoEDI)throws Exception{
		EmpresaFacadeRemote empresaFacade =  (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
		EgresoDetalleInterfaz eDI = new EgresoDetalleInterfaz();
		eDI.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
		eDI.setStrNroDocumento(""+documentoSunat.getId().getIntItemDocumentoSunat());			
		log.info(documentoSunat.getDetalleIGV().getBdMontoTotal());
		log.info(documentoSunat.getDetalleTotalGeneral().getBdMontoTotal());
		
		if(documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_RECIBOHONORARIOS))
			eDI.setBdMonto(documentoSunat.getDetalleIGV().getBdMontoTotal());
		else
			eDI.setBdMonto(documentoSunat.getDetalleTotalGeneral().getBdMontoTotal());
		
		eDI.setIntParaConcepto(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
		eDI.setBdSubTotal(eDI.getBdMonto());
		eDI.setSucursal(empresaFacade.getSucursalPorId(documentoSunat.getDetalleTotalGeneral().getIntSucuIdSucursal()));
		if(intTipoEDI.equals(EDI_NORMAL)){
			eDI.setStrDescripcion(documentoSunat.getStrGlosa());
			eDI.setEsDiferencial(Boolean.FALSE);
		}			
		else{
			eDI.setStrDescripcion("Monto Diferencial");
			eDI.setEsDiferencial(Boolean.TRUE);
		}
			
		//eDI.setDocumentoSunat(documentoSunat);
		for(Subsucursal subsucursal : eDI.getSucursal().getListaSubSucursal()){
			if(subsucursal.getId().getIntIdSubSucursal().equals(documentoSunat.getDetalleTotalGeneral().getIntSudeIdSubsucursal()))
				eDI.setSubsucursal(subsucursal);
		}
		return eDI;
	}
	
	private boolean existeDiferencialCambiario(DocumentoSunat documentoSunat, DocumentoSunat documentoSunatDiferencia)throws Exception{
		/*log.info(documentoSunat.getDetalleIGV().getBdMontoTotal());
		log.info(documentoSunatDiferencia.getDetalleIGV().getBdMontoTotal());
		log.info(documentoSunat.getDetalleTotalGeneral().getBdMontoTotal());
		log.info(documentoSunatDiferencia.getDetalleTotalGeneral().getBdMontoTotal());
		*/if(documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_RECIBOHONORARIOS)){
			if(!documentoSunat.getDetalleIGV().getBdMontoTotal().equals(documentoSunatDiferencia.getDetalleIGV().getBdMontoTotal())){
				documentoSunatDiferencia.getDetalleIGV().setBdMontoTotal(documentoSunatDiferencia.getDetalleIGV().getBdMontoTotal()
				.subtract(documentoSunat.getDetalleIGV().getBdMontoTotal()));
				return Boolean.TRUE;
			}else{
				return Boolean.FALSE;
			}			
		}else{
			if(documentoSunat.getDetalleTotalGeneral().getBdMontoTotal().compareTo(documentoSunatDiferencia.getDetalleTotalGeneral().getBdMontoTotal())!=0){
				documentoSunatDiferencia.getDetalleTotalGeneral().setBdMontoTotal(documentoSunatDiferencia.getDetalleTotalGeneral().getBdMontoTotal()
				.subtract(documentoSunat.getDetalleTotalGeneral().getBdMontoTotal()));
				return Boolean.TRUE;
			}else{
				return Boolean.FALSE;
			}			
		}
	}
	
	private DocumentoSunat obtenerDocumentoSunatDiferencia(DocumentoSunat documentoSunat)throws Exception{
		DocumentoSunat documentoSunatDiferencia = new DocumentoSunat();
		documentoSunatDiferencia.setId(documentoSunat.getId());
		documentoSunatDiferencia = documentoSunatService.buscarDocumentoSunat(documentoSunatDiferencia, null).get(0);
		documentoSunatDiferencia.getOrdenCompra().setProveedor(boProveedor.getPorOrdenCompra(documentoSunatDiferencia.getOrdenCompra()));
		for(DocumentoSunatDetalle documentoSunatDetalle : documentoSunatDiferencia.getListaDocumentoSunatDetalle())
			documentoSunatDetalle.setOrdenCompraDetalle(boOrdenCompraDetalle.getPorDocumentoSunatDetalle(documentoSunatDetalle));
		
		GeneralFacadeRemote generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);		
		log.info(documentoSunat.getIntParaMoneda());
		Date dtFechaTipoCambioDiferencial = new Date();
		//Se calcula el tipo de cambio con dtFechaGiro, esto para casos de verRegistro y calcular el tipoDeCambio con la fecha en la que se giró 
		if(documentoSunat.getDtFechaDeGiro()!=null){
			dtFechaTipoCambioDiferencial = documentoSunat.getDtFechaDeGiro();
		}
		
		TipoCambio tipoCambioActual = generalFacade.getTipoCambioPorMonedaYFecha(
						documentoSunat.getIntParaMoneda(),	dtFechaTipoCambioDiferencial,	documentoSunat.getId().getIntPersEmpresa());
		log.info(tipoCambioActual);
		return documentoSunatService.calcularMontos(documentoSunatDiferencia, tipoCambioActual);		
	}
	
	public List<EgresoDetalleInterfaz> cargarListaEgresoDetalleInterfaz(DocumentoSunat documentoSunat)throws BusinessException{
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = new ArrayList<EgresoDetalleInterfaz>();
		try{
			EgresoDetalleInterfaz eDINormal = generarEgresoDetalleInterfaz(documentoSunat, EDI_NORMAL);			
			listaEgresoDetalleInterfaz.add(eDINormal);
			DocumentoSunat documentoSunatDiferencia = obtenerDocumentoSunatDiferencia(documentoSunat);
			log.info(documentoSunat.getDetalleIGV().getBdMontoTotal());
			log.info(documentoSunatDiferencia.getDetalleIGV().getBdMontoTotal());
			log.info(documentoSunat.getDetalleTotalGeneral().getBdMontoTotal());
			log.info(documentoSunatDiferencia.getDetalleTotalGeneral().getBdMontoTotal());
			//log.info(tipoCambioActual);
			//log.info(documentoSunatDiferencia.getDetalleTotalGeneral());
			if(existeDiferencialCambiario(documentoSunat, documentoSunatDiferencia)){
				EgresoDetalleInterfaz eDIDiferencial = generarEgresoDetalleInterfaz(documentoSunatDiferencia, EDI_DIFERENCIAL);	
				listaEgresoDetalleInterfaz.add(eDIDiferencial);
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}		
		return listaEgresoDetalleInterfaz;
	}
	
	public List<DocumentoSunat> obtenerListaDocumentoSunatPorEgreso(Egreso egreso)throws BusinessException{
		List<DocumentoSunat> listaDocumentoSunat= new ArrayList<DocumentoSunat>();
		try{
			DocumentoSunat documentoSunatFiltro = new DocumentoSunat();
			documentoSunatFiltro.getId().setIntPersEmpresa(egreso.getId().getIntPersEmpresaEgreso());
			documentoSunatFiltro.setIntEmpresaLibro(egreso.getIntPersEmpresaLibro());
			documentoSunatFiltro.setIntPeriodoLibro(egreso.getIntContPeriodoLibro());
			documentoSunatFiltro.setIntCodigoLibro(egreso.getIntContCodigoLibro());
			
			//listaDocumentoSunat = boDocumentoSunat.getPorBuscar(documentoSunatFiltro);
			listaDocumentoSunat = documentoSunatService.buscarDocumentoSunat(documentoSunatFiltro, null);
		}catch(Exception e){
			throw new BusinessException(e);
		}		
		return listaDocumentoSunat;
	}	
	
	private BigDecimal obtenerMontoTotalDocumentoSunat(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz)throws Exception{
		BigDecimal bdMontoTotal = new BigDecimal(0);
		for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
			bdMontoTotal = bdMontoTotal.add(eDI.getDocumentoSunat().getDetalleTotalGeneral().getBdMontoTotal());
		}
		return bdMontoTotal;
	}
	
	private TipoCambio obtenerTipoCambioActual(Integer intParaMoneda, Integer intIdEmpresa)throws Exception{
		GeneralFacadeRemote generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		return generalFacade.getTipoCambioPorMonedaYFecha(intParaMoneda, new Date(), intIdEmpresa);		
	}
	
	private ModeloDetalle obtenerModeloDetalle(DocumentoSunat documentoSunat)throws Exception{
		ModeloFacadeRemote modeloFacade =  (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
		Integer intIdEmpresa = documentoSunat.getId().getIntPersEmpresa();
		Modelo modelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_CANCELACIONORDENCOMPRA, intIdEmpresa).get(0);
		log.info(modelo);
		ModeloDetalle modeloDetalleUsar = null;
		if(documentoSunat.getIntParaTipoComprobante().equals(Constante.PARAM_T_TIPOCOMPROBANTE_RECIBOHONORARIOS))
			modeloDetalleUsar = documentoSunatModeloService.obtenerModeloDetalleIGVGiro(modelo);
		else
			modeloDetalleUsar = documentoSunatModeloService.obtenerModeloDetalleIGVGiro(modelo);
		
		return modeloDetalleUsar;
	}
	
	private EgresoDetalle generarEgresoDetalleDebe(DocumentoSunat documentoSunat,	Usuario usuario)throws Exception{
		log.info("--generarEgresoDetalleDebe");
		Integer intIdEmpresa = documentoSunat.getId().getIntPersEmpresa();
		OrdenCompra ordenCompra = documentoSunat.getOrdenCompra();
		Persona personaProveedor = ordenCompra.getPersonaProveedor();
		DocumentoSunatDetalle detalleTotalGeneral = documentoSunat.getDetalleTotalGeneral();
		ModeloDetalle modeloDetalle = obtenerModeloDetalle(documentoSunat);
		
		EgresoDetalle egresoDetalleDebe = new EgresoDetalle();		
		egresoDetalleDebe.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoDetalleDebe.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
		egresoDetalleDebe.setIntParaTipoComprobante(documentoSunat.getIntParaTipoComprobante());
		egresoDetalleDebe.setStrSerieDocumento(documentoSunat.getStrSerieDocumento());
		egresoDetalleDebe.setStrNumeroDocumento(documentoSunat.getStrNumeroDocumento());
		egresoDetalleDebe.setStrDescripcionEgreso(null);
		egresoDetalleDebe.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalleDebe.setIntPersonaGirado(personaProveedor.getIntIdPersona());
		egresoDetalleDebe.setIntCuentaGirado(null);
		egresoDetalleDebe.setIntSucuIdSucursalEgreso(detalleTotalGeneral.getIntSucuIdSucursal());
		egresoDetalleDebe.setIntSudeIdSubsucursalEgreso(detalleTotalGeneral.getIntSudeIdSubsucursal());
		egresoDetalleDebe.setIntParaTipoMoneda(detalleTotalGeneral.getIntParaTipoMoneda());

		egresoDetalleDebe.setBdMontoCargo(detalleTotalGeneral.getBdMontoTotal().abs());
		egresoDetalleDebe.setBdMontoAbono(null);
		
		egresoDetalleDebe.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		egresoDetalleDebe.setTsFechaRegistro(MyUtil.obtenerFechaActual());
		egresoDetalleDebe.setIntPersEmpresaUsuario(intIdEmpresa);
		egresoDetalleDebe.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
		egresoDetalleDebe.setIntPersEmpresaLibroDestino(null);
		egresoDetalleDebe.setIntContPeriodoLibroDestino(null);
		egresoDetalleDebe.setIntContCodigoLibroDestino(null);
		egresoDetalleDebe.setIntPersEmpresaCuenta(modeloDetalle.getPlanCuenta().getId().getIntEmpresaCuentaPk());
		egresoDetalleDebe.setIntContPeriodoCuenta(modeloDetalle.getPlanCuenta().getId().getIntPeriodoCuenta());
		egresoDetalleDebe.setStrContNumeroCuenta(modeloDetalle.getPlanCuenta().getId().getStrNumeroCuenta());
		egresoDetalleDebe.setIntParaTipoFondoFijo(null);
		egresoDetalleDebe.setIntItemPeriodoFondo(null);
		egresoDetalleDebe.setIntSucuIdSucursal(null);
		egresoDetalleDebe.setIntItemFondoFijo(null);
		
		return egresoDetalleDebe;
	}	
	
	private EgresoDetalle generarEgresoDetalleDiferencial(EgresoDetalleInterfaz eDI, ControlFondosFijos controlFondosFijos, Usuario usuario)
	throws Exception{
		log.info("--generarEgresoDetalleDiferencial");
		DocumentoSunat documentoSunat = eDI.getDocumentoSunat();
		Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
		DocumentoSunatDetalle detalleTotalGeneral = documentoSunat.getDetalleTotalGeneral();
		ModeloDetalle modeloDetalle = obtenerModeloDetalle(documentoSunat);
		
		EgresoDetalle egresoDetalleDiferencial = new EgresoDetalle();		
		egresoDetalleDiferencial.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoDetalleDiferencial.setIntParaDocumentoGeneral(controlFondosFijos.getId().getIntParaTipoFondoFijo());
		egresoDetalleDiferencial.setIntParaTipoComprobante(null);
		egresoDetalleDiferencial.setStrSerieDocumento(null);
		egresoDetalleDiferencial.setStrNumeroDocumento(null);
		egresoDetalleDiferencial.setStrDescripcionEgreso(null);
		egresoDetalleDiferencial.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalleDiferencial.setIntPersonaGirado(controlFondosFijos.getSucursal().getIntPersPersonaPk());
		egresoDetalleDiferencial.setIntCuentaGirado(null);
		egresoDetalleDiferencial.setIntSucuIdSucursalEgreso(controlFondosFijos.getId().getIntSucuIdSucursal());
		egresoDetalleDiferencial.setIntSudeIdSubsucursalEgreso(controlFondosFijos.getIntSudeIdSubsucursal());
		egresoDetalleDiferencial.setIntParaTipoMoneda(detalleTotalGeneral.getIntParaTipoMoneda());

		if(eDI.getBdMonto().signum()>=0)
			egresoDetalleDiferencial.setBdMontoCargo(eDI.getBdMonto().abs());
		else
			egresoDetalleDiferencial.setBdMontoAbono(eDI.getBdMonto().abs());
		
		egresoDetalleDiferencial.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		egresoDetalleDiferencial.setTsFechaRegistro(MyUtil.obtenerFechaActual());
		egresoDetalleDiferencial.setIntPersEmpresaUsuario(intIdEmpresa);
		egresoDetalleDiferencial.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
		egresoDetalleDiferencial.setIntPersEmpresaLibroDestino(null);
		egresoDetalleDiferencial.setIntContPeriodoLibroDestino(null);
		egresoDetalleDiferencial.setIntContCodigoLibroDestino(null);
		egresoDetalleDiferencial.setIntPersEmpresaCuenta(modeloDetalle.getPlanCuenta().getId().getIntEmpresaCuentaPk());
		egresoDetalleDiferencial.setIntContPeriodoCuenta(modeloDetalle.getPlanCuenta().getId().getIntPeriodoCuenta());
		egresoDetalleDiferencial.setStrContNumeroCuenta(modeloDetalle.getPlanCuenta().getId().getStrNumeroCuenta());
		egresoDetalleDiferencial.setIntParaTipoFondoFijo(null);
		egresoDetalleDiferencial.setIntItemPeriodoFondo(null);
		egresoDetalleDiferencial.setIntSucuIdSucursal(null);
		egresoDetalleDiferencial.setIntItemFondoFijo(null);
		
		return egresoDetalleDiferencial;
	}
	
	private EgresoDetalle generarEgresoDetalleHaber(ControlFondosFijos controlFondosFijos, Usuario usuario, BigDecimal bdMontoGirar)
	throws Exception{
		BancoFacadeLocal bancoFacade =  (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
		
		Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
		Bancofondo bancoFondo = bancoFacade.obtenerBancoFondoParaIngresoDesdeControl(controlFondosFijos);
		
		EgresoDetalle egresoDetalleHaber = new EgresoDetalle();		
		egresoDetalleHaber.getId().setIntPersEmpresaEgreso(intIdEmpresa);
		egresoDetalleHaber.setIntParaDocumentoGeneral(controlFondosFijos.getId().getIntParaTipoFondoFijo());
		egresoDetalleHaber.setIntParaTipoComprobante(null);
		egresoDetalleHaber.setStrSerieDocumento(null);
		egresoDetalleHaber.setStrNumeroDocumento(null);
		egresoDetalleHaber.setStrDescripcionEgreso(null);
		egresoDetalleHaber.setIntPersEmpresaGirado(intIdEmpresa);
		egresoDetalleHaber.setIntPersonaGirado(controlFondosFijos.getSucursal().getIntPersPersonaPk());
		egresoDetalleHaber.setIntCuentaGirado(null);
		egresoDetalleHaber.setIntSucuIdSucursalEgreso(controlFondosFijos.getId().getIntSucuIdSucursal());
		egresoDetalleHaber.setIntSudeIdSubsucursalEgreso(controlFondosFijos.getIntSudeIdSubsucursal());
		egresoDetalleHaber.setIntParaTipoMoneda(null);

		egresoDetalleHaber.setBdMontoCargo(null);
		egresoDetalleHaber.setBdMontoAbono(bdMontoGirar.abs());
		
		egresoDetalleHaber.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		egresoDetalleHaber.setTsFechaRegistro(MyUtil.obtenerFechaActual());
		egresoDetalleHaber.setIntPersEmpresaUsuario(intIdEmpresa);
		egresoDetalleHaber.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
		egresoDetalleHaber.setIntPersEmpresaLibroDestino(null);
		egresoDetalleHaber.setIntContPeriodoLibroDestino(null);
		egresoDetalleHaber.setIntContCodigoLibroDestino(null);
		egresoDetalleHaber.setIntPersEmpresaCuenta(bancoFondo.getFondoDetalleUsar().getPlanCuenta().getId().getIntEmpresaCuentaPk());
		egresoDetalleHaber.setIntContPeriodoCuenta(bancoFondo.getFondoDetalleUsar().getPlanCuenta().getId().getIntPeriodoCuenta());
		egresoDetalleHaber.setStrContNumeroCuenta(bancoFondo.getFondoDetalleUsar().getPlanCuenta().getId().getStrNumeroCuenta());
		egresoDetalleHaber.setIntParaTipoFondoFijo(null);
		egresoDetalleHaber.setIntItemPeriodoFondo(null);
		egresoDetalleHaber.setIntSucuIdSucursal(null);
		egresoDetalleHaber.setIntItemFondoFijo(null);
		
		return egresoDetalleHaber;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalleDebe(EgresoDetalleInterfaz egresoDetalleInterfaz,ControlFondosFijos controlFondosFijos) 
	throws Exception{		
		DocumentoSunat documentoSunat = egresoDetalleInterfaz.getDocumentoSunat();
		Integer intIdEmpresa = documentoSunat.getId().getIntPersEmpresa();
		Persona personaGirar = documentoSunat.getProveedor();
		TipoCambio tipoCambioActual = null;
		ModeloDetalle modeloDetalle = obtenerModeloDetalle(documentoSunat);
			
		Integer intMoneda = controlFondosFijos.getIntParaMoneda();
			
		boolean esMonedaExtranjera;
		LibroDiarioDetalle libroDiarioDetalleDebe = new LibroDiarioDetalle();
		libroDiarioDetalleDebe.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalleDebe.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());				
		libroDiarioDetalleDebe.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
		libroDiarioDetalleDebe.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
		libroDiarioDetalleDebe.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
		libroDiarioDetalleDebe.setIntPersPersona(personaGirar.getIntIdPersona());
		libroDiarioDetalleDebe.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS);
		libroDiarioDetalleDebe.setStrSerieDocumento(documentoSunat.getStrSerieDocumento());
		libroDiarioDetalleDebe.setStrNumeroDocumento(documentoSunat.getStrNumeroDocumento());
		libroDiarioDetalleDebe.setIntParaMonedaDocumento(intMoneda);
		
		if(!libroDiarioDetalleDebe.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
			libroDiarioDetalleDebe.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			esMonedaExtranjera = Boolean.TRUE;
		}else{
			esMonedaExtranjera = Boolean.FALSE;
		}
			
		libroDiarioDetalleDebe.setIntPersEmpresaSucursal(intIdEmpresa);
		libroDiarioDetalleDebe.setIntSucuIdSucursal(documentoSunat.getDetalleTotalGeneral().getIntSucuIdSucursal());
		libroDiarioDetalleDebe.setIntSudeIdSubSucursal(documentoSunat.getDetalleTotalGeneral().getIntSudeIdSubsucursal());		
			
		if(!esMonedaExtranjera){
			libroDiarioDetalleDebe.setBdDebeSoles(egresoDetalleInterfaz.getBdMonto().abs());
		}else{
			libroDiarioDetalleDebe.setBdDebeExtranjero(egresoDetalleInterfaz.getBdMonto());
			libroDiarioDetalleDebe.setBdDebeSoles(libroDiarioDetalleDebe.getBdDebeExtranjero().multiply(tipoCambioActual.getBdPromedio()));
		}
				
		return libroDiarioDetalleDebe;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalleDiferencial(EgresoDetalleInterfaz egresoDetalleInterfaz, ControlFondosFijos 
	controlFondosFijos) throws Exception{		
		DocumentoSunat documentoSunat = egresoDetalleInterfaz.getDocumentoSunat();
		Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
		TipoCambio tipoCambioActual = null;
		ModeloDetalle modeloDetalle = obtenerModeloDetalle(documentoSunat);
			
		Integer intMoneda = controlFondosFijos.getIntParaMoneda();
			
		boolean esMonedaExtranjera = Boolean.FALSE;
		LibroDiarioDetalle libroDiarioDetalleDiferencial = new LibroDiarioDetalle();
		libroDiarioDetalleDiferencial.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalleDiferencial.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());				
		libroDiarioDetalleDiferencial.setIntPersEmpresaCuenta(modeloDetalle.getId().getIntPersEmpresaCuenta());
		libroDiarioDetalleDiferencial.setIntContPeriodo(modeloDetalle.getId().getIntContPeriodoCuenta());
		libroDiarioDetalleDiferencial.setStrContNumeroCuenta(modeloDetalle.getId().getStrContNumeroCuenta());
		libroDiarioDetalleDiferencial.setIntPersPersona(controlFondosFijos.getSucursal().getIntPersPersonaPk());
		libroDiarioDetalleDiferencial.setIntParaDocumentoGeneral(controlFondosFijos.getId().getIntParaTipoFondoFijo());
		libroDiarioDetalleDiferencial.setStrSerieDocumento(null);
		libroDiarioDetalleDiferencial.setStrNumeroDocumento(null);
		libroDiarioDetalleDiferencial.setIntParaMonedaDocumento(intMoneda);
		
		if(!libroDiarioDetalleDiferencial.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
			libroDiarioDetalleDiferencial.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			esMonedaExtranjera = Boolean.TRUE;
		}
		
		libroDiarioDetalleDiferencial.setIntPersEmpresaSucursal(intIdEmpresa);
		libroDiarioDetalleDiferencial.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
		libroDiarioDetalleDiferencial.setIntSudeIdSubSucursal(controlFondosFijos.getIntSudeIdSubsucursal());		
		
		if(!esMonedaExtranjera){
			if(egresoDetalleInterfaz.getBdMonto().signum()>=0)
				libroDiarioDetalleDiferencial.setBdDebeSoles(egresoDetalleInterfaz.getBdMonto().abs());
			else
				libroDiarioDetalleDiferencial.setBdHaberSoles(egresoDetalleInterfaz.getBdMonto().abs());
		}else{
			if(egresoDetalleInterfaz.getBdMonto().signum()>=0){
				libroDiarioDetalleDiferencial.setBdDebeExtranjero(egresoDetalleInterfaz.getBdMonto().abs());
				libroDiarioDetalleDiferencial.setBdDebeSoles(libroDiarioDetalleDiferencial.getBdDebeExtranjero().multiply(tipoCambioActual.getBdPromedio()));
			}else{
				libroDiarioDetalleDiferencial.setBdHaberExtranjero(egresoDetalleInterfaz.getBdMonto());
				libroDiarioDetalleDiferencial.setBdHaberSoles(libroDiarioDetalleDiferencial.getBdHaberExtranjero().multiply(tipoCambioActual.getBdPromedio()));
			}
			
		}
				
		return libroDiarioDetalleDiferencial;
	}
	
	private LibroDiarioDetalle generarLibroDiarioDetalleHaber(ControlFondosFijos controlFondosFijos, BigDecimal bdMontoAGirar) 
	throws Exception{
		BancoFacadeLocal bancoFacade =  (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
		Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
		TipoCambio tipoCambioActual = null;
		Bancofondo bancoFondo = bancoFacade.obtenerBancoFondoParaIngresoDesdeControl(controlFondosFijos);	
		Integer intMoneda = controlFondosFijos.getIntParaMoneda();
			
		boolean esMonedaExtranjera;
		LibroDiarioDetalle libroDiarioDetalleHaber = new LibroDiarioDetalle();
		libroDiarioDetalleHaber.getId().setIntPersEmpresaLibro(intIdEmpresa);
		libroDiarioDetalleHaber.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());				
		libroDiarioDetalleHaber.setIntPersEmpresaCuenta(bancoFondo.getFondoDetalleUsar().getPlanCuenta().getId().getIntEmpresaCuentaPk());
		libroDiarioDetalleHaber.setIntContPeriodo(bancoFondo.getFondoDetalleUsar().getPlanCuenta().getId().getIntPeriodoCuenta());
		libroDiarioDetalleHaber.setStrContNumeroCuenta(bancoFondo.getFondoDetalleUsar().getPlanCuenta().getId().getStrNumeroCuenta());
		libroDiarioDetalleHaber.setIntPersPersona(controlFondosFijos.getSucursal().getIntPersPersonaPk());
		libroDiarioDetalleHaber.setIntParaDocumentoGeneral(controlFondosFijos.getId().getIntParaTipoFondoFijo());
		libroDiarioDetalleHaber.setStrSerieDocumento(null);
		libroDiarioDetalleHaber.setStrNumeroDocumento(null);
		libroDiarioDetalleHaber.setIntParaMonedaDocumento(intMoneda);
		
		if(!libroDiarioDetalleHaber.getIntParaMonedaDocumento().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
			tipoCambioActual = obtenerTipoCambioActual(intMoneda, intIdEmpresa);
			libroDiarioDetalleHaber.setIntTipoCambio(tipoCambioActual.getId().getIntParaClaseTipoCambio());
			esMonedaExtranjera = Boolean.TRUE;
		}else{
			esMonedaExtranjera = Boolean.FALSE;
		}
			
		libroDiarioDetalleHaber.setIntPersEmpresaSucursal(intIdEmpresa);
		libroDiarioDetalleHaber.setIntSucuIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
		libroDiarioDetalleHaber.setIntSudeIdSubSucursal(controlFondosFijos.getIntSudeIdSubsucursal());		
			
		if(!esMonedaExtranjera){
			libroDiarioDetalleHaber.setBdHaberSoles(bdMontoAGirar.abs());
		}else{
			libroDiarioDetalleHaber.setBdDebeExtranjero(bdMontoAGirar.abs());
			libroDiarioDetalleHaber.setBdDebeSoles(libroDiarioDetalleHaber.getBdDebeExtranjero().multiply(tipoCambioActual.getBdPromedio()));
		}
				
		return libroDiarioDetalleHaber;
	}	
	
	public Egreso generarEgresoDocumentoSunat(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, ControlFondosFijos controlFondosFijos, 
		Usuario usuario)throws BusinessException{
		Egreso egreso = new Egreso();
		try{
			
			Integer intIdEmpresa = controlFondosFijos.getId().getIntPersEmpresa();
			Persona personaGirar = listaEgresoDetalleInterfaz.get(0).getDocumentoSunat().getProveedor();
			BigDecimal bdMontoTotal = obtenerMontoTotalDocumentoSunat(listaEgresoDetalleInterfaz);
			String strGlosaEgreso = listaEgresoDetalleInterfaz.get(0).getDocumentoSunat().getStrGlosa();
			
			egreso.getId().setIntPersEmpresaEgreso(intIdEmpresa);
			egreso.setIntParaTipoOperacion(Constante.PARAM_T_TIPODEOPERACION_EGRESO);
			egreso.setIntParaFormaPago(Constante.PARAM_T_FORMAPAGOEGRESO_EFECTIVO);
			egreso.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			egreso.setIntItemPeriodoEgreso(MyUtil.obtenerPeriodoActual());
			egreso.setIntItemEgreso(null);
			egreso.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			egreso.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			egreso.setIntParaSubTipoOperacion(Constante.PARAM_T_TIPODESUBOPERACION_CANCELACION);
			egreso.setTsFechaProceso(MyUtil.obtenerFechaActual());
			egreso.setDtFechaEgreso(MyUtil.obtenerFechaActual());
			egreso.setIntParaTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			egreso.setIntItemPeriodoFondo(controlFondosFijos.getId().getIntItemPeriodoFondo());
			egreso.setIntItemFondoFijo(controlFondosFijos.getId().getIntItemFondoFijo());
			egreso.setIntItemBancoFondo(null);
			egreso.setIntItemBancoCuenta(null);
			egreso.setIntItemBancoCuentaCheque(null);
			egreso.setIntNumeroPlanilla(null);
			egreso.setIntNumeroCheque(null);
			egreso.setIntNumeroTransferencia(null);
			egreso.setTsFechaPagoDiferido(null);
			egreso.setIntPersEmpresaGirado(intIdEmpresa);
			egreso.setIntPersPersonaGirado(personaGirar.getIntIdPersona());
			egreso.setIntCuentaGirado(null);
			//jchavez 20.05.2014 cambio de tipo de dato
			egreso.setStrPersCuentaBancariaGirado(null);
			egreso.setIntPersEmpresaBeneficiario(null);
			egreso.setIntPersPersonaBeneficiario(null);
			egreso.setIntPersCuentaBancariaBeneficiario(null);
			egreso.setIntPersPersonaApoderado(null);
			egreso.setIntPersEmpresaApoderado(null);
			egreso.setBdMontoTotal(bdMontoTotal);
			egreso.setStrObservacion(strGlosaEgreso);
			egreso.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			egreso.setTsFechaRegistro(MyUtil.obtenerFechaActual());
			egreso.setIntPersEmpresaUsuario(intIdEmpresa);
			egreso.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			egreso.setIntPersEmpresaEgresoAnula(null);
			egreso.setIntPersPersonaEgresoAnula(null);
			egreso.setIntParaTipoApoderado(null);
			egreso.setIntItemArchivoApoderado(null);
			egreso.setIntItemHistoricoApoderado(null);			
			egreso.setIntParaTipoGiro(null);
			egreso.setIntItemArchivoGiro(null);
			egreso.setIntItemHistoricoGiro(null);
			
			for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
				DocumentoSunat documentoSunat = eDI.getDocumentoSunat();
				if(eDI.isEsDiferencial())
					egreso.getListaEgresoDetalle().add(generarEgresoDetalleDiferencial(eDI, controlFondosFijos, usuario));
				else
					egreso.getListaEgresoDetalle().add(generarEgresoDetalleDebe(documentoSunat, usuario));			
			}
			egreso.getListaEgresoDetalle().add(generarEgresoDetalleHaber(controlFondosFijos, usuario, bdMontoTotal));
			
			LibroDiario libroDiario = new LibroDiario();
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(MyUtil.obtenerPeriodoActual());
			libroDiario.setStrGlosa(strGlosaEgreso);
			libroDiario.setTsFechaRegistro(MyUtil.obtenerFechaActual());
			libroDiario.setTsFechaDocumento(MyUtil.obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);
			
			for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){				
				if(eDI.isEsDiferencial())
					libroDiario.getListaLibroDiarioDetalle().add(generarLibroDiarioDetalleDiferencial(eDI, controlFondosFijos));
				else
					libroDiario.getListaLibroDiarioDetalle().add(generarLibroDiarioDetalleDebe(eDI, controlFondosFijos));
			}
			LibroDiarioDetalle libroDiarioDetalleBanco = generarLibroDiarioDetalleHaber(controlFondosFijos, bdMontoTotal);
			libroDiario.getListaLibroDiarioDetalle().add(libroDiarioDetalleBanco);
			
			egreso.setLibroDiario(libroDiario);
			egreso.setControlFondosFijos(controlFondosFijos);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return egreso;
	}
	
	public Egreso grabarGiroDocumentoSunat(List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz, ControlFondosFijos 
		controlFondosFijos, Usuario usuario)throws BusinessException{
		try{
			EgresoFacadeLocal egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			
			Egreso egreso = generarEgresoDocumentoSunat(listaEgresoDetalleInterfaz, controlFondosFijos, usuario);
			
			log.info(egreso);
			for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
				log.info(egresoDetalle);
			}
			log.info(egreso.getLibroDiario());
			for(LibroDiarioDetalle libroDiarioDetalle : egreso.getLibroDiario().getListaLibroDiarioDetalle()){
				log.info(libroDiarioDetalle);
			}
			
			egreso = egresoFacade.grabarEgresoParaGiroPrestamo(egreso);
			
			for(EgresoDetalleInterfaz eDI : listaEgresoDetalleInterfaz){
				if(!eDI.isEsDiferencial()){
					DocumentoSunat documentoSunat = eDI.getDocumentoSunat();
					documentoSunat.setIntPersEmpresaEgreso(egreso.getId().getIntPersEmpresaEgreso());
					documentoSunat.setIntItemEgresoGeneral(egreso.getId().getIntItemEgresoGeneral());
					boDocumentoSunat.modificar(documentoSunat);
				}
			}
			
			return egreso;	
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
}