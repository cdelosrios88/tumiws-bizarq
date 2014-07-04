<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
	<h:form>
		<h:panelGrid columns="2">
			<h:outputText value="Nueva contraseña: "/>
			<h:inputSecret id="newPassword" immediate="true"
				value="#{loginController.newPassword}">
			</h:inputSecret>
			<h:outputText value="Confirmar contraseña: "/>
			<h:inputSecret id="confirmPassword" immediate="true"
				value="#{loginController.verifyPassword}">
			</h:inputSecret>
			<h:outputText value="." style="color:white;font-size: 5px;"/>
		</h:panelGrid>
		<h:panelGrid columns="3">
			<a4j:commandButton id="btnActualizar" value=" Actualizar "
				actionListener="#{loginController.changePassword}"/>
			<h:outputText value="." style="color:white;font-size: 5px;"/>				
			<a4j:commandButton value=" Cancelar " action="inicio"  actionListener="#{loginController.clean}"   />
		</h:panelGrid>				
</h:form>