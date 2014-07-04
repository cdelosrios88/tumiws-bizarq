<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : Franko Yalico    		  -->
	<!-- Modulo    : Cobranza                 -->
	<!-- Prototipo : EFECTUADO new Person	  -->
	<!-- Fecha     : 29/11/2013               -->
	
	<rich:modalPanel id="pNewPersona" width="400" height="250"
						resizeable="false" style="background-color:#DEEBF5;">
		<f:facet name="header">
			<h:panelGrid>
				<rich:column style="border: none;">
					<h:outputText value="Nueva Persona" />
				</rich:column>
			</h:panelGrid>
		</f:facet>
		<f:facet name="controls">
			<h:panelGroup>
				<h:graphicImage value="/images/icons/remove_20.png"
					styleClass="hidelink">
					<rich:componentControl for="pNewPersona" operation="hide"
						event="onclick" />
				</h:graphicImage>
			</h:panelGroup>
		</f:facet>
		<rich:spacer height="3px" />
		<h:form id="fmNewPersona">
			<h:panelGrid columns="2">
			         		<rich:column style="border:none;">
			         			<h:outputText value="Ap. Paterno:"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:inputText value="#{efectuadoController.socioSelecionado.strApePatSoc}"
			         						 size="30"/>			         						
			         		</rich:column>
			</h:panelGrid>
			<h:panelGrid columns="2">
			   <rich:column style="border:none;">
			      <h:outputText value="Ap. Materno: "/>
			   </rich:column>
			   <rich:column style="border:none;">
			      <h:inputText  value="#{efectuadoController.socioSelecionado.strApeMatSoc}"
			      				size="30"/>			      				
			   </rich:column>
			</h:panelGrid> 
			<h:panelGrid columns="2"> 
			     <rich:column style="border:none;">
			        <h:outputText value="Nombres: "/>
			     </rich:column>
			         		
			     <rich:column style="border:none;">
			         <h:inputText  value="#{efectuadoController.socioSelecionado.strNombreSoc}" 
			         				size="40"/>
			         				
			     </rich:column>			         					         		
			</h:panelGrid>
			
			<h:panelGrid columns="3">
				<rich:column style="border:none;" width="130px;">
					<h:outputText value="Número Documento"/>
          		</rich:column>
				<rich:column style="border:none;">
					<h:outputText value=":"/>
				</rich:column>
				<rich:column style="border:none;padding-left:10px;"> 
					<h:inputText   size="10" onkeydown="return validarNumDocIdentidad(this,event,8)"
								   style="text-align: right" value="#{efectuadoController.documentoSeleccionado.strNumeroIdentidad}"/>
				</rich:column>
			</h:panelGrid>
			
			<h:panelGroup id="panelMensaje1" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
							styleClass="rich-tabcell-noborder">
					<h:outputText value="#{efectuadoController.mensajeOperacion}" 
						styleClass="msgInfo"
						style="font-weight:bold"
						rendered="#{efectuadoController.mostrarMensajeExito}"/>
					<h:outputText value="#{efectuadoController.mensajeOperacion}" 
						styleClass="msgError"
						style="font-weight:bold"
						rendered="#{efectuadoController.mostrarMensajeError}"/>
			</h:panelGroup>
			
			<h:panelGrid id="panelGrabar" columns="2">
			<a4j:commandButton value="Validar" action="#{efectuadoController.validarNuevaPersona}"			    
			     reRender="panelGrabar,panelMensaje1" disabled="#{efectuadoController.blnNewPersona}"/>			
			<a4j:commandButton value="Grabar" action="#{efectuadoController.grabarNuevaPersona}"
			     oncomplete="Richfaces.hideModalPanel('pNewPersona')" disabled="#{!efectuadoController.blnNewPersona}"
			     reRender="tablaRelacionarSocio"/>
			</h:panelGrid>
		</h:form>
	</rich:modalPanel>	