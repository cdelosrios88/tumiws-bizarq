package pe.com.tumi.report.business;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.estado.controller.EstadoCuentaController;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.persona.empresa.domain.Juridica;

import pe.com.tumi.report.engine.Reporteador;

public class MakerEstadoCuentaCabecera {
	
	protected static Logger log = Logger.getLogger(Reporteador.class);
	
	public List<Juridica> getDataBeanListSucursales() {
		List<Juridica> lstSucursales = null;
		List<Juridica> dataBeanList = new ArrayList<Juridica>();
		EstadoCuentaController estCta = (EstadoCuentaController)getSessionBean("estadoCuentaController") ;
		try {
			lstSucursales = estCta.getListaRazonSocialSucursal();
			if (lstSucursales!=null && !lstSucursales.isEmpty()) {
				for (Juridica sucu : lstSucursales) {
					dataBeanList.add(sucu);
				}
			}			
		} catch (Exception e) {
			log.error("Error en getDataBeanListSucursales ---> "+e);
		}

		return dataBeanList;
		
	}
	
	public List<Tabla> getDataBeanListUnidadEjec() {		
		List<Tabla> lstUnidadEjec= null;
		List<Tabla> dataBeanList = new ArrayList<Tabla>();
		EstadoCuentaController estCta = (EstadoCuentaController)getSessionBean("estadoCuentaController") ;
		try {
			lstUnidadEjec = estCta.getListaUnidadEjecutora();
			if (lstUnidadEjec!=null && !lstUnidadEjec.isEmpty()) {
				for (Tabla unidadEjec : lstUnidadEjec) {
					dataBeanList.add(unidadEjec);
				}
			}
		} catch (Exception e) {
			log.error("Error en getDataBeanListUnidadEjec ---> "+e);
		}
		return dataBeanList;
		
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
