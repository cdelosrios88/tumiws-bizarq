package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;

public interface EnviomontoDao extends TumiDao{
	public Enviomonto grabar(Enviomonto pDto) throws DAOException;
	public Enviomonto modificar(Enviomonto o) throws DAOException;
	public List<Enviomonto> getListaPorPk(Object o) throws DAOException;
	public List<Enviomonto> getListaDeBuscar(Object o) throws DAOException;
	public List<Enviomonto> getLista(Object o) throws DAOException;
	/** CREADO 06-08-2013 **/
	public List<Enviomonto> getListaPorEnvioConcepto(Object o) throws DAOException;
	
	public List<Enviomonto> getListaEnvioMontoPlanillaEfectuada(Object o)throws DAOException;
	public List<Enviomonto> getListaItemConcepto(Object o)throws DAOException;
	public List<Enviomonto> getListaEnviomontoXItemEnvioresumen(Object o)throws DAOException;
	public List<Enviomonto> getListaXItemEnvioCtoGral(Object o)throws DAOException;
	
	public List<Enviomonto> getEnviomontoPorInt(Object o) throws DAOException;	

}	
