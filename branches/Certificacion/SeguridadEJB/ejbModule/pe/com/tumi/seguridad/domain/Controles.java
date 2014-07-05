package pe.com.tumi.seguridad.domain;


public class Controles {

	public static final String certificadoPanelGestor = "certificadoPanelGestor";
	public static final String certificadoPanelObservacionGestor = "certificadoPanelObservacionGestor";
	public static final String certificadoPanelCuentasPorCobrar = "certificadoPanelCuentasPorCobrar";
	public static final String certificadoPanelInterventor = "certificadoPanelInterventor";
	public static final String certificadoPanelInterventorObservacion = "certificadoPanelInterventorObservacion";
	public static final String certificadoInputDescuentos = "certificadoInputDescuentos";
	
	private Boolean panelGestorHabilitado = true;
	private Boolean panelObservacionGestorHabilitado = true;
	private Boolean panelCuentasPorCobrarHabilitado = true;
	private Boolean panelInterventorHabilitado = true;
	private Boolean panelInterventorObservacionHabilitado = true;
	private Boolean inputDescuentos = true;
	public Boolean getPanelGestorHabilitado() {
		return panelGestorHabilitado;
	}
	public void setPanelGestorHabilitado(Boolean panelGestorHabilitado) {
		this.panelGestorHabilitado = panelGestorHabilitado;
	}
	public Boolean getPanelObservacionGestorHabilitado() {
		return panelObservacionGestorHabilitado;
	}
	public void setPanelObservacionGestorHabilitado(
			Boolean panelObservacionGestorHabilitado) {
		this.panelObservacionGestorHabilitado = panelObservacionGestorHabilitado;
	}
	public Boolean getPanelCuentasPorCobrarHabilitado() {
		return panelCuentasPorCobrarHabilitado;
	}
	public void setPanelCuentasPorCobrarHabilitado(
			Boolean panelCuentasPorCobrarHabilitado) {
		this.panelCuentasPorCobrarHabilitado = panelCuentasPorCobrarHabilitado;
	}
	public Boolean getPanelInterventorHabilitado() {
		return panelInterventorHabilitado;
	}
	public void setPanelInterventorHabilitado(Boolean panelInterventorHabilitado) {
		this.panelInterventorHabilitado = panelInterventorHabilitado;
	}
	public Boolean getPanelInterventorObservacionHabilitado() {
		return panelInterventorObservacionHabilitado;
	}
	public void setPanelInterventorObservacionHabilitado(
			Boolean panelInterventorObservacionHabilitado) {
		this.panelInterventorObservacionHabilitado = panelInterventorObservacionHabilitado;
	}
	public static String getCertificadopanelgestor() {
		return certificadoPanelGestor;
	}
	public static String getCertificadopanelobservaciongestor() {
		return certificadoPanelObservacionGestor;
	}
	public static String getCertificadopanelcuentasporcobrar() {
		return certificadoPanelCuentasPorCobrar;
	}
	public static String getCertificadopanelinterventor() {
		return certificadoPanelInterventor;
	}
	public static String getCertificadopanelinterventorobservacion() {
		return certificadoPanelInterventorObservacion;
	}
	public Boolean getInputDescuentos() {
		return inputDescuentos;
	}
	public void setInputDescuentos(Boolean inputDescuentos) {
		this.inputDescuentos = inputDescuentos;
	}
	public static String getCertificadoinputdescuentos() {
		return certificadoInputDescuentos;
	}
	
	
}
