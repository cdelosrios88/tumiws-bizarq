<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<!-- 
-----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-006       			30/10/2014     Christian De los Ríos        Se realiza el popup concerniente a los botonoes Ver y Modificar
 -->

<rich:modalPanel id="panelUpdateViewConciliacion" width="450"
	height="138" resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Ver Conciliación" styleClass="tamanioLetra"/>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hidelinkTipoCambio" />
			<rich:componentControl for="panelUpdateViewConciliacion"
				attachTo="hidelinkTipoCambio" operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>
	<h:form id="formUpViewConcil">
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5">
			<h:panelGrid border="0">
				<h:outputText id="lblCodigo"
					value="Elija la opción que desee realizar..."
					styleClass="tamanioLetra" />
			</h:panelGrid>
			<rich:spacer height="16px" />
			<h:panelGrid columns="3" border="0"
				style="margin:0 auto; width: 100%">
				<a4j:commandButton value="Modificar"
					
					action="#{conciliacionController.irModificarConciliacion}"
					styleClass="btnEstilos" reRender="contPanelInferior,panelMensaje,panelDatosAnular"
					oncomplete="Richfaces.hideModalPanel('panelUpdateViewConciliacion')" />

				<a4j:commandButton value="Ver"
					action="#{conciliacionController.verRegistro}"
					styleClass="btnEstilos" reRender="contPanelInferior,panelMensaje,panelDatosAnular"
					oncomplete="Richfaces.hideModalPanel('panelUpdateViewConciliacion')" />
			</h:panelGrid>
		</rich:panel>
		<rich:spacer height="4px" />
		<rich:spacer height="8px" />
	</h:form>
</rich:modalPanel>