package pe.com.tumi.tesoreria.logistica.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DocumentoSunatDoc extends TumiDomain{
	//BD
	private DocumentoSunatDocId	id;
	private Integer 	intParaTipoDocumentoGeneral;	//PARA_TIPODOCUMENTOGENERAL_N_CO
	private BigDecimal 	bdMontoDocumento;				//DOSD_MONTODOCUMENTO_N
	private Timestamp 	tsFechaRegistro;				//DOSD_FECHAREGISTRO_D
	private Date 		dtFechaVencimiento;				//DOSD_FECHAVENCIMIENTO_D
	private Integer 	intParaEstado;					//PARA_ESTADO_N_COD
	private Integer 	intParaEstadoPagoDocSunat;		//PARA_ESTADOPAGODS_N_COD	
	private Integer 	intPersEmpresaEgresoDocSunat;	//PERS_EMPRESAEGRESODS_N_PK
	private Integer 	intItemEgresoGeneralDocSuant;	//TESO_ITEMEGRESOGENERALDS_N	
	private Integer 	intParaTipo;					//PARA_TIPO_N_COD	
	private Integer 	intItemArchivo;					//MAE_ITEMARCHIVO_N
	private Integer 	intItemHistorico;				//MAE_ITEMHISTORICO_N
	private Integer 	intParaEstadoPago;				//PARA_ESTADOPAGO_N_COD
	private Integer 	intPersEmpresaEgreso;			//PERS_EMPRESAEGRESO_N_PK
	private Integer 	intItemEgresoGeneral;			//TESO_ITEMEGRESOGENERAL_N
	private Integer 	intPersEmpresaUsuario;			//PERS_EMPRESAUSUARIO_N_PK
	private Integer 	intPersPersonaUsuario;			//PERS_PERSONAUSUARIO_N_PK
	private Integer 	intPersEmpresaAnula;			//PERS_EMPRESAANULA_N_PK
	private Integer 	intPersPersonaAnula;			//PERS_PERSONAANULA_N_PK
	private Timestamp 	tsFechaAnula;					//DSDO_FECHAANULA_D
	private Integer 	intPersEmpresaCanjea;			//PERS_EMPRESACANJEA_N_PK
	private Integer 	intPersPersonaCanjea;			//PERS_PERSONACANJEA_N_PK
	private String 		strObservacionCanjea;			//DSDO_OBSERVACIONCANJEA_V
	private Timestamp 	tsFechaCanjea;					//DSDO_FECHACANJEA_D
	private Integer 	intParaTipoCanjea;				//PARA_TIPOCANJEA_N_COD
	private Integer 	intItemArchivoCanjea;			//MAE_ITEMARCHIVOCANJEA_N
	private Integer 	intItemHistoricoCanjea;			//MAE_ITEMHISTORICOCANJEA_N
	private Integer 	intPersEmpresaIngreso;			//PERS_EMPRESAINGRESO_N_PK
	private Integer 	intItemIngresoGeneral;			//TESO_ITEMINGRESOGENERAL_N
	
	//ADICIONALES
	private Integer 	intTipoMoneda;
	private BigDecimal	bdTipoCambio;
	private BigDecimal 	bdMontoAplicar;
	private BigDecimal	bdMontoSaldoTemp;
	
	public DocumentoSunatDocId getId() {
		return id;
	}
	public void setId(DocumentoSunatDocId id) {
		this.id = id;
	}
	public Integer getIntParaTipoDocumentoGeneral() {
		return intParaTipoDocumentoGeneral;
	}
	public void setIntParaTipoDocumentoGeneral(Integer intParaTipoDocumentoGeneral) {
		this.intParaTipoDocumentoGeneral = intParaTipoDocumentoGeneral;
	}
	public BigDecimal getBdMontoDocumento() {
		return bdMontoDocumento;
	}
	public void setBdMontoDocumento(BigDecimal bdMontoDocumento) {
		this.bdMontoDocumento = bdMontoDocumento;
	}
	public Timestamp getTsFechaRegistro() {
		return tsFechaRegistro;
	}
	public void setTsFechaRegistro(Timestamp tsFechaRegistro) {
		this.tsFechaRegistro = tsFechaRegistro;
	}
	public Date getDtFechaVencimiento() {
		return dtFechaVencimiento;
	}
	public void setDtFechaVencimiento(Date dtFechaVencimiento) {
		this.dtFechaVencimiento = dtFechaVencimiento;
	}
	public Integer getIntParaEstado() {
		return intParaEstado;
	}
	public void setIntParaEstado(Integer intParaEstado) {
		this.intParaEstado = intParaEstado;
	}
	public Integer getIntParaEstadoPagoDocSunat() {
		return intParaEstadoPagoDocSunat;
	}
	public void setIntParaEstadoPagoDocSunat(Integer intParaEstadoPagoDocSunat) {
		this.intParaEstadoPagoDocSunat = intParaEstadoPagoDocSunat;
	}
	public Integer getIntPersEmpresaEgresoDocSunat() {
		return intPersEmpresaEgresoDocSunat;
	}
	public void setIntPersEmpresaEgresoDocSunat(Integer intPersEmpresaEgresoDocSunat) {
		this.intPersEmpresaEgresoDocSunat = intPersEmpresaEgresoDocSunat;
	}
	public Integer getIntItemEgresoGeneralDocSuant() {
		return intItemEgresoGeneralDocSuant;
	}
	public void setIntItemEgresoGeneralDocSuant(Integer intItemEgresoGeneralDocSuant) {
		this.intItemEgresoGeneralDocSuant = intItemEgresoGeneralDocSuant;
	}
	public Integer getIntParaTipo() {
		return intParaTipo;
	}
	public void setIntParaTipo(Integer intParaTipo) {
		this.intParaTipo = intParaTipo;
	}
	public Integer getIntItemArchivo() {
		return intItemArchivo;
	}
	public void setIntItemArchivo(Integer intItemArchivo) {
		this.intItemArchivo = intItemArchivo;
	}
	public Integer getIntItemHistorico() {
		return intItemHistorico;
	}
	public void setIntItemHistorico(Integer intItemHistorico) {
		this.intItemHistorico = intItemHistorico;
	}
	public Integer getIntParaEstadoPago() {
		return intParaEstadoPago;
	}
	public void setIntParaEstadoPago(Integer intParaEstadoPago) {
		this.intParaEstadoPago = intParaEstadoPago;
	}
	public Integer getIntPersEmpresaEgreso() {
		return intPersEmpresaEgreso;
	}
	public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
		this.intPersEmpresaEgreso = intPersEmpresaEgreso;
	}
	public Integer getIntItemEgresoGeneral() {
		return intItemEgresoGeneral;
	}
	public void setIntItemEgresoGeneral(Integer intItemEgresoGeneral) {
		this.intItemEgresoGeneral = intItemEgresoGeneral;
	}
	public Integer getIntPersEmpresaUsuario() {
		return intPersEmpresaUsuario;
	}
	public void setIntPersEmpresaUsuario(Integer intPersEmpresaUsuario) {
		this.intPersEmpresaUsuario = intPersEmpresaUsuario;
	}
	public Integer getIntPersPersonaUsuario() {
		return intPersPersonaUsuario;
	}
	public void setIntPersPersonaUsuario(Integer intPersPersonaUsuario) {
		this.intPersPersonaUsuario = intPersPersonaUsuario;
	}
	public Integer getIntPersEmpresaAnula() {
		return intPersEmpresaAnula;
	}
	public void setIntPersEmpresaAnula(Integer intPersEmpresaAnula) {
		this.intPersEmpresaAnula = intPersEmpresaAnula;
	}
	public Integer getIntPersPersonaAnula() {
		return intPersPersonaAnula;
	}
	public void setIntPersPersonaAnula(Integer intPersPersonaAnula) {
		this.intPersPersonaAnula = intPersPersonaAnula;
	}
	public Timestamp getTsFechaAnula() {
		return tsFechaAnula;
	}
	public void setTsFechaAnula(Timestamp tsFechaAnula) {
		this.tsFechaAnula = tsFechaAnula;
	}
	public Integer getIntPersEmpresaCanjea() {
		return intPersEmpresaCanjea;
	}
	public void setIntPersEmpresaCanjea(Integer intPersEmpresaCanjea) {
		this.intPersEmpresaCanjea = intPersEmpresaCanjea;
	}
	public Integer getIntPersPersonaCanjea() {
		return intPersPersonaCanjea;
	}
	public void setIntPersPersonaCanjea(Integer intPersPersonaCanjea) {
		this.intPersPersonaCanjea = intPersPersonaCanjea;
	}
	public String getStrObservacionCanjea() {
		return strObservacionCanjea;
	}
	public void setStrObservacionCanjea(String strObservacionCanjea) {
		this.strObservacionCanjea = strObservacionCanjea;
	}
	public Timestamp getTsFechaCanjea() {
		return tsFechaCanjea;
	}
	public void setTsFechaCanjea(Timestamp tsFechaCanjea) {
		this.tsFechaCanjea = tsFechaCanjea;
	}
	public Integer getIntParaTipoCanjea() {
		return intParaTipoCanjea;
	}
	public void setIntParaTipoCanjea(Integer intParaTipoCanjea) {
		this.intParaTipoCanjea = intParaTipoCanjea;
	}
	public Integer getIntItemArchivoCanjea() {
		return intItemArchivoCanjea;
	}
	public void setIntItemArchivoCanjea(Integer intItemArchivoCanjea) {
		this.intItemArchivoCanjea = intItemArchivoCanjea;
	}
	public Integer getIntItemHistoricoCanjea() {
		return intItemHistoricoCanjea;
	}
	public void setIntItemHistoricoCanjea(Integer intItemHistoricoCanjea) {
		this.intItemHistoricoCanjea = intItemHistoricoCanjea;
	}
	public Integer getIntPersEmpresaIngreso() {
		return intPersEmpresaIngreso;
	}
	public void setIntPersEmpresaIngreso(Integer intPersEmpresaIngreso) {
		this.intPersEmpresaIngreso = intPersEmpresaIngreso;
	}
	public Integer getIntItemIngresoGeneral() {
		return intItemIngresoGeneral;
	}
	public void setIntItemIngresoGeneral(Integer intItemIngresoGeneral) {
		this.intItemIngresoGeneral = intItemIngresoGeneral;
	}
	public Integer getIntTipoMoneda() {
		return intTipoMoneda;
	}
	public void setIntTipoMoneda(Integer intTipoMoneda) {
		this.intTipoMoneda = intTipoMoneda;
	}
	public BigDecimal getBdTipoCambio() {
		return bdTipoCambio;
	}
	public void setBdTipoCambio(BigDecimal bdTipoCambio) {
		this.bdTipoCambio = bdTipoCambio;
	}
	public BigDecimal getBdMontoAplicar() {
		return bdMontoAplicar;
	}
	public void setBdMontoAplicar(BigDecimal bdMontoAplicar) {
		this.bdMontoAplicar = bdMontoAplicar;
	}
	public BigDecimal getBdMontoSaldoTemp() {
		return bdMontoSaldoTemp;
	}
	public void setBdMontoSaldoTemp(BigDecimal bdMontoSaldoTemp) {
		this.bdMontoSaldoTemp = bdMontoSaldoTemp;
	}
}