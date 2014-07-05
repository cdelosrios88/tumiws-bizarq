package pe.com.tumi.seguridad.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.seguridad.domain.AccesoExternoPc;
import pe.com.tumi.seguridad.domain.AdminMenu;
import pe.com.tumi.seguridad.domain.RegistroPc;
import pe.com.tumi.seguridad.domain.SolicitudCambio;
import pe.com.tumi.seguridad.service.impl.AdminMenuServiceImpl;
import pe.com.tumi.seguridad.service.impl.RegistroPcServiceImpl;

public class RegistroPcController extends GenericController {
	private    	RegistroPcServiceImpl 	registroPcService;
	private		List 					beanListRegistroPc;
	private		List 					beanListAccesosExtPc = new ArrayList();
	private		Integer					intCboEmpresasBusq;
	private		Integer					intCboSucursalesBusq;
	private		Integer					intCboAreasBusq;
	private		Integer					intCboEstadoBusq;
	private		Integer					intCboEmpRegPc;
	private		Integer					intCboSucRegPc;
	private		Integer					intCboAreaRegPc;
	private		Integer					intCboEstadoRegPc;
	private		Integer					intCboExternos;
	private 	RegistroPc				beanRegistroPc = new RegistroPc();
	private 	Boolean					blnRegistroPcRendered = false;
	
	public void listarRegistroPc(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging registroPcController.listarSolicitudes-----------------------------");
		setService(registroPcService);
		
		log.info("getIntCboEmpresasBusq(): "+getIntCboEmpresasBusq());
		log.info("getIntCboSucursalesBusq(): "+getIntCboSucursalesBusq());
		log.info("getIntCboAreasBusq(): "+getIntCboAreasBusq());
		log.info("getIntCboEstadoBusq(): "+getIntCboEstadoBusq());
		
        Integer intIdEmpresa = (getIntCboEmpresasBusq()!=null && getIntCboEmpresasBusq()!=0)?getIntCboEmpresasBusq():null;
		Integer intIdSucursal = (getIntCboSucursalesBusq()!=null && getIntCboSucursalesBusq()!=0)?getIntCboSucursalesBusq():null;
		Integer intIdArea = (getIntCboAreasBusq()!=null && getIntCboAreasBusq()!=0)?getIntCboAreasBusq():null;
		Integer intIdEstado = (getIntCboEstadoBusq()!=null && getIntCboEstadoBusq()!=0)?getIntCboEstadoBusq():null;;
		
		HashMap prmtRegPc = new HashMap();
		prmtRegPc.put("pIntIdEmpresa", intIdEmpresa);
		prmtRegPc.put("pIntIdSucursal", intIdSucursal);
		prmtRegPc.put("pIntIdArea", intIdArea);
		prmtRegPc.put("pIntIdEstado", intIdEstado);
		prmtRegPc.put("pIntIdRegPc", null);
		
		ArrayList listaRegistroPc = new ArrayList();
		listaRegistroPc = getService().listarRegistroPc(prmtRegPc);
		log.info("listaRegistroPc.size(): "+listaRegistroPc.size());
		
		setBeanListRegistroPc(listaRegistroPc);
		
	}
	
	public void modificarRegistroPc(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging registroPcController.modificarRegistroPc-----------------------------");
		setService(registroPcService);
		String strIdRegPc = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formRegPc:hiddenIdRegPc");
		log.info("strIdRegPc : "+strIdRegPc);
		
		HashMap prmtRegPc = new HashMap();
		prmtRegPc.put("pIntIdEmpresa", null);
		prmtRegPc.put("pIntIdSucursal", null);
		prmtRegPc.put("pIntIdArea", null);
		prmtRegPc.put("pIntIdEstado", null);
		prmtRegPc.put("pIntIdRegPc", (strIdRegPc!=null)?Integer.parseInt(strIdRegPc):null);
		
		ArrayList listaRegistroPc = new ArrayList();
		listaRegistroPc = getService().listarRegistroPc(prmtRegPc);
		log.info("listaRegistroPc.size(): "+listaRegistroPc.size());
		
		RegistroPc regPc = new RegistroPc();
		regPc = (RegistroPc)listaRegistroPc.get(0);
		log.info("regPc.getIntIdEmpresa(): "+regPc.getIntIdEmpresa());
		log.info("regPc.getIntIdSucursal(): "+regPc.getIntIdSucursal());
		log.info("regPc.getIntIdArea(): "+regPc.getIntIdArea());
		log.info("regPc.getIntIdPc(): "+regPc.getIntIdPc());
		log.info("regPc.getStrCodPc(): "+regPc.getStrCodPc());
		log.info("regPc.getIntIdEstado(): "+regPc.getIntIdEstado());
		
		//Listar accesos externos
		AccesoExternoPc prmtAccesosExtPc = new AccesoExternoPc();
		prmtAccesosExtPc.setIntIdEmpresa(regPc.getIntIdEmpresa());
		prmtAccesosExtPc.setIntIdSucursal(regPc.getIntIdSucursal());
		prmtAccesosExtPc.setIntIdArea(regPc.getIntIdArea());
		prmtAccesosExtPc.setIntIdComputadora(regPc.getIntIdPc());
		
		ArrayList lsAccesosExternos = new ArrayList();
		lsAccesosExternos = getService().listarAccesosExtPc(prmtAccesosExtPc);
		log.info("lsAccesosExternos.size(): "+lsAccesosExternos.size());
		
		setBlnRegistroPcRendered(true);
		setBeanRegistroPc(regPc);
		setBeanListAccesosExtPc(lsAccesosExternos);
	}
	
