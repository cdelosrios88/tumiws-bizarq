<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Franko Yalico    		  -->
	<!-- Modulo    : Cobranza                 -->
	<!-- Prototipo : EFECTUADO Upload		  -->
	<!-- Fecha     : 29/11/2013               -->

<rich:modalPanel id="pUploadEfectuadoConArchivo" width="400" height="200"
				resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Upload"/>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
				<rich:componentControl for="pUploadEfectuadoConArchivo" operation="hide" event="onclick"/>
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<rich:spacer height="3px" />
	<h:form id="fmUpload">
		<h:panelGrid border="0">
			<rich:column>
				<rich:fileUpload id="uploadExcel"
					addControlLabel="Adjuntar Archivo"					
					clearControlLabel="Limpiar"
					cancelEntryControlLabel="Cancelar"
					uploadControlLabel="Subir Archivo"
					listHeight="65"
					listWidth="320"
					fileUploadListener="#{efectuadoController.manejarSubirArchivo}"
					maxFilesQuantity="1"
					doneLabel="Archivo cargado correctamente"
					immediateUpload="false"
					acceptedTypes="xls,XLS,txt,TXT">
					<f:facet name="label">
						<h:outputText value="{_KB}KB de {KB}KB cargados --- {mm}:{ss}"/>
					</f:facet>					
				</rich:fileUpload>
			</rich:column>
		</h:panelGrid>
		
		
		
		<h:panelGrid columns="3">
			<rich:column width="100">	
			</rich:column>
			<rich:column width="100">				
				<a4j:commandButton value="Aceptar"
					styleClass="btnEstilos"	
					action="#{efectuadoController.aceptarUpload}"						
					reRender="contPanelInferiorConArchivo,panelMensajeInicial"
					oncomplete="Richfaces.hideModalPanel('pUploadEfectuadoConArchivo')"/>
			</rich:column>
			<rich:column style="border:none">
				<a4j:commandButton value="Cancelar"
					styleClass="btnEstilos"
					action= "#{efectuadoController.habilitarPanelInferiorConArchivo}"
					reRender="contPanelInferiorConArchivo"
					oncomplete="Richfaces.hideModalPanel('pUploadEfectuadoConArchivo')"/>
			</rich:column>
		</h:panelGrid>
	</h:form>
</rich:modalPanel>

