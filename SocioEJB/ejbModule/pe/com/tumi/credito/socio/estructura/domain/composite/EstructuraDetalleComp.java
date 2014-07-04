package pe.com.tumi.credito.socio.estructura.domain.composite;

import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstructuraDetalleComp extends TumiDomain {
	
	private EstructuraDetalle estructuraDetalle;
	private Boolean blnCheckSubsucursal;
	private Integer intFechaEnviado;
	private String strFechaEnviado;
	private Integer intFechaCheque;
	private String strFechaCheque;
	private Integer intFechaEfectuado;
	private String strFechaEfectuado;
	private Boolean blnCheckCodigo;
	private String strTipoSocio;
	private String strModalidad;
	private String strSucursal;
	private String strSubsucursal;
	
	
	public EstructuraDetalle getEstructuraDetalle() {
		return estructuraDetalle;
	}
	public void setEstructuraDetalle(EstructuraDetalle estructuraDetalle) {
		this.estructuraDetalle = estructuraDetalle;
	}
	public Boolean getBlnCheckSubsucursal() {
		return blnCheckSubsucursal;
	}
	public void setBlnCheckSubsucursal(Boolean blnCheckSubsucursal) {
		this.blnCheckSubsucursal = blnCheckSubsucursal;
	}
	public Integer getIntFechaEnviado() {
		return intFechaEnviado;
	}
	public void setIntFechaEnviado(Integer intFechaEnviado) {
		this.intFechaEnviado = intFechaEnviado;
	}
	public Integer getIntFechaCheque() {
		return intFechaCheque;
	}
	public void setIntFechaCheque(Integer intFechaCheque) {
		this.intFechaCheque = intFechaCheque;
	}
	public Integer getIntFechaEfectuado() {
		return intFechaEfectuado;
	}
	public void setIntFechaEfectuado(Integer intFechaEfectuado) {
		this.intFechaEfectuado = intFechaEfectuado;
	}
	public Boolean getBlnCheckCodigo() {
		return blnCheckCodigo;
	}
	public void setBlnCheckCodigo(Boolean blnCheckCodigo) {
		this.blnCheckCodigo = blnCheckCodigo;
	}
	public String getStrTipoSocio() {
		return strTipoSocio;
	}
	public void setStrTipoSocio(String strTipoSocio) {
		this.strTipoSocio = strTipoSocio;
	}
	public String getStrModalidad() {
		return strModalidad;
	}
	public void setStrModalidad(String strModalidad) {
		this.strModalidad = strModalidad;
	}
	public String getStrSucursal() {
		return strSucursal;
	}
	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}
	public String getStrSubsucursal() {
		return strSubsucursal;
	}
	public void setStrSubsucursal(String strSubsucursal) {
		this.strSubsucursal = strSubsucursal;
	}
	public String getStrFechaEnviado() {
		return strFechaEnviado;
	}
	public void setStrFechaEnviado(String strFechaEnviado) {
		this.strFechaEnviado = strFechaEnviado;
	}
	public String getStrFechaCheque() {
		return strFechaCheque;
	}
	public void setStrFechaCheque(String strFechaCheque) {
		this.strFechaCheque = strFechaCheque;
	}
	public String getStrFechaEfectuado() {
		return strFechaEfectuado;
	}
	public void setStrFechaEfectuado(String strFechaEfectuado) {
		this.strFechaEfectuado = strFechaEfectuado;
	}
}
