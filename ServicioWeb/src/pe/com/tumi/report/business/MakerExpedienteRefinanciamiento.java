package pe.com.tumi.report.business;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import pe.com.tumi.report.bean.DataBeanExpedienteRefinanciamiento;
import pe.com.tumi.servicio.refinanciamiento.controller.SolicitudRefinanController;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;

/**
 * 
 * @author CGD
 *
 */
public class MakerExpedienteRefinanciamiento {

	
	public List<DataBeanExpedienteRefinanciamiento> getDataBeanList() {
		List<ExpedienteCreditoComp> lstRefinanciamientos = null;
		List<DataBeanExpedienteRefinanciamiento> dataBeanList = new ArrayList<DataBeanExpedienteRefinanciamiento>();
		SolicitudRefinanController solicitudRefinan = (SolicitudRefinanController)getSessionBean("solicitudRefinanController") ;
		
		lstRefinanciamientos = solicitudRefinan.getListaExpedienteCreditoComp();
		if(lstRefinanciamientos != null && !lstRefinanciamientos.isEmpty()){
			for (ExpedienteCreditoComp refinanciamiento : lstRefinanciamientos) {
				DataBeanExpedienteRefinanciamiento refinan = new DataBeanExpedienteRefinanciamiento();
				refinan.setStrCuenta(refinanciamiento.getSocioComp().getCuenta().getStrNumeroCuenta());
				refinan.setStrCuotaFija(refinanciamiento.getBdCuotaFija().toString());
				refinan.setStrCuotas(refinanciamiento.getExpedienteCredito().getIntNumeroCuota().toString());
				//refinan.setStrEstado(strEstado);
				refinan.setStrFecAutorizacion(refinanciamiento.getStrFechaAutorizacion());
				refinan.setStrFecRequisito(refinanciamiento.getStrFechaRequisito());
				refinan.setStrFecSolicitud(refinanciamiento.getStrFechaSolicitud());
				refinan.setStrMontoCredito(refinanciamiento.getExpedienteCredito().getBdMontoTotal().toString());
				refinan.setStrNombreCompleto(refinanciamiento.getSocioComp().getPersona().getNatural().getStrApellidoPaterno()+" "+ refinanciamiento.getSocioComp().getPersona().getNatural().getStrApellidoMaterno() +", "+refinanciamiento.getSocioComp().getPersona().getNatural().getStrNombres());
				refinan.setStrNroSolicitud(refinanciamiento.getExpedienteCredito().getId().getIntItemExpediente()+"-"+refinanciamiento.getExpedienteCredito().getId().getIntItemDetExpediente());
				dataBeanList.add(refinan);
			}
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
