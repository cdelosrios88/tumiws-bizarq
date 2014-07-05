package pe.com.tumi.servicio.test.controller;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;

public class TestController {

	private ConfServSolicitud dto;

	public TestController() {
		if(dto== null)
			dto = new ConfServSolicitud();
		dto.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
	}

	public ConfServSolicitud getDto() {
		return dto;
	}
	public void setDto(ConfServSolicitud dto) {
		this.dto = dto;
	}
	
}
