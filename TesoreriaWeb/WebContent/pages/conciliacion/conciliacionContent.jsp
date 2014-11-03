<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>


<rich:modalPanel id="panelUpdateViewConciliacion" width="400" height="155" resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Modificar/Ver Conciliacion"/>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink" id="hidelinkConciliacion"/>
			<rich:componentControl for="panelUpdateViewConciliacion" attachTo="hidelinkConciliacion" operation="hide" event="onclick"/>
		</h:panelGroup>
	</f:facet>
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5;width: 370px;">                 
			<h:panelGrid columns="2"  border="0" cellspacing="4" >
				<h:outputText value="¿Desea Ud. Actualizar o Ver una Conciliacion?" style="padding-right: 35px;"/>
			</h:panelGrid>
			<rich:spacer height="16px"/>
			<h:panelGrid columns="3" border="0"  style="width: 300px;">
				<a4j:commandButton value="Actualizar" actionListener="#{conciliacionController.irModificarConciliacion}" 
					styleClass="btnEstilos" 
					reRender="contPanelInferior,panelUpdateViewConciliacion"
					oncomplete="Richfaces.hideModalPanel('panelUpdateViewConciliacion')"/>
	
				<a4j:commandButton value="Ver" actionListener="#{conciliacionController.irVerSolicitudActividad}" 
					styleClass="btnEstilos" reRender="contPanelInferior,panelUpdateViewConciliacion"
					oncomplete="Richfaces.hideModalPanel('panelUpdateViewConciliacion')"/>
			</h:panelGrid>
		</rich:panel>
</rich:modalPanel> 
