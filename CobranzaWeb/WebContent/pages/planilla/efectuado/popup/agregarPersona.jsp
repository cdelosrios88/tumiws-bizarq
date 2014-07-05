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
	<!-- Prototipo : EFECTUADO agregar Persona-->
	<!-- Fecha     : 28/11/2013               -->
	
	<rich:modalPanel id="pAgregarPersona" width="375" height="225"
						resizeable="false" style="background-color:#DEEBF5;">
		<f:facet name="header">
			<h:panelGrid>
				<rich:column style="border: none;">
					<h:outputText value="Agregar Persona" />
				</rich:column>
			</h:panelGrid>
		</f:facet>
		<f:facet name="controls">
			<h:panelGroup>
				<h:graphicImage value="/images/icons/remove_20.png"
					styleClass="hidelink">
					<rich:componentControl for="pAgregarPersona" operation="hide"
						event="onclick" />
				</h:graphicImage>
			</h:panelGroup>
		</f:facet>
		<rich:spacer height="3px" />
		<h:form id="formAgregarPersona">
			<h:panelGrid columns="2">
			         		<rich:column style="border:none;">
			         			<h:outputText value="Ap. Paterno:"/>
			         		</rich:column>
			         		<rich:column style="border:none;">
			         			<h:inputText  size="30" value="#{efectuadoController.socioSelecionado.strApePatSoc}"/>
			         		</rich:column>
			</h:panelGrid>
			<h:panelGrid columns="2">
			   <rich:column style="border:none;">
			      <h:outputText value="Ap. Materno: "/>
			   </rich:column>
			   <rich:column style="border:none;">
			      <h:inputText  size="30" value="#{efectuadoController.socioSelecionado.strApeMatSoc}"/>
			   </rich:column>
			</h:panelGrid> 
			<h:panelGrid columns="2"> 
			     <rich:column style="border:none;">
			        <h:outputText value="Nombres: "/>
			     </rich:column>
			         		
			     <rich:column style="border:none;">
			         <h:inputText  size="40" value="#{efectuadoController.socioSelecionado.strNombreSoc}"/>
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
			<h:panelGrid>
			<a4j:commandButton value="Grabar" action="#{efectuadoController.grabarAgregarPersona}"
			     oncomplete="Richfaces.hideModalPanel('pAgregarPersona')" reRender="tablaRelacionarSocio"/>
			</h:panelGrid>
		</h:form>
	</rich:modalPanel>	