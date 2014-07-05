<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Fredy Ramirez      			-->
	<!-- Modulo    : Cobranza                 		-->
	<!-- Prototipo : SELECIONAR Garante       -->
	<!-- Fecha     : 06/12/2012               		-->

<rich:modalPanel id="mpBusqGarante" width="780" height="320"
	resizeable="false" style="background-color:#DEEBF5">
	<f:facet name="header">
		<h:panelGrid>
			<rich:column style="border: none;">
				<h:outputText value="Seleccionar Garante"></h:outputText>
			</rich:column>
		</h:panelGrid>
	</f:facet>
	<f:facet name="controls">
		<h:panelGroup>
			<h:graphicImage value="/images/icons/remove_20.png"
				styleClass="hidelink" id="hideBusqGarante" />
			<rich:componentControl for="mpBusqGarante" attachTo="hideBusqGarante"
				operation="hide" event="onclick" />
		</h:panelGroup>
	</f:facet>
	
<h:form id="mpBusqGaranteForm">
	   	<h:panelGroup layout="block" style="padding-left:5px; padding-right:5px">
	    	   <rich:panel style=";border:0px">
                <h:outputText style = "color:RED;" value="#{cuentaCteController.messageValidation}"></h:outputText>
              </rich:panel>
	    	<h:panelGrid styleClass="tableCellBorder4">
	    		<rich:columnGroup>
	    		    <rich:column>
	    		        <h:outputText value="DNI:"></h:outputText>
	    		    </rich:column>
	    			<rich:column>
           				<h:inputText value="#{cuentaCteController.txtDniGarante}"></h:inputText>
           			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton actionListener="#{cuentaCteController.buscarGarante}" value="Buscar" 
	    								reRender="divTblGarantes,mpBusqGaranteForm" styleClass="btnEstilos"></a4j:commandButton>
	    			</rich:column>
	    		</rich:columnGroup>
	    	</h:panelGrid>
	    	
	    	<h:panelGroup id="divTblGarantes" layout="block" style="padding:13px">
	    		<rich:extendedDataTable id="tblGarantes" enableContextMenu="false" style="margin:0 auto"
	    					rendered="#{not empty cuentaCteController.listGaranteComp}"
	                        var="item" value="#{cuentaCteController.listGaranteComp}"  
			  		 		rowKeyVar="rowKey" rows="5" sortMode="single" width="700px" height="200px">
			                           
					 	<rich:column width="41">
			                   	<h:outputText value="#{rowKey + 1}"></h:outputText>
			            </rich:column>
			            
			           <rich:column width="240" style="text-align: left">
	                        <f:facet name="header">
	                            <h:outputText value="Nombre" ></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.socio.id.intIdPersona} - #{item.socio.strNombreSoc} #{item.socio.strApePatSoc} #{item.socio.strApeMatSoc}"></h:outputText>
			            </rich:column>        
			            <rich:column width="60">
	                        <f:facet name="header">
	                            <h:outputText value="Dni"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.persona.documento.strNumeroIdentidad}"></h:outputText>
			            </rich:column> 
			            
			            <rich:column width="200" style="text-align: left">
	                        <f:facet name="header">
	                            <h:outputText value="Condición"></h:outputText>
	                        </f:facet>
	                        <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_CONDICIONSOCIO}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.cuenta.intParaCondicionCuentaCod}"/>
					 	     -
					 	    <tumih:outputText cache="#{applicationScope.Constante.PARAM_T_TIPO_CONDSOCIO}" 
							   		          itemValue="intIdDetalle" itemLabel="strDescripcion" 
										      property="#{item.cuenta.intParaSubCondicionCuentaCod}"/>
						</rich:column> 
				        <rich:column width="100">
	                        <f:facet name="header">
	                            <h:outputText value="Cuenta"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.cuenta.strNumeroCuenta}"></h:outputText>
			            </rich:column> 
			            
			            <rich:column width="41" >
	                        <f:facet name="header" >
	                            <h:outputText value="Sel."></h:outputText>
	                        </f:facet>
							<h:selectOneRadio valueChangeListener="#{cuentaCteController.seleccionarGarante}">
								<f:selectItem itemValue="#{rowKey}"/>
								<a4j:support event="onclick" ajaxSingle="true" reRender="divFormCambioGarante,divListCambioGarante,btnGrabar,divFormTransferencia"
											oncomplete="Richfaces.hideModalPanel('mpBusqGarante')"></a4j:support>
							</h:selectOneRadio>
			            </rich:column>
			                    
				   <f:facet name="footer">
				   		 <h:panelGroup layout="block">
					   		 <rich:datascroller for="tblGarantes" maxPages="10"/>
				   		 </h:panelGroup>  
			       </f:facet>
	            </rich:extendedDataTable>
	    	</h:panelGroup>
	   	</h:panelGroup>
	   	</h:form>	
</rich:modalPanel>