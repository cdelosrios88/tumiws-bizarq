package pe.com.tumi.empresa.controller;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;

public class ReporteFondosFijosController {

	// Variables locales
	protected static Logger log = Logger.getLogger(ReporteFondosFijosController.class);
	private List<Sucursal> listaJuridicaSucursal;
	private int intCboSucursalEmp;
	private List listaEgreso;
	private List<Sucursal> listJuridicaSucursal;
	private Integer SESION_IDEMPRESA;
	
	
	
	
	public void getListSucursales() {
		
		try {
			if(listJuridicaSucursal == null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				listJuridicaSucursal = facade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	public List getListaEgreso() {
		return listaEgreso;
	}

	public void setListaEgreso(List listaEgreso) {
		this.listaEgreso = listaEgreso;
	}

	public int getIntCboSucursalEmp() {
		return intCboSucursalEmp;
	}

	public void setIntCboSucursalEmp(int intCboSucursalEmp) {
		this.intCboSucursalEmp = intCboSucursalEmp;
	}

	public List<Sucursal> getListaJuridicaSucursal() {
		return listaJuridicaSucursal;
	}

	public void setListaJuridicaSucursal(List<Sucursal> listaJuridicaSucursal) {
		this.listaJuridicaSucursal = listaJuridicaSucursal;
	}

	public ReporteFondosFijosController() {
		init();
	}

	public void init() {
		try {
			EmpresaFacadeLocal facade = null;
			facade = (EmpresaFacadeLocal) EJBFactory.getLocal(EmpresaFacadeLocal.class);
			listaJuridicaSucursal = facade.getListaSucursalPorPkEmpresa(2);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
}