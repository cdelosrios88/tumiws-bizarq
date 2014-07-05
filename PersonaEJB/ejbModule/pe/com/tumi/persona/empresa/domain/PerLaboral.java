package pe.com.tumi.persona.empresa.domain;


import java.math.BigDecimal;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;

public class PerLaboral extends TumiDomain {

	private PerLaboralPK id;
	private String strCentroTrabajo;
	private Integer intCargo;
	private Integer intCondicionLaboral;
	private Integer intCondicionLaboralDet;
	private Integer intTipoContrato;
	private Date dtInicioContrato;
	private Date dtFinContrato;
	private Integer intTipoArchivoContrato;
	private Integer intItemArchivoContrato;
	private Integer intItemHistoricoContrato;
	private Date dtInicioServicio;
	private BigDecimal bdRemuneracion;
	private Integer intRegimenLaboral;
	private String strSolicitudONP;
	
	private Date dtInicioServicioDesde;
	private Date dtInicioServicioHasta;
	
	private Date dtInicioContratoDesde;
	private Date dtInicioContratoHasta;
	
	private Date dtFinContratoDesde;
	private Date dtFinContratoHasta;	

	private Archivo contrato;
	
	//Getters & Setters
	public String getStrCentroTrabajo() {
		return strCentroTrabajo;
	}
	public void setStrCentroTrabajo(String strCentroTrabajo) {
		this.strCentroTrabajo = strCentroTrabajo;
	}
	public Integer getIntCargo() {
		return intCargo;
	}
	public void setIntCargo(Integer intCargo) {
		this.intCargo = intCargo;
	}
	public Integer getIntCondicionLaboral() {
		return intCondicionLaboral;
	}
	public void setIntCondicionLaboral(Integer intCondicionLaboral) {
		this.intCondicionLaboral = intCondicionLaboral;
	}
	public Integer getIntCondicionLaboralDet() {
		return intCondicionLaboralDet;
	}
	public void setIntCondicionLaboralDet(Integer intCondicionLaboralDet) {
		this.intCondicionLaboralDet = intCondicionLaboralDet;
	}
	public Integer getIntTipoContrato() {
		return intTipoContrato;
	}
	public void setIntTipoContrato(Integer intTipoContrato) {
		this.intTipoContrato = intTipoContrato;
	}
	public Integer getIntTipoArchivoContrato() {
		return intTipoArchivoContrato;
	}
	public void setIntTipoArchivoContrato(Integer intTipoArchivoContrato) {
		this.intTipoArchivoContrato = intTipoArchivoContrato;
	}
	public Integer getIntItemArchivoContrato() {
		return intItemArchivoContrato;
	}
	public void setIntItemArchivoContrato(Integer intItemArchivoContrato) {
		this.intItemArchivoContrato = intItemArchivoContrato;
	}
	public Date getDtInicioServicio() {
		return dtInicioServicio;
	}
	public void setDtInicioServicio(Date dtInicioServicio) {
		this.dtInicioServicio = dtInicioServicio;
	}
	public BigDecimal getBdRemuneracion() {
		return bdRemuneracion;
	}
	public void setBdRemuneracion(BigDecimal bdRemuneracion) {
		this.bdRemuneracion = bdRemuneracion;
	}
	public Integer getIntRegimenLaboral() {
		return intRegimenLaboral;
	}
	public void setIntRegimenLaboral(Integer intRegimenLaboral) {
		this.intRegimenLaboral = intRegimenLaboral;
	}
	public String getStrSolicitudONP() {
		return strSolicitudONP;
	}
	public void setStrSolicitudONP(String strSolicitudONP) {
		this.strSolicitudONP = strSolicitudONP;
	}
	public Date getDtInicioServicioDesde() {
		return dtInicioServicioDesde;
	}
	public void setDtInicioServicioDesde(Date dtInicioServicioDesde) {
		this.dtInicioServicioDesde = dtInicioServicioDesde;
	}
	public Date getDtInicioServicioHasta() {
		return dtInicioServicioHasta;
	}
	public void setDtInicioServicioHasta(Date dtInicioServicioHasta) {
		this.dtInicioServicioHasta = dtInicioServicioHasta;
	}
	public Date getDtInicioContrato() {
		return dtInicioContrato;
	}
	public void setDtInicioContrato(Date dtInicioContrato) {
		this.dtInicioContrato = dtInicioContrato;
	}
	public Date getDtFinContrato() {
		return dtFinContrato;
	}
	public void setDtFinContrato(Date dtFinContrato) {
		this.dtFinContrato = dtFinContrato;
	}
	public Date getDtInicioContratoDesde() {
		return dtInicioContratoDesde;
	}
	public void setDtInicioContratoDesde(Date dtInicioContratoDesde) {
		this.dtInicioContratoDesde = dtInicioContratoDesde;
	}
	public Date getDtInicioContratoHasta() {
		return dtInicioContratoHasta;
	}
	public void setDtInicioContratoHasta(Date dtInicioContratoHasta) {
		this.dtInicioContratoHasta = dtInicioContratoHasta;
	}
	public Date getDtFinContratoDesde() {
		return dtFinContratoDesde;
	}
	public void setDtFinContratoDesde(Date dtFinContratoDesde) {
		this.dtFinContratoDesde = dtFinContratoDesde;
	}
	public Date getDtFinContratoHasta() {
		return dtFinContratoHasta;
	}
	public void setDtFinContratoHasta(Date dtFinContratoHasta) {
		this.dtFinContratoHasta = dtFinContratoHasta;
	}
	public PerLaboralPK getId() {
		return id;
	}
	public void setId(PerLaboralPK id) {
		this.id = id;
	}
	public Integer getIntItemHistoricoContrato() {
		return intItemHistoricoContrato;
	}
	public void setIntItemHistoricoContrato(Integer intItemHistoricoContrato) {
		this.intItemHistoricoContrato = intItemHistoricoContrato;
	}
	public Archivo getContrato() {
		return contrato;
	}
	public void setContrato(Archivo contrato) {
		this.contrato = contrato;
	}
	
	@Override
	public String toString() {
		return "PerLaboral [id=" + id + ", strCentroTrabajo="
				+ strCentroTrabajo + ", intCargo=" + intCargo
				+ ", intCondicionLaboral=" + intCondicionLaboral
				+ ", intCondicionLaboralDet=" + intCondicionLaboralDet
				+ ", intTipoContrato=" + intTipoContrato
				+ ", dtInicioContrato=" + dtInicioContrato + ", dtFinContrato="
				+ dtFinContrato + ", intTipoArchivoContrato="
				+ intTipoArchivoContrato + ", intItemArchivoContrato="
				+ intItemArchivoContrato + ", intItemHistoricoContrato="
				+ intItemHistoricoContrato + ", dtInicioServicio="
				+ dtInicioServicio + ", bdRemuneracion=" + bdRemuneracion
				+ ", intRegimenLaboral=" + intRegimenLaboral
				+ ", strSolicitudONP=" + strSolicitudONP
				+ ", dtInicioServicioDesde=" + dtInicioServicioDesde
				+ ", dtInicioServicioHasta=" + dtInicioServicioHasta
				+ ", dtInicioContratoDesde=" + dtInicioContratoDesde
				+ ", dtInicioContratoHasta=" + dtInicioContratoHasta
				+ ", dtFinContratoDesde=" + dtFinContratoDesde
				+ ", dtFinContratoHasta=" + dtFinContratoHasta + "]";
	}
}
