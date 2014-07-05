<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Arturo Julca	    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : CONFIGURACION				    -->
	<!-- Fecha     : 08/08/2012               		-->

	
	<h:form id="frmConfigurar">
	   	<h:panelGrid >
	   		<rich:column style="text-align: left">
	   			<h:outputText value="Seleccione un elemento del esquema que desea usar como operando:"/>
	   		</rich:column>
			<rich:column width="680">
				<rich:panel style="text-align: center;border:0px;width=670px;overflow: scroll;height:140px;" >
				<h:panelGrid width="650">
		        	<rich:dataTable 
						sortMode="single" 
					    var="item" 
					    value="#{anexoController.listaAnexoDetalle}"  
						rowKeyVar="rowKey" 
						width="640px" 
						rows="#{fn:length(anexoController.listaAnexoDetalle)}">					
						
						<rich:column width="60" style="text-align: center">
	                        <f:facet name="header">
	                            <h:outputText value="Agregar"/>
	                        </f:facet>
							<a4j:commandLink
								value="Agregar"
								actionListener="#{anexoController.agregarAnexoDetalleOperacion}"
								reRender="panelListaAnexoDetOp,colListaTexto">
									<f:attribute name="item" value="#{item}"/>
							</a4j:commandLink>
			            </rich:column>
			            <rich:column width="580px" style="text-align: left">
							<f:facet name="header">
								<h:outputText value="Descripción"/>
							</f:facet>
							<h:outputText value="#{item.strNumeracion}.- " escape="false"/>
							<h:outputText value="#{item.strTexto}"/>
						</rich:column>						
					</rich:dataTable>
		        </h:panelGrid>
		        </rich:panel>
		   	</rich:column>			
	    </h:panelGrid>
	    
	    <rich:spacer height="5px"/>  
	     	
	    	<h:panelGrid id="panelListaAnexoDetOp">
	    		<rich:column style="text-align: left">
	    			<h:outputText value="El elemento '#{anexoController.anexoDetalleSeleccionado.strTexto}' se conformara por las siguientes operaciones:"/>
	    		</rich:column>
	    		<rich:column width="680">
		    		<rich:panel style="text-align: center;border:0px;width=670px;overflow: scroll;height:140px;" >
		    		<h:panelGrid width="650">
		    			<rich:dataTable 
							sortMode="single" 
						    var="item" 
						    value="#{anexoController.listaAnexoDetalleOperador}"  
							rowKeyVar="rowKey" 
							width="640px"
							rows="#{fn:length(anexoController.listaAnexoDetalleOperador)}">					
							
							<rich:column width="60" style="text-align: center">
		                        <f:facet name="header">
		                            <h:outputText value="Desagregar"/>
		                        </f:facet>
		                        <a4j:commandLink
									value="Desagregar"
									actionListener="#{anexoController.desagregarAnexoDetalleOperacion}"
									reRender="panelListaAnexoDetOp,colListaTexto">
										<f:attribute name="item" value="#{item}"/>
								</a4j:commandLink>
				            </rich:column>
				            <rich:column width="80px" style="text-align: center">
								<f:facet name="header">
									<h:outputText value="Operación"/>
								</f:facet>
								<h:selectOneMenu
									style="width: 80px;"
									value="#{item.intTipoOperacion}">
									<tumih:selectItems var="sel" 
										cache="#{applicationScope.Constante.PARAM_T_TIPOOPERACIONCONTABLE}" 
										itemValue="#{sel.intIdDetalle}" 
										itemLabel="#{sel.strDescripcion}"/>
								</h:selectOneMenu>
							</rich:column>
							<rich:column width="500px" style="text-align: left">
								<f:facet name="header">
									<h:outputText value="Descripción"/>
								</f:facet>
								<h:outputText value="#{item.anexoDetalleReferencia.strNumeracion}.- " escape="false"/>
								<h:outputText value="#{item.anexoDetalleReferencia.strTexto}"/>
							</rich:column>
						</rich:dataTable>
					</h:panelGrid>					

					</rich:panel>
				</rich:column>
	    	</h:panelGrid>
	    	
	    	<h:panelGrid columns="2">
				<rich:column width="190">
			    </rich:column>
			    <rich:column style="border:none" id="colBtnModificar">
			    	<a4j:commandButton value="Aceptar" styleClass="btnEstilos"
		        		action="#{anexoController.aceptarConfigurar}"
		        		onclick="Richfaces.hideModalPanel('pConfigurar')"
		        		reRender="colListaTexto"
		        	/>
		        	<a4j:commandButton value="Cancelar" styleClass="btnEstilos"
		           		action="#{anexoController.cancelarConfigurar}"
		             	onclick="Richfaces.hideModalPanel('pConfigurar')"
		        	/>
			 	</rich:column>
			</h:panelGrid>
	</h:form>