	public void grabarRegistroPc(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging registroPcController.grabarRegistro-----------------------------");
		setService(registroPcService);
		RegistroPc regPc = new RegistroPc();
		regPc = getBeanRegistroPc();
		
		log.info("regPc.getIntIdEmpresa(): "+regPc.getIntIdEmpresa());
		log.info("regPc.getIntIdSucursal(): "+regPc.getIntIdSucursal());
		log.info("regPc.getIntIdArea(): "+regPc.getIntIdArea());
		log.info("regPc.getIntIdPc(): "+regPc.getIntIdPc());
		log.info("regPc.getStrCodPc(): "+regPc.getStrCodPc());
		log.info("regPc.getIntIdEstado(): "+regPc.getIntIdEstado());
		
		getService().grabarRegistroPc(regPc);
		log.info("Se ejecutó: getService().grabarRegistroPc(regPc)");
		log.info("regPc.getIntIdPcOut(): "+regPc.getIntIdPcOut());
		
		ArrayList arrayExt = new ArrayList();
		arrayExt = (ArrayList) getBeanListAccesosExtPc();
		
		for(int i=0; i<arrayExt.size(); i++){
			AccesoExternoPc accesoExtPc = new AccesoExternoPc();
			accesoExtPc = (AccesoExternoPc)arrayExt.get(i);
			accesoExtPc.setIntIdEmpresa(regPc.getIntIdEmpresa());
			accesoExtPc.setIntIdSucursal(regPc.getIntIdSucursal());
			accesoExtPc.setIntIdArea(regPc.getIntIdArea());
			accesoExtPc.setIntIdComputadora(regPc.getIntIdPcOut());
			accesoExtPc.setIntIdTipoAcceso(accesoExtPc.getIntIdTipoAcceso());
			accesoExtPc.setIntIdEstado(accesoExtPc.getIntIdEstado());
			// El tipo de acceso se obtiene en agregarExternos 
			// El estado se obtiene por default
			accesoExtPc.setIntConta(i);
			getService().grabarAccesoExtPc(accesoExtPc);
			log.info("Se ejecutó: getService().grabarAccesoExtPc(accesoExtPc)");
		}
		
		listarRegistroPc(event);
	}
	
	public void eliminarRegistroPc(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging registroPcController.eliminarRegistroPc-----------------------------");
		setService(registroPcService);
		String strIdRegPc = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formRegPc:hiddenIdRegPc");
		log.info("strIdRegPc : "+strIdRegPc);
		
		RegistroPc regPc = new RegistroPc();
		regPc.setIntIdPc((strIdRegPc!=null)?Integer.parseInt(strIdRegPc):null);
		
		getService().eliminarRegistroPc(regPc);
		log.info("Se ha eliminado el menu: "+strIdRegPc);
		listarRegistroPc(event);
	}
	
	public void habilitarGrabarRegistroPc(ActionEvent event){
		log.info("-----------------------Debugging registroPcController.habilitarGrabarRegistroPc-----------------------------");
		setBlnRegistroPcRendered(true);
		limpiarFormRegistroPc();
	}
	
	public void limpiarFormRegistroPc(){
		log.info("-----------------------Debugging registroPcController.limpiarFormRegistroPc----------------------------------");
		RegistroPc regPc = new RegistroPc();
		setBeanRegistroPc(regPc);
		beanListAccesosExtPc.clear();
	}
	
	public void cancelarRegistroPc(ActionEvent event){
		log.info("-----------------------Debugging registroPcController.cancelarRegistroPc-------------------------------------");
		limpiarFormRegistroPc();
		setBlnRegistroPcRendered(false);
	}
	
