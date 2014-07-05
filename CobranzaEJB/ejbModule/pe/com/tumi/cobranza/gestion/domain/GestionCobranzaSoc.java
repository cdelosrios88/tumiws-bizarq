package pe.com.tumi.cobranza.gestion.domain;

import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSocId;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;

public class GestionCobranzaSoc extends TumiDomain{
	
	private GestionCobranzaSocId id;
	private Integer intTipoPersonaOpe;
	private Integer intPersEmpresa;
	private Integer intPersPersona;
	private Integer intCsocCuenta;
	private Integer intCserItemExp;
	private Integer intCserdeteItemExp;
	private Integer intParaEstado;
	private Boolean isDeleted;
    private Socio socio;
    private SocioComp socioComp;
    
    //AUTOR Y FECHA CREACION: JCHAVEZ / 04.09.2013 
    private GestionCobranza gestionCobranza;

	public GestionCobranzaSocId getId() {
		return id;
	}

	public void setId(GestionCobranzaSocId id) {
		this.id = id;
	}

	public Integer getIntTipoPersonaOpe() {
		return intTipoPersonaOpe;
	}

	public void setIntTipoPersonaOpe(Integer intTipoPersonaOpe) {
		this.intTipoPersonaOpe = intTipoPersonaOpe;
	}

	public Integer getIntPersEmpresa() {
		return intPersEmpresa;
	}

	public void setIntPersEmpresa(Integer intPersEmpresa) {
		this.intPersEmpresa = intPersEmpresa;
	}

	public Integer getIntPersPersona() {
		return intPersPersona;
	}

	public void setIntPersPersona(Integer intPersPersona) {
		this.intPersPersona = intPersPersona;
	}

	public Integer getIntCsocCuenta() {
		return intCsocCuenta;
	}

	public void setIntCsocCuenta(Integer intCsocCuenta) {
		this.intCsocCuenta = intCsocCuenta;
	}

	public Integer getIntCserItemExp() {
		return intCserItemExp;
	}

	public void setIntCserItemExp(Integer intCserItemExp) {
		this.intCserItemExp = intCserItemExp;
	}

	public Integer getIntCserdeteItemExp() {
		return intCserdeteItemExp;
	}

	public void setIntCserdeteItemExp(Integer intCserdeteItemExp) {
		this.intCserdeteItemExp = intCserdeteItemExp;
	}

	public Integer getIntParaEstado() {
		return intParaEstado;
	}

	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public SocioComp getSocioComp() {
		return socioComp;
	}

	public void setSocioComp(SocioComp socioComp) {
		this.socioComp = socioComp;
	}

	public GestionCobranza getGestionCobranza() {
		return gestionCobranza;
	}

	public void setGestionCobranza(GestionCobranza gestionCobranza) {
		this.gestionCobranza = gestionCobranza;
	}
}
