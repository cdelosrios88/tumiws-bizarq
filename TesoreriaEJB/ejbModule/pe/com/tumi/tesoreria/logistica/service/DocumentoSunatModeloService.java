package pe.com.tumi.tesoreria.logistica.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;

public class DocumentoSunatModeloService {

	protected static Logger log = Logger.getLogger(DocumentoSunatModeloService.class);
	
	

	private boolean validarListaModeloDetalleNivel(ModeloDetalle modeloDetalle, List<ModeloDetalleNivel> listaModeloDetalleNivelRequisitos){
		for(ModeloDetalleNivel modeloDetalleNivelRequisito : listaModeloDetalleNivelRequisitos){
			boolean seEncuentra = Boolean.FALSE;
			//log.info(modeloDetalleNivelRequisito);
			for(ModeloDetalleNivel modeloDetalleNivel : modeloDetalle.getListModeloDetalleNivel()){
				//log.info(modeloDetalleNivel);
				if(MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getStrDescripcion(), modeloDetalleNivel.getStrDescripcion())
				&& MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getIntParaTipoRegistro(), modeloDetalleNivel.getIntParaTipoRegistro())
				&& MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getIntDatoTablas(), modeloDetalleNivel.getIntDatoTablas())
				&& MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getIntDatoArgumento(), modeloDetalleNivel.getIntDatoArgumento())
				&& MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getIntValor(), modeloDetalleNivel.getIntValor())){
					seEncuentra = Boolean.TRUE;
					break;
				}
			}
			//log.info("seEncuentra:"+seEncuentra);
			if(!seEncuentra){
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	private boolean validarListaModeloDetalleFacturasPorPagar(ModeloDetalle modeloDetalle, ModeloDetalleNivel modeloDetalleNivelRequisito){
		boolean seEncuentra = Boolean.FALSE;
		for(ModeloDetalleNivel modeloDetalleNivel : modeloDetalle.getListModeloDetalleNivel()){
			if(MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getStrDescripcion(), modeloDetalleNivel.getStrDescripcion())
			&& MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getIntParaTipoRegistro(), modeloDetalleNivel.getIntParaTipoRegistro())
			&& MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getIntDatoTablas(), modeloDetalleNivel.getIntDatoTablas())
			&& MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getIntDatoArgumento(), modeloDetalleNivel.getIntDatoArgumento())
			&& MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getIntValor(), modeloDetalleNivel.getIntValor())){
				seEncuentra = Boolean.TRUE;
				break;
			}
		}			
		
		return seEncuentra;
	}
	
	private boolean validarListaModeloDetalleNivel2(ModeloDetalle modeloDetalle, List<ModeloDetalleNivel> listaModeloDetalleNivelRequisitos){
		for(ModeloDetalleNivel modeloDetalleNivelRequisito : listaModeloDetalleNivelRequisitos){
			boolean seEncuentra = Boolean.FALSE;
			//log.info(modeloDetalleNivelRequisito);
			for(ModeloDetalleNivel modeloDetalleNivel : modeloDetalle.getListModeloDetalleNivel()){
				//log.info(modeloDetalleNivel);
				if(MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getStrDescripcion(), modeloDetalleNivel.getStrDescripcion())
				&& MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getIntParaTipoRegistro(), modeloDetalleNivel.getIntParaTipoRegistro())
				&& MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getIntDatoTablas(), modeloDetalleNivel.getIntDatoTablas())
				&& MyUtil.equalsWithNulls(modeloDetalleNivelRequisito.getIntDatoArgumento(), modeloDetalleNivel.getIntDatoArgumento())){
					seEncuentra = Boolean.TRUE;
					break;
				}
			}
			//log.info("seEncuentra:"+seEncuentra);
			if(!seEncuentra){
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	
	public ModeloDetalle obtenerModeloDetalleIGV(Modelo modelo)throws Exception{
		ModeloDetalle modeloDetalleIGV = null;
		
		ModeloDetalleNivel modeloDetalleNivel1 = new ModeloDetalleNivel();
		modeloDetalleNivel1.setStrDescripcion("Para_TipoCptoDocSunat_N_Cod");
		modeloDetalleNivel1.setIntParaTipoRegistro(Constante.PARAM_T_TIPOREGISTROMODELO_TABLA);
		modeloDetalleNivel1.setIntDatoTablas(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_);
		modeloDetalleNivel1.setIntDatoArgumento(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_IGV);
		
		ModeloDetalleNivel modeloDetalleNivel2 = generarModeloDetalleNivelTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA);
		
		ModeloDetalleNivel modeloDetalleNivel3 = new ModeloDetalleNivel();
		modeloDetalleNivel3.setStrDescripcion("DoSu_IndicadorIGV_N");
		modeloDetalleNivel3.setIntParaTipoRegistro(Constante.PARAM_T_TIPOREGISTROMODELO_VALORFIJO);
		modeloDetalleNivel3.setIntValor(1);
		
		List<ModeloDetalleNivel> listaModeloDetalleNivelRequisitos = new ArrayList<ModeloDetalleNivel>();
		listaModeloDetalleNivelRequisitos.add(modeloDetalleNivel1);
		listaModeloDetalleNivelRequisitos.add(modeloDetalleNivel2);
		listaModeloDetalleNivelRequisitos.add(modeloDetalleNivel3);
		
		for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){			
			if(validarListaModeloDetalleNivel(modeloDetalle, listaModeloDetalleNivelRequisitos)){
				modeloDetalleIGV = modeloDetalle;
				break;
			}
		}
		return modeloDetalleIGV;
	}
	
	
	public ModeloDetalle obtenerModeloDetalleIGVPercepciones(Modelo modelo)throws Exception{
		ModeloDetalle modeloDetalleIGVPercepciones = null;
		
		ModeloDetalleNivel modeloDetalleNivel1 = new ModeloDetalleNivel();
		modeloDetalleNivel1.setStrDescripcion("Para_TipoCptoDocSunat_N_Cod");
		modeloDetalleNivel1.setIntParaTipoRegistro(Constante.PARAM_T_TIPOREGISTROMODELO_TABLA);
		modeloDetalleNivel1.setIntDatoTablas(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_);
		modeloDetalleNivel1.setIntDatoArgumento(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_PERCEPCION);
		
		ModeloDetalleNivel modeloDetalleNivel2 = generarModeloDetalleNivelTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_FACTURA);
		
		ModeloDetalleNivel modeloDetalleNivel3 = new ModeloDetalleNivel();
		modeloDetalleNivel3.setStrDescripcion("DoSu_IndicadorPercepcion_N");
		modeloDetalleNivel3.setIntParaTipoRegistro(Constante.PARAM_T_TIPOREGISTROMODELO_VALORFIJO);
		modeloDetalleNivel3.setIntValor(1);
		
		List<ModeloDetalleNivel> listaModeloDetalleNivelRequisitos = new ArrayList<ModeloDetalleNivel>();
		listaModeloDetalleNivelRequisitos.add(modeloDetalleNivel1);
		listaModeloDetalleNivelRequisitos.add(modeloDetalleNivel2);
		listaModeloDetalleNivelRequisitos.add(modeloDetalleNivel3);
		
		for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){			
			if(validarListaModeloDetalleNivel(modeloDetalle, listaModeloDetalleNivelRequisitos)){
				modeloDetalleIGVPercepciones = modeloDetalle;
				break;
			}
		}
		return modeloDetalleIGVPercepciones;
	}	
	
	public ModeloDetalle obtenerModeloDetalleImpuestoRenta(Modelo modelo)throws Exception{
		ModeloDetalle modeloDetalleImpuestoRenta = null;
		
		ModeloDetalleNivel modeloDetalleNivel1 = new ModeloDetalleNivel();
		modeloDetalleNivel1.setStrDescripcion("Para_TipoCptoDocSunat_N_Cod");
		modeloDetalleNivel1.setIntParaTipoRegistro(Constante.PARAM_T_TIPOREGISTROMODELO_TABLA);
		modeloDetalleNivel1.setIntDatoTablas(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_);
		modeloDetalleNivel1.setIntDatoArgumento(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_RETENCION);
		
		ModeloDetalleNivel modeloDetalleNivel2 = generarModeloDetalleNivelTipoComprobante(Constante.PARAM_T_TIPOCOMPROBANTE_RECIBOHONORARIOS);
		
		List<ModeloDetalleNivel> listaModeloDetalleNivelRequisitos = new ArrayList<ModeloDetalleNivel>();
		listaModeloDetalleNivelRequisitos.add(modeloDetalleNivel1);
		listaModeloDetalleNivelRequisitos.add(modeloDetalleNivel2);
		
		for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){			
			if(validarListaModeloDetalleNivel(modeloDetalle, listaModeloDetalleNivelRequisitos)){
				modeloDetalleImpuestoRenta = modeloDetalle;
				break;
			}
		}
		return modeloDetalleImpuestoRenta;
	}
	
	public ModeloDetalleNivel generarModeloDetalleNivelTipoComprobante(Integer intDatoArgumento)throws Exception{
		ModeloDetalleNivel modeloDetalleNivel = new ModeloDetalleNivel();
		modeloDetalleNivel.setStrDescripcion("Para_TipoComprobante_N_Cod");
		modeloDetalleNivel.setIntParaTipoRegistro(Constante.PARAM_T_TIPOREGISTROMODELO_TABLA);
		modeloDetalleNivel.setIntDatoTablas(Constante.PARAM_T_TIPOCOMPROBANTE_);
		modeloDetalleNivel.setIntDatoArgumento(intDatoArgumento);
		
		return modeloDetalleNivel;
	}
	
	public ModeloDetalle obtenerModeloDetalleFacturasPorPagar(Modelo modelo)throws Exception{
		ModeloDetalle modeloDetalleImpuestoRenta = null;
		
		ModeloDetalleNivel modeloDetalleNivelRequisito = new ModeloDetalleNivel();
		modeloDetalleNivelRequisito.setStrDescripcion("Para_TipoCptoDocSunat_N_Cod");
		modeloDetalleNivelRequisito.setIntParaTipoRegistro(Constante.PARAM_T_TIPOREGISTROMODELO_TABLA);
		modeloDetalleNivelRequisito.setIntDatoTablas(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_);
		modeloDetalleNivelRequisito.setIntDatoArgumento(Constante.PARAM_T_CONCEPTODOCUMENTOSUNAT_TOTALGENERAL);
		
		for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle())			
			if(validarListaModeloDetalleFacturasPorPagar(modeloDetalle, modeloDetalleNivelRequisito)){
				modeloDetalleImpuestoRenta = modeloDetalle;
				break;
			}
		
		return modeloDetalleImpuestoRenta;
	}
	
	public ModeloDetalle obtenerModeloDetalleIGVGiro(Modelo modelo)throws Exception{
		ModeloDetalle modeloDetalleIGVGiro = null;		
		for(ModeloDetalle modeloDetalle : modelo.getListModeloDetalle()){			
			ModeloDetalleNivel modeloDetalleNivel1 = new ModeloDetalleNivel();
			modeloDetalleNivel1.setStrDescripcion("Para_TipoComprobante_N_Cod");
			modeloDetalleNivel1.setIntParaTipoRegistro(Constante.PARAM_T_TIPOREGISTROMODELO_TABLA);
			modeloDetalleNivel1.setIntDatoTablas(114);
			modeloDetalleNivel1.setIntDatoArgumento(3);
			
			ModeloDetalleNivel modeloDetalleNivel2 = new ModeloDetalleNivel();
			modeloDetalleNivel2.setStrDescripcion("Para_TipoCptoDocSunat_N_Cod");
			modeloDetalleNivel2.setIntParaTipoRegistro(Constante.PARAM_T_TIPOREGISTROMODELO_TABLA);
			modeloDetalleNivel2.setIntDatoTablas(269);
			modeloDetalleNivel2.setIntDatoArgumento(7);
			
			List<ModeloDetalleNivel> listaModeloDetalleNivelRequisitos = new ArrayList<ModeloDetalleNivel>();
			listaModeloDetalleNivelRequisitos.add(modeloDetalleNivel1);
			listaModeloDetalleNivelRequisitos.add(modeloDetalleNivel2);
			
			if(validarListaModeloDetalleNivel2(modeloDetalle, listaModeloDetalleNivelRequisitos)){
				modeloDetalleIGVGiro = modeloDetalle;
				break;
			}
		}
		return modeloDetalleIGVGiro;
	}
	
}