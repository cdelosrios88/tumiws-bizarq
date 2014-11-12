package pe.com.tumi.contabilidad.operaciones.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.bo.LibroDiarioBO;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.service.LibroDiarioService;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.contabilidad.operaciones.bo.HojaManualBO;
import pe.com.tumi.contabilidad.operaciones.bo.HojaManualDetalleBO;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManual;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalle;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;

public class HojaManualService {
	HojaManualBO boHojaManual = (HojaManualBO)TumiFactory.get(HojaManualBO.class);
	LibroDiarioBO boLibroDiario = (LibroDiarioBO)TumiFactory.get(LibroDiarioBO.class);
	HojaManualDetalleBO boHojaManualDetalle = (HojaManualDetalleBO)TumiFactory.get(HojaManualDetalleBO.class);
	LibroDiarioService libroDiarioService = (LibroDiarioService)TumiFactory.get(LibroDiarioService.class);
	
	
	
	public HojaManual grabarHojaManual(HojaManual o) throws Exception{
		
		//Para grabar HojaManual primero debe guardarse en paralelo en LibroDiario
		LibroDiario diario = o.getLibroDiario();
		diario.setListaLibroDiarioDetalle(new ArrayList<LibroDiarioDetalle>());
		System.out.println("diario.id.intPersEmpresaLibro: "+diario.getId().getIntPersEmpresaLibro());//diario.id.intPersEmpresaLibro desde el Controller
		diario.getId().setIntContPeriodoLibro(o.getId().getIntContPeriodoHoja());
		//diario.id.intContCodigoHoja autogenerado;
		
		diario.setTsFechaRegistro(new Timestamp((new Date()).getTime())); //la fecha actual
		diario.setTsFechaDocumento(o.getTsHomaFechaRegistro());
		diario.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		diario.setStrGlosa(o.getStrHomaGlosa());
		/*Agregado por Rodolfo Villarreal 15/07/2014*/
		diario.getId().setIntPersEmpresaLibro(o.getId().getIntPersEmpresaHojaPk());
		diario.getId().setIntContPeriodoLibro(o.getId().getIntContPeriodoHoja());
		diario.getId().setIntContCodigoLibro(o.getId().getIntContCodigoHoja());
		diario.setTsFechaDocumento(o.getTsHomaFechaRegistro());
		diario.setIntPersEmpresaUsuario(o.getId().getIntPersEmpresaHojaPk());
		diario.setIntPersPersonaUsuario(o.getListHojaManualDetalle().get(0).getPersona().getIntIdPersona());
		diario.setIntParaTipoDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_NOTACONTABLE);
		diario.setTsfFechaEliminacion(null);
		/*Construyendo libro*/
		for (HojaManualDetalle x : o.getListHojaManualDetalle()) {
			LibroDiarioDetalle haber = new LibroDiarioDetalle();
			haber.setId(new LibroDiarioDetalleId());
			haber.getId().setIntPersEmpresaLibro(o.getId().getIntPersEmpresaHojaPk());
			haber.getId().setIntContPeriodoLibro(o.getId().getIntContPeriodoHoja());
			haber.getId().setIntContCodigoLibro(o.getId().getIntContCodigoHoja());
			haber.setIntPersEmpresaCuenta(o.getId().getIntPersEmpresaHojaPk());
			haber.setIntContPeriodo(x.getPlanCuenta().getId().getIntPeriodoCuenta());
			haber.setStrContNumeroCuenta(x.getPlanCuenta().getId().getStrNumeroCuenta());
			haber.setIntPersPersona(x.getPersona().getIntIdPersona());
			haber.setIntParaDocumentoGeneral(x.getIntParaDocumentoGeneralCod());
			haber.setStrSerieDocumento(x.getStrHmdeSerieDocumento());
			haber.setStrNumeroDocumento(x.getStrHmdeNumeroDocumento());
			haber.setIntSucuIdSucursal(x.getIntSucuIdSucursalPk());
			haber.setIntSudeIdSubSucursal(x.getIntSudeIdSubsucursalPk());
			haber.setIntParaMonedaDocumento(x.getIntParaMonedaDocumento());
			haber.setBdDebeSoles(x.getBdHmdeDebeSoles());
			haber.setBdDebeExtranjero(x.getBdHmdeDebeExtranjero());
			haber.setBdHaberExtranjero(x.getBdHmdeHaberExtranjero());
			haber.setBdHaberSoles(x.getBdHmdeHaberSoles());
			haber.setStrComentario(Constante.PARAM_T_DOCUMENTOGENERAL_NOTACONTABLEDESCRIPCION);
			haber.setIntPersEmpresaSucursal(diario.getId().getIntPersEmpresaLibro());
			diario.getListaLibroDiarioDetalle().add(haber);
		}
		/*fin Libro*/
		
