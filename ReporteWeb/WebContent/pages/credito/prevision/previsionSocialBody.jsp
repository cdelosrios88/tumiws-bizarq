<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
<!-- Empresa   : Cooperativa El Tumi      -->
<!-- Autor     : Christian De los Ríos	  -->
<!-- MODULO    : REPORTE                  -->			
<!-- FECHA     : 27/11/2013               -->

<h:outputText value="#{fondoSepelioController.limpiarFondoSepelio}" />
<h:outputText value="#{fondoRetiroController.limpiarFondoRetiro}" />
<h:outputText value="#{aesController.limpiarAes}" />

<a4j:region>
	<a4j:jsFunction name="getSubSucursal" reRender="cboSubSucursal"
		action="#{fondoSepelioController.getListSubSucursal}">
		<f:param name="pCboSucursal"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="getSubSucursalFonRet" reRender="cboSubSucursalFonRet"
		action="#{fondoRetiroController.getListSubSucursal}">
		<f:param name="pCboSucursal"></f:param>
	</a4j:jsFunction>
	
	<a4j:jsFunction name="getSubSucursalAes" reRender="cboSubSucursalAes"
		action="#{aesController.getListSubSucursal}">
		<f:param name="pCboSucursal"></f:param>
	</a4j:jsFunction>
</a4j:region>

<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
	<rich:tab label="Fondo de Sepelio" disabled="#{!fondoSepelioController.poseePermiso}">
		<a4j:include viewId="/pages/credito/prevision/tabFondoSepelio/fondoSepelio.jsp"/>
	</rich:tab>
	<rich:tab label="Fondo de Retiro" disabled="#{!fondoRetiroController.poseePermiso}">
		<a4j:include viewId="/pages/credito/prevision/tabFondoRetiro/fondoRetiro.jsp"/>
	</rich:tab>
	<rich:tab label="AES" disabled="#{!aesController.poseePermiso}">
		<a4j:include viewId="/pages/credito/prevision/tabAes/aes.jsp"/>
	</rich:tab>
</rich:tabPanel>