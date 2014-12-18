package pe.com.tumi.fondosfijos.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.CommonUtils;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.UtilManagerReport;
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
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private String 		mensajeOperacion;
	
	TablaFacadeRemote tablaFacade;
	MovEgresoFacadeLocal movEgresoFacade;
	
	public boolean isMostrarMensajeExito() {
		return mostrarMensajeExito;
	}

	public void setMostrarMensajeExito(boolean mostrarMensajeExito) {
		this.mostrarMensajeExito = mostrarMensajeExito;
	}

	public boolean isMostrarMensajeError() {
		return mostrarMensajeError;
	}

	public void setMostrarMensajeError(boolean mostrarMensajeError) {
		this.mostrarMensajeError = mostrarMensajeError;
	}

	public String getMensajeOperacion() {
		return mensajeOperacion;
	}

	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}

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
		intIdFondoFijo = -1;
		mostrarMensaje(Boolean.TRUE, "");
		return "";
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
			tablaFacade =  (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			movEgresoFacade = (MovEgresoFacadeLocal) EJBFactory.getLocal(MovEgresoFacadeLocal.class);;
			lstTipoFondoFijo = tablaFacade.getListaTablaPorAgrupamientoA(Constante.PARAM_T_FONDOSFIJOS, Constante.PARAM_STR_AGRUP_B);
			listYears = CommonUtils.getListAnios(Constante.INT_INI_YEAR);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
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
	public void seleccionarFondoFijo (ActionEvent event) {
		System.out.println(intIdFondoFijo);
	}
	public void obtenerFondoFijo (ActionEvent event){
		try {
			if(intYear ==0)
				intYear = Calendar.getInstance().get(Calendar.YEAR);
			lstFondoFijo = movEgresoFacade.getListFondoFijo(intIdSucursal, intYear, intIdTipoFondoFijo);
			intIdFondoFijo  = -1;
			lstFondoFijo = movEgresoFacade.getListFondoFijo(intIdSucursal, intYear, intIdTipoFondoFijo);
			System.out.println(lstFondoFijo.size());
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
	}
	
	public void printFondoFijoDetalle(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		MovEgreso objMovEgreso = null;
		try {
			if(lstFondoFijo!=null && !lstFondoFijo.isEmpty()){
				for(MovEgreso movEgreso: lstFondoFijo){
					if(movEgreso.getIntRow()==intIdFondoFijo){
						objMovEgreso =  movEgreso;
						break;
					}
				}
			}
			
			if(objMovEgreso!=null){
				objMovEgreso = movEgresoFacade.getListEgresoById(objMovEgreso);
				objMovEgreso.setLstMovEgresoDetalle(movEgresoFacade.getListEgresoDetalleById(objMovEgreso));
				
				parametro.put("P_NROEGRESO", objMovEgreso.getStrNroEgreso());
				parametro.put("P_SUCURSAL", objMovEgreso.getStrSucursal());
				parametro.put("P_FECHAEGRESO", objMovEgreso.getStrFechaEgreso());
				parametro.put("P_PAGOEGRESO", objMovEgreso.getStrFormaPago());
				parametro.put("P_MONEDA", objMovEgreso.getStrMoneda());
				parametro.put("P_MONTO", objMovEgreso.getBdMonto());
				parametro.put("P_DESCMONTO", objMovEgreso.getStrDescMonto());
				parametro.put("P_BANCO", objMovEgreso.getStrBanco());
				parametro.put("P_NROCHEQUE", objMovEgreso.getStrNroCheque());
				parametro.put("P_ENTREGUEA", objMovEgreso.getStrPersonaEntrega());
				parametro.put("P_NROASIENTO", objMovEgreso.getStrNroAsiento());
				parametro.put("P_CONCEPTO", objMovEgreso.getStrConcepto());
				parametro.put("P_DNIENTREGUE", objMovEgreso.getStrNroDocEntrega());
				parametro.put("P_USUARIO", objMovEgreso.getStrNombreUsuario());
				parametro.put("P_FECHAHORA", objMovEgreso.getStrFechaHora());
			}
			
			strNombreReporte = "egresoDet";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(objMovEgreso.getLstMovEgresoDetalle()), 
					Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error("Error en imprimirReporteIngresos ---> "+e);
		}
	}
	
	public void consultarEgreso (){
		
		if(intIdFondoFijo < 0){
			mostrarMensaje(Boolean.FALSE, "Por favor seleccionar fondo fijo.");
		}else {
			mostrarMensaje(Boolean.TRUE, "");
			System.out.println(intIdFondoFijo);
			MovEgreso objMovEgreso = new MovEgreso();
			objMovEgreso = lstFondoFijo.get(intIdFondoFijo);
			MovEgresoFacadeLocal objFacade;
			try {
				objFacade = (MovEgresoFacadeLocal) EJBFactory.getLocal(MovEgresoFacadeLocal.class);
				listaEgreso = objFacade.getEgresos(objMovEgreso);
			} catch (EJBFactoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void mostrarMensaje(boolean exito, String mensaje){
		if(exito){
			mostrarMensajeExito = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mensajeOperacion = mensaje;
		}else{
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.TRUE;
			mensajeOperacion = mensaje;
		}
	}
}