		//Se graba en la base de datos
		diario = libroDiarioService.grabarLibroDiario(diario);
		
		//Se guarda en LibroDiarioDetalle según lo que se tiene en HojaManualDetalle
		LibroDiarioDetalle ldde = new LibroDiarioDetalle();
		Integer intItemDetalle = new Integer(0);
		for(HojaManualDetalle hmde : o.getListHojaManualDetalle()){
			intItemDetalle = intItemDetalle + 1;
			//ldde.id se setea en el metodo libroDiarioService.dynamicSaveLibroDiarioDet()
			ldde.setIntPersEmpresaCuenta(hmde.getPlanCuenta().getId().getIntEmpresaCuentaPk());
			ldde.setIntContPeriodo(hmde.getPlanCuenta().getId().getIntPeriodoCuenta());
			ldde.setStrNumeroDocumento(hmde.getPlanCuenta().getId().getStrNumeroCuenta());
			ldde.setIntPersPersona(hmde.getPersona().getIntIdPersona());
			ldde.setIntParaDocumentoGeneral(hmde.getIntParaDocumentoGeneralCod());
			ldde.setStrSerieDocumento(hmde.getStrHmdeSerieDocumento());
			ldde.setStrNumeroDocumento(hmde.getStrHmdeNumeroDocumento());
			ldde.setIntPersEmpresaSucursal(diario.getId().getIntPersEmpresaLibro());
			ldde.setIntSucuIdSucursal(hmde.getIntSucuIdSucursalPk());
			ldde.setIntSudeIdSubSucursal(hmde.getIntSudeIdSubsucursalPk());
			ldde.setIntParaMonedaDocumento(hmde.getIntParaMonedaDocumento());
			ldde.setIntTipoCambio(hmde.getIntHmdeTipoCambio()); //¿Parametros?
			ldde.setBdDebeSoles(hmde.getBdHmdeDebeSoles());
			ldde.setBdHaberSoles(hmde.getBdHmdeHaberSoles());
			ldde.setBdDebeExtranjero(hmde.getBdHmdeDebeExtranjero());
			ldde.setBdHaberExtranjero(hmde.getBdHmdeHaberExtranjero());
			//ldde.strComentario;
			libroDiarioService.dynamicSaveLibroDiarioDet(ldde, diario.getId(),intItemDetalle);
		}
		
