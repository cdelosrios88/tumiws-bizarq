package pe.com.tumi.cobranza.gestion.domain;

import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class GestionCobranzaEnt extends TumiDomain{
	
	private GestionCobranzaEntId id;
	private Integer intNivel;
	private Integer intCodigo;
	private Integer intEstadoCod;
	
	private Estructura estructura;
	
	private Boolean isDeleted;
	
	public GestionCobranzaEnt()
	{
		super();
		setId(new GestionCobranzaEntId());
	}
	

	
	public GestionCobranzaEntId getId() {
		return id;
	}



	public void setId(GestionCobranzaEntId id) {
		this.id = id;
	}



	public Integer getIntNivel() {
		return intNivel;
	}
	public void setIntNivel(Integer intNivel) {
		this.intNivel = intNivel;
	}
	public Integer getIntCodigo() {
		return intCodigo;
	}
	public void setIntCodigo(Integer intCodigo) {
		this.intCodigo = intCodigo;
	}
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public Estructura getEstructura() {
		return estructura;
	}

	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}



	public Boolean getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	

}
