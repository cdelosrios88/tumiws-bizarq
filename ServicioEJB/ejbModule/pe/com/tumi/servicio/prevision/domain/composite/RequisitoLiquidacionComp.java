package pe.com.tumi.servicio.prevision.domain.composite;

import pe.com.tumi.common.MyFile;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;

public class RequisitoLiquidacionComp extends TumiDomain{

	private RequisitoLiquidacion requisitoLiquidacion;
	private ConfServDetalle detalle;
	private Archivo archivoAdjunto;
	private MyFile fileDocAdjunto;
	
	/*
	 * JCHAVEZ 06.02.2014
	 * Atributos de requisitos necesarios para el Giro de Liquidación
	 */
	private Integer intEmpresa;
	private Integer intItemRequisito;
	private Integer intItemRequisitoDetalle;
	private String strDescripcionRequisito;
	private Integer intParaTipoOperacion;
	private Integer intParaSubTipoOperacion;
	
	public RequisitoLiquidacion getRequisitoLiquidacion() {
		return requisitoLiquidacion;
	}
	public void setRequisitoLiquidacion(RequisitoLiquidacion requisitoLiquidacion) {
		this.requisitoLiquidacion = requisitoLiquidacion;
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
	//JCHAVEZ 08.02.2014
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
	public Integer getIntParaSubTipoOperacion() {
		return intParaSubTipoOperacion;
	}
	public void setIntParaSubTipoOperacion(Integer intParaSubTipoOperacion) {
		this.intParaSubTipoOperacion = intParaSubTipoOperacion;
	}
	public Integer getIntParaTipoOperacion() {
		return intParaTipoOperacion;
	}
	public void setIntParaTipoOperacion(Integer intParaTipoOperacion) {
		this.intParaTipoOperacion = intParaTipoOperacion;
	}
}
