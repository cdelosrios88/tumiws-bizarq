package pe.com.tumi.report.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.estado.controller.EstadoCuentaController;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.EstadoCuentaComp;
//import pe.com.tumi.report.bean.DataBeanEstadoCuentaMovimientos;
import pe.com.tumi.report.engine.Reporteador;

public class MakerEstadoCuentaTabDetalle {
	protected static Logger log = Logger.getLogger(Reporteador.class);
	
	public List<EstadoCuentaComp> getDataBeanList() { //DataBeanEstadoCuentaPrestamos
//		List<EstadoCuentaComp> lstMovimientos = null;
		List<EstadoCuentaComp> dataBeanList = new ArrayList<EstadoCuentaComp>();
		EstadoCuentaController estCta = (EstadoCuentaController)getSessionBean("estadoCuentaController");
		
//		lstMovimientos = estCta.getListaEstadoCuentaComp();
//		try {
//			lstMovimientos = estCta.getListaEstadoCuentaComp();
//			if (lstMovimientos!=null && !lstMovimientos.isEmpty()) {
//				for (EstadoCuentaComp movimientos : lstMovimientos) {
//					movimientos.setStrMontoMovAportes(bigDecimalToString(movimientos.getBdTasaInteres()));
//					movimientos.setStrMontoMovPrestamos(bigDecimalToString(movimientos.getBdMontoTotal()));
//					movimientos.setStrMontoMovCreditos(bigDecimalToString(movimientos.getBdSaldoCredito()));
//					movimientos.setStrMontoMovInteres(bigDecimalToString(movimientos.getBdDiferencia()));
//					movimientos.setStrMontoMovMntCuenta(bigDecimalToString(movimientos.getBdUltimoEnvio()));
//					movimientos.setStrMontoMovActividad(bigDecimalToString(movimientos.getBdTasaInteres()));
//					movimientos.setStrMontoMovMulta(bigDecimalToString(movimientos.getBdMontoTotal()));
//					movimientos.setStrMontoMovFdoSepelio(bigDecimalToString(movimientos.getBdSaldoCredito()));
//					movimientos.setStrMontoMovFdoRetiro(bigDecimalToString(movimientos.getBdDiferencia()));
//					movimientos.setStrMontoMovCtaXPagar(bigDecimalToString(movimientos.getBdUltimoEnvio()));
//					movimientos.setStrMontoMovCtaXCobrar(bigDecimalToString(movimientos.getBdUltimoEnvio()));
//					movimientos.setStrSumatoriaFilas(bigDecimalToString(movimientos.getBdUltimoEnvio()));
//					dataBeanList.add(movimientos);
//				}
//			}	
//		} catch (Exception e) {
//			log.error("Error en getDataBeanList ---> "+e);
//		}
		dataBeanList = estCta.getListaEstadoCuentaComp();
		return dataBeanList;
	}
	
	public static String bigDecimalToString(BigDecimal big)	{
		String retorna = "";
		if (big==null) {
			retorna = "";
		}else{
			String s = big.toString();
			Integer r = s.indexOf(".");
			if (r == -1) {
				retorna = s+".00";
			}else{
				Integer largo = s.length();
				String decimal= s.substring(r+1, largo);
				if (decimal.length()==1) {
					retorna = s+"0";
				}else{
					retorna = s;
				}
			}
		}
		Integer r = retorna.indexOf("-");
		if (r != -1) {
			retorna = (new BigDecimal(retorna).multiply(BigDecimal.valueOf(-1))).toString();
		}
        return retorna;
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
