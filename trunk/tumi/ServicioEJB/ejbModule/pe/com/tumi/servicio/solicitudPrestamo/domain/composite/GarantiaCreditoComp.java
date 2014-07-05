package pe.com.tumi.servicio.solicitudPrestamo.domain.composite;

import java.math.BigDecimal;

import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;

public class GarantiaCreditoComp extends TumiDomain {
	private GarantiaCredito garantiaCredito;
	private SocioComp socioComp;
	private SocioComp garanteComp;
	private Estructura estructura;
	private Integer intNroGarantizados;
	private ExpedienteCredito expediente;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 05-09-2013
	private ExpedienteComp expedienteComp;
	private Integer intItem;
	private BigDecimal bdSaldoAporte;
	private String strSaldoAporte;
	
	// 09.09.2013-CGD
	// atributos 
	//private String strDescValidaRefinan;
	//private Boolean blnEstadoValidaRefinan;
	
	//12.09.2013 - CGD
	//private Integer intItemCreditoGarantia; // usado x motivos de validacion
	
	public GarantiaCredito getGarantiaCredito() {
		return garantiaCredito;
	}
	public void setGarantiaCredito(GarantiaCredito garantiaCredito) {
		this.garantiaCredito = garantiaCredito;
	}
	public SocioComp getSocioComp() {
		return socioComp;
	}
	public void setSocioComp(SocioComp socioComp) {
		this.socioComp = socioComp;
	}
	public Estructura getEstructura() {
		return estructura;
	}
	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}
	public Integer getIntNroGarantizados() {
		return intNroGarantizados;
	}
	public void setIntNroGarantizados(Integer intNroGarantizados) {
		this.intNroGarantizados = intNroGarantizados;
	}
	public ExpedienteCredito getExpediente() {
		return expediente;
	}
	public void setExpediente(ExpedienteCredito expediente) {
		this.expediente = expediente;
	}
	public SocioComp getGaranteComp() {
		return garanteComp;
	}
	public void setGaranteComp(SocioComp garanteComp) {
		this.garanteComp = garanteComp;
	}

	public ExpedienteComp getExpedienteComp() {
		return expedienteComp;
	}
	public void setExpedienteComp(ExpedienteComp expedienteComp) {
		this.expedienteComp = expedienteComp;
	}
	public Integer getIntItem() {
		return intItem;
	}
	public void setIntItem(Integer intItem) {
		this.intItem = intItem;
	}
	public BigDecimal getBdSaldoAporte() {
		return bdSaldoAporte;
	}
	public void setBdSaldoAporte(BigDecimal bdSaldoAporte) {
		this.bdSaldoAporte = bdSaldoAporte;
	}
	public String getStrSaldoAporte() {
		return strSaldoAporte;
	}
	public void setStrSaldoAporte(String strSaldoAporte) {
		this.strSaldoAporte = strSaldoAporte;
	}
	
}