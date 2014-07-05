<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
<!-- Empresa   : Cooperativa El Tumi      			-->
<!-- Autor     : Christian De los Ríos	  			-->
<!-- MODULO    : REPORTE - Planillas de Descuento   -->			
<!-- FECHA     : 23/02/2014               			-->

<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>

<h:outputText value="#{planillaDescuentoController.limpiarPlanillaDescuento}"/>
<h:outputText value="#{pendienteCobroController.limpiarPendienteCobro}"/>
<h:outputText value="#{morosidadPlanillaController.limpiarMorosidadPlanilla}"/>
<h:outputText value="#{socioDiferenciaController.limpiarSociosDiferencia}"/>

<a4j:region>
	<a4j:jsFunction name="getSubSucursal" reRender="cboSubSucursal"
		action="#{planillaDescuentoController.getListSubSucursal}">
		<f:param name="pCboSucursal"/>
	</a4j:jsFunction>
</a4j:region>

<a4j:region>
	<a4j:jsFunction name="getSubSucursalPC" reRender="cboSubSucursalPC"
		action="#{pendienteCobroController.getListSubSucursal}">
		<f:param name="pCboSucursalPC"/>
	</a4j:jsFunction>
</a4j:region>

<a4j:region>
	<a4j:jsFunction name="getSubSucursalMP" reRender="cboSubSucursalMP"
		action="#{morosidadPlanillaController.getListSubSucursal}">
		<f:param name="pCboSucursalMP"/>
	</a4j:jsFunction>
</a4j:region>

<a4j:region>
	<a4j:jsFunction name="getSubSucursalSD" reRender="cboSubSucursalSD"
		action="#{socioDiferenciaController.getListSubSucursal}">
		<f:param name="pCboSucursalSD"/>
	</a4j:jsFunction>
</a4j:region>

<a4j:region>
	<a4j:jsFunction name="getListUnidEjecutora" reRender="cboEntidad"
		action="#{socioDiferenciaController.getListUnidadEjecutora}">
		<f:param name="pCboSubSucursalSD"/>
	</a4j:jsFunction>
</a4j:region>

<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
	<rich:tab label="Control de Planillas Desagregado" disabled="#{!planillaDescuentoController.poseePermiso}">
		<a4j:include viewId="/pages/cobranza/planillaDescuento/tabPlanillaDsctoDesagregado/planillaDsctoDesagregado.jsp"/>
	</rich:tab>
	<rich:tab label="Pendientes de Cobro" disabled="#{!pendienteCobroController.poseePermiso}">
		<a4j:include viewId="/pages/cobranza/planillaDescuento/tabPendienteCobro/pendienteCobro.jsp"/>
	</rich:tab>
	<rich:tab label="Morosidad de Planilla" disabled="#{!morosidadPlanillaController.poseePermiso}">
		<a4j:include viewId="/pages/cobranza/planillaDescuento/tabMorosidadPlanilla/morosidadPlanilla.jsp"/>
	</rich:tab>
	<rich:tab label="Socios con Diferencia" disabled="#{!socioDiferenciaController.poseePermiso}">
		<a4j:include viewId="/pages/cobranza/planillaDescuento/tabSociosDiferencia/socioDiferencia.jsp"/>
	</rich:tab>
</rich:tabPanel>