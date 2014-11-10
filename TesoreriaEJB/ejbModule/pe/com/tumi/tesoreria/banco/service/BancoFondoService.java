/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-006       			20/10/2014     		Bisarq        Nuevos Metodos        
*/
package pe.com.tumi.tesoreria.banco.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.bo.BancocuentaBO;
import pe.com.tumi.tesoreria.banco.bo.BancocuentachequeBO;
import pe.com.tumi.tesoreria.banco.bo.BancofondoBO;
import pe.com.tumi.tesoreria.banco.bo.FondodetalleBO;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalle;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.BancofondoId;
import pe.com.tumi.tesoreria.banco.domain.Fondodetalle;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;


public class BancoFondoService {

	protected static Logger log = Logger.getLogger(BancoFondoService.class);
	
	BancofondoBO boBancoFondo = (BancofondoBO)TumiFactory.get(BancofondoBO.class);
	FondodetalleBO boFondoDetalle = (FondodetalleBO)TumiFactory.get(FondodetalleBO.class);
	BancocuentaBO boBancoCuenta = (BancocuentaBO)TumiFactory.get(BancocuentaBO.class);
	BancocuentachequeBO boBancoCuentaCheque = (BancocuentachequeBO)TumiFactory.get(BancocuentachequeBO.class);
	AccesoService accesoService = (AccesoService)TumiFactory.get(AccesoService.class);
	
	
	private Bancocuentacheque grabarBancoCuentaCheque(Bancocuentacheque bancoCuentaCheque, Bancofondo bancoFondo, Bancocuenta bancoCuenta,
			GeneralFacadeRemote generalFacade, TipoArchivo tipoArchivo) throws BusinessException{
		
		Archivo archivo = bancoCuentaCheque.getArchivo();
		String target = archivo.getStrNombrearchivo();
		archivo = generalFacade.grabarArchivo(archivo);
		
		bancoCuentaCheque.setIntTipoCod(archivo.getId().getIntParaTipoCod());
		bancoCuentaCheque.setIntItemarchivo(archivo.getId().getIntItemArchivo());
		bancoCuentaCheque.setIntItemhistorico(archivo.getId().getIntItemHistorico());
		
		bancoCuentaCheque.getId().setIntEmpresaPk(bancoFondo.getId().getIntEmpresaPk());
		bancoCuentaCheque.getId().setIntItembancofondo(bancoFondo.getId().getIntItembancofondo());
		bancoCuentaCheque.getId().setIntItembancocuenta(bancoCuenta.getId().getIntItembancocuenta());
		bancoCuentaCheque.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		log.info(bancoCuentaCheque);
		
		boBancoCuentaCheque.grabar(bancoCuentaCheque);
		
		if(archivo!=null && target!=null && !target.isEmpty()){
			FileUtil.renombrarArchivo(target, tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());
		}
		
		return bancoCuentaCheque;
	}
	
