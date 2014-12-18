package pe.com.tumi.fondosfijos.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.reporte.operativo.tesoreria.domain.EgresoFondoFijo;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgreso;
import pe.com.tumi.reporte.operativo.tesoreria.facade.MovEgresoFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;

public class ReporteFondosFijosController {

	// Variables locales
	protected static Logger log = Logger.getLogger(ReporteFondosFijosController.class);
	private int intCboSucursalEmp;
	private List<EgresoFondoFijo> listaEgreso;
	private int intIdFondoFijo;
	private List<Sucursal> listJuridicaSucursal;
	private List<Tabla> lstTipoFondoFijo;
	private List<SelectItem> listYears;
	private List<MovEgreso> lstFondoFijo;
	private int intIdSucursal;
	private int intYear;
	private int intIdTipoFondoFijo;
	private String strIniciar;
	
	
	public int getIntIdFondoFijo() {
		return intIdFondoFijo;
	}

	public void setIntIdFondoFijo(int intIdFondoFijo) {
		this.intIdFondoFijo = intIdFondoFijo;
	}

	public String getStrIniciar() {
		lstFondoFijo = null;
		intIdSucursal  =0;
		intYear = 0;
		intIdTipoFondoFijo = 0;
		listaEgreso = null;
		intIdFondoFijo = 0;
		return strIniciar;
	}

	public void setStrIniciar(String strIniciar) {
		this.strIniciar = strIniciar;
	}

	public List<MovEgreso> getLstFondoFijo() {
		return lstFondoFijo;
	}

	public void setLstFondoFijo(List<MovEgreso> lstFondoFijo) {
		this.lstFondoFijo = lstFondoFijo;
	}

	public int getIntIdSucursal() {
		return intIdSucursal;
	}

	public void setIntIdSucursal(int intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}

	public int getIntYear() {
		return intYear;
	}

	public void setIntYear(int intYear) {
		this.intYear = intYear;
	}

	public int getIntIdTipoFondoFijo() {
		return intIdTipoFondoFijo;
	}

	public void setIntIdTipoFondoFijo(int intIdTipoFondoFijo) {
		this.intIdTipoFondoFijo = intIdTipoFondoFijo;
	}

	public List<SelectItem> getListYears() {
		return listYears;
	}

	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}

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

	public List<EgresoFondoFijo>  getListaEgreso() {
		return listaEgreso;
	}

	public void setListaEgreso(List<EgresoFondoFijo>  listaEgreso) {
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
			listYears = getListAnios(Constante.INT_INI_YEAR);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	public List<SelectItem> getListAnios(int intIniAnio) {
		List<SelectItem> listYears = new ArrayList<SelectItem>(); 
		try {
			int year=intIniAnio;
			int cont=0;

			for(int j=year; j<=Calendar.getInstance().get(Calendar.YEAR); j++){
				cont++;
			}			
			for(int i=0; i<cont; i++){
				listYears.add(i, new SelectItem(year));
				year--;
			}	
		} catch (Exception e) {
			log.error("Error en getListYears ---> "+e);
		}
		return listYears;
	}
	

	public void seleccionarSucursasl (ActionEvent event){
		intYear = 0;
		intIdTipoFondoFijo = 0;
		lstFondoFijo = null;
	}
	
	public void seleccionarAnio (ActionEvent event){
		intIdTipoFondoFijo = 0;
		lstFondoFijo = null;
	}
	
	public void obtenerFondoFijo (ActionEvent event){
		MovEgresoFacadeLocal objFacade;
		try {
			objFacade = (MovEgresoFacadeLocal) EJBFactory.getLocal(MovEgresoFacadeLocal.class);
			if(intYear ==0)
				intYear = Calendar.getInstance().get(Calendar.YEAR);
			lstFondoFijo = objFacade.getListFondoFijo(intIdSucursal, intYear, intIdTipoFondoFijo);
			System.out.println(lstFondoFijo.size());
		} catch (EJBFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void consultarEgreso (){
		System.out.println(intIdFondoFijo);
		EgresoFondoFijo objEgresoFondo = new EgresoFondoFijo();
		objEgresoFondo.setStrNroMovimiento("2014-10-1");
		objEgresoFondo.setStrConcepto("Texto de prueba");
		objEgresoFondo.setDblMontoReporte(new Double(10));
		listaEgreso =  new ArrayList<EgresoFondoFijo>();
		listaEgreso.add(objEgresoFondo);
		
	}
	
}