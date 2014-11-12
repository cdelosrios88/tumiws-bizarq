<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Rodolfo Villarreal    			-->
	<!-- Modulo    : Tributacion               		-->
	<!-- Prototipo : PROTOTIPO TRIBUTACION		    -->
	<!-- Fecha     : 01/08/2014               		-->
	<!-- Modificación     	: Junior Chávez 		-->
	<!-- Fecha Modificación : 08/08/2014            -->

<rich:modalPanel id="mpTributacion" width="620" height="280"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border:none">
				<h:outputText value="Seleccionar el Impuesto"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hideSeleccionPersonaJuridica" />
			<rich:componentControl for="mpTributacion" attachTo="hideSeleccionPersonaJuridica"
				operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>
	
	<h:form id="frmTributacionPopup">
			<h:panelGroup id="divTributacion" layout="block">
	    	
	    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<a4j:commandButton action="#{tributacionController.searchImpuestoSelect}" value="Buscar" 
	    								reRender="divTblPersona" styleClass="btnEstilos"></a4j:commandButton>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	 
	    	<h:panelGroup id="divTblPersona" layout="block" style="padding:15px">
	    		<rich:extendedDataTable id="tblPersona" enableContextMenu="false" style="margin:0 auto"
	                        var="item" value="#{tributacionController.listaPersonaJuri}" 
			  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="550px" height="165px">
			                    
			            <rich:column width="60">
	                        <f:facet name="header">
	                            <h:outputText value="Opción"></h:outputText>
	                        </f:facet>
							<h:selectOneRadio onclick="seleccionarPersona(this.value)">
								<f:selectItem itemValue="#{rowKey}"/>
							</h:selectOneRadio>
			            </rich:column>
			            <rich:column width="41px">
			                   	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			            <rich:column width="350">
	                        <f:facet name="header">
	                            <h:outputText value="Razón Social"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.strJuriRazonsocial}"></h:outputText>
			            </rich:column> 
			            <rich:column>
	                        <f:facet name="header">
	                            <h:outputText value="RUC"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.strPersRuc}" ></h:outputText>
			            </rich:column>
			                    
				   <f:facet name="footer">
				   		 <h:panelGroup layout="block">
					   		 <rich:datascroller for="tblPersona" maxPages="10"/>
				   		 </h:panelGroup>  
			       </f:facet>
	            </rich:extendedDataTable>
	    	</h:panelGroup>
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>