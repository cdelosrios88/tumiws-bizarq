package pe.com.tumi.contabilidad.core.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelComp;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelId;
import pe.com.tumi.contabilidad.core.domain.ModeloId;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Remote
public interface ModeloFacadeRemote {
	public Modelo grabarModelo(Modelo o)throws BusinessException;
	public Modelo modificarModelo(Modelo o)throws BusinessException;
	public Modelo getModeloPorPk(ModeloId pId) throws BusinessException;
	public List<Modelo> getListaModeloBusqueda(Modelo pModelo) throws BusinessException;
	public Modelo getModeloConDetallePorId(ModeloId pId) throws BusinessException;
	public List<Modelo> obtenerTipoModeloActual(Integer intTipoModelo, Integer intIdEmpresa) throws BusinessException;
	public List<ModeloDetalle> getListaModeloDetallePorModeloId(ModeloId pId) throws BusinessException;
   	public List<ModeloDetalleNivel> getListaModeloDetNivelPorModeloDetalleId(ModeloDetalleId pId) throws BusinessException;

   	public List<Modelo> obtenerTipoModeloRefinanciamiento(Integer intTipoModelo, Integer intIdEmpresa) throws BusinessException;
   	//Agregado 30.12.2013 JCHAVEZ
   	public List<ModeloDetalleNivelComp> getModeloGiroPrestamo(ModeloDetalleNivel o) throws BusinessException;
   	//Agregado 02.01.2014 JCHAVEZ
   	public List<ModeloDetalleNivelId> getPkModeloXReprestamo(ModeloDetalleNivel modeloFiltro, Integer intTipoCredito, Integer intItemCredito, Integer intCategoriaRiesgo) throws BusinessException;
   	public List<ModeloDetalleNivelComp> getCampoXPkModelo(ModeloDetalleNivel modeloFiltro) throws BusinessException;
   	//Agregado 21.01.2014 JCHAVEZ
   	public List<ModeloDetalleNivelComp> getModeloGiroPrevision(ModeloDetalleNivel o) throws BusinessException;
   	//Agregado 23.01.2014 JCHAVEZ
   	public List<ModeloDetalleNivelComp> getModeloPlanillaMovilidad(ModeloDetalleNivel o) throws BusinessException;

    
    public List<ModeloDetalle> getListaDebeOfCobranza(Integer intEmpresa,    												
													  Integer intPeriodo,												
													  Integer intCodigoModelo) throws BusinessException;
    
    public List<ModeloDetalle> getListaDebeOfCobranzaUSUARIO10(Integer empresaDebe,
																  Integer periodoDebe,
																  Integer codigoModeloDebe,
																  Integer codigo) throws BusinessException;
    
    public List<ModeloDetalle> getListaDebeOfCobranzaUSUARIONO10(Integer empresaDebe,
																  Integer periodoDebe,
																  Integer codigoModeloDebe,
																  Integer codigo) throws BusinessException;
    
	public String getCuentaPorPagar(Integer intEmpresa,								   
									Integer intPeriodo)throws BusinessException;
	
   	public List<ModeloDetalleNivel> getNumeroCuentaPrestamo(Integer empresa,
															Integer periodo,
															Integer paraTipoRiesgo,
															Integer itemConcepto,
															Integer categoria,
															Integer conceptoGeneral,
						   									Integer intTipoModeloContable) throws BusinessException;
   	
   	public List<ModeloDetalleNivel> getNroCtaPrestamoSinCategoria(Integer empresa,
																	Integer periodo,
																	Integer paraTipoRiesgo,
																	Integer itemConcepto,
																	Integer conceptoGeneral,
											   						Integer intTipoModeloContable) throws BusinessException;
	//jchavez 27.05.2014
   	public List<ModeloDetalleNivelComp> getModeloProvisionRetiro(ModeloDetalleNivel o) throws BusinessException;
	public List<ModeloDetalleNivelComp> getModeloProvRetiroInteres(ModeloDetalleNivel o) throws BusinessException;
	//Autor: fyalico / Tarea: Creación / Fecha: 11.09.2014 
	public String getCuentaPorCobrar(Integer intEmpresa,								   
									 Integer intPeriodo)throws BusinessException;
}
