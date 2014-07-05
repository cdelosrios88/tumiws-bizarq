<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Arturo Julca    		  -->	
	

<a4j:include viewId="/pages/compras/popup/agregarRequisicionDetalle.jsp"/>
<a4j:include viewId="/pages/compras/popup/alertaRequisicion.jsp"/>

<a4j:include viewId="/pages/compras/popup/buscarRequisicionInforme.jsp"/>
<a4j:include viewId="/pages/compras/popup/buscarPersonaInforme.jsp"/>
<a4j:include viewId="/pages/compras/popup/adjuntarDocumentoInforme.jsp"/>
<a4j:include viewId="/pages/compras/popup/alertaInforme.jsp"/>

<a4j:include viewId="/pages/compras/popup/buscarRequisicionContrato.jsp"/>
<a4j:include viewId="/pages/compras/popup/buscarPersonaContrato.jsp"/>
<a4j:include viewId="/pages/compras/popup/buscarDomicilioContrato.jsp"/>
<a4j:include viewId="/pages/compras/popup/agregarDomicilioContrato.jsp"/>
<a4j:include viewId="/pages/compras/popup/adjuntarDocumentoContrato.jsp"/>
<a4j:include viewId="/pages/compras/popup/buscarAnteriorContrato.jsp"/>
<a4j:include viewId="/pages/compras/popup/alertaContrato.jsp"/>

<a4j:include viewId="/pages/compras/popup/buscarRequisicionCuadro.jsp"/>
<a4j:include viewId="/pages/compras/popup/agregarProveedorCuadro.jsp"/>
<a4j:include viewId="/pages/compras/popup/buscarPersonaCuadro.jsp"/>
<a4j:include viewId="/pages/compras/popup/adjuntarDocumentoCuadro.jsp"/>
<a4j:include viewId="/pages/compras/popup/agregarProveedorDetalleCuadro.jsp"/>
<a4j:include viewId="/pages/compras/popup/alertaCuadro.jsp"/>

<a4j:include viewId="/pages/compras/popup/buscarRequisicionOrden.jsp"/>
<a4j:include viewId="/pages/compras/popup/agregarDetalleOrden.jsp"/>
<a4j:include viewId="/pages/compras/popup/buscarPersonaOrden.jsp"/>
<a4j:include viewId="/pages/compras/popup/buscarPlanCuentaOrden.jsp"/>
<a4j:include viewId="/pages/compras/popup/agregarDocumentoOrden.jsp"/>
<a4j:include viewId="/pages/compras/popup/buscarCuentaBancariaOrden.jsp"/>
<a4j:include viewId="/pages/compras/popup/alertaOrden.jsp"/>

<a4j:include viewId="/pages/compras/popup/buscarOrdenCompraSunat.jsp"/>
<a4j:include viewId="/pages/compras/popup/agregarDetalleSunat.jsp"/>
<a4j:include viewId="/pages/compras/popup/agregarLetraSunat.jsp"/>
<a4j:include viewId="/pages/compras/popup/alertaDocumentoSunat.jsp"/>
<!-- Agregado por cdelosrios, 01/11/2013 -->
<a4j:include viewId="/pages/compras/popup/adjuntarDocumentoSunat.jsp"/>
<a4j:include viewId="/pages/compras/popup/verDocumentoSunat.jsp"/>
<!-- Fin agregado por cdelosrios, 01/11/2013 -->
<!-- Agregado por cdelosrios, 15/11/2013 -->
<a4j:include viewId="/pages/compras/popup/verDocumentoAdelanto.jsp"/>
<!-- Fin agregado por cdelosrios, 15/11/2013 -->
<h:outputText value="#{requisicionController.limpiarRequisicion}" />
<h:outputText value="#{contratoController.limpiarContrato}" />
<h:outputText value="#{informeController.limpiarInforme}" />
<h:outputText value="#{cuadroController.limpiarCuadro}" />
<h:outputText value="#{ordenController.limpiarOrden}" />
<h:outputText value="#{sunatController.limpiarSunat}" />
<rich:tabPanel activeTabClass="activo" inactiveTabClass="inactivo" disabledTabClass="disabled">

		<rich:tab label="Requisicion" disabled="#{!requisicionController.poseePermiso}">
            <a4j:include viewId="/pages/compras/tabRequisicion.jsp"/>
     	</rich:tab>
		<rich:tab label="Contrato" disabled="#{!contratoController.poseePermiso}">
            <a4j:include viewId="/pages/compras/tabContrato.jsp"/>
     	</rich:tab>
     	<rich:tab label="Informe" disabled="#{!informeController.poseePermiso}">
            <a4j:include viewId="/pages/compras/tabInforme.jsp"/>
     	</rich:tab>
     	<rich:tab label="Cuadro Comparativo" disabled="#{!cuadroController.poseePermiso}">
			<a4j:include viewId="/pages/compras/tabCuadro.jsp"/>
     	</rich:tab>
     	<rich:tab label="Orden Bienes/Servicio" disabled="#{!ordenController.poseePermiso}">
            <a4j:include viewId="/pages/compras/tabOrdenContable.jsp"/>
     	</rich:tab>
     	<rich:tab label="Documento SUNAT" disabled="#{!sunatController.poseePermiso}">
            <a4j:include viewId="/pages/compras/tabDocumentoSunat.jsp"/>
     	</rich:tab>
</rich:tabPanel>