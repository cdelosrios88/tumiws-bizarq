package pe.com.tumi.popup.dao;

import java.text.ParseException;
import java.util.ArrayList;

import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.popup.domain.CuentaBancaria;

public interface PopupDao {
	
	public abstract ArrayList listarPersonaNatural(Object prmtBusq) throws DaoException, ParseException;
	
	public abstract void grabarBeneficiario(Object o) throws DaoException, ParseException;
	
	public abstract void grabarVinculoBeneficio(Object o) throws DaoException, ParseException;
	
	public abstract ArrayList listarDomicilio(Object prmtBusq) throws DaoException;
	
	public abstract void grabarDomicilio(Object o) throws DaoException, ParseException;
	
	public abstract ArrayList listarComunicacion(Object prmtBusq) throws DaoException;
	
	public abstract void grabarComunicacion(Object o) throws DaoException, ParseException;
	
	public abstract void eliminarPersonaDet(Object prmtPersonaDet) throws DaoException;

	public abstract void grabarCtaBancaria(Object beanCtaBancaria) throws DaoException;

	public abstract ArrayList listarCtaBancaria(Object cta) throws DaoException;

	public abstract ArrayList listarRepLegal(Object replegal) throws DaoException;

	public abstract void grabarRepLegal(Object repLegal) throws DaoException;

	public abstract ArrayList getRolesPersona(Object replegal) throws DaoException;

	public abstract ArrayList listarPerNaturalVinculo(Object replegal) throws DaoException;
}
