package pe.com.tumi.cobranza.planilla.domain;

import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class SolicitudCtaCte extends TumiDomain{

	private SolicitudCtaCteId id;
	 
	private Integer	intPersEmpresa	;
	private Integer	intPersPersona	;
	private Integer	intCsocCuenta	;
	private Integer	intSucuIdsucursalsocio	;
	private Integer	intSudeIdsubsucursalsocio	;
	private Integer	intPeriodo	;
	private Integer	intParaTipomodalidad	;
	private Integer	intParaTipo	;
	private Integer	intMaeItemarchivo	;
	private Integer	intMaeItemhistorico ;
	private Integer	intParaTipo1	;
	private Integer	intMaeItemarchivo1	;
	private Integer	intMaeItemhistorico1 ;
	private Integer	intParaTipo2	;
	private Integer	intMaeItemarchivo2	;
	private Integer	intMaeItemhistorico2 ;
	
	
	//Auxiliar 
	private Natural socio;
	private Natural usuario;
	
	private Juridica sucursal;
	private Cuenta cuenta;
	SolicitudCtaCteTipo    solCtaCteTipo;
	SolicitudCtaCteTipo    solCtaCteTipoTemp;
	EstadoSolicitudCtaCte  estSolCtaCte;
	List <EstadoSolicitudCtaCte> listaEstSolCtaCte;
	List <SolicitudCtaCteTipo>   listaSolCtaCteTipo;
	List <PersonaRol> listaPersonaSocioRol;
	
	private Integer intEstadoSolCtaCte;
	
	private Documento documentoSocio;
     
	private Integer nroEstadosSolicitud;
	
	public SolicitudCtaCte(){
		id = new SolicitudCtaCteId();
		solCtaCteTipo =  new SolicitudCtaCteTipo();
		solCtaCteTipoTemp =  new SolicitudCtaCteTipo();
		estSolCtaCte  =  new EstadoSolicitudCtaCte();
	}
	
	public SolicitudCtaCteId getId() {
		return id;
	}
	public void setId(SolicitudCtaCteId id) {
		this.id = id;
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
	public Integer getIntSucuIdsucursalsocio() {
		return intSucuIdsucursalsocio;
	}
	public void setIntSucuIdsucursalsocio(Integer intSucuIdsucursalsocio) {
		this.intSucuIdsucursalsocio = intSucuIdsucursalsocio;
	}
	public Integer getIntSudeIdsubsucursalsocio() {
		return intSudeIdsubsucursalsocio;
	}
	public void setIntSudeIdsubsucursalsocio(Integer intSudeIdsubsucursalsocio) {
		this.intSudeIdsubsucursalsocio = intSudeIdsubsucursalsocio;
	}
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}
	public Integer getIntParaTipomodalidad() {
		return intParaTipomodalidad;
	}
	public void setIntParaTipomodalidad(Integer intParaTipomodalidad) {
		this.intParaTipomodalidad = intParaTipomodalidad;
	}
	public Integer getIntParaTipo() {
		return intParaTipo;
	}
	public void setIntParaTipo(Integer intParaTipo) {
		this.intParaTipo = intParaTipo;
	}
	public Integer getIntMaeItemarchivo() {
		return intMaeItemarchivo;
	}
	public void setIntMaeItemarchivo(Integer intMaeItemarchivo) {
		this.intMaeItemarchivo = intMaeItemarchivo;
	}
	public Integer getIntMaeItemhistorico() {
		return intMaeItemhistorico;
	}
	public void setIntMaeItemhistorico(Integer intMaeItemhistorico) {
		this.intMaeItemhistorico = intMaeItemhistorico;
	}
	public Natural getSocio() {
		return socio;
	}
	public void setSocio(Natural socio) {
		this.socio = socio;
	}
	public Juridica getSucursal() {
		return sucursal;
	}
	public void setSucursal(Juridica sucursal) {
		this.sucursal = sucursal;
	}
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	public List<EstadoSolicitudCtaCte> getListaEstSolCtaCte() {
		return listaEstSolCtaCte;
	}
	public void setListaEstSolCtaCte(List<EstadoSolicitudCtaCte> listaEstSolCtaCte) {
		this.listaEstSolCtaCte = listaEstSolCtaCte;
	}
	public List<SolicitudCtaCteTipo> getListaSolCtaCteTipo() {
		return listaSolCtaCteTipo;
	}
	public void setListaSolCtaCteTipo(List<SolicitudCtaCteTipo> listaSolCtaCteTipo) {
		this.listaSolCtaCteTipo = listaSolCtaCteTipo;
	}


	public SolicitudCtaCteTipo getSolCtaCteTipo() {
		return solCtaCteTipo;
	}


	public void setSolCtaCteTipo(SolicitudCtaCteTipo solCtaCteTipo) {
		this.solCtaCteTipo = solCtaCteTipo;
	}


	public EstadoSolicitudCtaCte getEstSolCtaCte() {
		return estSolCtaCte;
	}


	public void setEstSolCtaCte(EstadoSolicitudCtaCte estSolCtaCte) {
		this.estSolCtaCte = estSolCtaCte;
	}

	public Natural getUsuario() {
		return usuario;
	}

	public void setUsuario(Natural usuario) {
		this.usuario = usuario;
	}

	public Documento getDocumentoSocio() {
		return documentoSocio;
	}

	public void setDocumentoSocio(Documento documentoSocio) {
		this.documentoSocio = documentoSocio;
	}

	public List<PersonaRol> getListaPersonaSocioRol() {
		return listaPersonaSocioRol;
	}

	public void setListaPersonaSocioRol(List<PersonaRol> listaPersonaSocioRol) {
		this.listaPersonaSocioRol = listaPersonaSocioRol;
	}

	public Integer getNroEstadosSolicitud() {
		return nroEstadosSolicitud;
	}

	public void setNroEstadosSolicitud(Integer nroEstadosSolicitud) {
		this.nroEstadosSolicitud = nroEstadosSolicitud;
	}

	public Integer getIntEstadoSolCtaCte() {
		return intEstadoSolCtaCte;
	}

	public void setIntEstadoSolCtaCte(Integer intEstadoSolCtaCte) {
		this.intEstadoSolCtaCte = intEstadoSolCtaCte;
	}

	public Integer getIntParaTipo1() {
		return intParaTipo1;
	}

	public void setIntParaTipo1(Integer intParaTipo1) {
		this.intParaTipo1 = intParaTipo1;
	}

	public Integer getIntMaeItemarchivo1() {
		return intMaeItemarchivo1;
	}

	public void setIntMaeItemarchivo1(Integer intMaeItemarchivo1) {
		this.intMaeItemarchivo1 = intMaeItemarchivo1;
	}

	public Integer getIntMaeItemhistorico1() {
		return intMaeItemhistorico1;
	}

	public void setIntMaeItemhistorico1(Integer intMaeItemhistorico1) {
		this.intMaeItemhistorico1 = intMaeItemhistorico1;
	}

	public Integer getIntParaTipo2() {
		return intParaTipo2;
	}

	public void setIntParaTipo2(Integer intParaTipo2) {
		this.intParaTipo2 = intParaTipo2;
	}

	public Integer getIntMaeItemarchivo2() {
		return intMaeItemarchivo2;
	}

	public void setIntMaeItemarchivo2(Integer intMaeItemarchivo2) {
		this.intMaeItemarchivo2 = intMaeItemarchivo2;
	}

	public Integer getIntMaeItemhistorico2() {
		return intMaeItemhistorico2;
	}

	public void setIntMaeItemhistorico2(Integer intMaeItemhistorico2) {
		this.intMaeItemhistorico2 = intMaeItemhistorico2;
	}

	public SolicitudCtaCteTipo getSolCtaCteTipoTemp() {
		return solCtaCteTipoTemp;
	}

	public void setSolCtaCteTipoTemp(SolicitudCtaCteTipo solCtaCteTipoTemp) {
		this.solCtaCteTipoTemp = solCtaCteTipoTemp;
	}	
	
	
	
	
	
}
