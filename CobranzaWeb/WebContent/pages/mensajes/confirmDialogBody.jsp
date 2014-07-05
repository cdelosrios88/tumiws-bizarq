<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 31/08/2011               -->
	
<rich:modalPanel id="mpConfirmMessage" width="360" height="180"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="#{messageController.strTypeMessage}"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="#{messageController.strCloseIconPath}" styleClass="hidelink" id="hideMessage" />
			<rich:componentControl for="mpConfirmMessage" attachTo="hideMessage" operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>
	<h:form>
		<rich:panel style="border: 0px solid #17356f;background-color:#DEEBF5">
			<h:panelGrid border="0" columns="2" style="text-align: left">
				<rich:column width="55">
					<h:graphicImage value="#{messageController.strMessageIcon}" styleClass="hidelink" id="hideMessage" />
				</rich:column>
				<rich:column>
					<h:outputText id="lblCodigo" value="#{messageController.strMessage}"></h:outputText>
				</rich:column>
			</h:panelGrid>
			<rich:spacer height="16px" />
			<h:panelGrid border="0" style="margin:0 auto; width: 100%" columns="2">
				<a4j:commandButton value="#{messageController.strAcceptBtn}" styleClass="btnEstilos" style="color:#000000"
								onmousedown="#{messageController.strFunctionAccept}" onclick="Richfaces.hideModalPanel('mpConfirmMessage')" onmouseup="#{messageController.strFunctionValidator}">
				</a4j:commandButton>
				<a4j:commandButton value="#{messageController.strCancelBtn}" styleClass="btnEstilos" style="color:#000000"
								onmousedown="rejectMessage()" onclick="Richfaces.hideModalPanel('mpConfirmMessage')" onmouseup="#{messageController.strFunctionValidator}">
				</a4j:commandButton>
			</h:panelGrid>
		</rich:panel>
		<rich:spacer height="4px" />
		<rich:spacer height="8px" />
	</h:form>
</rich:modalPanel>