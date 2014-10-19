package pe.com.tumi.tesoreria.banco.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.bo.AccesoBO;
import pe.com.tumi.tesoreria.banco.bo.AccesoDetalleResBO;
import pe.com.tumi.tesoreria.banco.bo.BancocuentaBO;
import pe.com.tumi.tesoreria.banco.bo.BancocuentachequeBO;
import pe.com.tumi.tesoreria.banco.bo.BancofondoBO;
import pe.com.tumi.tesoreria.banco.bo.FondodetalleBO;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalle;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalleRes;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.BancocuentaId;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;
import pe.com.tumi.tesoreria.banco.domain.BancocuentachequeId;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.BancofondoId;
import pe.com.tumi.tesoreria.banco.domain.Fondodetalle;
import pe.com.tumi.tesoreria.banco.service.AccesoService;
import pe.com.tumi.tesoreria.banco.service.BancoFondoService;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;

@Stateless
public class BancoFacade extends TumiFacade implements BancoFacadeRemote, BancoFacadeLocal {
	
	BancofondoBO boBancofondo = (BancofondoBO)TumiFactory.get(BancofondoBO.class);
	FondodetalleBO boFondodetalle = (FondodetalleBO)TumiFactory.get(FondodetalleBO.class);
	BancocuentaBO boBancoCuenta = (BancocuentaBO)TumiFactory.get(BancocuentaBO.class);
	BancocuentachequeBO boBancoCuentaCheque = (BancocuentachequeBO)TumiFactory.get(BancocuentachequeBO.class);
	BancoFondoService bancoFondoService = (BancoFondoService)TumiFactory.get(BancoFondoService.class);
	AccesoService accesoService = (AccesoService)TumiFactory.get(AccesoService.class);
	AccesoBO boAcceso = (AccesoBO)TumiFactory.get(AccesoBO.class);
	AccesoDetalleResBO boAccesoDetalleRes = (AccesoDetalleResBO)TumiFactory.get(AccesoDetalleResBO.class);
	
	
    public Bancofondo grabarBanco(Bancofondo o) throws BusinessException{
    	Bancofondo dto = null;
		try{
			dto = bancoFondoService.grabarBanco(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public Bancofondo grabarFondo(Bancofondo o) throws BusinessException{
    	Bancofondo dto = null;
		try{
			dto = bancoFondoService.grabarFondo(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Bancofondo> buscarBancoFondo(Bancofondo o) throws BusinessException{
    	List<Bancofondo> lista = null;
		try{
			lista = bancoFondoService.buscarBancoFondo(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Bancocuentacheque> getListaBancoCuentaChequePorBancoCuenta(Bancocuenta o) throws BusinessException{
    	List<Bancocuentacheque> lista = null;
		try{
			GeneralFacadeRemote generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);		
			lista = bancoFondoService.getListaBancoCuentaChequePorBancoCuenta(o, generalFacade);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public Bancofondo modificarFondo(Bancofondo o) throws BusinessException{
    	Bancofondo dto = null;
		try{
			dto = bancoFondoService.modificarFondo(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public Bancofondo modificarBanco(Bancofondo o) throws BusinessException{
    	Bancofondo dto = null;
		try{
			dto = bancoFondoService.modificarBanco(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public Bancofondo eliminarBancoFondo(Bancofondo o) throws BusinessException{
    	Bancofondo dto = null;
		try{
			o.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			boBancofondo.modificar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Fondodetalle> getListaFondoDetallePorSubsucursalPK(SubSucursalPK o) throws BusinessException{
    	List<Fondodetalle> lista = null;
		try{
			lista = boFondodetalle.getPorSubSucursalPK(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancofondo getBancoFondoPorId(BancofondoId o) throws BusinessException{
    	Bancofondo dto = null;
		try{					
			dto = boBancofondo.getPorPk(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    public Acceso grabarAcceso(Acceso o) throws BusinessException{
    	Acceso dto = null;
		try{
			dto = accesoService.grabarAcceso(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Acceso> buscarAcceso(Acceso o) throws BusinessException{
    	List<Acceso> lista = null;
		try{					
			lista = accesoService.buscarAcceso(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancocuenta getBancoCuentaPorId(BancocuentaId o) throws BusinessException{
    	Bancocuenta dto = null;
		try{					
			dto = boBancoCuenta.getPorPk(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    public Acceso modificarAcceso(Acceso o) throws BusinessException{
    	Acceso dto = null;
		try{
			dto = accesoService.modificarAcceso(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    public Acceso anularAcceso(Acceso o) throws BusinessException{
    	Acceso dto = null;
		try{
			o.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			boAcceso.modificar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<AccesoDetalleRes> getListaAccesoDetalleResPorAccesoDetalle(AccesoDetalle o) throws BusinessException{
    	List<AccesoDetalleRes> lista = null;
		try{					
			lista = boAccesoDetalleRes.getPorAccesoDetalle(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancofondo getBancoFondoPorTipoFondoFijoYMoneda(Bancofondo o) throws BusinessException{
    	Bancofondo dto = null;
		try{					
			dto = boBancofondo.getPorTipoFondoFijoYMoneda(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Acceso validarAccesoParaApertura(Acceso o, Integer intTipoFondoFijo, Integer intTipoMoneda) throws BusinessException{
    	Acceso dto = null;
		try{
			dto = accesoService.validarAccesoParaApertura(o, intTipoFondoFijo, intTipoMoneda);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Fondodetalle> getListaFondoDetallePorBancoFondo(Bancofondo o) throws BusinessException{
    	List<Fondodetalle> lista = null;
		try{
			lista = boFondodetalle.getPorBancoFondo(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancocuentacheque getBancoCuentaChequePorId(BancocuentachequeId o) throws BusinessException{
    	Bancocuentacheque dto = null;
		try{					
			dto = boBancoCuentaCheque.getPorPk(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    public Bancocuentacheque modificarBancoCuentaCheque(Bancocuentacheque o) throws BusinessException{
    	Bancocuentacheque dto = null;
		try{
			dto = boBancoCuentaCheque.modificar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancofondo getBancoFondoPorBancoCuenta(Bancocuenta bancoCuenta) throws BusinessException{
    	Bancofondo bancoFondo = null;
		try{					
			bancoFondo = boBancofondo.getPorBancoCuenta(bancoCuenta);			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return bancoFondo;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancocuenta getBancoCuentaPorBancoCuentaCheque(Bancocuentacheque bancoCuentaCheque) throws BusinessException{
    	Bancocuenta bancoCuenta = null;
		try{					
			bancoCuenta = boBancoCuenta.getPorBancoCuentaCheque(bancoCuentaCheque);			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return bancoCuenta;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancofondo getBancoFondoPorBancoCuentaCheque(Bancocuentacheque bancoCuentaCheque) throws BusinessException{
    	Bancofondo bancoFondo = null;
		try{					
			Bancocuenta bancoCuenta = boBancoCuenta.getPorBancoCuentaCheque(bancoCuentaCheque);
			bancoFondo = getBancoFondoPorBancoCuenta(bancoCuenta);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return bancoFondo;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Bancofondo> getBancoFondoPorEmpresayPersonaBanco(Integer intEmpresa, Integer intPersona) throws BusinessException{
    	List<Bancofondo> lista = null;
		try{
			lista = boBancofondo.getPorEmpresayPersonaBanco(intEmpresa, intPersona);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Bancocuenta> getBancoCuentaPorBancoFondo(Bancofondo bancoFondo) throws BusinessException{
    	List<Bancocuenta> lista = null;
		try{
			lista = bancoFondoService.getListaBancoCuentaPorBancoFondo(bancoFondo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    public Bancocuenta modificarBancoCuenta(Bancocuenta o) throws BusinessException{
    	Bancocuenta dto = null;
		try{
			dto = boBancoCuenta.modificar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancofondo obtenerBancoFondoPorControl(ControlFondosFijos controlFondosFijos) throws BusinessException{
    	Bancofondo dto;
    	try{
    		dto = bancoFondoService.obtenerBancoFondoPorControl(controlFondosFijos);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
  //Autor: jchavez / Tarea: Modificacion / Fecha: 30.09.2014
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancofondo obtenerBancoFondoParaIngreso(Usuario usuario, ControlFondosFijos 	controlFondosFijosCerrar) throws BusinessException{ //Integer intMoneda
    	Bancofondo dto = null;
		try{
			//Autor: jchavez / Tarea: Modificacion / Fecha: 30.09.2014
			dto = bancoFondoService.obtenerBancoFondoParaIngreso(usuario, controlFondosFijosCerrar);//intMoneda);
			//Fin jchavez - 30.09.2014
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancofondo obtenerBancoFondoParaIngreso(Usuario usuario, Integer intMoneda) throws BusinessException{
    	Bancofondo dto = null;
		try{
			dto = bancoFondoService.obtenerBancoFondoParaIngreso(usuario, intMoneda);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancofondo obtenerBancoFondoParaIngresoDesdeControl(ControlFondosFijos controlFondosFijos) throws BusinessException{
    	Bancofondo bancoFondo = null;
		try{
			Sucursal sucursal = new Sucursal();
			sucursal.getId().setIntPersEmpresaPk(controlFondosFijos.getId().getIntPersEmpresa());
			sucursal.getId().setIntIdSucursal(controlFondosFijos.getId().getIntSucuIdSucursal());
			
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			sucursal = empresaFacade.getSucursalPorPK(sucursal);
			Subsucursal subsucursal = empresaFacade.getSubSucursalPorIdSubSucursal(controlFondosFijos.getIntSudeIdSubsucursal());
			
			bancoFondo = bancoFondoService.obtenerBancoFondoPorControl(controlFondosFijos);
			bancoFondo.setFondoDetalleUsar(bancoFondoService.obtenerFondoDetalleContable(bancoFondo, sucursal, subsucursal));
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return bancoFondo;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Bancofondo> buscarBancoFondoParaDeposito(Bancofondo o, Usuario usuario) throws BusinessException{
    	List<Bancofondo> lista = null;
		try{
			lista = bancoFondoService.buscarBancoFondoParaDeposito(o, usuario);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Bancofondo> obtenerListaFondoExistente(Integer intIdEmpresa)throws BusinessException{
    	List<Bancofondo> lista = null;
		try{
			lista = bancoFondoService.obtenerListaFondoExistente(intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Bancofondo> obtenerListaBancoExistente(Integer intIdEmpresa)throws BusinessException{
    	List<Bancofondo> lista = null;
		try{
			lista = bancoFondoService.obtenerListaBancoExistente(intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancocuentacheque getUltimoBancoCuentaCheque(Bancocuenta bancoCuenta)throws BusinessException{
    	Bancocuentacheque dto = null;
		try{
			dto = bancoFondoService.getUltimoBancoCuentaCheque(bancoCuenta);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Fondodetalle obtenerFondoDetalleContable(Bancofondo bancoFondo, Sucursal sucursal, Subsucursal subsucursal) throws BusinessException{
    	Fondodetalle fondoDetalle = null;
		try{
			fondoDetalle =bancoFondoService.obtenerFondoDetalleContable(bancoFondo, sucursal, subsucursal);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return fondoDetalle;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Bancofondo obtenerBancoPorPlanCuenta(LibroDiarioDetalle libroDiarioDetalle) throws BusinessException{
    	Bancofondo dto = null;
		try{
			Bancocuenta bancoCuenta = boBancoCuenta.getPorPlanCuenta(libroDiarioDetalle).get(0);
			dto = getBancoFondoPorBancoCuenta(bancoCuenta);
			dto.setBancoCuentaSeleccionada(bancoCuenta);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Acceso obtenerAccesoPorControlFondosFijos(ControlFondosFijos controlFondosFijos) throws BusinessException{
    	Acceso dto = null;
		try{
			dto = accesoService.obtenerAccesoPorControlFondosFijos(controlFondosFijos);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return dto;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Bancocuenta> buscarListaBancoCuenta(Bancocuenta bancoCuentaFiltro) throws BusinessException{
    	List<Bancocuenta> lista = null;
		try{
			lista = bancoFondoService.buscarListaBancoCuenta(bancoCuentaFiltro);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
    
    /**
     * Autor: jchavez / Tarea: Creación / Fecha: 16.10.2014
     * Funcionalidad: Método que retorna lista de documentos configurados para el Fondo Fijo seleccionado
     * @author jchavez
     * @param intEmpresa
     * @param intTipoDocumento
     * @param intMoneda
     * @return lista - Lista de documentos configurados.
     * @throws BusinessException
     */
    public List<Fondodetalle> getDocumentoPorFondoFijo(Integer intEmpresa, Integer intTipoFondoFijo, Integer intMoneda) throws BusinessException{
    	List<Fondodetalle> lista = null;
		try{
			lista = boFondodetalle.getDocumentoPorFondoFijo(intEmpresa, intTipoFondoFijo, intMoneda);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	} 
}