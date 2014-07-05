package pe.com.tumi.servicio.solicitudPrestamo.domain;

import java.math.BigDecimal;
import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class CancelacionCredito extends TumiDomain {
	private CancelacionCreditoId id;
	private Integer		intItemExpediente;
	private Integer		intItemDetExpediente;
	private BigDecimal	bdMontoCancelado;
	
	public CancelacionCredito(){
		id = new CancelacionCreditoId();
	}
	
	public CancelacionCreditoId getId() {
		return id;
	}
	public void setId(CancelacionCreditoId id) {
		this.id = id;
	}
	public Integer getIntItemExpediente() {
		return intItemExpediente;
	}
	public void setIntItemExpediente(Integer intItemExpediente) {
		this.intItemExpediente = intItemExpediente;
	}
	public BigDecimal getBdMontoCancelado() {
		return bdMontoCancelado;
	}
	public void setBdMontoCancelado(BigDecimal bdMontoCancelado) {
		this.bdMontoCancelado = bdMontoCancelado;
	}
	
	public Integer getIntItemDetExpediente() {
		return intItemDetExpediente;
	}

	public void setIntItemDetExpediente(Integer intItemDetExpediente) {
		this.intItemDetExpediente = intItemDetExpediente;
	}

	@Override
	public String toString() {
		return "CancelacionCredito [id=" + id + ", intItemExpediente="
				+ intItemExpediente + ", bdMontoCancelado=" + bdMontoCancelado
				+ "]";
	}

}