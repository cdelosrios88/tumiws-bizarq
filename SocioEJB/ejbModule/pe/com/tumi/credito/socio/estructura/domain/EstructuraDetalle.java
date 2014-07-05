package pe.com.tumi.credito.socio.estructura.domain;

import java.util.Date;
import java.util.List;

import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EstructuraDetalle extends TumiDomain {

	private EstructuraDetalleId id;
	private Estructura estructura;
	private Integer intParaTipoSocioCod;
	private Integer intParaModalidadCod;
	private Integer intSeguSucursalPk;
	private Integer intSeguSubSucursalPk;
	private Integer intDiaEnviado;
	private Integer intSaltoEnviado;
	private Integer intDiaEfectuado;
	private Integer intSaltoEfectuado;
	private String strCodigoExterno;
	private Integer intDiaCheque;
	private Integer intSaltoCheque;
	private Integer intPersEmpresaPk;
	private Integer intPersPersonaUsuarioPk;
	private Integer intParaEstadoCod;
	private Date 	dtFechaEliminacion;
	private List<ConvenioEstructuraDetalle> listaConvenioDetalle;
	private List<Subsucursal> listaSubsucursal;
	private Boolean blnChecked;
	private String strChecked;
	private Sucursal sucursal;
	
	public EstructuraDetalleId getId() {
		return id;
	}
	public void setId(EstructuraDetalleId id) {
		this.id = id;
	}
	public Estructura getEstructura() {
		return estructura;
	}
	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}
	public Integer getIntParaTipoSocioCod() {
		return intParaTipoSocioCod;
	}
	public void setIntParaTipoSocioCod(Integer intParaTipoSocioCod) {
		this.intParaTipoSocioCod = intParaTipoSocioCod;
	}
	public Integer getIntParaModalidadCod() {
		return intParaModalidadCod;
	}
	public void setIntParaModalidadCod(Integer intParaModalidadCod) {
		this.intParaModalidadCod = intParaModalidadCod;
	}
	public Integer getIntSeguSucursalPk() {
		return intSeguSucursalPk;
	}
	public void setIntSeguSucursalPk(Integer intSeguSucursalPk) {
		this.intSeguSucursalPk = intSeguSucursalPk;
	}
	public Integer getIntSeguSubSucursalPk() {
		return intSeguSubSucursalPk;
	}
	public void setIntSeguSubSucursalPk(Integer intSeguSubSucursalPk) {
		this.intSeguSubSucursalPk = intSeguSubSucursalPk;
	}
	public Integer getIntDiaEnviado() {
		return intDiaEnviado;
	}
	public void setIntDiaEnviado(Integer intDiaEnviado) {
		this.intDiaEnviado = intDiaEnviado;
	}
	public Integer getIntSaltoEnviado() {
		return intSaltoEnviado;
	}
	public void setIntSaltoEnviado(Integer intSaltoEnviado) {
		this.intSaltoEnviado = intSaltoEnviado;
	}
	public Integer getIntDiaEfectuado() {
		return intDiaEfectuado;
	}
	public void setIntDiaEfectuado(Integer intDiaEfectuado) {
		this.intDiaEfectuado = intDiaEfectuado;
	}
	public Integer getIntSaltoEfectuado() {
		return intSaltoEfectuado;
	}
	public void setIntSaltoEfectuado(Integer intSaltoEfectuado) {
		this.intSaltoEfectuado = intSaltoEfectuado;
	}
	public String getStrCodigoExterno() {
		return strCodigoExterno;
	}
	public void setStrCodigoExterno(String strCodigoExterno) {
		this.strCodigoExterno = strCodigoExterno;
	}
	public Integer getIntDiaCheque() {
		return intDiaCheque;
	}
	public void setIntDiaCheque(Integer intDiaCheque) {
		this.intDiaCheque = intDiaCheque;
	}
	public Integer getIntSaltoCheque() {
		return intSaltoCheque;
	}
	public void setIntSaltoCheque(Integer intSaltoCheque) {
		this.intSaltoCheque = intSaltoCheque;
	}
	public Integer getIntPersEmpresaPk() {
		return intPersEmpresaPk;
	}
	public void setIntPersEmpresaPk(Integer intPersEmpresaPk) {
		this.intPersEmpresaPk = intPersEmpresaPk;
	}
	public Integer getIntPersPersonaUsuarioPk() {
		return intPersPersonaUsuarioPk;
	}
	public void setIntPersPersonaUsuarioPk(Integer intPersPersonaUsuarioPk) {
		this.intPersPersonaUsuarioPk = intPersPersonaUsuarioPk;
	}
	public List<ConvenioEstructuraDetalle> getListaConvenioDetalle() {
		return listaConvenioDetalle;
	}
	public void setListaConvenioDetalle(List<ConvenioEstructuraDetalle> listaConvenioDetalle) {
		this.listaConvenioDetalle = listaConvenioDetalle;
	}
	public List<Subsucursal> getListaSubsucursal() {
		return listaSubsucursal;
	}
	public void setListaSubsucursal(List<Subsucursal> listaSubsucursal) {
		this.listaSubsucursal = listaSubsucursal;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Date getDtFechaEliminacion() {
		return dtFechaEliminacion;
	}
	public void setDtFechaEliminacion(Date dtFechaEliminacion) {
		this.dtFechaEliminacion = dtFechaEliminacion;
	}
	public Boolean getBlnChecked() {
		return blnChecked;
	}
	public void setBlnChecked(Boolean blnChecked) {
		this.blnChecked = blnChecked;
	}
	public String getStrChecked() {
		return strChecked;
	}
	public void setStrChecked(String strChecked) {
		this.strChecked = strChecked;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	@Override
	public String toString() {
		return "EstructuraDetalle [intSeguSucursalPk=" + intSeguSucursalPk+
				 ", intParaTipoSocioCod=" + intParaTipoSocioCod + 
				 "]";
	}
}
