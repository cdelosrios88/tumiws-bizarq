package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.List;

import pe.com.tumi.cobranza.planilla.dao.DevolucionDao;
import pe.com.tumi.cobranza.planilla.domain.Devolucion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class DevolucionDaoIbatis extends TumiDaoIbatis implements DevolucionDao{
	
	public Devolucion grabar(Devolucion o) throws DAOException{
		Devolucion dto = null;
		try{
			System.out.println("o.getId() ---> "+o.getId());
			System.out.println("o ---> "+o);
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			System.out.println(""+e);
			throw new DAOException(e);
		}
		return dto;
	}
	
	
	public Devolucion modificar(Devolucion o) throws DAOException{
		Devolucion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;	
	}
	
	public List<Devolucion> getListaPorSolicitudCtaCteTipo(Object o) throws DAOException{
		
		List<Devolucion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorSolicitudCtaCteTipo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
		
	}
	
	

}
