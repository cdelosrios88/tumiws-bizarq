package pe.com.tumi.credito.socio.convenio.domain.composite;

import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class HojaPlaneamientoComp extends TumiDomain {
	private	Adenda adenda;
	private ConvenioEstructuraDetalle convenioEstructuraDetalle;
	private EstructuraDetalle estructuraDetalle;
	private String strNivel;
	private String strModalidad;
	private Integer intIndeterminado;
	private Integer intDocAdjunto;
	private Integer intCartaAutorizacion;
	
	//Otros complementos
	private Integer intDiferenciaFecha;
	
	public Adenda getAdenda() {
		return adenda;
	}
	public void setAdenda(Adenda adenda) {
		this.adenda = adenda;
	}
	public ConvenioEstructuraDetalle getConvenioEstructuraDetalle() {
		return convenioEstructuraDetalle;
	}
	public void setConvenioEstructuraDetalle(
			ConvenioEstructuraDetalle convenioEstructuraDetalle) {
		this.convenioEstructuraDetalle = convenioEstructuraDetalle;
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
	public Integer getIntIndeterminado() {
		return intIndeterminado;
	}
	public void setIntIndeterminado(Integer intIndeterminado) {
		this.intIndeterminado = intIndeterminado;
	}
	public Integer getIntDocAdjunto() {
		return intDocAdjunto;
	}
	public void setIntDocAdjunto(Integer intDocAdjunto) {
		this.intDocAdjunto = intDocAdjunto;
	}
	public Integer getIntCartaAutorizacion() {
		return intCartaAutorizacion;
	}
	public void setIntCartaAutorizacion(Integer intCartaAutorizacion) {
		this.intCartaAutorizacion = intCartaAutorizacion;
	}
	public Integer getIntDiferenciaFecha() {
		return intDiferenciaFecha;
	}
	public void setIntDiferenciaFecha(Integer intDiferenciaFecha) {
		this.intDiferenciaFecha = intDiferenciaFecha;
	}
}
