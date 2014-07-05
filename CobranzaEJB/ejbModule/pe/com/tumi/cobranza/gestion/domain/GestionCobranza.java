package pe.com.tumi.cobranza.gestion.domain;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.ws.dcs.vri.common.impl.TimeScaler;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Persona;

public class GestionCobranza extends TumiDomain{
	
	private GestionCobranzaId id;
	
    private Integer  intItemGestionCobranza;
	private Integer  intTipoGestionCobCod;
	private Integer  intSubTipoGestionCobCod;
	private GestorCobranza gestorCobranza;
	private Persona  persona; 
	private DocumentoCobranza docCobranza;
	
	//ID Gestor
	private Integer  intIdPersonaGestor;
	private Integer  intIdEmpresaGestor;
	private Integer  intItemGestorcobranzasuc;
	
    private Date dtFechaGestion;
    private Timestamp dtHoraInicio;
    private Timestamp dtHoraFin;
    private String  strContacto;
    private String  strObservacion;
    private Integer intIdSucursalGestion;
    //
    private Integer intItemcierregescobcie;

  
    
    
    //Flags Paginas
    private boolean cobranzaRendered = false;
    
    //Filtro 
    private Integer intSucursal;
    private String  strNroDocuIdentidad;
    private Date    dtFechaGestionIni;
    private Date    dtFechaGestionFin;
    
    private Integer  intEstado;
    private Date dtFechaCierre;
    private Integer intParaLugarGestion;
    private Integer intParaTipoResultado;
    private Integer intParaTipoSubResultado;
    private Integer intItemDocCob;
    private Integer intItemArchivo;
    private Integer intItemHistorico;
    
   
    //Auxiliares
    private Integer intOpcSocioEntidad;
    private String strDescEntidad;
    private String strDescSocio;
    private String strTipoEnitidad;
    private String strTipoSocio;
    private Integer intSocioId;
    private String strSocioNombre;
    private String strDesDocCobranza;
    
    private Integer intHoraInicio;
    private Integer intMiniInicio;
    private Integer intHoraFin;
    private Integer intMiniFin;
     
    
    
    private GestionCobranzaCierre gestCobCierre;
    
    public Date getDtFechaCierre() {
		return dtFechaCierre;
	}



	public void setDtFechaCierre(Date dtFechaCierre) {
		this.dtFechaCierre = dtFechaCierre;
	}



	private List<GestionCobranzaEnt> listaGestionCobranzaEnt;
    
	private List<GestionCobranzaSoc> listaGestionCobranzaSoc;
	
	private List<Object> listaGestionCobranzaDetalle = new ArrayList<Object>();
	private List<Object> listaGestionCobranzaDetalleTemp = new ArrayList<Object>();
	
       
    



	public GestionCobranza(){
    	super();
    	setId(new GestionCobranzaId());
    }
    
    
    
    public Persona getPersona() {
		return persona;
	}



	public void setPersona(Persona persona) {
		this.persona = persona;
	}



	public Date getDtFechaGestionIni() {
		return dtFechaGestionIni;
	}



	public void setDtFechaGestionIni(Date dtFechaGestionIni) {
		this.dtFechaGestionIni = dtFechaGestionIni;
	}



	public Date getDtFechaGestionFin() {
		return dtFechaGestionFin;
	}



	public void setDtFechaGestionFin(Date dtFechaGestionFin) {
		this.dtFechaGestionFin = dtFechaGestionFin;
	}



	public Integer getIntIdPersonaGestor() {
		return intIdPersonaGestor;
	}
	public void setIntIdPersonaGestor(Integer intIdPersonaGestor) {
		this.intIdPersonaGestor = intIdPersonaGestor;
	}
	public GestionCobranzaId getId() {
		return id;
	}
	public void setId(GestionCobranzaId id) {
		this.id = id;
	}
	public Integer getIntItemGestionCobranza() {
		return intItemGestionCobranza;
	}
	public void setIntItemGestionCobranza(Integer intItemGestionCobranza) {
		this.intItemGestionCobranza = intItemGestionCobranza;
	}
	
