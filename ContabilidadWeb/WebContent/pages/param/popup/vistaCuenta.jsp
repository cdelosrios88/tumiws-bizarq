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
	<!-- Prototipo : Ver Cuentas				    -->
	<!-- Fecha     : 08/08/2012               		-->

	
	<h:form id="frmVerCuentas">
	   	<h:panelGrid >
	   		<rich:column style="text-align: left">
	   			<h:outputText value="Las cuentas asociadas al elemento '#{anexoController.anexoDetalleSeleccionado.strTexto}' son :"/>
	   		</rich:column>
			<rich:column width="660">
				<rich:panel style="text-align: center;border:0px;width=650px;overflow: scroll;height:220px;" >
				<h:panelGrid width="620">
		        	<rich:dataTable 
						sortMode="single" 
						var="item" 
						value="#{anexoController.anexoDetalleSeleccionado.listaAnexoDetalleCuenta}"  
						rowKeyVar="rowKey" 
						width="600px" 
						rows="#{fn:length(anexoController.anexoDetalleSeleccionado.listaAnexoDetalleCuenta)}">					
							
							<rich:column width="20px">
			            		<h:outputText value="#{rowKey + 1}"></h:outputText>
			            	</rich:column>
							<rich:column width="80" style="text-align: center">
		                       <f:facet name="header">
		                            <h:outputText value="Periodo"/>
		                        </f:facet>
		                        <h:outputText value="#{item.id.intContPeriodoCuenta}"/>
				            </rich:column>
				            <rich:column width="250px" style="text-align: left">
								<f:facet name="header">
									<h:outputText value="Cuenta Contable"/>
								</f:facet>
								<h:outputText value="#{item.id.strContNumeroCuenta}"/>
							</rich:column>
							<rich:column width="250px" style="text-align: left">
								<f:facet name="header">
									<h:outputText value="Descripción"/>
								</f:facet>
								<h:outputText value="#{item.planCuenta.strDescripcion}"/>
							</rich:column>
						</rich:dataTable>
		        </h:panelGrid>
		        </rich:panel>
		   	</rich:column>			
	    </h:panelGrid>    
	    
	</h:form>