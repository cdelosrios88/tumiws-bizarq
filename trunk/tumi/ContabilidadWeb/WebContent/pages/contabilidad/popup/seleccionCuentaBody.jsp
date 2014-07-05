<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Jhonathan Morán    			-->
	<!-- Modulo    : Créditos                 		-->
	<!-- Prototipo : PROTOTIPO SOCIO ESTRUCTURA     -->
	<!-- Fecha     : 17/05/2012               		-->

<rich:modalPanel id="mpSeleccionCuenta" width="610" height="262"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="#{planCuentasController.strTituloPopupCuenta}"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hideSeleccionCueta" />
			<rich:componentControl for="mpSeleccionCuenta" attachTo="hideSeleccionCueta"
				operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>
	
	<h:form id="frmSeleccionCuenta">
	   	<h:panelGroup id="divSeleccionCuenta" layout="block">
	    	
	    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate">
	    		<rich:columnGroup>
	    			<rich:column>
	    				<h:selectOneMenu value="#{planCuentasController.intCboTipoCuentaBusq}" onchange="selecTipoCuenta(#{applicationScope.Constante.ONCHANGE_VALUE})">
	    					<f:selectItem itemValue="0" itemLabel="Seleccione.."/>
	    					<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_FILTROSELECTPLANCUENTAS}" 
								  				itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}"/>
	    				</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
           				<h:outputText value="Período"></h:outputText>
           			</rich:column>
           			<rich:column>
           				<h:selectOneMenu value="#{planCuentasController.intCboPeriodoBusq}">
           					<f:selectItems value="#{planCuentasController.cboPeriodos}"></f:selectItems>
           				</h:selectOneMenu>
           			</rich:column>
	    			<rich:column>
	    				<h:inputText id="txtCuentaBusq" value="#{planCuentasController.strCuentaBusq}" 
	    							disabled="#{planCuentasController.isDisabledTxtCuentaBusq}" size="35"></h:inputText>
	    			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton actionListener="#{planCuentasController.filtrarCuentaOrigenDestino}" value="Buscar" 
	    								reRender="divTblSeleccionCuenta" styleClass="btnEstilos"></a4j:commandButton>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	
	    	<h:panelGroup id="divTblSeleccionCuenta" layout="block" style="padding:15px">
	    		<rich:extendedDataTable id="tblSeleccionCuenta" enableContextMenu="false" style="margin:0 auto"
	                        var="item" value="#{planCuentasController.listCuentaOrigenDestino}" 
			  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="550px" height="165px">
			                           
					 	<rich:column width="31px">
			                   	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			                    
			            <rich:column>
	                        <f:facet name="header">
	                            <h:outputText value="Periodo"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.id.intPeriodoCuenta}"></h:outputText>
			            </rich:column> 
			            <rich:column width="120">
	                        <f:facet name="header">
	                            <h:outputText value="Cuenta"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.id.strNumeroCuenta}"></h:outputText>
			            </rich:column>
			            <rich:column width="200">
	                        <f:facet name="header">
	                            <h:outputText value="Descripción" style="font-weight:bold"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.strDescripcion}"></h:outputText>
			            </rich:column>
			            <rich:column>
	                        <f:facet name="header">
	                            <h:outputText value="Seleccionar"></h:outputText>
	                        </f:facet>
							<h:selectOneRadio valueChangeListener="#{planCuentasController.seleccionarCuenta}">
								<f:selectItem itemValue="#{rowKey}"/>
								<a4j:support event="onclick" reRender="txtCuentaOrigen,txtCuentaDestino" ajaxSingle="true" 
											oncomplete="Richfaces.hideModalPanel('mpSeleccionCuenta')"></a4j:support>
							</h:selectOneRadio>
			            </rich:column>
			                    
				   <f:facet name="footer">
				   		 <h:panelGroup layout="block">
					   		 <rich:datascroller for="tblSeleccionCuenta" maxPages="10"/>
				   		 </h:panelGroup>  
			       </f:facet>
	            </rich:extendedDataTable>
	    	</h:panelGroup>
	   	</h:panelGroup>
	</h:form>
</rich:modalPanel>

<!-- Added by cdelosrios on 13/09/2013 -->
<rich:modalPanel id="pSeleccionElemento" width="530" height="330"
	resizeable="false" style="background-color:#DEEBF5;">
    <f:facet name="header">
        <h:panelGrid>
          <rich:column style="border: none;">
            <h:outputText value="Seleccionar Elemento"/>
          </rich:column>
        </h:panelGrid>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
       		<h:graphicImage value="/images/icons/remove_20.png" styleClass="hidelink">
           		<rich:componentControl for="pSeleccionElemento" operation="hide" event="onclick"/>
           	</h:graphicImage>
        </h:panelGroup>
    </f:facet>
    <a4j:include viewId="/pages/contabilidad/popup/seleccionElemento.jsp"/>
</rich:modalPanel>
<!-- End added by cdelosrios on 13/09/2013 -->