	public Integer getIntSubTipoGestionCobCod() {
		return intSubTipoGestionCobCod;
	}
	public void setIntSubTipoGestionCobCod(Integer intSubTipoGestionCobCod) {
		this.intSubTipoGestionCobCod = intSubTipoGestionCobCod;
	}
	public GestorCobranza getGestorCobranza() {
		return gestorCobranza;
	}
	public void setGestorCobranza(GestorCobranza gestorCobranza) {
		this.gestorCobranza = gestorCobranza;
	}
	public Date getDtFechaGestion() {
		return dtFechaGestion;
	}
	public void setDtFechaGestion(Date dtFechaGestion) {
		this.dtFechaGestion = dtFechaGestion;
	}
	public Timestamp getDtHoraInicio() {
		return dtHoraInicio;
	}
	
	public Timestamp getDtHoraFin() {
		return dtHoraFin;
	}



	public void setDtHoraFin(Timestamp dtHoraFin) {
		this.dtHoraFin = dtHoraFin;
	}



	public void setDtHoraInicio(Timestamp dtHoraInicio) {
		this.dtHoraInicio = dtHoraInicio;
	}



	public String getStrContacto() {
		return strContacto;
	}
	public void setStrContacto(String strContacto) {
		this.strContacto = strContacto;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public Integer getIntSucursal() {
		return intSucursal;
	}



	public Integer getIntTipoGestionCobCod() {
		return intTipoGestionCobCod;
	}



	public void setIntTipoGestionCobCod(Integer intTipoGestionCobCod) {
		this.intTipoGestionCobCod = intTipoGestionCobCod;
	}



	public void setIntSucursal(Integer intSucursal) {
		this.intSucursal = intSucursal;
	}



	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}

	public List<GestionCobranzaEnt> getListaGestionCobranzaEnt() {
		return listaGestionCobranzaEnt;
	}

	public void setListaGestionCobranzaEnt(
			List<GestionCobranzaEnt> listaGestionCobranzaEnt) {
		this.listaGestionCobranzaEnt = listaGestionCobranzaEnt;
	}



	public String getStrNroDocuIdentidad() {
		return strNroDocuIdentidad;
	}



	public void setStrNroDocuIdentidad(String strNroDocuIdentidad) {
		this.strNroDocuIdentidad = strNroDocuIdentidad;
	}

	public boolean isCobranzaRendered() {
		return cobranzaRendered;
	}

	public void setCobranzaRendered(boolean cobranzaRendered) {
		this.cobranzaRendered = cobranzaRendered;
	}

	


	public Integer getIntEstado() {
		return intEstado;
	}



	public void setIntEstado(Integer intEstado) {
		this.intEstado = intEstado;
	}



	public void setIntIdEmpresaGestor(Integer intIdEmpresaGestor) {
		this.intIdEmpresaGestor = intIdEmpresaGestor;
	}



	public Integer getIntItemGestorcobranzasuc() {
		return intItemGestorcobranzasuc;
	}



	public void setIntItemGestorcobranzasuc(Integer intItemGestorcobranzasuc) {
		this.intItemGestorcobranzasuc = intItemGestorcobranzasuc;
	}



	public Integer getIntIdEmpresaGestor() {
		return intIdEmpresaGestor;
	}



	public Integer getIntIdSucursalGestion() {
		return intIdSucursalGestion;
	}



	public void setIntIdSucursalGestion(Integer intIdSucursalGestion) {
		this.intIdSucursalGestion = intIdSucursalGestion;
	}


	public List<GestionCobranzaSoc> getListaGestionCobranzaSoc() {
		return listaGestionCobranzaSoc;
	}

	public void setListaGestionCobranzaSoc(
			List<GestionCobranzaSoc> listaGestionCobranzaSoc) {
		this.listaGestionCobranzaSoc = listaGestionCobranzaSoc;
	}
 
