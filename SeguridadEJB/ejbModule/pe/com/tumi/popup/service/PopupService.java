package pe.com.tumi.popup.service;

import java.text.ParseException;
import java.util.ArrayList;

import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.empresa.domain.PerNatural;
import pe.com.tumi.empresa.domain.RepresentanteLegal;
import pe.com.tumi.popup.dao.PopupDao;
import pe.com.tumi.popup.domain.CuentaBancaria;

public interface PopupService {
	public abstract PopupDao getPopupDAO();
	
	public abstract ArrayList listarPersonaNatural(Object prmtBusq) throws DaoException, ParseException;
	
	public abstract void grabarBeneficiario(Object usu) throws DaoException, ParseException;

	public abstract void grabarVinculoBeneficio(Object usu) throws DaoException;
	
	public abstract ArrayList listarDomicilio(Object prmtBusq) throws DaoException;
	
	public abstract void grabarDomicilio(Object o) throws DaoException;
	
	public abstract ArrayList listarComunicacion(Object prmtBusq) throws DaoException;
	
	public abstract void grabarComunicacion(Object o) throws DaoException;
	
	public abstract void eliminarPersonaDet(Object prmtPersonaDet) throws DaoException;
	
	public void grabarCtaBancaria(Object beanCtaBancaria) throws DaoException;
	
	public abstract ArrayList listarCtaBancaria(Object cta) throws DaoException;

	public abstract ArrayList listarRepLegal(Object replegal) throws DaoException;

	public abstract void grabarRepLegal(Object repLegal) throws DaoException;

	public abstract ArrayList getRolesPersona(Object replegal) throws DaoException;

	public abstract ArrayList listarPerNaturalVinculo(Object perjuri) throws DaoException;
} 
