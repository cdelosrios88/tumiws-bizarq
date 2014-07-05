package pe.com.tumi.servicio.solicitudPrestamo.domain.composite;

import pe.com.tumi.common.MyFile;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;

public class RequisitoCreditoComp extends TumiDomain{
	private RequisitoCredito requisitoCredito;
	private ConfServDetalle detalle;
	private Archivo archivoAdjunto;
	private MyFile fileDocAdjunto;
	
	/*
	 * JCHAVEZ 31.01.2014
	 * Atributos de requisitos necesarios para el Giro del Préstamo
	 */
	private Integer intEmpresa;
	private Integer intItemRequisito;
	private Integer intItemRequisitoDetalle;
	private String strDescripcionRequisito;
	private Integer intParaTipoCredito;
	private Integer intItemCredito;

	public RequisitoCredito getRequisitoCredito() {
		return requisitoCredito;
	}
	public void setRequisitoCredito(RequisitoCredito requisitoCredito) {
		this.requisitoCredito = requisitoCredito;
	}
	public ConfServDetalle getDetalle() {
		return detalle;
	}
	public void setDetalle(ConfServDetalle detalle) {
		this.detalle = detalle;
	}
	public Archivo getArchivoAdjunto() {
		return archivoAdjunto;
	}
	public void setArchivoAdjunto(Archivo archivoAdjunto) {
		this.archivoAdjunto = archivoAdjunto;
	}
	public MyFile getFileDocAdjunto() {
		return fileDocAdjunto;
	}
	public void setFileDocAdjunto(MyFile fileDocAdjunto) {
		this.fileDocAdjunto = fileDocAdjunto;
	}
	//JCHAVEZ 31.01.2014
	public Integer getIntEmpresa() {
		return intEmpresa;
	}
	public void setIntEmpresa(Integer intEmpresa) {
		this.intEmpresa = intEmpresa;
	}
	public Integer getIntItemRequisito() {
		return intItemRequisito;
	}
	public void setIntItemRequisito(Integer intItemRequisito) {
		this.intItemRequisito = intItemRequisito;
	}
	public Integer getIntItemRequisitoDetalle() {
		return intItemRequisitoDetalle;
	}
	public void setIntItemRequisitoDetalle(Integer intItemRequisitoDetalle) {
		this.intItemRequisitoDetalle = intItemRequisitoDetalle;
	}
	public String getStrDescripcionRequisito() {
		return strDescripcionRequisito;
	}
	public void setStrDescripcionRequisito(String strDescripcionRequisito) {
		this.strDescripcionRequisito = strDescripcionRequisito;
	}
	public Integer getIntParaTipoCredito() {
		return intParaTipoCredito;
	}
	public void setIntParaTipoCredito(Integer intParaTipoCredito) {
		this.intParaTipoCredito = intParaTipoCredito;
	}
	public Integer getIntItemCredito() {
		return intItemCredito;
	}
	public void setIntItemCredito(Integer intItemCredito) {
		this.intItemCredito = intItemCredito;
	}
}