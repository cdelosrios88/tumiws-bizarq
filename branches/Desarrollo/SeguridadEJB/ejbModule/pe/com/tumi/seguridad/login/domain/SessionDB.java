/************************************************************************
/* Nombre de componente: SessionDB
 * Descripción: Bean utilizado para controlar las sesiones bloqueadas en BD
 * Cod. Req.: REQ14-003   
 * Autor : Luis Polanco  Fecha:12/08/2014 16:20:00
 * Versión : v1.0 - Creacion de componente 
 * Fecha creación : 12/08/2014
/* ********************************************************************* */
package pe.com.tumi.seguridad.login.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class SessionDB extends TumiDomain {
	private String strSID;
	private String strSerial;
	private String strUserName;
	private String strBdUser;
	private String strTypeLock;
	private String strProcessLocker;
	private String strSchemaName;
	private String strObjectName;
	private String strObjectType;
	private String strProgram;
	private String strOwner;
	private String strSqlQuery;
	
	private String strMachine;
	private String strSqlId;
	
	public String getStrSID() {
		return strSID;
	}
	public void setStrSID(String strSID) {
		this.strSID = strSID;
	}
	public String getStrSerial() {
		return strSerial;
	}
	public void setStrSerial(String strSerial) {
		this.strSerial = strSerial;
	}
	public String getStrUserName() {
		return strUserName;
	}
	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}
	public String getStrBdUser() {
		return strBdUser;
	}
	public void setStrBdUser(String strBdUser) {
		this.strBdUser = strBdUser;
	}
	public String getStrTypeLock() {
		return strTypeLock;
	}
	public void setStrTypeLock(String strTypeLock) {
		this.strTypeLock = strTypeLock;
	}
	public String getStrProcessLocker() {
		return strProcessLocker;
	}
	public void setStrProcessLocker(String strProcessLocker) {
		this.strProcessLocker = strProcessLocker;
	}
	public String getStrSchemaName() {
		return strSchemaName;
	}
	public void setStrSchemaName(String strSchemaName) {
		this.strSchemaName = strSchemaName;
	}
	public String getStrObjectName() {
		return strObjectName;
	}
	public void setStrObjectName(String strObjectName) {
		this.strObjectName = strObjectName;
	}
	public String getStrObjectType() {
		return strObjectType;
	}
	public void setStrObjectType(String strObjectType) {
		this.strObjectType = strObjectType;
	}
	public String getStrProgram() {
		return strProgram;
	}
	public void setStrProgram(String strProgram) {
		this.strProgram = strProgram;
	}
	public String getStrOwner() {
		return strOwner;
	}
	public void setStrOwner(String strOwner) {
		this.strOwner = strOwner;
	}
	public String getStrSqlQuery() {
		return strSqlQuery;
	}
	public void setStrSqlQuery(String strSqlQuery) {
		this.strSqlQuery = strSqlQuery;
	}
	public String getStrMachine() {
		return strMachine;
	}
	public void setStrMachine(String strMachine) {
		this.strMachine = strMachine;
	}
	public String getStrSqlId() {
		return strSqlId;
	}
	public void setStrSqlId(String strSqlId) {
		this.strSqlId = strSqlId;
	}
}