	public List<Object> getListaGestionCobranzaDetalle() {
		return listaGestionCobranzaDetalle;
	}

	public void setListaGestionCobranzaDetalle(
			List<Object> listaGestionCobranzaDetalle) {
		this.listaGestionCobranzaDetalle = listaGestionCobranzaDetalle;
	}



	public Integer getIntOpcSocioEntidad() {
		return intOpcSocioEntidad;
	}



	public void setIntOpcSocioEntidad(Integer intOpcSocioEntidad) {
		this.intOpcSocioEntidad = intOpcSocioEntidad;
	}



	public String getStrDescEntidad() {
		return strDescEntidad;
	}



	public void setStrDescEntidad(String strDescEntidad) {
		this.strDescEntidad = strDescEntidad;
	}



	public String getStrDescSocio() {
		return strDescSocio;
	}



	public void setStrDescSocio(String strDescSocio) {
		this.strDescSocio = strDescSocio;
	}



	public String getStrTipoEnitidad() {
		return strTipoEnitidad;
	}



	public void setStrTipoEnitidad(String strTipoEnitidad) {
		this.strTipoEnitidad = strTipoEnitidad;
	}



	public String getStrTipoSocio() {
		return strTipoSocio;
	}



	public void setStrTipoSocio(String strTipoSocio) {
		this.strTipoSocio = strTipoSocio;
	}



	public Integer getIntParaLugarGestion() {
		return intParaLugarGestion;
	}



	public void setIntParaLugarGestion(Integer intParaLugarGestion) {
		this.intParaLugarGestion = intParaLugarGestion;
	}



	public Integer getIntParaTipoResultado() {
		return intParaTipoResultado;
	}



	public void setIntParaTipoResultado(Integer intParaTipoResultado) {
		this.intParaTipoResultado = intParaTipoResultado;
	}



	public Integer getIntParaTipoSubResultado() {
		return intParaTipoSubResultado;
	}



	public void setIntParaTipoSubResultado(Integer intParaTipoSubResultado) {
		this.intParaTipoSubResultado = intParaTipoSubResultado;
	}



	public Integer getIntSocioId() {
		return intSocioId;
	}



	public void setIntSocioId(Integer intSocioId) {
		this.intSocioId = intSocioId;
	}



	public String getStrSocioNombre() {
		return strSocioNombre;
	}



	public void setStrSocioNombre(String strSocioNombre) {
		this.strSocioNombre = strSocioNombre;
	}



	

	


	public Integer getIntItemDocCob() {
		return intItemDocCob;
	}



	public void setIntItemDocCob(Integer intItemDocCob) {
		this.intItemDocCob = intItemDocCob;
	}



	public Integer getIntItemArchivo() {
		return intItemArchivo;
	}



	public void setIntItemArchivo(Integer intItemArchivo) {
		this.intItemArchivo = intItemArchivo;
	}



	public Integer getIntItemHistorico() {
		return intItemHistorico;
	}



	public void setIntItemHistorico(Integer intItemHistorico) {
		this.intItemHistorico = intItemHistorico;
	}



	public List<Object> getListaGestionCobranzaDetalleTemp() {
		return listaGestionCobranzaDetalleTemp;
	}



	public void setListaGestionCobranzaDetalleTemp(
			List<Object> listaGestionCobranzaDetalleTemp) {
		this.listaGestionCobranzaDetalleTemp = listaGestionCobranzaDetalleTemp;
	}



	


	public String getStrDesDocCobranza() {
		return strDesDocCobranza;
	}



	public void setStrDesDocCobranza(String strDesDocCobranza) {
		this.strDesDocCobranza = strDesDocCobranza;
	}



	public DocumentoCobranza getDocCobranza() {
		return docCobranza;
	}



	public void setDocCobranza(DocumentoCobranza docCobranza) {
		this.docCobranza = docCobranza;
	}



	public Integer getIntHoraInicio() {
		return intHoraInicio;
	}



