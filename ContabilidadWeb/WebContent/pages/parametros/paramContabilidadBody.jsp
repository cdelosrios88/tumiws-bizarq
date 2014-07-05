<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Jhonathan Morán    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO SOCIO ESTRUCTURA     -->
	<!-- Fecha     : 17/05/2012               		-->

<script language="JavaScript" src="/js/main.js"  type="text/javascript">

</script>

<a4j:include viewId="/pages/mensajes/mensajesPanel.jsp"/>

<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">
   	 <rich:tab label="Tipo de Cambio" disabled="#{tipoCambioController.blnPermisoMenu}">
   	 	<h:panelGroup layout="block" style="border:1px solid #17356f">
   	 		<a4j:include viewId="/pages/parametros/tabTipoCambio.jsp"/>
   	 	</h:panelGroup>
   	 </rich:tab>
   	 <rich:tab label="Tarifas Generales" disabled="#{tarifaController.blnPermisoMenu}">
   	 	<h:panelGroup layout="block" style="border:1px solid #17356f">
   	 		<a4j:include viewId="/pages/parametros/tabTarifaGeneral.jsp"/>
   	 	</h:panelGroup>
   	 </rich:tab>
</rich:tabPanel>