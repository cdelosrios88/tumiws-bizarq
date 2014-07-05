package pe.com.tumi.report.business;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


//import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaComp;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaDetalleMovimiento;
import pe.com.tumi.report.engine.Reporteador;
import pe.com.tumi.seguridad.controller.EstadoCuentaController;

public class MakerEstadoCuentaTabDetalle {
	protected static Logger log = Logger.getLogger(Reporteador.class);
	NumberFormat formato;
	
	public List<DataBeanEstadoCuentaDetalleMovimiento> getDataBeanList() { 
//		List<DataBeanEstadoCuentaComp> dataBeanList = new ArrayList<DataBeanEstadoCuentaComp>();
		List<DataBeanEstadoCuentaDetalleMovimiento> dataBeanList = new ArrayList<DataBeanEstadoCuentaDetalleMovimiento>();
		EstadoCuentaController estCta = (EstadoCuentaController)getSessionBean("estadoCuentaController");
		if (estCta.getLstDetalleMovimiento()!=null && !estCta.getLstDetalleMovimiento().isEmpty()) {
			for (DataBeanEstadoCuentaDetalleMovimiento lista : estCta.getLstDetalleMovimiento()) {
				lista.setStrBdMontoAporte(convertirMontos(lista.getBdMontoAporte()));
				lista.setStrBdMontoMantenimiento(convertirMontos(lista.getBdMontoMantenimiento()));
				lista.setStrBdMontoFondoSepelio(convertirMontos(lista.getBdMontoFondoSepelio()));
				lista.setStrBdMontoFondoRetiro(convertirMontos(lista.getBdMontoFondoRetiro()));
				lista.setStrBdMontoPrestamo(convertirMontos(lista.getBdMontoPrestamo()));
				lista.setStrBdMontoCredito(convertirMontos(lista.getBdMontoCredito()));
				lista.setStrBdMontoActividad(convertirMontos(lista.getBdMontoActividad()));
				lista.setStrBdMontoMulta(convertirMontos(lista.getBdMontoMulta()));
				lista.setStrBdMontoInteres(convertirMontos(lista.getBdMontoInteres()));
				lista.setStrBdMontoCtaPorPagar(convertirMontos(lista.getBdMontoCtaPorPagar()));
				lista.setStrBdMontoCtaPorCobrar(convertirMontos(lista.getBdMontoCtaPorCobrar()));
				lista.setStrBdSumaMontoFila(convertirMontos(lista.getBdSumaMontoFila()));
			}
		}
		dataBeanList = estCta.getLstDetalleMovimiento();
		return dataBeanList;
	}
	
	public String convertirMontos(BigDecimal bdMonto){
		String strMonto = "";
		//Formato de nro....
		if (bdMonto!=null) {
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(','); 
			formato = new DecimalFormat("#,##0.00",otherSymbols);
			strMonto = formato.format(bdMonto);
		}else return strMonto;

        return strMonto;
	}	
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}

	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
}
