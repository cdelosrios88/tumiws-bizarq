package pe.com.tumi.tesoreria.egreso.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelComp;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.tesoreria.egreso.bo.MovilidadBO;
import pe.com.tumi.tesoreria.egreso.bo.MovilidadDetalleBO;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.domain.MovilidadDetalle;


public class MovilidadService {

	protected static Logger log = Logger.getLogger(MovilidadService.class);
	
	MovilidadBO boMovilidad = (MovilidadBO)TumiFactory.get(MovilidadBO.class);
	MovilidadDetalleBO boMovilidadDetalle = (MovilidadDetalleBO)TumiFactory.get(MovilidadDetalleBO.class);
	EgresoUtilService egresoUtilService = (EgresoUtilService)TumiFactory.get(EgresoUtilService.class);
	
	public Movilidad grabarMovilidad(Movilidad movilidad) throws BusinessException{
		try{
			LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			
			log.info(movilidad);
			//Grabacion de Movilidad
			movilidad = boMovilidad.grabar(movilidad);
//			movilidad.setListaMovilidadDetalle(new ArrayList<MovilidadDetalle>());
			for(MovilidadDetalle movilidadDetalle : movilidad.getListaMovilidadDetalle()){
				//Grabacion de Movilidad Detalle
				grabarMovilidadDetalle(movilidadDetalle, movilidad);
			}
			movilidad = generarAsientoContable(movilidad);
			
			LibroDiario libroDiario = libroDiarioFacade.grabarLibroDiario(movilidad.getLibroDiario());
			log.info("Se grabo en Asiento Contable...."+libroDiario);
			//Agregamos la llave del Libro Diario en la Planilla Movilidad
			movilidad.setIntPersEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
			movilidad.setIntContPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
			movilidad.setIntContCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
			
			movilidad = boMovilidad.modificar(movilidad);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return movilidad;		
	}
		
	public Movilidad generarAsientoContable(Movilidad movilidad) throws Exception{
		log.info("***************** GENERACION DEL ASIENTO CONTABLE *****************");
		Calendar cal = Calendar.getInstance();
		Integer anioActual = cal.get(Calendar.YEAR);
		Integer intIdEmpresa = movilidad.getId().getIntPersEmpresaMovilidad();
		List<LibroDiarioDetalle> lstLibroDiarioDetalleTemp = new ArrayList<LibroDiarioDetalle>(); 
//		PlanCuentaId pId = new PlanCuentaId();	
		List<LibroDiarioDetalle> lstLibroDiarioDetalleFinal = new ArrayList<LibroDiarioDetalle>();
		
		try {
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
//			PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			
			//1. Generación del Libro Diario (cabecera)
			LibroDiario libroDiario = new LibroDiario();
			libroDiario.setId(new LibroDiarioId());
			libroDiario.getId().setIntPersEmpresaLibro(intIdEmpresa);
			libroDiario.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
			libroDiario.setStrGlosa("PLANILLA MOVILIDAD");
			libroDiario.setTsFechaRegistro(obtenerFechaActual());
			libroDiario.setTsFechaDocumento(obtenerFechaActual());
			libroDiario.setIntPersEmpresaUsuario(intIdEmpresa);
			libroDiario.setIntPersPersonaUsuario(movilidad.getIntPersPersonaUsuario());
			libroDiario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			libroDiario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_RENDICION);	
			

			//2. Buscamos el Modelo Detalle Nivel 
			//2.1 Ordenamos la lista de movilidad detalle por Tipo Movilidad
			Collections.sort(movilidad.getListaMovilidadDetalle(), new Comparator<MovilidadDetalle>() {
	            public int compare(MovilidadDetalle o1, MovilidadDetalle o2) {
	            	MovilidadDetalle e1 = (MovilidadDetalle) o1;
	            	MovilidadDetalle e2 = (MovilidadDetalle) o2;
	                return e1.getIntParaTipoMovilidad().compareTo(e2.getIntParaTipoMovilidad());
	            }
	        });

			//3. Generación del Libro Diario Detalle (cabecera)
			for (MovilidadDetalle movDet : movilidad.getListaMovilidadDetalle()) {
				ModeloDetalleNivel modeloFiltro= new ModeloDetalleNivel();
				modeloFiltro.setId(new ModeloDetalleNivelId());
				
				modeloFiltro.getId().setIntEmpresaPk(intIdEmpresa);
				modeloFiltro.getId().setIntPersEmpresaCuenta(intIdEmpresa);
				modeloFiltro.getId().setIntContPeriodoCuenta(anioActual);
				modeloFiltro.setIntTipoModeloContable(Constante.PARAM_TIPOMODELO_PLANILLA_MOVILIDAD);
				modeloFiltro.setIntValor(movDet.getIntParaTipoMovilidad());
				movDet.setListaModeloDetalleNivelComp(modeloFacade.getModeloPlanillaMovilidad(modeloFiltro));
				
				if (movDet.getListaModeloDetalleNivelComp()!=null && !movDet.getListaModeloDetalleNivelComp().isEmpty()) {
					for (ModeloDetalleNivelComp o : movDet.getListaModeloDetalleNivelComp()) {
						LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
						libroDiarioDetalle.setId(new LibroDiarioDetalleId());
						libroDiarioDetalle.getId().setIntPersEmpresaLibro(movDet.getId().getIntPersEmpresaMovilidad());
						libroDiarioDetalle.getId().setIntContPeriodoLibro(obtenerPeriodoActual());
						
						libroDiarioDetalle.setIntPersPersona(movilidad.getIntPersPersonaUsuario());
						libroDiarioDetalle.setIntPersEmpresaCuenta(o.getIntEmpresaCuenta());
						libroDiarioDetalle.setIntContPeriodo(o.getIntPeriodoCuenta());
						libroDiarioDetalle.setStrContNumeroCuenta(o.getStrNumeroCuenta());
						libroDiarioDetalle.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD);
						
						//Descripción del Plan de Cuenta
//						pId.setIntEmpresaCuentaPk(o.getIntEmpresaCuenta());
//						pId.setStrNumeroCuenta(o.getStrNumeroCuenta());
//						pId.setIntPeriodoCuenta(o.getIntPeriodoCuenta());
//						libroDiarioDetalle.setStrComentario(planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().length()<20?planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion():planCuentaFacade.getPlanCuentaPorPk(pId).getStrDescripcion().substring(0, 20));
						libroDiarioDetalle.setStrComentario(o.getStrDescCuenta());
						
						libroDiarioDetalle.setStrNumeroDocumento(""+movilidad.getId().getIntItemMovilidad());
						libroDiarioDetalle.setIntPersEmpresaSucursal(movilidad.getId().getIntPersEmpresaMovilidad());
						libroDiarioDetalle.setIntSucuIdSucursal(movilidad.getIntSucuIdSucursal());
						libroDiarioDetalle.setIntSudeIdSubSucursal(movilidad.getIntSudeIdSubsucursal());
						//El Tipo de Moneda que se maneja es SOLES
						libroDiarioDetalle.setIntParaMonedaDocumento(Constante.PARAM_T_TIPOMONEDA_SOLES);
						libroDiarioDetalle.setIntTipoCambio(new Integer(1));
						if (o.getIntParamDebeHaber().equals(1)) libroDiarioDetalle.setBdDebeSoles(movDet.getBdMonto());
						if (o.getIntParamDebeHaber().equals(2)) libroDiarioDetalle.setBdHaberSoles(movDet.getBdMonto());
						lstLibroDiarioDetalleTemp.add(libroDiarioDetalle);
					}					
				}else{
					throw new Exception("Error en la generacion del Asiento Contable");
				}
			}
			
			//Procesamos el Libro Diario Detalle con Numero de Cuenta (agrupacion)
			if (lstLibroDiarioDetalleTemp!=null && !lstLibroDiarioDetalleTemp.isEmpty()) {
				//Ordenamos por Numero de Cuenta
				Collections.sort(lstLibroDiarioDetalleTemp, new Comparator<LibroDiarioDetalle>() {
		            public int compare(LibroDiarioDetalle o1, LibroDiarioDetalle o2) {
		            	LibroDiarioDetalle e1 = (LibroDiarioDetalle) o1;
		            	LibroDiarioDetalle e2 = (LibroDiarioDetalle) o2;
		                return e1.getStrContNumeroCuenta().compareTo(e2.getStrContNumeroCuenta());
		            }
		        });

				Integer cont = 1;
				String strContNumeroCuenta = "";
				Integer intTamanioLista = lstLibroDiarioDetalleTemp.size();
				LibroDiarioDetalle libroDiarioDetalleAnterior = null;
				for (LibroDiarioDetalle o : lstLibroDiarioDetalleTemp) {
					if (cont!=1) {
						if (o.getStrContNumeroCuenta().compareTo(strContNumeroCuenta)!=0) {
							lstLibroDiarioDetalleFinal.add(libroDiarioDetalleAnterior);
							libroDiarioDetalleAnterior = o;
							strContNumeroCuenta = o.getStrContNumeroCuenta();
							//Verificando si es el ultimo registro...
							if (cont.equals(intTamanioLista)) lstLibroDiarioDetalleFinal.add(o);
							else cont++;
						}else{
							if (libroDiarioDetalleAnterior.getBdDebeSoles()!=null) libroDiarioDetalleAnterior.setBdDebeSoles(libroDiarioDetalleAnterior.getBdDebeSoles().add(o.getBdDebeSoles()));
							if (libroDiarioDetalleAnterior.getBdHaberSoles()!=null) libroDiarioDetalleAnterior.setBdHaberSoles(libroDiarioDetalleAnterior.getBdHaberSoles().add(o.getBdHaberSoles()));
							//Verificando si es el ultimo registro...
							if (cont.equals(intTamanioLista)) lstLibroDiarioDetalleFinal.add(libroDiarioDetalleAnterior);
							else cont++;
						}							
					}else{
						libroDiarioDetalleAnterior = o;
						strContNumeroCuenta = o.getStrContNumeroCuenta();
						cont++;
					}
				}
			}
			movilidad.setLibroDiario(libroDiario);
			movilidad.getLibroDiario().setListaLibroDiarioDetalle(lstLibroDiarioDetalleFinal);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return movilidad;
	}

