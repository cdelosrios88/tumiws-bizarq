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

import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaResumenPrestamos;
import pe.com.tumi.report.engine.Reporteador;
import pe.com.tumi.seguridad.controller.EstadoCuentaController;

public class MakerEstadoCuentaTabResumen {
	protected static Logger log = Logger.getLogger(Reporteador.class);
	NumberFormat formato;
	
	public List<DataBeanEstadoCuentaResumenPrestamos> getDataBeanList() {
		List<DataBeanEstadoCuentaResumenPrestamos> lstPrestamos = null;
		List<DataBeanEstadoCuentaResumenPrestamos> dataBeanList = new ArrayList<DataBeanEstadoCuentaResumenPrestamos>();
		EstadoCuentaController estCta = (EstadoCuentaController)getSessionBean("estadoCuentaController") ;
		try {
			lstPrestamos = estCta.getLstDataBeanEstadoCuentaResumenPrestamos();
			if (lstPrestamos!=null && !lstPrestamos.isEmpty()) {
				for (DataBeanEstadoCuentaResumenPrestamos estCtaPrestamos : lstPrestamos) {
					estCtaPrestamos.setStrTasaInteres(bigDecimalToString(estCtaPrestamos.getBdTasaInteres()));//estCtaPrestamos.getBdTasaInteres()
					estCtaPrestamos.setStrMontoTotal(bigDecimalToString(estCtaPrestamos.getBdMontoTotal()));
					estCtaPrestamos.setStrSaldoCredito(bigDecimalToString(estCtaPrestamos.getBdSaldoCredito()));
					estCtaPrestamos.setStrDiferencia(bigDecimalToString(estCtaPrestamos.getBdDiferencia()));
					estCtaPrestamos.setStrUltimoEnvio(bigDecimalToString(estCtaPrestamos.getBdUltimoEnvio()));
					dataBeanList.add(estCtaPrestamos);
				}
			}			
		} catch (Exception e) {
			log.error("Error en getDataBeanList ---> "+e);
		}
		return dataBeanList;
	}
	
	public String bigDecimalToString(BigDecimal bdMonto)	{
		String strMonto = "";
		if (bdMonto!=null) {
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(','); 
			formato = new DecimalFormat("#,##0.00",otherSymbols);
			strMonto = formato.format(bdMonto);
		}				
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
