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
	<!-- Prototipo : SELECCIONAR ELEMENTO		    -->
	<!-- Fecha     : 08/08/2012               		-->

	
	<h:form id="frmSeleccionCuentaRatio">
	   	<h:panelGroup id="divSeleccionCuenta" layout="block">
	    	
	    	<h:panelGrid style="border-spacing:5px 0px; border-collapse:separate" columns="5">
	    			<rich:column width="120">
	    				<h:selectOneMenu style="width: 120px;" 
	    					value="#{ratioController.intTipoBusquedaPlanCuenta}">
	    					<f:selectItem itemValue="0" itemLabel="Seleccione"/>
	    					<tumih:selectItems var="sel" 
	    						cache="#{applicationScope.Constante.PARAM_T_FILTROSELECTPLANCUENTAS}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
							<a4j:support event="onchange" 
								action="#{ratioController.seleccionarTipoBusquedaPlanCuenta}" 
								reRender="colTextoPlanCuenta"  />
	    				</h:selectOneMenu>
	    			</rich:column>
	    			<rich:column>
           				<h:outputText value="Periodo"></h:outputText>
           			</rich:column>
           			<rich:column width="80">
           				<h:selectOneMenu style="width: 80px;" 
           					value="#{ratioController.planCuentaFiltro.id.intPeriodoCuenta}">
           					<f:selectItem itemValue="-1" itemLabel="Todos"/>
           					<tumih:selectItems var="sel"
								value="#{ratioController.listaAnios}" 
								itemValue="#{sel.intIdDetalle}" 
								itemLabel="#{sel.strDescripcion}"/>
           				</h:selectOneMenu>
           			</rich:column>
	    			<rich:column id="colTextoPlanCuenta">
	    				<h:inputText
	    					value="#{ratioController.planCuentaFiltro.strComentario}"
	    					disabled="#{!ratioController.habilitarTextoPlanCuenta}" 
	    					size="30"/>
	    			</rich:column>
	    			<rich:column>
	    				<a4j:commandButton action="#{ratioController.buscarPlanCuenta}" 
	    					value="Buscar" 
	    					reRender="divTblSeleccionCuentarATIO" 
	    					styleClass="btnEstilos">
	    				</a4j:commandButton>
	    			</rich:column>	    		
	    	</h:panelGrid>
	    	
	    	
	    	<h:panelGroup id="panelDesPlanCuenta">
		    	<h:panelGroup rendered="#{empty ratioController.anexoDetalleSeleccionado.listaAnexoDetalleCuenta}">
		    		<h:outputText value="No posee una cuenta seleccionada actualemente"/>
		    	</h:panelGroup>
		    	<h:panelGroup rendered="#{!empty ratioController.anexoDetalleSeleccionado.listaAnexoDetalleCuenta}">
		    		<h:outputText value="Cuenta seleccionada actualemente : '#{ratioController.anexoDetalleSeleccionado.listaAnexoDetalleCuenta[0].id.strContNumeroCuenta}' "/>
		    		<a4j:commandLink 
		    			value="Deseleccionar"
						action="#{ratioController.deseleccionarPlanCuenta}"
						reRender="panelDesPlanCuenta,colListaTextoRatio">
					</a4j:commandLink>
		    	</h:panelGroup>
		    </h:panelGroup>		  
		    	
	    	<h:panelGroup id="divTblSeleccionCuentarATIO" layout="block" style="padding:15px">
	    		<rich:dataTable id="tblSeleccionCuenta" 
	    			var="item" 
	                value="#{ratioController.listaPlanCuenta}" 
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
									actionListener="#{ratioController.seleccionarPlanCuenta}" 
									reRender="colListaTextoRatio,panelMensajeRatio" 
									ajaxSingle="true" 
									oncomplete="Richfaces.hideModalPanel('pSeleccionCuenta')">
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