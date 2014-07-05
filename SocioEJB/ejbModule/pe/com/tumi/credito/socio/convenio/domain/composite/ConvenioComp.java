package pe.com.tumi.credito.socio.convenio.domain.composite;

import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConvenioComp extends TumiDomain {
	private Adenda adenda;
	private EstructuraDetalle estructuraDetalle;
	private String strNivel;
	private String strModalidad;
	private Integer intRetencionPlanilla;
	private Integer intDonacionRegalia;
	private Integer intVigenciaConvenio;
	private Integer intDocAdjunto;
	private Integer intDiferenciaFecha;
	
	public Adenda getAdenda() {
		return adenda;
	}
	public void setAdenda(Adenda adenda) {
		this.adenda = adenda;
	}
	public EstructuraDetalle getEstructuraDetalle() {
		return estructuraDetalle;
	}
	public void setEstructuraDetalle(EstructuraDetalle estructuraDetalle) {
		this.estructuraDetalle = estructuraDetalle;
	}
	public String getStrNivel() {
		return strNivel;
	}
	public void setStrNivel(String strNivel) {
		this.strNivel = strNivel;
	}
	public String getStrModalidad() {
		return strModalidad;
	}
	public void setStrModalidad(String strModalidad) {
		this.strModalidad = strModalidad;
	}
	public Integer getIntRetencionPlanilla() {
		return intRetencionPlanilla;
	}
	public void setIntRetencionPlanilla(Integer intRetencionPlanilla) {
		this.intRetencionPlanilla = intRetencionPlanilla;
	}
	public Integer getIntDonacionRegalia() {
		return intDonacionRegalia;
	}
	public void setIntDonacionRegalia(Integer intDonacionRegalia) {
		this.intDonacionRegalia = intDonacionRegalia;
	}
	public Integer getIntVigenciaConvenio() {
		return intVigenciaConvenio;
	}
	public void setIntVigenciaConvenio(Integer intVigenciaConvenio) {
		this.intVigenciaConvenio = intVigenciaConvenio;
	}
	public Integer getIntDocAdjunto() {
		return intDocAdjunto;
	}
	public void setIntDocAdjunto(Integer intDocAdjunto) {
		this.intDocAdjunto = intDocAdjunto;
	}
	public Integer getIntDiferenciaFecha() {
		return intDiferenciaFecha;
	}
	public void setIntDiferenciaFecha(Integer intDiferenciaFecha) {
		this.intDiferenciaFecha = intDiferenciaFecha;
	}
}
