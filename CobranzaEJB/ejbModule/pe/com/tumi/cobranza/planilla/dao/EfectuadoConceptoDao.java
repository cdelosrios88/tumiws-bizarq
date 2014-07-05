package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;

public interface EfectuadoConceptoDao extends TumiDao{
	public List<EfectuadoConcepto> getListaPorEfectuado(Object o) throws DAOException;
	/** CREADO 06/08/2013 **/
	public List<EfectuadoConcepto> getListaPorEfectuadoYExpediente(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 14-09-2013
	public List<EfectuadoConcepto> getListaPorEfectuadoPKEnvioConcepto(Object o) throws DAOException;
	public EfectuadoConcepto grabar(EfectuadoConcepto o)throws DAOException;
	public EfectuadoConcepto grabarSub(EfectuadoConcepto o)throws DAOException;
	
	public EfectuadoConcepto modificar(EfectuadoConcepto o)throws DAOException;
	
	public List<EfectuadoConcepto> montoExpedientePrestamo(Object o) throws DAOException;
	
	public List<EfectuadoConcepto> montoExpedienteInteres(Object o) throws DAOException;
	
	public List<EfectuadoConcepto> montoCuentaPorPagar(Object o) throws DAOException;

		
	public List<EfectuadoConcepto> prestamoInteres(Object o) throws DAOException;	
}	
