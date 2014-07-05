package pe.com.tumi.cobranza.planilla.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class EfectuadoConcepto extends TumiDomain{
		private EfectuadoConceptoId id;
		
		private java.lang.Integer intItemCuentaConcepto;
		private java.lang.Integer intItemExpediente;
		private java.lang.Integer intItemDetExpediente;
		private java.lang.Integer intTipoConceptoGeneralCod;
     	private java.math.BigDecimal bdMontoConcepto;
     	private java.lang.Integer intEstadoCod;
     	
     	private java.lang.Integer intOrdenPrioridad;
     	
     	private Efectuado efectuado;
     	
     	//auxiliares
     	private java.lang.Integer intParaTipoConceptoCod;
    	private  java.lang.Integer intItemConcepto;
    	private java.lang.Integer intIdsucursaladministraPk;
     	private java.lang.Integer intIdsubsucursaladministra;
    	private String strContNumeroCuenta;
    	private Integer intParaOpcionDebeHaber;
    	private  java.lang.Integer intCategoria;
    	
     	public EfectuadoConcepto()
     	{
     		id= new EfectuadoConceptoId();
     	}
     	
		public EfectuadoConceptoId getId() {
			return id;
		}
		public void setId(EfectuadoConceptoId id) {
			this.id = id;
		}

		public java.lang.Integer getIntItemCuentaConcepto() {
			return intItemCuentaConcepto;
		}
		public void setIntItemCuentaConcepto(java.lang.Integer intItemCuentaConcepto) {
			this.intItemCuentaConcepto = intItemCuentaConcepto;
		}

		public java.lang.Integer getIntItemExpediente() {
			return intItemExpediente;
		}
		public void setIntItemExpediente(java.lang.Integer intItemExpediente) {
			this.intItemExpediente = intItemExpediente;
		}

		public java.lang.Integer getIntItemDetExpediente() {
			return intItemDetExpediente;
		}
		public void setIntItemDetExpediente(java.lang.Integer intItemDetExpediente) {
			this.intItemDetExpediente = intItemDetExpediente;
		}

		public java.lang.Integer getIntTipoConceptoGeneralCod() {
			return intTipoConceptoGeneralCod;
		}
		public void setIntTipoConceptoGeneralCod(
				java.lang.Integer intTipoConceptoGeneralCod) {
			this.intTipoConceptoGeneralCod = intTipoConceptoGeneralCod;
		}

		public java.math.BigDecimal getBdMontoConcepto() {
			return bdMontoConcepto;
		}
		public void setBdMontoConcepto(java.math.BigDecimal bdMontoConcepto) {
			this.bdMontoConcepto = bdMontoConcepto;
		}

		public java.lang.Integer getIntEstadoCod() {
			return intEstadoCod;
		}
		public void setIntEstadoCod(java.lang.Integer intEstadoCod) {
			this.intEstadoCod = intEstadoCod;
		}

		public Efectuado getEfectuado() {
			return efectuado;
		}
		public void setEfectuado(Efectuado efectuado) {
			this.efectuado = efectuado;
		}	
		
		public java.lang.Integer getIntOrdenPrioridad() {
			return intOrdenPrioridad;
		}

		public void setIntOrdenPrioridad(java.lang.Integer intOrdenPrioridad) {
			this.intOrdenPrioridad = intOrdenPrioridad;
		}
				


		public java.lang.Integer getIntParaTipoConceptoCod() {
			return intParaTipoConceptoCod;
		}

		public void setIntParaTipoConceptoCod(java.lang.Integer intParaTipoConceptoCod) {
			this.intParaTipoConceptoCod = intParaTipoConceptoCod;
		}

		public Integer getIntItemConcepto() {
			return intItemConcepto;
		}

		public void setIntItemConcepto(Integer intItemConcepto) {
			this.intItemConcepto = intItemConcepto;
		}
				
		public java.lang.Integer getIntIdsucursaladministraPk() {
			return intIdsucursaladministraPk;
		}

		public void setIntIdsucursaladministraPk(
				java.lang.Integer intIdsucursaladministraPk) {
			this.intIdsucursaladministraPk = intIdsucursaladministraPk;
		}

		public java.lang.Integer getIntIdsubsucursaladministra() {
			return intIdsubsucursaladministra;
		}

		public void setIntIdsubsucursaladministra(
				java.lang.Integer intIdsubsucursaladministra) {
			this.intIdsubsucursaladministra = intIdsubsucursaladministra;
		}

		public String getStrContNumeroCuenta() {
			return strContNumeroCuenta;
		}

		public void setStrContNumeroCuenta(String strContNumeroCuenta) {
			this.strContNumeroCuenta = strContNumeroCuenta;
		}
			
		public Integer getIntParaOpcionDebeHaber() {
			return intParaOpcionDebeHaber;
		}

		public void setIntParaOpcionDebeHaber(Integer intParaOpcionDebeHaber) {
			this.intParaOpcionDebeHaber = intParaOpcionDebeHaber;
		}



		public java.lang.Integer getIntCategoria() {
			return intCategoria;
		}

		public void setIntCategoria(java.lang.Integer intCategoria) {
			this.intCategoria = intCategoria;
		}

		@Override
		public String toString() {
			return "EfectuadoConcepto [intItemCuentaConcepto="
					+ intItemCuentaConcepto + ", intItemExpediente="
					+ intItemExpediente + ", intTipoConceptoGeneralCod="
					+ intTipoConceptoGeneralCod + ", bdMontoConcepto="
					+ bdMontoConcepto + ", intItemConcepto=" + intItemConcepto
					+ ", intIdsucursaladministraPk="
					+ intIdsucursaladministraPk
					+ ", intIdsubsucursaladministra="
					+ intIdsubsucursaladministra + ", strContNumeroCuenta="
					+ strContNumeroCuenta + ", intParaOpcionDebeHaber="
					+ intParaOpcionDebeHaber + "]";
		}
		
}
