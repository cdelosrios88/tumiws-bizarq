package pe.com.tumi.popup.service.impl;

import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.popup.dao.PopupDao;
import pe.com.tumi.popup.domain.CuentaBancaria;
import pe.com.tumi.popup.service.PopupService;

public class PopupServiceImpl implements PopupService {

	private PopupDao popupDAO;

	public PopupDao getPopupDAO() {
		return popupDAO;
	}
	public void setPopupDAO(PopupDao popupDAO) {
		this.popupDAO = popupDAO;
	}
	
	public ArrayList listarPersonaNatural(Object prmtBusq) throws DaoException, ParseException {
		try{
			return getPopupDAO().listarPersonaNatural(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarBeneficiario(Object o) throws DaoException, ParseException {
		try{
			getPopupDAO().grabarBeneficiario(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public void grabarVinculoBeneficio(Object o) throws DaoException {
		try{
			getPopupDAO().grabarVinculoBeneficio(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public ArrayList listarDomicilio(Object prmtBusq) throws DaoException {
		try{
			return getPopupDAO().listarDomicilio(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarDomicilio(Object o) throws DaoException {
		try{
			getPopupDAO().grabarDomicilio(o);
		}catch(Exception e) {
			throw new DaoException(e);			
		}
	}
	
	public ArrayList listarComunicacion(Object prmtBusq) throws DaoException {
		try{
			return getPopupDAO().listarComunicacion(prmtBusq);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void grabarComunicacion(Object o) throws DaoException {
		try{
			getPopupDAO().grabarComunicacion(o);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
	public void eliminarPersonaDet(Object prmtUsuarioDet) throws DaoException {
		try{
			getPopupDAO().eliminarPersonaDet(prmtUsuarioDet);
		}catch(Exception e) {
			throw new DaoException(e);			
		}	
	}
	
	public void grabarCtaBancaria(Object beanCtaBancaria) throws DaoException {
		try{
			getPopupDAO().grabarCtaBancaria(beanCtaBancaria);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	public ArrayList listarCtaBancaria(Object cta) throws DaoException {
		try{
			return getPopupDAO().listarCtaBancaria(cta);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	public ArrayList listarRepLegal(Object replegal) throws DaoException {
		try{
			return getPopupDAO().listarRepLegal(replegal);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	public void grabarRepLegal(Object repLegal) throws DaoException {
		try{
			getPopupDAO().grabarRepLegal(repLegal);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	public ArrayList getRolesPersona(Object replegal) throws DaoException {
		try{
			return getPopupDAO().getRolesPersona(replegal);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	public ArrayList listarPerNaturalVinculo(Object perjuri)
			throws DaoException {
		try{
			return getPopupDAO().listarPerNaturalVinculo(perjuri);
		}catch(Exception e) {
			throw new DaoException(e);
		}
	}
	
}