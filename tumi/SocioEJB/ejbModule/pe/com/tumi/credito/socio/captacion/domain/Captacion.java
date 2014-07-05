package pe.com.tumi.credito.socio.captacion.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Captacion extends TumiDomain {

	private CaptacionId id;
	private Condicion condicion;
	private String strDescripcion;
	private Date dtInicio;
	private Date dtFin;
	private String 	strDtFechaIni;
	private String 	strDtFechaFin;
	private Integer intParaTipopersonaCod;
	private Integer intParaRolPk;
	private Integer intParaCondicionLaboralCod;
	private Integer intParaTipoDescuentoCod;
	private Integer intParaTipoConfiguracionCod;
	private Integer intParaMonedaCod;
	private BigDecimal bdValorConfiguracion;
	private BigDecimal bdPorcConfiguracion;
	private Integer intParaAplicacionCod;
	private BigDecimal bdTem;
	private Integer intTasaNaturaleza;
	private Integer intTasaFormula;
	private BigDecimal bdTea;
	private BigDecimal bdTna;
	private Integer intEdadLimite;
	private Integer intTiempoSustento;
	private Integer intPeriodicSustento;
	private Integer intParaTipoMaxMinSustCod;
	private Integer intCeseBeneficio;
	private Integer intNumeroCuota;
	private Integer intTiempoPermanencia;
	private BigDecimal bdPenalidadPorcentaje;
	private BigDecimal bdPenalidadTasaMora;
	private Integer intParaPenalidadAplicacion;
	private Integer intTiempoAportacion;
	private Integer intPeriodicAportacionCod;
	private Integer intParaTipoMaxMinAportCod;
	private Integer intCuota;
	private Integer intParaPeriodicCuotasCod;
	private Integer intParaTipoMaxMinCuotaCod;
	private Integer intTiempoDevolucion;
	private Integer intParaTipoMaxMinDevolucionCod;
	private Integer intParaTipoDia;
	private Integer intRegularidad;
	private Integer intPeriodicidad;
	private Date dtFechaRegistro;
	private String strDtFechaRegistro;
	private Integer intParaEstadoSolicitudCod;
	private Integer intParaEstadoCod;
	//Por definir
	private Integer intTipoFecha;
	private Integer intTasaInteres;
	private Integer intLimiteEdad;
	//private Integer intIndeterminado;
	private Integer intVigencia;
	private Integer intAportacionVigente;
	//private Integer intAportacionCaduco;
	private Integer intTipoCuenta;
	
	private List<ConvenioEstructuraDetalle> listaConvenioDetalle;
	private List<Concepto> listaConcepto;
	private List<Requisito> listaRequisito;
	private List<Afecto> listaAfecto;
	private List<Condicion> listaCondicion;
	private List<CondicionComp> listaCondicionComp;
	private List<Contable> listaContable;
	private List<Vinculo> listaVinculo;
	
	//Para muestras en interfaz
	private String strRequisitosConcatenados;
	private String strVinculosConcatenados;
	private String strConceptosConcatenados;
	
	//Getters y Setters
	public CaptacionId getId() {
		return id;
	}
	public void setId(CaptacionId id) {
		this.id = id;
	}
	public Condicion getCondicion() {
		return condicion;
	}
	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public Date getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}
	public Date getDtFin() {
		return dtFin;
	}
	public void setDtFin(Date dtFin) {
		this.dtFin = dtFin;
	}
	public String getStrDtFechaIni() {
		return strDtFechaIni;
	}
	public void setStrDtFechaIni(String strDtFechaIni) {
		this.strDtFechaIni = strDtFechaIni;
	}
	public String getStrDtFechaFin() {
		return strDtFechaFin;
	}
	public void setStrDtFechaFin(String strDtFechaFin) {
		this.strDtFechaFin = strDtFechaFin;
	}
	public Integer getIntParaTipopersonaCod() {
		return intParaTipopersonaCod;
	}
	public void setIntParaTipopersonaCod(Integer intParaTipopersonaCod) {
		this.intParaTipopersonaCod = intParaTipopersonaCod;
	}
	public Integer getIntParaRolPk() {
		return intParaRolPk;
	}
	public void setIntParaRolPk(Integer intParaRolPk) {
		this.intParaRolPk = intParaRolPk;
	}
	public Integer getIntParaCondicionLaboralCod() {
		return intParaCondicionLaboralCod;
	}
	public void setIntParaCondicionLaboralCod(Integer intParaCondicionLaboralCod) {
		this.intParaCondicionLaboralCod = intParaCondicionLaboralCod;
	}
	public Integer getIntParaTipoDescuentoCod() {
		return intParaTipoDescuentoCod;
	}
	public void setIntParaTipoDescuentoCod(Integer intParaTipoDescuentoCod) {
		this.intParaTipoDescuentoCod = intParaTipoDescuentoCod;
	}
	public Integer getIntParaTipoConfiguracionCod() {
		return intParaTipoConfiguracionCod;
	}
	public void setIntParaTipoConfiguracionCod(Integer intParaTipoConfiguracionCod) {
		this.intParaTipoConfiguracionCod = intParaTipoConfiguracionCod;
	}
	public Integer getIntParaMonedaCod() {
		return intParaMonedaCod;
	}
	public void setIntParaMonedaCod(Integer intParaMonedaCod) {
		this.intParaMonedaCod = intParaMonedaCod;
	}
	public BigDecimal getBdValorConfiguracion() {
		return bdValorConfiguracion;
	}
	public void setBdValorConfiguracion(BigDecimal bdValorConfiguracion) {
		this.bdValorConfiguracion = bdValorConfiguracion;
	}
	public BigDecimal getBdPorcConfiguracion() {
		return bdPorcConfiguracion;
	}
	public void setBdPorcConfiguracion(BigDecimal bdPorcConfiguracion) {
		this.bdPorcConfiguracion = bdPorcConfiguracion;
	}
	public Integer getIntParaAplicacionCod() {
		return intParaAplicacionCod;
	}
	public void setIntParaAplicacionCod(Integer intParaAplicacionCod) {
		this.intParaAplicacionCod = intParaAplicacionCod;
	}
	public BigDecimal getBdTem() {
		return bdTem;
	}
	public void setBdTem(BigDecimal bdTem) {
		this.bdTem = bdTem;
	}
	public Integer getIntTasaNaturaleza() {
		return intTasaNaturaleza;
	}
	public void setIntTasaNaturaleza(Integer intTasaNaturaleza) {
		this.intTasaNaturaleza = intTasaNaturaleza;
	}
	public Integer getIntTasaFormula() {
		return intTasaFormula;
	}
	public void setIntTasaFormula(Integer intTasaFormula) {
		this.intTasaFormula = intTasaFormula;
	}
	public BigDecimal getBdTea() {
		return bdTea;
	}
	public void setBdTea(BigDecimal bdTea) {
		this.bdTea = bdTea;
	}
	public BigDecimal getBdTna() {
		return bdTna;
	}
	public void setBdTna(BigDecimal bdTna) {
		this.bdTna = bdTna;
	}
	public Integer getIntEdadLimite() {
		return intEdadLimite;
	}
	public void setIntEdadLimite(Integer intEdadLimite) {
		this.intEdadLimite = intEdadLimite;
	}
	public Integer getIntTiempoSustento() {
		return intTiempoSustento;
	}
	public void setIntTiempoSustento(Integer intTiempoSustento) {
		this.intTiempoSustento = intTiempoSustento;
	}
	public Integer getIntPeriodicSustento() {
		return intPeriodicSustento;
	}
	public void setIntPeriodicSustento(Integer intPeriodicSustento) {
		this.intPeriodicSustento = intPeriodicSustento;
	}
	public Integer getIntParaTipoMaxMinSustCod() {
		return intParaTipoMaxMinSustCod;
	}
	public void setIntParaTipoMaxMinSustCod(Integer intParaTipoMaxMinSustCod) {
		this.intParaTipoMaxMinSustCod = intParaTipoMaxMinSustCod;
	}
	public Integer getIntCeseBeneficio() {
		return intCeseBeneficio;
	}
	public void setIntCeseBeneficio(Integer intCeseBeneficio) {
		this.intCeseBeneficio = intCeseBeneficio;
	}
	public Integer getIntNumeroCuota() {
		return intNumeroCuota;
	}
	public void setIntNumeroCuota(Integer intNumeroCuota) {
		this.intNumeroCuota = intNumeroCuota;
	}
	public Integer getIntTiempoPermanencia() {
		return intTiempoPermanencia;
	}
	public void setIntTiempoPermanencia(Integer intTiempoPermanencia) {
		this.intTiempoPermanencia = intTiempoPermanencia;
	}
	public BigDecimal getBdPenalidadPorcentaje() {
		return bdPenalidadPorcentaje;
	}
	public void setBdPenalidadPorcentaje(BigDecimal bdPenalidadPorcentaje) {
		this.bdPenalidadPorcentaje = bdPenalidadPorcentaje;
	}
	public BigDecimal getBdPenalidadTasaMora() {
		return bdPenalidadTasaMora;
	}
	public void setBdPenalidadTasaMora(BigDecimal bdPenalidadTasaMora) {
		this.bdPenalidadTasaMora = bdPenalidadTasaMora;
	}
	public Integer getIntParaPenalidadAplicacion() {
		return intParaPenalidadAplicacion;
	}
	public void setIntParaPenalidadAplicacion(Integer intParaPenalidadAplicacion) {
		this.intParaPenalidadAplicacion = intParaPenalidadAplicacion;
	}
	public Integer getIntTiempoAportacion() {
		return intTiempoAportacion;
	}
	public void setIntTiempoAportacion(Integer intTiempoAportacion) {
		this.intTiempoAportacion = intTiempoAportacion;
	}
	public Integer getIntPeriodicAportacionCod() {
		return intPeriodicAportacionCod;
	}
	public void setIntPeriodicAportacionCod(Integer intPeriodicAportacionCod) {
		this.intPeriodicAportacionCod = intPeriodicAportacionCod;
	}
	public Integer getIntParaTipoMaxMinAportCod() {
		return intParaTipoMaxMinAportCod;
	}
	public void setIntParaTipoMaxMinAportCod(Integer intParaTipoMaxMinAportCod) {
		this.intParaTipoMaxMinAportCod = intParaTipoMaxMinAportCod;
	}
	public Integer getIntCuota() {
		return intCuota;
	}
	public void setIntCuota(Integer intCuota) {
		this.intCuota = intCuota;
	}
	public Integer getIntParaPeriodicCuotasCod() {
		return intParaPeriodicCuotasCod;
	}
	public void setIntParaPeriodicCuotasCod(Integer intParaPeriodicCuotasCod) {
		this.intParaPeriodicCuotasCod = intParaPeriodicCuotasCod;
	}
	public Integer getIntParaTipoMaxMinCuotaCod() {
		return intParaTipoMaxMinCuotaCod;
	}
	public void setIntParaTipoMaxMinCuotaCod(Integer intParaTipoMaxMinCuotaCod) {
		this.intParaTipoMaxMinCuotaCod = intParaTipoMaxMinCuotaCod;
	}
	public Integer getIntTiempoDevolucion() {
		return intTiempoDevolucion;
	}
	public void setIntTiempoDevolucion(Integer intTiempoDevolucion) {
		this.intTiempoDevolucion = intTiempoDevolucion;
	}
	public Integer getIntParaTipoMaxMinDevolucionCod() {
		return intParaTipoMaxMinDevolucionCod;
	}
	public void setIntParaTipoMaxMinDevolucionCod(
			Integer intParaTipoMaxMinDevolucionCod) {
		this.intParaTipoMaxMinDevolucionCod = intParaTipoMaxMinDevolucionCod;
	}
	public Integer getIntParaTipoDia() {
		return intParaTipoDia;
	}
	public void setIntParaTipoDia(Integer intParaTipoDia) {
		this.intParaTipoDia = intParaTipoDia;
	}
	public Integer getIntRegularidad() {
		return intRegularidad;
	}
	public void setIntRegularidad(Integer intRegularidad) {
		this.intRegularidad = intRegularidad;
	}
	public Integer getIntPeriodicidad() {
		return intPeriodicidad;
	}
	public void setIntPeriodicidad(Integer intPeriodicidad) {
		this.intPeriodicidad = intPeriodicidad;
	}
	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	public String getStrDtFechaRegistro() {
		return strDtFechaRegistro;
	}
	public void setStrDtFechaRegistro(String strDtFechaRegistro) {
		this.strDtFechaRegistro = strDtFechaRegistro;
	}
	public Integer getIntParaEstadoSolicitudCod() {
		return intParaEstadoSolicitudCod;
	}
	public void setIntParaEstadoSolicitudCod(Integer intParaEstadoSolicitudCod) {
		this.intParaEstadoSolicitudCod = intParaEstadoSolicitudCod;
	}
	public Integer getIntParaEstadoCod() {
		return intParaEstadoCod;
	}
	public void setIntParaEstadoCod(Integer intParaEstadoCod) {
		this.intParaEstadoCod = intParaEstadoCod;
	}
	public Integer getIntTipoFecha() {
		return intTipoFecha;
	}
	public void setIntTipoFecha(Integer intTipoFecha) {
		this.intTipoFecha = intTipoFecha;
	}
	public Integer getIntTasaInteres() {
		return intTasaInteres;
	}
	public void setIntTasaInteres(Integer intTasaInteres) {
		this.intTasaInteres = intTasaInteres;
	}
	public Integer getIntLimiteEdad() {
		return intLimiteEdad;
	}
	public void setIntLimiteEdad(Integer intLimiteEdad) {
		this.intLimiteEdad = intLimiteEdad;
	}
	public Integer getIntVigencia() {
		return intVigencia;
	}
	public void setIntVigencia(Integer intVigencia) {
		this.intVigencia = intVigencia;
	}
	public Integer getIntAportacionVigente() {
		return intAportacionVigente;
	}
	public void setIntAportacionVigente(Integer intAportacionVigente) {
		this.intAportacionVigente = intAportacionVigente;
	}
	public Integer getIntTipoCuenta() {
		return intTipoCuenta;
	}
	public void setIntTipoCuenta(Integer intTipoCuenta) {
		this.intTipoCuenta = intTipoCuenta;
	}
	public List<ConvenioEstructuraDetalle> getListaConvenioDetalle() {
		return listaConvenioDetalle;
	}
	public void setListaConvenioDetalle(List<ConvenioEstructuraDetalle> listaConvenioDetalle) {
		this.listaConvenioDetalle = listaConvenioDetalle;
	}
	public List<Concepto> getListaConcepto() {
		return listaConcepto;
	}
	public void setListaConcepto(List<Concepto> listaConcepto) {
		this.listaConcepto = listaConcepto;
	}
	public List<Requisito> getListaRequisito() {
		return listaRequisito;
	}
	public void setListaRequisito(List<Requisito> listaRequisito) {
		this.listaRequisito = listaRequisito;
	}
	public List<Afecto> getListaAfecto() {
		return listaAfecto;
	}
	public void setListaAfecto(List<Afecto> listaAfecto) {
		this.listaAfecto = listaAfecto;
	}
	public List<Condicion> getListaCondicion() {
		return listaCondicion;
	}
	public void setListaCondicion(List<Condicion> listaCondicion) {
		this.listaCondicion = listaCondicion;
	}
	public List<CondicionComp> getListaCondicionComp() {
		return listaCondicionComp;
	}
	public void setListaCondicionComp(List<CondicionComp> listaCondicionComp) {
		this.listaCondicionComp = listaCondicionComp;
	}
	public List<Contable> getListaContable() {
		return listaContable;
	}
	public void setListaContable(List<Contable> listaContable) {
		this.listaContable = listaContable;
	}
	public List<Vinculo> getListaVinculo() {
		return listaVinculo;
	}
	public void setListaVinculo(List<Vinculo> listaVinculo) {
		this.listaVinculo = listaVinculo;
	}
	public String getStrRequisitosConcatenados() {
		return strRequisitosConcatenados;
	}
	public void setStrRequisitosConcatenados(String strRequisitosConcatenados) {
		this.strRequisitosConcatenados = strRequisitosConcatenados;
	}
	public String getStrVinculosConcatenados() {
		return strVinculosConcatenados;
	}
	public void setStrVinculosConcatenados(String strVinculosConcatenados) {
		this.strVinculosConcatenados = strVinculosConcatenados;
	}
	public String getStrConceptosConcatenados() {
		return strConceptosConcatenados;
	}
	public void setStrConceptosConcatenados(String strConceptosConcatenados) {
		this.strConceptosConcatenados = strConceptosConcatenados;
	}

}
