<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

<rich:modalPanel id="pOpciones" width="300" height="125"
						resizeable="false" style="background-color:#DEEBF5;">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Opciones a exportar" />
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink">
				<rich:componentControl for="pOpciones" operation="hide" event="onclick" />
			</h:graphicImage>
		</h:panelGroup>
	</f:facet>
	<rich:spacer height="3px" />
		<h:form id="fmOpciones">
			<h:panelGrid columns="6">
				<rich:column style="border:none;">
			         <h:commandButton value="TEXTO" styleClass="btnEstilosPlanilla"
			         				 rendered="#{envioController.blnMostrarTxth}"
					 				 action="#{envioController.exportarEnvioresumenTXT}"
					 				 style="width:75px"/>												
			    </rich:column>
			    <rich:column style="border:none;">
			         <a4j:commandButton value="TEXTO" styleClass="btnEstilosPlanilla" 
					 					action="#{envioController.exportarTXTa4j}"
					 					rendered="#{envioController.blnMostrarTxta4j}"
					 					style="width:75px" reRender="panelMensaje,fmOpciones"/>												
			    </rich:column>				    
			    <rich:column style="border:none;">
			         <h:commandButton value="EXCEL" styleClass="btnEstilosPlanilla" 
					 				  action="#{envioController.exportarEnvioresumenEXCEL}"
					 				  rendered="#{envioController.blnMostrarExcelh}"
					 				  style="width:75px"/>			
			    </rich:column>
			    <rich:column style="border:none;">
			         <a4j:commandButton value="EXCEL" styleClass="btnEstilosPlanilla" 
					 					action="#{envioController.exportarEXCELa4j}"
					 					rendered="#{envioController.blnMostrarExcela4j}"
					 					style="width:75px" reRender="panelMensaje,fmOpciones"/>			
			    </rich:column>
			    <rich:column style="border:none;">
			         <h:commandButton value="DBF" styleClass="btnEstilosPlanilla" 
					 				  action="#{envioController.exportarEnvioresumenDBF}"
					 				  rendered="#{envioController.blnMostrarDBFh}"
					 				  style="width:75px"/>			
			    </rich:column>
			     <rich:column style="border:none;">
			         <a4j:commandButton value="DBF" styleClass="btnEstilosPlanilla" 
					 					action="#{envioController.exportarDBFa4j}"
					 					rendered="#{envioController.blnMostrarDBFa4j}"
					 					style="width:75px" reRender="panelMensaje,fmOpciones"/>			
			    </rich:column>			    
			</h:panelGrid>
			<rich:spacer height="8px" />
			<h:panelGroup id="panelMensaje" style="border: 0px solid #17356f;background-color:#DEEBF5;text-align: center"
			styleClass="rich-tabcell-noborder">
			<h:outputText value="#{envioController.mensajeOperacion}" 
				styleClass="msgInfo"
				style="font-weight:bold"
				rendered="#{envioController.mostrarMensajeExito}"/>
			<h:outputText value="#{envioController.mensajeOperacion}" 
				styleClass="msgError"
				style="font-weight:bold"
				rendered="#{envioController.mostrarMensajeError}"/>
		</h:panelGroup>
		</h:form>
</rich:modalPanel>						