<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         		-->
	<!-- Autor     : Arturo Julca	    			-->
	<!-- Modulo    : Contabilidad               	-->

	
	<h:form id="frmSeleccionCuenta">
	   	<h:panelGroup layout="block">
	    	
	    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate" columns="5">
	    			<rich:column width="120">
	    				<h:selectOneMenu style="width: 120px;" 
	    					value="#{aperturaCierreController.intTipoBusquedaPlanCuenta}">
	    					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
	    					<tumih:selectItems var="sel" 
	    						cache="#{applicationScope.Constante.PARAM_T_FILTROSELECTPLANCUENTAS}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" 
								action="#{aperturaCierreController.seleccionarTipoBusquedaPlanCuenta}" 
								reRender="colTextoPlanCuenta"  />
	    				</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
           				<h:outputText value="Periodo"></h:outputText>
           			</rich:column>
           			<rich:column width="80">
           				<h:selectOneMenu style="width: 80px;" 
           					value="#{aperturaCierreController.planCuentaFiltro.id.intPeriodoCuenta}"
           					disabled="true">
           					<f:selectItem itemValue="-1" itemLabel="Todos"/>
           					<tumih:selectItems var="sel"
								value="#{aperturaCierreController.listaAnios}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
           				</h:selectOneMenu>
           			</rich:column>
	    			<rich:column id="colTextoPlanCuenta">
	    				<h:inputText
	    					value="#{aperturaCierreController.planCuentaFiltro.strComentario}"
	    					disabled="#{!aperturaCierreController.habilitarTextoPlanCuenta}" 
	    					size="30"/>
	    			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton action="#{aperturaCierreController.buscarPlanCuenta}" 
	    					value="Buscar" 
	    					reRender="divTblSeleccionCuenta" 
	    					styleClass="btnEstilos">
	    				</a4j:commandButton>
	    			</rich:column>	    		
	    	</h:panelGrid>
	    	
	    	<h:panelGroup id="divTblSeleccionCuenta" layout="block" style="padding:15px">
	    		<rich:dataTable id="tblSeleccionCuenta" 
	    			var="item" 
	                value="#{aperturaCierreController.listaPlanCuenta}" 
			  		rowKeyVar="rowKey" 
			  		rows="5" 
			  		sortMode="single" 
			  		width="660px">
			                           
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
			            <rich:column width="300" style="text-align: left">
	                        <f:facet name="header">
	                            <h:outputText value="Descripción"></h:outputText>
	                        </f:facet>
							<h:outputText value="#{item.strDescripcion}"></h:outputText>
			            </rich:column>
			            <rich:column width="100" style="text-align: center">
	                        <f:facet name="header">
	                            <h:outputText value="Seleccionar"></h:outputText>
	                        </f:facet>
							<h:selectOneRadio>
								<f:selectItem itemValue="#{rowKey}"/>
								<a4j:support event="onclick"
									actionListener="#{aperturaCierreController.seleccionarPlanCuenta}" 
									reRender="colTablaCuentaCierreDetalle,panelMensajeApertura,panelInferiorApertura" 
									ajaxSingle="true" 
									oncomplete="Richfaces.hideModalPanel('pBuscarCuenta')">
									<f:attribute name="item" value="#{item}"/>
								</a4j:support>
							</h:selectOneRadio>
			            </rich:column>
			                    
					   <f:facet name="footer">
					   		 <h:panelGroup layout="block">
						   		 <rich:datascroller for="tblSeleccionCuenta" maxPages="10"/>
					   		 </h:panelGroup>  
				       </f:facet>
	            </rich:dataTable>
	    	</h:panelGroup>
	   	</h:panelGroup>
	</h:form>