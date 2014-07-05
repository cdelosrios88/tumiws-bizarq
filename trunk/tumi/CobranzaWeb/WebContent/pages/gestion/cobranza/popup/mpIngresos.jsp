<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>

	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Fredy Ramirez       			-->
	<!-- Modulo    : Cobranza                 		-->
	<!-- Prototipo : Ingresos                       -->
	<!-- Fecha     : 28/11/2012               		-->

<rich:modalPanel id="mpIngresos" width="460" height="250"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Seleccionar Ingreso"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hideIngresos" />
			<rich:componentControl for="mpIngresos" attachTo="hideIngresos"
				operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>
	
<h:form>
	   	<h:panelGroup id="divIngresos" layout="block" style="padding-left:5px; padding-right:5px">
	    	
	    	<h:panelGroup id="divTblIngresos" layout="block" style="padding:13px">
	    		<rich:extendedDataTable id="tblIngresos" enableContextMenu="false" style="margin:0 auto"
	    					rendered="#{not empty enlaceReciboController.listIngreso}"
	                        var="item" value="#{enlaceReciboController.listIngreso}"  
			  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="400px" height="165px">
			                           
					 	<rich:column width="41">
			                   	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			                    
			            <rich:column>
	                        <f:facet name="header">
	                            <h:outputText value="Nro Ingreso"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.intItemPeriodoIngreso}-#{item.id.intIdIngresoGeneral}"></h:outputText>
			            </rich:column> 
			            <rich:column width="80">
	                        <f:facet name="header">
	                            <h:outputText value="Fecha"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.dtFechaIngreso}">
							  <f:convertDateTime pattern="dd/MM/yyyy"/>
							</h:outputText>
			            </rich:column>
			            
			            <rich:column width="100">
	                        <f:facet name="header">
	                            <h:outputText value="Monto"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.bdMontoTotal}">
							</h:outputText>
			            </rich:column>
			            
			            <rich:column width="41">
	                        <f:facet name="header">
	                            <h:outputText value="Sel."></h:outputText>
	                        </f:facet>
							<h:selectOneRadio valueChangeListener="#{enlaceReciboController.seleccionarIngreso}">
								<f:selectItem itemValue="#{rowKey}"/>
								<a4j:support event="onclick" ajaxSingle="true" reRender="txtDescIngreso"
											oncomplete="Richfaces.hideModalPanel('mpIngresos')"></a4j:support>
							</h:selectOneRadio>
			            </rich:column>
			                    
				   <f:facet name="footer">
				   		 <h:panelGroup layout="block">
					   		 <rich:datascroller for="tblIngresos" maxPages="10"/>
				   		 </h:panelGroup>  
			       </f:facet>
	            </rich:extendedDataTable>
	    	</h:panelGroup>
	   	</h:panelGroup>
	   	</h:form>	
</rich:modalPanel>