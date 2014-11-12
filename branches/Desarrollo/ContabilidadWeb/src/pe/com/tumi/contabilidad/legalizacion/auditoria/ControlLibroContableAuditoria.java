package pe.com.tumi.contabilidad.legalizacion.auditoria;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.contabilidad.legalizacion.controller.ControlLibroContableController;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalle;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.facade.AuditoriaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class ControlLibroContableAuditoria {
	protected static Logger log = Logger.getLogger(ControlLibroContableController.class); 
	/* Inicio - AuditoriaFacade - jbermudez 10.09.2014 */
	private AuditoriaFacadeRemote 			auditoriaFacade;
	// propiedades que capturan atributos de sesión
	private Integer 						IDUSUARIO_SESION;
	private Integer 						IDEMPRESA_SESION;

	public ControlLibroContableAuditoria() throws BusinessException, EJBFactoryException{
		Usuario usuarioSesion = (Usuario) FacesContextUtil.getRequest().getSession().getAttribute("usuario");
		IDUSUARIO_SESION = usuarioSesion.getIntPersPersonaPk();
		IDEMPRESA_SESION = usuarioSesion.getEmpresa().getIntIdEmpresa();
		
		auditoriaFacade = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
	}

    /* Se implementa la Auditoría para los Nuevos Registros y las Modificaciones */
	//TABLA CON_LIBROLEGALIZACION
	public List<Auditoria> generarAuditoriaLegal(Integer intTipoCambio, LibroLegalizacion libroLegalizacion, LibroLegalizacion libroLegalizacionOld) throws BusinessException{
		log.info("Inicio generarAuditoriaLegal");
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			//Generamos las Llaves
			List<String> listaLlaves = new ArrayList<String>();
			listaLlaves.add("" + libroLegalizacion.getId().getIntPersEmpresa());
			listaLlaves.add("" + libroLegalizacion.getId().getIntParaLibroContable());
			listaLlaves.add("" + libroLegalizacion.getId().getIntItemLibroLegalizacion());
			
			// Inserción de Nuevos Registros
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_INSERT)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
				if(libroLegalizacion.getId().getIntPersEmpresa() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_PERS_EMPRESA_N_PK);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getId().getIntPersEmpresa());
					lista.add(auditoria);
				}
				if(libroLegalizacion.getId().getIntParaLibroContable() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_PARA_LIBROCONTABLE_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getId().getIntParaLibroContable());
					lista.add(auditoria);
				}
				if(libroLegalizacion.getId().getIntItemLibroLegalizacion() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_CONT_ITEMLIBROLEGALIZACION);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getId().getIntItemLibroLegalizacion());
					lista.add(auditoria);
				}
				if(libroLegalizacion.getIntPersEmpresaLegal() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_PERS_EMPRESALEGAL_N_PK);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getIntPersEmpresaLegal());
					lista.add(auditoria);
				}
				if(libroLegalizacion.getIntPersPersona() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_PERS_PERSONA_N_PK);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getIntPersPersona());
					lista.add(auditoria);
				}
				if(libroLegalizacion.getDtFechaLegalizacion() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_LILE_FECHALEGALIZACION_D);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getDtFechaLegalizacion());
					lista.add(auditoria);
				}
				if(libroLegalizacion.getIntNroCertificado() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_LILE_NROCERTIFICADO_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getIntNroCertificado());
					lista.add(auditoria);
				}
				if(libroLegalizacion.getIntFolioInicio() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_LILE_FOLIOINICIO_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getIntFolioInicio());
					lista.add(auditoria);
				}
				if(libroLegalizacion.getIntFolioFin() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_LILE_FOLIOFIN_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getIntFolioFin());
					lista.add(auditoria);
				}
				if(libroLegalizacion.getIntParaTipo() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_PARA_TIPO_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getIntParaTipo());
					lista.add(auditoria);
				}
				if(libroLegalizacion.getIntItemArchivo() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_MAE_ITEMARCHIVO_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getIntItemArchivo());
					lista.add(auditoria);
				}
				if(libroLegalizacion.getIntItemHistorico() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_MAE_ITEMHISTORICO_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroLegalizacion.getIntItemHistorico());
					lista.add(auditoria);
				}
			}
			
			// Modificación de Registros
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_UPDATE)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_UPDATE");
				if(!libroLegalizacionOld.getId().getIntPersEmpresa().equals(libroLegalizacion.getId().getIntPersEmpresa())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_PERS_EMPRESA_N_PK);
					auditoria.setStrValoranterior(libroLegalizacionOld.getId().getIntPersEmpresa() != null ? "" + libroLegalizacionOld.getId().getIntPersEmpresa() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getId().getIntPersEmpresa() != null ? "" + libroLegalizacion.getId().getIntPersEmpresa() : null);
					lista.add(auditoria);
				}
				if(!libroLegalizacionOld.getId().getIntParaLibroContable().equals(libroLegalizacion.getId().getIntParaLibroContable())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_PARA_LIBROCONTABLE_N_COD);
					auditoria.setStrValoranterior(libroLegalizacionOld.getId().getIntParaLibroContable() != null ? "" + libroLegalizacionOld.getId().getIntParaLibroContable() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getId().getIntParaLibroContable() != null ? "" + libroLegalizacion.getId().getIntParaLibroContable() : null);
					lista.add(auditoria);
				}
				if(!libroLegalizacionOld.getId().getIntItemLibroLegalizacion().equals(libroLegalizacion.getId().getIntItemLibroLegalizacion())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_CONT_ITEMLIBROLEGALIZACION);
					auditoria.setStrValoranterior(libroLegalizacionOld.getId().getIntItemLibroLegalizacion() != null ? "" + libroLegalizacionOld.getId().getIntItemLibroLegalizacion() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getId().getIntItemLibroLegalizacion() != null ? "" + libroLegalizacion.getId().getIntItemLibroLegalizacion() : null);
					lista.add(auditoria);
				}
				if(!libroLegalizacionOld.getIntPersEmpresaLegal().equals(libroLegalizacion.getIntPersEmpresaLegal())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_PERS_EMPRESALEGAL_N_PK);
					auditoria.setStrValoranterior(libroLegalizacionOld.getIntPersEmpresaLegal() != null ? "" + libroLegalizacionOld.getIntPersEmpresaLegal() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getIntPersEmpresaLegal() != null ? "" + libroLegalizacion.getIntPersEmpresaLegal() : null);
					lista.add(auditoria);
				}
				if(!libroLegalizacionOld.getIntPersPersona().equals(libroLegalizacion.getIntPersPersona())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_PERS_PERSONA_N_PK);
					auditoria.setStrValoranterior(libroLegalizacionOld.getIntPersPersona() != null ? "" + libroLegalizacionOld.getIntPersPersona() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getIntPersPersona() != null ? "" + libroLegalizacion.getIntPersPersona() : null);
					lista.add(auditoria);
				}
				if(!libroLegalizacionOld.getDtFechaLegalizacion().equals(libroLegalizacion.getDtFechaLegalizacion())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_LILE_FECHALEGALIZACION_D);
					auditoria.setStrValoranterior(libroLegalizacionOld.getDtFechaLegalizacion() != null ? "" + libroLegalizacionOld.getDtFechaLegalizacion() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getDtFechaLegalizacion() != null ? "" + libroLegalizacion.getDtFechaLegalizacion() : null);
					lista.add(auditoria);
				}
				if(!libroLegalizacionOld.getIntNroCertificado().equals(libroLegalizacion.getIntNroCertificado())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_LILE_NROCERTIFICADO_N);
					auditoria.setStrValoranterior(libroLegalizacionOld.getIntNroCertificado() != null ? "" + libroLegalizacionOld.getIntNroCertificado() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getIntNroCertificado() != null ? "" + libroLegalizacion.getIntNroCertificado() : null);
					lista.add(auditoria);
				}
				if(!libroLegalizacionOld.getIntFolioInicio().equals(libroLegalizacion.getIntFolioInicio())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_LILE_FOLIOINICIO_N);
					auditoria.setStrValoranterior(libroLegalizacionOld.getIntFolioInicio() != null ? "" + libroLegalizacionOld.getIntFolioInicio() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getIntFolioInicio() != null ? "" + libroLegalizacion.getIntFolioInicio() : null);
					lista.add(auditoria);
				}
				if(!libroLegalizacionOld.getIntFolioFin().equals(libroLegalizacion.getIntFolioFin())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_LILE_FOLIOFIN_N);
					auditoria.setStrValoranterior(libroLegalizacionOld.getIntFolioFin() != null ? "" + libroLegalizacionOld.getIntFolioFin() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getIntFolioFin() != null ? "" + libroLegalizacion.getIntFolioFin() : null);
					lista.add(auditoria);
				}
				if(!libroLegalizacionOld.getIntParaTipo().equals(libroLegalizacion.getIntParaTipo())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_PARA_TIPO_N_COD);
					auditoria.setStrValoranterior(libroLegalizacionOld.getIntParaTipo() != null ? "" + libroLegalizacionOld.getIntParaTipo() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getIntParaTipo() != null ? "" + libroLegalizacion.getIntParaTipo() : null);
					lista.add(auditoria);
				}
				if(!libroLegalizacionOld.getIntItemArchivo().equals(libroLegalizacion.getIntItemArchivo())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_MAE_ITEMARCHIVO_N);
					auditoria.setStrValoranterior(libroLegalizacionOld.getIntItemArchivo() != null ? "" + libroLegalizacionOld.getIntItemArchivo() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getIntItemArchivo() != null ? "" + libroLegalizacion.getIntItemArchivo() : null);
					lista.add(auditoria);
				}
				if(!libroLegalizacionOld.getIntItemHistorico().equals(libroLegalizacion.getIntItemHistorico())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROLEGALIZACION_MAE_ITEMHISTORICO_N);
					auditoria.setStrValoranterior(libroLegalizacionOld.getIntItemHistorico() != null ? "" + libroLegalizacionOld.getIntItemHistorico() : null);
					auditoria.setStrValornuevo(libroLegalizacion.getIntItemHistorico() != null ? "" + libroLegalizacion.getIntItemHistorico() : null);
					lista.add(auditoria);
				}

			}
		}catch(Exception e){
			log.error("Error en generarAuditoriaLibroLegalizacion --> " + e);
		}
		log.info("Fin Legal");
		return lista;
	}

	//TABLA CON_LIBROCONTABLEDETALLE
	public List<Auditoria> generarAuditoriaLibro(Integer intTipoCambio, LibroContableDetalle libroContableDetalle, LibroContableDetalle libroContableDetalleOld) throws BusinessException{
		log.info("Inicio generarAuditoriaLibro");
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			//Generamos las Llaves
			List<String> listaLlaves = new ArrayList<String>();
			listaLlaves.add("" + libroContableDetalle.getId().getIntEmpresaPk());
			listaLlaves.add("" + libroContableDetalle.getId().getIntLibroContable());
			listaLlaves.add("" + libroContableDetalle.getId().getIntItemLibroContableDet());
			
			// Inserción de Nuevos Registros
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_INSERT)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
				if(libroContableDetalle.getId().getIntEmpresaPk() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_PERS_EMPRESA_N_PK);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroContableDetalle.getId().getIntEmpresaPk());
					lista.add(auditoria);
				}
				if(libroContableDetalle.getId().getIntLibroContable() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_PARA_LIBROCONTABLE_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroContableDetalle.getId().getIntLibroContable());
					lista.add(auditoria);
				}
				if(libroContableDetalle.getId().getIntItemLibroContableDet() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_CONT_ITEMLIBROCONTABLEDET_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroContableDetalle.getId().getIntItemLibroContableDet());
					lista.add(auditoria);
				}
				if(libroContableDetalle.getIntPeriodo() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_LICD_PERIODO_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroContableDetalle.getIntPeriodo());
					lista.add(auditoria);
				}
				if(libroContableDetalle.getIntFolioInicio() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_LICD_FOLIOINICIO_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroContableDetalle.getIntFolioInicio());
					lista.add(auditoria);
				}
				if(libroContableDetalle.getIntFolioFin() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_LICD_FOLIOFIN_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroContableDetalle.getIntFolioFin());
					lista.add(auditoria);
				}
				if(libroContableDetalle.getIntItemLibroLegalizacion() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_CONT_ITEMLIBROLEGALIZACION);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroContableDetalle.getIntItemLibroLegalizacion());
					lista.add(auditoria);
				}
				if(libroContableDetalle.getIntTipo() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_PARA_TIPO_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroContableDetalle.getIntTipo());
					lista.add(auditoria);
				}
				if(libroContableDetalle.getIntItemArchivo() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_MAE_ITEMARCHIVO_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroContableDetalle.getIntItemArchivo());
					lista.add(auditoria);
				}
				if(libroContableDetalle.getIntItemHistorico() != null) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_MAE_ITEMHISTORICO_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + libroContableDetalle.getIntItemHistorico());
					lista.add(auditoria);
				}
			}
			
			// Modificación de Registros
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_UPDATE)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_UPDATE");
				if(!libroContableDetalleOld.getId().getIntEmpresaPk().equals(libroContableDetalle.getId().getIntEmpresaPk())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_PERS_EMPRESA_N_PK);
					auditoria.setStrValoranterior(libroContableDetalleOld.getId().getIntEmpresaPk() != null ? "" + libroContableDetalleOld.getId().getIntEmpresaPk() : null);
					auditoria.setStrValornuevo(libroContableDetalle.getId().getIntEmpresaPk() != null ? "" + libroContableDetalle.getId().getIntEmpresaPk() : null);
					lista.add(auditoria);
				}
				if(!libroContableDetalleOld.getId().getIntLibroContable().equals(libroContableDetalle.getId().getIntLibroContable())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_PARA_LIBROCONTABLE_N_COD);
					auditoria.setStrValoranterior(libroContableDetalleOld.getId().getIntLibroContable() != null ? "" + libroContableDetalleOld.getId().getIntLibroContable() : null);
					auditoria.setStrValornuevo(libroContableDetalle.getId().getIntLibroContable() != null ? "" + libroContableDetalle.getId().getIntLibroContable() : null);
					lista.add(auditoria);
				}
				if(!libroContableDetalleOld.getId().getIntItemLibroContableDet().equals(libroContableDetalle.getId().getIntItemLibroContableDet())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_CONT_ITEMLIBROCONTABLEDET_N);
					auditoria.setStrValoranterior(libroContableDetalleOld.getId().getIntItemLibroContableDet() != null ? "" + libroContableDetalleOld.getId().getIntItemLibroContableDet() : null);
					auditoria.setStrValornuevo(libroContableDetalle.getId().getIntItemLibroContableDet() != null ? "" + libroContableDetalle.getId().getIntItemLibroContableDet() : null);
					lista.add(auditoria);
				}
				if(!libroContableDetalleOld.getIntPeriodo().equals(libroContableDetalle.getIntPeriodo())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_LICD_PERIODO_N);
					auditoria.setStrValoranterior(libroContableDetalleOld.getIntPeriodo() != null ? "" + libroContableDetalleOld.getIntPeriodo() : null);
					auditoria.setStrValornuevo(libroContableDetalle.getIntPeriodo() != null ? "" + libroContableDetalle.getIntPeriodo() : null);
					lista.add(auditoria);
				}

				if(!libroContableDetalleOld.getIntFolioInicio().equals(libroContableDetalle.getIntFolioInicio())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_LICD_FOLIOINICIO_N);
					auditoria.setStrValoranterior(libroContableDetalleOld.getIntFolioInicio() != null ? "" + libroContableDetalleOld.getIntFolioInicio() : null);
					auditoria.setStrValornuevo(libroContableDetalle.getIntFolioInicio() != null ? "" + libroContableDetalle.getIntFolioInicio() : null);
					lista.add(auditoria);
				}
				if(!libroContableDetalleOld.getIntFolioFin().equals(libroContableDetalle.getIntFolioFin())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_LICD_FOLIOFIN_N);
					auditoria.setStrValoranterior(libroContableDetalleOld.getIntFolioFin() != null ? "" + libroContableDetalleOld.getIntFolioFin() : null);
					auditoria.setStrValornuevo(libroContableDetalle.getIntFolioFin() != null ? "" + libroContableDetalle.getIntFolioFin() : null);
					lista.add(auditoria);
				}
				if(!libroContableDetalleOld.getIntItemLibroLegalizacion().equals(libroContableDetalle.getIntItemLibroLegalizacion())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_CONT_ITEMLIBROLEGALIZACION);
					auditoria.setStrValoranterior(libroContableDetalleOld.getIntItemLibroLegalizacion() != null ? "" + libroContableDetalleOld.getIntItemLibroLegalizacion() : null);
					auditoria.setStrValornuevo(libroContableDetalle.getIntItemLibroLegalizacion() != null ? "" + libroContableDetalle.getIntItemLibroLegalizacion() : null);
					lista.add(auditoria);
				}
				if(!libroContableDetalleOld.getIntTipo().equals(libroContableDetalle.getIntTipo())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_PARA_TIPO_N_COD);
					auditoria.setStrValoranterior(libroContableDetalleOld.getIntTipo() != null ? "" + libroContableDetalleOld.getIntTipo() : null);
					auditoria.setStrValornuevo(libroContableDetalle.getIntTipo() != null ? "" + libroContableDetalle.getIntTipo() : null);
					lista.add(auditoria);
				}
				if(!libroContableDetalleOld.getIntItemArchivo().equals(libroContableDetalle.getIntItemArchivo())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_MAE_ITEMARCHIVO_N);
					auditoria.setStrValoranterior(libroContableDetalleOld.getIntItemArchivo() != null ? "" + libroContableDetalleOld.getIntItemArchivo() : null);
					auditoria.setStrValornuevo(libroContableDetalle.getIntItemArchivo() != null ? "" + libroContableDetalle.getIntItemArchivo() : null);
					lista.add(auditoria);
				}
				if(!libroContableDetalleOld.getIntItemHistorico().equals(libroContableDetalle.getIntItemHistorico())) {
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CON_LIBROCONTABLEDETALLE_MAE_ITEMHISTORICO_N);
					auditoria.setStrValoranterior(libroContableDetalleOld.getIntItemHistorico() != null ? "" + libroContableDetalleOld.getIntItemHistorico() : null);
					auditoria.setStrValornuevo(libroContableDetalle.getIntItemHistorico() != null ? "" + libroContableDetalle.getIntItemHistorico() : null);
					lista.add(auditoria);
				}

			}
		}catch(Exception e){
			log.error("Error en generarAuditoriaLibroContableDetalle --> " + e);
		}
		log.info("Fin Libro");
		return lista;
	}	
	/* Método que carga los datos comunes de la estructura Auditoria */
	public Auditoria beanAuditoria(Integer intTipoCambio, List<String> llaves, String tabla) {
		log.info("Inicio");
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = auditoriaFacade.beanAuditoria(IDEMPRESA_SESION, IDUSUARIO_SESION, intTipoCambio, llaves, tabla);
		} catch (Exception e) {
			log.error("Error al grabar en Auditoria: " + e);
		}
		log.info("Fin");
		return auditoria;
	}
	
	/* Método que graba en la tabla Auditoria */
	public Auditoria grabarAuditoria(Auditoria auditoria)throws BusinessException {
		log.info("Inicio");
		try {
			auditoriaFacade.grabarAuditoria(auditoria);
		} catch (Exception e) {
			log.error("Error al grabar en Auditoria: " + e);
		}
		log.info("Fin");
		return auditoria;
	}
    /* Fin - Auditoria - jbermudez 10.09.2014 */
}
