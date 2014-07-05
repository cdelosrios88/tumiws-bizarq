package pe.com.tumi.seguridad.login.domain;

import java.sql.Timestamp;
import java.util.Date;

import java.util.List;

//import pe.com.tumi.empresa.domain.Mof;
//import pe.com.tumi.empresa.domain.SegVPerfildetalle;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;

public class Perfil extends TumiDomain {

	private PerfilId id;
	private String strDescripcion;
	private Timestamp tsFechaRegistro;
	private Integer intTipoPerfil;
	private Date dtDesde;
	private Date dtHasta;
	private Integer intIdEstado;
	private Integer intPersPersonaUsuarioPk;
	private Juridica juridica;
	//private List<Mof> listaMof;
	private List<UsuarioPerfil> listaUsuarioPerfil;
	private List<PermisoPerfil> listaPermisoPerfil;
	
	private Boolean blnIndeterminado; 
	private Boolean blnVigencia;
	
	public PerfilId getId() {
		return id;
	}
	public void setId(PerfilId id) {
		this.id = id;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Integer getIntTipoPerfil() {
		return intTipoPerfil;
	}
	public void setIntTipoPerfil(Integer intTipoPerfil) {
		this.intTipoPerfil = intTipoPerfil;
	}
	public Date getDtDesde() {
		return dtDesde;
	}
	public void setDtDesde(Date dtDesde) {
		this.dtDesde = dtDesde;
	}
	public Date getDtHasta() {
		return dtHasta;
	}
	public void setDtHasta(Date dtHasta) {
		this.dtHasta = dtHasta;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Juridica getJuridica() {
		return juridica;
	}
	public void setJuridica(Juridica juridica) {
		this.juridica = juridica;
	}
	public List<UsuarioPerfil> getListaUsuarioPerfil() {
		return listaUsuarioPerfil;
	}
	public void setListaUsuarioPerfil(List<UsuarioPerfil> listaUsuarioPerfil) {
		this.listaUsuarioPerfil = listaUsuarioPerfil;
	}
	public Boolean getBlnIndeterminado() {
		return blnIndeterminado;
	}
	public void setBlnIndeterminado(Boolean blnIndeterminado) {
		this.blnIndeterminado = blnIndeterminado;
	}
	public List<PermisoPerfil> getListaPermisoPerfil() {
		return listaPermisoPerfil;
	}
	public void setListaPermisoPerfil(List<PermisoPerfil> listaPermisoPerfil) {
		this.listaPermisoPerfil = listaPermisoPerfil;
	}
	public Boolean getBlnVigencia() {
		return blnVigencia;
	}
	public void setBlnVigencia(Boolean blnVigencia) {
		this.blnVigencia = blnVigencia;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	
}
