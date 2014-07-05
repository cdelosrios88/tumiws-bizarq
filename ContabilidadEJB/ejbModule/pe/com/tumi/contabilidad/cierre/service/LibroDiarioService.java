package pe.com.tumi.contabilidad.cierre.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.contabilidad.cierre.bo.LibroDiarioBO;
import pe.com.tumi.contabilidad.cierre.bo.LibroDiarioDetalleBO;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LibroDiarioService {	

	protected static Logger log = Logger.getLogger(LibroDiarioService.class);
	
	LibroDiarioBO boLibroDiario = (LibroDiarioBO)TumiFactory.get(LibroDiarioBO.class);
	LibroDiarioDetalleBO boLibroDiarioDetalle = (LibroDiarioDetalleBO)TumiFactory.get(LibroDiarioDetalleBO.class);
	
	public LibroDiarioDetalle dynamicSaveLibroDiarioDet(LibroDiarioDetalle o, LibroDiarioId id, Integer intItemDetalle) throws BusinessException{
		LibroDiarioDetalle domain = null;
		try {
			//para grabar o actualizar
			domain = boLibroDiarioDetalle.getPorPk(o.getId());
			if(domain!=null){
				domain = boLibroDiarioDetalle.modificar(o);
			}else{
				o.getId().setIntPersEmpresaLibro(id.getIntPersEmpresaLibro());
				o.getId().setIntContPeriodoLibro(id.getIntContPeriodoLibro());
				o.getId().setIntContCodigoLibro(id.getIntContCodigoLibro());
				o.getId().setIntContItemLibro(intItemDetalle);
				domain = boLibroDiarioDetalle.grabar(o);
			}
		} catch (Exception e) {
			log.error("Error en dynamicSaveLibroDiarioDet ---> "+e);
		}
		return domain;
	}
	
	/**
	 * fgfdgd dfgdfn
	 * @param libroDiario
	 * @throws Exception
	 */
	private void asignarCodigoLibro(LibroDiario libroDiario)throws Exception{
		log.info(libroDiario);
		int intMayorCodigoLibro = new Integer(0);
		try {
			List<LibroDiario> listaLibroDiario = boLibroDiario.buscarParaCodigoLibro(libroDiario.getId());			
			if(listaLibroDiario!=null && !listaLibroDiario.isEmpty()){
				for(LibroDiario libroDiarioTemp : listaLibroDiario){
					log.info(libroDiarioTemp);
				}
				//Ordenamos los libros por intContCodigoLibro
				Collections.sort(listaLibroDiario, new Comparator<LibroDiario>(){
					public int compare(LibroDiario uno, LibroDiario otro) {
						return otro.getId().getIntContCodigoLibro().compareTo(uno.getId().getIntContCodigoLibro());
					}
				});
				LibroDiario libroDiarioUltimo = listaLibroDiario.get(0);
				intMayorCodigoLibro = libroDiarioUltimo.getId().getIntContCodigoLibro();
			}
			
			libroDiario.getId().setIntContCodigoLibro(intMayorCodigoLibro+1);
		} catch (Exception e) {
			log.error("Error enasignarCodigoLibro ---> "+e);
		}
		
	}
	
	public LibroDiario grabarLibroDiario(LibroDiario libroDiario)throws Exception{
		try{			
			// Validación montos DEBE y HABER deben de ser iguales 
			if(!validarLibroDiario(libroDiario)){
				libroDiario.setStrMsgErrorGeneracionLibroDiario("No se puede grabar el Libro Diario porque los montos acumulados de DEBE y HABER no son iguales.");
				throw new Exception("No se puede grabar el libro diario porque los montos acumulados de DEBE y HABER no son iguales.");
			}				

			// Recupera el código del libro diario a grabar
			asignarCodigoLibro(libroDiario);
			log.info(libroDiario);
			//CGD-12.10.2013
			if(libroDiario.getStrGlosa()== null){
				libroDiario.setStrGlosa("NO SE INGRESO GLOSA");
			}
			boLibroDiario.grabar(libroDiario);
			
			Integer intItemDetalle = new Integer(0);
			// Se setean datos faltantes en el libro diario detalle y se procede a su grabación
			for(LibroDiarioDetalle libroDiarioDetalle : libroDiario.getListaLibroDiarioDetalle()){
				intItemDetalle = intItemDetalle + 1;
				//Agregado por cdelosrios, 22/10/2013
				if(libroDiarioDetalle.getId()==null) libroDiarioDetalle.setId(new LibroDiarioDetalleId());
				//Fin agregado por cdelosrios, 22/10/2013
				libroDiarioDetalle.getId().setIntPersEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());
				libroDiarioDetalle.getId().setIntContPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
				libroDiarioDetalle.getId().setIntContCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
				libroDiarioDetalle.getId().setIntContItemLibro(intItemDetalle);
				log.info(libroDiarioDetalle);
				boLibroDiarioDetalle.grabar(libroDiarioDetalle);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return libroDiario;
	}
	
	public LibroDiario modificarLibroDiario(LibroDiario libroDiario)throws Exception{
		try{			
			boLibroDiario.modificar(libroDiario);
			
			for(LibroDiarioDetalle libroDiarioDetalle : libroDiario.getListaLibroDiarioDetalle()){
				boLibroDiarioDetalle.modificar(libroDiarioDetalle);
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return libroDiario;
	}
	
	public LibroDiario getLibroDiarioPorId(LibroDiarioId libroDiarioId)throws Exception{
		LibroDiario libroDiario = null;
		try{	
			libroDiario = boLibroDiario.getPorPk(libroDiarioId);
			if(libroDiario!=null)
			libroDiario.setStrNumeroAsiento(
					obtenerPeriodoItem(	libroDiarioId.getIntContPeriodoLibro(),libroDiarioId.getIntContCodigoLibro(),"000000"));
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return libroDiario;
	}
	
	private String obtenerPeriodoItem(Integer intPeriodo, Integer item, String patron){
		try{
			DecimalFormat formato = new DecimalFormat(patron);
			return intPeriodo+"-"+formato.format(Double.parseDouble(""+item));
		}catch (Exception e) {
			log.error(e.getMessage());
			return intPeriodo+"-"+item;
		}
	}
	
	/**
	 * 
	 * @param libroDiario
	 * @return
	 * @throws Exception
	 */
	private boolean validarLibroDiario(LibroDiario libroDiario)throws Exception{
		Boolean blnSalida = Boolean.FALSE;
		BigDecimal bdMontoHaber = new BigDecimal(0);
		BigDecimal bdMontoDebe  = new BigDecimal(0);
		
		try {
			for(LibroDiarioDetalle libroDiarioDetalle : libroDiario.getListaLibroDiarioDetalle()){
				if(libroDiarioDetalle.getBdHaberSoles()!=null)
					bdMontoHaber = bdMontoHaber.add(libroDiarioDetalle.getBdHaberSoles());
				if(libroDiarioDetalle.getBdDebeSoles()!=null)
					bdMontoDebe = bdMontoDebe.add(libroDiarioDetalle.getBdDebeSoles());
			}
			
			log.info("--bdMontoHaber : "+bdMontoHaber);
			log.info("--bdMontoDebe  : "+bdMontoDebe);
			
			if(bdMontoHaber.compareTo(bdMontoDebe)==0){
				blnSalida = Boolean.TRUE;
			}
			
		} catch (Exception e) {
			log.error("Error en validarLibroDiario ---> "+e);
		}
		return blnSalida;
	}
	
	
}