	private Integer	obtenerPeriodoActual(){
		String strPeriodo = "";
		Calendar cal = Calendar.getInstance();
		int año = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH);
		mes = mes + 1; 
		if(mes<10){
			strPeriodo = año + "0" + mes;
		}else{
			strPeriodo  = año + "" + mes;
		}		
		return Integer.parseInt(strPeriodo);		
	}
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	public Movilidad anularMovilidad(Movilidad movilidad) throws BusinessException{
		try{
			movilidad.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);

			log.info(movilidad);
			movilidad = boMovilidad.modificar(movilidad);

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return movilidad;
	}

	public Movilidad modificarMovilidad(Movilidad movilidad) throws BusinessException{
		try{
			log.info("Movilidad a modificar: "+movilidad);
			List<MovilidadDetalle> listaMovilidadDetalleIU = new ArrayList<MovilidadDetalle>();
			//Obtenemos los detalle de la movilidad a modificar.
			List<MovilidadDetalle> listaMovilidadDetalleBD = boMovilidadDetalle.getPorMovilidad(movilidad);
			//Modificamos Movilidad (Campos intPersPersonaEdita y intPersEmpresaEdita)
			movilidad = boMovilidad.modificar(movilidad);
			//Recorremos la lista de los nuevos detalles generados. 
			for(MovilidadDetalle movilidadDetalle : movilidad.getListaMovilidadDetalle()){
				if(movilidadDetalle.getId().getIntItemMovilidadDetalle()==null){ //Si el detalle ingresado es nuevo, grabar
					grabarMovilidadDetalle(movilidadDetalle, movilidad);
				}else{ //Si es un detalle existente, se agrega la la lista
					listaMovilidadDetalleIU.add(movilidadDetalle);
				}
			}
			
			boolean seEncuentraEnIU = Boolean.FALSE;
			//Recorremos la lista de los detalles ya registrados 
			for(MovilidadDetalle movilidadDetalleBD : listaMovilidadDetalleBD){
				seEncuentraEnIU = Boolean.FALSE;
				for(MovilidadDetalle movilidadDetalleIU : listaMovilidadDetalleIU){
					//Comparo los registros de la BD con los registros que estan en la interfaz,
					if(movilidadDetalleBD.getId().getIntItemMovilidadDetalle().equals(movilidadDetalleIU.getId().getIntItemMovilidadDetalle())){						
						seEncuentraEnIU = Boolean.TRUE;
						break;
					}
				}//si no se encuentra, quiere decir que ese registro ha sido anulado, por ende se anula de la BD
				if(!seEncuentraEnIU){
					anularMovilidadDetalle(movilidadDetalleBD,movilidad);
				}
			}
			//Generamos los registros de Libro Diario y Libro Diadio detalle
			movilidad = generarAsientoContable(movilidad);			
			modificarAsientoContable(movilidad);
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return movilidad;
	}
	
	//Modificar Libro Diario y Libro Diario Detalle
	public Movilidad modificarAsientoContable(Movilidad movilidad) throws Exception{
		LibroDiario libroDiario = new LibroDiario();
		libroDiario.setId(new LibroDiarioId());
		List<LibroDiarioDetalle> lstLibroDiarioDetalleBD = new ArrayList<LibroDiarioDetalle>();
//		List<LibroDiarioDetalle> lstLibroDiarioDetalleIU = new ArrayList<LibroDiarioDetalle>();
		try {
			LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			
			libroDiario.getId().setIntPersEmpresaLibro(movilidad.getIntPersEmpresaLibro());
			libroDiario.getId().setIntContPeriodoLibro(movilidad.getIntContPeriodoLibro());
			libroDiario.getId().setIntContCodigoLibro(movilidad.getIntContCodigoLibro());
			//Almaceno los registros encontrados en la BD en la lista "lstLibroDiarioDetalleOld"
			lstLibroDiarioDetalleBD.addAll(libroDiarioFacade.getListaLibroDiarioDetallePorLibroDiario(libroDiario));

			
			//Comparacion entre los registros existentes en la BD y los ingresados (caso registros ingresados existen en BD)
			if (movilidad.getLibroDiario().getListaLibroDiarioDetalle()!=null && !movilidad.getLibroDiario().getListaLibroDiarioDetalle().isEmpty()) {
				boolean seEncuentraEnIU = Boolean.FALSE;
				for (LibroDiarioDetalle libroDiarioDetalleBD : lstLibroDiarioDetalleBD) {
					seEncuentraEnIU = Boolean.FALSE;
					for (LibroDiarioDetalle libroDiarioDetalleIU : movilidad.getLibroDiario().getListaLibroDiarioDetalle()) {
						if(libroDiarioDetalleBD.getStrContNumeroCuenta().equals(libroDiarioDetalleIU.getStrContNumeroCuenta())){						
							seEncuentraEnIU = Boolean.TRUE;
							if (libroDiarioDetalleBD.getBdDebeSoles()!=null) libroDiarioDetalleBD.setBdDebeSoles(libroDiarioDetalleIU.getBdDebeSoles());
							if (libroDiarioDetalleBD.getBdHaberSoles()!=null) libroDiarioDetalleBD.setBdHaberSoles(libroDiarioDetalleIU.getBdHaberSoles());
							libroDiarioFacade.modificarLibroDiarioDetalle(libroDiarioDetalleBD);
							break;
						}
					}
					if(!seEncuentraEnIU){
						if (libroDiarioDetalleBD.getBdDebeSoles()!=null) libroDiarioDetalleBD.setBdDebeSoles(BigDecimal.ZERO);
						if (libroDiarioDetalleBD.getBdHaberSoles()!=null) libroDiarioDetalleBD.setBdHaberSoles(BigDecimal.ZERO);
						libroDiarioFacade.modificarLibroDiarioDetalle(libroDiarioDetalleBD);
					}
				}
			}
			
			//Recorremos la lista de los detalles generados y grabamos los que sean nuevos.
			Boolean seEncuentraEnBD = false;
			Integer intItemDetalle = 0;
			//Calculamos el ultuimo item Libro Ingresado
			Integer intItemDetalleUltimo = 0; 
			for (LibroDiarioDetalle libroDiarioDetalleBD : lstLibroDiarioDetalleBD) {
				if(libroDiarioDetalleBD.getId().getIntContItemLibro().compareTo(intItemDetalle)==1){
					intItemDetalleUltimo = libroDiarioDetalleBD.getId().getIntContItemLibro();
				}
			}
			//Comparacion entre los registros ingresados y los existentes en la BD (caso registros ingresados sean nuevos)
			for(LibroDiarioDetalle libroDiarioDetalleIU : movilidad.getLibroDiario().getListaLibroDiarioDetalle()){
				seEncuentraEnBD = Boolean.FALSE;
				for (LibroDiarioDetalle libroDiarioDetalleBD : lstLibroDiarioDetalleBD) {
					if(libroDiarioDetalleBD.getStrContNumeroCuenta().equals(libroDiarioDetalleIU.getStrContNumeroCuenta())){ //Si el detalle ya existe...  
						seEncuentraEnBD = true;
//						lstLibroDiarioDetalleIU.add(libroDiarioDetalleBD);
						break;
					}
				}
				if (!seEncuentraEnBD) {
					//Si el detalle ingresado es nuevo, grabar...
					intItemDetalle = intItemDetalleUltimo + 1;
					intItemDetalleUltimo = intItemDetalle;
					libroDiarioDetalleIU.getId().setIntContCodigoLibro(movilidad.getIntContCodigoLibro());
					libroDiarioDetalleIU.getId().setIntContItemLibro(intItemDetalle);
					libroDiarioFacade.grabarLibroDiarioDetalle(libroDiarioDetalleIU);
				}
			}		
			

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return movilidad;
	}
//	//Se elimina fisicamente el registro de Libro Diario Detalle
//	private LibroDiarioDetalle anularLibroDiarioDetalle(LibroDiarioDetalle libroDiarioDetalle) throws BusinessException{
//		try {
//			LibroDiarioFacadeRemote libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
//			libroDiarioFacade.eliminar(libroDiarioDetalle.getId());
//			
//		}catch(BusinessException e){
//			throw e;
//		}catch(Exception e){
//			throw new BusinessException(e);
//		}
//		return libroDiarioDetalle;
//	}
	private MovilidadDetalle grabarMovilidadDetalle(MovilidadDetalle movilidadDetalle, Movilidad movilidad) throws BusinessException{
		movilidadDetalle.getId().setIntPersEmpresaMovilidad(movilidad.getId().getIntPersEmpresaMovilidad());
		movilidadDetalle.getId().setIntItemMovilidad(movilidad.getId().getIntItemMovilidad());
		
		log.info(movilidadDetalle);
		boMovilidadDetalle.grabar(movilidadDetalle);
		
		return movilidadDetalle;
	}
	
	private MovilidadDetalle anularMovilidadDetalle(MovilidadDetalle movilidadDetalle, Movilidad movilidad) throws BusinessException{
		movilidadDetalle.setIntPersPersonaEliminacion(movilidad.getIntPersPersonaEdita());
		movilidadDetalle.setIntPersEmpresaEliminacion(movilidad.getIntPersEmpresaEdita());
		movilidadDetalle.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
		
		movilidadDetalle.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
		log.info(movilidadDetalle);
		boMovilidadDetalle.modificar(movilidadDetalle);
		
		return movilidadDetalle;
	}
	
	public List<Movilidad> buscarMovilidad(Movilidad movilidadFiltro) throws BusinessException{
		List<Movilidad> listaMovilidad = new ArrayList<Movilidad>();
		List<Movilidad> listaMovilidadTemp = null;
		try{
//			List<Movilidad> listaMovilidadTemp = new ArrayList<Movilidad>();
//			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
//			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
//			Persona persona;
			Sucursal sucursal = new Sucursal();
			sucursal.setId(new SucursalId());
			
			log.info(movilidadFiltro);			
//			listaMovilidad = boMovilidad.getPorBusqueda(movilidadFiltro);
			if (movilidadFiltro.getIntMes().equals(-1)) {
				for (int i = 0; i <= 12; i++) {
					movilidadFiltro.setIntPeriodo(Integer.parseInt(movilidadFiltro.getIntAño()+(i<10?"0"+i:""+i)));
					listaMovilidadTemp = boMovilidad.getPorFiltroBusqueda(movilidadFiltro);
					for (Movilidad movilidad : listaMovilidadTemp) {
						listaMovilidad.add(movilidad);
					}
				}
			}
//			listaMovilidad = boMovilidad.getPorFiltroBusqueda(movilidadFiltro);
			
			//Comentado 16.12.2013 JCHAVEZ la búsqueda se realiza por base de datos
//			//SI EXISTE UN TEXTO PARA FILTRAR
//			if(movilidadFiltro.getStrTextoFiltro()!=null && !movilidadFiltro.getStrTextoFiltro().isEmpty()){
//				//FILTRAMOS POR USUARIO O POR SUCURSAL
//				if(movilidadFiltro.getIntTipoBusqueda().equals(Constante.FILTROMOVIMIENTO_USUARIO)){
//					for(Movilidad movilidad : listaMovilidad){
//						persona = personaFacade.getPersonaNaturalPorIdPersona(movilidad.getIntPersPersonaUsuario());
//						if(persona.getNatural().getStrNombres().toUpperCase().contains(movilidadFiltro.getStrTextoFiltro().toUpperCase()) || 
//							persona.getNatural().getStrApellidoPaterno().toUpperCase().contains(movilidadFiltro.getStrTextoFiltro().toUpperCase()) ||
//							persona.getNatural().getStrApellidoMaterno().toUpperCase().contains(movilidadFiltro.getStrTextoFiltro().toUpperCase())){
//							
//							movilidad.setPersona(persona);
//							
//							sucursal.getId().setIntIdSucursal(movilidad.getIntSucuIdSucursal());
//							movilidad.setSucursal(empresaFacade.getSucursalPorPK(sucursal));
//							listaMovilidadTemp.add(movilidad);
//						}
//					}
//					
//				}else if(movilidadFiltro.getIntTipoBusqueda().equals(Constante.FILTROMOVIMIENTO_SUCURSAL)){
//					for(Movilidad movilidad : listaMovilidad){
//						sucursal.getId().setIntIdSucursal(movilidad.getIntSucuIdSucursal());
//						sucursal = empresaFacade.getSucursalPorPK(sucursal);
//						if(sucursal.getJuridica().getStrSiglas().toUpperCase().contains(movilidadFiltro.getStrTextoFiltro().toUpperCase())){
//							
//							movilidad.setPersona(personaFacade.getPersonaNaturalPorIdPersona(movilidad.getIntPersPersonaUsuario()));
//							
//							movilidad.setSucursal(sucursal);
//							listaMovilidadTemp.add(movilidad);
//						}
//					}
//					
//				}
//				
//				listaMovilidad = listaMovilidadTemp;
//			}else{
//				for(Movilidad movilidad : listaMovilidad){
//					persona = personaFacade.getPersonaNaturalPorIdPersona(movilidad.getIntPersPersonaUsuario());
//					movilidad.setPersona(persona);
//					
//					sucursal.getId().setIntIdSucursal(movilidad.getIntSucuIdSucursal());
//					movilidad.setSucursal(empresaFacade.getSucursalPorPK(sucursal));
//				}
//			}
			
			//OBTENEMOS EL MONTO ACUMULADO DE CADA MOVILIDAD
			for(Movilidad movilidad : listaMovilidad){
				obtenerMontoAcumulado(movilidad);
				if(movilidad.getIntItemEgresoGeneral()!=null){
					movilidad.setEgreso(egresoUtilService.obtenerEgresoPorMovilidad(movilidad));
				}
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaMovilidad;
	}
	
	private Movilidad obtenerMontoAcumulado(Movilidad movilidad) throws Exception{
		try{
			BigDecimal bdMontoAcumulado;
			List<MovilidadDetalle> listaMovilidadDetalle;
			
			//añadimos el detalle. Si la cabecera es activa solo se añaden los detalles activos.
			listaMovilidadDetalle = boMovilidadDetalle.getPorMovilidad(movilidad);
			/*if(movilidad.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				for(MovilidadDetalle movilidadDetalle : listaMovilidadDetalle){
					if(movilidadDetalle.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
						movilidad.getListaMovilidadDetalle().add(movilidadDetalle);
					}
				}
			}else{
				movilidad.setListaMovilidadDetalle(listaMovilidadDetalle);
			}*/
			movilidad.setListaMovilidadDetalle(listaMovilidadDetalle);
			bdMontoAcumulado = new BigDecimal(0);
			for(MovilidadDetalle movilidadDetalle : movilidad.getListaMovilidadDetalle()){
				bdMontoAcumulado = bdMontoAcumulado.add(movilidadDetalle.getBdMonto());
			}
			movilidad.setBdMontoAcumulado(bdMontoAcumulado);
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return movilidad;
	}
	
	public List<MovilidadDetalle> getListaMovilidadDetalleValidar(Movilidad movilidad, MovilidadDetalle movilidadDetalle) throws BusinessException{
		List<MovilidadDetalle> listaMovilidadDetalle = new ArrayList<MovilidadDetalle>();
		try{
			List<Movilidad> listaMovilidad = boMovilidad.getPorPersona(movilidad);
			
			for(Movilidad movilidadTemp : listaMovilidad){
				movilidadTemp.setListaMovilidadDetalle(boMovilidadDetalle.getPorMovilidad(movilidadTemp));
				for(MovilidadDetalle movilidadDetalleTemp : movilidadTemp.getListaMovilidadDetalle()){
					if(movilidadDetalleTemp.getDtFechaMovilidad().equals(movilidadDetalle.getDtFechaMovilidad())){
						listaMovilidadDetalle.add(movilidadDetalleTemp);
					}
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaMovilidadDetalle;
	}

	
	public List<Movilidad> buscarMovilidadPorIdPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		List<Movilidad> listaMovilidad = new ArrayList<Movilidad>();
		try{
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			Movilidad movilidadFiltro = new Movilidad();
			movilidadFiltro.setIntPersEmpresaUsuario(intIdEmpresa);
			movilidadFiltro.setIntPersPersonaUsuario(intIdPersona);
			listaMovilidad = boMovilidad.getPorPersona(movilidadFiltro);
			
			//OBTENEMOS EL MONTO ACUMULADO DE CADA MOVILIDAD
			for(Movilidad movilidad : listaMovilidad){
				obtenerMontoAcumulado(movilidad);
				Sucursal sucursal = new Sucursal();
				sucursal.setId(new SucursalId());
				sucursal.getId().setIntIdSucursal(movilidad.getIntSucuIdSucursal());
				sucursal = empresaFacade.getSucursalPorPK(sucursal);
				movilidad.setSucursal(sucursal);
				movilidad.setSubsucursal(empresaFacade.getSubSucursalPorIdSubSucursal(movilidad.getIntSudeIdSubsucursal()));
				
				log.info("sucu:"+movilidad.getSucursal());
				log.info("subs:"+movilidad.getSubsucursal());
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaMovilidad;
	}
	
	public List<Movilidad> buscarMovilidadParaGirar(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		List<Movilidad> listaMovilidad = new ArrayList<Movilidad>();
		try{
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			Movilidad movilidadFiltro = new Movilidad();
			movilidadFiltro.setIntPersEmpresaUsuario(intIdEmpresa);
			movilidadFiltro.setIntPersPersonaUsuario(intIdPersona);
			List<Movilidad> listaMovilidadTemp = boMovilidad.getPorPersona(movilidadFiltro);
			
			//OBTENEMOS EL MONTO ACUMULADO DE CADA MOVILIDAD
			for(Movilidad movilidad : listaMovilidadTemp){
				if(movilidad.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
				&& movilidad.getIntParaEstadoPago().equals(Constante.PARAM_T_ESTADOPAGO_PENDIENTE)){
					obtenerMontoAcumulado(movilidad);
					Sucursal sucursal = new Sucursal();
					sucursal.setId(new SucursalId());
					sucursal.getId().setIntIdSucursal(movilidad.getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);
					movilidad.setSucursal(sucursal);
					movilidad.setSubsucursal(empresaFacade.getSubSucursalPorIdSubSucursal(movilidad.getIntSudeIdSubsucursal()));
					
					listaMovilidad.add(movilidad);
				}				
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaMovilidad;
	}
	
	public List<Movilidad> obtenerListaMovilidadPorEgreso(Egreso egreso) throws BusinessException{
		List<Movilidad> listaMovilidad = new ArrayList<Movilidad>();
		try{
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			List<Movilidad> listaMovilidadTemp = boMovilidad.getPorEgreso(egreso);
			//OBTENEMOS EL MONTO ACUMULADO DE CADA MOVILIDAD
			for(Movilidad movilidad : listaMovilidadTemp){
				if(movilidad.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
					obtenerMontoAcumulado(movilidad);
					Sucursal sucursal = new Sucursal();
					sucursal.setId(new SucursalId());
					sucursal.getId().setIntIdSucursal(movilidad.getIntSucuIdSucursal());
					sucursal = empresaFacade.getSucursalPorPK(sucursal);
					movilidad.setSucursal(sucursal);
					movilidad.setSubsucursal(empresaFacade.getSubSucursalPorIdSubSucursal(movilidad.getIntSudeIdSubsucursal()));
					
					listaMovilidad.add(movilidad);
				}				
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaMovilidad;
	}
}