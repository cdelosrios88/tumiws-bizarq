package pe.com.tumi.cobranza.planilla.domain.composite;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.persona.contacto.domain.Documento;

public class ItemPlanilla extends TumiDomain{
	
	private Socio socio;
	private Estructura estructura;
	private Documento documento;
	
	private BigDecimal bdHaberes;
	private BigDecimal bdIncentivos;
	private BigDecimal bdCas;
	
	private BigDecimal bdHaberesEfectuado;
	private BigDecimal bdIncentivosEfectuado;
	private BigDecimal bdCasEfectuado;
	
	private BigDecimal bdEnvioTotal;
	private Integer intEmpresa;
	private Integer intPeriodo;
	
	private List<Envioconcepto> listaEnvioConcepto;
	private List<Enviomonto> listaEnviomonto;
	private String strhaberIncentivoCas;
	private BigDecimal bdHaberesI;
	private BigDecimal bdIncentivosI;
	private BigDecimal bdCasI;
	private Enviomonto lEnviomonto;
	private String condicionCuenta;
	private Integer intCodigoPersona;
	private Expediente expediente;
	private List<Efectuado> listaEfectuado;
	private Efectuado efectuado;
	private CuentaIntegrante cuentaIntegrante;
	private BigDecimal bdMontoEnTipoPlanilla;
	private BigDecimal bdtotalHaberIncentivo;
	
	private BigDecimal bdTotalHabHabIIncIncI;
	private Integer intCantModalidades;
	
	private List<Expediente> listaExpediente;
	
	private Boolean blnLIC;
	private Boolean blnDJUD;
	private Boolean blnCartaAutorizacion;
	private BigDecimal bdTotalCasCasI;
	private Boolean blnAgregoTipoPlanilla;
	private Boolean blnEnvioConcepto;
	private Integer intItemEnvioConcepto;
	private String strCodigoPlanilla;
	private String strCodigoPersona;
	private Boolean blnTieneHaber;
	private Boolean blnTieneIncentivo;
	
	public String getStrhaberIncentivoCas() {
		return strhaberIncentivoCas;
	}
    public void setStrhaberIncentivoCas(String strhaberIncentivoCas) {
		this.strhaberIncentivoCas = strhaberIncentivoCas;
	}
	
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	public Estructura getEstructura() {
		return estructura;
	}
	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public BigDecimal getBdHaberes() {
		if(bdHaberes == null)
		{
			bdHaberes = new BigDecimal(0);
		}
		return bdHaberes;
	}
	public void setBdHaberes(BigDecimal bdHaberes) {
		this.bdHaberes = bdHaberes;
	}
	public BigDecimal getBdIncentivos() {
		if(bdIncentivos == null)
		{
			bdIncentivos = new BigDecimal(0);
		}
		return bdIncentivos;
	}
	public void setBdIncentivos(BigDecimal bdIncentivos) {
		this.bdIncentivos = bdIncentivos;
	}

	public BigDecimal getBdCas() {
		if(bdCas == null)
		{
			bdCas = new BigDecimal(0);
		}
		return bdCas;
	}
	public void setBdCas(BigDecimal bdCas) {
		this.bdCas = bdCas;
	}

	public BigDecimal getBdEnvioTotal() {
//		return bdEnvioTotal;
		return bdHaberes.add(bdIncentivos).add(bdHaberesI).add(bdIncentivosI);
	}
	public void setBdEnvioTotal(BigDecimal bdEnvioTotal) {
		this.bdEnvioTotal = bdEnvioTotal;
	}

	public BigDecimal getBdHaberesEfectuado() {
		return bdHaberesEfectuado;
	}
	public void setBdHaberesEfectuado(BigDecimal bdHaberesEfectuado) {
		this.bdHaberesEfectuado = bdHaberesEfectuado;
	}
	
	public BigDecimal getBdIncentivosEfectuado() {
		return bdIncentivosEfectuado;
	}
	public void setBdIncentivosEfectuado(BigDecimal bdIncentivosEfectuado) {
		this.bdIncentivosEfectuado = bdIncentivosEfectuado;
	}
	
	public BigDecimal getBdCasEfectuado() {
		return bdCasEfectuado;
	}
	public void setBdCasEfectuado(BigDecimal bdCasEfectuado) {
		this.bdCasEfectuado = bdCasEfectuado;
	}
	
