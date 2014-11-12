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
	<!-- Fecha     : 25/07/2014               		-->
	<!-- Modificación     	: Junior Chávez 		-->
	<!-- Fecha Modificación : 08/08/2014            -->

<rich:modalPanel id="mpSeleccionPersonaRol" width="620" height="280"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border:none">
				<h:outputText value="Selección de Personal"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hideSeleccionPersona" />
			<rich:componentControl for="mpSeleccionPersonaRol" attachTo="hideSeleccionPersona"
				operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>
	
	<h:form id="frmSeleccionPersonal">
	   	<h:panelGroup id="divSeleccionPersona" layout="block">
	    	
	    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:selectOneMenu value="#{tributacionController.intCboTipoPersonaBusq}" onchange="selecTipoPersona1(#{applicationScope.Constante.ONCHANGE_VALUE})">
	    					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_TIPOPERSONA}" 
								  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	    				</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
	    				<h:selectOneMenu id="cboFiltroPersona1" value="#{tributacionController.intCboFiltroPersonaBusq}" onchange="selecFiltroPersona1(#{applicationScope.Constante.ONCHANGE_VALUE})">
	    					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	    					<f:selectItems value="#{tributacionController.cboFiltroPersona}"/>
	    				</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
	    				<h:inputText id="txtPersonaBusq" value="#{tributacionController.strTxtPersonaBusq}" 
	    							disabled="#{tributacionController.isDisabledTxtPersonaBusq}" size="35"></h:inputText>
	    			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton action="#{tributacionController.buscarPersonal}" value="Buscar" 
	    								reRender="divTblPersona" styleClass="btnEstilos"></a4j:commandButton>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	
	    	<h:panelGroup id="divTblPersona" layout="block" style="padding:15px">
	    		<rich:extendedDataTable id="tblPersona" enableContextMenu="false" style="margin:0 auto"
	                        var="item" value="#{tributacionController.listaPersonal}" 
			  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="550px" height="165px">
			                    
			            <rich:column width="60">
	                        <f:facet name="header">
	                            <h:outputText value="Opción"></h:outputText>
	                        </f:facet>
							<h:selectOneRadio onclick="seleccionarPersonaRol(this.value)">
								<f:selectItem itemValue="#{rowKey}"/>
							</h:selectOneRadio>
			            </rich:column>
			            <rich:column width="41px">
			                   	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			            <rich:column width="350">
	                        <f:facet name="header">
	                            <h:outputText value="Apellidos y Nombres"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.strNombreCompleto}"></h:outputText>
							<%--<h:outputText value="#{item.juridica.strRazonSocial}" rendered="#{item.intTipoPersonaCod==Constante.PARAM_T_TIPOPERSONA_JURIDICA}"></h:outputText> --%>
			            </rich:column> 
			            <rich:column>
	                        <f:facet name="header">
	                            <h:outputText value="Documento"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.strNumeroDocumento}"></h:outputText>
							<%--<h:outputText value="#{item.strRuc}" rendered="#{item.intTipoPersonaCod==Constante.PARAM_T_TIPOPERSONA_JURIDICA}"></h:outputText> --%>
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