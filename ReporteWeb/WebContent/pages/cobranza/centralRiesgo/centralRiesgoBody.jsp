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

<h:outputText value="#{carteraCreditoController.limpiarCarteraCredito}"/>
<h:outputText value="#{fenacrepController.limpiarFenacrep}"/>

<a4j:region>
	<a4j:jsFunction name="getSubSucursal" reRender="cboSubSucursal"
		action="#{carteraCreditoController.getListSubSucursal}">
		<f:param name="pCboSucursal"/>
	</a4j:jsFunction>
</a4j:region>

<a4j:region>
	<a4j:jsFunction name="getSubTipoCredito" reRender="idSubTipoCredito"
		action="#{carteraCreditoController.getListSubTipoCredito}">
		<f:param name="pCboTipoCredito"/>
	</a4j:jsFunction>
</a4j:region>

<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
	<rich:tab label="Cartera de Créditos" >
		<a4j:include viewId="/pages/cobranza/centralRiesgo/tabCarteraCredito/carteraCredito.jsp"/>
	</rich:tab>
	<rich:tab label="Terceros - Minsa" >
	
	</rich:tab>
	<rich:tab label="Reporte FENACREP" >
		<a4j:include viewId="/pages/cobranza/centralRiesgo/tabFenacrep/reporteFENACREP.jsp"/>
	</rich:tab>
</rich:tabPanel>