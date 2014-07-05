package pe.com.tumi.credito.socio.aperturaCuenta.core.domain;

import java.sql.Timestamp;
import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CuentaIntegrante extends TumiDomain {
	private CuentaIntegranteId id;
	private Integer intParaTipoIntegranteCod;
	private Timestamp tsFechaIngreso;
	private Timestamp tsFechaRenuncia;
	private Integer intPersonaUsuario;
	private Integer intPersonaFecRenuncia;
	private Integer	intCorrPersona;
	
	private List<CuentaIntegrantePermiso> listaCuentaIntegrantePermiso;
	
	public CuentaIntegranteId getId() {
		return id;
	}
	public void setId(CuentaIntegranteId id) {
		this.id = id;
	}
	public Integer getIntParaTipoIntegranteCod() {
		return intParaTipoIntegranteCod;
	}
	public void setIntParaTipoIntegranteCod(Integer intParaTipoIntegranteCod) {
		this.intParaTipoIntegranteCod = intParaTipoIntegranteCod;
	}
	public Timestamp getTsFechaIngreso() {
		return tsFechaIngreso;
	}
	public void setTsFechaIngreso(Timestamp tsFechaIngreso) {
		this.tsFechaIngreso = tsFechaIngreso;
	}
	public Timestamp getTsFechaRenuncia() {
		return tsFechaRenuncia;
	}
	public void setTsFechaRenuncia(Timestamp tsFechaRenuncia) {
		this.tsFechaRenuncia = tsFechaRenuncia;
	}
	public Integer getIntPersonaUsuario() {
		return intPersonaUsuario;
	}
	public void setIntPersonaUsuario(Integer intPersonaUsuario) {
		this.intPersonaUsuario = intPersonaUsuario;
	}
	public Integer getIntPersonaFecRenuncia() {
		return intPersonaFecRenuncia;
	}
	public void setIntPersonaFecRenuncia(Integer intPersonaFecRenuncia) {
		this.intPersonaFecRenuncia = intPersonaFecRenuncia;
	}
	public Integer getIntCorrPersona() {
		return intCorrPersona;
	}
	public void setIntCorrPersona(Integer intCorrPersona) {
		this.intCorrPersona = intCorrPersona;
	}
	public List<CuentaIntegrantePermiso> getListaCuentaIntegrantePermiso() {
		return listaCuentaIntegrantePermiso;
	}
	public void setListaCuentaIntegrantePermiso(
			List<CuentaIntegrantePermiso> listaCuentaIntegrantePermiso) {
		this.listaCuentaIntegrantePermiso = listaCuentaIntegrantePermiso;
	}
	@Override
	public String toString() {
		return "CuentaIntegrante [id=" + id + "]";
	}
	
}