		//Guardar HojaManual
		HojaManual domain = null;
		//o.id.intPersEmpresaHojaPk viende del Controller
		//o.id.intContPeriodoHoja viene del Controller
		//o.id.intContCodigoHoja es autogenerado
		//o.intParaDocumentoGeneralCod ¿PARAMETROS?
		//o.strHomaGlosa viene del Controller
		java.util.Date date= new java.util.Date();
		o.setTsHomaFechaRegistro(new Timestamp(date.getTime()));
		o.setIntPersEmpresaLibroPk(diario.getId().getIntPersEmpresaLibro());
		o.setIntContPeriodoLibro(diario.getId().getIntContPeriodoLibro());
		o.setIntContCodigoLibro(diario.getId().getIntContCodigoLibro());
		o.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_NOTACONTABLE);
		//Grabar en la base de datos
		domain = boHojaManual.grabarHojaManual(o);
		
		if(domain!=null){
			for(HojaManualDetalle hmde : o.getListHojaManualDetalle()){
				//hmde.id se setea en el metodo this.dynamicSaveHojaManualDet()
				hmde.setIntPersEmpresaCuentaPk(hmde.getPlanCuenta().getId().getIntEmpresaCuentaPk());
				hmde.setIntContPeriodoCuenta(hmde.getPlanCuenta().getId().getIntPeriodoCuenta());
				hmde.setStrContNumeroCuenta(hmde.getPlanCuenta().getId().getStrNumeroCuenta());
				hmde.setIntPersPersonaPk(hmde.getPersona().getIntIdPersona());
				//hmde.intHmdeTipoCambio ¿PARAMETROS?
				hmde.setIntPersEmpresaSucursalPk(domain.getId().getIntPersEmpresaHojaPk());
				this.dynamicSaveHojaManualDet(hmde, domain.getId());
			}
		}
		return domain;
	}
	
	public HojaManual modificarHojaManual(HojaManual o) throws BusinessException{
		HojaManual domain = null;
		domain = boHojaManual.modificarHojaManual(o);
		
		if(domain!=null){
			for(HojaManualDetalle hojaManualDetalle : o.getListHojaManualDetalle()){
				dynamicSaveHojaManualDet(hojaManualDetalle, domain.getId());
			}
		}
		return domain;
	}
	
	public HojaManualDetalle dynamicSaveHojaManualDet(HojaManualDetalle o, HojaManualId id) throws BusinessException{
		HojaManualDetalle domain = null;
		
		//en caso cumpla con la condición se elimina físicamente de la base de datos 
		if(o.getIsDeleted()!=null && o.getIsDeleted()){
			domain = boHojaManualDetalle.getHojaManualDetallePorPk(o.getId());
			if(domain!=null){
				domain = boHojaManualDetalle.eliminarHojaManualDetalle(domain.getId());
				return domain;
			}
		}
		
		//para grabar o actualizar
		domain = boHojaManualDetalle.getHojaManualDetallePorPk(o.getId());
		if(domain!=null){
			domain = boHojaManualDetalle.modificarHojaManualDetalle(o);
		}else{
			o.getId().setIntPersEmpresaHojaPk(id.getIntPersEmpresaHojaPk());
			o.getId().setIntContPeriodoHoja(id.getIntContPeriodoHoja());
			o.getId().setIntContCodigoHoja(id.getIntContCodigoHoja());
			domain = boHojaManualDetalle.grabarHojaManualDetalle(o);
		}
		
		return domain;
	}
	
	public List<HojaManual> getListHojaManualBusqueda(HojaManualDetalle o) throws BusinessException, EJBFactoryException{
		List<HojaManual> lista = null;
		List<HojaManualDetalle> listHojaManualDetalle = null;
		List<HojaManual> listaAux = null;
		
		lista = boHojaManual.getListHojaManualBusqueda(o.getHojaManual());
		
		if(lista!=null && lista.size()>0){
			listaAux = new ArrayList<HojaManual>();
			for(HojaManual hojaManual : lista){
				HojaManualDetalle hmdeBusq = new HojaManualDetalle();
				hmdeBusq.getId().setIntPersEmpresaHojaPk(hojaManual.getId().getIntPersEmpresaHojaPk());
				hmdeBusq.getId().setIntContPeriodoHoja(hojaManual.getId().getIntContPeriodoHoja());
				hmdeBusq.getId().setIntContCodigoHoja(hojaManual.getId().getIntContCodigoHoja());
				hmdeBusq.setStrContNumeroCuenta(o.getStrContNumeroCuenta());
				listHojaManualDetalle = boHojaManualDetalle.getListHojaManualDetBusq(hmdeBusq);
				if(listHojaManualDetalle!=null){
					BigDecimal bdSuma = new BigDecimal(0);
					for(HojaManualDetalle hmde : listHojaManualDetalle){
						if(hmde.getBdHmdeDebeSoles()!=null){
							bdSuma = bdSuma.add(hmde.getBdHmdeDebeSoles());
						}
						//Setear los datos de la Persona
						PersonaFacadeRemote personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
						Persona persona = personaFacade.getPersonaNaturalPorIdPersona(hmde.getIntPersPersonaPk());
						hmde.setPersona(persona);
						System.out.println("persona: "+persona);
						if(persona!=null){
							System.out.println("persona.natural.strNombres: "+persona.getNatural().getStrNombres());
						}
						//Setear el Plan de Cuenta
						PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
						PlanCuentaId planCuentaId = new PlanCuentaId();
						planCuentaId.setIntEmpresaCuentaPk(hmde.getIntPersEmpresaCuentaPk());
						planCuentaId.setIntPeriodoCuenta(hmde.getIntContPeriodoCuenta());
						planCuentaId.setStrNumeroCuenta(hmde.getStrContNumeroCuenta());
						PlanCuenta planCuenta = planCuentaFacade.getPlanCuentaPorPk(planCuentaId);
						hmde.setPlanCuenta(planCuenta);
						System.out.println("planCuenta: "+planCuenta);
						if(planCuenta!=null){
							System.out.println("planCuenta.id.strNumeroCuenta: "+planCuenta.getId().getStrNumeroCuenta());
						}
					}
					System.out.println("bdSuma: "+bdSuma);
					hojaManual.setBdMonto(bdSuma);
					hojaManual.setListHojaManualDetalle(listHojaManualDetalle);
					listaAux.add(hojaManual);
				}
			}
			lista = listaAux;
		}
		
		return lista;
	}
}
