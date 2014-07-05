package pe.com.tumi.report.bean;

import java.math.BigDecimal;
import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.DataBeanEstadoCuentaPrestamos;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DataBeanEstCtaResumen extends TumiDomain {
	//Datos del Socio...
	private CuentaComp cuentaComp;
	//Datos de la cuenta...
	private SocioComp socioComp;
	//Lista de prestamos...
	private List<DataBeanEstadoCuentaPrestamos> listaDataBeanEstadoCuentaPrestamos;
	//Ultimos envios beneficios...
	private BigDecimal bdUltimoEnvioAportes;
	private BigDecimal bdUltimoEnvioFdoSepelio;
	private BigDecimal bdUltimoEnvioMant;
	private BigDecimal bdUltimoEnvioFdoRetiro;
	//Periodo ultimo envio...
	private Integer intAnioPeriodoTemp;			
	private Integer intMesPeriodoTemp;
	//Sumatoria saldos y ultimos envios...
	private BigDecimal bdSumatoriaSaldo;
	private BigDecimal bdSumatoriaUltimoEnvio;
	//Lista cuentas liquidadas...
	private List<CuentaComp> listaCuentaComp;
	
	public CuentaComp getCuentaComp() {
		return cuentaComp;
	}
	public void setCuentaComp(CuentaComp cuentaComp) {
		this.cuentaComp = cuentaComp;
	}
	public SocioComp getSocioComp() {
		return socioComp;
	}
	public void setSocioComp(SocioComp socioComp) {
		this.socioComp = socioComp;
	}
	public List<DataBeanEstadoCuentaPrestamos> getListaDataBeanEstadoCuentaPrestamos() {
		return listaDataBeanEstadoCuentaPrestamos;
	}
	public void setListaDataBeanEstadoCuentaPrestamos(
			List<DataBeanEstadoCuentaPrestamos> listaDataBeanEstadoCuentaPrestamos) {
		this.listaDataBeanEstadoCuentaPrestamos = listaDataBeanEstadoCuentaPrestamos;
	}
	public BigDecimal getBdUltimoEnvioAportes() {
		return bdUltimoEnvioAportes;
	}
	public void setBdUltimoEnvioAportes(BigDecimal bdUltimoEnvioAportes) {
		this.bdUltimoEnvioAportes = bdUltimoEnvioAportes;
	}
	public BigDecimal getBdUltimoEnvioFdoSepelio() {
		return bdUltimoEnvioFdoSepelio;
	}
	public void setBdUltimoEnvioFdoSepelio(BigDecimal bdUltimoEnvioFdoSepelio) {
		this.bdUltimoEnvioFdoSepelio = bdUltimoEnvioFdoSepelio;
	}
	public BigDecimal getBdUltimoEnvioMant() {
		return bdUltimoEnvioMant;
	}
	public void setBdUltimoEnvioMant(BigDecimal bdUltimoEnvioMant) {
		this.bdUltimoEnvioMant = bdUltimoEnvioMant;
	}
	public BigDecimal getBdUltimoEnvioFdoRetiro() {
		return bdUltimoEnvioFdoRetiro;
	}
	public void setBdUltimoEnvioFdoRetiro(BigDecimal bdUltimoEnvioFdoRetiro) {
		this.bdUltimoEnvioFdoRetiro = bdUltimoEnvioFdoRetiro;
	}
	public Integer getIntAnioPeriodoTemp() {
		return intAnioPeriodoTemp;
	}
	public void setIntAnioPeriodoTemp(Integer intAnioPeriodoTemp) {
		this.intAnioPeriodoTemp = intAnioPeriodoTemp;
	}
	public Integer getIntMesPeriodoTemp() {
		return intMesPeriodoTemp;
	}
	public void setIntMesPeriodoTemp(Integer intMesPeriodoTemp) {
		this.intMesPeriodoTemp = intMesPeriodoTemp;
	}
	public BigDecimal getBdSumatoriaSaldo() {
		return bdSumatoriaSaldo;
	}
	public void setBdSumatoriaSaldo(BigDecimal bdSumatoriaSaldo) {
		this.bdSumatoriaSaldo = bdSumatoriaSaldo;
	}
	public BigDecimal getBdSumatoriaUltimoEnvio() {
		return bdSumatoriaUltimoEnvio;
	}
	public void setBdSumatoriaUltimoEnvio(BigDecimal bdSumatoriaUltimoEnvio) {
		this.bdSumatoriaUltimoEnvio = bdSumatoriaUltimoEnvio;
	}
	public List<CuentaComp> getListaCuentaComp() {
		return listaCuentaComp;
	}
	public void setListaCuentaComp(List<CuentaComp> listaCuentaComp) {
		this.listaCuentaComp = listaCuentaComp;
	}
}