	public List<Envioconcepto> getListaEnvioConcepto() {
		return listaEnvioConcepto;
	}
	public void setListaEnvioConcepto(List<Envioconcepto> listaEnvioConcepto) {
		this.listaEnvioConcepto = listaEnvioConcepto;
	}
	public List<Enviomonto> getListaEnviomonto() {
		return listaEnviomonto;
	}
	public void setListaEnviomonto(List<Enviomonto> listaEnviomonto) {
		this.listaEnviomonto = listaEnviomonto;
	}
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}
	public Integer getIntEmpresa() {
		return intEmpresa;
	}
	public void setIntEmpresa(Integer intEmpresa) {
		this.intEmpresa = intEmpresa;
	}
	public BigDecimal getBdCasI() {
		return bdCasI;
	}
	public void setBdCasI(BigDecimal bdCasI) {
		this.bdCasI = bdCasI;
	}
	public BigDecimal getBdHaberesI() {
		return bdHaberesI;
	}
	public void setBdHaberesI(BigDecimal bdHaberesI) {
		this.bdHaberesI = bdHaberesI;
	}
	public BigDecimal getBdIncentivosI() {
		return bdIncentivosI;
	}
	public void setBdIncentivosI(BigDecimal bdIncentivosI) {
		this.bdIncentivosI = bdIncentivosI;
	}
	public Enviomonto getlEnviomonto() {
		return lEnviomonto;
	}
	public void setlEnviomonto(Enviomonto lEnviomonto) {
		this.lEnviomonto = lEnviomonto;
	}
	public String getCondicionCuenta() {
		return condicionCuenta;
	}
	public void setCondicionCuenta(String condicionCuenta) {
		this.condicionCuenta = condicionCuenta;
	}
	public Integer getIntCodigoPersona() {
		return intCodigoPersona;
	}
	public void setIntCodigoPersona(Integer intCodigoPersona) {
		this.intCodigoPersona = intCodigoPersona;
	}
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	public List<Efectuado> getListaEfectuado() {
		return listaEfectuado;
	}
	public void setListaEfectuado(List<Efectuado> listaEfectuado) {
		this.listaEfectuado = listaEfectuado;
	}
	public Efectuado getEfectuado() {
		return efectuado;
	}
	public void setEfectuado(Efectuado efectuado) {
		this.efectuado = efectuado;
	}
	public CuentaIntegrante getCuentaIntegrante() {
		return cuentaIntegrante;
	}
	public void setCuentaIntegrante(CuentaIntegrante cuentaIntegrante) {
		this.cuentaIntegrante = cuentaIntegrante;
	}
	public BigDecimal getBdMontoEnTipoPlanilla() {
		return bdMontoEnTipoPlanilla;
	}
	public void setBdMontoEnTipoPlanilla(BigDecimal bdMontoEnTipoPlanilla) {
		this.bdMontoEnTipoPlanilla = bdMontoEnTipoPlanilla;
	}
	
	public BigDecimal getBdtotalHaberIncentivo() {
		return bdtotalHaberIncentivo;
	}
	public void setBdtotalHaberIncentivo(BigDecimal bdtotalHaberIncentivo) {
		this.bdtotalHaberIncentivo = bdtotalHaberIncentivo;
	}
	
	public BigDecimal getTotalHaberIncentivo()
	{
		return bdHaberes.add(bdIncentivos);
	}
	
	
	public BigDecimal getTotalCasCaI()
	{
		return bdCas.add(bdCasI);
	}
		
	
	public BigDecimal getBdTotalHabHabIIncIncI() {
		return bdHaberes.add(bdHaberesI).add(bdIncentivos).add(bdIncentivosI);
	}
	public void setBdTotalHabHabIIncIncI(BigDecimal bdTotalHabHabIIncIncI) {
		this.bdTotalHabHabIIncIncI = bdTotalHabHabIIncIncI;
	}
	
	public Integer getIntCantModalidades() {
		return intCantModalidades;
	}
	public void setIntCantModalidades(Integer intCantModalidades) {
		this.intCantModalidades = intCantModalidades;
	}
	
	
	
	public List<Expediente> getListaExpediente() {
		return listaExpediente;
	}
	public void setListaExpediente(List<Expediente> listaExpediente) {
		this.listaExpediente = listaExpediente;
	}	
	
	public Boolean getBlnLIC() {
		return blnLIC;
	}
	public void setBlnLIC(Boolean blnLIC) {
		this.blnLIC = blnLIC;
	}
	
	public Boolean getBlnDJUD() {
		return blnDJUD;
	}
	public void setBlnDJUD(Boolean blnDJUD) {
		this.blnDJUD = blnDJUD;
	}
		
	public Boolean getBlnCartaAutorizacion() {
		return blnCartaAutorizacion;
	}
	public void setBlnCartaAutorizacion(Boolean blnCartaAutorizacion) {
		this.blnCartaAutorizacion = blnCartaAutorizacion;
	}	
	
	public BigDecimal getBdTotalCasCasI() {
		return getBdCas().add(getBdCasI());
	}
	
	public void setBdTotalCasCasI(BigDecimal bdTotalCasCasI) {
		this.bdTotalCasCasI = bdTotalCasCasI;
	}
			
	public Boolean getBlnAgregoTipoPlanilla() {
		return blnAgregoTipoPlanilla;
	}
	public void setBlnAgregoTipoPlanilla(Boolean blnAgregoTipoPlanilla) {
		this.blnAgregoTipoPlanilla = blnAgregoTipoPlanilla;
	}
	
	public Boolean getBlnEnvioConcepto() {
		return blnEnvioConcepto;
	}
	public void setBlnEnvioConcepto(Boolean blnEnvioConcepto) {
		this.blnEnvioConcepto = blnEnvioConcepto;
	}
	
	public Integer getIntItemEnvioConcepto() {
		return intItemEnvioConcepto;
	}
	public void setIntItemEnvioConcepto(Integer intItemEnvioConcepto) {
		this.intItemEnvioConcepto = intItemEnvioConcepto;
	}
		
	public String getStrCodigoPlanilla() {
		return strCodigoPlanilla;
	}
	public void setStrCodigoPlanilla(String strCodigoPlanilla) {
		this.strCodigoPlanilla = strCodigoPlanilla;
	}
	
	
	public String getStrCodigoPersona() {
		return strCodigoPersona;
	}
	public void setStrCodigoPersona(String strCodigoPersona) {
		this.strCodigoPersona = strCodigoPersona;
	}
	
	public Boolean getBlnTieneHaber() {
		return blnTieneHaber;
	}
	public void setBlnTieneHaber(Boolean blnTieneHaber) {
		this.blnTieneHaber = blnTieneHaber;
	}
	public Boolean getBlnTieneIncentivo() {
		return blnTieneIncentivo;
	}
	public void setBlnTieneIncentivo(Boolean blnTieneIncentivo) {
		this.blnTieneIncentivo = blnTieneIncentivo;
	}
	
	public Boolean getTieneIncentivo()
	{
		Boolean ret = Boolean.FALSE;
		Iterator <SocioEstructura> it = socio.getListSocioEstructura().iterator();
		while(it.hasNext())
		{
			SocioEstructura item = (SocioEstructura)it.next();
			if(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS.compareTo(item.getIntModalidad()) == 0)
			{
				ret = Boolean.TRUE;
			}
		}
		return ret;
	}
	
	public Boolean getTieneHaber()
	{
		Boolean ret = Boolean.FALSE;
		Iterator <SocioEstructura> it = socio.getListSocioEstructura().iterator();
		while(it.hasNext())
		{
			SocioEstructura item = (SocioEstructura)it.next();
			if(Constante.PARAM_T_MODALIDADPLANILLA_HABERES.compareTo(item.getIntModalidad()) == 0)
			{
				ret = Boolean.TRUE;
			}
		}
		return ret;
	}
	
	@Override
	public String toString() {
		return "ItemPlanilla [bdHaberes=" + bdHaberes + ", bdIncentivos="
				+ bdIncentivos + ", bdHaberesEfectuado=" + bdHaberesEfectuado
				+ ", bdIncentivosEfectuado=" + bdIncentivosEfectuado
				+ ", blnTieneHaber=" + blnTieneHaber + ", blnTieneIncentivo="
				+ blnTieneIncentivo + "]";
	}
	
	
	
}
