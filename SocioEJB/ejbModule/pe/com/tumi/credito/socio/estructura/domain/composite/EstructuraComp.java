package pe.com.tumi.credito.socio.estructura.domain.composite;

import java.util.Date;

import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstructuraComp extends TumiDomain {
	private Estructura estructura;
	private EstructuraDetalle estructuraDetalle;
	private String strNivelConcatenado;
	private String strTipoSocioConcatenado;
	private String strConfiguracionConcatenado;
	private String strModalidadConcatenado;
	private Integer intFechaEnviadoDesde;
	private Integer IntFechaEnviadoHasta;
	private Integer intFechaEfectuadoDesde;
	private Integer IntFechaEfectuadoHasta;
	private Integer intFechaChequeDesde;
	private Integer IntFechaChequeHasta;
	private String strSucursalConcatenado;
	private Boolean chkEstructura;
	
    private Integer opcionBusquedaCobranza;	

	
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
	public String getStrNivelConcatenado() {
		return strNivelConcatenado;
	}
	public void setStrNivelConcatenado(String strNivelConcatenado) {
		this.strNivelConcatenado = strNivelConcatenado;
	}
	public String getStrTipoSocioConcatenado() {
		return strTipoSocioConcatenado;
	}
	public void setStrTipoSocioConcatenado(String strTipoSocioConcatenado) {
		this.strTipoSocioConcatenado = strTipoSocioConcatenado;
	}
	public String getStrConfiguracionConcatenado() {
		return strConfiguracionConcatenado;
	}
	public void setStrConfiguracionConcatenado(String strConfiguracionConcatenado) {
		this.strConfiguracionConcatenado = strConfiguracionConcatenado;
	}
	public String getStrModalidadConcatenado() {
		return strModalidadConcatenado;
	}
	public void setStrModalidadConcatenado(String strModalidadConcatenado) {
		this.strModalidadConcatenado = strModalidadConcatenado;
	}
	public Integer getIntFechaEnviadoDesde() {
		return intFechaEnviadoDesde;
	}
	public void setIntFechaEnviadoDesde(Integer intFechaEnviadoDesde) {
		this.intFechaEnviadoDesde = intFechaEnviadoDesde;
	}
	public Integer getIntFechaEnviadoHasta() {
		return IntFechaEnviadoHasta;
	}
	public void setIntFechaEnviadoHasta(Integer intFechaEnviadoHasta) {
		IntFechaEnviadoHasta = intFechaEnviadoHasta;
	}
	public Integer getIntFechaEfectuadoDesde() {
		return intFechaEfectuadoDesde;
	}
	public void setIntFechaEfectuadoDesde(Integer intFechaEfectuadoDesde) {
		this.intFechaEfectuadoDesde = intFechaEfectuadoDesde;
	}
	public Integer getIntFechaEfectuadoHasta() {
		return IntFechaEfectuadoHasta;
	}
	public void setIntFechaEfectuadoHasta(Integer intFechaEfectuadoHasta) {
		IntFechaEfectuadoHasta = intFechaEfectuadoHasta;
	}
	public Integer getIntFechaChequeDesde() {
		return intFechaChequeDesde;
	}
	public void setIntFechaChequeDesde(Integer intFechaChequeDesde) {
		this.intFechaChequeDesde = intFechaChequeDesde;
	}
	public Integer getIntFechaChequeHasta() {
		return IntFechaChequeHasta;
	}
	public void setIntFechaChequeHasta(Integer intFechaChequeHasta) {
		IntFechaChequeHasta = intFechaChequeHasta;
	}
	public String getStrSucursalConcatenado() {
		return strSucursalConcatenado;
	}
	public void setStrSucursalConcatenado(String strSucursalConcatenado) {
		this.strSucursalConcatenado = strSucursalConcatenado;
	}
	public Boolean getChkEstructura() {
		return chkEstructura;
	}
	public void setChkEstructura(Boolean chkEstructura) {
		this.chkEstructura = chkEstructura;
	}
	public Integer getOpcionBusquedaCobranza() {
		return opcionBusquedaCobranza;
	}
	public void setOpcionBusquedaCobranza(Integer opcionBusquedaCobranza) {
		this.opcionBusquedaCobranza = opcionBusquedaCobranza;
	}
	
	
}
