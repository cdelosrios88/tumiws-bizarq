package pe.com.tumi.credito.socio.captacion.controller;

import pe.com.tumi.credito.socio.captacion.domain.Captacion;

public class CaptacionController {

	private Captacion captacion;

	public CaptacionController() {
		if(captacion== null)
			captacion = new Captacion();
		captacion.setStrDescripcion("esto es una prueba");
		captacion.setIntParaTipopersonaCod(10);
	}

	public Captacion getCaptacion() {
		return captacion;
	}

	public void setCaptacion(Captacion captacion) {
		this.captacion = captacion;
	}
	
	
}
