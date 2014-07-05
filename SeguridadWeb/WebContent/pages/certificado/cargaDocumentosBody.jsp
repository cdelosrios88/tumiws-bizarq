<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
	<!-- Company:ASIS TP -- Autor:ev -->
	<!-- Adjuntar Documentos -->
<h:form id="frmCargaDocumento">

			<rich:panel>
	      <f:facet name="header">
	        <h:outputText value="Adjuntar Archivos" />
	      </f:facet>
		

			<h:panelGrid columns="2" id="usuarioPanel">
				<h:outputText value="Nro de Certificado: " />
				<h:inputText value="#{usuarioController.bean.id}" readonly="true" size="6" 
					style="background-color: #D0D0D0" id="id">
					<f:validateLength minimum="1" maximum="6" />
				</h:inputText>
			</h:panelGrid>
		
	<!-- 	<h:panelGrid columns="2" id="usuarioPanel1">
				<rich:spacer height="4px"/><rich:spacer height="4px"/>
				<form id="frmUpload" method="post" enctype="multipart/form-data" 
					action="servlet/UploadFile">
					<input type="file" name="fichero" value="Browse" accept="text/txt" style="font-size:10px;"/>
					<input type="submit" value="Cargar " style="font-size:10px;"/>
		</form>
				<rich:spacer height="10px"/>
			</h:panelGrid>  -->
				
			<rich:spacer height="10px"/><rich:spacer height="10px"/>
			
	
			<h:panelGrid columns="1" id="documentos">
				<rich:datascroller for="rdt1" maxPages="15" rendered="#{not empty CertificadoController.beanList}"/>
				<rich:spacer height="10px"/>
				<rich:dataTable id="rdt1" rows="10" value="#{CertificadoController.beanList}" var="item"  width="100%" 
				  dir="LTR" frame="hsides" rules="all" rowKeyVar="rowKey" >
				  
					<f:facet name="header">
						<h:outputText value="Descripción de Documentos" />
					</f:facet>
					
					<rich:column>
						<f:facet name="header">
							<h:outputText value="#" />
						</f:facet>
						<h:outputText value="#{rowKey+1}"></h:outputText>
					</rich:column>
																		
					<rich:column >
						<f:facet name="header">
							<h:outputText value="Nombre de Documento" />
						</f:facet>
						<h:outputText value="Documento.doc"></h:outputText> 
					</rich:column>
					
									
					<rich:column >
						<f:facet name="header">
							<h:outputText value="Ruta de Documento" />
						</f:facet>
						<h:outputText value="C:/Documentos/"></h:outputText> 
					</rich:column>
					
					<rich:column >
						<f:facet name="header">
							<h:outputText value="Usuario Sap" />
						</f:facet>
						<h:outputText value="U41233989"></h:outputText> 
					</rich:column>
					
					<!-- Ver Archivos Adjuntos del  Certificado -->
					<rich:column>
						<f:facet name="header">
							<h:outputText value="Descargar" />
						</f:facet>
						<h:commandLink action="archivo" id="RolLink"
							actionListener="#{usuarioController.loadRol}" immediate="true">
							<h:graphicImage value="/images/icons/descarga.png" 
									style="border:0px;align:center"  />
							<f:param name="sid3" id="sid3" value="#{item.id}" />
							<rich:toolTip for="RolLink" value="Descargar"/>
						</h:commandLink>
					</rich:column>
				
			</rich:dataTable>
		</h:panelGrid>
 <a4j:commandButton action="buscarCertificado" value=" Volver "			
			reRender="pgDatatable2" styleClass="spacio"/>
		
			
			<h:commandLink value=" aquí " action="#" id="clDownloadOks" immediate="true"
							actionListener="#{CertificadoController.downloadOks}"/>
	</rich:panel>

</h:form>
	