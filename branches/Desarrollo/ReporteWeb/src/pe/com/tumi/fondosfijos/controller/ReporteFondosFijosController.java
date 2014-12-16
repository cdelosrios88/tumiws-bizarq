package pe.com.tumi.fondosfijos.controller;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;

public class ReporteFondosFijosController {

	// Variables locales
	protected static Logger log = Logger.getLogger(ReporteFondosFijosController.class);
	private int intCboSucursalEmp;
	private List listaEgreso;
	private List<Sucursal> listJuridicaSucursal;
	private List<Tabla> lstTipoFondoFijo;
	private Integer SESION_IDEMPRESA;
	
	
	
	
	public List<Sucursal> getListJuridicaSucursal() {
		return listJuridicaSucursal;
	}

	public void setListJuridicaSucursal(List<Sucursal> listJuridicaSucursal) {
		this.listJuridicaSucursal = listJuridicaSucursal;
	}

	public List<Tabla> getLstTipoFondoFijo() {
		return lstTipoFondoFijo;
	}

	public void setLstTipoFondoFijo(List<Tabla> lstTipoFondoFijo) {
		this.lstTipoFondoFijo = lstTipoFondoFijo;
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


	public ReporteFondosFijosController() {
		init();
	}

	public void init() {
		try {
			EmpresaFacadeRemote facade = null;
			facade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			listJuridicaSucursal = facade.getListaSucursalPorPkEmpresa(2);
			TablaFacadeRemote   tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			lstTipoFondoFijo = tablaFacade.getListaTablaPorAgrupamientoA(Constante.PARAM_T_FONDOSFIJOS, Constante.PARAM_STR_AGRUP_B);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
}