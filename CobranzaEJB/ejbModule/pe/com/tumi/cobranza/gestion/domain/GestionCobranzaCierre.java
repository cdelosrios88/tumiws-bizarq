package pe.com.tumi.cobranza.gestion.domain;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.ws.dcs.vri.common.impl.TimeScaler;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class GestionCobranzaCierre extends TumiDomain{
	
	private GestionCobranzaCierreId id;
	
    private Integer intEmpresaGestor;
    private Integer intPersonaGestor;
    private Integer intItemGestorCobSuc;
    private String  strObservacion;
    private Date    dtUltFechaCierre;
    
    private List<GestionCobranza> listaGestionCobranza;
    
    
    public GestionCobranzaCierre(){
    	setId(new GestionCobranzaCierreId());
    }
    
	public GestionCobranzaCierreId getId() {
		return id;
	}
	public void setId(GestionCobranzaCierreId id) {
		this.id = id;
	}
	public Integer getIntEmpresaGestor() {
		return intEmpresaGestor;
	}
	public void setIntEmpresaGestor(Integer intEmpresaGestor) {
		this.intEmpresaGestor = intEmpresaGestor;
	}
	public Integer getIntPersonaGestor() {
		return intPersonaGestor;
	}
	public void setIntPersonaGestor(Integer intPersonaGestor) {
		this.intPersonaGestor = intPersonaGestor;
	}
	public Integer getIntItemGestorCobSuc() {
		return intItemGestorCobSuc;
	}
	public void setIntItemGestorCobSuc(Integer intItemGestorCobSuc) {
		this.intItemGestorCobSuc = intItemGestorCobSuc;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}

	public Date getDtUltFechaCierre() {
		return dtUltFechaCierre;
	}

	public void setDtUltFechaCierre(Date dtUltFechaCierre) {
		this.dtUltFechaCierre = dtUltFechaCierre;
	}

	public List<GestionCobranza> getListaGestionCobranza() {
		return listaGestionCobranza;
	}

	public void setListaGestionCobranza(List<GestionCobranza> listaGestionCobranza) {
		this.listaGestionCobranza = listaGestionCobranza;
	}

    
    
}