	private Bancocuenta grabarBancoCuenta(Bancocuenta bancoCuenta, Bancofondo bancoFondo,PersonaFacadeRemote personaFacade, 
			GeneralFacadeRemote generalFacade, TipoArchivo tipoArchivo) throws BusinessException{
		
		CuentaBancaria cuentaBancaria = bancoCuenta.getCuentaBancaria();
		cuentaBancaria.getId().setIntIdPersona(bancoFondo.getIntPersonabancoPk());
		cuentaBancaria = personaFacade.grabarCuentaBancaria(cuentaBancaria);
		
		bancoCuenta.getId().setIntEmpresaPk(bancoFondo.getId().getIntEmpresaPk());
		bancoCuenta.getId().setIntItembancofondo(bancoFondo.getId().getIntItembancofondo());
		bancoCuenta.setIntPersona(bancoFondo.getIntPersonabancoPk());
		bancoCuenta.setIntCuentabancaria(cuentaBancaria.getId().getIntIdCuentaBancaria());
		
		log.info(bancoCuenta);		
		boBancoCuenta.grabar(bancoCuenta);	
		
		for(Bancocuentacheque bancoCuentaCheque : bancoCuenta.getListaBancocuentacheque()){
			grabarBancoCuentaCheque(bancoCuentaCheque, bancoFondo, bancoCuenta,	generalFacade, tipoArchivo);
		}
		
		return bancoCuenta;
	}
	
	
	public Bancofondo grabarBanco(Bancofondo bancoFondo) throws BusinessException{
		try{
			PersonaFacadeRemote personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			GeneralFacadeRemote generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			TipoArchivo tipoArchivo = generalFacade.getTipoArchivoPorPk(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BANCOCUENTACHEQUE);
			
			log.info(bancoFondo);
			boBancoFondo.grabar(bancoFondo);
			
			for(Bancocuenta bancoCuenta : bancoFondo.getListaBancocuenta()){
				
				grabarBancoCuenta(bancoCuenta,bancoFondo,personaFacade, generalFacade, tipoArchivo);
				
				
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return bancoFondo;
	}
	
	public Bancofondo grabarFondo(Bancofondo bancoFondo) throws BusinessException{
		try{
			log.info(bancoFondo);
			boBancoFondo.grabar(bancoFondo);
			
			for(Fondodetalle fondoDetalle : bancoFondo.getListaFondodetalle()){
				grabarFondoDetalle(fondoDetalle,bancoFondo);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return bancoFondo;
	}	
	
	private Persona obtenerPersonaJuridica(Bancofondo bancoFondo)throws Exception{
		PersonaFacadeRemote personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
		return personaFacade.getPersonaJuridicaPorIdPersonaYIdEmpresa(bancoFondo.getIntPersonabancoPk(),bancoFondo.getIntEmpresabancoPk());
		
		
	}
	
	public List<Bancofondo> buscarBancoFondo(Bancofondo bancoFondoFiltro) throws BusinessException{
		List<Bancofondo> listaBancoFondo = null;
		try{
			log.info(bancoFondoFiltro);
			
			listaBancoFondo = boBancoFondo.getPorBusqueda(bancoFondoFiltro);
			List<Bancofondo> listaBancoFondoTemp = new ArrayList<Bancofondo>();
			List<Fondodetalle> listaFondoDetalle = new ArrayList<Fondodetalle>();
			
			if(bancoFondoFiltro.getIntTipoBancoFondoFiltro().equals(Constante.PARAM_T_BANCOFONDOFIJO_BANCO) 
			|| bancoFondoFiltro.getIntTipoBancoFondoFiltro()==null){
				for(Bancofondo bancoFondo : listaBancoFondo){
					//bancoFondo.getIntTipoFondoFijo()==null -> es de tipo banco
					
					if(bancoFondo.getIntTipoFondoFijo()==null){
						//set Persona Juridica
						Persona perJuridica = obtenerPersonaJuridica(bancoFondo);
						bancoFondo.setPersonaEmpresa(perJuridica.getPersonaEmpresa());
						bancoFondo.getPersonaEmpresa().setPersona(perJuridica);
						
						bancoFondo.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_BANCO);
						bancoFondo.setListaBancocuenta(getListaBancoCuentaPorBancoFondo(bancoFondo));
						bancoFondo.setIntCantidadBancoCuenta(bancoFondo.getListaBancocuenta().size());
						listaBancoFondoTemp.add(bancoFondo);
					}
				}			
			}
			
			if(bancoFondoFiltro.getIntTipoBancoFondoFiltro().equals(Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO)
			|| bancoFondoFiltro.getIntTipoBancoFondoFiltro()==null){				
				for(Bancofondo bancoFondo : listaBancoFondo){
					//bancoFondo.getIntTipoFondoFijo()!=null -> es de tipo fondofijo
					if(bancoFondo.getIntTipoFondoFijo()!=null){
						listaFondoDetalle = boFondoDetalle.getPorBancoFondo(bancoFondo);
						for(Fondodetalle fondoDetalle : listaFondoDetalle){
							if(fondoDetalle.getIntCodigodetalle().equals(Constante.CODIGODETALLE_SOBREGIRO)){
								bancoFondo.setPoseeSobregiro(Boolean.TRUE);
								break;
							}
						}
						bancoFondo.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO);
						bancoFondo.setListaFondodetalle(listaFondoDetalle);
						listaBancoFondoTemp.add(bancoFondo);
					}						
				}				
			}
			
			listaBancoFondo = listaBancoFondoTemp;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaBancoFondo;
	}

	
	private Fondodetalle grabarFondoDetalle(Fondodetalle fondoDetalle, Bancofondo bancoFondo) throws BusinessException{
		fondoDetalle.getId().setIntEmpresaPk(bancoFondo.getId().getIntEmpresaPk());
		fondoDetalle.getId().setIntItembancofondo(bancoFondo.getId().getIntItembancofondo());
		fondoDetalle.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		log.info(fondoDetalle);
		
		boFondoDetalle.grabar(fondoDetalle);
		return fondoDetalle;
	}
	
	
	public Bancofondo modificarFondo(Bancofondo bancoFondo) throws BusinessException{
		try{
			List<Fondodetalle> listaFondoDetalle = bancoFondo.getListaFondodetalle();
			List<Fondodetalle> listaFondoDetalleIU = new ArrayList<Fondodetalle>();
			List<Fondodetalle> listaFondoDetalleBD = boFondoDetalle.getPorBancoFondo(bancoFondo);
			
			log.info(bancoFondo);
			boBancoFondo.modificar(bancoFondo);
			
			for(Fondodetalle fondoDetalle : listaFondoDetalle){
				if(fondoDetalle.getId().getIntItemfondodetalle()==null){
					grabarFondoDetalle(fondoDetalle, bancoFondo);
				}else{
					listaFondoDetalleIU.add(fondoDetalle);
				}
			}
			
			
			boolean seEncuentraEnIU = Boolean.FALSE;
			for(Fondodetalle fondoDetalleBD : listaFondoDetalleBD){
				seEncuentraEnIU = Boolean.FALSE;
				for(Fondodetalle fondoDetalleIU : listaFondoDetalleIU){
					if(fondoDetalleBD.getId().getIntItemfondodetalle().equals(fondoDetalleIU.getId().getIntItemfondodetalle())){
						fondoDetalleIU.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						log.info("mod:"+fondoDetalleIU);
						boFondoDetalle.modificar(fondoDetalleIU);
						seEncuentraEnIU = Boolean.TRUE;
						break;
					}
				}
				if(!seEncuentraEnIU){
					fondoDetalleBD.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					log.info("eli:"+fondoDetalleBD);
					boFondoDetalle.modificar(fondoDetalleBD);
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return bancoFondo;
	}
	
	public List<Bancocuenta> getListaBancoCuentaPorBancoFondo(Bancofondo bancoFondo) throws Exception{
		
		List<Bancocuenta> listaBancoCuenta;
		List<Bancocuenta> listaBancoCuentaTemp = new ArrayList<Bancocuenta>();
		listaBancoCuenta = boBancoCuenta.getPorBancoFondo(bancoFondo);

		for(Bancocuenta bancoCuenta : listaBancoCuenta){
			bancoCuenta.setCuentaBancaria(obtenerCuentaBancaria(bancoCuenta));
			if(bancoCuenta.getCuentaBancaria().getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){				
				listaBancoCuentaTemp.add(bancoCuenta);
			}
		}
		listaBancoCuenta = listaBancoCuentaTemp;
		
		return listaBancoCuenta;
	}
	
	private CuentaBancaria obtenerCuentaBancaria(Bancocuenta bancoCuenta)throws Exception{
		PersonaFacadeRemote personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
		
		CuentaBancariaPK cuentaBancariaPK = new CuentaBancariaPK();
		//Autor: jchavez / Tarea: Modificación / Fecha: 19.08.2014 / Se coloca en persona el id de la cooperativa
		cuentaBancariaPK.setIntIdPersona(bancoCuenta.getId().getIntEmpresaPk());//bancoCuenta.getIntPersona());
		cuentaBancariaPK.setIntIdCuentaBancaria(bancoCuenta.getIntCuentabancaria());
		return personaFacade.getCuentaBancariaPorPK(cuentaBancariaPK);
	}
	
	public List<Bancocuentacheque> getListaBancoCuentaChequePorBancoCuenta(Bancocuenta bancoCuenta,
			GeneralFacadeRemote generalFacade) throws BusinessException{
		
		List<Bancocuentacheque> listaBancoCuentaCheque = boBancoCuentaCheque.getPorBancoCuenta(bancoCuenta);
			
			ArchivoId archivoId;
			for(Bancocuentacheque bancoCuentaCheque : listaBancoCuentaCheque){
				//log.info(bancoCuentaCheque);
				
				if(bancoCuentaCheque.getIntItemarchivo()!=null){
					archivoId = new ArchivoId();
					archivoId.setIntParaTipoCod(bancoCuentaCheque.getIntTipoCod());
					archivoId.setIntItemArchivo(bancoCuentaCheque.getIntItemarchivo());
					archivoId.setIntItemHistorico(bancoCuentaCheque.getIntItemhistorico());
					bancoCuentaCheque.setArchivo(generalFacade.getArchivoPorPK(archivoId));
				}
				
				bancoCuentaCheque.setBancoCuenta(bancoCuenta);
			}
		
		return listaBancoCuentaCheque;
	}
	
	public Bancofondo modificarBanco(Bancofondo bancoFondo) throws Exception{
		try{
			PersonaFacadeRemote personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			GeneralFacadeRemote generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			TipoArchivo tipoArchivo = generalFacade.getTipoArchivoPorPk(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BANCOCUENTACHEQUE);
			
			List<Bancocuenta> listaBancoCuenta = bancoFondo.getListaBancocuenta();
			List<Bancocuenta> listaBancoCuentaIU = new ArrayList<Bancocuenta>();
			List<Bancocuenta> listaBancoCuentaBD = getListaBancoCuentaPorBancoFondo(bancoFondo);
			
			log.info(bancoFondo);
			boBancoFondo.modificar(bancoFondo);
			
			for(Bancocuenta bancoCuenta : listaBancoCuenta){
				if(bancoCuenta.getId().getIntItembancocuenta()==null){
					grabarBancoCuenta(bancoCuenta,bancoFondo,personaFacade,generalFacade,tipoArchivo);
				}else{
					listaBancoCuentaIU.add(bancoCuenta);
				}
			}
			
			boolean seEncuentaEnIU = Boolean.FALSE;
			for(Bancocuenta bancoCuentaBD : listaBancoCuentaBD){
				seEncuentaEnIU = Boolean.FALSE;
				for(Bancocuenta bancoCuentaIU : listaBancoCuentaIU){
					if(bancoCuentaIU.getId().getIntItembancocuenta().equals(bancoCuentaBD.getId().getIntItembancocuenta())){
						seEncuentaEnIU = Boolean.TRUE;
						
						modificarBancoCuenta(bancoCuentaIU, bancoFondo, generalFacade, tipoArchivo);
						break;
					}
				}
				if(!seEncuentaEnIU){
					log.info("eli:"+seEncuentaEnIU);
					CuentaBancaria cuentaBancaria = bancoCuentaBD.getCuentaBancaria();
					cuentaBancaria.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					
					log.info("CuB:"+cuentaBancaria);
					personaFacade.modificarCuentaBancaria(cuentaBancaria);
				}
			}

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return bancoFondo;
	}
	
	private Bancocuenta modificarBancoCuenta(Bancocuenta bancoCuenta, Bancofondo bancoFondo, 
			GeneralFacadeRemote generalFacade,TipoArchivo tipoArchivo) throws BusinessException, EJBFactoryException{
		
		List<Bancocuentacheque> listaBancoCuentaCheque = bancoCuenta.getListaBancocuentacheque();
		List<Bancocuentacheque> listaBancoCuentaChequeTemp = new ArrayList<Bancocuentacheque>();
		List<Bancocuentacheque> listaBancoCuentaChequeBD = getListaBancoCuentaChequePorBancoCuenta(bancoCuenta, generalFacade);
		PersonaFacadeRemote personaFacade2 =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
		
		
		log.info("CuB:"+bancoCuenta.getCuentaBancaria());
		
		personaFacade2.modificarCuentaBancaria(bancoCuenta.getCuentaBancaria());
		
		log.info("mod:"+bancoCuenta);
		boBancoCuenta.modificar(bancoCuenta);
		
		
		for(Bancocuentacheque bancoCuentaCheque : listaBancoCuentaCheque){
			if(bancoCuentaCheque.getId().getIntItembancuencheque()==null){
				grabarBancoCuentaCheque(bancoCuentaCheque, bancoFondo, bancoCuenta,	generalFacade, tipoArchivo);
			}else{
				listaBancoCuentaChequeTemp.add(bancoCuentaCheque);
			}
		}
		
		boolean seEncuentraEnIU = Boolean.FALSE;
		for(Bancocuentacheque bancoCuentaChequeBD : listaBancoCuentaChequeBD){
			seEncuentraEnIU = Boolean.FALSE;
			for(Bancocuentacheque bancoCuentaChequeIU : listaBancoCuentaChequeTemp){
				if(bancoCuentaChequeIU.getId().getIntItembancuencheque().equals(bancoCuentaChequeBD.getId().getIntItembancuencheque())){
					seEncuentraEnIU = Boolean.TRUE;
					modificarBancoCuentaCheque(bancoCuentaChequeIU, bancoCuentaChequeBD, generalFacade, tipoArchivo);
					break;
				}
			}
			if(!seEncuentraEnIU){
				bancoCuentaChequeBD.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
				log.info("eli:"+bancoCuentaChequeBD);
				boBancoCuentaCheque.modificar(bancoCuentaChequeBD);
			}
		}
		return bancoCuenta;
	}
	
	private void modificarBancoCuentaCheque(Bancocuentacheque bancoCuentaChequeIU, Bancocuentacheque bancoCuentaChequeBD, 
		GeneralFacadeRemote generalFacade, TipoArchivo tipoArchivo) throws BusinessException{
		
		boolean modificarArchivo = Boolean.FALSE;
		Archivo archivo = null;
		String target = "";
		
		if(bancoCuentaChequeIU.getIntItemarchivo()!=null){
			if(bancoCuentaChequeBD.getIntItemarchivo()==null){
				archivo = bancoCuentaChequeIU.getArchivo();
				target = archivo.getStrNombrearchivo();
				archivo = generalFacade.grabarArchivo(archivo);
				
				bancoCuentaChequeIU.setIntTipoCod(archivo.getId().getIntParaTipoCod());
				bancoCuentaChequeIU.setIntItemarchivo(archivo.getId().getIntItemArchivo());
				bancoCuentaChequeIU.setIntItemhistorico(archivo.getId().getIntItemHistorico());
				
				modificarArchivo = Boolean.TRUE;
				log.info("--+:modificarArchivo:"+bancoCuentaChequeIU);
				
			}else if(!bancoCuentaChequeIU.getArchivo().getStrNombrearchivo().equals(bancoCuentaChequeBD.getArchivo().getStrNombrearchivo())){
				archivo = bancoCuentaChequeIU.getArchivo();
				target = archivo.getStrNombrearchivo();
				archivo = generalFacade.grabarArchivo(archivo);
				
				bancoCuentaChequeIU.setIntTipoCod(archivo.getId().getIntParaTipoCod());
				bancoCuentaChequeIU.setIntItemarchivo(archivo.getId().getIntItemArchivo());
				bancoCuentaChequeIU.setIntItemhistorico(archivo.getId().getIntItemHistorico());
				
				modificarArchivo = Boolean.TRUE;
				log.info("--+:modificarArchivo:"+bancoCuentaChequeIU);
			}
		}

		log.info("--+:"+bancoCuentaChequeIU);
		boBancoCuentaCheque.modificar(bancoCuentaChequeIU);
		
		if(modificarArchivo){
			FileUtil.renombrarArchivo(target, tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());
		}
	}
	
	
	public Bancofondo obtenerBancoFondoPorControl(ControlFondosFijos controlFondosFijos) throws BusinessException{
		Bancofondo bancoFondo = null;
		try{
			bancoFondo = new Bancofondo();
			bancoFondo.getId().setIntEmpresaPk(controlFondosFijos.getId().getIntPersEmpresa());
			bancoFondo.setIntTipoFondoFijo(controlFondosFijos.getId().getIntParaTipoFondoFijo());
			bancoFondo.setIntMonedaCod(controlFondosFijos.getIntParaMoneda());
			log.info(bancoFondo);
			bancoFondo = boBancoFondo.getPorTipoFondoFijoYMoneda(bancoFondo);
			log.info(bancoFondo);
			bancoFondo.setListaFondodetalle(boFondoDetalle.getPorBancoFondo(bancoFondo));
			
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return bancoFondo;
	}	
	
	//Autor: jchavez / Tarea: Modificacion / Fecha: 30.09.2014
	public Bancofondo obtenerBancoFondoParaIngreso(Usuario usuario, ControlFondosFijos controlFondosFijosCerrar) throws BusinessException{ //Integer intMoneda
		Bancofondo bancoFondo = new Bancofondo();
		try{
			Sucursal sucursal = usuario.getSucursal();
			Subsucursal	subsucursal = usuario.getSubSucursal();
			Integer intIdEmpresa = sucursal.getId().getIntPersEmpresaPk();
			
			bancoFondo.getId().setIntEmpresaPk(intIdEmpresa);
			//Autor: jchavez / Tarea: Modificacion / Fecha: 30.09.2014
			bancoFondo.setIntTipoFondoFijo(controlFondosFijosCerrar.getId().getIntParaTipoFondoFijo()); //Constante.PARAM_T_TIPOFONDOFIJO_CAJA
			//Fin jchavez - 30.09.2014
			bancoFondo.setIntMonedaCod(controlFondosFijosCerrar.getIntParaMoneda());
			bancoFondo = boBancoFondo.getPorTipoFondoFijoYMoneda(bancoFondo);
			
			if(bancoFondo==null) return null;
			
			bancoFondo.setListaFondodetalle(boFondoDetalle.getPorBancoFondo(bancoFondo));
			
			bancoFondo.setFondoDetalleUsar(obtenerFondoDetalleContable(bancoFondo, sucursal, subsucursal));
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return bancoFondo;
	}

	public Bancofondo obtenerBancoFondoParaIngreso(Usuario usuario, Integer intMoneda) throws BusinessException{
		Bancofondo bancoFondo = new Bancofondo();
		try{
			Sucursal sucursal = usuario.getSucursal();
			Subsucursal	subsucursal = usuario.getSubSucursal();
			Integer intIdEmpresa = sucursal.getId().getIntPersEmpresaPk();
			
			bancoFondo.getId().setIntEmpresaPk(intIdEmpresa);
			bancoFondo.setIntTipoFondoFijo(Constante.PARAM_T_TIPOFONDOFIJO_CAJA);
			bancoFondo.setIntMonedaCod(intMoneda);
			bancoFondo = boBancoFondo.getPorTipoFondoFijoYMoneda(bancoFondo);
			
			if(bancoFondo==null) return null;
			
			bancoFondo.setListaFondodetalle(boFondoDetalle.getPorBancoFondo(bancoFondo));
			
			bancoFondo.setFondoDetalleUsar(obtenerFondoDetalleContable(bancoFondo, sucursal, subsucursal));
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return bancoFondo;
	}
	
	
	public Fondodetalle obtenerFondoDetalleContable(Bancofondo bancoFondo, Sucursal sucursal, Subsucursal subsucursal)throws Exception{
		Fondodetalle fondoDetalleContable = null;
		for(Fondodetalle fondoDetalle : bancoFondo.getListaFondodetalle()){
			if(fondoDetalle.getIntCodigodetalle().equals(Constante.CODIGODETALLE_CUENTACONTABLE)
			&& fondoDetalle.getIntTotalsucursalCod()==null 
			&& fondoDetalle.getIntIdsucursal().equals(sucursal.getId().getIntIdSucursal())
			&& fondoDetalle.getIntIdsubsucursal().equals(subsucursal.getId().getIntIdSubSucursal())){
				fondoDetalleContable = fondoDetalle;
				break;
			}				
		}
		
		if(bancoFondo.getFondoDetalleUsar()==null){
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			for(Fondodetalle fondoDetalle : bancoFondo.getListaFondodetalle()){
				if(fondoDetalle.getIntCodigodetalle().equals(Constante.CODIGODETALLE_CUENTACONTABLE)
				&& fondoDetalle.getIntTotalsucursalCod()!=null 
				&& empresaFacade.validarTotalSucursal(sucursal.getIntIdTipoSucursal(), fondoDetalle.getIntTotalsucursalCod())){
					fondoDetalleContable = fondoDetalle;
					break;
				}
			}
		}
		
		if(fondoDetalleContable !=null){
			PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			PlanCuentaId planCuentaId = new PlanCuentaId();
			planCuentaId.setIntEmpresaCuentaPk(fondoDetalleContable.getIntEmpresacuentaPk());
			planCuentaId.setIntPeriodoCuenta(fondoDetalleContable.getIntPeriodocuenta());
			planCuentaId.setStrNumeroCuenta(fondoDetalleContable.getStrNumerocuenta());
			fondoDetalleContable.setPlanCuenta(planCuentaFacade.getPlanCuentaPorPk(planCuentaId));
		}
		
		return fondoDetalleContable;
	}
	
	public List<Bancofondo> buscarBancoFondoParaDeposito(Bancofondo bancoFondoFiltro, Usuario usuario) throws BusinessException{
		List<Bancofondo> listaBancoFondo = new ArrayList<Bancofondo>();
		try{
			Integer intIdEmpresa = bancoFondoFiltro.getId().getIntEmpresaPk();
			
			Acceso accesoFiltro = new Acceso();
			accesoFiltro.getId().setIntPersEmpresaAcceso(intIdEmpresa);
			accesoFiltro.setIntPersEmpresaSucursal(intIdEmpresa);
			accesoFiltro.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			accesoFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			accesoFiltro.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			
			List<Acceso> listaAcceso = accesoService.buscarAcceso(accesoFiltro);
			
			List<Bancocuenta> listaBancoCuenta = new ArrayList<Bancocuenta>();
			HashSet<Integer> hashSetBancoFondoId = new HashSet<Integer>();
			
			for(Acceso acceso : listaAcceso){
				for(AccesoDetalle accesoDetalle : acceso.getListaAccesoDetalle()){
					if(accesoDetalle.getIntTipoAccesoDetalle().equals(Constante.TIPOACCESODETALLE_CUENTABANCARIA)){
						hashSetBancoFondoId.add(accesoDetalle.getIntItemBancoFondo());
						listaBancoCuenta.add(accesoDetalle.getBancoCuenta());
					}
				}
			}
			
			for(Integer intIdBanco : hashSetBancoFondoId){
				BancofondoId  bancoFondoId = new BancofondoId();
				bancoFondoId.setIntEmpresaPk(intIdEmpresa);
				bancoFondoId.setIntItembancofondo(intIdBanco);
				Bancofondo bancoFondo = boBancoFondo.getPorPk(bancoFondoId);
				if(bancoFondo.getIntEstadoCod().equals(bancoFondoFiltro.getIntEstadoCod()) 
				&& ((bancoFondo.getIntMonedaCod()==null) || bancoFondo.getIntMonedaCod().equals(bancoFondoFiltro.getIntMonedaCod()))){
					listaBancoFondo.add(bancoFondo);
				}				
			}
			
			//List<Bancofondo> listaTemp = new ArrayList<Bancofondo>();
			for(Bancofondo bancoFondo : listaBancoFondo){

				Persona perJuridica = obtenerPersonaJuridica(bancoFondo);
				bancoFondo.setPersonaEmpresa(perJuridica.getPersonaEmpresa());
				bancoFondo.getPersonaEmpresa().setPersona(perJuridica);
				
				for(Bancocuenta bancoCuenta : listaBancoCuenta){
					if(bancoFondo.getId().getIntItembancofondo().equals(bancoCuenta.getId().getIntItembancofondo())){
						bancoCuenta.setCuentaBancaria(obtenerCuentaBancaria(bancoCuenta));
						bancoFondo.getListaBancocuenta().add(bancoCuenta);
					}
				}			
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaBancoFondo;
	}
	
	public List<Bancofondo> obtenerListaFondoExistente(Integer intIdEmpresa)throws Exception{
		Bancofondo fondoFiltro = new Bancofondo();
		fondoFiltro.getId().setIntEmpresaPk(intIdEmpresa);
		fondoFiltro.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		fondoFiltro.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_FONDOFIJO);
		List<Bancofondo> listaFondo = buscarBancoFondo(fondoFiltro);
		/*for(Bancofondo fondo : listaFondo){
			log.info(fondo);
			for(Fondodetalle fondoDetalle : fondo.getListaFondodetalle()){
				if(fondoDetalle.getIntCodigodetalle().equals(Constante.CODIGODETALLE_CUENTACONTABLE))log.info(fondoDetalle);
			}
		}*/
		return listaFondo;
	}
	
	
	public List<Bancofondo> obtenerListaBancoExistente(Integer intIdEmpresa)throws Exception{
		Bancofondo bancoFiltro = new Bancofondo();
		bancoFiltro.getId().setIntEmpresaPk(intIdEmpresa);
		bancoFiltro.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		bancoFiltro.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_BANCO);
		List<Bancofondo> listaBanco = buscarBancoFondo(bancoFiltro);
		/*for(Bancofondo banco : listaBanco){
			log.info(banco);
			for(Bancocuenta bancoCuenta : banco.getListaBancocuenta()){
				log.info(bancoCuenta);
			}
		}*/
		return listaBanco;
	}
	
	public Bancocuentacheque getUltimoBancoCuentaCheque(Bancocuenta bancoCuenta) throws Exception{		
		List<Bancocuentacheque> listaBancoCuentaCheque = boBancoCuentaCheque.getPorBancoCuenta(bancoCuenta);			
		Bancocuentacheque bancoCuentaChequeUltima = null;
		for(Bancocuentacheque bancoCuentaCheque : listaBancoCuentaCheque){
			if(bancoCuentaCheque.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				bancoCuentaChequeUltima = bancoCuentaCheque;
				break;
			}
		}
		return bancoCuentaChequeUltima;
	}
	
	public List<Bancocuenta> buscarListaBancoCuenta(Bancocuenta bancoCuentaFiltro)throws BusinessException{
		List<Bancocuenta> listaBancoCuenta = null;
		try{
			LibroDiarioDetalle libroDiarioDetalle = new LibroDiarioDetalle();
			//Inicio: REQ14-006 - bizarq - 20/10/2014
			libroDiarioDetalle.setId(new LibroDiarioDetalleId());
			//Fin: REQ14-006 - bizarq - 20/10/2014
			libroDiarioDetalle.getId().setIntPersEmpresaLibro(bancoCuentaFiltro.getIntEmpresacuentaPk());
			libroDiarioDetalle.setIntPersEmpresaCuenta(bancoCuentaFiltro.getIntEmpresacuentaPk());
			libroDiarioDetalle.setIntContPeriodo(bancoCuentaFiltro.getIntPeriodocuenta());
			libroDiarioDetalle.setStrContNumeroCuenta(bancoCuentaFiltro.getStrNumerocuenta());
			
			log.info(libroDiarioDetalle);
			listaBancoCuenta = boBancoCuenta.getPorPlanCuenta(libroDiarioDetalle);
			List<Bancocuenta> listaTemp = new ArrayList<Bancocuenta>();
			for(Bancocuenta bancoCuenta : listaBancoCuenta){
				bancoCuenta.setBancofondo(boBancoFondo.getPorBancoCuenta(bancoCuenta));
				//Inicio: REQ14-006 - bizarq - 20/10/2014
				bancoCuenta.setCuentaBancaria(obtenerCuentaBancaria(bancoCuenta));
				//Fin: REQ14-006 - bizarq - 20/10/2014
				if(bancoCuenta.getBancofondo().getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
				&& bancoCuenta.getBancofondo().getIntBancoCod().equals(bancoCuentaFiltro.getBancofondo().getIntBancoCod())){
					listaTemp.add(bancoCuenta);
				}
			}
			
			listaBancoCuenta = listaTemp;
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaBancoCuenta;
	}	
}