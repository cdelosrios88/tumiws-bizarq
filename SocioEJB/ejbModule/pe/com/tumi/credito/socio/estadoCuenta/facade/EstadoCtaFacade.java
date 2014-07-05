package pe.com.tumi.credito.socio.estadoCuenta.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.planilla.domain.EnviadoEfectuadoComp;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.estadoCuenta.bo.EstadoCuentaBO;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.MovimientoEstCtaComp;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.PrevisionSocialComp;
import pe.com.tumi.credito.socio.estadoCuenta.service.EstadoCtaService;
import pe.com.tumi.credito.socio.estructura.bo.DescuentoBO;
import pe.com.tumi.credito.socio.estructura.domain.Descuento;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;

@Stateless
public class EstadoCtaFacade implements EstadoCtaFacadeRemote, EstadoCtaFacadeLocal {

	protected  static Logger log = Logger.getLogger(EstadoCtaFacade.class);
	private EstadoCuentaBO estadoCuentaBO = (EstadoCuentaBO)TumiFactory.get(EstadoCuentaBO.class);
	private DescuentoBO descuentoBO = (DescuentoBO)TumiFactory.get(DescuentoBO.class);
	private EstadoCtaService estadoCtaService = (EstadoCtaService)TumiFactory.get(EstadoCtaService.class);
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 08-08-2013
	 * OBTENER CUENTAS POR PK SOCIO Y TIPO DE CUENTA
	 **/
	public List<CuentaComp> getLsCtasPorPkSocioYTipCta(SocioPK pPK,Integer intParaTipoCuenta, Integer intParaSituacionCuentaCod, Integer intCuenta) throws BusinessException {
		List<CuentaComp> lista = null;
    	try{
    		lista = estadoCuentaBO.getLsCtasPorPkSocioYTipCta(pPK, intParaTipoCuenta, intParaSituacionCuentaCod, intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 21.08.2013
	 * Recupera motivos de descuento (Columnas TERCEROS) 
	 * @param intPeriodo
	 * @param intParaModalidadCod
	 * @param strLibEle
	 * @return
	 * @throws BusinessException
	 */
	public List<Descuento> getListaColumnasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strLibEle) throws BusinessException {
		List<Descuento> lista = null;
    	try{
    		lista = descuentoBO.getListaColumnasPorPeriodoModalidadYDni(intPeriodo, intParaModalidadCod, strLibEle);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 21.08.2013
	 * Recupera los periodos en que se realizan los descuentos, desde un periodo en adelante (Filas TERCEROS)
	 * @param intPeriodo
	 * @param intParaModalidadCod
	 * @param strLibEle
	 * @return
	 * @throws BusinessException
	 */
	public List<Descuento> getListaFilasPorPeriodoModalidadYDni(Integer intPeriodo, Integer intParaModalidadCod, String strLibEle) throws BusinessException {
		List<Descuento> lista = null;
    	try{
    		lista = descuentoBO.getListaFilasPorPeriodoModalidadYDni(intPeriodo, intParaModalidadCod, strLibEle);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 21.08.2013
	 * Recupera montos descontados por motivo y periodo (TERCEROS)
	 * @param strDsteCpto
	 * @param strNomCpto
	 * @param intPeriodo
	 * @param intMes
	 * @param intParaModalidadCod
	 * @param strLibEle
	 * @return
	 * @throws BusinessException
	 */
	public List<Descuento> getMontoTotalPorNomCptoYPeriodo(String strDsteCpto, String strNomCpto, Integer intPeriodo, Integer intMes, Integer intParaModalidadCod, String strLibEle) throws BusinessException {
		List<Descuento> dscto = null;
    	try{
    		dscto = descuentoBO.getMontoTotalPorNomCptoYPeriodo(strDsteCpto, strNomCpto, intPeriodo, intMes, intParaModalidadCod, strLibEle);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dscto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04.09.2013 
	 * Recupera lista de personas garantizadas (Datos Grales. y Prestamos).
	 * @param gCred
	 * @return
	 * @throws BusinessException
	 */
	public List<GarantiaCreditoComp> getListaGarantiaCreditoCompPorEmpPersCta(GarantiaCredito gCred) throws BusinessException {
		List<GarantiaCreditoComp> lstRetorna = null;
		try{
			lstRetorna = estadoCtaService.getListaGarantiaCreditoCompPorEmpPersCta(gCred);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRetorna;
	}	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 10.09.2013
	 * Recupera Envio Concepto, Envio Monto, Efectuado, Efectuado Concepto y Prioridad Descuento por Cuenta PK y desde un peiorio en adelante.
	 * @param cuentaComp
	 * @param intPeriodo
	 * @return
	 * @throws BusinessException
	 */
	public List<EnviadoEfectuadoComp> getListaEnviadoEfectuado(CuentaComp cuentaComp, Integer intPeriodo) throws BusinessException {
		List<EnviadoEfectuadoComp> lista = null;
    	try{
    		lista = estadoCtaService.getListaEnviadoEfectuado(cuentaComp, intPeriodo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 17.09.2013
	 * Recupera lista de préstamos rechazados de Expediente Credito (Mod.SERVICIOS).
	 * @param cuentaComp
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaPrestamosRechazadosPorCuenta (CuentaComp cuentaComp, Integer intTipoCredito) throws BusinessException {
		List<ExpedienteCreditoComp> lista = null;
    	try{
    		lista = estadoCtaService.getListaPrestamosRechazadosPorCuenta(cuentaComp,intTipoCredito);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 17.09.2013
	 * Recupera lista de préstamos girados o aprobados de Expediente Credito (Mod.MOVIMIENTO).
	 * @param cuentaComp
	 * @param intEstadoSolicPrestamo
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteComp> getListaPrestamosPorCuenta (CuentaComp cuentaComp, Integer intEstadoSolicPrestamo) throws BusinessException {
		List<ExpedienteComp> lista = null;
    	try{
    		lista = estadoCtaService.getListaPrestamosAprobadosPorCuenta(cuentaComp, intEstadoSolicPrestamo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 23.09.2013
	 * Recupera Expediente Prevision (Mod.SERVICIO) por Cuenta PK.
	 * @param cuentaComp
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedientePrevisionComp> getListaExpedientePrevisionComp (CuentaComp cuentaComp) throws BusinessException {
		List<ExpedientePrevisionComp> lista = null;
    	try{
    		lista = estadoCtaService.getListaExpedientePrevisionComp(cuentaComp);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 26.09.2013
	 * Recupera cuotas pagadas y a pagar por periodo del beneficio prestado.
	 * Aplica solo a Fdo. de Sepelio y Fdo. de Retiro.
	 * @param cuentaComp
	 * @return
	 * @throws BusinessException
	 */
	public List<PrevisionSocialComp> getListaPrevisionSocialComp (CuentaComp cuentaComp) throws BusinessException {
		List<PrevisionSocialComp> lista = null;
    	try{
    		lista = estadoCtaService.getListaPrevisionSocialComp(cuentaComp);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 27.09.2013 
	 * Recupera lista de movimientos por Concepto Pago.
	 * Aplica para Fdo. de sepelio y Fdo. de Retiro, si su concepto general es Aporte (PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO)
	 * @param listaConceptoPago
	 * @return
	 * @throws BusinessException
	 */
	public List<MovimientoEstCtaComp> getListaMovimiento(List<ConceptoPago> listaConceptoPago) throws BusinessException {
		List<MovimientoEstCtaComp> lista = null;
    	try{
    		lista = estadoCtaService.getListaMovimiento(listaConceptoPago);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 02.10.2013 
	 * Lista los movimientos que cumplen:
	 * itemCuentaConcepto = PARAM_T_TIPOCUENTA_FONDO_RETIRO && paraTipoConceptoGeneral = PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES.
	 * Estos no se encuentran registrados en CMO_CUENTACONCEPTO, solo se registran en CMO_MOVIMIENTOCTACTE (proceso batch).
	 * @param cuentaComp
	 * @return
	 * @throws BusinessException
	 */
	public List<MovimientoEstCtaComp> getListaMovimientoFdoRetiroInteres(CuentaComp cuentaComp) throws BusinessException {
		List<MovimientoEstCtaComp> lista = null;
    	try{
    		lista = estadoCtaService.getListaMovimientoFdoRetiroInteres(cuentaComp);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013
	 * Recupera Gestion Cobranza Soc por Cuenta Id desde un peiorio en adelante
	 * @param cId
	 * @return
	 * @throws BusinessException
	 */
	public List<GestionCobranzaSoc> getListaGestionCobranzaSoc(CuentaId cId, String strFechaGestion) throws BusinessException {
		List<GestionCobranzaSoc> lista = null;
    	try{
    		lista = estadoCtaService.getListaGestionCobranzaSoc(cId, strFechaGestion);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 29.10.2013
	 * Recupera Datos de Cta. Integrante Y Cuenta por Socio, Tipo Cta. y Situacion Cta.
	 * @param pPK
	 * @param intCuenta
	 * @param intParaTipoCuenta
	 * @param intParaSituacionCuentaCod
	 * @return
	 * @throws BusinessException
	 */
	public List<CuentaComp> getCtaIntYCtaXSocTipYSitCta(SocioPK pPK, Integer intCuenta, Integer intParaTipoCuenta, Integer intParaSituacionCuentaCod) throws BusinessException {
		List<CuentaComp> lista = null;
    	try{
    		lista = estadoCuentaBO.getCtaIntYCtaXSocTipYSitCta(pPK, intCuenta, intParaTipoCuenta, intParaSituacionCuentaCod);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}