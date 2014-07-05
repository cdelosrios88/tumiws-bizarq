package pe.com.tumi.credito.socio.estructura.domain.composite;

import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class ConvenioEstructuraDetalleComp extends TumiDomain {
	private Estructura estructura;
	private EstructuraDetalle estructuraDetalle;
	private ConvenioEstructuraDetalle convenioEstructuraDetalle;
	private Adenda adenda;
	private String strUnidadEjecutora;
	private String strDependencia;
	private String strCsvPkModalidadTipoSocio;
	private String strModalidadTipoSocio;
	
	//Getters y Setters
	public Estructura getEstructura() {
		return estructura;
	}
	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}
	public EstructuraDetalle getEstructuraDetalle() {
		return estructuraDetalle;
	}
	public void setEstructuraDetalle(EstructuraDetalle estructuraDetalle) {
		this.estructuraDetalle = estructuraDetalle;
	}
	public ConvenioEstructuraDetalle getConvenioEstructuraDetalle() {
		return convenioEstructuraDetalle;
	}
	public void setConvenioEstructuraDetalle(
			ConvenioEstructuraDetalle convenioEstructuraDetalle) {
		this.convenioEstructuraDetalle = convenioEstructuraDetalle;
	}
	public Adenda getAdenda() {
		return adenda;
	}
	public void setAdenda(Adenda adenda) {
		this.adenda = adenda;
	}
	public String getStrUnidadEjecutora() {
		return strUnidadEjecutora;
	}
	public void setStrUnidadEjecutora(String strUnidadEjecutora) {
		this.strUnidadEjecutora = strUnidadEjecutora;
	}
	public String getStrDependencia() {
		return strDependencia;
	}
	public void setStrDependencia(String strDependencia) {
		this.strDependencia = strDependencia;
	}
	public String getStrCsvPkModalidadTipoSocio() {
		return strCsvPkModalidadTipoSocio;
	}
	public void setStrCsvPkModalidadTipoSocio(String strCsvPkModalidadTipoSocio) {
		this.strCsvPkModalidadTipoSocio = strCsvPkModalidadTipoSocio;
	}
	public String getStrModalidadTipoSocio() {
		return strModalidadTipoSocio;
	}
	public void setStrModalidadTipoSocio(String strModalidadTipoSocio) {
		this.strModalidadTipoSocio = strModalidadTipoSocio;
	}
}