	public void setIntHoraInicio(Integer intHoraInicio) {
		this.intHoraInicio = intHoraInicio;
	}



	public Integer getIntMiniInicio() {
		return intMiniInicio;
	}



	public void setIntMiniInicio(Integer intMiniInicio) {
		this.intMiniInicio = intMiniInicio;
	}



	public Integer getIntHoraFin() {
		return intHoraFin;
	}



	public void setIntHoraFin(Integer intHoraFin) {
		this.intHoraFin = intHoraFin;
	}



	public Integer getIntMiniFin() {
		return intMiniFin;
	}



	public void setIntMiniFin(Integer intMiniFin) {
		this.intMiniFin = intMiniFin;
	}



	public Integer getIntItemcierregescobcie() {
		return intItemcierregescobcie;
	}



	public void setIntItemcierregescobcie(Integer intItemcierregescobcie) {
		this.intItemcierregescobcie = intItemcierregescobcie;
	}



	@Override
	public String toString() {
		return "GestionCobranza [id=" + id + ", intItemGestionCobranza="
				+ intItemGestionCobranza + ", intTipoGestionCobCod="
				+ intTipoGestionCobCod + ", intSubTipoGestionCobCod="
				+ intSubTipoGestionCobCod + ", gestorCobranza="
				+ gestorCobranza + ", persona=" + persona + ", docCobranza="
				+ docCobranza + ", intIdPersonaGestor=" + intIdPersonaGestor
				+ ", intIdEmpresaGestor=" + intIdEmpresaGestor
				+ ", intItemGestorcobranzasuc=" + intItemGestorcobranzasuc
				+ ", dtFechaGestion=" + dtFechaGestion + ", dtHoraInicio="
				+ dtHoraInicio + ", dtHoraFin=" + dtHoraFin + ", strContacto="
				+ strContacto + ", strObservacion=" + strObservacion
				+ ", intIdSucursalGestion=" + intIdSucursalGestion
				+ ", intItemcierregescobcie=" + intItemcierregescobcie
				+ ", cobranzaRendered=" + cobranzaRendered + ", intSucursal="
				+ intSucursal + ", strNroDocuIdentidad=" + strNroDocuIdentidad
				+ ", dtFechaGestionIni=" + dtFechaGestionIni
				+ ", dtFechaGestionFin=" + dtFechaGestionFin + ", intEstado="
				+ intEstado + ", dtFechaCierre=" + dtFechaCierre
				+ ", intParaLugarGestion=" + intParaLugarGestion
				+ ", intParaTipoResultado=" + intParaTipoResultado
				+ ", intParaTipoSubResultado=" + intParaTipoSubResultado
				+ ", intItemDocCob=" + intItemDocCob + ", intItemArchivo="
				+ intItemArchivo + ", intItemHistorico=" + intItemHistorico
				+ ", intOpcSocioEntidad=" + intOpcSocioEntidad
				+ ", strDescEntidad=" + strDescEntidad + ", strDescSocio="
				+ strDescSocio + ", strTipoEnitidad=" + strTipoEnitidad
				+ ", strTipoSocio=" + strTipoSocio + ", intSocioId="
				+ intSocioId + ", strSocioNombre=" + strSocioNombre
				+ ", strDesDocCobranza=" + strDesDocCobranza
				+ ", intHoraInicio=" + intHoraInicio + ", intMiniInicio="
				+ intMiniInicio + ", intHoraFin=" + intHoraFin
				+ ", intMiniFin=" + intMiniFin + ", gestCobCierre="
				+ gestCobCierre + ", listaGestionCobranzaEnt="
				+ listaGestionCobranzaEnt + ", listaGestionCobranzaSoc="
				+ listaGestionCobranzaSoc + ", listaGestionCobranzaDetalle="
				+ listaGestionCobranzaDetalle
				+ ", listaGestionCobranzaDetalleTemp="
				+ listaGestionCobranzaDetalleTemp + "]";
	}

    

	

	
   


}
