package pe.com.tumi.cobranza.gestion.domain;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.ws.dcs.vri.common.impl.TimeScaler;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class DocumentoCobranza extends TumiDomain{
	
	private DocumentoCobranzaId id;
    
	private String strNombre;	
	private Integer intParaEstado;
	private Integer intParaTipoCobranza;
   
	public DocumentoCobranza(){
		  setId(new DocumentoCobranzaId());
	}

	public DocumentoCobranzaId getId() {
		return id;
	}
	
	public void setId(DocumentoCobranzaId id) {
		this.id = id;
	}
	
	public String getStrNombre() {
		return strNombre;
	}
	
	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}

	public Integer getIntParaEstado() {
		return intParaEstado;
	}

	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}

	public Integer getIntParaTipoCobranza() {
		return intParaTipoCobranza;
	}

	public void setIntParaTipoCobranza(Integer intParaTipoCobranza) {
		this.intParaTipoCobranza = intParaTipoCobranza;
	}
   
	
}
