<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Fredy Ramirez      			-->
	<!-- Modulo    : Cobranza                 		-->
	<!-- Prototipo : SELECIONAR ENTIDAD/SOCIO       -->
	<!-- Fecha     : 07/11/2012               		-->

<rich:modalPanel id="mpBusqSocio" width="655" height="265"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Seleccionar Socio"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hideBusqSocio" />
			<rich:componentControl for="mpBusqSocio" attachTo="hideBusqSocio"
				operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>
	
<h:form>
	   	<h:panelGroup id="divBusqSocio" layout="block" style="padding-left:5px; padding-right:5px">
	    	
	    	<h:panelGrid styleClass="tableCellBorder4">
	    		<rich:columnGroup>
	    		    <rich:column>
	    		        <h:outputText value="Buscar Por:"></h:outputText>
	    		    </rich:column>
	    			<rich:column>
	    			    <h:selectOneMenu value="#{cobranzaController.intTipoBusqueda}">
		                     	<f:selectItem itemLabel="Dni" itemValue="1"/>
		                    	<f:selectItem itemLabel="Descripción" itemValue="2"/>
					   	</h:selectOneMenu>
           			</rich:column>
           			<rich:column>
           				<h:inputText value="#{cobranzaController.strDni}"></h:inputText>
           			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton actionListener="#{cobranzaController.buscarEntidadSocio}" value="Buscar" 
	    								reRender="divTblSocios" styleClass="btnEstilos"></a4j:commandButton>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	
	    	<h:panelGroup id="divTblSocios" layout="block" style="padding:13px">
	    		<rich:extendedDataTable id="tblSocios" enableContextMenu="false" style="margin:0 auto"
	    					rendered="#{not empty cobranzaController.listaSocio}"
	                        var="item" value="#{cobranzaController.listaSocio}"  
			  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="560px" height="165px">
			                           
					 	<rich:column width="41">
			                   	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			                    
			            <rich:column>
	                        <f:facet name="header">
	                            <h:outputText value="Dni"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.persona.documento.strNumeroIdentidad}"></h:outputText>
			            </rich:column> 
			            <rich:column width="360">
	                        <f:facet name="header">
	                            <h:outputText value="Nombre"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.socio.strNombreSoc} #{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}"></h:outputText>
			            </rich:column>
			            <rich:column width="41">
	                        <f:facet name="header">
	                            <h:outputText value="Sel."></h:outputText>
	                        </f:facet>
							<h:selectOneRadio valueChangeListener="#{cobranzaController.seleccionarEntidadSocio}">
								<f:selectItem itemValue="#{rowKey}"/>
								<a4j:support event="onclick" ajaxSingle="true" reRender="frmPrincipal,idStrSocioNombre,divTblGestionCobranzaDet,idIPaneltemExpediente"
											oncomplete="Richfaces.hideModalPanel('mpBusqSocio')"></a4j:support>
							</h:selectOneRadio>
			            </rich:column>
			                    
				   <f:facet name="footer">
				   		 <h:panelGroup layout="block">
					   		 <rich:datascroller for="tblSocios" maxPages="10"/>
				   		 </h:panelGroup>  
			       </f:facet>
	            </rich:extendedDataTable>
	    	</h:panelGroup>
	   	</h:panelGroup>
	   	</h:form>	
</rich:modalPanel>