	public void agregarExternos(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging registroPcController.agregarExternos----------------------------------------");

		RegistroPc regPc = new RegistroPc();
		regPc = getBeanRegistroPc();
		
		log.info("getIntCboExternos(): "+getIntCboExternos());
		String strTipoAcceso = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formRegistroPc:hiddenTerceros");
		log.info("strTipoAcceso: "+strTipoAcceso);
		
		AccesoExternoPc accesoExtPc = new AccesoExternoPc();
		accesoExtPc.setIntIdTipoAcceso(getIntCboExternos());
		accesoExtPc.setStrTipoAcceso(strTipoAcceso);
		accesoExtPc.setIntIdEstado(1);// por default
		
		beanListAccesosExtPc.add(accesoExtPc);
		
		//listarExternos(event);
	}
	
	public void listarExternos(ActionEvent event) throws DaoException{
		log.info("-----------------------Debugging registroPcController.listarExternos-----------------------------");
		setService(registroPcService);
		
		HashMap prmtAccesosExtPc = new HashMap();
		
		ArrayList listaAccesosExtPc = new ArrayList();
		listaAccesosExtPc = getService().listarAccesosExtPc(prmtAccesosExtPc);
		log.info("listaAccesosExtPc.size(): "+listaAccesosExtPc.size());
		
		setBeanListAccesosExtPc(listaAccesosExtPc);
	}

	//--------------------------------------------------------------------------------------------------------------------------------
	//Getters y Setters
	//--------------------------------------------------------------------------------------------------------------------------------
	public RegistroPcServiceImpl getRegistroPcService() {
		return registroPcService;
	}

	public void setRegistroPcService(RegistroPcServiceImpl registroPcService) {
		this.registroPcService = registroPcService;
	}

	public List getBeanListRegistroPc() {
		return beanListRegistroPc;
	}

	public void setBeanListRegistroPc(List beanListRegistroPc) {
		this.beanListRegistroPc = beanListRegistroPc;
	}

	public Integer getIntCboEmpresasBusq() {
		return intCboEmpresasBusq;
	}

	public void setIntCboEmpresasBusq(Integer intCboEmpresasBusq) {
		this.intCboEmpresasBusq = intCboEmpresasBusq;
	}

	public Integer getIntCboSucursalesBusq() {
		return intCboSucursalesBusq;
	}

	public void setIntCboSucursalesBusq(Integer intCboSucursalesBusq) {
		this.intCboSucursalesBusq = intCboSucursalesBusq;
	}

	public Integer getIntCboAreasBusq() {
		return intCboAreasBusq;
	}

	public void setIntCboAreasBusq(Integer intCboAreasBusq) {
		this.intCboAreasBusq = intCboAreasBusq;
	}

	public Integer getIntCboEstadoBusq() {
		return intCboEstadoBusq;
	}

	public void setIntCboEstadoBusq(Integer intCboEstadoBusq) {
		this.intCboEstadoBusq = intCboEstadoBusq;
	}

	public RegistroPc getBeanRegistroPc() {
		return beanRegistroPc;
	}

	public void setBeanRegistroPc(RegistroPc beanRegistroPc) {
		this.beanRegistroPc = beanRegistroPc;
	}

	public Integer getIntCboEmpRegPc() {
		return intCboEmpRegPc;
	}

	public void setIntCboEmpRegPc(Integer intCboEmpRegPc) {
		this.intCboEmpRegPc = intCboEmpRegPc;
	}

	public Integer getIntCboSucRegPc() {
		return intCboSucRegPc;
	}

	public void setIntCboSucRegPc(Integer intCboSucRegPc) {
		this.intCboSucRegPc = intCboSucRegPc;
	}

	public Integer getIntCboAreaRegPc() {
		return intCboAreaRegPc;
	}

	public void setIntCboAreaRegPc(Integer intCboAreaRegPc) {
		this.intCboAreaRegPc = intCboAreaRegPc;
	}

	public Integer getIntCboEstadoRegPc() {
		return intCboEstadoRegPc;
	}

	public void setIntCboEstadoRegPc(Integer intCboEstadoRegPc) {
		this.intCboEstadoRegPc = intCboEstadoRegPc;
	}

	public Boolean getBlnRegistroPcRendered() {
		return blnRegistroPcRendered;
	}

	public void setBlnRegistroPcRendered(Boolean blnRegistroPcRendered) {
		this.blnRegistroPcRendered = blnRegistroPcRendered;
	}

	public List getBeanListAccesosExtPc() {
		return beanListAccesosExtPc;
	}

	public void setBeanListAccesosExtPc(List beanListAccesosExtPc) {
		this.beanListAccesosExtPc = beanListAccesosExtPc;
	}

	public Integer getIntCboExternos() {
		return intCboExternos;
	}

	public void setIntCboExternos(Integer intCboExternos) {
		this.intCboExternos = intCboExternos;
	}
	
}
