package pe.com.tumi.parametro.general.facade;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.domain.AuditoriaMotivo;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.Detraccion;
import pe.com.tumi.parametro.general.domain.Tarifa;
import pe.com.tumi.parametro.general.domain.TarifaId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.domain.TipoCambioId;
import pe.com.tumi.parametro.general.domain.Ubigeo;

@Remote
public interface GeneralFacadeRemote {
	public List<Ubigeo> getListaUbigeoDeDepartamento() throws BusinessException;
	public List<Ubigeo> getListaUbigeoDeProvinciaPorIdUbigeo(Integer pIntId) throws BusinessException;
	public List<Ubigeo> getListaUbigeoDeDistritoPorIdUbigeo(Integer pIntId) throws BusinessException;
	public TipoArchivo getTipoArchivoPorPk(Integer pId) throws BusinessException;
	public Archivo grabarArchivo(Archivo o)throws BusinessException;
	public Archivo getArchivoPorPK(ArchivoId pId) throws BusinessException;
	public TipoCambio grabarTipoCambio(TipoCambio o) throws BusinessException;
	public TipoCambio modificarTipoCambio(TipoCambio o) throws BusinessException;
	public TipoCambio getTipoCambioPorPK(TipoCambioId pId) throws BusinessException;
	public List<TipoCambio> getListaTipoCambioBusqueda(TipoCambio pTipoCambio) throws BusinessException;
	public Tarifa grabarTarifa(Tarifa o) throws BusinessException;
	public Tarifa modificarTarifa(Tarifa o) throws BusinessException;
	public Tarifa getTarifaPorPK(TarifaId pId) throws BusinessException;
	public List<Tarifa> getListaTarifaBusqueda(Tarifa pTarifa) throws BusinessException;
	public Archivo getListaArchivoDeVersionFinalPorTipoYItem(Integer intParaTipoCod , Integer intItemArchivo) throws BusinessException;
	public Auditoria grabarAuditoria(Auditoria pAuditoria) throws BusinessException;
	public List<Detraccion> getListaDetraccionTodos() throws BusinessException;
	public Tarifa getTarifaIGV(Integer intIdEmpresa, Date dtFecha) throws BusinessException;
	public Tarifa getTarifaIGVActual(Integer intIdEmpresa) throws BusinessException;
	public Detraccion getDetraccionPorTipo(Integer intTipoDetraccion) throws BusinessException;
	public TipoCambio getTipoCambioPorMonedaYFecha(Integer intParaMoneda, Date dtFechaCambio, Integer intIdEmpresa) throws BusinessException;
	public TipoCambio getTipoCambioActualPorClaseYMoneda(Integer pIntPersEmpresa,Integer pIntParaClaseTipoCambio,Integer pIntParaMoneda) throws BusinessException;
	public AuditoriaMotivo grabarAuditoriaMotivo(AuditoriaMotivo pAuditoriaMotivo) throws BusinessException;	
	public void eliminarArchivo(Archivo o)throws BusinessException;
	//RVILLARREAL 22.04.2014
	public List<Ubigeo> getListaPorIdUbigeo(Integer pIntId) throws BusinessException;
}

