package pe.com.tumi.tesoreria.egreso.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DocumentoGeneral;

public class DocumentoGeneralService {

	protected static Logger log = Logger.getLogger(DocumentoGeneralService.class);
	
	public List<DocumentoGeneral> filtrarDuplicidadDocumentoGeneralParaEgreso(
		List<DocumentoGeneral> listaDocumentoPorAgregar, Integer intTipoDocumentoAgregar, List<DocumentoGeneral>listaDocumentoAgregados)
		throws Exception{
		
		if(listaDocumentoPorAgregar==null || listaDocumentoPorAgregar.isEmpty()){
			return listaDocumentoPorAgregar;
		}
		
		List<DocumentoGeneral> listaDocumentoGeneralAgregado = new ArrayList<DocumentoGeneral>();
		for(DocumentoGeneral documentoGeneralAgregado : listaDocumentoAgregados){
			if(documentoGeneralAgregado.getIntTipoDocumento().equals(intTipoDocumentoAgregar)){
				listaDocumentoGeneralAgregado.add(documentoGeneralAgregado);
			}
		}
		
		if(listaDocumentoGeneralAgregado.isEmpty()){
			return listaDocumentoPorAgregar;
		}
		
		List<DocumentoGeneral> listaDocumentoGeneralTemp = new ArrayList<DocumentoGeneral>();
		boolean yaSeEncuentraAgregado = Boolean.FALSE;
		
		if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
			for(DocumentoGeneral documentoGeneralPorAgregar : listaDocumentoPorAgregar){
				yaSeEncuentraAgregado = Boolean.FALSE;
				for(DocumentoGeneral documentoGeneralAgregado : listaDocumentoGeneralAgregado){
					if(documentoGeneralPorAgregar.getMovilidad().getId().getIntItemMovilidad().equals(
						documentoGeneralAgregado.getMovilidad().getId().getIntItemMovilidad())){
						yaSeEncuentraAgregado = Boolean.TRUE;	break;
					}
				}
				if(!yaSeEncuentraAgregado) listaDocumentoGeneralTemp.add(documentoGeneralPorAgregar);
			}
		}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
			for(DocumentoGeneral documentoGeneralPorAgregar : listaDocumentoPorAgregar){
				yaSeEncuentraAgregado = Boolean.FALSE;
				for(DocumentoGeneral documentoGeneralAgregado : listaDocumentoGeneralAgregado){
					if((documentoGeneralPorAgregar.getExpedienteCredito().getId().getIntPersEmpresaPk().equals(
						documentoGeneralAgregado.getExpedienteCredito().getId().getIntPersEmpresaPk()))
					&&(documentoGeneralPorAgregar.getExpedienteCredito().getId().getIntCuentaPk().equals(
						documentoGeneralAgregado.getExpedienteCredito().getId().getIntCuentaPk()))
					&&(documentoGeneralPorAgregar.getExpedienteCredito().getId().getIntItemExpediente().equals(
						documentoGeneralAgregado.getExpedienteCredito().getId().getIntItemExpediente()))
					&&(documentoGeneralPorAgregar.getExpedienteCredito().getId().getIntItemDetExpediente().equals(
						documentoGeneralAgregado.getExpedienteCredito().getId().getIntItemDetExpediente()))
					){
						yaSeEncuentraAgregado = Boolean.TRUE;	break;
					}
				}
				if(!yaSeEncuentraAgregado) listaDocumentoGeneralTemp.add(documentoGeneralPorAgregar);
			}
		}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
			for(DocumentoGeneral documentoGeneralPorAgregar : listaDocumentoPorAgregar){
				yaSeEncuentraAgregado = Boolean.FALSE;
				for(DocumentoGeneral documentoGeneralAgregado : listaDocumentoGeneralAgregado){
					if((documentoGeneralPorAgregar.getExpedientePrevision().getId().getIntPersEmpresaPk().equals(
						documentoGeneralAgregado.getExpedientePrevision().getId().getIntPersEmpresaPk()))
					&&(documentoGeneralPorAgregar.getExpedientePrevision().getId().getIntCuentaPk().equals(
						documentoGeneralAgregado.getExpedientePrevision().getId().getIntCuentaPk()))
					&&(documentoGeneralPorAgregar.getExpedientePrevision().getId().getIntItemExpediente().equals(
						documentoGeneralAgregado.getExpedientePrevision().getId().getIntItemExpediente()))
					){
						yaSeEncuentraAgregado = Boolean.TRUE;	break;
					}
				}
				if(!yaSeEncuentraAgregado) listaDocumentoGeneralTemp.add(documentoGeneralPorAgregar);
			}
			
		}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
			for(DocumentoGeneral documentoGeneralPorAgregar : listaDocumentoPorAgregar){
				yaSeEncuentraAgregado = Boolean.FALSE;
				for(DocumentoGeneral documentoGeneralAgregado : listaDocumentoGeneralAgregado){
					if((documentoGeneralPorAgregar.getExpedienteLiquidacion().getId().getIntPersEmpresaPk().equals(
						documentoGeneralAgregado.getExpedienteLiquidacion().getId().getIntPersEmpresaPk()))
					&&(documentoGeneralPorAgregar.getExpedienteLiquidacion().getId().getIntItemExpediente().equals(
						documentoGeneralAgregado.getExpedienteLiquidacion().getId().getIntItemExpediente()))
					){
						yaSeEncuentraAgregado = Boolean.TRUE;	break;
					}
				}
				if(!yaSeEncuentraAgregado) listaDocumentoGeneralTemp.add(documentoGeneralPorAgregar);
			}
		
		}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS)){
			for(DocumentoGeneral documentoGeneralPorAgregar : listaDocumentoPorAgregar){
				yaSeEncuentraAgregado = Boolean.FALSE;
				for(DocumentoGeneral documentoGeneralAgregado : listaDocumentoGeneralAgregado){
					if((documentoGeneralPorAgregar.getDocumentoSunat().getId().getIntPersEmpresa().equals(
						documentoGeneralAgregado.getDocumentoSunat().getId().getIntPersEmpresa()))
					&&(documentoGeneralPorAgregar.getDocumentoSunat().getId().getIntItemDocumentoSunat().equals(
						documentoGeneralAgregado.getDocumentoSunat().getId().getIntItemDocumentoSunat()))
					){
						yaSeEncuentraAgregado = Boolean.TRUE;	break;
					}
				}
				if(!yaSeEncuentraAgregado) listaDocumentoGeneralTemp.add(documentoGeneralPorAgregar);
			}
		}
		
		return listaDocumentoGeneralTemp;
	}
	
	public List<DocumentoGeneral> filtrarDuplicidadDocumentoGeneralParaIngreso(
			List<DocumentoGeneral> listaDocumentoPorAgregar, Integer intTipoDocumentoAgregar, List<DocumentoGeneral>listaDocumentoAgregados)
			throws Exception{
			
			if(listaDocumentoPorAgregar==null || listaDocumentoPorAgregar.isEmpty()){
				return listaDocumentoPorAgregar;
			}
			
			List<DocumentoGeneral> listaDocumentoGeneralAgregado = new ArrayList<DocumentoGeneral>();
			for(DocumentoGeneral documentoGeneralAgregado : listaDocumentoAgregados){
				if(documentoGeneralAgregado.getIntTipoDocumento().equals(intTipoDocumentoAgregar)){
					listaDocumentoGeneralAgregado.add(documentoGeneralAgregado);
				}
			}
			
			if(listaDocumentoGeneralAgregado.isEmpty()){
				return listaDocumentoPorAgregar;
			}
			
			List<DocumentoGeneral> listaDocumentoGeneralTemp = new ArrayList<DocumentoGeneral>();
			boolean yaSeEncuentraAgregado = Boolean.FALSE;
			
			if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA)){
				for(DocumentoGeneral documentoGeneralPorAgregar : listaDocumentoPorAgregar){
					yaSeEncuentraAgregado = Boolean.FALSE;
					for(DocumentoGeneral documentoGeneralAgregado : listaDocumentoGeneralAgregado){
						if(documentoGeneralPorAgregar.getEfectuadoResumen().getId().getIntItemEfectuadoResumen().equals(
							documentoGeneralAgregado.getEfectuadoResumen().getId().getIntItemEfectuadoResumen())){
							yaSeEncuentraAgregado = Boolean.TRUE;	break;
						}
					}
					if(!yaSeEncuentraAgregado) listaDocumentoGeneralTemp.add(documentoGeneralPorAgregar);
				}
			}
			
			return listaDocumentoGeneralTemp;
		}
}