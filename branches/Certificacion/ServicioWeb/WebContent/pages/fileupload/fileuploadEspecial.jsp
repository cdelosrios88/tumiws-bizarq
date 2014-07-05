<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html><%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Modulo    : Seguridad                -->
	<!-- Prototipo : Empresas			      -->			
	<!-- Fecha     : 31/08/2011               -->
	
<rich:modalPanel id="mpFileUploadEspecial" width="370" height="230"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="#{fileUploadControllerServicio.strTitle}"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="#{fileUploadControllerServicio.strCloseIconPath}"
				styleClass="hidelink" id="hideFileUpload" />
			<rich:componentControl for="mpFileUploadEspecial" attachTo="hideFileUpload"
				operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>
	<h:form>
		<h:panelGroup>
			<h:panelGrid border="0" columns="1" style="text-align:left; margin:0 auto">
				<rich:column>
					<h:outputText value="#{fileUploadControllerServicio.strDescripcion}"></h:outputText>
				</rich:column>
				<rich:column>
					<rich:fileUpload 
						addControlLabel="Adjuntar Archivo"
						clearControlLabel="Limpiar" 
						cancelEntryControlLabel="Cancelar"
						uploadControlLabel="Subir Archivo" 
						listHeight="64" listWidth="320"
						fileUploadListener="#{fileUploadControllerServicio.adjuntarArchivo}"
						maxFilesQuantity="1" 
						doneLabel="Archivo cargado correctamente"
						immediateUpload="#{applicationScope.FileUtil.autoUpload}"
						acceptedTypes="jpg, gif, png, bmp">
						<f:facet name="label">
						<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}" />
						</f:facet>
					</rich:fileUpload>
				</rich:column>
			</h:panelGrid>
			<rich:spacer height="16px" />
			<h:panelGrid border="0" style="margin:0 auto">
				<a4j:commandButton value="Aceptar" 
									styleClass="btnEstilos" 
									onmousedown="#{fileUploadControllerServicio.strJsFunction2}"
									reRender="pgSolicCreditoEspecial"
									oncomplete="Richfaces.hideModalPanel('mpFileUploadEspecial')">
				</a4j:commandButton>
			</h:panelGrid>
		</h:panelGroup>
		<rich:spacer height="4px" />
		<rich:spacer height="8px" />
	</h:form>
</rich:modalPanel>