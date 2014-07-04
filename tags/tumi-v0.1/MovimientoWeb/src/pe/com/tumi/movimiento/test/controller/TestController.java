package pe.com.tumi.movimiento.test.controller;

import java.math.BigDecimal;

import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;

public class TestController {

	private CuentaConcepto dto;

	public TestController() {
		if(dto== null)
			dto = new CuentaConcepto();
		dto.setBdSaldo(new BigDecimal(10));
	}

	public CuentaConcepto getDto() {
		return dto;
	}
	public void setDto(CuentaConcepto dto) {
		this.dto = dto;
	}
	
}
