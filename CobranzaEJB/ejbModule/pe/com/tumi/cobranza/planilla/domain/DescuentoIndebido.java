package pe.com.tumi.cobranza.planilla.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class DescuentoIndebido extends TumiDomain{
		private DescuentoIndebidoId id;
		
		private Timestamp	tsDeinFecha	;
		private	Integer intParaDocumentoGeneralcod;
		private	Integer intCsocCuenta;
		private	Integer intPersEmpresaCuentaenv	;
		private	Integer intCcobItemEfectuado	;
		private	Integer intCcobItemEfectuadoconcepto	;
		private	BigDecimal bdDeinMonto	;
		private	Integer intParaEstadocod	;
		private	Integer intParaEstadoPagadocod	;
		private	Integer intPersEmpresaEgreso	;
		private Integer	intTesoItemEgresoGeneral	;
		private	Integer intPersEmpresaLibro	;
		private	Integer intContPeriodoLibro	;
		private	Integer intContCodigoLibro	;
		private	Integer intPersEmpresaSolctacte	;
		private	Integer intCcobItemsolCtacte	;
		private	Integer intParaTiposolicitudCtactecod	;
		
		//Auxiliares.
		
		private String periodoPlanilla;
		private SocioEstructura socioEstructura;
		
		
		public DescuentoIndebidoId getId() {
			return id;
		}
		public void setId(DescuentoIndebidoId id) {
			this.id = id;
		}
		public Timestamp getTsDeinFecha() {
			return tsDeinFecha;
		}
		public void setTsDeinFecha(Timestamp tsDeinFecha) {
			this.tsDeinFecha = tsDeinFecha;
		}
		public Integer getIntParaDocumentoGeneralcod() {
			return intParaDocumentoGeneralcod;
		}
		public void setIntParaDocumentoGeneralcod(Integer intParaDocumentoGeneralcod) {
			this.intParaDocumentoGeneralcod = intParaDocumentoGeneralcod;
		}
		public Integer getIntCsocCuenta() {
			return intCsocCuenta;
		}
		public void setIntCsocCuenta(Integer intCsocCuenta) {
			this.intCsocCuenta = intCsocCuenta;
		}
		public Integer getIntPersEmpresaCuentaenv() {
			return intPersEmpresaCuentaenv;
		}
		public void setIntPersEmpresaCuentaenv(Integer intPersEmpresaCuentaenv) {
			this.intPersEmpresaCuentaenv = intPersEmpresaCuentaenv;
		}
		public Integer getIntCcobItemEfectuado() {
			return intCcobItemEfectuado;
		}
		public void setIntCcobItemEfectuado(Integer intCcobItemEfectuado) {
			this.intCcobItemEfectuado = intCcobItemEfectuado;
		}
		public Integer getIntCcobItemEfectuadoconcepto() {
			return intCcobItemEfectuadoconcepto;
		}
		public void setIntCcobItemEfectuadoconcepto(Integer intCcobItemEfectuadoconcepto) {
			this.intCcobItemEfectuadoconcepto = intCcobItemEfectuadoconcepto;
		}
		public BigDecimal getBdDeinMonto() {
			return bdDeinMonto;
		}
		public void setBdDeinMonto(BigDecimal bdDeinMonto) {
			this.bdDeinMonto = bdDeinMonto;
		}
		public Integer getIntParaEstadocod() {
			return intParaEstadocod;
		}
		public void setIntParaEstadocod(Integer intParaEstadocod) {
			this.intParaEstadocod = intParaEstadocod;
		}
		public Integer getIntParaEstadoPagadocod() {
			return intParaEstadoPagadocod;
		}
		public void setIntParaEstadoPagadocod(Integer intParaEstadoPagadocod) {
			this.intParaEstadoPagadocod = intParaEstadoPagadocod;
		}
		public Integer getIntPersEmpresaEgreso() {
			return intPersEmpresaEgreso;
		}
		public void setIntPersEmpresaEgreso(Integer intPersEmpresaEgreso) {
			this.intPersEmpresaEgreso = intPersEmpresaEgreso;
		}
		public Integer getIntTesoItemEgresoGeneral() {
			return intTesoItemEgresoGeneral;
		}
		public void setIntTesoItemEgresoGeneral(Integer intTesoItemEgresoGeneral) {
			this.intTesoItemEgresoGeneral = intTesoItemEgresoGeneral;
		}
		public Integer getIntPersEmpresaLibro() {
			return intPersEmpresaLibro;
		}
		public void setIntPersEmpresaLibro(Integer intPersEmpresaLibro) {
			this.intPersEmpresaLibro = intPersEmpresaLibro;
		}
		public Integer getIntContPeriodoLibro() {
			return intContPeriodoLibro;
		}
		public void setIntContPeriodoLibro(Integer intContPeriodoLibro) {
			this.intContPeriodoLibro = intContPeriodoLibro;
		}
		public Integer getIntContCodigoLibro() {
			return intContCodigoLibro;
		}
		public void setIntContCodigoLibro(Integer intContCodigoLibro) {
			this.intContCodigoLibro = intContCodigoLibro;
		}
		public Integer getIntPersEmpresaSolctacte() {
			return intPersEmpresaSolctacte;
		}
		public void setIntPersEmpresaSolctacte(Integer intPersEmpresaSolctacte) {
			this.intPersEmpresaSolctacte = intPersEmpresaSolctacte;
		}
		public Integer getIntCcobItemsolCtacte() {
			return intCcobItemsolCtacte;
		}
		public void setIntCcobItemsolCtacte(Integer intCcobItemsolCtacte) {
			this.intCcobItemsolCtacte = intCcobItemsolCtacte;
		}
		public Integer getIntParaTiposolicitudCtactecod() {
			return intParaTiposolicitudCtactecod;
		}
		public void setIntParaTiposolicitudCtactecod(
				Integer intParaTiposolicitudCtactecod) {
			this.intParaTiposolicitudCtactecod = intParaTiposolicitudCtactecod;
		}
		
		public SocioEstructura getSocioEstructura() {
			return socioEstructura;
		}
		public void setSocioEstructura(SocioEstructura socioEstructura) {
			this.socioEstructura = socioEstructura;
		}
		public String getPeriodoPlanilla() {
			return periodoPlanilla;
		}
		public void setPeriodoPlanilla(String periodoPlanilla) {
			this.periodoPlanilla = periodoPlanilla;
		}
		
		
		

		
}
