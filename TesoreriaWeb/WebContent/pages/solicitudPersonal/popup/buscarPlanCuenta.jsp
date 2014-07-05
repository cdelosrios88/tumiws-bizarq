<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Arturo Julca	    			-->

<rich:modalPanel id="pBuscarPlanCuenta" width="730" height="290"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Buscar Plan Cuenta"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
           <h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pBuscarPlanCuenta" operation="hide" event="onclick"/>
           </h:graphicImage>
       </h:panelGroup>
    </f:facet>	

	<h:form>
	    	<h:panelGrid columns="5" id="panelBuscarPC">
	    			<rich:column width="120">
	    				<h:selectOneMenu style="width: 120px;" 
	    					value="#{solicitudPersonalController.intTipoBusquedaPlanCuenta}">
	    					<tumih:selectItems var="sel" 
	    						cache="#{applicationScope.Constante.PARAM_T_FILTROSELECTPLANCUENTAS}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" reRender="panelBuscarPC"/>
	    				</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
           				<h:outputText value="Periodo"></h:outputText>
           			</rich:column>
           			<rich:column width="100">
           				<h:selectOneMenu style="width: 100px;" 
           					disabled="true"
           					value="#{solicitudPersonalController.planCuentaFiltro.id.intPeriodoCuenta}">
           					<f:selectItem itemValue="0" itemLabel="Todos"/>
           					<tumih:selectItems var="sel"
           						value="#{solicitudPersonalController.listaAnios}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
           				</h:selectOneMenu>
           			</rich:column>
	    			<rich:column>
	    				<h:inputText
	    					value="#{solicitudPersonalController.planCuentaFiltro.strComentario}"
	    					size="45"/>
	    			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton action="#{solicitudPersonalController.buscarPlanCuenta}" 
	    					value="Buscar" 
	    					reRender="tablaBuscarPlanCuenta" 
	    					styleClass="btnEstilos">
	    				</a4j:commandButton>
	    			</rich:column>	    		
	    	</h:panelGrid>
		    	
	    	<h:panelGroup id="tablaBuscarPlanCuenta">
	    		<rich:dataTable id="tblSeleccionCuenta" 
	    			var="item" 
	                value="#{solicitudPersonalController.listaPlanCuenta}" 
			  		rowKeyVar="rowKey" 
			  		rows="5" 
			  		sortMode="single" 
			  		width="710px">
			                           
					<rich:column width="30px">
			        	<h:outputText value="#{rowKey + 1}"></h:outputText>
			  		</rich:column>			                    
			        <rich:column width="80">
	                	<f:facet name="header">
							<h:outputText value="Periodo"></h:outputText>
	             		</f:facet>
						<h:outputText value="#{item.id.intPeriodoCuenta}"></h:outputText>
					</rich:column> 
					<rich:column width="150">
						<f:facet name="header">
							<h:outputText value="Cuenta"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.id.strNumeroCuenta}"></h:outputText>
					</rich:column>
					<rich:column width="350" style="text-align: left">
						<f:facet name="header">
							<h:outputText value="Descripción"></h:outputText>
						</f:facet>
						<h:outputText value="#{item.strDescripcion}"></h:outputText>
					</rich:column>
					<rich:column width="100" style="text-align: center">
						<f:facet name="header">
							<h:outputText value="Seleccionar"/>
						</f:facet>
						<a4j:commandLink
							value="Seleccionar"
							actionListener="#{solicitudPersonalController.seleccionarPlanCuenta}" 
							reRender="panelPlanCuentaDetalle" 
							oncomplete="Richfaces.hideModalPanel('pBuscarPlanCuenta')">
							<f:attribute name="item" value="#{item}"/>
						</a4j:commandLink>
			     	</rich:column>			                    
					
					<f:facet name="footer">
						<h:panelGroup layout="block">
							<rich:datascroller for="tblSeleccionCuenta" maxPages="10"/>
					   	</h:panelGroup>
					</f:facet>
	            </rich:dataTable>
	    	</h:panelGroup>
	</h:form>
	</rich:modalPanel>